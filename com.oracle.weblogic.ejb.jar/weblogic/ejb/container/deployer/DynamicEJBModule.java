package weblogic.ejb.container.deployer;

import java.security.AccessController;
import javax.naming.NamingException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleException;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

public final class DynamicEJBModule extends EJBModule implements weblogic.ejb.spi.DynamicEJBModule {
   private static final DebugLogger debugLogger;
   private static final AuthenticatedSubject KERNEL_ID;
   private final String origModuleId;
   private EjbDescriptorBean ejbDescriptor;
   private static final int STATE_NEW = 1;
   private static final int STATE_INITIALIZED = 2;
   private static final int STATE_PREPARED = 4;
   private static final int STATE_ADMIN = 8;
   private static final int STATE_ACTIVE = 16;
   private int state = 1;

   public DynamicEJBModule(String origModuleId, String uniqueId, ApplicationContextInternal ctx) {
      super(origModuleId + "_" + uniqueId + "_dynamic_internal", (CoherenceService)null);
      this.appCtx = ctx;
      this.origModuleId = origModuleId;
   }

   private static void debug(String s) {
      debugLogger.debug("[DynamicEJBModule] " + s);
   }

   private void pushRunAsSubject(AuthenticatedSubject runAsSubject) {
      SecurityManager.pushSubject(KERNEL_ID, runAsSubject);
   }

   private void popRunAsSubject() {
      SecurityManager.popSubject(KERNEL_ID);
   }

   public void setEjbDescriptorBean(EjbDescriptorBean ejbDescriptorBean) {
      this.ejbDescriptor = ejbDescriptorBean;
   }

   protected ModuleContext getModuleContext() {
      return this.appCtx.getModuleContext(this.origModuleId);
   }

   protected ModuleRegistry getRegistry() {
      return null;
   }

   protected void initializeAutoRefLibraries() {
   }

   protected void processAnnotations() {
   }

   public ModuleExtensionContext getModuleExtensionContext() {
      return null;
   }

   protected EjbDescriptorBean getEJBDescriptor() {
      return this.ejbDescriptor;
   }

   public String getName() {
      return this.getId();
   }

   protected void setupPersistenceUnitRegistry() {
   }

   protected boolean isCDIEnabled() {
      return false;
   }

   public boolean deployDynamicEJB() {
      boolean var2;
      try {
         this.pushRunAsSubject(KERNEL_ID);
         this.init();
         this.state = 2;
         this.prepare();
         this.state = 4;
         this.activate();
         boolean var1 = true;
         return var1;
      } catch (ModuleException var6) {
         debug("dynamic deployment of " + this.getURI() + " failed with exception: " + var6);
         if (debugLogger.isDebugEnabled()) {
            var6.getNestedException().printStackTrace();
         }

         this.undeployDynamicEJB();
         var2 = false;
      } finally {
         this.popRunAsSubject();
      }

      return var2;
   }

   public boolean startDynamicEJB() {
      boolean var2;
      try {
         this.pushRunAsSubject(KERNEL_ID);
         this.start();
         this.state = 8;
         this.adminToProduction();
         this.state = 16;
         boolean var1 = true;
         return var1;
      } catch (ModuleException var6) {
         debug("dynamic deployment of " + this.getURI() + " failed with exception: " + var6);
         if (debugLogger.isDebugEnabled()) {
            var6.getNestedException().printStackTrace();
         }

         this.undeployDynamicEJB();
         var2 = false;
      } finally {
         this.popRunAsSubject();
      }

      return var2;
   }

   public void undeployDynamicEJB() {
      this.pushRunAsSubject(KERNEL_ID);
      if (this.state >= 16) {
         this.forceProductionToAdmin();
      }

      if (this.state >= 8) {
         try {
            this.deactivate();
         } catch (ModuleException var4) {
            if (debugLogger.isDebugEnabled()) {
               debug("Ignoring " + var4);
            }
         }
      }

      if (this.state >= 4) {
         try {
            this.unprepare();
            this.unregisterMBeans();
         } catch (ModuleException var3) {
            if (debugLogger.isDebugEnabled()) {
               debug("Ignoring " + var3);
            }
         }
      }

      try {
         this.remove();
      } catch (ModuleException var2) {
         if (debugLogger.isDebugEnabled()) {
            debug("Ignoring " + var2);
         }
      }

      this.state = 1;
      this.popRunAsSubject();
   }

   protected void setupCoherenceCaches(WeblogicEjbJarBean wlJar) {
   }

   protected void releaseCoherenceCaches() {
   }

   public boolean isMetadataComplete() {
      return true;
   }

   private void init() throws ModuleException {
      if (this.appCtx == null) {
         this.appCtx = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
      }

      try {
         this.appCtx.getEnvContext().createSubcontext("ejb");
      } catch (NamingException var2) {
      }

      this.classLoader = this.appCtx.getAppClassLoader();
      this.initMBeans();
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
