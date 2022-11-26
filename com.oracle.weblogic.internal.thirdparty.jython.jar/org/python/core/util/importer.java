package org.python.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.EnumSet;
import org.python.core.BytecodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyList;
import org.python.core.PyModule;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.imp;

public abstract class importer extends PyObject {
   final SearchOrderEntry[] searchOrder = this.makeSearchOrder();

   public importer(PyType subType) {
      super(subType);
   }

   public importer() {
   }

   public abstract String get_data(String var1);

   protected abstract String getSeparator();

   protected abstract String makePackagePath(String var1);

   protected abstract String makeFilename(String var1);

   protected abstract String makeFilePath(String var1);

   protected abstract Object makeEntry(String var1);

   protected abstract Bundle makeBundle(String var1, Object var2);

   private SearchOrderEntry[] makeSearchOrder() {
      String initName = "__init__.py";
      return new SearchOrderEntry[]{new SearchOrderEntry(this.getSeparator() + imp.makeCompiledFilename(initName), EnumSet.of(importer.EntryType.IS_PACKAGE, importer.EntryType.IS_BYTECODE)), new SearchOrderEntry(this.getSeparator() + initName, EnumSet.of(importer.EntryType.IS_PACKAGE, importer.EntryType.IS_SOURCE)), new SearchOrderEntry("$py.class", EnumSet.of(importer.EntryType.IS_BYTECODE)), new SearchOrderEntry(".py", EnumSet.of(importer.EntryType.IS_SOURCE))};
   }

   protected final PyObject importer_find_module(String fullname, String path) {
      ModuleInfo moduleInfo = this.getModuleInfo(fullname);
      return (PyObject)(moduleInfo != importer.ModuleInfo.ERROR && moduleInfo != importer.ModuleInfo.NOT_FOUND ? this : Py.None);
   }

   protected final PyObject importer_load_module(String fullname) {
      ModuleCodeData moduleCodeData = this.getModuleCode(fullname);
      if (moduleCodeData == null) {
         return Py.None;
      } else {
         PyModule mod = imp.addModule(fullname);
         mod.__dict__.__setitem__((String)"__loader__", this);
         if (moduleCodeData.isPackage) {
            PyList pkgpath = new PyList();
            pkgpath.add(this.makePackagePath(fullname));
            mod.__dict__.__setitem__((String)"__path__", pkgpath);
         }

         imp.createFromCode(fullname, moduleCodeData.code, moduleCodeData.path);
         Py.writeDebug("import", "import " + fullname + " # loaded from " + moduleCodeData.path);
         return mod;
      }
   }

   protected final boolean importer_is_package(String fullname) {
      ModuleInfo info = this.getModuleInfo(fullname);
      return info == importer.ModuleInfo.PACKAGE;
   }

   protected abstract long getSourceMtime(String var1);

   protected final ModuleInfo getModuleInfo(String fullname) {
      String path = this.makeFilename(fullname);
      SearchOrderEntry[] var3 = this.searchOrder;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SearchOrderEntry entry = var3[var5];
         Object importEntry = this.makeEntry(path + entry.suffix);
         if (importEntry != null) {
            if (entry.type.contains(importer.EntryType.IS_PACKAGE)) {
               return importer.ModuleInfo.PACKAGE;
            }

            return importer.ModuleInfo.MODULE;
         }
      }

      return importer.ModuleInfo.NOT_FOUND;
   }

   protected final ModuleCodeData getModuleCode(String fullname) {
      String path = this.makeFilename(fullname);
      String fullPath = this.makeFilePath(fullname);
      if (path.length() < 0) {
         return null;
      } else {
         SearchOrderEntry[] var4 = this.searchOrder;
         int var5 = var4.length;
         int var6 = 0;

         String fullSearchPath;
         boolean isPackage;
         byte[] codeBytes;
         while(true) {
            if (var6 >= var5) {
               return null;
            }

            SearchOrderEntry entry = var4[var6];
            String suffix = entry.suffix;
            String searchPath = path + suffix;
            fullSearchPath = fullPath + suffix;
            Py.writeDebug("import", "# trying " + searchPath);
            Object tocEntry = this.makeEntry(searchPath);
            if (tocEntry != null) {
               isPackage = entry.type.contains(importer.EntryType.IS_PACKAGE);
               boolean isBytecode = entry.type.contains(importer.EntryType.IS_BYTECODE);
               long mtime = -1L;
               if (isBytecode) {
                  mtime = this.getSourceMtime(searchPath);
               }

               Bundle bundle = this.makeBundle(searchPath, tocEntry);

               try {
                  if (!isBytecode) {
                     codeBytes = imp.compileSource(fullname, bundle.inputStream, fullSearchPath);
                     break;
                  }

                  try {
                     codeBytes = imp.readCode(fullname, bundle.inputStream, true, mtime);
                  } catch (IOException var22) {
                     throw Py.ImportError(var22.getMessage() + "[path=" + fullSearchPath + "]");
                  }

                  if (codeBytes != null) {
                     break;
                  }
               } finally {
                  bundle.close();
               }
            }

            ++var6;
         }

         PyCode code = BytecodeLoader.makeCode(fullname + "$py", codeBytes, fullSearchPath);
         return new ModuleCodeData(code, isPackage, fullSearchPath);
      }
   }

   protected static class SearchOrderEntry {
      public String suffix;
      public EnumSet type;

      public SearchOrderEntry(String suffix, EnumSet type) {
         this.suffix = suffix;
         this.type = type;
      }
   }

   protected class ModuleCodeData {
      public PyCode code;
      public boolean isPackage;
      public String path;

      public ModuleCodeData(PyCode code, boolean isPackage, String path) {
         this.code = code;
         this.isPackage = isPackage;
         this.path = path;
      }
   }

   protected abstract static class Bundle implements AutoCloseable {
      public InputStream inputStream;

      public Bundle(InputStream inputStream) {
         this.inputStream = inputStream;
      }

      public abstract void close();
   }

   protected static enum ModuleInfo {
      ERROR,
      NOT_FOUND,
      MODULE,
      PACKAGE;
   }

   static enum EntryType {
      IS_SOURCE,
      IS_BYTECODE,
      IS_PACKAGE;
   }
}
