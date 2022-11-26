package com.bea.logging;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import weblogic.i18n.logging.CatalogMessage;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.utils.StringUtils;

public final class LogFileRotator {
   private static GlobalAccountRunner globalAccountRunner;
   public static final int NUM_OF_DIGITS_IN_SUFFIX = 5;
   private static final int FAILURE_EXPANSION_AMOUNT = 1024000;
   private static final int RETRY_COUNT = 5;
   private static final boolean DEBUG = false;
   static final String LOG_WILL_BE_ROTATED_ID = "320400";
   static final String LOG_NEEDS_ROTATION_ID = "320401";
   private static final String LOCALIZER_CLASS = "com.bea.logging.LoggingServiceLogLocalizer";
   private static final Calendar INITIAL_TIME = Calendar.getInstance();
   private static final String NONE = "none";
   private long nextRotationSize;
   private boolean bootStrapDone;
   private File currentLogFile;
   private String baseLogFileName;
   private boolean rotateLogOnStartup;
   private AtomicLong bytesWritten;
   private boolean formattedLogFileName;
   private SortedSet previousLogFiles;
   private String rotationType;
   private int fileMinSize;
   private boolean numberOfFilesLimited;
   private String rotationTime;
   private String logFileRotationDir;
   private int fileCount;
   private String partitionId;

   public static Logger getLogRotationLogger() {
      return LogFileRotator.LogRotationInitializer.LOG_ROTATION_LOGGER;
   }

   public static void setGlobalAccountRunner(GlobalAccountRunner gar) {
      globalAccountRunner = gar;
   }

   GlobalAccountRunner getGlobalAccountRunner() {
      return globalAccountRunner;
   }

   LogFileRotator() {
      this.nextRotationSize = LogFileConfig.FILESIZE_LIMIT;
      this.bootStrapDone = false;
      this.currentLogFile = null;
      this.baseLogFileName = null;
      this.rotateLogOnStartup = true;
      this.bytesWritten = new AtomicLong(0L);
      this.formattedLogFileName = false;
      this.previousLogFiles = null;
      this.rotationType = "bySize";
      this.fileMinSize = 500;
      this.numberOfFilesLimited = false;
      this.rotationTime = "00:00";
      this.logFileRotationDir = null;
      this.fileCount = 7;
   }

   private void initialize() {
      this.resetRotationThresholds();
      this.formattedLogFileName = this.isFormattedLogFileName();
      this.initCurrentLogFile();
      this.bytesWritten.set(this.currentLogFile.length());
      if (!this.bootStrapDone) {
         File parent = this.currentLogFile.getParentFile();
         if (parent != null) {
            parent.mkdirs();
         }

         if (this.rotateLogOnStartup) {
            try {
               this.rotate();
            } catch (Exception var3) {
               getLogRotationLogger().log(Level.SEVERE, "Got an exception while rotating log file.", var3);
            }
         }

         this.bootStrapDone = true;
      }

   }

   void initCurrentLogFile() {
      if (this.formattedLogFileName) {
         this.currentLogFile = new File(StringUtils.replaceGlobal(this.getBaseLogFileName(), "%", ""));
      } else {
         this.currentLogFile = new File(this.getBaseLogFileName());
      }

      this.currentLogFile = this.currentLogFile.getAbsoluteFile();
   }

   File getCurrentLogFile() {
      return this.currentLogFile;
   }

   String getLogRotationDir() {
      return this.logFileRotationDir;
   }

   boolean logNeedsRotation() {
      if (this.bytesWritten.get() >= this.nextRotationSize) {
         this.bytesWritten.set(this.currentLogFile.length());
         return this.bytesWritten.get() >= this.nextRotationSize;
      } else {
         return false;
      }
   }

   void incrementNextRotationSize() {
      this.nextRotationSize += 1024000L;
   }

   void incrementBytesWritten(long value) {
      this.bytesWritten.addAndGet(value);
   }

