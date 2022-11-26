package weblogic.ejb.container.deployer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.naming.Context;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.Environment;
import weblogic.ejb.QueryHome;
import weblogic.ejb.QueryLocalHome;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.EJBCheckerFactory;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.entity.EntityBeanCheckerFactory;
import weblogic.ejb.container.dd.DDDefaults;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanQuery;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.QueryCache;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.manager.BeanManagedPersistenceManager;
import weblogic.ejb.container.manager.DBManager;
import weblogic.ejb.container.manager.ExclusiveEntityManager;
import weblogic.ejb.container.manager.ReadOnlyEntityManager;
import weblogic.ejb.container.manager.TTLManager;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.QueryBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.logging.Loggable;
import weblogic.utils.classloaders.GenericClassLoader;

public final class EntityBeanInfoImpl extends ClientDrivenBeanInfoImpl implements EntityBeanInfo {
   private final int concurrencyStrategy;
   private final boolean isBeanManagedPersistence;
   private final String persistenceUseIdentifier;
   private final String persistenceUseVersion;
   private final String persistenceUseStorage;
   private final boolean cacheBetweenTransactions;
   private final boolean boxCarUpdates;
   private final String isModifiedMethodName;
   private final boolean unknownPK;
   private final String primaryKeyClassName;
   private final Collection allQueries;
   private final boolean isReentrant;
   private final CMPInfoImpl cmpInfo;
   private final String generatedBeanClassName;
   private final String generatedBeanInterfaceName;
   private Class generatedBeanClass;
   private Class generatedBeanInterface;
   private final Class pkClass;
   private final boolean disableReadyInstances;
   private final String invalidationTargetEJBName;
   private final String entityCacheName;
   private final int estimatedBeanSize;
   private PersistenceManager persistenceManager;
   private BeanManager beanManager;
   private boolean singleInstanceReadOnly = false;
   private boolean enableDynamicQueries = false;
   private String categoryCmpField;
   private final EntityBeanUpdateListener ul;

   public EntityBeanInfoImpl(DeploymentInfo di, CompositeDescriptor cdesc, GenericClassLoader moduleCL) throws ClassNotFoundException, WLDeploymentException {
      super(di, cdesc, moduleCL);
      EntityBeanBean entity = (EntityBeanBean)cdesc.getBean();
      this.isBeanManagedPersistence = cdesc.isBeanManagedPersistence();
      this.isModifiedMethodName = cdesc.getIsModifiedMethodName();
      this.boxCarUpdates = cdesc.getDelayUpdatesUntilEndOfTx();
      this.primaryKeyClassName = entity.getPrimKeyClass();
      this.unknownPK = this.primaryKeyClassName.equals("java.lang.Object");
      this.isReentrant = entity.isReentrant();
      NamingConvention nc = new NamingConvention(entity.getEjbClass(), cdesc.getEJBName());

      try {
         this.pkClass = this.loadClass(this.primaryKeyClassName);
      } catch (ClassNotFoundException var10) {
         throw new ClassNotFoundException(EJBComplianceTextFormatter.getInstance().ENTITYBEANINFOIMPL(this.primaryKeyClassName));
      }

      this.checkClassLoaders(cdesc, this.pkClass);
      if (!this.isBeanManagedPersistence) {
         this.cmpInfo = new CMPInfoImpl(this, cdesc);
         this.persistenceUseIdentifier = this.cmpInfo.getPersistenceUseIdentifier();
         this.persistenceUseVersion = this.cmpInfo.getPersistenceUseVersion();
         this.persistenceUseStorage = this.cmpInfo.getPersistenceUseStorage();
         if (this.persistenceUseIdentifier != null) {
            this.generatedBeanClassName = nc.getCmpBeanClassName(this.persistenceUseIdentifier);
            if (this.getDeploymentInfo().isEnableBeanClassRedeploy()) {
               moduleCL.excludeClass(this.generatedBeanClassName);
            }

            this.cmpInfo.setGeneratedBeanClassName(this.generatedBeanClassName);
         } else {
            this.generatedBeanClassName = null;
         }

         this.allQueries = new ArrayList();
         Collection c = this.cmpInfo.getAllQueries();
         Enumeration e = Collections.enumeration(c);

         while(e.hasMoreElements()) {
            QueryBean mb = (QueryBean)e.nextElement();
            EntityBeanQuery ebq = new EntityBeanQueryImpl(mb);
            this.allQueries.add(ebq);
         }
      } else {
         this.persistenceUseIdentifier = "N/A: not a CMP Bean";
         this.persistenceUseVersion = "N/A: not a CMP Bean";
         this.persistenceUseStorage = "N/A: not a CMP Bean";
         this.generatedBeanClassName = nc.getGeneratedBeanClassName();
         moduleCL.excludeClass(this.generatedBeanClassName);
         this.generatedBeanClass = null;
         this.allQueries = null;
         this.cmpInfo = null;
      }

      this.generatedBeanInterfaceName = nc.getGeneratedBeanInterfaceName();
      if (cdesc.hasEntityCacheReference()) {
         this.entityCacheName = cdesc.getEntityCacheName();
         this.estimatedBeanSize = cdesc.getEstimatedBeanSize();
      } else {
         this.entityCacheName = null;
         this.estimatedBeanSize = -1;
      }

      this.concurrencyStrategy = DDUtils.concurrencyStringToInt(cdesc.getConcurrencyStrategy());
      this.cacheBetweenTransactions = cdesc.getCacheBetweenTransactions();
      this.invalidationTargetEJBName = cdesc.getInvalidationTargetEJBName();
      this.disableReadyInstances = cdesc.getDisableReadyIntances();
      this.initializeDynamicQueryMethodInfos(cdesc);
      this.ul = new EntityBeanUpdateListener(this);
   }

