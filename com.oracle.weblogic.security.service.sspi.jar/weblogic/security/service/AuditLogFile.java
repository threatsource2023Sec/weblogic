package weblogic.security.service;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.SecurityLogger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class AuditLogFile {
   public static final String AUDIT_NAME = "DefaultAuditRecorder";
   public static final String AUDIT_EXTENSION = ".log";
   private PrintWriter out;
   private File logFile = null;
   private File rootDir = null;
   private final Semaphore auditFileSemaphore = new Semaphore(1, true);
   private int rotationMinutes = 1;
   private Timer timer = null;

   public AuditLogFile(File logDir) throws AuditLogFileException {
      this.rootDir = logDir;
      if (!this.rootDir.exists()) {
         this.rootDir.mkdirs();
      }

      this.logFile = new File(this.rootDir, "DefaultAuditRecorder.log");

      try {
         this.out = new PrintWriter(new FileOutputStream(this.logFile, true), true);
      } catch (IOException var3) {
         throw new AuditLogFileException(SecurityLogger.getCouldNotCreateAuditLogFileExc(this.logFile.getAbsolutePath()), var3);
      }
   }

   public synchronized void setRotationTrigger(int rotationmins) {
      this.rotationMinutes = rotationmins;
      this.initTriggers();
   }

   private void initTriggers() {
      long triggerInterval = (long)this.rotationMinutes * 60000L;

      try {
         this.cancelTimer();
         this.timer = new Timer();
         Calendar localCalender = Calendar.getInstance();
         localCalender.add(12, this.rotationMinutes);
         Date futureDate = localCalender.getTime();
         RotateTimerTask timerTask = new RotateTimerTask();
         this.timer.schedule(timerTask, futureDate, triggerInterval);
      } catch (RuntimeException var6) {
         throw new AssertionError(ApiLogger.getAuditLogTriggerError());
      }
   }

   public void cancelTimer() {
      if (this.timer != null) {
         this.timer.cancel();
      }

   }

   public void addEntry(String auditRecord) {
      this.acquireLock();

      try {
         this.out.println(auditRecord);
      } finally {
         this.releaseLock();
      }

   }

   public void close() {
      this.acquireLock();

      try {
         if (this.out != null) {
            this.out.close();
         }
      } finally {
         this.releaseLock();
      }

   }

   public void write(String s) {
      this.acquireLock();

      try {
         this.out.write(s);
      } finally {
         this.releaseLock();
      }

   }

   public void write(int c) {
      this.acquireLock();

      try {
         this.out.write(c);
      } finally {
         this.releaseLock();
      }

   }

   public void addEntryNoLock(String auditRecord) {
      this.out.println(auditRecord);
   }

   public void writeNoLock(String s) {
      this.out.write(s);
   }

   public void writeNoLock(int c) {
      this.out.write(c);
   }

   public void acquireLock() {
      this.auditFileSemaphore.acquireUninterruptibly();
   }

   public void releaseLock() {
      this.auditFileSemaphore.release();
   }

   private class RotateTimerTask extends TimerTask {
      private RotateTimerTask() {
      }

      public void run() {
         try {
            Calendar calInstance = Calendar.getInstance();
            int month = calInstance.get(2) + 1;
            int day = calInstance.get(5);
            int year = calInstance.get(1);
            int hour = calInstance.get(10);
            int minute = calInstance.get(12);
            String dateStr = (year < 10 ? "0" : "") + year + (month < 10 ? "0" : "") + month + (day < 10 ? "0" : "") + day + (hour < 10 ? "0" : "") + hour + (minute < 10 ? "0" : "") + minute;
            File backFile = new File(AuditLogFile.this.rootDir, "DefaultAuditRecorder." + dateStr + ".log");
            AuditLogFile.this.acquireLock();

            try {
               AuditLogFile.this.out.close();
               AuditLogFile.this.logFile.renameTo(backFile);
               AuditLogFile.this.logFile = new File(AuditLogFile.this.rootDir, "DefaultAuditRecorder.log");
               AuditLogFile.this.out = new PrintWriter(new FileOutputStream(AuditLogFile.this.logFile, true), true);
            } finally {
               AuditLogFile.this.releaseLock();
            }

         } catch (IOException var13) {
            throw new AuditLogFileException(SecurityLogger.getCouldNotCreateAuditLogFileExc(AuditLogFile.this.logFile.getAbsolutePath()), var13);
         }
      }

      // $FF: synthetic method
      RotateTimerTask(Object x1) {
         this();
      }
   }
}
