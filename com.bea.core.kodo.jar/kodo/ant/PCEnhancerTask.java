package kodo.ant;

import kodo.conf.ConsolidatedConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class PCEnhancerTask extends org.apache.openjpa.ant.PCEnhancerTask {
   protected ConfigurationImpl newConfiguration() {
      return new ConsolidatedConfiguration();
   }
}
