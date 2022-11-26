package weblogic.ejb.embeddable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.metadata.EjbJarLoader;
import weblogic.j2ee.J2EEUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.jndi.Environment;
import weblogic.management.DomainDir;
import weblogic.server.embed.EmbeddedServer;
import weblogic.server.embed.EmbeddedServerException;
import weblogic.server.embed.EmbeddedServerFactory;
import weblogic.server.embed.EmbeddedServer.State;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.FileSource;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.ZipSource;

public class EJBContainerImpl extends EJBContainer {
   public static final String DEFAULT_APPLICATION_NAME = "EmbeddableEJBApplicationName";
   private static final DebugLogger debugLogger;
   private static final String OUTPUT_ENCODING = "UTF-8";
   private static final String BUILD_DIR_NAME = "build";
   private static final String SOURCE_DIR_NAME = "source";
   private EmbeddedServer embeddedServer;
   private Collection ejbModules;
   private String applicationName;

   protected EJBContainerImpl() {
      this.applicationName = "EmbeddableEJBApplicationName";
   }

   public EJBContainerImpl(Map properties) {
      this.setApplicationName(properties);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Embedded EJB application: " + this.applicationName);
      }

      this.embeddedServer = this.initializeServer();

      try {
         this.ejbModules = this.getEJBModules(properties);
      } catch (IOException | XMLStreamException var3) {
         throw new EJBException("Error constructing EJB Container.", var3);
      }

