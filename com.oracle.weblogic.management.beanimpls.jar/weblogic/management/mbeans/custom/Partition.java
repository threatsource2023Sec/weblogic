package weblogic.management.mbeans.custom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.deploy.internal.ResourceDeploymentPlanDescriptorLoader;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBean;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.PartitionFileSystemMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.DescriptorInfoUtils;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public final class Partition extends ConfigurationMBeanCustomizer {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final PartitionMBean partitionMBean;
   private volatile String uploadDir;

   public Partition(ConfigurationMBeanCustomized base) {
      super(base);
      this.partitionMBean = (PartitionMBean)base;
   }

   public String getUploadDirectoryName() {
      String partitionUploadDir = null;
      String partition_name = this.getMbean().getName();
      Object parent = this.getMbean().getParent();
      if (parent instanceof DomainMBean) {
         String serverName = ((DomainMBean)parent).getAdminServerName();
         if (this.uploadDir == null && serverName != null && partition_name != null) {
            PartitionFileSystemMBean pfs = this.partitionMBean.getSystemFileSystem();
            partitionUploadDir = pfs.getRoot() + File.separator + "servers" + File.separator + serverName + File.separator + "upload" + File.separator;
         }
      }

      return partitionUploadDir;
   }

   public void setUploadDirectoryName(String uploadDir) {
      this.uploadDir = uploadDir;
   }

   public TargetMBean lookupAvailableTarget(String name) {
      TargetMBean[] var2 = this.partitionMBean.getAvailableTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean bean = var2[var4];
         if (bean.getName().equals(name)) {
            return bean;
         }
      }

      return null;
   }

   public TargetMBean[] findEffectiveTargets() throws Exception {
      Set targets = new HashSet();
      TargetMBean[] effectiveTargets = new TargetMBean[targets.size()];
      return (TargetMBean[])targets.toArray(effectiveTargets);
   }

   public String[] findEffectiveServerNames() {
      Set effectiveTargets = PartitionUtils.getServers(this.partitionMBean);
      return (String[])effectiveTargets.toArray(new String[effectiveTargets.size()]);
   }

   public TargetMBean[] findEffectiveAdminTargets() throws Exception {
      Set targets = new HashSet();
      TargetMBean[] effectiveAdminTargets = new TargetMBean[targets.size()];
      return (TargetMBean[])targets.toArray(effectiveAdminTargets);
   }

   public ResourceGroupMBean[] findAdminResourceGroupsTargeted(String serverName) {
      List result = new ArrayList();
      return (ResourceGroupMBean[])result.toArray(new ResourceGroupMBean[result.size()]);
   }

   public TargetMBean[] findEffectiveAvailableTargets() throws Exception {
      Set effectiveAvailableTargets = new HashSet();
      TargetMBean[] availableTargets = this.partitionMBean.getAvailableTargets();
      if (availableTargets != null && availableTargets.length > 0) {
         effectiveAvailableTargets.addAll(Arrays.asList(this.partitionMBean.getAvailableTargets()));
      }

      TargetMBean adminVirtualTarget = this.partitionMBean.getAdminVirtualTarget();
      if (adminVirtualTarget != null) {
         effectiveAvailableTargets.add(this.partitionMBean.getAdminVirtualTarget());
      }

      return (TargetMBean[])effectiveAvailableTargets.toArray(new TargetMBean[effectiveAvailableTargets.size()]);
   }

   public TargetMBean lookupEffectiveAvailableTarget(String name) throws Exception {
      TargetMBean[] var2 = this.partitionMBean.findEffectiveAvailableTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean t = var2[var4];
         if (t.getName().equals(name)) {
            return t;
         }
      }

      return null;
   }

   public void _preDestroy() {
      this.removeApplicationDeployments();
      this.removePartitionInternalDeployments();
   }

   public void _postCreate() {
      try {
         PartitionUtils.createAdminVirtualTarget(this.partitionMBean);
      } catch (ManagementException var2) {
         throw new RuntimeException(var2);
      }
   }

   private void removeApplicationDeployments() {
      WebLogicMBean wlmb = this.partitionMBean.getParent();
      if (wlmb instanceof DomainMBean) {
         if (wlmb instanceof AbstractDescriptorBean) {
            DomainMBean domainParent = (DomainMBean)wlmb;
            AbstractDescriptorBean domainAbstract = (AbstractDescriptorBean)wlmb;
            ReferenceManager domainReferenceManager = domainAbstract._getReferenceManager();
            String partitionName = this.partitionMBean.getName();
            AppDeploymentMBean[] potentialPartitionApps = domainParent.getAppDeployments();
            AppDeploymentMBean[] var7 = potentialPartitionApps;
            int var8 = potentialPartitionApps.length;

            int var9;
            for(var9 = 0; var9 < var8; ++var9) {
               AppDeploymentMBean ppa = var7[var9];
               String ppaName = ppa.getPartitionName();
               if (ppaName != null && ppaName.equals(partitionName)) {
                  domainParent.destroyAppDeployment(ppa);
               }
            }

            ArrayList modifiedAppDeployments = new ArrayList();
            potentialPartitionApps = domainParent.getInternalAppDeployments();
            AppDeploymentMBean[] newInternalApps = potentialPartitionApps;
            var9 = potentialPartitionApps.length;

            for(int var16 = 0; var16 < var9; ++var16) {
               AppDeploymentMBean ppa = newInternalApps[var16];
               String ppaName = ppa.getPartitionName();
               if (ppaName == null) {
                  modifiedAppDeployments.add(ppa);
               } else if (!ppaName.equals(partitionName)) {
                  modifiedAppDeployments.add(ppa);
               } else if (ppa instanceof AbstractDescriptorBean) {
                  AbstractDescriptorBean adb = (AbstractDescriptorBean)ppa;
                  domainReferenceManager.unregisterBean(adb, true);
               }
            }

            newInternalApps = (AppDeploymentMBean[])modifiedAppDeployments.toArray(new AppDeploymentMBean[modifiedAppDeployments.size()]);
            domainParent.setInternalAppDeployments(newInternalApps);
         }
      }
   }

   private void removePartitionRefcsToRGResources() {
      if (this.partitionMBean.getDataSourceForJobScheduler() != null) {
         this.partitionMBean.setDataSourceForJobScheduler((JDBCSystemResourceMBean)null);
      }

   }

   private void removePartitionInternalDeployments() {
      DomainMBean domain = (DomainMBean)DomainMBean.class.cast(this.partitionMBean.getParent());
      domain.setInternalAppDeployments(this.removePartitionInternalDeployments(domain.getInternalAppDeployments()));
      domain.setInternalLibraries((LibraryMBean[])this.removePartitionInternalDeployments(domain.getInternalLibraries()));
   }

   private AppDeploymentMBean[] removePartitionInternalDeployments(AppDeploymentMBean[] beans) {
      Set internalBeans = new HashSet();
      Collections.addAll(internalBeans, beans);
      Iterator it = internalBeans.iterator();

      while(it.hasNext()) {
         AppDeploymentMBean internalBean = (AppDeploymentMBean)it.next();
         if (this.getName().equals(internalBean.getPartitionName())) {
            it.remove();
         }
      }

      return (AppDeploymentMBean[])internalBeans.toArray((AppDeploymentMBean[])((AppDeploymentMBean[])Array.newInstance(beans.getClass().getComponentType(), internalBeans.size())));
   }

   public byte[] getResourceDeploymentPlan() {
      String path = this.getLocalPlanPath();
      if (path != null) {
         try {
            return Files.readAllBytes(Paths.get(path));
         } catch (IOException var3) {
            throw new IllegalArgumentException(var3.getMessage(), var3);
         }
      } else {
         return null;
      }
   }

   public byte[] getResourceDeploymentPlanExternalDescriptors() {
      ResourceDeploymentPlanBean plan = this.getResourceDeploymentPlanDescriptor();
      if (plan == null) {
         return null;
      } else {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();

         try {
            ZipOutputStream zip = new ZipOutputStream(bos);
            Throwable var4 = null;

            try {
               ExternalResourceOverrideBean[] var5 = plan.getExternalResourceOverrides();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  ExternalResourceOverrideBean external = var5[var7];
                  String externalPath = external.getDescriptorFilePath();
                  if (externalPath != null) {
                     zip.putNextEntry(new ZipEntry(external.getResourceName()));
                     zip.write(Files.readAllBytes(Paths.get(externalPath)));
                     zip.closeEntry();
                  }
               }
            } catch (Throwable var18) {
               var4 = var18;
               throw var18;
            } finally {
               if (zip != null) {
                  if (var4 != null) {
                     try {
                        zip.close();
                     } catch (Throwable var17) {
                        var4.addSuppressed(var17);
                     }
                  } else {
                     zip.close();
                  }
               }

            }
         } catch (IOException var20) {
            throw new IllegalArgumentException(var20.getMessage(), var20);
         }

         byte[] bytes = bos.toByteArray();
         return bytes.length == 0 ? null : bytes;
      }
   }

   public ResourceDeploymentPlanBean getResourceDeploymentPlanDescriptor() {
      ResourceDeploymentPlanBean result = null;
      String path = this.getLocalPlanPath();
      if (path != null) {
         File planFile = new File(path);
         if (!planFile.exists()) {
            Descriptor descriptor = this.partitionMBean.getDescriptor();
            Map temporaryFiles = DescriptorInfoUtils.getExtensionTemporaryFiles(descriptor);
            String relativePlanPath = Paths.get(DomainDir.getRootDir()).relativize(Paths.get(path)).toString();
            if (temporaryFiles.containsKey(relativePlanPath)) {
               planFile = new File((String)temporaryFiles.get(relativePlanPath));
            }
         }

         try {
            result = (new ResourceDeploymentPlanDescriptorLoader(planFile)).getResourceDeploymentPlanBean();
         } catch (XMLStreamException | IOException var7) {
            throw new IllegalArgumentException(var7.getMessage(), var7);
         }
      }

      return result;
   }

   private String getLocalPlanPath() {
      String rdpPath = this.partitionMBean.getResourceDeploymentPlanPath();
      if (rdpPath == null) {
         return null;
      } else {
         return ManagementService.getRuntimeAccess(kernelId).isAdminServer() ? rdpPath : DomainDir.getConfigDir() + File.separator + "rdp" + File.separator + FileUtils.mapNameToFileName(this.partitionMBean.getName(), false) + File.separator + "rdp.xml";
      }
   }
}
