package weblogic.management.rest.lib.bean.utils;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBean;

public class DescriptorUtils {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(DescriptorUtils.class);
   private static final String ARRAY_TYPE_PREFIX = "[L";
   private static final String ARRAY_TYPE_SUFFIX = ";";
   private static final String[] INTERNAL_ATTR_NAMES = new String[]{"MBeanInfo", "Parent", "ObjectName"};
   private static final String[] INTERNAL_METHOD_NAMES = new String[]{"wls_getDisplayName", "isSet", "unSet", "preDeregister", "restoreDefaultValue", "freezeCurrentValue", "isInherited", "getInheritedProperties"};

   public static BeanInfo getBeanInfo(Object bean) throws Exception {
      return ManagementService.getBeanInfoAccess().getBeanInfoForInstance(bean, false, (String)null);
   }

   public static boolean isNonPublicBeanTypeInCurrentVersion(BeanType type, PropertyDescriptor pd) throws Exception {
      VersionVisibility versionVisibility = VersionVisibility.getVersionVisibility(type.getVersionVisibility(), pd);
      return versionVisibility != null && versionVisibility.isVisible(SupportedVersions.LATEST_VERSION_NUMBER) ? isNonPublicBeanTypeInCurrentVersion(pd.getPropertyType()) : false;
   }

   public static boolean isNonPublicBeanTypeInCurrentVersion(Class clazz) throws Exception {
      if (clazz.isArray()) {
         return isNonPublicBeanTypeInCurrentVersion(clazz.getComponentType());
      } else {
         String typeName = clazz.getName();
         if (isBaseBeanType(typeName)) {
            return false;
         } else {
            BeanInfo info = ManagementService.getBeanInfoAccess().getBeanInfoForInterface(typeName, false, (String)null);
            if (info == null) {
               return false;
            } else {
               FeatureDescriptor desc = info.getBeanDescriptor();
               if (isInternal(desc)) {
                  return false;
               } else {
                  return isExcluded(desc) || isObsolete(desc) || getStringField(desc, "deprecated") != null;
               }
            }
         }
      }
   }

   public static boolean isPublicAttribute(PropertyDescriptor info) {
      for(int i = 0; i < INTERNAL_ATTR_NAMES.length; ++i) {
         if (INTERNAL_ATTR_NAMES[i].equals(info.getName())) {
            return false;
         }
      }

      return isPublic(info);
   }

   public static boolean isPublicMethod(MethodDescriptor info) {
      for(int i = 0; i < INTERNAL_METHOD_NAMES.length; ++i) {
         if (INTERNAL_METHOD_NAMES[i].equals(info.getName())) {
            return false;
         }
      }

      return isPublic(info);
   }

   public static String getRelationship(BeanType type, PropertyDescriptor info) throws Exception {
      String field = "restRelationship";
      String rel = getStringField(info, field);
      if (rel == null) {
         field = "relationship";
         rel = getStringField(info, field);
      }

      if (!"containment".equals(rel) && !"reference".equals(rel)) {
         type.addPropertyProblem(info, "invalid " + field + ": " + rel);
         return null;
      } else {
         return rel;
      }
   }

   public static String getDescription(FeatureDescriptor descriptor) {
      return getStringField(descriptor, "description");
   }

   public static String getFinder(BeanType type, PropertyDescriptor pd) throws Exception {
      MethodDescriptor[] var2 = type.getBeanInfo().getMethodDescriptors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         MethodDescriptor md = var2[var4];
         if (isPublicMethod(md) && "finder".equals(getStringField(md, "role")) && pd.getName().equals(getStringField(md, "property"))) {
            return md.getName();
         }
      }

