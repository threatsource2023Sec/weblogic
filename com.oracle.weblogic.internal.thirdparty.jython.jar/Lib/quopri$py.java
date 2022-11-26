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
@Filename("quopri.py")
public class quopri$py extends PyFunctionTable implements PyRunnable {
   static quopri$py self;
   static final PyCode f$0;
   static final PyCode needsquoting$1;
   static final PyCode quote$2;
   static final PyCode encode$3;
   static final PyCode write$4;
   static final PyCode encodestring$5;
   static final PyCode decode$6;
   static final PyCode decodestring$7;
   static final PyCode ishex$8;
   static final PyCode unhex$9;
   static final PyCode main$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Conversions to/from quoted-printable transport encoding as per RFC 1521."));
      var1.setline(3);
      PyString.fromInterned("Conversions to/from quoted-printable transport encoding as per RFC 1521.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("encode"), PyString.fromInterned("decode"), PyString.fromInterned("encodestring"), PyString.fromInterned("decodestring")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      PyString var6 = PyString.fromInterned("=");
      var1.setlocal("ESCAPE", var6);
      var3 = null;
      var1.setline(10);
      PyInteger var7 = Py.newInteger(76);
      var1.setlocal("MAXLINESIZE", var7);
      var3 = null;
      var1.setline(11);
      var6 = PyString.fromInterned("0123456789ABCDEF");
      var1.setlocal("HEX", var6);
      var3 = null;
      var1.setline(12);
      var6 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var6);
      var3 = null;

      PyObject var4;
      PyObject[] var10;
      try {
         var1.setline(15);
         String[] var9 = new String[]{"a2b_qp", "b2a_qp"};
         var10 = imp.importFrom("binascii", var9, var1, -1);
         var4 = var10[0];
         var1.setlocal("a2b_qp", var4);
         var4 = null;
         var4 = var10[1];
         var1.setlocal("b2a_qp", var4);
         var4 = null;
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(17);
         var4 = var1.getname("None");
         var1.setlocal("a2b_qp", var4);
         var4 = null;
         var1.setline(18);
         var4 = var1.getname("None");
         var1.setlocal("b2a_qp", var4);
         var4 = null;
      }

