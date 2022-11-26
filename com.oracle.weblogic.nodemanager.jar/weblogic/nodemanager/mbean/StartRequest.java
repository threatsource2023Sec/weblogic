package weblogic.nodemanager.mbean;

import java.io.IOException;
import java.util.Properties;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.nodemanager.NMConnectException;
import weblogic.nodemanager.NodeManagerLogger;
import weblogic.nodemanager.client.NMClient;
import weblogic.utils.StackTraceUtilsClient;

class StartRequest implements Runnable, NodeManagerTask {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugNodeManagerClient");
   private NMClient nmc;
   private final Properties props;
   private final Properties runtimeProperties;
   private Exception error;
   private final long beginTime;
   private long endTime;
   private String status;
   private boolean finished;
   private final String serverName;

   StartRequest(String serverName, NMClient nmc, Properties props, Properties runtimeProperties) {
      this.nmc = nmc;
      this.props = props;
      this.runtimeProperties = runtimeProperties;
      this.serverName = serverName;
      this.status = "TASK IN PROGRESS";
      this.beginTime = System.currentTimeMillis();
   }

   public void run() {
      Exception error = null;

      String status;
      try {
         this.start();
         status = "TASK COMPLETED";
      } catch (IOException var7) {
         error = var7;
         status = "FAILED";
      }

      synchronized(this) {
         if (error != null) {
            if (error instanceof NMConnectException) {
               NMConnectException nmce = (NMConnectException)error;
               NodeManagerLogger.logNMNotRunning(nmce.getHost(), Integer.toString(nmce.getPort()));
            } else if (debugLogger.isDebugEnabled()) {
               NodeManagerLogger.logServerStartFailure(this.serverName, StackTraceUtilsClient.throwable2StackTrace(error));
            } else {
               NodeManagerLogger.logServerStartFailure(this.serverName, StackTraceUtilsClient.throwableToString(error));
            }
         }

         this.status = status;
         this.error = error;
         this.endTime = System.currentTimeMillis();
         this.finished = true;
         this.nmc = null;
         this.notifyAll();
      }
   }

   private void start() throws IOException {
      try {
         if (this.runtimeProperties != null && !this.runtimeProperties.isEmpty()) {
            this.nmc.setRuntimeProperties(this.runtimeProperties);
         }

         this.nmc.start(this.props);
      } finally {
         this.nmc.done();
      }

   }

   public synchronized boolean isFinished() {
      return this.finished;
   }

   public synchronized void waitForFinish(long timeout) throws InterruptedException {
      long start = System.currentTimeMillis();

      for(long elapsed = 0L; elapsed < timeout && !this.finished; elapsed = System.currentTimeMillis() - start) {
         this.wait(timeout - elapsed);
      }

   }

   public synchronized void waitForFinish() throws InterruptedException {
      while(!this.finished) {
         this.wait();
      }

   }

   public void cancel() {
      throw new UnsupportedOperationException();
   }

   public synchronized long getBeginTime() {
      return this.beginTime;
   }

   public synchronized long getEndTime() {
      return this.endTime;
   }

   public synchronized String getStatus() {
      return this.status;
   }

   public synchronized Exception getError() {
      return this.error;
   }

   public synchronized void cleanup() {
      if (!this.finished) {
         throw new IllegalStateException("Task is still executing");
      }
   }
}
