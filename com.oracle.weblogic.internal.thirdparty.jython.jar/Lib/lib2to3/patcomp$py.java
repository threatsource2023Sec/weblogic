package lib2to3;

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
@Filename("lib2to3/patcomp.py")
public class patcomp$py extends PyFunctionTable implements PyRunnable {
   static patcomp$py self;
   static final PyCode f$0;
   static final PyCode PatternSyntaxError$1;
   static final PyCode tokenize_wrapper$2;
   static final PyCode PatternCompiler$3;
   static final PyCode __init__$4;
   static final PyCode compile_pattern$5;
   static final PyCode compile_node$6;
   static final PyCode compile_basic$7;
   static final PyCode get_int$8;
   static final PyCode _type_of_literal$9;
   static final PyCode pattern_convert$10;
   static final PyCode compile_pattern$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Pattern compiler.\n\nThe grammer is taken from PatternGrammar.txt.\n\nThe compiler compiles a pattern to a pytree.*Pattern instance.\n"));
      var1.setline(9);
      PyString.fromInterned("Pattern compiler.\n\nThe grammer is taken from PatternGrammar.txt.\n\nThe compiler compiles a pattern to a pytree.*Pattern instance.\n");
      var1.setline(11);
      PyString var3 = PyString.fromInterned("Guido van Rossum <guido@python.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(14);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(15);
      var5 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var5);
      var3 = null;
      var1.setline(18);
      String[] var6 = new String[]{"driver", "literals", "token", "tokenize", "parse", "grammar"};
      PyObject[] var7 = imp.importFrom("pgen2", var6, var1, 1);
      PyObject var4 = var7[0];
      var1.setlocal("driver", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("literals", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("token", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("tokenize", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("parse", var4);
      var4 = null;
      var4 = var7[5];
      var1.setlocal("grammar", var4);
      var4 = null;
      var1.setline(21);
      var6 = new String[]{"pytree"};
      var7 = imp.importFrom("", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(22);
      var6 = new String[]{"pygram"};
      var7 = imp.importFrom("", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("pygram", var4);
      var4 = null;
      var1.setline(25);
      var5 = var1.getname("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("__file__")), (PyObject)PyString.fromInterned("PatternGrammar.txt"));
      var1.setlocal("_PATTERN_GRAMMAR_FILE", var5);
      var3 = null;
      var1.setline(29);
      var7 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("PatternSyntaxError", var7, PatternSyntaxError$1);
      var1.setlocal("PatternSyntaxError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(33);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, tokenize_wrapper$2, PyString.fromInterned("Tokenizes a string suppressing significant whitespace."));
      var1.setlocal("tokenize_wrapper", var8);
      var3 = null;
      var1.setline(43);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("PatternCompiler", var7, PatternCompiler$3);
      var1.setlocal("PatternCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(180);
      PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("NAME"), var1.getname("token").__getattr__("NAME"), PyString.fromInterned("STRING"), var1.getname("token").__getattr__("STRING"), PyString.fromInterned("NUMBER"), var1.getname("token").__getattr__("NUMBER"), PyString.fromInterned("TOKEN"), var1.getname("None")});
      var1.setlocal("TOKEN_MAP", var9);
      var3 = null;
      var1.setline(186);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _type_of_literal$9, (PyObject)null);
      var1.setlocal("_type_of_literal", var8);
      var3 = null;
      var1.setline(195);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, pattern_convert$10, PyString.fromInterned("Converts raw node information to a Node or Leaf instance."));
      var1.setlocal("pattern_convert", var8);
      var3 = null;
      var1.setline(204);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, compile_pattern$11, (PyObject)null);
      var1.setlocal("compile_pattern", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PatternSyntaxError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(30);
      return var1.getf_locals();
   }

   public PyObject tokenize_wrapper$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var10;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(34);
            PyString.fromInterned("Tokenizes a string suppressing significant whitespace.");
            var1.setline(35);
            var10 = var1.getglobal("set");
            PyObject[] var8 = new PyObject[]{var1.getglobal("token").__getattr__("NEWLINE"), var1.getglobal("token").__getattr__("INDENT"), var1.getglobal("token").__getattr__("DEDENT")};
            PyTuple var10002 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            var3 = var10.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(36);
            var3 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(0)).__getattr__("readline"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(37);
            var3 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
      }

      do {
         var1.setline(37);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(38);
         PyObject var9 = var1.getlocal(3);
         PyObject[] var6 = Py.unpackSequence(var9, 5);
         PyObject var7 = var6[0];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[3];
         var1.setlocal(7, var7);
         var7 = null;
         var7 = var6[4];
         var1.setlocal(8, var7);
         var7 = null;
         var5 = null;
         var1.setline(39);
         var9 = var1.getlocal(4);
         var10 = var9._notin(var1.getlocal(1));
         var5 = null;
      } while(!var10.__nonzero__());

      var1.setline(40);
      var1.setline(40);
      var10 = var1.getlocal(3);
      var1.f_lasti = 1;
      var5 = new Object[8];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var10;
   }

   public PyObject PatternCompiler$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(45);
      PyObject[] var3 = new PyObject[]{var1.getname("_PATTERN_GRAMMAR_FILE")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, PyString.fromInterned("Initializer.\n\n        Takes an optional alternative filename for the pattern grammar.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(56);
      var3 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, compile_pattern$5, PyString.fromInterned("Compiles a pattern string to a nested pytree.*Pattern object."));
      var1.setlocal("compile_pattern", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compile_node$6, PyString.fromInterned("Compiles a node, recursively.\n\n        This is one big switch on the node type.\n        "));
      var1.setlocal("compile_node", var4);
      var3 = null;
      var1.setline(139);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, compile_basic$7, (PyObject)null);
      var1.setlocal("compile_basic", var4);
      var3 = null;
      var1.setline(174);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_int$8, (PyObject)null);
      var1.setlocal("get_int", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString.fromInterned("Initializer.\n\n        Takes an optional alternative filename for the pattern grammar.\n        ");
      var1.setline(50);
      PyObject var3 = var1.getglobal("driver").__getattr__("load_grammar").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("grammar", var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getglobal("pygram").__getattr__("Symbols").__call__(var2, var1.getlocal(0).__getattr__("grammar"));
      var1.getlocal(0).__setattr__("syms", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getglobal("pygram").__getattr__("python_grammar");
      var1.getlocal(0).__setattr__("pygrammar", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("pygram").__getattr__("python_symbols");
      var1.getlocal(0).__setattr__("pysyms", var3);
      var3 = null;
      var1.setline(54);
      PyObject var10000 = var1.getglobal("driver").__getattr__("Driver");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("grammar"), var1.getglobal("pattern_convert")};
      String[] var4 = new String[]{"convert"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("driver", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile_pattern$5(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyString.fromInterned("Compiles a pattern string to a nested pytree.*Pattern object.");
      var1.setline(58);
      PyObject var3 = var1.getglobal("tokenize_wrapper").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;

      try {
         var1.setline(60);
         PyObject var10000 = var1.getlocal(0).__getattr__("driver").__getattr__("parse_tokens");
         PyObject[] var7 = new PyObject[]{var1.getlocal(4), var1.getlocal(2)};
         String[] var8 = new String[]{"debug"};
         var10000 = var10000.__call__(var2, var7, var8);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("parse").__getattr__("ParseError"))) {
            PyObject var4 = var6.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(62);
            throw Py.makeException(var1.getglobal("PatternSyntaxError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6))));
         }

         throw var6;
      }

      var1.setline(63);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(64);
         PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(5)), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(66);
         var3 = var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(5));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject compile_node$6(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Compiles a node, recursively.\n\n        This is one big switch on the node type.\n        ");
      var1.setline(75);
      PyObject var3 = var1.getlocal(1).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("syms").__getattr__("Matcher"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(78);
      var3 = var1.getlocal(1).__getattr__("type");
      var10000 = var3._eq(var1.getlocal(0).__getattr__("syms").__getattr__("Alternatives"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      String[] var7;
      PyObject[] var9;
      PyList var12;
      if (var10000.__nonzero__()) {
         var1.setline(80);
         var12 = new PyList();
         var3 = var12.__getattr__("append");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(80);
         var3 = var1.getlocal(1).__getattr__("children").__getslice__((PyObject)null, (PyObject)null, Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(80);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(80);
               var1.dellocal(3);
               PyList var10 = var12;
               var1.setlocal(2, var10);
               var3 = null;
               var1.setline(81);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(82);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(83);
                  var10000 = var1.getglobal("pytree").__getattr__("WildcardPattern");
                  var9 = new PyObject[3];
                  PyList var10002 = new PyList();
                  var5 = var10002.__getattr__("append");
                  var1.setlocal(6, var5);
                  var5 = null;
                  var1.setline(83);
                  var5 = var1.getlocal(2).__iter__();

                  while(true) {
                     var1.setline(83);
                     PyObject var6 = var5.__iternext__();
                     if (var6 == null) {
                        var1.setline(83);
                        var1.dellocal(6);
                        var9[0] = var10002;
                        var9[1] = Py.newInteger(1);
                        var9[2] = Py.newInteger(1);
                        var7 = new String[]{"min", "max"};
                        var10000 = var10000.__call__(var2, var9, var7);
                        var4 = null;
                        var4 = var10000;
                        var1.setlocal(5, var4);
                        var4 = null;
                        var1.setline(84);
                        var3 = var1.getlocal(5).__getattr__("optimize").__call__(var2);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(7, var6);
                     var1.setline(83);
                     var1.getlocal(6).__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(7)})));
                  }
               }
            }

            var1.setlocal(4, var4);
            var1.setline(80);
            var1.getlocal(3).__call__(var2, var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(4)));
         }
      } else {
         var1.setline(86);
         var4 = var1.getlocal(1).__getattr__("type");
         var10000 = var4._eq(var1.getlocal(0).__getattr__("syms").__getattr__("Alternative"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(93);
            var4 = var1.getlocal(1).__getattr__("type");
            var10000 = var4._eq(var1.getlocal(0).__getattr__("syms").__getattr__("NegatedUnit"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(94);
               var4 = var1.getlocal(0).__getattr__("compile_basic").__call__(var2, var1.getlocal(1).__getattr__("children").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(95);
               var4 = var1.getglobal("pytree").__getattr__("NegatedPattern").__call__(var2, var1.getlocal(10));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(96);
               var3 = var1.getlocal(5).__getattr__("optimize").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(98);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var4 = var1.getlocal(1).__getattr__("type");
                  var10000 = var4._eq(var1.getlocal(0).__getattr__("syms").__getattr__("Unit"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(100);
               var4 = var1.getglobal("None");
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(101);
               var4 = var1.getlocal(1).__getattr__("children");
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(102);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
               var10000 = var4._ge(Py.newInteger(3));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(12).__getitem__(Py.newInteger(1)).__getattr__("type");
                  var10000 = var4._eq(var1.getglobal("token").__getattr__("EQUAL"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(103);
                  var4 = var1.getlocal(12).__getitem__(Py.newInteger(0)).__getattr__("value");
                  var1.setlocal(11, var4);
                  var4 = null;
                  var1.setline(104);
                  var4 = var1.getlocal(12).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
                  var1.setlocal(12, var4);
                  var4 = null;
               }

               var1.setline(105);
               var4 = var1.getglobal("None");
               var1.setlocal(13, var4);
               var4 = null;
               var1.setline(106);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
               var10000 = var4._ge(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(12).__getitem__(Py.newInteger(-1)).__getattr__("type");
                  var10000 = var4._eq(var1.getlocal(0).__getattr__("syms").__getattr__("Repeater"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(107);
                  var4 = var1.getlocal(12).__getitem__(Py.newInteger(-1));
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(108);
                  var4 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(12, var4);
                  var4 = null;
               }

               var1.setline(111);
               var4 = var1.getlocal(0).__getattr__("compile_basic").__call__(var2, var1.getlocal(12), var1.getlocal(13));
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(113);
               var4 = var1.getlocal(13);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(114);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var4 = var1.getlocal(13).__getattr__("type");
                     var10000 = var4._eq(var1.getlocal(0).__getattr__("syms").__getattr__("Repeater"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(115);
                  var4 = var1.getlocal(13).__getattr__("children");
                  var1.setlocal(14, var4);
                  var4 = null;
                  var1.setline(116);
                  var4 = var1.getlocal(14).__getitem__(Py.newInteger(0));
                  var1.setlocal(15, var4);
                  var4 = null;
                  var1.setline(117);
                  var4 = var1.getlocal(15).__getattr__("type");
                  var10000 = var4._eq(var1.getglobal("token").__getattr__("STAR"));
                  var4 = null;
                  PyInteger var11;
                  if (var10000.__nonzero__()) {
                     var1.setline(118);
                     var11 = Py.newInteger(0);
                     var1.setlocal(16, var11);
                     var4 = null;
                     var1.setline(119);
                     var4 = var1.getglobal("pytree").__getattr__("HUGE");
                     var1.setlocal(17, var4);
                     var4 = null;
                  } else {
                     var1.setline(120);
                     var4 = var1.getlocal(15).__getattr__("type");
                     var10000 = var4._eq(var1.getglobal("token").__getattr__("PLUS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(121);
                        var11 = Py.newInteger(1);
                        var1.setlocal(16, var11);
                        var4 = null;
                        var1.setline(122);
                        var4 = var1.getglobal("pytree").__getattr__("HUGE");
                        var1.setlocal(17, var4);
                        var4 = null;
                     } else {
                        var1.setline(123);
                        var4 = var1.getlocal(15).__getattr__("type");
                        var10000 = var4._eq(var1.getglobal("token").__getattr__("LBRACE"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(124);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var4 = var1.getlocal(14).__getitem__(Py.newInteger(-1)).__getattr__("type");
                              var10000 = var4._eq(var1.getglobal("token").__getattr__("RBRACE"));
                              var4 = null;
                              if (!var10000.__nonzero__()) {
                                 var10000 = Py.None;
                                 throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                              }
                           }

                           var1.setline(125);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var4 = var1.getglobal("len").__call__(var2, var1.getlocal(14));
                              var10000 = var4._in(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(5)}));
                              var4 = null;
                              if (!var10000.__nonzero__()) {
                                 var10000 = Py.None;
                                 throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                              }
                           }

                           var1.setline(126);
                           var4 = var1.getlocal(0).__getattr__("get_int").__call__(var2, var1.getlocal(14).__getitem__(Py.newInteger(1)));
                           var1.setlocal(16, var4);
                           var1.setlocal(17, var4);
                           var1.setline(127);
                           var4 = var1.getglobal("len").__call__(var2, var1.getlocal(14));
                           var10000 = var4._eq(Py.newInteger(5));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(128);
                              var4 = var1.getlocal(0).__getattr__("get_int").__call__(var2, var1.getlocal(14).__getitem__(Py.newInteger(3)));
                              var1.setlocal(17, var4);
                              var4 = null;
                           }
                        } else {
                           var1.setline(130);
                           if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("False").__nonzero__()) {
                              var10000 = Py.None;
                              throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                           }
                        }
                     }
                  }

                  var1.setline(131);
                  var4 = var1.getlocal(16);
                  var10000 = var4._ne(Py.newInteger(1));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(17);
                     var10000 = var4._ne(Py.newInteger(1));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(132);
                     var4 = var1.getlocal(10).__getattr__("optimize").__call__(var2);
                     var1.setlocal(10, var4);
                     var4 = null;
                     var1.setline(133);
                     var10000 = var1.getglobal("pytree").__getattr__("WildcardPattern");
                     var9 = new PyObject[]{new PyList(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(10)})}), var1.getlocal(16), var1.getlocal(17)};
                     var7 = new String[]{"min", "max"};
                     var10000 = var10000.__call__(var2, var9, var7);
                     var4 = null;
                     var4 = var10000;
                     var1.setlocal(10, var4);
                     var4 = null;
                  }
               }

               var1.setline(135);
               var4 = var1.getlocal(11);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(136);
                  var4 = var1.getlocal(11);
                  var1.getlocal(10).__setattr__("name", var4);
                  var4 = null;
               }

               var1.setline(137);
               var3 = var1.getlocal(10).__getattr__("optimize").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(87);
            var12 = new PyList();
            var4 = var12.__getattr__("append");
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(87);
            var4 = var1.getlocal(1).__getattr__("children").__iter__();

            while(true) {
               var1.setline(87);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(87);
                  var1.dellocal(9);
                  PyList var8 = var12;
                  var1.setlocal(8, var8);
                  var4 = null;
                  var1.setline(88);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(8));
                  var10000 = var4._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(89);
                     var3 = var1.getlocal(8).__getitem__(Py.newInteger(0));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(90);
                     var10000 = var1.getglobal("pytree").__getattr__("WildcardPattern");
                     var9 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(8)}), Py.newInteger(1), Py.newInteger(1)};
                     var7 = new String[]{"min", "max"};
                     var10000 = var10000.__call__(var2, var9, var7);
                     var4 = null;
                     var4 = var10000;
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(91);
                     var3 = var1.getlocal(5).__getattr__("optimize").__call__(var2);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }

               var1.setlocal(4, var5);
               var1.setline(87);
               var1.getlocal(9).__call__(var2, var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(4)));
            }
         }
      }
   }

   public PyObject compile_basic$7(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._ge(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(142);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getlocal(3).__getattr__("type");
      var10000 = var3._eq(var1.getglobal("token").__getattr__("STRING"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(144);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getglobal("literals").__getattr__("evalString").__call__(var2, var1.getlocal(3).__getattr__("value")));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(145);
         var3 = var1.getglobal("pytree").__getattr__("LeafPattern").__call__(var2, var1.getglobal("_type_of_literal").__call__(var2, var1.getlocal(4)), var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(146);
         PyObject var4 = var1.getlocal(3).__getattr__("type");
         var10000 = var4._eq(var1.getglobal("token").__getattr__("NAME"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(147);
            var4 = var1.getlocal(3).__getattr__("value");
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(148);
            if (var1.getlocal(4).__getattr__("isupper").__call__(var2).__nonzero__()) {
               var1.setline(149);
               var4 = var1.getlocal(4);
               var10000 = var4._notin(var1.getglobal("TOKEN_MAP"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(150);
                  throw Py.makeException(var1.getglobal("PatternSyntaxError").__call__(var2, PyString.fromInterned("Invalid token: %r")._mod(var1.getlocal(4))));
               } else {
                  var1.setline(151);
                  if (var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
                     var1.setline(152);
                     throw Py.makeException(var1.getglobal("PatternSyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't have details for token")));
                  } else {
                     var1.setline(153);
                     var3 = var1.getglobal("pytree").__getattr__("LeafPattern").__call__(var2, var1.getglobal("TOKEN_MAP").__getitem__(var1.getlocal(4)));
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            } else {
               var1.setline(155);
               var4 = var1.getlocal(4);
               var10000 = var4._eq(PyString.fromInterned("any"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(156);
                  var4 = var1.getglobal("None");
                  var1.setlocal(5, var4);
                  var4 = null;
               } else {
                  var1.setline(157);
                  if (var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__not__().__nonzero__()) {
                     var1.setline(158);
                     var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("pysyms"), var1.getlocal(4), var1.getglobal("None"));
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(159);
                     var4 = var1.getlocal(5);
                     var10000 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(160);
                        throw Py.makeException(var1.getglobal("PatternSyntaxError").__call__(var2, PyString.fromInterned("Invalid symbol: %r")._mod(var1.getlocal(4))));
                     }
                  }
               }

               var1.setline(161);
               if (var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
                  var1.setline(162);
                  PyList var7 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)).__getattr__("children").__getitem__(Py.newInteger(1)))});
                  var1.setlocal(6, var7);
                  var4 = null;
               } else {
                  var1.setline(164);
                  var4 = var1.getglobal("None");
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(165);
               var3 = var1.getglobal("pytree").__getattr__("NodePattern").__call__(var2, var1.getlocal(5), var1.getlocal(6));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(166);
            var4 = var1.getlocal(3).__getattr__("value");
            var10000 = var4._eq(PyString.fromInterned("("));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(167);
               var3 = var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(168);
               var4 = var1.getlocal(3).__getattr__("value");
               var10000 = var4._eq(PyString.fromInterned("["));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(169);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var4 = var1.getlocal(2);
                     var10000 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(170);
                  var4 = var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(171);
                  var10000 = var1.getglobal("pytree").__getattr__("WildcardPattern");
                  PyObject[] var6 = new PyObject[]{new PyList(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(7)})}), Py.newInteger(0), Py.newInteger(1)};
                  String[] var5 = new String[]{"min", "max"};
                  var10000 = var10000.__call__(var2, var6, var5);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(172);
                  if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("False").__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(3));
                  } else {
                     var1.f_lasti = -1;
                     return Py.None;
                  }
               }
            }
         }
      }
   }

   public PyObject get_int$8(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("type");
         PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("NUMBER"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(176);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("value"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _type_of_literal$9(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyObject var3;
      if (var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("isalpha").__call__(var2).__nonzero__()) {
         var1.setline(188);
         var3 = var1.getglobal("token").__getattr__("NAME");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(189);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._in(var1.getglobal("grammar").__getattr__("opmap"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var3 = var1.getglobal("grammar").__getattr__("opmap").__getitem__(var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(192);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject pattern_convert$10(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyString.fromInterned("Converts raw node information to a Node or Leaf instance.");
      var1.setline(197);
      PyObject var3 = var1.getlocal(1);
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
      var1.setline(198);
      PyObject var10000 = var1.getlocal(5);
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("number2symbol"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(199);
         var10000 = var1.getglobal("pytree").__getattr__("Node");
         PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(5), var1.getlocal(4)};
         String[] var7 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var6, var7);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(201);
         var10000 = var1.getglobal("pytree").__getattr__("Leaf");
         var4 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
         String[] var8 = new String[]{"context"};
         var10000 = var10000.__call__(var2, var4, var8);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject compile_pattern$11(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyObject var3 = var1.getglobal("PatternCompiler").__call__(var2).__getattr__("compile_pattern").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public patcomp$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PatternSyntaxError$1 = Py.newCode(0, var2, var1, "PatternSyntaxError", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "skip", "tokens", "quintuple", "type", "value", "start", "end", "line_text"};
      tokenize_wrapper$2 = Py.newCode(1, var2, var1, "tokenize_wrapper", 33, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      PatternCompiler$3 = Py.newCode(0, var2, var1, "PatternCompiler", 43, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "grammar_file"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "debug", "with_tree", "tokens", "root", "e"};
      compile_pattern$5 = Py.newCode(4, var2, var1, "compile_pattern", 56, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "alts", "_[80_20]", "ch", "p", "_[83_40]", "a", "units", "_[87_21]", "pattern", "name", "nodes", "repeat", "children", "child", "min", "max"};
      compile_node$6 = Py.newCode(2, var2, var1, "compile_node", 68, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "repeat", "node", "value", "type", "content", "subpattern"};
      compile_basic$7 = Py.newCode(3, var2, var1, "compile_basic", 139, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      get_int$8 = Py.newCode(2, var2, var1, "get_int", 174, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      _type_of_literal$9 = Py.newCode(1, var2, var1, "_type_of_literal", 186, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"grammar", "raw_node_info", "type", "value", "context", "children"};
      pattern_convert$10 = Py.newCode(2, var2, var1, "pattern_convert", 195, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern"};
      compile_pattern$11 = Py.newCode(1, var2, var1, "compile_pattern", 204, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new patcomp$py("lib2to3/patcomp$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(patcomp$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PatternSyntaxError$1(var2, var3);
         case 2:
            return this.tokenize_wrapper$2(var2, var3);
         case 3:
            return this.PatternCompiler$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.compile_pattern$5(var2, var3);
         case 6:
            return this.compile_node$6(var2, var3);
         case 7:
            return this.compile_basic$7(var2, var3);
         case 8:
            return this.get_int$8(var2, var3);
         case 9:
            return this._type_of_literal$9(var2, var3);
         case 10:
            return this.pattern_convert$10(var2, var3);
         case 11:
            return this.compile_pattern$11(var2, var3);
         default:
            return null;
      }
   }
}
