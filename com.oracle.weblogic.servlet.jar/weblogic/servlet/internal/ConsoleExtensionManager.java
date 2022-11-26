package weblogic.servlet.internal;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.Home;
import weblogic.management.DomainDir;
import weblogic.management.configuration.DomainMBean;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.FileUtils;
import weblogic.xml.stax.XMLStreamInputFactory;

public class ConsoleExtensionManager {
   private static final ConsoleExtensionManager INSTANCE = new ConsoleExtensionManager();
   private static final XMLInputFactory XMLINFACTORY = XMLStreamInputFactory.newInstance();
   private static final String extConfigFileName = "ConsoleExtensions.xml";

   public static ConsoleExtensionManager getInstance() {
      return INSTANCE;
   }

   protected ConsoleExtensionManager() {
   }

   public ExtensionDef[] findExtensions() {
      Map extensions = new HashMap();
      this.includeDirectoryContents(this.getHomeExtensionDir(), extensions, false);
      this.includeDirectoryContents(this.getHomeAutodeployExtensionDir(), extensions, true);
      this.includeDirectoryContents(this.getDomainExtensionDir(), extensions, true);
      File configFile = this.getHomeExtensionConfigFile();
      if (configFile.exists()) {
         this.applyExtensionConfigFile(configFile, extensions);
      }

      configFile = this.getDomainExtensionConfigFile();
      if (configFile.exists()) {
         this.applyExtensionConfigFile(configFile, extensions);
      }

      ExtensionDef[] result = new ExtensionDef[extensions.size()];
      extensions.values().toArray(result);
      return result;
   }

   public boolean shouldIncludeExtension(String moduleName, ExtensionDef extDef) {
      if (extDef != null) {
         if (moduleName.endsWith(".war")) {
            moduleName = moduleName.substring(0, moduleName.length() - 4);
         }

         if (!moduleName.equals(extDef.getModuleName())) {
            return false;
         } else if (!extDef.isEnabled()) {
            return false;
         } else {
            String[] services = extDef.getRequiredServices();
            if (services != null) {
               for(int s = 0; s < services.length; ++s) {
                  if (!this.isServiceEnabled(services[s])) {
                     return false;
                  }
               }
            }

            File archive = new File(extDef.getPath());
            return archive.exists();
         }
      } else {
         return false;
      }
   }

   public void release() {
   }

   protected boolean isServiceEnabled(String serviceName) {
      return WebServerRegistry.getInstance().getManagementProvider().isServiceAvailable(serviceName);
   }

   private void applyExtensionConfigFile(File file, Map extensions) {
      XMLStreamReader reader = null;

      try {
         reader = XMLINFACTORY.createXMLStreamReader(new FileReader(file.getPath()));

         while(reader.hasNext()) {
            switch (reader.next()) {
               case 1:
                  if ("extensions".equals(reader.getLocalName())) {
                     this.parseExtensions(reader, extensions);
                  }
            }
         }
      } catch (Exception var6) {
      }

      try {
         reader.close();
      } catch (XMLStreamException var5) {
      }

   }

   private void parseExtensions(XMLStreamReader reader, Map extensions) throws XMLStreamException {
      while(reader.hasNext()) {
         switch (reader.next()) {
            case 1:
               if (!"extension-def".equals(reader.getLocalName())) {
                  break;
               }

               String moduleValue = reader.getAttributeValue((String)null, "module");
               String pathValue = reader.getAttributeValue((String)null, "path");
               String enabledValue = reader.getAttributeValue((String)null, "enabled");
               String requiredServicesValue = reader.getAttributeValue((String)null, "requiredServices");
               String prerequisitesValue = reader.getAttributeValue((String)null, "prerequisites");
               if (isEmptyString(moduleValue)) {
                  moduleValue = "console";
               }

               boolean enabled = true;
               if (!isEmptyString(enabledValue)) {
                  enabled = "true".equals(enabledValue);
               }

               if (isEmptyString(pathValue)) {
                  break;
               }

               String name = this.computeExtensionDefName(moduleValue, pathValue);
               ExtensionDef extDef = (ExtensionDef)extensions.get(name);
               if (extDef != null) {
                  extDef.setEnabled(enabled);
                  extDef.setPrerequisites(prerequisitesValue);
                  extDef.setRequiredServices(requiredServicesValue);
               } else {
                  extDef = new ExtensionDef(moduleValue, pathValue, enabled, requiredServicesValue, prerequisitesValue);
               }

               extensions.put(extDef.getName(), extDef);
               break;
            case 2:
               if ("extensions".equals(reader.getLocalName())) {
                  return;
               }
         }
      }

   }

   protected File getDomainExtensionDir() {
      DomainMBean domain = WebServerRegistry.getInstance().getManagementProvider().getDomainMBean();
      String extDir = domain.getConsoleExtensionDirectory();
      if (extDir != null) {
         File f = new File(domain.getRootDirectory(), extDir);
         if (f.isDirectory()) {
            return f;
         }
      }

      return null;
   }

   protected File getHomeExtensionDir() {
      File wlhome = new File(Home.getPath());
      return wlhome.getParentFile() != null ? new File(wlhome.getParentFile(), "server" + File.separator + "lib" + File.separator + "console-ext") : null;
   }

