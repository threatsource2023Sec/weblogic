package weblogic.deploy.service.datatransferhandlers;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.ApplicationFileManager;
import weblogic.application.archive.utils.ArchiveUtils;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.ModuleDiscovery;
import weblogic.deploy.common.Debug;
import weblogic.deploy.common.DeploymentConstants;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.deploy.internal.DeployerRuntimeLogger;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class SourceCache implements DeploymentConstants {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String CONFIG_DIR_PREFIX;
   private String appName = null;
   private File tempDirectory;
   private File rootDirectory;
   private String sourcePath = null;
   private String planPath = null;
   private String planDir = null;
   private String altDDPath = null;
   private String altWLDDPath = null;
   private boolean isSrcArchive = false;
   private boolean isSystemResource = false;
   private boolean isStandaloneModule = false;
   private HashMap jarCache = new HashMap();
   private static HashMap sourceCacheMap;

   public SourceCache(BasicDeploymentMBean mbean, File tempDirectory) throws IOException {
      this.appName = mbean.getName();
      this.tempDirectory = new File(tempDirectory, "_WL_TEMP_APP_DOWNLOADS" + File.separator + this.appName);
      boolean tempDirExists = this.tempDirectory.exists();
      if (!tempDirExists) {
         tempDirExists = this.tempDirectory.mkdirs();
      }

      if (!tempDirExists) {
         throw new IOException("Could not create temp location for application : " + this.appName);
      } else {
         if (mbean instanceof AppDeploymentMBean) {
            AppDeploymentMBean appMBean = (AppDeploymentMBean)mbean;
            this.sourcePath = appMBean.getAbsoluteSourcePath();
            this.rootDirectory = new File(this.sourcePath);
            this.planPath = appMBean.getAbsolutePlanPath();
            this.planDir = appMBean.getAbsolutePlanDir();
            this.altDDPath = appMBean.getAltDescriptorPath();
            this.altWLDDPath = appMBean.getAltWLSDescriptorPath();
            File srcFile = new File(this.sourcePath);
            if (!srcFile.exists()) {
               throw new IOException("Invalid source path : " + srcFile.getAbsolutePath());
            }

            if (srcFile.isFile()) {
               if (ArchiveUtils.isValidArchiveName(srcFile.getName())) {
                  this.isSrcArchive = true;
               } else if (ArchiveUtils.isValidWLSModuleName(srcFile.getName())) {
                  this.isStandaloneModule = true;
               }
            }
         } else {
            this.sourcePath = CONFIG_DIR_PREFIX + ((SystemResourceMBean)mbean).getDescriptorFileName();
            this.rootDirectory = new File(DomainDir.getConfigDir());
            this.isSystemResource = true;
         }

      }
   }

   public MultiDataStream getDataLocations(List uris, boolean isPlanUpdate, BasicDeploymentMBean depMBean) throws IOException {
      return this.isSystemResource ? this.getSystemResourceDataLocations(uris) : this.getAppDataLocations(uris, isPlanUpdate, (AppDeploymentMBean)depMBean);
   }

   public MultiDataStream getAppDataLocationsForModuleIds(List moduleIds, AppDeploymentMBean appMBean) throws IOException {
      if (moduleIds != null && !moduleIds.isEmpty()) {
         if (this.isSrcArchive) {
            String msg = DeployerRuntimeLogger.logPartialRedeployOfArchiveLoggable(this.appName).getMessage();
            throw new IOException(msg);
         } else {
            String[] moduleIdsArray = new String[moduleIds.size()];
            moduleIdsArray = (String[])((String[])moduleIds.toArray(moduleIdsArray));
            File[] fileLocations = getAppFiles(moduleIdsArray, appMBean);
            if (fileLocations != null && fileLocations.length != 0) {
               MultiDataStream multiStream = DataStreamFactory.createMultiDataStream();
               List foundPlanURIs = new ArrayList();

               for(int i = 0; i < fileLocations.length; ++i) {
                  if (verifyIfFileIsIn(fileLocations[i], this.rootDirectory)) {
                     String eachURI = getFilePathRelativeTo(fileLocations[i], this.rootDirectory);
                     File eachFile = new File(this.rootDirectory, eachURI);
                     if (eachFile.isDirectory()) {
                        multiStream.addFileDataStream(eachURI, new File(this.getOrCreateJarForURI(eachURI)), true);
                     } else {
                        multiStream.addFileDataStream(eachURI, eachFile, ArchiveUtils.isValidArchiveName(eachFile.getName()));
                     }
                  } else {
                     foundPlanURIs.add(fileLocations[i]);
                  }
               }

               if (!foundPlanURIs.isEmpty()) {
                  multiStream.addFileDataStream(this.getTempDescsFilePath(), true);
               }

               return multiStream;
            } else {
               throw new IOException("Container did not find file locations for moduleIds '" + moduleIds + "'");
            }
         }
      } else {
         throw new IOException("Empty ModuleIds list");
      }
   }

   public String toString() {
      return "SourceCache(" + this.appName + ")";
   }

   public void close() {
      synchronized(this.jarCache) {
         Iterator iter = this.jarCache.values().iterator();
         if (iter != null && iter.hasNext()) {
            while(iter.hasNext()) {
               String eachLocation = (String)iter.next();
               if (eachLocation != null) {
                  File eachFile = new File(eachLocation);
                  if (eachFile.exists()) {
                     eachFile.delete();
                  }
               }
            }

            this.jarCache.clear();
         }
      }
   }

   public static void invalidateCache(BasicDeploymentMBean mbean) {
      SourceCache cache = null;
      synchronized(sourceCacheMap) {
         cache = (SourceCache)sourceCacheMap.remove(mbean.getName());
      }

      if (cache != null) {
         cache.close();
      }

   }

   public static void updateDescriptorsInCache(AppDeploymentMBean mbean) {
      SourceCache cache = null;
      synchronized(sourceCacheMap) {
         cache = (SourceCache)sourceCacheMap.get(mbean.getName());
      }

      if (cache != null) {
         cache.updateDescriptors(mbean);
      }

   }

   static SourceCache getSourceCache(BasicDeploymentMBean mbean) throws IOException {
      synchronized(sourceCacheMap) {
         SourceCache sourceCache = (SourceCache)sourceCacheMap.get(mbean.getName());
         if (sourceCache == null) {
            if (isDebugEnabled()) {
               debugSay("Creating new source cache for " + mbean);
            }

            File tmpDir = new File(DomainDir.getTempDirForServer(ManagementService.getPropertyService(kernelId).getServerName()));
            sourceCache = new SourceCache(mbean, tmpDir);
            sourceCacheMap.put(mbean.getName(), sourceCache);
         }

         return sourceCache;
      }
   }

   private MultiDataStream getAppDataLocations(List uris, boolean isPlanUpdate, AppDeploymentMBean appDeployment) throws IOException {
      boolean isFullAppUpdate = uris == null || uris.isEmpty() || this.isStandaloneModule;
      MultiDataStream multiStream = DataStreamFactory.createMultiDataStream();
      if (isFullAppUpdate) {
         multiStream.addDataStream(this.getTempSrcLocation());
         String srcDescLocation = this.getTempDescsFilePath();
         if (srcDescLocation != null) {
            multiStream.addFileDataStream(srcDescLocation, true);
         }

         return multiStream;
      } else {
         if (isDebugEnabled()) {
            debugSay(" +++ isPlanUpdate: " + isPlanUpdate);
            debugSay(" +++ uris passed : " + uris);
         }

         if (isPlanUpdate) {
            multiStream.addFileDataStream(this.getTempDescsFilePath(), true);
            return multiStream;
         } else {
            String[] fileNames = new String[uris.size()];
            fileNames = (String[])((String[])uris.toArray(fileNames));
            validateDelta(fileNames, appDeployment);
            Iterator iter = uris.iterator();

            while(iter.hasNext()) {
               String eachURI = (String)iter.next();
               File eachFile = new File(this.rootDirectory, eachURI);
               if (eachFile.isDirectory()) {
                  multiStream.addFileDataStream(eachURI, new File(this.getOrCreateJarForURI(eachURI)), true);
               } else {
                  multiStream.addFileDataStream(eachURI, eachFile, ArchiveUtils.isValidArchiveName(eachFile.getName()));
               }
            }

            return multiStream;
         }
      }
   }

   private static boolean isDebugEnabled() {
      return Debug.isServiceHttpDebugEnabled();
   }

   private static void debugSay(String msg) {
      Debug.serviceHttpDebug(" +++ " + msg);
   }

   private MultiDataStream getSystemResourceDataLocations(List uris) throws IOException {
      weblogic.utils.Debug.assertion(this.isSystemResource);
      MultiDataStream multiAppStream = DataStreamFactory.createMultiDataStream();
      File descFile = new File(this.sourcePath);
      weblogic.utils.Debug.assertion(descFile.exists());
      multiAppStream.addFileDataStream(getRootPath(this.rootDirectory, descFile) + descFile.getName(), descFile, false);
      return multiAppStream;
   }

   private String getOrCreateJarForURI(String uri) throws IOException {
      if (uri != null && uri.length() != 0) {
         File sourceFile = this.rootDirectory;
         File givenURIFile = new File(sourceFile, uri);
         String jarLocation = null;
         synchronized(this.jarCache) {
            jarLocation = (String)this.jarCache.get(uri);
            File jarFile;
            if (jarLocation != null) {
               jarFile = new File(jarLocation);
               if (jarFile.exists() && jarFile.lastModified() >= FileUtils.getLastModified(givenURIFile)) {
                  return jarLocation;
               }
            }

            this.jarCache.remove(uri);
            jarFile = File.createTempFile("wl_comp", ".jar", this.tempDirectory);
            jarFile.deleteOnExit();
            JarFileUtils.createJarFileFromDirectory(jarFile, givenURIFile, false);
            jarLocation = jarFile.getAbsolutePath();
            this.jarCache.put(uri, jarLocation);
            return jarLocation;
         }
      } else {
         throw new AssertionError("URI cannot be null");
      }
   }

   private FileDataStream getTempSrcLocation() throws IOException {
      File originalSrcFile = new File(this.sourcePath);
      if (this.isSrcArchive) {
         if (isDebugEnabled()) {
            debugSay("originalSrcFile : " + originalSrcFile.getAbsolutePath());
         }

         return DataStreamFactory.createFileDataStream(originalSrcFile.getName(), originalSrcFile, true);
      } else if (!originalSrcFile.isDirectory()) {
         if (isDebugEnabled()) {
            debugSay("originalSrcFile : " + originalSrcFile.getAbsolutePath());
         }

         return DataStreamFactory.createFileDataStream("wl_app_src.jar", originalSrcFile, false);
      } else {
         synchronized(this.jarCache) {
            String tempSrcFileName = (String)this.jarCache.get("wl_app_src.jar");
            File tempFile;
            if (tempSrcFileName != null && tempSrcFileName.length() != 0) {
               tempFile = new File(tempSrcFileName);
               if (tempFile.exists()) {
                  long tempTimestamp = tempFile.lastModified();
                  long origTimestamp = FileUtils.getLastModified(originalSrcFile);
                  if (tempTimestamp >= origTimestamp) {
                     if (isDebugEnabled()) {
                        debugSay("srcPath '" + this.sourcePath + "' not modified. returning : " + tempSrcFileName);
                     }

                     return DataStreamFactory.createFileDataStream(tempFile, true);
                  }
               }
            }

            if (isDebugEnabled()) {
               debugSay("srcPath '" + this.sourcePath + "' modified. re-constructing temp jar");
            }

            tempFile = new File(this.tempDirectory, "wl_app_src.jar");
            JarFileUtils.createJarFileFromDirectory(tempFile, originalSrcFile, false);
            tempFile.deleteOnExit();
            tempSrcFileName = tempFile.getAbsolutePath();
            this.jarCache.put("wl_app_src.jar", tempSrcFileName);
            return DataStreamFactory.createFileDataStream(tempFile, true);
         }
      }
   }

   private String getTempDescsFilePath() throws IOException {
      if (this.planDir == null && this.planPath == null && this.altDDPath == null && this.altWLDDPath == null) {
         return null;
      } else {
         synchronized(this.jarCache) {
            String descFileName = (String)this.jarCache.get("wl_app_desc.jar");
            if (descFileName != null && descFileName.length() != 0 && !this.areDescsModified(descFileName)) {
               if (isDebugEnabled()) {
                  debugSay("descriptors not modified. returning : " + descFileName);
               }

               return descFileName;
            } else {
               if (isDebugEnabled()) {
                  debugSay("descriptors modified. Re-constructing temp jar for descs");
               }

               File tempDescLocation = FileUtils.createTempDir("DESC_DIR", this.tempDirectory);
               File tempPlanDir = new File(tempDescLocation, "plan");
               if (this.planDir != null && this.planDir.length() != 0) {
                  FileUtils.copyPreserveTimestampsPreservePermissions(new File(this.planDir), tempPlanDir);
               }

               if (this.planPath != null && this.planPath.length() != 0) {
                  boolean tempPlanDirExists = tempPlanDir.exists();
                  if (!tempPlanDirExists) {
                     tempPlanDirExists = tempPlanDir.mkdirs();
                  }

                  if (!tempPlanDirExists) {
                     throw new IOException("Could not create temp location for planDir");
                  }

                  FileUtils.copyPreserveTimestampsPreservePermissions(new File(this.planPath), tempPlanDir);
               }

               if (this.altDDPath != null && this.altDDPath.length() != 0) {
                  FileUtils.copyPreserveTimestampsPreservePermissions(new File(this.altDDPath), tempDescLocation);
               }

               if (this.altWLDDPath != null && this.altWLDDPath.length() != 0) {
                  FileUtils.copyPreserveTimestampsPreservePermissions(new File(this.altWLDDPath), tempDescLocation);
               }

               File[] descFiles = tempDescLocation.listFiles();
               if (descFiles != null && descFiles.length != 0) {
                  File descJarLocation = new File(this.tempDirectory, "wl_app_desc.jar");
                  JarFileUtils.createJarFileFromDirectory(descJarLocation, tempDescLocation, false);
                  descJarLocation.deleteOnExit();
                  FileUtils.remove(tempDescLocation);
                  descFileName = descJarLocation.getAbsolutePath();
                  this.jarCache.put("wl_app_desc.jar", descFileName);
                  return descFileName;
               } else {
                  return null;
               }
            }
         }
      }
   }

   private boolean areFilesPartOfSrc(List urisOrFiles) throws IOException {
      VirtualJarFile vjf = null;

      boolean var6;
      try {
         vjf = VirtualJarFactory.createVirtualJar(new File(this.sourcePath));
         Iterator fileIter = urisOrFiles.iterator();

         Iterator entries;
         do {
            if (!fileIter.hasNext()) {
               return false;
            }

            String eachUriOrFile = (String)fileIter.next();
            entries = vjf.getEntries(eachUriOrFile);
         } while(!entries.hasNext());

         var6 = true;
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var14) {
            }
         }

      }

      return var6;
   }

   private void updateDescriptors(AppDeploymentMBean appMBean) {
      weblogic.utils.Debug.assertion(!this.isSystemResource);
      this.planPath = appMBean.getAbsolutePlanPath();
      this.planDir = appMBean.getAbsolutePlanDir();
      this.altDDPath = appMBean.getAltDescriptorPath();
      this.altWLDDPath = appMBean.getAltWLSDescriptorPath();
      synchronized(this.jarCache) {
         String descFileName = (String)this.jarCache.remove("wl_app_desc.jar");
         if (descFileName != null) {
            File descFile = new File(descFileName);
            if (descFile.exists()) {
               descFile.delete();
            }
         }

      }
   }

   private boolean areDescsModified(String givenTempLocation) {
      if (this.planDir == null && this.planPath == null && this.altDDPath == null && this.altWLDDPath == null) {
         return false;
      } else if (givenTempLocation == null) {
         return true;
      } else {
         File givenTempFile = new File(givenTempLocation);
         if (!givenTempFile.exists()) {
            return true;
         } else {
            long givenFileLastTimestamp = FileUtils.getLastModified(givenTempFile);
            long descsLastTimestamp = this.getLastModifiedForDescs();
            return descsLastTimestamp > givenFileLastTimestamp;
         }
      }
   }

   private long getLastModifiedForDescs() {
      long[] allTimestamps = new long[]{getLastModifiedForPath(this.planDir), getLastModifiedForPath(this.planPath), getLastModifiedForPath(this.altDDPath), getLastModifiedForPath(this.altWLDDPath)};
      long greatestLastModified = 0L;

      for(int i = 0; i < allTimestamps.length; ++i) {
         long eachTimestamp = allTimestamps[i];
         if (eachTimestamp > greatestLastModified) {
            greatestLastModified = eachTimestamp;
         }
      }

      return greatestLastModified;
   }

   private static long getLastModifiedForPath(String givenPath) {
      long lastModified = 0L;
      if (givenPath != null) {
         File givenFile = new File(givenPath);
         if (givenFile.exists()) {
            lastModified = FileUtils.getLastModified(givenFile);
         }
      }

      return lastModified;
   }

   private static String getRootPath(File baseFile, File givenURIFile) throws IOException {
      String baseLocation = baseFile.getCanonicalPath();
      if (isDebugEnabled()) {
         debugSay(" +++ baseLocation : " + baseLocation);
      }

      String givenURI = givenURIFile.getCanonicalPath();
      if (isDebugEnabled()) {
         debugSay(" +++ givenURI : " + givenURI);
      }

      int baseLocationIndex = givenURI.indexOf(baseLocation);
      if (baseLocationIndex == -1) {
         throw new AssertionError("uri '" + givenURI + "' is not sub dir of'" + baseLocation + "'");
      } else {
         String rootPath = givenURI.substring(baseLocation.length() + 1, givenURI.indexOf(givenURIFile.getName()));
         if (isDebugEnabled()) {
            debugSay(" +++ rootPath : " + rootPath);
         }

         return rootPath;
      }
   }

   private static String getFilePathRelativeTo(File sourceFile, File givenRootDir) throws IOException {
      if (sourceFile != null && sourceFile.exists()) {
         if (givenRootDir != null && givenRootDir.exists()) {
            String sourcePath = sourceFile.getCanonicalPath();
            String canonicalRootDir = givenRootDir.getCanonicalPath();
            if (!sourcePath.startsWith(canonicalRootDir)) {
               throw new IOException("SourcePath '" + sourcePath + "' does not start with '" + canonicalRootDir + "'");
            } else {
               String relativePath = sourcePath.substring(canonicalRootDir.length() + 1);
               return relativePath;
            }
         } else {
            throw new IOException("Root directory is null or doesnot exist");
         }
      } else {
         throw new IOException("Source is null or doesnot exist");
      }
   }

   private static boolean verifyIfFileIsIn(File sourceFile, File givenRootDir) throws IOException {
      if (sourceFile != null && sourceFile.exists()) {
         if (givenRootDir != null && givenRootDir.exists()) {
            if (!givenRootDir.isDirectory()) {
               throw new IOException("Root File is not a directory");
            } else {
               try {
                  File rootDir = givenRootDir.getCanonicalFile();

                  File parentFile;
                  for(File currentFile = sourceFile; (parentFile = currentFile.getParentFile()) != null; currentFile = currentFile.getParentFile()) {
                     parentFile = parentFile.getCanonicalFile();
                     if (parentFile.equals(rootDir)) {
                        return true;
                     }
                  }

                  return false;
               } catch (IOException var5) {
                  return false;
               }
            }
         } else {
            throw new IOException("Root directory is null or doesnot exist");
         }
      } else {
         throw new IOException("Source is null or doesnot exist");
      }
   }

   public static File[] validateDelta(String[] files, AppDeploymentMBean appDepMBean) throws IOException {
      if (files == null) {
         return null;
      } else {
         File app = new File(appDepMBean.getAbsoluteSourcePath());
         if (!app.isDirectory()) {
            Loggable l = DeployerRuntimeLogger.logPartialRedeployOfArchiveLoggable(appDepMBean.getName());
            throw new IOException(l.getMessage());
         } else {
            List list = new ArrayList(files.length);

            for(int i = 0; i < files.length; ++i) {
               File f = new File(app, files[i]);
               if (!f.exists()) {
                  Loggable l = DeployerRuntimeLogger.invalidDeltaLoggable(files[i], appDepMBean.getApplicationName());
                  throw new IOException(l.getMessage());
               }

               list.add(f);
            }

            return (File[])((File[])list.toArray(new File[list.size()]));
         }
      }
   }

   private static File[] getAppFiles(String[] moduleIds, AppDeploymentMBean appDepMBean) throws IOException {
      String appName = appDepMBean.getApplicationName();
      File app = new File(appDepMBean.getAbsoluteSourcePath());
      if (!app.isDirectory()) {
         String msg = DeployerRuntimeLogger.logPartialRedeployOfArchiveLoggable(appName).getMessage();
         throw new IOException(msg);
      } else if (!EarUtils.isEar(app)) {
         return new File[]{app};
      } else {
         List appFiles = new ArrayList();
         Set ids = new HashSet(Arrays.asList(moduleIds));
         VirtualJarFile vjf = null;

         try {
            ApplicationFileManager appFileManager = ApplicationFileManager.newInstance(app);
            vjf = appFileManager.getVirtualJarFile();
            String planDirPath = appDepMBean.getAbsolutePlanDir();
            File absPlanDir = planDirPath == null ? null : new File(planDirPath);
            Map descriptors = addAppDescriptors(appDepMBean, appFiles, vjf);
            File altDD = (File)descriptors.get("META-INF/application.xml");
            File wlAltDD = (File)descriptors.get("META-INF/weblogic-application.xml");
            File altWLExt = (File)descriptors.get("META-INF/weblogic-extension.xml");
            ApplicationDescriptor desc = new ApplicationDescriptor(altDD, wlAltDD, altWLExt, absPlanDir, getDeploymentPlanDescriptor(appDepMBean.getAbsolutePlanPath()), app.getName());
            ApplicationBean appDD = desc.getApplicationDescriptor();
            if (appDD == null && vjf != null) {
               appDD = ModuleDiscovery.discoverModules(vjf);
            }

            addAppURIs(appDD, ids, appFiles, vjf);
            if (!ids.isEmpty()) {
               WeblogicApplicationBean wlapp = desc.getWeblogicApplicationDescriptor();
               addWlAppURIs(wlapp, ids, appFiles, vjf);
            }

            if (!ids.isEmpty()) {
               StringBuffer sb = new StringBuffer();
               Iterator i = ids.iterator();

               while(i.hasNext()) {
                  sb.append(i.next());
                  if (i.hasNext()) {
                     sb.append(", ");
                  }
               }

               throw new IOException(J2EELogger.logUrisDidntMatchModulesLoggable(sb.toString()).getMessage());
            }
         } catch (XMLStreamException var18) {
            IOException exception = new IOException(var18.getMessage());
            exception.initCause(var18);
            throw exception;
         }

         return (File[])((File[])appFiles.toArray(new File[appFiles.size()]));
      }
   }

   private static DeploymentPlanBean getDeploymentPlanDescriptor(String path) {
      if (path == null) {
         return null;
      } else {
         File pf = new File(path);
         DeploymentPlanBean plan = null;

         try {
            DeploymentPlanDescriptorLoader dpd = new DeploymentPlanDescriptorLoader(pf);
            plan = dpd.getDeploymentPlanBean();
            return plan;
         } catch (XMLStreamException var4) {
            throw new IllegalArgumentException(var4.getMessage(), var4);
         } catch (IOException var5) {
            throw new IllegalArgumentException(var5.getMessage(), var5);
         } catch (ClassCastException var6) {
            throw new IllegalArgumentException(var6.getMessage(), var6);
         }
      }
   }

   private static Map addAppDescriptors(AppDeploymentMBean appDepMBean, List appFiles, VirtualJarFile vjf) {
      String[] uris = new String[]{"META-INF/application.xml", "META-INF/weblogic-application.xml", "META-INF/weblogic-extension.xml"};
      Map descriptors = new HashMap(uris.length);

      for(int i = 0; i < uris.length; ++i) {
         File desc = getFile(uris[i], vjf);
         if (desc != null) {
            descriptors.put(uris[i], desc);
         }
      }

      File absPlanDir = appDepMBean.getAbsolutePlanDir() == null ? null : new File(appDepMBean.getAbsolutePlanDir());

      for(int i = 0; i < uris.length; ++i) {
         File planDD = getExternalPlanDescriptorFile(getDeploymentPlanDescriptor(appDepMBean.getAbsolutePlanPath()), absPlanDir, appDepMBean.getApplicationName(), uris[i]);
         if (planDD != null && planDD.exists()) {
            descriptors.put(uris[i], planDD);
         }
      }

      String altdd = appDepMBean.getAltDescriptorPath();
      if (altdd != null && altdd.trim().length() > 0) {
         descriptors.put("META-INF/application.xml", new File(altdd));
      }

      String wlAltdd = appDepMBean.getAltWLSDescriptorPath();
      if (wlAltdd != null && wlAltdd.trim().length() > 0) {
         descriptors.put("META-INF/weblogic-application.xml", new File(wlAltdd));
      }

      Iterator i = descriptors.values().iterator();

      while(i.hasNext()) {
         File f = (File)i.next();
         if (f.exists()) {
            appFiles.add(f);
         }
      }

      return descriptors;
   }

   private static File getFile(String uri, VirtualJarFile vjf) {
      File[] roots = vjf.getRootFiles();

      for(int i = 0; i < roots.length; ++i) {
         File ret = new File(roots[i], uri);
         if (ret.exists()) {
            return ret;
         }
      }

      return null;
   }

   private static void addAppURIs(ApplicationBean appDD, Set ids, List appFiles, VirtualJarFile vjf) {
      if (appDD != null) {
         ModuleBean[] modules = appDD.getModules();

         for(int i = 0; i < modules.length; ++i) {
            String uri = null;
            String name = null;
            File f;
            if (modules[i].getWeb() != null) {
               uri = modules[i].getWeb().getWebUri();
               name = EarUtils.fixAppContextRoot(modules[i].getWeb().getContextRoot());
               if (name == null || "".equals(name) || "/".equals(name)) {
                  name = uri;
               }

               if (ids.contains(name)) {
                  f = getFile(uri, vjf);
                  if (f != null) {
                     appFiles.add(f);
                  }

                  ids.remove(name);
               }
            } else {
               if (modules[i].getEjb() != null) {
                  uri = modules[i].getEjb();
               } else if (modules[i].getConnector() != null) {
                  uri = modules[i].getConnector();
               } else if (modules[i].getJava() != null) {
                  uri = modules[i].getJava();
               }

               if (uri != null && ids.contains(uri)) {
                  f = getFile(uri, vjf);
                  if (f != null) {
                     appFiles.add(f);
                  }

                  ids.remove(uri);
               }
            }

            if (ids.isEmpty()) {
               break;
            }
         }

      }
   }

   private static void addWlAppURIs(WeblogicApplicationBean wlapp, Set ids, List appFiles, VirtualJarFile vjf) {
      if (wlapp != null) {
         WeblogicModuleBean[] mods = wlapp.getModules();

         for(int i = 0; i < mods.length; ++i) {
            File f;
            if ("JDBC".equals(mods[i].getType())) {
               if (ids.contains(mods[i].getName())) {
                  ids.remove(mods[i].getName());
                  f = getFile(mods[i].getPath(), vjf);
                  if (f != null) {
                     appFiles.add(f);
                  }
               }
            } else if (ids.contains(mods[i].getPath())) {
               ids.remove(mods[i].getPath());
               f = getFile(mods[i].getPath(), vjf);
               if (f != null) {
                  appFiles.add(f);
               }
            }
         }

      }
   }

   private static File getExternalPlanDescriptorFile(DeploymentPlanBean planBean, File configDir, String moduleName, String descriptorUri) {
      if (planBean != null && configDir != null) {
         ModuleDescriptorBean md = planBean.findModuleDescriptor(moduleName, descriptorUri);
         if (md != null && md.isExternal()) {
            File config;
            if (planBean.rootModule(moduleName)) {
               config = configDir;
            } else {
               config = new File(configDir, moduleName);
            }

            return new File(config, md.getUri());
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   static {
      CONFIG_DIR_PREFIX = DomainDir.getRootDir() + File.separator + "config" + File.separator;
      sourceCacheMap = new HashMap();
   }
}
