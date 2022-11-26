package org.apache.openjpa.slice;

import java.util.Map;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.slice.jdbc.DistributedJDBCBrokerFactory;

public class ProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public static final String PREFIX_SLICE = "openjpa.slice";
   public static final String HINT_TARGET = "openjpa.hint.slice.Target";

   public void putBrokerFactoryAliases(Map m) {
      m.put("slice", DistributedJDBCBrokerFactory.class.getName());
   }

   public String getConfigurationPrefix() {
      return "openjpa.slice";
   }

   public int getType() {
      return 1000;
   }
}
