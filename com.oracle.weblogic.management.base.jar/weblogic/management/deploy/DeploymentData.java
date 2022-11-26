package weblogic.management.deploy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.beans.factory.InvalidTargetException;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WebDeploymentMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.LocatorUtilities;

/** @deprecated */
@Deprecated
public class DeploymentData implements Serializable {
   private static final long serialVersionUID = -1065644178659248034L;
   public static final int UNKNOWN = 0;
   public static final int CLUSTER = 1;
   public static final int SERVER = 2;
   public static final int VIRTUALHOST = 3;
   public static final int JMSSERVER = 4;
   public static final int SAFAGENT = 5;
   public static final int VIRTUALTARGET = 6;
   private String[] files;
   private boolean[] isDirectory;
   private HashMap targets = null;
   private boolean deleteFlag = false;
   private boolean isNameFromSource = false;
   private boolean isNewApp = false;
   private boolean isActionFromDeployer = false;
   private String plan = null;
   private String root = null;
   private String config = null;
   private DeploymentOptions deployOpts = new DeploymentOptions();
   private ArrayList globaltargets = new ArrayList();
   private Map moduleTargets = new HashMap();
   private Map allSubModuleTargets = new HashMap();
   public static final String STANDALONE_MODULE = "_the_standalone_module";
   private boolean standaloneModule = false;
   private boolean earmodule = false;
   private boolean planUpdate = false;
   private boolean targetsFromConfig = false;
   private String intendedState;
   private boolean remote = false;
   private boolean thinClient = false;
   private static TargetHelperInterface targetHelper = (TargetHelperInterface)LocatorUtilities.getService(TargetHelperInterface.class);
   private static ApplicationUtilsInterface applicationUtils = (ApplicationUtilsInterface)LocatorUtilities.getService(ApplicationUtilsInterface.class);
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean enforceClusterConstraints = false;
   private int timeOut = 3600000;
   private String appDD = null;
   private String webDD = null;
   private String deploymentPrincipalName = null;
   private String partition = null;
   private String resourceGroup = null;
   private String resourceGroupTemplate = null;

   public DeploymentData copy() {
      DeploymentData copiedData = new DeploymentData();
      copiedData.setFile(this.files);
      copiedData.isDirectory = this.isDirectory;
      copiedData.deleteFlag = this.deleteFlag;
      copiedData.isNameFromSource = this.isNameFromSource;
      copiedData.isNewApp = this.isNewApp;
      copiedData.isActionFromDeployer = this.isActionFromDeployer;
      copiedData.plan = this.plan;
      copiedData.root = this.root;
      copiedData.config = this.config;
      copiedData.deployOpts = this.deployOpts;
      copiedData.globaltargets.addAll(this.globaltargets);
      copiedData.moduleTargets.putAll(this.moduleTargets);
      copiedData.allSubModuleTargets.putAll(this.allSubModuleTargets);
      copiedData.standaloneModule = this.standaloneModule;
      copiedData.earmodule = this.earmodule;
      copiedData.planUpdate = this.planUpdate;
      copiedData.targetsFromConfig = this.targetsFromConfig;
      copiedData.intendedState = this.intendedState;
      copiedData.remote = this.remote;
      copiedData.thinClient = this.thinClient;
      return copiedData;
   }

   public boolean isTargetsFromConfig() {
      return this.targetsFromConfig;
   }

   public void setTargetsFromConfig(boolean targetsFromConfig) {
      this.targetsFromConfig = targetsFromConfig;
   }

   public DeploymentData() {
      this.files = null;
   }

   public DeploymentData(String[] files) {
      this.files = files;
   }

   public boolean usesNonExclusiveLock() {
      return this.getDeploymentOptions() != null ? this.getDeploymentOptions().usesNonExclusiveLock() : false;
   }

   public boolean isPlanUpdate() {
      return this.planUpdate;
   }

   public void setPlanUpdate(boolean b) {
      this.planUpdate = b;
   }

   public boolean hasTargets() {
      return !this.globaltargets.isEmpty() || !this.moduleTargets.isEmpty() || !this.allSubModuleTargets.isEmpty();
   }

   public String[] getGlobalTargets() {
      return (String[])((String[])this.globaltargets.toArray(new String[this.globaltargets.size()]));
   }

   public boolean hasGlobalTarget(String targetname) {
      return this.globaltargets.contains(targetname);
   }

   public void addGlobalTarget(String targetName) {
      if (!this.hasGlobalTarget(targetName)) {
         this.globaltargets.add(targetName);
      }

   }

   public void removeGlobalTarget(String targetName) {
      if (this.hasGlobalTarget(targetName)) {
         this.globaltargets.remove(targetName);
      }

   }

