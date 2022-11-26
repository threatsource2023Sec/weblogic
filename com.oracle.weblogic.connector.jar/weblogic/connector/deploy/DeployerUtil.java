package weblogic.connector.deploy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import javax.resource.spi.work.SecurityContext;
import weblogic.application.ApplicationContextInternal;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.exception.RAException;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.AdminObjInfo;
import weblogic.connector.external.ConfigPropInfo;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.RAInfo;
import weblogic.connector.security.SecurityHelperFactory;
import weblogic.connector.security.work.CallbackHandlerFactoryImpl;
import weblogic.connector.work.SecurityContextProcessor;
import weblogic.connector.work.WorkManager;
import weblogic.j2ee.descriptor.wl.ConnectorWorkManagerBean;
import weblogic.management.DeploymentException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.IndexedDirectoryClassFinder;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.jars.VirtualJarFile;

public class DeployerUtil {
   private static String defaultNativeLibDir = "j2ca_native_lib";

   public static void createNativeLibDir(VirtualJarFile vjar, RAInfo raInfo, ApplicationContextInternal appCtx) throws DeploymentException {
      String nativeLibDir = raInfo.getNativeLibDir();
      if (nativeLibDir == null || nativeLibDir.length() == 0) {
         File appFile = new File(appCtx.getStagingPath());
         if (appFile.isDirectory()) {
            nativeLibDir = appCtx.getStagingPath();
         } else {
            nativeLibDir = (new File(appCtx.getStagingPath())).getParent();
         }

         if (!nativeLibDir.endsWith(File.separator)) {
            nativeLibDir = nativeLibDir + File.separator;
         }

         nativeLibDir = nativeLibDir + defaultNativeLibDir;
      }

      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      SecurityHelperFactory.getInstance().pushSubject(kernelId, kernelId);

      try {
         File d = new File(nativeLibDir);
         Iterator iter = vjar.entries();

         while(iter.hasNext()) {
            ZipEntry entry = (ZipEntry)iter.next();
            String tmp = entry.getName();
            if (tmp.endsWith(".dll") || tmp.endsWith(".so") || tmp.endsWith(".sl")) {
               String exMsg;
               try {
                  String fileName;
                  if (!d.exists() && !d.mkdirs() || !d.isDirectory()) {
                     fileName = Debug.getExceptionErrorCreatingNativeLibDir(d.getPath());
                     Exception exc = new IOException(fileName);
                     throw new DeploymentException(fileName, exc);
                  }

                  fileName = entry.getName();
                  int lastForwardSlashIndex = fileName.lastIndexOf("/");
                  int lastBackSlashIndex = fileName.lastIndexOf("\\");
                  int lastSlashIndex = lastForwardSlashIndex > lastBackSlashIndex ? lastForwardSlashIndex : lastBackSlashIndex;
                  if (lastSlashIndex != -1) {
                     fileName = fileName.substring(lastSlashIndex + 1);
                  }

                  Debug.logExtractingNativeLib(fileName, nativeLibDir);
                  if (Debug.isDeploymentEnabled()) {
                     Debug.deployment("Extracting " + fileName + " to " + nativeLibDir);
                  }

                  InputStream is = vjar.getInputStream(entry);
                  FileOutputStream os = new FileOutputStream(nativeLibDir + File.separator + fileName);
                  byte[] b = new byte[512];

                  int len;
                  while((len = is.read(b, 0, b.length)) != -1) {
                     os.write(b, 0, len);
                  }

                  is.close();
                  os.close();
               } catch (FileNotFoundException var21) {
                  exMsg = Debug.getExceptionFileNotFoundForNativeLibDir(raInfo.getDisplayName());
                  throw new DeploymentException(exMsg, var21);
               } catch (IOException var22) {
                  exMsg = Debug.getExceptionExceptionCreatingNativeLibDir(raInfo.getDisplayName(), var22.toString());
                  throw new DeploymentException(exMsg, var22);
               }
            }
         }
      } finally {
         SecurityHelperFactory.getInstance().popSubject(kernelId);
      }

   }

