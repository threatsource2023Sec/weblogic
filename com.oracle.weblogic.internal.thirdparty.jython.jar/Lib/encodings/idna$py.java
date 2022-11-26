package encodings;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("encodings/idna.py")
public class idna$py extends PyFunctionTable implements PyRunnable {
   static idna$py self;
   static final PyCode f$0;
   static final PyCode nameprep$1;
   static final PyCode ToASCII$2;
   static final PyCode ToUnicode$3;
   static final PyCode Codec$4;
   static final PyCode encode$5;
   static final PyCode decode$6;
   static final PyCode IncrementalEncoder$7;
   static final PyCode _buffer_encode$8;
   static final PyCode IncrementalDecoder$9;
   static final PyCode _buffer_decode$10;
   static final PyCode StreamWriter$11;
   static final PyCode StreamReader$12;
   static final PyCode getregentry$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(3);
      String[] var7 = new String[]{"IDN"};
      PyObject[] var8 = imp.importFrom("java.net", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("IDN", var4);
      var4 = null;

      try {
         var1.setline(6);
         var7 = new String[]{"StringPrep", "StringPrepParseException"};
         var8 = imp.importFrom("org.python.icu.text", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal("StringPrep", var4);
         var4 = null;
         var4 = var8[1];
         var1.setlocal("StringPrepParseException", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(9);
         String[] var9 = new String[]{"StringPrep", "StringPrepParseException"};
         PyObject[] var11 = imp.importFrom("com.ibm.icu.text", var9, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("StringPrep", var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal("StringPrepParseException", var5);
         var5 = null;
      }

      var1.setline(13);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("[.。．｡]"));
      var1.setlocal("dots", var3);
      var3 = null;
      var1.setline(16);
      var8 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var8, nameprep$1, (PyObject)null);
      var1.setlocal("nameprep", var12);
      var3 = null;
      var1.setline(24);
      var8 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var8, ToASCII$2, (PyObject)null);
      var1.setlocal("ToASCII", var12);
      var3 = null;
      var1.setline(28);
      var8 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var8, ToUnicode$3, (PyObject)null);
      var1.setlocal("ToUnicode", var12);
      var3 = null;
      var1.setline(36);
      var8 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      var4 = Py.makeClass("Codec", var8, Codec$4);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(87);
      var8 = new PyObject[]{var1.getname("codecs").__getattr__("BufferedIncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var8, IncrementalEncoder$7);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(121);
      var8 = new PyObject[]{var1.getname("codecs").__getattr__("BufferedIncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var8, IncrementalDecoder$9);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(161);
      var8 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var8, StreamWriter$11);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(164);
      var8 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var8, StreamReader$12);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(169);
      var8 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var8, getregentry$13, (PyObject)null);
      var1.setlocal("getregentry", var12);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject nameprep$1(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(18);
         PyObject var3 = var1.getglobal("StringPrep").__getattr__("getInstance").__call__(var2, var1.getglobal("StringPrep").__getattr__("RFC3491_NAMEPREP")).__getattr__("prepare").__call__(var2, var1.getlocal(0), var1.getglobal("StringPrep").__getattr__("ALLOW_UNASSIGNED"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("StringPrepParseException"))) {
            PyObject var5 = var4.value;
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(21);
            throw Py.makeException(var1.getglobal("UnicodeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid character")));
         } else {
            throw var4;
         }
      }
   }

