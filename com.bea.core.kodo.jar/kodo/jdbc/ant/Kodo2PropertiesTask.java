package kodo.jdbc.ant;

import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class Kodo2PropertiesTask extends AbstractTask {
   protected String file = null;

   public void setFile(String file) {
      this.file = file;
   }

   protected ConfigurationImpl newConfiguration() {
      return new JDBCConsolidatedConfiguration();
   }

   protected void executeOn(String[] files) throws Exception {
   }
}
