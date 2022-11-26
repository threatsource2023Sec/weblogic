package compiler;

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
@MTime(1498849384000L)
@Filename("compiler/transformer.py")
public class transformer$py extends PyFunctionTable implements PyRunnable {
   static transformer$py self;
   static final PyCode f$0;
   static final PyCode WalkerError$1;
   static final PyCode parseFile$2;
   static final PyCode parse$3;
   static final PyCode asList$4;
   static final PyCode extractLineNo$5;
   static final PyCode Node$6;
   static final PyCode Transformer$7;
   static final PyCode __init__$8;
   static final PyCode transform$9;
   static final PyCode parsesuite$10;
   static final PyCode parseexpr$11;
   static final PyCode parsefile$12;
   static final PyCode compile_node$13;
   static final PyCode single_input$14;
   static final PyCode file_input$15;
   static final PyCode eval_input$16;
   static final PyCode decorator_name$17;
   static final PyCode decorator$18;
   static final PyCode decorators$19;
   static final PyCode funcdef$20;
   static final PyCode lambdef$21;
   static final PyCode classdef$22;
   static final PyCode stmt$23;
   static final PyCode simple_stmt$24;
   static final PyCode parameters$25;
   static final PyCode varargslist$26;
   static final PyCode fpdef$27;
   static final PyCode fplist$28;
   static final PyCode dotted_name$29;
   static final PyCode comp_op$30;
   static final PyCode trailer$31;
   static final PyCode sliceop$32;
   static final PyCode argument$33;
   static final PyCode expr_stmt$34;
   static final PyCode print_stmt$35;
   static final PyCode del_stmt$36;
   static final PyCode pass_stmt$37;
   static final PyCode break_stmt$38;
   static final PyCode continue_stmt$39;
   static final PyCode return_stmt$40;
   static final PyCode yield_stmt$41;
   static final PyCode yield_expr$42;
   static final PyCode raise_stmt$43;
   static final PyCode import_stmt$44;
   static final PyCode import_name$45;
   static final PyCode import_from$46;
   static final PyCode global_stmt$47;
   static final PyCode exec_stmt$48;
   static final PyCode assert_stmt$49;
   static final PyCode if_stmt$50;
   static final PyCode while_stmt$51;
   static final PyCode for_stmt$52;
   static final PyCode try_stmt$53;
   static final PyCode with_stmt$54;
   static final PyCode with_var$55;
   static final PyCode suite$56;
   static final PyCode testlist$57;
   static final PyCode testlist_gexp$58;
   static final PyCode test$59;
   static final PyCode or_test$60;
   static final PyCode and_test$61;
   static final PyCode not_test$62;
   static final PyCode comparison$63;
   static final PyCode expr$64;
   static final PyCode xor_expr$65;
   static final PyCode and_expr$66;
   static final PyCode shift_expr$67;
   static final PyCode arith_expr$68;
   static final PyCode term$69;
   static final PyCode factor$70;
   static final PyCode power$71;
   static final PyCode atom$72;
   static final PyCode atom_lpar$73;
   static final PyCode atom_lsqb$74;
   static final PyCode atom_lbrace$75;
   static final PyCode atom_backquote$76;
   static final PyCode atom_number$77;
   static final PyCode decode_literal$78;
   static final PyCode atom_string$79;
   static final PyCode atom_name$80;
   static final PyCode lookup_node$81;
   static final PyCode com_node$82;
   static final PyCode com_NEWLINE$83;
   static final PyCode com_arglist$84;
   static final PyCode com_fpdef$85;
   static final PyCode com_fplist$86;
   static final PyCode com_dotted_name$87;
   static final PyCode com_dotted_as_name$88;
   static final PyCode com_dotted_as_names$89;
   static final PyCode com_import_as_name$90;
   static final PyCode com_import_as_names$91;
   static final PyCode com_bases$92;
   static final PyCode com_try_except_finally$93;
   static final PyCode com_with$94;
   static final PyCode com_with_var$95;
   static final PyCode com_augassign_op$96;
   static final PyCode com_augassign$97;
   static final PyCode com_assign$98;
   static final PyCode com_assign_tuple$99;
   static final PyCode com_assign_list$100;
   static final PyCode com_assign_name$101;
   static final PyCode com_assign_trailer$102;
   static final PyCode com_assign_attr$103;
   static final PyCode com_binary$104;
   static final PyCode com_stmt$105;
   static final PyCode com_append_stmt$106;
   static final PyCode com_list_constructor$107;
   static final PyCode com_list_comprehension$108;
   static final PyCode com_list_iter$109;
   static final PyCode com_list_constructor$110;
   static final PyCode com_generator_expression$111;
   static final PyCode com_gen_iter$112;
   static final PyCode com_dictmaker$113;
   static final PyCode com_apply_trailer$114;
   static final PyCode com_select_member$115;
   static final PyCode com_call_function$116;
   static final PyCode com_argument$117;
   static final PyCode com_subscriptlist$118;
   static final PyCode com_subscript$119;
   static final PyCode com_sliceobj$120;
   static final PyCode com_slice$121;
   static final PyCode get_docstring$122;
   static final PyCode debug_tree$123;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Parse tree transformation module.\n\nTransforms Python source code into an abstract syntax tree (AST)\ndefined in the ast module.\n\nThe simplest ways to invoke this module are via parse and parseFile.\nparse(buf) -> AST\nparseFile(path) -> AST\n"));
      var1.setline(9);
      PyString.fromInterned("Parse tree transformation module.\n\nTransforms Python source code into an abstract syntax tree (AST)\ndefined in the ast module.\n\nThe simplest ways to invoke this module are via parse and parseFile.\nparse(buf) -> AST\nparseFile(path) -> AST\n");
      var1.setline(28);
      imp.importAll("compiler.ast", var1, -1);
      var1.setline(29);
      PyObject var3 = imp.importOne("symbol", var1, -1);
      var1.setlocal("symbol", var3);
      var3 = null;
      var1.setline(30);
      var3 = imp.importOne("token", var1, -1);
      var1.setlocal("token", var3);
      var3 = null;
      var1.setline(31);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(32);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__not__().__nonzero__()) {
         var1.setline(33);
         var3 = imp.importOne("parser", var1, -1);
         var1.setlocal("parser", var3);
         var3 = null;
      }

      var1.setline(35);
      PyObject[] var8 = new PyObject[]{var1.getname("StandardError")};
      PyObject var4 = Py.makeClass("WalkerError", var8, WalkerError$1);
      var1.setlocal("WalkerError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(38);
      String[] var9 = new String[]{"CO_VARARGS", "CO_VARKEYWORDS"};
      var8 = imp.importFrom("compiler.consts", var9, var1, -1);
      var4 = var8[0];
      var1.setlocal("CO_VARARGS", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("CO_VARKEYWORDS", var4);
      var4 = null;
      var1.setline(39);
      var9 = new String[]{"OP_ASSIGN", "OP_DELETE", "OP_APPLY"};
      var8 = imp.importFrom("compiler.consts", var9, var1, -1);
      var4 = var8[0];
      var1.setlocal("OP_ASSIGN", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("OP_DELETE", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("OP_APPLY", var4);
      var4 = null;
      var1.setline(41);
      var8 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var8, parseFile$2, (PyObject)null);
      var1.setlocal("parseFile", var10);
      var3 = null;
      var1.setline(51);
      var8 = new PyObject[]{PyString.fromInterned("exec")};
      var10 = new PyFunction(var1.f_globals, var8, parse$3, (PyObject)null);
      var1.setlocal("parse", var10);
      var3 = null;
      var1.setline(60);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, asList$4, (PyObject)null);
      var1.setlocal("asList", var10);
      var3 = null;
      var1.setline(74);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, extractLineNo$5, (PyObject)null);
      var1.setlocal("extractLineNo", var10);
      var3 = null;
      var1.setline(84);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, Node$6, (PyObject)null);
      var1.setlocal("Node", var10);
      var3 = null;
      var1.setline(96);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Transformer", var8, Transformer$7);
      var1.setlocal("Transformer", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1381);
      PyList var11 = new PyList(new PyObject[]{var1.getname("symbol").__getattr__("expr_stmt"), var1.getname("symbol").__getattr__("testlist"), var1.getname("symbol").__getattr__("testlist_safe"), var1.getname("symbol").__getattr__("test"), var1.getname("symbol").__getattr__("or_test"), var1.getname("symbol").__getattr__("and_test"), var1.getname("symbol").__getattr__("not_test"), var1.getname("symbol").__getattr__("comparison"), var1.getname("symbol").__getattr__("expr"), var1.getname("symbol").__getattr__("xor_expr"), var1.getname("symbol").__getattr__("and_expr"), var1.getname("symbol").__getattr__("shift_expr"), var1.getname("symbol").__getattr__("arith_expr"), var1.getname("symbol").__getattr__("term"), var1.getname("symbol").__getattr__("factor"), var1.getname("symbol").__getattr__("power")});
      var1.setlocal("_doc_nodes", var11);
      var3 = null;
      var1.setline(1402);
      PyDictionary var12 = new PyDictionary(new PyObject[]{var1.getname("token").__getattr__("LESS"), PyString.fromInterned("<"), var1.getname("token").__getattr__("GREATER"), PyString.fromInterned(">"), var1.getname("token").__getattr__("EQEQUAL"), PyString.fromInterned("=="), var1.getname("token").__getattr__("EQUAL"), PyString.fromInterned("=="), var1.getname("token").__getattr__("LESSEQUAL"), PyString.fromInterned("<="), var1.getname("token").__getattr__("GREATEREQUAL"), PyString.fromInterned(">="), var1.getname("token").__getattr__("NOTEQUAL"), PyString.fromInterned("!=")});
      var1.setlocal("_cmp_types", var12);
      var3 = null;
      var1.setline(1412);
      var11 = new PyList(new PyObject[]{var1.getname("symbol").__getattr__("funcdef"), var1.getname("symbol").__getattr__("classdef"), var1.getname("symbol").__getattr__("stmt"), var1.getname("symbol").__getattr__("small_stmt"), var1.getname("symbol").__getattr__("flow_stmt"), var1.getname("symbol").__getattr__("simple_stmt"), var1.getname("symbol").__getattr__("compound_stmt"), var1.getname("symbol").__getattr__("expr_stmt"), var1.getname("symbol").__getattr__("print_stmt"), var1.getname("symbol").__getattr__("del_stmt"), var1.getname("symbol").__getattr__("pass_stmt"), var1.getname("symbol").__getattr__("break_stmt"), var1.getname("symbol").__getattr__("continue_stmt"), var1.getname("symbol").__getattr__("return_stmt"), var1.getname("symbol").__getattr__("raise_stmt"), var1.getname("symbol").__getattr__("import_stmt"), var1.getname("symbol").__getattr__("global_stmt"), var1.getname("symbol").__getattr__("exec_stmt"), var1.getname("symbol").__getattr__("assert_stmt"), var1.getname("symbol").__getattr__("if_stmt"), var1.getname("symbol").__getattr__("while_stmt"), var1.getname("symbol").__getattr__("for_stmt"), var1.getname("symbol").__getattr__("try_stmt"), var1.getname("symbol").__getattr__("with_stmt"), var1.getname("symbol").__getattr__("suite"), var1.getname("symbol").__getattr__("testlist"), var1.getname("symbol").__getattr__("testlist_safe"), var1.getname("symbol").__getattr__("test"), var1.getname("symbol").__getattr__("and_test"), var1.getname("symbol").__getattr__("not_test"), var1.getname("symbol").__getattr__("comparison"), var1.getname("symbol").__getattr__("exprlist"), var1.getname("symbol").__getattr__("expr"), var1.getname("symbol").__getattr__("xor_expr"), var1.getname("symbol").__getattr__("and_expr"), var1.getname("symbol").__getattr__("shift_expr"), var1.getname("symbol").__getattr__("arith_expr"), var1.getname("symbol").__getattr__("term"), var1.getname("symbol").__getattr__("factor"), var1.getname("symbol").__getattr__("power"), var1.getname("symbol").__getattr__("atom")});
      var1.setlocal("_legal_node_types", var11);
      var3 = null;
      var1.setline(1456);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("symbol"), (PyObject)PyString.fromInterned("yield_stmt")).__nonzero__()) {
         var1.setline(1457);
         var1.getname("_legal_node_types").__getattr__("append").__call__(var2, var1.getname("symbol").__getattr__("yield_stmt"));
      }

      var1.setline(1458);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("symbol"), (PyObject)PyString.fromInterned("yield_expr")).__nonzero__()) {
         var1.setline(1459);
         var1.getname("_legal_node_types").__getattr__("append").__call__(var2, var1.getname("symbol").__getattr__("yield_expr"));
      }

      var1.setline(1461);
      var11 = new PyList(new PyObject[]{var1.getname("symbol").__getattr__("test"), var1.getname("symbol").__getattr__("or_test"), var1.getname("symbol").__getattr__("and_test"), var1.getname("symbol").__getattr__("not_test"), var1.getname("symbol").__getattr__("comparison"), var1.getname("symbol").__getattr__("expr"), var1.getname("symbol").__getattr__("xor_expr"), var1.getname("symbol").__getattr__("and_expr"), var1.getname("symbol").__getattr__("shift_expr"), var1.getname("symbol").__getattr__("arith_expr"), var1.getname("symbol").__getattr__("term"), var1.getname("symbol").__getattr__("factor")});
      var1.setlocal("_assign_types", var11);
      var3 = null;
      var1.setline(1476);
      var12 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_names", var12);
      var3 = null;
      var1.setline(1477);
      var3 = var1.getname("symbol").__getattr__("sym_name").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1477);
         var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var7;
         if (var4 == null) {
            var1.setline(1479);
            var3 = var1.getname("token").__getattr__("tok_name").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(1479);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1482);
                  var8 = Py.EmptyObjects;
                  var10 = new PyFunction(var1.f_globals, var8, debug_tree$123, (PyObject)null);
                  var1.setlocal("debug_tree", var10);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal("k", var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal("v", var6);
               var6 = null;
               var1.setline(1480);
               var7 = var1.getname("v");
               var1.getname("_names").__setitem__(var1.getname("k"), var7);
               var5 = null;
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal("k", var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal("v", var6);
         var6 = null;
         var1.setline(1478);
         var7 = var1.getname("v");
         var1.getname("_names").__setitem__(var1.getname("k"), var7);
         var5 = null;
      }
   }

   public PyObject WalkerError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(36);
      return var1.getf_locals();
   }

   public PyObject parseFile$2(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("U"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(1).__getattr__("read").__call__(var2)._add(PyString.fromInterned("\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(48);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(49);
      var3 = var1.getglobal("parse").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse$3(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("exec"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("single"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(53);
         var3 = var1.getglobal("Transformer").__call__(var2).__getattr__("parsesuite").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(54);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("eval"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(55);
            var3 = var1.getglobal("Transformer").__call__(var2).__getattr__("parseexpr").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(57);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("compile() arg 3 must be 'exec' or 'eval' or 'single'")));
         }
      }
   }

   public PyObject asList$4(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(62);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(62);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(72);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(63);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("asList")).__nonzero__()) {
            var1.setline(64);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("asList").__call__(var2));
         } else {
            var1.setline(66);
            PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
            PyObject var10000 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")}))));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(67);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("tuple").__call__(var2, var1.getglobal("asList").__call__(var2, var1.getlocal(2))));
            } else {
               var1.setline(68);
               var5 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
               var10000 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(69);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("asList").__call__(var2, var1.getlocal(2)));
               } else {
                  var1.setline(71);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
               }
            }
         }
      }
   }

   public PyObject extractLineNo$5(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getglobal("tuple")).__not__().__nonzero__()) {
         var1.setline(77);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(78);
         PyObject var4 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(78);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var5);
            var1.setline(79);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
               var1.setline(80);
               PyObject var6 = var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(1));
               var1.setlocal(2, var6);
               var6 = null;
               var1.setline(81);
               var6 = var1.getlocal(2);
               PyObject var10000 = var6._isnot(var1.getglobal("None"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(82);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject Node$6(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(86);
      if (var1.getglobal("nodes").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         try {
            var1.setline(88);
            PyObject var10000 = var1.getglobal("nodes").__getitem__(var1.getlocal(1));
            PyObject[] var6 = Py.EmptyObjects;
            String[] var7 = new String[0];
            var10000 = var10000._callextra(var6, var7, var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)null);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("TypeError"))) {
               var1.setline(90);
               Py.printComma(var1.getglobal("nodes").__getitem__(var1.getlocal(1)));
               Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
               Py.println(var1.getlocal(0));
               var1.setline(91);
               throw Py.makeException();
            } else {
               throw var4;
            }
         }
      } else {
         var1.setline(93);
         throw Py.makeException(var1.getglobal("WalkerError"), PyString.fromInterned("Can't find appropriate Node type: %s")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0))));
      }
   }

   public PyObject Transformer$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Utility object for transforming Python parse trees.\n\n    Exposes the following methods:\n        tree = transform(ast_tree)\n        tree = parsesuite(text)\n        tree = parseexpr(text)\n        tree = parsefile(fileob | filename)\n    "));
      var1.setline(104);
      PyString.fromInterned("Utility object for transforming Python parse trees.\n\n    Exposes the following methods:\n        tree = transform(ast_tree)\n        tree = parsesuite(text)\n        tree = parseexpr(text)\n        tree = parsefile(fileob | filename)\n    ");
      var1.setline(106);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, transform$9, PyString.fromInterned("Transform an AST into a modified parse tree."));
      var1.setlocal("transform", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parsesuite$10, PyString.fromInterned("Return a modified parse tree for the given suite text."));
      var1.setlocal("parsesuite", var4);
      var3 = null;
      var1.setline(132);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseexpr$11, PyString.fromInterned("Return a modified parse tree for the given expression text."));
      var1.setlocal("parseexpr", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parsefile$12, PyString.fromInterned("Return a modified parse tree for the contents of the given file."));
      var1.setlocal("parsefile", var4);
      var3 = null;
      var1.setline(147);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compile_node$13, (PyObject)null);
      var1.setlocal("compile_node", var4);
      var3 = null;
      var1.setline(171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, single_input$14, (PyObject)null);
      var1.setlocal("single_input", var4);
      var3 = null;
      var1.setline(181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, file_input$15, (PyObject)null);
      var1.setlocal("file_input", var4);
      var3 = null;
      var1.setline(193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, eval_input$16, (PyObject)null);
      var1.setlocal("eval_input", var4);
      var3 = null;
      var1.setline(198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decorator_name$17, (PyObject)null);
      var1.setlocal("decorator_name", var4);
      var3 = null;
      var1.setline(212);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decorator$18, (PyObject)null);
      var1.setlocal("decorator", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decorators$19, (PyObject)null);
      var1.setlocal("decorators", var4);
      var3 = null;
      var1.setline(237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, funcdef$20, (PyObject)null);
      var1.setlocal("funcdef", var4);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lambdef$21, (PyObject)null);
      var1.setlocal("lambdef", var4);
      var3 = null;
      var1.setline(282);
      PyObject var5 = var1.getname("lambdef");
      var1.setlocal("old_lambdef", var5);
      var3 = null;
      var1.setline(284);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, classdef$22, (PyObject)null);
      var1.setlocal("classdef", var4);
      var3 = null;
      var1.setline(306);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stmt$23, (PyObject)null);
      var1.setlocal("stmt", var4);
      var3 = null;
      var1.setline(309);
      var5 = var1.getname("stmt");
      var1.setlocal("small_stmt", var5);
      var3 = null;
      var1.setline(310);
      var5 = var1.getname("stmt");
      var1.setlocal("flow_stmt", var5);
      var3 = null;
      var1.setline(311);
      var5 = var1.getname("stmt");
      var1.setlocal("compound_stmt", var5);
      var3 = null;
      var1.setline(313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, simple_stmt$24, (PyObject)null);
      var1.setlocal("simple_stmt", var4);
      var3 = null;
      var1.setline(320);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parameters$25, (PyObject)null);
      var1.setlocal("parameters", var4);
      var3 = null;
      var1.setline(323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, varargslist$26, (PyObject)null);
      var1.setlocal("varargslist", var4);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fpdef$27, (PyObject)null);
      var1.setlocal("fpdef", var4);
      var3 = null;
      var1.setline(329);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fplist$28, (PyObject)null);
      var1.setlocal("fplist", var4);
      var3 = null;
      var1.setline(332);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dotted_name$29, (PyObject)null);
      var1.setlocal("dotted_name", var4);
      var3 = null;
      var1.setline(335);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, comp_op$30, (PyObject)null);
      var1.setlocal("comp_op", var4);
      var3 = null;
      var1.setline(338);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, trailer$31, (PyObject)null);
      var1.setlocal("trailer", var4);
      var3 = null;
      var1.setline(341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sliceop$32, (PyObject)null);
      var1.setlocal("sliceop", var4);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, argument$33, (PyObject)null);
      var1.setlocal("argument", var4);
      var3 = null;
      var1.setline(352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, expr_stmt$34, (PyObject)null);
      var1.setlocal("expr_stmt", var4);
      var3 = null;
      var1.setline(369);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_stmt$35, (PyObject)null);
      var1.setlocal("print_stmt", var4);
      var3 = null;
      var1.setline(389);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, del_stmt$36, (PyObject)null);
      var1.setlocal("del_stmt", var4);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pass_stmt$37, (PyObject)null);
      var1.setlocal("pass_stmt", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, break_stmt$38, (PyObject)null);
      var1.setlocal("break_stmt", var4);
      var3 = null;
      var1.setline(398);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, continue_stmt$39, (PyObject)null);
      var1.setlocal("continue_stmt", var4);
      var3 = null;
      var1.setline(401);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, return_stmt$40, (PyObject)null);
      var1.setlocal("return_stmt", var4);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, yield_stmt$41, (PyObject)null);
      var1.setlocal("yield_stmt", var4);
      var3 = null;
      var1.setline(411);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, yield_expr$42, (PyObject)null);
      var1.setlocal("yield_expr", var4);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, raise_stmt$43, (PyObject)null);
      var1.setlocal("raise_stmt", var4);
      var3 = null;
      var1.setline(434);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, import_stmt$44, (PyObject)null);
      var1.setlocal("import_stmt", var4);
      var3 = null;
      var1.setline(439);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, import_name$45, (PyObject)null);
      var1.setlocal("import_name", var4);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, import_from$46, (PyObject)null);
      var1.setlocal("import_from", var4);
      var3 = null;
      var1.setline(466);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, global_stmt$47, (PyObject)null);
      var1.setlocal("global_stmt", var4);
      var3 = null;
      var1.setline(473);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, exec_stmt$48, (PyObject)null);
      var1.setlocal("exec_stmt", var4);
      var3 = null;
      var1.setline(487);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, assert_stmt$49, (PyObject)null);
      var1.setlocal("assert_stmt", var4);
      var3 = null;
      var1.setline(496);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, if_stmt$50, (PyObject)null);
      var1.setlocal("if_stmt", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, while_stmt$51, (PyObject)null);
      var1.setlocal("while_stmt", var4);
      var3 = null;
      var1.setline(524);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, for_stmt$52, (PyObject)null);
      var1.setlocal("for_stmt", var4);
      var3 = null;
      var1.setline(539);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, try_stmt$53, (PyObject)null);
      var1.setlocal("try_stmt", var4);
      var3 = null;
      var1.setline(542);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, with_stmt$54, (PyObject)null);
      var1.setlocal("with_stmt", var4);
      var3 = null;
      var1.setline(545);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, with_var$55, (PyObject)null);
      var1.setlocal("with_var", var4);
      var3 = null;
      var1.setline(548);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, suite$56, (PyObject)null);
      var1.setlocal("suite", var4);
      var3 = null;
      var1.setline(564);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testlist$57, (PyObject)null);
      var1.setlocal("testlist", var4);
      var3 = null;
      var1.setline(570);
      var5 = var1.getname("testlist");
      var1.setlocal("testlist_safe", var5);
      var3 = null;
      var1.setline(571);
      var5 = var1.getname("testlist");
      var1.setlocal("testlist1", var5);
      var3 = null;
      var1.setline(572);
      var5 = var1.getname("testlist");
      var1.setlocal("exprlist", var5);
      var3 = null;
      var1.setline(574);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testlist_gexp$58, (PyObject)null);
      var1.setlocal("testlist_gexp", var4);
      var3 = null;
      var1.setline(580);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$59, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      var1.setline(594);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, or_test$60, (PyObject)null);
      var1.setlocal("or_test", var4);
      var3 = null;
      var1.setline(599);
      var5 = var1.getname("or_test");
      var1.setlocal("old_test", var5);
      var3 = null;
      var1.setline(601);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, and_test$61, (PyObject)null);
      var1.setlocal("and_test", var4);
      var3 = null;
      var1.setline(605);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, not_test$62, (PyObject)null);
      var1.setlocal("not_test", var4);
      var3 = null;
      var1.setline(612);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, comparison$63, (PyObject)null);
      var1.setlocal("comparison", var4);
      var3 = null;
      var1.setline(645);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, expr$64, (PyObject)null);
      var1.setlocal("expr", var4);
      var3 = null;
      var1.setline(649);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, xor_expr$65, (PyObject)null);
      var1.setlocal("xor_expr", var4);
      var3 = null;
      var1.setline(653);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, and_expr$66, (PyObject)null);
      var1.setlocal("and_expr", var4);
      var3 = null;
      var1.setline(657);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shift_expr$67, (PyObject)null);
      var1.setlocal("shift_expr", var4);
      var3 = null;
      var1.setline(670);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, arith_expr$68, (PyObject)null);
      var1.setlocal("arith_expr", var4);
      var3 = null;
      var1.setline(682);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, term$69, (PyObject)null);
      var1.setlocal("term", var4);
      var3 = null;
      var1.setline(700);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, factor$70, (PyObject)null);
      var1.setlocal("factor", var4);
      var3 = null;
      var1.setline(713);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, power$71, (PyObject)null);
      var1.setlocal("power", var4);
      var3 = null;
      var1.setline(726);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom$72, (PyObject)null);
      var1.setlocal("atom", var4);
      var3 = null;
      var1.setline(729);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_lpar$73, (PyObject)null);
      var1.setlocal("atom_lpar", var4);
      var3 = null;
      var1.setline(734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_lsqb$74, (PyObject)null);
      var1.setlocal("atom_lsqb", var4);
      var3 = null;
      var1.setline(739);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_lbrace$75, (PyObject)null);
      var1.setlocal("atom_lbrace", var4);
      var3 = null;
      var1.setline(744);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_backquote$76, (PyObject)null);
      var1.setlocal("atom_backquote", var4);
      var3 = null;
      var1.setline(747);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_number$77, (PyObject)null);
      var1.setlocal("atom_number", var4);
      var3 = null;
      var1.setline(752);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decode_literal$78, (PyObject)null);
      var1.setlocal("decode_literal", var4);
      var3 = null;
      var1.setline(763);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_string$79, (PyObject)null);
      var1.setlocal("atom_string", var4);
      var3 = null;
      var1.setline(769);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, atom_name$80, (PyObject)null);
      var1.setlocal("atom_name", var4);
      var3 = null;
      var1.setline(785);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lookup_node$81, (PyObject)null);
      var1.setlocal("lookup_node", var4);
      var3 = null;
      var1.setline(788);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_node$82, (PyObject)null);
      var1.setlocal("com_node", var4);
      var3 = null;
      var1.setline(795);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_NEWLINE$83, (PyObject)null);
      var1.setlocal("com_NEWLINE", var4);
      var3 = null;
      var1.setline(801);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_arglist$84, (PyObject)null);
      var1.setlocal("com_arglist", var4);
      var3 = null;
      var1.setline(851);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_fpdef$85, (PyObject)null);
      var1.setlocal("com_fpdef", var4);
      var3 = null;
      var1.setline(857);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_fplist$86, (PyObject)null);
      var1.setlocal("com_fplist", var4);
      var3 = null;
      var1.setline(866);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_dotted_name$87, (PyObject)null);
      var1.setlocal("com_dotted_name", var4);
      var3 = null;
      var1.setline(874);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_dotted_as_name$88, (PyObject)null);
      var1.setlocal("com_dotted_as_name", var4);
      var3 = null;
      var1.setline(884);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_dotted_as_names$89, (PyObject)null);
      var1.setlocal("com_dotted_as_names", var4);
      var3 = null;
      var1.setline(892);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_import_as_name$90, (PyObject)null);
      var1.setlocal("com_import_as_name", var4);
      var3 = null;
      var1.setline(902);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_import_as_names$91, (PyObject)null);
      var1.setlocal("com_import_as_names", var4);
      var3 = null;
      var1.setline(910);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_bases$92, (PyObject)null);
      var1.setlocal("com_bases", var4);
      var3 = null;
      var1.setline(916);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_try_except_finally$93, (PyObject)null);
      var1.setlocal("com_try_except_finally", var4);
      var3 = null;
      var1.setline(957);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_with$94, (PyObject)null);
      var1.setlocal("com_with", var4);
      var3 = null;
      var1.setline(967);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_with_var$95, (PyObject)null);
      var1.setlocal("com_with_var", var4);
      var3 = null;
      var1.setline(971);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_augassign_op$96, (PyObject)null);
      var1.setlocal("com_augassign_op", var4);
      var3 = null;
      var1.setline(975);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_augassign$97, PyString.fromInterned("Return node suitable for lvalue of augmented assignment\n\n        Names, slices, and attributes are the only allowable nodes.\n        "));
      var1.setlocal("com_augassign", var4);
      var3 = null;
      var1.setline(985);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_assign$98, (PyObject)null);
      var1.setlocal("com_assign", var4);
      var3 = null;
      var1.setline(1029);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_assign_tuple$99, (PyObject)null);
      var1.setlocal("com_assign_tuple", var4);
      var3 = null;
      var1.setline(1035);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_assign_list$100, (PyObject)null);
      var1.setlocal("com_assign_list", var4);
      var3 = null;
      var1.setline(1045);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_assign_name$101, (PyObject)null);
      var1.setlocal("com_assign_name", var4);
      var3 = null;
      var1.setline(1048);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_assign_trailer$102, (PyObject)null);
      var1.setlocal("com_assign_trailer", var4);
      var3 = null;
      var1.setline(1058);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_assign_attr$103, (PyObject)null);
      var1.setlocal("com_assign_attr", var4);
      var3 = null;
      var1.setline(1061);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_binary$104, PyString.fromInterned("Compile 'NODE (OP NODE)*' into (type, [ node1, ..., nodeN ])."));
      var1.setlocal("com_binary", var4);
      var3 = null;
      var1.setline(1073);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_stmt$105, (PyObject)null);
      var1.setlocal("com_stmt", var4);
      var3 = null;
      var1.setline(1080);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_append_stmt$106, (PyObject)null);
      var1.setlocal("com_append_stmt", var4);
      var3 = null;
      var1.setline(1088);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("symbol"), (PyObject)PyString.fromInterned("list_for")).__nonzero__()) {
         var1.setline(1089);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, com_list_constructor$107, (PyObject)null);
         var1.setlocal("com_list_constructor", var4);
         var3 = null;
         var1.setline(1102);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, com_list_comprehension$108, (PyObject)null);
         var1.setlocal("com_list_comprehension", var4);
         var3 = null;
         var1.setline(1137);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, com_list_iter$109, (PyObject)null);
         var1.setlocal("com_list_iter", var4);
         var3 = null;
      } else {
         var1.setline(1141);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, com_list_constructor$110, (PyObject)null);
         var1.setlocal("com_list_constructor", var4);
         var3 = null;
      }

      var1.setline(1147);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("symbol"), (PyObject)PyString.fromInterned("gen_for")).__nonzero__()) {
         var1.setline(1148);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, com_generator_expression$111, (PyObject)null);
         var1.setlocal("com_generator_expression", var4);
         var3 = null;
         var1.setline(1182);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, com_gen_iter$112, (PyObject)null);
         var1.setlocal("com_gen_iter", var4);
         var3 = null;
      }

      var1.setline(1186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_dictmaker$113, (PyObject)null);
      var1.setlocal("com_dictmaker", var4);
      var3 = null;
      var1.setline(1194);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_apply_trailer$114, (PyObject)null);
      var1.setlocal("com_apply_trailer", var4);
      var3 = null;
      var1.setline(1205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_select_member$115, (PyObject)null);
      var1.setlocal("com_select_member", var4);
      var3 = null;
      var1.setline(1210);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_call_function$116, (PyObject)null);
      var1.setlocal("com_call_function", var4);
      var3 = null;
      var1.setline(1253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_argument$117, (PyObject)null);
      var1.setlocal("com_argument", var4);
      var3 = null;
      var1.setline(1270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_subscriptlist$118, (PyObject)null);
      var1.setlocal("com_subscriptlist", var4);
      var3 = null;
      var1.setline(1290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_subscript$119, (PyObject)null);
      var1.setlocal("com_subscript", var4);
      var3 = null;
      var1.setline(1300);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_sliceobj$120, (PyObject)null);
      var1.setlocal("com_sliceobj", var4);
      var3 = null;
      var1.setline(1336);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, com_slice$121, (PyObject)null);
      var1.setlocal("com_slice", var4);
      var3 = null;
      var1.setline(1350);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get_docstring$122, (PyObject)null);
      var1.setlocal("get_docstring", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_dispatch", var3);
      var3 = null;
      var1.setline(108);
      PyObject var7 = var1.getglobal("symbol").__getattr__("sym_name").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(108);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(111);
            var7 = var1.getlocal(0).__getattr__("com_NEWLINE");
            var1.getlocal(0).__getattr__("_dispatch").__setitem__(var1.getglobal("token").__getattr__("NEWLINE"), var7);
            var3 = null;
            var1.setline(112);
            var3 = new PyDictionary(new PyObject[]{var1.getglobal("token").__getattr__("LPAR"), var1.getlocal(0).__getattr__("atom_lpar"), var1.getglobal("token").__getattr__("LSQB"), var1.getlocal(0).__getattr__("atom_lsqb"), var1.getglobal("token").__getattr__("LBRACE"), var1.getlocal(0).__getattr__("atom_lbrace"), var1.getglobal("token").__getattr__("BACKQUOTE"), var1.getlocal(0).__getattr__("atom_backquote"), var1.getglobal("token").__getattr__("NUMBER"), var1.getlocal(0).__getattr__("atom_number"), var1.getglobal("token").__getattr__("STRING"), var1.getlocal(0).__getattr__("atom_string"), var1.getglobal("token").__getattr__("NAME"), var1.getlocal(0).__getattr__("atom_name")});
            var1.getlocal(0).__setattr__((String)"_atom_dispatch", var3);
            var3 = null;
            var1.setline(120);
            var7 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("encoding", var7);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(109);
         if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)).__nonzero__()) {
            var1.setline(110);
            PyObject var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.getlocal(0).__getattr__("_dispatch").__setitem__(var1.getlocal(1), var8);
            var5 = null;
         }
      }
   }

   public PyObject transform$9(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Transform an AST into a modified parse tree.");
      var1.setline(124);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list"));
      }

      PyObject var5;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(125);
         var10000 = var1.getglobal("parser").__getattr__("ast2tuple");
         PyObject[] var3 = new PyObject[]{var1.getlocal(1), Py.newInteger(1)};
         String[] var4 = new String[]{"line_info"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var5 = var10000;
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(126);
      var5 = var1.getlocal(0).__getattr__("compile_node").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject parsesuite$10(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyString.fromInterned("Return a modified parse tree for the given suite text.");
      var1.setline(130);
      PyObject var3 = var1.getlocal(0).__getattr__("transform").__call__(var2, var1.getglobal("parser").__getattr__("suite").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseexpr$11(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Return a modified parse tree for the given expression text.");
      var1.setline(134);
      PyObject var3 = var1.getlocal(0).__getattr__("transform").__call__(var2, var1.getglobal("parser").__getattr__("expr").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parsefile$12(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("Return a modified parse tree for the contents of the given file.");
      var1.setline(138);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(139);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(140);
      var3 = var1.getlocal(0).__getattr__("parsesuite").__call__(var2, var1.getlocal(1).__getattr__("read").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compile_node$13(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(151);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("encoding_decl"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(2));
         var1.getlocal(0).__setattr__("encoding", var3);
         var3 = null;
         var1.setline(153);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(154);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(156);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(var1.getglobal("symbol").__getattr__("single_input"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(157);
         var3 = var1.getlocal(0).__getattr__("single_input").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(158);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(var1.getglobal("symbol").__getattr__("file_input"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(159);
            var3 = var1.getlocal(0).__getattr__("file_input").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(160);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(var1.getglobal("symbol").__getattr__("eval_input"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(161);
               var3 = var1.getlocal(0).__getattr__("eval_input").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(162);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(var1.getglobal("symbol").__getattr__("lambdef"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(163);
                  var3 = var1.getlocal(0).__getattr__("lambdef").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(164);
                  var4 = var1.getlocal(2);
                  var10000 = var4._eq(var1.getglobal("symbol").__getattr__("funcdef"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(165);
                     var3 = var1.getlocal(0).__getattr__("funcdef").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(166);
                     var4 = var1.getlocal(2);
                     var10000 = var4._eq(var1.getglobal("symbol").__getattr__("classdef"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(167);
                        var3 = var1.getlocal(0).__getattr__("classdef").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(169);
                        throw Py.makeException(var1.getglobal("WalkerError"), new PyTuple(new PyObject[]{PyString.fromInterned("unexpected node type"), var1.getlocal(2)}));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject single_input$14(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(176);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(var1.getglobal("token").__getattr__("NEWLINE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(177);
         var3 = var1.getlocal(0).__getattr__("com_stmt").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(179);
         var3 = var1.getglobal("Pass").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject file_input$15(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(1), var1.getglobal("symbol").__getattr__("file_input"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyInteger var6;
      if (var10000.__nonzero__()) {
         var1.setline(184);
         var6 = Py.newInteger(1);
         var1.setlocal(3, var6);
         var3 = null;
      } else {
         var1.setline(186);
         var6 = Py.newInteger(0);
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(187);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(188);
      var3 = var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(188);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(191);
            var3 = var1.getglobal("Module").__call__(var2, var1.getlocal(2), var1.getglobal("Stmt").__call__(var2, var1.getlocal(4)));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(189);
         PyObject var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var10000 = var5._ne(var1.getglobal("token").__getattr__("ENDMARKER"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
            var10000 = var5._ne(var1.getglobal("token").__getattr__("NEWLINE"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(190);
            var1.getlocal(0).__getattr__("com_append_stmt").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         }
      }
   }

   public PyObject eval_input$16(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyObject var3 = var1.getglobal("Expression").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decorator_name$17(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(200);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._ge(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2)._mod(Py.newInteger(2));
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(202);
      var3 = var1.getlocal(0).__getattr__("atom_name").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(203);
      PyInteger var4 = Py.newInteger(1);
      var1.setlocal(4, var4);
      var3 = null;

      while(true) {
         var1.setline(204);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(210);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(205);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(var1.getlocal(4)).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(var1.getglobal("token").__getattr__("DOT"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(206);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1))).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(207);
         var3 = var1.getglobal("Getattr").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1))).__getitem__(Py.newInteger(1)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(208);
         var3 = var1.getlocal(4);
         var3 = var3._iadd(Py.newInteger(2));
         var1.setlocal(4, var3);
      }
   }

   public PyObject decorator$18(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._in(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(5), Py.newInteger(6)}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(215);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("AT"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(216);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("NEWLINE"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(218);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("dotted_name"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(219);
      var3 = var1.getlocal(0).__getattr__("decorator_name").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(221);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._gt(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(222);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(var1.getglobal("token").__getattr__("LPAR"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(223);
         var3 = var1.getlocal(0).__getattr__("com_call_function").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(3)));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(225);
         var3 = var1.getlocal(2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(227);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decorators$19(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(232);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(232);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(235);
            var6 = var1.getglobal("Decorators").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(233);
         if (var1.getglobal("__debug__").__nonzero__()) {
            PyObject var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            PyObject var10000 = var5._eq(var1.getglobal("symbol").__getattr__("decorator"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(234);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("decorator").__call__(var2, var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)));
      }
   }

   public PyObject funcdef$20(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(6));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(243);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(var1.getglobal("symbol").__getattr__("decorators"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(244);
         var3 = var1.getlocal(0).__getattr__("decorators").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(246);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var3._eq(Py.newInteger(5));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(247);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(249);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-4)).__getitem__(Py.newInteger(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(250);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-4)).__getitem__(Py.newInteger(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-3)).__getitem__(Py.newInteger(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(253);
      var3 = var1.getlocal(5).__getitem__(Py.newInteger(0));
      var10000 = var3._eq(var1.getglobal("symbol").__getattr__("varargslist"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(254);
         var3 = var1.getlocal(0).__getattr__("com_arglist").__call__(var2, var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(256);
         PyTuple var7 = new PyTuple(Py.EmptyObjects);
         var1.setlocal(6, var7);
         var1.setlocal(7, var7);
         var1.setline(257);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(8, var8);
         var3 = null;
      }

      var1.setline(258);
      var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(263);
      var3 = var1.getlocal(9);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(264);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("Stmt")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(265);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(10).__getattr__("nodes").__getitem__(Py.newInteger(0)), var1.getglobal("Discard")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(266);
         var1.getlocal(10).__getattr__("nodes").__delitem__((PyObject)Py.newInteger(0));
      }

      var1.setline(267);
      var10000 = var1.getglobal("Function");
      PyObject[] var9 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(3)};
      String[] var6 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var9, var6);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lambdef$21(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("varargslist"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(273);
         var3 = var1.getlocal(0).__getattr__("com_arglist").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(275);
         PyTuple var6 = new PyTuple(Py.EmptyObjects);
         var1.setlocal(2, var6);
         var1.setlocal(3, var6);
         var1.setline(276);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(4, var8);
         var3 = null;
      }

      var1.setline(279);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(281);
      var10000 = var1.getglobal("Lambda");
      PyObject[] var9 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
      String[] var7 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var9, var7);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject classdef$22(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(288);
      var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(289);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("COLON"));
      var3 = null;
      PyList var5;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var5);
         var3 = null;
      } else {
         var1.setline(291);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(3)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("RPAR"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(292);
            var5 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var5);
            var3 = null;
         } else {
            var1.setline(294);
            var3 = var1.getlocal(0).__getattr__("com_bases").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
            var1.setlocal(4, var3);
            var3 = null;
         }
      }

      var1.setline(297);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(299);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(300);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Stmt")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(301);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(5).__getattr__("nodes").__getitem__(Py.newInteger(0)), var1.getglobal("Discard")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(302);
         var1.getlocal(5).__getattr__("nodes").__delitem__((PyObject)Py.newInteger(0));
      }

      var1.setline(304);
      var10000 = var1.getglobal("Class");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(3), var1.getlocal(5), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject stmt$23(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3 = var1.getlocal(0).__getattr__("com_stmt").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject simple_stmt$24(PyFrame var1, ThreadState var2) {
      var1.setline(315);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(316);
      PyObject var5 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(316);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(318);
            var5 = var1.getglobal("Stmt").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(317);
         var1.getlocal(0).__getattr__("com_append_stmt").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getitem__(var1.getlocal(3)));
      }
   }

   public PyObject parameters$25(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject varargslist$26(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject fpdef$27(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject fplist$28(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject dotted_name$29(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject comp_op$30(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject trailer$31(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject sliceop$32(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject argument$33(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      throw Py.makeException(var1.getglobal("WalkerError"));
   }

   public PyObject expr_stmt$34(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(355);
      var3 = var1.getlocal(0).__getattr__("lookup_node").__call__(var2, var1.getlocal(2)).__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(356);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(357);
         var10000 = var1.getglobal("Discard");
         PyObject[] var8 = new PyObject[]{var1.getlocal(3), var1.getlocal(3).__getattr__("lineno")};
         String[] var10 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var8, var10);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(358);
         PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var10000 = var4._eq(var1.getglobal("token").__getattr__("EQUAL"));
         var4 = null;
         String[] var7;
         PyObject[] var9;
         if (!var10000.__nonzero__()) {
            var1.setline(364);
            var4 = var1.getlocal(0).__getattr__("com_augassign").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(365);
            var4 = var1.getlocal(0).__getattr__("com_augassign_op").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(366);
            var10000 = var1.getglobal("AugAssign");
            var9 = new PyObject[]{var1.getlocal(6), var1.getlocal(7).__getitem__(Py.newInteger(1)), var1.getlocal(3), var1.getlocal(7).__getitem__(Py.newInteger(2))};
            var7 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var9, var7);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(359);
            PyList var6 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var6);
            var4 = null;
            var1.setline(360);
            var4 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(2)), (PyObject)Py.newInteger(2)).__iter__();

            while(true) {
               var1.setline(360);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(362);
                  var10000 = var1.getglobal("Assign");
                  var9 = new PyObject[]{var1.getlocal(4), var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
                  var7 = new String[]{"lineno"};
                  var10000 = var10000.__call__(var2, var9, var7);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(5, var5);
               var1.setline(361);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)), var1.getglobal("OP_ASSIGN")));
            }
         }
      }
   }

   public PyObject print_stmt$35(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(372);
      PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var6._eq(Py.newInteger(1));
      var3 = null;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var1.setline(373);
         var7 = Py.newInteger(1);
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(374);
         var6 = var1.getglobal("None");
         var1.setlocal(4, var6);
         var3 = null;
      } else {
         var1.setline(375);
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var10000 = var6._eq(var1.getglobal("token").__getattr__("RIGHTSHIFT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(376);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var6._eq(Py.newInteger(3));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var6 = var1.getlocal(1).__getitem__(Py.newInteger(3)).__getitem__(Py.newInteger(0));
                  var10000 = var6._eq(var1.getglobal("token").__getattr__("COMMA"));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(378);
            var6 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)));
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(379);
            var7 = Py.newInteger(4);
            var1.setlocal(3, var7);
            var3 = null;
         } else {
            var1.setline(381);
            var6 = var1.getglobal("None");
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(382);
            var7 = Py.newInteger(1);
            var1.setlocal(3, var7);
            var3 = null;
         }
      }

      var1.setline(383);
      var6 = var1.getglobal("range").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(383);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(385);
            var6 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(var1.getglobal("token").__getattr__("COMMA"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(386);
               var10000 = var1.getglobal("Print");
               PyObject[] var10 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
               String[] var9 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var10, var9);
               var3 = null;
               var6 = var10000;
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(387);
               var10000 = var1.getglobal("Printnl");
               PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
               String[] var5 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var8, var5);
               var4 = null;
               var6 = var10000;
               var1.f_lasti = -1;
               return var6;
            }
         }

         var1.setlocal(5, var4);
         var1.setline(384);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5))));
      }
   }

   public PyObject del_stmt$36(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyObject var3 = var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getglobal("OP_DELETE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pass_stmt$37(PyFrame var1, ThreadState var2) {
      var1.setline(393);
      PyObject var10000 = var1.getglobal("Pass");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject break_stmt$38(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyObject var10000 = var1.getglobal("Break");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject continue_stmt$39(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyObject var10000 = var1.getglobal("Continue");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject return_stmt$40(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._lt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(404);
         var10000 = var1.getglobal("Return");
         PyObject[] var6 = new PyObject[]{var1.getglobal("Const").__call__(var2, var1.getglobal("None")), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var7 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var6, var7);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(405);
         var10000 = var1.getglobal("Return");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var5 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject yield_stmt$41(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(409);
      PyObject var10000 = var1.getglobal("Discard");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(2).__getattr__("lineno")};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject yield_expr$42(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(413);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(415);
         var3 = var1.getglobal("Const").__call__(var2, var1.getglobal("None"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(416);
      var10000 = var1.getglobal("Yield");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject raise_stmt$43(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(421);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(5)));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(423);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(424);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._gt(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(425);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(427);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(428);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(429);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(431);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(432);
      var10000 = var1.getglobal("Raise");
      PyObject[] var5 = new PyObject[]{var1.getlocal(4), var1.getlocal(3), var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject import_stmt$44(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(437);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject import_name$45(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyObject var10000 = var1.getglobal("Import");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("com_dotted_as_names").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject import_from$46(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyObject var3;
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
         var10000 = var3._eq(PyString.fromInterned("from"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(448);
      PyInteger var6 = Py.newInteger(1);
      var1.setlocal(2, var6);
      var3 = null;

      while(true) {
         var1.setline(449);
         var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(1));
         var10000 = var3._eq(PyString.fromInterned("."));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(451);
            var3 = var1.getlocal(2)._sub(Py.newInteger(1));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(452);
            var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(var1.getglobal("symbol").__getattr__("dotted_name"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(453);
               var3 = var1.getlocal(0).__getattr__("com_dotted_name").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(2)));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(454);
               var3 = var1.getlocal(2);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(2, var3);
            } else {
               var1.setline(456);
               PyString var9 = PyString.fromInterned("");
               var1.setlocal(4, var9);
               var3 = null;
            }

            var1.setline(457);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(1));
               var10000 = var3._eq(PyString.fromInterned("import"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(458);
            var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)._add(Py.newInteger(1))).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(var1.getglobal("token").__getattr__("STAR"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(459);
               var10000 = var1.getglobal("From");
               PyObject[] var10 = new PyObject[]{var1.getlocal(4), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("*"), var1.getglobal("None")})}), var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
               String[] var8 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var10, var8);
               var3 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(462);
               var10000 = var1.getlocal(1);
               PyObject var10001 = var1.getlocal(2)._add(Py.newInteger(1));
               PyObject var4 = var1.getlocal(1).__getitem__(var1.getlocal(2)._add(Py.newInteger(1))).__getitem__(Py.newInteger(0));
               PyObject var10002 = var4._eq(var1.getglobal("token").__getattr__("LPAR"));
               var4 = null;
               var4 = var10000.__getitem__(var10001._add(var10002));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(463);
               var10000 = var1.getglobal("From");
               PyObject[] var7 = new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("com_import_as_names").__call__(var2, var1.getlocal(5)), var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
               String[] var5 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var7, var5);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(450);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(2, var3);
      }
   }

   public PyObject global_stmt$47(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(469);
      PyObject var5 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(469);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(471);
            PyObject var10000 = var1.getglobal("Global");
            PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
            String[] var7 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var7);
            var3 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(470);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)).__getitem__(Py.newInteger(1)));
      }
   }

   public PyObject exec_stmt$48(PyFrame var1, ThreadState var2) {
      var1.setline(475);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(476);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ge(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(477);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(478);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._ge(Py.newInteger(6));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(479);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(5)));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(481);
            var3 = var1.getglobal("None");
            var1.setlocal(4, var3);
            var3 = null;
         }
      } else {
         var1.setline(483);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var1.setlocal(4, var3);
      }

      var1.setline(485);
      var10000 = var1.getglobal("Exec");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject assert_stmt$49(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(490);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(491);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(493);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(494);
      var10000 = var1.getglobal("Assert");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject if_stmt$50(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(499);
      PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(3)), (PyObject)Py.newInteger(4)).__iter__();

      while(true) {
         var1.setline(499);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(504);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._mod(Py.newInteger(4));
            PyObject var10000 = var6._eq(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(505);
               var6 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
               var1.setlocal(6, var6);
               var3 = null;
            } else {
               var1.setline(508);
               var6 = var1.getglobal("None");
               var1.setlocal(6, var6);
               var3 = null;
            }

            var1.setline(509);
            var10000 = var1.getglobal("If");
            PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(6), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
            String[] var7 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var8, var7);
            var3 = null;
            var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(500);
         PyObject var5 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)._add(Py.newInteger(1))));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(501);
         var5 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)._add(Py.newInteger(3))));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(502);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
      }
   }

   public PyObject while_stmt$51(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(515);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(517);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(518);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(6)));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(520);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(522);
      var10000 = var1.getglobal("While");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject for_stmt$52(PyFrame var1, ThreadState var2) {
      var1.setline(527);
      PyObject var3 = var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getglobal("OP_ASSIGN"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(528);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(529);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(5)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(531);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(8));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(532);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(8)));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(534);
         var3 = var1.getglobal("None");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(536);
      var10000 = var1.getglobal("For");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject try_stmt$53(PyFrame var1, ThreadState var2) {
      var1.setline(540);
      PyObject var3 = var1.getlocal(0).__getattr__("com_try_except_finally").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject with_stmt$54(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyObject var3 = var1.getlocal(0).__getattr__("com_with").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject with_var$55(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      PyObject var3 = var1.getlocal(0).__getattr__("com_with_var").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject suite$56(PyFrame var1, ThreadState var2) {
      var1.setline(550);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(551);
         var3 = var1.getlocal(0).__getattr__("com_stmt").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(553);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(554);
         PyObject var7 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(554);
            PyObject var5 = var7.__iternext__();
            if (var5 == null) {
               var1.setline(557);
               var3 = var1.getglobal("Stmt").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(555);
            PyObject var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(var1.getglobal("symbol").__getattr__("stmt"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(556);
               var1.getlocal(0).__getattr__("com_append_stmt").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            }
         }
      }
   }

   public PyObject testlist$57(PyFrame var1, ThreadState var2) {
      var1.setline(568);
      PyObject var3 = var1.getlocal(0).__getattr__("com_binary").__call__(var2, var1.getglobal("Tuple"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testlist_gexp$58(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("gen_for"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(576);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(577);
         var3 = var1.getlocal(0).__getattr__("com_generator_expression").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(578);
         var3 = var1.getlocal(0).__getattr__("testlist").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject test$59(PyFrame var1, ThreadState var2) {
      var1.setline(582);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("lambdef"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(583);
         var3 = var1.getlocal(0).__getattr__("lambdef").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(584);
         PyObject var4 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(585);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var4._gt(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(586);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var4._eq(Py.newInteger(5));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(587);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
               var10000 = var4._eq(PyString.fromInterned("if"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(588);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(3)).__getitem__(Py.newInteger(1));
               var10000 = var4._eq(PyString.fromInterned("else"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(589);
            var4 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(590);
            var4 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(4)));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(591);
            var10000 = var1.getglobal("IfExp");
            PyObject[] var6 = new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            String[] var5 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(592);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject or_test$60(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("lambdef"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(597);
         var3 = var1.getlocal(0).__getattr__("lambdef").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(598);
         var3 = var1.getlocal(0).__getattr__("com_binary").__call__(var2, var1.getglobal("Or"), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject and_test$61(PyFrame var1, ThreadState var2) {
      var1.setline(603);
      PyObject var3 = var1.getlocal(0).__getattr__("com_binary").__call__(var2, var1.getglobal("And"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject not_test$62(PyFrame var1, ThreadState var2) {
      var1.setline(607);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(608);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(609);
         var10000 = var1.getglobal("Not");
         PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var4 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(610);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject comparison$63(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(615);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(616);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(618);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(619);
         PyObject var7 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(619);
            PyObject var5 = var7.__iternext__();
            if (var5 == null) {
               var1.setline(643);
               var10000 = var1.getglobal("Compare");
               PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(8)};
               String[] var9 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var8, var9);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var5);
            var1.setline(620);
            PyObject var6 = var1.getlocal(1).__getitem__(var1.getlocal(4)._sub(Py.newInteger(1)));
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(624);
            var6 = var1.getlocal(5).__getitem__(Py.newInteger(1));
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(625);
            var6 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(var1.getglobal("token").__getattr__("NAME"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(626);
               var6 = var1.getlocal(6).__getitem__(Py.newInteger(1));
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(627);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
               var10000 = var6._eq(Py.newInteger(3));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(628);
                  var6 = var1.getlocal(7);
                  var10000 = var6._eq(PyString.fromInterned("not"));
                  var6 = null;
                  PyString var10;
                  if (var10000.__nonzero__()) {
                     var1.setline(629);
                     var10 = PyString.fromInterned("not in");
                     var1.setlocal(7, var10);
                     var6 = null;
                  } else {
                     var1.setline(631);
                     var10 = PyString.fromInterned("is not");
                     var1.setlocal(7, var10);
                     var6 = null;
                  }
               }
            } else {
               var1.setline(633);
               var6 = var1.getglobal("_cmp_types").__getitem__(var1.getlocal(6).__getitem__(Py.newInteger(0)));
               var1.setlocal(7, var6);
               var6 = null;
            }

            var1.setline(635);
            var6 = var1.getlocal(5).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2));
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(636);
            var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(4)))})));
         }
      }
   }

   public PyObject expr$64(PyFrame var1, ThreadState var2) {
      var1.setline(647);
      PyObject var3 = var1.getlocal(0).__getattr__("com_binary").__call__(var2, var1.getglobal("Bitor"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject xor_expr$65(PyFrame var1, ThreadState var2) {
      var1.setline(651);
      PyObject var3 = var1.getlocal(0).__getattr__("com_binary").__call__(var2, var1.getglobal("Bitxor"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject and_expr$66(PyFrame var1, ThreadState var2) {
      var1.setline(655);
      PyObject var3 = var1.getlocal(0).__getattr__("com_binary").__call__(var2, var1.getglobal("Bitand"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shift_expr$67(PyFrame var1, ThreadState var2) {
      var1.setline(659);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(660);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(660);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(668);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(661);
         PyObject var5 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(662);
         var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._eq(var1.getglobal("token").__getattr__("LEFTSHIFT"));
         var5 = null;
         String[] var6;
         PyObject[] var7;
         if (var10000.__nonzero__()) {
            var1.setline(663);
            var10000 = var1.getglobal("LeftShift");
            var7 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)}), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var6 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var6);
            var5 = null;
            var5 = var10000;
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(664);
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("token").__getattr__("RIGHTSHIFT"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(667);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unexpected token: %s")._mod(var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0))));
            }

            var1.setline(665);
            var10000 = var1.getglobal("RightShift");
            var7 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)}), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var6 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var6);
            var5 = null;
            var5 = var10000;
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject arith_expr$68(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(672);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(672);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(680);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(673);
         PyObject var5 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(674);
         var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._eq(var1.getglobal("token").__getattr__("PLUS"));
         var5 = null;
         String[] var6;
         PyObject[] var7;
         if (var10000.__nonzero__()) {
            var1.setline(675);
            var10000 = var1.getglobal("Add");
            var7 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)}), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var6 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var6);
            var5 = null;
            var5 = var10000;
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(676);
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("token").__getattr__("MINUS"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(679);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unexpected token: %s")._mod(var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0))));
            }

            var1.setline(677);
            var10000 = var1.getglobal("Sub");
            var7 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)}), var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var6 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var6);
            var5 = null;
            var5 = var10000;
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject term$69(PyFrame var1, ThreadState var2) {
      var1.setline(683);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(684);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(684);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(698);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(685);
         PyObject var5 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(686);
         var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(687);
         var5 = var1.getlocal(5);
         PyObject var10000 = var5._eq(var1.getglobal("token").__getattr__("STAR"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(688);
            var5 = var1.getglobal("Mul").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(689);
            var5 = var1.getlocal(5);
            var10000 = var5._eq(var1.getglobal("token").__getattr__("SLASH"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(690);
               var5 = var1.getglobal("Div").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
               var1.setlocal(2, var5);
               var5 = null;
            } else {
               var1.setline(691);
               var5 = var1.getlocal(5);
               var10000 = var5._eq(var1.getglobal("token").__getattr__("PERCENT"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(692);
                  var5 = var1.getglobal("Mod").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
                  var1.setlocal(2, var5);
                  var5 = null;
               } else {
                  var1.setline(693);
                  var5 = var1.getlocal(5);
                  var10000 = var5._eq(var1.getglobal("token").__getattr__("DOUBLESLASH"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(696);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unexpected token: %s")._mod(var1.getlocal(5)));
                  }

                  var1.setline(694);
                  var5 = var1.getglobal("FloorDiv").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
                  var1.setlocal(2, var5);
                  var5 = null;
               }
            }
         }

         var1.setline(697);
         var5 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2));
         var1.getlocal(2).__setattr__("lineno", var5);
         var5 = null;
      }
   }

   public PyObject factor$70(PyFrame var1, ThreadState var2) {
      var1.setline(701);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(702);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(703);
      var3 = var1.getlocal(0).__getattr__("lookup_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1))).__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(705);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("PLUS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(706);
         var10000 = var1.getglobal("UnaryAdd");
         PyObject[] var7 = new PyObject[]{var1.getlocal(4), var1.getlocal(2).__getitem__(Py.newInteger(2))};
         String[] var8 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var7, var8);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(707);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._eq(var1.getglobal("token").__getattr__("MINUS"));
         var4 = null;
         String[] var5;
         PyObject[] var6;
         if (var10000.__nonzero__()) {
            var1.setline(708);
            var10000 = var1.getglobal("UnarySub");
            var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(2).__getitem__(Py.newInteger(2))};
            var5 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(709);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(var1.getglobal("token").__getattr__("TILDE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(710);
               var10000 = var1.getglobal("Invert");
               var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(2).__getitem__(Py.newInteger(2))};
               var5 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var6, var5);
               var4 = null;
               var4 = var10000;
               var1.setlocal(4, var4);
               var4 = null;
            }

            var1.setline(711);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject power$71(PyFrame var1, ThreadState var2) {
      var1.setline(715);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(716);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      while(true) {
         var1.setline(716);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(724);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(717);
         var5 = var1.getlocal(1).__getitem__(var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(718);
         var5 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(719);
            var10000 = var1.getglobal("Power");
            PyObject[] var7 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)._add(Py.newInteger(1))))}), var1.getlocal(4).__getitem__(Py.newInteger(2))};
            String[] var8 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var8);
            var5 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(722);
         PyObject var6 = var1.getlocal(0).__getattr__("com_apply_trailer").__call__(var2, var1.getlocal(2), var1.getlocal(4));
         var1.setlocal(2, var6);
         var6 = null;
      }
   }

   public PyObject atom$72(PyFrame var1, ThreadState var2) {
      var1.setline(727);
      PyObject var3 = var1.getlocal(0).__getattr__("_atom_dispatch").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0))).__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject atom_lpar$73(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("RPAR"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(731);
         var10000 = var1.getglobal("Tuple");
         PyObject[] var5 = new PyObject[]{new PyTuple(Py.EmptyObjects), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var4 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(732);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject atom_lsqb$74(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("RSQB"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(736);
         var10000 = var1.getglobal("List");
         PyObject[] var5 = new PyObject[]{new PyTuple(Py.EmptyObjects), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var4 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(737);
         var3 = var1.getlocal(0).__getattr__("com_list_constructor").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject atom_lbrace$75(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("RBRACE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(741);
         var10000 = var1.getglobal("Dict");
         PyObject[] var5 = new PyObject[]{new PyTuple(Py.EmptyObjects), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var4 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(742);
         var3 = var1.getlocal(0).__getattr__("com_dictmaker").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject atom_backquote$76(PyFrame var1, ThreadState var2) {
      var1.setline(745);
      PyObject var3 = var1.getglobal("Backquote").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject atom_number$77(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      PyObject var3 = var1.getglobal("eval").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(750);
      PyObject var10000 = var1.getglobal("Const");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode_literal$78(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("encoding").__nonzero__()) {
         var1.setline(757);
         var3 = var1.getlocal(0).__getattr__("encoding");
         PyObject var10000 = var3._notin(new PyList(new PyObject[]{PyString.fromInterned("utf-8"), PyString.fromInterned("iso-8859-1")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(758);
            var3 = var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("utf-8")).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("encoding"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(759);
         var3 = var1.getglobal("eval").__call__(var2, PyString.fromInterned("# coding: %s\n%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1)})));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(761);
         var3 = var1.getglobal("eval").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject atom_string$79(PyFrame var1, ThreadState var2) {
      var1.setline(764);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(765);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(765);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(767);
            PyObject var10000 = var1.getglobal("Const");
            PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
            String[] var8 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var8);
            var3 = null;
            var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(766);
         PyObject var5 = var1.getlocal(2);
         var5 = var5._iadd(var1.getlocal(0).__getattr__("decode_literal").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))));
         var1.setlocal(2, var5);
      }
   }

   public PyObject atom_name$80(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyObject var10000 = var1.getglobal("Name");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject lookup_node$81(PyFrame var1, ThreadState var2) {
      var1.setline(786);
      PyObject var3 = var1.getlocal(0).__getattr__("_dispatch").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_node$82(PyFrame var1, ThreadState var2) {
      var1.setline(793);
      PyObject var3 = var1.getlocal(0).__getattr__("_dispatch").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0))).__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_NEWLINE$83(PyFrame var1, ThreadState var2) {
      var1.setline(799);
      PyObject var3 = var1.getglobal("Discard").__call__(var2, var1.getglobal("Const").__call__(var2, var1.getglobal("None")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_arglist$84(PyFrame var1, ThreadState var2) {
      var1.setline(807);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(808);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(809);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(811);
      var4 = Py.newInteger(0);
      var1.setlocal(5, var4);
      var3 = null;

      while(true) {
         var1.setline(812);
         PyObject var5 = var1.getlocal(5);
         PyObject var10000 = var5._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(813);
         var5 = var1.getlocal(1).__getitem__(var1.getlocal(5));
         var1.setlocal(6, var5);
         var3 = null;
         var1.setline(814);
         var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
         var10000 = var5._eq(var1.getglobal("token").__getattr__("STAR"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(815);
            var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("token").__getattr__("STAR"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(816);
               var5 = var1.getlocal(1).__getitem__(var1.getlocal(5)._add(Py.newInteger(1)));
               var1.setlocal(6, var5);
               var3 = null;
               var1.setline(817);
               var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
               var10000 = var5._eq(var1.getglobal("token").__getattr__("NAME"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(818);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(1)));
                  var1.setline(819);
                  var5 = var1.getlocal(4)._or(var1.getglobal("CO_VARARGS"));
                  var1.setlocal(4, var5);
                  var3 = null;
                  var1.setline(820);
                  var5 = var1.getlocal(5)._add(Py.newInteger(3));
                  var1.setlocal(5, var5);
                  var3 = null;
               }
            }

            var1.setline(822);
            var5 = var1.getlocal(5);
            var10000 = var5._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(824);
               var5 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getitem__(Py.newInteger(0));
               var1.setlocal(7, var5);
               var3 = null;
               var1.setline(825);
               var5 = var1.getlocal(7);
               var10000 = var5._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(828);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unexpected token: %s")._mod(var1.getlocal(7)));
               }

               var1.setline(826);
               var5 = var1.getlocal(1).__getitem__(var1.getlocal(5)._add(Py.newInteger(1)));
               var1.setlocal(6, var5);
               var3 = null;
               var1.setline(829);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(1)));
               var1.setline(830);
               var5 = var1.getlocal(4)._or(var1.getglobal("CO_VARKEYWORDS"));
               var1.setlocal(4, var5);
               var3 = null;
            }
            break;
         }

         var1.setline(835);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_fpdef").__call__(var2, var1.getlocal(6)));
         var1.setline(837);
         var5 = var1.getlocal(5)._add(Py.newInteger(1));
         var1.setlocal(5, var5);
         var3 = null;
         var1.setline(838);
         var5 = var1.getlocal(5);
         var10000 = var5._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("token").__getattr__("EQUAL"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(839);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)._add(Py.newInteger(1)))));
            var1.setline(840);
            var5 = var1.getlocal(5)._add(Py.newInteger(2));
            var1.setlocal(5, var5);
            var3 = null;
         } else {
            var1.setline(841);
            if (var1.getglobal("len").__call__(var2, var1.getlocal(3)).__nonzero__()) {
               var1.setline(844);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("non-default argument follows default argument"));
            }
         }

         var1.setline(847);
         var5 = var1.getlocal(5)._add(Py.newInteger(1));
         var1.setlocal(5, var5);
         var3 = null;
      }

      var1.setline(849);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject com_fpdef$85(PyFrame var1, ThreadState var2) {
      var1.setline(853);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("LPAR"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(854);
         var3 = var1.getlocal(0).__getattr__("com_fplist").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(855);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject com_fplist$86(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(860);
         var3 = var1.getlocal(0).__getattr__("com_fpdef").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(861);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(862);
         PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(862);
            PyObject var5 = var6.__iternext__();
            if (var5 == null) {
               var1.setline(864);
               var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(863);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_fpdef").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
         }
      }
   }

   public PyObject com_dotted_name$87(PyFrame var1, ThreadState var2) {
      var1.setline(868);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(869);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(869);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(872);
            var6 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(870);
         PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var5._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects))));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(Py.newInteger(1));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(871);
            var5 = var1.getlocal(2)._add(var1.getlocal(3).__getitem__(Py.newInteger(1)))._add(PyString.fromInterned("."));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject com_dotted_as_name$88(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("dotted_as_name"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(876);
      var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(877);
      var3 = var1.getlocal(0).__getattr__("com_dotted_name").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(878);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(879);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(880);
         PyObject var4;
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
            var10000 = var4._eq(PyString.fromInterned("as"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(881);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(var1.getglobal("token").__getattr__("NAME"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(882);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(1))});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject com_dotted_as_names$89(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("dotted_as_names"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(886);
      var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(887);
      PyList var5 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("com_dotted_as_name").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)))});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(888);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(888);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(890);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(889);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_dotted_as_name").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
      }
   }

   public PyObject com_import_as_name$90(PyFrame var1, ThreadState var2) {
      var1.setline(893);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("import_as_name"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(894);
      var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(895);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(896);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(897);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(898);
         PyObject var4;
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
            var10000 = var4._eq(PyString.fromInterned("as"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1));
            }
         }

         var1.setline(899);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(var1.getglobal("token").__getattr__("NAME"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(900);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(1))});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject com_import_as_names$91(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("import_as_names"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(904);
      var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(905);
      PyList var5 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("com_import_as_name").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)))});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(906);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(906);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(908);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(907);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_import_as_name").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
      }
   }

   public PyObject com_bases$92(PyFrame var1, ThreadState var2) {
      var1.setline(911);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(912);
      PyObject var5 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(912);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(914);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(913);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
      }
   }

   public PyObject com_try_except_finally$93(PyFrame var1, ThreadState var2) {
      var1.setline(921);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(3)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(923);
         var10000 = var1.getglobal("TryFinally");
         PyObject[] var7 = new PyObject[]{var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2))), var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(5))), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
         String[] var11 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var7, var11);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(928);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(929);
         PyObject var8 = var1.getglobal("None");
         var1.setlocal(3, var8);
         var4 = null;
         var1.setline(930);
         var8 = var1.getglobal("None");
         var1.setlocal(4, var8);
         var4 = null;
         var1.setline(931);
         var8 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(3), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(3)).__iter__();

         while(true) {
            var1.setline(931);
            PyObject var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(950);
               var10000 = var1.getglobal("TryExcept");
               PyObject[] var10 = new PyObject[]{var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2))), var1.getlocal(2), var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
               String[] var9 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var10, var9);
               var4 = null;
               var8 = var10000;
               var1.setlocal(9, var8);
               var4 = null;
               var1.setline(952);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(953);
                  var10000 = var1.getglobal("TryFinally");
                  var10 = new PyObject[]{var1.getlocal(9), var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
                  var9 = new String[]{"lineno"};
                  var10000 = var10000.__call__(var2, var10, var9);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(955);
               var3 = var1.getlocal(9);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(5, var5);
            var1.setline(932);
            PyObject var6 = var1.getlocal(1).__getitem__(var1.getlocal(5));
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(933);
            var6 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(var1.getglobal("symbol").__getattr__("except_clause"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(935);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var10000 = var6._gt(Py.newInteger(2));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(936);
                  var6 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(2)));
                  var1.setlocal(7, var6);
                  var6 = null;
                  var1.setline(937);
                  var6 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
                  var10000 = var6._gt(Py.newInteger(4));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(938);
                     var6 = var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(4)), var1.getglobal("OP_ASSIGN"));
                     var1.setlocal(8, var6);
                     var6 = null;
                  } else {
                     var1.setline(940);
                     var6 = var1.getglobal("None");
                     var1.setlocal(8, var6);
                     var6 = null;
                  }
               } else {
                  var1.setline(942);
                  var6 = var1.getglobal("None");
                  var1.setlocal(7, var6);
                  var1.setlocal(8, var6);
               }

               var1.setline(943);
               var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)._add(Py.newInteger(2))))})));
            }

            var1.setline(945);
            var6 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(var1.getglobal("token").__getattr__("NAME"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(946);
               var6 = var1.getlocal(6).__getitem__(Py.newInteger(1));
               var10000 = var6._eq(PyString.fromInterned("else"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(947);
                  var6 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)._add(Py.newInteger(2))));
                  var1.setlocal(3, var6);
                  var6 = null;
               } else {
                  var1.setline(948);
                  var6 = var1.getlocal(6).__getitem__(Py.newInteger(1));
                  var10000 = var6._eq(PyString.fromInterned("finally"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(949);
                     var6 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)._add(Py.newInteger(2))));
                     var1.setlocal(4, var6);
                     var6 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject com_with$94(PyFrame var1, ThreadState var2) {
      var1.setline(959);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(960);
      var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(961);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("COLON"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(962);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(964);
         var3 = var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(2)), var1.getglobal("OP_ASSIGN"));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(965);
      var10000 = var1.getglobal("With");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_with_var$95(PyFrame var1, ThreadState var2) {
      var1.setline(969);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_augassign_op$96(PyFrame var1, ThreadState var2) {
      var1.setline(972);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("augassign"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(973);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_augassign$97(PyFrame var1, ThreadState var2) {
      var1.setline(979);
      PyString.fromInterned("Return node suitable for lvalue of augmented assignment\n\n        Names, slices, and attributes are the only allowable nodes.\n        ");
      var1.setline(980);
      PyObject var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(981);
      var3 = var1.getlocal(2).__getattr__("__class__");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("Name"), var1.getglobal("Slice"), var1.getglobal("Subscript"), var1.getglobal("Getattr")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(982);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(983);
         throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to %s")._mod(var1.getlocal(2).__getattr__("__class__").__getattr__("__name__")));
      }
   }

   public PyObject com_assign$98(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(988);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(989);
         PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(990);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("symbol").__getattr__("exprlist"), var1.getglobal("symbol").__getattr__("testlist"), var1.getglobal("symbol").__getattr__("testlist_safe"), var1.getglobal("symbol").__getattr__("testlist_gexp")}));
         var3 = null;
         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(991);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(992);
               var3 = var1.getlocal(0).__getattr__("com_assign_tuple").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(993);
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var1.setlocal(1, var4);
            var4 = null;
         } else {
            var1.setline(994);
            var4 = var1.getlocal(3);
            var10000 = var4._in(var1.getglobal("_assign_types"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(995);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var4._gt(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(996);
                  throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to operator"));
               }

               var1.setline(997);
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
               var1.setlocal(1, var4);
               var4 = null;
            } else {
               var1.setline(998);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(var1.getglobal("symbol").__getattr__("power"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1011);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(var1.getglobal("symbol").__getattr__("atom"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1012);
                     var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                     var1.setlocal(3, var4);
                     var4 = null;
                     var1.setline(1013);
                     var4 = var1.getlocal(3);
                     var10000 = var4._eq(var1.getglobal("token").__getattr__("LPAR"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1014);
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                        var1.setlocal(1, var4);
                        var4 = null;
                        var1.setline(1015);
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                        var10000 = var4._eq(var1.getglobal("token").__getattr__("RPAR"));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           continue;
                        }

                        var1.setline(1016);
                        throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to ()"));
                     }

                     var1.setline(1017);
                     var4 = var1.getlocal(3);
                     var10000 = var4._eq(var1.getglobal("token").__getattr__("LSQB"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1018);
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                        var1.setlocal(1, var4);
                        var4 = null;
                        var1.setline(1019);
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                        var10000 = var4._eq(var1.getglobal("token").__getattr__("RSQB"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1020);
                           throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to []"));
                        }

                        var1.setline(1021);
                        var3 = var1.getlocal(0).__getattr__("com_assign_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(1022);
                     var4 = var1.getlocal(3);
                     var10000 = var4._eq(var1.getglobal("token").__getattr__("NAME"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1023);
                        var3 = var1.getlocal(0).__getattr__("com_assign_name").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(2));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(1025);
                     throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to literal"));
                  }

                  var1.setline(1027);
                  throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("bad assignment (%s)")._mod(var1.getlocal(3)));
               } else {
                  var1.setline(999);
                  var4 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                  var10000 = var4._ne(var1.getglobal("symbol").__getattr__("atom"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1000);
                     throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to operator"));
                  }

                  var1.setline(1001);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                  var10000 = var4._gt(Py.newInteger(2));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1002);
                     var4 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
                     var1.setlocal(4, var4);
                     var4 = null;
                     var1.setline(1003);
                     var4 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1))).__iter__();

                     while(true) {
                        var1.setline(1003);
                        PyObject var5 = var4.__iternext__();
                        if (var5 == null) {
                           var1.setline(1008);
                           var3 = var1.getlocal(0).__getattr__("com_assign_trailer").__call__(var2, var1.getlocal(4), var1.getlocal(1).__getitem__(Py.newInteger(-1)), var1.getlocal(2));
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setlocal(5, var5);
                        var1.setline(1004);
                        PyObject var6 = var1.getlocal(1).__getitem__(var1.getlocal(5));
                        var1.setlocal(6, var6);
                        var6 = null;
                        var1.setline(1005);
                        var6 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                        var10000 = var6._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1006);
                           throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to operator"));
                        }

                        var1.setline(1007);
                        var6 = var1.getlocal(0).__getattr__("com_apply_trailer").__call__(var2, var1.getlocal(4), var1.getlocal(6));
                        var1.setlocal(4, var6);
                        var6 = null;
                     }
                  }

                  var1.setline(1010);
                  var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
                  var1.setlocal(1, var4);
                  var4 = null;
               }
            }
         }
      }
   }

   public PyObject com_assign_tuple$99(PyFrame var1, ThreadState var2) {
      var1.setline(1030);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1031);
      PyObject var5 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(1031);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1033);
            PyObject var10000 = var1.getglobal("AssTuple");
            PyObject[] var6 = new PyObject[]{var1.getlocal(3), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(1))};
            String[] var7 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var7);
            var3 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(1032);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(4)), var1.getlocal(2)));
      }
   }

   public PyObject com_assign_list$100(PyFrame var1, ThreadState var2) {
      var1.setline(1036);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1037);
      PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(1037);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(1043);
            var10000 = var1.getglobal("AssList");
            PyObject[] var7 = new PyObject[]{var1.getlocal(3), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(1))};
            String[] var8 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var8);
            var3 = null;
            var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(1038);
         PyObject var5 = var1.getlocal(4)._add(Py.newInteger(1));
         var10000 = var5._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1039);
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1))).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("symbol").__getattr__("list_for"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1040);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to list comprehension"));
            }

            var1.setline(1041);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var5 = var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1))).__getitem__(Py.newInteger(0));
               var10000 = var5._eq(var1.getglobal("token").__getattr__("COMMA"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1))));
               }
            }
         }

         var1.setline(1042);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(4)), var1.getlocal(2)));
      }
   }

   public PyObject com_assign_name$101(PyFrame var1, ThreadState var2) {
      var1.setline(1046);
      PyObject var10000 = var1.getglobal("AssName");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject com_assign_trailer$102(PyFrame var1, ThreadState var2) {
      var1.setline(1049);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1050);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("DOT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1051);
         var3 = var1.getlocal(0).__getattr__("com_assign_attr").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1052);
         PyObject var4 = var1.getlocal(4);
         var10000 = var4._eq(var1.getglobal("token").__getattr__("LSQB"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1053);
            var3 = var1.getlocal(0).__getattr__("com_subscriptlist").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1054);
            var4 = var1.getlocal(4);
            var10000 = var4._eq(var1.getglobal("token").__getattr__("LPAR"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1055);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("can't assign to function call"));
            } else {
               var1.setline(1056);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("unknown trailer type: %s")._mod(var1.getlocal(4)));
            }
         }
      }
   }

   public PyObject com_assign_attr$103(PyFrame var1, ThreadState var2) {
      var1.setline(1059);
      PyObject var10000 = var1.getglobal("AssAttr");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(3), var1.getlocal(2).__getitem__(Py.newInteger(-1))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject com_binary$104(PyFrame var1, ThreadState var2) {
      var1.setline(1062);
      PyString.fromInterned("Compile 'NODE (OP NODE)*' into (type, [ node1, ..., nodeN ]).");
      var1.setline(1063);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1064);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1065);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1066);
         var3 = var1.getlocal(0).__getattr__("lookup_node").__call__(var2, var1.getlocal(4)).__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1067);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(1068);
         PyObject var7 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(1068);
            PyObject var5 = var7.__iternext__();
            if (var5 == null) {
               var1.setline(1071);
               var10000 = var1.getlocal(1);
               PyObject[] var8 = new PyObject[]{var1.getlocal(5), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(2))};
               String[] var9 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var8, var9);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(6, var5);
            var1.setline(1069);
            PyObject var6 = var1.getlocal(2).__getitem__(var1.getlocal(6));
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(1070);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("lookup_node").__call__(var2, var1.getlocal(4)).__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)));
         }
      }
   }

   public PyObject com_stmt$105(PyFrame var1, ThreadState var2) {
      var1.setline(1074);
      PyObject var3 = var1.getlocal(0).__getattr__("lookup_node").__call__(var2, var1.getlocal(1)).__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1075);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(1076);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Stmt")).__nonzero__()) {
         var1.setline(1077);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1078);
         var3 = var1.getglobal("Stmt").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject com_append_stmt$106(PyFrame var1, ThreadState var2) {
      var1.setline(1081);
      PyObject var3 = var1.getlocal(0).__getattr__("lookup_node").__call__(var2, var1.getlocal(2)).__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1082);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(1083);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Stmt")).__nonzero__()) {
         var1.setline(1084);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(3).__getattr__("nodes"));
      } else {
         var1.setline(1086);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject com_list_constructor$107(PyFrame var1, ThreadState var2) {
      var1.setline(1091);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1092);
      PyObject var7 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      while(true) {
         var1.setline(1092);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(1100);
            var10000 = var1.getglobal("List");
            PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("lineno")};
            String[] var9 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var8, var9);
            var3 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(1093);
         var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var10000 = var5._eq(var1.getglobal("symbol").__getattr__("list_for"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1094);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
               var10000 = var5._eq(Py.newInteger(1));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(1095);
            var5 = var1.getlocal(0).__getattr__("com_list_comprehension").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(var1.getlocal(3)));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(1097);
         PyObject var6 = var1.getlocal(1).__getitem__(var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var10000 = var6._eq(var1.getglobal("token").__getattr__("COMMA"));
         var6 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1099);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
         }
      }
   }

   public PyObject com_list_comprehension$108(PyFrame var1, ThreadState var2) {
      var1.setline(1109);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1110);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var5);
      var3 = null;

      while(true) {
         var1.setline(1111);
         PyObject var10000;
         String[] var4;
         PyObject[] var6;
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(1135);
            var10000 = var1.getglobal("ListComp");
            var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(3)};
            var4 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1112);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1113);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(PyString.fromInterned("for"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1114);
            var3 = var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getglobal("OP_ASSIGN"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(1115);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(4)));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(1116);
            var3 = var1.getglobal("ListCompFor").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)var1.getlocal(7), (PyObject)(new PyList(Py.EmptyObjects)));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(1117);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2));
            var1.getlocal(8).__setattr__("lineno", var3);
            var3 = null;
            var1.setline(1118);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
            var1.setline(1119);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1120);
               var3 = var1.getglobal("None");
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(1122);
               var3 = var1.getlocal(0).__getattr__("com_list_iter").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(5)));
               var1.setlocal(2, var3);
               var3 = null;
            }
         } else {
            var1.setline(1123);
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned("if"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1132);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("unexpected list comprehension element: %s %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
            }

            var1.setline(1124);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2)));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(1125);
            var10000 = var1.getglobal("ListCompIf");
            var6 = new PyObject[]{var1.getlocal(9), var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var4 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(1126);
            var1.getlocal(8).__getattr__("ifs").__getattr__("append").__call__(var2, var1.getlocal(10));
            var1.setline(1127);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1128);
               var3 = var1.getglobal("None");
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(1130);
               var3 = var1.getlocal(0).__getattr__("com_list_iter").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(3)));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject com_list_iter$109(PyFrame var1, ThreadState var2) {
      var1.setline(1138);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("list_iter"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(1139);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_list_constructor$110(PyFrame var1, ThreadState var2) {
      var1.setline(1142);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1143);
      PyObject var5 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(1143);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1145);
            PyObject var10000 = var1.getglobal("List");
            PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(2).__getitem__(Py.newInteger(0)).__getattr__("lineno")};
            String[] var7 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var7);
            var3 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(1144);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
      }
   }

   public PyObject com_generator_expression$111(PyFrame var1, ThreadState var2) {
      var1.setline(1153);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1154);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var5);
      var3 = null;

      while(true) {
         var1.setline(1155);
         PyObject var10000;
         String[] var4;
         PyObject[] var6;
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(1179);
            var3 = var1.getglobal("True");
            var1.getlocal(4).__getitem__(Py.newInteger(0)).__setattr__("is_outmost", var3);
            var3 = null;
            var1.setline(1180);
            var10000 = var1.getglobal("GenExpr");
            var6 = new PyObject[]{var1.getglobal("GenExprInner").__call__(var2, var1.getlocal(1), var1.getlocal(4)), var1.getlocal(3)};
            var4 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1156);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1157);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(PyString.fromInterned("for"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1158);
            var3 = var1.getlocal(0).__getattr__("com_assign").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getglobal("OP_ASSIGN"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(1159);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(4)));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(1160);
            var10000 = var1.getglobal("GenExprFor");
            var6 = new PyObject[]{var1.getlocal(6), var1.getlocal(7), new PyList(Py.EmptyObjects), var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var4 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(1162);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
            var1.setline(1163);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1164);
               var3 = var1.getglobal("None");
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(1166);
               var3 = var1.getlocal(0).__getattr__("com_gen_iter").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(5)));
               var1.setlocal(2, var3);
               var3 = null;
            }
         } else {
            var1.setline(1167);
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned("if"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1176);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("unexpected generator expression element: %s %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
            }

            var1.setline(1168);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2)));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(1169);
            var10000 = var1.getglobal("GenExprIf");
            var6 = new PyObject[]{var1.getlocal(9), var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(2))};
            var4 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(1170);
            var1.getlocal(8).__getattr__("ifs").__getattr__("append").__call__(var2, var1.getlocal(10));
            var1.setline(1171);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1172);
               var3 = var1.getglobal("None");
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(1174);
               var3 = var1.getlocal(0).__getattr__("com_gen_iter").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(3)));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }
   }

   public PyObject com_gen_iter$112(PyFrame var1, ThreadState var2) {
      var1.setline(1183);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(var1.getglobal("symbol").__getattr__("gen_iter"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(1184);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject com_dictmaker$113(PyFrame var1, ThreadState var2) {
      var1.setline(1188);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1189);
      PyObject var5 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(4)).__iter__();

      while(true) {
         var1.setline(1189);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1192);
            PyObject var10000 = var1.getglobal("Dict");
            PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0)).__getattr__("lineno")};
            String[] var7 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var6, var7);
            var3 = null;
            var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(1190);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))), var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)._add(Py.newInteger(2))))})));
      }
   }

   public PyObject com_apply_trailer$114(PyFrame var1, ThreadState var2) {
      var1.setline(1195);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1196);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("LPAR"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1197);
         var3 = var1.getlocal(0).__getattr__("com_call_function").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(2)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1198);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._eq(var1.getglobal("token").__getattr__("DOT"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1199);
            var3 = var1.getlocal(0).__getattr__("com_select_member").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(2)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1200);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(var1.getglobal("token").__getattr__("LSQB"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1201);
               var3 = var1.getlocal(0).__getattr__("com_subscriptlist").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getglobal("OP_APPLY"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1203);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("unknown node type: %s")._mod(var1.getlocal(3)));
            }
         }
      }
   }

   public PyObject com_select_member$115(PyFrame var1, ThreadState var2) {
      var1.setline(1206);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(var1.getglobal("token").__getattr__("NAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1207);
         throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("member must be a name"));
      } else {
         var1.setline(1208);
         var10000 = var1.getglobal("Getattr");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(2).__getitem__(Py.newInteger(2))};
         String[] var4 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject com_call_function$116(PyFrame var1, ThreadState var2) {
      var1.setline(1211);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("RPAR"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1212);
         var10000 = var1.getglobal("CallFunc");
         PyObject[] var9 = new PyObject[]{var1.getlocal(1), new PyList(Py.EmptyObjects), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(2))};
         String[] var14 = new String[]{"lineno"};
         var10000 = var10000.__call__(var2, var9, var14);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1213);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1214);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(4, var10);
         var4 = null;
         var1.setline(1215);
         PyObject var11 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var11);
         var4 = null;
         var1.setline(1216);
         var11 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(1216);
            PyObject var5 = var11.__iternext__();
            PyObject var6;
            if (var5 == null) {
               var1.setline(1231);
               var6 = var1.getlocal(6)._add(Py.newInteger(1));
               var1.setlocal(6, var6);
               var6 = null;
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(1217);
            var6 = var1.getlocal(2).__getitem__(var1.getlocal(6));
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(1218);
            var6 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(var1.getglobal("token").__getattr__("STAR"));
            var6 = null;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(7).__getitem__(Py.newInteger(0));
               var10000 = var6._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
               var6 = null;
            }

            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(1220);
            var6 = var1.getlocal(0).__getattr__("com_argument").__call__(var2, var1.getlocal(7), var1.getlocal(4));
            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(4, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(8, var8);
            var8 = null;
            var6 = null;
            var1.setline(1222);
            var6 = var1.getlocal(5);
            var10000 = var6._ne(Py.newInteger(2));
            var6 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("GenExpr"));
               if (var10000.__nonzero__()) {
                  var6 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
                  var10000 = var6._eq(Py.newInteger(3));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(7).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
                     var10000 = var6._eq(var1.getglobal("symbol").__getattr__("gen_for"));
                     var6 = null;
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(1226);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("generator expression needs parenthesis"));
            }

            var1.setline(1228);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(8));
         }

         var1.setline(1232);
         var11 = var1.getlocal(6);
         var10000 = var11._lt(var1.getlocal(5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var11 = var1.getlocal(2).__getitem__(var1.getlocal(6)).__getitem__(Py.newInteger(0));
            var10000 = var11._eq(var1.getglobal("token").__getattr__("COMMA"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1234);
            var11 = var1.getlocal(6)._add(Py.newInteger(1));
            var1.setlocal(6, var11);
            var4 = null;
         }

         var1.setline(1235);
         var11 = var1.getglobal("None");
         var1.setlocal(9, var11);
         var1.setlocal(10, var11);

         while(true) {
            var1.setline(1236);
            var11 = var1.getlocal(6);
            var10000 = var11._lt(var1.getlocal(5));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1250);
               var10000 = var1.getglobal("CallFunc");
               PyObject[] var13 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(9), var1.getlocal(10), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(2))};
               String[] var12 = new String[]{"lineno"};
               var10000 = var10000.__call__(var2, var13, var12);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1237);
            var11 = var1.getlocal(2).__getitem__(var1.getlocal(6));
            var1.setlocal(11, var11);
            var4 = null;
            var1.setline(1238);
            var11 = var1.getlocal(2).__getitem__(var1.getlocal(6)._add(Py.newInteger(1)));
            var1.setlocal(12, var11);
            var4 = null;
            var1.setline(1239);
            var11 = var1.getlocal(6)._add(Py.newInteger(3));
            var1.setlocal(6, var11);
            var4 = null;
            var1.setline(1240);
            var11 = var1.getlocal(11).__getitem__(Py.newInteger(0));
            var10000 = var11._eq(var1.getglobal("token").__getattr__("STAR"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1241);
               var11 = var1.getlocal(9);
               var10000 = var11._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1242);
                  throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("already have the varargs indentifier"));
               }

               var1.setline(1243);
               var11 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(12));
               var1.setlocal(9, var11);
               var4 = null;
            } else {
               var1.setline(1244);
               var11 = var1.getlocal(11).__getitem__(Py.newInteger(0));
               var10000 = var11._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1249);
                  throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("unknown node type: %s")._mod(var1.getlocal(11)));
               }

               var1.setline(1245);
               var11 = var1.getlocal(10);
               var10000 = var11._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1246);
                  throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("already have the kwargs indentifier"));
               }

               var1.setline(1247);
               var11 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(12));
               var1.setlocal(10, var11);
               var4 = null;
            }
         }
      }
   }

   public PyObject com_argument$117(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("symbol").__getattr__("gen_for"));
         var3 = null;
      }

      PyTuple var6;
      if (var10000.__nonzero__()) {
         var1.setline(1255);
         var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1256);
         var6 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getlocal(0).__getattr__("com_generator_expression").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getitem__(Py.newInteger(2)))});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(1257);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var4._eq(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1258);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1259);
               throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("non-keyword arg after keyword arg"));
            } else {
               var1.setline(1260);
               var6 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)))});
               var1.f_lasti = -1;
               return var6;
            }
         } else {
            var1.setline(1261);
            var4 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1262);
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var1.setlocal(5, var4);
            var4 = null;

            while(true) {
               var1.setline(1263);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
               var10000 = var4._eq(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                  var10000 = var4._ne(var1.getglobal("token").__getattr__("NAME"));
                  var4 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(1265);
                  var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                  var10000 = var4._ne(var1.getglobal("token").__getattr__("NAME"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1266);
                     throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("keyword can't be an expression (%s)")._mod(var1.getlocal(5).__getitem__(Py.newInteger(0))));
                  }

                  var1.setline(1267);
                  var10000 = var1.getglobal("Keyword");
                  PyObject[] var7 = new PyObject[]{var1.getlocal(5).__getitem__(Py.newInteger(1)), var1.getlocal(4), var1.getlocal(5).__getitem__(Py.newInteger(2))};
                  String[] var5 = new String[]{"lineno"};
                  var10000 = var10000.__call__(var2, var7, var5);
                  var4 = null;
                  var4 = var10000;
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(1268);
                  var6 = new PyTuple(new PyObject[]{Py.newInteger(1), var1.getlocal(6)});
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setline(1264);
               var4 = var1.getlocal(5).__getitem__(Py.newInteger(1));
               var1.setlocal(5, var4);
               var4 = null;
            }
         }
      }
   }

   public PyObject com_subscriptlist$118(PyFrame var1, ThreadState var2) {
      var1.setline(1277);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1278);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1279);
         var3 = var1.getlocal(4).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("COLON"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(4).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
               var10000 = var3._eq(var1.getglobal("token").__getattr__("COLON"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(4).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
            var10000 = var3._ne(var1.getglobal("symbol").__getattr__("sliceop"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1282);
            var3 = var1.getlocal(0).__getattr__("com_slice").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(1284);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(1285);
      PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(1285);
         PyObject var5 = var6.__iternext__();
         if (var5 == null) {
            var1.setline(1287);
            var10000 = var1.getglobal("Subscript");
            PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(5), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(2))};
            String[] var8 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var7, var8);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(6, var5);
         var1.setline(1286);
         var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_subscript").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6))));
      }
   }

   public PyObject com_subscript$119(PyFrame var1, ThreadState var2) {
      var1.setline(1292);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1293);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1294);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("DOT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(2)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("DOT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1295);
         var3 = var1.getglobal("Ellipsis").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1296);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._eq(var1.getglobal("token").__getattr__("COLON"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var4._gt(Py.newInteger(2));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1297);
            var3 = var1.getlocal(0).__getattr__("com_sliceobj").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1298);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject com_sliceobj$120(PyFrame var1, ThreadState var2) {
      var1.setline(1310);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1312);
      PyObject var6 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      PyObject var10000 = var6._eq(var1.getglobal("token").__getattr__("COLON"));
      var3 = null;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var1.setline(1313);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("Const").__call__(var2, var1.getglobal("None")));
         var1.setline(1314);
         var7 = Py.newInteger(2);
         var1.setlocal(3, var7);
         var3 = null;
      } else {
         var1.setline(1316);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))));
         var1.setline(1318);
         var7 = Py.newInteger(3);
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(1320);
      var6 = var1.getlocal(3);
      var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var6 = var1.getlocal(1).__getitem__(var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var10000 = var6._eq(var1.getglobal("symbol").__getattr__("test"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1321);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3))));
         var1.setline(1322);
         var6 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.setlocal(3, var6);
         var3 = null;
      } else {
         var1.setline(1324);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("Const").__call__(var2, var1.getglobal("None")));
      }

      var1.setline(1328);
      var6 = var1.getglobal("range").__call__(var2, var1.getlocal(3), var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      while(true) {
         var1.setline(1328);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1334);
            var10000 = var1.getglobal("Sliceobj");
            PyObject[] var9 = new PyObject[]{var1.getlocal(2), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(1))};
            String[] var8 = new String[]{"lineno"};
            var10000 = var10000.__call__(var2, var9, var8);
            var3 = null;
            var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(1329);
         PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1330);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         var10000 = var5._eq(Py.newInteger(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1331);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("Const").__call__(var2, var1.getglobal("None")));
         } else {
            var1.setline(1333);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(2))));
         }
      }
   }

   public PyObject com_slice$121(PyFrame var1, ThreadState var2) {
      var1.setline(1338);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var1.setlocal(5, var3);
      var1.setline(1339);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1340);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("token").__getattr__("COLON"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1341);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2)));
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(1343);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
            var1.setlocal(4, var3);
            var3 = null;
         }
      } else {
         var1.setline(1344);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1345);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1346);
            var3 = var1.getlocal(0).__getattr__("com_node").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(3)));
            var1.setlocal(5, var3);
            var3 = null;
         }
      }

      var1.setline(1347);
      var10000 = var1.getglobal("Slice");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getglobal("extractLineNo").__call__(var2, var1.getlocal(2))};
      String[] var4 = new String[]{"lineno"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_docstring$122(PyFrame var1, ThreadState var2) {
      var1.setline(1351);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1352);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1353);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1354);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(var1.getglobal("symbol").__getattr__("suite"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(1355);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1356);
            var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1357);
            var4 = var1.getlocal(1).__iter__();

            do {
               var1.setline(1357);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1360);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(1358);
               var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var10000 = var6._eq(var1.getglobal("symbol").__getattr__("stmt"));
               var6 = null;
            } while(!var10000.__nonzero__());

            var1.setline(1359);
            var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(1361);
         var4 = var1.getlocal(2);
         var10000 = var4._eq(var1.getglobal("symbol").__getattr__("file_input"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1362);
            var4 = var1.getlocal(1).__iter__();

            do {
               var1.setline(1362);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1365);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(1363);
               var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var10000 = var6._eq(var1.getglobal("symbol").__getattr__("stmt"));
               var6 = null;
            } while(!var10000.__nonzero__());

            var1.setline(1364);
            var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1366);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(var1.getglobal("symbol").__getattr__("atom"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1373);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(var1.getglobal("symbol").__getattr__("stmt"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(2);
                  var10000 = var4._eq(var1.getglobal("symbol").__getattr__("simple_stmt"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(2);
                     var10000 = var4._eq(var1.getglobal("symbol").__getattr__("small_stmt"));
                     var4 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1375);
                  var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1376);
                  var4 = var1.getlocal(2);
                  var10000 = var4._in(var1.getglobal("_doc_nodes"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                     var10000 = var4._eq(Py.newInteger(1));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1377);
                     var3 = var1.getlocal(0).__getattr__("get_docstring").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1378);
                     var3 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            } else {
               var1.setline(1367);
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
               var10000 = var4._eq(var1.getglobal("token").__getattr__("STRING"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1372);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1368);
                  PyString var7 = PyString.fromInterned("");
                  var1.setlocal(4, var7);
                  var4 = null;
                  var1.setline(1369);
                  var4 = var1.getlocal(1).__iter__();

                  while(true) {
                     var1.setline(1369);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(1371);
                        var3 = var1.getlocal(4);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(5, var5);
                     var1.setline(1370);
                     var6 = var1.getlocal(4)._add(var1.getglobal("eval").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(1))));
                     var1.setlocal(4, var6);
                     var6 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject debug_tree$123(PyFrame var1, ThreadState var2) {
      var1.setline(1483);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1484);
      PyObject var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1484);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1491);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1485);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("int")).__nonzero__()) {
            var1.setline(1486);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("_names").__getattr__("get").__call__(var2, var1.getlocal(2), var1.getlocal(2)));
         } else {
            var1.setline(1487);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
               var1.setline(1488);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
            } else {
               var1.setline(1490);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("debug_tree").__call__(var2, var1.getlocal(2)));
            }
         }
      }
   }

   public transformer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      WalkerError$1 = Py.newCode(0, var2, var1, "WalkerError", 35, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"path", "f", "src"};
      parseFile$2 = Py.newCode(1, var2, var1, "parseFile", 41, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"buf", "mode"};
      parse$3 = Py.newCode(2, var2, var1, "parse", 51, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"nodes", "l", "item"};
      asList$4 = Py.newCode(1, var2, var1, "asList", 60, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ast", "child", "lineno"};
      extractLineNo$5 = Py.newCode(1, var2, var1, "extractLineNo", 74, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kind"};
      Node$6 = Py.newCode(1, var2, var1, "Node", 84, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Transformer$7 = Py.newCode(0, var2, var1, "Transformer", 96, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "name"};
      __init__$8 = Py.newCode(1, var2, var1, "__init__", 106, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree"};
      transform$9 = Py.newCode(2, var2, var1, "transform", 122, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      parsesuite$10 = Py.newCode(2, var2, var1, "parsesuite", 128, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      parseexpr$11 = Py.newCode(2, var2, var1, "parseexpr", 132, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      parsefile$12 = Py.newCode(2, var2, var1, "parsefile", 136, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "n"};
      compile_node$13 = Py.newCode(2, var2, var1, "compile_node", 147, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "n"};
      single_input$14 = Py.newCode(2, var2, var1, "single_input", 171, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "doc", "i", "stmts", "node"};
      file_input$15 = Py.newCode(2, var2, var1, "file_input", 181, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      eval_input$16 = Py.newCode(2, var2, var1, "eval_input", 193, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "listlen", "item", "i"};
      decorator_name$17 = Py.newCode(2, var2, var1, "decorator_name", 198, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "funcname", "expr"};
      decorator$18 = Py.newCode(2, var2, var1, "decorator", 212, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "items", "dec_nodelist"};
      decorators$19 = Py.newCode(2, var2, var1, "decorators", 229, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "decorators", "lineno", "name", "args", "names", "defaults", "flags", "doc", "code"};
      funcdef$20 = Py.newCode(2, var2, var1, "funcdef", 237, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "names", "defaults", "flags", "code"};
      lambdef$21 = Py.newCode(2, var2, var1, "lambdef", 270, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "name", "doc", "bases", "code"};
      classdef$22 = Py.newCode(2, var2, var1, "classdef", 284, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      stmt$23 = Py.newCode(2, var2, var1, "stmt", 306, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "stmts", "i"};
      simple_stmt$24 = Py.newCode(2, var2, var1, "simple_stmt", 313, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      parameters$25 = Py.newCode(2, var2, var1, "parameters", 320, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      varargslist$26 = Py.newCode(2, var2, var1, "varargslist", 323, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      fpdef$27 = Py.newCode(2, var2, var1, "fpdef", 326, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      fplist$28 = Py.newCode(2, var2, var1, "fplist", 329, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      dotted_name$29 = Py.newCode(2, var2, var1, "dotted_name", 332, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      comp_op$30 = Py.newCode(2, var2, var1, "comp_op", 335, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      trailer$31 = Py.newCode(2, var2, var1, "trailer", 338, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      sliceop$32 = Py.newCode(2, var2, var1, "sliceop", 341, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      argument$33 = Py.newCode(2, var2, var1, "argument", 344, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "en", "exprNode", "nodesl", "i", "lval", "op"};
      expr_stmt$34 = Py.newCode(2, var2, var1, "expr_stmt", 352, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "items", "start", "dest", "i"};
      print_stmt$35 = Py.newCode(2, var2, var1, "print_stmt", 369, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      del_stmt$36 = Py.newCode(2, var2, var1, "del_stmt", 389, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      pass_stmt$37 = Py.newCode(2, var2, var1, "pass_stmt", 392, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      break_stmt$38 = Py.newCode(2, var2, var1, "break_stmt", 395, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      continue_stmt$39 = Py.newCode(2, var2, var1, "continue_stmt", 398, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      return_stmt$40 = Py.newCode(2, var2, var1, "return_stmt", 401, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "expr"};
      yield_stmt$41 = Py.newCode(2, var2, var1, "yield_stmt", 407, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "value"};
      yield_expr$42 = Py.newCode(2, var2, var1, "yield_expr", 411, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "expr3", "expr2", "expr1"};
      raise_stmt$43 = Py.newCode(2, var2, var1, "raise_stmt", 418, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      import_stmt$44 = Py.newCode(2, var2, var1, "import_stmt", 434, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      import_name$45 = Py.newCode(2, var2, var1, "import_name", 439, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "idx", "level", "fromname", "node"};
      import_from$46 = Py.newCode(2, var2, var1, "import_from", 444, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "names", "i"};
      global_stmt$47 = Py.newCode(2, var2, var1, "global_stmt", 466, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "expr1", "expr2", "expr3"};
      exec_stmt$48 = Py.newCode(2, var2, var1, "exec_stmt", 473, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "expr1", "expr2"};
      assert_stmt$49 = Py.newCode(2, var2, var1, "assert_stmt", 487, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "tests", "i", "testNode", "suiteNode", "elseNode"};
      if_stmt$50 = Py.newCode(2, var2, var1, "if_stmt", 496, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "testNode", "bodyNode", "elseNode"};
      while_stmt$51 = Py.newCode(2, var2, var1, "while_stmt", 511, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "assignNode", "listNode", "bodyNode", "elseNode"};
      for_stmt$52 = Py.newCode(2, var2, var1, "for_stmt", 524, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      try_stmt$53 = Py.newCode(2, var2, var1, "try_stmt", 539, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      with_stmt$54 = Py.newCode(2, var2, var1, "with_stmt", 542, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      with_var$55 = Py.newCode(2, var2, var1, "with_var", 545, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "stmts", "node"};
      suite$56 = Py.newCode(2, var2, var1, "suite", 548, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      testlist$57 = Py.newCode(2, var2, var1, "testlist", 564, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "test"};
      testlist_gexp$58 = Py.newCode(2, var2, var1, "testlist_gexp", 574, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "then", "test", "else_"};
      test$59 = Py.newCode(2, var2, var1, "test", 580, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      or_test$60 = Py.newCode(2, var2, var1, "or_test", 594, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      and_test$61 = Py.newCode(2, var2, var1, "and_test", 601, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "result"};
      not_test$62 = Py.newCode(2, var2, var1, "not_test", 605, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "node", "results", "i", "nl", "n", "type", "lineno"};
      comparison$63 = Py.newCode(2, var2, var1, "comparison", 612, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      expr$64 = Py.newCode(2, var2, var1, "expr", 645, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      xor_expr$65 = Py.newCode(2, var2, var1, "xor_expr", 649, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      and_expr$66 = Py.newCode(2, var2, var1, "and_expr", 653, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "node", "i", "right"};
      shift_expr$67 = Py.newCode(2, var2, var1, "shift_expr", 657, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "node", "i", "right"};
      arith_expr$68 = Py.newCode(2, var2, var1, "arith_expr", 670, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "node", "i", "right", "t"};
      term$69 = Py.newCode(2, var2, var1, "term", 682, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "elt", "t", "node"};
      factor$70 = Py.newCode(2, var2, var1, "factor", 700, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "node", "i", "elt"};
      power$71 = Py.newCode(2, var2, var1, "power", 713, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      atom$72 = Py.newCode(2, var2, var1, "atom", 726, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      atom_lpar$73 = Py.newCode(2, var2, var1, "atom_lpar", 729, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      atom_lsqb$74 = Py.newCode(2, var2, var1, "atom_lsqb", 734, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      atom_lbrace$75 = Py.newCode(2, var2, var1, "atom_lbrace", 739, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      atom_backquote$76 = Py.newCode(2, var2, var1, "atom_backquote", 744, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "k"};
      atom_number$77 = Py.newCode(2, var2, var1, "atom_number", 747, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lit"};
      decode_literal$78 = Py.newCode(2, var2, var1, "decode_literal", 752, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "k", "node"};
      atom_string$79 = Py.newCode(2, var2, var1, "atom_string", 763, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      atom_name$80 = Py.newCode(2, var2, var1, "atom_name", 769, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      lookup_node$81 = Py.newCode(2, var2, var1, "lookup_node", 785, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      com_node$82 = Py.newCode(2, var2, var1, "com_node", 788, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      com_NEWLINE$83 = Py.newCode(2, var2, var1, "com_NEWLINE", 795, true, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "names", "defaults", "flags", "i", "node", "t"};
      com_arglist$84 = Py.newCode(2, var2, var1, "com_arglist", 801, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      com_fpdef$85 = Py.newCode(2, var2, var1, "com_fpdef", 851, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "list", "i"};
      com_fplist$86 = Py.newCode(2, var2, var1, "com_fplist", 857, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "name", "n"};
      com_dotted_name$87 = Py.newCode(2, var2, var1, "com_dotted_name", 866, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "dot"};
      com_dotted_as_name$88 = Py.newCode(2, var2, var1, "com_dotted_as_name", 874, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "names", "i"};
      com_dotted_as_names$89 = Py.newCode(2, var2, var1, "com_dotted_as_names", 884, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      com_import_as_name$90 = Py.newCode(2, var2, var1, "com_import_as_name", 892, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "names", "i"};
      com_import_as_names$91 = Py.newCode(2, var2, var1, "com_import_as_names", 902, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "bases", "i"};
      com_bases$92 = Py.newCode(2, var2, var1, "com_bases", 910, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "clauses", "elseNode", "finallyNode", "i", "node", "expr1", "expr2", "try_except"};
      com_try_except_finally$93 = Py.newCode(2, var2, var1, "com_try_except_finally", 916, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "expr", "body", "var"};
      com_with$94 = Py.newCode(2, var2, var1, "com_with", 957, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      com_with_var$95 = Py.newCode(2, var2, var1, "com_with_var", 967, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      com_augassign_op$96 = Py.newCode(2, var2, var1, "com_augassign_op", 971, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "l"};
      com_augassign$97 = Py.newCode(2, var2, var1, "com_augassign", 975, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "assigning", "t", "primary", "i", "ch"};
      com_assign$98 = Py.newCode(3, var2, var1, "com_assign", 985, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "assigning", "assigns", "i"};
      com_assign_tuple$99 = Py.newCode(3, var2, var1, "com_assign_tuple", 1029, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "assigning", "assigns", "i"};
      com_assign_list$100 = Py.newCode(3, var2, var1, "com_assign_list", 1035, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "assigning"};
      com_assign_name$101 = Py.newCode(3, var2, var1, "com_assign_name", 1045, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primary", "node", "assigning", "t"};
      com_assign_trailer$102 = Py.newCode(4, var2, var1, "com_assign_trailer", 1048, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primary", "node", "assigning"};
      com_assign_attr$103 = Py.newCode(4, var2, var1, "com_assign_attr", 1058, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "constructor", "nodelist", "l", "n", "items", "i"};
      com_binary$104 = Py.newCode(3, var2, var1, "com_binary", 1061, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "result"};
      com_stmt$105 = Py.newCode(2, var2, var1, "com_stmt", 1073, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stmts", "node", "result"};
      com_append_stmt$106 = Py.newCode(3, var2, var1, "com_append_stmt", 1080, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "values", "i"};
      com_list_constructor$107 = Py.newCode(2, var2, var1, "com_list_constructor", 1089, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expr", "node", "lineno", "fors", "t", "assignNode", "listNode", "newfor", "test", "newif"};
      com_list_comprehension$108 = Py.newCode(3, var2, var1, "com_list_comprehension", 1102, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      com_list_iter$109 = Py.newCode(2, var2, var1, "com_list_iter", 1137, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "values", "i"};
      com_list_constructor$110 = Py.newCode(2, var2, var1, "com_list_constructor", 1141, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expr", "node", "lineno", "fors", "t", "assignNode", "genNode", "newfor", "test", "newif"};
      com_generator_expression$111 = Py.newCode(3, var2, var1, "com_generator_expression", 1148, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      com_gen_iter$112 = Py.newCode(2, var2, var1, "com_gen_iter", 1182, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "items", "i"};
      com_dictmaker$113 = Py.newCode(2, var2, var1, "com_dictmaker", 1186, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primaryNode", "nodelist", "t"};
      com_apply_trailer$114 = Py.newCode(3, var2, var1, "com_apply_trailer", 1194, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primaryNode", "nodelist"};
      com_select_member$115 = Py.newCode(3, var2, var1, "com_select_member", 1205, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primaryNode", "nodelist", "args", "kw", "len_nodelist", "i", "node", "result", "star_node", "dstar_node", "tok", "ch"};
      com_call_function$116 = Py.newCode(3, var2, var1, "com_call_function", 1210, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist", "kw", "test", "result", "n", "node"};
      com_argument$117 = Py.newCode(3, var2, var1, "com_argument", 1253, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primary", "nodelist", "assigning", "sub", "subscripts", "i"};
      com_subscriptlist$118 = Py.newCode(4, var2, var1, "com_subscriptlist", 1270, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "ch", "t"};
      com_subscript$119 = Py.newCode(2, var2, var1, "com_subscript", 1290, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "items", "i", "j", "ch"};
      com_sliceobj$120 = Py.newCode(2, var2, var1, "com_sliceobj", 1300, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primary", "node", "assigning", "lower", "upper"};
      com_slice$121 = Py.newCode(4, var2, var1, "com_slice", 1336, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "n", "sub", "s", "t"};
      get_docstring$122 = Py.newCode(3, var2, var1, "get_docstring", 1350, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tree", "l", "elt"};
      debug_tree$123 = Py.newCode(1, var2, var1, "debug_tree", 1482, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new transformer$py("compiler/transformer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(transformer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.WalkerError$1(var2, var3);
         case 2:
            return this.parseFile$2(var2, var3);
         case 3:
            return this.parse$3(var2, var3);
         case 4:
            return this.asList$4(var2, var3);
         case 5:
            return this.extractLineNo$5(var2, var3);
         case 6:
            return this.Node$6(var2, var3);
         case 7:
            return this.Transformer$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.transform$9(var2, var3);
         case 10:
            return this.parsesuite$10(var2, var3);
         case 11:
            return this.parseexpr$11(var2, var3);
         case 12:
            return this.parsefile$12(var2, var3);
         case 13:
            return this.compile_node$13(var2, var3);
         case 14:
            return this.single_input$14(var2, var3);
         case 15:
            return this.file_input$15(var2, var3);
         case 16:
            return this.eval_input$16(var2, var3);
         case 17:
            return this.decorator_name$17(var2, var3);
         case 18:
            return this.decorator$18(var2, var3);
         case 19:
            return this.decorators$19(var2, var3);
         case 20:
            return this.funcdef$20(var2, var3);
         case 21:
            return this.lambdef$21(var2, var3);
         case 22:
            return this.classdef$22(var2, var3);
         case 23:
            return this.stmt$23(var2, var3);
         case 24:
            return this.simple_stmt$24(var2, var3);
         case 25:
            return this.parameters$25(var2, var3);
         case 26:
            return this.varargslist$26(var2, var3);
         case 27:
            return this.fpdef$27(var2, var3);
         case 28:
            return this.fplist$28(var2, var3);
         case 29:
            return this.dotted_name$29(var2, var3);
         case 30:
            return this.comp_op$30(var2, var3);
         case 31:
            return this.trailer$31(var2, var3);
         case 32:
            return this.sliceop$32(var2, var3);
         case 33:
            return this.argument$33(var2, var3);
         case 34:
            return this.expr_stmt$34(var2, var3);
         case 35:
            return this.print_stmt$35(var2, var3);
         case 36:
            return this.del_stmt$36(var2, var3);
         case 37:
            return this.pass_stmt$37(var2, var3);
         case 38:
            return this.break_stmt$38(var2, var3);
         case 39:
            return this.continue_stmt$39(var2, var3);
         case 40:
            return this.return_stmt$40(var2, var3);
         case 41:
            return this.yield_stmt$41(var2, var3);
         case 42:
            return this.yield_expr$42(var2, var3);
         case 43:
            return this.raise_stmt$43(var2, var3);
         case 44:
            return this.import_stmt$44(var2, var3);
         case 45:
            return this.import_name$45(var2, var3);
         case 46:
            return this.import_from$46(var2, var3);
         case 47:
            return this.global_stmt$47(var2, var3);
         case 48:
            return this.exec_stmt$48(var2, var3);
         case 49:
            return this.assert_stmt$49(var2, var3);
         case 50:
            return this.if_stmt$50(var2, var3);
         case 51:
            return this.while_stmt$51(var2, var3);
         case 52:
            return this.for_stmt$52(var2, var3);
         case 53:
            return this.try_stmt$53(var2, var3);
         case 54:
            return this.with_stmt$54(var2, var3);
         case 55:
            return this.with_var$55(var2, var3);
         case 56:
            return this.suite$56(var2, var3);
         case 57:
            return this.testlist$57(var2, var3);
         case 58:
            return this.testlist_gexp$58(var2, var3);
         case 59:
            return this.test$59(var2, var3);
         case 60:
            return this.or_test$60(var2, var3);
         case 61:
            return this.and_test$61(var2, var3);
         case 62:
            return this.not_test$62(var2, var3);
         case 63:
            return this.comparison$63(var2, var3);
         case 64:
            return this.expr$64(var2, var3);
         case 65:
            return this.xor_expr$65(var2, var3);
         case 66:
            return this.and_expr$66(var2, var3);
         case 67:
            return this.shift_expr$67(var2, var3);
         case 68:
            return this.arith_expr$68(var2, var3);
         case 69:
            return this.term$69(var2, var3);
         case 70:
            return this.factor$70(var2, var3);
         case 71:
            return this.power$71(var2, var3);
         case 72:
            return this.atom$72(var2, var3);
         case 73:
            return this.atom_lpar$73(var2, var3);
         case 74:
            return this.atom_lsqb$74(var2, var3);
         case 75:
            return this.atom_lbrace$75(var2, var3);
         case 76:
            return this.atom_backquote$76(var2, var3);
         case 77:
            return this.atom_number$77(var2, var3);
         case 78:
            return this.decode_literal$78(var2, var3);
         case 79:
            return this.atom_string$79(var2, var3);
         case 80:
            return this.atom_name$80(var2, var3);
         case 81:
            return this.lookup_node$81(var2, var3);
         case 82:
            return this.com_node$82(var2, var3);
         case 83:
            return this.com_NEWLINE$83(var2, var3);
         case 84:
            return this.com_arglist$84(var2, var3);
         case 85:
            return this.com_fpdef$85(var2, var3);
         case 86:
            return this.com_fplist$86(var2, var3);
         case 87:
            return this.com_dotted_name$87(var2, var3);
         case 88:
            return this.com_dotted_as_name$88(var2, var3);
         case 89:
            return this.com_dotted_as_names$89(var2, var3);
         case 90:
            return this.com_import_as_name$90(var2, var3);
         case 91:
            return this.com_import_as_names$91(var2, var3);
         case 92:
            return this.com_bases$92(var2, var3);
         case 93:
            return this.com_try_except_finally$93(var2, var3);
         case 94:
            return this.com_with$94(var2, var3);
         case 95:
            return this.com_with_var$95(var2, var3);
         case 96:
            return this.com_augassign_op$96(var2, var3);
         case 97:
            return this.com_augassign$97(var2, var3);
         case 98:
            return this.com_assign$98(var2, var3);
         case 99:
            return this.com_assign_tuple$99(var2, var3);
         case 100:
            return this.com_assign_list$100(var2, var3);
         case 101:
            return this.com_assign_name$101(var2, var3);
         case 102:
            return this.com_assign_trailer$102(var2, var3);
         case 103:
            return this.com_assign_attr$103(var2, var3);
         case 104:
            return this.com_binary$104(var2, var3);
         case 105:
            return this.com_stmt$105(var2, var3);
         case 106:
            return this.com_append_stmt$106(var2, var3);
         case 107:
            return this.com_list_constructor$107(var2, var3);
         case 108:
            return this.com_list_comprehension$108(var2, var3);
         case 109:
            return this.com_list_iter$109(var2, var3);
         case 110:
            return this.com_list_constructor$110(var2, var3);
         case 111:
            return this.com_generator_expression$111(var2, var3);
         case 112:
            return this.com_gen_iter$112(var2, var3);
         case 113:
            return this.com_dictmaker$113(var2, var3);
         case 114:
            return this.com_apply_trailer$114(var2, var3);
         case 115:
            return this.com_select_member$115(var2, var3);
         case 116:
            return this.com_call_function$116(var2, var3);
         case 117:
            return this.com_argument$117(var2, var3);
         case 118:
            return this.com_subscriptlist$118(var2, var3);
         case 119:
            return this.com_subscript$119(var2, var3);
         case 120:
            return this.com_sliceobj$120(var2, var3);
         case 121:
            return this.com_slice$121(var2, var3);
         case 122:
            return this.get_docstring$122(var2, var3);
         case 123:
            return this.debug_tree$123(var2, var3);
         default:
            return null;
      }
   }
}
