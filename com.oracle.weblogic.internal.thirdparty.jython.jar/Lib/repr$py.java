import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
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
@Filename("repr.py")
public class repr$py extends PyFunctionTable implements PyRunnable {
   static repr$py self;
   static final PyCode f$0;
   static final PyCode Repr$1;
   static final PyCode __init__$2;
   static final PyCode repr$3;
   static final PyCode repr1$4;
   static final PyCode _repr_iterable$5;
   static final PyCode repr_tuple$6;
   static final PyCode repr_list$7;
   static final PyCode repr_array$8;
   static final PyCode repr_set$9;
   static final PyCode repr_frozenset$10;
   static final PyCode repr_deque$11;
   static final PyCode repr_dict$12;
   static final PyCode repr_str$13;
   static final PyCode repr_long$14;
   static final PyCode repr_instance$15;
   static final PyCode _possibly_sorted$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Redo the builtin repr() (representation) but with limits on most sizes."));
      var1.setline(1);
      PyString.fromInterned("Redo the builtin repr() (representation) but with limits on most sizes.");
      var1.setline(3);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Repr"), PyString.fromInterned("repr")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(5);
      PyObject var5 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var5);
      var3 = null;
      var1.setline(6);
      String[] var6 = new String[]{"islice"};
      PyObject[] var7 = imp.importFrom("itertools", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("islice", var4);
      var4 = null;
      var1.setline(8);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Repr", var7, Repr$1);
      var1.setlocal("Repr", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(122);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, _possibly_sorted$16, (PyObject)null);
      var1.setlocal("_possibly_sorted", var8);
      var3 = null;
      var1.setline(131);
      var5 = var1.getname("Repr").__call__(var2);
      var1.setlocal("aRepr", var5);
      var3 = null;
      var1.setline(132);
      var5 = var1.getname("aRepr").__getattr__("repr");
      var1.setlocal("repr", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Repr$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr$3, (PyObject)null);
      var1.setlocal("repr", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr1$4, (PyObject)null);
      var1.setlocal("repr1", var4);
      var3 = null;
      var1.setline(41);
      var3 = new PyObject[]{PyString.fromInterned("")};
      var4 = new PyFunction(var1.f_globals, var3, _repr_iterable$5, (PyObject)null);
      var1.setlocal("_repr_iterable", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_tuple$6, (PyObject)null);
      var1.setlocal("repr_tuple", var4);
      var3 = null;
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_list$7, (PyObject)null);
      var1.setlocal("repr_list", var4);
      var3 = null;
      var1.setline(60);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_array$8, (PyObject)null);
      var1.setlocal("repr_array", var4);
      var3 = null;
      var1.setline(64);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_set$9, (PyObject)null);
      var1.setlocal("repr_set", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_frozenset$10, (PyObject)null);
      var1.setlocal("repr_frozenset", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_deque$11, (PyObject)null);
      var1.setlocal("repr_deque", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_dict$12, (PyObject)null);
      var1.setlocal("repr_dict", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_str$13, (PyObject)null);
      var1.setlocal("repr_str", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_long$14, (PyObject)null);
      var1.setlocal("repr_long", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_instance$15, (PyObject)null);
      var1.setlocal("repr_instance", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(11);
      PyInteger var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"maxlevel", var3);
      var3 = null;
      var1.setline(12);
      var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"maxtuple", var3);
      var3 = null;
      var1.setline(13);
      var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"maxlist", var3);
      var3 = null;
      var1.setline(14);
      var3 = Py.newInteger(5);
      var1.getlocal(0).__setattr__((String)"maxarray", var3);
      var3 = null;
      var1.setline(15);
      var3 = Py.newInteger(4);
      var1.getlocal(0).__setattr__((String)"maxdict", var3);
      var3 = null;
      var1.setline(16);
      var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"maxset", var3);
      var3 = null;
      var1.setline(17);
      var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"maxfrozenset", var3);
      var3 = null;
      var1.setline(18);
      var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"maxdeque", var3);
      var3 = null;
      var1.setline(19);
      var3 = Py.newInteger(30);
      var1.getlocal(0).__setattr__((String)"maxstring", var3);
      var3 = null;
      var1.setline(20);
      var3 = Py.newInteger(40);
      var1.getlocal(0).__setattr__((String)"maxlong", var3);
      var3 = null;
      var1.setline(21);
      var3 = Py.newInteger(20);
      var1.getlocal(0).__setattr__((String)"maxother", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject repr$3(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getlocal(0).__getattr__("repr1").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("maxlevel"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr1$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(28);
      PyString var5 = PyString.fromInterned(" ");
      PyObject var10000 = var5._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(29);
         var3 = var1.getlocal(3).__getattr__("split").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(30);
         var3 = PyString.fromInterned("_").__getattr__("join").__call__(var2, var1.getlocal(4));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(31);
      if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("repr_")._add(var1.getlocal(3))).__nonzero__()) {
         var1.setline(32);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("repr_")._add(var1.getlocal(3))).__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(34);
         PyObject var4 = var1.getglobal("__builtin__").__getattr__("repr").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(35);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         var10000 = var4._gt(var1.getlocal(0).__getattr__("maxother"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(36);
            var4 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxother")._sub(Py.newInteger(3))._floordiv(Py.newInteger(2)));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(37);
            var4 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxother")._sub(Py.newInteger(3))._sub(var1.getlocal(6)));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(38);
            var4 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(5).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(5))._sub(var1.getlocal(7)), (PyObject)null, (PyObject)null));
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(39);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _repr_iterable$5(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(7);
      }

      if (var10000.__nonzero__()) {
         var1.setline(44);
         PyString var5 = PyString.fromInterned("...");
         var1.setlocal(8, var5);
         var3 = null;
      } else {
         var1.setline(46);
         var3 = var1.getlocal(2)._sub(Py.newInteger(1));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(47);
         var3 = var1.getlocal(0).__getattr__("repr1");
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(48);
         PyList var7 = new PyList();
         var3 = var7.__getattr__("append");
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(48);
         var3 = var1.getglobal("islice").__call__(var2, var1.getlocal(1), var1.getlocal(5)).__iter__();

         while(true) {
            var1.setline(48);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(48);
               var1.dellocal(12);
               PyList var6 = var7;
               var1.setlocal(11, var6);
               var3 = null;
               var1.setline(49);
               var3 = var1.getlocal(7);
               var10000 = var3._gt(var1.getlocal(5));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(49);
                  var1.getlocal(11).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("..."));
               }

               var1.setline(50);
               var3 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(11));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(51);
               var3 = var1.getlocal(7);
               var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(6);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(51);
                  var3 = var1.getlocal(6)._add(var1.getlocal(4));
                  var1.setlocal(4, var3);
                  var3 = null;
               }
               break;
            }

            var1.setlocal(13, var4);
            var1.setline(48);
            var1.getlocal(12).__call__(var2, var1.getlocal(10).__call__(var2, var1.getlocal(13), var1.getlocal(9)));
         }
      }

      var1.setline(52);
      var3 = PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(8), var1.getlocal(4)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_tuple$6(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var10000 = var1.getlocal(0).__getattr__("_repr_iterable");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), PyString.fromInterned("("), PyString.fromInterned(")"), var1.getlocal(0).__getattr__("maxtuple"), PyString.fromInterned(",")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject repr_list$7(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var10000 = var1.getlocal(0).__getattr__("_repr_iterable");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), PyString.fromInterned("["), PyString.fromInterned("]"), var1.getlocal(0).__getattr__("maxlist")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject repr_array$8(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = PyString.fromInterned("array('%s', [")._mod(var1.getlocal(1).__getattr__("typecode"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(62);
      PyObject var10000 = var1.getlocal(0).__getattr__("_repr_iterable");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), PyString.fromInterned("])"), var1.getlocal(0).__getattr__("maxarray")};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_set$9(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getglobal("_possibly_sorted").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(66);
      PyObject var10000 = var1.getlocal(0).__getattr__("_repr_iterable");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), PyString.fromInterned("set(["), PyString.fromInterned("])"), var1.getlocal(0).__getattr__("maxset")};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_frozenset$10(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getglobal("_possibly_sorted").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(70);
      PyObject var10000 = var1.getlocal(0).__getattr__("_repr_iterable");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), PyString.fromInterned("frozenset(["), PyString.fromInterned("])"), var1.getlocal(0).__getattr__("maxfrozenset")};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_deque$11(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var10000 = var1.getlocal(0).__getattr__("_repr_iterable");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), PyString.fromInterned("deque(["), PyString.fromInterned("])"), var1.getlocal(0).__getattr__("maxdeque")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject repr_dict$12(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyString var7;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var7 = PyString.fromInterned("{}");
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(79);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._le(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(79);
            var7 = PyString.fromInterned("{...}");
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(80);
            var4 = var1.getlocal(2)._sub(Py.newInteger(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(81);
            var4 = var1.getlocal(0).__getattr__("repr1");
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(82);
            PyList var8 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var8);
            var4 = null;
            var1.setline(83);
            var4 = var1.getglobal("islice").__call__(var2, var1.getglobal("_possibly_sorted").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("maxdict")).__iter__();

            while(true) {
               var1.setline(83);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(87);
                  var4 = var1.getlocal(3);
                  var10000 = var4._gt(var1.getlocal(0).__getattr__("maxdict"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(87);
                     var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("..."));
                  }

                  var1.setline(88);
                  var4 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(6));
                  var1.setlocal(10, var4);
                  var4 = null;
                  var1.setline(89);
                  var3 = PyString.fromInterned("{%s}")._mod(new PyTuple(new PyObject[]{var1.getlocal(10)}));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(7, var5);
               var1.setline(84);
               PyObject var6 = var1.getlocal(5).__call__(var2, var1.getlocal(7), var1.getlocal(4));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(85);
               var6 = var1.getlocal(5).__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(7)), var1.getlocal(4));
               var1.setlocal(9, var6);
               var6 = null;
               var1.setline(86);
               var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)})));
            }
         }
      }
   }

   public PyObject repr_str$13(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getglobal("__builtin__").__getattr__("repr").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(0).__getattr__("maxstring"), (PyObject)null));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._gt(var1.getlocal(0).__getattr__("maxstring"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxstring")._sub(Py.newInteger(3))._floordiv(Py.newInteger(2)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(95);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxstring")._sub(Py.newInteger(3))._sub(var1.getlocal(4)));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(96);
         var3 = var1.getglobal("__builtin__").__getattr__("repr").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null)._add(var1.getlocal(1).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(var1.getlocal(5)), (PyObject)null, (PyObject)null)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(97);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(3).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(var1.getlocal(5)), (PyObject)null, (PyObject)null));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(98);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_long$14(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getglobal("__builtin__").__getattr__("repr").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(102);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._gt(var1.getlocal(0).__getattr__("maxlong"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxlong")._sub(Py.newInteger(3))._floordiv(Py.newInteger(2)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(104);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxlong")._sub(Py.newInteger(3))._sub(var1.getlocal(4)));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(105);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(3).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(var1.getlocal(5)), (PyObject)null, (PyObject)null));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(106);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_instance$15(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(110);
         var6 = var1.getglobal("__builtin__").__getattr__("repr").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("Exception"))) {
            var1.setline(114);
            var4 = PyString.fromInterned("<%s instance at %x>")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("id").__call__(var2, var1.getlocal(1))}));
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(115);
      var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var6._gt(var1.getlocal(0).__getattr__("maxstring"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var6 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxstring")._sub(Py.newInteger(3))._floordiv(Py.newInteger(2)));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(117);
         var6 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("maxstring")._sub(Py.newInteger(3))._sub(var1.getlocal(4)));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(118);
         var6 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(3).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(var1.getlocal(5)), (PyObject)null, (PyObject)null));
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(119);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _possibly_sorted$16(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(127);
         var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("Exception"))) {
            var1.setline(129);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public repr$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Repr$1 = Py.newCode(0, var2, var1, "Repr", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 10, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      repr$3 = Py.newCode(2, var2, var1, "repr", 23, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "typename", "parts", "s", "i", "j"};
      repr1$4 = Py.newCode(3, var2, var1, "repr1", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "left", "right", "maxiter", "trail", "n", "s", "newlevel", "repr1", "pieces", "_[48_22]", "elem"};
      _repr_iterable$5 = Py.newCode(7, var2, var1, "_repr_iterable", 41, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_tuple$6 = Py.newCode(3, var2, var1, "repr_tuple", 54, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_list$7 = Py.newCode(3, var2, var1, "repr_list", 57, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "header"};
      repr_array$8 = Py.newCode(3, var2, var1, "repr_array", 60, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_set$9 = Py.newCode(3, var2, var1, "repr_set", 64, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_frozenset$10 = Py.newCode(3, var2, var1, "repr_frozenset", 68, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_deque$11 = Py.newCode(3, var2, var1, "repr_deque", 73, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "n", "newlevel", "repr1", "pieces", "key", "keyrepr", "valrepr", "s"};
      repr_dict$12 = Py.newCode(3, var2, var1, "repr_dict", 76, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "s", "i", "j"};
      repr_str$13 = Py.newCode(3, var2, var1, "repr_str", 91, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "s", "i", "j"};
      repr_long$14 = Py.newCode(3, var2, var1, "repr_long", 100, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "s", "i", "j"};
      repr_instance$15 = Py.newCode(3, var2, var1, "repr_instance", 108, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      _possibly_sorted$16 = Py.newCode(1, var2, var1, "_possibly_sorted", 122, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new repr$py("repr$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(repr$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Repr$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.repr$3(var2, var3);
         case 4:
            return this.repr1$4(var2, var3);
         case 5:
            return this._repr_iterable$5(var2, var3);
         case 6:
            return this.repr_tuple$6(var2, var3);
         case 7:
            return this.repr_list$7(var2, var3);
         case 8:
            return this.repr_array$8(var2, var3);
         case 9:
            return this.repr_set$9(var2, var3);
         case 10:
            return this.repr_frozenset$10(var2, var3);
         case 11:
            return this.repr_deque$11(var2, var3);
         case 12:
            return this.repr_dict$12(var2, var3);
         case 13:
            return this.repr_str$13(var2, var3);
         case 14:
            return this.repr_long$14(var2, var3);
         case 15:
            return this.repr_instance$15(var2, var3);
         case 16:
            return this._possibly_sorted$16(var2, var3);
         default:
            return null;
      }
   }
}
