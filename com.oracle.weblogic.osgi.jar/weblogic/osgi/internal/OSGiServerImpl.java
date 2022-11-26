package weblogic.osgi.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.osgi.framework.wiring.FrameworkWiring;
import org.osgi.service.startlevel.StartLevel;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import weblogic.application.internal.ClassLoaders;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.RuntimeDir.Current;
import weblogic.management.configuration.OsgiFrameworkMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.osgi.OSGiBundle;
import weblogic.osgi.OSGiException;
import weblogic.osgi.OSGiLogger;
import weblogic.osgi.OSGiServer;
import weblogic.osgi.spi.WebLogicOSGiServiceProvider;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.utils.classloaders.AddURLInterceptingClassLoader;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Home;
import weblogic.utils.collections.PropertiesHelper;

public class OSGiServerImpl implements OSGiServer {
   private static final String OSGI = "osgi";
   private static final String BUNDLE = "Bundle";
   private static final String BOOT_DELEGATION = "org.osgi.framework.bootdelegation";
   private static final String FELIX_CACHE_DIR = "org.osgi.framework.storage";
   private static final String CLEAN_BUNDLE_CACHE = "org.osgi.framework.storage.clean";
   private static final String SYSTEM_EXTRA = "org.osgi.framework.system.packages.extra";
   private static final String FRAMEWORK_PARENT = "org.osgi.framework.bundle.parent";
   private static final String FELIX_URLHANDLERS = "felix.service.urlhandlers";
   private static final String FALSE = "false";
   private static final String FRAMEWORK_PARENT_FRAMEWORK_OPTION = "framework";
   private static final String POPULATE = "populate";
   private static final String[] POPULATE_ADDITIONS = new String[]{"com.bea.core.servicehelper", "com.bea.logging", "javax.servlet", "javax.servlet.http"};
   private static final String FILTER_STRING = "(objectClass=" + StartLevel.class.getName() + ")";
   private static boolean firstFramework = true;
   private boolean isPartition = false;
   private int location = 1;
   private final OsgiFrameworkMBean bean;
   private Framework framework;
   private BundleContext frameworkContext;
   private final Object lock = new Object();
   private ServiceReference currentServiceReference;
   private StartLevel startLevelService;
   private ServiceTracker startLevelTracker;
   private final List serviceProviders;
   private String partitionName = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public OSGiServerImpl(OsgiFrameworkMBean myBean, List providers) {
      this.bean = myBean;
      this.serviceProviders = providers;
      this.partitionName = this.getOsgiPartition();
   }

   public String getName() {
      return this.bean.getName();
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public ClassFinder getBundleClassFinder(String bundleName, String bundleVersion, Context namingContext) {
      try {
         return this.internalGetBundleClassFinder(bundleName, bundleVersion, namingContext);
      } catch (Throwable var5) {
         if (Logger.isDebugEnabled()) {
            Logger.getLogger().debug(var5.getMessage(), var5);
         }

         return null;
      }
   }

   public OSGiBundle installBundle(InputStream jarFile, int startLevel) throws OSGiException {
      BundleContext context = this.framework.getBundleContext();
      if (context == null) {
         throw new OSGiException("Framework is stopped");
      } else {
         Bundle myBundle;
         try {
            myBundle = context.installBundle("" + this.location++, jarFile);
         } catch (BundleException var9) {
            throw new OSGiException(var9);
         }

         StartLevel localStartService;
         synchronized(this.lock) {
            localStartService = this.startLevelService;
         }

         if (localStartService != null && startLevel >= 1) {
            localStartService.setBundleStartLevel(myBundle, startLevel);
         }

         return new OSGiBundleImpl(myBundle);
      }
   }

   public void startAllBundles() {
      BundleContext context = this.framework.getBundleContext();
      if (context != null) {
         Bundle[] bundles = context.getBundles();
         Bundle[] var3 = bundles;
         int var4 = bundles.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Bundle bundle = var3[var5];
            int state = bundle.getState();
            if (Utilities.isFragment(bundle) || state != 4 && state != 2) {
               if (Logger.isDebugEnabled()) {
                  Logger.getLogger().debug("Not Starting bundle " + bundle.getSymbolicName() + ":" + bundle.getBundleId() + " because state is " + Utilities.osgiStateToString(state) + " or isFragment=" + Utilities.isFragment(bundle));
               }
            } else {
               try {
                  if (Logger.isDebugEnabled()) {
                     Logger.getLogger().debug("Starting bundle " + bundle.getSymbolicName() + ":" + bundle.getBundleId());
                  }

                  bundle.start(2);
                  if (Logger.isDebugEnabled()) {
                     Logger.getLogger().debug("Started bundle " + bundle.getSymbolicName() + ":" + bundle.getBundleId() + " and is now in state " + Utilities.osgiStateToString(bundle.getState()));
                  }
               } catch (BundleException var9) {
                  if (Logger.isDebugEnabled()) {
                     Logger.getLogger().debug(var9.getMessage(), var9);
                  }
               }
            }
         }

      }
   }

