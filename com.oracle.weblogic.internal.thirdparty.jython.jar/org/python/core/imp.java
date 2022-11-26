package org.python.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.python.antlr.base.mod;
import org.python.compiler.Module;
import org.python.core.util.FileUtil;
import org.python.core.util.PlatformUtil;

public class imp {
   private static final String patched = "PATCHED";
   private static final String IMPORT_LOG = "import";
   private static final String UNKNOWN_SOURCEFILE = "<unknown>";
   private static final int APIVersion = 37;
   public static final int NO_MTIME = -1;
   public static final int DEFAULT_LEVEL = -1;
   private static final boolean IS_OSX = PySystemState.getNativePlatform().equals("darwin");
   private static final PyObject nonEmptyFromlist = new PyTuple(new PyObject[]{Py.newString("__doc__")});
   private static final PyTuple all = new PyTuple(new PyObject[]{Py.newString('*')});

   public static ClassLoader getSyspathJavaLoader() {
      return Py.getSystemState().getSyspathJavaLoader();
   }

   public static ClassLoader getParentClassLoader() {
      ClassLoader current = imp.class.getClassLoader();
      ClassLoader context = Thread.currentThread().getContextClassLoader();
      if (context == current) {
         return current;
      } else if (context == null) {
         return current;
      } else if (current == null) {
         return context;
      } else if (isParentClassLoader(context, current)) {
         return current;
      } else {
         return isParentClassLoader(current, context) ? context : current;
      }
   }

   private static boolean isParentClassLoader(ClassLoader suspectedParent, ClassLoader child) {
      try {
         ClassLoader parent = child.getParent();
         if (suspectedParent == parent) {
            return true;
         } else {
            return parent != null && parent != child ? isParentClassLoader(suspectedParent, parent) : false;
         }
      } catch (SecurityException var3) {
         return false;
      }
   }

   private imp() {
   }

   public static PyModule addModule(String name) {
      name = name.intern();
      PyObject modules = Py.getSystemState().modules;
      PyModule module = (PyModule)modules.__finditem__(name);
      if (module != null) {
         return module;
      } else {
         module = new PyModule(name, (PyObject)null);
         PyModule __builtin__ = (PyModule)modules.__finditem__("__builtin__");
         PyObject __dict__ = module.__getattr__("__dict__");
         __dict__.__setitem__("__builtins__", __builtin__.__getattr__("__dict__"));
         __dict__.__setitem__("__package__", Py.None);
         modules.__setitem__((String)name, module);
         return module;
      }
   }

   private static void removeModule(String name) {
      name = name.intern();
      PyObject modules = Py.getSystemState().modules;
      if (modules.__finditem__(name) != null) {
         try {
            modules.__delitem__(name);
         } catch (PyException var3) {
            if (!var3.match(Py.KeyError)) {
               throw var3;
            }
         }
      }

   }

   private static byte[] readBytes(InputStream fp) {
      byte[] var1;
      try {
         var1 = FileUtil.readBytes(fp);
      } catch (IOException var10) {
         throw Py.IOError(var10);
      } finally {
         try {
            fp.close();
         } catch (IOException var9) {
            throw Py.IOError(var9);
         }
      }

      return var1;
   }

   private static InputStream makeStream(File file) {
      try {
         return new FileInputStream(file);
      } catch (IOException var2) {
         throw Py.IOError(var2);
      }
   }

   static PyObject createFromPyClass(String name, InputStream fp, boolean testing, String sourceName, String compiledName) {
      return createFromPyClass(name, fp, testing, sourceName, compiledName, -1L);
   }

   static PyObject createFromPyClass(String name, InputStream fp, boolean testing, String sourceName, String compiledName, long mtime) {
      return createFromPyClass(name, fp, testing, sourceName, compiledName, mtime, imp.CodeImport.source);
   }

   static PyObject createFromPyClass(String name, InputStream fp, boolean testing, String sourceName, String compiledName, long mtime, CodeImport source) {
      CodeData data = null;

      try {
         data = readCodeData(compiledName, fp, testing, mtime);
      } catch (IOException var12) {
         if (!testing) {
            throw Py.ImportError(var12.getMessage() + "[name=" + name + ", source=" + sourceName + ", compiled=" + compiledName + "]");
         }
      }

      if (testing && data == null) {
         return null;
      } else {
         PyCode code;
         try {
            code = BytecodeLoader.makeCode(name + "$py", data.getBytes(), source == imp.CodeImport.compiled_only ? data.getFilename() : sourceName);
         } catch (Throwable var11) {
            if (testing) {
               return null;
            }

            throw Py.JavaError(var11);
         }

         Py.writeComment("import", String.format("import %s # precompiled from %s", name, compiledName));
         return createFromCode(name, code, compiledName);
      }
   }

