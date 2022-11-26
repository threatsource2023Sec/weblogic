package weblogic.ejb.container.deployer;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.application.naming.Environment;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.dd.ClusteringDescriptor;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.dd.DDDefaults;
import weblogic.ejb.container.deployer.mbimpl.MethodInfoImpl;
import weblogic.ejb.container.interfaces.BaseEJBHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingManager;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.internal.ClientViewDescriptor;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.internal.WSObjectFactoryImpl;
import weblogic.ejb.container.monitoring.EJBRuntimeMBeanImpl;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb.spi.WSObjectFactory;
import weblogic.j2ee.dd.xml.WseeAnnotationProcessor;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.ExecuteQueueRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.annotation.BeaSynthetic.Helper;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.CombinedIterator;
import weblogic.utils.reflect.MethodText;

abstract class ClientDrivenBeanInfoImpl extends BeanInfoImpl implements ClientDrivenBeanInfo {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Name jndiName;
   private final String jndiNameAsString;
   private final Set portableJndiNames = new HashSet();
   private Name localJndiName;
   private String localJndiNameAsString;
   private final Set localPortableJndiNames = new HashSet();
   private final String homeInterfaceName;
   private final String remoteInterfaceName;
   private String homeClassName;
   private String ejbObjectClassName;
   private Class homeInterfaceClass;
   private Class remoteInterfaceClass;
   private Class homeClass;
   private Class ejbObjectClass;
   private final String localHomeInterfaceName;
   private final String localInterfaceName;
   private String localHomeClassName;
   private String ejbLocalObjectClassName;
   private Class localHomeInterfaceClass;
   private Class localInterfaceClass;
   private Class localHomeClass;
   private Class localClass;
   private String serviceEndpointName;
   private Class serviceEndpointClass;
   private String webserviceObjectClassName;
   private Class webserviceObjectClass;
   protected WSObjectFactory webserviceObjectFactory;
   protected final Map remoteMethods = new HashMap();
   protected final Map homeMethods = new HashMap();
   protected final Map localHomeMethods = new HashMap();
   protected final Map localMethods = new HashMap();
   protected final Map callbackMethods = new HashMap();
   protected Map webserviceMethods = null;
   protected final WseeAnnotationProcessor wseeAnnotationProcessor;
   protected BaseEJBRemoteHomeIntf remoteHome;
   protected BaseEJBLocalHomeIntf localHome;
   protected EjbJndiBinder jndiBinder;
   private ClusteringDescriptor clusteringDescriptor;
   private boolean callByReference;

   ClientDrivenBeanInfoImpl(DeploymentInfo di, CompositeDescriptor desc, GenericClassLoader moduleCL) throws ClassNotFoundException, WLDeploymentException {
      super(di, desc, moduleCL);
      if (isServer()) {
         super.checkSecurityRoleRefs();
      }

      this.wseeAnnotationProcessor = (WseeAnnotationProcessor)GlobalServiceLocator.getServiceLocator().getService(WseeAnnotationProcessor.class, new Annotation[0]);
      this.homeInterfaceName = desc.getHomeInterfaceName();
      this.remoteInterfaceName = desc.getRemoteInterfaceName();
      this.localHomeInterfaceName = desc.getLocalHomeInterfaceName();
      this.localInterfaceName = desc.getLocalInterfaceName();
      String name = this.compDesc.getJNDIName();
      if (this.isEJB30() && null == name && desc.isSession()) {
         String[] businessRemotes = desc.getBusinessRemotes();
         if (businessRemotes != null && businessRemotes.length > 0) {
            name = this.getGeneratedJndiNameForHome();
         }
      }

      this.jndiNameAsString = this.transformJndiName(name);
      this.jndiName = this.jndiNameAsString != null ? this.getName(this.jndiNameAsString) : null;
      if (desc.isStatelessSession()) {
         this.serviceEndpointName = desc.getServiceEndpointName();
      }

      this.checkClientViews();
   }

   public final String getGeneratedJndiNameForHome() {
      return this.getGeneratedJndiNameFor("Home");
   }

   public final String getGeneratedJndiNameFor(String suffix) {
      return this.getIsIdenticalKey().replace('.', '_') + "_" + suffix;
   }

   protected void constructPortableNames(String viewName, Set names) {
      StringBuilder buf = new StringBuilder();
      if (viewName != null) {
         buf.append("!").append(viewName);
      }

      buf.insert(0, this.getEJBName());
      names.add("java:module/" + buf);
      buf.insert(0, this.getDeploymentInfo().getModuleName() + "/");
      names.add("java:app/" + buf);
      if (this.getDeploymentInfo().isEar()) {
         buf.insert(0, this.getDeploymentInfo().getApplicationName() + "/");
      }

      names.add("java:global/" + buf);
   }

