/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.secant.migration.utility;

/**
 *
 * @author Manjut
 */
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageReadParam;
import javax.imageio.stream.FileImageInputStream;

import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageReader;
import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageReaderSpi;
import org.dcm4che2.io.DicomOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Kumar Satyam
 *
 * This is an utility class for Matrix to decode RLE images.
 *
 * This class reads the DICOM image and extract the pixel data and decode the pixel data, then write back to another
 * DICOM file. This class also modifies the required headers for planar configuration.
 */
public class ImageProcessor {

    private int planarConfigration;
    /*
     * Based on this flag, SamplePerPixel tag at createDicomFile(..) method get modified.
     */
    private boolean  sppOne = false;
    private static final Log log = LogFactory.getLog(ImageProcessor.class);
    //private static final Logger log = LoggerFactory.getLogger(ImageProcessor.class);

    private File sourceDir = null;

//    public static void main(String[] args) {
//        log.info("Process Started...");
//        ImageProcessor rledf = new ImageProcessor();
//        try {
//            rledf.processDirectory(new File("D:\\DICOM Dump Work folder\\Migration Test\\001RMC0T022005061014455572 - Copy\\images_1"));
//        } catch (Exception e) {
//            System.err.println("Check the planar configutarion/Type of file.");
//        }
//    }

    /**
     * Default constructor.
     */
    public ImageProcessor() {
    }

    public ImageProcessor(File RLE_Encoded_Img_Dir) {
        super();
        planarConfigration = -1;//MatrixProperties.planarConfigration;
        sourceDir = RLE_Encoded_Img_Dir;
        processDirectory(sourceDir);
    }


    /**
     * This method reads the source DICOM file, decodes it and write to the destination file.
     * @param source File
     * @param destination File
     */
    public void decodeRLEFile(File source, File destination) {

        int height = -1;
        int width = -1;
        int samplesPerPixel = -1;
        String transferSyntax = null;
        String photometricInterpretation = null;

        DicomImageReader reader = null;
        FileImageInputStream input = null;

        DicomObject dicomObject = new BasicDicomObject();

        try {
            input = new FileImageInputStream(source);

            // creating the instance of the DicomImageReader.
            reader = (DicomImageReader) new DicomImageReaderSpi().createReaderInstance();

            dicomObject = new DicomInputStream(source).readDicomObject();

            height = dicomObject.getInt(Tag.Columns);
            width = dicomObject.getInt(Tag.Rows);

            /*
             * Reading SamplePerPixel, transferSyntax and PhotometricInterpretation for each object.
             */
            samplesPerPixel = dicomObject.getInt(Tag.SamplesPerPixel);
            transferSyntax = dicomObject.getString(Tag.TransferSyntaxUID);
            photometricInterpretation = dicomObject.getString(Tag.PhotometricInterpretation).toUpperCase();

            /*
             * Checking the DICOM image Transfer syntax for RLELossLess. If the TransferSyntax not RLE then skip image.
             * Else check the SamplesPrePixel. If the SamplesPerPixel is 1 and PhotometricInterpretation = YBR_FULL then
             * we need to change the SamplePerPixel to 3.
             */

            // Checking the DICOM image is in RLE or not?
            if (transferSyntax.equals(UID.RLELossless)) {
                /*
                 * Check photometricInterpretation. If it is YBR_FULL or YBR_FULL_422 etc, it will mark for decoding.
                 */
                if((photometricInterpretation.equalsIgnoreCase("YBR_FULL")
                        || photometricInterpretation.equalsIgnoreCase("YBR_FULL_422")) && samplesPerPixel == 1 ){
                    log.info("File " + source.getName() + " Found as " + photometricInterpretation
                            + " with SamplesPerPixel is " + samplesPerPixel);
                    sppOne = true;
                }
                if(!(photometricInterpretation.equals("YBR_FULL") )){
                    log.info("ImageProcessor skipping the RLE image " + source.getName() + ". Reason : File not in "
                            + "YBR_FULL. It is in " + photometricInterpretation);
                    return;
                }
            } else {
                log.info("ImageProcessor skipping the image " + source.getName() + ". Reason TransferSyntax is not RLE : "
                        + transferSyntax);
                return;
            }

        } catch (IOException ex) {
            log.error("Unable to read souce file. Due to : " + ex.getMessage());
            return;
        }
        reader.setInput(input);

        ImageReadParam imageReadParam = reader.getDefaultReadParam();
        try {

            byte[] pixelData_RLE_Encoded = reader.readBytes(0, imageReadParam);

            int numOfSeg = (int) arr2long(pixelData_RLE_Encoded, 0);
            int Y_seg_start = (int) arr2long(pixelData_RLE_Encoded, 4);
            int B_seg_start = (int) arr2long(pixelData_RLE_Encoded, 8);
            int R_seg_start = (int) arr2long(pixelData_RLE_Encoded, 12);

            byte[] YBR_UNCOMPRESSED = decodeRLE(pixelData_RLE_Encoded, Y_seg_start, B_seg_start, R_seg_start, width, height, numOfSeg);

            createDicomFile(YBR_UNCOMPRESSED, source, destination,UID.ExplicitVRLittleEndian);
            input.close();

        } catch (IOException ex) {
            log.error("Exception in processing the pixel data : " + ex.getMessage());
        }
    }

