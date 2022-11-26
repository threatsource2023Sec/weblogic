package weblogic.management.patching;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.patching.agent.PlatformUtils;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;

public class ExtensionPropertiesReader {
   public List readExtensionProperties(String extensionPropertiesPath, String extensionString) throws JSONException, IOException, IllegalArgumentException {
      List propertiesFromExtPath = null;
      List propertiesFromExtString = null;
      List propertiesList = new ArrayList();
      Iterator var6;
      ExtensionProperties extPropsString;
      if (extensionString != null) {
         propertiesFromExtString = this.parseExtensionString(extensionString);
         if (propertiesFromExtString != null && !propertiesFromExtString.isEmpty()) {
            var6 = propertiesFromExtString.iterator();

            while(var6.hasNext()) {
               extPropsString = (ExtensionProperties)var6.next();
               propertiesList.add(extPropsString);
            }
         }
      }

      if (extensionPropertiesPath != null && !extensionPropertiesPath.isEmpty()) {
         propertiesFromExtPath = this.getExtensionPropertiesFromJSON(extensionPropertiesPath);
         if (propertiesFromExtPath != null && !propertiesFromExtPath.isEmpty()) {
            if (propertiesFromExtString != null && !propertiesFromExtString.isEmpty()) {
               var6 = propertiesFromExtString.iterator();

               label62:
               while(var6.hasNext()) {
                  extPropsString = (ExtensionProperties)var6.next();
                  Iterator var8 = propertiesFromExtPath.iterator();

                  while(true) {
                     while(true) {
                        if (!var8.hasNext()) {
                           continue label62;
                        }

                        ExtensionProperties extPropsPath = (ExtensionProperties)var8.next();
                        if (extPropsPath.getExtensionJarLocation().equalsIgnoreCase(extPropsString.getExtensionJarLocation())) {
                           Set keysExtPath = extPropsPath.getExtensionParameters().keySet();
                           Iterator var11 = keysExtPath.iterator();

                           while(var11.hasNext()) {
                              String key = (String)var11.next();
                              if (!extPropsString.getExtensionParameters().containsKey(key)) {
                                 extPropsString.addParameter(key, (String)extPropsPath.getExtensionParameters().get(key));
                              } else if (PatchingDebugLogger.isDebugEnabled()) {
                                 PatchingDebugLogger.debug("ExtensionParam key " + key + " overridden with value " + (String)extPropsString.getExtensionParameters().get(key) + " from extensionString");
                              }
                           }
                        } else {
                           propertiesList.add(extPropsPath);
                        }
                     }
                  }
               }
            } else {
               var6 = propertiesFromExtPath.iterator();

               while(var6.hasNext()) {
                  extPropsString = (ExtensionProperties)var6.next();
                  propertiesList.add(extPropsString);
               }
            }
         }
      }

      return propertiesList;
   }

   public List parseExtensionString(String extensionString) throws IllegalArgumentException {
      ArrayList properties = new ArrayList();
      String extensionStringSplitRegex = ":";
      if (PlatformUtils.isWindows) {
         extensionStringSplitRegex = extensionStringSplitRegex + "(?!(\\/|\\d|\\\\))";
      } else {
         extensionStringSplitRegex = extensionStringSplitRegex + "(?!(\\/\\/|\\d))";
      }

      ArrayList extensionStringList = new ArrayList(Arrays.asList(extensionString.split(extensionStringSplitRegex)));
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("split regex: " + extensionStringSplitRegex);
         PatchingDebugLogger.debug("extension list: ");
         extensionStringList.stream().forEach(PatchingDebugLogger::debug);
      }