   public void addGlobalTargets(String[] givenTargets) {
      if (givenTargets != null && givenTargets.length != 0) {
         this.addGlobalTargets(Arrays.asList(givenTargets));
      }
   }

   public void addGlobalTargets(List givenTargets) {
      if (givenTargets != null && !givenTargets.isEmpty()) {
         Iterator iter = givenTargets.iterator();

         while(iter.hasNext()) {
            this.addGlobalTarget((String)iter.next());
         }

      }
   }

   public void setGlobalTargets(String[] targetNames) {
      this.globaltargets = new ArrayList(targetNames.length);

      for(int i = 0; i < targetNames.length; ++i) {
         this.globaltargets.add(targetNames[i]);
      }

   }

   public void addOrUpdateModuleTargets(Map givenMTs) {
      if (givenMTs != null && !givenMTs.isEmpty()) {
         Iterator givenEntries = givenMTs.entrySet().iterator();

         while(givenEntries.hasNext()) {
            Map.Entry eachEntry = (Map.Entry)givenEntries.next();
            this.addModuleTargets((String)eachEntry.getKey(), (String[])((String[])eachEntry.getValue()));
         }

      }
   }

   public Map getAllModuleTargets() {
      return this.moduleTargets;
   }

   public boolean hasModuleTargets() {
      return !this.moduleTargets.isEmpty();
   }

   public String[] getModuleTargets(String moduleName) {
      return (String[])((String[])this.moduleTargets.get(moduleName));
   }

   public void addModuleTarget(String moduleName, String newtarget) {
      if (newtarget != null && moduleName != null) {
         String[] curtargets = (String[])((String[])this.moduleTargets.get(moduleName));
         String[] result;
         if (curtargets == null) {
            result = new String[]{newtarget};
         } else {
            result = this.merge(curtargets, new String[]{newtarget});
         }

         this.moduleTargets.put(moduleName, result);
      } else {
         throw new IllegalArgumentException("Parameters must not be null");
      }
   }

   public void addModuleTargets(String moduleName, String[] newtargets) {
      if (newtargets != null && newtargets.length != 0 && moduleName != null) {
         for(int i = 0; i < newtargets.length; ++i) {
            this.addModuleTarget(moduleName, newtargets[i]);
         }

      } else {
         throw new IllegalArgumentException("No module targets to add.");
      }
   }

   public boolean isStandaloneModule() {
      return this.standaloneModule;
   }

   public void addSubModuleTarget(String moduleName, String subModuleName, String[] newtargets) throws IllegalArgumentException {
      if (newtargets != null && newtargets.length != 0 && subModuleName != null) {
         moduleName = this.validateModuleName(moduleName);
         subModuleName = this.validateSubModuleName(subModuleName);
         Map smodtargets = (Map)this.allSubModuleTargets.get(moduleName);
         if (smodtargets == null) {
            smodtargets = new HashMap();
            ((Map)smodtargets).put(subModuleName, newtargets);
         } else {
            String[] curtargets = (String[])((String[])((Map)smodtargets).get(subModuleName));
            if (curtargets == null) {
               ((Map)smodtargets).put(subModuleName, newtargets);
            } else {
               ((Map)smodtargets).put(subModuleName, this.merge(curtargets, newtargets));
            }
         }

         this.allSubModuleTargets.put(moduleName, smodtargets);
      } else {
         throw new IllegalArgumentException("Parameters must not be null");
      }
   }

   public boolean isSubModuleTargeted(String moduleName, String subModuleName) {
      moduleName = this.validateModuleName(moduleName);
      Map smodtargets = (Map)this.allSubModuleTargets.get(moduleName);
      if (smodtargets == null) {
         return false;
      } else {
         String[] curtargets = (String[])((String[])smodtargets.get(this.validateSubModuleName(subModuleName)));
         return curtargets != null;
      }
   }

   public void addOrUpdateSubModuleTargetsFor(String moduleName, Map submodules) {
      if (submodules != null && !submodules.isEmpty()) {
         Iterator allEntries = submodules.entrySet().iterator();

         while(allEntries.hasNext()) {
            Map.Entry eachEntry = (Map.Entry)allEntries.next();
            String submodule = (String)eachEntry.getKey();
            String[] subTargets = (String[])((String[])eachEntry.getValue());
            this.addSubModuleTarget(moduleName, submodule, subTargets);
         }

      }
   }

