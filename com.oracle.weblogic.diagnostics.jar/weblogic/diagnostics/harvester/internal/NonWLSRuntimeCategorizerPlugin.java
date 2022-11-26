package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.mbean.typing.MBeanCategorizerPlugins;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

class NonWLSRuntimeCategorizerPlugin extends MBeanCategorizerPlugins.NonWLSPlugin {
   private static final long serialVersionUID = 1L;

   public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
      boolean matches = false;

      try {
         matches = !WLSCategorizerUtil.isWLSMBeanPattern(mbean);
         return matches;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   public String getCategoryName() {
      return "WLDFNonWLSRuntimeCategorizerPlugin";
   }
}
