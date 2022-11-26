package weblogic.diagnostics.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionImageRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionImageRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFPartitionImageRuntimeMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();
   protected String partitionId = null;
   protected String partitionName = null;
   protected ImageRuntimeHelper imHelper;

   private PartitionImageRuntimeMBeanImpl() throws ManagementException {
   }

   public PartitionImageRuntimeMBeanImpl(RuntimeMBean parent) throws ManagementException {
      super(parent.getName(), parent);
      this.imHelper = ImageRuntimeHelper.getInstance();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      this.partitionName = this.name;
      this.partitionId = runtimeAccess.getDomain().lookupPartition(this.name).getPartitionID();
   }

   protected PartitionImageRuntimeMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage() throws ManagementException {
      return this.setTaskRestParent(this.imHelper.captureImage(this.partitionName, this.partitionId));
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(int lockoutMinutes) throws ManagementException {
      return this.setTaskRestParent(this.imHelper.captureImage(this.partitionName, this.partitionId, lockoutMinutes));
   }

   public WLDFImageCreationTaskRuntimeMBean[] listImageCaptureTasks() {
      return this.getImageCaptureTasks();
   }

   public WLDFImageCreationTaskRuntimeMBean[] getImageCaptureTasks() {
      WLDFImageCreationTaskRuntimeMBean[] tasks = this.imHelper.listImageCaptureTasks(this.partitionName);
      if (tasks != null) {
         WLDFImageCreationTaskRuntimeMBean[] var2 = tasks;
         int var3 = tasks.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFImageCreationTaskRuntimeMBean task = var2[var4];
            this.setTaskRestParent(task);
         }
      }

      return tasks;
   }

   public WLDFImageCreationTaskRuntimeMBean lookupImageCaptureTask(String name) {
      if (name == null) {
         return null;
      } else {
         WLDFImageCreationTaskRuntimeMBean[] tasks = this.getImageCaptureTasks();
         if (tasks != null) {
            WLDFImageCreationTaskRuntimeMBean[] var3 = tasks;
            int var4 = tasks.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               WLDFImageCreationTaskRuntimeMBean task = var3[var5];
               if (name.equals(task.getName())) {
                  this.setTaskRestParent(task);
                  return task;
               }
            }
         }

         return null;
      }
   }

   public void clearCompletedImageCaptureTasks() {
      this.imHelper.clearCompletedImageCaptureTasks(this.partitionName);
   }

   protected WLDFImageCreationTaskRuntimeMBean setTaskRestParent(WLDFImageCreationTaskRuntimeMBean task) {
      ((RuntimeMBeanDelegate)task).setRestParent(this);
      return task;
   }

   public void resetImageLockout() {
      this.imHelper.resetImageLockout(this.partitionName);
   }

   public String[] getAvailableCapturedImages() {
      return this.imHelper.getAvailableCapturedImages(this.partitionName);
   }

   public void purgeCapturedImages(String age) {
      this.imHelper.purgeCapturedImages(this.partitionName, age);
   }

   public String openImageDataStream(String imageName, String imageEntry) throws ManagementException {
      return this.imHelper.openImageDataStream(this.partitionName, imageName, imageEntry);
   }

   public byte[] getNextImageDataChunk(String imageStreamHandle) throws ManagementException {
      return this.imHelper.getNextImageDataChunk(imageStreamHandle);
   }

   public void closeImageDataStream(String imageStreamHandle) throws ManagementException {
      this.imHelper.closeImageDataStream(imageStreamHandle);
   }

   public String getImageDir() {
      return this.imHelper.getImageDir();
   }

   public int getImageTimeout() {
      return this.imHelper.getImageTimeout();
   }

   public File findImageFile(String imageName) throws IllegalArgumentException, FileNotFoundException {
      if (imageName != null && !imageName.isEmpty()) {
         File imgFile = this.imHelper.findImageFile(this.partitionName, imageName);
         if (!imgFile.isDirectory() && imgFile.exists()) {
            return imgFile;
         } else {
            throw new FileNotFoundException(DTF.getImageFileDoesNotExist(imageName));
         }
      } else {
         throw new IllegalArgumentException(DTF.getImageFileNullOrEmpty());
      }
   }
}
