package distutils;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
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
@Filename("distutils/archive_util.py")
public class archive_util$py extends PyFunctionTable implements PyRunnable {
   static archive_util$py self;
   static final PyCode f$0;
   static final PyCode _get_gid$1;
   static final PyCode _get_uid$2;
   static final PyCode make_tarball$3;
   static final PyCode _set_uid_gid$4;
   static final PyCode make_zipfile$5;
   static final PyCode check_archive_formats$6;
   static final PyCode make_archive$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.archive_util\n\nUtility functions for creating archive files (tarballs, zip files,\nthat sort of thing)."));
      var1.setline(4);
      PyString.fromInterned("distutils.archive_util\n\nUtility functions for creating archive files (tarballs, zip files,\nthat sort of thing).");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var1.setline(9);
      String[] var8 = new String[]{"warn"};
      PyObject[] var9 = imp.importFrom("warnings", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("warn", var4);
      var4 = null;
      var1.setline(10);
      var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var1.setline(12);
      var8 = new String[]{"DistutilsExecError"};
      var9 = imp.importFrom("distutils.errors", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var1.setline(13);
      var8 = new String[]{"spawn"};
      var9 = imp.importFrom("distutils.spawn", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("spawn", var4);
      var4 = null;
      var1.setline(14);
      var8 = new String[]{"mkpath"};
      var9 = imp.importFrom("distutils.dir_util", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("mkpath", var4);
      var4 = null;
      var1.setline(15);
      var8 = new String[]{"log"};
      var9 = imp.importFrom("distutils", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("log", var4);
      var4 = null;

      PyException var10;
      try {
         var1.setline(18);
         var8 = new String[]{"getpwnam"};
         var9 = imp.importFrom("pwd", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("getpwnam", var4);
         var4 = null;
      } catch (Throwable var6) {
         var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(20);
         var4 = var1.getname("None");
         var1.setlocal("getpwnam", var4);
         var4 = null;
      }

      try {
         var1.setline(23);
         var8 = new String[]{"getgrnam"};
         var9 = imp.importFrom("grp", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("getgrnam", var4);
         var4 = null;
      } catch (Throwable var5) {
         var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(25);
         var4 = var1.getname("None");
         var1.setlocal("getgrnam", var4);
         var4 = null;
      }

      var1.setline(27);
      var9 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var9, _get_gid$1, PyString.fromInterned("Returns a gid, given a group name."));
      var1.setlocal("_get_gid", var11);
      var3 = null;
      var1.setline(39);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _get_uid$2, PyString.fromInterned("Returns an uid, given a user name."));
      var1.setlocal("_get_uid", var11);
      var3 = null;
      var1.setline(51);
      var9 = new PyObject[]{PyString.fromInterned("gzip"), Py.newInteger(0), Py.newInteger(0), var1.getname("None"), var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, make_tarball$3, PyString.fromInterned("Create a (possibly compressed) tar file from all the files under\n    'base_dir'.\n\n    'compress' must be \"gzip\" (the default), \"compress\", \"bzip2\", or None.\n    (compress will be deprecated in Python 3.2)\n\n    'owner' and 'group' can be used to define an owner and a group for the\n    archive that is being built. If not provided, the current owner and group\n    will be used.\n\n    The output tar file will be named 'base_dir' +  \".tar\", possibly plus\n    the appropriate compression extension (\".gz\", \".bz2\" or \".Z\").\n\n    Returns the output filename.\n    "));
      var1.setlocal("make_tarball", var11);
      var3 = null;
      var1.setline(121);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var9, make_zipfile$5, PyString.fromInterned("Create a zip file from all the files under 'base_dir'.\n\n    The output zip file will be named 'base_name' + \".zip\".  Uses either the\n    \"zipfile\" Python module (if available) or the InfoZIP \"zip\" utility\n    (if installed and found on the default search path).  If neither tool is\n    available, raises DistutilsExecError.  Returns the name of the output zip\n    file.\n    "));
      var1.setlocal("make_zipfile", var11);
      var3 = null;
      var1.setline(175);
      PyDictionary var12 = new PyDictionary(new PyObject[]{PyString.fromInterned("gztar"), new PyTuple(new PyObject[]{var1.getname("make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), PyString.fromInterned("gzip")})}), PyString.fromInterned("gzip'ed tar-file")}), PyString.fromInterned("bztar"), new PyTuple(new PyObject[]{var1.getname("make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), PyString.fromInterned("bzip2")})}), PyString.fromInterned("bzip2'ed tar-file")}), PyString.fromInterned("ztar"), new PyTuple(new PyObject[]{var1.getname("make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), PyString.fromInterned("compress")})}), PyString.fromInterned("compressed tar file")}), PyString.fromInterned("tar"), new PyTuple(new PyObject[]{var1.getname("make_tarball"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compress"), var1.getname("None")})}), PyString.fromInterned("uncompressed tar file")}), PyString.fromInterned("zip"), new PyTuple(new PyObject[]{var1.getname("make_zipfile"), new PyList(Py.EmptyObjects), PyString.fromInterned("ZIP file")})});
      var1.setlocal("ARCHIVE_FORMATS", var12);
      var3 = null;
      var1.setline(183);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, check_archive_formats$6, PyString.fromInterned("Returns the first format from the 'format' list that is unknown.\n\n    If all formats are known, returns None\n    "));
      var1.setlocal("check_archive_formats", var11);
      var3 = null;
      var1.setline(193);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0), var1.getname("None"), var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, make_archive$7, PyString.fromInterned("Create an archive file (eg. zip or tar).\n\n    'base_name' is the name of the file to create, minus any format-specific\n    extension; 'format' is the archive format: one of \"zip\", \"tar\", \"ztar\",\n    or \"gztar\".\n\n    'root_dir' is a directory that will be the root directory of the\n    archive; ie. we typically chdir into 'root_dir' before creating the\n    archive.  'base_dir' is the directory where we start archiving from;\n    ie. 'base_dir' will be the common prefix of all files and\n    directories in the archive.  'root_dir' and 'base_dir' both default\n    to the current directory.  Returns the name of the archive file.\n\n    'owner' and 'group' are used when creating a tar archive. By default,\n    uses the current owner and group.\n    "));
      var1.setlocal("make_archive", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_gid$1(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("Returns a gid, given a group name.");
      var1.setline(29);
      PyObject var3 = var1.getglobal("getgrnam");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(30);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var7;
         try {
            var1.setline(32);
            var7 = var1.getglobal("getgrnam").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var7);
            var4 = null;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("KeyError"))) {
               throw var4;
            }

            var1.setline(34);
            PyObject var5 = var1.getglobal("None");
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(35);
         var7 = var1.getlocal(1);
         var10000 = var7._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(36);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(37);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _get_uid$2(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyString.fromInterned("Returns an uid, given a user name.");
      var1.setline(41);
      PyObject var3 = var1.getglobal("getpwnam");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(42);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var7;
         try {
            var1.setline(44);
            var7 = var1.getglobal("getpwnam").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var7);
            var4 = null;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("KeyError"))) {
               throw var4;
            }

            var1.setline(46);
            PyObject var5 = var1.getglobal("None");
            var1.setlocal(1, var5);
            var5 = null;
         }

