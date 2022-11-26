package weblogic.j2eeclient;

import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.ModuleContainerIntegrationService;
import com.oracle.injection.provider.weld.WeldInjectionContainer;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.server.Bootstrap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyStore;
import java.security.PermissionCollection;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.utils.EarUtils;
import weblogic.common.ResourceException;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBean;
import weblogic.j2ee.injection.PitchforkContext;
import weblogic.j2eeclient.jndi.Environment;
import weblogic.j2eeclient.security.SecurityPolicyHelper;
import weblogic.j2eeclient.tools.JarFileInjectionArchive;
import weblogic.kernel.KernelStatus;
import weblogic.persistence.spi.AppClientPersistenceProviderResolver;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.Security;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class AppClientContainer {
   private static AppClientTextTextFormatter TEXT_FORMATTER = AppClientTextTextFormatter.getInstance();
   private final File clientJar;
   private final String[] userCodeArgs;
   private final File configDir;
   private final DeploymentPlanBean plan;
   private String moduleName = null;
   private final File[] applicationLibraries;
   private static final boolean runningInsideWebStart = Boolean.getBoolean("weblogic.j2ee.client.isWebStart");
   private static final boolean DEBUG = Boolean.getBoolean("weblogic.debug.DebugJ2EEClient");
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppClient");
   private ApplicationClientBean stdDD;
   private Class mainClass;
   private String applicationName;
   private final String url;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public AppClientContainer(File clientJar, String url, String[] argv, String configDirName, String planFileName, String moduleName, String applicationName, File[] applicationLibs) throws Exception {
      KernelStatus.setIsJ2eeClient(true);
      this.clientJar = clientJar;
      this.url = url;
      this.userCodeArgs = argv;
      this.configDir = configDirName == null ? null : new File(configDirName);
      this.plan = planFileName == null ? null : (new DeploymentPlanDescriptorLoader(new File(planFileName))).getDeploymentPlanBean();
      this.moduleName = moduleName;
      this.applicationLibraries = applicationLibs;
      this.applicationName = applicationName;
      Environment.init(url);
   }

   public void run() throws Exception {
      try {
         this.runInternal();
      } catch (InvocationTargetException var2) {
         var2.getTargetException().printStackTrace();
      } catch (Throwable var3) {
         if (debugLogger.isDebugEnabled()) {
            var3.printStackTrace();
         } else {
            System.err.println(var3.getMessage());
         }
      }

   }

   public void runInternal() throws Exception {
      GenericClassLoader gcl = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
      if (DEBUG) {
         Debug.say("Starting");
      }

      if (DEBUG && runningInsideWebStart) {
         Debug.say("JavaWebStart");
      }

      ClassFinder cf = this.defaultClassFinder();
      gcl.addClassFinder(cf);
      this.addClassFinderForLibraries(gcl, this.applicationLibraries);
      Thread.currentThread().setContextClassLoader(gcl);
      String mainClassName = this.readMainClassName(gcl);
      this.mainClass = gcl.loadClass(mainClassName);
      AppClientPersistenceUnitRegistry appclpu = new AppClientPersistenceUnitRegistry(this.clientJar, gcl, this.moduleName, this.configDir, this.plan);
      GenericClassLoader cl = new GenericClassLoader(cf);
      ApplicationClientDescriptor acd = new ApplicationClientDescriptor(gcl, this.configDir, this.plan, this.moduleName);
      if (System.getSecurityManager() != null) {
         this.registerClientCodeBasePermissions(this.clientJar, this.applicationLibraries, acd);
      }

      this.stdDD = ApplicationClientUtils.getAnnotationProcessedDescriptor(gcl, acd, this.mainClass);
      WeblogicApplicationClientBean wlDD = acd.getWeblogicApplicationClientBean();
      this.computeModuleName(this.stdDD.getJavaEEModuleName());
      this.computeApplicationName(wlDD);
      this.runUserApplicationCode(gcl, mainClassName, appclpu, cl, wlDD);
   }

   private void initializeCDI() {
      WeldInjectionContainer cdi = new WeldInjectionContainer();

      try {
         ModuleContainerIntegrationService integrationService = new ModuleContainerIntegrationService(TransactionHelper.getTransactionHelper());
         cdi.setIntegrationService(integrationService);
         cdi.addInjectionArchive(new JarFileInjectionArchive(Thread.currentThread().getContextClassLoader(), this.clientJar));
         cdi.initialize();
         cdi.deploy();
         cdi.start();
      } catch (InjectionException var3) {
         throw new RuntimeException(var3);
      } catch (Throwable var4) {
      }

   }

   private void runUserApplicationCode(GenericClassLoader gcl, String mainClassName, AppClientPersistenceUnitRegistry appclpu, GenericClassLoader cl, WeblogicApplicationClientBean wlDD) throws Exception {
      try {
         Environment jndiEnvironment = Environment.bootstrap(this.applicationName);
         Context localRootCtx = jndiEnvironment.getLocalRootCtx();
         Context remoteRootCtx = jndiEnvironment.getRemoteRootCtx();
         Binder binder = new Binder(this.clientJar.getName(), this.url, this.applicationName, this.moduleName, this.stdDD, wlDD, localRootCtx, remoteRootCtx);
         binder.bindToEnvironment(cl, appclpu);
         this.loadCertificate(System.getProperty("javax.net.ssl.keyStore"), System.getProperty("javax.net.ssl.keyStorePassword"));
         this.performInjections(gcl, mainClassName, cl);
         (new AppClientPersistenceProviderResolver()).postConstruct();
         this.initializeCDI();
         UserCodeInvoker invoker = new UserCodeInvoker(this.clientJar, this.userCodeArgs, binder.getDataSourceBinder(), binder.getMgedBeanProcessor());
         invoker.invokeUserCode(gcl, mainClassName, this.stdDD.getPostConstructs(), this.stdDD.getPreDestroys());
      } catch (Exception var11) {
         if (!DEBUG) {
            throw var11;
         }

         var11.printStackTrace();
         System.err.println("Dumping Client Environment:");
         System.err.println(Environment.instance());
      }

   }

   private void addClassFinderForLibraries(GenericClassLoader gcl, File[] libraries) throws IOException {
      if (libraries != null) {
         for(int count = 0; count < libraries.length; ++count) {
            gcl.addClassFinder(new JarClassFinder(libraries[count]));
         }
      }

   }

   private ClassFinder defaultClassFinder() throws IOException, FileNotFoundException {
      ClassFinder cf = NullClassFinder.NULL_FINDER;
      if (!runningInsideWebStart) {
         try {
            cf = new JarClassFinder(this.clientJar);
         } catch (final FileNotFoundException var4) {
            String msg = TEXT_FORMATTER.fileNotFound(this.clientJar.getAbsolutePath());
            throw new FileNotFoundException(msg) {
               {
                  this.setStackTrace(var4.getStackTrace());
               }
            };
         }
      }

      return (ClassFinder)cf;
   }

   private void performInjections(GenericClassLoader gcl, String mainClassName, GenericClassLoader cl) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, Exception, IOException, ResourceException, NamingException {
      PitchforkContext pitchforkContext = new PitchforkContext((String)null);
      Bootstrap bootstrap = (Bootstrap)Class.forName("com.oracle.pitchfork.spi.WLSBootstrap").getConstructor(ClassLoader.class, String.class, String.class, Boolean.TYPE).newInstance(cl, null, null, Boolean.FALSE);
      bootstrap.deploy(this.getComponentContributor(pitchforkContext));
   }

   private ComponentContributor getComponentContributor(PitchforkContext pitchforkContext) throws Exception {
      Class cl = Class.forName("weblogic.j2ee.injection.J2eeClientComponentContributor");
      Constructor cons = cl.getDeclaredConstructor(this.mainClass.getClass(), ApplicationClientBean.class, PitchforkContext.class);
      return (ComponentContributor)cons.newInstance(this.mainClass, this.stdDD, pitchforkContext);
   }

   private String computeApplicationName(WeblogicApplicationClientBean wlDD) throws NamingException, MalformedURLException {
      if (this.applicationName == null || this.applicationName.isEmpty()) {
         this.applicationName = wlDD.getServerApplicationName();
      }

      if (this.applicationName == null || this.applicationName.isEmpty()) {
         int protocolIdx = this.url.indexOf("://") + 3;
         int urlEndIdx = this.url.indexOf(47, protocolIdx);
         if (urlEndIdx != -1 && this.url.length() > urlEndIdx) {
            this.applicationName = this.url.substring(urlEndIdx);
         }
      }

      if (this.applicationName == null || this.applicationName.isEmpty()) {
         Debug.say("Unable to find an application name to find context");
      }

      return this.applicationName;
   }

   private void computeModuleName(String javaEEModuleName) {
      if (this.moduleName == null) {
         this.moduleName = javaEEModuleName;
      }

      if (this.moduleName == null) {
         String jarName = this.clientJar.getName();
         if (!jarName.toLowerCase().endsWith(".jar")) {
            throw new AssertionError("Invalid client jar name: " + this.clientJar);
         }

         this.moduleName = jarName.substring(0, jarName.length() - 4);
      }

   }

   private void loadCertificate(String keystoreFile, String keyPass) throws Exception {
      if (keystoreFile != null && keyPass != null) {
         KeyStore ks = KeyStore.getInstance("JKS");
         FileInputStream istream = new FileInputStream(keystoreFile);
         char[] passphrase = keyPass.toCharArray();
         ks.load(new FileInputStream(keystoreFile), passphrase);
         istream.close();
         String alias = null;
         Enumeration aliases = ks.aliases();
         if (aliases.hasMoreElements()) {
            alias = (String)aliases.nextElement();
         }

         if (alias == null) {
            throw new SecurityException(TEXT_FORMATTER.keystoreHasNoAlias());
         }

         Certificate[] certificates = ks.getCertificateChain(alias);
         if (certificates == null) {
            throw new SecurityException(TEXT_FORMATTER.noCertificateForAlias(alias));
         }

         Key key = ks.getKey(alias, passphrase);
         if (key == null) {
            throw new SecurityException(TEXT_FORMATTER.noKeyFoundForAlias(alias));
         }

         Security.loadLocalIdentity(certificates, (PrivateKey)key);
      }

   }

   private String readMainClassName(ClassLoader cl) throws IOException {
      String rtn = null;
      if (runningInsideWebStart) {
         Enumeration e = cl.getResources("META-INF/MANIFEST.MF");

         while(e.hasMoreElements()) {
            URL url = (URL)e.nextElement();
            InputStream is = url.openStream();
            Manifest mf = new Manifest(is);
            rtn = (String)mf.getMainAttributes().get(Name.MAIN_CLASS);
            if (rtn != null && rtn.length() > 0) {
               break;
            }
         }
      } else {
         VirtualJarFile jf = null;

         try {
            jf = VirtualJarFactory.createVirtualJar(this.clientJar);
            Manifest m = jf.getManifest();
            if (m == null) {
               throw new IOException(TEXT_FORMATTER.clientJarMissingManifest(this.clientJar.getPath()));
            }

            rtn = (String)m.getMainAttributes().get(Name.MAIN_CLASS);
         } finally {
            if (jf != null) {
               try {
                  jf.close();
               } catch (Exception var12) {
               }
            }

         }
      }

      if (rtn == null) {
         throw new IOException(AppClientLogger.missingMainAttributeLoggable(Name.MAIN_CLASS.toString(), this.clientJar.getPath()).getMessageText());
      } else {
         if (DEBUG) {
            Debug.say("Main Class: " + rtn);
         }

         return rtn;
      }
   }

   private void registerClientCodeBasePermissions(File clientJar, File[] appLibs, final ApplicationClientDescriptor acDescriptor) {
      List fileList = new ArrayList();
      if (clientJar != null) {
         fileList.add(clientJar);
      }

      final File[] files;
      if (appLibs != null && appLibs.length > 0) {
         files = appLibs;
         int var6 = appLibs.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File f = files[var7];
            fileList.add(f);
         }
      }

      if (fileList.size() > 0) {
         files = (File[])fileList.toArray(new File[fileList.size()]);
         SecurityManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Void run() {
               SecurityPolicyHelper.registerSecurityPermissions(files, AppClientContainer.this.getPermissions(acDescriptor));
               return null;
            }
         });
      }

   }

   private PermissionCollection getPermissions(ApplicationClientDescriptor acDescriptor) {
      PermissionCollection pc = null;
      PermissionsBean pb = null;

      try {
         if (acDescriptor != null) {
            pb = acDescriptor.getPermissionsBean();
         }

         pc = EarUtils.getPermissions(pb);
      } catch (Exception var5) {
         System.err.println("Error registering security permissions for the application client: " + var5);
      }

      return pc;
   }
}
