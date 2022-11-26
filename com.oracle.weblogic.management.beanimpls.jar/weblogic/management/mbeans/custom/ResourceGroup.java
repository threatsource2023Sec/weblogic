package weblogic.management.mbeans.custom;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.DomainDir;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ResourceGroupUpdateListener;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.AttributeAggregator;

public class ResourceGroup extends ConfigurationMBeanCustomizer {
   private final ResourceGroupMBean resourceGroup;
   private final ConfigurationMBeanCustomized base;
   private String uploadDir;
   private final ResourceGroupUpdateListener updatelistener;

   public ResourceGroup(ConfigurationMBeanCustomized base) {
      super(base);
      this.base = base;
      this.resourceGroup = (ResourceGroupMBean)base;
      this.updatelistener = new ResourceGroupUpdateListener();
      this.resourceGroup.addBeanUpdateListener(this.updatelistener);
   }

   public TargetMBean[] findEffectiveTargets() throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] defaultTargets = null;
      Set targets = new HashSet();
      Object parent = this.getMbean().getParent();
      if (parent != null && parent instanceof PartitionMBean) {
         if (this.resourceGroup.isAutoTargetAdminServer()) {
            TargetMBean adminVT = ((PartitionMBean)parent).getAdminVirtualTarget();
            if (adminVT != null) {
               targets.add(adminVT);
            }
         }

         defaultTargets = ((PartitionMBean)parent).getDefaultTargets();
      }

      if (this.resourceGroup.getTargets().length > 0) {
         targets.addAll(Arrays.asList(this.resourceGroup.getTargets()));
      }

      if (this.resourceGroup.isUseDefaultTarget() && defaultTargets != null && defaultTargets.length > 0) {
         targets.addAll(Arrays.asList(defaultTargets));
      }

      return (TargetMBean[])targets.toArray(new TargetMBean[targets.size()]);
   }

   public DeploymentMBean[] getDeployments() {
      return (DeploymentMBean[])((DeploymentMBean[])ResourceGroup.DEPLOYMENTAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public BasicDeploymentMBean[] getBasicDeployments() {
      return ResourceGroupTemplate.getBasicDeployments((ResourceGroupMBean)this.getMbean());
   }

   public SystemResourceMBean[] getSystemResources() {
      return (SystemResourceMBean[])((SystemResourceMBean[])ResourceGroup.SYSTEMRESOURCEAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public String getUploadDirectoryName() {
      Object parent = this.getMbean().getParent();
      if (parent != null && parent instanceof PartitionMBean) {
         PartitionMBean partition = (PartitionMBean)parent;
         this.uploadDir = partition.getUploadDirectoryName();
      }

      if (parent != null && parent instanceof DomainMBean) {
         DomainMBean domain = (DomainMBean)parent;
         String serverName = domain.getAdminServerName();
         this.uploadDir = DomainDir.getPathRelativeServerDirNonCanonical(serverName, "upload") + File.separator;
      }

      return this.uploadDir;
   }

   public void setUploadDirectoryName(String uploadDir) {
      this.uploadDir = uploadDir;
   }

   public TargetMBean lookupTarget(String name) {
      TargetMBean[] var2 = ((ResourceGroupMBean)this.getMbean()).getTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean bean = var2[var4];
         if (bean.getName().equals(name)) {
            return bean;
         }
      }

      return null;
   }

   public void _preDestroy() {
      ResourceGroupTemplate.destroySystemResources(this.resourceGroup);
      this.resourceGroup.removeBeanUpdateListener(this.updatelistener);
   }

   public Boolean[] areDefinedInTemplate(ConfigurationMBean[] cbs) {
      if (cbs == null) {
         return null;
      } else if (cbs.length == 0) {
         return new Boolean[0];
      } else {
         Boolean[] res = new Boolean[cbs.length];

         for(int i = 0; i < cbs.length; ++i) {
            DescriptorBean rgt = cbs[i];
            if (cbs[i] == null) {
               throw new IllegalArgumentException("One of the provided config bean is null");
            }

            while(rgt != null && !(rgt instanceof ResourceGroupMBean) && !(rgt instanceof ResourceGroupTemplateMBean)) {
               rgt = ((DescriptorBean)rgt).getParentBean();
            }

            if (rgt == null) {
               throw new IllegalArgumentException(String.format("%s is not parented under a resource group", cbs[i].getName()));
            }

            if (rgt instanceof ResourceGroupMBean) {
               if (!rgt.equals(this.resourceGroup)) {
                  throw new IllegalArgumentException(String.format("%s is parented under a different resource group (%s)", cbs[i].getName(), ((ResourceGroupMBean)rgt).getName()));
               }
            } else {
               if (this.resourceGroup.getResourceGroupTemplate() == null) {
                  throw new IllegalArgumentException(String.format("%s is parented under a resource group template (%s), but %s does not reference a resource group template", cbs[i].getName(), ((ResourceGroupTemplateMBean)rgt).getName(), this.resourceGroup.getName()));
               }

               if (!this.resourceGroup.getResourceGroupTemplate().equals(rgt)) {
                  throw new IllegalArgumentException(String.format("%s is parented under a resource group template (%s), but %s reference a different resource group template (%s)", cbs[i].getName(), ((ResourceGroupTemplateMBean)rgt).getName(), this.resourceGroup.getName(), this.resourceGroup.getResourceGroupTemplate().getName()));
               }
            }

            if (!(rgt instanceof ResourceGroupMBean)) {
               res[i] = true;
            } else {
               if (this.resourceGroup.getResourceGroupTemplate() != null) {
                  try {
                     Class clz = cbs[i].getClass();
                     Method mthd1 = clz.getMethod("_getDelegateBean");
                     Object delegate = mthd1.invoke(cbs[i]);
                     if (delegate != null && ((AbstractDescriptorBean)cbs[i])._isTransient()) {
                        res[i] = true;
                        continue;
                     }
                  } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var8) {
                     throw new RuntimeException("Error while calling _getDelegateBean on" + cbs[i].toString(), var8);
                  }
               }

               res[i] = false;
            }
         }

         return res;
      }
   }

   private static class SYSTEMRESOURCEAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator(ResourceGroupMBean.class, SystemResourceMBean.class, "getSystemResources");
   }

   private static class DEPLOYMENTAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator(ResourceGroupMBean.class, DeploymentMBean.class, "getDeployments");
   }
}
