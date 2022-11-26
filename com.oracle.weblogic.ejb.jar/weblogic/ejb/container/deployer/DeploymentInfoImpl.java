package weblogic.ejb.container.deployer;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.ApplicationException;
import javax.security.jacc.PolicyConfiguration;
import javax.security.jacc.PolicyConfigurationFactory;
import javax.security.jacc.PolicyContextException;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.ApplicationContextInternal;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.LifecycleCallbackInterceptorChecker;
import weblogic.ejb.container.compliance.TimeoutCheckHelper;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.deployer.mbimpl.ContainerTransactionImpl;
import weblogic.ejb.container.deployer.mbimpl.ExcludeListImpl;
import weblogic.ejb.container.deployer.mbimpl.IsolationLevelImpl;
import weblogic.ejb.container.deployer.mbimpl.MethodDescriptorImpl;
import weblogic.ejb.container.deployer.mbimpl.MethodPermissionImpl;
import weblogic.ejb.container.deployer.mbimpl.RelationshipsImpl;
import weblogic.ejb.container.deployer.mbimpl.RetryMethodsOnRollbackImpl;
import weblogic.ejb.container.deployer.mbimpl.RoleDescriptorImpl;
import weblogic.ejb.container.deployer.mbimpl.SecurityRoleMappingImpl;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.ContainerTransaction;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.ExceptionInfo;
import weblogic.ejb.container.interfaces.ExcludeList;
import weblogic.ejb.container.interfaces.IsolationLevel;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MethodDescriptor;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.MethodPermission;
import weblogic.ejb.container.interfaces.RetryMethodsOnRollback;
import weblogic.ejb.container.interfaces.RoleDescriptor;
import weblogic.ejb.container.interfaces.SecurityRoleMapping;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.ejb.container.persistence.CMPBeanDescriptorImpl;
import weblogic.ejb.container.persistence.InstalledPersistence;
import weblogic.ejb.container.persistence.PersistenceException;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.ApplicationExceptionBean;
import weblogic.j2ee.descriptor.AssemblyDescriptorBean;
import weblogic.j2ee.descriptor.ContainerTransactionBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.ExcludeListBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.MethodPermissionBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.IdempotentMethodsBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.MethodBean;
import weblogic.j2ee.descriptor.wl.RetryMethodsOnRollbackBean;
import weblogic.j2ee.descriptor.wl.RunAsRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.SkipStateReplicationMethodsBean;
import weblogic.j2ee.descriptor.wl.TransactionIsolationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.kernel.KernelStatus;
import weblogic.logging.Loggable;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.jacc.RoleMapper;
import weblogic.security.jacc.RoleMapperFactory;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.classloaders.ClassPreProcessor;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class DeploymentInfoImpl implements DeploymentInfo {
   private static final DebugLogger debugLogger;
   private static final AuthenticatedSubject KERNEL_ID;
   private static final boolean SKIP_ENTITY_PKENHANCE;
   private static final ExceptionInfo APP_EXCEPTION_DEFAULT;
   private static final ExceptionInfo NOT_APP_EXCEPTION_DEFAULT;
   private final GenericClassLoader classLoader;
   private final Map beanInfos = new HashMap();
   private final Set sessionBeanInfos = new HashSet();
   private final Set messageDrivenBeanInfos = new HashSet();
   private final Set entityBeanInfos = new HashSet();
   private final EjbDescriptorBean ejbDescriptor;
   private final Map runAsRoleAssignments = new HashMap();
   private final SecurityRoleMapping securityRoleMapping = new SecurityRoleMappingImpl();
   private Relationships relationships;
   private final String applicationId;
   private final String applicationName;
   private final String moduleId;
   private final String moduleName;
   private final String moduleURI;
   private String securityRealmName;
   private final String jarFileName;
   private final String clientJarFileName;
   private String jaccPolicyContextId;
   private PolicyConfiguration jaccPolicyConfig;
   private URL jaccCodeSourceURL;
   private RoleMapper jaccRoleMapper;
   private boolean enableDynamicQueries = false;
   private final boolean enableBeanClassRedeploy;
   private final PitchforkContext pitchforkContext;
   private final Set disabledWarnings;
   private final Map applicationExceptions = new HashMap();
   private final Map appExeptionsCache = new ConcurrentHashMap();
   private final Map ejbToInterceptor = new HashMap();
   private final Map iceptorClassToIBean = new HashMap();
   private boolean isEar;
   private final boolean isCDIEnabled;
   private BeanDiscoveryMode beanDiscoveryMode;
   private String compMBeanName;
   private boolean isAnyAuthUserRoleDefinedInDD = false;
   private String myLogString = null;

   public DeploymentInfoImpl(EjbDescriptorBean desc, GenericClassLoader moduleCL, String moduleName, String moduleURI, String moduleId, VirtualJarFile virtualJarFile, ApplicationContextInternal appCtx, boolean isCDIEnabled) throws ErrorCollectionException, DeploymentDescriptorException, PersistenceException, WLDeploymentException {
      this.classLoader = moduleCL;
      this.ejbDescriptor = desc;
      this.isCDIEnabled = isCDIEnabled;
      if (appCtx != null) {
         this.applicationName = appCtx.getApplicationName();
         this.applicationId = appCtx.getApplicationId();
         this.securityRealmName = appCtx.getApplicationSecurityRealmName();
         this.isEar = appCtx.isEar();
      } else {
         this.applicationName = "";
         this.applicationId = null;
      }

      this.moduleName = moduleName;
      this.moduleURI = moduleURI;
      this.moduleId = moduleId;
      this.jarFileName = virtualJarFile != null ? virtualJarFile.getName() : null;
      this.clientJarFileName = desc.getEjbJarBean().getEjbClientJar();
      this.disabledWarnings = this.getDisabledWarnings();
      this.enableBeanClassRedeploy = desc.getWeblogicEjbJarBean().isEnableBeanClassRedeploy();
      String componentFactoryClassName = desc.getWeblogicEjbJarBean().getComponentFactoryClassName();
      if (componentFactoryClassName == null && appCtx != null && appCtx.getWLApplicationDD() != null) {
         componentFactoryClassName = appCtx.getWLApplicationDD().getComponentFactoryClassName();
      }

      this.pitchforkContext = new PitchforkContext(componentFactoryClassName);
      if (KernelStatus.isServer()) {
         if (this.securityRealmName == null) {
            this.securityRealmName = SecurityServiceManager.getDefaultRealmName();
         }

         this.initializeJAAC(appCtx, virtualJarFile);
      }

      this.initializeRoles();
      this.initializeJarScopedRunAsRoles();
      this.initializeBeanInfos();
      this.initializeMethodPermissions();
      this.initializeTransactionAttribute(appCtx);
      this.initializeTransactionLevels();
      this.initializeIdempotentMethods();
      this.initializeSkipStateReplicationMethods();
      this.initializeRetryMethodsOnRollback();
      this.initializeExcludedMethods();
      if (this.ejbDescriptor.isEjb30()) {
         this.initializeApplicationExceptions();
      }

      this.setupRelationsForEJBAndInterceptors();
      this.initMyLogString();
      if (debugLogger.isDebugEnabled()) {
         debug("DeploymentInfoImpl() creates new instance:[moduleName=" + moduleName + ",applicationName=" + this.applicationName + "].");
      }

   }

   private void initializeJAAC(ApplicationContextInternal appCtx, VirtualJarFile vjf) throws WLDeploymentException {
      if (appCtx != null && appCtx.getSecurityProvider().isJACCEnabled()) {
         this.jaccPolicyContextId = ManagementService.getRuntimeAccess(KERNEL_ID).getServer().getName() + "_" + this.applicationName + "_" + this.moduleId;
         File f = vjf.getDirectory();
         String jaccCodeSource;
         if (f != null) {
            jaccCodeSource = f.getName();
         } else {
            jaccCodeSource = vjf.getName();
            int lastSlash = jaccCodeSource.lastIndexOf(File.separatorChar);
            if (lastSlash != -1 && jaccCodeSource.length() > 1) {
               jaccCodeSource = jaccCodeSource.substring(0, lastSlash);
            }
         }

         if (this.jaccPolicyContextId != null) {
            try {
               this.jaccCodeSourceURL = new URL((new URI("file:///" + jaccCodeSource.replace('\\', '/'))).toString());
            } catch (URISyntaxException | MalformedURLException var7) {
               throw new WLDeploymentException(var7.getMessage(), var7);
            }
         }

         try {
            this.jaccPolicyConfig = PolicyConfigurationFactory.getPolicyConfigurationFactory().getPolicyConfiguration(this.jaccPolicyContextId, true);
            this.jaccRoleMapper = RoleMapperFactory.getRoleMapperFactory().getRoleMapper(this.applicationId, this.jaccPolicyContextId, false);
         } catch (PolicyContextException | ClassNotFoundException var6) {
            throw new WLDeploymentException(var6.getMessage(), var6);
         }
      }
   }

   public String getRunAsRoleAssignment(String roleName) {
      return (String)this.runAsRoleAssignments.get(roleName);
   }

   /** @deprecated */
   @Deprecated
   public String getApplicationName() {
      return this.applicationName;
   }

   public String getJACCPolicyContextId() {
      return this.jaccPolicyContextId;
   }

   public PolicyConfiguration getJACCPolicyConfig() {
      return this.jaccPolicyConfig;
   }

   public URL getJACCCodeSourceURL() {
      return this.jaccCodeSourceURL;
   }

   public RoleMapper getJACCRoleMapper() {
      return this.jaccRoleMapper;
   }

   public String getModuleURI() {
      return this.moduleURI;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public String getSecurityRealmName() {
      return this.securityRealmName;
   }

   /** @deprecated */
   @Deprecated
   public String getJarFileName() {
      return this.jarFileName;
   }

   public String getClientJarFileName() {
      return this.clientJarFileName;
   }

   public boolean isDynamicQueriesEnabled() {
      return this.enableDynamicQueries;
   }

   public EjbDescriptorBean getEjbDescriptorBean() {
      return this.ejbDescriptor;
   }

   public Map getApplicationExceptions() {
      return this.applicationExceptions;
   }

   public Collection getBeanInfos() {
      return this.beanInfos.values();
   }

   public Collection getSessionBeanInfos() {
      return this.sessionBeanInfos;
   }

   public Collection getMessageDrivenBeanInfos() {
      return this.messageDrivenBeanInfos;
   }

   public Collection getEntityBeanInfos() {
      return this.entityBeanInfos;
   }

   public BeanInfo getBeanInfo(String ejbName) {
      return (BeanInfo)this.beanInfos.get(ejbName);
   }

   public GenericClassLoader getModuleClassLoader() {
      return this.classLoader;
   }

   public SecurityRoleMapping getDeploymentRoles() {
      return this.securityRoleMapping;
   }

   public Relationships getRelationships() {
      return this.relationships;
   }

   public boolean isWarningDisabled(String warning) {
      return this.disabledWarnings.contains(warning);
   }

   public boolean isEnableBeanClassRedeploy() {
      return this.enableBeanClassRedeploy;
   }

   public PitchforkContext getPitchforkContext() {
      return this.pitchforkContext;
   }

   public List getInterceptorBeans(String ejbName) {
      List interceptorBeans = new LinkedList();
      Set interceptorClassNames = (Set)this.ejbToInterceptor.get(ejbName);
      Set defaultInterceptorClassNames = (Set)this.ejbToInterceptor.get("*");
      if (interceptorClassNames == null) {
         interceptorClassNames = defaultInterceptorClassNames;
      } else if (defaultInterceptorClassNames != null) {
         interceptorClassNames.addAll(defaultInterceptorClassNames);
      }

      if (interceptorClassNames != null) {
         Iterator var5 = interceptorClassNames.iterator();

         while(var5.hasNext()) {
            String interceptorClassName = (String)var5.next();
            interceptorBeans.add(this.iceptorClassToIBean.get(interceptorClassName));
         }
      }

      return interceptorBeans;
   }

   private void initializeRoles() {
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      if (ad != null) {
         boolean isAnyAuthRoleDefined = false;
         Collection roleDescriptors = new ArrayList();
         SecurityRoleBean[] var4 = ad.getSecurityRoles();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            SecurityRoleBean sr = var4[var6];
            roleDescriptors.add(new RoleDescriptorImpl(this.ejbDescriptor, sr.getRoleName()));
            if ("**".equals(sr.getRoleName())) {
               isAnyAuthRoleDefined = true;
            }
         }

         roleDescriptors.add(new RoleDescriptorImpl(this.ejbDescriptor, "**"));

         RoleDescriptor rd;
         for(Iterator var8 = roleDescriptors.iterator(); var8.hasNext(); this.isAnyAuthUserRoleDefinedInDD = isAnyAuthRoleDefined || rd.isAnyAuthUserRoleDefined()) {
            rd = (RoleDescriptor)var8.next();
            this.securityRoleMapping.addRoleToPrincipalsMapping(rd.getName(), rd.getAllSecurityPrincipals());
            if (rd.isExternallyDefined()) {
               this.securityRoleMapping.addExternallyDefinedRole(rd.getName());
            }
         }

      }
   }

   private void initializeJarScopedRunAsRoles() {
      RunAsRoleAssignmentBean[] var1 = this.ejbDescriptor.getWeblogicEjbJarBean().getRunAsRoleAssignments();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         RunAsRoleAssignmentBean ra = var1[var3];
         this.runAsRoleAssignments.put(ra.getRoleName(), ra.getRunAsPrincipalName());
      }

   }

   private void initializeBeanInfos() throws DeploymentDescriptorException, PersistenceException, WLDeploymentException {
      InstalledPersistence installedPersistence = new InstalledPersistence();
      List cmp20BeanList = new ArrayList();
      Map beanMap = new HashMap();
      List allBeanList = new ArrayList();
      Map allBeanMap = new HashMap();
      EnterpriseBeanBean[] beans = CompositeDescriptor.getEnterpriseBeans(this.ejbDescriptor.getEjbJarBean());
      int var9;
      if (!SKIP_ENTITY_PKENHANCE) {
         List pkClassNames = new ArrayList();
         EnterpriseBeanBean[] var8 = beans;
         var9 = beans.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            EnterpriseBeanBean ebb = var8[var10];
            if (ebb instanceof EntityBeanBean) {
               pkClassNames.add(((EntityBeanBean)ebb).getPrimKeyClass());
            }
         }

         ClassPreProcessor cpp = new PKClassPreProcessor(pkClassNames);
         this.classLoader.addInstanceClassPreProcessor(cpp);
         Iterator var23 = pkClassNames.iterator();

         while(var23.hasNext()) {
            String pkClassName = (String)var23.next();

            try {
               this.classLoader.loadClass(pkClassName);
            } catch (ClassNotFoundException var17) {
               throw new WLDeploymentException(pkClassName, var17);
            }
         }
      }

      EnterpriseBeanBean[] var18 = beans;
      int var21 = beans.length;

      for(var9 = 0; var9 < var21; ++var9) {
         EnterpriseBeanBean bean = var18[var9];
         CompositeDescriptor desc = new CompositeDescriptor(bean, this.ejbDescriptor);
         BeanInfo bi = null;

         try {
            bi = this.createBeanInfoImpl(desc);
         } catch (ClassNotFoundException var16) {
            Loggable l = EJBLogger.logUnableLoadClassSpecifiedInDDLoggable(var16.getMessage());
            throw new DeploymentDescriptorException(l.getMessage(), new DescriptorErrorInfo("<ejb-class>", bean.getEjbName(), bean.getEjbName()));
         }

         if (bi instanceof EntityBeanInfo) {
            EntityBeanInfo ebi = (EntityBeanInfo)bi;
            if (!ebi.getIsBeanManagedPersistence()) {
               CMPInfoImpl cmpi = (CMPInfoImpl)ebi.getCMPInfo();
               CMPBeanDescriptor bd = new CMPBeanDescriptorImpl(ebi, this.ejbDescriptor);
               if (cmpi.uses20CMP()) {
                  beanMap.put(ebi.getEJBName(), bd);
                  cmp20BeanList.add(ebi);
                  this.enableDynamicQueries |= ebi.isDynamicQueriesEnabled();
               }

               allBeanMap.put(ebi.getEJBName(), bd);
               allBeanList.add(ebi);
               cmpi.setPersistenceType(this.getPersistenceType(ebi, installedPersistence));
            }
         }
      }

      this.relationships = new RelationshipsImpl(this.ejbDescriptor.getEjbJarBean().getRelationships());
      Iterator var19 = cmp20BeanList.iterator();

      EntityBeanInfo ebi;
      while(var19.hasNext()) {
         ebi = (EntityBeanInfo)var19.next();
         CMPInfoImpl cmpi = (CMPInfoImpl)ebi.getCMPInfo();
         cmpi.setBeanMap(beanMap);
         cmpi.setRelationships(this.relationships);
      }

      var19 = allBeanList.iterator();

      while(var19.hasNext()) {
         ebi = (EntityBeanInfo)var19.next();
         ((CMPInfoImpl)ebi.getCMPInfo()).setAllBeanMap(allBeanMap);
      }

   }

   private BeanInfo createBeanInfoImpl(CompositeDescriptor desc) throws ClassNotFoundException, WLDeploymentException {
      EnterpriseBeanBean ebb = desc.getBean();
      BeanInfoImpl bi = null;
      if (ebb instanceof SessionBeanBean) {
         String sessionType = ((SessionBeanBean)ebb).getSessionType();
         if ("Stateful".equalsIgnoreCase(sessionType)) {
            bi = new StatefulSessionBeanInfoImpl(this, desc, this.classLoader);
         } else if ("Stateless".equalsIgnoreCase(sessionType)) {
            bi = new StatelessSessionBeanInfoImpl(this, desc, this.classLoader);
         } else {
            if (!"Singleton".equalsIgnoreCase(sessionType)) {
               throw new AssertionError("Unknown type of Session Bean :" + sessionType);
            }

            bi = new SingletonSessionBeanInfoImpl(this, desc, this.classLoader);
         }

         this.debugCreateBeanObject(sessionType, ((BeanInfoImpl)bi).getEJBName());
         this.sessionBeanInfos.add((SessionBeanInfo)bi);
      } else if (ebb instanceof MessageDrivenBeanBean) {
         bi = new MessageDrivenBeanInfoImpl(this, desc, this.classLoader);
         this.debugCreateBeanObject("MessageDriven", ((BeanInfoImpl)bi).getEJBName());
         this.messageDrivenBeanInfos.add((MessageDrivenBeanInfo)bi);
      } else {
         if (!(ebb instanceof EntityBeanBean)) {
            throw new AssertionError("Unknown type of bean:" + ebb);
         }

         bi = new EntityBeanInfoImpl(this, desc, this.classLoader);
         this.debugCreateBeanObject("Entity", ((BeanInfoImpl)bi).getEJBName());
         this.entityBeanInfos.add((EntityBeanInfo)bi);
      }

      this.beanInfos.put(desc.getEJBName(), bi);
      ((BeanInfoImpl)bi).init();
      return (BeanInfo)bi;
   }

   private PersistenceType getPersistenceType(EntityBeanInfo ebi, InstalledPersistence installedPersistence) throws PersistenceException {
      CMPInfo cmpi = ebi.getCMPInfo();
      String persistenceUseIdentifier = cmpi.getPersistenceUseIdentifier();
      if (persistenceUseIdentifier == null) {
         throw new PersistenceException(this.getFmt().BEAN_MISSING_PERSISTENCE_USE(ebi.getEJBName()));
      } else {
         PersistenceType persistenceType = installedPersistence.getInstalledType(persistenceUseIdentifier, cmpi.getPersistenceUseVersion());
         if (persistenceType != null) {
            if (!persistenceType.getCmpVersion().equalsIgnoreCase(ebi.getCMPInfo().getCMPVersion())) {
               throw new PersistenceException(this.getFmt().incompatibleCmpVersion(ebi.getEJBName(), ebi.getCMPInfo().getCMPVersion(), persistenceType.getCmpVersion()));
            } else if (!persistenceType.getWeblogicVersion().equalsIgnoreCase("7")) {
               throw new PersistenceException(this.getFmt().incompatibleVendorPersistenceType(ebi.getEJBName(), persistenceType.getWeblogicVersion(), "7"));
            } else {
               return persistenceType;
            }
         } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getFmt().vendorPersistenceTypeNotInstalled(persistenceUseIdentifier, cmpi.getPersistenceUseVersion(), ebi.getEJBName()));
            Iterator iter = installedPersistence.getInstalledTypes().iterator();
            if (!iter.hasNext()) {
               sb.append("<No persistence types installed.>");
            } else {
               while(iter.hasNext()) {
                  PersistenceType pType = (PersistenceType)iter.next();
                  sb.append("(" + pType.getIdentifier() + ", " + pType.getVersion() + ")");
                  if (iter.hasNext()) {
                     sb.append(", ");
                  }
               }

               sb.append(".");
            }

            throw new PersistenceException(sb.toString());
         }
      }
   }

   private Collection getAllMethodPermissions() {
      Collection result = new ArrayList();
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      if (null != ad) {
         MethodPermissionBean[] var3 = ad.getMethodPermissions();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MethodPermissionBean mp = var3[var5];
            result.add(new MethodPermissionImpl(mp));
         }
      }

      return result;
   }

   private void initializeMethodPermissions() throws DeploymentDescriptorException {
      Collection methodPerms = this.getAllMethodPermissions();
      if (!methodPerms.isEmpty()) {
         this.processMPs(methodPerms, (short)1);
         this.processMPs(methodPerms, (short)2);
         this.processMPs(methodPerms, (short)3);
      }

   }

   private void initializeTransactionAttribute(ApplicationContextInternal appCtx) throws DeploymentDescriptorException, WLDeploymentException {
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      if (null != ad) {
         Collection conTrans = new ArrayList();
         ContainerTransactionBean[] var4 = ad.getContainerTransactions();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ContainerTransactionBean ct = var4[var6];
            conTrans.add(new ContainerTransactionImpl(ct));
         }

         this.processCTs(conTrans, (short)1);
         this.processCTs(conTrans, (short)2);
         this.processCTs(conTrans, (short)3);
      }

      int jtaConfigTimeout = 0;
      if (appCtx != null) {
         jtaConfigTimeout = TransactionService.getJTAConfigTimeout(appCtx.getEffectiveDomain(), ComponentInvocationContextManager.getInstance(KERNEL_ID).getCurrentComponentInvocationContext());
      }

      try {
         Iterator var10 = this.getSessionBeanInfos().iterator();

         while(var10.hasNext()) {
            SessionBeanInfo sbi = (SessionBeanInfo)var10.next();
            LifecycleCallbackInterceptorChecker.validateLifeCycleTxAttributes(sbi);
         }

         var10 = this.getBeanInfos().iterator();

         while(var10.hasNext()) {
            BeanInfo bi = (BeanInfo)var10.next();
            bi.assignDefaultTXAttributesIfNecessary(jtaConfigTimeout);
            TimeoutCheckHelper.validateTimeoutMethodsTransactionType(bi);
         }

      } catch (ComplianceException var8) {
         throw new WLDeploymentException(var8.getMessage(), var8);
      }
   }

   private void initializeTransactionLevels() throws DeploymentDescriptorException {
      Collection isoLevels = this.getAllIsolationLevels();
      this.processISOs(isoLevels, (short)1);
      this.processISOs(isoLevels, (short)2);
      this.processISOs(isoLevels, (short)3);
   }

   private void initializeIdempotentMethods() throws DeploymentDescriptorException {
      Collection meths = this.getAllIdempotentMethods();
      this.processIdempotency(meths, (short)1);
      this.processIdempotency(meths, (short)2);
      this.processIdempotency(meths, (short)3);
   }

   private void initializeSkipStateReplicationMethods() throws DeploymentDescriptorException {
      Collection meths = this.getAllSkipStateReplicationMethods();
      this.processSkipReplication(meths, (short)1);
      this.processSkipReplication(meths, (short)2);
      this.processSkipReplication(meths, (short)3);
   }

   private void initializeRetryMethodsOnRollback() throws DeploymentDescriptorException {
      Collection retryMethods = this.getAllRetryMethods();
      this.processRetries(retryMethods, (short)1);
      this.processRetries(retryMethods, (short)2);
      this.processRetries(retryMethods, (short)3);
   }

   private void initializeExcludedMethods() throws DeploymentDescriptorException {
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      if (null != ad) {
         ExcludeListBean el = ad.getExcludeList();
         if (el != null) {
            ExcludeList excludeList = new ExcludeListImpl(el);
            Iterator var4 = excludeList.getAllMethodDescriptors().iterator();

            while(var4.hasNext()) {
               MethodDescriptor md = (MethodDescriptor)var4.next();
               Iterator var6 = this.lookupMethodInfos(md).iterator();

               while(var6.hasNext()) {
                  MethodInfo mi = (MethodInfo)var6.next();
                  mi.setIsExcluded(true);
                  mi.setMethodDescriptorMethodType(md.getMethodType());
               }
            }
         }
      }

   }

   private void initializeApplicationExceptions() {
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      ApplicationExceptionBean[] apExs = ad != null ? ad.getApplicationExceptions() : null;
      if (apExs != null) {
         ApplicationExceptionBean[] var3 = apExs;
         int var4 = apExs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ApplicationExceptionBean ae = var3[var5];
            this.applicationExceptions.put(ae.getExceptionClass(), new ExceptionInfoImpl(true, ae.isInherited(), ae.isRollback()));
         }
      }

   }

   private boolean addApplicationException(Class exception) {
      if (this.ejbDescriptor.isEjb30() && !this.ejbDescriptor.getEjbJarBean().isMetadataComplete() && null != (exception = this.getAppExceptionClass(exception))) {
         ApplicationException ae = (ApplicationException)exception.getAnnotation(ApplicationException.class);
         synchronized(this.applicationExceptions) {
            if (!this.applicationExceptions.containsKey(exception.getName())) {
               this.applicationExceptions.put(exception.getName(), new ExceptionInfoImpl(true, ae.inherited(), ae.rollback()));
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private Class getAppExceptionClass(Class exClass) {
      while(!exClass.equals(Object.class)) {
         if (exClass.isAnnotationPresent(ApplicationException.class)) {
            return exClass;
         }

         exClass = exClass.getSuperclass();
      }

      return null;
   }

   public MessageDestinationDescriptorBean[] getMessageDestinationDescriptors() {
      WeblogicEjbJarBean wej = this.ejbDescriptor.getWeblogicEjbJarBean();
      return wej != null ? wej.getMessageDestinationDescriptors() : null;
   }

   public MessageDestinationBean[] getMessageDestinations() {
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      return ad != null ? ad.getMessageDestinations() : null;
   }

   private void setupRelationsForEJBAndInterceptors() {
      Set allIceptorClassNames = new HashSet();
      AssemblyDescriptorBean ad = this.ejbDescriptor.getEjbJarBean().getAssemblyDescriptor();
      int var5;
      if (ad != null) {
         InterceptorBindingBean[] var3 = ad.getInterceptorBindings();
         int var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            InterceptorBindingBean ibb = var3[var5];
            String[] iceptorClassNames = null;
            if (ibb.getInterceptorOrder() != null) {
               iceptorClassNames = ibb.getInterceptorOrder().getInterceptorClasses();
            } else {
               iceptorClassNames = ibb.getInterceptorClasses();
            }

            String ejbName = ibb.getEjbName();
            Set interceptorClassNames = (Set)this.ejbToInterceptor.get(ejbName);
            if (interceptorClassNames == null) {
               interceptorClassNames = new HashSet();
            }

            String[] var10 = iceptorClassNames;
            int var11 = iceptorClassNames.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String iceptorClassName = var10[var12];
               allIceptorClassNames.add(iceptorClassName);
               ((Set)interceptorClassNames).add(iceptorClassName);
            }

            this.ejbToInterceptor.put(ejbName, interceptorClassNames);
         }
      }

      InterceptorsBean iceptors = this.ejbDescriptor.getEjbJarBean().getInterceptors();
      if (iceptors != null) {
         InterceptorBean[] var15 = iceptors.getInterceptors();
         var5 = var15.length;

         for(int var16 = 0; var16 < var5; ++var16) {
            InterceptorBean ib = var15[var16];
            if (allIceptorClassNames.contains(ib.getInterceptorClass())) {
               this.iceptorClassToIBean.put(ib.getInterceptorClass(), ib);
            }
         }
      }

   }

   private static int selectForUpdateFromIsoLevel(String s) {
      if (s.equalsIgnoreCase("TransactionReadCommittedForUpdate")) {
         return 1;
      } else if (s.equalsIgnoreCase("TransactionReadCommittedForUpdateNoWait")) {
         return 2;
      } else if (s.equalsIgnoreCase("TRANSACTION_READ_COMMITTED_FOR_UPDATE")) {
         return 1;
      } else {
         return s.equalsIgnoreCase("TRANSACTION_READ_COMMITTED_FOR_UPDATE_NO_WAIT") ? 2 : 0;
      }
   }

   private void processISOs(Collection isoLevels, short methodType) throws DeploymentDescriptorException {
      Iterator var3 = isoLevels.iterator();

      label39:
      while(var3.hasNext()) {
         IsolationLevel il = (IsolationLevel)var3.next();
         Iterator var5 = il.getAllMethodDescriptors().iterator();

         while(true) {
            MethodDescriptor md;
            do {
               if (!var5.hasNext()) {
                  continue label39;
               }

               md = (MethodDescriptor)var5.next();
            } while(md.getMethodType() != methodType);

            Collection mis = this.lookupMethodInfos(md);
            if (mis.isEmpty()) {
               String interfaceName = "";
               if (md.getMethodIntf() != null) {
                  interfaceName = md.getMethodIntf() + " ";
               }

               throw new DeploymentDescriptorException(this.getFmt().noMethodFoundForEJBDeploymentDescriptorSetting(md.getMethodSignature(), md.getEjbName(), interfaceName, "weblogic-ejb-jar.xml", "isolation-level"), new DescriptorErrorInfo("<isolation-level>", md.getEjbName(), md.getMethodSignature()));
            }

            Iterator var8 = mis.iterator();

            while(var8.hasNext()) {
               MethodInfo mi = (MethodInfo)var8.next();
               String isoLevelStr = il.getIsolationLevel();
               mi.setSelectForUpdate(selectForUpdateFromIsoLevel(isoLevelStr));
               mi.setTxIsolationLevel(DDUtils.isoStringToInt(isoLevelStr));
            }
         }
      }

   }

   private Collection getAllIsolationLevels() {
      Collection result = new ArrayList();
      TransactionIsolationBean[] var2 = this.ejbDescriptor.getWeblogicEjbJarBean().getTransactionIsolations();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TransactionIsolationBean ti = var2[var4];
         result.add(new IsolationLevelImpl(ti));
      }

      return result;
   }

   private Collection getAllIdempotentMethods() {
      Collection result = new ArrayList();
      IdempotentMethodsBean im = this.ejbDescriptor.getWeblogicEjbJarBean().getIdempotentMethods();
      if (im != null) {
         MethodBean[] var3 = im.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MethodBean mb = var3[var5];
            result.add(new MethodDescriptorImpl(mb));
         }
      }

      return result;
   }

   private Collection getAllSkipStateReplicationMethods() {
      Collection result = new ArrayList();
      SkipStateReplicationMethodsBean ssrmb = this.ejbDescriptor.getWeblogicEjbJarBean().getSkipStateReplicationMethods();
      if (ssrmb != null) {
         MethodBean[] var3 = ssrmb.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MethodBean mb = var3[var5];
            result.add(new MethodDescriptorImpl(mb));
         }
      }

      return result;
   }

   private Collection getAllRetryMethods() {
      Collection result = new ArrayList();
      RetryMethodsOnRollbackBean[] var2 = this.ejbDescriptor.getWeblogicEjbJarBean().getRetryMethodsOnRollbacks();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         RetryMethodsOnRollbackBean rm = var2[var4];
         result.add(new RetryMethodsOnRollbackImpl(rm));
      }

      return result;
   }

   private void processIdempotency(Collection mds, short methodType) throws DeploymentDescriptorException {
      Iterator var3 = mds.iterator();

      while(true) {
         MethodDescriptor md;
         do {
            if (!var3.hasNext()) {
               return;
            }

            md = (MethodDescriptor)var3.next();
         } while(md.getMethodType() != methodType);

         Collection mis = this.lookupMethodInfos(md);
         if (mis.isEmpty()) {
            String interfaceName = "";
            if (md.getMethodIntf() != null) {
               interfaceName = md.getMethodIntf() + " ";
            }

            throw new DeploymentDescriptorException(this.getFmt().noMethodFoundForEJBDeploymentDescriptorSetting(md.getMethodSignature(), md.getEjbName(), interfaceName, "weblogic-ejb-jar.xml", "idempotency"), new DescriptorErrorInfo("<idempotent-methods>", md.getEjbName(), md.getMethodSignature()));
         }

         Iterator var6 = mis.iterator();

         while(var6.hasNext()) {
            MethodInfo mi = (MethodInfo)var6.next();
            mi.setIdempotent(true);
         }
      }
   }

   private void processSkipReplication(Collection mds, short methodType) throws DeploymentDescriptorException {
      Iterator var3 = mds.iterator();

      while(true) {
         MethodDescriptor md;
         do {
            if (!var3.hasNext()) {
               return;
            }

            md = (MethodDescriptor)var3.next();
         } while(md.getMethodType() != methodType);

         Collection mis = this.lookupMethodInfos(md);
         if (mis.isEmpty()) {
            String interfaceName = "";
            if (md.getMethodIntf() != null) {
               interfaceName = md.getMethodIntf() + " ";
            }

            throw new DeploymentDescriptorException(this.getFmt().noMethodFoundForEJBDeploymentDescriptorSetting(md.getMethodSignature(), md.getEjbName(), interfaceName, "weblogic-ejb-jar.xml", "skip-state-replication-methods"), new DescriptorErrorInfo("<skip-state-replication-methods>", md.getEjbName(), md.getMethodSignature()));
         }

         Iterator var6 = mis.iterator();

         while(var6.hasNext()) {
            MethodInfo mi = (MethodInfo)var6.next();
            mi.setSkipStateReplication(true);
         }
      }
   }

   private void processRetries(Collection retryMethods, short methodType) throws DeploymentDescriptorException {
      Iterator var3 = retryMethods.iterator();

      label39:
      while(var3.hasNext()) {
         RetryMethodsOnRollback rm = (RetryMethodsOnRollback)var3.next();
         Iterator var5 = rm.getAllMethodDescriptors().iterator();

         while(true) {
            MethodDescriptor md;
            do {
               if (!var5.hasNext()) {
                  continue label39;
               }

               md = (MethodDescriptor)var5.next();
            } while(md.getMethodType() != methodType);

            Collection mis = this.lookupMethodInfos(md);
            if (mis.isEmpty()) {
               String interfaceName = "";
               if (md.getMethodIntf() != null) {
                  interfaceName = md.getMethodIntf() + " ";
               }

               throw new DeploymentDescriptorException(this.getFmt().noMethodFoundForEJBDeploymentDescriptorSetting(md.getMethodSignature(), md.getEjbName(), interfaceName, "weblogic-ejb-jar.xml", "retry-methods-on-rollback"), new DescriptorErrorInfo("<isolation-level>", md.getEjbName(), md.getMethodSignature()));
            }

            Iterator var8 = mis.iterator();

            while(var8.hasNext()) {
               MethodInfo mi = (MethodInfo)var8.next();
               mi.setRetryOnRollbackCount(rm.getRetryCount());
            }
         }
      }

   }

   private void processMPs(Collection mps, short methodType) throws DeploymentDescriptorException {
      Iterator var3 = mps.iterator();

      label62:
      while(var3.hasNext()) {
         MethodPermission mp = (MethodPermission)var3.next();
         boolean isUnchecked = mp.isUnchecked();
         Iterator var6 = mp.getAllMethodDescriptors().iterator();

         label60:
         while(true) {
            MethodDescriptor md;
            do {
               if (!var6.hasNext()) {
                  continue label62;
               }

               md = (MethodDescriptor)var6.next();
            } while(md.getMethodType() != methodType);

            Collection mis = this.lookupMethodInfos(md);
            if (mis.isEmpty()) {
               String[] associatedXMLElements = new String[]{"<method>", "<method-permission>"};
               String interfaceName = "";
               if (md.getMethodIntf() != null) {
                  interfaceName = md.getMethodIntf() + " ";
               }

               throw new DeploymentDescriptorException(this.getFmt().noMethodFoundForEJBDeploymentDescriptorSetting(md.getMethodSignature(), md.getEjbName(), interfaceName, "ejb-jar.xml", "method permission"), new DescriptorErrorInfo(associatedXMLElements, md.getEjbName(), md.getMethodSignature()));
            }

            Iterator var9 = mis.iterator();

            while(true) {
               while(true) {
                  if (!var9.hasNext()) {
                     continue label60;
                  }

                  MethodInfo mi = (MethodInfo)var9.next();
                  mi.setMethodDescriptorMethodType(md.getMethodType());
                  if (isUnchecked) {
                     mi.setUnchecked(true);
                  } else {
                     Iterator var11 = mp.getAllRoleNames().iterator();

                     while(var11.hasNext()) {
                        String roleName = (String)var11.next();
                        if (!this.securityRoleMapping.hasRole(roleName) && (!"**".equals(roleName) || this.isAnyAuthUserRoleDefinedInDD())) {
                           throw new DeploymentDescriptorException(this.getFmt().METHOD_PERMISSION_ROLE_NAME_NOT_DECLARED(roleName), new DescriptorErrorInfo("<role-name>", roleName, roleName));
                        }

                        mi.addSecurityRoleRestriction(roleName);
                     }
                  }
               }
            }
         }
      }

   }

   private Collection lookupMethodInfos(MethodDescriptor md) throws DeploymentDescriptorException {
      BeanInfo bd = this.getBeanInfo(md.getEjbName());
      if (bd == null) {
         throw new DeploymentDescriptorException("Could not find an EJB named " + md.getEjbName() + " while parsing the assembly-descriptor", new DescriptorErrorInfo("<ejb-name>", md.getEjbName(), md.getEjbName()));
      } else {
         List methodInfos = new ArrayList();
         String sig = md.getMethodSignature();
         String methodIntf = md.getMethodIntf();
         if (bd instanceof MessageDrivenBeanInfo) {
            if ("MessageEndpoint".equals(methodIntf)) {
               if (((MessageDrivenBeanInfo)bd).getIsJMSBased()) {
                  if (sig.startsWith("onMessage") || sig.equals("*")) {
                     methodInfos.add(((MessageDrivenBeanInfo)bd).getOnMessageMethodInfo());
                  }
               } else {
                  methodInfos.addAll(this.getMatchingMethods(bd, "MessageEndpoint", sig));
               }
            } else if ("Timer".equals(methodIntf)) {
               methodInfos.addAll(this.getMatchingMethods(bd, "Timer", sig));
            } else {
               if (((MessageDrivenBeanInfo)bd).getIsJMSBased()) {
                  if (sig.startsWith("onMessage") || sig.equals("*")) {
                     methodInfos.add(((MessageDrivenBeanInfo)bd).getOnMessageMethodInfo());
                  }
               } else {
                  methodInfos.addAll(this.getMatchingMethods(bd, "MessageEndpoint", sig));
               }

               methodInfos.addAll(this.getMatchingMethods(bd, "Timer", sig));
            }
         } else if ("Remote".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "Remote", sig));
         } else if ("Home".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "Home", sig));
         } else if ("Local".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "Local", sig));
         } else if ("LocalHome".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "LocalHome", sig));
         } else if ("ServiceEndpoint".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "ServiceEndpoint", sig));
         } else if ("Timer".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "Timer", sig));
         } else if ("LifecycleCallback".equals(methodIntf)) {
            methodInfos.addAll(this.getMatchingMethods(bd, "LifecycleCallback", sig));
         } else {
            methodInfos.addAll(this.getMatchingMethods(bd, "Local", sig));
            methodInfos.addAll(this.getMatchingMethods(bd, "LocalHome", sig));
            methodInfos.addAll(this.getMatchingMethods(bd, "Remote", sig));
            methodInfos.addAll(this.getMatchingMethods(bd, "Home", sig));
            methodInfos.addAll(this.getMatchingMethods(bd, "ServiceEndpoint", sig));
            methodInfos.addAll(this.getMatchingMethods(bd, "Timer", sig));
            methodInfos.addAll(this.getMatchingMethods(bd, "LifecycleCallback", sig));
         }

         return methodInfos;
      }
   }

   private Collection getMatchingMethods(BeanInfo beanD, String intf, String methodSig) {
      Collection returnMethodInfos = new ArrayList();
      Collection mis = null;
      ClientDrivenBeanInfo bi = null;
      MessageDrivenBeanInfo mdbi = null;
      if (beanD instanceof MessageDrivenBeanInfo) {
         mdbi = (MessageDrivenBeanInfo)beanD;
      } else {
         bi = (ClientDrivenBeanInfo)beanD;
      }

      if (intf == "Home") {
         mis = bi.getAllHomeMethodInfos();
      } else if (intf == "Remote") {
         mis = bi.getAllRemoteMethodInfos();
      } else if (intf == "LocalHome") {
         mis = bi.getAllLocalHomeMethodInfos();
      } else if (intf == "Local") {
         mis = bi.getAllLocalMethodInfos();
      } else if (intf == "MessageEndpoint") {
         mis = mdbi.getAllMessagingTypeMethodInfos();
      } else if (intf == "ServiceEndpoint") {
         mis = bi.getAllWebserviceMethodInfos();
      } else if (intf == "Timer") {
         if (mdbi != null) {
            mis = mdbi.getAllTimerMethodInfos();
         } else {
            mis = bi.getAllTimerMethodInfos();
         }
      } else {
         if (intf != "LifecycleCallback") {
            throw new AssertionError("Unknown interface type: " + intf);
         }

         mis = bi.getAllCallbackMethodInfos();
      }

      if (methodSig.equals("*")) {
         returnMethodInfos.addAll(mis);
         return returnMethodInfos;
      } else {
         Iterator var8 = mis.iterator();

         while(true) {
            MethodInfo mi;
            do {
               if (!var8.hasNext()) {
                  return returnMethodInfos;
               }

               mi = (MethodInfo)var8.next();
            } while(!methodSig.equals(mi.getSignature()) && !methodSig.equals(mi.getMethodName()));

            returnMethodInfos.add(mi);
         }
      }
   }

   private void processCTs(Collection cts, short methodType) throws DeploymentDescriptorException {
      Iterator var3 = cts.iterator();

      label39:
      while(var3.hasNext()) {
         ContainerTransaction ct = (ContainerTransaction)var3.next();
         Iterator var5 = ct.getAllMethodDescriptors().iterator();

         while(true) {
            MethodDescriptor md;
            do {
               if (!var5.hasNext()) {
                  continue label39;
               }

               md = (MethodDescriptor)var5.next();
            } while(md.getMethodType() != methodType);

            Collection mis = this.lookupMethodInfos(md);
            if (mis.isEmpty()) {
               String interfaceName = "";
               if (md.getMethodIntf() != null) {
                  interfaceName = md.getMethodIntf() + " ";
               }

               throw new DeploymentDescriptorException(this.getFmt().noMethodFoundForEJBDeploymentDescriptorSetting(md.getMethodSignature(), md.getEjbName(), interfaceName, "ejb-jar.xml", "transaction attribute"), new DescriptorErrorInfo("<trans-attribute>", md.getEjbName(), md.getMethodSignature()));
            }

            Iterator var8 = mis.iterator();

            while(var8.hasNext()) {
               MethodInfo mi = (MethodInfo)var8.next();
               Short txAttr = (Short)DDConstants.VALID_TX_ATTRIBUTES.get(ct.getTransactionAttribute());
               mi.setTransactionAttribute(txAttr);
            }
         }
      }

   }

   private Set getDisabledWarnings() {
      Set dws = new HashSet();
      String[] var2 = this.ejbDescriptor.getWeblogicEjbJarBean().getDisableWarnings();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         dws.add(s);
      }

      return dws;
   }

   public String getApplicationId() {
      return this.applicationId;
   }

   public boolean isEar() {
      return this.isEar;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   void setEJBCompMBeanName(String compMBeanName) {
      this.compMBeanName = compMBeanName;
   }

   /** @deprecated */
   @Deprecated
   public String getEJBCompMBeanName() {
      return this.compMBeanName;
   }

   public ExceptionInfo getExceptionInfo(Method m, Throwable th) {
      if (!this.ejbDescriptor.isEjb30()) {
         return EJBRuntimeUtils.isAppException(m, th) ? APP_EXCEPTION_DEFAULT : NOT_APP_EXCEPTION_DEFAULT;
      } else {
         Class exClass = th.getClass();
         if (!Error.class.isAssignableFrom(exClass) && !RemoteException.class.isAssignableFrom(exClass)) {
            ExceptionInfo ei = (ExceptionInfo)this.appExeptionsCache.get(exClass);
            if (ei != null) {
               return ei;
            } else {
               String className = exClass.getName();
               String exClassName = this.getAppExceptionClassName(exClass, this.applicationExceptions);
               if (exClassName != null) {
                  ei = (ExceptionInfo)this.applicationExceptions.get(exClassName);
                  boolean inherited = !exClassName.equals(className);
                  if (!inherited || ei.isInherited()) {
                     this.appExeptionsCache.put(exClass, ei);
                     return ei;
                  }
               } else if (this.addApplicationException(exClass)) {
                  return this.getExceptionInfo(m, th);
               }

               ei = !RuntimeException.class.isAssignableFrom(th.getClass()) ? APP_EXCEPTION_DEFAULT : NOT_APP_EXCEPTION_DEFAULT;
               this.appExeptionsCache.put(exClass, ei);
               return ei;
            }
         } else {
            return NOT_APP_EXCEPTION_DEFAULT;
         }
      }
   }

   public boolean isCDIEnabled() {
      return this.isCDIEnabled;
   }

   public boolean isAnyAuthUserRoleDefinedInDD() {
      return this.isAnyAuthUserRoleDefinedInDD;
   }

   private String getAppExceptionClassName(Class exClass, Map applicationExceptions) {
      while(!exClass.equals(Object.class)) {
         String className = exClass.getName();
         if (applicationExceptions.containsKey(className)) {
            return className;
         }

         exClass = exClass.getSuperclass();
      }

      return null;
   }

   private EJBComplianceTextFormatter getFmt() {
      return EJBComplianceTextFormatter.getInstance();
   }

   public String getLogString() {
      return this.myLogString;
   }

   private void initMyLogString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getShortLogString());
      sb.append("(");
      Iterator var2 = this.getBeanInfos().iterator();

      while(var2.hasNext()) {
         BeanInfo bi = (BeanInfo)var2.next();
         sb.append(bi.getEJBName());
         sb.append(", ");
      }

      if (sb.lastIndexOf(", ") != -1) {
         sb.setCharAt(sb.lastIndexOf(","), ')');
      } else {
         sb.append(")");
      }

      this.myLogString = sb.toString();
   }

   private String getShortLogString() {
      return "[" + this.applicationId + ":" + this.applicationName + ", " + this.moduleId + ":" + this.moduleName + ":" + this.moduleURI + "]";
   }

   private static void debug(String s) {
      debugLogger.debug("[DeploymentInfoImpl] " + s);
   }

   private void debugCreateBeanObject(String beanType, String ejbName) {
      debug("EJB container creates a " + beanType + " bean with name " + ejbName + "  belonging to " + this.moduleName + " module.");
   }

   public void setBeanDiscoveryMode(BeanDiscoveryMode beanDiscoveryMode) {
      this.beanDiscoveryMode = beanDiscoveryMode;
   }

   public BeanDiscoveryMode getBeanDiscoveryMode() {
      return this.beanDiscoveryMode;
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      SKIP_ENTITY_PKENHANCE = Boolean.getBoolean("weblogic.ejb.entity.IgnorePKClassOptimization");
      APP_EXCEPTION_DEFAULT = new ExceptionInfoImpl(true, false, false);
      NOT_APP_EXCEPTION_DEFAULT = new ExceptionInfoImpl(false, false, false);
   }
}
