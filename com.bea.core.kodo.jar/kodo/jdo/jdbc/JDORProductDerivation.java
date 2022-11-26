package kodo.jdo.jdbc;

import java.util.Map;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.JDOConnection;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCStoreManager;
import org.apache.openjpa.jdbc.meta.MappingDefaultsImpl;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;

public class JDORProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public void putBrokerFactoryAliases(Map m) {
   }

   public int getType() {
      return 300;
   }

   public void validate() throws Exception {
      PersistenceManagerFactory.class.getClassLoader();
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (!(c instanceof JDBCConfigurationImpl)) {
         return false;
      } else {
         JDBCConfigurationImpl conf = (JDBCConfigurationImpl)c;
         conf.getStoreFacadeTypeRegistry().registerImplementation(FetchPlan.class, JDBCStoreManager.class, JDBCFetchPlanImpl.class);
         conf.getStoreFacadeTypeRegistry().registerImplementation(JDOConnection.class, JDBCStoreManager.class, JDBCJDOConnection.class);
         String jdo = "jdo";
         String kodo3 = "kodo3";
         conf.metaFactoryPlugin.setAlias(jdo, JDORMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias(jdo, JDORMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias("jdo-orm", ORMFileJDORMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias("jdo-table", TableJDORMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias("file", MappingFileDeprecatedJDOMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias("db", TableDeprecatedJDOMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setAlias("metadata", ExtensionDeprecatedJDOMappingFactory.class.getName());
         conf.mappingFactoryPlugin.setMetaDataFactoryDefault(kodo3, "file");
         conf.mappingFactoryPlugin.setMappedMetaDataFactoryDefault(jdo, "jdo-orm");
         conf.mappingDefaultsPlugin.setAlias(jdo, MappingDefaultsImpl.class.getName());
         conf.mappingDefaultsPlugin.setAlias(kodo3, DeprecatedJDOMappingDefaults.class.getName());
         return true;
      }
   }

   public boolean afterSpecificationSet(Configuration c) {
      String jdo = "jdo";
      if (!(c instanceof JDBCConfigurationImpl)) {
         return false;
      } else {
         JDBCConfigurationImpl conf = (JDBCConfigurationImpl)c;
         if (!jdo.equals(conf.getSpecification())) {
            return false;
         } else {
            conf.mappingDefaultsPlugin.setDefault(jdo);
            conf.mappingDefaultsPlugin.setString(jdo);
            return true;
         }
      }
   }
}
