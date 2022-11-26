package distutils.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_upload.py")
public class test_upload$py extends PyFunctionTable implements PyRunnable {
   static test_upload$py self;
   static final PyCode f$0;
   static final PyCode FakeOpen$1;
   static final PyCode __init__$2;
   static final PyCode getcode$3;
   static final PyCode uploadTestCase$4;
   static final PyCode setUp$5;
   static final PyCode tearDown$6;
   static final PyCode _urlopen$7;
   static final PyCode test_finalize_options$8;
   static final PyCode test_saved_password$9;
   static final PyCode test_upload$10;
   static final PyCode test_suite$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.upload."));
      var1.setline(2);
      PyString.fromInterned("Tests for distutils.command.upload.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"upload"};
      var6 = imp.importFrom("distutils.command", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("upload_mod", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"upload"};
      var6 = imp.importFrom("distutils.command.upload", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("upload", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"PYPIRC", "PyPIRCCommandTestCase"};
      var6 = imp.importFrom("distutils.tests.test_config", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("PYPIRC", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("PyPIRCCommandTestCase", var4);
      var4 = null;
      var1.setline(13);
      PyString var7 = PyString.fromInterned("[distutils]\n\nindex-servers =\n    server1\n    server2\n\n[server1]\nusername:me\npassword:aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n\n[server2]\nusername:meagain\npassword: secret\nrealm:acme\nrepository:http://another.pypi/\n");
      var1.setlocal("PYPIRC_LONG_PASSWORD", var7);
      var3 = null;
      var1.setline(32);
      var7 = PyString.fromInterned("[distutils]\n\nindex-servers =\n    server1\n\n[server1]\nusername:me\n");
      var1.setlocal("PYPIRC_NOPASSWORD", var7);
      var3 = null;
      var1.setline(42);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FakeOpen", var6, FakeOpen$1);
      var1.setlocal("FakeOpen", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(56);
      var6 = new PyObject[]{var1.getname("PyPIRCCommandTestCase")};
      var4 = Py.makeClass("uploadTestCase", var6, uploadTestCase$4);
      var1.setlocal("uploadTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(127);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test_suite$11, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(130);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(131);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeOpen$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(44);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcode$3, (PyObject)null);
      var1.setlocal("getcode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("url", var3);
      var3 = null;
      var1.setline(46);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
         var1.setline(47);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("req", var3);
         var3 = null;
      } else {
         var1.setline(49);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("req", var3);
         var3 = null;
      }

      var1.setline(50);
      PyString var4 = PyString.fromInterned("OK");
      var1.getlocal(0).__setattr__((String)"msg", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getcode$3(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyInteger var3 = Py.newInteger(200);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject uploadTestCase$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(58);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$5, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(64);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$6, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _urlopen$7, (PyObject)null);
      var1.setlocal("_urlopen", var4);
      var3 = null;
      var1.setline(72);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$8, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_saved_password$9, (PyObject)null);
      var1.setlocal("test_saved_password", var4);
      var3 = null;
      var1.setline(101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_upload$10, (PyObject)null);
      var1.setlocal("test_upload", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$5(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.getglobal("super").__call__(var2, var1.getglobal("uploadTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(60);
      PyObject var3 = var1.getglobal("upload_mod").__getattr__("urlopen");
      var1.getlocal(0).__setattr__("old_open", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(0).__getattr__("_urlopen");
      var1.getglobal("upload_mod").__setattr__("urlopen", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("last_open", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$6(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(0).__getattr__("old_open");
      var1.getglobal("upload_mod").__setattr__("urlopen", var3);
      var3 = null;
      var1.setline(66);
      var1.getglobal("super").__call__(var2, var1.getglobal("uploadTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _urlopen$7(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getglobal("FakeOpen").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("last_open", var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getlocal(0).__getattr__("last_open");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_finalize_options$8(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(0).__getattr__("rc"), var1.getglobal("PYPIRC"));
      var1.setline(76);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getglobal("upload").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(78);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(79);
      var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("username"), PyString.fromInterned("me")}), new PyTuple(new PyObject[]{PyString.fromInterned("password"), PyString.fromInterned("secret")}), new PyTuple(new PyObject[]{PyString.fromInterned("realm"), PyString.fromInterned("pypi")}), new PyTuple(new PyObject[]{PyString.fromInterned("repository"), PyString.fromInterned("http://pypi.python.org/pypi")})})).__iter__();

      while(true) {
         var1.setline(79);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(82);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(3)), var1.getlocal(4));
      }
   }

   public PyObject test_saved_password$9(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(0).__getattr__("rc"), var1.getglobal("PYPIRC_NOPASSWORD"));
      var1.setline(89);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getglobal("upload").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(91);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(92);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("password"), var1.getglobal("None"));
      var1.setline(96);
      PyString var4 = PyString.fromInterned("xxx");
      var1.getlocal(1).__setattr__((String)"password", var4);
      var3 = null;
      var1.setline(97);
      var3 = var1.getglobal("upload").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(98);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(99);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("password"), (PyObject)PyString.fromInterned("xxx"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_upload$10(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("xxx"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(104);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(2));
      var1.setline(105);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("xxx"), PyString.fromInterned("2.6"), var1.getlocal(2)});
      PyObject[] var4 = Py.unpackSequence(var6, 3);
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
      var1.setline(106);
      PyList var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)})});
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(107);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(0).__getattr__("rc"), var1.getglobal("PYPIRC_LONG_PASSWORD"));
      var1.setline(110);
      PyObject var10000 = var1.getlocal(0).__getattr__("create_dist");
      PyObject[] var9 = new PyObject[]{var1.getlocal(6), PyUnicode.fromInterned("dédé")};
      String[] var7 = new String[]{"dist_files", "author"};
      var10000 = var10000.__call__(var2, var9, var7);
      var3 = null;
      var3 = var10000;
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(111);
      var3 = var1.getglobal("upload").__call__(var2, var1.getlocal(8));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(112);
      var1.getlocal(9).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(113);
      var1.getlocal(9).__getattr__("run").__call__(var2);
      var1.setline(116);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dÃ©dÃ©"), (PyObject)var1.getlocal(0).__getattr__("last_open").__getattr__("req").__getattr__("data"));
      var1.setline(117);
      var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(0).__getattr__("last_open").__getattr__("req").__getattr__("headers"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(118);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(10).__getitem__(PyString.fromInterned("Content-length")), (PyObject)PyString.fromInterned("2085"));
      var1.setline(119);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(10).__getitem__(PyString.fromInterned("Content-type")).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multipart/form-data")));
      var1.setline(120);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("last_open").__getattr__("req").__getattr__("get_method").__call__(var2), (PyObject)PyString.fromInterned("POST"));
      var1.setline(121);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("last_open").__getattr__("req").__getattr__("get_full_url").__call__(var2), (PyObject)PyString.fromInterned("http://pypi.python.org/pypi"));
      var1.setline(123);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var10 = PyString.fromInterned("xxx");
      PyObject var10002 = var10._in(var1.getlocal(0).__getattr__("last_open").__getattr__("req").__getattr__("data"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(124);
      var3 = var1.getlocal(0).__getattr__("last_open").__getattr__("req").__getattr__("headers").__getitem__(PyString.fromInterned("Authorization"));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(125);
      var10000 = var1.getlocal(0).__getattr__("assertFalse");
      var10 = PyString.fromInterned("\n");
      var10002 = var10._in(var1.getlocal(11));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$11(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("uploadTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_upload$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FakeOpen$1 = Py.newCode(0, var2, var1, "FakeOpen", 42, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "url"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 44, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcode$3 = Py.newCode(1, var2, var1, "getcode", 52, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      uploadTestCase$4 = Py.newCode(0, var2, var1, "uploadTestCase", 56, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$5 = Py.newCode(1, var2, var1, "setUp", 58, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$6 = Py.newCode(1, var2, var1, "tearDown", 64, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url"};
      _urlopen$7 = Py.newCode(2, var2, var1, "_urlopen", 68, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "attr", "waited"};
      test_finalize_options$8 = Py.newCode(1, var2, var1, "test_finalize_options", 72, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd"};
      test_saved_password$9 = Py.newCode(1, var2, var1, "test_saved_password", 84, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp", "path", "command", "pyversion", "filename", "dist_files", "pkg_dir", "dist", "cmd", "headers", "auth"};
      test_upload$10 = Py.newCode(1, var2, var1, "test_upload", 101, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$11 = Py.newCode(0, var2, var1, "test_suite", 127, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_upload$py("distutils/tests/test_upload$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_upload$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FakeOpen$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.getcode$3(var2, var3);
         case 4:
            return this.uploadTestCase$4(var2, var3);
         case 5:
            return this.setUp$5(var2, var3);
         case 6:
            return this.tearDown$6(var2, var3);
         case 7:
            return this._urlopen$7(var2, var3);
         case 8:
            return this.test_finalize_options$8(var2, var3);
         case 9:
            return this.test_saved_password$9(var2, var3);
         case 10:
            return this.test_upload$10(var2, var3);
         case 11:
            return this.test_suite$11(var2, var3);
         default:
            return null;
      }
   }
}
