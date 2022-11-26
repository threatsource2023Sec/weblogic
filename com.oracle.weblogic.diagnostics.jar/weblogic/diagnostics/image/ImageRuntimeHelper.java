package weblogic.diagnostics.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import weblogic.diagnostics.accessor.data.IncrementalDataReader;
import weblogic.diagnostics.accessor.data.IncrementalDataReaderFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

class ImageRuntimeHelper {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ImageRuntimeHelper singleton;
   private static ImageManager im;
   private List tasks = Collections.synchronizedList(new ArrayList());

   static synchronized ImageRuntimeHelper getInstance() throws ManagementException {
      if (singleton == null) {
         singleton = new ImageRuntimeHelper();
      }

      return singleton;
   }

   public ImageRuntimeHelper() throws ManagementException {
      im = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String partitionName, String partitionId) throws ManagementException {
      try {
         WLDFImageCreationTaskRuntimeMBean trmb = (WLDFImageCreationTaskRuntimeMBean)im.captureImage(partitionName, partitionId);
         this.tasks.add(new PartitionAwareTaskWrapper(partitionName, trmb));
         return trmb;
      } catch (ImageAlreadyCapturedException var4) {
         throw new ManagementException(var4);
      } catch (InvalidDestinationDirectoryException var5) {
         throw new ManagementException(var5);
      }
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String partitionName, String partitionId, int lockoutMinutes) throws ManagementException {
      try {
         WLDFImageCreationTaskRuntimeMBean trmb = (WLDFImageCreationTaskRuntimeMBean)im.captureImage(partitionName, partitionId, lockoutMinutes);
         this.tasks.add(new PartitionAwareTaskWrapper(partitionName, trmb));
         return trmb;
      } catch (ImageAlreadyCapturedException var5) {
         throw new ManagementException(var5);
      } catch (InvalidDestinationDirectoryException var6) {
         throw new ManagementException(var6);
      } catch (InvalidLockoutTimeException var7) {
         throw new ManagementException(var7);
      }
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String partitionName, String partitionId, String destination) throws ManagementException {
      try {
         WLDFImageCreationTaskRuntimeMBean trmb = (WLDFImageCreationTaskRuntimeMBean)im.captureImage(partitionName, partitionId, destination);
         this.tasks.add(new PartitionAwareTaskWrapper(partitionName, trmb));
         return trmb;
      } catch (ImageAlreadyCapturedException var5) {
         throw new ManagementException(var5);
      } catch (InvalidDestinationDirectoryException var6) {
         throw new ManagementException(var6);
      }
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String partitionName, String partitionId, String destination, int lockoutMinutes) throws ManagementException {
      try {
         WLDFImageCreationTaskRuntimeMBean trmb = (WLDFImageCreationTaskRuntimeMBean)im.captureImage(partitionName, partitionId, destination, lockoutMinutes);
         this.tasks.add(new PartitionAwareTaskWrapper(partitionName, trmb));
         return trmb;
      } catch (ImageAlreadyCapturedException var6) {
         throw new ManagementException(var6);
      } catch (InvalidDestinationDirectoryException var7) {
         throw new ManagementException(var7);
      } catch (InvalidLockoutTimeException var8) {
         throw new ManagementException(var8);
      }
   }

   public WLDFImageCreationTaskRuntimeMBean[] listImageCaptureTasks(String partitionName) {
      List list = new ArrayList();
      Iterator var3 = this.tasks.iterator();

      while(true) {
         PartitionAwareTaskWrapper taskWrapper;
         do {
            if (!var3.hasNext()) {
               return (WLDFImageCreationTaskRuntimeMBean[])list.toArray(new WLDFImageCreationTaskRuntimeMBean[list.size()]);
            }

            taskWrapper = (PartitionAwareTaskWrapper)var3.next();
         } while(partitionName != null && !partitionName.equals(taskWrapper.getPartitionName()));

         list.add(taskWrapper.getTask());
      }
   }

