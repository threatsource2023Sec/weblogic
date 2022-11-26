package modjy;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_input.py")
public class modjy_input$py extends PyFunctionTable implements PyRunnable {
   static modjy_input$py self;
   static final PyCode f$0;
   static final PyCode modjy_input_object$1;
   static final PyCode __init__$2;
   static final PyCode istream_read$3;
   static final PyCode read$4;
   static final PyCode readline$5;
   static final PyCode readlines$6;
   static final PyCode __iter__$7;
   static final PyCode next$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = imp.importOne("jarray", var1, -1);
      var1.setlocal("jarray", var3);
      var3 = null;
      var1.setline(27);
      PyObject[] var5 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("modjy_input_object", var5, modjy_input_object$1);
      var1.setlocal("modjy_input_object", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject modjy_input_object$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      PyObject[] var3 = new PyObject[]{Py.newInteger(8192)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, istream_read$3, (PyObject)null);
      var1.setlocal("istream_read", var4);
      var3 = null;
      var1.setline(45);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, read$4, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(85);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, readline$5, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(145);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, readlines$6, (PyObject)null);
      var1.setlocal("readlines", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$7, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$8, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("istream", var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("buffer_size", var3);
      var3 = null;
      var1.setline(32);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject istream_read$3(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getglobal("jarray").__getattr__("zeros").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("b"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getlocal(0).__getattr__("istream").__getattr__("read").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(38);
         var5 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(39);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._le(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(40);
            var5 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(41);
            var4 = var1.getlocal(3);
            var10000 = var4._lt(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(42);
               var4 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
               var1.setlocal(2, var4);
               var4 = null;
            }

            var1.setline(43);
            var3 = var1.getlocal(2).__getattr__("tostring").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject read$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(49);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(50);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(51);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2));
         }

         var1.setline(52);
         PyString var7 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"buffer", var7);
         var3 = null;
         var1.setline(53);
         var3 = var1.getlocal(0).__getattr__("buffer_size");
         var1.setlocal(4, var3);
         var3 = null;

         while(true) {
            var1.setline(54);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(55);
            var3 = var1.getlocal(0).__getattr__("istream_read").__call__(var2, var1.getlocal(4));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(56);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               break;
            }

            var1.setline(58);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2));
         }

         var1.setline(59);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(62);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(63);
         var4 = var1.getlocal(5);
         var10000 = var4._ge(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(64);
            var4 = var1.getlocal(2).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("buffer", var4);
            var4 = null;
            var1.setline(65);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(66);
            PyList var6 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var6);
            var4 = null;
            var1.setline(67);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(68);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2));
            }

            var1.setline(69);
            PyString var8 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"buffer", var8);
            var4 = null;

            while(true) {
               var1.setline(70);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(71);
               var4 = var1.getlocal(1)._sub(var1.getlocal(5));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(72);
               var4 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("buffer_size"), var1.getlocal(6));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(73);
               var4 = var1.getlocal(0).__getattr__("istream_read").__call__(var2, var1.getlocal(4));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(74);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(76);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2));
               var1.setline(77);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(78);
               var4 = var1.getlocal(7);
               var10000 = var4._ge(var1.getlocal(6));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(79);
                  var4 = var1.getlocal(2).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("buffer", var4);
                  var4 = null;
                  var1.setline(80);
                  var4 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
                  var1.getlocal(3).__setitem__((PyObject)Py.newInteger(-1), var4);
                  var4 = null;
                  break;
               }

               var1.setline(82);
               var4 = var1.getlocal(5);
               var4 = var4._iadd(var1.getlocal(7));
               var1.setlocal(5, var4);
            }

            var1.setline(83);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject readline$5(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      PyObject var4;
      PyList var5;
      PyString var6;
      if (var10000.__nonzero__()) {
         var1.setline(89);
         var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(90);
         var3 = var1.getlocal(3);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(91);
            var3 = var1.getlocal(3);
            var3 = var3._iadd(Py.newInteger(1));
            var1.setlocal(3, var3);
            var1.setline(92);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("buffer", var3);
            var3 = null;
            var1.setline(93);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(94);
            var5 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var5);
            var4 = null;
            var1.setline(95);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(96);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
            }

            var1.setline(97);
            var6 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"buffer", var6);
            var4 = null;

            while(true) {
               var1.setline(98);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(99);
               var4 = var1.getlocal(0).__getattr__("istream_read").__call__(var2, var1.getlocal(0).__getattr__("buffer_size"));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(100);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(102);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
               var1.setline(103);
               var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(104);
               var4 = var1.getlocal(3);
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(105);
                  var4 = var1.getlocal(3);
                  var4 = var4._iadd(Py.newInteger(1));
                  var1.setlocal(3, var4);
                  var1.setline(106);
                  var4 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("buffer", var4);
                  var4 = null;
                  var1.setline(107);
                  var4 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
                  var1.getlocal(4).__setitem__((PyObject)Py.newInteger(-1), var4);
                  var4 = null;
                  break;
               }
            }

            var1.setline(109);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(112);
         var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(113);
         var4 = var1.getlocal(3);
         var10000 = var4._ge(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(114);
            var4 = var1.getlocal(3);
            var4 = var4._iadd(Py.newInteger(1));
            var1.setlocal(3, var4);
            var1.setline(115);
            var4 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("buffer", var4);
            var4 = null;
            var1.setline(116);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(117);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(118);
            var4 = var1.getlocal(5);
            var10000 = var4._ge(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(119);
               var4 = var1.getlocal(2).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("buffer", var4);
               var4 = null;
               var1.setline(120);
               var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(121);
               var5 = new PyList(Py.EmptyObjects);
               var1.setlocal(4, var5);
               var4 = null;
               var1.setline(122);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(123);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
               }

               var1.setline(124);
               var6 = PyString.fromInterned("");
               var1.getlocal(0).__setattr__((String)"buffer", var6);
               var4 = null;

               while(true) {
                  var1.setline(125);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break;
                  }

                  var1.setline(126);
                  var4 = var1.getlocal(0).__getattr__("istream_read").__call__(var2, var1.getlocal(0).__getattr__("buffer_size"));
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(127);
                  if (var1.getlocal(2).__not__().__nonzero__()) {
                     break;
                  }

                  var1.setline(129);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
                  var1.setline(130);
                  var4 = var1.getlocal(1)._sub(var1.getlocal(5));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(131);
                  var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(6));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(132);
                  var4 = var1.getlocal(3);
                  var10000 = var4._ge(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(133);
                     var4 = var1.getlocal(3);
                     var4 = var4._iadd(Py.newInteger(1));
                     var1.setlocal(3, var4);
                     var1.setline(134);
                     var4 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
                     var1.getlocal(0).__setattr__("buffer", var4);
                     var4 = null;
                     var1.setline(135);
                     var4 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
                     var1.getlocal(4).__setitem__((PyObject)Py.newInteger(-1), var4);
                     var4 = null;
                     break;
                  }

                  var1.setline(137);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(138);
                  var4 = var1.getlocal(7);
                  var10000 = var4._ge(var1.getlocal(6));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(139);
                     var4 = var1.getlocal(2).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
                     var1.getlocal(0).__setattr__("buffer", var4);
                     var4 = null;
                     var1.setline(140);
                     var4 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
                     var1.getlocal(4).__setitem__((PyObject)Py.newInteger(-1), var4);
                     var4 = null;
                     break;
                  }

                  var1.setline(142);
                  var4 = var1.getlocal(5);
                  var4 = var4._iadd(var1.getlocal(7));
                  var1.setlocal(5, var4);
               }

               var1.setline(143);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject readlines$6(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(147);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var4);
      var3 = null;

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(148);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(149);
         var5 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(150);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(152);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(153);
         var5 = var1.getlocal(2);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var1.setlocal(2, var5);
         var1.setline(154);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2);
            var10000 = var5._ge(var1.getlocal(1));
            var3 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(156);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __iter__$7(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$8(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(165);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(166);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(167);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public modjy_input$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_input_object$1 = Py.newCode(0, var2, var1, "modjy_input_object", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "servlet_inputstream", "bufsize"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 29, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "data", "m"};
      istream_read$3 = Py.newCode(2, var2, var1, "istream_read", 34, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "data", "buffers", "recv_size", "buf_len", "left", "n"};
      read$4 = Py.newCode(2, var2, var1, "read", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "data", "nl", "buffers", "buf_len", "left", "n"};
      readline$5 = Py.newCode(2, var2, var1, "readline", 85, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint", "total", "list", "line"};
      readlines$6 = Py.newCode(2, var2, var1, "readlines", 145, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$7 = Py.newCode(1, var2, var1, "__iter__", 160, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$8 = Py.newCode(1, var2, var1, "next", 163, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_input$py("modjy/modjy_input$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_input$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_input_object$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.istream_read$3(var2, var3);
         case 4:
            return this.read$4(var2, var3);
         case 5:
            return this.readline$5(var2, var3);
         case 6:
            return this.readlines$6(var2, var3);
         case 7:
            return this.__iter__$7(var2, var3);
         case 8:
            return this.next$8(var2, var3);
         default:
            return null;
      }
   }
}
