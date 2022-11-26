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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("UserList.py")
public class UserList$py extends PyFunctionTable implements PyRunnable {
   static UserList$py self;
   static final PyCode f$0;
   static final PyCode UserList$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode __lt__$4;
   static final PyCode __le__$5;
   static final PyCode __eq__$6;
   static final PyCode __ne__$7;
   static final PyCode __gt__$8;
   static final PyCode __ge__$9;
   static final PyCode _UserList__cast$10;
   static final PyCode __cmp__$11;
   static final PyCode __contains__$12;
   static final PyCode __len__$13;
   static final PyCode __getitem__$14;
   static final PyCode __setitem__$15;
   static final PyCode __delitem__$16;
   static final PyCode __getslice__$17;
   static final PyCode __setslice__$18;
   static final PyCode __delslice__$19;
   static final PyCode __add__$20;
   static final PyCode __radd__$21;
   static final PyCode __iadd__$22;
   static final PyCode __mul__$23;
   static final PyCode __imul__$24;
   static final PyCode append$25;
   static final PyCode insert$26;
   static final PyCode pop$27;
   static final PyCode remove$28;
   static final PyCode count$29;
   static final PyCode index$30;
   static final PyCode reverse$31;
   static final PyCode sort$32;
   static final PyCode extend$33;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A more or less complete user-defined wrapper around list objects."));
      var1.setline(1);
      PyString.fromInterned("A more or less complete user-defined wrapper around list objects.");
      var1.setline(3);
      PyObject var3 = imp.importOne("collections", var1, -1);
      var1.setlocal("collections", var3);
      var3 = null;
      var1.setline(5);
      PyObject[] var5 = new PyObject[]{var1.getname("collections").__getattr__("MutableSequence")};
      PyObject var4 = Py.makeClass("UserList", var5, UserList$1);
      var1.setlocal("UserList", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UserList$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(6);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(16);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(17);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __lt__$4, (PyObject)null);
      var1.setlocal("__lt__", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __le__$5, (PyObject)null);
      var1.setlocal("__le__", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$6, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(20);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$7, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(21);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __gt__$8, (PyObject)null);
      var1.setlocal("__gt__", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ge__$9, (PyObject)null);
      var1.setlocal("__ge__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _UserList__cast$10, (PyObject)null);
      var1.setlocal("_UserList__cast", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$11, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(28);
      PyObject var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$12, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$13, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$14, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(32);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$15, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$16, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getslice__$17, (PyObject)null);
      var1.setlocal("__getslice__", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setslice__$18, (PyObject)null);
      var1.setlocal("__setslice__", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delslice__$19, (PyObject)null);
      var1.setlocal("__delslice__", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __add__$20, (PyObject)null);
      var1.setlocal("__add__", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __radd__$21, (PyObject)null);
      var1.setlocal("__radd__", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iadd__$22, (PyObject)null);
      var1.setlocal("__iadd__", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __mul__$23, (PyObject)null);
      var1.setlocal("__mul__", var4);
      var3 = null;
      var1.setline(72);
      var5 = var1.getname("__mul__");
      var1.setlocal("__rmul__", var5);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __imul__$24, (PyObject)null);
      var1.setlocal("__imul__", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$25, (PyObject)null);
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(77);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, insert$26, (PyObject)null);
      var1.setlocal("insert", var4);
      var3 = null;
      var1.setline(78);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, pop$27, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$28, (PyObject)null);
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, count$29, (PyObject)null);
      var1.setlocal("count", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, index$30, (PyObject)null);
      var1.setlocal("index", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reverse$31, (PyObject)null);
      var1.setlocal("reverse", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sort$32, (PyObject)null);
      var1.setlocal("sort", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, extend$33, (PyObject)null);
      var1.setlocal("extend", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"data", var3);
      var3 = null;
      var1.setline(8);
      PyObject var4 = var1.getlocal(1);
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(10);
         var4 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var4._eq(var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(11);
            var4 = var1.getlocal(1);
            var1.getlocal(0).__getattr__("data").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var4);
            var3 = null;
         } else {
            var1.setline(12);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserList")).__nonzero__()) {
               var1.setline(13);
               var4 = var1.getlocal(1).__getattr__("data").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.getlocal(0).__getattr__("data").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var4);
               var3 = null;
            } else {
               var1.setline(15);
               var4 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
               var1.getlocal(0).__setattr__("data", var4);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __lt__$4(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._lt(var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __le__$5(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._le(var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$6(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ne__$7(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __gt__$8(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._gt(var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ge__$9(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      PyObject var10000 = var3._ge(var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _UserList__cast$10(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserList")).__nonzero__()) {
         var1.setline(24);
         var3 = var1.getlocal(1).__getattr__("data");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(25);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __cmp__$11(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(0).__getattr__("_UserList__cast").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$12(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$13(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$14(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setitem__$15(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("data").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$16(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      var1.getlocal(0).__getattr__("data").__delitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getslice__$17(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setslice__$18(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(39);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("UserList")).__nonzero__()) {
         var1.setline(40);
         var3 = var1.getlocal(3).__getattr__("data");
         var1.getlocal(0).__getattr__("data").__setslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null, var3);
         var3 = null;
      } else {
         var1.setline(41);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("data"))).__nonzero__()) {
            var1.setline(42);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__getattr__("data").__setslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null, var3);
            var3 = null;
         } else {
            var1.setline(44);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
            var1.getlocal(0).__getattr__("data").__setslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null, var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delslice__$19(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(47);
      var1.getlocal(0).__getattr__("data").__delslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __add__$20(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserList")).__nonzero__()) {
         var1.setline(50);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1).__getattr__("data")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(51);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("data"))).__nonzero__()) {
            var1.setline(52);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(54);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._add(var1.getglobal("list").__call__(var2, var1.getlocal(1))));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __radd__$21(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserList")).__nonzero__()) {
         var1.setline(57);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(1).__getattr__("data")._add(var1.getlocal(0).__getattr__("data")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(58);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("data"))).__nonzero__()) {
            var1.setline(59);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(1)._add(var1.getlocal(0).__getattr__("data")));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(61);
            var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(1))._add(var1.getlocal(0).__getattr__("data")));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __iadd__$22(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var10000;
      String var3;
      PyObject var4;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserList")).__nonzero__()) {
         var1.setline(64);
         var10000 = var1.getlocal(0);
         var3 = "data";
         var4 = var10000;
         var5 = var4.__getattr__(var3);
         var5 = var5._iadd(var1.getlocal(1).__getattr__("data"));
         var4.__setattr__(var3, var5);
      } else {
         var1.setline(65);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("data"))).__nonzero__()) {
            var1.setline(66);
            var10000 = var1.getlocal(0);
            var3 = "data";
            var4 = var10000;
            var5 = var4.__getattr__(var3);
            var5 = var5._iadd(var1.getlocal(1));
            var4.__setattr__(var3, var5);
         } else {
            var1.setline(68);
            var10000 = var1.getlocal(0);
            var3 = "data";
            var4 = var10000;
            var5 = var4.__getattr__(var3);
            var5 = var5._iadd(var1.getglobal("list").__call__(var2, var1.getlocal(1)));
            var4.__setattr__(var3, var5);
         }
      }

      var1.setline(69);
      PyObject var6 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject __mul__$23(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("data")._mul(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __imul__$24(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "data";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._imul(var1.getlocal(1));
      var4.__setattr__(var3, var5);
      var1.setline(75);
      PyObject var6 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject append$25(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      var1.getlocal(0).__getattr__("data").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject insert$26(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      var1.getlocal(0).__getattr__("data").__getattr__("insert").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop$27(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("pop").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$28(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      var1.getlocal(0).__getattr__("data").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject count$29(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("count").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject index$30(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var10000 = var1.getlocal(0).__getattr__("data").__getattr__("index");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject reverse$31(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      var1.getlocal(0).__getattr__("data").__getattr__("reverse").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sort$32(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyObject var10000 = var1.getlocal(0).__getattr__("data").__getattr__("sort");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject extend$33(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UserList")).__nonzero__()) {
         var1.setline(86);
         var1.getlocal(0).__getattr__("data").__getattr__("extend").__call__(var2, var1.getlocal(1).__getattr__("data"));
      } else {
         var1.setline(88);
         var1.getlocal(0).__getattr__("data").__getattr__("extend").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public UserList$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UserList$1 = Py.newCode(0, var2, var1, "UserList", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "initlist"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 6, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 16, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __lt__$4 = Py.newCode(2, var2, var1, "__lt__", 17, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __le__$5 = Py.newCode(2, var2, var1, "__le__", 18, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$6 = Py.newCode(2, var2, var1, "__eq__", 19, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$7 = Py.newCode(2, var2, var1, "__ne__", 20, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __gt__$8 = Py.newCode(2, var2, var1, "__gt__", 21, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ge__$9 = Py.newCode(2, var2, var1, "__ge__", 22, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      _UserList__cast$10 = Py.newCode(2, var2, var1, "_UserList__cast", 23, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$11 = Py.newCode(2, var2, var1, "__cmp__", 26, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      __contains__$12 = Py.newCode(2, var2, var1, "__contains__", 29, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$13 = Py.newCode(1, var2, var1, "__len__", 30, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      __getitem__$14 = Py.newCode(2, var2, var1, "__getitem__", 31, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "item"};
      __setitem__$15 = Py.newCode(3, var2, var1, "__setitem__", 32, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      __delitem__$16 = Py.newCode(2, var2, var1, "__delitem__", 33, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j"};
      __getslice__$17 = Py.newCode(3, var2, var1, "__getslice__", 34, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "other"};
      __setslice__$18 = Py.newCode(4, var2, var1, "__setslice__", 37, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j"};
      __delslice__$19 = Py.newCode(3, var2, var1, "__delslice__", 45, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __add__$20 = Py.newCode(2, var2, var1, "__add__", 48, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __radd__$21 = Py.newCode(2, var2, var1, "__radd__", 55, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __iadd__$22 = Py.newCode(2, var2, var1, "__iadd__", 62, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      __mul__$23 = Py.newCode(2, var2, var1, "__mul__", 70, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      __imul__$24 = Py.newCode(2, var2, var1, "__imul__", 73, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      append$25 = Py.newCode(2, var2, var1, "append", 76, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "item"};
      insert$26 = Py.newCode(3, var2, var1, "insert", 77, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      pop$27 = Py.newCode(2, var2, var1, "pop", 78, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      remove$28 = Py.newCode(2, var2, var1, "remove", 79, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      count$29 = Py.newCode(2, var2, var1, "count", 80, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item", "args"};
      index$30 = Py.newCode(3, var2, var1, "index", 81, true, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reverse$31 = Py.newCode(1, var2, var1, "reverse", 82, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwds"};
      sort$32 = Py.newCode(3, var2, var1, "sort", 83, true, true, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      extend$33 = Py.newCode(2, var2, var1, "extend", 84, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new UserList$py("UserList$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(UserList$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UserList$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.__lt__$4(var2, var3);
         case 5:
            return this.__le__$5(var2, var3);
         case 6:
            return this.__eq__$6(var2, var3);
         case 7:
            return this.__ne__$7(var2, var3);
         case 8:
            return this.__gt__$8(var2, var3);
         case 9:
            return this.__ge__$9(var2, var3);
         case 10:
            return this._UserList__cast$10(var2, var3);
         case 11:
            return this.__cmp__$11(var2, var3);
         case 12:
            return this.__contains__$12(var2, var3);
         case 13:
            return this.__len__$13(var2, var3);
         case 14:
            return this.__getitem__$14(var2, var3);
         case 15:
            return this.__setitem__$15(var2, var3);
         case 16:
            return this.__delitem__$16(var2, var3);
         case 17:
            return this.__getslice__$17(var2, var3);
         case 18:
            return this.__setslice__$18(var2, var3);
         case 19:
            return this.__delslice__$19(var2, var3);
         case 20:
            return this.__add__$20(var2, var3);
         case 21:
            return this.__radd__$21(var2, var3);
         case 22:
            return this.__iadd__$22(var2, var3);
         case 23:
            return this.__mul__$23(var2, var3);
         case 24:
            return this.__imul__$24(var2, var3);
         case 25:
            return this.append$25(var2, var3);
         case 26:
            return this.insert$26(var2, var3);
         case 27:
            return this.pop$27(var2, var3);
         case 28:
            return this.remove$28(var2, var3);
         case 29:
            return this.count$29(var2, var3);
         case 30:
            return this.index$30(var2, var3);
         case 31:
            return this.reverse$31(var2, var3);
         case 32:
            return this.sort$32(var2, var3);
         case 33:
            return this.extend$33(var2, var3);
         default:
            return null;
      }
   }
}
