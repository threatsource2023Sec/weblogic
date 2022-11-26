package kodo.jdbc.profile.gui;

import org.apache.openjpa.conf.OpenJPAConfiguration;

public class ProfilingInterface extends kodo.profile.gui.ProfilingInterface {
   public ProfilingInterface() {
   }

   public ProfilingInterface(OpenJPAConfiguration conf) {
      super(conf);
   }

   public String[] getInitialCategoryNames() {
      return new String[]{"SQL"};
   }
}
