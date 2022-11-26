package weblogic.management.provider.internal;

import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.utils.ActiveBeanUtilBase;

@Service
@Singleton
public class ActiveBeanUtilBaseImpl implements ActiveBeanUtilBase {
   public ConfigurationMBean toOriginalBean(ConfigurationMBean bean) {
      return bean;
   }

   public ConfigurationMBean toActiveBean(ConfigurationMBean bean) {
      WebLogicMBean container = this.findNonDomainContainer(bean);
      if (container == null) {
         return bean;
      } else {
         PartitionMBean containingPartition = this.findContainingPartition(container);
         String fullName;
         if (containingPartition == null) {
            fullName = bean.getName();
         } else {
            fullName = bean.getName() + "$" + containingPartition.getName();
         }

         DomainMBean domain = this.getDomain(bean);
         ConfigurationMBean result = this.findResource(domain, bean, fullName);
         return result;
      }
   }

   private ConfigurationMBean findResource(PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, String shortName) {
      BasicDeploymentMBean[] var5 = resourceGroup.getBasicDeployments();
      int var6 = var5.length;

      int var7;
      ConfigurationMBean match;
      for(var7 = 0; var7 < var6; ++var7) {
         BasicDeploymentMBean bd = var5[var7];
         match = this.checkNameAndType(bean, shortName, bd, partition == null ? null : partition.getName(), resourceGroup.getName());
         if (match != null) {
            return match;
         }
      }

      DeploymentMBean[] var10 = resourceGroup.getDeployments();
      var6 = var10.length;

      for(var7 = 0; var7 < var6; ++var7) {
         DeploymentMBean d = var10[var7];
         match = this.checkNameAndType(bean, shortName, d, partition == null ? null : partition.getName(), resourceGroup.getName());
         if (match != null) {
            return match;
         }
      }

      return null;
   }

   private ConfigurationMBean checkNameAndType(ConfigurationMBean bean, String shortBeanName, ConfigurationMBean candidate, String partitionName, String resourceGroupName) {
      if (candidate.getName().equals(shortBeanName)) {
         if (bean.getClass().isAssignableFrom(candidate.getClass())) {
            return (ConfigurationMBean)bean.getClass().cast(candidate);
         } else if (partitionName == null) {
            throw new IllegalArgumentException("Bean " + bean.getName() + " has type " + bean.getClass().getName() + " which does not match " + candidate.getClass().getName() + " of bean " + candidate.getName() + " in domain-level resource group " + resourceGroupName);
         } else {
            throw new IllegalArgumentException("Bean " + bean.getName() + " has type " + bean.getClass().getName() + " which does not match " + candidate.getClass().getName() + " of bean " + candidate.getName() + " in partition " + partitionName + ", resource group " + resourceGroupName);
         }
      } else {
         return null;
      }
   }

   private ConfigurationMBean findResource(DomainMBean domain, ConfigurationMBean bean, String fullName) {
      BasicDeploymentMBean[] var4 = domain.getBasicDeployments();
      int var5 = var4.length;

      int var6;
      ConfigurationMBean result;
      for(var6 = 0; var6 < var5; ++var6) {
         BasicDeploymentMBean bd = var4[var6];
         result = this.checkNameAndType(bean, bd, fullName);
         if (result != null) {
            return result;
         }
      }

      DeploymentMBean[] var9 = domain.getDeployments();
      var5 = var9.length;

      for(var6 = 0; var6 < var5; ++var6) {
         DeploymentMBean d = var9[var6];
         result = this.checkNameAndType(bean, d, fullName);
         if (result != null) {
            return result;
         }
      }

      return null;
   }

   private ConfigurationMBean checkNameAndType(ConfigurationMBean bean, ConfigurationMBean candidate, String fullName) {
      return candidate.getName().equals(fullName) && bean.getClass().isAssignableFrom(candidate.getClass()) ? (ConfigurationMBean)bean.getClass().cast(candidate) : null;
   }

   private PartitionMBean findContainingPartition(WebLogicMBean container) {
      while(container != null) {
         if (container instanceof PartitionMBean) {
            return (PartitionMBean)container;
         }

         container = container.getParent();
      }

      return null;
   }

   private WebLogicMBean findNonDomainContainer(ConfigurationMBean bean) {
      WebLogicMBean parent = bean.getParent();
      boolean foundContainer = false;

      while(!foundContainer && parent != null) {
         foundContainer = parent instanceof ResourceGroupMBean || parent instanceof PartitionMBean;
         if (!foundContainer) {
            parent = parent.getParent();
         }
      }

      return parent;
   }

   private DomainMBean getDomain(ConfigurationMBean bean) {
      DescriptorBean root = bean.getDescriptor().getRootBean();
      if (root instanceof DomainMBean) {
         return (DomainMBean)root;
      } else {
         throw new IllegalArgumentException("Root of " + bean.getName() + " is not DomainMBean as expected");
      }
   }

   private String toShort(String name) {
      int lastDollar = name.lastIndexOf(36);
      return lastDollar < 0 ? name : name.substring(0, lastDollar);
   }

   private String getPartitionName(String name) {
      int lastDollar = name.lastIndexOf(36);
      return lastDollar < 0 ? "" : name.substring(lastDollar + 1);
   }
}
