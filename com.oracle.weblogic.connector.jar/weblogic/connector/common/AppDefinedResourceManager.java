package weblogic.connector.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.exception.RAException;
import weblogic.j2ee.descriptor.PropertyBean;

public abstract class AppDefinedResourceManager {
   protected final HashMap appDefinedObjects = new HashMap();
   private final String moduleName;
   protected final RAInstanceManager raInstanceManager;

   public AppDefinedResourceManager(String moduleName, RAInstanceManager raInstanceManager) {
      this.moduleName = moduleName;
      this.raInstanceManager = raInstanceManager;
   }

   public Collection getResources() {
      synchronized(this.raInstanceManager) {
         return new ArrayList(this.appDefinedObjects.values());
      }
   }

   public int getResourceCount() {
      synchronized(this.raInstanceManager) {
         return this.appDefinedObjects.size();
      }
   }

   protected final void initAppDefinedObjectInfo(UniversalResourceKey key, AppDefinedObjectInfo info) throws ResourceException {
      if (this.raInstanceManager.isActivated()) {
         this.doCreateAppDefinedObject(info);
      }

      this.appDefinedObjects.put(key, info);
   }

   public void checkForUnPrepare() throws RAException {
      if (!this.appDefinedObjects.isEmpty()) {
         Iterator var1 = this.appDefinedObjects.values().iterator();
         if (var1.hasNext()) {
            AppDefinedObjectInfo adminObject = (AppDefinedObjectInfo)var1.next();
            UniversalResourceKey key = adminObject.getKey();
            throw new RAException(ConnectorLogger.getExceptionAppDefinedResourceExist(this.moduleName, key.getDefApp(), key.getDefModule(), key.getDefComp(), key.getJndi()));
         }
      }
   }

   public void shutdown() {
      synchronized(this.raInstanceManager) {
         this.appDefinedObjects.clear();
      }
   }

   public void activateResources() {
      Iterator var1 = this.appDefinedObjects.values().iterator();

      while(var1.hasNext()) {
         AppDefinedObjectInfo info = (AppDefinedObjectInfo)var1.next();

         try {
            this.doCreateAppDefinedObject(info);
         } catch (ResourceException var4) {
         }
      }

   }

   protected abstract void doCreateAppDefinedObject(AppDefinedObjectInfo var1) throws ResourceException;

   public abstract void destroyResource(AppDefinedObjectInfo var1) throws ResourceException;

   public void deactivateResources() {
      Iterator var1 = this.appDefinedObjects.values().iterator();

      while(var1.hasNext()) {
         AppDefinedObjectInfo info = (AppDefinedObjectInfo)var1.next();

         try {
            this.destroyResource(info);
         } catch (ResourceException var4) {
         }
      }

   }

   public AppDefinedObjectInfo revokeResource(UniversalResourceKey key) throws ResourceException {
      AppDefinedObjectInfo info = (AppDefinedObjectInfo)this.appDefinedObjects.get(key);
      if (info == null) {
         Debug.throwAssertionError("Resource does not exist " + key.toString());
      }

      if (info.refCount <= 0) {
         Debug.throwAssertionError("unexpected ref count of resource " + info.getKey());
      }

      --info.refCount;
      if (info.refCount == 0) {
         this.appDefinedObjects.remove(key);
         return info;
      } else {
         return null;
      }
   }

   public AppDefinedObjectInfo findCompatibleResource(UniversalResourceKey key, PropertyBean propertyBean) throws ResourceException {
      AppDefinedObjectInfo info = (AppDefinedObjectInfo)this.appDefinedObjects.get(key);
      if (info == null) {
         return null;
      } else {
         info.checkCompatible(key, propertyBean);
         ++info.refCount;
         return info;
      }
   }
}
