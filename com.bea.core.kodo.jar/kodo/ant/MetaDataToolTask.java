package kodo.ant;

import kodo.conf.ConsolidatedConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class MetaDataToolTask extends org.apache.openjpa.ant.MetaDataToolTask {
   protected ConfigurationImpl newConfiguration() {
      return new ConsolidatedConfiguration();
   }
}
