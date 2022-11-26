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
@Filename("lib2to3/pgen2/pgen.py")
public class pgen$py extends PyFunctionTable implements PyRunnable {
   static pgen$py self;
   static final PyCode f$0;
   static final PyCode PgenGrammar$1;
   static final PyCode ParserGenerator$2;
   static final PyCode __init__$3;
   static final PyCode make_grammar$4;
   static final PyCode make_first$5;
   static final PyCode make_label$6;
   static final PyCode addfirstsets$7;
   static final PyCode calcfirst$8;
   static final PyCode parse$9;
   static final PyCode make_dfa$10;
   static final PyCode closure$11;
   static final PyCode addclosure$12;
   static final PyCode dump_nfa$13;
   static final PyCode dump_dfa$14;
   static final PyCode simplify_dfa$15;
   static final PyCode parse_rhs$16;
   static final PyCode parse_alt$17;
   static final PyCode parse_item$18;
   static final PyCode parse_atom$19;
   static final PyCode expect$20;
   static final PyCode gettoken$21;
   static final PyCode raise_error$22;
   static final PyCode NFAState$23;
   static final PyCode __init__$24;
   static final PyCode addarc$25;
   static final PyCode DFAState$26;
   static final PyCode __init__$27;
   static final PyCode addarc$28;
   static final PyCode unifystate$29;
   static final PyCode __eq__$30;
   static final PyCode generate_grammar$31;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      String[] var3 = new String[]{"grammar", "token", "tokenize"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 1);
      PyObject var4 = var5[0];
      var1.setlocal("grammar", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("token", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("tokenize", var4);
      var4 = null;
      var1.setline(7);
      var5 = new PyObject[]{var1.getname("grammar").__getattr__("Grammar")};
      var4 = Py.makeClass("PgenGrammar", var5, PgenGrammar$1);
      var1.setlocal("PgenGrammar", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(10);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ParserGenerator", var5, ParserGenerator$2);
      var1.setlocal("ParserGenerator", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(337);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("NFAState", var5, NFAState$23);
      var1.setlocal("NFAState", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(347);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("DFAState", var5, DFAState$26);
      var1.setlocal("DFAState", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(384);
      var5 = new PyObject[]{PyString.fromInterned("Grammar.txt")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, generate_grammar$31, (PyObject)null);
      var1.setlocal("generate_grammar", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PgenGrammar$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(8);
      return var1.getf_locals();
   }

   public PyObject ParserGenerator$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, make_grammar$4, (PyObject)null);
      var1.setlocal("make_grammar", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, make_first$5, (PyObject)null);
      var1.setlocal("make_first", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, make_label$6, (PyObject)null);
      var1.setlocal("make_label", var4);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addfirstsets$7, (PyObject)null);
      var1.setlocal("addfirstsets", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, calcfirst$8, (PyObject)null);
      var1.setlocal("calcfirst", var4);
      var3 = null;
      var1.setline(145);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$9, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(169);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, make_dfa$10, (PyObject)null);
      var1.setlocal("make_dfa", var4);
      var3 = null;
      var1.setline(205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_nfa$13, (PyObject)null);
      var1.setlocal("dump_nfa", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_dfa$14, (PyObject)null);
      var1.setlocal("dump_dfa", var4);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, simplify_dfa$15, (PyObject)null);
      var1.setlocal("simplify_dfa", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_rhs$16, (PyObject)null);
      var1.setlocal("parse_rhs", var4);
      var3 = null;
      var1.setline(266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_alt$17, (PyObject)null);
      var1.setlocal("parse_alt", var4);
      var3 = null;
      var1.setline(276);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_item$18, (PyObject)null);
      var1.setlocal("parse_item", var4);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_atom$19, (PyObject)null);
      var1.setlocal("parse_atom", var4);
      var3 = null;
      var1.setline(313);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, expect$20, (PyObject)null);
      var1.setlocal("expect", var4);
      var3 = null;
      var1.setline(321);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gettoken$21, (PyObject)null);
      var1.setlocal("gettoken", var4);
      var3 = null;
      var1.setline(328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, raise_error$22, (PyObject)null);
      var1.setlocal("raise_error", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(14);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(15);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(16);
         var3 = var1.getlocal(2).__getattr__("close");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(17);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(18);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(2).__getattr__("readline"));
      var1.getlocal(0).__setattr__("generator", var3);
      var3 = null;
      var1.setline(20);
      var1.getlocal(0).__getattr__("gettoken").__call__(var2);
      var1.setline(21);
      var3 = var1.getlocal(0).__getattr__("parse").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("dfas", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("startsymbol", var5);
      var5 = null;
      var3 = null;
      var1.setline(22);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(23);
         var1.getlocal(3).__call__(var2);
      }

      var1.setline(24);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"first", var6);
      var3 = null;
      var1.setline(25);
      var1.getlocal(0).__getattr__("addfirstsets").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_grammar$4(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("PgenGrammar").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getlocal(0).__getattr__("dfas").__getattr__("keys").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(30);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(31);
      var1.getlocal(2).__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("startsymbol"));
      var1.setline(32);
      var1.getlocal(2).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("startsymbol"));
      var1.setline(33);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(33);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(37);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(37);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(49);
                  var3 = var1.getlocal(1).__getattr__("symbol2number").__getitem__(var1.getlocal(0).__getattr__("startsymbol"));
                  var1.getlocal(1).__setattr__("start", var3);
                  var3 = null;
                  var1.setline(50);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var4);
               var1.setline(38);
               var5 = var1.getlocal(0).__getattr__("dfas").__getitem__(var1.getlocal(3));
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(39);
               PyList var11 = new PyList(Py.EmptyObjects);
               var1.setlocal(6, var11);
               var5 = null;
               var1.setline(40);
               var5 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(40);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(47);
                     var1.getlocal(1).__getattr__("states").__getattr__("append").__call__(var2, var1.getlocal(6));
                     var1.setline(48);
                     PyTuple var13 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(0).__getattr__("make_first").__call__(var2, var1.getlocal(1), var1.getlocal(3))});
                     var1.getlocal(1).__getattr__("dfas").__setitem__((PyObject)var1.getlocal(1).__getattr__("symbol2number").__getitem__(var1.getlocal(3)), var13);
                     var5 = null;
                     break;
                  }

                  var1.setlocal(7, var6);
                  var1.setline(41);
                  PyList var7 = new PyList(Py.EmptyObjects);
                  var1.setlocal(8, var7);
                  var7 = null;
                  var1.setline(42);
                  PyObject var12 = var1.getlocal(7).__getattr__("arcs").__getattr__("iteritems").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(42);
                     PyObject var8 = var12.__iternext__();
                     if (var8 == null) {
                        var1.setline(44);
                        if (var1.getlocal(7).__getattr__("isfinal").__nonzero__()) {
                           var1.setline(45);
                           var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(0), var1.getlocal(5).__getattr__("index").__call__(var2, var1.getlocal(7))})));
                        }

                        var1.setline(46);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
                        break;
                     }

                     PyObject[] var9 = Py.unpackSequence(var8, 2);
                     PyObject var10 = var9[0];
                     var1.setlocal(9, var10);
                     var10 = null;
                     var10 = var9[1];
                     var1.setlocal(10, var10);
                     var10 = null;
                     var1.setline(43);
                     var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("make_label").__call__(var2, var1.getlocal(1), var1.getlocal(9)), var1.getlocal(5).__getattr__("index").__call__(var2, var1.getlocal(10))})));
                  }
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(34);
         var5 = Py.newInteger(256)._add(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("symbol2number")));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(35);
         var5 = var1.getlocal(4);
         var1.getlocal(1).__getattr__("symbol2number").__setitem__(var1.getlocal(3), var5);
         var5 = null;
         var1.setline(36);
         var5 = var1.getlocal(3);
         var1.getlocal(1).__getattr__("number2symbol").__setitem__(var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject make_first$5(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3 = var1.getlocal(0).__getattr__("first").__getitem__(var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(54);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(55);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(55);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(59);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(56);
         PyObject var5 = var1.getlocal(0).__getattr__("make_label").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(58);
         PyInteger var7 = Py.newInteger(1);
         var1.getlocal(4).__setitem__((PyObject)var1.getlocal(6), var7);
         var5 = null;
      }
   }

   public PyObject make_label$6(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("labels"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(64);
      PyObject var10000;
      PyObject var4;
      if (var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("isalpha").__call__(var2).__nonzero__()) {
         var1.setline(66);
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(1).__getattr__("symbol2number"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(68);
            var3 = var1.getlocal(2);
            var10000 = var3._in(var1.getlocal(1).__getattr__("symbol2label"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(69);
               var3 = var1.getlocal(1).__getattr__("symbol2label").__getitem__(var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(71);
               var1.getlocal(1).__getattr__("labels").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("symbol2number").__getitem__(var1.getlocal(2)), var1.getglobal("None")})));
               var1.setline(72);
               var4 = var1.getlocal(3);
               var1.getlocal(1).__getattr__("symbol2label").__setitem__(var1.getlocal(2), var4);
               var4 = null;
               var1.setline(73);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(76);
            var4 = var1.getglobal("getattr").__call__(var2, var1.getglobal("token"), var1.getlocal(2), var1.getglobal("None"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(77);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("int")).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(2));
            } else {
               var1.setline(78);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var4 = var1.getlocal(4);
                  var10000 = var4._in(var1.getglobal("token").__getattr__("tok_name"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(2));
                  }
               }

               var1.setline(79);
               var4 = var1.getlocal(4);
               var10000 = var4._in(var1.getlocal(1).__getattr__("tokens"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(80);
                  var3 = var1.getlocal(1).__getattr__("tokens").__getitem__(var1.getlocal(4));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(82);
                  var1.getlocal(1).__getattr__("labels").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("None")})));
                  var1.setline(83);
                  var4 = var1.getlocal(3);
                  var1.getlocal(1).__getattr__("tokens").__setitem__(var1.getlocal(4), var4);
                  var4 = null;
                  var1.setline(84);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      } else {
         var1.setline(87);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("\""), PyString.fromInterned("'")}));
            var4 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(2));
            }
         }

         var1.setline(88);
         var4 = var1.getglobal("eval").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(89);
         if (var1.getlocal(5).__getitem__(Py.newInteger(0)).__getattr__("isalpha").__call__(var2).__nonzero__()) {
            var1.setline(91);
            var4 = var1.getlocal(5);
            var10000 = var4._in(var1.getlocal(1).__getattr__("keywords"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(92);
               var3 = var1.getlocal(1).__getattr__("keywords").__getitem__(var1.getlocal(5));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(94);
               var1.getlocal(1).__getattr__("labels").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getlocal(5)})));
               var1.setline(95);
               var4 = var1.getlocal(3);
               var1.getlocal(1).__getattr__("keywords").__setitem__(var1.getlocal(5), var4);
               var4 = null;
               var1.setline(96);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(99);
            var4 = var1.getglobal("grammar").__getattr__("opmap").__getitem__(var1.getlocal(5));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(100);
            var4 = var1.getlocal(4);
            var10000 = var4._in(var1.getlocal(1).__getattr__("tokens"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(101);
               var3 = var1.getlocal(1).__getattr__("tokens").__getitem__(var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(103);
               var1.getlocal(1).__getattr__("labels").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("None")})));
               var1.setline(104);
               var4 = var1.getlocal(3);
               var1.getlocal(1).__getattr__("tokens").__setitem__(var1.getlocal(4), var4);
               var4 = null;
               var1.setline(105);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject addfirstsets$7(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyObject var3 = var1.getlocal(0).__getattr__("dfas").__getattr__("keys").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(109);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(110);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(110);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(111);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._notin(var1.getlocal(0).__getattr__("first"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(112);
            var1.getlocal(0).__getattr__("calcfirst").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject calcfirst$8(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = var1.getlocal(0).__getattr__("dfas").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(117);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__getattr__("first").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(119);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(120);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(121);
      var3 = var1.getlocal(3).__getattr__("arcs").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(121);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         if (var4 == null) {
            var1.setline(135);
            var8 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(9, var8);
            var3 = null;
            var1.setline(136);
            var3 = var1.getlocal(5).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(136);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(143);
                  var3 = var1.getlocal(4);
                  var1.getlocal(0).__getattr__("first").__setitem__(var1.getlocal(1), var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(137);
               var9 = var1.getlocal(10).__iter__();

               while(true) {
                  var1.setline(137);
                  var6 = var9.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(11, var6);
                  var1.setline(138);
                  PyObject var7 = var1.getlocal(11);
                  var10000 = var7._in(var1.getlocal(9));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(139);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("rule %s is ambiguous; %s is in the first sets of %s as well as %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(11), var1.getlocal(6), var1.getlocal(9).__getitem__(var1.getlocal(11))}))));
                  }

                  var1.setline(142);
                  var7 = var1.getlocal(6);
                  var1.getlocal(9).__setitem__(var1.getlocal(11), var7);
                  var7 = null;
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(122);
         var9 = var1.getlocal(6);
         var10000 = var9._in(var1.getlocal(0).__getattr__("dfas"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(123);
            var9 = var1.getlocal(6);
            var10000 = var9._in(var1.getlocal(0).__getattr__("first"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(124);
               var9 = var1.getlocal(0).__getattr__("first").__getitem__(var1.getlocal(6));
               var1.setlocal(8, var9);
               var5 = null;
               var1.setline(125);
               var9 = var1.getlocal(8);
               var10000 = var9._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(126);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("recursion for rule %r")._mod(var1.getlocal(1))));
               }
            } else {
               var1.setline(128);
               var1.getlocal(0).__getattr__("calcfirst").__call__(var2, var1.getlocal(6));
               var1.setline(129);
               var9 = var1.getlocal(0).__getattr__("first").__getitem__(var1.getlocal(6));
               var1.setlocal(8, var9);
               var5 = null;
            }

            var1.setline(130);
            var1.getlocal(4).__getattr__("update").__call__(var2, var1.getlocal(8));
            var1.setline(131);
            var9 = var1.getlocal(8);
            var1.getlocal(5).__setitem__(var1.getlocal(6), var9);
            var5 = null;
         } else {
            var1.setline(133);
            PyInteger var10 = Py.newInteger(1);
            var1.getlocal(4).__setitem__((PyObject)var1.getlocal(6), var10);
            var5 = null;
            var1.setline(134);
            PyDictionary var11 = new PyDictionary(new PyObject[]{var1.getlocal(6), Py.newInteger(1)});
            var1.getlocal(5).__setitem__((PyObject)var1.getlocal(6), var11);
            var5 = null;
         }
      }
   }

   public PyObject parse$9(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(147);
      PyObject var6 = var1.getglobal("None");
      var1.setlocal(2, var6);
      var3 = null;

      while(true) {
         var1.setline(149);
         var6 = var1.getlocal(0).__getattr__("type");
         PyObject var10000 = var6._ne(var1.getglobal("token").__getattr__("ENDMARKER"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(167);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var7;
         }

         while(true) {
            var1.setline(150);
            var6 = var1.getlocal(0).__getattr__("type");
            var10000 = var6._eq(var1.getglobal("token").__getattr__("NEWLINE"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(153);
               var6 = var1.getlocal(0).__getattr__("expect").__call__(var2, var1.getglobal("token").__getattr__("NAME"));
               var1.setlocal(3, var6);
               var3 = null;
               var1.setline(154);
               var1.getlocal(0).__getattr__("expect").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("OP"), (PyObject)PyString.fromInterned(":"));
               var1.setline(155);
               var6 = var1.getlocal(0).__getattr__("parse_rhs").__call__(var2);
               PyObject[] var4 = Py.unpackSequence(var6, 2);
               PyObject var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(5, var5);
               var5 = null;
               var3 = null;
               var1.setline(156);
               var1.getlocal(0).__getattr__("expect").__call__(var2, var1.getglobal("token").__getattr__("NEWLINE"));
               var1.setline(158);
               var6 = var1.getlocal(0).__getattr__("make_dfa").__call__(var2, var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(160);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var1.setlocal(7, var6);
               var3 = null;
               var1.setline(161);
               var1.getlocal(0).__getattr__("simplify_dfa").__call__(var2, var1.getlocal(6));
               var1.setline(162);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var1.setlocal(8, var6);
               var3 = null;
               var1.setline(163);
               var6 = var1.getlocal(6);
               var1.getlocal(1).__setitem__(var1.getlocal(3), var6);
               var3 = null;
               var1.setline(165);
               var6 = var1.getlocal(2);
               var10000 = var6._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(166);
                  var6 = var1.getlocal(3);
                  var1.setlocal(2, var6);
                  var3 = null;
               }
               break;
            }

            var1.setline(151);
            var1.getlocal(0).__getattr__("gettoken").__call__(var2);
         }
      }
   }

   public PyObject make_dfa$10(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("NFAState")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(175);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("NFAState")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(176);
            PyObject[] var3 = Py.EmptyObjects;
            PyObject var10002 = var1.f_globals;
            PyObject[] var10003 = var3;
            PyCode var10004 = closure$11;
            var3 = new PyObject[]{var1.getclosure(0)};
            PyFunction var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
            var1.setlocal(3, var11);
            var3 = null;
            var1.setline(180);
            var3 = Py.EmptyObjects;
            var10002 = var1.f_globals;
            var10003 = var3;
            var10004 = addclosure$12;
            var3 = new PyObject[]{var1.getclosure(0)};
            var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
            var1.setderef(0, var11);
            var3 = null;
            var1.setline(188);
            PyList var12 = new PyList(new PyObject[]{var1.getglobal("DFAState").__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(1)), var1.getlocal(2))});
            var1.setlocal(4, var12);
            var3 = null;
            var1.setline(189);
            PyObject var14 = var1.getlocal(4).__iter__();

            label60:
            while(true) {
               var1.setline(189);
               PyObject var4 = var14.__iternext__();
               if (var4 == null) {
                  var1.setline(203);
                  var14 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var14;
               }

               var1.setlocal(5, var4);
               var1.setline(190);
               PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(191);
               PyObject var13 = var1.getlocal(5).__getattr__("nfaset").__iter__();

               while(true) {
                  var1.setline(191);
                  PyObject var6 = var13.__iternext__();
                  PyObject var7;
                  PyObject var8;
                  PyObject[] var9;
                  PyObject var16;
                  if (var6 == null) {
                     var1.setline(195);
                     var13 = var1.getlocal(6).__getattr__("iteritems").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(195);
                        var6 = var13.__iternext__();
                        if (var6 == null) {
                           continue label60;
                        }

                        PyObject[] var15 = Py.unpackSequence(var6, 2);
                        var8 = var15[0];
                        var1.setlocal(8, var8);
                        var8 = null;
                        var8 = var15[1];
                        var1.setlocal(10, var8);
                        var8 = null;
                        var1.setline(196);
                        var7 = var1.getlocal(4).__iter__();

                        do {
                           var1.setline(196);
                           var8 = var7.__iternext__();
                           if (var8 == null) {
                              var1.setline(200);
                              var16 = var1.getglobal("DFAState").__call__(var2, var1.getlocal(10), var1.getlocal(2));
                              var1.setlocal(11, var16);
                              var9 = null;
                              var1.setline(201);
                              var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(11));
                              break;
                           }

                           var1.setlocal(11, var8);
                           var1.setline(197);
                           var16 = var1.getlocal(11).__getattr__("nfaset");
                           var10000 = var16._eq(var1.getlocal(10));
                           var9 = null;
                        } while(!var10000.__nonzero__());

                        var1.setline(202);
                        var1.getlocal(5).__getattr__("addarc").__call__(var2, var1.getlocal(11), var1.getlocal(8));
                     }
                  }

                  var1.setlocal(7, var6);
                  var1.setline(192);
                  var7 = var1.getlocal(7).__getattr__("arcs").__iter__();

                  while(true) {
                     var1.setline(192);
                     var8 = var7.__iternext__();
                     if (var8 == null) {
                        break;
                     }

                     var9 = Py.unpackSequence(var8, 2);
                     PyObject var10 = var9[0];
                     var1.setlocal(8, var10);
                     var10 = null;
                     var10 = var9[1];
                     var1.setlocal(9, var10);
                     var10 = null;
                     var1.setline(193);
                     var16 = var1.getlocal(8);
                     var10000 = var16._isnot(var1.getglobal("None"));
                     var9 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(194);
                        var1.getderef(0).__call__(var2, var1.getlocal(9), var1.getlocal(6).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)(new PyDictionary(Py.EmptyObjects))));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject closure$11(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(178);
      var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(179);
      PyObject var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject addclosure$12(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("NFAState")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(182);
         PyObject var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(183);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(184);
            PyInteger var7 = Py.newInteger(1);
            var1.getlocal(1).__setitem__((PyObject)var1.getlocal(0), var7);
            var3 = null;
            var1.setline(185);
            var3 = var1.getlocal(0).__getattr__("arcs").__iter__();

            while(true) {
               var1.setline(185);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(186);
               PyObject var8 = var1.getlocal(2);
               var10000 = var8._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(187);
                  var1.getderef(0).__call__(var2, var1.getlocal(3), var1.getlocal(1));
               }
            }
         }
      }
   }

   public PyObject dump_nfa$13(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      Py.printComma(PyString.fromInterned("Dump of NFA for"));
      Py.println(var1.getlocal(1));
      var1.setline(207);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(2)});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(208);
      PyObject var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(4)).__iter__();

      while(true) {
         var1.setline(208);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(209);
         Py.printComma(PyString.fromInterned("  State"));
         Py.printComma(var1.getlocal(5));
         PyObject var10 = var1.getlocal(6);
         Object var10000 = var10._is(var1.getlocal(3));
         var5 = null;
         if (((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("(final)");
         }

         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("");
         }

         Py.println((PyObject)var10000);
         var1.setline(210);
         var10 = var1.getlocal(6).__getattr__("arcs").__iter__();

         while(true) {
            var1.setline(210);
            var6 = var10.__iternext__();
            if (var6 == null) {
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(7, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(8, var8);
            var8 = null;
            var1.setline(211);
            PyObject var11 = var1.getlocal(8);
            PyObject var12 = var11._in(var1.getlocal(4));
            var7 = null;
            if (var12.__nonzero__()) {
               var1.setline(212);
               var11 = var1.getlocal(4).__getattr__("index").__call__(var2, var1.getlocal(8));
               var1.setlocal(9, var11);
               var7 = null;
            } else {
               var1.setline(214);
               var11 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var1.setlocal(9, var11);
               var7 = null;
               var1.setline(215);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
            }

            var1.setline(216);
            var11 = var1.getlocal(7);
            var12 = var11._is(var1.getglobal("None"));
            var7 = null;
            if (var12.__nonzero__()) {
               var1.setline(217);
               Py.println(PyString.fromInterned("    -> %d")._mod(var1.getlocal(9)));
            } else {
               var1.setline(219);
               Py.println(PyString.fromInterned("    %s -> %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(9)})));
            }
         }
      }
   }

   public PyObject dump_dfa$14(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      Py.printComma(PyString.fromInterned("Dump of DFA for"));
      Py.println(var1.getlocal(1));
      var1.setline(223);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(223);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(224);
         Py.printComma(PyString.fromInterned("  State"));
         Py.printComma(var1.getlocal(3));
         Object var10000 = var1.getlocal(4).__getattr__("isfinal");
         if (((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("(final)");
         }

         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("");
         }

         Py.println((PyObject)var10000);
         var1.setline(225);
         PyObject var9 = var1.getlocal(4).__getattr__("arcs").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(225);
            var6 = var9.__iternext__();
            if (var6 == null) {
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(5, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(6, var8);
            var8 = null;
            var1.setline(226);
            Py.println(PyString.fromInterned("    %s -> %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(2).__getattr__("index").__call__(var2, var1.getlocal(6))})));
         }
      }
   }

   public PyObject simplify_dfa$15(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyObject var3 = var1.getglobal("True");
      var1.setlocal(2, var3);
      var3 = null;

      label38:
      while(true) {
         var1.setline(236);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(237);
         var3 = var1.getglobal("False");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(238);
         var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            label28:
            while(true) {
               var1.setline(238);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  continue label38;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(239);
               PyObject var9 = var1.getglobal("range").__call__(var2, var1.getlocal(3)._add(Py.newInteger(1)), var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

               PyObject var10000;
               PyObject var7;
               do {
                  var1.setline(239);
                  var6 = var9.__iternext__();
                  if (var6 == null) {
                     continue label28;
                  }

                  var1.setlocal(5, var6);
                  var1.setline(240);
                  var7 = var1.getlocal(1).__getitem__(var1.getlocal(5));
                  var1.setlocal(6, var7);
                  var7 = null;
                  var1.setline(241);
                  var7 = var1.getlocal(4);
                  var10000 = var7._eq(var1.getlocal(6));
                  var7 = null;
               } while(!var10000.__nonzero__());

               var1.setline(243);
               var1.getlocal(1).__delitem__(var1.getlocal(5));
               var1.setline(244);
               var7 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(244);
                  PyObject var8 = var7.__iternext__();
                  if (var8 == null) {
                     var1.setline(246);
                     var7 = var1.getglobal("True");
                     var1.setlocal(2, var7);
                     var7 = null;
                     break;
                  }

                  var1.setlocal(7, var8);
                  var1.setline(245);
                  var1.getlocal(7).__getattr__("unifystate").__call__(var2, var1.getlocal(6), var1.getlocal(4));
               }
            }
         }
      }
   }

   public PyObject parse_rhs$16(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(0).__getattr__("parse_alt").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(252);
      var3 = var1.getlocal(0).__getattr__("value");
      PyObject var10000 = var3._ne(PyString.fromInterned("|"));
      var3 = null;
      PyTuple var8;
      if (var10000.__nonzero__()) {
         var1.setline(253);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(255);
         PyObject var7 = var1.getglobal("NFAState").__call__(var2);
         var1.setlocal(3, var7);
         var4 = null;
         var1.setline(256);
         var7 = var1.getglobal("NFAState").__call__(var2);
         var1.setlocal(4, var7);
         var4 = null;
         var1.setline(257);
         var1.getlocal(3).__getattr__("addarc").__call__(var2, var1.getlocal(1));
         var1.setline(258);
         var1.getlocal(2).__getattr__("addarc").__call__(var2, var1.getlocal(4));

         while(true) {
            var1.setline(259);
            var7 = var1.getlocal(0).__getattr__("value");
            var10000 = var7._eq(PyString.fromInterned("|"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(264);
               var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(260);
            var1.getlocal(0).__getattr__("gettoken").__call__(var2);
            var1.setline(261);
            var7 = var1.getlocal(0).__getattr__("parse_alt").__call__(var2);
            PyObject[] var9 = Py.unpackSequence(var7, 2);
            PyObject var6 = var9[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(2, var6);
            var6 = null;
            var4 = null;
            var1.setline(262);
            var1.getlocal(3).__getattr__("addarc").__call__(var2, var1.getlocal(1));
            var1.setline(263);
            var1.getlocal(2).__getattr__("addarc").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject parse_alt$17(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getattr__("parse_item").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(269);
         var3 = var1.getlocal(0).__getattr__("value");
         PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("("), PyString.fromInterned("[")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("type");
            var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getglobal("token").__getattr__("STRING")}));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(274);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(271);
         var3 = var1.getlocal(0).__getattr__("parse_item").__call__(var2);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(272);
         var1.getlocal(2).__getattr__("addarc").__call__(var2, var1.getlocal(3));
         var1.setline(273);
         var3 = var1.getlocal(4);
         var1.setlocal(2, var3);
         var3 = null;
      }
   }

   public PyObject parse_item$18(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getlocal(0).__getattr__("value");
      PyObject var10000 = var3._eq(PyString.fromInterned("["));
      var3 = null;
      PyObject[] var5;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(279);
         var1.getlocal(0).__getattr__("gettoken").__call__(var2);
         var1.setline(280);
         var3 = var1.getlocal(0).__getattr__("parse_rhs").__call__(var2);
         PyObject[] var9 = Py.unpackSequence(var3, 2);
         PyObject var8 = var9[0];
         var1.setlocal(1, var8);
         var5 = null;
         var8 = var9[1];
         var1.setlocal(2, var8);
         var5 = null;
         var3 = null;
         var1.setline(281);
         var1.getlocal(0).__getattr__("expect").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("OP"), (PyObject)PyString.fromInterned("]"));
         var1.setline(282);
         var1.getlocal(1).__getattr__("addarc").__call__(var2, var1.getlocal(2));
         var1.setline(283);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(285);
         PyObject var4 = var1.getlocal(0).__getattr__("parse_atom").__call__(var2);
         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
         var1.setline(286);
         var4 = var1.getlocal(0).__getattr__("value");
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(287);
         var4 = var1.getlocal(3);
         var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned("+"), PyString.fromInterned("*")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(288);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(289);
            var1.getlocal(0).__getattr__("gettoken").__call__(var2);
            var1.setline(290);
            var1.getlocal(2).__getattr__("addarc").__call__(var2, var1.getlocal(1));
            var1.setline(291);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(PyString.fromInterned("+"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(292);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(294);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1)});
               var1.f_lasti = -1;
               return var7;
            }
         }
      }
   }

   public PyObject parse_atom$19(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var3 = var1.getlocal(0).__getattr__("value");
      PyObject var10000 = var3._eq(PyString.fromInterned("("));
      var3 = null;
      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(299);
         var1.getlocal(0).__getattr__("gettoken").__call__(var2);
         var1.setline(300);
         var3 = var1.getlocal(0).__getattr__("parse_rhs").__call__(var2);
         PyObject[] var7 = Py.unpackSequence(var3, 2);
         PyObject var5 = var7[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(301);
         var1.getlocal(0).__getattr__("expect").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("OP"), (PyObject)PyString.fromInterned(")"));
         var1.setline(302);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(303);
         PyObject var4 = var1.getlocal(0).__getattr__("type");
         var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getglobal("token").__getattr__("STRING")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(304);
            var4 = var1.getglobal("NFAState").__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(305);
            var4 = var1.getglobal("NFAState").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(306);
            var1.getlocal(1).__getattr__("addarc").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("value"));
            var1.setline(307);
            var1.getlocal(0).__getattr__("gettoken").__call__(var2);
            var1.setline(308);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(310);
            var1.getlocal(0).__getattr__("raise_error").__call__((ThreadState)var2, PyString.fromInterned("expected (...) or NAME or STRING, got %s/%s"), (PyObject)var1.getlocal(0).__getattr__("type"), (PyObject)var1.getlocal(0).__getattr__("value"));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject expect$20(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("value");
            var10000 = var3._ne(var1.getlocal(2));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(315);
         var10000 = var1.getlocal(0).__getattr__("raise_error");
         PyObject[] var4 = new PyObject[]{PyString.fromInterned("expected %s/%s, got %s/%s"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("type"), var1.getlocal(0).__getattr__("value")};
         var10000.__call__(var2, var4);
      }

      var1.setline(317);
      var3 = var1.getlocal(0).__getattr__("value");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(318);
      var1.getlocal(0).__getattr__("gettoken").__call__(var2);
      var1.setline(319);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gettoken$21(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyObject var3 = var1.getlocal(0).__getattr__("generator").__getattr__("next").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(323);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("tokenize").__getattr__("COMMENT"), var1.getglobal("tokenize").__getattr__("NL")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(325);
            var3 = var1.getlocal(1);
            PyObject[] var4 = Py.unpackSequence(var3, 5);
            PyObject var5 = var4[0];
            var1.getlocal(0).__setattr__("type", var5);
            var5 = null;
            var5 = var4[1];
            var1.getlocal(0).__setattr__("value", var5);
            var5 = null;
            var5 = var4[2];
            var1.getlocal(0).__setattr__("begin", var5);
            var5 = null;
            var5 = var4[3];
            var1.getlocal(0).__setattr__("end", var5);
            var5 = null;
            var5 = var4[4];
            var1.getlocal(0).__setattr__("line", var5);
            var5 = null;
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(324);
         var3 = var1.getlocal(0).__getattr__("generator").__getattr__("next").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject raise_error$22(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      if (var1.getlocal(2).__nonzero__()) {
         try {
            var1.setline(331);
            PyObject var3 = var1.getlocal(1)._mod(var1.getlocal(2));
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var5) {
            Py.setException(var5, var1);
            var1.setline(333);
            PyObject var4 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(1)}))._add(var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(2))));
            var1.setlocal(1, var4);
            var4 = null;
         }
      }

      var1.setline(334);
      throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("end").__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("end").__getitem__(Py.newInteger(1)), var1.getlocal(0).__getattr__("line")}))));
   }

   public PyObject NFAState$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(339);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$24, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(342);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, addarc$25, (PyObject)null);
      var1.setlocal("addarc", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$24(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"arcs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addarc$25(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         PyObject var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str"));
         }

         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(344);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("NFAState")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(345);
         var1.getlocal(0).__getattr__("arcs").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)})));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject DFAState$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(349);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$27, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(357);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addarc$28, (PyObject)null);
      var1.setlocal("addarc", var4);
      var3 = null;
      var1.setline(363);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unifystate$29, (PyObject)null);
      var1.setlocal("unifystate", var4);
      var3 = null;
      var1.setline(368);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$30, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(382);
      PyObject var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$27(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("dict")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(351);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getglobal("iter").__call__(var2, var1.getlocal(1)).__getattr__("next").__call__(var2), var1.getglobal("NFAState")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(352);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("NFAState")).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            } else {
               var1.setline(353);
               PyObject var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("nfaset", var3);
               var3 = null;
               var1.setline(354);
               var3 = var1.getlocal(2);
               var10000 = var3._in(var1.getlocal(1));
               var3 = null;
               var3 = var10000;
               var1.getlocal(0).__setattr__("isfinal", var3);
               var3 = null;
               var1.setline(355);
               PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"arcs", var4);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject addarc$28(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(359);
         PyObject var3;
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._notin(var1.getlocal(0).__getattr__("arcs"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(360);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("DFAState")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(361);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__getattr__("arcs").__setitem__(var1.getlocal(2), var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject unifystate$29(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyObject var3 = var1.getlocal(0).__getattr__("arcs").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(364);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(365);
         PyObject var7 = var1.getlocal(4);
         PyObject var10000 = var7._is(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(366);
            var7 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("arcs").__setitem__(var1.getlocal(3), var7);
            var5 = null;
         }
      }
   }

   public PyObject __eq__$30(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("DFAState")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(371);
         PyObject var3 = var1.getlocal(0).__getattr__("isfinal");
         var10000 = var3._ne(var1.getlocal(1).__getattr__("isfinal"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(372);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(375);
            PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("arcs"));
            var10000 = var4._ne(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("arcs")));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(376);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(377);
               var4 = var1.getlocal(0).__getattr__("arcs").__getattr__("iteritems").__call__(var2).__iter__();

               do {
                  var1.setline(377);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(380);
                     var3 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  PyObject[] var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(2, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var1.setline(378);
                  PyObject var8 = var1.getlocal(3);
                  var10000 = var8._isnot(var1.getlocal(1).__getattr__("arcs").__getattr__("get").__call__(var2, var1.getlocal(2)));
                  var6 = null;
               } while(!var10000.__nonzero__());

               var1.setline(379);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject generate_grammar$31(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyObject var3 = var1.getglobal("ParserGenerator").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(386);
      var3 = var1.getlocal(1).__getattr__("make_grammar").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public pgen$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PgenGrammar$1 = Py.newCode(0, var2, var1, "PgenGrammar", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ParserGenerator$2 = Py.newCode(0, var2, var1, "ParserGenerator", 10, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "stream", "close_stream"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 12, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c", "names", "name", "i", "dfa", "states", "state", "arcs", "label", "next"};
      make_grammar$4 = Py.newCode(1, var2, var1, "make_grammar", 27, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c", "name", "rawfirst", "first", "label", "ilabel"};
      make_first$5 = Py.newCode(3, var2, var1, "make_first", 52, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c", "label", "ilabel", "itoken", "value"};
      make_label$6 = Py.newCode(3, var2, var1, "make_label", 61, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "name"};
      addfirstsets$7 = Py.newCode(1, var2, var1, "addfirstsets", 107, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "dfa", "state", "totalset", "overlapcheck", "label", "next", "fset", "inverse", "itsfirst", "symbol"};
      calcfirst$8 = Py.newCode(2, var2, var1, "calcfirst", 115, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dfas", "startsymbol", "name", "a", "z", "dfa", "oldlen", "newlen"};
      parse$9 = Py.newCode(1, var2, var1, "parse", 145, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "finish", "closure", "states", "state", "arcs", "nfastate", "label", "next", "nfaset", "st", "addclosure"};
      String[] var10001 = var2;
      pgen$py var10007 = self;
      var2 = new String[]{"addclosure"};
      make_dfa$10 = Py.newCode(3, var10001, var1, "make_dfa", 169, false, false, var10007, 10, var2, (String[])null, 1, 4097);
      var2 = new String[]{"state", "base"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"addclosure"};
      closure$11 = Py.newCode(1, var10001, var1, "closure", 176, false, false, var10007, 11, (String[])null, var2, 0, 4097);
      var2 = new String[]{"state", "base", "label", "next"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"addclosure"};
      addclosure$12 = Py.newCode(2, var10001, var1, "addclosure", 180, false, false, var10007, 12, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "name", "start", "finish", "todo", "i", "state", "label", "next", "j"};
      dump_nfa$13 = Py.newCode(4, var2, var1, "dump_nfa", 205, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "dfa", "i", "state", "label", "next"};
      dump_dfa$14 = Py.newCode(3, var2, var1, "dump_dfa", 221, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dfa", "changes", "i", "state_i", "j", "state_j", "state"};
      simplify_dfa$15 = Py.newCode(2, var2, var1, "simplify_dfa", 228, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "z", "aa", "zz"};
      parse_rhs$16 = Py.newCode(1, var2, var1, "parse_rhs", 249, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "c", "d"};
      parse_alt$17 = Py.newCode(1, var2, var1, "parse_alt", 266, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "z", "value"};
      parse_item$18 = Py.newCode(1, var2, var1, "parse_item", 276, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "z"};
      parse_atom$19 = Py.newCode(1, var2, var1, "parse_atom", 296, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value"};
      expect$20 = Py.newCode(3, var2, var1, "expect", 313, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tup"};
      gettoken$21 = Py.newCode(1, var2, var1, "gettoken", 321, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      raise_error$22 = Py.newCode(3, var2, var1, "raise_error", 328, true, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NFAState$23 = Py.newCode(0, var2, var1, "NFAState", 337, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$24 = Py.newCode(1, var2, var1, "__init__", 339, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "next", "label"};
      addarc$25 = Py.newCode(3, var2, var1, "addarc", 342, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DFAState$26 = Py.newCode(0, var2, var1, "DFAState", 347, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nfaset", "final"};
      __init__$27 = Py.newCode(3, var2, var1, "__init__", 349, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "next", "label"};
      addarc$28 = Py.newCode(3, var2, var1, "addarc", 357, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "old", "new", "label", "next"};
      unifystate$29 = Py.newCode(3, var2, var1, "unifystate", 363, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "label", "next"};
      __eq__$30 = Py.newCode(2, var2, var1, "__eq__", 368, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "p"};
      generate_grammar$31 = Py.newCode(1, var2, var1, "generate_grammar", 384, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pgen$py("lib2to3/pgen2/pgen$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pgen$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PgenGrammar$1(var2, var3);
         case 2:
            return this.ParserGenerator$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.make_grammar$4(var2, var3);
         case 5:
            return this.make_first$5(var2, var3);
         case 6:
            return this.make_label$6(var2, var3);
         case 7:
            return this.addfirstsets$7(var2, var3);
         case 8:
            return this.calcfirst$8(var2, var3);
         case 9:
            return this.parse$9(var2, var3);
         case 10:
            return this.make_dfa$10(var2, var3);
         case 11:
            return this.closure$11(var2, var3);
         case 12:
            return this.addclosure$12(var2, var3);
         case 13:
            return this.dump_nfa$13(var2, var3);
         case 14:
            return this.dump_dfa$14(var2, var3);
         case 15:
            return this.simplify_dfa$15(var2, var3);
         case 16:
            return this.parse_rhs$16(var2, var3);
         case 17:
            return this.parse_alt$17(var2, var3);
         case 18:
            return this.parse_item$18(var2, var3);
         case 19:
            return this.parse_atom$19(var2, var3);
         case 20:
            return this.expect$20(var2, var3);
         case 21:
            return this.gettoken$21(var2, var3);
         case 22:
            return this.raise_error$22(var2, var3);
         case 23:
            return this.NFAState$23(var2, var3);
         case 24:
            return this.__init__$24(var2, var3);
         case 25:
            return this.addarc$25(var2, var3);
         case 26:
            return this.DFAState$26(var2, var3);
         case 27:
            return this.__init__$27(var2, var3);
         case 28:
            return this.addarc$28(var2, var3);
         case 29:
            return this.unifystate$29(var2, var3);
         case 30:
            return this.__eq__$30(var2, var3);
         case 31:
            return this.generate_grammar$31(var2, var3);
         default:
            return null;
      }
   }
}
