package weblogic.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.management.InvalidAttributeValueException;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.deploy.internal.DeploymentManagerLogger;
import weblogic.management.internal.InternalApplication;
import weblogic.management.internal.InternalApplicationProcessor;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.UpdateException;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

@Service
@Singleton
public class InternalAppProcessor implements InternalApplicationProcessor {
   @Inject
   private InternalAppService internalAppService;
   private static boolean done = false;
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final AuthenticatedSubject KERNELID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String COMMON_MODULES;

   public void updateConfiguration(PartitionMBean partition) throws UpdateException {
      if (partition != null) {
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Processing internal apps for partition " + partition.getName());
         }

         DomainMBean domain = (DomainMBean)partition.getParentBean();
         partition.setInternalAppDeployments((AppDeploymentMBean[])null);
         partition.setInternalLibraries((LibraryMBean[])null);
         TargetMBean[] adminTargets = getTargetsForCurrentServer(partition.findEffectiveAdminTargets());
         TargetMBean[] effectiveTargets = getTargetsForCurrentServer(partition.findEffectiveTargets());
         if (adminTargets.length == 0 && effectiveTargets.length == 0) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug(String.format("Skipping partition internal apps processing for %s: server %s is not part of the partition admin or effective targets", partition.getName(), getRuntimeAccess().getServerName(), partition.getName()));
            }

         } else {
            Iterator var5 = this.internalAppService.getEarlyInternalApplications().iterator();

            while(true) {
               InternalApplication app;
               do {
                  if (!var5.hasNext()) {
                     return;
                  }

                  app = (InternalApplication)var5.next();
               } while(!app.isMtEnabled());

               if (LOGGER.isDebugEnabled()) {
                  LOGGER.debug(String.format("Processing internal app %s for partition %s", app.getName(), partition.getName()));
               }

               try {
                  File source = getSourceFile(app);
                  TargetMBean[] targets;
                  if (app.isAdminOnly()) {
                     if (adminTargets.length == 0) {
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug(String.format("Skipping internal app processing %s for partition %s: the server %s is not part of the partition admin targets", app.getName(), getRuntimeAccess().getServerName(), partition.getName()));
                        }
                        continue;
                     }

                     targets = adminTargets;
                  } else {
                     if (effectiveTargets.length == 0) {
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug(String.format("Skipping internal app processing %s for partition %s: the server %s is not part of the partition effective targets", app.getName(), getRuntimeAccess().getServerName(), partition.getName()));
                        }
                        continue;
                     }

                     targets = effectiveTargets;
                  }

                  TargetMBean[] var9 = targets;
                  int var10 = targets.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     TargetMBean target = var9[var11];
                     if (!app.isDeployPerVT() && targets.length > 1 && target.getName().compareTo(partition.getAdminVirtualTarget().getName()) != 0) {
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug(String.format("Skipping internal app processing %s for virtual target %s, since flag isDeployPerVT is false and the app has already been processed once", app.getName(), target.getName()));
                        }
                     } else {
                        String appName = app.getName() + "-" + target.getName();
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug(String.format("Creating AppDeploymentMBean '%s for internal app: '%s' - partition %s' - target='%s'", appName, app.getName(), partition.getName(), target.getName()));
                        }

                        String path = source.getPath();
                        String cloneName = PartitionProcessor.addSuffix(partition, appName);
                        Object du;
                        Object clone;
                        if (app.isLib()) {
                           if (LOGGER.isDebugEnabled()) {
                              LOGGER.debug("Creating LibraryMBean for internal app: " + path);
                           }

                           du = partition.createInternalLibrary(appName, path);
                           clone = domain.lookupInternalLibrary(cloneName);
                           if (clone == null) {
                              clone = (AppDeploymentMBean)PartitionProcessor.clone(domain, partition, (ResourceGroupMBean)null, (LibraryMBean)du, false, domain);
                              domain.addInternalLibrary((LibraryMBean)clone);
                           }
                        } else {
                           if (LOGGER.isDebugEnabled()) {
                              LOGGER.debug("Creating AppDeploymentMBean for internal app: " + path);
                           }

                           du = partition.createInternalAppDeployment(appName, path);
                           clone = domain.lookupInternalAppDeployment(cloneName);
                           if (clone == null) {
                              clone = (AppDeploymentMBean)PartitionProcessor.clone(domain, partition, (ResourceGroupMBean)null, (ConfigurationMBean)du, false, domain);
                              domain.addInternalAppDeployment((AppDeploymentMBean)clone);
                           }
                        }

                        processAppDeploymentMBean((AppDeploymentMBean)du, domain, app);
                        processAppDeploymentMBean((AppDeploymentMBean)clone, domain, app);
                        if (app.getName() != null && app.getName().equals("wls-management-services") && domain.isInternalAppsDeployOnDemandEnabled()) {
                           if (LOGGER.isDebugEnabled()) {
                              LOGGER.debug(String.format("Enabling on-demand deployment flags for internal app %s for partition %s, so that the deployment of this app is deferred until it is first accessed", app.getName(), partition.getName()));
                           }

                           ((AppDeploymentMBean)du).setOnDemandContextPaths(new String[]{"management"});
                           ((AppDeploymentMBean)du).setOnDemandDisplayRefresh(false);
                           ((AppDeploymentMBean)clone).setOnDemandContextPaths(new String[]{"management"});
                           ((AppDeploymentMBean)clone).setOnDemandDisplayRefresh(false);
                        }

                        ((AppDeploymentMBean)clone).setSourcePath(path);
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug(String.format("Targeting internal app %s for partition %s after: %s", appName, partition.getName(), target.getName()));
                        }

                        ((AppDeploymentMBean)clone).setTargets(new TargetMBean[]{target});
                     }
                  }
               } catch (ManagementException | InvalidAttributeValueException | IllegalArgumentException var18) {
                  handleErr(var18, app.getName(), app.isCritical());
               }
            }
         }
      }
   }

   public void updateConfiguration(DomainMBean domain) throws UpdateException {
      Class var2 = InternalAppProcessor.class;
      synchronized(InternalAppProcessor.class) {
         if (!done) {
            if (LOGGER.isDebugEnabled()) {
               LOGGER.debug("Processing internal apps for domain ");
            }

            boolean isAdminServer = isAdminServer();
            createStagingDir();
            ConfigurableInternalAppHelper viaHelper = ConfigurableInternalAppHelper.getInstance(this.internalAppService.getInternalApplications());
            Iterator var5 = this.internalAppService.getInternalApplications().iterator();

            while(true) {
               InternalApplication app;
               do {
                  if (!var5.hasNext()) {
                     done = true;
                     return;
                  }

                  app = (InternalApplication)var5.next();
               } while(app.isAdminOnly() && !isAdminServer);

               if (viaHelper.isDeployable(app.getOptionalFeatureName())) {
                  stageFiles(app);

                  try {
                     String appName = app.getName();
                     if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(String.format("Processing internal app: %s for domain", appName));
                     }

                     File source = getSourceFile(app);
                     if (!source.exists()) {
                        throw new ApplicationException(DeployerRuntimeLogger.noAppFilesExist(appName));
                     }

                     String path = source.getPath();
                     Object du;
                     if (app.isLib()) {
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug("Creating LibraryMBean for internal app: " + path);
                        }

                        du = domain.createInternalLibrary(appName, path);
                     } else {
                        if (LOGGER.isDebugEnabled()) {
                           LOGGER.debug("Creating AppDeploymentMBean for internal app: " + path);
                        }

                        du = domain.createInternalAppDeployment(appName, path);
                     }

                     processAppDeploymentMBean((AppDeploymentMBean)du, domain, app);
                     TargetMBean[] targets = null;
                     if (app.isClustered()) {
                        if (app.isClustered() && app.getVirtualHostName() != null) {
                           targets = getTargetsForVirtualHostInternalApp(domain, app.getVirtualHostName());
                           if (LOGGER.isDebugEnabled() && targets != null) {
                              LOGGER.debug(String.format("Targeting internal app %s for VirtualHost: %s", appName, app.getVirtualHostName()));
                           }
                        }

                        if (targets == null) {
                           targets = getTargetsForClusteredInternalApp(domain);
                        }
                     } else {
                        targets = getTargetsForInternalApp(domain);
                     }

                     if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(String.format("Targeting internal app %s after: %s", appName, targetNames(targets)));
                     }

                     ((AppDeploymentMBean)du).setTargets(targets);
                  } catch (ApplicationException | IllegalArgumentException | ManagementException | InvalidAttributeValueException var13) {
                     handleErr(var13, app.getName(), app.isCritical());
                  }
               }
            }
         }
      }
   }

   private static String targetNames(TargetMBean[] targets) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < targets.length; ++i) {
         if (i == 0) {
            sb.append("[ ");
         }

         if (targets[i] != null) {
            sb.append(targets[i].getName());
         } else {
            sb.append("null");
         }

         if (i < targets.length - 1) {
            sb.append(",");
         } else {
            sb.append(" ]");
         }
      }

      return sb.toString();
   }

   private static void processAppDeploymentMBean(AppDeploymentMBean du, DomainMBean domain, InternalApplication app) throws ManagementException {
      if (app.isBackground()) {
         du.setBackgroundDeployment(true);
      }

      du.setPersistenceEnabled(false);
      du.setDeploymentOrder(0);
      du.setStagingMode("nostage");
      du.setSecurityDDModel("DDOnly");
      du.setInternalApp(true);
      if (app.getOnDemandContextPaths() != null && app.getOnDemandContextPaths().length > 0 && domain.isInternalAppsDeployOnDemandEnabled()) {
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(String.format("Marking internal application for on-demand deployment: %s with refresh page boolean: %b", app.getName(), app.isOnDemandDisplayRefresh()));
         }

         du.setOnDemandContextPaths(app.getOnDemandContextPaths());
         du.setOnDemandDisplayRefresh(app.isOnDemandDisplayRefresh());
         du.setBackgroundDeployment(true);
      }

      du.setDeploymentPlanDescriptor(app.getDeploymentPlanBean());
      ((AbstractDescriptorBean)du)._setTransient(true);
   }

   private static void stageFiles(InternalApplication app) throws UpdateException {
      if (!app.isAdminOnly()) {
         String fileName = app.getName() + app.getSuffix();
         String path = app.getLocation();
         boolean result = false;
         DeploymentException error = new DeploymentException(fileName);
         File stagedApp = new File(getStageDir(), fileName);
         File wlsAppPath = new File(path);
         if (wlsAppPath.exists() && wlsAppPath.isDirectory()) {
            String sourceFileName = fileName;
            if (app.getSourceFileName() != null) {
               sourceFileName = app.getSourceFileName();
            }

            File sourceAppFile = new File(wlsAppPath, sourceFileName);
            if (sourceAppFile.exists()) {
               if (stagedApp.lastModified() == sourceAppFile.lastModified()) {
                  result = true;
               } else {
                  if (LOGGER.isDebugEnabled()) {
                     LOGGER.debug(String.format("Staging File: %s%s. copying '%s' to '%s'", path, fileName, sourceFileName, stagedApp));
                  }

                  try {
                     FileUtils.copyPreserveTimestampsPreservePermissions(sourceAppFile, stagedApp);
                     result = true;
                  } catch (IOException var21) {
                     if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(String.format("Exception while staging file: %s%s. copying: '%s' to: '%s' %s", path, fileName, sourceFileName, stagedApp, var21.getMessage()));
                     }

                     error = new DeploymentException(var21);
                  }
               }
            } else {
               error = new DeploymentException(DeployerRuntimeLogger.noAppFilesExist(sourceAppFile.toString()));
            }
         } else {
            error = new DeploymentException(DeployerRuntimeLogger.noAppFilesExist(wlsAppPath.toString()));
         }

         if (!result) {
            InputStream in = InternalAppProcessor.class.getResourceAsStream("/" + fileName);
            if (in != null) {
               try {
                  if (LOGGER.isDebugEnabled()) {
                     LOGGER.debug(String.format("Staging globalWarFile. copying: '%s' to '%s'", fileName, stagedApp));
                  }

                  FileUtils.writeToFile(in, stagedApp);
                  result = true;
               } catch (IOException var19) {
                  if (LOGGER.isDebugEnabled()) {
                     LOGGER.debug(String.format("Exception while staging file: /%s unable to get resource from classloader: %s", fileName, var19.getMessage()));
                  }

                  error = new DeploymentException(var19);
               } finally {
                  try {
                     in.close();
                  } catch (IOException var18) {
                  }

               }
            }
         }

         if (!result) {
            handleErr(error, app.getName(), app.isCritical());
         }

      }
   }

   private static void createStagingDir() {
      File stageDir = getStageDir();
      if (stageDir.exists()) {
         if (!stageDir.isDirectory()) {
            stageDir.delete();
            stageDir.mkdirs();
         }
      } else {
         stageDir.mkdirs();
      }

   }

   private static File getSourceFile(InternalApplication app) {
      File source;
      if (app.isAdminOnly()) {
         source = new File(new File(app.getLocation()), app.getName() + app.getSuffix());
      } else {
         source = new File(getStageDir(), app.getName() + app.getSuffix());
      }

      return source;
   }

   private static File getStageDir() {
      String path = DomainDir.getTempDirForServer(getRuntimeAccess().getServerName()) + File.separatorChar + ".internal";

      try {
         return (new File(path)).getCanonicalFile();
      } catch (IOException var2) {
         throw new AssertionError(var2);
      }
   }

   private static TargetMBean[] getTargetsForCurrentServer(TargetMBean[] targets) {
      if (targets != null && targets.length > 0) {
         Set serverTargets = new HashSet();
         TargetMBean[] var2 = targets;
         int var3 = targets.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            if (target != null) {
               Iterator var6 = target.getServerNames().iterator();

               while(var6.hasNext()) {
                  Object serverName = var6.next();
                  if (getRuntimeAccess().getServerName().equals((String)serverName)) {
                     serverTargets.add(target);
                  }
               }
            }
         }

         return (TargetMBean[])serverTargets.toArray(new TargetMBean[serverTargets.size()]);
      } else {
         return new TargetMBean[0];
      }
   }

   private static TargetMBean[] getTargetsForInternalApp(DomainMBean domain) {
      ServerMBean thisServer = domain.lookupServer(getRuntimeAccess().getServerName());
      return new TargetMBean[]{thisServer};
   }

   private static TargetMBean[] getTargetsForClusteredInternalApp(DomainMBean domain) {
      ServerMBean thisServer = domain.lookupServer(getRuntimeAccess().getServerName());
      ClusterMBean thisCluster = thisServer.getCluster();
      return thisCluster != null ? new TargetMBean[]{thisCluster} : new TargetMBean[]{thisServer};
   }

   private static TargetMBean[] getTargetsForVirtualHostInternalApp(DomainMBean domain, String hostname) {
      if (hostname != null) {
         ServerMBean thisServer = domain.lookupServer(getRuntimeAccess().getServerName());
         VirtualHostMBean[] virtualHosts = domain.getVirtualHosts();
         if (virtualHosts != null && virtualHosts.length > 0) {
            VirtualHostMBean[] var4 = virtualHosts;
            int var5 = virtualHosts.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               VirtualHostMBean vh = var4[var6];
               String[] hostNames = vh.getVirtualHostNames();
               if (hostNames != null && hostNames.length > 0) {
                  String[] var13 = hostNames;
                  int var14 = hostNames.length;

                  for(int var11 = 0; var11 < var14; ++var11) {
                     String host = var13[var11];
                     if (hostname.equals(host)) {
                        return new TargetMBean[]{vh};
                     }
                  }
               } else {
                  NetworkAccessPointMBean nap = null;
                  String napName = vh.getNetworkAccessPoint();
                  if (napName != null && !napName.isEmpty()) {
                     nap = thisServer.lookupNetworkAccessPoint(napName);
                  }

                  if (nap != null && hostname.equals(nap.getListenAddress())) {
                     return new TargetMBean[]{vh};
                  }
               }
            }
         }
      }

      return null;
   }

   private static RuntimeAccess getRuntimeAccess() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(KERNELID);
      if (ra != null) {
         return ra;
      } else {
         throw new IllegalStateException("RuntimeAccess is not initialized");
      }
   }

   private static boolean isAdminServer() {
      return getRuntimeAccess().isAdminServer();
   }

   private static void handleErr(Exception e, String name, boolean critical) throws UpdateException {
      if (critical) {
         throw new UpdateException(DeploymentManagerLogger.logCriticalInternalAppNotDeployed(name, e.getMessage()), e);
      } else {
         DeploymentManagerLogger.logNonCriticalInternalAppNotDeployed(name, e.getMessage());
      }
   }

   static {
      COMMON_MODULES = InternalAppService.COMMON_MODULES;
   }
}
