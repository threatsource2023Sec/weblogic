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
@Filename("runpy.py")
public class runpy$py extends PyFunctionTable implements PyRunnable {
   static runpy$py self;
   static final PyCode f$0;
   static final PyCode _TempModule$1;
   static final PyCode __init__$2;
   static final PyCode __enter__$3;
   static final PyCode __exit__$4;
   static final PyCode _ModifiedArgv0$5;
   static final PyCode __init__$6;
   static final PyCode __enter__$7;
   static final PyCode __exit__$8;
   static final PyCode _run_code$9;
   static final PyCode _run_module_code$10;
   static final PyCode _get_filename$11;
   static final PyCode _get_module_details$12;
   static final PyCode _get_main_module_details$13;
   static final PyCode _run_module_as_main$14;
   static final PyCode run_module$15;
   static final PyCode _get_importer$16;
   static final PyCode _get_code_from_file$17;
   static final PyCode run_path$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("runpy.py - locating and running Python code using the module namespace\n\nProvides support for locating and running Python scripts using the Python\nmodule namespace instead of the native filesystem.\n\nThis allows Python code to play nicely with non-filesystem based PEP 302\nimporters when locating support scripts as well as when importing modules.\n"));
      var1.setline(8);
      PyString.fromInterned("runpy.py - locating and running Python code using the module namespace\n\nProvides support for locating and running Python scripts using the Python\nmodule namespace instead of the native filesystem.\n\nThis allows Python code to play nicely with non-filesystem based PEP 302\nimporters when locating support scripts as well as when importing modules.\n");
      var1.setline(12);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(13);
      var3 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var3);
      var3 = null;
      var1.setline(14);
      String[] var7 = new String[]{"read_code"};
      PyObject[] var8 = imp.importFrom("pkgutil", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("read_code", var4);
      var4 = null;

      try {
         var1.setline(16);
         var7 = new String[]{"get_loader"};
         var8 = imp.importFrom("imp", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal("get_loader", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(18);
         String[] var9 = new String[]{"get_loader"};
         PyObject[] var11 = imp.importFrom("pkgutil", var9, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("get_loader", var5);
         var5 = null;
      }

      var1.setline(20);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("run_module"), PyString.fromInterned("run_path")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(24);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_TempModule", var8, _TempModule$1);
      var1.setlocal("_TempModule", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(47);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_ModifiedArgv0", var8, _ModifiedArgv0$5);
      var1.setlocal("_ModifiedArgv0", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(62);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var13 = new PyFunction(var1.f_globals, var8, _run_code$9, PyString.fromInterned("Helper to run code in nominated namespace"));
      var1.setlocal("_run_code", var13);
      var3 = null;
      var1.setline(75);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var8, _run_module_code$10, PyString.fromInterned("Helper to run code in new namespace with sys modified"));
      var1.setlocal("_run_module_code", var13);
      var3 = null;
      var1.setline(92);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, _get_filename$11, (PyObject)null);
      var1.setlocal("_get_filename", var13);
      var3 = null;
      var1.setline(100);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, _get_module_details$12, (PyObject)null);
      var1.setlocal("_get_module_details", var13);
      var3 = null;
      var1.setline(120);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, _get_main_module_details$13, (PyObject)null);
      var1.setlocal("_get_main_module_details", var13);
      var3 = null;
      var1.setline(136);
      var8 = new PyObject[]{var1.getname("True")};
      var13 = new PyFunction(var1.f_globals, var8, _run_module_as_main$14, PyString.fromInterned("Runs the designated module in the __main__ namespace\n\n       Note that the executed module will have full access to the\n       __main__ namespace. If this is not desirable, the run_module()\n       function should be used to run the module code in a fresh namespace.\n\n       At the very least, these variables in __main__ will be overwritten:\n           __name__\n           __file__\n           __loader__\n           __package__\n    "));
      var1.setlocal("_run_module_as_main", var13);
      var3 = null;
      var1.setline(164);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("False")};
      var13 = new PyFunction(var1.f_globals, var8, run_module$15, PyString.fromInterned("Execute a module's code without importing it\n\n       Returns the resulting top level namespace dictionary\n    "));
      var1.setlocal("run_module", var13);
      var3 = null;
      var1.setline(185);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, _get_importer$16, PyString.fromInterned("Python version of PyImport_GetImporter C API function"));
      var1.setlocal("_get_importer", var13);
      var3 = null;
      var1.setline(213);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, _get_code_from_file$17, (PyObject)null);
      var1.setlocal("_get_code_from_file", var13);
      var3 = null;
      var1.setline(223);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var8, run_path$18, PyString.fromInterned("Execute code located at the specified filesystem location\n\n       Returns the resulting top level namespace dictionary\n\n       The file path may refer directly to a Python script (i.e.\n       one that could be directly executed with execfile) or else\n       it may refer to a zipfile or directory containing a top\n       level __main__.py script.\n    "));
      var1.setlocal("run_path", var13);
      var3 = null;
      var1.setline(272);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(274);
         var3 = var1.getname("len").__call__(var2, var1.getname("sys").__getattr__("argv"));
         var10000 = var3._lt(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(275);
            var3 = var1.getname("sys").__getattr__("stderr");
            Py.println(var3, PyString.fromInterned("No module specified for execution"));
         } else {
            var1.setline(277);
            var1.getname("sys").__getattr__("argv").__delitem__((PyObject)Py.newInteger(0));
            var1.setline(278);
            var1.getname("_run_module_as_main").__call__(var2, var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _TempModule$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Temporarily replace a module in sys.modules with an empty namespace"));
      var1.setline(25);
      PyString.fromInterned("Temporarily replace a module in sys.modules with an empty namespace");
      var1.setline(26);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$3, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$4, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("mod_name", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getglobal("imp").__getattr__("new_module").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(29);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_saved_module", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$3(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getlocal(0).__getattr__("mod_name");
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(34);
         var1.getlocal(0).__getattr__("_saved_module").__getattr__("append").__call__(var2, var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1)));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("KeyError"))) {
            throw var5;
         }

         var1.setline(36);
      }

      var1.setline(37);
      var3 = var1.getlocal(0).__getattr__("module");
      var1.getglobal("sys").__getattr__("modules").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$4(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_saved_module").__nonzero__()) {
         var1.setline(42);
         var3 = var1.getlocal(0).__getattr__("_saved_module").__getitem__(Py.newInteger(0));
         var1.getglobal("sys").__getattr__("modules").__setitem__(var1.getlocal(0).__getattr__("mod_name"), var3);
         var3 = null;
      } else {
         var1.setline(44);
         var1.getglobal("sys").__getattr__("modules").__delitem__(var1.getlocal(0).__getattr__("mod_name"));
      }

      var1.setline(45);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_saved_module", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ModifiedArgv0$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(48);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$7, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$8, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getglobal("object").__call__(var2);
      var1.getlocal(0).__setattr__("_saved_value", var3);
      var1.getlocal(0).__setattr__("_sentinel", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$7(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3 = var1.getlocal(0).__getattr__("_saved_value");
      PyObject var10000 = var3._isnot(var1.getlocal(0).__getattr__("_sentinel"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(54);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Already preserving saved value")));
      } else {
         var1.setline(55);
         var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("_saved_value", var3);
         var3 = null;
         var1.setline(56);
         var3 = var1.getlocal(0).__getattr__("value");
         var1.getglobal("sys").__getattr__("argv").__setitem__((PyObject)Py.newInteger(0), var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __exit__$8(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getattr__("_sentinel");
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(0).__getattr__("_saved_value");
      var1.getglobal("sys").__getattr__("argv").__setitem__((PyObject)Py.newInteger(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _run_code$9(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyString.fromInterned("Helper to run code in nominated namespace");
      var1.setline(66);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.setline(68);
      var10000 = var1.getlocal(1).__getattr__("update");
      PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      String[] var4 = new String[]{"__name__", "__file__", "__loader__", "__package__"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(72);
      Py.exec(var1.getlocal(0), var1.getlocal(1), (PyObject)null);
      var1.setline(73);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _run_module_code$10(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(78);
      PyString.fromInterned("Helper to run code in new namespace with sys modified");
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("_TempModule").__call__(var2, var1.getlocal(2)))).__enter__(var2);

      label27: {
         try {
            label31: {
               var1.setlocal(6, var4);
               ContextManager var9;
               PyObject var5 = (var9 = ContextGuard.getManager(var1.getglobal("_ModifiedArgv0").__call__(var2, var1.getlocal(3)))).__enter__(var2);

               try {
                  var1.setline(80);
                  var5 = var1.getlocal(6).__getattr__("module").__getattr__("__dict__");
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(81);
                  PyObject var10000 = var1.getglobal("_run_code");
                  PyObject[] var10 = new PyObject[]{var1.getlocal(0), var1.getlocal(7), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
                  var10000.__call__(var2, var10);
               } catch (Throwable var6) {
                  if (var9.__exit__(var2, Py.setException(var6, var1))) {
                     break label31;
                  }

                  throw (Throwable)Py.makeException();
               }

               var9.__exit__(var2, (PyException)null);
            }
         } catch (Throwable var7) {
            if (var3.__exit__(var2, Py.setException(var7, var1))) {
               break label27;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(85);
      PyObject var8 = var1.getlocal(7).__getattr__("copy").__call__(var2);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject _get_filename$11(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("get_filename"), PyString.fromInterned("_get_filename")})).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(93);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(97);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(94);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getglobal("None"));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(95);
         var5 = var1.getlocal(3);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(96);
      var5 = var1.getlocal(3).__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _get_module_details$12(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getglobal("get_loader").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(102);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("No module named %s")._mod(var1.getlocal(0))));
      } else {
         var1.setline(104);
         if (var1.getlocal(1).__getattr__("is_package").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(105);
            var3 = var1.getlocal(0);
            var10000 = var3._eq(PyString.fromInterned("__main__"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".__main__"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(106);
               throw Py.makeException(var1.getglobal("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot use package as __main__ module")));
            } else {
               try {
                  var1.setline(108);
                  var3 = var1.getlocal(0)._add(PyString.fromInterned(".__main__"));
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(109);
                  var3 = var1.getglobal("_get_module_details").__call__(var2, var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var3;
               } catch (Throwable var6) {
                  PyException var8 = Py.setException(var6, var1);
                  if (var8.match(var1.getglobal("ImportError"))) {
                     PyObject var5 = var8.value;
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(111);
                     throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("%s; %r is a package and cannot ")._add(PyString.fromInterned("be directly executed"))._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(0)}))));
                  } else {
                     throw var8;
                  }
               }
            }
         } else {
            var1.setline(113);
            PyObject var4 = var1.getlocal(1).__getattr__("get_code").__call__(var2, var1.getlocal(0));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(114);
            var4 = var1.getlocal(4);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(115);
               throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("No code object available for %s")._mod(var1.getlocal(0))));
            } else {
               var1.setline(116);
               var4 = var1.getglobal("_get_filename").__call__(var2, var1.getlocal(1), var1.getlocal(0));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(117);
               PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(4), var1.getlocal(5)});
               var1.f_lasti = -1;
               return var7;
            }
         }
      }
   }

   public PyObject _get_main_module_details$13(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString var3 = PyString.fromInterned("__main__");
      var1.setlocal(0, var3);
      var3 = null;

      try {
         var1.setline(125);
         PyObject var7 = var1.getglobal("_get_module_details").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var7;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("ImportError"))) {
            PyObject var5 = var4.value;
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(127);
            var5 = var1.getlocal(0);
            PyObject var10000 = var5._in(var1.getglobal("str").__call__(var2, var1.getlocal(1)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(128);
               throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("can't find %r module in %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("sys").__getattr__("path").__getitem__(Py.newInteger(0))}))));
            } else {
               var1.setline(130);
               throw Py.makeException();
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject _run_module_as_main$14(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Runs the designated module in the __main__ namespace\n\n       Note that the executed module will have full access to the\n       __main__ namespace. If this is not desirable, the run_module()\n       function should be used to run the module code in a fresh namespace.\n\n       At the very least, these variables in __main__ will be overwritten:\n           __name__\n           __file__\n           __loader__\n           __package__\n    ");

      PyObject var10000;
      PyException var3;
      PyObject var7;
      try {
         var1.setline(150);
         var10000 = var1.getlocal(1);
         if (!var10000.__nonzero__()) {
            var7 = var1.getlocal(0);
            var10000 = var7._ne(PyString.fromInterned("__main__"));
            var3 = null;
         }

         PyObject var5;
         PyObject[] var8;
         if (var10000.__nonzero__()) {
            var1.setline(151);
            var7 = var1.getglobal("_get_module_details").__call__(var2, var1.getlocal(0));
            var8 = Py.unpackSequence(var7, 4);
            var5 = var8[0];
            var1.setlocal(0, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var8[2];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var8[3];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(153);
            var7 = var1.getglobal("_get_main_module_details").__call__(var2);
            var8 = Py.unpackSequence(var7, 4);
            var5 = var8[0];
            var1.setlocal(0, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var8[2];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var8[3];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
         }
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(155);
         var4 = PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("executable"), var1.getglobal("str").__call__(var2, var1.getlocal(5))}));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(156);
         var1.getglobal("sys").__getattr__("exit").__call__(var2, var1.getlocal(6));
      }

      var1.setline(157);
      var7 = var1.getlocal(0).__getattr__("rpartition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0));
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(158);
      var7 = var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("__main__")).__getattr__("__dict__");
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(159);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(160);
         var7 = var1.getlocal(4);
         var1.getglobal("sys").__getattr__("argv").__setitem__((PyObject)Py.newInteger(0), var7);
         var3 = null;
      }

      var1.setline(161);
      var10000 = var1.getglobal("_run_code");
      PyObject[] var9 = new PyObject[]{var1.getlocal(3), var1.getlocal(8), var1.getglobal("None"), PyString.fromInterned("__main__"), var1.getlocal(4), var1.getlocal(2), var1.getlocal(7)};
      var7 = var10000.__call__(var2, var9);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject run_module$15(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyString.fromInterned("Execute a module's code without importing it\n\n       Returns the resulting top level namespace dictionary\n    ");
      var1.setline(170);
      PyObject var3 = var1.getglobal("_get_module_details").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(0, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(171);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(172);
         var3 = var1.getlocal(0);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(173);
      var3 = var1.getlocal(0).__getattr__("rpartition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(174);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(175);
         var10000 = var1.getglobal("_run_module_code");
         PyObject[] var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(1), var1.getlocal(2), var1.getlocal(6), var1.getlocal(4), var1.getlocal(7)};
         var3 = var10000.__call__(var2, var6);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(179);
         var10000 = var1.getglobal("_run_code");
         var4 = new PyObject[]{var1.getlocal(5), new PyDictionary(Py.EmptyObjects), var1.getlocal(1), var1.getlocal(2), var1.getlocal(6), var1.getlocal(4), var1.getlocal(7)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_importer$16(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("Python version of PyImport_GetImporter C API function");
      var1.setline(187);
      PyObject var3 = var1.getglobal("sys").__getattr__("path_importer_cache");
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var7;
      try {
         var1.setline(189);
         var3 = var1.getlocal(1).__getitem__(var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var10) {
         PyException var11 = Py.setException(var10, var1);
         if (!var11.match(var1.getglobal("KeyError"))) {
            throw var11;
         }

         var1.setline(194);
         PyObject var4 = var1.getglobal("None");
         var1.getlocal(1).__setitem__(var1.getlocal(0), var4);
         var4 = null;
         var1.setline(195);
         var4 = var1.getglobal("sys").__getattr__("path_hooks").__iter__();

         while(true) {
            var1.setline(195);
            PyObject var5 = var4.__iternext__();
            PyException var6;
            PyObject var12;
            if (var5 == null) {
               try {
                  var1.setline(207);
                  var12 = var1.getglobal("imp").__getattr__("NullImporter").__call__(var2, var1.getlocal(0));
                  var1.setlocal(2, var12);
                  var6 = null;
                  break;
               } catch (Throwable var8) {
                  var6 = Py.setException(var8, var1);
                  if (var6.match(var1.getglobal("ImportError"))) {
                     var1.setline(209);
                     var7 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var7;
                  }

                  throw var6;
               }
            }

            var1.setlocal(3, var5);

            try {
               var1.setline(197);
               var12 = var1.getlocal(3).__call__(var2, var1.getlocal(0));
               var1.setlocal(2, var12);
               var6 = null;
               break;
            } catch (Throwable var9) {
               var6 = Py.setException(var9, var1);
               if (!var6.match(var1.getglobal("ImportError"))) {
                  throw var6;
               }

               var1.setline(200);
            }
         }

         var1.setline(210);
         var4 = var1.getlocal(2);
         var1.getlocal(1).__setitem__(var1.getlocal(0), var4);
         var4 = null;
      }

      var1.setline(211);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject _get_code_from_file$17(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

      label33: {
         try {
            var1.setlocal(1, var4);
            var1.setline(216);
            var4 = var1.getglobal("read_code").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
         } catch (Throwable var6) {
            if (var3.__exit__(var2, Py.setException(var6, var1))) {
               break label33;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(217);
      PyObject var7 = var1.getlocal(2);
      PyObject var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         label38: {
            var4 = (var3 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rU")))).__enter__(var2);

            try {
               var1.setlocal(1, var4);
               var1.setline(220);
               var4 = var1.getglobal("compile").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("read").__call__(var2), (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("exec"));
               var1.setlocal(2, var4);
               var4 = null;
            } catch (Throwable var5) {
               if (var3.__exit__(var2, Py.setException(var5, var1))) {
                  break label38;
               }

               throw (Throwable)Py.makeException();
            }

            var3.__exit__(var2, (PyException)null);
         }
      }

      var1.setline(221);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject run_path$18(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public runpy$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _TempModule$1 = Py.newCode(0, var2, var1, "_TempModule", 24, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mod_name"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mod_name"};
      __enter__$3 = Py.newCode(1, var2, var1, "__enter__", 31, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __exit__$4 = Py.newCode(2, var2, var1, "__exit__", 40, true, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ModifiedArgv0$5 = Py.newCode(0, var2, var1, "_ModifiedArgv0", 47, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$6 = Py.newCode(2, var2, var1, "__init__", 48, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$7 = Py.newCode(1, var2, var1, "__enter__", 52, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __exit__$8 = Py.newCode(2, var2, var1, "__exit__", 58, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "run_globals", "init_globals", "mod_name", "mod_fname", "mod_loader", "pkg_name"};
      _run_code$9 = Py.newCode(7, var2, var1, "_run_code", 62, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"code", "init_globals", "mod_name", "mod_fname", "mod_loader", "pkg_name", "temp_module", "mod_globals"};
      _run_module_code$10 = Py.newCode(6, var2, var1, "_run_module_code", 75, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"loader", "mod_name", "attr", "meth"};
      _get_filename$11 = Py.newCode(2, var2, var1, "_get_filename", 92, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mod_name", "loader", "pkg_main_name", "e", "code", "filename"};
      _get_module_details$12 = Py.newCode(1, var2, var1, "_get_module_details", 100, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"main_name", "exc"};
      _get_main_module_details$13 = Py.newCode(0, var2, var1, "_get_main_module_details", 120, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mod_name", "alter_argv", "loader", "code", "fname", "exc", "msg", "pkg_name", "main_globals"};
      _run_module_as_main$14 = Py.newCode(2, var2, var1, "_run_module_as_main", 136, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mod_name", "init_globals", "run_name", "alter_sys", "loader", "code", "fname", "pkg_name"};
      run_module$15 = Py.newCode(4, var2, var1, "run_module", 164, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path_name", "cache", "importer", "hook"};
      _get_importer$16 = Py.newCode(1, var2, var1, "_get_importer", 185, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fname", "f", "code"};
      _get_code_from_file$17 = Py.newCode(1, var2, var1, "_get_code_from_file", 213, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path_name", "init_globals", "run_name", "importer", "code", "main_name", "saved_main", "mod_name", "loader", "fname", "pkg_name", "temp_module", "mod_globals"};
      run_path$18 = Py.newCode(3, var2, var1, "run_path", 223, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new runpy$py("runpy$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(runpy$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._TempModule$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__enter__$3(var2, var3);
         case 4:
            return this.__exit__$4(var2, var3);
         case 5:
            return this._ModifiedArgv0$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.__enter__$7(var2, var3);
         case 8:
            return this.__exit__$8(var2, var3);
         case 9:
            return this._run_code$9(var2, var3);
         case 10:
            return this._run_module_code$10(var2, var3);
         case 11:
            return this._get_filename$11(var2, var3);
         case 12:
            return this._get_module_details$12(var2, var3);
         case 13:
            return this._get_main_module_details$13(var2, var3);
         case 14:
            return this._run_module_as_main$14(var2, var3);
         case 15:
            return this.run_module$15(var2, var3);
         case 16:
            return this._get_importer$16(var2, var3);
         case 17:
            return this._get_code_from_file$17(var2, var3);
         case 18:
            return this.run_path$18(var2, var3);
         default:
            return null;
      }
   }
}
