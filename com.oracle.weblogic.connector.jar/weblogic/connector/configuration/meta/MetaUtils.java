package weblogic.connector.configuration.meta;

import weblogic.connector.utils.TypeUtils;
import weblogic.descriptor.DescriptorBean;

public class MetaUtils {
   public static boolean isPropertySet(Object bean, String property) {
      return ((DescriptorBean)bean).isSet(property);
   }

   public static boolean isValidPropertyType(Class clz) {
      return !clz.isPrimitive() && String.class != clz ? TypeUtils.isObjectTypeOfPrimitive(clz.getName()) : true;
   }

   public static String canonicalizeConfigPropertyType(Class clz) {
      String name = clz.getName();
      return clz.isPrimitive() ? TypeUtils.primitiveToObject(name) : name;
   }
}
