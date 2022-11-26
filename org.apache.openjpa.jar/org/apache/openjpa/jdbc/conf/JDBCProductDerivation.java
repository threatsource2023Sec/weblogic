package org.apache.openjpa.jdbc.conf;

import java.util.Map;
import org.apache.openjpa.conf.BrokerFactoryValue;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.ConfigurationProvider;

public class JDBCProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public void putBrokerFactoryAliases(Map m) {
      m.put("jdbc", JDBCBrokerFactory.class.getName());
   }

   public int getType() {
      return 200;
   }

   public boolean beforeConfigurationConstruct(ConfigurationProvider cp) {
      if (BrokerFactoryValue.get(cp) == null) {
         BrokerFactoryValue.set(cp, "jdbc");
         return true;
      } else {
         return false;
      }
   }
}
