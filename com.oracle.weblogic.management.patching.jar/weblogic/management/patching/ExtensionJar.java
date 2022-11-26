package weblogic.management.patching;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.codehaus.jettison.json.JSONException;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;

public class ExtensionJar {
   public File extensionJarFile;
   public static final String CONFIGURATION_FILE_NAME = "extensionConfiguration.json";
   public static final String CONFIGURATION_FILE_DIR = "resources";
   public static final String CONFIGURATION_FILE_SEPARATOR = "/";
   public static final String JAR_FILE_SEPARATOR = "/";
   public static final String CLASS_DIR = "classes";
   public static final String SCRIPTS_DIR = "scripts";
   public static final String UNIX_SCRIPT_DIR = "unix";
   public static final String WINDOWS_SCRIPT_DIR = "windows";
   String relativeJarLocation;
   Map extensionsMap;
   public List unixScripts;
   public Set scriptsInConfiguration;
   public List windowsScripts;

   public List getUnixScripts() {
      return this.unixScripts;
   }

   public void setUnixScripts(List unixScripts) {
      this.unixScripts = unixScripts;
   }

   public List getWindowsScripts() {
      return this.windowsScripts;
   }

   public void setWindowsScripts(List windowsScripts) {
      this.windowsScripts = windowsScripts;
   }

   public String getRelativeJarLocation() {
      return this.relativeJarLocation;
   }

   public void setRelativeJarLocation(String relativeJarLocation) {
      this.relativeJarLocation = relativeJarLocation;
   }

   public Set getScriptsInConfiguration() {
      return this.scriptsInConfiguration;
   }

   public ExtensionJar(Path jarPath) throws IOException, JSONException, IllegalArgumentException {
      this.extensionJarFile = jarPath.toFile();
      this.unixScripts = new ArrayList();
      this.windowsScripts = new ArrayList();
      this.scriptsInConfiguration = new HashSet();
      this.extensionsMap = new HashMap();
      JarFile extensionJar = new JarFile(this.extensionJarFile);
      Throwable var3 = null;

      try {
         new ArrayList();
         if (extensionJar.getJarEntry("resources/extensionConfiguration.json") == null) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().invalidJarFile(jarPath.toString()));
         }

         InputStream inputStream = extensionJar.getInputStream(extensionJar.getJarEntry("resources/extensionConfiguration.json"));
         Throwable var6 = null;

         List extensionInJar;
         try {
            ExtensionConfigurationReader extensionReader = new ExtensionConfigurationReader();
            String extensionConfigPath = Paths.get(jarPath.toString(), "resources", "extensionConfiguration.json").toString();
            extensionInJar = extensionReader.readExtensionConfiguration(inputStream, extensionConfigPath);
         } catch (Throwable var30) {
            var6 = var30;
            throw var30;
         } finally {
            if (inputStream != null) {
               if (var6 != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var29) {
                     var6.addSuppressed(var29);
                  }
               } else {
                  inputStream.close();
               }
            }

         }

         Iterator var34 = extensionInJar.iterator();

         while(var34.hasNext()) {
            ExtensionConfiguration extension = (ExtensionConfiguration)var34.next();
            Object epExtensions;
            if (this.extensionsMap.containsKey(extension.getExtensionPoint())) {
               epExtensions = (List)this.extensionsMap.get(extension.getExtensionPoint());
            } else {
               epExtensions = new ArrayList();
            }

            this.scriptsInConfiguration.add(extension.getParameterValue("scriptName"));
            ((List)epExtensions).add(extension);
            this.extensionsMap.put(extension.getExtensionPoint(), epExtensions);
         }

         Enumeration enums = extensionJar.entries();

         while(enums.hasMoreElements()) {
            JarEntry entry = (JarEntry)enums.nextElement();
            File f = new File(entry.getName());
            if (f.getParent() != null) {
               if (!entry.getName().endsWith("/") && f.getParent().endsWith("unix")) {
                  this.unixScripts.add(f.getName());
               }

               if (!entry.getName().endsWith("/") && f.getParent().endsWith("windows")) {
                  this.windowsScripts.add(f.getName());
               }
            }
         }
      } catch (Throwable var32) {
         var3 = var32;
         throw var32;
      } finally {
         if (extensionJar != null) {
            if (var3 != null) {
               try {
                  extensionJar.close();
               } catch (Throwable var28) {
                  var3.addSuppressed(var28);
               }
            } else {
               extensionJar.close();
            }
         }

      }

   }

   public List getExtensionsForExtensionPoint(ExtensionPoint ep) {
      return (List)(this.extensionsMap.containsKey(ep) ? (List)this.extensionsMap.get(ep) : new ArrayList());
   }

   public Set getExtensionPointsInJar() {
      return this.extensionsMap.keySet();
   }
}
