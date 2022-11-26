package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;

/** @deprecated */
@Deprecated
final class ProvisioningJSONMsgProcessor {
   private static final JsonReaderFactory jsonReaderFactory = createJsonReaderFactory();
   private static final JsonWriterFactory jsonWriterFactory = createJsonWriterFactory();
   private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

   private ProvisioningJSONMsgProcessor() {
   }

   private static final JsonReaderFactory createJsonReaderFactory() {
      return Json.createReaderFactory((Map)null);
   }

   private static final JsonWriterFactory createJsonWriterFactory() {
      Map writerFactoryConfiguration = new HashMap();
      writerFactoryConfiguration.put("javax.json.stream.JsonGenerator.prettyPrinting", Boolean.TRUE);
      return Json.createWriterFactory(writerFactoryConfiguration);
   }

   /** @deprecated */
   @Deprecated
   static final JsonObject readJSONFromInputStream(InputStream inputStream) {
      JsonReader jsonReader = jsonReaderFactory.createReader(inputStream, UTF_8_CHARSET);

      assert jsonReader != null;

      return jsonReader.readObject();
   }

   /** @deprecated */
   @Deprecated
   static final void writeJSONToOutputStream(JsonObject jsonObject, OutputStream outputStream) {
      JsonWriter jsonWriter = jsonWriterFactory.createWriter(outputStream, UTF_8_CHARSET);

      assert jsonWriter != null;

      jsonWriter.writeObject(jsonObject);
   }