   protected String getSyntheticHomeImplClassName() {
      throw new AssertionError("No synthetic home for entity");
   }

   protected String getSyntheticLocalHomeImplClassName() {
      throw new AssertionError("No synthetic local home for entity");
   }

   public void prepare(Environment env) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("prepare() for ejb:" + this.getEJBName());
      }

      if (!this.isBeanManagedPersistence && this.getCMPInfo().uses20CMP()) {
         if (this.hasRemoteClientView()) {
            this.updateTxSettingsForCreateQuery(this.homeMethods);
         }

         if (this.hasLocalClientView()) {
            this.updateTxSettingsForCreateQuery(this.localHomeMethods);
         }
      }

      super.prepare(env);
   }

   private void updateTxSettingsForCreateQuery(Map mis) {
      MethodInfo mi = (MethodInfo)mis.get(getCreateQuerySignature());
      mi.setTransactionAttribute((short)1);
      mi.setTxIsolationLevel(-1);
      mi.setSelectForUpdate(0);
   }

   public void activate(Map cacheMap, Map queryCacheMap) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("activate() for ejb:" + this.getEJBName());
      }

      super.activate(cacheMap, queryCacheMap);
      if (!this.isBeanManagedPersistence) {
         Iterator var3 = this.getMethodDescriptors().iterator();

         while(var3.hasNext()) {
            MethodDescriptor md = (MethodDescriptor)var3.next();
            Method m = md.getMethod();
            if (this.cmpInfo.isQueryCachingEnabled(m)) {
               md.setMethodId(this.cmpInfo.getQueryCachingEnabledFinderIndex(m));
               if (this.cmpInfo.isEnableEagerRefresh(m)) {
                  ((TTLManager)this.beanManager).addEagerRefreshMethod(m, md.getMethodId());
               }
            }
         }
      }

   }

   protected Method perhapsGetBeanMethod(Method m, String intfType) {
      if (intfType.equals("Home") || intfType.equals("LocalHome")) {
         String methodName = m.getName();
         if (methodName.startsWith("find")) {
            Class genBeanIntf = this.getGeneratedBeanInterface();
            String ejbMethodName = "ejb" + methodName.substring(0, 1).toUpperCase(Locale.ENGLISH) + methodName.substring(1);

            try {
               return genBeanIntf.getMethod(ejbMethodName, m.getParameterTypes());
            } catch (NoSuchMethodException var12) {
               Method[] interfaceClass;
               if (!methodName.equals("findByPrimaryKey")) {
                  interfaceClass = null;
                  Class interfaceClass;
                  if (intfType.equals("Home")) {
                     interfaceClass = this.getHomeInterfaceClass();
                  } else {
                     interfaceClass = this.getLocalHomeInterfaceClass();
                  }

                  try {
                     return interfaceClass.getMethod(m.getName(), m.getParameterTypes());
                  } catch (NoSuchMethodException var11) {
                     throw new AssertionError("ejbFindXXX method for '" + getMethodSignature(m) + "' not found on class '" + genBeanIntf.getName() + "'.");
                  }
               }

               interfaceClass = genBeanIntf.getMethods();
               int var8 = interfaceClass.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Method allMethod = interfaceClass[var9];
                  if (allMethod.getName().equals("ejbFindByPrimaryKey")) {
                     return allMethod;
                  }
               }
            }
         }
      }

      return m;
   }

   public int getInstanceLockOrder() {
      return !this.isBeanManagedPersistence ? this.cmpInfo.getInstanceLockOrder() : 100;
   }

   public boolean isReadOnlyWithSingleInstance() {
      return this.singleInstanceReadOnly;
   }

   public boolean isReadOnly() {
      return this.concurrencyStrategy == 5;
   }

   public boolean isOptimistic() {
      return this.concurrencyStrategy == 6;
   }

   public int getConcurrencyStrategy() {
      return this.concurrencyStrategy;
   }

   public boolean usesBeanManagedTx() {
      return false;
   }

   public Class getPrimaryKeyClass() {
      return this.pkClass;
   }

   public boolean isUnknownPrimaryKey() {
      return this.unknownPK;
   }

   public boolean getIsBeanManagedPersistence() {
      return this.isBeanManagedPersistence;
   }

   public String getPersistenceUseIdentifier() {
      return this.persistenceUseIdentifier;
   }

   public String getPersistenceUseVersion() {
      return this.persistenceUseVersion;
   }

   public String getPersistenceUseStorage() {
      return this.persistenceUseStorage;
   }

   public boolean getCacheBetweenTransactions() {
      return this.cacheBetweenTransactions;
   }

   public boolean getDisableReadyInstances() {
      return this.disableReadyInstances;
   }

   public boolean getBoxCarUpdates() {
      return this.boxCarUpdates;
   }

   public String getPrimaryKeyClassName() {
      return this.primaryKeyClassName;
   }

   public String getIsModifiedMethodName() {
      return this.isModifiedMethodName;
   }

   public Collection getAllQueries() {
      return this.allQueries;
   }

   public boolean isReentrant() {
      return this.isReentrant;
   }

   public CMPInfo getCMPInfo() {
      return this.cmpInfo;
   }

   public String getGeneratedBeanClassName() {
      return this.generatedBeanClassName;
   }

   public String getGeneratedBeanInterfaceName() {
      return this.generatedBeanInterfaceName;
   }

   public Class getGeneratedBeanClass() {
      if (this.generatedBeanClass == null) {
         this.generatedBeanClass = this.loadForSure(this.generatedBeanClassName);
      }

      return this.generatedBeanClass;
   }

   public Class getGeneratedBeanInterface() {
      if (this.generatedBeanInterface == null) {
         this.generatedBeanInterface = this.loadForSure(this.generatedBeanInterfaceName);
      }

      return this.generatedBeanInterface;
   }

   public PersistenceManager getPersistenceManager() {
      if (this.persistenceManager == null) {
         if (this.getIsBeanManagedPersistence()) {
            this.persistenceManager = new BeanManagedPersistenceManager();
         } else {
            this.persistenceManager = this.getCMPInfo().getPersistenceType().getPersistenceManager();
         }
      }

      return this.persistenceManager;
   }

   public BeanManager getBeanManagerInstance(EJBRuntimeHolder compRuntime) {
      if (this.beanManager == null) {
         switch (this.getConcurrencyStrategy()) {
            case 1:
               if (!this.getIsBeanManagedPersistence() && this.getCMPInfo().getPersistenceType().hasExclusiveManager()) {
                  this.beanManager = this.getCMPInfo().getPersistenceType().getExclusiveManager();
               } else {
                  this.beanManager = new ExclusiveEntityManager(compRuntime);
               }
               break;
            case 2:
               if (!this.getIsBeanManagedPersistence() && this.getCMPInfo().getPersistenceType().hasDatabaseManager()) {
                  this.beanManager = this.getCMPInfo().getPersistenceType().getDatabaseManager();
               } else {
                  this.beanManager = new DBManager(compRuntime);
               }
               break;
            case 3:
            default:
               throw new AssertionError("Unexpected value: " + this.getConcurrencyStrategy());
            case 4:
               if (!this.getIsBeanManagedPersistence() && this.getCMPInfo().getPersistenceType().hasReadonlyManager()) {
                  this.beanManager = this.getCMPInfo().getPersistenceType().getReadonlyManager();
               } else {
                  this.beanManager = new ReadOnlyEntityManager(compRuntime);
               }
               break;
            case 5:
               if (!this.getIsBeanManagedPersistence() && this.getCMPInfo().getPersistenceType().hasReadonlyManager()) {
                  this.beanManager = this.getCMPInfo().getPersistenceType().getReadonlyManager();
               } else {
                  this.beanManager = new TTLManager(compRuntime);
               }
               break;
            case 6:
               if (!this.getIsBeanManagedPersistence() && this.getCMPInfo().getPersistenceType().hasDatabaseManager()) {
                  this.beanManager = this.getCMPInfo().getPersistenceType().getDatabaseManager();
               } else if (this.cacheBetweenTransactions) {
                  this.beanManager = new TTLManager(compRuntime);
               } else {
                  this.beanManager = new DBManager(compRuntime);
               }
         }
      }

      return this.beanManager;
   }

   protected EJBCache getCache(Map cacheMap) throws WLDeploymentException {
      EJBCache cache = null;
      if (this.entityCacheName != null) {
         Loggable l;
         if (cacheMap == null) {
            l = EJBLogger.logmustUseTwoPhaseDeploymentLoggable(this.getEJBName(), this.entityCacheName);
            throw new WLDeploymentException(l.getMessageText());
         }

         cache = (EJBCache)cacheMap.get(this.entityCacheName);
         if (cache == null) {
            l = EJBLogger.logmissingCacheDefinitionLoggable(this.getEJBName(), this.entityCacheName);
            throw new WLDeploymentException(l.getMessageText());
         }
      }

      return cache;
   }

   private QueryCache getQueryCache(Map cacheMap) throws WLDeploymentException {
      QueryCache cache = null;
      if (this.entityCacheName != null) {
         if (cacheMap == null) {
            Loggable l = EJBLogger.logmustUseTwoPhaseDeploymentLoggable(this.getEJBName(), this.entityCacheName);
            throw new WLDeploymentException(l.getMessageText());
         }

         cache = (QueryCache)cacheMap.get(this.entityCacheName);
      }

      return cache;
   }

   public int getEstimatedBeanSize() {
      return this.estimatedBeanSize;
   }

   public String getCacheName() {
      return this.entityCacheName;
   }

   protected short getTxAttribute(MethodInfo methodInfo, Class c) {
      return this.getConcurrencyStrategy() == 4 ? 0 : methodInfo.getTransactionAttribute();
   }

   public void assignDefaultTXAttributesIfNecessary(int jtaConfigTimeout) {
      super.setupTxTimeout(jtaConfigTimeout);
      List methodInfos = new ArrayList();
      if (this.hasRemoteClientView()) {
         methodInfos.add(this.getRemoteMethodInfo("getEJBHome()"));
         methodInfos.add(this.getRemoteMethodInfo("getHandle()"));
         methodInfos.add(this.getRemoteMethodInfo("getPrimaryKey()"));
         methodInfos.add(this.getRemoteMethodInfo("isIdentical(javax.ejb.EJBObject)"));
         methodInfos.add(this.getHomeMethodInfo("getEJBMetaData()"));
         methodInfos.add(this.getHomeMethodInfo("getHomeHandle()"));
      }

      if (this.hasLocalClientView()) {
         methodInfos.add(this.getLocalMethodInfo("getEJBLocalHome()"));
         methodInfos.add(this.getLocalMethodInfo("getPrimaryKey()"));
         methodInfos.add(this.getLocalMethodInfo("isIdentical(javax.ejb.EJBLocalObject)"));
         methodInfos.add(this.getLocalMethodInfo("getLocalHandle()"));
      }

      Iterator var3 = methodInfos.iterator();

      while(var3.hasNext()) {
         MethodInfo mi = (MethodInfo)var3.next();
         if (mi != null && mi.getTransactionAttribute() == -1) {
            mi.setTransactionAttribute(DDDefaults.getTransactionAttribute(this.isEJB30()));
         }
      }

      super.assignDefaultTXAttributesIfNecessary();
   }

   public String getInvalidationTargetEJBName() {
      return this.invalidationTargetEJBName;
   }

   public InvalidationBeanManager getInvalidationTargetBeanManager() {
      if (this.invalidationTargetEJBName == null) {
         return null;
      } else {
         EntityBeanInfo bi = (EntityBeanInfo)this.getDeploymentInfo().getBeanInfo(this.invalidationTargetEJBName);
         if (bi == null) {
            throw new AssertionError("Unable to find entity bean in deployment with name: " + this.invalidationTargetEJBName);
         } else {
            BeanManager bm = bi.getBeanManager();
            if (bm == null) {
               throw new AssertionError("Unable to find entity bean in deployment with name: " + this.invalidationTargetEJBName);
            } else {
               return (InvalidationBeanManager)bm;
            }
         }
      }
   }

   public boolean isDynamicQueriesEnabled() {
      return this.enableDynamicQueries;
   }

   private void initializeDynamicQueryMethodInfos(CompositeDescriptor cmbd) {
      if (!this.isBeanManagedPersistence && this.getCMPInfo().uses20CMP()) {
         if (cmbd.isDynamicQueriesEnabled()) {
            this.enableDynamicQueries = true;
         }

         if (this.hasRemoteClientView()) {
            this.createMethodInfos("Home", this.homeMethods, new Method[]{getRemoteCreateQueryMethod()});
         }

         if (this.hasLocalClientView()) {
            this.createMethodInfos("LocalHome", this.localHomeMethods, new Method[]{getLocalCreateQueryMethod()});
         }
      }

   }

   public static Method getRemoteCreateQueryMethod() {
      return getCreateQueryMethod(QueryHome.class);
   }

   public static Method getLocalCreateQueryMethod() {
      return getCreateQueryMethod(QueryLocalHome.class);
   }

   public static String getCreateQuerySignature() {
      return DDUtils.getMethodSignature(getRemoteCreateQueryMethod());
   }

   public static Method getCreateQueryMethod(Class intf) {
      try {
         return intf.getMethod("createQuery");
      } catch (NoSuchMethodException var2) {
         throw new AssertionError("Could not find createQuery method");
      }
   }

   public void updateImplClassLoader() throws WLDeploymentException {
      super.updateImplClassLoader();

      try {
         this.generatedBeanClass = this.loadClass(this.generatedBeanClassName);
      } catch (ClassNotFoundException var3) {
         throw new WLDeploymentException("Could not load updated impl class: " + var3);
      }

      if (!this.isBeanManagedPersistence) {
         this.cmpInfo.beanImplClassChangeNotification();
      }

      try {
         this.getBeanManager().beanImplClassChangeNotification();
      } catch (UnsupportedOperationException var2) {
         throw new WLDeploymentException("Bean Manager does not support partial updates");
      }
   }

   public int getMaxQueriesInCache() {
      return this.isBeanManagedPersistence ? 0 : this.getCMPInfo().getMaxQueriesInCache();
   }

   public boolean isEntityBean() {
      return true;
   }

   public String getCategoryCmpField() {
      return this.categoryCmpField;
   }

   public void setCategoryCmpField(String s) {
      this.categoryCmpField = s;
   }

   public void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescriptor, ApplicationContextInternal appCtx) {
      this.ul.addBeanUpdateListener(wlBean, ejbDescriptor, appCtx);
   }

   public void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescriptor, ApplicationContextInternal appCtx) {
      this.ul.removeBeanUpdateListener(wlBean, ejbDescriptor, appCtx);
   }

   public EJBCheckerFactory getEJBCheckerFactory(DeploymentInfo di) {
      return new EntityBeanCheckerFactory(di, this);
   }

   protected void initCacheManager(EJBCache cache, BeanManager bm, Map cacheMap, Map queryCacheMap, Context environmentContext) throws WLDeploymentException {
      if (bm instanceof TTLManager) {
         QueryCache qcache = this.getQueryCache(queryCacheMap);
         ((TTLManager)bm).setup(this.remoteHome, this.localHome, this, environmentContext, cache, qcache, this.getRuntimeHelper().getSecurityHelper());
      } else {
         super.initCacheManager(cache, bm, cacheMap, queryCacheMap, environmentContext);
      }

   }
}