   public static byte[] readCode(String name, InputStream fp, boolean testing) throws IOException {
      return readCode(name, fp, testing, -1L);
   }

   public static byte[] readCode(String name, InputStream fp, boolean testing, long mtime) throws IOException {
      CodeData data = readCodeData(name, fp, testing, mtime);
      return data == null ? null : data.getBytes();
   }

   public static CodeData readCodeData(String name, InputStream fp, boolean testing) throws IOException {
      return readCodeData(name, fp, testing, -1L);
   }

   public static CodeData readCodeData(String name, InputStream fp, boolean testing, long mtime) throws IOException {
      byte[] data = readBytes(fp);
      AnnotationReader ar = new AnnotationReader(data);
      int api = ar.getVersion();
      if (api != 37) {
         if (testing) {
            return null;
         } else {
            String fmt = "compiled unit contains version %d code (%d required): %.200s";
            throw Py.ImportError(String.format(fmt, api, 37, name));
         }
      } else {
         if (testing && mtime != -1L) {
            long time = ar.getMTime();
            if (mtime != time) {
               return null;
            }
         }

         return new CodeData(data, mtime, ar.getFilename());
      }
   }

   public static byte[] compileSource(String name, File file) {
      return compileSource(name, (File)file, (String)null);
   }

   public static byte[] compileSource(String name, File file, String sourceFilename) {
      return compileSource(name, file, sourceFilename, (String)null);
   }

   public static byte[] compileSource(String name, File file, String sourceFilename, String compiledFilename) {
      if (sourceFilename == null) {
         sourceFilename = file.toString();
      }

      long mtime = file.lastModified();
      return compileSource(name, makeStream(file), sourceFilename, mtime);
   }

   public static String makeCompiledFilename(String filename) {
      return filename.substring(0, filename.length() - 3) + "$py.class";
   }

   public static String cacheCompiledSource(String sourceFilename, String compiledFilename, byte[] compiledSource) {
      if (compiledFilename == null) {
         if (sourceFilename == null || sourceFilename.equals("<unknown>")) {
            return null;
         }

         compiledFilename = makeCompiledFilename(sourceFilename);
      }

      FileOutputStream fop = null;

      String var5;
      try {
         SecurityManager man = System.getSecurityManager();
         if (man != null) {
            man.checkWrite(compiledFilename);
         }

         fop = new FileOutputStream(compiledFilename);
         FileUtil.setOwnerOnlyPermissions(new File(compiledFilename));
         fop.write(compiledSource);
         fop.close();
         var5 = compiledFilename;
         return var5;
      } catch (IOException var17) {
         Py.writeDebug("import", "Unable to write to source cache file '" + compiledFilename + "' due to " + var17);
         var5 = null;
         return var5;
      } catch (SecurityException var18) {
         Py.writeDebug("import", "Unable to write to source cache file '" + compiledFilename + "' due to " + var18);
         var5 = null;
      } finally {
         if (fop != null) {
            try {
               fop.close();
            } catch (IOException var16) {
               Py.writeDebug("import", "Unable to close source cache file '" + compiledFilename + "' due to " + var16);
            }
         }

      }

      return var5;
   }

   public static byte[] compileSource(String name, InputStream fp, String filename) {
      return compileSource(name, fp, filename, -1L);
   }

   public static byte[] compileSource(String name, InputStream fp, String filename, long mtime) {
      ByteArrayOutputStream ofp = new ByteArrayOutputStream();

      try {
         if (filename == null) {
            filename = "<unknown>";
         }

         mod node;
         try {
            node = ParserFacade.parse(fp, CompileMode.exec, filename, new CompilerFlags());
         } finally {
            fp.close();
         }

         Module.compile(node, ofp, name + "$py", filename, true, false, (CompilerFlags)null, mtime);
         return ofp.toByteArray();
      } catch (Throwable var11) {
         throw ParserFacade.fixParseError((ParserFacade.ExpectedEncodingBufferedReader)null, var11, filename);
      }
   }

