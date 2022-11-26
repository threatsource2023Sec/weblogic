package com.bea.core.repackaged.springframework.jmx.export.assembler;

import com.bea.core.repackaged.springframework.jmx.export.metadata.JmxMetadataUtils;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedNotification;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.management.modelmbean.ModelMBeanNotificationInfo;

public abstract class AbstractConfigurableMBeanInfoAssembler extends AbstractReflectiveMBeanInfoAssembler {
   @Nullable
   private ModelMBeanNotificationInfo[] notificationInfos;
   private final Map notificationInfoMappings = new HashMap();

   public void setNotificationInfos(ManagedNotification[] notificationInfos) {
      ModelMBeanNotificationInfo[] infos = new ModelMBeanNotificationInfo[notificationInfos.length];

      for(int i = 0; i < notificationInfos.length; ++i) {
         ManagedNotification notificationInfo = notificationInfos[i];
         infos[i] = JmxMetadataUtils.convertToModelMBeanNotificationInfo(notificationInfo);
      }

      this.notificationInfos = infos;
   }

   public void setNotificationInfoMappings(Map notificationInfoMappings) {
      notificationInfoMappings.forEach((beanKey, result) -> {
         ModelMBeanNotificationInfo[] var10000 = (ModelMBeanNotificationInfo[])this.notificationInfoMappings.put(beanKey, this.extractNotificationMetadata(result));
      });
   }

   protected ModelMBeanNotificationInfo[] getNotificationInfo(Object managedBean, String beanKey) {
      ModelMBeanNotificationInfo[] result = null;
      if (StringUtils.hasText(beanKey)) {
         result = (ModelMBeanNotificationInfo[])this.notificationInfoMappings.get(beanKey);
      }

      if (result == null) {
         result = this.notificationInfos;
      }

      return result != null ? result : new ModelMBeanNotificationInfo[0];
   }

   private ModelMBeanNotificationInfo[] extractNotificationMetadata(Object mapValue) {
      if (mapValue instanceof ManagedNotification) {
         ManagedNotification mn = (ManagedNotification)mapValue;
         return new ModelMBeanNotificationInfo[]{JmxMetadataUtils.convertToModelMBeanNotificationInfo(mn)};
      } else if (mapValue instanceof Collection) {
         Collection col = (Collection)mapValue;
         List result = new ArrayList();
         Iterator var4 = col.iterator();

         while(var4.hasNext()) {
            Object colValue = var4.next();
            if (!(colValue instanceof ManagedNotification)) {
               throw new IllegalArgumentException("Property 'notificationInfoMappings' only accepts ManagedNotifications for Map values");
            }

            ManagedNotification mn = (ManagedNotification)colValue;
            result.add(JmxMetadataUtils.convertToModelMBeanNotificationInfo(mn));
         }

         return (ModelMBeanNotificationInfo[])result.toArray(new ModelMBeanNotificationInfo[0]);
      } else {
         throw new IllegalArgumentException("Property 'notificationInfoMappings' only accepts ManagedNotifications for Map values");
      }
   }
}
