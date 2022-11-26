package weblogic.ejb.container.deployer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.naming.CompositeName;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.InterceptorHelper;
import weblogic.ejb.container.compliance.TimeoutCheckHelper;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.deployer.mbimpl.MethodInfoImpl;
import weblogic.ejb.container.interfaces.BaseEJBHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CachingDescriptor;
import weblogic.ejb.container.interfaces.ConcurrencyInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EjbComponentCreator;
import weblogic.ejb.container.interfaces.IIOPSecurityDescriptor;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SecurityRoleReference;
import weblogic.ejb.container.internal.ClientViewDescriptor;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.utils.EJBCICHelper;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.Injector;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.kernel.KernelStatus;
import weblogic.logging.Loggable;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.annotation.BeaSynthetic.Helper;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;

abstract class BeanInfoImpl implements BeanInfo {
   protected static final DebugLogger debugLogger;
   private static final AuthenticatedSubject KERNEL_ID;
   private final CachingDescriptor cachingDescriptor;
   private final IIOPSecurityDescriptor iiopSecurityDescriptor;
   private final DeploymentInfo deploymentInfo;
   private final ComponentInvocationContextManager cicMgr;
   private final ComponentInvocationContext cic;
   private int txTimeoutSeconds = 0;
   private GenericClassLoader cl;
   private final GenericClassLoader moduleCL;
   private final String ejbName;
   private final String dispatchPolicy;
   private final boolean stickToFirstServer;
   private final int remoteClientTimeout;
   private final String displayName;
   private final Collection envEntries;
   private final Collection ejbRefs;
   private final Collection ejbLocalRefs;
   private final Collection resRefs;
   private final Collection resEnvRefs;
   private final Collection wlResRefs;
   private final Collection wlResEnvRefs;
   private final Collection messageDestRefs;
   private final Map secRoleRefs;
   private final String beanClassName;
   private final String isIdenticalKey;
   private final String fullyQualifiedName;
   private final boolean isEJB30;
   private EjbComponentCreator ejbComponentCreator;
   protected final CompositeDescriptor compDesc;
   private final Map ejbRefJNDINames;
   private final Map ejbLocalRefJNDINames;
   private Class beanClass;
   private boolean runAsPrincipalCalculated = false;
   private String runAsPrincipalName;
   private final String createAsPrincipalName;
   private final String removeAsPrincipalName;
   private final String passivateAsPrincipalName;
   protected final List methodDescriptors = new ArrayList();
   private final Map timerMethodInfos = new HashMap();
   private MethodDescriptor ejbTimeoutMethodDescriptor;
   private Method ejbTimeoutMethod;
   private final Map autoTimersMap = new HashMap();
   private final Map autoTimersMDMap = new HashMap();
   private final Map autoTimersMDBySignatureMap = new HashMap();
   private final boolean isClusteredTimers;
   private boolean usingJTAConfigTimeout;
   private RuntimeHelper runtimeHelper;
   private BeanManager beanManager;
   private Environment envBuilder;
   private boolean isVersionGreaterThan30;

