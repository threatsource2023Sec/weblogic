package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

final class GetConfigurableAttributesResponse {
   private final JsonArray allConfigurableAttributes;

   GetConfigurableAttributesResponse(JsonArray allConfigurableAttributes) {
      String cn = this.getClass().getName();
      String mn = "<init>";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "<init>", allConfigurableAttributes);
      }

      this.allConfigurableAttributes = allConfigurableAttributes;
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "<init>");
      }

   }

   public Properties toProperties() {
      String cn = this.getClass().getName();
      String mn = "toProperties";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "toProperties");
      }

      Properties returnValue = new Properties();
      if (this.allConfigurableAttributes != null) {
         Collection configurableAttributeBlocks = this.allConfigurableAttributes.getValuesAs(JsonObject.class);
         if (configurableAttributeBlocks != null && !configurableAttributeBlocks.isEmpty()) {
            Iterator var6 = configurableAttributeBlocks.iterator();

            label90:
            while(true) {
               String provisioningComponentName;
               List configurableAttributes;
               do {
                  do {
                     JsonArray individualConfigurableAttributes;
                     do {
                        JsonObject configurableAttributeBlock;
                        do {
                           do {
                              if (!var6.hasNext()) {
                                 break label90;
                              }

                              configurableAttributeBlock = (JsonObject)var6.next();
                           } while(configurableAttributeBlock == null);

                           provisioningComponentName = configurableAttributeBlock.getString("name");
                        } while(provisioningComponentName == null);

                        individualConfigurableAttributes = configurableAttributeBlock.getJsonArray("configurableAttributes");
                     } while(individualConfigurableAttributes == null);

                     configurableAttributes = individualConfigurableAttributes.getValuesAs(JsonObject.class);
                  } while(configurableAttributes == null);
               } while(configurableAttributes.isEmpty());

               Iterator var11 = configurableAttributes.iterator();

               while(var11.hasNext()) {
                  JsonObject configurableAttributeObject = (JsonObject)var11.next();
                  if (configurableAttributeObject != null) {
                     String configurableAttributeName = configurableAttributeObject.getString("name");
                     if (configurableAttributeName != null) {
                        JsonValue defaultValue = (JsonValue)configurableAttributeObject.get("defaultValue");
                        String defaultValueString;
                        if (defaultValue == null) {
                           defaultValueString = "";
                        } else {
                           JsonValue.ValueType defaultValueType = defaultValue.getValueType();

                           assert defaultValueType != null;

                           switch (defaultValueType) {
                              case ARRAY:
                              case NULL:
                              case OBJECT:
                                 defaultValueString = "";
                                 break;
                              case STRING:
                                 assert defaultValue instanceof JsonString;

                                 defaultValueString = ((JsonString)defaultValue).getString();
                                 break;
                              default:
                                 defaultValueString = defaultValue.toString();
                           }
                        }

                        assert defaultValueString != null;

                        returnValue.setProperty(provisioningComponentName + "." + configurableAttributeName, defaultValueString);
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "toProperties", returnValue);
      }

      return returnValue;
   }
}
