package kodo.jdbc.ant;

import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class ReverseMappingToolTask extends org.apache.openjpa.jdbc.ant.ReverseMappingToolTask {
   protected ConfigurationImpl newConfiguration() {
      return new JDBCConsolidatedConfiguration();
   }
}
