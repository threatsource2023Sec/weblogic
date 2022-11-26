package weblogic.persistence;

import com.oracle.injection.InjectionContainer;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.PersistenceUnitParent;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryProvider;
import weblogic.application.naming.EnvironmentException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.PersistencePropertyBean;
import weblogic.j2ee.descriptor.PersistenceUnitBean;
import weblogic.kernel.Kernel;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.persistence.spi.JPAIntegrationProvider;
import weblogic.persistence.spi.JPAIntegrationProviderFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.ClassPreProcessor;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.debug.ClassLoaderDebugger;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.utils.reflect.DynamicProxyUtils;

public class BasePersistenceUnitInfo implements PersistenceUnitInfo {
   public static final DebugLogger DEBUG;
   private static final DebugLogger vDebugLogger;
   private static final String JTA_DATA_SOURCE_PROP_KEY = "javax.persistence.jtaDataSource";
   private static final String NON_JTA_DATA_SOURCE_PROP_KEY = "javax.persistence.nonJtaDataSource";
   private static final String ECLIPSELINK_LOGGER_PROP_KEY = "eclipselink.logging.logger";
   private static final String ECLIPSELINK_TARGET_SERVER_PROP_KEY = "eclipselink.target-server";
   private static final String APPLICATION_ID_PROP_KEY = "weblogic.application-id";
   private static final String ECLIPSELINK_TUNING_KEY = "eclipselink.tuning";
   private static final String ECLIPSELINK_DDL_GENERATION_KEY = "eclipselink.ddl-generation";
   private static final String ECLIPSELINK_DDL_GENERATION_OUTPUT_MODE_KEY = "eclipselink.ddl-generation.output-mode";
   private static final String ECLIPSELINK_DEPLOY_ON_STARTUP_KEY = "eclipselink.deploy-on-startup";
   private static final String STANDARD_SCHEMA_GENERATION_DATABASE_ACTION_KEY = "javax.persistence.schema-generation.database.action";
   private static final String STANDARD_SCHEMA_GENERATION_SCRIPTS_ACTION_KEY = "javax.persistence.schema-generation.scripts.action";
   private static final String STANDARD_SQL_LOAD_SCRIPT_SOURCE_KEY = "javax.persistence.sql_load-script-source";
   private static final String JAVAX_PERSISTENCE_BEAN_MANAGER_KEY = "javax.persistence.bean.manager";
   private static final String ECLIPSELINK_TUNING_EXALOGIC_VALUE = "ExaLogic";
   private static final String ECLIPSELINK_LOGGER_PROP_VALUE;
   private static final AuthenticatedSubject KERNEL_ID;
   private final String defaultELTargetServer;
   private Constructor emfProxyCtr;
   private EntityManagerFactory unwrappedEMF;
   private boolean isJPA1FilteringEnabled;
   private boolean isStandardSchemaGeneration;
   private boolean isEagerDBInitialization;
   private boolean isDataSourceMissing;
   private boolean isEMFReInitializationCompleted = false;
   protected final JPAIntegrationProvider.Type type;
   protected final String persistenceUnitId;
   protected String providerClassName;
   protected final PersistenceUnitBean dd;
   protected final URL rootUrl;
   protected final List jarFileUrls = new ArrayList();
   protected Properties properties = new Properties();
   protected DataSource jtaDataSource;
   protected DataSource nonJtaDataSource;
   protected final GenericClassLoader cl;
   protected final URL jarParentUrl;
   protected String originalVersion;
   protected boolean isParentMBeanSet = false;
   protected final String persistenceArchiveId;
   protected ApplicationContextInternal appCtx;
   private volatile boolean isPersistenceContextSerializableSet = false;
   private volatile boolean isPersistenceContextSerializable = false;

