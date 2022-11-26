package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

class WLSRuntimeCategorizerPlugin implements MBeanCategorizer.Plugin {
   private static final long serialVersionUID = 1L;

   public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
      boolean matches = false;

      try {
         matches = WLSCategorizerUtil.isWLSRuntimeMBeanPattern(mbean);
         return matches;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public String getTypeName(MBeanServerConnection mbs, ObjectName mbean) {
      String ret = null;
      String type = mbean.getKeyProperty("Type");
      if (type.contains(".")) {
         ret = type;
      } else if (type.endsWith("Runtime")) {
         ret = "weblogic.management.runtime." + type + "MBean";
      }

      return ret;
   }

   public String getCategoryName() {
      return "WLDFRuntimeMBeanCategorizer";
   }
}