   protected File getHomeAutodeployExtensionDir() {
      File wlhome = new File(Home.getPath());
      return wlhome.getParentFile() != null ? new File(wlhome.getParentFile(), "server" + File.separator + "lib" + File.separator + "console-ext" + File.separator + "autodeploy") : null;
   }

   protected File getDomainExtensionConfigFile() {
      String name = WebServerRegistry.getInstance().getManagementProvider().getServerName();
      return new File(DomainDir.getDataDirForServer(name) + File.separatorChar + "console" + File.separatorChar + "ConsoleExtensions.xml");
   }

   protected File getHomeExtensionConfigFile() {
      File wlhome = new File(Home.getPath());
      return wlhome.getParentFile() != null ? new File(wlhome.getParentFile(), "server" + File.separator + "lib" + File.separator + "console-ext" + File.separator + "ConsoleExtensions.xml") : null;
   }

   private void includeDirectoryContents(File extDir, Map extensions, boolean shouldEnable) {
      FileFilter WEBAPP_EXTN = FileUtils.makeExtensionFilter(new String[]{".jar", ".war"});
      if (extDir != null && extDir.exists() && extDir.isDirectory()) {
         File[] jarFiles = extDir.listFiles(WEBAPP_EXTN);

         for(int i = 0; jarFiles != null && i < jarFiles.length; ++i) {
            String extensionName = this.computeExtensionName(jarFiles[i].getAbsolutePath());
            String moduleName = "console";
            if (extensionName.toLowerCase().indexOf("help") > 0) {
               moduleName = "consolehelp";
            }

            ExtensionDef def = new ExtensionDef(moduleName, jarFiles[i].getAbsolutePath(), shouldEnable, (String)null, (String)null);
            def.setDiscovered(true);
            extensions.put(def.getName(), def);
         }
      }

   }

   protected String computeExtensionDefName(String moduleName, String path) {
      return moduleName + "." + this.computeExtensionName(path);
   }

   protected String computeExtensionName(String path) {
      int last = path.lastIndexOf(File.separator);
      if (last > 0) {
         path = path.substring(last + 1);
      }

      last = path.lastIndexOf(".");
      if (last > 0) {
         path = path.substring(0, last);
      }

      return path;
   }

   private static boolean isEmptyString(String string) {
      return string == null || string.length() == 0 || isWhitespace(string);
   }

   private static boolean isWhitespace(String string) {
      for(int i = 0; i < string.length(); ++i) {
         if (!Character.isWhitespace(string.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   public class ExtensionDef {
      private String name = null;
      private String extensionName = null;
      private String moduleName;
      private String path;
      private boolean enabled;
      private boolean discovered;
      private String[] prerequisites;
      private String[] requiredServices;

      ExtensionDef(String module, String pathValue, boolean enabled, String requiredServices, String prerequisites) {
         this.extensionName = ConsoleExtensionManager.this.computeExtensionName(pathValue);
         this.moduleName = module;
         this.name = this.moduleName + "." + this.extensionName;
         this.path = pathValue;
         this.setEnabled(enabled);
         this.setRequiredServices(requiredServices);
         this.setPrerequisites(prerequisites);
         this.setDiscovered(false);
      }

      public boolean isEnabled() {
         return this.enabled;
      }

      public boolean isDiscovered() {
         return this.discovered;
      }

      public String getName() {
         return this.name;
      }

      public String getExtensionName() {
         return this.extensionName;
      }

      public String getModuleName() {
         return this.moduleName;
      }

      public String getPath() {
         return this.path;
      }

      public File getFile() {
         return new File(this.getPath());
      }

      public void setEnabled(boolean enabled) {
         this.enabled = enabled;
      }

      public void setDiscovered(boolean discovered) {
         this.discovered = discovered;
      }

      public void setRequiredServices(String requiredServices) {
         if (requiredServices != null) {
            this.requiredServices = requiredServices.split(",");
         } else {
            this.requiredServices = null;
         }

      }

      public String[] getRequiredServices() {
         return this.requiredServices == null ? new String[0] : this.requiredServices;
      }

      public String getRequiredServiceList() {
         return this.requiredServices != null ? this.getStringArrayString(this.requiredServices) : null;
      }

      public void setPrerequisites(String prerequisites) {
         if (prerequisites != null) {
            this.prerequisites = prerequisites.split(",");
         } else {
            this.prerequisites = null;
         }

      }

      public String[] getPrerequisites() {
         return this.prerequisites == null ? new String[0] : this.prerequisites;
      }

      public String getPrerequisiteList() {
         return this.prerequisites != null ? this.getStringArrayString(this.prerequisites) : null;
      }

      private String getStringArrayString(String[] items) {
         StringBuffer results = new StringBuffer();
         if (items != null) {
            for(int i = 0; i < items.length; ++i) {
               results.append(items[i]);
               if (i + 1 != items.length) {
                  results.append(", ");
               }
            }
         }

         return results.toString();
      }

      public String toString() {
         return super.toString() + ",\n        name=" + this.name + ",\n        extensionName=" + this.extensionName + ",\n        moduleName=" + this.moduleName + ",\n        path=" + this.path + ",\n        enabled=" + this.enabled + ",\n        discovered=" + this.discovered + ",\n        prerequisites=" + this.getStringArrayString(this.prerequisites) + ",\n        requiredServices=" + this.getStringArrayString(this.requiredServices);
      }
   }
}
