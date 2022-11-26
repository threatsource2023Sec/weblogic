package org.python.modules;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PyList;
import org.python.core.PyModule;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.PyTuple;
import org.python.core.__builtin__;
import org.python.core.imp;

public class _imp {
   public static PyString __doc__ = new PyString("This module provides the components needed to build your own\n__import__ function.  Undocumented functions are obsolete.\n");
   public static final int PY_SOURCE = 1;
   public static final int PY_COMPILED = 2;
   public static final int C_EXTENSION = 3;
   public static final int PKG_DIRECTORY = 5;
   public static final int C_BUILTIN = 6;
   public static final int PY_FROZEN = 7;
   public static final int IMP_HOOK = 9;

   private static PyObject newFile(File file) {
      try {
         return new PyFile(new FileInputStream(file));
      } catch (IOException var2) {
         throw Py.IOError(var2);
      }
   }

   private static boolean caseok(File file, String filename) {
      return imp.caseok(file, filename);
   }

   static ModuleInfo findFromSource(String name, String entry, boolean findingPackage, boolean preferSource) {
      String sourceName = "__init__.py";
      String compiledName = imp.makeCompiledFilename(sourceName);
      String directoryName = PySystemState.getPathLazy(entry);
      String displayDirName = entry.equals("") ? null : entry;
      File dir = findingPackage ? new File(directoryName) : new File(directoryName, name);
      File sourceFile = new File(dir, sourceName);
      File compiledFile = new File(dir, compiledName);
      boolean pkg = dir.isDirectory() && caseok(dir, name) && (sourceFile.isFile() || compiledFile.isFile());
      if (!findingPackage) {
         if (pkg) {
            return new ModuleInfo(Py.None, (new File(displayDirName, name)).getPath(), "", "", 5);
         }

         Py.writeDebug("import", "trying source " + dir.getPath());
         sourceName = name + ".py";
         compiledName = imp.makeCompiledFilename(sourceName);
         sourceFile = new File(directoryName, sourceName);
         compiledFile = new File(directoryName, compiledName);
      }

      if (sourceFile.isFile() && caseok(sourceFile, sourceName)) {
         if (!preferSource && compiledFile.isFile() && caseok(compiledFile, compiledName)) {
            Py.writeDebug("import", "trying precompiled " + compiledFile.getPath());
            long pyTime = sourceFile.lastModified();
            long classTime = compiledFile.lastModified();
            if (classTime >= pyTime) {
               return new ModuleInfo(newFile(compiledFile), (new File(displayDirName, compiledName)).getPath(), ".class", "rb", 2);
            }
         }

         return new ModuleInfo(newFile(sourceFile), (new File(displayDirName, sourceName)).getPath(), ".py", "r", 1);
      } else {
         Py.writeDebug("import", "trying " + compiledFile.getPath());
         return compiledFile.isFile() && caseok(compiledFile, compiledName) ? new ModuleInfo(newFile(compiledFile), (new File(displayDirName, compiledName)).getPath(), ".class", "rb", 2) : null;
      }
   }

   public static PyObject load_dynamic(String name, String pathname) {
      return load_dynamic(name, pathname, (PyObject)null);
   }

   public static PyObject load_dynamic(String name, String pathname, PyObject file) {
      throw Py.ImportError("No module named " + name);
   }

   public static PyObject load_source(String modname, String filename) {
      return load_source(modname, filename, (PyObject)null);
   }

   public static PyObject load_source(String modname, String filename, PyObject file) {
      PyObject mod = Py.None;
      if (file == null) {
         file = new PyFile(filename, "r", 1024);
      }

      Object o = ((PyObject)file).__tojava__(InputStream.class);
      if (o == Py.NoConversion) {
         throw Py.TypeError("must be a file-like object");
      } else {
         PySystemState sys = Py.getSystemState();
         String compiledFilename = imp.makeCompiledFilename(sys.getPath(filename));
         mod = imp.createFromSource(modname.intern(), (InputStream)o, filename, compiledFilename);
         PyObject modules = sys.modules;
         modules.__setitem__(modname.intern(), mod);
         return mod;
      }
   }

   public static PyObject reload(PyObject module) {
      return __builtin__.reload(module);
   }

   public static PyObject load_compiled(String name, PyString pathname) {
      String _pathname = Py.fileSystemDecode(pathname);
      return _load_compiled(name, _pathname, new PyFile(_pathname, "rb", -1));
   }

   public static PyObject load_compiled(String name, PyString pathname, PyObject file) {
      return _load_compiled(name, Py.fileSystemDecode(pathname), file);
   }

   private static PyObject _load_compiled(String name, String pathname, PyObject file) {
      InputStream stream = (InputStream)file.__tojava__(InputStream.class);
      if (stream == Py.NoConversion) {
         throw Py.TypeError("must be a file-like object");
      } else {
         String sourceName = pathname;
         if (pathname.endsWith("$py.class")) {
            sourceName = pathname.substring(0, pathname.length() - 9) + ".py";
         }

         return imp.loadFromCompiled(name.intern(), stream, sourceName, pathname);
      }
   }

