package weblogic.management.utils;

import java.security.AccessController;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class BeanNameUtil {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String getFullName(ConfigurationMBean bean) {
      return bean.getName();
   }

   public static String getShortName(ConfigurationMBean bean) {
      PartitionMBean partition = belongsToPartition(bean);
      return partition != null ? getUndecoratedConfigBeanName(bean.getName(), partition) : bean.getName();
   }

   public static PartitionMBean belongsToPartition(ConfigurationMBean bean) {
      DomainMBean domainMBean = PartitionUtils.getDomain(bean);
      return null;
   }

   public static String getUndecoratedConfigBeanName(String beanName, PartitionMBean partition) {
      String beanSuffix = PartitionUtils.getSuffix(partition);
      if (beanName.endsWith(beanSuffix)) {
         int index = beanName.lastIndexOf(beanSuffix);
         return beanName.substring(0, index);
      } else {
         return beanName;
      }
   }
}
