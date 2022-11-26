package kodo.profile;

import com.solarmetric.profile.ProfilingInterface;
import kodo.manage.Management;

public class GUIProfiling extends LocalProfiling {
   private ProfilingInterface iface = null;

   public void startConfiguration() {
      super.startConfiguration();
      this.iface = Management.getInstance(this.conf).newVisualProfilingInterface();
   }

   public ProfilingInterface getProfilingInterface() {
      return this.iface;
   }
}
