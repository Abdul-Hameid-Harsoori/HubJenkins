package com.secant.migration;

import com.secant.migration.beans.RunMeOn;
import com.secant.migration.threads.MigratorThread;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MigrationExecutor {

    private ThreadPoolTaskExecutor migrationTPExecutor;

    public MigrationExecutor(ThreadPoolTaskExecutor migrationTPExecutor) {
	this.migrationTPExecutor = migrationTPExecutor;
    }

    public void executeMThreads(int numThreads, ApplicationContext ctx,String migrationLevel,RunMeOn runMeOn) {
	for (int i = 1; i < numThreads+1; i++) {
	    MigratorThread migrator = (MigratorThread) ctx.getBean("migratorThread");
            migrator.setThreadNumber(i);
            migrator.setContext(ctx);
            migrator.setInstanceDetails(migrationLevel);
            migrator.setRunMeOn(runMeOn);
            migrationTPExecutor.execute(migrator);
	}
    }
}
