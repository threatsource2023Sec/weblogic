package com.bea.logging;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.Timer;
import java.util.TimerTask;

public class RotatingFileOutputStream extends OutputStream {
   private static final boolean DEBUG = false;
   OutputStream delegate;
   private LogFileRotator rotator;
   private boolean streamOpened;
   private String rotationType;
   private int timeSpan;
   private long timeSpanFactor;
   private static final int DEFAULT_BUFFER_SIZE = 8192;
   private Timer rotationTimer;
   private TimerTask timerTask;
   private Object rotationMonitor;
   private long lastTimerRunTime;
   private int bufferSizeKB;
   private boolean rotateImmediately;
   private boolean isFileRotated;

   public RotatingFileOutputStream() {
      this.delegate = null;
      this.rotator = null;
      this.streamOpened = false;
      this.rotationType = "bySize";
      this.timeSpan = 24;
      this.timeSpanFactor = 3600000L;
      this.rotationTimer = null;
      this.rotationMonitor = this;
      this.lastTimerRunTime = -1L;
      this.rotateImmediately = true;
      this.isFileRotated = false;
      this.rotator = new LogFileRotator();
   }

   public RotatingFileOutputStream(LogFileConfigBean logFileConfig) throws IOException {
      this();
      this.initialize(logFileConfig);
   }

   public void setRotationMonitor(Object timerMonitor) {
      this.rotationMonitor = timerMonitor;
   }

   protected int getBufferSize() {
      return 8192;
   }

   public long getLastTimerRunTime() {
      return this.lastTimerRunTime;
   }

   protected void startTimer(long startTime, long delay) {
      this.timerTask = this.createTimerTask();
      this.getRotationTimer().scheduleAtFixedRate(this.timerTask, new Date(startTime), delay);
   }

   private TimerTask createTimerTask() {
      return new TimerTask() {
         public void run() {
            try {
               synchronized(RotatingFileOutputStream.this.rotationMonitor) {
                  RotatingFileOutputStream.this.lastTimerRunTime = System.currentTimeMillis();
                  RotatingFileOutputStream.this.forceRotation();
               }
            } catch (IOException var4) {
            }

         }
      };
   }

   private void restartTimer() {
      synchronized(this.rotationMonitor) {
         if (this.timerTask != null) {
            boolean var2 = this.timerTask.cancel();
         }

         if (!this.rotationType.equals("bySize") && !this.rotationType.equals("none")) {
            long startTime = this.rotator.getLogRotationBeginTime();
            long delay = (long)this.timeSpan * this.timeSpanFactor;

            for(long now = Calendar.getInstance().getTimeInMillis(); startTime <= now; startTime += delay) {
            }

            this.startTimer(startTime, delay);
         }
      }
   }

   private Timer getRotationTimer() {
      synchronized(this.rotationMonitor) {
         if (this.rotationTimer == null) {
            this.rotationTimer = new Timer();
         }

         return this.rotationTimer;
      }
   }

   public void write(int b) throws IOException {
      synchronized(this.rotationMonitor) {
         if (this.streamOpened && this.delegate != null) {
            this.delegate.write(b);
            this.ensureRotated(1, this.rotateImmediately);
         }

      }
   }

