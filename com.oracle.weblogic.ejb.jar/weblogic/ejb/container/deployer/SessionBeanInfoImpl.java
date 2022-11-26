package weblogic.ejb.container.deployer;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.Environment;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.EJBCheckerFactory;
import weblogic.ejb.container.compliance.session.SessionBeanCheckerFactory;
import weblogic.ejb.container.dd.DDDefaults;
import weblogic.ejb.container.interfaces.BaseEJBHomeIntf;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.Ejb3SessionHome;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.AsyncInvocationManager;
import weblogic.ejb.container.internal.ClientViewDescriptor;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.ejb.container.utils.MethodKey;
import weblogic.ejb.spi.BusinessObject;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.SessionBeanReference;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb.spi.WSObjectFactory;
import weblogic.j2ee.descriptor.AsyncMethodBean;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.jndi.internal.JNDIHelper;
import weblogic.logging.Loggable;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.classloaders.GenericClassLoader;

abstract class SessionBeanInfoImpl extends ClientDrivenBeanInfoImpl implements SessionBeanInfo {
   private static final int TYPE_STATELESS = 1;
   private static final int TYPE_STATEFUL = 2;
   private static final int TYPE_SINGLETON = 4;
   private final int beanType;
   private final Set busLocalIntfs = new HashSet();
   private final Map busLocalImpls = new HashMap();
   private final Set busRemoteIntfs = new HashSet();
   private final Map busRemoteImpls = new HashMap();
   private final Map genBusRemoteIntfs = new HashMap();
   private final Map jndiNameMap = new HashMap();
   private final boolean usesBeanManagedTx;
   private final boolean isEndpointView;
   private final String generatedBeanClassName;
   private final String generatedBeanInterfaceName;
   private Class generatedBeanClass;
   private Class generatedBeanInterface;
   private final boolean hasNoIntfView;
   private Class noIntfViewImpl;
   private final Set asyncMethods = new HashSet();
   private final BaseBeanUpdateListener ul;
   private AsyncInvocationManager asyncInvManager;

