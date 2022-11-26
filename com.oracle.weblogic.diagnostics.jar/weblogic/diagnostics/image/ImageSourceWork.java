package weblogic.diagnostics.image;

import java.io.OutputStream;

class ImageSourceWork implements Runnable {
   private ImageSource imageSource;
   private boolean finished;
   private long startTime;
   private long imageSourceElapsedTime;
   private OutputStream imageOutputStream;
   private Throwable failureThrowable;
   private boolean timedOut;
   private String sourceName;
   private String partitionName = null;

   ImageSourceWork(String partitionName, String sourceName, ImageSource source, OutputStream out) {
      this.sourceName = sourceName;
      this.imageSource = source;
      this.imageOutputStream = out;
      this.partitionName = partitionName;
   }

   void timeoutWork() {
      this.timedOut = true;
      this.imageSource.timeoutImageCreation();
   }

   OutputStream getOutputStream() {
      return this.imageOutputStream;
   }

   long getImageSourceElapsedTime() {
      return this.imageSourceElapsedTime;
   }

   boolean isFinished() {
      return this.finished;
   }

   Throwable getFailureThrowable() {
      return this.failureThrowable;
   }

   String getImageSourceName() {
      return this.sourceName;
   }

   public void run() {
      boolean var12 = false;

      label98: {
         try {
            var12 = true;
            this.startTime = System.currentTimeMillis();
            if (this.partitionName == null) {
               this.imageSource.createDiagnosticImage(this.imageOutputStream);
               var12 = false;
            } else if (this.imageSource instanceof PartitionAwareImageSource) {
               PartitionAwareImageSource partSource = (PartitionAwareImageSource)this.imageSource;
               partSource.createDiagnosticImage(this.partitionName, this.imageOutputStream);
               var12 = false;
            } else {
               var12 = false;
            }
            break label98;
         } catch (Throwable var16) {
            this.failureThrowable = var16;
            var12 = false;
         } finally {
            if (var12) {
               this.imageSourceElapsedTime = System.currentTimeMillis() - this.startTime;
               this.finished = true;
               synchronized(this) {
                  this.notifyAll();
               }
            }
         }

         this.imageSourceElapsedTime = System.currentTimeMillis() - this.startTime;
         this.finished = true;
         synchronized(this) {
            this.notifyAll();
            return;
         }
      }

      this.imageSourceElapsedTime = System.currentTimeMillis() - this.startTime;
      this.finished = true;
      synchronized(this) {
         this.notifyAll();
      }

   }
}