   public void write(byte[] b) throws IOException {
      this.write(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      synchronized(this.rotationMonitor) {
         if (this.streamOpened && this.delegate != null) {
            this.delegate.write(b, off, len);
            this.ensureRotated(len, this.rotateImmediately);
         }

      }
   }

   Object getRotationMonitor() {
      return this.rotationMonitor;
   }

   public void flush() throws IOException {
      synchronized(this.rotationMonitor) {
         if (this.streamOpened && this.delegate != null) {
            this.delegate.flush();
         }

      }
   }

   public void close() throws IOException {
      try {
         this.rotator.executeGlobalAccountRunnerTask(new Runnable() {
            public void run() {
               try {
                  synchronized(RotatingFileOutputStream.this.rotationMonitor) {
                     if (RotatingFileOutputStream.this.delegate != null) {
                        RotatingFileOutputStream.this.delegate.close();
                        RotatingFileOutputStream.this.delegate = null;
                        RotatingFileOutputStream.this.streamOpened = false;
                     }

                  }
               } catch (IOException var4) {
                  throw new RuntimeException(var4);
               }
            }
         });
      } catch (Exception var2) {
         throw new IOException(var2);
      }
   }

   public boolean isStreamOpened() {
      return this.streamOpened;
   }

   public void open() throws IOException {
      try {
         this.rotator.executeGlobalAccountRunnerTask(new Runnable() {
            public void run() {
               synchronized(RotatingFileOutputStream.this.rotationMonitor) {
                  try {
                     RotatingFileOutputStream.this.close();
                     OutputStream os = new FileOutputStream(RotatingFileOutputStream.this.rotator.getCurrentLogFile(), true);
                     if (RotatingFileOutputStream.this.bufferSizeKB <= 0) {
                        RotatingFileOutputStream.this.delegate = os;
                     } else {
                        RotatingFileOutputStream.this.delegate = new BufferedOutputStream(os, RotatingFileOutputStream.this.bufferSizeKB * 1024);
                     }

                     RotatingFileOutputStream.this.streamOpened = true;
                  } catch (IOException var4) {
                     throw new RuntimeException(var4);
                  }

               }
            }
         });
      } catch (Exception var2) {
         throw new IOException(var2);
      }
   }

   void ensureRotated(int bytesWritten, boolean flag) throws IOException {
      synchronized(this.rotationMonitor) {
         this.rotator.incrementBytesWritten((long)bytesWritten);
         if (flag && this.rotator.logNeedsRotation()) {
            this.forceRotation();
         }

      }
   }

   public void ensureRotation() throws IOException {
      this.ensureRotated(0, true);
   }

   public String getCurrentLogFile() {
      return this.rotator.getCurrentLogFile().getAbsolutePath();
   }

   public void forceRotation() throws IOException {
      try {
         this.rotator.executeGlobalAccountRunnerTask(new Runnable() {
            public void run() {
               try {
                  synchronized(RotatingFileOutputStream.this.rotationMonitor) {
                     if (RotatingFileOutputStream.this.delegate != null) {
                        try {
                           RotatingFileOutputStream.this.close();
                           RotatingFileOutputStream.this.setFileRotated(true);
                           RotatingFileOutputStream.this.rotator.rotateInternal();
                        } catch (Exception var9) {
                           RotatingFileOutputStream.this.rotator.incrementNextRotationSize();
                        } finally {
                           RotatingFileOutputStream.this.open();
                        }

                     }
                  }
               } catch (IOException var12) {
                  throw new RuntimeException(var12);
               }
            }
         });
      } catch (Exception var2) {
         throw new IOException(var2);
      }
   }

   void initialize(LogFileConfigBean logFileConfig) throws IOException {
      this.initialize(logFileConfig, false, false);
   }

   void initialize(LogFileConfigBean logFileConfig, boolean reinitialize, boolean reopenStream) throws IOException {
      synchronized(this.rotationMonitor) {
         this.rotator.initialize(logFileConfig, reinitialize);
         this.bufferSizeKB = logFileConfig.getBufferSizeKB();
         if (this.bufferSizeKB < 0) {
            this.bufferSizeKB = 0;
         }

         if (!reinitialize || reopenStream) {
            this.open();
         }

         this.rotationType = logFileConfig.getRotationType();
         this.timeSpan = logFileConfig.getRotationTimeSpan();
         this.timeSpanFactor = logFileConfig.getRotationTimeSpanFactor();
         this.restartTimer();
      }
   }

   public SortedSet getRotatedLogFiles() {
      return this.rotator.getRotatedLogFiles();
   }

   boolean isRotateImmediately() {
      return this.rotateImmediately;
   }

   public void setRotateImmediately(boolean rotateImmediately) {
      this.rotateImmediately = rotateImmediately;
   }

   public String getLogRotationDir() {
      return this.rotator.getLogRotationDir();
   }

   public String getBaseLogFileName() {
      return this.rotator.getBaseLogFileName();
   }

   public boolean isFileRotated() {
      return this.isFileRotated;
   }

   public void setFileRotated(boolean isFileRotated) {
      this.isFileRotated = isFileRotated;
   }
}
