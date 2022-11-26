package weblogic.management.jmx.modelmbean;

import java.util.HashMap;
import java.util.Map;

public class DescriptorMapper {
   private static final String DOMAIN_NAME = "com.bea.";
   private static final Map beanInfoToJMX = new HashMap();

   public static String beanInfoToJMX(String annotation) {
      if (beanInfoToJMX.containsKey(annotation)) {
         return (String)beanInfoToJMX.get(annotation);
      } else {
         String result = "com.bea." + annotation;
         result.intern();
         beanInfoToJMX.put(annotation, result);
         return result;
      }
   }

   static {
      beanInfoToJMX.put("default", "defaultValue");
      beanInfoToJMX.put("legalNull", "isNullable");
      beanInfoToJMX.put("legalMin", "minValue");
      beanInfoToJMX.put("legalMax", "maxValue");
      beanInfoToJMX.put("legalValues", "legalValues");
      beanInfoToJMX.put("openType", "openType");
      beanInfoToJMX.put("unit", "unit");
      beanInfoToJMX.put("deprecated", "deprecated");
      beanInfoToJMX.put("since", "since");
      beanInfoToJMX.put("log", "log");
      beanInfoToJMX.put("descriptorType", "descriptorType");
      beanInfoToJMX.put("persistPolicy", "persistPolicy");
      beanInfoToJMX.put("package", "package");
      beanInfoToJMX.put("visibility", "visiblity");
      beanInfoToJMX.put("displayName", "displayName");
      beanInfoToJMX.put("interfaceclassname", "interfaceclassname");
      beanInfoToJMX.put("excludeFromRest", (Object)null);
      beanInfoToJMX.put("restInternal", (Object)null);
      beanInfoToJMX.put("restName", (Object)null);
      beanInfoToJMX.put("restRelationship", (Object)null);
      beanInfoToJMX.put("restReadOnly", (Object)null);
      beanInfoToJMX.put("restDerivedDefault", (Object)null);
      beanInfoToJMX.put("restProductionModeDefault", (Object)null);
   }
}