   public void addOrUpdateSubModuleTargets(Map givenSMTs) {
      if (givenSMTs != null && !givenSMTs.isEmpty()) {
         Iterator givenEntries = givenSMTs.entrySet().iterator();

         while(givenEntries.hasNext()) {
            Map.Entry eachEntry = (Map.Entry)givenEntries.next();
            String eachModule = (String)eachEntry.getKey();
            Map eachSubModules = (Map)eachEntry.getValue();
            this.addOrUpdateSubModuleTargetsFor(eachModule, eachSubModules);
         }

      }
   }

   public Set getAllTargetedServers(Set logicalTargets) throws InvalidTargetException {
      Set result = new HashSet();
      DomainMBean d = ManagementService.getRuntimeAccess(kernelId).getDomain();
      TargetMBean[] tm = targetHelper.lookupTargetMBeans(d, (String[])((String[])logicalTargets.toArray(new String[logicalTargets.size()])), this.getPartition());

      for(int i = 0; tm != null && i < tm.length; ++i) {
         result.addAll(tm[i].getServerNames());
      }

      return result;
   }

   public Set getAllTargetedServers(Set logicalTargets, DomainMBean editDomain) throws InvalidTargetException {
      Set result = new HashSet();
      DomainMBean d = ManagementService.getRuntimeAccess(kernelId).getDomain();
      boolean inEditDomain = true;

      TargetMBean[] tm;
      try {
         tm = targetHelper.lookupTargetMBeans(editDomain, (String[])((String[])logicalTargets.toArray(new String[logicalTargets.size()])), this.getPartition());
      } catch (InvalidTargetException var17) {
         inEditDomain = false;
         tm = targetHelper.lookupTargetMBeans(d, (String[])((String[])logicalTargets.toArray(new String[logicalTargets.size()])), this.getPartition());
      }

      int i;
      for(i = 0; tm != null && i < tm.length; ++i) {
         result.addAll(tm[i].getServerNames());
      }

      if (inEditDomain) {
         try {
            tm = targetHelper.lookupTargetMBeans(d, (String[])((String[])logicalTargets.toArray(new String[logicalTargets.size()])));

            for(i = 0; tm != null && i < tm.length; ++i) {
               result.addAll(tm[i].getServerNames());
            }
         } catch (InvalidTargetException var18) {
            Set targets = new HashSet();
            Iterator var9 = logicalTargets.iterator();

            label55:
            while(true) {
               TargetMBean targetMBean;
               do {
                  if (!var9.hasNext()) {
                     tm = targetHelper.lookupTargetMBeans(d, (String[])((String[])targets.toArray(new String[targets.size()])));

                     for(int i = 0; tm != null && i < tm.length; ++i) {
                        result.addAll(tm[i].getServerNames());
                     }
                     break label55;
                  }

                  Object target = var9.next();
                  targetMBean = editDomain.lookupInAllTargets((String)target);
               } while(!(targetMBean instanceof VirtualTargetMBean));

               TargetMBean[] vtTargets = ((VirtualTargetMBean)targetMBean).getTargets();
               TargetMBean[] var13 = vtTargets;
               int var14 = vtTargets.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  TargetMBean vtTarget = var13[var15];
                  targets.add(vtTarget.getName());
               }
            }
         }
      }

