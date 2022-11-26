import java.util.Arrays;
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
@Filename("UserDict.py")
public class UserDict$py extends PyFunctionTable implements PyRunnable {
   static UserDict$py self;
   static final PyCode f$0;
   static final PyCode UserDict$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode __cmp__$4;
   static final PyCode __len__$5;
   static final PyCode __getitem__$6;
   static final PyCode __setitem__$7;
   static final PyCode __delitem__$8;
   static final PyCode clear$9;
   static final PyCode copy$10;
   static final PyCode keys$11;
   static final PyCode items$12;
   static final PyCode iteritems$13;
   static final PyCode iterkeys$14;
   static final PyCode itervalues$15;
   static final PyCode values$16;
   static final PyCode has_key$17;
   static final PyCode update$18;
   static final PyCode get$19;
   static final PyCode setdefault$20;
   static final PyCode pop$21;
   static final PyCode popitem$22;
   static final PyCode __contains__$23;
   static final PyCode fromkeys$24;
   static final PyCode IterableUserDict$25;
   static final PyCode __iter__$26;
   static final PyCode DictMixin$27;
   static final PyCode __iter__$28;
   static final PyCode has_key$29;
   static final PyCode __contains__$30;
   static final PyCode iteritems$31;
   static final PyCode iterkeys$32;
   static final PyCode itervalues$33;
   static final PyCode values$34;
   static final PyCode items$35;
   static final PyCode clear$36;
   static final PyCode setdefault$37;
   static final PyCode pop$38;
   static final PyCode popitem$39;
   static final PyCode update$40;
   static final PyCode get$41;
   static final PyCode __repr__$42;
   static final PyCode __cmp__$43;
   static final PyCode __len__$44;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A more or less complete user-defined wrapper around dictionary objects."));
      var1.setline(1);
      PyString.fromInterned("A more or less complete user-defined wrapper around dictionary objects.");
      var1.setline(3);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("UserDict", var3, UserDict$1);
      var1.setlocal("UserDict", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(79);
      var3 = new PyObject[]{var1.getname("UserDict")};
      var4 = Py.makeClass("IterableUserDict", var3, IterableUserDict$25);
      var1.setlocal("IterableUserDict", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(83);
      PyObject var5 = imp.importOne("_abcoll", var1, -1);
      var1.setlocal("_abcoll", var5);
      var3 = null;
      var1.setline(84);
      var1.getname("_abcoll").__getattr__("MutableMapping").__getattr__("register").__call__(var2, var1.getname("IterableUserDict"));
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = Py.makeClass("DictMixin", var3, DictMixin$27);
      var1.setlocal("DictMixin", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UserDict$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(4);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(10);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(11);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$4, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(16);
      PyObject var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(17);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$5, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$6, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$7, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(25);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$8, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$9, (PyObject)null);
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$10, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$11, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$12, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iteritems$13, (PyObject)null);
      var1.setlocal("iteritems", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$14, (PyObject)null);
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(43);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itervalues$15, (PyObject)null);
      var1.setlocal("itervalues", var4);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$16, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$17, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(46);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, update$18, (PyObject)null);
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(58);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$19, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(62);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, setdefault$20, (PyObject)null);
      var1.setlocal("setdefault", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop$21, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, popitem$22, (PyObject)null);
      var1.setlocal("popitem", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$23, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(72);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, fromkeys$24, (PyObject)null);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("fromkeys", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"data", var3);
      var3 = null;
      var1.setline(6);
      PyObject var4 = var1.getlocal(1);
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(7);
         var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(1));
      }

      var1.setline(8);
      if (var1.getglobal("len").__call__(var2, var1.getlocal(2)).__nonzero__()) {
         var1.setline(9);
         var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$4(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserDict")).__nonzero__()) {
         var1.setline(13);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1).__getattr__("data"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(15);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __len__$5(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$6(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         var3 = var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(21);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("__class__"), (PyObject)PyString.fromInterned("__missing__")).__nonzero__()) {
            var1.setline(22);
            var3 = var1.getlocal(0).__getattr__("__class__").__getattr__("__missing__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(23);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
         }
      }
   }

   public PyObject __setitem__$7(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("data").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$8(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      var1.getlocal(0).__getattr__("data").__delitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear$9(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      var1.getlocal(0).__getattr__("data").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$10(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._is(var1.getglobal("UserDict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(29);
         var3 = var1.getglobal("UserDict").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("copy").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(30);
         PyObject var4 = imp.importOne("copy", var1, -1);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(31);
         var4 = var1.getlocal(0).__getattr__("data");
         var1.setlocal(2, var4);
         var4 = null;
         var4 = null;

         PyObject var5;
         try {
            var1.setline(33);
            PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"data", var7);
            var5 = null;
            var1.setline(34);
            var5 = var1.getlocal(1).__getattr__("copy").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var5);
            var5 = null;
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(36);
            var5 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("data", var5);
            var5 = null;
            throw (Throwable)var6;
         }

         var1.setline(36);
         var5 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("data", var5);
         var5 = null;
         var1.setline(37);
         var1.getlocal(3).__getattr__("update").__call__(var2, var1.getlocal(0));
         var1.setline(38);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject keys$11(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject items$12(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iteritems$13(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("iteritems").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterkeys$14(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("iterkeys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject itervalues$15(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("itervalues").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$16(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$17(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject update$18(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(48);
      } else {
         var1.setline(49);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserDict")).__nonzero__()) {
            var1.setline(50);
            var1.getlocal(0).__getattr__("data").__getattr__("update").__call__(var2, var1.getlocal(1).__getattr__("data"));
         } else {
            var1.setline(51);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects))));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("items")).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(52);
               var1.getlocal(0).__getattr__("data").__getattr__("update").__call__(var2, var1.getlocal(1));
            } else {
               var1.setline(54);
               var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(54);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var1.setline(55);
                  PyObject var7 = var1.getlocal(4);
                  var1.getlocal(0).__setitem__(var1.getlocal(3), var7);
                  var5 = null;
               }
            }
         }
      }

      var1.setline(56);
      if (var1.getglobal("len").__call__(var2, var1.getlocal(2)).__nonzero__()) {
         var1.setline(57);
         var1.getlocal(0).__getattr__("data").__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get$19(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(60);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(61);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setdefault$20(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(64);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(65);
      var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pop$21(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var10000 = var1.getlocal(0).__getattr__("data").__getattr__("pop");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject popitem$22(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("popitem").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$23(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fromkeys$24(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getlocal(0).__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(75);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(77);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(76);
         PyObject var5 = var1.getlocal(2);
         var1.getlocal(3).__setitem__(var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject IterableUserDict$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(80);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __iter__$26, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __iter__$26(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DictMixin$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(96);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __iter__$28, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$29, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$30, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iteritems$31, (PyObject)null);
      var1.setlocal("iteritems", var4);
      var3 = null;
      var1.setline(112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$32, (PyObject)null);
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(116);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itervalues$33, (PyObject)null);
      var1.setlocal("itervalues", var4);
      var3 = null;
      var1.setline(119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$34, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$35, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$36, (PyObject)null);
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(126);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, setdefault$37, (PyObject)null);
      var1.setlocal("setdefault", var4);
      var3 = null;
      var1.setline(132);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop$38, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, popitem$39, (PyObject)null);
      var1.setlocal("popitem", var4);
      var3 = null;
      var1.setline(151);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, update$40, (PyObject)null);
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(166);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$41, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$42, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$43, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$44, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __iter__$28(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(97);
            var3 = var1.getlocal(0).__getattr__("keys").__call__(var2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(97);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(98);
         var1.setline(98);
         var6 = var1.getlocal(1);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject has_key$29(PyFrame var1, ThreadState var2) {
      PyObject var4;
      try {
         var1.setline(101);
         var1.getlocal(0).__getitem__(var1.getlocal(1));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(103);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(104);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __contains__$30(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getlocal(0).__getattr__("has_key").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iteritems$31(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(110);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var7 = (PyObject)var10000;
      }

      var1.setline(110);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(111);
         var1.setline(111);
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getitem__(var1.getlocal(1))};
         PyTuple var8 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject iterkeys$32(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = var1.getlocal(0).__getattr__("__iter__").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject itervalues$33(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(117);
            var3 = var1.getlocal(0).__getattr__("iteritems").__call__(var2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      var1.setline(117);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(118);
         var1.setline(118);
         var8 = var1.getlocal(2);
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject values$34(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(0).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(120);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(120);
            var1.dellocal(1);
            PyList var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(120);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject items$35(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iteritems").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject clear$36(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getlocal(0).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(124);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(125);
         var1.getlocal(0).__delitem__(var1.getlocal(1));
      }
   }

   public PyObject setdefault$37(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(128);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(130);
            PyObject var5 = var1.getlocal(2);
            var1.getlocal(0).__setitem__(var1.getlocal(1), var5);
            var5 = null;
            var1.setline(131);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject pop$38(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(134);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("pop expected at most 2 arguments, got ")._add(var1.getglobal("repr").__call__(var2, Py.newInteger(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(2))))));
      } else {
         PyObject var4;
         try {
            var1.setline(137);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("KeyError"))) {
               var1.setline(139);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(140);
                  var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var1.f_lasti = -1;
                  return var4;
               }

               var1.setline(141);
               throw Py.makeException();
            }

            throw var6;
         }

         var1.setline(142);
         var1.getlocal(0).__delitem__(var1.getlocal(1));
         var1.setline(143);
         var4 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject popitem$39(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(146);
         PyObject var7 = var1.getlocal(0).__getattr__("iteritems").__call__(var2).__getattr__("next").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("StopIteration"))) {
            var1.setline(148);
            throw Py.makeException(var1.getglobal("KeyError"), PyString.fromInterned("container is empty"));
         }

         throw var3;
      }

      var1.setline(149);
      var1.getlocal(0).__delitem__(var1.getlocal(1));
      var1.setline(150);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject update$40(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(154);
      } else {
         var1.setline(155);
         PyObject var4;
         PyObject[] var5;
         PyObject var6;
         PyObject var7;
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("iteritems")).__nonzero__()) {
            var1.setline(156);
            var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(156);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(157);
               var7 = var1.getlocal(4);
               var1.getlocal(0).__setitem__(var1.getlocal(3), var7);
               var5 = null;
            }
         } else {
            var1.setline(158);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("keys")).__nonzero__()) {
               var1.setline(159);
               var3 = var1.getlocal(1).__getattr__("keys").__call__(var2).__iter__();

               while(true) {
                  var1.setline(159);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(3, var4);
                  var1.setline(160);
                  var7 = var1.getlocal(1).__getitem__(var1.getlocal(3));
                  var1.getlocal(0).__setitem__(var1.getlocal(3), var7);
                  var5 = null;
               }
            } else {
               var1.setline(162);
               var3 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(162);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var1.setline(163);
                  var7 = var1.getlocal(4);
                  var1.getlocal(0).__setitem__(var1.getlocal(3), var7);
                  var5 = null;
               }
            }
         }
      }

      var1.setline(164);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(165);
         var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get$41(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(168);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(170);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __repr__$42(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getglobal("dict").__call__(var2, var1.getlocal(0).__getattr__("iteritems").__call__(var2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$43(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(175);
         PyInteger var5 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(176);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("DictMixin")).__nonzero__()) {
            var1.setline(177);
            PyObject var4 = var1.getglobal("dict").__call__(var2, var1.getlocal(1).__getattr__("iteritems").__call__(var2));
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(178);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getglobal("dict").__call__(var2, var1.getlocal(0).__getattr__("iteritems").__call__(var2)), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __len__$44(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public UserDict$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UserDict$1 = Py.newCode(0, var2, var1, "UserDict", 3, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dict", "kwargs"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 4, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 10, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict"};
      __cmp__$4 = Py.newCode(2, var2, var1, "__cmp__", 11, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$5 = Py.newCode(1, var2, var1, "__len__", 17, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __getitem__$6 = Py.newCode(2, var2, var1, "__getitem__", 18, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "item"};
      __setitem__$7 = Py.newCode(3, var2, var1, "__setitem__", 24, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __delitem__$8 = Py.newCode(2, var2, var1, "__delitem__", 25, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$9 = Py.newCode(1, var2, var1, "clear", 26, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "copy", "data", "c"};
      copy$10 = Py.newCode(1, var2, var1, "copy", 27, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$11 = Py.newCode(1, var2, var1, "keys", 39, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$12 = Py.newCode(1, var2, var1, "items", 40, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      iteritems$13 = Py.newCode(1, var2, var1, "iteritems", 41, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      iterkeys$14 = Py.newCode(1, var2, var1, "iterkeys", 42, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      itervalues$15 = Py.newCode(1, var2, var1, "itervalues", 43, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$16 = Py.newCode(1, var2, var1, "values", 44, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$17 = Py.newCode(2, var2, var1, "has_key", 45, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "kwargs", "k", "v"};
      update$18 = Py.newCode(3, var2, var1, "update", 46, false, true, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "failobj"};
      get$19 = Py.newCode(3, var2, var1, "get", 58, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "failobj"};
      setdefault$20 = Py.newCode(3, var2, var1, "setdefault", 62, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "args"};
      pop$21 = Py.newCode(3, var2, var1, "pop", 66, true, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      popitem$22 = Py.newCode(1, var2, var1, "popitem", 68, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$23 = Py.newCode(2, var2, var1, "__contains__", 70, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "iterable", "value", "d", "key"};
      fromkeys$24 = Py.newCode(3, var2, var1, "fromkeys", 72, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IterableUserDict$25 = Py.newCode(0, var2, var1, "IterableUserDict", 79, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __iter__$26 = Py.newCode(1, var2, var1, "__iter__", 80, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DictMixin$27 = Py.newCode(0, var2, var1, "DictMixin", 87, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "k"};
      __iter__$28 = Py.newCode(1, var2, var1, "__iter__", 96, false, false, self, 28, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key"};
      has_key$29 = Py.newCode(2, var2, var1, "has_key", 99, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$30 = Py.newCode(2, var2, var1, "__contains__", 105, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k"};
      iteritems$31 = Py.newCode(1, var2, var1, "iteritems", 109, false, false, self, 31, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      iterkeys$32 = Py.newCode(1, var2, var1, "iterkeys", 112, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_", "v"};
      itervalues$33 = Py.newCode(1, var2, var1, "itervalues", 116, false, false, self, 33, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "_[120_16]", "v", "_"};
      values$34 = Py.newCode(1, var2, var1, "values", 119, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$35 = Py.newCode(1, var2, var1, "items", 121, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      clear$36 = Py.newCode(1, var2, var1, "clear", 123, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      setdefault$37 = Py.newCode(3, var2, var1, "setdefault", 126, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "args", "value"};
      pop$38 = Py.newCode(3, var2, var1, "pop", 132, true, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k", "v"};
      popitem$39 = Py.newCode(1, var2, var1, "popitem", 144, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "kwargs", "k", "v"};
      update$40 = Py.newCode(3, var2, var1, "update", 151, false, true, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      get$41 = Py.newCode(3, var2, var1, "get", 166, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$42 = Py.newCode(1, var2, var1, "__repr__", 171, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$43 = Py.newCode(2, var2, var1, "__cmp__", 173, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$44 = Py.newCode(1, var2, var1, "__len__", 179, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new UserDict$py("UserDict$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(UserDict$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UserDict$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.__cmp__$4(var2, var3);
         case 5:
            return this.__len__$5(var2, var3);
         case 6:
            return this.__getitem__$6(var2, var3);
         case 7:
            return this.__setitem__$7(var2, var3);
         case 8:
            return this.__delitem__$8(var2, var3);
         case 9:
            return this.clear$9(var2, var3);
         case 10:
            return this.copy$10(var2, var3);
         case 11:
            return this.keys$11(var2, var3);
         case 12:
            return this.items$12(var2, var3);
         case 13:
            return this.iteritems$13(var2, var3);
         case 14:
            return this.iterkeys$14(var2, var3);
         case 15:
            return this.itervalues$15(var2, var3);
         case 16:
            return this.values$16(var2, var3);
         case 17:
            return this.has_key$17(var2, var3);
         case 18:
            return this.update$18(var2, var3);
         case 19:
            return this.get$19(var2, var3);
         case 20:
            return this.setdefault$20(var2, var3);
         case 21:
            return this.pop$21(var2, var3);
         case 22:
            return this.popitem$22(var2, var3);
         case 23:
            return this.__contains__$23(var2, var3);
         case 24:
            return this.fromkeys$24(var2, var3);
         case 25:
            return this.IterableUserDict$25(var2, var3);
         case 26:
            return this.__iter__$26(var2, var3);
         case 27:
            return this.DictMixin$27(var2, var3);
         case 28:
            return this.__iter__$28(var2, var3);
         case 29:
            return this.has_key$29(var2, var3);
         case 30:
            return this.__contains__$30(var2, var3);
         case 31:
            return this.iteritems$31(var2, var3);
         case 32:
            return this.iterkeys$32(var2, var3);
         case 33:
            return this.itervalues$33(var2, var3);
         case 34:
            return this.values$34(var2, var3);
         case 35:
            return this.items$35(var2, var3);
         case 36:
            return this.clear$36(var2, var3);
         case 37:
            return this.setdefault$37(var2, var3);
         case 38:
            return this.pop$38(var2, var3);
         case 39:
            return this.popitem$39(var2, var3);
         case 40:
            return this.update$40(var2, var3);
         case 41:
            return this.get$41(var2, var3);
         case 42:
            return this.__repr__$42(var2, var3);
         case 43:
            return this.__cmp__$43(var2, var3);
         case 44:
            return this.__len__$44(var2, var3);
         default:
            return null;
      }
   }
}