      this.deployEJBModules();
   }

   private EmbeddedServer initializeServer() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Starting embedded server...");
      }

      EmbeddedServer server = this.getEmbeddedServer();

      try {
         EmbeddedServer.State origState = server.getState();
         server.start();
         if (origState != State.SUSPENDED) {
            server.cleanupOnExit();
         }
      } catch (EmbeddedServerException var3) {
         throw new EJBException("Error instantiating embedded server", var3);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Successfully started embedded server");
      }

      return server;
   }

   private File getDeployable() throws IOException, XMLStreamException {
      return this.ejbModules.size() == 1 ? (File)this.ejbModules.iterator().next() : this.buildApp();
   }

   private void deployEJBModules() {
      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Deploying embedded EJB application: " + this.applicationName);
         }

         this.embeddedServer.getDeployer().deployApp(this.applicationName, this.getDeployable());
      } catch (IOException | XMLStreamException | EmbeddedServerException var2) {
         throw new EJBException("Error deploying EJB modules. Exception: " + var2.getMessage(), var2);
      }
   }

   protected File buildApp() throws IOException, XMLStreamException {
      ApplicationBean applicationBean = this.getApplicationBean();
      applicationBean.addDisplayName(this.applicationName);
      WeblogicApplicationBean wlApplicationBean = this.getWeblogicApplicationBean();
      File parentDir = this.createEmbeddableApplicationDirs();
      File buildDir = new File(parentDir, "build");
      File sourceDir = new File(parentDir, "source");
      File propertiesFile = new File(buildDir, ".beabuild.txt");
      propertiesFile.deleteOnExit();
      BufferedWriter writer = new BufferedWriter(new FileWriter(propertiesFile));

      try {
         writer.write("bea.srcdir=" + this.fixFilePath(sourceDir.getAbsolutePath()));
         writer.newLine();
         Iterator var8 = this.ejbModules.iterator();

         File wlApplicationXmlFile;
         while(var8.hasNext()) {
            wlApplicationXmlFile = (File)var8.next();
            String ejbModuleName = this.getEJBModuleName(wlApplicationXmlFile);
            applicationBean.createModule().setEjb(ejbModuleName);
            writer.write(this.fixFilePath(wlApplicationXmlFile.getAbsolutePath()) + "=" + ejbModuleName);
            writer.newLine();
         }

         File applicationXmlFile = new File(buildDir, "META-INF/application.xml");
         applicationXmlFile.deleteOnExit();
         wlApplicationXmlFile = new File(applicationXmlFile.getParentFile(), "weblogic-application.xml");
         wlApplicationXmlFile.deleteOnExit();
         this.writeBean(applicationXmlFile, (DescriptorBean)applicationBean);
         this.writeBean(wlApplicationXmlFile, (DescriptorBean)wlApplicationBean);
         return buildDir;
      } finally {
         try {
            writer.close();
         } catch (Exception var16) {
         }

      }
   }

   private String fixFilePath(String filePath) {
      String buf = filePath.replaceAll("\\\\", "\\\\\\\\");
      if (buf.indexOf(":") == 1 && buf.length() > 1) {
         buf = buf.substring(0, 1) + "\\:" + buf.substring(2);
      }

      return buf;
   }

   protected ApplicationBean getApplicationBean() {
      EditableDescriptorManager edm = new EditableDescriptorManager();
      return (ApplicationBean)edm.createDescriptorRoot(ApplicationBean.class).getRootBean();
   }

   protected WeblogicApplicationBean getWeblogicApplicationBean() {
      EditableDescriptorManager edm = new EditableDescriptorManager();
      return (WeblogicApplicationBean)edm.createDescriptorRoot(WeblogicApplicationBean.class).getRootBean();
   }

   protected File createEmbeddableApplicationDirs() throws IOException {
      String tmpDir = DomainDir.getTempDir();
      File parentDir = new File(tmpDir, "embeddable-ejb");
      parentDir.mkdirs();
      File ejbAppDir = FileUtils.createTempDir("ejb-app", parentDir);
      ejbAppDir.deleteOnExit();
      File sourceDir = new File(ejbAppDir, "source");
      sourceDir.mkdirs();
      sourceDir.deleteOnExit();
      File buildDir = new File(ejbAppDir, "build");
      buildDir.mkdirs();
      buildDir.deleteOnExit();
      return ejbAppDir;
   }

   protected void writeBean(File file, DescriptorBean bean) throws IOException {
      file.getParentFile().mkdirs();
      FileOutputStream fos = new FileOutputStream(file);

      try {
         (new EditableDescriptorManager()).writeDescriptorAsXML(bean.getDescriptor(), fos, "UTF-8");
         fos.flush();
      } finally {
         fos.close();
      }

   }

   public void close() {
      try {
         this.embeddedServer.getDeployer().undeployApp(this.applicationName);
         this.embeddedServer.suspend();
      } catch (EmbeddedServerException var2) {
         throw new EJBException("Error closing EJB modules for application '" + this.applicationName + "'.", var2);
      }
   }

   public Context getContext() {
      try {
         return (new Environment()).getInitialContext();
      } catch (NamingException var2) {
         throw new EJBException("Error obtaining InitialContext", var2);
      }
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   protected void setApplicationName(Map properties) {
      String appName = null;
      if (properties != null) {
         appName = (String)properties.get("javax.ejb.embeddable.appName");
      }

      if (appName == null) {
         appName = "EmbeddableEJBApplicationName";
      }

      this.applicationName = appName;
   }

   private Collection getEJBModules(Map properties) throws XMLStreamException, IOException {
      if (properties != null) {
         Object modules = properties.get("javax.ejb.embeddable.modules");
         if (modules != null) {
            if (modules instanceof File) {
               return this.createFileSet((File)modules);
            }

            if (modules instanceof File[]) {
               return this.createFileSet((File[])((File[])modules));
            }

            if (modules instanceof String) {
               return this.getEJBModuleRoots((String)modules);
            }

            if (modules instanceof String[]) {
               return this.getEJBModuleRoots((String[])((String[])modules));
            }
         }
      }

      return this.getAllEJBModuleRoots();
   }

   private Set createFileSet(File... files) throws XMLStreamException, IOException {
      Map modules = new HashMap();
      Set fileSet = new HashSet();
      File[] var4 = files;
      int var5 = files.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File oneFile = var4[var6];
         String moduleName = this.getEJBModuleName(oneFile);
         if (modules.get(moduleName) != null) {
            throw new EJBException("The ejb module name '" + moduleName + "' exists for multiple ejb modules.");
         }

         modules.put(moduleName, oneFile);
      }

      Collections.addAll(fileSet, files);
      return fileSet;
   }

   protected Collection getEJBModuleRoots(String... moduleNames) throws XMLStreamException, IOException {
      Set moduleNameSet = new HashSet();
      Collections.addAll(moduleNameSet, moduleNames);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Searching the classpath for Modules with the following names: " + moduleNameSet);
      }

      Set moduleRoots = new HashSet();
      Iterator it = getEJBModuleRootsFromClasspath();

      while(it.hasNext()) {
         File ejbJar = (File)it.next();
         if (moduleNameSet.remove(this.getEJBModuleName(ejbJar))) {
            moduleRoots.add(ejbJar);
            if (moduleNameSet.isEmpty()) {
               return moduleRoots;
            }
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("The following EJB module will be ignored since it wasn't in the list of modules to deploy or another module with the same name was already found: " + ejbJar.getAbsolutePath());
         }
      }

      if (!moduleNameSet.isEmpty()) {
         String[] moduleNamesArray = new String[moduleNameSet.size()];
         moduleNameSet.toArray(moduleNamesArray);
         throw new EJBException("The following EJB Modules were not discovered on the classpath:" + Arrays.toString(moduleNamesArray));
      } else {
         return moduleRoots;
      }
   }

   protected Collection getAllEJBModuleRoots() throws XMLStreamException, IOException {
      Map foundModules = new HashMap();
      Iterator it = getEJBModuleRootsFromClasspath();

      while(it.hasNext()) {
         File ejbJar = (File)it.next();
         String moduleName = this.getEJBModuleName(ejbJar);
         if (!foundModules.containsKey(moduleName)) {
            foundModules.put(moduleName, ejbJar);
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("The following EJB module will be ignored since another module with the same name was already found: " + ejbJar.getAbsolutePath());
         }
      }

      if (foundModules.isEmpty()) {
         throw new EJBException("No EJB Modules were discovered on the classpath.");
      } else {
         return foundModules.values();
      }
   }

   private static Iterator getEJBModuleRootsFromClasspath() {
      return new Iterator() {
         String[] pathElements;
         int currentPathElement;
         File currentEJBModule;

         {
            this.pathElements = StringUtils.splitCompletely(System.getProperty("java.class.path"), File.pathSeparator, false);
            this.currentPathElement = 0;
            this.currentEJBModule = null;
         }

         public boolean hasNext() {
            while(this.currentPathElement < this.pathElements.length) {
               if (this.currentEJBModule != null) {
                  return true;
               }

               File potentialEjbJar = new File(this.pathElements[this.currentPathElement++]);
               if (potentialEjbJar.exists()) {
                  try {
                     if (J2EEUtils.isEJB(potentialEjbJar)) {
                        this.currentEJBModule = potentialEjbJar;
                        if (EJBContainerImpl.debugLogger.isDebugEnabled()) {
                           EJBContainerImpl.debugLogger.debug("Found EJB in classpath: " + this.currentEJBModule.getAbsolutePath());
                        }

                        return true;
                     }
                  } catch (IOException var3) {
                     if (EJBContainerImpl.debugLogger.isDebugEnabled()) {
                        EJBContainerImpl.debugLogger.debug("IOException processing classpath entry " + potentialEjbJar.getAbsolutePath() + ".  Exception: " + var3);
                     }
                  }
               }
            }

            return false;
         }

         public File next() {
            if (this.currentEJBModule == null && !this.hasNext()) {
               throw new NoSuchElementException("No more ejb-jars to process");
            } else {
               File val = this.currentEJBModule;
               this.currentEJBModule = null;
               return val;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
         }
      };
   }

   private File getEjbJarXml(File dir) {
      File[] var2 = dir.listFiles();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File oneDirFile = var2[var4];
         if (oneDirFile.isDirectory() && oneDirFile.getName().equals("META-INF")) {
            File[] var6 = oneDirFile.listFiles();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               File oneMetaInfFile = var6[var8];
               if (oneMetaInfFile.getName().equals("ejb-jar.xml")) {
                  return oneMetaInfFile;
               }
            }

            return null;
         }
      }

      return null;
   }

   private String getModuleNameFromEjbJarXml(File ejbJar, File ejbJarXmlFile) throws XMLStreamException, IOException {
      String ejbDescUri = ejbJarXmlFile.getAbsolutePath();
      return this.getModuleName(new FileSource(ejbJar.getCanonicalPath(), ejbJarXmlFile), ejbDescUri);
   }

   private String getModuleNameFromEjbJarXml(ZipFile zipFile, ZipEntry zipEntry) throws XMLStreamException, IOException {
      return this.getModuleName(new ZipSource(zipFile, zipEntry), (String)null);
   }

   private String getModuleName(Source source, String descriptorUri) throws XMLStreamException, IOException {
      EditableDescriptorManager edm = new EditableDescriptorManager();
      EjbJarLoader ejbLoader = new EjbJarLoader(edm, descriptorUri, source);
      EjbJarBean std = (EjbJarBean)ejbLoader.loadEditableDescriptorBean();
      return std == null ? null : std.getModuleName();
   }

   protected String getEJBModuleName(File ejbJar) throws XMLStreamException, IOException {
      if (ejbJar.isDirectory()) {
         File ejbJarXmlFile = this.getEjbJarXml(ejbJar);
         if (ejbJarXmlFile == null) {
            return ejbJar.getName();
         } else {
            String moduleName = this.getModuleNameFromEjbJarXml(ejbJar, ejbJarXmlFile);
            if (moduleName == null || moduleName.trim().length() == 0) {
               moduleName = ejbJar.getName();
            }

            return moduleName;
         }
      } else {
         String jarName = ejbJar.getName();
         if (jarName.endsWith(".jar")) {
            JarFile jarFile = this.getJarFile(ejbJar);
            JarEntry jarEntry = jarFile.getJarEntry("META-INF/ejb-jar.xml");
            if (jarEntry == null) {
               return jarName.substring(0, jarName.length() - 4);
            } else {
               String moduleName = this.getModuleNameFromEjbJarXml((ZipFile)jarFile, (ZipEntry)jarEntry);
               if (moduleName == null || moduleName.trim().length() == 0) {
                  moduleName = jarName.substring(0, jarName.length() - 4);
               }

               return moduleName;
            }
         } else {
            throw new EJBException("Could not determine the module name for " + ejbJar.getAbsolutePath());
         }
      }
   }

   protected JarFile getJarFile(File file) throws IOException {
      return new JarFile(file);
   }

   protected EmbeddedServer getEmbeddedServer() {
      return EmbeddedServerFactory.getEmbeddedServer();
   }

   public static void main(String[] args) throws Exception {
      Iterator it = getEJBModuleRootsFromClasspath();

      while(it.hasNext()) {
         System.out.println("Found EJB: " + ((File)it.next()).getAbsolutePath());
      }

      EJBContainerImpl container = new EJBContainerImpl((Map)null);
      System.out.println("Created container");
      Context ic = container.getContext();
      System.out.println("got context" + ic);
      container.close();
      System.out.println("closed container");
   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }
}
