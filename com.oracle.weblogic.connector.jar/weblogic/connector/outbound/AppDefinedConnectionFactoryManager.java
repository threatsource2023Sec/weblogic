package weblogic.connector.outbound;

import java.util.Iterator;
import java.util.List;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.AppDefinedObjectInfo;
import weblogic.connector.common.AppDefinedResourceManager;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;

public class AppDefinedConnectionFactoryManager extends AppDefinedResourceManager {
   private RAOutboundManager raOutboundManager;

   public AppDefinedConnectionFactoryManager(String moduleName, RAOutboundManager raOutboundManager) {
      super(moduleName, raOutboundManager.getRA());
      this.raOutboundManager = raOutboundManager;
   }

   protected void doCreateAppDefinedObject(AppDefinedObjectInfo info) throws ResourceException {
      AppDefinedConnectionFactoryInfo cfInfo = (AppDefinedConnectionFactoryInfo)info;
      this.raOutboundManager.doCreateAppDefinedConnectionFactory(cfInfo.getKey(), cfInfo.metaInfo);
   }

   public void destroyResource(AppDefinedObjectInfo info) throws ResourceException {
      AppDefinedConnectionFactoryInfo cfInfo = (AppDefinedConnectionFactoryInfo)info;

      try {
         this.raOutboundManager.doDestroyAppdefinedConnectionFactory(cfInfo.getKey(), cfInfo.metaInfo);
      } catch (ResourceException var4) {
         ConnectorLogger.logPoolDestroyError(info.getKey().toString(), var4.getMessage(), var4);
         throw var4;
      }
   }

   public AppDefinedConnectionFactoryInfo createAppDefinedConnectionFactoryInfo(UniversalResourceKey key, ConnectionFactoryMetaInfo metaInfo, ConnectionFactoryResourceBean cfBean) throws ResourceException {
      AppDefinedConnectionFactoryInfo info = new AppDefinedConnectionFactoryInfo(key, metaInfo, cfBean);
      this.initAppDefinedObjectInfo(key, info);
      return info;
   }

   public void getAllConnectionPool(List result) {
      synchronized(this.raInstanceManager) {
         Iterator var3 = this.appDefinedObjects.values().iterator();

         while(var3.hasNext()) {
            AppDefinedObjectInfo info = (AppDefinedObjectInfo)var3.next();
            AppDefinedConnectionFactoryInfo cfInfo = (AppDefinedConnectionFactoryInfo)info;
            ConnectionPool pool = cfInfo.metaInfo.pool;
            if (pool != null) {
               result.add(pool);
            }
         }

      }
   }
}
