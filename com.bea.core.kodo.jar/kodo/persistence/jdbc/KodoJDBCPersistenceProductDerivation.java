package kodo.persistence.jdbc;

import java.util.Map;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;

public class KodoJDBCPersistenceProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public void putBrokerFactoryAliases(Map m) {
   }

   public int getType() {
      return 400;
   }

   public ConfigurationProvider newConfigurationProvider() {
      return null;
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (!(c instanceof JDBCConfigurationImpl)) {
         return false;
      } else {
         JDBCConfigurationImpl conf = (JDBCConfigurationImpl)c;
         String jpa = "jpa";
         String ejb = "ejb";
         conf.metaFactoryPlugin.setAlias(jpa, KodoPersistenceMappingFactory.class.getName());
         conf.metaFactoryPlugin.setAlias(ejb, KodoPersistenceMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias(jpa, KodoPersistenceMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias(ejb, KodoPersistenceMappingFactory.class.getName());
         return true;
      }
   }
}
