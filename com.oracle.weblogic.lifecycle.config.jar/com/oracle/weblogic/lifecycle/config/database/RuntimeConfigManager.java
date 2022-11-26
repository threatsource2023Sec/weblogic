package com.oracle.weblogic.lifecycle.config.database;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.properties.ConfidentialPropertyValue;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.inject.Inject;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;

@Service
public class RuntimeConfigManager {
   @Inject
   IterableProvider allRuntimeServices;
   @Inject
   private LifecycleConfigManager lcm;
   private static final String HOST_NAME = "hostname";
   private static final String PORT = "port";

   public RuntimeConfigService createRuntime(String type, String name) throws LifecycleException {
      return this.createRuntime(type, name, new HashMap());
   }

   public RuntimeConfigService createRuntime(String type, String name, Map properties) throws LifecycleException {
      if (name != null && type != null) {
         Properties props = new Properties();
         String hostname = null;
         String port = null;
         Iterator var7 = properties.keySet().iterator();

         while(var7.hasNext()) {
            String propertyName = (String)var7.next();
            boolean addProperty = true;
            Object value = properties.get(propertyName);
            String propertyValue;
            if (value instanceof StringPropertyValue) {
               propertyValue = ((StringPropertyValue)value).getValue();
               if (propertyName.equalsIgnoreCase("hostname")) {
                  hostname = propertyValue;
                  addProperty = false;
               } else if (propertyName.equalsIgnoreCase("port")) {
                  port = propertyValue;
                  addProperty = false;
               }
            } else if (value instanceof ConfidentialPropertyValue) {
               propertyValue = ((ConfidentialPropertyValue)value).getValue();
            } else {
               propertyValue = value.toString();
            }

            if (addProperty) {
               props.put(propertyName, propertyValue);
            }
         }

         props.remove("name");
         props.remove("type");
         Map map = new HashMap();
         map.put("name", name);
         map.put("type", type);
         if (hostname != null) {
            map.put("hostname", hostname);
         }

         if (port != null) {
            map.put("port", port);
         }

         map.put("properties", props);
         this.lcm.add((String)"/lifecycle-config/runtimes/runtime", RuntimeConfigService.getInstanceId(map), map);
         return this.getRuntimeByName(name);
      } else {
         throw new LifecycleException("name and type are required to create a Runtime");
      }
   }

   public void deleteRuntime(RuntimeConfigService runtimeService) {
      this.lcm.delete("/lifecycle-config/runtimes/runtime", runtimeService.getInstanceId());
   }

   public List getRuntimes() {
      return ConfigUtil.toList(this.allRuntimeServices);
   }

   public List getRuntimesByType(String type) {
      List runtimesByType = new ArrayList();
      Iterator var3 = this.allRuntimeServices.iterator();

      while(var3.hasNext()) {
         RuntimeConfigService runtimeService = (RuntimeConfigService)var3.next();
         if (type.equals(runtimeService.getType())) {
            runtimesByType.add(runtimeService);
         }
      }

      return runtimesByType;
   }

   public RuntimeConfigService getRuntimeByName(String name) {
      Iterator var2 = this.allRuntimeServices.iterator();

      RuntimeConfigService runtimeService;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         runtimeService = (RuntimeConfigService)var2.next();
      } while(!name.equals(runtimeService.getName()));

      return runtimeService;
   }
}
