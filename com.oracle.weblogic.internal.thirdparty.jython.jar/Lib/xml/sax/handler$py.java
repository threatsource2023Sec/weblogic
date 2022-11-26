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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/sax/handler.py")
public class handler$py extends PyFunctionTable implements PyRunnable {
   static handler$py self;
   static final PyCode f$0;
   static final PyCode ErrorHandler$1;
   static final PyCode error$2;
   static final PyCode fatalError$3;
   static final PyCode warning$4;
   static final PyCode ContentHandler$5;
   static final PyCode __init__$6;
   static final PyCode setDocumentLocator$7;
   static final PyCode startDocument$8;
   static final PyCode endDocument$9;
   static final PyCode startPrefixMapping$10;
   static final PyCode endPrefixMapping$11;
   static final PyCode startElement$12;
   static final PyCode endElement$13;
   static final PyCode startElementNS$14;
   static final PyCode endElementNS$15;
   static final PyCode characters$16;
   static final PyCode ignorableWhitespace$17;
   static final PyCode processingInstruction$18;
   static final PyCode skippedEntity$19;
   static final PyCode DTDHandler$20;
   static final PyCode notationDecl$21;
   static final PyCode unparsedEntityDecl$22;
   static final PyCode EntityResolver$23;
   static final PyCode resolveEntity$24;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nThis module contains the core classes of version 2.0 of SAX for Python.\nThis file provides only default classes with absolutely minimum\nfunctionality, from which drivers and applications can be subclassed.\n\nMany of these classes are empty and are included only as documentation\nof the interfaces.\n\n$Id: handler.py,v 1.5 2002/02/14 08:09:36 loewis Exp $\n"));
      var1.setline(10);
      PyString.fromInterned("\nThis module contains the core classes of version 2.0 of SAX for Python.\nThis file provides only default classes with absolutely minimum\nfunctionality, from which drivers and applications can be subclassed.\n\nMany of these classes are empty and are included only as documentation\nof the interfaces.\n\n$Id: handler.py,v 1.5 2002/02/14 08:09:36 loewis Exp $\n");
      var1.setline(12);
      PyString var3 = PyString.fromInterned("2.0beta");
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(22);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("ErrorHandler", var5, ErrorHandler$1);
      var1.setlocal("ErrorHandler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(47);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("ContentHandler", var5, ContentHandler$5);
      var1.setlocal("ContentHandler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(211);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("DTDHandler", var5, DTDHandler$20);
      var1.setlocal("DTDHandler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(226);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("EntityResolver", var5, EntityResolver$23);
      var1.setlocal("EntityResolver", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(246);
      var3 = PyString.fromInterned("http://xml.org/sax/features/namespaces");
      var1.setlocal("feature_namespaces", var3);
      var3 = null;
      var1.setline(252);
      var3 = PyString.fromInterned("http://xml.org/sax/features/namespace-prefixes");
      var1.setlocal("feature_namespace_prefixes", var3);
      var3 = null;
      var1.setline(259);
      var3 = PyString.fromInterned("http://xml.org/sax/features/string-interning");
      var1.setlocal("feature_string_interning", var3);
      var3 = null;
      var1.setline(265);
      var3 = PyString.fromInterned("http://xml.org/sax/features/validation");
      var1.setlocal("feature_validation", var3);
      var3 = null;
      var1.setline(271);
      var3 = PyString.fromInterned("http://xml.org/sax/features/external-general-entities");
      var1.setlocal("feature_external_ges", var3);
      var3 = null;
      var1.setline(276);
      var3 = PyString.fromInterned("http://xml.org/sax/features/external-parameter-entities");
      var1.setlocal("feature_external_pes", var3);
      var3 = null;
      var1.setline(283);
      PyList var6 = new PyList(new PyObject[]{var1.getname("feature_namespaces"), var1.getname("feature_namespace_prefixes"), var1.getname("feature_string_interning"), var1.getname("feature_validation"), var1.getname("feature_external_ges"), var1.getname("feature_external_pes")});
      var1.setlocal("all_features", var6);
      var3 = null;
      var1.setline(297);
      var3 = PyString.fromInterned("http://xml.org/sax/properties/lexical-handler");
      var1.setlocal("property_lexical_handler", var3);
      var3 = null;
      var1.setline(302);
      var3 = PyString.fromInterned("http://xml.org/sax/properties/declaration-handler");
      var1.setlocal("property_declaration_handler", var3);
      var3 = null;
      var1.setline(308);
      var3 = PyString.fromInterned("http://xml.org/sax/properties/dom-node");
      var1.setlocal("property_dom_node", var3);
      var3 = null;
      var1.setline(315);
      var3 = PyString.fromInterned("http://xml.org/sax/properties/xml-string");
      var1.setlocal("property_xml_string", var3);
      var3 = null;
      var1.setline(321);
      var3 = PyString.fromInterned("http://www.python.org/sax/properties/encoding");
      var1.setlocal("property_encoding", var3);
      var3 = null;
      var1.setline(332);
      var3 = PyString.fromInterned("http://www.python.org/sax/properties/interning-dict");
      var1.setlocal("property_interning_dict", var3);
      var3 = null;
      var1.setline(340);
      var6 = new PyList(new PyObject[]{var1.getname("property_lexical_handler"), var1.getname("property_dom_node"), var1.getname("property_declaration_handler"), var1.getname("property_xml_string"), var1.getname("property_encoding"), var1.getname("property_interning_dict")});
      var1.setlocal("all_properties", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ErrorHandler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Basic interface for SAX error handlers.\n\n    If you create an object that implements this interface, then\n    register the object with your XMLReader, the parser will call the\n    methods in your object to report all warnings and errors. There\n    are three levels of errors available: warnings, (possibly)\n    recoverable errors, and unrecoverable errors. All methods take a\n    SAXParseException as the only parameter."));
      var1.setline(30);
      PyString.fromInterned("Basic interface for SAX error handlers.\n\n    If you create an object that implements this interface, then\n    register the object with your XMLReader, the parser will call the\n    methods in your object to report all warnings and errors. There\n    are three levels of errors available: warnings, (possibly)\n    recoverable errors, and unrecoverable errors. All methods take a\n    SAXParseException as the only parameter.");
      var1.setline(32);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, error$2, PyString.fromInterned("Handle a recoverable error."));
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$3, PyString.fromInterned("Handle a non-recoverable error."));
      var1.setlocal("fatalError", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$4, PyString.fromInterned("Handle a warning."));
      var1.setlocal("warning", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject error$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("Handle a recoverable error.");
      var1.setline(34);
      throw Py.makeException(var1.getlocal(1));
   }

   public PyObject fatalError$3(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyString.fromInterned("Handle a non-recoverable error.");
      var1.setline(38);
      throw Py.makeException(var1.getlocal(1));
   }

   public PyObject warning$4(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyString.fromInterned("Handle a warning.");
      var1.setline(42);
      Py.println(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ContentHandler$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Interface for receiving logical document content events.\n\n    This is the main callback interface in SAX, and the one most\n    important to applications. The order of events in this interface\n    mirrors the order of the information in the document."));
      var1.setline(52);
      PyString.fromInterned("Interface for receiving logical document content events.\n\n    This is the main callback interface in SAX, and the one most\n    important to applications. The order of events in this interface\n    mirrors the order of the information in the document.");
      var1.setline(54);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDocumentLocator$7, PyString.fromInterned("Called by the parser to give the application a locator for\n        locating the origin of document events.\n\n        SAX parsers are strongly encouraged (though not absolutely\n        required) to supply a locator: if it does so, it must supply\n        the locator to the application by invoking this method before\n        invoking any of the other methods in the DocumentHandler\n        interface.\n\n        The locator allows the application to determine the end\n        position of any document-related event, even if the parser is\n        not reporting an error. Typically, the application will use\n        this information for reporting its own errors (such as\n        character content that does not match an application's\n        business rules). The information returned by the locator is\n        probably not sufficient for use with a search engine.\n\n        Note that the locator will return correct information only\n        during the invocation of the events in this interface. The\n        application should not attempt to use it at any other time."));
      var1.setlocal("setDocumentLocator", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDocument$8, PyString.fromInterned("Receive notification of the beginning of a document.\n\n        The SAX parser will invoke this method only once, before any\n        other methods in this interface or in DTDHandler (except for\n        setDocumentLocator)."));
      var1.setlocal("startDocument", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDocument$9, PyString.fromInterned("Receive notification of the end of a document.\n\n        The SAX parser will invoke this method only once, and it will\n        be the last method invoked during the parse. The parser shall\n        not invoke this method until it has either abandoned parsing\n        (because of an unrecoverable error) or reached the end of\n        input."));
      var1.setlocal("endDocument", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startPrefixMapping$10, PyString.fromInterned("Begin the scope of a prefix-URI Namespace mapping.\n\n        The information from this event is not necessary for normal\n        Namespace processing: the SAX XML reader will automatically\n        replace prefixes for element and attribute names when the\n        http://xml.org/sax/features/namespaces feature is true (the\n        default).\n\n        There are cases, however, when applications need to use\n        prefixes in character data or in attribute values, where they\n        cannot safely be expanded automatically; the\n        start/endPrefixMapping event supplies the information to the\n        application to expand prefixes in those contexts itself, if\n        necessary.\n\n        Note that start/endPrefixMapping events are not guaranteed to\n        be properly nested relative to each-other: all\n        startPrefixMapping events will occur before the corresponding\n        startElement event, and all endPrefixMapping events will occur\n        after the corresponding endElement event, but their order is\n        not guaranteed."));
      var1.setlocal("startPrefixMapping", var4);
      var3 = null;
      var1.setline(119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endPrefixMapping$11, PyString.fromInterned("End the scope of a prefix-URI mapping.\n\n        See startPrefixMapping for details. This event will always\n        occur after the corresponding endElement event, but the order\n        of endPrefixMapping events is not otherwise guaranteed."));
      var1.setlocal("endPrefixMapping", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$12, PyString.fromInterned("Signals the start of an element in non-namespace mode.\n\n        The name parameter contains the raw XML 1.0 name of the\n        element type as a string and the attrs parameter holds an\n        instance of the Attributes class containing the attributes of\n        the element."));
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$13, PyString.fromInterned("Signals the end of an element in non-namespace mode.\n\n        The name parameter contains the name of the element type, just\n        as with the startElement event."));
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(140);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElementNS$14, PyString.fromInterned("Signals the start of an element in namespace mode.\n\n        The name parameter contains the name of the element type as a\n        (uri, localname) tuple, the qname parameter the raw XML 1.0\n        name used in the source document, and the attrs parameter\n        holds an instance of the Attributes class containing the\n        attributes of the element.\n\n        The uri part of the name tuple is None for elements which have\n        no namespace."));
      var1.setlocal("startElementNS", var4);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElementNS$15, PyString.fromInterned("Signals the end of an element in namespace mode.\n\n        The name parameter contains the name of the element type, just\n        as with the startElementNS event."));
      var1.setlocal("endElementNS", var4);
      var3 = null;
      var1.setline(158);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$16, PyString.fromInterned("Receive notification of character data.\n\n        The Parser will call this method to report each chunk of\n        character data. SAX parsers may return all contiguous\n        character data in a single chunk, or they may split it into\n        several chunks; however, all of the characters in any single\n        event must come from the same external entity so that the\n        Locator provides useful information."));
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$17, PyString.fromInterned("Receive notification of ignorable whitespace in element content.\n\n        Validating Parsers must use this method to report each chunk\n        of ignorable whitespace (see the W3C XML 1.0 recommendation,\n        section 2.10): non-validating parsers may also use this method\n        if they are capable of parsing and using content models.\n\n        SAX parsers may return all contiguous whitespace in a single\n        chunk, or they may split it into several chunks; however, all\n        of the characters in any single event must come from the same\n        external entity, so that the Locator provides useful\n        information.\n\n        The application must not attempt to read from the array\n        outside of the specified range."));
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$18, PyString.fromInterned("Receive notification of a processing instruction.\n\n        The Parser will invoke this method once for each processing\n        instruction found: note that processing instructions may occur\n        before or after the main document element.\n\n        A SAX parser should never report an XML declaration (XML 1.0,\n        section 2.8) or a text declaration (XML 1.0, section 4.3.1)\n        using this method."));
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, skippedEntity$19, PyString.fromInterned("Receive notification of a skipped entity.\n\n        The Parser will invoke this method once for each entity\n        skipped. Non-validating processors may skip entities if they\n        have not seen the declarations (because, for example, the\n        entity was declared in an external DTD subset). All processors\n        may skip external entities, depending on the values of the\n        http://xml.org/sax/features/external-general-entities and the\n        http://xml.org/sax/features/external-parameter-entities\n        properties."));
      var1.setlocal("skippedEntity", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_locator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDocumentLocator$7(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Called by the parser to give the application a locator for\n        locating the origin of document events.\n\n        SAX parsers are strongly encouraged (though not absolutely\n        required) to supply a locator: if it does so, it must supply\n        the locator to the application by invoking this method before\n        invoking any of the other methods in the DocumentHandler\n        interface.\n\n        The locator allows the application to determine the end\n        position of any document-related event, even if the parser is\n        not reporting an error. Typically, the application will use\n        this information for reporting its own errors (such as\n        character content that does not match an application's\n        business rules). The information returned by the locator is\n        probably not sufficient for use with a search engine.\n\n        Note that the locator will return correct information only\n        during the invocation of the events in this interface. The\n        application should not attempt to use it at any other time.");
      var1.setline(78);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_locator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDocument$8(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Receive notification of the beginning of a document.\n\n        The SAX parser will invoke this method only once, before any\n        other methods in this interface or in DTDHandler (except for\n        setDocumentLocator).");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDocument$9(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Receive notification of the end of a document.\n\n        The SAX parser will invoke this method only once, and it will\n        be the last method invoked during the parse. The parser shall\n        not invoke this method until it has either abandoned parsing\n        (because of an unrecoverable error) or reached the end of\n        input.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startPrefixMapping$10(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Begin the scope of a prefix-URI Namespace mapping.\n\n        The information from this event is not necessary for normal\n        Namespace processing: the SAX XML reader will automatically\n        replace prefixes for element and attribute names when the\n        http://xml.org/sax/features/namespaces feature is true (the\n        default).\n\n        There are cases, however, when applications need to use\n        prefixes in character data or in attribute values, where they\n        cannot safely be expanded automatically; the\n        start/endPrefixMapping event supplies the information to the\n        application to expand prefixes in those contexts itself, if\n        necessary.\n\n        Note that start/endPrefixMapping events are not guaranteed to\n        be properly nested relative to each-other: all\n        startPrefixMapping events will occur before the corresponding\n        startElement event, and all endPrefixMapping events will occur\n        after the corresponding endElement event, but their order is\n        not guaranteed.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endPrefixMapping$11(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("End the scope of a prefix-URI mapping.\n\n        See startPrefixMapping for details. This event will always\n        occur after the corresponding endElement event, but the order\n        of endPrefixMapping events is not otherwise guaranteed.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$12(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("Signals the start of an element in non-namespace mode.\n\n        The name parameter contains the raw XML 1.0 name of the\n        element type as a string and the attrs parameter holds an\n        instance of the Attributes class containing the attributes of\n        the element.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElement$13(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Signals the end of an element in non-namespace mode.\n\n        The name parameter contains the name of the element type, just\n        as with the startElement event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElementNS$14(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Signals the start of an element in namespace mode.\n\n        The name parameter contains the name of the element type as a\n        (uri, localname) tuple, the qname parameter the raw XML 1.0\n        name used in the source document, and the attrs parameter\n        holds an instance of the Attributes class containing the\n        attributes of the element.\n\n        The uri part of the name tuple is None for elements which have\n        no namespace.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElementNS$15(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyString.fromInterned("Signals the end of an element in namespace mode.\n\n        The name parameter contains the name of the element type, just\n        as with the startElementNS event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$16(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyString.fromInterned("Receive notification of character data.\n\n        The Parser will call this method to report each chunk of\n        character data. SAX parsers may return all contiguous\n        character data in a single chunk, or they may split it into\n        several chunks; however, all of the characters in any single\n        event must come from the same external entity so that the\n        Locator provides useful information.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$17(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyString.fromInterned("Receive notification of ignorable whitespace in element content.\n\n        Validating Parsers must use this method to report each chunk\n        of ignorable whitespace (see the W3C XML 1.0 recommendation,\n        section 2.10): non-validating parsers may also use this method\n        if they are capable of parsing and using content models.\n\n        SAX parsers may return all contiguous whitespace in a single\n        chunk, or they may split it into several chunks; however, all\n        of the characters in any single event must come from the same\n        external entity, so that the Locator provides useful\n        information.\n\n        The application must not attempt to read from the array\n        outside of the specified range.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$18(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyString.fromInterned("Receive notification of a processing instruction.\n\n        The Parser will invoke this method once for each processing\n        instruction found: note that processing instructions may occur\n        before or after the main document element.\n\n        A SAX parser should never report an XML declaration (XML 1.0,\n        section 2.8) or a text declaration (XML 1.0, section 4.3.1)\n        using this method.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject skippedEntity$19(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyString.fromInterned("Receive notification of a skipped entity.\n\n        The Parser will invoke this method once for each entity\n        skipped. Non-validating processors may skip entities if they\n        have not seen the declarations (because, for example, the\n        entity was declared in an external DTD subset). All processors\n        may skip external entities, depending on the values of the\n        http://xml.org/sax/features/external-general-entities and the\n        http://xml.org/sax/features/external-parameter-entities\n        properties.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DTDHandler$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handle DTD events.\n\n    This interface specifies only those DTD events required for basic\n    parsing (unparsed entities and attributes)."));
      var1.setline(215);
      PyString.fromInterned("Handle DTD events.\n\n    This interface specifies only those DTD events required for basic\n    parsing (unparsed entities and attributes).");
      var1.setline(217);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, notationDecl$21, PyString.fromInterned("Handle a notation declaration event."));
      var1.setlocal("notationDecl", var4);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unparsedEntityDecl$22, PyString.fromInterned("Handle an unparsed entity declaration event."));
      var1.setlocal("unparsedEntityDecl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject notationDecl$21(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyString.fromInterned("Handle a notation declaration event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unparsedEntityDecl$22(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Handle an unparsed entity declaration event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EntityResolver$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Basic interface for resolving entities. If you create an object\n    implementing this interface, then register the object with your\n    Parser, the parser will call the method in your object to\n    resolve all external entities. Note that DefaultHandler implements\n    this interface with the default behaviour."));
      var1.setline(231);
      PyString.fromInterned("Basic interface for resolving entities. If you create an object\n    implementing this interface, then register the object with your\n    Parser, the parser will call the method in your object to\n    resolve all external entities. Note that DefaultHandler implements\n    this interface with the default behaviour.");
      var1.setline(233);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, resolveEntity$24, PyString.fromInterned("Resolve the system identifier of an entity and return either\n        the system identifier to read from as a string, or an InputSource\n        to read from."));
      var1.setlocal("resolveEntity", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject resolveEntity$24(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      PyString.fromInterned("Resolve the system identifier of an entity and return either\n        the system identifier to read from as a string, or an InputSource\n        to read from.");
      var1.setline(237);
      PyObject var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public handler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ErrorHandler$1 = Py.newCode(0, var2, var1, "ErrorHandler", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "exception"};
      error$2 = Py.newCode(2, var2, var1, "error", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      fatalError$3 = Py.newCode(2, var2, var1, "fatalError", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      warning$4 = Py.newCode(2, var2, var1, "warning", 40, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ContentHandler$5 = Py.newCode(0, var2, var1, "ContentHandler", 47, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$6 = Py.newCode(1, var2, var1, "__init__", 54, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locator"};
      setDocumentLocator$7 = Py.newCode(2, var2, var1, "setDocumentLocator", 57, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startDocument$8 = Py.newCode(1, var2, var1, "startDocument", 80, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDocument$9 = Py.newCode(1, var2, var1, "endDocument", 87, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      startPrefixMapping$10 = Py.newCode(3, var2, var1, "startPrefixMapping", 96, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      endPrefixMapping$11 = Py.newCode(2, var2, var1, "endPrefixMapping", 119, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attrs"};
      startElement$12 = Py.newCode(3, var2, var1, "startElement", 126, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$13 = Py.newCode(2, var2, var1, "endElement", 134, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "qname", "attrs"};
      startElementNS$14 = Py.newCode(4, var2, var1, "startElementNS", 140, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "qname"};
      endElementNS$15 = Py.newCode(3, var2, var1, "endElementNS", 152, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      characters$16 = Py.newCode(2, var2, var1, "characters", 158, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "whitespace"};
      ignorableWhitespace$17 = Py.newCode(2, var2, var1, "ignorableWhitespace", 168, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$18 = Py.newCode(3, var2, var1, "processingInstruction", 185, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      skippedEntity$19 = Py.newCode(2, var2, var1, "skippedEntity", 196, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DTDHandler$20 = Py.newCode(0, var2, var1, "DTDHandler", 211, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "publicId", "systemId"};
      notationDecl$21 = Py.newCode(4, var2, var1, "notationDecl", 217, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "ndata"};
      unparsedEntityDecl$22 = Py.newCode(5, var2, var1, "unparsedEntityDecl", 220, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EntityResolver$23 = Py.newCode(0, var2, var1, "EntityResolver", 226, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "publicId", "systemId"};
      resolveEntity$24 = Py.newCode(3, var2, var1, "resolveEntity", 233, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new handler$py("xml/sax/handler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(handler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ErrorHandler$1(var2, var3);
         case 2:
            return this.error$2(var2, var3);
         case 3:
            return this.fatalError$3(var2, var3);
         case 4:
            return this.warning$4(var2, var3);
         case 5:
            return this.ContentHandler$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.setDocumentLocator$7(var2, var3);
         case 8:
            return this.startDocument$8(var2, var3);
         case 9:
            return this.endDocument$9(var2, var3);
         case 10:
            return this.startPrefixMapping$10(var2, var3);
         case 11:
            return this.endPrefixMapping$11(var2, var3);
         case 12:
            return this.startElement$12(var2, var3);
         case 13:
            return this.endElement$13(var2, var3);
         case 14:
            return this.startElementNS$14(var2, var3);
         case 15:
            return this.endElementNS$15(var2, var3);
         case 16:
            return this.characters$16(var2, var3);
         case 17:
            return this.ignorableWhitespace$17(var2, var3);
         case 18:
            return this.processingInstruction$18(var2, var3);
         case 19:
            return this.skippedEntity$19(var2, var3);
         case 20:
            return this.DTDHandler$20(var2, var3);
         case 21:
            return this.notationDecl$21(var2, var3);
         case 22:
            return this.unparsedEntityDecl$22(var2, var3);
         case 23:
            return this.EntityResolver$23(var2, var3);
         case 24:
            return this.resolveEntity$24(var2, var3);
         default:
            return null;
      }
   }
}
