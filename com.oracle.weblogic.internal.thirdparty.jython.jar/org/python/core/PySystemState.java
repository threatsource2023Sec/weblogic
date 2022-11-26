package org.python.core;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.AccessControlException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jnr.posix.util.Platform;
import org.python.Version;
import org.python.core.adapter.ClassicPyObjectAdapter;
import org.python.core.adapter.ExtensiblePyObjectAdapter;
import org.python.core.packagecache.PackageManager;
import org.python.core.packagecache.SysPackageManager;
import org.python.modules.Setup;
import org.python.modules.zipimport.zipimporter;
import org.python.util.Generic;

public class PySystemState extends PyObject implements AutoCloseable, ClassDictInit, Closeable, Traverseproc {
   public static final String PYTHON_CACHEDIR = "python.cachedir";
   public static final String PYTHON_CACHEDIR_SKIP = "python.cachedir.skip";
   public static final String PYTHON_CONSOLE_ENCODING = "python.console.encoding";
   public static final String PYTHON_IO_ENCODING = "python.io.encoding";
   public static final String PYTHON_IO_ERRORS = "python.io.errors";
   protected static final String CACHEDIR_DEFAULT_NAME = "cachedir";
   public static final String JYTHON_JAR = "jython.jar";
   public static final String JYTHON_DEV_JAR = "jython-dev.jar";
   public static final PyString version = new PyString(Version.getVersion());
   public static final PyTuple subversion = new PyTuple(new PyObject[]{new PyString("Jython"), Py.newString(""), Py.newString("")});
   public static final int hexversion;
   public static final PyVersionInfo version_info;
   public static final int maxunicode = 1114111;
   public static final int maxsize = Integer.MAX_VALUE;
   public static final PyString float_repr_style;
   static final PyString FILE_SYSTEM_ENCODING;
   public static boolean py3kwarning;
   public static final Class flags;
   public static final PyTuple _mercurial;
   public static final PyObject copyright;
   private static Map builtinNames;
   public static PyTuple builtin_module_names;
   public static PackageManager packageManager;
   private static File cachedir;
   private static PyList defaultPath;
   private static PyList defaultArgv;
   private static PyObject defaultExecutable;
   public static Properties registry;
   public static PyObject prefix;
   public static PyObject exec_prefix;
   public static final PyString byteorder;
   public static final int maxint = Integer.MAX_VALUE;
   public static final int minint = Integer.MIN_VALUE;
   private static boolean initialized;
   public PyList argv = new PyList();
   public PyObject modules;
   public Map modules_reloading;
   private ReentrantLock importLock;
   private ClassLoader syspathJavaLoader;
   public PyList path;
   public PyList warnoptions = new PyList();
   public PyObject builtins;
   private static PyObject defaultPlatform;
   public PyObject platform;
   public PyList meta_path;
   public PyList path_hooks;
   public PyObject path_importer_cache;
   public PyObject ps1;
   public PyObject ps2;
   public PyObject executable;
   private String currentWorkingDir;
   private ClassLoader classLoader;
   public PyObject stdout;
   public PyObject stderr;
   public PyObject stdin;
   public PyObject __stdout__;
   public PyObject __stderr__;
   public PyObject __stdin__;
   public PyObject __displayhook__;
   public PyObject __excepthook__;
   public PyObject last_value;
   public PyObject last_type;
   public PyObject last_traceback;
   public PyObject __name__;
   public PyObject __dict__;
   private int recursionlimit;
   private int checkinterval;
   private codecs.CodecState codecState;
   public boolean _systemRestart;
   public boolean dont_write_bytecode;
   private final PySystemStateCloser closer;
   private static final ReferenceQueue systemStateQueue;
   private static final ConcurrentMap sysClosers;
   public static final PyObject float_info;
   public static final PyObject long_info;
   private static final String INITIALIZER_SERVICE = "META-INF/services/org.python.core.JythonInitializer";

