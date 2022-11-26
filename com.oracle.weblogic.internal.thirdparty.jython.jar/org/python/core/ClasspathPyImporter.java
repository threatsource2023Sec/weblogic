package org.python.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.python.core.util.FileUtil;
import org.python.core.util.StringUtil;
import org.python.core.util.importer;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.util.Generic;

@Untraversable
@ExposedType(
   name = "ClasspathPyImporter"
)
public class ClasspathPyImporter extends importer {
   public static final String PYCLASSPATH_PREFIX = "__pyclasspath__/";
   public static final PyType TYPE;
   private Map entries = Generic.map();
   private String path;

   public ClasspathPyImporter(PyType subType) {
      super(subType);
   }

   public ClasspathPyImporter() {
   }

   @ExposedNew
   final void ClasspathPyImporter___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("__init__", args, kwds, new String[]{"path"});
      String path = ap.getString(0);
      if (path != null && path.startsWith("__pyclasspath__/")) {
         if (!path.endsWith("/")) {
            path = path + "/";
         }

         this.path = path;
      } else {
         throw Py.ImportError("path isn't for classpath importer");
      }
   }

   public String get_data(String path) {
      return this.ClasspathPyImporter_get_data(path);
   }

   final String ClasspathPyImporter_get_data(String path) {
      int len = "__pyclasspath__/".length();
      if (len < path.length() && path.startsWith("__pyclasspath__/")) {
         path = path.substring(len);
      }

      try {
         importer.Bundle bundle = this.makeBundle(path, this.makeEntry(path));
         Throwable var4 = null;

         String var6;
         try {
            byte[] data = FileUtil.readBytes(bundle.inputStream);
            var6 = StringUtil.fromBytes(data);
         } catch (Throwable var16) {
            var4 = var16;
            throw var16;
         } finally {
            if (bundle != null) {
               if (var4 != null) {
                  try {
                     bundle.close();
                  } catch (Throwable var15) {
                     var4.addSuppressed(var15);
                  }
               } else {
                  bundle.close();
               }
            }

         }

         return var6;
      } catch (IOException var18) {
         throw Py.IOError(var18);
      }
   }

   public String get_source(String fullname) {
      return this.ClasspathPyImporter_get_source(fullname);
   }

   final String ClasspathPyImporter_get_source(String fullname) {
      importer.ModuleInfo moduleInfo = this.getModuleInfo(fullname);
      if (moduleInfo == importer.ModuleInfo.ERROR) {
         return null;
      } else if (moduleInfo == importer.ModuleInfo.NOT_FOUND) {
         throw Py.ImportError(String.format("can't find module '%s'", fullname));
      } else {
         String path = this.makeFilename(fullname);
         if (moduleInfo == importer.ModuleInfo.PACKAGE) {
            path = path + File.separator + "__init__.py";
         } else {
            path = path + ".py";
         }

         try {
            importer.Bundle bundle = this.makeBundle(path, this.makeEntry(path));
            Throwable var5 = null;

            Object var7;
            try {
               InputStream is = bundle.inputStream;
               if (is != null) {
                  byte[] data = FileUtil.readBytes(is);
                  String var8 = StringUtil.fromBytes(data);
                  return var8;
               }

               var7 = null;
            } catch (Throwable var19) {
               var5 = var19;
               throw var19;
            } finally {
               if (bundle != null) {
                  if (var5 != null) {
                     try {
                        bundle.close();
                     } catch (Throwable var18) {
                        var5.addSuppressed(var18);
                     }
                  } else {
                     bundle.close();
                  }
               }

            }

            return (String)var7;
         } catch (IOException var21) {
            throw Py.IOError(var21);
         }
      }
   }

   final PyObject ClasspathPyImporter_find_module(String fullname, String path) {
      return this.importer_find_module(fullname, path);
   }

   final boolean ClasspathPyImporter_is_package(String fullname) {
      return this.importer_is_package(fullname);
   }

   final PyObject ClasspathPyImporter_get_code(String fullname) {
      importer.ModuleCodeData moduleCodeData = this.getModuleCode(fullname);
      return (PyObject)(moduleCodeData != null ? moduleCodeData.code : Py.None);
   }

   final PyObject ClasspathPyImporter_load_module(String fullname) {
      return this.importer_load_module(fullname);
   }

   protected long getSourceMtime(String path) {
      return -1L;
   }

   protected importer.Bundle makeBundle(String fullFilename, String entry) {
      InputStream is = (InputStream)this.entries.remove(entry);
      return new importer.Bundle(is) {
         public void close() {
            try {
               this.inputStream.close();
            } catch (IOException var2) {
               throw Py.JavaError(var2);
            }
         }
      };
   }

   protected String makeEntry(String filename) {
      if (!this.getSeparator().equals(File.separator)) {
         filename = filename.replace(File.separator, this.getSeparator());
      }

      if (this.entries.containsKey(filename)) {
         return filename;
      } else {
         InputStream is;
         if (Py.getSystemState().getClassLoader() != null) {
            is = this.tryClassLoader(filename, Py.getSystemState().getClassLoader(), "sys");
         } else {
            is = this.tryClassLoader(filename, imp.getParentClassLoader(), "parent");
         }

         if (is != null) {
            this.entries.put(filename, is);
            return filename;
         } else {
            return null;
         }
      }
   }

   private InputStream tryClassLoader(String fullFilename, ClassLoader loader, String name) {
      if (loader != null) {
         Py.writeDebug("import", "trying " + fullFilename + " in " + name + " class loader");
         return loader.getResourceAsStream(fullFilename);
      } else {
         return null;
      }
   }

   protected String makeFilename(String fullname) {
      return this.path.replace("__pyclasspath__/", "") + fullname.replace('.', '/');
   }

   protected String makeFilePath(String fullname) {
      return this.path + fullname.replace('.', '/');
   }

   protected String makePackagePath(String fullname) {
      return this.path;
   }

   protected String getSeparator() {
      return "/";
   }

   static {
      PyType.addBuilder(ClasspathPyImporter.class, new PyExposer());
      TYPE = PyType.fromClass(ClasspathPyImporter.class);
   }

   private static class ClasspathPyImporter___init___exposer extends PyBuiltinMethod {
      public ClasspathPyImporter___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public ClasspathPyImporter___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((ClasspathPyImporter)this.self).ClasspathPyImporter___init__(var1, var2);
         return Py.None;
      }
   }

   private static class ClasspathPyImporter_get_data_exposer extends PyBuiltinMethodNarrow {
      public ClasspathPyImporter_get_data_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public ClasspathPyImporter_get_data_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter_get_data_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((ClasspathPyImporter)this.self).ClasspathPyImporter_get_data(var1.asString());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class ClasspathPyImporter_get_source_exposer extends PyBuiltinMethodNarrow {
      public ClasspathPyImporter_get_source_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public ClasspathPyImporter_get_source_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter_get_source_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((ClasspathPyImporter)this.self).ClasspathPyImporter_get_source(var1.asString());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class ClasspathPyImporter_find_module_exposer extends PyBuiltinMethodNarrow {
      public ClasspathPyImporter_find_module_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public ClasspathPyImporter_find_module_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter_find_module_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((ClasspathPyImporter)this.self).ClasspathPyImporter_find_module(var1.asString(), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((ClasspathPyImporter)this.self).ClasspathPyImporter_find_module(var1.asString(), (String)null);
      }
   }

   private static class ClasspathPyImporter_is_package_exposer extends PyBuiltinMethodNarrow {
      public ClasspathPyImporter_is_package_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public ClasspathPyImporter_is_package_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter_is_package_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((ClasspathPyImporter)this.self).ClasspathPyImporter_is_package(var1.asString()));
      }
   }

   private static class ClasspathPyImporter_get_code_exposer extends PyBuiltinMethodNarrow {
      public ClasspathPyImporter_get_code_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public ClasspathPyImporter_get_code_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter_get_code_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((ClasspathPyImporter)this.self).ClasspathPyImporter_get_code(var1.asString());
      }
   }

   private static class ClasspathPyImporter_load_module_exposer extends PyBuiltinMethodNarrow {
      public ClasspathPyImporter_load_module_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public ClasspathPyImporter_load_module_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClasspathPyImporter_load_module_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((ClasspathPyImporter)this.self).ClasspathPyImporter_load_module(var1.asString());
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         ClasspathPyImporter var4 = new ClasspathPyImporter(this.for_type);
         if (var1) {
            var4.ClasspathPyImporter___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ClasspathPyImporterDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new ClasspathPyImporter___init___exposer("__init__"), new ClasspathPyImporter_get_data_exposer("get_data"), new ClasspathPyImporter_get_source_exposer("get_source"), new ClasspathPyImporter_find_module_exposer("find_module"), new ClasspathPyImporter_is_package_exposer("is_package"), new ClasspathPyImporter_get_code_exposer("get_code"), new ClasspathPyImporter_load_module_exposer("load_module")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("ClasspathPyImporter", ClasspathPyImporter.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
