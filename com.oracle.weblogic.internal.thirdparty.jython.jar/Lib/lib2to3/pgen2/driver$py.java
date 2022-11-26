package lib2to3.pgen2;

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
@MTime(1498849383000L)
@Filename("lib2to3/pgen2/driver.py")
public class driver$py extends PyFunctionTable implements PyRunnable {
   static driver$py self;
   static final PyCode f$0;
   static final PyCode Driver$1;
   static final PyCode __init__$2;
   static final PyCode parse_tokens$3;
   static final PyCode parse_stream_raw$4;
   static final PyCode parse_stream$5;
   static final PyCode parse_file$6;
   static final PyCode parse_string$7;
   static final PyCode load_grammar$8;
   static final PyCode _newer$9;
   static final PyCode main$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Parser driver.\n\nThis provides a high-level interface to parse a file into a syntax tree.\n\n"));
      var1.setline(12);
      PyString.fromInterned("Parser driver.\n\nThis provides a high-level interface to parse a file into a syntax tree.\n\n");
      var1.setline(14);
      PyString var3 = PyString.fromInterned("Guido van Rossum <guido@python.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(16);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("Driver"), PyString.fromInterned("load_grammar")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(19);
      PyObject var6 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var6);
      var3 = null;
      var1.setline(20);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(21);
      var6 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var6);
      var3 = null;
      var1.setline(22);
      var6 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var6);
      var3 = null;
      var1.setline(23);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(26);
      String[] var7 = new String[]{"grammar", "parse", "token", "tokenize", "pgen"};
      PyObject[] var8 = imp.importFrom("", var7, var1, 1);
      PyObject var4 = var8[0];
      var1.setlocal("grammar", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("parse", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("token", var4);
      var4 = null;
      var4 = var8[3];
      var1.setlocal("tokenize", var4);
      var4 = null;
      var4 = var8[4];
      var1.setlocal("pgen", var4);
      var4 = null;
      var1.setline(29);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Driver", var8, Driver$1);
      var1.setlocal("Driver", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(109);
      var8 = new PyObject[]{PyString.fromInterned("Grammar.txt"), var1.getname("None"), var1.getname("True"), var1.getname("False"), var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var8, load_grammar$8, PyString.fromInterned("Load the grammar (maybe from a pickle)."));
      var1.setlocal("load_grammar", var9);
      var3 = null;
      var1.setline(134);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _newer$9, PyString.fromInterned("Inquire whether file a was written since file b."));
      var1.setlocal("_newer", var9);
      var3 = null;
      var1.setline(143);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, main$10, PyString.fromInterned("Main program, when run as a script: produce grammar pickle files.\n\n    Calls load_grammar for each argument, a path to a grammar text file.\n    "));
      var1.setlocal("main", var9);
      var3 = null;
      var1.setline(156);
      var6 = var1.getname("__name__");
      PyObject var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(157);
         var1.getname("sys").__getattr__("exit").__call__(var2, var1.getname("int").__call__(var2, var1.getname("main").__call__(var2).__not__()));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Driver$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(38);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parse_tokens$3, PyString.fromInterned("Parse a series of tokens and return the syntax tree."));
      var1.setlocal("parse_tokens", var4);
      var3 = null;
      var1.setline(86);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parse_stream_raw$4, PyString.fromInterned("Parse a stream and return the syntax tree."));
      var1.setlocal("parse_stream_raw", var4);
      var3 = null;
      var1.setline(91);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parse_stream$5, PyString.fromInterned("Parse a stream and return the syntax tree."));
      var1.setlocal("parse_stream", var4);
      var3 = null;
      var1.setline(95);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parse_file$6, PyString.fromInterned("Parse a file and return the syntax tree."));
      var1.setlocal("parse_file", var4);
      var3 = null;
      var1.setline(103);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, parse_string$7, PyString.fromInterned("Parse a string and return the syntax tree."));
      var1.setlocal("parse_string", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("grammar", var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(34);
         var3 = var1.getglobal("logging").__getattr__("getLogger").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(35);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("logger", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("convert", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_tokens$3(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Parse a series of tokens and return the syntax tree.");
      var1.setline(41);
      PyObject var3 = var1.getglobal("parse").__getattr__("Parser").__call__(var2, var1.getlocal(0).__getattr__("grammar"), var1.getlocal(0).__getattr__("convert"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(42);
      var1.getlocal(3).__getattr__("setup").__call__(var2);
      var1.setline(43);
      PyInteger var8 = Py.newInteger(1);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(44);
      var8 = Py.newInteger(0);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(45);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var1.setlocal(7, var3);
      var1.setlocal(8, var3);
      var1.setlocal(9, var3);
      var1.setlocal(10, var3);
      var1.setline(46);
      PyUnicode var9 = PyUnicode.fromInterned("");
      var1.setlocal(11, var9);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(47);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(82);
            throw Py.makeException(var1.getglobal("parse").__getattr__("ParseError").__call__(var2, PyString.fromInterned("incomplete input"), var1.getlocal(6), var1.getlocal(7), new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(8)})));
         }

         var1.setlocal(12, var4);
         var1.setline(48);
         PyObject var5 = var1.getlocal(12);
         PyObject[] var6 = Py.unpackSequence(var5, 5);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var6[3];
         var1.setlocal(9, var7);
         var7 = null;
         var7 = var6[4];
         var1.setlocal(10, var7);
         var7 = null;
         var5 = null;
         var1.setline(49);
         var5 = var1.getlocal(8);
         PyObject var10000 = var5._ne(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
         var5 = null;
         PyInteger var11;
         if (var10000.__nonzero__()) {
            var1.setline(50);
            if (var1.getglobal("__debug__").__nonzero__()) {
               PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
               var10000 = var10._le(var1.getlocal(8));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}), var1.getlocal(8)}));
               }
            }

            var1.setline(51);
            var5 = var1.getlocal(8);
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(13, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(14, var7);
            var7 = null;
            var5 = null;
            var1.setline(52);
            var5 = var1.getlocal(4);
            var10000 = var5._lt(var1.getlocal(13));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(53);
               var5 = var1.getlocal(11);
               var5 = var5._iadd(PyString.fromInterned("\n")._mul(var1.getlocal(13)._sub(var1.getlocal(4))));
               var1.setlocal(11, var5);
               var1.setline(54);
               var5 = var1.getlocal(13);
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(55);
               var11 = Py.newInteger(0);
               var1.setlocal(5, var11);
               var5 = null;
            }

            var1.setline(56);
            var5 = var1.getlocal(5);
            var10000 = var5._lt(var1.getlocal(14));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(57);
               var5 = var1.getlocal(11);
               var5 = var5._iadd(var1.getlocal(10).__getslice__(var1.getlocal(5), var1.getlocal(14), (PyObject)null));
               var1.setlocal(11, var5);
               var1.setline(58);
               var5 = var1.getlocal(14);
               var1.setlocal(5, var5);
               var5 = null;
            }
         }

         var1.setline(59);
         var5 = var1.getlocal(6);
         var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("tokenize").__getattr__("COMMENT"), var1.getglobal("tokenize").__getattr__("NL")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(60);
            var5 = var1.getlocal(11);
            var5 = var5._iadd(var1.getlocal(7));
            var1.setlocal(11, var5);
            var1.setline(61);
            var5 = var1.getlocal(9);
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
            var1.setline(62);
            if (var1.getlocal(7).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__nonzero__()) {
               var1.setline(63);
               var5 = var1.getlocal(4);
               var5 = var5._iadd(Py.newInteger(1));
               var1.setlocal(4, var5);
               var1.setline(64);
               var11 = Py.newInteger(0);
               var1.setlocal(5, var11);
               var5 = null;
            }
         } else {
            var1.setline(66);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(var1.getglobal("token").__getattr__("OP"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(67);
               var5 = var1.getglobal("grammar").__getattr__("opmap").__getitem__(var1.getlocal(7));
               var1.setlocal(6, var5);
               var5 = null;
            }

            var1.setline(68);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(69);
               var1.getlocal(0).__getattr__("logger").__getattr__("debug").__call__(var2, PyString.fromInterned("%s %r (prefix=%r)"), var1.getglobal("token").__getattr__("tok_name").__getitem__(var1.getlocal(6)), var1.getlocal(7), var1.getlocal(11));
            }

            var1.setline(71);
            if (var1.getlocal(3).__getattr__("addtoken").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)var1.getlocal(7), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(8)}))).__nonzero__()) {
               var1.setline(72);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(73);
                  var1.getlocal(0).__getattr__("logger").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Stop."));
               }

               var1.setline(84);
               var3 = var1.getlocal(3).__getattr__("rootnode");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(75);
            PyString var12 = PyString.fromInterned("");
            var1.setlocal(11, var12);
            var5 = null;
            var1.setline(76);
            var5 = var1.getlocal(9);
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
            var1.setline(77);
            if (var1.getlocal(7).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__nonzero__()) {
               var1.setline(78);
               var5 = var1.getlocal(4);
               var5 = var5._iadd(Py.newInteger(1));
               var1.setlocal(4, var5);
               var1.setline(79);
               var11 = Py.newInteger(0);
               var1.setlocal(5, var11);
               var5 = null;
            }
         }
      }
   }

   public PyObject parse_stream_raw$4(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("Parse a stream and return the syntax tree.");
      var1.setline(88);
      PyObject var3 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(1).__getattr__("readline"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(0).__getattr__("parse_tokens").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_stream$5(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("Parse a stream and return the syntax tree.");
      var1.setline(93);
      PyObject var3 = var1.getlocal(0).__getattr__("parse_stream_raw").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_file$6(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyString.fromInterned("Parse a file and return the syntax tree.");
      var1.setline(97);
      PyObject var3 = var1.getglobal("codecs").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("r"), (PyObject)var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(99);
            var4 = var1.getlocal(0).__getattr__("parse_stream").__call__(var2, var1.getlocal(4), var1.getlocal(3));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(101);
         var1.getlocal(4).__getattr__("close").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(101);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      throw (Throwable)var7;
   }

   public PyObject parse_string$7(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Parse a string and return the syntax tree.");
      var1.setline(105);
      PyObject var3 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(1)).__getattr__("readline"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(0).__getattr__("parse_tokens").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_grammar$8(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyString.fromInterned("Load the grammar (maybe from a pickle).");
      var1.setline(112);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(113);
         var3 = var1.getglobal("logging").__getattr__("getLogger").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(114);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(0));
         var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(116);
         var3 = var1.getlocal(6);
         var10000 = var3._eq(PyString.fromInterned(".txt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(117);
            PyString var8 = PyString.fromInterned("");
            var1.setlocal(6, var8);
            var3 = null;
         }

         var1.setline(118);
         var3 = var1.getlocal(5)._add(var1.getlocal(6))._add(PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getglobal("sys").__getattr__("version_info"))))._add(PyString.fromInterned(".pickle"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(119);
      var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("_newer").__call__(var2, var1.getlocal(1), var1.getlocal(0)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(120);
         var1.getlocal(4).__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Generating grammar tables from %s"), (PyObject)var1.getlocal(0));
         var1.setline(121);
         var3 = var1.getglobal("pgen").__getattr__("generate_grammar").__call__(var2, var1.getlocal(0));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(122);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(123);
            var1.getlocal(4).__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Writing grammar tables to %s"), (PyObject)var1.getlocal(1));

            try {
               var1.setline(125);
               var1.getlocal(7).__getattr__("dump").__call__(var2, var1.getlocal(1));
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (!var9.match(var1.getglobal("IOError"))) {
                  throw var9;
               }

               PyObject var7 = var9.value;
               var1.setlocal(8, var7);
               var4 = null;
               var1.setline(127);
               var1.getlocal(4).__getattr__("info").__call__(var2, PyString.fromInterned("Writing failed:")._add(var1.getglobal("str").__call__(var2, var1.getlocal(8))));
            }
         }
      } else {
         var1.setline(129);
         var3 = var1.getglobal("grammar").__getattr__("Grammar").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(130);
         var1.getlocal(7).__getattr__("load").__call__(var2, var1.getlocal(1));
      }

      var1.setline(131);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _newer$9(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyString.fromInterned("Inquire whether file a was written since file b.");
      var1.setline(136);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(137);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(138);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(139);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(140);
            PyObject var4 = var1.getglobal("os").__getattr__("path").__getattr__("getmtime").__call__(var2, var1.getlocal(0));
            PyObject var10000 = var4._ge(var1.getglobal("os").__getattr__("path").__getattr__("getmtime").__call__(var2, var1.getlocal(1)));
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject main$10(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyString.fromInterned("Main program, when run as a script: produce grammar pickle files.\n\n    Calls load_grammar for each argument, a path to a grammar text file.\n    ");
      var1.setline(148);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(149);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(150);
      PyObject var10000 = var1.getglobal("logging").__getattr__("basicConfig");
      PyObject[] var7 = new PyObject[]{var1.getglobal("logging").__getattr__("INFO"), var1.getglobal("sys").__getattr__("stdout"), PyString.fromInterned("%(message)s")};
      String[] var4 = new String[]{"level", "stream", "format"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(152);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(152);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(154);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var8);
         var1.setline(153);
         var10000 = var1.getglobal("load_grammar");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True"), var1.getglobal("True")};
         String[] var6 = new String[]{"save", "force"};
         var10000.__call__(var2, var5, var6);
         var5 = null;
      }
   }

   public driver$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Driver$1 = Py.newCode(0, var2, var1, "Driver", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "grammar", "convert", "logger"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tokens", "debug", "p", "lineno", "column", "type", "value", "start", "end", "line_text", "prefix", "quintuple", "s_lineno", "s_column"};
      parse_tokens$3 = Py.newCode(3, var2, var1, "parse_tokens", 38, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stream", "debug", "tokens"};
      parse_stream_raw$4 = Py.newCode(3, var2, var1, "parse_stream_raw", 86, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stream", "debug"};
      parse_stream$5 = Py.newCode(3, var2, var1, "parse_stream", 91, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "encoding", "debug", "stream"};
      parse_file$6 = Py.newCode(4, var2, var1, "parse_file", 95, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "debug", "tokens"};
      parse_string$7 = Py.newCode(3, var2, var1, "parse_string", 103, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"gt", "gp", "save", "force", "logger", "head", "tail", "g", "e"};
      load_grammar$8 = Py.newCode(5, var2, var1, "load_grammar", 109, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b"};
      _newer$9 = Py.newCode(2, var2, var1, "_newer", 134, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "gt"};
      main$10 = Py.newCode(1, var2, var1, "main", 143, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new driver$py("lib2to3/pgen2/driver$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(driver$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Driver$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.parse_tokens$3(var2, var3);
         case 4:
            return this.parse_stream_raw$4(var2, var3);
         case 5:
            return this.parse_stream$5(var2, var3);
         case 6:
            return this.parse_file$6(var2, var3);
         case 7:
            return this.parse_string$7(var2, var3);
         case 8:
            return this.load_grammar$8(var2, var3);
         case 9:
            return this._newer$9(var2, var3);
         case 10:
            return this.main$10(var2, var3);
         default:
            return null;
      }
   }
}