   public static PyObject createFromSource(String name, InputStream fp, String filename) {
      return createFromSource(name, fp, filename, (String)null, -1L);
   }

   public static PyObject createFromSource(String name, InputStream fp, String filename, String outFilename) {
      return createFromSource(name, fp, filename, outFilename, -1L);
   }

   public static PyObject createFromSource(String name, InputStream fp, String filename, String outFilename, long mtime) {
      byte[] bytes = compileSource(name, fp, filename, mtime);
      if (!Py.getSystemState().dont_write_bytecode) {
         cacheCompiledSource(filename, outFilename, bytes);
      }

      Py.writeComment("import", "'" + name + "' as " + filename);
      PyCode code = BytecodeLoader.makeCode(name + "$py", bytes, filename);
      return createFromCode(name, code, filename);
   }

   public static PyObject createFromCode(String name, PyCode c) {
      return createFromCode(name, c, (String)null);
   }

   public static PyObject createFromCode(String name, PyCode c, String moduleLocation) {
      PyUnicode.checkEncoding(name);
      PyModule module = addModule(name);
      PyBaseCode code = null;
      if (c instanceof PyBaseCode) {
         code = (PyBaseCode)c;
      }

      if (moduleLocation != null) {
         module.__setattr__("__file__", Py.fileSystemEncode(moduleLocation));
      } else if (module.__findattr__("__file__") == null) {
         Py.writeDebug("import", String.format("Warning: %s __file__ is unknown", name));
      }

      ReentrantLock importLock = Py.getSystemState().getImportLock();
      importLock.lock();

      PyModule var7;
      try {
         PyFrame f = new PyFrame(code, module.__dict__, module.__dict__, (PyObject)null);
         code.call(Py.getThreadState(), f);
         var7 = module;
      } catch (RuntimeException var11) {
         removeModule(name);
         throw var11;
      } finally {
         importLock.unlock();
      }

      return var7;
   }

   static PyObject createFromClass(String name, Class c) {
      if (PyRunnable.class.isAssignableFrom(c)) {
         try {
            if (ContainsPyBytecode.class.isAssignableFrom(c)) {
               try {
                  BytecodeLoader.fixPyBytecode(c);
               } catch (NoSuchFieldException var3) {
                  throw Py.JavaError(var3);
               } catch (IOException var4) {
                  throw Py.JavaError(var4);
               } catch (ClassNotFoundException var5) {
                  throw Py.JavaError(var5);
               }
            }

            return createFromCode(name, ((PyRunnable)c.newInstance()).getMain());
         } catch (InstantiationException var6) {
            throw Py.JavaError(var6);
         } catch (IllegalAccessException var7) {
            throw Py.JavaError(var7);
         }
      } else {
         return PyType.fromClass(c, false);
      }
   }

   public static PyObject getImporter(PyObject p) {
      PySystemState sys = Py.getSystemState();
      return getPathImporter(sys.path_importer_cache, sys.path_hooks, p);
   }

   static PyObject getPathImporter(PyObject cache, PyList hooks, PyObject p) {
      PyObject importer = cache.__finditem__(p);
      if (importer != null) {
         return (PyObject)importer;
      } else {
         PyObject iter = hooks.__iter__();

         PyObject hook;
         while((hook = iter.__iternext__()) != null) {
            try {
               importer = hook.__call__(p);
               break;
            } catch (PyException var8) {
               if (!var8.match(Py.ImportError)) {
                  throw var8;
               }
            }
         }

         if (importer == null) {
            try {
               importer = new PyNullImporter(p);
            } catch (PyException var7) {
               if (!var7.match(Py.ImportError)) {
                  throw var7;
               }
            }
         }

         if (importer != null) {
            cache.__setitem__((PyObject)p, (PyObject)importer);
         } else {
            importer = Py.None;
         }

         return (PyObject)importer;
      }
   }

