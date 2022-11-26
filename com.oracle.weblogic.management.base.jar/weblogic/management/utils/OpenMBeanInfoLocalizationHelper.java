package weblogic.management.utils;

import java.util.Locale;
import javax.management.Descriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfo;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfo;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;

public class OpenMBeanInfoLocalizationHelper {
   static MBeanInfo localizeOpenMBeanInfo(OpenMBeanInfoSupport info, Locale locale) {
      Descriptor descriptor = info.getDescriptor();
      String bundle = (String)descriptor.getFieldValue("descriptionResourceBundleBaseName");
      if ((bundle == null || bundle.length() == 0) && MBeanInfoLocalizationHelper.getDefaultResourceBundle(info, locale) != null) {
         bundle = MBeanInfoLocalizationHelper.getDefaultResourceBundleName(info);
      }

      String baseDefaultResourceKey = MBeanInfoLocalizationHelper.getBaseDefaultResourceKey(info);
      String defaultResourceKey = baseDefaultResourceKey + "mbean";
      String description = GenericMBeanInfoLocalizationHelper.localizeDescription(descriptor, locale, bundle, defaultResourceKey);
      if (description == null) {
         description = info.getDescription();
      }

      descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(info, locale, bundle);
      MBeanAttributeInfo[] attributes = info.getAttributes();
      MBeanConstructorInfo[] constructors = info.getConstructors();
      MBeanOperationInfo[] operations = info.getOperations();
      MBeanNotificationInfo[] notifications = info.getNotifications();
      OpenMBeanAttributeInfo[] openAttributes = localizeOpenMBeanAttributes(attributes, locale, bundle, baseDefaultResourceKey + "attribute.");
      OpenMBeanConstructorInfo[] openConstructors = localizeOpenMBeanConstructors(constructors, locale, bundle, baseDefaultResourceKey + "constructor.");
      OpenMBeanOperationInfo[] openOperations = localizeOpenMBeanOperations(operations, locale, bundle, baseDefaultResourceKey + "operation.");
      MBeanNotificationInfo[] mbeanNotifications = GenericMBeanInfoLocalizationHelper.localizeNotifications(notifications, locale, bundle, baseDefaultResourceKey + "notification.");
      return new OpenMBeanInfoSupport(info.getClassName(), description, openAttributes, openConstructors, openOperations, mbeanNotifications, descriptor);
   }

   private static OpenMBeanAttributeInfo[] localizeOpenMBeanAttributes(MBeanAttributeInfo[] attributes, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (attributes == null) {
         return null;
      } else {
         OpenMBeanAttributeInfo[] openAttributes = new OpenMBeanAttributeInfo[attributes.length];

         for(int i = 0; i < attributes.length; ++i) {
            OpenMBeanAttributeInfoSupport attrInfo = (OpenMBeanAttributeInfoSupport)OpenMBeanAttributeInfoSupport.class.cast(attributes[i]);
            openAttributes[i] = localizeOpenMBeanAttribute(attrInfo, locale, parentBundle, baseDefaultResourceKey);
         }

         return openAttributes;
      }
   }

   static OpenMBeanAttributeInfoSupport localizeOpenMBeanAttribute(OpenMBeanAttributeInfoSupport attrInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (attrInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(attrInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(attrInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + attrInfo.getName());
         if (description == null) {
            description = attrInfo.getDescription();
         }

         return new OpenMBeanAttributeInfoSupport(attrInfo.getName(), description, attrInfo.getOpenType(), attrInfo.isReadable(), attrInfo.isWritable(), attrInfo.isIs(), descriptor);
      }
   }

   private static OpenMBeanConstructorInfo[] localizeOpenMBeanConstructors(MBeanConstructorInfo[] constructors, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (constructors == null) {
         return null;
      } else {
         OpenMBeanConstructorInfo[] openConstructors = new OpenMBeanConstructorInfo[constructors.length];

         for(int i = 0; i < constructors.length; ++i) {
            OpenMBeanConstructorInfoSupport constInfo = (OpenMBeanConstructorInfoSupport)OpenMBeanConstructorInfoSupport.class.cast(constructors[i]);
            openConstructors[i] = localizeOpenMBeanConstructor(constInfo, locale, parentBundle, baseDefaultResourceKey);
         }

         return openConstructors;
      }
   }

   static OpenMBeanConstructorInfoSupport localizeOpenMBeanConstructor(OpenMBeanConstructorInfoSupport constInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (constInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(constInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(constInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + constInfo.getName());
         if (description == null) {
            description = constInfo.getDescription();
         }

         MBeanParameterInfo[] params = constInfo.getSignature();
         OpenMBeanParameterInfo[] newParams = localizeOpenMBeanParameters(params, locale, parentBundle, baseDefaultResourceKey + constInfo.getName() + ".");
         return new OpenMBeanConstructorInfoSupport(constInfo.getName(), description, newParams, descriptor);
      }
   }

   private static OpenMBeanOperationInfo[] localizeOpenMBeanOperations(MBeanOperationInfo[] operations, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (operations == null) {
         return null;
      } else {
         OpenMBeanOperationInfo[] openOperations = new OpenMBeanOperationInfo[operations.length];

         for(int i = 0; i < operations.length; ++i) {
            OpenMBeanOperationInfoSupport operInfo = (OpenMBeanOperationInfoSupport)OpenMBeanOperationInfoSupport.class.cast(operations[i]);
            openOperations[i] = localizeOpenMBeanOperation(operInfo, locale, parentBundle, baseDefaultResourceKey);
         }

         return openOperations;
      }
   }

   static OpenMBeanOperationInfoSupport localizeOpenMBeanOperation(OpenMBeanOperationInfoSupport operInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (operInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(operInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(operInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + operInfo.getName());
         if (description == null) {
            description = operInfo.getDescription();
         }

         MBeanParameterInfo[] params = operInfo.getSignature();
         OpenMBeanParameterInfo[] newParams = localizeOpenMBeanParameters(params, locale, parentBundle, baseDefaultResourceKey + operInfo.getName() + ".");
         return new OpenMBeanOperationInfoSupport(operInfo.getName(), description, newParams, operInfo.getReturnOpenType(), operInfo.getImpact(), descriptor);
      }
   }

   private static OpenMBeanParameterInfo[] localizeOpenMBeanParameters(MBeanParameterInfo[] params, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (params == null) {
         return null;
      } else {
         OpenMBeanParameterInfo[] newParams = new OpenMBeanParameterInfo[params.length];

         for(int i = 0; i < params.length; ++i) {
            OpenMBeanParameterInfoSupport paramInfo = (OpenMBeanParameterInfoSupport)OpenMBeanParameterInfoSupport.class.cast(params[i]);
            newParams[i] = localizeOpenMBeanParameter(paramInfo, locale, parentBundle, baseDefaultResourceKey);
         }

         return newParams;
      }
   }

   static OpenMBeanParameterInfoSupport localizeOpenMBeanParameter(OpenMBeanParameterInfoSupport paramInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (paramInfo == null) {
         return null;
      } else {
         Descriptor descriptor = GenericMBeanInfoLocalizationHelper.localizeDescriptor(paramInfo, locale, parentBundle);
         String description = GenericMBeanInfoLocalizationHelper.localizeDescription(paramInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + paramInfo.getName());
         if (description == null) {
            description = paramInfo.getDescription();
         }

         return new OpenMBeanParameterInfoSupport(paramInfo.getName(), description, paramInfo.getOpenType(), descriptor);
      }
   }
}
