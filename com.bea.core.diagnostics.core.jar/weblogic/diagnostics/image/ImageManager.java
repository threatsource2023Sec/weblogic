package weblogic.diagnostics.image;

import java.io.File;
import java.util.Map;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.TaskRuntimeMBean;

@Contract
public interface ImageManager {
   ImageSource registerImageSource(String var1, ImageSource var2);

   ImageSource unregisterImageSource(String var1) throws ImageSourceNotFoundException;

   TaskRuntimeMBean captureImage(String var1, String var2) throws ImageAlreadyCapturedException, InvalidDestinationDirectoryException;

   TaskRuntimeMBean captureImage(String var1, String var2, String var3) throws ImageAlreadyCapturedException, InvalidDestinationDirectoryException;

   TaskRuntimeMBean captureImage(String var1, String var2, int var3) throws InvalidLockoutTimeException, ImageAlreadyCapturedException, InvalidDestinationDirectoryException;

   TaskRuntimeMBean captureImage(String var1, String var2, String var3, int var4) throws InvalidLockoutTimeException, ImageAlreadyCapturedException, InvalidDestinationDirectoryException;

   String captureImage(String var1, String var2, String var3, boolean var4, long var5) throws InvalidDestinationDirectoryException, ImageCaptureFailedException, ImageCaptureTimeoutException, ImageAlreadyCapturedException;

   File findImageFile(String var1, String var2);

   String[] getAvailableCapturedImages(String var1);

   String getDestinationDirectory();

   void resetImageLockout(String var1);

   Map getInternalImageSources(String var1);

   String[] getImageSources();

   String getImageDir();

   boolean tasksInProgress(String var1);

   int getImageTimeout();

   void purgeCapturedImages(String var1, String var2);
}
