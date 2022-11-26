package kodo.ant;

import kodo.conf.ConsolidatedConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class ApplicationIdToolTask extends org.apache.openjpa.ant.ApplicationIdToolTask {
   protected ConfigurationImpl newConfiguration() {
      return new ConsolidatedConfiguration();
   }
}