   public PySystemState() {
      this.platform = defaultPlatform;
      this.ps1 = PyAttributeDeleted.INSTANCE;
      this.ps2 = PyAttributeDeleted.INSTANCE;
      this.classLoader = null;
      this.last_value = Py.None;
      this.last_type = Py.None;
      this.last_traceback = Py.None;
      this.__name__ = new PyString("sys");
      this.recursionlimit = 1000;
      this.checkinterval = 100;
      this._systemRestart = false;
      this.dont_write_bytecode = false;
      initialize();
      this.closer = new PySystemStateCloser(this);
      this.modules = new PyStringMap();
      this.modules_reloading = new HashMap();
      this.importLock = new ReentrantLock();
      this.syspathJavaLoader = new SyspathJavaLoader(imp.getParentClassLoader());
      this.argv = (PyList)defaultArgv.repeat(1);
      this.path = (PyList)defaultPath.repeat(1);
      this.path.append(Py.newString("__classpath__"));
      this.path.append(Py.newString("__pyclasspath__/"));
      this.executable = defaultExecutable;
      this.builtins = getDefaultBuiltins();
      this.platform = defaultPlatform;
      this.meta_path = new PyList();
      this.path_hooks = new PyList();
      this.path_hooks.append(new JavaImporter());
      this.path_hooks.append(zipimporter.TYPE);
      this.path_hooks.append(ClasspathPyImporter.TYPE);
      this.path_importer_cache = new PyDictionary();
      this.currentWorkingDir = (new File("")).getAbsolutePath();
      this.dont_write_bytecode = Options.dont_write_bytecode;
      py3kwarning = Options.py3k_warning;
      String mode = Options.unbuffered ? "b" : "";
      int buffering = Options.unbuffered ? 0 : 1;
      this.stdin = this.__stdin__ = new PyFile(System.in, "<stdin>", "r" + mode, buffering, false);
      this.stdout = this.__stdout__ = new PyFile(System.out, "<stdout>", "w" + mode, buffering, false);
      this.stderr = this.__stderr__ = new PyFile(System.err, "<stderr>", "w" + mode, 0, false);
      this.initEncoding();
      this.__displayhook__ = new PySystemStateFunctions("displayhook", 10, 1, 1);
      this.__excepthook__ = new PySystemStateFunctions("excepthook", 30, 3, 3);
      if (this.builtins == null) {
         this.builtins = getDefaultBuiltins();
      }

      this.modules.__setitem__((String)"__builtin__", new PyModule("__builtin__", getDefaultBuiltins()));
      this.__dict__ = new PyStringMap();
      this.__dict__.invoke("update", this.getType().fastGetDict());
      this.__dict__.__setitem__("displayhook", this.__displayhook__);
      this.__dict__.__setitem__("excepthook", this.__excepthook__);
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"trace", (PyObject)null);
      dict.__setitem__((String)"profile", (PyObject)null);
      dict.__setitem__((String)"windowsversion", (PyObject)null);
      if (!System.getProperty("os.name").startsWith("Windows")) {
         dict.__setitem__((String)"getwindowsversion", (PyObject)null);
      }

   }

   void reload() throws PyIgnoreMethodTag {
      this.__dict__.invoke("update", this.getType().fastGetDict());
   }

   private static void checkReadOnly(String name) {
      if (name == "__dict__" || name == "__class__" || name == "registry" || name == "exec_prefix" || name == "packageManager") {
         throw Py.TypeError("readonly attribute");
      }
   }

   private static void checkMustExist(String name) {
      if (name == "__dict__" || name == "__class__" || name == "registry" || name == "exec_prefix" || name == "platform" || name == "packageManager" || name == "builtins" || name == "warnoptions") {
         throw Py.TypeError("readonly attribute");
      }
   }

   private static String getCommandResult(String... command) {
      String result = "";
      String line = null;
      ProcessBuilder pb = new ProcessBuilder(command);

      try {
         Process p = pb.start();
         BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

         while((line = br.readLine()) != null) {
            if (line.length() > 0 && result.length() == 0) {
               result = line.trim();
            }
         }

         br.close();
         if (p.waitFor() != 0) {
            result = "";
         }
      } catch (InterruptedException | SecurityException | IOException var6) {
         result = "";
      }

      return result;
   }

   private void initEncoding() {
      String encoding = registry.getProperty("python.io.encoding");
      String errors = registry.getProperty("python.io.errors");
      if (encoding == null) {
         encoding = Py.getConsole().getEncoding();
      }

      ((PyFile)this.stdin).setEncoding(encoding, errors);
      ((PyFile)this.stdout).setEncoding(encoding, errors);
      ((PyFile)this.stderr).setEncoding(encoding, "backslashreplace");
   }

   /** @deprecated */
   @Deprecated
   public void shadow() {
   }

   public static PyObject getDefaultBuiltins() {
      return PySystemState.DefaultBuiltinsHolder.builtins;
   }

   public PyObject getBuiltins() {
      return this.builtins;
   }

   public void setBuiltins(PyObject value) {
      this.builtins = value;
      this.modules.__setitem__((String)"__builtin__", new PyModule("__builtin__", value));
   }

   public PyObject getWarnoptions() {
      return this.warnoptions;
   }

   public void setWarnoptions(PyObject value) {
      this.warnoptions = new PyList(value);
   }

   public PyObject getPlatform() {
      return this.platform;
   }

   public void setPlatform(PyObject value) {
      this.platform = value;
   }

   public WinVersion getwindowsversion() {
      return WinVersion.getWinVersion();
   }

   public synchronized codecs.CodecState getCodecState() {
      if (this.codecState == null) {
         this.codecState = new codecs.CodecState();

         try {
            imp.load("encodings");
         } catch (PyException var2) {
            if (var2.type != Py.ImportError) {
               throw var2;
            }
         }
      }

      return this.codecState;
   }

   public ReentrantLock getImportLock() {
      return this.importLock;
   }

   public ClassLoader getSyspathJavaLoader() {
      return this.syspathJavaLoader;
   }

   public PyObject __findattr_ex__(String name) {
      PyException exc;
      if (name == "exc_value") {
         exc = Py.getThreadState().exception;
         return exc == null ? null : exc.value;
      } else if (name == "exc_type") {
         exc = Py.getThreadState().exception;
         return exc == null ? null : exc.type;
      } else if (name == "exc_traceback") {
         exc = Py.getThreadState().exception;
         return exc == null ? null : exc.traceback;
      } else {
         PyObject ret = super.__findattr_ex__(name);
         if (ret != null) {
            if (!(ret instanceof PyMethod)) {
               if (ret == PyAttributeDeleted.INSTANCE) {
                  return null;
               }

               return ret;
            }

            if (this.__dict__.__finditem__(name) instanceof PyReflectedFunction) {
               return ret;
            }
         }

         return this.__dict__.__finditem__(name);
      }
   }

   public void __setattr__(String name, PyObject value) {
      checkReadOnly(name);
      if (name == "builtins") {
         this.setBuiltins(value);
      } else {
         PyObject ret = this.getType().lookup(name);
         if (ret != null && ret._doset(this, value)) {
            return;
         }

         this.__dict__.__setitem__(name, value);
      }

   }

   public void __delattr__(String name) {
      checkMustExist(name);
      PyObject ret = this.getType().lookup(name);
      if (ret != null) {
         ret._doset(this, PyAttributeDeleted.INSTANCE);
      }

      try {
         this.__dict__.__delitem__(name);
      } catch (PyException var4) {
         if (ret == null) {
            throw Py.AttributeError(name);
         }
      }

   }

   public void __rawdir__(PyDictionary accum) {
      accum.update(this.__dict__);
   }

   public String toString() {
      return "<module '" + this.__name__ + "' (built-in)>";
   }

   public int getrecursionlimit() {
      return this.recursionlimit;
   }

   public void setrecursionlimit(int recursionlimit) {
      if (recursionlimit <= 0) {
         throw Py.ValueError("Recursion limit must be positive");
      } else {
         this.recursionlimit = recursionlimit;
      }
   }

   public PyObject gettrace() {
      ThreadState ts = Py.getThreadState();
      return ts.tracefunc == null ? Py.None : ((PythonTraceFunction)ts.tracefunc).tracefunc;
   }

   public void settrace(PyObject tracefunc) {
      ThreadState ts = Py.getThreadState();
      if (tracefunc == Py.None) {
         ts.tracefunc = null;
      } else {
         ts.tracefunc = new PythonTraceFunction(tracefunc);
      }

   }

   public PyObject getprofile() {
      ThreadState ts = Py.getThreadState();
      return ts.profilefunc == null ? Py.None : ((PythonTraceFunction)ts.profilefunc).tracefunc;
   }

   public void setprofile(PyObject profilefunc) {
      ThreadState ts = Py.getThreadState();
      if (profilefunc == Py.None) {
         ts.profilefunc = null;
      } else {
         ts.profilefunc = new PythonTraceFunction(profilefunc);
      }

   }

   public PyString getdefaultencoding() {
      return new PyString(codecs.getDefaultEncoding());
   }

   public void setdefaultencoding(String encoding) {
      codecs.setDefaultEncoding(encoding);
   }

   public PyObject getfilesystemencoding() {
      return FILE_SYSTEM_ENCODING;
   }

   public PyInteger getcheckinterval() {
      return new PyInteger(this.checkinterval);
   }

   public void setcheckinterval(int interval) {
      this.checkinterval = interval;
   }

   public void setCurrentWorkingDir(String path) {
      this.currentWorkingDir = path;
   }

   public String getCurrentWorkingDir() {
      return this.currentWorkingDir;
   }

   public String getPath(String path) {
      return getPath(this, path);
   }

   public static String getPathLazy(String path) {
      return getPath((PySystemState)null, path);
   }

   private static String getPath(PySystemState sys, String path) {
      if (path != null) {
         path = getFile(sys, path).getAbsolutePath();
      }

      return path;
   }

   public File getFile(String path) {
      return getFile(this, path);
   }

   private static File getFile(PySystemState sys, String path) {
      File file = new File(path);
      if (!file.isAbsolute()) {
         if (sys == null) {
            sys = Py.getSystemState();
         }

         String cwd = sys.getCurrentWorkingDir();
         if (Platform.IS_WINDOWS) {
            file = getWindowsFile(cwd, path);
         } else {
            file = new File(cwd, path);
         }
      }

      return file;
   }

   private static File getWindowsFile(String cwd, String path) {
      if (path.indexOf(47) >= 0) {
         path = path.replace('/', '\\');
      }

      char d = driveLetter(path);
      if (d != 0) {
         return d == driveLetter(cwd) ? new File(cwd, path.substring(2)) : new File(path);
      } else if (path.startsWith("\\")) {
         return driveLetter(cwd) != 0 ? new File(cwd.substring(0, 2), path) : new File(uncShare(cwd), path);
      } else {
         return new File(cwd, path);
      }
   }

   private static char driveLetter(String path) {
      if (path.length() >= 2 && path.charAt(1) == ':') {
         char pathDrive = path.charAt(0);
         if (Character.isLetter(pathDrive)) {
            return Character.toUpperCase(pathDrive);
         }
      }

      return '\u0000';
   }

   private static String uncShare(String path) {
      int n = path.length();
      if (n >= 5 && path.startsWith("\\\\")) {
         int p = path.indexOf(92, 2);
         if (p >= 3 && n > p + 2) {
            int dir = path.indexOf(92, p + 1);
            if (dir < 0) {
               return path;
            }

            if (dir > p + 1) {
               return path.substring(0, dir);
            }
         }
      }

      return null;
   }

   public void callExitFunc() throws PyIgnoreMethodTag {
      PyObject exitfunc = this.__findattr__("exitfunc");
      if (exitfunc != null) {
         try {
            exitfunc.__call__();
         } catch (PyException var3) {
            if (!var3.match(Py.SystemExit)) {
               Py.println(this.stderr, Py.newString("Error in sys.exitfunc:"));
            }

            Py.printException(var3);
         }
      }

      Py.flushLine();
   }

   public ClassLoader getClassLoader() {
      return this.classLoader;
   }

   public void setClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   private static String findRoot(Properties preProperties, Properties postProperties, String jarFileName) {
      String root = null;

      try {
         if (postProperties != null) {
            root = postProperties.getProperty("python.home");
         }

         if (root == null) {
            root = preProperties.getProperty("python.home");
         }

         if (root == null) {
            root = preProperties.getProperty("install.root");
         }

         determinePlatform(preProperties);
      } catch (Exception var9) {
         return null;
      }

      if (root == null || root.equals("")) {
         String classpath = preProperties.getProperty("java.class.path");
         if (classpath != null) {
            String lowerCaseClasspath = classpath.toLowerCase();
            int jarIndex = lowerCaseClasspath.indexOf("jython.jar");
            if (jarIndex < 0) {
               jarIndex = lowerCaseClasspath.indexOf("jython-dev.jar");
            }

            if (jarIndex >= 0) {
               int start = classpath.lastIndexOf(File.pathSeparator, jarIndex) + 1;
               root = classpath.substring(start, jarIndex);
            } else if (jarFileName != null) {
               root = (new File(jarFileName)).getParent();
            }
         }
      }

      if (root == null) {
         return null;
      } else {
         File rootFile = new File(root);

         try {
            return rootFile.getCanonicalPath();
         } catch (IOException var8) {
            return rootFile.getAbsolutePath();
         }
      }
   }

   private static void determinePlatform(Properties props) {
      String version = props.getProperty("java.version");
      if (version == null) {
         version = "???";
      }

      String lversion = version.toLowerCase();
      if (lversion.startsWith("java")) {
         version = version.substring(4, version.length());
      }

      if (lversion.startsWith("jdk") || lversion.startsWith("jre")) {
         version = version.substring(3, version.length());
      }

      if (version.equals("12")) {
         version = "1.2";
      }

      defaultPlatform = new PyShadowString("java" + version, getNativePlatform());
   }

   public static String getNativePlatform() {
      String osname = System.getProperty("os.name");
      if (osname.equals("Linux")) {
         return "linux2";
      } else if (osname.equals("Mac OS X")) {
         return "darwin";
      } else if (osname.toLowerCase().contains("cygwin")) {
         return "cygwin";
      } else {
         return osname.startsWith("Windows") ? "win32" : osname.replaceAll("[\\s/]", "").toLowerCase();
      }
   }

   private static void initRegistry(Properties preProperties, Properties postProperties, boolean standalone, String jarFileName) {
      if (registry != null) {
         Py.writeError("systemState", "trying to reinitialize registry");
      } else {
         registry = preProperties;
         String prefix = findRoot(preProperties, postProperties, jarFileName);
         String exec_prefix = prefix;
         if (prefix != null) {
            if (prefix.length() == 0) {
               exec_prefix = ".";
               prefix = ".";
            }

            try {
               File homeFile = new File(registry.getProperty("user.home"), ".jython");
               addRegistryFile(homeFile);
               addRegistryFile(new File(prefix, "registry"));
            } catch (Exception var8) {
            }
         }

         if (prefix != null) {
            PySystemState.prefix = Py.fileSystemEncode(prefix);
         }

         if (exec_prefix != null) {
            PySystemState.exec_prefix = Py.fileSystemEncode(exec_prefix);
         }

         try {
            String jythonpath = System.getenv("JYTHONPATH");
            if (jythonpath != null) {
               registry.setProperty("python.path", jythonpath);
            }
         } catch (SecurityException var7) {
         }

         registry.putAll(postProperties);
         if (standalone && !registry.containsKey("python.cachedir.skip")) {
            registry.put("python.cachedir.skip", "true");
         }

         if (!registry.containsKey("python.console.encoding")) {
            registry.put("python.console.encoding", getConsoleEncoding(registry));
         }

         Options.setFromRegistry();
      }
   }

   private static String getConsoleEncoding(Properties props) {
      String encoding = props.getProperty("sun.stdout.encoding");
      String os = props.getProperty("os.name");
      if (encoding != null) {
         return encoding;
      } else {
         String output;
         if (os != null && os.startsWith("Windows")) {
            output = getCommandResult("cmd", "/c", "chcp");
            Pattern DIGITS_PATTERN = Pattern.compile("[1-9]\\d+");
            Matcher matcher = DIGITS_PATTERN.matcher(output);
            if (matcher.find()) {
               return "cp".concat(output.substring(matcher.start(), matcher.end()));
            }
         } else {
            output = getCommandResult("locale", "charmap");
            if (output.length() > 0) {
               return output;
            }
         }

         return "utf-8";
      }
   }

   private static void addRegistryFile(File file) {
      if (file.exists()) {
         if (!file.isDirectory()) {
            Properties fileProperties = new Properties();

            try {
               FileInputStream fp = new FileInputStream(file);

               try {
                  fileProperties.load(fp);
                  Iterator var3 = fileProperties.entrySet().iterator();

                  while(var3.hasNext()) {
                     Map.Entry kv = (Map.Entry)var3.next();
                     Object key = kv.getKey();
                     if (!registry.containsKey(key)) {
                        registry.put(key, kv.getValue());
                     }
                  }
               } finally {
                  fp.close();
               }
            } catch (IOException var10) {
               System.err.println("couldn't open registry file: " + file.toString());
            }
         } else {
            System.err.println("warning: " + file.toString() + " is a directory, not a file");
         }
      }

   }

   public static Properties getBaseProperties() {
      try {
         return System.getProperties();
      } catch (AccessControlException var1) {
         return new Properties();
      }
   }

   public static synchronized void initialize() {
      initialize((Properties)null, (Properties)null);
   }

   public static synchronized void initialize(Properties preProperties, Properties postProperties) {
      initialize(preProperties, postProperties, new String[]{""});
   }

   public static synchronized void initialize(Properties preProperties, Properties postProperties, String[] argv) {
      initialize(preProperties, postProperties, argv, (ClassLoader)null);
   }

   public static synchronized void initialize(Properties preProperties, Properties postProperties, String[] argv, ClassLoader classLoader) {
      initialize(preProperties, postProperties, argv, classLoader, new ClassicPyObjectAdapter());
   }

   public static synchronized void initialize(Properties preProperties, Properties postProperties, String[] argv, ClassLoader classLoader, ExtensiblePyObjectAdapter adapter) {
      if (!initialized) {
         if (preProperties == null) {
            preProperties = getBaseProperties();
         }

         if (postProperties == null) {
            postProperties = new Properties();
         }

         try {
            ClassLoader context = Thread.currentThread().getContextClassLoader();
            if (context != null) {
               if (initialize(preProperties, postProperties, argv, classLoader, adapter, context)) {
                  return;
               }
            } else {
               Py.writeDebug("initializer", "Context class loader null, skipping");
            }

            ClassLoader sysStateLoader = PySystemState.class.getClassLoader();
            if (sysStateLoader != null) {
               if (initialize(preProperties, postProperties, argv, classLoader, adapter, sysStateLoader)) {
                  return;
               }
            } else {
               Py.writeDebug("initializer", "PySystemState.class class loader null, skipping");
            }
         } catch (UnsupportedCharsetException var7) {
            Py.writeWarning("initializer", "Unable to load the UTF-8 charset to read an initializer definition");
            var7.printStackTrace(System.err);
         } catch (SecurityException var8) {
         } catch (Exception var9) {
            Py.writeWarning("initializer", "Unexpected exception thrown while trying to use initializer service");
            var9.printStackTrace(System.err);
         }

         doInitialize(preProperties, postProperties, argv, classLoader, adapter);
      }
   }

   private static boolean initialize(Properties pre, Properties post, String[] argv, ClassLoader sysClassLoader, ExtensiblePyObjectAdapter adapter, ClassLoader initializerClassLoader) {
      InputStream in = initializerClassLoader.getResourceAsStream("META-INF/services/org.python.core.JythonInitializer");
      if (in == null) {
         Py.writeDebug("initializer", "'META-INF/services/org.python.core.JythonInitializer' not found on " + initializerClassLoader);
         return false;
      } else {
         BufferedReader r = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));

         String className;
         try {
            className = r.readLine();
         } catch (IOException var13) {
            Py.writeWarning("initializer", "Failed reading 'META-INF/services/org.python.core.JythonInitializer' from " + initializerClassLoader);
            var13.printStackTrace(System.err);
            return false;
         }

         Class initializer;
         try {
            initializer = initializerClassLoader.loadClass(className);
         } catch (ClassNotFoundException var12) {
            Py.writeWarning("initializer", "Specified initializer class '" + className + "' not found, continuing");
            return false;
         }

         try {
            ((JythonInitializer)initializer.newInstance()).initialize(pre, post, argv, sysClassLoader, adapter);
         } catch (Exception var11) {
            Py.writeWarning("initializer", "Failed initializing with class '" + className + "', continuing");
            var11.printStackTrace(System.err);
            return false;
         }

         if (!initialized) {
            Py.writeWarning("initializer", "Initializer '" + className + "' failed to call doInitialize, using default initialization");
         }

         return initialized;
      }
   }

   public static synchronized PySystemState doInitialize(Properties preProperties, Properties postProperties, String[] argv, ClassLoader classLoader, ExtensiblePyObjectAdapter adapter) {
      if (initialized) {
         return Py.defaultSystemState;
      } else {
         initialized = true;
         Py.setAdapter(adapter);
         boolean standalone = false;
         String jarFileName = Py._getJarFileName();
         if (jarFileName != null) {
            standalone = isStandalone(jarFileName);
         }

         initRegistry(preProperties, postProperties, standalone, jarFileName);
         initBuiltins(registry);
         defaultPath = initPath(registry, standalone, jarFileName);
         defaultArgv = initArgv(argv);
         defaultExecutable = initExecutable(registry);
         initPackages(registry);
         initConsole(registry);
         Py.defaultSystemState = new PySystemState();
         Py.setSystemState(Py.defaultSystemState);
         if (classLoader != null) {
            Py.defaultSystemState.setClassLoader(classLoader);
         }

         Py.initClassExceptions(getDefaultBuiltins());
         new PySyntaxError("", 1, 1, "", "");
         Py.defaultSystemState.__setattr__("_jy_console", Py.java2py(Py.getConsole()));
         return Py.defaultSystemState;
      }
   }

   private static PyVersionInfo getVersionInfo() {
      String s;
      if (Version.PY_RELEASE_LEVEL == 10) {
         s = "alpha";
      } else if (Version.PY_RELEASE_LEVEL == 11) {
         s = "beta";
      } else if (Version.PY_RELEASE_LEVEL == 12) {
         s = "candidate";
      } else if (Version.PY_RELEASE_LEVEL == 15) {
         s = "final";
      } else {
         if (Version.PY_RELEASE_LEVEL != 170) {
            throw new RuntimeException("Illegal value for PY_RELEASE_LEVEL: " + Version.PY_RELEASE_LEVEL);
         }

         s = "snapshot";
      }

      return new PyVersionInfo(new PyObject[]{Py.newInteger(Version.PY_MAJOR_VERSION), Py.newInteger(Version.PY_MINOR_VERSION), Py.newInteger(Version.PY_MICRO_VERSION), Py.newString(s), Py.newInteger(Version.PY_RELEASE_SERIAL)});
   }

   public static boolean isPackageCacheEnabled() {
      return cachedir != null;
   }

   private static void initCacheDirectory(Properties props) {
      String skip = props.getProperty("python.cachedir.skip", "false");
      if (skip.equalsIgnoreCase("true")) {
         cachedir = null;
      } else {
         cachedir = new File(props.getProperty("python.cachedir", "cachedir"));
         if (!cachedir.isAbsolute()) {
            String prefixString = prefix == null ? null : Py.fileSystemDecode(prefix);
            cachedir = new File(prefixString, cachedir.getPath());
         }

      }
   }

   private static void initPackages(Properties props) {
      initCacheDirectory(props);
      File pkgdir;
      if (cachedir != null) {
         pkgdir = new File(cachedir, "packages");
      } else {
         pkgdir = null;
      }

      packageManager = new SysPackageManager(pkgdir, props);
   }

   private static PyList initArgv(String[] args) {
      PyList argv = new PyList();
      if (args != null) {
         String[] var2 = args;
         int var3 = args.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String arg = var2[var4];
            argv.append(Py.fileSystemEncode(arg));
         }
      }

      return argv;
   }

   private static PyObject initExecutable(Properties props) {
      String executable = props.getProperty("python.executable");
      File executableFile;
      if (executable != null) {
         executableFile = new File(executable);
      } else {
         if (prefix == null) {
            return Py.None;
         }

         executableFile = new File(Py.fileSystemDecode(prefix), Platform.IS_WINDOWS ? "bin\\jython.exe" : "bin/jython");
      }

      try {
         executableFile = executableFile.getCanonicalFile();
      } catch (IOException var4) {
         executableFile = executableFile.getAbsoluteFile();
      }

      return Py.newStringOrUnicode(executableFile.getPath());
   }

   private static void initConsole(Properties props) {
      String encoding = props.getProperty("python.console.encoding");
      String consoleName = props.getProperty("python.console", "").trim();
      Class consoleType = Console.class;
      if (consoleName.length() > 0 && Py.isInteractive()) {
         try {
            Class consoleClass = Class.forName(consoleName);
            if (!consoleType.isAssignableFrom(consoleClass)) {
               throw new ClassCastException();
            }

            Constructor consoleConstructor = consoleClass.getConstructor(String.class);
            Object consoleObject = consoleConstructor.newInstance(encoding);
            Console console = (Console)consoleType.cast(consoleObject);
            Py.installConsole(console);
            return;
         } catch (NoClassDefFoundError var9) {
            writeConsoleWarning(consoleName, "not found");
         } catch (ClassCastException var10) {
            writeConsoleWarning(consoleName, "does not implement " + consoleType);
         } catch (NoSuchMethodException var11) {
            writeConsoleWarning(consoleName, "has no constructor from String");
         } catch (InvocationTargetException var12) {
            writeConsoleWarning(consoleName, var12.getCause().toString());
         } catch (Exception var13) {
            writeConsoleWarning(consoleName, var13.toString());
         }
      }

      try {
         Py.installConsole(new PlainConsole(encoding));
      } catch (Exception var8) {
         writeConsoleWarning(consoleName, var8.toString());
      }
   }

   private static void writeConsoleWarning(String consoleName, String msg) {
      Py.writeWarning("console", "Failed to install '" + consoleName + "': " + msg + ".");
   }

   private static void addBuiltin(String name) {
      int colon = name.indexOf(58);
      String classname;
      String modname;
      if (colon != -1) {
         modname = name.substring(0, colon).trim();
         classname = name.substring(colon + 1, name.length()).trim();
         if (classname.equals("null")) {
            classname = null;
         }
      } else {
         modname = name.trim();
         classname = "org.python.modules." + modname;
      }

      if (classname != null) {
         builtinNames.put(modname, classname);
      } else {
         builtinNames.remove(modname);
      }

   }

   private static void initBuiltins(Properties props) {
      builtinNames = Generic.map();
      builtinNames.put("__builtin__", "");
      builtinNames.put("sys", "");
      String[] var1 = Setup.builtinModules;
      int var2 = var1.length;

      int n;
      for(n = 0; n < var2; ++n) {
         String builtinModule = var1[n];
         addBuiltin(builtinModule);
      }

      String builtinprop = props.getProperty("python.modules.builtin", "");
      StringTokenizer tok = new StringTokenizer(builtinprop, ",");

      while(tok.hasMoreTokens()) {
         addBuiltin(tok.nextToken());
      }

      n = builtinNames.size();
      PyObject[] built_mod = new PyObject[n];
      int i = 0;

      String key;
      for(Iterator var6 = builtinNames.keySet().iterator(); var6.hasNext(); built_mod[i++] = Py.newString(key)) {
         key = (String)var6.next();
      }

      builtin_module_names = new PyTuple(built_mod);
   }

   public static String getBuiltin(String name) {
      return (String)builtinNames.get(name);
   }

   private static PyList initPath(Properties props, boolean standalone, String jarFileName) {
      PyList path = new PyList();
      addPaths(path, props.getProperty("python.path", ""));
      if (prefix != null) {
         String libpath = (new File(Py.fileSystemDecode(prefix), "Lib")).toString();
         path.append(Py.fileSystemEncode(libpath));
      }

      if (standalone) {
         addPaths(path, jarFileName + "/Lib");
      }

      return path;
   }

   private static boolean isStandalone(String jarFileName) {
      boolean standalone = false;
      if (jarFileName != null) {
         JarFile jarFile = null;

         try {
            jarFile = new JarFile(jarFileName);
            JarEntry jarEntry = jarFile.getJarEntry("Lib/os.py");
            standalone = jarEntry != null;
         } catch (IOException var12) {
         } finally {
            if (jarFile != null) {
               try {
                  jarFile.close();
               } catch (IOException var11) {
               }
            }

         }
      }

      return standalone;
   }

   private static void addPaths(PyList path, String pypath) {
      StringTokenizer tok = new StringTokenizer(pypath, File.pathSeparator);

      while(tok.hasMoreTokens()) {
         path.append(Py.newStringOrUnicode(tok.nextToken().trim()));
      }

   }

   public static PyJavaPackage add_package(String n) {
      return add_package(n, (String)null);
   }

   public static PyJavaPackage add_package(String n, String contents) {
      return packageManager.makeJavaPackage(n, contents, (String)null);
   }

   public static void add_classdir(String directoryPath) {
      packageManager.addDirectory(new File(directoryPath));
   }

   public static void add_extdir(String directoryPath) {
      packageManager.addJarDir(directoryPath, false);
   }

   public static void add_extdir(String directoryPath, boolean cache) {
      packageManager.addJarDir(directoryPath, cache);
   }

   static void displayhook(PyObject o) {
      if (o != Py.None) {
         PyObject currentBuiltins = Py.getSystemState().getBuiltins();
         currentBuiltins.__setitem__("_", Py.None);
         Py.stdout.println((PyObject)o.__repr__());
         currentBuiltins.__setitem__("_", o);
      }
   }

   static void excepthook(PyObject type, PyObject val, PyObject tb) {
      Py.displayException(type, val, tb, (PyObject)null);
   }

   public static void exit(PyObject status) {
      throw new PyException(Py.SystemExit, status);
   }

   public static void exit() {
      exit(Py.None);
   }

   public static PyTuple exc_info() {
      PyException exc = Py.getThreadState().exception;
      if (exc == null) {
         return new PyTuple(new PyObject[]{Py.None, Py.None, Py.None});
      } else {
         PyObject tb = exc.traceback;
         PyObject value = exc.value;
         return new PyTuple(new PyObject[]{exc.type, value == null ? Py.None : value, (PyObject)(tb == null ? Py.None : tb)});
      }
   }

   public static void exc_clear() {
      Py.getThreadState().exception = null;
   }

   public static PyFrame _getframe() {
      return _getframe(-1);
   }

   public static PyFrame _getframe(int depth) {
      PyFrame f;
      for(f = Py.getFrame(); depth > 0 && f != null; --depth) {
         f = f.f_back;
      }

      if (f == null) {
         throw Py.ValueError("call stack is not deep enough");
      } else {
         return f;
      }
   }

   public static PyDictionary _current_frames() {
      return ThreadStateMapping._current_frames();
   }

   public void registerCloser(Callable resourceCloser) {
      this.closer.registerCloser(resourceCloser);
   }

   public boolean unregisterCloser(Callable resourceCloser) {
      return this.closer.unregisterCloser(resourceCloser);
   }

   public void cleanup() {
      this.closer.cleanup();
   }

   public void close() {
      this.cleanup();
   }

   public static String getSystemVersionString() {
      try {
         boolean win = System.getProperty("os.name").startsWith("Windows");
         Process p = Runtime.getRuntime().exec(win ? "cmd.exe /C ver" : "uname -v");
         BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

         String uname_sysver;
         for(uname_sysver = br.readLine(); uname_sysver != null && uname_sysver.length() == 0; uname_sysver = br.readLine()) {
         }

         while(br.readLine() != null) {
         }

         br.close();
         if (p.waitFor() != 0) {
            uname_sysver = "";
         }

         if (win && uname_sysver.length() > 0) {
            int start = uname_sysver.toLowerCase().indexOf("version ");
            if (start != -1) {
               start += 8;
               int end = uname_sysver.length();
               if (uname_sysver.endsWith("]")) {
                  --end;
               }

               uname_sysver = uname_sysver.substring(start, end);
            }
         }

         return uname_sysver;
      } catch (Exception var6) {
         return "";
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.argv != null) {
         retVal = visit.visit(this.argv, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.modules != null) {
         retVal = visit.visit(this.modules, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.path != null) {
         retVal = visit.visit(this.path, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.warnoptions != null) {
         retVal = visit.visit(this.warnoptions, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.builtins != null) {
         retVal = visit.visit(this.builtins, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.platform != null) {
         retVal = visit.visit(this.platform, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.meta_path != null) {
         retVal = visit.visit(this.meta_path, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.path_hooks != null) {
         retVal = visit.visit(this.path_hooks, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.path_importer_cache != null) {
         retVal = visit.visit(this.path_importer_cache, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.ps1 != null) {
         retVal = visit.visit(this.ps1, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.ps2 != null) {
         retVal = visit.visit(this.ps2, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.executable != null) {
         retVal = visit.visit(this.executable, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.stdout != null) {
         retVal = visit.visit(this.stdout, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.stderr != null) {
         retVal = visit.visit(this.stderr, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.stdin != null) {
         retVal = visit.visit(this.stdin, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__stdout__ != null) {
         retVal = visit.visit(this.__stdout__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__stderr__ != null) {
         retVal = visit.visit(this.__stderr__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__stdin__ != null) {
         retVal = visit.visit(this.__stdin__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__displayhook__ != null) {
         retVal = visit.visit(this.__displayhook__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__excepthook__ != null) {
         retVal = visit.visit(this.__excepthook__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.last_value != null) {
         retVal = visit.visit(this.last_value, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.last_type != null) {
         retVal = visit.visit(this.last_type, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.last_traceback != null) {
         retVal = visit.visit(this.last_traceback, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__name__ != null) {
         retVal = visit.visit(this.__name__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.__dict__ == null ? 0 : visit.visit(this.__dict__, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.argv || ob == this.modules || ob == this.path || ob == this.warnoptions || ob == this.builtins || ob == this.platform || ob == this.meta_path || ob == this.path_hooks || ob == this.path_importer_cache || ob == this.ps1 || ob == this.ps2 || ob == this.executable || ob == this.stdout || ob == this.stderr || ob == this.stdin || ob == this.__stdout__ || ob == this.__stderr__ || ob == this.__stdin__ || ob == this.__displayhook__ || ob == this.__excepthook__ || ob == this.last_value || ob == this.last_type || ob == this.last_traceback || ob == this.__name__ || ob == this.__dict__);
   }

   static {
      hexversion = Version.PY_MAJOR_VERSION << 24 | Version.PY_MINOR_VERSION << 16 | Version.PY_MICRO_VERSION << 8 | Version.PY_RELEASE_LEVEL << 4 | Version.PY_RELEASE_SERIAL << 0;
      version_info = getVersionInfo();
      float_repr_style = Py.newString("short");
      FILE_SYSTEM_ENCODING = Py.newString("utf-8");
      py3kwarning = false;
      flags = Options.class;
      _mercurial = new PyTuple(new PyObject[]{Py.newString("Jython"), Py.newString(Version.getHGIdentifier()), Py.newString(Version.getHGVersion())});
      copyright = Py.newString("Copyright (c) 2000-2017 Jython Developers.\nAll rights reserved.\n\nCopyright (c) 2000 BeOpen.com.\nAll Rights Reserved.\n\nCopyright (c) 2000 The Apache Software Foundation.\nAll rights reserved.\n\nCopyright (c) 1995-2000 Corporation for National Research Initiatives.\nAll Rights Reserved.\n\nCopyright (c) 1991-1995 Stichting Mathematisch Centrum, Amsterdam.\nAll Rights Reserved.");
      builtin_module_names = null;
      exec_prefix = Py.EmptyString;
      byteorder = new PyString("big");
      initialized = false;
      defaultPlatform = new PyShadowString("java", getNativePlatform());
      systemStateQueue = new ReferenceQueue();
      sysClosers = Generic.concurrentMap();
      float_info = FloatInfo.getInfo();
      long_info = LongInfo.getInfo();
   }

   public static class PySystemStateCloser {
      private final Set resourceClosers;
      private volatile boolean isCleanup;
      private final Thread shutdownHook;

      private PySystemStateCloser(PySystemState sys) {
         this.resourceClosers = Collections.synchronizedSet(new LinkedHashSet());
         this.isCleanup = false;
         this.shutdownHook = this.initShutdownCloser();
         WeakReference ref = new WeakReference(sys, PySystemState.systemStateQueue);
         PySystemState.sysClosers.put(ref, this);
         cleanupOtherClosers();
      }

      private static void cleanupOtherClosers() {
         Reference ref;
         while((ref = PySystemState.systemStateQueue.poll()) != null) {
            PySystemStateCloser closer = (PySystemStateCloser)PySystemState.sysClosers.get(ref);
            PySystemState.sysClosers.remove(ref);
            closer.cleanup();
         }

      }

      private void registerCloser(Callable closer) {
         if (!this.isCleanup) {
            this.resourceClosers.add(closer);
         }

      }

      private boolean unregisterCloser(Callable closer) {
         return this.resourceClosers.remove(closer);
      }

      private synchronized void cleanup() {
         if (!this.isCleanup) {
            this.isCleanup = true;
            if (this.shutdownHook != null) {
               try {
                  Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
               } catch (IllegalStateException var2) {
               }
            }

            this.runClosers();
            this.resourceClosers.clear();
            this.isCleanup = false;
         }
      }

      private synchronized void runClosers() {
         if (this.resourceClosers != null) {
            LinkedList rc = new LinkedList(this.resourceClosers);
            Iterator iter = rc.descendingIterator();

            while(iter.hasNext()) {
               Callable callable = (Callable)iter.next();

               try {
                  callable.call();
               } catch (Exception var5) {
               }
            }
         }

      }

      private Thread initShutdownCloser() {
         try {
            Thread shutdownHook = new Thread(new ShutdownCloser(this), "Jython Shutdown Closer");
            Runtime.getRuntime().addShutdownHook(shutdownHook);
            return shutdownHook;
         } catch (SecurityException var2) {
            Py.writeDebug("PySystemState", "Can't register cleanup closer hook");
            return null;
         }
      }

      // $FF: synthetic method
      PySystemStateCloser(PySystemState x0, Object x1) {
         this(x0);
      }

      private class ShutdownCloser implements Runnable {
         PySystemStateCloser closer = null;

         public ShutdownCloser(PySystemStateCloser closer) {
            this.closer = closer;
         }

         public void run() {
            synchronized(this.closer) {
               PySystemStateCloser.this.runClosers();
               PySystemStateCloser.this.resourceClosers.clear();
            }
         }
      }
   }

   private static class DefaultBuiltinsHolder {
      static final PyObject builtins = fillin();

      static PyObject fillin() {
         PyObject temp = new PyStringMap();
         __builtin__.fillWithBuiltins(temp);
         return temp;
      }
   }
}
