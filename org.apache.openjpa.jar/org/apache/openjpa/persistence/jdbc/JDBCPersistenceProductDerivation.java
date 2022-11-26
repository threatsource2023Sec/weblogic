package org.apache.openjpa.persistence.jdbc;

import java.security.AccessController;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.persistence.FetchPlan;

public class JDBCPersistenceProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public void putBrokerFactoryAliases(Map m) {
   }

   public int getType() {
      return 300;
   }

   public void validate() throws Exception {
      AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(EntityManagerFactory.class));
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (c instanceof OpenJPAConfiguration) {
         ((OpenJPAConfiguration)c).getStoreFacadeTypeRegistry().registerImplementation(FetchPlan.class, JDBCStoreManager.class, JDBCFetchPlanImpl.class);
      }

      if (!(c instanceof JDBCConfigurationImpl)) {
         return false;
      } else {
         JDBCConfigurationImpl conf = (JDBCConfigurationImpl)c;
         String jpa = "jpa";
         String ejb = "ejb";
         conf.metaFactoryPlugin.setAlias(ejb, PersistenceMappingFactory.class.getName());
         conf.metaFactoryPlugin.setAlias(jpa, PersistenceMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias(ejb, PersistenceMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias(jpa, PersistenceMappingFactory.class.getName());
         conf.mappingDefaultsPlugin.setAlias(ejb, PersistenceMappingDefaults.class.getName());
         conf.mappingDefaultsPlugin.setAlias(jpa, PersistenceMappingDefaults.class.getName());
         return true;
      }
   }

   public boolean afterSpecificationSet(Configuration c) {
      if (!(c instanceof JDBCConfigurationImpl)) {
         return false;
      } else {
         JDBCConfigurationImpl conf = (JDBCConfigurationImpl)c;
         String jpa = "jpa";
         if (!jpa.equals(conf.getSpecification())) {
            return false;
         } else {
            conf.mappingDefaultsPlugin.setDefault(jpa);
            conf.mappingDefaultsPlugin.setString(jpa);
            return true;
         }
      }
   }
}
