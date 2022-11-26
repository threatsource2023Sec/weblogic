package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleManager;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.config.Environments;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.config.Runtimes;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;

@Service
public class LifecycleManagerImpl implements LifecycleManager, PostConstruct {
   @Inject
   private LifecycleConfigFactory lifecycleConfigFactory;
   @Inject
   private ServiceLocator locator;
   private ScheduledFuture syncHandle;
   private final Runnable syncThread = new Runnable() {
      public void run() {
         try {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Periodic Sync Starting");
            }

            LifecycleManagerImpl.this.syncEnvironments();
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Periodic Sync Completed");
            }
         } catch (LifecycleException var2) {
            if (LifecycleUtils.isDebugEnabled()) {
               LifecycleUtils.debug("Caught Exception performing periodic sync", var2);
            }
         }

      }
   };

   public Environment createEnvironment(String environmentName) throws LifecycleException {
      Objects.requireNonNull(environmentName);
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Creating environment" + environmentName);
      }

      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      if (lifecycleConfig == null) {
         throw new RuntimeException(CatalogUtils.getMsgLCMConfigNotFound());
      } else {
         Environments environmentConfigs = lifecycleConfig.getEnvironments();
         if (environmentConfigs == null) {
            throw new RuntimeException(CatalogUtils.getMsgEnvConfigNotFound());
         } else {
            try {
               com.oracle.weblogic.lifecycle.config.Environment environmentConfig = environmentConfigs.createEnvironment(environmentName);
               return this.getEnvironmentFromConfig(environmentConfig);
            } catch (IllegalStateException var5) {
               throw new LifecycleException(CatalogUtils.getMsgEnvExists(environmentName), var5);
            }
         }
      }
   }

   public boolean deleteEnvironment(String environmentName) {
      if (environmentName == null) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecycleManager.deleteEnvironment environmentName is Null");
         }

         return false;
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("deleting environment" + environmentName);
         }

         LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
         if (lifecycleConfig == null) {
            throw new RuntimeException(CatalogUtils.getMsgLCMConfigNotFound());
         } else {
            Environments environmentConfigs = lifecycleConfig.getEnvironments();
            if (environmentConfigs == null) {
               throw new RuntimeException(CatalogUtils.getMsgEnvConfigNotFound());
            } else {
               com.oracle.weblogic.lifecycle.config.Environment environmentConfig = environmentConfigs.getEnvironmentByName(environmentName);
               if (environmentConfig != null) {
                  return environmentConfigs.deleteEnvironment(environmentConfig) != null;
               } else {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("Environment does not exist");
                  }

                  return false;
               }
            }
         }
      }
   }

   public Environment getEnvironment(String environmentName) {
      if (environmentName == null) {
         return null;
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Getting environment" + environmentName);
         }

         LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
         if (lifecycleConfig == null) {
            throw new RuntimeException(CatalogUtils.getMsgLCMConfigNotFound());
         } else {
            Environments environmentConfigs = lifecycleConfig.getEnvironments();
            if (environmentConfigs == null) {
               throw new RuntimeException(CatalogUtils.getMsgEnvConfigNotFound());
            } else {
               com.oracle.weblogic.lifecycle.config.Environment environmentConfig = environmentConfigs.getEnvironmentByName(environmentName);
               if (environmentConfig == null) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("Environment not found");
                  }

                  return null;
               } else {
                  return this.getEnvironmentFromConfig(environmentConfig);
               }
            }
         }
      }
   }

   public Environment getEnvironment(String partitionId, String runtimeName) {
      if (partitionId == null) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Environment.getEnvironment : partitionId is Null");
         }

         return null;
      } else if (runtimeName == null) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Environment.getEnvironment : runtimeName is Null");
         }

         return null;
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Getting environment for partition " + runtimeName + "/" + partitionId);
         }

         LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
         if (lifecycleConfig == null) {
            throw new RuntimeException(CatalogUtils.getMsgLCMConfigNotFound());
         } else {
            Runtimes runtimes = lifecycleConfig.getRuntimes();
            if (runtimes == null) {
               throw new RuntimeException(CatalogUtils.getMsgRuntimesNotFound());
            } else {
               Runtime runtime = runtimes.getRuntimeByName(runtimeName);
               if (runtime == null) {
                  throw new RuntimeException(CatalogUtils.getMsgRuntimeNotFound(runtimeName));
               } else {
                  Partition partition = runtime.getPartitionById(partitionId);
                  if (partition == null) {
                     throw new RuntimeException(CatalogUtils.getMsgPartitionNotFound(partitionId));
                  } else {
                     Environments environments = lifecycleConfig.getEnvironments();
                     if (environments == null) {
                        throw new RuntimeException(CatalogUtils.getMsgEnvConfigNotFound());
                     } else {
                        com.oracle.weblogic.lifecycle.config.Environment environment = environments.getReferencedEnvironment(partition);
                        if (environment != null) {
                           return this.getEnvironmentFromConfig(environment);
                        } else {
                           if (LifecycleUtils.isDebugEnabled()) {
                              LifecycleUtils.debug("Environment not found for the partition");
                           }

                           return null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public List getEnvironments() {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Getting all environments");
      }

      List environmentList = new ArrayList();
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      if (lifecycleConfig == null) {
         throw new RuntimeException(CatalogUtils.getMsgLCMConfigNotFound());
      } else {
         Environments environmentConfigs = lifecycleConfig.getEnvironments();
         if (environmentConfigs == null) {
            throw new RuntimeException(CatalogUtils.getMsgEnvConfigNotFound());
         } else {
            List environmentConfigList = environmentConfigs.getEnvironments();
            if (environmentConfigList == null) {
               return environmentList;
            } else {
               Iterator iterator = environmentConfigList.iterator();

               while(iterator.hasNext()) {
                  com.oracle.weblogic.lifecycle.config.Environment environmentConfig = (com.oracle.weblogic.lifecycle.config.Environment)iterator.next();
                  Environment environment = this.getEnvironmentFromConfig(environmentConfig);
                  environmentList.add(environment);
               }

               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("Found " + environmentList.size() + " environments");
               }

               return environmentList;
            }
         }
      }
   }

   public void syncEnvironments() throws LifecycleException {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Syncing environments");
      }

      RuntimeManager runtimeManager = (RuntimeManager)this.locator.getService(RuntimeManager.class, new Annotation[0]);
      List runtimeList = runtimeManager.getRuntimes();
      Iterator var3 = runtimeList.iterator();

      while(var3.hasNext()) {
         LifecycleRuntime runtime = (LifecycleRuntime)var3.next();
         runtime.syncPartitions();
      }

      List environments = this.getEnvironments();

      Environment environment;
      for(Iterator var7 = environments.iterator(); var7.hasNext(); this.syncEnvironment(runtimeManager, environment, (Set)null)) {
         environment = (Environment)var7.next();
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("syncEnvironments: Calling sync on environment= " + environment.getName());
         }
      }

   }

   public void configurePeriodicSync(int numHours) {
      TimeUnit timeUnitHour = TimeUnit.HOURS;
      synchronized(this) {
         if (this.syncHandle != null) {
            this.syncHandle.cancel(false);
            this.syncHandle = null;
         }

         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Configuring periodic Sync Interval to " + numHours);
         }

         if (numHours > 0) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            this.syncHandle = scheduler.scheduleAtFixedRate(this.syncThread, 1L, (long)numHours, timeUnitHour);
         }
      }
   }

   public void syncEnvironment(String environmentName) throws LifecycleException {
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Syncing environment " + environmentName);
      }

      RuntimeManager runtimeManager = (RuntimeManager)this.locator.getService(RuntimeManager.class, new Annotation[0]);
      Environment environment = this.getEnvironment(environmentName);
      Set runtimes = new HashSet();
      this.syncEnvironment(runtimeManager, environment, runtimes);
   }

   private void syncEnvironment(RuntimeManager runtimeManager, Environment environment, Set runtimes) throws LifecycleException {
      if (environment == null) {
         throw new LifecycleException(CatalogUtils.getMsgNullEnvForSync());
      } else {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("syncEnvironment: Calling sync on environment= " + environment.getName());
         }

         if (runtimes != null) {
            ArrayList list = new ArrayList();
            list.addAll(runtimes);
            Iterator var5 = list.iterator();

            while(var5.hasNext()) {
               String runtimeName = (String)var5.next();
               LifecycleRuntime runtime = runtimeManager.getRuntime(runtimeName);
               if (runtime != null) {
                  runtime.syncPartitions();
               }
            }
         }

      }
   }

   private Environment getEnvironmentFromConfig(com.oracle.weblogic.lifecycle.config.Environment env) {
      EnvironmentImpl environment = (EnvironmentImpl)this.locator.getService(EnvironmentImpl.class, new Annotation[0]);

      assert environment != null;

      environment.setName(env.getName());
      return environment;
   }

   private String loadPlugin(String pluginPath) throws Exception {
      URL url = new URL("file:///" + pluginPath);
      ServiceJarClassLoader classLoader = new ServiceJarClassLoader(url, LifecycleManagerImpl.class.getClassLoader());
      Map pluginClasses = null;

      try {
         pluginClasses = classLoader.loadClasses();
      } finally {
         classLoader.close();
      }

      if (pluginClasses != null && !pluginClasses.isEmpty()) {
         String pluginType = (String)pluginClasses.keySet().iterator().next();
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("loading lifecycle plugin of type " + pluginType);
         }

         List classes = (List)pluginClasses.get(pluginType);
         Iterator var7 = classes.iterator();

         while(true) {
            List descriptors;
            do {
               if (!var7.hasNext()) {
                  return pluginType;
               }

               Class classObject = (Class)var7.next();
               descriptors = ServiceLocatorUtilities.addClasses(this.locator, new Class[]{classObject});
            } while(!LifecycleUtils.isDebugEnabled());

            Iterator var10 = descriptors.iterator();

            while(var10.hasNext()) {
               ActiveDescriptor descriptor = (ActiveDescriptor)var10.next();
               Set types = descriptor.getContractTypes();
               Iterator iterator = types.iterator();

               while(iterator.hasNext()) {
                  LifecycleUtils.debug("contract type=" + iterator.next());
               }
            }
         }
      } else {
         throw new LifecycleException(CatalogUtils.getMsgPluginLoadFailed(pluginPath));
      }
   }

   public void postConstruct() {
      LifecycleConfig lifecycleConfig = this.lifecycleConfigFactory.getLifecycleConfig();
      if (lifecycleConfig == null) {
         throw new RuntimeException(CatalogUtils.getMsgLCMConfigNotFound());
      } else {
         try {
            if (LifecycleUtils.isAppServer()) {
               this.loadPlugins();
            }
         } catch (Exception var3) {
            if (LifecycleUtils.isAppServer()) {
               LCMLogger.logExceptionLoadingPlugins(var3);
            }
         }

         this.configurePeriodicSync(LifecycleUtils.getPeriodicSyncInterval());
      }
   }

   private void loadPlugins() {
      String wlHome = System.getenv("WL_HOME");
      String pluginDirStr = wlHome + File.separator + "common" + File.separator + "lifecycle" + File.separator + "plugins";
      if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Plugin directory = " + pluginDirStr);
      }

      File pluginDir = new File(pluginDirStr);
      if (pluginDir.isDirectory()) {
         File[] files = pluginDir.listFiles(this.getFileNameFilter());

         for(int i = 0; i < files.length; ++i) {
            try {
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("Registering lifecycle plugin from plugins directory : " + files[i].toString());
               }

               this.loadPlugin(files[i].toString());
            } catch (Exception var7) {
               if (LifecycleUtils.isAppServer()) {
                  LCMLogger.logExceptionLoadingPlugins(var7);
               } else if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug("Exception when registering from plugin Directory", var7);
               }
            }
         }
      } else if (LifecycleUtils.isDebugEnabled()) {
         LifecycleUtils.debug("Plugin directory does not exist");
      }

   }

   private FilenameFilter getFileNameFilter() {
      String ext = ".jar";
      FilenameFilter filter = new MyFileNameFilter(ext);
      return filter;
   }

   private class MyFileNameFilter implements FilenameFilter {
      private String ext;

      public MyFileNameFilter(String ext) {
         this.ext = ext.toLowerCase();
      }

      public boolean accept(File dir, String name) {
         return name.toLowerCase().endsWith(this.ext);
      }
   }
}
