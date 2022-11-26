package weblogic.diagnostics.image;

import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.version;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.image.descriptor.FailedImageSourceBean;
import weblogic.diagnostics.image.descriptor.ImageSummaryBean;
import weblogic.diagnostics.image.descriptor.SuccessfulImageSourceBean;
import weblogic.diagnostics.image.descriptor.SystemPropertyBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;

class ImageSummary implements PartitionAwareImageSource {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean captureCancelled;
   private String imageName;
   private Date imageCreationDate;
   private Long imageCreationElapsedTime;
   private Map successfulImageSources = new HashMap();
   private Map failedImageSources = new HashMap();
   private ImageRequester imageRequester;
   private ImageSummaryBean root;

   ImageSummary(long creationTime, ImageRequester requester, String name) {
      this.imageCreationDate = new Date(creationTime);
      this.imageRequester = requester;
      this.imageName = name;
      this.setImageCreationElapsedTime(0L);
   }

   void setImageCreationElapsedTime(long totalElapsedTime) {
      this.imageCreationElapsedTime = new Long(totalElapsedTime);
   }

   void addSuccessfulImageSource(String name, long elapsedTime) {
      this.successfulImageSources.put(name, new Long(elapsedTime));
   }

   void addFailedImageSource(String name, Throwable th) {
      this.failedImageSources.put(name, th);
   }

   void captureCancelled() {
      this.captureCancelled = true;
   }

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal(partitionName, out);
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal((String)null, out);
   }

   private void createDiagnosticImageInternal(String partitionName, OutputStream out) throws ImageSourceCreationException {
      DescriptorManager dm = new DescriptorManager();
      Descriptor desc = dm.createDescriptorRoot(ImageSummaryBean.class);
      this.root = (ImageSummaryBean)desc.getRootBean();
      this.writeHeader(partitionName);
      this.writeImageSourceElapsedTimes();
      this.writeFailedImageSources();
      this.writeImageRequester();

      try {
         dm.writeDescriptorBeanAsXML((DescriptorBean)this.root, out);
      } catch (IOException var6) {
         throw new ImageSourceCreationException(var6);
      }
   }

   public void timeoutImageCreation() {
   }

   private void writeHeader(String partitionName) {
      this.root.setImageCaptureCancelled(this.captureCancelled);
      this.root.setImageCreationDate(this.imageCreationDate.toString());
      this.root.setImageCreationElapsedTime(this.imageCreationElapsedTime);
      this.root.setImageFileName(this.imageName);
      this.root.setMuxerClass(ManagementService.getRuntimeAccess(kernelId).getServer().getMuxerClass());
      this.root.setServerName(ManagementService.getRuntimeAccess(kernelId).getServer().getName());
      this.root.setServerReleaseInfo(version.getWebServerReleaseInfo());
      if (partitionName == null) {
         Iterator props = System.getProperties().entrySet().iterator();

         while(props.hasNext()) {
            Map.Entry entry = (Map.Entry)props.next();
            SystemPropertyBean prop = this.root.createSystemProperty();
            prop.setName(entry.getKey().toString());
            prop.setValue(entry.getValue().toString());
         }

      }
   }

   private void writeImageSourceElapsedTimes() {
      Set keys = this.successfulImageSources.keySet();
      Iterator i = keys.iterator();

      while(i.hasNext()) {
         String imageSource = (String)i.next();
         Long elapsedTime = (Long)this.successfulImageSources.get(imageSource);
         SuccessfulImageSourceBean bean = this.root.createSuccessfulImageSource();
         bean.setImageSource(imageSource);
         bean.setImageCaptureElapsedTime(elapsedTime);
      }

   }

   private void writeFailedImageSources() {
      Set keys = this.failedImageSources.keySet();
      Iterator i = keys.iterator();

      while(i.hasNext()) {
         String imageSource = (String)i.next();
         Throwable th = (Throwable)this.failedImageSources.get(imageSource);
         if (th != null) {
            String trace = StackTraceUtils.throwable2StackTrace(th);
            FailedImageSourceBean bean = this.root.createFailedImageSource();
            bean.setImageSource(imageSource);
            bean.setFailureExceptionStackTrace(trace);
         }
      }

   }

   private void writeImageRequester() {
      this.root.setRequesterThreadName(this.imageRequester.getRequesterThreadName());
      this.root.setRequesterUserId(this.imageRequester.getRequesterUserId());
      this.root.setRequestStackTrace(this.imageRequester.getRequester());
   }
}