   public void init() throws ClassNotFoundException, WLDeploymentException {
      NamingConvention nc = new NamingConvention(this.getBeanClassName(), this.getEJBName());
      this.localJndiNameAsString = this.transformJndiName(this.compDesc.getLocalJNDIName());
      this.localJndiName = this.getName(this.localJndiNameAsString);
      if (this.hasRemoteClientView()) {
         if (this.hasDeclaredRemoteHome()) {
            this.homeClassName = nc.getHomeClassName();
            this.homeInterfaceClass = this.loadClass(this.homeInterfaceName);
            this.checkClassLoaders(this.compDesc, this.homeInterfaceClass);
            this.remoteInterfaceClass = this.loadClass(this.remoteInterfaceName);
            this.checkClassLoaders(this.compDesc, this.remoteInterfaceClass);
            this.constructPortableNames(this.homeInterfaceName, this.portableJndiNames);
         } else {
            this.homeClassName = this.getSyntheticHomeImplClassName();
         }

         this.ejbObjectClassName = nc.getEJBObjectClassName();
         this.clusteringDescriptor = this.compDesc.getClusteringDescriptor();
      }

      if (this.hasLocalClientView()) {
         if (this.hasDeclaredLocalHome()) {
            this.localHomeClassName = nc.getLocalHomeClassName();
            this.localHomeInterfaceClass = this.loadClass(this.localHomeInterfaceName);
            this.checkClassLoaders(this.compDesc, this.localHomeInterfaceClass);
            this.localInterfaceClass = this.loadClass(this.localInterfaceName);
            this.checkClassLoaders(this.compDesc, this.localInterfaceClass);
            this.constructPortableNames(this.localHomeInterfaceName, this.localPortableJndiNames);
         } else {
            this.localHomeClassName = this.getSyntheticLocalHomeImplClassName();
         }

         this.ejbLocalObjectClassName = nc.getEJBLocalObjectClassName();
      }

      if (this.hasWebserviceClientView()) {
         this.webserviceObjectClassName = nc.getWsObjectClassName();
         if (this.serviceEndpointName != null) {
            this.serviceEndpointClass = this.loadClass(this.serviceEndpointName);
         }

         this.webserviceObjectClass = null;
      }

      this.callByReference = this.compDesc.useCallByReference();
      if (!this.callByReference) {
         this.callByReference = this.checkIfItsSafeToUseCallByReference();
      }

      if (!this.callByReference) {
         this.warnIfParameterNotSerializable();
      }

      this.initializeAllMethodInfos();
   }

   public abstract String getGeneratedBeanClassName();

   public abstract Class getGeneratedBeanClass();

   public abstract String getGeneratedBeanInterfaceName();

   public abstract Class getGeneratedBeanInterface();

   public Name getJNDIName() {
      return this.jndiName;
   }

   public String getJNDINameAsString() {
      return this.jndiNameAsString;
   }

   public Set getPortableJNDINames() {
      return this.portableJndiNames;
   }

   public Name getLocalJNDIName() {
      return this.localJndiName;
   }

   public String getLocalJNDINameAsString() {
      return this.localJndiNameAsString;
   }

   public Set getLocalPortableJNDINames() {
      return this.localPortableJndiNames;
   }

   public Class getHomeClass() {
      if (this.homeClass == null) {
         this.homeClass = this.loadForSure(this.homeClassName);
      }

      return this.homeClass;
   }

   public Class getLocalHomeClass() {
      if (this.localHomeClass == null) {
         this.localHomeClass = this.loadForSure(this.localHomeClassName);
      }

      return this.localHomeClass;
   }

   public String getHomeInterfaceName() {
      return this.homeInterfaceName;
   }

   public String getLocalHomeInterfaceName() {
      return this.localHomeInterfaceName;
   }

   public boolean hasDeclaredRemoteHome() {
      return this.homeInterfaceName != null;
   }

   public boolean hasDeclaredLocalHome() {
      return this.localHomeInterfaceName != null;
   }

   public Class getHomeInterfaceClass() {
      return this.homeInterfaceClass;
   }

   public Class getLocalHomeInterfaceClass() {
      return this.localHomeInterfaceClass;
   }

   public Class getRemoteClass() {
      if (this.ejbObjectClass == null) {
         this.ejbObjectClass = this.loadForSure(this.ejbObjectClassName);
      }

      return this.ejbObjectClass;
   }