   public BasePersistenceUnitInfo(JPAIntegrationProvider.Type type, PersistenceUnitBean dd, String persistenceArchiveId, URL rootUrl, GenericClassLoader cl, URL jarParentUrl, String originalVersion, ApplicationContextInternal appCtx) {
      this.type = type;
      this.dd = dd;
      this.persistenceArchiveId = persistenceArchiveId;
      this.persistenceUnitId = persistenceArchiveId + "#" + dd.getName();
      this.rootUrl = rootUrl;
      this.cl = cl;
      this.jarParentUrl = jarParentUrl;
      this.originalVersion = originalVersion;
      this.appCtx = appCtx;
      boolean use12 = false;

      try {
         cl.loadClass("org.eclipse.persistence.platform.server.wls.WebLogic_12_Platform");
         use12 = true;
      } catch (Throwable var11) {
      }

      this.defaultELTargetServer = use12 ? "WebLogic_12" : "WebLogic_10";
   }

   private void initSchemaGenerationParams() {
      String stdSchemaGenerationAction = "none";
      if (this.properties.get("javax.persistence.schema-generation.database.action") instanceof String) {
         stdSchemaGenerationAction = ((String)this.properties.get("javax.persistence.schema-generation.database.action")).trim().toLowerCase();
      }

      String stdSQLLoadSource = "";
      if (this.properties.get("javax.persistence.sql_load-script-source") instanceof String) {
         stdSQLLoadSource = ((String)this.properties.get("javax.persistence.sql_load-script-source")).trim();
      }

      if (!"none".equals(stdSchemaGenerationAction) || !"".equals(stdSQLLoadSource)) {
         this.isStandardSchemaGeneration = true;
         this.isEagerDBInitialization = true;
      }

      String elDeployOnStartup = "false";
      if (this.properties.get("eclipselink.deploy-on-startup") instanceof String) {
         elDeployOnStartup = ((String)this.properties.get("eclipselink.deploy-on-startup")).trim().toLowerCase();
      }

      if ("true".equals(elDeployOnStartup)) {
         this.isEagerDBInitialization = true;
      }

   }

   public void init() throws EnvironmentException {
      this.createJarFileUrls();
      this.lookUpDataSources();
      this.processProperties();
      this.initSchemaGenerationParams();
      if (this.isProviderJPA1()) {
         this.isJPA1FilteringEnabled = !Boolean.valueOf(this.getProperties().getProperty("weblogic.disableJPA1Filtering"));
      } else {
         this.isJPA1FilteringEnabled = false;
      }

      this.initializeEntityManagerFactory(!this.isDataSourceReInitializationNeeded(), (BeanManager)null);
   }

   private boolean isDataSourceReInitializationNeeded() {
      return this.isDataSourceMissing && (this.isHibernatePersistenceUnit() || this.isEagerDBInitialization);
   }

   public String getPersistenceUnitId() {
      return this.persistenceUnitId;
   }

   protected boolean isKodoPersistenceUnit() {
      String pp = this.getPersistenceProviderClassName();
      return this.isEmpty(pp) || pp.equals("kodo.persistence.PersistenceProviderImpl") || pp.equals("org.apache.openjpa.persistence.PersistenceProviderImpl");
   }

   protected boolean isHibernatePersistenceUnit() {
      return JPAIntegrationProvider.Type.HYBERNATE.equals(this.type);
   }

   public String getPersistenceUnitName() {
      return this.dd.getName();
   }

   public String getPersistenceProviderClassName() {
      if (this.providerClassName != null) {
         return this.providerClassName;
      } else {
         String name = this.dd.getProvider();
         if (this.isEmpty(name)) {
            name = JPAIntegrationProviderFactory.getDefaultJPAProviderClassName();
            J2EELogger.logUsingDefaultPersistenceProvider(this.getPersistenceUnitId(), name);
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("The persistence unit " + this.getPersistenceUnitId() + " will use the following PersistenceProvider class: " + name);
         }

         this.providerClassName = name;
         return this.providerClassName;
      }
   }