      return null;
   }

   public static String[] getRolesAllowed(FeatureDescriptor descriptor) {
      return (String[])((String[])descriptor.getValue("rolesAllowed"));
   }

   public static String getStringField(FeatureDescriptor descriptor, String field) {
      return (String)descriptor.getValue(field);
   }

   public static boolean getBooleanField(FeatureDescriptor descriptor, String field) {
      Boolean val = (Boolean)descriptor.getValue(field);
      return val != null ? val : false;
   }

   public static Number getNumberField(FeatureDescriptor descriptor, String field) throws Exception {
      return (Number)descriptor.getValue(field);
   }

   public static String getBeanTypeName(PropertyDescriptor info) throws Exception {
      if (isSingletonBean(info)) {
         return getInterfaceClassName(info);
      } else if (isBeanCollection(info)) {
         String arrayType = getInterfaceClassName(info);
         if (arrayType.startsWith("[L") && arrayType.endsWith(";")) {
            return arrayType.substring("[L".length(), arrayType.length() - ";".length());
         } else {
            throw new AssertionError("Type " + arrayType + " is not an array type");
         }
      } else {
         return null;
      }
   }

   public static String getInterfaceClassName(BeanDescriptor descriptor) {
      return getStringField(descriptor, "interfaceclassname");
   }

   public static String getInterfaceClassName(PropertyDescriptor descriptor) {
      return descriptor.getPropertyType().getName();
   }

   public static boolean isSingletonBean(PropertyDescriptor info) throws Exception {
      return BeanUtils.isBeanClass(info.getPropertyType());
   }

   public static boolean isBeanCollection(PropertyDescriptor info) throws Exception {
      Class propertyType = info.getPropertyType();
      return propertyType.isArray() ? BeanUtils.isBeanClass(propertyType.getComponentType()) : false;
   }

   public static Boolean getVisibleToPartitions(FeatureDescriptor descriptor) throws Exception {
      String v2p = getStringField(descriptor, "VisibleToPartitions");
      if (v2p == null) {
         return null;
      } else if ("ALWAYS".equals(v2p)) {
         return true;
      } else if ("NEVER".equals(v2p)) {
         return false;
      } else {
         throw new AssertionError("Illegal VisibleToPartitions value: " + v2p);
      }
   }

   public static boolean isRuntimeMBean(PropertyDescriptor info) {
      Class propertyType = info.getPropertyType();
      if (propertyType.isArray()) {
         propertyType = propertyType.getComponentType();
      }

      return RuntimeMBean.class.isAssignableFrom(propertyType);
   }

   private static boolean isPublic(FeatureDescriptor desc) {
      if (isInternal(desc)) {
         return true;
      } else {
         return !isExcluded(desc) && !isDeprecated(desc) && !isObsolete(desc);
      }
   }

   public static boolean isInternal(FeatureDescriptor desc) {
      return getStringField(desc, "restInternal") != null;
   }

   private static boolean isExcluded(FeatureDescriptor desc) {
      return getStringField(desc, "excludeFromRest") != null || getBooleanField(desc, "exclude");
   }

   private static boolean isObsolete(FeatureDescriptor desc) {
      return desc.getValue("obsolete") != null;
   }

   private static boolean isDeprecated(FeatureDescriptor desc) {
      return getDeprecatedVersionNumber(desc) == 0;
   }

   public static int getDeprecatedVersionNumber(FeatureDescriptor desc) {
      return getVersionNumber(desc, "deprecated");
   }

   private static int getVersionNumber(FeatureDescriptor desc, String field) {
      String val = getStringField(desc, field);
      if (val == null) {
         return -1;
      } else {
         int idx = val.indexOf(" ");
         if (idx != -1) {
            val = val.substring(0, idx);
         }

         int version = SupportedVersions.getVersionNumber(val);
         return version == -1 ? 0 : version;
      }
   }

   public static boolean isAbstract(BeanDescriptor desc) {
      return getBooleanField(desc, "abstract");
   }

   public static boolean isOrdered(PropertyDescriptor info) {
      return false;
   }

   public static boolean isValueObject(BeanInfo info) {
      return getBooleanField(info.getBeanDescriptor(), "valueObject");
   }

   public static boolean isBeanType(String typeName) throws Exception {
      return getTypeInfo(typeName, false) != null;
   }

   public static BeanInfo getBeanTypeInfo(String typeName) throws Exception {
      return getTypeInfo(typeName, true);
   }

   private static BeanInfo getTypeInfo(String typeName, boolean reportNoBeanInfo) throws Exception {
      if (isBaseBeanType(typeName)) {
         return null;
      } else {
         BeanInfo info = ManagementService.getBeanInfoAccess().getBeanInfoForInterface(typeName, false, (String)null);
         if (info == null) {
            if (reportNoBeanInfo && DEBUG.isDebugEnabled()) {
               DEBUG.debug("BeanInfo not found: " + typeName);
            }

            return null;
         } else {
            BeanDescriptor bd = info.getBeanDescriptor();
            return !isPublic(bd) ? null : info;
         }
      }
   }

   private static boolean isBaseBeanType(String typeName) {
      if (WebLogicMBean.class.getName().equals(typeName)) {
         return true;
      } else {
         return DescriptorBean.class.getName().equals(typeName);
      }
   }

   public static String getSingularRestName(FeatureDescriptor fd) {
      String customName = getStringField(fd, "restSingularName");
      return customName != null ? customName : StringUtil.getSingular(getRestName(fd));
   }

   public static String getRestName(FeatureDescriptor fd) {
      String customName = getStringField(fd, "restName");
      return customName != null ? customName : getRestName(fd.getName());
   }

   public static String getRestName(String beanName) {
      int count;
      for(count = 0; count < beanName.length() && Character.isUpperCase(beanName.charAt(count)); ++count) {
      }

      if (count == beanName.length()) {
         return beanName;
      } else if (count == 0) {
         return beanName;
      } else {
         return count == 1 ? beanName.substring(0, count).toLowerCase() + beanName.substring(count) : beanName;
      }
   }
}
