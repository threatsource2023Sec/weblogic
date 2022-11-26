package weblogic.ejb.container.deployer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ejb.SessionSynchronization;
import javax.persistence.PersistenceContextType;
import weblogic.application.naming.Environment;
import weblogic.application.naming.PersistenceUnitRegistry;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.ReadConfig;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.Ejb30SessionBeanClassChecker;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.deployer.mbimpl.ConcurrencyInfoImpl;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.interfaces.WLSessionSynchronization;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.StatefulEJBHomeImpl;
import weblogic.ejb.container.internal.StatefulEJBLocalHomeImpl;
import weblogic.ejb.container.manager.ReplicatedStatefulSessionManager;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.ejb.container.utils.MethodKey;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.ConcurrentMethodBean;
import weblogic.j2ee.descriptor.InitMethodBean;
import weblogic.j2ee.descriptor.MethodParamsBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.RemoveMethodBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionClusteringBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBean;
import weblogic.logging.Loggable;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.reflect.ReflectUtils;

class StatefulSessionBeanInfoImpl extends SessionBeanInfoImpl implements StatefulSessionBeanInfo {
   private final int replicationType;
   private final boolean implementsSessionSync = SessionSynchronization.class.isAssignableFrom(this.getBeanClass());
   private final String swapDirectoryName;
   private final long idleTimeoutMS = (long)this.getCachingDescriptor().getIdleTimeoutSecondsCache() * 1000L;
   private final long sessionTimeoutMS;
   private final boolean allowRemoveDuringTx;
   private final boolean passivateDuringReplication;
   private final boolean calculateDeltaUsingReflection;
   private boolean beanIsPassivationCapable;
   private boolean beanIsPassivationCapableReSet;
   private final PersistenceContextRefBean[] extendedPCRefs;
   private final StatefulTimeoutConfiguration statefulTimeoutConfiguration;
   private final Map sigToRemoveMethodBean = new HashMap();
   private Map concurrencyInfos;
   private MethodDescriptor postConstructMethodDescriptor;
   private MethodDescriptor preDestroyMethodDescriptor;
   private MethodDescriptor postActivateMethodDescriptor;
   private MethodDescriptor prePassivateMethodDescriptor;
   private MethodInfo postConstructMethodInfo;
   private MethodInfo preDestroyMethodInfo;
   private MethodInfo prePassivateMethodInfo;
   private MethodInfo postActivateMethodInfo;

   public StatefulSessionBeanInfoImpl(DeploymentInfo di, CompositeDescriptor desc, GenericClassLoader moduleCL) throws ClassNotFoundException, WLDeploymentException {
      super(di, desc, moduleCL);
      this.sessionTimeoutMS = (long)desc.getSessionTimeoutSeconds() * 1000L;
      StatefulSessionDescriptorBean ssd = desc.getWlBean().getStatefulSessionDescriptor();
      if (ssd.getPersistentStoreDir() == null) {
         this.swapDirectoryName = "pstore";
      } else {
         this.swapDirectoryName = ssd.getPersistentStoreDir();
      }

      this.allowRemoveDuringTx = ssd.isAllowRemoveDuringTransaction();
      this.beanIsPassivationCapable = ((SessionBeanBean)this.compDesc.getBean()).isPassivationCapable();
      this.beanIsPassivationCapableReSet = false;
      StatefulSessionClusteringBean ssc = ssd.getStatefulSessionClustering();
      this.passivateDuringReplication = ssc.isPassivateDuringReplication();
      this.calculateDeltaUsingReflection = ssc.isCalculateDeltaUsingReflection();
      if (this.needSetReplicationType(desc) && "InMemory".equals(ssc.getReplicationType())) {
         this.replicationType = 2;
         if (isServer() && !ReadConfig.isClusteredServer()) {
            EJBLogger.logWarningOnSFSBInMemoryReplicationFeature(this.getEJBName());
         }
      } else {
         this.replicationType = 1;
      }

      Set epcRefs = new HashSet();
      PersistenceContextRefBean[] var7 = this.getPersistenceContextRefs();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         PersistenceContextRefBean pcRef = var7[var9];
         if (PersistenceContextType.valueOf(pcRef.getPersistenceContextType().toUpperCase()) == PersistenceContextType.EXTENDED) {
            epcRefs.add(pcRef);
         }
      }

