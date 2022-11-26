package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.ConnectionPoolDataSource;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACConnectionEnvFactory;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACPooledConnectionState;

public class HAPooledConnectionEnvFactory extends PooledConnectionEnvFactory implements RACConnectionEnvFactory {
   HAConnectionPool hacp;
   RACModule racModule;

   public HAPooledConnectionEnvFactory(JDBCConnectionPool pool, String appName, String moduleName, String compName, Properties props) throws ResourceException, SQLException {
      super(pool, appName, moduleName, compName, props);
      this.hacp = (HAConnectionPool)pool;
      this.racModule = this.hacp.getRACModule();
   }

   public PooledResource createResource(PooledResourceInfo connInfo) throws ResourceException {
      HAConnectionEnv hace = (HAConnectionEnv)super.createResource(connInfo);
      this.racModule.connectionOpened(hace);
      return hace;
   }

   public void refreshResource(PooledResource resource) throws ResourceException {
      ((ConnectionEnv)resource).setConnectionPoolDataSource(this.cpDataSrc);
      super.refreshResource(resource);
   }

   public ConnectionEnv instantiatePooledResource(Properties poolParams) {
      ConnectionEnv cc = new HAConnectionEnv(poolParams, this);
      return cc;
   }

   protected void setDataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws SQLException {
      if (connInfo instanceof HAPooledResourceInfo) {
         String iurl = ((HAPooledResourceInfo)connInfo).getUrl();
         ConnectionPoolDataSource cpds = this.getCPDataSrc(this.driverProps);
         Properties props = (Properties)this.driverProps.clone();
         props.put("url", iurl);
         String instanceName = HAUtil.getInstance().getInstanceName(connInfo);
         if (instanceName == null && this.pool.isSharingPool() && this.pool instanceof SharingConnectionPool) {
            SharingConnectionPool scp = (SharingConnectionPool)this.pool;
            if (scp.getPDBServiceName() != null) {
               throw new SQLException("Shared pool " + this.pool.getName() + " service " + scp.getPDBServiceName() + " not running on any instances");
            }
         }

         if (instanceName != null && (iurl == null || iurl.indexOf("INSTANCE_NAME") == -1)) {
            props.put("oracle.jdbc.targetInstanceName", instanceName);
         }

         DataSourceUtil.initProps(this.poolname, cpds, props);
         cc.setConnectionPoolDataSource(cpds);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("setting connectionpooldatasource url to " + iurl);
         }
      } else {
         cc.setConnectionPoolDataSource(this.cpDataSrc);
      }

      cc.setDriverProperties(this.driverProps);
   }

   public RACPooledConnectionState createRACPooledConnectionState(RACConnectionEnv racce) throws ResourceException {
      RACPooledConnectionState state = this.racModule.createRACPooledConnectionState(racce);
      PooledResourceInfo info = this.hacp.getPooledResourceInfo(state.getRACInstance(), (Properties)null);
      racce.setPooledResourceInfo(info);

      try {
         this.setDataSourceProperties(racce, info);
         return state;
      } catch (SQLException var5) {
         throw new ResourceException(var5);
      }
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("HAPooledConnectionEnvFactory [" + this.poolname + "]: " + msg);
   }
}
