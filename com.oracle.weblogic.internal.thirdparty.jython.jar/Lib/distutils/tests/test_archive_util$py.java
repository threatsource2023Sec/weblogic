package distutils.tests;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_archive_util.py")
public class test_archive_util$py extends PyFunctionTable implements PyRunnable {
   static test_archive_util$py self;
   static final PyCode f$0;
   static final PyCode can_fs_encode$1;
   static final PyCode ArchiveUtilTestCase$2;
   static final PyCode test_make_tarball$3;
   static final PyCode _make_tarball$4;
   static final PyCode _tarinfo$5;
   static final PyCode _create_files$6;
   static final PyCode test_tarfile_vs_tar$7;
   static final PyCode test_compress_deprecated$8;
   static final PyCode test_make_zipfile$9;
   static final PyCode test_check_archive_formats$10;
   static final PyCode test_make_archive$11;
   static final PyCode test_make_archive_owner_group$12;
   static final PyCode test_tarfile_root_owner$13;
   static final PyCode test_make_archive_cwd$14;
   static final PyCode _breaks$15;
   static final PyCode test_make_tarball_unicode$16;
   static final PyCode test_make_tarball_unicode_latin1$17;
   static final PyCode test_make_tarball_unicode_extended$18;
   static final PyCode test_suite$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.archive_util."));
      var1.setline(2);
      PyString.fromInterned("Tests for distutils.archive_util.");
      var1.setline(3);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(5);
      PyObject var8 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var8);
      var3 = null;
      var1.setline(6);
      var8 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var8);
      var3 = null;
      var1.setline(7);
      var8 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var8);
      var3 = null;
      var1.setline(8);
      var8 = imp.importOne("tarfile", var1, -1);
      var1.setlocal("tarfile", var8);
      var3 = null;
      var1.setline(9);
      String[] var9 = new String[]{"splitdrive"};
      PyObject[] var10 = imp.importFrom("os.path", var9, var1, -1);
      PyObject var4 = var10[0];
      var1.setlocal("splitdrive", var4);
      var4 = null;
      var1.setline(10);
      var8 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var8);
      var3 = null;
      var1.setline(12);
      var9 = new String[]{"check_archive_formats", "make_tarball", "make_zipfile", "make_archive", "ARCHIVE_FORMATS"};
      var10 = imp.importFrom("distutils.archive_util", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("check_archive_formats", var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal("make_tarball", var4);
      var4 = null;
      var4 = var10[2];
      var1.setlocal("make_zipfile", var4);
      var4 = null;
      var4 = var10[3];
      var1.setlocal("make_archive", var4);
      var4 = null;
      var4 = var10[4];
      var1.setlocal("ARCHIVE_FORMATS", var4);
      var4 = null;
      var1.setline(15);
      var9 = new String[]{"find_executable", "spawn"};
      var10 = imp.importFrom("distutils.spawn", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("find_executable", var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal("spawn", var4);
      var4 = null;
      var1.setline(16);
      var9 = new String[]{"support"};
      var10 = imp.importFrom("distutils.tests", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(17);
      var9 = new String[]{"check_warnings", "run_unittest"};
      var10 = imp.importFrom("test.test_support", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("check_warnings", var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;

      PyException var11;
      try {
         var1.setline(20);
         var8 = imp.importOne("grp", var1, -1);
         var1.setlocal("grp", var8);
         var3 = null;
         var1.setline(21);
         var8 = imp.importOne("pwd", var1, -1);
         var1.setlocal("pwd", var8);
         var3 = null;
         var1.setline(22);
         var8 = var1.getname("True");
         var1.setlocal("UID_GID_SUPPORT", var8);
         var3 = null;
      } catch (Throwable var7) {
         var11 = Py.setException(var7, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(24);
         var4 = var1.getname("False");
         var1.setlocal("UID_GID_SUPPORT", var4);
         var4 = null;
      }

      try {
         var1.setline(27);
         var8 = imp.importOne("zipfile", var1, -1);
         var1.setlocal("zipfile", var8);
         var3 = null;
         var1.setline(28);
         var8 = var1.getname("True");
         var1.setlocal("ZIP_SUPPORT", var8);
         var3 = null;
      } catch (Throwable var6) {
         var11 = Py.setException(var6, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(30);
         var4 = var1.getname("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("zip"));
         var1.setlocal("ZIP_SUPPORT", var4);
         var4 = null;
      }

      try {
         var1.setline(34);
         var8 = imp.importOne("zlib", var1, -1);
         var1.setlocal("zlib", var8);
         var3 = null;
      } catch (Throwable var5) {
         var11 = Py.setException(var5, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(36);
         var4 = var1.getname("None");
         var1.setlocal("zlib", var4);
         var4 = null;
      }

      var1.setline(38);
      var10 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var10, can_fs_encode$1, PyString.fromInterned("\n    Return True if the filename can be saved in the file system.\n    "));
      var1.setlocal("can_fs_encode", var12);
      var3 = null;
      var1.setline(51);
      var10 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("ArchiveUtilTestCase", var10, ArchiveUtilTestCase$2);
      var1.setlocal("ArchiveUtilTestCase", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(324);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, test_suite$19, (PyObject)null);
      var1.setlocal("test_suite", var12);
      var3 = null;
      var1.setline(327);
      var8 = var1.getname("__name__");
      PyObject var10000 = var8._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(328);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject can_fs_encode$1(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyString.fromInterned("\n    Return True if the filename can be saved in the file system.\n    ");
      var1.setline(42);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("supports_unicode_filenames").__nonzero__()) {
         var1.setline(43);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(45);
            var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getglobal("sys").__getattr__("getfilesystemencoding").__call__(var2));
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("UnicodeEncodeError"))) {
               var1.setline(47);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         var1.setline(48);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ArchiveUtilTestCase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(55);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_make_tarball$3, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_make_tarball", var5);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _make_tarball$4, (PyObject)null);
      var1.setlocal("_make_tarball", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _tarinfo$5, (PyObject)null);
      var1.setlocal("_tarinfo", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_files$6, (PyObject)null);
      var1.setlocal("_create_files", var4);
      var3 = null;
      var1.setline(119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_tarfile_vs_tar$7, (PyObject)null);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("Requires zlib"));
      PyObject var10001 = var1.getname("unittest").__getattr__("skipUnless");
      PyObject var10003 = var1.getname("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tar"));
      if (var10003.__nonzero__()) {
         var10003 = var1.getname("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gzip"));
      }

      var5 = var10001.__call__((ThreadState)var2, (PyObject)var10003, (PyObject)PyString.fromInterned("Need the tar command to run")).__call__((ThreadState)var2, (PyObject)var4);
      var5 = var10000.__call__(var2, var5);
      var1.setlocal("test_tarfile_vs_tar", var5);
      var3 = null;
      var1.setline(173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_compress_deprecated$8, (PyObject)null);
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("compress")), (PyObject)PyString.fromInterned("The compress program is required")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_compress_deprecated", var5);
      var3 = null;
      var1.setline(205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_zipfile$9, (PyObject)null);
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("Requires zlib"));
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("ZIP_SUPPORT"), (PyObject)PyString.fromInterned("Need zip support to run")).__call__((ThreadState)var2, (PyObject)var4);
      var5 = var10000.__call__(var2, var5);
      var1.setlocal("test_make_zipfile", var5);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_check_archive_formats$10, (PyObject)null);
      var1.setlocal("test_check_archive_formats", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_archive$11, (PyObject)null);
      var1.setlocal("test_make_archive", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_archive_owner_group$12, (PyObject)null);
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("Requires zlib")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_make_archive_owner_group", var5);
      var3 = null;
      var1.setline(257);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_tarfile_root_owner$13, (PyObject)null);
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("Requires zlib"));
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("UID_GID_SUPPORT"), (PyObject)PyString.fromInterned("Requires grp and pwd support")).__call__((ThreadState)var2, (PyObject)var4);
      var5 = var10000.__call__(var2, var5);
      var1.setlocal("test_tarfile_root_owner", var5);
      var3 = null;
      var1.setline(283);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_archive_cwd$14, (PyObject)null);
      var1.setlocal("test_make_archive_cwd", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_tarball_unicode$16, PyString.fromInterned("\n        Mirror test_make_tarball, except filename is unicode.\n        "));
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_make_tarball_unicode", var5);
      var3 = null;
      var1.setline(304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_tarball_unicode_latin1$17, PyString.fromInterned("\n        Mirror test_make_tarball, except filename is unicode and contains\n        latin characters.\n        "));
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib"));
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("can_fs_encode").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("årchiv")), (PyObject)PyString.fromInterned("File system cannot handle this filename")).__call__((ThreadState)var2, (PyObject)var4);
      var5 = var10000.__call__(var2, var5);
      var1.setlocal("test_make_tarball_unicode_latin1", var5);
      var3 = null;
      var1.setline(314);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_tarball_unicode_extended$18, PyString.fromInterned("\n        Mirror test_make_tarball, except filename is unicode and contains\n        characters outside the latin charset.\n        "));
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib"));
      var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("can_fs_encode").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("のアーカイブ")), (PyObject)PyString.fromInterned("File system cannot handle this filename")).__call__((ThreadState)var2, (PyObject)var4);
      var5 = var10000.__call__(var2, var5);
      var1.setlocal("test_make_tarball_unicode_extended", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_make_tarball$3(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      var1.getlocal(0).__getattr__("_make_tarball").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("archive"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _make_tarball$4(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(62);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("file1")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(63);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("file2")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(64);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("sub")));
      var1.setline(65);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("sub"), PyString.fromInterned("file3")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(67);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(68);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("skipUnless");
      var3 = var1.getglobal("splitdrive").__call__(var2, var1.getlocal(2)).__getitem__(Py.newInteger(0));
      PyObject var10002 = var3._eq(var1.getglobal("splitdrive").__call__(var2, var1.getlocal(3)).__getitem__(Py.newInteger(0)));
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("source and target should be on same drive"));
      var1.setline(71);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(75);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
      var3 = null;

      try {
         var1.setline(77);
         var1.getglobal("make_tarball").__call__((ThreadState)var2, (PyObject)var1.getglobal("splitdrive").__call__(var2, var1.getlocal(4)).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("."));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(79);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(5));
         throw (Throwable)var7;
      }

      var1.setline(79);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(5));
      var1.setline(82);
      var3 = var1.getlocal(4)._add(PyString.fromInterned(".tar.gz"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(83);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.setline(86);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(88);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
      var3 = null;

      try {
         var1.setline(90);
         var10000 = var1.getglobal("make_tarball");
         PyObject[] var4 = new PyObject[]{var1.getglobal("splitdrive").__call__(var2, var1.getlocal(4)).__getitem__(Py.newInteger(1)), PyString.fromInterned("."), var1.getglobal("None")};
         String[] var5 = new String[]{"compress"};
         var10000.__call__(var2, var4, var5);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(92);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(5));
         throw (Throwable)var6;
      }

      var1.setline(92);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(5));
      var1.setline(93);
      var3 = var1.getlocal(4)._add(PyString.fromInterned(".tar"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(94);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _tarinfo$5(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getglobal("tarfile").__getattr__("open").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(99);
            var4 = var1.getlocal(2).__getattr__("getnames").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(100);
            var1.getlocal(3).__getattr__("sort").__call__(var2);
            var1.setline(101);
            var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(3));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(103);
         var1.getlocal(2).__getattr__("close").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(103);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      throw (Throwable)var7;
   }

   public PyObject _create_files$6(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("dist"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(109);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
      var1.setline(110);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("file1")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(111);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("file2")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(112);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("sub")));
      var1.setline(113);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("sub"), PyString.fromInterned("file3")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(114);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("sub2")));
      var1.setline(115);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("archive"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(117);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject test_tarfile_vs_tar$7(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_files").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(124);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(125);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var3 = null;

      try {
         var1.setline(127);
         var1.getglobal("make_tarball").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("dist"));
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(129);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var9;
      }

      var1.setline(129);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(132);
      var3 = var1.getlocal(3)._add(PyString.fromInterned(".tar.gz"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(133);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(5)));
      var1.setline(136);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("archive2.tar.gz"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(137);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("tar"), PyString.fromInterned("-cf"), PyString.fromInterned("archive2.tar"), PyString.fromInterned("dist")});
      var1.setlocal(7, var10);
      var3 = null;
      var1.setline(138);
      var10 = new PyList(new PyObject[]{PyString.fromInterned("gzip"), PyString.fromInterned("-f9"), PyString.fromInterned("archive2.tar")});
      var1.setlocal(8, var10);
      var3 = null;
      var1.setline(139);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(140);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var3 = null;

      try {
         var1.setline(142);
         var1.getglobal("spawn").__call__(var2, var1.getlocal(7));
         var1.setline(143);
         var1.getglobal("spawn").__call__(var2, var1.getlocal(8));
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(145);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var8;
      }

      var1.setline(145);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(147);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.setline(149);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_tarinfo").__call__(var2, var1.getlocal(5)), var1.getlocal(0).__getattr__("_tarinfo").__call__(var2, var1.getlocal(6)));
      var1.setline(152);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("archive"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(153);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(154);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var3 = null;

      PyObject var10000;
      String[] var11;
      try {
         var1.setline(156);
         var10000 = var1.getglobal("make_tarball");
         var4 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("dist"), var1.getglobal("None")};
         var11 = new String[]{"compress"};
         var10000.__call__(var2, var4, var11);
         var4 = null;
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(158);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var7;
      }

      var1.setline(158);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(159);
      var3 = var1.getlocal(3)._add(PyString.fromInterned(".tar"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(160);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(5)));
      var1.setline(163);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("archive"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(164);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(165);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var3 = null;

      try {
         var1.setline(167);
         var10000 = var1.getglobal("make_tarball");
         var4 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("dist"), var1.getglobal("None"), var1.getglobal("True")};
         var11 = new String[]{"compress", "dry_run"};
         var10000.__call__(var2, var4, var11);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(169);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var6;
      }

      var1.setline(169);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(170);
      var3 = var1.getlocal(3)._add(PyString.fromInterned(".tar"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(171);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(5)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_compress_deprecated$8(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(176);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_files").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(179);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(180);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var3 = null;

      String[] var6;
      ContextManager var11;
      PyObject[] var12;
      PyObject var10000;
      try {
         label45: {
            var5 = (var11 = ContextGuard.getManager(var1.getglobal("check_warnings").__call__(var2))).__enter__(var2);

            try {
               var1.setlocal(5, var5);
               var1.setline(183);
               var1.getglobal("warnings").__getattr__("simplefilter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("always"));
               var1.setline(184);
               var10000 = var1.getglobal("make_tarball");
               var12 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("dist"), PyString.fromInterned("compress")};
               var6 = new String[]{"compress"};
               var10000.__call__(var2, var12, var6);
               var5 = null;
            } catch (Throwable var9) {
               if (var11.__exit__(var2, Py.setException(var9, var1))) {
                  break label45;
               }

               throw (Throwable)Py.makeException();
            }

            var11.__exit__(var2, (PyException)null);
         }
      } catch (Throwable var10) {
         Py.addTraceback(var10, var1);
         var1.setline(186);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var10;
      }

      var1.setline(186);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(187);
      var3 = var1.getlocal(3)._add(PyString.fromInterned(".tar.Z"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(188);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.setline(189);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("warnings")), (PyObject)Py.newInteger(1));
      var1.setline(192);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(6));
      var1.setline(193);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(194);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var3 = null;

      try {
         label46: {
            var5 = (var11 = ContextGuard.getManager(var1.getglobal("check_warnings").__call__(var2))).__enter__(var2);

            try {
               var1.setlocal(5, var5);
               var1.setline(197);
               var1.getglobal("warnings").__getattr__("simplefilter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("always"));
               var1.setline(198);
               var10000 = var1.getglobal("make_tarball");
               var12 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("dist"), PyString.fromInterned("compress"), var1.getglobal("True")};
               var6 = new String[]{"compress", "dry_run"};
               var10000.__call__(var2, var12, var6);
               var5 = null;
            } catch (Throwable var7) {
               if (var11.__exit__(var2, Py.setException(var7, var1))) {
                  break label46;
               }

               throw (Throwable)Py.makeException();
            }

            var11.__exit__(var2, (PyException)null);
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(201);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var8;
      }

      var1.setline(201);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(202);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)).__not__());
      var1.setline(203);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("warnings")), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_zipfile$9(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(210);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1), PyString.fromInterned("file1")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(211);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1), PyString.fromInterned("file2")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(213);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("archive"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(215);
      var1.getglobal("make_zipfile").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      var1.setline(218);
      var3 = var1.getlocal(3)._add(PyString.fromInterned(".zip"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_check_archive_formats$10(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("check_archive_formats").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("gztar"), PyString.fromInterned("xxx"), PyString.fromInterned("zip")}))), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(223);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("check_archive_formats").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("gztar"), PyString.fromInterned("zip")}))), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_archive$11(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("archive"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(228);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ValueError"), var1.getglobal("make_archive"), var1.getlocal(2), PyString.fromInterned("xxx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_archive_owner_group$12(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3;
      if (var1.getglobal("UID_GID_SUPPORT").__nonzero__()) {
         var1.setline(235);
         var3 = var1.getglobal("grp").__getattr__("getgrgid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(236);
         var3 = var1.getglobal("pwd").__getattr__("getpwuid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(238);
         PyString var6 = PyString.fromInterned("root");
         var1.setlocal(1, var6);
         var1.setlocal(2, var6);
      }

      var1.setline(240);
      var3 = var1.getlocal(0).__getattr__("_create_files").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(241);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("mkdtemp").__call__(var2), (PyObject)PyString.fromInterned("archive"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(242);
      PyObject var10000 = var1.getglobal("make_archive");
      PyObject[] var8 = new PyObject[]{var1.getlocal(5), PyString.fromInterned("zip"), var1.getlocal(4), var1.getlocal(3), var1.getlocal(2), var1.getlocal(1)};
      String[] var7 = new String[]{"owner", "group"};
      var10000 = var10000.__call__(var2, var8, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(244);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.setline(246);
      var3 = var1.getglobal("make_archive").__call__(var2, var1.getlocal(5), PyString.fromInterned("zip"), var1.getlocal(4), var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(247);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.setline(249);
      var10000 = var1.getglobal("make_archive");
      var8 = new PyObject[]{var1.getlocal(5), PyString.fromInterned("tar"), var1.getlocal(4), var1.getlocal(3), var1.getlocal(2), var1.getlocal(1)};
      var7 = new String[]{"owner", "group"};
      var10000 = var10000.__call__(var2, var8, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(251);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.setline(253);
      var10000 = var1.getglobal("make_archive");
      var8 = new PyObject[]{var1.getlocal(5), PyString.fromInterned("tar"), var1.getlocal(4), var1.getlocal(3), PyString.fromInterned("kjhkjhkjg"), PyString.fromInterned("oihohoh")};
      var7 = new String[]{"owner", "group"};
      var10000 = var10000.__call__(var2, var8, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(255);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(6)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_tarfile_root_owner$13(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_files").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(261);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(262);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var1.setline(263);
      var3 = var1.getglobal("grp").__getattr__("getgrgid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(264);
      var3 = var1.getglobal("pwd").__getattr__("getpwuid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      PyObject var8;
      try {
         var1.setline(266);
         PyObject var10000 = var1.getglobal("make_tarball");
         var4 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("dist"), var1.getglobal("None"), var1.getlocal(6), var1.getlocal(5)};
         String[] var9 = new String[]{"compress", "owner", "group"};
         var10000 = var10000.__call__(var2, var4, var9);
         var4 = null;
         var8 = var10000;
         var1.setlocal(7, var8);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(269);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
         throw (Throwable)var6;
      }

      var1.setline(269);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(4));
      var1.setline(272);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(7)));
      var1.setline(275);
      var3 = var1.getglobal("tarfile").__getattr__("open").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(277);
         var8 = var1.getlocal(8).__getattr__("getmembers").__call__(var2).__iter__();

         while(true) {
            var1.setline(277);
            var5 = var8.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(9, var5);
            var1.setline(278);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getattr__("uid"), (PyObject)Py.newInteger(0));
            var1.setline(279);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getattr__("gid"), (PyObject)Py.newInteger(0));
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(281);
         var1.getlocal(8).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(281);
      var1.getlocal(8).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_archive_cwd$14(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(285);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, _breaks$15, (PyObject)null);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(287);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(2), new PyList(Py.EmptyObjects), PyString.fromInterned("xxx file")});
      var1.getglobal("ARCHIVE_FORMATS").__setitem__((PyObject)PyString.fromInterned("xxx"), var10);
      var3 = null;
      var3 = null;

      try {
         try {
            var1.setline(290);
            PyObject var10000 = var1.getglobal("make_archive");
            PyObject[] var4 = new PyObject[]{PyString.fromInterned("xxx"), PyString.fromInterned("xxx"), var1.getlocal(0).__getattr__("mkdtemp").__call__(var2)};
            String[] var5 = new String[]{"root_dir"};
            var10000.__call__(var2, var4, var5);
            var4 = null;
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(292);
         }

         var1.setline(293);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("os").__getattr__("getcwd").__call__(var2), var1.getlocal(1));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(295);
         var1.getglobal("ARCHIVE_FORMATS").__delitem__((PyObject)PyString.fromInterned("xxx"));
         throw (Throwable)var7;
      }

      var1.setline(295);
      var1.getglobal("ARCHIVE_FORMATS").__delitem__((PyObject)PyString.fromInterned("xxx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _breaks$15(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2));
   }

   public PyObject test_make_tarball_unicode$16(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyString.fromInterned("\n        Mirror test_make_tarball, except filename is unicode.\n        ");
      var1.setline(302);
      var1.getlocal(0).__getattr__("_make_tarball").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("archive"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_tarball_unicode_latin1$17(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyString.fromInterned("\n        Mirror test_make_tarball, except filename is unicode and contains\n        latin characters.\n        ");
      var1.setline(312);
      var1.getlocal(0).__getattr__("_make_tarball").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("årchiv"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_tarball_unicode_extended$18(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyString.fromInterned("\n        Mirror test_make_tarball, except filename is unicode and contains\n        characters outside the latin charset.\n        ");
      var1.setline(322);
      var1.getlocal(0).__getattr__("_make_tarball").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("のアーカイブ"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$19(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("ArchiveUtilTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_archive_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename"};
      can_fs_encode$1 = Py.newCode(1, var2, var1, "can_fs_encode", 38, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ArchiveUtilTestCase$2 = Py.newCode(0, var2, var1, "ArchiveUtilTestCase", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_make_tarball$3 = Py.newCode(1, var2, var1, "test_make_tarball", 55, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_name", "tmpdir", "tmpdir2", "base_name", "old_dir", "tarball"};
      _make_tarball$4 = Py.newCode(2, var2, var1, "_make_tarball", 59, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "tar", "names"};
      _tarinfo$5 = Py.newCode(2, var2, var1, "_tarinfo", 96, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "dist", "tmpdir2", "base_name"};
      _create_files$6 = Py.newCode(1, var2, var1, "_create_files", 105, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "tmpdir2", "base_name", "old_dir", "tarball", "tarball2", "tar_cmd", "gzip_cmd"};
      test_tarfile_vs_tar$7 = Py.newCode(1, var2, var1, "test_tarfile_vs_tar", 119, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "tmpdir2", "base_name", "old_dir", "w", "tarball"};
      test_compress_deprecated$8 = Py.newCode(1, var2, var1, "test_compress_deprecated", 173, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "tmpdir2", "base_name", "tarball"};
      test_make_zipfile$9 = Py.newCode(1, var2, var1, "test_make_zipfile", 205, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_check_archive_formats$10 = Py.newCode(1, var2, var1, "test_check_archive_formats", 220, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "base_name"};
      test_make_archive$11 = Py.newCode(1, var2, var1, "test_make_archive", 225, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "group", "owner", "base_dir", "root_dir", "base_name", "res"};
      test_make_archive_owner_group$12 = Py.newCode(1, var2, var1, "test_make_archive_owner_group", 230, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "tmpdir2", "base_name", "old_dir", "group", "owner", "archive_name", "archive", "member"};
      test_tarfile_root_owner$13 = Py.newCode(1, var2, var1, "test_tarfile_root_owner", 257, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "current_dir", "_breaks"};
      test_make_archive_cwd$14 = Py.newCode(1, var2, var1, "test_make_archive_cwd", 283, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kw"};
      _breaks$15 = Py.newCode(2, var2, var1, "_breaks", 285, true, true, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_make_tarball_unicode$16 = Py.newCode(1, var2, var1, "test_make_tarball_unicode", 297, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_make_tarball_unicode_latin1$17 = Py.newCode(1, var2, var1, "test_make_tarball_unicode_latin1", 304, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_make_tarball_unicode_extended$18 = Py.newCode(1, var2, var1, "test_make_tarball_unicode_extended", 314, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$19 = Py.newCode(0, var2, var1, "test_suite", 324, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_archive_util$py("distutils/tests/test_archive_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_archive_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.can_fs_encode$1(var2, var3);
         case 2:
            return this.ArchiveUtilTestCase$2(var2, var3);
         case 3:
            return this.test_make_tarball$3(var2, var3);
         case 4:
            return this._make_tarball$4(var2, var3);
         case 5:
            return this._tarinfo$5(var2, var3);
         case 6:
            return this._create_files$6(var2, var3);
         case 7:
            return this.test_tarfile_vs_tar$7(var2, var3);
         case 8:
            return this.test_compress_deprecated$8(var2, var3);
         case 9:
            return this.test_make_zipfile$9(var2, var3);
         case 10:
            return this.test_check_archive_formats$10(var2, var3);
         case 11:
            return this.test_make_archive$11(var2, var3);
         case 12:
            return this.test_make_archive_owner_group$12(var2, var3);
         case 13:
            return this.test_tarfile_root_owner$13(var2, var3);
         case 14:
            return this.test_make_archive_cwd$14(var2, var3);
         case 15:
            return this._breaks$15(var2, var3);
         case 16:
            return this.test_make_tarball_unicode$16(var2, var3);
         case 17:
            return this.test_make_tarball_unicode_latin1$17(var2, var3);
         case 18:
            return this.test_make_tarball_unicode_extended$18(var2, var3);
         case 19:
            return this.test_suite$19(var2, var3);
         default:
            return null;
      }
   }
}
