package kodo.profile;

import com.solarmetric.profile.ProfilingInterface;

public class ExportProfiling extends LocalProfiling {
   private ProfilingInterfaceTimedExport iface = null;

   public void startConfiguration() {
      super.startConfiguration();
      this.iface = new ProfilingInterfaceTimedExport(this.conf);
   }

   public ProfilingInterface getProfilingInterface() {
      return this.iface;
   }

   public void setIntervalMillis(long millis) {
      this.iface.setIntervalMillis(millis);
   }

   public void setBasename(String basename) {
      this.iface.setBasename(basename);
   }

   public void setUniqueNames(boolean unique) {
      this.iface.setUniqueNames(unique);
   }
}
