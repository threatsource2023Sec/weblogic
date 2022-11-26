package com.oracle.weblogic.lifecycle.provisioning.wlst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;

final class ConfigurableAttributeValues {
   private static final int NORMAL = 0;
   private static final int ESCAPING = 1;
   private final JsonBuilderFactory builderFactory;

   ConfigurableAttributeValues() {
      this(Json.createBuilderFactory((Map)null));
   }

   ConfigurableAttributeValues(JsonBuilderFactory builderFactory) {
      if (builderFactory == null) {
         this.builderFactory = Json.createBuilderFactory((Map)null);
      } else {
         this.builderFactory = builderFactory;
      }

      assert this.builderFactory != null;

   }

   public final Map toMap(Map keyValuePairs) {
      String cn = this.getClass().getName();
      String mn = "toMap";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "toMap", keyValuePairs);
      }

      Map returnValue = null;
      if (keyValuePairs != null) {
         Properties properties;
         if (keyValuePairs instanceof Properties) {
            properties = (Properties)keyValuePairs;
         } else {
            properties = new Properties();
            if (!keyValuePairs.isEmpty()) {
               Collection entrySet = keyValuePairs.entrySet();
               if (entrySet != null && !entrySet.isEmpty()) {
                  Iterator var8 = entrySet.iterator();

                  label87:
                  while(true) {
                     while(true) {
                        Map.Entry entry;
                        Object key;
                        do {
                           do {
                              if (!var8.hasNext()) {
                                 break label87;
                              }

                              entry = (Map.Entry)var8.next();
                           } while(entry == null);

                           key = entry.getKey();
                        } while(!(key instanceof String));

                        Object value = entry.getValue();
                        if (value == null) {
                           properties.setProperty((String)key, (String)null);
                        } else if (value instanceof String) {
                           properties.setProperty((String)key, (String)value);
                        } else if (value instanceof Map) {
                           Collection valueEntries = ((Map)value).entrySet();
                           if (valueEntries != null && !valueEntries.isEmpty()) {
                              Iterator var13 = valueEntries.iterator();

                              while(var13.hasNext()) {
                                 Map.Entry valueEntry = (Map.Entry)var13.next();
                                 if (valueEntry != null) {
                                    Object valueEntryKey = valueEntry.getKey();
                                    if (valueEntryKey instanceof String) {
                                       Object valueEntryValue = valueEntry.getValue();
                                       if (valueEntryValue instanceof String) {
                                          properties.setProperty(((String)key).replace(".", "\\.") + "." + (String)valueEntryKey, (String)valueEntryValue);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         assert properties != null;

         returnValue = this.toMap(properties);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "toMap", returnValue);
      }

      return returnValue;
   }

   public final Map toMap(Properties properties) {
      String cn = this.getClass().getName();
      String mn = "toMap";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "toMap", properties);
      }

      Map returnValue = null;
      if (properties != null) {
         Collection qualifiedPropertyNames = properties.stringPropertyNames();
         if (qualifiedPropertyNames != null) {
            returnValue = new LinkedHashMap();
            if (!qualifiedPropertyNames.isEmpty()) {
               Iterator var7 = qualifiedPropertyNames.iterator();

               while(var7.hasNext()) {
                  String qualifiedPropertyName = (String)var7.next();
                  if (qualifiedPropertyName != null) {
                     ConfigurableAttribute configurableAttribute = parse(qualifiedPropertyName);
                     if (configurableAttribute != null && configurableAttribute.configurableAttributeName != null) {
                        Map configurableAttributes = (Map)returnValue.get(configurableAttribute.provisioningComponentName);
                        if (configurableAttributes == null) {
                           configurableAttributes = new LinkedHashMap();
                           returnValue.put(configurableAttribute.provisioningComponentName, configurableAttributes);
                        }

                        ((Map)configurableAttributes).put(configurableAttribute.configurableAttributeName, properties.getProperty(qualifiedPropertyName));
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "toMap", returnValue);
      }

      return returnValue;
   }

   public final JsonStructure toJson(Map map) {
      String cn = this.getClass().getName();
      String mn = "toJson";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "toJson", map);
      }

      JsonStructure returnValue = null;
      if (map != null) {
         Collection entrySet = map.entrySet();
         if (entrySet != null) {
            JsonArrayBuilder provisioningComponentsBuilder = this.builderFactory.createArrayBuilder();

            assert provisioningComponentsBuilder != null;

            if (!entrySet.isEmpty()) {
               Iterator var8 = entrySet.iterator();

               label87:
               while(true) {
                  Map.Entry entry;
                  String provisioningComponentName;
                  do {
                     do {
                        if (!var8.hasNext()) {
                           break label87;
                        }

                        entry = (Map.Entry)var8.next();
                     } while(entry == null);

                     provisioningComponentName = (String)entry.getKey();
                  } while(provisioningComponentName == null);

                  JsonObjectBuilder provisioningComponentBuilder = this.builderFactory.createObjectBuilder();

                  assert provisioningComponentBuilder != null;

                  provisioningComponentBuilder.add("name", provisioningComponentName);
                  Map configurableAttributeValues = (Map)entry.getValue();
                  if (configurableAttributeValues == null) {
                     provisioningComponentBuilder.addNull("configurableAttributes");
                  } else {
                     Collection configurableAttributeValuesEntrySet = configurableAttributeValues.entrySet();
                     if (configurableAttributeValuesEntrySet == null) {
                        provisioningComponentBuilder.addNull("configurableAttributes");
                     } else {
                        JsonArrayBuilder configurableAttributeValuesArrayBuilder = this.builderFactory.createArrayBuilder();

                        assert configurableAttributeValuesArrayBuilder != null;

                        Iterator var15 = configurableAttributeValuesEntrySet.iterator();

                        while(var15.hasNext()) {
                           Map.Entry configurableAttributeValuesEntry = (Map.Entry)var15.next();
                           if (configurableAttributeValuesEntry != null) {
                              String configurableAttributeName = (String)configurableAttributeValuesEntry.getKey();
                              if (configurableAttributeName != null) {
                                 JsonObjectBuilder configurableAttributeEntryBuilder = this.builderFactory.createObjectBuilder();

                                 assert configurableAttributeEntryBuilder != null;

                                 configurableAttributeEntryBuilder.add("name", configurableAttributeName);
                                 configurableAttributeEntryBuilder.add("value", (String)configurableAttributeValuesEntry.getValue());
                                 configurableAttributeValuesArrayBuilder.add(configurableAttributeEntryBuilder);
                              }
                           }
                        }

                        provisioningComponentBuilder.add("configurableAttributes", configurableAttributeValuesArrayBuilder);
                     }
                  }

                  provisioningComponentsBuilder.add(provisioningComponentBuilder);
               }
            }

            returnValue = provisioningComponentsBuilder.build();
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "toJson", returnValue);
      }

      return returnValue;
   }

   public static final void store(Properties configurableAttributes, Properties comments, Writer writer) throws IOException {
      String cn = ConfigurableAttributeValues.class.getName();
      String mn = "store";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "store", new Object[]{configurableAttributes, writer});
      }

      if (configurableAttributes != null && writer != null) {
         if (comments == null) {
            configurableAttributes.store(writer, " Configurable attribute values for provisioning partitions");
         } else {
            Collection propertyNames = comments.stringPropertyNames();
            if (propertyNames != null && !propertyNames.isEmpty()) {
               BufferedWriter bw;
               if (writer instanceof BufferedWriter) {
                  bw = (BufferedWriter)writer;
               } else {
                  bw = new BufferedWriter(writer);
               }

               StringWriter sw = new StringWriter();
               Throwable var9 = null;

               try {
                  configurableAttributes.store(sw, " Configurable attribute values for provisioning partitions");
                  BufferedReader br = new BufferedReader(new StringReader(sw.toString()));
                  Throwable var11 = null;

                  try {
                     while(true) {
                        String line;
                        if ((line = br.readLine()) == null) {
                           bw.flush();
                           break;
                        }

                        if (!line.isEmpty() && !line.startsWith("#") && !line.startsWith("!")) {
                           bw.newLine();
                           int state = 0;
                           int equalsIndex = -1;
                           int length = line.length();
                           StringBuilder propertyName = new StringBuilder();

                           int codePoint;
                           label433:
                           for(int i = 0; i < length; i += Character.charCount(codePoint)) {
                              codePoint = line.codePointAt(i);
                              switch (codePoint) {
                                 case 61:
                                    switch (state) {
                                       case 0:
                                          equalsIndex = i;
                                          break label433;
                                       case 1:
                                          state = 0;
                                          propertyName.append("\\=");
                                          continue;
                                       default:
                                          throw new IllegalStateException();
                                    }
                                 case 92:
                                    switch (state) {
                                       case 0:
                                          state = 1;
                                          continue;
                                       case 1:
                                          state = 0;
                                          propertyName.appendCodePoint(codePoint);
                                          continue;
                                       default:
                                          throw new IllegalStateException();
                                    }
                                 default:
                                    switch (state) {
                                       case 0:
                                          propertyName.appendCodePoint(codePoint);
                                          break;
                                       case 1:
                                          state = 0;
                                          propertyName.appendCodePoint(codePoint);
                                          break;
                                       default:
                                          throw new IllegalStateException();
                                    }
                              }
                           }

                           assert equalsIndex >= 0 : "No equals sign found? Did Properties#store(Writer, String) change?!";

                           String qualifiedPropertyName = propertyName.toString();
                           ConfigurableAttribute ca = parse(qualifiedPropertyName);
                           if (ca != null) {
                              String comment = comments.getProperty(qualifiedPropertyName);
                              if (comment != null) {
                                 bw.write("#" + comment);
                                 bw.newLine();
                              }
                           }
                        }

                        bw.write(line);
                        bw.newLine();
                     }
                  } catch (Throwable var41) {
                     var11 = var41;
                     throw var41;
                  } finally {
                     if (br != null) {
                        if (var11 != null) {
                           try {
                              br.close();
                           } catch (Throwable var40) {
                              var11.addSuppressed(var40);
                           }
                        } else {
                           br.close();
                        }
                     }

                  }
               } catch (Throwable var43) {
                  var9 = var43;
                  throw var43;
               } finally {
                  if (sw != null) {
                     if (var9 != null) {
                        try {
                           sw.close();
                        } catch (Throwable var39) {
                           var9.addSuppressed(var39);
                        }
                     } else {
                        sw.close();
                     }
                  }

               }
            } else {
               configurableAttributes.store(writer, " Configurable attribute values for provisioning partitions");
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "store");
      }

   }

   static final ConfigurableAttribute parse(String qualifiedPropertyName) {
      String cn = ConfigurableAttributeValues.class.getName();
      String mn = "parse";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "parse", qualifiedPropertyName);
      }

      String provisioningComponentName = null;
      String configurableAttributeName = null;
      if (qualifiedPropertyName != null) {
         int qualifiedPropertyNameLength = qualifiedPropertyName.length();
         if (qualifiedPropertyNameLength > 0) {
            StringBuilder sb = new StringBuilder();
            int state = 0;
            int offset = 0;

            while(true) {
               int codePoint;
               label68: {
                  if (offset < qualifiedPropertyNameLength) {
                     codePoint = qualifiedPropertyName.codePointAt(offset);
                     label66:
                     switch (codePoint) {
                        case 46:
                           switch (state) {
                              case 0:
                                 if (sb.length() > 0) {
                                    provisioningComponentName = sb.toString();
                                    sb.setLength(0);
                                 }

                                 if (offset + 1 < qualifiedPropertyNameLength) {
                                    configurableAttributeName = qualifiedPropertyName.substring(offset + 1);
                                 }
                                 break label66;
                              case 1:
                                 state = 0;
                                 sb.appendCodePoint(46);
                                 break label68;
                              default:
                                 throw new IllegalStateException();
                           }
                        case 92:
                           switch (state) {
                              case 0:
                                 state = 1;
                                 break label68;
                              case 1:
                                 state = 0;
                                 sb.appendCodePoint(92);
                                 break label68;
                              default:
                                 throw new IllegalStateException();
                           }
                        default:
                           switch (state) {
                              case 0:
                                 sb.appendCodePoint(codePoint);
                                 break label68;
                              case 1:
                                 state = 0;
                                 sb.appendCodePoint(92);
                                 sb.appendCodePoint(codePoint);
                                 break label68;
                              default:
                                 throw new IllegalStateException();
                           }
                     }
                  }

                  if (provisioningComponentName == null && configurableAttributeName == null && sb.length() > 0) {
                     configurableAttributeName = sb.toString();
                  }
                  break;
               }

               offset += Character.charCount(codePoint);
            }
         }
      }

      ConfigurableAttribute returnValue;
      if (configurableAttributeName == null) {
         returnValue = null;
      } else {
         returnValue = new ConfigurableAttribute(provisioningComponentName, configurableAttributeName);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "parse", returnValue);
      }

      return returnValue;
   }

   static final class ConfigurableAttribute {
      final String provisioningComponentName;
      final String configurableAttributeName;

      private ConfigurableAttribute(String provisioningComponentName, String configurableAttributeName) {
         Objects.requireNonNull(configurableAttributeName, "configurableAttributeName == null");
         this.provisioningComponentName = provisioningComponentName;
         this.configurableAttributeName = configurableAttributeName;
      }

      public final boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (other instanceof ConfigurableAttribute) {
            ConfigurableAttribute her = (ConfigurableAttribute)other;
            if (this.provisioningComponentName == null) {
               if (her.provisioningComponentName != null) {
                  return false;
               }
            } else if (!this.provisioningComponentName.equals(her.provisioningComponentName)) {
               return false;
            }

            if (this.configurableAttributeName == null) {
               if (her.configurableAttributeName != null) {
                  return false;
               }
            } else if (!this.configurableAttributeName.equals(her.configurableAttributeName)) {
               return false;
            }

            return true;
         } else {
            return false;
         }
      }

      public final int hashCode() {
         int hashCode = 17;
         int c = this.provisioningComponentName == null ? 0 : this.provisioningComponentName.hashCode();
         hashCode = 37 * hashCode + c;
         c = this.configurableAttributeName == null ? 0 : this.configurableAttributeName.hashCode();
         hashCode = 37 * hashCode + c;
         return hashCode;
      }

      public final String toString() {
         StringBuilder sb = new StringBuilder("[");
         sb.append(this.provisioningComponentName);
         sb.append(", ");
         sb.append(this.configurableAttributeName);
         sb.append("]");
         return sb.toString();
      }

      // $FF: synthetic method
      ConfigurableAttribute(String x0, String x1, Object x2) {
         this(x0, x1);
      }
   }
}
