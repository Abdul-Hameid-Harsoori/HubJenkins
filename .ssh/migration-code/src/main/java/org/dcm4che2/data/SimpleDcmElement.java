/*
 * @author Anant jagania
 *
 * Created on 16-Nov-07
 *
 * Technology Solutions Company. - Copyright © 2007
 *
 * Technology Solutions Company. (hereafter, the Company) software
 * [both binary and source (if released)]
 * (hereafter, the Software) is intellectual property owned by the Company
 * and is the copyright of the Company in all countries in the world and
 * ownership remains with the Company.You are not allowed to distribute the
 * binary and source code (if released) to third parties.  You are not allowed
 * to reverse engineer, disassemble or decompile code, or make any modifications
 * to the binary or source code, remove or alter any trademark, logo,
 * copyright or other proprietary notices, legends, symbols, or labels in the Software.
 * You are not allowed to sub-license the Software or any derivative work based on or
 * derived from the Software.
 *
 * YOU ACKNOWLEDGE AND AGREE THAT THE SOFTWARE IS DELIVERED 'AS IS' WITHOUT
 * WARRANTY AND WITHOUT ANY SUPPORT SERVICES(UNLESS AGREED TO OTHERWISE BY THE COMPANY).
 * THE COMPANY MAKES NO WARRANTIES, EITHER EXPRESSED OR IMPLIED, AS TO THE
 * SOFTWARE AND OR ITS DERIVATIVES.
 *
 * IT IS UNDERSTOOD BY YOU THAT THE COMPANY SHALL NOT BE LIABLE FOR ANY LOSS OR DAMAGES
 * THAT MAY ARISE, INCLUDING ANY INDIRECT, SPECIAL, OR CONSEQUENTIAL LOSS OR DAMAGE IN
 * CONNECTION WITH OR ARISING FROM THE PERFORMANCE OR USE OF THE
 * SOFTWARE, INCLUDING FITNESS FOR ANY PARTICULAR PURPOSE.
 *
 * By using or copying this Software, you agree to abide by the copyright laws
 * and all other applicable laws of the United States and the State of Illinois
 * including, but not limited to, export control laws, and the terms of this license.
 * You may be held legally responsible for any copyright infringement that is caused
 * or encouraged by your failure to abide by the terms of this notice.

 */
package org.dcm4che2.data;

/**
 * Added this class just to access
 * (default access specifier)SimpleDicomElement class in other then
 * org.dcm4che2.data package.
 */
@SuppressWarnings("serial")
public class SimpleDcmElement extends SimpleDicomElement {

    public SimpleDcmElement(int tag, VR vr, boolean bigEndian, byte[] value,
                            Object cachedValue) {
        super(tag, vr, bigEndian, value, cachedValue);
    }

}