   public static void updateClassFinder(GenericClassLoader classLoader, RarArchive rar, List classFinderList) throws RAException {
      if (!rar.getVirtualJarFile().isDirectory()) {
         throw new Error("the VirtualJarFile should already be expanded to a directory but not:" + rar);
      } else {
         MultiClassFinder multiFinder;
         try {
            multiFinder = getClassFinder(rar, true);
         } catch (IOException var5) {
            throw new RAException("failed to create classloader for adapter " + rar, var5);
         }

         addClassFinderToClassloader(classLoader, multiFinder, classFinderList);
      }
   }

   public static MultiClassFinder getClassFinder(RarArchive rar, boolean scanManifestClasspath) throws IOException {
      MultiClassFinder finder = new MultiClassFinder();
      VirtualJarFile jarFile = rar.getVirtualJarFile();
      populateExplordedRarClassFinderWithoutManifest(jarFile, finder);
      populateNestedJarsClassFinder(jarFile, finder);
      populateRootDirectoryClassFinder(jarFile, finder);
      if (scanManifestClasspath) {
         populateOriginalRarClassFinderWithManifest(rar, finder);
      }

      return finder;
   }

   private static void populateOriginalRarClassFinderWithManifest(RarArchive rar, MultiClassFinder multiFinder) {
      ClassFinder manifestFinder = ClassPathUtil.createManifestFinder(rar.getOriginalVirtualJarFile());
      if (Debug.isClassLoadingEnabled()) {
         Debug.classloading("updateClassFinder: added manifestFinder: " + manifestFinder);
      }

      multiFinder.addFinder(manifestFinder);
   }

   private static void populateExplordedRarClassFinderWithoutManifest(VirtualJarFile vjar, MultiClassFinder multiFinder) throws IOException {
      File f = new File(vjar.getName());
      if (Debug.isClassLoadingEnabled()) {
         Debug.classloading("updateClassFinder: added entry for rar dir: " + f.getAbsolutePath());
      }

      ClassFinder raClassFinder = new IndexedDirectoryClassFinder(f);
      multiFinder.addFinder(raClassFinder);
   }

