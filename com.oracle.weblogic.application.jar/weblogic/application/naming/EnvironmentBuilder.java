package weblogic.application.naming;

import commonj.timers.TimerManager;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.ejb.EJBContext;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.UserTransaction;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;
import javax.xml.ws.WebServiceContext;
import org.omg.CORBA.ORB;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.env.bindings.DefaultBindings;
import weblogic.application.internal.FlowContext;
import weblogic.application.naming.jms.JMSContributor;
import weblogic.application.naming.jms.JMSContributorFactory;
import weblogic.common.ResourceException;
import weblogic.deployment.ServiceRefProcessor;
import weblogic.deployment.ServiceRefProcessorException;
import weblogic.deployment.ServiceRefProcessorFactory;
import weblogic.deployment.jms.PooledConnectionFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb20.internal.HandleDelegateImpl;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.MailSessionBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.jdbc.common.internal.DataSourceManager;
import weblogic.jdbc.common.internal.DataSourceService;
import weblogic.jndi.WLContext;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.jndi.internal.AuthenticatedNamingNode;
import weblogic.jndi.internal.JNDIHelper;
import weblogic.kernel.KernelStatus;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.persistence.PersistenceEnvReference;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;
import weblogic.utils.reflect.ReflectUtils;
import weblogic.validation.injection.ValidationManager;
import weblogic.work.ShutdownCallback;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.j2ee.J2EEWorkManager;
import weblogic.workarea.WorkContextHelper;

final class EnvironmentBuilder implements Environment {
   private static final String JMX_SUB_CTX_NAME = "jmx";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String COHERENCE_JNDI_OBJECT_FACTORY = "weblogic.cacheprovider.coherence.jndi.CoherenceObjectFactory";
   private static final String NAMING_URL_OBJECT_FACTORY = "weblogic.application.naming.URLObjectFactory";
   private static final String MAIL_SESSION_OBJECT_FACTORY = "weblogic.deployment.MailSessionObjectFactory";
   private static final String JMS_POOLED_CONNECTION_OBJECT_FACTORY = "weblogic.deployment.JmsPooledConnectionObjectFactory";
   private static final String PERSISTENCE_MANAGER_FACTORY_OBJECT_FACTORY = "weblogic.persistence.PersistenceManagerFactoryObjectFactory";
   private static final String PERSISTENCE_MANAGER_OBJECT_FACTORY = "weblogic.persistence.PersistenceManagerObjectFactory";
   private static final String MAIL_SESSION_REF_TYPE = "javax.mail.Session";
   private Context globalNamingRootCtx;
   private final Context rootContext;
   private Context javaGlobalCtx;
   private final Context javaAppCtx;
   private Context javaModuleCtx;
   private Context javaCompCtx;
   private Context javaCompEnvCtx;
   private Context wlInternalGlobalCtx;
   private Context wlsConnectorResRefCtx;
   private final String applicationName;
   private final String moduleName;
   private final String moduleId;
   private final String componentName;
   private final Environment.EnvType envType;
   private final DebugLogger debugLogger;
   private final Map ejbRefs = new HashMap();
   private final Set envRefs = new HashSet();
   private final List jmsRefs = new ArrayList();
   private final Set msgDestRefs = new HashSet();
   private final Set persistenceContextRefs = new HashSet();
   private final Set persistenceUnitRefs = new HashSet();
   private final List resourceRefs = new ArrayList();
   private final Set resourceEnvRefs = new HashSet();
   private final Set defaultRefs = new HashSet();
   private final List tmRefs = new ArrayList();
   private final List wmRefs = new ArrayList();
   private final Set dataSources = new HashSet();
   private final List administeredObjects = new ArrayList();
   private final List connectionFactoryResources = new ArrayList();
   private final Set mailSessions = new HashSet();
   private final Map serviceRefs = new HashMap();
   private final PortableJNDIBinder binder;
   private boolean isClient;
   private ErrorCollectionException errors = null;
   private final Map validatedEjbRefs = new HashMap();
   private final Map validatedEnvRefs = new HashMap();
   private final Map validatedMsgDestRefs = new HashMap();
   private final Map validatedPersistenceContextRefs = new HashMap();
   private final Map validatedPersistenceUnitRefs = new HashMap();
   private final ChangeTrackingCache validatedAllResourceRefs = new ChangeTrackingCache();
   private final Map validatedResourceEnvRefs = new HashMap();
   private final Map validatedDataSources = new HashMap();
   private final Map validatedServiceRefs = new HashMap();
   private final Map validatedInjectionTargets = new HashMap();
   private final List envEntries = new ArrayList();
   private final List defaultBindings = GlobalServiceLocator.getServiceLocator().getAllServices(DefaultBindings.class, new Annotation[0]);
   private JMSContributor jmsContributor = null;
   private final AdministeredObjectUtilityService raUtil = (AdministeredObjectUtilityService)GlobalServiceLocator.getServiceLocator().getService(AdministeredObjectUtilityService.class, new Annotation[0]);
   private HashMap mergedEnvEntries = new HashMap();
   private StringBuilder envEntryMergeErrors;
   private static Collection emptyModuleExtensions = Collections.emptyList();

   EnvironmentBuilder(Context rootCtx, String applicationName, String moduleName, String moduleId, String componentName, Environment.EnvType envType, DebugLogger logger, Context appNamingRootCtx, Context moduleNSCtx, boolean isClient) throws NamingException {
      this.isClient = isClient;
      this.initGlobalNodes();
      this.rootContext = rootCtx;
      this.applicationName = applicationName;
      this.envType = envType;
      this.debugLogger = logger;
      this.moduleName = moduleName;
      this.moduleId = moduleId;
      this.componentName = componentName;
      this.javaModuleCtx = moduleNSCtx;
      this.javaAppCtx = envType == Environment.EnvType.MANAGED_BEAN ? appNamingRootCtx : (Context)appNamingRootCtx.lookup("app");
      this.constructSubContextsUnderRoot();
      this.bindInternalSubContexts(appNamingRootCtx, moduleNSCtx);
      this.binder = (PortableJNDIBinder)(envType == Environment.EnvType.CLIENT ? new AppClientPortableJNDIBinder(this) : new GeneralPortableJNDIBinder(this));
      this.bindDefaults();
   }

   private void initGlobalNodes() throws NamingException {
      weblogic.jndi.Environment env = new weblogic.jndi.Environment();
      env.setCreateIntermediateContexts(true);
      this.globalNamingRootCtx = env.getInitialContext();
      this.javaGlobalCtx = (Context)this.globalNamingRootCtx.lookup("java:global");
      if (!this.isClient) {
         this.wlInternalGlobalCtx = (Context)this.globalNamingRootCtx.lookup(NamingConstants.InternalGlobalNS);
      }

   }

   private void bindInternalSubContexts(Context appNamingRootCtx, Context internalModuleNSCtx) throws NamingException {
      if (this.envType != Environment.EnvType.MANAGED_BEAN) {
         if (this.rootContext != appNamingRootCtx) {
            this.transferInternalAppCtxTo(appNamingRootCtx, this.rootContext);
         }

         if (!this.isClient) {
            this.rootContext.bind(NamingConstants.InternalGlobalNS, this.wlInternalGlobalCtx);
         }

         if (internalModuleNSCtx != null) {
            this.rootContext.bind(NamingConstants.INTERNAL_MODULE_NS, internalModuleNSCtx);
         }

         this.rootContext.createSubcontext(NamingConstants.INTERNAL_COMP_NS);
      }
   }

   private void transferInternalAppCtxTo(Context appNamingRootCtx, Context destContext) throws NamingException {
      destContext.bind(NamingConstants.WLInternalNS, new AuthenticatedNamingNode());
      WLContext internalRoot = null;

      try {
         internalRoot = (WLContext)appNamingRootCtx.lookup(NamingConstants.WLInternalNS);
      } catch (NameNotFoundException var8) {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         PrintWriter pw = new PrintWriter(baos);
         var8.printStackTrace(pw);
         pw.flush();
         throw new AssertionError("Unable to retrieve the internal java:app node from the jndi. This could occur because the EnvironmentBuilder was not initialized with  the appCtxRoot that was created in EnvContextFlow.prepare()." + baos);
      }

      try {
         destContext.bind(NamingConstants.INTERNAL_APP_NS, internalRoot.lookup("app", KERNEL_ID));
      } catch (RemoteException var7) {
         throw new AssertionError(var7);
      }
   }

   private void constructSubContextsUnderRoot() throws NamingException {
      if (this.supportOperationUnderRootContext()) {
         this.rootContext.createSubcontext("bea");
         this.rootContext.bind("bea/ModuleName", this.moduleId);
         if (this.envType != Environment.EnvType.MANAGED_BEAN) {
            this.javaModuleCtx.bind("ModuleName", this.moduleName);
         }

         if (!this.isClient) {
            this.rootContext.bind("global", this.javaGlobalCtx);
         }

         this.rootContext.bind("app", this.javaAppCtx);
         this.rootContext.bind("module", this.javaModuleCtx);
      }

      this.javaCompCtx = this.createSubcontext(this.rootContext, "comp");
      this.javaCompEnvCtx = this.createSubcontext(this.javaCompCtx, "env");
      this.createSubcontext(this.javaCompCtx, "_WL_internal");
   }

   private Context createSubcontext(Context rootCtx, String name) throws NamingException {
      try {
         return (Context)rootCtx.lookup(name);
      } catch (NamingException var4) {
         return rootCtx.createSubcontext(name);
      }
   }

   private void bindDefaults() throws NamingException {
      if (this.supportOperationUnderRootContext()) {
         JNDIHelper.createNonListableSubcontext(this.javaCompEnvCtx, "jmx");
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Injected Default Bindings size = " + this.defaultBindings.size());
         }

         Iterator var1 = this.defaultBindings.iterator();

         while(var1.hasNext()) {
            DefaultBindings defBindings = (DefaultBindings)var1.next();
            Iterator var3 = defBindings.getDefaultBindings().entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry binding = (Map.Entry)var3.next();
               if (this.debugLogger.isDebugEnabled()) {
                  this.debug("Adding Default Environment Binding " + binding.toString() + " has KEY " + (String)binding.getKey() + " and has VALUE " + binding.getValue());
               }

               this.binder.bind((String)binding.getKey(), binding.getValue());
               this.defaultRefs.add(binding.getKey());
            }
         }

