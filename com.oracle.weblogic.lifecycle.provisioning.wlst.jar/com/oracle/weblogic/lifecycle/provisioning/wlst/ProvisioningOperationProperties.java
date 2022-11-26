package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

final class ProvisioningOperationProperties {
   private final JsonBuilderFactory builderFactory;
   private final Properties provisioningOperationProperties;

   ProvisioningOperationProperties(Map provisioningOperationProperties) {
      this(Json.createBuilderFactory((Map)null), provisioningOperationProperties);
   }

   ProvisioningOperationProperties(JsonBuilderFactory builderFactory, Map provisioningOperationProperties) {
      String cn = this.getClass().getName();
      String mn = "<init>";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "<init>", new Object[]{builderFactory, provisioningOperationProperties});
      }

      if (builderFactory == null) {
         this.builderFactory = Json.createBuilderFactory((Map)null);
      } else {
         this.builderFactory = builderFactory;
      }

      assert this.builderFactory != null;

      if (provisioningOperationProperties != null && !(provisioningOperationProperties instanceof Properties)) {
         Properties properties = new Properties();
         if (!provisioningOperationProperties.isEmpty()) {
            Collection entries = provisioningOperationProperties.entrySet();
            if (entries != null && !entries.isEmpty()) {
               Iterator var8 = entries.iterator();

               while(var8.hasNext()) {
                  Map.Entry entry = (Map.Entry)var8.next();
                  if (entry != null) {
                     Object key = entry.getKey();
                     if (key instanceof String) {
                        Object value = entry.getValue();
                        if (value instanceof String) {
                           properties.setProperty((String)key, (String)value);
                        }
                     }
                  }
               }
            }
         }

         this.provisioningOperationProperties = properties;
      } else {
         this.provisioningOperationProperties = (Properties)provisioningOperationProperties;
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "<init>");
      }

   }

   public final JsonStructure toJson() {
      String cn = this.getClass().getName();
      String mn = "toJson";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "toJson");
      }

      JsonStructure returnValue = null;
      if (this.provisioningOperationProperties != null) {
         Collection propertyNames = this.provisioningOperationProperties.stringPropertyNames();
         if (propertyNames != null) {
            JsonArrayBuilder propertiesBuilder = this.builderFactory.createArrayBuilder();

            assert propertiesBuilder != null;

            if (!propertyNames.isEmpty()) {
               Iterator var7 = propertyNames.iterator();

               while(var7.hasNext()) {
                  String propertyName = (String)var7.next();
                  if (propertyName != null) {
                     JsonObjectBuilder objectBuilder = this.builderFactory.createObjectBuilder();

                     assert objectBuilder != null;

                     objectBuilder.add("name", propertyName);
                     objectBuilder.add("value", this.provisioningOperationProperties.getProperty(propertyName));
                     propertiesBuilder.add(objectBuilder);
                  }
               }
            }

            returnValue = propertiesBuilder.build();
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "toJson", returnValue);
      }

      return returnValue;
   }
}
