package weblogic.management.remote.util;

import javax.management.ObjectName;
import weblogic.management.jmx.mbeanserver.WLSCoherenceOrVTMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSCoherenceOrVTMBeanNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanAttributeChangeNotification;
import weblogic.management.jmx.mbeanserver.WLSMBeanNotification;
import weblogic.management.visibility.MBeanType;
import weblogic.management.visibility.WLSMBeanVisibility;
import weblogic.management.visibility.utils.MBeanNameUtil;

public class MBeanVisibilityUtil {
   public static final String PARTITION_KEY = "Partition";
   public static final String PARTITION_RUNTIME_KEY = "PartitionRuntime";
   public static final String DOMAIN_PARTITION_RUNTIME_KEY = "DomainPartitionRuntime";
   public static final String COHERENCE_KEY = "domainPartition";
   public static final String GLOBAL_PARTITION = "DOMAIN";

   public static boolean isMBeanVisibleToPartition(String partitionName, WLSMBeanNotification notification) {
      boolean isMBeanVisible = false;
      if (notification instanceof WLSCoherenceOrVTMBeanNotification) {
         if (notification.getMBeanType().equals(MBeanType.WLS_COHERENCE)) {
            isMBeanVisible = MBeanNameUtil.isCoherenceMBeanInSamePartition(partitionName, notification.getMBeanName());
         }
      } else {
         isMBeanVisible = isMBeanVisible(notification.getMBeanName(), partitionName, notification.getMBeanType(), notification.getVisibility());
      }

      return isMBeanVisible;
   }

   public static boolean isAttributeVisibleToPartition(String partitionName, WLSMBeanAttributeChangeNotification notification) {
      boolean isMBeanVisible;
      if (notification instanceof WLSCoherenceOrVTMBeanAttributeChangeNotification) {
         isMBeanVisible = MBeanNameUtil.isCoherenceMBeanInSamePartition(partitionName, (ObjectName)notification.getSource());
      } else {
         isMBeanVisible = isMBeanVisible((ObjectName)notification.getSource(), partitionName, notification.getMBeanType(), notification.getVisibility());
      }

      return isMBeanVisible && notification.isAttributeVisible();
   }

   private static boolean isMBeanVisible(ObjectName oname, String partitionName, MBeanType type, WLSMBeanVisibility wlsmBeanVisibility) {
      if (!type.equals(MBeanType.SECURITY)) {
         if (type.equals(MBeanType.JDK)) {
            return wlsmBeanVisibility.equals(WLSMBeanVisibility.ALL);
         } else if (!type.equals(MBeanType.JRF_GLOBAL) && !type.equals(MBeanType.JRF_PARTITION)) {
            if (!type.equals(MBeanType.WLS_PARTITION)) {
               boolean isAnnotated = !wlsmBeanVisibility.equals(WLSMBeanVisibility.NONE);
               if (type.equals(MBeanType.WLS_DEPLOYMENT_RUNTIME)) {
                  return isAnnotated && MBeanNameUtil.isPartitionOwnedWLSDeploymentMBeanInSamePartition(partitionName, oname);
               } else if (!type.equals(MBeanType.WLS_CONFIGURATION_RUNTIME)) {
                  return isAnnotated;
               } else {
                  return isAnnotated && MBeanNameUtil.isWLSPartitionConfigurationMBeanInSamePartition(partitionName, oname);
               }
            } else {
               return MBeanNameUtil.isMBeanInSamePartition(partitionName, oname);
            }
         } else {
            return type.equals(MBeanType.JRF_PARTITION) ? MBeanNameUtil.isMBeanInSamePartition(partitionName, oname) : wlsmBeanVisibility.equals(WLSMBeanVisibility.ALL);
         }
      } else {
         return true;
      }
   }
}
