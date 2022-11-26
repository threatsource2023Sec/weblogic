package email;

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
@Filename("email/_parseaddr.py")
public class _parseaddr$py extends PyFunctionTable implements PyRunnable {
   static _parseaddr$py self;
   static final PyCode f$0;
   static final PyCode parsedate_tz$1;
   static final PyCode parsedate$2;
   static final PyCode mktime_tz$3;
   static final PyCode quote$4;
   static final PyCode AddrlistClass$5;
   static final PyCode __init__$6;
   static final PyCode gotonext$7;
   static final PyCode getaddrlist$8;
   static final PyCode getaddress$9;
   static final PyCode getrouteaddr$10;
   static final PyCode getaddrspec$11;
   static final PyCode getdomain$12;
   static final PyCode getdelimited$13;
   static final PyCode getquote$14;
   static final PyCode getcomment$15;
   static final PyCode getdomainliteral$16;
   static final PyCode getatom$17;
   static final PyCode getphraselist$18;
   static final PyCode AddressList$19;
   static final PyCode __init__$20;
   static final PyCode __len__$21;
   static final PyCode __add__$22;
   static final PyCode __iadd__$23;
   static final PyCode __sub__$24;
   static final PyCode __isub__$25;
   static final PyCode __getitem__$26;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Email address parsing code.\n\nLifted directly from rfc822.py.  This should eventually be rewritten.\n"));
      var1.setline(7);
      PyString.fromInterned("Email address parsing code.\n\nLifted directly from rfc822.py.  This should eventually be rewritten.\n");
      var1.setline(9);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("mktime_tz"), PyString.fromInterned("parsedate"), PyString.fromInterned("parsedate_tz"), PyString.fromInterned("quote")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(16);
      PyObject var5 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var5);
      var3 = null;
      var5 = imp.importOne("calendar", var1, -1);
      var1.setlocal("calendar", var5);
      var3 = null;
      var1.setline(18);
      PyString var6 = PyString.fromInterned(" ");
      var1.setlocal("SPACE", var6);
      var3 = null;
      var1.setline(19);
      var6 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var6);
      var3 = null;
      var1.setline(20);
      var6 = PyString.fromInterned(", ");
      var1.setlocal("COMMASPACE", var6);
      var3 = null;
      var1.setline(23);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("jan"), PyString.fromInterned("feb"), PyString.fromInterned("mar"), PyString.fromInterned("apr"), PyString.fromInterned("may"), PyString.fromInterned("jun"), PyString.fromInterned("jul"), PyString.fromInterned("aug"), PyString.fromInterned("sep"), PyString.fromInterned("oct"), PyString.fromInterned("nov"), PyString.fromInterned("dec"), PyString.fromInterned("january"), PyString.fromInterned("february"), PyString.fromInterned("march"), PyString.fromInterned("april"), PyString.fromInterned("may"), PyString.fromInterned("june"), PyString.fromInterned("july"), PyString.fromInterned("august"), PyString.fromInterned("september"), PyString.fromInterned("october"), PyString.fromInterned("november"), PyString.fromInterned("december")});
      var1.setlocal("_monthnames", var3);
      var3 = null;
      var1.setline(28);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("mon"), PyString.fromInterned("tue"), PyString.fromInterned("wed"), PyString.fromInterned("thu"), PyString.fromInterned("fri"), PyString.fromInterned("sat"), PyString.fromInterned("sun")});
      var1.setlocal("_daynames", var3);
      var3 = null;
      var1.setline(36);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("UT"), Py.newInteger(0), PyString.fromInterned("UTC"), Py.newInteger(0), PyString.fromInterned("GMT"), Py.newInteger(0), PyString.fromInterned("Z"), Py.newInteger(0), PyString.fromInterned("AST"), Py.newInteger(-400), PyString.fromInterned("ADT"), Py.newInteger(-300), PyString.fromInterned("EST"), Py.newInteger(-500), PyString.fromInterned("EDT"), Py.newInteger(-400), PyString.fromInterned("CST"), Py.newInteger(-600), PyString.fromInterned("CDT"), Py.newInteger(-500), PyString.fromInterned("MST"), Py.newInteger(-700), PyString.fromInterned("MDT"), Py.newInteger(-600), PyString.fromInterned("PST"), Py.newInteger(-800), PyString.fromInterned("PDT"), Py.newInteger(-700)});
      var1.setlocal("_timezones", var7);
      var3 = null;
      var1.setline(45);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, parsedate_tz$1, PyString.fromInterned("Convert a date string to a time tuple.\n\n    Accounts for military timezones.\n    "));
      var1.setlocal("parsedate_tz", var9);
      var3 = null;
      var1.setline(143);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, parsedate$2, PyString.fromInterned("Convert a time string to a time tuple."));
      var1.setlocal("parsedate", var9);
      var3 = null;
      var1.setline(152);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, mktime_tz$3, PyString.fromInterned("Turn a 10-tuple as returned by parsedate_tz() into a POSIX timestamp."));
      var1.setlocal("mktime_tz", var9);
      var3 = null;
      var1.setline(162);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, quote$4, PyString.fromInterned("Prepare string to be used in a quoted string.\n\n    Turns backslash and double quote characters into quoted pairs.  These\n    are the only characters that need to be quoted inside a quoted string.\n    Does not add the surrounding double quotes.\n    "));
      var1.setlocal("quote", var9);
      var3 = null;
      var1.setline(172);
      var8 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("AddrlistClass", var8, AddrlistClass$5);
      var1.setlocal("AddrlistClass", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(452);
      var8 = new PyObject[]{var1.getname("AddrlistClass")};
      var4 = Py.makeClass("AddressList", var8, AddressList$19);
      var1.setlocal("AddressList", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parsedate_tz$1(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString.fromInterned("Convert a date string to a time tuple.\n\n    Accounts for military timezones.\n    ");
      var1.setline(50);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(53);
      PyObject var10000 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
         var10000 = var3._in(var1.getglobal("_daynames"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(55);
         var1.getlocal(0).__delitem__((PyObject)Py.newInteger(0));
      } else {
         var1.setline(57);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(58);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(59);
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var3);
            var3 = null;
         }
      }

      var1.setline(60);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(61);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(62);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(Py.newInteger(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(63);
            var3 = var1.getlocal(2)._add(var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(0, var3);
            var3 = null;
         }
      }

      var1.setline(64);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(3));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(66);
         var3 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(67);
         var3 = var1.getlocal(1);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(68);
            PyList var10 = new PyList(new PyObject[]{var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null), var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
            var1.getlocal(0).__setslice__(Py.newInteger(3), (PyObject)null, (PyObject)null, var10);
            var3 = null;
         } else {
            var1.setline(70);
            var1.getlocal(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
         }
      }

      var1.setline(71);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var10000 = var3._lt(Py.newInteger(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(72);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(73);
         PyObject var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(74);
         var4 = var1.getlocal(0);
         PyObject[] var5 = Py.unpackSequence(var4, 5);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[4];
         var1.setlocal(8, var6);
         var6 = null;
         var4 = null;
         var1.setline(75);
         var4 = var1.getlocal(5).__getattr__("lower").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(76);
         var4 = var1.getlocal(5);
         var10000 = var4._notin(var1.getglobal("_monthnames"));
         var4 = null;
         PyTuple var9;
         if (var10000.__nonzero__()) {
            var1.setline(77);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4).__getattr__("lower").__call__(var2)});
            var5 = Py.unpackSequence(var9, 2);
            var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var4 = null;
            var1.setline(78);
            var4 = var1.getlocal(5);
            var10000 = var4._notin(var1.getglobal("_monthnames"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(79);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(80);
         var4 = var1.getglobal("_monthnames").__getattr__("index").__call__(var2, var1.getlocal(5))._add(Py.newInteger(1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(81);
         var4 = var1.getlocal(5);
         var10000 = var4._gt(Py.newInteger(12));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(82);
            var4 = var1.getlocal(5);
            var4 = var4._isub(Py.newInteger(12));
            var1.setlocal(5, var4);
         }

         var1.setline(83);
         var4 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
         var10000 = var4._eq(PyString.fromInterned(","));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(84);
            var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(85);
         var4 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(86);
         var4 = var1.getlocal(1);
         var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(87);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(6)});
            var5 = Py.unpackSequence(var9, 2);
            var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
         }

         var1.setline(88);
         var4 = var1.getlocal(6).__getitem__(Py.newInteger(-1));
         var10000 = var4._eq(PyString.fromInterned(","));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(89);
            var4 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(6, var4);
            var4 = null;
         }

         var1.setline(90);
         if (var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("isdigit").__call__(var2).__not__().__nonzero__()) {
            var1.setline(91);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(6)});
            var5 = Py.unpackSequence(var9, 2);
            var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
         }

         var1.setline(92);
         var4 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
         var10000 = var4._eq(PyString.fromInterned(","));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(93);
            var4 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(7, var4);
            var4 = null;
         }

         var1.setline(94);
         var4 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(95);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
         var10000 = var4._eq(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(96);
            var4 = var1.getlocal(7);
            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var4 = null;
            var1.setline(97);
            PyString var12 = PyString.fromInterned("0");
            var1.setlocal(11, var12);
            var4 = null;
         } else {
            var1.setline(98);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var10000 = var4._eq(Py.newInteger(3));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(101);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(99);
            var4 = var1.getlocal(7);
            var5 = Py.unpackSequence(var4, 3);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(11, var6);
            var6 = null;
            var4 = null;
         }

         PyException var13;
         try {
            var1.setline(103);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(104);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(105);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(9));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(106);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(10));
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(107);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(11));
            var1.setlocal(11, var4);
            var4 = null;
         } catch (Throwable var7) {
            var13 = Py.setException(var7, var1);
            if (var13.match(var1.getglobal("ValueError"))) {
               var1.setline(109);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            throw var13;
         }

         var1.setline(115);
         var4 = var1.getlocal(6);
         var10000 = var4._lt(Py.newInteger(100));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(117);
            var4 = var1.getlocal(6);
            var10000 = var4._gt(Py.newInteger(68));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(118);
               var4 = var1.getlocal(6);
               var4 = var4._iadd(Py.newInteger(1900));
               var1.setlocal(6, var4);
            } else {
               var1.setline(121);
               var4 = var1.getlocal(6);
               var4 = var4._iadd(Py.newInteger(2000));
               var1.setlocal(6, var4);
            }
         }

         var1.setline(122);
         var4 = var1.getglobal("None");
         var1.setlocal(12, var4);
         var4 = null;
         var1.setline(123);
         var4 = var1.getlocal(8).__getattr__("upper").__call__(var2);
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(124);
         var4 = var1.getlocal(8);
         var10000 = var4._in(var1.getglobal("_timezones"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(125);
            var4 = var1.getglobal("_timezones").__getitem__(var1.getlocal(8));
            var1.setlocal(12, var4);
            var4 = null;
         } else {
            try {
               var1.setline(128);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
               var1.setlocal(12, var4);
               var4 = null;
            } catch (Throwable var8) {
               var13 = Py.setException(var8, var1);
               if (!var13.match(var1.getglobal("ValueError"))) {
                  throw var13;
               }

               var1.setline(130);
            }
         }

         var1.setline(132);
         if (var1.getlocal(12).__nonzero__()) {
            var1.setline(133);
            var4 = var1.getlocal(12);
            var10000 = var4._lt(Py.newInteger(0));
            var4 = null;
            PyInteger var14;
            if (var10000.__nonzero__()) {
               var1.setline(134);
               var14 = Py.newInteger(-1);
               var1.setlocal(13, var14);
               var4 = null;
               var1.setline(135);
               var4 = var1.getlocal(12).__neg__();
               var1.setlocal(12, var4);
               var4 = null;
            } else {
               var1.setline(137);
               var14 = Py.newInteger(1);
               var1.setlocal(13, var14);
               var4 = null;
            }

            var1.setline(138);
            var4 = var1.getlocal(13)._mul(var1.getlocal(12)._floordiv(Py.newInteger(100))._mul(Py.newInteger(3600))._add(var1.getlocal(12)._mod(Py.newInteger(100))._mul(Py.newInteger(60))));
            var1.setlocal(12, var4);
            var4 = null;
         }

         var1.setline(140);
         PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(4), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), Py.newInteger(0), Py.newInteger(1), Py.newInteger(-1), var1.getlocal(12)});
         var1.f_lasti = -1;
         return var11;
      }
   }

   public PyObject parsedate$2(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyString.fromInterned("Convert a time string to a time tuple.");
      var1.setline(145);
      PyObject var3 = var1.getglobal("parsedate_tz").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(146);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(147);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(149);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject mktime_tz$3(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Turn a 10-tuple as returned by parsedate_tz() into a POSIX timestamp.");
      var1.setline(154);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(9));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(156);
         var3 = var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null)._add(new PyTuple(new PyObject[]{Py.newInteger(-1)})));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(158);
         PyObject var4 = var1.getglobal("calendar").__getattr__("timegm").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(159);
         var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getitem__(Py.newInteger(9)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject quote$4(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyString.fromInterned("Prepare string to be used in a quoted string.\n\n    Turns backslash and double quote characters into quoted pairs.  These\n    are the only characters that need to be quoted inside a quoted string.\n    Does not add the surrounding double quotes.\n    ");
      var1.setline(169);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\\\")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\\\""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AddrlistClass$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Address parser class by Ben Escoto.\n\n    To understand what this class does, it helps to have a copy of RFC 2822 in\n    front of you.\n\n    Note: this class interface is deprecated and may be removed in the future.\n    Use rfc822.AddressList instead.\n    "));
      var1.setline(180);
      PyString.fromInterned("Address parser class by Ben Escoto.\n\n    To understand what this class does, it helps to have a copy of RFC 2822 in\n    front of you.\n\n    Note: this class interface is deprecated and may be removed in the future.\n    Use rfc822.AddressList instead.\n    ");
      var1.setline(182);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, PyString.fromInterned("Initialize a new instance.\n\n        `field' is an unparsed address header field, containing\n        one or more addresses.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gotonext$7, PyString.fromInterned("Parse up to the start of the next address."));
      var1.setlocal("gotonext", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddrlist$8, PyString.fromInterned("Parse all addresses.\n\n        Returns a list containing all of the addresses.\n        "));
      var1.setlocal("getaddrlist", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddress$9, PyString.fromInterned("Parse the next address."));
      var1.setlocal("getaddress", var4);
      var3 = null;
      var1.setline(284);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getrouteaddr$10, PyString.fromInterned("Parse a route address (Return-path value).\n\n        This method just skips all the route stuff and returns the addrspec.\n        "));
      var1.setlocal("getrouteaddr", var4);
      var3 = null;
      var1.setline(316);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddrspec$11, PyString.fromInterned("Parse an RFC 2822 addr-spec."));
      var1.setlocal("getaddrspec", var4);
      var3 = null;
      var1.setline(341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdomain$12, PyString.fromInterned("Get the complete domain name from an address."));
      var1.setlocal("getdomain", var4);
      var3 = null;
      var1.setline(360);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, getdelimited$13, PyString.fromInterned("Parse a header fragment delimited by special characters.\n\n        `beginchar' is the start character for the fragment.\n        If self is not looking at an instance of `beginchar' then\n        getdelimited returns the empty string.\n\n        `endchars' is a sequence of allowable end-delimiting characters.\n        Parsing stops when one of these is encountered.\n\n        If `allowcomments' is non-zero, embedded RFC 2822 comments are allowed\n        within the parsed fragment.\n        "));
      var1.setlocal("getdelimited", var4);
      var3 = null;
      var1.setline(397);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getquote$14, PyString.fromInterned("Get a quote-delimited fragment from self's field."));
      var1.setlocal("getquote", var4);
      var3 = null;
      var1.setline(401);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcomment$15, PyString.fromInterned("Get a parenthesis-delimited fragment from self's field."));
      var1.setlocal("getcomment", var4);
      var3 = null;
      var1.setline(405);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdomainliteral$16, PyString.fromInterned("Parse an RFC 2822 domain-literal."));
      var1.setlocal("getdomainliteral", var4);
      var3 = null;
      var1.setline(409);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getatom$17, PyString.fromInterned("Parse an RFC 2822 atom.\n\n        Optional atomends specifies a different set of end token delimiters\n        (the default is to use self.atomends).  This is used e.g. in\n        getphraselist() since phrase endings must not include the `.' (which\n        is legal in phrases)."));
      var1.setlocal("getatom", var4);
      var3 = null;
      var1.setline(429);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getphraselist$18, PyString.fromInterned("Parse a sequence of RFC 2822 phrases.\n\n        A phrase is a sequence of words, which are in turn either RFC 2822\n        atoms or quoted-strings.  Phrases are canonicalized by squeezing all\n        runs of continuous whitespace into one space.\n        "));
      var1.setlocal("getphraselist", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Initialize a new instance.\n\n        `field' is an unparsed address header field, containing\n        one or more addresses.\n        ");
      var1.setline(188);
      PyString var3 = PyString.fromInterned("()<>@,:;.\"[]");
      var1.getlocal(0).__setattr__((String)"specials", var3);
      var3 = null;
      var1.setline(189);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"pos", var4);
      var3 = null;
      var1.setline(190);
      var3 = PyString.fromInterned(" \t");
      var1.getlocal(0).__setattr__((String)"LWS", var3);
      var3 = null;
      var1.setline(191);
      var3 = PyString.fromInterned("\r\n");
      var1.getlocal(0).__setattr__((String)"CR", var3);
      var3 = null;
      var1.setline(192);
      PyObject var5 = var1.getlocal(0).__getattr__("LWS")._add(var1.getlocal(0).__getattr__("CR"));
      var1.getlocal(0).__setattr__("FWS", var5);
      var3 = null;
      var1.setline(193);
      var5 = var1.getlocal(0).__getattr__("specials")._add(var1.getlocal(0).__getattr__("LWS"))._add(var1.getlocal(0).__getattr__("CR"));
      var1.getlocal(0).__setattr__("atomends", var5);
      var3 = null;
      var1.setline(197);
      var5 = var1.getlocal(0).__getattr__("atomends").__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)PyString.fromInterned(""));
      var1.getlocal(0).__setattr__("phraseends", var5);
      var3 = null;
      var1.setline(198);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("field", var5);
      var3 = null;
      var1.setline(199);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commentlist", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gotonext$7(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyString.fromInterned("Parse up to the start of the next address.");

      while(true) {
         var1.setline(203);
         PyObject var3 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(204);
         var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var3._in(var1.getlocal(0).__getattr__("LWS")._add(PyString.fromInterned("\n\r")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(205);
            var10000 = var1.getlocal(0);
            String var6 = "pos";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var6);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var6, var5);
         } else {
            var1.setline(206);
            var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var3._eq(PyString.fromInterned("("));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(207);
            var1.getlocal(0).__getattr__("commentlist").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getaddrlist$8(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyString.fromInterned("Parse all addresses.\n\n        Returns a list containing all of the addresses.\n        ");
      var1.setline(216);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(217);
         PyObject var4 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(223);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(218);
         var4 = var1.getlocal(0).__getattr__("getaddress").__call__(var2);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(219);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(220);
            var4 = var1.getlocal(1);
            var4 = var4._iadd(var1.getlocal(2));
            var1.setlocal(1, var4);
         } else {
            var1.setline(222);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")})));
         }
      }
   }

   public PyObject getaddress$9(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyString.fromInterned("Parse the next address.");
      var1.setline(227);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commentlist", var3);
      var3 = null;
      var1.setline(228);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      var1.setline(230);
      PyObject var6 = var1.getlocal(0).__getattr__("pos");
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(231);
      var6 = var1.getlocal(0).__getattr__("commentlist");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(232);
      var6 = var1.getlocal(0).__getattr__("getphraselist").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(234);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      var1.setline(235);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(237);
      var6 = var1.getlocal(0).__getattr__("pos");
      PyObject var10000 = var6._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
      var3 = null;
      PyObject var4;
      PyObject var5;
      String var7;
      if (var10000.__nonzero__()) {
         var1.setline(239);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(240);
            var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("SPACE").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")), var1.getlocal(3).__getitem__(Py.newInteger(0))})});
            var1.setlocal(4, var3);
            var3 = null;
         }
      } else {
         var1.setline(242);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(PyString.fromInterned(".@"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(245);
            var6 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("pos", var6);
            var3 = null;
            var1.setline(246);
            var6 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("commentlist", var6);
            var3 = null;
            var1.setline(247);
            var6 = var1.getlocal(0).__getattr__("getaddrspec").__call__(var2);
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(248);
            var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("SPACE").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")), var1.getlocal(5)})});
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(250);
            var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var6._eq(PyString.fromInterned(":"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(252);
               var3 = new PyList(Py.EmptyObjects);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(254);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field"));
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(255);
               var10000 = var1.getlocal(0);
               var7 = "pos";
               var4 = var10000;
               var5 = var4.__getattr__(var7);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setattr__(var7, var5);

               while(true) {
                  var1.setline(256);
                  var6 = var1.getlocal(0).__getattr__("pos");
                  var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(257);
                  var1.getlocal(0).__getattr__("gotonext").__call__(var2);
                  var1.setline(258);
                  var6 = var1.getlocal(0).__getattr__("pos");
                  var10000 = var6._lt(var1.getlocal(6));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                     var10000 = var6._eq(PyString.fromInterned(";"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(259);
                     var10000 = var1.getlocal(0);
                     var7 = "pos";
                     var4 = var10000;
                     var5 = var4.__getattr__(var7);
                     var5 = var5._iadd(Py.newInteger(1));
                     var4.__setattr__(var7, var5);
                     break;
                  }

                  var1.setline(261);
                  var6 = var1.getlocal(4)._add(var1.getlocal(0).__getattr__("getaddress").__call__(var2));
                  var1.setlocal(4, var6);
                  var3 = null;
               }
            } else {
               var1.setline(263);
               var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var6._eq(PyString.fromInterned("<"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(265);
                  var6 = var1.getlocal(0).__getattr__("getrouteaddr").__call__(var2);
                  var1.setlocal(7, var6);
                  var3 = null;
                  var1.setline(267);
                  if (var1.getlocal(0).__getattr__("commentlist").__nonzero__()) {
                     var1.setline(268);
                     var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("SPACE").__getattr__("join").__call__(var2, var1.getlocal(3))._add(PyString.fromInterned(" ("))._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")))._add(PyString.fromInterned(")")), var1.getlocal(7)})});
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(271);
                     var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("SPACE").__getattr__("join").__call__(var2, var1.getlocal(3)), var1.getlocal(7)})});
                     var1.setlocal(4, var3);
                     var3 = null;
                  }
               } else {
                  var1.setline(274);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(275);
                     var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("SPACE").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")), var1.getlocal(3).__getitem__(Py.newInteger(0))})});
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(276);
                     var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                     var10000 = var6._in(var1.getlocal(0).__getattr__("specials"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(277);
                        var10000 = var1.getlocal(0);
                        var7 = "pos";
                        var4 = var10000;
                        var5 = var4.__getattr__(var7);
                        var5 = var5._iadd(Py.newInteger(1));
                        var4.__setattr__(var7, var5);
                     }
                  }
               }
            }
         }
      }

      var1.setline(279);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      var1.setline(280);
      var6 = var1.getlocal(0).__getattr__("pos");
      var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._eq(PyString.fromInterned(","));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(281);
         var10000 = var1.getlocal(0);
         var7 = "pos";
         var4 = var10000;
         var5 = var4.__getattr__(var7);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var7, var5);
      }

      var1.setline(282);
      var6 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getrouteaddr$10(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyString.fromInterned("Parse a route address (Return-path value).\n\n        This method just skips all the route stuff and returns the addrspec.\n        ");
      var1.setline(289);
      PyObject var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
      PyObject var10000 = var3._ne(PyString.fromInterned("<"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(292);
         var3 = var1.getglobal("False");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(293);
         var10000 = var1.getlocal(0);
         String var6 = "pos";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var6, var5);
         var1.setline(294);
         var1.getlocal(0).__getattr__("gotonext").__call__(var2);
         var1.setline(295);
         PyString var7 = PyString.fromInterned("");
         var1.setlocal(2, var7);
         var3 = null;

         while(true) {
            var1.setline(296);
            var3 = var1.getlocal(0).__getattr__("pos");
            var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(297);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(298);
               var1.getlocal(0).__getattr__("getdomain").__call__(var2);
               var1.setline(299);
               var3 = var1.getglobal("False");
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(300);
               var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var3._eq(PyString.fromInterned(">"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(301);
                  var10000 = var1.getlocal(0);
                  var6 = "pos";
                  var4 = var10000;
                  var5 = var4.__getattr__(var6);
                  var5 = var5._iadd(Py.newInteger(1));
                  var4.__setattr__(var6, var5);
                  break;
               }

               var1.setline(303);
               var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var3._eq(PyString.fromInterned("@"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(304);
                  var10000 = var1.getlocal(0);
                  var6 = "pos";
                  var4 = var10000;
                  var5 = var4.__getattr__(var6);
                  var5 = var5._iadd(Py.newInteger(1));
                  var4.__setattr__(var6, var5);
                  var1.setline(305);
                  var3 = var1.getglobal("True");
                  var1.setlocal(1, var3);
                  var3 = null;
               } else {
                  var1.setline(306);
                  var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var3._eq(PyString.fromInterned(":"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(309);
                     var3 = var1.getlocal(0).__getattr__("getaddrspec").__call__(var2);
                     var1.setlocal(2, var3);
                     var3 = null;
                     var1.setline(310);
                     var10000 = var1.getlocal(0);
                     var6 = "pos";
                     var4 = var10000;
                     var5 = var4.__getattr__(var6);
                     var5 = var5._iadd(Py.newInteger(1));
                     var4.__setattr__(var6, var5);
                     break;
                  }

                  var1.setline(307);
                  var10000 = var1.getlocal(0);
                  var6 = "pos";
                  var4 = var10000;
                  var5 = var4.__getattr__(var6);
                  var5 = var5._iadd(Py.newInteger(1));
                  var4.__setattr__(var6, var5);
               }
            }

            var1.setline(312);
            var1.getlocal(0).__getattr__("gotonext").__call__(var2);
         }

         var1.setline(314);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getaddrspec$11(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyString.fromInterned("Parse an RFC 2822 addr-spec.");
      var1.setline(318);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(320);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);

      PyObject var10000;
      PyObject var5;
      PyObject var7;
      while(true) {
         var1.setline(321);
         var7 = var1.getlocal(0).__getattr__("pos");
         var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(322);
         var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var7._eq(PyString.fromInterned("."));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(323);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setline(324);
            var10000 = var1.getlocal(0);
            String var9 = "pos";
            PyObject var4 = var10000;
            var5 = var4.__getattr__(var9);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var9, var5);
         } else {
            var1.setline(325);
            var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var7._eq(PyString.fromInterned("\""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(326);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("\"%s\"")._mod(var1.getglobal("quote").__call__(var2, var1.getlocal(0).__getattr__("getquote").__call__(var2))));
            } else {
               var1.setline(327);
               var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var7._in(var1.getlocal(0).__getattr__("atomends"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(330);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getatom").__call__(var2));
            }
         }

         var1.setline(331);
         var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      }

      var1.setline(333);
      var7 = var1.getlocal(0).__getattr__("pos");
      var10000 = var7._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var7._ne(PyString.fromInterned("@"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(334);
         var7 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(336);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"));
         var1.setline(337);
         var10000 = var1.getlocal(0);
         String var8 = "pos";
         var5 = var10000;
         PyObject var6 = var5.__getattr__(var8);
         var6 = var6._iadd(Py.newInteger(1));
         var5.__setattr__(var8, var6);
         var1.setline(338);
         var1.getlocal(0).__getattr__("gotonext").__call__(var2);
         var1.setline(339);
         var7 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(1))._add(var1.getlocal(0).__getattr__("getdomain").__call__(var2));
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject getdomain$12(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyString.fromInterned("Get the complete domain name from an address.");
      var1.setline(343);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var6;
      while(true) {
         var1.setline(344);
         var6 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(345);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(var1.getlocal(0).__getattr__("LWS"));
         var3 = null;
         PyObject var4;
         PyObject var5;
         String var7;
         if (var10000.__nonzero__()) {
            var1.setline(346);
            var10000 = var1.getlocal(0);
            var7 = "pos";
            var4 = var10000;
            var5 = var4.__getattr__(var7);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var7, var5);
         } else {
            var1.setline(347);
            var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var6._eq(PyString.fromInterned("("));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(348);
               var1.getlocal(0).__getattr__("commentlist").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
            } else {
               var1.setline(349);
               var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var6._eq(PyString.fromInterned("["));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(350);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getdomainliteral").__call__(var2));
               } else {
                  var1.setline(351);
                  var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var6._eq(PyString.fromInterned("."));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(352);
                     var10000 = var1.getlocal(0);
                     var7 = "pos";
                     var4 = var10000;
                     var5 = var4.__getattr__(var7);
                     var5 = var5._iadd(Py.newInteger(1));
                     var4.__setattr__(var7, var5);
                     var1.setline(353);
                     var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  } else {
                     var1.setline(354);
                     var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                     var10000 = var6._in(var1.getlocal(0).__getattr__("atomends"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(357);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getatom").__call__(var2));
                  }
               }
            }
         }
      }

      var1.setline(358);
      var6 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getdelimited$13(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyString.fromInterned("Parse a header fragment delimited by special characters.\n\n        `beginchar' is the start character for the fragment.\n        If self is not looking at an instance of `beginchar' then\n        getdelimited returns the empty string.\n\n        `endchars' is a sequence of allowable end-delimiting characters.\n        Parsing stops when one of these is encountered.\n\n        If `allowcomments' is non-zero, embedded RFC 2822 comments are allowed\n        within the parsed fragment.\n        ");
      var1.setline(373);
      PyObject var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(374);
         PyString var7 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(376);
         PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("")});
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(377);
         PyObject var8 = var1.getglobal("False");
         var1.setlocal(5, var8);
         var4 = null;
         var1.setline(378);
         var10000 = var1.getlocal(0);
         String var9 = "pos";
         PyObject var5 = var10000;
         PyObject var6 = var5.__getattr__(var9);
         var6 = var6._iadd(Py.newInteger(1));
         var5.__setattr__(var9, var6);

         while(true) {
            var1.setline(379);
            var8 = var1.getlocal(0).__getattr__("pos");
            var10000 = var8._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
            var4 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(380);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(381);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos")));
               var1.setline(382);
               var8 = var1.getglobal("False");
               var1.setlocal(5, var8);
               var4 = null;
            } else {
               var1.setline(383);
               var8 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var8._in(var1.getlocal(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(384);
                  var10000 = var1.getlocal(0);
                  var9 = "pos";
                  var5 = var10000;
                  var6 = var5.__getattr__(var9);
                  var6 = var6._iadd(Py.newInteger(1));
                  var5.__setattr__(var9, var6);
                  break;
               }

               var1.setline(386);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var8 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var8._eq(PyString.fromInterned("("));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(387);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
                  continue;
               }

               var1.setline(389);
               var8 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var8._eq(PyString.fromInterned("\\"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(390);
                  var8 = var1.getglobal("True");
                  var1.setlocal(5, var8);
                  var4 = null;
               } else {
                  var1.setline(392);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos")));
               }
            }

            var1.setline(393);
            var10000 = var1.getlocal(0);
            var9 = "pos";
            var5 = var10000;
            var6 = var5.__getattr__(var9);
            var6 = var6._iadd(Py.newInteger(1));
            var5.__setattr__(var9, var6);
         }

         var1.setline(395);
         var3 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getquote$14(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyString.fromInterned("Get a quote-delimited fragment from self's field.");
      var1.setline(399);
      PyObject var3 = var1.getlocal(0).__getattr__("getdelimited").__call__((ThreadState)var2, PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\"\r"), (PyObject)var1.getglobal("False"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcomment$15(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Get a parenthesis-delimited fragment from self's field.");
      var1.setline(403);
      PyObject var3 = var1.getlocal(0).__getattr__("getdelimited").__call__((ThreadState)var2, PyString.fromInterned("("), (PyObject)PyString.fromInterned(")\r"), (PyObject)var1.getglobal("True"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getdomainliteral$16(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyString.fromInterned("Parse an RFC 2822 domain-literal.");
      var1.setline(407);
      PyObject var3 = PyString.fromInterned("[%s]")._mod(var1.getlocal(0).__getattr__("getdelimited").__call__((ThreadState)var2, PyString.fromInterned("["), (PyObject)PyString.fromInterned("]\r"), (PyObject)var1.getglobal("False")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getatom$17(PyFrame var1, ThreadState var2) {
      var1.setline(415);
      PyString.fromInterned("Parse an RFC 2822 atom.\n\n        Optional atomends specifies a different set of end token delimiters\n        (the default is to use self.atomends).  This is used e.g. in\n        getphraselist() since phrase endings must not include the `.' (which\n        is legal in phrases).");
      var1.setline(416);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(417);
      PyObject var6 = var1.getlocal(1);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(418);
         var6 = var1.getlocal(0).__getattr__("atomends");
         var1.setlocal(1, var6);
         var3 = null;
      }

      while(true) {
         var1.setline(420);
         var6 = var1.getlocal(0).__getattr__("pos");
         var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(421);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(424);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos")));
         var1.setline(425);
         var10000 = var1.getlocal(0);
         String var7 = "pos";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var7);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var7, var5);
      }

      var1.setline(427);
      var6 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getphraselist$18(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyString.fromInterned("Parse a sequence of RFC 2822 phrases.\n\n        A phrase is a sequence of words, which are in turn either RFC 2822\n        atoms or quoted-strings.  Phrases are canonicalized by squeezing all\n        runs of continuous whitespace into one space.\n        ");
      var1.setline(436);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var6;
      while(true) {
         var1.setline(438);
         var6 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(439);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(var1.getlocal(0).__getattr__("FWS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(440);
            var10000 = var1.getlocal(0);
            String var7 = "pos";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var7);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var7, var5);
         } else {
            var1.setline(441);
            var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var6._eq(PyString.fromInterned("\""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(442);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getquote").__call__(var2));
            } else {
               var1.setline(443);
               var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var6._eq(PyString.fromInterned("("));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(444);
                  var1.getlocal(0).__getattr__("commentlist").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
               } else {
                  var1.setline(445);
                  var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var6._in(var1.getlocal(0).__getattr__("phraseends"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(448);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getatom").__call__(var2, var1.getlocal(0).__getattr__("phraseends")));
               }
            }
         }
      }

      var1.setline(450);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject AddressList$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An AddressList encapsulates a list of parsed RFC 2822 addresses."));
      var1.setline(453);
      PyString.fromInterned("An AddressList encapsulates a list of parsed RFC 2822 addresses.");
      var1.setline(454);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(461);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$21, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(464);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __add__$22, (PyObject)null);
      var1.setlocal("__add__", var4);
      var3 = null;
      var1.setline(473);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iadd__$23, (PyObject)null);
      var1.setlocal("__iadd__", var4);
      var3 = null;
      var1.setline(480);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __sub__$24, (PyObject)null);
      var1.setlocal("__sub__", var4);
      var3 = null;
      var1.setline(488);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __isub__$25, (PyObject)null);
      var1.setlocal("__isub__", var4);
      var3 = null;
      var1.setline(495);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$26, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      var1.getglobal("AddrlistClass").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(456);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(457);
         var3 = var1.getlocal(0).__getattr__("getaddrlist").__call__(var2);
         var1.getlocal(0).__setattr__("addresslist", var3);
         var3 = null;
      } else {
         var1.setline(459);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"addresslist", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$21(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("addresslist"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __add__$22(PyFrame var1, ThreadState var2) {
      var1.setline(466);
      PyObject var3 = var1.getglobal("AddressList").__call__(var2, var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(467);
      var3 = var1.getlocal(0).__getattr__("addresslist").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(2).__setattr__("addresslist", var3);
      var3 = null;
      var1.setline(468);
      var3 = var1.getlocal(1).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(468);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(471);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(469);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(470);
            var1.getlocal(2).__getattr__("addresslist").__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject __iadd__$23(PyFrame var1, ThreadState var2) {
      var1.setline(475);
      PyObject var3 = var1.getlocal(1).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(475);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(478);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(476);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(477);
            var1.getlocal(0).__getattr__("addresslist").__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject __sub__$24(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      PyObject var3 = var1.getglobal("AddressList").__call__(var2, var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(483);
      var3 = var1.getlocal(0).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(483);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(486);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(484);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(var1.getlocal(1).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(485);
            var1.getlocal(2).__getattr__("addresslist").__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject __isub__$25(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      PyObject var3 = var1.getlocal(1).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(490);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(493);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(491);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(492);
            var1.getlocal(0).__getattr__("addresslist").__getattr__("remove").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject __getitem__$26(PyFrame var1, ThreadState var2) {
      var1.setline(497);
      PyObject var3 = var1.getlocal(0).__getattr__("addresslist").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public _parseaddr$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"data", "i", "stuff", "s", "dd", "mm", "yy", "tm", "tz", "thh", "tmm", "tss", "tzoffset", "tzsign"};
      parsedate_tz$1 = Py.newCode(1, var2, var1, "parsedate_tz", 45, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "t"};
      parsedate$2 = Py.newCode(1, var2, var1, "parsedate", 143, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "t"};
      mktime_tz$3 = Py.newCode(1, var2, var1, "mktime_tz", 152, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str"};
      quote$4 = Py.newCode(1, var2, var1, "quote", 162, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AddrlistClass$5 = Py.newCode(0, var2, var1, "AddrlistClass", 172, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "field"};
      __init__$6 = Py.newCode(2, var2, var1, "__init__", 182, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      gotonext$7 = Py.newCode(1, var2, var1, "gotonext", 201, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "ad"};
      getaddrlist$8 = Py.newCode(1, var2, var1, "getaddrlist", 211, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldpos", "oldcl", "plist", "returnlist", "addrspec", "fieldlen", "routeaddr"};
      getaddress$9 = Py.newCode(1, var2, var1, "getaddress", 225, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expectroute", "adlist"};
      getrouteaddr$10 = Py.newCode(1, var2, var1, "getrouteaddr", 284, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aslist"};
      getaddrspec$11 = Py.newCode(1, var2, var1, "getaddrspec", 316, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sdlist"};
      getdomain$12 = Py.newCode(1, var2, var1, "getdomain", 341, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "beginchar", "endchars", "allowcomments", "slist", "quote"};
      getdelimited$13 = Py.newCode(4, var2, var1, "getdelimited", 360, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getquote$14 = Py.newCode(1, var2, var1, "getquote", 397, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcomment$15 = Py.newCode(1, var2, var1, "getcomment", 401, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getdomainliteral$16 = Py.newCode(1, var2, var1, "getdomainliteral", 405, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "atomends", "atomlist"};
      getatom$17 = Py.newCode(2, var2, var1, "getatom", 409, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "plist"};
      getphraselist$18 = Py.newCode(1, var2, var1, "getphraselist", 429, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AddressList$19 = Py.newCode(0, var2, var1, "AddressList", 452, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "field"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 454, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$21 = Py.newCode(1, var2, var1, "__len__", 461, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "newaddr", "x"};
      __add__$22 = Py.newCode(2, var2, var1, "__add__", 464, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "x"};
      __iadd__$23 = Py.newCode(2, var2, var1, "__iadd__", 473, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "newaddr", "x"};
      __sub__$24 = Py.newCode(2, var2, var1, "__sub__", 480, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "x"};
      __isub__$25 = Py.newCode(2, var2, var1, "__isub__", 488, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$26 = Py.newCode(2, var2, var1, "__getitem__", 495, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _parseaddr$py("email/_parseaddr$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_parseaddr$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.parsedate_tz$1(var2, var3);
         case 2:
            return this.parsedate$2(var2, var3);
         case 3:
            return this.mktime_tz$3(var2, var3);
         case 4:
            return this.quote$4(var2, var3);
         case 5:
            return this.AddrlistClass$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.gotonext$7(var2, var3);
         case 8:
            return this.getaddrlist$8(var2, var3);
         case 9:
            return this.getaddress$9(var2, var3);
         case 10:
            return this.getrouteaddr$10(var2, var3);
         case 11:
            return this.getaddrspec$11(var2, var3);
         case 12:
            return this.getdomain$12(var2, var3);
         case 13:
            return this.getdelimited$13(var2, var3);
         case 14:
            return this.getquote$14(var2, var3);
         case 15:
            return this.getcomment$15(var2, var3);
         case 16:
            return this.getdomainliteral$16(var2, var3);
         case 17:
            return this.getatom$17(var2, var3);
         case 18:
            return this.getphraselist$18(var2, var3);
         case 19:
            return this.AddressList$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.__len__$21(var2, var3);
         case 22:
            return this.__add__$22(var2, var3);
         case 23:
            return this.__iadd__$23(var2, var3);
         case 24:
            return this.__sub__$24(var2, var3);
         case 25:
            return this.__isub__$25(var2, var3);
         case 26:
            return this.__getitem__$26(var2, var3);
         default:
            return null;
      }
   }
}
