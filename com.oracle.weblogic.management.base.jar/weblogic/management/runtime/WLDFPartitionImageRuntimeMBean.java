package weblogic.management.runtime;

import java.io.File;
import java.io.FileNotFoundException;
import weblogic.management.ManagementException;

public interface WLDFPartitionImageRuntimeMBean extends RuntimeMBean {
   WLDFImageCreationTaskRuntimeMBean captureImage() throws ManagementException;

   WLDFImageCreationTaskRuntimeMBean captureImage(int var1) throws ManagementException;

   /** @deprecated */
   @Deprecated
   WLDFImageCreationTaskRuntimeMBean[] listImageCaptureTasks();

   WLDFImageCreationTaskRuntimeMBean[] getImageCaptureTasks();

   WLDFImageCreationTaskRuntimeMBean lookupImageCaptureTask(String var1);

   void clearCompletedImageCaptureTasks();

   void resetImageLockout();

   String[] getAvailableCapturedImages();

   void purgeCapturedImages(String var1);

   String getImageDir();

   int getImageTimeout();

   String openImageDataStream(String var1, String var2) throws ManagementException;

   byte[] getNextImageDataChunk(String var1) throws ManagementException;

   void closeImageDataStream(String var1) throws ManagementException;

   File findImageFile(String var1) throws IllegalArgumentException, FileNotFoundException;
}
