package com.bea.core.repackaged.springframework.jmx.export.metadata;

import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import javax.management.modelmbean.ModelMBeanNotificationInfo;

public abstract class JmxMetadataUtils {
   public static ModelMBeanNotificationInfo convertToModelMBeanNotificationInfo(ManagedNotification notificationInfo) {
      String[] notifTypes = notificationInfo.getNotificationTypes();
      if (ObjectUtils.isEmpty((Object[])notifTypes)) {
         throw new IllegalArgumentException("Must specify at least one notification type");
      } else {
         String name = notificationInfo.getName();
         if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Must specify notification name");
         } else {
            String description = notificationInfo.getDescription();
            return new ModelMBeanNotificationInfo(notifTypes, name, description);
         }
      }
   }
}
