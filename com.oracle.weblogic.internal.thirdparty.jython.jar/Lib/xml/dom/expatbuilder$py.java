package xml.dom;

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
@Filename("xml/dom/expatbuilder.py")
public class expatbuilder$py extends PyFunctionTable implements PyRunnable {
   static expatbuilder$py self;
   static final PyCode f$0;
   static final PyCode ElementInfo$1;
   static final PyCode __init__$2;
   static final PyCode __getstate__$3;
   static final PyCode __setstate__$4;
   static final PyCode getAttributeType$5;
   static final PyCode getAttributeTypeNS$6;
   static final PyCode isElementContent$7;
   static final PyCode isEmpty$8;
   static final PyCode isId$9;
   static final PyCode isIdNS$10;
   static final PyCode _intern$11;
   static final PyCode _parse_ns_name$12;
   static final PyCode ExpatBuilder$13;
   static final PyCode __init__$14;
   static final PyCode createParser$15;
   static final PyCode getParser$16;
   static final PyCode reset$17;
   static final PyCode install$18;
   static final PyCode parseFile$19;
   static final PyCode parseString$20;
   static final PyCode _setup_subset$21;
   static final PyCode start_doctype_decl_handler$22;
   static final PyCode end_doctype_decl_handler$23;
   static final PyCode pi_handler$24;
   static final PyCode character_data_handler_cdata$25;
   static final PyCode character_data_handler$26;
   static final PyCode entity_decl_handler$27;
   static final PyCode notation_decl_handler$28;
   static final PyCode comment_handler$29;
   static final PyCode start_cdata_section_handler$30;
   static final PyCode end_cdata_section_handler$31;
   static final PyCode external_entity_ref_handler$32;
   static final PyCode first_element_handler$33;
   static final PyCode start_element_handler$34;
   static final PyCode _finish_start_element$35;
   static final PyCode end_element_handler$36;
   static final PyCode _finish_end_element$37;
   static final PyCode _handle_white_text_nodes$38;
   static final PyCode element_decl_handler$39;
   static final PyCode attlist_decl_handler$40;
   static final PyCode xml_decl_handler$41;
   static final PyCode FilterVisibilityController$42;
   static final PyCode __init__$43;
   static final PyCode startContainer$44;
   static final PyCode acceptNode$45;
   static final PyCode FilterCrutch$46;
   static final PyCode __init__$47;
   static final PyCode Rejecter$48;
   static final PyCode __init__$49;
   static final PyCode start_element_handler$50;
   static final PyCode end_element_handler$51;
   static final PyCode Skipper$52;
   static final PyCode start_element_handler$53;
   static final PyCode end_element_handler$54;
   static final PyCode FragmentBuilder$55;
   static final PyCode __init__$56;
   static final PyCode reset$57;
   static final PyCode parseFile$58;
   static final PyCode parseString$59;
   static final PyCode _getDeclarations$60;
   static final PyCode _getNSattrs$61;
   static final PyCode external_entity_ref_handler$62;
   static final PyCode Namespaces$63;
   static final PyCode _initNamespaces$64;
   static final PyCode createParser$65;
   static final PyCode install$66;
   static final PyCode start_namespace_decl_handler$67;
   static final PyCode start_element_handler$68;
   static final PyCode end_element_handler$69;
   static final PyCode ExpatBuilderNS$70;
   static final PyCode reset$71;
   static final PyCode FragmentBuilderNS$72;
   static final PyCode reset$73;
   static final PyCode _getNSattrs$74;
   static final PyCode ParseEscape$75;
   static final PyCode InternalSubsetExtractor$76;
   static final PyCode getSubset$77;
   static final PyCode parseFile$78;
   static final PyCode parseString$79;
   static final PyCode install$80;
   static final PyCode start_doctype_decl_handler$81;
   static final PyCode end_doctype_decl_handler$82;
   static final PyCode start_element_handler$83;
   static final PyCode parse$84;
   static final PyCode parseString$85;
   static final PyCode parseFragment$86;
   static final PyCode parseFragmentString$87;
   static final PyCode makeBuilder$88;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Facility to use the Expat parser to load a minidom instance\nfrom a string or file.\n\nThis avoids all the overhead of SAX and pulldom to gain performance.\n"));
      var1.setline(5);
      PyString.fromInterned("Facility to use the Expat parser to load a minidom instance\nfrom a string or file.\n\nThis avoids all the overhead of SAX and pulldom to gain performance.\n");
      var1.setline(30);
      String[] var3 = new String[]{"xmlbuilder", "minidom", "Node"};
      PyObject[] var5 = imp.importFrom("xml.dom", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("xmlbuilder", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("minidom", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Node", var4);
      var4 = null;
      var1.setline(31);
      var3 = new String[]{"EMPTY_NAMESPACE", "EMPTY_PREFIX", "XMLNS_NAMESPACE"};
      var5 = imp.importFrom("xml.dom", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("EMPTY_NAMESPACE", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("EMPTY_PREFIX", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("XMLNS_NAMESPACE", var4);
      var4 = null;
      var1.setline(32);
      var3 = new String[]{"expat"};
      var5 = imp.importFrom("xml.parsers", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("expat", var4);
      var4 = null;
      var1.setline(33);
      var3 = new String[]{"_append_child", "_set_attribute_node"};
      var5 = imp.importFrom("xml.dom.minidom", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("_append_child", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("_set_attribute_node", var4);
      var4 = null;
      var1.setline(34);
      var3 = new String[]{"NodeFilter"};
      var5 = imp.importFrom("xml.dom.NodeFilter", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("NodeFilter", var4);
      var4 = null;
      var1.setline(36);
      imp.importAll("xml.dom.minicompat", var1, -1);
      var1.setline(38);
      PyObject var6 = var1.getname("Node").__getattr__("TEXT_NODE");
      var1.setlocal("TEXT_NODE", var6);
      var3 = null;
      var1.setline(39);
      var6 = var1.getname("Node").__getattr__("CDATA_SECTION_NODE");
      var1.setlocal("CDATA_SECTION_NODE", var6);
      var3 = null;
      var1.setline(40);
      var6 = var1.getname("Node").__getattr__("DOCUMENT_NODE");
      var1.setlocal("DOCUMENT_NODE", var6);
      var3 = null;
      var1.setline(42);
      var6 = var1.getname("xmlbuilder").__getattr__("DOMBuilderFilter").__getattr__("FILTER_ACCEPT");
      var1.setlocal("FILTER_ACCEPT", var6);
      var3 = null;
      var1.setline(43);
      var6 = var1.getname("xmlbuilder").__getattr__("DOMBuilderFilter").__getattr__("FILTER_REJECT");
      var1.setlocal("FILTER_REJECT", var6);
      var3 = null;
      var1.setline(44);
      var6 = var1.getname("xmlbuilder").__getattr__("DOMBuilderFilter").__getattr__("FILTER_SKIP");
      var1.setlocal("FILTER_SKIP", var6);
      var3 = null;
      var1.setline(45);
      var6 = var1.getname("xmlbuilder").__getattr__("DOMBuilderFilter").__getattr__("FILTER_INTERRUPT");
      var1.setlocal("FILTER_INTERRUPT", var6);
      var3 = null;
      var1.setline(47);
      var6 = var1.getname("minidom").__getattr__("getDOMImplementation").__call__(var2);
      var1.setlocal("theDOMImplementation", var6);
      var3 = null;
      var1.setline(50);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("CDATA"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("cdata")), PyString.fromInterned("ENUM"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("enumeration")), PyString.fromInterned("ENTITY"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("entity")), PyString.fromInterned("ENTITIES"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("entities")), PyString.fromInterned("ID"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("id")), PyString.fromInterned("IDREF"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("idref")), PyString.fromInterned("IDREFS"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("idrefs")), PyString.fromInterned("NMTOKEN"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("nmtoken")), PyString.fromInterned("NMTOKENS"), var1.getname("minidom").__getattr__("TypeInfo").__call__((ThreadState)var2, (PyObject)var1.getname("None"), (PyObject)PyString.fromInterned("nmtokens"))});
      var1.setlocal("_typeinfo_map", var7);
      var3 = null;
      var1.setline(62);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ElementInfo", var5, ElementInfo$1);
      var1.setlocal("ElementInfo", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(113);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, _intern$11, (PyObject)null);
      var1.setlocal("_intern", var8);
      var3 = null;
      var1.setline(116);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _parse_ns_name$12, (PyObject)null);
      var1.setlocal("_parse_ns_name", var8);
      var3 = null;
      var1.setline(133);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("ExpatBuilder", var5, ExpatBuilder$13);
      var1.setlocal("ExpatBuilder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(461);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getname("FILTER_ACCEPT"), var1.getname("FILTER_REJECT"), var1.getname("FILTER_SKIP")});
      var1.setlocal("_ALLOWED_FILTER_RETURNS", var9);
      var3 = null;
      var1.setline(463);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FilterVisibilityController", var5, FilterVisibilityController$42);
      var1.setlocal("FilterVisibilityController", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(521);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FilterCrutch", var5, FilterCrutch$46);
      var1.setlocal("FilterCrutch", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(533);
      var5 = new PyObject[]{var1.getname("FilterCrutch")};
      var4 = Py.makeClass("Rejecter", var5, Rejecter$48);
      var1.setlocal("Rejecter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(561);
      var5 = new PyObject[]{var1.getname("FilterCrutch")};
      var4 = Py.makeClass("Skipper", var5, Skipper$52);
      var1.setlocal("Skipper", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(585);
      PyString var10 = PyString.fromInterned("http://xml.python.org/entities/fragment-builder/internal");
      var1.setlocal("_FRAGMENT_BUILDER_INTERNAL_SYSTEM_ID", var10);
      var3 = null;
      var1.setline(588);
      var6 = PyString.fromInterned("<!DOCTYPE wrapper\n  %%s [\n  <!ENTITY fragment-builder-internal\n    SYSTEM \"%s\">\n%%s\n]>\n<wrapper %%s\n>&fragment-builder-internal;</wrapper>")._mod(var1.getname("_FRAGMENT_BUILDER_INTERNAL_SYSTEM_ID"));
      var1.setlocal("_FRAGMENT_BUILDER_TEMPLATE", var6);
      var3 = null;
      var1.setline(601);
      var5 = new PyObject[]{var1.getname("ExpatBuilder")};
      var4 = Py.makeClass("FragmentBuilder", var5, FragmentBuilder$55);
      var1.setlocal("FragmentBuilder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(718);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Namespaces", var5, Namespaces$63);
      var1.setlocal("Namespaces", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(818);
      var5 = new PyObject[]{var1.getname("Namespaces"), var1.getname("ExpatBuilder")};
      var4 = Py.makeClass("ExpatBuilderNS", var5, ExpatBuilderNS$70);
      var1.setlocal("ExpatBuilderNS", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(826);
      var5 = new PyObject[]{var1.getname("Namespaces"), var1.getname("FragmentBuilder")};
      var4 = Py.makeClass("FragmentBuilderNS", var5, FragmentBuilderNS$72);
      var1.setlocal("FragmentBuilderNS", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(863);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("ParseEscape", var5, ParseEscape$75);
      var1.setlocal("ParseEscape", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(867);
      var5 = new PyObject[]{var1.getname("ExpatBuilder")};
      var4 = Py.makeClass("InternalSubsetExtractor", var5, InternalSubsetExtractor$76);
      var1.setlocal("InternalSubsetExtractor", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(911);
      var5 = new PyObject[]{var1.getname("True")};
      var8 = new PyFunction(var1.f_globals, var5, parse$84, PyString.fromInterned("Parse a document, returning the resulting Document node.\n\n    'file' may be either a file name or an open file object.\n    "));
      var1.setlocal("parse", var8);
      var3 = null;
      var1.setline(932);
      var5 = new PyObject[]{var1.getname("True")};
      var8 = new PyFunction(var1.f_globals, var5, parseString$85, PyString.fromInterned("Parse a document from a string, returning the resulting\n    Document node.\n    "));
      var1.setlocal("parseString", var8);
      var3 = null;
      var1.setline(943);
      var5 = new PyObject[]{var1.getname("True")};
      var8 = new PyFunction(var1.f_globals, var5, parseFragment$86, PyString.fromInterned("Parse a fragment of a document, given the context from which it\n    was originally extracted.  context should be the parent of the\n    node(s) which are in the fragment.\n\n    'file' may be either a file name or an open file object.\n    "));
      var1.setlocal("parseFragment", var8);
      var3 = null;
      var1.setline(966);
      var5 = new PyObject[]{var1.getname("True")};
      var8 = new PyFunction(var1.f_globals, var5, parseFragmentString$87, PyString.fromInterned("Parse a fragment of a document from a string, given the context\n    from which it was originally extracted.  context should be the\n    parent of the node(s) which are in the fragment.\n    "));
      var1.setlocal("parseFragmentString", var8);
      var3 = null;
      var1.setline(978);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, makeBuilder$88, PyString.fromInterned("Create a builder based on an Options object."));
      var1.setlocal("makeBuilder", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ElementInfo$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(63);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_attr_info"), PyString.fromInterned("_model"), PyString.fromInterned("tagName")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(65);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(70);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$3, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(73);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$4, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      var1.setline(76);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getAttributeType$5, (PyObject)null);
      var1.setlocal("getAttributeType", var5);
      var3 = null;
      var1.setline(86);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getAttributeTypeNS$6, (PyObject)null);
      var1.setlocal("getAttributeTypeNS", var5);
      var3 = null;
      var1.setline(89);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isElementContent$7, (PyObject)null);
      var1.setlocal("isElementContent", var5);
      var3 = null;
      var1.setline(97);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isEmpty$8, (PyObject)null);
      var1.setlocal("isEmpty", var5);
      var3 = null;
      var1.setline(103);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isId$9, (PyObject)null);
      var1.setlocal("isId", var5);
      var3 = null;
      var1.setline(109);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isIdNS$10, (PyObject)null);
      var1.setlocal("isIdNS", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tagName", var3);
      var3 = null;
      var1.setline(67);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_attr_info", var4);
      var3 = null;
      var1.setline(68);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_model", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$3(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_attr_info"), var1.getlocal(0).__getattr__("_model"), var1.getlocal(0).__getattr__("tagName")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$4(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("_attr_info", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_model", var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("tagName", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getAttributeType$5(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(0).__getattr__("_attr_info").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(77);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(84);
            var5 = var1.getglobal("minidom").__getattr__("_no_type");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(78);
         var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(79);
      var5 = var1.getlocal(2).__getitem__(Py.newInteger(-2));
      var1.setlocal(3, var5);
      var5 = null;
      var1.setline(80);
      var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
      var10000 = var5._eq(PyString.fromInterned("("));
      var5 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         var5 = var1.getglobal("_typeinfo_map").__getitem__(PyString.fromInterned("ENUM"));
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(83);
         var5 = var1.getglobal("_typeinfo_map").__getitem__(var1.getlocal(2).__getitem__(Py.newInteger(-2)));
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject getAttributeTypeNS$6(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getglobal("minidom").__getattr__("_no_type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isElementContent$7(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_model").__nonzero__()) {
         var1.setline(91);
         var3 = var1.getlocal(0).__getattr__("_model").__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(92);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("expat").__getattr__("model").__getattr__("XML_CTYPE_ANY"), var1.getglobal("expat").__getattr__("model").__getattr__("XML_CTYPE_MIXED")}));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(95);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject isEmpty$8(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_model").__nonzero__()) {
         var1.setline(99);
         var3 = var1.getlocal(0).__getattr__("_model").__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(var1.getglobal("expat").__getattr__("model").__getattr__("XML_CTYPE_EMPTY"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(101);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject isId$9(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyObject var3 = var1.getlocal(0).__getattr__("_attr_info").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(104);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(107);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(105);
         var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(106);
      var5 = var1.getlocal(2).__getitem__(Py.newInteger(-2));
      var10000 = var5._eq(PyString.fromInterned("ID"));
      var5 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject isIdNS$10(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getlocal(0).__getattr__("isId").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _intern$11(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = var1.getlocal(0).__getattr__("_intern_setdefault").__call__(var2, var1.getlocal(1), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _parse_ns_name$12(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var10000;
      PyString var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = PyString.fromInterned(" ");
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(118);
      PyObject var6 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(119);
      var6 = var1.getlocal(0).__getattr__("_intern_setdefault");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(120);
      var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var10000 = var6._eq(Py.newInteger(3));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(121);
         var6 = var1.getlocal(2);
         var4 = Py.unpackSequence(var6, 3);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(122);
         var6 = var1.getlocal(3).__call__(var2, var1.getlocal(6), var1.getlocal(6));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(123);
         var6 = PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)}));
         var1.setlocal(7, var6);
         var3 = null;
         var1.setline(124);
         var6 = var1.getlocal(3).__call__(var2, var1.getlocal(7), var1.getlocal(7));
         var1.setlocal(7, var6);
         var3 = null;
         var1.setline(125);
         var6 = var1.getlocal(3).__call__(var2, var1.getlocal(5), var1.getlocal(5));
         var1.setlocal(5, var6);
         var3 = null;
      } else {
         var1.setline(127);
         var6 = var1.getlocal(2);
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(128);
         var6 = var1.getglobal("EMPTY_PREFIX");
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(129);
         var6 = var1.getlocal(3).__call__(var2, var1.getlocal(5), var1.getlocal(5));
         var1.setlocal(7, var6);
         var1.setlocal(5, var6);
      }

      var1.setline(130);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3).__call__(var2, var1.getlocal(4), var1.getlocal(4)), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject ExpatBuilder$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Document builder that uses Expat to build a ParsedXML.DOM document\n    instance."));
      var1.setline(135);
      PyString.fromInterned("Document builder that uses Expat to build a ParsedXML.DOM document\n    instance.");
      var1.setline(137);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(151);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, createParser$15, PyString.fromInterned("Create a new parser object."));
      var1.setlocal("createParser", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getParser$16, PyString.fromInterned("Return the parser object, creating a new one if needed."));
      var1.setlocal("getParser", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$17, PyString.fromInterned("Free all data structures used during DOM construction."));
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(174);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, install$18, PyString.fromInterned("Install the callbacks needed to build the DOM into the parser."));
      var1.setlocal("install", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseFile$19, PyString.fromInterned("Parse a document from a file object, returning the document\n        node."));
      var1.setlocal("parseFile", var4);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseString$20, PyString.fromInterned("Parse a document from a string, returning the document node."));
      var1.setlocal("parseString", var4);
      var3 = null;
      var1.setline(232);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _setup_subset$21, PyString.fromInterned("Load the internal subset if there might be one."));
      var1.setlocal("_setup_subset", var4);
      var3 = null;
      var1.setline(240);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start_doctype_decl_handler$22, (PyObject)null);
      var1.setlocal("start_doctype_decl_handler", var4);
      var3 = null;
      var1.setline(261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_doctype_decl_handler$23, (PyObject)null);
      var1.setlocal("end_doctype_decl_handler", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pi_handler$24, (PyObject)null);
      var1.setlocal("pi_handler", var4);
      var3 = null;
      var1.setline(274);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, character_data_handler_cdata$25, (PyObject)null);
      var1.setlocal("character_data_handler_cdata", var4);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, character_data_handler$26, (PyObject)null);
      var1.setlocal("character_data_handler", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, entity_decl_handler$27, (PyObject)null);
      var1.setlocal("entity_decl_handler", var4);
      var3 = null;
      var1.setline(327);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, notation_decl_handler$28, (PyObject)null);
      var1.setlocal("notation_decl_handler", var4);
      var3 = null;
      var1.setline(333);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, comment_handler$29, (PyObject)null);
      var1.setlocal("comment_handler", var4);
      var3 = null;
      var1.setline(339);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start_cdata_section_handler$30, (PyObject)null);
      var1.setlocal("start_cdata_section_handler", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_cdata_section_handler$31, (PyObject)null);
      var1.setlocal("end_cdata_section_handler", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, external_entity_ref_handler$32, (PyObject)null);
      var1.setlocal("external_entity_ref_handler", var4);
      var3 = null;
      var1.setline(350);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, first_element_handler$33, (PyObject)null);
      var1.setlocal("first_element_handler", var4);
      var3 = null;
      var1.setline(356);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start_element_handler$34, (PyObject)null);
      var1.setlocal("start_element_handler", var4);
      var3 = null;
      var1.setline(376);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _finish_start_element$35, (PyObject)null);
      var1.setlocal("_finish_start_element", var4);
      var3 = null;
      var1.setline(399);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_element_handler$36, (PyObject)null);
      var1.setlocal("end_element_handler", var4);
      var3 = null;
      var1.setline(404);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _finish_end_element$37, (PyObject)null);
      var1.setlocal("_finish_end_element", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_white_text_nodes$38, (PyObject)null);
      var1.setlocal("_handle_white_text_nodes", var4);
      var3 = null;
      var1.setline(432);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, element_decl_handler$39, (PyObject)null);
      var1.setlocal("element_decl_handler", var4);
      var3 = null;
      var1.setline(440);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, attlist_decl_handler$40, (PyObject)null);
      var1.setlocal("attlist_decl_handler", var4);
      var3 = null;
      var1.setline(448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, xml_decl_handler$41, (PyObject)null);
      var1.setlocal("xml_decl_handler", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(139);
         var3 = var1.getglobal("xmlbuilder").__getattr__("Options").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(140);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_options", var3);
      var3 = null;
      var1.setline(141);
      var3 = var1.getlocal(0).__getattr__("_options").__getattr__("filter");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(142);
         var3 = var1.getglobal("FilterVisibilityController").__call__(var2, var1.getlocal(0).__getattr__("_options").__getattr__("filter"));
         var1.getlocal(0).__setattr__("_filter", var3);
         var3 = null;
      } else {
         var1.setline(144);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_filter", var3);
         var3 = null;
         var1.setline(147);
         var3 = var1.getglobal("id");
         var1.getlocal(0).__setattr__("_finish_start_element", var3);
         var3 = null;
      }

      var1.setline(148);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_parser", var3);
      var3 = null;
      var1.setline(149);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject createParser$15(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("Create a new parser object.");
      var1.setline(153);
      PyObject var3 = var1.getglobal("expat").__getattr__("ParserCreate").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getParser$16(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyString.fromInterned("Return the parser object, creating a new one if needed.");
      var1.setline(157);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_parser").__not__().__nonzero__()) {
         var1.setline(158);
         var3 = var1.getlocal(0).__getattr__("createParser").__call__(var2);
         var1.getlocal(0).__setattr__("_parser", var3);
         var3 = null;
         var1.setline(159);
         var3 = var1.getlocal(0).__getattr__("_parser").__getattr__("intern").__getattr__("setdefault");
         var1.getlocal(0).__setattr__("_intern_setdefault", var3);
         var3 = null;
         var1.setline(160);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__getattr__("_parser").__setattr__("buffer_text", var3);
         var3 = null;
         var1.setline(161);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__getattr__("_parser").__setattr__("ordered_attributes", var3);
         var3 = null;
         var1.setline(162);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__getattr__("_parser").__setattr__("specified_attributes", var3);
         var3 = null;
         var1.setline(163);
         var1.getlocal(0).__getattr__("install").__call__(var2, var1.getlocal(0).__getattr__("_parser"));
      }

      var1.setline(164);
      var3 = var1.getlocal(0).__getattr__("_parser");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$17(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyString.fromInterned("Free all data structures used during DOM construction.");
      var1.setline(168);
      PyObject var3 = var1.getglobal("theDOMImplementation").__getattr__("createDocument").__call__(var2, var1.getglobal("EMPTY_NAMESPACE"), var1.getglobal("None"), var1.getglobal("None"));
      var1.getlocal(0).__setattr__("document", var3);
      var3 = null;
      var1.setline(170);
      var3 = var1.getlocal(0).__getattr__("document");
      var1.getlocal(0).__setattr__("curNode", var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getlocal(0).__getattr__("document").__getattr__("_elem_info");
      var1.getlocal(0).__setattr__("_elem_info", var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_cdata", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install$18(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyString.fromInterned("Install the callbacks needed to build the DOM into the parser.");
      var1.setline(177);
      PyObject var3 = var1.getlocal(0).__getattr__("start_doctype_decl_handler");
      var1.getlocal(1).__setattr__("StartDoctypeDeclHandler", var3);
      var3 = null;
      var1.setline(178);
      var3 = var1.getlocal(0).__getattr__("first_element_handler");
      var1.getlocal(1).__setattr__("StartElementHandler", var3);
      var3 = null;
      var1.setline(179);
      var3 = var1.getlocal(0).__getattr__("end_element_handler");
      var1.getlocal(1).__setattr__("EndElementHandler", var3);
      var3 = null;
      var1.setline(180);
      var3 = var1.getlocal(0).__getattr__("pi_handler");
      var1.getlocal(1).__setattr__("ProcessingInstructionHandler", var3);
      var3 = null;
      var1.setline(181);
      if (var1.getlocal(0).__getattr__("_options").__getattr__("entities").__nonzero__()) {
         var1.setline(182);
         var3 = var1.getlocal(0).__getattr__("entity_decl_handler");
         var1.getlocal(1).__setattr__("EntityDeclHandler", var3);
         var3 = null;
      }

      var1.setline(183);
      var3 = var1.getlocal(0).__getattr__("notation_decl_handler");
      var1.getlocal(1).__setattr__("NotationDeclHandler", var3);
      var3 = null;
      var1.setline(184);
      if (var1.getlocal(0).__getattr__("_options").__getattr__("comments").__nonzero__()) {
         var1.setline(185);
         var3 = var1.getlocal(0).__getattr__("comment_handler");
         var1.getlocal(1).__setattr__("CommentHandler", var3);
         var3 = null;
      }

      var1.setline(186);
      if (var1.getlocal(0).__getattr__("_options").__getattr__("cdata_sections").__nonzero__()) {
         var1.setline(187);
         var3 = var1.getlocal(0).__getattr__("start_cdata_section_handler");
         var1.getlocal(1).__setattr__("StartCdataSectionHandler", var3);
         var3 = null;
         var1.setline(188);
         var3 = var1.getlocal(0).__getattr__("end_cdata_section_handler");
         var1.getlocal(1).__setattr__("EndCdataSectionHandler", var3);
         var3 = null;
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("character_data_handler_cdata");
         var1.getlocal(1).__setattr__("CharacterDataHandler", var3);
         var3 = null;
      } else {
         var1.setline(191);
         var3 = var1.getlocal(0).__getattr__("character_data_handler");
         var1.getlocal(1).__setattr__("CharacterDataHandler", var3);
         var3 = null;
      }

      var1.setline(192);
      var3 = var1.getlocal(0).__getattr__("external_entity_ref_handler");
      var1.getlocal(1).__setattr__("ExternalEntityRefHandler", var3);
      var3 = null;
      var1.setline(193);
      var3 = var1.getlocal(0).__getattr__("xml_decl_handler");
      var1.getlocal(1).__setattr__("XmlDeclHandler", var3);
      var3 = null;
      var1.setline(194);
      var3 = var1.getlocal(0).__getattr__("element_decl_handler");
      var1.getlocal(1).__setattr__("ElementDeclHandler", var3);
      var3 = null;
      var1.setline(195);
      var3 = var1.getlocal(0).__getattr__("attlist_decl_handler");
      var1.getlocal(1).__setattr__("AttlistDeclHandler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseFile$19(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyString.fromInterned("Parse a document from a file object, returning the document\n        node.");
      var1.setline(200);
      PyObject var3 = var1.getlocal(0).__getattr__("getParser").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(201);
      var3 = var1.getglobal("True");
      var1.setlocal(3, var3);
      var3 = null;

      try {
         while(true) {
            var1.setline(203);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(204);
            var3 = var1.getlocal(1).__getattr__("read").__call__(var2, Py.newInteger(16)._mul(Py.newInteger(1024)));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(205);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               break;
            }

            var1.setline(207);
            var1.getlocal(2).__getattr__("Parse").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
            var1.setline(208);
            PyObject var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("document").__getattr__("documentElement");
            }

            if (var10000.__nonzero__()) {
               var1.setline(209);
               var1.getlocal(0).__getattr__("_setup_subset").__call__(var2, var1.getlocal(4));
            }

            var1.setline(210);
            var3 = var1.getglobal("False");
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(211);
         var1.getlocal(2).__getattr__("Parse").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getglobal("True"));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("ParseEscape"))) {
            throw var5;
         }

         var1.setline(213);
      }

      var1.setline(214);
      var3 = var1.getlocal(0).__getattr__("document");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(215);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.setline(216);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_parser", var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseString$20(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyString.fromInterned("Parse a document from a string, returning the document node.");
      var1.setline(221);
      PyObject var3 = var1.getlocal(0).__getattr__("getParser").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(223);
         var1.getlocal(2).__getattr__("Parse").__call__(var2, var1.getlocal(1), var1.getglobal("True"));
         var1.setline(224);
         var1.getlocal(0).__getattr__("_setup_subset").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("ParseEscape"))) {
            throw var5;
         }

         var1.setline(226);
      }

      var1.setline(227);
      var3 = var1.getlocal(0).__getattr__("document");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(228);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.setline(229);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_parser", var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _setup_subset$21(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyString.fromInterned("Load the internal subset if there might be one.");
      var1.setline(234);
      if (var1.getlocal(0).__getattr__("document").__getattr__("doctype").__nonzero__()) {
         var1.setline(235);
         PyObject var3 = var1.getglobal("InternalSubsetExtractor").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(236);
         var1.getlocal(2).__getattr__("parseString").__call__(var2, var1.getlocal(1));
         var1.setline(237);
         var3 = var1.getlocal(2).__getattr__("getSubset").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(238);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__getattr__("document").__getattr__("doctype").__setattr__("internalSubset", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_doctype_decl_handler$22(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("implementation").__getattr__("createDocumentType").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(244);
      var3 = var1.getlocal(0).__getattr__("document");
      var1.getlocal(5).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(245);
      var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("document"), var1.getlocal(5));
      var1.setline(246);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__getattr__("document").__setattr__("doctype", var3);
      var3 = null;
      var1.setline(247);
      PyObject var10000 = var1.getlocal(0).__getattr__("_filter");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(5));
         var10000 = var3._eq(var1.getglobal("FILTER_REJECT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(248);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("document").__setattr__("doctype", var3);
         var3 = null;
         var1.setline(249);
         var1.getlocal(0).__getattr__("document").__getattr__("childNodes").__delitem__((PyObject)Py.newInteger(-1));
         var1.setline(250);
         var3 = var1.getglobal("None");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(251);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("_parser").__setattr__("EntityDeclHandler", var3);
         var3 = null;
         var1.setline(252);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("_parser").__setattr__("NotationDeclHandler", var3);
         var3 = null;
      }

      var1.setline(253);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(254);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(255);
            PyList var4 = new PyList(Py.EmptyObjects);
            var1.getlocal(5).__getattr__("entities").__setattr__((String)"_seq", var4);
            var3 = null;
            var1.setline(256);
            var4 = new PyList(Py.EmptyObjects);
            var1.getlocal(5).__getattr__("notations").__setattr__((String)"_seq", var4);
            var3 = null;
         }

         var1.setline(257);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("_parser").__setattr__("CommentHandler", var3);
         var3 = null;
         var1.setline(258);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("_parser").__setattr__("ProcessingInstructionHandler", var3);
         var3 = null;
         var1.setline(259);
         var3 = var1.getlocal(0).__getattr__("end_doctype_decl_handler");
         var1.getlocal(0).__getattr__("_parser").__setattr__("EndDoctypeDeclHandler", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_doctype_decl_handler$23(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_options").__getattr__("comments").__nonzero__()) {
         var1.setline(263);
         var3 = var1.getlocal(0).__getattr__("comment_handler");
         var1.getlocal(0).__getattr__("_parser").__setattr__("CommentHandler", var3);
         var3 = null;
      }

      var1.setline(264);
      var3 = var1.getlocal(0).__getattr__("pi_handler");
      var1.getlocal(0).__getattr__("_parser").__setattr__("ProcessingInstructionHandler", var3);
      var3 = null;
      var1.setline(265);
      PyObject var10000 = var1.getlocal(0).__getattr__("_elem_info");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_filter");
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(266);
         var3 = var1.getglobal("id");
         var1.getlocal(0).__setattr__("_finish_end_element", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pi_handler$24(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("createProcessingInstruction").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(270);
      var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("curNode"), var1.getlocal(3));
      var1.setline(271);
      PyObject var10000 = var1.getlocal(0).__getattr__("_filter");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(3));
         var10000 = var3._eq(var1.getglobal("FILTER_REJECT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(272);
         var1.getlocal(0).__getattr__("curNode").__getattr__("removeChild").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject character_data_handler_cdata$25(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyObject var3 = var1.getlocal(0).__getattr__("curNode").__getattr__("childNodes");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(276);
      PyObject var10000;
      if (var1.getlocal(0).__getattr__("_cdata").__nonzero__()) {
         var1.setline(277);
         var10000 = var1.getlocal(0).__getattr__("_cdata_continue");
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getattr__("nodeType");
            var10000 = var3._eq(var1.getglobal("CDATA_SECTION_NODE"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(279);
            var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getattr__("appendData").__call__(var2, var1.getlocal(1));
            var1.setline(280);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("document").__getattr__("createCDATASection").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(282);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_cdata_continue", var3);
         var3 = null;
      } else {
         var1.setline(283);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getattr__("nodeType");
            var10000 = var3._eq(var1.getglobal("TEXT_NODE"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(284);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(285);
            var3 = var1.getlocal(3).__getattr__("data")._add(var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(286);
            var3 = var1.getlocal(3).__getattr__("__dict__");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(287);
            var3 = var1.getlocal(4);
            var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("data"), var3);
            var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
            var1.setline(288);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(290);
         var3 = var1.getglobal("minidom").__getattr__("Text").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(291);
         var3 = var1.getlocal(3).__getattr__("__dict__");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(292);
         var3 = var1.getlocal(1);
         var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(293);
         var3 = var1.getlocal(0).__getattr__("document");
         var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var3);
         var3 = null;
      }

      var1.setline(294);
      var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("curNode"), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject character_data_handler$26(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyObject var3 = var1.getlocal(0).__getattr__("curNode").__getattr__("childNodes");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(298);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("TEXT_NODE"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(299);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(300);
         var3 = var1.getlocal(3).__getattr__("__dict__");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(301);
         var3 = var1.getlocal(3).__getattr__("data")._add(var1.getlocal(1));
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(302);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(303);
         var3 = var1.getglobal("minidom").__getattr__("Text").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(304);
         var3 = var1.getlocal(3).__getattr__("__dict__");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(305);
         var3 = var1.getlocal(3).__getattr__("data")._add(var1.getlocal(1));
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(306);
         var3 = var1.getlocal(0).__getattr__("document");
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var3);
         var3 = null;
         var1.setline(307);
         var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("curNode"), var1.getlocal(3));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject entity_decl_handler$27(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(313);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(314);
         if (var1.getlocal(0).__getattr__("_options").__getattr__("entities").__not__().__nonzero__()) {
            var1.setline(315);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(316);
            PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("_create_entity").__call__(var2, var1.getlocal(1), var1.getlocal(6), var1.getlocal(5), var1.getlocal(7));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(318);
            var3 = var1.getlocal(3);
            PyObject var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(321);
               var3 = var1.getlocal(0).__getattr__("document").__getattr__("createTextNode").__call__(var2, var1.getlocal(3));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(322);
               var1.getlocal(8).__getattr__("childNodes").__getattr__("append").__call__(var2, var1.getlocal(9));
            }

            var1.setline(323);
            var1.getlocal(0).__getattr__("document").__getattr__("doctype").__getattr__("entities").__getattr__("_seq").__getattr__("append").__call__(var2, var1.getlocal(8));
            var1.setline(324);
            var10000 = var1.getlocal(0).__getattr__("_filter");
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(8));
               var10000 = var3._eq(var1.getglobal("FILTER_REJECT"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(325);
               var1.getlocal(0).__getattr__("document").__getattr__("doctype").__getattr__("entities").__getattr__("_seq").__delitem__((PyObject)Py.newInteger(-1));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject notation_decl_handler$28(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("_create_notation").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(329);
      var1.getlocal(0).__getattr__("document").__getattr__("doctype").__getattr__("notations").__getattr__("_seq").__getattr__("append").__call__(var2, var1.getlocal(5));
      var1.setline(330);
      PyObject var10000 = var1.getlocal(0).__getattr__("_filter");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(5));
         var10000 = var3._eq(var1.getglobal("FILTER_ACCEPT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(331);
         var1.getlocal(0).__getattr__("document").__getattr__("doctype").__getattr__("notations").__getattr__("_seq").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject comment_handler$29(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("createComment").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(335);
      var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("curNode"), var1.getlocal(2));
      var1.setline(336);
      PyObject var10000 = var1.getlocal(0).__getattr__("_filter");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(var1.getglobal("FILTER_REJECT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(337);
         var1.getlocal(0).__getattr__("curNode").__getattr__("removeChild").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_cdata_section_handler$30(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_cdata", var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_cdata_continue", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_cdata_section_handler$31(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_cdata", var3);
      var3 = null;
      var1.setline(345);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_cdata_continue", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject external_entity_ref_handler$32(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject first_element_handler$33(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      PyObject var3 = var1.getlocal(0).__getattr__("_filter");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_elem_info").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(352);
         var3 = var1.getglobal("id");
         var1.getlocal(0).__setattr__("_finish_end_element", var3);
         var3 = null;
      }

      var1.setline(353);
      var3 = var1.getlocal(0).__getattr__("start_element_handler");
      var1.getlocal(0).__getattr__("getParser").__call__(var2).__setattr__("StartElementHandler", var3);
      var3 = null;
      var1.setline(354);
      var1.getlocal(0).__getattr__("start_element_handler").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_element_handler$34(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("createElement").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(358);
      var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("curNode"), var1.getlocal(3));
      var1.setline(359);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("curNode", var3);
      var3 = null;
      var1.setline(361);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(362);
         var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(362);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(363);
            PyObject var5 = var1.getglobal("minidom").__getattr__("Attr").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(4)), var1.getglobal("EMPTY_NAMESPACE"), var1.getglobal("None"), var1.getglobal("EMPTY_PREFIX"));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(365);
            var5 = var1.getlocal(2).__getitem__(var1.getlocal(4)._add(Py.newInteger(1)));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(366);
            var5 = var1.getlocal(5).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__getattr__("__dict__");
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(367);
            var5 = var1.getlocal(6);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("data"), var5);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var5);
            var1.setline(368);
            var5 = var1.getlocal(5).__getattr__("__dict__");
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(369);
            var5 = var1.getlocal(6);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("value"), var5);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var5);
            var1.setline(370);
            var5 = var1.getlocal(0).__getattr__("document");
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var5);
            var5 = null;
            var1.setline(371);
            var1.getglobal("_set_attribute_node").__call__(var2, var1.getlocal(3), var1.getlocal(5));
         }
      }

      var1.setline(373);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getlocal(0).__getattr__("document").__getattr__("documentElement"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(374);
         var1.getlocal(0).__getattr__("_finish_start_element").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _finish_start_element$35(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      if (var1.getlocal(0).__getattr__("_filter").__nonzero__()) {
         var1.setline(380);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getlocal(0).__getattr__("document").__getattr__("documentElement"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(381);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(382);
         var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("startContainer").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(383);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("FILTER_REJECT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(385);
            var1.getglobal("Rejecter").__call__(var2, var1.getlocal(0));
         } else {
            var1.setline(386);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("FILTER_SKIP"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(391);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(389);
            var1.getglobal("Skipper").__call__(var2, var1.getlocal(0));
         }

         var1.setline(392);
         var3 = var1.getlocal(1).__getattr__("parentNode");
         var1.getlocal(0).__setattr__("curNode", var3);
         var3 = null;
         var1.setline(393);
         var1.getlocal(1).__getattr__("parentNode").__getattr__("removeChild").__call__(var2, var1.getlocal(1));
         var1.setline(394);
         var1.getlocal(1).__getattr__("unlink").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_element_handler$36(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyObject var3 = var1.getlocal(0).__getattr__("curNode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(401);
      var3 = var1.getlocal(2).__getattr__("parentNode");
      var1.getlocal(0).__setattr__("curNode", var3);
      var3 = null;
      var1.setline(402);
      var1.getlocal(0).__getattr__("_finish_end_element").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _finish_end_element$37(PyFrame var1, ThreadState var2) {
      var1.setline(405);
      PyObject var3 = var1.getlocal(0).__getattr__("_elem_info").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("tagName"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(406);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(407);
         var1.getlocal(0).__getattr__("_handle_white_text_nodes").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      var1.setline(408);
      if (var1.getlocal(0).__getattr__("_filter").__nonzero__()) {
         var1.setline(409);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getlocal(0).__getattr__("document").__getattr__("documentElement"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(410);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(411);
         var3 = var1.getlocal(0).__getattr__("_filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(var1.getglobal("FILTER_REJECT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(412);
            var1.getlocal(0).__getattr__("curNode").__getattr__("removeChild").__call__(var2, var1.getlocal(1));
            var1.setline(413);
            var1.getlocal(1).__getattr__("unlink").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _handle_white_text_nodes$38(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var10000 = var1.getlocal(0).__getattr__("_options").__getattr__("whitespace_in_element_content");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__getattr__("isElementContent").__call__(var2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(418);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(423);
         PyList var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(424);
         PyObject var6 = var1.getlocal(1).__getattr__("childNodes").__iter__();

         while(true) {
            var1.setline(424);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(429);
               var6 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(429);
                  var4 = var6.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(430);
                  var1.getlocal(1).__getattr__("removeChild").__call__(var2, var1.getlocal(4));
               }
            }

            var1.setlocal(4, var4);
            var1.setline(425);
            PyObject var5 = var1.getlocal(4).__getattr__("nodeType");
            var10000 = var5._eq(var1.getglobal("TEXT_NODE"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4).__getattr__("data").__getattr__("strip").__call__(var2).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(426);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public PyObject element_decl_handler$39(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      PyObject var3 = var1.getlocal(0).__getattr__("_elem_info").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(434);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(435);
         var3 = var1.getglobal("ElementInfo").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.getlocal(0).__getattr__("_elem_info").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      } else {
         var1.setline(437);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(3).__getattr__("_model");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(438);
         var3 = var1.getlocal(2);
         var1.getlocal(3).__setattr__("_model", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject attlist_decl_handler$40(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyObject var3 = var1.getlocal(0).__getattr__("_elem_info").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(442);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(443);
         var3 = var1.getglobal("ElementInfo").__call__(var2, var1.getlocal(1));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(444);
         var3 = var1.getlocal(6);
         var1.getlocal(0).__getattr__("_elem_info").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(445);
      var1.getlocal(6).__getattr__("_attr_info").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), var1.getlocal(2), var1.getglobal("None"), var1.getglobal("None"), var1.getlocal(4), Py.newInteger(0), var1.getlocal(3), var1.getlocal(5)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject xml_decl_handler$41(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("document").__setattr__("version", var3);
      var3 = null;
      var1.setline(450);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("document").__setattr__("encoding", var3);
      var3 = null;
      var1.setline(452);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(453);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(454);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__getattr__("document").__setattr__("standalone", var3);
            var3 = null;
         } else {
            var1.setline(456);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__getattr__("document").__setattr__("standalone", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FilterVisibilityController$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Wrapper around a DOMBuilderFilter which implements the checks\n    to make the whatToShow filter attribute work."));
      var1.setline(465);
      PyString.fromInterned("Wrapper around a DOMBuilderFilter which implements the checks\n    to make the whatToShow filter attribute work.");
      var1.setline(467);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("filter")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(469);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$43, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(472);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startContainer$44, (PyObject)null);
      var1.setlocal("startContainer", var5);
      var3 = null;
      var1.setline(485);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, acceptNode$45, (PyObject)null);
      var1.setlocal("acceptNode", var5);
      var3 = null;
      var1.setline(505);
      PyDictionary var6 = new PyDictionary(new PyObject[]{var1.getname("Node").__getattr__("ELEMENT_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_ELEMENT"), var1.getname("Node").__getattr__("ATTRIBUTE_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_ATTRIBUTE"), var1.getname("Node").__getattr__("TEXT_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_TEXT"), var1.getname("Node").__getattr__("CDATA_SECTION_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_CDATA_SECTION"), var1.getname("Node").__getattr__("ENTITY_REFERENCE_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_ENTITY_REFERENCE"), var1.getname("Node").__getattr__("ENTITY_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_ENTITY"), var1.getname("Node").__getattr__("PROCESSING_INSTRUCTION_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_PROCESSING_INSTRUCTION"), var1.getname("Node").__getattr__("COMMENT_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_COMMENT"), var1.getname("Node").__getattr__("DOCUMENT_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_DOCUMENT"), var1.getname("Node").__getattr__("DOCUMENT_TYPE_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_DOCUMENT_TYPE"), var1.getname("Node").__getattr__("DOCUMENT_FRAGMENT_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_DOCUMENT_FRAGMENT"), var1.getname("Node").__getattr__("NOTATION_NODE"), var1.getname("NodeFilter").__getattr__("SHOW_NOTATION")});
      var1.setlocal("_nodetype_mask", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$43(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filter", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startContainer$44(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyObject var3 = var1.getlocal(0).__getattr__("_nodetype_mask").__getitem__(var1.getlocal(1).__getattr__("nodeType"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(474);
      if (var1.getlocal(0).__getattr__("filter").__getattr__("whatToShow")._and(var1.getlocal(2)).__nonzero__()) {
         var1.setline(475);
         var3 = var1.getlocal(0).__getattr__("filter").__getattr__("startContainer").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(476);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._eq(var1.getglobal("FILTER_INTERRUPT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(477);
            throw Py.makeException(var1.getglobal("ParseEscape"));
         } else {
            var1.setline(478);
            var3 = var1.getlocal(3);
            var10000 = var3._notin(var1.getglobal("_ALLOWED_FILTER_RETURNS"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(479);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("startContainer() returned illegal value: ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(3))));
            } else {
               var1.setline(481);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }
      } else {
         var1.setline(483);
         var3 = var1.getglobal("FILTER_ACCEPT");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject acceptNode$45(PyFrame var1, ThreadState var2) {
      var1.setline(486);
      PyObject var3 = var1.getlocal(0).__getattr__("_nodetype_mask").__getitem__(var1.getlocal(1).__getattr__("nodeType"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(487);
      if (!var1.getlocal(0).__getattr__("filter").__getattr__("whatToShow")._and(var1.getlocal(2)).__nonzero__()) {
         var1.setline(503);
         var3 = var1.getglobal("FILTER_ACCEPT");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(488);
         var3 = var1.getlocal(0).__getattr__("filter").__getattr__("acceptNode").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(489);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._eq(var1.getglobal("FILTER_INTERRUPT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(490);
            throw Py.makeException(var1.getglobal("ParseEscape"));
         } else {
            var1.setline(491);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(var1.getglobal("FILTER_SKIP"));
            var3 = null;
            PyObject var4;
            if (!var10000.__nonzero__()) {
               var1.setline(498);
               var4 = var1.getlocal(3);
               var10000 = var4._notin(var1.getglobal("_ALLOWED_FILTER_RETURNS"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(499);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("acceptNode() returned illegal value: ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(3))));
               } else {
                  var1.setline(501);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(493);
               var3 = var1.getlocal(1).__getattr__("parentNode");
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(494);
               var3 = var1.getlocal(1).__getattr__("childNodes").__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

               while(true) {
                  var1.setline(494);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(497);
                     var3 = var1.getglobal("FILTER_REJECT");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(495);
                  var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(5));
               }
            }
         }
      }
   }

   public PyObject FilterCrutch$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(522);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_builder"), PyString.fromInterned("_level"), PyString.fromInterned("_old_start"), PyString.fromInterned("_old_end")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(524);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(525);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_level", var3);
      var3 = null;
      var1.setline(526);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_builder", var4);
      var3 = null;
      var1.setline(527);
      var4 = var1.getlocal(1).__getattr__("_parser");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(528);
      var4 = var1.getlocal(2).__getattr__("StartElementHandler");
      var1.getlocal(0).__setattr__("_old_start", var4);
      var3 = null;
      var1.setline(529);
      var4 = var1.getlocal(2).__getattr__("EndElementHandler");
      var1.getlocal(0).__setattr__("_old_end", var4);
      var3 = null;
      var1.setline(530);
      var4 = var1.getlocal(0).__getattr__("start_element_handler");
      var1.getlocal(2).__setattr__("StartElementHandler", var4);
      var3 = null;
      var1.setline(531);
      var4 = var1.getlocal(0).__getattr__("end_element_handler");
      var1.getlocal(2).__setattr__("EndElementHandler", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Rejecter$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(534);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(536);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$49, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(548);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, start_element_handler$50, (PyObject)null);
      var1.setlocal("start_element_handler", var5);
      var3 = null;
      var1.setline(551);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, end_element_handler$51, (PyObject)null);
      var1.setlocal("end_element_handler", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$49(PyFrame var1, ThreadState var2) {
      var1.setline(537);
      var1.getglobal("FilterCrutch").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(538);
      PyObject var3 = var1.getlocal(1).__getattr__("_parser");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(539);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("ProcessingInstructionHandler"), PyString.fromInterned("CommentHandler"), PyString.fromInterned("CharacterDataHandler"), PyString.fromInterned("StartCdataSectionHandler"), PyString.fromInterned("EndCdataSectionHandler"), PyString.fromInterned("ExternalEntityRefHandler")})).__iter__();

      while(true) {
         var1.setline(539);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(546);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getglobal("None"));
      }
   }

   public PyObject start_element_handler$50(PyFrame var1, ThreadState var2) {
      var1.setline(549);
      PyObject var3 = var1.getlocal(0).__getattr__("_level")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("_level", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_element_handler$51(PyFrame var1, ThreadState var2) {
      var1.setline(552);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(554);
         var3 = var1.getlocal(0).__getattr__("_builder").__getattr__("_parser");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(555);
         var1.getlocal(0).__getattr__("_builder").__getattr__("install").__call__(var2, var1.getlocal(2));
         var1.setline(556);
         var3 = var1.getlocal(0).__getattr__("_old_start");
         var1.getlocal(2).__setattr__("StartElementHandler", var3);
         var3 = null;
         var1.setline(557);
         var3 = var1.getlocal(0).__getattr__("_old_end");
         var1.getlocal(2).__setattr__("EndElementHandler", var3);
         var3 = null;
      } else {
         var1.setline(559);
         var3 = var1.getlocal(0).__getattr__("_level")._sub(Py.newInteger(1));
         var1.getlocal(0).__setattr__("_level", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Skipper$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(562);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(564);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, start_element_handler$53, (PyObject)null);
      var1.setlocal("start_element_handler", var5);
      var3 = null;
      var1.setline(570);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, end_element_handler$54, (PyObject)null);
      var1.setlocal("end_element_handler", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject start_element_handler$53(PyFrame var1, ThreadState var2) {
      var1.setline(565);
      PyObject var3 = var1.getlocal(0).__getattr__("_builder").__getattr__("curNode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(566);
      PyObject var10000 = var1.getlocal(0).__getattr__("_old_start");
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.setline(567);
      var3 = var1.getlocal(0).__getattr__("_builder").__getattr__("curNode");
      var10000 = var3._isnot(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(568);
         var3 = var1.getlocal(0).__getattr__("_level")._add(Py.newInteger(1));
         var1.getlocal(0).__setattr__("_level", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_element_handler$54(PyFrame var1, ThreadState var2) {
      var1.setline(571);
      PyObject var3 = var1.getlocal(0).__getattr__("_level");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(574);
         var3 = var1.getlocal(0).__getattr__("_old_start");
         var1.getlocal(0).__getattr__("_builder").__getattr__("_parser").__setattr__("StartElementHandler", var3);
         var3 = null;
         var1.setline(575);
         var3 = var1.getlocal(0).__getattr__("_old_end");
         var1.getlocal(0).__getattr__("_builder").__getattr__("_parser").__setattr__("EndElementHandler", var3);
         var3 = null;
         var1.setline(576);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_builder", var3);
         var3 = null;
      } else {
         var1.setline(578);
         var3 = var1.getlocal(0).__getattr__("_level")._sub(Py.newInteger(1));
         var1.getlocal(0).__setattr__("_level", var3);
         var3 = null;
         var1.setline(579);
         var10000 = var1.getlocal(0).__getattr__("_old_end");
         PyObject[] var5 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000._callextra(var5, var4, var1.getlocal(1), (PyObject)null);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FragmentBuilder$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Builder which constructs document fragments given XML source\n    text and a context node.\n\n    The context node is expected to provide information about the\n    namespace declarations which are in scope at the start of the\n    fragment.\n    "));
      var1.setline(608);
      PyString.fromInterned("Builder which constructs document fragments given XML source\n    text and a context node.\n\n    The context node is expected to provide information about the\n    namespace declarations which are in scope at the start of the\n    fragment.\n    ");
      var1.setline(610);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$56, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(619);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$57, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(623);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseFile$58, PyString.fromInterned("Parse a document fragment from a file object, returning the\n        fragment node."));
      var1.setlocal("parseFile", var4);
      var3 = null;
      var1.setline(628);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parseString$59, PyString.fromInterned("Parse a document fragment from a string, returning the\n        fragment node."));
      var1.setlocal("parseString", var4);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getDeclarations$60, PyString.fromInterned("Re-create the internal subset from the DocumentType node.\n\n        This is only needed if we don't already have the\n        internalSubset as a string.\n        "));
      var1.setlocal("_getDeclarations", var4);
      var3 = null;
      var1.setline(692);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getNSattrs$61, (PyObject)null);
      var1.setlocal("_getNSattrs", var4);
      var3 = null;
      var1.setline(695);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, external_entity_ref_handler$62, (PyObject)null);
      var1.setlocal("external_entity_ref_handler", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$56(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyObject var3 = var1.getlocal(1).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getglobal("DOCUMENT_NODE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(612);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("originalDocument", var3);
         var3 = null;
         var1.setline(613);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("context", var3);
         var3 = null;
      } else {
         var1.setline(615);
         var3 = var1.getlocal(1).__getattr__("ownerDocument");
         var1.getlocal(0).__setattr__("originalDocument", var3);
         var3 = null;
         var1.setline(616);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("context", var3);
         var3 = null;
      }

      var1.setline(617);
      var1.getglobal("ExpatBuilder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$57(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      var1.getglobal("ExpatBuilder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(621);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("fragment", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseFile$58(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyString.fromInterned("Parse a document fragment from a file object, returning the\n        fragment node.");
      var1.setline(626);
      PyObject var3 = var1.getlocal(0).__getattr__("parseString").__call__(var2, var1.getlocal(1).__getattr__("read").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseString$59(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyString.fromInterned("Parse a document fragment from a string, returning the\n        fragment node.");
      var1.setline(631);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_source", var3);
      var3 = null;
      var1.setline(632);
      var3 = var1.getlocal(0).__getattr__("getParser").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(633);
      var3 = var1.getlocal(0).__getattr__("originalDocument").__getattr__("doctype");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(634);
      PyString var5 = PyString.fromInterned("");
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(635);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(636);
         PyObject var10000 = var1.getlocal(3).__getattr__("internalSubset");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_getDeclarations").__call__(var2);
         }

         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(637);
         if (var1.getlocal(3).__getattr__("publicId").__nonzero__()) {
            var1.setline(638);
            var3 = PyString.fromInterned("PUBLIC \"%s\" \"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("publicId"), var1.getlocal(3).__getattr__("systemId")}));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(640);
            if (var1.getlocal(3).__getattr__("systemId").__nonzero__()) {
               var1.setline(641);
               var3 = PyString.fromInterned("SYSTEM \"%s\"")._mod(var1.getlocal(3).__getattr__("systemId"));
               var1.setlocal(4, var3);
               var3 = null;
            }
         }
      } else {
         var1.setline(643);
         var5 = PyString.fromInterned("");
         var1.setlocal(5, var5);
         var3 = null;
      }

      var1.setline(644);
      var3 = var1.getlocal(0).__getattr__("_getNSattrs").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(645);
      var3 = var1.getglobal("_FRAGMENT_BUILDER_TEMPLATE")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}));
      var1.setlocal(7, var3);
      var3 = null;

      try {
         var1.setline(647);
         var1.getlocal(2).__getattr__("Parse").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)Py.newInteger(1));
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(649);
         var1.getlocal(0).__getattr__("reset").__call__(var2);
         var1.setline(650);
         throw Py.makeException();
      }

      var1.setline(651);
      var3 = var1.getlocal(0).__getattr__("fragment");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(652);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.setline(654);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _getDeclarations$60(PyFrame var1, ThreadState var2) {
      var1.setline(661);
      PyString.fromInterned("Re-create the internal subset from the DocumentType node.\n\n        This is only needed if we don't already have the\n        internalSubset as a string.\n        ");
      var1.setline(662);
      PyObject var3 = var1.getlocal(0).__getattr__("context").__getattr__("ownerDocument").__getattr__("doctype");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(663);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(664);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(665);
         var3 = var1.getglobal("range").__call__(var2, var1.getlocal(1).__getattr__("notations").__getattr__("length")).__iter__();

         label44:
         while(true) {
            var1.setline(665);
            PyObject var4 = var3.__iternext__();
            PyObject var5;
            if (var4 == null) {
               var1.setline(675);
               var3 = var1.getglobal("range").__call__(var2, var1.getlocal(1).__getattr__("entities").__getattr__("length")).__iter__();

               while(true) {
                  var1.setline(675);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label44;
                  }

                  var1.setlocal(3, var4);
                  var1.setline(676);
                  var5 = var1.getlocal(1).__getattr__("entities").__getattr__("item").__call__(var2, var1.getlocal(3));
                  var1.setlocal(5, var5);
                  var5 = null;
                  var1.setline(677);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(678);
                     var5 = var1.getlocal(2)._add(PyString.fromInterned("\n  "));
                     var1.setlocal(2, var5);
                     var5 = null;
                  }

                  var1.setline(679);
                  var5 = PyString.fromInterned("%s<!ENTITY %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("nodeName")}));
                  var1.setlocal(2, var5);
                  var5 = null;
                  var1.setline(680);
                  if (var1.getlocal(5).__getattr__("publicId").__nonzero__()) {
                     var1.setline(681);
                     var5 = PyString.fromInterned("%s PUBLIC \"%s\"\n             \"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("publicId"), var1.getlocal(5).__getattr__("systemId")}));
                     var1.setlocal(2, var5);
                     var5 = null;
                  } else {
                     var1.setline(683);
                     if (var1.getlocal(5).__getattr__("systemId").__nonzero__()) {
                        var1.setline(684);
                        var5 = PyString.fromInterned("%s SYSTEM \"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("systemId")}));
                        var1.setlocal(2, var5);
                        var5 = null;
                     } else {
                        var1.setline(686);
                        var5 = PyString.fromInterned("%s \"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("firstChild").__getattr__("data")}));
                        var1.setlocal(2, var5);
                        var5 = null;
                     }
                  }

                  var1.setline(687);
                  if (var1.getlocal(5).__getattr__("notationName").__nonzero__()) {
                     var1.setline(688);
                     var5 = PyString.fromInterned("%s NOTATION %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("notationName")}));
                     var1.setlocal(2, var5);
                     var5 = null;
                  }

                  var1.setline(689);
                  var5 = var1.getlocal(2)._add(PyString.fromInterned(">"));
                  var1.setlocal(2, var5);
                  var5 = null;
               }
            }

            var1.setlocal(3, var4);
            var1.setline(666);
            var5 = var1.getlocal(1).__getattr__("notations").__getattr__("item").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(667);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(668);
               var5 = var1.getlocal(2)._add(PyString.fromInterned("\n  "));
               var1.setlocal(2, var5);
               var5 = null;
            }

            var1.setline(669);
            var5 = PyString.fromInterned("%s<!NOTATION %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__getattr__("nodeName")}));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(670);
            if (var1.getlocal(4).__getattr__("publicId").__nonzero__()) {
               var1.setline(671);
               var5 = PyString.fromInterned("%s PUBLIC \"%s\"\n             \"%s\">")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__getattr__("publicId"), var1.getlocal(4).__getattr__("systemId")}));
               var1.setlocal(2, var5);
               var5 = null;
            } else {
               var1.setline(674);
               var5 = PyString.fromInterned("%s SYSTEM \"%s\">")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__getattr__("systemId")}));
               var1.setlocal(2, var5);
               var5 = null;
            }
         }
      }

      var1.setline(690);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _getNSattrs$61(PyFrame var1, ThreadState var2) {
      var1.setline(693);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject external_entity_ref_handler$62(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getglobal("_FRAGMENT_BUILDER_INTERNAL_SYSTEM_ID"));
      var3 = null;
      PyObject[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(699);
         var3 = var1.getlocal(0).__getattr__("document");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(700);
         var3 = var1.getlocal(0).__getattr__("curNode");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(701);
         var3 = var1.getlocal(0).__getattr__("_parser").__getattr__("ExternalEntityParserCreate").__call__(var2, var1.getlocal(1));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(703);
         var3 = var1.getlocal(0).__getattr__("originalDocument");
         var1.getlocal(0).__setattr__("document", var3);
         var3 = null;
         var1.setline(704);
         var3 = var1.getlocal(0).__getattr__("document").__getattr__("createDocumentFragment").__call__(var2);
         var1.getlocal(0).__setattr__("fragment", var3);
         var3 = null;
         var1.setline(705);
         var3 = var1.getlocal(0).__getattr__("fragment");
         var1.getlocal(0).__setattr__("curNode", var3);
         var3 = null;
         var3 = null;

         PyObject var6;
         try {
            var1.setline(707);
            var1.getlocal(7).__getattr__("Parse").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_source"), (PyObject)Py.newInteger(1));
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(709);
            var6 = var1.getlocal(6);
            var1.getlocal(0).__setattr__("curNode", var6);
            var4 = null;
            var1.setline(710);
            var6 = var1.getlocal(5);
            var1.getlocal(0).__setattr__("document", var6);
            var4 = null;
            var1.setline(711);
            var6 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_source", var6);
            var4 = null;
            throw (Throwable)var5;
         }

         var1.setline(709);
         var6 = var1.getlocal(6);
         var1.getlocal(0).__setattr__("curNode", var6);
         var4 = null;
         var1.setline(710);
         var6 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("document", var6);
         var4 = null;
         var1.setline(711);
         var6 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_source", var6);
         var4 = null;
         var1.setline(712);
         PyInteger var7 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(714);
         var10000 = var1.getglobal("ExpatBuilder").__getattr__("external_entity_ref_handler");
         var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Namespaces$63(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mix-in class for builders; adds support for namespaces."));
      var1.setline(719);
      PyString.fromInterned("Mix-in class for builders; adds support for namespaces.");
      var1.setline(721);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _initNamespaces$64, (PyObject)null);
      var1.setlocal("_initNamespaces", var4);
      var3 = null;
      var1.setline(726);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, createParser$65, PyString.fromInterned("Create a new namespace-handling parser."));
      var1.setlocal("createParser", var4);
      var3 = null;
      var1.setline(732);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, install$66, PyString.fromInterned("Insert the namespace-handlers onto the parser."));
      var1.setlocal("install", var4);
      var3 = null;
      var1.setline(739);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start_namespace_decl_handler$67, PyString.fromInterned("Push this namespace declaration on our storage."));
      var1.setlocal("start_namespace_decl_handler", var4);
      var3 = null;
      var1.setline(743);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start_element_handler$68, (PyObject)null);
      var1.setlocal("start_element_handler", var4);
      var3 = null;
      var1.setline(795);
      if (var1.getname("__debug__").__nonzero__()) {
         var1.setline(801);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, end_element_handler$69, (PyObject)null);
         var1.setlocal("end_element_handler", var4);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject _initNamespaces$64(PyFrame var1, ThreadState var2) {
      var1.setline(724);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_ns_ordered_prefixes", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject createParser$65(PyFrame var1, ThreadState var2) {
      var1.setline(727);
      PyString.fromInterned("Create a new namespace-handling parser.");
      var1.setline(728);
      PyObject var10000 = var1.getglobal("expat").__getattr__("ParserCreate");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned(" ")};
      String[] var4 = new String[]{"namespace_separator"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(729);
      var5 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("namespace_prefixes", var5);
      var3 = null;
      var1.setline(730);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject install$66(PyFrame var1, ThreadState var2) {
      var1.setline(733);
      PyString.fromInterned("Insert the namespace-handlers onto the parser.");
      var1.setline(734);
      var1.getglobal("ExpatBuilder").__getattr__("install").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(735);
      if (var1.getlocal(0).__getattr__("_options").__getattr__("namespace_declarations").__nonzero__()) {
         var1.setline(736);
         PyObject var3 = var1.getlocal(0).__getattr__("start_namespace_decl_handler");
         var1.getlocal(1).__setattr__("StartNamespaceDeclHandler", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_namespace_decl_handler$67(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      PyString.fromInterned("Push this namespace declaration on our storage.");
      var1.setline(741);
      var1.getlocal(0).__getattr__("_ns_ordered_prefixes").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_element_handler$68(PyFrame var1, ThreadState var2) {
      var1.setline(744);
      PyString var3 = PyString.fromInterned(" ");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      PyObject var5;
      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(745);
         var8 = var1.getglobal("_parse_ns_name").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var8, 4);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(747);
         var8 = var1.getglobal("EMPTY_NAMESPACE");
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(748);
         var8 = var1.getlocal(1);
         var1.setlocal(6, var8);
         var3 = null;
         var1.setline(749);
         var8 = var1.getglobal("None");
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(750);
         var8 = var1.getglobal("EMPTY_PREFIX");
         var1.setlocal(5, var8);
         var3 = null;
      }

      var1.setline(751);
      var8 = var1.getglobal("minidom").__getattr__("Element").__call__(var2, var1.getlocal(6), var1.getlocal(3), var1.getlocal(5), var1.getlocal(4));
      var1.setlocal(7, var8);
      var3 = null;
      var1.setline(752);
      var8 = var1.getlocal(0).__getattr__("document");
      var1.getlocal(7).__setattr__("ownerDocument", var8);
      var3 = null;
      var1.setline(753);
      var1.getglobal("_append_child").__call__(var2, var1.getlocal(0).__getattr__("curNode"), var1.getlocal(7));
      var1.setline(754);
      var8 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("curNode", var8);
      var3 = null;
      var1.setline(756);
      PyObject var9;
      if (var1.getlocal(0).__getattr__("_ns_ordered_prefixes").__nonzero__()) {
         var1.setline(757);
         var8 = var1.getlocal(0).__getattr__("_ns_ordered_prefixes").__iter__();

         while(true) {
            var1.setline(757);
            var9 = var8.__iternext__();
            if (var9 == null) {
               var1.setline(770);
               var1.getlocal(0).__getattr__("_ns_ordered_prefixes").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
               break;
            }

            PyObject[] var11 = Py.unpackSequence(var9, 2);
            PyObject var6 = var11[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(758);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(759);
               var5 = var1.getglobal("minidom").__getattr__("Attr").__call__(var2, var1.getglobal("_intern").__call__(var2, var1.getlocal(0), PyString.fromInterned("xmlns:")._add(var1.getlocal(5))), var1.getglobal("XMLNS_NAMESPACE"), var1.getlocal(5), PyString.fromInterned("xmlns"));
               var1.setlocal(8, var5);
               var5 = null;
            } else {
               var1.setline(762);
               var5 = var1.getglobal("minidom").__getattr__("Attr").__call__(var2, PyString.fromInterned("xmlns"), var1.getglobal("XMLNS_NAMESPACE"), PyString.fromInterned("xmlns"), var1.getglobal("EMPTY_PREFIX"));
               var1.setlocal(8, var5);
               var5 = null;
            }

            var1.setline(764);
            var5 = var1.getlocal(8).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__getattr__("__dict__");
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(765);
            var5 = var1.getlocal(3);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("data"), var5);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var5);
            var1.setline(766);
            var5 = var1.getlocal(8).__getattr__("__dict__");
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(767);
            var5 = var1.getlocal(3);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("value"), var5);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var5);
            var1.setline(768);
            var5 = var1.getlocal(0).__getattr__("document");
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var5);
            var5 = null;
            var1.setline(769);
            var1.getglobal("_set_attribute_node").__call__(var2, var1.getlocal(7), var1.getlocal(8));
         }
      }

      var1.setline(772);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(773);
         var8 = var1.getlocal(7).__getattr__("_attrs");
         var1.setlocal(10, var8);
         var3 = null;
         var1.setline(774);
         var8 = var1.getlocal(7).__getattr__("_attrsNS");
         var1.setlocal(11, var8);
         var3 = null;
         var1.setline(775);
         var8 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(775);
            var9 = var8.__iternext__();
            if (var9 == null) {
               break;
            }

            var1.setlocal(12, var9);
            var1.setline(776);
            var5 = var1.getlocal(2).__getitem__(var1.getlocal(12));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(777);
            var5 = var1.getlocal(2).__getitem__(var1.getlocal(12)._add(Py.newInteger(1)));
            var1.setlocal(14, var5);
            var5 = null;
            var1.setline(778);
            PyString var12 = PyString.fromInterned(" ");
            var10000 = var12._in(var1.getlocal(13));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(779);
               var5 = var1.getglobal("_parse_ns_name").__call__(var2, var1.getlocal(0), var1.getlocal(13));
               PyObject[] var10 = Py.unpackSequence(var5, 4);
               PyObject var7 = var10[0];
               var1.setlocal(3, var7);
               var7 = null;
               var7 = var10[1];
               var1.setlocal(4, var7);
               var7 = null;
               var7 = var10[2];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var10[3];
               var1.setlocal(6, var7);
               var7 = null;
               var5 = null;
               var1.setline(780);
               var5 = var1.getglobal("minidom").__getattr__("Attr").__call__(var2, var1.getlocal(6), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(781);
               var5 = var1.getlocal(8);
               var1.getlocal(10).__setitem__(var1.getlocal(6), var5);
               var5 = null;
               var1.setline(782);
               var5 = var1.getlocal(8);
               var1.getlocal(11).__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})), var5);
               var5 = null;
            } else {
               var1.setline(784);
               var5 = var1.getglobal("minidom").__getattr__("Attr").__call__(var2, var1.getlocal(13), var1.getglobal("EMPTY_NAMESPACE"), var1.getlocal(13), var1.getglobal("EMPTY_PREFIX"));
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(786);
               var5 = var1.getlocal(8);
               var1.getlocal(10).__setitem__(var1.getlocal(13), var5);
               var5 = null;
               var1.setline(787);
               var5 = var1.getlocal(8);
               var1.getlocal(11).__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getglobal("EMPTY_NAMESPACE"), var1.getlocal(13)})), var5);
               var5 = null;
            }

            var1.setline(788);
            var5 = var1.getlocal(8).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__getattr__("__dict__");
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(789);
            var5 = var1.getlocal(14);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("data"), var5);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var5);
            var1.setline(790);
            var5 = var1.getlocal(8).__getattr__("__dict__");
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(791);
            var5 = var1.getlocal(0).__getattr__("document");
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var5);
            var5 = null;
            var1.setline(792);
            var5 = var1.getlocal(14);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("value"), var5);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var5);
            var1.setline(793);
            var5 = var1.getlocal(7);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("ownerElement"), var5);
            var5 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_element_handler$69(PyFrame var1, ThreadState var2) {
      var1.setline(802);
      PyObject var3 = var1.getlocal(0).__getattr__("curNode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(803);
      PyString var6 = PyString.fromInterned(" ");
      PyObject var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(804);
         var3 = var1.getglobal("_parse_ns_name").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 4);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(805);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(2).__getattr__("namespaceURI");
            var10000 = var3._eq(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(2).__getattr__("localName");
               var10000 = var3._eq(var1.getlocal(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(2).__getattr__("prefix");
                  var10000 = var3._eq(var1.getlocal(5));
                  var3 = null;
               }
            }

            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("element stack messed up! (namespace)"));
            }
         }
      } else {
         var1.setline(810);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(2).__getattr__("nodeName");
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("element stack messed up - bad nodeName"));
            }
         }

         var1.setline(812);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(2).__getattr__("namespaceURI");
            var10000 = var3._eq(var1.getglobal("EMPTY_NAMESPACE"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("element stack messed up - bad namespaceURI"));
            }
         }
      }

      var1.setline(814);
      var3 = var1.getlocal(2).__getattr__("parentNode");
      var1.getlocal(0).__setattr__("curNode", var3);
      var3 = null;
      var1.setline(815);
      var1.getlocal(0).__getattr__("_finish_end_element").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ExpatBuilderNS$70(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Document builder that supports namespaces."));
      var1.setline(819);
      PyString.fromInterned("Document builder that supports namespaces.");
      var1.setline(821);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, reset$71, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject reset$71(PyFrame var1, ThreadState var2) {
      var1.setline(822);
      var1.getglobal("ExpatBuilder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(823);
      var1.getlocal(0).__getattr__("_initNamespaces").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FragmentBuilderNS$72(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Fragment builder that supports namespaces."));
      var1.setline(827);
      PyString.fromInterned("Fragment builder that supports namespaces.");
      var1.setline(829);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, reset$73, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(833);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getNSattrs$74, PyString.fromInterned("Return string of namespace attributes from this element and\n        ancestors."));
      var1.setlocal("_getNSattrs", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject reset$73(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      var1.getglobal("FragmentBuilder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(831);
      var1.getlocal(0).__getattr__("_initNamespaces").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getNSattrs$74(PyFrame var1, ThreadState var2) {
      var1.setline(835);
      PyString.fromInterned("Return string of namespace attributes from this element and\n        ancestors.");
      var1.setline(841);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(842);
      PyObject var7 = var1.getlocal(0).__getattr__("context");
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(843);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;

      while(true) {
         var1.setline(844);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(860);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(845);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_ns_prefix_uri")).__nonzero__()) {
            var1.setline(846);
            var7 = var1.getlocal(2).__getattr__("_ns_prefix_uri").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(846);
               PyObject var4 = var7.__iternext__();
               if (var4 == null) {
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(848);
               PyObject var9 = var1.getlocal(4);
               PyObject var10000 = var9._in(var1.getlocal(3));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(850);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
                  var1.setline(851);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(852);
                     var9 = PyString.fromInterned("xmlns:")._add(var1.getlocal(4));
                     var1.setlocal(6, var9);
                     var5 = null;
                  } else {
                     var1.setline(854);
                     PyString var10 = PyString.fromInterned("xmlns");
                     var1.setlocal(6, var10);
                     var5 = null;
                  }

                  var1.setline(855);
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(856);
                     var9 = PyString.fromInterned("%s\n    %s='%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6), var1.getlocal(5)}));
                     var1.setlocal(1, var9);
                     var5 = null;
                  } else {
                     var1.setline(858);
                     var9 = PyString.fromInterned(" %s='%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)}));
                     var1.setlocal(1, var9);
                     var5 = null;
                  }
               }
            }
         }

         var1.setline(859);
         var7 = var1.getlocal(2).__getattr__("parentNode");
         var1.setlocal(2, var7);
         var3 = null;
      }
   }

   public PyObject ParseEscape$75(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised to short-circuit parsing in InternalSubsetExtractor."));
      var1.setline(864);
      PyString.fromInterned("Exception raised to short-circuit parsing in InternalSubsetExtractor.");
      var1.setline(865);
      return var1.getf_locals();
   }

   public PyObject InternalSubsetExtractor$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("XML processor which can rip out the internal document type subset."));
      var1.setline(868);
      PyString.fromInterned("XML processor which can rip out the internal document type subset.");
      var1.setline(870);
      PyObject var3 = var1.getname("None");
      var1.setlocal("subset", var3);
      var3 = null;
      var1.setline(872);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, getSubset$77, PyString.fromInterned("Return the internal subset as a string."));
      var1.setlocal("getSubset", var5);
      var3 = null;
      var1.setline(876);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parseFile$78, (PyObject)null);
      var1.setlocal("parseFile", var5);
      var3 = null;
      var1.setline(882);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parseString$79, (PyObject)null);
      var1.setlocal("parseString", var5);
      var3 = null;
      var1.setline(888);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, install$80, (PyObject)null);
      var1.setlocal("install", var5);
      var3 = null;
      var1.setline(892);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, start_doctype_decl_handler$81, (PyObject)null);
      var1.setlocal("start_doctype_decl_handler", var5);
      var3 = null;
      var1.setline(902);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, end_doctype_decl_handler$82, (PyObject)null);
      var1.setlocal("end_doctype_decl_handler", var5);
      var3 = null;
      var1.setline(907);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, start_element_handler$83, (PyObject)null);
      var1.setlocal("start_element_handler", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getSubset$77(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      PyString.fromInterned("Return the internal subset as a string.");
      var1.setline(874);
      PyObject var3 = var1.getlocal(0).__getattr__("subset");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseFile$78(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(878);
         var1.getglobal("ExpatBuilder").__getattr__("parseFile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("ParseEscape"))) {
            throw var3;
         }

         var1.setline(880);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseString$79(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(884);
         var1.getglobal("ExpatBuilder").__getattr__("parseString").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("ParseEscape"))) {
            throw var3;
         }

         var1.setline(886);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install$80(PyFrame var1, ThreadState var2) {
      var1.setline(889);
      PyObject var3 = var1.getlocal(0).__getattr__("start_doctype_decl_handler");
      var1.getlocal(1).__setattr__("StartDoctypeDeclHandler", var3);
      var3 = null;
      var1.setline(890);
      var3 = var1.getlocal(0).__getattr__("start_element_handler");
      var1.getlocal(1).__setattr__("StartElementHandler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_doctype_decl_handler$81(PyFrame var1, ThreadState var2) {
      var1.setline(894);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(895);
         PyObject var3 = var1.getlocal(0).__getattr__("getParser").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(896);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"subset", var4);
         var3 = null;
         var1.setline(897);
         var3 = var1.getlocal(0).__getattr__("subset").__getattr__("append");
         var1.getlocal(5).__setattr__("DefaultHandler", var3);
         var3 = null;
         var1.setline(898);
         var3 = var1.getlocal(0).__getattr__("end_doctype_decl_handler");
         var1.getlocal(5).__setattr__("EndDoctypeDeclHandler", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(900);
         throw Py.makeException(var1.getglobal("ParseEscape").__call__(var2));
      }
   }

   public PyObject end_doctype_decl_handler$82(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("subset")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"), (PyObject)PyString.fromInterned("\n")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r"), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(904);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("subset", var3);
      var3 = null;
      var1.setline(905);
      throw Py.makeException(var1.getglobal("ParseEscape").__call__(var2));
   }

   public PyObject start_element_handler$83(PyFrame var1, ThreadState var2) {
      var1.setline(908);
      throw Py.makeException(var1.getglobal("ParseEscape").__call__(var2));
   }

   public PyObject parse$84(PyFrame var1, ThreadState var2) {
      var1.setline(915);
      PyString.fromInterned("Parse a document, returning the resulting Document node.\n\n    'file' may be either a file name or an open file object.\n    ");
      var1.setline(916);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(917);
         var3 = var1.getglobal("ExpatBuilderNS").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(919);
         var3 = var1.getglobal("ExpatBuilder").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(921);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("StringTypes")).__nonzero__()) {
         var1.setline(922);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(3, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(924);
            PyObject var4 = var1.getlocal(2).__getattr__("parseFile").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(926);
            var1.getlocal(3).__getattr__("close").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(926);
         var1.getlocal(3).__getattr__("close").__call__(var2);
      } else {
         var1.setline(928);
         var3 = var1.getlocal(2).__getattr__("parseFile").__call__(var2, var1.getlocal(0));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(929);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseString$85(PyFrame var1, ThreadState var2) {
      var1.setline(935);
      PyString.fromInterned("Parse a document from a string, returning the resulting\n    Document node.\n    ");
      var1.setline(936);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(937);
         var3 = var1.getglobal("ExpatBuilderNS").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(939);
         var3 = var1.getglobal("ExpatBuilder").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(940);
      var3 = var1.getlocal(2).__getattr__("parseString").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseFragment$86(PyFrame var1, ThreadState var2) {
      var1.setline(949);
      PyString.fromInterned("Parse a fragment of a document, given the context from which it\n    was originally extracted.  context should be the parent of the\n    node(s) which are in the fragment.\n\n    'file' may be either a file name or an open file object.\n    ");
      var1.setline(950);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(951);
         var3 = var1.getglobal("FragmentBuilderNS").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(953);
         var3 = var1.getglobal("FragmentBuilder").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(955);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("StringTypes")).__nonzero__()) {
         var1.setline(956);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(4, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(958);
            PyObject var4 = var1.getlocal(3).__getattr__("parseFile").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var4);
            var4 = null;
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(960);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(960);
         var1.getlocal(4).__getattr__("close").__call__(var2);
      } else {
         var1.setline(962);
         var3 = var1.getlocal(3).__getattr__("parseFile").__call__(var2, var1.getlocal(0));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(963);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseFragmentString$87(PyFrame var1, ThreadState var2) {
      var1.setline(970);
      PyString.fromInterned("Parse a fragment of a document from a string, given the context\n    from which it was originally extracted.  context should be the\n    parent of the node(s) which are in the fragment.\n    ");
      var1.setline(971);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(972);
         var3 = var1.getglobal("FragmentBuilderNS").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(974);
         var3 = var1.getglobal("FragmentBuilder").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(975);
      var3 = var1.getlocal(3).__getattr__("parseString").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makeBuilder$88(PyFrame var1, ThreadState var2) {
      var1.setline(979);
      PyString.fromInterned("Create a builder based on an Options object.");
      var1.setline(980);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("namespaces").__nonzero__()) {
         var1.setline(981);
         var3 = var1.getglobal("ExpatBuilderNS").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(983);
         var3 = var1.getglobal("ExpatBuilder").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public expatbuilder$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ElementInfo$1 = Py.newCode(0, var2, var1, "ElementInfo", 62, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tagName", "model"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 65, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$3 = Py.newCode(1, var2, var1, "__getstate__", 70, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      __setstate__$4 = Py.newCode(2, var2, var1, "__setstate__", 73, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aname", "info", "t"};
      getAttributeType$5 = Py.newCode(2, var2, var1, "getAttributeType", 76, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getAttributeTypeNS$6 = Py.newCode(3, var2, var1, "getAttributeTypeNS", 86, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type"};
      isElementContent$7 = Py.newCode(1, var2, var1, "isElementContent", 89, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isEmpty$8 = Py.newCode(1, var2, var1, "isEmpty", 97, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aname", "info"};
      isId$9 = Py.newCode(2, var2, var1, "isId", 103, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "euri", "ename", "auri", "aname"};
      isIdNS$10 = Py.newCode(5, var2, var1, "isIdNS", 109, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"builder", "s"};
      _intern$11 = Py.newCode(2, var2, var1, "_intern", 113, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"builder", "name", "parts", "intern", "uri", "localname", "prefix", "qname"};
      _parse_ns_name$12 = Py.newCode(2, var2, var1, "_parse_ns_name", 116, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExpatBuilder$13 = Py.newCode(0, var2, var1, "ExpatBuilder", 133, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "options"};
      __init__$14 = Py.newCode(2, var2, var1, "__init__", 137, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      createParser$15 = Py.newCode(1, var2, var1, "createParser", 151, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getParser$16 = Py.newCode(1, var2, var1, "getParser", 155, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$17 = Py.newCode(1, var2, var1, "reset", 166, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser"};
      install$18 = Py.newCode(2, var2, var1, "install", 174, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "parser", "first_buffer", "buffer", "doc"};
      parseFile$19 = Py.newCode(2, var2, var1, "parseFile", 197, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "parser", "doc"};
      parseString$20 = Py.newCode(2, var2, var1, "parseString", 219, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer", "extractor", "subset"};
      _setup_subset$21 = Py.newCode(2, var2, var1, "_setup_subset", 232, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doctypeName", "systemId", "publicId", "has_internal_subset", "doctype"};
      start_doctype_decl_handler$22 = Py.newCode(5, var2, var1, "start_doctype_decl_handler", 240, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_doctype_decl_handler$23 = Py.newCode(1, var2, var1, "end_doctype_decl_handler", 261, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data", "node"};
      pi_handler$24 = Py.newCode(3, var2, var1, "pi_handler", 268, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "childNodes", "node", "value", "d"};
      character_data_handler_cdata$25 = Py.newCode(2, var2, var1, "character_data_handler_cdata", 274, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "childNodes", "node", "d"};
      character_data_handler$26 = Py.newCode(2, var2, var1, "character_data_handler", 296, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "entityName", "is_parameter_entity", "value", "base", "systemId", "publicId", "notationName", "node", "child"};
      entity_decl_handler$27 = Py.newCode(8, var2, var1, "entity_decl_handler", 309, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "notationName", "base", "systemId", "publicId", "node"};
      notation_decl_handler$28 = Py.newCode(5, var2, var1, "notation_decl_handler", 327, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "node"};
      comment_handler$29 = Py.newCode(2, var2, var1, "comment_handler", 333, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      start_cdata_section_handler$30 = Py.newCode(1, var2, var1, "start_cdata_section_handler", 339, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_cdata_section_handler$31 = Py.newCode(1, var2, var1, "end_cdata_section_handler", 343, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "base", "systemId", "publicId"};
      external_entity_ref_handler$32 = Py.newCode(5, var2, var1, "external_entity_ref_handler", 347, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attributes"};
      first_element_handler$33 = Py.newCode(3, var2, var1, "first_element_handler", 350, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attributes", "node", "i", "a", "value", "d"};
      start_element_handler$34 = Py.newCode(3, var2, var1, "start_element_handler", 356, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "filt"};
      _finish_start_element$35 = Py.newCode(2, var2, var1, "_finish_start_element", 376, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "curNode"};
      end_element_handler$36 = Py.newCode(2, var2, var1, "end_element_handler", 399, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "curNode", "info"};
      _finish_end_element$37 = Py.newCode(2, var2, var1, "_finish_end_element", 404, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "info", "L", "child"};
      _handle_white_text_nodes$38 = Py.newCode(3, var2, var1, "_handle_white_text_nodes", 415, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "model", "info"};
      element_decl_handler$39 = Py.newCode(3, var2, var1, "element_decl_handler", 432, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem", "name", "type", "default", "required", "info"};
      attlist_decl_handler$40 = Py.newCode(6, var2, var1, "attlist_decl_handler", 440, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "version", "encoding", "standalone"};
      xml_decl_handler$41 = Py.newCode(4, var2, var1, "xml_decl_handler", 448, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FilterVisibilityController$42 = Py.newCode(0, var2, var1, "FilterVisibilityController", 463, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filter"};
      __init__$43 = Py.newCode(2, var2, var1, "__init__", 469, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "mask", "val"};
      startContainer$44 = Py.newCode(2, var2, var1, "startContainer", 472, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "mask", "val", "parent", "child"};
      acceptNode$45 = Py.newCode(2, var2, var1, "acceptNode", 485, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FilterCrutch$46 = Py.newCode(0, var2, var1, "FilterCrutch", 521, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "builder", "parser"};
      __init__$47 = Py.newCode(2, var2, var1, "__init__", 524, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Rejecter$48 = Py.newCode(0, var2, var1, "Rejecter", 533, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "builder", "parser", "name"};
      __init__$49 = Py.newCode(2, var2, var1, "__init__", 536, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      start_element_handler$50 = Py.newCode(2, var2, var1, "start_element_handler", 548, true, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "parser"};
      end_element_handler$51 = Py.newCode(2, var2, var1, "end_element_handler", 551, true, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Skipper$52 = Py.newCode(0, var2, var1, "Skipper", 561, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "node"};
      start_element_handler$53 = Py.newCode(2, var2, var1, "start_element_handler", 564, true, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      end_element_handler$54 = Py.newCode(2, var2, var1, "end_element_handler", 570, true, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FragmentBuilder$55 = Py.newCode(0, var2, var1, "FragmentBuilder", 601, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "context", "options"};
      __init__$56 = Py.newCode(3, var2, var1, "__init__", 610, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$57 = Py.newCode(1, var2, var1, "reset", 619, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      parseFile$58 = Py.newCode(2, var2, var1, "parseFile", 623, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "parser", "doctype", "ident", "subset", "nsattrs", "document", "fragment"};
      parseString$59 = Py.newCode(2, var2, var1, "parseString", 628, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doctype", "s", "i", "notation", "entity"};
      _getDeclarations$60 = Py.newCode(1, var2, var1, "_getDeclarations", 656, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _getNSattrs$61 = Py.newCode(1, var2, var1, "_getNSattrs", 692, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "base", "systemId", "publicId", "old_document", "old_cur_node", "parser"};
      external_entity_ref_handler$62 = Py.newCode(5, var2, var1, "external_entity_ref_handler", 695, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Namespaces$63 = Py.newCode(0, var2, var1, "Namespaces", 718, false, false, self, 63, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _initNamespaces$64 = Py.newCode(1, var2, var1, "_initNamespaces", 721, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser"};
      createParser$65 = Py.newCode(1, var2, var1, "createParser", 726, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser"};
      install$66 = Py.newCode(2, var2, var1, "install", 732, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      start_namespace_decl_handler$67 = Py.newCode(3, var2, var1, "start_namespace_decl_handler", 739, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attributes", "uri", "localname", "prefix", "qname", "node", "a", "d", "_attrs", "_attrsNS", "i", "aname", "value"};
      start_element_handler$68 = Py.newCode(3, var2, var1, "start_element_handler", 743, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "curNode", "uri", "localname", "prefix", "qname"};
      end_element_handler$69 = Py.newCode(2, var2, var1, "end_element_handler", 801, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExpatBuilderNS$70 = Py.newCode(0, var2, var1, "ExpatBuilderNS", 818, false, false, self, 70, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      reset$71 = Py.newCode(1, var2, var1, "reset", 821, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FragmentBuilderNS$72 = Py.newCode(0, var2, var1, "FragmentBuilderNS", 826, false, false, self, 72, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      reset$73 = Py.newCode(1, var2, var1, "reset", 829, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "context", "L", "prefix", "uri", "declname"};
      _getNSattrs$74 = Py.newCode(1, var2, var1, "_getNSattrs", 833, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ParseEscape$75 = Py.newCode(0, var2, var1, "ParseEscape", 863, false, false, self, 75, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InternalSubsetExtractor$76 = Py.newCode(0, var2, var1, "InternalSubsetExtractor", 867, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      getSubset$77 = Py.newCode(1, var2, var1, "getSubset", 872, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      parseFile$78 = Py.newCode(2, var2, var1, "parseFile", 876, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string"};
      parseString$79 = Py.newCode(2, var2, var1, "parseString", 882, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser"};
      install$80 = Py.newCode(2, var2, var1, "install", 888, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "has_internal_subset", "parser"};
      start_doctype_decl_handler$81 = Py.newCode(5, var2, var1, "start_doctype_decl_handler", 892, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      end_doctype_decl_handler$82 = Py.newCode(1, var2, var1, "end_doctype_decl_handler", 902, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attrs"};
      start_element_handler$83 = Py.newCode(3, var2, var1, "start_element_handler", 907, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "namespaces", "builder", "fp", "result"};
      parse$84 = Py.newCode(2, var2, var1, "parse", 911, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "namespaces", "builder"};
      parseString$85 = Py.newCode(2, var2, var1, "parseString", 932, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "context", "namespaces", "builder", "fp", "result"};
      parseFragment$86 = Py.newCode(3, var2, var1, "parseFragment", 943, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "context", "namespaces", "builder"};
      parseFragmentString$87 = Py.newCode(3, var2, var1, "parseFragmentString", 966, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"options"};
      makeBuilder$88 = Py.newCode(1, var2, var1, "makeBuilder", 978, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new expatbuilder$py("xml/dom/expatbuilder$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(expatbuilder$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ElementInfo$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__getstate__$3(var2, var3);
         case 4:
            return this.__setstate__$4(var2, var3);
         case 5:
            return this.getAttributeType$5(var2, var3);
         case 6:
            return this.getAttributeTypeNS$6(var2, var3);
         case 7:
            return this.isElementContent$7(var2, var3);
         case 8:
            return this.isEmpty$8(var2, var3);
         case 9:
            return this.isId$9(var2, var3);
         case 10:
            return this.isIdNS$10(var2, var3);
         case 11:
            return this._intern$11(var2, var3);
         case 12:
            return this._parse_ns_name$12(var2, var3);
         case 13:
            return this.ExpatBuilder$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.createParser$15(var2, var3);
         case 16:
            return this.getParser$16(var2, var3);
         case 17:
            return this.reset$17(var2, var3);
         case 18:
            return this.install$18(var2, var3);
         case 19:
            return this.parseFile$19(var2, var3);
         case 20:
            return this.parseString$20(var2, var3);
         case 21:
            return this._setup_subset$21(var2, var3);
         case 22:
            return this.start_doctype_decl_handler$22(var2, var3);
         case 23:
            return this.end_doctype_decl_handler$23(var2, var3);
         case 24:
            return this.pi_handler$24(var2, var3);
         case 25:
            return this.character_data_handler_cdata$25(var2, var3);
         case 26:
            return this.character_data_handler$26(var2, var3);
         case 27:
            return this.entity_decl_handler$27(var2, var3);
         case 28:
            return this.notation_decl_handler$28(var2, var3);
         case 29:
            return this.comment_handler$29(var2, var3);
         case 30:
            return this.start_cdata_section_handler$30(var2, var3);
         case 31:
            return this.end_cdata_section_handler$31(var2, var3);
         case 32:
            return this.external_entity_ref_handler$32(var2, var3);
         case 33:
            return this.first_element_handler$33(var2, var3);
         case 34:
            return this.start_element_handler$34(var2, var3);
         case 35:
            return this._finish_start_element$35(var2, var3);
         case 36:
            return this.end_element_handler$36(var2, var3);
         case 37:
            return this._finish_end_element$37(var2, var3);
         case 38:
            return this._handle_white_text_nodes$38(var2, var3);
         case 39:
            return this.element_decl_handler$39(var2, var3);
         case 40:
            return this.attlist_decl_handler$40(var2, var3);
         case 41:
            return this.xml_decl_handler$41(var2, var3);
         case 42:
            return this.FilterVisibilityController$42(var2, var3);
         case 43:
            return this.__init__$43(var2, var3);
         case 44:
            return this.startContainer$44(var2, var3);
         case 45:
            return this.acceptNode$45(var2, var3);
         case 46:
            return this.FilterCrutch$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.Rejecter$48(var2, var3);
         case 49:
            return this.__init__$49(var2, var3);
         case 50:
            return this.start_element_handler$50(var2, var3);
         case 51:
            return this.end_element_handler$51(var2, var3);
         case 52:
            return this.Skipper$52(var2, var3);
         case 53:
            return this.start_element_handler$53(var2, var3);
         case 54:
            return this.end_element_handler$54(var2, var3);
         case 55:
            return this.FragmentBuilder$55(var2, var3);
         case 56:
            return this.__init__$56(var2, var3);
         case 57:
            return this.reset$57(var2, var3);
         case 58:
            return this.parseFile$58(var2, var3);
         case 59:
            return this.parseString$59(var2, var3);
         case 60:
            return this._getDeclarations$60(var2, var3);
         case 61:
            return this._getNSattrs$61(var2, var3);
         case 62:
            return this.external_entity_ref_handler$62(var2, var3);
         case 63:
            return this.Namespaces$63(var2, var3);
         case 64:
            return this._initNamespaces$64(var2, var3);
         case 65:
            return this.createParser$65(var2, var3);
         case 66:
            return this.install$66(var2, var3);
         case 67:
            return this.start_namespace_decl_handler$67(var2, var3);
         case 68:
            return this.start_element_handler$68(var2, var3);
         case 69:
            return this.end_element_handler$69(var2, var3);
         case 70:
            return this.ExpatBuilderNS$70(var2, var3);
         case 71:
            return this.reset$71(var2, var3);
         case 72:
            return this.FragmentBuilderNS$72(var2, var3);
         case 73:
            return this.reset$73(var2, var3);
         case 74:
            return this._getNSattrs$74(var2, var3);
         case 75:
            return this.ParseEscape$75(var2, var3);
         case 76:
            return this.InternalSubsetExtractor$76(var2, var3);
         case 77:
            return this.getSubset$77(var2, var3);
         case 78:
            return this.parseFile$78(var2, var3);
         case 79:
            return this.parseString$79(var2, var3);
         case 80:
            return this.install$80(var2, var3);
         case 81:
            return this.start_doctype_decl_handler$81(var2, var3);
         case 82:
            return this.end_doctype_decl_handler$82(var2, var3);
         case 83:
            return this.start_element_handler$83(var2, var3);
         case 84:
            return this.parse$84(var2, var3);
         case 85:
            return this.parseString$85(var2, var3);
         case 86:
            return this.parseFragment$86(var2, var3);
         case 87:
            return this.parseFragmentString$87(var2, var3);
         case 88:
            return this.makeBuilder$88(var2, var3);
         default:
            return null;
      }
   }
}
