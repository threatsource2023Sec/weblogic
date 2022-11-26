package unittest;

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
@Filename("unittest/util.py")
public class util$py extends PyFunctionTable implements PyRunnable {
   static util$py self;
   static final PyCode f$0;
   static final PyCode safe_repr$1;
   static final PyCode strclass$2;
   static final PyCode sorted_list_difference$3;
   static final PyCode unorderable_list_difference$4;
   static final PyCode _count_diff_all_purpose$5;
   static final PyCode _ordered_count$6;
   static final PyCode _count_diff_hashable$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Various utility functions."));
      var1.setline(1);
      PyString.fromInterned("Various utility functions.");
      var1.setline(2);
      String[] var3 = new String[]{"namedtuple", "OrderedDict"};
      PyObject[] var5 = imp.importFrom("collections", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("OrderedDict", var4);
      var4 = null;
      var1.setline(5);
      PyObject var6 = var1.getname("True");
      var1.setlocal("__unittest", var6);
      var3 = null;
      var1.setline(7);
      PyInteger var7 = Py.newInteger(80);
      var1.setlocal("_MAX_LENGTH", var7);
      var3 = null;
      var1.setline(8);
      var5 = new PyObject[]{var1.getname("False")};
      PyFunction var8 = new PyFunction(var1.f_globals, var5, safe_repr$1, (PyObject)null);
      var1.setlocal("safe_repr", var8);
      var3 = null;
      var1.setline(18);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, strclass$2, (PyObject)null);
      var1.setlocal("strclass", var8);
      var3 = null;
      var1.setline(21);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, sorted_list_difference$3, PyString.fromInterned("Finds elements in only one or the other of two, sorted input lists.\n\n    Returns a two-element tuple of lists.    The first list contains those\n    elements in the \"expected\" list but not in the \"actual\" list, and the\n    second contains those elements in the \"actual\" list but not in the\n    \"expected\" list.    Duplicate elements in either input list are ignored.\n    "));
      var1.setlocal("sorted_list_difference", var8);
      var3 = null;
      var1.setline(62);
      var5 = new PyObject[]{var1.getname("False")};
      var8 = new PyFunction(var1.f_globals, var5, unorderable_list_difference$4, PyString.fromInterned("Same behavior as sorted_list_difference but\n    for lists of unorderable items (like dicts).\n\n    As it does a linear search per item (remove) it\n    has O(n*n) performance.\n    "));
      var1.setlocal("unorderable_list_difference", var8);
      var3 = null;
      var1.setline(98);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Mismatch"), (PyObject)PyString.fromInterned("actual expected value"));
      var1.setlocal("_Mismatch", var6);
      var3 = null;
      var1.setline(100);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _count_diff_all_purpose$5, PyString.fromInterned("Returns list of (cnt_act, cnt_exp, elem) triples where the counts differ"));
      var1.setlocal("_count_diff_all_purpose", var8);
      var3 = null;
      var1.setline(135);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _ordered_count$6, PyString.fromInterned("Return dict of element counts, in the order they were first seen"));
      var1.setlocal("_ordered_count", var8);
      var3 = null;
      var1.setline(142);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _count_diff_hashable$7, PyString.fromInterned("Returns list of (cnt_act, cnt_exp, elem) triples where the counts differ"));
      var1.setlocal("_count_diff_hashable", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject safe_repr$1(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(10);
         var6 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("Exception"))) {
            throw var3;
         }

         var1.setline(12);
         PyObject var4 = var1.getglobal("object").__getattr__("__repr__").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(13);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (!var10000.__nonzero__()) {
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var6._lt(var1.getglobal("_MAX_LENGTH"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(14);
         var6 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(15);
         var6 = var1.getlocal(2).__getslice__((PyObject)null, var1.getglobal("_MAX_LENGTH"), (PyObject)null)._add(PyString.fromInterned(" [truncated]..."));
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject strclass$2(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__module__"), var1.getlocal(0).__getattr__("__name__")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sorted_list_difference$3(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("Finds elements in only one or the other of two, sorted input lists.\n\n    Returns a two-element tuple of lists.    The first list contains those\n    elements in the \"expected\" list but not in the \"actual\" list, and the\n    second contains those elements in the \"actual\" list but not in the\n    \"expected\" list.    Duplicate elements in either input list are ignored.\n    ");
      var1.setline(29);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var1.setlocal(3, var3);
      var1.setline(30);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(31);
      var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var3 = null;

      while(true) {
         var1.setline(32);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         try {
            var1.setline(34);
            PyObject var9 = var1.getlocal(0).__getitem__(var1.getlocal(2));
            var1.setlocal(6, var9);
            var3 = null;
            var1.setline(35);
            var9 = var1.getlocal(1).__getitem__(var1.getlocal(3));
            var1.setlocal(7, var9);
            var3 = null;
            var1.setline(36);
            var9 = var1.getlocal(6);
            PyObject var10000 = var9._lt(var1.getlocal(7));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(37);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
               var1.setline(38);
               var9 = var1.getlocal(2);
               var9 = var9._iadd(Py.newInteger(1));
               var1.setlocal(2, var9);

               while(true) {
                  var1.setline(39);
                  var9 = var1.getlocal(0).__getitem__(var1.getlocal(2));
                  var10000 = var9._eq(var1.getlocal(6));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(40);
                  var9 = var1.getlocal(2);
                  var9 = var9._iadd(Py.newInteger(1));
                  var1.setlocal(2, var9);
               }
            } else {
               var1.setline(41);
               var9 = var1.getlocal(6);
               var10000 = var9._gt(var1.getlocal(7));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(42);
                  var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(7));
                  var1.setline(43);
                  var9 = var1.getlocal(3);
                  var9 = var9._iadd(Py.newInteger(1));
                  var1.setlocal(3, var9);

                  while(true) {
                     var1.setline(44);
                     var9 = var1.getlocal(1).__getitem__(var1.getlocal(3));
                     var10000 = var9._eq(var1.getlocal(7));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(45);
                     var9 = var1.getlocal(3);
                     var9 = var9._iadd(Py.newInteger(1));
                     var1.setlocal(3, var9);
                  }
               } else {
                  var1.setline(47);
                  var9 = var1.getlocal(2);
                  var9 = var9._iadd(Py.newInteger(1));
                  var1.setlocal(2, var9);
                  var3 = null;

                  PyObject var4;
                  try {
                     while(true) {
                        var1.setline(49);
                        var4 = var1.getlocal(0).__getitem__(var1.getlocal(2));
                        var10000 = var4._eq(var1.getlocal(6));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(50);
                        var4 = var1.getlocal(2);
                        var4 = var4._iadd(Py.newInteger(1));
                        var1.setlocal(2, var4);
                     }
                  } catch (Throwable var5) {
                     Py.addTraceback(var5, var1);
                     var1.setline(52);
                     var4 = var1.getlocal(3);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(3, var4);

                     while(true) {
                        var1.setline(53);
                        var4 = var1.getlocal(1).__getitem__(var1.getlocal(3));
                        var10000 = var4._eq(var1.getlocal(7));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           throw (Throwable)var5;
                        }

                        var1.setline(54);
                        var4 = var1.getlocal(3);
                        var4 = var4._iadd(Py.newInteger(1));
                        var1.setlocal(3, var4);
                     }
                  }

                  var1.setline(52);
                  var4 = var1.getlocal(3);
                  var4 = var4._iadd(Py.newInteger(1));
                  var1.setlocal(3, var4);

                  while(true) {
                     var1.setline(53);
                     var4 = var1.getlocal(1).__getitem__(var1.getlocal(3));
                     var10000 = var4._eq(var1.getlocal(7));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(54);
                     var4 = var1.getlocal(3);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(3, var4);
                  }
               }
            }
         } catch (Throwable var6) {
            PyException var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("IndexError"))) {
               var1.setline(56);
               var1.getlocal(4).__getattr__("extend").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
               var1.setline(57);
               var1.getlocal(5).__getattr__("extend").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
               break;
            }

            throw var8;
         }
      }

      var1.setline(59);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject unorderable_list_difference$4(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Same behavior as sorted_list_difference but\n    for lists of unorderable items (like dicts).\n\n    As it does a linear search per item (remove) it\n    has O(n*n) performance.\n    ");
      var1.setline(69);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(70);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(71);
         PyObject var9;
         PyException var10;
         if (!var1.getlocal(0).__nonzero__()) {
            var1.setline(84);
            PyTuple var11;
            if (!var1.getlocal(2).__nonzero__()) {
               var1.setline(96);
               var11 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1)});
               var1.f_lasti = -1;
               return var11;
            } else {
               while(true) {
                  var1.setline(85);
                  if (!var1.getlocal(1).__nonzero__()) {
                     var1.setline(93);
                     var11 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
                     var1.f_lasti = -1;
                     return var11;
                  }

                  var1.setline(86);
                  var9 = var1.getlocal(1).__getattr__("pop").__call__(var2);
                  var1.setlocal(5, var9);
                  var3 = null;
                  var1.setline(87);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));

                  try {
                     while(true) {
                        var1.setline(89);
                        if (!var1.getglobal("True").__nonzero__()) {
                           break;
                        }

                        var1.setline(90);
                        var1.getlocal(1).__getattr__("remove").__call__(var2, var1.getlocal(5));
                     }
                  } catch (Throwable var6) {
                     var10 = Py.setException(var6, var1);
                     if (!var10.match(var1.getglobal("ValueError"))) {
                        throw var10;
                     }

                     var1.setline(92);
                  }
               }
            }
         }

         var1.setline(72);
         var9 = var1.getlocal(0).__getattr__("pop").__call__(var2);
         var1.setlocal(5, var9);
         var3 = null;

         try {
            var1.setline(74);
            var1.getlocal(1).__getattr__("remove").__call__(var2, var1.getlocal(5));
         } catch (Throwable var8) {
            var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getglobal("ValueError"))) {
               throw var10;
            }

            var1.setline(76);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         }

         var1.setline(77);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(78);
            var9 = (new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})).__iter__();

            while(true) {
               var1.setline(78);
               PyObject var4 = var9.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(6, var4);

               try {
                  while(true) {
                     var1.setline(80);
                     if (!var1.getglobal("True").__nonzero__()) {
                        break;
                     }

                     var1.setline(81);
                     var1.getlocal(6).__getattr__("remove").__call__(var2, var1.getlocal(5));
                  }
               } catch (Throwable var7) {
                  PyException var5 = Py.setException(var7, var1);
                  if (!var5.match(var1.getglobal("ValueError"))) {
                     throw var5;
                  }

                  var1.setline(83);
               }
            }
         }
      }
   }

   public PyObject _count_diff_all_purpose$5(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("Returns list of (cnt_act, cnt_exp, elem) triples where the counts differ");
      var1.setline(103);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("list").__call__(var2, var1.getlocal(0)), var1.getglobal("list").__call__(var2, var1.getlocal(1))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(104);
      var3 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(2)), var1.getglobal("len").__call__(var2, var1.getlocal(3))});
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(105);
      PyObject var9 = var1.getglobal("object").__call__(var2);
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(106);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setlocal(7, var11);
      var3 = null;
      var1.setline(107);
      var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2)).__iter__();

      label64:
      while(true) {
         PyObject var6;
         PyObject var7;
         PyInteger var13;
         PyObject var10000;
         do {
            var1.setline(107);
            PyObject var10 = var9.__iternext__();
            PyObject[] var12;
            if (var10 == null) {
               var1.setline(123);
               var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3)).__iter__();

               while(true) {
                  do {
                     var1.setline(123);
                     var10 = var9.__iternext__();
                     if (var10 == null) {
                        var1.setline(133);
                        var9 = var1.getlocal(7);
                        var1.f_lasti = -1;
                        return var9;
                     }

                     var12 = Py.unpackSequence(var10, 2);
                     var6 = var12[0];
                     var1.setlocal(8, var6);
                     var6 = null;
                     var6 = var12[1];
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(124);
                     var5 = var1.getlocal(9);
                     var10000 = var5._is(var1.getlocal(6));
                     var5 = null;
                  } while(var10000.__nonzero__());

                  var1.setline(126);
                  var13 = Py.newInteger(0);
                  var1.setlocal(11, var13);
                  var5 = null;
                  var1.setline(127);
                  var5 = var1.getglobal("range").__call__(var2, var1.getlocal(8), var1.getlocal(5)).__iter__();

                  while(true) {
                     var1.setline(127);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        var1.setline(131);
                        var5 = var1.getglobal("_Mismatch").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getlocal(11), (PyObject)var1.getlocal(9));
                        var1.setlocal(14, var5);
                        var5 = null;
                        var1.setline(132);
                        var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(14));
                        break;
                     }

                     var1.setlocal(12, var6);
                     var1.setline(128);
                     var7 = var1.getlocal(3).__getitem__(var1.getlocal(12));
                     var10000 = var7._eq(var1.getlocal(9));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(129);
                        var7 = var1.getlocal(11);
                        var7 = var7._iadd(Py.newInteger(1));
                        var1.setlocal(11, var7);
                        var1.setline(130);
                        var7 = var1.getlocal(6);
                        var1.getlocal(3).__setitem__(var1.getlocal(12), var7);
                        var7 = null;
                     }
                  }
               }
            }

            var12 = Py.unpackSequence(var10, 2);
            var6 = var12[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal(9, var6);
            var6 = null;
            var1.setline(108);
            var5 = var1.getlocal(9);
            var10000 = var5._is(var1.getlocal(6));
            var5 = null;
         } while(var10000.__nonzero__());

         var1.setline(110);
         var13 = Py.newInteger(0);
         var1.setlocal(10, var13);
         var1.setlocal(11, var13);
         var1.setline(111);
         var5 = var1.getglobal("range").__call__(var2, var1.getlocal(8), var1.getlocal(4)).__iter__();

         while(true) {
            var1.setline(111);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(115);
               var5 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3)).__iter__();

               while(true) {
                  var1.setline(115);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(119);
                     var5 = var1.getlocal(10);
                     var10000 = var5._ne(var1.getlocal(11));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(120);
                        var5 = var1.getglobal("_Mismatch").__call__(var2, var1.getlocal(10), var1.getlocal(11), var1.getlocal(9));
                        var1.setlocal(14, var5);
                        var5 = null;
                        var1.setline(121);
                        var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(14));
                     }
                     continue label64;
                  }

                  PyObject[] var14 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var14[0];
                  var1.setlocal(12, var8);
                  var8 = null;
                  var8 = var14[1];
                  var1.setlocal(13, var8);
                  var8 = null;
                  var1.setline(116);
                  var7 = var1.getlocal(13);
                  var10000 = var7._eq(var1.getlocal(9));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(117);
                     var7 = var1.getlocal(11);
                     var7 = var7._iadd(Py.newInteger(1));
                     var1.setlocal(11, var7);
                     var1.setline(118);
                     var7 = var1.getlocal(6);
                     var1.getlocal(3).__setitem__(var1.getlocal(12), var7);
                     var7 = null;
                  }
               }
            }

            var1.setlocal(12, var6);
            var1.setline(112);
            var7 = var1.getlocal(2).__getitem__(var1.getlocal(12));
            var10000 = var7._eq(var1.getlocal(9));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(113);
               var7 = var1.getlocal(10);
               var7 = var7._iadd(Py.newInteger(1));
               var1.setlocal(10, var7);
               var1.setline(114);
               var7 = var1.getlocal(6);
               var1.getlocal(2).__setitem__(var1.getlocal(12), var7);
               var7 = null;
            }
         }
      }
   }

   public PyObject _ordered_count$6(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyString.fromInterned("Return dict of element counts, in the order they were first seen");
      var1.setline(137);
      PyObject var3 = var1.getglobal("OrderedDict").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(138);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(138);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(140);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(139);
         PyObject var5 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
         var1.getlocal(1).__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject _count_diff_hashable$7(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Returns list of (cnt_act, cnt_exp, elem) triples where the counts differ");
      var1.setline(145);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("_ordered_count").__call__(var2, var1.getlocal(0)), var1.getglobal("_ordered_count").__call__(var2, var1.getlocal(1))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(146);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(147);
      PyObject var8 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(147);
         PyObject var9 = var8.__iternext__();
         PyObject var10000;
         PyObject var6;
         PyObject[] var10;
         if (var9 == null) {
            var1.setline(152);
            var8 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(152);
               var9 = var8.__iternext__();
               if (var9 == null) {
                  var1.setline(156);
                  var8 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var8;
               }

               var10 = Py.unpackSequence(var9, 2);
               var6 = var10[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var10[1];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(153);
               var5 = var1.getlocal(5);
               var10000 = var5._notin(var1.getlocal(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(154);
                  var5 = var1.getglobal("_Mismatch").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getlocal(7), (PyObject)var1.getlocal(5));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(155);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
               }
            }
         }

         var10 = Py.unpackSequence(var9, 2);
         var6 = var10[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(148);
         var5 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(0));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(149);
         var5 = var1.getlocal(6);
         var10000 = var5._ne(var1.getlocal(7));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(150);
            var5 = var1.getglobal("_Mismatch").__call__(var2, var1.getlocal(6), var1.getlocal(7), var1.getlocal(5));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(151);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
         }
      }
   }

   public util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj", "short", "result"};
      safe_repr$1 = Py.newCode(2, var2, var1, "safe_repr", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls"};
      strclass$2 = Py.newCode(1, var2, var1, "strclass", 18, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"expected", "actual", "i", "j", "missing", "unexpected", "e", "a"};
      sorted_list_difference$3 = Py.newCode(2, var2, var1, "sorted_list_difference", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"expected", "actual", "ignore_duplicate", "missing", "unexpected", "item", "lst"};
      unorderable_list_difference$4 = Py.newCode(3, var2, var1, "unorderable_list_difference", 62, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"actual", "expected", "s", "t", "m", "n", "NULL", "result", "i", "elem", "cnt_s", "cnt_t", "j", "other_elem", "diff"};
      _count_diff_all_purpose$5 = Py.newCode(2, var2, var1, "_count_diff_all_purpose", 100, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"iterable", "c", "elem"};
      _ordered_count$6 = Py.newCode(1, var2, var1, "_ordered_count", 135, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"actual", "expected", "s", "t", "result", "elem", "cnt_s", "cnt_t", "diff"};
      _count_diff_hashable$7 = Py.newCode(2, var2, var1, "_count_diff_hashable", 142, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new util$py("unittest/util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.safe_repr$1(var2, var3);
         case 2:
            return this.strclass$2(var2, var3);
         case 3:
            return this.sorted_list_difference$3(var2, var3);
         case 4:
            return this.unorderable_list_difference$4(var2, var3);
         case 5:
            return this._count_diff_all_purpose$5(var2, var3);
         case 6:
            return this._ordered_count$6(var2, var3);
         case 7:
            return this._count_diff_hashable$7(var2, var3);
         default:
            return null;
      }
   }
}