         this.bindDefaultConcurrentManagedObjects();
         WorkContextHelper.bind(this.javaCompCtx);
         DisconnectMonitorListImpl.bindToJNDI(this.javaCompCtx);
      }

      if (KernelStatus.isServer()) {
         this.javaCompCtx.bind("HandleDelegate", new HandleDelegateImpl());
      }

      this.javaCompCtx.bind("InAppClientContainer", this.isClient);
   }

   private void bindDefaultConcurrentManagedObjects() throws NamingException {
      if (this.getApplicationContext() != null) {
         if (this.envType == Environment.EnvType.WEBAPP || this.envType == Environment.EnvType.EJB) {
            ConcurrentManagedObjectCollection collection = this.getApplicationContext().getConcurrentManagedObjectCollection();
            collection.bindDefaultConcurrentManagedObjects(this.javaCompCtx, this.moduleId, this.componentName, this.selectClassloader(), this.rootContext, true);
         }
      }
   }

   public Context getRootContext() {
      return this.rootContext;
   }

   public Context getCompEnvContext() {
      return this.javaCompEnvCtx;
   }

   public Context getCompContext() {
      return this.javaCompCtx;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getComponentName() {
      return this.componentName;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public boolean isEjbComponent() {
      return this.envType == Environment.EnvType.EJB;
   }

   public void destroy() {
      if (this.supportOperationUnderRootContext()) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("destroying environment");
         }

         this.ejbRefs.clear();

         try {
            this.rootContext.unbind("app");
            this.rootContext.unbind("java:global");
            if (this.envType != Environment.EnvType.APPLICATION) {
               this.rootContext.unbind("module");
            }

            this.unbindDefaults();
            if (this.envType != Environment.EnvType.MANAGED_BEAN) {
               this.rootContext.unbind(NamingConstants.InternalGlobalNS);
               this.rootContext.unbind(NamingConstants.INTERNAL_MODULE_NS);
               this.rootContext.unbind(NamingConstants.INTERNAL_COMP_NS);
               this.rootContext.unbind(NamingConstants.WLInternalNS);
            }
         } catch (NamingException var3) {
            J2EELogger.logErrorCleaningReferences(this.getApplicationName(), var3);
            if (this.debugLogger.isDebugEnabled()) {
               var3.printStackTrace();
            }
         }

         try {
            EnvUtils.destroyContextBindings(this.rootContext, this.debugLogger);
         } catch (NamingException var2) {
            J2EELogger.logErrorCleaningReferences(this.getApplicationName(), var2);
            if (this.debugLogger.isDebugEnabled()) {
               var2.printStackTrace();
            }
         }

      }
   }

   private void unbindDefaults() throws NamingException {
      if (this.javaCompCtx != null) {
         WorkContextHelper.unbind(this.javaCompCtx);
         DisconnectMonitorListImpl.unbindFromJNDI(this.javaCompCtx);
         this.unbindDefaultConcurrentManagedObjects();
      }
   }

   private void unbindDefaultConcurrentManagedObjects() throws NamingException {
      if (this.getApplicationContext() != null) {
         if (this.envType == Environment.EnvType.WEBAPP || this.envType == Environment.EnvType.EJB) {
            ConcurrentManagedObjectCollection collection = this.getApplicationContext().getConcurrentManagedObjectCollection();
            collection.unbindDefaultConcurrentManagedObjects(this.javaCompCtx, true);
         }
      }
   }

   public void bindValidation(List validationDescriptorURLs) throws NamingException, EnvironmentException {
      this.bindValidation((ValidationManager.ValidationBean)null, validationDescriptorURLs);
   }

   public void bindValidation(ValidationManager.ValidationBean validationBean, List validationDescriptorURLs) throws NamingException, EnvironmentException {
      try {
         if (validationBean == null) {
            ValidationManager.defaultInstance().bindValidation(this.javaCompCtx, validationDescriptorURLs);
         } else {
            ValidationManager.defaultInstance().bindValidation(this.javaCompCtx, validationDescriptorURLs, validationBean);
         }
      } catch (ValidationException var4) {
         throw new EnvironmentException(var4);
      } catch (Throwable var5) {
      }

   }

   public void unbindValidation() {
      ValidationManager.defaultInstance().unbindValidation(this.javaCompCtx);
   }

   private void bindEnvironmentEntries() throws NamingException, EnvironmentException {
      if (this.envEntryMergeErrors != null) {
         J2EELogger.logEnvEntryInconsistenciesDetected(this.envEntryMergeErrors.toString(), new RuntimeException("code path leading to bindEnvironmentEntries"));
      }

      ClassLoader moduleCL = this.selectClassloader();
      Iterator var2 = this.mergedEnvEntries.values().iterator();

      while(var2.hasNext()) {
         MergedEnvEntryBean env = (MergedEnvEntryBean)var2.next();
         env.reportIfMultiple();
         String name = env.getEnvEntryName();
         if (!this.envRefs.contains(name)) {
            if (env.getEnvEntryValue() != null) {
               this.bindEnvEntryBinding(name, EnvUtils.getValue(env, moduleCL));
            } else if (env.getLookupName() != null) {
               this.bindEnvEntryBinding(name, this.createLinkRef(env.getLookupName()));
            }
         }
      }

   }

   private ClassLoader selectClassloader() {
      ClassLoader moduleCL = null;
      ApplicationContextInternal appCtx = this.getApplicationContext();
      if (appCtx != null) {
         ModuleContext mc = appCtx.getModuleContext(this.moduleId);
         if (mc != null) {
            moduleCL = mc.getClassLoader();
         }
      }

      if (moduleCL == null) {
         moduleCL = Thread.currentThread().getContextClassLoader();
      }

      return (ClassLoader)moduleCL;
   }

   private void bindEnvEntryBinding(String name, Object value) throws NamingException {
      if (!this.resourceEnvRefs.contains(name) && !this.envRefs.contains(name) && !this.resourceRefs.contains(name)) {
         this.binder.bind(name, value);
         this.envRefs.add(name);
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("bound env-entry with name:" + name);
         }
      } else if (this.debugLogger.isDebugEnabled()) {
         this.debug("bound env-entry with name:" + name + " already bound from resourceEnvRefs/resourceRefs.");
      }

   }

   private void bindResourceReferences() throws NamingException, EnvironmentException {
      Iterator var1 = this.validatedAllResourceRefs.changedValues(false).iterator();

      while(var1.hasNext()) {
         EnvEntriesValidateHelper.ResourceRefInfo resInfo = (EnvEntriesValidateHelper.ResourceRefInfo)var1.next();
         this.bindResourceReference(resInfo.getBean(), resInfo.getDesBean(), resInfo.getJNDIName(), resInfo.getRunAsSubject());
      }

   }

   private void bindDataSources(DataSourceBean[] dataSourceBeans) throws NamingException, ResourceException {
      DataSourceService service = DataSourceManager.getInstance().getDataSourceService();
      DataSourceBean[] var3 = dataSourceBeans;
      int var4 = dataSourceBeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DataSourceBean dsb = var3[var5];
         String name = dsb.getName();
         if (!this.dataSources.contains(name)) {
            JDBCDataSourceBean jdbcDataSource = service.createJDBCDataSourceBean(dsb);
            DataSource rmiDataSource = service.createDataSource(jdbcDataSource, this.applicationName, this.moduleName, this.componentName);

            try {
               if (!name.startsWith("java:global") && !name.startsWith("java:app")) {
                  this.binder.bind(name, rmiDataSource);
               } else {
                  this.binder.bind(name, new DataSourceOpaqueReference((FlowContext)this.getApplicationContext(), rmiDataSource, dsb, this.applicationName, this.moduleName, this.componentName));
               }

               this.dataSources.add(name);
            } catch (NamingException var11) {
               this.destroyDataSource(name);
               throw var11;
            }
         }
      }

      this.bindDefaultDataSource();
   }

   private void unbindDataSources() {
      if (!this.dataSources.isEmpty()) {
         Iterator var1 = this.dataSources.iterator();

         while(var1.hasNext()) {
            String dataSource = (String)var1.next();

            try {
               this.binder.unbind(dataSource);
               this.destroyDataSource(dataSource);
            } catch (ResourceException | NamingException var4) {
               if (this.debugLogger.isDebugEnabled()) {
                  this.debugLogger.debug("Error unbinding data-source:", var4);
               }
            }
         }

      }
   }

   private void destroyDataSource(String name) throws ResourceException {
      DataSourceService dss = DataSourceManager.getInstance().getDataSourceService();
      dss.destroyDataSource(name, this.applicationName, this.moduleName, this.componentName);
   }

   private void bindDefaultDataSource() throws NamingException, ResourceException {
      if (this.javaCompCtx != null && !this.dataSources.contains("java:comp/DefaultDataSource")) {
         DataSourceBean dataSourceMetadata = DataSourceOpaqueReference.getDataSourceBeanForName(this.applicationName, this.componentName, "java:comp/DefaultDataSource");
         DataSourceOpaqueReference dataSourceOpaqueReference = new DataSourceOpaqueReference((FlowContext)this.getApplicationContext(), (DataSource)null, dataSourceMetadata, this.applicationName, this.moduleName, this.componentName);
         this.binder.bind("java:comp/DefaultDataSource", dataSourceOpaqueReference);
         this.dataSources.add("java:comp/DefaultDataSource");
      }

   }

   private void bindAdministeredObject(AdministeredObjectBean[] administeredObjectBeans) throws NamingException, javax.resource.ResourceException {
      if (administeredObjectBeans != null && administeredObjectBeans.length != 0) {
         if (this.getApplicationContext() != null) {
            AdministeredObjectBean[] var2 = administeredObjectBeans;
            int var3 = administeredObjectBeans.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               AdministeredObjectBean aob = var2[var4];
               String name = aob.getName();
               Reference ref = this.raUtil.createAdministeredObject(aob, this.moduleName, this.componentName, this.getApplicationContext().getApplicationId());
               if (ref != null) {
                  this.binder.bind(name, ref);
               }

               this.administeredObjects.add(aob);
            }

         }
      }
   }

   private void unbindAdministeredObject() {
      if (this.getApplicationContext() != null) {
         Iterator var1 = this.administeredObjects.iterator();

         while(var1.hasNext()) {
            AdministeredObjectBean aob = (AdministeredObjectBean)var1.next();
            String name = aob.getName();
            String raName = aob.getResourceAdapter();
            String appId = this.getApplicationContext().getApplicationId();

            try {
               Object handle = this.raUtil.revokeAdministeredObject(name, raName, this.moduleName, this.componentName, appId);
               if (handle != null) {
                  this.binder.unbind(name);
                  this.raUtil.destroyAdministeredObject(handle, name, raName, this.moduleName, this.componentName, appId);
               }
            } catch (javax.resource.ResourceException | NamingException var7) {
               if (this.debugLogger.isDebugEnabled()) {
                  this.debugLogger.debug("Error unbinding administered-object", var7);
               }
            }
         }

      }
   }

   private void bindConnectionFactoryResources(ConnectionFactoryResourceBean[] cfrbs) throws NamingException, javax.resource.ResourceException {
      if (cfrbs != null && cfrbs.length != 0) {
         if (this.getApplicationContext() != null) {
            ConnectionFactoryResourceBean[] var2 = cfrbs;
            int var3 = cfrbs.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               ConnectionFactoryResourceBean cfrb = var2[var4];
               String name = cfrb.getName();
               Reference ref = this.raUtil.createConnectionFactory(cfrb, this.moduleName, this.componentName, this.getApplicationContext().getApplicationId());
               if (ref != null) {
                  this.binder.bind(name, ref);
               }

               this.connectionFactoryResources.add(cfrb);
            }

         }
      }
   }

   private void unbindConnectionFactoryResources() {
      if (!this.connectionFactoryResources.isEmpty()) {
         if (this.getApplicationContext() != null) {
            Iterator var1 = this.connectionFactoryResources.iterator();

            while(var1.hasNext()) {
               ConnectionFactoryResourceBean cfrb = (ConnectionFactoryResourceBean)var1.next();
               String name = cfrb.getName();
               String raName = cfrb.getResourceAdapter();
               String appId = this.getApplicationContext().getApplicationId();

               try {
                  Object handle = this.raUtil.revokeConnectionFactory(name, raName, this.moduleName, this.componentName, appId);
                  if (handle != null) {
                     this.binder.unbind(name);
                     this.raUtil.destroyConnectionFactory(handle, name, raName, this.moduleName, this.componentName, appId);
                  }
               } catch (javax.resource.ResourceException | NamingException var7) {
                  if (this.debugLogger.isDebugEnabled()) {
                     this.debugLogger.debug("Error unbinding connection-factory", var7);
                  }
               }
            }

         }
      }
   }

   private void bindMailSessions(MailSessionBean[] mailSessionBeans) throws NamingException {
      if (mailSessionBeans != null && mailSessionBeans.length != 0) {
         MailSessionBean[] var2 = mailSessionBeans;
         int var3 = mailSessionBeans.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MailSessionBean msb = var2[var4];
            Properties props = new Properties();
            String name = msb.getName();
            if (!this.mailSessions.contains(name)) {
               JavaEEPropertyBean[] var8 = msb.getProperties();
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  JavaEEPropertyBean prop = var8[var10];
                  props.put(prop.getName(), prop.getValue());
               }

               String from = msb.getFrom();
               if (from != null && from.length() != 0) {
                  props.put("mail.from", from);
               }

               String host = msb.getHost();
               if (host != null && host.length() != 0) {
                  props.put("mail.host", host);
               }

               String user = msb.getUser();
               if (user != null && user.length() != 0) {
                  props.put("mail.user", user);
               }

               String storeProtocol = msb.getStoreProtocol();
               String transportProtocol;
               if (storeProtocol != null && storeProtocol.length() != 0) {
                  props.put("mail.store.protocol", storeProtocol);
                  transportProtocol = msb.getStoreProtocolClass();
                  if (transportProtocol != null && transportProtocol.length() != 0) {
                     props.put("mail." + storeProtocol + ".class", transportProtocol);
                  }
               }

               transportProtocol = msb.getTransportProtocol();
               if (transportProtocol != null && transportProtocol.length() != 0) {
                  props.put("mail.transport.protocol", transportProtocol);
                  String transportProtocolClass = msb.getTransportProtocolClass();
                  if (transportProtocolClass != null && transportProtocolClass.length() != 0) {
                     props.put("mail." + transportProtocol + ".class", transportProtocolClass);
                  }
               }

               this.binder.bind(name, new MailSessionReference(msb.getUser(), msb.getPassword(), props));
               this.mailSessions.add(name);
            }
         }

      }
   }

   private void unbindMailSessions() {
      if (!this.mailSessions.isEmpty()) {
         Iterator var1 = this.mailSessions.iterator();

         while(var1.hasNext()) {
            String mailSession = (String)var1.next();

            try {
               this.binder.unbind(mailSession);
            } catch (NamingException var4) {
               if (this.debugLogger.isDebugEnabled()) {
                  this.debugLogger.debug("Error unbinding mail-session:", var4);
               }
            }
         }

      }
   }

   private void bindResourceReference(ResourceRefBean resRef, ResourceDescriptionBean resourceDescription, String jndiName, AuthenticatedSubject runAsSubject) throws NamingException, EnvironmentException {
      String resRefName = resRef.getResRefName();
      String refType = resRef.getResType();
      if ("javax.sql.DataSource".equals(refType)) {
         this.bindDataSourceRef(resRef, resourceDescription, jndiName);
      } else if (!"javax.jms.QueueConnectionFactory".equals(refType) && !"javax.jms.TopicConnectionFactory".equals(refType) && !"javax.jms.XAQueueConnectionFactory".equals(refType) && !"javax.jms.XATopicConnectionFactory".equals(refType) && !"javax.jms.ConnectionFactory".equals(refType) && !"javax.jms.XAConnectionFactory".equals(refType)) {
         if ("java.net.URL".equals(refType)) {
            this.bindURLRef(resRef, jndiName);
         } else if ("commonj.work.WorkManager".equals(refType)) {
            this.bindResourceReference(resRef.getResRefName(), J2EEWorkManager.get(this.applicationName, this.moduleId, resRef.getResRefName()));
            this.wmRefs.add(resRef.getResRefName());
         } else if ("commonj.timers.TimerManager".equals(refType)) {
            this.bindTimerManager(resRef.getResRefName());
         } else if (!"org.omg.CORBA.ORB".equals(refType) && !"org.omg.CORBA_2_3.ORB".equals(refType)) {
            if (!"com.tangosol.net.NamedCache".equals(refType) && !"com.tangosol.net.Service".equals(refType)) {
               if ("javax.mail.Session".equals(refType)) {
                  this.bindMailSessionResourceRef(resRef, jndiName);
               } else {
                  this.bindResourceReference(resRefName, new LinkRef(jndiName));
                  this.bindConnectorContext(resRef, jndiName);
               }
            } else {
               String name = resRef.getResRefName();
               if (name == null || name.length() == 0) {
                  throw new EnvironmentException("ResourceReference has no name set");
               }

               String mappedName = resRef.getMappedName();
               if (mappedName == null || mappedName.length() == 0) {
                  throw new EnvironmentException("ResourceReference has no mappedName set");
               }

               this.bindCoherenceResourceRef(resRef, mappedName);
            }
         } else {
            this.bindResourceReference(resRef.getResRefName(), new LinkRef("java:comp/ORB"));
         }
      } else {
         this.bindJmsResourceRef(resRef, jndiName, runAsSubject);
      }

   }

   private void unbindTimerManagerRefs() {
      Iterator var1 = this.tmRefs.iterator();

      while(var1.hasNext()) {
         String name = (String)var1.next();
         this.removeTimerManagerRef(name);
      }

      this.tmRefs.clear();
   }

   private void removeTimerManagerRef(String name) {
      TimerManager tm = null;

      try {
         tm = (TimerManager)this.binder.lookup(name);
         this.binder.unbind(name);
      } catch (NamingException var7) {
         J2EELogger.logErrorCleaningReferences(this.getApplicationName(), var7);
         if (this.debugLogger.isDebugEnabled()) {
            var7.printStackTrace();
         }
      } finally {
         if (tm != null) {
            tm.stop();
         }

      }

   }

   private void bindTimerManager(String name) throws NamingException, EnvironmentException {
      if (name != null && name.length() != 0) {
         StringBuilder tmName = new StringBuilder();
         if (this.applicationName != null) {
            tmName.append(this.applicationName);
         }

         if (this.moduleId != null) {
            tmName.append("@" + this.moduleId);
         }

         if (this.componentName != null) {
            tmName.append("@" + this.componentName);
         }

         tmName.append("@" + name);
         this.binder.bind(name, TimerManagerFactory.getTimerManagerFactory().getCommonjTimerManager(tmName.toString(), this.getApplicationContext().getWorkManagerCollection().getDefault()));
         this.tmRefs.add(name);
      } else {
         throw new EnvironmentException("ResourceReference has no name set");
      }
   }

   private void unbindWorkManagerRefs() {
      Iterator var1 = this.wmRefs.iterator();

      while(var1.hasNext()) {
         String name = (String)var1.next();
         this.removeWorkManagerRef(name);
      }

      this.wmRefs.clear();
   }

   private void removeWorkManagerRef(String name) {
      J2EEWorkManager wm = null;

      try {
         wm = (J2EEWorkManager)this.binder.lookup(name);
         this.binder.unbind(name);
      } catch (NamingException var7) {
         J2EELogger.logErrorCleaningReferences(this.getApplicationName(), var7);
         if (this.debugLogger.isDebugEnabled()) {
            var7.printStackTrace();
         }
      } finally {
         if (wm != null) {
            wm.shutdown((ShutdownCallback)null);
         }

      }

   }

   private void bindMailSessionResourceRef(ResourceRefBean resRef, String jndiName) throws NamingException {
      if (!EnvUtils.isResourceShareable(resRef)) {
         EnvReference ref = new EnvReference(this, resRef.getResType(), "weblogic.deployment.MailSessionObjectFactory");
         ref.setResourceRefBean(resRef);
         ref.setJndiName(jndiName);
         this.bindResourceReference(resRef.getResRefName(), ref);
      } else {
         this.bindResourceReference(resRef.getResRefName(), this.createLinkRef(jndiName));
      }

   }

   private void bindJmsResourceRef(ResourceRefBean resRef, String jndiName, AuthenticatedSubject runAs) throws NamingException {
      EnvReference ref = new EnvReference(this, resRef.getResType(), "weblogic.deployment.JmsPooledConnectionObjectFactory");
      ref.setResourceRefBean(resRef);
      ref.setJndiName(jndiName);
      ref.setRunAs(runAs);
      this.binder.bind(resRef.getResRefName(), ref);
      this.bindConnectorContext(resRef, jndiName);
      String name = resRef.getResRefName();
      this.jmsRefs.add(name);
      if (this.debugLogger.isDebugEnabled()) {
         this.debug("bound jms resource-ref with name:" + name);
      }

   }

   private void unbindJmsResourceRefs() {
      Iterator var1 = this.jmsRefs.iterator();

      while(var1.hasNext()) {
         String name = (String)var1.next();

         try {
            Object obj = this.binder.lookup(name);
            if (obj != null) {
               ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
               boolean force = serverRuntime.getStateVal() == 18;
               ((PooledConnectionFactory)obj).close(force);
            }

            this.binder.unbind(name);
         } catch (JMSException var6) {
            J2EELogger.logErrorCleaningReferences(this.getApplicationName(), var6);
            if (this.debugLogger.isDebugEnabled()) {
               var6.printStackTrace();
            }
         } catch (NamingException var7) {
            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("Exception cleaning up JMS resource-ref", var7);
            }
         }
      }

      this.jmsRefs.clear();
   }

   private void bindCoherenceResourceRef(ResourceRefBean resRefBean, String jndiName) throws NamingException {
      EnvReference ref = new EnvReference(this, resRefBean.getResType(), "weblogic.cacheprovider.coherence.jndi.CoherenceObjectFactory");
      ref.setResourceRefBean(resRefBean);
      ref.setJndiName(jndiName);
      ref.setClassloader(this.selectClassloader());
      this.bindResourceReference(resRefBean.getResRefName(), ref);
   }

   private void bindResourceReference(String name, Object value) throws NamingException {
      if (!this.resourceEnvRefs.contains(name) && !this.resourceRefs.contains(name) && !this.envRefs.contains(name)) {
         this.binder.bind(name, value);
         this.resourceRefs.add(name);
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("bound resource-ref with name:" + name);
         }
      } else if (this.debugLogger.isDebugEnabled()) {
         this.debug("bound resource-ref with name:" + name + " already bound from resourceEnvRefs/EnvRefs.");
      }

   }

   private void bindURLRef(ResourceRefBean resRef, String jndiName) throws NamingException {
      boolean shareable = EnvUtils.isResourceShareable(resRef);

      Object obj;
      try {
         if (!shareable) {
            EnvReference ref = new EnvReference(this, resRef.getResType(), "weblogic.application.naming.URLObjectFactory");
            ref.setJndiName(jndiName);
            obj = ref;
         } else {
            obj = new URLReference(jndiName);
         }
      } catch (MalformedURLException var6) {
         obj = this.createLinkRef(jndiName);
      }

      this.bindResourceReference(resRef.getResRefName(), obj);
      this.bindConnectorContext(resRef, jndiName);
   }

   private void bindDataSourceRef(ResourceRefBean resRef, ResourceDescriptionBean resourceDescription, String jndiName) throws NamingException, EnvironmentException {
      if (resourceDescription == null || !jndiName.startsWith("java:global") && !jndiName.startsWith("java:app")) {
         Object o = null;

         try {
            o = this.javaAppCtx.lookup(jndiName);
         } catch (Exception var8) {
         }

         try {
            if (o == null) {
               o = this.javaAppCtx.lookup("jdbc/" + jndiName);
            }
         } catch (Exception var7) {
         }

         try {
            if (o == null) {
               o = InitialContext.doLookup(jndiName);
            }
         } catch (Exception var6) {
         }

         if (o != null) {
            this.bindResourceReference(resRef.getResRefName(), o);
         } else {
            this.bindResourceReference(resRef.getResRefName(), this.createLinkRef(jndiName));
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("Created link-ref to an unresolved DataSource at " + jndiName);
            }
         }

      } else {
         this.rewrapDataSourceRef(resRef, resourceDescription, jndiName);
      }
   }

   private void rewrapDataSourceRef(ResourceRefBean resRef, ResourceDescriptionBean resourceDescription, String jndiName) throws NamingException, EnvironmentException {
      if (this.debugLogger.isDebugEnabled()) {
         this.debug("Looking up DataSourceBean for DataSource at " + jndiName);
      }

      DataSourceBean dataSourceMetadata = DataSourceOpaqueReference.getDataSourceBeanForName(this.applicationName, this.componentName, jndiName);
      if (dataSourceMetadata == null) {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Created link-ref to an unresolved DataSource at " + jndiName);
         }

      } else {
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("Resetting the JNDI name for bean to " + resRef.getResRefName());
         }

         dataSourceMetadata.setName(resRef.getResRefName());
         if (resourceDescription.getDefaultResourcePrincipal() != null) {
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("Resetting the user and password for the bean to " + resourceDescription.getDefaultResourcePrincipal().getName() + " & " + resourceDescription.getDefaultResourcePrincipal().getPassword());
            }

            dataSourceMetadata.setUser(resourceDescription.getDefaultResourcePrincipal().getName());
            dataSourceMetadata.setPassword(resourceDescription.getDefaultResourcePrincipal().getPassword());
         }

         try {
            this.bindDataSources(new DataSourceBean[]{dataSourceMetadata});
         } catch (ResourceException var6) {
            throw new EnvironmentException(var6);
         }
      }
   }

   private void bindConnectorContext(ResourceRefBean resRefBean, String jndiName) throws NamingException {
      if (this.javaCompEnvCtx != null) {
         if (this.wlsConnectorResRefCtx == null) {
            this.wlsConnectorResRefCtx = this.javaCompEnvCtx.createSubcontext("wls-connector-resref");
         }

         this.wlsConnectorResRefCtx.bind(resRefBean.getResRefName() + "JNDI", jndiName);
         this.wlsConnectorResRefCtx.bind(resRefBean.getResRefName() + "Auth", resRefBean.getResAuth());
         this.wlsConnectorResRefCtx.bind(resRefBean.getResRefName() + "SharingScope", resRefBean.getResSharingScope());
      }
   }

   private void unbindConnectorContext(List names) {
      if (this.wlsConnectorResRefCtx != null) {
         Iterator var2 = names.iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();

            try {
               this.wlsConnectorResRefCtx.unbind(name + "JNDI");
               this.wlsConnectorResRefCtx.unbind(name + "Auth");
               this.wlsConnectorResRefCtx.unbind(name + "SharingScope");
            } catch (NamingException var5) {
            }
         }

      }
   }

   private void bindResourceEnvReferences(ResourceEnvRefBean[] resEnvRefs, Map jndiNames, ClassLoader moduleLoader) throws NamingException, EnvironmentException {
      ResourceEnvRefBean[] var4 = resEnvRefs;
      int var5 = resEnvRefs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ResourceEnvRefBean resEnvRef = var4[var6];
         String name = resEnvRef.getResourceEnvRefName();
         if (!this.resourceEnvRefs.contains(name) && !this.defaultRefs.contains(name)) {
            String jndiName = (String)jndiNames.get(resEnvRef.getResourceEnvRefName());
            jndiName = this.decideResEnvRefJNDIName(resEnvRef, jndiName, moduleLoader);
            if (jndiName == null || jndiName.length() == 0) {
               if (this.isEjbComponent()) {
                  Loggable l = J2EELogger.logNoJNDIForResourceEnvRefLoggable(resEnvRef.getResourceEnvRefName());
                  throw new EnvironmentException(l.getMessage());
               }

               J2EELogger.logNoJNDIForResourceEnvRef(resEnvRef.getResourceEnvRefName());
            }

            LinkRef value = this.createLinkRef(jndiName);
            this.binder.bind(name, value);
            this.resourceEnvRefs.add(name);
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("bound resource-env-ref with name:" + name + " value:" + value);
            }
         }
      }

   }

   private String fetchResourceEnvRefType(ResourceEnvRefBean refBean, ClassLoader moduleLoader) throws ClassNotFoundException {
      if (!StringUtils.isEmptyString(refBean.getResourceEnvRefType())) {
         return refBean.getResourceEnvRefType();
      } else {
         InjectionTargetBean[] targets = refBean.getInjectionTargets();
         if (targets != null && targets.length != 0) {
            if (moduleLoader == null) {
               moduleLoader = ClassLoader.getSystemClassLoader();
            }

            Class clazz = moduleLoader.loadClass(targets[0].getInjectionTargetClass());
            AccessibleObject ao = ReflectUtils.getMethodOrFieldForSetter(clazz, targets[0].getInjectionTargetName(), (Class)null);
            return ReflectUtils.getTypeOfSetter(ao).getName();
         } else {
            return null;
         }
      }
   }

   private void bindServiceReferences(ServiceRefBean[] serviceRefs, ServiceReferenceDescriptionBean[] wlserviceRefs, ServletContext sc, String uri) throws NamingException, EnvironmentException {
      Map wlMap = new HashMap();
      int var7;
      int var8;
      if (wlserviceRefs != null) {
         ServiceReferenceDescriptionBean[] var6 = wlserviceRefs;
         var7 = wlserviceRefs.length;

         for(var8 = 0; var8 < var7; ++var8) {
            ServiceReferenceDescriptionBean bean = var6[var8];
            wlMap.put(bean.getServiceRefName(), bean);
         }
      }

      ServiceRefBean[] var10 = serviceRefs;
      var7 = serviceRefs.length;

      for(var8 = 0; var8 < var7; ++var8) {
         ServiceRefBean sr = var10[var8];
         this.bindServiceRef(sr, (ServiceReferenceDescriptionBean)wlMap.get(sr.getServiceRefName()), sc, uri);
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("bound service-ref with name:" + sr.getServiceRefName());
         }
      }

   }

   private void unbindServiceReferences() {
      if (this.debugLogger.isDebugEnabled()) {
         this.debug("unbinding all service-refs");
      }

      Iterator var1 = this.serviceRefs.values().iterator();

      while(var1.hasNext()) {
         ServiceRefProcessor helper = (ServiceRefProcessor)var1.next();

         try {
            helper.unbindServiceRef(this.getCompEnvContext());
         } catch (NamingException var4) {
            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("Exception removing service-ref", var4);
            }
         }
      }

      this.serviceRefs.clear();
   }

   private void bindServiceRef(ServiceRefBean sr, ServiceReferenceDescriptionBean wlsr, ServletContext sc, String uri) throws NamingException, EnvironmentException {
      try {
         if (this.envType != Environment.EnvType.CLIENT) {
            if (!this.serviceRefs.containsKey(sr.getServiceRefName())) {
               ServiceRefProcessor helper = ServiceRefProcessorFactory.getInstance().getProcessor(sr, wlsr, sc);
               helper.bindServiceRef(this.getRootContext(), this.getCompEnvContext(), uri);
               this.serviceRefs.put(sr.getServiceRefName(), helper);
            }

         }
      } catch (ServiceRefProcessorException var6) {
         throw new EnvironmentException(var6);
      }
   }

   private void bindPersistenceContextRefs(PersistenceContextRefBean[] pcRefs, ClassLoader cl, PersistenceUnitRegistryProvider regProvider) throws EnvironmentException, NamingException {
      for(int i = 0; pcRefs != null && i < pcRefs.length; ++i) {
         String name = pcRefs[i].getPersistenceContextRefName();
         if (!this.persistenceContextRefs.contains(name)) {
            PersistenceEnvReference ref = new PersistenceEnvReference(this, "weblogic.persistence.PersistenceManagerObjectFactory", pcRefs[i], regProvider, cl);
            this.binder.bind(pcRefs[i].getPersistenceContextRefName(), ref);
            this.persistenceContextRefs.add(name);
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("bound persistence-context-ref with name:" + name);
            }
         }
      }

   }

   private void bindPersistenceUnitRefs(PersistenceUnitRefBean[] puRefs, ClassLoader cl, PersistenceUnitRegistryProvider regProvider) throws EnvironmentException, NamingException {
      for(int i = 0; puRefs != null && i < puRefs.length; ++i) {
         String name = puRefs[i].getPersistenceUnitRefName();
         if (!this.persistenceUnitRefs.contains(name)) {
            PersistenceEnvReference ref = new PersistenceEnvReference(this, "weblogic.persistence.PersistenceManagerFactoryObjectFactory", puRefs[i], regProvider, cl);
            this.binder.bind(puRefs[i].getPersistenceUnitRefName(), ref);
            this.persistenceUnitRefs.add(name);
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("bound persistence-unit-ref with name:" + name);
            }
         }
      }

   }

   private void bindJmsResourceDefinitions(J2eeClientEnvironmentBean dd) throws NamingException, javax.resource.ResourceException, ResourceException {
      if (dd.getJmsConnectionFactories().length > 0 || dd.getJmsDestinations().length > 0) {
         if (this.jmsContributor == null) {
            this.jmsContributor = ((JMSContributorFactory)GlobalServiceLocator.getServiceLocator().getService(JMSContributorFactory.class, new Annotation[0])).get(this.javaGlobalCtx, this.javaAppCtx, this.javaModuleCtx, this.javaCompCtx);
         }

         Set connectionFactoryResources = new HashSet();
         Set administeredObjects = new HashSet();
         this.jmsContributor.bindJMSResourceDefinitions(this.getApplicationContext(), dd, connectionFactoryResources, administeredObjects, this.getApplicationName(), this.getModuleId(), this.moduleName, this.getComponentName(), this.envType);
         if (!connectionFactoryResources.isEmpty()) {
            this.bindConnectionFactoryResources((ConnectionFactoryResourceBean[])connectionFactoryResources.toArray(new ConnectionFactoryResourceBean[0]));
         }

         if (!administeredObjects.isEmpty()) {
            this.bindAdministeredObject((AdministeredObjectBean[])administeredObjects.toArray(new AdministeredObjectBean[0]));
         }
      }

   }

   private void unbindJmsResourceDefinitions() {
      if (this.jmsContributor != null) {
         try {
            this.jmsContributor.unbindJMSResourceDefinitions(this.getApplicationContext());
         } catch (ResourceException var2) {
            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("Error unbinding JMS Resource Definition : ", var2);
            }
         }

      }
   }

   private void registerEJBRemoteReferences(EjbRefBean[] ejbRefs, Map jndiNames, String jarName) {
      EjbRefBean[] var4 = ejbRefs;
      int var5 = ejbRefs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EjbRefBean ejbRef = var4[var6];
         String refName = ejbRef.getEjbRefName();
         Object bindValue = this.decideEJBRefBindValue(refName, ejbRef.getEjbLink(), ejbRef.getHome(), ejbRef.getRemote(), ejbRef.getMappedName(), ejbRef.getLookupName(), false, (String)jndiNames.get(refName), jarName);
         this.putEJBRef(refName, bindValue);
      }

   }

   private void registerEJBLocalReferences(EjbLocalRefBean[] ejbRefs, Map jndiNames, String jarName) {
      EjbLocalRefBean[] var4 = ejbRefs;
      int var5 = ejbRefs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EjbLocalRefBean ejbLocalRef = var4[var6];
         String refName = ejbLocalRef.getEjbRefName();
         Object bindValue = this.decideEJBRefBindValue(refName, ejbLocalRef.getEjbLink(), ejbLocalRef.getLocalHome(), ejbLocalRef.getLocal(), ejbLocalRef.getMappedName(), ejbLocalRef.getLookupName(), true, (String)jndiNames.get(refName), jarName);
         this.putEJBRef(refName, bindValue);
      }

   }

   private void putEJBRef(String name, Object value) {
      if (value != null && !this.ejbRefs.containsKey(name)) {
         if (value instanceof ReferenceResolver) {
            this.registerReferenceResolver((ReferenceResolver)value);
         }

         this.ejbRefs.put(name, value);
      }

   }

   private Object decideEJBRefBindValue(String ejbRefName, String ejbLink, String home, String remoteOrLocal, String mappedName, String lookupName, boolean isLocal, String jndiName, String jarName) {
      Object bindValue = null;
      if (jndiName != null && jndiName.length() > 0) {
         bindValue = this.createLinkRef(jndiName);
      } else if (ejbLink == null && lookupName != null) {
         bindValue = this.createLinkRef(lookupName);
      } else if (ejbLink == null && mappedName != null) {
         if (mappedName.startsWith("weblogic-jndi:")) {
            jndiName = mappedName.substring("weblogic-jndi:".length(), mappedName.length());
         } else {
            jndiName = mappedName + "#" + remoteOrLocal;
         }

         bindValue = this.createLinkRef(jndiName);
      } else {
         bindValue = new EjbReferenceResolver(this.getApplicationContext().getApplicationId(), this.moduleId, jarName, ejbRefName, ejbLink, home, remoteOrLocal, isLocal);
      }

      return bindValue;
   }

   private void registerReferenceResolver(ReferenceResolver resolver) {
      ModuleRegistry mr;
      if (this.envType != Environment.EnvType.APPLICATION) {
         mr = this.getApplicationContext().getModuleContext(this.moduleId).getRegistry();
         mr.addReferenceResolver(resolver);
      } else {
         mr = (ModuleRegistry)this.getApplicationContext().getUserObject(ModuleRegistry.class.getName());
         mr.addReferenceResolver(resolver);
      }

   }

   private void bindEJBReferences(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean) throws NamingException {
      if (this.notAppClient(envBean)) {
         this.registerEJBReferences((J2eeEnvironmentBean)envBean, wlEnvBean, this.getModuleURI());
      } else {
         this.registerEJBRemoteReferences(envBean, wlEnvBean, this.getModuleURI());
      }

      Iterator var3 = this.ejbRefs.keySet().iterator();

      while(var3.hasNext()) {
         String ejbRefName = (String)var3.next();
         Object value = this.ejbRefs.get(ejbRefName);
         if (value instanceof ReferenceResolver) {
            value = ((ReferenceResolver)value).get();
         }

         this.binder.bind(ejbRefName, value);
         if (this.debugLogger.isDebugEnabled()) {
            this.debug("bound ejb ref with name:" + ejbRefName + "value:" + value);
         }
      }

   }

   private LinkRef createLinkRef(String jndiName) {
      return new LinkRef(EnvUtils.transformJNDIName(jndiName, this.applicationName));
   }

   private void unbind(String type, Collection refs) {
      this.unbindRefs(type, refs);
      refs.clear();
   }

   private void unbindRefs(String type, Collection refs) {
      Iterator var3 = refs.iterator();

      while(var3.hasNext()) {
         String name = (String)var3.next();

         try {
            this.binder.unbind(name);
            if (this.debugLogger.isDebugEnabled()) {
               this.debug("unbound " + type + " with name:" + name);
            }
         } catch (NamingException var6) {
            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("Error unbinding env-entry with name:" + name, var6);
            }
         }
      }

   }

   private ApplicationContextInternal getApplicationContext() {
      return ApplicationAccess.getApplicationAccess().getApplicationContext(this.applicationName);
   }

   private void debug(String msg) {
      String s = "Component env [" + this.applicationName + ":" + this.moduleName + ":" + this.componentName + "]";
      this.debugLogger.debug(s + " " + msg);
   }

   public Context getModuleNSContext() {
      return this.javaModuleCtx;
   }

   public Context getAppNSContext() {
      return this.javaAppCtx;
   }

   public Context getGlobalNSContext() {
      return this.javaGlobalCtx;
   }

   private boolean supportOperationUnderRootContext() {
      return this.envType != Environment.EnvType.APPLICATION && this.envType != Environment.EnvType.CLIENT;
   }

   private void mergeResourceReferences(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, AuthenticatedSubject runAsSubject) throws NamingException, EnvironmentException {
      if (envBean != null && envBean.getResourceRefs() != null) {
         new HashMap();
         if (wlEnvBean != null) {
            Map resourceDescriptions = this.asRDDMap(wlEnvBean.getResourceDescriptions(), this.applicationName);
         }

         this.validateResourceReferences(envBean.getResourceRefs(), wlEnvBean, runAsSubject);
      }

   }

   private void bindConcurrentObjects(ResourceEnvRefBean[] resEnvRefs, ResourceEnvDescriptionBean[] resourceEnvDescriptions, ClassLoader cl) throws NamingException {
      if (this.getApplicationContext() != null) {
         Map objectNames = new HashMap();
         int var6;
         int var7;
         String name;
         String resourceEnvRefType;
         if (resourceEnvDescriptions != null) {
            ResourceEnvDescriptionBean[] var5 = resourceEnvDescriptions;
            var6 = resourceEnvDescriptions.length;

            for(var7 = 0; var7 < var6; ++var7) {
               ResourceEnvDescriptionBean desc = var5[var7];
               name = desc.getResourceEnvRefName();
               resourceEnvRefType = desc.getResourceLink();
               objectNames.put(name, resourceEnvRefType);
            }
         }

         ResourceEnvRefBean[] var14 = resEnvRefs;
         var6 = resEnvRefs.length;

         for(var7 = 0; var7 < var6; ++var7) {
            ResourceEnvRefBean resEnvRef = var14[var7];
            name = resEnvRef.getResourceEnvRefName();
            if (!this.resourceEnvRefs.contains(name) && !this.defaultRefs.contains(name)) {
               resourceEnvRefType = resEnvRef.getResourceEnvRefType();
               String defaultCMOName = ConcurrentUtils.getDefaultJSR236CMOName(resourceEnvRefType);
               if (defaultCMOName != null) {
                  String objectName = (String)objectNames.get(name);
                  if (objectName == null) {
                     objectName = resEnvRef.getMappedName();
                  }

                  if (objectName != null) {
                     Object obj = this.getApplicationContext().getConcurrentManagedObjectCollection().getBindObject(resourceEnvRefType, this.moduleId, objectName, this.componentName, cl, this.rootContext);
                     if (obj != null) {
                        this.binder.bind(name, obj);
                        this.resourceEnvRefs.add(name);
                        if (this.debugLogger.isDebugEnabled()) {
                           this.debug("bound resource-env-ref with name:" + name + " ConcurrentManagedObject:" + obj);
                        }
                     } else {
                        obj = this.getApplicationContext().getConcurrentManagedObjectCollection().getBindObject(resourceEnvRefType, this.moduleId, defaultCMOName, this.componentName, cl, this.rootContext);
                        this.binder.bind(name, obj);
                        this.resourceEnvRefs.add(name);
                        if (this.debugLogger.isDebugEnabled()) {
                           this.debug("bound resource-env-ref with default name:" + name + " ConcurrentManagedObject:" + obj);
                        }
                     }
                  } else if (this.debugLogger.isDebugEnabled()) {
                     this.debug("will bind a LinkRef for resource-env-ref name:" + name + " objectName:" + objectName);
                  }
               }
            }
         }

      }
   }

   private void bindResourceEnvReferences(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, ClassLoader moduleLoader) throws NamingException, EnvironmentException {
      if (envBean != null) {
         ResourceEnvRefBean[] resEnvRefs = envBean.getResourceEnvRefs();
         if (resEnvRefs != null && resEnvRefs.length > 0) {
            ResourceEnvDescriptionBean[] resourceEnvDescriptions = null;
            Map jndiNames = null;
            if (wlEnvBean != null) {
               resourceEnvDescriptions = wlEnvBean.getResourceEnvDescriptions();
               jndiNames = EnvUtils.genResEnvDesJNDIMap(wlEnvBean.getResourceEnvDescriptions(), this.applicationName);
            } else {
               jndiNames = new HashMap();
            }

            this.bindConcurrentObjects(resEnvRefs, resourceEnvDescriptions, moduleLoader);
            this.bindResourceEnvReferences((ResourceEnvRefBean[])resEnvRefs, (Map)jndiNames, moduleLoader);
         }

      }
   }

   private void bindServiceReferences(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, ServletContext sc, String uri) throws NamingException, EnvironmentException {
      if (envBean != null && envBean.getServiceRefs() != null) {
         ServiceReferenceDescriptionBean[] wlserviceRefs = wlEnvBean == null ? new ServiceReferenceDescriptionBean[0] : wlEnvBean.getServiceReferenceDescriptions();
         this.bindServiceReferences(envBean.getServiceRefs(), wlserviceRefs, sc, uri);
      }

   }

   private void bindMessageDestinationReferences(J2eeClientEnvironmentBean envBean, String jarName) throws NamingException, EnvironmentException {
      if (envBean != null && envBean.getMessageDestinationRefs() != null) {
         MessageDestinationRefBean[] var3 = envBean.getMessageDestinationRefs();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MessageDestinationRefBean destRef = var3[var5];
            String name = destRef.getMessageDestinationRefName();
            if (!this.msgDestRefs.contains(name)) {
               if (destRef.getLookupName() != null) {
                  this.binder.bind(destRef.getMessageDestinationRefName(), this.createLinkRef(destRef.getLookupName()));
               } else {
                  this.binder.bind(destRef.getMessageDestinationRefName(), MessageDestinationReference.getBindable(this, destRef, jarName));
               }

               this.msgDestRefs.add(name);
               if (this.debugLogger.isDebugEnabled()) {
                  this.debug("bound message-destination-ref with name:" + name);
               }
            }
         }
      }

   }

   private void registerEJBReferences(J2eeEnvironmentBean envBean, WeblogicEnvironmentBean weblogicEnvBean, String jarName) {
      if (envBean != null && (envBean.getEjbLocalRefs().length > 0 || envBean.getEjbRefs().length > 0)) {
         Map jndiNames = this.genJNDIMap(weblogicEnvBean);
         if (envBean.getEjbRefs() != null) {
            this.registerEJBRemoteReferences(envBean.getEjbRefs(), jndiNames, jarName);
         }

         if (envBean.getEjbLocalRefs() != null && !this.isClient) {
            this.registerEJBLocalReferences(envBean.getEjbLocalRefs(), jndiNames, jarName);
         }
      }

   }

   private Map genJNDIMap(WeblogicEnvironmentBean wlEnvBean) {
      Map jndiMap = new HashMap();
      if (wlEnvBean == null) {
         return jndiMap;
      } else {
         EjbReferenceDescriptionBean[] var3 = wlEnvBean.getEjbReferenceDescriptions();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            EjbReferenceDescriptionBean desc = var3[var5];
            jndiMap.put(desc.getEjbRefName(), EnvUtils.transformJNDIName(desc.getJNDIName(), this.applicationName));
         }

         return jndiMap;
      }
   }

   private void registerEJBRemoteReferences(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, String jarName) {
      if (this.debugLogger.isDebugEnabled()) {
         Debug.assertion(this.ejbRefs != null);
      }

      if (envBean != null && envBean.getEjbRefs().length > 0) {
         this.registerEJBRemoteReferences(envBean.getEjbRefs(), this.genJNDIMap(wlEnvBean), jarName);
      }

   }

   private String decideResEnvRefJNDIName(ResourceEnvRefBean resEnvRef, String jndiName, ClassLoader moduleLoader) throws EnvironmentException {
      String resourceEnvRefType = null;
      String bindingName = EnvUtils.decideJNDIName(jndiName, resEnvRef.getLookupName(), resEnvRef.getMappedName());

      try {
         ClassLoader classLoader = moduleLoader;
         resourceEnvRefType = this.fetchResourceEnvRefType(resEnvRef, moduleLoader);
         if (bindingName == null && resourceEnvRefType != null) {
            if (moduleLoader == null) {
               classLoader = ClassLoader.getSystemClassLoader();
            }

            Class type = classLoader.loadClass(resourceEnvRefType);
            if (EJBContext.class.isAssignableFrom(type)) {
               bindingName = "java:comp/EJBContext";
            } else if (TimerService.class == type) {
               bindingName = "java:comp/TimerService";
            } else if (UserTransaction.class == type) {
               bindingName = "java:comp/UserTransaction";
            } else if (TransactionSynchronizationRegistry.class == type) {
               bindingName = "java:comp/TransactionSynchronizationRegistry";
            } else if (ORB.class.isAssignableFrom(type)) {
               bindingName = "java:comp/ORB";
            } else if (WebServiceContext.class == type) {
               bindingName = "java:comp/WebServiceContext";
            } else if (Validator.class.isAssignableFrom(type)) {
               bindingName = "java:comp/Validator";
            } else if (ValidatorFactory.class.isAssignableFrom(type)) {
               bindingName = "java:comp/ValidatorFactory";
            }

            if (bindingName == null) {
               bindingName = ConcurrentUtils.getDefaultJSR236ComponentJNDI(resEnvRef.getResourceEnvRefType());
            }

            if (bindingName == null) {
               bindingName = resEnvRef.getResourceEnvRefName();
            }
         }

         return bindingName;
      } catch (ClassNotFoundException var8) {
         throw new EnvironmentException("Unable to load class: " + resourceEnvRefType, var8);
      }
   }

   private String decideResRefJNDIName(String jndiName, String lookupName, String mappedName, String resRefName) {
      String result = EnvUtils.decideJNDIName(jndiName, lookupName, mappedName);
      if (result == null || result.length() == 0) {
         result = resRefName;
      }

      return result;
   }

   private void validate(J2eeEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, ClassLoader cl, String jarName, AuthenticatedSubject runAsSubject) throws EnvironmentException {
      this.validateEnvEntries(envBean, wlEnvBean, cl, jarName, runAsSubject);
      this.validateEJBLocalReferences(envBean.getEjbLocalRefs(), wlEnvBean, jarName);
      this.validatePersistenceContextRefs(envBean.getPersistenceContextRefs());
      this.throwEnvironmentException();
   }

   private void validateEnvEntries(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, ClassLoader cl, String jarName, AuthenticatedSubject runAsSubject) throws EnvironmentException {
      this.validateDataSources(envBean);
      this.validateEJBReferences(envBean.getEjbRefs(), wlEnvBean, jarName);
      this.validateEnvironmentEntries(envBean.getEnvEntries());
      this.validateMessageDestinationReferences(envBean.getMessageDestinationRefs());
      this.validatePersistenceUnitRefs(envBean.getPersistenceUnitRefs());
      this.validateResourceEnvReferences(envBean.getResourceEnvRefs(), wlEnvBean, cl);
      this.validateResourceReferences(envBean.getResourceRefs(), wlEnvBean, runAsSubject);
      this.validateServiceReferences(envBean.getServiceRefs(), wlEnvBean);
   }

   private void validateDataSources(J2eeClientEnvironmentBean envBean) {
      DataSourceBean[] var2 = envBean.getDataSources();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         DataSourceBean dataSourceBean = var2[var4];
         String name = dataSourceBean.getName();
         DataSourceBean previousDataSourceBean = (DataSourceBean)this.validatedDataSources.get(name);
         if (previousDataSourceBean != null) {
            if (EnvEntriesValidateHelper.areDataSourceBeansConflicting(dataSourceBean, previousDataSourceBean)) {
               if (envBean instanceof InterceptorBean) {
                  J2EELogger.logDatasourceOverwrittenWarning(name, ((InterceptorBean)envBean).getInterceptorClass(), this.getModuleId());
               } else {
                  this.addEnvironmentException("data-source", name);
               }
            }
         } else {
            this.validatedDataSources.put(name, dataSourceBean);
         }
      }

   }

   private void validateEJBReferences(EjbRefBean[] ejbRefs, WeblogicEnvironmentBean wlEnvBean, String jarName) {
      Map jndiMap = this.genJNDIMap(wlEnvBean);
      EjbRefBean[] var5 = ejbRefs;
      int var6 = ejbRefs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         EjbRefBean ejbRef = var5[var7];
         String refName = ejbRef.getEjbRefName();
         Object bindValue = this.decideEJBRefBindValue(refName, ejbRef.getEjbLink(), ejbRef.getHome(), ejbRef.getRemote(), ejbRef.getMappedName(), ejbRef.getLookupName(), false, (String)jndiMap.get(refName), jarName);
         this.validateEJBRef(refName, bindValue);
      }

   }

   private void validateEJBLocalReferences(EjbLocalRefBean[] ejbLocalRefs, WeblogicEnvironmentBean wlEnvBean, String jarName) {
      Map jndiMap = this.genJNDIMap(wlEnvBean);
      EjbLocalRefBean[] var5 = ejbLocalRefs;
      int var6 = ejbLocalRefs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         EjbLocalRefBean ejbLocalRef = var5[var7];
         String refName = ejbLocalRef.getEjbRefName();
         String jndiName = (String)jndiMap.get(refName);
         Object bindValue = this.decideEJBRefBindValue(refName, ejbLocalRef.getEjbLink(), ejbLocalRef.getLocalHome(), ejbLocalRef.getLocal(), ejbLocalRef.getMappedName(), ejbLocalRef.getLookupName(), true, jndiName, jarName);
         this.validateEJBRef(refName, bindValue);
      }

   }

   private void validateEJBRef(String refName, Object bindValue) {
      Object previousValue = this.validatedEjbRefs.get(refName);
      if (previousValue != null) {
         if (!previousValue.equals(bindValue)) {
            this.addEnvironmentException("ejb-ref/ejb-local-ref", refName);
         }
      } else {
         if (bindValue instanceof ReferenceResolver) {
            this.registerReferenceResolver((ReferenceResolver)bindValue);
         }

         this.validatedEjbRefs.put(refName, bindValue);
      }

   }

   private void validateEnvironmentEntries(EnvEntryBean[] envEntries) {
      EnvEntryBean[] var2 = envEntries;
      int var3 = envEntries.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnvEntryBean env = var2[var4];
         if (env.getLookupName() != null && env.getEnvEntryValue() != null) {
            this.addEnvironmentException(J2EELogger.logDuplicateEnvEntryValueLoggable(env.getEnvEntryName()).getMessage());
         }

         if (this.envType == Environment.EnvType.APPLICATION && !EnvEntriesValidateHelper.isJNDINameSpaceValid(env)) {
            this.addEnvironmentException(J2EELogger.logInvalidNameSpaceLoggable(env.getEnvEntryName()).getMessage());
         }

         InjectionTargetBean[] var6 = env.getInjectionTargets();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            InjectionTargetBean target = var6[var8];
            HashSet targetNames = (HashSet)this.validatedInjectionTargets.get(target.getInjectionTargetClass());
            if (targetNames != null) {
               if (!targetNames.contains(target.getInjectionTargetName())) {
                  targetNames.add(target.getInjectionTargetName());
               } else {
                  J2EELogger.logDuplicateInjectionTargetWarning(target.getInjectionTargetClass(), target.getInjectionTargetName());
               }
            } else {
               HashSet targetClass = new HashSet();
               targetClass.add(target.getInjectionTargetName());
               this.validatedInjectionTargets.put(target.getInjectionTargetClass(), targetClass);
            }
         }

         String name = env.getEnvEntryName();
         EnvEntryBean previousEnv = (EnvEntryBean)this.validatedEnvRefs.get(name);
         if (previousEnv != null) {
            if (EnvEntriesValidateHelper.areEnvEntryBeansConflicting(env, previousEnv)) {
               this.addEnvironmentException("env-entry", name);
            }
         } else {
            this.validatedEnvRefs.put(name, env);
         }
      }

   }

   private void addEnvironmentException(String element, String value) {
      this.addEnvironmentException(this.buildErrorMessage(element, value));
   }

   private void addEnvironmentException(String msg) {
      if (this.errors == null) {
         this.errors = new ErrorCollectionException();
      }

      this.errors.add(new EnvironmentException(msg));
   }

   private void throwEnvironmentException() throws EnvironmentException {
      if (this.errors != null && !this.errors.isEmpty()) {
         String msg = J2EELogger.logEnvEntriesValidationErrorsLoggable(this.getModuleId(), this.errors.getMessage()).getMessage();
         throw new EnvironmentException(msg);
      }
   }

   private String buildErrorMessage(String element, String value) {
      return J2EELogger.logConflictedEnvEntryLoggable(element, value).getMessage();
   }

   private void validateMessageDestinationReferences(MessageDestinationRefBean[] destRefs) {
      MessageDestinationRefBean[] var2 = destRefs;
      int var3 = destRefs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MessageDestinationRefBean destRef = var2[var4];
         String name = destRef.getMessageDestinationRefName();
         MessageDestinationRefBean preMessageDestinationRefBean = (MessageDestinationRefBean)this.validatedMsgDestRefs.get(name);
         if (preMessageDestinationRefBean != null) {
            if (EnvEntriesValidateHelper.areMessageDestinationRefBeansConflicting(destRef, preMessageDestinationRefBean)) {
               this.addEnvironmentException("message-destination-ref", name);
            }
         } else {
            this.validatedMsgDestRefs.put(name, destRef);
         }
      }

   }

   private void validatePersistenceUnitRefs(PersistenceUnitRefBean[] puRefs) {
      PersistenceUnitRefBean[] var2 = puRefs;
      int var3 = puRefs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PersistenceUnitRefBean puRef = var2[var4];
         String name = puRef.getPersistenceUnitRefName();
         PersistenceUnitRefBean pUCache = (PersistenceUnitRefBean)this.validatedPersistenceUnitRefs.get(name);
         if (pUCache != null) {
            if (EnvEntriesValidateHelper.arePersistenceUnitRefBeansConflicting(puRef, pUCache)) {
               this.addEnvironmentException("persistence-unit-ref", name);
            }
         } else {
            this.validatedPersistenceUnitRefs.put(name, puRef);
         }
      }

   }

   private void validatePersistenceContextRefs(PersistenceContextRefBean[] pcRefs) {
      PersistenceContextRefBean[] var2 = pcRefs;
      int var3 = pcRefs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PersistenceContextRefBean pcRef = var2[var4];
         String name = pcRef.getPersistenceContextRefName();
         PersistenceContextRefBean prePCRefBean = (PersistenceContextRefBean)this.validatedPersistenceContextRefs.get(name);
         if (prePCRefBean != null) {
            if (EnvEntriesValidateHelper.arePersistenceContextRefBeansConflicting(pcRef, prePCRefBean)) {
               this.addEnvironmentException("persistence-context-ref", name);
            }
         } else {
            this.validatedPersistenceContextRefs.put(name, pcRef);
         }
      }

   }

   private void validateResourceEnvReferences(ResourceEnvRefBean[] resEnvRefs, WeblogicEnvironmentBean wlEnvBean, ClassLoader cl) throws EnvironmentException {
      Map jndiNames = new HashMap();
      if (wlEnvBean != null) {
         jndiNames = EnvUtils.genResEnvDesJNDIMap(wlEnvBean.getResourceEnvDescriptions(), this.applicationName);
      }

      ResourceEnvRefBean[] var5 = resEnvRefs;
      int var6 = resEnvRefs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ResourceEnvRefBean resEnvRef = var5[var7];
         String jndiName = (String)((Map)jndiNames).get(resEnvRef.getResourceEnvRefName());
         jndiName = this.decideResEnvRefJNDIName(resEnvRef, jndiName, cl);
         if (jndiName == null || jndiName.length() == 0) {
            if (this.isEjbComponent()) {
               Loggable l = J2EELogger.logNoJNDIForResourceEnvRefLoggable(resEnvRef.getResourceEnvRefName());
               this.addEnvironmentException(l.getMessage());
               continue;
            }

            J2EELogger.logNoJNDIForResourceEnvRef(resEnvRef.getResourceEnvRefName());
         }

         this.registerAndValidateResourceEnvReference(resEnvRef, EnvUtils.transformJNDIName(jndiName, this.applicationName));
      }

   }

   private void registerAndValidateResourceEnvReference(ResourceEnvRefBean resEnvRef, String jndiName) {
      String refName = resEnvRef.getResourceEnvRefName();
      EnvEntriesValidateHelper.ResourceEnvRefInfo resourceEnvRefInfo = (EnvEntriesValidateHelper.ResourceEnvRefInfo)this.validatedResourceEnvRefs.get(refName);
      if (resourceEnvRefInfo != null) {
         if (EnvEntriesValidateHelper.areResourceEnvRefBeansConflicting(resEnvRef, jndiName, resourceEnvRefInfo)) {
            this.addEnvironmentException("resource-env-ref", refName);
         }
      } else {
         this.validatedResourceEnvRefs.put(refName, new EnvEntriesValidateHelper.ResourceEnvRefInfo(resEnvRef, jndiName));
      }

   }

   private void validateResourceReferences(ResourceRefBean[] resRefs, WeblogicEnvironmentBean wlEnvBean, AuthenticatedSubject runAsSubject) {
      Map resourceDescriptions = new HashMap();
      if (wlEnvBean != null) {
         resourceDescriptions = this.asRDDMap(wlEnvBean.getResourceDescriptions(), this.applicationName);
      }

      ResourceRefBean[] var5 = resRefs;
      int var6 = resRefs.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ResourceRefBean resRef = var5[var7];
         String resRefName = resRef.getResRefName();
         ResourceDescriptionData resourceDescriptionData = (ResourceDescriptionData)((Map)resourceDescriptions).get(resRefName);
         String jndiName = resourceDescriptionData != null ? resourceDescriptionData.getJNDIName() : null;
         jndiName = this.decideResRefJNDIName(jndiName, resRef.getLookupName(), resRef.getMappedName(), resRefName);
         if (jndiName != null && jndiName.length() != 0) {
            EnvEntriesValidateHelper.ResourceRefInfo resourceRefInfo = (EnvEntriesValidateHelper.ResourceRefInfo)this.validatedAllResourceRefs.get(resRefName);
            if (resourceRefInfo != null) {
               EnvEntriesValidateHelper.ResourceRefInfo newResourceRefInfo = resourceRefInfo.mergeResourceRefAndVerify(resourceDescriptionData, jndiName, resRefName, resRef);
               if (newResourceRefInfo == null) {
                  this.addEnvironmentException("resource-ref", resRefName);
               } else {
                  resourceRefInfo = newResourceRefInfo;
               }
            } else {
               resourceRefInfo = new EnvEntriesValidateHelper.ResourceRefInfo(resRef, jndiName, resourceDescriptionData, runAsSubject);
            }

            this.validatedAllResourceRefs.put(resRefName, resourceRefInfo);
         } else {
            Loggable l = J2EELogger.logNoJNDIForResourceRefLoggable(resRefName);
            this.addEnvironmentException(l.getMessage());
         }
      }

   }

   private void validateServiceReferences(ServiceRefBean[] serviceRefBeans, WeblogicEnvironmentBean wlEnvBean) {
      Map wlMap = new HashMap();
      int var5;
      int var6;
      if (wlEnvBean != null && wlEnvBean.getServiceReferenceDescriptions() != null) {
         ServiceReferenceDescriptionBean[] var4 = wlEnvBean.getServiceReferenceDescriptions();
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ServiceReferenceDescriptionBean bean = var4[var6];
            wlMap.put(bean.getServiceRefName(), bean);
         }
      }

      ServiceRefBean[] var12 = serviceRefBeans;
      var5 = serviceRefBeans.length;

      for(var6 = 0; var6 < var5; ++var6) {
         ServiceRefBean sr = var12[var6];
         String name = sr.getServiceRefName();
         ServiceReferenceDescriptionBean wlsr = (ServiceReferenceDescriptionBean)wlMap.get(sr.getServiceRefName());
         EnvEntriesValidateHelper.ServiceRefInfo serviceRefInfo = (EnvEntriesValidateHelper.ServiceRefInfo)this.validatedServiceRefs.get(name);
         if (serviceRefInfo != null) {
            ServiceRefBean previousBean = serviceRefInfo.getSr();
            if (EnvEntriesValidateHelper.isServiceRefBeanConflicting(sr, previousBean, wlsr, serviceRefInfo.getWlsr())) {
               this.addEnvironmentException("service-ref", name);
            }
         } else {
            this.validatedServiceRefs.put(name, new EnvEntriesValidateHelper.ServiceRefInfo(sr, wlsr));
         }
      }

   }

   public void contributeClientEnvEntries(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean) {
      this.contributeEnvEntries((J2eeClientEnvironmentBean)envBean, wlEnvBean, (AuthenticatedSubject)null);
   }

   public void contributeEnvEntries(J2eeEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, AuthenticatedSubject runAsSubject) {
      this.contributeEnvEntries((J2eeClientEnvironmentBean)envBean, wlEnvBean, runAsSubject);
   }

   private void contributeEnvEntries(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, AuthenticatedSubject runAsSubject) {
      this.envEntries.add(new EnvEntryRecord(envBean, wlEnvBean, runAsSubject));
      this.mergeEnvEntries(envBean.getEnvEntries());
   }

   private void mergeEnvEntries(EnvEntryBean[] entries) {
      EnvEntryBean[] var2 = entries;
      int var3 = entries.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EnvEntryBean envEntryBean = var2[var4];
         String name = envEntryBean.getEnvEntryName();
         MergedEnvEntryBean mergedEnvEntryBean = (MergedEnvEntryBean)this.mergedEnvEntries.get(name);
         if (mergedEnvEntryBean == null) {
            this.mergedEnvEntries.put(name, new MergedEnvEntryBean(this.debugLogger, this.envEntryMergeErrors, name, this.getApplicationName(), this.getModuleId(), this.getModuleExtensions(), envEntryBean));
         } else {
            this.envEntryMergeErrors = mergedEnvEntryBean.contribute(envEntryBean);
         }
      }

   }

   protected Collection getModuleExtensions() {
      FlowContext fc = (FlowContext)this.getApplicationContext();
      return fc == null ? emptyModuleExtensions : fc.getModuleExtensions(this.getModuleId());
   }

   public void validateEnvEntries(ClassLoader cl) throws EnvironmentException {
      Iterator var2;
      for(var2 = this.envEntries.iterator(); var2.hasNext(); this.throwEnvironmentException()) {
         EnvEntryRecord entry = (EnvEntryRecord)var2.next();
         if (this.notAppClient(entry.getEnvBean())) {
            this.validate((J2eeEnvironmentBean)entry.getEnvBean(), entry.getWlEnvBean(), cl, this.getModuleURI(), entry.getRunAsSubject());
         } else {
            this.validateEnvEntries(entry.getEnvBean(), entry.getWlEnvBean(), cl, this.getModuleURI(), entry.getRunAsSubject());
         }
      }

      var2 = this.validatedEjbRefs.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.putEJBRef((String)entry.getKey(), entry.getValue());
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("validateEnvEntries: " + this.applicationName + " " + this.moduleId + " " + this.componentName + " envtype:" + this.envType);
      }

   }

   private boolean notAppClient(J2eeClientEnvironmentBean envBean) {
      return envBean instanceof J2eeEnvironmentBean && !this.isClient;
   }

   public void unbindEnvEntries() {
      this.unbindAdministeredObject();
      this.unbindConnectionFactoryResources();
      this.unbindDataSources();
      this.unbindMailSessions();
      this.unbindJmsResourceDefinitions();
      this.unbindRefs("ejb-ref/ejb-local-ref", this.ejbRefs.keySet());
      this.unbind("env-entry", this.envRefs);
      this.unbind("message-destination-ref", this.msgDestRefs);
      this.unbind("persistence-context-ref", this.persistenceContextRefs);
      this.unbind("persistence-unit-ref", this.persistenceUnitRefs);
      this.unbind("resource-env-ref", this.resourceEnvRefs);
      this.unbindConnectorContext(this.resourceRefs);
      this.unbindConnectorContext(this.jmsRefs);
      this.unbindTimerManagerRefs();
      this.unbindWorkManagerRefs();
      this.unbindJmsResourceRefs();
      this.unbind("resource-ref", this.resourceRefs);
      this.validatedAllResourceRefs.clear();
      this.unbindServiceReferences();
      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("unbindEnvEntries: " + this.applicationName + " " + this.moduleId + " " + this.componentName + " envtype:" + this.envType);
      }

   }

   public void bindEnvEntriesFromDDs(ClassLoader cl, PersistenceUnitRegistryProvider purProvider) throws NamingException, EnvironmentException {
      this.bindEnvEntriesFromDDs(cl, purProvider, (ServletContext)null);
   }

   public void bindEnvEntriesFromDDs(ClassLoader cl, PersistenceUnitRegistryProvider purProvider, ServletContext sc) throws NamingException, EnvironmentException {
      javaURLContextFactory.pushContext(this.getRootContext());

      try {
         Iterator var4 = this.envEntries.iterator();

         while(true) {
            if (!var4.hasNext()) {
               this.bindEnvironmentEntries();
               this.bindResourceReferences();
               if (this.debugLogger.isDebugEnabled()) {
                  this.debug(EnvUtils.dumpContext(this.rootContext));
               }
               break;
            }

            EnvEntryRecord entry = (EnvEntryRecord)var4.next();
            J2eeClientEnvironmentBean dd = entry.getEnvBean();
            WeblogicEnvironmentBean wldd = entry.getWlEnvBean();
            this.bindAdministeredObject(dd.getAdministeredObjects());
            this.bindConnectionFactoryResources(dd.getConnectionFactories());
            this.bindJmsResourceDefinitions(dd);
            this.bindDataSources(dd.getDataSources());
            this.bindEJBReferences(dd, wldd);
            this.bindMailSessions(dd.getMailSessions());
            this.bindMessageDestinationReferences(dd, this.moduleId);
            this.bindPersistenceUnitRefs(dd.getPersistenceUnitRefs(), cl, purProvider);
            this.bindResourceEnvReferences(dd, wldd, cl);
            this.mergeResourceReferences(dd, wldd, entry.getRunAsSubject());
            this.bindServiceReferences(dd, wldd, sc, this.moduleId);
            if (this.notAppClient(dd)) {
               this.bindPersistenceContextRefs(((J2eeEnvironmentBean)dd).getPersistenceContextRefs(), cl, purProvider);
            }
         }
      } catch (javax.resource.ResourceException | ResourceException var11) {
         throw new EnvironmentException(var11);
      } finally {
         javaURLContextFactory.popContext();
      }

      if (this.debugLogger.isDebugEnabled()) {
         this.debugLogger.debug("bindEnvEntriesFromDDs: " + this.applicationName + " " + this.moduleId + " " + this.componentName + " envtype:" + this.envType);
      }

   }

   private String getModuleURI() {
      String moduleURI = null;
      ApplicationContextInternal appCtx = this.getApplicationContext();
      if (appCtx != null) {
         ModuleContext modCtx = appCtx.getModuleContext(this.moduleId);
         if (modCtx != null) {
            moduleURI = modCtx.getURI();
         }
      }

      return moduleURI;
   }

   private Map asRDDMap(ResourceDescriptionBean[] descs, String appName) {
      if (descs == null) {
         return Collections.emptyMap();
      } else {
         Map resourceDescriptions = new HashMap();
         ResourceDescriptionBean[] var4 = descs;
         int var5 = descs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ResourceDescriptionBean desc = var4[var6];
            String jndiName = EnvUtils.decideJndiName(desc.getJNDIName(), desc.getResourceLink(), appName);
            resourceDescriptions.put(desc.getResRefName(), new ResourceDescriptionData(jndiName, desc));
         }

         return resourceDescriptions;
      }
   }

   static final class ResourceDescriptionData {
      private final ResourceDescriptionBean rdb;
      private final String jndiName;

      ResourceDescriptionData(String jndiName, ResourceDescriptionBean rdb) {
         this.jndiName = jndiName;
         this.rdb = rdb;
      }

      String getJNDIName() {
         return this.jndiName;
      }

      ResourceDescriptionBean getResourceDescription() {
         return this.rdb;
      }
   }

   private static final class EnvEntryRecord {
      private final J2eeClientEnvironmentBean envBean;
      private final WeblogicEnvironmentBean wlEnvBean;
      private final AuthenticatedSubject runAsSubject;

      EnvEntryRecord(J2eeClientEnvironmentBean envBean, WeblogicEnvironmentBean wlEnvBean, AuthenticatedSubject runAsSubject) {
         this.envBean = envBean;
         this.wlEnvBean = wlEnvBean;
         this.runAsSubject = runAsSubject;
      }

      AuthenticatedSubject getRunAsSubject() {
         return this.runAsSubject;
      }

      J2eeClientEnvironmentBean getEnvBean() {
         return this.envBean;
      }

      WeblogicEnvironmentBean getWlEnvBean() {
         return this.wlEnvBean;
      }
   }
}
