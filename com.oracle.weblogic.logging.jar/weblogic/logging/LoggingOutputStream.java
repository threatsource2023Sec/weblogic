package weblogic.logging;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.LogRecord;
import weblogic.kernel.KernelLogManager;
import weblogic.utils.PropertyHelper;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class LoggingOutputStream extends OutputStream {
   private final OverloadActionRunnable OVERLOAD_TASK;
   static final String STDOUT = "Stdout";
   static final String STDERR = "StdErr";
   private static final int MAX_WM_QUEUE_DEPTH = 5;
   private static final boolean DEBUG = false;
   private static final int DEFAULT_BUFFER_SIZE = 10240;
   private static final int BUFFER_SIZE_LIMIT = PropertyHelper.getInteger("weblogic.log.LoggingOutputStreamBufferSize", 10240);
   private ByteArrayOutputStream buffer;
   private String logStream;
   private WLLevel level;
   private boolean async;
   private WorkManager workManager;
   private ConcurrentLinkedQueue logRecordQueue;
   private ThreadLocal reentrant;

   public LoggingOutputStream(String streamName, WLLevel msgLevel, boolean async) {
      this.OVERLOAD_TASK = new OverloadActionRunnable();
      this.async = false;
      this.workManager = null;
      this.logRecordQueue = null;
      this.reentrant = new ThreadLocal();
      this.buffer = new ByteArrayOutputStream();
      this.logStream = streamName;
      this.level = msgLevel;
      this.async = async;
      if (async) {
         this.workManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.logging.LoggingOutputStream", -1, 1);
         this.logRecordQueue = new ConcurrentLinkedQueue();
      }

   }

   public LoggingOutputStream(String streamName, WLLevel msgLevel) {
      this(streamName, msgLevel, false);
   }

   public void write(int b) {
      synchronized(this.buffer) {
         this.buffer.write(b);
         if (this.buffer.size() > BUFFER_SIZE_LIMIT) {
            this.flush();
         }

      }
   }

   int getBufferSize() {
      return this.buffer.size();
   }

   int getBufferSizeLimit() {
      return BUFFER_SIZE_LIMIT;
   }

   String getBufferString() {
      return this.buffer.toString();
   }

   public void flush() {
      String msg = this.buffer.toString().trim();
      if (msg.length() != 0) {
         WLLogRecord rec = new WLLogRecord(this.level, msg);
         rec.setLoggerName(this.logStream);
         if (this.async) {
            this.logRecordQueue.add(rec);
            if (this.reentrant.get() == null) {
               try {
                  this.reentrant.set(this);
                  this.flushQueue();
               } finally {
                  this.reentrant.set((Object)null);
               }
            }
         } else {
            KernelLogManager.getLogger().log(rec);
         }

         this.buffer.reset();
      }
   }

   private void flushQueue() {
      if (this.workManager.getQueueDepth() < 5) {
         this.workManager.schedule(new LoggingWorkAdapter());
      }

   }

   private final class LoggingWorkAdapter extends WorkAdapter {
      private LoggingWorkAdapter() {
      }

      public Runnable cancel(String reason) {
         return LoggingOutputStream.this.OVERLOAD_TASK;
      }

      public Runnable overloadAction(String reason) {
         return LoggingOutputStream.this.OVERLOAD_TASK;
      }

      public void run() {
         LogRecord rec = null;

         while((rec = (LogRecord)LoggingOutputStream.this.logRecordQueue.poll()) != null) {
            KernelLogManager.getLogger().log(rec);
         }

      }

      // $FF: synthetic method
      LoggingWorkAdapter(Object x1) {
         this();
      }
   }

   private class OverloadActionRunnable implements Runnable {
      private OverloadActionRunnable() {
      }

      public void run() {
         while(LoggingOutputStream.this.logRecordQueue.poll() != null) {
         }

      }

      // $FF: synthetic method
      OverloadActionRunnable(Object x1) {
         this();
      }
   }
}