   BeanInfoImpl(DeploymentInfo di, CompositeDescriptor desc, GenericClassLoader moduleCL) throws ClassNotFoundException, WLDeploymentException {
      this.deploymentInfo = di;
      this.compDesc = desc;
      this.moduleCL = moduleCL;
      this.cl = new GenericClassLoader(moduleCL.getClassFinder(), moduleCL);
      if (moduleCL.getAnnotation() != null) {
         this.cl.setAnnotation(new Annotation(moduleCL.getAnnotation().getApplicationName(), moduleCL.getAnnotation().getModuleName()));
      }

      this.beanClassName = this.compDesc.getEJBClassName();
      this.dispatchPolicy = this.compDesc.getDispatchPolicy();
      this.stickToFirstServer = this.compDesc.getStickToFirstServer();
      this.remoteClientTimeout = this.compDesc.getRemoteClientTimeout();
      this.ejbName = this.compDesc.getEJBName();

      assert this.ejbName != null;

      if (isServer()) {
         this.cicMgr = ComponentInvocationContextManager.getInstance(KERNEL_ID);
         this.cic = this.cicMgr.createComponentInvocationContext(di.getApplicationId(), di.getModuleName(), this.ejbName);
         this.displayName = this.ejbName + "(Application: " + di.getApplicationName() + ", EJBComponent: " + di.getModuleId() + ")";
      } else {
         this.displayName = this.ejbName + "(Archive: " + di.getJarFileName() + ")";
         this.cicMgr = null;
         this.cic = null;
      }

      this.envEntries = this.compDesc.getAllEnvironmentEntries();
      this.ejbRefs = this.compDesc.getAllEJBReferences();
      this.ejbRefJNDINames = this.compDesc.getAllEJBReferenceJNDINames();
      this.ejbLocalRefs = this.compDesc.getAllEJBLocalReferences();
      this.ejbLocalRefJNDINames = this.compDesc.getAllEJBLocalReferenceJNDINames();
      this.resRefs = this.compDesc.getAllResourceReferences();
      this.resEnvRefs = this.compDesc.getAllResourceEnvReferences();
      this.wlResRefs = this.compDesc.getAllWlResourceReferences();
      this.wlResEnvRefs = this.compDesc.getAllWlResourceEnvReferences();
      this.messageDestRefs = this.compDesc.getAllMessageDestinationReferences();
      this.secRoleRefs = this.compDesc.getSecurityRoleReferencesMap();
      this.cachingDescriptor = this.compDesc.getCachingDescriptor();
      this.iiopSecurityDescriptor = this.compDesc.getIIOPSecurityDescriptor();
      this.beanClass = this.loadClass(this.beanClassName);
      this.checkClassLoaders(this.compDesc, this.beanClass);
      this.isIdenticalKey = di.getApplicationName() + di.getModuleURI() + this.ejbName;
      this.fullyQualifiedName = di.getApplicationId() + "_" + di.getModuleId() + "_" + this.ejbName;
      this.createAsPrincipalName = this.compDesc.getCreateAsPrincipalName();
      this.removeAsPrincipalName = this.compDesc.getRemoveAsPrincipalName();
      this.passivateAsPrincipalName = this.compDesc.getPassivateAsPrincipalName();
      this.isClusteredTimers = this.compDesc.isClusteredTimers();
      this.initializeTimeoutMethods();
      this.initializeTimerMethodInfos();
      this.isEJB30 = this.compDesc.isEJB30();
      this.isVersionGreaterThan30 = this.compDesc.isVersionGreaterThan30();
   }

   protected final String transformJndiName(String jndiName) {
      return EnvUtils.transformJNDIName(jndiName, this.getDeploymentInfo().getApplicationName());
   }

   public String getRunAsPrincipalName() {
      if (!this.runAsPrincipalCalculated) {
         throw new AssertionError("unexpected codepath");
      } else {
         return this.runAsPrincipalName;
      }
   }

   public AuthenticatedSubject getRunAsSubject() throws PrincipalNotFoundException {
      String runAsPrincipal = this.getRunAsPrincipalName();
      AuthenticatedSubject runAsSubject = null;
      if (runAsPrincipal == null) {
         runAsSubject = SubjectUtils.getAnonymousSubject();
         runAsSubject.setQOS((byte)101);
      } else {
         runAsSubject = this.runtimeHelper.getRunAsSubject(runAsPrincipal);
      }

      return runAsSubject;
   }

   public String getCreateAsPrincipalName() {
      return this.createAsPrincipalName;
   }

   public String getRemoveAsPrincipalName() {
      return this.removeAsPrincipalName;
   }

   public String getPassivateAsPrincipalName() {
      return this.passivateAsPrincipalName;
   }

   public DeploymentInfo getDeploymentInfo() {
      return this.deploymentInfo;
   }