   private static void populateRootDirectoryClassFinder(VirtualJarFile vjar, MultiClassFinder multiFinder) throws IOException {
      File rootDir = vjar.getDirectory();
      if (rootDir != null) {
         File[] var3 = rootDir.listFiles();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File f = var3[var5];
            if (f.isDirectory() && f.getName().toLowerCase().endsWith(".jar")) {
               if (Debug.isClassLoadingEnabled()) {
                  Debug.classloading("populateRootDirectoryClassFinder: added root entry: " + f.getPath());
               }

               ClassFinder classpathClassFinder = new IndexedDirectoryClassFinder(f);
               multiFinder.addFinder(classpathClassFinder);
            }
         }

      }
   }

   private static void populateNestedJarsClassFinder(VirtualJarFile vjar, MultiClassFinder multiFinder) throws IOException {
      Iterator iterator = vjar.entries();

      while(iterator.hasNext()) {
         ZipEntry entry = (ZipEntry)iterator.next();
         if (entry.getName().endsWith(".jar")) {
            String jarfileName = vjar.getName() + File.separator + entry.getName();
            if (Debug.isClassLoadingEnabled()) {
               Debug.classloading("populateNestedJarsClassFinder: add nested jar entry: " + jarfileName);
            }

            File f = new File(jarfileName);
            ClassFinder finder = new JarClassFinder(f);
            multiFinder.addFinder(finder);
         }
      }

   }

   private static void addClassFinderToClassloader(GenericClassLoader classLoader, MultiClassFinder multiFinder, List classFinderList) {
      classLoader.addClassFinder(multiFinder);
      if (Debug.isClassLoadingEnabled()) {
         Debug.classloading("RA DeployerUtil: add classfinders to classloader:[" + classLoader + "]; cf:" + multiFinder);
      }

      classFinderList.add(multiFinder);
      Iterator var3 = classFinderList.iterator();

      while(var3.hasNext()) {
         ClassFinder finder = (ClassFinder)var3.next();
         if (Debug.isClassLoadingEnabled()) {
            Debug.classloading("RA DeployerUtil: dump classfinder:" + finder + ":" + finder.getClassPath());
         }
      }

   }

   protected static ConnectorModuleChangePackage enumerateChanges(RAInstanceManager raIM, RAInfo raInfo, RAInfo newRAInfo) {
      ConnectorModuleChangePackage changePkg = new ConnectorModuleChangePackage();
      changePkg.addChanges(enumerateAdminObjectChanges(raIM, raInfo, newRAInfo));
      changePkg.addChanges(enumerateConnectionPoolChanges(raIM, raInfo, newRAInfo));
      changePkg.addChange(enumerateRAPropsChanges(raIM, raInfo, newRAInfo));
      changePkg.addChanges(enumerateWLSWorkManagerChanges(raIM, raInfo, newRAInfo));
      changePkg.addChanges(enumerateSecurityContextChanges(raIM, raInfo, newRAInfo));
      return changePkg;
   }

   private static Properties getPoolParamPropertyChanges(OutboundInfo outboundInfo, OutboundInfo newOutboundInfo) {
      Properties changedProperties = new Properties();
      int initialCapacity = outboundInfo.getInitialCapacity();
      int newInitialCapacity = newOutboundInfo.getInitialCapacity();
      int maxCapacity = outboundInfo.getMaxCapacity();
      int newMaxCapacity = newOutboundInfo.getMaxCapacity();
      int capacityIncrement = outboundInfo.getCapacityIncrement();
      int newCapacityIncrement = newOutboundInfo.getCapacityIncrement();
      int shrinkFrequencySeconds = outboundInfo.getShrinkFrequencySeconds();
      int newShrinkFrequencySeconds = newOutboundInfo.getShrinkFrequencySeconds();
      int inactiveConnectionTimeoutSeconds = outboundInfo.getInactiveConnectionTimeoutSeconds();
      int newInactiveConnectionTimeoutSeconds = newOutboundInfo.getInactiveConnectionTimeoutSeconds();
      int highestNumWaiters = outboundInfo.getHighestNumWaiters();
      int newHighestNumWaiters = newOutboundInfo.getHighestNumWaiters();
      int highestNumUnavailable = outboundInfo.getHighestNumUnavailable();
      int newHighestNumUnavailable = newOutboundInfo.getHighestNumUnavailable();
      int connectionCreationRetryFrequencySeconds = outboundInfo.getConnectionCreationRetryFrequencySeconds();
      int newConnectionCreationRetryFrequencySeconds = newOutboundInfo.getConnectionCreationRetryFrequencySeconds();
      int connectionReserveTimeoutSeconds = outboundInfo.getConnectionReserveTimeoutSeconds();
      int newConnectionReserveTimeoutSeconds = newOutboundInfo.getConnectionReserveTimeoutSeconds();
      int testFrequencySeconds = outboundInfo.getTestFrequencySeconds();
      int newTestFrequencySeconds = newOutboundInfo.getTestFrequencySeconds();
      int profileHarvestFrequencySeconds = outboundInfo.getProfileHarvestFrequencySeconds();
      int newProfileHarvestFrequencySeconds = newOutboundInfo.getProfileHarvestFrequencySeconds();
      boolean shrinkingEnabled = outboundInfo.isShrinkingEnabled();
      boolean newShrinkingEnabled = newOutboundInfo.isShrinkingEnabled();
      boolean isTestConnectionsOnCreate = outboundInfo.isTestConnectionsOnCreate();
      boolean newIsTestConnectionsOnCreate = newOutboundInfo.isTestConnectionsOnCreate();
      boolean isTestConnectionsOnRelease = outboundInfo.isTestConnectionsOnRelease();
      boolean newIsTestConnectionsOnRelease = newOutboundInfo.isTestConnectionsOnRelease();
      boolean isTestConnectionsOnReserve = outboundInfo.isTestConnectionsOnReserve();
      boolean newIsTestConnectionsOnReserve = newOutboundInfo.isTestConnectionsOnReserve();
      if (initialCapacity != newInitialCapacity) {
         changedProperties.setProperty("initialCapacity", String.valueOf(newInitialCapacity));
      }

      if (maxCapacity != newMaxCapacity) {
         changedProperties.setProperty("maxCapacity", String.valueOf(newMaxCapacity));
      }

      if (capacityIncrement != newCapacityIncrement) {
         changedProperties.setProperty("capacityIncrement", String.valueOf(newCapacityIncrement));
      }

      if (shrinkFrequencySeconds != newShrinkFrequencySeconds) {
         changedProperties.setProperty("shrinkFrequencySeconds", String.valueOf(newShrinkFrequencySeconds));
      }

      if (inactiveConnectionTimeoutSeconds != newInactiveConnectionTimeoutSeconds) {
         changedProperties.setProperty("inactiveResTimeoutSeconds", String.valueOf(newInactiveConnectionTimeoutSeconds));
      }

      if (highestNumWaiters != newHighestNumWaiters) {
         changedProperties.setProperty("maxWaiters", String.valueOf(newHighestNumWaiters));
      }

      if (highestNumUnavailable != newHighestNumUnavailable) {
         changedProperties.setProperty("maxUnavl", String.valueOf(newHighestNumUnavailable));
      }

      if (connectionCreationRetryFrequencySeconds != newConnectionCreationRetryFrequencySeconds) {
         changedProperties.setProperty("resCreationRetrySeconds", String.valueOf(newConnectionCreationRetryFrequencySeconds));
      }

      if (connectionReserveTimeoutSeconds != newConnectionReserveTimeoutSeconds) {
         changedProperties.setProperty("resvTimeoutSeconds", String.valueOf(newConnectionReserveTimeoutSeconds));
      }

      if (testFrequencySeconds != newTestFrequencySeconds) {
         changedProperties.setProperty("testFrequencySeconds", String.valueOf(newTestFrequencySeconds));
      }

      if (profileHarvestFrequencySeconds != newProfileHarvestFrequencySeconds) {
         changedProperties.setProperty("harvestFreqSecsonds", String.valueOf(newProfileHarvestFrequencySeconds));
      }

      if (shrinkingEnabled != newShrinkingEnabled) {
         changedProperties.setProperty("shrinkEnabled", String.valueOf(newShrinkingEnabled));
      }

      if (isTestConnectionsOnCreate != newIsTestConnectionsOnCreate) {
         changedProperties.setProperty("testOnCreate", String.valueOf(newIsTestConnectionsOnCreate));
      }

      if (isTestConnectionsOnRelease != newIsTestConnectionsOnRelease) {
         changedProperties.setProperty("testOnRelease", String.valueOf(newIsTestConnectionsOnRelease));
      }

      if (isTestConnectionsOnReserve != newIsTestConnectionsOnReserve) {
         changedProperties.setProperty("testOnReserve", String.valueOf(newIsTestConnectionsOnReserve));
      }

      return changedProperties;
   }

   private static Properties getLoggingPropertyChanges(OutboundInfo outboundInfo, OutboundInfo newOutboundInfo) {
      Properties changedProperties = new Properties();
      int fileCount = outboundInfo.getFileCount();
      int newFileCount = newOutboundInfo.getFileCount();
      int fileSizeLimit = outboundInfo.getFileSizeLimit();
      int newFileSizeLimit = newOutboundInfo.getFileSizeLimit();
      int fileTimeSpan = outboundInfo.getFileTimeSpan();
      int newFileTimeSpan = newOutboundInfo.getFileTimeSpan();
      String logFileRotationDir = outboundInfo.getLogFileRotationDir();
      String newLogFileRotationDir = newOutboundInfo.getLogFileRotationDir();
      String logFilename = outboundInfo.getLogFilename();
      String newLogFilename = newOutboundInfo.getLogFilename();
      boolean isLoggingEnabled = outboundInfo.isLoggingEnabled();
      boolean newIsLoggingEnabled = newOutboundInfo.isLoggingEnabled();
      boolean isNumberOfFilesLimited = outboundInfo.isNumberOfFilesLimited();
      boolean newIsNumberOfFilesLimited = newOutboundInfo.isNumberOfFilesLimited();
      boolean isRotateLogOnStartup = outboundInfo.isRotateLogOnStartup();
      boolean newIsRotateLogOnStartup = newOutboundInfo.isRotateLogOnStartup();
      String rotationTime = outboundInfo.getRotationTime();
      String newRotationTime = newOutboundInfo.getRotationTime();
      String rotationType = outboundInfo.getRotationType();
      String newRotationType = newOutboundInfo.getRotationType();
      if (fileCount != newFileCount) {
         changedProperties.setProperty("FileCount", String.valueOf(newFileCount));
      }

      if (fileSizeLimit != newFileSizeLimit) {
         changedProperties.setProperty("FileSizeLimit", String.valueOf(newFileSizeLimit));
      }

      if (fileTimeSpan != newFileTimeSpan) {
         changedProperties.setProperty("FileTimeSpan", String.valueOf(newFileTimeSpan));
      }

      if (!stringsMatch(logFileRotationDir, newLogFileRotationDir)) {
         changedProperties.setProperty("LogFileRotationDir", newLogFileRotationDir);
      }

      if (!stringsMatch(logFilename, newLogFilename)) {
         changedProperties.setProperty("LogFilename", newLogFilename);
      }

      if (isLoggingEnabled != newIsLoggingEnabled) {
         changedProperties.setProperty("LoggingEnabled", String.valueOf(newIsLoggingEnabled));
      }

      if (isNumberOfFilesLimited != newIsNumberOfFilesLimited) {
         changedProperties.setProperty("NumberOfFilesLimited", String.valueOf(newIsNumberOfFilesLimited));
      }

      if (isRotateLogOnStartup != newIsRotateLogOnStartup) {
         changedProperties.setProperty("RotateLogOnStartup", String.valueOf(newIsRotateLogOnStartup));
      }

      if (!stringsMatch(rotationTime, newRotationTime)) {
         changedProperties.setProperty("RotationTime", String.valueOf(newRotationTime));
      }

      if (!stringsMatch(rotationType, newRotationType)) {
         changedProperties.setProperty("RotationType", String.valueOf(newRotationType));
      }

      return changedProperties;
   }

   private static boolean stringsMatch(String str1, String str2) {
      return str1 == null ? str2 == null : str1.equals(str2);
   }

   private static List enumerateConnectionPoolChanges(RAInstanceManager raIM, RAInfo oldRaInfo, RAInfo newRAInfo) {
      List poolChanges = new ArrayList();
      List oldOutboundList = oldRaInfo.getOutboundInfos();
      Iterator oldIter = oldOutboundList.iterator();

      OutboundInfo newOutboundInfo;
      while(oldIter.hasNext()) {
         ConnectionPoolChangePackage poolChgPkg = null;
         OutboundInfo oldOutboundInfo = (OutboundInfo)oldIter.next();
         newOutboundInfo = newRAInfo.getOutboundInfo(oldOutboundInfo.getJndiName());
         if (newOutboundInfo != null) {
            boolean isOldPoolFailed = raIM.getRAOutboundManager().isFailedPool(oldOutboundInfo.getJndiName());
            Properties changedPoolParamProperties;
            Properties changedLoggingProperties;
            if (isOldPoolFailed) {
               changedPoolParamProperties = new Properties();
               changedLoggingProperties = new Properties();
               Map changedProperties = new HashMap();
               poolChgPkg = new ConnectionPoolChangePackage(raIM, newOutboundInfo, changedPoolParamProperties, changedLoggingProperties, changedProperties, ConnectorModuleChangePackage.ChangeType.UPDATE);
            } else {
               changedPoolParamProperties = getPoolParamPropertyChanges(oldOutboundInfo, newOutboundInfo);
               changedLoggingProperties = getLoggingPropertyChanges(oldOutboundInfo, newOutboundInfo);

               Object changedProperties;
               try {
                  changedProperties = enumeratePropsChanges(oldOutboundInfo.getMCFProps(), newOutboundInfo.getMCFProps(), false);
               } catch (RAOutboundException var14) {
                  ConnectorLogger.logCannotRDetermineConnectionPoolChange(oldOutboundInfo.getJndiName(), var14);
                  changedProperties = new HashMap();
               }

               boolean foundChanges = changedPoolParamProperties.size() + changedLoggingProperties.size() + ((Map)changedProperties).size() > 0;
               if (foundChanges) {
                  poolChgPkg = new ConnectionPoolChangePackage(raIM, newOutboundInfo, changedPoolParamProperties, changedLoggingProperties, (Map)changedProperties, ConnectorModuleChangePackage.ChangeType.UPDATE);
               }
            }
         } else {
            poolChgPkg = new ConnectionPoolChangePackage(raIM, oldOutboundInfo, (Properties)null, (Properties)null, (Map)null, ConnectorModuleChangePackage.ChangeType.REMOVE);
         }

         if (poolChgPkg != null) {
            poolChanges.add(poolChgPkg);
         }
      }

      List newOutboundList = newRAInfo.getOutboundInfos();
      Iterator newPoolIter = newOutboundList.iterator();

      while(newPoolIter.hasNext()) {
         newOutboundInfo = null;
         OutboundInfo newOutboundInfo = (OutboundInfo)newPoolIter.next();
         OutboundInfo oldOutboundInfo = oldRaInfo.getOutboundInfo(newOutboundInfo.getJndiName());
         if (oldOutboundInfo == null) {
            ConnectionPoolChangePackage poolChgPkg = new ConnectionPoolChangePackage(raIM, newOutboundInfo, (Properties)null, (Properties)null, (Map)null, ConnectorModuleChangePackage.ChangeType.NEW);
            poolChanges.add(poolChgPkg);
         }
      }

      return poolChanges;
   }

   private static List enumerateAdminObjectChanges(RAInstanceManager raIM, RAInfo raInfo, RAInfo newRAInfo) {
      List adminObjectChanges = new ArrayList();
      List adminBeanList = newRAInfo.getAdminObjs();
      Iterator iter = adminBeanList.iterator();

      while(iter.hasNext()) {
         AdminObjInfo admObjInfo = (AdminObjInfo)iter.next();
         AdminObjInfo oldAdmObjInfo = raInfo.getAdminObject(admObjInfo.getJndiName());
         if (oldAdmObjInfo == null) {
            adminObjectChanges.add(new AdminObjectChangePackage(raIM, admObjInfo, ConnectorModuleChangePackage.ChangeType.NEW, (Map)null));
         } else {
            Map changedProperties = enumeratePropsChanges(oldAdmObjInfo.getConfigProps(), admObjInfo.getConfigProps(), true);
            if (!changedProperties.isEmpty()) {
               adminObjectChanges.add(new AdminObjectChangePackage(raIM, admObjInfo, ConnectorModuleChangePackage.ChangeType.UPDATE, changedProperties));
            }
         }
      }

      List oldAdminObjs = raInfo.getAdminObjs();
      Iterator var10 = oldAdminObjs.iterator();

      while(var10.hasNext()) {
         AdminObjInfo oldAdminObj = (AdminObjInfo)var10.next();
         if (newRAInfo.getAdminObject(oldAdminObj.getJndiName()) == null) {
            adminObjectChanges.add(new AdminObjectChangePackage(raIM, oldAdminObj, ConnectorModuleChangePackage.ChangeType.REMOVE, (Map)null));
         }
      }

      return adminObjectChanges;
   }

   private static RAPropsChangePackage enumerateRAPropsChanges(RAInstanceManager raIM, RAInfo raInfo, RAInfo newRAInfo) {
      RAPropsChangePackage raPropsChangePkg = null;
      Map changedProperties = enumeratePropsChanges(raInfo.getRAConfigProps(), newRAInfo.getRAConfigProps(), true);
      if (!changedProperties.isEmpty()) {
         raPropsChangePkg = new RAPropsChangePackage(raIM, changedProperties);
      }

      if (!stringsMatch(raInfo.getJndiName(), newRAInfo.getJndiName())) {
         if (raPropsChangePkg == null) {
            raPropsChangePkg = new RAPropsChangePackage(raIM, (Map)null);
         }

         raPropsChangePkg.setNewJNDIName(newRAInfo.getJndiName());
      }

      return raPropsChangePkg;
   }

   public static Map enumeratePropsChanges(Map oldProps, Map newProps, boolean onlyDyamic) {
      Map result = new HashMap();
      Iterator var4 = oldProps.entrySet().iterator();

      while(true) {
         ConfigPropInfo oldProp;
         ConfigPropInfo newProp;
         do {
            do {
               if (!var4.hasNext()) {
                  return result;
               }

               Map.Entry oldPropEntry = (Map.Entry)var4.next();
               oldProp = (ConfigPropInfo)oldPropEntry.getValue();
               newProp = (ConfigPropInfo)newProps.get(oldPropEntry.getKey());
            } while(newProp == null);
         } while(onlyDyamic && !newProp.isDynamicUpdatable());

         if (!hasEqualValue(oldProp, newProp)) {
            result.put(newProp.getName().toLowerCase(), newProp);
         }
      }
   }

   private static boolean hasEqualValue(ConfigPropInfo prop1, ConfigPropInfo prop2) {
      if (prop1.getValue() == null) {
         return prop2.getValue() == null;
      } else {
         return prop1.getValue().equals(prop2.getValue());
      }
   }

   private static List enumerateWLSWorkManagerChanges(RAInstanceManager raIM, RAInfo raInfo, RAInfo newRAInfo) {
      List wlsWMChanges = new ArrayList();
      if (raIM.getBootstrapContext() != null) {
         ConnectorWorkManagerBean newConnWM = newRAInfo.getConnectorWorkManager();
         ConnectorWorkManagerBean oldConnWM = raInfo.getConnectorWorkManager();
         if (newConnWM.getMaxConcurrentLongRunningRequests() != oldConnWM.getMaxConcurrentLongRunningRequests()) {
            wlsWMChanges.add(new ConnectorWorkManagerChangePackage(raIM, ConnectorModuleChangePackage.ChangeType.UPDATE, newConnWM.getMaxConcurrentLongRunningRequests()));
         }
      }

      return wlsWMChanges;
   }

   private static List enumerateSecurityContextChanges(RAInstanceManager raIM, RAInfo raInfo, RAInfo newRAInfo) {
      List changes = new ArrayList();
      if (raIM.getBootstrapContext() != null) {
         WorkManager wm = (WorkManager)raIM.getBootstrapContext().getWorkManager();
         SecurityContextProcessor sp = (SecurityContextProcessor)wm.getWorkContextManager().getProcessor(SecurityContext.class);
         CallbackHandlerFactoryImpl callbackHandlerFactory = (CallbackHandlerFactoryImpl)sp.getCallbackHandlerFactory();
         changes.add(new SecurityContextChangePackage(callbackHandlerFactory, newRAInfo.getSecurityIdentityInfo()));
      }

      return changes;
   }
}
