package weblogic.management.patching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;

public class ExtensionConfigurationReader {
   public List readExtensionConfiguration(InputStream extensionConfiguration, String extensionConfigPath) throws JSONException, IOException, IllegalArgumentException {
      ArrayList extensions = new ArrayList();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Reading JSON from inputSteam");
      }

      String propType = PatchingMessageTextFormatter.getInstance().getExtensionString();
      StringBuilder jsonStrBuilder = new StringBuilder();
      BufferedReader reader = new BufferedReader(new InputStreamReader(extensionConfiguration));
      Throwable var8 = null;

      try {
         String str;
         try {
            while((str = reader.readLine()) != null) {
               jsonStrBuilder.append(str);
            }
         } catch (Throwable var24) {
            var8 = var24;
            throw var24;
         }
      } finally {
         if (reader != null) {
            if (var8 != null) {
               try {
                  reader.close();
               } catch (Throwable var23) {
                  var8.addSuppressed(var23);
               }
            } else {
               reader.close();
            }
         }

      }

      String jsonContent = jsonStrBuilder.toString();
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Loading JSON object");
      }

      JSONObject readJson = new JSONObject(jsonContent);
      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Loaded JSON object: " + readJson);
      }

      if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Extracting extensions array");
      }

      JSONArray extensionPointArray = readJson.getJSONArray("extensions");
      if (extensionPointArray == null) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("extensionPointArray is null");
         }

         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotLoadExtensionConfiguration(propType, extensionConfigPath));
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ExtensionPoints extracted. Length is: " + extensionPointArray.length());
         }

         if (extensionPointArray != null && extensionPointArray.length() > 0) {
            for(int i = 0; i < extensionPointArray.length(); ++i) {
               JSONObject extnObject = extensionPointArray.getJSONObject(i);
               String extensionPoint = extnObject.getString("extensionPoint");
               String extensionClass = extnObject.getString("extensionClass");
               ExtensionConfiguration extension = new ExtensionConfiguration(extensionPoint, extensionClass);
               if (extension.getExtensionPoint() == null) {
                  if (PatchingDebugLogger.isDebugEnabled()) {
                     PatchingDebugLogger.debug("Invalid extension point name: " + extensionPoint);
                  }

                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidExtensionName(extensionPoint, extensionConfigPath));
               }

               if (extnObject.has("extensionParameters")) {
                  JSONObject extParamJsonObj = extnObject.getJSONObject("extensionParameters");
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

                           throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().badFormatInFile(key, extensionConfigPath));
                        }

                        extension.addParameter(key, value);
                     }
                  }
               }

               extensions.add(extension);
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ExtensionPoints Array is empty");
         }

         return extensions;
      }
   }
}