    /**
     * @author Anil Kumar.C
     *
     * Reades a byte array fron the given start position and convert four bytes from start position to long value.
     *
     * @param arr byte[] - Byte array to read.
     * @param start int  - Starting position of the byte array to read.
     * @return long.
     */
    private static long arr2long(byte[] arr, int start) {
        int index = 0;
        int length = 4;
        int pos = 0;
        byte[] tmp = new byte[length];
        long value = 0;
        try {
            for (index = start; index < (start + length); index++) {
                tmp[pos] = arr[index];
                pos++;
            }
            index = 0;
            for (int shiftBy = 0; shiftBy < 32; shiftBy += 8) {
                value |= ((long) (tmp[index] & 0xff)) << shiftBy;
                index++;
            }
        } catch (Exception e) {
            log.error("Unable to convert to long. Due to : " + e.getMessage());

        }
        return value;
    }

    /**
     * @author Anil Kumar.C
     *
     * This method takes a RLE encoded pixel data and extract the Y_COMPRESSED_COMPONENT, B_COMPRESSED_COMPONENT,
     * R_COMPRESSED_COMPONENT. Then decode each component sepratly and generate Y_UNCOMPRESSED_COMPONENT,
     * B_UNCOMPRESSED_COMPONENT, R_UNCOMPRESSED_COMPONENT.
     *
     * Again merge these uncompressed components in to single YBR_UNCOMPRESED like Y0B0R0,Y1B1R1,Y2B2R2...ETC.
     *
     * This YBR_UNCOMPRESSED will be returned back to the caller.
     *
     * @param pixelData byte[] RLE Compressed pixel data with all components.
     * @param Y_offset int -Y component offset value
     * @param B_offset int -B component offset value
     * @param R_offset int -R component offset value
     * @param width int  -width of the image
     * @param height int -height of the image.
     * @param frames int -number of frames in the image.
     * @return byte[] -RLE decompressed pixel data.
     */
    private final byte[] decodeRLE(byte[] pixelData, int Y_offset, int B_offset, int R_offset, int width, int height, int frames) {

        // Creating three components[Y,B,R] from the pixel data.
        byte[] y_compressed_component = new byte[B_offset - Y_offset];
        byte[] b_compressed_component = new byte[R_offset - B_offset];
        byte[] r_compressed_component = new byte[pixelData.length - R_offset];

        // Create three byte arrays to store decoded compenent
        byte[] y_uncompressed_component = new byte[width * height];
        byte[] b_uncompressed_component = new byte[width * height];
        byte[] r_uncompressed_component = new byte[width * height];

        byte[] YBR_UNCOMPRESSED = new byte[width * height * frames];

        int index = 0;
        for (int pos = Y_offset; pos < B_offset; pos++) {
            y_compressed_component[index++] = pixelData[pos];
        }
        index = 0;
        for (int pos = B_offset; pos < R_offset; pos++) {
            b_compressed_component[index++] = pixelData[pos];
        }
        index = 0;
        for (int pos = R_offset; pos < pixelData.length; pos++) {
            r_compressed_component[index++] = pixelData[pos];
        }

        // Y decoded component.
        y_uncompressed_component = decodeComponent(y_compressed_component, width, height);
        // B decoded component.
        b_uncompressed_component = decodeComponent(b_compressed_component, width, height);
        // R decoded component.
        r_uncompressed_component = decodeComponent(r_compressed_component, width, height);

        int idx_YBR = 0;

        if ( planarConfigration == -1) {
            log.error("First Set the Planar configuration.");
            System.exit(0);
        }

        // Check the planar configuration is supprtive or not?
        if (planarConfigration < -1 || planarConfigration > 1) {
            log.info("Plannar configuration is not supported. Check whether planar configuration is set or not?");
            log.info("Stoping the application...");
            System.exit(0);
        }

        /*
         * If the PLANAR CONFIGURATION is 1, then we need to arrange the pixel data in Y0B0R0, Y1B1R1 .... YnBnRn format.
         */
        if (planarConfigration == 0) {
            for (int pos = 0; pos < height * width; pos++) {
                YBR_UNCOMPRESSED[idx_YBR++] = y_uncompressed_component[pos];
                YBR_UNCOMPRESSED[idx_YBR++] = b_uncompressed_component[pos];
                YBR_UNCOMPRESSED[idx_YBR++] = r_uncompressed_component[pos];
            }
        }
        /*
         * If the PLANAR CONFIGURATION is 0, then we need to arrange the pixel data in Y0Y1Y2...Yn, B0B1B2...Bn,R0R1 ....Rn format.
         */
        if (planarConfigration == 1) {
            for (int pos = 0; pos < height * width; pos++) {
                YBR_UNCOMPRESSED[idx_YBR++] = y_uncompressed_component[pos];
            }
            for (int pos = 0; pos < height * width; pos++) {
                YBR_UNCOMPRESSED[idx_YBR++] = b_uncompressed_component[pos];
            }
            for (int pos = 0; pos < height * width; pos++) {
                YBR_UNCOMPRESSED[idx_YBR++] = r_uncompressed_component[pos];
            }
        }

        // Decoded pixel data with YBR component.
        return YBR_UNCOMPRESSED;
    }

