package org.apache.openjpa.conf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.openjpa.abstractstore.AbstractStoreBrokerFactory;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.conf.ProductDerivation;
import org.apache.openjpa.lib.conf.ProductDerivations;

public class BrokerFactoryValue extends PluginValue {
   public static final String KEY = "BrokerFactory";
   private static final String[] _aliases;

   public static Object get(ConfigurationProvider cp) {
      Map props = cp.getProperties();
      return props.get(ProductDerivations.getConfigurationKey("BrokerFactory", props));
   }

   public static void set(ConfigurationProvider cp, String value) {
      String key = ProductDerivations.getConfigurationKey("BrokerFactory", cp.getProperties());
      cp.addProperty(key, value);
   }

   public BrokerFactoryValue() {
      super("BrokerFactory", false);
      this.setAliases(_aliases);
   }

   static {
      Map aliases = new HashMap();
      aliases.put("abstractstore", AbstractStoreBrokerFactory.class.getName());
      ProductDerivation[] ds = ProductDerivations.getProductDerivations();

      int i;
      for(i = 0; i < ds.length; ++i) {
         if (ds[i] instanceof OpenJPAProductDerivation) {
            ((OpenJPAProductDerivation)ds[i]).putBrokerFactoryAliases(aliases);
         }
      }

      _aliases = new String[aliases.size() * 2];
      i = 0;

      Map.Entry e;
      for(Iterator iter = aliases.entrySet().iterator(); iter.hasNext(); _aliases[i++] = (String)e.getValue()) {
         e = (Map.Entry)iter.next();
         _aliases[i++] = (String)e.getKey();
      }

   }
}
