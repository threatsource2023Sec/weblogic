package weblogic.ejb.container.deployer;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import weblogic.application.naming.Environment;
import weblogic.application.naming.ModuleRegistry;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.deployer.mbimpl.ConcurrencyInfoImpl;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SingletonSessionBeanInfo;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.SingletonEJBHomeImpl;
import weblogic.ejb.container.internal.SingletonEJBLocalHomeImpl;
import weblogic.ejb.container.manager.SingletonSessionManager;
import weblogic.ejb.container.utils.MethodKey;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.ConcurrentMethodBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.MethodParamsBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.logging.Loggable;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.classloaders.GenericClassLoader;

class SingletonSessionBeanInfoImpl extends SessionBeanInfoImpl implements SingletonSessionBeanInfo {
   private final boolean initOnStartup;
   private final boolean isCMC;
   private final Set dependsOn;
   private Map concurrencyInfos;
   private SingletonDependencyResolver dependencyResolver;
   private MethodInfo postConstructMethodInfo;
   private MethodInfo preDestroyMethodInfo;
   private MethodDescriptor postConstructMethodDescriptor;
   private MethodDescriptor preDestroyMethodDescriptor;

   public SingletonSessionBeanInfoImpl(DeploymentInfo di, CompositeDescriptor desc, GenericClassLoader cl) throws ClassNotFoundException, WLDeploymentException {
      super(di, desc, cl);
      SessionBeanBean sb = (SessionBeanBean)desc.getBean();
      this.initOnStartup = sb.isInitOnStartup();
      this.isCMC = !"Bean".equals(sb.getConcurrencyManagement());
      if (sb.getDependsOn() != null) {
         Set set = new HashSet();
         String[] var6 = sb.getDependsOn().getEjbNames();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String s = var6[var8];
            set.add(s);
         }

         this.dependsOn = Collections.unmodifiableSet(set);
      } else {
         this.dependsOn = Collections.emptySet();
      }

   }

   protected BaseBeanUpdateListener getBeanUpdateListener() {
      return new SingletonSessionBeanUpdateListener(this);
   }

   protected String getSyntheticHomeImplClassName() {
      return SingletonEJBHomeImpl.class.getName();
   }

   protected String getSyntheticLocalHomeImplClassName() {
      return SingletonEJBLocalHomeImpl.class.getName();
   }

   public SingletonSessionManager getBeanManagerInstance(EJBRuntimeHolder mbean) {
      return new SingletonSessionManager(mbean);
   }

   public boolean usesContainerManagedConcurrency() {
      return this.isCMC;
   }

   public boolean initOnStartup() {
      return this.initOnStartup;
   }

   private void initializeConcurrencyInfoMappings() {
      SessionBeanBean sbb = (SessionBeanBean)this.compDesc.getBean();
      if (this.isCMC && sbb.getConcurrentMethods() != null) {
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

   public void prepare(Environment env) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("prepare() for ejb:" + this.getEJBName());
      }

      this.initializeConcurrencyInfoMappings();
      super.prepare(env);
      if (null != this.postConstructMethodInfo) {
         if (1 == this.postConstructMethodInfo.getTransactionAttribute()) {
            this.postConstructMethodInfo.setTransactionAttribute((short)3);
         }

         this.postConstructMethodDescriptor = this.getCallbackMethodDescriptor(this.postConstructMethodInfo);
      }

      if (null != this.preDestroyMethodInfo) {
         if (1 == this.preDestroyMethodInfo.getTransactionAttribute()) {
            this.preDestroyMethodInfo.setTransactionAttribute((short)3);
         }

         this.preDestroyMethodDescriptor = this.getCallbackMethodDescriptor(this.preDestroyMethodInfo);
      }

   }

   protected ConcurrencyInfo getConcurrencyInfo(MethodInfo mi) {
      return (ConcurrencyInfo)this.concurrencyInfos.get(new MethodKey(mi.getMethod()));
   }

   public String getEjbCreateInitMethodName(Method m) {
      throw new AssertionError("Should not get called");
   }

   public void registerSingletonDependencyResolver(ModuleRegistry mr) {
      if (!this.dependsOn.isEmpty()) {
         DeploymentInfo di = this.getDeploymentInfo();
         this.dependencyResolver = new SingletonDependencyResolver(di.getApplicationId(), di.getModuleId(), di.getModuleURI(), this.dependsOn, this);
         mr.addReferenceResolver(this.dependencyResolver);
      }

   }

   public SingletonDependencyResolver getSingletonDependencyResolver() {
      return this.dependencyResolver;
   }

   public MethodDescriptor getPostConstructMethodDescriptor() {
      return this.postConstructMethodDescriptor;
   }

   public MethodDescriptor getPreDestroyMethodDescriptor() {
      return this.preDestroyMethodDescriptor;
   }

   public void initializeAllMethodInfos() throws WLDeploymentException {
      super.initializeAllMethodInfos();

      try {
         EnterpriseBeanBean ebb = this.compDesc.getBean();
         Set callbacks = new HashSet();
         Method postConstructMethod = this.addCallbackMethods(callbacks, ((SessionBeanBean)ebb).getPostConstructs());
         Method preDestroyMethod = this.addCallbackMethods(callbacks, ((SessionBeanBean)ebb).getPreDestroys());
         this.createMethodInfos("LifecycleCallback", this.callbackMethods, (Method[])callbacks.toArray(new Method[0]));
         if (null != postConstructMethod) {
            this.postConstructMethodInfo = (MethodInfo)this.callbackMethods.get(DDUtils.getMethodSignature(postConstructMethod));
         }

         if (null != preDestroyMethod) {
            this.preDestroyMethodInfo = (MethodInfo)this.callbackMethods.get(DDUtils.getMethodSignature(preDestroyMethod));
         }

      } catch (Throwable var5) {
         Loggable l = EJBLogger.logunableToInitializeInterfaceMethodInfoLoggable(this.getEJBName(), StackTraceUtilsClient.throwable2StackTrace(var5));
         throw new WLDeploymentException(l.getMessageText(), var5);
      }
   }

   public String toString() {
      return this.getDisplayName();
   }
}
