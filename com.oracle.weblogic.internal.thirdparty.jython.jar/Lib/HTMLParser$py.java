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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("HTMLParser.py")
public class HTMLParser$py extends PyFunctionTable implements PyRunnable {
   static HTMLParser$py self;
   static final PyCode f$0;
   static final PyCode HTMLParseError$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode HTMLParser$4;
   static final PyCode __init__$5;
   static final PyCode reset$6;
   static final PyCode feed$7;
   static final PyCode close$8;
   static final PyCode error$9;
   static final PyCode get_starttag_text$10;
   static final PyCode set_cdata_mode$11;
   static final PyCode clear_cdata_mode$12;
   static final PyCode goahead$13;
   static final PyCode parse_html_declaration$14;
   static final PyCode parse_bogus_comment$15;
   static final PyCode parse_pi$16;
   static final PyCode parse_starttag$17;
   static final PyCode check_for_whole_start_tag$18;
   static final PyCode parse_endtag$19;
   static final PyCode handle_startendtag$20;
   static final PyCode handle_starttag$21;
   static final PyCode handle_endtag$22;
   static final PyCode handle_charref$23;
   static final PyCode handle_entityref$24;
   static final PyCode handle_data$25;
   static final PyCode handle_comment$26;
   static final PyCode handle_decl$27;
   static final PyCode handle_pi$28;
   static final PyCode unknown_decl$29;
   static final PyCode unescape$30;
   static final PyCode replaceEntities$31;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A parser for HTML and XHTML."));
      var1.setline(1);
      PyString.fromInterned("A parser for HTML and XHTML.");
      var1.setline(11);
      PyObject var3 = imp.importOne("markupbase", var1, -1);
      var1.setlocal("markupbase", var3);
      var3 = null;
      var1.setline(12);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[&<]"));
      var1.setlocal("interesting_normal", var3);
      var3 = null;
      var1.setline(17);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&[a-zA-Z#]"));
      var1.setlocal("incomplete", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&([a-zA-Z][-.a-zA-Z0-9]*)[^a-zA-Z0-9]"));
      var1.setlocal("entityref", var3);
      var3 = null;
      var1.setline(20);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&#(?:[0-9]+|[xX][0-9a-fA-F]+)[^0-9a-fA-F]"));
      var1.setlocal("charref", var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<[a-zA-Z]"));
      var1.setlocal("starttagopen", var3);
      var3 = null;
      var1.setline(23);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
      var1.setlocal("piclose", var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--\\s*>"));
      var1.setlocal("commentclose", var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([a-zA-Z][-.a-zA-Z0-9:_]*)(?:\\s|/(?!>))*"));
      var1.setlocal("tagfind", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[a-zA-Z][^\t\n\r\f />\u0000]*"));
      var1.setlocal("tagfind_tolerant", var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("((?<=[\\'\"\\s/])[^\\s/>][^\\s/=>]*)(\\s*=+\\s*(\\'[^\\']*\\'|\"[^\"]*\"|(?![\\'\"])[^>\\s]*))?(?:\\s|/(?!>))*"));
      var1.setlocal("attrfind", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n  <[a-zA-Z][-.a-zA-Z0-9:_]*          # tag name\n  (?:[\\s/]*                          # optional whitespace before attribute name\n    (?:(?<=['\"\\s/])[^\\s/>][^\\s/=>]*  # attribute name\n      (?:\\s*=+\\s*                    # value indicator\n        (?:'[^']*'                   # LITA-enclosed value\n          |\"[^\"]*\"                   # LIT-enclosed value\n          |(?!['\"])[^>\\s]*           # bare value\n         )\n       )?(?:\\s|/(?!>))*\n     )*\n   )?\n  \\s*                                # trailing whitespace\n"), (PyObject)var1.getname("re").__getattr__("VERBOSE"));
      var1.setlocal("locatestarttagend", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
      var1.setlocal("endendtag", var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</\\s*([a-zA-Z][-.a-zA-Z0-9:_]*)\\s*>"));
      var1.setlocal("endtagfind", var3);
      var3 = null;
      var1.setline(54);
      PyObject[] var5 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("HTMLParseError", var5, HTMLParseError$1);
      var1.setlocal("HTMLParseError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(72);
      var5 = new PyObject[]{var1.getname("markupbase").__getattr__("ParserBase")};
      var4 = Py.makeClass("HTMLParser", var5, HTMLParser$4);
      var1.setlocal("HTMLParser", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTMLParseError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised for all parse errors."));
      var1.setline(55);
      PyString.fromInterned("Exception raised for all parse errors.");
      var1.setline(57);
      PyObject[] var3 = new PyObject[]{new PyTuple(new PyObject[]{var1.getname("None"), var1.getname("None")})};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(1).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(59);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("msg", var3);
         var3 = null;
         var1.setline(60);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("lineno", var3);
         var3 = null;
         var1.setline(61);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var1.getlocal(0).__setattr__("offset", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getlocal(0).__getattr__("lineno");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(66);
         var3 = var1.getlocal(1)._add(PyString.fromInterned(", at line %d")._mod(var1.getlocal(0).__getattr__("lineno")));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(67);
      var3 = var1.getlocal(0).__getattr__("offset");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var3 = var1.getlocal(1)._add(PyString.fromInterned(", column %d")._mod(var1.getlocal(0).__getattr__("offset")._add(Py.newInteger(1))));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(69);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HTMLParser$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Find tags and other markup and call handler functions.\n\n    Usage:\n        p = HTMLParser()\n        p.feed(data)\n        ...\n        p.close()\n\n    Start tags are handled by calling self.handle_starttag() or\n    self.handle_startendtag(); end tags by self.handle_endtag().  The\n    data between tags is passed from the parser to the derived class\n    by calling self.handle_data() with the data as argument (the data\n    may be split up in arbitrary chunks).  Entity references are\n    passed by calling self.handle_entityref() with the entity\n    reference as the argument.  Numeric character references are\n    passed to self.handle_charref() with the string containing the\n    reference as the argument.\n    "));
      var1.setline(90);
      PyString.fromInterned("Find tags and other markup and call handler functions.\n\n    Usage:\n        p = HTMLParser()\n        p.feed(data)\n        ...\n        p.close()\n\n    Start tags are handled by calling self.handle_starttag() or\n    self.handle_startendtag(); end tags by self.handle_endtag().  The\n    data between tags is passed from the parser to the derived class\n    by calling self.handle_data() with the data as argument (the data\n    may be split up in arbitrary chunks).  Entity references are\n    passed by calling self.handle_entityref() with the entity\n    reference as the argument.  Numeric character references are\n    passed to self.handle_charref() with the string containing the\n    reference as the argument.\n    ");
      var1.setline(92);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("script"), PyString.fromInterned("style")});
      var1.setlocal("CDATA_CONTENT_ELEMENTS", var3);
      var3 = null;
      var1.setline(95);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$5, PyString.fromInterned("Initialize and reset this instance."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(99);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, reset$6, PyString.fromInterned("Reset this instance.  Loses all unprocessed data."));
      var1.setlocal("reset", var5);
      var3 = null;
      var1.setline(107);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, feed$7, PyString.fromInterned("Feed data to the parser.\n\n        Call this as often as you want, with as little or as much text\n        as you want (may include '\\n').\n        "));
      var1.setlocal("feed", var5);
      var3 = null;
      var1.setline(116);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$8, PyString.fromInterned("Handle any buffered data."));
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(120);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, error$9, (PyObject)null);
      var1.setlocal("error", var5);
      var3 = null;
      var1.setline(123);
      PyObject var6 = var1.getname("None");
      var1.setlocal("_HTMLParser__starttag_text", var6);
      var3 = null;
      var1.setline(125);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_starttag_text$10, PyString.fromInterned("Return full source of start tag: '<...>'."));
      var1.setlocal("get_starttag_text", var5);
      var3 = null;
      var1.setline(129);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_cdata_mode$11, (PyObject)null);
      var1.setlocal("set_cdata_mode", var5);
      var3 = null;
      var1.setline(133);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, clear_cdata_mode$12, (PyObject)null);
      var1.setlocal("clear_cdata_mode", var5);
      var3 = null;
      var1.setline(140);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, goahead$13, (PyObject)null);
      var1.setlocal("goahead", var5);
      var3 = null;
      var1.setline(234);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_html_declaration$14, (PyObject)null);
      var1.setlocal("parse_html_declaration", var5);
      var3 = null;
      var1.setline(255);
      var4 = new PyObject[]{Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, parse_bogus_comment$15, (PyObject)null);
      var1.setlocal("parse_bogus_comment", var5);
      var3 = null;
      var1.setline(267);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_pi$16, (PyObject)null);
      var1.setlocal("parse_pi", var5);
      var3 = null;
      var1.setline(279);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_starttag$17, (PyObject)null);
      var1.setlocal("parse_starttag", var5);
      var3 = null;
      var1.setline(331);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, check_for_whole_start_tag$18, (PyObject)null);
      var1.setlocal("check_for_whole_start_tag", var5);
      var3 = null;
      var1.setline(363);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parse_endtag$19, (PyObject)null);
      var1.setlocal("parse_endtag", var5);
      var3 = null;
      var1.setline(403);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_startendtag$20, (PyObject)null);
      var1.setlocal("handle_startendtag", var5);
      var3 = null;
      var1.setline(408);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_starttag$21, (PyObject)null);
      var1.setlocal("handle_starttag", var5);
      var3 = null;
      var1.setline(412);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_endtag$22, (PyObject)null);
      var1.setlocal("handle_endtag", var5);
      var3 = null;
      var1.setline(416);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_charref$23, (PyObject)null);
      var1.setlocal("handle_charref", var5);
      var3 = null;
      var1.setline(420);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_entityref$24, (PyObject)null);
      var1.setlocal("handle_entityref", var5);
      var3 = null;
      var1.setline(424);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_data$25, (PyObject)null);
      var1.setlocal("handle_data", var5);
      var3 = null;
      var1.setline(428);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_comment$26, (PyObject)null);
      var1.setlocal("handle_comment", var5);
      var3 = null;
      var1.setline(432);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_decl$27, (PyObject)null);
      var1.setlocal("handle_decl", var5);
      var3 = null;
      var1.setline(436);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_pi$28, (PyObject)null);
      var1.setlocal("handle_pi", var5);
      var3 = null;
      var1.setline(439);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unknown_decl$29, (PyObject)null);
      var1.setlocal("unknown_decl", var5);
      var3 = null;
      var1.setline(443);
      var6 = var1.getname("None");
      var1.setlocal("entitydefs", var6);
      var3 = null;
      var1.setline(444);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unescape$30, (PyObject)null);
      var1.setlocal("unescape", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyString.fromInterned("Initialize and reset this instance.");
      var1.setline(97);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$6(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyString.fromInterned("Reset this instance.  Loses all unprocessed data.");
      var1.setline(101);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"rawdata", var3);
      var3 = null;
      var1.setline(102);
      var3 = PyString.fromInterned("???");
      var1.getlocal(0).__setattr__((String)"lasttag", var3);
      var3 = null;
      var1.setline(103);
      PyObject var4 = var1.getglobal("interesting_normal");
      var1.getlocal(0).__setattr__("interesting", var4);
      var3 = null;
      var1.setline(104);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("cdata_elem", var4);
      var3 = null;
      var1.setline(105);
      var1.getglobal("markupbase").__getattr__("ParserBase").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$7(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Feed data to the parser.\n\n        Call this as often as you want, with as little or as much text\n        as you want (may include '\\n').\n        ");
      var1.setline(113);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("rawdata", var3);
      var3 = null;
      var1.setline(114);
      var1.getlocal(0).__getattr__("goahead").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$8(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Handle any buffered data.");
      var1.setline(118);
      var1.getlocal(0).__getattr__("goahead").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$9(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      throw Py.makeException(var1.getglobal("HTMLParseError").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("getpos").__call__(var2)));
   }

   public PyObject get_starttag_text$10(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Return full source of start tag: '<...>'.");
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("_HTMLParser__starttag_text");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_cdata_mode$11(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.getlocal(0).__setattr__("cdata_elem", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("</\\s*%s\\s*>")._mod(var1.getlocal(0).__getattr__("cdata_elem")), var1.getglobal("re").__getattr__("I"));
      var1.getlocal(0).__setattr__("interesting", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_cdata_mode$12(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject var3 = var1.getglobal("interesting_normal");
      var1.getlocal(0).__setattr__("interesting", var3);
      var3 = null;
      var1.setline(135);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("cdata_elem", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject goahead$13(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(142);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(143);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(144);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(145);
         var3 = var1.getlocal(0).__getattr__("interesting").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(146);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(147);
            var3 = var1.getlocal(5).__getattr__("start").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(149);
            if (var1.getlocal(0).__getattr__("cdata_elem").__nonzero__()) {
               break;
            }

            var1.setline(151);
            var3 = var1.getlocal(4);
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(152);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(6));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(152);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(6), (PyObject)null));
         }

         var1.setline(153);
         var3 = var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(3), var1.getlocal(6));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(154);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(155);
         var3 = var1.getlocal(2).__getattr__("startswith");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(156);
         if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)var1.getlocal(3)).__nonzero__()) {
            var1.setline(157);
            if (var1.getglobal("starttagopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
               var1.setline(158);
               var3 = var1.getlocal(0).__getattr__("parse_starttag").__call__(var2, var1.getlocal(3));
               var1.setlocal(8, var3);
               var3 = null;
            } else {
               var1.setline(159);
               if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</"), (PyObject)var1.getlocal(3)).__nonzero__()) {
                  var1.setline(160);
                  var3 = var1.getlocal(0).__getattr__("parse_endtag").__call__(var2, var1.getlocal(3));
                  var1.setlocal(8, var3);
                  var3 = null;
               } else {
                  var1.setline(161);
                  if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!--"), (PyObject)var1.getlocal(3)).__nonzero__()) {
                     var1.setline(162);
                     var3 = var1.getlocal(0).__getattr__("parse_comment").__call__(var2, var1.getlocal(3));
                     var1.setlocal(8, var3);
                     var3 = null;
                  } else {
                     var1.setline(163);
                     if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<?"), (PyObject)var1.getlocal(3)).__nonzero__()) {
                        var1.setline(164);
                        var3 = var1.getlocal(0).__getattr__("parse_pi").__call__(var2, var1.getlocal(3));
                        var1.setlocal(8, var3);
                        var3 = null;
                     } else {
                        var1.setline(165);
                        if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!"), (PyObject)var1.getlocal(3)).__nonzero__()) {
                           var1.setline(166);
                           var3 = var1.getlocal(0).__getattr__("parse_html_declaration").__call__(var2, var1.getlocal(3));
                           var1.setlocal(8, var3);
                           var3 = null;
                        } else {
                           var1.setline(167);
                           var3 = var1.getlocal(3)._add(Py.newInteger(1));
                           var10000 = var3._lt(var1.getlocal(4));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              break;
                           }

                           var1.setline(168);
                           var1.getlocal(0).__getattr__("handle_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"));
                           var1.setline(169);
                           var3 = var1.getlocal(3)._add(Py.newInteger(1));
                           var1.setlocal(8, var3);
                           var3 = null;
                        }
                     }
                  }
               }
            }

            var1.setline(172);
            var3 = var1.getlocal(8);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(173);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(175);
               var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(3)._add(Py.newInteger(1)));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(176);
               var3 = var1.getlocal(8);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(177);
                  var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)var1.getlocal(3)._add(Py.newInteger(1)));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(178);
                  var3 = var1.getlocal(8);
                  var10000 = var3._lt(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(179);
                     var3 = var1.getlocal(3)._add(Py.newInteger(1));
                     var1.setlocal(8, var3);
                     var3 = null;
                  }
               } else {
                  var1.setline(181);
                  var3 = var1.getlocal(8);
                  var3 = var3._iadd(Py.newInteger(1));
                  var1.setlocal(8, var3);
               }

               var1.setline(182);
               var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null));
            }

            var1.setline(183);
            var3 = var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(3), var1.getlocal(8));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(184);
            if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&#"), (PyObject)var1.getlocal(3)).__nonzero__()) {
               var1.setline(185);
               var3 = var1.getglobal("charref").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(186);
               if (!var1.getlocal(5).__nonzero__()) {
                  var1.setline(195);
                  PyString var5 = PyString.fromInterned(";");
                  var10000 = var5._in(var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(196);
                     var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null));
                     var1.setline(197);
                     var3 = var1.getlocal(0).__getattr__("updatepos").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(2));
                     var1.setlocal(3, var3);
                     var3 = null;
                  }
                  break;
               }

               var1.setline(187);
               var3 = var1.getlocal(5).__getattr__("group").__call__(var2).__getslice__(Py.newInteger(2), Py.newInteger(-1), (PyObject)null);
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(188);
               var1.getlocal(0).__getattr__("handle_charref").__call__(var2, var1.getlocal(9));
               var1.setline(189);
               var3 = var1.getlocal(5).__getattr__("end").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(190);
               if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"), (PyObject)var1.getlocal(8)._sub(Py.newInteger(1))).__not__().__nonzero__()) {
                  var1.setline(191);
                  var3 = var1.getlocal(8)._sub(Py.newInteger(1));
                  var1.setlocal(8, var3);
                  var3 = null;
               }

               var1.setline(192);
               var3 = var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(3), var1.getlocal(8));
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(199);
               if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)var1.getlocal(3)).__nonzero__()) {
                  var1.setline(200);
                  var3 = var1.getglobal("entityref").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(201);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(202);
                     var3 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(203);
                     var1.getlocal(0).__getattr__("handle_entityref").__call__(var2, var1.getlocal(9));
                     var1.setline(204);
                     var3 = var1.getlocal(5).__getattr__("end").__call__(var2);
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(205);
                     if (var1.getlocal(7).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"), (PyObject)var1.getlocal(8)._sub(Py.newInteger(1))).__not__().__nonzero__()) {
                        var1.setline(206);
                        var3 = var1.getlocal(8)._sub(Py.newInteger(1));
                        var1.setlocal(8, var3);
                        var3 = null;
                     }

                     var1.setline(207);
                     var3 = var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(3), var1.getlocal(8));
                     var1.setlocal(3, var3);
                     var3 = null;
                  } else {
                     var1.setline(209);
                     var3 = var1.getglobal("incomplete").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(210);
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(212);
                        var10000 = var1.getlocal(1);
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(5).__getattr__("group").__call__(var2);
                           var10000 = var3._eq(var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
                           var3 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(213);
                           var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EOF in middle of entity or char ref"));
                        }
                        break;
                     }

                     var1.setline(216);
                     var3 = var1.getlocal(3)._add(Py.newInteger(1));
                     var10000 = var3._lt(var1.getlocal(4));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(219);
                     var1.getlocal(0).__getattr__("handle_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"));
                     var1.setline(220);
                     var3 = var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(3), var1.getlocal(3)._add(Py.newInteger(1)));
                     var1.setlocal(3, var3);
                     var3 = null;
                  }
               } else {
                  var1.setline(224);
                  if (var1.getglobal("__debug__").__nonzero__() && !Py.newInteger(0).__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("interesting.search() lied"));
                  }
               }
            }
         }
      }

      var1.setline(226);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("cdata_elem").__not__();
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(227);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(4), (PyObject)null));
         var1.setline(228);
         var3 = var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(229);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("rawdata", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_html_declaration$14(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(2)), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("<!"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(237);
         var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_html_declaration()"));
      }

      var1.setline(238);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(4)), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("<!--"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(240);
         var3 = var1.getlocal(0).__getattr__("parse_comment").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(241);
         PyObject var4 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(3)), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("<!["));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(242);
            var3 = var1.getlocal(0).__getattr__("parse_marked_section").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(243);
            var4 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(9)), (PyObject)null).__getattr__("lower").__call__(var2);
            var10000 = var4._eq(PyString.fromInterned("<!doctype"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(245);
               var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(1)._add(Py.newInteger(9)));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(246);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(Py.newInteger(-1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(247);
                  PyInteger var5 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(248);
                  var1.getlocal(0).__getattr__("handle_decl").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(3), (PyObject)null));
                  var1.setline(249);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(251);
               var3 = var1.getlocal(0).__getattr__("parse_bogus_comment").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject parse_bogus_comment$15(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(257);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(2)), (PyObject)null);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("<!"), PyString.fromInterned("</")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(258);
         var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_comment()"));
      }

      var1.setline(259);
      var3 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(1)._add(Py.newInteger(2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(260);
      var3 = var1.getlocal(4);
      var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(261);
         PyInteger var4 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(262);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(263);
            var1.getlocal(0).__getattr__("handle_comment").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(4), (PyObject)null));
         }

         var1.setline(264);
         var3 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parse_pi$16(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(269);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(2)), (PyObject)null);
         PyObject var10000 = var3._eq(PyString.fromInterned("<?"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("unexpected call to parse_pi()"));
         }
      }

      var1.setline(270);
      var3 = var1.getglobal("piclose").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(271);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(272);
         PyInteger var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(273);
         PyObject var4 = var1.getlocal(3).__getattr__("start").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(274);
         var1.getlocal(0).__getattr__("handle_pi").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(4), (PyObject)null));
         var1.setline(275);
         var4 = var1.getlocal(3).__getattr__("end").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(276);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parse_starttag$17(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_HTMLParser__starttag_text", var3);
      var3 = null;
      var1.setline(281);
      var3 = var1.getlocal(0).__getattr__("check_for_whole_start_tag").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(282);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(283);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(284);
         PyObject var4 = var1.getlocal(0).__getattr__("rawdata");
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(285);
         var4 = var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
         var1.getlocal(0).__setattr__("_HTMLParser__starttag_text", var4);
         var4 = null;
         var1.setline(288);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var7);
         var4 = null;
         var1.setline(289);
         var4 = var1.getglobal("tagfind").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(Py.newInteger(1)));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(290);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(5).__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("unexpected call to parse_starttag()"));
         } else {
            var1.setline(291);
            var4 = var1.getlocal(5).__getattr__("end").__call__(var2);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(292);
            var4 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("lower").__call__(var2);
            var1.getlocal(0).__setattr__("lasttag", var4);
            var1.setlocal(7, var4);

            PyObject[] var5;
            PyObject var6;
            PyString var9;
            while(true) {
               var1.setline(294);
               var4 = var1.getlocal(6);
               var10000 = var4._lt(var1.getlocal(2));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(295);
               var4 = var1.getglobal("attrfind").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(6));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(296);
               if (var1.getlocal(8).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(298);
               var4 = var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)Py.newInteger(2), (PyObject)Py.newInteger(3));
               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(11, var6);
               var6 = null;
               var4 = null;
               var1.setline(299);
               if (var1.getlocal(10).__not__().__nonzero__()) {
                  var1.setline(300);
                  var4 = var1.getglobal("None");
                  var1.setlocal(11, var4);
                  var4 = null;
               } else {
                  var1.setline(301);
                  var4 = var1.getlocal(11).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                  PyString var10001 = PyString.fromInterned("'");
                  var10000 = var4;
                  var9 = var10001;
                  PyObject var8;
                  if ((var8 = var10000._eq(var10001)).__nonzero__()) {
                     var8 = var9._eq(var1.getlocal(11).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
                  }

                  var10000 = var8;
                  var4 = null;
                  if (!var8.__nonzero__()) {
                     var4 = var1.getlocal(11).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                     var10001 = PyString.fromInterned("\"");
                     var10000 = var4;
                     var9 = var10001;
                     if ((var8 = var10000._eq(var10001)).__nonzero__()) {
                        var8 = var9._eq(var1.getlocal(11).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
                     }

                     var10000 = var8;
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(303);
                     var4 = var1.getlocal(11).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(11, var4);
                     var4 = null;
                  }
               }

               var1.setline(304);
               if (var1.getlocal(11).__nonzero__()) {
                  var1.setline(305);
                  var4 = var1.getlocal(0).__getattr__("unescape").__call__(var2, var1.getlocal(11));
                  var1.setlocal(11, var4);
                  var4 = null;
               }

               var1.setline(306);
               var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9).__getattr__("lower").__call__(var2), var1.getlocal(11)})));
               var1.setline(307);
               var4 = var1.getlocal(8).__getattr__("end").__call__(var2);
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(309);
            var4 = var1.getlocal(3).__getslice__(var1.getlocal(6), var1.getlocal(2), (PyObject)null).__getattr__("strip").__call__(var2);
            var1.setlocal(12, var4);
            var4 = null;
            var1.setline(310);
            var4 = var1.getlocal(12);
            var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned(">"), PyString.fromInterned("/>")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(311);
               var4 = var1.getlocal(0).__getattr__("getpos").__call__(var2);
               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(13, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(14, var6);
               var6 = null;
               var4 = null;
               var1.setline(312);
               var9 = PyString.fromInterned("\n");
               var10000 = var9._in(var1.getlocal(0).__getattr__("_HTMLParser__starttag_text"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(313);
                  var4 = var1.getlocal(13)._add(var1.getlocal(0).__getattr__("_HTMLParser__starttag_text").__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(314);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_HTMLParser__starttag_text"))._sub(var1.getlocal(0).__getattr__("_HTMLParser__starttag_text").__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                  var1.setlocal(14, var4);
                  var4 = null;
               } else {
                  var1.setline(317);
                  var4 = var1.getlocal(14)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_HTMLParser__starttag_text")));
                  var1.setlocal(14, var4);
                  var4 = null;
               }

               var1.setline(318);
               var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null));
               var1.setline(319);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(320);
               if (var1.getlocal(12).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/>")).__nonzero__()) {
                  var1.setline(322);
                  var1.getlocal(0).__getattr__("handle_startendtag").__call__(var2, var1.getlocal(7), var1.getlocal(4));
               } else {
                  var1.setline(324);
                  var1.getlocal(0).__getattr__("handle_starttag").__call__(var2, var1.getlocal(7), var1.getlocal(4));
                  var1.setline(325);
                  var4 = var1.getlocal(7);
                  var10000 = var4._in(var1.getlocal(0).__getattr__("CDATA_CONTENT_ELEMENTS"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(326);
                     var1.getlocal(0).__getattr__("set_cdata_mode").__call__(var2, var1.getlocal(7));
                  }
               }

               var1.setline(327);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject check_for_whole_start_tag$18(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(333);
      var3 = var1.getglobal("locatestarttagend").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(334);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(335);
         var3 = var1.getlocal(3).__getattr__("end").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(336);
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(337);
         var3 = var1.getlocal(5);
         PyObject var10000 = var3._eq(PyString.fromInterned(">"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(338);
            var3 = var1.getlocal(4)._add(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(339);
            PyObject var4 = var1.getlocal(5);
            var10000 = var4._eq(PyString.fromInterned("/"));
            var4 = null;
            PyInteger var5;
            if (var10000.__nonzero__()) {
               var1.setline(340);
               if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/>"), (PyObject)var1.getlocal(4)).__nonzero__()) {
                  var1.setline(341);
                  var3 = var1.getlocal(4)._add(Py.newInteger(2));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(342);
               if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getlocal(4)).__nonzero__()) {
                  var1.setline(344);
                  var5 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setline(346);
               var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(1), var1.getlocal(4)._add(Py.newInteger(1)));
               var1.setline(347);
               var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("malformed empty start tag"));
            }

            var1.setline(348);
            var4 = var1.getlocal(5);
            var10000 = var4._eq(PyString.fromInterned(""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(350);
               var5 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(351);
               var4 = var1.getlocal(5);
               var10000 = var4._in(PyString.fromInterned("abcdefghijklmnopqrstuvwxyz=/ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(355);
                  var5 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(356);
                  var4 = var1.getlocal(4);
                  var10000 = var4._gt(var1.getlocal(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(357);
                     var3 = var1.getlocal(4);
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(359);
                     var3 = var1.getlocal(1)._add(Py.newInteger(1));
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      } else {
         var1.setline(360);
         throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("we should not get here!")));
      }
   }

   public PyObject parse_endtag$19(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(365);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(2)), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("</"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("unexpected call to parse_endtag"));
         }
      }

      var1.setline(366);
      var3 = var1.getglobal("endendtag").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(367);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(368);
         PyInteger var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(369);
         PyObject var4 = var1.getlocal(3).__getattr__("end").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(370);
         var4 = var1.getglobal("endtagfind").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(371);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(372);
            var4 = var1.getlocal(0).__getattr__("cdata_elem");
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(373);
               var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(4), (PyObject)null));
               var1.setline(374);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(376);
               var4 = var1.getglobal("tagfind_tolerant").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(2)));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(377);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  var1.setline(379);
                  var4 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(3)), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("</>"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(380);
                     var3 = var1.getlocal(1)._add(Py.newInteger(3));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(382);
                     var3 = var1.getlocal(0).__getattr__("parse_bogus_comment").__call__(var2, var1.getlocal(1));
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(383);
                  var4 = var1.getlocal(5).__getattr__("group").__call__(var2).__getattr__("lower").__call__(var2);
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(388);
                  var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(5).__getattr__("end").__call__(var2));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(389);
                  var1.getlocal(0).__getattr__("handle_endtag").__call__(var2, var1.getlocal(6));
                  var1.setline(390);
                  var3 = var1.getlocal(4)._add(Py.newInteger(1));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         } else {
            var1.setline(392);
            var4 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("lower").__call__(var2);
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(393);
            var4 = var1.getlocal(0).__getattr__("cdata_elem");
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(394);
               var4 = var1.getlocal(7);
               var10000 = var4._ne(var1.getlocal(0).__getattr__("cdata_elem"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(395);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(4), (PyObject)null));
                  var1.setline(396);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }
            }

            var1.setline(398);
            var1.getlocal(0).__getattr__("handle_endtag").__call__(var2, var1.getlocal(7));
            var1.setline(399);
            var1.getlocal(0).__getattr__("clear_cdata_mode").__call__(var2);
            var1.setline(400);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject handle_startendtag$20(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      var1.getlocal(0).__getattr__("handle_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(405);
      var1.getlocal(0).__getattr__("handle_endtag").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_starttag$21(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_endtag$22(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_charref$23(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_entityref$24(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_data$25(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_comment$26(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_decl$27(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_pi$28(PyFrame var1, ThreadState var2) {
      var1.setline(437);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_decl$29(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unescape$30(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(445);
      PyString var3 = PyString.fromInterned("&");
      PyObject var10000 = var3._notin(var1.getlocal(1));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(446);
         var5 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(447);
         PyObject[] var4 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var4;
         PyCode var10004 = replaceEntities$31;
         var4 = new PyObject[]{var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
         var1.setlocal(2, var6);
         var4 = null;
         var1.setline(472);
         var5 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("&(#?[xX]?(?:[0-9a-fA-F]+|\\w{1,8}));"), (PyObject)var1.getlocal(2), (PyObject)var1.getlocal(1));
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject replaceEntities$31(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      PyObject var3 = var1.getlocal(0).__getattr__("groups").__call__(var2).__getitem__(Py.newInteger(0));
      var1.setlocal(0, var3);
      var3 = null;

      PyObject var10000;
      try {
         var1.setline(450);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("#"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(451);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(452);
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var10000 = var3._in(new PyList(new PyObject[]{PyString.fromInterned("x"), PyString.fromInterned("X")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(453);
               var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(16));
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(455);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(456);
            var3 = var1.getglobal("unichr").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var10) {
         PyException var4 = Py.setException(var10, var1);
         if (var4.match(var1.getglobal("ValueError"))) {
            var1.setline(458);
            var3 = PyString.fromInterned("&#")._add(var1.getlocal(0))._add(PyString.fromInterned(";"));
            var1.f_lasti = -1;
            return var3;
         }

         throw var4;
      }

      var1.setline(462);
      PyObject var5 = imp.importOne("htmlentitydefs", var1, -1);
      var1.setlocal(2, var5);
      var5 = null;
      var1.setline(463);
      var5 = var1.getglobal("HTMLParser").__getattr__("entitydefs");
      var10000 = var5._is(var1.getglobal("None"));
      var5 = null;
      if (var10000.__nonzero__()) {
         var1.setline(464);
         PyDictionary var11 = new PyDictionary(new PyObject[]{PyString.fromInterned("apos"), PyUnicode.fromInterned("'")});
         var1.setlocal(3, var11);
         var1.getglobal("HTMLParser").__setattr__((String)"entitydefs", var11);
         var1.setline(465);
         var5 = var1.getlocal(2).__getattr__("name2codepoint").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(465);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(4, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(5, var8);
            var8 = null;
            var1.setline(466);
            PyObject var13 = var1.getglobal("unichr").__call__(var2, var1.getlocal(5));
            var1.getlocal(3).__setitem__(var1.getlocal(4), var13);
            var7 = null;
         }
      }

      try {
         var1.setline(468);
         var3 = var1.getderef(0).__getattr__("entitydefs").__getitem__(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var9) {
         PyException var12 = Py.setException(var9, var1);
         if (var12.match(var1.getglobal("KeyError"))) {
            var1.setline(470);
            var3 = PyString.fromInterned("&")._add(var1.getlocal(0))._add(PyString.fromInterned(";"));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var12;
         }
      }
   }

   public HTMLParser$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HTMLParseError$1 = Py.newCode(0, var2, var1, "HTMLParseError", 54, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "position"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 57, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 63, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTMLParser$4 = Py.newCode(0, var2, var1, "HTMLParser", 72, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$5 = Py.newCode(1, var2, var1, "__init__", 95, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$6 = Py.newCode(1, var2, var1, "reset", 99, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$7 = Py.newCode(2, var2, var1, "feed", 107, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$8 = Py.newCode(1, var2, var1, "close", 116, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      error$9 = Py.newCode(2, var2, var1, "error", 120, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_starttag_text$10 = Py.newCode(1, var2, var1, "get_starttag_text", 125, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem"};
      set_cdata_mode$11 = Py.newCode(2, var2, var1, "set_cdata_mode", 129, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear_cdata_mode$12 = Py.newCode(1, var2, var1, "clear_cdata_mode", 133, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "end", "rawdata", "i", "n", "match", "j", "startswith", "k", "name"};
      goahead$13 = Py.newCode(2, var2, var1, "goahead", 140, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "gtpos"};
      parse_html_declaration$14 = Py.newCode(2, var2, var1, "parse_html_declaration", 234, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "report", "rawdata", "pos"};
      parse_bogus_comment$15 = Py.newCode(3, var2, var1, "parse_bogus_comment", 255, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "match", "j"};
      parse_pi$16 = Py.newCode(2, var2, var1, "parse_pi", 267, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "endpos", "rawdata", "attrs", "match", "k", "tag", "m", "attrname", "rest", "attrvalue", "end", "lineno", "offset"};
      parse_starttag$17 = Py.newCode(2, var2, var1, "parse_starttag", 279, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "m", "j", "next"};
      check_for_whole_start_tag$18 = Py.newCode(2, var2, var1, "check_for_whole_start_tag", 331, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "match", "gtpos", "namematch", "tagname", "elem"};
      parse_endtag$19 = Py.newCode(2, var2, var1, "parse_endtag", 363, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs"};
      handle_startendtag$20 = Py.newCode(3, var2, var1, "handle_startendtag", 403, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs"};
      handle_starttag$21 = Py.newCode(3, var2, var1, "handle_starttag", 408, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      handle_endtag$22 = Py.newCode(2, var2, var1, "handle_endtag", 412, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      handle_charref$23 = Py.newCode(2, var2, var1, "handle_charref", 416, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      handle_entityref$24 = Py.newCode(2, var2, var1, "handle_entityref", 420, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$25 = Py.newCode(2, var2, var1, "handle_data", 424, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_comment$26 = Py.newCode(2, var2, var1, "handle_comment", 428, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "decl"};
      handle_decl$27 = Py.newCode(2, var2, var1, "handle_decl", 432, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_pi$28 = Py.newCode(2, var2, var1, "handle_pi", 436, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      unknown_decl$29 = Py.newCode(2, var2, var1, "unknown_decl", 439, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "replaceEntities"};
      String[] var10001 = var2;
      HTMLParser$py var10007 = self;
      var2 = new String[]{"self"};
      unescape$30 = Py.newCode(2, var10001, var1, "unescape", 444, false, false, var10007, 30, var2, (String[])null, 0, 4097);
      var2 = new String[]{"s", "c", "htmlentitydefs", "entitydefs", "k", "v"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      replaceEntities$31 = Py.newCode(1, var10001, var1, "replaceEntities", 447, false, false, var10007, 31, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new HTMLParser$py("HTMLParser$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(HTMLParser$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.HTMLParseError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.HTMLParser$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.reset$6(var2, var3);
         case 7:
            return this.feed$7(var2, var3);
         case 8:
            return this.close$8(var2, var3);
         case 9:
            return this.error$9(var2, var3);
         case 10:
            return this.get_starttag_text$10(var2, var3);
         case 11:
            return this.set_cdata_mode$11(var2, var3);
         case 12:
            return this.clear_cdata_mode$12(var2, var3);
         case 13:
            return this.goahead$13(var2, var3);
         case 14:
            return this.parse_html_declaration$14(var2, var3);
         case 15:
            return this.parse_bogus_comment$15(var2, var3);
         case 16:
            return this.parse_pi$16(var2, var3);
         case 17:
            return this.parse_starttag$17(var2, var3);
         case 18:
            return this.check_for_whole_start_tag$18(var2, var3);
         case 19:
            return this.parse_endtag$19(var2, var3);
         case 20:
            return this.handle_startendtag$20(var2, var3);
         case 21:
            return this.handle_starttag$21(var2, var3);
         case 22:
            return this.handle_endtag$22(var2, var3);
         case 23:
            return this.handle_charref$23(var2, var3);
         case 24:
            return this.handle_entityref$24(var2, var3);
         case 25:
            return this.handle_data$25(var2, var3);
         case 26:
            return this.handle_comment$26(var2, var3);
         case 27:
            return this.handle_decl$27(var2, var3);
         case 28:
            return this.handle_pi$28(var2, var3);
         case 29:
            return this.unknown_decl$29(var2, var3);
         case 30:
            return this.unescape$30(var2, var3);
         case 31:
            return this.replaceEntities$31(var2, var3);
         default:
            return null;
      }
   }
}
