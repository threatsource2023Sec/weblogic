import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFloat;
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

@APIVersion(37)
@MTime(1498849383000L)
@Filename("colorsys.py")
public class colorsys$py extends PyFunctionTable implements PyRunnable {
   static colorsys$py self;
   static final PyCode f$0;
   static final PyCode rgb_to_yiq$1;
   static final PyCode yiq_to_rgb$2;
   static final PyCode rgb_to_hls$3;
   static final PyCode hls_to_rgb$4;
   static final PyCode _v$5;
   static final PyCode rgb_to_hsv$6;
   static final PyCode hsv_to_rgb$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Conversion functions between RGB and other color systems.\n\nThis modules provides two functions for each color system ABC:\n\n  rgb_to_abc(r, g, b) --> a, b, c\n  abc_to_rgb(a, b, c) --> r, g, b\n\nAll inputs and outputs are triples of floats in the range [0.0...1.0]\n(with the exception of I and Q, which covers a slightly larger range).\nInputs outside the valid range may cause exceptions or invalid outputs.\n\nSupported color systems:\nRGB: Red, Green, Blue components\nYIQ: Luminance, Chrominance (used by composite video signals)\nHLS: Hue, Luminance, Saturation\nHSV: Hue, Saturation, Value\n"));
      var1.setline(17);
      PyString.fromInterned("Conversion functions between RGB and other color systems.\n\nThis modules provides two functions for each color system ABC:\n\n  rgb_to_abc(r, g, b) --> a, b, c\n  abc_to_rgb(a, b, c) --> r, g, b\n\nAll inputs and outputs are triples of floats in the range [0.0...1.0]\n(with the exception of I and Q, which covers a slightly larger range).\nInputs outside the valid range may cause exceptions or invalid outputs.\n\nSupported color systems:\nRGB: Red, Green, Blue components\nYIQ: Luminance, Chrominance (used by composite video signals)\nHLS: Hue, Luminance, Saturation\nHSV: Hue, Saturation, Value\n");
      var1.setline(24);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("rgb_to_yiq"), PyString.fromInterned("yiq_to_rgb"), PyString.fromInterned("rgb_to_hls"), PyString.fromInterned("hls_to_rgb"), PyString.fromInterned("rgb_to_hsv"), PyString.fromInterned("hsv_to_rgb")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(29);
      PyObject var4 = Py.newFloat(1.0)._div(Py.newFloat(3.0));
      var1.setlocal("ONE_THIRD", var4);
      var3 = null;
      var1.setline(30);
      var4 = Py.newFloat(1.0)._div(Py.newFloat(6.0));
      var1.setlocal("ONE_SIXTH", var4);
      var3 = null;
      var1.setline(31);
      var4 = Py.newFloat(2.0)._div(Py.newFloat(3.0));
      var1.setlocal("TWO_THIRD", var4);
      var3 = null;
      var1.setline(37);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, rgb_to_yiq$1, (PyObject)null);
      var1.setlocal("rgb_to_yiq", var6);
      var3 = null;
      var1.setline(43);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, yiq_to_rgb$2, (PyObject)null);
      var1.setlocal("yiq_to_rgb", var6);
      var3 = null;
      var1.setline(67);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, rgb_to_hls$3, (PyObject)null);
      var1.setlocal("rgb_to_hls", var6);
      var3 = null;
      var1.setline(90);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, hls_to_rgb$4, (PyObject)null);
      var1.setlocal("hls_to_rgb", var6);
      var3 = null;
      var1.setline(100);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _v$5, (PyObject)null);
      var1.setlocal("_v", var6);
      var3 = null;
      var1.setline(116);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, rgb_to_hsv$6, (PyObject)null);
      var1.setlocal("rgb_to_hsv", var6);
      var3 = null;
      var1.setline(135);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, hsv_to_rgb$7, (PyObject)null);
      var1.setlocal("hsv_to_rgb", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject rgb_to_yiq$1(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = Py.newFloat(0.3)._mul(var1.getlocal(0))._add(Py.newFloat(0.59)._mul(var1.getlocal(1)))._add(Py.newFloat(0.11)._mul(var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(39);
      var3 = Py.newFloat(0.6)._mul(var1.getlocal(0))._sub(Py.newFloat(0.28)._mul(var1.getlocal(1)))._sub(Py.newFloat(0.32)._mul(var1.getlocal(2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(40);
      var3 = Py.newFloat(0.21)._mul(var1.getlocal(0))._sub(Py.newFloat(0.52)._mul(var1.getlocal(1)))._add(Py.newFloat(0.31)._mul(var1.getlocal(2)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(41);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject yiq_to_rgb$2(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getlocal(0)._add(Py.newFloat(0.948262)._mul(var1.getlocal(1)))._add(Py.newFloat(0.624013)._mul(var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getlocal(0)._sub(Py.newFloat(0.276066)._mul(var1.getlocal(1)))._sub(Py.newFloat(0.63981)._mul(var1.getlocal(2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(0)._sub(Py.newFloat(1.10545)._mul(var1.getlocal(1)))._add(Py.newFloat(1.72986)._mul(var1.getlocal(2)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(Py.newFloat(0.0));
      var3 = null;
      PyFloat var4;
      if (var10000.__nonzero__()) {
         var1.setline(48);
         var4 = Py.newFloat(0.0);
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(49);
      var3 = var1.getlocal(4);
      var10000 = var3._lt(Py.newFloat(0.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(50);
         var4 = Py.newFloat(0.0);
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(51);
      var3 = var1.getlocal(5);
      var10000 = var3._lt(Py.newFloat(0.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(52);
         var4 = Py.newFloat(0.0);
         var1.setlocal(5, var4);
         var3 = null;
      }

      var1.setline(53);
      var3 = var1.getlocal(3);
      var10000 = var3._gt(Py.newFloat(1.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(54);
         var4 = Py.newFloat(1.0);
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(55);
      var3 = var1.getlocal(4);
      var10000 = var3._gt(Py.newFloat(1.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(56);
         var4 = Py.newFloat(1.0);
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(57);
      var3 = var1.getlocal(5);
      var10000 = var3._gt(Py.newFloat(1.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(58);
         var4 = Py.newFloat(1.0);
         var1.setlocal(5, var4);
         var3 = null;
      }

      var1.setline(59);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject rgb_to_hls$3(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getlocal(4)._add(var1.getlocal(3))._div(Py.newFloat(2.0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(72);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(var1.getlocal(3));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(73);
         var5 = new PyTuple(new PyObject[]{Py.newFloat(0.0), var1.getlocal(5), Py.newFloat(0.0)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(74);
         PyObject var4 = var1.getlocal(5);
         var10000 = var4._le(Py.newFloat(0.5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(75);
            var4 = var1.getlocal(3)._sub(var1.getlocal(4))._div(var1.getlocal(3)._add(var1.getlocal(4)));
            var1.setlocal(6, var4);
            var4 = null;
         } else {
            var1.setline(77);
            var4 = var1.getlocal(3)._sub(var1.getlocal(4))._div(Py.newFloat(2.0)._sub(var1.getlocal(3))._sub(var1.getlocal(4)));
            var1.setlocal(6, var4);
            var4 = null;
         }

         var1.setline(78);
         var4 = var1.getlocal(3)._sub(var1.getlocal(0))._div(var1.getlocal(3)._sub(var1.getlocal(4)));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(79);
         var4 = var1.getlocal(3)._sub(var1.getlocal(1))._div(var1.getlocal(3)._sub(var1.getlocal(4)));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(80);
         var4 = var1.getlocal(3)._sub(var1.getlocal(2))._div(var1.getlocal(3)._sub(var1.getlocal(4)));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(81);
         var4 = var1.getlocal(0);
         var10000 = var4._eq(var1.getlocal(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(82);
            var4 = var1.getlocal(9)._sub(var1.getlocal(8));
            var1.setlocal(10, var4);
            var4 = null;
         } else {
            var1.setline(83);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(84);
               var4 = Py.newFloat(2.0)._add(var1.getlocal(7))._sub(var1.getlocal(9));
               var1.setlocal(10, var4);
               var4 = null;
            } else {
               var1.setline(86);
               var4 = Py.newFloat(4.0)._add(var1.getlocal(8))._sub(var1.getlocal(7));
               var1.setlocal(10, var4);
               var4 = null;
            }
         }

         var1.setline(87);
         var4 = var1.getlocal(10)._div(Py.newFloat(6.0))._mod(Py.newFloat(1.0));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(88);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(5), var1.getlocal(6)});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject hls_to_rgb$4(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newFloat(0.0));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(92);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(93);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._le(Py.newFloat(0.5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(94);
            var4 = var1.getlocal(1)._mul(Py.newFloat(1.0)._add(var1.getlocal(2)));
            var1.setlocal(3, var4);
            var4 = null;
         } else {
            var1.setline(96);
            var4 = var1.getlocal(1)._add(var1.getlocal(2))._sub(var1.getlocal(1)._mul(var1.getlocal(2)));
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(97);
         var4 = Py.newFloat(2.0)._mul(var1.getlocal(1))._sub(var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(98);
         var5 = new PyTuple(new PyObject[]{var1.getglobal("_v").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(0)._add(var1.getglobal("ONE_THIRD"))), var1.getglobal("_v").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(0)), var1.getglobal("_v").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(0)._sub(var1.getglobal("ONE_THIRD")))});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _v$5(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(2)._mod(Py.newFloat(1.0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(102);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(var1.getglobal("ONE_SIXTH"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         var3 = var1.getlocal(0)._add(var1.getlocal(1)._sub(var1.getlocal(0))._mul(var1.getlocal(2))._mul(Py.newFloat(6.0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(104);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._lt(Py.newFloat(0.5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(105);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(106);
            var4 = var1.getlocal(2);
            var10000 = var4._lt(var1.getglobal("TWO_THIRD"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(107);
               var3 = var1.getlocal(0)._add(var1.getlocal(1)._sub(var1.getlocal(0))._mul(var1.getglobal("TWO_THIRD")._sub(var1.getlocal(2)))._mul(Py.newFloat(6.0)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(108);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject rgb_to_hsv$6(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getlocal(3);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(var1.getlocal(3));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(121);
         var5 = new PyTuple(new PyObject[]{Py.newFloat(0.0), Py.newFloat(0.0), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(122);
         PyObject var4 = var1.getlocal(3)._sub(var1.getlocal(4))._div(var1.getlocal(3));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(123);
         var4 = var1.getlocal(3)._sub(var1.getlocal(0))._div(var1.getlocal(3)._sub(var1.getlocal(4)));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(124);
         var4 = var1.getlocal(3)._sub(var1.getlocal(1))._div(var1.getlocal(3)._sub(var1.getlocal(4)));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(125);
         var4 = var1.getlocal(3)._sub(var1.getlocal(2))._div(var1.getlocal(3)._sub(var1.getlocal(4)));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(126);
         var4 = var1.getlocal(0);
         var10000 = var4._eq(var1.getlocal(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(127);
            var4 = var1.getlocal(9)._sub(var1.getlocal(8));
            var1.setlocal(10, var4);
            var4 = null;
         } else {
            var1.setline(128);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(129);
               var4 = Py.newFloat(2.0)._add(var1.getlocal(7))._sub(var1.getlocal(9));
               var1.setlocal(10, var4);
               var4 = null;
            } else {
               var1.setline(131);
               var4 = Py.newFloat(4.0)._add(var1.getlocal(8))._sub(var1.getlocal(7));
               var1.setlocal(10, var4);
               var4 = null;
            }
         }

         var1.setline(132);
         var4 = var1.getlocal(10)._div(Py.newFloat(6.0))._mod(Py.newFloat(1.0));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(133);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(6), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject hsv_to_rgb$7(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newFloat(0.0));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(137);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(2), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(138);
         PyObject var4 = var1.getglobal("int").__call__(var2, var1.getlocal(0)._mul(Py.newFloat(6.0)));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(139);
         var4 = var1.getlocal(0)._mul(Py.newFloat(6.0))._sub(var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(140);
         var4 = var1.getlocal(2)._mul(Py.newFloat(1.0)._sub(var1.getlocal(1)));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(141);
         var4 = var1.getlocal(2)._mul(Py.newFloat(1.0)._sub(var1.getlocal(1)._mul(var1.getlocal(4))));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(142);
         var4 = var1.getlocal(2)._mul(Py.newFloat(1.0)._sub(var1.getlocal(1)._mul(Py.newFloat(1.0)._sub(var1.getlocal(4)))));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(143);
         var4 = var1.getlocal(3)._mod(Py.newInteger(6));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(144);
         var4 = var1.getlocal(3);
         var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(145);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(7), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(146);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(147);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(2), var1.getlocal(5)});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(148);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(149);
                  var5 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(2), var1.getlocal(7)});
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(150);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(Py.newInteger(3));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(151);
                     var5 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(2)});
                     var1.f_lasti = -1;
                     return var5;
                  } else {
                     var1.setline(152);
                     var4 = var1.getlocal(3);
                     var10000 = var4._eq(Py.newInteger(4));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(153);
                        var5 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5), var1.getlocal(2)});
                        var1.f_lasti = -1;
                        return var5;
                     } else {
                        var1.setline(154);
                        var4 = var1.getlocal(3);
                        var10000 = var4._eq(Py.newInteger(5));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(155);
                           var5 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5), var1.getlocal(6)});
                           var1.f_lasti = -1;
                           return var5;
                        } else {
                           var1.f_lasti = -1;
                           return Py.None;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public colorsys$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"r", "g", "b", "y", "i", "q"};
      rgb_to_yiq$1 = Py.newCode(3, var2, var1, "rgb_to_yiq", 37, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"y", "i", "q", "r", "g", "b"};
      yiq_to_rgb$2 = Py.newCode(3, var2, var1, "yiq_to_rgb", 43, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"r", "g", "b", "maxc", "minc", "l", "s", "rc", "gc", "bc", "h"};
      rgb_to_hls$3 = Py.newCode(3, var2, var1, "rgb_to_hls", 67, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "l", "s", "m2", "m1"};
      hls_to_rgb$4 = Py.newCode(3, var2, var1, "hls_to_rgb", 90, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m1", "m2", "hue"};
      _v$5 = Py.newCode(3, var2, var1, "_v", 100, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"r", "g", "b", "maxc", "minc", "v", "s", "rc", "gc", "bc", "h"};
      rgb_to_hsv$6 = Py.newCode(3, var2, var1, "rgb_to_hsv", 116, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "s", "v", "i", "f", "p", "q", "t"};
      hsv_to_rgb$7 = Py.newCode(3, var2, var1, "hsv_to_rgb", 135, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new colorsys$py("colorsys$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(colorsys$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.rgb_to_yiq$1(var2, var3);
         case 2:
            return this.yiq_to_rgb$2(var2, var3);
         case 3:
            return this.rgb_to_hls$3(var2, var3);
         case 4:
            return this.hls_to_rgb$4(var2, var3);
         case 5:
            return this._v$5(var2, var3);
         case 6:
            return this.rgb_to_hsv$6(var2, var3);
         case 7:
            return this.hsv_to_rgb$7(var2, var3);
         default:
            return null;
      }
   }
}
