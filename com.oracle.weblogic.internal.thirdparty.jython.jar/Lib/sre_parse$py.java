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
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("sre_parse.py")
public class sre_parse$py extends PyFunctionTable implements PyRunnable {
   static sre_parse$py self;
   static final PyCode f$0;
   static final PyCode Pattern$1;
   static final PyCode __init__$2;
   static final PyCode opengroup$3;
   static final PyCode closegroup$4;
   static final PyCode checkgroup$5;
   static final PyCode SubPattern$6;
   static final PyCode __init__$7;
   static final PyCode dump$8;
   static final PyCode __repr__$9;
   static final PyCode __len__$10;
   static final PyCode __delitem__$11;
   static final PyCode __getitem__$12;
   static final PyCode __setitem__$13;
   static final PyCode insert$14;
   static final PyCode append$15;
   static final PyCode getwidth$16;
   static final PyCode Tokenizer$17;
   static final PyCode __init__$18;
   static final PyCode _Tokenizer__next$19;
   static final PyCode match$20;
   static final PyCode get$21;
   static final PyCode tell$22;
   static final PyCode seek$23;
   static final PyCode isident$24;
   static final PyCode isdigit$25;
   static final PyCode isname$26;
   static final PyCode _class_escape$27;
   static final PyCode _escape$28;
   static final PyCode _parse_sub$29;
   static final PyCode _parse_sub_cond$30;
   static final PyCode _parse$31;
   static final PyCode parse$32;
   static final PyCode parse_template$33;
   static final PyCode literal$34;
   static final PyCode expand_template$35;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Internal support module for sre"));
      var1.setline(11);
      PyString.fromInterned("Internal support module for sre");
      var1.setline(15);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(17);
      imp.importAll("sre_constants", var1, -1);
      var1.setline(18);
      String[] var5 = new String[]{"MAXREPEAT"};
      PyObject[] var6 = imp.importFrom("_sre", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("MAXREPEAT", var4);
      var4 = null;
      var1.setline(20);
      PyString var7 = PyString.fromInterned(".\\[{()*+?^$|");
      var1.setlocal("SPECIAL_CHARS", var7);
      var3 = null;
      var1.setline(21);
      var7 = PyString.fromInterned("*+?{");
      var1.setlocal("REPEAT_CHARS", var7);
      var3 = null;
      var1.setline(23);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0123456789"));
      var1.setlocal("DIGITS", var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("01234567"));
      var1.setlocal("OCTDIGITS", var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0123456789abcdefABCDEF"));
      var1.setlocal("HEXDIGITS", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" \t\n\r\u000b\f"));
      var1.setlocal("WHITESPACE", var3);
      var3 = null;
      var1.setline(30);
      PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("\\a"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0007"))}), PyString.fromInterned("\\b"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\b"))}), PyString.fromInterned("\\f"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\f"))}), PyString.fromInterned("\\n"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"))}), PyString.fromInterned("\\r"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r"))}), PyString.fromInterned("\\t"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"))}), PyString.fromInterned("\\v"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u000b"))}), PyString.fromInterned("\\\\"), new PyTuple(new PyObject[]{var1.getname("LITERAL"), var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"))})});
      var1.setlocal("ESCAPES", var8);
      var3 = null;
      var1.setline(41);
      var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("\\A"), new PyTuple(new PyObject[]{var1.getname("AT"), var1.getname("AT_BEGINNING_STRING")}), PyString.fromInterned("\\b"), new PyTuple(new PyObject[]{var1.getname("AT"), var1.getname("AT_BOUNDARY")}), PyString.fromInterned("\\B"), new PyTuple(new PyObject[]{var1.getname("AT"), var1.getname("AT_NON_BOUNDARY")}), PyString.fromInterned("\\d"), new PyTuple(new PyObject[]{var1.getname("IN"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("CATEGORY"), var1.getname("CATEGORY_DIGIT")})})}), PyString.fromInterned("\\D"), new PyTuple(new PyObject[]{var1.getname("IN"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("CATEGORY"), var1.getname("CATEGORY_NOT_DIGIT")})})}), PyString.fromInterned("\\s"), new PyTuple(new PyObject[]{var1.getname("IN"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("CATEGORY"), var1.getname("CATEGORY_SPACE")})})}), PyString.fromInterned("\\S"), new PyTuple(new PyObject[]{var1.getname("IN"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("CATEGORY"), var1.getname("CATEGORY_NOT_SPACE")})})}), PyString.fromInterned("\\w"), new PyTuple(new PyObject[]{var1.getname("IN"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("CATEGORY"), var1.getname("CATEGORY_WORD")})})}), PyString.fromInterned("\\W"), new PyTuple(new PyObject[]{var1.getname("IN"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("CATEGORY"), var1.getname("CATEGORY_NOT_WORD")})})}), PyString.fromInterned("\\Z"), new PyTuple(new PyObject[]{var1.getname("AT"), var1.getname("AT_END_STRING")})});
      var1.setlocal("CATEGORIES", var8);
      var3 = null;
      var1.setline(54);
      var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("i"), var1.getname("SRE_FLAG_IGNORECASE"), PyString.fromInterned("L"), var1.getname("SRE_FLAG_LOCALE"), PyString.fromInterned("m"), var1.getname("SRE_FLAG_MULTILINE"), PyString.fromInterned("s"), var1.getname("SRE_FLAG_DOTALL"), PyString.fromInterned("x"), var1.getname("SRE_FLAG_VERBOSE"), PyString.fromInterned("t"), var1.getname("SRE_FLAG_TEMPLATE"), PyString.fromInterned("u"), var1.getname("SRE_FLAG_UNICODE")});
      var1.setlocal("FLAGS", var8);
      var3 = null;
      var1.setline(66);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Pattern", var6, Pattern$1);
      var1.setlocal("Pattern", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(89);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("SubPattern", var6, SubPattern$6);
      var1.setlocal("SubPattern", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(178);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Tokenizer", var6, Tokenizer$17);
      var1.setlocal("Tokenizer", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(211);
      var6 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var6, isident$24, (PyObject)null);
      var1.setlocal("isident", var9);
      var3 = null;
      var1.setline(214);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, isdigit$25, (PyObject)null);
      var1.setlocal("isdigit", var9);
      var3 = null;
      var1.setline(217);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, isname$26, (PyObject)null);
      var1.setlocal("isname", var9);
      var3 = null;
      var1.setline(226);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _class_escape$27, (PyObject)null);
      var1.setlocal("_class_escape", var9);
      var3 = null;
      var1.setline(258);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _escape$28, (PyObject)null);
      var1.setlocal("_escape", var9);
      var3 = null;
      var1.setline(302);
      var6 = new PyObject[]{Py.newInteger(1)};
      var9 = new PyFunction(var1.f_globals, var6, _parse_sub$29, (PyObject)null);
      var1.setlocal("_parse_sub", var9);
      var3 = null;
      var1.setline(361);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _parse_sub_cond$30, (PyObject)null);
      var1.setlocal("_parse_sub_cond", var9);
      var3 = null;
      var1.setline(375);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("|)"));
      var1.setlocal("_PATTERNENDERS", var3);
      var3 = null;
      var1.setline(376);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("=!<"));
      var1.setlocal("_ASSERTCHARS", var3);
      var3 = null;
      var1.setline(377);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("=!"));
      var1.setlocal("_LOOKBEHINDASSERTCHARS", var3);
      var3 = null;
      var1.setline(378);
      var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("MIN_REPEAT"), var1.getname("MAX_REPEAT")})));
      var1.setlocal("_REPEATCODES", var3);
      var3 = null;
      var1.setline(380);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _parse$31, (PyObject)null);
      var1.setlocal("_parse", var9);
      var3 = null;
      var1.setline(674);
      var6 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, parse$32, (PyObject)null);
      var1.setlocal("parse", var9);
      var3 = null;
      var1.setline(702);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, parse_template$33, (PyObject)null);
      var1.setlocal("parse_template", var9);
      var3 = null;
      var1.setline(789);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, expand_template$35, (PyObject)null);
      var1.setlocal("expand_template", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Pattern$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(68);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(73);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, opengroup$3, (PyObject)null);
      var1.setlocal("opengroup", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, closegroup$4, (PyObject)null);
      var1.setlocal("closegroup", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, checkgroup$5, (PyObject)null);
      var1.setlocal("checkgroup", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"flags", var3);
      var3 = null;
      var1.setline(70);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"open", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"groups", var3);
      var3 = null;
      var1.setline(72);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"groupdict", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject opengroup$3(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getlocal(0).__getattr__("groups");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getlocal(2)._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("groups", var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(77);
         var3 = var1.getlocal(0).__getattr__("groupdict").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(78);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(79);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("redefinition of group name %s as group %d; was group %d")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getlocal(2), var1.getlocal(3)})));
         }

         var1.setline(81);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("groupdict").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(82);
      var1.getlocal(0).__getattr__("open").__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.setline(83);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject closegroup$4(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getlocal(0).__getattr__("open").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject checkgroup$5(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(var1.getlocal(0).__getattr__("groups"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._notin(var1.getlocal(0).__getattr__("open"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SubPattern$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(91);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(97);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, dump$8, (PyObject)null);
      var1.setlocal("dump", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$9, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$10, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$11, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$12, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(135);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$13, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, insert$14, (PyObject)null);
      var1.setlocal("insert", var4);
      var3 = null;
      var1.setline(139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$15, (PyObject)null);
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getwidth$16, (PyObject)null);
      var1.setlocal("getwidth", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("pattern", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(95);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(96);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("width", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump$8(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(99);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects))), var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)))});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(100);
      PyObject var10 = var1.getlocal(0).__getattr__("data").__iter__();

      while(true) {
         var1.setline(100);
         PyObject var4 = var10.__iternext__();
         if (var4 == null) {
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
         var1.setline(101);
         Py.printComma(var1.getlocal(1)._mul(PyString.fromInterned("  "))._add(var1.getlocal(4)));
         var1.setline(101);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(2, var11);
         var5 = null;
         var1.setline(102);
         PyObject var12 = var1.getlocal(4);
         PyObject var10000 = var12._eq(PyString.fromInterned("in"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(104);
            Py.println();
            var1.setline(104);
            var11 = Py.newInteger(1);
            var1.setlocal(2, var11);
            var5 = null;
            var1.setline(105);
            var12 = var1.getlocal(5).__iter__();

            while(true) {
               var1.setline(105);
               var6 = var12.__iternext__();
               if (var6 == null) {
                  break;
               }

               PyObject[] var14 = Py.unpackSequence(var6, 2);
               PyObject var8 = var14[0];
               var1.setlocal(4, var8);
               var8 = null;
               var8 = var14[1];
               var1.setlocal(6, var8);
               var8 = null;
               var1.setline(106);
               Py.printComma(var1.getlocal(1)._add(Py.newInteger(1))._mul(PyString.fromInterned("  "))._add(var1.getlocal(4)));
               Py.println(var1.getlocal(6));
            }
         } else {
            var1.setline(107);
            var12 = var1.getlocal(4);
            var10000 = var12._eq(PyString.fromInterned("branch"));
            var5 = null;
            PyInteger var7;
            if (var10000.__nonzero__()) {
               var1.setline(108);
               Py.println();
               var1.setline(108);
               var11 = Py.newInteger(1);
               var1.setlocal(2, var11);
               var5 = null;
               var1.setline(109);
               var11 = Py.newInteger(0);
               var1.setlocal(7, var11);
               var5 = null;
               var1.setline(110);
               var12 = var1.getlocal(5).__getitem__(Py.newInteger(1)).__iter__();

               while(true) {
                  var1.setline(110);
                  var6 = var12.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(6, var6);
                  var1.setline(111);
                  PyObject var13 = var1.getlocal(7);
                  var10000 = var13._gt(Py.newInteger(0));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(112);
                     Py.println(var1.getlocal(1)._mul(PyString.fromInterned("  "))._add(PyString.fromInterned("or")));
                  }

                  var1.setline(113);
                  var1.getlocal(6).__getattr__("dump").__call__(var2, var1.getlocal(1)._add(Py.newInteger(1)));
                  var1.setline(113);
                  var7 = Py.newInteger(1);
                  var1.setlocal(2, var7);
                  var7 = null;
                  var1.setline(114);
                  var13 = var1.getlocal(7)._add(Py.newInteger(1));
                  var1.setlocal(7, var13);
                  var7 = null;
               }
            } else {
               var1.setline(115);
               var12 = var1.getglobal("type").__call__(var2, var1.getlocal(5));
               var10000 = var12._in(var1.getlocal(3));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(116);
                  var12 = var1.getlocal(5).__iter__();

                  while(true) {
                     var1.setline(116);
                     var6 = var12.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(6, var6);
                     var1.setline(117);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("SubPattern")).__nonzero__()) {
                        var1.setline(118);
                        if (var1.getlocal(2).__not__().__nonzero__()) {
                           var1.setline(118);
                           Py.println();
                        }

                        var1.setline(119);
                        var1.getlocal(6).__getattr__("dump").__call__(var2, var1.getlocal(1)._add(Py.newInteger(1)));
                        var1.setline(119);
                        var7 = Py.newInteger(1);
                        var1.setlocal(2, var7);
                        var7 = null;
                     } else {
                        var1.setline(121);
                        Py.printComma(var1.getlocal(6));
                        var1.setline(121);
                        var7 = Py.newInteger(0);
                        var1.setlocal(2, var7);
                        var7 = null;
                     }
                  }
               } else {
                  var1.setline(123);
                  Py.printComma(var1.getlocal(5));
                  var1.setline(123);
                  var11 = Py.newInteger(0);
                  var1.setlocal(2, var11);
                  var5 = null;
               }
            }
         }

         var1.setline(124);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(124);
            Py.println();
         }
      }
   }

   public PyObject __repr__$9(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$10(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __delitem__$11(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      var1.getlocal(0).__getattr__("data").__delitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$12(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("slice")).__nonzero__()) {
         var1.setline(133);
         var3 = var1.getglobal("SubPattern").__call__(var2, var1.getlocal(0).__getattr__("pattern"), var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(134);
         var3 = var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __setitem__$13(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("data").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject insert$14(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      var1.getlocal(0).__getattr__("data").__getattr__("insert").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append$15(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      var1.getlocal(0).__getattr__("data").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getwidth$16(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("width").__nonzero__()) {
         var1.setline(144);
         var3 = var1.getlocal(0).__getattr__("width");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(145);
         PyLong var4 = Py.newLong("0");
         var1.setlocal(1, var4);
         var1.setlocal(2, var4);
         var1.setline(146);
         PyTuple var11 = new PyTuple(new PyObject[]{var1.getglobal("ANY"), var1.getglobal("RANGE"), var1.getglobal("IN"), var1.getglobal("LITERAL"), var1.getglobal("NOT_LITERAL"), var1.getglobal("CATEGORY")});
         var1.setlocal(3, var11);
         var4 = null;
         var1.setline(147);
         var11 = new PyTuple(new PyObject[]{var1.getglobal("MIN_REPEAT"), var1.getglobal("MAX_REPEAT")});
         var1.setlocal(4, var11);
         var4 = null;
         var1.setline(148);
         PyObject var12 = var1.getlocal(0).__getattr__("data").__iter__();

         while(true) {
            var1.setline(148);
            PyObject var5 = var12.__iternext__();
            if (var5 == null) {
               break;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(5, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(149);
            PyObject var13 = var1.getlocal(5);
            PyObject var10000 = var13._eq(var1.getglobal("BRANCH"));
            var6 = null;
            PyObject var8;
            if (!var10000.__nonzero__()) {
               var1.setline(158);
               var13 = var1.getlocal(5);
               var10000 = var13._eq(var1.getglobal("CALL"));
               var6 = null;
               PyObject[] var15;
               if (var10000.__nonzero__()) {
                  var1.setline(159);
                  var13 = var1.getlocal(6).__getattr__("getwidth").__call__(var2);
                  var15 = Py.unpackSequence(var13, 2);
                  var8 = var15[0];
                  var1.setlocal(7, var8);
                  var8 = null;
                  var8 = var15[1];
                  var1.setlocal(8, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(160);
                  var13 = var1.getlocal(1)._add(var1.getlocal(7));
                  var1.setlocal(1, var13);
                  var6 = null;
                  var1.setline(161);
                  var13 = var1.getlocal(2)._add(var1.getlocal(8));
                  var1.setlocal(2, var13);
                  var6 = null;
               } else {
                  var1.setline(162);
                  var13 = var1.getlocal(5);
                  var10000 = var13._eq(var1.getglobal("SUBPATTERN"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(163);
                     var13 = var1.getlocal(6).__getitem__(Py.newInteger(1)).__getattr__("getwidth").__call__(var2);
                     var15 = Py.unpackSequence(var13, 2);
                     var8 = var15[0];
                     var1.setlocal(7, var8);
                     var8 = null;
                     var8 = var15[1];
                     var1.setlocal(8, var8);
                     var8 = null;
                     var6 = null;
                     var1.setline(164);
                     var13 = var1.getlocal(1)._add(var1.getlocal(7));
                     var1.setlocal(1, var13);
                     var6 = null;
                     var1.setline(165);
                     var13 = var1.getlocal(2)._add(var1.getlocal(8));
                     var1.setlocal(2, var13);
                     var6 = null;
                  } else {
                     var1.setline(166);
                     var13 = var1.getlocal(5);
                     var10000 = var13._in(var1.getlocal(4));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(167);
                        var13 = var1.getlocal(6).__getitem__(Py.newInteger(2)).__getattr__("getwidth").__call__(var2);
                        var15 = Py.unpackSequence(var13, 2);
                        var8 = var15[0];
                        var1.setlocal(7, var8);
                        var8 = null;
                        var8 = var15[1];
                        var1.setlocal(8, var8);
                        var8 = null;
                        var6 = null;
                        var1.setline(168);
                        var13 = var1.getlocal(1)._add(var1.getglobal("long").__call__(var2, var1.getlocal(7))._mul(var1.getlocal(6).__getitem__(Py.newInteger(0))));
                        var1.setlocal(1, var13);
                        var6 = null;
                        var1.setline(169);
                        var13 = var1.getlocal(2)._add(var1.getglobal("long").__call__(var2, var1.getlocal(8))._mul(var1.getlocal(6).__getitem__(Py.newInteger(1))));
                        var1.setlocal(2, var13);
                        var6 = null;
                     } else {
                        var1.setline(170);
                        var13 = var1.getlocal(5);
                        var10000 = var13._in(var1.getlocal(3));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(171);
                           var13 = var1.getlocal(1)._add(Py.newInteger(1));
                           var1.setlocal(1, var13);
                           var6 = null;
                           var1.setline(172);
                           var13 = var1.getlocal(2)._add(Py.newInteger(1));
                           var1.setlocal(2, var13);
                           var6 = null;
                        } else {
                           var1.setline(173);
                           var13 = var1.getlocal(5);
                           var10000 = var13._eq(var1.getglobal("SUCCESS"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              break;
                           }
                        }
                     }
                  }
               }
            } else {
               var1.setline(150);
               var13 = var1.getglobal("sys").__getattr__("maxint");
               var1.setlocal(7, var13);
               var6 = null;
               var1.setline(151);
               PyInteger var14 = Py.newInteger(0);
               var1.setlocal(8, var14);
               var6 = null;
               var1.setline(152);
               var13 = var1.getlocal(6).__getitem__(Py.newInteger(1)).__iter__();

               while(true) {
                  var1.setline(152);
                  var7 = var13.__iternext__();
                  if (var7 == null) {
                     var1.setline(156);
                     var13 = var1.getlocal(1)._add(var1.getlocal(7));
                     var1.setlocal(1, var13);
                     var6 = null;
                     var1.setline(157);
                     var13 = var1.getlocal(2)._add(var1.getlocal(8));
                     var1.setlocal(2, var13);
                     var6 = null;
                     break;
                  }

                  var1.setlocal(6, var7);
                  var1.setline(153);
                  var8 = var1.getlocal(6).__getattr__("getwidth").__call__(var2);
                  PyObject[] var9 = Py.unpackSequence(var8, 2);
                  PyObject var10 = var9[0];
                  var1.setlocal(9, var10);
                  var10 = null;
                  var10 = var9[1];
                  var1.setlocal(10, var10);
                  var10 = null;
                  var8 = null;
                  var1.setline(154);
                  var8 = var1.getglobal("min").__call__(var2, var1.getlocal(7), var1.getlocal(9));
                  var1.setlocal(7, var8);
                  var8 = null;
                  var1.setline(155);
                  var8 = var1.getglobal("max").__call__(var2, var1.getlocal(8), var1.getlocal(10));
                  var1.setlocal(8, var8);
                  var8 = null;
               }
            }
         }

         var1.setline(175);
         var11 = new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("maxint"))), var1.getglobal("int").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(2), var1.getglobal("sys").__getattr__("maxint")))});
         var1.getlocal(0).__setattr__((String)"width", var11);
         var4 = null;
         var1.setline(176);
         var3 = var1.getlocal(0).__getattr__("width");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Tokenizer$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(179);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Tokenizer__next$19, (PyObject)null);
      var1.setlocal("_Tokenizer__next", var4);
      var3 = null;
      var1.setline(196);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, match$20, (PyObject)null);
      var1.setlocal("match", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get$21, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$22, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, seek$23, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("string", var3);
      var3 = null;
      var1.setline(181);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"index", var4);
      var3 = null;
      var1.setline(182);
      var1.getlocal(0).__getattr__("_Tokenizer__next").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Tokenizer__next$19(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getlocal(0).__getattr__("index");
      PyObject var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("string")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(185);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("next", var3);
         var3 = null;
         var1.setline(186);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(187);
         var3 = var1.getlocal(0).__getattr__("string").__getitem__(var1.getlocal(0).__getattr__("index"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(188);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("\\"));
         var3 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(190);
               var3 = var1.getlocal(0).__getattr__("string").__getitem__(var1.getlocal(0).__getattr__("index")._add(Py.newInteger(1)));
               var1.setlocal(2, var3);
               var3 = null;
            } catch (Throwable var4) {
               PyException var5 = Py.setException(var4, var1);
               if (var5.match(var1.getglobal("IndexError"))) {
                  var1.setline(192);
                  throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bogus escape (end of line)"));
               }

               throw var5;
            }

            var1.setline(193);
            var3 = var1.getlocal(1)._add(var1.getlocal(2));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(194);
         var3 = var1.getlocal(0).__getattr__("index")._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var1.getlocal(0).__setattr__("index", var3);
         var3 = null;
         var1.setline(195);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("next", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject match$20(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("next"));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(198);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(199);
            var1.getlocal(0).__getattr__("_Tokenizer__next").__call__(var2);
         }

         var1.setline(200);
         var4 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(201);
         var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject get$21(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var3 = var1.getlocal(0).__getattr__("next");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(204);
      var1.getlocal(0).__getattr__("_Tokenizer__next").__call__(var2);
      var1.setline(205);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tell$22(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("index"), var1.getlocal(0).__getattr__("next")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$23(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("index", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("next", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isident$24(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString var3 = PyString.fromInterned("a");
      PyObject var10001 = var1.getlocal(0);
      PyString var10000 = var3;
      PyObject var5 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var5._le(PyString.fromInterned("z"));
      }

      PyObject var6 = var4;
      var3 = null;
      if (!var4.__nonzero__()) {
         var3 = PyString.fromInterned("A");
         var10001 = var1.getlocal(0);
         var10000 = var3;
         var5 = var10001;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var5._le(PyString.fromInterned("Z"));
         }

         var6 = var4;
         var3 = null;
         if (!var4.__nonzero__()) {
            var5 = var1.getlocal(0);
            var6 = var5._eq(PyString.fromInterned("_"));
            var3 = null;
         }
      }

      var5 = var6;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject isdigit$25(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyString var3 = PyString.fromInterned("0");
      PyObject var10001 = var1.getlocal(0);
      PyString var10000 = var3;
      PyObject var5 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var5._le(PyString.fromInterned("9"));
      }

      var3 = null;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject isname$26(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyObject var3;
      if (var1.getglobal("isident").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0))).__not__().__nonzero__()) {
         var1.setline(220);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(221);
         PyObject var4 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         PyObject var10000;
         do {
            var1.setline(221);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(224);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(1, var5);
            var1.setline(222);
            var10000 = var1.getglobal("isident").__call__(var2, var1.getlocal(1)).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isdigit").__call__(var2, var1.getlocal(1)).__not__();
            }
         } while(!var10000.__nonzero__());

         var1.setline(223);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _class_escape$27(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyObject var3 = var1.getglobal("ESCAPES").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(229);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(230);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(231);
         PyObject var4 = var1.getglobal("CATEGORIES").__getattr__("get").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(232);
         PyObject var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(var1.getglobal("IN"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(233);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            try {
               var1.setline(235);
               var4 = var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(236);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(PyString.fromInterned("x"));
               var4 = null;
               PyTuple var6;
               if (var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(238);
                     var4 = var1.getlocal(0).__getattr__("next");
                     var10000 = var4._in(var1.getglobal("HEXDIGITS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var4._lt(Py.newInteger(4));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(240);
                        var4 = var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
                        var1.setlocal(1, var4);
                        var4 = null;
                        var1.setline(241);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var4._ne(Py.newInteger(2));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(242);
                           throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bogus escape: %s")._mod(var1.getglobal("repr").__call__(var2, PyString.fromInterned("\\")._add(var1.getlocal(1)))));
                        }

                        var1.setline(243);
                        var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(16))._and(Py.newInteger(255))});
                        var1.f_lasti = -1;
                        return var6;
                     }

                     var1.setline(239);
                     var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                     var1.setlocal(1, var4);
                     var4 = null;
                  }
               }

               var1.setline(244);
               var4 = var1.getlocal(3);
               var10000 = var4._in(var1.getglobal("OCTDIGITS"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(246);
                     var4 = var1.getlocal(0).__getattr__("next");
                     var10000 = var4._in(var1.getglobal("OCTDIGITS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var4._lt(Py.newInteger(4));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(248);
                        var4 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                        var1.setlocal(1, var4);
                        var4 = null;
                        var1.setline(249);
                        var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(8))._and(Py.newInteger(255))});
                        var1.f_lasti = -1;
                        return var6;
                     }

                     var1.setline(247);
                     var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                     var1.setlocal(1, var4);
                     var4 = null;
                  }
               }

               var1.setline(250);
               var4 = var1.getlocal(3);
               var10000 = var4._in(var1.getglobal("DIGITS"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(251);
                  throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bogus escape: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
               }

               var1.setline(252);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var4._eq(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(253);
                  var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)))});
                  var1.f_lasti = -1;
                  return var6;
               }
            } catch (Throwable var5) {
               PyException var7 = Py.setException(var5, var1);
               if (!var7.match(var1.getglobal("ValueError"))) {
                  throw var7;
               }

               var1.setline(255);
            }

            var1.setline(256);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bogus escape: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
         }
      }
   }

   public PyObject _escape$28(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyObject var3 = var1.getglobal("CATEGORIES").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(261);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(262);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(263);
         PyObject var4 = var1.getglobal("ESCAPES").__getattr__("get").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(264);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(265);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            try {
               var1.setline(267);
               var4 = var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(268);
               var4 = var1.getlocal(4);
               PyObject var10000 = var4._eq(PyString.fromInterned("x"));
               var4 = null;
               PyTuple var6;
               if (var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(270);
                     var4 = var1.getlocal(0).__getattr__("next");
                     var10000 = var4._in(var1.getglobal("HEXDIGITS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var4._lt(Py.newInteger(4));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(272);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var4._ne(Py.newInteger(4));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(273);
                           throw Py.makeException(var1.getglobal("ValueError"));
                        }

                        var1.setline(274);
                        var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(16))._and(Py.newInteger(255))});
                        var1.f_lasti = -1;
                        return var6;
                     }

                     var1.setline(271);
                     var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                     var1.setlocal(1, var4);
                     var4 = null;
                  }
               }

               var1.setline(275);
               var4 = var1.getlocal(4);
               var10000 = var4._eq(PyString.fromInterned("0"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(277);
                     var4 = var1.getlocal(0).__getattr__("next");
                     var10000 = var4._in(var1.getglobal("OCTDIGITS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                        var10000 = var4._lt(Py.newInteger(4));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(279);
                        var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(8))._and(Py.newInteger(255))});
                        var1.f_lasti = -1;
                        return var6;
                     }

                     var1.setline(278);
                     var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                     var1.setlocal(1, var4);
                     var4 = null;
                  }
               }

               var1.setline(280);
               var4 = var1.getlocal(4);
               var10000 = var4._in(var1.getglobal("DIGITS"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(282);
                  var4 = var1.getlocal(0).__getattr__("next");
                  var10000 = var4._in(var1.getglobal("DIGITS"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(283);
                     var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                     var1.setlocal(1, var4);
                     var4 = null;
                     var1.setline(284);
                     var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
                     var10000 = var4._in(var1.getglobal("OCTDIGITS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
                        var10000 = var4._in(var1.getglobal("OCTDIGITS"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var4 = var1.getlocal(0).__getattr__("next");
                           var10000 = var4._in(var1.getglobal("OCTDIGITS"));
                           var4 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(287);
                        var4 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                        var1.setlocal(1, var4);
                        var4 = null;
                        var1.setline(288);
                        var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(8))._and(Py.newInteger(255))});
                        var1.f_lasti = -1;
                        return var6;
                     }
                  }

                  var1.setline(290);
                  var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(291);
                  var4 = var1.getlocal(5);
                  var10000 = var4._lt(var1.getlocal(2).__getattr__("groups"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(292);
                     if (var1.getlocal(2).__getattr__("checkgroup").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
                        var1.setline(293);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("cannot refer to open group"));
                     }

                     var1.setline(294);
                     var6 = new PyTuple(new PyObject[]{var1.getglobal("GROUPREF"), var1.getlocal(5)});
                     var1.f_lasti = -1;
                     return var6;
                  }

                  var1.setline(295);
                  throw Py.makeException(var1.getglobal("ValueError"));
               }

               var1.setline(296);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var4._eq(Py.newInteger(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(297);
                  var6 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)))});
                  var1.f_lasti = -1;
                  return var6;
               }
            } catch (Throwable var5) {
               PyException var7 = Py.setException(var5, var1);
               if (!var7.match(var1.getglobal("ValueError"))) {
                  throw var7;
               }

               var1.setline(299);
            }

            var1.setline(300);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bogus escape: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
         }
      }
   }

   public PyObject _parse_sub$29(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(306);
      PyObject var8 = var1.getlocal(3).__getattr__("append");
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(307);
      var8 = var1.getlocal(0).__getattr__("match");
      var1.setlocal(5, var8);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(308);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(309);
         var1.getlocal(4).__call__(var2, var1.getglobal("_parse").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
         var1.setline(310);
         if (!var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("|")).__nonzero__()) {
            var1.setline(312);
            if (!var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(314);
               var10000 = var1.getlocal(0).__getattr__("next").__not__();
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")"), (PyObject)Py.newInteger(0));
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(317);
                  throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("pattern not properly closed"));
               }
            }
            break;
         }
      }

      var1.setline(319);
      var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var10000 = var8._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(320);
         var8 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(322);
         PyObject var4 = var1.getglobal("SubPattern").__call__(var2, var1.getlocal(1));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(323);
         var4 = var1.getlocal(6).__getattr__("append");
         var1.setlocal(7, var4);
         var4 = null;

         PyObject var5;
         PyObject var6;
         PyObject var7;
         label88:
         while(true) {
            var1.setline(326);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(327);
            var4 = var1.getglobal("None");
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(328);
            var4 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(328);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(338);
                  var6 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(338);
                     var7 = var6.__iternext__();
                     if (var7 == null) {
                        var1.setline(340);
                        var1.getlocal(7).__call__(var2, var1.getlocal(8));
                        continue label88;
                     }

                     var1.setlocal(9, var7);
                     var1.setline(339);
                     var1.getlocal(9).__delitem__((PyObject)Py.newInteger(0));
                  }
               }

               var1.setlocal(9, var5);
               var1.setline(329);
               if (var1.getlocal(9).__not__().__nonzero__()) {
                  break label88;
               }

               var1.setline(331);
               var6 = var1.getlocal(8);
               var10000 = var6._is(var1.getglobal("None"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(332);
                  var6 = var1.getlocal(9).__getitem__(Py.newInteger(0));
                  var1.setlocal(8, var6);
                  var6 = null;
               } else {
                  var1.setline(333);
                  var6 = var1.getlocal(9).__getitem__(Py.newInteger(0));
                  var10000 = var6._ne(var1.getlocal(8));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     break label88;
                  }
               }
            }
         }

         var1.setline(345);
         var4 = var1.getlocal(3).__iter__();

         do {
            var1.setline(345);
            var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(351);
               PyList var9 = new PyList(Py.EmptyObjects);
               var1.setlocal(10, var9);
               var6 = null;
               var1.setline(352);
               var6 = var1.getlocal(10).__getattr__("append");
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(353);
               var6 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(353);
                  var7 = var6.__iternext__();
                  if (var7 == null) {
                     var1.setline(355);
                     var1.getlocal(7).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("IN"), var1.getlocal(10)})));
                     var1.setline(356);
                     var8 = var1.getlocal(6);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setlocal(9, var7);
                  var1.setline(354);
                  var1.getlocal(11).__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(0)));
               }
            }

            var1.setlocal(9, var5);
            var1.setline(346);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
            var10000 = var6._ne(Py.newInteger(1));
            var6 = null;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(9).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
               var10000 = var6._ne(var1.getglobal("LITERAL"));
               var6 = null;
            }
         } while(!var10000.__nonzero__());

         var1.setline(358);
         var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("BRANCH"), new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(3)})})));
         var1.setline(359);
         var8 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject _parse_sub_cond$30(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = var1.getglobal("_parse").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(363);
      if (var1.getlocal(0).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("|")).__nonzero__()) {
         var1.setline(364);
         var3 = var1.getglobal("_parse").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(365);
         if (var1.getlocal(0).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("|")).__nonzero__()) {
            var1.setline(366);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("conditional backref with more than two branches"));
         }
      } else {
         var1.setline(368);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(369);
      PyObject var10000 = var1.getlocal(0).__getattr__("next");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")"), (PyObject)Py.newInteger(0)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(370);
         throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("pattern not properly closed"));
      } else {
         var1.setline(371);
         var3 = var1.getglobal("SubPattern").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(372);
         var1.getlocal(5).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("GROUPREF_EXISTS"), new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})})));
         var1.setline(373);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _parse$31(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyObject var3 = var1.getglobal("SubPattern").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(385);
      var3 = var1.getlocal(2).__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(386);
      var3 = var1.getlocal(0).__getattr__("get");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(387);
      var3 = var1.getlocal(0).__getattr__("match");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(388);
      var3 = var1.getglobal("len");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(389);
      var3 = var1.getglobal("_PATTERNENDERS");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getglobal("_ASSERTCHARS");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(391);
      var3 = var1.getglobal("_LOOKBEHINDASSERTCHARS");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(392);
      var3 = var1.getglobal("_REPEATCODES");
      var1.setlocal(10, var3);
      var3 = null;

      label468:
      while(true) {
         var1.setline(394);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(396);
         var3 = var1.getlocal(0).__getattr__("next");
         PyObject var10000 = var3._in(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(398);
         var3 = var1.getlocal(4).__call__(var2);
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(399);
         var3 = var1.getlocal(11);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(402);
         if (var1.getlocal(1).__getattr__("flags")._and(var1.getglobal("SRE_FLAG_VERBOSE")).__nonzero__()) {
            var1.setline(404);
            var3 = var1.getlocal(11);
            var10000 = var3._in(var1.getglobal("WHITESPACE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               continue;
            }

            var1.setline(406);
            var3 = var1.getlocal(11);
            var10000 = var3._eq(PyString.fromInterned("#"));
            var3 = null;
            if (var10000.__nonzero__()) {
               while(true) {
                  var1.setline(407);
                  if (!Py.newInteger(1).__nonzero__()) {
                     continue label468;
                  }

                  var1.setline(408);
                  var3 = var1.getlocal(4).__call__(var2);
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(409);
                  var3 = var1.getlocal(11);
                  var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("\n")}));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     continue label468;
                  }
               }
            }
         }

         var1.setline(413);
         var10000 = var1.getlocal(11);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
            var10000 = var3._notin(var1.getglobal("SPECIAL_CHARS"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(414);
            var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(11))})));
         } else {
            var1.setline(416);
            var3 = var1.getlocal(11);
            var10000 = var3._eq(PyString.fromInterned("["));
            var3 = null;
            PyTuple var10;
            if (var10000.__nonzero__()) {
               var1.setline(418);
               PyList var11 = new PyList(Py.EmptyObjects);
               var1.setlocal(12, var11);
               var3 = null;
               var1.setline(419);
               var3 = var1.getlocal(12).__getattr__("append");
               var1.setlocal(13, var3);
               var3 = null;
               var1.setline(422);
               if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^")).__nonzero__()) {
                  var1.setline(423);
                  var1.getlocal(13).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("NEGATE"), var1.getglobal("None")})));
               }

               var1.setline(425);
               var3 = var1.getlocal(12).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.setlocal(14, var3);
               var3 = null;

               while(true) {
                  var1.setline(426);
                  if (!Py.newInteger(1).__nonzero__()) {
                     break;
                  }

                  var1.setline(427);
                  var3 = var1.getlocal(4).__call__(var2);
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(428);
                  var3 = var1.getlocal(11);
                  var10000 = var3._eq(PyString.fromInterned("]"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(12);
                     var10000 = var3._ne(var1.getlocal(14));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(430);
                  var10000 = var1.getlocal(11);
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
                     var10000 = var3._eq(PyString.fromInterned("\\"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(431);
                     var3 = var1.getglobal("_class_escape").__call__(var2, var1.getlocal(0), var1.getlocal(11));
                     var1.setlocal(15, var3);
                     var3 = null;
                  } else {
                     var1.setline(432);
                     if (!var1.getlocal(11).__nonzero__()) {
                        var1.setline(435);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unexpected end of regular expression"));
                     }

                     var1.setline(433);
                     var10 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(11))});
                     var1.setlocal(15, var10);
                     var3 = null;
                  }

                  var1.setline(436);
                  if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-")).__nonzero__()) {
                     var1.setline(438);
                     var3 = var1.getlocal(4).__call__(var2);
                     var1.setlocal(11, var3);
                     var3 = null;
                     var1.setline(439);
                     var3 = var1.getlocal(11);
                     var10000 = var3._eq(PyString.fromInterned("]"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(440);
                        var3 = var1.getlocal(15).__getitem__(Py.newInteger(0));
                        var10000 = var3._eq(var1.getglobal("IN"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(441);
                           var3 = var1.getlocal(15).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                           var1.setlocal(15, var3);
                           var3 = null;
                        }

                        var1.setline(442);
                        var1.getlocal(13).__call__(var2, var1.getlocal(15));
                        var1.setline(443);
                        var1.getlocal(13).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"))})));
                        break;
                     }

                     var1.setline(445);
                     if (!var1.getlocal(11).__nonzero__()) {
                        var1.setline(458);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unexpected end of regular expression"));
                     }

                     var1.setline(446);
                     var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
                     var10000 = var3._eq(PyString.fromInterned("\\"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(447);
                        var3 = var1.getglobal("_class_escape").__call__(var2, var1.getlocal(0), var1.getlocal(11));
                        var1.setlocal(16, var3);
                        var3 = null;
                     } else {
                        var1.setline(449);
                        var10 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(11))});
                        var1.setlocal(16, var10);
                        var3 = null;
                     }

                     var1.setline(450);
                     var3 = var1.getlocal(15).__getitem__(Py.newInteger(0));
                     var10000 = var3._ne(var1.getglobal("LITERAL"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var3 = var1.getlocal(16).__getitem__(Py.newInteger(0));
                        var10000 = var3._ne(var1.getglobal("LITERAL"));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(451);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bad character range"));
                     }

                     var1.setline(452);
                     var3 = var1.getlocal(15).__getitem__(Py.newInteger(1));
                     var1.setlocal(17, var3);
                     var3 = null;
                     var1.setline(453);
                     var3 = var1.getlocal(16).__getitem__(Py.newInteger(1));
                     var1.setlocal(18, var3);
                     var3 = null;
                     var1.setline(454);
                     var3 = var1.getlocal(18);
                     var10000 = var3._lt(var1.getlocal(17));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(455);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bad character range"));
                     }

                     var1.setline(456);
                     var1.getlocal(13).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("RANGE"), new PyTuple(new PyObject[]{var1.getlocal(17), var1.getlocal(18)})})));
                  } else {
                     var1.setline(460);
                     var3 = var1.getlocal(15).__getitem__(Py.newInteger(0));
                     var10000 = var3._eq(var1.getglobal("IN"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(461);
                        var3 = var1.getlocal(15).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                        var1.setlocal(15, var3);
                        var3 = null;
                     }

                     var1.setline(462);
                     var1.getlocal(13).__call__(var2, var1.getlocal(15));
                  }
               }

               var1.setline(465);
               var3 = var1.getlocal(6).__call__(var2, var1.getlocal(12));
               var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(12).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                  var10000 = var3._eq(var1.getglobal("LITERAL"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(466);
                  var1.getlocal(3).__call__(var2, var1.getlocal(12).__getitem__(Py.newInteger(0)));
               } else {
                  var1.setline(467);
                  var3 = var1.getlocal(6).__call__(var2, var1.getlocal(12));
                  var10000 = var3._eq(Py.newInteger(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(12).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                     var10000 = var3._eq(var1.getglobal("NEGATE"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(12).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
                        var10000 = var3._eq(var1.getglobal("LITERAL"));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(468);
                     var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("NOT_LITERAL"), var1.getlocal(12).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1))})));
                  } else {
                     var1.setline(471);
                     var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("IN"), var1.getlocal(12)})));
                  }
               }
            } else {
               var1.setline(473);
               var10000 = var1.getlocal(11);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
                  var10000 = var3._in(var1.getglobal("REPEAT_CHARS"));
                  var3 = null;
               }

               PyString var8;
               if (var10000.__nonzero__()) {
                  var1.setline(475);
                  var3 = var1.getlocal(11);
                  var10000 = var3._eq(PyString.fromInterned("?"));
                  var3 = null;
                  PyObject[] var4;
                  PyObject var5;
                  if (var10000.__nonzero__()) {
                     var1.setline(476);
                     var10 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1)});
                     var4 = Py.unpackSequence(var10, 2);
                     var5 = var4[0];
                     var1.setlocal(19, var5);
                     var5 = null;
                     var5 = var4[1];
                     var1.setlocal(20, var5);
                     var5 = null;
                     var3 = null;
                  } else {
                     var1.setline(477);
                     var3 = var1.getlocal(11);
                     var10000 = var3._eq(PyString.fromInterned("*"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(478);
                        var10 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("MAXREPEAT")});
                        var4 = Py.unpackSequence(var10, 2);
                        var5 = var4[0];
                        var1.setlocal(19, var5);
                        var5 = null;
                        var5 = var4[1];
                        var1.setlocal(20, var5);
                        var5 = null;
                        var3 = null;
                     } else {
                        var1.setline(480);
                        var3 = var1.getlocal(11);
                        var10000 = var3._eq(PyString.fromInterned("+"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(481);
                           var10 = new PyTuple(new PyObject[]{Py.newInteger(1), var1.getglobal("MAXREPEAT")});
                           var4 = Py.unpackSequence(var10, 2);
                           var5 = var4[0];
                           var1.setlocal(19, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(20, var5);
                           var5 = null;
                           var3 = null;
                        } else {
                           var1.setline(482);
                           var3 = var1.getlocal(11);
                           var10000 = var3._eq(PyString.fromInterned("{"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(511);
                              throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("not supported"));
                           }

                           var1.setline(483);
                           var3 = var1.getlocal(0).__getattr__("next");
                           var10000 = var3._eq(PyString.fromInterned("}"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(484);
                              var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(11))})));
                              continue;
                           }

                           var1.setline(486);
                           var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
                           var1.setlocal(21, var3);
                           var3 = null;
                           var1.setline(487);
                           var10 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("MAXREPEAT")});
                           var4 = Py.unpackSequence(var10, 2);
                           var5 = var4[0];
                           var1.setlocal(19, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(20, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(488);
                           var8 = PyString.fromInterned("");
                           var1.setlocal(17, var8);
                           var1.setlocal(18, var8);

                           while(true) {
                              var1.setline(489);
                              var3 = var1.getlocal(0).__getattr__("next");
                              var10000 = var3._in(var1.getglobal("DIGITS"));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 var1.setline(491);
                                 if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__nonzero__()) {
                                    while(true) {
                                       var1.setline(492);
                                       var3 = var1.getlocal(0).__getattr__("next");
                                       var10000 = var3._in(var1.getglobal("DIGITS"));
                                       var3 = null;
                                       if (!var10000.__nonzero__()) {
                                          break;
                                       }

                                       var1.setline(493);
                                       var3 = var1.getlocal(18)._add(var1.getlocal(4).__call__(var2));
                                       var1.setlocal(18, var3);
                                       var3 = null;
                                    }
                                 } else {
                                    var1.setline(495);
                                    var3 = var1.getlocal(17);
                                    var1.setlocal(18, var3);
                                    var3 = null;
                                 }

                                 var1.setline(496);
                                 if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}")).__not__().__nonzero__()) {
                                    var1.setline(497);
                                    var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getglobal("ord").__call__(var2, var1.getlocal(11))})));
                                    var1.setline(498);
                                    var1.getlocal(0).__getattr__("seek").__call__(var2, var1.getlocal(21));
                                    continue label468;
                                 }

                                 var1.setline(500);
                                 if (var1.getlocal(17).__nonzero__()) {
                                    var1.setline(501);
                                    var3 = var1.getglobal("int").__call__(var2, var1.getlocal(17));
                                    var1.setlocal(19, var3);
                                    var3 = null;
                                    var1.setline(502);
                                    var3 = var1.getlocal(19);
                                    var10000 = var3._ge(var1.getglobal("MAXREPEAT"));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(503);
                                       throw Py.makeException(var1.getglobal("OverflowError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("the repetition number is too large")));
                                    }
                                 }

                                 var1.setline(504);
                                 if (var1.getlocal(18).__nonzero__()) {
                                    var1.setline(505);
                                    var3 = var1.getglobal("int").__call__(var2, var1.getlocal(18));
                                    var1.setlocal(20, var3);
                                    var3 = null;
                                    var1.setline(506);
                                    var3 = var1.getlocal(20);
                                    var10000 = var3._ge(var1.getglobal("MAXREPEAT"));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(507);
                                       throw Py.makeException(var1.getglobal("OverflowError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("the repetition number is too large")));
                                    }

                                    var1.setline(508);
                                    var3 = var1.getlocal(20);
                                    var10000 = var3._lt(var1.getlocal(19));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(509);
                                       throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bad repeat interval")));
                                    }
                                 }
                                 break;
                              }

                              var1.setline(490);
                              var3 = var1.getlocal(17)._add(var1.getlocal(0).__getattr__("get").__call__(var2));
                              var1.setlocal(17, var3);
                              var3 = null;
                           }
                        }
                     }
                  }

                  var1.setline(513);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(514);
                     var3 = var1.getlocal(2).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
                     var1.setlocal(22, var3);
                     var3 = null;
                  } else {
                     var1.setline(516);
                     var3 = var1.getglobal("None");
                     var1.setlocal(22, var3);
                     var3 = null;
                  }

                  var1.setline(517);
                  var10000 = var1.getlocal(22).__not__();
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(6).__call__(var2, var1.getlocal(22));
                     var10000 = var3._eq(Py.newInteger(1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(22).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                        var10000 = var3._eq(var1.getglobal("AT"));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(518);
                     throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("nothing to repeat"));
                  }

                  var1.setline(519);
                  var3 = var1.getlocal(22).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                  var10000 = var3._in(var1.getlocal(10));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(520);
                     throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("multiple repeat"));
                  }

                  var1.setline(521);
                  if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?")).__nonzero__()) {
                     var1.setline(522);
                     var10 = new PyTuple(new PyObject[]{var1.getglobal("MIN_REPEAT"), new PyTuple(new PyObject[]{var1.getlocal(19), var1.getlocal(20), var1.getlocal(22)})});
                     var1.getlocal(2).__setitem__((PyObject)Py.newInteger(-1), var10);
                     var3 = null;
                  } else {
                     var1.setline(524);
                     var10 = new PyTuple(new PyObject[]{var1.getglobal("MAX_REPEAT"), new PyTuple(new PyObject[]{var1.getlocal(19), var1.getlocal(20), var1.getlocal(22)})});
                     var1.getlocal(2).__setitem__((PyObject)Py.newInteger(-1), var10);
                     var3 = null;
                  }
               } else {
                  var1.setline(526);
                  var3 = var1.getlocal(11);
                  var10000 = var3._eq(PyString.fromInterned("."));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(527);
                     var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("ANY"), var1.getglobal("None")})));
                  } else {
                     var1.setline(529);
                     var3 = var1.getlocal(11);
                     var10000 = var3._eq(PyString.fromInterned("("));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(659);
                        var3 = var1.getlocal(11);
                        var10000 = var3._eq(PyString.fromInterned("^"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(660);
                           var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AT"), var1.getglobal("AT_BEGINNING")})));
                        } else {
                           var1.setline(662);
                           var3 = var1.getlocal(11);
                           var10000 = var3._eq(PyString.fromInterned("$"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(663);
                              var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AT"), var1.getglobal("AT_END")})));
                           } else {
                              var1.setline(665);
                              var10000 = var1.getlocal(11);
                              if (var10000.__nonzero__()) {
                                 var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
                                 var10000 = var3._eq(PyString.fromInterned("\\"));
                                 var3 = null;
                              }

                              if (!var10000.__nonzero__()) {
                                 var1.setline(670);
                                 throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("parser error"));
                              }

                              var1.setline(666);
                              var3 = var1.getglobal("_escape").__call__(var2, var1.getlocal(0), var1.getlocal(11), var1.getlocal(1));
                              var1.setlocal(31, var3);
                              var3 = null;
                              var1.setline(667);
                              var1.getlocal(3).__call__(var2, var1.getlocal(31));
                           }
                        }
                     } else {
                        var1.setline(530);
                        PyInteger var7 = Py.newInteger(1);
                        var1.setlocal(23, var7);
                        var3 = null;
                        var1.setline(531);
                        var3 = var1.getglobal("None");
                        var1.setlocal(24, var3);
                        var3 = null;
                        var1.setline(532);
                        var3 = var1.getglobal("None");
                        var1.setlocal(25, var3);
                        var3 = null;
                        var1.setline(533);
                        if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?")).__nonzero__()) {
                           var1.setline(534);
                           var7 = Py.newInteger(0);
                           var1.setlocal(23, var7);
                           var3 = null;
                           var1.setline(536);
                           if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("P")).__nonzero__()) {
                              var1.setline(538);
                              if (!var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<")).__nonzero__()) {
                                 var1.setline(553);
                                 if (!var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("=")).__nonzero__()) {
                                    var1.setline(573);
                                    var3 = var1.getlocal(4).__call__(var2);
                                    var1.setlocal(26, var3);
                                    var3 = null;
                                    var1.setline(574);
                                    var3 = var1.getlocal(26);
                                    var10000 = var3._is(var1.getglobal("None"));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(575);
                                       throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unexpected end of pattern"));
                                    }

                                    var1.setline(576);
                                    throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unknown specifier: ?P%s")._mod(var1.getlocal(26)));
                                 }

                                 var1.setline(555);
                                 var8 = PyString.fromInterned("");
                                 var1.setlocal(24, var8);
                                 var3 = null;

                                 while(true) {
                                    var1.setline(556);
                                    if (!Py.newInteger(1).__nonzero__()) {
                                       break;
                                    }

                                    var1.setline(557);
                                    var3 = var1.getlocal(4).__call__(var2);
                                    var1.setlocal(26, var3);
                                    var3 = null;
                                    var1.setline(558);
                                    var3 = var1.getlocal(26);
                                    var10000 = var3._is(var1.getglobal("None"));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(559);
                                       throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unterminated name"));
                                    }

                                    var1.setline(560);
                                    var3 = var1.getlocal(26);
                                    var10000 = var3._eq(PyString.fromInterned(")"));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       break;
                                    }

                                    var1.setline(562);
                                    var3 = var1.getlocal(24)._add(var1.getlocal(26));
                                    var1.setlocal(24, var3);
                                    var3 = null;
                                 }

                                 var1.setline(563);
                                 if (var1.getlocal(24).__not__().__nonzero__()) {
                                    var1.setline(564);
                                    throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing group name")));
                                 }

                                 var1.setline(565);
                                 if (var1.getglobal("isname").__call__(var2, var1.getlocal(24)).__not__().__nonzero__()) {
                                    var1.setline(566);
                                    throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bad character in group name"));
                                 }

                                 var1.setline(567);
                                 var3 = var1.getlocal(1).__getattr__("groupdict").__getattr__("get").__call__(var2, var1.getlocal(24));
                                 var1.setlocal(27, var3);
                                 var3 = null;
                                 var1.setline(568);
                                 var3 = var1.getlocal(27);
                                 var10000 = var3._is(var1.getglobal("None"));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(569);
                                    throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unknown group name"));
                                 }

                                 var1.setline(570);
                                 var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("GROUPREF"), var1.getlocal(27)})));
                                 continue;
                              }

                              var1.setline(540);
                              var8 = PyString.fromInterned("");
                              var1.setlocal(24, var8);
                              var3 = null;

                              while(true) {
                                 var1.setline(541);
                                 if (!Py.newInteger(1).__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(542);
                                 var3 = var1.getlocal(4).__call__(var2);
                                 var1.setlocal(26, var3);
                                 var3 = null;
                                 var1.setline(543);
                                 var3 = var1.getlocal(26);
                                 var10000 = var3._is(var1.getglobal("None"));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(544);
                                    throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unterminated name"));
                                 }

                                 var1.setline(545);
                                 var3 = var1.getlocal(26);
                                 var10000 = var3._eq(PyString.fromInterned(">"));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(547);
                                 var3 = var1.getlocal(24)._add(var1.getlocal(26));
                                 var1.setlocal(24, var3);
                                 var3 = null;
                              }

                              var1.setline(548);
                              var7 = Py.newInteger(1);
                              var1.setlocal(23, var7);
                              var3 = null;
                              var1.setline(549);
                              if (var1.getlocal(24).__not__().__nonzero__()) {
                                 var1.setline(550);
                                 throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing group name")));
                              }

                              var1.setline(551);
                              if (var1.getglobal("isname").__call__(var2, var1.getlocal(24)).__not__().__nonzero__()) {
                                 var1.setline(552);
                                 throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bad character in group name"));
                              }
                           } else {
                              var1.setline(577);
                              if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":")).__nonzero__()) {
                                 var1.setline(579);
                                 var7 = Py.newInteger(2);
                                 var1.setlocal(23, var7);
                                 var3 = null;
                              } else {
                                 var1.setline(580);
                                 if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#")).__nonzero__()) {
                                    while(true) {
                                       var1.setline(582);
                                       if (!Py.newInteger(1).__nonzero__()) {
                                          break;
                                       }

                                       var1.setline(583);
                                       var3 = var1.getlocal(0).__getattr__("next");
                                       var10000 = var3._is(var1.getglobal("None"));
                                       var3 = null;
                                       if (!var10000.__nonzero__()) {
                                          var3 = var1.getlocal(0).__getattr__("next");
                                          var10000 = var3._eq(PyString.fromInterned(")"));
                                          var3 = null;
                                       }

                                       if (var10000.__nonzero__()) {
                                          break;
                                       }

                                       var1.setline(585);
                                       var1.getlocal(4).__call__(var2);
                                    }

                                    var1.setline(586);
                                    if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")")).__not__().__nonzero__()) {
                                       var1.setline(587);
                                       throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unbalanced parenthesis"));
                                    }
                                    continue;
                                 }

                                 var1.setline(589);
                                 var3 = var1.getlocal(0).__getattr__("next");
                                 var10000 = var3._in(var1.getlocal(8));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(591);
                                    var3 = var1.getlocal(4).__call__(var2);
                                    var1.setlocal(26, var3);
                                    var3 = null;
                                    var1.setline(592);
                                    var7 = Py.newInteger(1);
                                    var1.setlocal(28, var7);
                                    var3 = null;
                                    var1.setline(593);
                                    var3 = var1.getlocal(26);
                                    var10000 = var3._eq(PyString.fromInterned("<"));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(594);
                                       var3 = var1.getlocal(0).__getattr__("next");
                                       var10000 = var3._notin(var1.getlocal(9));
                                       var3 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(595);
                                          throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("syntax error"));
                                       }

                                       var1.setline(596);
                                       var7 = Py.newInteger(-1);
                                       var1.setlocal(28, var7);
                                       var3 = null;
                                       var1.setline(597);
                                       var3 = var1.getlocal(4).__call__(var2);
                                       var1.setlocal(26, var3);
                                       var3 = null;
                                    }

                                    var1.setline(598);
                                    var3 = var1.getglobal("_parse_sub").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                                    var1.setlocal(29, var3);
                                    var3 = null;
                                    var1.setline(599);
                                    if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")")).__not__().__nonzero__()) {
                                       var1.setline(600);
                                       throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unbalanced parenthesis"));
                                    }

                                    var1.setline(601);
                                    var3 = var1.getlocal(26);
                                    var10000 = var3._eq(PyString.fromInterned("="));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(602);
                                       var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("ASSERT"), new PyTuple(new PyObject[]{var1.getlocal(28), var1.getlocal(29)})})));
                                    } else {
                                       var1.setline(604);
                                       var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("ASSERT_NOT"), new PyTuple(new PyObject[]{var1.getlocal(28), var1.getlocal(29)})})));
                                    }
                                    continue;
                                 }

                                 var1.setline(606);
                                 if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(")).__nonzero__()) {
                                    var1.setline(608);
                                    var8 = PyString.fromInterned("");
                                    var1.setlocal(30, var8);
                                    var3 = null;

                                    while(true) {
                                       var1.setline(609);
                                       if (!Py.newInteger(1).__nonzero__()) {
                                          break;
                                       }

                                       var1.setline(610);
                                       var3 = var1.getlocal(4).__call__(var2);
                                       var1.setlocal(26, var3);
                                       var3 = null;
                                       var1.setline(611);
                                       var3 = var1.getlocal(26);
                                       var10000 = var3._is(var1.getglobal("None"));
                                       var3 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(612);
                                          throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unterminated name"));
                                       }

                                       var1.setline(613);
                                       var3 = var1.getlocal(26);
                                       var10000 = var3._eq(PyString.fromInterned(")"));
                                       var3 = null;
                                       if (var10000.__nonzero__()) {
                                          break;
                                       }

                                       var1.setline(615);
                                       var3 = var1.getlocal(30)._add(var1.getlocal(26));
                                       var1.setlocal(30, var3);
                                       var3 = null;
                                    }

                                    var1.setline(616);
                                    var7 = Py.newInteger(2);
                                    var1.setlocal(23, var7);
                                    var3 = null;
                                    var1.setline(617);
                                    if (var1.getlocal(30).__not__().__nonzero__()) {
                                       var1.setline(618);
                                       throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing group name")));
                                    }

                                    var1.setline(619);
                                    if (var1.getglobal("isname").__call__(var2, var1.getlocal(30)).__nonzero__()) {
                                       var1.setline(620);
                                       var3 = var1.getlocal(1).__getattr__("groupdict").__getattr__("get").__call__(var2, var1.getlocal(30));
                                       var1.setlocal(25, var3);
                                       var3 = null;
                                       var1.setline(621);
                                       var3 = var1.getlocal(25);
                                       var10000 = var3._is(var1.getglobal("None"));
                                       var3 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(622);
                                          throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unknown group name"));
                                       }
                                    } else {
                                       try {
                                          var1.setline(625);
                                          var3 = var1.getglobal("int").__call__(var2, var1.getlocal(30));
                                          var1.setlocal(25, var3);
                                          var3 = null;
                                       } catch (Throwable var6) {
                                          PyException var9 = Py.setException(var6, var1);
                                          if (var9.match(var1.getglobal("ValueError"))) {
                                             var1.setline(627);
                                             throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bad character in group name"));
                                          }

                                          throw var9;
                                       }
                                    }
                                 } else {
                                    var1.setline(630);
                                    var3 = var1.getlocal(0).__getattr__("next");
                                    var10000 = var3._in(var1.getglobal("FLAGS"));
                                    var3 = null;
                                    if (var10000.__not__().__nonzero__()) {
                                       var1.setline(631);
                                       throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unexpected end of pattern"));
                                    }

                                    while(true) {
                                       var1.setline(632);
                                       var3 = var1.getlocal(0).__getattr__("next");
                                       var10000 = var3._in(var1.getglobal("FLAGS"));
                                       var3 = null;
                                       if (!var10000.__nonzero__()) {
                                          break;
                                       }

                                       var1.setline(633);
                                       var3 = var1.getlocal(1).__getattr__("flags")._or(var1.getglobal("FLAGS").__getitem__(var1.getlocal(4).__call__(var2)));
                                       var1.getlocal(1).__setattr__("flags", var3);
                                       var3 = null;
                                    }
                                 }
                              }
                           }
                        }

                        var1.setline(634);
                        if (var1.getlocal(23).__nonzero__()) {
                           var1.setline(636);
                           var3 = var1.getlocal(23);
                           var10000 = var3._eq(Py.newInteger(2));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(638);
                              var3 = var1.getglobal("None");
                              var1.setlocal(23, var3);
                              var3 = null;
                           } else {
                              var1.setline(640);
                              var3 = var1.getlocal(1).__getattr__("opengroup").__call__(var2, var1.getlocal(24));
                              var1.setlocal(23, var3);
                              var3 = null;
                           }

                           var1.setline(641);
                           if (var1.getlocal(25).__nonzero__()) {
                              var1.setline(642);
                              var3 = var1.getglobal("_parse_sub_cond").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(25));
                              var1.setlocal(29, var3);
                              var3 = null;
                           } else {
                              var1.setline(644);
                              var3 = var1.getglobal("_parse_sub").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                              var1.setlocal(29, var3);
                              var3 = null;
                           }

                           var1.setline(645);
                           if (var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")")).__not__().__nonzero__()) {
                              var1.setline(646);
                              throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unbalanced parenthesis"));
                           }

                           var1.setline(647);
                           var3 = var1.getlocal(23);
                           var10000 = var3._isnot(var1.getglobal("None"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(648);
                              var1.getlocal(1).__getattr__("closegroup").__call__(var2, var1.getlocal(23));
                           }

                           var1.setline(649);
                           var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("SUBPATTERN"), new PyTuple(new PyObject[]{var1.getlocal(23), var1.getlocal(29)})})));
                        } else {
                           var1.setline(651);
                           if (Py.newInteger(1).__nonzero__()) {
                              var1.setline(652);
                              var3 = var1.getlocal(4).__call__(var2);
                              var1.setlocal(26, var3);
                              var3 = null;
                              var1.setline(653);
                              var3 = var1.getlocal(26);
                              var10000 = var3._is(var1.getglobal("None"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(654);
                                 throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unexpected end of pattern"));
                              }

                              var1.setline(655);
                              var3 = var1.getlocal(26);
                              var10000 = var3._eq(PyString.fromInterned(")"));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 var1.setline(657);
                                 throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unknown extension"));
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      var1.setline(672);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse$32(PyFrame var1, ThreadState var2) {
      var1.setline(677);
      PyObject var3 = var1.getglobal("Tokenizer").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(679);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(680);
         var3 = var1.getglobal("Pattern").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(681);
      var3 = var1.getlocal(1);
      var1.getlocal(2).__setattr__("flags", var3);
      var3 = null;
      var1.setline(682);
      var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("str", var3);
      var3 = null;
      var1.setline(684);
      var3 = var1.getglobal("_parse_sub").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(686);
      var3 = var1.getlocal(3).__getattr__("get").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(687);
      var3 = var1.getlocal(5);
      var10000 = var3._eq(PyString.fromInterned(")"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(688);
         throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unbalanced parenthesis"));
      } else {
         var1.setline(689);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(690);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bogus characters at end of regular expression"));
         } else {
            var1.setline(692);
            if (var1.getlocal(1)._and(var1.getglobal("SRE_FLAG_DEBUG")).__nonzero__()) {
               var1.setline(693);
               var1.getlocal(4).__getattr__("dump").__call__(var2);
            }

            var1.setline(695);
            var10000 = var1.getlocal(1)._and(var1.getglobal("SRE_FLAG_VERBOSE")).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4).__getattr__("pattern").__getattr__("flags")._and(var1.getglobal("SRE_FLAG_VERBOSE"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(698);
               var3 = var1.getglobal("parse").__call__(var2, var1.getlocal(0), var1.getlocal(4).__getattr__("pattern").__getattr__("flags"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(700);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject parse_template$33(PyFrame var1, ThreadState var2) {
      var1.setline(705);
      PyObject var3 = var1.getglobal("Tokenizer").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(706);
      var3 = var1.getlocal(2).__getattr__("get");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(707);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(708);
      var3 = var1.getlocal(4).__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(709);
      PyObject[] var13 = new PyObject[]{var1.getlocal(4), var1.getlocal(5)};
      PyFunction var14 = new PyFunction(var1.f_globals, var13, literal$34, (PyObject)null);
      var1.setlocal(6, var14);
      var3 = null;
      var1.setline(714);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(0), (PyObject)null);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(715);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(7));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(716);
         var3 = var1.getglobal("chr");
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(718);
         var3 = var1.getglobal("unichr");
         var1.setlocal(8, var3);
         var3 = null;
      }

      while(true) {
         var1.setline(719);
         PyObject var4;
         if (Py.newInteger(1).__nonzero__()) {
            var1.setline(720);
            var3 = var1.getlocal(3).__call__(var2);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(721);
            var3 = var1.getlocal(9);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(723);
               var10000 = var1.getlocal(9);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(9).__getitem__(Py.newInteger(0));
                  var10000 = var3._eq(PyString.fromInterned("\\"));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(774);
                  var1.getlocal(6).__call__(var2, var1.getlocal(9));
                  continue;
               }

               var1.setline(725);
               var3 = var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(726);
               var3 = var1.getlocal(10);
               var10000 = var3._eq(PyString.fromInterned("g"));
               var3 = null;
               PyException var17;
               if (var10000.__nonzero__()) {
                  var1.setline(727);
                  PyString var18 = PyString.fromInterned("");
                  var1.setlocal(11, var18);
                  var3 = null;
                  var1.setline(728);
                  if (var1.getlocal(2).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<")).__nonzero__()) {
                     while(true) {
                        var1.setline(729);
                        if (!Py.newInteger(1).__nonzero__()) {
                           break;
                        }

                        var1.setline(730);
                        var3 = var1.getlocal(3).__call__(var2);
                        var1.setlocal(12, var3);
                        var3 = null;
                        var1.setline(731);
                        var3 = var1.getlocal(12);
                        var10000 = var3._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(732);
                           throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unterminated group name"));
                        }

                        var1.setline(733);
                        var3 = var1.getlocal(12);
                        var10000 = var3._eq(PyString.fromInterned(">"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(735);
                        var3 = var1.getlocal(11)._add(var1.getlocal(12));
                        var1.setlocal(11, var3);
                        var3 = null;
                     }
                  }

                  var1.setline(736);
                  if (var1.getlocal(11).__not__().__nonzero__()) {
                     var1.setline(737);
                     throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("missing group name"));
                  }

                  try {
                     var1.setline(739);
                     var3 = var1.getglobal("int").__call__(var2, var1.getlocal(11));
                     var1.setlocal(13, var3);
                     var3 = null;
                     var1.setline(740);
                     var3 = var1.getlocal(13);
                     var10000 = var3._lt(Py.newInteger(0));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(741);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("negative group number"));
                     }
                  } catch (Throwable var8) {
                     var17 = Py.setException(var8, var1);
                     if (!var17.match(var1.getglobal("ValueError"))) {
                        throw var17;
                     }

                     var1.setline(743);
                     if (var1.getglobal("isname").__call__(var2, var1.getlocal(11)).__not__().__nonzero__()) {
                        var1.setline(744);
                        throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("bad character in group name"));
                     }

                     try {
                        var1.setline(746);
                        var4 = var1.getlocal(1).__getattr__("groupindex").__getitem__(var1.getlocal(11));
                        var1.setlocal(13, var4);
                        var4 = null;
                     } catch (Throwable var7) {
                        PyException var11 = Py.setException(var7, var1);
                        if (var11.match(var1.getglobal("KeyError"))) {
                           var1.setline(748);
                           throw Py.makeException(var1.getglobal("IndexError"), PyString.fromInterned("unknown group name"));
                        }

                        throw var11;
                     }
                  }

                  var1.setline(749);
                  var1.getlocal(5).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("MARK"), var1.getlocal(13)})));
                  continue;
               }

               var1.setline(750);
               var3 = var1.getlocal(10);
               var10000 = var3._eq(PyString.fromInterned("0"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(751);
                  var3 = var1.getlocal(2).__getattr__("next");
                  var10000 = var3._in(var1.getglobal("OCTDIGITS"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(752);
                     var3 = var1.getlocal(9)._add(var1.getlocal(3).__call__(var2));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(753);
                     var3 = var1.getlocal(2).__getattr__("next");
                     var10000 = var3._in(var1.getglobal("OCTDIGITS"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(754);
                        var3 = var1.getlocal(9)._add(var1.getlocal(3).__call__(var2));
                        var1.setlocal(9, var3);
                        var3 = null;
                     }
                  }

                  var1.setline(755);
                  var1.getlocal(6).__call__(var2, var1.getlocal(8).__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(8))._and(Py.newInteger(255))));
                  continue;
               }

               var1.setline(756);
               var3 = var1.getlocal(10);
               var10000 = var3._in(var1.getglobal("DIGITS"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(757);
                  var3 = var1.getglobal("False");
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(758);
                  var3 = var1.getlocal(2).__getattr__("next");
                  var10000 = var3._in(var1.getglobal("DIGITS"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(759);
                     var3 = var1.getlocal(9)._add(var1.getlocal(3).__call__(var2));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(760);
                     var3 = var1.getlocal(10);
                     var10000 = var3._in(var1.getglobal("OCTDIGITS"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(9).__getitem__(Py.newInteger(2));
                        var10000 = var3._in(var1.getglobal("OCTDIGITS"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(2).__getattr__("next");
                           var10000 = var3._in(var1.getglobal("OCTDIGITS"));
                           var3 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(762);
                        var3 = var1.getlocal(9)._add(var1.getlocal(3).__call__(var2));
                        var1.setlocal(9, var3);
                        var3 = null;
                        var1.setline(763);
                        var3 = var1.getglobal("True");
                        var1.setlocal(14, var3);
                        var3 = null;
                        var1.setline(764);
                        var1.getlocal(6).__call__(var2, var1.getlocal(8).__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(9).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(8))._and(Py.newInteger(255))));
                     }
                  }

                  var1.setline(765);
                  if (var1.getlocal(14).__not__().__nonzero__()) {
                     var1.setline(766);
                     var1.getlocal(5).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("MARK"), var1.getglobal("int").__call__(var2, var1.getlocal(9).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null))})));
                  }
                  continue;
               }

               try {
                  var1.setline(769);
                  var3 = var1.getlocal(8).__call__(var2, var1.getglobal("ESCAPES").__getitem__(var1.getlocal(9)).__getitem__(Py.newInteger(1)));
                  var1.setlocal(9, var3);
                  var3 = null;
               } catch (Throwable var9) {
                  var17 = Py.setException(var9, var1);
                  if (!var17.match(var1.getglobal("KeyError"))) {
                     throw var17;
                  }

                  var1.setline(771);
               }

               var1.setline(772);
               var1.getlocal(6).__call__(var2, var1.getlocal(9));
               continue;
            }
         }

         var1.setline(776);
         PyInteger var15 = Py.newInteger(0);
         var1.setlocal(15, var15);
         var3 = null;
         var1.setline(777);
         var10 = new PyList(Py.EmptyObjects);
         var1.setlocal(16, var10);
         var3 = null;
         var1.setline(778);
         var3 = var1.getlocal(16).__getattr__("append");
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(779);
         var3 = (new PyList(new PyObject[]{var1.getglobal("None")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var1.setlocal(18, var3);
         var3 = null;
         var1.setline(780);
         var3 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(780);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(787);
               PyTuple var16 = new PyTuple(new PyObject[]{var1.getlocal(16), var1.getlocal(18)});
               var1.f_lasti = -1;
               return var16;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(781);
            PyObject var12 = var1.getlocal(10);
            var10000 = var12._eq(var1.getglobal("MARK"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(782);
               var1.getlocal(17).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(15), var1.getlocal(2)})));
            } else {
               var1.setline(785);
               var12 = var1.getlocal(2);
               var1.getlocal(18).__setitem__(var1.getlocal(15), var12);
               var5 = null;
            }

            var1.setline(786);
            var12 = var1.getlocal(15)._add(Py.newInteger(1));
            var1.setlocal(15, var12);
            var5 = null;
         }
      }
   }

   public PyObject literal$34(PyFrame var1, ThreadState var2) {
      var1.setline(710);
      PyObject var10000 = var1.getlocal(1);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(var1.getglobal("LITERAL"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(711);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1))._add(var1.getlocal(0))});
         var1.getlocal(1).__setitem__((PyObject)Py.newInteger(-1), var4);
         var3 = null;
      } else {
         var1.setline(713);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("LITERAL"), var1.getlocal(0)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject expand_template$35(PyFrame var1, ThreadState var2) {
      var1.setline(790);
      PyObject var3 = var1.getlocal(1).__getattr__("group");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(791);
      var3 = var1.getlocal(1).__getattr__("string").__getslice__((PyObject)null, Py.newInteger(0), (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(792);
      var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(793);
      var3 = var1.getlocal(5).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(5, var3);
      var3 = null;

      try {
         var1.setline(795);
         var3 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(795);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               break;
            }

            PyObject[] var10 = Py.unpackSequence(var8, 2);
            PyObject var6 = var10[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var10[1];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(796);
            var5 = var1.getlocal(2).__call__(var2, var1.getlocal(7));
            var1.getlocal(5).__setitem__(var1.getlocal(6), var5);
            var1.setlocal(8, var5);
            var1.setline(797);
            var5 = var1.getlocal(8);
            PyObject var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(798);
               throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("unmatched group"));
            }
         }
      } catch (Throwable var7) {
         PyException var9 = Py.setException(var7, var1);
         if (var9.match(var1.getglobal("IndexError"))) {
            var1.setline(800);
            throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("invalid group reference"));
         }

         throw var9;
      }

      var1.setline(801);
      var3 = var1.getlocal(3).__getattr__("join").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return var3;
   }

   public sre_parse$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Pattern$1 = Py.newCode(0, var2, var1, "Pattern", 66, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 68, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "gid", "ogid"};
      opengroup$3 = Py.newCode(2, var2, var1, "opengroup", 73, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "gid"};
      closegroup$4 = Py.newCode(2, var2, var1, "closegroup", 84, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "gid"};
      checkgroup$5 = Py.newCode(2, var2, var1, "checkgroup", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SubPattern$6 = Py.newCode(0, var2, var1, "SubPattern", 89, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pattern", "data"};
      __init__$7 = Py.newCode(3, var2, var1, "__init__", 91, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "nl", "seqtypes", "op", "av", "a", "i"};
      dump$8 = Py.newCode(2, var2, var1, "dump", 97, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$9 = Py.newCode(1, var2, var1, "__repr__", 125, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$10 = Py.newCode(1, var2, var1, "__len__", 127, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __delitem__$11 = Py.newCode(2, var2, var1, "__delitem__", 129, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$12 = Py.newCode(2, var2, var1, "__getitem__", 131, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "code"};
      __setitem__$13 = Py.newCode(3, var2, var1, "__setitem__", 135, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "code"};
      insert$14 = Py.newCode(3, var2, var1, "insert", 137, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code"};
      append$15 = Py.newCode(2, var2, var1, "append", 139, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lo", "hi", "UNITCODES", "REPEATCODES", "op", "av", "i", "j", "l", "h"};
      getwidth$16 = Py.newCode(1, var2, var1, "getwidth", 141, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Tokenizer$17 = Py.newCode(0, var2, var1, "Tokenizer", 178, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "string"};
      __init__$18 = Py.newCode(2, var2, var1, "__init__", 179, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "char", "c"};
      _Tokenizer__next$19 = Py.newCode(1, var2, var1, "_Tokenizer__next", 183, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "char", "skip"};
      match$20 = Py.newCode(3, var2, var1, "match", 196, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "this"};
      get$21 = Py.newCode(1, var2, var1, "get", 202, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$22 = Py.newCode(1, var2, var1, "tell", 206, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      seek$23 = Py.newCode(2, var2, var1, "seek", 208, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"char"};
      isident$24 = Py.newCode(1, var2, var1, "isident", 211, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"char"};
      isdigit$25 = Py.newCode(1, var2, var1, "isdigit", 214, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "char"};
      isname$26 = Py.newCode(1, var2, var1, "isname", 217, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "escape", "code", "c"};
      _class_escape$27 = Py.newCode(2, var2, var1, "_class_escape", 226, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "escape", "state", "code", "c", "group"};
      _escape$28 = Py.newCode(3, var2, var1, "_escape", 258, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "state", "nested", "items", "itemsappend", "sourcematch", "subpattern", "subpatternappend", "prefix", "item", "set", "setappend"};
      _parse_sub$29 = Py.newCode(3, var2, var1, "_parse_sub", 302, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "state", "condgroup", "item_yes", "item_no", "subpattern"};
      _parse_sub_cond$30 = Py.newCode(3, var2, var1, "_parse_sub_cond", 361, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "state", "subpattern", "subpatternappend", "sourceget", "sourcematch", "_len", "PATTERNENDERS", "ASSERTCHARS", "LOOKBEHINDASSERTCHARS", "REPEATCODES", "this", "set", "setappend", "start", "code1", "code2", "lo", "hi", "min", "max", "here", "item", "group", "name", "condgroup", "char", "gid", "dir", "p", "condname", "code"};
      _parse$31 = Py.newCode(2, var2, var1, "_parse", 380, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "flags", "pattern", "source", "p", "tail"};
      parse$32 = Py.newCode(3, var2, var1, "parse", 674, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "pattern", "s", "sget", "p", "a", "literal", "sep", "makechar", "this", "c", "name", "char", "index", "isoctal", "i", "groups", "groupsappend", "literals"};
      parse_template$33 = Py.newCode(2, var2, var1, "parse_template", 702, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"literal", "p", "pappend"};
      literal$34 = Py.newCode(3, var2, var1, "literal", 709, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"template", "match", "g", "sep", "groups", "literals", "index", "group", "s"};
      expand_template$35 = Py.newCode(2, var2, var1, "expand_template", 789, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sre_parse$py("sre_parse$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sre_parse$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Pattern$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.opengroup$3(var2, var3);
         case 4:
            return this.closegroup$4(var2, var3);
         case 5:
            return this.checkgroup$5(var2, var3);
         case 6:
            return this.SubPattern$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.dump$8(var2, var3);
         case 9:
            return this.__repr__$9(var2, var3);
         case 10:
            return this.__len__$10(var2, var3);
         case 11:
            return this.__delitem__$11(var2, var3);
         case 12:
            return this.__getitem__$12(var2, var3);
         case 13:
            return this.__setitem__$13(var2, var3);
         case 14:
            return this.insert$14(var2, var3);
         case 15:
            return this.append$15(var2, var3);
         case 16:
            return this.getwidth$16(var2, var3);
         case 17:
            return this.Tokenizer$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this._Tokenizer__next$19(var2, var3);
         case 20:
            return this.match$20(var2, var3);
         case 21:
            return this.get$21(var2, var3);
         case 22:
            return this.tell$22(var2, var3);
         case 23:
            return this.seek$23(var2, var3);
         case 24:
            return this.isident$24(var2, var3);
         case 25:
            return this.isdigit$25(var2, var3);
         case 26:
            return this.isname$26(var2, var3);
         case 27:
            return this._class_escape$27(var2, var3);
         case 28:
            return this._escape$28(var2, var3);
         case 29:
            return this._parse_sub$29(var2, var3);
         case 30:
            return this._parse_sub_cond$30(var2, var3);
         case 31:
            return this._parse$31(var2, var3);
         case 32:
            return this.parse$32(var2, var3);
         case 33:
            return this.parse_template$33(var2, var3);
         case 34:
            return this.literal$34(var2, var3);
         case 35:
            return this.expand_template$35(var2, var3);
         default:
            return null;
      }
   }
}
