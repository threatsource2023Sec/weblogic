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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/sax/saxlib.py")
public class saxlib$py extends PyFunctionTable implements PyRunnable {
   static saxlib$py self;
   static final PyCode f$0;
   static final PyCode XMLFilter$1;
   static final PyCode __init__$2;
   static final PyCode setParent$3;
   static final PyCode getParent$4;
   static final PyCode Attributes$5;
   static final PyCode getLength$6;
   static final PyCode getType$7;
   static final PyCode getValue$8;
   static final PyCode getValueByQName$9;
   static final PyCode getNameByQName$10;
   static final PyCode getNames$11;
   static final PyCode getQNames$12;
   static final PyCode __len__$13;
   static final PyCode __getitem__$14;
   static final PyCode keys$15;
   static final PyCode has_key$16;
   static final PyCode get$17;
   static final PyCode copy$18;
   static final PyCode items$19;
   static final PyCode values$20;
   static final PyCode DeclHandler$21;
   static final PyCode attributeDecl$22;
   static final PyCode elementDecl$23;
   static final PyCode internalEntityDecl$24;
   static final PyCode externalEntityDecl$25;
   static final PyCode LexicalHandler$26;
   static final PyCode comment$27;
   static final PyCode startDTD$28;
   static final PyCode endDTD$29;
   static final PyCode startEntity$30;
   static final PyCode endEntity$31;
   static final PyCode startCDATA$32;
   static final PyCode endCDATA$33;
   static final PyCode AttributeList$34;
   static final PyCode getLength$35;
   static final PyCode getName$36;
   static final PyCode getType$37;
   static final PyCode getValue$38;
   static final PyCode __len__$39;
   static final PyCode __getitem__$40;
   static final PyCode keys$41;
   static final PyCode has_key$42;
   static final PyCode get$43;
   static final PyCode copy$44;
   static final PyCode items$45;
   static final PyCode values$46;
   static final PyCode DocumentHandler$47;
   static final PyCode characters$48;
   static final PyCode endDocument$49;
   static final PyCode endElement$50;
   static final PyCode ignorableWhitespace$51;
   static final PyCode processingInstruction$52;
   static final PyCode setDocumentLocator$53;
   static final PyCode startDocument$54;
   static final PyCode startElement$55;
   static final PyCode HandlerBase$56;
   static final PyCode Parser$57;
   static final PyCode __init__$58;
   static final PyCode parse$59;
   static final PyCode parseFile$60;
   static final PyCode setDocumentHandler$61;
   static final PyCode setDTDHandler$62;
   static final PyCode setEntityResolver$63;
   static final PyCode setErrorHandler$64;
   static final PyCode setLocale$65;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nThis module contains the core classes of version 2.0 of SAX for Python.\nThis file provides only default classes with absolutely minimum\nfunctionality, from which drivers and applications can be subclassed.\n\nMany of these classes are empty and are included only as documentation\nof the interfaces.\n\n$Id: saxlib.py,v 1.12 2002/05/10 14:49:21 akuchling Exp $\n"));
      var1.setline(10);
      PyString.fromInterned("\nThis module contains the core classes of version 2.0 of SAX for Python.\nThis file provides only default classes with absolutely minimum\nfunctionality, from which drivers and applications can be subclassed.\n\nMany of these classes are empty and are included only as documentation\nof the interfaces.\n\n$Id: saxlib.py,v 1.12 2002/05/10 14:49:21 akuchling Exp $\n");
      var1.setline(12);
      PyString var3 = PyString.fromInterned("2.0beta");
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(18);
      String[] var5 = new String[]{"ErrorHandler", "ContentHandler", "DTDHandler", "EntityResolver"};
      PyObject[] var6 = imp.importFrom("handler", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("ErrorHandler", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("ContentHandler", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("DTDHandler", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("EntityResolver", var4);
      var4 = null;
      var1.setline(19);
      var5 = new String[]{"XMLReader", "InputSource", "Locator", "IncrementalParser"};
      var6 = imp.importFrom("xmlreader", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("XMLReader", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("InputSource", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("Locator", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("IncrementalParser", var4);
      var4 = null;
      var1.setline(20);
      imp.importAll("_exceptions", var1, -1);
      var1.setline(22);
      var5 = new String[]{"feature_namespaces", "feature_namespace_prefixes", "feature_string_interning", "feature_validation", "feature_external_ges", "feature_external_pes", "all_features", "property_lexical_handler", "property_declaration_handler", "property_dom_node", "property_xml_string", "all_properties"};
      var6 = imp.importFrom("handler", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("feature_namespaces", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("feature_namespace_prefixes", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("feature_string_interning", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("feature_validation", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("feature_external_ges", var4);
      var4 = null;
      var4 = var6[5];
      var1.setlocal("feature_external_pes", var4);
      var4 = null;
      var4 = var6[6];
      var1.setlocal("all_features", var4);
      var4 = null;
      var4 = var6[7];
      var1.setlocal("property_lexical_handler", var4);
      var4 = null;
      var4 = var6[8];
      var1.setlocal("property_declaration_handler", var4);
      var4 = null;
      var4 = var6[9];
      var1.setlocal("property_dom_node", var4);
      var4 = null;
      var4 = var6[10];
      var1.setlocal("property_xml_string", var4);
      var4 = null;
      var4 = var6[11];
      var1.setlocal("all_properties", var4);
      var4 = null;
      var1.setline(44);
      var6 = new PyObject[]{var1.getname("XMLReader")};
      var4 = Py.makeClass("XMLFilter", var6, XMLFilter$1);
      var1.setlocal("XMLFilter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(70);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Attributes", var6, Attributes$5);
      var1.setlocal("Attributes", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(150);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DeclHandler", var6, DeclHandler$21);
      var1.setlocal("DeclHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(212);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("LexicalHandler", var6, LexicalHandler$26);
      var1.setlocal("LexicalHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(286);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("AttributeList", var6, AttributeList$34);
      var1.setlocal("AttributeList", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(337);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DocumentHandler", var6, DocumentHandler$47);
      var1.setlocal("DocumentHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(375);
      var6 = new PyObject[]{var1.getname("EntityResolver"), var1.getname("DTDHandler"), var1.getname("DocumentHandler"), var1.getname("ErrorHandler")};
      var4 = Py.makeClass("HandlerBase", var6, HandlerBase$56);
      var1.setlocal("HandlerBase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(388);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Parser", var6, Parser$57);
      var1.setlocal("Parser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject XMLFilter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Interface for a SAX2 parser filter.\n\n    A parser filter is an XMLReader that gets its events from another\n    XMLReader (which may in turn also be a filter) rather than from a\n    primary source like a document or other non-SAX data source.\n    Filters can modify a stream of events before passing it on to its\n    handlers."));
      var1.setline(51);
      PyString.fromInterned("Interface for a SAX2 parser filter.\n\n    A parser filter is an XMLReader that gets its events from another\n    XMLReader (which may in turn also be a filter) rather than from a\n    primary source like a document or other non-SAX data source.\n    Filters can modify a stream of events before passing it on to its\n    handlers.");
      var1.setline(53);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Creates a filter instance, allowing applications to set the\n        parent on instantiation."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setParent$3, PyString.fromInterned("Sets the parent XMLReader of this filter. The argument may\n        not be None."));
      var1.setlocal("setParent", var4);
      var3 = null;
      var1.setline(64);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getParent$4, PyString.fromInterned("Returns the parent of this filter."));
      var1.setlocal("getParent", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("Creates a filter instance, allowing applications to set the\n        parent on instantiation.");
      var1.setline(56);
      var1.getglobal("XMLReader").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(57);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_parent", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setParent$3(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned("Sets the parent XMLReader of this filter. The argument may\n        not be None.");
      var1.setline(62);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_parent", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getParent$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyString.fromInterned("Returns the parent of this filter.");
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getattr__("_parent");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Attributes$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Interface for a list of XML attributes.\n\n    Contains a list of XML attributes, accessible by name."));
      var1.setline(73);
      PyString.fromInterned("Interface for a list of XML attributes.\n\n    Contains a list of XML attributes, accessible by name.");
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, getLength$6, PyString.fromInterned("Returns the number of attributes in the list."));
      var1.setlocal("getLength", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getType$7, PyString.fromInterned("Returns the type of the attribute with the given name."));
      var1.setlocal("getType", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValue$8, PyString.fromInterned("Returns the value of the attribute with the given name."));
      var1.setlocal("getValue", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValueByQName$9, PyString.fromInterned("Returns the value of the attribute with the given raw (or\n        qualified) name."));
      var1.setlocal("getValueByQName", var4);
      var3 = null;
      var1.setline(92);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNameByQName$10, PyString.fromInterned("Returns the namespace name of the attribute with the given\n        raw (or qualified) name."));
      var1.setlocal("getNameByQName", var4);
      var3 = null;
      var1.setline(97);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNames$11, PyString.fromInterned("Returns a list of the names of all attributes\n        in the list."));
      var1.setlocal("getNames", var4);
      var3 = null;
      var1.setline(102);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNames$12, PyString.fromInterned("Returns a list of the raw qualified names of all attributes\n        in the list."));
      var1.setlocal("getQNames", var4);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$13, PyString.fromInterned("Alias for getLength."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(111);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$14, PyString.fromInterned("Alias for getValue."));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$15, PyString.fromInterned("Returns a list of the attribute names in the list."));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$16, PyString.fromInterned("True if the attribute is in the list, false otherwise."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(123);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$17, PyString.fromInterned("Return the value associated with attribute name; if it is not\n        available, then return the alternative."));
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$18, PyString.fromInterned("Return a copy of the Attributes object."));
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(132);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$19, PyString.fromInterned("Return a list of (attribute_name, value) pairs."));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$20, PyString.fromInterned("Return a list of all attribute values."));
      var1.setlocal("values", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getLength$6(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("Returns the number of attributes in the list.");
      var1.setline(77);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getType$7(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Returns the type of the attribute with the given name.");
      var1.setline(81);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getValue$8(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("Returns the value of the attribute with the given name.");
      var1.setline(85);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getValueByQName$9(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Returns the value of the attribute with the given raw (or\n        qualified) name.");
      var1.setline(90);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getNameByQName$10(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Returns the namespace name of the attribute with the given\n        raw (or qualified) name.");
      var1.setline(95);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getNames$11(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("Returns a list of the names of all attributes\n        in the list.");
      var1.setline(100);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject getQNames$12(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Returns a list of the raw qualified names of all attributes\n        in the list.");
      var1.setline(105);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject __len__$13(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyString.fromInterned("Alias for getLength.");
      var1.setline(109);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject __getitem__$14(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Alias for getValue.");
      var1.setline(113);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject keys$15(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyString.fromInterned("Returns a list of the attribute names in the list.");
      var1.setline(117);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject has_key$16(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("True if the attribute is in the list, false otherwise.");
      var1.setline(121);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject get$17(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyString.fromInterned("Return the value associated with attribute name; if it is not\n        available, then return the alternative.");
      var1.setline(126);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject copy$18(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyString.fromInterned("Return a copy of the Attributes object.");
      var1.setline(130);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject items$19(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Return a list of (attribute_name, value) pairs.");
      var1.setline(134);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject values$20(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("Return a list of all attribute values.");
      var1.setline(138);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method must be implemented!")));
   }

   public PyObject DeclHandler$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Optional SAX2 handler for DTD declaration events.\n\n    Note that some DTD declarations are already reported through the\n    DTDHandler interface. All events reported to this handler will\n    occur between the startDTD and endDTD events of the\n    LexicalHandler.\n\n    To set the DeclHandler for an XMLReader, use the setProperty method\n    with the identifier http://xml.org/sax/handlers/DeclHandler."));
      var1.setline(159);
      PyString.fromInterned("Optional SAX2 handler for DTD declaration events.\n\n    Note that some DTD declarations are already reported through the\n    DTDHandler interface. All events reported to this handler will\n    occur between the startDTD and endDTD events of the\n    LexicalHandler.\n\n    To set the DeclHandler for an XMLReader, use the setProperty method\n    with the identifier http://xml.org/sax/handlers/DeclHandler.");
      var1.setline(161);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, attributeDecl$22, PyString.fromInterned("Report an attribute type declaration.\n\n        Only the first declaration will be reported. The type will be\n        one of the strings \"CDATA\", \"ID\", \"IDREF\", \"IDREFS\",\n        \"NMTOKEN\", \"NMTOKENS\", \"ENTITY\", \"ENTITIES\", or \"NOTATION\", or\n        a list of names (in the case of enumerated definitions).\n\n        elem_name is the element type name, attr_name the attribute\n        type name, type a string representing the attribute type,\n        value_def a string representing the default declaration\n        ('#IMPLIED', '#REQUIRED', '#FIXED' or None). value is a string\n        representing the attribute's default value, or None if there\n        is none."));
      var1.setlocal("attributeDecl", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, elementDecl$23, PyString.fromInterned("Report an element type declaration.\n\n        Only the first declaration will be reported.\n\n        content_model is the string 'EMPTY', the string 'ANY' or the content\n        model structure represented as tuple (separator, tokens, modifier)\n        where separator is the separator in the token list (that is, '|' or\n        ','), tokens is the list of tokens (element type names or tuples\n        representing parentheses) and modifier is the quantity modifier\n        ('*', '?' or '+')."));
      var1.setlocal("elementDecl", var4);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, internalEntityDecl$24, PyString.fromInterned("Report an internal entity declaration.\n\n        Only the first declaration of an entity will be reported.\n\n        name is the name of the entity. If it is a parameter entity,\n        the name will begin with '%'. value is the replacement text of\n        the entity."));
      var1.setlocal("internalEntityDecl", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, externalEntityDecl$25, PyString.fromInterned("Report a parsed entity declaration. (Unparsed entities are\n        reported to the DTDHandler.)\n\n        Only the first declaration for each entity will be reported.\n\n        name is the name of the entity. If it is a parameter entity,\n        the name will begin with '%'. public_id and system_id are the\n        public and system identifiers of the entity. public_id will be\n        None if none were declared."));
      var1.setlocal("externalEntityDecl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject attributeDecl$22(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyString.fromInterned("Report an attribute type declaration.\n\n        Only the first declaration will be reported. The type will be\n        one of the strings \"CDATA\", \"ID\", \"IDREF\", \"IDREFS\",\n        \"NMTOKEN\", \"NMTOKENS\", \"ENTITY\", \"ENTITIES\", or \"NOTATION\", or\n        a list of names (in the case of enumerated definitions).\n\n        elem_name is the element type name, attr_name the attribute\n        type name, type a string representing the attribute type,\n        value_def a string representing the default declaration\n        ('#IMPLIED', '#REQUIRED', '#FIXED' or None). value is a string\n        representing the attribute's default value, or None if there\n        is none.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject elementDecl$23(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("Report an element type declaration.\n\n        Only the first declaration will be reported.\n\n        content_model is the string 'EMPTY', the string 'ANY' or the content\n        model structure represented as tuple (separator, tokens, modifier)\n        where separator is the separator in the token list (that is, '|' or\n        ','), tokens is the list of tokens (element type names or tuples\n        representing parentheses) and modifier is the quantity modifier\n        ('*', '?' or '+').");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject internalEntityDecl$24(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyString.fromInterned("Report an internal entity declaration.\n\n        Only the first declaration of an entity will be reported.\n\n        name is the name of the entity. If it is a parameter entity,\n        the name will begin with '%'. value is the replacement text of\n        the entity.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject externalEntityDecl$25(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyString.fromInterned("Report a parsed entity declaration. (Unparsed entities are\n        reported to the DTDHandler.)\n\n        Only the first declaration for each entity will be reported.\n\n        name is the name of the entity. If it is a parameter entity,\n        the name will begin with '%'. public_id and system_id are the\n        public and system identifiers of the entity. public_id will be\n        None if none were declared.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LexicalHandler$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Optional SAX2 handler for lexical events.\n\n    This handler is used to obtain lexical information about an XML\n    document, that is, information about how the document was encoded\n    (as opposed to what it contains, which is reported to the\n    ContentHandler), such as comments and CDATA marked section\n    boundaries.\n\n    To set the LexicalHandler of an XMLReader, use the setProperty\n    method with the property identifier\n    'http://xml.org/sax/handlers/LexicalHandler'. There is no\n    guarantee that the XMLReader will support or recognize this\n    property."));
      var1.setline(225);
      PyString.fromInterned("Optional SAX2 handler for lexical events.\n\n    This handler is used to obtain lexical information about an XML\n    document, that is, information about how the document was encoded\n    (as opposed to what it contains, which is reported to the\n    ContentHandler), such as comments and CDATA marked section\n    boundaries.\n\n    To set the LexicalHandler of an XMLReader, use the setProperty\n    method with the property identifier\n    'http://xml.org/sax/handlers/LexicalHandler'. There is no\n    guarantee that the XMLReader will support or recognize this\n    property.");
      var1.setline(227);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, comment$27, PyString.fromInterned("Reports a comment anywhere in the document (including the\n        DTD and outside the document element).\n\n        content is a string that holds the contents of the comment."));
      var1.setlocal("comment", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDTD$28, PyString.fromInterned("Report the start of the DTD declarations, if the document\n        has an associated DTD.\n\n        A startEntity event will be reported before declaration events\n        from the external DTD subset are reported, and this can be\n        used to infer from which subset DTD declarations derive.\n\n        name is the name of the document element type, public_id the\n        public identifier of the DTD (or None if none were supplied)\n        and system_id the system identfier of the external subset (or\n        None if none were supplied)."));
      var1.setlocal("startDTD", var4);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDTD$29, PyString.fromInterned("Signals the end of DTD declarations."));
      var1.setlocal("endDTD", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startEntity$30, PyString.fromInterned("Report the beginning of an entity.\n\n        The start and end of the document entity is not reported. The\n        start and end of the external DTD subset is reported with the\n        pseudo-name '[dtd]'.\n\n        Skipped entities will be reported through the skippedEntity\n        event of the ContentHandler rather than through this event.\n\n        name is the name of the entity. If it is a parameter entity,\n        the name will begin with '%'."));
      var1.setlocal("startEntity", var4);
      var3 = null;
      var1.setline(262);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endEntity$31, PyString.fromInterned("Reports the end of an entity. name is the name of the\n        entity, and follows the same conventions as for\n        startEntity."));
      var1.setlocal("endEntity", var4);
      var3 = null;
      var1.setline(267);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startCDATA$32, PyString.fromInterned("Reports the beginning of a CDATA marked section.\n\n        The contents of the CDATA marked section will be reported\n        through the characters event."));
      var1.setlocal("startCDATA", var4);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endCDATA$33, PyString.fromInterned("Reports the end of a CDATA marked section."));
      var1.setlocal("endCDATA", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject comment$27(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyString.fromInterned("Reports a comment anywhere in the document (including the\n        DTD and outside the document element).\n\n        content is a string that holds the contents of the comment.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDTD$28(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyString.fromInterned("Report the start of the DTD declarations, if the document\n        has an associated DTD.\n\n        A startEntity event will be reported before declaration events\n        from the external DTD subset are reported, and this can be\n        used to infer from which subset DTD declarations derive.\n\n        name is the name of the document element type, public_id the\n        public identifier of the DTD (or None if none were supplied)\n        and system_id the system identfier of the external subset (or\n        None if none were supplied).");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDTD$29(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyString.fromInterned("Signals the end of DTD declarations.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startEntity$30(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Report the beginning of an entity.\n\n        The start and end of the document entity is not reported. The\n        start and end of the external DTD subset is reported with the\n        pseudo-name '[dtd]'.\n\n        Skipped entities will be reported through the skippedEntity\n        event of the ContentHandler rather than through this event.\n\n        name is the name of the entity. If it is a parameter entity,\n        the name will begin with '%'.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endEntity$31(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString.fromInterned("Reports the end of an entity. name is the name of the\n        entity, and follows the same conventions as for\n        startEntity.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startCDATA$32(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyString.fromInterned("Reports the beginning of a CDATA marked section.\n\n        The contents of the CDATA marked section will be reported\n        through the characters event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endCDATA$33(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyString.fromInterned("Reports the end of a CDATA marked section.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AttributeList$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Interface for an attribute list. This interface provides\n    information about a list of attributes for an element (only\n    specified or defaulted attributes will be reported). Note that the\n    information returned by this object will be valid only during the\n    scope of the DocumentHandler.startElement callback, and the\n    attributes will not necessarily be provided in the order declared\n    or specified."));
      var1.setline(293);
      PyString.fromInterned("Interface for an attribute list. This interface provides\n    information about a list of attributes for an element (only\n    specified or defaulted attributes will be reported). Note that the\n    information returned by this object will be valid only during the\n    scope of the DocumentHandler.startElement callback, and the\n    attributes will not necessarily be provided in the order declared\n    or specified.");
      var1.setline(295);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, getLength$35, PyString.fromInterned("Return the number of attributes in list."));
      var1.setlocal("getLength", var4);
      var3 = null;
      var1.setline(298);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getName$36, PyString.fromInterned("Return the name of an attribute in the list."));
      var1.setlocal("getName", var4);
      var3 = null;
      var1.setline(301);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getType$37, PyString.fromInterned("Return the type of an attribute in the list. (Parameter can be\n        either integer index or attribute name.)"));
      var1.setlocal("getType", var4);
      var3 = null;
      var1.setline(305);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValue$38, PyString.fromInterned("Return the value of an attribute in the list. (Parameter can be\n        either integer index or attribute name.)"));
      var1.setlocal("getValue", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$39, PyString.fromInterned("Alias for getLength."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(312);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$40, PyString.fromInterned("Alias for getName (if key is an integer) and getValue (if string)."));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(315);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$41, PyString.fromInterned("Returns a list of the attribute names."));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(318);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$42, PyString.fromInterned("True if the attribute is in the list, false otherwise."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(321);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$43, PyString.fromInterned("Return the value associated with attribute name; if it is not\n        available, then return the alternative."));
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(325);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$44, PyString.fromInterned("Return a copy of the AttributeList."));
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$45, PyString.fromInterned("Return a list of (attribute_name,value) pairs."));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$46, PyString.fromInterned("Return a list of all attribute values."));
      var1.setlocal("values", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getLength$35(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyString.fromInterned("Return the number of attributes in list.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getName$36(PyFrame var1, ThreadState var2) {
      var1.setline(299);
      PyString.fromInterned("Return the name of an attribute in the list.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getType$37(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyString.fromInterned("Return the type of an attribute in the list. (Parameter can be\n        either integer index or attribute name.)");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getValue$38(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyString.fromInterned("Return the value of an attribute in the list. (Parameter can be\n        either integer index or attribute name.)");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$39(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyString.fromInterned("Alias for getLength.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$40(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyString.fromInterned("Alias for getName (if key is an integer) and getValue (if string).");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject keys$41(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      PyString.fromInterned("Returns a list of the attribute names.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_key$42(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyString.fromInterned("True if the attribute is in the list, false otherwise.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get$43(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyString.fromInterned("Return the value associated with attribute name; if it is not\n        available, then return the alternative.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$44(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyString.fromInterned("Return a copy of the AttributeList.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject items$45(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyString.fromInterned("Return a list of (attribute_name,value) pairs.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject values$46(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyString.fromInterned("Return a list of all attribute values.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DocumentHandler$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handle general document events. This is the main client\n    interface for SAX: it contains callbacks for the most important\n    document events, such as the start and end of elements. You need\n    to create an object that implements this interface, and then\n    register it with the Parser. If you do not want to implement\n    the entire interface, you can derive a class from HandlerBase,\n    which implements the default functionality. You can find the\n    location of any document event using the Locator interface\n    supplied by setDocumentLocator()."));
      var1.setline(346);
      PyString.fromInterned("Handle general document events. This is the main client\n    interface for SAX: it contains callbacks for the most important\n    document events, such as the start and end of elements. You need\n    to create an object that implements this interface, and then\n    register it with the Parser. If you do not want to implement\n    the entire interface, you can derive a class from HandlerBase,\n    which implements the default functionality. You can find the\n    location of any document event using the Locator interface\n    supplied by setDocumentLocator().");
      var1.setline(348);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, characters$48, PyString.fromInterned("Handle a character data event."));
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDocument$49, PyString.fromInterned("Handle an event for the end of a document."));
      var1.setlocal("endDocument", var4);
      var3 = null;
      var1.setline(354);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$50, PyString.fromInterned("Handle an event for the end of an element."));
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(357);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$51, PyString.fromInterned("Handle an event for ignorable whitespace in element content."));
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(360);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$52, PyString.fromInterned("Handle a processing instruction event."));
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(363);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDocumentLocator$53, PyString.fromInterned("Receive an object for locating the origin of SAX document events."));
      var1.setlocal("setDocumentLocator", var4);
      var3 = null;
      var1.setline(366);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDocument$54, PyString.fromInterned("Handle an event for the beginning of a document."));
      var1.setlocal("startDocument", var4);
      var3 = null;
      var1.setline(369);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$55, PyString.fromInterned("Handle an event for the beginning of an element."));
      var1.setlocal("startElement", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject characters$48(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      PyString.fromInterned("Handle a character data event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDocument$49(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyString.fromInterned("Handle an event for the end of a document.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElement$50(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyString.fromInterned("Handle an event for the end of an element.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$51(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyString.fromInterned("Handle an event for ignorable whitespace in element content.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$52(PyFrame var1, ThreadState var2) {
      var1.setline(361);
      PyString.fromInterned("Handle a processing instruction event.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDocumentLocator$53(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyString.fromInterned("Receive an object for locating the origin of SAX document events.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDocument$54(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyString.fromInterned("Handle an event for the beginning of a document.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$55(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyString.fromInterned("Handle an event for the beginning of an element.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HandlerBase$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Default base class for handlers. This class implements the\n    default behaviour for four SAX interfaces: EntityResolver,\n    DTDHandler, DocumentHandler, and ErrorHandler: rather\n    than implementing those full interfaces, you may simply extend\n    this class and override the methods that you need. Note that the\n    use of this class is optional (you are free to implement the\n    interfaces directly if you wish)."));
      var1.setline(383);
      PyString.fromInterned("Default base class for handlers. This class implements the\n    default behaviour for four SAX interfaces: EntityResolver,\n    DTDHandler, DocumentHandler, and ErrorHandler: rather\n    than implementing those full interfaces, you may simply extend\n    this class and override the methods that you need. Note that the\n    use of this class is optional (you are free to implement the\n    interfaces directly if you wish).");
      return var1.getf_locals();
   }

   public PyObject Parser$57(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Basic interface for SAX (Simple API for XML) parsers. All SAX\n    parsers must implement this basic interface: it allows users to\n    register handlers for different types of events and to initiate a\n    parse from a URI, a character stream, or a byte stream. SAX\n    parsers should also implement a zero-argument constructor."));
      var1.setline(393);
      PyString.fromInterned("Basic interface for SAX (Simple API for XML) parsers. All SAX\n    parsers must implement this basic interface: it allows users to\n    register handlers for different types of events and to initiate a\n    parse from a URI, a character stream, or a byte stream. SAX\n    parsers should also implement a zero-argument constructor.");
      var1.setline(395);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$58, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(401);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$59, PyString.fromInterned("Parse an XML document from a system identifier."));
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(404);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseFile$60, PyString.fromInterned("Parse an XML document from a file-like object."));
      var1.setlocal("parseFile", var4);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDocumentHandler$61, PyString.fromInterned("Register an object to receive basic document-related events."));
      var1.setlocal("setDocumentHandler", var4);
      var3 = null;
      var1.setline(411);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDTDHandler$62, PyString.fromInterned("Register an object to receive basic DTD-related events."));
      var1.setlocal("setDTDHandler", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setEntityResolver$63, PyString.fromInterned("Register an object to resolve external entities."));
      var1.setlocal("setEntityResolver", var4);
      var3 = null;
      var1.setline(419);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setErrorHandler$64, PyString.fromInterned("Register an object to receive error-message events."));
      var1.setlocal("setErrorHandler", var4);
      var3 = null;
      var1.setline(423);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setLocale$65, PyString.fromInterned("Allow an application to set the locale for errors and warnings.\n\n        SAX parsers are not required to provide localisation for errors\n        and warnings; if they cannot support the requested locale,\n        however, they must throw a SAX exception. Applications may\n        request a locale change in the middle of a parse."));
      var1.setlocal("setLocale", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$58(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyObject var3 = var1.getglobal("DocumentHandler").__call__(var2);
      var1.getlocal(0).__setattr__("doc_handler", var3);
      var3 = null;
      var1.setline(397);
      var3 = var1.getglobal("DTDHandler").__call__(var2);
      var1.getlocal(0).__setattr__("dtd_handler", var3);
      var3 = null;
      var1.setline(398);
      var3 = var1.getglobal("EntityResolver").__call__(var2);
      var1.getlocal(0).__setattr__("ent_handler", var3);
      var3 = null;
      var1.setline(399);
      var3 = var1.getglobal("ErrorHandler").__call__(var2);
      var1.getlocal(0).__setattr__("err_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$59(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Parse an XML document from a system identifier.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseFile$60(PyFrame var1, ThreadState var2) {
      var1.setline(405);
      PyString.fromInterned("Parse an XML document from a file-like object.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDocumentHandler$61(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyString.fromInterned("Register an object to receive basic document-related events.");
      var1.setline(409);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("doc_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDTDHandler$62(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyString.fromInterned("Register an object to receive basic DTD-related events.");
      var1.setline(413);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("dtd_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setEntityResolver$63(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyString.fromInterned("Register an object to resolve external entities.");
      var1.setline(417);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ent_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setErrorHandler$64(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyString.fromInterned("Register an object to receive error-message events.");
      var1.setline(421);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("err_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setLocale$65(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      PyString.fromInterned("Allow an application to set the locale for errors and warnings.\n\n        SAX parsers are not required to provide localisation for errors\n        and warnings; if they cannot support the requested locale,\n        however, they must throw a SAX exception. Applications may\n        request a locale change in the middle of a parse.");
      var1.setline(430);
      throw Py.makeException(var1.getglobal("SAXNotSupportedException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Locale support not implemented")));
   }

   public saxlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      XMLFilter$1 = Py.newCode(0, var2, var1, "XMLFilter", 44, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "parent"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 53, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parent"};
      setParent$3 = Py.newCode(2, var2, var1, "setParent", 59, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getParent$4 = Py.newCode(1, var2, var1, "getParent", 64, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Attributes$5 = Py.newCode(0, var2, var1, "Attributes", 70, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      getLength$6 = Py.newCode(1, var2, var1, "getLength", 75, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getType$7 = Py.newCode(2, var2, var1, "getType", 79, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getValue$8 = Py.newCode(2, var2, var1, "getValue", 83, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getValueByQName$9 = Py.newCode(2, var2, var1, "getValueByQName", 87, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getNameByQName$10 = Py.newCode(2, var2, var1, "getNameByQName", 92, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getNames$11 = Py.newCode(1, var2, var1, "getNames", 97, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getQNames$12 = Py.newCode(1, var2, var1, "getQNames", 102, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$13 = Py.newCode(1, var2, var1, "__len__", 107, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$14 = Py.newCode(2, var2, var1, "__getitem__", 111, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$15 = Py.newCode(1, var2, var1, "keys", 115, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      has_key$16 = Py.newCode(2, var2, var1, "has_key", 119, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "alternative"};
      get$17 = Py.newCode(3, var2, var1, "get", 123, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$18 = Py.newCode(1, var2, var1, "copy", 128, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$19 = Py.newCode(1, var2, var1, "items", 132, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$20 = Py.newCode(1, var2, var1, "values", 136, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DeclHandler$21 = Py.newCode(0, var2, var1, "DeclHandler", 150, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "elem_name", "attr_name", "type", "value_def", "value"};
      attributeDecl$22 = Py.newCode(6, var2, var1, "attributeDecl", 161, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem_name", "content_model"};
      elementDecl$23 = Py.newCode(3, var2, var1, "elementDecl", 176, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      internalEntityDecl$24 = Py.newCode(3, var2, var1, "internalEntityDecl", 188, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "public_id", "system_id"};
      externalEntityDecl$25 = Py.newCode(4, var2, var1, "externalEntityDecl", 197, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LexicalHandler$26 = Py.newCode(0, var2, var1, "LexicalHandler", 212, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "content"};
      comment$27 = Py.newCode(2, var2, var1, "comment", 227, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "public_id", "system_id"};
      startDTD$28 = Py.newCode(4, var2, var1, "startDTD", 233, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDTD$29 = Py.newCode(1, var2, var1, "endDTD", 246, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      startEntity$30 = Py.newCode(2, var2, var1, "startEntity", 249, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endEntity$31 = Py.newCode(2, var2, var1, "endEntity", 262, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startCDATA$32 = Py.newCode(1, var2, var1, "startCDATA", 267, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endCDATA$33 = Py.newCode(1, var2, var1, "endCDATA", 273, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AttributeList$34 = Py.newCode(0, var2, var1, "AttributeList", 286, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      getLength$35 = Py.newCode(1, var2, var1, "getLength", 295, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      getName$36 = Py.newCode(2, var2, var1, "getName", 298, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      getType$37 = Py.newCode(2, var2, var1, "getType", 301, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      getValue$38 = Py.newCode(2, var2, var1, "getValue", 305, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$39 = Py.newCode(1, var2, var1, "__len__", 309, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __getitem__$40 = Py.newCode(2, var2, var1, "__getitem__", 312, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$41 = Py.newCode(1, var2, var1, "keys", 315, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$42 = Py.newCode(2, var2, var1, "has_key", 318, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "alternative"};
      get$43 = Py.newCode(3, var2, var1, "get", 321, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$44 = Py.newCode(1, var2, var1, "copy", 325, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$45 = Py.newCode(1, var2, var1, "items", 328, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$46 = Py.newCode(1, var2, var1, "values", 331, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocumentHandler$47 = Py.newCode(0, var2, var1, "DocumentHandler", 337, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ch", "start", "length"};
      characters$48 = Py.newCode(4, var2, var1, "characters", 348, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDocument$49 = Py.newCode(1, var2, var1, "endDocument", 351, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$50 = Py.newCode(2, var2, var1, "endElement", 354, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ch", "start", "length"};
      ignorableWhitespace$51 = Py.newCode(4, var2, var1, "ignorableWhitespace", 357, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$52 = Py.newCode(3, var2, var1, "processingInstruction", 360, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locator"};
      setDocumentLocator$53 = Py.newCode(2, var2, var1, "setDocumentLocator", 363, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startDocument$54 = Py.newCode(1, var2, var1, "startDocument", 366, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "atts"};
      startElement$55 = Py.newCode(3, var2, var1, "startElement", 369, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HandlerBase$56 = Py.newCode(0, var2, var1, "HandlerBase", 375, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Parser$57 = Py.newCode(0, var2, var1, "Parser", 388, false, false, self, 57, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$58 = Py.newCode(1, var2, var1, "__init__", 395, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "systemId"};
      parse$59 = Py.newCode(2, var2, var1, "parse", 401, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fileobj"};
      parseFile$60 = Py.newCode(2, var2, var1, "parseFile", 404, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      setDocumentHandler$61 = Py.newCode(2, var2, var1, "setDocumentHandler", 407, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      setDTDHandler$62 = Py.newCode(2, var2, var1, "setDTDHandler", 411, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resolver"};
      setEntityResolver$63 = Py.newCode(2, var2, var1, "setEntityResolver", 415, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      setErrorHandler$64 = Py.newCode(2, var2, var1, "setErrorHandler", 419, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locale"};
      setLocale$65 = Py.newCode(2, var2, var1, "setLocale", 423, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new saxlib$py("xml/sax/saxlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(saxlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.XMLFilter$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.setParent$3(var2, var3);
         case 4:
            return this.getParent$4(var2, var3);
         case 5:
            return this.Attributes$5(var2, var3);
         case 6:
            return this.getLength$6(var2, var3);
         case 7:
            return this.getType$7(var2, var3);
         case 8:
            return this.getValue$8(var2, var3);
         case 9:
            return this.getValueByQName$9(var2, var3);
         case 10:
            return this.getNameByQName$10(var2, var3);
         case 11:
            return this.getNames$11(var2, var3);
         case 12:
            return this.getQNames$12(var2, var3);
         case 13:
            return this.__len__$13(var2, var3);
         case 14:
            return this.__getitem__$14(var2, var3);
         case 15:
            return this.keys$15(var2, var3);
         case 16:
            return this.has_key$16(var2, var3);
         case 17:
            return this.get$17(var2, var3);
         case 18:
            return this.copy$18(var2, var3);
         case 19:
            return this.items$19(var2, var3);
         case 20:
            return this.values$20(var2, var3);
         case 21:
            return this.DeclHandler$21(var2, var3);
         case 22:
            return this.attributeDecl$22(var2, var3);
         case 23:
            return this.elementDecl$23(var2, var3);
         case 24:
            return this.internalEntityDecl$24(var2, var3);
         case 25:
            return this.externalEntityDecl$25(var2, var3);
         case 26:
            return this.LexicalHandler$26(var2, var3);
         case 27:
            return this.comment$27(var2, var3);
         case 28:
            return this.startDTD$28(var2, var3);
         case 29:
            return this.endDTD$29(var2, var3);
         case 30:
            return this.startEntity$30(var2, var3);
         case 31:
            return this.endEntity$31(var2, var3);
         case 32:
            return this.startCDATA$32(var2, var3);
         case 33:
            return this.endCDATA$33(var2, var3);
         case 34:
            return this.AttributeList$34(var2, var3);
         case 35:
            return this.getLength$35(var2, var3);
         case 36:
            return this.getName$36(var2, var3);
         case 37:
            return this.getType$37(var2, var3);
         case 38:
            return this.getValue$38(var2, var3);
         case 39:
            return this.__len__$39(var2, var3);
         case 40:
            return this.__getitem__$40(var2, var3);
         case 41:
            return this.keys$41(var2, var3);
         case 42:
            return this.has_key$42(var2, var3);
         case 43:
            return this.get$43(var2, var3);
         case 44:
            return this.copy$44(var2, var3);
         case 45:
            return this.items$45(var2, var3);
         case 46:
            return this.values$46(var2, var3);
         case 47:
            return this.DocumentHandler$47(var2, var3);
         case 48:
            return this.characters$48(var2, var3);
         case 49:
            return this.endDocument$49(var2, var3);
         case 50:
            return this.endElement$50(var2, var3);
         case 51:
            return this.ignorableWhitespace$51(var2, var3);
         case 52:
            return this.processingInstruction$52(var2, var3);
         case 53:
            return this.setDocumentLocator$53(var2, var3);
         case 54:
            return this.startDocument$54(var2, var3);
         case 55:
            return this.startElement$55(var2, var3);
         case 56:
            return this.HandlerBase$56(var2, var3);
         case 57:
            return this.Parser$57(var2, var3);
         case 58:
            return this.__init__$58(var2, var3);
         case 59:
            return this.parse$59(var2, var3);
         case 60:
            return this.parseFile$60(var2, var3);
         case 61:
            return this.setDocumentHandler$61(var2, var3);
         case 62:
            return this.setDTDHandler$62(var2, var3);
         case 63:
            return this.setEntityResolver$63(var2, var3);
         case 64:
            return this.setErrorHandler$64(var2, var3);
         case 65:
            return this.setLocale$65(var2, var3);
         default:
            return null;
      }
   }
}
