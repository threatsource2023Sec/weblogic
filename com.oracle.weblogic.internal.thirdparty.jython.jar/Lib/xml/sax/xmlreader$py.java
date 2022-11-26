package xml.sax;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/sax/xmlreader.py")
public class xmlreader$py extends PyFunctionTable implements PyRunnable {
   static xmlreader$py self;
   static final PyCode f$0;
   static final PyCode XMLReader$1;
   static final PyCode __init__$2;
   static final PyCode parse$3;
   static final PyCode getContentHandler$4;
   static final PyCode setContentHandler$5;
   static final PyCode getDTDHandler$6;
   static final PyCode setDTDHandler$7;
   static final PyCode getEntityResolver$8;
   static final PyCode setEntityResolver$9;
   static final PyCode getErrorHandler$10;
   static final PyCode setErrorHandler$11;
   static final PyCode setLocale$12;
   static final PyCode getFeature$13;
   static final PyCode setFeature$14;
   static final PyCode getProperty$15;
   static final PyCode setProperty$16;
   static final PyCode IncrementalParser$17;
   static final PyCode __init__$18;
   static final PyCode parse$19;
   static final PyCode feed$20;
   static final PyCode prepareParser$21;
   static final PyCode close$22;
   static final PyCode reset$23;
   static final PyCode Locator$24;
   static final PyCode getColumnNumber$25;
   static final PyCode getLineNumber$26;
   static final PyCode getPublicId$27;
   static final PyCode getSystemId$28;
   static final PyCode InputSource$29;
   static final PyCode __init__$30;
   static final PyCode setPublicId$31;
   static final PyCode getPublicId$32;
   static final PyCode setSystemId$33;
   static final PyCode getSystemId$34;
   static final PyCode setEncoding$35;
   static final PyCode getEncoding$36;
   static final PyCode setByteStream$37;
   static final PyCode getByteStream$38;
   static final PyCode setCharacterStream$39;
   static final PyCode getCharacterStream$40;
   static final PyCode AttributesImpl$41;
   static final PyCode __init__$42;
   static final PyCode getLength$43;
   static final PyCode getType$44;
   static final PyCode getValue$45;
   static final PyCode getValueByQName$46;
   static final PyCode getNameByQName$47;
   static final PyCode getQNameByName$48;
   static final PyCode getNames$49;
   static final PyCode getQNames$50;
   static final PyCode __len__$51;
   static final PyCode __getitem__$52;
   static final PyCode keys$53;
   static final PyCode has_key$54;
   static final PyCode get$55;
   static final PyCode copy$56;
   static final PyCode items$57;
   static final PyCode values$58;
   static final PyCode AttributesNSImpl$59;
   static final PyCode __init__$60;
   static final PyCode getValueByQName$61;
   static final PyCode getNameByQName$62;
   static final PyCode getQNameByName$63;
   static final PyCode getQNames$64;
   static final PyCode copy$65;
   static final PyCode _test$66;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("An XML Reader is the SAX 2 name for an XML parser. XML Parsers\nshould be based on this code. "));
      var1.setline(2);
      PyString.fromInterned("An XML Reader is the SAX 2 name for an XML parser. XML Parsers\nshould be based on this code. ");
      var1.setline(4);
      PyObject var3 = imp.importOne("handler", var1, -1);
      var1.setlocal("handler", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"SAXNotSupportedException", "SAXNotRecognizedException"};
      PyObject[] var6 = imp.importFrom("_exceptions", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("SAXNotSupportedException", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("SAXNotRecognizedException", var4);
      var4 = null;
      var1.setline(11);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("XMLReader", var6, XMLReader$1);
      var1.setlocal("XMLReader", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(91);
      var6 = new PyObject[]{var1.getname("XMLReader")};
      var4 = Py.makeClass("IncrementalParser", var6, IncrementalParser$17);
      var1.setlocal("IncrementalParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(163);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Locator", var6, Locator$24);
      var1.setlocal("Locator", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(187);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("InputSource", var6, InputSource$29);
      var1.setlocal("InputSource", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(276);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("AttributesImpl", var6, AttributesImpl$41);
      var1.setlocal("AttributesImpl", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(338);
      var6 = new PyObject[]{var1.getname("AttributesImpl")};
      var4 = Py.makeClass("AttributesNSImpl", var6, AttributesNSImpl$59);
      var1.setlocal("AttributesNSImpl", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(372);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _test$66, (PyObject)null);
      var1.setlocal("_test", var7);
      var3 = null;
      var1.setline(377);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(378);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject XMLReader$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Interface for reading an XML document using callbacks.\n\n    XMLReader is the interface that an XML parser's SAX2 driver must\n    implement. This interface allows an application to set and query\n    features and properties in the parser, to register event handlers\n    for document processing, and to initiate a document parse.\n\n    All SAX interfaces are assumed to be synchronous: the parse\n    methods must not return until parsing is complete, and readers\n    must wait for an event-handler callback to return before reporting\n    the next event."));
      var1.setline(22);
      PyString.fromInterned("Interface for reading an XML document using callbacks.\n\n    XMLReader is the interface that an XML parser's SAX2 driver must\n    implement. This interface allows an application to set and query\n    features and properties in the parser, to register event handlers\n    for document processing, and to initiate a document parse.\n\n    All SAX interfaces are assumed to be synchronous: the parse\n    methods must not return until parsing is complete, and readers\n    must wait for an event-handler callback to return before reporting\n    the next event.");
      var1.setline(24);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$3, PyString.fromInterned("Parse an XML document from a system identifier or an InputSource."));
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getContentHandler$4, PyString.fromInterned("Returns the current ContentHandler."));
      var1.setlocal("getContentHandler", var4);
      var3 = null;
      var1.setline(38);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setContentHandler$5, PyString.fromInterned("Registers a new object to receive document content events."));
      var1.setlocal("setContentHandler", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getDTDHandler$6, PyString.fromInterned("Returns the current DTD handler."));
      var1.setlocal("getDTDHandler", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDTDHandler$7, PyString.fromInterned("Register an object to receive basic DTD-related events."));
      var1.setlocal("setDTDHandler", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getEntityResolver$8, PyString.fromInterned("Returns the current EntityResolver."));
      var1.setlocal("getEntityResolver", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setEntityResolver$9, PyString.fromInterned("Register an object to resolve external entities."));
      var1.setlocal("setEntityResolver", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getErrorHandler$10, PyString.fromInterned("Returns the current ErrorHandler."));
      var1.setlocal("getErrorHandler", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setErrorHandler$11, PyString.fromInterned("Register an object to receive error-message events."));
      var1.setlocal("setErrorHandler", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setLocale$12, PyString.fromInterned("Allow an application to set the locale for errors and warnings.\n\n        SAX parsers are not required to provide localization for errors\n        and warnings; if they cannot support the requested locale,\n        however, they must throw a SAX exception. Applications may\n        request a locale change in the middle of a parse."));
      var1.setlocal("setLocale", var4);
      var3 = null;
      var1.setline(75);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getFeature$13, PyString.fromInterned("Looks up and returns the state of a SAX2 feature."));
      var1.setlocal("getFeature", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setFeature$14, PyString.fromInterned("Sets the state of a SAX2 feature."));
      var1.setlocal("setFeature", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getProperty$15, PyString.fromInterned("Looks up and returns the value of a SAX2 property."));
      var1.setlocal("getProperty", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setProperty$16, PyString.fromInterned("Sets the value of a SAX2 property."));
      var1.setlocal("setProperty", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("handler").__getattr__("ContentHandler").__call__(var2);
      var1.getlocal(0).__setattr__("_cont_handler", var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("handler").__getattr__("DTDHandler").__call__(var2);
      var1.getlocal(0).__setattr__("_dtd_handler", var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("handler").__getattr__("EntityResolver").__call__(var2);
      var1.getlocal(0).__setattr__("_ent_handler", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getglobal("handler").__getattr__("ErrorHandler").__call__(var2);
      var1.getlocal(0).__setattr__("_err_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$3(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("Parse an XML document from a system identifier or an InputSource.");
      var1.setline(32);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getContentHandler$4(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyString.fromInterned("Returns the current ContentHandler.");
      var1.setline(36);
      PyObject var3 = var1.getlocal(0).__getattr__("_cont_handler");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setContentHandler$5(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Registers a new object to receive document content events.");
      var1.setline(40);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_cont_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getDTDHandler$6(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Returns the current DTD handler.");
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("_dtd_handler");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setDTDHandler$7(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyString.fromInterned("Register an object to receive basic DTD-related events.");
      var1.setline(48);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_dtd_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getEntityResolver$8(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Returns the current EntityResolver.");
      var1.setline(52);
      PyObject var3 = var1.getlocal(0).__getattr__("_ent_handler");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setEntityResolver$9(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("Register an object to resolve external entities.");
      var1.setline(56);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_ent_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getErrorHandler$10(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("Returns the current ErrorHandler.");
      var1.setline(60);
      PyObject var3 = var1.getlocal(0).__getattr__("_err_handler");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setErrorHandler$11(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyString.fromInterned("Register an object to receive error-message events.");
      var1.setline(64);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_err_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setLocale$12(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Allow an application to set the locale for errors and warnings.\n\n        SAX parsers are not required to provide localization for errors\n        and warnings; if they cannot support the requested locale,\n        however, they must throw a SAX exception. Applications may\n        request a locale change in the middle of a parse.");
      var1.setline(73);
      throw Py.makeException(var1.getglobal("SAXNotSupportedException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Locale support not implemented")));
   }

   public PyObject getFeature$13(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("Looks up and returns the state of a SAX2 feature.");
      var1.setline(77);
      throw Py.makeException(var1.getglobal("SAXNotRecognizedException").__call__(var2, PyString.fromInterned("Feature '%s' not recognized")._mod(var1.getlocal(1))));
   }

   public PyObject setFeature$14(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Sets the state of a SAX2 feature.");
      var1.setline(81);
      throw Py.makeException(var1.getglobal("SAXNotRecognizedException").__call__(var2, PyString.fromInterned("Feature '%s' not recognized")._mod(var1.getlocal(1))));
   }

   public PyObject getProperty$15(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("Looks up and returns the value of a SAX2 property.");
      var1.setline(85);
      throw Py.makeException(var1.getglobal("SAXNotRecognizedException").__call__(var2, PyString.fromInterned("Property '%s' not recognized")._mod(var1.getlocal(1))));
   }

   public PyObject setProperty$16(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned("Sets the value of a SAX2 property.");
      var1.setline(89);
      throw Py.makeException(var1.getglobal("SAXNotRecognizedException").__call__(var2, PyString.fromInterned("Property '%s' not recognized")._mod(var1.getlocal(1))));
   }

   public PyObject IncrementalParser$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This interface adds three extra methods to the XMLReader\n    interface that allow XML parsers to support incremental\n    parsing. Support for this interface is optional, since not all\n    underlying XML parsers support this functionality.\n\n    When the parser is instantiated it is ready to begin accepting\n    data from the feed method immediately. After parsing has been\n    finished with a call to close the reset method must be called to\n    make the parser ready to accept new data, either from feed or\n    using the parse method.\n\n    Note that these methods must _not_ be called during parsing, that\n    is, after parse has been called and before it returns.\n\n    By default, the class also implements the parse method of the XMLReader\n    interface using the feed, close and reset methods of the\n    IncrementalParser interface as a convenience to SAX 2.0 driver\n    writers."));
      var1.setline(109);
      PyString.fromInterned("This interface adds three extra methods to the XMLReader\n    interface that allow XML parsers to support incremental\n    parsing. Support for this interface is optional, since not all\n    underlying XML parsers support this functionality.\n\n    When the parser is instantiated it is ready to begin accepting\n    data from the feed method immediately. After parsing has been\n    finished with a call to close the reset method must be called to\n    make the parser ready to accept new data, either from feed or\n    using the parse method.\n\n    Note that these methods must _not_ be called during parsing, that\n    is, after parse has been called and before it returns.\n\n    By default, the class also implements the parse method of the XMLReader\n    interface using the feed, close and reset methods of the\n    IncrementalParser interface as a convenience to SAX 2.0 driver\n    writers.");
      var1.setline(111);
      PyObject[] var3 = new PyObject[]{Py.newInteger(2)._pow(Py.newInteger(16))};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$19, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, feed$20, PyString.fromInterned("This method gives the raw XML data in the data parameter to\n        the parser and makes it parse the data, emitting the\n        corresponding events. It is allowed for XML constructs to be\n        split across several calls to feed.\n\n        feed may raise SAXException."));
      var1.setlocal("feed", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prepareParser$21, PyString.fromInterned("This method is called by the parse implementation to allow\n        the SAX 2.0 driver to prepare itself for parsing."));
      var1.setlocal("prepareParser", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$22, PyString.fromInterned("This method is called when the entire XML document has been\n        passed to the parser through the feed method, to notify the\n        parser that there are no more data. This allows the parser to\n        do the final checks on the document and empty the internal\n        data buffer.\n\n        The parser will not be ready to parse another document until\n        the reset method has been called.\n\n        close may raise SAXException."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(154);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$23, PyString.fromInterned("This method is called after close has been called to reset\n        the parser so that it is ready to parse new documents. The\n        results of calling parse or feed after close without calling\n        reset are undefined."));
      var1.setlocal("reset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_bufsize", var3);
      var3 = null;
      var1.setline(113);
      var1.getglobal("XMLReader").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$19(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = imp.importOne("saxutils", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(117);
      var3 = var1.getlocal(2).__getattr__("prepare_input_source").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(119);
      var1.getlocal(0).__getattr__("prepareParser").__call__(var2, var1.getlocal(1));
      var1.setline(120);
      var3 = var1.getlocal(1).__getattr__("getByteStream").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("_bufsize"));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(122);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._ne(PyString.fromInterned(""));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(125);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(123);
         var1.getlocal(0).__getattr__("feed").__call__(var2, var1.getlocal(4));
         var1.setline(124);
         var3 = var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("_bufsize"));
         var1.setlocal(4, var3);
         var3 = null;
      }
   }

   public PyObject feed$20(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("This method gives the raw XML data in the data parameter to\n        the parser and makes it parse the data, emitting the\n        corresponding events. It is allowed for XML constructs to be\n        split across several calls to feed.\n\n        feed may raise SAXException.");
      var1.setline(134);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject prepareParser$21(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("This method is called by the parse implementation to allow\n        the SAX 2.0 driver to prepare itself for parsing.");
      var1.setline(139);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prepareParser must be overridden!")));
   }

   public PyObject close$22(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyString.fromInterned("This method is called when the entire XML document has been\n        passed to the parser through the feed method, to notify the\n        parser that there are no more data. This allows the parser to\n        do the final checks on the document and empty the internal\n        data buffer.\n\n        The parser will not be ready to parse another document until\n        the reset method has been called.\n\n        close may raise SAXException.");
      var1.setline(152);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject reset$23(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyString.fromInterned("This method is called after close has been called to reset\n        the parser so that it is ready to parse new documents. The\n        results of calling parse or feed after close without calling\n        reset are undefined.");
      var1.setline(159);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject Locator$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Interface for associating a SAX event with a document\n    location. A locator object will return valid results only during\n    calls to DocumentHandler methods; at any other time, the\n    results are unpredictable."));
      var1.setline(167);
      PyString.fromInterned("Interface for associating a SAX event with a document\n    location. A locator object will return valid results only during\n    calls to DocumentHandler methods; at any other time, the\n    results are unpredictable.");
      var1.setline(169);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, getColumnNumber$25, PyString.fromInterned("Return the column number where the current event ends."));
      var1.setlocal("getColumnNumber", var4);
      var3 = null;
      var1.setline(173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLineNumber$26, PyString.fromInterned("Return the line number where the current event ends."));
      var1.setlocal("getLineNumber", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getPublicId$27, PyString.fromInterned("Return the public identifier for the current event."));
      var1.setlocal("getPublicId", var4);
      var3 = null;
      var1.setline(181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getSystemId$28, PyString.fromInterned("Return the system identifier for the current event."));
      var1.setlocal("getSystemId", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getColumnNumber$25(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Return the column number where the current event ends.");
      var1.setline(171);
      PyInteger var3 = Py.newInteger(-1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getLineNumber$26(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyString.fromInterned("Return the line number where the current event ends.");
      var1.setline(175);
      PyInteger var3 = Py.newInteger(-1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getPublicId$27(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyString.fromInterned("Return the public identifier for the current event.");
      var1.setline(179);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getSystemId$28(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyString.fromInterned("Return the system identifier for the current event.");
      var1.setline(183);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject InputSource$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Encapsulation of the information needed by the XMLReader to\n    read entities.\n\n    This class may include information about the public identifier,\n    system identifier, byte stream (possibly with character encoding\n    information) and/or the character stream of an entity.\n\n    Applications will create objects of this class for use in the\n    XMLReader.parse method and for returning from\n    EntityResolver.resolveEntity.\n\n    An InputSource belongs to the application, the XMLReader is not\n    allowed to modify InputSource objects passed to it from the\n    application, although it may make copies and modify those."));
      var1.setline(201);
      PyString.fromInterned("Encapsulation of the information needed by the XMLReader to\n    read entities.\n\n    This class may include information about the public identifier,\n    system identifier, byte stream (possibly with character encoding\n    information) and/or the character stream of an entity.\n\n    Applications will create objects of this class for use in the\n    XMLReader.parse method and for returning from\n    EntityResolver.resolveEntity.\n\n    An InputSource belongs to the application, the XMLReader is not\n    allowed to modify InputSource objects passed to it from the\n    application, although it may make copies and modify those.");
      var1.setline(203);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(210);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setPublicId$31, PyString.fromInterned("Sets the public identifier of this InputSource."));
      var1.setlocal("setPublicId", var4);
      var3 = null;
      var1.setline(214);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getPublicId$32, PyString.fromInterned("Returns the public identifier of this InputSource."));
      var1.setlocal("getPublicId", var4);
      var3 = null;
      var1.setline(218);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setSystemId$33, PyString.fromInterned("Sets the system identifier of this InputSource."));
      var1.setlocal("setSystemId", var4);
      var3 = null;
      var1.setline(222);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getSystemId$34, PyString.fromInterned("Returns the system identifier of this InputSource."));
      var1.setlocal("getSystemId", var4);
      var3 = null;
      var1.setline(226);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setEncoding$35, PyString.fromInterned("Sets the character encoding of this InputSource.\n\n        The encoding must be a string acceptable for an XML encoding\n        declaration (see section 4.3.3 of the XML recommendation).\n\n        The encoding attribute of the InputSource is ignored if the\n        InputSource also contains a character stream."));
      var1.setlocal("setEncoding", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getEncoding$36, PyString.fromInterned("Get the character encoding of this InputSource."));
      var1.setlocal("getEncoding", var4);
      var3 = null;
      var1.setline(240);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setByteStream$37, PyString.fromInterned("Set the byte stream (a Python file-like object which does\n        not perform byte-to-character conversion) for this input\n        source.\n\n        The SAX parser will ignore this if there is also a character\n        stream specified, but it will use a byte stream in preference\n        to opening a URI connection itself.\n\n        If the application knows the character encoding of the byte\n        stream, it should set it with the setEncoding method."));
      var1.setlocal("setByteStream", var4);
      var3 = null;
      var1.setline(253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getByteStream$38, PyString.fromInterned("Get the byte stream for this input source.\n\n        The getEncoding method will return the character encoding for\n        this byte stream, or None if unknown."));
      var1.setlocal("getByteStream", var4);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setCharacterStream$39, PyString.fromInterned("Set the character stream for this input source. (The stream\n        must be a Python 2.0 Unicode-wrapped file-like that performs\n        conversion to Unicode strings.)\n\n        If there is a character stream specified, the SAX parser will\n        ignore any byte stream and will not attempt to open a URI\n        connection to the system identifier."));
      var1.setlocal("setCharacterStream", var4);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getCharacterStream$40, PyString.fromInterned("Get the character stream for this input source."));
      var1.setlocal("getCharacterStream", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_InputSource__system_id", var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_InputSource__public_id", var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_InputSource__encoding", var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_InputSource__bytefile", var3);
      var3 = null;
      var1.setline(208);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_InputSource__charfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setPublicId$31(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyString.fromInterned("Sets the public identifier of this InputSource.");
      var1.setline(212);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_InputSource__public_id", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getPublicId$32(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyString.fromInterned("Returns the public identifier of this InputSource.");
      var1.setline(216);
      PyObject var3 = var1.getlocal(0).__getattr__("_InputSource__public_id");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setSystemId$33(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyString.fromInterned("Sets the system identifier of this InputSource.");
      var1.setline(220);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_InputSource__system_id", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getSystemId$34(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("Returns the system identifier of this InputSource.");
      var1.setline(224);
      PyObject var3 = var1.getlocal(0).__getattr__("_InputSource__system_id");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setEncoding$35(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyString.fromInterned("Sets the character encoding of this InputSource.\n\n        The encoding must be a string acceptable for an XML encoding\n        declaration (see section 4.3.3 of the XML recommendation).\n\n        The encoding attribute of the InputSource is ignored if the\n        InputSource also contains a character stream.");
      var1.setline(234);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_InputSource__encoding", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getEncoding$36(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyString.fromInterned("Get the character encoding of this InputSource.");
      var1.setline(238);
      PyObject var3 = var1.getlocal(0).__getattr__("_InputSource__encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setByteStream$37(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString.fromInterned("Set the byte stream (a Python file-like object which does\n        not perform byte-to-character conversion) for this input\n        source.\n\n        The SAX parser will ignore this if there is also a character\n        stream specified, but it will use a byte stream in preference\n        to opening a URI connection itself.\n\n        If the application knows the character encoding of the byte\n        stream, it should set it with the setEncoding method.");
      var1.setline(251);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_InputSource__bytefile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getByteStream$38(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyString.fromInterned("Get the byte stream for this input source.\n\n        The getEncoding method will return the character encoding for\n        this byte stream, or None if unknown.");
      var1.setline(258);
      PyObject var3 = var1.getlocal(0).__getattr__("_InputSource__bytefile");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setCharacterStream$39(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyString.fromInterned("Set the character stream for this input source. (The stream\n        must be a Python 2.0 Unicode-wrapped file-like that performs\n        conversion to Unicode strings.)\n\n        If there is a character stream specified, the SAX parser will\n        ignore any byte stream and will not attempt to open a URI\n        connection to the system identifier.");
      var1.setline(268);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_InputSource__charfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getCharacterStream$40(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyString.fromInterned("Get the character stream for this input source.");
      var1.setline(272);
      PyObject var3 = var1.getlocal(0).__getattr__("_InputSource__charfile");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AttributesImpl$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(278);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$42, PyString.fromInterned("Non-NS-aware implementation.\n\n        attrs should be of the form {name : value}."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(284);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLength$43, (PyObject)null);
      var1.setlocal("getLength", var4);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getType$44, (PyObject)null);
      var1.setlocal("getType", var4);
      var3 = null;
      var1.setline(290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValue$45, (PyObject)null);
      var1.setlocal("getValue", var4);
      var3 = null;
      var1.setline(293);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValueByQName$46, (PyObject)null);
      var1.setlocal("getValueByQName", var4);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNameByQName$47, (PyObject)null);
      var1.setlocal("getNameByQName", var4);
      var3 = null;
      var1.setline(301);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNameByName$48, (PyObject)null);
      var1.setlocal("getQNameByName", var4);
      var3 = null;
      var1.setline(306);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNames$49, (PyObject)null);
      var1.setlocal("getNames", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNames$50, (PyObject)null);
      var1.setlocal("getQNames", var4);
      var3 = null;
      var1.setline(312);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$51, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(315);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$52, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(318);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$53, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(321);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$54, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(324);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$55, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(327);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$56, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$57, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(333);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$58, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$42(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("Non-NS-aware implementation.\n\n        attrs should be of the form {name : value}.");
      var1.setline(282);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_attrs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getLength$43(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_attrs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getType$44(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyString var3 = PyString.fromInterned("CDATA");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getValue$45(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getValueByQName$46(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getNameByQName$47(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      if (var1.getlocal(0).__getattr__("_attrs").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(298);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(299);
         PyObject var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getQNameByName$48(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      if (var1.getlocal(0).__getattr__("_attrs").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(303);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(304);
         PyObject var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getNames$49(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getQNames$50(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$51(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_attrs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$52(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject keys$53(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$54(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("has_key").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$55(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy$56(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("_attrs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject items$57(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$58(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AttributesNSImpl$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(340);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$60, PyString.fromInterned("NS-aware implementation.\n\n        attrs should be of the form {(ns_uri, lname): value, ...}.\n        qnames of the form {(ns_uri, lname): qname, ...}."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(348);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValueByQName$61, (PyObject)null);
      var1.setlocal("getValueByQName", var4);
      var3 = null;
      var1.setline(355);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNameByQName$62, (PyObject)null);
      var1.setlocal("getNameByQName", var4);
      var3 = null;
      var1.setline(362);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNameByName$63, (PyObject)null);
      var1.setlocal("getQNameByName", var4);
      var3 = null;
      var1.setline(365);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNames$64, (PyObject)null);
      var1.setlocal("getQNames", var4);
      var3 = null;
      var1.setline(368);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$65, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$60(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyString.fromInterned("NS-aware implementation.\n\n        attrs should be of the form {(ns_uri, lname): value, ...}.\n        qnames of the form {(ns_uri, lname): qname, ...}.");
      var1.setline(345);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_attrs", var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_qnames", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getValueByQName$61(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      PyObject var3 = var1.getlocal(0).__getattr__("_qnames").__getattr__("items").__call__(var2).__iter__();

      PyObject var10000;
      PyObject var7;
      do {
         var1.setline(349);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(353);
            throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(350);
         var7 = var1.getlocal(3);
         var10000 = var7._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(351);
      var7 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(2));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject getNameByQName$62(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = var1.getlocal(0).__getattr__("_qnames").__getattr__("items").__call__(var2).__iter__();

      PyObject var10000;
      PyObject var7;
      do {
         var1.setline(356);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(360);
            throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(357);
         var7 = var1.getlocal(3);
         var10000 = var7._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(358);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject getQNameByName$63(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyObject var3 = var1.getlocal(0).__getattr__("_qnames").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getQNames$64(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyObject var3 = var1.getlocal(0).__getattr__("_qnames").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy$65(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("_attrs"), var1.getlocal(0).__getattr__("_qnames"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _test$66(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      var1.getglobal("XMLReader").__call__(var2);
      var1.setline(374);
      var1.getglobal("IncrementalParser").__call__(var2);
      var1.setline(375);
      var1.getglobal("Locator").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public xmlreader$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      XMLReader$1 = Py.newCode(0, var2, var1, "XMLReader", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source"};
      parse$3 = Py.newCode(2, var2, var1, "parse", 30, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getContentHandler$4 = Py.newCode(1, var2, var1, "getContentHandler", 34, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      setContentHandler$5 = Py.newCode(2, var2, var1, "setContentHandler", 38, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getDTDHandler$6 = Py.newCode(1, var2, var1, "getDTDHandler", 42, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      setDTDHandler$7 = Py.newCode(2, var2, var1, "setDTDHandler", 46, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getEntityResolver$8 = Py.newCode(1, var2, var1, "getEntityResolver", 50, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resolver"};
      setEntityResolver$9 = Py.newCode(2, var2, var1, "setEntityResolver", 54, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getErrorHandler$10 = Py.newCode(1, var2, var1, "getErrorHandler", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      setErrorHandler$11 = Py.newCode(2, var2, var1, "setErrorHandler", 62, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locale"};
      setLocale$12 = Py.newCode(2, var2, var1, "setLocale", 66, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getFeature$13 = Py.newCode(2, var2, var1, "getFeature", 75, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "state"};
      setFeature$14 = Py.newCode(3, var2, var1, "setFeature", 79, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getProperty$15 = Py.newCode(2, var2, var1, "getProperty", 83, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      setProperty$16 = Py.newCode(3, var2, var1, "setProperty", 87, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalParser$17 = Py.newCode(0, var2, var1, "IncrementalParser", 91, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "bufsize"};
      __init__$18 = Py.newCode(2, var2, var1, "__init__", 111, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "saxutils", "file", "buffer"};
      parse$19 = Py.newCode(2, var2, var1, "parse", 115, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$20 = Py.newCode(2, var2, var1, "feed", 127, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source"};
      prepareParser$21 = Py.newCode(2, var2, var1, "prepareParser", 136, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$22 = Py.newCode(1, var2, var1, "close", 141, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$23 = Py.newCode(1, var2, var1, "reset", 154, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Locator$24 = Py.newCode(0, var2, var1, "Locator", 163, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      getColumnNumber$25 = Py.newCode(1, var2, var1, "getColumnNumber", 169, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLineNumber$26 = Py.newCode(1, var2, var1, "getLineNumber", 173, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getPublicId$27 = Py.newCode(1, var2, var1, "getPublicId", 177, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getSystemId$28 = Py.newCode(1, var2, var1, "getSystemId", 181, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InputSource$29 = Py.newCode(0, var2, var1, "InputSource", 187, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "system_id"};
      __init__$30 = Py.newCode(2, var2, var1, "__init__", 203, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "public_id"};
      setPublicId$31 = Py.newCode(2, var2, var1, "setPublicId", 210, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getPublicId$32 = Py.newCode(1, var2, var1, "getPublicId", 214, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "system_id"};
      setSystemId$33 = Py.newCode(2, var2, var1, "setSystemId", 218, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getSystemId$34 = Py.newCode(1, var2, var1, "getSystemId", 222, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding"};
      setEncoding$35 = Py.newCode(2, var2, var1, "setEncoding", 226, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getEncoding$36 = Py.newCode(1, var2, var1, "getEncoding", 236, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bytefile"};
      setByteStream$37 = Py.newCode(2, var2, var1, "setByteStream", 240, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getByteStream$38 = Py.newCode(1, var2, var1, "getByteStream", 253, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charfile"};
      setCharacterStream$39 = Py.newCode(2, var2, var1, "setCharacterStream", 260, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getCharacterStream$40 = Py.newCode(1, var2, var1, "getCharacterStream", 270, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AttributesImpl$41 = Py.newCode(0, var2, var1, "AttributesImpl", 276, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "attrs"};
      __init__$42 = Py.newCode(2, var2, var1, "__init__", 278, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLength$43 = Py.newCode(1, var2, var1, "getLength", 284, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getType$44 = Py.newCode(2, var2, var1, "getType", 287, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getValue$45 = Py.newCode(2, var2, var1, "getValue", 290, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getValueByQName$46 = Py.newCode(2, var2, var1, "getValueByQName", 293, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getNameByQName$47 = Py.newCode(2, var2, var1, "getNameByQName", 296, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getQNameByName$48 = Py.newCode(2, var2, var1, "getQNameByName", 301, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getNames$49 = Py.newCode(1, var2, var1, "getNames", 306, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getQNames$50 = Py.newCode(1, var2, var1, "getQNames", 309, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$51 = Py.newCode(1, var2, var1, "__len__", 312, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$52 = Py.newCode(2, var2, var1, "__getitem__", 315, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$53 = Py.newCode(1, var2, var1, "keys", 318, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      has_key$54 = Py.newCode(2, var2, var1, "has_key", 321, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "alternative"};
      get$55 = Py.newCode(3, var2, var1, "get", 324, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$56 = Py.newCode(1, var2, var1, "copy", 327, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$57 = Py.newCode(1, var2, var1, "items", 330, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$58 = Py.newCode(1, var2, var1, "values", 333, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AttributesNSImpl$59 = Py.newCode(0, var2, var1, "AttributesNSImpl", 338, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "attrs", "qnames"};
      __init__$60 = Py.newCode(3, var2, var1, "__init__", 340, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "nsname", "qname"};
      getValueByQName$61 = Py.newCode(2, var2, var1, "getValueByQName", 348, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "nsname", "qname"};
      getNameByQName$62 = Py.newCode(2, var2, var1, "getNameByQName", 355, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getQNameByName$63 = Py.newCode(2, var2, var1, "getQNameByName", 362, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getQNames$64 = Py.newCode(1, var2, var1, "getQNames", 365, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$65 = Py.newCode(1, var2, var1, "copy", 368, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _test$66 = Py.newCode(0, var2, var1, "_test", 372, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new xmlreader$py("xml/sax/xmlreader$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(xmlreader$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.XMLReader$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.parse$3(var2, var3);
         case 4:
            return this.getContentHandler$4(var2, var3);
         case 5:
            return this.setContentHandler$5(var2, var3);
         case 6:
            return this.getDTDHandler$6(var2, var3);
         case 7:
            return this.setDTDHandler$7(var2, var3);
         case 8:
            return this.getEntityResolver$8(var2, var3);
         case 9:
            return this.setEntityResolver$9(var2, var3);
         case 10:
            return this.getErrorHandler$10(var2, var3);
         case 11:
            return this.setErrorHandler$11(var2, var3);
         case 12:
            return this.setLocale$12(var2, var3);
         case 13:
            return this.getFeature$13(var2, var3);
         case 14:
            return this.setFeature$14(var2, var3);
         case 15:
            return this.getProperty$15(var2, var3);
         case 16:
            return this.setProperty$16(var2, var3);
         case 17:
            return this.IncrementalParser$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.parse$19(var2, var3);
         case 20:
            return this.feed$20(var2, var3);
         case 21:
            return this.prepareParser$21(var2, var3);
         case 22:
            return this.close$22(var2, var3);
         case 23:
            return this.reset$23(var2, var3);
         case 24:
            return this.Locator$24(var2, var3);
         case 25:
            return this.getColumnNumber$25(var2, var3);
         case 26:
            return this.getLineNumber$26(var2, var3);
         case 27:
            return this.getPublicId$27(var2, var3);
         case 28:
            return this.getSystemId$28(var2, var3);
         case 29:
            return this.InputSource$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this.setPublicId$31(var2, var3);
         case 32:
            return this.getPublicId$32(var2, var3);
         case 33:
            return this.setSystemId$33(var2, var3);
         case 34:
            return this.getSystemId$34(var2, var3);
         case 35:
            return this.setEncoding$35(var2, var3);
         case 36:
            return this.getEncoding$36(var2, var3);
         case 37:
            return this.setByteStream$37(var2, var3);
         case 38:
            return this.getByteStream$38(var2, var3);
         case 39:
            return this.setCharacterStream$39(var2, var3);
         case 40:
            return this.getCharacterStream$40(var2, var3);
         case 41:
            return this.AttributesImpl$41(var2, var3);
         case 42:
            return this.__init__$42(var2, var3);
         case 43:
            return this.getLength$43(var2, var3);
         case 44:
            return this.getType$44(var2, var3);
         case 45:
            return this.getValue$45(var2, var3);
         case 46:
            return this.getValueByQName$46(var2, var3);
         case 47:
            return this.getNameByQName$47(var2, var3);
         case 48:
            return this.getQNameByName$48(var2, var3);
         case 49:
            return this.getNames$49(var2, var3);
         case 50:
            return this.getQNames$50(var2, var3);
         case 51:
            return this.__len__$51(var2, var3);
         case 52:
            return this.__getitem__$52(var2, var3);
         case 53:
            return this.keys$53(var2, var3);
         case 54:
            return this.has_key$54(var2, var3);
         case 55:
            return this.get$55(var2, var3);
         case 56:
            return this.copy$56(var2, var3);
         case 57:
            return this.items$57(var2, var3);
         case 58:
            return this.values$58(var2, var3);
         case 59:
            return this.AttributesNSImpl$59(var2, var3);
         case 60:
            return this.__init__$60(var2, var3);
         case 61:
            return this.getValueByQName$61(var2, var3);
         case 62:
            return this.getNameByQName$62(var2, var3);
         case 63:
            return this.getQNameByName$63(var2, var3);
         case 64:
            return this.getQNames$64(var2, var3);
         case 65:
            return this.copy$65(var2, var3);
         case 66:
            return this._test$66(var2, var3);
         default:
            return null;
      }
   }
}
