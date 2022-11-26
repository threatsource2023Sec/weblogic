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
@Filename("tokenize.py")
public class tokenize$py extends PyFunctionTable implements PyRunnable {
   static tokenize$py self;
   static final PyCode f$0;
   static final PyCode group$1;
   static final PyCode any$2;
   static final PyCode maybe$3;
   static final PyCode TokenError$4;
   static final PyCode StopTokenizing$5;
   static final PyCode printtoken$6;
   static final PyCode tokenize$7;
   static final PyCode tokenize_loop$8;
   static final PyCode Untokenizer$9;
   static final PyCode __init__$10;
   static final PyCode add_whitespace$11;
   static final PyCode untokenize$12;
   static final PyCode compat$13;
   static final PyCode untokenize$14;
   static final PyCode generate_tokens$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tokenization help for Python programs.\n\ngenerate_tokens(readline) is a generator that breaks a stream of\ntext into Python tokens.  It accepts a readline-like method which is called\nrepeatedly to get the next line of input (or \"\" for EOF).  It generates\n5-tuples with these members:\n\n    the token type (see token.py)\n    the token (a string)\n    the starting (row, column) indices of the token (a 2-tuple of ints)\n    the ending (row, column) indices of the token (a 2-tuple of ints)\n    the original line (string)\n\nIt is designed to match the working of the Python tokenizer exactly, except\nthat it produces COMMENT tokens for comments and gives type OP for all\noperators\n\nOlder entry points\n    tokenize_loop(readline, tokeneater)\n    tokenize(readline, tokeneater=printtoken)\nare the same, except instead of generating tokens, tokeneater is a callback\nfunction to which the 5 fields described above are passed as 5 arguments,\neach time a new token is found."));
      var1.setline(23);
      PyString.fromInterned("Tokenization help for Python programs.\n\ngenerate_tokens(readline) is a generator that breaks a stream of\ntext into Python tokens.  It accepts a readline-like method which is called\nrepeatedly to get the next line of input (or \"\" for EOF).  It generates\n5-tuples with these members:\n\n    the token type (see token.py)\n    the token (a string)\n    the starting (row, column) indices of the token (a 2-tuple of ints)\n    the ending (row, column) indices of the token (a 2-tuple of ints)\n    the original line (string)\n\nIt is designed to match the working of the Python tokenizer exactly, except\nthat it produces COMMENT tokens for comments and gives type OP for all\noperators\n\nOlder entry points\n    tokenize_loop(readline, tokeneater)\n    tokenize(readline, tokeneater=printtoken)\nare the same, except instead of generating tokens, tokeneater is a callback\nfunction to which the 5 fields described above are passed as 5 arguments,\neach time a new token is found.");
      var1.setline(25);
      PyString var3 = PyString.fromInterned("Ka-Ping Yee <ping@lfw.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(26);
      var3 = PyString.fromInterned("GvR, ESR, Tim Peters, Thomas Wouters, Fred Drake, Skip Montanaro, Raymond Hettinger");
      var1.setlocal("__credits__", var3);
      var3 = null;
      var1.setline(29);
      PyObject var6 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var6);
      var3 = null;
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(30);
      imp.importAll("token", var1, -1);
      var1.setline(32);
      var6 = imp.importOne("token", var1, -1);
      var1.setlocal("token", var6);
      var3 = null;
      var1.setline(33);
      PyList var10000 = new PyList();
      var6 = var10000.__getattr__("append");
      var1.setlocal("_[33_11]", var6);
      var3 = null;
      var1.setline(33);
      var6 = var1.getname("dir").__call__(var2, var1.getname("token")).__iter__();

      while(true) {
         var1.setline(33);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(33);
            var1.dellocal("_[33_11]");
            PyList var8 = var10000;
            var1.setlocal("__all__", var8);
            var3 = null;
            var1.setline(34);
            var6 = var1.getname("__all__");
            var6 = var6._iadd(new PyList(new PyObject[]{PyString.fromInterned("COMMENT"), PyString.fromInterned("tokenize"), PyString.fromInterned("generate_tokens"), PyString.fromInterned("NL"), PyString.fromInterned("untokenize")}));
            var1.setlocal("__all__", var6);
            var1.setline(35);
            var1.dellocal("x");
            var1.setline(36);
            var1.dellocal("token");
            var1.setline(38);
            var6 = var1.getname("N_TOKENS");
            var1.setlocal("COMMENT", var6);
            var3 = null;
            var1.setline(39);
            var3 = PyString.fromInterned("COMMENT");
            var1.getname("tok_name").__setitem__((PyObject)var1.getname("COMMENT"), var3);
            var3 = null;
            var1.setline(40);
            var6 = var1.getname("N_TOKENS")._add(Py.newInteger(1));
            var1.setlocal("NL", var6);
            var3 = null;
            var1.setline(41);
            var3 = PyString.fromInterned("NL");
            var1.getname("tok_name").__setitem__((PyObject)var1.getname("NL"), var3);
            var3 = null;
            var1.setline(42);
            var6 = var1.getname("N_TOKENS");
            var6 = var6._iadd(Py.newInteger(2));
            var1.setlocal("N_TOKENS", var6);
            var1.setline(44);
            PyObject[] var9 = Py.EmptyObjects;
            PyFunction var10 = new PyFunction(var1.f_globals, var9, group$1, (PyObject)null);
            var1.setlocal("group", var10);
            var3 = null;
            var1.setline(45);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, any$2, (PyObject)null);
            var1.setlocal("any", var10);
            var3 = null;
            var1.setline(46);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, maybe$3, (PyObject)null);
            var1.setlocal("maybe", var10);
            var3 = null;
            var1.setline(48);
            var3 = PyString.fromInterned("[ \\f\\t]*");
            var1.setlocal("Whitespace", var3);
            var3 = null;
            var1.setline(49);
            var3 = PyString.fromInterned("#[^\\r\\n]*");
            var1.setlocal("Comment", var3);
            var3 = null;
            var1.setline(50);
            var6 = var1.getname("Whitespace")._add(var1.getname("any").__call__(var2, PyString.fromInterned("\\\\\\r?\\n")._add(var1.getname("Whitespace"))))._add(var1.getname("maybe").__call__(var2, var1.getname("Comment")));
            var1.setlocal("Ignore", var6);
            var3 = null;
            var1.setline(51);
            var3 = PyString.fromInterned("[a-zA-Z_]\\w*");
            var1.setlocal("Name", var3);
            var3 = null;
            var1.setline(53);
            var3 = PyString.fromInterned("0[xX][\\da-fA-F]+[lL]?");
            var1.setlocal("Hexnumber", var3);
            var3 = null;
            var1.setline(54);
            var3 = PyString.fromInterned("(0[oO][0-7]+)|(0[0-7]*)[lL]?");
            var1.setlocal("Octnumber", var3);
            var3 = null;
            var1.setline(55);
            var3 = PyString.fromInterned("0[bB][01]+[lL]?");
            var1.setlocal("Binnumber", var3);
            var3 = null;
            var1.setline(56);
            var3 = PyString.fromInterned("[1-9]\\d*[lL]?");
            var1.setlocal("Decnumber", var3);
            var3 = null;
            var1.setline(57);
            var6 = var1.getname("group").__call__(var2, var1.getname("Hexnumber"), var1.getname("Binnumber"), var1.getname("Octnumber"), var1.getname("Decnumber"));
            var1.setlocal("Intnumber", var6);
            var3 = null;
            var1.setline(58);
            var3 = PyString.fromInterned("[eE][-+]?\\d+");
            var1.setlocal("Exponent", var3);
            var3 = null;
            var1.setline(59);
            var6 = var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\d+\\.\\d*"), (PyObject)PyString.fromInterned("\\.\\d+"))._add(var1.getname("maybe").__call__(var2, var1.getname("Exponent")));
            var1.setlocal("Pointfloat", var6);
            var3 = null;
            var1.setline(60);
            var6 = PyString.fromInterned("\\d+")._add(var1.getname("Exponent"));
            var1.setlocal("Expfloat", var6);
            var3 = null;
            var1.setline(61);
            var6 = var1.getname("group").__call__(var2, var1.getname("Pointfloat"), var1.getname("Expfloat"));
            var1.setlocal("Floatnumber", var6);
            var3 = null;
            var1.setline(62);
            var6 = var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\d+[jJ]"), (PyObject)var1.getname("Floatnumber")._add(PyString.fromInterned("[jJ]")));
            var1.setlocal("Imagnumber", var6);
            var3 = null;
            var1.setline(63);
            var6 = var1.getname("group").__call__(var2, var1.getname("Imagnumber"), var1.getname("Floatnumber"), var1.getname("Intnumber"));
            var1.setlocal("Number", var6);
            var3 = null;
            var1.setline(66);
            var3 = PyString.fromInterned("[^'\\\\]*(?:\\\\.[^'\\\\]*)*'");
            var1.setlocal("Single", var3);
            var3 = null;
            var1.setline(68);
            var3 = PyString.fromInterned("[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"");
            var1.setlocal("Double", var3);
            var3 = null;
            var1.setline(70);
            var3 = PyString.fromInterned("[^'\\\\]*(?:(?:\\\\.|'(?!''))[^'\\\\]*)*'''");
            var1.setlocal("Single3", var3);
            var3 = null;
            var1.setline(72);
            var3 = PyString.fromInterned("[^\"\\\\]*(?:(?:\\\\.|\"(?!\"\"))[^\"\\\\]*)*\"\"\"");
            var1.setlocal("Double3", var3);
            var3 = null;
            var1.setline(73);
            var6 = var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[uUbB]?[rR]?'''"), (PyObject)PyString.fromInterned("[uUbB]?[rR]?\"\"\""));
            var1.setlocal("Triple", var6);
            var3 = null;
            var1.setline(75);
            var6 = var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[uUbB]?[rR]?'[^\\n'\\\\]*(?:\\\\.[^\\n'\\\\]*)*'"), (PyObject)PyString.fromInterned("[uUbB]?[rR]?\"[^\\n\"\\\\]*(?:\\\\.[^\\n\"\\\\]*)*\""));
            var1.setlocal("String", var6);
            var3 = null;
            var1.setline(81);
            PyObject var13 = var1.getname("group");
            var9 = new PyObject[]{PyString.fromInterned("\\*\\*=?"), PyString.fromInterned(">>=?"), PyString.fromInterned("<<=?"), PyString.fromInterned("<>"), PyString.fromInterned("!="), PyString.fromInterned("//=?"), PyString.fromInterned("[+\\-*/%&|^=<>]=?"), PyString.fromInterned("~")};
            var6 = var13.__call__(var2, var9);
            var1.setlocal("Operator", var6);
            var3 = null;
            var1.setline(86);
            var3 = PyString.fromInterned("[][(){}]");
            var1.setlocal("Bracket", var3);
            var3 = null;
            var1.setline(87);
            var6 = var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\r?\\n"), (PyObject)PyString.fromInterned("[:;.,`@]"));
            var1.setlocal("Special", var6);
            var3 = null;
            var1.setline(88);
            var6 = var1.getname("group").__call__(var2, var1.getname("Operator"), var1.getname("Bracket"), var1.getname("Special"));
            var1.setlocal("Funny", var6);
            var3 = null;
            var1.setline(90);
            var6 = var1.getname("group").__call__(var2, var1.getname("Number"), var1.getname("Funny"), var1.getname("String"), var1.getname("Name"));
            var1.setlocal("PlainToken", var6);
            var3 = null;
            var1.setline(91);
            var6 = var1.getname("Ignore")._add(var1.getname("PlainToken"));
            var1.setlocal("Token", var6);
            var3 = null;
            var1.setline(94);
            var6 = var1.getname("group").__call__(var2, PyString.fromInterned("[uUbB]?[rR]?'[^\\n'\\\\]*(?:\\\\.[^\\n'\\\\]*)*")._add(var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"), (PyObject)PyString.fromInterned("\\\\\\r?\\n"))), PyString.fromInterned("[uUbB]?[rR]?\"[^\\n\"\\\\]*(?:\\\\.[^\\n\"\\\\]*)*")._add(var1.getname("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\\\\\\r?\\n"))));
            var1.setlocal("ContStr", var6);
            var3 = null;
            var1.setline(98);
            var6 = var1.getname("group").__call__((ThreadState)var2, PyString.fromInterned("\\\\\\r?\\n|\\Z"), (PyObject)var1.getname("Comment"), (PyObject)var1.getname("Triple"));
            var1.setlocal("PseudoExtras", var6);
            var3 = null;
            var1.setline(99);
            var13 = var1.getname("Whitespace");
            PyObject var10001 = var1.getname("group");
            var9 = new PyObject[]{var1.getname("PseudoExtras"), var1.getname("Number"), var1.getname("Funny"), var1.getname("ContStr"), var1.getname("Name")};
            var6 = var13._add(var10001.__call__(var2, var9));
            var1.setlocal("PseudoToken", var6);
            var3 = null;
            var1.setline(101);
            var6 = var1.getname("map").__call__((ThreadState)var2, (PyObject)var1.getname("re").__getattr__("compile"), (PyObject)(new PyTuple(new PyObject[]{var1.getname("Token"), var1.getname("PseudoToken"), var1.getname("Single3"), var1.getname("Double3")})));
            PyObject[] var7 = Py.unpackSequence(var6, 4);
            PyObject var5 = var7[0];
            var1.setlocal("tokenprog", var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal("pseudoprog", var5);
            var5 = null;
            var5 = var7[2];
            var1.setlocal("single3prog", var5);
            var5 = null;
            var5 = var7[3];
            var1.setlocal("double3prog", var5);
            var5 = null;
            var3 = null;
            var1.setline(103);
            PyDictionary var11 = new PyDictionary(new PyObject[]{PyString.fromInterned("'"), var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("Single")), PyString.fromInterned("\""), var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("Double")), PyString.fromInterned("'''"), var1.getname("single3prog"), PyString.fromInterned("\"\"\""), var1.getname("double3prog"), PyString.fromInterned("r'''"), var1.getname("single3prog"), PyString.fromInterned("r\"\"\""), var1.getname("double3prog"), PyString.fromInterned("u'''"), var1.getname("single3prog"), PyString.fromInterned("u\"\"\""), var1.getname("double3prog"), PyString.fromInterned("ur'''"), var1.getname("single3prog"), PyString.fromInterned("ur\"\"\""), var1.getname("double3prog"), PyString.fromInterned("R'''"), var1.getname("single3prog"), PyString.fromInterned("R\"\"\""), var1.getname("double3prog"), PyString.fromInterned("U'''"), var1.getname("single3prog"), PyString.fromInterned("U\"\"\""), var1.getname("double3prog"), PyString.fromInterned("uR'''"), var1.getname("single3prog"), PyString.fromInterned("uR\"\"\""), var1.getname("double3prog"), PyString.fromInterned("Ur'''"), var1.getname("single3prog"), PyString.fromInterned("Ur\"\"\""), var1.getname("double3prog"), PyString.fromInterned("UR'''"), var1.getname("single3prog"), PyString.fromInterned("UR\"\"\""), var1.getname("double3prog"), PyString.fromInterned("b'''"), var1.getname("single3prog"), PyString.fromInterned("b\"\"\""), var1.getname("double3prog"), PyString.fromInterned("br'''"), var1.getname("single3prog"), PyString.fromInterned("br\"\"\""), var1.getname("double3prog"), PyString.fromInterned("B'''"), var1.getname("single3prog"), PyString.fromInterned("B\"\"\""), var1.getname("double3prog"), PyString.fromInterned("bR'''"), var1.getname("single3prog"), PyString.fromInterned("bR\"\"\""), var1.getname("double3prog"), PyString.fromInterned("Br'''"), var1.getname("single3prog"), PyString.fromInterned("Br\"\"\""), var1.getname("double3prog"), PyString.fromInterned("BR'''"), var1.getname("single3prog"), PyString.fromInterned("BR\"\"\""), var1.getname("double3prog"), PyString.fromInterned("r"), var1.getname("None"), PyString.fromInterned("R"), var1.getname("None"), PyString.fromInterned("u"), var1.getname("None"), PyString.fromInterned("U"), var1.getname("None"), PyString.fromInterned("b"), var1.getname("None"), PyString.fromInterned("B"), var1.getname("None")});
            var1.setlocal("endprogs", var11);
            var3 = null;
            var1.setline(122);
            var11 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal("triple_quoted", var11);
            var3 = null;
            var1.setline(123);
            var6 = (new PyTuple(new PyObject[]{PyString.fromInterned("'''"), PyString.fromInterned("\"\"\""), PyString.fromInterned("r'''"), PyString.fromInterned("r\"\"\""), PyString.fromInterned("R'''"), PyString.fromInterned("R\"\"\""), PyString.fromInterned("u'''"), PyString.fromInterned("u\"\"\""), PyString.fromInterned("U'''"), PyString.fromInterned("U\"\"\""), PyString.fromInterned("ur'''"), PyString.fromInterned("ur\"\"\""), PyString.fromInterned("Ur'''"), PyString.fromInterned("Ur\"\"\""), PyString.fromInterned("uR'''"), PyString.fromInterned("uR\"\"\""), PyString.fromInterned("UR'''"), PyString.fromInterned("UR\"\"\""), PyString.fromInterned("b'''"), PyString.fromInterned("b\"\"\""), PyString.fromInterned("B'''"), PyString.fromInterned("B\"\"\""), PyString.fromInterned("br'''"), PyString.fromInterned("br\"\"\""), PyString.fromInterned("Br'''"), PyString.fromInterned("Br\"\"\""), PyString.fromInterned("bR'''"), PyString.fromInterned("bR\"\"\""), PyString.fromInterned("BR'''"), PyString.fromInterned("BR\"\"\"")})).__iter__();

            while(true) {
               var1.setline(123);
               var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(132);
                  var11 = new PyDictionary(Py.EmptyObjects);
                  var1.setlocal("single_quoted", var11);
                  var3 = null;
                  var1.setline(133);
                  var6 = (new PyTuple(new PyObject[]{PyString.fromInterned("'"), PyString.fromInterned("\""), PyString.fromInterned("r'"), PyString.fromInterned("r\""), PyString.fromInterned("R'"), PyString.fromInterned("R\""), PyString.fromInterned("u'"), PyString.fromInterned("u\""), PyString.fromInterned("U'"), PyString.fromInterned("U\""), PyString.fromInterned("ur'"), PyString.fromInterned("ur\""), PyString.fromInterned("Ur'"), PyString.fromInterned("Ur\""), PyString.fromInterned("uR'"), PyString.fromInterned("uR\""), PyString.fromInterned("UR'"), PyString.fromInterned("UR\""), PyString.fromInterned("b'"), PyString.fromInterned("b\""), PyString.fromInterned("B'"), PyString.fromInterned("B\""), PyString.fromInterned("br'"), PyString.fromInterned("br\""), PyString.fromInterned("Br'"), PyString.fromInterned("Br\""), PyString.fromInterned("bR'"), PyString.fromInterned("bR\""), PyString.fromInterned("BR'"), PyString.fromInterned("BR\"")})).__iter__();

                  while(true) {
                     var1.setline(133);
                     var4 = var6.__iternext__();
                     if (var4 == null) {
                        var1.setline(143);
                        PyInteger var12 = Py.newInteger(8);
                        var1.setlocal("tabsize", var12);
                        var3 = null;
                        var1.setline(145);
                        var9 = new PyObject[]{var1.getname("Exception")};
                        var4 = Py.makeClass("TokenError", var9, TokenError$4);
                        var1.setlocal("TokenError", var4);
                        var4 = null;
                        Arrays.fill(var9, (Object)null);
                        var1.setline(147);
                        var9 = new PyObject[]{var1.getname("Exception")};
                        var4 = Py.makeClass("StopTokenizing", var9, StopTokenizing$5);
                        var1.setlocal("StopTokenizing", var4);
                        var4 = null;
                        Arrays.fill(var9, (Object)null);
                        var1.setline(149);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, printtoken$6, (PyObject)null);
                        var1.setlocal("printtoken", var10);
                        var3 = null;
                        var1.setline(155);
                        var9 = new PyObject[]{var1.getname("printtoken")};
                        var10 = new PyFunction(var1.f_globals, var9, tokenize$7, PyString.fromInterned("\n    The tokenize() function accepts two parameters: one representing the\n    input stream, and one providing an output mechanism for tokenize().\n\n    The first parameter, readline, must be a callable object which provides\n    the same interface as the readline() method of built-in file objects.\n    Each call to the function should return one line of input as a string.\n\n    The second parameter, tokeneater, must also be a callable object. It is\n    called once for each token, with five arguments, corresponding to the\n    tuples generated by generate_tokens().\n    "));
                        var1.setlocal("tokenize", var10);
                        var3 = null;
                        var1.setline(174);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, tokenize_loop$8, (PyObject)null);
                        var1.setlocal("tokenize_loop", var10);
                        var3 = null;
                        var1.setline(178);
                        var9 = Py.EmptyObjects;
                        var4 = Py.makeClass("Untokenizer", var9, Untokenizer$9);
                        var1.setlocal("Untokenizer", var4);
                        var4 = null;
                        Arrays.fill(var9, (Object)null);
                        var1.setline(243);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, untokenize$14, PyString.fromInterned("Transform tokens back into Python source code.\n\n    Each element returned by the iterable must be a token sequence\n    with at least two elements, a token number and token value.  If\n    only two tokens are passed, the resulting output is poor.\n\n    Round-trip invariant for full input:\n        Untokenized source will match input source exactly\n\n    Round-trip invariant for limited intput:\n        # Output text will tokenize the back to the input\n        t1 = [tok[:2] for tok in generate_tokens(f.readline)]\n        newcode = untokenize(t1)\n        readline = iter(newcode.splitlines(1)).next\n        t2 = [tok[:2] for tok in generate_tokens(readline)]\n        assert t1 == t2\n    "));
                        var1.setlocal("untokenize", var10);
                        var3 = null;
                        var1.setline(264);
                        var9 = Py.EmptyObjects;
                        var10 = new PyFunction(var1.f_globals, var9, generate_tokens$15, PyString.fromInterned("\n    The generate_tokens() generator requires one argment, readline, which\n    must be a callable object which provides the same interface as the\n    readline() method of built-in file objects. Each call to the function\n    should return one line of input as a string.  Alternately, readline\n    can be a callable function terminating with StopIteration:\n        readline = open(myfile).next    # Example of alternate readline\n\n    The generator produces 5-tuples with these members: the token type; the\n    token string; a 2-tuple (srow, scol) of ints specifying the row and\n    column where the token begins in the source; a 2-tuple (erow, ecol) of\n    ints specifying the row and column where the token ends in the source;\n    and the line on which the token was found. The line passed is the\n    logical line; continuation lines are included.\n    "));
                        var1.setlocal("generate_tokens", var10);
                        var3 = null;
                        var1.setline(421);
                        var6 = var1.getname("__name__");
                        var13 = var6._eq(PyString.fromInterned("__main__"));
                        var3 = null;
                        if (var13.__nonzero__()) {
                           var1.setline(422);
                           var6 = imp.importOne("sys", var1, -1);
                           var1.setlocal("sys", var6);
                           var3 = null;
                           var1.setline(423);
                           var6 = var1.getname("len").__call__(var2, var1.getname("sys").__getattr__("argv"));
                           var13 = var6._gt(Py.newInteger(1));
                           var3 = null;
                           if (var13.__nonzero__()) {
                              var1.setline(424);
                              var1.getname("tokenize").__call__(var2, var1.getname("open").__call__(var2, var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(1))).__getattr__("readline"));
                           } else {
                              var1.setline(426);
                              var1.getname("tokenize").__call__(var2, var1.getname("sys").__getattr__("stdin").__getattr__("readline"));
                           }
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal("t", var4);
                     var1.setline(141);
                     var5 = var1.getname("t");
                     var1.getname("single_quoted").__setitem__(var1.getname("t"), var5);
                     var5 = null;
                  }
               }

               var1.setlocal("t", var4);
               var1.setline(131);
               var5 = var1.getname("t");
               var1.getname("triple_quoted").__setitem__(var1.getname("t"), var5);
               var5 = null;
            }
         }

         var1.setlocal("x", var4);
         var1.setline(33);
         if (var1.getname("x").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__not__().__nonzero__()) {
            var1.setline(33);
            var1.getname("_[33_11]").__call__(var2, var1.getname("x"));
         }
      }
   }

   public PyObject group$1(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = PyString.fromInterned("(")._add(PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getlocal(0)))._add(PyString.fromInterned(")"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject any$2(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var10000 = var1.getglobal("group");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000._add(PyString.fromInterned("*"));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject maybe$3(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var10000 = var1.getglobal("group");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000._add(PyString.fromInterned("?"));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject TokenError$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(145);
      return var1.getf_locals();
   }

   public PyObject StopTokenizing$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(147);
      return var1.getf_locals();
   }

   public PyObject printtoken$6(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyObject var3 = var1.getlocal(2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(151);
      var3 = var1.getlocal(3);
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(152);
      Py.println(PyString.fromInterned("%d,%d-%d,%d:\t%s\t%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getglobal("tok_name").__getitem__(var1.getlocal(0)), var1.getglobal("repr").__call__(var2, var1.getlocal(1))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tokenize$7(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyString.fromInterned("\n    The tokenize() function accepts two parameters: one representing the\n    input stream, and one providing an output mechanism for tokenize().\n\n    The first parameter, readline, must be a callable object which provides\n    the same interface as the readline() method of built-in file objects.\n    Each call to the function should return one line of input as a string.\n\n    The second parameter, tokeneater, must also be a callable object. It is\n    called once for each token, with five arguments, corresponding to the\n    tuples generated by generate_tokens().\n    ");

      try {
         var1.setline(169);
         var1.getglobal("tokenize_loop").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("StopTokenizing"))) {
            throw var3;
         }

         var1.setline(171);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tokenize_loop$8(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3 = var1.getglobal("generate_tokens").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(175);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(176);
         PyObject var10000 = var1.getlocal(1);
         PyObject[] var5 = Py.EmptyObjects;
         String[] var6 = new String[0];
         var10000._callextra(var5, var6, var1.getlocal(2), (PyObject)null);
         var5 = null;
      }
   }

   public PyObject Untokenizer$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(180);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_whitespace$11, (PyObject)null);
      var1.setlocal("add_whitespace", var4);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, untokenize$12, (PyObject)null);
      var1.setlocal("untokenize", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compat$13, (PyObject)null);
      var1.setlocal("compat", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"tokens", var3);
      var3 = null;
      var1.setline(182);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"prev_row", var4);
      var3 = null;
      var1.setline(183);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"prev_col", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_whitespace$11(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(187);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._le(var1.getlocal(0).__getattr__("prev_row"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(188);
      var3 = var1.getlocal(3)._sub(var1.getlocal(0).__getattr__("prev_col"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(189);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(190);
         var1.getlocal(0).__getattr__("tokens").__getattr__("append").__call__(var2, PyString.fromInterned(" ")._mul(var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject untokenize$12(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(193);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(2, var4);
         var1.setline(194);
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10000 = var5._eq(Py.newInteger(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(195);
            var1.getlocal(0).__getattr__("compat").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            break;
         }

         var1.setline(197);
         var5 = var1.getlocal(2);
         PyObject[] var6 = Py.unpackSequence(var5, 5);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[3];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[4];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(198);
         var1.getlocal(0).__getattr__("add_whitespace").__call__(var2, var1.getlocal(5));
         var1.setline(199);
         var1.getlocal(0).__getattr__("tokens").__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(200);
         var5 = var1.getlocal(6);
         var6 = Py.unpackSequence(var5, 2);
         var7 = var6[0];
         var1.getlocal(0).__setattr__("prev_row", var7);
         var7 = null;
         var7 = var6[1];
         var1.getlocal(0).__setattr__("prev_col", var7);
         var7 = null;
         var5 = null;
         var1.setline(201);
         var5 = var1.getlocal(3);
         var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("NEWLINE"), var1.getglobal("NL")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var10000 = var1.getlocal(0);
            String var9 = "prev_row";
            PyObject var8 = var10000;
            var7 = var8.__getattr__(var9);
            var7 = var7._iadd(Py.newInteger(1));
            var8.__setattr__(var9, var7);
            var1.setline(203);
            PyInteger var10 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"prev_col", var10);
            var5 = null;
         }
      }

      var1.setline(204);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("tokens"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compat$13(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(208);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(209);
      var3 = var1.getlocal(0).__getattr__("tokens").__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(211);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("NAME"), var1.getglobal("NUMBER")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(212);
         var3 = var1.getlocal(7);
         var3 = var3._iadd(PyString.fromInterned(" "));
         var1.setlocal(7, var3);
      }

      var1.setline(213);
      var3 = var1.getlocal(6);
      var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("NEWLINE"), var1.getglobal("NL")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(214);
         var3 = var1.getglobal("True");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(215);
      var3 = var1.getglobal("False");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(216);
         PyObject var9 = var3.__iternext__();
         if (var9 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(9, var9);
         var1.setline(217);
         var5 = var1.getlocal(9).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(219);
         var5 = var1.getlocal(6);
         var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("NAME"), var1.getglobal("NUMBER")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(220);
            var5 = var1.getlocal(7);
            var5 = var5._iadd(PyString.fromInterned(" "));
            var1.setlocal(7, var5);
         }

         var1.setline(223);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(var1.getglobal("STRING"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(224);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(225);
               var5 = PyString.fromInterned(" ")._add(var1.getlocal(7));
               var1.setlocal(7, var5);
               var5 = null;
            }

            var1.setline(226);
            var5 = var1.getglobal("True");
            var1.setlocal(8, var5);
            var5 = null;
         } else {
            var1.setline(228);
            var5 = var1.getglobal("False");
            var1.setlocal(8, var5);
            var5 = null;
         }

         var1.setline(230);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(var1.getglobal("INDENT"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(231);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
         } else {
            var1.setline(233);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(var1.getglobal("DEDENT"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(234);
               var1.getlocal(4).__getattr__("pop").__call__(var2);
            } else {
               var1.setline(236);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("NEWLINE"), var1.getglobal("NL")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(237);
                  var5 = var1.getglobal("True");
                  var1.setlocal(3, var5);
                  var5 = null;
               } else {
                  var1.setline(238);
                  var10000 = var1.getlocal(3);
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(4);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(239);
                     var1.getlocal(5).__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(-1)));
                     var1.setline(240);
                     var5 = var1.getglobal("False");
                     var1.setlocal(3, var5);
                     var5 = null;
                  }
               }

               var1.setline(241);
               var1.getlocal(5).__call__(var2, var1.getlocal(7));
            }
         }
      }
   }

   public PyObject untokenize$14(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Transform tokens back into Python source code.\n\n    Each element returned by the iterable must be a token sequence\n    with at least two elements, a token number and token value.  If\n    only two tokens are passed, the resulting output is poor.\n\n    Round-trip invariant for full input:\n        Untokenized source will match input source exactly\n\n    Round-trip invariant for limited intput:\n        # Output text will tokenize the back to the input\n        t1 = [tok[:2] for tok in generate_tokens(f.readline)]\n        newcode = untokenize(t1)\n        readline = iter(newcode.splitlines(1)).next\n        t2 = [tok[:2] for tok in generate_tokens(readline)]\n        assert t1 == t2\n    ");
      var1.setline(261);
      PyObject var3 = var1.getglobal("Untokenizer").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(262);
      var3 = var1.getlocal(1).__getattr__("untokenize").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject generate_tokens$15(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public tokenize$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"choices"};
      group$1 = Py.newCode(1, var2, var1, "group", 44, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"choices"};
      any$2 = Py.newCode(1, var2, var1, "any", 45, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"choices"};
      maybe$3 = Py.newCode(1, var2, var1, "maybe", 46, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TokenError$4 = Py.newCode(0, var2, var1, "TokenError", 145, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StopTokenizing$5 = Py.newCode(0, var2, var1, "StopTokenizing", 147, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"type", "token", "srow_scol", "erow_ecol", "line", "srow", "scol", "erow", "ecol"};
      printtoken$6 = Py.newCode(5, var2, var1, "printtoken", 149, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"readline", "tokeneater"};
      tokenize$7 = Py.newCode(2, var2, var1, "tokenize", 155, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"readline", "tokeneater", "token_info"};
      tokenize_loop$8 = Py.newCode(2, var2, var1, "tokenize_loop", 174, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Untokenizer$9 = Py.newCode(0, var2, var1, "Untokenizer", 178, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$10 = Py.newCode(1, var2, var1, "__init__", 180, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "row", "col", "col_offset"};
      add_whitespace$11 = Py.newCode(2, var2, var1, "add_whitespace", 185, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable", "t", "tok_type", "token", "start", "end", "line"};
      untokenize$12 = Py.newCode(2, var2, var1, "untokenize", 192, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "token", "iterable", "startline", "indents", "toks_append", "toknum", "tokval", "prevstring", "tok"};
      compat$13 = Py.newCode(3, var2, var1, "compat", 206, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"iterable", "ut"};
      untokenize$14 = Py.newCode(1, var2, var1, "untokenize", 243, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"readline", "lnum", "parenlev", "continued", "namechars", "numchars", "contstr", "needcont", "contline", "indents", "line", "pos", "max", "strstart", "endmatch", "endprog", "end", "column", "comment_token", "nl_pos", "pseudomatch", "start", "spos", "epos", "token", "initial", "indent"};
      generate_tokens$15 = Py.newCode(1, var2, var1, "generate_tokens", 264, false, false, self, 15, (String[])null, (String[])null, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tokenize$py("tokenize$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tokenize$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.group$1(var2, var3);
         case 2:
            return this.any$2(var2, var3);
         case 3:
            return this.maybe$3(var2, var3);
         case 4:
            return this.TokenError$4(var2, var3);
         case 5:
            return this.StopTokenizing$5(var2, var3);
         case 6:
            return this.printtoken$6(var2, var3);
         case 7:
            return this.tokenize$7(var2, var3);
         case 8:
            return this.tokenize_loop$8(var2, var3);
         case 9:
            return this.Untokenizer$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.add_whitespace$11(var2, var3);
         case 12:
            return this.untokenize$12(var2, var3);
         case 13:
            return this.compat$13(var2, var3);
         case 14:
            return this.untokenize$14(var2, var3);
         case 15:
            return this.generate_tokens$15(var2, var3);
         default:
            return null;
      }
   }
}
