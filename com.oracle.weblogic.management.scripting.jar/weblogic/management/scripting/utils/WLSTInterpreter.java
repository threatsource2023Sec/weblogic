package weblogic.management.scripting.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import org.python.core.Options;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.InteractiveInterpreter;
import weblogic.Home;
import weblogic.WLST;
import weblogic.kernel.Kernel;
import weblogic.management.scripting.JLineWrapper;
import weblogic.management.scripting.core.utils.WLSTCoreUtil;
import weblogic.utils.StringUtils;

public class WLSTInterpreter extends InteractiveInterpreter {
   public static final String SKIP_WLS_MODULE_SCANNING = "skipWLSModuleScanning";
   public static final String ENABLE_SCRIPT_MODE = "enableScriptMode";
   public static final String SCRIPT_DIR = "wlstScriptDir";
   public static final String ONLINE_ONLY_MODE = "onlineOnlyMode";
   public static final String DISABLE_PLUGIN_JAR_LOADING_MODE = "disablePluginJarLoadingMode";
   private OutputStream stdOstream;
   private Writer stdWriter;
   private OutputStream errOstream;
   private Writer errWriter;
   private String wlstHome;
   private String scriptDir;
   private String weblogicJarLocation;
   private static final String WLST_PROFILE_FILE = "wlstProfile.py";
   private static final String JAR_EXT = ".jar";
   private static final String FILE_PREFIX = "file:";
   private static final boolean isRunningInServer = Kernel.isServer();
   boolean isStandalone;
   private static final String WLST_UTIL_WRAPPER_CLASS = "weblogic.management.scripting.utils.WLSTUtilWrapper";
   private static final String WLST_CORE_UTIL_WRAPPER_CLASS = "weblogic.management.scripting.core.utils.WLSTCoreUtilWrapper";
   private WLSTUtilWrapperFactory wlstUtilWrapper;
   private boolean easeSyntax;
   private boolean scriptMode;
   private boolean onlineOnlyMode;
   private boolean recordingInProgress;
   private boolean recordAll;
   private PyObject offline_cmo;
   private PyObject offline_myps1;
   private boolean disconnected;
   private boolean hasLocalInstall;
   private boolean disablePluginJarLoadingMode;
   private boolean initialized;

   public WLSTInterpreter() {
      this((Hashtable)null);
   }

   public WLSTInterpreter(Hashtable h) {
      this.stdOstream = null;
      this.stdWriter = null;
      this.errOstream = null;
      this.errWriter = null;
      this.wlstHome = null;
      this.scriptDir = null;
      this.weblogicJarLocation = null;
      this.isStandalone = false;
      this.wlstUtilWrapper = null;
      this.easeSyntax = false;
      this.scriptMode = false;
      this.onlineOnlyMode = false;
      this.recordingInProgress = false;
      this.recordAll = false;
      this.offline_cmo = null;
      this.offline_myps1 = null;
      this.disconnected = false;
      this.hasLocalInstall = true;
      this.disablePluginJarLoadingMode = false;
      this.initialized = false;
      Class var2 = WLSTInterpreter.class;
      synchronized(WLSTInterpreter.class) {
         if (!this.initialized) {
            PySystemState.initialize();
            super.set("theInterpreter", this);
            super.exec("import sys\n");
            ClassLoader tccl = Thread.currentThread().getContextClassLoader();
            this.setClassLoader(tccl);
            boolean skipPackageScanning = isSkipPackageScanning();
            boolean skipWLSModuleScanning = false;
            Object val;
            if (skipPackageScanning) {
               skipWLSModuleScanning = true;
            } else if (h != null) {
               val = h.get("skipWLSModuleScanning");
               if (val != null && val instanceof Boolean) {
                  skipWLSModuleScanning = (Boolean)val;
               }
            }

            if (h != null) {
               val = h.get("enableScriptMode");
               if (val != null && val instanceof Boolean) {
                  this.setScriptMode((Boolean)val);
               }
            }

            if (h != null) {
               val = h.get("wlstScriptDir");
               if (val != null && val instanceof String) {
                  this.setScriptDir((String)val);
               }
            }

            if (h != null) {
               val = h.get("onlineOnlyMode");
               if (val != null && val instanceof Boolean) {
                  this.setOnlineOnlyMode((Boolean)val);
               }
            }

            if (h != null) {
               val = h.get("disablePluginJarLoadingMode");
               if (val != null && val instanceof Boolean) {
                  this.setDisablePluginJarLoadingMode((Boolean)val);
               }
            }

            if (WLSTInterpreter.class.getClassLoader().getResource("weblogic.management.scripting.utils.WLSTUtilWrapper".replace('.', '/') + ".class") == null && WLSTInterpreter.class.getClassLoader().getResource("weblogic.management.scripting.core.utils.WLSTCoreUtilWrapper".replace('.', '/') + ".class") != null) {
               this.isStandalone = true;
            }

            if (!this.isStandalone) {
               try {
                  String weblogic90Location = (new File(Home.getFile().getParentFile().getAbsolutePath())).getAbsolutePath();
                  String commonLocation = weblogic90Location + File.separator + "common";
                  this.wlstHome = commonLocation + File.separator + "wlst";
                  this.wlstHome = StringUtils.replaceGlobal(this.wlstHome, File.separator, "/");
                  debugInit("wlstHome = " + this.wlstHome);
                  if (!(new File(this.wlstHome)).exists()) {
                     this.hasLocalInstall = false;
                     this.wlstHome = null;
                     debugInit("wlstHome directory does not exist ");
                  }
               } catch (Exception var9) {
                  this.hasLocalInstall = false;
                  debugInit("hasLocalInstall = false " + var9);
               }

               if (!skipPackageScanning) {
                  boolean isMaven = this.getDisablePluginJarLoadingMode() || this.getOnlineOnlyMode();
                  this.addWebLogicJar(isMaven);
               }
            }

            if (skipPackageScanning) {
               addJavaRuntime();
            }

            this.setWLSTUtilWrapper();
            WLSTUtilHelper.wlstAsModule = false;
            debugInit("set jython modules");
            this.setJythonModules();
            debugInit("Set up offline stuff.");
            this.wlstUtilWrapper.setupOffline(this);
            debugInit("set modules");
            this.setModules();
            debugInit("exec profile");
            this.execProfile();
            debugInit("Scan wls modules");
            if (!skipWLSModuleScanning && !this.isStandalone) {
               this.addWLSModules();
            }

            this.initialized = true;
         }

      }
   }

