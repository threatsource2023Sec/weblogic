package lib2to3.pgen2;

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
@Filename("lib2to3/pgen2/conv.py")
public class conv$py extends PyFunctionTable implements PyRunnable {
   static conv$py self;
   static final PyCode f$0;
   static final PyCode Converter$1;
   static final PyCode run$2;
   static final PyCode parse_graminit_h$3;
   static final PyCode parse_graminit_c$4;
   static final PyCode finish_off$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Convert graminit.[ch] spit out by pgen to Python code.\n\nPgen is the Python parser generator.  It is useful to quickly create a\nparser from a grammar file in Python's grammar notation.  But I don't\nwant my parsers to be written in C (yet), so I'm translating the\nparsing tables to Python data structures and writing a Python parse\nengine.\n\nNote that the token numbers are constants determined by the standard\nPython tokenizer.  The standard token module defines these numbers and\ntheir names (the names are not used much).  The token numbers are\nhardcoded into the Python tokenizer and into pgen.  A Python\nimplementation of the Python tokenizer is also available, in the\nstandard tokenize module.\n\nOn the other hand, symbol numbers (representing the grammar's\nnon-terminals) are assigned by pgen based on the actual grammar\ninput.\n\nNote: this module is pretty much obsolete; the pgen module generates\nequivalent grammar tables directly from the Grammar.txt input file\nwithout having to invoke the Python pgen C program.\n\n"));
      var1.setline(27);
      PyString.fromInterned("Convert graminit.[ch] spit out by pgen to Python code.\n\nPgen is the Python parser generator.  It is useful to quickly create a\nparser from a grammar file in Python's grammar notation.  But I don't\nwant my parsers to be written in C (yet), so I'm translating the\nparsing tables to Python data structures and writing a Python parse\nengine.\n\nNote that the token numbers are constants determined by the standard\nPython tokenizer.  The standard token module defines these numbers and\ntheir names (the names are not used much).  The token numbers are\nhardcoded into the Python tokenizer and into pgen.  A Python\nimplementation of the Python tokenizer is also available, in the\nstandard tokenize module.\n\nOn the other hand, symbol numbers (representing the grammar's\nnon-terminals) are assigned by pgen based on the actual grammar\ninput.\n\nNote: this module is pretty much obsolete; the pgen module generates\nequivalent grammar tables directly from the Grammar.txt input file\nwithout having to invoke the Python pgen C program.\n\n");
      var1.setline(30);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(33);
      String[] var5 = new String[]{"grammar", "token"};
      PyObject[] var6 = imp.importFrom("pgen2", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("grammar", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(36);
      var6 = new PyObject[]{var1.getname("grammar").__getattr__("Grammar")};
      var4 = Py.makeClass("Converter", var6, Converter$1);
      var1.setlocal("Converter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Converter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Grammar subclass that reads classic pgen output files.\n\n    The run() method reads the tables as produced by the pgen parser\n    generator, typically contained in two C files, graminit.h and\n    graminit.c.  The other methods are for internal use only.\n\n    See the base class for more documentation.\n\n    "));
      var1.setline(45);
      PyString.fromInterned("Grammar subclass that reads classic pgen output files.\n\n    The run() method reads the tables as produced by the pgen parser\n    generator, typically contained in two C files, graminit.h and\n    graminit.c.  The other methods are for internal use only.\n\n    See the base class for more documentation.\n\n    ");
      var1.setline(47);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, run$2, PyString.fromInterned("Load the grammar tables from the text files written by pgen."));
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_graminit_h$3, PyString.fromInterned("Parse the .h file written by pgen.  (Internal)\n\n        This file is a sequence of #define statements defining the\n        nonterminals of the grammar as numbers.  We build two tables\n        mapping the numbers to names and back.\n\n        "));
      var1.setlocal("parse_graminit_h", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_graminit_c$4, PyString.fromInterned("Parse the .c file written by pgen.  (Internal)\n\n        The file looks as follows.  The first two lines are always this:\n\n        #include \"pgenheaders.h\"\n        #include \"grammar.h\"\n\n        After that come four blocks:\n\n        1) one or more state definitions\n        2) a table defining dfas\n        3) a table defining labels\n        4) a struct defining the grammar\n\n        A state definition has the following form:\n        - one or more arc arrays, each of the form:\n          static arc arcs_<n>_<m>[<k>] = {\n                  {<i>, <j>},\n                  ...\n          };\n        - followed by a state array, of the form:\n          static state states_<s>[<t>] = {\n                  {<k>, arcs_<n>_<m>},\n                  ...\n          };\n\n        "));
      var1.setlocal("parse_graminit_c", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, finish_off$5, PyString.fromInterned("Create additional useful structures.  (Internal)."));
      var1.setlocal("finish_off", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$2(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyString.fromInterned("Load the grammar tables from the text files written by pgen.");
      var1.setline(49);
      var1.getlocal(0).__getattr__("parse_graminit_h").__call__(var2, var1.getlocal(1));
      var1.setline(50);
      var1.getlocal(0).__getattr__("parse_graminit_c").__call__(var2, var1.getlocal(2));
      var1.setline(51);
      var1.getlocal(0).__getattr__("finish_off").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_graminit_h$3(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Parse the .h file written by pgen.  (Internal)\n\n        This file is a sequence of #define statements defining the\n        nonterminals of the grammar as numbers.  We build two tables\n        mapping the numbers to names and back.\n\n        ");

      PyException var3;
      PyObject var4;
      PyObject var10;
      try {
         var1.setline(62);
         var10 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var10);
         var3 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(64);
            Py.println(PyString.fromInterned("Can't open %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3)})));
            var1.setline(65);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(66);
      PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"symbol2number", var11);
      var3 = null;
      var1.setline(67);
      var11 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"number2symbol", var11);
      var3 = null;
      var1.setline(68);
      PyInteger var12 = Py.newInteger(0);
      var1.setlocal(4, var12);
      var3 = null;
      var1.setline(69);
      var10 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(69);
         PyObject var5 = var10.__iternext__();
         if (var5 == null) {
            var1.setline(82);
            var4 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var4;
         }

         var1.setlocal(5, var5);
         var1.setline(70);
         PyObject var6 = var1.getlocal(4);
         var6 = var6._iadd(Py.newInteger(1));
         var1.setlocal(4, var6);
         var1.setline(71);
         var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^#define\\s+(\\w+)\\s+(\\d+)$"), (PyObject)var1.getlocal(5));
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(72);
         PyObject var10000 = var1.getlocal(6).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5).__getattr__("strip").__call__(var2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(73);
            Py.println(PyString.fromInterned("%s(%s): can't parse %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(5).__getattr__("strip").__call__(var2)})));
         } else {
            var1.setline(76);
            var6 = var1.getlocal(6).__getattr__("groups").__call__(var2);
            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(7, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(8, var8);
            var8 = null;
            var6 = null;
            var1.setline(77);
            var6 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(78);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var6 = var1.getlocal(7);
               var10000 = var6._notin(var1.getlocal(0).__getattr__("symbol2number"));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(79);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var6 = var1.getlocal(8);
               var10000 = var6._notin(var1.getlocal(0).__getattr__("number2symbol"));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(80);
            var6 = var1.getlocal(8);
            var1.getlocal(0).__getattr__("symbol2number").__setitem__(var1.getlocal(7), var6);
            var6 = null;
            var1.setline(81);
            var6 = var1.getlocal(7);
            var1.getlocal(0).__getattr__("number2symbol").__setitem__(var1.getlocal(8), var6);
            var6 = null;
         }
      }
   }

   public PyObject parse_graminit_c$4(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyString.fromInterned("Parse the .c file written by pgen.  (Internal)\n\n        The file looks as follows.  The first two lines are always this:\n\n        #include \"pgenheaders.h\"\n        #include \"grammar.h\"\n\n        After that come four blocks:\n\n        1) one or more state definitions\n        2) a table defining dfas\n        3) a table defining labels\n        4) a struct defining the grammar\n\n        A state definition has the following form:\n        - one or more arc arrays, each of the form:\n          static arc arcs_<n>_<m>[<k>] = {\n                  {<i>, <j>},\n                  ...\n          };\n        - followed by a state array, of the form:\n          static state states_<s>[<t>] = {\n                  {<k>, arcs_<n>_<m>},\n                  ...\n          };\n\n        ");

      PyException var3;
      PyObject var13;
      try {
         var1.setline(113);
         var13 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var13);
         var3 = null;
      } catch (Throwable var11) {
         var3 = Py.setException(var11, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            PyObject var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(115);
            Py.println(PyString.fromInterned("Can't open %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3)})));
            var1.setline(116);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(118);
      PyInteger var14 = Py.newInteger(0);
      var1.setlocal(4, var14);
      var3 = null;
      var1.setline(121);
      PyTuple var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
      PyObject[] var5 = Py.unpackSequence(var15, 2);
      PyObject var6 = var5[0];
      var1.setlocal(4, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(5, var6);
      var6 = null;
      var3 = null;
      var1.setline(122);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var13 = var1.getlocal(5);
         var10000 = var13._eq(PyString.fromInterned("#include \"pgenheaders.h\"\n"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
         }
      }

      var1.setline(123);
      var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
      var5 = Py.unpackSequence(var15, 2);
      var6 = var5[0];
      var1.setlocal(4, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(5, var6);
      var6 = null;
      var3 = null;
      var1.setline(124);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var13 = var1.getlocal(5);
         var10000 = var13._eq(PyString.fromInterned("#include \"grammar.h\"\n"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
         }
      }

      var1.setline(127);
      var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
      var5 = Py.unpackSequence(var15, 2);
      var6 = var5[0];
      var1.setlocal(4, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(5, var6);
      var6 = null;
      var3 = null;
      var1.setline(128);
      PyDictionary var17 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(6, var17);
      var3 = null;
      var1.setline(129);
      PyList var18 = new PyList(Py.EmptyObjects);
      var1.setlocal(7, var18);
      var3 = null;

      label288:
      while(true) {
         var1.setline(130);
         PyObject[] var7;
         PyObject var8;
         PyObject var16;
         PyTuple var20;
         if (!var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("static arc ")).__nonzero__()) {
            var1.setline(164);
            var13 = var1.getlocal(7);
            var1.getlocal(0).__setattr__("states", var13);
            var3 = null;
            var1.setline(167);
            var17 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(19, var17);
            var3 = null;
            var1.setline(168);
            var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("static dfa dfas\\[(\\d+)\\] = {$"), (PyObject)var1.getlocal(5));
            var1.setlocal(8, var13);
            var3 = null;
            var1.setline(169);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
            }

            var1.setline(170);
            var13 = var1.getglobal("int").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(20, var13);
            var3 = null;
            var1.setline(171);
            var13 = var1.getglobal("range").__call__(var2, var1.getlocal(20)).__iter__();

            while(true) {
               var1.setline(171);
               var16 = var13.__iternext__();
               if (var16 == null) {
                  var1.setline(194);
                  var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                  var5 = Py.unpackSequence(var15, 2);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var3 = null;
                  var1.setline(195);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var13 = var1.getlocal(5);
                     var10000 = var13._eq(PyString.fromInterned("};\n"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                     }
                  }

                  var1.setline(196);
                  var13 = var1.getlocal(19);
                  var1.getlocal(0).__setattr__("dfas", var13);
                  var3 = null;
                  var1.setline(199);
                  var18 = new PyList(Py.EmptyObjects);
                  var1.setlocal(30, var18);
                  var3 = null;
                  var1.setline(200);
                  var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                  var5 = Py.unpackSequence(var15, 2);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var3 = null;
                  var1.setline(201);
                  var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("static label labels\\[(\\d+)\\] = {$"), (PyObject)var1.getlocal(5));
                  var1.setlocal(8, var13);
                  var3 = null;
                  var1.setline(202);
                  if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }

                  var1.setline(203);
                  var13 = var1.getglobal("int").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                  var1.setlocal(31, var13);
                  var3 = null;
                  var1.setline(204);
                  var13 = var1.getglobal("range").__call__(var2, var1.getlocal(31)).__iter__();

                  while(true) {
                     var1.setline(204);
                     var16 = var13.__iternext__();
                     if (var16 == null) {
                        var1.setline(215);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(216);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(5);
                           var10000 = var13._eq(PyString.fromInterned("};\n"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        var1.setline(217);
                        var13 = var1.getlocal(30);
                        var1.getlocal(0).__setattr__("labels", var13);
                        var3 = null;
                        var1.setline(220);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(221);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(5);
                           var10000 = var13._eq(PyString.fromInterned("grammar _PyParser_Grammar = {\n"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        var1.setline(222);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(223);
                        var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+(\\d+),$"), (PyObject)var1.getlocal(5));
                        var1.setlocal(8, var13);
                        var3 = null;
                        var1.setline(224);
                        if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                           throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                        }

                        var1.setline(225);
                        var13 = var1.getglobal("int").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                        var1.setlocal(20, var13);
                        var3 = null;
                        var1.setline(226);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(20);
                           var10000 = var13._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dfas")));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var10000 = Py.None;
                              throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                           }
                        }

                        var1.setline(227);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(228);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(5);
                           var10000 = var13._eq(PyString.fromInterned("\tdfas,\n"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        var1.setline(229);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(230);
                        var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+{(\\d+), labels},$"), (PyObject)var1.getlocal(5));
                        var1.setlocal(8, var13);
                        var3 = null;
                        var1.setline(231);
                        if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                           throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                        }

                        var1.setline(232);
                        var13 = var1.getglobal("int").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                        var1.setlocal(31, var13);
                        var3 = null;
                        var1.setline(233);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(31);
                           var10000 = var13._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("labels")));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        var1.setline(234);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(235);
                        var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+(\\d+)$"), (PyObject)var1.getlocal(5));
                        var1.setlocal(8, var13);
                        var3 = null;
                        var1.setline(236);
                        if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                           throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                        }

                        var1.setline(237);
                        var13 = var1.getglobal("int").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                        var1.setlocal(32, var13);
                        var3 = null;
                        var1.setline(238);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(32);
                           var10000 = var13._in(var1.getlocal(0).__getattr__("number2symbol"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        var1.setline(239);
                        var13 = var1.getlocal(32);
                        var1.getlocal(0).__setattr__("start", var13);
                        var3 = null;
                        var1.setline(240);
                        var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                        var5 = Py.unpackSequence(var15, 2);
                        var6 = var5[0];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(5, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(241);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var13 = var1.getlocal(5);
                           var10000 = var13._eq(PyString.fromInterned("};\n"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        label231: {
                           try {
                              var1.setline(243);
                              var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                              var5 = Py.unpackSequence(var15, 2);
                              var6 = var5[0];
                              var1.setlocal(4, var6);
                              var6 = null;
                              var6 = var5[1];
                              var1.setlocal(5, var6);
                              var6 = null;
                              var3 = null;
                           } catch (Throwable var12) {
                              var3 = Py.setException(var12, var1);
                              if (var3.match(var1.getglobal("StopIteration"))) {
                                 var1.setline(245);
                                 break label231;
                              }

                              throw var3;
                           }

                           var1.setline(247);
                           if (var1.getglobal("__debug__").__nonzero__() && !Py.newInteger(0).__nonzero__()) {
                              throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                           }
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(14, var16);
                     var1.setline(205);
                     var20 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                     var7 = Py.unpackSequence(var20, 2);
                     var8 = var7[0];
                     var1.setlocal(4, var8);
                     var8 = null;
                     var8 = var7[1];
                     var1.setlocal(5, var8);
                     var8 = null;
                     var6 = null;
                     var1.setline(206);
                     var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+{(\\d+), (0|\"\\w+\")},$"), (PyObject)var1.getlocal(5));
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(207);
                     if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                        throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                     }

                     var1.setline(208);
                     var6 = var1.getlocal(8).__getattr__("groups").__call__(var2);
                     var7 = Py.unpackSequence(var6, 2);
                     var8 = var7[0];
                     var1.setlocal(23, var8);
                     var8 = null;
                     var8 = var7[1];
                     var1.setlocal(24, var8);
                     var8 = null;
                     var6 = null;
                     var1.setline(209);
                     var6 = var1.getglobal("int").__call__(var2, var1.getlocal(23));
                     var1.setlocal(23, var6);
                     var6 = null;
                     var1.setline(210);
                     var6 = var1.getlocal(24);
                     var10000 = var6._eq(PyString.fromInterned("0"));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(211);
                        var6 = var1.getglobal("None");
                        var1.setlocal(24, var6);
                        var6 = null;
                     } else {
                        var1.setline(213);
                        var6 = var1.getglobal("eval").__call__(var2, var1.getlocal(24));
                        var1.setlocal(24, var6);
                        var6 = null;
                     }

                     var1.setline(214);
                     var1.getlocal(30).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(23), var1.getlocal(24)})));
                  }
               }

               var1.setlocal(14, var16);
               var1.setline(172);
               var20 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
               var7 = Py.unpackSequence(var20, 2);
               var8 = var7[0];
               var1.setlocal(4, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(5, var8);
               var8 = null;
               var6 = null;
               var1.setline(173);
               var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+{(\\d+), \"(\\w+)\", (\\d+), (\\d+), states_(\\d+),$"), (PyObject)var1.getlocal(5));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(175);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
               }

               var1.setline(176);
               var6 = var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
               var1.setlocal(21, var6);
               var6 = null;
               var1.setline(177);
               var6 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(8).__getattr__("group").__call__(var2, Py.newInteger(1), Py.newInteger(3), Py.newInteger(4), Py.newInteger(5)));
               var7 = Py.unpackSequence(var6, 4);
               var8 = var7[0];
               var1.setlocal(22, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(23, var8);
               var8 = null;
               var8 = var7[2];
               var1.setlocal(24, var8);
               var8 = null;
               var8 = var7[3];
               var1.setlocal(25, var8);
               var8 = null;
               var6 = null;
               var1.setline(178);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getlocal(0).__getattr__("symbol2number").__getitem__(var1.getlocal(21));
                  var10000 = var6._eq(var1.getlocal(22));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }
               }

               var1.setline(179);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getlocal(0).__getattr__("number2symbol").__getitem__(var1.getlocal(22));
                  var10000 = var6._eq(var1.getlocal(21));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }
               }

               var1.setline(180);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getlocal(23);
                  var10000 = var6._eq(Py.newInteger(0));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }
               }

               var1.setline(181);
               var6 = var1.getlocal(7).__getitem__(var1.getlocal(25));
               var1.setlocal(18, var6);
               var6 = null;
               var1.setline(182);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getlocal(24);
                  var10000 = var6._eq(var1.getglobal("len").__call__(var2, var1.getlocal(18)));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }
               }

               var1.setline(183);
               var20 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
               var7 = Py.unpackSequence(var20, 2);
               var8 = var7[0];
               var1.setlocal(4, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(5, var8);
               var8 = null;
               var6 = null;
               var1.setline(184);
               var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+(\"(?:\\\\\\d\\d\\d)*\")},$"), (PyObject)var1.getlocal(5));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(185);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
               }

               var1.setline(186);
               PyDictionary var22 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(26, var22);
               var6 = null;
               var1.setline(187);
               var6 = var1.getglobal("eval").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
               var1.setlocal(27, var6);
               var6 = null;
               var1.setline(188);
               var6 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(27)).__iter__();

               while(true) {
                  var1.setline(188);
                  PyObject var19 = var6.__iternext__();
                  if (var19 == null) {
                     var1.setline(193);
                     var20 = new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(26)});
                     var1.getlocal(19).__setitem__((PyObject)var1.getlocal(22), var20);
                     var6 = null;
                     break;
                  }

                  PyObject[] var21 = Py.unpackSequence(var19, 2);
                  PyObject var9 = var21[0];
                  var1.setlocal(14, var9);
                  var9 = null;
                  var9 = var21[1];
                  var1.setlocal(28, var9);
                  var9 = null;
                  var1.setline(189);
                  var8 = var1.getglobal("ord").__call__(var2, var1.getlocal(28));
                  var1.setlocal(29, var8);
                  var8 = null;
                  var1.setline(190);
                  var8 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(8)).__iter__();

                  while(true) {
                     var1.setline(190);
                     var9 = var8.__iternext__();
                     if (var9 == null) {
                        break;
                     }

                     var1.setlocal(15, var9);
                     var1.setline(191);
                     if (var1.getlocal(29)._and(Py.newInteger(1)._lshift(var1.getlocal(15))).__nonzero__()) {
                        var1.setline(192);
                        PyInteger var10 = Py.newInteger(1);
                        var1.getlocal(26).__setitem__((PyObject)var1.getlocal(14)._mul(Py.newInteger(8))._add(var1.getlocal(15)), var10);
                        var10 = null;
                     }
                  }
               }
            }
         }

         while(true) {
            var1.setline(131);
            if (!var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("static arc ")).__nonzero__()) {
               var1.setline(147);
               var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("static state states_(\\d+)\\[(\\d+)\\] = {$"), (PyObject)var1.getlocal(5));
               var1.setlocal(8, var13);
               var3 = null;
               var1.setline(148);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
               }

               var1.setline(149);
               var13 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(8).__getattr__("groups").__call__(var2));
               var5 = Py.unpackSequence(var13, 2);
               var6 = var5[0];
               var1.setlocal(16, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(17, var6);
               var6 = null;
               var3 = null;
               var1.setline(150);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var13 = var1.getlocal(16);
                  var10000 = var13._eq(var1.getglobal("len").__call__(var2, var1.getlocal(7)));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }
               }

               var1.setline(151);
               var18 = new PyList(Py.EmptyObjects);
               var1.setlocal(18, var18);
               var3 = null;
               var1.setline(152);
               var13 = var1.getglobal("range").__call__(var2, var1.getlocal(17)).__iter__();

               while(true) {
                  var1.setline(152);
                  var16 = var13.__iternext__();
                  if (var16 == null) {
                     var1.setline(160);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(18));
                     var1.setline(161);
                     var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                     var5 = Py.unpackSequence(var15, 2);
                     var6 = var5[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var3 = null;
                     var1.setline(162);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var13 = var1.getlocal(5);
                        var10000 = var13._eq(PyString.fromInterned("};\n"));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                        }
                     }

                     var1.setline(163);
                     var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                     var5 = Py.unpackSequence(var15, 2);
                     var6 = var5[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var3 = null;
                     continue label288;
                  }

                  var1.setlocal(13, var16);
                  var1.setline(153);
                  var20 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                  var7 = Py.unpackSequence(var20, 2);
                  var8 = var7[0];
                  var1.setlocal(4, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(5, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(154);
                  var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+{(\\d+), arcs_(\\d+)_(\\d+)},$"), (PyObject)var1.getlocal(5));
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(155);
                  if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  }

                  var1.setline(156);
                  var6 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(8).__getattr__("groups").__call__(var2));
                  var7 = Py.unpackSequence(var6, 3);
                  var8 = var7[0];
                  var1.setlocal(11, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(9, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(10, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(157);
                  var6 = var1.getlocal(6).__getitem__(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10)}));
                  var1.setlocal(12, var6);
                  var6 = null;
                  var1.setline(158);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var6 = var1.getlocal(11);
                     var10000 = var6._eq(var1.getglobal("len").__call__(var2, var1.getlocal(12)));
                     var6 = null;
                     if (!var10000.__nonzero__()) {
                        throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                     }
                  }

                  var1.setline(159);
                  var1.getlocal(18).__getattr__("append").__call__(var2, var1.getlocal(12));
               }
            }

            var1.setline(132);
            var13 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("static arc arcs_(\\d+)_(\\d+)\\[(\\d+)\\] = {$"), (PyObject)var1.getlocal(5));
            var1.setlocal(8, var13);
            var3 = null;
            var1.setline(134);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
            }

            var1.setline(135);
            var13 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(8).__getattr__("groups").__call__(var2));
            var5 = Py.unpackSequence(var13, 3);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(11, var6);
            var6 = null;
            var3 = null;
            var1.setline(136);
            var18 = new PyList(Py.EmptyObjects);
            var1.setlocal(12, var18);
            var3 = null;
            var1.setline(137);
            var13 = var1.getglobal("range").__call__(var2, var1.getlocal(11)).__iter__();

            while(true) {
               var1.setline(137);
               var16 = var13.__iternext__();
               if (var16 == null) {
                  var1.setline(143);
                  var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                  var5 = Py.unpackSequence(var15, 2);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var3 = null;
                  var1.setline(144);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var13 = var1.getlocal(5);
                     var10000 = var13._eq(PyString.fromInterned("};\n"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                     }
                  }

                  var1.setline(145);
                  var13 = var1.getlocal(12);
                  var1.getlocal(6).__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10)})), var13);
                  var3 = null;
                  var1.setline(146);
                  var15 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
                  var5 = Py.unpackSequence(var15, 2);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var3 = null;
                  break;
               }

               var1.setlocal(13, var16);
               var1.setline(138);
               var20 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(2).__getattr__("next").__call__(var2)});
               var7 = Py.unpackSequence(var20, 2);
               var8 = var7[0];
               var1.setlocal(4, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(5, var8);
               var8 = null;
               var6 = null;
               var1.setline(139);
               var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+{(\\d+), (\\d+)},$"), (PyObject)var1.getlocal(5));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(140);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(8).__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
               }

               var1.setline(141);
               var6 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(8).__getattr__("groups").__call__(var2));
               var7 = Py.unpackSequence(var6, 2);
               var8 = var7[0];
               var1.setlocal(14, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(15, var8);
               var8 = null;
               var6 = null;
               var1.setline(142);
               var1.getlocal(12).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(14), var1.getlocal(15)})));
            }
         }
      }
   }

   public PyObject finish_off$5(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString.fromInterned("Create additional useful structures.  (Internal).");
      var1.setline(251);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"keywords", var3);
      var3 = null;
      var1.setline(252);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"tokens", var3);
      var3 = null;
      var1.setline(253);
      PyObject var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getattr__("labels")).__iter__();

      while(true) {
         var1.setline(253);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         PyObject[] var7 = Py.unpackSequence(var6, 2);
         PyObject var8 = var7[0];
         var1.setlocal(2, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(3, var8);
         var8 = null;
         var6 = null;
         var1.setline(254);
         PyObject var10 = var1.getlocal(2);
         PyObject var10000 = var10._eq(var1.getglobal("token").__getattr__("NAME"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10 = var1.getlocal(3);
            var10000 = var10._isnot(var1.getglobal("None"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(255);
            var10 = var1.getlocal(1);
            var1.getlocal(0).__getattr__("keywords").__setitem__(var1.getlocal(3), var10);
            var5 = null;
         } else {
            var1.setline(256);
            var10 = var1.getlocal(3);
            var10000 = var10._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(257);
               var10 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("tokens").__setitem__(var1.getlocal(2), var10);
               var5 = null;
            }
         }
      }
   }

   public conv$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Converter$1 = Py.newCode(0, var2, var1, "Converter", 36, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "graminit_h", "graminit_c"};
      run$2 = Py.newCode(3, var2, var1, "run", 47, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f", "err", "lineno", "line", "mo", "symbol", "number"};
      parse_graminit_h$3 = Py.newCode(2, var2, var1, "parse_graminit_h", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f", "err", "lineno", "line", "allarcs", "states", "mo", "n", "m", "k", "arcs", "_", "i", "j", "s", "t", "state", "dfas", "ndfas", "symbol", "number", "x", "y", "z", "first", "rawbitset", "c", "byte", "labels", "nlabels", "start"};
      parse_graminit_c$4 = Py.newCode(2, var2, var1, "parse_graminit_c", 84, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ilabel", "type", "value"};
      finish_off$5 = Py.newCode(1, var2, var1, "finish_off", 249, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new conv$py("lib2to3/pgen2/conv$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(conv$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Converter$1(var2, var3);
         case 2:
            return this.run$2(var2, var3);
         case 3:
            return this.parse_graminit_h$3(var2, var3);
         case 4:
            return this.parse_graminit_c$4(var2, var3);
         case 5:
            return this.finish_off$5(var2, var3);
         default:
            return null;
      }
   }
}
