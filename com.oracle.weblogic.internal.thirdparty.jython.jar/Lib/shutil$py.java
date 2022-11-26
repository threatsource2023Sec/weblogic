import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("shutil.py")
public class shutil$py extends PyFunctionTable implements PyRunnable {
   static shutil$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode SpecialFileError$2;
   static final PyCode ExecError$3;
   static final PyCode copyfileobj$4;
   static final PyCode _samefile$5;
   static final PyCode copyfile$6;
   static final PyCode copymode$7;
   static final PyCode copystat$8;
   static final PyCode copy$9;
   static final PyCode copy2$10;
   static final PyCode ignore_patterns$11;
   static final PyCode _ignore_patterns$12;
   static final PyCode copytree$13;
   static final PyCode rmtree$14;
   static final PyCode onerror$15;
   static final PyCode onerror$16;
   static final PyCode _basename$17;
   static final PyCode move$18;
   static final PyCode _destinsrc$19;
   static final PyCode _get_gid$20;
   static final PyCode _get_uid$21;
   static final PyCode _make_tarball$22;
   static final PyCode _set_uid_gid$23;
   static final PyCode _call_external_zip$24;
   static final PyCode _make_zipfile$25;
   static final PyCode get_archive_formats$26;
   static final PyCode register_archive_format$27;
   static final PyCode unregister_archive_format$28;
   static final PyCode make_archive$29;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utility functions for copying and archiving files and directory trees.\n\nXXX The functions here don't copy the resource fork or other metadata on Mac.\n\n"));
      var1.setline(5);
      PyString.fromInterned("Utility functions for copying and archiving files and directory trees.\n\nXXX The functions here don't copy the resource fork or other metadata on Mac.\n\n");
      var1.setline(7);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(10);
      String[] var8 = new String[]{"abspath"};
      PyObject[] var9 = imp.importFrom("os.path", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("abspath", var4);
      var4 = null;
      var1.setline(11);
      var3 = imp.importOne("fnmatch", var1, -1);
      var1.setlocal("fnmatch", var3);
      var3 = null;
      var1.setline(12);
      var3 = imp.importOne("collections", var1, -1);
      var1.setlocal("collections", var3);
      var3 = null;
      var1.setline(13);
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;

      PyException var10;
      try {
         var1.setline(16);
         var8 = new String[]{"getpwnam"};
         var9 = imp.importFrom("pwd", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("getpwnam", var4);
         var4 = null;
      } catch (Throwable var7) {
         var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(18);
         var4 = var1.getname("None");
         var1.setlocal("getpwnam", var4);
         var4 = null;
      }

      try {
         var1.setline(21);
         var8 = new String[]{"getgrnam"};
         var9 = imp.importFrom("grp", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("getgrnam", var4);
         var4 = null;
      } catch (Throwable var6) {
         var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(23);
         var4 = var1.getname("None");
         var1.setlocal("getgrnam", var4);
         var4 = null;
      }

      var1.setline(25);
      PyList var11 = new PyList(new PyObject[]{PyString.fromInterned("copyfileobj"), PyString.fromInterned("copyfile"), PyString.fromInterned("copymode"), PyString.fromInterned("copystat"), PyString.fromInterned("copy"), PyString.fromInterned("copy2"), PyString.fromInterned("copytree"), PyString.fromInterned("move"), PyString.fromInterned("rmtree"), PyString.fromInterned("Error"), PyString.fromInterned("SpecialFileError"), PyString.fromInterned("ExecError"), PyString.fromInterned("make_archive"), PyString.fromInterned("get_archive_formats"), PyString.fromInterned("register_archive_format"), PyString.fromInterned("unregister_archive_format"), PyString.fromInterned("ignore_patterns")});
      var1.setlocal("__all__", var11);
      var3 = null;
      var1.setline(31);
      var9 = new PyObject[]{var1.getname("EnvironmentError")};
      var4 = Py.makeClass("Error", var9, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(34);
      var9 = new PyObject[]{var1.getname("EnvironmentError")};
      var4 = Py.makeClass("SpecialFileError", var9, SpecialFileError$2);
      var1.setlocal("SpecialFileError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(38);
      var9 = new PyObject[]{var1.getname("EnvironmentError")};
      var4 = Py.makeClass("ExecError", var9, ExecError$3);
      var1.setlocal("ExecError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);

      try {
         var1.setline(42);
         var1.getname("WindowsError");
      } catch (Throwable var5) {
         var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("NameError"))) {
            throw var10;
         }

         var1.setline(44);
         var4 = var1.getname("None");
         var1.setlocal("WindowsError", var4);
         var4 = null;
      }

      var1.setline(46);
      var9 = new PyObject[]{Py.newInteger(16)._mul(Py.newInteger(1024))};
      PyFunction var12 = new PyFunction(var1.f_globals, var9, copyfileobj$4, PyString.fromInterned("copy data from file-like object fsrc to file-like object fdst"));
      var1.setlocal("copyfileobj", var12);
      var3 = null;
      var1.setline(54);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _samefile$5, (PyObject)null);
      var1.setlocal("_samefile", var12);
      var3 = null;
      var1.setline(66);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, copyfile$6, PyString.fromInterned("Copy data from src to dst"));
      var1.setlocal("copyfile", var12);
      var3 = null;
      var1.setline(86);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, copymode$7, PyString.fromInterned("Copy mode bits from src to dst"));
      var1.setlocal("copymode", var12);
      var3 = null;
      var1.setline(93);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, copystat$8, PyString.fromInterned("Copy all stat info (mode bits, atime, mtime, flags) from src to dst"));
      var1.setlocal("copystat", var12);
      var3 = null;
      var1.setline(111);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, copy$9, PyString.fromInterned("Copy data and mode bits (\"cp src dst\").\n\n    The destination may be a directory.\n\n    "));
      var1.setlocal("copy", var12);
      var3 = null;
      var1.setline(122);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, copy2$10, PyString.fromInterned("Copy data and all stat info (\"cp -p src dst\").\n\n    The destination may be a directory.\n\n    "));
      var1.setlocal("copy2", var12);
      var3 = null;
      var1.setline(133);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, ignore_patterns$11, PyString.fromInterned("Function that can be used as copytree() ignore parameter.\n\n    Patterns is a sequence of glob-style patterns\n    that are used to exclude files"));
      var1.setlocal("ignore_patterns", var12);
      var3 = null;
      var1.setline(145);
      var9 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var9, copytree$13, PyString.fromInterned("Recursively copy a directory tree using copy2().\n\n    The destination directory must not already exist.\n    If exception(s) occur, an Error is raised with a list of reasons.\n\n    If the optional symlinks flag is true, symbolic links in the\n    source tree result in symbolic links in the destination tree; if\n    it is false, the contents of the files pointed to by symbolic\n    links are copied.\n\n    The optional ignore argument is a callable. If given, it\n    is called with the `src` parameter, which is the directory\n    being visited by copytree(), and `names` which is the list of\n    `src` contents, as returned by os.listdir():\n\n        callable(src, names) -> ignored_names\n\n    Since copytree() is called recursively, the callable will be\n    called once for each directory that is copied. It returns a\n    list of names relative to the `src` directory that should\n    not be copied.\n\n    XXX Consider this example code rather than the ultimate tool.\n\n    "));
      var1.setlocal("copytree", var12);
      var3 = null;
      var1.setline(210);
      var9 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var9, rmtree$14, PyString.fromInterned("Recursively delete a directory tree.\n\n    If ignore_errors is set, errors are ignored; otherwise, if onerror\n    is set, it is called to handle the error with arguments (func,\n    path, exc_info) where func is os.listdir, os.remove, or os.rmdir;\n    path is the argument to that function that caused it to fail; and\n    exc_info is a tuple returned by sys.exc_info().  If ignore_errors\n    is false and onerror is None, an exception is raised.\n\n    "));
      var1.setlocal("rmtree", var12);
      var3 = null;
      var1.setline(259);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _basename$17, (PyObject)null);
      var1.setlocal("_basename", var12);
      var3 = null;
      var1.setline(264);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, move$18, PyString.fromInterned("Recursively move a file or directory to another location. This is\n    similar to the Unix \"mv\" command.\n\n    If the destination is a directory or a symlink to a directory, the source\n    is moved inside the directory. The destination path must not already\n    exist.\n\n    If the destination already exists but is not a directory, it may be\n    overwritten depending on os.rename() semantics.\n\n    If the destination is on our current filesystem, then rename() is used.\n    Otherwise, src is copied to the destination and then removed.\n    A lot more could be done here...  A look at a mv.c shows a lot of\n    the issues this implementation glosses over.\n\n    "));
      var1.setlocal("move", var12);
      var3 = null;
      var1.setline(304);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _destinsrc$19, (PyObject)null);
      var1.setlocal("_destinsrc", var12);
      var3 = null;
      var1.setline(313);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _get_gid$20, PyString.fromInterned("Returns a gid, given a group name."));
      var1.setlocal("_get_gid", var12);
      var3 = null;
      var1.setline(325);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, _get_uid$21, PyString.fromInterned("Returns an uid, given a user name."));
      var1.setlocal("_get_uid", var12);
      var3 = null;
      var1.setline(337);
      var9 = new PyObject[]{PyString.fromInterned("gzip"), Py.newInteger(0), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var9, _make_tarball$22, PyString.fromInterned("Create a (possibly compressed) tar file from all the files under\n    'base_dir'.\n\n    'compress' must be \"gzip\" (the default), \"bzip2\", or None.\n\n    'owner' and 'group' can be used to define an owner and a group for the\n    archive that is being built. If not provided, the current owner and group\n    will be used.\n\n    The output tar file will be named 'base_name' +  \".tar\", possibly plus\n    the appropriate compression extension (\".gz\", or \".bz2\").\n\n    Returns the output filename.\n    "));
      var1.setlocal("_make_tarball", var12);
      var3 = null;
      var1.setline(398);
      var9 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var12 = new PyFunction(var1.f_globals, var9, _call_external_zip$24, (PyObject)null);
      var1.setlocal("_call_external_zip", var12);
      var3 = null;
      var1.setline(416);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var9, _make_zipfile$25, PyString.fromInterned("Create a zip file from all the files under 'base_dir'.\n\n    The output zip file will be named 'base_name' + \".zip\".  Uses either the\n    \"zipfile\" Python module (if available) or the InfoZIP \"zip\" utility\n    (if installed and found on the default search path).  If neither tool is\n    available, raises ExecError.  Returns the name of the output zip\n    file.\n    "));
      var1.setlocal("_make_zipfile", var12);
      var3 = null;
      var1.setline(463);
      PyDictionary var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("gztar"), new PyTuple(new PyObject[]{var1.getname("_make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), PyString.fromInterned("gzip")})}), PyString.fromInterned("gzip'ed tar-file")}), PyString.fromInterned("bztar"), new PyTuple(new PyObject[]{var1.getname("_make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), PyString.fromInterned("bzip2")})}), PyString.fromInterned("bzip2'ed tar-file")}), PyString.fromInterned("tar"), new PyTuple(new PyObject[]{var1.getname("_make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), var1.getname("None")})}), PyString.fromInterned("uncompressed tar file")}), PyString.fromInterned("zip"), new PyTuple(new PyObject[]{var1.getname("_make_zipfile"), new PyList(Py.EmptyObjects), PyString.fromInterned("ZIP file")})});
      var1.setlocal("_ARCHIVE_FORMATS", var13);
      var3 = null;
      var1.setline(470);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, get_archive_formats$26, PyString.fromInterned("Returns a list of supported formats for archiving and unarchiving.\n\n    Each element of the returned sequence is a tuple (name, description)\n    "));
      var1.setlocal("get_archive_formats", var12);
      var3 = null;
      var1.setline(480);
      var9 = new PyObject[]{var1.getname("None"), PyString.fromInterned("")};
      var12 = new PyFunction(var1.f_globals, var9, register_archive_format$27, PyString.fromInterned("Registers an archive format.\n\n    name is the name of the format. function is the callable that will be\n    used to create archives. If provided, extra_args is a sequence of\n    (name, value) tuples that will be passed as arguments to the callable.\n    description can be provided to describe the format, and will be returned\n    by the get_archive_formats() function.\n    "));
      var1.setlocal("register_archive_format", var12);
      var3 = null;
      var1.setline(501);
      var9 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var9, unregister_archive_format$28, (PyObject)null);
      var1.setlocal("unregister_archive_format", var12);
      var3 = null;
      var1.setline(504);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var9, make_archive$29, PyString.fromInterned("Create an archive file (eg. zip or tar).\n\n    'base_name' is the name of the file to create, minus any format-specific\n    extension; 'format' is the archive format: one of \"zip\", \"tar\", \"bztar\"\n    or \"gztar\".\n\n    'root_dir' is a directory that will be the root directory of the\n    archive; ie. we typically chdir into 'root_dir' before creating the\n    archive.  'base_dir' is the directory where we start archiving from;\n    ie. 'base_dir' will be the common prefix of all files and\n    directories in the archive.  'root_dir' and 'base_dir' both default\n    to the current directory.  Returns the name of the archive file.\n\n    'owner' and 'group' are used when creating a tar archive. By default,\n    uses the current owner and group.\n    "));
      var1.setlocal("make_archive", var12);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      return var1.getf_locals();
   }

   public PyObject SpecialFileError$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when trying to do a kind of operation (e.g. copying) which is\n    not supported on a special file (e.g. a named pipe)"));
      var1.setline(36);
      PyString.fromInterned("Raised when trying to do a kind of operation (e.g. copying) which is\n    not supported on a special file (e.g. a named pipe)");
      return var1.getf_locals();
   }

   public PyObject ExecError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when a command could not be executed"));
      var1.setline(39);
      PyString.fromInterned("Raised when a command could not be executed");
      return var1.getf_locals();
   }

   public PyObject copyfileobj$4(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyString.fromInterned("copy data from file-like object fsrc to file-like object fdst");

      while(true) {
         var1.setline(48);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(49);
         PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(50);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            break;
         }

         var1.setline(52);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _samefile$5(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path"), (PyObject)PyString.fromInterned("samefile")).__nonzero__()) {
         try {
            var1.setline(58);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("samefile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("OSError"))) {
               var1.setline(60);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var6;
            }
         }
      } else {
         var1.setline(63);
         PyObject var4 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0)));
         PyObject var10000 = var4._eq(var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1))));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject copyfile$6(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(67);
      PyString.fromInterned("Copy data from src to dst");
      var1.setline(68);
      if (var1.getglobal("_samefile").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__nonzero__()) {
         var1.setline(69);
         throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("`%s` and `%s` are the same file")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}))));
      } else {
         var1.setline(71);
         PyObject var3 = (new PyList(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})).__iter__();

         while(true) {
            var1.setline(71);
            PyObject var4 = var3.__iternext__();
            PyObject var11;
            if (var4 == null) {
               ContextManager var9;
               var4 = (var9 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

               label43: {
                  try {
                     label60: {
                        var1.setlocal(4, var4);
                        ContextManager var10;
                        var11 = (var10 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb")))).__enter__(var2);

                        try {
                           var1.setlocal(5, var11);
                           var1.setline(84);
                           var1.getglobal("copyfileobj").__call__(var2, var1.getlocal(4), var1.getlocal(5));
                        } catch (Throwable var6) {
                           if (var10.__exit__(var2, Py.setException(var6, var1))) {
                              break label60;
                           }

                           throw (Throwable)Py.makeException();
                        }

                        var10.__exit__(var2, (PyException)null);
                     }
                  } catch (Throwable var7) {
                     if (var9.__exit__(var2, Py.setException(var7, var1))) {
                        break label43;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var9.__exit__(var2, (PyException)null);
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);

            PyException var5;
            try {
               var1.setline(73);
               var11 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var11);
               var5 = null;
            } catch (Throwable var8) {
               var5 = Py.setException(var8, var1);
               if (var5.match(var1.getglobal("OSError"))) {
                  var1.setline(76);
                  continue;
               }

               throw var5;
            }

            var1.setline(79);
            if (var1.getglobal("stat").__getattr__("S_ISFIFO").__call__(var2, var1.getlocal(3).__getattr__("st_mode")).__nonzero__()) {
               var1.setline(80);
               throw Py.makeException(var1.getglobal("SpecialFileError").__call__(var2, PyString.fromInterned("`%s` is a named pipe")._mod(var1.getlocal(2))));
            }
         }
      }
   }

   public PyObject copymode$7(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("Copy mode bits from src to dst");
      var1.setline(88);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("chmod")).__nonzero__()) {
         var1.setline(89);
         PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(90);
         var3 = var1.getglobal("stat").__getattr__("S_IMODE").__call__(var2, var1.getlocal(2).__getattr__("st_mode"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(91);
         var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copystat$8(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Copy all stat info (mode bits, atime, mtime, flags) from src to dst");
      var1.setline(95);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(96);
      var3 = var1.getglobal("stat").__getattr__("S_IMODE").__call__(var2, var1.getlocal(2).__getattr__("st_mode"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(97);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("utime")).__nonzero__()) {
         var1.setline(98);
         var1.getglobal("os").__getattr__("utime").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("st_atime"), var1.getlocal(2).__getattr__("st_mtime")})));
      }

      var1.setline(99);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("chmod")).__nonzero__()) {
         var1.setline(100);
         var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      }

      var1.setline(101);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("chflags"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("st_flags"));
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(103);
            var1.getglobal("os").__getattr__("chflags").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getattr__("st_flags"));
         } catch (Throwable var7) {
            label43: {
               PyException var8 = Py.setException(var7, var1);
               if (var8.match(var1.getglobal("OSError"))) {
                  PyObject var4 = var8.value;
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(105);
                  var4 = (new PyTuple(new PyObject[]{PyString.fromInterned("EOPNOTSUPP"), PyString.fromInterned("ENOTSUP")})).__iter__();

                  while(true) {
                     var1.setline(105);
                     PyObject var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(109);
                        throw Py.makeException();
                     }

                     var1.setlocal(5, var5);
                     var1.setline(106);
                     var10000 = var1.getglobal("hasattr").__call__(var2, var1.getglobal("errno"), var1.getlocal(5));
                     if (var10000.__nonzero__()) {
                        PyObject var6 = var1.getlocal(4).__getattr__("errno");
                        var10000 = var6._eq(var1.getglobal("getattr").__call__(var2, var1.getglobal("errno"), var1.getlocal(5)));
                        var6 = null;
                     }

                     if (var10000.__nonzero__()) {
                        break label43;
                     }
                  }
               }

               throw var8;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$9(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyString.fromInterned("Copy data and mode bits (\"cp src dst\").\n\n    The destination may be a directory.\n\n    ");
      var1.setline(117);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(118);
         PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0)));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(119);
      var1.getglobal("copyfile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(120);
      var1.getglobal("copymode").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy2$10(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned("Copy data and all stat info (\"cp -p src dst\").\n\n    The destination may be a directory.\n\n    ");
      var1.setline(128);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(129);
         PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0)));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(130);
      var1.getglobal("copyfile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(131);
      var1.getglobal("copystat").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignore_patterns$11(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(137);
      PyString.fromInterned("Function that can be used as copytree() ignore parameter.\n\n    Patterns is a sequence of glob-style patterns\n    that are used to exclude files");
      var1.setline(138);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = _ignore_patterns$12;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(143);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _ignore_patterns$12(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(140);
      PyObject var5 = var1.getderef(0).__iter__();

      while(true) {
         var1.setline(140);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(142);
            var5 = var1.getglobal("set").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(141);
         var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getglobal("fnmatch").__getattr__("filter").__call__(var2, var1.getlocal(1), var1.getlocal(3)));
      }
   }

   public PyObject copytree$13(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Recursively copy a directory tree using copy2().\n\n    The destination directory must not already exist.\n    If exception(s) occur, an Error is raised with a list of reasons.\n\n    If the optional symlinks flag is true, symbolic links in the\n    source tree result in symbolic links in the destination tree; if\n    it is false, the contents of the files pointed to by symbolic\n    links are copied.\n\n    The optional ignore argument is a callable. If given, it\n    is called with the `src` parameter, which is the directory\n    being visited by copytree(), and `names` which is the list of\n    `src` contents, as returned by os.listdir():\n\n        callable(src, names) -> ignored_names\n\n    Since copytree() is called recursively, the callable will be\n    called once for each directory that is copied. It returns a\n    list of names relative to the `src` directory that should\n    not be copied.\n\n    XXX Consider this example code rather than the ultimate tool.\n\n    ");
      var1.setline(171);
      PyObject var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(175);
         var3 = var1.getglobal("set").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(177);
      var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(1));
      var1.setline(178);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(179);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         PyObject var5;
         do {
            var1.setline(179);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               try {
                  var1.setline(200);
                  var1.getglobal("copystat").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (!var10.match(var1.getglobal("OSError"))) {
                     throw var10;
                  }

                  var4 = var10.value;
                  var1.setlocal(12, var4);
                  var4 = null;
                  var1.setline(202);
                  var4 = var1.getglobal("WindowsError");
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(12), var1.getglobal("WindowsError"));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(204);
                  } else {
                     var1.setline(206);
                     var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getglobal("str").__call__(var2, var1.getlocal(12))})));
                  }
               }

               var1.setline(207);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(208);
                  throw Py.makeException(var1.getglobal("Error"), var1.getlocal(6));
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(7, var4);
            var1.setline(180);
            var5 = var1.getlocal(7);
            var10000 = var5._in(var1.getlocal(5));
            var5 = null;
         } while(var10000.__nonzero__());

         var1.setline(182);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(7));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(183);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(7));
         var1.setlocal(9, var5);
         var5 = null;

         try {
            var1.setline(185);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(8));
            }

            if (var10000.__nonzero__()) {
               var1.setline(186);
               var5 = var1.getglobal("os").__getattr__("readlink").__call__(var2, var1.getlocal(8));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(187);
               var1.getglobal("os").__getattr__("symlink").__call__(var2, var1.getlocal(10), var1.getlocal(9));
            } else {
               var1.setline(188);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                  var1.setline(189);
                  var1.getglobal("copytree").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(2), var1.getlocal(3));
               } else {
                  var1.setline(192);
                  var1.getglobal("copy2").__call__(var2, var1.getlocal(8), var1.getlocal(9));
               }
            }
         } catch (Throwable var8) {
            PyException var11 = Py.setException(var8, var1);
            PyObject var6;
            if (var11.match(var1.getglobal("Error"))) {
               var6 = var11.value;
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(196);
               var1.getlocal(6).__getattr__("extend").__call__(var2, var1.getlocal(11).__getattr__("args").__getitem__(Py.newInteger(0)));
            } else {
               if (!var11.match(var1.getglobal("EnvironmentError"))) {
                  throw var11;
               }

               var6 = var11.value;
               var1.setlocal(12, var6);
               var6 = null;
               var1.setline(198);
               var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9), var1.getglobal("str").__call__(var2, var1.getlocal(12))})));
            }
         }
      }
   }

   public PyObject rmtree$14(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyString.fromInterned("Recursively delete a directory tree.\n\n    If ignore_errors is set, errors are ignored; otherwise, if onerror\n    is set, it is called to handle the error with arguments (func,\n    path, exc_info) where func is os.listdir, os.remove, or os.rmdir;\n    path is the argument to that function that caused it to fail; and\n    exc_info is a tuple returned by sys.exc_info().  If ignore_errors\n    is false and onerror is None, an exception is raised.\n\n    ");
      var1.setline(221);
      PyObject[] var3;
      PyFunction var12;
      PyObject var13;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(222);
         var3 = Py.EmptyObjects;
         var12 = new PyFunction(var1.f_globals, var3, onerror$15, (PyObject)null);
         var1.setlocal(2, var12);
         var3 = null;
      } else {
         var1.setline(224);
         var13 = var1.getlocal(2);
         PyObject var10000 = var13._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(225);
            var3 = Py.EmptyObjects;
            var12 = new PyFunction(var1.f_globals, var3, onerror$16, (PyObject)null);
            var1.setlocal(2, var12);
            var3 = null;
         }
      }

      PyException var14;
      try {
         var1.setline(228);
         if (var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(230);
            throw Py.makeException(var1.getglobal("OSError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot call rmtree on a symbolic link")));
         }
      } catch (Throwable var11) {
         var14 = Py.setException(var11, var1);
         if (var14.match(var1.getglobal("OSError"))) {
            var1.setline(232);
            var1.getlocal(2).__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("islink"), var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
            var1.setline(234);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var14;
      }

      var1.setline(235);
      PyList var16 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var16);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(237);
         var13 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var13);
         var3 = null;
      } catch (Throwable var10) {
         var14 = Py.setException(var10, var1);
         if (!var14.match(var1.getglobal("os").__getattr__("error"))) {
            throw var14;
         }

         var4 = var14.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(239);
         var1.getlocal(2).__call__(var2, var1.getglobal("os").__getattr__("listdir"), var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
      }

      var1.setline(240);
      var13 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(240);
         var4 = var13.__iternext__();
         if (var4 == null) {
            try {
               var1.setline(254);
               var1.getglobal("os").__getattr__("rmdir").__call__(var2, var1.getlocal(0));
            } catch (Throwable var7) {
               var14 = Py.setException(var7, var1);
               if (!var14.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var14;
               }

               var1.setline(256);
               var1.getlocal(2).__call__(var2, var1.getglobal("os").__getattr__("rmdir"), var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(241);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;

         PyInteger var6;
         PyException var15;
         try {
            var1.setline(243);
            var5 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(6)).__getattr__("st_mode");
            var1.setlocal(7, var5);
            var5 = null;
         } catch (Throwable var9) {
            var15 = Py.setException(var9, var1);
            if (!var15.match(var1.getglobal("os").__getattr__("error"))) {
               throw var15;
            }

            var1.setline(245);
            var6 = Py.newInteger(0);
            var1.setlocal(7, var6);
            var6 = null;
         }

         var1.setline(246);
         if (var1.getglobal("stat").__getattr__("S_ISDIR").__call__(var2, var1.getlocal(7)).__nonzero__()) {
            var1.setline(247);
            var1.getglobal("rmtree").__call__(var2, var1.getlocal(6), var1.getlocal(1), var1.getlocal(2));
         } else {
            try {
               var1.setline(250);
               var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(6));
            } catch (Throwable var8) {
               var15 = Py.setException(var8, var1);
               if (!var15.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var15;
               }

               PyObject var17 = var15.value;
               var1.setlocal(4, var17);
               var6 = null;
               var1.setline(252);
               var1.getlocal(2).__call__(var2, var1.getglobal("os").__getattr__("remove"), var1.getlocal(6), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
            }
         }
      }
   }

   public PyObject onerror$15(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject onerror$16(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      throw Py.makeException();
   }

   public PyObject _basename$17(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0).__getattr__("rstrip").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("sep")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject move$18(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString.fromInterned("Recursively move a file or directory to another location. This is\n    similar to the Unix \"mv\" command.\n\n    If the destination is a directory or a symlink to a directory, the source\n    is moved inside the directory. The destination path must not already\n    exist.\n\n    If the destination already exists but is not a directory, it may be\n    overwritten depending on os.rename() semantics.\n\n    If the destination is on our current filesystem, then rename() is used.\n    Otherwise, src is copied to the destination and then removed.\n    A lot more could be done here...  A look at a mv.c shows a lot of\n    the issues this implementation glosses over.\n\n    ");
      var1.setline(281);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(282);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(283);
         if (var1.getglobal("_samefile").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__nonzero__()) {
            var1.setline(286);
            var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setline(287);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(289);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getglobal("_basename").__call__(var2, var1.getlocal(0)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(290);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__nonzero__()) {
            var1.setline(291);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Destination path '%s' already exists")._mod(var1.getlocal(2)));
         }
      }

      try {
         var1.setline(293);
         var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("OSError"))) {
            throw var7;
         }

         var1.setline(295);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(296);
            if (var1.getglobal("_destinsrc").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__nonzero__()) {
               var1.setline(297);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Cannot move a directory '%s' into itself '%s'.")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
            }

            var1.setline(298);
            PyObject var10000 = var1.getglobal("copytree");
            PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(2), var1.getglobal("True")};
            String[] var5 = new String[]{"symlinks"};
            var10000.__call__(var2, var4, var5);
            var4 = null;
            var1.setline(299);
            var1.getglobal("rmtree").__call__(var2, var1.getlocal(0));
         } else {
            var1.setline(301);
            var1.getglobal("copy2").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.setline(302);
            var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(0));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _destinsrc$19(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyObject var3 = var1.getglobal("abspath").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(306);
      var3 = var1.getglobal("abspath").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(307);
      if (var1.getlocal(0).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("sep")).__not__().__nonzero__()) {
         var1.setline(308);
         var3 = var1.getlocal(0);
         var3 = var3._iadd(var1.getglobal("os").__getattr__("path").__getattr__("sep"));
         var1.setlocal(0, var3);
      }

      var1.setline(309);
      if (var1.getlocal(1).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("sep")).__not__().__nonzero__()) {
         var1.setline(310);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(var1.getglobal("os").__getattr__("path").__getattr__("sep"));
         var1.setlocal(1, var3);
      }

      var1.setline(311);
      var3 = var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_gid$20(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyString.fromInterned("Returns a gid, given a group name.");
      var1.setline(315);
      PyObject var3 = var1.getglobal("getgrnam");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(316);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var7;
         try {
            var1.setline(318);
            var7 = var1.getglobal("getgrnam").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var7);
            var4 = null;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("KeyError"))) {
               throw var4;
            }

            var1.setline(320);
            PyObject var5 = var1.getglobal("None");
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(321);
         var7 = var1.getlocal(1);
         var10000 = var7._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(322);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(323);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _get_uid$21(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyString.fromInterned("Returns an uid, given a user name.");
      var1.setline(327);
      PyObject var3 = var1.getglobal("getpwnam");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(328);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var7;
         try {
            var1.setline(330);
            var7 = var1.getglobal("getpwnam").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var7);
            var4 = null;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("KeyError"))) {
               throw var4;
            }

            var1.setline(332);
            PyObject var5 = var1.getglobal("None");
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(333);
         var7 = var1.getlocal(1);
         var10000 = var7._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(334);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(335);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _make_tarball$22(PyFrame var1, ThreadState var2) {
      var1.to_cell(5, 0);
      var1.to_cell(6, 3);
      var1.setline(352);
      PyString.fromInterned("Create a (possibly compressed) tar file from all the files under\n    'base_dir'.\n\n    'compress' must be \"gzip\" (the default), \"bzip2\", or None.\n\n    'owner' and 'group' can be used to define an owner and a group for the\n    archive that is being built. If not provided, the current owner and group\n    will be used.\n\n    The output tar file will be named 'base_name' +  \".tar\", possibly plus\n    the appropriate compression extension (\".gz\", or \".bz2\").\n\n    Returns the output filename.\n    ");
      var1.setline(353);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("gzip"), PyString.fromInterned("gz"), PyString.fromInterned("bzip2"), PyString.fromInterned("bz2"), var1.getglobal("None"), PyString.fromInterned("")});
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(354);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("gzip"), PyString.fromInterned(".gz"), PyString.fromInterned("bzip2"), PyString.fromInterned(".bz2")});
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(357);
      PyObject var7 = var1.getlocal(2);
      PyObject var10000 = var7._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(2);
         var10000 = var7._notin(var1.getlocal(9).__getattr__("keys").__call__(var2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(358);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("bad value for 'compress': must be None, 'gzip' or 'bzip2'"));
      } else {
         var1.setline(361);
         var7 = var1.getlocal(0)._add(PyString.fromInterned(".tar"))._add(var1.getlocal(9).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("")));
         var1.setlocal(10, var7);
         var3 = null;
         var1.setline(362);
         var7 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(10));
         var1.setlocal(11, var7);
         var3 = null;
         var1.setline(364);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(11)).__not__().__nonzero__()) {
            var1.setline(365);
            var7 = var1.getlocal(7);
            var10000 = var7._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(366);
               var1.getlocal(7).__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("creating %s"), (PyObject)var1.getlocal(11));
            }

            var1.setline(367);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(368);
               var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(11));
            }
         }

         var1.setline(372);
         var7 = imp.importOne("tarfile", var1, -1);
         var1.setlocal(12, var7);
         var3 = null;
         var1.setline(374);
         var7 = var1.getlocal(7);
         var10000 = var7._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(375);
            var1.getlocal(7).__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Creating tar archive"));
         }

         var1.setline(377);
         var7 = var1.getglobal("_get_uid").__call__(var2, var1.getderef(0));
         var1.setderef(1, var7);
         var3 = null;
         var1.setline(378);
         var7 = var1.getglobal("_get_gid").__call__(var2, var1.getderef(3));
         var1.setderef(2, var7);
         var3 = null;
         var1.setline(380);
         PyObject[] var8 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var8;
         PyCode var10004 = _set_uid_gid$23;
         var8 = new PyObject[]{var1.getclosure(2), var1.getclosure(3), var1.getclosure(1), var1.getclosure(0)};
         PyFunction var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var8);
         var1.setlocal(13, var9);
         var3 = null;
         var1.setline(389);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(390);
            var7 = var1.getlocal(12).__getattr__("open").__call__(var2, var1.getlocal(10), PyString.fromInterned("w|%s")._mod(var1.getlocal(8).__getitem__(var1.getlocal(2))));
            var1.setlocal(14, var7);
            var3 = null;
            var3 = null;

            try {
               var1.setline(392);
               var10000 = var1.getlocal(14).__getattr__("add");
               PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(13)};
               String[] var5 = new String[]{"filter"};
               var10000.__call__(var2, var4, var5);
               var4 = null;
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(394);
               var1.getlocal(14).__getattr__("close").__call__(var2);
               throw (Throwable)var6;
            }

            var1.setline(394);
            var1.getlocal(14).__getattr__("close").__call__(var2);
         }

         var1.setline(396);
         var7 = var1.getlocal(10);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _set_uid_gid$23(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(382);
         var3 = var1.getderef(0);
         var1.getlocal(0).__setattr__("gid", var3);
         var3 = null;
         var1.setline(383);
         var3 = var1.getderef(1);
         var1.getlocal(0).__setattr__("gname", var3);
         var3 = null;
      }

      var1.setline(384);
      var3 = var1.getderef(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(385);
         var3 = var1.getderef(2);
         var1.getlocal(0).__setattr__("uid", var3);
         var3 = null;
         var1.setline(386);
         var3 = var1.getderef(3);
         var1.getlocal(0).__setattr__("uname", var3);
         var3 = null;
      }

      var1.setline(387);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _call_external_zip$24(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyString var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(401);
         var3 = PyString.fromInterned("-r");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(403);
         var3 = PyString.fromInterned("-rq");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(404);
      String[] var6 = new String[]{"DistutilsExecError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(405);
      var6 = new String[]{"spawn"};
      var7 = imp.importFrom("distutils.spawn", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal(6, var4);
      var4 = null;

      try {
         var1.setline(407);
         PyObject var10000 = var1.getlocal(6);
         var7 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("zip"), var1.getlocal(4), var1.getlocal(1), var1.getlocal(0)}), var1.getlocal(3)};
         String[] var8 = new String[]{"dry_run"};
         var10000.__call__(var2, var7, var8);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (var9.match(var1.getlocal(5))) {
            var1.setline(411);
            throw Py.makeException(var1.getglobal("ExecError"), PyString.fromInterned("unable to create zip file '%s': could neither import the 'zipfile' module nor find a standalone zip utility")._mod(var1.getlocal(1)));
         }

         throw var9;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _make_zipfile$25(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      PyString.fromInterned("Create a zip file from all the files under 'base_dir'.\n\n    The output zip file will be named 'base_name' + \".zip\".  Uses either the\n    \"zipfile\" Python module (if available) or the InfoZIP \"zip\" utility\n    (if installed and found on the default search path).  If neither tool is\n    available, raises ExecError.  Returns the name of the output zip\n    file.\n    ");
      var1.setline(425);
      PyObject var3 = var1.getlocal(0)._add(PyString.fromInterned(".zip"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(426);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(428);
      PyObject var10000;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)).__not__().__nonzero__()) {
         var1.setline(429);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(430);
            var1.getlocal(4).__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("creating %s"), (PyObject)var1.getlocal(6));
         }

         var1.setline(431);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(432);
            var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(6));
         }
      }

      PyObject var4;
      try {
         var1.setline(437);
         var3 = imp.importOne("zipfile", var1, -1);
         var1.setlocal(7, var3);
         var3 = null;
      } catch (Throwable var8) {
         PyException var10 = Py.setException(var8, var1);
         if (!var10.match(var1.getglobal("ImportError"))) {
            throw var10;
         }

         var1.setline(439);
         var4 = var1.getglobal("None");
         var1.setlocal(7, var4);
         var4 = null;
      }

      var1.setline(441);
      var3 = var1.getlocal(7);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(442);
         var1.getglobal("_call_external_zip").__call__(var2, var1.getlocal(1), var1.getlocal(5), var1.getlocal(2), var1.getlocal(3));
      } else {
         var1.setline(444);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(445);
            var1.getlocal(4).__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("creating '%s' and adding '%s' to it"), (PyObject)var1.getlocal(5), (PyObject)var1.getlocal(1));
         }

         var1.setline(448);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(449);
            var10000 = var1.getlocal(7).__getattr__("ZipFile");
            PyObject[] var12 = new PyObject[]{var1.getlocal(5), PyString.fromInterned("w"), var1.getlocal(7).__getattr__("ZIP_DEFLATED")};
            String[] var9 = new String[]{"compression"};
            var10000 = var10000.__call__(var2, var12, var9);
            var3 = null;
            var3 = var10000;
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(452);
            var3 = var1.getglobal("os").__getattr__("walk").__call__(var2, var1.getlocal(1)).__iter__();

            while(true) {
               var1.setline(452);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(459);
                  var1.getlocal(8).__getattr__("close").__call__(var2);
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 3);
               PyObject var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(453);
               PyObject var11 = var1.getlocal(11).__iter__();

               while(true) {
                  var1.setline(453);
                  var6 = var11.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(12, var6);
                  var1.setline(454);
                  PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(9), var1.getlocal(12)));
                  var1.setlocal(13, var7);
                  var7 = null;
                  var1.setline(455);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(13)).__nonzero__()) {
                     var1.setline(456);
                     var1.getlocal(8).__getattr__("write").__call__(var2, var1.getlocal(13), var1.getlocal(13));
                     var1.setline(457);
                     var7 = var1.getlocal(4);
                     var10000 = var7._isnot(var1.getglobal("None"));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(458);
                        var1.getlocal(4).__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("adding '%s'"), (PyObject)var1.getlocal(13));
                     }
                  }
               }
            }
         }
      }

      var1.setline(461);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_archive_formats$26(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      PyString.fromInterned("Returns a list of supported formats for archiving and unarchiving.\n\n    Each element of the returned sequence is a tuple (name, description)\n    ");
      var1.setline(475);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getglobal("_ARCHIVE_FORMATS").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(475);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(475);
            var1.dellocal(1);
            PyList var7 = var10000;
            var1.setlocal(0, var7);
            var3 = null;
            var1.setline(477);
            var1.getlocal(0).__getattr__("sort").__call__(var2);
            var1.setline(478);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(475);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3).__getitem__(Py.newInteger(2))})));
      }
   }

   public PyObject register_archive_format$27(PyFrame var1, ThreadState var2) {
      var1.setline(488);
      PyString.fromInterned("Registers an archive format.\n\n    name is the name of the format. function is the callable that will be\n    used to create archives. If provided, extra_args is a sequence of\n    (name, value) tuples that will be passed as arguments to the callable.\n    description can be provided to describe the format, and will be returned\n    by the get_archive_formats() function.\n    ");
      var1.setline(489);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(490);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(491);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("collections").__getattr__("Callable")).__not__().__nonzero__()) {
         var1.setline(492);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("The %s object is not callable")._mod(var1.getlocal(1))));
      } else {
         var1.setline(493);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("tuple"), var1.getglobal("list")}))).__not__().__nonzero__()) {
            var1.setline(494);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("extra_args needs to be a sequence")));
         } else {
            var1.setline(495);
            var3 = var1.getlocal(2).__iter__();

            do {
               var1.setline(495);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(499);
                  PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
                  var1.getglobal("_ARCHIVE_FORMATS").__setitem__((PyObject)var1.getlocal(0), var7);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(496);
               var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("tuple"), var1.getglobal("list")}))).__not__();
               if (!var10000.__nonzero__()) {
                  PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
                  var10000 = var5._ne(Py.newInteger(2));
                  var5 = null;
               }
            } while(!var10000.__nonzero__());

            var1.setline(497);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("extra_args elements are : (arg_name, value)")));
         }
      }
   }

   public PyObject unregister_archive_format$28(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      var1.getglobal("_ARCHIVE_FORMATS").__delitem__(var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_archive$29(PyFrame var1, ThreadState var2) {
      var1.setline(521);
      PyString.fromInterned("Create an archive file (eg. zip or tar).\n\n    'base_name' is the name of the file to create, minus any format-specific\n    extension; 'format' is the archive format: one of \"zip\", \"tar\", \"bztar\"\n    or \"gztar\".\n\n    'root_dir' is a directory that will be the root directory of the\n    archive; ie. we typically chdir into 'root_dir' before creating the\n    archive.  'base_dir' is the directory where we start archiving from;\n    ie. 'base_dir' will be the common prefix of all files and\n    directories in the archive.  'root_dir' and 'base_dir' both default\n    to the current directory.  Returns the name of the archive file.\n\n    'owner' and 'group' are used when creating a tar archive. By default,\n    uses the current owner and group.\n    ");
      var1.setline(522);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(523);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(524);
         var3 = var1.getlocal(8);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(525);
            var1.getlocal(8).__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing into '%s'"), (PyObject)var1.getlocal(2));
         }

         var1.setline(526);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(527);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(528);
            var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
         }
      }

      var1.setline(530);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(531);
         var3 = var1.getglobal("os").__getattr__("curdir");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(533);
      PyDictionary var12 = new PyDictionary(new PyObject[]{PyString.fromInterned("dry_run"), var1.getlocal(5), PyString.fromInterned("logger"), var1.getlocal(8)});
      var1.setlocal(10, var12);
      var3 = null;

      try {
         var1.setline(536);
         var3 = var1.getglobal("_ARCHIVE_FORMATS").__getitem__(var1.getlocal(1));
         var1.setlocal(11, var3);
         var3 = null;
      } catch (Throwable var8) {
         PyException var13 = Py.setException(var8, var1);
         if (var13.match(var1.getglobal("KeyError"))) {
            var1.setline(538);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown archive format '%s'")._mod(var1.getlocal(1)));
         }

         throw var13;
      }

      var1.setline(540);
      var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(541);
      var3 = var1.getlocal(11).__getitem__(Py.newInteger(1)).__iter__();

      while(true) {
         var1.setline(541);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(544);
            var3 = var1.getlocal(1);
            var10000 = var3._ne(PyString.fromInterned("zip"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(545);
               var3 = var1.getlocal(6);
               var1.getlocal(10).__setitem__((PyObject)PyString.fromInterned("owner"), var3);
               var3 = null;
               var1.setline(546);
               var3 = var1.getlocal(7);
               var1.getlocal(10).__setitem__((PyObject)PyString.fromInterned("group"), var3);
               var3 = null;
            }

            var3 = null;

            try {
               var1.setline(549);
               var10000 = var1.getlocal(12);
               PyObject[] var10 = new PyObject[]{var1.getlocal(0), var1.getlocal(3)};
               String[] var11 = new String[0];
               var10000 = var10000._callextra(var10, var11, (PyObject)null, var1.getlocal(10));
               var4 = null;
               var4 = var10000;
               var1.setlocal(15, var4);
               var4 = null;
            } catch (Throwable var7) {
               Py.addTraceback(var7, var1);
               var1.setline(551);
               var4 = var1.getlocal(2);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(552);
                  var4 = var1.getlocal(8);
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(553);
                     var1.getlocal(8).__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing back to '%s'"), (PyObject)var1.getlocal(9));
                  }

                  var1.setline(554);
                  var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(9));
               }

               throw (Throwable)var7;
            }

            var1.setline(551);
            var4 = var1.getlocal(2);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(552);
               var4 = var1.getlocal(8);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(553);
                  var1.getlocal(8).__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing back to '%s'"), (PyObject)var1.getlocal(9));
               }

               var1.setline(554);
               var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(9));
            }

            var1.setline(556);
            var3 = var1.getlocal(15);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(13, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(14, var6);
         var6 = null;
         var1.setline(542);
         PyObject var9 = var1.getlocal(14);
         var1.getlocal(10).__setitem__(var1.getlocal(13), var9);
         var5 = null;
      }
   }

   public shutil$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 31, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SpecialFileError$2 = Py.newCode(0, var2, var1, "SpecialFileError", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ExecError$3 = Py.newCode(0, var2, var1, "ExecError", 38, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fsrc", "fdst", "length", "buf"};
      copyfileobj$4 = Py.newCode(3, var2, var1, "copyfileobj", 46, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst"};
      _samefile$5 = Py.newCode(2, var2, var1, "_samefile", 54, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "fn", "st", "fsrc", "fdst"};
      copyfile$6 = Py.newCode(2, var2, var1, "copyfile", 66, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "st", "mode"};
      copymode$7 = Py.newCode(2, var2, var1, "copymode", 86, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "st", "mode", "why", "err"};
      copystat$8 = Py.newCode(2, var2, var1, "copystat", 93, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst"};
      copy$9 = Py.newCode(2, var2, var1, "copy", 111, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst"};
      copy2$10 = Py.newCode(2, var2, var1, "copy2", 122, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"patterns", "_ignore_patterns"};
      String[] var10001 = var2;
      shutil$py var10007 = self;
      var2 = new String[]{"patterns"};
      ignore_patterns$11 = Py.newCode(1, var10001, var1, "ignore_patterns", 133, true, false, var10007, 11, var2, (String[])null, 0, 4097);
      var2 = new String[]{"path", "names", "ignored_names", "pattern"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"patterns"};
      _ignore_patterns$12 = Py.newCode(2, var10001, var1, "_ignore_patterns", 138, false, false, var10007, 12, (String[])null, var2, 0, 4097);
      var2 = new String[]{"src", "dst", "symlinks", "ignore", "names", "ignored_names", "errors", "name", "srcname", "dstname", "linkto", "err", "why"};
      copytree$13 = Py.newCode(4, var2, var1, "copytree", 145, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "ignore_errors", "onerror", "names", "err", "name", "fullname", "mode"};
      rmtree$14 = Py.newCode(3, var2, var1, "rmtree", 210, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      onerror$15 = Py.newCode(1, var2, var1, "onerror", 222, true, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      onerror$16 = Py.newCode(1, var2, var1, "onerror", 225, true, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      _basename$17 = Py.newCode(1, var2, var1, "_basename", 259, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "real_dst"};
      move$18 = Py.newCode(2, var2, var1, "move", 264, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst"};
      _destinsrc$19 = Py.newCode(2, var2, var1, "_destinsrc", 304, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "result"};
      _get_gid$20 = Py.newCode(1, var2, var1, "_get_gid", 313, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "result"};
      _get_uid$21 = Py.newCode(1, var2, var1, "_get_uid", 325, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base_name", "base_dir", "compress", "verbose", "dry_run", "owner", "group", "logger", "tar_compression", "compress_ext", "archive_name", "archive_dir", "tarfile", "_set_uid_gid", "tar", "uid", "gid"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"owner", "uid", "gid", "group"};
      _make_tarball$22 = Py.newCode(8, var10001, var1, "_make_tarball", 337, false, false, var10007, 22, var2, (String[])null, 2, 4097);
      var2 = new String[]{"tarinfo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"gid", "group", "uid", "owner"};
      _set_uid_gid$23 = Py.newCode(1, var10001, var1, "_set_uid_gid", 380, false, false, var10007, 23, (String[])null, var2, 0, 4097);
      var2 = new String[]{"base_dir", "zip_filename", "verbose", "dry_run", "zipoptions", "DistutilsExecError", "spawn"};
      _call_external_zip$24 = Py.newCode(4, var2, var1, "_call_external_zip", 398, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base_name", "base_dir", "verbose", "dry_run", "logger", "zip_filename", "archive_dir", "zipfile", "zip", "dirpath", "dirnames", "filenames", "name", "path"};
      _make_zipfile$25 = Py.newCode(5, var2, var1, "_make_zipfile", 416, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"formats", "_[475_15]", "name", "registry"};
      get_archive_formats$26 = Py.newCode(0, var2, var1, "get_archive_formats", 470, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "function", "extra_args", "description", "element"};
      register_archive_format$27 = Py.newCode(4, var2, var1, "register_archive_format", 480, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      unregister_archive_format$28 = Py.newCode(1, var2, var1, "unregister_archive_format", 501, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base_name", "format", "root_dir", "base_dir", "verbose", "dry_run", "owner", "group", "logger", "save_cwd", "kwargs", "format_info", "func", "arg", "val", "filename"};
      make_archive$29 = Py.newCode(9, var2, var1, "make_archive", 504, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new shutil$py("shutil$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(shutil$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.SpecialFileError$2(var2, var3);
         case 3:
            return this.ExecError$3(var2, var3);
         case 4:
            return this.copyfileobj$4(var2, var3);
         case 5:
            return this._samefile$5(var2, var3);
         case 6:
            return this.copyfile$6(var2, var3);
         case 7:
            return this.copymode$7(var2, var3);
         case 8:
            return this.copystat$8(var2, var3);
         case 9:
            return this.copy$9(var2, var3);
         case 10:
            return this.copy2$10(var2, var3);
         case 11:
            return this.ignore_patterns$11(var2, var3);
         case 12:
            return this._ignore_patterns$12(var2, var3);
         case 13:
            return this.copytree$13(var2, var3);
         case 14:
            return this.rmtree$14(var2, var3);
         case 15:
            return this.onerror$15(var2, var3);
         case 16:
            return this.onerror$16(var2, var3);
         case 17:
            return this._basename$17(var2, var3);
         case 18:
            return this.move$18(var2, var3);
         case 19:
            return this._destinsrc$19(var2, var3);
         case 20:
            return this._get_gid$20(var2, var3);
         case 21:
            return this._get_uid$21(var2, var3);
         case 22:
            return this._make_tarball$22(var2, var3);
         case 23:
            return this._set_uid_gid$23(var2, var3);
         case 24:
            return this._call_external_zip$24(var2, var3);
         case 25:
            return this._make_zipfile$25(var2, var3);
         case 26:
            return this.get_archive_formats$26(var2, var3);
         case 27:
            return this.register_archive_format$27(var2, var3);
         case 28:
            return this.unregister_archive_format$28(var2, var3);
         case 29:
            return this.make_archive$29(var2, var3);
         default:
            return null;
      }
   }
}
