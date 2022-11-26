package weblogic.management.provider.internal.situationalconfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.DomainDir;
import weblogic.management.ManagementLogger;
import weblogic.management.SpecialPropertiesProcessor;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.internal.ProductionModeHelper;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.ConfiguredDeploymentsAccess;
import weblogic.management.provider.internal.DescriptorManagerHelper;
import weblogic.management.provider.internal.DynamicMBeanProcessor;
import weblogic.management.provider.internal.DynamicServersProcessor;
import weblogic.management.provider.internal.PartitionProcessor;
import weblogic.management.provider.internal.RuntimeAccessDeploymentReceiverService;
import weblogic.management.provider.internal.federatedconfig.FederatedConfigImpl;
import weblogic.management.utils.federatedconfig.FederatedConfig;
import weblogic.management.utils.federatedconfig.FederatedConfigLocator;
import weblogic.management.utils.situationalconfig.SituationalConfigLoader;
import weblogic.management.workflow.DescriptorLock;
import weblogic.management.workflow.DescriptorLockHandle;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.LocatorUtilities;

public class XMLFileLoader implements SituationalConfigLoader {
   private DescriptorLock descriptorLock = (DescriptorLock)LocatorUtilities.getService(DescriptorLock.class);
   private DescriptorLockHandle descriptorLockHandle;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private List sitConfigLocators = new ArrayList();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private static final File domainDirConfig = new File(DomainDir.getConfigDir(), "config.xml");
   private static boolean runWithDebugFile = Boolean.getBoolean("weblogic.runWithDebugSitConfig");
   private SituationalPropertiesProcessor situationalPropManager = (SituationalPropertiesProcessor)LocatorUtilities.getService(SituationalPropertiesProcessor.class);
   private boolean sitConfigApplied = false;
   private boolean forceReload = false;
   @Inject
   private ConfiguredDeploymentsAccess configuredDeployments;

