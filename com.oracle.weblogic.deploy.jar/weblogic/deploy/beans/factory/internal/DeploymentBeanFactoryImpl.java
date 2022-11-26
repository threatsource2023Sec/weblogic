package weblogic.deploy.beans.factory.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.management.InvalidAttributeValueException;
import weblogic.deploy.api.shared.WebLogicModuleTypeUtil;
import weblogic.deploy.beans.factory.DeploymentBeanFactory;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.deploy.common.Debug;
import weblogic.deploy.internal.TargetHelper;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.MBeanConverter;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class DeploymentBeanFactoryImpl implements DeploymentBeanFactory {
   private DomainMBean editableDomain = null;
   private boolean callerOwnsEditLock = false;
   private DomainMBean runtimeDomain = null;
   private DomainMBean alternateEditableDomain;
   private HashMap deployerInitiatedBeanUpdates = new HashMap();
   private static final String LIBRARIES_PROP_NAME = "Libraries";
   private static final String APP_DEPLOYMENTS_PROP_NAME = "AppDeployments";
   private static final String SUB_DEPLOYMENTS_PROP_NAME = "SubDeployments";
   private static final String TARGETS_PROP_NAME = "Targets";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public final AppDeploymentMBean createAppDeploymentMBean(String name, File pkg, DeploymentData data) throws InvalidTargetException, FileNotFoundException, ManagementException {
      String partition = data.getPartition();
      String resourceGroup = data.getResourceGroup();
      String resourceGroupTemplate = data.getResourceGroupTemplate();
      Object du;
      ResourceGroupMBean rgm;
      PartitionMBean pm;
      ResourceGroupTemplateMBean rgtm;
      if (data.isLibrary()) {
         if (resourceGroup != null) {
            rgm = null;
            if (partition != null) {
               pm = this.getEditableDomain().lookupPartition(partition);
               rgm = pm.lookupResourceGroup(resourceGroup);
            } else {
               rgm = this.getEditableDomain().lookupResourceGroup(resourceGroup);
            }

            AppDeploymentMBean lookup = rgm.lookupLibrary(name);
            if (lookup != null && ((AbstractDescriptorBean)lookup)._isTransient()) {
               lookup.setSourcePath(pkg.getPath());
               du = lookup;
            } else {
               du = rgm.createLibrary(name, pkg.getPath());
            }

            this.addBeanUpdate(rgm, "Libraries", 2, du);
         } else if (resourceGroupTemplate != null) {
            rgtm = this.getEditableDomain().lookupResourceGroupTemplate(resourceGroupTemplate);
            du = rgtm.createLibrary(name, pkg.getPath());
            this.addBeanUpdate(rgtm, "Libraries", 2, du);
         } else {
            du = this.getEditableDomain().createLibrary(name, pkg.getPath());
            this.addBeanUpdate(this.getEditableDomain(), "Libraries", 2, du);
         }
      } else {
         try {
            if (resourceGroup != null) {
               rgm = null;
               if (partition != null) {
                  pm = this.getEditableDomain().lookupPartition(partition);
                  rgm = pm.lookupResourceGroup(resourceGroup);
               } else {
                  rgm = this.getEditableDomain().lookupResourceGroup(resourceGroup);
               }

               AppDeploymentMBean lookup = rgm.lookupAppDeployment(name);
               if (lookup != null && ((AbstractDescriptorBean)lookup)._isTransient()) {
                  lookup.setSourcePath(pkg.getPath());
                  du = lookup;
               } else {
                  du = rgm.createAppDeployment(name, pkg.getPath());
               }

               if (data.hasModuleTargets()) {
                  ((AppDeploymentMBean)du).setUntargeted(true);
               }

               this.addBeanUpdate(rgm, "AppDeployments", 2, du);
            } else if (resourceGroupTemplate != null) {
               rgtm = this.getEditableDomain().lookupResourceGroupTemplate(resourceGroupTemplate);
               du = rgtm.createAppDeployment(name, pkg.getPath());
               if (data.hasModuleTargets()) {
                  ((AppDeploymentMBean)du).setUntargeted(true);
               }

               this.addBeanUpdate(rgtm, "AppDeployments", 2, du);
            } else {
               du = this.getEditableDomain().createAppDeployment(name, pkg.getPath());
               this.addBeanUpdate(this.getEditableDomain(), "AppDeployments", 2, du);
            }
         } catch (IllegalArgumentException var11) {
            Throwable caused = var11.getCause();
            if (caused != null) {
               if (caused instanceof ManagementException) {
                  throw (ManagementException)caused;
               }

               throw new ManagementException(caused);
            }

            throw new ManagementException(var11);
         }
      }

      try {
         this.addTargetsInDeploymentData(data, (BasicDeploymentMBean)du);
      } catch (InvalidTargetException var10) {
         AppDeploymentHelper.destroyAppOrLib((AppDeploymentMBean)du, this.getEditableDomain());
         throw var10;
      }

      ((AppDeploymentMBean)du).setModuleType(WebLogicModuleTypeUtil.getFileModuleTypeAsString(pkg));
      initialize((AppDeploymentMBean)du, data);
      return (AppDeploymentMBean)du;
   }

   public final BasicDeploymentMBean addTargetsInDeploymentData(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException, ManagementException {
      this.addGlobalTargetsToDeployable(data, du);
      this.addModuleTargetsToDeployable(data, du);
      this.addSubModuleTargetsToDeployable(data, du);
      return du;
   }

   public final BasicDeploymentMBean removeTargetsInDeploymentData(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      if (data == null) {
         return du;
      } else {
         InvalidTargetException itxSave = null;
         if (!data.isRGOrRGTOperation()) {
            try {
               this.rmGlobalTargets(data, du);
            } catch (InvalidTargetException var6) {
               itxSave = var6;
            }
         }

         this.rmModuleTargets(data, du);
         this.rmSubModuleTargets(data, du);
         boolean propagateToModule = this.rmGlobalTargetsPropagateToModules(data, du);
         boolean propagateToSubModule = this.rmGlobalTargetsPropagateToSubModules(data, du);
         if (!propagateToModule && !propagateToSubModule && null != itxSave) {
            throw itxSave;
         } else {
            return du;
         }
      }
   }

   public final void removeMBean(AppDeploymentMBean d) throws ManagementException {
      DomainMBean domain = this.getEditableDomain();
      if (MBeanConverter.isDebugEnabled()) {
         MBeanConverter.debug("DeploymentBeanFactory: Destroy " + d.getObjectName() + " from " + domain.getObjectName());
      }

      AppDeploymentHelper.destroyAppOrLib(d, domain);
      String propName = d instanceof LibraryMBean ? "Libraries" : "AppDeployments";
      Object parent = d.getParent();
      if (parent instanceof DomainMBean) {
         this.addBeanUpdate(domain, propName, 3, d);
      } else if (parent instanceof ResourceGroupMBean) {
         this.addBeanUpdate((ResourceGroupMBean)parent, propName, 3, d);
      } else if (parent instanceof ResourceGroupTemplateMBean) {
         this.addBeanUpdate((ResourceGroupTemplateMBean)parent, propName, 3, d);
      }

   }

   public final void setEditableDomain(DomainMBean newDomain, boolean callerOwnsEditLock) {
      this.editableDomain = newDomain;
      this.callerOwnsEditLock = callerOwnsEditLock;
   }

   public final void resetEditableDomain() {
      this.editableDomain = null;
      this.callerOwnsEditLock = false;
   }

   public final DomainMBean getEditableDomain() {
      if (this.editableDomain != null) {
         return this.editableDomain;
      } else {
         if (this.runtimeDomain == null) {
            this.runtimeDomain = ManagementService.getRuntimeAccess(kernelId).getDomain();
         }

         return this.runtimeDomain;
      }
   }

   public boolean isDeployerInitiatedBeanUpdate(DescriptorBean source, BeanUpdateEvent.PropertyUpdate update) {
      ArrayList propUpdates = (ArrayList)this.deployerInitiatedBeanUpdates.get(this.getBeanId(source));
      return propUpdates != null && propUpdates.size() != 0 ? this.contains(propUpdates, update) : false;
   }

   public void resetDeployerInitiatedBeanUpdates() {
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentBeanFactoryImpl.resetDeployerInitiatedBeanUpdates");
      }

      this.deployerInitiatedBeanUpdates.clear();
   }

   public synchronized DomainMBean getAlternateEditableDomain() {
      return this.alternateEditableDomain;
   }

   public synchronized void setAlternateEditableDomain(DomainMBean domain) {
      this.alternateEditableDomain = domain;
   }

   public synchronized void resetAlternateEditableDomain() {
      this.alternateEditableDomain = null;
   }

   private void rmSubModuleTargets(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      Map smtargets = data.getAllSubModuleTargets();
      if (data.isStandaloneModule()) {
         this.rmSubTargets(du.getSubDeployments(), (Map)smtargets.get("_the_standalone_module"));
      } else {
         Iterator it = smtargets.keySet().iterator();

         while(it.hasNext()) {
            String module = (String)it.next();
            SubDeploymentMBean mtarget = du.lookupSubDeployment(module);
            if (mtarget == null) {
               throw new InvalidTargetException(module);
            }

            this.rmSubTargets(mtarget.getSubDeployments(), (Map)smtargets.get(module));
         }
      }

   }

   private void rmSubTargets(SubDeploymentMBean[] sdbeans, Map smtarget) throws InvalidTargetException {
      if (smtarget != null && sdbeans != null) {
         Iterator it2 = smtarget.keySet().iterator();

         while(true) {
            int i;
            TargetMBean[] targetsAfterRemove;
            label36:
            do {
               while(it2.hasNext()) {
                  String sdname = (String)it2.next();

                  for(i = 0; sdbeans != null && i < sdbeans.length; ++i) {
                     if (sdbeans[i].getName().equals(sdname)) {
                        this.removeTarget(sdbeans[i], (String[])((String[])smtarget.get(sdname)));
                        targetsAfterRemove = sdbeans[i].getTargets();
                        continue label36;
                     }
                  }
               }

               return;
            } while(targetsAfterRemove != null && targetsAfterRemove.length != 0);

            this.removeSubDeployment(sdbeans[i]);
         }
      }
   }

   private void rmModuleTargets(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      if (!data.isStandaloneModule()) {
         Map mtargets = data.getAllModuleTargets();
         Iterator it = mtargets.keySet().iterator();

         while(true) {
            while(it.hasNext()) {
               String module = (String)it.next();
               SubDeploymentMBean mtbean = du.lookupSubDeployment(module);
               String[] targets = (String[])((String[])mtargets.get(module));
               if (mtbean != null) {
                  if (this.isRGOrRGTOperation(data)) {
                     this.removeRGOrRGTTargets(mtbean, targets, data);
                  } else {
                     this.removeTarget(mtbean, targets);
                  }
               } else {
                  SubDeploymentMBean nuBean = this.findOrCreateSubDeployment(module, du);
                  TargetMBean[] tmbeans = du.getTargets();
                  String[] globalTargets = new String[tmbeans.length];

                  for(int j = 0; tmbeans != null && j < tmbeans.length; ++j) {
                     globalTargets[j] = tmbeans[j].getName();
                  }

                  this.addTargets(nuBean, TargetHelper.lookupTargetMBeans(this.getEditableDomain(), globalTargets));
                  this.removeTarget(nuBean, targets);
               }
            }

            return;
         }
      }
   }

   private boolean rmGlobalTargetsPropagateToModules(DeploymentData data, BasicDeploymentMBean du) {
      boolean rem = false;
      String[] targets = data.getGlobalTargets();
      SubDeploymentMBean[] subDepls = du.getSubDeployments();

      for(int i = 0; i < subDepls.length; ++i) {
         try {
            this.removeTarget(subDepls[i], targets, true);
            rem = true;
         } catch (InvalidTargetException var8) {
         }
      }

      return rem;
   }

   private boolean rmGlobalTargetsPropagateToSubModules(DeploymentData data, BasicDeploymentMBean du) {
      if (data.isStandaloneModule()) {
         return false;
      } else {
         boolean rem = false;
         String[] targets = data.getGlobalTargets();
         SubDeploymentMBean[] subDepls = du.getSubDeployments();

         for(int i = 0; i < subDepls.length; ++i) {
            SubDeploymentMBean[] childSubDepls = subDepls[i].getSubDeployments();

            for(int j = 0; j < childSubDepls.length; ++j) {
               try {
                  this.removeTarget(childSubDepls[j], targets);
                  rem = true;
               } catch (InvalidTargetException var10) {
               }
            }
         }

         return rem;
      }
   }

   private void rmGlobalTargets(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      String[] targets = data.getGlobalTargets();
      this.removeTarget(du, targets);
   }

   private void removeTarget(TargetInfoMBean tbean, String[] rmTargets) throws InvalidTargetException {
      this.removeTarget(tbean, rmTargets, false);
   }

   private void removeTarget(TargetInfoMBean tbean, String[] rmTargets, boolean ignoreNoTarget) throws InvalidTargetException {
      for(int i = 0; rmTargets != null && i < rmTargets.length; ++i) {
         boolean isTarget = ignoreNoTarget;
         String rmTarget = rmTargets[i];
         TargetMBean[] targets = tbean.getTargets();

         for(int j = 0; targets != null && j < targets.length; ++j) {
            if (targets[j].getName().equals(rmTarget)) {
               try {
                  isTarget = true;
                  tbean.removeTarget(targets[j]);
                  this.addBeanUpdate(tbean, "Targets", 3, targets[j]);
               } catch (InvalidAttributeValueException var10) {
                  throw new InvalidTargetException(var10.toString());
               } catch (DistributedManagementException var11) {
                  throw new InvalidTargetException(var11.toString());
               }
            }
         }

         if (!isTarget) {
            throw new InvalidTargetException(rmTarget);
         }
      }

   }

   private void addGlobalTargetsToDeployable(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      if (!this.isRGOrRGTOperation(data)) {
         this.addTargets(du, TargetHelper.lookupTargetMBeans(this.getEditableDomain(), this.getTargetsToAdd(data.getGlobalTargets())));
      }

   }

   private String[] getTargetsToAdd(String[] origTargets) {
      ArrayList targetsToAdd = new ArrayList();
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      String[] var4 = origTargets;
      int var5 = origTargets.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String gt = var4[var6];
         if (!ApplicationUtils.isDynamicClusterServer(gt, domain)) {
            targetsToAdd.add(gt);
         }
      }

      return (String[])((String[])targetsToAdd.toArray(new String[targetsToAdd.size()]));
   }

   private void addModuleTargetsToDeployable(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      Map mtargets = data.getAllModuleTargets();
      Iterator it = mtargets.keySet().iterator();

      while(it.hasNext()) {
         String moduleName = (String)it.next();
         String[] targetNames = (String[])((String[])mtargets.get(moduleName));
         if (targetNames != null) {
            SubDeploymentMBean mtbean = this.findOrCreateSubDeployment(moduleName, du);
            if (this.isRGOrRGTOperation(data)) {
               this.addRGOrRGTTargets(mtbean, targetNames, data);
            } else {
               this.addTargets(mtbean, TargetHelper.lookupTargetMBeans(this.getEditableDomain(), targetNames));
            }
         }
      }

   }

   private SubDeploymentMBean findOrCreateSubDeployment(String sdname, BasicDeploymentMBean du) {
      SubDeploymentMBean mod = du.lookupSubDeployment(sdname);
      if (mod == null) {
         mod = du.createSubDeployment(sdname);
         this.addBeanUpdate(du, "SubDeployments", 2, mod);
      }

      return mod;
   }

   private boolean isRGOrRGTOperation(DeploymentData data) {
      return data.getPartition() != null || data.getResourceGroup() != null || data.getResourceGroupTemplate() != null;
   }

   private void addRGOrRGTTargets(SubDeploymentMBean subD, String[] targets, DeploymentData data) {
      boolean foundRGOrRGTargets = false;
      boolean foundJMSServerOrSAFAgentTargets = false;
      int i = 0;

      try {
         TargetMBean[] targetMBeans = TargetHelper.lookupTargetMBeansFromDD(this.getEditableDomain(), targets, data);

         label58:
         while(true) {
            TargetMBean targetMBean;
            do {
               if (foundJMSServerOrSAFAgentTargets || i >= targetMBeans.length) {
                  if (foundJMSServerOrSAFAgentTargets) {
                     this.addTargets(subD, targetMBeans);
                     return;
                  }
                  break label58;
               }

               targetMBean = targetMBeans[i++];
            } while(!(targetMBean instanceof JMSServerMBean) && !(targetMBean instanceof SAFAgentMBean));

            foundJMSServerOrSAFAgentTargets = true;
         }
      } catch (InvalidTargetException var9) {
      }

      String target;
      for(i = 0; !foundRGOrRGTargets && i < targets.length; foundRGOrRGTargets = "resourceGroup".equals(target) || "resourceGroupTemplate".equals(target)) {
         target = targets[i++];
      }

      subD.setUntargeted(!foundRGOrRGTargets);
   }

   private void removeRGOrRGTTargets(SubDeploymentMBean subD, String[] targets, DeploymentData data) {
      boolean foundRGOrRGTargets = false;
      boolean foundJMSServerOrSAFAgentTargets = false;
      int i = 0;

      try {
         TargetMBean[] targetMBeans = TargetHelper.lookupTargetMBeansFromDD(this.getEditableDomain(), targets, data);

         label53:
         while(true) {
            TargetMBean targetMBean;
            do {
               if (foundJMSServerOrSAFAgentTargets || i >= targetMBeans.length) {
                  if (foundJMSServerOrSAFAgentTargets) {
                     this.removeTarget(subD, targets);
                     return;
                  }
                  break label53;
               }

               targetMBean = targetMBeans[i++];
            } while(!(targetMBean instanceof JMSServerMBean) && !(targetMBean instanceof SAFAgentMBean));

            foundJMSServerOrSAFAgentTargets = true;
         }
      } catch (InvalidTargetException var9) {
      }

      String target;
      for(i = 0; !foundRGOrRGTargets && i < targets.length; foundRGOrRGTargets = "resourceGroup".equals(target) || "resourceGroupTemplate".equals(target)) {
         target = targets[i++];
      }

      subD.setUntargeted(foundRGOrRGTargets);
   }

   private void addTargets(TargetInfoMBean tbean, TargetMBean[] targetMBeans) throws InvalidTargetException {
      TargetMBean[] currentTargets = tbean.getTargets();

      for(int i = 0; i < targetMBeans.length; ++i) {
         TargetMBean targetMBean = targetMBeans[i];
         if (isNewTarget(currentTargets, targetMBean)) {
            boolean skipTarget = false;

            try {
               DomainMBean domain = this.getEditableDomain();
               ServerMBean server = domain.lookupServer(targetMBean.getName());
               if (server != null) {
                  ClusterMBean cluster = server.getCluster();
                  if (cluster != null && !isNewTarget(currentTargets, cluster)) {
                     skipTarget = true;
                  }
               }

               if (!skipTarget) {
                  tbean.addTarget(targetMBean);
                  this.addBeanUpdate(tbean, "Targets", 2, targetMBean);
               }
            } catch (InvalidAttributeValueException var10) {
               throw new InvalidTargetException(var10.toString());
            } catch (DistributedManagementException var11) {
               throw new InvalidTargetException(var11.toString());
            }
         }
      }

   }

   private static boolean isNewTarget(TargetMBean[] currentTargets, TargetMBean targetMBean) {
      if (currentTargets == null) {
         return false;
      } else {
         for(int i = 0; i < currentTargets.length; ++i) {
            TargetMBean currentTarget = currentTargets[i];
            if (currentTarget.getName().equals(targetMBean.getName())) {
               return false;
            }
         }

         return true;
      }
   }

   private void addSubModuleTargetsToDeployable(DeploymentData data, BasicDeploymentMBean du) throws InvalidTargetException {
      Map smtargetmap = data.getAllSubModuleTargets();
      if (!smtargetmap.isEmpty()) {
         Iterator it = smtargetmap.keySet().iterator();

         while(it.hasNext()) {
            String modname = (String)it.next();
            Map smtargets = (Map)smtargetmap.get(modname);
            Iterator it2 = smtargets.keySet().iterator();

            while(it2.hasNext()) {
               String smodname = (String)it2.next();
               String[] targetNames = (String[])((String[])smtargets.get(smodname));
               SubDeploymentMBean s;
               if (modname.equals("_the_standalone_module")) {
                  s = this.findOrCreateSubDeployment(smodname, du);
               } else {
                  s = this.findOrCreateSubSubDeployment(du, modname, smodname);
               }

               if (targetNames != null) {
                  if (this.isRGOrRGTOperation(data)) {
                     this.addRGOrRGTTargets(s, targetNames, data);
                  } else {
                     this.addTargets(s, TargetHelper.lookupTargetMBeans(this.getEditableDomain(), targetNames));
                  }
               }
            }
         }

      }
   }

   private SubDeploymentMBean findOrCreateSubSubDeployment(BasicDeploymentMBean d, String modname, String smodname) {
      SubDeploymentMBean mod = d.lookupSubDeployment(modname);
      SubDeploymentMBean smod;
      if (mod == null) {
         mod = d.createSubDeployment(modname);
         smod = mod.createSubDeployment(smodname);
         this.addBeanUpdate(mod, "SubDeployments", 2, smod);
      } else {
         smod = mod.lookupSubDeployment(smodname);
         if (smod == null) {
            smod = mod.createSubDeployment(smodname);
            this.addBeanUpdate(mod, "SubDeployments", 2, smod);
         }
      }

      return smod;
   }

   private static void initialize(AppDeploymentMBean du, DeploymentData data) {
      if (data.getDeploymentPlan() != null) {
         if (data.getConfigDirectory() != null) {
            File f = new File(data.getConfigDirectory());
            String[] s = f.list();
            if (s != null && s.length > 0) {
               du.setPlanDir(data.getConfigDirectory());
            }
         }

         du.setPlanPath(data.getDeploymentPlan());
      }

      if (data.getAltDescriptorPath() != null) {
         du.setAltDescriptorPath(data.getAltDescriptorPath());
      }

      if (data.getAltWLSDescriptorPath() != null) {
         du.setAltWLSDescriptorPath(data.getAltWLSDescriptorPath());
      }

      if (data.isSecurityValidationEnabled()) {
         du.setValidateDDSecurityData(true);
      }

      if (data.getSecurityModel() != null) {
         du.setSecurityDDModel(data.getSecurityModel());
      }

      if (du.getVersionIdentifier() != null) {
         AppDeploymentMBean sourceDMB = ApplicationUtils.getActiveAppDeployment(du.getApplicationName());
         if (sourceDMB != null) {
            du.setSecurityDDModel(sourceDMB.getSecurityDDModel());
            if (sourceDMB.isValidateDDSecurityData() != du.isValidateDDSecurityData()) {
               du.setValidateDDSecurityData(sourceDMB.isValidateDDSecurityData());
            }
         }
      }

      if (data.getDeploymentPrincipalName() != null) {
         du.setDeploymentPrincipalName(data.getDeploymentPrincipalName());
      }

   }

   private void addBeanUpdate(DescriptorBean source, String propertyName, int updateType, Object addedOrRemoved) {
      if (this.callerOwnsEditLock) {
         String beanId = this.getBeanId(source);
         ArrayList propUpdates = (ArrayList)this.deployerInitiatedBeanUpdates.get(beanId);
         if (propUpdates == null) {
            propUpdates = new ArrayList();
            this.deployerInitiatedBeanUpdates.put(beanId, propUpdates);
         }

         Iterator iter = propUpdates.iterator();

         BeanUpdateEvent.PropertyUpdate update;
         do {
            do {
               if (!iter.hasNext()) {
                  BeanUpdateEvent.PropertyUpdate propUpdate = new BeanUpdateEvent.PropertyUpdate(propertyName, updateType, addedOrRemoved, true, false, false);
                  if (Debug.isDeploymentDebugEnabled()) {
                     Debug.deploymentDebug("DeploymentBeanFactoryImpl.addPropertyUpdate  source=" + source + ", update=" + propUpdate);
                  }

                  propUpdates.add(propUpdate);
                  return;
               }

               update = (BeanUpdateEvent.PropertyUpdate)iter.next();
            } while(!propertyName.equals(update.getPropertyName()));
         } while((updateType != 2 || update.getUpdateType() != 3 || !addedOrRemoved.equals(update.getAddedObject())) && (updateType != 3 || update.getUpdateType() != 2 || !addedOrRemoved.equals(update.getRemovedObject())));

         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentBeanFactoryImpl.removePropertyUpdate " + update);
         }

         iter.remove();
      }
   }

   private boolean contains(ArrayList propUpdates, BeanUpdateEvent.PropertyUpdate update) {
      String propName = update.getPropertyName();
      int updateType = update.getUpdateType();
      if (Debug.isDeploymentDebugEnabled()) {
         Debug.deploymentDebug("DeploymentBeanFactoryImpl.contains deployerUpdates=" + propUpdates + ", update=" + update);
      }

      Iterator iter = propUpdates.iterator();

      BeanUpdateEvent.PropertyUpdate curUpdate;
      do {
         do {
            do {
               if (!iter.hasNext()) {
                  return false;
               }

               curUpdate = (BeanUpdateEvent.PropertyUpdate)iter.next();
            } while(!propName.equals(curUpdate.getPropertyName()));
         } while(updateType != curUpdate.getUpdateType());
      } while((updateType != 2 || !this.isEqualBean(update.getAddedObject(), curUpdate.getAddedObject())) && (updateType != 3 || !this.isEqualBean(update.getRemovedObject(), curUpdate.getRemovedObject())));

      return true;
   }

   private String getBeanId(DescriptorBean bean) {
      return bean instanceof WebLogicMBean ? bean.getClass().getName() + ":" + ((WebLogicMBean)bean).getName() : bean.toString();
   }

   private boolean isEqualBean(Object o1, Object o2) {
      if (o1 instanceof WebLogicMBean && o2 instanceof WebLogicMBean) {
         return o1.getClass().equals(o2.getClass()) && ((WebLogicMBean)o1).getName().equals(((WebLogicMBean)o2).getName());
      } else {
         return o1.equals(o2);
      }
   }

   private void removeSubDeployment(SubDeploymentMBean subDeployment) {
      WebLogicMBean parentBean = subDeployment.getParent();
      if (parentBean instanceof AppDeploymentMBean) {
         AppDeploymentMBean parentApp = (AppDeploymentMBean)parentBean;
         parentApp.destroySubDeployment(subDeployment);
         this.addBeanUpdate(parentApp, "SubDeployments", 3, subDeployment);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentBeanFactoryImpl.removeSubDeployment('" + subDeployment + "') from '" + parentApp + "'");
         }
      } else if (parentBean instanceof SubDeploymentMBean) {
         SubDeploymentMBean parentApp = (SubDeploymentMBean)parentBean;
         parentApp.destroySubDeployment(subDeployment);
         this.addBeanUpdate(parentApp, "SubDeployments", 3, subDeployment);
         if (Debug.isDeploymentDebugEnabled()) {
            Debug.deploymentDebug("DeploymentBeanFactoryImpl.removeSubDeployment('" + subDeployment + "') from '" + parentApp + "'");
         }

         SubDeploymentMBean[] subDepsLeft = parentApp.getSubDeployments();
         if (subDepsLeft == null || subDepsLeft.length == 0) {
            this.removeSubDeployment(parentApp);
         }
      }

   }
}
