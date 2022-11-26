package com.oracle.weblogic.lifecycle.config.database;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
public class ResourceConfigManager {
   @Inject
   private ServiceLocator locator;
   @Inject
   private LifecycleConfigManager lcm;

   public void createResource(String pathKey, String instanceId, String name, String type, Properties properties) {
      Map map = new HashMap();
      map.put("name", name);
      map.put("type", type);
      map.put("properties", properties);
      this.validateResource(name, type, properties);
      if (instanceId == null) {
         instanceId = "underTenants";
      }

      this.lcm.add((String)pathKey, instanceId, map);
   }

   private void validateResource(String name, String type, Properties properties) {
      ResourceHandler handler = this.getResourceHandler(name, type, properties);
      if (handler == null) {
         throw new RuntimeException("No resource handler for resource type [" + type + "] found");
      } else {
         handler.validate(name, type, properties);
      }
   }

   public ResourceHandler getResourceHandler(String name, String type, Properties properties) {
      ResourceHandler handler = null;
      Collection resourceHandlers = this.locator.getAllServices(ResourceHandler.class, new Annotation[0]);
      Iterator var6 = resourceHandlers.iterator();

      while(var6.hasNext()) {
         ResourceHandler resourceHandler = (ResourceHandler)var6.next();
         if (resourceHandler.handles(name, type, properties)) {
            handler = resourceHandler;
            break;
         }
      }

      return handler;
   }

   public ResourceConfigService updateResource(String resourceName, Properties properties) {
      return null;
   }

   public ResourceConfigService deleteResource(String resourceName) {
      return null;
   }
}
