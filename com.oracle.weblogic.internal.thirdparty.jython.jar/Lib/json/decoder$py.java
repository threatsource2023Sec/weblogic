package json;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/decoder.py")
public class decoder$py extends PyFunctionTable implements PyRunnable {
   static decoder$py self;
   static final PyCode f$0;
   static final PyCode _floatconstants$1;
   static final PyCode linecol$2;
   static final PyCode errmsg$3;
   static final PyCode py_scanstring$4;
   static final PyCode JSONObject$5;
   static final PyCode JSONArray$6;
   static final PyCode JSONDecoder$7;
   static final PyCode __init__$8;
   static final PyCode decode$9;
   static final PyCode raw_decode$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Implementation of JSONDecoder\n"));
      var1.setline(2);
      PyString.fromInterned("Implementation of JSONDecoder\n");
      var1.setline(3);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(7);
      String[] var7 = new String[]{"scanner"};
      PyObject[] var8 = imp.importFrom("json", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("scanner", var4);
      var4 = null;

      try {
         var1.setline(9);
         var7 = new String[]{"scanstring"};
         var8 = imp.importFrom("_json", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal("c_scanstring", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(11);
         var4 = var1.getname("None");
         var1.setlocal("c_scanstring", var4);
         var4 = null;
      }

      var1.setline(13);
      PyList var11 = new PyList(new PyObject[]{PyString.fromInterned("JSONDecoder")});
      var1.setlocal("__all__", var11);
      var3 = null;
      var1.setline(15);
      var3 = var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("MULTILINE"))._or(var1.getname("re").__getattr__("DOTALL"));
      var1.setlocal("FLAGS", var3);
      var3 = null;
      var1.setline(17);
      var8 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var8, _floatconstants$1, (PyObject)null);
      var1.setlocal("_floatconstants", var12);
      var3 = null;
      var1.setline(24);
      var3 = var1.getname("_floatconstants").__call__(var2);
      PyObject[] var10 = Py.unpackSequence(var3, 3);
      PyObject var5 = var10[0];
      var1.setlocal("NaN", var5);
      var5 = null;
      var5 = var10[1];
      var1.setlocal("PosInf", var5);
      var5 = null;
      var5 = var10[2];
      var1.setlocal("NegInf", var5);
      var5 = null;
      var3 = null;
      var1.setline(27);
      var8 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var8, linecol$2, (PyObject)null);
      var1.setlocal("linecol", var12);
      var3 = null;
      var1.setline(36);
      var8 = new PyObject[]{var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var8, errmsg$3, (PyObject)null);
      var1.setlocal("errmsg", var12);
      var3 = null;
      var1.setline(51);
      PyDictionary var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("-Infinity"), var1.getname("NegInf"), PyString.fromInterned("Infinity"), var1.getname("PosInf"), PyString.fromInterned("NaN"), var1.getname("NaN")});
      var1.setlocal("_CONSTANTS", var13);
      var3 = null;
      var1.setline(57);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(.*?)([\"\\\\\\x00-\\x1f])"), (PyObject)var1.getname("FLAGS"));
      var1.setlocal("STRINGCHUNK", var3);
      var3 = null;
      var1.setline(58);
      var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("\""), PyUnicode.fromInterned("\""), PyString.fromInterned("\\"), PyUnicode.fromInterned("\\"), PyString.fromInterned("/"), PyUnicode.fromInterned("/"), PyString.fromInterned("b"), PyUnicode.fromInterned("\b"), PyString.fromInterned("f"), PyUnicode.fromInterned("\f"), PyString.fromInterned("n"), PyUnicode.fromInterned("\n"), PyString.fromInterned("r"), PyUnicode.fromInterned("\r"), PyString.fromInterned("t"), PyUnicode.fromInterned("\t")});
      var1.setlocal("BACKSLASH", var13);
      var3 = null;
      var1.setline(63);
      PyString var14 = PyString.fromInterned("utf-8");
      var1.setlocal("DEFAULT_ENCODING", var14);
      var3 = null;
      var1.setline(65);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("True"), var1.getname("BACKSLASH"), var1.getname("STRINGCHUNK").__getattr__("match")};
      var12 = new PyFunction(var1.f_globals, var8, py_scanstring$4, PyString.fromInterned("Scan the string s for a JSON string. End is the index of the\n    character in s after the quote that started the JSON string.\n    Unescapes all valid JSON string escape sequences and raises ValueError\n    on attempt to decode an invalid string. If strict is False then literal\n    control characters are allowed in the string.\n\n    Returns a tuple of the decoded string and the index of the character in s\n    after the end quote."));
      var1.setlocal("py_scanstring", var12);
      var3 = null;
      var1.setline(144);
      PyObject var10000 = var1.getname("c_scanstring");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getname("py_scanstring");
      }

      var3 = var10000;
      var1.setlocal("scanstring", var3);
      var3 = null;
      var1.setline(146);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ \\t\\n\\r]*"), (PyObject)var1.getname("FLAGS"));
      var1.setlocal("WHITESPACE", var3);
      var3 = null;
      var1.setline(147);
      var14 = PyString.fromInterned(" \t\n\r");
      var1.setlocal("WHITESPACE_STR", var14);
      var3 = null;
      var1.setline(149);
      var8 = new PyObject[]{var1.getname("WHITESPACE").__getattr__("match"), var1.getname("WHITESPACE_STR")};
      var12 = new PyFunction(var1.f_globals, var8, JSONObject$5, (PyObject)null);
      var1.setlocal("JSONObject", var12);
      var3 = null;
      var1.setline(237);
      var8 = new PyObject[]{var1.getname("WHITESPACE").__getattr__("match"), var1.getname("WHITESPACE_STR")};
      var12 = new PyFunction(var1.f_globals, var8, JSONArray$6, (PyObject)null);
      var1.setlocal("JSONArray", var12);
      var3 = null;
      var1.setline(273);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("JSONDecoder", var8, JSONDecoder$7);
      var1.setlocal("JSONDecoder", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _floatconstants$1(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = PyString.fromInterned("7FF80000000000007FF0000000000000").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hex"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("sys").__getattr__("byteorder");
      PyObject var10000 = var3._ne(PyString.fromInterned("big"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1))._add(var1.getlocal(0).__getslice__(Py.newInteger(8), (PyObject)null, (PyObject)null).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1)));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(21);
      var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dd"), (PyObject)var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(22);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(2).__neg__()});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject linecol$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getlocal(0).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1))._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         var3 = var1.getlocal(1)._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(32);
         var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("rindex").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1)));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(33);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject errmsg$3(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getglobal("linecol").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         PyString var8 = PyString.fromInterned("{0}: line {1} column {2} (char {3})");
         var1.setlocal(6, var8);
         var3 = null;
         var1.setline(41);
         var3 = var1.getlocal(6).__getattr__("format").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(5), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(44);
         PyObject var7 = var1.getglobal("linecol").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         PyObject[] var10 = Py.unpackSequence(var7, 2);
         PyObject var6 = var10[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(8, var6);
         var6 = null;
         var4 = null;
         var1.setline(45);
         PyString var9 = PyString.fromInterned("{0}: line {1} column {2} - line {3} column {4} (char {5} - {6})");
         var1.setlocal(6, var9);
         var4 = null;
         var1.setline(46);
         var10000 = var1.getlocal(6).__getattr__("format");
         var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), var1.getlocal(2), var1.getlocal(3)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject py_scanstring$4(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("Scan the string s for a JSON string. End is the index of the\n    character in s after the quote that started the JSON string.\n    Unescapes all valid JSON string escape sequences and raises ValueError\n    on attempt to decode an invalid string. If strict is False then literal\n    control characters are allowed in the string.\n\n    Returns a tuple of the decoded string and the index of the character in s\n    after the end quote.");
      var1.setline(75);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         var3 = var1.getglobal("DEFAULT_ENCODING");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(77);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(6).__getattr__("append");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(79);
      var3 = var1.getlocal(1)._sub(Py.newInteger(1));
      var1.setlocal(8, var3);
      var3 = null;

      while(true) {
         var1.setline(80);
         if (Py.newInteger(1).__nonzero__()) {
            var1.setline(81);
            var3 = var1.getlocal(5).__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(82);
            var3 = var1.getlocal(9);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(83);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Unterminated string starting at"), (PyObject)var1.getlocal(0), (PyObject)var1.getlocal(8))));
            }

            var1.setline(85);
            var3 = var1.getlocal(9).__getattr__("end").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(86);
            var3 = var1.getlocal(9).__getattr__("groups").__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(10, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(11, var5);
            var5 = null;
            var3 = null;
            var1.setline(88);
            if (var1.getlocal(10).__nonzero__()) {
               var1.setline(89);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("unicode")).__not__().__nonzero__()) {
                  var1.setline(90);
                  var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(10), var1.getlocal(2));
                  var1.setlocal(10, var3);
                  var3 = null;
               }

               var1.setline(91);
               var1.getlocal(7).__call__(var2, var1.getlocal(10));
            }

            var1.setline(94);
            var3 = var1.getlocal(11);
            var10000 = var3._eq(PyString.fromInterned("\""));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(96);
               var3 = var1.getlocal(11);
               var10000 = var3._ne(PyString.fromInterned("\\"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(97);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(99);
                     var3 = PyString.fromInterned("Invalid control character {0!r} at").__getattr__("format").__call__(var2, var1.getlocal(11));
                     var1.setlocal(12, var3);
                     var3 = null;
                     var1.setline(100);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__(var2, var1.getlocal(12), var1.getlocal(0), var1.getlocal(1))));
                  }

                  var1.setline(102);
                  var1.getlocal(7).__call__(var2, var1.getlocal(11));
               } else {
                  PyException var11;
                  try {
                     var1.setline(105);
                     var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
                     var1.setlocal(13, var3);
                     var3 = null;
                  } catch (Throwable var6) {
                     var11 = Py.setException(var6, var1);
                     if (var11.match(var1.getglobal("IndexError"))) {
                        var1.setline(107);
                        throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Unterminated string starting at"), (PyObject)var1.getlocal(0), (PyObject)var1.getlocal(8))));
                     }

                     throw var11;
                  }

                  var1.setline(110);
                  var3 = var1.getlocal(13);
                  var10000 = var3._ne(PyString.fromInterned("u"));
                  var3 = null;
                  PyObject var9;
                  if (var10000.__nonzero__()) {
                     try {
                        var1.setline(112);
                        var3 = var1.getlocal(4).__getitem__(var1.getlocal(13));
                        var1.setlocal(14, var3);
                        var3 = null;
                     } catch (Throwable var7) {
                        var11 = Py.setException(var7, var1);
                        if (var11.match(var1.getglobal("KeyError"))) {
                           var1.setline(114);
                           var9 = PyString.fromInterned("Invalid \\escape: ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(13)));
                           var1.setlocal(12, var9);
                           var4 = null;
                           var1.setline(115);
                           throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__(var2, var1.getlocal(12), var1.getlocal(0), var1.getlocal(1))));
                        }

                        throw var11;
                     }

                     var1.setline(116);
                     var3 = var1.getlocal(1);
                     var3 = var3._iadd(Py.newInteger(1));
                     var1.setlocal(1, var3);
                  } else {
                     var1.setline(119);
                     var3 = var1.getlocal(0).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), var1.getlocal(1)._add(Py.newInteger(5)), (PyObject)null);
                     var1.setlocal(13, var3);
                     var3 = null;
                     var1.setline(120);
                     var3 = var1.getlocal(1)._add(Py.newInteger(5));
                     var1.setlocal(15, var3);
                     var3 = null;
                     var1.setline(121);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(13));
                     var10000 = var3._ne(Py.newInteger(4));
                     var3 = null;
                     PyString var13;
                     if (var10000.__nonzero__()) {
                        var1.setline(122);
                        var13 = PyString.fromInterned("Invalid \\uXXXX escape");
                        var1.setlocal(12, var13);
                        var3 = null;
                        var1.setline(123);
                        throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__(var2, var1.getlocal(12), var1.getlocal(0), var1.getlocal(1))));
                     }

                     var1.setline(124);
                     var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(13), (PyObject)Py.newInteger(16));
                     var1.setlocal(16, var3);
                     var3 = null;
                     var1.setline(126);
                     PyInteger var12 = Py.newInteger(55296);
                     PyObject var10001 = var1.getlocal(16);
                     PyInteger var14 = var12;
                     var3 = var10001;
                     if ((var9 = var14._le(var10001)).__nonzero__()) {
                        var9 = var3._le(Py.newInteger(56319));
                     }

                     var10000 = var9;
                     var3 = null;
                     if (var9.__nonzero__()) {
                        var3 = var1.getglobal("sys").__getattr__("maxunicode");
                        var10000 = var3._gt(Py.newInteger(65535));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(127);
                        var13 = PyString.fromInterned("Invalid \\uXXXX\\uXXXX surrogate pair");
                        var1.setlocal(12, var13);
                        var3 = null;
                        var1.setline(128);
                        var3 = var1.getlocal(0).__getslice__(var1.getlocal(1)._add(Py.newInteger(5)), var1.getlocal(1)._add(Py.newInteger(7)), (PyObject)null);
                        var10000 = var3._eq(PyString.fromInterned("\\u"));
                        var3 = null;
                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(129);
                           throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__(var2, var1.getlocal(12), var1.getlocal(0), var1.getlocal(1))));
                        }

                        var1.setline(130);
                        var3 = var1.getlocal(0).__getslice__(var1.getlocal(1)._add(Py.newInteger(7)), var1.getlocal(1)._add(Py.newInteger(11)), (PyObject)null);
                        var1.setlocal(17, var3);
                        var3 = null;
                        var1.setline(131);
                        var3 = var1.getglobal("len").__call__(var2, var1.getlocal(17));
                        var10000 = var3._ne(Py.newInteger(4));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(132);
                           throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__(var2, var1.getlocal(12), var1.getlocal(0), var1.getlocal(1))));
                        }

                        var1.setline(133);
                        var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(17), (PyObject)Py.newInteger(16));
                        var1.setlocal(18, var3);
                        var3 = null;
                        var1.setline(134);
                        var3 = Py.newInteger(65536)._add(var1.getlocal(16)._sub(Py.newInteger(55296))._lshift(Py.newInteger(10))._or(var1.getlocal(18)._sub(Py.newInteger(56320))));
                        var1.setlocal(16, var3);
                        var3 = null;
                        var1.setline(135);
                        var3 = var1.getlocal(15);
                        var3 = var3._iadd(Py.newInteger(6));
                        var1.setlocal(15, var3);
                     }

                     var1.setline(136);
                     var3 = var1.getglobal("unichr").__call__(var2, var1.getlocal(16));
                     var1.setlocal(14, var3);
                     var3 = null;
                     var1.setline(137);
                     var3 = var1.getlocal(15);
                     var1.setlocal(1, var3);
                     var3 = null;
                  }

                  var1.setline(139);
                  var1.getlocal(7).__call__(var2, var1.getlocal(14));
               }
               continue;
            }
         }

         var1.setline(140);
         PyTuple var10 = new PyTuple(new PyObject[]{PyUnicode.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6)), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var10;
      }
   }

   public PyObject JSONObject$5(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(152);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setlocal(10, var11);
      var3 = null;
      var1.setline(153);
      var3 = var1.getlocal(10).__getattr__("append");
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(156);
      var3 = var1.getlocal(8).__getslice__(var1.getlocal(9), var1.getlocal(9)._add(Py.newInteger(1)), (PyObject)null);
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(158);
      var3 = var1.getlocal(12);
      PyObject var10000 = var3._ne(PyString.fromInterned("\""));
      var3 = null;
      PyObject var12;
      PyTuple var15;
      if (var10000.__nonzero__()) {
         var1.setline(159);
         var3 = var1.getlocal(12);
         var10000 = var3._in(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(160);
            var3 = var1.getlocal(6).__call__(var2, var1.getlocal(8), var1.getlocal(9)).__getattr__("end").__call__(var2);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(161);
            var3 = var1.getlocal(8).__getslice__(var1.getlocal(9), var1.getlocal(9)._add(Py.newInteger(1)), (PyObject)null);
            var1.setlocal(12, var3);
            var3 = null;
         }

         var1.setline(163);
         var3 = var1.getlocal(12);
         var10000 = var3._eq(PyString.fromInterned("}"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(164);
            var3 = var1.getlocal(5);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(165);
               var3 = var1.getlocal(5).__call__(var2, var1.getlocal(10));
               var1.setlocal(13, var3);
               var3 = null;
               var1.setline(166);
               var15 = new PyTuple(new PyObject[]{var1.getlocal(13), var1.getlocal(9)});
               var1.f_lasti = -1;
               return var15;
            }

            var1.setline(167);
            PyDictionary var17 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(10, var17);
            var4 = null;
            var1.setline(168);
            var12 = var1.getlocal(4);
            var10000 = var12._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(169);
               var12 = var1.getlocal(4).__call__(var2, var1.getlocal(10));
               var1.setlocal(10, var12);
               var4 = null;
            }

            var1.setline(170);
            var15 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(9)._add(Py.newInteger(1))});
            var1.f_lasti = -1;
            return var15;
         }

         var1.setline(171);
         var12 = var1.getlocal(12);
         var10000 = var12._ne(PyString.fromInterned("\""));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting property name enclosed in double quotes"), (PyObject)var1.getlocal(8), (PyObject)var1.getlocal(9))));
         }
      }

      var1.setline(174);
      var12 = var1.getlocal(9);
      var12 = var12._iadd(Py.newInteger(1));
      var1.setlocal(9, var12);

      while(true) {
         var1.setline(175);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(176);
         var12 = var1.getglobal("scanstring").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(1), var1.getlocal(2));
         PyObject[] var13 = Py.unpackSequence(var12, 2);
         PyObject var6 = var13[0];
         var1.setlocal(14, var6);
         var6 = null;
         var6 = var13[1];
         var1.setlocal(9, var6);
         var6 = null;
         var4 = null;
         var1.setline(180);
         var12 = var1.getlocal(8).__getslice__(var1.getlocal(9), var1.getlocal(9)._add(Py.newInteger(1)), (PyObject)null);
         var10000 = var12._ne(PyString.fromInterned(":"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(181);
            var12 = var1.getlocal(6).__call__(var2, var1.getlocal(8), var1.getlocal(9)).__getattr__("end").__call__(var2);
            var1.setlocal(9, var12);
            var4 = null;
            var1.setline(182);
            var12 = var1.getlocal(8).__getslice__(var1.getlocal(9), var1.getlocal(9)._add(Py.newInteger(1)), (PyObject)null);
            var10000 = var12._ne(PyString.fromInterned(":"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(183);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting ':' delimiter"), (PyObject)var1.getlocal(8), (PyObject)var1.getlocal(9))));
            }
         }

         var1.setline(184);
         var12 = var1.getlocal(9);
         var12 = var12._iadd(Py.newInteger(1));
         var1.setlocal(9, var12);

         PyException var16;
         try {
            var1.setline(187);
            var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
            var10000 = var12._in(var1.getlocal(7));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(188);
               var12 = var1.getlocal(9);
               var12 = var12._iadd(Py.newInteger(1));
               var1.setlocal(9, var12);
               var1.setline(189);
               var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
               var10000 = var12._in(var1.getlocal(7));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(190);
                  var12 = var1.getlocal(6).__call__(var2, var1.getlocal(8), var1.getlocal(9)._add(Py.newInteger(1))).__getattr__("end").__call__(var2);
                  var1.setlocal(9, var12);
                  var4 = null;
               }
            }
         } catch (Throwable var10) {
            var16 = Py.setException(var10, var1);
            if (!var16.match(var1.getglobal("IndexError"))) {
               throw var16;
            }

            var1.setline(192);
         }

         try {
            var1.setline(195);
            var12 = var1.getlocal(3).__call__(var2, var1.getlocal(8), var1.getlocal(9));
            var13 = Py.unpackSequence(var12, 2);
            var6 = var13[0];
            var1.setlocal(15, var6);
            var6 = null;
            var6 = var13[1];
            var1.setlocal(9, var6);
            var6 = null;
            var4 = null;
         } catch (Throwable var7) {
            var16 = Py.setException(var7, var1);
            if (var16.match(var1.getglobal("StopIteration"))) {
               var1.setline(197);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting object"), (PyObject)var1.getlocal(8), (PyObject)var1.getlocal(9))));
            }

            throw var16;
         }

         var1.setline(198);
         var1.getlocal(11).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(14), var1.getlocal(15)})));

         PyString var14;
         try {
            var1.setline(201);
            var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
            var1.setlocal(12, var12);
            var4 = null;
            var1.setline(202);
            var12 = var1.getlocal(12);
            var10000 = var12._in(var1.getlocal(7));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(203);
               var12 = var1.getlocal(6).__call__(var2, var1.getlocal(8), var1.getlocal(9)._add(Py.newInteger(1))).__getattr__("end").__call__(var2);
               var1.setlocal(9, var12);
               var4 = null;
               var1.setline(204);
               var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
               var1.setlocal(12, var12);
               var4 = null;
            }
         } catch (Throwable var9) {
            var16 = Py.setException(var9, var1);
            if (!var16.match(var1.getglobal("IndexError"))) {
               throw var16;
            }

            var1.setline(206);
            var14 = PyString.fromInterned("");
            var1.setlocal(12, var14);
            var5 = null;
         }

         var1.setline(207);
         var12 = var1.getlocal(9);
         var12 = var12._iadd(Py.newInteger(1));
         var1.setlocal(9, var12);
         var1.setline(209);
         var12 = var1.getlocal(12);
         var10000 = var12._eq(PyString.fromInterned("}"));
         var4 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(211);
         var12 = var1.getlocal(12);
         var10000 = var12._ne(PyString.fromInterned(","));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(212);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting ',' delimiter"), (PyObject)var1.getlocal(8), (PyObject)var1.getlocal(9)._sub(Py.newInteger(1)))));
         }

         try {
            var1.setline(215);
            var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
            var1.setlocal(12, var12);
            var4 = null;
            var1.setline(216);
            var12 = var1.getlocal(12);
            var10000 = var12._in(var1.getlocal(7));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(217);
               var12 = var1.getlocal(9);
               var12 = var12._iadd(Py.newInteger(1));
               var1.setlocal(9, var12);
               var1.setline(218);
               var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
               var1.setlocal(12, var12);
               var4 = null;
               var1.setline(219);
               var12 = var1.getlocal(12);
               var10000 = var12._in(var1.getlocal(7));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(220);
                  var12 = var1.getlocal(6).__call__(var2, var1.getlocal(8), var1.getlocal(9)._add(Py.newInteger(1))).__getattr__("end").__call__(var2);
                  var1.setlocal(9, var12);
                  var4 = null;
                  var1.setline(221);
                  var12 = var1.getlocal(8).__getitem__(var1.getlocal(9));
                  var1.setlocal(12, var12);
                  var4 = null;
               }
            }
         } catch (Throwable var8) {
            var16 = Py.setException(var8, var1);
            if (!var16.match(var1.getglobal("IndexError"))) {
               throw var16;
            }

            var1.setline(223);
            var14 = PyString.fromInterned("");
            var1.setlocal(12, var14);
            var5 = null;
         }

         var1.setline(225);
         var12 = var1.getlocal(9);
         var12 = var12._iadd(Py.newInteger(1));
         var1.setlocal(9, var12);
         var1.setline(226);
         var12 = var1.getlocal(12);
         var10000 = var12._ne(PyString.fromInterned("\""));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(227);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting property name enclosed in double quotes"), (PyObject)var1.getlocal(8), (PyObject)var1.getlocal(9)._sub(Py.newInteger(1)))));
         }
      }

      var1.setline(229);
      var12 = var1.getlocal(5);
      var10000 = var12._isnot(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(230);
         var12 = var1.getlocal(5).__call__(var2, var1.getlocal(10));
         var1.setlocal(13, var12);
         var4 = null;
         var1.setline(231);
         var15 = new PyTuple(new PyObject[]{var1.getlocal(13), var1.getlocal(9)});
         var1.f_lasti = -1;
         return var15;
      } else {
         var1.setline(232);
         var12 = var1.getglobal("dict").__call__(var2, var1.getlocal(10));
         var1.setlocal(10, var12);
         var4 = null;
         var1.setline(233);
         var12 = var1.getlocal(4);
         var10000 = var12._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(234);
            var12 = var1.getlocal(4).__call__(var2, var1.getlocal(10));
            var1.setlocal(10, var12);
            var4 = null;
         }

         var1.setline(235);
         var15 = new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(9)});
         var1.f_lasti = -1;
         return var15;
      }
   }

   public PyObject JSONArray$6(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(239);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(240);
      var3 = var1.getlocal(4).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(241);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(242);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(4), var1.getlocal(5)._add(Py.newInteger(1))).__getattr__("end").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(243);
         var3 = var1.getlocal(4).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(245);
      var3 = var1.getlocal(7);
      var10000 = var3._eq(PyString.fromInterned("]"));
      var3 = null;
      PyTuple var13;
      if (var10000.__nonzero__()) {
         var1.setline(246);
         var13 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)._add(Py.newInteger(1))});
         var1.f_lasti = -1;
         return var13;
      } else {
         var1.setline(247);
         PyObject var10 = var1.getlocal(6).__getattr__("append");
         var1.setlocal(8, var10);
         var4 = null;

         while(true) {
            var1.setline(248);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            PyException var11;
            try {
               var1.setline(250);
               var10 = var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(5));
               PyObject[] var12 = Py.unpackSequence(var10, 2);
               PyObject var6 = var12[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
            } catch (Throwable var7) {
               var11 = Py.setException(var7, var1);
               if (var11.match(var1.getglobal("StopIteration"))) {
                  var1.setline(252);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting object"), (PyObject)var1.getlocal(4), (PyObject)var1.getlocal(5))));
               }

               throw var11;
            }

            var1.setline(253);
            var1.getlocal(8).__call__(var2, var1.getlocal(9));
            var1.setline(254);
            var10 = var1.getlocal(4).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
            var1.setlocal(7, var10);
            var4 = null;
            var1.setline(255);
            var10 = var1.getlocal(7);
            var10000 = var10._in(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(256);
               var10 = var1.getlocal(2).__call__(var2, var1.getlocal(4), var1.getlocal(5)._add(Py.newInteger(1))).__getattr__("end").__call__(var2);
               var1.setlocal(5, var10);
               var4 = null;
               var1.setline(257);
               var10 = var1.getlocal(4).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
               var1.setlocal(7, var10);
               var4 = null;
            }

            var1.setline(258);
            var10 = var1.getlocal(5);
            var10 = var10._iadd(Py.newInteger(1));
            var1.setlocal(5, var10);
            var1.setline(259);
            var10 = var1.getlocal(7);
            var10000 = var10._eq(PyString.fromInterned("]"));
            var4 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(261);
            var10 = var1.getlocal(7);
            var10000 = var10._ne(PyString.fromInterned(","));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(262);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__((ThreadState)var2, PyString.fromInterned("Expecting ',' delimiter"), (PyObject)var1.getlocal(4), (PyObject)var1.getlocal(5))));
            }

            try {
               var1.setline(264);
               var10 = var1.getlocal(4).__getitem__(var1.getlocal(5));
               var10000 = var10._in(var1.getlocal(3));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(265);
                  var10 = var1.getlocal(5);
                  var10 = var10._iadd(Py.newInteger(1));
                  var1.setlocal(5, var10);
                  var1.setline(266);
                  var10 = var1.getlocal(4).__getitem__(var1.getlocal(5));
                  var10000 = var10._in(var1.getlocal(3));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(267);
                     var10 = var1.getlocal(2).__call__(var2, var1.getlocal(4), var1.getlocal(5)._add(Py.newInteger(1))).__getattr__("end").__call__(var2);
                     var1.setlocal(5, var10);
                     var4 = null;
                  }
               }
            } catch (Throwable var8) {
               var11 = Py.setException(var8, var1);
               if (!var11.match(var1.getglobal("IndexError"))) {
                  throw var11;
               }

               var1.setline(269);
            }
         }

         var1.setline(271);
         var13 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var13;
      }
   }

   public PyObject JSONDecoder$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple JSON <http://json.org> decoder\n\n    Performs the following translations in decoding by default:\n\n    +---------------+-------------------+\n    | JSON          | Python            |\n    +===============+===================+\n    | object        | dict              |\n    +---------------+-------------------+\n    | array         | list              |\n    +---------------+-------------------+\n    | string        | unicode           |\n    +---------------+-------------------+\n    | number (int)  | int, long         |\n    +---------------+-------------------+\n    | number (real) | float             |\n    +---------------+-------------------+\n    | true          | True              |\n    +---------------+-------------------+\n    | false         | False             |\n    +---------------+-------------------+\n    | null          | None              |\n    +---------------+-------------------+\n\n    It also understands ``NaN``, ``Infinity``, and ``-Infinity`` as\n    their corresponding ``float`` values, which is outside the JSON spec.\n\n    "));
      var1.setline(301);
      PyString.fromInterned("Simple JSON <http://json.org> decoder\n\n    Performs the following translations in decoding by default:\n\n    +---------------+-------------------+\n    | JSON          | Python            |\n    +===============+===================+\n    | object        | dict              |\n    +---------------+-------------------+\n    | array         | list              |\n    +---------------+-------------------+\n    | string        | unicode           |\n    +---------------+-------------------+\n    | number (int)  | int, long         |\n    +---------------+-------------------+\n    | number (real) | float             |\n    +---------------+-------------------+\n    | true          | True              |\n    +---------------+-------------------+\n    | false         | False             |\n    +---------------+-------------------+\n    | null          | None              |\n    +---------------+-------------------+\n\n    It also understands ``NaN``, ``Infinity``, and ``-Infinity`` as\n    their corresponding ``float`` values, which is outside the JSON spec.\n\n    ");
      var1.setline(303);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, PyString.fromInterned("``encoding`` determines the encoding used to interpret any ``str``\n        objects decoded by this instance (utf-8 by default).  It has no\n        effect when decoding ``unicode`` objects.\n\n        Note that currently only encodings that are a superset of ASCII work,\n        strings of other encodings should be passed in as ``unicode``.\n\n        ``object_hook``, if specified, will be called with the result\n        of every JSON object decoded and its return value will be used in\n        place of the given ``dict``.  This can be used to provide custom\n        deserializations (e.g. to support JSON-RPC class hinting).\n\n        ``object_pairs_hook``, if specified will be called with the result of\n        every JSON object decoded with an ordered list of pairs.  The return\n        value of ``object_pairs_hook`` will be used instead of the ``dict``.\n        This feature can be used to implement custom decoders that rely on the\n        order that the key and value pairs are decoded (for example,\n        collections.OrderedDict will remember the order of insertion). If\n        ``object_hook`` is also defined, the ``object_pairs_hook`` takes\n        priority.\n\n        ``parse_float``, if specified, will be called with the string\n        of every JSON float to be decoded. By default this is equivalent to\n        float(num_str). This can be used to use another datatype or parser\n        for JSON floats (e.g. decimal.Decimal).\n\n        ``parse_int``, if specified, will be called with the string\n        of every JSON int to be decoded. By default this is equivalent to\n        int(num_str). This can be used to use another datatype or parser\n        for JSON integers (e.g. float).\n\n        ``parse_constant``, if specified, will be called with one of the\n        following strings: -Infinity, Infinity, NaN.\n        This can be used to raise an exception if invalid JSON numbers\n        are encountered.\n\n        If ``strict`` is false (true is the default), then control\n        characters will be allowed inside strings.  Control characters in\n        this context are those with character codes in the 0-31 range,\n        including ``'\\t'`` (tab), ``'\\n'``, ``'\\r'`` and ``'\\0'``.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(360);
      var3 = new PyObject[]{var1.getname("WHITESPACE").__getattr__("match")};
      var4 = new PyFunction(var1.f_globals, var3, decode$9, PyString.fromInterned("Return the Python representation of ``s`` (a ``str`` or ``unicode``\n        instance containing a JSON document)\n\n        "));
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(371);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, raw_decode$10, PyString.fromInterned("Decode a JSON document from ``s`` (a ``str`` or ``unicode``\n        beginning with a JSON document) and return a 2-tuple of the Python\n        representation and the index in ``s`` where the document ended.\n\n        This can be used to decode a JSON document from a string that may\n        have extraneous data at the end.\n\n        "));
      var1.setlocal("raw_decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyString.fromInterned("``encoding`` determines the encoding used to interpret any ``str``\n        objects decoded by this instance (utf-8 by default).  It has no\n        effect when decoding ``unicode`` objects.\n\n        Note that currently only encodings that are a superset of ASCII work,\n        strings of other encodings should be passed in as ``unicode``.\n\n        ``object_hook``, if specified, will be called with the result\n        of every JSON object decoded and its return value will be used in\n        place of the given ``dict``.  This can be used to provide custom\n        deserializations (e.g. to support JSON-RPC class hinting).\n\n        ``object_pairs_hook``, if specified will be called with the result of\n        every JSON object decoded with an ordered list of pairs.  The return\n        value of ``object_pairs_hook`` will be used instead of the ``dict``.\n        This feature can be used to implement custom decoders that rely on the\n        order that the key and value pairs are decoded (for example,\n        collections.OrderedDict will remember the order of insertion). If\n        ``object_hook`` is also defined, the ``object_pairs_hook`` takes\n        priority.\n\n        ``parse_float``, if specified, will be called with the string\n        of every JSON float to be decoded. By default this is equivalent to\n        float(num_str). This can be used to use another datatype or parser\n        for JSON floats (e.g. decimal.Decimal).\n\n        ``parse_int``, if specified, will be called with the string\n        of every JSON int to be decoded. By default this is equivalent to\n        int(num_str). This can be used to use another datatype or parser\n        for JSON integers (e.g. float).\n\n        ``parse_constant``, if specified, will be called with one of the\n        following strings: -Infinity, Infinity, NaN.\n        This can be used to raise an exception if invalid JSON numbers\n        are encountered.\n\n        If ``strict`` is false (true is the default), then control\n        characters will be allowed inside strings.  Control characters in\n        this context are those with character codes in the 0-31 range,\n        including ``'\\t'`` (tab), ``'\\n'``, ``'\\r'`` and ``'\\0'``.\n\n        ");
      var1.setline(348);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.setline(349);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("object_hook", var3);
      var3 = null;
      var1.setline(350);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("object_pairs_hook", var3);
      var3 = null;
      var1.setline(351);
      PyObject var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("float");
      }

      var3 = var10000;
      var1.getlocal(0).__setattr__("parse_float", var3);
      var3 = null;
      var1.setline(352);
      var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("int");
      }

      var3 = var10000;
      var1.getlocal(0).__setattr__("parse_int", var3);
      var3 = null;
      var1.setline(353);
      var10000 = var1.getlocal(5);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("_CONSTANTS").__getattr__("__getitem__");
      }

      var3 = var10000;
      var1.getlocal(0).__setattr__("parse_constant", var3);
      var3 = null;
      var1.setline(354);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("strict", var3);
      var3 = null;
      var1.setline(355);
      var3 = var1.getglobal("JSONObject");
      var1.getlocal(0).__setattr__("parse_object", var3);
      var3 = null;
      var1.setline(356);
      var3 = var1.getglobal("JSONArray");
      var1.getlocal(0).__setattr__("parse_array", var3);
      var3 = null;
      var1.setline(357);
      var3 = var1.getglobal("scanstring");
      var1.getlocal(0).__setattr__("parse_string", var3);
      var3 = null;
      var1.setline(358);
      var3 = var1.getglobal("scanner").__getattr__("make_scanner").__call__(var2, var1.getlocal(0));
      var1.getlocal(0).__setattr__("scan_once", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$9(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyString.fromInterned("Return the Python representation of ``s`` (a ``str`` or ``unicode``\n        instance containing a JSON document)\n\n        ");
      var1.setline(365);
      PyObject var10000 = var1.getlocal(0).__getattr__("raw_decode");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0)).__getattr__("end").__call__(var2)};
      String[] var4 = new String[]{"idx"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(366);
      var6 = var1.getlocal(2).__call__(var2, var1.getlocal(1), var1.getlocal(4)).__getattr__("end").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(367);
      var6 = var1.getlocal(4);
      var10000 = var6._ne(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(368);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("errmsg").__call__(var2, PyString.fromInterned("Extra data"), var1.getlocal(1), var1.getlocal(4), var1.getglobal("len").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(369);
         var6 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject raw_decode$10(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyString.fromInterned("Decode a JSON document from ``s`` (a ``str`` or ``unicode``\n        beginning with a JSON document) and return a 2-tuple of the Python\n        representation and the index in ``s`` where the document ended.\n\n        This can be used to decode a JSON document from a string that may\n        have extraneous data at the end.\n\n        ");

      PyException var3;
      try {
         var1.setline(381);
         PyObject var7 = var1.getlocal(0).__getattr__("scan_once").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("StopIteration"))) {
            var1.setline(383);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No JSON object could be decoded")));
         }

         throw var3;
      }

      var1.setline(384);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var8;
   }

   public decoder$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"_BYTES", "nan", "inf"};
      _floatconstants$1 = Py.newCode(0, var2, var1, "_floatconstants", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"doc", "pos", "lineno", "colno"};
      linecol$2 = Py.newCode(2, var2, var1, "linecol", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "doc", "pos", "end", "lineno", "colno", "fmt", "endlineno", "endcolno"};
      errmsg$3 = Py.newCode(4, var2, var1, "errmsg", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "end", "encoding", "strict", "_b", "_m", "chunks", "_append", "begin", "chunk", "content", "terminator", "msg", "esc", "char", "next_end", "uni", "esc2", "uni2"};
      py_scanstring$4 = Py.newCode(6, var2, var1, "py_scanstring", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s_and_end", "encoding", "strict", "scan_once", "object_hook", "object_pairs_hook", "_w", "_ws", "s", "end", "pairs", "pairs_append", "nextchar", "result", "key", "value"};
      JSONObject$5 = Py.newCode(8, var2, var1, "JSONObject", 149, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s_and_end", "scan_once", "_w", "_ws", "s", "end", "values", "nextchar", "_append", "value"};
      JSONArray$6 = Py.newCode(4, var2, var1, "JSONArray", 237, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JSONDecoder$7 = Py.newCode(0, var2, var1, "JSONDecoder", 273, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "encoding", "object_hook", "parse_float", "parse_int", "parse_constant", "strict", "object_pairs_hook"};
      __init__$8 = Py.newCode(8, var2, var1, "__init__", 303, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "_w", "obj", "end"};
      decode$9 = Py.newCode(3, var2, var1, "decode", 360, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "idx", "obj", "end"};
      raw_decode$10 = Py.newCode(3, var2, var1, "raw_decode", 371, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new decoder$py("json/decoder$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(decoder$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._floatconstants$1(var2, var3);
         case 2:
            return this.linecol$2(var2, var3);
         case 3:
            return this.errmsg$3(var2, var3);
         case 4:
            return this.py_scanstring$4(var2, var3);
         case 5:
            return this.JSONObject$5(var2, var3);
         case 6:
            return this.JSONArray$6(var2, var3);
         case 7:
            return this.JSONDecoder$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.decode$9(var2, var3);
         case 10:
            return this.raw_decode$10(var2, var3);
         default:
            return null;
      }
   }
}