   private void setWLSTUtilWrapper() {
      try {
         Class clz;
         if (this.isStandalone) {
            clz = Class.forName("weblogic.management.scripting.core.utils.WLSTCoreUtilWrapper", true, WLSTInterpreter.class.getClassLoader());
         } else {
            clz = Class.forName("weblogic.management.scripting.utils.WLSTUtilWrapper", true, WLSTInterpreter.class.getClassLoader());
         }

         debugInit("call getInstance on " + clz);
         Method getInstance = clz.getMethod("getInstance");
         this.wlstUtilWrapper = (WLSTUtilWrapperFactory)WLSTUtilWrapperFactory.class.cast(getInstance.invoke((Object)null));
      } catch (ClassNotFoundException var3) {
         throw new IllegalStateException(var3);
      } catch (Exception var4) {
         throw new IllegalStateException(var4);
      }
   }

   public void setClassLoader(ClassLoader cls) {
      this.systemState.setClassLoader(cls);
   }

   public ClassLoader getClassLoader() {
      return this.systemState.getClassLoader();
   }

   public static boolean isRunningInServer() {
      return isRunningInServer;
   }

   private static void debugInit(String msg) {
      WLSTUtilHelper.debugInit(msg);
   }

   private static void debugInit(String msg, Throwable t) {
      WLSTUtilHelper.debugInit(msg, t);
   }

   private static boolean isSkipPackageScanning() {
      String skipCacheDir = System.getProperty("python.cachedir.skip");
      return skipCacheDir != null && skipCacheDir.equalsIgnoreCase("true");
   }

   private void setJythonModules() {
      if (this.wlstHome == null && this.isStandalone) {
         this.wlstHome = this.findWLSTHomeForCAM();
      }

      debugInit("wlstHome resolved to " + this.wlstHome);
      String jythonModuleJarName = "jython-modules";
      String lib = null;
      if (this.hasLocalInstall) {
         lib = this.wlstHome + "/modules/" + jythonModuleJarName + ".jar/Lib";
      } else {
         lib = this.getJarLocationFromClasspath(jythonModuleJarName) + "/Lib";
      }

      File f = new File(lib);
      lib = StringUtils.replaceGlobal(f.getAbsolutePath(), File.separator, "/");
      debugInit("The wlst lib is evaluated to " + lib);
      super.exec("sys.path.append('" + lib + "')");
   }