   public final ComponentInvocationContext getCIC() {
      if (this.cic == null) {
         throw new UnsupportedOperationException("CIC is not supported outside of the server");
      } else {
         return this.cic;
      }
   }

   public final ManagedInvocationContext setCIC() {
      return EJBCICHelper.pushEJBCIC(this.cic);
   }

   public boolean getClientsOnSameServer() {
      return this.compDesc.getClientsOnSameServer();
   }

   public Map getAllEJBReferenceJNDINames() {
      return this.ejbRefJNDINames;
   }

   public Map getAllEJBLocalReferenceJNDINames() {
      return this.ejbLocalRefJNDINames;
   }

   protected static boolean isServer() {
      return KernelStatus.isServer();
   }

   protected void checkClassLoaders(CompositeDescriptor desc, Class c) {
      ClassLoader myLoader = this.getClass().getClassLoader();
      if (c.getClassLoader() == myLoader) {
         if (isServer()) {
            if (!this.isWarningDisabled("BEA-010001") && !c.getName().startsWith("weblogic")) {
               EJBLogger.logRedeployClasspathFailure(desc.getEJBName(), c.getName());
            }
         } else if (!this.isWarningDisabled("BEA-010054")) {
            EJBLogger.logEJBClassFoundInClasspath(desc.getEJBName(), c.getName());
         }
      }

   }

   public int getTransactionTimeoutSeconds() {
      return this.txTimeoutSeconds;
   }