   void resetRotationThresholds() {
      this.resetRotationThresholds(false);
   }

   private void resetRotationThresholds(boolean reinitialize) {
      this.nextRotationSize = LogFileConfig.FILESIZE_LIMIT;
      if (!reinitialize) {
         this.bytesWritten.set(0L);
      }

      if (this.rotationType.equalsIgnoreCase("bySize") || this.rotationType.equalsIgnoreCase("bySizeOrTime")) {
         this.nextRotationSize = (long)(this.fileMinSize * 1024);
      }

   }

   long getLogRotationBeginTime() {
      String pattern = this.rotationTime;
      Calendar sched = Calendar.getInstance();
      Calendar now = Calendar.getInstance();
      if (pattern != null && pattern.length() != 0) {
         SimpleDateFormat dateFormatter = new SimpleDateFormat("k:mm");

         try {
            sched.setTime(dateFormatter.parse(pattern));
            sched.set(1, INITIAL_TIME.get(1));
            sched.set(2, INITIAL_TIME.get(2));
            sched.set(5, INITIAL_TIME.get(5));
         } catch (ParseException var6) {
            return now.getTimeInMillis();
         }

         return sched.getTimeInMillis();
      } else {
         return now.getTimeInMillis();
      }
   }

   private void rotateLogFile(File currentFile, File newFile) throws IOException {
      if (!currentFile.equals(newFile)) {
         if (newFile.exists() && !newFile.delete()) {
            throw new LogRotationException("Error deleting file " + newFile.getCanonicalPath());
         } else if (!currentFile.renameTo(newFile)) {
            throw new LogRotationException("Error rotating log file " + currentFile.getCanonicalPath());
         }
      }
   }

   final void executeGlobalAccountRunnerTask(Runnable task) throws Exception {
      if (globalAccountRunner != null && this.partitionId != null && this.partitionId.equals("0")) {
         globalAccountRunner.accountAsGlobal(task);
      } else {
         task.run();
      }

   }

