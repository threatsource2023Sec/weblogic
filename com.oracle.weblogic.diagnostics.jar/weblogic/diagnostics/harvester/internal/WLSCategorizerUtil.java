package weblogic.diagnostics.harvester.internal;

import javax.management.ObjectName;

abstract class WLSCategorizerUtil {
   private static final String WLS_JMX_DOMAIN = "com.bea";
   static ObjectName WLS_RTMBEAN_PATTERN;
   static ObjectName WLS_DOMAIN_PATTERN;

   static boolean isWLSRuntimeMBeanPattern(ObjectName mbean) {
      return WLS_RTMBEAN_PATTERN.apply(mbean);
   }

   static boolean isWLSMBeanPattern(ObjectName mbean) {
      return WLS_DOMAIN_PATTERN.apply(mbean);
   }

   static {
      try {
         WLS_RTMBEAN_PATTERN = new ObjectName("com.bea:Type=*Runtime,Name=*,*");
         WLS_DOMAIN_PATTERN = new ObjectName("com.bea:*");
      } catch (Exception var1) {
      }

   }
}
