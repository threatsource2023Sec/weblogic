package weblogic.diagnostics.image;

import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.management.ManagementException;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;

class ImageWork implements Runnable {
   private TaskRuntimeMBeanImpl imageWatcher;
   private String destination;
   private int lockoutMinutes;
   ImageRequester imageRequester;

   ImageWork(String dest, int minutes, ImageRequester requester) {
      this.destination = dest;
      this.lockoutMinutes = minutes;
      this.imageRequester = requester;

      try {
         this.imageWatcher = new TaskRuntimeMBeanImpl();
      } catch (ManagementException var5) {
         UnexpectedExceptionHandler.handle("Problem registering TaskRuntimeMBean.", var5);
      }

   }

   WLDFImageCreationTaskRuntimeMBean getImageWatcher() {
      return this.imageWatcher;
   }

   public void run() {
      long creationTime = System.currentTimeMillis();
      ImageBuilder imageBuilder = new ImageBuilder(this.destination, creationTime, this.imageRequester);
      this.imageWatcher.setImageBuilder(imageBuilder);
      this.imageWatcher.setBeginTime(creationTime);
      imageBuilder.buildImage(this.imageWatcher);
   }
}
