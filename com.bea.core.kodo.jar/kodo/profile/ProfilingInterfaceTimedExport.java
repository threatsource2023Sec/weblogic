package kodo.profile;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAVersion;

public class ProfilingInterfaceTimedExport extends com.solarmetric.profile.ProfilingInterfaceTimedExport {
   public ProfilingInterfaceTimedExport(OpenJPAConfiguration conf) {
      super(conf);
   }

   public String getVersionString() {
      return (new OpenJPAVersion()).toString();
   }
}