   public boolean isUsingJTAConfigTimeout() {
      return this.usingJTAConfigTimeout;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public final String getIsIdenticalKey() {
      return this.isIdenticalKey;
   }

   public final String getFullyQualifiedName() {
      return this.fullyQualifiedName;
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getBeanClassName() {
      return this.beanClassName;
   }

   public Collection getAllEnvironmentEntries() {
      return this.envEntries;
   }

   public Collection getAllEJBReferences() {
      return this.ejbRefs;
   }

   public Collection getAllEJBLocalReferences() {
      return this.ejbLocalRefs;
   }

   public Collection getAllResourceReferences() {
      return this.resRefs;
   }

   public boolean hasResourceRefs() {
      return !this.resRefs.isEmpty();
   }

   public Collection getAllResourceEnvReferences() {
      return this.resEnvRefs;
   }

   public Collection getAllWlResourceReferences() {
      return this.wlResRefs;
   }

   public Collection getAllWlResourceEnvReferences() {
      return this.wlResEnvRefs;
   }

   public Collection getAllMessageDestinationReferences() {
      return this.messageDestRefs;
   }

   public Collection getAllSecurityRoleReferences() {
      return this.secRoleRefs.values();
   }

   public SecurityRoleReference getSecurityRoleReference(String roleName) {
      return (SecurityRoleReference)this.secRoleRefs.get(roleName);
   }

   public PersistenceContextRefBean[] getPersistenceContextRefs() {
      return this.compDesc.getPersistenceContextRefs();
   }

   public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
      return this.compDesc.getPersistenceUnitRefs();
   }

   public ClassLoader getClassLoader() {
      return this.cl;
   }

   public ClassLoader getModuleClassLoader() {
      return this.moduleCL;
   }

   protected Name getName(String name) {
      if (name == null) {
         return null;
      } else {
         try {
            return new CompositeName(name);
         } catch (InvalidNameException var3) {
            throw new AssertionError("Invalid JNDI Name" + name, var3);
         }
      }
   }

   protected Class loadClass(String className) throws ClassNotFoundException {
      return this.cl.loadClass(className);
   }

   protected Class loadForSure(String className) {
      try {
         return this.loadClass(className);
      } catch (ClassNotFoundException var3) {
         throw new AssertionError("Failed to load required application class" + className, var3);
      }
   }

   public abstract Iterator getAllMethodInfosIterator();

   public Collection getAllTimerMethodInfos() {
      return this.timerMethodInfos.values();
   }

   public MethodInfo getTimerMethodInfo(String methodSignature) {
      return (MethodInfo)this.timerMethodInfos.get(methodSignature);
   }

   public boolean isEJB30() {
      return this.isEJB30;
   }

   public void setEjbComponentCreator(EjbComponentCreator ejbComponentCreator) {
      this.ejbComponentCreator = ejbComponentCreator;
   }

   public EjbComponentCreator getEjbComponentCreator() {
      return this.ejbComponentCreator;
   }

   public boolean isTimerDriven() {
      return this.ejbTimeoutMethod != null || !this.autoTimersMap.isEmpty();
   }

   public boolean isClusteredTimers() {
      return this.isClusteredTimers;
   }

   public String getTimerStoreName() {
      return this.compDesc.getTimerStoreName();
   }

   private void initializeTimeoutMethods() throws WLDeploymentException {
      NamedMethodBean nmb = this.compDesc.getTimeoutMethod();
      if (nmb != null) {
         this.ejbTimeoutMethod = InterceptorHelper.getTimeoutMethodFromDD(nmb, this.beanClass);
         if (this.ejbTimeoutMethod == null) {
            throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().EJB_TIMEOUT_METHOD_NOT_FOUND(this.getDisplayName(), getMethodSignature(nmb.getMethodName(), new String[]{"javax.ejb.Timer"})));
         }
      }

      try {
         TimeoutCheckHelper.validateTimeoutMethodIsejbTimeout(this.beanClass, this.ejbTimeoutMethod);
         if (TimedObject.class.isAssignableFrom(this.beanClass)) {
            try {
               this.ejbTimeoutMethod = this.beanClass.getMethod("ejbTimeout", Timer.class);
               this.ejbTimeoutMethod.setAccessible(true);
            } catch (NoSuchMethodException var5) {
            }
         }

         Iterator var2 = this.compDesc.getAutoTimers().entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry me = (Map.Entry)var2.next();
            Method m = InterceptorHelper.getTimeoutMethodFromDD((NamedMethodBean)me.getValue(), this.beanClass);
            if (m == null) {
               throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().EJB_TIMEOUT_METHOD_NOT_FOUND(this.getDisplayName(), ((NamedMethodBean)me.getValue()).toString()));
            }

            this.autoTimersMap.put(me.getKey(), m);
         }

      } catch (ComplianceException var6) {
         throw new WLDeploymentException(var6.getMessage(), var6);
      }
   }

   public Method getTimeoutMethod() {
      return this.ejbTimeoutMethod;
   }

   public Collection getAutomaticTimerMethods() {
      return this.autoTimersMap.values();
   }

   private void initializeTimerMethodInfos() {
      if (this.ejbTimeoutMethod != null) {
         MethodInfo mi = MethodInfoImpl.createMethodInfoImpl(this.ejbTimeoutMethod, "Timer");
         this.timerMethodInfos.put(mi.getSignature(), mi);
      }

      Iterator var4 = this.autoTimersMap.values().iterator();

      while(var4.hasNext()) {
         Method m = (Method)var4.next();
         MethodInfo mi = MethodInfoImpl.createMethodInfoImpl(m, "Timer");
         this.timerMethodInfos.put(mi.getSignature(), mi);
      }

   }

   public CachingDescriptor getCachingDescriptor() {
      return this.cachingDescriptor;
   }

   public IIOPSecurityDescriptor getIIOPSecurityDescriptor() {
      return this.iiopSecurityDescriptor;
   }

   public boolean isWarningDisabled(String warning) {
      return this.deploymentInfo.isWarningDisabled(warning);
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public abstract BeanManager getBeanManagerInstance(EJBRuntimeHolder var1);

   public void setupBeanManager(EJBRuntimeHolder runtime) {
      this.beanManager = this.getBeanManagerInstance(runtime);
   }

   public void onUndeploy() {
   }

   public String getDispatchPolicy() {
      return this.dispatchPolicy;
   }

   public boolean getStickToFirstServer() {
      return this.stickToFirstServer;
   }

   public int getRemoteClientTimeout() {
      return this.remoteClientTimeout;
   }

   public void updateImplClassLoader() throws WLDeploymentException {
      this.cl = new GenericClassLoader(this.cl.getClassFinder(), this.cl.getParent());

      try {
         this.beanClass = this.loadClass(this.beanClassName);
      } catch (ClassNotFoundException var2) {
         throw new WLDeploymentException("Couldn't load updated impl class: " + var2);
      }
   }

   public void setTransactionTimeoutSeconds(int seconds, boolean fromConfig) {
      this.txTimeoutSeconds = seconds;
      this.usingJTAConfigTimeout = fromConfig;
   }

   protected StringBuffer assignDefaultTXAttributesIfNecessary(String interfaceName, Collection methodInfos, short defaultTxAttr) {
      StringBuffer sb = new StringBuffer();
      Iterator var5 = methodInfos.iterator();

      while(var5.hasNext()) {
         MethodInfo mi = (MethodInfo)var5.next();
         if (!Helper.isBeaSyntheticMethod(mi.getMethod()) && mi.getTransactionAttribute() == -1) {
            if (sb.length() == 0) {
               sb.append(interfaceName);
               sb.append('[');
            } else {
               sb.append(", ");
            }

            sb.append(mi.getSignature());
            mi.setTransactionAttribute(defaultTxAttr);
         }
      }

      if (sb.length() > 0) {
         sb.append("]  ");
      }

      return sb;
   }

   protected abstract short getTxAttribute(MethodInfo var1, Class var2);

   protected MethodDescriptor setMethodDescriptor(BaseEJBHomeIntf h, Method m, Class c, MethodInfo methodInfo, String mdName, ClientViewDescriptor cvDesc) throws WLDeploymentException {
      MethodDescriptor md = this.createMethodDescriptor(m, c, methodInfo, cvDesc);
      this.setMDField(h, c, md, mdName, cvDesc.getIntfType());
      return md;
   }

   public MethodDescriptor createMethodDescriptor(Method m, Class c, MethodInfo methodInfo, ClientViewDescriptor cvDesc) throws WLDeploymentException {
      try {
         MethodDescriptor md = new MethodDescriptor(m, methodInfo, this.getTxAttribute(methodInfo, c), this.compDesc.getEntityAlwaysUsesTransaction(), this.runtimeHelper.getRunAsSubject(this.getRunAsPrincipalName()), this.getConcurrencyInfo(methodInfo), this.isRemoveMethod(methodInfo), this.isRetainIfException(methodInfo), this.containsExtendedPersistenceContextRefs(), cvDesc);
         this.methodDescriptors.add(md);
         if (this.runtimeHelper.processUncheckedExcludedMethod(md) && debugLogger.isDebugEnabled()) {
            debug("method: '" + methodInfo.getMethodName() + "' is unchecked or excluded runtime unchecked/excluded list has been updated.");
         }

         return md;
      } catch (PrincipalNotFoundException var6) {
         throw new WLDeploymentException(var6.toString());
      }
   }

   protected boolean containsExtendedPersistenceContextRefs() {
      return false;
   }

   protected boolean isRemoveMethod(MethodInfo mi) {
      return false;
   }

   protected boolean isRetainIfException(MethodInfo mi) {
      return false;
   }

   protected ConcurrencyInfo getConcurrencyInfo(MethodInfo mi) {
      return null;
   }

   private void setMDField(BaseEJBHomeIntf home, Class c, MethodDescriptor md, String mdName, String interfaceType) throws WLDeploymentException {
      Loggable l;
      try {
         if (!interfaceType.equals("Home") && !interfaceType.equals("LocalHome")) {
            if (!interfaceType.equals("Remote") && !interfaceType.equals("Local") && !interfaceType.equals("ServiceEndpoint") && !interfaceType.equals("MessageEndpoint")) {
               throw new IllegalArgumentException("Unknown interface type: '" + interfaceType + "'");
            }

            this.setObjectMDField(c, md, mdName);
         } else {
            this.setHomeMDField(home, md, mdName);
         }

      } catch (IllegalAccessException var8) {
         l = EJBLogger.logMismatchBetweenBeanAndGeneratedCodeLoggable(this.getDisplayName(), StackTraceUtilsClient.throwable2StackTrace(var8));
         throw new WLDeploymentException(l.getMessageText(), var8);
      } catch (NoSuchFieldException var9) {
         l = EJBLogger.logMismatchBetweenBeanAndGeneratedCodeLoggable(this.getDisplayName(), StackTraceUtilsClient.throwable2StackTrace(var9));
         throw new WLDeploymentException(l.getMessageText(), var9);
      }
   }

   private void setHomeMDField(BaseEJBHomeIntf home, MethodDescriptor md, String mdName) throws IllegalAccessException, NoSuchFieldException {
      Field mdField = home.getClass().getField("md_" + mdName);
      mdField.set(home, md);
   }

   private void setObjectMDField(Class c, MethodDescriptor md, String mdName) throws IllegalAccessException, NoSuchFieldException {
      Field mdField = c.getField("md_" + mdName);
      mdField.set(c, md);
   }

   void setRuntimeHelper(RuntimeHelper r) {
      this.runtimeHelper = r;
   }

   RuntimeHelper getRuntimeHelper() {
      return this.runtimeHelper;
   }

   protected final void checkSecurityRoleRefs() throws WLDeploymentException {
      Iterator var1 = this.secRoleRefs.entrySet().iterator();

      Map.Entry sr;
      do {
         if (!var1.hasNext()) {
            return;
         }

         sr = (Map.Entry)var1.next();
      } while(((SecurityRoleReference)sr.getValue()).getReferencedRole() != null);

      throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().NULL_SECURITY_ROLE_REF_LINK(this.getEJBName(), (String)sr.getKey()));
   }

   protected void registerRoleRefs() throws WLDeploymentException {
      this.runtimeHelper.registerRoleRefs(this.getEJBName(), this.secRoleRefs);
   }

   protected List getMethodDescriptors() {
      return this.methodDescriptors;
   }

   protected static String getMethodSignature(Method m) {
      return DDUtils.getMethodSignature(m);
   }

   protected static String getMethodSignature(String name, String[] params) {
      return DDUtils.getMethodSignature(name, params);
   }

   protected void dumpMethodDescriptorFields(Field[] fields, Object obj) {
      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field f = var3[var5];
         if (f.getName().startsWith("md_")) {
            try {
               MethodDescriptor md = (MethodDescriptor)f.get(obj);
               debug("MethodDescriptor: " + md);
            } catch (IllegalAccessException var8) {
               debug("", var8);
            } catch (ClassCastException var9) {
               debug("", var9);
            }
         }
      }

   }

   public String getNetworkAccessPoint() {
      return this.compDesc.getNetworkAccessPoint();
   }

   public MethodDescriptor getEjbTimeoutMethodDescriptor() {
      return this.ejbTimeoutMethodDescriptor;
   }

   public MethodDescriptor getAutomaticTimerMethodDescriptor(String methodSig) {
      return (MethodDescriptor)this.autoTimersMDBySignatureMap.get(methodSig);
   }

   public Map getAutomaticTimerMDs() {
      return this.autoTimersMDMap;
   }

   protected void setupTxTimeout(int jtaConfigTimeout) {
      this.txTimeoutSeconds = this.compDesc.getTransactionTimeoutSeconds();
      if (this.txTimeoutSeconds == 0 && jtaConfigTimeout != 0) {
         this.txTimeoutSeconds = jtaConfigTimeout;
         this.usingJTAConfigTimeout = true;
      }

   }

   public void prepare(Environment env) throws WLDeploymentException {
      this.envBuilder = env;
      this.calculateRunAsPrincipal();
      this.runtimeHelper.checkRunAsPrivileges(this);
      if (this.ejbTimeoutMethod != null) {
         this.ejbTimeoutMethodDescriptor = this.getTimeoutMethodDescriptor(this.ejbTimeoutMethod);
      }

      Iterator var2 = this.autoTimersMap.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry me = (Map.Entry)var2.next();
         MethodDescriptor md = this.getTimeoutMethodDescriptor((Method)me.getValue());
         this.autoTimersMDMap.put(me.getKey(), md);
         this.autoTimersMDBySignatureMap.put(DDUtils.getMethodSignature(md.getMethod()), md);
      }

   }

   private MethodDescriptor getTimeoutMethodDescriptor(Method timeoutMethod) throws WLDeploymentException {
      MethodInfo mi = this.getTimerMethodInfo(DDUtils.getMethodSignature(timeoutMethod));
      mi.setUnchecked(true);
      ClientViewDescriptor timerViewDesc = new ClientViewDescriptor(TimedObject.class, "Timer", true, false, this);
      MethodDescriptor md = this.createMethodDescriptor(timeoutMethod, TimedObject.class, mi, timerViewDesc);
      this.methodDescriptors.add(md);
      return md;
   }

   public void unprepare() {
   }

   public void perhapsStartTimerManager() {
      this.getBeanManager().perhapsStartTimerManager();
   }

   private void calculateRunAsPrincipal() throws WLDeploymentException {
      String runAsRole = this.compDesc.getRunAsRoleName();
      if (runAsRole != null) {
         this.runAsPrincipalName = this.compDesc.getRunAsIdentityPrincipal();
         if (this.runAsPrincipalName == null) {
            this.runAsPrincipalName = this.deploymentInfo.getRunAsRoleAssignment(runAsRole);
         }

         if (this.runAsPrincipalName == null) {
            this.runAsPrincipalName = this.runtimeHelper.getRunAsPrincipalFromRoleMapping(this.ejbName, runAsRole, this.deploymentInfo.getDeploymentRoles());
         }

         if (!this.runtimeHelper.isUserPrincipal(this.runAsPrincipalName)) {
            throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().INVALID_RUN_AS_PRINCIPAL_FOR_EJB(this.getDisplayName(), this.runAsPrincipalName));
         }
      }

      this.runAsPrincipalCalculated = true;
   }

   public String getHomeInterfaceName() {
      return null;
   }

   public String getLocalHomeInterfaceName() {
      return null;
   }

   public String getLocalInterfaceName() {
      return null;
   }

   public String getRemoteInterfaceName() {
      return null;
   }

   public boolean hasLocalClientView() {
      return false;
   }

   public boolean hasRemoteClientView() {
      return false;
   }

   public boolean isClientDriven() {
      return false;
   }

   public boolean isEntityBean() {
      return false;
   }

   public boolean isSessionBean() {
      return false;
   }

   public boolean isMessageDrivenBean() {
      return false;
   }

   public Set getBusinessLocals() {
      return Collections.emptySet();
   }

   public Set getBusinessRemotes() {
      return Collections.emptySet();
   }

   protected Environment getEnvBuilder() {
      return this.envBuilder;
   }

   void setEnvBuilder(Environment envBuilder) {
      this.envBuilder = envBuilder;
   }

   public void clearInjectors() {
      this.beanManager.clearInjectors();
   }

   public void registerInjector(Injector injector) {
      if (this.beanManager == null) {
         throw new IllegalStateException("Bean Manager instance has not yet been created!");
      } else {
         this.beanManager.registerInjector(injector);
      }
   }

   public boolean isVersionGreaterThan30() {
      return this.isVersionGreaterThan30;
   }

   public EnterpriseBeanBean getEnterpriseBeanBean() {
      return this.compDesc.getBean();
   }

   public WeblogicEnterpriseBeanBean getWeblogicEnterpriseBeanBean() {
      return this.compDesc.getWlBean();
   }

   private static void debug(String s) {
      debugLogger.debug("[BeanInfoImpl] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[BeanInfoImpl] " + s, th);
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