   public SessionBeanInfoImpl(DeploymentInfo di, CompositeDescriptor desc, GenericClassLoader moduleCL) throws ClassNotFoundException, WLDeploymentException {
      super(di, desc, moduleCL);
      SessionBeanBean sb = (SessionBeanBean)desc.getBean();
      String type = sb.getSessionType();
      if ("Stateless".equalsIgnoreCase(type)) {
         this.beanType = 1;
      } else if ("Stateful".equalsIgnoreCase(type)) {
         this.beanType = 2;
      } else {
         if (!"Singleton".equalsIgnoreCase(type)) {
            throw new AssertionError("Unknown Session Bean type : " + type);
         }

         this.beanType = 4;
      }

      if ((this.beanType == 1 || this.beanType == 4) && this.wseeAnnotationProcessor != null && this.wseeAnnotationProcessor.hasWSAnnotation(this.getBeanClass())) {
         this.isEndpointView = true;
      } else {
         this.isEndpointView = false;
      }

      this.usesBeanManagedTx = "Bean".equalsIgnoreCase(sb.getTransactionType());
      NamingConvention nc = new NamingConvention(sb.getEjbClass(), sb.getEjbName());
      this.generatedBeanClassName = nc.getGeneratedBeanClassName();
      if (di.isEnableBeanClassRedeploy()) {
         moduleCL.excludeClass(this.generatedBeanClassName);
      }

      this.generatedBeanInterfaceName = nc.getGeneratedBeanInterfaceName();
      String[] var7 = desc.getBusinessLocals();
      int var8 = var7.length;

      int var9;
      String ifaceName;
      Class remoteIface;
      for(var9 = 0; var9 < var8; ++var9) {
         ifaceName = var7[var9];
         remoteIface = this.loadClass(ifaceName);
         this.busLocalIntfs.add(remoteIface);
         this.checkClassLoaders(desc, remoteIface);
         this.constructJNDINames(remoteIface, desc.getBusinessJNDIName(remoteIface));
      }

      var7 = desc.getBusinessRemotes();
      var8 = var7.length;

      for(var9 = 0; var9 < var8; ++var9) {
         ifaceName = var7[var9];
         remoteIface = this.loadClass(ifaceName);
         this.busRemoteIntfs.add(remoteIface);
         this.checkClassLoaders(desc, remoteIface);
         this.constructJNDINames(remoteIface, this.transformJndiName(desc.getBusinessJNDIName(remoteIface)));
      }

      this.hasNoIntfView = sb.getLocalBean() != null || !super.hasLocalClientView() && this.busLocalIntfs.isEmpty() && !this.hasRemoteClientView() && !this.hasWebserviceClientView();
      if (this.hasNoIntfView) {
         this.constructJNDINames(this.getBeanClass(), desc.getBusinessJNDIName(this.getBeanClass()));
      }

      int views = this.getLocalHomeInterfaceName() != null ? 1 : 0;
      views += this.getHomeInterfaceName() != null ? 1 : 0;
      views += this.getBusinessLocals().size() + this.getBusinessRemotes().size();
      views += this.hasNoIntfView ? 1 : 0;
      if (views == 1) {
         String ifaceName = this.getLocalHomeInterfaceName();
         Object jndiNames;
         if (ifaceName != null) {
            jndiNames = this.getLocalPortableJNDINames();
         } else {
            ifaceName = this.getHomeInterfaceName();
            if (ifaceName != null) {
               jndiNames = this.getPortableJNDINames();
            } else {
               Class iface;
               if (this.getBusinessLocals().size() == 1) {
                  iface = (Class)this.getBusinessLocals().iterator().next();
               } else if (this.getBusinessRemotes().size() == 1) {
                  iface = (Class)this.getBusinessRemotes().iterator().next();
               } else {
                  iface = this.getBeanClass();
               }

               ifaceName = iface.getName();
               jndiNames = (Set)this.jndiNameMap.get(iface);
               if (jndiNames == null) {
                  jndiNames = new HashSet();
                  this.jndiNameMap.put(iface, jndiNames);
               }
            }
         }

         this.constructPortableNames((String)null, (Set)jndiNames);
      }

      AsyncMethodBean[] var15 = ((SessionBeanBean)desc.getBean()).getAsyncMethods();
      var9 = var15.length;

      for(int var18 = 0; var18 < var9; ++var18) {
         AsyncMethodBean mb = var15[var18];
         this.asyncMethods.add(new MethodKey(mb.getMethodName(), mb.getMethodParams() == null ? null : mb.getMethodParams().getMethodParams()));
      }

      this.initializeMethodInfos();
      this.ul = this.getBeanUpdateListener();
   }

   private void constructJNDINames(Class iface, String jndiNameStr) {
      Set jndiNames = (Set)this.jndiNameMap.get(iface);
      if (jndiNames == null) {
         jndiNames = new HashSet();
      }

      if (jndiNameStr != null) {
         ((Set)jndiNames).add(jndiNameStr);
      }

      this.constructPortableNames(iface.getName(), (Set)jndiNames);
      this.jndiNameMap.put(iface, jndiNames);
   }

   private void initializeMethodInfos() {
      if (!this.busRemoteIntfs.isEmpty()) {
         this.createMethodInfos("Remote", this.remoteMethods, BusinessObject.class.getMethods());
      }

      Iterator var1 = this.busRemoteIntfs.iterator();

      Class iface;
      while(var1.hasNext()) {
         iface = (Class)var1.next();
         this.createMethodInfos("Remote", this.remoteMethods, iface.getMethods());
      }

      var1 = this.busLocalIntfs.iterator();

      while(var1.hasNext()) {
         iface = (Class)var1.next();
         this.createMethodInfos("Local", this.localMethods, iface.getMethods());
      }

      if (this.hasNoIntfView()) {
         this.createMethodInfos("Local", this.localMethods, EJBMethodsUtil.getNoInterfaceViewBusinessMethods(this.getBeanClass()));
      }

   }

