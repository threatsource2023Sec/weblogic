package weblogic.diagnostics.image;

import java.io.PrintWriter;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;

public class TaskRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFImageCreationTaskRuntimeMBean {
   private int imageStatus = 0;
   private long imageCaptureStartTime;
   private long imageCaptureEndTime;
   private boolean systemTask;
   private Exception imageError;
   private static int count = 0;
   private ImageBuilder imageBuilder;

   public TaskRuntimeMBeanImpl() throws ManagementException {
      super("DiagnosticImageCaptureTaskRuntime_" + getCount());
   }

   synchronized void setError(Exception error) {
      this.imageError = error;
      this.imageStatus = 3;
   }

   synchronized void setBeginTime(long time) {
      this.imageCaptureStartTime = time;
      this.imageStatus = 1;
   }

   synchronized void setEndTime(long time) {
      this.imageCaptureEndTime = time;
      this.imageStatus = 2;
   }

   void setImageBuilder(ImageBuilder builder) {
      this.imageBuilder = builder;
   }

   synchronized int getStatusInternal() {
      return this.imageStatus;
   }

   public String getDescription() {
      String description = this.imageBuilder != null ? this.imageBuilder.getArchiveName() : null;
      return description == null ? "Diagnostic image request pending execution." : description;
   }

   public String getImageFileName() {
      return this.imageBuilder != null ? this.imageBuilder.getArchiveFileName() : null;
   }

   public synchronized String getStatus() {
      return ImageConstants.CAPTURE_STATES[this.imageStatus];
   }

   public String getProgress() {
      if (this.isRunning()) {
         return "processing";
      } else if (this.imageStatus == 0) {
         return "pending";
      } else {
         return this.imageStatus == 2 ? "success" : "failed";
      }
   }

   public synchronized boolean isRunning() {
      return this.imageStatus == 1;
   }

   public synchronized long getBeginTime() {
      return this.imageCaptureStartTime;
   }

   public synchronized long getEndTime() {
      return this.imageCaptureEndTime;
   }

   public TaskRuntimeMBean[] getSubTasks() {
      return null;
   }

   public TaskRuntimeMBean getParentTask() {
      return null;
   }

   public void cancel() {
      if (this.isRunning()) {
         this.imageBuilder.requestImageCaptureCancel();
      }

   }

   public synchronized Exception getError() {
      return this.imageError;
   }

   public boolean isSystemTask() {
      return this.systemTask;
   }

   public void setSystemTask(boolean status) {
      this.systemTask = status;
   }

   public void printLog(PrintWriter writer) {
   }

   private static synchronized int getCount() {
      return ++count;
   }
}
