package kodo.jdbc.ant;

import java.io.IOException;
import kodo.jdbc.conf.JDBCConsolidatedConfiguration;
import org.apache.openjpa.lib.ant.AbstractTask;
import org.apache.openjpa.lib.conf.ConfigurationImpl;

public class Kodo2MigratorTask extends AbstractTask {
   protected String file = null;

   public void setFile(String file) {
      this.file = file;
   }

   public Object createConfig() {
      return null;
   }

   protected ConfigurationImpl newConfiguration() {
      return new JDBCConsolidatedConfiguration();
   }

   protected void executeOn(String[] files) throws IOException {
   }
}
