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
@MTime(1498849383000L)
@Filename("compiler/ast.py")
public class ast$py extends PyFunctionTable implements PyRunnable {
   static compiler.ast$py self;
   static final PyCode f$0;
   static final PyCode flatten$1;
   static final PyCode flatten_nodes$2;
   static final PyCode Node$3;
   static final PyCode getChildren$4;
   static final PyCode __iter__$5;
   static final PyCode asList$6;
   static final PyCode getChildNodes$7;
   static final PyCode EmptyNode$8;
   static final PyCode Expression$9;
   static final PyCode __init__$10;
   static final PyCode getChildren$11;
   static final PyCode getChildNodes$12;
   static final PyCode __repr__$13;
   static final PyCode Add$14;
   static final PyCode __init__$15;
   static final PyCode getChildren$16;
   static final PyCode getChildNodes$17;
   static final PyCode __repr__$18;
   static final PyCode And$19;
   static final PyCode __init__$20;
   static final PyCode getChildren$21;
   static final PyCode getChildNodes$22;
   static final PyCode __repr__$23;
   static final PyCode AssAttr$24;
   static final PyCode __init__$25;
   static final PyCode getChildren$26;
   static final PyCode getChildNodes$27;
   static final PyCode __repr__$28;
   static final PyCode AssList$29;
   static final PyCode __init__$30;
   static final PyCode getChildren$31;
   static final PyCode getChildNodes$32;
   static final PyCode __repr__$33;
   static final PyCode AssName$34;
   static final PyCode __init__$35;
   static final PyCode getChildren$36;
   static final PyCode getChildNodes$37;
   static final PyCode __repr__$38;
   static final PyCode AssTuple$39;
   static final PyCode __init__$40;
   static final PyCode getChildren$41;
   static final PyCode getChildNodes$42;
   static final PyCode __repr__$43;
   static final PyCode Assert$44;
   static final PyCode __init__$45;
   static final PyCode getChildren$46;
   static final PyCode getChildNodes$47;
   static final PyCode __repr__$48;
   static final PyCode Assign$49;
   static final PyCode __init__$50;
   static final PyCode getChildren$51;
   static final PyCode getChildNodes$52;
   static final PyCode __repr__$53;
   static final PyCode AugAssign$54;
   static final PyCode __init__$55;
   static final PyCode getChildren$56;
   static final PyCode getChildNodes$57;
   static final PyCode __repr__$58;
   static final PyCode Backquote$59;
   static final PyCode __init__$60;
   static final PyCode getChildren$61;
   static final PyCode getChildNodes$62;
   static final PyCode __repr__$63;
   static final PyCode Bitand$64;
   static final PyCode __init__$65;
   static final PyCode getChildren$66;
   static final PyCode getChildNodes$67;
   static final PyCode __repr__$68;
   static final PyCode Bitor$69;
   static final PyCode __init__$70;
   static final PyCode getChildren$71;
   static final PyCode getChildNodes$72;
   static final PyCode __repr__$73;
   static final PyCode Bitxor$74;
   static final PyCode __init__$75;
   static final PyCode getChildren$76;
   static final PyCode getChildNodes$77;
   static final PyCode __repr__$78;
   static final PyCode Break$79;
   static final PyCode __init__$80;
   static final PyCode getChildren$81;
   static final PyCode getChildNodes$82;
   static final PyCode __repr__$83;
   static final PyCode CallFunc$84;
   static final PyCode __init__$85;
   static final PyCode getChildren$86;
   static final PyCode getChildNodes$87;
   static final PyCode __repr__$88;
   static final PyCode Class$89;
   static final PyCode __init__$90;
   static final PyCode getChildren$91;
   static final PyCode getChildNodes$92;
   static final PyCode __repr__$93;
   static final PyCode Compare$94;
   static final PyCode __init__$95;
   static final PyCode getChildren$96;
   static final PyCode getChildNodes$97;
   static final PyCode __repr__$98;
   static final PyCode Const$99;
   static final PyCode __init__$100;
   static final PyCode getChildren$101;
   static final PyCode getChildNodes$102;
   static final PyCode __repr__$103;
   static final PyCode Continue$104;
   static final PyCode __init__$105;
   static final PyCode getChildren$106;
   static final PyCode getChildNodes$107;
   static final PyCode __repr__$108;
   static final PyCode Decorators$109;
   static final PyCode __init__$110;
   static final PyCode getChildren$111;
   static final PyCode getChildNodes$112;
   static final PyCode __repr__$113;
   static final PyCode Dict$114;
   static final PyCode __init__$115;
   static final PyCode getChildren$116;
   static final PyCode getChildNodes$117;
   static final PyCode __repr__$118;
   static final PyCode Discard$119;
   static final PyCode __init__$120;
   static final PyCode getChildren$121;
   static final PyCode getChildNodes$122;
   static final PyCode __repr__$123;
   static final PyCode Div$124;
   static final PyCode __init__$125;
   static final PyCode getChildren$126;
   static final PyCode getChildNodes$127;
   static final PyCode __repr__$128;
   static final PyCode Ellipsis$129;
   static final PyCode __init__$130;
   static final PyCode getChildren$131;
   static final PyCode getChildNodes$132;
   static final PyCode __repr__$133;
   static final PyCode Exec$134;
   static final PyCode __init__$135;
   static final PyCode getChildren$136;
   static final PyCode getChildNodes$137;
   static final PyCode __repr__$138;
   static final PyCode FloorDiv$139;
   static final PyCode __init__$140;
   static final PyCode getChildren$141;
   static final PyCode getChildNodes$142;
   static final PyCode __repr__$143;
   static final PyCode For$144;
   static final PyCode __init__$145;
   static final PyCode getChildren$146;
   static final PyCode getChildNodes$147;
   static final PyCode __repr__$148;
   static final PyCode From$149;
   static final PyCode __init__$150;
   static final PyCode getChildren$151;
   static final PyCode getChildNodes$152;
   static final PyCode __repr__$153;
   static final PyCode Function$154;
   static final PyCode __init__$155;
   static final PyCode getChildren$156;
   static final PyCode getChildNodes$157;
   static final PyCode __repr__$158;
   static final PyCode GenExpr$159;
   static final PyCode __init__$160;
   static final PyCode getChildren$161;
   static final PyCode getChildNodes$162;
   static final PyCode __repr__$163;
   static final PyCode GenExprFor$164;
   static final PyCode __init__$165;
   static final PyCode getChildren$166;
   static final PyCode getChildNodes$167;
   static final PyCode __repr__$168;
   static final PyCode GenExprIf$169;
   static final PyCode __init__$170;
   static final PyCode getChildren$171;
   static final PyCode getChildNodes$172;
   static final PyCode __repr__$173;
   static final PyCode GenExprInner$174;
   static final PyCode __init__$175;
   static final PyCode getChildren$176;
   static final PyCode getChildNodes$177;
   static final PyCode __repr__$178;
   static final PyCode Getattr$179;
   static final PyCode __init__$180;
   static final PyCode getChildren$181;
   static final PyCode getChildNodes$182;
   static final PyCode __repr__$183;
   static final PyCode Global$184;
   static final PyCode __init__$185;
   static final PyCode getChildren$186;
   static final PyCode getChildNodes$187;
   static final PyCode __repr__$188;
   static final PyCode If$189;
   static final PyCode __init__$190;
   static final PyCode getChildren$191;
   static final PyCode getChildNodes$192;
   static final PyCode __repr__$193;
   static final PyCode IfExp$194;
   static final PyCode __init__$195;
   static final PyCode getChildren$196;
   static final PyCode getChildNodes$197;
   static final PyCode __repr__$198;
   static final PyCode Import$199;
   static final PyCode __init__$200;
   static final PyCode getChildren$201;
   static final PyCode getChildNodes$202;
   static final PyCode __repr__$203;
   static final PyCode Invert$204;
   static final PyCode __init__$205;
   static final PyCode getChildren$206;
   static final PyCode getChildNodes$207;
   static final PyCode __repr__$208;
   static final PyCode Keyword$209;
   static final PyCode __init__$210;
   static final PyCode getChildren$211;
   static final PyCode getChildNodes$212;
   static final PyCode __repr__$213;
   static final PyCode Lambda$214;
   static final PyCode __init__$215;
   static final PyCode getChildren$216;
   static final PyCode getChildNodes$217;
   static final PyCode __repr__$218;
   static final PyCode LeftShift$219;
   static final PyCode __init__$220;
   static final PyCode getChildren$221;
   static final PyCode getChildNodes$222;
   static final PyCode __repr__$223;
   static final PyCode List$224;
   static final PyCode __init__$225;
   static final PyCode getChildren$226;
   static final PyCode getChildNodes$227;
   static final PyCode __repr__$228;
   static final PyCode ListComp$229;
   static final PyCode __init__$230;
   static final PyCode getChildren$231;
   static final PyCode getChildNodes$232;
   static final PyCode __repr__$233;
   static final PyCode ListCompFor$234;
   static final PyCode __init__$235;
   static final PyCode getChildren$236;
   static final PyCode getChildNodes$237;
   static final PyCode __repr__$238;
   static final PyCode ListCompIf$239;
   static final PyCode __init__$240;
   static final PyCode getChildren$241;
   static final PyCode getChildNodes$242;
   static final PyCode __repr__$243;
   static final PyCode SetComp$244;
   static final PyCode __init__$245;
   static final PyCode getChildren$246;
   static final PyCode getChildNodes$247;
   static final PyCode __repr__$248;
   static final PyCode DictComp$249;
   static final PyCode __init__$250;
   static final PyCode getChildren$251;
   static final PyCode getChildNodes$252;
   static final PyCode __repr__$253;
   static final PyCode Mod$254;
   static final PyCode __init__$255;
   static final PyCode getChildren$256;
   static final PyCode getChildNodes$257;
   static final PyCode __repr__$258;
   static final PyCode Module$259;
   static final PyCode __init__$260;
   static final PyCode getChildren$261;
   static final PyCode getChildNodes$262;
   static final PyCode __repr__$263;
   static final PyCode Mul$264;
   static final PyCode __init__$265;
   static final PyCode getChildren$266;
   static final PyCode getChildNodes$267;
   static final PyCode __repr__$268;
   static final PyCode Name$269;
   static final PyCode __init__$270;
   static final PyCode getChildren$271;
   static final PyCode getChildNodes$272;
   static final PyCode __repr__$273;
   static final PyCode Not$274;
   static final PyCode __init__$275;
   static final PyCode getChildren$276;
   static final PyCode getChildNodes$277;
   static final PyCode __repr__$278;
   static final PyCode Or$279;
   static final PyCode __init__$280;
   static final PyCode getChildren$281;
   static final PyCode getChildNodes$282;
   static final PyCode __repr__$283;
   static final PyCode Pass$284;
   static final PyCode __init__$285;
   static final PyCode getChildren$286;
   static final PyCode getChildNodes$287;
   static final PyCode __repr__$288;
   static final PyCode Power$289;
   static final PyCode __init__$290;
   static final PyCode getChildren$291;
   static final PyCode getChildNodes$292;
   static final PyCode __repr__$293;
   static final PyCode Print$294;
   static final PyCode __init__$295;
   static final PyCode getChildren$296;
   static final PyCode getChildNodes$297;
   static final PyCode __repr__$298;
   static final PyCode Printnl$299;
   static final PyCode __init__$300;
   static final PyCode getChildren$301;
   static final PyCode getChildNodes$302;
   static final PyCode __repr__$303;
   static final PyCode Raise$304;
   static final PyCode __init__$305;
   static final PyCode getChildren$306;
   static final PyCode getChildNodes$307;
   static final PyCode __repr__$308;
   static final PyCode Return$309;
   static final PyCode __init__$310;
   static final PyCode getChildren$311;
   static final PyCode getChildNodes$312;
   static final PyCode __repr__$313;
   static final PyCode RightShift$314;
   static final PyCode __init__$315;
   static final PyCode getChildren$316;
   static final PyCode getChildNodes$317;
   static final PyCode __repr__$318;
   static final PyCode Set$319;
   static final PyCode __init__$320;
   static final PyCode getChildren$321;
   static final PyCode getChildNodes$322;
   static final PyCode __repr__$323;
   static final PyCode Slice$324;
   static final PyCode __init__$325;
   static final PyCode getChildren$326;
   static final PyCode getChildNodes$327;
   static final PyCode __repr__$328;
   static final PyCode Sliceobj$329;
   static final PyCode __init__$330;
   static final PyCode getChildren$331;
   static final PyCode getChildNodes$332;
   static final PyCode __repr__$333;
   static final PyCode Stmt$334;
   static final PyCode __init__$335;
   static final PyCode getChildren$336;
   static final PyCode getChildNodes$337;
   static final PyCode __repr__$338;
   static final PyCode Sub$339;
   static final PyCode __init__$340;
   static final PyCode getChildren$341;
   static final PyCode getChildNodes$342;
   static final PyCode __repr__$343;
   static final PyCode Subscript$344;
   static final PyCode __init__$345;
   static final PyCode getChildren$346;
   static final PyCode getChildNodes$347;
   static final PyCode __repr__$348;
   static final PyCode TryExcept$349;
   static final PyCode __init__$350;
   static final PyCode getChildren$351;
   static final PyCode getChildNodes$352;
   static final PyCode __repr__$353;
   static final PyCode TryFinally$354;
   static final PyCode __init__$355;
   static final PyCode getChildren$356;
   static final PyCode getChildNodes$357;
   static final PyCode __repr__$358;
   static final PyCode Tuple$359;
   static final PyCode __init__$360;
   static final PyCode getChildren$361;
   static final PyCode getChildNodes$362;
   static final PyCode __repr__$363;
   static final PyCode UnaryAdd$364;
   static final PyCode __init__$365;
   static final PyCode getChildren$366;
   static final PyCode getChildNodes$367;
   static final PyCode __repr__$368;
   static final PyCode UnarySub$369;
   static final PyCode __init__$370;
   static final PyCode getChildren$371;
   static final PyCode getChildNodes$372;
   static final PyCode __repr__$373;
   static final PyCode While$374;
   static final PyCode __init__$375;
   static final PyCode getChildren$376;
   static final PyCode getChildNodes$377;
   static final PyCode __repr__$378;
   static final PyCode With$379;
   static final PyCode __init__$380;
   static final PyCode getChildren$381;
   static final PyCode getChildNodes$382;
   static final PyCode __repr__$383;
   static final PyCode Yield$384;
   static final PyCode __init__$385;
   static final PyCode getChildren$386;
   static final PyCode getChildNodes$387;
   static final PyCode __repr__$388;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Python abstract syntax node definitions\n\nThis file is automatically generated by Tools/compiler/astgen.py\n"));
      var1.setline(4);
      PyString.fromInterned("Python abstract syntax node definitions\n\nThis file is automatically generated by Tools/compiler/astgen.py\n");
      var1.setline(5);
      String[] var3 = new String[]{"CO_VARARGS", "CO_VARKEYWORDS"};
      PyObject[] var7 = imp.importFrom("compiler.consts", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("CO_VARARGS", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("CO_VARKEYWORDS", var4);
      var4 = null;
      var1.setline(7);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, flatten$1, (PyObject)null);
      var1.setlocal("flatten", var8);
      var3 = null;
      var1.setline(18);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, flatten_nodes$2, (PyObject)null);
      var1.setlocal("flatten_nodes", var8);
      var3 = null;
      var1.setline(21);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("nodes", var10);
      var3 = null;
      var1.setline(23);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Node", var7, Node$3);
      var1.setlocal("Node", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(35);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("EmptyNode", var7, EmptyNode$8);
      var1.setlocal("EmptyNode", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(38);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Expression", var7, Expression$9);
      var1.setlocal("Expression", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(53);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Add", var7, Add$14);
      var1.setlocal("Add", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(68);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("And", var7, And$19);
      var1.setlocal("And", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(84);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("AssAttr", var7, AssAttr$24);
      var1.setlocal("AssAttr", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(100);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("AssList", var7, AssList$29);
      var1.setlocal("AssList", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(116);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("AssName", var7, AssName$34);
      var1.setlocal("AssName", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(131);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("AssTuple", var7, AssTuple$39);
      var1.setlocal("AssTuple", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(147);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Assert", var7, Assert$44);
      var1.setlocal("Assert", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(169);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Assign", var7, Assign$49);
      var1.setlocal("Assign", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(190);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("AugAssign", var7, AugAssign$54);
      var1.setlocal("AugAssign", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(206);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Backquote", var7, Backquote$59);
      var1.setlocal("Backquote", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(220);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Bitand", var7, Bitand$64);
      var1.setlocal("Bitand", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(236);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Bitor", var7, Bitor$69);
      var1.setlocal("Bitor", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(252);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Bitxor", var7, Bitxor$74);
      var1.setlocal("Bitxor", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(268);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Break", var7, Break$79);
      var1.setlocal("Break", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(281);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("CallFunc", var7, CallFunc$84);
      var1.setlocal("CallFunc", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(310);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Class", var7, Class$89);
      var1.setlocal("Class", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(339);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Compare", var7, Compare$94);
      var1.setlocal("Compare", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(360);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Const", var7, Const$99);
      var1.setlocal("Const", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(374);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Continue", var7, Continue$104);
      var1.setlocal("Continue", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(387);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Decorators", var7, Decorators$109);
      var1.setlocal("Decorators", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(403);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Dict", var7, Dict$114);
      var1.setlocal("Dict", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(419);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Discard", var7, Discard$119);
      var1.setlocal("Discard", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(433);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Div", var7, Div$124);
      var1.setlocal("Div", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(448);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Ellipsis", var7, Ellipsis$129);
      var1.setlocal("Ellipsis", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(461);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Exec", var7, Exec$134);
      var1.setlocal("Exec", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(487);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("FloorDiv", var7, FloorDiv$139);
      var1.setlocal("FloorDiv", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(502);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("For", var7, For$144);
      var1.setlocal("For", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(530);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("From", var7, From$149);
      var1.setlocal("From", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(546);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Function", var7, Function$154);
      var1.setlocal("Function", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(585);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("GenExpr", var7, GenExpr$159);
      var1.setlocal("GenExpr", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(602);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("GenExprFor", var7, GenExprFor$164);
      var1.setlocal("GenExprFor", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(627);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("GenExprIf", var7, GenExprIf$169);
      var1.setlocal("GenExprIf", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(641);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("GenExprInner", var7, GenExprInner$174);
      var1.setlocal("GenExprInner", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(662);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Getattr", var7, Getattr$179);
      var1.setlocal("Getattr", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(677);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Global", var7, Global$184);
      var1.setlocal("Global", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(691);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("If", var7, If$189);
      var1.setlocal("If", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(713);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("IfExp", var7, IfExp$194);
      var1.setlocal("IfExp", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(729);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Import", var7, Import$199);
      var1.setlocal("Import", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(743);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Invert", var7, Invert$204);
      var1.setlocal("Invert", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(757);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Keyword", var7, Keyword$209);
      var1.setlocal("Keyword", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(772);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Lambda", var7, Lambda$214);
      var1.setlocal("Lambda", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(803);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("LeftShift", var7, LeftShift$219);
      var1.setlocal("LeftShift", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(818);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("List", var7, List$224);
      var1.setlocal("List", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(834);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("ListComp", var7, ListComp$229);
      var1.setlocal("ListComp", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(855);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("ListCompFor", var7, ListCompFor$234);
      var1.setlocal("ListCompFor", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(879);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("ListCompIf", var7, ListCompIf$239);
      var1.setlocal("ListCompIf", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(893);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("SetComp", var7, SetComp$244);
      var1.setlocal("SetComp", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(914);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("DictComp", var7, DictComp$249);
      var1.setlocal("DictComp", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(938);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Mod", var7, Mod$254);
      var1.setlocal("Mod", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(953);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Module", var7, Module$259);
      var1.setlocal("Module", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(968);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Mul", var7, Mul$264);
      var1.setlocal("Mul", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(983);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Name", var7, Name$269);
      var1.setlocal("Name", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(997);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Not", var7, Not$274);
      var1.setlocal("Not", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1011);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Or", var7, Or$279);
      var1.setlocal("Or", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1027);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Pass", var7, Pass$284);
      var1.setlocal("Pass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1040);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Power", var7, Power$289);
      var1.setlocal("Power", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1055);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Print", var7, Print$294);
      var1.setlocal("Print", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1077);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Printnl", var7, Printnl$299);
      var1.setlocal("Printnl", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1099);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Raise", var7, Raise$304);
      var1.setlocal("Raise", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1126);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Return", var7, Return$309);
      var1.setlocal("Return", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1140);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("RightShift", var7, RightShift$314);
      var1.setlocal("RightShift", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1155);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Set", var7, Set$319);
      var1.setlocal("Set", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1171);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Slice", var7, Slice$324);
      var1.setlocal("Slice", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1199);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Sliceobj", var7, Sliceobj$329);
      var1.setlocal("Sliceobj", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1215);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Stmt", var7, Stmt$334);
      var1.setlocal("Stmt", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1231);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Sub", var7, Sub$339);
      var1.setlocal("Sub", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1246);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Subscript", var7, Subscript$344);
      var1.setlocal("Subscript", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1269);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("TryExcept", var7, TryExcept$349);
      var1.setlocal("TryExcept", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1294);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("TryFinally", var7, TryFinally$354);
      var1.setlocal("TryFinally", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1309);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Tuple", var7, Tuple$359);
      var1.setlocal("Tuple", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1325);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("UnaryAdd", var7, UnaryAdd$364);
      var1.setlocal("UnaryAdd", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1339);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("UnarySub", var7, UnarySub$369);
      var1.setlocal("UnarySub", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1353);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("While", var7, While$374);
      var1.setlocal("While", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1378);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("With", var7, With$379);
      var1.setlocal("With", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1403);
      var7 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Yield", var7, Yield$384);
      var1.setlocal("Yield", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1417);
      PyObject var11 = var1.getname("globals").__call__(var2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1417);
         var4 = var11.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal("name", var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal("obj", var6);
         var6 = null;
         var1.setline(1418);
         PyObject var10000 = var1.getname("isinstance").__call__(var2, var1.getname("obj"), var1.getname("type"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getname("issubclass").__call__(var2, var1.getname("obj"), var1.getname("Node"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(1419);
            PyObject var9 = var1.getname("obj");
            var1.getname("nodes").__setitem__(var1.getname("name").__getattr__("lower").__call__(var2), var9);
            var5 = null;
         }
      }
   }

   public PyObject flatten$1(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(9);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         while(true) {
            var1.setline(9);
            PyObject var4 = var7.__iternext__();
            if (var4 == null) {
               var1.setline(16);
               var7 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setlocal(2, var4);
            var1.setline(10);
            PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(11);
            var5 = var1.getlocal(3);
            PyObject var10000 = var5._is(var1.getglobal("tuple"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(3);
               var10000 = var5._is(var1.getglobal("list"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(12);
               var5 = var1.getglobal("flatten").__call__(var2, var1.getlocal(2)).__iter__();

               while(true) {
                  var1.setline(12);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(4, var6);
                  var1.setline(13);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            } else {
               var1.setline(15);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
            }
         }
      }
   }

   public PyObject flatten_nodes$2(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("flatten").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(19);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(19);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(19);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Node")).__nonzero__()) {
            var1.setline(19);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject Node$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class for ast nodes."));
      var1.setline(24);
      PyString.fromInterned("Abstract base class for ast nodes.");
      var1.setline(25);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, getChildren$4, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$5, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, asList$6, (PyObject)null);
      var1.setlocal("asList", var4);
      var3 = null;
      var1.setline(32);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$7, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getChildren$4(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$5(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(28);
            var3 = var1.getlocal(0).__getattr__("getChildren").__call__(var2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(28);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(29);
         var1.setline(29);
         var6 = var1.getlocal(1);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject asList$6(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getlocal(0).__getattr__("getChildren").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$7(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EmptyNode$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(36);
      return var1.getf_locals();
   }

   public PyObject Expression$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(40);
      PyString var3 = PyString.fromInterned("Expression");
      var1.getname("nodes").__setitem__((PyObject)PyString.fromInterned("expression"), var3);
      var3 = null;
      var1.setline(41);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(44);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getChildren$11, (PyObject)null);
      var1.setlocal("getChildren", var5);
      var3 = null;
      var1.setline(47);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getChildNodes$12, (PyObject)null);
      var1.setlocal("getChildNodes", var5);
      var3 = null;
      var1.setline(50);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$13, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$11(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("node")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$12(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("node")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$13(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = PyString.fromInterned("Expression(%s)")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("node")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Add$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(54);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$15, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$16, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$17, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$18, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$15(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$16(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$17(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$18(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = PyString.fromInterned("Add((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject And$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(69);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$21, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$22, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$23, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$21(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$22(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(78);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(79);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$23(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = PyString.fromInterned("And(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AssAttr$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(85);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$25, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$26, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$27, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(97);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$28, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$25(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("attrname", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$26(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr"), var1.getlocal(0).__getattr__("attrname"), var1.getlocal(0).__getattr__("flags")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$27(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$28(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = PyString.fromInterned("AssAttr(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("attrname")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("flags"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AssList$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(101);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$31, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$32, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$33, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$31(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$32(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(110);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(111);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$33(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = PyString.fromInterned("AssList(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AssName$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(117);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$35, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$36, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$37, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$38, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$35(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$36(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("flags")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$37(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$38(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = PyString.fromInterned("AssName(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("name")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("flags"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AssTuple$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(132);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$40, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$41, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$42, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$43, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$40(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$41(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$42(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(141);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(142);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$43(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyObject var3 = PyString.fromInterned("AssTuple(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Assert$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(148);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$45, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$46, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$47, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$48, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$45(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(150);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("fail", var3);
      var3 = null;
      var1.setline(151);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$46(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.setline(156);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("fail"));
      var1.setline(157);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$47(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(161);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.setline(162);
      PyObject var4 = var1.getlocal(0).__getattr__("fail");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(163);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("fail"));
      }

      var1.setline(164);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$48(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyObject var3 = PyString.fromInterned("Assert(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("test")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("fail"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Assign$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(170);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$50, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(175);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$51, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$52, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$53, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$50(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$51(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(177);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(178);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(179);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$52(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(183);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(184);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(185);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$53(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = PyString.fromInterned("Assign(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AugAssign$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(191);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$55, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$56, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$57, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(203);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$58, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$55(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.setline(193);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("op", var3);
      var3 = null;
      var1.setline(194);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(195);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$56(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("node"), var1.getlocal(0).__getattr__("op"), var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$57(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("node"), var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$58(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = PyString.fromInterned("AugAssign(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("node")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("op")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Backquote$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(207);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$60, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$61, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(214);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$62, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$63, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$60(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$61(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$62(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$63(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3 = PyString.fromInterned("Backquote(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Bitand$64(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(221);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$65, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$66, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$67, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$68, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$65(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(223);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$66(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$67(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(230);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(231);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$68(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = PyString.fromInterned("Bitand(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Bitor$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(237);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$70, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(241);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$71, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(244);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$72, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$73, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$70(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(239);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$71(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$72(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(246);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(247);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$73(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyObject var3 = PyString.fromInterned("Bitor(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Bitxor$74(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(253);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$75, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(257);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$76, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$77, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$78, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$75(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$76(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$77(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(262);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(263);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$78(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = PyString.fromInterned("Bitxor(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Break$79(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(269);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$80, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$81, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$82, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(278);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$83, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$80(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$81(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$82(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$83(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      PyString var3 = PyString.fromInterned("Break()");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CallFunc$84(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(282);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$85, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(289);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$86, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$87, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$88, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$85(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.setline(284);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("args", var3);
      var3 = null;
      var1.setline(285);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("star_args", var3);
      var3 = null;
      var1.setline(286);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("dstar_args", var3);
      var3 = null;
      var1.setline(287);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$86(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(291);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("node"));
      var1.setline(292);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("args")));
      var1.setline(293);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("star_args"));
      var1.setline(294);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dstar_args"));
      var1.setline(295);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$87(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(299);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("node"));
      var1.setline(300);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("args")));
      var1.setline(301);
      PyObject var4 = var1.getlocal(0).__getattr__("star_args");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(302);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("star_args"));
      }

      var1.setline(303);
      var4 = var1.getlocal(0).__getattr__("dstar_args");
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(304);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dstar_args"));
      }

      var1.setline(305);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$88(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyObject var3 = PyString.fromInterned("CallFunc(%s, %s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("node")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("args")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("star_args")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("dstar_args"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Class$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(311);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$90, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(319);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$91, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$92, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(336);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$93, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$90(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(313);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("bases", var3);
      var3 = null;
      var1.setline(314);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("doc", var3);
      var3 = null;
      var1.setline(315);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.setline(316);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("decorators", var3);
      var3 = null;
      var1.setline(317);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$91(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(321);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("name"));
      var1.setline(322);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("bases")));
      var1.setline(323);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("doc"));
      var1.setline(324);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.setline(325);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("decorators"));
      var1.setline(326);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$92(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(330);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("bases")));
      var1.setline(331);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.setline(332);
      PyObject var4 = var1.getlocal(0).__getattr__("decorators");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(333);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("decorators"));
      }

      var1.setline(334);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$93(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3 = PyString.fromInterned("Class(%s, %s, %s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("name")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("bases")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("doc")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("code")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("decorators"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Compare$94(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(340);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$95, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(345);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$96, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$97, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(357);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$98, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$95(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(342);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("ops", var3);
      var3 = null;
      var1.setline(343);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$96(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(347);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(348);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("ops")));
      var1.setline(349);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$97(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(353);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(354);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("ops")));
      var1.setline(355);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$98(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyObject var3 = PyString.fromInterned("Compare(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("ops"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Const$99(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(361);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$100, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(365);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$101, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(368);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$102, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(371);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$103, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$100(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(363);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$101(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("value")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$102(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$103(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var3 = PyString.fromInterned("Const(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("value"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Continue$104(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(375);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$105, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$106, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(381);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$107, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$108, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$105(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$106(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$107(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$108(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyString var3 = PyString.fromInterned("Continue()");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Decorators$109(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(388);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$110, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$111, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$112, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(400);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$113, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$110(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$111(PyFrame var1, ThreadState var2) {
      var1.setline(393);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$112(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(397);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(398);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$113(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyObject var3 = PyString.fromInterned("Decorators(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Dict$114(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(404);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$115, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(408);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$116, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(411);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$117, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(416);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$118, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$115(PyFrame var1, ThreadState var2) {
      var1.setline(405);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("items", var3);
      var3 = null;
      var1.setline(406);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$116(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("items")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$117(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(413);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("items")));
      var1.setline(414);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$118(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      PyObject var3 = PyString.fromInterned("Dict(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("items"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Discard$119(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(420);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$120, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(424);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$121, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(427);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$122, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$123, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$120(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(422);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$121(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$122(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$123(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyObject var3 = PyString.fromInterned("Discard(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Div$124(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(434);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$125, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(439);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$126, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$127, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(445);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$128, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$125(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(436);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(437);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$126(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$127(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$128(PyFrame var1, ThreadState var2) {
      var1.setline(446);
      PyObject var3 = PyString.fromInterned("Div((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Ellipsis$129(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(449);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$130, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(452);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$131, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$132, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(458);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$133, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$130(PyFrame var1, ThreadState var2) {
      var1.setline(450);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$131(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$132(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$133(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      PyString var3 = PyString.fromInterned("Ellipsis()");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Exec$134(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(462);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$135, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(468);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$136, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(475);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$137, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(484);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$138, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$135(PyFrame var1, ThreadState var2) {
      var1.setline(463);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(464);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("locals", var3);
      var3 = null;
      var1.setline(465);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("globals", var3);
      var3 = null;
      var1.setline(466);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$136(PyFrame var1, ThreadState var2) {
      var1.setline(469);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(470);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(471);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("locals"));
      var1.setline(472);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("globals"));
      var1.setline(473);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$137(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(477);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(478);
      PyObject var4 = var1.getlocal(0).__getattr__("locals");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(479);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("locals"));
      }

      var1.setline(480);
      var4 = var1.getlocal(0).__getattr__("globals");
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(481);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("globals"));
      }

      var1.setline(482);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$138(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyObject var3 = PyString.fromInterned("Exec(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("locals")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("globals"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FloorDiv$139(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(488);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$140, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(493);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$141, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(496);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$142, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(499);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$143, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$140(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(490);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(491);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$141(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$142(PyFrame var1, ThreadState var2) {
      var1.setline(497);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$143(PyFrame var1, ThreadState var2) {
      var1.setline(500);
      PyObject var3 = PyString.fromInterned("FloorDiv((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject For$144(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(503);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$145, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(510);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$146, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(518);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$147, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(527);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$148, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$145(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("assign", var3);
      var3 = null;
      var1.setline(505);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("list", var3);
      var3 = null;
      var1.setline(506);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("body", var3);
      var3 = null;
      var1.setline(507);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("else_", var3);
      var3 = null;
      var1.setline(508);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$146(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(512);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("assign"));
      var1.setline(513);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("list"));
      var1.setline(514);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(515);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      var1.setline(516);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$147(PyFrame var1, ThreadState var2) {
      var1.setline(519);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(520);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("assign"));
      var1.setline(521);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("list"));
      var1.setline(522);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(523);
      PyObject var4 = var1.getlocal(0).__getattr__("else_");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(524);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      }

      var1.setline(525);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$148(PyFrame var1, ThreadState var2) {
      var1.setline(528);
      PyObject var3 = PyString.fromInterned("For(%s, %s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("assign")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("list")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("body")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("else_"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject From$149(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(531);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$150, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(537);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$151, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(540);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$152, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(543);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$153, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$150(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("modname", var3);
      var3 = null;
      var1.setline(533);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("names", var3);
      var3 = null;
      var1.setline(534);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("level", var3);
      var3 = null;
      var1.setline(535);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$151(PyFrame var1, ThreadState var2) {
      var1.setline(538);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("modname"), var1.getlocal(0).__getattr__("names"), var1.getlocal(0).__getattr__("level")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$152(PyFrame var1, ThreadState var2) {
      var1.setline(541);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$153(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      PyObject var3 = PyString.fromInterned("From(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("modname")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("names")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("level"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Function$154(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(547);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$155, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(563);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$156, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(574);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$157, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(582);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$158, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$155(PyFrame var1, ThreadState var2) {
      var1.setline(548);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("decorators", var3);
      var3 = null;
      var1.setline(549);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(550);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("argnames", var3);
      var3 = null;
      var1.setline(551);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("defaults", var3);
      var3 = null;
      var1.setline(552);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(553);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("doc", var3);
      var3 = null;
      var1.setline(554);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.setline(555);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(556);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("varargs", var3);
      var1.getlocal(0).__setattr__("kwargs", var3);
      var1.setline(557);
      PyInteger var4;
      if (var1.getlocal(5)._and(var1.getglobal("CO_VARARGS")).__nonzero__()) {
         var1.setline(558);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"varargs", var4);
         var3 = null;
      }

      var1.setline(559);
      if (var1.getlocal(5)._and(var1.getglobal("CO_VARKEYWORDS")).__nonzero__()) {
         var1.setline(560);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"kwargs", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$156(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(565);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("decorators"));
      var1.setline(566);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("name"));
      var1.setline(567);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("argnames"));
      var1.setline(568);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("defaults")));
      var1.setline(569);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("flags"));
      var1.setline(570);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("doc"));
      var1.setline(571);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.setline(572);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$157(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(576);
      PyObject var4 = var1.getlocal(0).__getattr__("decorators");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(577);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("decorators"));
      }

      var1.setline(578);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("defaults")));
      var1.setline(579);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.setline(580);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$158(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyObject var3 = PyString.fromInterned("Function(%s, %s, %s, %s, %s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("decorators")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("name")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("argnames")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("defaults")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("flags")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("doc")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("code"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject GenExpr$159(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(586);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$160, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(593);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$161, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(596);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$162, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(599);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$163, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$160(PyFrame var1, ThreadState var2) {
      var1.setline(587);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.setline(588);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(589);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned(".0")});
      var1.getlocal(0).__setattr__((String)"argnames", var4);
      var3 = null;
      var1.setline(590);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("varargs", var3);
      var1.getlocal(0).__setattr__("kwargs", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$161(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("code")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$162(PyFrame var1, ThreadState var2) {
      var1.setline(597);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("code")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$163(PyFrame var1, ThreadState var2) {
      var1.setline(600);
      PyObject var3 = PyString.fromInterned("GenExpr(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("code"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject GenExprFor$164(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(603);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$165, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(610);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$166, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(617);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$167, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(624);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$168, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$165(PyFrame var1, ThreadState var2) {
      var1.setline(604);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("assign", var3);
      var3 = null;
      var1.setline(605);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("iter", var3);
      var3 = null;
      var1.setline(606);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("ifs", var3);
      var3 = null;
      var1.setline(607);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(608);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("is_outmost", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$166(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(612);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("assign"));
      var1.setline(613);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("iter"));
      var1.setline(614);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("ifs")));
      var1.setline(615);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$167(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(619);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("assign"));
      var1.setline(620);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("iter"));
      var1.setline(621);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("ifs")));
      var1.setline(622);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$168(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyObject var3 = PyString.fromInterned("GenExprFor(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("assign")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("iter")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("ifs"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject GenExprIf$169(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(628);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$170, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(632);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$171, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(635);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$172, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(638);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$173, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$170(PyFrame var1, ThreadState var2) {
      var1.setline(629);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(630);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$171(PyFrame var1, ThreadState var2) {
      var1.setline(633);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("test")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$172(PyFrame var1, ThreadState var2) {
      var1.setline(636);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("test")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$173(PyFrame var1, ThreadState var2) {
      var1.setline(639);
      PyObject var3 = PyString.fromInterned("GenExprIf(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("test"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject GenExprInner$174(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(642);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$175, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(647);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$176, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(653);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$177, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(659);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$178, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$175(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(644);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("quals", var3);
      var3 = null;
      var1.setline(645);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$176(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(649);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(650);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(651);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$177(PyFrame var1, ThreadState var2) {
      var1.setline(654);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(655);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(656);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(657);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$178(PyFrame var1, ThreadState var2) {
      var1.setline(660);
      PyObject var3 = PyString.fromInterned("GenExprInner(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("quals"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Getattr$179(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(663);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$180, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(668);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$181, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(671);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$182, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(674);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$183, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$180(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(665);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("attrname", var3);
      var3 = null;
      var1.setline(666);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$181(PyFrame var1, ThreadState var2) {
      var1.setline(669);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr"), var1.getlocal(0).__getattr__("attrname")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$182(PyFrame var1, ThreadState var2) {
      var1.setline(672);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$183(PyFrame var1, ThreadState var2) {
      var1.setline(675);
      PyObject var3 = PyString.fromInterned("Getattr(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("attrname"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Global$184(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(678);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$185, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(682);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$186, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(685);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$187, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(688);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$188, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$185(PyFrame var1, ThreadState var2) {
      var1.setline(679);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("names", var3);
      var3 = null;
      var1.setline(680);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$186(PyFrame var1, ThreadState var2) {
      var1.setline(683);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("names")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$187(PyFrame var1, ThreadState var2) {
      var1.setline(686);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$188(PyFrame var1, ThreadState var2) {
      var1.setline(689);
      PyObject var3 = PyString.fromInterned("Global(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("names"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject If$189(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(692);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$190, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(697);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$191, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(703);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$192, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(710);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$193, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$190(PyFrame var1, ThreadState var2) {
      var1.setline(693);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tests", var3);
      var3 = null;
      var1.setline(694);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("else_", var3);
      var3 = null;
      var1.setline(695);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$191(PyFrame var1, ThreadState var2) {
      var1.setline(698);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(699);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("tests")));
      var1.setline(700);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      var1.setline(701);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$192(PyFrame var1, ThreadState var2) {
      var1.setline(704);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(705);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("tests")));
      var1.setline(706);
      PyObject var4 = var1.getlocal(0).__getattr__("else_");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(707);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      }

      var1.setline(708);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$193(PyFrame var1, ThreadState var2) {
      var1.setline(711);
      PyObject var3 = PyString.fromInterned("If(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("tests")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("else_"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IfExp$194(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(714);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$195, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(720);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$196, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(723);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$197, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(726);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$198, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$195(PyFrame var1, ThreadState var2) {
      var1.setline(715);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(716);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("then", var3);
      var3 = null;
      var1.setline(717);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("else_", var3);
      var3 = null;
      var1.setline(718);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$196(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("test"), var1.getlocal(0).__getattr__("then"), var1.getlocal(0).__getattr__("else_")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$197(PyFrame var1, ThreadState var2) {
      var1.setline(724);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("test"), var1.getlocal(0).__getattr__("then"), var1.getlocal(0).__getattr__("else_")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$198(PyFrame var1, ThreadState var2) {
      var1.setline(727);
      PyObject var3 = PyString.fromInterned("IfExp(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("test")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("then")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("else_"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Import$199(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(730);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$200, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$201, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(737);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$202, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(740);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$203, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$200(PyFrame var1, ThreadState var2) {
      var1.setline(731);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("names", var3);
      var3 = null;
      var1.setline(732);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$201(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("names")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$202(PyFrame var1, ThreadState var2) {
      var1.setline(738);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$203(PyFrame var1, ThreadState var2) {
      var1.setline(741);
      PyObject var3 = PyString.fromInterned("Import(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("names"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Invert$204(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(744);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$205, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(748);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$206, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(751);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$207, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(754);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$208, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$205(PyFrame var1, ThreadState var2) {
      var1.setline(745);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(746);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$206(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$207(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$208(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      PyObject var3 = PyString.fromInterned("Invert(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Keyword$209(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(758);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$210, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(763);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$211, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(766);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$212, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(769);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$213, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$210(PyFrame var1, ThreadState var2) {
      var1.setline(759);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(760);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(761);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$211(PyFrame var1, ThreadState var2) {
      var1.setline(764);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$212(PyFrame var1, ThreadState var2) {
      var1.setline(767);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$213(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyObject var3 = PyString.fromInterned("Keyword(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("name")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Lambda$214(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(773);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$215, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(786);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$216, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(794);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$217, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(800);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$218, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$215(PyFrame var1, ThreadState var2) {
      var1.setline(774);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("argnames", var3);
      var3 = null;
      var1.setline(775);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("defaults", var3);
      var3 = null;
      var1.setline(776);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(777);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("code", var3);
      var3 = null;
      var1.setline(778);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(779);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("varargs", var3);
      var1.getlocal(0).__setattr__("kwargs", var3);
      var1.setline(780);
      PyInteger var4;
      if (var1.getlocal(3)._and(var1.getglobal("CO_VARARGS")).__nonzero__()) {
         var1.setline(781);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"varargs", var4);
         var3 = null;
      }

      var1.setline(782);
      if (var1.getlocal(3)._and(var1.getglobal("CO_VARKEYWORDS")).__nonzero__()) {
         var1.setline(783);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"kwargs", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$216(PyFrame var1, ThreadState var2) {
      var1.setline(787);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(788);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("argnames"));
      var1.setline(789);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("defaults")));
      var1.setline(790);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("flags"));
      var1.setline(791);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.setline(792);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$217(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(796);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("defaults")));
      var1.setline(797);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("code"));
      var1.setline(798);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$218(PyFrame var1, ThreadState var2) {
      var1.setline(801);
      PyObject var3 = PyString.fromInterned("Lambda(%s, %s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("argnames")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("defaults")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("flags")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("code"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LeftShift$219(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(804);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$220, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(809);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$221, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(812);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$222, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(815);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$223, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$220(PyFrame var1, ThreadState var2) {
      var1.setline(805);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(806);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(807);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$221(PyFrame var1, ThreadState var2) {
      var1.setline(810);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$222(PyFrame var1, ThreadState var2) {
      var1.setline(813);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$223(PyFrame var1, ThreadState var2) {
      var1.setline(816);
      PyObject var3 = PyString.fromInterned("LeftShift((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject List$224(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(819);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$225, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(823);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$226, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(826);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$227, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(831);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$228, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$225(PyFrame var1, ThreadState var2) {
      var1.setline(820);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(821);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$226(PyFrame var1, ThreadState var2) {
      var1.setline(824);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$227(PyFrame var1, ThreadState var2) {
      var1.setline(827);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(828);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(829);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$228(PyFrame var1, ThreadState var2) {
      var1.setline(832);
      PyObject var3 = PyString.fromInterned("List(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ListComp$229(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(835);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$230, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(840);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$231, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(846);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$232, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(852);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$233, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$230(PyFrame var1, ThreadState var2) {
      var1.setline(836);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(837);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("quals", var3);
      var3 = null;
      var1.setline(838);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$231(PyFrame var1, ThreadState var2) {
      var1.setline(841);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(842);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(843);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(844);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$232(PyFrame var1, ThreadState var2) {
      var1.setline(847);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(848);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(849);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(850);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$233(PyFrame var1, ThreadState var2) {
      var1.setline(853);
      PyObject var3 = PyString.fromInterned("ListComp(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("quals"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ListCompFor$234(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(856);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$235, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(862);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$236, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(869);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$237, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(876);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$238, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$235(PyFrame var1, ThreadState var2) {
      var1.setline(857);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("assign", var3);
      var3 = null;
      var1.setline(858);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("list", var3);
      var3 = null;
      var1.setline(859);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("ifs", var3);
      var3 = null;
      var1.setline(860);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$236(PyFrame var1, ThreadState var2) {
      var1.setline(863);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(864);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("assign"));
      var1.setline(865);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("list"));
      var1.setline(866);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("ifs")));
      var1.setline(867);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$237(PyFrame var1, ThreadState var2) {
      var1.setline(870);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(871);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("assign"));
      var1.setline(872);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("list"));
      var1.setline(873);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("ifs")));
      var1.setline(874);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$238(PyFrame var1, ThreadState var2) {
      var1.setline(877);
      PyObject var3 = PyString.fromInterned("ListCompFor(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("assign")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("list")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("ifs"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ListCompIf$239(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(880);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$240, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(884);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$241, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(887);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$242, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(890);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$243, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$240(PyFrame var1, ThreadState var2) {
      var1.setline(881);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(882);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$241(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("test")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$242(PyFrame var1, ThreadState var2) {
      var1.setline(888);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("test")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$243(PyFrame var1, ThreadState var2) {
      var1.setline(891);
      PyObject var3 = PyString.fromInterned("ListCompIf(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("test"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SetComp$244(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(894);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$245, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(899);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$246, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(905);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$247, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(911);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$248, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$245(PyFrame var1, ThreadState var2) {
      var1.setline(895);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(896);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("quals", var3);
      var3 = null;
      var1.setline(897);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$246(PyFrame var1, ThreadState var2) {
      var1.setline(900);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(901);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(902);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(903);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$247(PyFrame var1, ThreadState var2) {
      var1.setline(906);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(907);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(908);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(909);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$248(PyFrame var1, ThreadState var2) {
      var1.setline(912);
      PyObject var3 = PyString.fromInterned("SetComp(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("quals"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DictComp$249(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(915);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$250, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(921);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$251, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(928);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$252, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(935);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$253, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$250(PyFrame var1, ThreadState var2) {
      var1.setline(916);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("key", var3);
      var3 = null;
      var1.setline(917);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(918);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("quals", var3);
      var3 = null;
      var1.setline(919);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$251(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(923);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("key"));
      var1.setline(924);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("value"));
      var1.setline(925);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(926);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$252(PyFrame var1, ThreadState var2) {
      var1.setline(929);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(930);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("key"));
      var1.setline(931);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("value"));
      var1.setline(932);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("quals")));
      var1.setline(933);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$253(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      PyObject var3 = PyString.fromInterned("DictComp(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("key")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("value")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("quals"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Mod$254(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(939);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$255, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(944);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$256, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(947);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$257, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(950);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$258, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$255(PyFrame var1, ThreadState var2) {
      var1.setline(940);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(941);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(942);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$256(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$257(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$258(PyFrame var1, ThreadState var2) {
      var1.setline(951);
      PyObject var3 = PyString.fromInterned("Mod((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Module$259(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(954);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$260, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(959);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$261, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(962);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$262, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(965);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$263, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$260(PyFrame var1, ThreadState var2) {
      var1.setline(955);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("doc", var3);
      var3 = null;
      var1.setline(956);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("node", var3);
      var3 = null;
      var1.setline(957);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$261(PyFrame var1, ThreadState var2) {
      var1.setline(960);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("doc"), var1.getlocal(0).__getattr__("node")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$262(PyFrame var1, ThreadState var2) {
      var1.setline(963);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("node")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$263(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      PyObject var3 = PyString.fromInterned("Module(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("doc")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("node"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Mul$264(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(969);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$265, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(974);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$266, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(977);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$267, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(980);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$268, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$265(PyFrame var1, ThreadState var2) {
      var1.setline(970);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(971);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(972);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$266(PyFrame var1, ThreadState var2) {
      var1.setline(975);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$267(PyFrame var1, ThreadState var2) {
      var1.setline(978);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$268(PyFrame var1, ThreadState var2) {
      var1.setline(981);
      PyObject var3 = PyString.fromInterned("Mul((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Name$269(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(984);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$270, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(988);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$271, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(991);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$272, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(994);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$273, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$270(PyFrame var1, ThreadState var2) {
      var1.setline(985);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(986);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$271(PyFrame var1, ThreadState var2) {
      var1.setline(989);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$272(PyFrame var1, ThreadState var2) {
      var1.setline(992);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$273(PyFrame var1, ThreadState var2) {
      var1.setline(995);
      PyObject var3 = PyString.fromInterned("Name(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("name"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Not$274(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(998);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$275, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1002);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$276, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1005);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$277, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1008);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$278, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$275(PyFrame var1, ThreadState var2) {
      var1.setline(999);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(1000);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$276(PyFrame var1, ThreadState var2) {
      var1.setline(1003);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$277(PyFrame var1, ThreadState var2) {
      var1.setline(1006);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$278(PyFrame var1, ThreadState var2) {
      var1.setline(1009);
      PyObject var3 = PyString.fromInterned("Not(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Or$279(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1012);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$280, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1016);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$281, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1019);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$282, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1024);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$283, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$280(PyFrame var1, ThreadState var2) {
      var1.setline(1013);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1014);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$281(PyFrame var1, ThreadState var2) {
      var1.setline(1017);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$282(PyFrame var1, ThreadState var2) {
      var1.setline(1020);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1021);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1022);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$283(PyFrame var1, ThreadState var2) {
      var1.setline(1025);
      PyObject var3 = PyString.fromInterned("Or(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Pass$284(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1028);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$285, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1031);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$286, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1034);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$287, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1037);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$288, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$285(PyFrame var1, ThreadState var2) {
      var1.setline(1029);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$286(PyFrame var1, ThreadState var2) {
      var1.setline(1032);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$287(PyFrame var1, ThreadState var2) {
      var1.setline(1035);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$288(PyFrame var1, ThreadState var2) {
      var1.setline(1038);
      PyString var3 = PyString.fromInterned("Pass()");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Power$289(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1041);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$290, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1046);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$291, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1049);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$292, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1052);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$293, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$290(PyFrame var1, ThreadState var2) {
      var1.setline(1042);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(1043);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(1044);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$291(PyFrame var1, ThreadState var2) {
      var1.setline(1047);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$292(PyFrame var1, ThreadState var2) {
      var1.setline(1050);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$293(PyFrame var1, ThreadState var2) {
      var1.setline(1053);
      PyObject var3 = PyString.fromInterned("Power((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Print$294(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1056);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$295, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1061);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$296, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1067);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$297, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1074);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$298, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$295(PyFrame var1, ThreadState var2) {
      var1.setline(1057);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1058);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("dest", var3);
      var3 = null;
      var1.setline(1059);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$296(PyFrame var1, ThreadState var2) {
      var1.setline(1062);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1063);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1064);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dest"));
      var1.setline(1065);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$297(PyFrame var1, ThreadState var2) {
      var1.setline(1068);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1069);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1070);
      PyObject var4 = var1.getlocal(0).__getattr__("dest");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1071);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dest"));
      }

      var1.setline(1072);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$298(PyFrame var1, ThreadState var2) {
      var1.setline(1075);
      PyObject var3 = PyString.fromInterned("Print(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("dest"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Printnl$299(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1078);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$300, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1083);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$301, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1089);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$302, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1096);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$303, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$300(PyFrame var1, ThreadState var2) {
      var1.setline(1079);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1080);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("dest", var3);
      var3 = null;
      var1.setline(1081);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$301(PyFrame var1, ThreadState var2) {
      var1.setline(1084);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1085);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1086);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dest"));
      var1.setline(1087);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$302(PyFrame var1, ThreadState var2) {
      var1.setline(1090);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1091);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1092);
      PyObject var4 = var1.getlocal(0).__getattr__("dest");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1093);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dest"));
      }

      var1.setline(1094);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$303(PyFrame var1, ThreadState var2) {
      var1.setline(1097);
      PyObject var3 = PyString.fromInterned("Printnl(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("dest"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Raise$304(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1100);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$305, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$306, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$307, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$308, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$305(PyFrame var1, ThreadState var2) {
      var1.setline(1101);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr1", var3);
      var3 = null;
      var1.setline(1102);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("expr2", var3);
      var3 = null;
      var1.setline(1103);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("expr3", var3);
      var3 = null;
      var1.setline(1104);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$306(PyFrame var1, ThreadState var2) {
      var1.setline(1107);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1108);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr1"));
      var1.setline(1109);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr2"));
      var1.setline(1110);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr3"));
      var1.setline(1111);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$307(PyFrame var1, ThreadState var2) {
      var1.setline(1114);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1115);
      PyObject var4 = var1.getlocal(0).__getattr__("expr1");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1116);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr1"));
      }

      var1.setline(1117);
      var4 = var1.getlocal(0).__getattr__("expr2");
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1118);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr2"));
      }

      var1.setline(1119);
      var4 = var1.getlocal(0).__getattr__("expr3");
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1120);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr3"));
      }

      var1.setline(1121);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$308(PyFrame var1, ThreadState var2) {
      var1.setline(1124);
      PyObject var3 = PyString.fromInterned("Raise(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr1")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr2")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr3"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Return$309(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1127);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$310, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$311, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$312, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$313, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$310(PyFrame var1, ThreadState var2) {
      var1.setline(1128);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(1129);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$311(PyFrame var1, ThreadState var2) {
      var1.setline(1132);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("value")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$312(PyFrame var1, ThreadState var2) {
      var1.setline(1135);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("value")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$313(PyFrame var1, ThreadState var2) {
      var1.setline(1138);
      PyObject var3 = PyString.fromInterned("Return(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("value"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject RightShift$314(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1141);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$315, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1146);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$316, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1149);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$317, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$318, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$315(PyFrame var1, ThreadState var2) {
      var1.setline(1142);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(1143);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(1144);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$316(PyFrame var1, ThreadState var2) {
      var1.setline(1147);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$317(PyFrame var1, ThreadState var2) {
      var1.setline(1150);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$318(PyFrame var1, ThreadState var2) {
      var1.setline(1153);
      PyObject var3 = PyString.fromInterned("RightShift((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Set$319(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1156);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$320, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$321, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$322, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$323, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$320(PyFrame var1, ThreadState var2) {
      var1.setline(1157);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1158);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$321(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$322(PyFrame var1, ThreadState var2) {
      var1.setline(1164);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1165);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1166);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$323(PyFrame var1, ThreadState var2) {
      var1.setline(1169);
      PyObject var3 = PyString.fromInterned("Set(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Slice$324(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1172);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$325, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$326, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$327, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$328, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$325(PyFrame var1, ThreadState var2) {
      var1.setline(1173);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(1174);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(1175);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lower", var3);
      var3 = null;
      var1.setline(1176);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("upper", var3);
      var3 = null;
      var1.setline(1177);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$326(PyFrame var1, ThreadState var2) {
      var1.setline(1180);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1181);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(1182);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("flags"));
      var1.setline(1183);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("lower"));
      var1.setline(1184);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("upper"));
      var1.setline(1185);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$327(PyFrame var1, ThreadState var2) {
      var1.setline(1188);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1189);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(1190);
      PyObject var4 = var1.getlocal(0).__getattr__("lower");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1191);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("lower"));
      }

      var1.setline(1192);
      var4 = var1.getlocal(0).__getattr__("upper");
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1193);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("upper"));
      }

      var1.setline(1194);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$328(PyFrame var1, ThreadState var2) {
      var1.setline(1197);
      PyObject var3 = PyString.fromInterned("Slice(%s, %s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("flags")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("lower")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("upper"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Sliceobj$329(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1200);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$330, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$331, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$332, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1212);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$333, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$330(PyFrame var1, ThreadState var2) {
      var1.setline(1201);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1202);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$331(PyFrame var1, ThreadState var2) {
      var1.setline(1205);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$332(PyFrame var1, ThreadState var2) {
      var1.setline(1208);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1209);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1210);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$333(PyFrame var1, ThreadState var2) {
      var1.setline(1213);
      PyObject var3 = PyString.fromInterned("Sliceobj(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Stmt$334(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1216);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$335, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$336, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$337, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$338, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$335(PyFrame var1, ThreadState var2) {
      var1.setline(1217);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1218);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$336(PyFrame var1, ThreadState var2) {
      var1.setline(1221);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$337(PyFrame var1, ThreadState var2) {
      var1.setline(1224);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1225);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1226);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$338(PyFrame var1, ThreadState var2) {
      var1.setline(1229);
      PyObject var3 = PyString.fromInterned("Stmt(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Sub$339(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1232);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$340, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$341, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1240);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$342, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$343, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$340(PyFrame var1, ThreadState var2) {
      var1.setline(1233);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(1234);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(1235);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$341(PyFrame var1, ThreadState var2) {
      var1.setline(1238);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$342(PyFrame var1, ThreadState var2) {
      var1.setline(1241);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$343(PyFrame var1, ThreadState var2) {
      var1.setline(1244);
      PyObject var3 = PyString.fromInterned("Sub((%s, %s))")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("right"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Subscript$344(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1247);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$345, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$346, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$347, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$348, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$345(PyFrame var1, ThreadState var2) {
      var1.setline(1248);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(1249);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("flags", var3);
      var3 = null;
      var1.setline(1250);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("subs", var3);
      var3 = null;
      var1.setline(1251);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$346(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1255);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(1256);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("flags"));
      var1.setline(1257);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("subs")));
      var1.setline(1258);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$347(PyFrame var1, ThreadState var2) {
      var1.setline(1261);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1262);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(1263);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("subs")));
      var1.setline(1264);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$348(PyFrame var1, ThreadState var2) {
      var1.setline(1267);
      PyObject var3 = PyString.fromInterned("Subscript(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("flags")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("subs"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TryExcept$349(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1270);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$350, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1276);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$351, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1283);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$352, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1291);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$353, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$350(PyFrame var1, ThreadState var2) {
      var1.setline(1271);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("body", var3);
      var3 = null;
      var1.setline(1272);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("handlers", var3);
      var3 = null;
      var1.setline(1273);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("else_", var3);
      var3 = null;
      var1.setline(1274);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$351(PyFrame var1, ThreadState var2) {
      var1.setline(1277);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1278);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(1279);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("handlers")));
      var1.setline(1280);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      var1.setline(1281);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$352(PyFrame var1, ThreadState var2) {
      var1.setline(1284);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1285);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(1286);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("handlers")));
      var1.setline(1287);
      PyObject var4 = var1.getlocal(0).__getattr__("else_");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1288);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      }

      var1.setline(1289);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$353(PyFrame var1, ThreadState var2) {
      var1.setline(1292);
      PyObject var3 = PyString.fromInterned("TryExcept(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("body")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("handlers")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("else_"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TryFinally$354(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1295);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$355, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1300);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$356, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1303);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$357, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1306);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$358, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$355(PyFrame var1, ThreadState var2) {
      var1.setline(1296);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("body", var3);
      var3 = null;
      var1.setline(1297);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("final", var3);
      var3 = null;
      var1.setline(1298);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$356(PyFrame var1, ThreadState var2) {
      var1.setline(1301);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("body"), var1.getlocal(0).__getattr__("final")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$357(PyFrame var1, ThreadState var2) {
      var1.setline(1304);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("body"), var1.getlocal(0).__getattr__("final")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$358(PyFrame var1, ThreadState var2) {
      var1.setline(1307);
      PyObject var3 = PyString.fromInterned("TryFinally(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("body")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("final"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Tuple$359(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1310);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$360, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1314);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$361, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$362, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1322);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$363, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$360(PyFrame var1, ThreadState var2) {
      var1.setline(1311);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodes", var3);
      var3 = null;
      var1.setline(1312);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$361(PyFrame var1, ThreadState var2) {
      var1.setline(1315);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("flatten").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$362(PyFrame var1, ThreadState var2) {
      var1.setline(1318);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1319);
      var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("flatten_nodes").__call__(var2, var1.getlocal(0).__getattr__("nodes")));
      var1.setline(1320);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$363(PyFrame var1, ThreadState var2) {
      var1.setline(1323);
      PyObject var3 = PyString.fromInterned("Tuple(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("nodes"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject UnaryAdd$364(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1326);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$365, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$366, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1333);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$367, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1336);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$368, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$365(PyFrame var1, ThreadState var2) {
      var1.setline(1327);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(1328);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$366(PyFrame var1, ThreadState var2) {
      var1.setline(1331);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$367(PyFrame var1, ThreadState var2) {
      var1.setline(1334);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$368(PyFrame var1, ThreadState var2) {
      var1.setline(1337);
      PyObject var3 = PyString.fromInterned("UnaryAdd(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject UnarySub$369(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1340);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$370, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$371, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$372, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1350);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$373, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$370(PyFrame var1, ThreadState var2) {
      var1.setline(1341);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(1342);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$371(PyFrame var1, ThreadState var2) {
      var1.setline(1345);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$372(PyFrame var1, ThreadState var2) {
      var1.setline(1348);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("expr")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$373(PyFrame var1, ThreadState var2) {
      var1.setline(1351);
      PyObject var3 = PyString.fromInterned("UnarySub(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject While$374(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1354);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$375, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1360);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$376, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1367);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$377, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1375);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$378, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$375(PyFrame var1, ThreadState var2) {
      var1.setline(1355);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(1356);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("body", var3);
      var3 = null;
      var1.setline(1357);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("else_", var3);
      var3 = null;
      var1.setline(1358);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$376(PyFrame var1, ThreadState var2) {
      var1.setline(1361);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1362);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.setline(1363);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(1364);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      var1.setline(1365);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$377(PyFrame var1, ThreadState var2) {
      var1.setline(1368);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1369);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.setline(1370);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(1371);
      PyObject var4 = var1.getlocal(0).__getattr__("else_");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1372);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("else_"));
      }

      var1.setline(1373);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$378(PyFrame var1, ThreadState var2) {
      var1.setline(1376);
      PyObject var3 = PyString.fromInterned("While(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("test")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("body")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("else_"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject With$379(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1379);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$380, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1385);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$381, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1392);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$382, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1400);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$383, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$380(PyFrame var1, ThreadState var2) {
      var1.setline(1380);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expr", var3);
      var3 = null;
      var1.setline(1381);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("vars", var3);
      var3 = null;
      var1.setline(1382);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("body", var3);
      var3 = null;
      var1.setline(1383);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$381(PyFrame var1, ThreadState var2) {
      var1.setline(1386);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1387);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(1388);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("vars"));
      var1.setline(1389);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(1390);
      PyObject var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getChildNodes$382(PyFrame var1, ThreadState var2) {
      var1.setline(1393);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1394);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("expr"));
      var1.setline(1395);
      PyObject var4 = var1.getlocal(0).__getattr__("vars");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1396);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("vars"));
      }

      var1.setline(1397);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("body"));
      var1.setline(1398);
      var4 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __repr__$383(PyFrame var1, ThreadState var2) {
      var1.setline(1401);
      PyObject var3 = PyString.fromInterned("With(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("expr")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("vars")), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("body"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Yield$384(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1404);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$385, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1408);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildren$386, (PyObject)null);
      var1.setlocal("getChildren", var4);
      var3 = null;
      var1.setline(1411);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getChildNodes$387, (PyObject)null);
      var1.setlocal("getChildNodes", var4);
      var3 = null;
      var1.setline(1414);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$388, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$385(PyFrame var1, ThreadState var2) {
      var1.setline(1405);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(1406);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getChildren$386(PyFrame var1, ThreadState var2) {
      var1.setline(1409);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("value")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getChildNodes$387(PyFrame var1, ThreadState var2) {
      var1.setline(1412);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("value")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$388(PyFrame var1, ThreadState var2) {
      var1.setline(1415);
      PyObject var3 = PyString.fromInterned("Yield(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("value"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public ast$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"seq", "l", "elt", "t", "elt2"};
      flatten$1 = Py.newCode(1, var2, var1, "flatten", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"seq", "_[19_12]", "n"};
      flatten_nodes$2 = Py.newCode(1, var2, var1, "flatten_nodes", 18, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Node$3 = Py.newCode(0, var2, var1, "Node", 23, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      getChildren$4 = Py.newCode(1, var2, var1, "getChildren", 25, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      __iter__$5 = Py.newCode(1, var2, var1, "__iter__", 27, false, false, self, 5, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      asList$6 = Py.newCode(1, var2, var1, "asList", 30, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$7 = Py.newCode(1, var2, var1, "getChildNodes", 32, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EmptyNode$8 = Py.newCode(0, var2, var1, "EmptyNode", 35, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Expression$9 = Py.newCode(0, var2, var1, "Expression", 38, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node"};
      __init__$10 = Py.newCode(2, var2, var1, "__init__", 41, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$11 = Py.newCode(1, var2, var1, "getChildren", 44, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$12 = Py.newCode(1, var2, var1, "getChildNodes", 47, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$13 = Py.newCode(1, var2, var1, "__repr__", 50, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Add$14 = Py.newCode(0, var2, var1, "Add", 53, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$15 = Py.newCode(3, var2, var1, "__init__", 54, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$16 = Py.newCode(1, var2, var1, "getChildren", 59, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$17 = Py.newCode(1, var2, var1, "getChildNodes", 62, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$18 = Py.newCode(1, var2, var1, "__repr__", 65, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      And$19 = Py.newCode(0, var2, var1, "And", 68, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$20 = Py.newCode(3, var2, var1, "__init__", 69, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$21 = Py.newCode(1, var2, var1, "getChildren", 73, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$22 = Py.newCode(1, var2, var1, "getChildNodes", 76, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$23 = Py.newCode(1, var2, var1, "__repr__", 81, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AssAttr$24 = Py.newCode(0, var2, var1, "AssAttr", 84, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "attrname", "flags", "lineno"};
      __init__$25 = Py.newCode(5, var2, var1, "__init__", 85, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$26 = Py.newCode(1, var2, var1, "getChildren", 91, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$27 = Py.newCode(1, var2, var1, "getChildNodes", 94, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$28 = Py.newCode(1, var2, var1, "__repr__", 97, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AssList$29 = Py.newCode(0, var2, var1, "AssList", 100, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$30 = Py.newCode(3, var2, var1, "__init__", 101, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$31 = Py.newCode(1, var2, var1, "getChildren", 105, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$32 = Py.newCode(1, var2, var1, "getChildNodes", 108, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$33 = Py.newCode(1, var2, var1, "__repr__", 113, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AssName$34 = Py.newCode(0, var2, var1, "AssName", 116, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "flags", "lineno"};
      __init__$35 = Py.newCode(4, var2, var1, "__init__", 117, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$36 = Py.newCode(1, var2, var1, "getChildren", 122, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$37 = Py.newCode(1, var2, var1, "getChildNodes", 125, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$38 = Py.newCode(1, var2, var1, "__repr__", 128, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AssTuple$39 = Py.newCode(0, var2, var1, "AssTuple", 131, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$40 = Py.newCode(3, var2, var1, "__init__", 132, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$41 = Py.newCode(1, var2, var1, "getChildren", 136, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$42 = Py.newCode(1, var2, var1, "getChildNodes", 139, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$43 = Py.newCode(1, var2, var1, "__repr__", 144, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Assert$44 = Py.newCode(0, var2, var1, "Assert", 147, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "fail", "lineno"};
      __init__$45 = Py.newCode(4, var2, var1, "__init__", 148, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$46 = Py.newCode(1, var2, var1, "getChildren", 153, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$47 = Py.newCode(1, var2, var1, "getChildNodes", 159, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$48 = Py.newCode(1, var2, var1, "__repr__", 166, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Assign$49 = Py.newCode(0, var2, var1, "Assign", 169, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "expr", "lineno"};
      __init__$50 = Py.newCode(4, var2, var1, "__init__", 170, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$51 = Py.newCode(1, var2, var1, "getChildren", 175, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$52 = Py.newCode(1, var2, var1, "getChildNodes", 181, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$53 = Py.newCode(1, var2, var1, "__repr__", 187, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AugAssign$54 = Py.newCode(0, var2, var1, "AugAssign", 190, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "op", "expr", "lineno"};
      __init__$55 = Py.newCode(5, var2, var1, "__init__", 191, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$56 = Py.newCode(1, var2, var1, "getChildren", 197, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$57 = Py.newCode(1, var2, var1, "getChildNodes", 200, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$58 = Py.newCode(1, var2, var1, "__repr__", 203, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Backquote$59 = Py.newCode(0, var2, var1, "Backquote", 206, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "lineno"};
      __init__$60 = Py.newCode(3, var2, var1, "__init__", 207, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$61 = Py.newCode(1, var2, var1, "getChildren", 211, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$62 = Py.newCode(1, var2, var1, "getChildNodes", 214, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$63 = Py.newCode(1, var2, var1, "__repr__", 217, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Bitand$64 = Py.newCode(0, var2, var1, "Bitand", 220, false, false, self, 64, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$65 = Py.newCode(3, var2, var1, "__init__", 221, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$66 = Py.newCode(1, var2, var1, "getChildren", 225, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$67 = Py.newCode(1, var2, var1, "getChildNodes", 228, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$68 = Py.newCode(1, var2, var1, "__repr__", 233, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Bitor$69 = Py.newCode(0, var2, var1, "Bitor", 236, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$70 = Py.newCode(3, var2, var1, "__init__", 237, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$71 = Py.newCode(1, var2, var1, "getChildren", 241, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$72 = Py.newCode(1, var2, var1, "getChildNodes", 244, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$73 = Py.newCode(1, var2, var1, "__repr__", 249, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Bitxor$74 = Py.newCode(0, var2, var1, "Bitxor", 252, false, false, self, 74, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$75 = Py.newCode(3, var2, var1, "__init__", 253, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$76 = Py.newCode(1, var2, var1, "getChildren", 257, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$77 = Py.newCode(1, var2, var1, "getChildNodes", 260, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$78 = Py.newCode(1, var2, var1, "__repr__", 265, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Break$79 = Py.newCode(0, var2, var1, "Break", 268, false, false, self, 79, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lineno"};
      __init__$80 = Py.newCode(2, var2, var1, "__init__", 269, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$81 = Py.newCode(1, var2, var1, "getChildren", 272, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$82 = Py.newCode(1, var2, var1, "getChildNodes", 275, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$83 = Py.newCode(1, var2, var1, "__repr__", 278, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CallFunc$84 = Py.newCode(0, var2, var1, "CallFunc", 281, false, false, self, 84, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "args", "star_args", "dstar_args", "lineno"};
      __init__$85 = Py.newCode(6, var2, var1, "__init__", 282, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$86 = Py.newCode(1, var2, var1, "getChildren", 289, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$87 = Py.newCode(1, var2, var1, "getChildNodes", 297, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$88 = Py.newCode(1, var2, var1, "__repr__", 307, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Class$89 = Py.newCode(0, var2, var1, "Class", 310, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "bases", "doc", "code", "decorators", "lineno"};
      __init__$90 = Py.newCode(7, var2, var1, "__init__", 311, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$91 = Py.newCode(1, var2, var1, "getChildren", 319, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$92 = Py.newCode(1, var2, var1, "getChildNodes", 328, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$93 = Py.newCode(1, var2, var1, "__repr__", 336, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Compare$94 = Py.newCode(0, var2, var1, "Compare", 339, false, false, self, 94, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "ops", "lineno"};
      __init__$95 = Py.newCode(4, var2, var1, "__init__", 340, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$96 = Py.newCode(1, var2, var1, "getChildren", 345, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$97 = Py.newCode(1, var2, var1, "getChildNodes", 351, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$98 = Py.newCode(1, var2, var1, "__repr__", 357, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Const$99 = Py.newCode(0, var2, var1, "Const", 360, false, false, self, 99, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "lineno"};
      __init__$100 = Py.newCode(3, var2, var1, "__init__", 361, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$101 = Py.newCode(1, var2, var1, "getChildren", 365, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$102 = Py.newCode(1, var2, var1, "getChildNodes", 368, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$103 = Py.newCode(1, var2, var1, "__repr__", 371, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Continue$104 = Py.newCode(0, var2, var1, "Continue", 374, false, false, self, 104, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lineno"};
      __init__$105 = Py.newCode(2, var2, var1, "__init__", 375, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$106 = Py.newCode(1, var2, var1, "getChildren", 378, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$107 = Py.newCode(1, var2, var1, "getChildNodes", 381, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$108 = Py.newCode(1, var2, var1, "__repr__", 384, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Decorators$109 = Py.newCode(0, var2, var1, "Decorators", 387, false, false, self, 109, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$110 = Py.newCode(3, var2, var1, "__init__", 388, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$111 = Py.newCode(1, var2, var1, "getChildren", 392, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$112 = Py.newCode(1, var2, var1, "getChildNodes", 395, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$113 = Py.newCode(1, var2, var1, "__repr__", 400, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Dict$114 = Py.newCode(0, var2, var1, "Dict", 403, false, false, self, 114, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "items", "lineno"};
      __init__$115 = Py.newCode(3, var2, var1, "__init__", 404, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$116 = Py.newCode(1, var2, var1, "getChildren", 408, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$117 = Py.newCode(1, var2, var1, "getChildNodes", 411, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$118 = Py.newCode(1, var2, var1, "__repr__", 416, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Discard$119 = Py.newCode(0, var2, var1, "Discard", 419, false, false, self, 119, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "lineno"};
      __init__$120 = Py.newCode(3, var2, var1, "__init__", 420, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$121 = Py.newCode(1, var2, var1, "getChildren", 424, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$122 = Py.newCode(1, var2, var1, "getChildNodes", 427, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$123 = Py.newCode(1, var2, var1, "__repr__", 430, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Div$124 = Py.newCode(0, var2, var1, "Div", 433, false, false, self, 124, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$125 = Py.newCode(3, var2, var1, "__init__", 434, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$126 = Py.newCode(1, var2, var1, "getChildren", 439, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$127 = Py.newCode(1, var2, var1, "getChildNodes", 442, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$128 = Py.newCode(1, var2, var1, "__repr__", 445, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Ellipsis$129 = Py.newCode(0, var2, var1, "Ellipsis", 448, false, false, self, 129, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lineno"};
      __init__$130 = Py.newCode(2, var2, var1, "__init__", 449, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$131 = Py.newCode(1, var2, var1, "getChildren", 452, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$132 = Py.newCode(1, var2, var1, "getChildNodes", 455, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$133 = Py.newCode(1, var2, var1, "__repr__", 458, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Exec$134 = Py.newCode(0, var2, var1, "Exec", 461, false, false, self, 134, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "locals", "globals", "lineno"};
      __init__$135 = Py.newCode(5, var2, var1, "__init__", 462, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$136 = Py.newCode(1, var2, var1, "getChildren", 468, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$137 = Py.newCode(1, var2, var1, "getChildNodes", 475, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$138 = Py.newCode(1, var2, var1, "__repr__", 484, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FloorDiv$139 = Py.newCode(0, var2, var1, "FloorDiv", 487, false, false, self, 139, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$140 = Py.newCode(3, var2, var1, "__init__", 488, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$141 = Py.newCode(1, var2, var1, "getChildren", 493, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$142 = Py.newCode(1, var2, var1, "getChildNodes", 496, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$143 = Py.newCode(1, var2, var1, "__repr__", 499, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      For$144 = Py.newCode(0, var2, var1, "For", 502, false, false, self, 144, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "assign", "list", "body", "else_", "lineno"};
      __init__$145 = Py.newCode(6, var2, var1, "__init__", 503, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$146 = Py.newCode(1, var2, var1, "getChildren", 510, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$147 = Py.newCode(1, var2, var1, "getChildNodes", 518, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$148 = Py.newCode(1, var2, var1, "__repr__", 527, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      From$149 = Py.newCode(0, var2, var1, "From", 530, false, false, self, 149, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "modname", "names", "level", "lineno"};
      __init__$150 = Py.newCode(5, var2, var1, "__init__", 531, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$151 = Py.newCode(1, var2, var1, "getChildren", 537, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$152 = Py.newCode(1, var2, var1, "getChildNodes", 540, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$153 = Py.newCode(1, var2, var1, "__repr__", 543, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Function$154 = Py.newCode(0, var2, var1, "Function", 546, false, false, self, 154, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "decorators", "name", "argnames", "defaults", "flags", "doc", "code", "lineno"};
      __init__$155 = Py.newCode(9, var2, var1, "__init__", 547, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$156 = Py.newCode(1, var2, var1, "getChildren", 563, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$157 = Py.newCode(1, var2, var1, "getChildNodes", 574, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$158 = Py.newCode(1, var2, var1, "__repr__", 582, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GenExpr$159 = Py.newCode(0, var2, var1, "GenExpr", 585, false, false, self, 159, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "code", "lineno"};
      __init__$160 = Py.newCode(3, var2, var1, "__init__", 586, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$161 = Py.newCode(1, var2, var1, "getChildren", 593, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$162 = Py.newCode(1, var2, var1, "getChildNodes", 596, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$163 = Py.newCode(1, var2, var1, "__repr__", 599, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GenExprFor$164 = Py.newCode(0, var2, var1, "GenExprFor", 602, false, false, self, 164, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "assign", "iter", "ifs", "lineno"};
      __init__$165 = Py.newCode(5, var2, var1, "__init__", 603, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$166 = Py.newCode(1, var2, var1, "getChildren", 610, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$167 = Py.newCode(1, var2, var1, "getChildNodes", 617, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$168 = Py.newCode(1, var2, var1, "__repr__", 624, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GenExprIf$169 = Py.newCode(0, var2, var1, "GenExprIf", 627, false, false, self, 169, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "lineno"};
      __init__$170 = Py.newCode(3, var2, var1, "__init__", 628, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$171 = Py.newCode(1, var2, var1, "getChildren", 632, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$172 = Py.newCode(1, var2, var1, "getChildNodes", 635, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$173 = Py.newCode(1, var2, var1, "__repr__", 638, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GenExprInner$174 = Py.newCode(0, var2, var1, "GenExprInner", 641, false, false, self, 174, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "quals", "lineno"};
      __init__$175 = Py.newCode(4, var2, var1, "__init__", 642, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$176 = Py.newCode(1, var2, var1, "getChildren", 647, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$177 = Py.newCode(1, var2, var1, "getChildNodes", 653, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$178 = Py.newCode(1, var2, var1, "__repr__", 659, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Getattr$179 = Py.newCode(0, var2, var1, "Getattr", 662, false, false, self, 179, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "attrname", "lineno"};
      __init__$180 = Py.newCode(4, var2, var1, "__init__", 663, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$181 = Py.newCode(1, var2, var1, "getChildren", 668, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$182 = Py.newCode(1, var2, var1, "getChildNodes", 671, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$183 = Py.newCode(1, var2, var1, "__repr__", 674, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Global$184 = Py.newCode(0, var2, var1, "Global", 677, false, false, self, 184, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "names", "lineno"};
      __init__$185 = Py.newCode(3, var2, var1, "__init__", 678, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$186 = Py.newCode(1, var2, var1, "getChildren", 682, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$187 = Py.newCode(1, var2, var1, "getChildNodes", 685, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$188 = Py.newCode(1, var2, var1, "__repr__", 688, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      If$189 = Py.newCode(0, var2, var1, "If", 691, false, false, self, 189, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tests", "else_", "lineno"};
      __init__$190 = Py.newCode(4, var2, var1, "__init__", 692, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$191 = Py.newCode(1, var2, var1, "getChildren", 697, false, false, self, 191, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$192 = Py.newCode(1, var2, var1, "getChildNodes", 703, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$193 = Py.newCode(1, var2, var1, "__repr__", 710, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IfExp$194 = Py.newCode(0, var2, var1, "IfExp", 713, false, false, self, 194, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "then", "else_", "lineno"};
      __init__$195 = Py.newCode(5, var2, var1, "__init__", 714, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$196 = Py.newCode(1, var2, var1, "getChildren", 720, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$197 = Py.newCode(1, var2, var1, "getChildNodes", 723, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$198 = Py.newCode(1, var2, var1, "__repr__", 726, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Import$199 = Py.newCode(0, var2, var1, "Import", 729, false, false, self, 199, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "names", "lineno"};
      __init__$200 = Py.newCode(3, var2, var1, "__init__", 730, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$201 = Py.newCode(1, var2, var1, "getChildren", 734, false, false, self, 201, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$202 = Py.newCode(1, var2, var1, "getChildNodes", 737, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$203 = Py.newCode(1, var2, var1, "__repr__", 740, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Invert$204 = Py.newCode(0, var2, var1, "Invert", 743, false, false, self, 204, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "lineno"};
      __init__$205 = Py.newCode(3, var2, var1, "__init__", 744, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$206 = Py.newCode(1, var2, var1, "getChildren", 748, false, false, self, 206, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$207 = Py.newCode(1, var2, var1, "getChildNodes", 751, false, false, self, 207, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$208 = Py.newCode(1, var2, var1, "__repr__", 754, false, false, self, 208, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Keyword$209 = Py.newCode(0, var2, var1, "Keyword", 757, false, false, self, 209, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "expr", "lineno"};
      __init__$210 = Py.newCode(4, var2, var1, "__init__", 758, false, false, self, 210, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$211 = Py.newCode(1, var2, var1, "getChildren", 763, false, false, self, 211, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$212 = Py.newCode(1, var2, var1, "getChildNodes", 766, false, false, self, 212, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$213 = Py.newCode(1, var2, var1, "__repr__", 769, false, false, self, 213, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Lambda$214 = Py.newCode(0, var2, var1, "Lambda", 772, false, false, self, 214, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "argnames", "defaults", "flags", "code", "lineno"};
      __init__$215 = Py.newCode(6, var2, var1, "__init__", 773, false, false, self, 215, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$216 = Py.newCode(1, var2, var1, "getChildren", 786, false, false, self, 216, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$217 = Py.newCode(1, var2, var1, "getChildNodes", 794, false, false, self, 217, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$218 = Py.newCode(1, var2, var1, "__repr__", 800, false, false, self, 218, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LeftShift$219 = Py.newCode(0, var2, var1, "LeftShift", 803, false, false, self, 219, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$220 = Py.newCode(3, var2, var1, "__init__", 804, false, false, self, 220, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$221 = Py.newCode(1, var2, var1, "getChildren", 809, false, false, self, 221, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$222 = Py.newCode(1, var2, var1, "getChildNodes", 812, false, false, self, 222, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$223 = Py.newCode(1, var2, var1, "__repr__", 815, false, false, self, 223, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      List$224 = Py.newCode(0, var2, var1, "List", 818, false, false, self, 224, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$225 = Py.newCode(3, var2, var1, "__init__", 819, false, false, self, 225, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$226 = Py.newCode(1, var2, var1, "getChildren", 823, false, false, self, 226, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$227 = Py.newCode(1, var2, var1, "getChildNodes", 826, false, false, self, 227, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$228 = Py.newCode(1, var2, var1, "__repr__", 831, false, false, self, 228, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ListComp$229 = Py.newCode(0, var2, var1, "ListComp", 834, false, false, self, 229, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "quals", "lineno"};
      __init__$230 = Py.newCode(4, var2, var1, "__init__", 835, false, false, self, 230, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$231 = Py.newCode(1, var2, var1, "getChildren", 840, false, false, self, 231, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$232 = Py.newCode(1, var2, var1, "getChildNodes", 846, false, false, self, 232, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$233 = Py.newCode(1, var2, var1, "__repr__", 852, false, false, self, 233, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ListCompFor$234 = Py.newCode(0, var2, var1, "ListCompFor", 855, false, false, self, 234, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "assign", "list", "ifs", "lineno"};
      __init__$235 = Py.newCode(5, var2, var1, "__init__", 856, false, false, self, 235, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$236 = Py.newCode(1, var2, var1, "getChildren", 862, false, false, self, 236, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$237 = Py.newCode(1, var2, var1, "getChildNodes", 869, false, false, self, 237, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$238 = Py.newCode(1, var2, var1, "__repr__", 876, false, false, self, 238, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ListCompIf$239 = Py.newCode(0, var2, var1, "ListCompIf", 879, false, false, self, 239, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "lineno"};
      __init__$240 = Py.newCode(3, var2, var1, "__init__", 880, false, false, self, 240, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$241 = Py.newCode(1, var2, var1, "getChildren", 884, false, false, self, 241, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$242 = Py.newCode(1, var2, var1, "getChildNodes", 887, false, false, self, 242, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$243 = Py.newCode(1, var2, var1, "__repr__", 890, false, false, self, 243, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SetComp$244 = Py.newCode(0, var2, var1, "SetComp", 893, false, false, self, 244, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "quals", "lineno"};
      __init__$245 = Py.newCode(4, var2, var1, "__init__", 894, false, false, self, 245, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$246 = Py.newCode(1, var2, var1, "getChildren", 899, false, false, self, 246, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$247 = Py.newCode(1, var2, var1, "getChildNodes", 905, false, false, self, 247, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$248 = Py.newCode(1, var2, var1, "__repr__", 911, false, false, self, 248, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DictComp$249 = Py.newCode(0, var2, var1, "DictComp", 914, false, false, self, 249, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "value", "quals", "lineno"};
      __init__$250 = Py.newCode(5, var2, var1, "__init__", 915, false, false, self, 250, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$251 = Py.newCode(1, var2, var1, "getChildren", 921, false, false, self, 251, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$252 = Py.newCode(1, var2, var1, "getChildNodes", 928, false, false, self, 252, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$253 = Py.newCode(1, var2, var1, "__repr__", 935, false, false, self, 253, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Mod$254 = Py.newCode(0, var2, var1, "Mod", 938, false, false, self, 254, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$255 = Py.newCode(3, var2, var1, "__init__", 939, false, false, self, 255, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$256 = Py.newCode(1, var2, var1, "getChildren", 944, false, false, self, 256, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$257 = Py.newCode(1, var2, var1, "getChildNodes", 947, false, false, self, 257, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$258 = Py.newCode(1, var2, var1, "__repr__", 950, false, false, self, 258, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Module$259 = Py.newCode(0, var2, var1, "Module", 953, false, false, self, 259, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "doc", "node", "lineno"};
      __init__$260 = Py.newCode(4, var2, var1, "__init__", 954, false, false, self, 260, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$261 = Py.newCode(1, var2, var1, "getChildren", 959, false, false, self, 261, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$262 = Py.newCode(1, var2, var1, "getChildNodes", 962, false, false, self, 262, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$263 = Py.newCode(1, var2, var1, "__repr__", 965, false, false, self, 263, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Mul$264 = Py.newCode(0, var2, var1, "Mul", 968, false, false, self, 264, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$265 = Py.newCode(3, var2, var1, "__init__", 969, false, false, self, 265, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$266 = Py.newCode(1, var2, var1, "getChildren", 974, false, false, self, 266, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$267 = Py.newCode(1, var2, var1, "getChildNodes", 977, false, false, self, 267, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$268 = Py.newCode(1, var2, var1, "__repr__", 980, false, false, self, 268, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Name$269 = Py.newCode(0, var2, var1, "Name", 983, false, false, self, 269, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "lineno"};
      __init__$270 = Py.newCode(3, var2, var1, "__init__", 984, false, false, self, 270, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$271 = Py.newCode(1, var2, var1, "getChildren", 988, false, false, self, 271, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$272 = Py.newCode(1, var2, var1, "getChildNodes", 991, false, false, self, 272, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$273 = Py.newCode(1, var2, var1, "__repr__", 994, false, false, self, 273, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Not$274 = Py.newCode(0, var2, var1, "Not", 997, false, false, self, 274, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "lineno"};
      __init__$275 = Py.newCode(3, var2, var1, "__init__", 998, false, false, self, 275, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$276 = Py.newCode(1, var2, var1, "getChildren", 1002, false, false, self, 276, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$277 = Py.newCode(1, var2, var1, "getChildNodes", 1005, false, false, self, 277, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$278 = Py.newCode(1, var2, var1, "__repr__", 1008, false, false, self, 278, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Or$279 = Py.newCode(0, var2, var1, "Or", 1011, false, false, self, 279, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$280 = Py.newCode(3, var2, var1, "__init__", 1012, false, false, self, 280, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$281 = Py.newCode(1, var2, var1, "getChildren", 1016, false, false, self, 281, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$282 = Py.newCode(1, var2, var1, "getChildNodes", 1019, false, false, self, 282, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$283 = Py.newCode(1, var2, var1, "__repr__", 1024, false, false, self, 283, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Pass$284 = Py.newCode(0, var2, var1, "Pass", 1027, false, false, self, 284, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lineno"};
      __init__$285 = Py.newCode(2, var2, var1, "__init__", 1028, false, false, self, 285, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$286 = Py.newCode(1, var2, var1, "getChildren", 1031, false, false, self, 286, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$287 = Py.newCode(1, var2, var1, "getChildNodes", 1034, false, false, self, 287, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$288 = Py.newCode(1, var2, var1, "__repr__", 1037, false, false, self, 288, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Power$289 = Py.newCode(0, var2, var1, "Power", 1040, false, false, self, 289, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$290 = Py.newCode(3, var2, var1, "__init__", 1041, false, false, self, 290, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$291 = Py.newCode(1, var2, var1, "getChildren", 1046, false, false, self, 291, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$292 = Py.newCode(1, var2, var1, "getChildNodes", 1049, false, false, self, 292, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$293 = Py.newCode(1, var2, var1, "__repr__", 1052, false, false, self, 293, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Print$294 = Py.newCode(0, var2, var1, "Print", 1055, false, false, self, 294, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "dest", "lineno"};
      __init__$295 = Py.newCode(4, var2, var1, "__init__", 1056, false, false, self, 295, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$296 = Py.newCode(1, var2, var1, "getChildren", 1061, false, false, self, 296, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$297 = Py.newCode(1, var2, var1, "getChildNodes", 1067, false, false, self, 297, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$298 = Py.newCode(1, var2, var1, "__repr__", 1074, false, false, self, 298, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Printnl$299 = Py.newCode(0, var2, var1, "Printnl", 1077, false, false, self, 299, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "dest", "lineno"};
      __init__$300 = Py.newCode(4, var2, var1, "__init__", 1078, false, false, self, 300, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$301 = Py.newCode(1, var2, var1, "getChildren", 1083, false, false, self, 301, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$302 = Py.newCode(1, var2, var1, "getChildNodes", 1089, false, false, self, 302, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$303 = Py.newCode(1, var2, var1, "__repr__", 1096, false, false, self, 303, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Raise$304 = Py.newCode(0, var2, var1, "Raise", 1099, false, false, self, 304, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr1", "expr2", "expr3", "lineno"};
      __init__$305 = Py.newCode(5, var2, var1, "__init__", 1100, false, false, self, 305, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$306 = Py.newCode(1, var2, var1, "getChildren", 1106, false, false, self, 306, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$307 = Py.newCode(1, var2, var1, "getChildNodes", 1113, false, false, self, 307, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$308 = Py.newCode(1, var2, var1, "__repr__", 1123, false, false, self, 308, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Return$309 = Py.newCode(0, var2, var1, "Return", 1126, false, false, self, 309, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "lineno"};
      __init__$310 = Py.newCode(3, var2, var1, "__init__", 1127, false, false, self, 310, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$311 = Py.newCode(1, var2, var1, "getChildren", 1131, false, false, self, 311, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$312 = Py.newCode(1, var2, var1, "getChildNodes", 1134, false, false, self, 312, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$313 = Py.newCode(1, var2, var1, "__repr__", 1137, false, false, self, 313, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RightShift$314 = Py.newCode(0, var2, var1, "RightShift", 1140, false, false, self, 314, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$315 = Py.newCode(3, var2, var1, "__init__", 1141, false, false, self, 315, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$316 = Py.newCode(1, var2, var1, "getChildren", 1146, false, false, self, 316, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$317 = Py.newCode(1, var2, var1, "getChildNodes", 1149, false, false, self, 317, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$318 = Py.newCode(1, var2, var1, "__repr__", 1152, false, false, self, 318, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Set$319 = Py.newCode(0, var2, var1, "Set", 1155, false, false, self, 319, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$320 = Py.newCode(3, var2, var1, "__init__", 1156, false, false, self, 320, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$321 = Py.newCode(1, var2, var1, "getChildren", 1160, false, false, self, 321, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$322 = Py.newCode(1, var2, var1, "getChildNodes", 1163, false, false, self, 322, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$323 = Py.newCode(1, var2, var1, "__repr__", 1168, false, false, self, 323, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Slice$324 = Py.newCode(0, var2, var1, "Slice", 1171, false, false, self, 324, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "flags", "lower", "upper", "lineno"};
      __init__$325 = Py.newCode(6, var2, var1, "__init__", 1172, false, false, self, 325, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$326 = Py.newCode(1, var2, var1, "getChildren", 1179, false, false, self, 326, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$327 = Py.newCode(1, var2, var1, "getChildNodes", 1187, false, false, self, 327, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$328 = Py.newCode(1, var2, var1, "__repr__", 1196, false, false, self, 328, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Sliceobj$329 = Py.newCode(0, var2, var1, "Sliceobj", 1199, false, false, self, 329, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$330 = Py.newCode(3, var2, var1, "__init__", 1200, false, false, self, 330, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$331 = Py.newCode(1, var2, var1, "getChildren", 1204, false, false, self, 331, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$332 = Py.newCode(1, var2, var1, "getChildNodes", 1207, false, false, self, 332, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$333 = Py.newCode(1, var2, var1, "__repr__", 1212, false, false, self, 333, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Stmt$334 = Py.newCode(0, var2, var1, "Stmt", 1215, false, false, self, 334, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$335 = Py.newCode(3, var2, var1, "__init__", 1216, false, false, self, 335, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$336 = Py.newCode(1, var2, var1, "getChildren", 1220, false, false, self, 336, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$337 = Py.newCode(1, var2, var1, "getChildNodes", 1223, false, false, self, 337, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$338 = Py.newCode(1, var2, var1, "__repr__", 1228, false, false, self, 338, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Sub$339 = Py.newCode(0, var2, var1, "Sub", 1231, false, false, self, 339, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "leftright", "lineno"};
      __init__$340 = Py.newCode(3, var2, var1, "__init__", 1232, false, false, self, 340, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$341 = Py.newCode(1, var2, var1, "getChildren", 1237, false, false, self, 341, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$342 = Py.newCode(1, var2, var1, "getChildNodes", 1240, false, false, self, 342, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$343 = Py.newCode(1, var2, var1, "__repr__", 1243, false, false, self, 343, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Subscript$344 = Py.newCode(0, var2, var1, "Subscript", 1246, false, false, self, 344, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "flags", "subs", "lineno"};
      __init__$345 = Py.newCode(5, var2, var1, "__init__", 1247, false, false, self, 345, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$346 = Py.newCode(1, var2, var1, "getChildren", 1253, false, false, self, 346, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$347 = Py.newCode(1, var2, var1, "getChildNodes", 1260, false, false, self, 347, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$348 = Py.newCode(1, var2, var1, "__repr__", 1266, false, false, self, 348, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TryExcept$349 = Py.newCode(0, var2, var1, "TryExcept", 1269, false, false, self, 349, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "body", "handlers", "else_", "lineno"};
      __init__$350 = Py.newCode(5, var2, var1, "__init__", 1270, false, false, self, 350, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$351 = Py.newCode(1, var2, var1, "getChildren", 1276, false, false, self, 351, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$352 = Py.newCode(1, var2, var1, "getChildNodes", 1283, false, false, self, 352, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$353 = Py.newCode(1, var2, var1, "__repr__", 1291, false, false, self, 353, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TryFinally$354 = Py.newCode(0, var2, var1, "TryFinally", 1294, false, false, self, 354, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "body", "final", "lineno"};
      __init__$355 = Py.newCode(4, var2, var1, "__init__", 1295, false, false, self, 355, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$356 = Py.newCode(1, var2, var1, "getChildren", 1300, false, false, self, 356, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$357 = Py.newCode(1, var2, var1, "getChildNodes", 1303, false, false, self, 357, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$358 = Py.newCode(1, var2, var1, "__repr__", 1306, false, false, self, 358, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Tuple$359 = Py.newCode(0, var2, var1, "Tuple", 1309, false, false, self, 359, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "nodes", "lineno"};
      __init__$360 = Py.newCode(3, var2, var1, "__init__", 1310, false, false, self, 360, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$361 = Py.newCode(1, var2, var1, "getChildren", 1314, false, false, self, 361, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$362 = Py.newCode(1, var2, var1, "getChildNodes", 1317, false, false, self, 362, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$363 = Py.newCode(1, var2, var1, "__repr__", 1322, false, false, self, 363, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnaryAdd$364 = Py.newCode(0, var2, var1, "UnaryAdd", 1325, false, false, self, 364, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "lineno"};
      __init__$365 = Py.newCode(3, var2, var1, "__init__", 1326, false, false, self, 365, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$366 = Py.newCode(1, var2, var1, "getChildren", 1330, false, false, self, 366, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$367 = Py.newCode(1, var2, var1, "getChildNodes", 1333, false, false, self, 367, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$368 = Py.newCode(1, var2, var1, "__repr__", 1336, false, false, self, 368, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnarySub$369 = Py.newCode(0, var2, var1, "UnarySub", 1339, false, false, self, 369, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "lineno"};
      __init__$370 = Py.newCode(3, var2, var1, "__init__", 1340, false, false, self, 370, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$371 = Py.newCode(1, var2, var1, "getChildren", 1344, false, false, self, 371, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$372 = Py.newCode(1, var2, var1, "getChildNodes", 1347, false, false, self, 372, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$373 = Py.newCode(1, var2, var1, "__repr__", 1350, false, false, self, 373, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      While$374 = Py.newCode(0, var2, var1, "While", 1353, false, false, self, 374, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "body", "else_", "lineno"};
      __init__$375 = Py.newCode(5, var2, var1, "__init__", 1354, false, false, self, 375, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$376 = Py.newCode(1, var2, var1, "getChildren", 1360, false, false, self, 376, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$377 = Py.newCode(1, var2, var1, "getChildNodes", 1367, false, false, self, 377, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$378 = Py.newCode(1, var2, var1, "__repr__", 1375, false, false, self, 378, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      With$379 = Py.newCode(0, var2, var1, "With", 1378, false, false, self, 379, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "vars", "body", "lineno"};
      __init__$380 = Py.newCode(5, var2, var1, "__init__", 1379, false, false, self, 380, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "children"};
      getChildren$381 = Py.newCode(1, var2, var1, "getChildren", 1385, false, false, self, 381, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nodelist"};
      getChildNodes$382 = Py.newCode(1, var2, var1, "getChildNodes", 1392, false, false, self, 382, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$383 = Py.newCode(1, var2, var1, "__repr__", 1400, false, false, self, 383, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Yield$384 = Py.newCode(0, var2, var1, "Yield", 1403, false, false, self, 384, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value", "lineno"};
      __init__$385 = Py.newCode(3, var2, var1, "__init__", 1404, false, false, self, 385, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildren$386 = Py.newCode(1, var2, var1, "getChildren", 1408, false, false, self, 386, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getChildNodes$387 = Py.newCode(1, var2, var1, "getChildNodes", 1411, false, false, self, 387, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$388 = Py.newCode(1, var2, var1, "__repr__", 1414, false, false, self, 388, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new compiler.ast$py("compiler/ast$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(compiler.ast$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.flatten$1(var2, var3);
         case 2:
            return this.flatten_nodes$2(var2, var3);
         case 3:
            return this.Node$3(var2, var3);
         case 4:
            return this.getChildren$4(var2, var3);
         case 5:
            return this.__iter__$5(var2, var3);
         case 6:
            return this.asList$6(var2, var3);
         case 7:
            return this.getChildNodes$7(var2, var3);
         case 8:
            return this.EmptyNode$8(var2, var3);
         case 9:
            return this.Expression$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.getChildren$11(var2, var3);
         case 12:
            return this.getChildNodes$12(var2, var3);
         case 13:
            return this.__repr__$13(var2, var3);
         case 14:
            return this.Add$14(var2, var3);
         case 15:
            return this.__init__$15(var2, var3);
         case 16:
            return this.getChildren$16(var2, var3);
         case 17:
            return this.getChildNodes$17(var2, var3);
         case 18:
            return this.__repr__$18(var2, var3);
         case 19:
            return this.And$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.getChildren$21(var2, var3);
         case 22:
            return this.getChildNodes$22(var2, var3);
         case 23:
            return this.__repr__$23(var2, var3);
         case 24:
            return this.AssAttr$24(var2, var3);
         case 25:
            return this.__init__$25(var2, var3);
         case 26:
            return this.getChildren$26(var2, var3);
         case 27:
            return this.getChildNodes$27(var2, var3);
         case 28:
            return this.__repr__$28(var2, var3);
         case 29:
            return this.AssList$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this.getChildren$31(var2, var3);
         case 32:
            return this.getChildNodes$32(var2, var3);
         case 33:
            return this.__repr__$33(var2, var3);
         case 34:
            return this.AssName$34(var2, var3);
         case 35:
            return this.__init__$35(var2, var3);
         case 36:
            return this.getChildren$36(var2, var3);
         case 37:
            return this.getChildNodes$37(var2, var3);
         case 38:
            return this.__repr__$38(var2, var3);
         case 39:
            return this.AssTuple$39(var2, var3);
         case 40:
            return this.__init__$40(var2, var3);
         case 41:
            return this.getChildren$41(var2, var3);
         case 42:
            return this.getChildNodes$42(var2, var3);
         case 43:
            return this.__repr__$43(var2, var3);
         case 44:
            return this.Assert$44(var2, var3);
         case 45:
            return this.__init__$45(var2, var3);
         case 46:
            return this.getChildren$46(var2, var3);
         case 47:
            return this.getChildNodes$47(var2, var3);
         case 48:
            return this.__repr__$48(var2, var3);
         case 49:
            return this.Assign$49(var2, var3);
         case 50:
            return this.__init__$50(var2, var3);
         case 51:
            return this.getChildren$51(var2, var3);
         case 52:
            return this.getChildNodes$52(var2, var3);
         case 53:
            return this.__repr__$53(var2, var3);
         case 54:
            return this.AugAssign$54(var2, var3);
         case 55:
            return this.__init__$55(var2, var3);
         case 56:
            return this.getChildren$56(var2, var3);
         case 57:
            return this.getChildNodes$57(var2, var3);
         case 58:
            return this.__repr__$58(var2, var3);
         case 59:
            return this.Backquote$59(var2, var3);
         case 60:
            return this.__init__$60(var2, var3);
         case 61:
            return this.getChildren$61(var2, var3);
         case 62:
            return this.getChildNodes$62(var2, var3);
         case 63:
            return this.__repr__$63(var2, var3);
         case 64:
            return this.Bitand$64(var2, var3);
         case 65:
            return this.__init__$65(var2, var3);
         case 66:
            return this.getChildren$66(var2, var3);
         case 67:
            return this.getChildNodes$67(var2, var3);
         case 68:
            return this.__repr__$68(var2, var3);
         case 69:
            return this.Bitor$69(var2, var3);
         case 70:
            return this.__init__$70(var2, var3);
         case 71:
            return this.getChildren$71(var2, var3);
         case 72:
            return this.getChildNodes$72(var2, var3);
         case 73:
            return this.__repr__$73(var2, var3);
         case 74:
            return this.Bitxor$74(var2, var3);
         case 75:
            return this.__init__$75(var2, var3);
         case 76:
            return this.getChildren$76(var2, var3);
         case 77:
            return this.getChildNodes$77(var2, var3);
         case 78:
            return this.__repr__$78(var2, var3);
         case 79:
            return this.Break$79(var2, var3);
         case 80:
            return this.__init__$80(var2, var3);
         case 81:
            return this.getChildren$81(var2, var3);
         case 82:
            return this.getChildNodes$82(var2, var3);
         case 83:
            return this.__repr__$83(var2, var3);
         case 84:
            return this.CallFunc$84(var2, var3);
         case 85:
            return this.__init__$85(var2, var3);
         case 86:
            return this.getChildren$86(var2, var3);
         case 87:
            return this.getChildNodes$87(var2, var3);
         case 88:
            return this.__repr__$88(var2, var3);
         case 89:
            return this.Class$89(var2, var3);
         case 90:
            return this.__init__$90(var2, var3);
         case 91:
            return this.getChildren$91(var2, var3);
         case 92:
            return this.getChildNodes$92(var2, var3);
         case 93:
            return this.__repr__$93(var2, var3);
         case 94:
            return this.Compare$94(var2, var3);
         case 95:
            return this.__init__$95(var2, var3);
         case 96:
            return this.getChildren$96(var2, var3);
         case 97:
            return this.getChildNodes$97(var2, var3);
         case 98:
            return this.__repr__$98(var2, var3);
         case 99:
            return this.Const$99(var2, var3);
         case 100:
            return this.__init__$100(var2, var3);
         case 101:
            return this.getChildren$101(var2, var3);
         case 102:
            return this.getChildNodes$102(var2, var3);
         case 103:
            return this.__repr__$103(var2, var3);
         case 104:
            return this.Continue$104(var2, var3);
         case 105:
            return this.__init__$105(var2, var3);
         case 106:
            return this.getChildren$106(var2, var3);
         case 107:
            return this.getChildNodes$107(var2, var3);
         case 108:
            return this.__repr__$108(var2, var3);
         case 109:
            return this.Decorators$109(var2, var3);
         case 110:
            return this.__init__$110(var2, var3);
         case 111:
            return this.getChildren$111(var2, var3);
         case 112:
            return this.getChildNodes$112(var2, var3);
         case 113:
            return this.__repr__$113(var2, var3);
         case 114:
            return this.Dict$114(var2, var3);
         case 115:
            return this.__init__$115(var2, var3);
         case 116:
            return this.getChildren$116(var2, var3);
         case 117:
            return this.getChildNodes$117(var2, var3);
         case 118:
            return this.__repr__$118(var2, var3);
         case 119:
            return this.Discard$119(var2, var3);
         case 120:
            return this.__init__$120(var2, var3);
         case 121:
            return this.getChildren$121(var2, var3);
         case 122:
            return this.getChildNodes$122(var2, var3);
         case 123:
            return this.__repr__$123(var2, var3);
         case 124:
            return this.Div$124(var2, var3);
         case 125:
            return this.__init__$125(var2, var3);
         case 126:
            return this.getChildren$126(var2, var3);
         case 127:
            return this.getChildNodes$127(var2, var3);
         case 128:
            return this.__repr__$128(var2, var3);
         case 129:
            return this.Ellipsis$129(var2, var3);
         case 130:
            return this.__init__$130(var2, var3);
         case 131:
            return this.getChildren$131(var2, var3);
         case 132:
            return this.getChildNodes$132(var2, var3);
         case 133:
            return this.__repr__$133(var2, var3);
         case 134:
            return this.Exec$134(var2, var3);
         case 135:
            return this.__init__$135(var2, var3);
         case 136:
            return this.getChildren$136(var2, var3);
         case 137:
            return this.getChildNodes$137(var2, var3);
         case 138:
            return this.__repr__$138(var2, var3);
         case 139:
            return this.FloorDiv$139(var2, var3);
         case 140:
            return this.__init__$140(var2, var3);
         case 141:
            return this.getChildren$141(var2, var3);
         case 142:
            return this.getChildNodes$142(var2, var3);
         case 143:
            return this.__repr__$143(var2, var3);
         case 144:
            return this.For$144(var2, var3);
         case 145:
            return this.__init__$145(var2, var3);
         case 146:
            return this.getChildren$146(var2, var3);
         case 147:
            return this.getChildNodes$147(var2, var3);
         case 148:
            return this.__repr__$148(var2, var3);
         case 149:
            return this.From$149(var2, var3);
         case 150:
            return this.__init__$150(var2, var3);
         case 151:
            return this.getChildren$151(var2, var3);
         case 152:
            return this.getChildNodes$152(var2, var3);
         case 153:
            return this.__repr__$153(var2, var3);
         case 154:
            return this.Function$154(var2, var3);
         case 155:
            return this.__init__$155(var2, var3);
         case 156:
            return this.getChildren$156(var2, var3);
         case 157:
            return this.getChildNodes$157(var2, var3);
         case 158:
            return this.__repr__$158(var2, var3);
         case 159:
            return this.GenExpr$159(var2, var3);
         case 160:
            return this.__init__$160(var2, var3);
         case 161:
            return this.getChildren$161(var2, var3);
         case 162:
            return this.getChildNodes$162(var2, var3);
         case 163:
            return this.__repr__$163(var2, var3);
         case 164:
            return this.GenExprFor$164(var2, var3);
         case 165:
            return this.__init__$165(var2, var3);
         case 166:
            return this.getChildren$166(var2, var3);
         case 167:
            return this.getChildNodes$167(var2, var3);
         case 168:
            return this.__repr__$168(var2, var3);
         case 169:
            return this.GenExprIf$169(var2, var3);
         case 170:
            return this.__init__$170(var2, var3);
         case 171:
            return this.getChildren$171(var2, var3);
         case 172:
            return this.getChildNodes$172(var2, var3);
         case 173:
            return this.__repr__$173(var2, var3);
         case 174:
            return this.GenExprInner$174(var2, var3);
         case 175:
            return this.__init__$175(var2, var3);
         case 176:
            return this.getChildren$176(var2, var3);
         case 177:
            return this.getChildNodes$177(var2, var3);
         case 178:
            return this.__repr__$178(var2, var3);
         case 179:
            return this.Getattr$179(var2, var3);
         case 180:
            return this.__init__$180(var2, var3);
         case 181:
            return this.getChildren$181(var2, var3);
         case 182:
            return this.getChildNodes$182(var2, var3);
         case 183:
            return this.__repr__$183(var2, var3);
         case 184:
            return this.Global$184(var2, var3);
         case 185:
            return this.__init__$185(var2, var3);
         case 186:
            return this.getChildren$186(var2, var3);
         case 187:
            return this.getChildNodes$187(var2, var3);
         case 188:
            return this.__repr__$188(var2, var3);
         case 189:
            return this.If$189(var2, var3);
         case 190:
            return this.__init__$190(var2, var3);
         case 191:
            return this.getChildren$191(var2, var3);
         case 192:
            return this.getChildNodes$192(var2, var3);
         case 193:
            return this.__repr__$193(var2, var3);
         case 194:
            return this.IfExp$194(var2, var3);
         case 195:
            return this.__init__$195(var2, var3);
         case 196:
            return this.getChildren$196(var2, var3);
         case 197:
            return this.getChildNodes$197(var2, var3);
         case 198:
            return this.__repr__$198(var2, var3);
         case 199:
            return this.Import$199(var2, var3);
         case 200:
            return this.__init__$200(var2, var3);
         case 201:
            return this.getChildren$201(var2, var3);
         case 202:
            return this.getChildNodes$202(var2, var3);
         case 203:
            return this.__repr__$203(var2, var3);
         case 204:
            return this.Invert$204(var2, var3);
         case 205:
            return this.__init__$205(var2, var3);
         case 206:
            return this.getChildren$206(var2, var3);
         case 207:
            return this.getChildNodes$207(var2, var3);
         case 208:
            return this.__repr__$208(var2, var3);
         case 209:
            return this.Keyword$209(var2, var3);
         case 210:
            return this.__init__$210(var2, var3);
         case 211:
            return this.getChildren$211(var2, var3);
         case 212:
            return this.getChildNodes$212(var2, var3);
         case 213:
            return this.__repr__$213(var2, var3);
         case 214:
            return this.Lambda$214(var2, var3);
         case 215:
            return this.__init__$215(var2, var3);
         case 216:
            return this.getChildren$216(var2, var3);
         case 217:
            return this.getChildNodes$217(var2, var3);
         case 218:
            return this.__repr__$218(var2, var3);
         case 219:
            return this.LeftShift$219(var2, var3);
         case 220:
            return this.__init__$220(var2, var3);
         case 221:
            return this.getChildren$221(var2, var3);
         case 222:
            return this.getChildNodes$222(var2, var3);
         case 223:
            return this.__repr__$223(var2, var3);
         case 224:
            return this.List$224(var2, var3);
         case 225:
            return this.__init__$225(var2, var3);
         case 226:
            return this.getChildren$226(var2, var3);
         case 227:
            return this.getChildNodes$227(var2, var3);
         case 228:
            return this.__repr__$228(var2, var3);
         case 229:
            return this.ListComp$229(var2, var3);
         case 230:
            return this.__init__$230(var2, var3);
         case 231:
            return this.getChildren$231(var2, var3);
         case 232:
            return this.getChildNodes$232(var2, var3);
         case 233:
            return this.__repr__$233(var2, var3);
         case 234:
            return this.ListCompFor$234(var2, var3);
         case 235:
            return this.__init__$235(var2, var3);
         case 236:
            return this.getChildren$236(var2, var3);
         case 237:
            return this.getChildNodes$237(var2, var3);
         case 238:
            return this.__repr__$238(var2, var3);
         case 239:
            return this.ListCompIf$239(var2, var3);
         case 240:
            return this.__init__$240(var2, var3);
         case 241:
            return this.getChildren$241(var2, var3);
         case 242:
            return this.getChildNodes$242(var2, var3);
         case 243:
            return this.__repr__$243(var2, var3);
         case 244:
            return this.SetComp$244(var2, var3);
         case 245:
            return this.__init__$245(var2, var3);
         case 246:
            return this.getChildren$246(var2, var3);
         case 247:
            return this.getChildNodes$247(var2, var3);
         case 248:
            return this.__repr__$248(var2, var3);
         case 249:
            return this.DictComp$249(var2, var3);
         case 250:
            return this.__init__$250(var2, var3);
         case 251:
            return this.getChildren$251(var2, var3);
         case 252:
            return this.getChildNodes$252(var2, var3);
         case 253:
            return this.__repr__$253(var2, var3);
         case 254:
            return this.Mod$254(var2, var3);
         case 255:
            return this.__init__$255(var2, var3);
         case 256:
            return this.getChildren$256(var2, var3);
         case 257:
            return this.getChildNodes$257(var2, var3);
         case 258:
            return this.__repr__$258(var2, var3);
         case 259:
            return this.Module$259(var2, var3);
         case 260:
            return this.__init__$260(var2, var3);
         case 261:
            return this.getChildren$261(var2, var3);
         case 262:
            return this.getChildNodes$262(var2, var3);
         case 263:
            return this.__repr__$263(var2, var3);
         case 264:
            return this.Mul$264(var2, var3);
         case 265:
            return this.__init__$265(var2, var3);
         case 266:
            return this.getChildren$266(var2, var3);
         case 267:
            return this.getChildNodes$267(var2, var3);
         case 268:
            return this.__repr__$268(var2, var3);
         case 269:
            return this.Name$269(var2, var3);
         case 270:
            return this.__init__$270(var2, var3);
         case 271:
            return this.getChildren$271(var2, var3);
         case 272:
            return this.getChildNodes$272(var2, var3);
         case 273:
            return this.__repr__$273(var2, var3);
         case 274:
            return this.Not$274(var2, var3);
         case 275:
            return this.__init__$275(var2, var3);
         case 276:
            return this.getChildren$276(var2, var3);
         case 277:
            return this.getChildNodes$277(var2, var3);
         case 278:
            return this.__repr__$278(var2, var3);
         case 279:
            return this.Or$279(var2, var3);
         case 280:
            return this.__init__$280(var2, var3);
         case 281:
            return this.getChildren$281(var2, var3);
         case 282:
            return this.getChildNodes$282(var2, var3);
         case 283:
            return this.__repr__$283(var2, var3);
         case 284:
            return this.Pass$284(var2, var3);
         case 285:
            return this.__init__$285(var2, var3);
         case 286:
            return this.getChildren$286(var2, var3);
         case 287:
            return this.getChildNodes$287(var2, var3);
         case 288:
            return this.__repr__$288(var2, var3);
         case 289:
            return this.Power$289(var2, var3);
         case 290:
            return this.__init__$290(var2, var3);
         case 291:
            return this.getChildren$291(var2, var3);
         case 292:
            return this.getChildNodes$292(var2, var3);
         case 293:
            return this.__repr__$293(var2, var3);
         case 294:
            return this.Print$294(var2, var3);
         case 295:
            return this.__init__$295(var2, var3);
         case 296:
            return this.getChildren$296(var2, var3);
         case 297:
            return this.getChildNodes$297(var2, var3);
         case 298:
            return this.__repr__$298(var2, var3);
         case 299:
            return this.Printnl$299(var2, var3);
         case 300:
            return this.__init__$300(var2, var3);
         case 301:
            return this.getChildren$301(var2, var3);
         case 302:
            return this.getChildNodes$302(var2, var3);
         case 303:
            return this.__repr__$303(var2, var3);
         case 304:
            return this.Raise$304(var2, var3);
         case 305:
            return this.__init__$305(var2, var3);
         case 306:
            return this.getChildren$306(var2, var3);
         case 307:
            return this.getChildNodes$307(var2, var3);
         case 308:
            return this.__repr__$308(var2, var3);
         case 309:
            return this.Return$309(var2, var3);
         case 310:
            return this.__init__$310(var2, var3);
         case 311:
            return this.getChildren$311(var2, var3);
         case 312:
            return this.getChildNodes$312(var2, var3);
         case 313:
            return this.__repr__$313(var2, var3);
         case 314:
            return this.RightShift$314(var2, var3);
         case 315:
            return this.__init__$315(var2, var3);
         case 316:
            return this.getChildren$316(var2, var3);
         case 317:
            return this.getChildNodes$317(var2, var3);
         case 318:
            return this.__repr__$318(var2, var3);
         case 319:
            return this.Set$319(var2, var3);
         case 320:
            return this.__init__$320(var2, var3);
         case 321:
            return this.getChildren$321(var2, var3);
         case 322:
            return this.getChildNodes$322(var2, var3);
         case 323:
            return this.__repr__$323(var2, var3);
         case 324:
            return this.Slice$324(var2, var3);
         case 325:
            return this.__init__$325(var2, var3);
         case 326:
            return this.getChildren$326(var2, var3);
         case 327:
            return this.getChildNodes$327(var2, var3);
         case 328:
            return this.__repr__$328(var2, var3);
         case 329:
            return this.Sliceobj$329(var2, var3);
         case 330:
            return this.__init__$330(var2, var3);
         case 331:
            return this.getChildren$331(var2, var3);
         case 332:
            return this.getChildNodes$332(var2, var3);
         case 333:
            return this.__repr__$333(var2, var3);
         case 334:
            return this.Stmt$334(var2, var3);
         case 335:
            return this.__init__$335(var2, var3);
         case 336:
            return this.getChildren$336(var2, var3);
         case 337:
            return this.getChildNodes$337(var2, var3);
         case 338:
            return this.__repr__$338(var2, var3);
         case 339:
            return this.Sub$339(var2, var3);
         case 340:
            return this.__init__$340(var2, var3);
         case 341:
            return this.getChildren$341(var2, var3);
         case 342:
            return this.getChildNodes$342(var2, var3);
         case 343:
            return this.__repr__$343(var2, var3);
         case 344:
            return this.Subscript$344(var2, var3);
         case 345:
            return this.__init__$345(var2, var3);
         case 346:
            return this.getChildren$346(var2, var3);
         case 347:
            return this.getChildNodes$347(var2, var3);
         case 348:
            return this.__repr__$348(var2, var3);
         case 349:
            return this.TryExcept$349(var2, var3);
         case 350:
            return this.__init__$350(var2, var3);
         case 351:
            return this.getChildren$351(var2, var3);
         case 352:
            return this.getChildNodes$352(var2, var3);
         case 353:
            return this.__repr__$353(var2, var3);
         case 354:
            return this.TryFinally$354(var2, var3);
         case 355:
            return this.__init__$355(var2, var3);
         case 356:
            return this.getChildren$356(var2, var3);
         case 357:
            return this.getChildNodes$357(var2, var3);
         case 358:
            return this.__repr__$358(var2, var3);
         case 359:
            return this.Tuple$359(var2, var3);
         case 360:
            return this.__init__$360(var2, var3);
         case 361:
            return this.getChildren$361(var2, var3);
         case 362:
            return this.getChildNodes$362(var2, var3);
         case 363:
            return this.__repr__$363(var2, var3);
         case 364:
            return this.UnaryAdd$364(var2, var3);
         case 365:
            return this.__init__$365(var2, var3);
         case 366:
            return this.getChildren$366(var2, var3);
         case 367:
            return this.getChildNodes$367(var2, var3);
         case 368:
            return this.__repr__$368(var2, var3);
         case 369:
            return this.UnarySub$369(var2, var3);
         case 370:
            return this.__init__$370(var2, var3);
         case 371:
            return this.getChildren$371(var2, var3);
         case 372:
            return this.getChildNodes$372(var2, var3);
         case 373:
            return this.__repr__$373(var2, var3);
         case 374:
            return this.While$374(var2, var3);
         case 375:
            return this.__init__$375(var2, var3);
         case 376:
            return this.getChildren$376(var2, var3);
         case 377:
            return this.getChildNodes$377(var2, var3);
         case 378:
            return this.__repr__$378(var2, var3);
         case 379:
            return this.With$379(var2, var3);
         case 380:
            return this.__init__$380(var2, var3);
         case 381:
            return this.getChildren$381(var2, var3);
         case 382:
            return this.getChildNodes$382(var2, var3);
         case 383:
            return this.__repr__$383(var2, var3);
         case 384:
            return this.Yield$384(var2, var3);
         case 385:
            return this.__init__$385(var2, var3);
         case 386:
            return this.getChildren$386(var2, var3);
         case 387:
            return this.getChildNodes$387(var2, var3);
         case 388:
            return this.__repr__$388(var2, var3);
         default:
            return null;
      }
   }
}
