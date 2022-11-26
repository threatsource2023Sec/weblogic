package org.apache.openjpa.persistence;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.conf.PluginValue;

public class EntityManagerFactoryValue extends PluginValue {
   public static final String KEY = "EntityManagerFactory";
   public static final String[] ALIASES = new String[]{"default", EntityManagerFactoryImpl.class.getName()};

   public static OpenJPAEntityManagerFactory newFactory(BrokerFactory bf) {
      OpenJPAConfiguration conf = bf.getConfiguration();
      PluginValue val = (PluginValue)conf.getValue("EntityManagerFactory");
      if (val == null) {
         return null;
      } else {
         EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl)val.instantiate(EntityManagerFactoryImpl.class, conf, true);
         emf.setBrokerFactory(bf);
         return emf;
      }
   }

   public EntityManagerFactoryValue() {
      super("EntityManagerFactory", false);
      this.setAliases(ALIASES);
      this.setDefault(ALIASES[0]);
      this.setClassName(ALIASES[1]);
      this.setScope(this.getClass());
   }
}