   public Class getLocalClass() {
      if (this.localClass == null) {
         this.localClass = this.loadForSure(this.ejbLocalObjectClassName);
      }

      return this.localClass;
   }

   public Class getWebserviceObjectClass() {
      if (this.webserviceObjectClass == null) {
         this.webserviceObjectClass = this.loadForSure(this.webserviceObjectClassName);
      }

      return this.webserviceObjectClass;
   }

   public String getRemoteInterfaceName() {
      return this.remoteInterfaceName;
   }

   public String getLocalInterfaceName() {
      return this.localInterfaceName;
   }

   public String getServiceEndpointName() {
      return this.serviceEndpointName;
   }

   public Class getRemoteInterfaceClass() {
      return this.remoteInterfaceClass;
   }

   public Class getLocalInterfaceClass() {
      return this.localInterfaceClass;
   }

   public Class getServiceEndpointClass() {
      return this.serviceEndpointClass;
   }

   public boolean hasRemoteClientView() {
      return this.remoteInterfaceName != null;
   }

   public boolean hasLocalClientView() {
      return this.localInterfaceName != null;
   }

   public boolean hasWebserviceClientView() {
      return this.serviceEndpointName != null;
   }

   public BaseEJBRemoteHomeIntf getRemoteHome() {
      return this.remoteHome;
   }

   public BaseEJBLocalHomeIntf getLocalHome() {
      return this.localHome;
   }

   public ClusteringDescriptor getClusteringDescriptor() {
      return this.clusteringDescriptor;
   }

   public String[] getImplementedInterfaceNames() {
      if (this.hasDeclaredRemoteHome() && this.hasDeclaredLocalHome()) {
         return new String[]{this.homeInterfaceName, this.localHomeInterfaceName};
      } else if (this.hasDeclaredRemoteHome()) {
         return new String[]{this.homeInterfaceName};
      } else {
         return this.hasDeclaredLocalHome() ? new String[]{this.localHomeInterfaceName} : new String[0];
      }
   }

   public boolean hasClientViewFor(String name) {
      return name != null && (name.equals(this.homeInterfaceName) || name.equals(this.localHomeInterfaceName));
   }

