package weblogic.management.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.management.Descriptor;
import javax.management.MBeanInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.openmbean.OpenMBeanInfoSupport;

public class MBeanInfoLocalizationHelper {
   public static MBeanInfo localizeMBeanInfo(MBeanInfo info, Locale locale) {
      Descriptor descriptor = info.getDescriptor();
      if (descriptor == null) {
         return info;
      } else {
         String bundle = (String)descriptor.getFieldValue("descriptionResourceBundleBaseName");
         if (bundle == null && getDefaultResourceBundle(info, locale) == null) {
            return info;
         } else if (info instanceof ModelMBeanInfo) {
            return ModelMBeanInfoLocalizationHelper.localizeModelMBeanInfo((ModelMBeanInfo)ModelMBeanInfo.class.cast(info), locale);
         } else {
            return info instanceof OpenMBeanInfoSupport ? OpenMBeanInfoLocalizationHelper.localizeOpenMBeanInfo((OpenMBeanInfoSupport)OpenMBeanInfoSupport.class.cast(info), locale) : GenericMBeanInfoLocalizationHelper.localizeGenericMBeanInfo(info, locale);
         }
      }
   }

   static String getDefaultResourceBundleName(MBeanInfo info) {
      Descriptor descriptor = info.getDescriptor();
      String interfaceName = (String)descriptor.getFieldValue("interfaceClassName");
      if (interfaceName == null) {
         interfaceName = info.getClassName();
      }

      String interfacePackageName = interfaceName.substring(0, interfaceName.lastIndexOf(46) + 1);
      String defaultBundleName = interfacePackageName + "MBeanDescriptions";
      return defaultBundleName;
   }

   static String getBaseDefaultResourceKey(MBeanInfo info) {
      Descriptor descriptor = info.getDescriptor();
      String interfaceName = (String)descriptor.getFieldValue("interfaceClassName");
      if (interfaceName == null) {
         interfaceName = info.getClassName();
      }

      String baseDefaultResourceKey = interfaceName.substring(interfaceName.lastIndexOf(46) + 1) + ".";
      return baseDefaultResourceKey;
   }

   static ResourceBundle getDefaultResourceBundle(MBeanInfo info, Locale locale) {
      try {
         return ResourceBundle.getBundle(getDefaultResourceBundleName(info), locale, Thread.currentThread().getContextClassLoader());
      } catch (MissingResourceException var3) {
         return null;
      }
   }
}
