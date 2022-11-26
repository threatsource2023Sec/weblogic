package weblogic.diagnostics.image;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class ImageBuilder {
   ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private WorkManager workManager = WorkManagerFactory.getInstance().getDefault();
   private ZipOutputStream zipFile;
   private ImageRequester imageRequester;
   private ImageSummary imageSummary;
   private String imageDirectory;
   private String archiveFileName;
   private String archiveName;
   private long imageCreationTime;
   private boolean cancelRequest = false;
   private boolean partitionCapture = false;
   private static final Map extensionsMap = buildExtensionsMap();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   ImageBuilder(String destination, long creationTime, ImageRequester requester) {
      this.imageDirectory = destination;
      this.imageCreationTime = creationTime;
      this.imageRequester = requester;
   }

   private static Map buildExtensionsMap() {
      Map map = new HashMap();
      map.put("JVM", ".xml");
      map.put("configuration", ".zip");
      map.put("CONNECTOR", ".xml");
      map.put("JDBC", ".txt");
      map.put("APPLICATION", ".xml");
      map.put("WORK_MANAGER", ".txt");
      map.put("Logging", ".txt");
      map.put("JMS", ".xml");
      map.put("JTA", ".xml");
      map.put("SAF", ".xml");
      map.put("PathService", ".xml");
      map.put("Deployment", ".xml");
      map.put("FlightRecorder", ".jfr");
      map.put("Cluster", ".xml");
      map.put("HarvesterImageSource", ".xml");
      map.put("InstrumentationImageSource", ".xml");
      map.put("WatchSource", ".xml");
      map.put("ManagementRuntimeImageSource", ".xml");
      map.put("JNDI_IMAGE_SOURCE", ".xml");
      map.put("PERSISTENT_STORE", ".xml");
      map.put("RCM", ".xml");
      return map;
   }

   void requestImageCaptureCancel() {
      this.cancelRequest = true;
   }

   String getArchiveName() {
      return this.partitionCapture ? this.archiveFileName : this.archiveName;
   }

   String getArchiveFileName() {
      return this.archiveFileName;
   }

   synchronized void buildImage(TaskRuntimeMBeanImpl watcher) {
      long createImageStartTime = System.currentTimeMillis();

      try {
         this.cancelRequest = false;
         Map imageSources = this.imageManager.getInternalImageSources(this.imageRequester.getPartitionName());
         this.createImageSourceArchive();
         synchronized(imageSources) {
            Set imageNames = imageSources.keySet();
            Iterator nameIterator = imageNames.iterator();

            while(!this.cancelRequest && nameIterator.hasNext()) {
               String sourceName = (String)nameIterator.next();
               ImageSource source = (ImageSource)imageSources.get(sourceName);
               long createImageSourceStartTime = System.currentTimeMillis();
               this.addImageSourceToArchive(this.imageRequester.getPartitionName(), sourceName, source);
               long createImageSourceElapsedTime = System.currentTimeMillis() - createImageSourceStartTime;
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Added image source " + sourceName + " to archive: " + this.archiveName + " in " + createImageSourceElapsedTime + " ms.");
               }
            }

            if (this.cancelRequest && this.imageSummary != null) {
               this.imageSummary.captureCancelled();
            }
         }
      } catch (IOException var61) {
         this.logBuilderError(watcher, var61);
      } finally {
         try {
            long createImageElapsedTime = System.currentTimeMillis() - createImageStartTime;
            if (this.imageSummary != null) {
               this.imageSummary.setImageCreationElapsedTime(createImageElapsedTime);
            }

            this.closeImageSourceArchive();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Finished processing archive: " + this.archiveName + " in " + createImageElapsedTime + " ms.");
            }
         } catch (Exception var58) {
            this.logBuilderError(watcher, var58);
         } finally {
            watcher.setEndTime(System.currentTimeMillis());
         }

      }

   }

   private void determineUniqueDiagnosticImageName() {
      StringBuffer sb = new StringBuffer();
      String separator = "_";
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
      sb.append("diagnostic_image_");
      String partitionName = this.imageRequester.getPartitionName();
      String partitionId = this.imageRequester.getPartitionId();
      if (partitionName != null && partitionName.length() > 0) {
         this.partitionCapture = true;
         sb.append(separator);
         sb.append(partitionName);
         sb.append(separator);
         sb.append(partitionId);
         sb.append(separator);
      }

      sb.append(ManagementService.getRuntimeAccess(kernelId).getServer().getName() + separator);
      sb.append(formatter.format(new Date(this.imageCreationTime)));
      String imageFilename = sb.toString();
      File imageFile = new File(this.imageDirectory, imageFilename + ".zip");
      if (imageFile.exists()) {
         imageFilename = this.incrementName(imageFilename);
      }

      this.archiveFileName = imageFilename + ".zip";
      this.archiveName = this.imageDirectory + File.separator + this.archiveFileName;
   }

   private void createImageSourceArchive() throws IOException {
      this.determineUniqueDiagnosticImageName();
      OutputStream out = new BufferedOutputStream(new FileOutputStream(this.archiveName));
      this.zipFile = new ZipOutputStream(out);
      this.imageSummary = new ImageSummary(this.imageCreationTime, this.imageRequester, this.archiveName);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Creating image source archive: " + this.archiveName);
      }

   }

   private void closeImageSourceArchive() throws IOException, ImageSourceCreationException {
      if (this.zipFile == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No zip formed for image source archive: " + this.archiveName);
         }

      } else {
         String summaryFileName = "image.summary";
         ZipEntry summaryEntry = new ZipEntry(summaryFileName);
         boolean var16 = false;

         label130: {
            label131: {
               try {
                  var16 = true;
                  synchronized(this.zipFile) {
                     this.zipFile.putNextEntry(summaryEntry);
                  }

                  this.imageSummary.createDiagnosticImage(this.imageRequester.getPartitionName(), this.zipFile);
                  var16 = false;
                  break label131;
               } catch (Exception var21) {
                  DiagnosticsLogger.logDiagnosticImageSourceCreationException(summaryFileName, var21);
                  var16 = false;
               } finally {
                  if (var16) {
                     synchronized(this.zipFile) {
                        this.zipFile.closeEntry();
                        this.zipFile.close();
                     }
                  }
               }

               synchronized(this.zipFile) {
                  this.zipFile.closeEntry();
                  this.zipFile.close();
                  break label130;
               }
            }

            synchronized(this.zipFile) {
               this.zipFile.closeEntry();
               this.zipFile.close();
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Closing image source archive: " + this.archiveName);
         }

      }
   }

   private void addImageSourceToArchive(String partitionName, String sourceName, ImageSource source) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Adding image source to archive: " + sourceName);
      }

      int idx = sourceName.indexOf(36);
      String src = idx > 0 ? sourceName.substring(0, idx) : sourceName;
      String extension = (String)extensionsMap.get(src);
      if (extension == null) {
         extension = ".img";
      }

      String file = sourceName + extension;
      ZipEntry nextEntry = new ZipEntry(file);
      synchronized(this.zipFile) {
         this.zipFile.putNextEntry(nextEntry);
      }

      ImageSourceOutputStream imageStream = new ImageSourceOutputStream(this.zipFile);
      ImageSourceWork sourceWork = new ImageSourceWork(partitionName, sourceName, source, imageStream);
      this.workManager.schedule(sourceWork);
      this.handleImageSourceWork(sourceWork);
      synchronized(this.zipFile) {
         this.zipFile.closeEntry();
         this.zipFile.flush();
      }

      if (sourceWork.getFailureThrowable() == null) {
         this.imageSummary.addSuccessfulImageSource(sourceName, sourceWork.getImageSourceElapsedTime());
      } else {
         this.imageSummary.addFailedImageSource(sourceName, sourceWork.getFailureThrowable());
         DiagnosticsLogger.logDiagnosticImageSourceCreationException(sourceName, sourceWork.getFailureThrowable());
      }

   }

   private String incrementName(String filename) {
      File imageDestination = new File(this.imageDirectory);
      String sep = "_";
      int digit = 0;
      FilenameFilter filter = new ImageFilenameFilter(filename + sep);
      String[] matchedImageNames = imageDestination.list(filter);
      if (matchedImageNames.length > 0) {
         Arrays.sort(matchedImageNames);
         String lastImageName = matchedImageNames[matchedImageNames.length - 1];
         lastImageName = lastImageName.split("[.]")[0];
         String[] parts = lastImageName.split(sep);
         digit = Integer.parseInt(parts[parts.length - 1]);
      }

      ++digit;
      return filename + sep + digit;
   }

   private void handleImageSourceWork(ImageSourceWork sourceWork) {
      this.waitForImageSource(sourceWork);
      if (!sourceWork.isFinished()) {
         sourceWork.timeoutWork();
      }

      ImageSourceOutputStream out = (ImageSourceOutputStream)sourceWork.getOutputStream();
      out.close();
   }

   private void waitForImageSource(ImageSourceWork work) {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Waiting for " + work.getImageSourceName() + " work to complete...");
         }

         int waitIncrement = 6000;

         for(int totalWait = 0; totalWait < 60000 && !work.isFinished(); totalWait += waitIncrement) {
            synchronized(work) {
               work.wait((long)waitIncrement);
            }
         }

         if (debugLogger.isDebugEnabled()) {
            if (!work.isFinished()) {
               debugLogger.debug("Timed out waiting for source " + work.getImageSourceName());
            } else {
               debugLogger.debug("Work for " + work.getImageSourceName() + " complete.");
            }
         }
      } catch (InterruptedException var7) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Work for " + work.getImageSourceName() + " interrupted: ", var7);
         }
      }

   }

   private void logBuilderError(TaskRuntimeMBeanImpl watcher, Exception error) {
      DiagnosticsLogger.logDiagnosticImageCreationError(error);
      watcher.setError(error);
   }
}
