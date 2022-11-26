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
@Filename("xmllib.py")
public class xmllib$py extends PyFunctionTable implements PyRunnable {
   static xmllib$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode XMLParser$2;
   static final PyCode __init__$3;
   static final PyCode _XMLParser__fixelements$4;
   static final PyCode _XMLParser__fixclass$5;
   static final PyCode _XMLParser__fixdict$6;
   static final PyCode reset$7;
   static final PyCode setnomoretags$8;
   static final PyCode setliteral$9;
   static final PyCode feed$10;
   static final PyCode close$11;
   static final PyCode translate_references$12;
   static final PyCode getnamespace$13;
   static final PyCode goahead$14;
   static final PyCode parse_comment$15;
   static final PyCode parse_doctype$16;
   static final PyCode parse_cdata$17;
   static final PyCode parse_proc$18;
   static final PyCode parse_attributes$19;
   static final PyCode parse_starttag$20;
   static final PyCode parse_endtag$21;
   static final PyCode finish_starttag$22;
   static final PyCode finish_endtag$23;
   static final PyCode handle_xml$24;
   static final PyCode handle_doctype$25;
   static final PyCode handle_starttag$26;
   static final PyCode handle_endtag$27;
   static final PyCode handle_charref$28;
   static final PyCode handle_data$29;
   static final PyCode handle_cdata$30;
   static final PyCode handle_comment$31;
   static final PyCode handle_proc$32;
   static final PyCode syntax_error$33;
   static final PyCode unknown_starttag$34;
   static final PyCode unknown_endtag$35;
   static final PyCode unknown_charref$36;
   static final PyCode unknown_entityref$37;
   static final PyCode TestXMLParser$38;
   static final PyCode __init__$39;
   static final PyCode handle_xml$40;
   static final PyCode handle_doctype$41;
   static final PyCode handle_data$42;
   static final PyCode flush$43;
   static final PyCode handle_cdata$44;
   static final PyCode handle_proc$45;
   static final PyCode handle_comment$46;
   static final PyCode syntax_error$47;
   static final PyCode unknown_starttag$48;
   static final PyCode unknown_endtag$49;
   static final PyCode unknown_entityref$50;
   static final PyCode unknown_charref$51;
   static final PyCode close$52;
   static final PyCode test$53;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A parser for XML, using the derived class as static DTD."));
      var1.setline(1);
      PyString.fromInterned("A parser for XML, using the derived class as static DTD.");
      var1.setline(5);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(9);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("The xmllib module is obsolete.  Use xml.sax instead."), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(11);
      var1.dellocal("warnings");
      var1.setline(13);
      PyString var5 = PyString.fromInterned("0.3");
      var1.setlocal("version", var5);
      var3 = null;
      var1.setline(15);
      PyObject[] var6 = new PyObject[]{var1.getname("RuntimeError")};
      PyObject var4 = Py.makeClass("Error", var6, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(20);
      var5 = PyString.fromInterned("[ \t\r\n]+");
      var1.setlocal("_S", var5);
      var3 = null;
      var1.setline(21);
      var5 = PyString.fromInterned("[ \t\r\n]*");
      var1.setlocal("_opS", var5);
      var3 = null;
      var1.setline(22);
      var5 = PyString.fromInterned("[a-zA-Z_:][-a-zA-Z0-9._:]*");
      var1.setlocal("_Name", var5);
      var3 = null;
      var1.setline(23);
      var5 = PyString.fromInterned("(?:'[^']*'|\"[^\"]*\")");
      var1.setlocal("_QStr", var5);
      var3 = null;
      var1.setline(24);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^\t\r\n -~ -ÿ]"));
      var1.setlocal("illegal", var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[]&<]"));
      var1.setlocal("interesting", var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"));
      var1.setlocal("amp", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("&(")._add(var1.getname("_Name"))._add(PyString.fromInterned("|#[0-9]+|#x[0-9a-fA-F]+)[^-a-zA-Z0-9._:]")));
      var1.setlocal("ref", var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("&(?P<name>")._add(var1.getname("_Name"))._add(PyString.fromInterned(")[^-a-zA-Z0-9._:]")));
      var1.setlocal("entityref", var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&#(?P<char>[0-9]+[^0-9]|x[0-9a-fA-F]+[^0-9a-fA-F])"));
      var1.setlocal("charref", var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_S")._add(PyString.fromInterned("$")));
      var1.setlocal("space", var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal("newline", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_S")._add(PyString.fromInterned("(?P<name>"))._add(var1.getname("_Name"))._add(PyString.fromInterned(")("))._add(var1.getname("_opS"))._add(PyString.fromInterned("="))._add(var1.getname("_opS"))._add(PyString.fromInterned("(?P<value>"))._add(var1.getname("_QStr"))._add(PyString.fromInterned("|[-a-zA-Z0-9.:+*%?!\\(\\)_#=~]+))?")));
      var1.setlocal("attrfind", var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("<")._add(var1.getname("_Name")));
      var1.setlocal("starttagopen", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_opS")._add(PyString.fromInterned("(?P<slash>/?)>")));
      var1.setlocal("starttagend", var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("<(?P<tagname>")._add(var1.getname("_Name"))._add(PyString.fromInterned(")(?P<attrs>(?:"))._add(var1.getname("attrfind").__getattr__("pattern"))._add(PyString.fromInterned(")*)"))._add(var1.getname("starttagend").__getattr__("pattern")));
      var1.setlocal("starttagmatch", var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</"));
      var1.setlocal("endtagopen", var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_opS")._add(PyString.fromInterned(">")));
      var1.setlocal("endbracket", var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("(?:[^>'\"]|")._add(var1.getname("_QStr"))._add(PyString.fromInterned(")*>")));
      var1.setlocal("endbracketfind", var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_Name"));
      var1.setlocal("tagfind", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!\\[CDATA\\["));
      var1.setlocal("cdataopen", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\]\\]>"));
      var1.setlocal("cdataclose", var3);
      var3 = null;
      var1.setline(52);
      var3 = PyString.fromInterned("(?P<%s>")._add(var1.getname("_QStr"))._add(PyString.fromInterned(")"));
      var1.setlocal("_SystemLiteral", var3);
      var3 = null;
      var1.setline(53);
      var5 = PyString.fromInterned("(?P<%s>\"[-'\\(\\)+,./:=?;!*#@$_%% \n\ra-zA-Z0-9]*\"|'[-\\(\\)+,./:=?;!*#@$_%% \n\ra-zA-Z0-9]*')");
      var1.setlocal("_PublicLiteral", var5);
      var3 = null;
      var1.setline(55);
      var3 = PyString.fromInterned("(?:SYSTEM|PUBLIC")._add(var1.getname("_S"))._add(var1.getname("_PublicLiteral")._mod(PyString.fromInterned("pubid")))._add(PyString.fromInterned(")"))._add(var1.getname("_S"))._add(var1.getname("_SystemLiteral")._mod(PyString.fromInterned("syslit")));
      var1.setlocal("_ExternalId", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("<!DOCTYPE")._add(var1.getname("_S"))._add(PyString.fromInterned("(?P<name>"))._add(var1.getname("_Name"))._add(PyString.fromInterned(")(?:"))._add(var1.getname("_S"))._add(var1.getname("_ExternalId"))._add(PyString.fromInterned(")?"))._add(var1.getname("_opS")));
      var1.setlocal("doctype", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("<\\?xml")._add(var1.getname("_S"))._add(PyString.fromInterned("version"))._add(var1.getname("_opS"))._add(PyString.fromInterned("="))._add(var1.getname("_opS"))._add(PyString.fromInterned("(?P<version>"))._add(var1.getname("_QStr"))._add(PyString.fromInterned(")"))._add(PyString.fromInterned("(?:"))._add(var1.getname("_S"))._add(PyString.fromInterned("encoding"))._add(var1.getname("_opS"))._add(PyString.fromInterned("="))._add(var1.getname("_opS"))._add(PyString.fromInterned("(?P<encoding>'[A-Za-z][-A-Za-z0-9._]*'|\"[A-Za-z][-A-Za-z0-9._]*\"))?(?:"))._add(var1.getname("_S"))._add(PyString.fromInterned("standalone"))._add(var1.getname("_opS"))._add(PyString.fromInterned("="))._add(var1.getname("_opS"))._add(PyString.fromInterned("(?P<standalone>'(?:yes|no)'|\"(?:yes|no)\"))?"))._add(var1.getname("_opS"))._add(PyString.fromInterned("\\?>")));
      var1.setlocal("xmldecl", var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("<\\?(?P<proc>")._add(var1.getname("_Name"))._add(PyString.fromInterned(")"))._add(var1.getname("_opS")));
      var1.setlocal("procopen", var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_opS")._add(PyString.fromInterned("\\?>")));
      var1.setlocal("procclose", var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!--"));
      var1.setlocal("commentopen", var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-->"));
      var1.setlocal("commentclose", var3);
      var3 = null;
      var1.setline(72);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--"));
      var1.setlocal("doubledash", var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getname("string").__getattr__("maketrans").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" \r\n\t"), (PyObject)PyString.fromInterned("    "));
      var1.setlocal("attrtrans", var3);
      var3 = null;
      var1.setline(76);
      var5 = PyString.fromInterned("[a-zA-Z_][-a-zA-Z0-9._]*");
      var1.setlocal("_NCName", var5);
      var3 = null;
      var1.setline(77);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, var1.getname("_NCName")._add(PyString.fromInterned("$")));
      var1.setlocal("ncname", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("(?:(?P<prefix>")._add(var1.getname("_NCName"))._add(PyString.fromInterned("):)?(?P<local>"))._add(var1.getname("_NCName"))._add(PyString.fromInterned(")$")));
      var1.setlocal("qname", var3);
      var3 = null;
      var1.setline(81);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("xmlns(?::(?P<ncname>")._add(var1.getname("_NCName"))._add(PyString.fromInterned("))?$")));
      var1.setlocal("xmlns", var3);
      var3 = null;
      var1.setline(91);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("XMLParser", var6, XMLParser$2);
      var1.setlocal("XMLParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(805);
      var6 = new PyObject[]{var1.getname("XMLParser")};
      var4 = Py.makeClass("TestXMLParser", var6, TestXMLParser$38);
      var1.setlocal("TestXMLParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(874);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test$53, (PyObject)null);
      var1.setlocal("test", var7);
      var3 = null;
      var1.setline(929);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(930);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      return var1.getf_locals();
   }

   public PyObject XMLParser$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(92);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("attributes", var3);
      var3 = null;
      var1.setline(93);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("elements", var3);
      var3 = null;
      var1.setline(96);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal("_XMLParser__accept_unquoted_attributes", var4);
      var3 = null;
      var1.setline(97);
      var4 = Py.newInteger(0);
      var1.setlocal("_XMLParser__accept_missing_endtag_name", var4);
      var3 = null;
      var1.setline(98);
      var4 = Py.newInteger(0);
      var1.setlocal("_XMLParser__map_case", var4);
      var3 = null;
      var1.setline(99);
      var4 = Py.newInteger(0);
      var1.setlocal("_XMLParser__accept_utf8", var4);
      var3 = null;
      var1.setline(100);
      var4 = Py.newInteger(1);
      var1.setlocal("_XMLParser__translate_attribute_references", var4);
      var3 = null;
      var1.setline(103);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(117);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _XMLParser__fixelements$4, (PyObject)null);
      var1.setlocal("_XMLParser__fixelements", var6);
      var3 = null;
      var1.setline(123);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _XMLParser__fixclass$5, (PyObject)null);
      var1.setlocal("_XMLParser__fixclass", var6);
      var3 = null;
      var1.setline(128);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _XMLParser__fixdict$6, (PyObject)null);
      var1.setlocal("_XMLParser__fixdict", var6);
      var3 = null;
      var1.setline(142);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, reset$7, (PyObject)null);
      var1.setlocal("reset", var6);
      var3 = null;
      var1.setline(159);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, setnomoretags$8, (PyObject)null);
      var1.setlocal("setnomoretags", var6);
      var3 = null;
      var1.setline(163);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, setliteral$9, (PyObject)null);
      var1.setlocal("setliteral", var6);
      var3 = null;
      var1.setline(170);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, feed$10, (PyObject)null);
      var1.setlocal("feed", var6);
      var3 = null;
      var1.setline(175);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, close$11, (PyObject)null);
      var1.setlocal("close", var6);
      var3 = null;
      var1.setline(183);
      var5 = new PyObject[]{Py.newInteger(1)};
      var6 = new PyFunction(var1.f_globals, var5, translate_references$12, (PyObject)null);
      var1.setlocal("translate_references", var6);
      var3 = null;
      var1.setline(233);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getnamespace$13, (PyObject)null);
      var1.setlocal("getnamespace", var6);
      var3 = null;
      var1.setline(242);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, goahead$14, (PyObject)null);
      var1.setlocal("goahead", var6);
      var3 = null;
      var1.setline(426);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_comment$15, (PyObject)null);
      var1.setlocal("parse_comment", var6);
      var3 = null;
      var1.setline(444);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_doctype$16, (PyObject)null);
      var1.setlocal("parse_doctype", var6);
      var3 = null;
      var1.setline(492);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_cdata$17, (PyObject)null);
      var1.setlocal("parse_cdata", var6);
      var3 = null;
      var1.setline(507);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("ns"), var1.getname("None"), PyString.fromInterned("src"), var1.getname("None"), PyString.fromInterned("prefix"), var1.getname("None")});
      var1.setlocal("_XMLParser__xml_namespace_attributes", var3);
      var3 = null;
      var1.setline(509);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_proc$18, (PyObject)null);
      var1.setlocal("parse_proc", var6);
      var3 = null;
      var1.setline(554);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_attributes$19, (PyObject)null);
      var1.setlocal("parse_attributes", var6);
      var3 = null;
      var1.setline(591);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_starttag$20, (PyObject)null);
      var1.setlocal("parse_starttag", var6);
      var3 = null;
      var1.setline(673);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_endtag$21, (PyObject)null);
      var1.setlocal("parse_endtag", var6);
      var3 = null;
      var1.setline(702);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finish_starttag$22, (PyObject)null);
      var1.setlocal("finish_starttag", var6);
      var3 = null;
      var1.setline(709);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finish_endtag$23, (PyObject)null);
      var1.setlocal("finish_endtag", var6);
      var3 = null;
      var1.setline(739);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_xml$24, (PyObject)null);
      var1.setlocal("handle_xml", var6);
      var3 = null;
      var1.setline(743);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_doctype$25, (PyObject)null);
      var1.setlocal("handle_doctype", var6);
      var3 = null;
      var1.setline(747);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_starttag$26, (PyObject)null);
      var1.setlocal("handle_starttag", var6);
      var3 = null;
      var1.setline(751);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_endtag$27, (PyObject)null);
      var1.setlocal("handle_endtag", var6);
      var3 = null;
      var1.setline(755);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_charref$28, (PyObject)null);
      var1.setlocal("handle_charref", var6);
      var3 = null;
      var1.setline(770);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("lt"), PyString.fromInterned("&#60;"), PyString.fromInterned("gt"), PyString.fromInterned("&#62;"), PyString.fromInterned("amp"), PyString.fromInterned("&#38;"), PyString.fromInterned("quot"), PyString.fromInterned("&#34;"), PyString.fromInterned("apos"), PyString.fromInterned("&#39;")});
      var1.setlocal("entitydefs", var3);
      var3 = null;
      var1.setline(778);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_data$29, (PyObject)null);
      var1.setlocal("handle_data", var6);
      var3 = null;
      var1.setline(782);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_cdata$30, (PyObject)null);
      var1.setlocal("handle_cdata", var6);
      var3 = null;
      var1.setline(786);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_comment$31, (PyObject)null);
      var1.setlocal("handle_comment", var6);
      var3 = null;
      var1.setline(790);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_proc$32, (PyObject)null);
      var1.setlocal("handle_proc", var6);
      var3 = null;
      var1.setline(794);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, syntax_error$33, (PyObject)null);
      var1.setlocal("syntax_error", var6);
      var3 = null;
      var1.setline(798);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unknown_starttag$34, (PyObject)null);
      var1.setlocal("unknown_starttag", var6);
      var3 = null;
      var1.setline(799);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unknown_endtag$35, (PyObject)null);
      var1.setlocal("unknown_endtag", var6);
      var3 = null;
      var1.setline(800);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unknown_charref$36, (PyObject)null);
      var1.setlocal("unknown_charref", var6);
      var3 = null;
      var1.setline(801);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unknown_entityref$37, (PyObject)null);
      var1.setlocal("unknown_entityref", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_XMLParser__fixed", var3);
      var3 = null;
      var1.setline(105);
      PyString var4 = PyString.fromInterned("accept_unquoted_attributes");
      PyObject var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(106);
         var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("accept_unquoted_attributes"));
         var1.getlocal(0).__setattr__("_XMLParser__accept_unquoted_attributes", var5);
         var3 = null;
      }

      var1.setline(107);
      var4 = PyString.fromInterned("accept_missing_endtag_name");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(108);
         var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("accept_missing_endtag_name"));
         var1.getlocal(0).__setattr__("_XMLParser__accept_missing_endtag_name", var5);
         var3 = null;
      }

      var1.setline(109);
      var4 = PyString.fromInterned("map_case");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(110);
         var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("map_case"));
         var1.getlocal(0).__setattr__("_XMLParser__map_case", var5);
         var3 = null;
      }

      var1.setline(111);
      var4 = PyString.fromInterned("accept_utf8");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(112);
         var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("accept_utf8"));
         var1.getlocal(0).__setattr__("_XMLParser__accept_utf8", var5);
         var3 = null;
      }

      var1.setline(113);
      var4 = PyString.fromInterned("translate_attribute_references");
      var10000 = var4._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("translate_attribute_references"));
         var1.getlocal(0).__setattr__("_XMLParser__translate_attribute_references", var5);
         var3 = null;
      }

      var1.setline(115);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _XMLParser__fixelements$4(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_XMLParser__fixed", var3);
      var3 = null;
      var1.setline(119);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"elements", var4);
      var3 = null;
      var1.setline(120);
      var1.getlocal(0).__getattr__("_XMLParser__fixdict").__call__(var2, var1.getlocal(0).__getattr__("__dict__"));
      var1.setline(121);
      var1.getlocal(0).__getattr__("_XMLParser__fixclass").__call__(var2, var1.getlocal(0).__getattr__("__class__"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _XMLParser__fixclass$5(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      var1.getlocal(0).__getattr__("_XMLParser__fixdict").__call__(var2, var1.getlocal(1).__getattr__("__dict__"));
      var1.setline(125);
      PyObject var3 = var1.getlocal(1).__getattr__("__bases__").__iter__();

      while(true) {
         var1.setline(125);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(126);
         var1.getlocal(0).__getattr__("_XMLParser__fixclass").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject _XMLParser__fixdict$6(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = var1.getlocal(1).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(129);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(130);
         PyObject var5 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
         PyObject var10000 = var5._eq(PyString.fromInterned("start_"));
         var5 = null;
         PyObject[] var6;
         PyObject var7;
         PyTuple var8;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var5 = var1.getlocal(2).__getslice__(Py.newInteger(6), (PyObject)null, (PyObject)null);
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(132);
            var5 = var1.getlocal(0).__getattr__("elements").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")})));
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
            var1.setline(133);
            var5 = var1.getlocal(4);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(134);
               var8 = new PyTuple(new PyObject[]{var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)), var1.getlocal(5)});
               var1.getlocal(0).__getattr__("elements").__setitem__((PyObject)var1.getlocal(3), var8);
               var5 = null;
            }
         } else {
            var1.setline(135);
            var5 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
            var10000 = var5._eq(PyString.fromInterned("end_"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(136);
               var5 = var1.getlocal(2).__getslice__(Py.newInteger(4), (PyObject)null, (PyObject)null);
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(137);
               var5 = var1.getlocal(0).__getattr__("elements").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")})));
               var6 = Py.unpackSequence(var5, 2);
               var7 = var6[0];
               var1.setlocal(4, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(5, var7);
               var7 = null;
               var5 = null;
               var1.setline(138);
               var5 = var1.getlocal(5);
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(139);
                  var8 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2))});
                  var1.getlocal(0).__getattr__("elements").__setitem__((PyObject)var1.getlocal(3), var8);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject reset$7(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"rawdata", var3);
      var3 = null;
      var1.setline(144);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var4);
      var3 = null;
      var1.setline(145);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"nomoretags", var5);
      var3 = null;
      var1.setline(146);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"literal", var5);
      var3 = null;
      var1.setline(147);
      var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"lineno", var5);
      var3 = null;
      var1.setline(148);
      var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_XMLParser__at_start", var5);
      var3 = null;
      var1.setline(149);
      PyObject var6 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_XMLParser__seen_doctype", var6);
      var3 = null;
      var1.setline(150);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_XMLParser__seen_starttag", var5);
      var3 = null;
      var1.setline(151);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_XMLParser__use_namespaces", var5);
      var3 = null;
      var1.setline(152);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("xml"), var1.getglobal("None")});
      var1.getlocal(0).__setattr__((String)"_XMLParser__namespaces", var7);
      var3 = null;
      var1.setline(155);
      var6 = var1.getlocal(0).__getattr__("elements");
      PyObject var10000 = var6._is(var1.getglobal("XMLParser").__getattr__("elements"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(156);
         var1.getlocal(0).__getattr__("_XMLParser__fixelements").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setnomoretags$8(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"nomoretags", var3);
      var1.getlocal(0).__setattr__((String)"literal", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setliteral$9(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"literal", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$10(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("rawdata", var3);
      var3 = null;
      var1.setline(172);
      var1.getlocal(0).__getattr__("goahead").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$11(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      var1.getlocal(0).__getattr__("goahead").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(177);
      if (var1.getlocal(0).__getattr__("_XMLParser__fixed").__nonzero__()) {
         var1.setline(178);
         PyInteger var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_XMLParser__fixed", var3);
         var3 = null;
         var1.setline(180);
         var1.getlocal(0).__delattr__("elements");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject translate_references$12(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_XMLParser__translate_attribute_references").__not__().__nonzero__()) {
         var1.setline(185);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(186);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(3, var4);
         var4 = null;

         while(true) {
            var1.setline(187);
            if (!Py.newInteger(1).__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(188);
            PyObject var5 = var1.getglobal("amp").__getattr__("search").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.setlocal(4, var5);
            var4 = null;
            var1.setline(189);
            var5 = var1.getlocal(4);
            PyObject var10000 = var5._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(190);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(191);
            var5 = var1.getlocal(4).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(5, var5);
            var4 = null;
            var1.setline(192);
            var5 = var1.getglobal("ref").__getattr__("match").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            var1.setlocal(4, var5);
            var4 = null;
            var1.setline(193);
            var5 = var1.getlocal(4);
            var10000 = var5._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(194);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bogus `&'"));
               var1.setline(195);
               var5 = var1.getlocal(5)._add(Py.newInteger(1));
               var1.setlocal(3, var5);
               var4 = null;
            } else {
               var1.setline(197);
               var5 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setlocal(3, var5);
               var4 = null;
               var1.setline(198);
               var5 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               var1.setlocal(6, var5);
               var4 = null;
               var1.setline(199);
               var4 = Py.newInteger(0);
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(200);
               var5 = var1.getlocal(6).__getitem__(Py.newInteger(0));
               var10000 = var5._eq(PyString.fromInterned("#"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(201);
                  var5 = var1.getlocal(6).__getitem__(Py.newInteger(1));
                  var10000 = var5._eq(PyString.fromInterned("x"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(202);
                     var5 = var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(16)));
                     var1.setlocal(6, var5);
                     var4 = null;
                  } else {
                     var1.setline(204);
                     var5 = var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(6).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)));
                     var1.setlocal(6, var5);
                     var4 = null;
                  }

                  var1.setline(205);
                  var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                  var10000 = var5._ne(PyString.fromInterned(";"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(206);
                     var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("`;' missing after char reference"));
                     var1.setline(207);
                     var5 = var1.getlocal(3)._sub(Py.newInteger(1));
                     var1.setlocal(3, var5);
                     var4 = null;
                  }
               } else {
                  var1.setline(208);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(209);
                     var5 = var1.getlocal(6);
                     var10000 = var5._in(var1.getlocal(0).__getattr__("entitydefs"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(210);
                        var5 = var1.getlocal(0).__getattr__("entitydefs").__getitem__(var1.getlocal(6));
                        var1.setlocal(6, var5);
                        var4 = null;
                        var1.setline(211);
                        var4 = Py.newInteger(1);
                        var1.setlocal(7, var4);
                        var4 = null;
                     } else {
                        var1.setline(212);
                        var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                        var10000 = var5._ne(PyString.fromInterned(";"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(213);
                           var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bogus `&'"));
                           var1.setline(214);
                           var5 = var1.getlocal(5)._add(Py.newInteger(1));
                           var1.setlocal(3, var5);
                           var4 = null;
                           continue;
                        }

                        var1.setline(217);
                        var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("reference to unknown entity `&%s;'")._mod(var1.getlocal(6)));
                        var1.setline(218);
                        var5 = PyString.fromInterned("&")._add(var1.getlocal(6))._add(PyString.fromInterned(";"));
                        var1.setlocal(6, var5);
                        var4 = null;
                     }
                  } else {
                     var1.setline(219);
                     var5 = var1.getlocal(1).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                     var10000 = var5._ne(PyString.fromInterned(";"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(220);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bogus `&'"));
                        var1.setline(221);
                        var5 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(3, var5);
                        var4 = null;
                        continue;
                     }
                  }
               }

               var1.setline(226);
               var5 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null)._add(var1.getlocal(6))._add(var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
               var1.setlocal(1, var5);
               var4 = null;
               var1.setline(227);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(228);
                  var5 = var1.getlocal(5);
                  var1.setlocal(3, var5);
                  var4 = null;
               } else {
                  var1.setline(230);
                  var5 = var1.getlocal(5)._add(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
                  var1.setlocal(3, var5);
                  var4 = null;
               }
            }
         }
      }
   }

   public PyObject getnamespace$13(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(235);
      PyObject var7 = var1.getlocal(0).__getattr__("stack").__iter__();

      while(true) {
         var1.setline(235);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(237);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(236);
         var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject goahead$14(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(244);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(245);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var10000;
      PyObject[] var4;
      while(true) {
         var1.setline(246);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(247);
         var3 = var1.getlocal(3);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(248);
            var6 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_XMLParser__at_start", var6);
            var3 = null;
         }

         var1.setline(249);
         if (var1.getlocal(0).__getattr__("nomoretags").__nonzero__()) {
            var1.setline(250);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(4), (PyObject)null);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(251);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
            var1.setline(252);
            var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
            var1.getlocal(0).__setattr__("lineno", var3);
            var3 = null;
            var1.setline(253);
            var3 = var1.getlocal(4);
            var1.setlocal(3, var3);
            var3 = null;
            break;
         }

         var1.setline(255);
         var3 = var1.getglobal("interesting").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(256);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(257);
            var3 = var1.getlocal(6).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(259);
            var3 = var1.getlocal(4);
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(260);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(261);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(7), (PyObject)null);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(262);
            var10000 = var1.getlocal(0).__getattr__("_XMLParser__at_start");
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("space").__getattr__("match").__call__(var2, var1.getlocal(5));
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(263);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal data at start of file"));
            }

            var1.setline(264);
            var6 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_XMLParser__at_start", var6);
            var3 = null;
            var1.setline(265);
            var10000 = var1.getlocal(0).__getattr__("stack").__not__();
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("space").__getattr__("match").__call__(var2, var1.getlocal(5));
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(266);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data not in content"));
            }

            var1.setline(267);
            var10000 = var1.getlocal(0).__getattr__("_XMLParser__accept_utf8").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("illegal").__getattr__("search").__call__(var2, var1.getlocal(5));
            }

            if (var10000.__nonzero__()) {
               var1.setline(268);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal character in content"));
            }

            var1.setline(269);
            var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
            var1.setline(270);
            var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
            var1.getlocal(0).__setattr__("lineno", var3);
            var3 = null;
         }

         var1.setline(271);
         var3 = var1.getlocal(7);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(272);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(273);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var10000 = var3._eq(PyString.fromInterned("<"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(274);
            if (var1.getglobal("starttagopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
               var1.setline(275);
               if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                  var1.setline(276);
                  var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(277);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
                  var1.setline(278);
                  var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                  var1.getlocal(0).__setattr__("lineno", var3);
                  var3 = null;
                  var1.setline(279);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(281);
                  var3 = var1.getlocal(0).__getattr__("parse_starttag").__call__(var2, var1.getlocal(3));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(282);
                  var3 = var1.getlocal(8);
                  var10000 = var3._lt(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(283);
                  var6 = Py.newInteger(1);
                  var1.getlocal(0).__setattr__((String)"_XMLParser__seen_starttag", var6);
                  var3 = null;
                  var1.setline(284);
                  var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                  var1.getlocal(0).__setattr__("lineno", var3);
                  var3 = null;
                  var1.setline(285);
                  var3 = var1.getlocal(8);
                  var1.setlocal(3, var3);
                  var3 = null;
               }
            } else {
               var1.setline(287);
               if (var1.getglobal("endtagopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
                  var1.setline(288);
                  var3 = var1.getlocal(0).__getattr__("parse_endtag").__call__(var2, var1.getlocal(3));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(289);
                  var3 = var1.getlocal(8);
                  var10000 = var3._lt(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(290);
                  var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                  var1.getlocal(0).__setattr__("lineno", var3);
                  var3 = null;
                  var1.setline(291);
                  var3 = var1.getlocal(8);
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(293);
                  if (var1.getglobal("commentopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
                     var1.setline(294);
                     if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                        var1.setline(295);
                        var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                        var1.setlocal(5, var3);
                        var3 = null;
                        var1.setline(296);
                        var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
                        var1.setline(297);
                        var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                        var1.getlocal(0).__setattr__("lineno", var3);
                        var3 = null;
                        var1.setline(298);
                        var3 = var1.getlocal(3)._add(Py.newInteger(1));
                        var1.setlocal(3, var3);
                        var3 = null;
                     } else {
                        var1.setline(300);
                        var3 = var1.getlocal(0).__getattr__("parse_comment").__call__(var2, var1.getlocal(3));
                        var1.setlocal(8, var3);
                        var3 = null;
                        var1.setline(301);
                        var3 = var1.getlocal(8);
                        var10000 = var3._lt(Py.newInteger(0));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(302);
                        var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                        var1.getlocal(0).__setattr__("lineno", var3);
                        var3 = null;
                        var1.setline(303);
                        var3 = var1.getlocal(8);
                        var1.setlocal(3, var3);
                        var3 = null;
                     }
                  } else {
                     var1.setline(305);
                     if (var1.getglobal("cdataopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
                        var1.setline(306);
                        var3 = var1.getlocal(0).__getattr__("parse_cdata").__call__(var2, var1.getlocal(3));
                        var1.setlocal(8, var3);
                        var3 = null;
                        var1.setline(307);
                        var3 = var1.getlocal(8);
                        var10000 = var3._lt(Py.newInteger(0));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(308);
                        var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                        var1.getlocal(0).__setattr__("lineno", var3);
                        var3 = null;
                        var1.setline(309);
                        var3 = var1.getlocal(8);
                        var1.setlocal(3, var3);
                        var3 = null;
                     } else {
                        var1.setline(311);
                        var3 = var1.getglobal("xmldecl").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                        var1.setlocal(6, var3);
                        var3 = null;
                        var1.setline(312);
                        if (var1.getlocal(6).__nonzero__()) {
                           var1.setline(313);
                           if (var1.getlocal(0).__getattr__("_XMLParser__at_start").__not__().__nonzero__()) {
                              var1.setline(314);
                              var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<?xml?> declaration not at start of document"));
                           }

                           var1.setline(315);
                           var3 = var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, PyString.fromInterned("version"), (PyObject)PyString.fromInterned("encoding"), (PyObject)PyString.fromInterned("standalone"));
                           var4 = Py.unpackSequence(var3, 3);
                           PyObject var5 = var4[0];
                           var1.setlocal(9, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(10, var5);
                           var5 = null;
                           var5 = var4[2];
                           var1.setlocal(11, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(318);
                           var3 = var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                           var10000 = var3._ne(PyString.fromInterned("1.0"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(319);
                              throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("only XML version 1.0 supported")));
                           }

                           var1.setline(320);
                           if (var1.getlocal(10).__nonzero__()) {
                              var1.setline(320);
                              var3 = var1.getlocal(10).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                              var1.setlocal(10, var3);
                              var3 = null;
                           }

                           var1.setline(321);
                           if (var1.getlocal(11).__nonzero__()) {
                              var1.setline(321);
                              var3 = var1.getlocal(11).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                              var1.setlocal(11, var3);
                              var3 = null;
                           }

                           var1.setline(322);
                           var1.getlocal(0).__getattr__("handle_xml").__call__(var2, var1.getlocal(10), var1.getlocal(11));
                           var1.setline(323);
                           var3 = var1.getlocal(6).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                           var1.setlocal(3, var3);
                           var3 = null;
                        } else {
                           var1.setline(325);
                           var3 = var1.getglobal("procopen").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                           var1.setlocal(6, var3);
                           var3 = null;
                           var1.setline(326);
                           if (var1.getlocal(6).__nonzero__()) {
                              var1.setline(327);
                              var3 = var1.getlocal(0).__getattr__("parse_proc").__call__(var2, var1.getlocal(3));
                              var1.setlocal(8, var3);
                              var3 = null;
                              var1.setline(328);
                              var3 = var1.getlocal(8);
                              var10000 = var3._lt(Py.newInteger(0));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 break;
                              }

                              var1.setline(329);
                              var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                              var1.getlocal(0).__setattr__("lineno", var3);
                              var3 = null;
                              var1.setline(330);
                              var3 = var1.getlocal(8);
                              var1.setlocal(3, var3);
                              var3 = null;
                           } else {
                              var1.setline(332);
                              var3 = var1.getglobal("doctype").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                              var1.setlocal(6, var3);
                              var3 = null;
                              var1.setline(333);
                              if (!var1.getlocal(6).__nonzero__()) {
                                 break;
                              }

                              var1.setline(334);
                              if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                                 var1.setline(335);
                                 var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                                 var1.setlocal(5, var3);
                                 var3 = null;
                                 var1.setline(336);
                                 var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
                                 var1.setline(337);
                                 var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                                 var1.getlocal(0).__setattr__("lineno", var3);
                                 var3 = null;
                                 var1.setline(338);
                                 var3 = var1.getlocal(3)._add(Py.newInteger(1));
                                 var1.setlocal(3, var3);
                                 var3 = null;
                              } else {
                                 var1.setline(340);
                                 if (var1.getlocal(0).__getattr__("_XMLParser__seen_doctype").__nonzero__()) {
                                    var1.setline(341);
                                    var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multiple DOCTYPE elements"));
                                 }

                                 var1.setline(342);
                                 if (var1.getlocal(0).__getattr__("_XMLParser__seen_starttag").__nonzero__()) {
                                    var1.setline(343);
                                    var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DOCTYPE not at beginning of document"));
                                 }

                                 var1.setline(344);
                                 var3 = var1.getlocal(0).__getattr__("parse_doctype").__call__(var2, var1.getlocal(6));
                                 var1.setlocal(8, var3);
                                 var3 = null;
                                 var1.setline(345);
                                 var3 = var1.getlocal(8);
                                 var10000 = var3._lt(Py.newInteger(0));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(346);
                                 var3 = var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
                                 var1.getlocal(0).__setattr__("_XMLParser__seen_doctype", var3);
                                 var3 = null;
                                 var1.setline(347);
                                 if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
                                    var1.setline(348);
                                    var3 = var1.getlocal(0).__getattr__("_XMLParser__seen_doctype").__getattr__("lower").__call__(var2);
                                    var1.getlocal(0).__setattr__("_XMLParser__seen_doctype", var3);
                                    var3 = null;
                                 }

                                 var1.setline(349);
                                 var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(8), (PyObject)null).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                                 var1.getlocal(0).__setattr__("lineno", var3);
                                 var3 = null;
                                 var1.setline(350);
                                 var3 = var1.getlocal(8);
                                 var1.setlocal(3, var3);
                                 var3 = null;
                              }
                           }
                        }
                     }
                  }
               }
            }
         } else {
            var1.setline(352);
            var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
            var10000 = var3._eq(PyString.fromInterned("&"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(353);
               if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                  var1.setline(354);
                  var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(355);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
                  var1.setline(356);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(358);
                  var3 = var1.getglobal("charref").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(359);
                  var3 = var1.getlocal(6);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(360);
                     var3 = var1.getlocal(6).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(361);
                     var3 = var1.getlocal(2).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                     var10000 = var3._ne(PyString.fromInterned(";"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(362);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("`;' missing in charref"));
                        var1.setline(363);
                        var3 = var1.getlocal(3)._sub(Py.newInteger(1));
                        var1.setlocal(3, var3);
                        var3 = null;
                     }

                     var1.setline(364);
                     if (var1.getlocal(0).__getattr__("stack").__not__().__nonzero__()) {
                        var1.setline(365);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data not in content"));
                     }

                     var1.setline(366);
                     var1.getlocal(0).__getattr__("handle_charref").__call__(var2, var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("char")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
                     var1.setline(367);
                     var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                     var1.getlocal(0).__setattr__("lineno", var3);
                     var3 = null;
                  } else {
                     var1.setline(369);
                     var3 = var1.getglobal("entityref").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(370);
                     var3 = var1.getlocal(6);
                     var10000 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(371);
                     var3 = var1.getlocal(6).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(372);
                     var3 = var1.getlocal(2).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                     var10000 = var3._ne(PyString.fromInterned(";"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(373);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("`;' missing in entityref"));
                        var1.setline(374);
                        var3 = var1.getlocal(3)._sub(Py.newInteger(1));
                        var1.setlocal(3, var3);
                        var3 = null;
                     }

                     var1.setline(375);
                     var3 = var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
                     var1.setlocal(12, var3);
                     var3 = null;
                     var1.setline(376);
                     if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
                        var1.setline(377);
                        var3 = var1.getlocal(12).__getattr__("lower").__call__(var2);
                        var1.setlocal(12, var3);
                        var3 = null;
                     }

                     var1.setline(378);
                     var3 = var1.getlocal(12);
                     var10000 = var3._in(var1.getlocal(0).__getattr__("entitydefs"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(379);
                        var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(6).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null)._add(var1.getlocal(0).__getattr__("entitydefs").__getitem__(var1.getlocal(12)))._add(var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
                        var1.getlocal(0).__setattr__("rawdata", var3);
                        var1.setlocal(2, var3);
                        var1.setline(380);
                        var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                        var1.setlocal(4, var3);
                        var3 = null;
                        var1.setline(381);
                        var3 = var1.getlocal(6).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                        var1.setlocal(3, var3);
                        var3 = null;
                     } else {
                        var1.setline(383);
                        var1.getlocal(0).__getattr__("unknown_entityref").__call__(var2, var1.getlocal(12));
                     }

                     var1.setline(384);
                     var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
                     var1.getlocal(0).__setattr__("lineno", var3);
                     var3 = null;
                  }
               }
            } else {
               var1.setline(386);
               var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
               var10000 = var3._eq(PyString.fromInterned("]"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(400);
                  throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("neither < nor & ??")));
               }

               var1.setline(387);
               if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
                  var1.setline(388);
                  var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(389);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
                  var1.setline(390);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(392);
                  var3 = var1.getlocal(4)._sub(var1.getlocal(3));
                  var10000 = var3._lt(Py.newInteger(3));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(394);
                  if (var1.getglobal("cdataclose").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
                     var1.setline(395);
                     var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bogus `]]>'"));
                  }

                  var1.setline(396);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3)));
                  var1.setline(397);
                  var3 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(405);
      var3 = var1.getlocal(3);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(406);
         var6 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_XMLParser__at_start", var6);
         var3 = null;
      }

      var1.setline(407);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(4));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(408);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(409);
         var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("bogus `%s'")._mod(var1.getlocal(5)));
         var1.setline(410);
         var10000 = var1.getlocal(0).__getattr__("_XMLParser__accept_utf8").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("illegal").__getattr__("search").__call__(var2, var1.getlocal(5));
         }

         if (var10000.__nonzero__()) {
            var1.setline(411);
            var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal character in content"));
         }

         var1.setline(412);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(5));
         var1.setline(413);
         var3 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")));
         var1.getlocal(0).__setattr__("lineno", var3);
         var3 = null;
         var1.setline(414);
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("rawdata", var3);
         var3 = null;
         var1.setline(415);
         var3 = var1.getlocal(0).__getattr__("goahead").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(416);
         PyObject var7 = var1.getlocal(2).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("rawdata", var7);
         var4 = null;
         var1.setline(417);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(418);
            if (var1.getlocal(0).__getattr__("_XMLParser__seen_starttag").__not__().__nonzero__()) {
               var1.setline(419);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no elements in file"));
            }

            var1.setline(420);
            if (var1.getlocal(0).__getattr__("stack").__nonzero__()) {
               var1.setline(421);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing end tags"));

               while(true) {
                  var1.setline(422);
                  if (!var1.getlocal(0).__getattr__("stack").__nonzero__()) {
                     break;
                  }

                  var1.setline(423);
                  var1.getlocal(0).__getattr__("finish_endtag").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0)));
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject parse_comment$15(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(428);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(4)), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("<!--"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(429);
         throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to handle_comment")));
      } else {
         var1.setline(430);
         var3 = var1.getglobal("commentclose").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(4)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(431);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(432);
            PyInteger var5 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(433);
            if (var1.getglobal("doubledash").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(4)), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))).__nonzero__()) {
               var1.setline(434);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("`--' inside comment"));
            }

            var1.setline(435);
            PyObject var4 = var1.getlocal(2).__getitem__(var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))._sub(Py.newInteger(1)));
            var10000 = var4._eq(PyString.fromInterned("-"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(436);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("comment cannot end in three dashes"));
            }

            var1.setline(437);
            var10000 = var1.getlocal(0).__getattr__("_XMLParser__accept_utf8").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("illegal").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(4)), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
            }

            if (var10000.__nonzero__()) {
               var1.setline(439);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal character in comment"));
            }

            var1.setline(440);
            var1.getlocal(0).__getattr__("handle_comment").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(4)), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null));
            var1.setline(441);
            var3 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject parse_doctype$16(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(446);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(447);
      var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(448);
      if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
         var1.setline(449);
         var3 = var1.getlocal(4).__getattr__("lower").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(450);
      var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pubid"), (PyObject)PyString.fromInterned("syslit"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(451);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(452);
         var3 = var1.getlocal(5).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(453);
         var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(5).__getattr__("split").__call__(var2));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(454);
      var3 = var1.getlocal(6);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(454);
         var3 = var1.getlocal(6).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(455);
      var3 = var1.getlocal(1).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(7, var3);
      var1.setlocal(8, var3);
      var1.setline(456);
      var3 = var1.getlocal(8);
      var10000 = var3._ge(var1.getlocal(3));
      var3 = null;
      PyInteger var8;
      if (var10000.__nonzero__()) {
         var1.setline(457);
         var8 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(458);
         PyObject var6 = var1.getlocal(2).__getitem__(var1.getlocal(8));
         var10000 = var6._eq(PyString.fromInterned("["));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(459);
            PyInteger var7 = Py.newInteger(0);
            var1.setlocal(9, var7);
            var4 = null;
            var1.setline(460);
            var6 = var1.getlocal(8)._add(Py.newInteger(1));
            var1.setlocal(8, var6);
            var4 = null;
            var1.setline(461);
            var7 = Py.newInteger(0);
            var1.setlocal(10, var7);
            var1.setlocal(11, var7);

            while(true) {
               var1.setline(462);
               var6 = var1.getlocal(8);
               var10000 = var6._lt(var1.getlocal(3));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(463);
               var6 = var1.getlocal(2).__getitem__(var1.getlocal(8));
               var1.setlocal(12, var6);
               var4 = null;
               var1.setline(464);
               var10000 = var1.getlocal(11).__not__();
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(12);
                  var10000 = var6._eq(PyString.fromInterned("\""));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(465);
                  var6 = var1.getlocal(10).__not__();
                  var1.setlocal(10, var6);
                  var4 = null;
               } else {
                  var1.setline(466);
                  var10000 = var1.getlocal(10).__not__();
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(12);
                     var10000 = var6._eq(PyString.fromInterned("'"));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(467);
                     var6 = var1.getlocal(11).__not__();
                     var1.setlocal(11, var6);
                     var4 = null;
                  } else {
                     var1.setline(468);
                     var10000 = var1.getlocal(11);
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getlocal(10);
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(469);
                     } else {
                        var1.setline(470);
                        var6 = var1.getlocal(9);
                        var10000 = var6._le(Py.newInteger(0));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var6 = var1.getlocal(12);
                           var10000 = var6._eq(PyString.fromInterned("]"));
                           var4 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(471);
                           var6 = var1.getglobal("endbracket").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(8)._add(Py.newInteger(1)));
                           var1.setlocal(1, var6);
                           var4 = null;
                           var1.setline(472);
                           var6 = var1.getlocal(1);
                           var10000 = var6._is(var1.getglobal("None"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(473);
                              var8 = Py.newInteger(-1);
                              var1.f_lasti = -1;
                              return var8;
                           }

                           var1.setline(474);
                           var1.getlocal(0).__getattr__("handle_doctype").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(2).__getslice__(var1.getlocal(7)._add(Py.newInteger(1)), var1.getlocal(8), (PyObject)null));
                           var1.setline(475);
                           var3 = var1.getlocal(1).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setline(476);
                        var6 = var1.getlocal(12);
                        var10000 = var6._eq(PyString.fromInterned("<"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(477);
                           var6 = var1.getlocal(9)._add(Py.newInteger(1));
                           var1.setlocal(9, var6);
                           var4 = null;
                        } else {
                           var1.setline(478);
                           var6 = var1.getlocal(12);
                           var10000 = var6._eq(PyString.fromInterned(">"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(479);
                              var6 = var1.getlocal(9)._sub(Py.newInteger(1));
                              var1.setlocal(9, var6);
                              var4 = null;
                              var1.setline(480);
                              var6 = var1.getlocal(9);
                              var10000 = var6._lt(Py.newInteger(0));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(481);
                                 var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bogus `>' in DOCTYPE"));
                              }
                           }
                        }
                     }
                  }
               }

               var1.setline(482);
               var6 = var1.getlocal(8)._add(Py.newInteger(1));
               var1.setlocal(8, var6);
               var4 = null;
            }
         }

         var1.setline(483);
         var6 = var1.getglobal("endbracketfind").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(8));
         var1.setlocal(1, var6);
         var4 = null;
         var1.setline(484);
         var6 = var1.getlocal(1);
         var10000 = var6._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(485);
            var8 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(486);
            var6 = var1.getglobal("endbracket").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(8));
            var10000 = var6._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(487);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("garbage in DOCTYPE"));
            }

            var1.setline(488);
            var1.getlocal(0).__getattr__("handle_doctype").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getglobal("None"));
            var1.setline(489);
            var3 = var1.getlocal(1).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject parse_cdata$17(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(494);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(9)), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("<![CDATA["));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(495);
         throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_cdata")));
      } else {
         var1.setline(496);
         var3 = var1.getglobal("cdataclose").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(9)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(497);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(498);
            PyInteger var4 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(499);
            var10000 = var1.getlocal(0).__getattr__("_XMLParser__accept_utf8").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("illegal").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(9)), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
            }

            if (var10000.__nonzero__()) {
               var1.setline(501);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal character in CDATA"));
            }

            var1.setline(502);
            if (var1.getlocal(0).__getattr__("stack").__not__().__nonzero__()) {
               var1.setline(503);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CDATA not in content"));
            }

            var1.setline(504);
            var1.getlocal(0).__getattr__("handle_cdata").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(9)), var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), (PyObject)null));
            var1.setline(505);
            var3 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject parse_proc$18(PyFrame var1, ThreadState var2) {
      var1.setline(510);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(511);
      var3 = var1.getglobal("procclose").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(512);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(513);
         PyInteger var8 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(514);
         PyObject var4 = var1.getlocal(3).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(515);
         var10000 = var1.getlocal(0).__getattr__("_XMLParser__accept_utf8").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("illegal").__getattr__("search").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(4));
         }

         if (var10000.__nonzero__()) {
            var1.setline(516);
            var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal character in processing instruction"));
         }

         var1.setline(517);
         var4 = var1.getglobal("tagfind").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(2)));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(518);
         var4 = var1.getlocal(5);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(519);
            throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_proc")));
         } else {
            var1.setline(520);
            var4 = var1.getlocal(5).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(521);
            var4 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(522);
            if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
               var1.setline(523);
               var4 = var1.getlocal(7).__getattr__("lower").__call__(var2);
               var1.setlocal(7, var4);
               var4 = null;
            }

            var1.setline(524);
            var4 = var1.getlocal(7);
            var10000 = var4._eq(PyString.fromInterned("xml:namespace"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(525);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("old-fashioned namespace declaration"));
               var1.setline(526);
               PyInteger var9 = Py.newInteger(-1);
               var1.getlocal(0).__setattr__((String)"_XMLParser__use_namespaces", var9);
               var4 = null;
               var1.setline(530);
               var10000 = var1.getlocal(0).__getattr__("_XMLParser__seen_doctype");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_XMLParser__seen_starttag");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(531);
                  var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xml:namespace declaration too late in document"));
               }

               var1.setline(532);
               var4 = var1.getlocal(0).__getattr__("parse_attributes").__call__(var2, var1.getlocal(7), var1.getlocal(6), var1.getlocal(4));
               PyObject[] var5 = Py.unpackSequence(var4, 3);
               PyObject var6 = var5[0];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(6, var6);
               var6 = null;
               var4 = null;
               var1.setline(533);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(534);
                  var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("namespace declaration inside namespace declaration"));
               }

               var1.setline(535);
               var4 = var1.getlocal(8).__getattr__("keys").__call__(var2).__iter__();

               while(true) {
                  var1.setline(535);
                  PyObject var7 = var4.__iternext__();
                  if (var7 == null) {
                     var1.setline(538);
                     PyString var10 = PyString.fromInterned("ns");
                     var10000 = var10._in(var1.getlocal(8));
                     var4 = null;
                     var10000 = var10000.__not__();
                     if (!var10000.__nonzero__()) {
                        var10 = PyString.fromInterned("prefix");
                        var10000 = var10._in(var1.getlocal(8));
                        var4 = null;
                        var10000 = var10000.__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(539);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xml:namespace without required attributes"));
                     }

                     var1.setline(540);
                     var4 = var1.getlocal(8).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix"));
                     var1.setlocal(11, var4);
                     var4 = null;
                     var1.setline(541);
                     var4 = var1.getglobal("ncname").__getattr__("match").__call__(var2, var1.getlocal(11));
                     var10000 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(542);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xml:namespace illegal prefix value"));
                        var1.setline(543);
                        var3 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(544);
                     var4 = var1.getlocal(11);
                     var10000 = var4._in(var1.getlocal(0).__getattr__("_XMLParser__namespaces"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(545);
                        var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xml:namespace prefix not unique"));
                     }

                     var1.setline(546);
                     var4 = var1.getlocal(8).__getitem__(PyString.fromInterned("ns"));
                     var1.getlocal(0).__getattr__("_XMLParser__namespaces").__setitem__(var1.getlocal(11), var4);
                     var4 = null;
                     break;
                  }

                  var1.setlocal(10, var7);
                  var1.setline(536);
                  var6 = var1.getlocal(10);
                  var10000 = var6._in(var1.getlocal(0).__getattr__("_XMLParser__xml_namespace_attributes"));
                  var6 = null;
                  if (var10000.__not__().__nonzero__()) {
                     var1.setline(537);
                     var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("unknown attribute `%s' in xml:namespace tag")._mod(var1.getlocal(10)));
                  }
               }
            } else {
               var1.setline(548);
               var4 = var1.getlocal(7).__getattr__("lower").__call__(var2);
               var10000 = var4._eq(PyString.fromInterned("xml"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(549);
                  var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal processing instruction target name"));
               }

               var1.setline(550);
               var1.getlocal(0).__getattr__("handle_proc").__call__(var2, var1.getlocal(7), var1.getlocal(2).__getslice__(var1.getlocal(6), var1.getlocal(4), (PyObject)null));
            }

            var1.setline(551);
            var3 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject parse_attributes$19(PyFrame var1, ThreadState var2) {
      var1.setline(555);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(556);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(557);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(6, var6);
      var3 = null;

      while(true) {
         var1.setline(558);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._lt(var1.getlocal(3));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(559);
         var3 = var1.getglobal("attrfind").__getattr__("match").__call__(var2, var1.getlocal(4), var1.getlocal(2));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(560);
         var3 = var1.getlocal(7);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(562);
         var3 = var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"), (PyObject)PyString.fromInterned("value"));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(9, var5);
         var5 = null;
         var3 = null;
         var1.setline(563);
         if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
            var1.setline(564);
            var3 = var1.getlocal(8).__getattr__("lower").__call__(var2);
            var1.setlocal(8, var3);
            var3 = null;
         }

         var1.setline(565);
         var3 = var1.getlocal(7).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(566);
         var3 = var1.getlocal(9);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyString var8;
         if (var10000.__nonzero__()) {
            var1.setline(567);
            var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("no value specified for attribute `%s'")._mod(var1.getlocal(8)));
            var1.setline(568);
            var3 = var1.getlocal(8);
            var1.setlocal(9, var3);
            var3 = null;
         } else {
            var1.setline(569);
            var3 = var1.getlocal(9).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            PyString var10001 = PyString.fromInterned("'");
            var10000 = var3;
            var8 = var10001;
            PyObject var7;
            if ((var7 = var10000._eq(var10001)).__nonzero__()) {
               var7 = var8._eq(var1.getlocal(9).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
            }

            var10000 = var7;
            var3 = null;
            if (!var7.__nonzero__()) {
               var3 = var1.getlocal(9).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
               var10001 = PyString.fromInterned("\"");
               var10000 = var3;
               var8 = var10001;
               if ((var7 = var10000._eq(var10001)).__nonzero__()) {
                  var7 = var8._eq(var1.getlocal(9).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
               }

               var10000 = var7;
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(571);
               var3 = var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
               var1.setlocal(9, var3);
               var3 = null;
            } else {
               var1.setline(572);
               if (var1.getlocal(0).__getattr__("_XMLParser__accept_unquoted_attributes").__not__().__nonzero__()) {
                  var1.setline(573);
                  var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("attribute `%s' value not quoted")._mod(var1.getlocal(8)));
               }
            }
         }

         var1.setline(574);
         var3 = var1.getglobal("xmlns").__getattr__("match").__call__(var2, var1.getlocal(8));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(575);
         var3 = var1.getlocal(7);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(577);
            var3 = var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ncname"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(578);
            var10000 = var1.getlocal(9);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("None");
            }

            var3 = var10000;
            var10000 = var1.getlocal(6);
            Object var9 = var1.getlocal(10);
            if (!((PyObject)var9).__nonzero__()) {
               var9 = PyString.fromInterned("");
            }

            var10000.__setitem__((PyObject)var9, var3);
            var3 = null;
            var1.setline(579);
            if (var1.getlocal(0).__getattr__("_XMLParser__use_namespaces").__not__().__nonzero__()) {
               var1.setline(580);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"))._add(Py.newInteger(1));
               var1.getlocal(0).__setattr__("_XMLParser__use_namespaces", var3);
               var3 = null;
            }
         } else {
            var1.setline(582);
            var8 = PyString.fromInterned("<");
            var10000 = var8._in(var1.getlocal(9));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(583);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("`<' illegal in attribute value"));
            }

            var1.setline(584);
            var3 = var1.getlocal(8);
            var10000 = var3._in(var1.getlocal(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(585);
               var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("attribute `%s' specified twice")._mod(var1.getlocal(8)));
            }

            var1.setline(586);
            var3 = var1.getlocal(9).__getattr__("translate").__call__(var2, var1.getglobal("attrtrans"));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(587);
            var3 = var1.getlocal(0).__getattr__("translate_references").__call__(var2, var1.getlocal(9));
            var1.getlocal(5).__setitem__(var1.getlocal(8), var3);
            var3 = null;
         }
      }

      var1.setline(588);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject parse_starttag$20(PyFrame var1, ThreadState var2) {
      var1.setline(592);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(594);
      var3 = var1.getglobal("endbracketfind").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(595);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(596);
         PyInteger var10 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(597);
         PyObject var4 = var1.getglobal("starttagmatch").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(598);
         var4 = var1.getlocal(4);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var10000 = var4._ne(var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(599);
            var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("garbage in starttag"));
            var1.setline(600);
            var3 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(601);
            var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tagname"));
            var1.setlocal(5, var4);
            var1.setlocal(6, var4);
            var1.setline(602);
            if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
               var1.setline(603);
               var4 = var1.getlocal(5).__getattr__("lower").__call__(var2);
               var1.setlocal(5, var4);
               var1.setlocal(6, var4);
            }

            var1.setline(604);
            var10000 = var1.getlocal(0).__getattr__("_XMLParser__seen_starttag").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_XMLParser__seen_doctype");
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(6);
                  var10000 = var4._ne(var1.getlocal(0).__getattr__("_XMLParser__seen_doctype"));
                  var4 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(606);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("starttag does not match DOCTYPE"));
            }

            var1.setline(607);
            var10000 = var1.getlocal(0).__getattr__("_XMLParser__seen_starttag");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("stack").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(608);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("multiple elements on top level"));
            }

            var1.setline(609);
            var4 = var1.getlocal(4).__getattr__("span").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attrs"));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(610);
            var4 = var1.getlocal(0).__getattr__("parse_attributes").__call__(var2, var1.getlocal(6), var1.getlocal(7), var1.getlocal(8));
            var5 = Py.unpackSequence(var4, 3);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
            var1.setline(611);
            var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(10), var1.getlocal(5)})));
            var1.setline(612);
            if (var1.getlocal(0).__getattr__("_XMLParser__use_namespaces").__nonzero__()) {
               var1.setline(613);
               var4 = var1.getglobal("qname").__getattr__("match").__call__(var2, var1.getlocal(6));
               var1.setlocal(11, var4);
               var4 = null;
            } else {
               var1.setline(615);
               var4 = var1.getglobal("None");
               var1.setlocal(11, var4);
               var4 = null;
            }

            var1.setline(616);
            var4 = var1.getlocal(11);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            PyObject var7;
            PyObject var11;
            PyObject[] var14;
            if (var10000.__nonzero__()) {
               var1.setline(617);
               var4 = var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix"), (PyObject)PyString.fromInterned("local"));
               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(12, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(618);
               var4 = var1.getlocal(12);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(619);
                  PyString var15 = PyString.fromInterned("");
                  var1.setlocal(12, var15);
                  var4 = null;
               }

               var1.setline(620);
               var4 = var1.getglobal("None");
               var1.setlocal(13, var4);
               var4 = null;
               var1.setline(621);
               var4 = var1.getlocal(0).__getattr__("stack").__iter__();

               while(true) {
                  var1.setline(621);
                  var11 = var4.__iternext__();
                  if (var11 == null) {
                     var1.setline(624);
                     var4 = var1.getlocal(13);
                     var10000 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(12);
                        var10000 = var4._ne(PyString.fromInterned(""));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(625);
                        var4 = var1.getlocal(0).__getattr__("_XMLParser__namespaces").__getattr__("get").__call__(var2, var1.getlocal(12));
                        var1.setlocal(13, var4);
                        var4 = null;
                     }

                     var1.setline(626);
                     var4 = var1.getlocal(13);
                     var10000 = var4._isnot(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(627);
                        var4 = var1.getlocal(13)._add(PyString.fromInterned(" "))._add(var1.getlocal(5));
                        var1.setlocal(5, var4);
                        var4 = null;
                     } else {
                        var1.setline(628);
                        var4 = var1.getlocal(12);
                        var10000 = var4._ne(PyString.fromInterned(""));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(629);
                           var4 = var1.getlocal(12)._add(PyString.fromInterned(":"))._add(var1.getlocal(5));
                           var1.setlocal(5, var4);
                           var4 = null;
                        }
                     }

                     var1.setline(630);
                     PyTuple var16 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(10), var1.getlocal(5)});
                     var1.getlocal(0).__getattr__("stack").__setitem__((PyObject)Py.newInteger(-1), var16);
                     var4 = null;
                     break;
                  }

                  var14 = Py.unpackSequence(var11, 3);
                  var7 = var14[0];
                  var1.setlocal(14, var7);
                  var7 = null;
                  var7 = var14[1];
                  var1.setlocal(15, var7);
                  var7 = null;
                  var7 = var14[2];
                  var1.setlocal(16, var7);
                  var7 = null;
                  var1.setline(622);
                  var6 = var1.getlocal(12);
                  var10000 = var6._in(var1.getlocal(15));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(623);
                     var6 = var1.getlocal(15).__getitem__(var1.getlocal(12));
                     var1.setlocal(13, var6);
                     var6 = null;
                  }
               }
            }

            var1.setline(632);
            PyDictionary var17 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(17, var17);
            var4 = null;
            var1.setline(633);
            var4 = var1.getlocal(9).__getattr__("keys").__call__(var2).__iter__();

            while(true) {
               var1.setline(633);
               var11 = var4.__iternext__();
               if (var11 == null) {
                  var1.setline(635);
                  if (var1.getlocal(0).__getattr__("_XMLParser__use_namespaces").__nonzero__()) {
                     var1.setline(636);
                     var17 = new PyDictionary(Py.EmptyObjects);
                     var1.setlocal(19, var17);
                     var4 = null;
                     var1.setline(637);
                     var4 = var1.getlocal(9).__getattr__("items").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(637);
                        var11 = var4.__iternext__();
                        if (var11 == null) {
                           var1.setline(657);
                           var4 = var1.getlocal(19);
                           var1.setlocal(9, var4);
                           var4 = null;
                           break;
                        }

                        var14 = Py.unpackSequence(var11, 2);
                        var7 = var14[0];
                        var1.setlocal(18, var7);
                        var7 = null;
                        var7 = var14[1];
                        var1.setlocal(20, var7);
                        var7 = null;
                        var1.setline(638);
                        var6 = var1.getlocal(18);
                        var1.setlocal(21, var6);
                        var6 = null;
                        var1.setline(639);
                        var6 = var1.getglobal("qname").__getattr__("match").__call__(var2, var1.getlocal(18));
                        var1.setlocal(11, var6);
                        var6 = null;
                        var1.setline(640);
                        var6 = var1.getlocal(11);
                        var10000 = var6._isnot(var1.getglobal("None"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(641);
                           var6 = var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix"), (PyObject)PyString.fromInterned("local"));
                           PyObject[] var13 = Py.unpackSequence(var6, 2);
                           PyObject var8 = var13[0];
                           var1.setlocal(22, var8);
                           var8 = null;
                           var8 = var13[1];
                           var1.setlocal(18, var8);
                           var8 = null;
                           var6 = null;
                           var1.setline(642);
                           if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
                              var1.setline(643);
                              var6 = var1.getlocal(18).__getattr__("lower").__call__(var2);
                              var1.setlocal(18, var6);
                              var6 = null;
                           }

                           var1.setline(644);
                           var6 = var1.getlocal(22);
                           var10000 = var6._isnot(var1.getglobal("None"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(645);
                              var6 = var1.getglobal("None");
                              var1.setlocal(23, var6);
                              var6 = null;
                              var1.setline(646);
                              var6 = var1.getlocal(0).__getattr__("stack").__iter__();

                              while(true) {
                                 var1.setline(646);
                                 var7 = var6.__iternext__();
                                 if (var7 == null) {
                                    var1.setline(649);
                                    var6 = var1.getlocal(23);
                                    var10000 = var6._is(var1.getglobal("None"));
                                    var6 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(650);
                                       var6 = var1.getlocal(0).__getattr__("_XMLParser__namespaces").__getattr__("get").__call__(var2, var1.getlocal(22));
                                       var1.setlocal(23, var6);
                                       var6 = null;
                                    }

                                    var1.setline(651);
                                    var6 = var1.getlocal(23);
                                    var10000 = var6._isnot(var1.getglobal("None"));
                                    var6 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(652);
                                       var6 = var1.getlocal(23)._add(PyString.fromInterned(" "))._add(var1.getlocal(18));
                                       var1.setlocal(18, var6);
                                       var6 = null;
                                    } else {
                                       var1.setline(654);
                                       var6 = var1.getlocal(22)._add(PyString.fromInterned(":"))._add(var1.getlocal(18));
                                       var1.setlocal(18, var6);
                                       var6 = null;
                                    }
                                    break;
                                 }

                                 PyObject[] var12 = Py.unpackSequence(var7, 3);
                                 PyObject var9 = var12[0];
                                 var1.setlocal(14, var9);
                                 var9 = null;
                                 var9 = var12[1];
                                 var1.setlocal(15, var9);
                                 var9 = null;
                                 var9 = var12[2];
                                 var1.setlocal(16, var9);
                                 var9 = null;
                                 var1.setline(647);
                                 var8 = var1.getlocal(22);
                                 var10000 = var8._in(var1.getlocal(15));
                                 var8 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(648);
                                    var8 = var1.getlocal(15).__getitem__(var1.getlocal(22));
                                    var1.setlocal(23, var8);
                                    var8 = null;
                                 }
                              }
                           }
                        }

                        var1.setline(655);
                        var6 = var1.getlocal(20);
                        var1.getlocal(19).__setitem__(var1.getlocal(18), var6);
                        var6 = null;
                        var1.setline(656);
                        var6 = var1.getlocal(21);
                        var1.getlocal(17).__setitem__(var1.getlocal(18), var6);
                        var6 = null;
                     }
                  }

                  var1.setline(658);
                  var4 = var1.getlocal(0).__getattr__("attributes").__getattr__("get").__call__(var2, var1.getlocal(5));
                  var1.setlocal(24, var4);
                  var4 = null;
                  var1.setline(659);
                  var4 = var1.getlocal(24);
                  var10000 = var4._isnot(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(660);
                     var4 = var1.getlocal(9).__getattr__("keys").__call__(var2).__iter__();

                     label113:
                     while(true) {
                        var1.setline(660);
                        var11 = var4.__iternext__();
                        if (var11 == null) {
                           var1.setline(663);
                           var4 = var1.getlocal(24).__getattr__("items").__call__(var2).__iter__();

                           while(true) {
                              var1.setline(663);
                              var11 = var4.__iternext__();
                              if (var11 == null) {
                                 break label113;
                              }

                              var14 = Py.unpackSequence(var11, 2);
                              var7 = var14[0];
                              var1.setlocal(18, var7);
                              var7 = null;
                              var7 = var14[1];
                              var1.setlocal(20, var7);
                              var7 = null;
                              var1.setline(664);
                              var6 = var1.getlocal(20);
                              var10000 = var6._isnot(var1.getglobal("None"));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var6 = var1.getlocal(18);
                                 var10000 = var6._in(var1.getlocal(9));
                                 var6 = null;
                                 var10000 = var10000.__not__();
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(665);
                                 var6 = var1.getlocal(20);
                                 var1.getlocal(9).__setitem__(var1.getlocal(18), var6);
                                 var6 = null;
                              }
                           }
                        }

                        var1.setlocal(18, var11);
                        var1.setline(661);
                        var6 = var1.getlocal(18);
                        var10000 = var6._in(var1.getlocal(24));
                        var6 = null;
                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(662);
                           var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("unknown attribute `%s' in tag `%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(17).__getitem__(var1.getlocal(18)), var1.getlocal(6)})));
                        }
                     }
                  }

                  var1.setline(666);
                  var4 = var1.getlocal(0).__getattr__("elements").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")}))).__getitem__(Py.newInteger(0));
                  var1.setlocal(25, var4);
                  var4 = null;
                  var1.setline(667);
                  var1.getlocal(0).__getattr__("finish_starttag").__call__(var2, var1.getlocal(5), var1.getlocal(9), var1.getlocal(25));
                  var1.setline(668);
                  var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("slash"));
                  var10000 = var4._eq(PyString.fromInterned("/"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(669);
                     var1.getlocal(0).__getattr__("finish_endtag").__call__(var2, var1.getlocal(6));
                  }

                  var1.setline(670);
                  var3 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(18, var11);
               var1.setline(634);
               var6 = var1.getlocal(18);
               var1.getlocal(17).__setitem__(var1.getlocal(18), var6);
               var6 = null;
            }
         }
      }
   }

   public PyObject parse_endtag$21(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(675);
      var3 = var1.getglobal("endbracketfind").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(676);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(677);
         PyInteger var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(678);
         PyObject var4 = var1.getglobal("tagfind").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(Py.newInteger(2)));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(679);
         var4 = var1.getlocal(4);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(680);
            if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
               var1.setline(681);
               var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(1)));
               var1.setline(682);
               var3 = var1.getlocal(1)._add(Py.newInteger(1));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(683);
            if (var1.getlocal(0).__getattr__("_XMLParser__accept_missing_endtag_name").__not__().__nonzero__()) {
               var1.setline(684);
               var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no name specified in end tag"));
            }

            var1.setline(685);
            var4 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(686);
            var4 = var1.getlocal(1)._add(Py.newInteger(2));
            var1.setlocal(6, var4);
            var4 = null;
         } else {
            var1.setline(688);
            var4 = var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(689);
            if (var1.getlocal(0).__getattr__("_XMLParser__map_case").__nonzero__()) {
               var1.setline(690);
               var4 = var1.getlocal(5).__getattr__("lower").__call__(var2);
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(691);
            if (var1.getlocal(0).__getattr__("literal").__nonzero__()) {
               var1.setline(692);
               var10000 = var1.getlocal(0).__getattr__("stack").__not__();
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(5);
                  var10000 = var4._ne(var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0)));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(693);
                  var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(1)));
                  var1.setline(694);
                  var3 = var1.getlocal(1)._add(Py.newInteger(1));
                  var1.f_lasti = -1;
                  return var3;
               }
            }

            var1.setline(695);
            var4 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(6, var4);
            var4 = null;
         }

         var1.setline(696);
         var4 = var1.getglobal("endbracket").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(6));
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(697);
            var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("garbage in end tag"));
         }

         var1.setline(698);
         var1.getlocal(0).__getattr__("finish_endtag").__call__(var2, var1.getlocal(5));
         var1.setline(699);
         var3 = var1.getlocal(3).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject finish_starttag$22(PyFrame var1, ThreadState var2) {
      var1.setline(703);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(704);
         var1.getlocal(0).__getattr__("handle_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(2));
      } else {
         var1.setline(706);
         var1.getlocal(0).__getattr__("unknown_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish_endtag$23(PyFrame var1, ThreadState var2) {
      var1.setline(710);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"literal", var3);
      var3 = null;
      var1.setline(711);
      PyObject var10000;
      PyObject var6;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(712);
         var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name-less end tag"));
         var1.setline(713);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"))._sub(Py.newInteger(1));
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(714);
         var6 = var1.getlocal(2);
         var10000 = var6._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(715);
            var1.getlocal(0).__getattr__("unknown_endtag").__call__(var2, var1.getlocal(1));
            var1.setline(716);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(718);
         var3 = Py.newInteger(-1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(719);
         var6 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"))).__iter__();

         while(true) {
            var1.setline(719);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(722);
               var6 = var1.getlocal(2);
               var10000 = var6._eq(Py.newInteger(-1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(723);
                  var1.getlocal(0).__getattr__("syntax_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unopened end tag"));
                  var1.setline(724);
                  var1.f_lasti = -1;
                  return Py.None;
               }
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(720);
            PyObject var5 = var1.getlocal(1);
            var10000 = var5._eq(var1.getlocal(0).__getattr__("stack").__getitem__(var1.getlocal(3)).__getitem__(Py.newInteger(0)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(721);
               var5 = var1.getlocal(3);
               var1.setlocal(2, var5);
               var5 = null;
            }
         }
      }

      while(true) {
         var1.setline(725);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"));
         var10000 = var6._gt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(726);
         var6 = var1.getlocal(2);
         var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"))._sub(Py.newInteger(1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(727);
            var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("missing close tag for %s")._mod(var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(2))));
         }

         var1.setline(728);
         var6 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(2));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(729);
         var6 = var1.getlocal(0).__getattr__("elements").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")}))).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(730);
         var6 = var1.getlocal(5);
         var10000 = var6._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(731);
            var1.getlocal(0).__getattr__("handle_endtag").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         } else {
            var1.setline(733);
            var1.getlocal(0).__getattr__("unknown_endtag").__call__(var2, var1.getlocal(4));
         }

         var1.setline(734);
         var6 = var1.getlocal(0).__getattr__("_XMLParser__use_namespaces");
         var10000 = var6._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(735);
            var3 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_XMLParser__use_namespaces", var3);
            var3 = null;
         }

         var1.setline(736);
         var1.getlocal(0).__getattr__("stack").__delitem__((PyObject)Py.newInteger(-1));
      }
   }

   public PyObject handle_xml$24(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_doctype$25(PyFrame var1, ThreadState var2) {
      var1.setline(744);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_starttag$26(PyFrame var1, ThreadState var2) {
      var1.setline(748);
      var1.getlocal(2).__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_endtag$27(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      var1.getlocal(2).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_charref$28(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(757);
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var6._eq(PyString.fromInterned("x"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(758);
            var6 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)Py.newInteger(16));
            var1.setlocal(2, var6);
            var3 = null;
         } else {
            var1.setline(760);
            var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var6);
            var3 = null;
         }
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("ValueError"))) {
            var1.setline(762);
            var1.getlocal(0).__getattr__("unknown_charref").__call__(var2, var1.getlocal(1));
            var1.setline(763);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(764);
      PyInteger var7 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var8 = var7;
      var6 = var10001;
      PyObject var4;
      if ((var4 = var8._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(255));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(765);
         var1.getlocal(0).__getattr__("unknown_charref").__call__(var2, var1.getlocal(1));
         var1.setline(766);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(767);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2)));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject handle_data$29(PyFrame var1, ThreadState var2) {
      var1.setline(779);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_cdata$30(PyFrame var1, ThreadState var2) {
      var1.setline(783);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_comment$31(PyFrame var1, ThreadState var2) {
      var1.setline(787);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_proc$32(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject syntax_error$33(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("Syntax error at line %d: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("lineno"), var1.getlocal(1)}))));
   }

   public PyObject unknown_starttag$34(PyFrame var1, ThreadState var2) {
      var1.setline(798);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_endtag$35(PyFrame var1, ThreadState var2) {
      var1.setline(799);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_charref$36(PyFrame var1, ThreadState var2) {
      var1.setline(800);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_entityref$37(PyFrame var1, ThreadState var2) {
      var1.setline(802);
      var1.getlocal(0).__getattr__("syntax_error").__call__(var2, PyString.fromInterned("reference to unknown entity `&%s;'")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestXMLParser$38(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(807);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$39, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(811);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_xml$40, (PyObject)null);
      var1.setlocal("handle_xml", var4);
      var3 = null;
      var1.setline(815);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_doctype$41, (PyObject)null);
      var1.setlocal("handle_doctype", var4);
      var3 = null;
      var1.setline(819);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_data$42, (PyObject)null);
      var1.setlocal("handle_data", var4);
      var3 = null;
      var1.setline(824);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$43, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(830);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_cdata$44, (PyObject)null);
      var1.setlocal("handle_cdata", var4);
      var3 = null;
      var1.setline(834);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_proc$45, (PyObject)null);
      var1.setlocal("handle_proc", var4);
      var3 = null;
      var1.setline(838);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_comment$46, (PyObject)null);
      var1.setlocal("handle_comment", var4);
      var3 = null;
      var1.setline(845);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, syntax_error$47, (PyObject)null);
      var1.setlocal("syntax_error", var4);
      var3 = null;
      var1.setline(848);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_starttag$48, (PyObject)null);
      var1.setlocal("unknown_starttag", var4);
      var3 = null;
      var1.setline(858);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_endtag$49, (PyObject)null);
      var1.setlocal("unknown_endtag", var4);
      var3 = null;
      var1.setline(862);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_entityref$50, (PyObject)null);
      var1.setlocal("unknown_entityref", var4);
      var3 = null;
      var1.setline(866);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_charref$51, (PyObject)null);
      var1.setlocal("unknown_charref", var4);
      var3 = null;
      var1.setline(870);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$52, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$39(PyFrame var1, ThreadState var2) {
      var1.setline(808);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"testdata", var3);
      var3 = null;
      var1.setline(809);
      PyObject var10000 = var1.getglobal("XMLParser").__getattr__("__init__");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_xml$40(PyFrame var1, ThreadState var2) {
      var1.setline(812);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(813);
      Py.printComma(PyString.fromInterned("xml: encoding ="));
      Py.printComma(var1.getlocal(1));
      Py.printComma(PyString.fromInterned("standalone ="));
      Py.println(var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_doctype$41(PyFrame var1, ThreadState var2) {
      var1.setline(816);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(817);
      Py.printComma(PyString.fromInterned("DOCTYPE:"));
      Py.printComma(var1.getlocal(1));
      Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_data$42(PyFrame var1, ThreadState var2) {
      var1.setline(820);
      PyObject var3 = var1.getlocal(0).__getattr__("testdata")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("testdata", var3);
      var3 = null;
      var1.setline(821);
      var3 = var1.getglobal("len").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("testdata")));
      PyObject var10000 = var3._ge(Py.newInteger(70));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(822);
         var1.getlocal(0).__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$43(PyFrame var1, ThreadState var2) {
      var1.setline(825);
      PyObject var3 = var1.getlocal(0).__getattr__("testdata");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(826);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(827);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"testdata", var4);
         var3 = null;
         var1.setline(828);
         Py.printComma(PyString.fromInterned("data:"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_cdata$44(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(832);
      Py.printComma(PyString.fromInterned("cdata:"));
      Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_proc$45(PyFrame var1, ThreadState var2) {
      var1.setline(835);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(836);
      Py.printComma(PyString.fromInterned("processing:"));
      Py.printComma(var1.getlocal(1));
      Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_comment$46(PyFrame var1, ThreadState var2) {
      var1.setline(839);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(840);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(841);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(68));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(842);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(32), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(2).__getslice__(Py.newInteger(-32), (PyObject)null, (PyObject)null));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(843);
      Py.printComma(PyString.fromInterned("comment:"));
      Py.println(var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject syntax_error$47(PyFrame var1, ThreadState var2) {
      var1.setline(846);
      Py.printComma(PyString.fromInterned("error at line %d:")._mod(var1.getlocal(0).__getattr__("lineno")));
      Py.println(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_starttag$48(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(850);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(851);
         Py.println(PyString.fromInterned("start tag: <")._add(var1.getlocal(1))._add(PyString.fromInterned(">")));
      } else {
         var1.setline(853);
         Py.printComma(PyString.fromInterned("start tag: <")._add(var1.getlocal(1)));
         var1.setline(854);
         PyObject var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(854);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(856);
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
            var1.setline(855);
            Py.printComma(var1.getlocal(3)._add(PyString.fromInterned("="))._add(PyString.fromInterned("\""))._add(var1.getlocal(4))._add(PyString.fromInterned("\"")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_endtag$49(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(860);
      Py.println(PyString.fromInterned("end tag: </")._add(var1.getlocal(1))._add(PyString.fromInterned(">")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_entityref$50(PyFrame var1, ThreadState var2) {
      var1.setline(863);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(864);
      Py.println(PyString.fromInterned("*** unknown entity ref: &")._add(var1.getlocal(1))._add(PyString.fromInterned(";")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_charref$51(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(868);
      Py.println(PyString.fromInterned("*** unknown char ref: &#")._add(var1.getlocal(1))._add(PyString.fromInterned(";")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$52(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      var1.getglobal("XMLParser").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.setline(872);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$53(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(876);
      String[] var9 = new String[]{"time"};
      PyObject[] var10 = imp.importFrom("time", var9, var1, -1);
      PyObject var4 = var10[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(878);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(879);
         var3 = var1.getlocal(1).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(881);
      var3 = var1.getlocal(2).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("st"));
      PyObject[] var11 = Py.unpackSequence(var3, 2);
      PyObject var5 = var11[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var11[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(882);
      var3 = var1.getglobal("TestXMLParser");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(883);
      PyInteger var13 = Py.newInteger(0);
      var1.setlocal(6, var13);
      var3 = null;
      var1.setline(884);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(884);
         var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(890);
            if (var1.getlocal(0).__nonzero__()) {
               var1.setline(891);
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var1.setlocal(9, var3);
               var3 = null;
            } else {
               var1.setline(893);
               PyString var14 = PyString.fromInterned("test.xml");
               var1.setlocal(9, var14);
               var3 = null;
            }

            var1.setline(895);
            var3 = var1.getlocal(9);
            var10000 = var3._eq(PyString.fromInterned("-"));
            var3 = null;
            PyException var16;
            if (var10000.__nonzero__()) {
               var1.setline(896);
               var3 = var1.getlocal(1).__getattr__("stdin");
               var1.setlocal(10, var3);
               var3 = null;
            } else {
               try {
                  var1.setline(899);
                  var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("r"));
                  var1.setlocal(10, var3);
                  var3 = null;
               } catch (Throwable var8) {
                  var16 = Py.setException(var8, var1);
                  if (!var16.match(var1.getglobal("IOError"))) {
                     throw var16;
                  }

                  var4 = var16.value;
                  var1.setlocal(11, var4);
                  var4 = null;
                  var1.setline(901);
                  Py.printComma(var1.getlocal(9));
                  Py.printComma(PyString.fromInterned(":"));
                  Py.println(var1.getlocal(11));
                  var1.setline(902);
                  var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               }
            }

            var1.setline(904);
            var3 = var1.getlocal(10).__getattr__("read").__call__(var2);
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(905);
            var3 = var1.getlocal(10);
            var10000 = var3._isnot(var1.getlocal(1).__getattr__("stdin"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(906);
               var1.getlocal(10).__getattr__("close").__call__(var2);
            }

            var1.setline(908);
            var3 = var1.getlocal(5).__call__(var2);
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(909);
            var3 = var1.getlocal(3).__call__(var2);
            var1.setlocal(14, var3);
            var3 = null;

            try {
               var1.setline(911);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(912);
                  var1.getlocal(13).__getattr__("feed").__call__(var2, var1.getlocal(12));
                  var1.setline(913);
                  var1.getlocal(13).__getattr__("close").__call__(var2);
               } else {
                  var1.setline(915);
                  var3 = var1.getlocal(12).__iter__();

                  while(true) {
                     var1.setline(915);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(917);
                        var1.getlocal(13).__getattr__("close").__call__(var2);
                        break;
                     }

                     var1.setlocal(15, var4);
                     var1.setline(916);
                     var1.getlocal(13).__getattr__("feed").__call__(var2, var1.getlocal(15));
                  }
               }
            } catch (Throwable var7) {
               var16 = Py.setException(var7, var1);
               if (!var16.match(var1.getglobal("Error"))) {
                  throw var16;
               }

               var4 = var16.value;
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(919);
               var4 = var1.getlocal(3).__call__(var2);
               var1.setlocal(16, var4);
               var4 = null;
               var1.setline(920);
               Py.println(var1.getlocal(11));
               var1.setline(921);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(922);
                  Py.println(PyString.fromInterned("total time: %g")._mod(var1.getlocal(16)._sub(var1.getlocal(14))));
               }

               var1.setline(923);
               var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var1.setline(924);
            var3 = var1.getlocal(3).__call__(var2);
            var1.setlocal(16, var3);
            var3 = null;
            var1.setline(925);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(926);
               Py.println(PyString.fromInterned("total time: %g")._mod(var1.getlocal(16)._sub(var1.getlocal(14))));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var12 = Py.unpackSequence(var4, 2);
         PyObject var6 = var12[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var12[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(885);
         var5 = var1.getlocal(7);
         var10000 = var5._eq(PyString.fromInterned("-s"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(886);
            var5 = var1.getglobal("XMLParser");
            var1.setlocal(5, var5);
            var5 = null;
         } else {
            var1.setline(887);
            var5 = var1.getlocal(7);
            var10000 = var5._eq(PyString.fromInterned("-t"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(888);
               PyInteger var15 = Py.newInteger(1);
               var1.setlocal(6, var15);
               var5 = null;
            }
         }
      }
   }

   public xmllib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      XMLParser$2 = Py.newCode(0, var2, var1, "XMLParser", 91, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kw"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 103, false, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _XMLParser__fixelements$4 = Py.newCode(1, var2, var1, "_XMLParser__fixelements", 117, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "kl", "k"};
      _XMLParser__fixclass$5 = Py.newCode(2, var2, var1, "_XMLParser__fixclass", 123, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "key", "tag", "start", "end"};
      _XMLParser__fixdict$6 = Py.newCode(2, var2, var1, "_XMLParser__fixdict", 128, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$7 = Py.newCode(1, var2, var1, "reset", 142, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setnomoretags$8 = Py.newCode(1, var2, var1, "setnomoretags", 159, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      setliteral$9 = Py.newCode(2, var2, var1, "setliteral", 163, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$10 = Py.newCode(2, var2, var1, "feed", 170, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$11 = Py.newCode(1, var2, var1, "close", 175, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "all", "i", "res", "s", "str", "rescan"};
      translate_references$12 = Py.newCode(3, var2, var1, "translate_references", 183, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nsdict", "t", "d", "nst"};
      getnamespace$13 = Py.newCode(1, var2, var1, "getnamespace", 233, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "end", "rawdata", "i", "n", "data", "res", "j", "k", "version", "encoding", "standalone", "name"};
      goahead$14 = Py.newCode(2, var2, var1, "goahead", 242, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "res"};
      parse_comment$15 = Py.newCode(2, var2, var1, "parse_comment", 426, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "res", "rawdata", "n", "name", "pubid", "syslit", "j", "k", "level", "dq", "sq", "c"};
      parse_doctype$16 = Py.newCode(2, var2, var1, "parse_doctype", 444, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "res"};
      parse_cdata$17 = Py.newCode(2, var2, var1, "parse_cdata", 492, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "end", "j", "res", "k", "name", "attrdict", "namespace", "attrname", "prefix"};
      parse_proc$18 = Py.newCode(2, var2, var1, "parse_proc", 509, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "i", "j", "rawdata", "attrdict", "namespace", "res", "attrname", "attrvalue", "ncname"};
      parse_attributes$19 = Py.newCode(4, var2, var1, "parse_attributes", 554, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "end", "tag", "nstag", "tagname", "k", "j", "attrdict", "nsdict", "res", "prefix", "ns", "t", "d", "nst", "attrnamemap", "key", "nattrdict", "val", "okey", "aprefix", "ans", "attributes", "method"};
      parse_starttag$20 = Py.newCode(2, var2, var1, "parse_starttag", 591, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "end", "res", "tag", "k"};
      parse_endtag$21 = Py.newCode(2, var2, var1, "parse_endtag", 673, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tagname", "attrdict", "method"};
      finish_starttag$22 = Py.newCode(4, var2, var1, "finish_starttag", 702, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "found", "i", "nstag", "method"};
      finish_endtag$23 = Py.newCode(2, var2, var1, "finish_endtag", 709, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding", "standalone"};
      handle_xml$24 = Py.newCode(3, var2, var1, "handle_xml", 739, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "pubid", "syslit", "data"};
      handle_doctype$25 = Py.newCode(5, var2, var1, "handle_doctype", 743, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "method", "attrs"};
      handle_starttag$26 = Py.newCode(4, var2, var1, "handle_starttag", 747, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "method"};
      handle_endtag$27 = Py.newCode(3, var2, var1, "handle_endtag", 751, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n"};
      handle_charref$28 = Py.newCode(2, var2, var1, "handle_charref", 755, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$29 = Py.newCode(2, var2, var1, "handle_data", 778, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_cdata$30 = Py.newCode(2, var2, var1, "handle_cdata", 782, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_comment$31 = Py.newCode(2, var2, var1, "handle_comment", 786, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "data"};
      handle_proc$32 = Py.newCode(3, var2, var1, "handle_proc", 790, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      syntax_error$33 = Py.newCode(2, var2, var1, "syntax_error", 794, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs"};
      unknown_starttag$34 = Py.newCode(3, var2, var1, "unknown_starttag", 798, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      unknown_endtag$35 = Py.newCode(2, var2, var1, "unknown_endtag", 799, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_charref$36 = Py.newCode(2, var2, var1, "unknown_charref", 800, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      unknown_entityref$37 = Py.newCode(2, var2, var1, "unknown_entityref", 801, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestXMLParser$38 = Py.newCode(0, var2, var1, "TestXMLParser", 805, false, false, self, 38, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kw"};
      __init__$39 = Py.newCode(2, var2, var1, "__init__", 807, false, true, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding", "standalone"};
      handle_xml$40 = Py.newCode(3, var2, var1, "handle_xml", 811, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "pubid", "syslit", "data"};
      handle_doctype$41 = Py.newCode(5, var2, var1, "handle_doctype", 815, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$42 = Py.newCode(2, var2, var1, "handle_data", 819, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      flush$43 = Py.newCode(1, var2, var1, "flush", 824, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_cdata$44 = Py.newCode(2, var2, var1, "handle_cdata", 830, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "data"};
      handle_proc$45 = Py.newCode(3, var2, var1, "handle_proc", 834, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "r"};
      handle_comment$46 = Py.newCode(2, var2, var1, "handle_comment", 838, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      syntax_error$47 = Py.newCode(2, var2, var1, "syntax_error", 845, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs", "name", "value"};
      unknown_starttag$48 = Py.newCode(3, var2, var1, "unknown_starttag", 848, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      unknown_endtag$49 = Py.newCode(2, var2, var1, "unknown_endtag", 858, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_entityref$50 = Py.newCode(2, var2, var1, "unknown_entityref", 862, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ref"};
      unknown_charref$51 = Py.newCode(2, var2, var1, "unknown_charref", 866, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$52 = Py.newCode(1, var2, var1, "close", 870, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "sys", "getopt", "time", "opts", "klass", "do_time", "o", "a", "file", "f", "msg", "data", "x", "t0", "c", "t1"};
      test$53 = Py.newCode(1, var2, var1, "test", 874, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new xmllib$py("xmllib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(xmllib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.XMLParser$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._XMLParser__fixelements$4(var2, var3);
         case 5:
            return this._XMLParser__fixclass$5(var2, var3);
         case 6:
            return this._XMLParser__fixdict$6(var2, var3);
         case 7:
            return this.reset$7(var2, var3);
         case 8:
            return this.setnomoretags$8(var2, var3);
         case 9:
            return this.setliteral$9(var2, var3);
         case 10:
            return this.feed$10(var2, var3);
         case 11:
            return this.close$11(var2, var3);
         case 12:
            return this.translate_references$12(var2, var3);
         case 13:
            return this.getnamespace$13(var2, var3);
         case 14:
            return this.goahead$14(var2, var3);
         case 15:
            return this.parse_comment$15(var2, var3);
         case 16:
            return this.parse_doctype$16(var2, var3);
         case 17:
            return this.parse_cdata$17(var2, var3);
         case 18:
            return this.parse_proc$18(var2, var3);
         case 19:
            return this.parse_attributes$19(var2, var3);
         case 20:
            return this.parse_starttag$20(var2, var3);
         case 21:
            return this.parse_endtag$21(var2, var3);
         case 22:
            return this.finish_starttag$22(var2, var3);
         case 23:
            return this.finish_endtag$23(var2, var3);
         case 24:
            return this.handle_xml$24(var2, var3);
         case 25:
            return this.handle_doctype$25(var2, var3);
         case 26:
            return this.handle_starttag$26(var2, var3);
         case 27:
            return this.handle_endtag$27(var2, var3);
         case 28:
            return this.handle_charref$28(var2, var3);
         case 29:
            return this.handle_data$29(var2, var3);
         case 30:
            return this.handle_cdata$30(var2, var3);
         case 31:
            return this.handle_comment$31(var2, var3);
         case 32:
            return this.handle_proc$32(var2, var3);
         case 33:
            return this.syntax_error$33(var2, var3);
         case 34:
            return this.unknown_starttag$34(var2, var3);
         case 35:
            return this.unknown_endtag$35(var2, var3);
         case 36:
            return this.unknown_charref$36(var2, var3);
         case 37:
            return this.unknown_entityref$37(var2, var3);
         case 38:
            return this.TestXMLParser$38(var2, var3);
         case 39:
            return this.__init__$39(var2, var3);
         case 40:
            return this.handle_xml$40(var2, var3);
         case 41:
            return this.handle_doctype$41(var2, var3);
         case 42:
            return this.handle_data$42(var2, var3);
         case 43:
            return this.flush$43(var2, var3);
         case 44:
            return this.handle_cdata$44(var2, var3);
         case 45:
            return this.handle_proc$45(var2, var3);
         case 46:
            return this.handle_comment$46(var2, var3);
         case 47:
            return this.syntax_error$47(var2, var3);
         case 48:
            return this.unknown_starttag$48(var2, var3);
         case 49:
            return this.unknown_endtag$49(var2, var3);
         case 50:
            return this.unknown_entityref$50(var2, var3);
         case 51:
            return this.unknown_charref$51(var2, var3);
         case 52:
            return this.close$52(var2, var3);
         case 53:
            return this.test$53(var2, var3);
         default:
            return null;
      }
   }
}