   public PersistenceUnitTransactionType getTransactionType() {
      return PersistenceUnitTransactionType.valueOf(this.dd.getTransactionType());
   }

   public DataSource getJtaDataSource() {
      return this.jtaDataSource;
   }

   public DataSource getNonJtaDataSource() {
      return this.nonJtaDataSource;
   }

   public List getMappingFileNames() {
      return Arrays.asList(this.dd.getMappingFiles());
   }

   public List getJarFileUrls() {
      return this.jarFileUrls;
   }

   public URL getPersistenceUnitRootUrl() {
      return this.rootUrl;
   }

   public List getManagedClassNames() {
      return Arrays.asList(this.dd.getClasses());
   }

   public boolean excludeUnlistedClasses() {
      boolean isSet = ((DescriptorBean)this.dd).isSet("ExcludeUnlistedClasses");
      return isSet ? this.dd.getExcludeUnlistedClasses() : false;
   }

   public Properties getProperties() {
      return this.properties;
   }

   public ClassLoader getClassLoader() {
      return this.cl;
   }

   public void addTransformer(ClassTransformer classTransformer) {
      this.cl.addInstanceClassPreProcessor(new ClassPreProcessorImpl(classTransformer, this.cl));
   }

   public ClassLoader getNewTempClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public ClassLoader run() {
            return new GenericClassLoader(BasePersistenceUnitInfo.this.cl.getClassFinder(), BasePersistenceUnitInfo.this.cl.getParent());
         }
      });
   }

   private void supressSchemaGeneration(Map props, boolean disableEagerDeploy) {
      if (this.isStandardSchemaGeneration) {
         props.put("javax.persistence.schema-generation.database.action", "none");
         props.put("javax.persistence.schema-generation.scripts.action", "none");
         props.put("javax.persistence.sql_load-script-source", new CharArrayReader(new char[0]));
      } else {
         props.put("eclipselink.ddl-generation", "none");
         props.put("eclipselink.ddl-generation.output-mode", "none");
         if (disableEagerDeploy) {
            props.put("eclipselink.deploy-on-startup", "false");
         }
      }

   }

   protected Map getPersistenceProviderProperties(boolean isFinalInit, BeanManager bm) {
      if (!this.isKodoPersistenceUnit() && (!this.isHibernatePersistenceUnit() || isFinalInit)) {
         Map props = new HashMap();
         if (this.getTransactionType() == PersistenceUnitTransactionType.JTA && this.isEmpty(this.dd.getJtaDataSource())) {
            props.put("javax.persistence.jtaDataSource", "java:comp/DefaultDataSource");
         } else if (!this.isEmpty(this.dd.getJtaDataSource()) && this.jtaDataSource == null && this.properties.getProperty("javax.persistence.jtaDataSource") == null) {
            props.put("javax.persistence.jtaDataSource", this.dd.getJtaDataSource());
         }

         if (!this.isEmpty(this.dd.getNonJtaDataSource()) && this.nonJtaDataSource == null && this.properties.getProperty("javax.persistence.nonJtaDataSource") == null) {
            props.put("javax.persistence.nonJtaDataSource", this.dd.getNonJtaDataSource());
         }

         if (this.isDataSourceReInitializationNeeded() && !isFinalInit) {
            this.supressSchemaGeneration(props, true);
            return props;
         } else {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Setting eclipselink target server platform: " + this.defaultELTargetServer);
            }

            props.put("eclipselink.target-server", this.defaultELTargetServer);
            if (this.appCtx != null) {
               props.put("weblogic.application-id", this.appCtx.getApplicationId());
            }

            if (Kernel.isServer() && ManagementService.getRuntimeAccess(KERNEL_ID).getDomain().isExalogicOptimizationsEnabled()) {
               props.put("eclipselink.tuning", "ExaLogic");
            }

            if (!this.isProviderLoadedFromApplication() && this.properties.getProperty("eclipselink.logging.logger") == null && ECLIPSELINK_LOGGER_PROP_VALUE == null && (this.dd.getProvider() == null || this.dd.getProvider().contains("org.eclipse.persistence"))) {
               props.put("eclipselink.logging.logger", "weblogic.eclipselink.WeblogicEclipseLinkLog");
            }

            if (this.appCtx != null && this.appCtx.isStaticDeploymentOperation()) {
               J2EELogger.logSkipSchemaGenerationForServerStartup(this.getPersistenceUnitId());
               this.supressSchemaGeneration(props, false);
            }

            if (bm != null) {
               props.put("javax.persistence.bean.manager", bm);
            }

            return props;
         }
      } else {
         return Collections.emptyMap();
      }
   }

   private String getCDIArchiveID(String archiveId) {
      int index = archiveId.lastIndexOf(35);
      return index >= 0 ? archiveId.substring(index + 1) : archiveId;
   }

   private BeanManager getBeanManager(String archiveId) {
      InjectionContainer ic = (InjectionContainer)this.appCtx.getUserObject(InjectionContainer.class.getName());
      if (ic != null && ic.getDeployment() != null) {
         com.oracle.injection.BeanManager bm = ic.getDeployment().getBeanManager(archiveId);
         if (bm != null) {
            return (BeanManager)bm.getInternalBeanManager();
         }
      }

      return null;
   }

   protected void lookUpDataSources() {
      if (this.isEmpty(this.dd.getJtaDataSource()) && this.isEmpty(this.dd.getNonJtaDataSource())) {
         this.isDataSourceMissing = true;
      } else {
         if (!this.isEmpty(this.dd.getJtaDataSource())) {
            this.jtaDataSource = this.lookUpDataSource(this.dd.getJtaDataSource());
            if (this.jtaDataSource == null) {
               this.isDataSourceMissing = true;
            }
         }

         if (!this.isEmpty(this.dd.getNonJtaDataSource())) {
            this.nonJtaDataSource = this.lookUpDataSource(this.dd.getNonJtaDataSource());
            if (this.nonJtaDataSource == null) {
               this.isDataSourceMissing = true;
            }
         }

      }
   }

   private boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }

   private DataSource lookUpDataSource(String jndiName) {
      try {
         return (DataSource)InitialContext.doLookup(jndiName);
      } catch (NamingException var3) {
         return null;
      }
   }

   protected void createJarFileUrls() throws EnvironmentException {
      if (this.dd.getJarFiles().length > 0 && "jar".equals(this.jarParentUrl.getProtocol())) {
         throw new IllegalStateException("jar-file references are only supported inside META-INF/classes and exploded-format deployments.  Unsupported reference found while parsing persistence unit '" + this.persistenceUnitId + "'.");
      } else {
         for(int i = 0; i < this.dd.getJarFiles().length; ++i) {
            String newUrlString = this.jarParentUrl.toString() + this.dd.getJarFiles()[i];

            URL jarFileUrl;
            try {
               jarFileUrl = new URL(newUrlString);
               jarFileUrl.openConnection().connect();
               this.jarFileUrls.add(jarFileUrl);
            } catch (MalformedURLException var6) {
               EnvironmentException e = new EnvironmentException("Error processing PersistenceUnit " + this.getPersistenceUnitName() + ": " + var6);
               e.setStackTrace(var6.getStackTrace());
               throw e;
            } catch (IOException var7) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("jar-file is not found at " + newUrlString);
               }

               if (this.appCtx != null) {
                  jarFileUrl = this.getJarFileUrlFromLibraryModule(this.dd.getJarFiles()[i]);
                  if (jarFileUrl != null) {
                     this.jarFileUrls.add(jarFileUrl);
                  }
               }
            }
         }

      }
   }

   protected boolean isProviderLoadedFromApplication() {
      try {
         Class providerClass = Class.forName(this.getPersistenceProviderClassName(), true, this.cl);
         ClassLoader pcl = providerClass.getClassLoader();
         return pcl instanceof GenericClassLoader && ((GenericClassLoader)pcl).getAnnotation().getApplicationName() != null;
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }

   public String getDescription() {
      return this.dd.getDescription();
   }

   public PersistenceUnitBean getDD() {
      return this.dd;
   }

   protected DataSource lookUpDataSourceAgain(Context appEnvCtx, String name) {
      try {
         return (DataSource)InitialContext.doLookup(name);
      } catch (NamingException var6) {
         try {
            if (name.startsWith("java:/app/")) {
               name = name.substring("java:/app/".length());
            } else if (name.startsWith("java:app/")) {
               name = name.substring("java:app/".length());
            }

            return (DataSource)appEnvCtx.lookup(name);
         } catch (NamingException var5) {
            return null;
         }
      }
   }

   private PersistenceProvider getPersistenceProvider() throws EnvironmentException {
      try {
         return (PersistenceProvider)Class.forName(this.getPersistenceProviderClassName(), true, this.cl).newInstance();
      } catch (Exception var3) {
         EnvironmentException ee = new EnvironmentException("Error instantiating the Persistence Provider class " + this.getPersistenceProviderClassName() + " of the PersistenceUnit " + this.getPersistenceUnitName() + ": " + var3);
         ee.initCause(var3);
         throw ee;
      }
   }

   protected void initializeEntityManagerFactory(boolean isFinalInit, BeanManager bm) throws EnvironmentException {
      if (this.unwrappedEMF == null) {
         this.unwrappedEMF = this.getPersistenceProvider().createContainerEntityManagerFactory(this, this.getPersistenceProviderProperties(isFinalInit, bm));
         if (this.unwrappedEMF == null) {
            throw new EnvironmentException("Could not find deployed EMF for persistence unit named " + this.getPersistenceUnitName() + ". Available EMFs in the current context: " + this);
         }

         Class[] ifaces = DynamicProxyUtils.getAllInterfaces(this.unwrappedEMF.getClass(), EntityManagerFactory.class);

         try {
            this.emfProxyCtr = Proxy.getProxyClass(this.cl, ifaces).getConstructor(InvocationHandler.class);
         } catch (NoSuchMethodException var5) {
            throw new AssertionError("Should not reach.", var5);
         }
      }

   }

   public EntityManagerFactory getEntityManagerFactory() {
      if (this.unwrappedEMF == null) {
         throw new IllegalStateException("EMF has either not been initialized yet or already been closed!");
      } else {
         EntityManagerFactoryProxyImpl proxyImpl = new EntityManagerFactoryProxyImpl(this.unwrappedEMF, this.getPersistenceUnitName());
         if (this.isJPA1FilteringEnabled) {
            proxyImpl.setInterceptor(JPA1EntityManagerFactoryInterceptor.getInstance());
         }

         try {
            return (EntityManagerFactory)this.emfProxyCtr.newInstance(proxyImpl);
         } catch (IllegalAccessException | InvocationTargetException | InstantiationException var3) {
            throw new AssertionError("Should not reach", var3);
         }
      }
   }

   public EntityManagerFactory getUnwrappedEntityManagerFactory() {
      if (this.unwrappedEMF == null) {
         throw new IllegalStateException("EMF has either not been initialized yet or already been closed!");
      } else {
         return this.unwrappedEMF;
      }
   }

   public void close() {
      if (this.unwrappedEMF != null) {
         this.unwrappedEMF.close();
         this.unwrappedEMF = null;
         this.providerClassName = null;
      }

   }

   public String getPersistenceXMLSchemaVersion() {
      return this.originalVersion;
   }

   public SharedCacheMode getSharedCacheMode() {
      return SharedCacheMode.valueOf(this.dd.getSharedCacheMode());
   }

   public ValidationMode getValidationMode() {
      return ValidationMode.valueOf(this.dd.getValidationMode());
   }

   public boolean isJPA1FilteringEnabled() {
      return this.isJPA1FilteringEnabled;
   }

   private boolean isProviderJPA1() {
      try {
         this.getPersistenceProvider().getProviderUtil();
         return false;
      } catch (Throwable var2) {
         return true;
      }
   }

   protected void processProperties() {
      if (this.dd.getProperties() != null) {
         PersistencePropertyBean[] props = this.dd.getProperties().getProperties();

         for(int i = 0; props != null && i < props.length; ++i) {
            this.properties.setProperty(props[i].getName(), props[i].getValue());
         }
      }

      if (DEBUG.isDebugEnabled()) {
         Iterator var3 = this.properties.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            DEBUG.debug("Found persistence property:" + entry.getKey() + "," + entry.getValue());
         }

         DEBUG.debug("Done parsing persistence properties.");
      }

   }

   public void activate(Context appEnvCtx) throws EnvironmentException {
      if (!this.isEmpty(this.dd.getJtaDataSource()) && this.jtaDataSource == null) {
         this.jtaDataSource = this.lookUpDataSourceAgain(appEnvCtx, this.dd.getJtaDataSource());
      }

      if (!this.isEmpty(this.dd.getNonJtaDataSource()) && this.nonJtaDataSource == null) {
         this.nonJtaDataSource = this.lookUpDataSourceAgain(appEnvCtx, this.dd.getNonJtaDataSource());
      }

      this.reInitEntityManagerFactoryPerhaps();
   }

   public void deactivate() {
      this.isEMFReInitializationCompleted = false;
   }

   public PersistenceUnitRuntimeMBean createRuntimeMBean(RuntimeMBean parentMBean) throws EnvironmentException {
      try {
         return new PersistenceUnitRuntimeMBeanImpl(this.getPersistenceUnitName(), parentMBean);
      } catch (ManagementException var3) {
         throw new EnvironmentException(var3);
      }
   }

   public void setParentRuntimeMBean(PersistenceUnitParent parent, RuntimeMBean parentMBean) throws EnvironmentException {
      if (parentMBean != null && !this.isParentMBeanSet) {
         this.isParentMBeanSet = true;
         PersistenceUnitRuntimeMBean mbean = this.createRuntimeMBean(parentMBean);
         if (mbean != null) {
            parent.addPersistenceUnit(mbean);
         }
      }

   }

   public void reInitEntityManagerFactoryPerhaps() throws EnvironmentException {
      if (!this.isEMFReInitializationCompleted) {
         BeanManager bm = null;
         if (this.appCtx != null) {
            bm = this.getBeanManager(this.getCDIArchiveID(this.persistenceArchiveId));
         }

         if (this.isDataSourceReInitializationNeeded()) {
            synchronized(this) {
               if (!this.isEMFReInitializationCompleted) {
                  this.close();
                  this.initializeEntityManagerFactory(true, bm);
                  this.isEMFReInitializationCompleted = true;
               }
            }
         } else if (!this.isKodoPersistenceUnit() && bm != null) {
            synchronized(this) {
               if (!this.isEMFReInitializationCompleted) {
                  this.close();
                  this.initializeEntityManagerFactory(true, bm);
                  this.isEMFReInitializationCompleted = true;
               }
            }
         }

      }
   }

   public void registerUpdateListeners() {
   }

   public void unregisterUpdateListeners() {
   }

   public JPAIntegrationProvider.Type getType() {
      return this.type;
   }

   private URL getJarFileUrlFromLibraryModule(String jarFileName) {
      if (this.appCtx == null) {
         return null;
      } else {
         String[] appOrMod = this.persistenceArchiveId.split("#");
         LibraryProvider libraryProvider = this.appCtx.getLibraryProvider(appOrMod[appOrMod.length - 1]);
         if (libraryProvider != null) {
            Library[] libs = libraryProvider.getReferencedLibraries();
            Library[] var5 = libs;
            int var6 = libs.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Library lib = var5[var7];

               try {
                  VirtualJarFile vjf = VirtualJarFactory.createVirtualJar(lib.getLocation());
                  Iterator it = vjf.entries();

                  while(it.hasNext()) {
                     ZipEntry entry = (ZipEntry)it.next();
                     if (entry.getName().indexOf(jarFileName) > -1) {
                        URL jarFileUrl = vjf.getResource(entry.getName());
                        if (DEBUG.isDebugEnabled()) {
                           DEBUG.debug("jar-file is found in library at " + jarFileUrl.toString());
                        }

                        return jarFileUrl;
                     }
                  }
               } catch (IOException var13) {
               }
            }
         }

         return null;
      }
   }

   public boolean isPersistenceContextSerializable() {
      if (this.isPersistenceContextSerializableSet) {
         return this.isPersistenceContextSerializable;
      } else {
         EntityManager em = null;

         boolean var2;
         try {
            em = this.getUnwrappedEntityManagerFactory().createEntityManager();
            var2 = this.isPersistenceContextSerializable(em.getClass());
         } finally {
            if (em != null) {
               em.close();
            }

         }

         return var2;
      }
   }

   public boolean isPersistenceContextSerializable(Class emClass) {
      if (this.isPersistenceContextSerializableSet) {
         return this.isPersistenceContextSerializable;
      } else {
         try {
            boolean result = Serializable.class.isAssignableFrom(emClass);
            if (!result || this.isProviderJPA1()) {
               this.isPersistenceContextSerializable = result;
               boolean var9 = this.isPersistenceContextSerializable;
               return var9;
            } else {
               Iterator var3 = this.unwrappedEMF.getMetamodel().getManagedTypes().iterator();

               do {
                  if (!var3.hasNext()) {
                     this.isPersistenceContextSerializable = true;
                     return this.isPersistenceContextSerializable;
                  }

                  ManagedType mt = (ManagedType)var3.next();
                  result = Serializable.class.isAssignableFrom(mt.getJavaType());
               } while(result);

               this.isPersistenceContextSerializable = result;
               boolean var5 = this.isPersistenceContextSerializable;
               return var5;
            }
         } finally {
            this.isPersistenceContextSerializableSet = true;
         }
      }
   }

   static {
      DEBUG = JPAIntegrationProviderFactory.DEBUG;
      vDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingVerbose");
      ECLIPSELINK_LOGGER_PROP_VALUE = System.getProperty("eclipselink.logging.logger");
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private static final class ClassPreProcessorImpl implements ClassPreProcessor {
      private final ClassTransformer transformer;
      private final GenericClassLoader cl;

      ClassPreProcessorImpl(ClassTransformer transformer, GenericClassLoader cl) {
         this.transformer = transformer;
         this.cl = cl;
      }

      public void initialize(Hashtable params) {
      }

      public byte[] preProcess(String className, byte[] classBytes) {
         ClassLoader oldcl = Thread.currentThread().getContextClassLoader();

         byte[] var5;
         try {
            Thread.currentThread().setContextClassLoader(this.cl);
            byte[] newBytes = this.transformer.transform(this.cl, className, (Class)null, (ProtectionDomain)null, classBytes);
            if (BasePersistenceUnitInfo.vDebugLogger.isDebugEnabled()) {
               ClassLoaderDebugger.verbose(this, "preProcess", "ClassLoader: " + this.cl.hashCode() + "; className: " + className + "; original size: " + classBytes.length, "JPA Pre-processor " + (newBytes != null ? "changed" : "did not touch") + " original class");
            }

            var5 = newBytes == null ? classBytes : newBytes;
         } catch (IllegalClassFormatException var11) {
            throw new RuntimeException(var11);
         } catch (RuntimeException var12) {
            throw var12;
         } catch (Exception var13) {
            throw new RuntimeException(var13);
         } finally {
            Thread.currentThread().setContextClassLoader(oldcl);
         }

         return var5;
      }
   }
}