      return result;
   }

   public Set getAllLogicalTargets() {
      Set result = new HashSet();
      result.addAll(this.globaltargets);
      Iterator it = this.moduleTargets.keySet().iterator();

      while(it.hasNext()) {
         String mod = (String)it.next();
         String[] targets = (String[])((String[])this.moduleTargets.get(mod));
         result.addAll(Arrays.asList((Object[])targets));
      }

      it = this.allSubModuleTargets.values().iterator();

      while(it.hasNext()) {
         Map stargets = (Map)it.next();
         Iterator it2 = stargets.values().iterator();

         while(it2.hasNext()) {
            String[] targets = (String[])((String[])it2.next());
            result.addAll(Arrays.asList((Object[])targets));
         }
      }

      return result;
   }

   private String[] merge(String[] curtargets, String[] newtargets) {
      Set mergedSet = new HashSet(Arrays.asList(curtargets));
      mergedSet.addAll(Arrays.asList(newtargets));
      String[] result = new String[mergedSet.size()];
      result = (String[])((String[])mergedSet.toArray(result));
      return result;
   }

   private String validateModuleName(String moduleName) throws IllegalArgumentException {
      if (moduleName == null) {
         if (this.earmodule) {
            throw new IllegalArgumentException(DeployerRuntimeLogger.logAppSubModuleTargetErr());
         }

         this.standaloneModule = true;
         moduleName = "_the_standalone_module";
      } else if (this.standaloneModule) {
         moduleName = "_the_standalone_module";
      } else {
         this.earmodule = true;
      }

      return moduleName;
   }

   private String validateSubModuleName(String subModuleName) {
      return subModuleName.trim();
   }

   public Map getSubModuleTargets(String moduleName) {
      return (Map)this.allSubModuleTargets.get(moduleName);
   }

   public Map getAllSubModuleTargets() {
      return this.allSubModuleTargets;
   }

   public boolean hasSubModuleTargets() {
      return !this.allSubModuleTargets.isEmpty();
   }

   public void addTargetsFromConfig(BasicDeploymentMBean dmb) {
      if (applicationUtils.isDeploymentScopedToResourceGroupOrTemplate(this)) {
         this.globaltargets.clear();
         this.addGlobalTargets(applicationUtils.getActualTargets(dmb));
      } else {
         this.addGlobalTargets(dmb.getTargets());
      }

      SubDeploymentMBean[] mtargets = dmb.getSubDeployments();

      for(int i = 0; mtargets != null && i < mtargets.length; ++i) {
         TargetMBean[] targets = mtargets[i].getTargets();
         if (targets != null && targets.length > 0) {
            this.addModuleTargets(mtargets[i].getName(), this.getNames(targets));
         }

         this.addSubModTargetsFromConfig(mtargets[i].getSubDeployments(), mtargets[i].getName());
      }

      this.setTargetsFromConfig(true);
   }

   private void addSubModTargetsFromConfig(TargetInfoMBean[] smtbeans, String modname) {
      for(int j = 0; smtbeans != null && j < smtbeans.length; ++j) {
         TargetMBean[] stargets = smtbeans[j].getTargets();
         if (stargets != null && stargets.length > 0) {
            this.addSubModuleTarget(modname, smtbeans[j].getName(), this.getNames(stargets));
         }
      }

   }

   private String[] getNames(TargetMBean[] targets) {
      if (targets == null) {
         return null;
      } else {
         String[] result = new String[targets.length];

         for(int i = 0; i < targets.length; ++i) {
            result[i] = targets[i].getName();
         }

         return result;
      }
   }

   private void addGlobalTargets(TargetMBean[] targets) {
      for(int i = 0; targets != null && i < targets.length; ++i) {
         TargetMBean target = targets[i];
         this.addGlobalTarget(target.getName());
      }

   }

   public String getSecurityModel() {
      return this.deployOpts.getSecurityModel();
   }

   public void setSecurityModel(String model) throws IllegalArgumentException {
      this.deployOpts.setSecurityModel(model);
   }

   public boolean isSecurityValidationEnabled() {
      return this.deployOpts.isSecurityValidationEnabled();
   }

   public void setSecurityValidationEnabled(boolean enable) {
      this.deployOpts.setSecurityValidationEnabled(enable);
   }

   public boolean isLibrary() {
      return this.deployOpts.isLibrary();
   }

   public void setLibrary(boolean isLibrary) {
      this.deployOpts.setLibrary(isLibrary);
   }

   public void setFile(String[] files) {
      this.files = files;
   }

   public void addFiles(String[] givenFiles) {
      HashSet currentFiles = new HashSet();
      if (this.files != null) {
         currentFiles.addAll(Arrays.asList(this.files));
      }

      for(int i = 0; i < givenFiles.length; ++i) {
         currentFiles.add(givenFiles[i]);
      }

      this.files = new String[currentFiles.size()];
      this.files = (String[])((String[])currentFiles.toArray(this.files));
   }

   public void setDelete(boolean deleteFlag) {
      this.deleteFlag = deleteFlag;
   }

   public boolean getDelete() {
      return this.deleteFlag;
   }

   public void setClusterConstraints(boolean clusterConstraints) {
      this.enforceClusterConstraints = clusterConstraints;
   }

   public boolean getClusterConstraints() {
      return this.enforceClusterConstraints;
   }

   public int getTimeOut() {
      return this.timeOut;
   }

   public void setTimeOut(int timeInMillis) {
      this.timeOut = timeInMillis;
   }

   public void setAltDescriptorPath(String dd) {
      this.appDD = dd;
   }

   public String getAltDescriptorPath() {
      if (this.appDD == null && this.getDeploymentOptions() != null) {
         this.appDD = this.getDeploymentOptions().getAltDD();
      }

      return this.appDD;
   }

   public void setAltWLSDescriptorPath(String dd) {
      this.webDD = dd;
   }

   public String getAltWLSDescriptorPath() {
      if (this.webDD == null && this.getDeploymentOptions() != null) {
         this.webDD = this.getDeploymentOptions().getAltWlsDD();
      }

      return this.webDD;
   }

   public void setDeploymentPrincipalName(String principal) {
      this.deploymentPrincipalName = principal;
   }

   public String getDeploymentPrincipalName() {
      if (this.deploymentPrincipalName == null && this.getDeploymentOptions() != null) {
         this.deploymentPrincipalName = this.getDeploymentOptions().getDeploymentPrincipalName();
      }

      return this.deploymentPrincipalName;
   }

   public void setPartition(String par) {
      this.partition = par;
   }

   public String getPartition() {
      if (this.partition == null && this.getDeploymentOptions() != null) {
         this.partition = this.getDeploymentOptions().getPartition();
      }

      return this.partition;
   }

   public void setResourceGroup(String rgp) {
      this.resourceGroup = rgp;
   }

   public String getResourceGroup() {
      if (this.resourceGroup == null && this.getDeploymentOptions() != null) {
         this.resourceGroup = this.getDeploymentOptions().getResourceGroup();
      }

      return this.resourceGroup;
   }

   public void setResourceGroupTemplate(String rgp) {
      this.resourceGroup = rgp;
   }

   public String getResourceGroupTemplate() {
      if (this.resourceGroupTemplate == null && this.getDeploymentOptions() != null) {
         this.resourceGroupTemplate = this.getDeploymentOptions().getResourceGroupTemplate();
      }

      return this.resourceGroupTemplate;
   }

   public boolean getSpecifiedTargetsOnly() {
      return this.getDeploymentOptions() != null ? this.getDeploymentOptions().getSpecifiedTargetsOnly() : false;
   }

   public String[] getSpecifiedModules() {
      return this.getDeploymentOptions() != null ? this.getDeploymentOptions().getSpecifiedModules() : null;
   }

   public String[] getFiles() {
      return this.files;
   }

   public boolean hasFiles() {
      return this.files != null && this.files.length > 0;
   }

   public int getTargetType(String target) {
      return targetHelper.getTypeForTarget(target);
   }

   public void setTargetType(String target, int type) {
   }

   public boolean getIsNameFromSource() {
      return this.isNameFromSource;
   }

   public void setIsNameFromSource(boolean isFromSource) {
      this.isNameFromSource = isFromSource;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Delete Files:" + this.getDelete() + "\n");
      sb.append("Timeout :" + this.getTimeOut() + "\n");
      sb.append("Targets: \n");
      if (this.globaltargets != null) {
         Iterator targs = this.globaltargets.iterator();

         while(targs.hasNext()) {
            sb.append((String)targs.next());
            sb.append("\n");
         }
      }

      sb.append(this.getModuleTargetsAsString());
      sb.append(this.getSubModuleTargetsAsString());
      sb.append("Files: \n");
      String[] fl = this.getFiles();
      if (fl != null) {
         for(int i = 0; i < fl.length; ++i) {
            sb.append(fl[i]);
            if (this.isDirectory != null) {
               sb.append(" - " + (this.isDirectory[i] ? "Directory" : "File"));
            }

            sb.append("\n");
         }
      } else {
         sb.append("null\n");
      }

      sb.append("Deployment Plan: ");
      sb.append(this.plan);
      sb.append("\n");
      sb.append("App root: ").append(this.root).append("\n");
      sb.append("App config: ").append(this.config).append("\n");
      sb.append("Partition: ").append(this.partition).append("\n");
      sb.append("ResourceGroup: ").append(this.resourceGroup).append("\n");
      sb.append("Deployment Options: ").append(this.deployOpts).append("\n");
      return sb.toString();
   }

   private String getSubModuleTargetsAsString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SubModuleTargets=");
      sb.append("{");
      if (this.allSubModuleTargets.isEmpty()) {
         sb.append("}");
      } else {
         Iterator iter = this.allSubModuleTargets.entrySet().iterator();

         while(iter.hasNext()) {
            sb.append("\n");
            Map.Entry each = (Map.Entry)iter.next();
            sb.append(each.getKey()).append("=");
            sb.append(toStringOfModuleTargetsOrSubmoduleTargetsMap((Map)each.getValue())).append(", ");
         }
      }

      sb.append("\n").append("}").append("\n");
      return sb.toString();
   }

   private String getModuleTargetsAsString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModuleTargets=");
      sb.append(toStringOfModuleTargetsOrSubmoduleTargetsMap(this.moduleTargets));
      sb.append("\n");
      return sb.toString();
   }

   private static String toStringOfModuleTargetsOrSubmoduleTargetsMap(Map given) {
      StringBuffer sb = new StringBuffer();
      if (given.isEmpty()) {
         sb.append(given);
      } else {
         Iterator iter = given.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry each = (Map.Entry)iter.next();
            sb.append(each.getKey()).append("=");
            sb.append(getStringArrayAsString((String[])((String[])each.getValue())));
            sb.append(", ");
         }
      }

      return sb.toString();
   }

   private static String getStringArrayAsString(String[] givenArray) {
      StringBuffer sb = new StringBuffer();
      sb.append("{");
      if (givenArray != null && givenArray.length != 0) {
         for(int i = 0; i < givenArray.length; ++i) {
            if (i != 0) {
               sb.append(", ");
            }

            sb.append(givenArray[i]);
         }

         sb.append("}");
         return sb.toString();
      } else {
         sb.append("}");
         return sb.toString();
      }
   }

   public boolean isNewApplication() {
      return this.isNewApp;
   }

   public void setNewApp(boolean isNew) {
      this.isNewApp = isNew;
   }

   public boolean isActionFromDeployer() {
      return this.isActionFromDeployer;
   }

   public void setActionFromDeployer(boolean isDeployer) {
      this.isActionFromDeployer = isDeployer;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      if (this.globaltargets == null) {
         this.globaltargets = new ArrayList();
      }

      if (this.moduleTargets == null) {
         this.moduleTargets = new HashMap();
      }

      if (this.allSubModuleTargets == null) {
         this.allSubModuleTargets = new HashMap();
      }

      if (this.deployOpts == null) {
         this.deployOpts = new DeploymentOptions();
      }

      if (this.targets != null && !this.targets.isEmpty()) {
         this.populateFromTargetInfos();
      }

   }

   private void populateFromTargetInfos() {
      Debug.assertion(this.targets != null);
      Iterator targetsFromClient = this.targets.keySet().iterator();

      while(targetsFromClient.hasNext()) {
         String target = (String)targetsFromClient.next();
         TargetInfo tInfo = (TargetInfo)this.targets.get(target);
         String[] modules = tInfo.getModules();
         if (modules == null) {
            this.addGlobalTarget(target);
         } else {
            this.addTargetToModules(modules, target);
         }
      }

   }

   public String getDeploymentPlan() {
      return this.plan;
   }

   public void setDeploymentPlan(String plan) {
      this.plan = plan;
   }

   public String getConfigDirectory() {
      return this.config;
   }

   public void setConfigDirectory(String config) {
      this.config = config;
   }

   public String getRootDirectory() {
      return this.root;
   }

   public void setRootDirectory(String dir) {
      this.root = dir;
   }

   public DeploymentOptions getDeploymentOptions() {
      return this.deployOpts;
   }

   public void setDeploymentOptions(DeploymentOptions opts) {
      if (opts == null) {
         opts = new DeploymentOptions();
      }

      this.deployOpts = opts;
   }

   /** @deprecated */
   @Deprecated
   public String[] getTargets() {
      if (this.globaltargets.isEmpty() && this.moduleTargets.isEmpty()) {
         return null;
      } else {
         HashSet set = new HashSet();
         set.addAll(this.globaltargets);
         if (!this.moduleTargets.isEmpty()) {
            String[] modules = this.getModules();

            for(int i = 0; i < modules.length; ++i) {
               set.addAll(Arrays.asList((Object[])this.getModuleTargets(modules[i])));
            }
         }

         ArrayList result = new ArrayList(set);
         return (String[])((String[])result.toArray(new String[result.size()]));
      }
   }

   /** @deprecated */
   @Deprecated
   public void addTarget(String target, String[] modules) {
      if (modules == null) {
         this.addGlobalTarget(target);
      } else {
         this.addTargetToModules(modules, target);
      }

   }

   private void addTargetToModules(String[] moduleNames, String target) {
      if (moduleNames != null && moduleNames.length != 0 && target != null) {
         for(int i = 0; i < moduleNames.length; ++i) {
            this.addModuleTargets(moduleNames[i], new String[]{target});
         }

      } else {
         throw new IllegalArgumentException("Parameters must be non null");
      }
   }

   /** @deprecated */
   @Deprecated
   public String[] getModulesForTarget(String target) {
      if (this.globaltargets.contains(target)) {
         return null;
      } else {
         ArrayList result = new ArrayList();
         Iterator it = this.moduleTargets.keySet().iterator();

         while(it.hasNext()) {
            String mod = (String)it.next();
            String[] thismodulesTargets = (String[])((String[])this.moduleTargets.get(mod));
            if (Arrays.asList((Object[])thismodulesTargets).contains(target)) {
               result.add(mod);
            }
         }

         return (String[])((String[])result.toArray(new String[0]));
      }
   }

   /** @deprecated */
   @Deprecated
   public void addModuleToTarget(String target, String module) {
      if (this.globaltargets.contains(target)) {
         this.globaltargets.remove(target);
         this.addModuleTargets(module, new String[]{target});
      }

   }

   /** @deprecated */
   @Deprecated
   public void addTargetsForComponent(ApplicationMBean mbean, String compName) throws IllegalArgumentException {
      ComponentMBean[] comps = mbean.getComponents();
      if (mbean != null && comps != null) {
         if (compName == null) {
            throw new IllegalArgumentException("No component provided");
         } else {
            for(int i = 0; i < comps.length; ++i) {
               ComponentMBean comp = comps[i];
               if (comp.getName().equals(compName)) {
                  TargetMBean[] targets = comp.getTargets();

                  for(int j = 0; j < targets.length; ++j) {
                     this.addModuleTargets(comp.getName(), new String[]{targets[j].getName()});
                  }

                  if (comp instanceof WebDeploymentMBean) {
                     VirtualHostMBean[] hosts = ((WebDeploymentMBean)comp).getVirtualHosts();

                     for(int j = 0; j < hosts.length; ++j) {
                        this.addModuleTargets(comp.getName(), new String[]{hosts[j].getName()});
                     }
                  }
                  break;
               }
            }

         }
      } else {
         throw new IllegalArgumentException("No application provided");
      }
   }

   /** @deprecated */
   @Deprecated
   public Set getTargetsForModule(String module) {
      if (this.globaltargets.isEmpty() && this.moduleTargets.isEmpty()) {
         return new HashSet(0);
      } else {
         String[] moduletargets = this.getModuleTargets(module);
         return moduletargets == null ? new HashSet(this.globaltargets) : new HashSet(Arrays.asList((Object[])moduletargets));
      }
   }

   /** @deprecated */
   @Deprecated
   public String[] getModules() {
      return (String[])((String[])this.moduleTargets.keySet().toArray(new String[this.moduleTargets.size()]));
   }

   void setAllSubModuleTargets(Map allSubModuleTargets) {
      this.allSubModuleTargets = allSubModuleTargets;
   }

   public void setIntendedState(String state) {
      this.intendedState = state;
   }

   public String getIntendedState() {
      return this.intendedState;
   }

   public void setRemote(boolean setRemote) {
      this.remote = setRemote;
   }

   public boolean isRemote() {
      return this.remote;
   }

   public boolean hasNoTargets() {
      String[] theGlobalTargets = this.getGlobalTargets();
      if (theGlobalTargets != null && theGlobalTargets.length > 0) {
         return false;
      } else {
         return !this.hasModuleTargets() && !this.hasSubModuleTargets();
      }
   }

   public void removeCommonTargets(DeploymentData other, boolean removeFromBoth) {
      this.removeCommonGlobalTargets(other, removeFromBoth);
      this.removeCommonModuleTargets(other, removeFromBoth);
      this.removeCommonSubModuleTargets(other, removeFromBoth);
   }

   public void removeCommonGlobalTargets(DeploymentData other, boolean removeFromBoth) {
      String[] currentGTs = this.getGlobalTargets();
      if (currentGTs != null && currentGTs.length != 0) {
         for(int i = 0; i < currentGTs.length; ++i) {
            if (other.hasGlobalTarget(currentGTs[i])) {
               if (removeFromBoth) {
                  this.removeGlobalTarget(currentGTs[i]);
               }

               other.removeGlobalTarget(currentGTs[i]);
            }
         }

      }
   }

   public void removeCommonModuleTargets(DeploymentData other, boolean removeFromBoth) {
      if (this.hasModuleTargets() && other.hasModuleTargets()) {
         Map currentMTs = new HashMap(this.getAllModuleTargets());
         Iterator curModules = currentMTs.keySet().iterator();

         while(true) {
            String eachModule;
            String[] mTargets;
            do {
               do {
                  if (!curModules.hasNext()) {
                     return;
                  }

                  eachModule = (String)curModules.next();
                  mTargets = this.getModuleTargets(eachModule);
               } while(mTargets == null);
            } while(mTargets.length <= 0);

            for(int i = 0; i < mTargets.length; ++i) {
               boolean removed = other.removeModuleTarget(eachModule, mTargets[i]);
               if (removed && removeFromBoth) {
                  this.removeModuleTarget(eachModule, mTargets[i]);
               }
            }
         }
      }
   }

   public void removeCommonSubModuleTargets(DeploymentData other, boolean removeFromBoth) {
      if (this.hasSubModuleTargets() && other.hasSubModuleTargets()) {
         Map curSubModuleTargets = new HashMap(this.getAllSubModuleTargets());
         Iterator curModules = curSubModuleTargets.keySet().iterator();

         label45:
         while(curModules.hasNext()) {
            String eachModule = (String)curModules.next();
            Map curSubs = new HashMap((Map)curSubModuleTargets.get(eachModule));
            Iterator allSubs = curSubs.keySet().iterator();

            while(true) {
               String eachSub;
               String[] subTargets;
               do {
                  do {
                     if (!allSubs.hasNext()) {
                        continue label45;
                     }

                     eachSub = (String)allSubs.next();
                     subTargets = (String[])((String[])curSubs.get(eachSub));
                  } while(subTargets == null);
               } while(subTargets.length <= 0);

               for(int i = 0; i < subTargets.length; ++i) {
                  boolean removed = other.removeSubModuleTarget(eachModule, eachSub, subTargets[i]);
                  if (removed && removeFromBoth) {
                     this.removeSubModuleTarget(eachModule, eachSub, subTargets[i]);
                  }
               }
            }
         }

      }
   }

   public boolean removeModuleTarget(String moduleName, String target) {
      if (target != null && moduleName != null) {
         String[] curtargets = (String[])((String[])this.moduleTargets.get(moduleName));
         if (curtargets != null && curtargets.length != 0) {
            List targetsList = new ArrayList(Arrays.asList(curtargets));
            int sizeBeforeRemove = targetsList.size();
            targetsList.remove(target);
            int sizeAfterRemove = targetsList.size();
            if (sizeBeforeRemove != sizeAfterRemove) {
               curtargets = new String[targetsList.size()];
               if (curtargets.length == 0) {
                  this.moduleTargets.remove(moduleName);
               } else {
                  curtargets = (String[])((String[])targetsList.toArray(curtargets));
                  this.moduleTargets.put(moduleName, curtargets);
               }

               return true;
            } else {
               return false;
            }
         } else {
            this.moduleTargets.remove(moduleName);
            return false;
         }
      } else {
         throw new IllegalArgumentException("Parameters must not be null");
      }
   }

   public boolean removeSubModuleTarget(String mod, String sub, String target) {
      if (mod != null && sub != null && target != null) {
         Map subMap = (Map)this.allSubModuleTargets.get(mod);
         if (subMap != null && !subMap.isEmpty()) {
            String[] subTargets = (String[])((String[])subMap.get(sub));
            if (subTargets != null && subTargets.length != 0) {
               List targetsList = new ArrayList(Arrays.asList(subTargets));
               int sizeBeforeRemove = targetsList.size();
               targetsList.remove(target);
               int sizeAfterRemove = targetsList.size();
               if (sizeBeforeRemove != sizeAfterRemove) {
                  subTargets = new String[targetsList.size()];
                  if (subTargets.length == 0) {
                     subMap.remove(sub);
                     if (subMap.isEmpty()) {
                        this.allSubModuleTargets.remove(mod);
                     }
                  } else {
                     subTargets = (String[])((String[])targetsList.toArray(subTargets));
                     subMap.put(sub, subTargets);
                     this.allSubModuleTargets.put(mod, subMap);
                  }

                  return true;
               } else {
                  return false;
               }
            } else {
               subMap.remove(sub);
               return false;
            }
         } else {
            this.allSubModuleTargets.remove(mod);
            return false;
         }
      } else {
         throw new IllegalArgumentException("Parameters must not be null");
      }
   }

   public void setThinClient(boolean setThinClient) {
      this.thinClient = setThinClient;
   }

   public boolean isThinClient() {
      return this.thinClient;
   }

   public boolean isRGOrRGTOperation() {
      return this.getPartition() != null || this.getResourceGroup() != null || this.getResourceGroupTemplate() != null;
   }

   class TargetInfo implements Serializable {
      static final long serialVersionUID = -5379313404023125526L;
      private String target;
      private HashSet modules = null;
      private int type = 0;

      TargetInfo(String target, String[] mods) {
         this.target = target;
         if (mods != null) {
            this.modules = new HashSet(mods.length);

            for(int i = 0; i < mods.length; ++i) {
               this.modules.add(mods[i]);
            }
         }

      }

      String getTarget() {
         return this.target;
      }

      String[] getModules() {
         String[] mods = null;
         if (this.modules != null) {
            mods = (String[])((String[])this.modules.toArray(new String[0]));
         }

         return mods;
      }

      void addModule(String module) {
         if (this.modules == null) {
            this.modules = new HashSet();
         }

         if (module != null) {
            this.modules.add(module);
         }

      }

      int getType() {
         return this.type;
      }

      void setType(int newType) {
         this.type = newType;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append(this.getTarget());
         sb.append(": (Modules: ");
         if (this.modules != null) {
            Iterator mods = this.modules.iterator();

            while(mods.hasNext()) {
               sb.append((String)mods.next());
               sb.append(" ");
            }
         } else {
            sb.append("null");
         }

         sb.append(")\n");
         return sb.toString();
      }
   }
}