   protected Method addCallbackMethods(Set methods, LifecycleCallbackBean[] lcbs) {
      if (null == lcbs) {
         return null;
      } else {
         LifecycleCallbackBean[] var3 = lcbs;
         int var4 = lcbs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            LifecycleCallbackBean lcb = var3[var5];
            if (lcb.getLifecycleCallbackClass().equals(this.getBeanClassName())) {
               try {
                  Method m = this.getBeanClass().getDeclaredMethod(lcb.getLifecycleCallbackMethod());
                  methods.add(m);
                  return m;
               } catch (NoSuchMethodException var9) {
                  Loggable l = EJBLogger.logNoLifecycleCallbackFoundinBeanClassLoggable(lcb.getLifecycleCallbackMethod(), this.getBeanClass().getName(), this.getEJBName());
                  throw new IllegalArgumentException(l.getMessage(), var9);
               }
            }
         }

         return null;
      }
   }

   protected MethodDescriptor getCallbackMethodDescriptor(MethodInfo mi) throws WLDeploymentException {
      mi.setUnchecked(true);
      ClientViewDescriptor cvd = new ClientViewDescriptor(this.getBeanClass(), "LifecycleCallback", true, false, this);
      MethodDescriptor md = this.createMethodDescriptor(mi.getMethod(), this.getBeanClass(), mi, cvd);
      this.methodDescriptors.add(md);
      return md;
   }

   protected abstract BaseBeanUpdateListener getBeanUpdateListener();

   public boolean hasAsyncMethods() {
      return !this.asyncMethods.isEmpty();
   }

   public AsyncInvocationManager getAsyncInvocationManager() {
      if (!this.hasAsyncMethods()) {
         throw new AssertionError("Should not be invoked");
      } else {
         if (this.asyncInvManager == null) {
            this.asyncInvManager = new AsyncInvocationManager(this);
         }

         return this.asyncInvManager;
      }
   }

   public boolean isAsyncMethod(Method m) {
      return this.asyncMethods.contains(new MethodKey(m));
   }

   public boolean hasBusinessRemotes() {
      return !this.busRemoteIntfs.isEmpty();
   }

   public boolean hasBusinessLocals() {
      return !this.busLocalIntfs.isEmpty();
   }

   public boolean hasNoIntfView() {
      return this.hasNoIntfView;
   }

   public String[] getImplementedInterfaceNames() {
      if (!this.hasBusinessLocals() && !this.hasBusinessRemotes()) {
         return super.getImplementedInterfaceNames();
      } else {
         String[] bls = this.compDesc.getBusinessLocals();
         String[] brs = this.compDesc.getBusinessRemotes();
         String[] homes = super.getImplementedInterfaceNames();
         String[] intfs = new String[bls.length + brs.length + homes.length];
         int i = 0;
         String[] var6 = homes;
         int var7 = homes.length;

         int var8;
         String s;
         for(var8 = 0; var8 < var7; ++var8) {
            s = var6[var8];
            intfs[i++] = s;
         }

         var6 = bls;
         var7 = bls.length;

         for(var8 = 0; var8 < var7; ++var8) {
            s = var6[var8];
            intfs[i++] = s;
         }

         var6 = brs;
         var7 = brs.length;

         for(var8 = 0; var8 < var7; ++var8) {
            s = var6[var8];
            intfs[i++] = s;
         }

         return intfs;
      }
   }

   public boolean hasClientViewFor(String clsName) {
      if (clsName == null) {
         return false;
      } else if (super.hasClientViewFor(clsName)) {
         return true;
      } else if (this.hasNoIntfView && this.getBeanClass().getName().equals(clsName)) {
         return true;
      } else {
         String[] var2 = this.compDesc.getBusinessLocals();
         int var3 = var2.length;

         int var4;
         String s;
         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            if (clsName.equals(s)) {
               return true;
            }
         }

         var2 = this.compDesc.getBusinessRemotes();
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            if (clsName.equals(s)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isLocalClientView(Class c) {
      return this.busLocalIntfs.contains(c) || this.hasNoIntfView && this.getBeanClass().equals(c);
   }

   public SessionBeanReference getSessionBeanReference() {
      return this.localHome instanceof Ejb3LocalHome ? ((Ejb3LocalHome)this.localHome).getSessionBeanReference() : null;
   }

   public Map getBusinessJNDINames() {
      return this.jndiNameMap;
   }

   public String getCustomJndiName(Class iface) {
      Set names = (Set)this.jndiNameMap.get(iface);
      if (names != null) {
         Iterator var3 = names.iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            if (!name.startsWith("java:")) {
               return name;
            }
         }
      }

      return null;
   }

   public Set getBusinessLocals() {
      return this.busLocalIntfs;
   }

   public Set getBusinessRemotes() {
      return this.busRemoteIntfs;
   }

   public boolean hasRemoteClientView() {
      return super.hasRemoteClientView() || !this.busRemoteIntfs.isEmpty();
   }

   public boolean hasLocalClientView() {
      return super.hasLocalClientView() || !this.busLocalIntfs.isEmpty() || this.hasNoIntfView();
   }

   public Class getGeneratedLocalBusinessImplClass(Class iface) {
      return (Class)this.busLocalImpls.get(iface);
   }

   public Class getGeneratedNoIntfViewImplClass() {
      return this.noIntfViewImpl;
   }

   public Class getGeneratedRemoteBusinessImplClass(Class iface) {
      return (Class)this.busRemoteImpls.get(iface);
   }

   public Class getGeneratedRemoteBusinessIntfClass(Class iface) {
      return (Class)this.genBusRemoteIntfs.get(iface);
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

   public final boolean isStateful() {
      return (this.beanType & 2) != 0;
   }

   public final boolean isSingleton() {
      return (this.beanType & 4) != 0;
   }

   public final boolean isStateless() {
      return (this.beanType & 1) != 0;
   }

   public boolean usesBeanManagedTx() {
      return this.usesBeanManagedTx;
   }

   public boolean hasWebserviceClientView() {
      if (!this.isEJB30()) {
         return super.hasWebserviceClientView();
      } else {
         return super.hasWebserviceClientView() || this.wseeAnnotationProcessor != null && this.wseeAnnotationProcessor.hasWSAnnotation(this.getBeanClass());
      }
   }

   protected boolean checkIfItsSafeToUseCallByReference() {
      boolean result = super.checkIfItsSafeToUseCallByReference();
      Iterator var2 = this.busRemoteIntfs.iterator();

      while(var2.hasNext()) {
         Class remoteIface = (Class)var2.next();
         Method[] var4 = remoteIface.getMethods();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Method m = var4[var6];
            if (!this.checkIfMethodCanUseCallByReference(m)) {
               return false;
            }
         }
      }

      return result;
   }

   protected void warnIfParameterNotSerializable() {
      super.warnIfParameterNotSerializable();
      Iterator var1 = this.busRemoteIntfs.iterator();

      while(var1.hasNext()) {
         Class intf = (Class)var1.next();
         Method[] var3 = intf.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method m = var3[var5];
            this.warnIfParamNotSerializableForMethod(m);
         }
      }

   }

   public void prepare(Environment env) throws WLDeploymentException {
      super.prepare(env);
      NamingConvention nc = new NamingConvention(this.getBeanClassName(), this.getEJBName());
      Iterator var3 = this.busRemoteIntfs.iterator();

      Class localIface;
      Class lbi;
      ClientViewDescriptor locBusView;
      while(var3.hasNext()) {
         localIface = (Class)var3.next();
         lbi = this.loadForSure(nc.getRemoteBusinessImplClassName(localIface));
         this.busRemoteImpls.put(localIface, lbi);
         locBusView = new ClientViewDescriptor(localIface, "Remote", false, true, this);
         this.setMethodDescriptors((BaseEJBHomeIntf)null, lbi, localIface.getMethods(), locBusView);
         if (!Remote.class.isAssignableFrom(localIface)) {
            Class c = this.loadForSure(nc.getRemoteBusinessIntfClassName(localIface));
            this.genBusRemoteIntfs.put(localIface, c);
         }

         ClientViewDescriptor busObjClientView = new ClientViewDescriptor(BusinessObject.class, "Remote", false, false, this);
         this.setMethodDescriptors((BaseEJBHomeIntf)null, lbi, BusinessObject.class.getMethods(), busObjClientView);
      }

      var3 = this.busLocalIntfs.iterator();

      while(var3.hasNext()) {
         localIface = (Class)var3.next();
         lbi = this.loadForSure(nc.getLocalBusinessImplClassName(localIface));
         this.busLocalImpls.put(localIface, lbi);
         locBusView = new ClientViewDescriptor(localIface, "Local", true, true, this);
         this.setMethodDescriptors((BaseEJBHomeIntf)null, lbi, localIface.getMethods(), locBusView);
      }

      if (this.hasNoIntfView()) {
         this.noIntfViewImpl = this.loadForSure(nc.getNoIntfViewImplClassName());
         ClientViewDescriptor noIntfView = new ClientViewDescriptor(this.getBeanClass(), "Local", true, true, this);
         this.setMethodDescriptors((BaseEJBHomeIntf)null, this.noIntfViewImpl, EJBMethodsUtil.getNoInterfaceViewBusinessMethods(this.getBeanClass()), noIntfView);
      }

      if (this.hasBusinessRemotes()) {
         ((Ejb3SessionHome)this.getRemoteHome()).prepare();
      }

      if (this.hasBusinessLocals() || this.hasNoIntfView()) {
         ((Ejb3SessionHome)this.getLocalHome()).prepare();
      }

      var3 = this.jndiNameMap.values().iterator();

      while(true) {
         Set names;
         do {
            if (!var3.hasNext()) {
               return;
            }

            names = (Set)var3.next();
         } while(names == null);

         Iterator var23 = names.iterator();

         while(var23.hasNext()) {
            String jndiNameStr = (String)var23.next();
            InitialContext ctx = null;

            try {
               if ("true".equalsIgnoreCase(System.getProperty("weblogic.jndi.relaxVersionLookup", ""))) {
                  Properties props = new Properties();
                  props.put("weblogic.jndi.relaxVersionLookup", "false");
                  ctx = new InitialContext(props);
               } else {
                  ctx = new InitialContext();
               }

               Object o = ctx.lookup(jndiNameStr);
               if (o != null) {
                  Loggable l = EJBLogger.logJNDINameAlreadyInUseLoggable(this.getDisplayName(), jndiNameStr);
                  if (!(o instanceof Remote)) {
                     throw new WLDeploymentException(l.getMessageText());
                  }

                  Remote r = (Remote)o;
                  if (!(o instanceof Proxy) && (!ServerHelper.isClusterable(r) || ServerHelper.isLocal(r)) || o instanceof Proxy && !JNDIHelper.isBindable(JNDIHelper.getInitialWLContext(ctx.getEnvironment()), jndiNameStr, true)) {
                     throw new WLDeploymentException(l.getMessageText());
                  }
               }
            } catch (NamingException var19) {
               if (!(var19 instanceof NameNotFoundException)) {
                  EJBLogger.logStackTraceAndMessage("Unexpected NamingException occurred during deploying " + this.getDisplayName() + " with jndiNameStr = " + jndiNameStr, var19);
               }
            } finally {
               if (ctx != null) {
                  try {
                     ctx.close();
                  } catch (NamingException var18) {
                  }
               }

            }
         }
      }
   }

   public EjbJndiBinder getJndiBinder() throws NamingException {
      if (this.jndiBinder == null) {
         this.jndiBinder = new Ejb3SessionBinder(this, this.getEnvBuilder());
      }

      return this.jndiBinder;
   }

   public Object getBindable(String ifaceName) {
      Object result = super.getBindable(ifaceName);
      if (result != null) {
         return result;
      } else {
         if (this.hasBusinessRemotes()) {
            result = ((Ejb3SessionHome)this.getRemoteHome()).getBindableImpl(ifaceName);
            if (result != null) {
               return result;
            }
         }

         if (this.hasBusinessLocals() || this.hasNoIntfView()) {
            result = ((Ejb3SessionHome)this.getLocalHome()).getBindableImpl(ifaceName);
            if (result != null) {
               return result;
            }
         }

         throw new AssertionError("Could not find object to bind for view: " + ifaceName);
      }
   }

   protected EJBCache getCache(Map cacheMap) {
      return null;
   }

   protected short getTxAttribute(MethodInfo methodInfo, Class c) {
      if (this.usesBeanManagedTx) {
         return 0;
      } else {
         String n;
         if (!EJBHome.class.isAssignableFrom(c) && !EJBLocalHome.class.isAssignableFrom(c)) {
            if (EJBObject.class.isAssignableFrom(c) || EJBLocalObject.class.isAssignableFrom(c)) {
               n = methodInfo.getMethodName();
               String[] nParams = methodInfo.getMethodParams();
               if (n.equals("remove") && (nParams == null || nParams.length == 0)) {
                  return this.getTxAttributeOfRemove();
               }
            }
         } else {
            n = methodInfo.getMethodName();
            if (n.equals("remove") || n.startsWith("create")) {
               return 0;
            }
         }

         return methodInfo.getTransactionAttribute();
      }
   }

   protected short getTxAttributeOfRemove() {
      return 0;
   }

   public void assignDefaultTXAttributesIfNecessary(int jtaConfigTimeout) {
      super.setupTxTimeout(jtaConfigTimeout);
      if (this.usesBeanManagedTx) {
         Iterator it = this.getAllMethodInfosIterator();

         while(it.hasNext()) {
            ((MethodInfo)it.next()).setTransactionAttribute((short)0);
         }
      } else {
         List methodInfos = new ArrayList();
         if (this.hasDeclaredRemoteHome()) {
            methodInfos.addAll(this.getAllHomeMethodInfos());
            methodInfos.add(this.getRemoteMethodInfo("remove()"));
            methodInfos.add(this.getRemoteMethodInfo("getEJBHome()"));
            methodInfos.add(this.getRemoteMethodInfo("getHandle()"));
            methodInfos.add(this.getRemoteMethodInfo("getPrimaryKey()"));
            methodInfos.add(this.getRemoteMethodInfo("isIdentical(javax.ejb.EJBObject)"));
         }

         if (this.hasDeclaredLocalHome()) {
            methodInfos.addAll(this.getAllLocalHomeMethodInfos());
            methodInfos.add(this.getLocalMethodInfo("remove()"));
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

   }

   protected void checkUpdatedClass(Class beanImpl) throws WLDeploymentException {
   }

   public void updateImplClassLoader() throws WLDeploymentException {
      super.updateImplClassLoader();

      try {
         this.generatedBeanClass = this.loadClass(this.generatedBeanClassName);
      } catch (ClassNotFoundException var3) {
         throw new WLDeploymentException("Could not load updated impl class: " + var3);
      }

      this.checkUpdatedClass(this.generatedBeanClass);

      try {
         this.getBeanManager().beanImplClassChangeNotification();
      } catch (UnsupportedOperationException var2) {
         throw new WLDeploymentException("Bean Manager does not support partial updates");
      }
   }

   public WSObjectFactory getWSObjectFactory() {
      return this.webserviceObjectFactory;
   }

   public boolean isSessionBean() {
      return true;
   }

   public boolean isEndpointView() {
      return this.isEndpointView;
   }

   public void addBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescriptor, ApplicationContextInternal appCtx) {
      this.ul.addBeanUpdateListener(wlBean, ejbDescriptor);
   }

   public void removeBeanUpdateListener(WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean ejbDescriptor, ApplicationContextInternal appCtx) {
      this.ul.removeBeanUpdateListener(wlBean, ejbDescriptor);
   }

   public EJBCheckerFactory getEJBCheckerFactory(DeploymentInfo di) {
      return new SessionBeanCheckerFactory(di, this);
   }
}
