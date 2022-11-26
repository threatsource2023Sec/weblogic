package weblogic.application.internal.flow;

import java.security.AccessController;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.BindingsFactory;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.ModuleRegistryImpl;
import weblogic.application.naming.NamingConstants;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.common.ResourceException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.jndi.internal.ApplicationNamingInfo;
import weblogic.jndi.internal.ApplicationNamingNode;
import weblogic.jndi.internal.AuthenticatedNamingNode;
import weblogic.jndi.internal.NamingNode;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class EnvContextFlow extends BaseFlow {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static final String EAR_PERSISTENCE_UNIT_REGISTRY_CLASS_NAME = "weblogic.persistence.EarPersistenceUnitRegistry";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Environment envBuilder;
   private J2eeEnvironmentBean dd;
   private WeblogicEnvironmentBean wldd;
   private Context javaGlobalContext;

   public EnvContextFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      try {
         this.prepareNamingContexts();
         this.prepareBindingEnvironment();
      } catch (Exception var2) {
         throw new DeploymentException(var2);
      }
   }

   public void activate() throws DeploymentException {
      if (this.appCtx.isEar() && this.envBuilder != null) {
         try {
            PersistenceUnitRegistryProvider purProvider = new PersistenceUnitRegistryProvider() {
               public PersistenceUnitRegistry getPersistenceUnitRegistry() {
                  return (PersistenceUnitRegistry)EnvContextFlow.this.appCtx.getUserObject("weblogic.persistence.EarPersistenceUnitRegistry");
               }
            };
            this.envBuilder.bindEnvEntriesFromDDs(this.appCtx.getAppClassLoader(), purProvider);
         } catch (Exception var2) {
            throw new DeploymentException(var2);
         }
      }
   }

   public void deactivate() throws DeploymentException {
      if (this.appCtx.isEar() && this.envBuilder != null) {
         this.envBuilder.unbindEnvEntries();
      }
   }

   public void unprepare() throws DeploymentException {
      super.unprepare();
      this.unbindCompContextFromAppRootContext();
      if (this.appCtx.isEar() && this.envBuilder != null) {
         this.unregisterMessageDestinations();
         this.envBuilder.destroy();
         this.appCtx.removeUserObject(ModuleRegistry.class.getName());
      }
   }

   private void unregisterMessageDestinations() {
      ApplicationBean appBean = this.appCtx.getApplicationDD();
      WeblogicApplicationBean wlAppBean = this.appCtx.getWLApplicationDD();
      MessageDestinationDescriptorBean[] descs = wlAppBean == null ? new MessageDestinationDescriptorBean[0] : wlAppBean.getMessageDestinationDescriptors();
      EnvUtils.unregisterMessageDestinations(appBean.getMessageDestinations(), descs, this.envBuilder.getApplicationName(), this.envBuilder.getModuleId());
   }

   private void prepareNamingContexts() throws NamingException {
      ApplicationNamingInfo appNamingInfo = new ApplicationNamingInfo();
      Hashtable env = new Hashtable(2);
      env.put("weblogic.jndi.createIntermediateContexts", "true");
      env.put("weblogic.jndi.replicateBindings", "false");
      Context appRootContext = (new ApplicationNamingNode(appNamingInfo)).getContext(env);
      this.appCtx.setRootContext(appRootContext);
      this.javaGlobalContext = this.getJavaGlobalContext();
      appRootContext.bind("java:global", this.javaGlobalContext);
      Context defaultCtx = javaURLContextFactory.getDefaultContext(kernelId);
      Object comp = defaultCtx.lookup("comp");
      appRootContext.bind("comp", comp);
      Context appEnvContext = appRootContext.createSubcontext("app");
      this.appCtx.setEnvContext(appEnvContext);
      appEnvContext.bind("AppName", this.appCtx.getApplicationName());
      appEnvContext.bind(NamingConstants.WLInternalNS + '/' + "ApplicationId", this.appCtx.getApplicationId());
      NamingNode internalAppNode = new AuthenticatedNamingNode();
      Context internalAppCtx = internalAppNode.getContext(env);
      appRootContext.bind(NamingConstants.WLInternalNS, internalAppCtx);
      internalAppCtx.createSubcontext("app");
   }

   private void prepareBindingEnvironment() throws NamingException, ResourceException, EnvironmentException {
      if (this.appCtx.isEar()) {
         this.appCtx.putUserObject(ModuleRegistry.class.getName(), new ModuleRegistryImpl());
         ApplicationBean appBean = this.appCtx.getApplicationDD();
         WeblogicApplicationBean wlAppBean = this.appCtx.getWLApplicationDD();
         this.dd = appBean.convertToJ2eeEnvironmentBean();
         if (wlAppBean != null) {
            this.wldd = wlAppBean.convertToWeblogicEnvironmentBean();
         }

         this.envBuilder = BindingsFactory.getInstance().createAppEnvironment(this.appCtx.getApplicationId(), debugLogger, this.appCtx.getRootContext());
         this.appCtx.setEnvironment(this.envBuilder);
         this.envBuilder.contributeEnvEntries(this.dd, this.wldd, (AuthenticatedSubject)null);
         this.envBuilder.validateEnvEntries(this.appCtx.getAppClassLoader());
         this.registerMessageDestinations();
      }
   }

   private void registerMessageDestinations() throws EnvironmentException {
      ApplicationBean appBean = this.appCtx.getApplicationDD();
      WeblogicApplicationBean wlAppBean = this.appCtx.getWLApplicationDD();
      MessageDestinationDescriptorBean[] descs = wlAppBean == null ? new MessageDestinationDescriptorBean[0] : wlAppBean.getMessageDestinationDescriptors();
      EnvUtils.registerMessageDestinations(appBean.getMessageDestinations(), descs, this.envBuilder.getApplicationName(), this.envBuilder.getModuleId());
   }

   private void unbindCompContextFromAppRootContext() throws DeploymentException {
      try {
         Context appContext = this.appCtx.getRootContext();
         appContext.unbind("comp");
      } catch (NamingException var2) {
         throw new DeploymentException("unable to unbind comp from " + this.appCtx.getApplicationId(), var2);
      }
   }

   private Context getJavaGlobalContext() throws NamingException {
      weblogic.jndi.Environment env = new weblogic.jndi.Environment();
      env.setCreateIntermediateContexts(true);
      Context defaultContext = env.getInitialContext();

      Context javaGlobalCtx;
      try {
         javaGlobalCtx = (Context)defaultContext.lookup("java:global");
      } catch (NamingException var5) {
         javaGlobalCtx = defaultContext.createSubcontext("java:global");
      }

      return javaGlobalCtx;
   }
}
