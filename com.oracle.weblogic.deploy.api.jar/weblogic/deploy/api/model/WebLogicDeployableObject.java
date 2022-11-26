package weblogic.deploy.api.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.internal.Closable;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ClassLoaderControl;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.internal.utils.LibrarySpec;
import weblogic.deploy.api.shared.ModuleTypeManager;
import weblogic.deploy.api.shared.ModuleTypeManagerFactory;
import weblogic.deploy.api.shared.WebLogicModuleTypeUtil;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.FileUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.compiler.Tool;
import weblogic.utils.jars.VirtualJarFile;

@Service
public class WebLogicDeployableObject implements DeployableObject, Closable, WebLogicDeployableObjectInterface {
   private static final boolean debug = Debug.isDebug("model");
   protected ModuleType moduleType;
   protected File moduleArchive;
   protected WebLogicDeployableObject parent = null;
   protected boolean standalone = true;
   protected List subModules = new ArrayList();
   protected String uri = null;
   protected Map ddMap = new HashMap();
   protected InstallDir installDir = null;
   protected boolean haveAppRoot = false;
   private VirtualJarFile vjf = null;
   protected GenericClassLoader gcl = null;
   protected DescriptorBean beanTree = null;
   protected DDRootFields ddRoot;
   protected boolean lazy = false;
   protected ClassLoaderControl clf = null;
   protected File plan;
   protected File plandir;
   protected DeploymentPlanBean planBean;
   protected LibrarySpec[] libraries = null;
   protected boolean deleteOnClose = false;
   protected ClassFinder resourceFinder;
   private String partitionName;
   protected String contextRoot;

   protected WebLogicDeployableObject(File module, ModuleType moduleType, WebLogicDeployableObject parent, String uri, String altdd, File installDir, File plan, File plandir) throws IOException {
      ConfigHelper.checkParam("File", module);
      this.setPlan(plan);
      this.setPlanDir(plandir);
      this.setParent(parent);
      this.setModuleArchive(module);
      this.setModuleType(moduleType);
      this.setUri(uri);
      this.setInstallDir(installDir);
   }

   public WebLogicDeployableObject(File module, ModuleType moduleType, WebLogicDeployableObject parent, String uri, String altdd, File installDir, File plan, File plandir, LibrarySpec[] libraries, boolean lazy) throws InvalidModuleException, IOException {
      ConfigHelper.checkParam("File", module);
      ConfigHelper.checkParam("ModuleType", moduleType);
      this.lazy = lazy;
      this.setPlan(plan);
      this.setPlanDir(plandir);
      this.setLibraries(libraries);
      if (parent == null) {
         DescriptorSupportManager.flush();
      }

      this.setParent(parent);
      this.setModuleArchive(module);
      this.setModuleType(moduleType);
      this.setUri(uri);
      this.setInstallDir(installDir);
      this.setDDBeanRoot(this.createDDBeanRoot(moduleType, altdd));
      if (!lazy) {
         this.getDDBeanRootInternal();
      }

   }

   private WebLogicDDBeanRoot createDDBeanRoot(ModuleType moduleType, String altdd) {
      return this.getDDBeanRootFactory().create(altdd, this, moduleType);
   }

   protected void setLibraries(LibrarySpec[] libraries) {
      this.libraries = libraries;
   }

   public File getPlandir() {
      return this.plandir;
   }