   private void setModules() {
      try {
         if (this.wlstHome == null && this.isStandalone) {
            this.wlstHome = this.findWLSTHomeForCAM();
         }

         debugInit("wlstHome resolved to " + this.wlstHome);
         if (this.hasLocalInstall && this.wlstHome != null) {
            File f = new File(this.wlstHome);
            String s = StringUtils.replaceGlobal(f.getAbsolutePath(), File.separator, "/");
            super.exec("sys.path.append('" + s + "')");
            super.exec("sys.path.append('" + s + "/lib')");
            super.exec("sys.path.append('" + s + "/modules')");
            debugInit("import py files under wlstHome: " + this.wlstHome + "/lib");
            this.importPyFiles(this.wlstHome + "/lib");
            debugInit("exec default platform files under wlstHome: " + this.wlstHome);
            this.execDefaultUserModules(this.wlstHome);
            debugInit("Append wlstModule_core.py to the sys.path");
            this.appendModulesPyFile(WLSTCoreUtil.class, "wlstModule_core.py");
            debugInit("Append wlstModule.py to the sys.path");
            this.appendModulesPyFile(this.getClass(), "wlstModule.py");
         } else {
            debugInit("Append wlstModule_core.py to the sys.path");
            this.appendModulesPyFile(WLSTCoreUtil.class, "wlstModule_core.py");
            debugInit("Append wlstModule.py to the sys.path");
            this.appendModulesPyFile(this.getClass(), "wlstModule.py");
         }

         debugInit("Exec modules under weblogic.wlstHome specified as system property.");
         this.execSpecifiedUserModules(System.getProperty("weblogic.wlstHome", ""));
         debugInit("Exec jars under wlstScriptDir");
         this.execJarUserModules();
         debugInit("Exec .py files under wlstScriptDir: " + this.scriptDir);
         this.execSpecifiedUserModules(this.scriptDir);
         super.exec("sys.path.insert(0,'.')");
      } catch (Throwable var3) {
         debugInit("The default modules were not executed ... ", var3);
      }

   }

   private String findWLSTHomeForCAM() {
      String WLSERVER_STR = "/wlserver";
      String FILE_STR = "file:";
      String wHome = null;
      String home = System.getProperty("weblogic.wlstHome", "");
      String wlserverLoc;
      if (home.length() != 0) {
         String[] homes = home.trim().split(File.pathSeparator);

         for(int i = 0; i < homes.length; ++i) {
            if (!StringUtils.isEmptyString(homes[i])) {
               debugInit("User specified wlst home is " + homes[i]);
               File f = new File(homes[i]);
               String s = StringUtils.replaceGlobal(f.getAbsolutePath(), File.separator, "/");
               wlserverLoc = s + "/modules/jython-modules.jar";
               File jarFile = new File(wlserverLoc);
               if (jarFile.exists() && jarFile.isFile()) {
                  wHome = s;
                  debugInit("The wlst home containing jython-modules.jar is " + s);
                  break;
               }
            }
         }
      }

      if (wHome == null) {
         String classLocation = WLST.class.getName().replace('.', '/') + ".class";
         String path = WLST.class.getClassLoader().getResource(classLocation).getPath();
         path = StringUtils.replaceGlobal(path, File.separator, "/");
         debugInit("Path for weblogic.WLST.class " + path);
         if (path != null) {
            int wlserverIndex = path.lastIndexOf("/wlserver/");
            int fileIndex = path.indexOf("file:");
            if (fileIndex > -1 && wlserverIndex > -1) {
               wlserverLoc = path.substring("file:".length(), wlserverIndex + "/wlserver".length());
               wHome = wlserverLoc + "/common/wlst";
               debugInit("Found wlst home " + wHome);
            }
         }
      }

      debugInit("wlst home is " + wHome);
      if (wHome == null) {
         throw new IllegalStateException("Cannot find wlst home. Please check weblogic.wlstHome property.");
      } else {
         return wHome;
      }
   }

   private void addWebLogicJar(boolean isMaven) {
      if (!isMaven) {
         debugInit("Adding weblogic.jar");
         this.weblogicJarLocation = this.findWeblogicJarFromWlstClasspathJar();
         if (this.weblogicJarLocation == null) {
            this.weblogicJarLocation = this.getJarLocationFromClasspath("weblogic");
         }
      } else if (this.hasLocalInstall) {
         debugInit("Adding weblogic.jar in Maven with local install");
         this.weblogicJarLocation = Home.getFile() + File.separator + "lib" + File.separator + "weblogic.jar";
      } else {
         debugInit("Adding weblogic.jar in Maven with no local install");
         this.weblogicJarLocation = this.getJarLocationFromClasspath("weblogic");
      }

      if (this.weblogicJarLocation == null) {
         debugInit("Failed to find weblogic.jar...skipping adding it to sys.path");
      } else {
         this.weblogicJarLocation = StringUtils.replaceGlobal(this.weblogicJarLocation, File.separator, "/");
         debugInit("WebLogic jar is at " + this.weblogicJarLocation);
         super.exec("sys.path.append(\"" + this.weblogicJarLocation + "\")\n");
      }
   }

