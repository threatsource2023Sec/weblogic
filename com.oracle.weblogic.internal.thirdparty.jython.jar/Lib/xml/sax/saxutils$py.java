package xml.sax;

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
@Filename("xml/sax/saxutils.py")
public class saxutils$py extends PyFunctionTable implements PyRunnable {
   static saxutils$py self;
   static final PyCode f$0;
   static final PyCode __dict_replace$1;
   static final PyCode escape$2;
   static final PyCode unescape$3;
   static final PyCode quoteattr$4;
   static final PyCode DefaultHandler$5;
   static final PyCode Location$6;
   static final PyCode __init__$7;
   static final PyCode getColumnNumber$8;
   static final PyCode getLineNumber$9;
   static final PyCode getPublicId$10;
   static final PyCode getSystemId$11;
   static final PyCode __str__$12;
   static final PyCode ErrorPrinter$13;
   static final PyCode __init__$14;
   static final PyCode warning$15;
   static final PyCode error$16;
   static final PyCode fatalError$17;
   static final PyCode _ErrorPrinter__getpos$18;
   static final PyCode ErrorRaiser$19;
   static final PyCode __init__$20;
   static final PyCode error$21;
   static final PyCode fatalError$22;
   static final PyCode warning$23;
   static final PyCode _outputwrapper$24;
   static final PyCode writetext$25;
   static final PyCode writetext$26;
   static final PyCode writeattr$27;
   static final PyCode XMLGenerator$28;
   static final PyCode __init__$29;
   static final PyCode startDocument$30;
   static final PyCode startPrefixMapping$31;
   static final PyCode endPrefixMapping$32;
   static final PyCode startElement$33;
   static final PyCode endElement$34;
   static final PyCode startElementNS$35;
   static final PyCode endElementNS$36;
   static final PyCode characters$37;
   static final PyCode ignorableWhitespace$38;
   static final PyCode processingInstruction$39;
   static final PyCode LexicalXMLGenerator$40;
   static final PyCode __init__$41;
   static final PyCode characters$42;
   static final PyCode startDTD$43;
   static final PyCode endDTD$44;
   static final PyCode comment$45;
   static final PyCode startCDATA$46;
   static final PyCode endCDATA$47;
   static final PyCode ContentGenerator$48;
   static final PyCode characters$49;
   static final PyCode XMLFilterBase$50;
   static final PyCode error$51;
   static final PyCode fatalError$52;
   static final PyCode warning$53;
   static final PyCode setDocumentLocator$54;
   static final PyCode startDocument$55;
   static final PyCode endDocument$56;
   static final PyCode startPrefixMapping$57;
   static final PyCode endPrefixMapping$58;
   static final PyCode startElement$59;
   static final PyCode endElement$60;
   static final PyCode startElementNS$61;
   static final PyCode endElementNS$62;
   static final PyCode characters$63;
   static final PyCode ignorableWhitespace$64;
   static final PyCode processingInstruction$65;
   static final PyCode skippedEntity$66;
   static final PyCode notationDecl$67;
   static final PyCode unparsedEntityDecl$68;
   static final PyCode resolveEntity$69;
   static final PyCode parse$70;
   static final PyCode setLocale$71;
   static final PyCode getFeature$72;
   static final PyCode setFeature$73;
   static final PyCode getProperty$74;
   static final PyCode setProperty$75;
   static final PyCode BaseIncrementalParser$76;
   static final PyCode parse$77;
   static final PyCode prepareParser$78;
   static final PyCode prepare_input_source$79;
   static final PyCode absolute_system_id$80;
   static final PyCode AttributeMap$81;
   static final PyCode __init__$82;
   static final PyCode getLength$83;
   static final PyCode getName$84;
   static final PyCode getType$85;
   static final PyCode getValue$86;
   static final PyCode __len__$87;
   static final PyCode __getitem__$88;
   static final PyCode items$89;
   static final PyCode keys$90;
   static final PyCode has_key$91;
   static final PyCode get$92;
   static final PyCode copy$93;
   static final PyCode values$94;
   static final PyCode EventBroadcaster$95;
   static final PyCode Event$96;
   static final PyCode __init__$97;
   static final PyCode __call__$98;
   static final PyCode __init__$99;
   static final PyCode __getattr__$100;
   static final PyCode __repr__$101;
   static final PyCode ESISDocHandler$102;
   static final PyCode __init__$103;
   static final PyCode processingInstruction$104;
   static final PyCode startElement$105;
   static final PyCode endElement$106;
   static final PyCode characters$107;
   static final PyCode Canonizer$108;
   static final PyCode __init__$109;
   static final PyCode processingInstruction$110;
   static final PyCode startElement$111;
   static final PyCode endElement$112;
   static final PyCode ignorableWhitespace$113;
   static final PyCode characters$114;
   static final PyCode write_data$115;
   static final PyCode mllib$116;
   static final PyCode __init__$117;
   static final PyCode reset$118;
   static final PyCode feed$119;
   static final PyCode close$120;
   static final PyCode get_stack$121;
   static final PyCode handle_starttag$122;
   static final PyCode handle_endtag$123;
   static final PyCode handle_data$124;
   static final PyCode handle_proc$125;
   static final PyCode unknown_starttag$126;
   static final PyCode unknown_endtag$127;
   static final PyCode syntax_error$128;
   static final PyCode Handler$129;
   static final PyCode __init__$130;
   static final PyCode get_stack$131;
   static final PyCode reset$132;
   static final PyCode characters$133;
   static final PyCode endElement$134;
   static final PyCode ignorableWhitespace$135;
   static final PyCode processingInstruction$136;
   static final PyCode startElement$137;
   static final PyCode error$138;
   static final PyCode fatalError$139;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nA library of useful helper classes to the saxlib classes, for the\nconvenience of application and driver writers.\n\n$Id: saxutils.py,v 1.37 2005/04/13 14:02:08 syt Exp $\n"));
      var1.setline(6);
      PyString.fromInterned("\nA library of useful helper classes to the saxlib classes, for the\nconvenience of application and driver writers.\n\n$Id: saxutils.py,v 1.37 2005/04/13 14:02:08 syt Exp $\n");
      var1.setline(7);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var3);
      var3 = null;
      var3 = imp.importOne("urllib2", var1, -1);
      var1.setlocal("urllib2", var3);
      var3 = null;
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("handler", var1, -1);
      var1.setlocal("handler", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("xmlreader", var1, -1);
      var1.setlocal("xmlreader", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("_exceptions", var1, -1);
      var1.setlocal("_exceptions", var3);
      var3 = null;
      var3 = imp.importOne("saxlib", var1, -1);
      var1.setlocal("saxlib", var3);
      var3 = null;
      var1.setline(12);
      String[] var7 = new String[]{"Absolutize", "MakeUrllibSafe", "IsAbsolute"};
      PyObject[] var8 = imp.importFrom("xml.Uri", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("Absolutize", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("MakeUrllibSafe", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("IsAbsolute", var4);
      var4 = null;

      try {
         var1.setline(15);
         PyList var10 = new PyList(new PyObject[]{var1.getname("types").__getattr__("StringType"), var1.getname("types").__getattr__("UnicodeType")});
         var1.setlocal("_StringTypes", var10);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getname("AttributeError"))) {
            throw var9;
         }

         var1.setline(17);
         PyList var6 = new PyList(new PyObject[]{var1.getname("types").__getattr__("StringType")});
         var1.setlocal("_StringTypes", var6);
         var4 = null;
      }

      var1.setline(19);
      var8 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var8, __dict_replace$1, PyString.fromInterned("Replace substrings of a string using a dictionary."));
      var1.setlocal("__dict_replace", var11);
      var3 = null;
      var1.setline(25);
      var8 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      var11 = new PyFunction(var1.f_globals, var8, escape$2, PyString.fromInterned("Escape &, <, and > in a string of data.\n\n    You can escape other strings of data by passing a dictionary as\n    the optional entities parameter.  The keys and values must all be\n    strings; each key will be replaced with its corresponding value.\n    "));
      var1.setlocal("escape", var11);
      var3 = null;
      var1.setline(39);
      var8 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      var11 = new PyFunction(var1.f_globals, var8, unescape$3, PyString.fromInterned("Unescape &amp;, &lt;, and &gt; in a string of data.\n\n    You can unescape other strings of data by passing a dictionary as\n    the optional entities parameter.  The keys and values must all be\n    strings; each key will be replaced with its corresponding value.\n    "));
      var1.setlocal("unescape", var11);
      var3 = null;
      var1.setline(53);
      var8 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      var11 = new PyFunction(var1.f_globals, var8, quoteattr$4, PyString.fromInterned("Escape and quote an attribute value.\n\n    Escape &, <, and > in a string of data, then quote it for use as\n    an attribute value.  The \" character will be escaped as well, if\n    necessary.\n\n    You can escape other strings of data by passing a dictionary as\n    the optional entities parameter.  The keys and values must all be\n    strings; each key will be replaced with its corresponding value.\n    "));
      var1.setlocal("quoteattr", var11);
      var3 = null;
      var1.setline(76);
      var8 = new PyObject[]{var1.getname("handler").__getattr__("EntityResolver"), var1.getname("handler").__getattr__("DTDHandler"), var1.getname("handler").__getattr__("ContentHandler"), var1.getname("handler").__getattr__("ErrorHandler")};
      var4 = Py.makeClass("DefaultHandler", var8, DefaultHandler$5);
      var1.setlocal("DefaultHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(85);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Location", var8, Location$6);
      var1.setlocal("Location", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(123);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("ErrorPrinter", var8, ErrorPrinter$13);
      var1.setlocal("ErrorPrinter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(158);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("ErrorRaiser", var8, ErrorRaiser$19);
      var1.setlocal("ErrorRaiser", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(177);
      var7 = new String[]{"AttributesImpl"};
      var8 = imp.importFrom("xmlreader", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("AttributesImpl", var4);
      var4 = null;
      var1.setline(180);
      var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(182);
      var8 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var8, _outputwrapper$24, (PyObject)null);
      var1.setlocal("_outputwrapper", var11);
      var3 = null;
      var1.setline(186);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("codecs"), (PyObject)PyString.fromInterned("register_error")).__nonzero__()) {
         var1.setline(187);
         var8 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
         var11 = new PyFunction(var1.f_globals, var8, writetext$25, (PyObject)null);
         var1.setlocal("writetext", var11);
         var3 = null;
      } else {
         var1.setline(192);
         var8 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
         var11 = new PyFunction(var1.f_globals, var8, writetext$26, (PyObject)null);
         var1.setlocal("writetext", var11);
         var3 = null;
      }

      var1.setline(203);
      var8 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var8, writeattr$27, (PyObject)null);
      var1.setlocal("writeattr", var11);
      var3 = null;
      var1.setline(221);
      var8 = new PyObject[]{var1.getname("handler").__getattr__("ContentHandler")};
      var4 = Py.makeClass("XMLGenerator", var8, XMLGenerator$28);
      var1.setlocal("XMLGenerator", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(319);
      var8 = new PyObject[]{var1.getname("XMLGenerator"), var1.getname("saxlib").__getattr__("LexicalHandler")};
      var4 = Py.makeClass("LexicalXMLGenerator", var8, LexicalXMLGenerator$40);
      var1.setlocal("LexicalXMLGenerator", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(362);
      var8 = new PyObject[]{var1.getname("XMLGenerator")};
      var4 = Py.makeClass("ContentGenerator", var8, ContentGenerator$48);
      var1.setlocal("ContentGenerator", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(370);
      var8 = new PyObject[]{var1.getname("saxlib").__getattr__("XMLFilter")};
      var4 = Py.makeClass("XMLFilterBase", var8, XMLFilterBase$50);
      var1.setlocal("XMLFilterBase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(468);
      var3 = var1.getname("XMLFilterBase");
      var1.setlocal("XMLFilterImpl", var3);
      var3 = null;
      var1.setline(472);
      var8 = new PyObject[]{var1.getname("xmlreader").__getattr__("IncrementalParser")};
      var4 = Py.makeClass("BaseIncrementalParser", var8, BaseIncrementalParser$76);
      var1.setlocal("BaseIncrementalParser", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(503);
      var8 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var8, prepare_input_source$79, PyString.fromInterned("This function takes an InputSource and an optional base URL and\n    returns a fully resolved InputSource object ready for reading."));
      var1.setlocal("prepare_input_source", var11);
      var3 = null;
      var1.setline(525);
      var8 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var8, absolute_system_id$80, (PyObject)null);
      var1.setlocal("absolute_system_id", var11);
      var3 = null;
      var1.setline(541);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("AttributeMap", var8, AttributeMap$81);
      var1.setlocal("AttributeMap", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(598);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("EventBroadcaster", var8, EventBroadcaster$95);
      var1.setlocal("EventBroadcaster", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(624);
      var3 = imp.importOne("saxlib", var1, -1);
      var1.setlocal("saxlib", var3);
      var3 = null;
      var1.setline(625);
      var8 = new PyObject[]{var1.getname("saxlib").__getattr__("HandlerBase")};
      var4 = Py.makeClass("ESISDocHandler", var8, ESISDocHandler$102);
      var1.setlocal("ESISDocHandler", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(652);
      var8 = new PyObject[]{var1.getname("saxlib").__getattr__("HandlerBase")};
      var4 = Py.makeClass("Canonizer", var8, Canonizer$108);
      var1.setlocal("Canonizer", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(700);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("mllib", var8, mllib$116);
      var1.setlocal("mllib", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __dict_replace$1(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyString.fromInterned("Replace substrings of a string using a dictionary.");
      var1.setline(21);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(21);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(23);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(22);
         PyObject var7 = var1.getlocal(0).__getattr__("replace").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(0, var7);
         var5 = null;
      }
   }

   public PyObject escape$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("Escape &, <, and > in a string of data.\n\n    You can escape other strings of data by passing a dictionary as\n    the optional entities parameter.  The keys and values must all be\n    strings; each key will be replaced with its corresponding value.\n    ");
      var1.setline(32);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(35);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(36);
         var3 = var1.getglobal("__dict_replace").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(37);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unescape$3(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Unescape &amp;, &lt;, and &gt; in a string of data.\n\n    You can unescape other strings of data by passing a dictionary as\n    the optional entities parameter.  The keys and values must all be\n    strings; each key will be replaced with its corresponding value.\n    ");
      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&lt;"), (PyObject)PyString.fromInterned("<"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&gt;"), (PyObject)PyString.fromInterned(">"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(48);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(49);
         var3 = var1.getglobal("__dict_replace").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(51);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&amp;"), (PyObject)PyString.fromInterned("&"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quoteattr$4(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyString.fromInterned("Escape and quote an attribute value.\n\n    Escape &, <, and > in a string of data, then quote it for use as\n    an attribute value.  The \" character will be escaped as well, if\n    necessary.\n\n    You can escape other strings of data by passing a dictionary as\n    the optional entities parameter.  The keys and values must all be\n    strings; each key will be replaced with its corresponding value.\n    ");
      var1.setline(64);
      PyObject var3 = var1.getglobal("escape").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(65);
      PyString var4 = PyString.fromInterned("\"");
      PyObject var10000 = var4._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(66);
         var4 = PyString.fromInterned("'");
         var10000 = var4._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(67);
            var3 = PyString.fromInterned("\"%s\"")._mod(var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;")));
            var1.setlocal(0, var3);
            var3 = null;
         } else {
            var1.setline(69);
            var3 = PyString.fromInterned("'%s'")._mod(var1.getlocal(0));
            var1.setlocal(0, var3);
            var3 = null;
         }
      } else {
         var1.setline(71);
         var3 = PyString.fromInterned("\"%s\"")._mod(var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(72);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DefaultHandler$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Default base class for SAX2 event handlers. Implements empty\n    methods for all callback methods, which can be overridden by\n    application implementors. Replaces the deprecated SAX1 HandlerBase\n    class."));
      var1.setline(81);
      PyString.fromInterned("Default base class for SAX2 event handlers. Implements empty\n    methods for all callback methods, which can be overridden by\n    application implementors. Replaces the deprecated SAX1 HandlerBase\n    class.");
      return var1.getf_locals();
   }

   public PyObject Location$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represents a location in an XML entity. Initialized by being passed\n    a locator, from which it reads off the current location, which is then\n    stored internally."));
      var1.setline(88);
      PyString.fromInterned("Represents a location in an XML entity. Initialized by being passed\n    a locator, from which it reads off the current location, which is then\n    stored internally.");
      var1.setline(90);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getColumnNumber$8, (PyObject)null);
      var1.setlocal("getColumnNumber", var4);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLineNumber$9, (PyObject)null);
      var1.setlocal("getLineNumber", var4);
      var3 = null;
      var1.setline(102);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getPublicId$10, (PyObject)null);
      var1.setlocal("getPublicId", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getSystemId$11, (PyObject)null);
      var1.setlocal("getSystemId", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$12, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyObject var3 = var1.getlocal(1).__getattr__("getColumnNumber").__call__(var2);
      var1.getlocal(0).__setattr__("_Location__col", var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(1).__getattr__("getLineNumber").__call__(var2);
      var1.getlocal(0).__setattr__("_Location__line", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getlocal(1).__getattr__("getPublicId").__call__(var2);
      var1.getlocal(0).__setattr__("_Location__pubid", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(1).__getattr__("getSystemId").__call__(var2);
      var1.getlocal(0).__setattr__("_Location__sysid", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getColumnNumber$8(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getlocal(0).__getattr__("_Location__col");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getLineNumber$9(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = var1.getlocal(0).__getattr__("_Location__line");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getPublicId$10(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyObject var3 = var1.getlocal(0).__getattr__("_Location__pubid");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getSystemId$11(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getlocal(0).__getattr__("_Location__sysid");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$12(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3 = var1.getlocal(0).__getattr__("_Location__line");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(110);
         var4 = PyString.fromInterned("?");
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(112);
         var3 = var1.getlocal(0).__getattr__("_Location__line");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(113);
      var3 = var1.getlocal(0).__getattr__("_Location__col");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var4 = PyString.fromInterned("?");
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(116);
         var3 = var1.getlocal(0).__getattr__("_Location__col");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(117);
      PyString var5 = PyString.fromInterned("%s:%s:%s");
      PyTuple var10001 = new PyTuple;
      PyObject[] var10003 = new PyObject[3];
      Object var10006 = var1.getlocal(0).__getattr__("_Location__sysid");
      if (!((PyObject)var10006).__nonzero__()) {
         var10006 = var1.getlocal(0).__getattr__("_Location__pubid");
         if (!((PyObject)var10006).__nonzero__()) {
            var10006 = PyString.fromInterned("<unknown>");
         }
      }

      var10003[0] = (PyObject)var10006;
      var10003[1] = var1.getlocal(1);
      var10003[2] = var1.getlocal(2);
      var10001.<init>(var10003);
      var3 = var5._mod(var10001);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ErrorPrinter$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A simple class that just prints error messages to standard out."));
      var1.setline(124);
      PyString.fromInterned("A simple class that just prints error messages to standard out.");
      var1.setline(126);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("sys").__getattr__("stderr")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$15, (PyObject)null);
      var1.setlocal("warning", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$16, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$17, (PyObject)null);
      var1.setlocal("fatalError", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ErrorPrinter__getpos$18, (PyObject)null);
      var1.setlocal("_ErrorPrinter__getpos", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_level", var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_outfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$15(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         var1.getlocal(0).__getattr__("_outfile").__getattr__("write").__call__(var2, PyString.fromInterned("WARNING in %s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_ErrorPrinter__getpos").__call__(var2, var1.getlocal(1)), var1.getlocal(1).__getattr__("getMessage").__call__(var2)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$16(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._le(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(138);
         var1.getlocal(0).__getattr__("_outfile").__getattr__("write").__call__(var2, PyString.fromInterned("ERROR in %s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_ErrorPrinter__getpos").__call__(var2, var1.getlocal(1)), var1.getlocal(1).__getattr__("getMessage").__call__(var2)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fatalError$17(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._le(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(144);
         var1.getlocal(0).__getattr__("_outfile").__getattr__("write").__call__(var2, PyString.fromInterned("FATAL ERROR in %s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_ErrorPrinter__getpos").__call__(var2, var1.getlocal(1)), var1.getlocal(1).__getattr__("getMessage").__call__(var2)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ErrorPrinter__getpos$18(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_exceptions").__getattr__("SAXParseException")).__nonzero__()) {
         var1.setline(150);
         PyObject var4 = PyString.fromInterned("%s:%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("getSystemId").__call__(var2), var1.getlocal(1).__getattr__("getLineNumber").__call__(var2), var1.getlocal(1).__getattr__("getColumnNumber").__call__(var2)}));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(154);
         PyString var3 = PyString.fromInterned("<unknown>");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ErrorRaiser$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A simple class that just raises the exceptions it is passed."));
      var1.setline(159);
      PyString.fromInterned("A simple class that just raises the exceptions it is passed.");
      var1.setline(161);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$21, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$22, (PyObject)null);
      var1.setlocal("fatalError", var4);
      var3 = null;
      var1.setline(172);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$23, (PyObject)null);
      var1.setlocal("warning", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_level", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$21(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._le(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(166);
         throw Py.makeException(var1.getlocal(1));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject fatalError$22(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._le(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(170);
         throw Py.makeException(var1.getlocal(1));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject warning$23(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(174);
         throw Py.makeException(var1.getlocal(1));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _outputwrapper$24(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyObject var3 = var1.getglobal("codecs").__getattr__("lookup").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(3));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(184);
      var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writetext$25(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString var3 = PyString.fromInterned("xmlcharrefreplace");
      var1.getlocal(0).__setattr__((String)"errors", var3);
      var3 = null;
      var1.setline(189);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("escape").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.setline(190);
      var3 = PyString.fromInterned("strict");
      var1.getlocal(0).__setattr__((String)"errors", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writetext$26(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = var1.getglobal("escape").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(195);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
      } catch (Throwable var8) {
         label35: {
            PyException var9 = Py.setException(var8, var1);
            if (var9.match(var1.getglobal("UnicodeError"))) {
               var1.setline(197);
               PyObject var4 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(197);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     break label35;
                  }

                  var1.setlocal(3, var5);

                  try {
                     var1.setline(199);
                     var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(3));
                  } catch (Throwable var7) {
                     PyException var6 = Py.setException(var7, var1);
                     if (!var6.match(var1.getglobal("UnicodeError"))) {
                        throw var6;
                     }

                     var1.setline(201);
                     var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("&#%d;")._mod(var1.getglobal("ord").__call__(var2, var1.getlocal(3))));
                  }
               }
            }

            throw var9;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writeattr$27(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getlocal(1).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(205);
      PyDictionary var4;
      PyString var5;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(206);
         var3 = var1.getlocal(1).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(207);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._le(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(208);
            var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("\""), PyString.fromInterned("&quot;")});
            var1.setlocal(4, var4);
            var3 = null;
            var1.setline(209);
            var5 = PyString.fromInterned("\"");
            var1.setlocal(5, var5);
            var3 = null;
         } else {
            var1.setline(211);
            var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("'"), PyString.fromInterned("&apos;")});
            var1.setlocal(4, var4);
            var3 = null;
            var1.setline(212);
            var5 = PyString.fromInterned("'");
            var1.setlocal(5, var5);
            var3 = null;
         }
      } else {
         var1.setline(214);
         var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(215);
         var5 = PyString.fromInterned("\"");
         var1.setlocal(5, var5);
         var3 = null;
      }

      var1.setline(216);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(5));
      var1.setline(217);
      var1.getglobal("writetext").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(4));
      var1.setline(218);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject XMLGenerator$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(222);
      PyString var3 = PyString.fromInterned("xml.sax.saxutils.prefix%s");
      var1.setlocal("GENERATED_PREFIX", var3);
      var3 = null;
      var1.setline(224);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), PyString.fromInterned("iso-8859-1")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$29, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(239);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startDocument$30, (PyObject)null);
      var1.setlocal("startDocument", var5);
      var3 = null;
      var1.setline(243);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startPrefixMapping$31, (PyObject)null);
      var1.setlocal("startPrefixMapping", var5);
      var3 = null;
      var1.setline(248);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endPrefixMapping$32, (PyObject)null);
      var1.setlocal("endPrefixMapping", var5);
      var3 = null;
      var1.setline(252);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startElement$33, (PyObject)null);
      var1.setlocal("startElement", var5);
      var3 = null;
      var1.setline(259);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endElement$34, (PyObject)null);
      var1.setlocal("endElement", var5);
      var3 = null;
      var1.setline(262);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startElementNS$35, (PyObject)null);
      var1.setlocal("startElementNS", var5);
      var3 = null;
      var1.setline(297);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endElementNS$36, (PyObject)null);
      var1.setlocal("endElementNS", var5);
      var3 = null;
      var1.setline(309);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, characters$37, (PyObject)null);
      var1.setlocal("characters", var5);
      var3 = null;
      var1.setline(312);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ignorableWhitespace$38, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var5);
      var3 = null;
      var1.setline(315);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, processingInstruction$39, (PyObject)null);
      var1.setlocal("processingInstruction", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(226);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(227);
         var3 = var1.getlocal(3).__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(228);
      var1.getglobal("handler").__getattr__("ContentHandler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(229);
      var3 = var1.getglobal("_outputwrapper").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.getlocal(0).__setattr__("_out", var3);
      var3 = null;
      var1.setline(230);
      PyList var4 = new PyList(new PyObject[]{new PyDictionary(Py.EmptyObjects)});
      var1.getlocal(0).__setattr__((String)"_ns_contexts", var4);
      var3 = null;
      var1.setline(231);
      var3 = var1.getlocal(0).__getattr__("_ns_contexts").__getitem__(Py.newInteger(-1));
      var1.getlocal(0).__setattr__("_current_context", var3);
      var3 = null;
      var1.setline(232);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_undeclared_ns_maps", var4);
      var3 = null;
      var1.setline(233);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_encoding", var3);
      var3 = null;
      var1.setline(234);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_generated_prefix_ctr", var5);
      var3 = null;
      var1.setline(235);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDocument$30(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("<?xml version=\"1.0\" encoding=\"%s\"?>\n")._mod(var1.getlocal(0).__getattr__("_encoding")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startPrefixMapping$31(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      var1.getlocal(0).__getattr__("_ns_contexts").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_current_context").__getattr__("copy").__call__(var2));
      var1.setline(245);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("_current_context").__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.setline(246);
      var1.getlocal(0).__getattr__("_undeclared_ns_maps").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endPrefixMapping$32(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var3 = var1.getlocal(0).__getattr__("_ns_contexts").__getitem__(Py.newInteger(-1));
      var1.getlocal(0).__setattr__("_current_context", var3);
      var3 = null;
      var1.setline(250);
      var1.getlocal(0).__getattr__("_ns_contexts").__delitem__((PyObject)Py.newInteger(-1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$33(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("<")._add(var1.getlocal(1)));
      var1.setline(254);
      PyObject var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(254);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(257);
            var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(255);
         var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned(" %s=")._mod(var1.getlocal(1)));
         var1.setline(256);
         var1.getglobal("writeattr").__call__(var2, var1.getlocal(0).__getattr__("_out"), var1.getlocal(3));
      }
   }

   public PyObject endElement$34(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("</%s>")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElementNS$35(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(264);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(265);
         var3 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(267);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(269);
            var3 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned(":"))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(270);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("<")._add(var1.getlocal(1)));
      var1.setline(272);
      var3 = var1.getlocal(0).__getattr__("_undeclared_ns_maps").__iter__();

      while(true) {
         var1.setline(272);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var7;
         if (var4 == null) {
            var1.setline(277);
            PyList var8 = new PyList(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"_undeclared_ns_maps", var8);
            var3 = null;
            var1.setline(279);
            var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(279);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(295);
                  var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(1, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(280);
               var7 = var1.getlocal(1).__getitem__(Py.newInteger(0));
               var10000 = var7._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(281);
                  var7 = var1.getlocal(1).__getitem__(Py.newInteger(1));
                  var1.setlocal(1, var7);
                  var5 = null;
               } else {
                  var1.setline(282);
                  var7 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)));
                  var10000 = var7._is(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(286);
                     var7 = var1.getlocal(0).__getattr__("GENERATED_PREFIX")._mod(var1.getlocal(0).__getattr__("_generated_prefix_ctr"));
                     var1.setlocal(7, var7);
                     var5 = null;
                     var1.setline(287);
                     var7 = var1.getlocal(0).__getattr__("_generated_prefix_ctr")._add(Py.newInteger(1));
                     var1.getlocal(0).__setattr__("_generated_prefix_ctr", var7);
                     var5 = null;
                     var1.setline(288);
                     var7 = var1.getlocal(7)._add(PyString.fromInterned(":"))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
                     var1.setlocal(1, var7);
                     var5 = null;
                     var1.setline(289);
                     var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned(" xmlns:%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getglobal("quoteattr").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)))})));
                     var1.setline(290);
                     var7 = var1.getlocal(7);
                     var1.getlocal(0).__getattr__("_current_context").__setitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)), var7);
                     var5 = null;
                  } else {
                     var1.setline(292);
                     var7 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned(":"))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
                     var1.setlocal(1, var7);
                     var5 = null;
                  }
               }

               var1.setline(293);
               var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned(" %s=")._mod(var1.getlocal(1)));
               var1.setline(294);
               var1.getglobal("writeattr").__call__(var2, var1.getlocal(0).__getattr__("_out"), var1.getlocal(6));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(273);
         var7 = var1.getlocal(4);
         var10000 = var7._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(274);
            var10000 = var1.getlocal(0).__getattr__("_out").__getattr__("write");
            PyString var10002 = PyString.fromInterned(" xmlns=\"%s\"");
            Object var10003 = var1.getlocal(5);
            if (!((PyObject)var10003).__nonzero__()) {
               var10003 = PyString.fromInterned("");
            }

            var10000.__call__(var2, var10002._mod((PyObject)var10003));
         } else {
            var1.setline(276);
            var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned(" xmlns:%s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
         }
      }
   }

   public PyObject endElementNS$36(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(302);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(303);
         var3 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(304);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(306);
            var3 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned(":"))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(307);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("</%s>")._mod(var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$37(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      var1.getglobal("writetext").__call__(var2, var1.getlocal(0).__getattr__("_out"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$38(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$39(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("<?%s %s?>")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LexicalXMLGenerator$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A XMLGenerator that also supports the LexicalHandler interface"));
      var1.setline(320);
      PyString.fromInterned("A XMLGenerator that also supports the LexicalHandler interface");
      var1.setline(322);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("iso-8859-1")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$41, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$42, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(335);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDTD$43, (PyObject)null);
      var1.setlocal("startDTD", var4);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDTD$44, (PyObject)null);
      var1.setlocal("endDTD", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, comment$45, (PyObject)null);
      var1.setlocal("comment", var4);
      var3 = null;
      var1.setline(352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startCDATA$46, (PyObject)null);
      var1.setlocal("startCDATA", var4);
      var3 = null;
      var1.setline(356);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endCDATA$47, (PyObject)null);
      var1.setlocal("endCDATA", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      var1.getglobal("XMLGenerator").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(324);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_in_cdata", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$42(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      if (var1.getlocal(0).__getattr__("_in_cdata").__nonzero__()) {
         var1.setline(328);
         var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]]>"), (PyObject)PyString.fromInterned("]]>]]&gt;<![CDATA[")));
      } else {
         var1.setline(330);
         var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, var1.getglobal("escape").__call__(var2, var1.getlocal(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDTD$43(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, PyString.fromInterned("<!DOCTYPE %s")._mod(var1.getlocal(1)));
      var1.setline(337);
      PyObject var10000;
      PyString var10002;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(338);
         var10000 = var1.getlocal(0).__getattr__("_out").__getattr__("write");
         var10002 = PyString.fromInterned(" PUBLIC %s %s");
         PyTuple var10003 = new PyTuple;
         PyObject[] var10005 = new PyObject[2];
         PyObject var10008 = var1.getglobal("quoteattr");
         Object var10010 = var1.getlocal(2);
         if (!((PyObject)var10010).__nonzero__()) {
            var10010 = PyString.fromInterned("");
         }

         var10005[0] = var10008.__call__((ThreadState)var2, (PyObject)var10010);
         var10008 = var1.getglobal("quoteattr");
         var10010 = var1.getlocal(3);
         if (!((PyObject)var10010).__nonzero__()) {
            var10010 = PyString.fromInterned("");
         }

         var10005[1] = var10008.__call__((ThreadState)var2, (PyObject)var10010);
         var10003.<init>(var10005);
         var10000.__call__(var2, var10002._mod(var10003));
      } else {
         var1.setline(341);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(342);
            var10000 = var1.getlocal(0).__getattr__("_out").__getattr__("write");
            var10002 = PyString.fromInterned(" SYSTEM %s");
            PyObject var4 = var1.getglobal("quoteattr");
            Object var3 = var1.getlocal(3);
            if (!((PyObject)var3).__nonzero__()) {
               var3 = PyString.fromInterned("");
            }

            var10000.__call__(var2, var10002._mod(var4.__call__((ThreadState)var2, (PyObject)var3)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDTD$44(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject comment$45(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!--"));
      var1.setline(349);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(350);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-->"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startCDATA$46(PyFrame var1, ThreadState var2) {
      var1.setline(353);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_in_cdata", var3);
      var3 = null;
      var1.setline(354);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<![CDATA["));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endCDATA$47(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_in_cdata", var3);
      var3 = null;
      var1.setline(358);
      var1.getlocal(0).__getattr__("_out").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]]>"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ContentGenerator$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(364);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, characters$49, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject characters$49(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyObject var3 = var1.getglobal("XMLGenerator").__getattr__("characters").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3)), (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject XMLFilterBase$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class is designed to sit between an XMLReader and the\n    client application's event handlers.  By default, it does nothing\n    but pass requests up to the reader and events on to the handlers\n    unmodified, but subclasses can override specific methods to modify\n    the event stream or the configuration requests as they pass\n    through."));
      var1.setline(376);
      PyString.fromInterned("This class is designed to sit between an XMLReader and the\n    client application's event handlers.  By default, it does nothing\n    but pass requests up to the reader and events on to the handlers\n    unmodified, but subclasses can override specific methods to modify\n    the event stream or the configuration requests as they pass\n    through.");
      var1.setline(380);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, error$51, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(383);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$52, (PyObject)null);
      var1.setlocal("fatalError", var4);
      var3 = null;
      var1.setline(386);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$53, (PyObject)null);
      var1.setlocal("warning", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDocumentLocator$54, (PyObject)null);
      var1.setlocal("setDocumentLocator", var4);
      var3 = null;
      var1.setline(394);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDocument$55, (PyObject)null);
      var1.setlocal("startDocument", var4);
      var3 = null;
      var1.setline(397);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDocument$56, (PyObject)null);
      var1.setlocal("endDocument", var4);
      var3 = null;
      var1.setline(400);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startPrefixMapping$57, (PyObject)null);
      var1.setlocal("startPrefixMapping", var4);
      var3 = null;
      var1.setline(403);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endPrefixMapping$58, (PyObject)null);
      var1.setlocal("endPrefixMapping", var4);
      var3 = null;
      var1.setline(406);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$59, (PyObject)null);
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(409);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$60, (PyObject)null);
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(412);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElementNS$61, (PyObject)null);
      var1.setlocal("startElementNS", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElementNS$62, (PyObject)null);
      var1.setlocal("endElementNS", var4);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$63, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$64, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(424);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$65, (PyObject)null);
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(427);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, skippedEntity$66, (PyObject)null);
      var1.setlocal("skippedEntity", var4);
      var3 = null;
      var1.setline(432);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, notationDecl$67, (PyObject)null);
      var1.setlocal("notationDecl", var4);
      var3 = null;
      var1.setline(435);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unparsedEntityDecl$68, (PyObject)null);
      var1.setlocal("unparsedEntityDecl", var4);
      var3 = null;
      var1.setline(440);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, resolveEntity$69, (PyObject)null);
      var1.setlocal("resolveEntity", var4);
      var3 = null;
      var1.setline(445);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$70, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(452);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setLocale$71, (PyObject)null);
      var1.setlocal("setLocale", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getFeature$72, (PyObject)null);
      var1.setlocal("getFeature", var4);
      var3 = null;
      var1.setline(458);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setFeature$73, (PyObject)null);
      var1.setlocal("setFeature", var4);
      var3 = null;
      var1.setline(461);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getProperty$74, (PyObject)null);
      var1.setlocal("getProperty", var4);
      var3 = null;
      var1.setline(464);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setProperty$75, (PyObject)null);
      var1.setlocal("setProperty", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject error$51(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      var1.getlocal(0).__getattr__("_err_handler").__getattr__("error").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fatalError$52(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      var1.getlocal(0).__getattr__("_err_handler").__getattr__("fatalError").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$53(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      var1.getlocal(0).__getattr__("_err_handler").__getattr__("warning").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDocumentLocator$54(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("setDocumentLocator").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDocument$55(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startDocument").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDocument$56(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endDocument").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startPrefixMapping$57(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startPrefixMapping").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endPrefixMapping$58(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endPrefixMapping").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$59(PyFrame var1, ThreadState var2) {
      var1.setline(407);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startElement").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElement$60(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endElement").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElementNS$61(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startElementNS").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElementNS$62(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endElementNS").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$63(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("characters").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$64(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("ignorableWhitespace").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$65(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("processingInstruction").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject skippedEntity$66(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("skippedEntity").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject notationDecl$67(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      var1.getlocal(0).__getattr__("_dtd_handler").__getattr__("notationDecl").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unparsedEntityDecl$68(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      var1.getlocal(0).__getattr__("_dtd_handler").__getattr__("unparsedEntityDecl").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resolveEntity$69(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyObject var3 = var1.getlocal(0).__getattr__("_ent_handler").__getattr__("resolveEntity").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse$70(PyFrame var1, ThreadState var2) {
      var1.setline(446);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setContentHandler").__call__(var2, var1.getlocal(0));
      var1.setline(447);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setErrorHandler").__call__(var2, var1.getlocal(0));
      var1.setline(448);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setEntityResolver").__call__(var2, var1.getlocal(0));
      var1.setline(449);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setDTDHandler").__call__(var2, var1.getlocal(0));
      var1.setline(450);
      var1.getlocal(0).__getattr__("_parent").__getattr__("parse").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setLocale$71(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setLocale").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getFeature$72(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyObject var3 = var1.getlocal(0).__getattr__("_parent").__getattr__("getFeature").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setFeature$73(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setFeature").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getProperty$74(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyObject var3 = var1.getlocal(0).__getattr__("_parent").__getattr__("getProperty").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setProperty$75(PyFrame var1, ThreadState var2) {
      var1.setline(465);
      var1.getlocal(0).__getattr__("_parent").__getattr__("setProperty").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BaseIncrementalParser$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class implements the parse method of the XMLReader\n    interface using the feed, close and reset methods of the\n    IncrementalParser interface as a convenience to SAX 2.0 driver\n    writers."));
      var1.setline(476);
      PyString.fromInterned("This class implements the parse method of the XMLReader\n    interface using the feed, close and reset methods of the\n    IncrementalParser interface as a convenience to SAX 2.0 driver\n    writers.");
      var1.setline(478);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, parse$77, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(496);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prepareParser$78, PyString.fromInterned("This method is called by the parse implementation to allow\n        the SAX 2.0 driver to prepare itself for parsing."));
      var1.setlocal("prepareParser", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject parse$77(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      PyObject var3 = var1.getglobal("prepare_input_source").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(480);
      var1.getlocal(0).__getattr__("prepareParser").__call__(var2, var1.getlocal(1));
      var1.setline(482);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startDocument").__call__(var2);
      var1.setline(485);
      var3 = var1.getlocal(1).__getattr__("getByteStream").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(486);
      var3 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(16384));
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(487);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._ne(PyString.fromInterned(""));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(491);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(492);
            var1.getlocal(0).__getattr__("reset").__call__(var2);
            var1.setline(494);
            var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endDocument").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(488);
         var1.getlocal(0).__getattr__("feed").__call__(var2, var1.getlocal(3));
         var1.setline(489);
         var3 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(16384));
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject prepareParser$78(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyString.fromInterned("This method is called by the parse implementation to allow\n        the SAX 2.0 driver to prepare itself for parsing.");
      var1.setline(499);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prepareParser must be overridden!")));
   }

   public PyObject prepare_input_source$79(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      PyString.fromInterned("This function takes an InputSource and an optional base URL and\n    returns a fully resolved InputSource object ready for reading.");
      var1.setline(507);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._in(var1.getglobal("_StringTypes"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(508);
         var3 = var1.getglobal("xmlreader").__getattr__("InputSource").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      } else {
         var1.setline(509);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("read")).__nonzero__()) {
            var1.setline(510);
            var3 = var1.getlocal(0);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(511);
            var3 = var1.getglobal("xmlreader").__getattr__("InputSource").__call__(var2);
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(512);
            var1.getlocal(0).__getattr__("setByteStream").__call__(var2, var1.getlocal(2));
            var1.setline(513);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("name")).__nonzero__()) {
               var1.setline(514);
               var1.getlocal(0).__getattr__("setSystemId").__call__(var2, var1.getglobal("absolute_system_id").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getlocal(1)));
            }
         }
      }

      var1.setline(516);
      var3 = var1.getlocal(0).__getattr__("getByteStream").__call__(var2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(517);
         var3 = var1.getglobal("absolute_system_id").__call__(var2, var1.getlocal(0).__getattr__("getSystemId").__call__(var2), var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(518);
         var1.getlocal(0).__getattr__("setSystemId").__call__(var2, var1.getlocal(3));
         var1.setline(519);
         var3 = var1.getglobal("urllib2").__getattr__("urlopen").__call__(var2, var1.getlocal(3));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(520);
         var1.getlocal(0).__getattr__("setByteStream").__call__(var2, var1.getlocal(2));
      }

      var1.setline(522);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject absolute_system_id$80(PyFrame var1, ThreadState var2) {
      var1.setline(526);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(527);
         var3 = PyString.fromInterned("file:%s")._mod(var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0)));
         var1.setlocal(0, var3);
         var3 = null;
      } else {
         var1.setline(528);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(529);
            var3 = var1.getglobal("Absolutize").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(0, var3);
            var3 = null;
         }
      }

      var1.setline(530);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("IsAbsolute").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(531);
         var3 = var1.getglobal("MakeUrllibSafe").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject AttributeMap$81(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An implementation of AttributeList that takes an (attr,val) hash\n    and uses it to implement the AttributeList interface."));
      var1.setline(543);
      PyString.fromInterned("An implementation of AttributeList that takes an (attr,val) hash\n    and uses it to implement the AttributeList interface.");
      var1.setline(545);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$82, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(548);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLength$83, (PyObject)null);
      var1.setlocal("getLength", var4);
      var3 = null;
      var1.setline(551);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getName$84, (PyObject)null);
      var1.setlocal("getName", var4);
      var3 = null;
      var1.setline(557);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getType$85, (PyObject)null);
      var1.setlocal("getType", var4);
      var3 = null;
      var1.setline(560);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValue$86, (PyObject)null);
      var1.setlocal("getValue", var4);
      var3 = null;
      var1.setline(569);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$87, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(572);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$88, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$89, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(581);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$90, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(584);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$91, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(587);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$92, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(590);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$93, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(593);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$94, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$82(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("map", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getLength$83(PyFrame var1, ThreadState var2) {
      var1.setline(549);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("map").__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getName$84(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(553);
         var3 = var1.getlocal(0).__getattr__("map").__getattr__("keys").__call__(var2).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("IndexError"))) {
            PyObject var5 = var4.value;
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(555);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject getType$85(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      PyString var3 = PyString.fromInterned("CDATA");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getValue$86(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(562);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._eq(var1.getglobal("types").__getattr__("IntType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(563);
            var3 = var1.getlocal(0).__getattr__("map").__getitem__(var1.getlocal(0).__getattr__("getName").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(565);
            var3 = var1.getlocal(0).__getattr__("map").__getitem__(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            PyObject var5 = var4.value;
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(567);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __len__$87(PyFrame var1, ThreadState var2) {
      var1.setline(570);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$88(PyFrame var1, ThreadState var2) {
      var1.setline(573);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("types").__getattr__("IntType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(574);
         var3 = var1.getlocal(0).__getattr__("map").__getattr__("keys").__call__(var2).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(576);
         var3 = var1.getlocal(0).__getattr__("map").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject items$89(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyObject var3 = var1.getlocal(0).__getattr__("map").__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject keys$90(PyFrame var1, ThreadState var2) {
      var1.setline(582);
      PyObject var3 = var1.getlocal(0).__getattr__("map").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$91(PyFrame var1, ThreadState var2) {
      var1.setline(585);
      PyObject var3 = var1.getlocal(0).__getattr__("map").__getattr__("has_key").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$92(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyObject var3 = var1.getlocal(0).__getattr__("map").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy$93(PyFrame var1, ThreadState var2) {
      var1.setline(591);
      PyObject var3 = var1.getglobal("AttributeMap").__call__(var2, var1.getlocal(0).__getattr__("map").__getattr__("copy").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$94(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getattr__("map").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject EventBroadcaster$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Takes a list of objects and forwards any method calls received\n    to all objects in the list. The attribute list holds the list and\n    can freely be modified by clients."));
      var1.setline(601);
      PyString.fromInterned("Takes a list of objects and forwards any method calls received\n    to all objects in the list. The attribute list holds the list and\n    can freely be modified by clients.");
      var1.setline(603);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Event", var3, Event$96);
      var1.setlocal("Event", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(614);
      var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, __init__$99, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(617);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __getattr__$100, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __repr__$101, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Event$96(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Helper objects that represent event methods."));
      var1.setline(604);
      PyString.fromInterned("Helper objects that represent event methods.");
      var1.setline(606);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$97, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(610);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$98, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$97(PyFrame var1, ThreadState var2) {
      var1.setline(607);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("list", var3);
      var3 = null;
      var1.setline(608);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$98(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyObject var3 = var1.getlocal(0).__getattr__("list").__iter__();

      while(true) {
         var1.setline(611);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(612);
         var1.getglobal("apply").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("name")), var1.getlocal(1));
      }
   }

   public PyObject __init__$99(PyFrame var1, ThreadState var2) {
      var1.setline(615);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("list", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$100(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyObject var3 = var1.getlocal(0).__getattr__("Event").__call__(var2, var1.getlocal(0).__getattr__("list"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$101(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyObject var3 = PyString.fromInterned("<EventBroadcaster instance at %d>")._mod(var1.getglobal("id").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ESISDocHandler$102(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A SAX document handler that produces naive ESIS output."));
      var1.setline(626);
      PyString.fromInterned("A SAX document handler that produces naive ESIS output.");
      var1.setline(628);
      PyObject[] var3 = new PyObject[]{var1.getname("sys").__getattr__("stdout")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$103, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(631);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$104, PyString.fromInterned("Receive an event signalling that a processing instruction\n        has been found."));
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(636);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$105, PyString.fromInterned("Receive an event signalling the start of an element."));
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(642);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$106, PyString.fromInterned("Receive an event signalling the end of an element."));
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(646);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$107, PyString.fromInterned("Receive an event signalling that character data has been found."));
      var1.setlocal("characters", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$103(PyFrame var1, ThreadState var2) {
      var1.setline(629);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("writer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$104(PyFrame var1, ThreadState var2) {
      var1.setline(633);
      PyString.fromInterned("Receive an event signalling that a processing instruction\n        has been found.");
      var1.setline(634);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("?")._add(var1.getlocal(1))._add(PyString.fromInterned(" "))._add(var1.getlocal(2))._add(PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$105(PyFrame var1, ThreadState var2) {
      var1.setline(637);
      PyString.fromInterned("Receive an event signalling the start of an element.");
      var1.setline(638);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("(")._add(var1.getlocal(1))._add(PyString.fromInterned("\n")));
      var1.setline(639);
      PyObject var3 = var1.getlocal(2).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(639);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(640);
         var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("A")._add(var1.getlocal(3))._add(PyString.fromInterned(" "))._add(var1.getlocal(2).__getitem__(var1.getlocal(3)))._add(PyString.fromInterned("\n")));
      }
   }

   public PyObject endElement$106(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      PyString.fromInterned("Receive an event signalling the end of an element.");
      var1.setline(644);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned(")")._add(var1.getlocal(1))._add(PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$107(PyFrame var1, ThreadState var2) {
      var1.setline(647);
      PyString.fromInterned("Receive an event signalling that character data has been found.");
      var1.setline(648);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("-")._add(var1.getlocal(1).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3)), (PyObject)null))._add(PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Canonizer$108(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A SAX document handler that produces canonized XML output."));
      var1.setline(653);
      PyString.fromInterned("A SAX document handler that produces canonized XML output.");
      var1.setline(655);
      PyObject[] var3 = new PyObject[]{var1.getname("sys").__getattr__("stdout")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$109, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(659);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$110, (PyObject)null);
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(663);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$111, (PyObject)null);
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(676);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$112, (PyObject)null);
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(680);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$113, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(683);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$114, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(687);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_data$115, PyString.fromInterned("Writes datachars to writer."));
      var1.setlocal("write_data", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$109(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"elem_level", var3);
      var3 = null;
      var1.setline(657);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("writer", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$110(PyFrame var1, ThreadState var2) {
      var1.setline(660);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("xml"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(661);
         var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("<?")._add(var1.getlocal(1))._add(PyString.fromInterned(" "))._add(var1.getlocal(2))._add(PyString.fromInterned("?>")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$111(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("<")._add(var1.getlocal(1)));
      var1.setline(666);
      PyObject var3 = var1.getlocal(2).__getattr__("keys").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(667);
      var1.getlocal(3).__getattr__("sort").__call__(var2);
      var1.setline(669);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(669);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(673);
            var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
            var1.setline(674);
            var3 = var1.getlocal(0).__getattr__("elem_level")._add(Py.newInteger(1));
            var1.getlocal(0).__setattr__("elem_level", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(670);
         var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned(" ")._add(var1.getlocal(4))._add(PyString.fromInterned("=\"")));
         var1.setline(671);
         var1.getlocal(0).__getattr__("write_data").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(4)));
         var1.setline(672);
         var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
      }
   }

   public PyObject endElement$112(PyFrame var1, ThreadState var2) {
      var1.setline(677);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, PyString.fromInterned("</")._add(var1.getlocal(1))._add(PyString.fromInterned(">")));
      var1.setline(678);
      PyObject var3 = var1.getlocal(0).__getattr__("elem_level")._sub(Py.newInteger(1));
      var1.getlocal(0).__setattr__("elem_level", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$113(PyFrame var1, ThreadState var2) {
      var1.setline(681);
      var1.getlocal(0).__getattr__("characters").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$114(PyFrame var1, ThreadState var2) {
      var1.setline(684);
      PyObject var3 = var1.getlocal(0).__getattr__("elem_level");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(685);
         var1.getlocal(0).__getattr__("write_data").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3)), (PyObject)null));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_data$115(PyFrame var1, ThreadState var2) {
      var1.setline(688);
      PyString.fromInterned("Writes datachars to writer.");
      var1.setline(689);
      PyObject var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(690);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(691);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(692);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(693);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(9)), (PyObject)PyString.fromInterned("&#9;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(694);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(10)), (PyObject)PyString.fromInterned("&#10;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(695);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(13)), (PyObject)PyString.fromInterned("&#13;"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(696);
      var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mllib$116(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A re-implementation of the htmllib, sgmllib and xmllib interfaces as a\n    SAX DocumentHandler."));
      var1.setline(702);
      PyString.fromInterned("A re-implementation of the htmllib, sgmllib and xmllib interfaces as a\n    SAX DocumentHandler.");
      var1.setline(716);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, __init__$117, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(719);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, reset$118, (PyObject)null);
      var1.setlocal("reset", var5);
      var3 = null;
      var1.setline(725);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, feed$119, (PyObject)null);
      var1.setlocal("feed", var5);
      var3 = null;
      var1.setline(728);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, close$120, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(731);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_stack$121, (PyObject)null);
      var1.setlocal("get_stack", var5);
      var3 = null;
      var1.setline(736);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, handle_starttag$122, (PyObject)null);
      var1.setlocal("handle_starttag", var5);
      var3 = null;
      var1.setline(739);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, handle_endtag$123, (PyObject)null);
      var1.setlocal("handle_endtag", var5);
      var3 = null;
      var1.setline(742);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, handle_data$124, (PyObject)null);
      var1.setlocal("handle_data", var5);
      var3 = null;
      var1.setline(745);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, handle_proc$125, (PyObject)null);
      var1.setlocal("handle_proc", var5);
      var3 = null;
      var1.setline(748);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, unknown_starttag$126, (PyObject)null);
      var1.setlocal("unknown_starttag", var5);
      var3 = null;
      var1.setline(751);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, unknown_endtag$127, (PyObject)null);
      var1.setlocal("unknown_endtag", var5);
      var3 = null;
      var1.setline(754);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, syntax_error$128, (PyObject)null);
      var1.setlocal("syntax_error", var5);
      var3 = null;
      var1.setline(759);
      var3 = new PyObject[]{var1.getname("saxlib").__getattr__("DocumentHandler"), var1.getname("saxlib").__getattr__("ErrorHandler")};
      PyObject var4 = Py.makeClass("Handler", var3, Handler$129);
      var1.setlocal("Handler", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      return var1.getf_locals();
   }

   public PyObject __init__$117(PyFrame var1, ThreadState var2) {
      var1.setline(717);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$118(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      PyObject var3 = imp.importOne("saxexts", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(721);
      var3 = var1.getlocal(1).__getattr__("XMLParserFactory").__getattr__("make_parser").__call__(var2);
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.setline(722);
      var3 = var1.getglobal("mllib").__getattr__("Handler").__call__(var2, var1.getlocal(0).__getattr__("parser"), var1.getlocal(0));
      var1.getlocal(0).__setattr__("handler", var3);
      var3 = null;
      var1.setline(723);
      var1.getlocal(0).__getattr__("handler").__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$119(PyFrame var1, ThreadState var2) {
      var1.setline(726);
      var1.getlocal(0).__getattr__("parser").__getattr__("feed").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$120(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      var1.getlocal(0).__getattr__("parser").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_stack$121(PyFrame var1, ThreadState var2) {
      var1.setline(732);
      PyObject var3 = var1.getlocal(0).__getattr__("handler").__getattr__("get_stack").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handle_starttag$122(PyFrame var1, ThreadState var2) {
      var1.setline(737);
      var1.getlocal(2).__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_endtag$123(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      var1.getlocal(2).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_data$124(PyFrame var1, ThreadState var2) {
      var1.setline(743);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_proc$125(PyFrame var1, ThreadState var2) {
      var1.setline(746);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_starttag$126(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_endtag$127(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject syntax_error$128(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Handler$129(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An internal class to handle SAX events and translate them to mllib\n        events."));
      var1.setline(761);
      PyString.fromInterned("An internal class to handle SAX events and translate them to mllib\n        events.");
      var1.setline(763);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$130, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(770);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_stack$131, (PyObject)null);
      var1.setlocal("get_stack", var4);
      var3 = null;
      var1.setline(773);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$132, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(778);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$133, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(781);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$134, (PyObject)null);
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(790);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$135, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(793);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$136, (PyObject)null);
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(796);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$137, (PyObject)null);
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(809);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$138, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(812);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$139, (PyObject)null);
      var1.setlocal("fatalError", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$130(PyFrame var1, ThreadState var2) {
      var1.setline(764);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("driver", var3);
      var3 = null;
      var1.setline(765);
      var1.getlocal(0).__getattr__("driver").__getattr__("setDocumentHandler").__call__(var2, var1.getlocal(0));
      var1.setline(766);
      var1.getlocal(0).__getattr__("driver").__getattr__("setErrorHandler").__call__(var2, var1.getlocal(0));
      var1.setline(767);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("handler", var3);
      var3 = null;
      var1.setline(768);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_stack$131(PyFrame var1, ThreadState var2) {
      var1.setline(771);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$132(PyFrame var1, ThreadState var2) {
      var1.setline(774);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$133(PyFrame var1, ThreadState var2) {
      var1.setline(779);
      var1.getlocal(0).__getattr__("handler").__getattr__("handle_data").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3)), (PyObject)null));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElement$134(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0).__getattr__("handler"), PyString.fromInterned("end_")._add(var1.getlocal(1))).__nonzero__()) {
         var1.setline(783);
         var1.getlocal(0).__getattr__("handler").__getattr__("handle_endtag").__call__(var2, var1.getlocal(1), var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("handler"), PyString.fromInterned("end_")._add(var1.getlocal(1))));
      } else {
         var1.setline(786);
         var1.getlocal(0).__getattr__("handler").__getattr__("unknown_endtag").__call__(var2, var1.getlocal(1));
      }

      var1.setline(788);
      var1.getlocal(0).__getattr__("stack").__delitem__((PyObject)Py.newInteger(-1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$135(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      var1.getlocal(0).__getattr__("handler").__getattr__("handle_data").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3)), (PyObject)null));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$136(PyFrame var1, ThreadState var2) {
      var1.setline(794);
      var1.getlocal(0).__getattr__("handler").__getattr__("handle_proc").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$137(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(799);
      if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0).__getattr__("handler"), PyString.fromInterned("start_")._add(var1.getlocal(1))).__nonzero__()) {
         var1.setline(800);
         var1.getlocal(0).__getattr__("handler").__getattr__("handle_starttag").__call__(var2, var1.getlocal(1), var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("handler"), PyString.fromInterned("start_")._add(var1.getlocal(1))), var1.getlocal(2));
      } else {
         var1.setline(805);
         var1.getlocal(0).__getattr__("handler").__getattr__("unknown_starttag").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$138(PyFrame var1, ThreadState var2) {
      var1.setline(810);
      var1.getlocal(0).__getattr__("handler").__getattr__("syntax_error").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fatalError$139(PyFrame var1, ThreadState var2) {
      var1.setline(813);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1))));
   }

   public saxutils$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "d", "key", "value"};
      __dict_replace$1 = Py.newCode(2, var2, var1, "__dict_replace", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "entities"};
      escape$2 = Py.newCode(2, var2, var1, "escape", 25, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "entities"};
      unescape$3 = Py.newCode(2, var2, var1, "unescape", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "entities"};
      quoteattr$4 = Py.newCode(2, var2, var1, "quoteattr", 53, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DefaultHandler$5 = Py.newCode(0, var2, var1, "DefaultHandler", 76, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Location$6 = Py.newCode(0, var2, var1, "Location", 85, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "locator"};
      __init__$7 = Py.newCode(2, var2, var1, "__init__", 90, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getColumnNumber$8 = Py.newCode(1, var2, var1, "getColumnNumber", 96, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLineNumber$9 = Py.newCode(1, var2, var1, "getLineNumber", 99, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getPublicId$10 = Py.newCode(1, var2, var1, "getPublicId", 102, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getSystemId$11 = Py.newCode(1, var2, var1, "getSystemId", 105, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "col"};
      __str__$12 = Py.newCode(1, var2, var1, "__str__", 108, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ErrorPrinter$13 = Py.newCode(0, var2, var1, "ErrorPrinter", 123, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "level", "outfile"};
      __init__$14 = Py.newCode(3, var2, var1, "__init__", 126, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      warning$15 = Py.newCode(2, var2, var1, "warning", 130, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      error$16 = Py.newCode(2, var2, var1, "error", 136, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      fatalError$17 = Py.newCode(2, var2, var1, "fatalError", 142, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      _ErrorPrinter__getpos$18 = Py.newCode(2, var2, var1, "_ErrorPrinter__getpos", 148, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ErrorRaiser$19 = Py.newCode(0, var2, var1, "ErrorRaiser", 158, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "level"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 161, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      error$21 = Py.newCode(2, var2, var1, "error", 164, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      fatalError$22 = Py.newCode(2, var2, var1, "fatalError", 168, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      warning$23 = Py.newCode(2, var2, var1, "warning", 172, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stream", "encoding", "writerclass"};
      _outputwrapper$24 = Py.newCode(2, var2, var1, "_outputwrapper", 182, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stream", "text", "entities"};
      writetext$25 = Py.newCode(3, var2, var1, "writetext", 187, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stream", "text", "entities", "c"};
      writetext$26 = Py.newCode(3, var2, var1, "writetext", 192, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stream", "text", "countdouble", "countsingle", "entities", "quote"};
      writeattr$27 = Py.newCode(2, var2, var1, "writeattr", 203, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      XMLGenerator$28 = Py.newCode(0, var2, var1, "XMLGenerator", 221, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "out", "encoding", "sys"};
      __init__$29 = Py.newCode(3, var2, var1, "__init__", 224, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startDocument$30 = Py.newCode(1, var2, var1, "startDocument", 239, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      startPrefixMapping$31 = Py.newCode(3, var2, var1, "startPrefixMapping", 243, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      endPrefixMapping$32 = Py.newCode(2, var2, var1, "endPrefixMapping", 248, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attrs", "value"};
      startElement$33 = Py.newCode(3, var2, var1, "startElement", 252, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$34 = Py.newCode(2, var2, var1, "endElement", 259, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "qname", "attrs", "k", "v", "value", "prefix"};
      startElementNS$35 = Py.newCode(4, var2, var1, "startElementNS", 262, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "qname"};
      endElementNS$36 = Py.newCode(3, var2, var1, "endElementNS", 297, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      characters$37 = Py.newCode(2, var2, var1, "characters", 309, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      ignorableWhitespace$38 = Py.newCode(2, var2, var1, "ignorableWhitespace", 312, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$39 = Py.newCode(3, var2, var1, "processingInstruction", 315, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LexicalXMLGenerator$40 = Py.newCode(0, var2, var1, "LexicalXMLGenerator", 319, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "out", "encoding"};
      __init__$41 = Py.newCode(3, var2, var1, "__init__", 322, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      characters$42 = Py.newCode(2, var2, var1, "characters", 326, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "public_id", "system_id"};
      startDTD$43 = Py.newCode(4, var2, var1, "startDTD", 335, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDTD$44 = Py.newCode(1, var2, var1, "endDTD", 344, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      comment$45 = Py.newCode(2, var2, var1, "comment", 347, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startCDATA$46 = Py.newCode(1, var2, var1, "startCDATA", 352, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endCDATA$47 = Py.newCode(1, var2, var1, "endCDATA", 356, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ContentGenerator$48 = Py.newCode(0, var2, var1, "ContentGenerator", 362, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "str", "start", "end"};
      characters$49 = Py.newCode(4, var2, var1, "characters", 364, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      XMLFilterBase$50 = Py.newCode(0, var2, var1, "XMLFilterBase", 370, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "exception"};
      error$51 = Py.newCode(2, var2, var1, "error", 380, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      fatalError$52 = Py.newCode(2, var2, var1, "fatalError", 383, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      warning$53 = Py.newCode(2, var2, var1, "warning", 386, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locator"};
      setDocumentLocator$54 = Py.newCode(2, var2, var1, "setDocumentLocator", 391, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startDocument$55 = Py.newCode(1, var2, var1, "startDocument", 394, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDocument$56 = Py.newCode(1, var2, var1, "endDocument", 397, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      startPrefixMapping$57 = Py.newCode(3, var2, var1, "startPrefixMapping", 400, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      endPrefixMapping$58 = Py.newCode(2, var2, var1, "endPrefixMapping", 403, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attrs"};
      startElement$59 = Py.newCode(3, var2, var1, "startElement", 406, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$60 = Py.newCode(2, var2, var1, "endElement", 409, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "qname", "attrs"};
      startElementNS$61 = Py.newCode(4, var2, var1, "startElementNS", 412, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "qname"};
      endElementNS$62 = Py.newCode(3, var2, var1, "endElementNS", 415, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      characters$63 = Py.newCode(2, var2, var1, "characters", 418, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars"};
      ignorableWhitespace$64 = Py.newCode(2, var2, var1, "ignorableWhitespace", 421, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$65 = Py.newCode(3, var2, var1, "processingInstruction", 424, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      skippedEntity$66 = Py.newCode(2, var2, var1, "skippedEntity", 427, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId"};
      notationDecl$67 = Py.newCode(4, var2, var1, "notationDecl", 432, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "ndata"};
      unparsedEntityDecl$68 = Py.newCode(5, var2, var1, "unparsedEntityDecl", 435, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "publicId", "systemId"};
      resolveEntity$69 = Py.newCode(3, var2, var1, "resolveEntity", 440, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source"};
      parse$70 = Py.newCode(2, var2, var1, "parse", 445, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locale"};
      setLocale$71 = Py.newCode(2, var2, var1, "setLocale", 452, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getFeature$72 = Py.newCode(2, var2, var1, "getFeature", 455, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "state"};
      setFeature$73 = Py.newCode(3, var2, var1, "setFeature", 458, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getProperty$74 = Py.newCode(2, var2, var1, "getProperty", 461, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      setProperty$75 = Py.newCode(3, var2, var1, "setProperty", 464, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseIncrementalParser$76 = Py.newCode(0, var2, var1, "BaseIncrementalParser", 472, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "inf", "buffer"};
      parse$77 = Py.newCode(2, var2, var1, "parse", 478, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source"};
      prepareParser$78 = Py.newCode(2, var2, var1, "prepareParser", 496, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "base", "f", "sysid"};
      prepare_input_source$79 = Py.newCode(2, var2, var1, "prepare_input_source", 503, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sysid", "base"};
      absolute_system_id$80 = Py.newCode(2, var2, var1, "absolute_system_id", 525, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AttributeMap$81 = Py.newCode(0, var2, var1, "AttributeMap", 541, false, false, self, 81, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "map"};
      __init__$82 = Py.newCode(2, var2, var1, "__init__", 545, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLength$83 = Py.newCode(1, var2, var1, "getLength", 548, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "e"};
      getName$84 = Py.newCode(2, var2, var1, "getName", 551, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      getType$85 = Py.newCode(2, var2, var1, "getType", 557, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "e"};
      getValue$86 = Py.newCode(2, var2, var1, "getValue", 560, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$87 = Py.newCode(1, var2, var1, "__len__", 569, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __getitem__$88 = Py.newCode(2, var2, var1, "__getitem__", 572, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$89 = Py.newCode(1, var2, var1, "items", 578, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$90 = Py.newCode(1, var2, var1, "keys", 581, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$91 = Py.newCode(2, var2, var1, "has_key", 584, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "alternative"};
      get$92 = Py.newCode(3, var2, var1, "get", 587, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$93 = Py.newCode(1, var2, var1, "copy", 590, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$94 = Py.newCode(1, var2, var1, "values", 593, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EventBroadcaster$95 = Py.newCode(0, var2, var1, "EventBroadcaster", 598, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Event$96 = Py.newCode(0, var2, var1, "Event", 603, false, false, self, 96, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "list", "name"};
      __init__$97 = Py.newCode(3, var2, var1, "__init__", 606, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rest", "obj"};
      __call__$98 = Py.newCode(2, var2, var1, "__call__", 610, true, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list"};
      __init__$99 = Py.newCode(2, var2, var1, "__init__", 614, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$100 = Py.newCode(2, var2, var1, "__getattr__", 617, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$101 = Py.newCode(1, var2, var1, "__repr__", 620, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ESISDocHandler$102 = Py.newCode(0, var2, var1, "ESISDocHandler", 625, false, false, self, 102, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "writer"};
      __init__$103 = Py.newCode(2, var2, var1, "__init__", 628, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "remainder"};
      processingInstruction$104 = Py.newCode(3, var2, var1, "processingInstruction", 631, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "amap", "a_name"};
      startElement$105 = Py.newCode(3, var2, var1, "startElement", 636, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$106 = Py.newCode(2, var2, var1, "endElement", 642, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "start_ix", "length"};
      characters$107 = Py.newCode(4, var2, var1, "characters", 646, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Canonizer$108 = Py.newCode(0, var2, var1, "Canonizer", 652, false, false, self, 108, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "writer"};
      __init__$109 = Py.newCode(2, var2, var1, "__init__", 655, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "remainder"};
      processingInstruction$110 = Py.newCode(3, var2, var1, "processingInstruction", 659, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "amap", "a_names", "a_name"};
      startElement$111 = Py.newCode(3, var2, var1, "startElement", 663, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$112 = Py.newCode(2, var2, var1, "endElement", 676, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "start_ix", "length"};
      ignorableWhitespace$113 = Py.newCode(4, var2, var1, "ignorableWhitespace", 680, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "start_ix", "length"};
      characters$114 = Py.newCode(4, var2, var1, "characters", 683, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write_data$115 = Py.newCode(2, var2, var1, "write_data", 687, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      mllib$116 = Py.newCode(0, var2, var1, "mllib", 700, false, false, self, 116, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$117 = Py.newCode(1, var2, var1, "__init__", 716, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "saxexts"};
      reset$118 = Py.newCode(1, var2, var1, "reset", 719, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$119 = Py.newCode(2, var2, var1, "feed", 725, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$120 = Py.newCode(1, var2, var1, "close", 728, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stack$121 = Py.newCode(1, var2, var1, "get_stack", 731, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "method", "atts"};
      handle_starttag$122 = Py.newCode(4, var2, var1, "handle_starttag", 736, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "method"};
      handle_endtag$123 = Py.newCode(3, var2, var1, "handle_endtag", 739, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$124 = Py.newCode(2, var2, var1, "handle_data", 742, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      handle_proc$125 = Py.newCode(3, var2, var1, "handle_proc", 745, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "atts"};
      unknown_starttag$126 = Py.newCode(3, var2, var1, "unknown_starttag", 748, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      unknown_endtag$127 = Py.newCode(2, var2, var1, "unknown_endtag", 751, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      syntax_error$128 = Py.newCode(2, var2, var1, "syntax_error", 754, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Handler$129 = Py.newCode(0, var2, var1, "Handler", 759, false, false, self, 129, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "driver", "handler"};
      __init__$130 = Py.newCode(3, var2, var1, "__init__", 763, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stack$131 = Py.newCode(1, var2, var1, "get_stack", 770, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$132 = Py.newCode(1, var2, var1, "reset", 773, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ch", "start", "length"};
      characters$133 = Py.newCode(4, var2, var1, "characters", 778, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$134 = Py.newCode(2, var2, var1, "endElement", 781, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ch", "start", "length"};
      ignorableWhitespace$135 = Py.newCode(4, var2, var1, "ignorableWhitespace", 790, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$136 = Py.newCode(3, var2, var1, "processingInstruction", 793, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "atts"};
      startElement$137 = Py.newCode(3, var2, var1, "startElement", 796, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      error$138 = Py.newCode(2, var2, var1, "error", 809, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      fatalError$139 = Py.newCode(2, var2, var1, "fatalError", 812, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new saxutils$py("xml/sax/saxutils$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(saxutils$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.__dict_replace$1(var2, var3);
         case 2:
            return this.escape$2(var2, var3);
         case 3:
            return this.unescape$3(var2, var3);
         case 4:
            return this.quoteattr$4(var2, var3);
         case 5:
            return this.DefaultHandler$5(var2, var3);
         case 6:
            return this.Location$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.getColumnNumber$8(var2, var3);
         case 9:
            return this.getLineNumber$9(var2, var3);
         case 10:
            return this.getPublicId$10(var2, var3);
         case 11:
            return this.getSystemId$11(var2, var3);
         case 12:
            return this.__str__$12(var2, var3);
         case 13:
            return this.ErrorPrinter$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.warning$15(var2, var3);
         case 16:
            return this.error$16(var2, var3);
         case 17:
            return this.fatalError$17(var2, var3);
         case 18:
            return this._ErrorPrinter__getpos$18(var2, var3);
         case 19:
            return this.ErrorRaiser$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.error$21(var2, var3);
         case 22:
            return this.fatalError$22(var2, var3);
         case 23:
            return this.warning$23(var2, var3);
         case 24:
            return this._outputwrapper$24(var2, var3);
         case 25:
            return this.writetext$25(var2, var3);
         case 26:
            return this.writetext$26(var2, var3);
         case 27:
            return this.writeattr$27(var2, var3);
         case 28:
            return this.XMLGenerator$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.startDocument$30(var2, var3);
         case 31:
            return this.startPrefixMapping$31(var2, var3);
         case 32:
            return this.endPrefixMapping$32(var2, var3);
         case 33:
            return this.startElement$33(var2, var3);
         case 34:
            return this.endElement$34(var2, var3);
         case 35:
            return this.startElementNS$35(var2, var3);
         case 36:
            return this.endElementNS$36(var2, var3);
         case 37:
            return this.characters$37(var2, var3);
         case 38:
            return this.ignorableWhitespace$38(var2, var3);
         case 39:
            return this.processingInstruction$39(var2, var3);
         case 40:
            return this.LexicalXMLGenerator$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this.characters$42(var2, var3);
         case 43:
            return this.startDTD$43(var2, var3);
         case 44:
            return this.endDTD$44(var2, var3);
         case 45:
            return this.comment$45(var2, var3);
         case 46:
            return this.startCDATA$46(var2, var3);
         case 47:
            return this.endCDATA$47(var2, var3);
         case 48:
            return this.ContentGenerator$48(var2, var3);
         case 49:
            return this.characters$49(var2, var3);
         case 50:
            return this.XMLFilterBase$50(var2, var3);
         case 51:
            return this.error$51(var2, var3);
         case 52:
            return this.fatalError$52(var2, var3);
         case 53:
            return this.warning$53(var2, var3);
         case 54:
            return this.setDocumentLocator$54(var2, var3);
         case 55:
            return this.startDocument$55(var2, var3);
         case 56:
            return this.endDocument$56(var2, var3);
         case 57:
            return this.startPrefixMapping$57(var2, var3);
         case 58:
            return this.endPrefixMapping$58(var2, var3);
         case 59:
            return this.startElement$59(var2, var3);
         case 60:
            return this.endElement$60(var2, var3);
         case 61:
            return this.startElementNS$61(var2, var3);
         case 62:
            return this.endElementNS$62(var2, var3);
         case 63:
            return this.characters$63(var2, var3);
         case 64:
            return this.ignorableWhitespace$64(var2, var3);
         case 65:
            return this.processingInstruction$65(var2, var3);
         case 66:
            return this.skippedEntity$66(var2, var3);
         case 67:
            return this.notationDecl$67(var2, var3);
         case 68:
            return this.unparsedEntityDecl$68(var2, var3);
         case 69:
            return this.resolveEntity$69(var2, var3);
         case 70:
            return this.parse$70(var2, var3);
         case 71:
            return this.setLocale$71(var2, var3);
         case 72:
            return this.getFeature$72(var2, var3);
         case 73:
            return this.setFeature$73(var2, var3);
         case 74:
            return this.getProperty$74(var2, var3);
         case 75:
            return this.setProperty$75(var2, var3);
         case 76:
            return this.BaseIncrementalParser$76(var2, var3);
         case 77:
            return this.parse$77(var2, var3);
         case 78:
            return this.prepareParser$78(var2, var3);
         case 79:
            return this.prepare_input_source$79(var2, var3);
         case 80:
            return this.absolute_system_id$80(var2, var3);
         case 81:
            return this.AttributeMap$81(var2, var3);
         case 82:
            return this.__init__$82(var2, var3);
         case 83:
            return this.getLength$83(var2, var3);
         case 84:
            return this.getName$84(var2, var3);
         case 85:
            return this.getType$85(var2, var3);
         case 86:
            return this.getValue$86(var2, var3);
         case 87:
            return this.__len__$87(var2, var3);
         case 88:
            return this.__getitem__$88(var2, var3);
         case 89:
            return this.items$89(var2, var3);
         case 90:
            return this.keys$90(var2, var3);
         case 91:
            return this.has_key$91(var2, var3);
         case 92:
            return this.get$92(var2, var3);
         case 93:
            return this.copy$93(var2, var3);
         case 94:
            return this.values$94(var2, var3);
         case 95:
            return this.EventBroadcaster$95(var2, var3);
         case 96:
            return this.Event$96(var2, var3);
         case 97:
            return this.__init__$97(var2, var3);
         case 98:
            return this.__call__$98(var2, var3);
         case 99:
            return this.__init__$99(var2, var3);
         case 100:
            return this.__getattr__$100(var2, var3);
         case 101:
            return this.__repr__$101(var2, var3);
         case 102:
            return this.ESISDocHandler$102(var2, var3);
         case 103:
            return this.__init__$103(var2, var3);
         case 104:
            return this.processingInstruction$104(var2, var3);
         case 105:
            return this.startElement$105(var2, var3);
         case 106:
            return this.endElement$106(var2, var3);
         case 107:
            return this.characters$107(var2, var3);
         case 108:
            return this.Canonizer$108(var2, var3);
         case 109:
            return this.__init__$109(var2, var3);
         case 110:
            return this.processingInstruction$110(var2, var3);
         case 111:
            return this.startElement$111(var2, var3);
         case 112:
            return this.endElement$112(var2, var3);
         case 113:
            return this.ignorableWhitespace$113(var2, var3);
         case 114:
            return this.characters$114(var2, var3);
         case 115:
            return this.write_data$115(var2, var3);
         case 116:
            return this.mllib$116(var2, var3);
         case 117:
            return this.__init__$117(var2, var3);
         case 118:
            return this.reset$118(var2, var3);
         case 119:
            return this.feed$119(var2, var3);
         case 120:
            return this.close$120(var2, var3);
         case 121:
            return this.get_stack$121(var2, var3);
         case 122:
            return this.handle_starttag$122(var2, var3);
         case 123:
            return this.handle_endtag$123(var2, var3);
         case 124:
            return this.handle_data$124(var2, var3);
         case 125:
            return this.handle_proc$125(var2, var3);
         case 126:
            return this.unknown_starttag$126(var2, var3);
         case 127:
            return this.unknown_endtag$127(var2, var3);
         case 128:
            return this.syntax_error$128(var2, var3);
         case 129:
            return this.Handler$129(var2, var3);
         case 130:
            return this.__init__$130(var2, var3);
         case 131:
            return this.get_stack$131(var2, var3);
         case 132:
            return this.reset$132(var2, var3);
         case 133:
            return this.characters$133(var2, var3);
         case 134:
            return this.endElement$134(var2, var3);
         case 135:
            return this.ignorableWhitespace$135(var2, var3);
         case 136:
            return this.processingInstruction$136(var2, var3);
         case 137:
            return this.startElement$137(var2, var3);
         case 138:
            return this.error$138(var2, var3);
         case 139:
            return this.fatalError$139(var2, var3);
         default:
            return null;
      }
   }
}
