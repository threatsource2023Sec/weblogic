package json.tests;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("json/tests/test_recursion.py")
public class test_recursion$py extends PyFunctionTable implements PyRunnable {
   static test_recursion$py self;
   static final PyCode f$0;
   static final PyCode JSONTestObject$1;
   static final PyCode TestRecursion$2;
   static final PyCode test_listrecursion$3;
   static final PyCode test_dictrecursion$4;
   static final PyCode test_defaultrecursion$5;
   static final PyCode RecursiveJSONEncoder$6;
   static final PyCode default$7;
   static final PyCode test_highly_nested_objects_decoding$8;
   static final PyCode test_highly_nested_objects_encoding$9;
   static final PyCode test_endless_recursion$10;
   static final PyCode EndlessJSONEncoder$11;
   static final PyCode default$12;
   static final PyCode TestPyRecursion$13;
   static final PyCode TestCRecursion$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"PyTest", "CTest"};
      PyObject[] var5 = imp.importFrom("json.tests", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(2);
      PyObject var6 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var6);
      var3 = null;
      var1.setline(3);
      var3 = new String[]{"test_support"};
      var5 = imp.importFrom("test", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("test_support", var4);
      var4 = null;
      var1.setline(6);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("JSONTestObject", var5, JSONTestObject$1);
      var1.setlocal("JSONTestObject", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(10);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestRecursion", var5, TestRecursion$2);
      var1.setlocal("TestRecursion", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(116);
      var5 = new PyObject[]{var1.getname("TestRecursion"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyRecursion", var5, TestPyRecursion$13);
      var1.setlocal("TestPyRecursion", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(117);
      var5 = new PyObject[]{var1.getname("TestRecursion"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCRecursion", var5, TestCRecursion$14);
      var1.setlocal("TestCRecursion", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject JSONTestObject$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(7);
      return var1.getf_locals();
   }

   public PyObject TestRecursion$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_listrecursion$3, (PyObject)null);
      var1.setlocal("test_listrecursion", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dictrecursion$4, (PyObject)null);
      var1.setlocal("test_dictrecursion", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_defaultrecursion$5, (PyObject)null);
      var1.setlocal("test_defaultrecursion", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_highly_nested_objects_decoding$8, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("test_support").__getattr__("is_jython"), (PyObject)PyString.fromInterned("See http://bugs.jython.org/issue2536.")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_highly_nested_objects_decoding", var5);
      var3 = null;
      var1.setline(89);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_highly_nested_objects_encoding$9, (PyObject)null);
      var5 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("test_support").__getattr__("is_jython"), (PyObject)PyString.fromInterned("See http://bugs.jython.org/issue2536.")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_highly_nested_objects_encoding", var5);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_endless_recursion$10, (PyObject)null);
      var5 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("test_support").__getattr__("is_jython"), (PyObject)PyString.fromInterned("See http://bugs.jython.org/issue2536.")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_endless_recursion", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_listrecursion$3(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(13);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(1));

      PyException var6;
      label35: {
         try {
            var1.setline(15);
            var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
         } catch (Throwable var5) {
            var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               var1.setline(17);
               break label35;
            }

            throw var6;
         }

         var1.setline(19);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("didn't raise ValueError on list recursion"));
      }

      var1.setline(20);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(21);
      var3 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(22);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));

      label27: {
         try {
            var1.setline(24);
            var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
         } catch (Throwable var4) {
            var6 = Py.setException(var4, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               var1.setline(26);
               break label27;
            }

            throw var6;
         }

         var1.setline(28);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("didn't raise ValueError on alternating list recursion"));
      }

      var1.setline(29);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(30);
      var3 = new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(2)});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(32);
      var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dictrecursion$4(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(36);
      PyObject var5 = var1.getlocal(1);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("test"), var5);
      var3 = null;

      label19: {
         try {
            var1.setline(38);
            var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var6 = Py.setException(var4, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               var1.setline(40);
               break label19;
            }

            throw var6;
         }

         var1.setline(42);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("didn't raise ValueError on dict recursion"));
      }

      var1.setline(43);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(44);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("a"), var1.getlocal(1), PyString.fromInterned("b"), var1.getlocal(1)});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(46);
      var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_defaultrecursion$5(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("json").__getattr__("JSONEncoder")};
      PyObject var4 = Py.makeClass("RecursiveJSONEncoder", var3, RecursiveJSONEncoder$6);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(59);
      PyObject var6 = var1.getlocal(1).__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(60);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("encode").__call__(var2, var1.getglobal("JSONTestObject")), (PyObject)PyString.fromInterned("\"JSONTestObject\""));
      var1.setline(61);
      var6 = var1.getglobal("True");
      var1.getlocal(2).__setattr__("recurse", var6);
      var3 = null;

      label19: {
         try {
            var1.setline(63);
            var1.getlocal(2).__getattr__("encode").__call__(var2, var1.getglobal("JSONTestObject"));
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("ValueError"))) {
               var1.setline(65);
               break label19;
            }

            throw var7;
         }

         var1.setline(67);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("didn't raise ValueError on default recursion"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject RecursiveJSONEncoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(50);
      PyObject var3 = var1.getname("False");
      var1.setlocal("recurse", var3);
      var3 = null;
      var1.setline(51);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, default$7, (PyObject)null);
      var1.setlocal("default", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject default$7(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("JSONTestObject"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(53);
         if (var1.getlocal(0).__getattr__("recurse").__nonzero__()) {
            var1.setline(54);
            PyList var5 = new PyList(new PyObject[]{var1.getglobal("JSONTestObject")});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(56);
            PyString var4 = PyString.fromInterned("JSONTestObject");
            var1.f_lasti = -1;
            return var4;
         }
      } else {
         var1.setline(57);
         var3 = var1.getglobal("pyjson").__getattr__("JSONEncoder").__getattr__("default").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject test_highly_nested_objects_decoding$8(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[6];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

      label81: {
         try {
            var1.setline(76);
            var1.getlocal(0).__getattr__("loads").__call__(var2, PyString.fromInterned("{\"a\":")._mul(Py.newInteger(100000))._add(PyString.fromInterned("1"))._add(PyString.fromInterned("}")._mul(Py.newInteger(100000))));
         } catch (Throwable var10) {
            if (var3.__exit__(var2, Py.setException(var10, var1))) {
               break label81;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

      label74: {
         try {
            var1.setline(78);
            var1.getlocal(0).__getattr__("loads").__call__(var2, PyString.fromInterned("{\"a\":")._mul(Py.newInteger(100000))._add(PyString.fromInterned("[1]"))._add(PyString.fromInterned("}")._mul(Py.newInteger(100000))));
         } catch (Throwable var9) {
            if (var3.__exit__(var2, Py.setException(var9, var1))) {
               break label74;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

      label67: {
         try {
            var1.setline(80);
            var1.getlocal(0).__getattr__("loads").__call__(var2, PyString.fromInterned("[")._mul(Py.newInteger(100000))._add(PyString.fromInterned("1"))._add(PyString.fromInterned("]")._mul(Py.newInteger(100000))));
         } catch (Throwable var8) {
            if (var3.__exit__(var2, Py.setException(var8, var1))) {
               break label67;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

      label60: {
         try {
            var1.setline(83);
            var1.getlocal(0).__getattr__("loads").__call__(var2, PyUnicode.fromInterned("{\"a\":")._mul(Py.newInteger(100000))._add(PyUnicode.fromInterned("1"))._add(PyUnicode.fromInterned("}")._mul(Py.newInteger(100000))));
         } catch (Throwable var7) {
            if (var3.__exit__(var2, Py.setException(var7, var1))) {
               break label60;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

      label53: {
         try {
            var1.setline(85);
            var1.getlocal(0).__getattr__("loads").__call__(var2, PyUnicode.fromInterned("{\"a\":")._mul(Py.newInteger(100000))._add(PyUnicode.fromInterned("[1]"))._add(PyUnicode.fromInterned("}")._mul(Py.newInteger(100000))));
         } catch (Throwable var6) {
            if (var3.__exit__(var2, Py.setException(var6, var1))) {
               break label53;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

      label46: {
         try {
            var1.setline(87);
            var1.getlocal(0).__getattr__("loads").__call__(var2, PyUnicode.fromInterned("[")._mul(Py.newInteger(100000))._add(PyUnicode.fromInterned("1"))._add(PyUnicode.fromInterned("]")._mul(Py.newInteger(100000))));
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label46;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_highly_nested_objects_encoding$9(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(92);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(93);
      PyObject var10 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(100000)).__iter__();

      while(true) {
         var1.setline(93);
         PyObject var12 = var10.__iternext__();
         if (var12 == null) {
            ContextManager var11;
            var12 = (var11 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

            label32: {
               try {
                  var1.setline(96);
                  var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
               } catch (Throwable var9) {
                  if (var11.__exit__(var2, Py.setException(var9, var1))) {
                     break label32;
                  }

                  throw (Throwable)Py.makeException();
               }

               var11.__exit__(var2, (PyException)null);
            }

            var12 = (var11 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("RuntimeError")))).__enter__(var2);

            label25: {
               try {
                  var1.setline(98);
                  var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(2));
               } catch (Throwable var8) {
                  if (var11.__exit__(var2, Py.setException(var8, var1))) {
                     break label25;
                  }

                  throw (Throwable)Py.makeException();
               }

               var11.__exit__(var2, (PyException)null);
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var12);
         var1.setline(94);
         PyTuple var13 = new PyTuple(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(1)}), new PyDictionary(new PyObject[]{PyString.fromInterned("k"), var1.getlocal(2)})});
         PyObject[] var6 = Py.unpackSequence(var13, 2);
         PyObject var7 = var6[0];
         var1.setlocal(1, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(2, var7);
         var7 = null;
         var5 = null;
      }
   }

   public PyObject test_endless_recursion$10(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(103);
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("json").__getattr__("JSONEncoder")};
      PyObject var4 = Py.makeClass("EndlessJSONEncoder", var3, EndlessJSONEncoder$11);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      ContextManager var7;
      var4 = (var7 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("Exception")))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(2, var4);
            var1.setline(112);
            PyObject var10000 = var1.getlocal(1);
            PyObject[] var8 = new PyObject[]{var1.getglobal("False")};
            String[] var5 = new String[]{"check_circular"};
            var10000 = var10000.__call__(var2, var8, var5);
            var4 = null;
            var10000.__getattr__("encode").__call__((ThreadState)var2, (PyObject)Py.newImaginary(5.0));
         } catch (Throwable var6) {
            if (var7.__exit__(var2, Py.setException(var6, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var7.__exit__(var2, (PyException)null);
      }

      var1.setline(113);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)var1.getglobal("type").__call__(var2, var1.getlocal(2).__getattr__("exception")), (PyObject)(new PyList(new PyObject[]{var1.getglobal("RuntimeError"), var1.getglobal("ValueError")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EndlessJSONEncoder$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(104);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, default$12, PyString.fromInterned("If check_circular is False, this will keep adding another list."));
      var1.setlocal("default", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject default$12(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("If check_circular is False, this will keep adding another list.");
      var1.setline(106);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestPyRecursion$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(116);
      return var1.getf_locals();
   }

   public PyObject TestCRecursion$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(117);
      return var1.getf_locals();
   }

   public test_recursion$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      JSONTestObject$1 = Py.newCode(0, var2, var1, "JSONTestObject", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestRecursion$2 = Py.newCode(0, var2, var1, "TestRecursion", 10, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "x", "y"};
      test_listrecursion$3 = Py.newCode(1, var2, var1, "test_listrecursion", 11, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "y"};
      test_dictrecursion$4 = Py.newCode(1, var2, var1, "test_dictrecursion", 34, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "RecursiveJSONEncoder", "enc"};
      test_defaultrecursion$5 = Py.newCode(1, var2, var1, "test_defaultrecursion", 48, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RecursiveJSONEncoder$6 = Py.newCode(0, var2, var1, "RecursiveJSONEncoder", 49, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "o"};
      default$7 = Py.newCode(2, var2, var1, "default", 51, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_highly_nested_objects_decoding$8 = Py.newCode(1, var2, var1, "test_highly_nested_objects_decoding", 70, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "d", "x"};
      test_highly_nested_objects_encoding$9 = Py.newCode(1, var2, var1, "test_highly_nested_objects_encoding", 89, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "EndlessJSONEncoder", "cm"};
      test_endless_recursion$10 = Py.newCode(1, var2, var1, "test_endless_recursion", 100, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EndlessJSONEncoder$11 = Py.newCode(0, var2, var1, "EndlessJSONEncoder", 103, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "o"};
      default$12 = Py.newCode(2, var2, var1, "default", 104, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyRecursion$13 = Py.newCode(0, var2, var1, "TestPyRecursion", 116, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCRecursion$14 = Py.newCode(0, var2, var1, "TestCRecursion", 117, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_recursion$py("json/tests/test_recursion$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_recursion$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.JSONTestObject$1(var2, var3);
         case 2:
            return this.TestRecursion$2(var2, var3);
         case 3:
            return this.test_listrecursion$3(var2, var3);
         case 4:
            return this.test_dictrecursion$4(var2, var3);
         case 5:
            return this.test_defaultrecursion$5(var2, var3);
         case 6:
            return this.RecursiveJSONEncoder$6(var2, var3);
         case 7:
            return this.default$7(var2, var3);
         case 8:
            return this.test_highly_nested_objects_decoding$8(var2, var3);
         case 9:
            return this.test_highly_nested_objects_encoding$9(var2, var3);
         case 10:
            return this.test_endless_recursion$10(var2, var3);
         case 11:
            return this.EndlessJSONEncoder$11(var2, var3);
         case 12:
            return this.default$12(var2, var3);
         case 13:
            return this.TestPyRecursion$13(var2, var3);
         case 14:
            return this.TestCRecursion$14(var2, var3);
         default:
            return null;
      }
   }
}