   public XMLFileLoader(boolean isInSitConfigState, boolean isForceReload, FederatedConfigLocator... locators) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("SituationalConfigLoader: " + Arrays.toString(locators));
      }

      FederatedConfigLocator[] var4 = locators;
      int var5 = locators.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         FederatedConfigLocator locator = var4[var6];
         this.sitConfigLocators.add(locator);
      }

      this.setSituationalConfigApplied(isInSitConfigState);
      this.forceReload = isForceReload;
   }

   public InputStream createFederatedStream(InputStream baseStream) throws Exception {
      FederatedConfig fc = FederatedConfigImpl.getSingleton();
      InputStream is = ((FederatedConfigImpl)fc).combine(baseStream, this.sitConfigLocators.iterator());
      return is;
   }

   private boolean containsFiles() {
      Iterator var1 = this.sitConfigLocators.iterator();

      FederatedConfigLocator fc;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         fc = (FederatedConfigLocator)var1.next();
         if (!(fc instanceof XMLSituationalConfigLocator)) {
            return true;
         }
      } while(((XMLSituationalConfigLocator)fc).getOrderedCollection().isEmpty());

      return true;
   }

   private boolean applyConfig(boolean deferOnNonDynamicChanges, Descriptor configDescriptor) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[SitConfig] loading config");
      }

      if (!domainDirConfig.exists()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] returning, no domain dir");
         }

         return false;
      } else if (!this.sitConfigApplied && !this.containsFiles() && !this.forceReload) {
         return false;
      } else {
         DescriptorLockHandle descriptorLockHandle = this.descriptorLock.lock(0L);
         if (descriptorLockHandle == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Possible live-lock with deployment, doing nothing");
            }

            return false;
         } else {
            boolean var25;
            try {
               RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
               InputStream baseConfigIS = new FileInputStream(domainDirConfig);
               DescriptorManager descMgr = DescriptorManagerHelper.getDescriptorManager(false);
               ArrayList errs = new ArrayList();
               Descriptor newDescriptor;
               if (!this.containsFiles()) {
                  this.sitConfigApplied = false;
                  newDescriptor = descMgr.createDescriptor(baseConfigIS, errs, false);
                  if (debugLogger.isDebugEnabled()) {
                     this.writeDescriptorToXML(newDescriptor, "Resetting sitational config Descriptor as XML ");
                  }
               } else {
                  this.sitConfigApplied = true;
                  newDescriptor = descMgr.createDescriptor(this.createFederatedStream(baseConfigIS), errs, false);
                  if (debugLogger.isDebugEnabled()) {
                     this.writeDescriptorToXML(newDescriptor, "Updated sitational config Descriptor as XML ");
                  }
               }

               DomainMBean sitConfigDomainMBean = (DomainMBean)newDescriptor.getRootBean();
               DynamicServersProcessor.updateConfiguration(sitConfigDomainMBean);
               PartitionProcessor.updateConfiguration(sitConfigDomainMBean);
               if (this.configuredDeployments != null) {
                  this.configuredDeployments.updateMultiVersionConfiguration(sitConfigDomainMBean);
               }

               Descriptor sitConfigDescriptor = sitConfigDomainMBean.getDescriptor();
               Descriptor currentRuntimeDescriptor = configDescriptor != null ? configDescriptor : runtimeAccess.getDomain().getDescriptor();
               this.situationalPropManager.loadConfiguration(sitConfigDomainMBean);
               SpecialPropertiesProcessor.updateConfiguration(sitConfigDomainMBean, false);
               DynamicMBeanProcessor.getInstance().updateConfiguration(sitConfigDomainMBean);
               if (!ManagementService.getPropertyService(kernelId).isAdminServer()) {
                  if (ProductionModeHelper.isGlobalProductionModeSet()) {
                     sitConfigDomainMBean.setProductionModeEnabled(ProductionModeHelper.getGlobalProductionMode());
                  }
               } else if (ProductionModeHelper.isProductionModePropertySet()) {
                  sitConfigDomainMBean.setProductionModeEnabled(ProductionModeHelper.getProductionModeProperty());
               }

               boolean hasNonDynamicChanges = false;

               try {
                  DescriptorDiff diff = currentRuntimeDescriptor.prepareUpdateDiff(sitConfigDescriptor, false);
                  Iterator it = diff.iterator();

                  while(!hasNonDynamicChanges && it.hasNext()) {
                     BeanUpdateEvent event = (BeanUpdateEvent)it.next();
                     BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

                     for(int i = 0; updates != null && i < updates.length && !hasNonDynamicChanges; ++i) {
                        if (!updates[i].isDynamic() && deferOnNonDynamicChanges) {
                           hasNonDynamicChanges = true;
                           DescriptorBean bean = event.getSourceBean();
                           String propertyName = updates[i].getPropertyName();
                           ManagementLogger.logNonDynamicSitConfigChange(bean.toString(), propertyName);
                        }
                     }
                  }

                  if (hasNonDynamicChanges && deferOnNonDynamicChanges) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Non-dynamic changes found, warn and do not apply.");
                     }

                     currentRuntimeDescriptor.rollbackUpdate();
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("hasNonDynamicChanges: " + hasNonDynamicChanges + "  - activating changes from situational config files.");
                     }

                     currentRuntimeDescriptor.activateUpdate();
                  }
               } catch (Exception var23) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Failure Preparing", var23);
                  }

                  currentRuntimeDescriptor.rollbackUpdate();
               }

               var25 = hasNonDynamicChanges;
            } finally {
               descriptorLockHandle.unlock();
            }

            return var25;
         }
      }
   }

   private void writeDescriptorToXML(Descriptor federatedConfigDescriptor, String message) throws IOException {
      try {
         if (debugLogger.isDebugEnabled()) {
            ByteArrayOutputStream descriptorToXml = new ByteArrayOutputStream();
            federatedConfigDescriptor.toXML(descriptorToXml);
            debugLogger.debug(message + "\n" + descriptorToXml.toString());
         }
      } catch (Exception var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Cannot write the Descriptor as xml due to " + var4.getMessage(), var4);
         }
      }

   }

   private boolean applySystemResource(String baseFileName, boolean deferOnNonDynamicChanges, HashMap systemResourceDescriptors) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[SitConfig] loading system resource config for " + baseFileName);
      }

      if (!(new File(baseFileName)).exists()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[SitConfig] returning, no system resource base file");
         }

         return false;
      } else if (!this.sitConfigApplied && !this.containsFiles() && !this.forceReload) {
         return false;
      } else {
         DescriptorLockHandle descriptorLockHandle = this.descriptorLock.lock(0L);
         if (descriptorLockHandle == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Possible live-lock with deployment, doing nothing");
            }

            return false;
         } else {
            boolean var10;
            try {
               InputStream baseConfigIS = new FileInputStream(baseFileName);
               DescriptorManager descMgr = DescriptorManagerHelper.getDescriptorManager(false);
               ArrayList errs = new ArrayList();
               Descriptor newDescriptor;
               if (!this.containsFiles()) {
                  this.sitConfigApplied = false;
                  newDescriptor = descMgr.createDescriptor(baseConfigIS, errs, false);
                  if (debugLogger.isDebugEnabled()) {
                     this.writeDescriptorToXML(newDescriptor, "Resetting sitational config system resource Descriptor as XML ");
                  }
               } else {
                  this.sitConfigApplied = true;
                  newDescriptor = descMgr.createDescriptor(this.createFederatedStream(baseConfigIS), errs, false);
                  if (debugLogger.isDebugEnabled()) {
                     this.writeDescriptorToXML(newDescriptor, "Updated sitational config Descriptor as XML ");
                  }
               }

               if (systemResourceDescriptors == null) {
                  var10 = this.processSystemResourceDescriptor(newDescriptor, baseFileName, deferOnNonDynamicChanges);
                  return var10;
               }

               var10 = this.processSystemResourceTemporaryDescriptor(newDescriptor, baseFileName, systemResourceDescriptors);
            } finally {
               descriptorLockHandle.unlock();
            }

            return var10;
         }
      }
   }

   public boolean loadConfig(boolean deferOnNonDynamicChanges) throws Exception {
      return this.loadConfig(deferOnNonDynamicChanges, (Descriptor)null);
   }

   public boolean loadConfig(boolean deferOnNonDynamicChanges, Descriptor configDescriptor) throws Exception {
      Object lock = RuntimeAccessDeploymentReceiverService.getLockObject();
      if (lock == null) {
         lock = this;
      }

      synchronized(lock) {
         return this.applyConfig(deferOnNonDynamicChanges, configDescriptor);
      }
   }

   public boolean loadJMS(String jmsBaseFileName, boolean deferOnNonDynamicChanges, HashMap systemResourceDescriptors) throws Exception {
      Object lock = RuntimeAccessDeploymentReceiverService.getLockObject();
      if (lock == null) {
         lock = this;
      }

      synchronized(lock) {
         return this.applySystemResource(jmsBaseFileName, deferOnNonDynamicChanges, systemResourceDescriptors);
      }
   }

   public boolean loadJDBC(String jdbcBaseFileName, boolean deferOnNonDynamicChanges, HashMap systemResourceDescriptors) throws Exception {
      Object lock = RuntimeAccessDeploymentReceiverService.getLockObject();
      if (lock == null) {
         lock = this;
      }

      synchronized(lock) {
         return this.applySystemResource(jdbcBaseFileName, deferOnNonDynamicChanges, systemResourceDescriptors);
      }
   }

   public boolean loadDiagnostics(String diagnosticsBaseFileName, boolean deferOnNonDynamicChanges, HashMap systemResourceDescriptors) throws Exception {
      Object lock = RuntimeAccessDeploymentReceiverService.getLockObject();
      if (lock == null) {
         lock = this;
      }

      synchronized(lock) {
         return this.applySystemResource(diagnosticsBaseFileName, deferOnNonDynamicChanges, systemResourceDescriptors);
      }
   }

   public boolean isSituationalConfigApplied() {
      return this.sitConfigApplied;
   }

   public void setSituationalConfigApplied(boolean applied) {
      this.sitConfigApplied = applied;
   }

   private boolean processSystemResourceDescriptor(Descriptor sitConfigDescriptor, String baseFileName, boolean deferOnNonDynamicChanges) {
      boolean hasNonDynamicChanges = false;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      SystemResourceMBean[] var6 = runtimeAccess.getDomain().getSystemResources();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         SystemResourceMBean sysRes = var6[var8];
         Descriptor currentRuntimeDescriptor = sysRes.getResource().getDescriptor();

         try {
            File baseFile = new File(sysRes.getSourcePath());
            if (!baseFile.getCanonicalPath().equals(baseFileName)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Skipping base file " + baseFile.getAbsolutePath() + " since it does not match " + baseFileName);
               }
            } else {
               DescriptorDiff diff = currentRuntimeDescriptor.prepareUpdateDiff(sitConfigDescriptor, false);
               Iterator it = diff.iterator();

               while(!hasNonDynamicChanges && it.hasNext()) {
                  BeanUpdateEvent event = (BeanUpdateEvent)it.next();
                  BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();

                  for(int i = 0; updates != null && i < updates.length && !hasNonDynamicChanges; ++i) {
                     if (!updates[i].isDynamic() && deferOnNonDynamicChanges) {
                        hasNonDynamicChanges = true;
                        DescriptorBean bean = event.getSourceBean();
                        String propertyName = updates[i].getPropertyName();
                        ManagementLogger.logNonDynamicSitConfigChange(bean.toString(), propertyName);
                     }
                  }
               }

               if (hasNonDynamicChanges && deferOnNonDynamicChanges) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Non-dynamic changes found, warn and do not apply.");
                  }

                  currentRuntimeDescriptor.rollbackUpdate();
               } else {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("hasNonDynamicChanges: " + hasNonDynamicChanges + "  - activating changes from situational config files.");
                  }

                  currentRuntimeDescriptor.activateUpdate();
               }
            }
         } catch (Exception var19) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failure Preparing", var19);
            }

            currentRuntimeDescriptor.rollbackUpdate();
         }
      }

      return hasNonDynamicChanges;
   }

   private boolean processSystemResourceTemporaryDescriptor(Descriptor sitConfigDescriptor, String baseFileName, HashMap systemResourceDescriptors) throws IOException {
      boolean hasNonDynamicChanges = false;
      Iterator var5 = systemResourceDescriptors.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         Descriptor currentRuntimeDescriptor = (Descriptor)entry.getValue();

         try {
            File baseFile = new File((String)entry.getKey());
            if (!baseFile.getCanonicalPath().equals(baseFileName)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Skipping base file " + baseFile.getAbsolutePath() + " since it does not match " + baseFileName);
               }
            } else {
               currentRuntimeDescriptor.prepareUpdate(sitConfigDescriptor, false);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(": prepared update to temp tree " + currentRuntimeDescriptor);
               }

               currentRuntimeDescriptor.activateUpdate();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug(": activated update to temp tree " + currentRuntimeDescriptor);
               }
            }
         } catch (Exception var9) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Failure Preparing or activating: ", var9);
            }

            currentRuntimeDescriptor.rollbackUpdate();
         }
      }

      return hasNonDynamicChanges;
   }

   static {
      if (runWithDebugFile) {
         new SituationalConfigDefaultTest();
      }

   }
}
