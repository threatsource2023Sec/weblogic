package weblogic.ejb.container.deployer;

import com.bea.wls.redef.RedefiningClassLoader;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.spi.WebModuleIntegrationService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import java.util.zip.ZipEntry;
import javax.ejb.NoSuchEJBException;
import javax.ejb.spi.HandleDelegate;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.AdminModeCompletionBarrier;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.application.PermissionsDescriptorLoader;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.MessageDestinationInfoRegistry;
import weblogic.application.naming.MessageDestinationInfoRegistryImpl;
import weblogic.application.naming.ModuleRegistry;
import weblogic.application.naming.PersistenceUnitRegistryProvider;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.PathUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.ReadConfig;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.ejbc.VersionHelperImpl;
import weblogic.ejb.container.injection.EjbComponentCreatorImpl;
import weblogic.ejb.container.injection.InjectionBasedEjbComponentContributor;
import weblogic.ejb.container.injection.InjectionBasedEjbComponentCreator;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EjbComponentCreator;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.SingletonSessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb.container.manager.SingletonSessionManager;
import weblogic.ejb.container.manager.StatelessManager;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.replication.ReplicatedBeanManager;
import weblogic.ejb.container.timer.EJBTimerManagerFactory;
import weblogic.ejb.spi.EJBC;
import weblogic.ejb.spi.EJBCFactory;
import weblogic.ejb.spi.EJBDeploymentException;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.VersionHelper;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.portable.HandleDelegateImpl;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.InterceptorBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.SecurityPermissionBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnvironmentBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentConcurrentRuntimeMBean;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceException;
import weblogic.security.service.SupplementalPolicyObject;
import weblogic.utils.BadOptionException;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.FileUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.DirectoryClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.jdt.JDTJavaCompilerFactory;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.validation.injection.ValidationManager;
import weblogic.work.WorkManagerCollection;
import weblogic.work.WorkManagerRuntimeMBeanImpl;
import weblogic.work.WorkManagerService;
import weblogic.work.concurrent.runtime.ConcurrentManagedObjectCollection;
import weblogic.work.concurrent.runtime.RuntimeMBeanRegister;

public final class EJBDeployer {
   private static final DebugLogger debugLogger;
   private static final AuthenticatedSubject KERNEL_ID;
   private static final String HANDLE_DELEGATE_BINDING = "HandleDelegate";
   private static final String TIMERSERVICE_BINDING = "TimerService";
   private static final String EJBCONTEXT_BINDING = "EJBContext";
   private static final HandleDelegate HANDLE_DELEGATE;
   private final ApplicationContextInternal appCtx;
   private final String moduleURI;
   private final String moduleName;
   private final String moduleId;
   private final ModuleRegistry mr;
   private final String ejbCompMBeanName;
   private final EJBRuntimeHolder compRTMBean;
   private final GenericClassLoader moduleCL;
   private final EnvironmentManager envManager;
   private final boolean isCDIEnabled;
   private DeploymentInfo dinfo;
   private VersionHelper vHelper;
   private RuntimeHelper helper;
   private String moduleFilePath;
   private String outputDirPath;
   private boolean suppPolicyRegistered;
   private boolean firstActivate = true;
   private final BeanDiscoveryMode beanDiscoveryMode;

   EJBDeployer(String moduleName, String moduleURI, ModuleRegistry mr, GenericClassLoader moduleCL, EJBRuntimeHolder compRTMBean, EnvironmentManager envManager, ApplicationContextInternal appCtx, String moduleId, String ejbCompMBeanName, boolean isCDIEnabled, BeanDiscoveryMode beanDiscoveryMode) {
      this.appCtx = appCtx;
      this.moduleName = moduleName;
      this.moduleId = moduleId;
      this.moduleURI = moduleURI;
      this.moduleCL = moduleCL;
      this.compRTMBean = compRTMBean;
      this.envManager = envManager;
      this.mr = mr;
      this.ejbCompMBeanName = ejbCompMBeanName;
      this.isCDIEnabled = isCDIEnabled;
      this.beanDiscoveryMode = beanDiscoveryMode;
   }

   private List getValidationDescriptorURLs(VirtualJarFile jf) {
      List urls = new LinkedList();
      if (jf != null) {
         URL resource = jf.getResource("META-INF/validation.xml");
         if (resource != null) {
            urls.add(resource);
         }
      }

      return urls;
   }

   private String getLogString() {
      return "[" + this.moduleId + ":" + this.moduleName + ":" + this.moduleURI + "]";
   }

   private boolean isEmpty(Object[] objs) {
      return objs == null || objs.length == 0;
   }

   private boolean hasSameCompModuleCtx(Environment env) {
      return env.getRootContext() == env.getModuleNSContext();
   }

   private Getopt2 makeGetopt2() {
      Getopt2 opts = new Getopt2();
      opts.addFlag("nodeploy", "Do not unpack jar files into the target dir.");
      opts.setUsageArgs("<source jar file> <target directory or jar file>");
      opts.addFlag("idl", "Generate idl for remote interfaces");
      opts.addFlag("idlOverwrite", "Always overwrite existing IDL files");
      opts.addFlag("idlVerbose", "Display verbose information for IDL generation");
      opts.addFlag("idlNoValueTypes", "Do not generate valuetypes and methods/attributes that contain them.");
      opts.markPrivate("idlNoValueTypes");
      opts.addOption("idlDirectory", "dir", "Specify the directory where IDL files will be created (default : current directory)");
      opts.addFlag("iiop", "Generate CORBA stubs");
      opts.addOption("iiopDirectory", "dir", "Specify the directory where IIOP stub files will be written (default : current directory)");
      opts.addFlag("forceGeneration", "Force generation of wrapper classes. Without this flag the classes may not be regenerated if it is determined to be unnecessary.");
      opts.addFlag("convertDDs", "Convert old deployment descriptors to new ones");
      opts.addFlag("disableHotCodeGen", "Generate ejb stub and skel as part of ejbc. Avoid HotCodeGen to have better performance.");
      new CompilerInvoker(opts);
      opts.markPrivate("nowrite");
      opts.markPrivate("commentary");
      opts.markPrivate("nodeploy");
      opts.markPrivate("compilerclass");
      opts.markPrivate("O");
      opts.markPrivate("d");
      opts.markPrivate("J");
      return opts;
   }

