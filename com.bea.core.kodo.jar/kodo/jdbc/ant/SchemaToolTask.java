package kodo.jdbc.ant;

import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class SchemaToolTask extends org.apache.openjpa.jdbc.ant.SchemaToolTask {
   protected ConfigurationImpl newConfiguration() {
      return new JDBCConsolidatedConfiguration();
   }
}
