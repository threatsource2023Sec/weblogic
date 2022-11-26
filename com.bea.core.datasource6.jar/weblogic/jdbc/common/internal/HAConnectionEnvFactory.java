package weblogic.jdbc.common.internal;

import java.util.Properties;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.jdbc.common.rac.RACConnectionEnv;
import weblogic.jdbc.common.rac.RACConnectionEnvFactory;
import weblogic.jdbc.common.rac.RACModule;
import weblogic.jdbc.common.rac.RACPooledConnectionState;

public class HAConnectionEnvFactory extends ConnectionEnvFactory implements RACConnectionEnvFactory {
   HAJDBCConnectionPool hacp;
   RACModule racModule;

   public HAConnectionEnvFactory(JDBCConnectionPool pool, String appName, String moduleName, String compName, Properties props) throws ResourceException {
      super(pool, appName, moduleName, compName, props);
      this.hacp = (HAJDBCConnectionPool)pool;
      this.racModule = this.hacp.getRACModule();
   }

   public PooledResource createResource(PooledResourceInfo connInfo) throws ResourceException {
      HAConnectionEnv hace = (HAConnectionEnv)super.createResource(connInfo);
      this.racModule.connectionOpened(hace);
      return hace;
   }

   public void refreshResource(PooledResource resource) throws ResourceException {
      super.refreshResource(resource);
   }

   public ConnectionEnv instantiatePooledResource(Properties poolParams) {
      ConnectionEnv cc = new HAConnectionEnv(poolParams, this);
      return cc;
   }

   public RACPooledConnectionState createRACPooledConnectionState(RACConnectionEnv racce) throws ResourceException {
      RACPooledConnectionState state = this.racModule.createRACPooledConnectionState(racce);
      PooledResourceInfo info = this.hacp.getPooledResourceInfo(state.getRACInstance(), (Properties)null);
      racce.setPooledResourceInfo(info);
      return state;
   }

   protected String getUrl(PooledResourceInfo info) {
      return info instanceof HAPooledResourceInfo && ((HAPooledResourceInfo)info).getUrl() != null ? ((HAPooledResourceInfo)info).getUrl() : this.url;
   }

   protected String getInstanceName(PooledResourceInfo info) throws ResourceException {
      String instanceName = HAUtil.getInstance().getInstanceName(info);
      if (instanceName == null && this.pool.isSharingPool() && this.pool instanceof SharingConnectionPool) {
         SharingConnectionPool scp = (SharingConnectionPool)this.pool;
         if (scp.getPDBServiceName() != null) {
            throw new ResourceException("Shared pool " + this.pool.getName() + " service " + scp.getPDBServiceName() + " not running on any instances");
         }
      }

      return instanceName;
   }
}
