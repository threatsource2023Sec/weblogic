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
@Filename("fpformat.py")
public class fpformat$py extends PyFunctionTable implements PyRunnable {
   static fpformat$py self;
   static final PyCode f$0;
   static final PyCode NotANumber$1;
   static final PyCode extract$2;
   static final PyCode unexpo$3;
   static final PyCode roundfrac$4;
   static final PyCode fix$5;
   static final PyCode sci$6;
   static final PyCode test$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("General floating point formatting functions.\n\nFunctions:\nfix(x, digits_behind)\nsci(x, digits_behind)\n\nEach takes a number or a string and a number of digits as arguments.\n\nParameters:\nx:             number to be formatted; or a string resembling a number\ndigits_behind: number of digits behind the decimal point\n"));
      var1.setline(12);
      PyString.fromInterned("General floating point formatting functions.\n\nFunctions:\nfix(x, digits_behind)\nsci(x, digits_behind)\n\nEach takes a number or a string and a number of digits as arguments.\n\nParameters:\nx:             number to be formatted; or a string resembling a number\ndigits_behind: number of digits behind the decimal point\n");
      var1.setline(13);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var6 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(14);
      PyObject var10000 = var1.getname("warnpy3k");
      var6 = new PyObject[]{PyString.fromInterned("the fpformat module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var8 = new String[]{"stacklevel"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(15);
      var1.dellocal("warnpy3k");
      var1.setline(17);
      PyObject var7 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var7);
      var3 = null;
      var1.setline(19);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("fix"), PyString.fromInterned("sci"), PyString.fromInterned("NotANumber")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(22);
      var7 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([-+]?)0*(\\d*)((?:\\.\\d*)?)(([eE][-+]?\\d+)?)$"));
      var1.setlocal("decoder", var7);
      var3 = null;

      try {
         var1.setline(30);
         var6 = new PyObject[]{var1.getname("ValueError")};
         var4 = Py.makeClass("NotANumber", var6, NotANumber$1);
         var1.setlocal("NotANumber", var4);
         var4 = null;
         Arrays.fill(var6, (Object)null);
      } catch (Throwable var5) {
         PyException var11 = Py.setException(var5, var1);
         if (!var11.match(var1.getname("TypeError"))) {
            throw var11;
         }

         var1.setline(33);
         PyString var10 = PyString.fromInterned("fpformat.NotANumber");
         var1.setlocal("NotANumber", var10);
         var4 = null;
      }

      var1.setline(35);
      var6 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var6, extract$2, PyString.fromInterned("Return (sign, intpart, fraction, expo) or raise an exception:\n    sign is '+' or '-'\n    intpart is 0 or more digits beginning with a nonzero\n    fraction is 0 or more digits\n    expo is an integer"));
      var1.setlocal("extract", var12);
      var3 = null;
      var1.setline(50);
      var6 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var6, unexpo$3, PyString.fromInterned("Remove the exponent by changing intpart and fraction."));
      var1.setlocal("unexpo", var12);
      var3 = null;
      var1.setline(64);
      var6 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var6, roundfrac$4, PyString.fromInterned("Round or extend the fraction to size digs."));
      var1.setlocal("roundfrac", var12);
      var3 = null;
      var1.setline(90);
      var6 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var6, fix$5, PyString.fromInterned("Format x as [-]ddd.ddd with 'digs' digits after the point\n    and at least one digit before.\n    If digs <= 0, the point is suppressed."));
      var1.setlocal("fix", var12);
      var3 = null;
      var1.setline(106);
      var6 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var6, sci$6, PyString.fromInterned("Format x as [-]d.dddE[+-]ddd with 'digs' digits after the point\n    and exactly one digit before.\n    If digs is <= 0, one digit is kept and the point is suppressed."));
      var1.setlocal("sci", var12);
      var3 = null;
      var1.setline(138);
      var6 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var6, test$7, PyString.fromInterned("Interactive test run."));
      var1.setlocal("test", var12);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NotANumber$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      return var1.getf_locals();
   }

   public PyObject extract$2(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyString.fromInterned("Return (sign, intpart, fraction, expo) or raise an exception:\n    sign is '+' or '-'\n    intpart is 0 or more digits beginning with a nonzero\n    fraction is 0 or more digits\n    expo is an integer");
      var1.setline(41);
      PyObject var3 = var1.getglobal("decoder").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(42);
         throw Py.makeException(var1.getglobal("NotANumber"), var1.getlocal(0));
      } else {
         var1.setline(43);
         var3 = var1.getlocal(1).__getattr__("group").__call__(var2, Py.newInteger(1), Py.newInteger(2), Py.newInteger(3), Py.newInteger(4));
         PyObject[] var4 = Py.unpackSequence(var3, 4);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(44);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("+"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(44);
            PyString var6 = PyString.fromInterned("");
            var1.setlocal(2, var6);
            var3 = null;
         }

         var1.setline(45);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(45);
            var3 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(46);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(46);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(47);
            PyInteger var7 = Py.newInteger(0);
            var1.setlocal(6, var7);
            var3 = null;
         }

         var1.setline(48);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)});
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject unexpo$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Remove the exponent by changing intpart and fraction.");
      var1.setline(52);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(53);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(54);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(0)._add(var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)), var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)});
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
         var1.setline(55);
         var3 = var1.getlocal(2);
         var10000 = var3._gt(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(56);
            var3 = var1.getlocal(0)._add(PyString.fromInterned("0")._mul(var1.getlocal(2)._sub(var1.getlocal(3))));
            var1.setlocal(0, var3);
            var3 = null;
         }
      } else {
         var1.setline(57);
         var3 = var1.getlocal(2);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(58);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(59);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)._add(var1.getlocal(1))});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(0, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(1, var5);
            var5 = null;
            var3 = null;
            var1.setline(60);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getlocal(4).__neg__());
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(61);
               var3 = PyString.fromInterned("0")._mul(var1.getlocal(2).__neg__()._sub(var1.getlocal(4)))._add(var1.getlocal(1));
               var1.setlocal(1, var3);
               var3 = null;
            }
         }
      }

      var1.setline(62);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject roundfrac$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyString.fromInterned("Round or extend the fraction to size digs.");
      var1.setline(66);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._le(var1.getlocal(2));
      var3 = null;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)._add(PyString.fromInterned("0")._mul(var1.getlocal(2)._sub(var1.getlocal(3))))});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(69);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(70);
         var4 = var1.getlocal(4)._add(var1.getlocal(2));
         var10000 = var4._lt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(71);
            var7 = new PyTuple(new PyObject[]{PyString.fromInterned("0")._mul(var1.getlocal(2).__neg__()), PyString.fromInterned("")});
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(72);
            var4 = var1.getlocal(0)._add(var1.getlocal(1));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(73);
            var4 = var1.getlocal(5).__getitem__(var1.getlocal(4)._add(var1.getlocal(2)));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(74);
            var4 = var1.getlocal(6);
            var10000 = var4._ge(PyString.fromInterned("5"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(75);
               var4 = var1.getlocal(4)._add(var1.getlocal(2))._sub(Py.newInteger(1));
               var1.setlocal(7, var4);
               var4 = null;

               while(true) {
                  var1.setline(76);
                  var4 = var1.getlocal(7);
                  var10000 = var4._ge(Py.newInteger(0));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(80);
                     var4 = PyString.fromInterned("0")._add(var1.getlocal(5));
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(81);
                     var4 = var1.getlocal(4)._add(Py.newInteger(1));
                     var1.setlocal(4, var4);
                     var4 = null;
                     var1.setline(82);
                     PyInteger var8 = Py.newInteger(0);
                     var1.setlocal(7, var8);
                     var4 = null;
                     break;
                  }

                  var1.setline(77);
                  var4 = var1.getlocal(5).__getitem__(var1.getlocal(7));
                  var10000 = var4._ne(PyString.fromInterned("9"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(78);
                  var4 = var1.getlocal(7)._sub(Py.newInteger(1));
                  var1.setlocal(7, var4);
                  var4 = null;
               }

               var1.setline(83);
               var4 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null)._add(var1.getglobal("chr").__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(5).__getitem__(var1.getlocal(7)))._add(Py.newInteger(1))))._add(PyString.fromInterned("0")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(5))._sub(var1.getlocal(7))._sub(Py.newInteger(1))));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(84);
               PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null), var1.getlocal(5).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null)});
               PyObject[] var5 = Py.unpackSequence(var9, 2);
               PyObject var6 = var5[0];
               var1.setlocal(0, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(1, var6);
               var6 = null;
               var4 = null;
            }

            var1.setline(85);
            var4 = var1.getlocal(2);
            var10000 = var4._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(86);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)});
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(88);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)._add(PyString.fromInterned("0")._mul(var1.getlocal(2).__neg__())), PyString.fromInterned("")});
               var1.f_lasti = -1;
               return var7;
            }
         }
      }
   }

   public PyObject fix$5(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("Format x as [-]ddd.ddd with 'digs' digits after the point\n    and at least one digit before.\n    If digs <= 0, the point is suppressed.");
      var1.setline(94);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ne(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      PyObject var4;
      try {
         var1.setline(96);
         var3 = var1.getglobal("extract").__call__(var2, var1.getlocal(0));
         PyObject[] var8 = Py.unpackSequence(var3, 4);
         PyObject var5 = var8[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var8[2];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var8[3];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         PyException var9 = Py.setException(var7, var1);
         if (var9.match(var1.getglobal("NotANumber"))) {
            var1.setline(98);
            var4 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var9;
      }

      var1.setline(99);
      var3 = var1.getglobal("unexpo").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
      PyObject[] var10 = Py.unpackSequence(var3, 2);
      PyObject var6 = var10[0];
      var1.setlocal(3, var6);
      var6 = null;
      var6 = var10[1];
      var1.setlocal(4, var6);
      var6 = null;
      var3 = null;
      var1.setline(100);
      var3 = var1.getglobal("roundfrac").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(1));
      var10 = Py.unpackSequence(var3, 2);
      var6 = var10[0];
      var1.setlocal(3, var6);
      var6 = null;
      var6 = var10[1];
      var1.setlocal(4, var6);
      var6 = null;
      var3 = null;

      while(true) {
         var1.setline(101);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(PyString.fromInterned("0"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(102);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(102);
               PyString var11 = PyString.fromInterned("0");
               var1.setlocal(3, var11);
               var3 = null;
            }

            var1.setline(103);
            var3 = var1.getlocal(1);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(103);
               var4 = var1.getlocal(2)._add(var1.getlocal(3))._add(PyString.fromInterned("."))._add(var1.getlocal(4));
               var1.f_lasti = -1;
               return var4;
            }

            var1.setline(104);
            var4 = var1.getlocal(2)._add(var1.getlocal(3));
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(101);
         var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject sci$6(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("Format x as [-]d.dddE[+-]ddd with 'digs' digits after the point\n    and exactly one digit before.\n    If digs is <= 0, one digit is kept and the point is suppressed.");
      var1.setline(110);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ne(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(110);
         var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(111);
      var3 = var1.getglobal("extract").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(112);
      PyTuple var6;
      if (var1.getlocal(3).__not__().__nonzero__()) {
         while(true) {
            var1.setline(113);
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
               var10000 = var3._eq(PyString.fromInterned("0"));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(116);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(117);
                  var6 = new PyTuple(new PyObject[]{var1.getlocal(4).__getitem__(Py.newInteger(0)), var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
                  var4 = Py.unpackSequence(var6, 2);
                  var5 = var4[0];
                  var1.setlocal(3, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(4, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(118);
                  var3 = var1.getlocal(5)._sub(Py.newInteger(1));
                  var1.setlocal(5, var3);
                  var3 = null;
               } else {
                  var1.setline(120);
                  PyString var7 = PyString.fromInterned("0");
                  var1.setlocal(3, var7);
                  var3 = null;
               }
               break;
            }

            var1.setline(114);
            var3 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(115);
            var3 = var1.getlocal(5)._sub(Py.newInteger(1));
            var1.setlocal(5, var3);
            var3 = null;
         }
      } else {
         var1.setline(122);
         var3 = var1.getlocal(5)._add(var1.getglobal("len").__call__(var2, var1.getlocal(3)))._sub(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(123);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)._add(var1.getlocal(4))});
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(124);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getglobal("roundfrac").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(1));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(126);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)._add(var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)), var1.getlocal(5)._add(var1.getglobal("len").__call__(var2, var1.getlocal(3)))._sub(Py.newInteger(1))});
         var4 = Py.unpackSequence(var6, 3);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(130);
      var3 = var1.getlocal(2)._add(var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(1);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(131);
         var3 = var1.getlocal(6)._add(PyString.fromInterned("."))._add(var1.getlocal(4));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(132);
      var3 = var1.getglobal("repr").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(5)));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(133);
      var3 = PyString.fromInterned("0")._mul(Py.newInteger(3)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(7))))._add(var1.getlocal(7));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getlocal(5);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(134);
         var3 = PyString.fromInterned("-")._add(var1.getlocal(7));
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(135);
         var3 = PyString.fromInterned("+")._add(var1.getlocal(7));
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(136);
      var3 = var1.getlocal(6)._add(PyString.fromInterned("e"))._add(var1.getlocal(7));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$7(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyString.fromInterned("Interactive test run.");

      PyException var3;
      try {
         while(true) {
            var1.setline(141);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(142);
            PyObject var7 = var1.getglobal("input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Enter (x, digs): "));
            PyObject[] var4 = Py.unpackSequence(var7, 2);
            PyObject var5 = var4[0];
            var1.setlocal(0, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(1, var5);
            var5 = null;
            var3 = null;
            var1.setline(143);
            Py.printComma(var1.getlocal(0));
            Py.printComma(var1.getglobal("fix").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
            Py.println(var1.getglobal("sci").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
         }
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(new PyTuple(new PyObject[]{var1.getglobal("EOFError"), var1.getglobal("KeyboardInterrupt")}))) {
            throw var3;
         }

         var1.setline(145);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public fpformat$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NotANumber$1 = Py.newCode(0, var2, var1, "NotANumber", 30, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "res", "sign", "intpart", "fraction", "exppart", "expo"};
      extract$2 = Py.newCode(1, var2, var1, "extract", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"intpart", "fraction", "expo", "f", "i"};
      unexpo$3 = Py.newCode(3, var2, var1, "unexpo", 50, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"intpart", "fraction", "digs", "f", "i", "total", "nextdigit", "n"};
      roundfrac$4 = Py.newCode(3, var2, var1, "roundfrac", 64, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "digs", "sign", "intpart", "fraction", "expo"};
      fix$5 = Py.newCode(2, var2, var1, "fix", 90, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "digs", "sign", "intpart", "fraction", "expo", "s", "e"};
      sci$6 = Py.newCode(2, var2, var1, "sci", 106, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "digs"};
      test$7 = Py.newCode(0, var2, var1, "test", 138, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fpformat$py("fpformat$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fpformat$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NotANumber$1(var2, var3);
         case 2:
            return this.extract$2(var2, var3);
         case 3:
            return this.unexpo$3(var2, var3);
         case 4:
            return this.roundfrac$4(var2, var3);
         case 5:
            return this.fix$5(var2, var3);
         case 6:
            return this.sci$6(var2, var3);
         case 7:
            return this.test$7(var2, var3);
         default:
            return null;
      }
   }
}