   private String makeClasspath(GenericClassLoader cl) {
      String preClasspath = ReadConfig.getJavaCompilerPreClassPath();
      String postClassPath = ReadConfig.getJavaCompilerPostClassPath();
      if (preClasspath == null && postClassPath == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         if (null != preClasspath) {
            sb.append(preClasspath).append(File.pathSeparator);
         }

         sb.append(cl.getClassPath());
         if (null != postClassPath) {
            sb.append(File.pathSeparator).append(postClassPath);
         }

         return sb.toString();
      }
   }

   private void unregisterMBeans() {
      if (this.compRTMBean != null) {
         EJBRuntimeMBean[] var1 = this.compRTMBean.getEJBRuntimes();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            EJBRuntimeMBean mbean = var1[var3];

            try {
               ((RuntimeMBeanDelegate)mbean).unregister();
            } catch (ManagementException var6) {
               EJBLogger.logStackTraceAndMessage("Error unregistering MBean: " + mbean, var6);
            }
         }

         this.compRTMBean.removeAllEJBRuntimeMBeans();
      }

   }

   private File getEJBCompilerCache() {
      return new File(getEjbCompilerCacheDir(), StringUtils.mangle(this.appCtx.getApplicationId() + "_" + this.moduleURI));
   }

   public static File getEjbCompilerCacheDir() {
      String serverName = ManagementService.getRuntimeAccess(KERNEL_ID).getServerName();
      String cacheDir;
      if (PathUtils.APPS_TMP_DIR != null) {
         cacheDir = PathUtils.APPS_TMP_DIR + File.separator + "servers" + File.separator + serverName;
      } else {
         cacheDir = DomainDir.getCacheDirForServer(serverName);
      }

      return new File(cacheDir + File.separator + "EJBCompilerCache");
   }

   private void compileIfNecessary(VirtualJarFile jf, GenericClassLoader cl) throws EJBDeploymentException {
      try {
         this.vHelper = new VersionHelperImpl(this.dinfo, (Getopt2)null);
      } catch (ClassNotFoundException var10) {
         throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var10);
      }

      boolean isForceGenerationEnabled = false;
      if (this.appCtx.getApplicationMBean() == null || !this.appCtx.getApplicationMBean().isInternalApp()) {
         isForceGenerationEnabled = ReadConfig.getForceGeneration();
      }

      Collection needsRecompile = null;
      if (isForceGenerationEnabled) {
         if (debugLogger.isDebugEnabled()) {
            debug("force-generation has been enabled");
         }

         needsRecompile = this.dinfo.getBeanInfos();
      } else {
         needsRecompile = this.vHelper.needsRecompile(jf, true);
      }

      if (!needsRecompile.isEmpty()) {
         File outputDir = this.getEJBCompilerCache();
         Loggable l;
         WLDeploymentException wlde;
         if (outputDir.exists()) {
            if (!outputDir.isDirectory()) {
               l = EJBLogger.logUnableToCreateTempDirLoggable(outputDir.getAbsolutePath());
               wlde = new WLDeploymentException(l.getMessageText());
               throw new EJBDeploymentException(this.moduleURI, this.moduleURI, wlde);
            }

            if (!isForceGenerationEnabled && this.vHelper.hasChecksum(outputDir)) {
               needsRecompile = this.vHelper.needsRecompile(outputDir, true);
            }
         } else if (!outputDir.mkdirs()) {
            l = EJBLogger.logUnableToCreateTempDirLoggable(outputDir.getAbsolutePath());
            wlde = new WLDeploymentException(l.getMessageText());
            throw new EJBDeploymentException(this.moduleURI, this.moduleURI, wlde);
         }

         l = null;

         DirectoryClassFinder dirClassFinder;
         try {
            dirClassFinder = new DirectoryClassFinder(outputDir);
         } catch (IOException var9) {
            throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var9);
         }

         cl.addClassFinderFirst(dirClassFinder);
         if (!needsRecompile.isEmpty()) {
            this.compileEjbs(outputDir, needsRecompile, cl, jf);
         }

         try {
            dirClassFinder.indexFiles();
         } catch (IOException var8) {
            throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var8);
         }
      }

   }

   private boolean perhapsUseDisableHotCodeGen(VirtualJarFile jf) {
      Iterator it = jf.entries();

      do {
         if (!it.hasNext()) {
            return false;
         }
      } while(((ZipEntry)it.next()).getName().indexOf("_WLSkel") == -1);

      return true;
   }

   private void compileEjbs(File outputDir, Collection needsRecompile, GenericClassLoader cl, VirtualJarFile jf) throws EJBDeploymentException {
      FileUtils.remove(outputDir, FileUtils.makeExtensionFilter(".java"));
      Getopt2 opts = this.makeGetopt2();
      String extraOptions = ReadConfig.getExtraEjbcOptions();
      this.outputDirPath = outputDir.getAbsolutePath();
      if (extraOptions != null && extraOptions.trim().length() > 0) {
         try {
            opts.grok(StringUtils.splitCompletely(extraOptions, " "));
         } catch (IllegalArgumentException var10) {
            throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var10);
         }
      }

      try {
         opts.setOption("compiler", ReadConfig.getJavaCompiler());
         opts.setOption("d", outputDir.getPath());
         String classpath = this.makeClasspath(cl);
         if (classpath != null) {
            opts.setOption("classpath", classpath);
         }

         opts.setFlag("nowarn", true);
         opts.setFlag("noexit", true);
         opts.setFlag("keepgenerated", ReadConfig.getKeepGenerated());
         boolean forceGeneration = false;
         if (this.appCtx.getApplicationMBean() == null || !this.appCtx.getApplicationMBean().isInternalApp()) {
            forceGeneration = ReadConfig.getForceGeneration();
         }

         opts.setFlag("forceGeneration", forceGeneration);
         if (this.perhapsUseDisableHotCodeGen(jf)) {
            if (debugLogger.isDebugEnabled()) {
               debug("Setting disableHotCodeGen to true, as the existing jar contained pre-generated skeletons");
            }

            opts.setFlag("disableHotCodeGen", true);
         }
      } catch (BadOptionException var11) {
         throw new AssertionError(var11);
      }

      try {
         if (opts.hasOption("keepgenerated")) {
            EJBLogger.logEJBBeingRecompiledOnServerKeepgenerated(this.moduleURI, outputDir.getAbsolutePath());
         } else {
            EJBLogger.logEJBBeingRecompiledOnServer(this.moduleURI);
         }

         EJBC ejbCompiler = EJBCFactory.createEJBC(opts, outputDir);
         ejbCompiler.compileEJB(cl, jf, this.vHelper, needsRecompile);
      } catch (ErrorCollectionException var9) {
         throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var9);
      }
   }

   private void registerSupplementalPolicyObject(EjbDescriptorBean desc) throws DeploymentException, SecurityServiceException, IOException, XMLStreamException {
      SecurityPermissionBean security = desc.getWeblogicEjbJarBean().getSecurityPermission();
      String securitySpec = null;
      if (security != null) {
         securitySpec = security.getSecurityPermissionSpec();
      }

      String newSecuritySpec = null;
      String[] deploymentPaths = this.getDeploymentPaths();
      if (securitySpec != null) {
         if (this.moduleFilePath != null) {
            StringBuilder filePerm = new StringBuilder("permission java.io.FilePermission \"");
            filePerm.append(deploymentPaths[0]);
            filePerm.append("/-\", \"read\"");
            newSecuritySpec = this.insertPermission(filePerm.toString(), securitySpec);
         } else {
            newSecuritySpec = securitySpec;
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debug("Setting Java EE Sandbox Security: ModuleId: " + this.moduleId + ",  SecuritySpec: '" + newSecuritySpec + "'");
      }

      this.suppPolicyRegistered = SupplementalPolicyObject.registerSEPermissions(KERNEL_ID, deploymentPaths, EarUtils.getPermissions(this.getPermissionsBean()), newSecuritySpec, "weblogic-ejb-jar.xml", "EJB", "EE_EJB");
   }

   private PermissionsBean getPermissionsBean() throws IOException, XMLStreamException {
      if (this.appCtx != null && this.appCtx.isEar()) {
         return this.appCtx.getPermissionsBean();
      } else {
         ModuleContext mc = this.appCtx.getModuleContext(this.moduleId);
         if (mc != null) {
            PermissionsDescriptorLoader desc = new PermissionsDescriptorLoader(mc.getVirtualJarFile(), mc.getConfigDir(), mc.getPlan(), mc.getURI(), this.appCtx.getDescriptorCacheDir());
            return (PermissionsBean)desc.loadDescriptorBean();
         } else {
            return null;
         }
      }
   }

   private void removeSupplementalPolicyObject() {
      if (this.suppPolicyRegistered) {
         if (debugLogger.isDebugEnabled()) {
            debug("Removing Java EE Sandbox Security: ModuleId: " + this.moduleId);
         }

         SupplementalPolicyObject.removePolicies(KERNEL_ID, this.getDeploymentPaths());
         this.suppPolicyRegistered = false;
      }
   }

   private String insertPermission(String permission, String grantBlock) {
      StringBuilder newGrant = new StringBuilder();
      if (grantBlock != null && grantBlock.trim().length() != 0) {
         StringBuilder sb = new StringBuilder(grantBlock);
         int idx = sb.indexOf("}");
         if (idx > 0) {
            sb.insert(idx, permission + ";\n");
         }

         newGrant = sb;
      } else {
         newGrant.append("grant {\n").append(permission).append(";\n};");
      }

      return newGrant.toString();
   }

   private String getEJBName(BeanInfo bi) {
      return bi != null ? bi.getEJBName() : this.moduleFilePath;
   }

   private void registerMessageDestinations() throws DeploymentException {
      MessageDestinationDescriptorBean[] mdds = this.dinfo.getMessageDestinationDescriptors();
      MessageDestinationBean[] mds = this.dinfo.getMessageDestinations();
      if (!this.isEmpty(mdds) || !this.isEmpty(mds)) {
         MessageDestinationInfoRegistry mdir = new MessageDestinationInfoRegistryImpl();
         this.mr.put(MessageDestinationInfoRegistry.class.getName(), mdir);

         try {
            mdir.register(mds, mdds);
         } catch (EnvironmentException var5) {
            throw new DeploymentException(var5);
         }
      }
   }

   private void unregisterMessageDestinations() {
      if (!this.isEmpty(this.dinfo.getMessageDestinationDescriptors()) || !this.isEmpty(this.dinfo.getMessageDestinations())) {
         this.mr.remove(MessageDestinationInfoRegistry.class.getName());
      }

   }

   private EjbComponentCreator initializeComponentCreator(PitchforkContext pc, ModuleExtensionContext extCtx) {
      Object compCreator;
      if (this.isCDIEnabled) {
         InjectionContainer ic = (InjectionContainer)this.mr.get(InjectionContainer.class.getName());
         ModuleContext moduleContext = this.appCtx.getModuleContext(this.moduleId);
         EjbComponentCreatorImpl pitchforkECC = new EjbComponentCreatorImpl(pc, new InjectionBasedEjbComponentContributor(this.dinfo, this.moduleCL, pc, ic, moduleContext, extCtx));
         compCreator = new InjectionBasedEjbComponentCreator(ic, this.moduleId, pitchforkECC);
      } else {
         compCreator = new EjbComponentCreatorImpl(pc, this.dinfo, this.moduleCL);
      }

      ((EjbComponentCreator)compCreator).initialize(this.dinfo, this.moduleCL);
      return (EjbComponentCreator)compCreator;
   }

   private void setupBeanInfos(VirtualJarFile jf) throws EJBDeploymentException {
      BeanInfo bi = null;

      try {
         List validationDescriptorURLs = this.getValidationDescriptorURLs(jf);
         ValidationManager.ValidationBean validationBean = EnvUtils.getDefaultValidationBean(validationDescriptorURLs);
         List cmpManagers = new ArrayList();
         Map bmMap = new HashMap();
         Iterator it = this.dinfo.getBeanInfos().iterator();

         while(it.hasNext()) {
            bi = (BeanInfo)it.next();
            ((BeanInfoImpl)bi).setRuntimeHelper(this.helper);
            if (!(bi instanceof MessageDrivenBeanInfo)) {
               bi.setupBeanManager(this.compRTMBean);
            } else {
               ((MessageDrivenBeanInfo)bi).setEJBComponentRuntime(this.compRTMBean);
            }

            if (bi instanceof EntityBeanInfo) {
               EntityBeanInfo ebi = (EntityBeanInfo)bi;
               BeanManager beanManager = ebi.getBeanManager();
               if (!ebi.getIsBeanManagedPersistence() && ebi.getCMPInfo().uses20CMP()) {
                  cmpManagers.add(beanManager);
                  bmMap.put(ebi.getEJBName(), beanManager);
               }
            }

            Environment env = this.envManager.createEnvironmentFor(bi.getEJBName());
            EJBRuntimeUtils.pushEnvironment(env.getRootContext());

            try {
               bi.prepare(env);
            } finally {
               EJBRuntimeUtils.popEnvironment();
            }

            this.setupEnvironment(bi, env, validationBean, validationDescriptorURLs);
            if (bi instanceof SingletonSessionBeanInfo) {
               ((SingletonSessionBeanInfo)bi).registerSingletonDependencyResolver(this.mr);
            }

            if (bi.isTimerDriven()) {
               EJBTimerManagerFactory.validateTimerConfig(bi);
            }
         }

         this.helper.deployAllPolicies();
         this.helper.deployRoles();
         it = cmpManagers.iterator();

         while(it.hasNext()) {
            BeanManager beanManager = (BeanManager)it.next();
            if (beanManager instanceof BaseEntityManager) {
               ((BaseEntityManager)beanManager).setBMMap(bmMap);
            }
         }

      } catch (WLDeploymentException var15) {
         EJBLogger.logStackTraceAndMessage("Error occurred while setting up bean info to EJB:" + this.getEJBName(bi), var15);
         throw new EJBDeploymentException(this.getEJBName(bi), this.moduleURI, var15);
      } catch (Throwable var16) {
         EJBLogger.logStackTraceAndMessage("Error occurred while setting up bean info to EJB:" + this.getEJBName(bi), var16);
         throw new EJBDeploymentException(this.getEJBName(bi), this.moduleURI, var16);
      }
   }

   private void setupEnvironment(BeanInfo bi, Environment env, ValidationManager.ValidationBean vb, List validationDescriptorURLs) throws WLDeploymentException {
      try {
         AuthenticatedSubject runAsSubject = null;

         try {
            runAsSubject = bi.getRunAsSubject();
         } catch (PrincipalNotFoundException var8) {
         }

         env.contributeEnvEntries(bi.getEnterpriseBeanBean(), bi.getWeblogicEnterpriseBeanBean(), runAsSubject);
         if (!this.hasSameCompModuleCtx(env)) {
            env.bindValidation(vb, validationDescriptorURLs);
         }

         env.getCompContext().rebind("HandleDelegate", HANDLE_DELEGATE);
         if (bi.isEJB30()) {
            env.getCompContext().rebind("EJBContext", EjbContextFactory.getInstance());
            env.getCompContext().rebind("TimerService", TimerServiceFactory.getInstance());
         }

         Iterator var10 = this.dinfo.getInterceptorBeans(bi.getEJBName()).iterator();

         while(var10.hasNext()) {
            InterceptorBean iBean = (InterceptorBean)var10.next();
            env.contributeEnvEntries(iBean, (WeblogicEnvironmentBean)null, runAsSubject);
         }

         env.validateEnvEntries(this.moduleCL);
      } catch (EnvironmentException | NamingException var9) {
         Loggable l = EJBLogger.logFailureWhileCreatingCompEnvLoggable();
         throw new WLDeploymentException(l.getMessageText(), var9);
      }
   }

   String needsRecompile(List implClasses, ClassLoader cl) throws ClassNotFoundException {
      Iterator var3 = implClasses.iterator();

      String className;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         className = (String)var3.next();
      } while(!this.vHelper.needsRecompile(className, cl));

      return className;
   }

   void updateImplClassLoader(String ejbName) throws WLDeploymentException {
      this.dinfo.getBeanInfo(ejbName).updateImplClassLoader();
   }

   void prepare(VirtualJarFile jf, EjbDescriptorBean ejbDesc) throws DeploymentException {
      EJBLogger.logDeploying(this.moduleURI);
      if (this.moduleCL instanceof RedefiningClassLoader) {
         this.setupRedefiningClassLoader((RedefiningClassLoader)this.moduleCL, ejbDesc);
      }

      if (jf != null) {
         this.moduleFilePath = jf.getName();
      }

      WorkManagerCollection wmCollection = this.appCtx.getWorkManagerCollection();
      wmCollection.populate(this.moduleId, ejbDesc.getWeblogicEjbJarBean());
      this.addWorkManagerRuntimes(wmCollection.getWorkManagers(this.moduleId));
      this.appCtx.getConcurrentManagedObjectCollection().populate(this.moduleId, RuntimeMBeanRegister.createEJBRegister((ComponentConcurrentRuntimeMBean)this.compRTMBean, ejbDesc.getWeblogicEjbJarBean()));

      try {
         this.dinfo = new DeploymentInfoImpl(ejbDesc, this.moduleCL, this.moduleName, this.moduleURI, this.moduleId, jf, this.appCtx, this.isCDIEnabled);
         ((DeploymentInfoImpl)this.dinfo).setEJBCompMBeanName(this.ejbCompMBeanName);
         ((DeploymentInfoImpl)this.dinfo).setBeanDiscoveryMode(this.beanDiscoveryMode);
      } catch (Exception var9) {
         EJBLogger.logStackTraceAndMessage("Error creating DeploymentInfoImpl Module: " + this.moduleName, var9);
         throw new EJBDeploymentException(this.moduleFilePath, this.moduleURI, var9);
      }

      Iterator var4 = this.dinfo.getEntityBeanInfos().iterator();

      while(var4.hasNext()) {
         EntityBeanInfo ebi = (EntityBeanInfo)var4.next();
         if (!ebi.getIsBeanManagedPersistence()) {
            try {
               ebi.getCMPInfo().setup(new File(jf.getName()), (Getopt2)null, jf);
            } catch (WLDeploymentException var8) {
               throw new EJBDeploymentException(ebi.getEJBName(), this.moduleURI, var8);
            }
         }
      }

      if (!this.dinfo.getSessionBeanInfos().isEmpty() && ejbDesc.isEjb30()) {
         this.dinfo.getPitchforkContext().getPitchforkUtils().acceptClassLoader(this.moduleCL);
      }

      if (jf != null) {
         this.compileIfNecessary(jf, this.moduleCL);
      }

      try {
         this.helper = new RuntimeHelper(this.dinfo, this.appCtx, KERNEL_ID);
         this.registerSupplementalPolicyObject(ejbDesc);
      } catch (Exception var7) {
         EJBLogger.logStackTraceAndMessage("Error creating RuntimeHelper Module: " + this.moduleURI, var7);
         throw new EJBDeploymentException(this.moduleFilePath, this.moduleURI, var7);
      }

      this.setupBeanInfos(jf);
      if (this.mr != null) {
         this.mr.put(weblogic.ejb.spi.DeploymentInfo.class.getName(), this.dinfo);
      }

      this.registerMessageDestinations();
      this.firstActivate = true;
      EJBLogger.logEJBDeploymentState("PREPARED", this.dinfo.getLogString());
   }

   void activate(EjbDescriptorBean ejbDescriptor, ModuleExtensionContext extCtx) throws EJBDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EJBDeployer] activate");
      }

      JDTJavaCompilerFactory.getInstance().resetCache(this.moduleCL);
      BeanInfo bi = null;

      try {
         EJBCacheDeployment.PreProcessor ejbCacheExt = (EJBCacheDeployment.PreProcessor)this.appCtx.getAppDeploymentExtension(EJBCacheDeployment.PreProcessor.class.getName());
         Map cacheMap = ejbCacheExt != null ? ejbCacheExt.getCacheMap() : null;
         Map queryCacheMap = ejbCacheExt != null ? ejbCacheExt.getQueryCacheMap() : null;
         EjbComponentCreator ecc = this.initializeComponentCreator(this.dinfo.getPitchforkContext(), extCtx);
         Iterator it = this.dinfo.getBeanInfos().iterator();

         Iterator it;
         label749:
         while(it.hasNext()) {
            bi = (BeanInfo)it.next();
            bi.setEjbComponentCreator(ecc);
            Environment env = this.envManager.getEnvironmentFor(bi.getEJBName());
            EJBRuntimeUtils.pushEnvironment(env.getRootContext());

            try {
               if (extCtx instanceof WebModuleIntegrationService && ((WebModuleIntegrationService)extCtx).isWebAppDestroyed()) {
                  AuthenticatedSubject runAsSubject = null;

                  try {
                     runAsSubject = bi.getRunAsSubject();
                  } catch (PrincipalNotFoundException var29) {
                  }

                  env.contributeEnvEntries(bi.getEnterpriseBeanBean(), bi.getWeblogicEnterpriseBeanBean(), runAsSubject);
                  if (bi.isEJB30()) {
                     env.getCompContext().rebind("EJBContext", EjbContextFactory.getInstance());
                     env.getCompContext().rebind("TimerService", TimerServiceFactory.getInstance());
                  }

                  ((BeanInfoImpl)bi).setEnvBuilder(env);
                  if (bi instanceof ClientDrivenBeanInfoImpl) {
                     ((ClientDrivenBeanInfoImpl)bi).resetJndiBinder();
                  }

                  bi.clearInjectors();
               }

               bi.activate(cacheMap, queryCacheMap);
            } finally {
               EJBRuntimeUtils.popEnvironment();
            }

            if (bi instanceof StatefulSessionBeanInfo) {
               ((StatefulSessionBeanInfo)bi).setPersistenceUnitRegistry(this.getPURegProvider(this.mr).getPersistenceUnitRegistry());
            }

            if (bi instanceof MessageDrivenBeanInfo) {
               EJBLogger.logDeployedMDB(bi.getEJBName());
            } else if (bi instanceof ClientDrivenBeanInfo) {
               EJBLogger.logDeployedWithEJBName(bi.getDisplayName());
               ClientDrivenBeanInfo cbi = (ClientDrivenBeanInfo)bi;
               String jndiName = cbi.getJNDINameAsString();
               if (jndiName != null && !cbi.getGeneratedJndiNameForHome().equals(jndiName) && cbi.getJndiBinder().isNameBound(jndiName)) {
                  EJBLogger.logDeployedWithJNDIName(jndiName);
               }

               String className;
               String name;
               if (!cbi.getPortableJNDINames().isEmpty()) {
                  className = cbi.getHomeInterfaceName() == null ? cbi.getEJBName() : cbi.getHomeInterfaceName();
                  it = cbi.getPortableJNDINames().iterator();

                  while(it.hasNext()) {
                     name = (String)it.next();
                     if (cbi.getJndiBinder().isNameBound(name)) {
                        EJBLogger.logJNDINamesMap(className, name);
                     }
                  }
               }

               jndiName = cbi.getLocalJNDINameAsString();
               if (jndiName != null && cbi.getJndiBinder().isNameBound(jndiName)) {
                  EJBLogger.logDeployedWithJNDIName(cbi.getLocalJNDINameAsString());
               }

               if (!cbi.getLocalPortableJNDINames().isEmpty()) {
                  className = cbi.getLocalHomeInterfaceName() == null ? cbi.getEJBName() : cbi.getLocalHomeInterfaceName();
                  it = cbi.getLocalPortableJNDINames().iterator();

                  while(it.hasNext()) {
                     name = (String)it.next();
                     if (cbi.getJndiBinder().isNameBound(name)) {
                        EJBLogger.logJNDINamesMap(className, name);
                     }
                  }
               }

               if (bi instanceof SessionBeanInfo) {
                  SessionBeanInfo sbi = (SessionBeanInfo)bi;
                  if (!sbi.getBusinessJNDINames().isEmpty()) {
                     it = sbi.getBusinessJNDINames().entrySet().iterator();

                     while(true) {
                        Map.Entry e;
                        do {
                           if (!it.hasNext()) {
                              continue label749;
                           }

                           e = (Map.Entry)it.next();
                        } while(e.getValue() == null);

                        Iterator var15 = ((Set)e.getValue()).iterator();

                        while(var15.hasNext()) {
                           String name = (String)var15.next();
                           if (sbi.getJndiBinder().isNameBound(name)) {
                              EJBLogger.logJNDINamesMap(((Class)e.getKey()).getCanonicalName(), name);
                           }
                        }
                     }
                  }
               }
            }
         }

         if (this.firstActivate) {
            it = this.dinfo.getEntityBeanInfos().iterator();

            EntityBeanInfo ebi;
            CMPInfo ci;
            while(it.hasNext()) {
               ebi = (EntityBeanInfo)it.next();
               ci = ebi.getCMPInfo();
               if (ci != null) {
                  ci.setupParentBeanManagers();
                  ci.setupMNBeanManagers();
               }
            }

            it = this.dinfo.getEntityBeanInfos().iterator();

            while(it.hasNext()) {
               ebi = (EntityBeanInfo)it.next();
               ci = ebi.getCMPInfo();
               if (ci != null) {
                  ci.setCycleExists();
               }
            }

            this.firstActivate = false;
         }

         this.helper.activate();
         HashSet envs = new HashSet();
         PersistenceUnitRegistryProvider purp = this.mr != null ? this.getPURegProvider(this.mr) : null;
         Iterator it = this.dinfo.getBeanInfos().iterator();

         while(it.hasNext()) {
            bi = (BeanInfo)it.next();
            envs.add(this.envManager.getEnvironmentFor(bi.getEJBName()));
         }

         it = envs.iterator();

         while(it.hasNext()) {
            Environment env = (Environment)it.next();
            EJBRuntimeUtils.pushEnvironment(env.getRootContext());

            try {
               env.bindEnvEntriesFromDDs(this.moduleCL, purp);
            } finally {
               EJBRuntimeUtils.popEnvironment();
            }
         }

         ConcurrentManagedObjectCollection cmoObjs = this.appCtx.getConcurrentManagedObjectCollection();
         Iterator it = this.dinfo.getBeanInfos().iterator();

         while(it.hasNext()) {
            bi = (BeanInfo)it.next();
            cmoObjs.updateContexts(this.moduleId, bi.getEJBName(), bi.getClassLoader());
         }

         it = this.dinfo.getBeanInfos().iterator();

         while(it.hasNext()) {
            BeanInfo beanInfo = (BeanInfo)it.next();
            if (beanInfo.isClientDriven()) {
               beanInfo.getBeanManager().setIsDeployed(true);
            }
         }

         Map wlBeans = new HashMap();
         WeblogicEjbJarBean wlJar = ejbDescriptor.getWeblogicEjbJarBean();
         WeblogicEnterpriseBeanBean[] var46 = wlJar.getWeblogicEnterpriseBeans();
         int var48 = var46.length;

         for(int var49 = 0; var49 < var48; ++var49) {
            WeblogicEnterpriseBeanBean webb = var46[var49];
            wlBeans.put(webb.getEjbName(), webb);
         }

         it = this.dinfo.getBeanInfos().iterator();

         while(it.hasNext()) {
            bi = (BeanInfo)it.next();
            bi.addBeanUpdateListener((WeblogicEnterpriseBeanBean)wlBeans.get(bi.getEJBName()), ejbDescriptor, this.appCtx);
         }

         this.appCtx.getWorkManagerCollection().activateModuleEntries(this.moduleId);
         EJBLogger.logEJBDeploymentState("ACTIVATED", this.dinfo.getLogString());
      } catch (WLDeploymentException var31) {
         EJBLogger.logStackTraceAndMessage("Error activating EJB: " + this.getEJBName(bi), var31);
         throw new EJBDeploymentException(this.getEJBName(bi), this.moduleURI, var31);
      } catch (Throwable var32) {
         EJBLogger.logStackTraceAndMessage("Error activating EJB: " + this.getEJBName(bi), var32);
         throw new EJBDeploymentException(this.getEJBName(bi), this.moduleURI, var32);
      }
   }

   private PersistenceUnitRegistryProvider getPURegProvider(ModuleRegistry reg) {
      return (PersistenceUnitRegistryProvider)reg.get(PersistenceUnitRegistryProvider.class.getName());
   }

   private void fillInterfaceInfo(EjbDescriptorBean ejbDesc, Set intfs) {
      EnterpriseBeansBean beans = ejbDesc.getEjbJarBean().getEnterpriseBeans();
      SessionBeanBean[] var4 = beans.getSessions();
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         SessionBeanBean sbb = var4[var6];
         this.fillInterfaceInfo((EnterpriseBeanBean)sbb, intfs);
         String[] remoteBusinessInterfaces = sbb.getBusinessRemotes();
         String[] localBusinessInterfaces;
         int var11;
         if (remoteBusinessInterfaces != null) {
            localBusinessInterfaces = remoteBusinessInterfaces;
            int var10 = remoteBusinessInterfaces.length;

            for(var11 = 0; var11 < var10; ++var11) {
               String rbi = localBusinessInterfaces[var11];
               intfs.add(rbi);
            }
         }

         if (sbb.getHome() != null) {
            intfs.add(sbb.getHome());
            intfs.add(sbb.getRemote());
         }

         localBusinessInterfaces = sbb.getBusinessLocals();
         if (localBusinessInterfaces != null) {
            String[] var18 = localBusinessInterfaces;
            var11 = localBusinessInterfaces.length;

            for(int var19 = 0; var19 < var11; ++var19) {
               String lbi = var18[var19];
               intfs.add(lbi);
            }
         }

         if (sbb.getLocalHome() != null) {
            intfs.add(sbb.getLocalHome());
            intfs.add(sbb.getLocal());
         }
      }

      EntityBeanBean[] var14 = beans.getEntities();
      var5 = var14.length;

      for(var6 = 0; var6 < var5; ++var6) {
         EntityBeanBean ebb = var14[var6];
         this.fillInterfaceInfo((EnterpriseBeanBean)ebb, intfs);
         if (ebb.getHome() != null) {
            intfs.add(ebb.getHome());
         }

         if (ebb.getLocal() != null) {
            intfs.add(ebb.getLocal());
         }

         if (ebb.getLocalHome() != null) {
            intfs.add(ebb.getLocalHome());
         }

         if (ebb.getRemote() != null) {
            intfs.add(ebb.getRemote());
         }

         if (ebb.getPrimKeyClass() != null) {
            intfs.add(ebb.getPrimKeyClass());
         }
      }

      MessageDrivenBeanBean[] var15 = beans.getMessageDrivens();
      var5 = var15.length;

      for(var6 = 0; var6 < var5; ++var6) {
         MessageDrivenBeanBean mdbb = var15[var6];
         this.fillInterfaceInfo((EnterpriseBeanBean)mdbb, intfs);
      }

   }

   private void fillInterfaceInfo(EnterpriseBeanBean bean, Set intfs) {
      if (bean.getEjbClass() != null) {
         intfs.add(bean.getEjbClass());

         try {
            ClassLoader loader = new GenericClassLoader(this.moduleCL.getClassFinder(), this.moduleCL.getParent());
            Class clz = loader.loadClass(bean.getEjbClass());

            while((clz = clz.getSuperclass()) != Object.class) {
               intfs.add(clz.getName());
            }
         } catch (ClassNotFoundException var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[EJBDeployer] Failed to load " + bean.getEjbClass() + ". ", var8);
            }
         }
      }

      EjbRefBean[] ejbRefs = bean.getEjbRefs();
      if (ejbRefs != null) {
         EjbRefBean[] var10 = ejbRefs;
         int var5 = ejbRefs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EjbRefBean ejbRef = var10[var6];
            if (ejbRef.getHome() != null) {
               intfs.add(ejbRef.getHome());
            }

            if (ejbRef.getRemote() != null) {
               intfs.add(ejbRef.getRemote());
            }
         }
      }

   }

   private void setupRedefiningClassLoader(RedefiningClassLoader cl, EjbDescriptorBean ejbDescBean) {
      Set ejbInterfaces = new HashSet();
      this.fillInterfaceInfo((EjbDescriptorBean)ejbDescBean, ejbInterfaces);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("ClassLoader " + cl + " EJB Interfaces " + ejbInterfaces);
      }

      if (!ejbInterfaces.isEmpty()) {
         cl.setExcludedClasses(ejbInterfaces);
      }

   }

   void start() throws EJBDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EJBDeployer] start");
      }

      Iterator var1 = this.dinfo.getBeanInfos().iterator();

      while(var1.hasNext()) {
         BeanInfo bi = (BeanInfo)var1.next();

         try {
            bi.perhapsStartTimerManager();
         } catch (Exception var8) {
            throw new EJBDeploymentException(bi.getEJBName(), this.moduleURI, var8);
         }
      }

      var1 = this.dinfo.getSessionBeanInfos().iterator();

      while(var1.hasNext()) {
         SessionBeanInfo sbi = (SessionBeanInfo)var1.next();
         if (sbi.isStateless()) {
            try {
               ((StatelessManager)sbi.getBeanManager()).initializePool();
            } catch (Exception var7) {
               EJBLogger.logErrorCreatingFreepool(sbi.getDisplayName(), var7);
            }
         } else if (sbi.isSingleton()) {
            try {
               ((SingletonSessionManager)sbi.getBeanManager()).perhapsInit();
            } catch (NoSuchEJBException var6) {
               throw new EJBDeploymentException(sbi.getEJBName(), this.moduleURI, var6);
            }
         }
      }

      var1 = this.dinfo.getEntityBeanInfos().iterator();

      EntityBeanInfo ebi;
      while(var1.hasNext()) {
         ebi = (EntityBeanInfo)var1.next();
         BeanManager entityManager = ebi.getBeanManager();
         if (entityManager instanceof BaseEntityManager) {
            try {
               ((BaseEntityManager)entityManager).initializePool();
            } catch (Exception var5) {
               EJBLogger.logErrorCreatingFreepool(ebi.getDisplayName(), var5);
            }
         }
      }

      if (!this.dinfo.getMessageDrivenBeanInfos().isEmpty()) {
         MDBService mds = MDBServiceActivator.getInstance().getMDBService();
         if (!this.startMdbsWithApplication() && mds != null && mds.register(this.dinfo.getMessageDrivenBeanInfos())) {
            if (debugLogger.isDebugEnabled()) {
               debug("MDBs added to MDBService for start.");
            }
         } else {
            try {
               Iterator var14 = this.dinfo.getMessageDrivenBeanInfos().iterator();

               while(var14.hasNext()) {
                  MessageDrivenBeanInfo bi = (MessageDrivenBeanInfo)var14.next();
                  bi.deployMessageDrivenBeans();
               }
            } catch (WLDeploymentException var9) {
               throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var9);
            } catch (Throwable var10) {
               throw new EJBDeploymentException(this.moduleURI, this.moduleURI, var10);
            }
         }
      }

      if (!this.dinfo.isDynamicQueriesEnabled()) {
         var1 = this.dinfo.getEntityBeanInfos().iterator();

         while(var1.hasNext()) {
            ebi = (EntityBeanInfo)var1.next();
            PersistenceManager pm = ebi.getPersistenceManager();
            if (pm != null && pm instanceof RDBMSPersistenceManager) {
               ((RDBMSPersistenceManager)pm).cleanup();
            }
         }
      }

      EJBLogger.logEJBDeploymentState("STARTED", this.dinfo.getLogString());
   }

   void unprepare() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EJBDeployer] unprepare");
      }

      this.appCtx.getWorkManagerCollection().removeModuleEntries(this.moduleId);
      this.appCtx.getConcurrentManagedObjectCollection().removeModuleEntries(this.moduleId);
      if (this.mr != null) {
         this.mr.remove(weblogic.ejb.spi.DeploymentInfo.class.getName(), this.dinfo);
      }

      if (this.dinfo != null) {
         Iterator var1 = this.dinfo.getBeanInfos().iterator();

         while(var1.hasNext()) {
            BeanInfo bi = (BeanInfo)var1.next();
            bi.unprepare();
         }
      }

      this.unregisterMBeans();
      this.removeSupplementalPolicyObject();
      this.envManager.cleanup();
      if (this.dinfo != null) {
         if (this.dinfo.getPitchforkContext() != null) {
            this.dinfo.getPitchforkContext().getPitchforkUtils().clearClassLoader(this.appCtx.getAppClassLoader());
         }

         this.unregisterMessageDestinations();
      }

      EJBLogger.logEJBDeploymentState("UNPREPARED", this.dinfo == null ? this.getLogString() : this.dinfo.getLogString());
   }

   private boolean startMdbsWithApplication() {
      WeblogicApplicationBean wldd = this.appCtx.getWLApplicationDD();
      return wldd != null && wldd.getEjb() != null && wldd.getEjb().isStartMdbsWithApplication();
   }

   private void addWorkManagerRuntimes(List workManagers) throws DeploymentException {
      try {
         ApplicationRuntimeMBean appRuntime = this.appCtx.getRuntime();
         Iterator var3 = workManagers.iterator();

         while(var3.hasNext()) {
            WorkManagerService wms = (WorkManagerService)var3.next();
            WorkManagerRuntimeMBean wmRuntime = WorkManagerRuntimeMBeanImpl.getWorkManagerRuntime(wms.getDelegate(), appRuntime, this.compRTMBean);
            if (wmRuntime != null) {
               this.compRTMBean.addWorkManagerRuntime(wmRuntime);
            }
         }

      } catch (ManagementException var6) {
         throw new DeploymentException("Error creating WorkManagerRuntimeMBean", var6);
      }
   }

   void deactivate(EjbDescriptorBean ejbDescriptor) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EJBDeployer] deactivate");
      }

      if (this.helper != null) {
         this.helper.unDeployRoles();
         this.helper.deactivate();
      }

      Iterator var2 = this.dinfo.getMessageDrivenBeanInfos().iterator();

      while(var2.hasNext()) {
         MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)var2.next();
         mdbi.undeployManagers();
      }

      var2 = this.dinfo.getBeanInfos().iterator();

      while(var2.hasNext()) {
         BeanInfo bi = (BeanInfo)var2.next();
         if (bi.isClientDriven()) {
            BeanManager bm = bi.getBeanManager();
            bm.setIsDeployed(false);
            bm.undeploy();
         }
      }

      HashSet envs = new HashSet();
      Iterator var11 = this.dinfo.getBeanInfos().iterator();

      while(var11.hasNext()) {
         BeanInfo bi = (BeanInfo)var11.next();
         envs.add(this.envManager.getEnvironmentFor(bi.getEJBName()));
      }

      var11 = envs.iterator();

      while(var11.hasNext()) {
         Environment env = (Environment)var11.next();
         if (env != null) {
            env.unbindEnvEntries();
            if (!this.hasSameCompModuleCtx(env)) {
               env.unbindValidation();
            }
         }
      }

      var11 = this.dinfo.getMessageDrivenBeanInfos().iterator();

      while(var11.hasNext()) {
         MessageDrivenBeanInfo bi = (MessageDrivenBeanInfo)var11.next();
         if (bi.getIsJMSBased()) {
            bi.unRegister();
         }
      }

      this.unregisterMBeans();
      Map wlBeans = new HashMap();
      WeblogicEjbJarBean wlJar = ejbDescriptor.getWeblogicEjbJarBean();
      WeblogicEnterpriseBeanBean[] var5 = wlJar.getWeblogicEnterpriseBeans();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WeblogicEnterpriseBeanBean webb = var5[var7];
         wlBeans.put(webb.getEjbName(), webb);
      }

      Iterator var17 = this.dinfo.getBeanInfos().iterator();

      while(var17.hasNext()) {
         BeanInfo bi = (BeanInfo)var17.next();
         bi.removeBeanUpdateListener((WeblogicEnterpriseBeanBean)wlBeans.get(bi.getEJBName()), ejbDescriptor, this.appCtx);
      }

      this.appCtx.getWorkManagerCollection().releaseModuleTasks(this.moduleName);
      EJBLogger.logEJBDeploymentState("DEACTIVATED", this.dinfo.getLogString());
   }

   void remove() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EJBDeployer] remove");
      }

      JDTJavaCompilerFactory.getInstance().resetCache(this.moduleCL);
      if (this.dinfo != null) {
         Iterator var1 = this.dinfo.getBeanInfos().iterator();

         while(var1.hasNext()) {
            BeanInfo bi = (BeanInfo)var1.next();
            if (bi instanceof MessageDrivenBeanInfo) {
               ((MessageDrivenBeanInfo)bi).deleteDurableSubscribers();
            } else if (bi.isClientDriven() && bi instanceof StatefulSessionBeanInfo) {
               BeanManager bm = bi.getBeanManager();
               if (bm instanceof ReplicatedBeanManager) {
                  ((ReplicatedBeanManager)bm).unregisterROIDs();
               }
            }

            if (bi.isTimerDriven()) {
               EJBTimerManagerFactory.removeAllTimers(bi);
            }
         }
      }

      this.deleteEJBCompileCacheFiles();
      EJBLogger.logEJBDeploymentState("REMOVED", this.dinfo == null ? this.getLogString() : this.dinfo.getLogString());
   }

   private void deleteEJBCompileCacheFiles() {
      File outputDir = this.getEJBCompilerCache();
      FileUtils.remove(outputDir);
   }

   void adminToProduction() {
      this.adminToProduction(false);
   }

   void adminBackToProduction() {
      this.adminToProduction(true);
   }

   private void adminToProduction(boolean includeNonJMSBased) {
      Iterator var2 = this.dinfo.getMessageDrivenBeanInfos().iterator();

      while(true) {
         MessageDrivenBeanInfo mdbi;
         do {
            if (!var2.hasNext()) {
               EJBLogger.logEJBDeploymentState("ADMIN-TO-PRODUCTION", this.dinfo.getLogString());
               return;
            }

            mdbi = (MessageDrivenBeanInfo)var2.next();
         } while(!includeNonJMSBased && !mdbi.getIsJMSBased());

         mdbi.resumeManagers();
      }
   }

   void gracefulProductionToAdmin(AdminModeCompletionBarrier barrier) {
      this.forceProductionToAdmin();
   }

   void forceProductionToAdmin() {
      Iterator var1 = this.dinfo.getMessageDrivenBeanInfos().iterator();

      while(var1.hasNext()) {
         MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)var1.next();
         mdbi.suspendManagers();
      }

      EJBLogger.logEJBDeploymentState("PRODUCTION-TO-ADMIN", this.dinfo.getLogString());
   }

   private String[] getDeploymentPaths() {
      String[] ejbDeployPaths = new String[]{this.getCanonicalPath(this.moduleFilePath), null};
      if (this.outputDirPath != null) {
         ejbDeployPaths[1] = this.getCanonicalPath(this.outputDirPath);
      } else {
         ejbDeployPaths[1] = this.getEJBCompilerCache().getAbsolutePath();
      }

      for(int i = 0; i < ejbDeployPaths.length; ++i) {
         if (ejbDeployPaths[i] != null && File.separatorChar == '\\') {
            ejbDeployPaths[i] = ejbDeployPaths[i].replace(File.separatorChar, '/');
         }
      }

      return ejbDeployPaths;
   }

   private String getCanonicalPath(String fileName) {
      return fileName != null ? (new File(fileName)).getAbsolutePath() : null;
   }

   private static void debug(String s) {
      debugLogger.debug("[EJBDeployer] " + s);
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      HANDLE_DELEGATE = new HandleDelegateImpl();
   }
}
