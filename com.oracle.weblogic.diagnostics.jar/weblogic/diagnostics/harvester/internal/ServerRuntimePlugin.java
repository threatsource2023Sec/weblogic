package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.mbean.typing.MBeanCategorizerPlugins;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

class ServerRuntimePlugin extends MBeanCategorizerPlugins.WLSPlugin {
   public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
      try {
         return mbs.isInstanceOf(mbean, "weblogic.management.runtime.RuntimeMBean");
      } catch (InstanceNotFoundException var4) {
         return false;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }
}