   public void refreshAllBundles() {
      if (this.framework != null) {
         BundleContext context = this.framework.getBundleContext();
         if (context != null) {
            Collection bundles = null;
            FrameworkWiring wiring = (FrameworkWiring)context.getBundle(0L).adapt(FrameworkWiring.class);
            if (wiring != null) {
               bundles = wiring.getRemovalPendingBundles();
               if (Logger.isDebugEnabled()) {
                  Logger.getLogger().debug("Removing Pending bundles ");
                  Iterator var4 = bundles.iterator();

                  while(var4.hasNext()) {
                     Bundle bundle = (Bundle)var4.next();
                     Logger.getLogger().debug("Removing Pending Bundle" + bundle.getSymbolicName());
                  }
               }

               if (!bundles.isEmpty()) {
                  wiring.refreshBundles(bundles, new FrameworkListener[]{(FrameworkListener)null});
               }

            }
         }
      }
   }

   private ClassFinder internalGetBundleClassFinder(String bundleName, String versionRange, Context namingContext) throws Throwable {
      BundleContext context = this.framework.getBundleContext();
      Bundle[] bundles = context.getBundles();
      if (bundles == null) {
         return null;
      } else {
         Bundle bestBundle = Utilities.getBestBundle(bundles, bundleName, versionRange);
         if (bestBundle == null) {
            return null;
         } else {
            ClassFinder retVal = new BundleClassFinder(bestBundle);
            if (namingContext == null) {
               return retVal;
            } else {
               Object rawSubContext;
               try {
                  rawSubContext = namingContext.lookup("osgi");
               } catch (NamingException var10) {
                  rawSubContext = null;
               }

               Context subContext;
               if (rawSubContext != null) {
                  subContext = (Context)rawSubContext;
               } else {
                  subContext = namingContext.createSubcontext("osgi");
               }

               subContext.bind("Bundle", bestBundle);
               return retVal;
            }
         }
      }
   }

   private void doPopulate() {
      File homeFile = Home.getFile();
      List associatedBundles = new LinkedList();

      try {
         Utilities.deployFileBundlesIntoFramework(this, associatedBundles, homeFile, "osgi-lib");
      } catch (IOException var4) {
         Utilities.uninstallAll(associatedBundles);
         OSGiLogger.logCouldNotDeployFromOSGiLib(this.bean.getName());
         Logger.printThrowable(var4);
      } catch (OSGiException var5) {
         Utilities.uninstallAll(associatedBundles);
         OSGiLogger.logCouldNotDeployFromOSGiLib(this.bean.getName());
         Logger.printThrowable(var5);
      }

      this.startAllBundles();
   }

