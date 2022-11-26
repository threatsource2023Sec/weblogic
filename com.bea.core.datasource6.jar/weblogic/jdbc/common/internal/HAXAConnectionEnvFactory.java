package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.XADataSource;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACConnectionEnvFactory;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACPooledConnectionState;

public class HAXAConnectionEnvFactory extends XAConnectionEnvFactory implements RACConnectionEnvFactory {
   HAJDBCConnectionPool hacp;
   RACModule racModule;

   public HAXAConnectionEnvFactory(JDBCConnectionPool connectionPool, String appname, String moduleName, String compName, String mpName, Properties props) throws ResourceException, SQLException {
      super(connectionPool, appname, moduleName, compName, mpName, props);
      this.hacp = (HAJDBCConnectionPool)this.pool;
      this.racModule = this.hacp.getRACModule();
   }

   public PooledResource createResource(PooledResourceInfo connInfo) throws ResourceException {
      HAConnectionEnv hace = (HAConnectionEnv)super.createResource(connInfo);
      this.racModule.connectionOpened(hace);
      return hace;
   }

   public void refreshResource(PooledResource resource, boolean reregister) throws ResourceException {
      if (this.refreshToSpecificInstance(resource)) {
         try {
            this.setXADataSourceProperties((ConnectionEnv)resource, resource.getPooledResourceInfo());
         } catch (SQLException var4) {
            throw new ResourceException(var4);
         }
      } else {
         ((ConnectionEnv)resource).setXADataSource(this.xaDataSrc);
      }

      super.refreshResource(resource, reregister);
   }

   public void refreshResource(PooledResource resource) throws ResourceException {
      this.refreshResource(resource, true);
   }

   protected void setXADataSourceProperties(ConnectionEnv cc, PooledResourceInfo connInfo) throws SQLException {
      if (connInfo instanceof HAPooledResourceInfo) {
         String iurl = ((HAPooledResourceInfo)connInfo).getUrl();
         XADataSource xads = this.getXADataSrc(this.driver, this.driverProps);
         Properties props = (Properties)this.driverProps.clone();
         props.put("url", iurl);
         String instanceName = ((HAPooledResourceInfo)connInfo).getInstanceName();
         if (instanceName != null && (iurl == null || iurl.indexOf("INSTANCE_NAME") == -1)) {
            props.put("oracle.jdbc.targetInstanceName", instanceName);
         }

         DataSourceUtil.initProps(this.poolname, xads, props);
         cc.setXADataSource(xads);
         if (JdbcDebug.JDBCRAC.isDebugEnabled()) {
            this.debug("setting xadatasource url to " + iurl);
         }
      } else {
         cc.setXADataSource(this.xaDataSrc);
      }

      cc.setDriverProperties(this.driverProps);
   }

   public RACPooledConnectionState createRACPooledConnectionState(RACConnectionEnv racce) throws ResourceException {
      RACPooledConnectionState state = this.racModule.createRACPooledConnectionState(racce);
      PooledResourceInfo info = this.hacp.getPooledResourceInfo(state.getRACInstance(), (Properties)null);
      racce.setPooledResourceInfo(info);

      try {
         this.setXADataSourceProperties(racce, info);
         return state;
      } catch (SQLException var5) {
         throw new ResourceException(var5);
      }
   }

   public ConnectionEnv instantiatePooledResource(Properties poolParams) {
      ConnectionEnv cc = new HAConnectionEnv(poolParams, true, this);
      return cc;
   }

   private boolean refreshToSpecificInstance(PooledResource resource) {
      PooledResourceInfo pri = resource.getPooledResourceInfo();
      return pri instanceof HAPooledResourceInfo ? ((HAPooledResourceInfo)pri).isRefreshToSpecificInstance() : false;
   }

   private void debug(String msg) {
      JdbcDebug.JDBCRAC.debug("HAXAConnectionEnvFactory [" + this.poolname + "]: " + msg);
   }
}
