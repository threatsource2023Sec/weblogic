package weblogic.diagnostics.integration;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Map;
import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationCallback;
import weblogic.diagnostics.context.CorrelationIntegrationManager;
import weblogic.diagnostics.image.ImageAlreadyCapturedException;
import weblogic.diagnostics.image.ImageCaptureFailedException;
import weblogic.diagnostics.image.ImageCaptureTimeoutException;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.InvalidDestinationDirectoryException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;

class DiagnosticIntegrationManagerImpl implements DiagnosticIntegrationManager {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DiagnosticIntegrationManager SINGLETON = null;
   private CorrelationIntegrationManager corrModifier = null;

   private DiagnosticIntegrationManagerImpl() {
      this.corrModifier = CorrelationIntegrationManager.Factory.getInstance();
   }

   static synchronized DiagnosticIntegrationManager getInstance() {
      if (!SecurityServiceManager.isKernelIdentity(SecurityServiceManager.getCurrentSubject(KERNEL_ID))) {
         return null;
      } else {
         if (SINGLETON == null) {
            SINGLETON = new DiagnosticIntegrationManagerImpl();
         }

         return SINGLETON;
      }
   }

   public void clearCorrelation() {
      this.corrModifier.clearCorrelation();
   }

   public Correlation findCorrelation() {
      return this.corrModifier.findCorrelation();
   }

   public Correlation findOrCreateCorrelation() {
      return this.corrModifier.findOrCreateCorrelation();
   }

   public void setCorrelationEnabled(boolean enabled) {
      this.corrModifier.setCorrelationEnabled(enabled);
   }

   public void setDMSCorrelationCallback(CorrelationCallback callback) {
      this.corrModifier.setDMSCorrelationCallback(callback);
   }

   public Correlation newCorrelation() {
      return this.corrModifier.newCorrelation();
   }

   public Correlation newCorrelation(String ecid, int[] ridComponents, int kidCount, Map values, long dyeVector, boolean inheritable) {
      return this.corrModifier.newCorrelation(ecid, ridComponents, kidCount, values, dyeVector, inheritable);
   }

   public void activateCorrelation(Correlation correlation) {
      this.corrModifier.activateCorrelation(correlation);
   }

   public String captureImage(String destination, boolean resetLockout, long timeoutMillis) throws InvalidDestinationDirectoryException, ImageCaptureFailedException, ImageCaptureTimeoutException, ImageAlreadyCapturedException {
      ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      if (imageManager == null) {
         throw new ImageCaptureFailedException("ImageManager is not available");
      } else {
         return imageManager.captureImage((String)null, (String)null, destination, resetLockout, timeoutMillis);
      }
   }
}
