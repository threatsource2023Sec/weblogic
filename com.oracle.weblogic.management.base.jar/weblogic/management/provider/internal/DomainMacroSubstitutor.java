package weblogic.management.provider.internal;

import weblogic.descriptor.AbstractMacroSubstitutor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorMacroResolver;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.ConfigurationPropertiesMBean;
import weblogic.management.configuration.ConfigurationPropertyMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.mbean.AbstractDynamicMBean;
import weblogic.management.mbeans.custom.WebLogic;

public class DomainMacroSubstitutor extends AbstractMacroSubstitutor {
   private static final DescriptorMacroResolver resolver = new DomainMacroResolver();

   public String performMacroSubstitution(String inputValue, DescriptorBean bean) {
      return this.substituteMacro(inputValue, resolver, bean);
   }

   private static String getDomainName(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof DomainMBean) {
            return ((DomainMBean)bean).getName();
         }

         bean = bean.getParentBean();
      }

      return null;
   }

   private static String getServerName(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof ServerMBean) {
            return ((ServerMBean)bean).getName();
         }

         bean = bean.getParentBean();
      }

      return null;
   }

   private static String getMachineName(DescriptorBean bean) {
      for(; bean != null; bean = bean.getParentBean()) {
         if (bean instanceof ServerMBean) {
            MachineMBean mmb = ((ServerMBean)bean).getMachine();
            if (mmb != null) {
               return mmb.getName();
            }
         }
      }

      return null;
   }

   private static String getClusterName(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof ServerMBean) {
            ClusterMBean cluster = ((ServerMBean)bean).getCluster();
            return cluster != null ? cluster.getName() : null;
         }

         bean = bean.getParentBean();
      }

      return null;
   }

   private static DomainMBean getDomain(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof DomainMBean) {
            return (DomainMBean)bean;
         }

         bean = bean.getParentBean();
      }

      return null;
   }

   private static ResourceGroupMBean getResourceGroup(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof ResourceGroupMBean) {
            return (ResourceGroupMBean)bean;
         }

         bean = bean.getParentBean();
      }

      return null;
   }

   private static DescriptorBean getCorrespondingPartitionIfCloned(DescriptorBean bean) {
      if (!(bean instanceof ConfigurationMBean)) {
         return bean;
      } else {
         String name = null;
         ResourceGroupMBean rg = getResourceGroup(bean);
         if (rg != null && rg.getResourceGroupTemplate() != null && !((ConfigurationMBean)bean).isSet("Name")) {
            Object customizer = ((AbstractDynamicMBean)bean).getValue("customizer");
            if (customizer != null) {
               name = ((WebLogic)customizer).getName();
            }
         }

         if (name == null) {
            name = ((ConfigurationMBean)bean).getName();
         }

         int idx = name.lastIndexOf("$");
         if (idx == -1) {
            return bean;
         } else {
            DomainMBean domain = getDomain(bean);
            String partitionName = name.substring(idx + 1);
            PartitionMBean partition = domain.lookupPartition(partitionName);
            return (DescriptorBean)(partition != null ? partition : bean);
         }
      }
   }

   private static class DomainMacroResolver implements DescriptorMacroResolver {
      private DomainMacroResolver() {
      }

      public String resolveMacroValue(String macro, DescriptorBean bean) {
         if (macro != null && !macro.isEmpty()) {
            if (macro.equals("domainName")) {
               return DomainMacroSubstitutor.getDomainName(bean);
            } else if (macro.equals("serverName")) {
               return DomainMacroSubstitutor.getServerName(bean);
            } else if (macro.equals("clusterName")) {
               return DomainMacroSubstitutor.getClusterName(bean);
            } else if (macro.equals("machineName")) {
               return DomainMacroSubstitutor.getMachineName(bean);
            } else {
               DescriptorBean propBean;
               if (macro.equals("id") && bean instanceof AbstractDescriptorBean) {
                  int id = ((AbstractDescriptorBean)bean).getInstanceId();

                  for(propBean = bean.getParentBean(); id == 0 && propBean != null; propBean = propBean.getParentBean()) {
                     if (propBean instanceof AbstractDescriptorBean) {
                        id = ((AbstractDescriptorBean)propBean).getInstanceId();
                     }
                  }

                  return "" + id;
               } else {
                  String propValue = null;
                  propBean = bean;
                  if (!(bean instanceof ConfigurationPropertiesMBean)) {
                     propBean = DomainMacroSubstitutor.getCorrespondingPartitionIfCloned(bean);
                  }

                  for(; propValue == null && propBean != null; propBean = propBean.getParentBean()) {
                     if (propBean instanceof ConfigurationPropertiesMBean) {
                        ConfigurationPropertyMBean[] props = ((ConfigurationPropertiesMBean)propBean).getConfigurationProperties();
                        ConfigurationPropertyMBean[] var6 = props;
                        int var7 = props.length;

                        for(int var8 = 0; var8 < var7; ++var8) {
                           ConfigurationPropertyMBean prop = var6[var8];
                           if (macro.equals(prop.getName())) {
                              propValue = prop.getValue();
                           }
                        }
                     }
                  }

                  return propValue != null ? propValue : System.getProperty(macro);
               }
            }
         } else {
            return "";
         }
      }

      // $FF: synthetic method
      DomainMacroResolver(Object x0) {
         this();
      }
   }
}