   static PyObject find_module(String name, String moduleName, PyList path) {
      PyObject loader = Py.None;
      PySystemState sys = Py.getSystemState();
      PyObject metaPath = sys.meta_path;
      Iterator var6 = metaPath.asIterable().iterator();

      do {
         PyObject p;
         if (!var6.hasNext()) {
            PyObject ret = loadBuiltin(moduleName);
            if (ret != null) {
               return ret;
            }

            path = path == null ? sys.path : path;

            for(int i = 0; i < path.__len__(); ++i) {
               p = path.__getitem__(i);
               PyObject importer = getPathImporter(sys.path_importer_cache, sys.path_hooks, p);
               if (importer != Py.None) {
                  PyObject findModule = importer.__getattr__("find_module");
                  loader = findModule.__call__(new PyObject[]{new PyString(moduleName)});
                  if (loader != Py.None) {
                     return loadFromLoader(loader, moduleName);
                  }
               }

               ret = loadFromSource(sys, name, moduleName, Py.fileSystemDecode(p));
               if (ret != null) {
                  return ret;
               }
            }

            return ret;
         }

         PyObject importer = (PyObject)var6.next();
         p = importer.__getattr__("find_module");
         loader = p.__call__(new PyObject[]{new PyString(moduleName), (PyObject)(path == null ? Py.None : path)});
      } while(loader == Py.None);

      return loadFromLoader(loader, moduleName);
   }

   private static PyObject loadBuiltin(String name) {
      if (name == "sys") {
         Py.writeComment("import", "'" + name + "' as sys in builtin modules");
         return Py.java2py(Py.getSystemState());
      } else if (name == "__builtin__") {
         Py.writeComment("import", "'" + name + "' as __builtin__ in builtin modules");
         return new PyModule("__builtin__", Py.getSystemState().builtins);
      } else {
         String mod = PySystemState.getBuiltin(name);
         if (mod != null) {
            Class c = Py.findClassEx(mod, "builtin modules");
            if (c != null) {
               Py.writeComment("import", "'" + name + "' as " + mod + " in builtin modules");

               try {
                  if (PyObject.class.isAssignableFrom(c)) {
                     return PyType.fromClass(c);
                  }

                  return createFromClass(name, c);
               } catch (NoClassDefFoundError var4) {
                  throw Py.ImportError("Cannot import " + name + ", missing class " + c.getName());
               }
            }
         }

         return null;
      }
   }

   static PyObject loadFromLoader(PyObject importer, String name) {
      PyUnicode.checkEncoding(name);
      PyObject load_module = importer.__getattr__("load_module");
      ReentrantLock importLock = Py.getSystemState().getImportLock();
      importLock.lock();

      PyObject var4;
      try {
         var4 = load_module.__call__(new PyObject[]{new PyString(name)});
      } finally {
         importLock.unlock();
      }

      return var4;
   }

   public static PyObject loadFromCompiled(String name, InputStream stream, String sourceName, String compiledName) {
      return createFromPyClass(name, stream, false, sourceName, compiledName);
   }

   static PyObject loadFromSource(PySystemState sys, String name, String modName, String entry) {
      String dirName = sys.getPath(entry);
      String sourceName = "__init__.py";
      String compiledName = makeCompiledFilename(sourceName);
      String displayDirName = entry.equals("") ? null : entry;
      String displaySourceName = (new File(new File(displayDirName, name), sourceName)).getPath();
      String displayCompiledName = (new File(new File(displayDirName, name), compiledName)).getPath();
      File dir = new File(dirName, name);
      File sourceFile = new File(dir, sourceName);
      File compiledFile = new File(dir, compiledName);
      boolean pkg = false;

      try {
         if (dir.isDirectory()) {
            if (!caseok(dir, name) || !sourceFile.isFile() && !compiledFile.isFile()) {
               String printDirName = PyString.encode_UnicodeEscape(displayDirName, '\'');
               Py.warning(Py.ImportWarning, String.format("Not importing directory %s: missing __init__.py", printDirName));
            } else {
               pkg = true;
            }
         }
      } catch (SecurityException var20) {
      }

      if (!pkg) {
         Py.writeDebug("import", "trying source " + dir.getPath());
         sourceName = name + ".py";
         compiledName = makeCompiledFilename(sourceName);
         displaySourceName = (new File(displayDirName, sourceName)).getPath();
         displayCompiledName = (new File(displayDirName, compiledName)).getPath();
         sourceFile = new File(dirName, sourceName);
         compiledFile = new File(dirName, compiledName);
      } else {
         PyModule m = addModule(modName);
         PyObject filename = Py.newStringOrUnicode((new File(displayDirName, name)).getPath());
         m.__dict__.__setitem__((String)"__path__", new PyList(new PyObject[]{filename}));
      }

      try {
         if (sourceFile.isFile() && caseok(sourceFile, sourceName)) {
            long pyTime = sourceFile.lastModified();
            if (compiledFile.isFile() && caseok(compiledFile, compiledName)) {
               Py.writeDebug("import", "trying precompiled " + compiledFile.getPath());
               long classTime = compiledFile.lastModified();
               if (classTime >= pyTime) {
                  PyObject ret = createFromPyClass(modName, makeStream(compiledFile), true, displaySourceName, displayCompiledName, pyTime);
                  if (ret != null) {
                     return ret;
                  }
               }

               return createFromSource(modName, makeStream(sourceFile), displaySourceName, compiledFile.getPath(), pyTime);
            }

            return createFromSource(modName, makeStream(sourceFile), displaySourceName, compiledFile.getPath(), pyTime);
         }

         Py.writeDebug("import", "trying precompiled with no source " + compiledFile.getPath());
         if (compiledFile.isFile() && caseok(compiledFile, compiledName)) {
            return createFromPyClass(modName, makeStream(compiledFile), false, displaySourceName, displayCompiledName, -1L, imp.CodeImport.compiled_only);
         }
      } catch (SecurityException var19) {
      }

      return null;
   }

