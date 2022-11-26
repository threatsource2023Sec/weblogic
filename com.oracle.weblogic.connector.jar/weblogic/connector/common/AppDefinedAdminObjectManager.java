package weblogic.connector.common;

import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.j2ee.descriptor.AdministeredObjectBean;

public class AppDefinedAdminObjectManager extends AppDefinedResourceManager {
   public AppDefinedAdminObjectManager(String moduleName, RAInstanceManager raInstanceManager) {
      super(moduleName, raInstanceManager);
   }

   public AppDefinedAdminObjectInfo createAppDefinedAdminObjectInfo(UniversalResourceKey key, AdminObjectMetaInfo metaInfo, AdministeredObjectBean administeredObjectBean) throws ResourceException {
      AppDefinedAdminObjectInfo info = new AppDefinedAdminObjectInfo(key, metaInfo, administeredObjectBean);
      this.initAppDefinedObjectInfo(key, info);
      return info;
   }

   protected void doCreateAppDefinedObject(AppDefinedObjectInfo info) throws ResourceException {
      AppDefinedAdminObjectInfo adminObject = (AppDefinedAdminObjectInfo)info;

      try {
         this.raInstanceManager.doCreateAppDefinedAdminObject(adminObject.getKey(), adminObject.metaInfo);
      } catch (ResourceException var4) {
         ConnectorLogger.logAdminObjectCreationError(info.getKey().toString(), var4.getMessage(), var4);
         throw var4;
      }
   }

   public void destroyResource(AppDefinedObjectInfo info) {
      AppDefinedAdminObjectInfo adminObject = (AppDefinedAdminObjectInfo)info;
      this.raInstanceManager.doStopAppDefinedAdminObject(adminObject.getKey(), adminObject.metaInfo);
   }
}
