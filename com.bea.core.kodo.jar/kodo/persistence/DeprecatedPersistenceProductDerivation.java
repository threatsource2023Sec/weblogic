package kodo.persistence;

import java.util.Map;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;

/** @deprecated */
public class DeprecatedPersistenceProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   private static final String PROVIDER_NAME = PersistenceProviderImpl.class.getName();
   private static final String EMF_NAME = KodoEntityManagerFactoryImpl.class.getName();

   public void putBrokerFactoryAliases(Map m) {
   }

   public int getType() {
      return 1;
   }

   public boolean beforeConfigurationConstruct(ConfigurationProvider cp) {
      Map props = cp.getProperties();
      Object provider = props.get("javax.persistence.provider");
      if (provider != null && PROVIDER_NAME.equals(provider.toString())) {
         if (Configurations.containsProperty("EntityManagerFactory", props)) {
            return false;
         } else {
            cp.addProperty("openjpa.EntityManagerFactory", EMF_NAME);
            return true;
         }
      } else {
         return false;
      }
   }
}
