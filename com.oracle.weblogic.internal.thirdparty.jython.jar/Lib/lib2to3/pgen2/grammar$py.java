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
@Filename("lib2to3/pgen2/grammar.py")
public class grammar$py extends PyFunctionTable implements PyRunnable {
   static grammar$py self;
   static final PyCode f$0;
   static final PyCode Grammar$1;
   static final PyCode __init__$2;
   static final PyCode dump$3;
   static final PyCode load$4;
   static final PyCode copy$5;
   static final PyCode report$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("This module defines the data structures used to represent a grammar.\n\nThese are a bit arcane because they are derived from the data\nstructures used by Python's 'pgen' parser generator.\n\nThere's also a table here mapping operators to their names in the\ntoken module; the Python tokenize module reports all operators as the\nfallback token code OP, but the parser needs the actual token code.\n\n"));
      var1.setline(13);
      PyString.fromInterned("This module defines the data structures used to represent a grammar.\n\nThese are a bit arcane because they are derived from the data\nstructures used by Python's 'pgen' parser generator.\n\nThere's also a table here mapping operators to their names in the\ntoken module; the Python tokenize module reports all operators as the\nfallback token code OP, but the parser needs the actual token code.\n\n");
      var1.setline(16);
      PyObject var3 = imp.importOne("pickle", var1, -1);
      var1.setlocal("pickle", var3);
      var3 = null;
      var1.setline(19);
      String[] var8 = new String[]{"token", "tokenize"};
      PyObject[] var9 = imp.importFrom("", var8, var1, 1);
      PyObject var4 = var9[0];
      var1.setlocal("token", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("tokenize", var4);
      var4 = null;
      var1.setline(22);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Grammar", var9, Grammar$1);
      var1.setlocal("Grammar", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(131);
      PyString var10 = PyString.fromInterned("\n( LPAR\n) RPAR\n[ LSQB\n] RSQB\n: COLON\n, COMMA\n; SEMI\n+ PLUS\n- MINUS\n* STAR\n/ SLASH\n| VBAR\n& AMPER\n< LESS\n> GREATER\n= EQUAL\n. DOT\n% PERCENT\n` BACKQUOTE\n{ LBRACE\n} RBRACE\n@ AT\n== EQEQUAL\n!= NOTEQUAL\n<> NOTEQUAL\n<= LESSEQUAL\n>= GREATEREQUAL\n~ TILDE\n^ CIRCUMFLEX\n<< LEFTSHIFT\n>> RIGHTSHIFT\n** DOUBLESTAR\n+= PLUSEQUAL\n-= MINEQUAL\n*= STAREQUAL\n/= SLASHEQUAL\n%= PERCENTEQUAL\n&= AMPEREQUAL\n|= VBAREQUAL\n^= CIRCUMFLEXEQUAL\n<<= LEFTSHIFTEQUAL\n>>= RIGHTSHIFTEQUAL\n**= DOUBLESTAREQUAL\n// DOUBLESLASH\n//= DOUBLESLASHEQUAL\n-> RARROW\n");
      var1.setlocal("opmap_raw", var10);
      var3 = null;
      var1.setline(180);
      PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("opmap", var11);
      var3 = null;
      var1.setline(181);
      var3 = var1.getname("opmap_raw").__getattr__("splitlines").__call__(var2).__iter__();

      while(true) {
         var1.setline(181);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("line", var4);
         var1.setline(182);
         if (var1.getname("line").__nonzero__()) {
            var1.setline(183);
            PyObject var5 = var1.getname("line").__getattr__("split").__call__(var2);
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal("op", var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal("name", var7);
            var7 = null;
            var5 = null;
            var1.setline(184);
            var5 = var1.getname("getattr").__call__(var2, var1.getname("token"), var1.getname("name"));
            var1.getname("opmap").__setitem__(var1.getname("op"), var5);
            var5 = null;
         }
      }
   }

   public PyObject Grammar$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Pgen parsing tables tables conversion class.\n\n    Once initialized, this class supplies the grammar tables for the\n    parsing engine implemented by parse.py.  The parsing engine\n    accesses the instance variables directly.  The class here does not\n    provide initialization of the tables; several subclasses exist to\n    do this (see the conv and pgen modules).\n\n    The load() method reads the tables from a pickle file, which is\n    much faster than the other ways offered by subclasses.  The pickle\n    file is written by calling dump() (after loading the grammar\n    tables using a subclass).  The report() method prints a readable\n    representation of the tables to stdout, for debugging.\n\n    The instance variables are as follows:\n\n    symbol2number -- a dict mapping symbol names to numbers.  Symbol\n                     numbers are always 256 or higher, to distinguish\n                     them from token numbers, which are between 0 and\n                     255 (inclusive).\n\n    number2symbol -- a dict mapping numbers to symbol names;\n                     these two are each other's inverse.\n\n    states        -- a list of DFAs, where each DFA is a list of\n                     states, each state is is a list of arcs, and each\n                     arc is a (i, j) pair where i is a label and j is\n                     a state number.  The DFA number is the index into\n                     this list.  (This name is slightly confusing.)\n                     Final states are represented by a special arc of\n                     the form (0, j) where j is its own state number.\n\n    dfas          -- a dict mapping symbol numbers to (DFA, first)\n                     pairs, where DFA is an item from the states list\n                     above, and first is a set of tokens that can\n                     begin this grammar rule (represented by a dict\n                     whose values are always 1).\n\n    labels        -- a list of (x, y) pairs where x is either a token\n                     number or a symbol number, and y is either None\n                     or a string; the strings are keywords.  The label\n                     number is the index in this list; label numbers\n                     are used to mark state transitions (arcs) in the\n                     DFAs.\n\n    start         -- the number of the grammar's start symbol.\n\n    keywords      -- a dict mapping keyword strings to arc labels.\n\n    tokens        -- a dict mapping token numbers to arc labels.\n\n    "));
      var1.setline(74);
      PyString.fromInterned("Pgen parsing tables tables conversion class.\n\n    Once initialized, this class supplies the grammar tables for the\n    parsing engine implemented by parse.py.  The parsing engine\n    accesses the instance variables directly.  The class here does not\n    provide initialization of the tables; several subclasses exist to\n    do this (see the conv and pgen modules).\n\n    The load() method reads the tables from a pickle file, which is\n    much faster than the other ways offered by subclasses.  The pickle\n    file is written by calling dump() (after loading the grammar\n    tables using a subclass).  The report() method prints a readable\n    representation of the tables to stdout, for debugging.\n\n    The instance variables are as follows:\n\n    symbol2number -- a dict mapping symbol names to numbers.  Symbol\n                     numbers are always 256 or higher, to distinguish\n                     them from token numbers, which are between 0 and\n                     255 (inclusive).\n\n    number2symbol -- a dict mapping numbers to symbol names;\n                     these two are each other's inverse.\n\n    states        -- a list of DFAs, where each DFA is a list of\n                     states, each state is is a list of arcs, and each\n                     arc is a (i, j) pair where i is a label and j is\n                     a state number.  The DFA number is the index into\n                     this list.  (This name is slightly confusing.)\n                     Final states are represented by a special arc of\n                     the form (0, j) where j is its own state number.\n\n    dfas          -- a dict mapping symbol numbers to (DFA, first)\n                     pairs, where DFA is an item from the states list\n                     above, and first is a set of tokens that can\n                     begin this grammar rule (represented by a dict\n                     whose values are always 1).\n\n    labels        -- a list of (x, y) pairs where x is either a token\n                     number or a symbol number, and y is either None\n                     or a string; the strings are keywords.  The label\n                     number is the index in this list; label numbers\n                     are used to mark state transitions (arcs) in the\n                     DFAs.\n\n    start         -- the number of the grammar's start symbol.\n\n    keywords      -- a dict mapping keyword strings to arc labels.\n\n    tokens        -- a dict mapping token numbers to arc labels.\n\n    ");
      var1.setline(76);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump$3, PyString.fromInterned("Dump the grammar tables to a pickle file."));
      var1.setlocal("dump", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load$4, PyString.fromInterned("Load the grammar tables from a pickle file."));
      var1.setlocal("load", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$5, PyString.fromInterned("\n        Copy the grammar.\n        "));
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, report$6, PyString.fromInterned("Dump the grammar tables to standard output, for debugging."));
      var1.setlocal("report", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"symbol2number", var3);
      var3 = null;
      var1.setline(78);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"number2symbol", var3);
      var3 = null;
      var1.setline(79);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"states", var4);
      var3 = null;
      var1.setline(80);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"dfas", var3);
      var3 = null;
      var1.setline(81);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(0), PyString.fromInterned("EMPTY")})});
      var1.getlocal(0).__setattr__((String)"labels", var4);
      var3 = null;
      var1.setline(82);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"keywords", var3);
      var3 = null;
      var1.setline(83);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"tokens", var3);
      var3 = null;
      var1.setline(84);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"symbol2label", var3);
      var3 = null;
      var1.setline(85);
      PyInteger var5 = Py.newInteger(256);
      var1.getlocal(0).__setattr__((String)"start", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump$3(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned("Dump the grammar tables to a pickle file.");
      var1.setline(89);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(90);
      var1.getglobal("pickle").__getattr__("dump").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("__dict__"), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(2));
      var1.setline(91);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load$4(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Load the grammar tables from a pickle file.");
      var1.setline(95);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(96);
      var3 = var1.getglobal("pickle").__getattr__("load").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(97);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(98);
      var1.getlocal(0).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$5(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("\n        Copy the grammar.\n        ");
      var1.setline(104);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(105);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("symbol2number"), PyString.fromInterned("number2symbol"), PyString.fromInterned("dfas"), PyString.fromInterned("keywords"), PyString.fromInterned("tokens"), PyString.fromInterned("symbol2label")})).__iter__();

      while(true) {
         var1.setline(105);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(108);
            var3 = var1.getlocal(0).__getattr__("labels").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
            var1.getlocal(1).__setattr__("labels", var3);
            var3 = null;
            var1.setline(109);
            var3 = var1.getlocal(0).__getattr__("states").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
            var1.getlocal(1).__setattr__("states", var3);
            var3 = null;
            var1.setline(110);
            var3 = var1.getlocal(0).__getattr__("start");
            var1.getlocal(1).__setattr__("start", var3);
            var3 = null;
            var1.setline(111);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(107);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)).__getattr__("copy").__call__(var2));
      }
   }

   public PyObject report$6(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyString.fromInterned("Dump the grammar tables to standard output, for debugging.");
      var1.setline(115);
      String[] var3 = new String[]{"pprint"};
      PyObject[] var5 = imp.importFrom("pprint", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(116);
      Py.println(PyString.fromInterned("s2n"));
      var1.setline(117);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("symbol2number"));
      var1.setline(118);
      Py.println(PyString.fromInterned("n2s"));
      var1.setline(119);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("number2symbol"));
      var1.setline(120);
      Py.println(PyString.fromInterned("states"));
      var1.setline(121);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("states"));
      var1.setline(122);
      Py.println(PyString.fromInterned("dfas"));
      var1.setline(123);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("dfas"));
      var1.setline(124);
      Py.println(PyString.fromInterned("labels"));
      var1.setline(125);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("labels"));
      var1.setline(126);
      Py.printComma(PyString.fromInterned("start"));
      Py.println(var1.getlocal(0).__getattr__("start"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public grammar$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Grammar$1 = Py.newCode(0, var2, var1, "Grammar", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 76, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f"};
      dump$3 = Py.newCode(2, var2, var1, "dump", 87, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f", "d"};
      load$4 = Py.newCode(2, var2, var1, "load", 93, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new", "dict_attr"};
      copy$5 = Py.newCode(1, var2, var1, "copy", 100, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pprint"};
      report$6 = Py.newCode(1, var2, var1, "report", 113, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new grammar$py("lib2to3/pgen2/grammar$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(grammar$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Grammar$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.dump$3(var2, var3);
         case 4:
            return this.load$4(var2, var3);
         case 5:
            return this.copy$5(var2, var3);
         case 6:
            return this.report$6(var2, var3);
         default:
            return null;
      }
   }
}
