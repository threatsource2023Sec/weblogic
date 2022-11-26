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
@Filename("sgmllib.py")
public class sgmllib$py extends PyFunctionTable implements PyRunnable {
   static sgmllib$py self;
   static final PyCode f$0;
   static final PyCode SGMLParseError$1;
   static final PyCode SGMLParser$2;
   static final PyCode __init__$3;
   static final PyCode reset$4;
   static final PyCode setnomoretags$5;
   static final PyCode setliteral$6;
   static final PyCode feed$7;
   static final PyCode close$8;
   static final PyCode error$9;
   static final PyCode goahead$10;
   static final PyCode parse_pi$11;
   static final PyCode get_starttag_text$12;
   static final PyCode parse_starttag$13;
   static final PyCode _convert_ref$14;
   static final PyCode parse_endtag$15;
   static final PyCode finish_shorttag$16;
   static final PyCode finish_starttag$17;
   static final PyCode finish_endtag$18;
   static final PyCode handle_starttag$19;
   static final PyCode handle_endtag$20;
   static final PyCode report_unbalanced$21;
   static final PyCode convert_charref$22;
   static final PyCode convert_codepoint$23;
   static final PyCode handle_charref$24;
   static final PyCode convert_entityref$25;
   static final PyCode handle_entityref$26;
   static final PyCode handle_data$27;
   static final PyCode handle_comment$28;
   static final PyCode handle_decl$29;
   static final PyCode handle_pi$30;
   static final PyCode unknown_starttag$31;
   static final PyCode unknown_endtag$32;
   static final PyCode unknown_charref$33;
   static final PyCode unknown_entityref$34;
   static final PyCode TestSGMLParser$35;
   static final PyCode __init__$36;
   static final PyCode handle_data$37;
   static final PyCode flush$38;
   static final PyCode handle_comment$39;
   static final PyCode unknown_starttag$40;
   static final PyCode unknown_endtag$41;
   static final PyCode unknown_entityref$42;
   static final PyCode unknown_charref$43;
   static final PyCode unknown_decl$44;
   static final PyCode close$45;
   static final PyCode test$46;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A parser for SGML, using the derived class as a static DTD."));
      var1.setline(1);
      PyString.fromInterned("A parser for SGML, using the derived class as a static DTD.");
      var1.setline(12);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(13);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the sgmllib module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(15);
      var1.dellocal("warnpy3k");
      var1.setline(17);
      PyObject var6 = imp.importOne("markupbase", var1, -1);
      var1.setlocal("markupbase", var6);
      var3 = null;
      var1.setline(18);
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(20);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("SGMLParser"), PyString.fromInterned("SGMLParseError")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(24);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[&<]"));
      var1.setlocal("interesting", var6);
      var3 = null;
      var1.setline(25);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&([a-zA-Z][a-zA-Z0-9]*|#[0-9]*)?|<([a-zA-Z][^<>]*|/([a-zA-Z][^<>]*)?|![^<>]*)?"));
      var1.setlocal("incomplete", var6);
      var3 = null;
      var1.setline(30);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&([a-zA-Z][-.a-zA-Z0-9]*)[^a-zA-Z0-9]"));
      var1.setlocal("entityref", var6);
      var3 = null;
      var1.setline(31);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&#([0-9]+)[^0-9]"));
      var1.setlocal("charref", var6);
      var3 = null;
      var1.setline(33);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<[>a-zA-Z]"));
      var1.setlocal("starttagopen", var6);
      var3 = null;
      var1.setline(34);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<[a-zA-Z][-.a-zA-Z0-9]*/"));
      var1.setlocal("shorttagopen", var6);
      var3 = null;
      var1.setline(35);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<([a-zA-Z][-.a-zA-Z0-9]*)/([^/]*)/"));
      var1.setlocal("shorttag", var6);
      var3 = null;
      var1.setline(36);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
      var1.setlocal("piclose", var6);
      var3 = null;
      var1.setline(37);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[<>]"));
      var1.setlocal("endbracket", var6);
      var3 = null;
      var1.setline(38);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[a-zA-Z][-_.a-zA-Z0-9]*"));
      var1.setlocal("tagfind", var6);
      var3 = null;
      var1.setline(39);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s*([a-zA-Z_][-:.a-zA-Z_0-9]*)(\\s*=\\s*(\\'[^\\']*\\'|\"[^\"]*\"|[][\\-a-zA-Z0-9./,:;+*%?!&$\\(\\)_#=~\\'\"@]*))?"));
      var1.setlocal("attrfind", var6);
      var3 = null;
      var1.setline(44);
      var5 = new PyObject[]{var1.getname("RuntimeError")};
      var4 = Py.makeClass("SGMLParseError", var5, SGMLParseError$1);
      var1.setlocal("SGMLParseError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(60);
      var5 = new PyObject[]{var1.getname("markupbase").__getattr__("ParserBase")};
      var4 = Py.makeClass("SGMLParser", var5, SGMLParser$2);
      var1.setlocal("SGMLParser", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(461);
      var5 = new PyObject[]{var1.getname("SGMLParser")};
      var4 = Py.makeClass("TestSGMLParser", var5, TestSGMLParser$35);
      var1.setlocal("TestSGMLParser", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(516);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var5, test$46, (PyObject)null);
      var1.setlocal("test", var9);
      var3 = null;
      var1.setline(552);
      var6 = var1.getname("__name__");
      var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(553);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SGMLParseError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised for all parse errors."));
      var1.setline(45);
      PyString.fromInterned("Exception raised for all parse errors.");
      var1.setline(46);
      return var1.getf_locals();
   }

   public PyObject SGMLParser$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(62);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&(?:([a-zA-Z][-.a-zA-Z0-9]*)|#([0-9]+))(;?)"));
      var1.setlocal("entity_or_charref", var3);
      var3 = null;
      var1.setline(66);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$3, PyString.fromInterned("Initialize and reset this instance."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(71);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, reset$4, PyString.fromInterned("Reset this instance. Loses all unprocessed data."));
      var1.setlocal("reset", var5);
      var3 = null;
      var1.setline(81);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setnomoretags$5, PyString.fromInterned("Enter literal mode (CDATA) till EOF.\n\n        Intended for derived classes only.\n        "));
      var1.setlocal("setnomoretags", var5);
      var3 = null;
      var1.setline(88);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setliteral$6, PyString.fromInterned("Enter literal mode (CDATA).\n\n        Intended for derived classes only.\n        "));
      var1.setlocal("setliteral", var5);
      var3 = null;
      var1.setline(95);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, feed$7, PyString.fromInterned("Feed some data to the parser.\n\n        Call this as often as you want, with as little or as much text\n        as you want (may include '\n').  (This just saves the text,\n        all the processing is done by goahead().)\n        "));
      var1.setlocal("feed", var5);
      var3 = null;
      var1.setline(106);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$8, PyString.fromInterned("Handle the remaining data."));
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(110);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, error$9, (PyObject)null);
      var1.setlocal("error", var5);
      var3 = null;
      var1.setline(116);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, goahead$10, (PyObject)null);
      var1.setlocal("goahead", var5);
      var3 = null;
      var1.setline(219);
      PyString var6 = PyString.fromInterned("=");
      var1.setlocal("_decl_otherchars", var6);
      var3 = null;
      var1.setline(222);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_pi$11, (PyObject)null);
      var1.setlocal("parse_pi", var5);
      var3 = null;
      var1.setline(234);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_starttag_text$12, (PyObject)null);
      var1.setlocal("get_starttag_text", var5);
      var3 = null;
      var1.setline(238);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_starttag$13, (PyObject)null);
      var1.setlocal("parse_starttag", var5);
      var3 = null;
      var1.setline(300);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _convert_ref$14, (PyObject)null);
      var1.setlocal("_convert_ref", var5);
      var3 = null;
      var1.setline(311);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_endtag$15, (PyObject)null);
      var1.setlocal("parse_endtag", var5);
      var3 = null;
      var1.setline(324);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finish_shorttag$16, (PyObject)null);
      var1.setlocal("finish_shorttag", var5);
      var3 = null;
      var1.setline(331);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finish_starttag$17, (PyObject)null);
      var1.setlocal("finish_starttag", var5);
      var3 = null;
      var1.setline(349);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finish_endtag$18, (PyObject)null);
      var1.setlocal("finish_endtag", var5);
      var3 = null;
      var1.setline(380);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_starttag$19, (PyObject)null);
      var1.setlocal("handle_starttag", var5);
      var3 = null;
      var1.setline(384);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_endtag$20, (PyObject)null);
      var1.setlocal("handle_endtag", var5);
      var3 = null;
      var1.setline(388);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, report_unbalanced$21, (PyObject)null);
      var1.setlocal("report_unbalanced", var5);
      var3 = null;
      var1.setline(393);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, convert_charref$22, PyString.fromInterned("Convert character reference, may be overridden."));
      var1.setlocal("convert_charref", var5);
      var3 = null;
      var1.setline(403);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, convert_codepoint$23, (PyObject)null);
      var1.setlocal("convert_codepoint", var5);
      var3 = null;
      var1.setline(406);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_charref$24, PyString.fromInterned("Handle character reference, no need to override."));
      var1.setlocal("handle_charref", var5);
      var3 = null;
      var1.setline(415);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("lt"), PyString.fromInterned("<"), PyString.fromInterned("gt"), PyString.fromInterned(">"), PyString.fromInterned("amp"), PyString.fromInterned("&"), PyString.fromInterned("quot"), PyString.fromInterned("\""), PyString.fromInterned("apos"), PyString.fromInterned("'")});
      var1.setlocal("entitydefs", var7);
      var3 = null;
      var1.setline(418);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, convert_entityref$25, PyString.fromInterned("Convert entity references.\n\n        As an alternative to overriding this method; one can tailor the\n        results by setting up the self.entitydefs mapping appropriately.\n        "));
      var1.setlocal("convert_entityref", var5);
      var3 = null;
      var1.setline(430);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_entityref$26, PyString.fromInterned("Handle entity references, no need to override."));
      var1.setlocal("handle_entityref", var5);
      var3 = null;
      var1.setline(439);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_data$27, (PyObject)null);
      var1.setlocal("handle_data", var5);
      var3 = null;
      var1.setline(443);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_comment$28, (PyObject)null);
      var1.setlocal("handle_comment", var5);
      var3 = null;
      var1.setline(447);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_decl$29, (PyObject)null);
      var1.setlocal("handle_decl", var5);
      var3 = null;
      var1.setline(451);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_pi$30, (PyObject)null);
      var1.setlocal("handle_pi", var5);
      var3 = null;
      var1.setline(455);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unknown_starttag$31, (PyObject)null);
      var1.setlocal("unknown_starttag", var5);
      var3 = null;
      var1.setline(456);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unknown_endtag$32, (PyObject)null);
      var1.setlocal("unknown_endtag", var5);
      var3 = null;
      var1.setline(457);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unknown_charref$33, (PyObject)null);
      var1.setlocal("unknown_charref", var5);
      var3 = null;
      var1.setline(458);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unknown_entityref$34, (PyObject)null);
      var1.setlocal("unknown_entityref", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Initialize and reset this instance.");
      var1.setline(68);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("verbose", var3);
      var3 = null;
      var1.setline(69);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$4(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Reset this instance. Loses all unprocessed data.");
      var1.setline(73);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_SGMLParser__starttag_text", var3);
      var3 = null;
      var1.setline(74);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"rawdata", var4);
      var3 = null;
      var1.setline(75);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var5);
      var3 = null;
      var1.setline(76);
      var4 = PyString.fromInterned("???");
      var1.getlocal(0).__setattr__((String)"lasttag", var4);
      var3 = null;
      var1.setline(77);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"nomoretags", var6);
      var3 = null;
      var1.setline(78);
      var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"literal", var6);
      var3 = null;
      var1.setline(79);
      var1.getglobal("markupbase").__getattr__("ParserBase").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setnomoretags$5(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Enter literal mode (CDATA) till EOF.\n\n        Intended for derived classes only.\n        ");
      var1.setline(86);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"nomoretags", var3);
      var1.getlocal(0).__setattr__((String)"literal", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setliteral$6(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("Enter literal mode (CDATA).\n\n        Intended for derived classes only.\n        ");
      var1.setline(93);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"literal", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$7(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("Feed some data to the parser.\n\n        Call this as often as you want, with as little or as much text\n        as you want (may include '\n').  (This just saves the text,\n        all the processing is done by goahead().)\n        ");
      var1.setline(103);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("rawdata", var3);
      var3 = null;
      var1.setline(104);
      var1.getlocal(0).__getattr__("goahead").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$8(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Handle the remaining data.");
      var1.setline(108);
      var1.getlocal(0).__getattr__("goahead").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$9(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      throw Py.makeException(var1.getglobal("SGMLParseError").__call__(var2, var1.getlocal(1)));
   }

   public PyObject goahead$10(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(118);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(119);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(120);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(121);
         if (var1.getlocal(0).__getattr__("nomoretags").__nonzero__()) {
            var1.setline(122);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(4), (PyObject)null));
            var1.setline(123);
            var3 = var1.getlocal(4);
            var1.setlocal(3, var3);
            var3 = null;
            break;
         }

         var1.setline(125);
         var3 = var1.getglobal("interesting").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(126);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(126);
            var3 = var1.getlocal(5).__getattr__("start").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(127);
            var3 = var1.getlocal(4);
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(128);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(6));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(129);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(6), (PyObject)null));
         }

         var1.setline(130);
         var3 = var1.getlocal(6);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(131);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(132);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var10000 = var3._eq(PyString.fromInterned("<"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(133);
            if (var1.getglobal("starttagopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
               var1.setline(134);
               if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                  var1.setline(135);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3)));
                  var1.setline(136);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
                  continue;
               }

               var1.setline(138);
               var3 = var1.getlocal(0).__getattr__("parse_starttag").__call__(var2, var1.getlocal(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(139);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(140);
               var3 = var1.getlocal(7);
               var1.setlocal(3, var3);
               var3 = null;
               continue;
            }

            var1.setline(142);
            if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</"), (PyObject)var1.getlocal(3)).__nonzero__()) {
               var1.setline(143);
               var3 = var1.getlocal(0).__getattr__("parse_endtag").__call__(var2, var1.getlocal(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(144);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(145);
               var3 = var1.getlocal(7);
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(146);
               var4 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"literal", var4);
               var3 = null;
               continue;
            }

            var1.setline(148);
            if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
               var1.setline(149);
               var3 = var1.getlocal(4);
               var10000 = var3._gt(var1.getlocal(3)._add(Py.newInteger(1)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(150);
               var1.getlocal(0).__getattr__("handle_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"));
               var1.setline(151);
               var3 = var1.getlocal(3)._add(Py.newInteger(1));
               var1.setlocal(3, var3);
               var3 = null;
               continue;
            }

            var1.setline(156);
            if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!--"), (PyObject)var1.getlocal(3)).__nonzero__()) {
               var1.setline(161);
               var3 = var1.getlocal(0).__getattr__("parse_comment").__call__(var2, var1.getlocal(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(162);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(163);
               var3 = var1.getlocal(7);
               var1.setlocal(3, var3);
               var3 = null;
               continue;
            }

            var1.setline(165);
            if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<?"), (PyObject)var1.getlocal(3)).__nonzero__()) {
               var1.setline(166);
               var3 = var1.getlocal(0).__getattr__("parse_pi").__call__(var2, var1.getlocal(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(167);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(168);
               var3 = var1.getlocal(3)._add(var1.getlocal(7));
               var1.setlocal(3, var3);
               var3 = null;
               continue;
            }

            var1.setline(170);
            if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!"), (PyObject)var1.getlocal(3)).__nonzero__()) {
               var1.setline(174);
               var3 = var1.getlocal(0).__getattr__("parse_declaration").__call__(var2, var1.getlocal(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(175);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(176);
               var3 = var1.getlocal(7);
               var1.setlocal(3, var3);
               var3 = null;
               continue;
            }
         } else {
            var1.setline(178);
            var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
            var10000 = var3._eq(PyString.fromInterned("&"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(179);
               if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                  var1.setline(180);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3)));
                  var1.setline(181);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
                  continue;
               }

               var1.setline(183);
               var3 = var1.getglobal("charref").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(184);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(185);
                  var3 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(186);
                  var1.getlocal(0).__getattr__("handle_charref").__call__(var2, var1.getlocal(8));
                  var1.setline(187);
                  var3 = var1.getlocal(5).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(188);
                  var3 = var1.getlocal(2).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                  var10000 = var3._ne(PyString.fromInterned(";"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(188);
                     var3 = var1.getlocal(3)._sub(Py.newInteger(1));
                     var1.setlocal(3, var3);
                     var3 = null;
                  }
                  continue;
               }

               var1.setline(190);
               var3 = var1.getglobal("entityref").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(191);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(192);
                  var3 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(193);
                  var1.getlocal(0).__getattr__("handle_entityref").__call__(var2, var1.getlocal(8));
                  var1.setline(194);
                  var3 = var1.getlocal(5).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(195);
                  var3 = var1.getlocal(2).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                  var10000 = var3._ne(PyString.fromInterned(";"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(195);
                     var3 = var1.getlocal(3)._sub(Py.newInteger(1));
                     var1.setlocal(3, var3);
                     var3 = null;
                  }
                  continue;
               }
            } else {
               var1.setline(198);
               var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("neither < nor & ??"));
            }
         }

         var1.setline(201);
         var3 = var1.getglobal("incomplete").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(202);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(203);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3)));
            var1.setline(204);
            var3 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(206);
            var3 = var1.getlocal(5).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(207);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(var1.getlocal(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(209);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(6), (PyObject)null));
            var1.setline(210);
            var3 = var1.getlocal(6);
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(212);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(4));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(213);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(4), (PyObject)null));
         var1.setline(214);
         var3 = var1.getlocal(4);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(215);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("rawdata", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_pi$11(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(224);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(2)), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("<?"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(225);
         var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_pi()"));
      }

      var1.setline(226);
      var3 = var1.getglobal("piclose").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(227);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(228);
         PyInteger var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(229);
         PyObject var4 = var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(230);
         var1.getlocal(0).__getattr__("handle_pi").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(4), (PyObject)null));
         var1.setline(231);
         var4 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(232);
         var3 = var1.getlocal(4)._sub(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_starttag_text$12(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyObject var3 = var1.getlocal(0).__getattr__("_SGMLParser__starttag_text");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_starttag$13(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_SGMLParser__starttag_text", var3);
      var3 = null;
      var1.setline(240);
      var3 = var1.getlocal(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(241);
      var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(242);
      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      PyInteger var7;
      if (var1.getglobal("shorttagopen").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(1)).__nonzero__()) {
         var1.setline(247);
         var3 = var1.getglobal("shorttag").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(248);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(249);
            var7 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(250);
            var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
            var1.setline(251);
            var4 = PyString.fromInterned("<%s/")._mod(var1.getlocal(5));
            var1.getlocal(0).__setattr__("_SGMLParser__starttag_text", var4);
            var4 = null;
            var1.setline(252);
            var4 = var1.getlocal(5).__getattr__("lower").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(253);
            var4 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(254);
            var1.getlocal(0).__getattr__("finish_shorttag").__call__(var2, var1.getlocal(5), var1.getlocal(6));
            var1.setline(255);
            var4 = var1.getlocal(3).__getslice__(var1.getlocal(2), var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))._add(Py.newInteger(1)), (PyObject)null);
            var1.getlocal(0).__setattr__("_SGMLParser__starttag_text", var4);
            var4 = null;
            var1.setline(256);
            var3 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(261);
         var4 = var1.getglobal("endbracket").__getattr__("search").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(Py.newInteger(1)));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(262);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(263);
            var7 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(264);
            var4 = var1.getlocal(4).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(266);
            PyList var8 = new PyList(Py.EmptyObjects);
            var1.setlocal(9, var8);
            var4 = null;
            var1.setline(267);
            var4 = var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(2)), (PyObject)null);
            PyObject var10000 = var4._eq(PyString.fromInterned("<>"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(269);
               var4 = var1.getlocal(8);
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(270);
               var4 = var1.getlocal(0).__getattr__("lasttag");
               var1.setlocal(5, var4);
               var4 = null;
            } else {
               var1.setline(272);
               var4 = var1.getglobal("tagfind").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(Py.newInteger(1)));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(273);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  var1.setline(274);
                  var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_starttag"));
               }

               var1.setline(275);
               var4 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(276);
               var4 = var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), var1.getlocal(7), (PyObject)null).__getattr__("lower").__call__(var2);
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(277);
               var4 = var1.getlocal(5);
               var1.getlocal(0).__setattr__("lasttag", var4);
               var4 = null;
            }

            while(true) {
               var1.setline(278);
               var4 = var1.getlocal(7);
               var10000 = var4._lt(var1.getlocal(8));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(279);
               var4 = var1.getglobal("attrfind").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(7));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(280);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(281);
               var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)Py.newInteger(2), (PyObject)Py.newInteger(3));
               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(11, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(12, var6);
               var6 = null;
               var4 = null;
               var1.setline(282);
               if (var1.getlocal(11).__not__().__nonzero__()) {
                  var1.setline(283);
                  var4 = var1.getlocal(10);
                  var1.setlocal(12, var4);
                  var4 = null;
               } else {
                  var1.setline(285);
                  var4 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                  PyString var10001 = PyString.fromInterned("'");
                  var10000 = var4;
                  PyString var10 = var10001;
                  PyObject var9;
                  if ((var9 = var10000._eq(var10001)).__nonzero__()) {
                     var9 = var10._eq(var1.getlocal(12).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
                  }

                  var10000 = var9;
                  var4 = null;
                  if (!var9.__nonzero__()) {
                     var4 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                     var10001 = PyString.fromInterned("\"");
                     var10000 = var4;
                     var10 = var10001;
                     if ((var9 = var10000._eq(var10001)).__nonzero__()) {
                        var9 = var10._eq(var1.getlocal(12).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
                     }

                     var10000 = var9;
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(288);
                     var4 = var1.getlocal(12).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(12, var4);
                     var4 = null;
                  }

                  var1.setline(289);
                  var4 = var1.getlocal(0).__getattr__("entity_or_charref").__getattr__("sub").__call__(var2, var1.getlocal(0).__getattr__("_convert_ref"), var1.getlocal(12));
                  var1.setlocal(12, var4);
                  var4 = null;
               }

               var1.setline(291);
               var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10).__getattr__("lower").__call__(var2), var1.getlocal(12)})));
               var1.setline(292);
               var4 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setlocal(7, var4);
               var4 = null;
            }

            var1.setline(293);
            var4 = var1.getlocal(3).__getitem__(var1.getlocal(8));
            var10000 = var4._eq(PyString.fromInterned(">"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(294);
               var4 = var1.getlocal(8)._add(Py.newInteger(1));
               var1.setlocal(8, var4);
               var4 = null;
            }

            var1.setline(295);
            var4 = var1.getlocal(3).__getslice__(var1.getlocal(2), var1.getlocal(8), (PyObject)null);
            var1.getlocal(0).__setattr__("_SGMLParser__starttag_text", var4);
            var4 = null;
            var1.setline(296);
            var1.getlocal(0).__getattr__("finish_starttag").__call__(var2, var1.getlocal(5), var1.getlocal(9));
            var1.setline(297);
            var3 = var1.getlocal(8);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _convert_ref$14(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)).__nonzero__()) {
         var1.setline(302);
         var10000 = var1.getlocal(0).__getattr__("convert_charref").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)));
         if (!var10000.__nonzero__()) {
            var10000 = PyString.fromInterned("&#%s%s")._mod(var1.getlocal(1).__getattr__("groups").__call__(var2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(304);
         if (var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(3)).__nonzero__()) {
            var1.setline(305);
            var10000 = var1.getlocal(0).__getattr__("convert_entityref").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            if (!var10000.__nonzero__()) {
               var10000 = PyString.fromInterned("&%s;")._mod(var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(308);
            var3 = PyString.fromInterned("&%s")._mod(var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject parse_endtag$15(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(313);
      var3 = var1.getglobal("endbracket").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(314);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(315);
         PyInteger var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(316);
         PyObject var4 = var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(317);
         var4 = var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(4), (PyObject)null).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(318);
         var4 = var1.getlocal(2).__getitem__(var1.getlocal(4));
         PyObject var10000 = var4._eq(PyString.fromInterned(">"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(319);
            var4 = var1.getlocal(4)._add(Py.newInteger(1));
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(320);
         var1.getlocal(0).__getattr__("finish_endtag").__call__(var2, var1.getlocal(5));
         var1.setline(321);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject finish_shorttag$16(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      var1.getlocal(0).__getattr__("finish_starttag").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(326);
      var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2));
      var1.setline(327);
      var1.getlocal(0).__getattr__("finish_endtag").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish_starttag$17(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyInteger var5;
      try {
         var1.setline(333);
         PyObject var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("start_")._add(var1.getlocal(1)));
         var1.setlocal(3, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            PyException var4;
            try {
               var1.setline(336);
               PyObject var9 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("do_")._add(var1.getlocal(1)));
               var1.setlocal(3, var9);
               var4 = null;
            } catch (Throwable var6) {
               var4 = Py.setException(var6, var1);
               if (var4.match(var1.getglobal("AttributeError"))) {
                  var1.setline(338);
                  var1.getlocal(0).__getattr__("unknown_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                  var1.setline(339);
                  var5 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var5;
               }

               throw var4;
            }

            var1.setline(341);
            var1.getlocal(0).__getattr__("handle_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(2));
            var1.setline(342);
            var5 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var5;
         }

         throw var3;
      }

      var1.setline(344);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(345);
      var1.getlocal(0).__getattr__("handle_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(2));
      var1.setline(346);
      var5 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject finish_endtag$18(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyObject var10000;
      PyObject var3;
      PyObject var4;
      PyException var8;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(351);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"))._sub(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(352);
         var3 = var1.getlocal(2);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(353);
            var1.getlocal(0).__getattr__("unknown_endtag").__call__(var2, var1.getlocal(1));
            var1.setline(354);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(356);
         var3 = var1.getlocal(1);
         var10000 = var3._notin(var1.getlocal(0).__getattr__("stack"));
         var3 = null;
         if (var10000.__nonzero__()) {
            label45: {
               try {
                  var1.setline(358);
                  var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("end_")._add(var1.getlocal(1)));
                  var1.setlocal(3, var3);
                  var3 = null;
               } catch (Throwable var6) {
                  var8 = Py.setException(var6, var1);
                  if (var8.match(var1.getglobal("AttributeError"))) {
                     var1.setline(360);
                     var1.getlocal(0).__getattr__("unknown_endtag").__call__(var2, var1.getlocal(1));
                     break label45;
                  }

                  throw var8;
               }

               var1.setline(362);
               var1.getlocal(0).__getattr__("report_unbalanced").__call__(var2, var1.getlocal(1));
            }

            var1.setline(363);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(364);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(365);
         var3 = var1.getglobal("range").__call__(var2, var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(365);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(366);
            PyObject var5 = var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(4));
            var10000 = var5._eq(var1.getlocal(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(366);
               var5 = var1.getlocal(4);
               var1.setlocal(2, var5);
               var5 = null;
            }
         }
      }

      while(true) {
         var1.setline(367);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"));
         var10000 = var3._gt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(368);
         var3 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1));
         var1.setlocal(1, var3);
         var3 = null;

         try {
            var1.setline(370);
            var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("end_")._add(var1.getlocal(1)));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("AttributeError"))) {
               throw var8;
            }

            var1.setline(372);
            var4 = var1.getglobal("None");
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(373);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(374);
            var1.getlocal(0).__getattr__("handle_endtag").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         } else {
            var1.setline(376);
            var1.getlocal(0).__getattr__("unknown_endtag").__call__(var2, var1.getlocal(1));
         }

         var1.setline(377);
         var1.getlocal(0).__getattr__("stack").__delitem__((PyObject)Py.newInteger(-1));
      }
   }

   public PyObject handle_starttag$19(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      var1.getlocal(2).__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_endtag$20(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      var1.getlocal(2).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject report_unbalanced$21(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      if (var1.getlocal(0).__getattr__("verbose").__nonzero__()) {
         var1.setline(390);
         Py.println(PyString.fromInterned("*** Unbalanced </")._add(var1.getlocal(1))._add(PyString.fromInterned(">")));
         var1.setline(391);
         Py.printComma(PyString.fromInterned("*** Stack:"));
         Py.println(var1.getlocal(0).__getattr__("stack"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject convert_charref$22(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      PyString.fromInterned("Convert character reference, may be overridden.");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(396);
         var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("ValueError"))) {
            var1.setline(398);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(399);
      PyInteger var7 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var10000 = var7;
      var6 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(127));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(400);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(401);
         var6 = var1.getlocal(0).__getattr__("convert_codepoint").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject convert_codepoint$23(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyObject var3 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handle_charref$24(PyFrame var1, ThreadState var2) {
      var1.setline(407);
      PyString.fromInterned("Handle character reference, no need to override.");
      var1.setline(408);
      PyObject var3 = var1.getlocal(0).__getattr__("convert_charref").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(409);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(410);
         var1.getlocal(0).__getattr__("unknown_charref").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(412);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject convert_entityref$25(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyString.fromInterned("Convert entity references.\n\n        As an alternative to overriding this method; one can tailor the\n        results by setting up the self.entitydefs mapping appropriately.\n        ");
      var1.setline(424);
      PyObject var3 = var1.getlocal(0).__getattr__("entitydefs");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(425);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(426);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(428);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject handle_entityref$26(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyString.fromInterned("Handle entity references, no need to override.");
      var1.setline(432);
      PyObject var3 = var1.getlocal(0).__getattr__("convert_entityref").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(433);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(434);
         var1.getlocal(0).__getattr__("unknown_entityref").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(436);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_data$27(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_comment$28(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_decl$29(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_pi$30(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_starttag$31(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_endtag$32(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_charref$33(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_entityref$34(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestSGMLParser$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(463);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$36, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_data$37, (PyObject)null);
      var1.setlocal("handle_data", var4);
      var3 = null;
      var1.setline(472);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$38, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(478);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_comment$39, (PyObject)null);
      var1.setlocal("handle_comment", var4);
      var3 = null;
      var1.setline(485);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_starttag$40, (PyObject)null);
      var1.setlocal("unknown_starttag", var4);
      var3 = null;
      var1.setline(495);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_endtag$41, (PyObject)null);
      var1.setlocal("unknown_endtag", var4);
      var3 = null;
      var1.setline(499);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_entityref$42, (PyObject)null);
      var1.setlocal("unknown_entityref", var4);
      var3 = null;
      var1.setline(503);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_charref$43, (PyObject)null);
      var1.setlocal("unknown_charref", var4);
      var3 = null;
      var1.setline(507);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_decl$44, (PyObject)null);
      var1.setlocal("unknown_decl", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$45, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$36(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"testdata", var3);
      var3 = null;
      var1.setline(465);
      var1.getglobal("SGMLParser").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_data$37(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyObject var3 = var1.getlocal(0).__getattr__("testdata")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("testdata", var3);
      var3 = null;
      var1.setline(469);
      var3 = var1.getglobal("len").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("testdata")));
      PyObject var10000 = var3._ge(Py.newInteger(70));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(470);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$38(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyObject var3 = var1.getlocal(0).__getattr__("testdata");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(474);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(475);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"testdata", var4);
         var3 = null;
         var1.setline(476);
         Py.printComma(PyString.fromInterned("data:"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_comment$39(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(480);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(481);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(68));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(482);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(32), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(2).__getslice__(Py.newInteger(-32), (PyObject)null, (PyObject)null));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(483);
      Py.printComma(PyString.fromInterned("comment:"));
      Py.println(var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_starttag$40(PyFrame var1, ThreadState var2) {
      var1.setline(486);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(487);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(488);
         Py.println(PyString.fromInterned("start tag: <")._add(var1.getlocal(1))._add(PyString.fromInterned(">")));
      } else {
         var1.setline(490);
         Py.printComma(PyString.fromInterned("start tag: <")._add(var1.getlocal(1)));
         var1.setline(491);
         PyObject var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(491);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(493);
               Py.println(PyString.fromInterned(">"));
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(492);
            Py.printComma(var1.getlocal(3)._add(PyString.fromInterned("="))._add(PyString.fromInterned("\""))._add(var1.getlocal(4))._add(PyString.fromInterned("\"")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_endtag$41(PyFrame var1, ThreadState var2) {
      var1.setline(496);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(497);
      Py.println(PyString.fromInterned("end tag: </")._add(var1.getlocal(1))._add(PyString.fromInterned(">")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_entityref$42(PyFrame var1, ThreadState var2) {
      var1.setline(500);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(501);
      Py.println(PyString.fromInterned("*** unknown entity ref: &")._add(var1.getlocal(1))._add(PyString.fromInterned(";")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_charref$43(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(505);
      Py.println(PyString.fromInterned("*** unknown char ref: &#")._add(var1.getlocal(1))._add(PyString.fromInterned(";")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_decl$44(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(509);
      Py.println(PyString.fromInterned("*** unknown decl: [")._add(var1.getlocal(1))._add(PyString.fromInterned("]")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$45(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      var1.getglobal("SGMLParser").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.setline(513);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$46(PyFrame var1, ThreadState var2) {
      var1.setline(517);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(519);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(520);
         var3 = var1.getlocal(1).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(522);
      var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("-s"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(523);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(524);
         var3 = var1.getglobal("SGMLParser");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(526);
         var3 = var1.getglobal("TestSGMLParser");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(528);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(529);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(531);
         PyString var6 = PyString.fromInterned("test.html");
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(533);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(PyString.fromInterned("-"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(534);
         var3 = var1.getlocal(1).__getattr__("stdin");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         try {
            var1.setline(537);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("r"));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("IOError"))) {
               throw var7;
            }

            var4 = var7.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(539);
            Py.printComma(var1.getlocal(3));
            Py.printComma(PyString.fromInterned(":"));
            Py.println(var1.getlocal(5));
            var1.setline(540);
            var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         }
      }

      var1.setline(542);
      var3 = var1.getlocal(4).__getattr__("read").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(543);
      var3 = var1.getlocal(4);
      var10000 = var3._isnot(var1.getlocal(1).__getattr__("stdin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(544);
         var1.getlocal(4).__getattr__("close").__call__(var2);
      }

      var1.setline(546);
      var3 = var1.getlocal(2).__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(547);
      var3 = var1.getlocal(6).__iter__();

      while(true) {
         var1.setline(547);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(549);
            var1.getlocal(7).__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(8, var4);
         var1.setline(548);
         var1.getlocal(7).__getattr__("feed").__call__(var2, var1.getlocal(8));
      }
   }

   public sgmllib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SGMLParseError$1 = Py.newCode(0, var2, var1, "SGMLParseError", 44, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SGMLParser$2 = Py.newCode(0, var2, var1, "SGMLParser", 60, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 66, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$4 = Py.newCode(1, var2, var1, "reset", 71, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setnomoretags$5 = Py.newCode(1, var2, var1, "setnomoretags", 81, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      setliteral$6 = Py.newCode(2, var2, var1, "setliteral", 88, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$7 = Py.newCode(2, var2, var1, "feed", 95, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$8 = Py.newCode(1, var2, var1, "close", 106, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      error$9 = Py.newCode(2, var2, var1, "error", 110, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "end", "rawdata", "i", "n", "match", "j", "k", "name"};
      goahead$10 = Py.newCode(2, var2, var1, "goahead", 116, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "match", "j"};
      parse_pi$11 = Py.newCode(2, var2, var1, "parse_pi", 222, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_starttag_text$12 = Py.newCode(1, var2, var1, "get_starttag_text", 234, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "start_pos", "rawdata", "match", "tag", "data", "k", "j", "attrs", "attrname", "rest", "attrvalue"};
      parse_starttag$13 = Py.newCode(2, var2, var1, "parse_starttag", 238, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "match"};
      _convert_ref$14 = Py.newCode(2, var2, var1, "_convert_ref", 300, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "match", "j", "tag"};
      parse_endtag$15 = Py.newCode(2, var2, var1, "parse_endtag", 311, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "data"};
      finish_shorttag$16 = Py.newCode(3, var2, var1, "finish_shorttag", 324, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs", "method"};
      finish_starttag$17 = Py.newCode(3, var2, var1, "finish_starttag", 331, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "found", "method", "i"};
      finish_endtag$18 = Py.newCode(2, var2, var1, "finish_endtag", 349, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "method", "attrs"};
      handle_starttag$19 = Py.newCode(4, var2, var1, "handle_starttag", 380, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "method"};
      handle_endtag$20 = Py.newCode(3, var2, var1, "handle_endtag", 384, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      report_unbalanced$21 = Py.newCode(2, var2, var1, "report_unbalanced", 388, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n"};
      convert_charref$22 = Py.newCode(2, var2, var1, "convert_charref", 393, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "codepoint"};
      convert_codepoint$23 = Py.newCode(2, var2, var1, "convert_codepoint", 403, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "replacement"};
      handle_charref$24 = Py.newCode(2, var2, var1, "handle_charref", 406, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "table"};
      convert_entityref$25 = Py.newCode(2, var2, var1, "convert_entityref", 418, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "replacement"};
      handle_entityref$26 = Py.newCode(2, var2, var1, "handle_entityref", 430, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$27 = Py.newCode(2, var2, var1, "handle_data", 439, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_comment$28 = Py.newCode(2, var2, var1, "handle_comment", 443, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "decl"};
      handle_decl$29 = Py.newCode(2, var2, var1, "handle_decl", 447, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_pi$30 = Py.newCode(2, var2, var1, "handle_pi", 451, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs"};
      unknown_starttag$31 = Py.newCode(3, var2, var1, "unknown_starttag", 455, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      unknown_endtag$32 = Py.newCode(2, var2, var1, "unknown_endtag", 456, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_charref$33 = Py.newCode(2, var2, var1, "unknown_charref", 457, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_entityref$34 = Py.newCode(2, var2, var1, "unknown_entityref", 458, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestSGMLParser$35 = Py.newCode(0, var2, var1, "TestSGMLParser", 461, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose"};
      __init__$36 = Py.newCode(2, var2, var1, "__init__", 463, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$37 = Py.newCode(2, var2, var1, "handle_data", 467, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      flush$38 = Py.newCode(1, var2, var1, "flush", 472, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "r"};
      handle_comment$39 = Py.newCode(2, var2, var1, "handle_comment", 478, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs", "name", "value"};
      unknown_starttag$40 = Py.newCode(3, var2, var1, "unknown_starttag", 485, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      unknown_endtag$41 = Py.newCode(2, var2, var1, "unknown_endtag", 495, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_entityref$42 = Py.newCode(2, var2, var1, "unknown_entityref", 499, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_charref$43 = Py.newCode(2, var2, var1, "unknown_charref", 503, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      unknown_decl$44 = Py.newCode(2, var2, var1, "unknown_decl", 507, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$45 = Py.newCode(1, var2, var1, "close", 511, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "sys", "klass", "file", "f", "msg", "data", "x", "c"};
      test$46 = Py.newCode(1, var2, var1, "test", 516, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sgmllib$py("sgmllib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sgmllib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SGMLParseError$1(var2, var3);
         case 2:
            return this.SGMLParser$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.reset$4(var2, var3);
         case 5:
            return this.setnomoretags$5(var2, var3);
         case 6:
            return this.setliteral$6(var2, var3);
         case 7:
            return this.feed$7(var2, var3);
         case 8:
            return this.close$8(var2, var3);
         case 9:
            return this.error$9(var2, var3);
         case 10:
            return this.goahead$10(var2, var3);
         case 11:
            return this.parse_pi$11(var2, var3);
         case 12:
            return this.get_starttag_text$12(var2, var3);
         case 13:
            return this.parse_starttag$13(var2, var3);
         case 14:
            return this._convert_ref$14(var2, var3);
         case 15:
            return this.parse_endtag$15(var2, var3);
         case 16:
            return this.finish_shorttag$16(var2, var3);
         case 17:
            return this.finish_starttag$17(var2, var3);
         case 18:
            return this.finish_endtag$18(var2, var3);
         case 19:
            return this.handle_starttag$19(var2, var3);
         case 20:
            return this.handle_endtag$20(var2, var3);
         case 21:
            return this.report_unbalanced$21(var2, var3);
         case 22:
            return this.convert_charref$22(var2, var3);
         case 23:
            return this.convert_codepoint$23(var2, var3);
         case 24:
            return this.handle_charref$24(var2, var3);
         case 25:
            return this.convert_entityref$25(var2, var3);
         case 26:
            return this.handle_entityref$26(var2, var3);
         case 27:
            return this.handle_data$27(var2, var3);
         case 28:
            return this.handle_comment$28(var2, var3);
         case 29:
            return this.handle_decl$29(var2, var3);
         case 30:
            return this.handle_pi$30(var2, var3);
         case 31:
            return this.unknown_starttag$31(var2, var3);
         case 32:
            return this.unknown_endtag$32(var2, var3);
         case 33:
            return this.unknown_charref$33(var2, var3);
         case 34:
            return this.unknown_entityref$34(var2, var3);
         case 35:
            return this.TestSGMLParser$35(var2, var3);
         case 36:
            return this.__init__$36(var2, var3);
         case 37:
            return this.handle_data$37(var2, var3);
         case 38:
            return this.flush$38(var2, var3);
         case 39:
            return this.handle_comment$39(var2, var3);
         case 40:
            return this.unknown_starttag$40(var2, var3);
         case 41:
            return this.unknown_endtag$41(var2, var3);
         case 42:
            return this.unknown_entityref$42(var2, var3);
         case 43:
            return this.unknown_charref$43(var2, var3);
         case 44:
            return this.unknown_decl$44(var2, var3);
         case 45:
            return this.close$45(var2, var3);
         case 46:
            return this.test$46(var2, var3);
         default:
            return null;
      }
   }
}
