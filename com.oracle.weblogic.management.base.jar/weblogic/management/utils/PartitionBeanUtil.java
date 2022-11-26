package weblogic.management.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;

public class PartitionBeanUtil {
   public static Iterator domainLevelResources(DomainMBean domain) {
      final BasicDeploymentMBean[] basicDeployments = domain.getBasicDeployments();
      final DeploymentMBean[] deployments = domain.getDeployments();
      return new Iterator() {
         private int nextBasicDeploymentSlot = this.nextUsableSlot(-1, basicDeployments);
         private int nextDeploymentSlot = this.nextUsableSlot(-1, deployments);

         private int nextUsableSlot(int firstSlotToIgnore, ConfigurationMBean[] depls) {
            for(int i = firstSlotToIgnore + 1; i < depls.length; ++i) {
               ConfigurationMBean bean = depls[i];
               if (PartitionBeanUtil.isDomainLevelBean(bean)) {
                  return i;
               }
            }

            return depls.length;
         }

         public boolean hasNext() {
            return this.nextBasicDeploymentSlot < basicDeployments.length || this.nextDeploymentSlot < deployments.length;
         }

         public ConfigurationMBean next() {
            if (this.nextBasicDeploymentSlot < basicDeployments.length) {
               ConfigurationMBean resultx = basicDeployments[this.nextBasicDeploymentSlot];
               this.nextBasicDeploymentSlot = this.nextUsableSlot(this.nextBasicDeploymentSlot, basicDeployments);
               return resultx;
            } else if (this.nextDeploymentSlot < deployments.length) {
               ConfigurationMBean result = deployments[this.nextDeploymentSlot++];
               this.nextDeploymentSlot = this.nextUsableSlot(this.nextDeploymentSlot, deployments);
               return result;
            } else {
               throw new NoSuchElementException();
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public static Iterator filterResources(final Collection resources, final Class tClass) {
      return new Iterator() {
         private final Iterator resourcesIt = resources.iterator();
         private ConfigurationMBean nextMatch = this.advanceToNextUsableResource();

         private ConfigurationMBean advanceToNextUsableResource() {
            while(true) {
               if (this.resourcesIt.hasNext()) {
                  ConfigurationMBean bean = (ConfigurationMBean)this.resourcesIt.next();
                  if (!tClass.isAssignableFrom(bean.getClass())) {
                     continue;
                  }

                  return bean;
               }

               return null;
            }
         }

         public boolean hasNext() {
            return this.nextMatch != null;
         }

         public ConfigurationMBean next() {
            if (this.nextMatch == null) {
               throw new NoSuchElementException();
            } else {
               ConfigurationMBean result = this.nextMatch;
               this.nextMatch = this.advanceToNextUsableResource();
               return result;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   private static boolean isDomainLevelBean(ConfigurationMBean bean) {
      if (!(bean instanceof AbstractDescriptorBean)) {
         return true;
      } else {
         AbstractDescriptorBean adb = (AbstractDescriptorBean)bean;
         return !adb._isTransient();
      }
   }
}