   public File getPlan() {
      return this.plan;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setPartitionName(String name) {
      this.partitionName = name;
   }

   public void setVirtualJarFile(VirtualJarFile theVJF) {
      this.closeVJF();
      this.vjf = theVJF;
   }

   public DeploymentPlanBean getPlanBean() throws IOException {
      if (this.planBean == null && this.plan != null) {
         InputStream is = new FileInputStream(this.plan);

         try {
            this.planBean = DescriptorParser.parseDeploymentPlan(is);
         } finally {
            is.close();
         }
      }

      return this.planBean;
   }

   protected void setPlanDir(File plandir) {
      this.plandir = plandir;
      if (plandir == null && this.planBean != null && this.planBean.getConfigRoot() != null) {
         this.plandir = new File(this.planBean.getConfigRoot());
      }

   }

   protected void setPlan(File plan) {
      this.plan = plan;
   }

   protected DDBeanRoot getDDBeanRootInternal() throws IOException, InvalidModuleException {
      if (this.ddRoot == null) {
         this.setDDBeanRoot(this.createDDBeanRoot());
      }

      WebLogicDDBeanRoot root = (WebLogicDDBeanRoot)this.ddRoot.getRoot();
      if (root != null && root.isInitialized()) {
         return root;
      } else {
         if (debug) {
            Debug.say("DDBeanRootImpl root for " + root.getFilename() + " is null or not initialized");
         }

         return null;
      }
   }

   private WebLogicDDBeanRoot createDDBeanRoot() {
      DDBeanRootFactory f = this.getDDBeanRootFactory();
      return f.create((String)null, this, this.moduleType, (DescriptorBean)null, true);
   }

   private DDBeanRootFactory getDDBeanRootFactory() {
      DDBeanRootFactory f = (DDBeanRootFactory)GlobalServiceLocator.getServiceLocator().getService(DDBeanRootFactory.class, new Annotation[0]);
      return f;
   }

   protected InputStream getDDStream(String altdd, ModuleType moduleType) throws FileNotFoundException {
      Object dd;
      if (altdd != null) {
         if (this.parent == null) {
            throw new AssertionError("Attempt to construct standalone module with altDD");
         }

         dd = this.parent.getEntry(altdd);
         if (dd == null) {
            throw new FileNotFoundException(SPIDeployerLogger.noFile(altdd));
         }

         if (debug) {
            Debug.say("using altdd at " + altdd);
         }
      } else if (this.getArchive().getName().endsWith(".xml")) {
         dd = new FileInputStream(this.getArchive());
      } else {
         dd = this.getEntry(WebLogicModuleTypeUtil.getDDUri(moduleType.getValue()));
      }

      return (InputStream)dd;
   }

   public ModuleType getType() {
      return this.moduleType;
   }

   public DDBean[] getChildBean(String xpath) {
      ConfigHelper.checkParam("xpath", xpath);
      return this.getDDBeanRoot().getChildBean(xpath);
   }

   public Class getClassFromScope(String className) {
      ConfigHelper.checkParam("className", className);

      try {
         this.gcl = this.getOrCreateGCL();
         return Class.forName(className, false, this.gcl);
      } catch (ClassNotFoundException var3) {
         return null;
      } catch (IOException var4) {
         return null;
      }
   }

   public DDBeanRoot getDDBeanRoot() {
      try {
         return this.getDDBeanRootInternal();
      } catch (Exception var2) {
         if (debug) {
            var2.printStackTrace();
         }

         throw new RuntimeException(var2);
      }
   }

   protected void setDDBeanRoot(DDBeanRoot root) {
      this.ddRoot = new DDRootFields((WebLogicDDBeanRoot)root);
   }

   public String[] getText(String xpath) {
      ConfigHelper.checkParam("xpath", xpath);
      return this.getDDBeanRoot().getText(xpath);
   }

   public boolean hasDDBean(String filename) throws FileNotFoundException {
      if (debug) {
         Debug.say("getting ddbean root for : " + filename);
      }

      if (filename == null) {
         throw new FileNotFoundException(filename);
      } else {
         if (filename.equals(".")) {
            filename = this.getArchive().getPath();
         }

         WebLogicDDBeanRoot root = (WebLogicDDBeanRoot)this.ddMap.get(filename);
         return root != null ? root.hasDBean() : false;
      }
   }

   public DDBeanRoot getDDBeanRoot(String filename) throws FileNotFoundException, DDBeanCreateException {
      if (debug) {
         Debug.say("getting ddbean root for : " + filename);
      }

      if (filename == null) {
         throw new FileNotFoundException(filename);
      } else {
         if (filename.equals(".")) {
            filename = this.getArchive().getPath();
         }

         WebLogicDDBeanRoot root = (WebLogicDDBeanRoot)this.ddMap.get(filename);
         if (root != null && root.isInitialized()) {
            return root;
         } else {
            if (debug) {
               Debug.say("DDBeanRootImpl root " + (root == null ? "" : root.getFilename()) + " is null or not initialized");
            }

            return null;
         }
      }
   }

   public InputStream getInputStream(String filename) throws FileNotFoundException {
      InputStream dd = this.getEntry(filename);
      if (dd == null && this.haveAppRoot) {
         File ddfile;
         if (this.parent == null) {
            ddfile = this.getInstallDir().getAppDDFile(filename);
         } else {
            ddfile = this.getInstallDir().getDDFile(this.uri, filename);
         }

         if (debug) {
            Debug.say("Looking for dd in config area: " + ddfile.getPath());
         }

         dd = new FileInputStream(ddfile);
      }

      return (InputStream)dd;
   }

   public Enumeration entries() {
      if (this.getArchive().getName().endsWith(".xml")) {
         List l = new ArrayList();
         l.add(this.moduleArchive.getPath());
         return Collections.enumeration(l);
      } else {
         Iterator vjfs = this.vjf.entries();
         Collection c = new ArrayList();

         while(vjfs.hasNext()) {
            c.add(((ZipEntry)vjfs.next()).getName());
         }

         return Collections.enumeration(c);
      }
   }

   public Enumeration getDDResourceEntries(String resourceType) {
      Collection allResources = new HashSet();
      String uri = null;
      Iterator uris = this.ddMap.keySet().iterator();

      while(uris.hasNext()) {
         uri = (String)uris.next();
         if (uri.endsWith(resourceType)) {
            allResources.add(uri);
         }
      }

      return Collections.enumeration(allResources);
   }

   public InputStream getEntry(String name) {
      ConfigHelper.checkParam("name", name);

      try {
         if (this.getArchive().getName().endsWith(".xml")) {
            if (name.equals(".")) {
               return new FileInputStream(this.getArchive());
            }

            if (debug) {
               Debug.say("No entry in document for " + name);
            }

            return null;
         }

         if (debug) {
            Debug.say("in DO : " + this.moduleArchive.getName() + " with uri " + this.uri);
         }

         if (debug) {
            Debug.say("Getting stream for entry " + name);
         }

         if (this.vjf != null) {
            ZipEntry z = this.vjf.getEntry(name);
            if (z != null) {
               return this.vjf.getInputStream(z);
            }
         }

         if (this.parent != null) {
            return this.getStreamFromParent(this.uri, name);
         }

         if (debug) {
            Debug.say("No entry in archive for " + name);
         }
      } catch (IOException var3) {
         if (debug) {
            Debug.say("No entry in archive for " + name);
         }
      }

      return null;
   }

   protected InputStream getStreamFromParent(String uri, String name) throws IOException {
      VirtualJarFile j = this.parent.getVirtualJarFile();
      if (j != null) {
         ZipEntry z = j.getEntry(uri + "/" + name);
         if (z != null) {
            return j.getInputStream(z);
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public String getModuleDTDVersion() {
      return !((WebLogicDDBeanRoot)this.getDDBeanRoot()).isSchemaBased() ? this.getDDBeanRoot().getDDBeanRootVersion() : null;
   }

   public void setDescriptorBean(DescriptorBean db) {
      this.beanTree = db;
   }

   public boolean isDBSet() {
      return this.beanTree != null;
   }

   public DescriptorBean getDescriptorBean() throws IOException {
      if (this.beanTree != null) {
         return this.beanTree;
      } else {
         if (debug) {
            Debug.say("beanTree of WebLogicDeployableObject (" + this.toString() + ") is null.");
         }

         return null;
      }
   }

   public void setDeleteOnClose() {
      this.deleteOnClose = true;
   }

   public LibrarySpec[] getLibraries() {
      return this.libraries;
   }

   public File getArchive() {
      return this.moduleArchive;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.moduleType.toString());
      sb.append(" Archive: ");
      sb.append(this.moduleArchive.toString());
      if (this.contextRoot != null) {
         sb.append(" ContextRoot: ");
         sb.append(this.contextRoot);
      }

      return sb.toString();
   }

   public String dump() {
      StringBuffer sb = new StringBuffer();
      if (this.getDDBeanRoot() != null) {
         sb.append(this.toString());
         sb.append("\n");
         sb.append(this.getDDBeanRoot().toString());
         sb.append("\n");
      }

      return sb.toString();
   }

   public String getFileName(DDBeanRoot root) {
      Iterator f = this.ddMap.keySet().iterator();

      DDBeanRoot ddr;
      String fn;
      do {
         if (!f.hasNext()) {
            return WebLogicModuleTypeUtil.getDDUri(this.moduleType.getValue());
         }

         fn = (String)f.next();
         ddr = (DDBeanRoot)this.ddMap.get(fn);
      } while(ddr != root);

      return fn;
   }

   public DDBeanRoot[] getDDBeanRoots() {
      return (DDBeanRoot[])((DDBeanRoot[])this.ddMap.values().toArray(new DDBeanRoot[0]));
   }

   public File getInstallDirPath() {
      return this.parent != null ? this.parent.getInstallDirPath() : this.getInstallDir().getInstallDir();
   }

   public InstallDir getInstallDir() {
      return this.parent != null ? this.parent.getInstallDir() : this.installDir;
   }

   protected void setInstallDir(File path) throws IOException {
      this.haveAppRoot = path != null;
      if (this.parent == null) {
         this.installDir = new InstallDir(this.moduleArchive.getName(), path);
      }
   }

   protected void setModuleArchive(File module) throws IOException {
      if (debug) {
         Debug.say("setting module archive: " + module.toString());
      }

      this.moduleArchive = module;
      if (!module.getName().endsWith(".xml")) {
         if (this.vjf == null && module != null) {
            ModuleTypeManager mtm = ((ModuleTypeManagerFactory)GlobalServiceLocator.getServiceLocator().getService(ModuleTypeManagerFactory.class, new Annotation[0])).create(this.moduleArchive);
            this.vjf = mtm.createVirtualJarFile();
         }

      }
   }

   protected void setModuleType(ModuleType mt) {
      if (debug) {
         Debug.say("setting module type: " + mt);
      }

      this.moduleType = mt;
   }

   public void setParent(WebLogicDeployableObject parent) {
      this.parent = parent;
   }

   public WebLogicDeployableObject getParent() {
      return this.parent;
   }

   public VirtualJarFile getVirtualJarFile() {
      return this.vjf;
   }

   public String getUri() {
      return this.uri;
   }

   protected void setUri(String u) {
      if (debug) {
         Debug.say("setting module uri: " + u);
      }

      this.uri = u;
   }

   public GenericClassLoader getOrCreateGCL() throws IOException {
      if (this.gcl == null) {
         ClassLoader tcl = Thread.currentThread().getContextClassLoader();
         if (this.moduleArchive.getName().endsWith(".xml")) {
            this.gcl = new GenericClassLoader(NullClassFinder.NULL_FINDER, tcl);
         } else {
            JarClassFinder jcf = new JarClassFinder(this.moduleArchive);
            this.gcl = new GenericClassLoader(new MultiClassFinder(), tcl);
            this.gcl.addClassFinder(jcf);
         }
      }

      return this.gcl;
   }

   public void close() {
      Iterator iter = this.subModules.iterator();

      while(iter.hasNext()) {
         WebLogicDeployableObject subModule = (WebLogicDeployableObject)iter.next();
         if (subModule != null) {
            subModule.close();
         }
      }

      this.closeGCL();
      this.closeVJF();
      this.closeResourceFinder();
      if (this.deleteOnClose && !debug) {
         FileUtils.remove(this.moduleArchive.getParentFile());
      }

      this.ddMap.clear();
   }

   protected void closeGCL() {
      if (this.gcl != null) {
         this.gcl.close();
      }

      this.gcl = null;
   }

   protected void closeResourceFinder() {
      if (this.resourceFinder != null) {
         this.resourceFinder.close();
      }

      this.resourceFinder = null;
   }

   protected void closeVJF() {
      if (this.vjf != null) {
         try {
            if (debug) {
               Debug.say("Closing " + this.vjf.getName());
            }

            this.vjf.close();
         } catch (IOException var2) {
         }
      }

      this.vjf = null;
   }

   public void setResourceFinder(ClassFinder finder) {
      this.resourceFinder = finder;
   }

   public ClassFinder getResourceFinder() {
      return this.resourceFinder;
   }

   public String getContextRoot() {
      return this.contextRoot;
   }

   public void setContextRoot(String contextRoot) {
      this.contextRoot = contextRoot;
   }

   public void setAppMerge(Tool am) {
   }

   protected class DDRootFields {
      private WebLogicDDBeanRoot root;

      DDRootFields(WebLogicDDBeanRoot root) {
         this.setRoot(root);
      }

      public DDBeanRoot getRoot() {
         return this.root;
      }

      public void setRoot(WebLogicDDBeanRoot root) {
         this.root = root;
      }
   }
}