      var1.setline(21);
      var10 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var10, needsquoting$1, PyString.fromInterned("Decide whether a particular character needs to be quoted.\n\n    The 'quotetabs' flag indicates whether embedded tabs and spaces should be\n    quoted.  Note that line-ending tabs and spaces are always encoded, as per\n    RFC 1521.\n    "));
      var1.setlocal("needsquoting", var11);
      var3 = null;
      var1.setline(35);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, quote$2, PyString.fromInterned("Quote a single character."));
      var1.setlocal("quote", var11);
      var3 = null;
      var1.setline(42);
      var10 = new PyObject[]{Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var10, encode$3, PyString.fromInterned("Read 'input', apply quoted-printable encoding, and write to 'output'.\n\n    'input' and 'output' are files with readline() and write() methods.\n    The 'quotetabs' flag indicates whether embedded tabs and spaces should be\n    quoted.  Note that line-ending tabs and spaces are always encoded, as per\n    RFC 1521.\n    The 'header' flag indicates whether we are encoding spaces as _ as per\n    RFC 1522.\n    "));
      var1.setlocal("encode", var11);
      var3 = null;
      var1.setline(105);
      var10 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var10, encodestring$5, (PyObject)null);
      var1.setlocal("encodestring", var11);
      var3 = null;
      var1.setline(116);
      var10 = new PyObject[]{Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var10, decode$6, PyString.fromInterned("Read 'input', apply quoted-printable decoding, and write to 'output'.\n    'input' and 'output' are files with readline() and write() methods.\n    If 'header' is true, decode underscore as space (per RFC 1522)."));
      var1.setlocal("decode", var11);
      var3 = null;
      var1.setline(159);
      var10 = new PyObject[]{Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var10, decodestring$7, (PyObject)null);
      var1.setlocal("decodestring", var11);
      var3 = null;
      var1.setline(171);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, ishex$8, PyString.fromInterned("Return true if the character 'c' is a hexadecimal digit."));
      var1.setlocal("ishex", var11);
      var3 = null;
      var1.setline(175);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, unhex$9, PyString.fromInterned("Get the integer value of a hexadecimal number."));
      var1.setlocal("unhex", var11);
      var3 = null;
      var1.setline(192);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, main$10, (PyObject)null);
      var1.setlocal("main", var11);
      var3 = null;
      var1.setline(236);
      PyObject var12 = var1.getname("__name__");
      PyObject var10000 = var12._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(237);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject needsquoting$1(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyString.fromInterned("Decide whether a particular character needs to be quoted.\n\n    The 'quotetabs' flag indicates whether embedded tabs and spaces should be\n    quoted.  Note that line-ending tabs and spaces are always encoded, as per\n    RFC 1521.\n    ");
      var1.setline(28);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(PyString.fromInterned(" \t"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(29);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(31);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("_"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(32);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(33);
            var4 = var1.getlocal(0);
            var10000 = var4._eq(var1.getglobal("ESCAPE"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               PyString var6 = PyString.fromInterned(" ");
               PyObject var10001 = var1.getlocal(0);
               PyString var7 = var6;
               var4 = var10001;
               PyObject var5;
               if ((var5 = var7._le(var10001)).__nonzero__()) {
                  var5 = var4._le(PyString.fromInterned("~"));
               }

               var4 = null;
               var10000 = var5.__not__();
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject quote$2(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Quote a single character.");
      var1.setline(37);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("ESCAPE")._add(var1.getglobal("HEX").__getitem__(var1.getlocal(1)._floordiv(Py.newInteger(16))))._add(var1.getglobal("HEX").__getitem__(var1.getlocal(1)._mod(Py.newInteger(16))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject encode$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Read 'input', apply quoted-printable encoding, and write to 'output'.\n\n    'input' and 'output' are files with readline() and write() methods.\n    The 'quotetabs' flag indicates whether embedded tabs and spaces should be\n    quoted.  Note that line-ending tabs and spaces are always encoded, as per\n    RFC 1521.\n    The 'header' flag indicates whether we are encoding spaces as _ as per\n    RFC 1522.\n    ");
      var1.setline(53);
      PyObject var3 = var1.getglobal("b2a_qp");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject[] var6;
      String[] var8;
      if (var10000.__nonzero__()) {
         var1.setline(54);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(55);
         var10000 = var1.getglobal("b2a_qp");
         var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(2), var1.getlocal(3)};
         var8 = new String[]{"quotetabs", "header"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(56);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(5));
         var1.setline(57);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(59);
         var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("\n")};
         PyFunction var7 = new PyFunction(var1.f_globals, var6, write$4, (PyObject)null);
         var1.setlocal(6, var7);
         var3 = null;
         var1.setline(69);
         var3 = var1.getglobal("None");
         var1.setlocal(7, var3);
         var3 = null;

         label61:
         while(true) {
            var1.setline(70);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(71);
            var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(72);
            if (var1.getlocal(8).__not__().__nonzero__()) {
               break;
            }

            var1.setline(74);
            PyList var9 = new PyList(Py.EmptyObjects);
            var1.setlocal(9, var9);
            var3 = null;
            var1.setline(76);
            PyString var10 = PyString.fromInterned("");
            var1.setlocal(10, var10);
            var3 = null;
            var1.setline(77);
            var3 = var1.getlocal(8).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("\n"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(78);
               var3 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(79);
               var10 = PyString.fromInterned("\n");
               var1.setlocal(10, var10);
               var3 = null;
            }

            var1.setline(81);
            var3 = var1.getlocal(8).__iter__();

            while(true) {
               var1.setline(81);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(89);
                  var3 = var1.getlocal(7);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(90);
                     var1.getlocal(6).__call__(var2, var1.getlocal(7));
                  }

                  var1.setline(93);
                  var3 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(9));
                  var1.setlocal(12, var3);
                  var3 = null;

                  while(true) {
                     var1.setline(94);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
                     var10000 = var3._gt(var1.getglobal("MAXLINESIZE"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(100);
                        var3 = var1.getlocal(12);
                        var1.setlocal(7, var3);
                        var3 = null;
                        continue label61;
                     }

                     var1.setline(97);
                     var10000 = var1.getlocal(6);
                     var6 = new PyObject[]{var1.getlocal(12).__getslice__((PyObject)null, var1.getglobal("MAXLINESIZE")._sub(Py.newInteger(1)), (PyObject)null), PyString.fromInterned("=\n")};
                     var8 = new String[]{"lineEnd"};
                     var10000.__call__(var2, var6, var8);
                     var3 = null;
                     var1.setline(98);
                     var3 = var1.getlocal(12).__getslice__(var1.getglobal("MAXLINESIZE")._sub(Py.newInteger(1)), (PyObject)null, (PyObject)null);
                     var1.setlocal(12, var3);
                     var3 = null;
                  }
               }

               var1.setlocal(11, var4);
               var1.setline(82);
               PyObject var5;
               if (var1.getglobal("needsquoting").__call__(var2, var1.getlocal(11), var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
                  var1.setline(83);
                  var5 = var1.getglobal("quote").__call__(var2, var1.getlocal(11));
                  var1.setlocal(11, var5);
                  var5 = null;
               }

               var1.setline(84);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(11);
                  var10000 = var5._eq(PyString.fromInterned(" "));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(85);
                  var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
               } else {
                  var1.setline(87);
                  var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(11));
               }
            }
         }

         var1.setline(102);
         var3 = var1.getlocal(7);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(103);
            var10000 = var1.getlocal(6);
            var6 = new PyObject[]{var1.getlocal(7), var1.getlocal(10)};
            var8 = new String[]{"lineEnd"};
            var10000.__call__(var2, var6, var8);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject write$4(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var10000 = var1.getlocal(0);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
         var10000 = var3._in(PyString.fromInterned(" \t"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(63);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(var1.getglobal("quote").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(-1))))._add(var1.getlocal(2)));
      } else {
         var1.setline(64);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(PyString.fromInterned("."));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(65);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getglobal("quote").__call__(var2, var1.getlocal(0))._add(var1.getlocal(2)));
         } else {
            var1.setline(67);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0)._add(var1.getlocal(2)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encodestring$5(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("b2a_qp");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      String[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var10000 = var1.getglobal("b2a_qp");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)};
         var4 = new String[]{"quotetabs", "header"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(108);
         var4 = new String[]{"StringIO"};
         PyObject[] var7 = imp.importFrom("cStringIO", var4, var1, -1);
         PyObject var5 = var7[0];
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(109);
         PyObject var8 = var1.getlocal(3).__call__(var2, var1.getlocal(0));
         var1.setlocal(4, var8);
         var4 = null;
         var1.setline(110);
         var8 = var1.getlocal(3).__call__(var2);
         var1.setlocal(5, var8);
         var4 = null;
         var1.setline(111);
         var1.getglobal("encode").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(1), var1.getlocal(2));
         var1.setline(112);
         var3 = var1.getlocal(5).__getattr__("getvalue").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject decode$6(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyString.fromInterned("Read 'input', apply quoted-printable decoding, and write to 'output'.\n    'input' and 'output' are files with readline() and write() methods.\n    If 'header' is true, decode underscore as space (per RFC 1522).");
      var1.setline(121);
      PyObject var3 = var1.getglobal("a2b_qp");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(122);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(123);
         var10000 = var1.getglobal("a2b_qp");
         PyObject[] var10 = new PyObject[]{var1.getlocal(3), var1.getlocal(2)};
         String[] var7 = new String[]{"header"};
         var10000 = var10000.__call__(var2, var10, var7);
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(124);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(4));
         var1.setline(125);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(127);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(5, var6);
         var3 = null;

         while(true) {
            var1.setline(128);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(129);
            var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(130);
            if (var1.getlocal(6).__not__().__nonzero__()) {
               break;
            }

            var1.setline(131);
            PyTuple var8 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(6))});
            PyObject[] var4 = Py.unpackSequence(var8, 2);
            PyObject var5 = var4[0];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;
            var1.setline(132);
            var3 = var1.getlocal(8);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(6).__getitem__(var1.getlocal(8)._sub(Py.newInteger(1)));
               var10000 = var3._eq(PyString.fromInterned("\n"));
               var3 = null;
            }

            PyInteger var9;
            if (var10000.__nonzero__()) {
               var1.setline(133);
               var9 = Py.newInteger(0);
               var1.setlocal(9, var9);
               var3 = null;
               var1.setline(133);
               var3 = var1.getlocal(8)._sub(Py.newInteger(1));
               var1.setlocal(8, var3);
               var3 = null;

               while(true) {
                  var1.setline(135);
                  var3 = var1.getlocal(8);
                  var10000 = var3._gt(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(6).__getitem__(var1.getlocal(8)._sub(Py.newInteger(1)));
                     var10000 = var3._in(PyString.fromInterned(" \t\r"));
                     var3 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(136);
                  var3 = var1.getlocal(8)._sub(Py.newInteger(1));
                  var1.setlocal(8, var3);
                  var3 = null;
               }
            } else {
               var1.setline(138);
               var9 = Py.newInteger(1);
               var1.setlocal(9, var9);
               var3 = null;
            }

            while(true) {
               var1.setline(139);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(var1.getlocal(8));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(140);
               var3 = var1.getlocal(6).__getitem__(var1.getlocal(7));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(141);
               var3 = var1.getlocal(10);
               var10000 = var3._eq(PyString.fromInterned("_"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(142);
                  var3 = var1.getlocal(5)._add(PyString.fromInterned(" "));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(142);
                  var3 = var1.getlocal(7)._add(Py.newInteger(1));
                  var1.setlocal(7, var3);
                  var3 = null;
               } else {
                  var1.setline(143);
                  var3 = var1.getlocal(10);
                  var10000 = var3._ne(var1.getglobal("ESCAPE"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(144);
                     var3 = var1.getlocal(5)._add(var1.getlocal(10));
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(144);
                     var3 = var1.getlocal(7)._add(Py.newInteger(1));
                     var1.setlocal(7, var3);
                     var3 = null;
                  } else {
                     var1.setline(145);
                     var3 = var1.getlocal(7)._add(Py.newInteger(1));
                     var10000 = var3._eq(var1.getlocal(8));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(9).__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(146);
                        var9 = Py.newInteger(1);
                        var1.setlocal(9, var9);
                        var3 = null;
                        break;
                     }

                     var1.setline(147);
                     var3 = var1.getlocal(7)._add(Py.newInteger(1));
                     var10000 = var3._lt(var1.getlocal(8));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(6).__getitem__(var1.getlocal(7)._add(Py.newInteger(1)));
                        var10000 = var3._eq(var1.getglobal("ESCAPE"));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(148);
                        var3 = var1.getlocal(5)._add(var1.getglobal("ESCAPE"));
                        var1.setlocal(5, var3);
                        var3 = null;
                        var1.setline(148);
                        var3 = var1.getlocal(7)._add(Py.newInteger(2));
                        var1.setlocal(7, var3);
                        var3 = null;
                     } else {
                        var1.setline(149);
                        var3 = var1.getlocal(7)._add(Py.newInteger(2));
                        var10000 = var3._lt(var1.getlocal(8));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getglobal("ishex").__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(7)._add(Py.newInteger(1))));
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getglobal("ishex").__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(7)._add(Py.newInteger(2))));
                           }
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(150);
                           var3 = var1.getlocal(5)._add(var1.getglobal("chr").__call__(var2, var1.getglobal("unhex").__call__(var2, var1.getlocal(6).__getslice__(var1.getlocal(7)._add(Py.newInteger(1)), var1.getlocal(7)._add(Py.newInteger(3)), (PyObject)null))));
                           var1.setlocal(5, var3);
                           var3 = null;
                           var1.setline(150);
                           var3 = var1.getlocal(7)._add(Py.newInteger(3));
                           var1.setlocal(7, var3);
                           var3 = null;
                        } else {
                           var1.setline(152);
                           var3 = var1.getlocal(5)._add(var1.getlocal(10));
                           var1.setlocal(5, var3);
                           var3 = null;
                           var1.setline(152);
                           var3 = var1.getlocal(7)._add(Py.newInteger(1));
                           var1.setlocal(7, var3);
                           var3 = null;
                        }
                     }
                  }
               }
            }

            var1.setline(153);
            if (var1.getlocal(9).__not__().__nonzero__()) {
               var1.setline(154);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(5)._add(PyString.fromInterned("\n")));
               var1.setline(155);
               var6 = PyString.fromInterned("");
               var1.setlocal(5, var6);
               var3 = null;
            }
         }

         var1.setline(156);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(157);
            var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(5));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject decodestring$7(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyObject var3 = var1.getglobal("a2b_qp");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      String[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(161);
         var10000 = var1.getglobal("a2b_qp");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1)};
         var4 = new String[]{"header"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(162);
         var4 = new String[]{"StringIO"};
         PyObject[] var7 = imp.importFrom("cStringIO", var4, var1, -1);
         PyObject var5 = var7[0];
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(163);
         PyObject var8 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var8);
         var4 = null;
         var1.setline(164);
         var8 = var1.getlocal(2).__call__(var2);
         var1.setlocal(4, var8);
         var4 = null;
         var1.setline(165);
         var10000 = var1.getglobal("decode");
         var7 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(1)};
         String[] var9 = new String[]{"header"};
         var10000.__call__(var2, var7, var9);
         var4 = null;
         var1.setline(166);
         var3 = var1.getlocal(4).__getattr__("getvalue").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ishex$8(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyString.fromInterned("Return true if the character 'c' is a hexadecimal digit.");
      var1.setline(173);
      PyString var3 = PyString.fromInterned("0");
      PyObject var10001 = var1.getlocal(0);
      PyString var10000 = var3;
      PyObject var5 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var5._le(PyString.fromInterned("9"));
      }

      PyObject var6 = var4;
      var3 = null;
      if (!var4.__nonzero__()) {
         var3 = PyString.fromInterned("a");
         var10001 = var1.getlocal(0);
         var10000 = var3;
         var5 = var10001;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var5._le(PyString.fromInterned("f"));
         }

         var6 = var4;
         var3 = null;
         if (!var4.__nonzero__()) {
            var3 = PyString.fromInterned("A");
            var10001 = var1.getlocal(0);
            var10000 = var3;
            var5 = var10001;
            if ((var4 = var10000._le(var10001)).__nonzero__()) {
               var4 = var5._le(PyString.fromInterned("F"));
            }

            var6 = var4;
            var3 = null;
         }
      }

      var5 = var6;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject unhex$9(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("Get the integer value of a hexadecimal number.");
      var1.setline(177);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(178);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(178);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(2, var4);
         var1.setline(179);
         PyString var5 = PyString.fromInterned("0");
         PyObject var10001 = var1.getlocal(2);
         PyString var10000 = var5;
         PyObject var8 = var10001;
         PyObject var6;
         if ((var6 = var10000._le(var10001)).__nonzero__()) {
            var6 = var8._le(PyString.fromInterned("9"));
         }

         var5 = null;
         if (var6.__nonzero__()) {
            var1.setline(180);
            var8 = var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
            var1.setlocal(3, var8);
            var5 = null;
         } else {
            var1.setline(181);
            var5 = PyString.fromInterned("a");
            var10001 = var1.getlocal(2);
            var10000 = var5;
            var8 = var10001;
            if ((var6 = var10000._le(var10001)).__nonzero__()) {
               var6 = var8._le(PyString.fromInterned("f"));
            }

            var5 = null;
            if (var6.__nonzero__()) {
               var1.setline(182);
               var8 = var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("a"))._sub(Py.newInteger(10));
               var1.setlocal(3, var8);
               var5 = null;
            } else {
               var1.setline(183);
               var5 = PyString.fromInterned("A");
               var10001 = var1.getlocal(2);
               var10000 = var5;
               var8 = var10001;
               if ((var6 = var10000._le(var10001)).__nonzero__()) {
                  var6 = var8._le(PyString.fromInterned("F"));
               }

               var5 = null;
               if (!var6.__nonzero__()) {
                  break;
               }

               var1.setline(184);
               var8 = var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A"))._sub(Py.newInteger(10));
               var1.setlocal(3, var8);
               var5 = null;
            }
         }

         var1.setline(187);
         var8 = var1.getlocal(1)._mul(Py.newInteger(16))._add(var1.getglobal("ord").__call__(var2, var1.getlocal(2))._sub(var1.getlocal(3)));
         var1.setlocal(1, var8);
         var5 = null;
      }

      var1.setline(188);
      var7 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject main$10(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(194);
      var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyObject var5;
      try {
         var1.setline(196);
         var3 = var1.getlocal(1).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("td"));
         PyObject[] var10 = Py.unpackSequence(var3, 2);
         var5 = var10[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var8) {
         PyException var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getlocal(1).__getattr__("error"))) {
            throw var9;
         }

         var4 = var9.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(198);
         var4 = var1.getlocal(0).__getattr__("stderr");
         var1.getlocal(0).__setattr__("stdout", var4);
         var4 = null;
         var1.setline(199);
         Py.println(var1.getlocal(4));
         var1.setline(200);
         Py.println(PyString.fromInterned("usage: quopri [-t | -d] [file] ..."));
         var1.setline(201);
         Py.println(PyString.fromInterned("-t: quote tabs"));
         var1.setline(202);
         Py.println(PyString.fromInterned("-d: decode; default encode"));
         var1.setline(203);
         var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      }

      var1.setline(204);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(5, var11);
      var3 = null;
      var1.setline(205);
      var11 = Py.newInteger(0);
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(206);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(206);
         var4 = var3.__iternext__();
         PyObject var6;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(209);
            var10000 = var1.getlocal(6);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(5);
            }

            if (var10000.__nonzero__()) {
               var1.setline(210);
               var3 = var1.getlocal(0).__getattr__("stderr");
               var1.getlocal(0).__setattr__("stdout", var3);
               var3 = null;
               var1.setline(211);
               Py.println(PyString.fromInterned("-t and -d are mutually exclusive"));
               var1.setline(212);
               var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            }

            var1.setline(213);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(213);
               PyList var14 = new PyList(new PyObject[]{PyString.fromInterned("-")});
               var1.setlocal(3, var14);
               var3 = null;
            }

            var1.setline(214);
            var11 = Py.newInteger(0);
            var1.setlocal(9, var11);
            var3 = null;
            var1.setline(215);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               while(true) {
                  var1.setline(215);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(231);
                     if (var1.getlocal(9).__nonzero__()) {
                        var1.setline(232);
                        var1.getlocal(0).__getattr__("exit").__call__(var2, var1.getlocal(9));
                     }

                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(10, var4);
                  var1.setline(216);
                  var5 = var1.getlocal(10);
                  var10000 = var5._eq(PyString.fromInterned("-"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(217);
                     var5 = var1.getlocal(0).__getattr__("stdin");
                     var1.setlocal(11, var5);
                     var5 = null;
                     break;
                  }

                  try {
                     var1.setline(220);
                     var5 = var1.getglobal("open").__call__(var2, var1.getlocal(10));
                     var1.setlocal(11, var5);
                     var5 = null;
                     break;
                  } catch (Throwable var7) {
                     PyException var16 = Py.setException(var7, var1);
                     if (!var16.match(var1.getglobal("IOError"))) {
                        throw var16;
                     }

                     var6 = var16.value;
                     var1.setlocal(4, var6);
                     var6 = null;
                     var1.setline(222);
                     var1.getlocal(0).__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("%s: can't open (%s)\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(4)})));
                     var1.setline(223);
                     PyInteger var15 = Py.newInteger(1);
                     var1.setlocal(9, var15);
                     var6 = null;
                  }
               }

               var1.setline(225);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(226);
                  var1.getglobal("decode").__call__(var2, var1.getlocal(11), var1.getlocal(0).__getattr__("stdout"));
               } else {
                  var1.setline(228);
                  var1.getglobal("encode").__call__(var2, var1.getlocal(11), var1.getlocal(0).__getattr__("stdout"), var1.getlocal(6));
               }

               var1.setline(229);
               var5 = var1.getlocal(11);
               var10000 = var5._isnot(var1.getlocal(0).__getattr__("stdin"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(230);
                  var1.getlocal(11).__getattr__("close").__call__(var2);
               }
            }
         }

         PyObject[] var12 = Py.unpackSequence(var4, 2);
         var6 = var12[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var12[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(207);
         var5 = var1.getlocal(7);
         var10000 = var5._eq(PyString.fromInterned("-t"));
         var5 = null;
         PyInteger var13;
         if (var10000.__nonzero__()) {
            var1.setline(207);
            var13 = Py.newInteger(1);
            var1.setlocal(6, var13);
            var5 = null;
         }

         var1.setline(208);
         var5 = var1.getlocal(7);
         var10000 = var5._eq(PyString.fromInterned("-d"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(208);
            var13 = Py.newInteger(1);
            var1.setlocal(5, var13);
            var5 = null;
         }
      }
   }

   public quopri$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"c", "quotetabs", "header"};
      needsquoting$1 = Py.newCode(3, var2, var1, "needsquoting", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c", "i"};
      quote$2 = Py.newCode(1, var2, var1, "quote", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "quotetabs", "header", "data", "odata", "write", "prevline", "line", "outline", "stripped", "c", "thisline"};
      encode$3 = Py.newCode(4, var2, var1, "encode", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "output", "lineEnd"};
      write$4 = Py.newCode(3, var2, var1, "write", 59, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "quotetabs", "header", "StringIO", "infp", "outfp"};
      encodestring$5 = Py.newCode(3, var2, var1, "encodestring", 105, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "output", "header", "data", "odata", "new", "line", "i", "n", "partial", "c"};
      decode$6 = Py.newCode(3, var2, var1, "decode", 116, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "header", "StringIO", "infp", "outfp"};
      decodestring$7 = Py.newCode(2, var2, var1, "decodestring", 159, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c"};
      ishex$8 = Py.newCode(1, var2, var1, "ishex", 171, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "bits", "c", "i"};
      unhex$9 = Py.newCode(1, var2, var1, "unhex", 175, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "getopt", "opts", "args", "msg", "deco", "tabs", "o", "a", "sts", "file", "fp"};
      main$10 = Py.newCode(0, var2, var1, "main", 192, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new quopri$py("quopri$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(quopri$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.needsquoting$1(var2, var3);
         case 2:
            return this.quote$2(var2, var3);
         case 3:
            return this.encode$3(var2, var3);
         case 4:
            return this.write$4(var2, var3);
         case 5:
            return this.encodestring$5(var2, var3);
         case 6:
            return this.decode$6(var2, var3);
         case 7:
            return this.decodestring$7(var2, var3);
         case 8:
            return this.ishex$8(var2, var3);
         case 9:
            return this.unhex$9(var2, var3);
         case 10:
            return this.main$10(var2, var3);
         default:
            return null;
      }
   }
}