    /**
     * @author Anil Kumar.C
     *
     * This method accepts Y or B or R componts which are RLE compressed and decode them and return a uncompressed
     * component of Y or B or R.
     *
     * @param component byte[] -RLE encoded component.
     * @param width int  -width of the image
     * @param height int -height of the image.
     * @return byte[]    -decoded component.
     */
    private final byte[] decodeComponent(byte[] component, int width, int height) {

        byte[] com_component = component;
        byte[] uncom_component = new byte[width * height];
        int placeHolder;
        byte repeatVal;

        int index = 0;

        for (int pos = 0; pos < com_component.length; pos++) {
            placeHolder = component[pos];
            if (placeHolder >= 0 && placeHolder <= 127) {
                /*
                 * If the placeHolder is +ve and in the range of [0 to 127], read next (placeHolder + 1) bytes.
                 */
                for (int repeater = 0; repeater < placeHolder + 1; repeater++) {
                    try {
                        uncom_component[index++] = com_component[++pos];
                    } catch (Exception e) {
                        pos--;
                        index--;
                    }
                }
            } else if (placeHolder <= -1 && placeHolder >= -127) {
                /*
                 * If the placeHolder is -ve and in the range of [-1 to -127], repete next byte (placeHolder + 1) times.
                 */
                repeatVal = component[++pos];
                int val1 = placeHolder;
                val1 *= -1;
                for (int repeater = 0; repeater < val1 + 1; repeater++) {
                    try {
                        uncom_component[index++] = repeatVal;
                    } catch (Exception e) {
                        index--;
                    }
                }
            } else if (placeHolder == -128) {
                /*
                 * If the placeHolder = -128, Do nothing.
                 */
            }
        }// End of for loop.

        // Return the decoded component to the caller.
        return uncom_component;
    }

    /**
     * This method accepts the decoded pixel data. It reades the original DICOM file and copy it to a destination object.
     *
     * @param decoded_PixelData byte[].
     */
    private void createDicomFile(byte[] decoded_PixelData, File source, File destination, String TransferSyntax) {
        DicomInputStream in = null;
        DicomOutputStream dcmOut = null;
        try {
            in = new DicomInputStream(source);
            DicomObject dcmObj = in.readDicomObject();
            DicomObject copy = new BasicDicomObject();
            /*
             * Creating a copy of the original DICOM object.
             */
            dcmObj.copyTo(copy);
            /*
             * Change the TransferSyntax to ExplicitVRLittleEndian.
             * Writing the decoded pixel data to DICOM object.
             */
            copy.putString(Tag.TransferSyntaxUID, VR.UI, TransferSyntax);
            copy.putBytes(Tag.PixelData, VR.OW, decoded_PixelData);
            /*
             * If the pixel data is in the form of Y0B0R0, Y1B1R1, Y2B2R2............YnBnRn, PLANAR CONFIGURATION -- 0
             * If the pixel data is in the form of Y0Y1Y2....Yn, B0B1B2.....Bn, R0R1R2...Rn, PLANAR CONFIGURATION -- 1
             */
            copy.putInt(Tag.PlanarConfiguration, VR.US, planarConfigration);
            /*
             * Changing the SamplesPerPixel tag based on sppOne parameter.
             */
            if(sppOne){
                copy.putInt(Tag.SamplesPerPixel, VR.US, 3);
            }
             /*
             * Removing the private tags from the DICOM image.
             */
            //copy = copy.excludePrivate();
            /*
             * Write the DICOM object to destination file.
             */
            dcmOut = new DicomOutputStream(destination);
            dcmOut.writeDicomFile(copy);

            log.info("Writing the Decoded image in to : " + destination.getAbsolutePath());

        } catch (IOException ie) {
            log.error("Unable to create destination file. Due to : " + ie.getMessage());
        } catch (Exception e) {
            log.error("Exception in creating destination file. Due to : " + e.getMessage());
        } finally {
            try {
                // Closing the streams.
                in.close();
                dcmOut.close();
            } catch (Exception e) {
                log.error("Failed to close the source and destination files. Due to : " + e.getMessage());
            }
        }
    }

    /**
     * This method accepts a directory to decode. Pick up each file and send it to processing.
     *
     * @param file File - Directory to decode.
     */
    private void processDirectory(File file) {
        if (file.isDirectory()) {
            File[] fs = file.listFiles();
            for (int i = 0; i < fs.length; i++) {
                log.info("Reading image for decoding : " + fs[i].getAbsolutePath());
                decodeRLEFile(fs[i], fs[i]);
                processDirectory(fs[i]);
            }
        }
    }
}