   private String findWeblogicJarFromWlstClasspathJar() {
      String wlstWlsClasspathJar = this.getJarLocationFromClasspath("wlst.wls.classpath");
      if (wlstWlsClasspathJar == null) {
         return null;
      } else {
         String weblogicJarPath = null;

         try {
            JarFile jarFile = new JarFile(wlstWlsClasspathJar);
            Manifest manifest = jarFile.getManifest();
            if (manifest == null) {
               return null;
            }

            Attributes attrs = manifest.getMainAttributes();
            String mcp = attrs.getValue(Name.CLASS_PATH);
            if (StringUtils.isEmptyString(mcp)) {
               return null;
            }

            File parent = (new File(wlstWlsClasspathJar)).getParentFile();
            String[] mcpArr = mcp.trim().split("\\s+");

            for(int i = 0; i < mcpArr.length; ++i) {
               if (!StringUtils.isEmptyString(mcpArr[i])) {
                  StringUtils.replaceGlobal(mcpArr[i], File.separator, "/");
                  if (mcpArr[i].endsWith("/weblogic.jar")) {
                     File weblogicJar = new File(parent, mcpArr[i]);
                     weblogicJarPath = weblogicJar.getCanonicalPath();
                     break;
                  }
               }
            }
         } catch (IOException var11) {
         }

         return weblogicJarPath;
      }
   }

   private void addWLSModules() {
      String jarFileName = this.weblogicJarLocation;
      if (jarFileName == null) {
         debugInit("addWLSModules() called with null weblogicJarLocation");
         String cp = System.getProperty("java.class.path");
         if (StringUtils.isEmptyString(cp)) {
            return;
         }

         String[] cpArr = cp.trim().split(System.getProperty("path.separator"));

         for(int i = 0; i < cpArr.length; ++i) {
            String temp = StringUtils.replaceGlobal(cpArr[i], File.separator, "/");
            if (temp.endsWith("/weblogic.jar")) {
               jarFileName = cpArr[i];
               break;
            }

            if (temp.indexOf("features/weblogic.server.modules_") != -1 && temp.endsWith(".jar")) {
               jarFileName = cpArr[i];
               break;
            }

            if (temp.indexOf("features/weblogic.server.merge.modules_") != -1 && temp.endsWith(".jar")) {
               jarFileName = cpArr[i];
               break;
            }
         }
      }

      if (jarFileName == null) {
         debugInit("addWLSModules() failed to find weblogic.jar file...skipping call to addModulesFromManifest()");
      } else {
         this.addModulesFromManifest(jarFileName);
      }
   }

   private void addModulesFromManifest(String jarFileName) {
      try {
         debugInit("Add modules from manifest of " + jarFileName);
         debugInit("java.class.path = " + System.getProperty("java.class.path"));
         JarFile jarFile = new JarFile(jarFileName);
         Manifest manifest = jarFile.getManifest();
         if (manifest == null) {
            return;
         }

         Attributes attrs = manifest.getMainAttributes();
         String mcp = attrs.getValue(Name.CLASS_PATH);
         if (StringUtils.isEmptyString(mcp)) {
            return;
         }

         File parent = (new File(jarFileName)).getParentFile();
         String[] mcpArr = mcp.trim().split("\\s+");
         boolean jarFromMavenRepo = false;

         int i;
         File f;
         for(i = 0; i < mcpArr.length; ++i) {
            if (!StringUtils.isEmptyString(mcpArr[i]) && mcpArr[i].endsWith(".jar")) {
               f = new File(parent, mcpArr[i]);
               if (!f.exists()) {
                  jarFromMavenRepo = true;
               }
               break;
            }
         }

         if (jarFromMavenRepo) {
            this.processMavenRepoJar(mcpArr);
         } else {
            for(i = 0; i < mcpArr.length; ++i) {
               if (!StringUtils.isEmptyString(mcpArr[i])) {
                  f = new File(parent, mcpArr[i]);
                  if (mcpArr[i].endsWith(".jar")) {
                     String p = f.getCanonicalPath();
                     debugInit("adding jar to package manager: " + p);
                     PySystemState.packageManager.addJar(p, true);
                  } else {
                     debugInit("adding directory to package manager: " + f.getAbsolutePath());
                     PySystemState.packageManager.addDirectory(f);
                  }
               }
            }
         }
      } catch (IOException var12) {
      }

   }