   static final String getJSONString(JsonStructure jsonStructure) {
      StringWriter writer = new StringWriter();
      JsonWriter jsonWriter = jsonWriterFactory.createWriter(writer);
      Throwable var3 = null;

      try {
         assert jsonWriter != null;

         jsonWriter.write(jsonStructure);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (jsonWriter != null) {
            if (var3 != null) {
               try {
                  jsonWriter.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               jsonWriter.close();
            }
         }

      }

      return writer.toString();
   }

   static final JsonObject getJSONObject(String jsonData) {
      JsonObject jsonObject = null;
      JsonReader jsonReader = jsonReaderFactory.createReader(new StringReader(jsonData));
      Throwable var3 = null;

      try {
         assert jsonReader != null;

         jsonObject = jsonReader.readObject();
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (jsonReader != null) {
            if (var3 != null) {
               try {
                  jsonReader.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               jsonReader.close();
            }
         }

      }

      return jsonObject;
   }

   static final JsonObject createJSONRequestForProvisioningPartition(String partitionName, String componentType, Properties configurableAttributeValues) {
      return createJSONRequestForProvisioningPartition(partitionName, componentType, configurableAttributeValues, (Properties)null);
   }

   static final JsonObject createJSONRequestForProvisioningPartition(String partitionName, String componentType, Properties configurableAttributeValues, Properties provisioningOperationProperties) {
      return oldCreateJSONRequestForProvisioningPartition(partitionName, componentType, configurableAttributeValues, provisioningOperationProperties);
   }

   static final JsonObject oldCreateJSONRequestForProvisioningPartition(String partitionName, String componentType, Properties configurableAttributeValues, Properties provisioningOperationProperties) {
      Objects.requireNonNull(partitionName);
      Objects.requireNonNull(componentType);
      JsonBuilderFactory jsonBuilderFactory = Json.createBuilderFactory((Map)null);
      Set allComponentTypes = null;
      Iterator var7;
      String currComponentType;
      String prefix;
      if (configurableAttributeValues != null) {
         Collection propertyNames = configurableAttributeValues.stringPropertyNames();
         if (propertyNames != null && !propertyNames.isEmpty()) {
            allComponentTypes = new HashSet();
            var7 = propertyNames.iterator();

            while(var7.hasNext()) {
               currComponentType = (String)var7.next();
               if (currComponentType != null) {
                  prefix = PropertiesHelper.getComponentType(currComponentType);
                  if (prefix != null) {
                     allComponentTypes.add(prefix);
                  }
               }
            }
         }
      }

      JsonArrayBuilder allConfigurableAttributeValuesBuilder = null;
      if (allComponentTypes != null && !allComponentTypes.isEmpty()) {
         allConfigurableAttributeValuesBuilder = jsonBuilderFactory.createArrayBuilder();
         var7 = allComponentTypes.iterator();

         label88:
         while(true) {
            do {
               if (!var7.hasNext()) {
                  break label88;
               }

               currComponentType = (String)var7.next();
            } while(currComponentType == null);

            debug("Creating configurable attributes for component: " + currComponentType);
            prefix = currComponentType + ".";
            JsonObjectBuilder dependentComponentJsonBuilder = jsonBuilderFactory.createObjectBuilder();
            dependentComponentJsonBuilder.add("name", currComponentType);
            JsonArrayBuilder dependentComponentConfigurableAttributesBuilder = jsonBuilderFactory.createArrayBuilder();
            Iterator var12 = configurableAttributeValues.stringPropertyNames().iterator();

            while(var12.hasNext()) {
               String propertyName = (String)var12.next();
               if (propertyName != null && propertyName.startsWith(prefix)) {
                  String actualPropertyName = PropertiesHelper.getActualPropertyName(propertyName);
                  String value = configurableAttributeValues.getProperty(propertyName);
                  debug("\t\t" + actualPropertyName + "=" + value);
                  dependentComponentConfigurableAttributesBuilder.add(jsonBuilderFactory.createObjectBuilder().add("name", actualPropertyName).add("value", value));
               }
            }

            dependentComponentJsonBuilder.add("configurableAttributes", dependentComponentConfigurableAttributesBuilder);
            allConfigurableAttributeValuesBuilder.add(dependentComponentJsonBuilder);
         }
      }

      JsonArrayBuilder propertiesBuilder = null;
      if (provisioningOperationProperties != null) {
         Collection propertyNames = provisioningOperationProperties.stringPropertyNames();
         if (propertyNames != null && !propertyNames.isEmpty()) {
            propertiesBuilder = jsonBuilderFactory.createArrayBuilder();

            assert propertiesBuilder != null;

            Iterator var20 = propertyNames.iterator();

            while(var20.hasNext()) {
               String propertyName = (String)var20.next();
               if (propertyName != null) {
                  JsonObjectBuilder objectBuilder = jsonBuilderFactory.createObjectBuilder();

                  assert objectBuilder != null;

                  objectBuilder.add("name", propertyName);
                  objectBuilder.add("value", provisioningOperationProperties.getProperty(propertyName));
                  propertiesBuilder.add(objectBuilder);
               }
            }
         }
      }

      JsonObjectBuilder objectBuilder = jsonBuilderFactory.createObjectBuilder();

      assert objectBuilder != null;

      objectBuilder.add("partitionName", partitionName);
      objectBuilder.add("componentName", componentType);
      if (allConfigurableAttributeValuesBuilder != null) {
         objectBuilder.add("configurableAttributes", allConfigurableAttributeValuesBuilder);
      }

      if (propertiesBuilder != null) {
         objectBuilder.add("properties", propertiesBuilder);
      }

      return objectBuilder.build();
   }

   static final void writeConfigurableAttributes(String componentType, JsonObject jsonObject, String filename) throws IOException {
      Writer writer = new FileWriter(filename);
      Throwable var4 = null;

      try {
         writeConfigurableAttributes(componentType, jsonObject, (Writer)writer);
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (writer != null) {
            if (var4 != null) {
               try {
                  writer.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               writer.close();
            }
         }

      }

   }

   static final void writeConfigurableAttributes(String componentType, JsonObject jsonObject, Writer writer) {
      Objects.requireNonNull(componentType);
      Objects.requireNonNull(writer);
      TreeMap sortedComponentProperties = new TreeMap();
      TreeMap sortedDependentComponentProperties = new TreeMap();
      boolean isComponentFound = false;
      JsonArray componentsArray;
      if (jsonObject == null) {
         componentsArray = null;
      } else {
         componentsArray = jsonObject.getJsonArray("components");
      }

      if (componentsArray == null) {
         debug("No components found");
      } else {
         Iterator var7 = componentsArray.getValuesAs(JsonObject.class).iterator();

         label99:
         while(var7.hasNext()) {
            JsonObject component = (JsonObject)var7.next();
            if (component != null) {
               String componentName = component.getString("name");
               if (componentType.equals(componentName)) {
                  debug("Processing component: " + componentName);
                  isComponentFound = true;
                  JsonArray configurableAttributesArray = component.getJsonArray("configurableAttributes");
                  if (configurableAttributesArray == null) {
                     debug("No configurable attributes found for component: " + componentName);
                     break;
                  }

                  debug("Found configurable attributes for component: " + componentName);
                  Iterator var11 = configurableAttributesArray.getValuesAs(JsonObject.class).iterator();

                  while(true) {
                     while(true) {
                        JsonObject configurableAttributeObj;
                        String configurableAttributeComponentName;
                        do {
                           do {
                              if (!var11.hasNext()) {
                                 break label99;
                              }

                              configurableAttributeObj = (JsonObject)var11.next();
                           } while(configurableAttributeObj == null);

                           configurableAttributeComponentName = configurableAttributeObj.getString("name");
                        } while(configurableAttributeComponentName == null);

                        JsonArray componentConfigurableAttributesArray = configurableAttributeObj.getJsonArray("configurableAttributes");
                        if (componentConfigurableAttributesArray != null) {
                           debug("Reading configurable properties for component: " + configurableAttributeComponentName);
                           Iterator var15 = componentConfigurableAttributesArray.getValuesAs(JsonObject.class).iterator();

                           while(var15.hasNext()) {
                              JsonObject configurableAttribute = (JsonObject)var15.next();
                              if (configurableAttribute != null) {
                                 String name = configurableAttribute.getString("name");
                                 if (name == null || name.isEmpty()) {
                                    error("Null or Empty property name found for component: " + configurableAttributeComponentName);
                                    throw new RuntimeException("Null or Empty property name found for component: " + configurableAttributeComponentName);
                                 }

                                 String escapedConfigurableAttributeComponentName = configurableAttributeComponentName.replaceAll(" ", "\\\\ ");
                                 String prefixedName = escapedConfigurableAttributeComponentName + "." + name;
                                 String value = configurableAttribute.isNull("defaultValue") ? "" : configurableAttribute.getString("defaultValue");
                                 debug("\t\t" + prefixedName + " = " + value);
                                 if (configurableAttributeComponentName.equals(componentType)) {
                                    sortedComponentProperties.put(prefixedName, configurableAttribute);
                                 } else {
                                    sortedDependentComponentProperties.put(prefixedName, configurableAttribute);
                                 }
                              }
                           }
                        } else {
                           debug("No configurable properties found for component: " + configurableAttributeComponentName);
                        }
                     }
                  }
               }

               debug("Ignoring component: " + componentName);
            }
         }
      }

      if (!isComponentFound) {
         throw new IllegalArgumentException("Error = No such provisioning component: " + componentType);
      } else {
         try {
            BufferedWriter bufferedWriter;
            if (writer instanceof BufferedWriter) {
               bufferedWriter = (BufferedWriter)writer;
            } else {
               bufferedWriter = new BufferedWriter(writer);
            }

            PropertiesHelper.writeHeader(componentType, bufferedWriter);
            PropertiesHelper.writePropertiesToFile(sortedComponentProperties, bufferedWriter);
            PropertiesHelper.writePropertiesToFile(sortedDependentComponentProperties, bufferedWriter);
            bufferedWriter.flush();
            bufferedWriter.close();
         } catch (Exception var21) {
            error("Error writing properties to file. " + var21.getMessage());
            throw new RuntimeException("Error writing properties to file. " + var21.getMessage(), var21);
         }
      }
   }

   private static final void error(String message) {
      PropertiesHelper.error("ProvisioningJSONMsgProcessor", message);
   }

   private static final void debug(String message) {
      PropertiesHelper.debug("ProvisioningJSONMsgProcessor", message);
   }
}