   private Class loadFramework(String jarFile, String factoryClassName) throws Throwable {
      File osgiJarFile;
      File osgiParent;
      if (jarFile == null) {
         File home = Home.getFile();
         osgiParent = new File(home, "lib");
         osgiJarFile = new File(osgiParent, "org.apache.felix.org.apache.felix.main.jar");
      } else {
         osgiJarFile = new File(jarFile);
      }

      if (osgiJarFile.exists() && osgiJarFile.canRead()) {
         URL[] osgiURLs = new URL[]{osgiJarFile.toURL()};
         osgiParent = null;
         GenericClassLoader osgiParent;
         if (Utilities.getCurrentCIC().isGlobalRuntime()) {
            osgiParent = AugmentableClassLoaderManager.getAugmentableSystemClassLoader();
         } else {
            osgiParent = ClassLoaders.instance.getOrCreatePartitionClassLoader(this.partitionName);
         }

         AddURLInterceptingClassLoader osgiLoader = new AddURLInterceptingClassLoader(osgiURLs, osgiParent);
         Class ffImplClass = Class.forName(factoryClassName, true, osgiLoader);
         return ffImplClass;
      } else {
         throw new OSGiException("Could not find or read " + osgiJarFile.getAbsolutePath());
      }
   }

   private void internalStart() throws Throwable {
      String factoryClassName = this.bean.getFactoryImplementationClass();
      boolean populate = this.bean.getDeployInstallationBundles().equals("populate");
      Class ffImplClass = this.loadFramework(this.bean.getOsgiImplementationLocation(), factoryClassName);
      FrameworkFactory ff = (FrameworkFactory)ffImplClass.newInstance();
      Object initProperties;
      if (this.bean.getInitProperties() != null) {
         initProperties = PropertiesHelper.toMap(this.bean.getInitProperties());
      } else {
         initProperties = new HashMap();
      }

      ((Map)initProperties).put("org.osgi.framework.bundle.parent", "framework");
      if (!firstFramework) {
         ((Map)initProperties).put("felix.service.urlhandlers", "false");
      } else {
         firstFramework = false;
      }

      String bootDelegation = Utilities.getCommaDelimitedList((String)((Map)initProperties).get("org.osgi.framework.bootdelegation"), this.bean.getOrgOsgiFrameworkBootdelegation(), populate ? POPULATE_ADDITIONS : null, true);
      if (bootDelegation != null) {
         ((Map)initProperties).put("org.osgi.framework.bootdelegation", bootDelegation);
      }

      String cacheDir = this.getFelixCacheDir();
      ((Map)initProperties).put("org.osgi.framework.storage", cacheDir);
      ((Map)initProperties).put("org.osgi.framework.storage.clean", "onFirstInit");
      String extraPackages = Utilities.getCommaDelimitedList((String)((Map)initProperties).get("org.osgi.framework.system.packages.extra"), this.bean.getOrgOsgiFrameworkSystemPackagesExtra(), populate ? POPULATE_ADDITIONS : null, false);
      if (extraPackages != null) {
         ((Map)initProperties).put("org.osgi.framework.system.packages.extra", extraPackages);
      }

      this.framework = ff.newFramework((Map)initProperties);
      this.framework.init();
      this.framework.start();
      this.frameworkContext = this.framework.getBundleContext();
      if (this.frameworkContext != null) {
         Filter osgiFilter = this.frameworkContext.createFilter(FILTER_STRING);
         this.startLevelTracker = new ServiceTracker(this.frameworkContext, osgiFilter, new StartLevelCustomizer(this.frameworkContext, this));
         synchronized(this.lock) {
            ServiceReference[] references = this.frameworkContext.getAllServiceReferences(StartLevel.class.getName(), (String)null);
            if (references != null) {
               ServiceReference[] var12 = references;
               int var13 = references.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  ServiceReference reference = var12[var14];
                  this.setStartLevelService(reference, this.frameworkContext);
               }
            }

            this.startLevelTracker.open();
         }
      }

      if (populate) {
         this.doPopulate();
      }

      Iterator var18 = this.serviceProviders.iterator();