   private void processMavenRepoJar(String[] mcpArr) throws IOException {
      debugInit("processing maven repo jar with manifest class-path length of " + mcpArr.length);

      for(int i = 0; i < mcpArr.length; ++i) {
         if (!StringUtils.isEmptyString(mcpArr[i]) && mcpArr[i].endsWith(".jar")) {
            String temp = StringUtils.replaceGlobal(mcpArr[i], File.separator, "/");
            int startingPoint = temp.lastIndexOf("/") + 1;
            String jarName = temp.substring(startingPoint, temp.lastIndexOf(".jar"));
            String jarLocation = this.getJarLocationFromClasspath(jarName);
            if (jarLocation != null && jarLocation.indexOf("/com/oracle/") > -1) {
               File f = new File(jarLocation);
               String jarPath = f.getCanonicalPath();
               debugInit("added manifest class-path entry jar to package manager: " + jarPath);
               PySystemState.packageManager.addJar(jarPath, true);
            } else {
               debugInit("skipping manifest class-path entry jar: " + temp);
            }
         } else {
            debugInit("skipping manifest class-path entry: " + mcpArr[i]);
         }
      }

   }

   private static void addJavaRuntime() {
      Class aClass;
      try {
         aClass = Class.forName("java.lang.Object");
      } catch (ClassNotFoundException var7) {
         return;
      }

      if (aClass != null) {
         String className = aClass.getName().replace('.', '/');
         URL url = aClass.getResource("/" + className + ".class");
         String path = url.getPath();
         if (path.indexOf(".jar!") != -1) {
            String jarPath = path.substring("file:".length(), path.indexOf(33));
            if (jarPath.matches("/[a-zA-Z]:/.*")) {
               jarPath = jarPath.substring(1);
            }

            try {
               jarPath = URLDecoder.decode(jarPath, "UTF-8");
            } catch (UnsupportedEncodingException var6) {
               return;
            }

            PySystemState.packageManager.addJar(jarPath, true);
         }
      }
   }

   private void importPyFiles(String lib) {
      File file = new File(lib);
      File[] pyFiles = file.listFiles();
      if (pyFiles != null) {
         for(int i = 0; i < pyFiles.length; ++i) {
            File pyFile = pyFiles[i];
            if (!pyFile.isDirectory() && pyFile.getName().endsWith(".py")) {
               debugInit("Importing module " + pyFile.getAbsolutePath());

               try {
                  super.exec("import " + pyFile.getName().substring(0, pyFile.getName().length() - 3));
               } catch (Exception var7) {
                  debugInit("Exception for file:  " + pyFile.getAbsolutePath(), var7);
                  this.wlstUtilWrapper.println(pyFile.getAbsolutePath(), var7);
               }
            }
         }
      }

   }

   private void execDefaultUserModules(String wlstHome) {
      this.execPyFiles(wlstHome);
   }

   private void execSpecifiedUserModules(String home) {
      if (home != null && home.length() != 0) {
         String[] homes = home.trim().split(File.pathSeparator);

         for(int i = 0; i < homes.length; ++i) {
            if (!StringUtils.isEmptyString(homes[i])) {
               debugInit("User specified wlst home is " + homes[i]);
               File f = new File(homes[i]);
               String s = StringUtils.replaceGlobal(f.getAbsolutePath(), File.separator, "/");
               super.exec("sys.path.append('" + s + "')");
               super.exec("sys.path.append('" + s + "/lib')");
               super.exec("sys.path.append('" + s + "/modules')");
               debugInit("import modules under user specified wlst home lib: " + homes[i] + "/lib");
               this.importPyFiles(homes[i] + "/lib");
               debugInit("Exec modules under user specified wlst home: " + homes[i]);
               this.execPyFiles(homes[i]);
            }
         }
      }

   }

