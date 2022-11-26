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
@Filename("sre_constants.py")
public class sre_constants$py extends PyFunctionTable implements PyRunnable {
   static sre_constants$py self;
   static final PyCode f$0;
   static final PyCode error$1;
   static final PyCode makedict$2;
   static final PyCode dump$3;
   static final PyCode f$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Internal support module for sre"));
      var1.setline(12);
      PyString.fromInterned("Internal support module for sre");
      var1.setline(16);
      PyInteger var3 = Py.newInteger(20031017);
      var1.setlocal("MAGIC", var3);
      var3 = null;
      var1.setline(18);
      String[] var5 = new String[]{"MAXREPEAT"};
      PyObject[] var6 = imp.importFrom("_sre", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("MAXREPEAT", var4);
      var4 = null;
      var1.setline(23);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("error", var6, error$1);
      var1.setlocal("error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(28);
      PyString var7 = PyString.fromInterned("failure");
      var1.setlocal("FAILURE", var7);
      var3 = null;
      var1.setline(29);
      var7 = PyString.fromInterned("success");
      var1.setlocal("SUCCESS", var7);
      var3 = null;
      var1.setline(31);
      var7 = PyString.fromInterned("any");
      var1.setlocal("ANY", var7);
      var3 = null;
      var1.setline(32);
      var7 = PyString.fromInterned("any_all");
      var1.setlocal("ANY_ALL", var7);
      var3 = null;
      var1.setline(33);
      var7 = PyString.fromInterned("assert");
      var1.setlocal("ASSERT", var7);
      var3 = null;
      var1.setline(34);
      var7 = PyString.fromInterned("assert_not");
      var1.setlocal("ASSERT_NOT", var7);
      var3 = null;
      var1.setline(35);
      var7 = PyString.fromInterned("at");
      var1.setlocal("AT", var7);
      var3 = null;
      var1.setline(36);
      var7 = PyString.fromInterned("bigcharset");
      var1.setlocal("BIGCHARSET", var7);
      var3 = null;
      var1.setline(37);
      var7 = PyString.fromInterned("branch");
      var1.setlocal("BRANCH", var7);
      var3 = null;
      var1.setline(38);
      var7 = PyString.fromInterned("call");
      var1.setlocal("CALL", var7);
      var3 = null;
      var1.setline(39);
      var7 = PyString.fromInterned("category");
      var1.setlocal("CATEGORY", var7);
      var3 = null;
      var1.setline(40);
      var7 = PyString.fromInterned("charset");
      var1.setlocal("CHARSET", var7);
      var3 = null;
      var1.setline(41);
      var7 = PyString.fromInterned("groupref");
      var1.setlocal("GROUPREF", var7);
      var3 = null;
      var1.setline(42);
      var7 = PyString.fromInterned("groupref_ignore");
      var1.setlocal("GROUPREF_IGNORE", var7);
      var3 = null;
      var1.setline(43);
      var7 = PyString.fromInterned("groupref_exists");
      var1.setlocal("GROUPREF_EXISTS", var7);
      var3 = null;
      var1.setline(44);
      var7 = PyString.fromInterned("in");
      var1.setlocal("IN", var7);
      var3 = null;
      var1.setline(45);
      var7 = PyString.fromInterned("in_ignore");
      var1.setlocal("IN_IGNORE", var7);
      var3 = null;
      var1.setline(46);
      var7 = PyString.fromInterned("info");
      var1.setlocal("INFO", var7);
      var3 = null;
      var1.setline(47);
      var7 = PyString.fromInterned("jump");
      var1.setlocal("JUMP", var7);
      var3 = null;
      var1.setline(48);
      var7 = PyString.fromInterned("literal");
      var1.setlocal("LITERAL", var7);
      var3 = null;
      var1.setline(49);
      var7 = PyString.fromInterned("literal_ignore");
      var1.setlocal("LITERAL_IGNORE", var7);
      var3 = null;
      var1.setline(50);
      var7 = PyString.fromInterned("mark");
      var1.setlocal("MARK", var7);
      var3 = null;
      var1.setline(51);
      var7 = PyString.fromInterned("max_repeat");
      var1.setlocal("MAX_REPEAT", var7);
      var3 = null;
      var1.setline(52);
      var7 = PyString.fromInterned("max_until");
      var1.setlocal("MAX_UNTIL", var7);
      var3 = null;
      var1.setline(53);
      var7 = PyString.fromInterned("min_repeat");
      var1.setlocal("MIN_REPEAT", var7);
      var3 = null;
      var1.setline(54);
      var7 = PyString.fromInterned("min_until");
      var1.setlocal("MIN_UNTIL", var7);
      var3 = null;
      var1.setline(55);
      var7 = PyString.fromInterned("negate");
      var1.setlocal("NEGATE", var7);
      var3 = null;
      var1.setline(56);
      var7 = PyString.fromInterned("not_literal");
      var1.setlocal("NOT_LITERAL", var7);
      var3 = null;
      var1.setline(57);
      var7 = PyString.fromInterned("not_literal_ignore");
      var1.setlocal("NOT_LITERAL_IGNORE", var7);
      var3 = null;
      var1.setline(58);
      var7 = PyString.fromInterned("range");
      var1.setlocal("RANGE", var7);
      var3 = null;
      var1.setline(59);
      var7 = PyString.fromInterned("repeat");
      var1.setlocal("REPEAT", var7);
      var3 = null;
      var1.setline(60);
      var7 = PyString.fromInterned("repeat_one");
      var1.setlocal("REPEAT_ONE", var7);
      var3 = null;
      var1.setline(61);
      var7 = PyString.fromInterned("subpattern");
      var1.setlocal("SUBPATTERN", var7);
      var3 = null;
      var1.setline(62);
      var7 = PyString.fromInterned("min_repeat_one");
      var1.setlocal("MIN_REPEAT_ONE", var7);
      var3 = null;
      var1.setline(65);
      var7 = PyString.fromInterned("at_beginning");
      var1.setlocal("AT_BEGINNING", var7);
      var3 = null;
      var1.setline(66);
      var7 = PyString.fromInterned("at_beginning_line");
      var1.setlocal("AT_BEGINNING_LINE", var7);
      var3 = null;
      var1.setline(67);
      var7 = PyString.fromInterned("at_beginning_string");
      var1.setlocal("AT_BEGINNING_STRING", var7);
      var3 = null;
      var1.setline(68);
      var7 = PyString.fromInterned("at_boundary");
      var1.setlocal("AT_BOUNDARY", var7);
      var3 = null;
      var1.setline(69);
      var7 = PyString.fromInterned("at_non_boundary");
      var1.setlocal("AT_NON_BOUNDARY", var7);
      var3 = null;
      var1.setline(70);
      var7 = PyString.fromInterned("at_end");
      var1.setlocal("AT_END", var7);
      var3 = null;
      var1.setline(71);
      var7 = PyString.fromInterned("at_end_line");
      var1.setlocal("AT_END_LINE", var7);
      var3 = null;
      var1.setline(72);
      var7 = PyString.fromInterned("at_end_string");
      var1.setlocal("AT_END_STRING", var7);
      var3 = null;
      var1.setline(73);
      var7 = PyString.fromInterned("at_loc_boundary");
      var1.setlocal("AT_LOC_BOUNDARY", var7);
      var3 = null;
      var1.setline(74);
      var7 = PyString.fromInterned("at_loc_non_boundary");
      var1.setlocal("AT_LOC_NON_BOUNDARY", var7);
      var3 = null;
      var1.setline(75);
      var7 = PyString.fromInterned("at_uni_boundary");
      var1.setlocal("AT_UNI_BOUNDARY", var7);
      var3 = null;
      var1.setline(76);
      var7 = PyString.fromInterned("at_uni_non_boundary");
      var1.setlocal("AT_UNI_NON_BOUNDARY", var7);
      var3 = null;
      var1.setline(79);
      var7 = PyString.fromInterned("category_digit");
      var1.setlocal("CATEGORY_DIGIT", var7);
      var3 = null;
      var1.setline(80);
      var7 = PyString.fromInterned("category_not_digit");
      var1.setlocal("CATEGORY_NOT_DIGIT", var7);
      var3 = null;
      var1.setline(81);
      var7 = PyString.fromInterned("category_space");
      var1.setlocal("CATEGORY_SPACE", var7);
      var3 = null;
      var1.setline(82);
      var7 = PyString.fromInterned("category_not_space");
      var1.setlocal("CATEGORY_NOT_SPACE", var7);
      var3 = null;
      var1.setline(83);
      var7 = PyString.fromInterned("category_word");
      var1.setlocal("CATEGORY_WORD", var7);
      var3 = null;
      var1.setline(84);
      var7 = PyString.fromInterned("category_not_word");
      var1.setlocal("CATEGORY_NOT_WORD", var7);
      var3 = null;
      var1.setline(85);
      var7 = PyString.fromInterned("category_linebreak");
      var1.setlocal("CATEGORY_LINEBREAK", var7);
      var3 = null;
      var1.setline(86);
      var7 = PyString.fromInterned("category_not_linebreak");
      var1.setlocal("CATEGORY_NOT_LINEBREAK", var7);
      var3 = null;
      var1.setline(87);
      var7 = PyString.fromInterned("category_loc_word");
      var1.setlocal("CATEGORY_LOC_WORD", var7);
      var3 = null;
      var1.setline(88);
      var7 = PyString.fromInterned("category_loc_not_word");
      var1.setlocal("CATEGORY_LOC_NOT_WORD", var7);
      var3 = null;
      var1.setline(89);
      var7 = PyString.fromInterned("category_uni_digit");
      var1.setlocal("CATEGORY_UNI_DIGIT", var7);
      var3 = null;
      var1.setline(90);
      var7 = PyString.fromInterned("category_uni_not_digit");
      var1.setlocal("CATEGORY_UNI_NOT_DIGIT", var7);
      var3 = null;
      var1.setline(91);
      var7 = PyString.fromInterned("category_uni_space");
      var1.setlocal("CATEGORY_UNI_SPACE", var7);
      var3 = null;
      var1.setline(92);
      var7 = PyString.fromInterned("category_uni_not_space");
      var1.setlocal("CATEGORY_UNI_NOT_SPACE", var7);
      var3 = null;
      var1.setline(93);
      var7 = PyString.fromInterned("category_uni_word");
      var1.setlocal("CATEGORY_UNI_WORD", var7);
      var3 = null;
      var1.setline(94);
      var7 = PyString.fromInterned("category_uni_not_word");
      var1.setlocal("CATEGORY_UNI_NOT_WORD", var7);
      var3 = null;
      var1.setline(95);
      var7 = PyString.fromInterned("category_uni_linebreak");
      var1.setlocal("CATEGORY_UNI_LINEBREAK", var7);
      var3 = null;
      var1.setline(96);
      var7 = PyString.fromInterned("category_uni_not_linebreak");
      var1.setlocal("CATEGORY_UNI_NOT_LINEBREAK", var7);
      var3 = null;
      var1.setline(98);
      PyList var8 = new PyList(new PyObject[]{var1.getname("FAILURE"), var1.getname("SUCCESS"), var1.getname("ANY"), var1.getname("ANY_ALL"), var1.getname("ASSERT"), var1.getname("ASSERT_NOT"), var1.getname("AT"), var1.getname("BRANCH"), var1.getname("CALL"), var1.getname("CATEGORY"), var1.getname("CHARSET"), var1.getname("BIGCHARSET"), var1.getname("GROUPREF"), var1.getname("GROUPREF_EXISTS"), var1.getname("GROUPREF_IGNORE"), var1.getname("IN"), var1.getname("IN_IGNORE"), var1.getname("INFO"), var1.getname("JUMP"), var1.getname("LITERAL"), var1.getname("LITERAL_IGNORE"), var1.getname("MARK"), var1.getname("MAX_UNTIL"), var1.getname("MIN_UNTIL"), var1.getname("NOT_LITERAL"), var1.getname("NOT_LITERAL_IGNORE"), var1.getname("NEGATE"), var1.getname("RANGE"), var1.getname("REPEAT"), var1.getname("REPEAT_ONE"), var1.getname("SUBPATTERN"), var1.getname("MIN_REPEAT_ONE")});
      var1.setlocal("OPCODES", var8);
      var3 = null;
      var1.setline(128);
      var8 = new PyList(new PyObject[]{var1.getname("AT_BEGINNING"), var1.getname("AT_BEGINNING_LINE"), var1.getname("AT_BEGINNING_STRING"), var1.getname("AT_BOUNDARY"), var1.getname("AT_NON_BOUNDARY"), var1.getname("AT_END"), var1.getname("AT_END_LINE"), var1.getname("AT_END_STRING"), var1.getname("AT_LOC_BOUNDARY"), var1.getname("AT_LOC_NON_BOUNDARY"), var1.getname("AT_UNI_BOUNDARY"), var1.getname("AT_UNI_NON_BOUNDARY")});
      var1.setlocal("ATCODES", var8);
      var3 = null;
      var1.setline(135);
      var8 = new PyList(new PyObject[]{var1.getname("CATEGORY_DIGIT"), var1.getname("CATEGORY_NOT_DIGIT"), var1.getname("CATEGORY_SPACE"), var1.getname("CATEGORY_NOT_SPACE"), var1.getname("CATEGORY_WORD"), var1.getname("CATEGORY_NOT_WORD"), var1.getname("CATEGORY_LINEBREAK"), var1.getname("CATEGORY_NOT_LINEBREAK"), var1.getname("CATEGORY_LOC_WORD"), var1.getname("CATEGORY_LOC_NOT_WORD"), var1.getname("CATEGORY_UNI_DIGIT"), var1.getname("CATEGORY_UNI_NOT_DIGIT"), var1.getname("CATEGORY_UNI_SPACE"), var1.getname("CATEGORY_UNI_NOT_SPACE"), var1.getname("CATEGORY_UNI_WORD"), var1.getname("CATEGORY_UNI_NOT_WORD"), var1.getname("CATEGORY_UNI_LINEBREAK"), var1.getname("CATEGORY_UNI_NOT_LINEBREAK")});
      var1.setlocal("CHCODES", var8);
      var3 = null;
      var1.setline(145);
      var6 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var6, makedict$2, (PyObject)null);
      var1.setlocal("makedict", var9);
      var3 = null;
      var1.setline(153);
      PyObject var10 = var1.getname("makedict").__call__(var2, var1.getname("OPCODES"));
      var1.setlocal("OPCODES", var10);
      var3 = null;
      var1.setline(154);
      var10 = var1.getname("makedict").__call__(var2, var1.getname("ATCODES"));
      var1.setlocal("ATCODES", var10);
      var3 = null;
      var1.setline(155);
      var10 = var1.getname("makedict").__call__(var2, var1.getname("CHCODES"));
      var1.setlocal("CHCODES", var10);
      var3 = null;
      var1.setline(158);
      PyDictionary var11 = new PyDictionary(new PyObject[]{var1.getname("GROUPREF"), var1.getname("GROUPREF_IGNORE"), var1.getname("IN"), var1.getname("IN_IGNORE"), var1.getname("LITERAL"), var1.getname("LITERAL_IGNORE"), var1.getname("NOT_LITERAL"), var1.getname("NOT_LITERAL_IGNORE")});
      var1.setlocal("OP_IGNORE", var11);
      var3 = null;
      var1.setline(165);
      var11 = new PyDictionary(new PyObject[]{var1.getname("AT_BEGINNING"), var1.getname("AT_BEGINNING_LINE"), var1.getname("AT_END"), var1.getname("AT_END_LINE")});
      var1.setlocal("AT_MULTILINE", var11);
      var3 = null;
      var1.setline(170);
      var11 = new PyDictionary(new PyObject[]{var1.getname("AT_BOUNDARY"), var1.getname("AT_LOC_BOUNDARY"), var1.getname("AT_NON_BOUNDARY"), var1.getname("AT_LOC_NON_BOUNDARY")});
      var1.setlocal("AT_LOCALE", var11);
      var3 = null;
      var1.setline(175);
      var11 = new PyDictionary(new PyObject[]{var1.getname("AT_BOUNDARY"), var1.getname("AT_UNI_BOUNDARY"), var1.getname("AT_NON_BOUNDARY"), var1.getname("AT_UNI_NON_BOUNDARY")});
      var1.setlocal("AT_UNICODE", var11);
      var3 = null;
      var1.setline(180);
      var11 = new PyDictionary(new PyObject[]{var1.getname("CATEGORY_DIGIT"), var1.getname("CATEGORY_DIGIT"), var1.getname("CATEGORY_NOT_DIGIT"), var1.getname("CATEGORY_NOT_DIGIT"), var1.getname("CATEGORY_SPACE"), var1.getname("CATEGORY_SPACE"), var1.getname("CATEGORY_NOT_SPACE"), var1.getname("CATEGORY_NOT_SPACE"), var1.getname("CATEGORY_WORD"), var1.getname("CATEGORY_LOC_WORD"), var1.getname("CATEGORY_NOT_WORD"), var1.getname("CATEGORY_LOC_NOT_WORD"), var1.getname("CATEGORY_LINEBREAK"), var1.getname("CATEGORY_LINEBREAK"), var1.getname("CATEGORY_NOT_LINEBREAK"), var1.getname("CATEGORY_NOT_LINEBREAK")});
      var1.setlocal("CH_LOCALE", var11);
      var3 = null;
      var1.setline(191);
      var11 = new PyDictionary(new PyObject[]{var1.getname("CATEGORY_DIGIT"), var1.getname("CATEGORY_UNI_DIGIT"), var1.getname("CATEGORY_NOT_DIGIT"), var1.getname("CATEGORY_UNI_NOT_DIGIT"), var1.getname("CATEGORY_SPACE"), var1.getname("CATEGORY_UNI_SPACE"), var1.getname("CATEGORY_NOT_SPACE"), var1.getname("CATEGORY_UNI_NOT_SPACE"), var1.getname("CATEGORY_WORD"), var1.getname("CATEGORY_UNI_WORD"), var1.getname("CATEGORY_NOT_WORD"), var1.getname("CATEGORY_UNI_NOT_WORD"), var1.getname("CATEGORY_LINEBREAK"), var1.getname("CATEGORY_UNI_LINEBREAK"), var1.getname("CATEGORY_NOT_LINEBREAK"), var1.getname("CATEGORY_UNI_NOT_LINEBREAK")});
      var1.setlocal("CH_UNICODE", var11);
      var3 = null;
      var1.setline(203);
      var3 = Py.newInteger(1);
      var1.setlocal("SRE_FLAG_TEMPLATE", var3);
      var3 = null;
      var1.setline(204);
      var3 = Py.newInteger(2);
      var1.setlocal("SRE_FLAG_IGNORECASE", var3);
      var3 = null;
      var1.setline(205);
      var3 = Py.newInteger(4);
      var1.setlocal("SRE_FLAG_LOCALE", var3);
      var3 = null;
      var1.setline(206);
      var3 = Py.newInteger(8);
      var1.setlocal("SRE_FLAG_MULTILINE", var3);
      var3 = null;
      var1.setline(207);
      var3 = Py.newInteger(16);
      var1.setlocal("SRE_FLAG_DOTALL", var3);
      var3 = null;
      var1.setline(208);
      var3 = Py.newInteger(32);
      var1.setlocal("SRE_FLAG_UNICODE", var3);
      var3 = null;
      var1.setline(209);
      var3 = Py.newInteger(64);
      var1.setlocal("SRE_FLAG_VERBOSE", var3);
      var3 = null;
      var1.setline(210);
      var3 = Py.newInteger(128);
      var1.setlocal("SRE_FLAG_DEBUG", var3);
      var3 = null;
      var1.setline(213);
      var3 = Py.newInteger(1);
      var1.setlocal("SRE_INFO_PREFIX", var3);
      var3 = null;
      var1.setline(214);
      var3 = Py.newInteger(2);
      var1.setlocal("SRE_INFO_LITERAL", var3);
      var3 = null;
      var1.setline(215);
      var3 = Py.newInteger(4);
      var1.setlocal("SRE_INFO_CHARSET", var3);
      var3 = null;
      var1.setline(217);
      var10 = var1.getname("__name__");
      PyObject var10000 = var10._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(218);
         var6 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var6, dump$3, (PyObject)null);
         var1.setlocal("dump", var9);
         var3 = null;
         var1.setline(223);
         var10 = var1.getname("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sre_constants.h"), (PyObject)PyString.fromInterned("w"));
         var1.setlocal("f", var10);
         var3 = null;
         var1.setline(224);
         var1.getname("f").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/*\n * Secret Labs' Regular Expression Engine\n *\n * regular expression matching engine\n *\n * NOTE: This file is generated by sre_constants.py.  If you need\n * to change anything in here, edit sre_constants.py and run it.\n *\n * Copyright (c) 1997-2001 by Secret Labs AB.  All rights reserved.\n *\n * See the _sre.c file for information on usage and redistribution.\n */\n\n"));
         var1.setline(240);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_MAGIC %d\n")._mod(var1.getname("MAGIC")));
         var1.setline(242);
         var1.getname("dump").__call__((ThreadState)var2, var1.getname("f"), (PyObject)var1.getname("OPCODES"), (PyObject)PyString.fromInterned("SRE_OP"));
         var1.setline(243);
         var1.getname("dump").__call__((ThreadState)var2, var1.getname("f"), (PyObject)var1.getname("ATCODES"), (PyObject)PyString.fromInterned("SRE"));
         var1.setline(244);
         var1.getname("dump").__call__((ThreadState)var2, var1.getname("f"), (PyObject)var1.getname("CHCODES"), (PyObject)PyString.fromInterned("SRE"));
         var1.setline(246);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_TEMPLATE %d\n")._mod(var1.getname("SRE_FLAG_TEMPLATE")));
         var1.setline(247);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_IGNORECASE %d\n")._mod(var1.getname("SRE_FLAG_IGNORECASE")));
         var1.setline(248);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_LOCALE %d\n")._mod(var1.getname("SRE_FLAG_LOCALE")));
         var1.setline(249);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_MULTILINE %d\n")._mod(var1.getname("SRE_FLAG_MULTILINE")));
         var1.setline(250);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_DOTALL %d\n")._mod(var1.getname("SRE_FLAG_DOTALL")));
         var1.setline(251);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_UNICODE %d\n")._mod(var1.getname("SRE_FLAG_UNICODE")));
         var1.setline(252);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_FLAG_VERBOSE %d\n")._mod(var1.getname("SRE_FLAG_VERBOSE")));
         var1.setline(254);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_INFO_PREFIX %d\n")._mod(var1.getname("SRE_INFO_PREFIX")));
         var1.setline(255);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_INFO_LITERAL %d\n")._mod(var1.getname("SRE_INFO_LITERAL")));
         var1.setline(256);
         var1.getname("f").__getattr__("write").__call__(var2, PyString.fromInterned("#define SRE_INFO_CHARSET %d\n")._mod(var1.getname("SRE_INFO_CHARSET")));
         var1.setline(258);
         var1.getname("f").__getattr__("close").__call__(var2);
         var1.setline(259);
         Py.println(PyString.fromInterned("done"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(24);
      return var1.getf_locals();
   }

   public PyObject makedict$2(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(147);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(148);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(148);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(151);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(3, var4);
         var1.setline(149);
         PyObject var5 = var1.getlocal(2);
         var1.getlocal(1).__setitem__(var1.getlocal(3), var5);
         var5 = null;
         var1.setline(150);
         var5 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(2, var5);
         var5 = null;
      }
   }

   public PyObject dump$3(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(220);
      PyObject var10000 = var1.getlocal(3).__getattr__("sort");
      PyObject[] var7 = new PyObject[1];
      var1.setline(220);
      PyObject[] var4 = Py.EmptyObjects;
      var7[0] = new PyFunction(var1.f_globals, var4, f$4);
      String[] var8 = new String[]{"key"};
      var10000.__call__(var2, var7, var8);
      var3 = null;
      var1.setline(221);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(221);
         PyObject var9 = var3.__iternext__();
         if (var9 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var9, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(222);
         var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("#define %s_%s %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__getattr__("upper").__call__(var2), var1.getlocal(5)})));
      }
   }

   public PyObject f$4(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public sre_constants$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error$1 = Py.newCode(0, var2, var1, "error", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"list", "d", "i", "item"};
      makedict$2 = Py.newCode(1, var2, var1, "makedict", 145, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "d", "prefix", "items", "k", "v"};
      dump$3 = Py.newCode(3, var2, var1, "dump", 218, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a"};
      f$4 = Py.newCode(1, var2, var1, "<lambda>", 220, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sre_constants$py("sre_constants$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sre_constants$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.error$1(var2, var3);
         case 2:
            return this.makedict$2(var2, var3);
         case 3:
            return this.dump$3(var2, var3);
         case 4:
            return this.f$4(var2, var3);
         default:
            return null;
      }
   }
}
