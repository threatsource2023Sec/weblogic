package com.oracle.weblogic.lifecycle.config.database;

import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlService;
import weblogic.management.DomainDir;
import weblogic.utils.FileUtils;

public class ConfigUtil {
   private static char SEPARATOR_CHAR = '.';
   private static String SEPARATOR = "\\.";
   private static String PROPERTY_PATH = "/property";
   private static Logger logger = Logger.getLogger("LifeCycle");
   private static boolean isXMLEnabled = true;
   private static boolean isDatabaseEnabled = false;

   static char getSeparatorChar() {
      return SEPARATOR_CHAR;
   }

   static String getSeparator() {
      return SEPARATOR;
   }

   private static void add(Hub hub, WriteableType wt, String instanceId, Map map) {
      if (wt.getInstance(instanceId) != null) {
         throw new RuntimeException(instanceId + " already exists");
      } else {
         ConfigValidator.validateAdd(hub, wt, instanceId, map);
         wt.addInstance(instanceId, map);
      }
   }

   private static void delete(Hub hub, WriteableBeanDatabase wbd, String pathKey, String instanceId) {
      WriteableType wt = wbd.findOrAddWriteableType(pathKey);
      ConfigValidator.validateDelete(hub, wt, instanceId);
      wt.removeInstance(instanceId);
   }

   private static void add(Hub hub, WriteableBeanDatabase wbd, String pathKey, String instanceId, String parentKey, String key, Map map) {
      WriteableType wt = wbd.findOrAddWriteableType(pathKey);
      if (parentKey != null) {
         instanceId = instanceId + getSeparatorChar() + parentKey;
      }

      if (key != null) {
         instanceId = instanceId + getSeparatorChar() + key;
      }

      add(hub, wt, instanceId, map);
   }

   private static void add(Hub hub, String pathKey, String instanceId, String parentKey, String key, Map map) {
      WriteableBeanDatabase wbd = hub.getWriteableDatabaseCopy();
      add(hub, wbd, pathKey, instanceId, parentKey, key, map);
      wbd.commit();
   }

   static String addWithSeparator(String... strings) {
      StringBuilder s = new StringBuilder();
      boolean added = false;
      String[] var3 = strings;
      int var4 = strings.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String string = var3[var5];
         if (added) {
            s.append(getSeparatorChar());
         } else {
            added = true;
         }

         s.append(string);
      }

