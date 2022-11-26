package org.python.modules.zipimport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.util.FileUtil;
import org.python.core.util.StringUtil;
import org.python.core.util.importer;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "zipimport.zipimporter",
   base = PyObject.class
)
public class zipimporter extends importer implements Traverseproc {
   public static final PyType TYPE;
   public static final PyString __doc__;
   public String archive;
   public String prefix;
   public PyObject files;
   private PySystemState sys;

   public PyString getArchive() {
      return Py.fileSystemEncode(this.archive);
   }

   public zipimporter() {
   }

   public zipimporter(PyType subType) {
      super(subType);
   }

   public zipimporter(String path) {
      this.zipimporter___init__(path);
   }

   @ExposedNew
   final void zipimporter___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("__init__", args, kwds, new String[]{"path"});
      String path = Py.fileSystemDecode(ap.getPyObject(0));
      this.zipimporter___init__(path);
   }

   private void zipimporter___init__(String path) {
      if (path != null && path.length() != 0) {
         File pathFile = new File(path);
         this.sys = Py.getSystemState();
         this.prefix = "";

         while(true) {
            File fullPathFile = new File(this.sys.getPath(pathFile.getPath()));

            try {
               if (fullPathFile.isFile()) {
                  this.archive = pathFile.getPath();
                  break;
               }
            } catch (SecurityException var5) {
            }

            File parentFile = pathFile.getParentFile();
            if (parentFile == null) {
               break;
            }

            this.prefix = pathFile.getName() + File.separator + this.prefix;
            pathFile = parentFile;
         }

         if (this.archive != null) {
            PyUnicode archivePath = Py.newUnicode(this.archive);
            this.files = zipimport._zip_directory_cache.__finditem__(archivePath);
            if (this.files == null) {
               this.files = this.readDirectory(this.archive);
               zipimport._zip_directory_cache.__setitem__(archivePath, this.files);
            }

            if (this.prefix != "" && !this.prefix.endsWith(File.separator)) {
               this.prefix = this.prefix + File.separator;
            }

         } else {
            throw zipimport.ZipImportError("not a Zip file: " + path);
         }
      } else {
         throw zipimport.ZipImportError("archive path is empty");
      }
   }

   public PyObject find_module(String fullname) {
      return this.zipimporter_find_module(fullname, (String)null);
   }

   public PyObject find_module(String fullname, String path) {
      return this.zipimporter_find_module(fullname, path);
   }

   final PyObject zipimporter_find_module(String fullname, String path) {
      return this.importer_find_module(fullname, path);
   }

   public PyObject load_module(String fullname) {
      return this.zipimporter_load_module(fullname);
   }

   final PyObject zipimporter_load_module(String fullname) {
      return this.importer_load_module(fullname);
   }

   public String get_data(String path) {
      return this.zipimporter_get_data(Py.newUnicode(path));
   }

   final String zipimporter_get_data(PyObject opath) {
      String path = Py.fileSystemDecode(opath);
      int len = this.archive.length();
      if (len < path.length() && path.startsWith(this.archive + File.separator)) {
         path = path.substring(len + 1);
      }

      PyObject tocEntry = this.files.__finditem__(path);
      if (tocEntry == null) {
         throw Py.IOError(path);
      } else {
         importer.Bundle zipBundle = this.makeBundle(path, tocEntry);

         byte[] data;
         try {
            data = FileUtil.readBytes(zipBundle.inputStream);
         } catch (IOException var11) {
            throw Py.IOError(var11);
         } finally {
            zipBundle.close();
         }

         return StringUtil.fromBytes(data);
      }
   }

   public boolean is_package(String fullname) {
      return this.zipimporter_is_package(fullname);
   }

   final boolean zipimporter_is_package(String fullname) {
      importer.ModuleInfo moduleInfo = this.getModuleInfo(fullname);
      if (moduleInfo == importer.ModuleInfo.NOT_FOUND) {
         throw zipimport.ZipImportError(String.format("can't find module '%s'", fullname));
      } else {
         return moduleInfo == importer.ModuleInfo.PACKAGE;
      }
   }

   public PyObject get_code(String fullname) {
      return this.zipimporter_get_code(fullname);
   }

   final PyObject zipimporter_get_code(String fullname) {
      importer.ModuleCodeData moduleCodeData = this.getModuleCode(fullname);
      return (PyObject)(moduleCodeData != null ? moduleCodeData.code : Py.None);
   }

   public PyObject get_filename(String fullname) {
      return this.zipimporter_get_filename(fullname);
   }

   final PyObject zipimporter_get_filename(String fullname) {
      importer.ModuleCodeData moduleCodeData = this.getModuleCode(fullname);
      return (PyObject)(moduleCodeData != null ? Py.fileSystemEncode(moduleCodeData.path) : Py.None);
   }

   public String get_source(String fullname) {
      return this.zipimporter_get_source(fullname);
   }

   final String zipimporter_get_source(String fullname) {
      importer.ModuleInfo moduleInfo = this.getModuleInfo(fullname);
      if (moduleInfo == importer.ModuleInfo.ERROR) {
         return null;
      } else if (moduleInfo == importer.ModuleInfo.NOT_FOUND) {
         throw zipimport.ZipImportError(String.format("can't find module '%s'", fullname));
      } else {
         String path = this.makeFilename(fullname);
         if (moduleInfo == importer.ModuleInfo.PACKAGE) {
            path = path + File.separator + "__init__.py";
         } else {
            path = path + ".py";
         }

         PyObject tocEntry = this.files.__finditem__(path);
         return tocEntry == null ? null : this.get_data(path);
      }
   }

   public ZipBundle makeBundle(String datapath, PyObject entry) {
      datapath = datapath.replace(File.separatorChar, '/');

      ZipFile zipArchive;
      try {
         zipArchive = new ZipFile(new File(this.sys.getPath(this.archive)));
      } catch (IOException var7) {
         throw zipimport.ZipImportError("zipimport: can not open file: " + this.archive);
      }

      ZipEntry dataEntry = zipArchive.getEntry(datapath);

      try {
         return new ZipBundle(zipArchive, zipArchive.getInputStream(dataEntry));
      } catch (IOException var6) {
         Py.writeDebug("import", "zipimporter.getDataStream exception: " + var6.toString());
         throw zipimport.ZipImportError("zipimport: can not open file: " + this.archive);
      }
   }

   protected long getSourceMtime(String path) {
      String sourcePath = path.substring(0, path.length() - 9) + ".py";
      PyObject sourceTocEntry = this.files.__finditem__(sourcePath);
      if (sourceTocEntry == null) {
         return -1L;
      } else {
         int time;
         int date;
         try {
            time = sourceTocEntry.__finditem__(5).asInt();
            date = sourceTocEntry.__finditem__(6).asInt();
         } catch (PyException var7) {
            if (!var7.match(Py.TypeError)) {
               throw var7;
            }

            time = -1;
            date = -1;
         }

         return this.dosTimeToEpoch(time, date);
      }
   }

   private PyObject readDirectory(String archive) {
      File file = new File(this.sys.getPath(archive));
      if (!file.canRead()) {
         throw zipimport.ZipImportError(String.format("can't open Zip file: '%s'", archive));
      } else {
         ZipFile zipFile;
         try {
            zipFile = new ZipFile(file);
         } catch (IOException var14) {
            throw zipimport.ZipImportError(String.format("can't read Zip file: '%s'", archive));
         }

         PyObject files = new PyDictionary();

         try {
            this.readZipFile(zipFile, files);
         } finally {
            try {
               zipFile.close();
            } catch (IOException var12) {
               throw Py.IOError(var12);
            }
         }

         return files;
      }
   }

   private void readZipFile(ZipFile zipFile, PyObject files) {
      Enumeration zipEntries = zipFile.entries();

      while(zipEntries.hasMoreElements()) {
         ZipEntry zipEntry = (ZipEntry)zipEntries.nextElement();
         String name = zipEntry.getName().replace('/', File.separatorChar);
         PyObject __file__ = Py.fileSystemEncode(this.archive + File.separator + name);
         PyObject compress = Py.newInteger(zipEntry.getMethod());
         PyObject data_size = new PyLong(zipEntry.getCompressedSize());
         PyObject file_size = new PyLong(zipEntry.getSize());
         PyObject file_offset = Py.newInteger(-1);
         PyObject time = new PyInteger(this.epochToDosTime(zipEntry.getTime()));
         PyObject date = new PyInteger(this.epochToDosDate(zipEntry.getTime()));
         PyObject crc = new PyLong(zipEntry.getCrc());
         PyTuple entry = new PyTuple(new PyObject[]{__file__, compress, data_size, file_size, file_offset, time, date, crc});
         files.__setitem__((PyObject)Py.newStringOrUnicode(name), entry);
      }

   }

   protected String getSeparator() {
      return File.separator;
   }

   protected String makeFilename(String fullname) {
      return this.prefix + this.getSubname(fullname).replace('.', File.separatorChar);
   }

   protected String makePackagePath(String fullname) {
      return this.archive + File.separator + this.prefix + this.getSubname(fullname);
   }

   protected String makeFilePath(String fullname) {
      return this.makePackagePath(fullname);
   }

   protected PyObject makeEntry(String fullFilename) {
      return this.files.__finditem__(fullFilename);
   }

   protected String getSubname(String fullname) {
      int i = fullname.lastIndexOf(".");
      return i >= 0 ? fullname.substring(i + 1) : fullname;
   }

   private int epochToDosDate(long time) {
      Date d = new Date(time);
      int year = d.getYear() + 1900;
      return year < 1980 ? 2162688 : year - 1980 << 9 | d.getMonth() + 1 << 5 | d.getDate() << 0;
   }

   private int epochToDosTime(long time) {
      Date d = new Date(time);
      return d.getHours() << 11 | d.getMinutes() << 5 | d.getSeconds() >> 1;
   }

   private long dosTimeToEpoch(int dosTime, int dosDate) {
      Date d = new Date((dosDate >> 9 & 127) + 80, (dosDate >> 5 & 15) - 1, dosDate & 31, dosTime >> 11 & 31, dosTime >> 5 & 63, (dosTime & 31) * 2);
      return d.getTime();
   }

   public String toString() {
      return this.zipimporter_toString();
   }

   final String zipimporter_toString() {
      String bytesName = this.archive != null ? Py.fileSystemEncode(this.archive).getString() : "???";
      return this.prefix != null && !"".equals(this.prefix) ? String.format("<zipimporter object \"%.300s%c%.150s\">", bytesName, File.separatorChar, this.prefix) : String.format("<zipimporter object \"%.300s\">", bytesName);
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.files != null) {
         int retVal = visit.visit(this.files, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.sys == null ? 0 : visit.visit(this.sys, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.files || ob == this.sys);
   }

   static {
      PyType.addBuilder(zipimporter.class, new PyExposer());
      TYPE = PyType.fromClass(zipimporter.class);
      __doc__ = new PyString("zipimporter(archivepath) -> zipimporter object\n\nCreate a new zipimporter instance. 'archivepath' must be a path to\na zipfile. ZipImportError is raised if 'archivepath' doesn't point to\na valid Zip archive.");
   }

   private class ZipBundle extends importer.Bundle {
      ZipFile zipFile;

      public ZipBundle(ZipFile zipFile, InputStream inputStream) {
         super(inputStream);
         this.zipFile = zipFile;
      }

      public void close() {
         try {
            this.zipFile.close();
         } catch (IOException var2) {
            throw Py.IOError(var2);
         }
      }
   }

   private static class zipimporter___init___exposer extends PyBuiltinMethod {
      public zipimporter___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public zipimporter___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((zipimporter)this.self).zipimporter___init__(var1, var2);
         return Py.None;
      }
   }

   private static class zipimporter_find_module_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_find_module_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public zipimporter_find_module_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_find_module_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((zipimporter)this.self).zipimporter_find_module(var1.asString(), var2.asStringOrNull());
      }

      public PyObject __call__(PyObject var1) {
         return ((zipimporter)this.self).zipimporter_find_module(var1.asString(), (String)null);
      }
   }

   private static class zipimporter_load_module_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_load_module_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public zipimporter_load_module_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_load_module_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((zipimporter)this.self).zipimporter_load_module(var1.asString());
      }
   }

   private static class zipimporter_get_data_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_get_data_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public zipimporter_get_data_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_get_data_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((zipimporter)this.self).zipimporter_get_data(var1);
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class zipimporter_is_package_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_is_package_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public zipimporter_is_package_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_is_package_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((zipimporter)this.self).zipimporter_is_package(var1.asString()));
      }
   }

   private static class zipimporter_get_code_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_get_code_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public zipimporter_get_code_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_get_code_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((zipimporter)this.self).zipimporter_get_code(var1.asString());
      }
   }

   private static class zipimporter_get_filename_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_get_filename_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public zipimporter_get_filename_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_get_filename_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((zipimporter)this.self).zipimporter_get_filename(var1.asString());
      }
   }

   private static class zipimporter_get_source_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_get_source_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public zipimporter_get_source_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_get_source_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         String var10000 = ((zipimporter)this.self).zipimporter_get_source(var1.asString());
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class zipimporter_toString_exposer extends PyBuiltinMethodNarrow {
      public zipimporter_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public zipimporter_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new zipimporter_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((zipimporter)this.self).zipimporter_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class _files_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _files_descriptor() {
         super("_files", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((zipimporter)var1).files;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class prefix_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public prefix_descriptor() {
         super("prefix", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((zipimporter)var1).prefix;
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __doc___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __doc___descriptor() {
         super("__doc__", PyString.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((zipimporter)var1).__doc__;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class archive_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public archive_descriptor() {
         super("archive", PyString.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((zipimporter)var1).getArchive();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         zipimporter var4 = new zipimporter(this.for_type);
         if (var1) {
            var4.zipimporter___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new zipimporterDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new zipimporter___init___exposer("__init__"), new zipimporter_find_module_exposer("find_module"), new zipimporter_load_module_exposer("load_module"), new zipimporter_get_data_exposer("get_data"), new zipimporter_is_package_exposer("is_package"), new zipimporter_get_code_exposer("get_code"), new zipimporter_get_filename_exposer("get_filename"), new zipimporter_get_source_exposer("get_source"), new zipimporter_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new _files_descriptor(), new prefix_descriptor(), new __doc___descriptor(), new archive_descriptor()};
         super("zipimport.zipimporter", zipimporter.class, PyObject.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
