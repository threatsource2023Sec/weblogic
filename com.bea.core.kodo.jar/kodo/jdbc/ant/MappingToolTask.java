package kodo.jdbc.ant;

import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class MappingToolTask extends org.apache.openjpa.jdbc.ant.MappingToolTask {
   protected ConfigurationImpl newConfiguration() {
      return new JDBCConsolidatedConfiguration();
   }
}
