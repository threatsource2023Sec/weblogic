package weblogic.application.internal.flow;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.Module;
import weblogic.application.ready.ReadyLifecycleManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.DeploymentException;

public final class AvailabilityRegistrationFlow extends BaseFlow {
   protected static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugReadyApp");

   public AvailabilityRegistrationFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   private boolean isReadyAppEnabled() throws DeploymentException {
      boolean readyAppEnabled = false;
      if (this.appCtx.isEar()) {
         if (this.appCtx.getApplicationDescriptor() != null) {
            ApplicationDescriptor ad = this.appCtx.getApplicationDescriptor();

            try {
               WeblogicApplicationBean wad = ad.getWeblogicApplicationDescriptor();
               if (wad != null && wad.getReadyRegistration() != null && "true".equals(wad.getReadyRegistration().toLowerCase())) {
                  readyAppEnabled = true;
               }
            } catch (IOException var6) {
               throw new DeploymentException(var6);
            } catch (XMLStreamException var7) {
               throw new DeploymentException(var7);
            }
         }
      } else {
         Module[] modules = this.appCtx.getApplicationModules();

         for(int i = 0; i < modules.length; ++i) {
            if (modules[i] != null && modules[i].getDescriptors() != null && modules[i].getDescriptors().length > 0) {
               for(int j = 0; j < modules[i].getDescriptors().length; ++j) {
                  if (modules[i].getDescriptors()[j] instanceof WeblogicWebAppBean) {
                     WeblogicWebAppBean wab = (WeblogicWebAppBean)modules[i].getDescriptors()[j];
                     if (wab.getReadyRegistration() != null && "true".equals(wab.getReadyRegistration().toLowerCase())) {
                        readyAppEnabled = true;
                     }
                  }
               }
            }
         }
      }

      LOGGER.debug("ReadyApp flag for " + this.appCtx.getApplicationId() + " = " + readyAppEnabled);
      return readyAppEnabled;
   }

   public void prepare() throws DeploymentException {
      if (this.isReadyAppEnabled()) {
         String applicationId = this.appCtx.getApplicationId();
         LOGGER.debug("Calling Ready App register method for applicationId " + applicationId);
         ReadyLifecycleManager.getInstance().register(applicationId);
      }

   }

   public void activate() {
      LOGGER.debug("=======> Activate called");
   }

   public void start(String[] uris) {
      LOGGER.debug("=======> Start called");
   }

   public void stop(String[] uris) {
      LOGGER.debug("=======> Stop called");
   }

   public void deactivate() {
      LOGGER.debug("=======> Deactivate called");

      try {
         if (this.isReadyAppEnabled()) {
            LOGGER.debug("Calling not ready method for " + this.appCtx.getApplicationId());
            ReadyLifecycleManager.getInstance().notReady();
         }
      } catch (DeploymentException var2) {
         LOGGER.debug("Exception occured durring attemp to read deployment descriptor");
      }

   }

   public void unprepare() {
      String applicationId = this.appCtx.getApplicationId();

      try {
         if (this.isReadyAppEnabled()) {
            LOGGER.debug("Calling Ready App unregister method for applicationId " + applicationId);
            ReadyLifecycleManager.getInstance().unregister(applicationId);
         }
      } catch (DeploymentException var3) {
         LOGGER.debug("Attempt to read deployment descriptor for unprepare failed, calling ReadyApp unregister just in case");
         ReadyLifecycleManager.getInstance().unregister(applicationId);
      }

   }

   public void prepareUpdate(String[] uris) {
      LOGGER.debug("=======> PrepareUpdate called");
   }

   public void activateUpdate(String[] uris) {
      LOGGER.debug("=======> ActivateUpdate called");
   }

   public void rollbackUpdate(String[] uris) {
      LOGGER.debug("=======> RollbackUpdate called");
   }
}