      while(var18.hasNext()) {
         WebLogicOSGiServiceProvider provider = (WebLogicOSGiServiceProvider)var18.next();
         provider.provideServices(this.framework, this.bean);
      }

   }

   void start() throws ServiceFailureException {
      try {
         ComponentInvocationContext cic = null;
         if (this.isPartition) {
            ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
            cic = cicm.createComponentInvocationContext(this.partitionName);
            ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(cic);
            Throwable var4 = null;

            try {
               this.internalStart();
            } catch (Throwable var14) {
               var4 = var14;
               throw var14;
            } finally {
               if (mic != null) {
                  if (var4 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } else {
            this.internalStart();
         }

      } catch (Throwable var16) {
         throw new ServiceFailureException(var16);
      }
   }

   private void internalStop() throws Throwable {
      if (this.frameworkContext != null) {
         synchronized(this.lock) {
            if (this.startLevelTracker != null) {
               this.startLevelTracker.close();
               this.startLevelTracker = null;
            }

            if (this.currentServiceReference != null) {
               this.frameworkContext.ungetService(this.currentServiceReference);
               this.currentServiceReference = null;
            }
         }
      }

      Iterator var1 = this.serviceProviders.iterator();

      while(var1.hasNext()) {
         WebLogicOSGiServiceProvider provider = (WebLogicOSGiServiceProvider)var1.next();
         provider.stopProvidingServices(this.bean);
      }

      if (this.framework != null) {
         this.framework.stop();
         this.framework.waitForStop(Long.MAX_VALUE);
         this.framework = null;
      }

   }

   void stop() {
      try {
         ComponentInvocationContext cic = null;
         if (this.isPartition) {
            ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
            cic = cicm.createComponentInvocationContext(this.partitionName);
            ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(cic);
            Throwable var4 = null;

            try {
               this.internalStop();
            } catch (Throwable var14) {
               var4 = var14;
               throw var14;
            } finally {
               if (mic != null) {
                  if (var4 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var13) {
                        var4.addSuppressed(var13);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } else {
            this.internalStop();
         }
      } catch (Throwable var16) {
         if (Logger.isDebugEnabled()) {
            Logger.getLogger().debug(var16.getMessage(), var16);
         }
      }

   }

   private void setStartLevelService(ServiceReference paramStartLevelReference, BundleContext context) {
      synchronized(this.lock) {
         StartLevel startLevel;
         if (this.currentServiceReference == null) {
            startLevel = (StartLevel)context.getService(paramStartLevelReference);
            if (startLevel != null) {
               this.currentServiceReference = paramStartLevelReference;
               this.startLevelService = startLevel;
            }
         } else if (this.currentServiceReference.compareTo(paramStartLevelReference) <= 0) {
            startLevel = (StartLevel)context.getService(paramStartLevelReference);
            if (startLevel != null) {
               context.ungetService(this.currentServiceReference);
               this.currentServiceReference = paramStartLevelReference;
               this.startLevelService = startLevel;
            }
         }
      }
   }

   private String getFelixCacheDir() {
      String cachedir = System.getProperty("weblogic.osgi.config.FelixCache");
      StringBuilder felixcache = new StringBuilder();
      if (cachedir == null) {
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         felixcache.append(Current.get().getTempDirForServer(server.getName()) + "/felix-cache/" + this.getName());
      } else {
         felixcache.append(cachedir + "/" + this.getName());
      }

      if (Logger.isDebugEnabled()) {
         Logger.getLogger().debug("The Felix Cache Directory is Set to " + felixcache.toString());
      }

      return felixcache.toString();
   }

   private String getOsgiPartition() {
      if (((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).isInPartition(this.bean)) {
         PartitionMBean parent = (PartitionMBean)((OsgiFrameworkMBean)((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).toOriginalBean(this.bean)).getParent().getParent();
         if (parent != null) {
            this.isPartition = true;
            return parent.getName();
         }
      }

      return "DOMAIN";
   }

   public String toString() {
      return "OSGiServerImpl(" + this.bean.getName() + "," + System.identityHashCode(this) + ")";
   }

   private static class StartLevelCustomizer implements ServiceTrackerCustomizer {
      private final BundleContext context;
      private final OSGiServerImpl parent;

      private StartLevelCustomizer(BundleContext context, OSGiServerImpl parent) {
         this.context = context;
         this.parent = parent;
      }

      public Object addingService(ServiceReference reference) {
         this.parent.setStartLevelService(reference, this.context);
         return null;
      }

      public void modifiedService(ServiceReference arg0, Object arg1) {
      }

      public void removedService(ServiceReference arg0, Object arg1) {
      }

      // $FF: synthetic method
      StartLevelCustomizer(BundleContext x0, OSGiServerImpl x1, Object x2) {
         this(x0, x1);
      }
   }
}
