package weblogic.management.utils;

import java.util.Locale;
import javax.management.Descriptor;
import javax.management.DescriptorRead;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import weblogic.diagnostics.debug.DebugLogger;

public class ModelMBeanInfoLocalizationHelper {
   private static DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugMBeanLocalization");

   static MBeanInfo localizeModelMBeanInfo(ModelMBeanInfo info, Locale locale) {
      Descriptor descriptor = null;

      try {
         descriptor = info.getMBeanDescriptor();
      } catch (MBeanException var15) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("ModelMBeanInfoLocalizationHelper.localizeModelMBeanInfo(): : Error getting ModelMBeanInfo Descriptor for:" + info);
         }

         return (MBeanInfo)MBeanInfo.class.cast(info);
      }

      String bundle = (String)descriptor.getFieldValue("descriptionResourceBundleBaseName");
      if ((bundle == null || bundle.length() == 0) && MBeanInfoLocalizationHelper.getDefaultResourceBundle((MBeanInfo)MBeanInfo.class.cast(info), locale) != null) {
         bundle = MBeanInfoLocalizationHelper.getDefaultResourceBundleName((MBeanInfo)MBeanInfo.class.cast(info));
      }

      String baseDefaultResourceKey = MBeanInfoLocalizationHelper.getBaseDefaultResourceKey((MBeanInfo)MBeanInfo.class.cast(info));
      String defaultResourceKey = baseDefaultResourceKey + "mbean";
      String description = GenericMBeanInfoLocalizationHelper.localizeDescription(descriptor, locale, bundle, defaultResourceKey);
      if (description == null) {
         description = info.getDescription();
      }

      descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor((DescriptorRead)DescriptorRead.class.cast(info), locale, bundle);
      MBeanAttributeInfo[] attributes = info.getAttributes();
      MBeanConstructorInfo[] constructors = info.getConstructors();
      MBeanOperationInfo[] operations = info.getOperations();
      MBeanNotificationInfo[] notifications = info.getNotifications();
      ModelMBeanAttributeInfo[] modelAttributes = localizeModelMBeanAttributes(attributes, locale, bundle, baseDefaultResourceKey + "attribute.");
      ModelMBeanConstructorInfo[] modelConstructors = localizeModelMBeanConstructors(constructors, locale, bundle, baseDefaultResourceKey + "constructor.");
      ModelMBeanOperationInfo[] modelOperations = localizeModelMBeanOperations(operations, locale, bundle, baseDefaultResourceKey + "operation.");
      ModelMBeanNotificationInfo[] modelNotifications = localizeModelMBeanNotifications(notifications, locale, bundle, baseDefaultResourceKey + "notification.");
      return new ModelMBeanInfoSupport(info.getClassName(), description, modelAttributes, modelConstructors, modelOperations, modelNotifications, descriptor);
   }

   private static ModelMBeanAttributeInfo[] localizeModelMBeanAttributes(MBeanAttributeInfo[] attributes, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (attributes == null) {
         return null;
      } else {
         ModelMBeanAttributeInfo[] modelAttributes = new ModelMBeanAttributeInfo[attributes.length];

         for(int i = 0; i < attributes.length; ++i) {
            modelAttributes[i] = localizeModelMBeanAttribute((ModelMBeanAttributeInfo)ModelMBeanAttributeInfo.class.cast(attributes[i]), locale, parentBundle, baseDefaultResourceKey);
         }

         return modelAttributes;
      }
   }

   static ModelMBeanAttributeInfo localizeModelMBeanAttribute(ModelMBeanAttributeInfo attrInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (attrInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(attrInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(attrInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + attrInfo.getName());
         if (description == null) {
            description = attrInfo.getDescription();
         }

         return new ModelMBeanAttributeInfo(attrInfo.getName(), attrInfo.getType(), description, attrInfo.isReadable(), attrInfo.isWritable(), attrInfo.isIs(), descriptor);
      }
   }

   private static ModelMBeanConstructorInfo[] localizeModelMBeanConstructors(MBeanConstructorInfo[] constructors, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (constructors == null) {
         return null;
      } else {
         ModelMBeanConstructorInfo[] modelConstructors = new ModelMBeanConstructorInfo[constructors.length];

         for(int i = 0; i < constructors.length; ++i) {
            modelConstructors[i] = localizeModelMBeanConstructor((ModelMBeanConstructorInfo)ModelMBeanConstructorInfo.class.cast(constructors[i]), locale, parentBundle, baseDefaultResourceKey);
         }

         return modelConstructors;
      }
   }

   static ModelMBeanConstructorInfo localizeModelMBeanConstructor(ModelMBeanConstructorInfo constInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (constInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(constInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(constInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + constInfo.getName());
         if (description == null) {
            description = constInfo.getDescription();
         }

         MBeanParameterInfo[] params = constInfo.getSignature();
         MBeanParameterInfo[] newParams = GenericMBeanInfoLocalizationHelper.localizeParameters(params, locale, parentBundle, baseDefaultResourceKey + constInfo.getName() + ".");
         return new ModelMBeanConstructorInfo(constInfo.getName(), description, newParams, descriptor);
      }
   }

   private static ModelMBeanOperationInfo[] localizeModelMBeanOperations(MBeanOperationInfo[] operations, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (operations == null) {
         return null;
      } else {
         ModelMBeanOperationInfo[] modelOperations = new ModelMBeanOperationInfo[operations.length];

         for(int i = 0; i < operations.length; ++i) {
            modelOperations[i] = localizeModelMBeanOperation((ModelMBeanOperationInfo)ModelMBeanOperationInfo.class.cast(operations[i]), locale, parentBundle, baseDefaultResourceKey);
         }

         return modelOperations;
      }
   }

   static ModelMBeanOperationInfo localizeModelMBeanOperation(ModelMBeanOperationInfo operInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (operInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(operInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(operInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + operInfo.getName());
         if (description == null) {
            description = operInfo.getDescription();
         }

         MBeanParameterInfo[] params = operInfo.getSignature();
         MBeanParameterInfo[] newParams = GenericMBeanInfoLocalizationHelper.localizeParameters(params, locale, parentBundle, baseDefaultResourceKey + operInfo.getName() + ".");
         return new ModelMBeanOperationInfo(operInfo.getName(), description, newParams, operInfo.getReturnType(), operInfo.getImpact(), descriptor);
      }
   }

   private static ModelMBeanNotificationInfo[] localizeModelMBeanNotifications(MBeanNotificationInfo[] notifications, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (notifications == null) {
         return null;
      } else {
         ModelMBeanNotificationInfo[] modelNotifications = new ModelMBeanNotificationInfo[notifications.length];

         for(int i = 0; i < notifications.length; ++i) {
            modelNotifications[i] = localizeModelMBeanNotification((ModelMBeanNotificationInfo)ModelMBeanNotificationInfo.class.cast(notifications[i]), locale, parentBundle, baseDefaultResourceKey);
         }

         return modelNotifications;
      }
   }

   static ModelMBeanNotificationInfo localizeModelMBeanNotification(ModelMBeanNotificationInfo notifInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (notifInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(notifInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(notifInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + notifInfo.getName());
         if (description == null) {
            description = notifInfo.getDescription();
         }

         return new ModelMBeanNotificationInfo(notifInfo.getNotifTypes(), notifInfo.getName(), description, descriptor);
      }
   }
}
