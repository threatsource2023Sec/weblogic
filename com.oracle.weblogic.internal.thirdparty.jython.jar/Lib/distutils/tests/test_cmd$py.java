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
@Filename("distutils/tests/test_cmd.py")
public class test_cmd$py extends PyFunctionTable implements PyRunnable {
   static test_cmd$py self;
   static final PyCode f$0;
   static final PyCode MyCmd$1;
   static final PyCode initialize_options$2;
   static final PyCode CommandTestCase$3;
   static final PyCode setUp$4;
   static final PyCode test_ensure_string_list$5;
   static final PyCode test_make_file$6;
   static final PyCode _execute$7;
   static final PyCode test_dump_options$8;
   static final PyCode _announce$9;
   static final PyCode test_ensure_string$10;
   static final PyCode test_ensure_string_list$11;
   static final PyCode test_ensure_filename$12;
   static final PyCode test_ensure_dirname$13;
   static final PyCode test_debug_print$14;
   static final PyCode test_suite$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.cmd."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.cmd.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"captured_stdout", "run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"Command"};
      var6 = imp.importFrom("distutils.cmd", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.dist", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"DistutilsOptionError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"debug"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("debug", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("MyCmd", var6, MyCmd$1);
      var1.setlocal("MyCmd", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("CommandTestCase", var6, CommandTestCase$3);
      var1.setlocal("CommandTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(123);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$15, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(126);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyCmd$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CommandTestCase$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$4, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(21);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ensure_string_list$5, (PyObject)null);
      var1.setlocal("test_ensure_string_list", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_file$6, (PyObject)null);
      var1.setlocal("test_make_file", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dump_options$8, (PyObject)null);
      var1.setlocal("test_dump_options", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ensure_string$10, (PyObject)null);
      var1.setlocal("test_ensure_string", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ensure_string_list$11, (PyObject)null);
      var1.setlocal("test_ensure_string_list", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ensure_filename$12, (PyObject)null);
      var1.setlocal("test_ensure_filename", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ensure_dirname$13, (PyObject)null);
      var1.setlocal("test_ensure_dirname", var4);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_debug_print$14, (PyObject)null);
      var1.setlocal("test_debug_print", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$4(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("MyCmd").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("cmd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ensure_string_list$5(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(24);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("one"), Py.newInteger(2), PyString.fromInterned("three")});
      var1.getlocal(1).__setattr__((String)"not_string_list", var4);
      var3 = null;
      var1.setline(25);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two"), PyString.fromInterned("three")});
      var1.getlocal(1).__setattr__((String)"yes_string_list", var4);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("object").__call__(var2);
      var1.getlocal(1).__setattr__("not_string_list2", var3);
      var3 = null;
      var1.setline(27);
      PyString var5 = PyString.fromInterned("ok");
      var1.getlocal(1).__setattr__((String)"yes_string_list2", var5);
      var3 = null;
      var1.setline(28);
      var1.getlocal(1).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("yes_string_list"));
      var1.setline(29);
      var1.getlocal(1).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("yes_string_list2"));
      var1.setline(31);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsOptionError"), (PyObject)var1.getlocal(1).__getattr__("ensure_string_list"), (PyObject)PyString.fromInterned("not_string_list"));
      var1.setline(34);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsOptionError"), (PyObject)var1.getlocal(1).__getattr__("ensure_string_list"), (PyObject)PyString.fromInterned("not_string_list2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_file$6(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(39);
      PyObject var3 = var1.getderef(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(42);
      PyObject var10000 = var1.getderef(0).__getattr__("assertRaises");
      PyObject[] var5 = new PyObject[]{var1.getglobal("TypeError"), var1.getlocal(1).__getattr__("make_file"), Py.newInteger(1), PyString.fromInterned(""), PyString.fromInterned("func"), new PyTuple(Py.EmptyObjects)};
      String[] var4 = new String[]{"infiles", "outfile", "func", "args"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(46);
      var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = _execute$7;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(48);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("force", var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("execute", var3);
      var3 = null;
      var1.setline(50);
      var10000 = var1.getlocal(1).__getattr__("make_file");
      var5 = new PyObject[]{PyString.fromInterned("in"), PyString.fromInterned("out"), PyString.fromInterned("func"), new PyTuple(Py.EmptyObjects)};
      var4 = new String[]{"infiles", "outfile", "func", "args"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _execute$7(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      var1.getderef(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("generating out from in"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dump_options$8(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(55);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = _announce$9;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(57);
      PyObject var6 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(58);
      var6 = var1.getlocal(1);
      var1.getlocal(2).__setattr__("announce", var6);
      var3 = null;
      var1.setline(59);
      PyInteger var7 = Py.newInteger(1);
      var1.getlocal(2).__setattr__((String)"option1", var7);
      var3 = null;
      var1.setline(60);
      var7 = Py.newInteger(1);
      var1.getlocal(2).__setattr__((String)"option2", var7);
      var3 = null;
      var1.setline(61);
      var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("option1"), PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned("option2"), PyString.fromInterned(""), PyString.fromInterned("")})});
      var1.getlocal(2).__setattr__((String)"user_options", var3);
      var3 = null;
      var1.setline(62);
      var1.getlocal(2).__getattr__("dump_options").__call__(var2);
      var1.setline(64);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("command options for 'MyCmd':"), PyString.fromInterned("  option1 = 1"), PyString.fromInterned("  option2 = 1")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(66);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _announce$9(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      var1.getderef(0).__getattr__("append").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ensure_string$10(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(70);
      PyString var4 = PyString.fromInterned("ok");
      var1.getlocal(1).__setattr__((String)"option1", var4);
      var3 = null;
      var1.setline(71);
      var1.getlocal(1).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option1"));
      var1.setline(73);
      var3 = var1.getglobal("None");
      var1.getlocal(1).__setattr__("option2", var3);
      var3 = null;
      var1.setline(74);
      var1.getlocal(1).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option2"), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(75);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("option2")));
      var1.setline(77);
      PyInteger var5 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"option3", var5);
      var3 = null;
      var1.setline(78);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsOptionError"), (PyObject)var1.getlocal(1).__getattr__("ensure_string"), (PyObject)PyString.fromInterned("option3"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ensure_string_list$11(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(82);
      PyString var4 = PyString.fromInterned("ok,dok");
      var1.getlocal(1).__setattr__((String)"option1", var4);
      var3 = null;
      var1.setline(83);
      var1.getlocal(1).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option1"));
      var1.setline(84);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("option1"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("ok"), PyString.fromInterned("dok")})));
      var1.setline(86);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("xxx"), PyString.fromInterned("www")});
      var1.getlocal(1).__setattr__((String)"option2", var5);
      var3 = null;
      var1.setline(87);
      var1.getlocal(1).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option2"));
      var1.setline(89);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("ok"), Py.newInteger(2)});
      var1.getlocal(1).__setattr__((String)"option3", var5);
      var3 = null;
      var1.setline(90);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsOptionError"), (PyObject)var1.getlocal(1).__getattr__("ensure_string_list"), (PyObject)PyString.fromInterned("option3"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ensure_filename$12(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(95);
      var3 = var1.getglobal("__file__");
      var1.getlocal(1).__setattr__("option1", var3);
      var3 = null;
      var1.setline(96);
      var1.getlocal(1).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option1"));
      var1.setline(97);
      PyString var4 = PyString.fromInterned("xxx");
      var1.getlocal(1).__setattr__((String)"option2", var4);
      var3 = null;
      var1.setline(98);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsOptionError"), (PyObject)var1.getlocal(1).__getattr__("ensure_filename"), (PyObject)PyString.fromInterned("option2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ensure_dirname$13(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(102);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("__file__"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("curdir");
      }

      var3 = var10000;
      var1.getlocal(1).__setattr__("option1", var3);
      var3 = null;
      var1.setline(103);
      var1.getlocal(1).__getattr__("ensure_dirname").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option1"));
      var1.setline(104);
      PyString var4 = PyString.fromInterned("xxx");
      var1.getlocal(1).__setattr__((String)"option2", var4);
      var3 = null;
      var1.setline(105);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsOptionError"), (PyObject)var1.getlocal(1).__getattr__("ensure_dirname"), (PyObject)PyString.fromInterned("option2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_debug_print$14(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(108);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var9;
      PyObject var4 = (var9 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      label38: {
         try {
            var1.setlocal(2, var4);
            var1.setline(110);
            var1.getlocal(1).__getattr__("debug_print").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"));
         } catch (Throwable var8) {
            if (var9.__exit__(var2, Py.setException(var8, var1))) {
               break label38;
            }

            throw (Throwable)Py.makeException();
         }

         var9.__exit__(var2, (PyException)null);
      }

      var1.setline(111);
      var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(112);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(114);
      var3 = var1.getglobal("True");
      var1.getglobal("debug").__setattr__("DEBUG", var3);
      var3 = null;
      var3 = null;

      try {
         ContextManager var10;
         PyObject var5 = (var10 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

         label28: {
            try {
               var1.setlocal(2, var5);
               var1.setline(117);
               var1.getlocal(1).__getattr__("debug_print").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"));
            } catch (Throwable var6) {
               if (var10.__exit__(var2, Py.setException(var6, var1))) {
                  break label28;
               }

               throw (Throwable)Py.makeException();
            }

            var10.__exit__(var2, (PyException)null);
         }

         var1.setline(118);
         var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(119);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned("xxx\n"));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(121);
         var4 = var1.getglobal("False");
         var1.getglobal("debug").__setattr__("DEBUG", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(121);
      var4 = var1.getglobal("False");
      var1.getglobal("debug").__setattr__("DEBUG", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$15(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("CommandTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_cmd$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MyCmd$1 = Py.newCode(0, var2, var1, "MyCmd", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CommandTestCase$3 = Py.newCode(0, var2, var1, "CommandTestCase", 15, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dist"};
      setUp$4 = Py.newCode(1, var2, var1, "setUp", 17, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      test_ensure_string_list$5 = Py.newCode(1, var2, var1, "test_ensure_string_list", 21, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "_execute"};
      String[] var10001 = var2;
      test_cmd$py var10007 = self;
      var2 = new String[]{"self"};
      test_make_file$6 = Py.newCode(1, var10001, var1, "test_make_file", 37, false, false, var10007, 6, var2, (String[])null, 0, 4097);
      var2 = new String[]{"func", "args", "exec_msg", "level"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _execute$7 = Py.newCode(4, var10001, var1, "_execute", 46, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "_announce", "cmd", "wanted", "msgs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"msgs"};
      test_dump_options$8 = Py.newCode(1, var10001, var1, "test_dump_options", 52, false, false, var10007, 8, var2, (String[])null, 1, 4097);
      var2 = new String[]{"msg", "level"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"msgs"};
      _announce$9 = Py.newCode(2, var10001, var1, "_announce", 55, false, false, var10007, 9, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      test_ensure_string$10 = Py.newCode(1, var2, var1, "test_ensure_string", 68, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      test_ensure_string_list$11 = Py.newCode(1, var2, var1, "test_ensure_string_list", 80, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      test_ensure_filename$12 = Py.newCode(1, var2, var1, "test_ensure_filename", 93, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      test_ensure_dirname$13 = Py.newCode(1, var2, var1, "test_ensure_dirname", 100, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "stdout"};
      test_debug_print$14 = Py.newCode(1, var2, var1, "test_debug_print", 107, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$15 = Py.newCode(0, var2, var1, "test_suite", 123, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_cmd$py("distutils/tests/test_cmd$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_cmd$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MyCmd$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.CommandTestCase$3(var2, var3);
         case 4:
            return this.setUp$4(var2, var3);
         case 5:
            return this.test_ensure_string_list$5(var2, var3);
         case 6:
            return this.test_make_file$6(var2, var3);
         case 7:
            return this._execute$7(var2, var3);
         case 8:
            return this.test_dump_options$8(var2, var3);
         case 9:
            return this._announce$9(var2, var3);
         case 10:
            return this.test_ensure_string$10(var2, var3);
         case 11:
            return this.test_ensure_string_list$11(var2, var3);
         case 12:
            return this.test_ensure_filename$12(var2, var3);
         case 13:
            return this.test_ensure_dirname$13(var2, var3);
         case 14:
            return this.test_debug_print$14(var2, var3);
         case 15:
            return this.test_suite$15(var2, var3);
         default:
            return null;
      }
   }
}