         var1.setline(47);
         var7 = var1.getlocal(1);
         var10000 = var7._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(48);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(49);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject make_tarball$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(5, 0);
      var1.to_cell(6, 3);
      var1.setline(67);
      PyString.fromInterned("Create a (possibly compressed) tar file from all the files under\n    'base_dir'.\n\n    'compress' must be \"gzip\" (the default), \"compress\", \"bzip2\", or None.\n    (compress will be deprecated in Python 3.2)\n\n    'owner' and 'group' can be used to define an owner and a group for the\n    archive that is being built. If not provided, the current owner and group\n    will be used.\n\n    The output tar file will be named 'base_dir' +  \".tar\", possibly plus\n    the appropriate compression extension (\".gz\", \".bz2\" or \".Z\").\n\n    Returns the output filename.\n    ");
      var1.setline(68);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("gzip"), PyString.fromInterned("gz"), PyString.fromInterned("bzip2"), PyString.fromInterned("bz2"), var1.getglobal("None"), PyString.fromInterned(""), PyString.fromInterned("compress"), PyString.fromInterned("")});
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(69);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("gzip"), PyString.fromInterned(".gz"), PyString.fromInterned("bzip2"), PyString.fromInterned(".bz2"), PyString.fromInterned("compress"), PyString.fromInterned(".Z")});
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(72);
      PyObject var7 = var1.getlocal(2);
      PyObject var10000 = var7._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(2);
         var10000 = var7._notin(var1.getlocal(8).__getattr__("keys").__call__(var2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(73);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("bad value for 'compress': must be None, 'gzip', 'bzip2' or 'compress'"));
      } else {
         var1.setline(77);
         var7 = var1.getlocal(0)._add(PyString.fromInterned(".tar"));
         var1.setlocal(9, var7);
         var3 = null;
         var1.setline(78);
         var7 = var1.getlocal(2);
         var10000 = var7._ne(PyString.fromInterned("compress"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(79);
            var7 = var1.getlocal(9);
            var7 = var7._iadd(var1.getlocal(8).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("")));
            var1.setlocal(9, var7);
         }

         var1.setline(81);
         var10000 = var1.getglobal("mkpath");
         PyObject[] var9 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(9)), var1.getlocal(4)};
         String[] var4 = new String[]{"dry_run"};
         var10000.__call__(var2, var9, var4);
         var3 = null;
         var1.setline(84);
         var7 = imp.importOne("tarfile", var1, -1);
         var1.setlocal(10, var7);
         var3 = null;
         var1.setline(86);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Creating tar archive"));
         var1.setline(88);
         var7 = var1.getglobal("_get_uid").__call__(var2, var1.getderef(0));
         var1.setderef(1, var7);
         var3 = null;
         var1.setline(89);
         var7 = var1.getglobal("_get_gid").__call__(var2, var1.getderef(3));
         var1.setderef(2, var7);
         var3 = null;
         var1.setline(91);
         var9 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var9;
         PyCode var10004 = _set_uid_gid$4;
         var9 = new PyObject[]{var1.getclosure(2), var1.getclosure(3), var1.getclosure(1), var1.getclosure(0)};
         PyFunction var10 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var9);
         var1.setlocal(11, var10);
         var3 = null;
         var1.setline(100);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(101);
            var7 = var1.getlocal(10).__getattr__("open").__call__(var2, var1.getlocal(9), PyString.fromInterned("w|%s")._mod(var1.getlocal(7).__getitem__(var1.getlocal(2))));
            var1.setlocal(12, var7);
            var3 = null;
            var3 = null;

            try {
               var1.setline(103);
               var10000 = var1.getlocal(12).__getattr__("add");
               PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(11)};
               String[] var5 = new String[]{"filter"};
               var10000.__call__(var2, var8, var5);
               var4 = null;
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(105);
               var1.getlocal(12).__getattr__("close").__call__(var2);
               throw (Throwable)var6;
            }

            var1.setline(105);
            var1.getlocal(12).__getattr__("close").__call__(var2);
         }

         var1.setline(108);
         var7 = var1.getlocal(2);
         var10000 = var7._eq(PyString.fromInterned("compress"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(109);
            var1.getglobal("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'compress' will be deprecated."), (PyObject)var1.getglobal("PendingDeprecationWarning"));
            var1.setline(111);
            var7 = var1.getlocal(9)._add(var1.getlocal(8).__getitem__(var1.getlocal(2)));
            var1.setlocal(13, var7);
            var3 = null;
            var1.setline(112);
            var7 = var1.getglobal("sys").__getattr__("platform");
            var10000 = var7._eq(PyString.fromInterned("win32"));
            var3 = null;
            PyList var11;
            if (var10000.__nonzero__()) {
               var1.setline(113);
               var11 = new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(9), var1.getlocal(13)});
               var1.setlocal(14, var11);
               var3 = null;
            } else {
               var1.setline(115);
               var11 = new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("-f"), var1.getlocal(9)});
               var1.setlocal(14, var11);
               var3 = null;
            }

            var1.setline(116);
            var10000 = var1.getglobal("spawn");
            var9 = new PyObject[]{var1.getlocal(14), var1.getlocal(4)};
            var4 = new String[]{"dry_run"};
            var10000.__call__(var2, var9, var4);
            var3 = null;
            var1.setline(117);
            var7 = var1.getlocal(13);
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(119);
            var7 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject _set_uid_gid$4(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(93);
         var3 = var1.getderef(0);
         var1.getlocal(0).__setattr__("gid", var3);
         var3 = null;
         var1.setline(94);
         var3 = var1.getderef(1);
         var1.getlocal(0).__setattr__("gname", var3);
         var3 = null;
      }

      var1.setline(95);
      var3 = var1.getderef(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(96);
         var3 = var1.getderef(2);
         var1.getlocal(0).__setattr__("uid", var3);
         var3 = null;
         var1.setline(97);
         var3 = var1.getderef(3);
         var1.getlocal(0).__setattr__("uname", var3);
         var3 = null;
      }

      var1.setline(98);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject make_zipfile$5(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyString.fromInterned("Create a zip file from all the files under 'base_dir'.\n\n    The output zip file will be named 'base_name' + \".zip\".  Uses either the\n    \"zipfile\" Python module (if available) or the InfoZIP \"zip\" utility\n    (if installed and found on the default search path).  If neither tool is\n    available, raises DistutilsExecError.  Returns the name of the output zip\n    file.\n    ");

      PyException var3;
      PyObject var4;
      PyObject var10;
      try {
         var1.setline(131);
         var10 = imp.importOne("zipfile", var1, -1);
         var1.setlocal(4, var10);
         var3 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(133);
         var4 = var1.getglobal("None");
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(135);
      var10 = var1.getlocal(0)._add(PyString.fromInterned(".zip"));
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(136);
      PyObject var10000 = var1.getglobal("mkpath");
      PyObject[] var11 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(5)), var1.getlocal(3)};
      String[] var12 = new String[]{"dry_run"};
      var10000.__call__(var2, var11, var12);
      var3 = null;
      var1.setline(140);
      var10 = var1.getlocal(4);
      var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(141);
         PyString var14;
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(142);
            var14 = PyString.fromInterned("-r");
            var1.setlocal(6, var14);
            var3 = null;
         } else {
            var1.setline(144);
            var14 = PyString.fromInterned("-rq");
            var1.setlocal(6, var14);
            var3 = null;
         }

         try {
            var1.setline(147);
            var10000 = var1.getglobal("spawn");
            var11 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("zip"), var1.getlocal(6), var1.getlocal(5), var1.getlocal(1)}), var1.getlocal(3)};
            var12 = new String[]{"dry_run"};
            var10000.__call__(var2, var11, var12);
            var3 = null;
         } catch (Throwable var8) {
            var3 = Py.setException(var8, var1);
            if (var3.match(var1.getglobal("DistutilsExecError"))) {
               var1.setline(152);
               throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("unable to create zip file '%s': could neither import the 'zipfile' module nor find a standalone zip utility")._mod(var1.getlocal(5)));
            }

            throw var3;
         }
      } else {
         var1.setline(158);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("creating '%s' and adding '%s' to it"), (PyObject)var1.getlocal(5), (PyObject)var1.getlocal(1));
         var1.setline(161);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(162);
            var10000 = var1.getlocal(4).__getattr__("ZipFile");
            var11 = new PyObject[]{var1.getlocal(5), PyString.fromInterned("w"), var1.getlocal(4).__getattr__("ZIP_DEFLATED")};
            var12 = new String[]{"compression"};
            var10000 = var10000.__call__(var2, var11, var12);
            var3 = null;
            var10 = var10000;
            var1.setlocal(7, var10);
            var3 = null;
            var1.setline(165);
            var10 = var1.getglobal("os").__getattr__("walk").__call__(var2, var1.getlocal(1)).__iter__();

            while(true) {
               var1.setline(165);
               var4 = var10.__iternext__();
               if (var4 == null) {
                  var1.setline(171);
                  var1.getlocal(7).__getattr__("close").__call__(var2);
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 3);
               PyObject var6 = var5[0];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(166);
               PyObject var13 = var1.getlocal(10).__iter__();

               while(true) {
                  var1.setline(166);
                  var6 = var13.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(11, var6);
                  var1.setline(167);
                  PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(8), var1.getlocal(11)));
                  var1.setlocal(12, var7);
                  var7 = null;
                  var1.setline(168);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(12)).__nonzero__()) {
                     var1.setline(169);
                     var1.getlocal(7).__getattr__("write").__call__(var2, var1.getlocal(12), var1.getlocal(12));
                     var1.setline(170);
                     var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("adding '%s'")._mod(var1.getlocal(12)));
                  }
               }
            }
         }
      }

      var1.setline(173);
      var10 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject check_archive_formats$6(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Returns the first format from the 'format' list that is unknown.\n\n    If all formats are known, returns None\n    ");
      var1.setline(188);
      PyObject var3 = var1.getlocal(0).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(188);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(191);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(1, var4);
         var1.setline(189);
         var5 = var1.getlocal(1);
         var10000 = var5._notin(var1.getglobal("ARCHIVE_FORMATS"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(190);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject make_archive$7(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyString.fromInterned("Create an archive file (eg. zip or tar).\n\n    'base_name' is the name of the file to create, minus any format-specific\n    extension; 'format' is the archive format: one of \"zip\", \"tar\", \"ztar\",\n    or \"gztar\".\n\n    'root_dir' is a directory that will be the root directory of the\n    archive; ie. we typically chdir into 'root_dir' before creating the\n    archive.  'base_dir' is the directory where we start archiving from;\n    ie. 'base_dir' will be the common prefix of all files and\n    directories in the archive.  'root_dir' and 'base_dir' both default\n    to the current directory.  Returns the name of the archive file.\n\n    'owner' and 'group' are used when creating a tar archive. By default,\n    uses the current owner and group.\n    ");
      var1.setline(211);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(212);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(213);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing into '%s'"), (PyObject)var1.getlocal(2));
         var1.setline(214);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(215);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(216);
            var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
         }
      }

      var1.setline(218);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(219);
         var3 = var1.getglobal("os").__getattr__("curdir");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(221);
      PyDictionary var12 = new PyDictionary(new PyObject[]{PyString.fromInterned("dry_run"), var1.getlocal(5)});
      var1.setlocal(9, var12);
      var3 = null;

      try {
         var1.setline(224);
         var3 = var1.getglobal("ARCHIVE_FORMATS").__getitem__(var1.getlocal(1));
         var1.setlocal(10, var3);
         var3 = null;
      } catch (Throwable var8) {
         PyException var13 = Py.setException(var8, var1);
         if (var13.match(var1.getglobal("KeyError"))) {
            var1.setline(226);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown archive format '%s'")._mod(var1.getlocal(1)));
         }

         throw var13;
      }

      var1.setline(228);
      var3 = var1.getlocal(10).__getitem__(Py.newInteger(0));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(229);
      var3 = var1.getlocal(10).__getitem__(Py.newInteger(1)).__iter__();

      while(true) {
         var1.setline(229);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(232);
            var3 = var1.getlocal(1);
            var10000 = var3._ne(PyString.fromInterned("zip"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(233);
               var3 = var1.getlocal(6);
               var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("owner"), var3);
               var3 = null;
               var1.setline(234);
               var3 = var1.getlocal(7);
               var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("group"), var3);
               var3 = null;
            }

            var3 = null;

            try {
               var1.setline(237);
               var10000 = var1.getlocal(11);
               PyObject[] var9 = new PyObject[]{var1.getlocal(0), var1.getlocal(3)};
               String[] var11 = new String[0];
               var10000 = var10000._callextra(var9, var11, (PyObject)null, var1.getlocal(9));
               var4 = null;
               var4 = var10000;
               var1.setlocal(14, var4);
               var4 = null;
            } catch (Throwable var7) {
               Py.addTraceback(var7, var1);
               var1.setline(239);
               var4 = var1.getlocal(2);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(240);
                  var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing back to '%s'"), (PyObject)var1.getlocal(8));
                  var1.setline(241);
                  var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(8));
               }

               throw (Throwable)var7;
            }

            var1.setline(239);
            var4 = var1.getlocal(2);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(240);
               var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing back to '%s'"), (PyObject)var1.getlocal(8));
               var1.setline(241);
               var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(8));
            }

            var1.setline(243);
            var3 = var1.getlocal(14);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(12, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(13, var6);
         var6 = null;
         var1.setline(230);
         PyObject var10 = var1.getlocal(13);
         var1.getlocal(9).__setitem__(var1.getlocal(12), var10);
         var5 = null;
      }
   }

   public archive_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "result"};
      _get_gid$1 = Py.newCode(1, var2, var1, "_get_gid", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "result"};
      _get_uid$2 = Py.newCode(1, var2, var1, "_get_uid", 39, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base_name", "base_dir", "compress", "verbose", "dry_run", "owner", "group", "tar_compression", "compress_ext", "archive_name", "tarfile", "_set_uid_gid", "tar", "compressed_name", "cmd", "uid", "gid"};
      String[] var10001 = var2;
      archive_util$py var10007 = self;
      var2 = new String[]{"owner", "uid", "gid", "group"};
      make_tarball$3 = Py.newCode(7, var10001, var1, "make_tarball", 51, false, false, var10007, 3, var2, (String[])null, 2, 4097);
      var2 = new String[]{"tarinfo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"gid", "group", "uid", "owner"};
      _set_uid_gid$4 = Py.newCode(1, var10001, var1, "_set_uid_gid", 91, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[]{"base_name", "base_dir", "verbose", "dry_run", "zipfile", "zip_filename", "zipoptions", "zip", "dirpath", "dirnames", "filenames", "name", "path"};
      make_zipfile$5 = Py.newCode(4, var2, var1, "make_zipfile", 121, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"formats", "format"};
      check_archive_formats$6 = Py.newCode(1, var2, var1, "check_archive_formats", 183, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base_name", "format", "root_dir", "base_dir", "verbose", "dry_run", "owner", "group", "save_cwd", "kwargs", "format_info", "func", "arg", "val", "filename"};
      make_archive$7 = Py.newCode(8, var2, var1, "make_archive", 193, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new archive_util$py("distutils/archive_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(archive_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._get_gid$1(var2, var3);
         case 2:
            return this._get_uid$2(var2, var3);
         case 3:
            return this.make_tarball$3(var2, var3);
         case 4:
            return this._set_uid_gid$4(var2, var3);
         case 5:
            return this.make_zipfile$5(var2, var3);
         case 6:
            return this.check_archive_formats$6(var2, var3);
         case 7:
            return this.make_archive$7(var2, var3);
         default:
            return null;
      }
   }
}