   public static boolean caseok(File file, String filename) {
      if (!Options.caseok && PlatformUtil.isCaseInsensitive()) {
         try {
            File canFile = new File(file.getCanonicalPath());
            boolean match = filename.regionMatches(0, canFile.getName(), 0, filename.length());
            if (!match) {
               File parent = file.getParentFile();
               String[] children = parent.list();
               String[] var6 = children;
               int var7 = children.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String c = var6[var8];
                  if (c.equals(filename)) {
                     return true;
                  }
               }
            }

            return match;
         } catch (IOException var10) {
            return false;
         }
      } else {
         return true;
      }
   }

   public static PyObject load(String name) {
      PyUnicode.checkEncoding(name);
      ReentrantLock importLock = Py.getSystemState().getImportLock();
      importLock.lock();

      PyObject var2;
      try {
         var2 = import_first(name, new StringBuilder());
      } finally {
         importLock.unlock();
      }

      return var2;
   }

   private static String get_parent(PyObject dict, int level) {
      int orig_level = level;
      if ((dict != null || level != -1) && level != 0) {
         PyObject tmp = dict.__finditem__("__package__");
         String modname;
         int dot;
         if (tmp != null && tmp != Py.None) {
            if (!Py.isInstance(tmp, PyString.TYPE)) {
               throw Py.ValueError("__package__ set to non-string");
            }

            modname = ((PyString)tmp).getString();
         } else {
            tmp = dict.__finditem__("__name__");
            if (tmp == null) {
               return null;
            }

            modname = tmp.toString();
            tmp = dict.__finditem__("__path__");
            if (tmp instanceof PyList) {
               dict.__setitem__((String)"__package__", new PyString(modname));
            } else {
               dot = modname.lastIndexOf(46);
               if (dot == -1) {
                  if (level <= -1) {
                     dict.__setitem__("__package__", Py.None);
                     return null;
                  }

                  throw Py.ValueError("Attempted relative import in non-package");
               }

               modname = modname.substring(0, dot);
               dict.__setitem__((String)"__package__", new PyString(modname));
            }
         }

         while(level-- > 1) {
            dot = modname.lastIndexOf(46);
            if (dot == -1) {
               throw Py.ValueError("Attempted relative import beyond toplevel package");
            }

            modname = modname.substring(0, dot);
         }

         if (Py.getSystemState().modules.__finditem__(modname) == null) {
            if (orig_level >= 1) {
               throw Py.SystemError(String.format("Parent module '%.200s' not loaded, cannot perform relative import", modname));
            }

            if (modname.length() > 0) {
               Py.warning(Py.RuntimeWarning, String.format("Parent module '%.200s' not found while handling absolute import", modname));
            }
         }

         return modname.intern();
      } else {
         return null;
      }
   }

   private static PyObject import_next(PyObject mod, StringBuilder parentNameBuffer, String name, String outerFullName, PyObject fromlist) {
      if (parentNameBuffer.length() > 0 && name != null && name.length() > 0) {
         parentNameBuffer.append('.');
      }

      parentNameBuffer.append(name);
      String fullName = parentNameBuffer.toString().intern();
      PyObject modules = Py.getSystemState().modules;
      PyObject ret = modules.__finditem__(fullName);
      if (ret != null) {
         return ret;
      } else {
         if (mod == null) {
            ret = find_module(fullName, name, (PyList)null);
         } else {
            ret = mod.impAttr(name.intern());
         }

         if (ret != null && ret != Py.None) {
            if (modules.__finditem__(fullName) == null) {
               modules.__setitem__(fullName, ret);
            } else {
               ret = modules.__finditem__(fullName);
            }

            if (IS_OSX && fullName.equals("setuptools.command")) {
               load("_fix_jython_setuptools_osx");
            }

            return ret;
         } else {
            if (JavaImportHelper.tryAddPackage(outerFullName, fromlist)) {
               ret = modules.__finditem__(fullName);
            }

            return ret;
         }
      }
   }

   private static PyObject import_first(String name, StringBuilder parentNameBuffer) {
      PyObject ret = import_next((PyObject)null, parentNameBuffer, name, (String)null, (PyObject)null);
      if (ret != null && ret != Py.None) {
         return ret;
      } else {
         throw Py.ImportError("No module named " + name);
      }
   }

   private static PyObject import_first(String name, StringBuilder parentNameBuffer, String fullName, PyObject fromlist) {
      PyObject ret = import_next((PyObject)null, parentNameBuffer, name, fullName, fromlist);
      if ((ret == null || ret == Py.None) && JavaImportHelper.tryAddPackage(fullName, fromlist)) {
         ret = import_next((PyObject)null, parentNameBuffer, name, fullName, fromlist);
      }

      if (ret != null && ret != Py.None) {
         return ret;
      } else {
         throw Py.ImportError("No module named " + name);
      }
   }

   private static PyObject import_logic(PyObject mod, StringBuilder parentNameBuffer, String dottedName, String fullName, PyObject fromlist) {
      int dot = false;
      int last_dot = 0;

      int dot;
      do {
         dot = dottedName.indexOf(46, last_dot);
         String name;
         if (dot == -1) {
            name = dottedName.substring(last_dot);
         } else {
            name = dottedName.substring(last_dot, dot);
         }

         PyJavaPackage jpkg = null;
         if (mod instanceof PyJavaPackage) {
            jpkg = (PyJavaPackage)mod;
         }

         mod = import_next(mod, parentNameBuffer, name, fullName, fromlist);
         if (jpkg != null && (mod == null || mod == Py.None)) {
            mod = import_next(jpkg, parentNameBuffer, name, fullName, fromlist);
         }

         if (mod == null || mod == Py.None) {
            throw Py.ImportError("No module named " + name);
         }

         last_dot = dot + 1;
      } while(dot != -1);

      return mod;
   }

   private static PyObject import_module_level(String name, boolean top, PyObject modDict, PyObject fromlist, int level) {
      PyObject moduleName;
      if (name.length() == 0) {
         if (level == 0 || modDict == null) {
            throw Py.ValueError("Empty module name");
         }

         moduleName = modDict.__findattr__("__name__");
         if (moduleName != null && moduleName.toString().equals("__name__")) {
            throw Py.ValueError("Attempted relative import in non-package");
         }
      }

      moduleName = Py.getSystemState().modules;
      PyObject pkgMod = null;
      String pkgName = null;
      if (modDict != null && modDict.isMappingType()) {
         pkgName = get_parent(modDict, level);
         pkgMod = moduleName.__finditem__(pkgName);
         if (pkgMod != null && !(pkgMod instanceof PyModule)) {
            pkgMod = null;
         }
      }

      int dot = name.indexOf(46);
      String firstName;
      if (dot == -1) {
         firstName = name;
      } else {
         firstName = name.substring(0, dot);
      }

      StringBuilder parentNameBuffer = new StringBuilder(pkgMod != null ? pkgName : "");
      PyObject topMod = import_next(pkgMod, parentNameBuffer, firstName, name, fromlist);
      if (topMod == Py.None || topMod == null) {
         parentNameBuffer = new StringBuilder("");
         if (level > 0) {
            topMod = import_first(pkgName + "." + firstName, parentNameBuffer, name, fromlist);
         } else {
            topMod = import_first(firstName, parentNameBuffer, name, fromlist);
         }
      }

      PyObject mod = topMod;
      if (dot != -1) {
         mod = import_logic(topMod, parentNameBuffer, name.substring(dot + 1), name, fromlist);
      }

      if (top) {
         return topMod;
      } else {
         if (fromlist != null && fromlist != Py.None) {
            ensureFromList(mod, fromlist, name);
         }

         return mod;
      }
   }

   private static void checkNotFile(String name) {
      if (name.indexOf(File.separatorChar) != -1) {
         throw Py.ImportError("Import by filename is not supported.");
      }
   }

   private static void ensureFromList(PyObject mod, PyObject fromlist, String name) {
      ensureFromList(mod, fromlist, name, false);
   }

   private static void ensureFromList(PyObject mod, PyObject fromlist, String name, boolean recursive) {
      if (name.length() == 0) {
         name = mod.__findattr__("__name__").toString();
      }

      StringBuilder modNameBuffer = new StringBuilder(name);
      Iterator var5 = fromlist.asIterable().iterator();

      while(true) {
         PyObject item;
         while(true) {
            if (!var5.hasNext()) {
               return;
            }

            item = (PyObject)var5.next();
            if (!Py.isInstance(item, PyBaseString.TYPE)) {
               throw Py.TypeError("Item in ``from list'' not a string");
            }

            if (!item.toString().equals("*")) {
               break;
            }

            if (!recursive) {
               PyObject all;
               if ((all = mod.__findattr__("__all__")) != null) {
                  ensureFromList(mod, all, name, true);
               }
               break;
            }
         }

         if (mod.__findattr__((PyString)item) == null) {
            String fullName = modNameBuffer.toString() + "." + item.toString();
            import_next(mod, modNameBuffer, item.toString(), fullName, (PyObject)null);
         }
      }
   }

   public static PyObject importName(String name, boolean top) {
      checkNotFile(name);
      PyUnicode.checkEncoding(name);
      ReentrantLock importLock = Py.getSystemState().getImportLock();
      importLock.lock();

      PyObject var3;
      try {
         var3 = import_module_level(name, top, (PyObject)null, (PyObject)null, -1);
      } finally {
         importLock.unlock();
      }

      return var3;
   }

   public static PyObject importName(String name, boolean top, PyObject modDict, PyObject fromlist, int level) {
      checkNotFile(name);
      PyUnicode.checkEncoding(name);
      ReentrantLock importLock = Py.getSystemState().getImportLock();
      importLock.lock();

      PyObject var6;
      try {
         var6 = import_module_level(name, top, modDict, fromlist, level);
      } finally {
         importLock.unlock();
      }

      return var6;
   }

   /** @deprecated */
   @Deprecated
   public static PyObject importOne(String mod, PyFrame frame) {
      return importOne(mod, frame, -1);
   }

   public static PyObject importOne(String mod, PyFrame frame, int level) {
      PyObject module = __builtin__.__import__(mod, frame.f_globals, frame.getLocals(), Py.None, level);
      return module;
   }

   /** @deprecated */
   @Deprecated
   public static PyObject importOneAs(String mod, PyFrame frame) {
      return importOneAs(mod, frame, -1);
   }

   public static PyObject importOneAs(String mod, PyFrame frame, int level) {
      PyObject module = __builtin__.__import__(mod, frame.f_globals, frame.getLocals(), Py.None, level);

      int dot2;
      for(int dot = mod.indexOf(46); dot != -1; dot = dot2) {
         dot2 = mod.indexOf(46, dot + 1);
         String name;
         if (dot2 == -1) {
            name = mod.substring(dot + 1);
         } else {
            name = mod.substring(dot + 1, dot2);
         }

         module = module.__getattr__(name);
      }

      return module;
   }

   /** @deprecated */
   @Deprecated
   public static PyObject[] importFrom(String mod, String[] names, PyFrame frame) {
      return importFromAs(mod, names, (String[])null, frame, -1);
   }

   public static PyObject[] importFrom(String mod, String[] names, PyFrame frame, int level) {
      return importFromAs(mod, names, (String[])null, frame, level);
   }

   /** @deprecated */
   @Deprecated
   public static PyObject[] importFromAs(String mod, String[] names, PyFrame frame) {
      return importFromAs(mod, names, (String[])null, frame, -1);
   }

   public static PyObject[] importFromAs(String mod, String[] names, String[] asnames, PyFrame frame, int level) {
      PyObject[] pyNames = new PyObject[names.length];

      for(int i = 0; i < names.length; ++i) {
         pyNames[i] = Py.newString(names[i]);
      }

      PyObject module = __builtin__.__import__(mod, frame.f_globals, frame.getLocals(), new PyTuple(pyNames), level);
      PyObject[] submods = new PyObject[names.length];

      for(int i = 0; i < names.length; ++i) {
         PyObject submod = module.__findattr__(names[i]);
         if (submod == null) {
            submod = module.impAttr(names[i]);
         }

         if (submod == null) {
            throw Py.ImportError("cannot import name " + names[i]);
         }

         submods[i] = submod;
      }

      return submods;
   }

   public static void importAll(String mod, PyFrame frame, int level) {
      PyObject module = __builtin__.__import__(mod, frame.f_globals, frame.getLocals(), all, level);
      importAll(module, frame);
   }

   /** @deprecated */
   @Deprecated
   public static void importAll(String mod, PyFrame frame) {
      importAll(mod, frame, -1);
   }

   public static void importAll(PyObject module, PyFrame frame) {
      boolean filter = true;
      PyObject names;
      if (module instanceof PyJavaPackage) {
         names = ((PyJavaPackage)module).fillDir();
      } else {
         PyObject __all__ = module.__findattr__("__all__");
         if (__all__ != null) {
            names = __all__;
            filter = false;
         } else {
            names = module.__dir__();
         }
      }

      loadNames(names, module, frame.getLocals(), filter);
   }

   private static void loadNames(PyObject names, PyObject module, PyObject locals, boolean filter) {
      Iterator var4 = names.asIterable().iterator();

      while(true) {
         String sname;
         do {
            if (!var4.hasNext()) {
               return;
            }

            PyObject name = (PyObject)var4.next();
            sname = ((PyString)name).internedString();
         } while(filter && sname.startsWith("_"));

         try {
            PyObject value = module.__findattr__(sname);
            if (value == null) {
               PyObject nameObj = module.__findattr__("__name__");
               if (nameObj != null) {
                  String submodName = nameObj.__str__().toString() + '.' + sname;
                  value = __builtin__.__import__(submodName, (PyObject)null, (PyObject)null, nonEmptyFromlist);
               }
            }

            locals.__setitem__(sname, value);
         } catch (Exception var10) {
         }
      }
   }

   static PyObject reload(PyModule m) {
      PySystemState sys = Py.getSystemState();
      PyObject modules = sys.modules;
      Map modules_reloading = sys.modules_reloading;
      ReentrantLock importLock = Py.getSystemState().getImportLock();
      importLock.lock();

      PyObject var5;
      try {
         var5 = _reload(m, modules, modules_reloading);
      } finally {
         modules_reloading.clear();
         importLock.unlock();
      }

      return var5;
   }

   private static PyObject _reload(PyModule m, PyObject modules, Map modules_reloading) {
      String name = m.__getattr__("__name__").toString().intern();
      PyModule nm = (PyModule)modules.__finditem__(name);
      if (nm != null && nm.__getattr__("__name__").toString().equals(name)) {
         PyModule existing_module = (PyModule)modules_reloading.get(name);
         if (existing_module != null) {
            return existing_module;
         } else {
            modules_reloading.put(name, nm);
            PyList path = Py.getSystemState().path;
            String modName = name;
            int dot = name.lastIndexOf(46);
            if (dot != -1) {
               String iname = name.substring(0, dot).intern();
               PyObject pkg = modules.__finditem__(iname);
               if (pkg == null) {
                  throw Py.ImportError("reload(): parent not in sys.modules");
               }

               path = (PyList)pkg.__getattr__("__path__");
               name = name.substring(dot + 1, name.length()).intern();
            }

            nm.__setattr__("__name__", new PyString(name));

            try {
               PyObject ret = find_module(name, modName, path);
               modules.__setitem__(modName, ret);
               return ret;
            } catch (RuntimeException var11) {
               modules.__setitem__((String)name, nm);
               throw var11;
            }
         }
      } else {
         throw Py.ImportError("reload(): module " + name + " not in sys.modules");
      }
   }

   public static int getAPIVersion() {
      return 37;
   }

   public static enum CodeImport {
      source,
      compiled_only;
   }

   public static class CodeData {
      private final byte[] bytes;
      private final long mtime;
      private final String filename;

      public CodeData(byte[] bytes, long mtime, String filename) {
         this.bytes = bytes;
         this.mtime = mtime;
         this.filename = filename;
      }

      public byte[] getBytes() {
         return this.bytes;
      }

      public long getMTime() {
         return this.mtime;
      }

      public String getFilename() {
         return this.filename;
      }
   }
}