      if (extensionString.isEmpty()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Empty extensionString");
         }

         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat("Empty extensionString"));
      } else {
         Iterator var5 = extensionStringList.iterator();

         while(var5.hasNext()) {
            String extension = (String)var5.next();
            ArrayList extensionList = new ArrayList(Arrays.asList(extension.split(",")));
            if (extensionList.size() < 1) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Unable to parse extension: " + extension);
               }

               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat(extension));
            }

            String extensionJarLocation = (String)extensionList.get(0);
            if (extensionJarLocation.contains("=")) {
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("Unable to parse extensionJarLocation: " + extensionJarLocation);
               }

               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat(extensionJarLocation));
            }

            extensionList.remove(0);
            ExtensionProperties extensionProperty = new ExtensionProperties(extensionJarLocation);
            Iterator var10 = extensionList.iterator();

            while(var10.hasNext()) {
               String extensionParameter = (String)var10.next();
               String[] pair = extensionParameter.split("=");
               if (pair.length != 2) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("Unable to parse extensionParameter: " + extensionParameter);
                  }

                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat(extensionParameter));
               }

               if (extensionProperty.hasParameter(pair[0])) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("Unable to parse extensionParameter: " + extensionParameter);
                  }

                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormat(extensionParameter));
               }

               extensionProperty.addParameter(pair[0], pair[1]);
            }

            properties.add(extensionProperty);
         }

         return properties;
      }
   }

   public List getExtensionPropertiesFromJSON(String extensionPropertiesPath) throws JSONException, IOException {
      ArrayList extensions = new ArrayList();
      File jsonPropertiesFile = new File(extensionPropertiesPath);
      String propType = PatchingMessageTextFormatter.getInstance().getExtensionString();
      if (!jsonPropertiesFile.exists()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Unable to find extension properties file: " + extensionPropertiesPath);
         }

         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().propsFileDoesNotExist(propType, extensionPropertiesPath));
      } else if (!jsonPropertiesFile.canRead()) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Unable to read extension properties file: " + extensionPropertiesPath);
         }

         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotReadFile(propType, extensionPropertiesPath));
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Reading properties file: " + extensionPropertiesPath);
         }

         byte[] encoded = Files.readAllBytes(Paths.get(extensionPropertiesPath));
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Decoding bytes");
         }

         String jsonContent = new String(encoded, Charset.defaultCharset());
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Loading JSON object");
         }

         JSONObject readJson = new JSONObject(jsonContent);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Loaded JSON object: " + readJson);
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Extracting applications array");
         }

         JSONArray extensionArray = readJson.getJSONArray("extensionProperties");
         if (extensionArray == null) {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("extensionArray is null");
            }

            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotLoadExtensionProps(propType, extensionPropertiesPath));
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Extensions extracted. Length is: " + extensionArray.length());
            }

            if (extensionArray != null && extensionArray.length() > 0) {
               for(int i = 0; i < extensionArray.length(); ++i) {
                  JSONObject extnObject = extensionArray.getJSONObject(i);
                  String extensionJarLocation = extnObject.getString("extensionJar");
                  ExtensionProperties extensionProperties = new ExtensionProperties(extensionJarLocation);
                  JSONObject extParamJsonObj = null;

                  try {
                     extParamJsonObj = extnObject.getJSONObject("extensionParameters");
                  } catch (JSONException var17) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("extensionParameters not defined ");
                     }
                  }

                  if (extParamJsonObj != null) {
                     if (PatchingDebugLogger.isDebugEnabled()) {
                        PatchingDebugLogger.debug("Loaded JSON object : " + extParamJsonObj);
                     }

                     Iterator keysItr = extParamJsonObj.keys();

                     while(keysItr.hasNext()) {
                        String key = (String)keysItr.next();
                        String value = extParamJsonObj.get(key).toString();
                        if (value == null || value.isEmpty()) {
                           if (PatchingDebugLogger.isDebugEnabled()) {
                              PatchingDebugLogger.debug("Unable to parse extensionParameter: " + key);
                           }

                           throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormatInFile(key, extensionPropertiesPath));
                        }

                        extensionProperties.addParameter(key, value);
                     }
                  }

                  extensions.add(extensionProperties);
               }
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Extension Array is empty");
            }

            return extensions;
         }
      }
   }

   public void assertString(String paramName, String str) {
      if (str == null || str.length() == 0) {
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().undefinedParameter(paramName));
      }
   }
}