   final void rotate() throws Exception {
      this.executeGlobalAccountRunnerTask(new Runnable() {
         public void run() {
            try {
               LogFileRotator.this.rotateInternal();
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }
      });
   }

   void rotateInternal() throws IOException {
      if (this.bytesWritten.get() != 0L) {
         File currentFile = this.currentLogFile;
         if (currentFile != null) {
            this.previousLogFiles = this.getRotatedLogFiles();
            if (currentFile.exists()) {
               File archiveFile;
               if (this.formattedLogFileName) {
                  archiveFile = this.getFormattedArchiveFile(currentFile);
               } else {
                  archiveFile = this.getUnformattedArchiveFile(currentFile);
               }

               this.logFileWillbeRotated(currentFile.getCanonicalPath());
               this.rotateLogFile(currentFile, archiveFile);
               this.logFileRotated(currentFile.getCanonicalPath(), archiveFile.getCanonicalPath());
               archiveFile.setLastModified(System.currentTimeMillis());
               this.resetRotationThresholds();
               if (this.numberOfFilesLimited) {
                  this.previousLogFiles.add(new FileInfo(archiveFile));
                  this.deleteOldFiles();
               }

            }
         }
      }
   }

   private final File getFormattedArchiveFile(File currentFile) {
      File newFile = new File(this.getFormattedFilename(this.getBaseLogFileName()));
      File firstFile = new File(this.getLogFileRotationDir(currentFile), newFile.getName());
      int index = 1;
      SortedSet sortList = this.previousLogFiles;
      if (!sortList.isEmpty()) {
         FileInfo fi = (FileInfo)sortList.last();
         String latestFile = fi.file.getName();

         try {
            if (latestFile.length() > newFile.getName().length() && latestFile.indexOf(newFile.getName()) > -1) {
               index = Integer.parseInt(latestFile.substring(newFile.getName().length())) + 1;
            } else if (!firstFile.exists()) {
               return firstFile;
            }
         } catch (Exception var9) {
            index = 1;
         }

         do {
            firstFile = new File(this.getLogFileRotationDir(currentFile), newFile.getName() + StringUtils.padNumberWidth((long)(index++), 5));
         } while(firstFile.exists());
      }

      return firstFile;
   }

   private File getLogFileRotationDir(File logFile) {
      File logDir = null;
      File currentDir = new File(".");
      String logDirPath = this.logFileRotationDir;
      if (logDirPath != null && logDirPath.trim().length() > 0) {
         logDir = new File(logDirPath);

         try {
            if (logDir.exists() && logDir.canWrite() || logDir.mkdirs()) {
               return logDir;
            }

            System.err.println("Log rotation dir " + logDirPath + " does not exists or cannot be created");
         } catch (SecurityException var7) {
            System.err.println("No write permissions in dir " + logDir.getAbsolutePath());
         }
      }

      logDir = logFile.getParentFile();

      try {
         if (logDir != null && (logDir.exists() || logDir.mkdirs())) {
            return logDir;
         }

         System.err.println("Log rotation dir does not exists or cannot be created, using the current dir " + currentDir.getAbsolutePath() + " as the default");
      } catch (SecurityException var6) {
         System.err.println("No write permissions in dir for log rotation, using the current dir " + currentDir.getAbsolutePath() + " as the default");
      }

      return currentDir;
   }

   private final File getUnformattedArchiveFile(File logFile) {
      File logDir = this.getLogFileRotationDir(logFile);
      SortedSet sortList = this.previousLogFiles;
      int nextIdx = 1;
      if (!sortList.isEmpty()) {
         FileInfo fi = (FileInfo)sortList.last();
         String latestFile = fi.file.getName();
         int current = Integer.parseInt(latestFile.substring(latestFile.length() - 5));
         if (current < 99999) {
            nextIdx = current + 1;
         }
      }

      File rotatedFile = null;

      for(int i = 0; i < 5; ++i) {
         String nextSuffix = StringUtils.padNumberWidth((long)nextIdx, 5);
         rotatedFile = new File(logDir, logFile.getName() + nextSuffix);
         if (!rotatedFile.exists()) {
            break;
         }

         ++nextIdx;
         if (nextIdx > 99999) {
            nextIdx = 1;
         }
      }

      return rotatedFile;
   }

   private void deleteOldFiles() {
      FileInfo fi;
      for(SortedSet sortList = this.previousLogFiles; sortList.size() > this.fileCount; sortList.remove(fi)) {
         fi = (FileInfo)sortList.first();
         File f = fi.file;
         boolean r = f.delete();
         if (!r && f.exists()) {
            try {
               Thread.currentThread();
               Thread.sleep(1000L);
            } catch (InterruptedException var6) {
            }

            r = f.delete();
         }
      }

   }

   public SortedSet getRotatedLogFiles() {
      File logDir = this.getLogFileRotationDir(this.currentLogFile);
      TreeSet sortList = new TreeSet();
      File[] files = logDir.listFiles(new LogfileFilter(new File(this.getBaseLogFileName())));
      if (files != null) {
         for(int i = 0; i < files.length; ++i) {
            sortList.add(new FileInfo(files[i]));
         }
      }

      if (this.formattedLogFileName && sortList.size() > 0 && sortList.contains(new FileInfo(this.currentLogFile))) {
         sortList.remove(sortList.last());
      }

      return sortList;
   }

   private boolean isFormattedLogFileName() {
      return this.getBaseLogFileName().indexOf(37) > -1;
   }

   private String getFormattedFilename(String fileName) {
      SimpleDateFormat simpleFormat = new SimpleDateFormat("'" + fileName.replace('%', '\'') + "'");
      String s = simpleFormat.format(new Date()).toString();
      if (s.indexOf("'") != -1) {
         s = StringUtils.replaceGlobal(s, "'", "");
      }

      return s;
   }

   String getBaseLogFileName() {
      return this.baseLogFileName;
   }

   private void logFileWillbeRotated(String arg0) {
      Object[] args = new Object[]{arg0};

      try {
         LogRecord lr = LoggingService.getInstance().getBaseLogRecordFactory().createBaseLogRecord(new CatalogMessage("320400", 32, args, "com.bea.logging.LoggingServiceLogLocalizer", this.getClass().getClassLoader()));
         getLogRotationLogger().log(lr);
      } catch (Exception var4) {
         var4.printStackTrace(System.out);
      }

   }

   private void logFileRotated(String arg0, String arg1) {
      Object[] args = new Object[]{arg0, arg1};

      try {
         LogRecord lr = LoggingService.getInstance().getBaseLogRecordFactory().createBaseLogRecord(new CatalogMessage("320401", 32, args, "com.bea.logging.LoggingServiceLogLocalizer", this.getClass().getClassLoader()));
         getLogRotationLogger().log(lr);
      } catch (Exception var5) {
         var5.printStackTrace(System.out);
      }

   }

   void initialize(LogFileConfigBean logFileConfig) {
      this.initialize(logFileConfig, false);
   }

   synchronized void initialize(LogFileConfigBean logFileConfig, boolean reinitialize) {
      if (!reinitialize) {
         String newBaseFileName = logFileConfig.getBaseLogFileName();
         if (!newBaseFileName.equals(this.baseLogFileName)) {
            this.baseLogFileName = newBaseFileName;
            this.bootStrapDone = false;
         }

         this.logFileRotationDir = logFileConfig.getLogFileRotationDir();
      }

      this.rotationType = logFileConfig.getRotationType();
      this.fileCount = logFileConfig.getRotatedFileCount();
      this.fileMinSize = logFileConfig.getRotationSize();
      this.numberOfFilesLimited = logFileConfig.isNumberOfFilesLimited();
      this.rotateLogOnStartup = logFileConfig.isRotateLogOnStartupEnabled();
      this.rotationTime = logFileConfig.getRotationTime();

      try {
         ComponentInvocationContextManager compCtxMgr = ComponentInvocationContextManager.getInstance();
         ComponentInvocationContext compCtx = compCtxMgr.getCurrentComponentInvocationContext();
         if (compCtx != null) {
            this.partitionId = compCtx.getPartitionId();
         }
      } catch (Throwable var5) {
      }

      if (!reinitialize) {
         this.initialize();
      } else {
         this.resetRotationThresholds(reinitialize);
      }

   }

   public static class FileInfo implements Comparable {
      private File file;
      private long timestamp;

      public FileInfo(File f) {
         this.file = f;
         this.timestamp = this.file.lastModified();
      }

      public File getFile() {
         return this.file;
      }

      public long getTimestamp() {
         return this.timestamp;
      }

      public int hashCode() {
         return this.file.hashCode();
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o instanceof FileInfo) {
            FileInfo other = (FileInfo)o;
            return this.file.equals(other.file);
         } else {
            return false;
         }
      }

      public int compareTo(Object obj) {
         FileInfo fi2 = (FileInfo)obj;
         long l = this.getTimestamp() - fi2.getTimestamp();
         if (l == 0L) {
            return this.file.compareTo(fi2.file);
         } else {
            return l > 0L ? 1 : -1;
         }
      }

      public String toString() {
         return this.file.toString();
      }
   }

   private static final class LogRotationInitializer {
      private static final Logger LOG_ROTATION_LOGGER = LoggingService.getInstance().getAnonymousLogger();

      private LogRotationInitializer() {
         LOG_ROTATION_LOGGER.addHandler(new StdoutHandler());
         LOG_ROTATION_LOGGER.setUseParentHandlers(false);
         LOG_ROTATION_LOGGER.setLevel(Level.ALL);
      }
   }

   public interface GlobalAccountRunner {
      void accountAsGlobal(Runnable var1) throws IOException;
   }
}