      return s.toString();
   }

   public static boolean isXMLEnabled() {
      return isXMLEnabled;
   }

   public static boolean isDatabaseEnabled() {
      return isDatabaseEnabled;
   }

   static List toList(Iterable iterable) {
      List copy = new ArrayList();
      Iterator i = iterable.iterator();

      while(i.hasNext()) {
         copy.add(i.next());
      }

      return copy;
   }

   public static void clear(Hub hub) {
      WriteableBeanDatabase wbd = hub.getWriteableDatabaseCopy();
      Iterator var2 = wbd.getAllTypes().iterator();

      while(var2.hasNext()) {
         Type type = (Type)var2.next();
         wbd.removeType(type.getName());
      }

      wbd.commit();
   }

   public static void enableXMLPersistence() {
      isXMLEnabled = true;
      isDatabaseEnabled = false;
   }

   public static void enableDatabasePersistence() {
      isXMLEnabled = false;
      isDatabaseEnabled = true;
   }

   public static void refreshHK2Cache(Hub hub) {
      logger.log(Level.INFO, "Resetting HK2 Cache");
   }

   public static String getPluginServiceName() {
      if (isXMLEnabled) {
         return "XMLConfigPersistencePlugin";
      } else {
         return isDatabaseEnabled() ? "DatabaseConfigPersistencePlugin" : null;
      }
   }

   static String getParentValue(String instanceId) {
      return getValueFromInstanceId(instanceId, 2);
   }

   static String getInstanceValue(String instanceId) {
      return getValueFromInstanceId(instanceId, 1);
   }

   private static String getValueFromInstanceId(String instanceId, int depth) {
      String[] keys = instanceId.split(getSeparator());
      return keys.length > depth ? keys[keys.length - depth] : null;
   }

   public static synchronized File loadXML(Hub hub, ServiceLocator locator) throws IOException {
      clear(hub);
      File lifecycleConfigFile = getConfigFile();
      URI url = null;
      if (lifecycleConfigFile != null) {
         url = lifecycleConfigFile.toURI();
      }

      Filter filter = BuilderHelper.createContractFilter(XmlHk2ConfigurationBean.class.getName());
      ServiceLocatorUtilities.removeFilter(locator, filter, true);
      XmlService xmlService = (XmlService)locator.getService(XmlService.class, new Annotation[0]);
      if (xmlService == null) {
         throw new IllegalStateException("No XmlService could be found in " + locator);
      } else {
         xmlService.unmarshal(url, LifecycleConfig.class);
         return lifecycleConfigFile;
      }
   }

   public static File getConfigFile() throws IOException {
      String CONFIG_FILE_NAME = "lifecycle-config.xml";
      String CONFIG_FILE_TEMPLATE_NAME = "lifecycle-config-template.xml";

      try {
         Class myClass = Class.forName("weblogic.kernel.KernelStatus");
         Method myMethod = myClass.getMethod("isServer");
         Object returnObject = myMethod.invoke((Object)null);
         Boolean isServer = (Boolean)returnObject;
         if (isServer) {
            File configDir = new File(DomainDir.getConfigDir());
            File configFile = new File(configDir, CONFIG_FILE_NAME);
            logger.log(Level.FINEST, "Running in WLS server, using lifecycle-config.xml in domain config directory: {0}", configDir);
            if (!configFile.exists()) {
               logger.log(Level.FINEST, "Config File Not Found.  Creating new file from lifecycle-config-template.xml");
               InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_TEMPLATE_NAME);
               FileUtils.writeToFile(is, configFile);
            }

            return configFile;
         }
      } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException | NoClassDefFoundError | ClassNotFoundException var9) {
         logger.log(Level.FINEST, "Caught exception trying to find lifeycle-config.xml under domain config directory. This is fine for SE case or unit tests.", var9);
      }

      logger.log(Level.FINEST, "Running in SE or unit tests");
      String seConfigDir = System.getProperty("lifecycle.configDir");
      if (seConfigDir != null && !seConfigDir.isEmpty()) {
         logger.log(Level.FINEST, "SE Configuration directory pointed by lifecycle.configDir={0}", seConfigDir);
         File configFile = new File(seConfigDir, CONFIG_FILE_NAME);
         logger.log(Level.FINEST, "Bootstapping SE Configuration from lifecycle.configDir ");
         if (configFile.exists()) {
            logger.log(Level.FINEST, "SE Configuration File Found in location pointed by lifecycle.configDir");
            return configFile;
         }

         logger.log(Level.FINEST, "SE Configuration File Not Found in location pointed by lifecycle.configDir");
      }

      String seConfigFile = System.getProperty("lifecycle.configFile");
      if (seConfigFile != null && !seConfigFile.isEmpty()) {
         File configFile = new File(seConfigFile);
         if (configFile.exists()) {
            logger.log(Level.FINEST, "Bootstapping Configuration from lifecycle.configFile={0}", seConfigFile);
            return configFile;
         }

         logger.log(Level.FINEST, "Configuration File Not Found in location pointed by lifecycle.configFile={0}", seConfigFile);
      }

      logger.log(Level.FINEST, "Bootstapping SE Configuration from current working directory");
      String workingDir = System.getProperty("user.dir");
      File configFile = new File(workingDir, CONFIG_FILE_NAME);
      if (configFile.exists()) {
         logger.log(Level.FINEST, "Config File Found in current working directory: {0}", workingDir);
      } else {
         logger.log(Level.FINEST, "Config File Not Found.  Creating new file from lifecycle-config-template.xml");
         InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_TEMPLATE_NAME);
         FileUtils.writeToFile(is, configFile);
      }

      return configFile;
   }
}