      this.extendedPCRefs = (PersistenceContextRefBean[])epcRefs.toArray(new PersistenceContextRefBean[epcRefs.size()]);
      this.statefulTimeoutConfiguration = this.compDesc.getStatefulTimeoutConfiguration();
      this.initializeRemoveMethodInfos();
   }

   private void initializeRemoveMethodInfos() throws WLDeploymentException {
      SessionBeanBean sessionBeanBean = (SessionBeanBean)this.compDesc.getBean();
      RemoveMethodBean[] var2 = sessionBeanBean.getRemoveMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         RemoveMethodBean removeBean = var2[var4];
         NamedMethodBean methodBean = removeBean.getBeanMethod();
         MethodParamsBean paramsBean = methodBean.getMethodParams();
         if (paramsBean == null) {
            List removeMethods = ReflectUtils.getDeclaredMethodsOfAGivenName(this.getBeanClass(), methodBean.getMethodName());
            Iterator var9 = removeMethods.iterator();

            while(var9.hasNext()) {
               Method removeMethod = (Method)var9.next();
               String sig = DDUtils.getMethodSignature(removeMethod);
               if (!this.sigToRemoveMethodBean.containsKey(sig)) {
                  this.sigToRemoveMethodBean.put(sig, removeBean);
               }

               if (!this.getDeploymentInfo().isCDIEnabled()) {
                  this.validateRemoveMethod(sessionBeanBean, sig);
               }
            }
         } else {
            String sig = DDUtils.getMethodSignature(methodBean.getMethodName(), paramsBean.getMethodParams());
            this.sigToRemoveMethodBean.put(sig, removeBean);
            if (!this.getDeploymentInfo().isCDIEnabled()) {
               this.validateRemoveMethod(sessionBeanBean, sig);
            }
         }
      }

   }

   public void initializeAllMethodInfos() throws WLDeploymentException {
      super.initializeAllMethodInfos();

      try {
         SessionBeanBean sbb = (SessionBeanBean)this.compDesc.getBean();
         Set callbacks = new HashSet();
         Method postConstructMethod = this.addCallbackMethods(callbacks, sbb.getPostConstructs());
         Method preDestroyMethod = this.addCallbackMethods(callbacks, sbb.getPreDestroys());
         Method prePassivateMethod = this.addCallbackMethods(callbacks, sbb.getPrePassivates());
         Method postActivateMethod = this.addCallbackMethods(callbacks, sbb.getPostActivates());
         this.createMethodInfos("LifecycleCallback", this.callbackMethods, (Method[])callbacks.toArray(new Method[0]));
         if (null != postConstructMethod) {
            this.postConstructMethodInfo = (MethodInfo)this.callbackMethods.get(DDUtils.getMethodSignature(postConstructMethod));
         }

         if (null != preDestroyMethod) {
            this.preDestroyMethodInfo = (MethodInfo)this.callbackMethods.get(DDUtils.getMethodSignature(preDestroyMethod));
         }

         if (null != prePassivateMethod) {
            this.prePassivateMethodInfo = (MethodInfo)this.callbackMethods.get(DDUtils.getMethodSignature(prePassivateMethod));
         }

         if (null != postActivateMethod) {
            this.postActivateMethodInfo = (MethodInfo)this.callbackMethods.get(DDUtils.getMethodSignature(postActivateMethod));
         }

      } catch (Throwable var7) {
         Loggable l = EJBLogger.logunableToInitializeInterfaceMethodInfoLoggable(this.getEJBName(), StackTraceUtilsClient.throwable2StackTrace(var7));
         throw new WLDeploymentException(l.getMessageText(), var7);
      }
   }

   private void validateRemoveMethod(SessionBeanBean sessionBeanBean, String sig) throws WLDeploymentException {
      MethodInfo mi = this.getRemoteMethodInfo(sig);
      if (mi == null) {
         mi = this.getLocalMethodInfo(sig);
      }

      try {
         Ejb30SessionBeanClassChecker.validateRemoveMethodToBeBusinessMethod(sessionBeanBean, mi, sig);
      } catch (ComplianceException var5) {
         throw new WLDeploymentException("Deploy failure: ", var5);
      }
   }

   private void initializeConcurrencyInfoMappings() {
      SessionBeanBean sbb = (SessionBeanBean)this.compDesc.getBean();
      if (sbb.getConcurrentMethods() != null) {
         this.concurrencyInfos = new HashMap();
         ConcurrentMethodBean[] var2 = sbb.getConcurrentMethods();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ConcurrentMethodBean cmb = var2[var4];
            MethodParamsBean mpb = cmb.getMethod().getMethodParams();
            MethodKey mk = new MethodKey(cmb.getMethod().getMethodName(), mpb == null ? new String[0] : mpb.getMethodParams());
            this.concurrencyInfos.put(mk, new ConcurrencyInfoImpl(cmb));
         }
      } else {
         this.concurrencyInfos = Collections.emptyMap();
      }

   }

   public Map getSessionSyncMethodMapping() {
      if (!this.implementsSessionSynchronization()) {
         throw new AssertionError("Invalid invocation for bean : " + this.getBeanClass());
      } else {
         Map map = new HashMap(3);

         try {
            if (SessionSynchronization.class.isAssignableFrom(this.getBeanClass())) {
               map.put(WLSessionSynchronization.class.getMethod("__WL_afterBegin"), this.getBeanClass().getMethod("afterBegin"));
               map.put(WLSessionSynchronization.class.getMethod("__WL_beforeCompletion"), this.getBeanClass().getMethod("beforeCompletion"));
               map.put(WLSessionSynchronization.class.getMethod("__WL_afterCompletion", Boolean.TYPE), this.getBeanClass().getMethod("afterCompletion", Boolean.TYPE));
            } else {
               SessionBeanBean sbb = (SessionBeanBean)this.compDesc.getBean();
               if (sbb.getAfterBeginMethod() != null) {
                  map.put(WLSessionSynchronization.class.getMethod("__WL_afterBegin"), EJBMethodsUtil.findBeanMethod(this.getBeanClass(), sbb.getAfterBeginMethod().getMethodName()));
               }

               if (sbb.getBeforeCompletionMethod() != null) {
                  map.put(WLSessionSynchronization.class.getMethod("__WL_beforeCompletion"), EJBMethodsUtil.findBeanMethod(this.getBeanClass(), sbb.getBeforeCompletionMethod().getMethodName()));
               }

               if (sbb.getAfterCompletionMethod() != null) {
                  map.put(WLSessionSynchronization.class.getMethod("__WL_afterCompletion", Boolean.TYPE), EJBMethodsUtil.findBeanMethod(this.getBeanClass(), sbb.getAfterCompletionMethod().getMethodName(), Boolean.TYPE));
               }
            }

            return map;
         } catch (NoSuchMethodException var3) {
            throw new AssertionError(var3);
         }
      }
   }

   public void prepare(Environment env) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("prepare() for ejb:" + this.getEJBName());
      }

      this.initializeConcurrencyInfoMappings();
      super.prepare(env);
      if (null != this.postConstructMethodInfo) {
         this.postConstructMethodDescriptor = this.getCallbackMethodDescriptor(this.postConstructMethodInfo);
      }

      if (null != this.preDestroyMethodInfo) {
         this.preDestroyMethodDescriptor = this.getCallbackMethodDescriptor(this.preDestroyMethodInfo);
      }

      if (null != this.postActivateMethodInfo) {
         this.postActivateMethodDescriptor = this.getCallbackMethodDescriptor(this.postActivateMethodInfo);
      }

      if (null != this.prePassivateMethodInfo) {
         this.prePassivateMethodDescriptor = this.getCallbackMethodDescriptor(this.prePassivateMethodInfo);
      }

   }

   protected BaseBeanUpdateListener getBeanUpdateListener() {
      return new StatefulSessionBeanUpdateListener(this);
   }

   protected String getSyntheticHomeImplClassName() {
      return StatefulEJBHomeImpl.class.getName();
   }

   protected String getSyntheticLocalHomeImplClassName() {
      return StatefulEJBLocalHomeImpl.class.getName();
   }

   protected void checkUpdatedClass(Class beanImpl) throws WLDeploymentException {
      if (this.implementsSessionSync != SessionSynchronization.class.isAssignableFrom(this.getBeanClass())) {
         throw new WLDeploymentException("Unable to perform a partial redeploy due to a SessionSynchronization change in the bean class.");
      }
   }

   protected short getTxAttributeOfRemove() {
      return this.allowRemoveDuringTx ? 2 : super.getTxAttributeOfRemove();
   }

   protected boolean isRemoveMethod(MethodInfo mi) {
      return this.sigToRemoveMethodBean.containsKey(mi.getSignature());
   }

   public Collection getRemoveMethods() {
      List removeMethods = new ArrayList();
      Method[] var2 = this.getBeanClass().getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         if (this.sigToRemoveMethodBean.containsKey(DDUtils.getMethodSignature(m))) {
            removeMethods.add(m);
         }
      }

      return removeMethods;
   }

   protected boolean isRetainIfException(MethodInfo mi) {
      RemoveMethodBean rmb = (RemoveMethodBean)this.sigToRemoveMethodBean.get(mi.getSignature());
      return rmb != null && rmb.isRetainIfException();
   }

   protected boolean needSetReplicationType(CompositeDescriptor desc) {
      return this.getJNDINameAsString() != null || desc.getBusinessRemotes().length > 0;
   }

   public int getReplicationType() {
      return this.replicationType;
   }

   public boolean isReplicated() {
      return this.getReplicationType() == 2;
   }

   public boolean implementsSessionSynchronization() {
      SessionBeanBean sb = (SessionBeanBean)this.compDesc.getBean();
      return this.implementsSessionSync || sb.getAfterBeginMethod() != null || sb.getAfterCompletionMethod() != null || sb.getBeforeCompletionMethod() != null;
   }

   public String getSwapDirectoryName() {
      return this.swapDirectoryName;
   }

   public long getIdleTimeoutMS() {
      return this.idleTimeoutMS;
   }

   public long getSessionTimeoutMS() {
      return this.sessionTimeoutMS;
   }

   public StatefulTimeoutConfiguration getStatefulTimeoutConfiguration() {
      return this.statefulTimeoutConfiguration;
   }

   public boolean isStatefulTimeoutConfigured() {
      return this.getStatefulTimeoutConfiguration() != null;
   }

   public boolean getCalculateDeltaUsingReflection() {
      return this.calculateDeltaUsingReflection;
   }

   public boolean getPassivateDuringReplication() {
      return this.passivateDuringReplication;
   }

   public boolean containsExtendedPersistenceContextRefs() {
      return this.extendedPCRefs.length > 0;
   }

   public void setPersistenceUnitRegistry(PersistenceUnitRegistry puReg) {
      if (this.containsExtendedPersistenceContextRefs()) {
         ((StatefulSessionManager)this.getBeanManager()).setupExtendedPCSupport(puReg, this.extendedPCRefs);
      }

   }

   public String getEjbCreateInitMethodName(Method m) {
      String ejbMethodName = null;
      SessionBeanBean sessionBeanBean = (SessionBeanBean)this.compDesc.getBean();
      InitMethodBean[] var4 = sessionBeanBean.getInitMethods();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         InitMethodBean initMethBean = var4[var6];
         if (initMethBean.getCreateMethod().getMethodParams() != null) {
            String[] beanParamTypeNames = initMethBean.getCreateMethod().getMethodParams().getMethodParams();
            Class[] createParameTypes = m.getParameterTypes();
            if (beanParamTypeNames.length == createParameTypes.length && initMethBean.getCreateMethod().getMethodName().equalsIgnoreCase(m.getName())) {
               boolean matchingMethod = true;

               for(int j = 0; j < createParameTypes.length; ++j) {
                  if (!createParameTypes[j].getName().equalsIgnoreCase(beanParamTypeNames[j])) {
                     matchingMethod = false;
                     break;
                  }
               }

               if (matchingMethod) {
                  ejbMethodName = initMethBean.getBeanMethod().getMethodName();
                  break;
               }
            }
         } else if (initMethBean.getCreateMethod().getMethodName().equalsIgnoreCase(m.getName())) {
            return initMethBean.getBeanMethod().getMethodName();
         }
      }

      if (ejbMethodName == null) {
         ejbMethodName = "ejb" + m.getName().substring(0, 1).toUpperCase(Locale.ENGLISH) + m.getName().substring(1);
      }

      return ejbMethodName;
   }

   public boolean isTimerDriven() {
      return false;
   }

   public boolean isPassivationCapable() {
      if (this.beanIsPassivationCapableReSet) {
         return this.beanIsPassivationCapable;
      } else {
         this.beanIsPassivationCapable = this.beanIsPassivationCapable && ((StatefulSessionManager)this.getBeanManager()).allExtendedPCSerializable();
         this.beanIsPassivationCapableReSet = true;
         return this.beanIsPassivationCapable;
      }
   }

   public BeanManager getBeanManagerInstance(EJBRuntimeHolder compRuntime) {
      return (BeanManager)(this.isReplicated() ? new ReplicatedStatefulSessionManager(compRuntime) : new StatefulSessionManager(compRuntime));
   }

   public boolean isAllowRemoveDuringTx() {
      return this.allowRemoveDuringTx;
   }

   protected ConcurrencyInfo getConcurrencyInfo(MethodInfo mi) {
      return (ConcurrencyInfo)this.concurrencyInfos.get(new MethodKey(mi.getMethod()));
   }

   public MethodDescriptor getPostConstructMethodDescriptor() {
      return this.postConstructMethodDescriptor;
   }

   public MethodDescriptor getPreDestroyMethodDescriptor() {
      return this.preDestroyMethodDescriptor;
   }

   public MethodDescriptor getPostActivateMethodDescriptor() {
      return this.postActivateMethodDescriptor;
   }

   public MethodDescriptor getPrePassivateMethodDescriptor() {
      return this.prePassivateMethodDescriptor;
   }
}
