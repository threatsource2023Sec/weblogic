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
@Filename("compiler/pycodegen.py")
public class pycodegen$py extends PyFunctionTable implements PyRunnable {
   static pycodegen$py self;
   static final PyCode f$0;
   static final PyCode compileFile$1;
   static final PyCode compile$2;
   static final PyCode AbstractCompileMode$3;
   static final PyCode __init__$4;
   static final PyCode _get_tree$5;
   static final PyCode compile$6;
   static final PyCode getCode$7;
   static final PyCode Expression$8;
   static final PyCode compile$9;
   static final PyCode Interactive$10;
   static final PyCode compile$11;
   static final PyCode Module$12;
   static final PyCode compile$13;
   static final PyCode dump$14;
   static final PyCode getPycHeader$15;
   static final PyCode LocalNameFinder$16;
   static final PyCode __init__$17;
   static final PyCode getLocals$18;
   static final PyCode visitDict$19;
   static final PyCode visitGlobal$20;
   static final PyCode visitFunction$21;
   static final PyCode visitLambda$22;
   static final PyCode visitImport$23;
   static final PyCode visitFrom$24;
   static final PyCode visitClass$25;
   static final PyCode visitAssName$26;
   static final PyCode is_constant_false$27;
   static final PyCode CodeGenerator$28;
   static final PyCode __init__$29;
   static final PyCode initClass$30;
   static final PyCode checkClass$31;
   static final PyCode _setupGraphDelegation$32;
   static final PyCode getCode$33;
   static final PyCode mangle$34;
   static final PyCode parseSymbols$35;
   static final PyCode get_module$36;
   static final PyCode isLocalName$37;
   static final PyCode storeName$38;
   static final PyCode loadName$39;
   static final PyCode delName$40;
   static final PyCode _nameOp$41;
   static final PyCode _implicitNameOp$42;
   static final PyCode set_lineno$43;
   static final PyCode visitModule$44;
   static final PyCode visitExpression$45;
   static final PyCode visitFunction$46;
   static final PyCode visitLambda$47;
   static final PyCode _visitFuncOrLambda$48;
   static final PyCode visitClass$49;
   static final PyCode visitIf$50;
   static final PyCode visitWhile$51;
   static final PyCode visitFor$52;
   static final PyCode visitBreak$53;
   static final PyCode visitContinue$54;
   static final PyCode visitTest$55;
   static final PyCode visitAnd$56;
   static final PyCode visitOr$57;
   static final PyCode visitIfExp$58;
   static final PyCode visitCompare$59;
   static final PyCode visitListComp$60;
   static final PyCode visitListCompFor$61;
   static final PyCode visitListCompIf$62;
   static final PyCode _makeClosure$63;
   static final PyCode visitGenExpr$64;
   static final PyCode visitGenExprInner$65;
   static final PyCode visitGenExprFor$66;
   static final PyCode visitGenExprIf$67;
   static final PyCode visitAssert$68;
   static final PyCode visitRaise$69;
   static final PyCode visitTryExcept$70;
   static final PyCode visitTryFinally$71;
   static final PyCode visitWith$72;
   static final PyCode visitDiscard$73;
   static final PyCode visitConst$74;
   static final PyCode visitKeyword$75;
   static final PyCode visitGlobal$76;
   static final PyCode visitName$77;
   static final PyCode visitPass$78;
   static final PyCode visitImport$79;
   static final PyCode visitFrom$80;
   static final PyCode f$81;
   static final PyCode _resolveDots$82;
   static final PyCode visitGetattr$83;
   static final PyCode visitAssign$84;
   static final PyCode visitAssName$85;
   static final PyCode visitAssAttr$86;
   static final PyCode _visitAssSequence$87;
   static final PyCode visitAssTuple$88;
   static final PyCode visitAssList$89;
   static final PyCode visitAugAssign$90;
   static final PyCode visitAugName$91;
   static final PyCode visitAugGetattr$92;
   static final PyCode visitAugSlice$93;
   static final PyCode visitAugSubscript$94;
   static final PyCode visitExec$95;
   static final PyCode visitCallFunc$96;
   static final PyCode visitPrint$97;
   static final PyCode visitPrintnl$98;
   static final PyCode visitReturn$99;
   static final PyCode visitYield$100;
   static final PyCode visitSlice$101;
   static final PyCode visitSubscript$102;
   static final PyCode binaryOp$103;
   static final PyCode visitAdd$104;
   static final PyCode visitSub$105;
   static final PyCode visitMul$106;
   static final PyCode visitDiv$107;
   static final PyCode visitFloorDiv$108;
   static final PyCode visitMod$109;
   static final PyCode visitPower$110;
   static final PyCode visitLeftShift$111;
   static final PyCode visitRightShift$112;
   static final PyCode unaryOp$113;
   static final PyCode visitInvert$114;
   static final PyCode visitUnarySub$115;
   static final PyCode visitUnaryAdd$116;
   static final PyCode visitUnaryInvert$117;
   static final PyCode visitNot$118;
   static final PyCode visitBackquote$119;
   static final PyCode bitOp$120;
   static final PyCode visitBitand$121;
   static final PyCode visitBitor$122;
   static final PyCode visitBitxor$123;
   static final PyCode visitEllipsis$124;
   static final PyCode visitTuple$125;
   static final PyCode visitList$126;
   static final PyCode visitSliceobj$127;
   static final PyCode visitDict$128;
   static final PyCode NestedScopeMixin$129;
   static final PyCode initClass$130;
   static final PyCode ModuleCodeGenerator$131;
   static final PyCode __init__$132;
   static final PyCode get_module$133;
   static final PyCode ExpressionCodeGenerator$134;
   static final PyCode __init__$135;
   static final PyCode get_module$136;
   static final PyCode InteractiveCodeGenerator$137;
   static final PyCode __init__$138;
   static final PyCode get_module$139;
   static final PyCode visitDiscard$140;
   static final PyCode AbstractFunctionCode$141;
   static final PyCode __init__$142;
   static final PyCode get_module$143;
   static final PyCode finish$144;
   static final PyCode generateArgUnpack$145;
   static final PyCode unpackSequence$146;
   static final PyCode FunctionCodeGenerator$147;
   static final PyCode __init__$148;
   static final PyCode GenExprCodeGenerator$149;
   static final PyCode __init__$150;
   static final PyCode AbstractClassCode$151;
   static final PyCode __init__$152;
   static final PyCode get_module$153;
   static final PyCode finish$154;
   static final PyCode ClassCodeGenerator$155;
   static final PyCode __init__$156;
   static final PyCode generateArgList$157;
   static final PyCode findOp$158;
   static final PyCode OpFinder$159;
   static final PyCode __init__$160;
   static final PyCode visitAssName$161;
   static final PyCode Delegator$162;
   static final PyCode __init__$163;
   static final PyCode __getattr__$164;
   static final PyCode AugGetattr$165;
   static final PyCode AugName$166;
   static final PyCode AugSlice$167;
   static final PyCode AugSubscript$168;
   static final PyCode wrap_aug$169;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("marshal", var1, -1);
      var1.setlocal("marshal", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(6);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(7);
      var3 = var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
      var1.setlocal("is_jython", var3);
      var3 = null;
      var1.setline(9);
      var6 = new String[]{"ast", "parse", "walk", "syntax"};
      var7 = imp.importFrom("compiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("ast", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("parse", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("walk", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("syntax", var4);
      var4 = null;
      var1.setline(10);
      var6 = new String[]{"misc", "future", "symbols"};
      var7 = imp.importFrom("compiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("misc", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("future", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("symbols", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"SC_LOCAL", "SC_GLOBAL_IMPLICIT", "SC_GLOBAL_EXPLICIT", "SC_FREE", "SC_CELL"};
      var7 = imp.importFrom("compiler.consts", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("SC_LOCAL", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("SC_GLOBAL_IMPLICIT", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("SC_GLOBAL_EXPLICIT", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("SC_FREE", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("SC_CELL", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"CO_VARARGS", "CO_VARKEYWORDS", "CO_NEWLOCALS", "CO_NESTED", "CO_GENERATOR", "CO_FUTURE_DIVISION", "CO_FUTURE_ABSIMPORT", "CO_FUTURE_WITH_STATEMENT", "CO_FUTURE_PRINT_FUNCTION"};
      var7 = imp.importFrom("compiler.consts", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("CO_VARARGS", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("CO_VARKEYWORDS", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("CO_NEWLOCALS", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("CO_NESTED", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("CO_GENERATOR", var4);
      var4 = null;
      var4 = var7[5];
      var1.setlocal("CO_FUTURE_DIVISION", var4);
      var4 = null;
      var4 = var7[6];
      var1.setlocal("CO_FUTURE_ABSIMPORT", var4);
      var4 = null;
      var4 = var7[7];
      var1.setlocal("CO_FUTURE_WITH_STATEMENT", var4);
      var4 = null;
      var4 = var7[8];
      var1.setlocal("CO_FUTURE_PRINT_FUNCTION", var4);
      var4 = null;
      var1.setline(16);
      if (var1.getname("is_jython").__not__().__nonzero__()) {
         var1.setline(17);
         var6 = new String[]{"TupleArg"};
         var7 = imp.importFrom("compiler.pyassem", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("TupleArg", var4);
         var4 = null;
      } else {
         var1.setline(19);
         var3 = var1.getname("None");
         var1.setlocal("TupleArg", var3);
         var3 = null;
      }

      try {
         var1.setline(24);
         var3 = var1.getname("sys").__getattr__("version_info").__getitem__(Py.newInteger(0));
         var1.setlocal("VERSION", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("AttributeError"))) {
            throw var8;
         }

         var1.setline(26);
         PyInteger var12 = Py.newInteger(1);
         var1.setlocal("VERSION", var12);
         var4 = null;
      }

      var1.setline(28);
      PyDictionary var9 = new PyDictionary(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0)}), PyString.fromInterned("CALL_FUNCTION"), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0)}), PyString.fromInterned("CALL_FUNCTION_VAR"), new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1)}), PyString.fromInterned("CALL_FUNCTION_KW"), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(1)}), PyString.fromInterned("CALL_FUNCTION_VAR_KW")});
      var1.setlocal("callfunc_opcode_info", var9);
      var3 = null;
      var1.setline(36);
      PyInteger var10 = Py.newInteger(1);
      var1.setlocal("LOOP", var10);
      var3 = null;
      var1.setline(37);
      var10 = Py.newInteger(2);
      var1.setlocal("EXCEPT", var10);
      var3 = null;
      var1.setline(38);
      var10 = Py.newInteger(3);
      var1.setlocal("TRY_FINALLY", var10);
      var3 = null;
      var1.setline(39);
      var10 = Py.newInteger(4);
      var1.setlocal("END_FINALLY", var10);
      var3 = null;
      var1.setline(41);
      var7 = new PyObject[]{Py.newInteger(0)};
      PyFunction var11 = new PyFunction(var1.f_globals, var7, compileFile$1, (PyObject)null);
      var1.setlocal("compileFile", var11);
      var3 = null;
      var1.setline(55);
      if (var1.getname("is_jython").__nonzero__()) {
         var1.setline(57);
         var3 = var1.getname("compile");
         var1.setlocal("compile", var3);
         var3 = null;
      } else {
         var1.setline(59);
         var7 = new PyObject[]{var1.getname("None"), var1.getname("None")};
         var11 = new PyFunction(var1.f_globals, var7, compile$2, PyString.fromInterned("Replacement for builtin compile() function"));
         var1.setlocal("compile", var11);
         var3 = null;
      }

      var1.setline(76);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("AbstractCompileMode", var7, AbstractCompileMode$3);
      var1.setlocal("AbstractCompileMode", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(97);
      var7 = new PyObject[]{var1.getname("AbstractCompileMode")};
      var4 = Py.makeClass("Expression", var7, Expression$8);
      var1.setlocal("Expression", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(106);
      var7 = new PyObject[]{var1.getname("AbstractCompileMode")};
      var4 = Py.makeClass("Interactive", var7, Interactive$10);
      var1.setlocal("Interactive", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(115);
      var7 = new PyObject[]{var1.getname("AbstractCompileMode")};
      var4 = Py.makeClass("Module", var7, Module$12);
      var1.setlocal("Module", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(142);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("LocalNameFinder", var7, LocalNameFinder$16);
      var1.setlocal("LocalNameFinder", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(185);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, is_constant_false$27, (PyObject)null);
      var1.setlocal("is_constant_false", var11);
      var3 = null;
      var1.setline(191);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("CodeGenerator", var7, CodeGenerator$28);
      var1.setlocal("CodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1279);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("NestedScopeMixin", var7, NestedScopeMixin$129);
      var1.setlocal("NestedScopeMixin", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1286);
      var7 = new PyObject[]{var1.getname("NestedScopeMixin"), var1.getname("CodeGenerator")};
      var4 = Py.makeClass("ModuleCodeGenerator", var7, ModuleCodeGenerator$131);
      var1.setlocal("ModuleCodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1300);
      var7 = new PyObject[]{var1.getname("NestedScopeMixin"), var1.getname("CodeGenerator")};
      var4 = Py.makeClass("ExpressionCodeGenerator", var7, ExpressionCodeGenerator$134);
      var1.setlocal("ExpressionCodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1314);
      var7 = new PyObject[]{var1.getname("NestedScopeMixin"), var1.getname("CodeGenerator")};
      var4 = Py.makeClass("InteractiveCodeGenerator", var7, InteractiveCodeGenerator$137);
      var1.setlocal("InteractiveCodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1337);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("AbstractFunctionCode", var7, AbstractFunctionCode$141);
      var1.setlocal("AbstractFunctionCode", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1399);
      var7 = new PyObject[]{var1.getname("NestedScopeMixin"), var1.getname("AbstractFunctionCode"), var1.getname("CodeGenerator")};
      var4 = Py.makeClass("FunctionCodeGenerator", var7, FunctionCodeGenerator$147);
      var1.setlocal("FunctionCodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1415);
      var7 = new PyObject[]{var1.getname("NestedScopeMixin"), var1.getname("AbstractFunctionCode"), var1.getname("CodeGenerator")};
      var4 = Py.makeClass("GenExprCodeGenerator", var7, GenExprCodeGenerator$149);
      var1.setlocal("GenExprCodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1430);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("AbstractClassCode", var7, AbstractClassCode$151);
      var1.setlocal("AbstractClassCode", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1452);
      var7 = new PyObject[]{var1.getname("NestedScopeMixin"), var1.getname("AbstractClassCode"), var1.getname("CodeGenerator")};
      var4 = Py.makeClass("ClassCodeGenerator", var7, ClassCodeGenerator$155);
      var1.setlocal("ClassCodeGenerator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1471);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, generateArgList$157, PyString.fromInterned("Generate an arg list marking TupleArgs"));
      var1.setlocal("generateArgList", var11);
      var3 = null;
      var1.setline(1488);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, findOp$158, PyString.fromInterned("Find the op (DELETE, LOAD, STORE) in an AssTuple tree"));
      var1.setlocal("findOp", var11);
      var3 = null;
      var1.setline(1494);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("OpFinder", var7, OpFinder$159);
      var1.setlocal("OpFinder", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1505);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Delegator", var7, Delegator$162);
      var1.setlocal("Delegator", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1522);
      var7 = new PyObject[]{var1.getname("Delegator")};
      var4 = Py.makeClass("AugGetattr", var7, AugGetattr$165);
      var1.setlocal("AugGetattr", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1525);
      var7 = new PyObject[]{var1.getname("Delegator")};
      var4 = Py.makeClass("AugName", var7, AugName$166);
      var1.setlocal("AugName", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1528);
      var7 = new PyObject[]{var1.getname("Delegator")};
      var4 = Py.makeClass("AugSlice", var7, AugSlice$167);
      var1.setlocal("AugSlice", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1531);
      var7 = new PyObject[]{var1.getname("Delegator")};
      var4 = Py.makeClass("AugSubscript", var7, AugSubscript$168);
      var1.setlocal("AugSubscript", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1534);
      var9 = new PyDictionary(new PyObject[]{var1.getname("ast").__getattr__("Getattr"), var1.getname("AugGetattr"), var1.getname("ast").__getattr__("Name"), var1.getname("AugName"), var1.getname("ast").__getattr__("Slice"), var1.getname("AugSlice"), var1.getname("ast").__getattr__("Subscript"), var1.getname("AugSubscript")});
      var1.setlocal("wrapper", var9);
      var3 = null;
      var1.setline(1541);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, wrap_aug$169, (PyObject)null);
      var1.setlocal("wrap_aug", var11);
      var3 = null;
      var1.setline(1544);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1545);
         var3 = var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(1545);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal("file", var4);
            var1.setline(1546);
            var1.getname("compileFile").__call__(var2, var1.getname("file"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compileFile$1(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("U"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getlocal(2).__getattr__("read").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(44);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(45);
      var3 = var1.getglobal("Module").__call__(var2, var1.getlocal(3), var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;

      try {
         var1.setline(47);
         var1.getlocal(4).__getattr__("compile").__call__(var2, var1.getlocal(1));
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("SyntaxError"))) {
            var1.setline(49);
            throw Py.makeException();
         }

         throw var6;
      }

      var1.setline(51);
      PyObject var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0)._add(PyString.fromInterned("c")), (PyObject)PyString.fromInterned("wb"));
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(52);
      var1.getlocal(4).__getattr__("dump").__call__(var2, var1.getlocal(2));
      var1.setline(53);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile$2(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Replacement for builtin compile() function");
      var1.setline(61);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(62);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("not implemented yet"));
      } else {
         var1.setline(64);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("single"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(65);
            var3 = var1.getglobal("Interactive").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(66);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned("exec"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(67);
               var3 = var1.getglobal("Module").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(68);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(PyString.fromInterned("eval"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(71);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("compile() 3rd arg must be 'exec' or 'eval' or 'single'")));
               }

               var1.setline(69);
               var3 = var1.getglobal("Expression").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setlocal(5, var3);
               var3 = null;
            }
         }

         var1.setline(73);
         var1.getlocal(5).__getattr__("compile").__call__(var2);
         var1.setline(74);
         var3 = var1.getlocal(5).__getattr__("code");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject AbstractCompileMode$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(78);
      PyObject var3 = var1.getname("None");
      var1.setlocal("mode", var3);
      var3 = null;
      var1.setline(80);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(85);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_tree$5, (PyObject)null);
      var1.setlocal("_get_tree", var5);
      var3 = null;
      var1.setline(91);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, compile$6, (PyObject)null);
      var1.setlocal("compile", var5);
      var3 = null;
      var1.setline(94);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getCode$7, (PyObject)null);
      var1.setlocal("getCode", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("source", var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_tree$5(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getglobal("parse").__call__(var2, var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("mode"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(87);
      var1.getglobal("misc").__getattr__("set_filename").__call__(var2, var1.getlocal(0).__getattr__("filename"), var1.getlocal(1));
      var1.setline(88);
      var1.getglobal("syntax").__getattr__("check").__call__(var2, var1.getlocal(1));
      var1.setline(89);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compile$6(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getCode$7(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getlocal(0).__getattr__("code");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Expression$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(99);
      PyString var3 = PyString.fromInterned("eval");
      var1.setlocal("mode", var3);
      var3 = null;
      var1.setline(101);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, compile$9, (PyObject)null);
      var1.setlocal("compile", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject compile$9(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_tree").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getglobal("ExpressionCodeGenerator").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getlocal(2).__getattr__("getCode").__call__(var2);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Interactive$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(108);
      PyString var3 = PyString.fromInterned("single");
      var1.setlocal("mode", var3);
      var3 = null;
      var1.setline(110);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, compile$11, (PyObject)null);
      var1.setlocal("compile", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject compile$11(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_tree").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getglobal("InteractiveCodeGenerator").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getlocal(2).__getattr__("getCode").__call__(var2);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(117);
      PyString var3 = PyString.fromInterned("exec");
      var1.setlocal("mode", var3);
      var3 = null;
      var1.setline(119);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, compile$13, (PyObject)null);
      var1.setlocal("compile", var5);
      var3 = null;
      var1.setline(127);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dump$14, (PyObject)null);
      var1.setlocal("dump", var5);
      var3 = null;
      var1.setline(131);
      var1.setline(131);
      PyObject var6 = var1.getname("is_jython").__nonzero__() ? var1.getname("None") : var1.getname("imp").__getattr__("get_magic").__call__(var2);
      var1.setlocal("MAGIC", var6);
      var3 = null;
      var1.setline(133);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getPycHeader$15, (PyObject)null);
      var1.setlocal("getPycHeader", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject compile$13(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_tree").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getglobal("ModuleCodeGenerator").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(122);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(123);
         var3 = imp.importOne("pprint", var1, -1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(124);
         Py.println(var1.getlocal(4).__getattr__("pprint").__call__(var2, var1.getlocal(2)));
      }

      var1.setline(125);
      var3 = var1.getlocal(3).__getattr__("getCode").__call__(var2);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump$14(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("getPycHeader").__call__(var2));
      var1.setline(129);
      var1.getglobal("marshal").__getattr__("dump").__call__(var2, var1.getlocal(0).__getattr__("code"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getPycHeader$15(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("getmtime").__call__(var2, var1.getlocal(0).__getattr__("filename"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(139);
      var3 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(140);
      var3 = var1.getlocal(0).__getattr__("MAGIC")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LocalNameFinder$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Find local names in scope"));
      var1.setline(143);
      PyString.fromInterned("Find local names in scope");
      var1.setline(144);
      PyObject[] var3 = new PyObject[]{new PyTuple(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLocals$18, (PyObject)null);
      var1.setlocal("getLocals", var4);
      var3 = null;
      var1.setline(158);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitDict$19, (PyObject)null);
      var1.setlocal("visitDict", var4);
      var3 = null;
      var1.setline(161);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitGlobal$20, (PyObject)null);
      var1.setlocal("visitGlobal", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitFunction$21, (PyObject)null);
      var1.setlocal("visitFunction", var4);
      var3 = null;
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitLambda$22, (PyObject)null);
      var1.setlocal("visitLambda", var4);
      var3 = null;
      var1.setline(171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitImport$23, (PyObject)null);
      var1.setlocal("visitImport", var4);
      var3 = null;
      var1.setline(175);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitFrom$24, (PyObject)null);
      var1.setlocal("visitFrom", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitClass$25, (PyObject)null);
      var1.setlocal("visitClass", var4);
      var3 = null;
      var1.setline(182);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitAssName$26, (PyObject)null);
      var1.setlocal("visitAssName", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyObject var3 = var1.getglobal("misc").__getattr__("Set").__call__(var2);
      var1.getlocal(0).__setattr__("names", var3);
      var3 = null;
      var1.setline(146);
      var3 = var1.getglobal("misc").__getattr__("Set").__call__(var2);
      var1.getlocal(0).__setattr__("globals", var3);
      var3 = null;
      var1.setline(147);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(147);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(148);
         var1.getlocal(0).__getattr__("names").__getattr__("add").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject getLocals$18(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject var3 = var1.getlocal(0).__getattr__("globals").__getattr__("elements").__call__(var2).__iter__();

      while(true) {
         var1.setline(153);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(156);
            var3 = var1.getlocal(0).__getattr__("names");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(154);
         if (var1.getlocal(0).__getattr__("names").__getattr__("has_elt").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(155);
            var1.getlocal(0).__getattr__("names").__getattr__("remove").__call__(var2, var1.getlocal(1));
         }
      }
   }

   public PyObject visitDict$19(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitGlobal$20(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(162);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(163);
         var1.getlocal(0).__getattr__("globals").__getattr__("add").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject visitFunction$21(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      var1.getlocal(0).__getattr__("names").__getattr__("add").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitLambda$22(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitImport$23(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(172);
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
         var1.setline(173);
         PyObject var10000 = var1.getlocal(0).__getattr__("names").__getattr__("add");
         PyObject var10002 = var1.getlocal(3);
         if (!var10002.__nonzero__()) {
            var10002 = var1.getlocal(2);
         }

         var10000.__call__(var2, var10002);
      }
   }

   public PyObject visitFrom$24(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyObject var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(176);
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
         var1.setline(177);
         PyObject var10000 = var1.getlocal(0).__getattr__("names").__getattr__("add");
         PyObject var10002 = var1.getlocal(3);
         if (!var10002.__nonzero__()) {
            var10002 = var1.getlocal(2);
         }

         var10000.__call__(var2, var10002);
      }
   }

   public PyObject visitClass$25(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      var1.getlocal(0).__getattr__("names").__getattr__("add").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssName$26(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      var1.getlocal(0).__getattr__("names").__getattr__("add").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_constant_false$27(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyInteger var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("ast").__getattr__("Const")).__nonzero__()) {
         var1.setline(187);
         if (var1.getlocal(0).__getattr__("value").__not__().__nonzero__()) {
            var1.setline(188);
            var3 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(189);
      var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CodeGenerator$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Defines basic code generator for Python bytecode\n\n    This class is an abstract base class.  Concrete subclasses must\n    define an __init__() that defines self.graph and then calls the\n    __init__() defined in this class.\n\n    The concrete class must also define the class attributes\n    NameFinder, FunctionGen, and ClassGen.  These attributes can be\n    defined in the initClass() method, which is a hook for\n    initializing these methods after all the classes have been\n    defined.\n    "));
      var1.setline(203);
      PyString.fromInterned("Defines basic code generator for Python bytecode\n\n    This class is an abstract base class.  Concrete subclasses must\n    define an __init__() that defines self.graph and then calls the\n    __init__() defined in this class.\n\n    The concrete class must also define the class attributes\n    NameFinder, FunctionGen, and ClassGen.  These attributes can be\n    defined in the initClass() method, which is a hook for\n    initializing these methods after all the classes have been\n    defined.\n    ");
      var1.setline(205);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("optimized", var3);
      var3 = null;
      var1.setline(206);
      PyObject var4 = var1.getname("None");
      var1.setlocal("_CodeGenerator__initialized", var4);
      var3 = null;
      var1.setline(207);
      var4 = var1.getname("None");
      var1.setlocal("class_name", var4);
      var3 = null;
      var1.setline(209);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$29, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(233);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, initClass$30, PyString.fromInterned("This method is called once for each class"));
      var1.setlocal("initClass", var6);
      var3 = null;
      var1.setline(236);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, checkClass$31, PyString.fromInterned("Verify that class is constructed correctly"));
      var1.setlocal("checkClass", var6);
      var3 = null;
      var1.setline(247);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _setupGraphDelegation$32, (PyObject)null);
      var1.setlocal("_setupGraphDelegation", var6);
      var3 = null;
      var1.setline(254);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getCode$33, PyString.fromInterned("Return a code object"));
      var1.setlocal("getCode", var6);
      var3 = null;
      var1.setline(258);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, mangle$34, (PyObject)null);
      var1.setlocal("mangle", var6);
      var3 = null;
      var1.setline(264);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parseSymbols$35, (PyObject)null);
      var1.setlocal("parseSymbols", var6);
      var3 = null;
      var1.setline(269);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_module$36, (PyObject)null);
      var1.setlocal("get_module", var6);
      var3 = null;
      var1.setline(274);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, isLocalName$37, (PyObject)null);
      var1.setlocal("isLocalName", var6);
      var3 = null;
      var1.setline(277);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, storeName$38, (PyObject)null);
      var1.setlocal("storeName", var6);
      var3 = null;
      var1.setline(280);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, loadName$39, (PyObject)null);
      var1.setlocal("loadName", var6);
      var3 = null;
      var1.setline(283);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, delName$40, (PyObject)null);
      var1.setlocal("delName", var6);
      var3 = null;
      var1.setline(286);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _nameOp$41, (PyObject)null);
      var1.setlocal("_nameOp", var6);
      var3 = null;
      var1.setline(307);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _implicitNameOp$42, PyString.fromInterned("Emit name ops for names generated implicitly by for loops\n\n        The interpreter generates names that start with a period or\n        dollar sign.  The symbol table ignores these names because\n        they aren't present in the program text.\n        "));
      var1.setlocal("_implicitNameOp", var6);
      var3 = null;
      var1.setline(324);
      var5 = new PyObject[]{var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var5, set_lineno$43, PyString.fromInterned("Emit SET_LINENO if necessary.\n\n        The instruction is considered necessary if the node has a\n        lineno attribute and it is different than the last lineno\n        emitted.\n\n        Returns true if SET_LINENO was emitted.\n\n        There are no rules for when an AST node should have a lineno\n        attribute.  The transformer and AST code need to be reviewed\n        and a consistent policy implemented and documented.  Until\n        then, this method works around missing line numbers.\n        "));
      var1.setlocal("set_lineno", var6);
      var3 = null;
      var1.setline(350);
      var4 = var1.getname("LocalNameFinder");
      var1.setlocal("NameFinder", var4);
      var3 = null;
      var1.setline(351);
      var4 = var1.getname("None");
      var1.setlocal("FunctionGen", var4);
      var3 = null;
      var1.setline(352);
      var4 = var1.getname("None");
      var1.setlocal("ClassGen", var4);
      var3 = null;
      var1.setline(354);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitModule$44, (PyObject)null);
      var1.setlocal("visitModule", var6);
      var3 = null;
      var1.setline(367);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitExpression$45, (PyObject)null);
      var1.setlocal("visitExpression", var6);
      var3 = null;
      var1.setline(374);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitFunction$46, (PyObject)null);
      var1.setlocal("visitFunction", var6);
      var3 = null;
      var1.setline(380);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitLambda$47, (PyObject)null);
      var1.setlocal("visitLambda", var6);
      var3 = null;
      var1.setline(383);
      var5 = new PyObject[]{Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, _visitFuncOrLambda$48, (PyObject)null);
      var1.setlocal("_visitFuncOrLambda", var6);
      var3 = null;
      var1.setline(402);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitClass$49, (PyObject)null);
      var1.setlocal("visitClass", var6);
      var3 = null;
      var1.setline(421);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitIf$50, (PyObject)null);
      var1.setlocal("visitIf", var6);
      var3 = null;
      var1.setline(443);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitWhile$51, (PyObject)null);
      var1.setlocal("visitWhile", var6);
      var3 = null;
      var1.setline(472);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitFor$52, (PyObject)null);
      var1.setlocal("visitFor", var6);
      var3 = null;
      var1.setline(496);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitBreak$53, (PyObject)null);
      var1.setlocal("visitBreak", var6);
      var3 = null;
      var1.setline(503);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitContinue$54, (PyObject)null);
      var1.setlocal("visitContinue", var6);
      var3 = null;
      var1.setline(530);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitTest$55, (PyObject)null);
      var1.setlocal("visitTest", var6);
      var3 = null;
      var1.setline(540);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAnd$56, (PyObject)null);
      var1.setlocal("visitAnd", var6);
      var3 = null;
      var1.setline(543);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitOr$57, (PyObject)null);
      var1.setlocal("visitOr", var6);
      var3 = null;
      var1.setline(546);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitIfExp$58, (PyObject)null);
      var1.setlocal("visitIfExp", var6);
      var3 = null;
      var1.setline(559);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitCompare$59, (PyObject)null);
      var1.setlocal("visitCompare", var6);
      var3 = null;
      var1.setline(584);
      var3 = Py.newInteger(0);
      var1.setlocal("_CodeGenerator__list_count", var3);
      var3 = null;
      var1.setline(586);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitListComp$60, (PyObject)null);
      var1.setlocal("visitListComp", var6);
      var3 = null;
      var1.setline(624);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitListCompFor$61, (PyObject)null);
      var1.setlocal("visitListCompFor", var6);
      var3 = null;
      var1.setline(637);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitListCompIf$62, (PyObject)null);
      var1.setlocal("visitListCompIf", var6);
      var3 = null;
      var1.setline(644);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _makeClosure$63, (PyObject)null);
      var1.setlocal("_makeClosure", var6);
      var3 = null;
      var1.setline(656);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitGenExpr$64, (PyObject)null);
      var1.setlocal("visitGenExpr", var6);
      var3 = null;
      var1.setline(668);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitGenExprInner$65, (PyObject)null);
      var1.setlocal("visitGenExprInner", var6);
      var3 = null;
      var1.setline(701);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitGenExprFor$66, (PyObject)null);
      var1.setlocal("visitGenExprFor", var6);
      var3 = null;
      var1.setline(722);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitGenExprIf$67, (PyObject)null);
      var1.setlocal("visitGenExprIf", var6);
      var3 = null;
      var1.setline(731);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAssert$68, (PyObject)null);
      var1.setlocal("visitAssert", var6);
      var3 = null;
      var1.setline(754);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitRaise$69, (PyObject)null);
      var1.setlocal("visitRaise", var6);
      var3 = null;
      var1.setline(768);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitTryExcept$70, (PyObject)null);
      var1.setlocal("visitTryExcept", var6);
      var3 = null;
      var1.setline(818);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitTryFinally$71, (PyObject)null);
      var1.setlocal("visitTryFinally", var6);
      var3 = null;
      var1.setline(835);
      var3 = Py.newInteger(0);
      var1.setlocal("_CodeGenerator__with_count", var3);
      var3 = null;
      var1.setline(837);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitWith$72, (PyObject)null);
      var1.setlocal("visitWith", var6);
      var3 = null;
      var1.setline(876);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitDiscard$73, (PyObject)null);
      var1.setlocal("visitDiscard", var6);
      var3 = null;
      var1.setline(881);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitConst$74, (PyObject)null);
      var1.setlocal("visitConst", var6);
      var3 = null;
      var1.setline(884);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitKeyword$75, (PyObject)null);
      var1.setlocal("visitKeyword", var6);
      var3 = null;
      var1.setline(888);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitGlobal$76, (PyObject)null);
      var1.setlocal("visitGlobal", var6);
      var3 = null;
      var1.setline(892);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitName$77, (PyObject)null);
      var1.setlocal("visitName", var6);
      var3 = null;
      var1.setline(896);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitPass$78, (PyObject)null);
      var1.setlocal("visitPass", var6);
      var3 = null;
      var1.setline(899);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitImport$79, (PyObject)null);
      var1.setlocal("visitImport", var6);
      var3 = null;
      var1.setline(914);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitFrom$80, (PyObject)null);
      var1.setlocal("visitFrom", var6);
      var3 = null;
      var1.setline(940);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _resolveDots$82, (PyObject)null);
      var1.setlocal("_resolveDots", var6);
      var3 = null;
      var1.setline(947);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitGetattr$83, (PyObject)null);
      var1.setlocal("visitGetattr", var6);
      var3 = null;
      var1.setline(953);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAssign$84, (PyObject)null);
      var1.setlocal("visitAssign", var6);
      var3 = null;
      var1.setline(964);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAssName$85, (PyObject)null);
      var1.setlocal("visitAssName", var6);
      var3 = null;
      var1.setline(973);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAssAttr$86, (PyObject)null);
      var1.setlocal("visitAssAttr", var6);
      var3 = null;
      var1.setline(983);
      var5 = new PyObject[]{PyString.fromInterned("UNPACK_SEQUENCE")};
      var6 = new PyFunction(var1.f_globals, var5, _visitAssSequence$87, (PyObject)null);
      var1.setlocal("_visitAssSequence", var6);
      var3 = null;
      var1.setline(989);
      var4 = var1.getname("VERSION");
      PyObject var10000 = var4._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(990);
         var4 = var1.getname("_visitAssSequence");
         var1.setlocal("visitAssTuple", var4);
         var3 = null;
         var1.setline(991);
         var4 = var1.getname("_visitAssSequence");
         var1.setlocal("visitAssList", var4);
         var3 = null;
      } else {
         var1.setline(993);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, visitAssTuple$88, (PyObject)null);
         var1.setlocal("visitAssTuple", var6);
         var3 = null;
         var1.setline(996);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, visitAssList$89, (PyObject)null);
         var1.setlocal("visitAssList", var6);
         var3 = null;
      }

      var1.setline(1001);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAugAssign$90, (PyObject)null);
      var1.setlocal("visitAugAssign", var6);
      var3 = null;
      var1.setline(1009);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("+="), PyString.fromInterned("INPLACE_ADD"), PyString.fromInterned("-="), PyString.fromInterned("INPLACE_SUBTRACT"), PyString.fromInterned("*="), PyString.fromInterned("INPLACE_MULTIPLY"), PyString.fromInterned("/="), PyString.fromInterned("INPLACE_DIVIDE"), PyString.fromInterned("//="), PyString.fromInterned("INPLACE_FLOOR_DIVIDE"), PyString.fromInterned("%="), PyString.fromInterned("INPLACE_MODULO"), PyString.fromInterned("**="), PyString.fromInterned("INPLACE_POWER"), PyString.fromInterned(">>="), PyString.fromInterned("INPLACE_RSHIFT"), PyString.fromInterned("<<="), PyString.fromInterned("INPLACE_LSHIFT"), PyString.fromInterned("&="), PyString.fromInterned("INPLACE_AND"), PyString.fromInterned("^="), PyString.fromInterned("INPLACE_XOR"), PyString.fromInterned("|="), PyString.fromInterned("INPLACE_OR")});
      var1.setlocal("_augmented_opcode", var7);
      var3 = null;
      var1.setline(1024);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAugName$91, (PyObject)null);
      var1.setlocal("visitAugName", var6);
      var3 = null;
      var1.setline(1030);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAugGetattr$92, (PyObject)null);
      var1.setlocal("visitAugGetattr", var6);
      var3 = null;
      var1.setline(1039);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAugSlice$93, (PyObject)null);
      var1.setlocal("visitAugSlice", var6);
      var3 = null;
      var1.setline(1056);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAugSubscript$94, (PyObject)null);
      var1.setlocal("visitAugSubscript", var6);
      var3 = null;
      var1.setline(1063);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitExec$95, (PyObject)null);
      var1.setlocal("visitExec", var6);
      var3 = null;
      var1.setline(1075);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitCallFunc$96, (PyObject)null);
      var1.setlocal("visitCallFunc", var6);
      var3 = null;
      var1.setline(1095);
      var5 = new PyObject[]{Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, visitPrint$97, (PyObject)null);
      var1.setlocal("visitPrint", var6);
      var3 = null;
      var1.setline(1111);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitPrintnl$98, (PyObject)null);
      var1.setlocal("visitPrintnl", var6);
      var3 = null;
      var1.setline(1118);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitReturn$99, (PyObject)null);
      var1.setlocal("visitReturn", var6);
      var3 = null;
      var1.setline(1123);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitYield$100, (PyObject)null);
      var1.setlocal("visitYield", var6);
      var3 = null;
      var1.setline(1130);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, visitSlice$101, (PyObject)null);
      var1.setlocal("visitSlice", var6);
      var3 = null;
      var1.setline(1157);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, visitSubscript$102, (PyObject)null);
      var1.setlocal("visitSubscript", var6);
      var3 = null;
      var1.setline(1174);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, binaryOp$103, (PyObject)null);
      var1.setlocal("binaryOp", var6);
      var3 = null;
      var1.setline(1179);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitAdd$104, (PyObject)null);
      var1.setlocal("visitAdd", var6);
      var3 = null;
      var1.setline(1182);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitSub$105, (PyObject)null);
      var1.setlocal("visitSub", var6);
      var3 = null;
      var1.setline(1185);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitMul$106, (PyObject)null);
      var1.setlocal("visitMul", var6);
      var3 = null;
      var1.setline(1188);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitDiv$107, (PyObject)null);
      var1.setlocal("visitDiv", var6);
      var3 = null;
      var1.setline(1191);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitFloorDiv$108, (PyObject)null);
      var1.setlocal("visitFloorDiv", var6);
      var3 = null;
      var1.setline(1194);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitMod$109, (PyObject)null);
      var1.setlocal("visitMod", var6);
      var3 = null;
      var1.setline(1197);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitPower$110, (PyObject)null);
      var1.setlocal("visitPower", var6);
      var3 = null;
      var1.setline(1200);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitLeftShift$111, (PyObject)null);
      var1.setlocal("visitLeftShift", var6);
      var3 = null;
      var1.setline(1203);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitRightShift$112, (PyObject)null);
      var1.setlocal("visitRightShift", var6);
      var3 = null;
      var1.setline(1208);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unaryOp$113, (PyObject)null);
      var1.setlocal("unaryOp", var6);
      var3 = null;
      var1.setline(1212);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitInvert$114, (PyObject)null);
      var1.setlocal("visitInvert", var6);
      var3 = null;
      var1.setline(1215);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitUnarySub$115, (PyObject)null);
      var1.setlocal("visitUnarySub", var6);
      var3 = null;
      var1.setline(1218);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitUnaryAdd$116, (PyObject)null);
      var1.setlocal("visitUnaryAdd", var6);
      var3 = null;
      var1.setline(1221);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitUnaryInvert$117, (PyObject)null);
      var1.setlocal("visitUnaryInvert", var6);
      var3 = null;
      var1.setline(1224);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitNot$118, (PyObject)null);
      var1.setlocal("visitNot", var6);
      var3 = null;
      var1.setline(1227);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitBackquote$119, (PyObject)null);
      var1.setlocal("visitBackquote", var6);
      var3 = null;
      var1.setline(1232);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, bitOp$120, (PyObject)null);
      var1.setlocal("bitOp", var6);
      var3 = null;
      var1.setline(1238);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitBitand$121, (PyObject)null);
      var1.setlocal("visitBitand", var6);
      var3 = null;
      var1.setline(1241);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitBitor$122, (PyObject)null);
      var1.setlocal("visitBitor", var6);
      var3 = null;
      var1.setline(1244);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitBitxor$123, (PyObject)null);
      var1.setlocal("visitBitxor", var6);
      var3 = null;
      var1.setline(1249);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitEllipsis$124, (PyObject)null);
      var1.setlocal("visitEllipsis", var6);
      var3 = null;
      var1.setline(1252);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitTuple$125, (PyObject)null);
      var1.setlocal("visitTuple", var6);
      var3 = null;
      var1.setline(1258);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitList$126, (PyObject)null);
      var1.setlocal("visitList", var6);
      var3 = null;
      var1.setline(1264);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitSliceobj$127, (PyObject)null);
      var1.setlocal("visitSliceobj", var6);
      var3 = null;
      var1.setline(1269);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitDict$128, (PyObject)null);
      var1.setlocal("visitDict", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyObject var3 = var1.getlocal(0).__getattr__("_CodeGenerator__initialized");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(211);
         var1.getlocal(0).__getattr__("initClass").__call__(var2);
         var1.setline(212);
         PyInteger var6 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("__class__").__setattr__((String)"_CodeGenerator__initialized", var6);
         var3 = null;
      }

      var1.setline(213);
      var1.getlocal(0).__getattr__("checkClass").__call__(var2);
      var1.setline(214);
      var3 = var1.getglobal("misc").__getattr__("Stack").__call__(var2);
      var1.getlocal(0).__setattr__("locals", var3);
      var3 = null;
      var1.setline(215);
      var3 = var1.getglobal("misc").__getattr__("Stack").__call__(var2);
      var1.getlocal(0).__setattr__("setups", var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("last_lineno", var3);
      var3 = null;
      var1.setline(217);
      var1.getlocal(0).__getattr__("_setupGraphDelegation").__call__(var2);
      var1.setline(218);
      PyString var8 = PyString.fromInterned("BINARY_DIVIDE");
      var1.getlocal(0).__setattr__((String)"_div_op", var8);
      var3 = null;
      var1.setline(221);
      var3 = var1.getlocal(0).__getattr__("get_module").__call__(var2).__getattr__("futures");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(222);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(222);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(223);
         PyObject var5 = var1.getlocal(2);
         var10000 = var5._eq(PyString.fromInterned("division"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(224);
            var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_FUTURE_DIVISION"));
            var1.setline(225);
            PyString var7 = PyString.fromInterned("BINARY_TRUE_DIVIDE");
            var1.getlocal(0).__setattr__((String)"_div_op", var7);
            var5 = null;
         } else {
            var1.setline(226);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(PyString.fromInterned("absolute_import"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(227);
               var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_FUTURE_ABSIMPORT"));
            } else {
               var1.setline(228);
               var5 = var1.getlocal(2);
               var10000 = var5._eq(PyString.fromInterned("with_statement"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(229);
                  var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_FUTURE_WITH_STATEMENT"));
               } else {
                  var1.setline(230);
                  var5 = var1.getlocal(2);
                  var10000 = var5._eq(PyString.fromInterned("print_function"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(231);
                     var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_FUTURE_PRINT_FUNCTION"));
                  }
               }
            }
         }
      }
   }

   public PyObject initClass$30(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyString.fromInterned("This method is called once for each class");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject checkClass$31(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyString.fromInterned("Verify that class is constructed correctly");

      try {
         var1.setline(239);
         PyObject var10000;
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("graph")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(240);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("getattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("NameFinder")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(241);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("getattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("FunctionGen")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }

         var1.setline(242);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("getattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("ClassGen")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AssertionError"))) {
            PyObject var4 = var3.value;
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(244);
            var4 = PyString.fromInterned("Bad class construction for %s")._mod(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(245);
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(2));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setupGraphDelegation$32(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyObject var3 = var1.getlocal(0).__getattr__("graph").__getattr__("emit");
      var1.getlocal(0).__setattr__("emit", var3);
      var3 = null;
      var1.setline(249);
      var3 = var1.getlocal(0).__getattr__("graph").__getattr__("newBlock");
      var1.getlocal(0).__setattr__("newBlock", var3);
      var3 = null;
      var1.setline(250);
      var3 = var1.getlocal(0).__getattr__("graph").__getattr__("startBlock");
      var1.getlocal(0).__setattr__("startBlock", var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getlocal(0).__getattr__("graph").__getattr__("nextBlock");
      var1.getlocal(0).__setattr__("nextBlock", var3);
      var3 = null;
      var1.setline(252);
      var3 = var1.getlocal(0).__getattr__("graph").__getattr__("setDocstring");
      var1.getlocal(0).__setattr__("setDocstring", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getCode$33(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyString.fromInterned("Return a code object");
      var1.setline(256);
      PyObject var3 = var1.getlocal(0).__getattr__("graph").__getattr__("getCode").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mangle$34(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyObject var3 = var1.getlocal(0).__getattr__("class_name");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(260);
         var3 = var1.getglobal("misc").__getattr__("mangle").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("class_name"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(262);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parseSymbols$35(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var3 = var1.getglobal("symbols").__getattr__("SymbolVisitor").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(266);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(267);
      var3 = var1.getlocal(2).__getattr__("scopes");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_module$36(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("should be implemented by subclasses"));
   }

   public PyObject isLocalName$37(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyObject var3 = var1.getlocal(0).__getattr__("locals").__getattr__("top").__call__(var2).__getattr__("has_elt").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject storeName$38(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      var1.getlocal(0).__getattr__("_nameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject loadName$39(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      var1.getlocal(0).__getattr__("_nameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject delName$40(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      var1.getlocal(0).__getattr__("_nameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _nameOp$41(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(288);
      var3 = var1.getlocal(0).__getattr__("scope").__getattr__("check_name").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(289);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getglobal("SC_LOCAL"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         if (var1.getlocal(0).__getattr__("optimized").__not__().__nonzero__()) {
            var1.setline(291);
            var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_NAME")), var1.getlocal(2));
         } else {
            var1.setline(293);
            var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_FAST")), var1.getlocal(2));
         }
      } else {
         var1.setline(294);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getglobal("SC_GLOBAL_EXPLICIT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(295);
            var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_GLOBAL")), var1.getlocal(2));
         } else {
            var1.setline(296);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(var1.getglobal("SC_GLOBAL_IMPLICIT"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(297);
               if (var1.getlocal(0).__getattr__("optimized").__not__().__nonzero__()) {
                  var1.setline(298);
                  var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_NAME")), var1.getlocal(2));
               } else {
                  var1.setline(300);
                  var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_GLOBAL")), var1.getlocal(2));
               }
            } else {
               var1.setline(301);
               var3 = var1.getlocal(3);
               var10000 = var3._eq(var1.getglobal("SC_FREE"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(3);
                  var10000 = var3._eq(var1.getglobal("SC_CELL"));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(304);
                  throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("unsupported scope for var %s: %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
               }

               var1.setline(302);
               var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_DEREF")), var1.getlocal(2));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _implicitNameOp$42(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyString.fromInterned("Emit name ops for names generated implicitly by for loops\n\n        The interpreter generates names that start with a period or\n        dollar sign.  The symbol table ignores these names because\n        they aren't present in the program text.\n        ");
      var1.setline(314);
      if (var1.getlocal(0).__getattr__("optimized").__nonzero__()) {
         var1.setline(315);
         var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_FAST")), var1.getlocal(2));
      } else {
         var1.setline(317);
         var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("_NAME")), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_lineno$43(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyString.fromInterned("Emit SET_LINENO if necessary.\n\n        The instruction is considered necessary if the node has a\n        lineno attribute and it is different than the last lineno\n        emitted.\n\n        Returns true if SET_LINENO was emitted.\n\n        There are no rules for when an AST node should have a lineno\n        attribute.  The transformer and AST code need to be reviewed\n        and a consistent policy implemented and documented.  Until\n        then, this method works around missing line numbers.\n        ");
      var1.setline(338);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("lineno"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(339);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("last_lineno"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(341);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SET_LINENO"), (PyObject)var1.getlocal(3));
         var1.setline(342);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("last_lineno", var3);
         var3 = null;
         var1.setline(343);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(344);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject visitModule$44(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyObject var3 = var1.getlocal(0).__getattr__("parseSymbols").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("scopes", var3);
      var3 = null;
      var1.setline(356);
      var3 = var1.getlocal(0).__getattr__("scopes").__getitem__(var1.getlocal(1));
      var1.getlocal(0).__setattr__("scope", var3);
      var3 = null;
      var1.setline(357);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SET_LINENO"), (PyObject)Py.newInteger(0));
      var1.setline(358);
      if (var1.getlocal(1).__getattr__("doc").__nonzero__()) {
         var1.setline(359);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1).__getattr__("doc"));
         var1.setline(360);
         var1.getlocal(0).__getattr__("storeName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__doc__"));
      }

      var1.setline(361);
      PyObject var10000 = var1.getglobal("walk");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1).__getattr__("node"), var1.getlocal(0).__getattr__("NameFinder").__call__(var2), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(362);
      var1.getlocal(0).__getattr__("locals").__getattr__("push").__call__(var2, var1.getlocal(2).__getattr__("getLocals").__call__(var2));
      var1.setline(363);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("node"));
      var1.setline(364);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
      var1.setline(365);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitExpression$45(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(369);
      PyObject var3 = var1.getlocal(0).__getattr__("parseSymbols").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("scopes", var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getlocal(0).__getattr__("scopes").__getitem__(var1.getlocal(1));
      var1.getlocal(0).__setattr__("scope", var3);
      var3 = null;
      var1.setline(371);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("node"));
      var1.setline(372);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitFunction$46(PyFrame var1, ThreadState var2) {
      var1.setline(375);
      PyObject var10000 = var1.getlocal(0).__getattr__("_visitFuncOrLambda");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), Py.newInteger(0)};
      String[] var4 = new String[]{"isLambda"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(376);
      if (var1.getlocal(1).__getattr__("doc").__nonzero__()) {
         var1.setline(377);
         var1.getlocal(0).__getattr__("setDocstring").__call__(var2, var1.getlocal(1).__getattr__("doc"));
      }

      var1.setline(378);
      var1.getlocal(0).__getattr__("storeName").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitLambda$47(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var10000 = var1.getlocal(0).__getattr__("_visitFuncOrLambda");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), Py.newInteger(1)};
      String[] var4 = new String[]{"isLambda"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _visitFuncOrLambda$48(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyObject var10000 = var1.getlocal(2).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("decorators");
      }

      PyObject var3;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(385);
         var3 = var1.getlocal(1).__getattr__("decorators").__getattr__("nodes").__iter__();

         while(true) {
            var1.setline(385);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(387);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("decorators").__getattr__("nodes"));
               var1.setlocal(4, var3);
               var3 = null;
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(386);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
         }
      } else {
         var1.setline(389);
         PyInteger var5 = Py.newInteger(0);
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(391);
      var10000 = var1.getlocal(0).__getattr__("FunctionGen");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("scopes"), var1.getlocal(2), var1.getlocal(0).__getattr__("class_name"), var1.getlocal(0).__getattr__("get_module").__call__(var2)};
      var3 = var10000.__call__(var2, var6);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(393);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(5));
      var1.setline(394);
      var1.getlocal(5).__getattr__("finish").__call__(var2);
      var1.setline(395);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(396);
      var3 = var1.getlocal(1).__getattr__("defaults").__iter__();

      while(true) {
         var1.setline(396);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(398);
            var1.getlocal(0).__getattr__("_makeClosure").__call__(var2, var1.getlocal(5), var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("defaults")));
            var1.setline(399);
            var3 = var1.getglobal("range").__call__(var2, var1.getlocal(4)).__iter__();

            while(true) {
               var1.setline(399);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var4);
               var1.setline(400);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION"), (PyObject)Py.newInteger(1));
            }
         }

         var1.setlocal(6, var4);
         var1.setline(397);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(6));
      }
   }

   public PyObject visitClass$49(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyObject var3 = var1.getlocal(0).__getattr__("ClassGen").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("scopes"), var1.getlocal(0).__getattr__("get_module").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(405);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(2));
      var1.setline(406);
      var1.getlocal(2).__getattr__("finish").__call__(var2);
      var1.setline(407);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(408);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1).__getattr__("name"));
      var1.setline(409);
      var3 = var1.getlocal(1).__getattr__("bases").__iter__();

      while(true) {
         var1.setline(409);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(411);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_TUPLE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("bases")));
            var1.setline(412);
            var1.getlocal(0).__getattr__("_makeClosure").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
            var1.setline(413);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION"), (PyObject)Py.newInteger(0));
            var1.setline(414);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_CLASS"));
            var1.setline(415);
            var1.getlocal(0).__getattr__("storeName").__call__(var2, var1.getlocal(1).__getattr__("name"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(410);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject visitIf$50(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(423);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("tests"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(424);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(3)).__iter__();

      while(true) {
         var1.setline(424);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(439);
            if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
               var1.setline(440);
               var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"));
            }

            var1.setline(441);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(425);
         PyObject var5 = var1.getlocal(1).__getattr__("tests").__getitem__(var1.getlocal(4));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(6, var7);
         var7 = null;
         var5 = null;
         var1.setline(426);
         if (!var1.getglobal("is_constant_false").__call__(var2, var1.getlocal(5)).__nonzero__()) {
            var1.setline(429);
            var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(5));
            var1.setline(430);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(5));
            var1.setline(431);
            var5 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(432);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE"), (PyObject)var1.getlocal(7));
            var1.setline(433);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
            var1.setline(434);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
            var1.setline(435);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(6));
            var1.setline(436);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(2));
            var1.setline(437);
            var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(7));
            var1.setline(438);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         }
      }
   }

   public PyObject visitWhile$51(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(446);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(447);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(449);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(450);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_LOOP"), (PyObject)var1.getlocal(4));
      var1.setline(452);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(453);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LOOP"), var1.getlocal(2)})));
      var1.setline(455);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_lineno");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"force"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(456);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("test"));
      var1.setline(457);
      var10000 = var1.getlocal(0).__getattr__("emit");
      PyString var10002 = PyString.fromInterned("JUMP_IF_FALSE");
      PyObject var10003 = var1.getlocal(3);
      if (!var10003.__nonzero__()) {
         var10003 = var1.getlocal(4);
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
      var1.setline(459);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
      var1.setline(460);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.setline(461);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("body"));
      var1.setline(462);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_ABSOLUTE"), (PyObject)var1.getlocal(2));
      var1.setline(464);
      var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(3));
      var1.setline(465);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.setline(466);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"));
      var1.setline(467);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.setline(468);
      if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
         var1.setline(469);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"));
      }

      var1.setline(470);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitFor$52(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(474);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(476);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LOOP"), var1.getlocal(2)})));
      var1.setline(478);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(479);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_LOOP"), (PyObject)var1.getlocal(4));
      var1.setline(480);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("list"));
      var1.setline(481);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET_ITER"));
      var1.setline(483);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(484);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_lineno");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), Py.newInteger(1)};
      String[] var4 = new String[]{"force"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(485);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FOR_ITER"), (PyObject)var1.getlocal(3));
      var1.setline(486);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("assign"));
      var1.setline(487);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("body"));
      var1.setline(488);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_ABSOLUTE"), (PyObject)var1.getlocal(2));
      var1.setline(489);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(3));
      var1.setline(490);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"));
      var1.setline(491);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.setline(492);
      if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
         var1.setline(493);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"));
      }

      var1.setline(494);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitBreak$53(PyFrame var1, ThreadState var2) {
      var1.setline(497);
      if (var1.getlocal(0).__getattr__("setups").__not__().__nonzero__()) {
         var1.setline(498);
         throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("'break' outside loop (%s, %d)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno")})));
      } else {
         var1.setline(500);
         var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
         var1.setline(501);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BREAK_LOOP"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject visitContinue$54(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      if (var1.getlocal(0).__getattr__("setups").__not__().__nonzero__()) {
         var1.setline(505);
         throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("'continue' outside loop (%s, %d)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno")})));
      } else {
         var1.setline(507);
         PyObject var3 = var1.getlocal(0).__getattr__("setups").__getattr__("top").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(508);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._eq(var1.getglobal("LOOP"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(509);
            var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
            var1.setline(510);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_ABSOLUTE"), (PyObject)var1.getlocal(3));
            var1.setline(511);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
         } else {
            var1.setline(512);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("EXCEPT"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("TRY_FINALLY"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(513);
               var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
               var1.setline(515);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("setups"));
               var1.setlocal(4, var3);
               var3 = null;

               do {
                  var1.setline(516);
                  var3 = var1.getlocal(4);
                  var10000 = var3._gt(Py.newInteger(0));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(517);
                  var3 = var1.getlocal(4)._sub(Py.newInteger(1));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(518);
                  var3 = var1.getlocal(0).__getattr__("setups").__getitem__(var1.getlocal(4));
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(2, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(519);
                  var3 = var1.getlocal(2);
                  var10000 = var3._eq(var1.getglobal("LOOP"));
                  var3 = null;
               } while(!var10000.__nonzero__());

               var1.setline(521);
               var3 = var1.getlocal(2);
               var10000 = var3._ne(var1.getglobal("LOOP"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(522);
                  throw Py.makeException(var1.getglobal("SyntaxError"), PyString.fromInterned("'continue' outside loop (%s, %d)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno")})));
               }

               var1.setline(524);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CONTINUE_LOOP"), (PyObject)var1.getlocal(5));
               var1.setline(525);
               var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
            } else {
               var1.setline(526);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("END_FINALLY"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(527);
                  PyString var6 = PyString.fromInterned("'continue' not allowed inside 'finally' clause (%s, %d)");
                  var1.setlocal(6, var6);
                  var3 = null;
                  var1.setline(528);
                  throw Py.makeException(var1.getglobal("SyntaxError"), var1.getlocal(6)._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno")})));
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject visitTest$55(PyFrame var1, ThreadState var2) {
      var1.setline(531);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(532);
      var3 = var1.getlocal(1).__getattr__("nodes").__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

      while(true) {
         var1.setline(532);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(537);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("nodes").__getitem__(Py.newInteger(-1)));
            var1.setline(538);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(533);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4));
         var1.setline(534);
         var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setline(535);
         var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
         var1.setline(536);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      }
   }

   public PyObject visitAnd$56(PyFrame var1, ThreadState var2) {
      var1.setline(541);
      var1.getlocal(0).__getattr__("visitTest").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("JUMP_IF_FALSE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitOr$57(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      var1.getlocal(0).__getattr__("visitTest").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("JUMP_IF_TRUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitIfExp$58(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(548);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(549);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("test"));
      var1.setline(550);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE"), (PyObject)var1.getlocal(3));
      var1.setline(551);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.setline(552);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("then"));
      var1.setline(553);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(2));
      var1.setline(554);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(3));
      var1.setline(555);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.setline(556);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"));
      var1.setline(557);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitCompare$59(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(561);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(562);
      var3 = var1.getlocal(1).__getattr__("ops").__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

      while(true) {
         var1.setline(562);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         if (var4 == null) {
            var1.setline(571);
            if (var1.getlocal(1).__getattr__("ops").__nonzero__()) {
               var1.setline(572);
               var3 = var1.getlocal(1).__getattr__("ops").__getitem__(Py.newInteger(-1));
               PyObject[] var7 = Py.unpackSequence(var3, 2);
               PyObject var8 = var7[0];
               var1.setlocal(3, var8);
               var5 = null;
               var8 = var7[1];
               var1.setlocal(4, var8);
               var5 = null;
               var3 = null;
               var1.setline(573);
               var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4));
               var1.setline(574);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("COMPARE_OP"), (PyObject)var1.getlocal(3));
            }

            var1.setline(575);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("ops"));
            PyObject var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(576);
               var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(577);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(5));
               var1.setline(578);
               var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(2));
               var1.setline(579);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_TWO"));
               var1.setline(580);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
               var1.setline(581);
               var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(5));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(563);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4));
         var1.setline(564);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
         var1.setline(565);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_THREE"));
         var1.setline(566);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("COMPARE_OP"), (PyObject)var1.getlocal(3));
         var1.setline(567);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE"), (PyObject)var1.getlocal(2));
         var1.setline(568);
         var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
         var1.setline(569);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      }
   }

   public PyObject visitListComp$60(PyFrame var1, ThreadState var2) {
      var1.setline(587);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(589);
      PyObject var3 = PyString.fromInterned("$append%d")._mod(var1.getlocal(0).__getattr__("_CodeGenerator__list_count"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(590);
      var3 = var1.getlocal(0).__getattr__("_CodeGenerator__list_count")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("_CodeGenerator__list_count", var3);
      var3 = null;
      var1.setline(591);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_LIST"), (PyObject)Py.newInteger(0));
      var1.setline(592);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
      var1.setline(593);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)PyString.fromInterned("append"));
      var1.setline(594);
      var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE"), (PyObject)var1.getlocal(2));
      var1.setline(596);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(597);
      var3 = var1.getglobal("zip").__call__(var2, var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("quals"))), var1.getlocal(1).__getattr__("quals")).__iter__();

      while(true) {
         var1.setline(597);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         if (var4 == null) {
            var1.setline(606);
            var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD"), (PyObject)var1.getlocal(2));
            var1.setline(607);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
            var1.setline(608);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION"), (PyObject)Py.newInteger(1));
            var1.setline(609);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
            var1.setline(611);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(611);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(620);
                  var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE"), (PyObject)var1.getlocal(2));
                  var1.setline(622);
                  var3 = var1.getlocal(0).__getattr__("_CodeGenerator__list_count")._sub(Py.newInteger(1));
                  var1.getlocal(0).__setattr__("_CodeGenerator__list_count", var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(612);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(613);
                  var9 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
                  var1.setlocal(10, var9);
                  var5 = null;
                  var1.setline(614);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(10));
                  var1.setline(615);
                  var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(8));
                  var1.setline(616);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
                  var1.setline(617);
                  var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(10));
               }

               var1.setline(618);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_ABSOLUTE"), (PyObject)var1.getlocal(6));
               var1.setline(619);
               var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(7));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(598);
         var9 = var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(5));
         PyObject[] var10 = Py.unpackSequence(var9, 2);
         PyObject var7 = var10[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var10[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(599);
         var9 = var1.getglobal("None");
         var1.setlocal(8, var9);
         var5 = null;
         var1.setline(600);
         var9 = var1.getlocal(5).__getattr__("ifs").__iter__();

         while(true) {
            var1.setline(600);
            var6 = var9.__iternext__();
            if (var6 == null) {
               var1.setline(604);
               var1.getlocal(3).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(8), var1.getlocal(7)})));
               break;
            }

            var1.setlocal(9, var6);
            var1.setline(601);
            var7 = var1.getlocal(8);
            PyObject var10000 = var7._is(var1.getglobal("None"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(602);
               var7 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
               var1.setlocal(8, var7);
               var7 = null;
            }

            var1.setline(603);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(9), var1.getlocal(8));
         }
      }
   }

   public PyObject visitListCompFor$61(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(626);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(628);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("list"));
      var1.setline(629);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET_ITER"));
      var1.setline(630);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(631);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_lineno");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"force"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(632);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FOR_ITER"), (PyObject)var1.getlocal(3));
      var1.setline(633);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
      var1.setline(634);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("assign"));
      var1.setline(635);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject visitListCompIf$62(PyFrame var1, ThreadState var2) {
      var1.setline(638);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_lineno");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"force"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(639);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("test"));
      var1.setline(640);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE"), (PyObject)var1.getlocal(2));
      var1.setline(641);
      var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setline(642);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _makeClosure$63(PyFrame var1, ThreadState var2) {
      var1.setline(645);
      PyObject var3 = var1.getlocal(1).__getattr__("scope").__getattr__("get_free_vars").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(646);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(647);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(647);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(649);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_TUPLE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)));
               var1.setline(650);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1));
               var1.setline(651);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAKE_CLOSURE"), (PyObject)var1.getlocal(2));
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(648);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CLOSURE"), (PyObject)var1.getlocal(4));
         }
      } else {
         var1.setline(653);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1));
         var1.setline(654);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAKE_FUNCTION"), (PyObject)var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitGenExpr$64(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyObject var3 = var1.getglobal("GenExprCodeGenerator").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("scopes"), var1.getlocal(0).__getattr__("class_name"), var1.getlocal(0).__getattr__("get_module").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(659);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1).__getattr__("code"), var1.getlocal(2));
      var1.setline(660);
      var1.getlocal(2).__getattr__("finish").__call__(var2);
      var1.setline(661);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(662);
      var1.getlocal(0).__getattr__("_makeClosure").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setline(664);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("code").__getattr__("quals").__getitem__(Py.newInteger(0)).__getattr__("iter"));
      var1.setline(665);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET_ITER"));
      var1.setline(666);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION"), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitGenExprInner$65(PyFrame var1, ThreadState var2) {
      var1.setline(669);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(672);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(673);
      PyObject var8 = var1.getglobal("zip").__call__(var2, var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("quals"))), var1.getlocal(1).__getattr__("quals")).__iter__();

      while(true) {
         var1.setline(673);
         PyObject var4 = var8.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         if (var4 == null) {
            var1.setline(682);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
            var1.setline(683);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("YIELD_VALUE"));
            var1.setline(684);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
            var1.setline(686);
            var8 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(686);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.setline(699);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 4);
               var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[3];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(687);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(688);
                  var9 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
                  var1.setlocal(10, var9);
                  var5 = null;
                  var1.setline(689);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(10));
                  var1.setline(690);
                  var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(8));
                  var1.setline(691);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
                  var1.setline(692);
                  var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(10));
               }

               var1.setline(693);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_ABSOLUTE"), (PyObject)var1.getlocal(5));
               var1.setline(694);
               var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(6));
               var1.setline(695);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"));
               var1.setline(696);
               var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
               var1.setline(697);
               var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(7));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(674);
         var9 = var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4));
         PyObject[] var10 = Py.unpackSequence(var9, 3);
         PyObject var7 = var10[0];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var10[1];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var10[2];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(675);
         var9 = var1.getglobal("None");
         var1.setlocal(8, var9);
         var5 = null;
         var1.setline(676);
         var9 = var1.getlocal(4).__getattr__("ifs").__iter__();

         while(true) {
            var1.setline(676);
            var6 = var9.__iternext__();
            if (var6 == null) {
               var1.setline(680);
               var1.getlocal(2).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(8), var1.getlocal(6), var1.getlocal(7)})));
               break;
            }

            var1.setlocal(9, var6);
            var1.setline(677);
            var7 = var1.getlocal(8);
            PyObject var10000 = var7._is(var1.getglobal("None"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(678);
               var7 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
               var1.setlocal(8, var7);
               var7 = null;
            }

            var1.setline(679);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(9), var1.getlocal(8));
         }
      }
   }

   public PyObject visitGenExprFor$66(PyFrame var1, ThreadState var2) {
      var1.setline(702);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(703);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(704);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(706);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LOOP"), var1.getlocal(2)})));
      var1.setline(707);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_LOOP"), (PyObject)var1.getlocal(4));
      var1.setline(709);
      if (var1.getlocal(1).__getattr__("is_outmost").__nonzero__()) {
         var1.setline(710);
         var1.getlocal(0).__getattr__("loadName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".0"));
      } else {
         var1.setline(712);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("iter"));
         var1.setline(713);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET_ITER"));
      }

      var1.setline(715);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(716);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_lineno");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"force"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(717);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FOR_ITER"), (PyObject)var1.getlocal(3));
      var1.setline(718);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
      var1.setline(719);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("assign"));
      var1.setline(720);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject visitGenExprIf$67(PyFrame var1, ThreadState var2) {
      var1.setline(723);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_lineno");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"force"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(724);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("test"));
      var1.setline(725);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE"), (PyObject)var1.getlocal(2));
      var1.setline(726);
      var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setline(727);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssert$68(PyFrame var1, ThreadState var2) {
      var1.setline(734);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(735);
         PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(736);
         var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
         var1.setline(740);
         var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
         var1.setline(741);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("test"));
         var1.setline(742);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_TRUE"), (PyObject)var1.getlocal(2));
         var1.setline(743);
         var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
         var1.setline(744);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         var1.setline(745);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_GLOBAL"), (PyObject)PyString.fromInterned("AssertionError"));
         var1.setline(746);
         if (var1.getlocal(1).__getattr__("fail").__nonzero__()) {
            var1.setline(747);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("fail"));
            var1.setline(748);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RAISE_VARARGS"), (PyObject)Py.newInteger(2));
         } else {
            var1.setline(750);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RAISE_VARARGS"), (PyObject)Py.newInteger(1));
         }

         var1.setline(751);
         var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
         var1.setline(752);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitRaise$69(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(756);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(757);
      PyObject var4;
      if (var1.getlocal(1).__getattr__("expr1").__nonzero__()) {
         var1.setline(758);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr1"));
         var1.setline(759);
         var4 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(760);
      if (var1.getlocal(1).__getattr__("expr2").__nonzero__()) {
         var1.setline(761);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr2"));
         var1.setline(762);
         var4 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(763);
      if (var1.getlocal(1).__getattr__("expr3").__nonzero__()) {
         var1.setline(764);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr3"));
         var1.setline(765);
         var4 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(766);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RAISE_VARARGS"), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitTryExcept$70(PyFrame var1, ThreadState var2) {
      var1.setline(769);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(770);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(771);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(772);
      if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
         var1.setline(773);
         var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(775);
         var3 = var1.getlocal(4);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(776);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(777);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_EXCEPT"), (PyObject)var1.getlocal(3));
      var1.setline(778);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(779);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("EXCEPT"), var1.getlocal(2)})));
      var1.setline(780);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("body"));
      var1.setline(781);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"));
      var1.setline(782);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.setline(783);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(5));
      var1.setline(784);
      var1.getlocal(0).__getattr__("startBlock").__call__(var2, var1.getlocal(3));
      var1.setline(786);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("handlers"))._sub(Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(787);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("handlers"))).__iter__();

      while(true) {
         var1.setline(787);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(812);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("END_FINALLY"));
            var1.setline(813);
            if (var1.getlocal(1).__getattr__("else_").__nonzero__()) {
               var1.setline(814);
               var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(5));
               var1.setline(815);
               var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("else_"));
            }

            var1.setline(816);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(7, var4);
         var1.setline(788);
         PyObject var5 = var1.getlocal(1).__getattr__("handlers").__getitem__(var1.getlocal(7));
         PyObject[] var6 = Py.unpackSequence(var5, 3);
         PyObject var7 = var6[0];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(9, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(2, var7);
         var7 = null;
         var5 = null;
         var1.setline(789);
         var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(8));
         var1.setline(790);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(791);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
            var1.setline(792);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(8));
            var1.setline(793);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("COMPARE_OP"), (PyObject)PyString.fromInterned("exception match"));
            var1.setline(794);
            var5 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
            var1.setlocal(10, var5);
            var5 = null;
            var1.setline(795);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE"), (PyObject)var1.getlocal(10));
            var1.setline(796);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
            var1.setline(797);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         }

         var1.setline(798);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         var1.setline(799);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(800);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(9));
         } else {
            var1.setline(802);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         }

         var1.setline(803);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         var1.setline(804);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(2));
         var1.setline(805);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)var1.getlocal(4));
         var1.setline(806);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(807);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(10));
         } else {
            var1.setline(809);
            var1.getlocal(0).__getattr__("nextBlock").__call__(var2);
         }

         var1.setline(810);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(811);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
         }
      }
   }

   public PyObject visitTryFinally$71(PyFrame var1, ThreadState var2) {
      var1.setline(819);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(820);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(821);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(822);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_FINALLY"), (PyObject)var1.getlocal(3));
      var1.setline(823);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(824);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("TRY_FINALLY"), var1.getlocal(2)})));
      var1.setline(825);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("body"));
      var1.setline(826);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"));
      var1.setline(827);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.setline(828);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
      var1.setline(829);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(3));
      var1.setline(830);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("END_FINALLY"), var1.getlocal(3)})));
      var1.setline(831);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("final"));
      var1.setline(832);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("END_FINALLY"));
      var1.setline(833);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitWith$72(PyFrame var1, ThreadState var2) {
      var1.setline(838);
      PyObject var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(839);
      var3 = var1.getlocal(0).__getattr__("newBlock").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(840);
      var3 = PyString.fromInterned("$exit%d")._mod(var1.getlocal(0).__getattr__("_CodeGenerator__with_count"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(841);
      var3 = PyString.fromInterned("$value%d")._mod(var1.getlocal(0).__getattr__("_CodeGenerator__with_count"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(842);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "_CodeGenerator__with_count";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(843);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(844);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(845);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
      var1.setline(846);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)PyString.fromInterned("__exit__"));
      var1.setline(847);
      var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE"), (PyObject)var1.getlocal(4));
      var1.setline(848);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)PyString.fromInterned("__enter__"));
      var1.setline(849);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION"), (PyObject)Py.newInteger(0));
      var1.setline(850);
      var3 = var1.getlocal(1).__getattr__("vars");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(851);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      } else {
         var1.setline(853);
         var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE"), (PyObject)var1.getlocal(5));
      }

      var1.setline(854);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_FINALLY"), (PyObject)var1.getlocal(3));
      var1.setline(855);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(2));
      var1.setline(856);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("TRY_FINALLY"), var1.getlocal(2)})));
      var1.setline(857);
      var3 = var1.getlocal(1).__getattr__("vars");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(858);
         var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD"), (PyObject)var1.getlocal(5));
         var1.setline(859);
         var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE"), (PyObject)var1.getlocal(5));
         var1.setline(860);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("vars"));
      }

      var1.setline(861);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("body"));
      var1.setline(862);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"));
      var1.setline(863);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.setline(864);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
      var1.setline(865);
      var1.getlocal(0).__getattr__("nextBlock").__call__(var2, var1.getlocal(3));
      var1.setline(866);
      var1.getlocal(0).__getattr__("setups").__getattr__("push").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("END_FINALLY"), var1.getlocal(3)})));
      var1.setline(867);
      var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD"), (PyObject)var1.getlocal(4));
      var1.setline(868);
      var1.getlocal(0).__getattr__("_implicitNameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE"), (PyObject)var1.getlocal(4));
      var1.setline(869);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("WITH_CLEANUP"));
      var1.setline(870);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("END_FINALLY"));
      var1.setline(871);
      var1.getlocal(0).__getattr__("setups").__getattr__("pop").__call__(var2);
      var1.setline(872);
      var10000 = var1.getlocal(0);
      var6 = "_CodeGenerator__with_count";
      var4 = var10000;
      var5 = var4.__getattr__(var6);
      var5 = var5._isub(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitDiscard$73(PyFrame var1, ThreadState var2) {
      var1.setline(877);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(878);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(879);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitConst$74(PyFrame var1, ThreadState var2) {
      var1.setline(882);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1).__getattr__("value"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitKeyword$75(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1).__getattr__("name"));
      var1.setline(886);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitGlobal$76(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitName$77(PyFrame var1, ThreadState var2) {
      var1.setline(893);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(894);
      var1.getlocal(0).__getattr__("loadName").__call__(var2, var1.getlocal(1).__getattr__("name"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitPass$78(PyFrame var1, ThreadState var2) {
      var1.setline(897);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitImport$79(PyFrame var1, ThreadState var2) {
      var1.setline(900);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(901);
      var1.setline(901);
      PyInteger var3 = var1.getlocal(0).__getattr__("graph").__getattr__("checkFlag").__call__(var2, var1.getglobal("CO_FUTURE_ABSIMPORT")).__nonzero__() ? Py.newInteger(0) : Py.newInteger(-1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(902);
      PyObject var7 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(902);
         PyObject var4 = var7.__iternext__();
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
         var1.setline(903);
         PyObject var8 = var1.getglobal("VERSION");
         PyObject var10000 = var8._gt(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(904);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(2));
            var1.setline(905);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
         }

         var1.setline(906);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_NAME"), (PyObject)var1.getlocal(3));
         var1.setline(907);
         var8 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(908);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(909);
            var1.getlocal(0).__getattr__("_resolveDots").__call__(var2, var1.getlocal(3));
            var1.setline(910);
            var1.getlocal(0).__getattr__("storeName").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(912);
            var1.getlocal(0).__getattr__("storeName").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject visitFrom$80(PyFrame var1, ThreadState var2) {
      var1.setline(915);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(916);
      PyObject var3 = var1.getlocal(1).__getattr__("level");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(917);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("graph").__getattr__("checkFlag").__call__(var2, var1.getglobal("CO_FUTURE_ABSIMPORT")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(918);
         PyInteger var7 = Py.newInteger(-1);
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(919);
      var10000 = var1.getglobal("map");
      var1.setline(919);
      PyObject[] var8 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var8, f$81)), (PyObject)var1.getlocal(1).__getattr__("names"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(920);
      var3 = var1.getglobal("VERSION");
      var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(921);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(2));
         var1.setline(922);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("tuple").__call__(var2, var1.getlocal(3)));
      }

      var1.setline(923);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_NAME"), (PyObject)var1.getlocal(1).__getattr__("modname"));
      var1.setline(924);
      var3 = var1.getlocal(1).__getattr__("names").__iter__();

      while(true) {
         var1.setline(924);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(938);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(925);
         PyObject var9 = var1.getglobal("VERSION");
         var10000 = var9._gt(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(926);
            var9 = var1.getlocal(4);
            var10000 = var9._eq(PyString.fromInterned("*"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(927);
               PyInteger var10 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"namespace", var10);
               var5 = null;
               var1.setline(928);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_STAR"));
               var1.setline(930);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var9 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("names"));
                  var10000 = var9._eq(Py.newInteger(1));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(931);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(933);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_FROM"), (PyObject)var1.getlocal(4));
            var1.setline(934);
            var1.getlocal(0).__getattr__("_resolveDots").__call__(var2, var1.getlocal(4));
            var1.setline(935);
            var10000 = var1.getlocal(0).__getattr__("storeName");
            PyObject var10002 = var1.getlocal(5);
            if (!var10002.__nonzero__()) {
               var10002 = var1.getlocal(4);
            }

            var10000.__call__(var2, var10002);
         } else {
            var1.setline(937);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_FROM"), (PyObject)var1.getlocal(4));
         }
      }
   }

   public PyObject f$81(PyFrame var1, ThreadState var2) {
      var1.setline(919);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(919);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _resolveDots$82(PyFrame var1, ThreadState var2) {
      var1.setline(941);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(942);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(943);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(944);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(944);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(3, var4);
            var1.setline(945);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)var1.getlocal(3));
         }
      }
   }

   public PyObject visitGetattr$83(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(949);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1).__getattr__("attrname")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssign$84(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(955);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(956);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("nodes"))._sub(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(957);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("nodes"))).__iter__();

      while(true) {
         var1.setline(957);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(958);
         PyObject var5 = var1.getlocal(1).__getattr__("nodes").__getitem__(var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(959);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._lt(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(960);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
         }

         var1.setline(961);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("ast").__getattr__("Node")).__nonzero__()) {
            var1.setline(962);
            var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject visitAssName$85(PyFrame var1, ThreadState var2) {
      var1.setline(965);
      PyObject var3 = var1.getlocal(1).__getattr__("flags");
      PyObject var10000 = var3._eq(PyString.fromInterned("OP_ASSIGN"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(966);
         var1.getlocal(0).__getattr__("storeName").__call__(var2, var1.getlocal(1).__getattr__("name"));
      } else {
         var1.setline(967);
         var3 = var1.getlocal(1).__getattr__("flags");
         var10000 = var3._eq(PyString.fromInterned("OP_DELETE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(968);
            var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
            var1.setline(969);
            var1.getlocal(0).__getattr__("delName").__call__(var2, var1.getlocal(1).__getattr__("name"));
         } else {
            var1.setline(971);
            Py.printComma(PyString.fromInterned("oops"));
            Py.println(var1.getlocal(1).__getattr__("flags"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssAttr$86(PyFrame var1, ThreadState var2) {
      var1.setline(974);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(975);
      PyObject var3 = var1.getlocal(1).__getattr__("flags");
      PyObject var10000 = var3._eq(PyString.fromInterned("OP_ASSIGN"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(976);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_ATTR"), (PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1).__getattr__("attrname")));
      } else {
         var1.setline(977);
         var3 = var1.getlocal(1).__getattr__("flags");
         var10000 = var3._eq(PyString.fromInterned("OP_DELETE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(978);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_ATTR"), (PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1).__getattr__("attrname")));
         } else {
            var1.setline(980);
            Py.printComma(PyString.fromInterned("warning: unexpected flags:"));
            Py.println(var1.getlocal(1).__getattr__("flags"));
            var1.setline(981);
            Py.println(var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _visitAssSequence$87(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      PyObject var3 = var1.getglobal("findOp").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ne(PyString.fromInterned("OP_DELETE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(985);
         var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(2), var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("nodes")));
      }

      var1.setline(986);
      var3 = var1.getlocal(1).__getattr__("nodes").__iter__();

      while(true) {
         var1.setline(986);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(987);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject visitAssTuple$88(PyFrame var1, ThreadState var2) {
      var1.setline(994);
      var1.getlocal(0).__getattr__("_visitAssSequence").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNPACK_TUPLE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssList$89(PyFrame var1, ThreadState var2) {
      var1.setline(997);
      var1.getlocal(0).__getattr__("_visitAssSequence").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNPACK_LIST"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAugAssign$90(PyFrame var1, ThreadState var2) {
      var1.setline(1002);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1003);
      PyObject var3 = var1.getglobal("wrap_aug").__call__(var2, var1.getlocal(1).__getattr__("node"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1004);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("load"));
      var1.setline(1005);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(1006);
      var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(0).__getattr__("_augmented_opcode").__getitem__(var1.getlocal(1).__getattr__("op")));
      var1.setline(1007);
      var1.getlocal(0).__getattr__("visit").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("store"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAugName$91(PyFrame var1, ThreadState var2) {
      var1.setline(1025);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("load"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1026);
         var1.getlocal(0).__getattr__("loadName").__call__(var2, var1.getlocal(1).__getattr__("name"));
      } else {
         var1.setline(1027);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("store"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1028);
            var1.getlocal(0).__getattr__("storeName").__call__(var2, var1.getlocal(1).__getattr__("name"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAugGetattr$92(PyFrame var1, ThreadState var2) {
      var1.setline(1031);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("load"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1032);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
         var1.setline(1033);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
         var1.setline(1034);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1).__getattr__("attrname")));
      } else {
         var1.setline(1035);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("store"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1036);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_TWO"));
            var1.setline(1037);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_ATTR"), (PyObject)var1.getlocal(0).__getattr__("mangle").__call__(var2, var1.getlocal(1).__getattr__("attrname")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAugSlice$93(PyFrame var1, ThreadState var2) {
      var1.setline(1040);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("load"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1041);
         var1.getlocal(0).__getattr__("visitSlice").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
      } else {
         var1.setline(1042);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("store"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1043);
            PyInteger var4 = Py.newInteger(0);
            var1.setlocal(3, var4);
            var3 = null;
            var1.setline(1044);
            if (var1.getlocal(1).__getattr__("lower").__nonzero__()) {
               var1.setline(1045);
               var3 = var1.getlocal(3)._or(Py.newInteger(1));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(1046);
            if (var1.getlocal(1).__getattr__("upper").__nonzero__()) {
               var1.setline(1047);
               var3 = var1.getlocal(3)._or(Py.newInteger(2));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(1048);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1049);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_TWO"));
            } else {
               var1.setline(1050);
               var3 = var1.getlocal(3);
               var10000 = var3._eq(Py.newInteger(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1051);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_FOUR"));
               } else {
                  var1.setline(1053);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_THREE"));
               }
            }

            var1.setline(1054);
            var1.getlocal(0).__getattr__("emit").__call__(var2, PyString.fromInterned("STORE_SLICE+%d")._mod(var1.getlocal(3)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAugSubscript$94(PyFrame var1, ThreadState var2) {
      var1.setline(1057);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("load"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1058);
         var1.getlocal(0).__getattr__("visitSubscript").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
      } else {
         var1.setline(1059);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("store"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1060);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_THREE"));
            var1.setline(1061);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SUBSCR"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitExec$95(PyFrame var1, ThreadState var2) {
      var1.setline(1064);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(1065);
      PyObject var3 = var1.getlocal(1).__getattr__("locals");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1066);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
      } else {
         var1.setline(1068);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("locals"));
      }

      var1.setline(1069);
      var3 = var1.getlocal(1).__getattr__("globals");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1070);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
      } else {
         var1.setline(1072);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("globals"));
      }

      var1.setline(1073);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EXEC_STMT"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitCallFunc$96(PyFrame var1, ThreadState var2) {
      var1.setline(1076);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1077);
      var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1078);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1079);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("node"));
      var1.setline(1080);
      PyObject var6 = var1.getlocal(1).__getattr__("args").__iter__();

      while(true) {
         var1.setline(1080);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1086);
            var6 = var1.getlocal(1).__getattr__("star_args");
            PyObject var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1087);
               var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("star_args"));
            }

            var1.setline(1088);
            var6 = var1.getlocal(1).__getattr__("dstar_args");
            var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1089);
               var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("dstar_args"));
            }

            var1.setline(1090);
            var6 = var1.getlocal(1).__getattr__("star_args");
            var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            var6 = var10000;
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(1091);
            var6 = var1.getlocal(1).__getattr__("dstar_args");
            var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            var6 = var10000;
            var1.setlocal(6, var6);
            var3 = null;
            var1.setline(1092);
            var6 = var1.getglobal("callfunc_opcode_info").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)}));
            var1.setlocal(7, var6);
            var3 = null;
            var1.setline(1093);
            var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(7), var1.getlocal(3)._lshift(Py.newInteger(8))._or(var1.getlocal(2)));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1081);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(4));
         var1.setline(1082);
         PyObject var5;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("ast").__getattr__("Keyword")).__nonzero__()) {
            var1.setline(1083);
            var5 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(1085);
            var5 = var1.getlocal(2)._add(Py.newInteger(1));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject visitPrint$97(PyFrame var1, ThreadState var2) {
      var1.setline(1096);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1097);
      if (var1.getlocal(1).__getattr__("dest").__nonzero__()) {
         var1.setline(1098);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("dest"));
      }

      var1.setline(1099);
      PyObject var3 = var1.getlocal(1).__getattr__("nodes").__iter__();

      while(true) {
         var1.setline(1099);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1108);
            PyObject var10000 = var1.getlocal(1).__getattr__("dest");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1109);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1100);
         if (var1.getlocal(1).__getattr__("dest").__nonzero__()) {
            var1.setline(1101);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
         }

         var1.setline(1102);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
         var1.setline(1103);
         if (var1.getlocal(1).__getattr__("dest").__nonzero__()) {
            var1.setline(1104);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_TWO"));
            var1.setline(1105);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_ITEM_TO"));
         } else {
            var1.setline(1107);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_ITEM"));
         }
      }
   }

   public PyObject visitPrintnl$98(PyFrame var1, ThreadState var2) {
      var1.setline(1112);
      PyObject var10000 = var1.getlocal(0).__getattr__("visitPrint");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), Py.newInteger(1)};
      String[] var4 = new String[]{"newline"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1113);
      if (var1.getlocal(1).__getattr__("dest").__nonzero__()) {
         var1.setline(1114);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_NEWLINE_TO"));
      } else {
         var1.setline(1116);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_NEWLINE"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitReturn$99(PyFrame var1, ThreadState var2) {
      var1.setline(1119);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1120);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("value"));
      var1.setline(1121);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitYield$100(PyFrame var1, ThreadState var2) {
      var1.setline(1124);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1125);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("value"));
      var1.setline(1126);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("YIELD_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitSlice$101(PyFrame var1, ThreadState var2) {
      var1.setline(1132);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(1133);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1134);
      PyObject var4;
      if (var1.getlocal(1).__getattr__("lower").__nonzero__()) {
         var1.setline(1135);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("lower"));
         var1.setline(1136);
         var4 = var1.getlocal(3)._or(Py.newInteger(1));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(1137);
      if (var1.getlocal(1).__getattr__("upper").__nonzero__()) {
         var1.setline(1138);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("upper"));
         var1.setline(1139);
         var4 = var1.getlocal(3)._or(Py.newInteger(2));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(1140);
      PyObject var10000;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1141);
         var4 = var1.getlocal(3);
         var10000 = var4._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1142);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
         } else {
            var1.setline(1143);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1144);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOPX"), (PyObject)Py.newInteger(3));
            } else {
               var1.setline(1146);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOPX"), (PyObject)Py.newInteger(2));
            }
         }
      }

      var1.setline(1147);
      var4 = var1.getlocal(1).__getattr__("flags");
      var10000 = var4._eq(PyString.fromInterned("OP_APPLY"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1148);
         var1.getlocal(0).__getattr__("emit").__call__(var2, PyString.fromInterned("SLICE+%d")._mod(var1.getlocal(3)));
      } else {
         var1.setline(1149);
         var4 = var1.getlocal(1).__getattr__("flags");
         var10000 = var4._eq(PyString.fromInterned("OP_ASSIGN"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1150);
            var1.getlocal(0).__getattr__("emit").__call__(var2, PyString.fromInterned("STORE_SLICE+%d")._mod(var1.getlocal(3)));
         } else {
            var1.setline(1151);
            var4 = var1.getlocal(1).__getattr__("flags");
            var10000 = var4._eq(PyString.fromInterned("OP_DELETE"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1154);
               Py.printComma(PyString.fromInterned("weird slice"));
               Py.println(var1.getlocal(1).__getattr__("flags"));
               var1.setline(1155);
               throw Py.makeException();
            }

            var1.setline(1152);
            var1.getlocal(0).__getattr__("emit").__call__(var2, PyString.fromInterned("DELETE_SLICE+%d")._mod(var1.getlocal(3)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitSubscript$102(PyFrame var1, ThreadState var2) {
      var1.setline(1158);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(1159);
      PyObject var3 = var1.getlocal(1).__getattr__("subs").__iter__();

      while(true) {
         var1.setline(1159);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1161);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("subs"));
            PyObject var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1162);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_TUPLE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("subs")));
            }

            var1.setline(1163);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1164);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOPX"), (PyObject)Py.newInteger(2));
            }

            var1.setline(1165);
            var3 = var1.getlocal(1).__getattr__("flags");
            var10000 = var3._eq(PyString.fromInterned("OP_APPLY"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1166);
               var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_SUBSCR"));
            } else {
               var1.setline(1167);
               var3 = var1.getlocal(1).__getattr__("flags");
               var10000 = var3._eq(PyString.fromInterned("OP_ASSIGN"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1168);
                  var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SUBSCR"));
               } else {
                  var1.setline(1169);
                  var3 = var1.getlocal(1).__getattr__("flags");
                  var10000 = var3._eq(PyString.fromInterned("OP_DELETE"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1170);
                     var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_SUBSCR"));
                  }
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1160);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject binaryOp$103(PyFrame var1, ThreadState var2) {
      var1.setline(1175);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("left"));
      var1.setline(1176);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("right"));
      var1.setline(1177);
      var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAdd$104(PyFrame var1, ThreadState var2) {
      var1.setline(1180);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_ADD"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitSub$105(PyFrame var1, ThreadState var2) {
      var1.setline(1183);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_SUBTRACT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitMul$106(PyFrame var1, ThreadState var2) {
      var1.setline(1186);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_MULTIPLY"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitDiv$107(PyFrame var1, ThreadState var2) {
      var1.setline(1189);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_div_op"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitFloorDiv$108(PyFrame var1, ThreadState var2) {
      var1.setline(1192);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_FLOOR_DIVIDE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitMod$109(PyFrame var1, ThreadState var2) {
      var1.setline(1195);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_MODULO"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitPower$110(PyFrame var1, ThreadState var2) {
      var1.setline(1198);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_POWER"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitLeftShift$111(PyFrame var1, ThreadState var2) {
      var1.setline(1201);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_LSHIFT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitRightShift$112(PyFrame var1, ThreadState var2) {
      var1.setline(1204);
      PyObject var3 = var1.getlocal(0).__getattr__("binaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("BINARY_RSHIFT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unaryOp$113(PyFrame var1, ThreadState var2) {
      var1.setline(1209);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(1210);
      var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitInvert$114(PyFrame var1, ThreadState var2) {
      var1.setline(1213);
      PyObject var3 = var1.getlocal(0).__getattr__("unaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNARY_INVERT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitUnarySub$115(PyFrame var1, ThreadState var2) {
      var1.setline(1216);
      PyObject var3 = var1.getlocal(0).__getattr__("unaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNARY_NEGATIVE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitUnaryAdd$116(PyFrame var1, ThreadState var2) {
      var1.setline(1219);
      PyObject var3 = var1.getlocal(0).__getattr__("unaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNARY_POSITIVE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitUnaryInvert$117(PyFrame var1, ThreadState var2) {
      var1.setline(1222);
      PyObject var3 = var1.getlocal(0).__getattr__("unaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNARY_INVERT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitNot$118(PyFrame var1, ThreadState var2) {
      var1.setline(1225);
      PyObject var3 = var1.getlocal(0).__getattr__("unaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNARY_NOT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitBackquote$119(PyFrame var1, ThreadState var2) {
      var1.setline(1228);
      PyObject var3 = var1.getlocal(0).__getattr__("unaryOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("UNARY_CONVERT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bitOp$120(PyFrame var1, ThreadState var2) {
      var1.setline(1233);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.setline(1234);
      PyObject var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(1234);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1235);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
         var1.setline(1236);
         var1.getlocal(0).__getattr__("emit").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject visitBitand$121(PyFrame var1, ThreadState var2) {
      var1.setline(1239);
      PyObject var3 = var1.getlocal(0).__getattr__("bitOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("nodes"), (PyObject)PyString.fromInterned("BINARY_AND"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitBitor$122(PyFrame var1, ThreadState var2) {
      var1.setline(1242);
      PyObject var3 = var1.getlocal(0).__getattr__("bitOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("nodes"), (PyObject)PyString.fromInterned("BINARY_OR"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitBitxor$123(PyFrame var1, ThreadState var2) {
      var1.setline(1245);
      PyObject var3 = var1.getlocal(0).__getattr__("bitOp").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("nodes"), (PyObject)PyString.fromInterned("BINARY_XOR"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitEllipsis$124(PyFrame var1, ThreadState var2) {
      var1.setline(1250);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("Ellipsis"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitTuple$125(PyFrame var1, ThreadState var2) {
      var1.setline(1253);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1254);
      PyObject var3 = var1.getlocal(1).__getattr__("nodes").__iter__();

      while(true) {
         var1.setline(1254);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1256);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_TUPLE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("nodes")));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1255);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject visitList$126(PyFrame var1, ThreadState var2) {
      var1.setline(1259);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1260);
      PyObject var3 = var1.getlocal(1).__getattr__("nodes").__iter__();

      while(true) {
         var1.setline(1260);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1262);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_LIST"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("nodes")));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1261);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject visitSliceobj$127(PyFrame var1, ThreadState var2) {
      var1.setline(1265);
      PyObject var3 = var1.getlocal(1).__getattr__("nodes").__iter__();

      while(true) {
         var1.setline(1265);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1267);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_SLICE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("nodes")));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1266);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject visitDict$128(PyFrame var1, ThreadState var2) {
      var1.setline(1270);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1271);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_MAP"), (PyObject)Py.newInteger(0));
      var1.setline(1272);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__iter__();

      while(true) {
         var1.setline(1272);
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
         var1.setline(1273);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"));
         var1.setline(1274);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(2));
         var1.setline(1275);
         var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(3));
         var1.setline(1276);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_THREE"));
         var1.setline(1277);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SUBSCR"));
      }
   }

   public PyObject NestedScopeMixin$129(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Defines initClass() for nested scoping (Python 2.2-compatible)"));
      var1.setline(1280);
      PyString.fromInterned("Defines initClass() for nested scoping (Python 2.2-compatible)");
      var1.setline(1281);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, initClass$130, (PyObject)null);
      var1.setlocal("initClass", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initClass$130(PyFrame var1, ThreadState var2) {
      var1.setline(1282);
      PyObject var3 = var1.getglobal("LocalNameFinder");
      var1.getlocal(0).__getattr__("__class__").__setattr__("NameFinder", var3);
      var3 = null;
      var1.setline(1283);
      var3 = var1.getglobal("FunctionCodeGenerator");
      var1.getlocal(0).__getattr__("__class__").__setattr__("FunctionGen", var3);
      var3 = null;
      var1.setline(1284);
      var3 = var1.getglobal("ClassCodeGenerator");
      var1.getlocal(0).__getattr__("__class__").__setattr__("ClassGen", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ModuleCodeGenerator$131(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1287);
      PyObject var3 = var1.getname("CodeGenerator").__getattr__("__init__");
      var1.setlocal("_ModuleCodeGenerator__super_init", var3);
      var3 = null;
      var1.setline(1289);
      var3 = var1.getname("None");
      var1.setlocal("scopes", var3);
      var3 = null;
      var1.setline(1291);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$132, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1297);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_module$133, (PyObject)null);
      var1.setlocal("get_module", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$132(PyFrame var1, ThreadState var2) {
      var1.setline(1292);
      PyObject var3 = var1.getglobal("pyassem").__getattr__("PyFlowGraph").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<module>"), (PyObject)var1.getlocal(1).__getattr__("filename"));
      var1.getlocal(0).__setattr__("graph", var3);
      var3 = null;
      var1.setline(1293);
      var3 = var1.getglobal("future").__getattr__("find_futures").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("futures", var3);
      var3 = null;
      var1.setline(1294);
      var1.getlocal(0).__getattr__("_ModuleCodeGenerator__super_init").__call__(var2);
      var1.setline(1295);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_module$133(PyFrame var1, ThreadState var2) {
      var1.setline(1298);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ExpressionCodeGenerator$134(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1301);
      PyObject var3 = var1.getname("CodeGenerator").__getattr__("__init__");
      var1.setlocal("_ExpressionCodeGenerator__super_init", var3);
      var3 = null;
      var1.setline(1303);
      var3 = var1.getname("None");
      var1.setlocal("scopes", var3);
      var3 = null;
      var1.setline(1304);
      PyTuple var4 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("futures", var4);
      var3 = null;
      var1.setline(1306);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$135, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1311);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_module$136, (PyObject)null);
      var1.setlocal("get_module", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$135(PyFrame var1, ThreadState var2) {
      var1.setline(1307);
      PyObject var3 = var1.getglobal("pyassem").__getattr__("PyFlowGraph").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<expression>"), (PyObject)var1.getlocal(1).__getattr__("filename"));
      var1.getlocal(0).__setattr__("graph", var3);
      var3 = null;
      var1.setline(1308);
      var1.getlocal(0).__getattr__("_ExpressionCodeGenerator__super_init").__call__(var2);
      var1.setline(1309);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_module$136(PyFrame var1, ThreadState var2) {
      var1.setline(1312);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject InteractiveCodeGenerator$137(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1316);
      PyObject var3 = var1.getname("CodeGenerator").__getattr__("__init__");
      var1.setlocal("_InteractiveCodeGenerator__super_init", var3);
      var3 = null;
      var1.setline(1318);
      var3 = var1.getname("None");
      var1.setlocal("scopes", var3);
      var3 = null;
      var1.setline(1319);
      PyTuple var4 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("futures", var4);
      var3 = null;
      var1.setline(1321);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$138, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1328);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_module$139, (PyObject)null);
      var1.setlocal("get_module", var6);
      var3 = null;
      var1.setline(1331);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, visitDiscard$140, (PyObject)null);
      var1.setlocal("visitDiscard", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$138(PyFrame var1, ThreadState var2) {
      var1.setline(1322);
      PyObject var3 = var1.getglobal("pyassem").__getattr__("PyFlowGraph").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<interactive>"), (PyObject)var1.getlocal(1).__getattr__("filename"));
      var1.getlocal(0).__setattr__("graph", var3);
      var3 = null;
      var1.setline(1323);
      var1.getlocal(0).__getattr__("_InteractiveCodeGenerator__super_init").__call__(var2);
      var1.setline(1324);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1325);
      var1.getglobal("walk").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.setline(1326);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_module$139(PyFrame var1, ThreadState var2) {
      var1.setline(1329);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject visitDiscard$140(PyFrame var1, ThreadState var2) {
      var1.setline(1334);
      var1.getlocal(0).__getattr__("visit").__call__(var2, var1.getlocal(1).__getattr__("expr"));
      var1.setline(1335);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_EXPR"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AbstractFunctionCode$141(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1338);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("optimized", var3);
      var3 = null;
      var1.setline(1339);
      var3 = Py.newInteger(0);
      var1.setlocal("lambdaCount", var3);
      var3 = null;
      var1.setline(1341);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$142, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1370);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_module$143, (PyObject)null);
      var1.setlocal("get_module", var5);
      var3 = null;
      var1.setline(1373);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finish$144, (PyObject)null);
      var1.setlocal("finish", var5);
      var3 = null;
      var1.setline(1379);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, generateArgUnpack$145, (PyObject)null);
      var1.setlocal("generateArgUnpack", var5);
      var3 = null;
      var1.setline(1386);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unpackSequence$146, (PyObject)null);
      var1.setlocal("unpackSequence", var5);
      var3 = null;
      var1.setline(1397);
      PyObject var6 = var1.getname("unpackSequence");
      var1.setlocal("unpackTuple", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$142(PyFrame var1, ThreadState var2) {
      var1.setline(1342);
      PyObject var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("class_name", var3);
      var3 = null;
      var1.setline(1343);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(1344);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1345);
         var3 = var1.getglobal("FunctionCodeGenerator");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1346);
         var3 = PyString.fromInterned("<lambda.%d>")._mod(var1.getlocal(6).__getattr__("lambdaCount"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1347);
         var3 = var1.getlocal(6).__getattr__("lambdaCount")._add(Py.newInteger(1));
         var1.getlocal(6).__setattr__("lambdaCount", var3);
         var3 = null;
      } else {
         var1.setline(1349);
         var3 = var1.getlocal(1).__getattr__("name");
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(1351);
      var3 = var1.getglobal("generateArgList").__call__(var2, var1.getlocal(1).__getattr__("argnames"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(1352);
      PyObject var10000 = var1.getglobal("pyassem").__getattr__("PyFlowGraph");
      PyObject[] var7 = new PyObject[]{var1.getlocal(7), var1.getlocal(1).__getattr__("filename"), var1.getlocal(8), Py.newInteger(1)};
      String[] var6 = new String[]{"optimized"};
      var10000 = var10000.__call__(var2, var7, var6);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("graph", var3);
      var3 = null;
      var1.setline(1354);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("isLambda", var3);
      var3 = null;
      var1.setline(1355);
      var1.getlocal(0).__getattr__("super_init").__call__(var2);
      var1.setline(1357);
      var10000 = var1.getlocal(3).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("doc");
      }

      if (var10000.__nonzero__()) {
         var1.setline(1358);
         var1.getlocal(0).__getattr__("setDocstring").__call__(var2, var1.getlocal(1).__getattr__("doc"));
      }

      var1.setline(1360);
      var10000 = var1.getglobal("walk");
      var7 = new PyObject[]{var1.getlocal(1).__getattr__("code"), var1.getlocal(0).__getattr__("NameFinder").__call__(var2, var1.getlocal(8)), Py.newInteger(0)};
      var6 = new String[]{"verbose"};
      var10000 = var10000.__call__(var2, var7, var6);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1361);
      var1.getlocal(0).__getattr__("locals").__getattr__("push").__call__(var2, var1.getlocal(10).__getattr__("getLocals").__call__(var2));
      var1.setline(1362);
      if (var1.getlocal(1).__getattr__("varargs").__nonzero__()) {
         var1.setline(1363);
         var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_VARARGS"));
      }

      var1.setline(1364);
      if (var1.getlocal(1).__getattr__("kwargs").__nonzero__()) {
         var1.setline(1365);
         var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_VARKEYWORDS"));
      }

      var1.setline(1366);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1367);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(1368);
         var1.getlocal(0).__getattr__("generateArgUnpack").__call__(var2, var1.getlocal(1).__getattr__("argnames"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_module$143(PyFrame var1, ThreadState var2) {
      var1.setline(1371);
      PyObject var3 = var1.getlocal(0).__getattr__("module");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject finish$144(PyFrame var1, ThreadState var2) {
      var1.setline(1374);
      var1.getlocal(0).__getattr__("graph").__getattr__("startExitBlock").__call__(var2);
      var1.setline(1375);
      if (var1.getlocal(0).__getattr__("isLambda").__not__().__nonzero__()) {
         var1.setline(1376);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getglobal("None"));
      }

      var1.setline(1377);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject generateArgUnpack$145(PyFrame var1, ThreadState var2) {
      var1.setline(1380);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      while(true) {
         var1.setline(1380);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1381);
         PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(1382);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(1383);
            var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_FAST"), (PyObject)PyString.fromInterned(".%d")._mod(var1.getlocal(2)._mul(Py.newInteger(2))));
            var1.setline(1384);
            var1.getlocal(0).__getattr__("unpackSequence").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject unpackSequence$146(PyFrame var1, ThreadState var2) {
      var1.setline(1387);
      PyObject var3 = var1.getglobal("VERSION");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1388);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNPACK_SEQUENCE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(1390);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNPACK_TUPLE"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(1391);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1391);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1392);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(1393);
            var1.getlocal(0).__getattr__("unpackSequence").__call__(var2, var1.getlocal(2));
         } else {
            var1.setline(1395);
            var1.getlocal(0).__getattr__("_nameOp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE"), (PyObject)var1.getlocal(2));
         }
      }
   }

   public PyObject FunctionCodeGenerator$147(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1401);
      PyObject var3 = var1.getname("CodeGenerator").__getattr__("__init__");
      var1.setlocal("super_init", var3);
      var3 = null;
      var1.setline(1402);
      var3 = var1.getname("None");
      var1.setlocal("scopes", var3);
      var3 = null;
      var1.setline(1404);
      var3 = var1.getname("AbstractFunctionCode").__getattr__("__init__");
      var1.setlocal("_FunctionCodeGenerator__super_init", var3);
      var3 = null;
      var1.setline(1406);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$148, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$148(PyFrame var1, ThreadState var2) {
      var1.setline(1407);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("scopes", var3);
      var3 = null;
      var1.setline(1408);
      var3 = var1.getlocal(2).__getitem__(var1.getlocal(1));
      var1.getlocal(0).__setattr__("scope", var3);
      var3 = null;
      var1.setline(1409);
      PyObject var10000 = var1.getlocal(0).__getattr__("_FunctionCodeGenerator__super_init");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      var10000.__call__(var2, var4);
      var1.setline(1410);
      var1.getlocal(0).__getattr__("graph").__getattr__("setFreeVars").__call__(var2, var1.getlocal(0).__getattr__("scope").__getattr__("get_free_vars").__call__(var2));
      var1.setline(1411);
      var1.getlocal(0).__getattr__("graph").__getattr__("setCellVars").__call__(var2, var1.getlocal(0).__getattr__("scope").__getattr__("get_cell_vars").__call__(var2));
      var1.setline(1412);
      var3 = var1.getlocal(0).__getattr__("scope").__getattr__("generator");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1413);
         var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_GENERATOR"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject GenExprCodeGenerator$149(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1417);
      PyObject var3 = var1.getname("CodeGenerator").__getattr__("__init__");
      var1.setlocal("super_init", var3);
      var3 = null;
      var1.setline(1418);
      var3 = var1.getname("None");
      var1.setlocal("scopes", var3);
      var3 = null;
      var1.setline(1420);
      var3 = var1.getname("AbstractFunctionCode").__getattr__("__init__");
      var1.setlocal("_GenExprCodeGenerator__super_init", var3);
      var3 = null;
      var1.setline(1422);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$150, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$150(PyFrame var1, ThreadState var2) {
      var1.setline(1423);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("scopes", var3);
      var3 = null;
      var1.setline(1424);
      var3 = var1.getlocal(2).__getitem__(var1.getlocal(1));
      var1.getlocal(0).__setattr__("scope", var3);
      var3 = null;
      var1.setline(1425);
      PyObject var10000 = var1.getlocal(0).__getattr__("_GenExprCodeGenerator__super_init");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(1), var1.getlocal(3), var1.getlocal(4)};
      var10000.__call__(var2, var4);
      var1.setline(1426);
      var1.getlocal(0).__getattr__("graph").__getattr__("setFreeVars").__call__(var2, var1.getlocal(0).__getattr__("scope").__getattr__("get_free_vars").__call__(var2));
      var1.setline(1427);
      var1.getlocal(0).__getattr__("graph").__getattr__("setCellVars").__call__(var2, var1.getlocal(0).__getattr__("scope").__getattr__("get_cell_vars").__call__(var2));
      var1.setline(1428);
      var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_GENERATOR"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AbstractClassCode$151(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1432);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$152, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_module$153, (PyObject)null);
      var1.setlocal("get_module", var4);
      var3 = null;
      var1.setline(1447);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, finish$154, (PyObject)null);
      var1.setlocal("finish", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$152(PyFrame var1, ThreadState var2) {
      var1.setline(1433);
      PyObject var3 = var1.getlocal(1).__getattr__("name");
      var1.getlocal(0).__setattr__("class_name", var3);
      var3 = null;
      var1.setline(1434);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(1435);
      PyObject var10000 = var1.getglobal("pyassem").__getattr__("PyFlowGraph");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1).__getattr__("name"), var1.getlocal(1).__getattr__("filename"), Py.newInteger(0), Py.newInteger(1)};
      String[] var4 = new String[]{"optimized", "klass"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("graph", var3);
      var3 = null;
      var1.setline(1437);
      var1.getlocal(0).__getattr__("super_init").__call__(var2);
      var1.setline(1438);
      var10000 = var1.getglobal("walk");
      var5 = new PyObject[]{var1.getlocal(1).__getattr__("code"), var1.getlocal(0).__getattr__("NameFinder").__call__(var2), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1439);
      var1.getlocal(0).__getattr__("locals").__getattr__("push").__call__(var2, var1.getlocal(4).__getattr__("getLocals").__call__(var2));
      var1.setline(1440);
      var1.getlocal(0).__getattr__("graph").__getattr__("setFlag").__call__(var2, var1.getglobal("CO_NEWLOCALS"));
      var1.setline(1441);
      if (var1.getlocal(1).__getattr__("doc").__nonzero__()) {
         var1.setline(1442);
         var1.getlocal(0).__getattr__("setDocstring").__call__(var2, var1.getlocal(1).__getattr__("doc"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_module$153(PyFrame var1, ThreadState var2) {
      var1.setline(1445);
      PyObject var3 = var1.getlocal(0).__getattr__("module");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject finish$154(PyFrame var1, ThreadState var2) {
      var1.setline(1448);
      var1.getlocal(0).__getattr__("graph").__getattr__("startExitBlock").__call__(var2);
      var1.setline(1449);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_LOCALS"));
      var1.setline(1450);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ClassCodeGenerator$155(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1453);
      PyObject var3 = var1.getname("CodeGenerator").__getattr__("__init__");
      var1.setlocal("super_init", var3);
      var3 = null;
      var1.setline(1454);
      var3 = var1.getname("None");
      var1.setlocal("scopes", var3);
      var3 = null;
      var1.setline(1456);
      var3 = var1.getname("AbstractClassCode").__getattr__("__init__");
      var1.setlocal("_ClassCodeGenerator__super_init", var3);
      var3 = null;
      var1.setline(1458);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$156, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$156(PyFrame var1, ThreadState var2) {
      var1.setline(1459);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("scopes", var3);
      var3 = null;
      var1.setline(1460);
      var3 = var1.getlocal(2).__getitem__(var1.getlocal(1));
      var1.getlocal(0).__setattr__("scope", var3);
      var3 = null;
      var1.setline(1461);
      var1.getlocal(0).__getattr__("_ClassCodeGenerator__super_init").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(1462);
      var1.getlocal(0).__getattr__("graph").__getattr__("setFreeVars").__call__(var2, var1.getlocal(0).__getattr__("scope").__getattr__("get_free_vars").__call__(var2));
      var1.setline(1463);
      var1.getlocal(0).__getattr__("graph").__getattr__("setCellVars").__call__(var2, var1.getlocal(0).__getattr__("scope").__getattr__("get_cell_vars").__call__(var2));
      var1.setline(1464);
      var1.getlocal(0).__getattr__("set_lineno").__call__(var2, var1.getlocal(1));
      var1.setline(1465);
      var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_GLOBAL"), (PyObject)PyString.fromInterned("__name__"));
      var1.setline(1466);
      var1.getlocal(0).__getattr__("storeName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__module__"));
      var1.setline(1467);
      if (var1.getlocal(1).__getattr__("doc").__nonzero__()) {
         var1.setline(1468);
         var1.getlocal(0).__getattr__("emit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)var1.getlocal(1).__getattr__("doc"));
         var1.setline(1469);
         var1.getlocal(0).__getattr__("storeName").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__doc__"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject generateArgList$157(PyFrame var1, ThreadState var2) {
      var1.setline(1472);
      PyString.fromInterned("Generate an arg list marking TupleArgs");
      var1.setline(1473);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1474);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1475);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1476);
      PyObject var7 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

      while(true) {
         var1.setline(1476);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(1486);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1)._add(var1.getlocal(2)), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(4, var4);
         var1.setline(1477);
         PyObject var5 = var1.getlocal(0).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1478);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("str")).__nonzero__()) {
            var1.setline(1479);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(5));
         } else {
            var1.setline(1480);
            if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("tuple")).__nonzero__()) {
               var1.setline(1485);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unexpect argument type:"), var1.getlocal(5));
            }

            var1.setline(1481);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("TupleArg").__call__(var2, var1.getlocal(4)._mul(Py.newInteger(2)), var1.getlocal(5)));
            var1.setline(1482);
            var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getglobal("misc").__getattr__("flatten").__call__(var2, var1.getlocal(5)));
            var1.setline(1483);
            var5 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.setlocal(3, var5);
            var5 = null;
         }
      }
   }

   public PyObject findOp$158(PyFrame var1, ThreadState var2) {
      var1.setline(1489);
      PyString.fromInterned("Find the op (DELETE, LOAD, STORE) in an AssTuple tree");
      var1.setline(1490);
      PyObject var3 = var1.getglobal("OpFinder").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1491);
      PyObject var10000 = var1.getglobal("walk");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(1492);
      var3 = var1.getlocal(1).__getattr__("op");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OpFinder$159(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1495);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$160, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1497);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visitAssName$161, (PyObject)null);
      var1.setlocal("visitAssName", var4);
      var3 = null;
      var1.setline(1502);
      PyObject var5 = var1.getname("visitAssName");
      var1.setlocal("visitAssAttr", var5);
      var3 = null;
      var1.setline(1503);
      var5 = var1.getname("visitAssName");
      var1.setlocal("visitSubscript", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$160(PyFrame var1, ThreadState var2) {
      var1.setline(1496);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("op", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visitAssName$161(PyFrame var1, ThreadState var2) {
      var1.setline(1498);
      PyObject var3 = var1.getlocal(0).__getattr__("op");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1499);
         var3 = var1.getlocal(1).__getattr__("flags");
         var1.getlocal(0).__setattr__("op", var3);
         var3 = null;
      } else {
         var1.setline(1500);
         var3 = var1.getlocal(0).__getattr__("op");
         var10000 = var3._ne(var1.getlocal(1).__getattr__("flags"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1501);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("mixed ops in stmt"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Delegator$162(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class to support delegation for augmented assignment nodes\n\n    To generator code for augmented assignments, we use the following\n    wrapper classes.  In visitAugAssign, the left-hand expression node\n    is visited twice.  The first time the visit uses the normal method\n    for that node .  The second time the visit uses a different method\n    that generates the appropriate code to perform the assignment.\n    These delegator classes wrap the original AST nodes in order to\n    support the variant visit methods.\n    "));
      var1.setline(1515);
      PyString.fromInterned("Base class to support delegation for augmented assignment nodes\n\n    To generator code for augmented assignments, we use the following\n    wrapper classes.  In visitAugAssign, the left-hand expression node\n    is visited twice.  The first time the visit uses the normal method\n    for that node .  The second time the visit uses a different method\n    that generates the appropriate code to perform the assignment.\n    These delegator classes wrap the original AST nodes in order to\n    support the variant visit methods.\n    ");
      var1.setline(1516);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$163, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1519);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$164, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$163(PyFrame var1, ThreadState var2) {
      var1.setline(1517);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("obj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$164(PyFrame var1, ThreadState var2) {
      var1.setline(1520);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("obj"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AugGetattr$165(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1523);
      return var1.getf_locals();
   }

   public PyObject AugName$166(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1526);
      return var1.getf_locals();
   }

   public PyObject AugSlice$167(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1529);
      return var1.getf_locals();
   }

   public PyObject AugSubscript$168(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1532);
      return var1.getf_locals();
   }

   public PyObject wrap_aug$169(PyFrame var1, ThreadState var2) {
      var1.setline(1542);
      PyObject var3 = var1.getglobal("wrapper").__getitem__(var1.getlocal(0).__getattr__("__class__")).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public pycodegen$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"filename", "display", "f", "buf", "mod"};
      compileFile$1 = Py.newCode(2, var2, var1, "compileFile", 41, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "filename", "mode", "flags", "dont_inherit", "gen"};
      compile$2 = Py.newCode(5, var2, var1, "compile", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractCompileMode$3 = Py.newCode(0, var2, var1, "AbstractCompileMode", 76, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "filename"};
      __init__$4 = Py.newCode(3, var2, var1, "__init__", 80, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree"};
      _get_tree$5 = Py.newCode(1, var2, var1, "_get_tree", 85, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      compile$6 = Py.newCode(1, var2, var1, "compile", 91, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getCode$7 = Py.newCode(1, var2, var1, "getCode", 94, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Expression$8 = Py.newCode(0, var2, var1, "Expression", 97, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree", "gen"};
      compile$9 = Py.newCode(1, var2, var1, "compile", 101, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Interactive$10 = Py.newCode(0, var2, var1, "Interactive", 106, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree", "gen"};
      compile$11 = Py.newCode(1, var2, var1, "compile", 110, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Module$12 = Py.newCode(0, var2, var1, "Module", 115, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "display", "tree", "gen", "pprint"};
      compile$13 = Py.newCode(2, var2, var1, "compile", 119, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      dump$14 = Py.newCode(2, var2, var1, "dump", 127, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mtime"};
      getPycHeader$15 = Py.newCode(1, var2, var1, "getPycHeader", 133, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LocalNameFinder$16 = Py.newCode(0, var2, var1, "LocalNameFinder", 142, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "names", "name"};
      __init__$17 = Py.newCode(2, var2, var1, "__init__", 144, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elt"};
      getLocals$18 = Py.newCode(1, var2, var1, "getLocals", 152, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitDict$19 = Py.newCode(2, var2, var1, "visitDict", 158, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "name"};
      visitGlobal$20 = Py.newCode(2, var2, var1, "visitGlobal", 161, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitFunction$21 = Py.newCode(2, var2, var1, "visitFunction", 165, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitLambda$22 = Py.newCode(2, var2, var1, "visitLambda", 168, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "name", "alias"};
      visitImport$23 = Py.newCode(2, var2, var1, "visitImport", 171, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "name", "alias"};
      visitFrom$24 = Py.newCode(2, var2, var1, "visitFrom", 175, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitClass$25 = Py.newCode(2, var2, var1, "visitClass", 179, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssName$26 = Py.newCode(2, var2, var1, "visitAssName", 182, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      is_constant_false$27 = Py.newCode(1, var2, var1, "is_constant_false", 185, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CodeGenerator$28 = Py.newCode(0, var2, var1, "CodeGenerator", 191, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "futures", "feature"};
      __init__$29 = Py.newCode(1, var2, var1, "__init__", 209, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initClass$30 = Py.newCode(1, var2, var1, "initClass", 233, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "intro"};
      checkClass$31 = Py.newCode(1, var2, var1, "checkClass", 236, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _setupGraphDelegation$32 = Py.newCode(1, var2, var1, "_setupGraphDelegation", 247, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getCode$33 = Py.newCode(1, var2, var1, "getCode", 254, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      mangle$34 = Py.newCode(2, var2, var1, "mangle", 258, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "s"};
      parseSymbols$35 = Py.newCode(2, var2, var1, "parseSymbols", 264, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_module$36 = Py.newCode(1, var2, var1, "get_module", 269, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      isLocalName$37 = Py.newCode(2, var2, var1, "isLocalName", 274, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      storeName$38 = Py.newCode(2, var2, var1, "storeName", 277, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      loadName$39 = Py.newCode(2, var2, var1, "loadName", 280, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      delName$40 = Py.newCode(2, var2, var1, "delName", 283, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "name", "scope"};
      _nameOp$41 = Py.newCode(3, var2, var1, "_nameOp", 286, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "name"};
      _implicitNameOp$42 = Py.newCode(3, var2, var1, "_implicitNameOp", 307, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "force", "lineno"};
      set_lineno$43 = Py.newCode(3, var2, var1, "set_lineno", 324, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "lnf"};
      visitModule$44 = Py.newCode(2, var2, var1, "visitModule", 354, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitExpression$45 = Py.newCode(2, var2, var1, "visitExpression", 367, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitFunction$46 = Py.newCode(2, var2, var1, "visitFunction", 374, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitLambda$47 = Py.newCode(2, var2, var1, "visitLambda", 380, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "isLambda", "decorator", "ndecorators", "gen", "default", "i"};
      _visitFuncOrLambda$48 = Py.newCode(3, var2, var1, "_visitFuncOrLambda", 383, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "gen", "base"};
      visitClass$49 = Py.newCode(2, var2, var1, "visitClass", 402, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "end", "numtests", "i", "test", "suite", "nextTest"};
      visitIf$50 = Py.newCode(2, var2, var1, "visitIf", 421, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "loop", "else_", "after"};
      visitWhile$51 = Py.newCode(2, var2, var1, "visitWhile", 443, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "start", "anchor", "after"};
      visitFor$52 = Py.newCode(2, var2, var1, "visitFor", 472, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitBreak$53 = Py.newCode(2, var2, var1, "visitBreak", 496, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "kind", "block", "top", "loop_block", "msg"};
      visitContinue$54 = Py.newCode(2, var2, var1, "visitContinue", 503, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "jump", "end", "child"};
      visitTest$55 = Py.newCode(3, var2, var1, "visitTest", 530, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAnd$56 = Py.newCode(2, var2, var1, "visitAnd", 540, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitOr$57 = Py.newCode(2, var2, var1, "visitOr", 543, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "endblock", "elseblock"};
      visitIfExp$58 = Py.newCode(2, var2, var1, "visitIfExp", 546, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "cleanup", "op", "code", "end"};
      visitCompare$59 = Py.newCode(2, var2, var1, "visitCompare", 559, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "append", "stack", "i", "for_", "start", "anchor", "cont", "if_", "skip_one"};
      visitListComp$60 = Py.newCode(2, var2, var1, "visitListComp", 586, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "start", "anchor"};
      visitListCompFor$61 = Py.newCode(2, var2, var1, "visitListCompFor", 624, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "branch"};
      visitListCompIf$62 = Py.newCode(3, var2, var1, "visitListCompIf", 637, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "gen", "args", "frees", "name"};
      _makeClosure$63 = Py.newCode(3, var2, var1, "_makeClosure", 644, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "gen"};
      visitGenExpr$64 = Py.newCode(2, var2, var1, "visitGenExpr", 656, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "stack", "i", "for_", "start", "anchor", "end", "cont", "if_", "skip_one"};
      visitGenExprInner$65 = Py.newCode(2, var2, var1, "visitGenExprInner", 668, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "start", "anchor", "end"};
      visitGenExprFor$66 = Py.newCode(2, var2, var1, "visitGenExprFor", 701, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "branch"};
      visitGenExprIf$67 = Py.newCode(3, var2, var1, "visitGenExprIf", 722, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "end"};
      visitAssert$68 = Py.newCode(2, var2, var1, "visitAssert", 731, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "n"};
      visitRaise$69 = Py.newCode(2, var2, var1, "visitRaise", 754, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "body", "handlers", "end", "lElse", "last", "i", "expr", "target", "next"};
      visitTryExcept$70 = Py.newCode(2, var2, var1, "visitTryExcept", 768, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "body", "final"};
      visitTryFinally$71 = Py.newCode(2, var2, var1, "visitTryFinally", 818, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "body", "final", "exitvar", "valuevar"};
      visitWith$72 = Py.newCode(2, var2, var1, "visitWith", 837, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitDiscard$73 = Py.newCode(2, var2, var1, "visitDiscard", 876, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitConst$74 = Py.newCode(2, var2, var1, "visitConst", 881, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitKeyword$75 = Py.newCode(2, var2, var1, "visitKeyword", 884, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitGlobal$76 = Py.newCode(2, var2, var1, "visitGlobal", 888, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitName$77 = Py.newCode(2, var2, var1, "visitName", 892, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitPass$78 = Py.newCode(2, var2, var1, "visitPass", 896, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "level", "name", "alias", "mod"};
      visitImport$79 = Py.newCode(2, var2, var1, "visitImport", 899, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "level", "fromlist", "name", "alias"};
      visitFrom$80 = Py.newCode(2, var2, var1, "visitFrom", 914, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"(name, alias)", "name", "alias"};
      f$81 = Py.newCode(1, var2, var1, "<lambda>", 919, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "elts", "elt"};
      _resolveDots$82 = Py.newCode(2, var2, var1, "_resolveDots", 940, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitGetattr$83 = Py.newCode(2, var2, var1, "visitGetattr", 947, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "dups", "i", "elt"};
      visitAssign$84 = Py.newCode(2, var2, var1, "visitAssign", 953, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssName$85 = Py.newCode(2, var2, var1, "visitAssName", 964, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssAttr$86 = Py.newCode(2, var2, var1, "visitAssAttr", 973, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "op", "child"};
      _visitAssSequence$87 = Py.newCode(3, var2, var1, "_visitAssSequence", 983, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssTuple$88 = Py.newCode(2, var2, var1, "visitAssTuple", 993, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssList$89 = Py.newCode(2, var2, var1, "visitAssList", 996, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "aug_node"};
      visitAugAssign$90 = Py.newCode(2, var2, var1, "visitAugAssign", 1001, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "mode"};
      visitAugName$91 = Py.newCode(3, var2, var1, "visitAugName", 1024, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "mode"};
      visitAugGetattr$92 = Py.newCode(3, var2, var1, "visitAugGetattr", 1030, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "mode", "slice"};
      visitAugSlice$93 = Py.newCode(3, var2, var1, "visitAugSlice", 1039, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "mode"};
      visitAugSubscript$94 = Py.newCode(3, var2, var1, "visitAugSubscript", 1056, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitExec$95 = Py.newCode(2, var2, var1, "visitExec", 1063, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "pos", "kw", "arg", "have_star", "have_dstar", "opcode"};
      visitCallFunc$96 = Py.newCode(2, var2, var1, "visitCallFunc", 1075, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "newline", "child"};
      visitPrint$97 = Py.newCode(3, var2, var1, "visitPrint", 1095, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitPrintnl$98 = Py.newCode(2, var2, var1, "visitPrintnl", 1111, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitReturn$99 = Py.newCode(2, var2, var1, "visitReturn", 1118, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitYield$100 = Py.newCode(2, var2, var1, "visitYield", 1123, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "aug_flag", "slice"};
      visitSlice$101 = Py.newCode(3, var2, var1, "visitSlice", 1130, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "aug_flag", "sub"};
      visitSubscript$102 = Py.newCode(3, var2, var1, "visitSubscript", 1157, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "op"};
      binaryOp$103 = Py.newCode(3, var2, var1, "binaryOp", 1174, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAdd$104 = Py.newCode(2, var2, var1, "visitAdd", 1179, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitSub$105 = Py.newCode(2, var2, var1, "visitSub", 1182, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitMul$106 = Py.newCode(2, var2, var1, "visitMul", 1185, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitDiv$107 = Py.newCode(2, var2, var1, "visitDiv", 1188, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitFloorDiv$108 = Py.newCode(2, var2, var1, "visitFloorDiv", 1191, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitMod$109 = Py.newCode(2, var2, var1, "visitMod", 1194, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitPower$110 = Py.newCode(2, var2, var1, "visitPower", 1197, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitLeftShift$111 = Py.newCode(2, var2, var1, "visitLeftShift", 1200, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitRightShift$112 = Py.newCode(2, var2, var1, "visitRightShift", 1203, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "op"};
      unaryOp$113 = Py.newCode(3, var2, var1, "unaryOp", 1208, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitInvert$114 = Py.newCode(2, var2, var1, "visitInvert", 1212, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitUnarySub$115 = Py.newCode(2, var2, var1, "visitUnarySub", 1215, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitUnaryAdd$116 = Py.newCode(2, var2, var1, "visitUnaryAdd", 1218, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitUnaryInvert$117 = Py.newCode(2, var2, var1, "visitUnaryInvert", 1221, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitNot$118 = Py.newCode(2, var2, var1, "visitNot", 1224, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitBackquote$119 = Py.newCode(2, var2, var1, "visitBackquote", 1227, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodes", "op", "node"};
      bitOp$120 = Py.newCode(3, var2, var1, "bitOp", 1232, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitBitand$121 = Py.newCode(2, var2, var1, "visitBitand", 1238, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitBitor$122 = Py.newCode(2, var2, var1, "visitBitor", 1241, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitBitxor$123 = Py.newCode(2, var2, var1, "visitBitxor", 1244, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitEllipsis$124 = Py.newCode(2, var2, var1, "visitEllipsis", 1249, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "elt"};
      visitTuple$125 = Py.newCode(2, var2, var1, "visitTuple", 1252, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "elt"};
      visitList$126 = Py.newCode(2, var2, var1, "visitList", 1258, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "child"};
      visitSliceobj$127 = Py.newCode(2, var2, var1, "visitSliceobj", 1264, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "k", "v"};
      visitDict$128 = Py.newCode(2, var2, var1, "visitDict", 1269, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NestedScopeMixin$129 = Py.newCode(0, var2, var1, "NestedScopeMixin", 1279, false, false, self, 129, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initClass$130 = Py.newCode(1, var2, var1, "initClass", 1281, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ModuleCodeGenerator$131 = Py.newCode(0, var2, var1, "ModuleCodeGenerator", 1286, false, false, self, 131, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree"};
      __init__$132 = Py.newCode(2, var2, var1, "__init__", 1291, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_module$133 = Py.newCode(1, var2, var1, "get_module", 1297, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExpressionCodeGenerator$134 = Py.newCode(0, var2, var1, "ExpressionCodeGenerator", 1300, false, false, self, 134, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree"};
      __init__$135 = Py.newCode(2, var2, var1, "__init__", 1306, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_module$136 = Py.newCode(1, var2, var1, "get_module", 1311, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InteractiveCodeGenerator$137 = Py.newCode(0, var2, var1, "InteractiveCodeGenerator", 1314, false, false, self, 137, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree"};
      __init__$138 = Py.newCode(2, var2, var1, "__init__", 1321, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_module$139 = Py.newCode(1, var2, var1, "get_module", 1328, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitDiscard$140 = Py.newCode(2, var2, var1, "visitDiscard", 1331, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractFunctionCode$141 = Py.newCode(0, var2, var1, "AbstractFunctionCode", 1337, false, false, self, 141, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "func", "scopes", "isLambda", "class_name", "mod", "klass", "name", "args", "hasTupleArg", "lnf"};
      __init__$142 = Py.newCode(6, var2, var1, "__init__", 1341, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_module$143 = Py.newCode(1, var2, var1, "get_module", 1370, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finish$144 = Py.newCode(1, var2, var1, "finish", 1373, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "i", "arg"};
      generateArgUnpack$145 = Py.newCode(2, var2, var1, "generateArgUnpack", 1379, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tup", "elt"};
      unpackSequence$146 = Py.newCode(2, var2, var1, "unpackSequence", 1386, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FunctionCodeGenerator$147 = Py.newCode(0, var2, var1, "FunctionCodeGenerator", 1399, false, false, self, 147, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "func", "scopes", "isLambda", "class_name", "mod"};
      __init__$148 = Py.newCode(6, var2, var1, "__init__", 1406, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GenExprCodeGenerator$149 = Py.newCode(0, var2, var1, "GenExprCodeGenerator", 1415, false, false, self, 149, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "gexp", "scopes", "class_name", "mod"};
      __init__$150 = Py.newCode(5, var2, var1, "__init__", 1422, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractClassCode$151 = Py.newCode(0, var2, var1, "AbstractClassCode", 1430, false, false, self, 151, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "klass", "scopes", "module", "lnf"};
      __init__$152 = Py.newCode(4, var2, var1, "__init__", 1432, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_module$153 = Py.newCode(1, var2, var1, "get_module", 1444, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finish$154 = Py.newCode(1, var2, var1, "finish", 1447, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ClassCodeGenerator$155 = Py.newCode(0, var2, var1, "ClassCodeGenerator", 1452, false, false, self, 155, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "klass", "scopes", "module"};
      __init__$156 = Py.newCode(4, var2, var1, "__init__", 1458, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"arglist", "args", "extra", "count", "i", "elt"};
      generateArgList$157 = Py.newCode(1, var2, var1, "generateArgList", 1471, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "v"};
      findOp$158 = Py.newCode(1, var2, var1, "findOp", 1488, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OpFinder$159 = Py.newCode(0, var2, var1, "OpFinder", 1494, false, false, self, 159, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$160 = Py.newCode(1, var2, var1, "__init__", 1495, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      visitAssName$161 = Py.newCode(2, var2, var1, "visitAssName", 1497, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Delegator$162 = Py.newCode(0, var2, var1, "Delegator", 1505, false, false, self, 162, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "obj"};
      __init__$163 = Py.newCode(2, var2, var1, "__init__", 1516, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$164 = Py.newCode(2, var2, var1, "__getattr__", 1519, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AugGetattr$165 = Py.newCode(0, var2, var1, "AugGetattr", 1522, false, false, self, 165, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      AugName$166 = Py.newCode(0, var2, var1, "AugName", 1525, false, false, self, 166, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      AugSlice$167 = Py.newCode(0, var2, var1, "AugSlice", 1528, false, false, self, 167, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      AugSubscript$168 = Py.newCode(0, var2, var1, "AugSubscript", 1531, false, false, self, 168, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"node"};
      wrap_aug$169 = Py.newCode(1, var2, var1, "wrap_aug", 1541, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pycodegen$py("compiler/pycodegen$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pycodegen$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.compileFile$1(var2, var3);
         case 2:
            return this.compile$2(var2, var3);
         case 3:
            return this.AbstractCompileMode$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this._get_tree$5(var2, var3);
         case 6:
            return this.compile$6(var2, var3);
         case 7:
            return this.getCode$7(var2, var3);
         case 8:
            return this.Expression$8(var2, var3);
         case 9:
            return this.compile$9(var2, var3);
         case 10:
            return this.Interactive$10(var2, var3);
         case 11:
            return this.compile$11(var2, var3);
         case 12:
            return this.Module$12(var2, var3);
         case 13:
            return this.compile$13(var2, var3);
         case 14:
            return this.dump$14(var2, var3);
         case 15:
            return this.getPycHeader$15(var2, var3);
         case 16:
            return this.LocalNameFinder$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.getLocals$18(var2, var3);
         case 19:
            return this.visitDict$19(var2, var3);
         case 20:
            return this.visitGlobal$20(var2, var3);
         case 21:
            return this.visitFunction$21(var2, var3);
         case 22:
            return this.visitLambda$22(var2, var3);
         case 23:
            return this.visitImport$23(var2, var3);
         case 24:
            return this.visitFrom$24(var2, var3);
         case 25:
            return this.visitClass$25(var2, var3);
         case 26:
            return this.visitAssName$26(var2, var3);
         case 27:
            return this.is_constant_false$27(var2, var3);
         case 28:
            return this.CodeGenerator$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.initClass$30(var2, var3);
         case 31:
            return this.checkClass$31(var2, var3);
         case 32:
            return this._setupGraphDelegation$32(var2, var3);
         case 33:
            return this.getCode$33(var2, var3);
         case 34:
            return this.mangle$34(var2, var3);
         case 35:
            return this.parseSymbols$35(var2, var3);
         case 36:
            return this.get_module$36(var2, var3);
         case 37:
            return this.isLocalName$37(var2, var3);
         case 38:
            return this.storeName$38(var2, var3);
         case 39:
            return this.loadName$39(var2, var3);
         case 40:
            return this.delName$40(var2, var3);
         case 41:
            return this._nameOp$41(var2, var3);
         case 42:
            return this._implicitNameOp$42(var2, var3);
         case 43:
            return this.set_lineno$43(var2, var3);
         case 44:
            return this.visitModule$44(var2, var3);
         case 45:
            return this.visitExpression$45(var2, var3);
         case 46:
            return this.visitFunction$46(var2, var3);
         case 47:
            return this.visitLambda$47(var2, var3);
         case 48:
            return this._visitFuncOrLambda$48(var2, var3);
         case 49:
            return this.visitClass$49(var2, var3);
         case 50:
            return this.visitIf$50(var2, var3);
         case 51:
            return this.visitWhile$51(var2, var3);
         case 52:
            return this.visitFor$52(var2, var3);
         case 53:
            return this.visitBreak$53(var2, var3);
         case 54:
            return this.visitContinue$54(var2, var3);
         case 55:
            return this.visitTest$55(var2, var3);
         case 56:
            return this.visitAnd$56(var2, var3);
         case 57:
            return this.visitOr$57(var2, var3);
         case 58:
            return this.visitIfExp$58(var2, var3);
         case 59:
            return this.visitCompare$59(var2, var3);
         case 60:
            return this.visitListComp$60(var2, var3);
         case 61:
            return this.visitListCompFor$61(var2, var3);
         case 62:
            return this.visitListCompIf$62(var2, var3);
         case 63:
            return this._makeClosure$63(var2, var3);
         case 64:
            return this.visitGenExpr$64(var2, var3);
         case 65:
            return this.visitGenExprInner$65(var2, var3);
         case 66:
            return this.visitGenExprFor$66(var2, var3);
         case 67:
            return this.visitGenExprIf$67(var2, var3);
         case 68:
            return this.visitAssert$68(var2, var3);
         case 69:
            return this.visitRaise$69(var2, var3);
         case 70:
            return this.visitTryExcept$70(var2, var3);
         case 71:
            return this.visitTryFinally$71(var2, var3);
         case 72:
            return this.visitWith$72(var2, var3);
         case 73:
            return this.visitDiscard$73(var2, var3);
         case 74:
            return this.visitConst$74(var2, var3);
         case 75:
            return this.visitKeyword$75(var2, var3);
         case 76:
            return this.visitGlobal$76(var2, var3);
         case 77:
            return this.visitName$77(var2, var3);
         case 78:
            return this.visitPass$78(var2, var3);
         case 79:
            return this.visitImport$79(var2, var3);
         case 80:
            return this.visitFrom$80(var2, var3);
         case 81:
            return this.f$81(var2, var3);
         case 82:
            return this._resolveDots$82(var2, var3);
         case 83:
            return this.visitGetattr$83(var2, var3);
         case 84:
            return this.visitAssign$84(var2, var3);
         case 85:
            return this.visitAssName$85(var2, var3);
         case 86:
            return this.visitAssAttr$86(var2, var3);
         case 87:
            return this._visitAssSequence$87(var2, var3);
         case 88:
            return this.visitAssTuple$88(var2, var3);
         case 89:
            return this.visitAssList$89(var2, var3);
         case 90:
            return this.visitAugAssign$90(var2, var3);
         case 91:
            return this.visitAugName$91(var2, var3);
         case 92:
            return this.visitAugGetattr$92(var2, var3);
         case 93:
            return this.visitAugSlice$93(var2, var3);
         case 94:
            return this.visitAugSubscript$94(var2, var3);
         case 95:
            return this.visitExec$95(var2, var3);
         case 96:
            return this.visitCallFunc$96(var2, var3);
         case 97:
            return this.visitPrint$97(var2, var3);
         case 98:
            return this.visitPrintnl$98(var2, var3);
         case 99:
            return this.visitReturn$99(var2, var3);
         case 100:
            return this.visitYield$100(var2, var3);
         case 101:
            return this.visitSlice$101(var2, var3);
         case 102:
            return this.visitSubscript$102(var2, var3);
         case 103:
            return this.binaryOp$103(var2, var3);
         case 104:
            return this.visitAdd$104(var2, var3);
         case 105:
            return this.visitSub$105(var2, var3);
         case 106:
            return this.visitMul$106(var2, var3);
         case 107:
            return this.visitDiv$107(var2, var3);
         case 108:
            return this.visitFloorDiv$108(var2, var3);
         case 109:
            return this.visitMod$109(var2, var3);
         case 110:
            return this.visitPower$110(var2, var3);
         case 111:
            return this.visitLeftShift$111(var2, var3);
         case 112:
            return this.visitRightShift$112(var2, var3);
         case 113:
            return this.unaryOp$113(var2, var3);
         case 114:
            return this.visitInvert$114(var2, var3);
         case 115:
            return this.visitUnarySub$115(var2, var3);
         case 116:
            return this.visitUnaryAdd$116(var2, var3);
         case 117:
            return this.visitUnaryInvert$117(var2, var3);
         case 118:
            return this.visitNot$118(var2, var3);
         case 119:
            return this.visitBackquote$119(var2, var3);
         case 120:
            return this.bitOp$120(var2, var3);
         case 121:
            return this.visitBitand$121(var2, var3);
         case 122:
            return this.visitBitor$122(var2, var3);
         case 123:
            return this.visitBitxor$123(var2, var3);
         case 124:
            return this.visitEllipsis$124(var2, var3);
         case 125:
            return this.visitTuple$125(var2, var3);
         case 126:
            return this.visitList$126(var2, var3);
         case 127:
            return this.visitSliceobj$127(var2, var3);
         case 128:
            return this.visitDict$128(var2, var3);
         case 129:
            return this.NestedScopeMixin$129(var2, var3);
         case 130:
            return this.initClass$130(var2, var3);
         case 131:
            return this.ModuleCodeGenerator$131(var2, var3);
         case 132:
            return this.__init__$132(var2, var3);
         case 133:
            return this.get_module$133(var2, var3);
         case 134:
            return this.ExpressionCodeGenerator$134(var2, var3);
         case 135:
            return this.__init__$135(var2, var3);
         case 136:
            return this.get_module$136(var2, var3);
         case 137:
            return this.InteractiveCodeGenerator$137(var2, var3);
         case 138:
            return this.__init__$138(var2, var3);
         case 139:
            return this.get_module$139(var2, var3);
         case 140:
            return this.visitDiscard$140(var2, var3);
         case 141:
            return this.AbstractFunctionCode$141(var2, var3);
         case 142:
            return this.__init__$142(var2, var3);
         case 143:
            return this.get_module$143(var2, var3);
         case 144:
            return this.finish$144(var2, var3);
         case 145:
            return this.generateArgUnpack$145(var2, var3);
         case 146:
            return this.unpackSequence$146(var2, var3);
         case 147:
            return this.FunctionCodeGenerator$147(var2, var3);
         case 148:
            return this.__init__$148(var2, var3);
         case 149:
            return this.GenExprCodeGenerator$149(var2, var3);
         case 150:
            return this.__init__$150(var2, var3);
         case 151:
            return this.AbstractClassCode$151(var2, var3);
         case 152:
            return this.__init__$152(var2, var3);
         case 153:
            return this.get_module$153(var2, var3);
         case 154:
            return this.finish$154(var2, var3);
         case 155:
            return this.ClassCodeGenerator$155(var2, var3);
         case 156:
            return this.__init__$156(var2, var3);
         case 157:
            return this.generateArgList$157(var2, var3);
         case 158:
            return this.findOp$158(var2, var3);
         case 159:
            return this.OpFinder$159(var2, var3);
         case 160:
            return this.__init__$160(var2, var3);
         case 161:
            return this.visitAssName$161(var2, var3);
         case 162:
            return this.Delegator$162(var2, var3);
         case 163:
            return this.__init__$163(var2, var3);
         case 164:
            return this.__getattr__$164(var2, var3);
         case 165:
            return this.AugGetattr$165(var2, var3);
         case 166:
            return this.AugName$166(var2, var3);
         case 167:
            return this.AugSlice$167(var2, var3);
         case 168:
            return this.AugSubscript$168(var2, var3);
         case 169:
            return this.wrap_aug$169(var2, var3);
         default:
            return null;
      }
   }
}
