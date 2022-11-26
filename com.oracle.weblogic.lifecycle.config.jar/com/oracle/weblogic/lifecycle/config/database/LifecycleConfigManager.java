package com.oracle.weblogic.lifecycle.config.database;

import java.beans.PropertyChangeEvent;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.api.ConfigurationUtilities;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;

@Service
public class LifecycleConfigManager {
   @Inject
   private ServiceLocator serviceLocator;
   @Inject
   private Hub hub;

   public RuntimeConfigManager getRuntimeConfigManager() {
      return (RuntimeConfigManager)this.serviceLocator.getService(RuntimeConfigManager.class, new Annotation[0]);
   }

   public EnvironmentConfigManager getEnvironmentConfigManager() {
      return (EnvironmentConfigManager)this.serviceLocator.getService(EnvironmentConfigManager.class, new Annotation[0]);
   }

   public TenantConfigManager getTenantConfigManager() {
      return (TenantConfigManager)this.serviceLocator.getService(TenantConfigManager.class, new Annotation[0]);
   }

   public PluginConfigManager getPluginConfigManager() {
      return (PluginConfigManager)this.serviceLocator.getService(PluginConfigManager.class, new Annotation[0]);
   }

   public void postConstruct() {
      try {
         ServiceLocatorUtilities.enableLookupExceptions(this.serviceLocator);
         ConfigurationUtilities.enableConfigurationSystem(this.serviceLocator);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void add(String type, String instanceId, Map map) {
      WriteableBeanDatabase wbd = this.hub.getWriteableDatabaseCopy();
      this.add(wbd.findOrAddWriteableType(type), instanceId, map);
      wbd.commit();
   }

   public void delete(String pathKey, String instanceId) {
      Map childMap = new HashMap();
      WriteableBeanDatabase wbd = this.hub.getWriteableDatabaseCopy();
      Type type1 = wbd.getType(pathKey);
      String typeName = type1.getName();
      Set allTypes = wbd.getAllTypes();
      Iterator var8 = allTypes.iterator();

      while(true) {
         Type childType;
         do {
            if (!var8.hasNext()) {
               var8 = childMap.keySet().iterator();

               while(var8.hasNext()) {
                  String key = (String)var8.next();
                  this.delete(wbd, (String)childMap.get(key), key);
               }

               this.delete(wbd, pathKey, instanceId);
               wbd.commit();
               return;
            }

            childType = (Type)var8.next();
         } while(!childType.getName().startsWith(typeName));

         Map childInstance = childType.getInstances();
         Set childKeySet = childInstance.keySet();
         Iterator var12 = childKeySet.iterator();

         while(var12.hasNext()) {
            String childInstanceId = (String)var12.next();
            if (childInstanceId.startsWith(instanceId) && childInstanceId.length() > instanceId.length()) {
               childMap.put(childInstanceId, childType.getName());
            }
         }
      }
   }

   static PropertyChangeEvent[] update(Hub hub, String pathKey, String instanceId, Map changes) {
      WriteableBeanDatabase wbd = hub.getWriteableDatabaseCopy();
      WriteableType wt = wbd.getWriteableType(pathKey);
      if (wt != null) {
         changes.put("updatedOn", LifecycleConfigService.ISO_DATE_FORMAT.format(new Date()));
         ConfigValidator.validateUpdate(hub, wt, instanceId, changes);
         PropertyChangeEvent[] changesDone = wt.modifyInstance(instanceId, changes, new PropertyChangeEvent[0]);
         wbd.commit();
         return changesDone;
      } else {
         return null;
      }
   }

   private void add(WriteableType wt, String instanceId, Map map) {
      if (wt.getInstance(instanceId) != null) {
         throw new RuntimeException(instanceId + " already exists");
      } else {
         ConfigValidator.validateAdd(this.hub, wt, instanceId, map);
         map.put("createdOn", LifecycleConfigService.ISO_DATE_FORMAT.format(new Date()));
         wt.addInstance(instanceId, map);
      }
   }

   private void delete(WriteableBeanDatabase wbd, String pathKey, String instanceId) {
      WriteableType wt = wbd.findOrAddWriteableType(pathKey);
      ConfigValidator.validateDelete(this.hub, wt, instanceId);
      wt.removeInstance(instanceId);
   }
}
