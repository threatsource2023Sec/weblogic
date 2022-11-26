import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("functools.py")
public class functools$py extends PyFunctionTable implements PyRunnable {
   static functools$py self;
   static final PyCode f$0;
   static final PyCode update_wrapper$1;
   static final PyCode wraps$2;
   static final PyCode total_ordering$3;
   static final PyCode f$4;
   static final PyCode f$5;
   static final PyCode f$6;
   static final PyCode f$7;
   static final PyCode f$8;
   static final PyCode f$9;
   static final PyCode f$10;
   static final PyCode f$11;
   static final PyCode f$12;
   static final PyCode f$13;
   static final PyCode f$14;
   static final PyCode f$15;
   static final PyCode cmp_to_key$16;
   static final PyCode K$17;
   static final PyCode __init__$18;
   static final PyCode __lt__$19;
   static final PyCode __gt__$20;
   static final PyCode __eq__$21;
   static final PyCode __le__$22;
   static final PyCode __ge__$23;
   static final PyCode __ne__$24;
   static final PyCode __hash__$25;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("functools.py - Tools for working with functions and callable objects\n"));
      var1.setline(2);
      PyString.fromInterned("functools.py - Tools for working with functions and callable objects\n");
      var1.setline(10);
      String[] var3 = new String[]{"partial", "reduce"};
      PyObject[] var5 = imp.importFrom("_functools", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("partial", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("reduce", var4);
      var4 = null;
      var1.setline(15);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("__module__"), PyString.fromInterned("__name__"), PyString.fromInterned("__doc__")});
      var1.setlocal("WRAPPER_ASSIGNMENTS", var6);
      var3 = null;
      var1.setline(16);
      var6 = new PyTuple(new PyObject[]{PyString.fromInterned("__dict__")});
      var1.setlocal("WRAPPER_UPDATES", var6);
      var3 = null;
      var1.setline(17);
      var5 = new PyObject[]{var1.getname("WRAPPER_ASSIGNMENTS"), var1.getname("WRAPPER_UPDATES")};
      PyFunction var7 = new PyFunction(var1.f_globals, var5, update_wrapper$1, PyString.fromInterned("Update a wrapper function to look like the wrapped function\n\n       wrapper is the function to be updated\n       wrapped is the original function\n       assigned is a tuple naming the attributes assigned directly\n       from the wrapped function to the wrapper function (defaults to\n       functools.WRAPPER_ASSIGNMENTS)\n       updated is a tuple naming the attributes of the wrapper that\n       are updated with the corresponding attribute from the wrapped\n       function (defaults to functools.WRAPPER_UPDATES)\n    "));
      var1.setlocal("update_wrapper", var7);
      var3 = null;
      var1.setline(39);
      var5 = new PyObject[]{var1.getname("WRAPPER_ASSIGNMENTS"), var1.getname("WRAPPER_UPDATES")};
      var7 = new PyFunction(var1.f_globals, var5, wraps$2, PyString.fromInterned("Decorator factory to apply update_wrapper() to a wrapper function\n\n       Returns a decorator that invokes update_wrapper() with the decorated\n       function as the wrapper argument and the arguments to wraps() as the\n       remaining arguments. Default arguments are as for update_wrapper().\n       This is a convenience function to simplify applying partial() to\n       update_wrapper().\n    "));
      var1.setlocal("wraps", var7);
      var3 = null;
      var1.setline(53);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, total_ordering$3, PyString.fromInterned("Class decorator that fills in missing ordering methods"));
      var1.setlocal("total_ordering", var7);
      var3 = null;
      var1.setline(80);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, cmp_to_key$16, PyString.fromInterned("Convert a cmp= function into a key= function"));
      var1.setlocal("cmp_to_key", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject update_wrapper$1(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("Update a wrapper function to look like the wrapped function\n\n       wrapper is the function to be updated\n       wrapped is the original function\n       assigned is a tuple naming the attributes assigned directly\n       from the wrapped function to the wrapper function (defaults to\n       functools.WRAPPER_ASSIGNMENTS)\n       updated is a tuple naming the attributes of the wrapper that\n       are updated with the corresponding attribute from the wrapped\n       function (defaults to functools.WRAPPER_UPDATES)\n    ");
      var1.setline(32);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(32);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(34);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(34);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(37);
                  var3 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var4);
               var1.setline(35);
               var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4)).__getattr__("update").__call__(var2, var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(4), (PyObject)(new PyDictionary(Py.EmptyObjects))));
            }
         }

         var1.setlocal(4, var4);
         var1.setline(33);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(4)));
      }
   }

   public PyObject wraps$2(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString.fromInterned("Decorator factory to apply update_wrapper() to a wrapper function\n\n       Returns a decorator that invokes update_wrapper() with the decorated\n       function as the wrapper argument and the arguments to wraps() as the\n       remaining arguments. Default arguments are as for update_wrapper().\n       This is a convenience function to simplify applying partial() to\n       update_wrapper().\n    ");
      var1.setline(50);
      PyObject var10000 = var1.getglobal("partial");
      PyObject[] var3 = new PyObject[]{var1.getglobal("update_wrapper"), var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)};
      String[] var4 = new String[]{"wrapped", "assigned", "updated"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject total_ordering$3(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("Class decorator that fills in missing ordering methods");
      var1.setline(55);
      PyObject[] var10002 = new PyObject[]{PyString.fromInterned("__lt__"), null, null, null, null, null, null, null};
      PyObject[] var10007 = new PyObject[3];
      PyObject[] var10012 = new PyObject[]{PyString.fromInterned("__gt__"), null};
      var1.setline(56);
      PyObject[] var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$4);
      var10007[0] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__le__"), null};
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$5);
      var10007[1] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__ge__"), null};
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$6);
      var10007[2] = new PyTuple(var10012);
      var10002[1] = new PyList(var10007);
      var10002[2] = PyString.fromInterned("__le__");
      var10007 = new PyObject[3];
      var10012 = new PyObject[]{PyString.fromInterned("__ge__"), null};
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$7);
      var10007[0] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__lt__"), null};
      var1.setline(60);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$8);
      var10007[1] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__gt__"), null};
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$9);
      var10007[2] = new PyTuple(var10012);
      var10002[3] = new PyList(var10007);
      var10002[4] = PyString.fromInterned("__gt__");
      var10007 = new PyObject[3];
      var10012 = new PyObject[]{PyString.fromInterned("__lt__"), null};
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$10);
      var10007[0] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__ge__"), null};
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$11);
      var10007[1] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__le__"), null};
      var1.setline(64);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$12);
      var10007[2] = new PyTuple(var10012);
      var10002[5] = new PyList(var10007);
      var10002[6] = PyString.fromInterned("__ge__");
      var10007 = new PyObject[3];
      var10012 = new PyObject[]{PyString.fromInterned("__le__"), null};
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$13);
      var10007[0] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__gt__"), null};
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$14);
      var10007[1] = new PyTuple(var10012);
      var10012 = new PyObject[]{PyString.fromInterned("__lt__"), null};
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var10012[1] = new PyFunction(var1.f_globals, var3, f$15);
      var10007[2] = new PyTuple(var10012);
      var10002[7] = new PyList(var10007);
      PyDictionary var8 = new PyDictionary(var10002);
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(69);
      PyObject var9 = var1.getglobal("set").__call__(var2, var1.getglobal("dir").__call__(var2, var1.getlocal(0)))._and(var1.getglobal("set").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(70);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(71);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("must define at least one ordering operation: < > <= >=")));
      } else {
         var1.setline(72);
         var9 = var1.getglobal("max").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var9);
         var3 = null;
         var1.setline(73);
         var9 = var1.getlocal(1).__getitem__(var1.getlocal(3)).__iter__();

         while(true) {
            var1.setline(73);
            PyObject var4 = var9.__iternext__();
            if (var4 == null) {
               var1.setline(78);
               var9 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var9;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(74);
            PyObject var7 = var1.getlocal(4);
            PyObject var10000 = var7._notin(var1.getlocal(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(75);
               var7 = var1.getlocal(4);
               var1.getlocal(5).__setattr__("__name__", var7);
               var5 = null;
               var1.setline(76);
               var7 = var1.getglobal("getattr").__call__(var2, var1.getglobal("int"), var1.getlocal(4)).__getattr__("__doc__");
               var1.getlocal(5).__setattr__("__doc__", var7);
               var5 = null;
               var1.setline(77);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(5));
            }
         }
      }
   }

   public PyObject f$4(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
      }

      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$6(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._lt(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._le(var1.getlocal(1));
      var3 = null;
      var10000 = var10000.__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._le(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         var10000 = var10000.__not__();
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$9(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._le(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$10(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._gt(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
      }

      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$11(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._gt(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$12(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._gt(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$13(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ge(var1.getlocal(1));
      var3 = null;
      var10000 = var10000.__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$14(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ge(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         var10000 = var10000.__not__();
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$15(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ge(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cmp_to_key$16(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(81);
      PyString.fromInterned("Convert a cmp= function into a key= function");
      var1.setline(82);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("K", var3, K$17);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(100);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject K$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(83);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("obj")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(84);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(86);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = __lt__$19;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(88);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = __gt__$20;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(90);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = __eq__$21;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(92);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = __le__$22;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("__le__", var5);
      var3 = null;
      var1.setline(94);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = __ge__$23;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("__ge__", var5);
      var3 = null;
      var1.setline(96);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = __ne__$24;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(98);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$25, (PyObject)null);
      var1.setlocal("__hash__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("obj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __lt__$19(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1).__getattr__("obj"));
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __gt__$20(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1).__getattr__("obj"));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$21(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1).__getattr__("obj"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __le__$22(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1).__getattr__("obj"));
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ge__$23(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1).__getattr__("obj"));
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ne__$24(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1).__getattr__("obj"));
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$25(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hash not implemented")));
   }

   public functools$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"wrapper", "wrapped", "assigned", "updated", "attr"};
      update_wrapper$1 = Py.newCode(4, var2, var1, "update_wrapper", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"wrapped", "assigned", "updated"};
      wraps$2 = Py.newCode(3, var2, var1, "wraps", 39, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "convert", "roots", "root", "opname", "opfunc"};
      total_ordering$3 = Py.newCode(1, var2, var1, "total_ordering", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$4 = Py.newCode(2, var2, var1, "<lambda>", 56, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$5 = Py.newCode(2, var2, var1, "<lambda>", 57, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$6 = Py.newCode(2, var2, var1, "<lambda>", 58, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$7 = Py.newCode(2, var2, var1, "<lambda>", 59, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$8 = Py.newCode(2, var2, var1, "<lambda>", 60, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$9 = Py.newCode(2, var2, var1, "<lambda>", 61, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$10 = Py.newCode(2, var2, var1, "<lambda>", 62, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$11 = Py.newCode(2, var2, var1, "<lambda>", 63, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$12 = Py.newCode(2, var2, var1, "<lambda>", 64, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$13 = Py.newCode(2, var2, var1, "<lambda>", 65, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$14 = Py.newCode(2, var2, var1, "<lambda>", 66, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      f$15 = Py.newCode(2, var2, var1, "<lambda>", 67, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mycmp", "K"};
      String[] var10001 = var2;
      functools$py var10007 = self;
      var2 = new String[]{"mycmp"};
      cmp_to_key$16 = Py.newCode(1, var10001, var1, "cmp_to_key", 80, false, false, var10007, 16, var2, (String[])null, 0, 4097);
      var2 = new String[0];
      K$17 = Py.newCode(0, var2, var1, "K", 82, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "obj", "args"};
      __init__$18 = Py.newCode(3, var2, var1, "__init__", 84, true, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mycmp"};
      __lt__$19 = Py.newCode(2, var10001, var1, "__lt__", 86, false, false, var10007, 19, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "other"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mycmp"};
      __gt__$20 = Py.newCode(2, var10001, var1, "__gt__", 88, false, false, var10007, 20, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "other"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mycmp"};
      __eq__$21 = Py.newCode(2, var10001, var1, "__eq__", 90, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "other"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mycmp"};
      __le__$22 = Py.newCode(2, var10001, var1, "__le__", 92, false, false, var10007, 22, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "other"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mycmp"};
      __ge__$23 = Py.newCode(2, var10001, var1, "__ge__", 94, false, false, var10007, 23, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "other"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mycmp"};
      __ne__$24 = Py.newCode(2, var10001, var1, "__ne__", 96, false, false, var10007, 24, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$25 = Py.newCode(1, var2, var1, "__hash__", 98, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new functools$py("functools$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(functools$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.update_wrapper$1(var2, var3);
         case 2:
            return this.wraps$2(var2, var3);
         case 3:
            return this.total_ordering$3(var2, var3);
         case 4:
            return this.f$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this.f$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.f$9(var2, var3);
         case 10:
            return this.f$10(var2, var3);
         case 11:
            return this.f$11(var2, var3);
         case 12:
            return this.f$12(var2, var3);
         case 13:
            return this.f$13(var2, var3);
         case 14:
            return this.f$14(var2, var3);
         case 15:
            return this.f$15(var2, var3);
         case 16:
            return this.cmp_to_key$16(var2, var3);
         case 17:
            return this.K$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.__lt__$19(var2, var3);
         case 20:
            return this.__gt__$20(var2, var3);
         case 21:
            return this.__eq__$21(var2, var3);
         case 22:
            return this.__le__$22(var2, var3);
         case 23:
            return this.__ge__$23(var2, var3);
         case 24:
            return this.__ne__$24(var2, var3);
         case 25:
            return this.__hash__$25(var2, var3);
         default:
            return null;
      }
   }
}