   public PyObject ToASCII$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("IDN").__getattr__("toASCII").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ToUnicode$3(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("IDN").__getattr__("toUnicode").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Codec$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(58);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$6, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(PyString.fromInterned("strict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(41);
         throw Py.makeException(var1.getglobal("UnicodeError").__call__(var2, PyString.fromInterned("unsupported error handling ")._add(var1.getlocal(2))));
      } else {
         var1.setline(43);
         PyTuple var6;
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(44);
            var6 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(46);
            PyList var4 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(47);
            PyObject var7 = var1.getglobal("dots").__getattr__("split").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var7);
            var4 = null;
            var1.setline(48);
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var7 = var1.getglobal("len").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(-1)));
               var10000 = var7._eq(Py.newInteger(0));
               var4 = null;
            }

            PyString var8;
            if (var10000.__nonzero__()) {
               var1.setline(49);
               var8 = PyString.fromInterned(".");
               var1.setlocal(5, var8);
               var4 = null;
               var1.setline(50);
               var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
            } else {
               var1.setline(52);
               var8 = PyString.fromInterned("");
               var1.setlocal(5, var8);
               var4 = null;
            }

            var1.setline(53);
            var7 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(53);
               PyObject var5 = var7.__iternext__();
               if (var5 == null) {
                  var1.setline(56);
                  var6 = new PyTuple(new PyObject[]{PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(3))._add(var1.getlocal(5)), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setlocal(6, var5);
               var1.setline(54);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("ToASCII").__call__(var2, var1.getlocal(6)));
            }
         }
      }
   }

   public PyObject decode$6(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(PyString.fromInterned("strict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(61);
         throw Py.makeException(var1.getglobal("UnicodeError").__call__(var2, PyString.fromInterned("Unsupported error handling ")._add(var1.getlocal(2))));
      } else {
         var1.setline(63);
         PyTuple var6;
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(64);
            var6 = new PyTuple(new PyObject[]{PyUnicode.fromInterned(""), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(67);
            PyObject var4;
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
               var1.setline(68);
               var4 = var1.getglobal("dots").__getattr__("split").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var4);
               var4 = null;
            } else {
               var1.setline(71);
               var4 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(72);
               var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("ascii"));
               var1.setline(73);
               var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(75);
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(-1)));
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
            }

            PyUnicode var7;
            if (var10000.__nonzero__()) {
               var1.setline(76);
               var7 = PyUnicode.fromInterned(".");
               var1.setlocal(4, var7);
               var4 = null;
               var1.setline(77);
               var1.getlocal(3).__delitem__((PyObject)Py.newInteger(-1));
            } else {
               var1.setline(79);
               var7 = PyUnicode.fromInterned("");
               var1.setlocal(4, var7);
               var4 = null;
            }

            var1.setline(81);
            PyList var8 = new PyList(Py.EmptyObjects);
            var1.setlocal(5, var8);
            var4 = null;
            var1.setline(82);
            var4 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(82);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(85);
                  var6 = new PyTuple(new PyObject[]{PyUnicode.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(5))._add(var1.getlocal(4)), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setlocal(6, var5);
               var1.setline(83);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getglobal("ToUnicode").__call__(var2, var1.getlocal(6)));
            }
         }
      }
   }

   public PyObject IncrementalEncoder$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(88);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _buffer_encode$8, (PyObject)null);
      var1.setlocal("_buffer_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _buffer_encode$8(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(PyString.fromInterned("strict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         throw Py.makeException(var1.getglobal("UnicodeError").__call__(var2, PyString.fromInterned("unsupported error handling ")._add(var1.getlocal(2))));
      } else {
         var1.setline(93);
         PyTuple var7;
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(94);
            var7 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(96);
            PyObject var4 = var1.getglobal("dots").__getattr__("split").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(97);
            PyUnicode var8 = PyUnicode.fromInterned("");
            var1.setlocal(5, var8);
            var4 = null;
            var1.setline(98);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(99);
               PyString var9;
               if (var1.getlocal(4).__getitem__(Py.newInteger(-1)).__not__().__nonzero__()) {
                  var1.setline(100);
                  var9 = PyString.fromInterned(".");
                  var1.setlocal(5, var9);
                  var4 = null;
                  var1.setline(101);
                  var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
               } else {
                  var1.setline(102);
                  if (var1.getlocal(3).__not__().__nonzero__()) {
                     var1.setline(104);
                     var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
                     var1.setline(105);
                     if (var1.getlocal(4).__nonzero__()) {
                        var1.setline(106);
                        var9 = PyString.fromInterned(".");
                        var1.setlocal(5, var9);
                        var4 = null;
                     }
                  }
               }
            }

            var1.setline(108);
            PyList var10 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var10);
            var4 = null;
            var1.setline(109);
            PyInteger var11 = Py.newInteger(0);
            var1.setlocal(7, var11);
            var4 = null;
            var1.setline(110);
            var4 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(110);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(117);
                  var4 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(6))._add(var1.getlocal(5));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(118);
                  var4 = var1.getlocal(7);
                  var4 = var4._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
                  var1.setlocal(7, var4);
                  var1.setline(119);
                  var7 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)});
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setlocal(8, var5);
               var1.setline(111);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getglobal("ToASCII").__call__(var2, var1.getlocal(8)));
               var1.setline(112);
               PyObject var6;
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(113);
                  var6 = var1.getlocal(7);
                  var6 = var6._iadd(Py.newInteger(1));
                  var1.setlocal(7, var6);
               }

               var1.setline(114);
               var6 = var1.getlocal(7);
               var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
               var1.setlocal(7, var6);
            }
         }
      }
   }

   public PyObject IncrementalDecoder$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(122);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _buffer_decode$10, (PyObject)null);
      var1.setlocal("_buffer_decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _buffer_decode$10(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(PyString.fromInterned("strict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(124);
         throw Py.makeException(var1.getglobal("UnicodeError").__call__(var2, PyString.fromInterned("Unsupported error handling ")._add(var1.getlocal(2))));
      } else {
         var1.setline(126);
         PyTuple var7;
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(127);
            var7 = new PyTuple(new PyObject[]{PyUnicode.fromInterned(""), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(130);
            PyObject var4;
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
               var1.setline(131);
               var4 = var1.getglobal("dots").__getattr__("split").__call__(var2, var1.getlocal(1));
               var1.setlocal(4, var4);
               var4 = null;
            } else {
               var1.setline(134);
               var4 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(135);
               var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("ascii"));
               var1.setline(136);
               var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               var1.setlocal(4, var4);
               var4 = null;
            }

            var1.setline(138);
            PyUnicode var8 = PyUnicode.fromInterned("");
            var1.setlocal(5, var8);
            var4 = null;
            var1.setline(139);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(140);
               if (var1.getlocal(4).__getitem__(Py.newInteger(-1)).__not__().__nonzero__()) {
                  var1.setline(141);
                  var8 = PyUnicode.fromInterned(".");
                  var1.setlocal(5, var8);
                  var4 = null;
                  var1.setline(142);
                  var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
               } else {
                  var1.setline(143);
                  if (var1.getlocal(3).__not__().__nonzero__()) {
                     var1.setline(145);
                     var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
                     var1.setline(146);
                     if (var1.getlocal(4).__nonzero__()) {
                        var1.setline(147);
                        var8 = PyUnicode.fromInterned(".");
                        var1.setlocal(5, var8);
                        var4 = null;
                     }
                  }
               }
            }

            var1.setline(149);
            PyList var9 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var9);
            var4 = null;
            var1.setline(150);
            PyInteger var10 = Py.newInteger(0);
            var1.setlocal(7, var10);
            var4 = null;
            var1.setline(151);
            var4 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(151);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(157);
                  var4 = PyUnicode.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(6))._add(var1.getlocal(5));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(158);
                  var4 = var1.getlocal(7);
                  var4 = var4._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
                  var1.setlocal(7, var4);
                  var1.setline(159);
                  var7 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)});
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setlocal(8, var5);
               var1.setline(152);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getglobal("ToUnicode").__call__(var2, var1.getlocal(8)));
               var1.setline(153);
               PyObject var6;
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(154);
                  var6 = var1.getlocal(7);
                  var6 = var6._iadd(Py.newInteger(1));
                  var1.setlocal(7, var6);
               }

               var1.setline(155);
               var6 = var1.getlocal(7);
               var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
               var1.setlocal(7, var6);
            }
         }
      }
   }

   public PyObject StreamWriter$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(162);
      return var1.getf_locals();
   }

   public PyObject StreamReader$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(165);
      return var1.getf_locals();
   }

   public PyObject getregentry$13(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("idna"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamWriter"), var1.getglobal("StreamReader")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamwriter", "streamreader"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public idna$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"label", "e"};
      nameprep$1 = Py.newCode(1, var2, var1, "nameprep", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"label"};
      ToASCII$2 = Py.newCode(1, var2, var1, "ToASCII", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"label"};
      ToUnicode$3 = Py.newCode(1, var2, var1, "ToUnicode", 28, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$4 = Py.newCode(0, var2, var1, "Codec", 36, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors", "result", "labels", "trailing_dot", "label"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 37, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "labels", "trailing_dot", "result", "label"};
      decode$6 = Py.newCode(3, var2, var1, "decode", 58, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$7 = Py.newCode(0, var2, var1, "IncrementalEncoder", 87, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors", "final", "labels", "trailing_dot", "result", "size", "label"};
      _buffer_encode$8 = Py.newCode(4, var2, var1, "_buffer_encode", 88, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$9 = Py.newCode(0, var2, var1, "IncrementalDecoder", 121, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors", "final", "labels", "trailing_dot", "result", "size", "label"};
      _buffer_decode$10 = Py.newCode(4, var2, var1, "_buffer_decode", 122, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$11 = Py.newCode(0, var2, var1, "StreamWriter", 161, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$12 = Py.newCode(0, var2, var1, "StreamReader", 164, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$13 = Py.newCode(0, var2, var1, "getregentry", 169, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new idna$py("encodings/idna$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(idna$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.nameprep$1(var2, var3);
         case 2:
            return this.ToASCII$2(var2, var3);
         case 3:
            return this.ToUnicode$3(var2, var3);
         case 4:
            return this.Codec$4(var2, var3);
         case 5:
            return this.encode$5(var2, var3);
         case 6:
            return this.decode$6(var2, var3);
         case 7:
            return this.IncrementalEncoder$7(var2, var3);
         case 8:
            return this._buffer_encode$8(var2, var3);
         case 9:
            return this.IncrementalDecoder$9(var2, var3);
         case 10:
            return this._buffer_decode$10(var2, var3);
         case 11:
            return this.StreamWriter$11(var2, var3);
         case 12:
            return this.StreamReader$12(var2, var3);
         case 13:
            return this.getregentry$13(var2, var3);
         default:
            return null;
      }
   }
}
