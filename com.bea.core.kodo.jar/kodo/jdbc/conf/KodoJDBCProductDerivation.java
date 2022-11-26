package kodo.jdbc.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import kodo.jdbc.kernel.KodoJDBCBrokerFactory;
import kodo.jdbc.meta.KodoMappingRepository;
import kodo.jdbc.schema.KodoPoolingDataSource;
import kodo.jdbc.sql.KodoSQLFactory;
import kodo.manage.Management;
import org.apache.openjpa.conf.BrokerFactoryValue;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;

public class KodoJDBCProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public void putBrokerFactoryAliases(Map m) {
      m.put("jdbc", KodoJDBCBrokerFactory.class.getName());
   }

   public int getType() {
      return 400;
   }

   public ConfigurationProvider newConfigurationProvider() {
      return null;
   }

   public boolean beforeConfigurationConstruct(ConfigurationProvider cp) {
      if (BrokerFactoryValue.get(cp) == null) {
         BrokerFactoryValue.set(cp, "jdbc");
         return true;
      } else {
         return false;
      }
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      if (!(c instanceof JDBCConfigurationImpl)) {
         return false;
      } else {
         JDBCConfigurationImpl conf = (JDBCConfigurationImpl)c;
         Management mgmt = Management.getInstance((OpenJPAConfiguration)c);
         if (mgmt != null) {
            mgmt.setVisualProfilingInterfaceClassName("kodo.jdbc.profile.gui.ProfilingInterface");
         }

         conf.driverDataSourcePlugin.setAlias("pooling", KodoPoolingDataSource.class.getName());
         conf.driverDataSourcePlugin.setDefault("pooling");
         conf.driverDataSourcePlugin.setString("pooling");
         conf.sqlFactoryPlugin.setAlias("default", KodoSQLFactory.class.getName());
         conf.sqlFactoryPlugin.setDefault("default");
         conf.sqlFactoryPlugin.setString("default");
         conf.metaRepositoryPlugin.setAlias("default", KodoMappingRepository.class.getName());
         conf.metaRepositoryPlugin.setDefault("default");
         conf.metaRepositoryPlugin.setString("default");
         List aliases = new ArrayList(Arrays.asList(conf.updateManagerPlugin.getAliases()));
         aliases.addAll(Arrays.asList("constraint", "kodo.jdbc.kernel.ConstraintUpdateManager", "batching-operation-order", "kodo.jdbc.kernel.BatchingOperationOrderUpdateManager", "table-lock", "kodo.jdbc.kernel.TableLockUpdateManager"));
         conf.updateManagerPlugin.setAliases((String[])((String[])aliases.toArray(new String[aliases.size()])));
         conf.updateManagerPlugin.setAlias("default", "kodo.jdbc.kernel.ConstraintUpdateManager");
         conf.updateManagerPlugin.setDefault("default");
         conf.updateManagerPlugin.setString("default");
         conf.dbdictionaryPlugin.setAlias("oracle", "kodo.jdbc.sql.KodoOracleDictionary");
         conf.dbdictionaryPlugin.setAlias("sybase", "kodo.jdbc.sql.KodoSybaseDictionary");
         conf.savepointManagerPlugin.setAlias("oracle", "kodo.jdbc.sql.OracleSavepointManager");
         return false;
      }
   }
}