   public void clearCompletedImageCaptureTasks(String partitionName) {
      Set removeWrapperSet = new HashSet();
      Iterator iter = this.tasks.iterator();

      while(true) {
         PartitionAwareTaskWrapper taskWrapper;
         do {
            if (!iter.hasNext()) {
               iter = removeWrapperSet.iterator();

               while(iter.hasNext()) {
                  this.tasks.remove((PartitionAwareTaskWrapper)iter.next());
               }

               return;
            }

            taskWrapper = (PartitionAwareTaskWrapper)iter.next();
         } while(partitionName != null && !partitionName.equals(taskWrapper.getPartitionName()));

         WLDFImageCreationTaskRuntimeMBean task = taskWrapper.getTask();
         if (task.getStatus().equalsIgnoreCase("Completed")) {
            removeWrapperSet.add(taskWrapper);
         }
      }
   }

   public void resetImageLockout(String partitionName) {
      im.resetImageLockout(partitionName);
   }

   public String[] getAvailableCapturedImages(String partitionName) {
      return im.getAvailableCapturedImages(partitionName);
   }

   public void purgeCapturedImages(String partitionName, String age) {
      im.purgeCapturedImages(partitionName, age);
   }

   public String openImageDataStream(String partitionName, String imageName, String imageEntry) throws ManagementException {
      ZipEntry imageZipEntry = null;
      IncrementalDataReader.CloseAction zipFileCloseAction = null;
      File tempFile = null;

      try {
         File file = im.findImageFile(partitionName, imageName);
         tempFile = File.createTempFile(file.getName(), (String)null);
         String handle = tempFile.getName();
         tempFile.delete();
         InputStream inputStream = null;
         final ZipFile imageZipFile;
         if (imageEntry == null) {
            inputStream = new FileInputStream(file);
            imageZipFile = null;
         } else {
            imageZipFile = new ZipFile(file);
            zipFileCloseAction = new IncrementalDataReader.CloseAction() {
               public void run() {
                  if (imageZipFile != null) {
                     try {
                        imageZipFile.close();
                     } catch (IOException var2) {
                     }
                  }

               }
            };
            imageZipEntry = imageZipFile.getEntry(imageEntry);
            inputStream = imageZipFile.getInputStream(imageZipEntry);
         }

         IncrementalDataReader reader = IncrementalDataReaderFactory.getInstance().initializeIncrementalDataReader(handle, (InputStream)inputStream);
         reader.setCloseAction(zipFileCloseAction);
         return handle;
      } catch (Exception var12) {
         if (zipFileCloseAction != null) {
            zipFileCloseAction.run();
         }

         throw new ManagementException(var12);
      }
   }

   public byte[] getNextImageDataChunk(String imageStreamHandle) throws ManagementException {
      try {
         IncrementalDataReader reader = IncrementalDataReaderFactory.getInstance().getIncrementalDataReader(imageStreamHandle);
         return reader.getNextDataChunk();
      } catch (Exception var3) {
         throw new ManagementException(var3);
      }
   }

   public void closeImageDataStream(String imageStreamHandle) throws ManagementException {
      try {
         IncrementalDataReaderFactory.getInstance().closeIncrementalDataReader(imageStreamHandle);
      } catch (Exception var3) {
         throw new ManagementException(var3);
      }
   }

   public String getImageDir() {
      return im.getImageDir();
   }

   public int getImageTimeout() {
      return im.getImageTimeout();
   }

   public File findImageFile(String partitionId, String imageName) {
      return im.findImageFile(partitionId, imageName);
   }

   private static class PartitionAwareTaskWrapper {
      private WLDFImageCreationTaskRuntimeMBean task;
      private String partitionName;

      private PartitionAwareTaskWrapper(String partitionName, WLDFImageCreationTaskRuntimeMBean task) {
         this.task = null;
         this.partitionName = null;
         this.task = task;
         this.partitionName = partitionName;
      }

      public WLDFImageCreationTaskRuntimeMBean getTask() {
         return this.task;
      }

      public String getPartitionName() {
         return this.partitionName;
      }

      public void setTask(WLDFImageCreationTaskRuntimeMBean task) {
         this.task = task;
      }

      public void setPartitionName(String partitionName) {
         this.partitionName = partitionName;
      }

      // $FF: synthetic method
      PartitionAwareTaskWrapper(String x0, WLDFImageCreationTaskRuntimeMBean x1, Object x2) {
         this(x0, x1);
      }
   }
}
