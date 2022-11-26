package weblogic.work;

public class WorkManagerDumper implements Runnable {
   private static WorkManagerDumper THE_ONE = null;
   private boolean enabled;
   private Thread thread;
   private String debugData;
   private boolean notified;

   public static WorkManagerDumper getInstance() {
      if (THE_ONE == null) {
         initSingleton();
      }

      return THE_ONE;
   }

   private static synchronized void initSingleton() {
      if (THE_ONE == null) {
         THE_ONE = new WorkManagerDumper();
      }

   }

   private WorkManagerDumper() {
      this.setEnabled(Boolean.getBoolean("weblogic.work.statedump"));
   }

   public synchronized void setEnabled(boolean enabled) {
      this.enabled = enabled;
      if (enabled) {
         if (this.thread == null) {
            this.thread = new Thread(this);
            this.thread.start();
            WorkManagerLogger.logWorkManagerDumperStarted();
         }
      } else if (this.thread != null) {
         this.thread = null;
         this.notifyAll();
      }

   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void run() {
      Thread.currentThread().setName("[WM statedump] started.");

      while(this.isEnabled()) {
         try {
            if (this.notified) {
               Thread.currentThread().setName("[WM statedump] " + this.debugData);
               this.notified = false;
            }

            synchronized(this) {
               if (this.isEnabled()) {
                  this.wait();
               }
            }
         } catch (Throwable var4) {
            var4.printStackTrace();
         }
      }

   }

   public synchronized void setDebugData(String text) {
      if (this.isEnabled()) {
         this.debugData = text;
         this.notified = true;
         this.notifyAll();
      }

   }
}
