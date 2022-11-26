package weblogic.management.patching;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import org.codehaus.jettison.json.JSONException;
import weblogic.management.ManagementException;
import weblogic.management.patching.agent.PlatformUtils;
import weblogic.management.patching.commands.ExtensionJarExtractorCommand;
import weblogic.management.patching.commands.PatchingMessageTextFormatter;
import weblogic.management.workflow.WorkflowBuilder;
import weblogic.management.workflow.command.SharedState;

public class ExtensionManager {
   List extensionPropertiesList;
   Map extensionsMap;
   Map extensionJars;

   public ExtensionManager(List extensionPropertiesList, String extensionBaseDir) throws ManagementException {
      this.extensionPropertiesList = extensionPropertiesList;
      this.extensionsMap = new HashMap();
      this.extensionJars = new HashMap();

      ExtensionProperties extensionProperties;
      for(Iterator var3 = extensionPropertiesList.iterator(); var3.hasNext(); this.overrideExtensionParams(extensionProperties)) {
         extensionProperties = (ExtensionProperties)var3.next();
         Path jarPath = Paths.get(extensionBaseDir).resolve(extensionProperties.getExtensionJarLocation());
         if (!jarPath.toFile().exists()) {
            throw new ManagementException(PatchingMessageTextFormatter.getInstance().getExtensionJarNotFound(extensionProperties.getExtensionJarLocation(), extensionBaseDir));
         }

         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ExtensionManager calling addExtensionsFromJar with param:extensionProperties:" + extensionProperties + "extensionBaseDir" + extensionBaseDir);
         }

         this.addExtensionsFromJar(extensionProperties, extensionBaseDir);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("ExtensionManager calling overrideExtensionParams with param:extensionProperties:" + extensionProperties);
         }
      }

   }

   public void overrideExtensionParams(ExtensionProperties extensionProperties) {
      Iterator var2 = EnumSet.allOf(ExtensionPoint.class).iterator();

      while(var2.hasNext()) {
         ExtensionPoint ep = (ExtensionPoint)var2.next();
         List epExtensions = (List)this.extensionsMap.get(ep);
         Iterator var5 = epExtensions.iterator();

         while(var5.hasNext()) {
            ExtensionConfiguration extensionConfiguration = (ExtensionConfiguration)var5.next();
            extensionConfiguration.overrideExtensionParams(extensionProperties.getExtensionParameters());
         }
      }

   }

   public Set getExtensionJarsForExtensionPoints() {
      Set extensionJarsForRemoteExtensions = new HashSet();
      Iterator var2 = EnumSet.allOf(ExtensionPoint.class).iterator();

      while(var2.hasNext()) {
         ExtensionPoint ep = (ExtensionPoint)var2.next();
         if (this.extensionJars.containsKey(ep)) {
            extensionJarsForRemoteExtensions.addAll((Collection)((List)this.extensionJars.get(ep)).stream().map((e) -> {
               return e.getRelativeJarLocation();
            }).collect(Collectors.toList()));
         }
      }

      return extensionJarsForRemoteExtensions;
   }

   public List getExtensionScriptsForOfflineBeforeUpdateExtension() {
      return (List)((List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_OFFLINE_BEFORE_UPDATE)).stream().map((e) -> {
         return (String)e.getExtensionPointParams().get("scriptName");
      }).collect(Collectors.toList());
   }

   public List getExtensionScriptsForOfflineAfterUpdateExtension() {
      return (List)((List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_OFFLINE_AFTER_UPDATE)).stream().map((e) -> {
         return (String)e.getExtensionPointParams().get("scriptName");
      }).collect(Collectors.toList());
   }

   public void addExtensionsFromJar(ExtensionProperties extensionProperties, String extensionBaseDir) throws ManagementException {
      Path jarPath = Paths.get(extensionBaseDir).resolve(extensionProperties.getExtensionJarLocation());

      try {
         ExtensionJar extensionJar = new ExtensionJar(jarPath);
         extensionJar.setRelativeJarLocation(extensionProperties.getExtensionJarLocation());
         Iterator var5 = EnumSet.allOf(ExtensionPoint.class).iterator();

         while(var5.hasNext()) {
            ExtensionPoint ep = (ExtensionPoint)var5.next();
            if (extensionJar.getExtensionPointsInJar().contains(ep)) {
               Object epJars;
               if (this.extensionJars.containsKey(ep)) {
                  epJars = (List)this.extensionJars.get(ep);
               } else {
                  epJars = new ArrayList();
               }

               ((List)epJars).add(extensionJar);
               this.extensionJars.put(ep, epJars);
            }

            if (this.extensionsMap.containsKey(ep)) {
               List epExtensions = (List)this.extensionsMap.get(ep);
               epExtensions.addAll(extensionJar.getExtensionsForExtensionPoint(ep));
            } else {
               this.extensionsMap.put(ep, extensionJar.getExtensionsForExtensionPoint(ep));
            }
         }

      } catch (IOException var8) {
         throw new ManagementException(PatchingMessageTextFormatter.getInstance().cannotReadJarFile(jarPath.toString()), var8);
      } catch (IllegalArgumentException | JSONException var9) {
         throw new ManagementException(var9.getMessage(), var9);
      }
   }

   public void validateExtensions() throws IllegalArgumentException {
      List allExtensionConfigurations = new ArrayList();
      List allExtensionJars = new ArrayList();
      Iterator var3 = EnumSet.allOf(ExtensionPoint.class).iterator();

      while(var3.hasNext()) {
         ExtensionPoint ep = (ExtensionPoint)var3.next();
         if (this.extensionsMap.containsKey(ep)) {
            allExtensionConfigurations.addAll((Collection)this.extensionsMap.get(ep));
         }

         if (this.extensionJars.containsKey(ep)) {
            allExtensionJars.addAll((Collection)this.extensionJars.get(ep));
         }
      }

      var3 = allExtensionConfigurations.iterator();

      while(var3.hasNext()) {
         ExtensionConfiguration extension = (ExtensionConfiguration)var3.next();
         String extClassName = extension.getExtensionPointClassName();

         try {
            Class extensionClazz = Class.forName(extClassName);
            Class superClass = extensionClazz.getSuperclass();
            Class extRevertInterface = Class.forName("weblogic.management.patching.extensions.ExtensionRevert");
            if (!superClass.equals(extRevertInterface)) {
               throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().unsupportedExtensionClass(extClassName));
            }
         } catch (ClassNotFoundException var9) {
            throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().unsupportedExtensionClass(extClassName), var9);
         }
      }

      ExtensionJar extensionJar;
      List unixScriptsInJar;
      Set scriptsInConfiguration;
      Iterator var14;
      String scriptName;
      if (PlatformUtils.isUnix) {
         var3 = allExtensionJars.iterator();

         while(var3.hasNext()) {
            extensionJar = (ExtensionJar)var3.next();
            unixScriptsInJar = extensionJar.getUnixScripts();
            scriptsInConfiguration = extensionJar.getScriptsInConfiguration();
            var14 = scriptsInConfiguration.iterator();

            while(var14.hasNext()) {
               scriptName = (String)var14.next();
               if (!unixScriptsInJar.contains(scriptName)) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().scriptNotFound(scriptName, extensionJar.getRelativeJarLocation(), "unix"));
               }
            }
         }
      } else {
         var3 = allExtensionJars.iterator();

         while(var3.hasNext()) {
            extensionJar = (ExtensionJar)var3.next();
            unixScriptsInJar = extensionJar.getWindowsScripts();
            scriptsInConfiguration = extensionJar.getScriptsInConfiguration();
            var14 = scriptsInConfiguration.iterator();

            while(var14.hasNext()) {
               scriptName = (String)var14.next();
               if (!unixScriptsInJar.contains(scriptName)) {
                  throw new IllegalArgumentException(PatchingMessageTextFormatter.getInstance().scriptNotFound(scriptName, extensionJar.getRelativeJarLocation(), "windows"));
               }
            }
         }
      }

   }

   public WorkflowBuilder getPerExtensionBuilder(ExtensionConfiguration extension, Map availableSharedStates) throws ClassNotFoundException {
      WorkflowBuilder perExtensionBuilder = WorkflowBuilder.newInstance();
      Class extensionClazz = this.getClass().getClassLoader().loadClass(extension.getExtensionPointClassName());
      Map mapForExtension = this.getRequiredSharedStates(availableSharedStates, extension, extensionClazz);
      perExtensionBuilder.add(mapForExtension);
      perExtensionBuilder.add(extensionClazz);
      return perExtensionBuilder;
   }

   public WorkflowBuilder getExtensionsForOnlineBeforeUpdate(Map availableSharedStates) throws ClassNotFoundException {
      List extensionsForOnlineBeforeUpdate = (List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_ONLINE_BEFORE_UPDATE);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Iterator var4 = extensionsForOnlineBeforeUpdate.iterator();

      while(var4.hasNext()) {
         ExtensionConfiguration extension = (ExtensionConfiguration)var4.next();
         builder.add(this.getPerExtensionBuilder(extension, availableSharedStates));
      }

      return builder;
   }

   public WorkflowBuilder getExtensionsForEachNode(Map availableSharedStates) throws ClassNotFoundException {
      List extensionsForEachNode = (List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_EACH_NODE);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Iterator var4 = extensionsForEachNode.iterator();

      while(var4.hasNext()) {
         ExtensionConfiguration extension = (ExtensionConfiguration)var4.next();
         builder.add(this.getPerExtensionBuilder(extension, availableSharedStates));
      }

      return builder;
   }

   public WorkflowBuilder getExtensionsForOnlineAfterUpdate(Map availableSharedStates) throws ClassNotFoundException {
      List extensionsForEachNode = (List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_ONLINE_AFTER_UPDATE);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Iterator var4 = extensionsForEachNode.iterator();

      while(var4.hasNext()) {
         ExtensionConfiguration extension = (ExtensionConfiguration)var4.next();
         builder.add(this.getPerExtensionBuilder(extension, availableSharedStates));
      }

      return builder;
   }

   public WorkflowBuilder getExtensionsForOnlineAfterServerStart(Map availableSharedStates) throws ClassNotFoundException {
      List extensionsForEachNode = (List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_ONLINE_AFTER_SERVER_START);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Iterator var4 = extensionsForEachNode.iterator();

      while(var4.hasNext()) {
         ExtensionConfiguration extension = (ExtensionConfiguration)var4.next();
         builder.add(this.getPerExtensionBuilder(extension, availableSharedStates));
      }

      return builder;
   }

   public WorkflowBuilder getExtensionsForRolloutSuccess(Map availableSharedStates) throws ClassNotFoundException {
      List extensionsForEachNode = (List)this.extensionsMap.get(ExtensionPoint.EXTENSION_POINT_ROLLOUT_SUCCESS);
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Iterator var4 = extensionsForEachNode.iterator();

      while(var4.hasNext()) {
         ExtensionConfiguration extension = (ExtensionConfiguration)var4.next();
         builder.add(this.getPerExtensionBuilder(extension, availableSharedStates));
      }

      return builder;
   }

   public WorkflowBuilder getJarExtractionCommandBuilder(String extractionBaseDir) {
      WorkflowBuilder builder = WorkflowBuilder.newInstance();
      Iterator var3 = this.extensionPropertiesList.iterator();

      while(var3.hasNext()) {
         ExtensionProperties extensionProps = (ExtensionProperties)var3.next();
         WorkflowBuilder jarExtractionBuilder = WorkflowBuilder.newInstance();
         Map hashmap = new HashMap();
         hashmap.put("extensionJarLocation", extensionProps.extensionJarLocation);
         hashmap.put("jarExtractionDir", extractionBaseDir);
         jarExtractionBuilder.add(hashmap);
         jarExtractionBuilder.add(ExtensionJarExtractorCommand.class);
         builder.add(jarExtractionBuilder);
      }

      return builder;
   }

   public Map getRequiredSharedStates(Map availableSharedStates, ExtensionConfiguration extension, Class extensionClazz) {
      Map map = new HashMap();
      Map extensionParams = extension.getExtensionPointParams();
      Field[] var7 = extensionClazz.getDeclaredFields();
      int var8 = var7.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Field f = var7[var9];
         if (f.getAnnotation(SharedState.class) != null) {
            if (extensionParams.containsKey(f.getName())) {
               map.put(f.getName(), extensionParams.get(f.getName()));
            } else if (availableSharedStates.containsKey(f.getName())) {
               map.put(f.getName(), availableSharedStates.get(f.getName()));
            }

            if (f.getName().equals("scriptEnvProps")) {
               Properties scriptEnvProp;
               if (map.containsKey("scriptEnvProps")) {
                  scriptEnvProp = (Properties)map.get("scriptEnvProps");
               } else {
                  scriptEnvProp = new Properties();
               }

               Iterator var11 = extensionParams.keySet().iterator();

               while(var11.hasNext()) {
                  String paramKey = (String)var11.next();
                  scriptEnvProp.put(paramKey, extensionParams.get(paramKey));
               }

               map.put("scriptEnvProps", scriptEnvProp);
            }
         }
      }

      return map;
   }
}
