package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.json.JsonObject;
import weblogic.management.scripting.WLSTUtils;

/** @deprecated */
@Deprecated
final class PropertiesHelper {
   static final String PROPERTY_NAME_SEPARATOR = ".";
   private static WLSTUtils WLST_CTX;

   private PropertiesHelper() {
   }

   static final WLSTUtils getWLSTCtx() {
      return WLST_CTX;
   }

   static final void setWLSTCtx(WLSTUtils debugger) {
      WLST_CTX = debugger;
   }

   static final String getPropertyNameSeparator() {
      return ".";
   }

   /** @deprecated */
   @Deprecated
   static final String getComponentType(String propertyName) {
      Objects.requireNonNull(propertyName);
      int propertyNameSeparatorIndex = propertyName.indexOf(".");
      if (propertyNameSeparatorIndex > 0) {
         return propertyName.substring(0, propertyNameSeparatorIndex);
      } else {
         throw new IllegalArgumentException("Invalid property name found: " + propertyName + ". Property name not prefixed with component type.");
      }
   }

   /** @deprecated */
   @Deprecated
   static final String getActualPropertyName(String propertyName) {
      Objects.requireNonNull(propertyName);
      int propertyNameSeparatorIndex = propertyName.indexOf(".");
      if (propertyNameSeparatorIndex > 0) {
         return propertyName.substring(propertyNameSeparatorIndex + 1);
      } else {
         throw new IllegalArgumentException("Invalid property name found: " + propertyName + ". Property name not prefixed with component type.");
      }
   }

   /** @deprecated */
   @Deprecated
   static final String getPropertyNamePrefix(String propertyName) {
      Objects.requireNonNull(propertyName);
      int propertyNameSeparatorIndex = propertyName.indexOf(".");
      if (propertyNameSeparatorIndex > 0) {
         return propertyName.substring(0, propertyNameSeparatorIndex + 1);
      } else {
         throw new IllegalArgumentException("Invalid property name found: " + propertyName + ". Property name not prefixed with component type.");
      }
   }

   static final void writeHeader(String componentType, BufferedWriter writer) throws IOException {
      writer.write("#Auto Generated: " + (new Date()).toString());
      writer.newLine();
      writer.write("#");
      writer.newLine();
      writer.write("#Properties for provisioning partition for component type: \"" + componentType + "\"");
   }

   static final void writePropertiesToFile(Map componentProperties, BufferedWriter writer) throws IOException {
      if (componentProperties != null && componentProperties.size() > 0) {
         boolean isNewSection = true;
         String prevComponentType = null;
         String currComponentType = null;
         Iterator var5 = componentProperties.entrySet().iterator();

         while(true) {
            Map.Entry componentProperty;
            do {
               if (!var5.hasNext()) {
                  return;
               }

               componentProperty = (Map.Entry)var5.next();
            } while(componentProperty == null);

            String fullyQualifiedConfigurableAttributeName = (String)componentProperty.getKey();
            currComponentType = getComponentType(fullyQualifiedConfigurableAttributeName);
            if (prevComponentType == null || !prevComponentType.equals(currComponentType)) {
               isNewSection = true;
            }

            if (isNewSection) {
               isNewSection = false;
               writer.newLine();
               writer.newLine();
               writer.write("#---------------------------------------------------------");
               writer.newLine();
               writer.write("#Properties for component type: " + currComponentType);
               writer.newLine();
               writer.write("#---------------------------------------------------------");
               writer.newLine();
            }

            JsonObject jsonObject = (JsonObject)componentProperty.getValue();

            assert jsonObject != null;

            String description = jsonObject.getString("description");
            if (description == null || description.trim().length() == 0) {
               description = jsonObject.getString("name");
            }

            String value = jsonObject.getString("defaultValue");
            writer.newLine();
            writer.write("#" + description);
            writer.newLine();
            writer.write(fullyQualifiedConfigurableAttributeName + "=" + value);
            writer.newLine();
            prevComponentType = currComponentType;
         }
      }
   }

   static final void error(String className, String message) {
      if (WLST_CTX != null) {
         String classNameStr = className == null ? "" : className;
         WLST_CTX.printDebug("[ERROR]::[" + classNameStr + "]::" + message);
      }

   }

   static final void debug(String className, String message) {
      if (WLST_CTX != null) {
         String classNameStr = className == null ? "" : className;
         WLST_CTX.printDebug("[DEBUG]::[" + classNameStr + "]::" + message);
      }

   }
}
