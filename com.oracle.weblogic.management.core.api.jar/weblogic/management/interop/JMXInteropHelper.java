package weblogic.management.interop;

public class JMXInteropHelper {
   private static String OBJECTNAME = "javax.management.ObjectName";
   private static String INTEROP_OBJECTNAME = "weblogic.management.interop.ObjectName";
   private static String WL_OBJECTNAME = "weblogic.management.WebLogicObjectName";
   private static String INTEROP_WL_OBJECTNAME = "weblogic.management.interop.WebLogicObjectName";
   private static String SUN_INTEROP_PROPERTY = System.getProperty("jmx.serial.form");
   private static boolean SUN_INTEROP_FLAG;

   public static String getJMXInteropClassName(String in) {
      if (in.equals(OBJECTNAME)) {
         return INTEROP_OBJECTNAME;
      } else {
         return in.equals(WL_OBJECTNAME) ? INTEROP_WL_OBJECTNAME : in;
      }
   }

   public static boolean isSunInteropPropertySpecified() {
      return SUN_INTEROP_FLAG;
   }

   static {
      SUN_INTEROP_FLAG = SUN_INTEROP_PROPERTY != null && SUN_INTEROP_PROPERTY.equals("1.0");
   }
}
