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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_register.py")
public class test_register$py extends PyFunctionTable implements PyRunnable {
   static test_register$py self;
   static final PyCode f$0;
   static final PyCode RawInputs$1;
   static final PyCode __init__$2;
   static final PyCode __call__$3;
   static final PyCode FakeOpener$4;
   static final PyCode __init__$5;
   static final PyCode __call__$6;
   static final PyCode open$7;
   static final PyCode read$8;
   static final PyCode RegisterTestCase$9;
   static final PyCode setUp$10;
   static final PyCode _getpass$11;
   static final PyCode tearDown$12;
   static final PyCode _get_cmd$13;
   static final PyCode test_create_pypirc$14;
   static final PyCode _no_way$15;
   static final PyCode test_password_not_in_file$16;
   static final PyCode test_registering$17;
   static final PyCode test_password_reset$18;
   static final PyCode test_strict$19;
   static final PyCode test_register_invalid_long_description$20;
   static final PyCode test_check_metadata_deprecated$21;
   static final PyCode test_suite$22;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.register."));
      var1.setline(2);
      PyString.fromInterned("Tests for distutils.command.register.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("getpass", var1, -1);
      var1.setlocal("getpass", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("urllib2", var1, -1);
      var1.setlocal("urllib2", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(9);
      String[] var6 = new String[]{"check_warnings", "run_unittest"};
      PyObject[] var7 = imp.importFrom("test.test_support", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("check_warnings", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"register"};
      var7 = imp.importFrom("distutils.command", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("register_module", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"register"};
      var7 = imp.importFrom("distutils.command.register", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("register", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"DistutilsSetupError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"PyPIRCCommandTestCase"};
      var7 = imp.importFrom("distutils.tests.test_config", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("PyPIRCCommandTestCase", var4);
      var4 = null;

      try {
         var1.setline(18);
         var3 = imp.importOne("docutils", var1, -1);
         var1.setlocal("docutils", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(20);
         var4 = var1.getname("None");
         var1.setlocal("docutils", var4);
         var4 = null;
      }

      var1.setline(22);
      PyString var9 = PyString.fromInterned("[distutils]\n\nindex-servers =\n    server1\n\n[server1]\nusername:me\n");
      var1.setlocal("PYPIRC_NOPASSWORD", var9);
      var3 = null;
      var1.setline(32);
      var9 = PyString.fromInterned("[distutils]\nindex-servers =\n    pypi\n\n[pypi]\nusername:tarek\npassword:password\n");
      var1.setlocal("WANTED_PYPIRC", var9);
      var3 = null;
      var1.setline(42);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("RawInputs", var7, RawInputs$1);
      var1.setlocal("RawInputs", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(54);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FakeOpener", var7, FakeOpener$4);
      var1.setlocal("FakeOpener", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(69);
      var7 = new PyObject[]{var1.getname("PyPIRCCommandTestCase")};
      var4 = Py.makeClass("RegisterTestCase", var7, RegisterTestCase$9);
      var1.setlocal("RegisterTestCase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(286);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, test_suite$22, (PyObject)null);
      var1.setlocal("test_suite", var10);
      var3 = null;
      var1.setline(289);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject RawInputs$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Fakes user inputs."));
      var1.setline(43);
      PyString.fromInterned("Fakes user inputs.");
      var1.setline(44);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(48);
      var3 = new PyObject[]{PyString.fromInterned("")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$3, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("answers", var3);
      var3 = null;
      var1.setline(46);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"index", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$3(PyFrame var1, ThreadState var2) {
      Throwable var3 = null;

      Throwable var10000;
      String var5;
      PyObject var6;
      PyObject var7;
      PyObject var10;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(50);
            var4 = var1.getlocal(0).__getattr__("answers").__getitem__(var1.getlocal(0).__getattr__("index"));
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label25;
         }

         var1.setline(52);
         var10 = var1.getlocal(0);
         var5 = "index";
         var6 = var10;
         var7 = var6.__getattr__(var5);
         var7 = var7._iadd(Py.newInteger(1));
         var6.__setattr__(var5, var7);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
         }
      }

      var3 = var10000;
      Py.addTraceback(var3, var1);
      var1.setline(52);
      var10 = var1.getlocal(0);
      var5 = "index";
      var6 = var10;
      var7 = var6.__getattr__(var5);
      var7 = var7._iadd(Py.newInteger(1));
      var6.__setattr__(var5, var7);
      throw (Throwable)var3;
   }

   public PyObject FakeOpener$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Fakes a PyPI server"));
      var1.setline(55);
      PyString.fromInterned("Fakes a PyPI server");
      var1.setline(56);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$6, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open$7, (PyObject)null);
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$8, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"reqs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$6(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject open$7(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      var1.getlocal(0).__getattr__("reqs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(64);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$8(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString var3 = PyString.fromInterned("xxx");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject RegisterTestCase$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(71);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, setUp$10, (PyObject)null);
      var1.setlocal("setUp", var5);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, tearDown$12, (PyObject)null);
      var1.setlocal("tearDown", var5);
      var3 = null;
      var1.setline(86);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, _get_cmd$13, (PyObject)null);
      var1.setlocal("_get_cmd", var5);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_create_pypirc$14, (PyObject)null);
      var1.setlocal("test_create_pypirc", var5);
      var3 = null;
      var1.setline(149);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_password_not_in_file$16, (PyObject)null);
      var1.setlocal("test_password_not_in_file", var5);
      var3 = null;
      var1.setline(161);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_registering$17, (PyObject)null);
      var1.setlocal("test_registering", var5);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_password_reset$18, (PyObject)null);
      var1.setlocal("test_password_reset", var5);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_strict$19, (PyObject)null);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless");
      PyObject var4 = var1.getname("docutils");
      PyObject var10002 = var4._isnot(var1.getname("None"));
      var4 = null;
      PyObject var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("needs docutils")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_strict", var6);
      var3 = null;
      var1.setline(263);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_register_invalid_long_description$20, (PyObject)null);
      var10000 = var1.getname("unittest").__getattr__("skipUnless");
      var4 = var1.getname("docutils");
      var10002 = var4._isnot(var1.getname("None"));
      var4 = null;
      var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("needs docutils")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_register_invalid_long_description", var6);
      var3 = null;
      var1.setline(278);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_check_metadata_deprecated$21, (PyObject)null);
      var1.setlocal("test_check_metadata_deprecated", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$10(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      var1.getglobal("super").__call__(var2, var1.getglobal("RegisterTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(74);
      PyObject var3 = var1.getglobal("getpass").__getattr__("getpass");
      var1.getlocal(0).__setattr__("_old_getpass", var3);
      var3 = null;
      var1.setline(75);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _getpass$11, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(1);
      var1.getglobal("getpass").__setattr__("getpass", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getglobal("urllib2").__getattr__("build_opener");
      var1.getlocal(0).__setattr__("old_opener", var3);
      var3 = null;
      var1.setline(79);
      var3 = var1.getglobal("FakeOpener").__call__(var2);
      var1.getlocal(0).__setattr__("conn", var3);
      var1.getglobal("urllib2").__setattr__("build_opener", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getpass$11(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString var3 = PyString.fromInterned("password");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tearDown$12(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(0).__getattr__("_old_getpass");
      var1.getglobal("getpass").__setattr__("getpass", var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(0).__getattr__("old_opener");
      var1.getglobal("urllib2").__setattr__("build_opener", var3);
      var3 = null;
      var1.setline(84);
      var1.getglobal("super").__call__(var2, var1.getglobal("RegisterTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_cmd$13(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(88);
         PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx"), PyString.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyString.fromInterned("xxx")});
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(91);
      var10000 = var1.getlocal(0).__getattr__("create_dist");
      PyObject[] var7 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var7, var4, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      PyObject[] var8 = Py.unpackSequence(var3, 2);
      PyObject var5 = var8[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(92);
      var3 = var1.getglobal("register").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_create_pypirc$14(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(102);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("rc")).__not__());
      var1.setline(112);
      var3 = var1.getglobal("RawInputs").__call__((ThreadState)var2, PyString.fromInterned("1"), (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("y"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getlocal(2).__getattr__("__call__");
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(116);
         var1.getlocal(1).__getattr__("run").__call__(var2);
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(118);
         var1.getglobal("register_module").__delattr__("raw_input");
         throw (Throwable)var6;
      }

      var1.setline(118);
      var1.getglobal("register_module").__delattr__("raw_input");
      var1.setline(121);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("rc")));
      var1.setline(124);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("rc"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(126);
         PyObject var4 = var1.getlocal(3).__getattr__("read").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(127);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getglobal("WANTED_PYPIRC"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(129);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(129);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(134);
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, _no_way$15, (PyObject)null);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(136);
      var3 = var1.getlocal(5);
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var1.setline(138);
      PyInteger var9 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"show_response", var9);
      var3 = null;
      var1.setline(139);
      var1.getlocal(1).__getattr__("run").__call__(var2);
      var1.setline(143);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("conn").__getattr__("reqs")), (PyObject)Py.newInteger(2));
      var1.setline(144);
      var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(0).__getattr__("conn").__getattr__("reqs").__getitem__(Py.newInteger(0)).__getattr__("headers"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(0).__getattr__("conn").__getattr__("reqs").__getitem__(Py.newInteger(1)).__getattr__("headers"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(146);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(7).__getitem__(PyString.fromInterned("Content-length")), var1.getlocal(6).__getitem__(PyString.fromInterned("Content-length")));
      var1.setline(147);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var10 = PyString.fromInterned("xxx");
      PyObject var10002 = var10._in(var1.getlocal(0).__getattr__("conn").__getattr__("reqs").__getitem__(Py.newInteger(1)).__getattr__("data"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _no_way$15(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      throw Py.makeException(var1.getglobal("AssertionError").__call__(var2, var1.getlocal(0)));
   }

   public PyObject test_password_not_in_file$16(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(0).__getattr__("rc"), var1.getglobal("PYPIRC_NOPASSWORD"));
      var1.setline(152);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(153);
      var1.getlocal(1).__getattr__("_set_config").__call__(var2);
      var1.setline(154);
      var1.getlocal(1).__getattr__("finalize_options").__call__(var2);
      var1.setline(155);
      var1.getlocal(1).__getattr__("send_metadata").__call__(var2);
      var1.setline(159);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("distribution").__getattr__("password"), (PyObject)PyString.fromInterned("password"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_registering$17(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(164);
      var3 = var1.getglobal("RawInputs").__call__((ThreadState)var2, PyString.fromInterned("2"), (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("tarek@ziade.org"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(165);
      var3 = var1.getlocal(2).__getattr__("__call__");
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(168);
         var1.getlocal(1).__getattr__("run").__call__(var2);
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(170);
         var1.getglobal("register_module").__delattr__("raw_input");
         throw (Throwable)var4;
      }

      var1.setline(170);
      var1.getglobal("register_module").__delattr__("raw_input");
      var1.setline(173);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("conn").__getattr__("reqs")), (PyObject)Py.newInteger(1));
      var1.setline(174);
      var3 = var1.getlocal(0).__getattr__("conn").__getattr__("reqs").__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(175);
      var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(3).__getattr__("headers"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(176);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getitem__(PyString.fromInterned("Content-length")), (PyObject)PyString.fromInterned("608"));
      var1.setline(177);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var5 = PyString.fromInterned("tarek");
      PyObject var10002 = var5._in(var1.getlocal(3).__getattr__("data"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_password_reset$18(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(182);
      var3 = var1.getglobal("RawInputs").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("3"), (PyObject)PyString.fromInterned("tarek@ziade.org"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getlocal(2).__getattr__("__call__");
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(186);
         var1.getlocal(1).__getattr__("run").__call__(var2);
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(188);
         var1.getglobal("register_module").__delattr__("raw_input");
         throw (Throwable)var4;
      }

      var1.setline(188);
      var1.getglobal("register_module").__delattr__("raw_input");
      var1.setline(191);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("conn").__getattr__("reqs")), (PyObject)Py.newInteger(1));
      var1.setline(192);
      var3 = var1.getlocal(0).__getattr__("conn").__getattr__("reqs").__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(193);
      var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(3).__getattr__("headers"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(194);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getitem__(PyString.fromInterned("Content-length")), (PyObject)PyString.fromInterned("290"));
      var1.setline(195);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var5 = PyString.fromInterned("tarek");
      PyObject var10002 = var5._in(var1.getlocal(3).__getattr__("data"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_strict$19(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(206);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(207);
      PyInteger var7 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"strict", var7);
      var3 = null;
      var1.setline(208);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(1).__getattr__("run"));
      var1.setline(211);
      PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyUnicode.fromInterned("éxéxé"), PyString.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyString.fromInterned("xxx"), PyString.fromInterned("long_description"), PyString.fromInterned("title\n==\n\ntext")});
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(216);
      var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2, var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(217);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(218);
      var7 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"strict", var7);
      var3 = null;
      var1.setline(219);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(1).__getattr__("run"));
      var1.setline(222);
      PyString var9 = PyString.fromInterned("title\n=====\n\ntext");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("long_description"), var9);
      var3 = null;
      var1.setline(223);
      var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2, var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(224);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(225);
      var7 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"strict", var7);
      var3 = null;
      var1.setline(226);
      var3 = var1.getglobal("RawInputs").__call__((ThreadState)var2, PyString.fromInterned("1"), (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("y"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getlocal(3).__getattr__("__call__");
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(230);
         var1.getlocal(1).__getattr__("run").__call__(var2);
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(232);
         var1.getglobal("register_module").__delattr__("raw_input");
         throw (Throwable)var6;
      }

      var1.setline(232);
      var1.getglobal("register_module").__delattr__("raw_input");
      var1.setline(235);
      var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(236);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(237);
      var3 = var1.getglobal("RawInputs").__call__((ThreadState)var2, PyString.fromInterned("1"), (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("y"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getlocal(3).__getattr__("__call__");
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(241);
         var1.getlocal(1).__getattr__("run").__call__(var2);
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(243);
         var1.getglobal("register_module").__delattr__("raw_input");
         throw (Throwable)var5;
      }

      var1.setline(243);
      var1.getglobal("register_module").__delattr__("raw_input");
      var1.setline(246);
      var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyUnicode.fromInterned("xxx"), PyString.fromInterned("author"), PyUnicode.fromInterned("Éric"), PyString.fromInterned("author_email"), PyUnicode.fromInterned("xxx"), PyUnicode.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyUnicode.fromInterned("xxx"), PyString.fromInterned("description"), PyUnicode.fromInterned("Something about esszet ß"), PyString.fromInterned("long_description"), PyUnicode.fromInterned("More things about esszet ß")});
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(252);
      var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2, var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(253);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(254);
      var7 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"strict", var7);
      var3 = null;
      var1.setline(255);
      var3 = var1.getglobal("RawInputs").__call__((ThreadState)var2, PyString.fromInterned("1"), (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("y"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(256);
      var3 = var1.getlocal(3).__getattr__("__call__");
      var1.getglobal("register_module").__setattr__("raw_input", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(259);
         var1.getlocal(1).__getattr__("run").__call__(var2);
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(261);
         var1.getglobal("register_module").__delattr__("raw_input");
         throw (Throwable)var4;
      }

      var1.setline(261);
      var1.getglobal("register_module").__delattr__("raw_input");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_register_invalid_long_description$20(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString var3 = PyString.fromInterned(":funkie:`str`");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(266);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx"), PyString.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyString.fromInterned("xxx"), PyString.fromInterned("long_description"), var1.getlocal(1)});
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(270);
      PyObject var5 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(271);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(272);
      var5 = var1.getglobal("True");
      var1.getlocal(3).__setattr__("strict", var5);
      var3 = null;
      var1.setline(273);
      var5 = var1.getglobal("RawInputs").__call__((ThreadState)var2, PyString.fromInterned("2"), (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("tarek@ziade.org"));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(274);
      var5 = var1.getlocal(4);
      var1.getglobal("register_module").__setattr__("raw_input", var5);
      var3 = null;
      var1.setline(275);
      var1.getlocal(0).__getattr__("addCleanup").__call__((ThreadState)var2, var1.getglobal("delattr"), (PyObject)var1.getglobal("register_module"), (PyObject)PyString.fromInterned("raw_input"));
      var1.setline(276);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(3).__getattr__("run"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_check_metadata_deprecated$21(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(280);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_cmd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var6;
      PyObject var4 = (var6 = ContextGuard.getManager(var1.getglobal("check_warnings").__call__(var2))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(2, var4);
            var1.setline(282);
            var1.getglobal("warnings").__getattr__("simplefilter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("always"));
            var1.setline(283);
            var1.getlocal(1).__getattr__("check_metadata").__call__(var2);
            var1.setline(284);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("warnings")), (PyObject)Py.newInteger(1));
         } catch (Throwable var5) {
            if (var6.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var6.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$22(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("RegisterTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_register$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      RawInputs$1 = Py.newCode(0, var2, var1, "RawInputs", 42, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "answers"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 44, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prompt"};
      __call__$3 = Py.newCode(2, var2, var1, "__call__", 48, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FakeOpener$4 = Py.newCode(0, var2, var1, "FakeOpener", 54, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$5 = Py.newCode(1, var2, var1, "__init__", 56, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __call__$6 = Py.newCode(2, var2, var1, "__call__", 59, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req"};
      open$7 = Py.newCode(2, var2, var1, "open", 62, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read$8 = Py.newCode(1, var2, var1, "read", 66, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RegisterTestCase$9 = Py.newCode(0, var2, var1, "RegisterTestCase", 69, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_getpass"};
      setUp$10 = Py.newCode(1, var2, var1, "setUp", 71, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt"};
      _getpass$11 = Py.newCode(1, var2, var1, "_getpass", 75, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$12 = Py.newCode(1, var2, var1, "tearDown", 81, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "metadata", "pkg_info", "dist"};
      _get_cmd$13 = Py.newCode(2, var2, var1, "_get_cmd", 86, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "inputs", "f", "content", "_no_way", "req1", "req2"};
      test_create_pypirc$14 = Py.newCode(1, var2, var1, "test_create_pypirc", 94, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt"};
      _no_way$15 = Py.newCode(1, var2, var1, "_no_way", 134, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      test_password_not_in_file$16 = Py.newCode(1, var2, var1, "test_password_not_in_file", 149, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "inputs", "req", "headers"};
      test_registering$17 = Py.newCode(1, var2, var1, "test_registering", 161, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "inputs", "req", "headers"};
      test_password_reset$18 = Py.newCode(1, var2, var1, "test_password_reset", 179, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "metadata", "inputs"};
      test_strict$19 = Py.newCode(1, var2, var1, "test_strict", 197, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "description", "metadata", "cmd", "inputs"};
      test_register_invalid_long_description$20 = Py.newCode(1, var2, var1, "test_register_invalid_long_description", 263, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "w"};
      test_check_metadata_deprecated$21 = Py.newCode(1, var2, var1, "test_check_metadata_deprecated", 278, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$22 = Py.newCode(0, var2, var1, "test_suite", 286, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_register$py("distutils/tests/test_register$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_register$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.RawInputs$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__call__$3(var2, var3);
         case 4:
            return this.FakeOpener$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.__call__$6(var2, var3);
         case 7:
            return this.open$7(var2, var3);
         case 8:
            return this.read$8(var2, var3);
         case 9:
            return this.RegisterTestCase$9(var2, var3);
         case 10:
            return this.setUp$10(var2, var3);
         case 11:
            return this._getpass$11(var2, var3);
         case 12:
            return this.tearDown$12(var2, var3);
         case 13:
            return this._get_cmd$13(var2, var3);
         case 14:
            return this.test_create_pypirc$14(var2, var3);
         case 15:
            return this._no_way$15(var2, var3);
         case 16:
            return this.test_password_not_in_file$16(var2, var3);
         case 17:
            return this.test_registering$17(var2, var3);
         case 18:
            return this.test_password_reset$18(var2, var3);
         case 19:
            return this.test_strict$19(var2, var3);
         case 20:
            return this.test_register_invalid_long_description$20(var2, var3);
         case 21:
            return this.test_check_metadata_deprecated$21(var2, var3);
         case 22:
            return this.test_suite$22(var2, var3);
         default:
            return null;
      }
   }
}
