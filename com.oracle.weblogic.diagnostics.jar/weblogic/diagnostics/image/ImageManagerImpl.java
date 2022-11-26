package weblogic.diagnostics.image;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.security.AccessController;
import java.text.Collator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.gathering.DataGatheringManager;
import weblogic.diagnostics.utils.DateUtils;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.management.DomainDir;
import weblogic.management.RuntimeDir.Current;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.PlatformConstants;
import weblogic.work.ContextWrap;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

@Service
@Singleton
public class ImageManagerImpl implements ImageManager, PropertyChangeListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final long MIN_USABLE_SPACE_FOR_IMG_CAPTURE = (long)Integer.parseInt(System.getProperty("weblogic.diagnostics.image.MinUsableSpaceForImageCapture", "100000000"));
   private static final FlightRecorderManager flightRecorderMgr = Factory.getInstance();
   private String configuredDestinationPath = null;
   private String globalAbsoluteDestPath = null;
   private String dBeanImageDir = null;
   private int imageTimeout = 0;
   private Map imageSources = new Hashtable();
   private Map partitionAwareImageSources = new Hashtable();
   private long lockoutExpiration = -1L;
   private HashMap lockoutExpirations = new HashMap();
   private WorkManager workManager;
   private ServerMBean serverBean;
   private Set activeTasks = new HashSet();
   private ComponentInvocationContextManager cicManager;
   private RuntimeAccess runtimeAccess;

   public ImageManagerImpl() {
      this.cicManager = ComponentInvocationContextManager.getInstance(kernelId);
      String name = "ImageWorkManager";
      int minThreads = 1;
      int maxThreads = 1;
      this.workManager = WorkManagerFactory.getInstance().findOrCreate(name, minThreads, maxThreads);
      this.runtimeAccess = (RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class);
      this.serverBean = this.runtimeAccess.getServer();
      WLDFServerDiagnosticMBean diagBean = this.serverBean.getServerDiagnosticConfig();
      this.initFromConfiguration(diagBean);
      this.isValidGlobalDestination(this.configuredDestinationPath);
      this.registerImageSource("JVM", new JVMSource());
      DataGatheringManager.initialize();
      if (flightRecorderMgr.isRecordingPossible()) {
         this.registerImageSource("FlightRecorder", new FlightRecorderSource(this));
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Flight recording is not possible, not registering the image source");
      }

   }

   public ImageSource registerImageSource(String name, ImageSource source) {
      if (name != null && source != null) {
         ImageSource previousImageSource = (ImageSource)this.imageSources.put(name, source);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Registered image source: " + name);
         }

         if (source instanceof PartitionAwareImageSource) {
            this.partitionAwareImageSources.put(name, source);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Registered partition aware image source: " + name);
            }
         }

         return previousImageSource;
      } else {
         throw new IllegalArgumentException("Name or source null.");
      }
   }

   public ImageSource unregisterImageSource(String name) throws ImageSourceNotFoundException {
      if (name == null) {
         throw new IllegalArgumentException("ImageSource name null.");
      } else {
         ImageSource previousImageSource = (ImageSource)this.imageSources.remove(name);
         if (previousImageSource == null) {
            throw new ImageSourceNotFoundException("Image source not found: " + name);
         } else {
            this.partitionAwareImageSources.remove(name);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unregistered image source: " + name);
            }

            return previousImageSource;
         }
      }
   }

   public String[] getImageSources() {
      synchronized(this.imageSources) {
         Set imageSourceNames = this.imageSources.keySet();
         return (String[])imageSourceNames.toArray(new String[imageSourceNames.size()]);
      }
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String partitionName, String partitionId) throws ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      return this.captureImage(partitionName, partitionId, (String)null);
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(final String partitionName, final String partitionId, final String destination) throws ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      ComponentInvocationContext cic = this.getCiCToRunAs(partitionName);

      try {
         return (WLDFImageCreationTaskRuntimeMBean)ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public WLDFImageCreationTaskRuntimeMBean call() throws Exception {
               return ImageManagerImpl.this.captureImageInternal(partitionName, partitionId, destination);
            }
         });
      } catch (ExecutionException var7) {
         Throwable cause = var7.getCause();
         if (cause instanceof InvalidDestinationDirectoryException) {
            throw (InvalidDestinationDirectoryException)cause;
         } else if (cause instanceof ImageAlreadyCapturedException) {
            throw (ImageAlreadyCapturedException)cause;
         } else {
            throw new RuntimeException(var7);
         }
      }
   }

   private WLDFImageCreationTaskRuntimeMBean captureImageInternal(String partitionName, String partitionId, String destination) throws ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      return this.createImageWork(partitionName, partitionId, destination, this.imageTimeout);
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String partitionName, String partitionId, int lockoutMinutes) throws InvalidLockoutTimeException, ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      return this.captureImage(partitionName, partitionId, (String)null, lockoutMinutes);
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(final String partitionName, final String partitionId, final String destination, final int lockoutMinutes) throws InvalidLockoutTimeException, ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      ComponentInvocationContext cic = this.getCiCToRunAs(partitionName);

      try {
         return (WLDFImageCreationTaskRuntimeMBean)ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public WLDFImageCreationTaskRuntimeMBean call() throws Exception {
               return ImageManagerImpl.this.captureImageInternal(partitionName, partitionId, destination, lockoutMinutes);
            }
         });
      } catch (ExecutionException var8) {
         Throwable cause = var8.getCause();
         if (cause instanceof InvalidDestinationDirectoryException) {
            throw (InvalidDestinationDirectoryException)cause;
         } else if (cause instanceof InvalidLockoutTimeException) {
            throw (InvalidLockoutTimeException)cause;
         } else if (cause instanceof ImageAlreadyCapturedException) {
            throw (ImageAlreadyCapturedException)cause;
         } else {
            throw new RuntimeException(var8);
         }
      }
   }

   private WLDFImageCreationTaskRuntimeMBean captureImageInternal(String partitionName, String partitionId, String destination, int lockoutMinutes) throws InvalidLockoutTimeException, ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      if (lockoutMinutes < 0) {
         DiagnosticsLogger.logDiagnosticImageLockoutBelow(lockoutMinutes);
         throw new InvalidLockoutTimeException("Specified lock time less than minimum.");
      } else if (lockoutMinutes > 1440) {
         DiagnosticsLogger.logDiagnosticImageLockoutAbove(lockoutMinutes);
         throw new InvalidLockoutTimeException("Specified lock time greater than maximum.");
      } else {
         this.purgeCompletedTasks(partitionName);
         WLDFImageCreationTaskRuntimeMBean work = this.createImageWork(partitionName, partitionId, destination, lockoutMinutes);
         this.activeTasks.add(work);
         return work;
      }
   }

   public String captureImage(final String partitionName, final String partitionId, final String destination, final boolean resetLockout, final long timeoutMillis) throws InvalidDestinationDirectoryException, ImageCaptureFailedException, ImageCaptureTimeoutException, ImageAlreadyCapturedException {
      ComponentInvocationContext cic = this.getCiCToRunAs(partitionName);

      try {
         return (String)ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public String call() throws Exception {
               return ImageManagerImpl.this.captureImageInternal(partitionName, partitionId, destination, resetLockout, timeoutMillis);
            }
         });
      } catch (ExecutionException var10) {
         Throwable cause = var10.getCause();
         if (cause instanceof InvalidDestinationDirectoryException) {
            throw (InvalidDestinationDirectoryException)cause;
         } else if (cause instanceof ImageCaptureFailedException) {
            throw (ImageCaptureFailedException)cause;
         } else if (cause instanceof ImageCaptureTimeoutException) {
            throw (ImageCaptureTimeoutException)cause;
         } else if (cause instanceof ImageAlreadyCapturedException) {
            throw (ImageAlreadyCapturedException)cause;
         } else {
            throw new ImageCaptureFailedException(var10);
         }
      }
   }

   private String captureImageInternal(String partitionName, String partitionId, String destination, boolean resetLockout, long timeoutMillis) throws InvalidDestinationDirectoryException, ImageCaptureFailedException, ImageCaptureTimeoutException, ImageAlreadyCapturedException {
      if (resetLockout) {
         this.resetImageLockout(partitionName);
      }

      TaskRuntimeMBeanImpl task = (TaskRuntimeMBeanImpl)this.createImageWork(partitionName, partitionId, destination, this.imageTimeout);
      long startTime = System.currentTimeMillis();

      while(this.waitingForImageCaptureCompletion(task, startTime, timeoutMillis)) {
         try {
            Thread.sleep(2000L);
         } catch (InterruptedException var11) {
         }
      }

      return task.getDescription();
   }

   private ComponentInvocationContext getCiCToRunAs(String partitionName) {
      ComponentInvocationContext cic = this.cicManager.getCurrentComponentInvocationContext();
      if (partitionName != null && !partitionName.equals("DOMAIN")) {
         String currentPartitionName = cic.getPartitionName();
         if (partitionName.equals(currentPartitionName)) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("getCiCToRunAs: using current CiC: " + currentPartitionName);
            }

            return cic;
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("getCiCToRunAs: create CiC for " + partitionName);
            }

            return this.cicManager.createComponentInvocationContext(partitionName);
         }
      } else if (!cic.isGlobalRuntime()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getCiCToRunAs: will runAs global, but current CiC was not global runtime: " + cic.getPartitionName() + ", current: " + "DOMAIN");
         }

         return this.cicManager.createComponentInvocationContext("DOMAIN");
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getCiCToRunAs: global capture and current CiC is global runtime: " + cic.getPartitionName());
         }

         return cic;
      }
   }

   private boolean waitingForImageCaptureCompletion(TaskRuntimeMBeanImpl task, long startTime, long timeoutMillis) throws ImageCaptureFailedException, ImageCaptureTimeoutException {
      int status = task.getStatusInternal();
      if (status == 2) {
         return false;
      } else if (status == 3) {
         throw new ImageCaptureFailedException(task.getError());
      } else {
         if (timeoutMillis > 0L) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= timeoutMillis) {
               task.cancel();
               throw new ImageCaptureTimeoutException();
            }
         }

         return true;
      }
   }

   public boolean tasksInProgress(String partitionName) {
      this.purgeCompletedTasks(partitionName);
      return !this.activeTasks.isEmpty();
   }

   public void setDestinationDirectory(String destination) throws InvalidDestinationDirectoryException {
      if (!this.isValidGlobalDestination(destination)) {
         DiagnosticsLogger.logDiagnosticImageDirectoryAccessError(destination);
         throw new InvalidDestinationDirectoryException(destination);
      } else {
         this.configuredDestinationPath = destination;
      }
   }

   public String getDestinationDirectory() {
      return this.getAbsoluteDestinationDirectory((String)null);
   }

   public void resetImageLockout(String partitionName) {
      this.setLockoutExpiration(partitionName, -1L);
   }

   public Map getInternalImageSources(String partitionName) {
      return partitionName == null ? this.imageSources : this.partitionAwareImageSources;
   }

   private void initFromConfiguration(WLDFServerDiagnosticMBean dBean) {
      String imageDir = dBean.getImageDir();
      this.dBeanImageDir = dBean.getImageDir();
      this.imageTimeout = dBean.getImageTimeout();
      File f = new File(imageDir);
      if (!f.isAbsolute()) {
         File f2 = new File(DomainDir.getPathRelativeServerDir(this.serverBean.getName(), imageDir));
         this.configuredDestinationPath = f2.getAbsolutePath();
      } else {
         this.configuredDestinationPath = imageDir;
      }

   }

   private synchronized WLDFImageCreationTaskRuntimeMBean createImageWork(String partitionName, String partitionId, String dest, int mins) throws ImageAlreadyCapturedException, InvalidDestinationDirectoryException {
      this.checkForThrottledImageCapture(partitionName);
      String destinationPath = this.getAbsoluteDestinationDirectory(dest);
      if (!this.isValidAbsoluteDestination(destinationPath)) {
         DiagnosticsLogger.logDiagnosticImageDirectoryAccessError(destinationPath);
         throw new InvalidDestinationDirectoryException(destinationPath);
      } else {
         DiagnosticsLogger.logDiagnosticImageCaptureRequest(destinationPath, mins);
         ImageRequester imageRequester = new ImageRequester(new Exception(), partitionName, partitionId);
         ImageWork imageWork = new ImageWork(destinationPath, mins, imageRequester);
         this.workManager.schedule(new ContextWrap(imageWork));
         if (mins != 0) {
            this.createLockout(partitionName, mins);
         }

         return imageWork.getImageWatcher();
      }
   }

   private void checkForThrottledImageCapture(String partitionName) throws ImageAlreadyCapturedException {
      long longestExpiration = this.getLockoutExpiration(partitionName);
      if (longestExpiration != -1L) {
         long currentTime = System.currentTimeMillis();
         boolean throttled = currentTime <= longestExpiration;
         if (throttled) {
            DiagnosticsLogger.logDiagnosticImageAlreadyCaptured();
            long lockoutDuration = longestExpiration - currentTime;
            String throttleMsg = "Image captures are throttled for another " + lockoutDuration + " milliseconds due to lockout specification.";
            throw new ImageAlreadyCapturedException(throttleMsg);
         } else {
            this.setLockoutExpiration(partitionName, -1L);
         }
      }
   }

   private long getLockoutExpiration(String partitionName) {
      return partitionName == null ? this.lockoutExpiration : this.getPartitionLockoutExpiration(partitionName);
   }

   private void setLockoutExpiration(String partitionName, long expiration) {
      if (partitionName == null) {
         this.lockoutExpiration = expiration;
      } else {
         Long expLong = expiration;
         this.lockoutExpirations.put(partitionName, expLong);
      }
   }

   private long getPartitionLockoutExpiration(String partitionName) {
      Long expiration = (Long)this.lockoutExpirations.get(partitionName);
      if (expiration == null) {
         expiration = -1L;
         this.lockoutExpirations.put(partitionName, expiration);
      }

      return expiration;
   }

   private void createLockout(String partitionName, int lockoutMinutes) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Creating lockout for image captures (minutes): " + lockoutMinutes);
      }

      long lockoutMilliseconds = (long)(lockoutMinutes * 60 * 1000);
      this.setLockoutExpiration(partitionName, System.currentTimeMillis() + lockoutMilliseconds);
   }

   private boolean isValidGlobalDestination(String destination) {
      if (destination == null) {
         return false;
      } else {
         File f = new File(destination);
         if (!f.isAbsolute()) {
            ServerMBean serverBean = this.runtimeAccess.getServer();
            File f2 = new File(DomainDir.getPathRelativeServerDir(serverBean.getName(), destination));
            this.globalAbsoluteDestPath = f2.getAbsolutePath();
         } else {
            this.globalAbsoluteDestPath = destination;
         }

         File directory = new File(this.globalAbsoluteDestPath);
         if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
               DiagnosticsLogger.logDiagnosticImageDirectoryCreationError(this.globalAbsoluteDestPath);
               return false;
            }
         } else if (!directory.isDirectory()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.globalAbsoluteDestPath + " is not a directory");
            }

            return false;
         }

         return !this.checkUsableSpace(directory) ? false : directory.canWrite();
      }
   }

   private boolean checkUsableSpace(File file) {
      long usableSpace = file.getUsableSpace();
      return usableSpace > MIN_USABLE_SPACE_FOR_IMG_CAPTURE;
   }

   public String getAbsoluteDestinationDirectory(String destinationOverride) {
      if (this.cicManager.getCurrentComponentInvocationContext().isGlobalRuntime()) {
         if (destinationOverride == null) {
            return this.globalAbsoluteDestPath;
         } else {
            File f = new File(destinationOverride);
            if (!f.isAbsolute()) {
               ServerMBean serverBean = this.runtimeAccess.getServer();
               File f2 = new File(DomainDir.getPathRelativeServerDir(serverBean.getName(), destinationOverride));
               return f2.getAbsolutePath();
            } else {
               return destinationOverride;
            }
         }
      } else {
         String partitionBaseImageDir = Current.get().getLogsDirForServer(this.serverBean.getName()) + PlatformConstants.FILE_SEP + "diagnostic_images";
         return partitionBaseImageDir;
      }
   }

   private boolean isValidAbsoluteDestination(String destination) {
      if (destination == null) {
         return false;
      } else {
         File destFile = new File(destination);
         if (!destFile.isAbsolute()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(destination + " is not an absolute destination");
            }

            return false;
         } else {
            if (!destFile.exists()) {
               boolean created = destFile.mkdirs();
               if (!created) {
                  DiagnosticsLogger.logDiagnosticImageDirectoryCreationError(destination);
                  return false;
               }
            } else if (!destFile.isDirectory()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(destination + " is not a directory");
               }

               return false;
            }

            return destFile.canWrite();
         }
      }
   }

   public void propertyChange(PropertyChangeEvent evt) {
      this.attributesChanged(evt.getSource());
   }

   private void attributesChanged(Object source) {
      if (source instanceof WLDFServerDiagnosticMBean) {
         WLDFServerDiagnosticMBean diagnosticMBean = (WLDFServerDiagnosticMBean)source;
         this.initFromConfiguration(diagnosticMBean);
      }

   }

   public String[] getAvailableCapturedImages(final String partitionName) {
      ComponentInvocationContext cic = this.getCiCToRunAs(partitionName);

      try {
         return (String[])((String[])ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public String[] call() throws Exception {
               return ImageManagerImpl.this.getAvailableCapturedImagesInternal(partitionName);
            }
         }));
      } catch (ExecutionException var4) {
         throw new RuntimeException(var4);
      }
   }

   private String[] getAvailableCapturedImagesInternal(String partitionName) {
      String[] names = new String[0];
      String destinationPath = this.getAbsoluteDestinationDirectory((String)null);
      if (destinationPath != null) {
         File directory = new File(destinationPath);
         File[] files = directory.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
               ComponentInvocationContext cic = ImageManagerImpl.this.cicManager.getCurrentComponentInvocationContext();
               String nameCheck = pathname.getName();
               return nameCheck.endsWith(".zip") && ImageManagerImpl.this.stringContainsPartitionIdOrIsNull(nameCheck, cic.isGlobalRuntime() ? null : cic.getPartitionId());
            }
         });
         if (files != null && files.length > 0) {
            names = new String[files.length];

            for(int i = 0; i < files.length; ++i) {
               names[i] = files[i].getName();
            }

            if (files.length > 1) {
               Arrays.sort(names, Collator.getInstance());
            }
         }
      }

      return names;
   }

   public void purgeCapturedImages(final String partitionName, final String age) {
      ComponentInvocationContext cic = this.getCiCToRunAs(partitionName);

      try {
         ComponentInvocationContextManager.runAs(kernelId, cic, new Runnable() {
            public void run() {
               ImageManagerImpl.this.purgeCapturedImagesInternal(partitionName, age);
            }
         });
      } catch (ExecutionException var5) {
         throw new RuntimeException(var5);
      }
   }

   private void purgeCapturedImagesInternal(String partitionName, String age) {
      long ageTimestamp = DateUtils.getTimestamp(age, false);
      String[] images = this.getAvailableCapturedImages(partitionName);
      if (images != null) {
         String[] var6 = images;
         int var7 = images.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String image = var6[var8];
            File imageFile = this.findImageFile(partitionName, image);
            long fileTimestamp = imageFile.lastModified();
            if (fileTimestamp < ageTimestamp) {
               imageFile.delete();
            }
         }
      }

   }

   private String getPartitionName(String partitionId) {
      if (partitionId == null) {
         return "DOMAIN";
      } else {
         PartitionTableEntry entry = PartitionTable.getInstance().lookupByID(partitionId);
         return entry == null ? null : entry.getPartitionName();
      }
   }

   public File findImageFile(final String partitionName, final String fileName) {
      ComponentInvocationContext cic = this.getCiCToRunAs(partitionName);

      try {
         return (File)ComponentInvocationContextManager.runAs(kernelId, cic, new Callable() {
            public File call() throws Exception {
               return ImageManagerImpl.this.findImageFileInternal(partitionName, fileName);
            }
         });
      } catch (ExecutionException var5) {
         throw new RuntimeException(var5);
      }
   }

   private File findImageFileInternal(String partitionName, String fileName) {
      File requestedFile = new File(fileName);
      ComponentInvocationContext cic = this.cicManager.getCurrentComponentInvocationContext();
      String partitionId = cic.isGlobalRuntime() ? null : cic.getPartitionId();
      if (!requestedFile.isAbsolute() && this.stringContainsPartitionIdOrIsNull(fileName, partitionId)) {
         File imageFile = new File(this.getDestinationDirectory() + File.separatorChar + requestedFile.getName());
         return imageFile;
      } else {
         throw new IllegalArgumentException("Invalid image file requested");
      }
   }

   private void purgeCompletedTasks(String partitionName) {
      Iterator taskIt = this.activeTasks.iterator();

      while(taskIt.hasNext()) {
         WLDFImageCreationTaskRuntimeMBean task = (WLDFImageCreationTaskRuntimeMBean)taskIt.next();
         if (!task.isRunning()) {
            taskIt.remove();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Purging completed image task " + task.getName());
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Image task " + task.getName() + " still running");
         }
      }

   }

   private boolean stringContainsPartitionIdOrIsNull(String stringToCheck, String partitionId) {
      return partitionId == null ? true : stringToCheck.contains(partitionId);
   }

   public String getImageDir() {
      return this.cicManager.getCurrentComponentInvocationContext().isGlobalRuntime() ? this.dBeanImageDir : "logs" + PlatformConstants.FILE_SEP + "diagnostic_images";
   }

   public int getImageTimeout() {
      return this.imageTimeout;
   }
}
