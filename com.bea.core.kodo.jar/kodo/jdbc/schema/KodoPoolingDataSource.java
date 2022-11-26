package kodo.jdbc.schema;

import com.solarmetric.jdbc.ConnectionPool;
import com.solarmetric.jdbc.ConnectionPoolImpl;
import com.solarmetric.jdbc.PSCacheConnectionDecorator;
import com.solarmetric.jdbc.PSCacheMBeanFactory;
import com.solarmetric.jdbc.PoolingDataSource;
import com.solarmetric.jdbc.PoolingDataSourceMBeanFactory;
import com.solarmetric.manage.jmx.MBeanHelper;
import com.solarmetric.manage.jmx.MultiMBean;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import kodo.manage.Management;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.schema.DriverDataSource;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;

public class KodoPoolingDataSource extends PoolingDataSource implements DriverDataSource, Configurable {
   private Configuration _conf;
   private Properties _props;

   public void setConnectionFactoryProperties(Properties props) {
      this._props = props;
      Configurations.configureInstance(this.getConnectionPool(), this._conf, this._props, (String)null);
   }

   public Properties getConnectionFactoryProperties() {
      return this._props;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setConfiguration(Configuration conf) {
      this._conf = conf;
      this.getLogs().setJDBCLog(conf.getLog("openjpa.jdbc.JDBC"));
      this.getLogs().setSQLLog(conf.getLog("openjpa.jdbc.SQL"));
   }

   public List createConnectionDecorators() {
      ConnectionPool cp = this.getConnectionPool();
      Management mgmt = Management.getInstance((JDBCConfiguration)this._conf);
      if (cp instanceof ConnectionPoolImpl && mgmt != null && mgmt.getMBeanServer() != null) {
         MultiMBean mb = PoolingDataSourceMBeanFactory.createPoolingDataSourceMBean(this, this._conf);
         MBeanHelper.register(mb, "PoolingDataSource", (String)null, mgmt.getMBeanServer(), this._conf);
      }

      if (!"com.microsoft.jdbc.sqlserver.SQLServerDriver".equals(this.getConnectionDriverName())) {
         PSCacheConnectionDecorator pcd = new PSCacheConnectionDecorator();
         pcd.getLogs().setJDBCLog(this._conf.getLog("openjpa.jdbc.JDBC"));
         pcd.getLogs().setSQLLog(this._conf.getLog("openjpa.jdbc.SQL"));
         Configurations.configureInstance(pcd, this._conf, this._props);
         if (mgmt != null && mgmt.getMBeanServer() != null) {
            MultiMBean mb = PSCacheMBeanFactory.createPSCacheMBean(pcd, this._conf);
            MBeanHelper.register(mb, "PreparedStatementCache", (String)null, mgmt.getMBeanServer(), this._conf);
         }

         return Collections.singletonList(pcd);
      } else {
         return null;
      }
   }

   public void initDBDictionary(DBDictionary dict) {
      ConnectionPool cp = this.getConnectionPool();
      if (cp instanceof ConnectionPoolImpl) {
         ConnectionPoolImpl pool = (ConnectionPoolImpl)cp;
         if (pool.getValidationSQL() == null) {
            pool.setValidationSQL(dict.validationSQL);
         }

         if (pool.getClosePoolSQL() == null) {
            pool.setClosePoolSQL(dict.closePoolSQL);
         }
      }

   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return false;
   }

   public Object unwrap(Class iface) throws SQLException {
      throw new SQLException();
   }
}