   public static PyObject find_module(String name) {
      return find_module(name, Py.None);
   }

   public static PyObject find_module(String name, PyObject path) {
      if (path == Py.None && PySystemState.getBuiltin(name) != null) {
         return new PyTuple(new PyObject[]{Py.None, Py.newString(name), new PyTuple(new PyObject[]{Py.EmptyString, Py.EmptyString, Py.newInteger(6)})});
      } else {
         if (path == Py.None) {
            path = Py.getSystemState().path;
         }

         Iterator var2 = ((PyObject)path).asIterable().iterator();

         ModuleInfo mi;
         do {
            if (!var2.hasNext()) {
               throw Py.ImportError("No module named " + name);
            }

            PyObject p = (PyObject)var2.next();
            mi = findFromSource(name, Py.fileSystemDecode(p), false, true);
         } while(mi == null);

         return new PyTuple(new PyObject[]{mi.file, Py.fileSystemEncode(mi.filename), new PyTuple(new PyObject[]{Py.newString(mi.suffix), Py.newString(mi.mode), Py.newInteger(mi.type)})});
      }
   }

   public static PyObject load_module(String name, PyObject file, PyObject filename, PyTuple data) {
      PyObject mod = Py.None;
      PySystemState sys = Py.getSystemState();
      int type = data.__getitem__(2).asInt();
      String filenameString = Py.fileSystemDecode((PyObject)filename);

      while(mod == Py.None) {
         switch (type) {
            case 1:
               Object o = file.__tojava__(InputStream.class);
               if (o == Py.NoConversion) {
                  throw Py.TypeError("must be a file-like object");
               }

               String resolvedFilename = sys.getPath(filenameString);
               String compiledName = imp.makeCompiledFilename(resolvedFilename);
               if (name.endsWith(".__init__")) {
                  name = name.substring(0, name.length() - ".__init__".length());
               } else if (name.equals("__init__")) {
                  name = (new File(sys.getCurrentWorkingDir())).getName();
               }

               File fp = new File(resolvedFilename);
               long mtime = -1L;
               if (fp.isFile()) {
                  mtime = fp.lastModified();
               }

               mod = imp.createFromSource(name.intern(), (InputStream)o, filenameString, compiledName, mtime);
               break;
            case 2:
               mod = _load_compiled(name, filenameString, file);
               break;
            case 3:
            case 4:
            default:
               throw Py.ImportError("No module named " + name);
            case 5:
               PyModule m = imp.addModule(name);
               m.__dict__.__setitem__((String)"__path__", new PyList(new PyObject[]{(PyObject)filename}));
               m.__dict__.__setitem__((String)"__file__", (PyObject)filename);
               ModuleInfo mi = findFromSource(name, filenameString, true, true);
               type = mi.type;
               file = mi.file;
               filenameString = mi.filename;
               filename = Py.newStringOrUnicode(filenameString);
         }
      }

      PyObject modules = sys.modules;
      modules.__setitem__(name.intern(), mod);
      return mod;
   }

   public static PyString makeCompiledFilename(PyString filename) {
      filename = Py.fileSystemEncode(filename);
      return Py.newString(imp.makeCompiledFilename(filename.getString()));
   }

   public static PyObject get_magic() {
      return new PyString("\u0003ó\r\n");
   }

   public static PyObject get_suffixes() {
      return new PyList(new PyObject[]{new PyTuple(new PyObject[]{new PyString(".py"), new PyString("r"), Py.newInteger(1)}), new PyTuple(new PyObject[]{new PyString("$py.class"), new PyString("rb"), Py.newInteger(2)})});
   }

   public static PyModule new_module(String name) {
      return new PyModule(name, (PyObject)null);
   }

   public static boolean is_builtin(String name) {
      return PySystemState.getBuiltin(name) != null;
   }

   public static boolean is_frozen(String name) {
      return false;
   }

   public static void acquire_lock() {
      Py.getSystemState().getImportLock().lock();
   }

   public static void release_lock() {
      try {
         Py.getSystemState().getImportLock().unlock();
      } catch (IllegalMonitorStateException var1) {
         throw Py.RuntimeError("not holding the import lock");
      }
   }

   public static boolean lock_held() {
      return Py.getSystemState().getImportLock().isHeldByCurrentThread();
   }

   private static class ModuleInfo {
      PyObject file;
      String filename;
      String suffix;
      String mode;
      int type;

      ModuleInfo(PyObject file, String filename, String suffix, String mode, int type) {
         this.file = file;
         this.filename = filename;
         this.suffix = suffix;
         this.mode = mode;
         this.type = type;
      }
   }
}
