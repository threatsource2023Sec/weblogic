package weblogic.management.utils;

import java.util.Arrays;
import java.util.Locale;
import java.util.MissingResourceException;
import javax.management.Descriptor;
import javax.management.DescriptorRead;
import javax.management.ImmutableDescriptor;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;
import weblogic.diagnostics.debug.DebugLogger;

class GenericMBeanInfoLocalizationHelper {
   public static final String DESCRIPTION_RESOURCE_BUNDLE_BASE_NAME = "descriptionResourceBundleBaseName";
   public static final String DESCRIPTION_RESOURCE_KEY = "descriptionResourceKey";
   public static final String DESCRIPTION_DISPLAY_NAME_KEY = "descriptionDisplayNameKey";
   private static DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugMBeanLocalization");

   static String localizeDescription(Descriptor descriptor, Locale locale, String parentBundle, String defaultResourceKey) {
      String description = null;
      if (descriptor != null) {
         String bundle = (String)descriptor.getFieldValue("descriptionResourceBundleBaseName");
         String resource = (String)descriptor.getFieldValue("descriptionResourceKey");
         if (bundle == null || bundle.length() == 0) {
            bundle = parentBundle;
         }

         if (resource == null || resource.length() == 0) {
            resource = defaultResourceKey;
         }

         if (bundle != null && bundle.length() > 0 && resource != null && resource.length() > 0) {
            MessageLocalizationHelper locHelper = null;

            try {
               locHelper = new MessageLocalizationHelper(bundle, locale);
            } catch (MissingResourceException var10) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("GenericMBeanInfoLocalizationHelper.localizeDescription(): : no resource bundle can be found for the specified base name " + bundle);
               }
            }

            if (locHelper != null) {
               try {
                  description = locHelper.getLocalizedMessage(resource);
               } catch (MissingResourceException var9) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("GenericMBeanInfoLocalizationHelper.localizeDescription(): : no object can be found for the given key " + resource);
                  }
               }
            }
         }
      }

      return description;
   }

   static Descriptor localizeDescriptor(DescriptorRead descriptorRead, Locale locale, String defaultResourceBundle) {
      Descriptor descriptor = descriptorRead.getDescriptor();
      String displayNameKey = (String)descriptor.getFieldValue("descriptionDisplayNameKey");
      if (displayNameKey != null && displayNameKey.length() != 0) {
         String bundle = (String)descriptor.getFieldValue("descriptionResourceBundleBaseName");
         if (bundle == null || bundle.length() == 0) {
            bundle = defaultResourceBundle;
         }

         if (defaultResourceBundle != null && defaultResourceBundle.length() != 0) {
            MessageLocalizationHelper locHelper = null;

            try {
               locHelper = new MessageLocalizationHelper(bundle, locale);
            } catch (MissingResourceException var12) {
               if (DEBUG_LOGGER.isDebugEnabled()) {
                  DEBUG_LOGGER.debug("MBeanInfoLocalizationHelper.localizeDescriptor(): : no resource bundle can be found for the specified base name " + bundle);
               }
            }

            String localizedDisplayName = null;
            if (locHelper != null) {
               try {
                  localizedDisplayName = locHelper.getLocalizedMessage(displayNameKey);
               } catch (MissingResourceException var11) {
                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("MBeanInfoLocalizationHelper.localizeDescriptor(): : no object can be found for the given key " + displayNameKey);
                  }
               }
            }

            String[] fieldNames = descriptor.getFieldNames();
            if (localizedDisplayName != null && descriptor.getFieldValue("displayName") == null && descriptor.getFieldValue("displayname") == null) {
               fieldNames = (String[])Arrays.copyOf(fieldNames, fieldNames.length + 1);
               fieldNames[fieldNames.length - 1] = "displayName";
            }

            Object[] fieldValues = new Object[fieldNames.length];

            for(int i = 0; i < fieldNames.length; ++i) {
               fieldValues[i] = descriptor.getFieldValue(fieldNames[i]);
               if ("displayname".equals(fieldNames[i])) {
                  fieldNames[i] = "displayName";
               }

               if (localizedDisplayName != null && "displayName".equals(fieldNames[i])) {
                  fieldValues[i] = localizedDisplayName;
               }
            }

            Descriptor newDescriptor = null;
            if (descriptor instanceof ImmutableDescriptor) {
               newDescriptor = new ImmutableDescriptor(fieldNames, fieldValues);
            } else {
               newDescriptor = new DescriptorSupport(fieldNames, fieldValues);
            }

            return (Descriptor)newDescriptor;
         } else {
            return descriptor;
         }
      } else {
         return descriptor;
      }
   }

   static MBeanInfo localizeGenericMBeanInfo(MBeanInfo info, Locale locale) {
      Descriptor descriptor = info.getDescriptor();
      String bundle = (String)descriptor.getFieldValue("descriptionResourceBundleBaseName");
      if ((bundle == null || bundle.length() == 0) && MBeanInfoLocalizationHelper.getDefaultResourceBundle(info, locale) != null) {
         bundle = MBeanInfoLocalizationHelper.getDefaultResourceBundleName(info);
      }

      String baseDefaultResourceKey = MBeanInfoLocalizationHelper.getBaseDefaultResourceKey(info);
      String defaultResourceKey = baseDefaultResourceKey + "mbean";
      String description = localizeDescription(info.getDescriptor(), locale, bundle, defaultResourceKey);
      if (description == null) {
         description = info.getDescription();
      }

      descriptor = localizeDescriptor(info, locale, bundle);
      MBeanAttributeInfo[] attributes = info.getAttributes();
      MBeanConstructorInfo[] constructors = info.getConstructors();
      MBeanOperationInfo[] operations = info.getOperations();
      MBeanNotificationInfo[] notifications = info.getNotifications();
      MBeanAttributeInfo[] mbeanAttributes = localizeAttributes(attributes, locale, bundle, baseDefaultResourceKey + "attribute.");
      MBeanConstructorInfo[] mbeanConstructors = localizeConstructors(constructors, locale, bundle, baseDefaultResourceKey + "constructor.");
      MBeanOperationInfo[] mbeanOperations = localizeOperations(operations, locale, bundle, baseDefaultResourceKey + "operation.");
      MBeanNotificationInfo[] mbeanNotifications = localizeNotifications(notifications, locale, bundle, baseDefaultResourceKey + "notification.");
      return new MBeanInfo(info.getClassName(), description, mbeanAttributes, mbeanConstructors, mbeanOperations, mbeanNotifications, descriptor);
   }

   private static MBeanAttributeInfo[] localizeAttributes(MBeanAttributeInfo[] attributes, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (attributes == null) {
         return null;
      } else {
         MBeanAttributeInfo[] mbeanAttributes = new MBeanAttributeInfo[attributes.length];

         for(int i = 0; i < attributes.length; ++i) {
            MBeanAttributeInfo attrInfo = attributes[i];
            if (attrInfo instanceof ModelMBeanAttributeInfo) {
               mbeanAttributes[i] = ModelMBeanInfoLocalizationHelper.localizeModelMBeanAttribute((ModelMBeanAttributeInfo)ModelMBeanAttributeInfo.class.cast(attrInfo), locale, parentBundle, baseDefaultResourceKey);
            } else if (attrInfo instanceof OpenMBeanAttributeInfoSupport) {
               mbeanAttributes[i] = OpenMBeanInfoLocalizationHelper.localizeOpenMBeanAttribute((OpenMBeanAttributeInfoSupport)OpenMBeanAttributeInfoSupport.class.cast(attrInfo), locale, parentBundle, baseDefaultResourceKey);
            } else {
               mbeanAttributes[i] = localizeMBeanAttribute(attrInfo, locale, parentBundle, baseDefaultResourceKey);
            }
         }

         return mbeanAttributes;
      }
   }

   private static MBeanAttributeInfo localizeMBeanAttribute(MBeanAttributeInfo attrInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (attrInfo == null) {
         return null;
      } else {
         Descriptor descriptor = localizeDescriptor(attrInfo, locale, parentBundle);
         String description = localizeDescription(attrInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + attrInfo.getName());
         if (description == null) {
            description = attrInfo.getDescription();
         }

         return new MBeanAttributeInfo(attrInfo.getName(), attrInfo.getType(), description, attrInfo.isReadable(), attrInfo.isWritable(), attrInfo.isIs(), descriptor);
      }
   }

   private static MBeanConstructorInfo[] localizeConstructors(MBeanConstructorInfo[] constructors, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (constructors == null) {
         return null;
      } else {
         MBeanConstructorInfo[] mbeanConstructors = new MBeanConstructorInfo[constructors.length];

         for(int i = 0; i < constructors.length; ++i) {
            MBeanConstructorInfo constInfo = constructors[i];
            if (constInfo instanceof ModelMBeanConstructorInfo) {
               mbeanConstructors[i] = ModelMBeanInfoLocalizationHelper.localizeModelMBeanConstructor((ModelMBeanConstructorInfo)ModelMBeanConstructorInfo.class.cast(constInfo), locale, parentBundle, baseDefaultResourceKey);
            } else if (constInfo instanceof OpenMBeanConstructorInfoSupport) {
               mbeanConstructors[i] = OpenMBeanInfoLocalizationHelper.localizeOpenMBeanConstructor((OpenMBeanConstructorInfoSupport)OpenMBeanConstructorInfoSupport.class.cast(constInfo), locale, parentBundle, baseDefaultResourceKey);
            } else {
               mbeanConstructors[i] = localizeMBeanConstructor(constInfo, locale, parentBundle, baseDefaultResourceKey);
            }
         }

         return mbeanConstructors;
      }
   }

   static MBeanConstructorInfo localizeMBeanConstructor(MBeanConstructorInfo constInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (constInfo == null) {
         return null;
      } else {
         Descriptor descriptor = localizeDescriptor(constInfo, locale, parentBundle);
         String description = localizeDescription(constInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + constInfo.getName());
         if (description == null) {
            description = constInfo.getDescription();
         }

         MBeanParameterInfo[] params = constInfo.getSignature();
         MBeanParameterInfo[] newParams = localizeParameters(params, locale, parentBundle, baseDefaultResourceKey + constInfo.getName() + ".");
         return new MBeanConstructorInfo(constInfo.getName(), description, newParams, descriptor);
      }
   }

   private static MBeanOperationInfo[] localizeOperations(MBeanOperationInfo[] operations, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (operations == null) {
         return null;
      } else {
         MBeanOperationInfo[] mbeanOperations = new MBeanOperationInfo[operations.length];

         for(int i = 0; i < operations.length; ++i) {
            MBeanOperationInfo operInfo = operations[i];
            if (operInfo instanceof ModelMBeanOperationInfo) {
               mbeanOperations[i] = ModelMBeanInfoLocalizationHelper.localizeModelMBeanOperation((ModelMBeanOperationInfo)ModelMBeanOperationInfo.class.cast(operInfo), locale, parentBundle, baseDefaultResourceKey);
            } else if (operInfo instanceof OpenMBeanOperationInfoSupport) {
               mbeanOperations[i] = OpenMBeanInfoLocalizationHelper.localizeOpenMBeanOperation((OpenMBeanOperationInfoSupport)OpenMBeanOperationInfoSupport.class.cast(operInfo), locale, parentBundle, baseDefaultResourceKey);
            } else {
               mbeanOperations[i] = localizeMBeanOperation(operInfo, locale, parentBundle, baseDefaultResourceKey);
            }
         }

         return mbeanOperations;
      }
   }

   static MBeanOperationInfo localizeMBeanOperation(MBeanOperationInfo operInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (operInfo == null) {
         return null;
      } else {
         Descriptor descriptor = localizeDescriptor(operInfo, locale, parentBundle);
         String description = localizeDescription(operInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + operInfo.getName());
         if (description == null) {
            description = operInfo.getDescription();
         }

         MBeanParameterInfo[] params = operInfo.getSignature();
         MBeanParameterInfo[] newParams = localizeParameters(params, locale, parentBundle, baseDefaultResourceKey + operInfo.getName() + ".");
         return new MBeanOperationInfo(operInfo.getName(), description, newParams, operInfo.getReturnType(), operInfo.getImpact(), descriptor);
      }
   }

   static MBeanNotificationInfo[] localizeNotifications(MBeanNotificationInfo[] notifications, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (notifications == null) {
         return null;
      } else {
         MBeanNotificationInfo[] mbeanNotifications = new MBeanNotificationInfo[notifications.length];

         for(int i = 0; i < notifications.length; ++i) {
            MBeanNotificationInfo notifInfo = notifications[i];
            if (notifInfo instanceof ModelMBeanNotificationInfo) {
               mbeanNotifications[i] = ModelMBeanInfoLocalizationHelper.localizeModelMBeanNotification((ModelMBeanNotificationInfo)ModelMBeanNotificationInfo.class.cast(notifInfo), locale, parentBundle, baseDefaultResourceKey);
            } else {
               mbeanNotifications[i] = localizeMBeanNotification(notifInfo, locale, parentBundle, baseDefaultResourceKey);
            }
         }

         return mbeanNotifications;
      }
   }

   private static MBeanNotificationInfo localizeMBeanNotification(MBeanNotificationInfo notifInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (notifInfo == null) {
         return null;
      } else {
         Descriptor descriptor = localizeDescriptor(notifInfo, locale, parentBundle);
         String description = localizeDescription(notifInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + notifInfo.getName());
         if (description == null) {
            description = notifInfo.getDescription();
         }

         return new MBeanNotificationInfo(notifInfo.getNotifTypes(), notifInfo.getName(), description, descriptor);
      }
   }

   static MBeanParameterInfo[] localizeParameters(MBeanParameterInfo[] params, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (params == null) {
         return null;
      } else {
         MBeanParameterInfo[] newParams = new MBeanParameterInfo[params.length];

         for(int i = 0; i < params.length; ++i) {
            MBeanParameterInfo paramInfo = params[i];
            if (paramInfo instanceof OpenMBeanParameterInfoSupport) {
               newParams[i] = OpenMBeanInfoLocalizationHelper.localizeOpenMBeanParameter((OpenMBeanParameterInfoSupport)OpenMBeanParameterInfoSupport.class.cast(paramInfo), locale, parentBundle, baseDefaultResourceKey);
            } else {
               newParams[i] = localizeMBeanParameter(paramInfo, locale, parentBundle, baseDefaultResourceKey);
            }
         }

         return newParams;
      }
   }

   private static MBeanParameterInfo localizeMBeanParameter(MBeanParameterInfo paramInfo, Locale locale, String parentBundle, String baseDefaultResourceKey) {
      if (paramInfo == null) {
         return null;
      } else {
         Descriptor descriptor = localizeDescriptor(paramInfo, locale, parentBundle);
         String description = localizeDescription(paramInfo.getDescriptor(), locale, parentBundle, baseDefaultResourceKey + paramInfo.getName());
         if (description == null) {
            description = paramInfo.getDescription();
         }

         return new MBeanParameterInfo(paramInfo.getName(), paramInfo.getType(), description, descriptor);
      }
   }
}
