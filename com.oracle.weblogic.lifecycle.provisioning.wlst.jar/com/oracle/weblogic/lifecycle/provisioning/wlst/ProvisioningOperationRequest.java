package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

final class ProvisioningOperationRequest {
   private final JsonBuilderFactory builderFactory;
   private final ConfigurableAttributeValues configurableAttributeValuesDelegate;
   private final String provisioningComponentName;
   private final Map configurableAttributeValues;
   private final Properties provisioningOperationProperties;

   ProvisioningOperationRequest(String provisioningComponentName, Map configurableAttributeValues, Map provisioningOperationProperties) {
      this(Json.createBuilderFactory((Map)null), provisioningComponentName, configurableAttributeValues, provisioningOperationProperties);
   }

   ProvisioningOperationRequest(JsonBuilderFactory builderFactory, String provisioningComponentName, Map configurableAttributeValues, Map provisioningOperationProperties) {
      String cn = this.getClass().getName();
      String mn = "<init>";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "<init>", new Object[]{builderFactory, provisioningComponentName, configurableAttributeValues, provisioningOperationProperties});
      }

      Objects.requireNonNull(provisioningComponentName, "provisioningComponentName == null");
      if (builderFactory == null) {
         this.builderFactory = Json.createBuilderFactory((Map)null);
      } else {
         this.builderFactory = builderFactory;
      }

      assert this.builderFactory != null;

      this.configurableAttributeValuesDelegate = new ConfigurableAttributeValues(this.builderFactory);
      this.provisioningComponentName = provisioningComponentName;
      this.configurableAttributeValues = configurableAttributeValues;
      if (provisioningOperationProperties != null && !(provisioningOperationProperties instanceof Properties)) {
         Properties properties = new Properties();
         if (!provisioningOperationProperties.isEmpty()) {
            Collection entries = provisioningOperationProperties.entrySet();
            if (entries != null && !entries.isEmpty()) {
               Iterator var10 = entries.iterator();

               while(var10.hasNext()) {
                  Map.Entry entry = (Map.Entry)var10.next();
                  if (entry != null) {
                     Object key = entry.getKey();
                     if (key instanceof String) {
                        Object value = entry.getValue();
                        if (value == null) {
                           properties.setProperty((String)key, (String)null);
                        } else {
                           properties.setProperty((String)key, value.toString());
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

   final JsonObject toJson() {
      String cn = this.getClass().getName();
      String mn = "toJson";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "toJson");
      }

      Map configurableAttributeValues = this.configurableAttributeValuesDelegate.toMap(this.configurableAttributeValues);
      JsonObjectBuilder builder = this.builderFactory.createObjectBuilder();

      assert builder != null;

      JsonStructure configurableAttributeValuesJson = this.configurableAttributeValuesDelegate.toJson(configurableAttributeValues);
      JsonStructure provisioningOperationPropertiesJson = (new ProvisioningOperationProperties(this.provisioningOperationProperties)).toJson();
      if (this.provisioningOperationProperties != null) {
         String partitionName = this.provisioningOperationProperties.getProperty("wlsPartitionName");
         if (partitionName != null) {
            builder.add("partitionName", partitionName);
         }
      }

      builder.add("componentName", this.provisioningComponentName);
      if (configurableAttributeValuesJson != null) {
         builder.add("configurableAttributes", configurableAttributeValuesJson);
      }

      if (provisioningOperationPropertiesJson != null) {
         builder.add("properties", provisioningOperationPropertiesJson);
      }

      JsonObject returnValue = builder.build();
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "toJson", returnValue);
      }

      return returnValue;
   }
}