   private void execJarUserModules() throws IOException {
      ClassLoader mycl = this.getClassLoader();
      if (mycl == null) {
         mycl = WLSTInterpreter.class.getClassLoader();
      }

      Enumeration dirURLs = null;
      if (mycl != null) {
         dirURLs = mycl.getResources("wlstScriptDir");
      }

      if (dirURLs != null && dirURLs.hasMoreElements()) {
         while(true) {
            URL dirURL;
            String jarPathForError;
            do {
               if (!dirURLs.hasMoreElements()) {
                  return;
               }

               dirURL = (URL)dirURLs.nextElement();
               if (dirURL != null && dirURL.getProtocol().equals("file")) {
                  debugInit("Jar specified wlst home is " + dirURL);
                  File scriptDir = new File(dirURL.getFile());
                  jarPathForError = StringUtils.replaceGlobal(scriptDir.getAbsolutePath(), File.separator, "/");
                  debugInit("Adding <dir>/wlstScriptDir found from the classload to sys.path.");
                  super.exec("sys.path.append('" + jarPathForError + "')");
                  super.exec("sys.path.append('" + jarPathForError + "/lib')");
                  super.exec("sys.path.append('" + jarPathForError + "/modules')");
                  debugInit("import py files under <dir>/wlstScriptDir/lib");
                  this.importPyFiles(scriptDir + "/lib");
                  debugInit("exec top level files under <dir>/wlstScriptDir");
                  this.execPyFiles(scriptDir.getAbsolutePath());
               }
            } while(!dirURL.getProtocol().equals("jar"));

            debugInit("Get all the py files in the directory in the jar that is in the classpath");
            String jarPath = dirURL.getPath().substring("file:".length(), dirURL.getPath().indexOf("!"));
            jarPathForError = dirURL.getPath().substring(5);
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            debugInit("Found script dir in " + jarPath);
            String s = StringUtils.replaceGlobal(jarPath, File.separator, "/") + "/" + "wlstScriptDir";
            debugInit("Append to sys.path: " + s);
            super.exec("sys.path.append('" + s + "')");
            super.exec("sys.path.append('" + s + "/lib')");
            super.exec("sys.path.append('" + s + "/modules')");
            debugInit("Import .py files from <jar>/wlstScriptDir/lib");
            Enumeration entries = jar.entries();

            JarEntry jarEntry;
            String name;
            String entry;
            while(entries.hasMoreElements()) {
               jarEntry = (JarEntry)entries.nextElement();
               name = jarEntry.getName();
               if (name.startsWith("wlstScriptDir/lib/")) {
                  entry = name.substring("wlstScriptDir/lib".length() + 1);
                  if (entry.endsWith("/")) {
                     entry = entry.substring(0, entry.length() - 1);
                  }

                  if (entry.endsWith(".py") && entry.indexOf(47) == -1) {
                     debugInit("Importing module " + name);

                     try {
                        super.exec("import " + entry.substring(0, entry.length() - 3));
                     } catch (Exception var13) {
                        debugInit("Exception for file:  " + name, var13);
                        this.wlstUtilWrapper.println(jarPathForError + "/lib/" + entry, var13);
                     }
                  }
               }
            }

            debugInit("Execute .py files from <jar>/wlstScriptDir");
            entries = jar.entries();

            while(entries.hasMoreElements()) {
               jarEntry = (JarEntry)entries.nextElement();
               name = jarEntry.getName();
               if (name.startsWith("wlstScriptDir/")) {
                  entry = name.substring("wlstScriptDir".length() + 1);
                  if (entry.endsWith("/")) {
                     entry = entry.substring(0, entry.length() - 1);
                  }

                  if (entry.endsWith(".py") && entry.indexOf(47) == -1) {
                     debugInit("Execing the py file " + name);

                     try {
                        debugInit("Execfile(" + jarEntry + ", " + jarPathForError + "/" + entry);
                        this.superExecfileWrapper(jar.getInputStream(jarEntry), jarPathForError + "/" + entry);
                     } catch (Exception var14) {
                        debugInit("Exception for file:  " + name, var14);
                        this.wlstUtilWrapper.println(jarPathForError + "/" + entry, var14);
                     }
                  }
               }
            }
         }
      } else {
         debugInit("No WLST script dirs found in jars ");
      }
   }

   private void execPyFiles(String home) {
      File file = new File(home);
      File[] pyFiles = file.listFiles();
      if (pyFiles != null) {
         for(int i = 0; i < pyFiles.length; ++i) {
            File pyFile = pyFiles[i];
            if (!pyFile.isDirectory() && pyFile.getName().endsWith(".py")) {
               debugInit("Execing the py file " + pyFile.getAbsolutePath());

               try {
                  this.superExecfileWrapper(pyFile.getAbsolutePath());
               } catch (Exception var7) {
                  debugInit("Exception for file:  " + pyFile.getAbsolutePath(), var7);
                  this.wlstUtilWrapper.println(pyFile.getAbsolutePath(), var7);
               }
            }
         }
      }

   }

   private void execProfile() {
      try {
         File profile = new File("./wlstProfile.py");
         if (profile.exists()) {
            debugInit("WLST Profile found in the current directory, we will execute it " + profile.getAbsolutePath());
            this.superExecfileWrapper(profile.getAbsolutePath());
            return;
         }

         String userHome = System.getProperty("user.home");
         profile = new File(userHome + "/" + "wlstProfile.py");
         if (profile.exists()) {
            debugInit("WLST Profile found in the user.home, we will execute it " + profile.getAbsolutePath());
            this.superExecfileWrapper(profile.getAbsolutePath());
            return;
         }

         debugInit("No Profile file found in either current directory or user.home " + userHome);
      } catch (Throwable var3) {
         debugInit("The profile was not executed ... ", var3);
      }

   }

