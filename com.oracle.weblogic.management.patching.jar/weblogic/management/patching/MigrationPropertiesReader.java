package weblogic.management.patching;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;

public class MigrationPropertiesReader {
   public List readMigrationProperties(String migrationPropertiesPath) throws JSONException, IOException, IllegalArgumentException {
      ArrayList properties = new ArrayList();
      File jsonPropertiesFile = new File(migrationPropertiesPath);
      String propType = PatchingMessageTextFormatter.getInstance().getMigrationString();
      if (!jsonPropertiesFile.exists()) {
         PatchingDebugLogger.debug("Unable to find migration properties file: " + migrationPropertiesPath);
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().propsFileDoesNotExist(propType, migrationPropertiesPath));
      } else if (!jsonPropertiesFile.canRead()) {
         PatchingDebugLogger.debug("Unable to read migration properties file: " + migrationPropertiesPath);
         throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().cannotReadFile(propType, migrationPropertiesPath));
      } else {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Reading properties file: " + migrationPropertiesPath);
         }

         byte[] encoded = Files.readAllBytes(Paths.get(migrationPropertiesPath));
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
            PatchingDebugLogger.debug("Extracting migrations array");
         }

         JSONArray migrationsArray = readJson.getJSONArray("migrations");
         if (PatchingDebugLogger.isDebugEnabled()) {
            if (migrationsArray == null) {
               PatchingDebugLogger.debug("migrationArray is null");
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().emptyProps(migrationPropertiesPath));
            }

            PatchingDebugLogger.debug("Migrations extracted. Length is: " + migrationsArray.length());
         }

         int i;
         if (migrationsArray != null && migrationsArray.length() > 0) {
            for(i = 0; i < migrationsArray.length(); ++i) {
               MigrationProperties migrationProperties = new MigrationProperties();
               JSONObject appObject = migrationsArray.getJSONObject(i);
               String source = appObject.getString("source");
               migrationProperties.setSource(source);
               String migrationType = appObject.getString("migrationType");
               migrationProperties.setMigrationType(migrationType);
               if (appObject.has("failback")) {
                  boolean failback = appObject.getBoolean("failback");
                  migrationProperties.setFailback(failback);
               }

               if (!migrationType.equalsIgnoreCase("none")) {
                  String destination = appObject.getString("destination");
                  migrationProperties.setDestination(destination);
               }

               properties.add(migrationProperties);
            }
         } else if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Migration array is empty");
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            for(i = 0; i < properties.size(); ++i) {
               PatchingDebugLogger.debug("Migration properties loaded for migration: " + ((MigrationProperties)properties.get(i)).getSource());
            }
         }

         return properties;
      }
   }
}