   protected void initializeAllMethodInfos() throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("initializeAllMethodInfos() for bean:" + this.getEJBName() + ".");
      }

      try {
         if (this.hasDeclaredRemoteHome()) {
            this.createMethodInfos("Remote", this.remoteMethods, this.remoteInterfaceClass.getMethods());
            this.createMethodInfos("Home", this.homeMethods, this.homeInterfaceClass.getMethods());
         }

         if (this.hasDeclaredLocalHome()) {
            this.createMethodInfos("Local", this.localMethods, this.localInterfaceClass.getMethods());
            this.createMethodInfos("Local", this.localMethods, BaseEJBLocalObjectIntf.class.getMethods());
            this.createMethodInfos("LocalHome", this.localHomeMethods, this.localHomeInterfaceClass.getMethods());
         }

         if (this.hasWebserviceClientView()) {
            Method[] wsmeths;
            if (this.isEJB30()) {
               wsmeths = this.wseeAnnotationProcessor == null ? new Method[0] : (Method[])this.wseeAnnotationProcessor.getWebServiceMethods(this.getBeanClass(), this.serviceEndpointClass).toArray(new Method[0]);
            } else {
               wsmeths = this.serviceEndpointClass.getMethods();
            }

            this.createMethodInfos("ServiceEndpoint", this.getWebserviceMethods(), wsmeths);
         }

      } catch (Throwable var3) {
         Loggable l = EJBLogger.logunableToInitializeInterfaceMethodInfoLoggable(this.getEJBName(), StackTraceUtilsClient.throwable2StackTrace(var3));
         throw new WLDeploymentException(l.getMessageText(), var3);
      }
   }

   protected void createMethodInfos(String intf, Map result, Method... methods) {
      boolean debugEnabled = debugLogger.isDebugEnabled();
      Method[] var5 = methods;
      int var6 = methods.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Method m = var5[var7];
         MethodInfo mi = MethodInfoImpl.createMethodInfoImpl(m, intf);
         if (debugEnabled) {
            debug(intf + "..result.put(" + mi.getSignature() + ")");
         }

         result.put(mi.getSignature(), mi);
      }

   }

   public MethodInfo getRemoteMethodInfo(String methodSig) {
      return (MethodInfo)this.remoteMethods.get(methodSig);
   }

   public MethodInfo getRemoteMethodInfo(String methodName, String[] params) {
      return (MethodInfo)this.remoteMethods.get(getMethodSignature(methodName, params));
   }

   public MethodInfo getRemoteMethodInfo(Method m) {
      return (MethodInfo)this.remoteMethods.get(getMethodSignature(m));
   }

   public Collection getAllRemoteMethodInfos() {
      return this.remoteMethods.values();
   }

   public MethodInfo getHomeMethodInfo(String methodSig) {
      return (MethodInfo)this.homeMethods.get(methodSig);
   }

   public MethodInfo getHomeMethodInfo(String methodName, String[] methodParams) {
      return (MethodInfo)this.homeMethods.get(getMethodSignature(methodName, methodParams));
   }

   public MethodInfo getHomeMethodInfo(Method m) {
      return (MethodInfo)this.homeMethods.get(getMethodSignature(m));
   }

   public Collection getAllHomeMethodInfos() {
      return this.homeMethods.values();
   }

   public MethodInfo getLocalMethodInfo(String methodSig) {
      return (MethodInfo)this.localMethods.get(methodSig);
   }

   public MethodInfo getLocalMethodInfo(String methodName, String[] methodParams) {
      return (MethodInfo)this.localMethods.get(getMethodSignature(methodName, methodParams));
   }

   public MethodInfo getLocalMethodInfo(Method m) {
      return (MethodInfo)this.localMethods.get(getMethodSignature(m));
   }

   public Collection getAllLocalMethodInfos() {
      return this.localMethods.values();
   }

   public MethodInfo getLocalHomeMethodInfo(String methodSig) {
      return (MethodInfo)this.localHomeMethods.get(methodSig);
   }

   public MethodInfo getLocalHomeMethodInfo(String methodName, String[] methodParams) {
      return (MethodInfo)this.localHomeMethods.get(getMethodSignature(methodName, methodParams));
   }

   public MethodInfo getLocalHomeMethodInfo(Method m) {
      return (MethodInfo)this.localHomeMethods.get(getMethodSignature(m));
   }

   public Collection getAllLocalHomeMethodInfos() {
      return this.localHomeMethods.values();
   }

   public MethodInfo getCallbackMethodInfo(String methodSig) {
      return (MethodInfo)this.callbackMethods.get(methodSig);
   }

   public MethodInfo getCallbackMethodInfo(Method m) {
      return (MethodInfo)this.callbackMethods.get(getMethodSignature(m));
   }

   public Collection getAllCallbackMethodInfos() {
      return this.callbackMethods.values();
   }

   private Map getWebserviceMethods() {
      if (this.webserviceMethods == null) {
         this.webserviceMethods = new HashMap();
      }

      return this.webserviceMethods;
   }

   public MethodInfo getWebserviceMethodInfo(String methodSig) {
      return (MethodInfo)this.getWebserviceMethods().get(methodSig);
   }

   public MethodInfo getWebserviceMethodInfo(String methodName, String[] methodParams) {
      return (MethodInfo)this.getWebserviceMethods().get(getMethodSignature(methodName, methodParams));
   }

   public MethodInfo getWebserviceMethodInfo(Method m) {
      return (MethodInfo)this.getWebserviceMethods().get(getMethodSignature(m));
   }

   public Collection getAllWebserviceMethodInfos() {
      return this.getWebserviceMethods().values();
   }

   public Iterator getAllMethodInfosIterator() {
      List l = new ArrayList();
      if (this.hasRemoteClientView()) {
         l.add(this.homeMethods.values().iterator());
         l.add(this.remoteMethods.values().iterator());
      }

      if (this.hasLocalClientView()) {
         l.add(this.localHomeMethods.values().iterator());
         l.add(this.localMethods.values().iterator());
      }

      if (this.hasWebserviceClientView()) {
         l.add(this.getWebserviceMethods().values().iterator());
         l.add(this.localHomeMethods.values().iterator());
      }

      if (this.isTimerDriven()) {
         l.add(this.getAllTimerMethodInfos().iterator());
      }

      l.add(this.getAllCallbackMethodInfos().iterator());
      return new CombinedIterator(l);
   }

   protected void setMethodDescriptors(BaseEJBHomeIntf h, ClientViewDescriptor cvDesc) throws WLDeploymentException {
      this.setMethodDescriptors(h, cvDesc.getViewClass(), cvDesc.getViewClass().getMethods(), cvDesc);
   }

   protected void setMethodDescriptors(BaseEJBHomeIntf h, Class c, Method[] methods, ClientViewDescriptor cvDesc) throws WLDeploymentException {
      String eqSig = EntityBeanInfoImpl.getCreateQuerySignature();
      Method[] var6 = methods;
      int var7 = methods.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Method m = var6[var8];
         if (!Helper.isBeaSyntheticMethod(m)) {
            MethodInfo methodInfo = null;
            String intfType = cvDesc.getIntfType();
            if (intfType.equals("Remote")) {
               methodInfo = this.getRemoteMethodInfo(m);
            } else if (intfType.equals("Home")) {
               methodInfo = this.getHomeMethodInfo(m);
            } else if (intfType.equals("Local")) {
               methodInfo = this.getLocalMethodInfo(m);
            } else if (intfType.equals("LocalHome")) {
               methodInfo = this.getLocalHomeMethodInfo(m);
            } else if (intfType.equals("ServiceEndpoint")) {
               methodInfo = this.getWebserviceMethodInfo(m);
            }

            if (methodInfo != null) {
               if (this instanceof EntityBeanInfoImpl && eqSig.equals(methodInfo.getSignature())) {
                  if (((EntityBeanInfo)this).isDynamicQueriesEnabled()) {
                     this.setMethodDescriptor(h, m, c, methodInfo, "createQuery", cvDesc);
                  }
               } else {
                  MethodText mt = new MethodText();
                  mt.setMethod(m);
                  mt.setOptions(128);
                  String mdName = mt.toString();
                  if (!intfType.equals("Home") && !intfType.equals("LocalHome")) {
                     if (!intfType.equals("Remote") && !intfType.equals("Local") && !intfType.equals("ServiceEndpoint")) {
                        throw new IllegalArgumentException("Unknown method interface type: " + intfType);
                     }

                     mdName = "eo_" + mdName;
                  } else if ((!mdName.equals("getEJBMetaData") || !intfType.equals("Home")) && (!mdName.equals("getHomeHandle") || !intfType.equals("Home")) && (!mdName.equals("getLocalHomeHandle") || !intfType.equals("LocalHome"))) {
                     mdName = this.homeToBeanName(mdName);
                  }

                  this.setMethodDescriptor(h, m, c, methodInfo, mdName, cvDesc);
               }
            }
         }
      }

   }

   protected MethodDescriptor setMethodDescriptor(BaseEJBHomeIntf h, Method m, Class c, MethodInfo methodInfo, String mdName, ClientViewDescriptor cvDesc) throws WLDeploymentException {
      try {
         Method am = this.perhapsGetBeanMethod(m, cvDesc.getIntfType());
         return super.setMethodDescriptor(h, am, c, methodInfo, mdName, cvDesc);
      } catch (Exception var8) {
         throw new WLDeploymentException(var8.toString());
      }
   }

   private String homeToBeanName(String m) {
      if (!m.startsWith("create") && !m.startsWith("find") && !m.startsWith("remove")) {
         return "ejbHome" + m;
      } else {
         StringBuffer sb = new StringBuffer("ejb" + m);
         sb.setCharAt(3, Character.toUpperCase(sb.charAt(3)));
         return sb.toString();
      }
   }

   protected Method perhapsGetBeanMethod(Method m, String interfaceType) {
      return m;
   }

   private void dumpMethodDescriptors() {
      if (this.hasDeclaredRemoteHome()) {
         debug("** Dumping Remote MethodDescriptor for: " + this.getDisplayName());
         this.dumpMethodDescriptorFields(this.getHomeClass().getFields(), this.remoteHome);
         this.dumpMethodDescriptorFields(this.getRemoteClass().getFields(), (Object)null);
      }

      if (this.hasDeclaredLocalHome()) {
         debug("** Dumping Local MethodDescriptor for: " + this.getDisplayName());
         this.dumpMethodDescriptorFields(this.getLocalHomeClass().getFields(), this.localHome);
         this.dumpMethodDescriptorFields(this.getLocalClass().getFields(), (Object)null);
      }

      if (this.hasWebserviceClientView()) {
         debug("** Dumping Webservice MethodDescriptor for: " + this.getDisplayName());
         this.dumpMethodDescriptorFields(this.getWebserviceObjectClass().getFields(), (Object)null);
      }

   }

   private void dumpMethodInfos() {
      Iterator var1;
      MethodInfo mi;
      if (this.hasRemoteClientView()) {
         debug("Dumping Remote MethodInfos for: " + this.getDisplayName());
         debug("Remote Methods:");
         var1 = this.getAllRemoteMethodInfos().iterator();

         while(var1.hasNext()) {
            mi = (MethodInfo)var1.next();
            debug(mi.toString());
         }

         if (this.hasDeclaredRemoteHome()) {
            debug("Home Methods:");
            var1 = this.getAllHomeMethodInfos().iterator();

            while(var1.hasNext()) {
               mi = (MethodInfo)var1.next();
               debug(mi.toString());
            }
         }
      }

      if (this.hasLocalClientView()) {
         debug("Dumping Local MethodInfos for: " + this.getDisplayName());
         debug("Local Methods:");
         var1 = this.getAllLocalMethodInfos().iterator();

         while(var1.hasNext()) {
            mi = (MethodInfo)var1.next();
            debug(mi.toString());
         }

         if (this.hasDeclaredLocalHome()) {
            debug("Local Home Methods:");
            var1 = this.getAllLocalHomeMethodInfos().iterator();

            while(var1.hasNext()) {
               mi = (MethodInfo)var1.next();
               debug(mi.toString());
            }
         }
      }

      if (this.hasWebserviceClientView()) {
         debug("Dumping Webservice MethodInfos for: " + this.getDisplayName());
         debug("Webservice Methods:");
         var1 = this.getAllWebserviceMethodInfos().iterator();

         while(var1.hasNext()) {
            mi = (MethodInfo)var1.next();
            debug(mi.toString());
         }
      }

   }

   public void prepare(Environment env) throws WLDeploymentException {
      super.prepare(env);
      BeanManager bm = this.getBeanManager();
      this.registerRoleRefs();
      Context ctx = null;

      try {
         try {
            ctx = new InitialContext();
         } catch (NamingException var18) {
            AssertionError ae = new AssertionError("Error creating InitialContext!");
            ae.initCause(var18);
            throw ae;
         }

         if (this.hasRemoteClientView()) {
            this.remoteHome = (BaseEJBRemoteHomeIntf)this.getHomeClass().newInstance();
            this.remoteHome.setup(this, bm);
            if (this.hasDeclaredRemoteHome()) {
               this.setMethodDescriptors(this.remoteHome, new ClientViewDescriptor(this.getHomeClass(), "Home", false, false, this));
               this.setMethodDescriptors((BaseEJBHomeIntf)null, new ClientViewDescriptor(this.getRemoteClass(), "Remote", false, false, this));
            }

            if (this.getJNDINameAsString() != null && this.hasDeclaredRemoteHome()) {
               try {
                  Remote r = (Remote)ctx.lookup(this.getJNDINameAsString());
                  if (!ServerHelper.isClusterable(r) || ServerHelper.isLocal(r)) {
                     Loggable l = EJBLogger.logJNDINameAlreadyInUseLoggable(this.getDisplayName(), this.getJNDINameAsString());
                     throw new WLDeploymentException(l.getMessageText());
                  }
               } catch (NamingException var20) {
               }
            }
         }

         if (this.hasLocalClientView()) {
            this.localHome = (BaseEJBLocalHomeIntf)this.getLocalHomeClass().newInstance();
            this.localHome.setup(this, bm);
            if (this.hasDeclaredLocalHome()) {
               this.setMethodDescriptors(this.localHome, new ClientViewDescriptor(this.getLocalHomeClass(), "LocalHome", true, false, this));
               this.setMethodDescriptors((BaseEJBHomeIntf)null, new ClientViewDescriptor(this.getLocalClass(), "Local", true, false, this));
            }

            if (this.getLocalJNDINameAsString() != null && this.hasDeclaredLocalHome()) {
               try {
                  ctx.lookup(this.getLocalJNDINameAsString());
                  Loggable l = EJBLogger.logJNDINameAlreadyInUseLoggable(this.getDisplayName(), this.getLocalJNDINameAsString());
                  throw new WLDeploymentException(l.getMessageText());
               } catch (NamingException var19) {
               }
            }
         }

         if (this.hasWebserviceClientView()) {
            this.webserviceObjectFactory = new WSObjectFactoryImpl(bm, this);

            try {
               this.setMethodDescriptors((BaseEJBHomeIntf)null, new ClientViewDescriptor(this.getWebserviceObjectClass(), "ServiceEndpoint", true, false, this));
            } catch (Throwable var17) {
               Debug.say(" development time message:  no webservice available for EJB '" + this.getEJBName() + "' " + var17.getMessage());
            }
         }

         if (debugLogger.isDebugEnabled()) {
            this.dumpMethodInfos();
            this.dumpMethodDescriptors();
         }
      } catch (IllegalAccessException | InstantiationException var21) {
         throw new AssertionError(var21);
      } finally {
         if (ctx != null) {
            try {
               ctx.close();
            } catch (NamingException var16) {
            }
         }

      }

   }

   public EjbJndiBinder getJndiBinder() throws NamingException {
      if (this.jndiBinder == null) {
         this.jndiBinder = new Ejb2JndiBinder(this, this.getEnvBuilder());
      }

      return this.jndiBinder;
   }

   void resetJndiBinder() {
      this.jndiBinder = null;
   }

   public void activate(Map cacheMap, Map queryCacheMap) throws WLDeploymentException {
      BeanManager bm = this.getBeanManager();
      if (bm instanceof CachingManager) {
         EJBCache cache = this.getCache(cacheMap);
         this.initCacheManager(cache, bm, cacheMap, queryCacheMap, this.getEnvBuilder().getRootContext());
      } else {
         bm.setup(this.remoteHome, this.localHome, this, this.getEnvBuilder().getRootContext(), this.getRuntimeHelper().getSecurityHelper());
      }

      EJBRuntimeMBean ejbRuntime = bm.getEJBRuntimeMBean();
      if (ejbRuntime != null) {
         String dp = this.getDispatchPolicy();
         if (dp == null || dp.trim().length() == 0) {
            dp = "default";
         }

         ExecuteQueueRuntimeMBean[] rts = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime().getExecuteQueueRuntimes();
         RuntimeMBean eq = null;
         ExecuteQueueRuntimeMBean[] var8 = rts;
         int var9 = rts.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ExecuteQueueRuntimeMBean rt = var8[var10];
            if (rt.getName().equals(dp)) {
               eq = rt;
               break;
            }
         }

         if (eq != null) {
            ((EJBRuntimeMBeanImpl)ejbRuntime).addResource(eq);
         } else if (debugLogger.isDebugEnabled()) {
            debug("Error looking up ExecuteQueueRuntimeMBean!!!!!!");
         }
      }

      if (this.hasRemoteClientView()) {
         this.remoteHome.activate();
      }

      try {
         this.getJndiBinder().bindToJNDI();
      } catch (NamingException var12) {
         throw new WLDeploymentException("Error in binding JNDI name of EJB " + this.getEJBName(), var12);
      }
   }

   protected void initCacheManager(EJBCache cache, BeanManager bm, Map cacheMap, Map queryCacheMap, Context environmentContext) throws WLDeploymentException {
      ((CachingManager)bm).setup(this.remoteHome, this.localHome, this, environmentContext, cache, this.getRuntimeHelper().getSecurityHelper());
   }

   public Object getBindable(String ifaceName) {
      if (ifaceName.equals(this.getLocalHomeInterfaceName())) {
         return this.getLocalHome();
      } else {
         return ifaceName.equals(this.getHomeInterfaceName()) ? this.getRemoteHome().getReferenceToBind() : null;
      }
   }

   protected abstract String getSyntheticHomeImplClassName();

   protected abstract String getSyntheticLocalHomeImplClassName();

   public void onUndeploy() {
      try {
         this.getJndiBinder().unbindFromJNDI();
      } catch (NamingException var2) {
      }

      super.onUndeploy();
   }

   protected abstract EJBCache getCache(Map var1) throws WLDeploymentException;

   public void updateImplClassLoader() throws WLDeploymentException {
      super.updateImplClassLoader();
   }

   protected void assignDefaultTXAttributesIfNecessary() {
      StringBuffer sb = new StringBuffer();
      short defTxAttr = DDDefaults.getTransactionAttribute(this.isEJB30());
      StringBuffer sb2 = new StringBuffer();
      short defTxAttrForBM = DDDefaults.getBeanMethodTransactionAttribute(this.isEJB30());
      if (this.hasDeclaredRemoteHome()) {
         sb.append(this.assignDefaultTXAttributesIfNecessary("home", this.getAllHomeMethodInfos(), defTxAttr));
      }

      sb.append(this.assignDefaultTXAttributesIfNecessary("remote", this.getAllRemoteMethodInfos(), defTxAttr));
      if (this.hasDeclaredLocalHome()) {
         sb.append(this.assignDefaultTXAttributesIfNecessary("local-home", this.getAllLocalHomeMethodInfos(), defTxAttr));
      }

      sb.append(this.assignDefaultTXAttributesIfNecessary("local", this.getAllLocalMethodInfos(), defTxAttr));
      sb.append(this.assignDefaultTXAttributesIfNecessary("webservice", this.getAllWebserviceMethodInfos(), defTxAttr));
      sb2.append(this.assignDefaultTXAttributesIfNecessary("beanClass", this.getAllTimerMethodInfos(), defTxAttrForBM));
      sb2.append(this.assignDefaultTXAttributesIfNecessary("beanClass", this.getAllCallbackMethodInfos(), defTxAttrForBM));
      if (sb.length() > 0 && !this.isEJB30()) {
         EJBLogger.logEJBUsesDefaultTXAttribute(this.getDisplayName(), (String)DDConstants.TX_ATTRIBUTE_STRINGS.get(defTxAttr), sb.toString());
      }

      if (sb2.length() > 0 && !this.isEJB30()) {
         EJBLogger.logEJBUsesDefaultTXAttribute(this.getDisplayName(), (String)DDConstants.TX_ATTRIBUTE_STRINGS.get(defTxAttrForBM), sb2.toString());
      }

   }

   private void checkClientViews() throws WLDeploymentException {
      if ((this.homeInterfaceName == null || this.remoteInterfaceName != null) && (this.homeInterfaceName != null || this.remoteInterfaceName == null)) {
         if (this.localHomeInterfaceName != null && this.localInterfaceName == null || this.localHomeInterfaceName == null && this.localInterfaceName != null) {
            throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().INCONSISTENT_LOCAL_VIEW(this.getEJBName()));
         }
      } else {
         throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().INCONSISTENT_REMOTE_VIEW(this.getEJBName()));
      }
   }

   protected boolean checkIfItsSafeToUseCallByReference() {
      Debug.assertion(!this.callByReference);
      if (!this.hasRemoteClientView()) {
         return false;
      } else {
         if (this.hasDeclaredRemoteHome()) {
            Method[] var1 = this.remoteInterfaceClass.getMethods();
            int var2 = var1.length;

            int var3;
            Method m;
            for(var3 = 0; var3 < var2; ++var3) {
               m = var1[var3];
               if (this.businessMethod(m) && !this.checkIfMethodCanUseCallByReference(m)) {
                  return false;
               }
            }

            var1 = this.homeInterfaceClass.getMethods();
            var2 = var1.length;

            for(var3 = 0; var3 < var2; ++var3) {
               m = var1[var3];
               if (this.homeMethod(m) && !this.checkIfMethodCanUseCallByReference(m)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private boolean businessMethod(Method m) {
      try {
         EJBObject.class.getMethod(m.getName(), m.getParameterTypes());
         return false;
      } catch (NoSuchMethodException var3) {
         return true;
      }
   }

   private boolean homeMethod(Method m) {
      try {
         EJBHome.class.getMethod(m.getName(), m.getParameterTypes());
         return false;
      } catch (NoSuchMethodException var4) {
         String n = m.getName();
         return !n.startsWith("find") && !n.startsWith("create") && !n.startsWith("remove");
      }
   }

   private boolean isMutable(Class c) {
      if (c.isPrimitive()) {
         return false;
      } else {
         return c != Boolean.class && c != Byte.class && c != Character.class && c != Double.class && c != Float.class && c != Integer.class && c != Long.class && c != Short.class && c != String.class && c != BigDecimal.class;
      }
   }

   protected boolean checkIfMethodCanUseCallByReference(Method m) {
      if (this.isMutable(m.getReturnType())) {
         return false;
      } else {
         Class[] var2 = m.getParameterTypes();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class c = var2[var4];
            if (this.isMutable(c)) {
               return false;
            }
         }

         return true;
      }
   }

   protected void warnIfParameterNotSerializable() {
      if (this.hasRemoteClientView()) {
         if (this.remoteInterfaceClass != null) {
            Method[] var1 = this.remoteInterfaceClass.getMethods();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               Method m = var1[var3];
               if (this.businessMethod(m)) {
                  this.warnIfParamNotSerializableForMethod(m);
               }
            }

         }
      }
   }

   protected void warnIfParamNotSerializableForMethod(Method method) {
      Type[] var2 = method.getGenericParameterTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type arg = var2[var4];
         if (arg instanceof Class) {
            Class clzz = (Class)arg;
            if (!clzz.isPrimitive() && !Serializable.class.isAssignableFrom(clzz) && !this.getDeploymentInfo().isWarningDisabled("BEA-012034")) {
               EJBLogger.logWarningParameterIsNotSerializable(method.toString(), this.compDesc.getEJBName(), clzz.getName());
            }
         }
      }

   }

   public boolean useCallByReference() {
      return this.callByReference;
   }

   public boolean isClientDriven() {
      return true;
   }

   public void unprepare() {
      BaseEJBRemoteHomeIntf homeObject = this.getRemoteHome();
      if (homeObject != null) {
         homeObject.unprepare();
      }

   }

   private static void debug(String s) {
      debugLogger.debug("[ClientDrivenBeanInfoImpl] " + s);
   }
}