   private String getJarLocationFromClasspath(String jarName) {
      debugInit("WLSTInterpreter classloader = " + WLSTInterpreter.class.getClassLoader());
      ClassLoader cls = WLSTInterpreter.class.getClassLoader();
      int i;
      String version;
      if (cls instanceof URLClassLoader) {
         URLClassLoader urlClassLoader = (URLClassLoader)cls;
         URL[] var4 = urlClassLoader.getURLs();
         i = var4.length;

         for(int var6 = 0; var6 < i; ++var6) {
            URL u = var4[var6];
            debugInit("Looking for jar " + jarName + " in " + u);
            version = StringUtils.replaceGlobal(u.getFile().toString(), File.separator, "/");
            String name = (new File(version)).getName();
            if (name.equals(jarName + ".jar")) {
               debugInit("Found jar " + u);
               return u.getFile().toString();
            }

            if (name.startsWith(jarName + "-") && name.endsWith(".jar")) {
               String version = name.substring(jarName.length() + 1, name.length() - ".jar".length());
               if (version.contains("/" + jarName + "/" + version + "/")) {
                  debugInit("Found jar " + u);
                  return u.getFile().toString();
               }
            }
         }
      }

      String cp = System.getProperty("java.class.path");
      debugInit("Looking for jar " + jarName + " in " + cp);
      if (StringUtils.isEmptyString(cp)) {
         return null;
      } else {
         String[] cpArr = cp.trim().split(System.getProperty("path.separator"));

         for(i = 0; i < cpArr.length; ++i) {
            String temp = StringUtils.replaceGlobal(cpArr[i], File.separator, "/");
            String name = (new File(temp)).getName();
            if (name.equals(jarName + ".jar")) {
               debugInit("Found jar " + cpArr[i]);
               return cpArr[i];
            }

            if (name.startsWith(jarName + "-") && name.endsWith(".jar")) {
               version = name.substring(jarName.length() + 1, name.length() - ".jar".length());
               if (temp.contains("/" + jarName + "/" + version + "/")) {
                  debugInit("Found jar " + cpArr[i]);
                  return cpArr[i];
               }
            }
         }

         return null;
      }
   }

   private void appendModulesPyFile(Class clazz, String pyName) {
      URL url = clazz.getResource("modules/" + pyName);
      debugInit("URL for module " + pyName + " is " + url);
      if (url != null) {
         String jarPath;
         if (url.getProtocol().equals("file")) {
            File dir = (new File(url.getFile())).getParentFile();
            jarPath = StringUtils.replaceGlobal(dir.getAbsolutePath(), File.separator, "/");
            debugInit("add path to " + jarPath);
            super.exec("sys.path.append('" + jarPath + "')");
         }

         if (url.getProtocol().equals("jar")) {
            String urlPath = url.getPath();
            jarPath = urlPath.substring("file:".length(), urlPath.indexOf("!")) + urlPath.substring(url.getPath().indexOf("!") + 1, urlPath.lastIndexOf("/"));
            String s = StringUtils.replaceGlobal(jarPath, File.separator, "/");
            debugInit("JAR: add path to " + s);
            super.exec("sys.path.append('" + s + "')");
         }
      }

   }

   public WLSTInterpreter getWLInterpreter() {
      return this;
   }

   public void setOut(OutputStream ostream) {
      this.stdOstream = ostream;
      this.wlstUtilWrapper.setStdOutputMedium(ostream);
      this.wlstUtilWrapper.setlogToStandardOut(false);
      super.setOut(ostream);
      this.stdWriter = null;
   }

   public void setOut(Writer writer) {
      this.stdWriter = writer;
      this.wlstUtilWrapper.setStdOutputMedium(writer);
      this.wlstUtilWrapper.setlogToStandardOut(false);
      super.setOut(writer);
      this.stdOstream = null;
   }

   public void setOut(PyObject po) {
      Object obj = po.__tojava__(Object.class);
      if (obj instanceof OutputStream) {
         this.setOut((OutputStream)obj);
      } else if (obj instanceof Writer) {
         this.setOut((Writer)obj);
      }

      super.setOut(po);
   }

   public void setErr(OutputStream ostream) {
      this.errOstream = ostream;
      this.wlstUtilWrapper.setErrOutputMedium(ostream);
      this.wlstUtilWrapper.setlogToStandardOut(false);
      super.setErr(ostream);
      this.errWriter = null;
   }

   public void setErr(Writer writer) {
      this.errWriter = writer;
      this.wlstUtilWrapper.setErrOutputMedium(writer);
      this.wlstUtilWrapper.setlogToStandardOut(false);
      super.setErr(writer);
      this.errOstream = null;
   }

   public Object getOut() {
      if (this.stdOstream != null) {
         return this.stdOstream;
      } else {
         return this.stdWriter != null ? this.stdWriter : super.systemState.stdout;
      }
   }

   public Object getErr() {
      if (this.errOstream != null) {
         return this.errOstream;
      } else {
         return this.errWriter != null ? this.errWriter : super.systemState.stderr;
      }
   }

   public void exec(String string) {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();

      try {
         if (this.getRecordAll()) {
            this.wlstUtilWrapper.getInfoHandler().write(string);
         }

         try {
            super.exec(string);
         } catch (Throwable var7) {
            throw new RuntimeException(var7);
         }
      } finally {
         Thread.currentThread().setContextClassLoader(tccl);
      }

   }

   public void execfile(String string) {
      if (this.getRecordAll()) {
         this.wlstUtilWrapper.getInfoHandler().write(string);
      }

      this.superExecfileWrapper(string);
   }

   public void execfile(InputStream is) {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();

      try {
         super.execfile(is);
      } finally {
         Thread.currentThread().setContextClassLoader(tccl);
      }

   }

   private void superExecfileWrapper(String string) {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();

      try {
         super.execfile(string);
      } finally {
         Thread.currentThread().setContextClassLoader(tccl);
      }

   }

   private void superExecfileWrapper(InputStream is, String string) {
      ClassLoader tccl = Thread.currentThread().getContextClassLoader();

      try {
         super.execfile(is, string);
      } finally {
         Thread.currentThread().setContextClassLoader(tccl);
      }

   }

   public boolean runsource(String command, String source, JLineWrapper wrapper) {
      boolean var4;
      try {
         if (wrapper != null) {
            wrapper.unInit();
         }

         var4 = this.runsource(command, source);
      } finally {
         if (wrapper != null) {
            wrapper.reInit();
         }

      }

      return var4;
   }

   public boolean getEaseSyntax() {
      return this.easeSyntax;
   }

   public void setEaseSyntax(boolean easeSyntax) {
      this.easeSyntax = easeSyntax;
   }

   public boolean getScriptMode() {
      return this.scriptMode;
   }

   public void setScriptMode(boolean scriptMode) {
      this.scriptMode = scriptMode;
   }

   public boolean getOnlineOnlyMode() {
      return this.onlineOnlyMode;
   }

   public void setOnlineOnlyMode(boolean onlineOnlyMode) {
      this.onlineOnlyMode = onlineOnlyMode;
   }

   public boolean getDisablePluginJarLoadingMode() {
      return this.disablePluginJarLoadingMode;
   }

   public void setDisablePluginJarLoadingMode(boolean disablePluginJarLoadingMode) {
      this.disablePluginJarLoadingMode = disablePluginJarLoadingMode;
   }

   public boolean getRecordingInProgress() {
      return this.recordingInProgress;
   }

   public void setRecordingInProgress(boolean recordingInProgress) {
      this.recordingInProgress = recordingInProgress;
   }

   public boolean getRecordAll() {
      return this.recordAll;
   }

   public void setRecordAll(boolean recordAll) {
      this.recordAll = recordAll;
   }

   public PyObject getOfflineCMO() {
      return this.offline_cmo;
   }

   public void setOfflineCMO(PyObject offlineCMO) {
      this.offline_cmo = offlineCMO;
   }

   public PyObject getOfflinePrompt() {
      return this.offline_myps1;
   }

   public void setOfflinePrompt(PyObject offlinePrompt) {
      this.offline_myps1 = offlinePrompt;
   }

   public boolean getDisconnected() {
      return this.disconnected;
   }

   public void setDisconnected(boolean disconnected) {
      this.disconnected = disconnected;
   }

   public boolean isStandalone() {
      return this.isStandalone;
   }

   private void setScriptDir(String scriptDir) {
      this.scriptDir = scriptDir;
   }

   public String getScriptDir() {
      return this.scriptDir;
   }

   static {
      Options.importSite = false;
      String cacheDir = System.getProperty("python.cachedir");
      if (cacheDir == null) {
         cacheDir = WLSTUtilHelper.getWLSTTempFile();
         System.setProperty("python.cachedir", cacheDir);
      }

      String var = System.getProperty("python.verbose");
      if (var == null) {
         System.setProperty("python.verbose", "warning");
      }

      System.setProperty("python.cachedir.skip", "false");
   }
}
