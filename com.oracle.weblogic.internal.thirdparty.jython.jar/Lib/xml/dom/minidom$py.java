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
@MTime(1498849384000L)
@Filename("xml/dom/minidom.py")
public class minidom$py extends PyFunctionTable implements PyRunnable {
   static minidom$py self;
   static final PyCode f$0;
   static final PyCode Node$1;
   static final PyCode __nonzero__$2;
   static final PyCode toxml$3;
   static final PyCode toprettyxml$4;
   static final PyCode hasChildNodes$5;
   static final PyCode _get_childNodes$6;
   static final PyCode _get_firstChild$7;
   static final PyCode _get_lastChild$8;
   static final PyCode insertBefore$9;
   static final PyCode appendChild$10;
   static final PyCode replaceChild$11;
   static final PyCode removeChild$12;
   static final PyCode normalize$13;
   static final PyCode cloneNode$14;
   static final PyCode isSupported$15;
   static final PyCode _get_localName$16;
   static final PyCode isSameNode$17;
   static final PyCode getInterface$18;
   static final PyCode getUserData$19;
   static final PyCode setUserData$20;
   static final PyCode _call_user_data_handler$21;
   static final PyCode unlink$22;
   static final PyCode _append_child$23;
   static final PyCode _in_document$24;
   static final PyCode _write_data$25;
   static final PyCode _get_elements_by_tagName_helper$26;
   static final PyCode _get_elements_by_tagName_ns_helper$27;
   static final PyCode DocumentFragment$28;
   static final PyCode __init__$29;
   static final PyCode Attr$30;
   static final PyCode __init__$31;
   static final PyCode _get_localName$32;
   static final PyCode _get_specified$33;
   static final PyCode __setattr__$34;
   static final PyCode _set_prefix$35;
   static final PyCode _set_value$36;
   static final PyCode unlink$37;
   static final PyCode _get_isId$38;
   static final PyCode _get_schemaType$39;
   static final PyCode NamedNodeMap$40;
   static final PyCode __init__$41;
   static final PyCode _get_length$42;
   static final PyCode item$43;
   static final PyCode items$44;
   static final PyCode itemsNS$45;
   static final PyCode has_key$46;
   static final PyCode keys$47;
   static final PyCode keysNS$48;
   static final PyCode values$49;
   static final PyCode get$50;
   static final PyCode __cmp__$51;
   static final PyCode __getitem__$52;
   static final PyCode __setitem__$53;
   static final PyCode getNamedItem$54;
   static final PyCode getNamedItemNS$55;
   static final PyCode removeNamedItem$56;
   static final PyCode removeNamedItemNS$57;
   static final PyCode setNamedItem$58;
   static final PyCode setNamedItemNS$59;
   static final PyCode __delitem__$60;
   static final PyCode __getstate__$61;
   static final PyCode __setstate__$62;
   static final PyCode TypeInfo$63;
   static final PyCode __init__$64;
   static final PyCode __repr__$65;
   static final PyCode _get_name$66;
   static final PyCode _get_namespace$67;
   static final PyCode Element$68;
   static final PyCode __init__$69;
   static final PyCode _get_localName$70;
   static final PyCode _get_tagName$71;
   static final PyCode unlink$72;
   static final PyCode getAttribute$73;
   static final PyCode getAttributeNS$74;
   static final PyCode setAttribute$75;
   static final PyCode setAttributeNS$76;
   static final PyCode getAttributeNode$77;
   static final PyCode getAttributeNodeNS$78;
   static final PyCode setAttributeNode$79;
   static final PyCode removeAttribute$80;
   static final PyCode removeAttributeNS$81;
   static final PyCode removeAttributeNode$82;
   static final PyCode hasAttribute$83;
   static final PyCode hasAttributeNS$84;
   static final PyCode getElementsByTagName$85;
   static final PyCode getElementsByTagNameNS$86;
   static final PyCode __repr__$87;
   static final PyCode writexml$88;
   static final PyCode _get_attributes$89;
   static final PyCode hasAttributes$90;
   static final PyCode setIdAttribute$91;
   static final PyCode setIdAttributeNS$92;
   static final PyCode setIdAttributeNode$93;
   static final PyCode _set_attribute_node$94;
   static final PyCode Childless$95;
   static final PyCode _get_firstChild$96;
   static final PyCode _get_lastChild$97;
   static final PyCode appendChild$98;
   static final PyCode hasChildNodes$99;
   static final PyCode insertBefore$100;
   static final PyCode removeChild$101;
   static final PyCode normalize$102;
   static final PyCode replaceChild$103;
   static final PyCode ProcessingInstruction$104;
   static final PyCode __init__$105;
   static final PyCode _get_data$106;
   static final PyCode _set_data$107;
   static final PyCode _get_target$108;
   static final PyCode _set_target$109;
   static final PyCode __setattr__$110;
   static final PyCode writexml$111;
   static final PyCode CharacterData$112;
   static final PyCode _get_length$113;
   static final PyCode _get_data$114;
   static final PyCode _set_data$115;
   static final PyCode __setattr__$116;
   static final PyCode __repr__$117;
   static final PyCode substringData$118;
   static final PyCode appendData$119;
   static final PyCode insertData$120;
   static final PyCode deleteData$121;
   static final PyCode replaceData$122;
   static final PyCode Text$123;
   static final PyCode splitText$124;
   static final PyCode writexml$125;
   static final PyCode _get_wholeText$126;
   static final PyCode replaceWholeText$127;
   static final PyCode _get_isWhitespaceInElementContent$128;
   static final PyCode _get_containing_element$129;
   static final PyCode _get_containing_entref$130;
   static final PyCode Comment$131;
   static final PyCode __init__$132;
   static final PyCode writexml$133;
   static final PyCode CDATASection$134;
   static final PyCode writexml$135;
   static final PyCode ReadOnlySequentialNamedNodeMap$136;
   static final PyCode __init__$137;
   static final PyCode __len__$138;
   static final PyCode _get_length$139;
   static final PyCode getNamedItem$140;
   static final PyCode getNamedItemNS$141;
   static final PyCode __getitem__$142;
   static final PyCode item$143;
   static final PyCode removeNamedItem$144;
   static final PyCode removeNamedItemNS$145;
   static final PyCode setNamedItem$146;
   static final PyCode setNamedItemNS$147;
   static final PyCode __getstate__$148;
   static final PyCode __setstate__$149;
   static final PyCode Identified$150;
   static final PyCode _identified_mixin_init$151;
   static final PyCode _get_publicId$152;
   static final PyCode _get_systemId$153;
   static final PyCode DocumentType$154;
   static final PyCode __init__$155;
   static final PyCode _get_internalSubset$156;
   static final PyCode cloneNode$157;
   static final PyCode writexml$158;
   static final PyCode Entity$159;
   static final PyCode __init__$160;
   static final PyCode _get_actualEncoding$161;
   static final PyCode _get_encoding$162;
   static final PyCode _get_version$163;
   static final PyCode appendChild$164;
   static final PyCode insertBefore$165;
   static final PyCode removeChild$166;
   static final PyCode replaceChild$167;
   static final PyCode Notation$168;
   static final PyCode __init__$169;
   static final PyCode DOMImplementation$170;
   static final PyCode hasFeature$171;
   static final PyCode createDocument$172;
   static final PyCode createDocumentType$173;
   static final PyCode getInterface$174;
   static final PyCode _create_document$175;
   static final PyCode ElementInfo$176;
   static final PyCode __init__$177;
   static final PyCode getAttributeType$178;
   static final PyCode getAttributeTypeNS$179;
   static final PyCode isElementContent$180;
   static final PyCode isEmpty$181;
   static final PyCode isId$182;
   static final PyCode isIdNS$183;
   static final PyCode __getstate__$184;
   static final PyCode __setstate__$185;
   static final PyCode _clear_id_cache$186;
   static final PyCode Document$187;
   static final PyCode __init__$188;
   static final PyCode _get_elem_info$189;
   static final PyCode _get_actualEncoding$190;
   static final PyCode _get_doctype$191;
   static final PyCode _get_documentURI$192;
   static final PyCode _get_encoding$193;
   static final PyCode _get_errorHandler$194;
   static final PyCode _get_standalone$195;
   static final PyCode _get_strictErrorChecking$196;
   static final PyCode _get_version$197;
   static final PyCode appendChild$198;
   static final PyCode removeChild$199;
   static final PyCode _get_documentElement$200;
   static final PyCode unlink$201;
   static final PyCode cloneNode$202;
   static final PyCode createDocumentFragment$203;
   static final PyCode createElement$204;
   static final PyCode createTextNode$205;
   static final PyCode createCDATASection$206;
   static final PyCode createComment$207;
   static final PyCode createProcessingInstruction$208;
   static final PyCode createAttribute$209;
   static final PyCode createElementNS$210;
   static final PyCode createAttributeNS$211;
   static final PyCode _create_entity$212;
   static final PyCode _create_notation$213;
   static final PyCode getElementById$214;
   static final PyCode getElementsByTagName$215;
   static final PyCode getElementsByTagNameNS$216;
   static final PyCode isSupported$217;
   static final PyCode importNode$218;
   static final PyCode writexml$219;
   static final PyCode renameNode$220;
   static final PyCode _clone_node$221;
   static final PyCode _nssplit$222;
   static final PyCode _get_StringIO$223;
   static final PyCode _do_pulldom_parse$224;
   static final PyCode parse$225;
   static final PyCode parseString$226;
   static final PyCode getDOMImplementation$227;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Simple implementation of the Level 1 DOM.\n\nNamespaces and other minor Level 2 features are also supported.\n\nparse(\"foo.xml\")\n\nparseString(\"<foo><bar/></foo>\")\n\nTodo:\n=====\n * convenience methods for getting elements and text.\n * more testing\n * bring some of the writer and linearizer code into conformance with this\n        interface\n * SAX 2 namespaces\n"));
      var1.setline(16);
      PyString.fromInterned("Simple implementation of the Level 1 DOM.\n\nNamespaces and other minor Level 2 features are also supported.\n\nparse(\"foo.xml\")\n\nparseString(\"<foo><bar/></foo>\")\n\nTodo:\n=====\n * convenience methods for getting elements and text.\n * more testing\n * bring some of the writer and linearizer code into conformance with this\n        interface\n * SAX 2 namespaces\n");
      var1.setline(18);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(19);
      var3 = imp.importOne("xml.dom", var1, -1);
      var1.setlocal("xml", var3);
      var3 = null;
      var1.setline(21);
      String[] var5 = new String[]{"EMPTY_NAMESPACE", "EMPTY_PREFIX", "XMLNS_NAMESPACE", "domreg"};
      PyObject[] var6 = imp.importFrom("xml.dom", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("EMPTY_NAMESPACE", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("EMPTY_PREFIX", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("XMLNS_NAMESPACE", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("domreg", var4);
      var4 = null;
      var1.setline(22);
      imp.importAll("xml.dom.minicompat", var1, -1);
      var1.setline(23);
      var5 = new String[]{"DOMImplementationLS", "DocumentLS"};
      var6 = imp.importFrom("xml.dom.xmlbuilder", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DOMImplementationLS", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("DocumentLS", var4);
      var4 = null;
      var1.setline(30);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getname("xml").__getattr__("dom").__getattr__("Node").__getattr__("ELEMENT_NODE"), var1.getname("xml").__getattr__("dom").__getattr__("Node").__getattr__("ENTITY_REFERENCE_NODE")});
      var1.setlocal("_nodeTypes_with_children", var7);
      var3 = null;
      var1.setline(34);
      var6 = new PyObject[]{var1.getname("xml").__getattr__("dom").__getattr__("Node")};
      var4 = Py.makeClass("Node", var6, Node$1);
      var1.setlocal("Node", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(271);
      PyObject var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Node"), PyString.fromInterned("firstChild"), PyString.fromInterned("First child node, or None.")};
      String[] var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(272);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Node"), PyString.fromInterned("lastChild"), PyString.fromInterned("Last child node, or None.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(273);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Node"), PyString.fromInterned("localName"), PyString.fromInterned("Namespace-local name of this node.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(276);
      var6 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var6, _append_child$23, (PyObject)null);
      var1.setlocal("_append_child", var9);
      var3 = null;
      var1.setline(286);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _in_document$24, (PyObject)null);
      var1.setlocal("_in_document", var9);
      var3 = null;
      var1.setline(294);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _write_data$25, PyString.fromInterned("Writes datachars to writer."));
      var1.setlocal("_write_data", var9);
      var3 = null;
      var1.setline(301);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _get_elements_by_tagName_helper$26, (PyObject)null);
      var1.setlocal("_get_elements_by_tagName_helper", var9);
      var3 = null;
      var1.setline(309);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _get_elements_by_tagName_ns_helper$27, (PyObject)null);
      var1.setlocal("_get_elements_by_tagName_ns_helper", var9);
      var3 = null;
      var1.setline(318);
      var6 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("DocumentFragment", var6, DocumentFragment$28);
      var1.setlocal("DocumentFragment", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(336);
      var6 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Attr", var6, Attr$30);
      var1.setlocal("Attr", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(450);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Attr"), PyString.fromInterned("isId"), PyString.fromInterned("True if this attribute is an ID.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(451);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Attr"), PyString.fromInterned("localName"), PyString.fromInterned("Namespace-local name of this attribute.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(452);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Attr"), PyString.fromInterned("schemaType"), PyString.fromInterned("Schema type for this attribute.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(455);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("NamedNodeMap", var6, NamedNodeMap$40);
      var1.setlocal("NamedNodeMap", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(604);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("NamedNodeMap"), PyString.fromInterned("length"), PyString.fromInterned("Number of nodes in the NamedNodeMap.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(607);
      var3 = var1.getname("NamedNodeMap");
      var1.setlocal("AttributeList", var3);
      var3 = null;
      var1.setline(610);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TypeInfo", var6, TypeInfo$63);
      var1.setlocal("TypeInfo", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(629);
      var3 = var1.getname("TypeInfo").__call__(var2, var1.getname("None"), var1.getname("None"));
      var1.setlocal("_no_type", var3);
      var3 = null;
      var1.setline(631);
      var6 = new PyObject[]{var1.getname("Node")};
      var4 = Py.makeClass("Element", var6, Element$68);
      var1.setlocal("Element", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(851);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Element"), PyString.fromInterned("attributes"), PyString.fromInterned("NamedNodeMap of attributes on the element.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(853);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Element"), PyString.fromInterned("localName"), PyString.fromInterned("Namespace-local name of this element.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(857);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _set_attribute_node$94, (PyObject)null);
      var1.setlocal("_set_attribute_node", var9);
      var3 = null;
      var1.setline(868);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Childless", var6, Childless$95);
      var1.setlocal("Childless", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(908);
      var6 = new PyObject[]{var1.getname("Childless"), var1.getname("Node")};
      var4 = Py.makeClass("ProcessingInstruction", var6, ProcessingInstruction$104);
      var1.setlocal("ProcessingInstruction", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(939);
      var6 = new PyObject[]{var1.getname("Childless"), var1.getname("Node")};
      var4 = Py.makeClass("CharacterData", var6, CharacterData$112);
      var1.setlocal("CharacterData", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1010);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("CharacterData"), PyString.fromInterned("length"), PyString.fromInterned("Length of the string data.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(1013);
      var6 = new PyObject[]{var1.getname("CharacterData")};
      var4 = Py.makeClass("Text", var6, Text$123);
      var1.setlocal("Text", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1103);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Text"), PyString.fromInterned("isWhitespaceInElementContent"), PyString.fromInterned("True iff this text node contains only whitespace and is in element content.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(1106);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Text"), PyString.fromInterned("wholeText"), PyString.fromInterned("The text of all logically-adjacent text nodes.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(1110);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _get_containing_element$129, (PyObject)null);
      var1.setlocal("_get_containing_element", var9);
      var3 = null;
      var1.setline(1118);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _get_containing_entref$130, (PyObject)null);
      var1.setlocal("_get_containing_entref", var9);
      var3 = null;
      var1.setline(1127);
      var6 = new PyObject[]{var1.getname("Childless"), var1.getname("CharacterData")};
      var4 = Py.makeClass("Comment", var6, Comment$131);
      var1.setlocal("Comment", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1140);
      var6 = new PyObject[]{var1.getname("Text")};
      var4 = Py.makeClass("CDATASection", var6, CDATASection$134);
      var1.setlocal("CDATASection", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1155);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ReadOnlySequentialNamedNodeMap", var6, ReadOnlySequentialNamedNodeMap$136);
      var1.setlocal("ReadOnlySequentialNamedNodeMap", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1217);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("ReadOnlySequentialNamedNodeMap"), PyString.fromInterned("length"), PyString.fromInterned("Number of entries in the NamedNodeMap.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(1221);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Identified", var6, Identified$150);
      var1.setlocal("Identified", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1237);
      var6 = new PyObject[]{var1.getname("Identified"), var1.getname("Childless"), var1.getname("Node")};
      var4 = Py.makeClass("DocumentType", var6, DocumentType$154);
      var1.setlocal("DocumentType", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1297);
      var6 = new PyObject[]{var1.getname("Identified"), var1.getname("Node")};
      var4 = Py.makeClass("Entity", var6, Entity$159);
      var1.setlocal("Entity", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1337);
      var6 = new PyObject[]{var1.getname("Identified"), var1.getname("Childless"), var1.getname("Node")};
      var4 = Py.makeClass("Notation", var6, Notation$168);
      var1.setlocal("Notation", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1346);
      var6 = new PyObject[]{var1.getname("DOMImplementationLS")};
      var4 = Py.makeClass("DOMImplementation", var6, DOMImplementation$170);
      var1.setlocal("DOMImplementation", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1425);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ElementInfo", var6, ElementInfo$176);
      var1.setlocal("ElementInfo", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1467);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _clear_id_cache$186, (PyObject)null);
      var1.setlocal("_clear_id_cache", var9);
      var3 = null;
      var1.setline(1475);
      var6 = new PyObject[]{var1.getname("Node"), var1.getname("DocumentLS")};
      var4 = Py.makeClass("Document", var6, Document$187);
      var1.setlocal("Document", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1809);
      var10000 = var1.getname("defproperty");
      var6 = new PyObject[]{var1.getname("Document"), PyString.fromInterned("documentElement"), PyString.fromInterned("Top-level element of this document.")};
      var8 = new String[]{"doc"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(1813);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _clone_node$221, PyString.fromInterned("\n    Clone a node and give it the new owner document.\n    Called by Node.cloneNode and Document.importNode\n    "));
      var1.setlocal("_clone_node", var9);
      var3 = null;
      var1.setline(1895);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _nssplit$222, (PyObject)null);
      var1.setlocal("_nssplit", var9);
      var3 = null;
      var1.setline(1903);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _get_StringIO$223, (PyObject)null);
      var1.setlocal("_get_StringIO", var9);
      var3 = null;
      var1.setline(1908);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _do_pulldom_parse$224, (PyObject)null);
      var1.setlocal("_do_pulldom_parse", var9);
      var3 = null;
      var1.setline(1916);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, parse$225, PyString.fromInterned("Parse a file into a DOM by filename or file object."));
      var1.setlocal("parse", var9);
      var3 = null;
      var1.setline(1926);
      var6 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, parseString$226, PyString.fromInterned("Parse a file into a DOM from a string."));
      var1.setlocal("parseString", var9);
      var3 = null;
      var1.setline(1936);
      var6 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var6, getDOMImplementation$227, (PyObject)null);
      var1.setlocal("getDOMImplementation", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Node$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(35);
      PyObject var3 = var1.getname("None");
      var1.setlocal("namespaceURI", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getname("None");
      var1.setlocal("parentNode", var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getname("None");
      var1.setlocal("ownerDocument", var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getname("None");
      var1.setlocal("nextSibling", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getname("None");
      var1.setlocal("previousSibling", var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getname("EMPTY_PREFIX");
      var1.setlocal("prefix", var3);
      var3 = null;
      var1.setline(43);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __nonzero__$2, (PyObject)null);
      var1.setlocal("__nonzero__", var5);
      var3 = null;
      var1.setline(46);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, toxml$3, (PyObject)null);
      var1.setlocal("toxml", var5);
      var3 = null;
      var1.setline(49);
      var4 = new PyObject[]{PyString.fromInterned("\t"), PyString.fromInterned("\n"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, toprettyxml$4, (PyObject)null);
      var1.setlocal("toprettyxml", var5);
      var3 = null;
      var1.setline(64);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, hasChildNodes$5, (PyObject)null);
      var1.setlocal("hasChildNodes", var5);
      var3 = null;
      var1.setline(70);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_childNodes$6, (PyObject)null);
      var1.setlocal("_get_childNodes", var5);
      var3 = null;
      var1.setline(73);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_firstChild$7, (PyObject)null);
      var1.setlocal("_get_firstChild", var5);
      var3 = null;
      var1.setline(77);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_lastChild$8, (PyObject)null);
      var1.setlocal("_get_lastChild", var5);
      var3 = null;
      var1.setline(81);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, insertBefore$9, (PyObject)null);
      var1.setlocal("insertBefore", var5);
      var3 = null;
      var1.setline(113);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, appendChild$10, (PyObject)null);
      var1.setlocal("appendChild", var5);
      var3 = null;
      var1.setline(130);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, replaceChild$11, (PyObject)null);
      var1.setlocal("replaceChild", var5);
      var3 = null;
      var1.setline(162);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeChild$12, (PyObject)null);
      var1.setlocal("removeChild", var5);
      var3 = null;
      var1.setline(178);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, normalize$13, (PyObject)null);
      var1.setlocal("normalize", var5);
      var3 = null;
      var1.setline(205);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, cloneNode$14, (PyObject)null);
      var1.setlocal("cloneNode", var5);
      var3 = null;
      var1.setline(208);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isSupported$15, (PyObject)null);
      var1.setlocal("isSupported", var5);
      var3 = null;
      var1.setline(211);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_localName$16, (PyObject)null);
      var1.setlocal("_get_localName", var5);
      var3 = null;
      var1.setline(217);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isSameNode$17, (PyObject)null);
      var1.setlocal("isSameNode", var5);
      var3 = null;
      var1.setline(220);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getInterface$18, (PyObject)null);
      var1.setlocal("getInterface", var5);
      var3 = null;
      var1.setline(230);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getUserData$19, (PyObject)null);
      var1.setlocal("getUserData", var5);
      var3 = null;
      var1.setline(236);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setUserData$20, (PyObject)null);
      var1.setlocal("setUserData", var5);
      var3 = null;
      var1.setline(254);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _call_user_data_handler$21, (PyObject)null);
      var1.setlocal("_call_user_data_handler", var5);
      var3 = null;
      var1.setline(262);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unlink$22, (PyObject)null);
      var1.setlocal("unlink", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __nonzero__$2(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject toxml$3(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(0).__getattr__("toprettyxml").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject toprettyxml$4(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("_get_StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(54);
         var3 = imp.importOne("codecs", var1, -1);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(56);
         var3 = var1.getlocal(5).__getattr__("lookup").__call__(var2, var1.getlocal(3)).__getitem__(Py.newInteger(3)).__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(57);
      var3 = var1.getlocal(0).__getattr__("nodeType");
      var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_NODE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(59);
         var10000 = var1.getlocal(0).__getattr__("writexml");
         PyObject[] var4 = new PyObject[]{var1.getlocal(4), PyString.fromInterned(""), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
         var10000.__call__(var2, var4);
      } else {
         var1.setline(61);
         var1.getlocal(0).__getattr__("writexml").__call__(var2, var1.getlocal(4), PyString.fromInterned(""), var1.getlocal(1), var1.getlocal(2));
      }

      var1.setline(62);
      var3 = var1.getlocal(4).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hasChildNodes$5(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("childNodes").__nonzero__()) {
         var1.setline(66);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(68);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_childNodes$6(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("childNodes");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_firstChild$7(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      if (var1.getlocal(0).__getattr__("childNodes").__nonzero__()) {
         var1.setline(75);
         PyObject var3 = var1.getlocal(0).__getattr__("childNodes").__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_lastChild$8(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      if (var1.getlocal(0).__getattr__("childNodes").__nonzero__()) {
         var1.setline(79);
         PyObject var3 = var1.getlocal(0).__getattr__("childNodes").__getitem__(Py.newInteger(-1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject insertBefore$9(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(1).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("DOCUMENT_FRAGMENT_NODE"));
      var3 = null;
      PyObject var4;
      if (!var10000.__nonzero__()) {
         var1.setline(87);
         var4 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var4._notin(var1.getlocal(0).__getattr__("_child_node_types"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(88);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, PyString.fromInterned("%s cannot be child of %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(0))}))));
         } else {
            var1.setline(90);
            var4 = var1.getlocal(1).__getattr__("parentNode");
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(91);
               var1.getlocal(1).__getattr__("parentNode").__getattr__("removeChild").__call__(var2, var1.getlocal(1));
            }

            var1.setline(92);
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(93);
               var1.getlocal(0).__getattr__("appendChild").__call__(var2, var1.getlocal(1));
            } else {
               try {
                  var1.setline(96);
                  var4 = var1.getlocal(0).__getattr__("childNodes").__getattr__("index").__call__(var2, var1.getlocal(2));
                  var1.setlocal(4, var4);
                  var4 = null;
               } catch (Throwable var5) {
                  PyException var6 = Py.setException(var5, var1);
                  if (var6.match(var1.getglobal("ValueError"))) {
                     var1.setline(98);
                     throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
                  }

                  throw var6;
               }

               var1.setline(99);
               var4 = var1.getlocal(1).__getattr__("nodeType");
               var10000 = var4._in(var1.getglobal("_nodeTypes_with_children"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(100);
                  var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
               }

               var1.setline(101);
               var1.getlocal(0).__getattr__("childNodes").__getattr__("insert").__call__(var2, var1.getlocal(4), var1.getlocal(1));
               var1.setline(102);
               var4 = var1.getlocal(2);
               var1.getlocal(1).__setattr__("nextSibling", var4);
               var4 = null;
               var1.setline(103);
               var4 = var1.getlocal(1);
               var1.getlocal(2).__setattr__("previousSibling", var4);
               var4 = null;
               var1.setline(104);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(105);
                  var4 = var1.getlocal(0).__getattr__("childNodes").__getitem__(var1.getlocal(4)._sub(Py.newInteger(1)));
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(106);
                  var4 = var1.getlocal(1);
                  var1.getlocal(5).__setattr__("nextSibling", var4);
                  var4 = null;
                  var1.setline(107);
                  var4 = var1.getlocal(5);
                  var1.getlocal(1).__setattr__("previousSibling", var4);
                  var4 = null;
               } else {
                  var1.setline(109);
                  var4 = var1.getglobal("None");
                  var1.getlocal(1).__setattr__("previousSibling", var4);
                  var4 = null;
               }

               var1.setline(110);
               var4 = var1.getlocal(0);
               var1.getlocal(1).__setattr__("parentNode", var4);
               var4 = null;
            }

            var1.setline(111);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(83);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getattr__("childNodes")).__iter__();

         while(true) {
            var1.setline(83);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(86);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var4);
            var1.setline(84);
            var1.getlocal(0).__getattr__("insertBefore").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         }
      }
   }

   public PyObject appendChild$10(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = var1.getlocal(1).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("DOCUMENT_FRAGMENT_NODE"));
      var3 = null;
      PyObject var4;
      if (!var10000.__nonzero__()) {
         var1.setline(119);
         var4 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var4._notin(var1.getlocal(0).__getattr__("_child_node_types"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(120);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, PyString.fromInterned("%s cannot be child of %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(0))}))));
         } else {
            var1.setline(122);
            var4 = var1.getlocal(1).__getattr__("nodeType");
            var10000 = var4._in(var1.getglobal("_nodeTypes_with_children"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(123);
               var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
            }

            var1.setline(124);
            var4 = var1.getlocal(1).__getattr__("parentNode");
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(125);
               var1.getlocal(1).__getattr__("parentNode").__getattr__("removeChild").__call__(var2, var1.getlocal(1));
            }

            var1.setline(126);
            var1.getglobal("_append_child").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setline(127);
            var4 = var1.getglobal("None");
            var1.getlocal(1).__setattr__("nextSibling", var4);
            var4 = null;
            var1.setline(128);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(115);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getattr__("childNodes")).__iter__();

         while(true) {
            var1.setline(115);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(118);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var4);
            var1.setline(116);
            var1.getlocal(0).__getattr__("appendChild").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject replaceChild$11(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getlocal(1).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("DOCUMENT_FRAGMENT_NODE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         var3 = var1.getlocal(2).__getattr__("nextSibling");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(133);
         var1.getlocal(0).__getattr__("removeChild").__call__(var2, var1.getlocal(2));
         var1.setline(134);
         var3 = var1.getlocal(0).__getattr__("insertBefore").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(135);
         PyObject var4 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var4._notin(var1.getlocal(0).__getattr__("_child_node_types"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(136);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, PyString.fromInterned("%s cannot be child of %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(0))}))));
         } else {
            var1.setline(138);
            var4 = var1.getlocal(1);
            var10000 = var4._is(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(139);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(140);
               var4 = var1.getlocal(1).__getattr__("parentNode");
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(141);
                  var1.getlocal(1).__getattr__("parentNode").__getattr__("removeChild").__call__(var2, var1.getlocal(1));
               }

               try {
                  var1.setline(143);
                  var4 = var1.getlocal(0).__getattr__("childNodes").__getattr__("index").__call__(var2, var1.getlocal(2));
                  var1.setlocal(4, var4);
                  var4 = null;
               } catch (Throwable var5) {
                  PyException var6 = Py.setException(var5, var1);
                  if (var6.match(var1.getglobal("ValueError"))) {
                     var1.setline(145);
                     throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
                  }

                  throw var6;
               }

               var1.setline(146);
               var4 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("childNodes").__setitem__(var1.getlocal(4), var4);
               var4 = null;
               var1.setline(147);
               var4 = var1.getlocal(0);
               var1.getlocal(1).__setattr__("parentNode", var4);
               var4 = null;
               var1.setline(148);
               var4 = var1.getglobal("None");
               var1.getlocal(2).__setattr__("parentNode", var4);
               var4 = null;
               var1.setline(149);
               var4 = var1.getlocal(1).__getattr__("nodeType");
               var10000 = var4._in(var1.getglobal("_nodeTypes_with_children"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(2).__getattr__("nodeType");
                  var10000 = var4._in(var1.getglobal("_nodeTypes_with_children"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(151);
                  var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
               }

               var1.setline(152);
               var4 = var1.getlocal(2).__getattr__("nextSibling");
               var1.getlocal(1).__setattr__("nextSibling", var4);
               var4 = null;
               var1.setline(153);
               var4 = var1.getlocal(2).__getattr__("previousSibling");
               var1.getlocal(1).__setattr__("previousSibling", var4);
               var4 = null;
               var1.setline(154);
               var4 = var1.getglobal("None");
               var1.getlocal(2).__setattr__("nextSibling", var4);
               var4 = null;
               var1.setline(155);
               var4 = var1.getglobal("None");
               var1.getlocal(2).__setattr__("previousSibling", var4);
               var4 = null;
               var1.setline(156);
               if (var1.getlocal(1).__getattr__("previousSibling").__nonzero__()) {
                  var1.setline(157);
                  var4 = var1.getlocal(1);
                  var1.getlocal(1).__getattr__("previousSibling").__setattr__("nextSibling", var4);
                  var4 = null;
               }

               var1.setline(158);
               if (var1.getlocal(1).__getattr__("nextSibling").__nonzero__()) {
                  var1.setline(159);
                  var4 = var1.getlocal(1);
                  var1.getlocal(1).__getattr__("nextSibling").__setattr__("previousSibling", var4);
                  var4 = null;
               }

               var1.setline(160);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject removeChild$12(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(164);
         var1.getlocal(0).__getattr__("childNodes").__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("ValueError"))) {
            var1.setline(166);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
         }

         throw var3;
      }

      var1.setline(167);
      PyObject var5 = var1.getlocal(1).__getattr__("nextSibling");
      PyObject var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(168);
         var5 = var1.getlocal(1).__getattr__("previousSibling");
         var1.getlocal(1).__getattr__("nextSibling").__setattr__("previousSibling", var5);
         var3 = null;
      }

      var1.setline(169);
      var5 = var1.getlocal(1).__getattr__("previousSibling");
      var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(170);
         var5 = var1.getlocal(1).__getattr__("nextSibling");
         var1.getlocal(1).__getattr__("previousSibling").__setattr__("nextSibling", var5);
         var3 = null;
      }

      var1.setline(171);
      var5 = var1.getglobal("None");
      var1.getlocal(1).__setattr__("nextSibling", var5);
      var1.getlocal(1).__setattr__("previousSibling", var5);
      var1.setline(172);
      var5 = var1.getlocal(1).__getattr__("nodeType");
      var10000 = var5._in(var1.getglobal("_nodeTypes_with_children"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
      }

      var1.setline(175);
      var5 = var1.getglobal("None");
      var1.getlocal(1).__setattr__("parentNode", var5);
      var3 = null;
      var1.setline(176);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject normalize$13(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(180);
      PyObject var6 = var1.getlocal(0).__getattr__("childNodes").__iter__();

      while(true) {
         var1.setline(180);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(203);
            var6 = var1.getlocal(1);
            var1.getlocal(0).__getattr__("childNodes").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var6);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(181);
         PyObject var5 = var1.getlocal(2).__getattr__("nodeType");
         PyObject var10000 = var5._eq(var1.getglobal("Node").__getattr__("TEXT_NODE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(182);
            if (var1.getlocal(2).__getattr__("data").__not__().__nonzero__()) {
               var1.setline(184);
               if (var1.getlocal(1).__nonzero__()) {
                  var1.setline(185);
                  var5 = var1.getlocal(2).__getattr__("nextSibling");
                  var1.getlocal(1).__getitem__(Py.newInteger(-1)).__setattr__("nextSibling", var5);
                  var5 = null;
               }

               var1.setline(186);
               if (var1.getlocal(2).__getattr__("nextSibling").__nonzero__()) {
                  var1.setline(187);
                  var5 = var1.getlocal(2).__getattr__("previousSibling");
                  var1.getlocal(2).__getattr__("nextSibling").__setattr__("previousSibling", var5);
                  var5 = null;
               }

               var1.setline(188);
               var1.getlocal(2).__getattr__("unlink").__call__(var2);
            } else {
               var1.setline(189);
               var10000 = var1.getlocal(1);
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getattr__("nodeType");
                  var10000 = var5._eq(var1.getlocal(2).__getattr__("nodeType"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(191);
                  var5 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(192);
                  var5 = var1.getlocal(3).__getattr__("data")._add(var1.getlocal(2).__getattr__("data"));
                  var1.getlocal(3).__setattr__("data", var5);
                  var5 = null;
                  var1.setline(193);
                  var5 = var1.getlocal(2).__getattr__("nextSibling");
                  var1.getlocal(3).__setattr__("nextSibling", var5);
                  var5 = null;
                  var1.setline(194);
                  if (var1.getlocal(2).__getattr__("nextSibling").__nonzero__()) {
                     var1.setline(195);
                     var5 = var1.getlocal(3);
                     var1.getlocal(2).__getattr__("nextSibling").__setattr__("previousSibling", var5);
                     var5 = null;
                  }

                  var1.setline(196);
                  var1.getlocal(2).__getattr__("unlink").__call__(var2);
               } else {
                  var1.setline(198);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
               }
            }
         } else {
            var1.setline(200);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
            var1.setline(201);
            var5 = var1.getlocal(2).__getattr__("nodeType");
            var10000 = var5._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(202);
               var1.getlocal(2).__getattr__("normalize").__call__(var2);
            }
         }
      }
   }

   public PyObject cloneNode$14(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyObject var10000 = var1.getglobal("_clone_node");
      PyObject var10002 = var1.getlocal(0);
      PyObject var10003 = var1.getlocal(1);
      PyObject var10004 = var1.getlocal(0).__getattr__("ownerDocument");
      if (!var10004.__nonzero__()) {
         var10004 = var1.getlocal(0);
      }

      PyObject var3 = var10000.__call__(var2, var10002, var10003, var10004);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isSupported$15(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getlocal(0).__getattr__("ownerDocument").__getattr__("implementation").__getattr__("hasFeature").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_localName$16(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isSameNode$17(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getInterface$18(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("isSupported").__call__(var2, var1.getlocal(1), var1.getglobal("None")).__nonzero__()) {
         var1.setline(222);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(224);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getUserData$19(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(232);
         var3 = var1.getlocal(0).__getattr__("_user_data").__getitem__(var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("KeyError")}))) {
            var1.setline(234);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject setUserData$20(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;

      try {
         var1.setline(239);
         var3 = var1.getlocal(0).__getattr__("_user_data");
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("AttributeError"))) {
            throw var6;
         }

         var1.setline(241);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(242);
         PyObject var7 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("_user_data", var7);
         var4 = null;
      }

      var1.setline(243);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(244);
         var3 = var1.getlocal(5).__getitem__(var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(245);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(247);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(248);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(249);
            var1.getlocal(5).__delitem__(var1.getlocal(1));
         }
      } else {
         var1.setline(251);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.getlocal(5).__setitem__((PyObject)var1.getlocal(1), var8);
         var3 = null;
      }

      var1.setline(252);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _call_user_data_handler$21(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_user_data")).__nonzero__()) {
         var1.setline(256);
         PyObject var3 = var1.getlocal(0).__getattr__("_user_data").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(256);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(5, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(6, var8);
            var8 = null;
            var6 = null;
            var1.setline(257);
            PyObject var9 = var1.getlocal(6);
            PyObject var10000 = var9._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(258);
               var10000 = var1.getlocal(6).__getattr__("handle");
               var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(5), var1.getlocal(2), var1.getlocal(3)};
               var10000.__call__(var2, var5);
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unlink$22(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("parentNode", var3);
      var1.getlocal(0).__setattr__("ownerDocument", var3);
      var1.setline(264);
      if (var1.getlocal(0).__getattr__("childNodes").__nonzero__()) {
         var1.setline(265);
         var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

         while(true) {
            var1.setline(265);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(267);
               var3 = var1.getglobal("NodeList").__call__(var2);
               var1.getlocal(0).__setattr__("childNodes", var3);
               var3 = null;
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(266);
            var1.getlocal(1).__getattr__("unlink").__call__(var2);
         }
      }

      var1.setline(268);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("previousSibling", var3);
      var3 = null;
      var1.setline(269);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("nextSibling", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _append_child$23(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getlocal(0).__getattr__("childNodes");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(279);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(280);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(281);
         var3 = var1.getlocal(3);
         var1.getlocal(1).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("previousSibling"), var3);
         var3 = null;
         var1.setline(282);
         var3 = var1.getlocal(1);
         var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("nextSibling"), var3);
         var3 = null;
      }

      var1.setline(283);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(284);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("parentNode"), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _in_document$24(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(288);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         PyObject var3;
         if (!var10000.__nonzero__()) {
            var1.setline(292);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(289);
         var3 = var1.getlocal(0).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_NODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(290);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(291);
         var4 = var1.getlocal(0).__getattr__("parentNode");
         var1.setlocal(0, var4);
         var4 = null;
      }
   }

   public PyObject _write_data$25(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyString.fromInterned("Writes datachars to writer.");
      var1.setline(296);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(297);
         PyObject var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(299);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_elements_by_tagName_helper$26(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyObject var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

      while(true) {
         var1.setline(302);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(307);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(303);
         PyObject var5 = var1.getlocal(3).__getattr__("nodeType");
         PyObject var10000 = var5._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(1);
            var10000 = var5._eq(PyString.fromInterned("*"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(3).__getattr__("tagName");
               var10000 = var5._eq(var1.getlocal(1));
               var5 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(305);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         }

         var1.setline(306);
         var1.getglobal("_get_elements_by_tagName_helper").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      }
   }

   public PyObject _get_elements_by_tagName_ns_helper$27(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyObject var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

      while(true) {
         var1.setline(310);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(316);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(311);
         PyObject var5 = var1.getlocal(4).__getattr__("nodeType");
         PyObject var10000 = var5._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(312);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(PyString.fromInterned("*"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(4).__getattr__("localName");
               var10000 = var5._eq(var1.getlocal(2));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(1);
               var10000 = var5._eq(PyString.fromInterned("*"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var5 = var1.getlocal(4).__getattr__("namespaceURI");
                  var10000 = var5._eq(var1.getlocal(1));
                  var5 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(314);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
            }

            var1.setline(315);
            var1.getglobal("_get_elements_by_tagName_ns_helper").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         }
      }
   }

   public PyObject DocumentFragment$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(319);
      PyObject var3 = var1.getname("Node").__getattr__("DOCUMENT_FRAGMENT_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(320);
      PyString var4 = PyString.fromInterned("#document-fragment");
      var1.setlocal("nodeName", var4);
      var3 = null;
      var1.setline(321);
      var3 = var1.getname("None");
      var1.setlocal("nodeValue", var3);
      var3 = null;
      var1.setline(322);
      var3 = var1.getname("None");
      var1.setlocal("attributes", var3);
      var3 = null;
      var1.setline(323);
      var3 = var1.getname("None");
      var1.setlocal("parentNode", var3);
      var3 = null;
      var1.setline(324);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getname("Node").__getattr__("ELEMENT_NODE"), var1.getname("Node").__getattr__("TEXT_NODE"), var1.getname("Node").__getattr__("CDATA_SECTION_NODE"), var1.getname("Node").__getattr__("ENTITY_REFERENCE_NODE"), var1.getname("Node").__getattr__("PROCESSING_INSTRUCTION_NODE"), var1.getname("Node").__getattr__("COMMENT_NODE"), var1.getname("Node").__getattr__("NOTATION_NODE")});
      var1.setlocal("_child_node_types", var5);
      var3 = null;
      var1.setline(332);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$29, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      PyObject var3 = var1.getglobal("NodeList").__call__(var2);
      var1.getlocal(0).__setattr__("childNodes", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Attr$30(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(337);
      PyObject var3 = var1.getname("Node").__getattr__("ATTRIBUTE_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(338);
      var3 = var1.getname("None");
      var1.setlocal("attributes", var3);
      var3 = null;
      var1.setline(339);
      var3 = var1.getname("None");
      var1.setlocal("ownerElement", var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getname("False");
      var1.setlocal("specified", var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getname("False");
      var1.setlocal("_is_id", var3);
      var3 = null;
      var1.setline(343);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getname("Node").__getattr__("TEXT_NODE"), var1.getname("Node").__getattr__("ENTITY_REFERENCE_NODE")});
      var1.setlocal("_child_node_types", var4);
      var3 = null;
      var1.setline(345);
      PyObject[] var5 = new PyObject[]{var1.getname("EMPTY_NAMESPACE"), var1.getname("None"), var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$31, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(359);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_localName$32, (PyObject)null);
      var1.setlocal("_get_localName", var6);
      var3 = null;
      var1.setline(362);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_specified$33, (PyObject)null);
      var1.setlocal("_get_specified", var6);
      var3 = null;
      var1.setline(365);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __setattr__$34, (PyObject)null);
      var1.setlocal("__setattr__", var6);
      var3 = null;
      var1.setline(380);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _set_prefix$35, (PyObject)null);
      var1.setlocal("_set_prefix", var6);
      var3 = null;
      var1.setline(396);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _set_value$36, (PyObject)null);
      var1.setlocal("_set_value", var6);
      var3 = null;
      var1.setline(403);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unlink$37, (PyObject)null);
      var1.setlocal("unlink", var6);
      var3 = null;
      var1.setline(420);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_isId$38, (PyObject)null);
      var1.setlocal("_get_isId", var6);
      var3 = null;
      var1.setline(436);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_schemaType$39, (PyObject)null);
      var1.setlocal("_get_schemaType", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$31(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(349);
      var3 = var1.getlocal(1);
      var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
      var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("name"), var3);
      var1.setline(350);
      var3 = var1.getlocal(2);
      var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("namespaceURI"), var3);
      var3 = null;
      var1.setline(351);
      var3 = var1.getlocal(4);
      var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
      var3 = null;
      var1.setline(352);
      var3 = var1.getglobal("NodeList").__call__(var2);
      var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("childNodes"), var3);
      var3 = null;
      var1.setline(355);
      var1.getlocal(0).__getattr__("childNodes").__getattr__("append").__call__(var2, var1.getglobal("Text").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_localName$32(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyObject var3 = var1.getlocal(0).__getattr__("nodeName").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(-1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_specified$33(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyObject var3 = var1.getlocal(0).__getattr__("specified");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setattr__$34(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(367);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("value"), PyString.fromInterned("nodeValue")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(368);
         var3 = var1.getlocal(2);
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("value"), var3);
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(369);
         var3 = var1.getlocal(0).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__getattr__("__dict__");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(370);
         var3 = var1.getlocal(2);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(371);
         var3 = var1.getlocal(0).__getattr__("ownerElement");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(372);
            var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0).__getattr__("ownerElement"));
         }
      } else {
         var1.setline(373);
         var3 = var1.getlocal(1);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("nodeName")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(374);
            var3 = var1.getlocal(2);
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("name"), var3);
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
            var1.setline(375);
            var3 = var1.getlocal(0).__getattr__("ownerElement");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(376);
               var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0).__getattr__("ownerElement"));
            }
         } else {
            var1.setline(378);
            var3 = var1.getlocal(2);
            var1.getlocal(3).__setitem__(var1.getlocal(1), var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_prefix$35(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getlocal(0).__getattr__("namespaceURI");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(382);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("xmlns"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(383);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._ne(var1.getglobal("XMLNS_NAMESPACE"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(384);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NamespaceErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal use of 'xmlns' prefix for the wrong namespace")));
         }
      }

      var1.setline(386);
      var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(387);
      var3 = var1.getlocal(1);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
      var3 = null;
      var1.setline(388);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(389);
         var3 = var1.getlocal(0).__getattr__("localName");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(391);
         var3 = PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("localName")}));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(392);
      if (var1.getlocal(0).__getattr__("ownerElement").__nonzero__()) {
         var1.setline(393);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0).__getattr__("ownerElement"));
      }

      var1.setline(394);
      var3 = var1.getlocal(4);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("name"), var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_value$36(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(398);
      var3 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("value"), var3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
      var1.setline(399);
      if (var1.getlocal(0).__getattr__("ownerElement").__nonzero__()) {
         var1.setline(400);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0).__getattr__("ownerElement"));
      }

      var1.setline(401);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unlink$37(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = var1.getlocal(0).__getattr__("ownerElement");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(409);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(410);
         var1.getlocal(1).__getattr__("_attrs").__delitem__(var1.getlocal(0).__getattr__("nodeName"));
         var1.setline(411);
         var1.getlocal(1).__getattr__("_attrsNS").__delitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("namespaceURI"), var1.getlocal(0).__getattr__("localName")})));
         var1.setline(412);
         if (var1.getlocal(0).__getattr__("_is_id").__nonzero__()) {
            var1.setline(413);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("_is_id", var3);
            var3 = null;
            var1.setline(414);
            var10000 = var1.getlocal(1);
            String var6 = "_magic_id_nodes";
            var4 = var10000;
            PyObject var5 = var4.__getattr__(var6);
            var5 = var5._isub(Py.newInteger(1));
            var4.__setattr__(var6, var5);
            var1.setline(415);
            var10000 = var1.getlocal(0).__getattr__("ownerDocument");
            var6 = "_magic_id_count";
            var4 = var10000;
            var5 = var4.__getattr__(var6);
            var5 = var5._isub(Py.newInteger(1));
            var4.__setattr__(var6, var5);
         }
      }

      var1.setline(416);
      var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

      while(true) {
         var1.setline(416);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(418);
            var1.getlocal(0).__getattr__("childNodes").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(417);
         var1.getlocal(2).__getattr__("unlink").__call__(var2);
      }
   }

   public PyObject _get_isId$38(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_is_id").__nonzero__()) {
         var1.setline(422);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(423);
         PyObject var4 = var1.getlocal(0).__getattr__("ownerDocument");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(424);
         var4 = var1.getlocal(0).__getattr__("ownerElement");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(425);
         var4 = var1.getlocal(1);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(426);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(428);
            var4 = var1.getlocal(1).__getattr__("_get_elem_info").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(429);
            var4 = var1.getlocal(3);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(430);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(431);
               if (var1.getlocal(0).__getattr__("namespaceURI").__nonzero__()) {
                  var1.setline(432);
                  var3 = var1.getlocal(3).__getattr__("isIdNS").__call__(var2, var1.getlocal(0).__getattr__("namespaceURI"), var1.getlocal(0).__getattr__("localName"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(434);
                  var3 = var1.getlocal(3).__getattr__("isId").__call__(var2, var1.getlocal(0).__getattr__("nodeName"));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _get_schemaType$39(PyFrame var1, ThreadState var2) {
      var1.setline(437);
      PyObject var3 = var1.getlocal(0).__getattr__("ownerDocument");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(438);
      var3 = var1.getlocal(0).__getattr__("ownerElement");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(439);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(440);
         var3 = var1.getglobal("_no_type");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(442);
         PyObject var4 = var1.getlocal(1).__getattr__("_get_elem_info").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(443);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(444);
            var3 = var1.getglobal("_no_type");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(445);
            if (var1.getlocal(0).__getattr__("namespaceURI").__nonzero__()) {
               var1.setline(446);
               var3 = var1.getlocal(3).__getattr__("getAttributeTypeNS").__call__(var2, var1.getlocal(0).__getattr__("namespaceURI"), var1.getlocal(0).__getattr__("localName"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(448);
               var3 = var1.getlocal(3).__getattr__("getAttributeType").__call__(var2, var1.getlocal(0).__getattr__("nodeName"));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject NamedNodeMap$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The attribute list is a transient interface to the underlying\n    dictionaries.  Mutations here will change the underlying element's\n    dictionary.\n\n    Ordering is imposed artificially and does not reflect the order of\n    attributes as found in an input document.\n    "));
      var1.setline(462);
      PyString.fromInterned("The attribute list is a transient interface to the underlying\n    dictionaries.  Mutations here will change the underlying element's\n    dictionary.\n\n    Ordering is imposed artificially and does not reflect the order of\n    attributes as found in an input document.\n    ");
      var1.setline(464);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_attrs"), PyString.fromInterned("_attrsNS"), PyString.fromInterned("_ownerElement")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(466);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$41, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(471);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_length$42, (PyObject)null);
      var1.setlocal("_get_length", var5);
      var3 = null;
      var1.setline(474);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, item$43, (PyObject)null);
      var1.setlocal("item", var5);
      var3 = null;
      var1.setline(480);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, items$44, (PyObject)null);
      var1.setlocal("items", var5);
      var3 = null;
      var1.setline(486);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, itemsNS$45, (PyObject)null);
      var1.setlocal("itemsNS", var5);
      var3 = null;
      var1.setline(492);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, has_key$46, (PyObject)null);
      var1.setlocal("has_key", var5);
      var3 = null;
      var1.setline(498);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, keys$47, (PyObject)null);
      var1.setlocal("keys", var5);
      var3 = null;
      var1.setline(501);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, keysNS$48, (PyObject)null);
      var1.setlocal("keysNS", var5);
      var3 = null;
      var1.setline(504);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, values$49, (PyObject)null);
      var1.setlocal("values", var5);
      var3 = null;
      var1.setline(507);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, get$50, (PyObject)null);
      var1.setlocal("get", var5);
      var3 = null;
      var1.setline(510);
      PyObject var6 = var1.getname("_get_length");
      var1.setlocal("__len__", var6);
      var3 = null;
      var1.setline(512);
      var6 = var1.getname("None");
      var1.setlocal("__hash__", var6);
      var3 = null;
      var1.setline(513);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __cmp__$51, (PyObject)null);
      var1.setlocal("__cmp__", var5);
      var3 = null;
      var1.setline(519);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getitem__$52, (PyObject)null);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(526);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setitem__$53, (PyObject)null);
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(541);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getNamedItem$54, (PyObject)null);
      var1.setlocal("getNamedItem", var5);
      var3 = null;
      var1.setline(547);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getNamedItemNS$55, (PyObject)null);
      var1.setlocal("getNamedItemNS", var5);
      var3 = null;
      var1.setline(553);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeNamedItem$56, (PyObject)null);
      var1.setlocal("removeNamedItem", var5);
      var3 = null;
      var1.setline(565);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeNamedItemNS$57, (PyObject)null);
      var1.setlocal("removeNamedItemNS", var5);
      var3 = null;
      var1.setline(577);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setNamedItem$58, (PyObject)null);
      var1.setlocal("setNamedItem", var5);
      var3 = null;
      var1.setline(590);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setNamedItemNS$59, (PyObject)null);
      var1.setlocal("setNamedItemNS", var5);
      var3 = null;
      var1.setline(593);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __delitem__$60, (PyObject)null);
      var1.setlocal("__delitem__", var5);
      var3 = null;
      var1.setline(598);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$61, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(601);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$62, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_attrs", var3);
      var3 = null;
      var1.setline(468);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_attrsNS", var3);
      var3 = null;
      var1.setline(469);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_ownerElement", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_length$42(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_attrs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject item$43(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(476);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(0).__getattr__("_attrs").__getattr__("keys").__call__(var2).__getitem__(var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("IndexError"))) {
            var1.setline(478);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject items$44(PyFrame var1, ThreadState var2) {
      var1.setline(481);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(482);
      PyObject var5 = var1.getlocal(0).__getattr__("_attrs").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(482);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(484);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(483);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("nodeName"), var1.getlocal(2).__getattr__("value")})));
      }
   }

   public PyObject itemsNS$45(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(488);
      PyObject var5 = var1.getlocal(0).__getattr__("_attrs").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(488);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(490);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(489);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("namespaceURI"), var1.getlocal(2).__getattr__("localName")}), var1.getlocal(2).__getattr__("value")})));
      }
   }

   public PyObject has_key$46(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringTypes")).__nonzero__()) {
         var1.setline(494);
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_attrs"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(496);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._in(var1.getlocal(0).__getattr__("_attrsNS"));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject keys$47(PyFrame var1, ThreadState var2) {
      var1.setline(499);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject keysNS$48(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrsNS").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$49(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$50(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$51(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs");
      PyObject var10000 = var3._is(var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("_attrs"), (PyObject)var1.getglobal("None")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(515);
         PyInteger var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(517);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getglobal("id").__call__(var2, var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __getitem__$52(PyFrame var1, ThreadState var2) {
      var1.setline(520);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(521);
         var3 = var1.getlocal(0).__getattr__("_attrsNS").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(523);
         var3 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __setitem__$53(PyFrame var1, ThreadState var2) {
      var1.setline(527);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("StringTypes")).__nonzero__()) {
         try {
            var1.setline(529);
            var3 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("KeyError"))) {
               throw var6;
            }

            var1.setline(531);
            PyObject var4 = var1.getglobal("Attr").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(532);
            var4 = var1.getlocal(0).__getattr__("_ownerElement").__getattr__("ownerDocument");
            var1.getlocal(3).__setattr__("ownerDocument", var4);
            var4 = null;
            var1.setline(533);
            var1.getlocal(0).__getattr__("setNamedItem").__call__(var2, var1.getlocal(3));
         }

         var1.setline(534);
         var3 = var1.getlocal(2);
         var1.getlocal(3).__setattr__("value", var3);
         var3 = null;
      } else {
         var1.setline(536);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Attr")).__not__().__nonzero__()) {
            var1.setline(537);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("value must be a string or Attr object"));
         }

         var1.setline(538);
         var3 = var1.getlocal(2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(539);
         var1.getlocal(0).__getattr__("setNamedItem").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getNamedItem$54(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(543);
         var3 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(545);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject getNamedItemNS$55(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(549);
         var3 = var1.getlocal(0).__getattr__("_attrsNS").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(551);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject removeNamedItem$56(PyFrame var1, ThreadState var2) {
      var1.setline(554);
      PyObject var3 = var1.getlocal(0).__getattr__("getNamedItem").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(555);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(556);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0).__getattr__("_ownerElement"));
         var1.setline(557);
         var1.getlocal(0).__getattr__("_attrs").__delitem__(var1.getlocal(2).__getattr__("nodeName"));
         var1.setline(558);
         var1.getlocal(0).__getattr__("_attrsNS").__delitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("namespaceURI"), var1.getlocal(2).__getattr__("localName")})));
         var1.setline(559);
         PyString var4 = PyString.fromInterned("ownerElement");
         var10000 = var4._in(var1.getlocal(2).__getattr__("__dict__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(560);
            var3 = var1.getglobal("None");
            var1.getlocal(2).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("ownerElement"), var3);
            var3 = null;
         }

         var1.setline(561);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(563);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
      }
   }

   public PyObject removeNamedItemNS$57(PyFrame var1, ThreadState var2) {
      var1.setline(566);
      PyObject var3 = var1.getlocal(0).__getattr__("getNamedItemNS").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(567);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(568);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0).__getattr__("_ownerElement"));
         var1.setline(569);
         var1.getlocal(0).__getattr__("_attrsNS").__delitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("namespaceURI"), var1.getlocal(3).__getattr__("localName")})));
         var1.setline(570);
         var1.getlocal(0).__getattr__("_attrs").__delitem__(var1.getlocal(3).__getattr__("nodeName"));
         var1.setline(571);
         PyString var4 = PyString.fromInterned("ownerElement");
         var10000 = var4._in(var1.getlocal(3).__getattr__("__dict__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(572);
            var3 = var1.getglobal("None");
            var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("ownerElement"), var3);
            var3 = null;
         }

         var1.setline(573);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(575);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
      }
   }

   public PyObject setNamedItem$58(PyFrame var1, ThreadState var2) {
      var1.setline(578);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Attr")).__not__().__nonzero__()) {
         var1.setline(579);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, PyString.fromInterned("%s cannot be child of %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(0))}))));
      } else {
         var1.setline(581);
         PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("name"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(582);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(583);
            var1.getlocal(2).__getattr__("unlink").__call__(var2);
         }

         var1.setline(584);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("_attrs").__setitem__(var1.getlocal(1).__getattr__("name"), var3);
         var3 = null;
         var1.setline(585);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("_attrsNS").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("namespaceURI"), var1.getlocal(1).__getattr__("localName")})), var3);
         var3 = null;
         var1.setline(586);
         var3 = var1.getlocal(0).__getattr__("_ownerElement");
         var1.getlocal(1).__setattr__("ownerElement", var3);
         var3 = null;
         var1.setline(587);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(1).__getattr__("ownerElement"));
         var1.setline(588);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setNamedItemNS$59(PyFrame var1, ThreadState var2) {
      var1.setline(591);
      PyObject var3 = var1.getlocal(0).__getattr__("setNamedItem").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __delitem__$60(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(595);
      var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(2).__getattr__("ownerElement"));
      var1.setline(596);
      var1.getlocal(2).__getattr__("unlink").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getstate__$61(PyFrame var1, ThreadState var2) {
      var1.setline(599);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_attrs"), var1.getlocal(0).__getattr__("_attrsNS"), var1.getlocal(0).__getattr__("_ownerElement")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$62(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("_attrs", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_attrsNS", var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("_ownerElement", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TypeInfo$63(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(611);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("namespace"), PyString.fromInterned("name")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(613);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$64, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(617);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$65, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(623);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_name$66, (PyObject)null);
      var1.setlocal("_get_name", var5);
      var3 = null;
      var1.setline(626);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_namespace$67, (PyObject)null);
      var1.setlocal("_get_namespace", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$64(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("namespace", var3);
      var3 = null;
      var1.setline(615);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$65(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("namespace").__nonzero__()) {
         var1.setline(619);
         var3 = PyString.fromInterned("<TypeInfo %r (from %r)>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("namespace")}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(621);
         var3 = PyString.fromInterned("<TypeInfo %r>")._mod(var1.getlocal(0).__getattr__("name"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_name$66(PyFrame var1, ThreadState var2) {
      var1.setline(624);
      PyObject var3 = var1.getlocal(0).__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_namespace$67(PyFrame var1, ThreadState var2) {
      var1.setline(627);
      PyObject var3 = var1.getlocal(0).__getattr__("namespace");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Element$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(632);
      PyObject var3 = var1.getname("Node").__getattr__("ELEMENT_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(633);
      var3 = var1.getname("None");
      var1.setlocal("nodeValue", var3);
      var3 = null;
      var1.setline(634);
      var3 = var1.getname("_no_type");
      var1.setlocal("schemaType", var3);
      var3 = null;
      var1.setline(636);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal("_magic_id_nodes", var4);
      var3 = null;
      var1.setline(638);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getname("Node").__getattr__("ELEMENT_NODE"), var1.getname("Node").__getattr__("PROCESSING_INSTRUCTION_NODE"), var1.getname("Node").__getattr__("COMMENT_NODE"), var1.getname("Node").__getattr__("TEXT_NODE"), var1.getname("Node").__getattr__("CDATA_SECTION_NODE"), var1.getname("Node").__getattr__("ENTITY_REFERENCE_NODE")});
      var1.setlocal("_child_node_types", var5);
      var3 = null;
      var1.setline(645);
      PyObject[] var6 = new PyObject[]{var1.getname("EMPTY_NAMESPACE"), var1.getname("None"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$69, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(660);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_localName$70, (PyObject)null);
      var1.setlocal("_get_localName", var7);
      var3 = null;
      var1.setline(663);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_tagName$71, (PyObject)null);
      var1.setlocal("_get_tagName", var7);
      var3 = null;
      var1.setline(666);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, unlink$72, (PyObject)null);
      var1.setlocal("unlink", var7);
      var3 = null;
      var1.setline(673);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getAttribute$73, (PyObject)null);
      var1.setlocal("getAttribute", var7);
      var3 = null;
      var1.setline(679);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getAttributeNS$74, (PyObject)null);
      var1.setlocal("getAttributeNS", var7);
      var3 = null;
      var1.setline(685);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setAttribute$75, (PyObject)null);
      var1.setlocal("setAttribute", var7);
      var3 = null;
      var1.setline(700);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setAttributeNS$76, (PyObject)null);
      var1.setlocal("setAttributeNS", var7);
      var3 = null;
      var1.setline(722);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getAttributeNode$77, (PyObject)null);
      var1.setlocal("getAttributeNode", var7);
      var3 = null;
      var1.setline(725);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getAttributeNodeNS$78, (PyObject)null);
      var1.setlocal("getAttributeNodeNS", var7);
      var3 = null;
      var1.setline(728);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setAttributeNode$79, (PyObject)null);
      var1.setlocal("setAttributeNode", var7);
      var3 = null;
      var1.setline(746);
      var3 = var1.getname("setAttributeNode");
      var1.setlocal("setAttributeNodeNS", var3);
      var3 = null;
      var1.setline(748);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, removeAttribute$80, (PyObject)null);
      var1.setlocal("removeAttribute", var7);
      var3 = null;
      var1.setline(755);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, removeAttributeNS$81, (PyObject)null);
      var1.setlocal("removeAttributeNS", var7);
      var3 = null;
      var1.setline(762);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, removeAttributeNode$82, (PyObject)null);
      var1.setlocal("removeAttributeNode", var7);
      var3 = null;
      var1.setline(775);
      var3 = var1.getname("removeAttributeNode");
      var1.setlocal("removeAttributeNodeNS", var3);
      var3 = null;
      var1.setline(777);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, hasAttribute$83, (PyObject)null);
      var1.setlocal("hasAttribute", var7);
      var3 = null;
      var1.setline(780);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, hasAttributeNS$84, (PyObject)null);
      var1.setlocal("hasAttributeNS", var7);
      var3 = null;
      var1.setline(783);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getElementsByTagName$85, (PyObject)null);
      var1.setlocal("getElementsByTagName", var7);
      var3 = null;
      var1.setline(786);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getElementsByTagNameNS$86, (PyObject)null);
      var1.setlocal("getElementsByTagNameNS", var7);
      var3 = null;
      var1.setline(790);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, __repr__$87, (PyObject)null);
      var1.setlocal("__repr__", var7);
      var3 = null;
      var1.setline(793);
      var6 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, writexml$88, (PyObject)null);
      var1.setlocal("writexml", var7);
      var3 = null;
      var1.setline(821);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_attributes$89, (PyObject)null);
      var1.setlocal("_get_attributes", var7);
      var3 = null;
      var1.setline(824);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, hasAttributes$90, (PyObject)null);
      var1.setlocal("hasAttributes", var7);
      var3 = null;
      var1.setline(832);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setIdAttribute$91, (PyObject)null);
      var1.setlocal("setIdAttribute", var7);
      var3 = null;
      var1.setline(836);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setIdAttributeNS$92, (PyObject)null);
      var1.setlocal("setIdAttributeNS", var7);
      var3 = null;
      var1.setline(840);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setIdAttributeNode$93, (PyObject)null);
      var1.setlocal("setIdAttributeNode", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$69(PyFrame var1, ThreadState var2) {
      var1.setline(647);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tagName", var3);
      var1.getlocal(0).__setattr__("nodeName", var3);
      var1.setline(648);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(649);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("namespaceURI", var3);
      var3 = null;
      var1.setline(650);
      var3 = var1.getglobal("NodeList").__call__(var2);
      var1.getlocal(0).__setattr__("childNodes", var3);
      var3 = null;
      var1.setline(652);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_attrs", var4);
      var3 = null;
      var1.setline(653);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_attrsNS", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_localName$70(PyFrame var1, ThreadState var2) {
      var1.setline(661);
      PyObject var3 = var1.getlocal(0).__getattr__("tagName").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(-1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_tagName$71(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      PyObject var3 = var1.getlocal(0).__getattr__("tagName");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unlink$72(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(667);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(669);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_attrs", var3);
            var3 = null;
            var1.setline(670);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_attrsNS", var3);
            var3 = null;
            var1.setline(671);
            var1.getglobal("Node").__getattr__("unlink").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(668);
         var1.getlocal(1).__getattr__("unlink").__call__(var2);
      }
   }

   public PyObject getAttribute$73(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(675);
         PyObject var6 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1)).__getattr__("value");
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(677);
            PyString var3 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject getAttributeNS$74(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(681);
         PyObject var6 = var1.getlocal(0).__getattr__("_attrsNS").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})).__getattr__("value");
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(683);
            PyString var3 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject setAttribute$75(PyFrame var1, ThreadState var2) {
      var1.setline(686);
      PyObject var3 = var1.getlocal(0).__getattr__("getAttributeNode").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(687);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(688);
         var3 = var1.getglobal("Attr").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(690);
         var3 = var1.getlocal(3).__getattr__("__dict__");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(691);
         var3 = var1.getlocal(2);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("value"), var3);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(692);
         var3 = var1.getlocal(0).__getattr__("ownerDocument");
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var3);
         var3 = null;
         var1.setline(693);
         var1.getlocal(0).__getattr__("setAttributeNode").__call__(var2, var1.getlocal(3));
      } else {
         var1.setline(694);
         var3 = var1.getlocal(2);
         var10000 = var3._ne(var1.getlocal(3).__getattr__("value"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(695);
            var3 = var1.getlocal(3).__getattr__("__dict__");
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(696);
            var3 = var1.getlocal(2);
            var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("value"), var3);
            var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
            var1.setline(697);
            if (var1.getlocal(3).__getattr__("isId").__nonzero__()) {
               var1.setline(698);
               var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setAttributeNS$76(PyFrame var1, ThreadState var2) {
      var1.setline(701);
      PyObject var3 = var1.getglobal("_nssplit").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(702);
      var3 = var1.getlocal(0).__getattr__("getAttributeNodeNS").__call__(var2, var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(703);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(705);
         var3 = var1.getglobal("Attr").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(5), var1.getlocal(4));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(706);
         var3 = var1.getlocal(6).__getattr__("__dict__");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(707);
         var3 = var1.getlocal(4);
         var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
         var3 = null;
         var1.setline(708);
         var3 = var1.getlocal(2);
         var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
         var3 = null;
         var1.setline(709);
         var3 = var1.getlocal(3);
         var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("value"), var3);
         var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var1.setline(710);
         var3 = var1.getlocal(0).__getattr__("ownerDocument");
         var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("ownerDocument"), var3);
         var3 = null;
         var1.setline(711);
         var1.getlocal(0).__getattr__("setAttributeNode").__call__(var2, var1.getlocal(6));
      } else {
         var1.setline(713);
         var3 = var1.getlocal(6).__getattr__("__dict__");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(714);
         var3 = var1.getlocal(3);
         var10000 = var3._ne(var1.getlocal(6).__getattr__("value"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(715);
            var3 = var1.getlocal(3);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("value"), var3);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
            var1.setline(716);
            if (var1.getlocal(6).__getattr__("isId").__nonzero__()) {
               var1.setline(717);
               var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
            }
         }

         var1.setline(718);
         var3 = var1.getlocal(6).__getattr__("prefix");
         var10000 = var3._ne(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(719);
            var3 = var1.getlocal(4);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
            var3 = null;
            var1.setline(720);
            var3 = var1.getlocal(2);
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getAttributeNode$77(PyFrame var1, ThreadState var2) {
      var1.setline(723);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getAttributeNodeNS$78(PyFrame var1, ThreadState var2) {
      var1.setline(726);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrsNS").__getattr__("get").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setAttributeNode$79(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyObject var3 = var1.getlocal(1).__getattr__("ownerElement");
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(0)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(730);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("InuseAttributeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attribute node already owned")));
      } else {
         var1.setline(731);
         var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("name"), var1.getglobal("None"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(732);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(733);
            var1.getlocal(0).__getattr__("removeAttributeNode").__call__(var2, var1.getlocal(2));
         }

         var1.setline(734);
         var3 = var1.getlocal(0).__getattr__("_attrsNS").__getattr__("get").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("namespaceURI"), var1.getlocal(1).__getattr__("localName")})), (PyObject)var1.getglobal("None"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(735);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._isnot(var1.getlocal(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(736);
            var1.getlocal(0).__getattr__("removeAttributeNode").__call__(var2, var1.getlocal(3));
         }

         var1.setline(737);
         var1.getglobal("_set_attribute_node").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(739);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(742);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(743);
            PyObject var4 = var1.getlocal(3);
            var10000 = var4._isnot(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(744);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject removeAttribute$80(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(750);
         PyObject var5 = var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(752);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
         }

         throw var3;
      }

      var1.setline(753);
      var1.getlocal(0).__getattr__("removeAttributeNode").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removeAttributeNS$81(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(757);
         PyObject var5 = var1.getlocal(0).__getattr__("_attrsNS").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         var1.setlocal(3, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(759);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
         }

         throw var3;
      }

      var1.setline(760);
      var1.getlocal(0).__getattr__("removeAttributeNode").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removeAttributeNode$82(PyFrame var1, ThreadState var2) {
      var1.setline(763);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(764);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
      } else {
         try {
            var1.setline(766);
            var1.getlocal(0).__getattr__("_attrs").__getitem__(var1.getlocal(1).__getattr__("name"));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("KeyError"))) {
               var1.setline(768);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
            }

            throw var5;
         }

         var1.setline(769);
         var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
         var1.setline(770);
         var1.getlocal(1).__getattr__("unlink").__call__(var2);
         var1.setline(773);
         var3 = var1.getlocal(0).__getattr__("ownerDocument");
         var1.getlocal(1).__setattr__("ownerDocument", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject hasAttribute$83(PyFrame var1, ThreadState var2) {
      var1.setline(778);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_attrs"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hasAttributeNS$84(PyFrame var1, ThreadState var2) {
      var1.setline(781);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_attrsNS"));
      var3 = null;
      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getElementsByTagName$85(PyFrame var1, ThreadState var2) {
      var1.setline(784);
      PyObject var3 = var1.getglobal("_get_elements_by_tagName_helper").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("NodeList").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getElementsByTagNameNS$86(PyFrame var1, ThreadState var2) {
      var1.setline(787);
      PyObject var3 = var1.getglobal("_get_elements_by_tagName_ns_helper").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("NodeList").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$87(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      PyObject var3 = PyString.fromInterned("<DOM Element: %s at %#x>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tagName"), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writexml$88(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(2)._add(PyString.fromInterned("<"))._add(var1.getlocal(0).__getattr__("tagName")));
      var1.setline(799);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_attributes").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(800);
      var3 = var1.getlocal(5).__getattr__("keys").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(801);
      var1.getlocal(6).__getattr__("sort").__call__(var2);
      var1.setline(803);
      var3 = var1.getlocal(6).__iter__();

      while(true) {
         var1.setline(803);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(807);
            if (var1.getlocal(0).__getattr__("childNodes").__nonzero__()) {
               var1.setline(808);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
               var1.setline(809);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("childNodes"));
               PyObject var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__getattr__("nodeType");
                  var10000 = var3._eq(var1.getglobal("Node").__getattr__("TEXT_NODE"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(811);
                  var1.getlocal(0).__getattr__("childNodes").__getitem__(Py.newInteger(0)).__getattr__("writexml").__call__(var2, var1.getlocal(1), PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""));
               } else {
                  var1.setline(813);
                  var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(4));
                  var1.setline(814);
                  var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

                  while(true) {
                     var1.setline(814);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(816);
                        var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(2));
                        break;
                     }

                     var1.setlocal(8, var4);
                     var1.setline(815);
                     var1.getlocal(8).__getattr__("writexml").__call__(var2, var1.getlocal(1), var1.getlocal(2)._add(var1.getlocal(3)), var1.getlocal(3), var1.getlocal(4));
                  }
               }

               var1.setline(817);
               var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("</%s>%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tagName"), var1.getlocal(4)})));
            } else {
               var1.setline(819);
               var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("/>%s")._mod(var1.getlocal(4)));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(7, var4);
         var1.setline(804);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned(" %s=\"")._mod(var1.getlocal(7)));
         var1.setline(805);
         var1.getglobal("_write_data").__call__(var2, var1.getlocal(1), var1.getlocal(5).__getitem__(var1.getlocal(7)).__getattr__("value"));
         var1.setline(806);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
      }
   }

   public PyObject _get_attributes$89(PyFrame var1, ThreadState var2) {
      var1.setline(822);
      PyObject var3 = var1.getglobal("NamedNodeMap").__call__(var2, var1.getlocal(0).__getattr__("_attrs"), var1.getlocal(0).__getattr__("_attrsNS"), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hasAttributes$90(PyFrame var1, ThreadState var2) {
      var1.setline(825);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_attrs").__nonzero__()) {
         var1.setline(826);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(828);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setIdAttribute$91(PyFrame var1, ThreadState var2) {
      var1.setline(833);
      PyObject var3 = var1.getlocal(0).__getattr__("getAttributeNode").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(834);
      var1.getlocal(0).__getattr__("setIdAttributeNode").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setIdAttributeNS$92(PyFrame var1, ThreadState var2) {
      var1.setline(837);
      PyObject var3 = var1.getlocal(0).__getattr__("getAttributeNodeNS").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(838);
      var1.getlocal(0).__getattr__("setIdAttributeNode").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setIdAttributeNode$93(PyFrame var1, ThreadState var2) {
      var1.setline(841);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("isSameNode").__call__(var2, var1.getlocal(1).__getattr__("ownerElement")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(842);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
      } else {
         var1.setline(843);
         var3 = var1.getglobal("_get_containing_entref").__call__(var2, var1.getlocal(0));
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(844);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__(var2));
         } else {
            var1.setline(845);
            if (var1.getlocal(1).__getattr__("_is_id").__not__().__nonzero__()) {
               var1.setline(846);
               var3 = var1.getglobal("True");
               var1.getlocal(1).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("_is_id"), var3);
               var3 = null;
               var1.setline(847);
               var10000 = var1.getlocal(0);
               String var6 = "_magic_id_nodes";
               PyObject var4 = var10000;
               PyObject var5 = var4.__getattr__(var6);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setattr__(var6, var5);
               var1.setline(848);
               var10000 = var1.getlocal(0).__getattr__("ownerDocument");
               var6 = "_magic_id_count";
               var4 = var10000;
               var5 = var4.__getattr__(var6);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setattr__(var6, var5);
               var1.setline(849);
               var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _set_attribute_node$94(PyFrame var1, ThreadState var2) {
      var1.setline(858);
      var1.getglobal("_clear_id_cache").__call__(var2, var1.getlocal(0));
      var1.setline(859);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("_attrs").__setitem__(var1.getlocal(1).__getattr__("name"), var3);
      var3 = null;
      var1.setline(860);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("_attrsNS").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("namespaceURI"), var1.getlocal(1).__getattr__("localName")})), var3);
      var3 = null;
      var1.setline(865);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("ownerElement"), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Childless$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mixin that makes childless-ness easy to implement and avoids\n    the complexity of the Node methods that deal with children.\n    "));
      var1.setline(871);
      PyString.fromInterned("Mixin that makes childless-ness easy to implement and avoids\n    the complexity of the Node methods that deal with children.\n    ");
      var1.setline(873);
      PyObject var3 = var1.getname("None");
      var1.setlocal("attributes", var3);
      var3 = null;
      var1.setline(874);
      var3 = var1.getname("EmptyNodeList").__call__(var2);
      var1.setlocal("childNodes", var3);
      var3 = null;
      var1.setline(875);
      var3 = var1.getname("None");
      var1.setlocal("firstChild", var3);
      var3 = null;
      var1.setline(876);
      var3 = var1.getname("None");
      var1.setlocal("lastChild", var3);
      var3 = null;
      var1.setline(878);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _get_firstChild$96, (PyObject)null);
      var1.setlocal("_get_firstChild", var5);
      var3 = null;
      var1.setline(881);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_lastChild$97, (PyObject)null);
      var1.setlocal("_get_lastChild", var5);
      var3 = null;
      var1.setline(884);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, appendChild$98, (PyObject)null);
      var1.setlocal("appendChild", var5);
      var3 = null;
      var1.setline(888);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, hasChildNodes$99, (PyObject)null);
      var1.setlocal("hasChildNodes", var5);
      var3 = null;
      var1.setline(891);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, insertBefore$100, (PyObject)null);
      var1.setlocal("insertBefore", var5);
      var3 = null;
      var1.setline(895);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeChild$101, (PyObject)null);
      var1.setlocal("removeChild", var5);
      var3 = null;
      var1.setline(899);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, normalize$102, (PyObject)null);
      var1.setlocal("normalize", var5);
      var3 = null;
      var1.setline(903);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, replaceChild$103, (PyObject)null);
      var1.setlocal("replaceChild", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_firstChild$96(PyFrame var1, ThreadState var2) {
      var1.setline(879);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_lastChild$97(PyFrame var1, ThreadState var2) {
      var1.setline(882);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject appendChild$98(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, var1.getlocal(0).__getattr__("nodeName")._add(PyString.fromInterned(" nodes cannot have children"))));
   }

   public PyObject hasChildNodes$99(PyFrame var1, ThreadState var2) {
      var1.setline(889);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject insertBefore$100(PyFrame var1, ThreadState var2) {
      var1.setline(892);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, var1.getlocal(0).__getattr__("nodeName")._add(PyString.fromInterned(" nodes do not have children"))));
   }

   public PyObject removeChild$101(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2, var1.getlocal(0).__getattr__("nodeName")._add(PyString.fromInterned(" nodes do not have children"))));
   }

   public PyObject normalize$102(PyFrame var1, ThreadState var2) {
      var1.setline(901);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject replaceChild$103(PyFrame var1, ThreadState var2) {
      var1.setline(904);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, var1.getlocal(0).__getattr__("nodeName")._add(PyString.fromInterned(" nodes do not have children"))));
   }

   public PyObject ProcessingInstruction$104(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(909);
      PyObject var3 = var1.getname("Node").__getattr__("PROCESSING_INSTRUCTION_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(911);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$105, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(915);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_data$106, (PyObject)null);
      var1.setlocal("_get_data", var5);
      var3 = null;
      var1.setline(917);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_data$107, (PyObject)null);
      var1.setlocal("_set_data", var5);
      var3 = null;
      var1.setline(921);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_target$108, (PyObject)null);
      var1.setlocal("_get_target", var5);
      var3 = null;
      var1.setline(923);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_target$109, (PyObject)null);
      var1.setlocal("_set_target", var5);
      var3 = null;
      var1.setline(927);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setattr__$110, (PyObject)null);
      var1.setlocal("__setattr__", var5);
      var3 = null;
      var1.setline(935);
      var4 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, writexml$111, (PyObject)null);
      var1.setlocal("writexml", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$105(PyFrame var1, ThreadState var2) {
      var1.setline(912);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("target", var3);
      var1.getlocal(0).__setattr__("nodeName", var3);
      var1.setline(913);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("data", var3);
      var1.getlocal(0).__setattr__("nodeValue", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_data$106(PyFrame var1, ThreadState var2) {
      var1.setline(916);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_data$107(PyFrame var1, ThreadState var2) {
      var1.setline(918);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(919);
      var3 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("data"), var3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_target$108(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyObject var3 = var1.getlocal(0).__getattr__("target");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_target$109(PyFrame var1, ThreadState var2) {
      var1.setline(924);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(925);
      var3 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("target"), var3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setattr__$110(PyFrame var1, ThreadState var2) {
      var1.setline(928);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("data"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("nodeValue"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(929);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
      } else {
         var1.setline(930);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("target"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._eq(PyString.fromInterned("nodeName"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(931);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("target"), var3);
            var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
         } else {
            var1.setline(933);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("__dict__").__setitem__(var1.getlocal(1), var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writexml$111(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s<?%s %s?>%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("data"), var1.getlocal(4)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CharacterData$112(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(940);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _get_length$113, (PyObject)null);
      var1.setlocal("_get_length", var4);
      var3 = null;
      var1.setline(942);
      PyObject var5 = var1.getname("_get_length");
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(944);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_data$114, (PyObject)null);
      var1.setlocal("_get_data", var4);
      var3 = null;
      var1.setline(946);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_data$115, (PyObject)null);
      var1.setlocal("_set_data", var4);
      var3 = null;
      var1.setline(950);
      var5 = var1.getname("_get_data");
      var1.setlocal("_get_nodeValue", var5);
      var3 = null;
      var1.setline(951);
      var5 = var1.getname("_set_data");
      var1.setlocal("_set_nodeValue", var5);
      var3 = null;
      var1.setline(953);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setattr__$116, (PyObject)null);
      var1.setlocal("__setattr__", var4);
      var3 = null;
      var1.setline(959);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$117, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(968);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, substringData$118, (PyObject)null);
      var1.setlocal("substringData", var4);
      var3 = null;
      var1.setline(977);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, appendData$119, (PyObject)null);
      var1.setlocal("appendData", var4);
      var3 = null;
      var1.setline(980);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, insertData$120, (PyObject)null);
      var1.setlocal("insertData", var4);
      var3 = null;
      var1.setline(989);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, deleteData$121, (PyObject)null);
      var1.setlocal("deleteData", var4);
      var3 = null;
      var1.setline(999);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, replaceData$122, (PyObject)null);
      var1.setlocal("replaceData", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_length$113(PyFrame var1, ThreadState var2) {
      var1.setline(941);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_data$114(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__").__getitem__(PyString.fromInterned("data"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_data$115(PyFrame var1, ThreadState var2) {
      var1.setline(947);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(948);
      var3 = var1.getlocal(1);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("data"), var3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setattr__$116(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("data"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("nodeValue"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(955);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
      } else {
         var1.setline(957);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("__dict__").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$117(PyFrame var1, ThreadState var2) {
      var1.setline(960);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(961);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(10));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(962);
         var4 = PyString.fromInterned("...");
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(964);
         var4 = PyString.fromInterned("");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(965);
      var3 = PyString.fromInterned("<DOM %s node \"%r%s\">")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(1).__getslice__(Py.newInteger(0), Py.newInteger(10), (PyObject)null), var1.getlocal(2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject substringData$118(PyFrame var1, ThreadState var2) {
      var1.setline(969);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(970);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be negative")));
      } else {
         var1.setline(971);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(972);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be beyond end of data")));
         } else {
            var1.setline(973);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(974);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("count cannot be negative")));
            } else {
               var1.setline(975);
               var3 = var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1), var1.getlocal(1)._add(var1.getlocal(2)), (PyObject)null);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject appendData$119(PyFrame var1, ThreadState var2) {
      var1.setline(978);
      PyObject var3 = var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject insertData$120(PyFrame var1, ThreadState var2) {
      var1.setline(981);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(982);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be negative")));
      } else {
         var1.setline(983);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(984);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be beyond end of data")));
         } else {
            var1.setline(985);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(986);
               var3 = PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null), var1.getlocal(2), var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null)}));
               var1.getlocal(0).__setattr__("data", var3);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject deleteData$121(PyFrame var1, ThreadState var2) {
      var1.setline(990);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(991);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be negative")));
      } else {
         var1.setline(992);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(993);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be beyond end of data")));
         } else {
            var1.setline(994);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(995);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("count cannot be negative")));
            } else {
               var1.setline(996);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(997);
                  var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1)._add(var1.getlocal(2)), (PyObject)null, (PyObject)null));
                  var1.getlocal(0).__setattr__("data", var3);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject replaceData$122(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1001);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be negative")));
      } else {
         var1.setline(1002);
         var3 = var1.getlocal(1);
         var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1003);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("offset cannot be beyond end of data")));
         } else {
            var1.setline(1004);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1005);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("count cannot be negative")));
            } else {
               var1.setline(1006);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(1007);
                  var3 = PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null), var1.getlocal(3), var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1)._add(var1.getlocal(2)), (PyObject)null, (PyObject)null)}));
                  var1.getlocal(0).__setattr__("data", var3);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject Text$123(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1019);
      PyObject var3 = var1.getname("Node").__getattr__("TEXT_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(1020);
      PyString var4 = PyString.fromInterned("#text");
      var1.setlocal("nodeName", var4);
      var3 = null;
      var1.setline(1021);
      var3 = var1.getname("None");
      var1.setlocal("attributes", var3);
      var3 = null;
      var1.setline(1023);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, splitText$124, (PyObject)null);
      var1.setlocal("splitText", var6);
      var3 = null;
      var1.setline(1038);
      var5 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var6 = new PyFunction(var1.f_globals, var5, writexml$125, (PyObject)null);
      var1.setlocal("writexml", var6);
      var3 = null;
      var1.setline(1043);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_wholeText$126, (PyObject)null);
      var1.setlocal("_get_wholeText", var6);
      var3 = null;
      var1.setline(1061);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, replaceWholeText$127, (PyObject)null);
      var1.setlocal("replaceWholeText", var6);
      var3 = null;
      var1.setline(1091);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_isWhitespaceInElementContent$128, (PyObject)null);
      var1.setlocal("_get_isWhitespaceInElementContent", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject splitText$124(PyFrame var1, ThreadState var2) {
      var1.setline(1024);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1025);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("IndexSizeErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal offset value")));
      } else {
         var1.setline(1026);
         var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1027);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
         var1.getlocal(2).__setattr__("data", var3);
         var3 = null;
         var1.setline(1028);
         var3 = var1.getlocal(0).__getattr__("ownerDocument");
         var1.getlocal(2).__setattr__("ownerDocument", var3);
         var3 = null;
         var1.setline(1029);
         var3 = var1.getlocal(0).__getattr__("nextSibling");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1030);
         var10000 = var1.getlocal(0).__getattr__("parentNode");
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._in(var1.getlocal(0).__getattr__("parentNode").__getattr__("childNodes"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1031);
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1032);
               var1.getlocal(0).__getattr__("parentNode").__getattr__("appendChild").__call__(var2, var1.getlocal(2));
            } else {
               var1.setline(1034);
               var1.getlocal(0).__getattr__("parentNode").__getattr__("insertBefore").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            }
         }

         var1.setline(1035);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
         var1.setline(1036);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject writexml$125(PyFrame var1, ThreadState var2) {
      var1.setline(1039);
      var1.getglobal("_write_data").__call__(var2, var1.getlocal(1), PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("data"), var1.getlocal(4)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_wholeText$126(PyFrame var1, ThreadState var2) {
      var1.setline(1044);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("data")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1045);
      PyObject var4 = var1.getlocal(0).__getattr__("previousSibling");
      var1.setlocal(2, var4);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(1046);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1047);
         var4 = var1.getlocal(2).__getattr__("nodeType");
         var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("Node").__getattr__("TEXT_NODE"), var1.getglobal("Node").__getattr__("CDATA_SECTION_NODE")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1048);
         var1.getlocal(1).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2).__getattr__("data"));
         var1.setline(1049);
         var4 = var1.getlocal(2).__getattr__("previousSibling");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(1052);
      var4 = var1.getlocal(0).__getattr__("nextSibling");
      var1.setlocal(2, var4);
      var3 = null;

      while(true) {
         var1.setline(1053);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1054);
         var4 = var1.getlocal(2).__getattr__("nodeType");
         var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("Node").__getattr__("TEXT_NODE"), var1.getglobal("Node").__getattr__("CDATA_SECTION_NODE")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1055);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("data"));
         var1.setline(1056);
         var4 = var1.getlocal(2).__getattr__("nextSibling");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(1059);
      var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject replaceWholeText$127(PyFrame var1, ThreadState var2) {
      var1.setline(1064);
      PyObject var3 = var1.getlocal(0).__getattr__("parentNode");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1065);
      var3 = var1.getlocal(0).__getattr__("previousSibling");
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(1066);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1067);
         var3 = var1.getlocal(3).__getattr__("nodeType");
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("Node").__getattr__("TEXT_NODE"), var1.getglobal("Node").__getattr__("CDATA_SECTION_NODE")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1068);
         var3 = var1.getlocal(3).__getattr__("previousSibling");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1069);
         var1.getlocal(2).__getattr__("removeChild").__call__(var2, var1.getlocal(3));
         var1.setline(1070);
         var3 = var1.getlocal(4);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1073);
      var3 = var1.getlocal(0).__getattr__("nextSibling");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1074);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1075);
         var1.getlocal(2).__getattr__("removeChild").__call__(var2, var1.getlocal(0));
      }

      while(true) {
         var1.setline(1076);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1077);
         var3 = var1.getlocal(3).__getattr__("nodeType");
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("Node").__getattr__("TEXT_NODE"), var1.getglobal("Node").__getattr__("CDATA_SECTION_NODE")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1078);
         var3 = var1.getlocal(3).__getattr__("nextSibling");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1079);
         var1.getlocal(2).__getattr__("removeChild").__call__(var2, var1.getlocal(3));
         var1.setline(1080);
         var3 = var1.getlocal(4);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1083);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1084);
         var3 = var1.getlocal(0).__getattr__("__dict__");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1085);
         var3 = var1.getlocal(1);
         var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("data"), var3);
         var3 = null;
         var1.setline(1086);
         var3 = var1.getlocal(1);
         var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("nodeValue"), var3);
         var3 = null;
         var1.setline(1087);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1089);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_isWhitespaceInElementContent$128(PyFrame var1, ThreadState var2) {
      var1.setline(1092);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("data").__getattr__("strip").__call__(var2).__nonzero__()) {
         var1.setline(1093);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1094);
         PyObject var4 = var1.getglobal("_get_containing_element").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1095);
         var4 = var1.getlocal(1);
         PyObject var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1096);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1097);
            var4 = var1.getlocal(0).__getattr__("ownerDocument").__getattr__("_get_elem_info").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1098);
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1099);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1101);
               var3 = var1.getlocal(2).__getattr__("isElementContent").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _get_containing_element$129(PyFrame var1, ThreadState var2) {
      var1.setline(1111);
      PyObject var3 = var1.getlocal(0).__getattr__("parentNode");
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(1112);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1116);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1113);
         var3 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1114);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1115);
         var4 = var1.getlocal(1).__getattr__("parentNode");
         var1.setlocal(1, var4);
         var4 = null;
      }
   }

   public PyObject _get_containing_entref$130(PyFrame var1, ThreadState var2) {
      var1.setline(1119);
      PyObject var3 = var1.getlocal(0).__getattr__("parentNode");
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(1120);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1124);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1121);
         var3 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("Node").__getattr__("ENTITY_REFERENCE_NODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1122);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1123);
         var4 = var1.getlocal(1).__getattr__("parentNode");
         var1.setlocal(1, var4);
         var4 = null;
      }
   }

   public PyObject Comment$131(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1128);
      PyObject var3 = var1.getname("Node").__getattr__("COMMENT_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(1129);
      PyString var4 = PyString.fromInterned("#comment");
      var1.setlocal("nodeName", var4);
      var3 = null;
      var1.setline(1131);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$132, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1134);
      var5 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var6 = new PyFunction(var1.f_globals, var5, writexml$133, (PyObject)null);
      var1.setlocal("writexml", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$132(PyFrame var1, ThreadState var2) {
      var1.setline(1132);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("data", var3);
      var1.getlocal(0).__setattr__("nodeValue", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writexml$133(PyFrame var1, ThreadState var2) {
      var1.setline(1135);
      PyString var3 = PyString.fromInterned("--");
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1136);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'--' is not allowed in a comment node")));
      } else {
         var1.setline(1137);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s<!--%s-->%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("data"), var1.getlocal(4)})));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject CDATASection$134(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1146);
      PyObject var3 = var1.getname("Node").__getattr__("CDATA_SECTION_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(1147);
      PyString var4 = PyString.fromInterned("#cdata-section");
      var1.setlocal("nodeName", var4);
      var3 = null;
      var1.setline(1149);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, writexml$135, (PyObject)null);
      var1.setlocal("writexml", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject writexml$135(PyFrame var1, ThreadState var2) {
      var1.setline(1150);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]]>"));
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1151);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("']]>' not allowed in a CDATA section")));
      } else {
         var1.setline(1152);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("<![CDATA[%s]]>")._mod(var1.getlocal(0).__getattr__("data")));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject ReadOnlySequentialNamedNodeMap$136(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1156);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_seq")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(1158);
      PyObject[] var4 = new PyObject[]{new PyTuple(Py.EmptyObjects)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$137, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1162);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __len__$138, (PyObject)null);
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(1165);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_length$139, (PyObject)null);
      var1.setlocal("_get_length", var5);
      var3 = null;
      var1.setline(1168);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getNamedItem$140, (PyObject)null);
      var1.setlocal("getNamedItem", var5);
      var3 = null;
      var1.setline(1173);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getNamedItemNS$141, (PyObject)null);
      var1.setlocal("getNamedItemNS", var5);
      var3 = null;
      var1.setline(1178);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getitem__$142, (PyObject)null);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(1187);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, item$143, (PyObject)null);
      var1.setlocal("item", var5);
      var3 = null;
      var1.setline(1195);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeNamedItem$144, (PyObject)null);
      var1.setlocal("removeNamedItem", var5);
      var3 = null;
      var1.setline(1199);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeNamedItemNS$145, (PyObject)null);
      var1.setlocal("removeNamedItemNS", var5);
      var3 = null;
      var1.setline(1203);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setNamedItem$146, (PyObject)null);
      var1.setlocal("setNamedItem", var5);
      var3 = null;
      var1.setline(1207);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setNamedItemNS$147, (PyObject)null);
      var1.setlocal("setNamedItemNS", var5);
      var3 = null;
      var1.setline(1211);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$148, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(1214);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$149, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$137(PyFrame var1, ThreadState var2) {
      var1.setline(1160);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_seq", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$138(PyFrame var1, ThreadState var2) {
      var1.setline(1163);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_seq"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_length$139(PyFrame var1, ThreadState var2) {
      var1.setline(1166);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_seq"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getNamedItem$140(PyFrame var1, ThreadState var2) {
      var1.setline(1169);
      PyObject var3 = var1.getlocal(0).__getattr__("_seq").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(1169);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1170);
         var5 = var1.getlocal(2).__getattr__("nodeName");
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(1171);
      var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getNamedItemNS$141(PyFrame var1, ThreadState var2) {
      var1.setline(1174);
      PyObject var3 = var1.getlocal(0).__getattr__("_seq").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(1174);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1175);
         var5 = var1.getlocal(3).__getattr__("namespaceURI");
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(3).__getattr__("localName");
            var10000 = var5._eq(var1.getlocal(2));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(1176);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __getitem__$142(PyFrame var1, ThreadState var2) {
      var1.setline(1179);
      PyObject var10000;
      PyObject[] var3;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(1180);
         var10000 = var1.getlocal(0).__getattr__("getNamedItemNS");
         var3 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
         var3 = null;
         var5 = var10000;
         var1.setlocal(2, var5);
         var3 = null;
      } else {
         var1.setline(1182);
         var5 = var1.getlocal(0).__getattr__("getNamedItem").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(1183);
      var5 = var1.getlocal(2);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1184);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(1185);
         var5 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject item$143(PyFrame var1, ThreadState var2) {
      var1.setline(1188);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1189);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(1191);
            var3 = var1.getlocal(0).__getattr__("_seq").__getitem__(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("IndexError"))) {
               var1.setline(1193);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject removeNamedItem$144(PyFrame var1, ThreadState var2) {
      var1.setline(1196);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NamedNodeMap instance is read-only")));
   }

   public PyObject removeNamedItemNS$145(PyFrame var1, ThreadState var2) {
      var1.setline(1200);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NamedNodeMap instance is read-only")));
   }

   public PyObject setNamedItem$146(PyFrame var1, ThreadState var2) {
      var1.setline(1204);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NamedNodeMap instance is read-only")));
   }

   public PyObject setNamedItemNS$147(PyFrame var1, ThreadState var2) {
      var1.setline(1208);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NoModificationAllowedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NamedNodeMap instance is read-only")));
   }

   public PyObject __getstate__$148(PyFrame var1, ThreadState var2) {
      var1.setline(1212);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_seq")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$149(PyFrame var1, ThreadState var2) {
      var1.setline(1215);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("_seq", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Identified$150(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mix-in class that supports the publicId and systemId attributes."));
      var1.setline(1222);
      PyString.fromInterned("Mix-in class that supports the publicId and systemId attributes.");
      var1.setline(1227);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _identified_mixin_init$151, (PyObject)null);
      var1.setlocal("_identified_mixin_init", var4);
      var3 = null;
      var1.setline(1231);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_publicId$152, (PyObject)null);
      var1.setlocal("_get_publicId", var4);
      var3 = null;
      var1.setline(1234);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_systemId$153, (PyObject)null);
      var1.setlocal("_get_systemId", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _identified_mixin_init$151(PyFrame var1, ThreadState var2) {
      var1.setline(1228);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("publicId", var3);
      var3 = null;
      var1.setline(1229);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("systemId", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_publicId$152(PyFrame var1, ThreadState var2) {
      var1.setline(1232);
      PyObject var3 = var1.getlocal(0).__getattr__("publicId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_systemId$153(PyFrame var1, ThreadState var2) {
      var1.setline(1235);
      PyObject var3 = var1.getlocal(0).__getattr__("systemId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DocumentType$154(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1238);
      PyObject var3 = var1.getname("Node").__getattr__("DOCUMENT_TYPE_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(1239);
      var3 = var1.getname("None");
      var1.setlocal("nodeValue", var3);
      var3 = null;
      var1.setline(1240);
      var3 = var1.getname("None");
      var1.setlocal("name", var3);
      var3 = null;
      var1.setline(1241);
      var3 = var1.getname("None");
      var1.setlocal("publicId", var3);
      var3 = null;
      var1.setline(1242);
      var3 = var1.getname("None");
      var1.setlocal("systemId", var3);
      var3 = null;
      var1.setline(1243);
      var3 = var1.getname("None");
      var1.setlocal("internalSubset", var3);
      var3 = null;
      var1.setline(1245);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$155, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1253);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_internalSubset$156, (PyObject)null);
      var1.setlocal("_get_internalSubset", var5);
      var3 = null;
      var1.setline(1256);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, cloneNode$157, (PyObject)null);
      var1.setlocal("cloneNode", var5);
      var3 = null;
      var1.setline(1283);
      var4 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, writexml$158, (PyObject)null);
      var1.setlocal("writexml", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$155(PyFrame var1, ThreadState var2) {
      var1.setline(1246);
      PyObject var3 = var1.getglobal("ReadOnlySequentialNamedNodeMap").__call__(var2);
      var1.getlocal(0).__setattr__("entities", var3);
      var3 = null;
      var1.setline(1247);
      var3 = var1.getglobal("ReadOnlySequentialNamedNodeMap").__call__(var2);
      var1.getlocal(0).__setattr__("notations", var3);
      var3 = null;
      var1.setline(1248);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1249);
         var3 = var1.getglobal("_nssplit").__call__(var2, var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(1250);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
      }

      var1.setline(1251);
      var3 = var1.getlocal(0).__getattr__("name");
      var1.getlocal(0).__setattr__("nodeName", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_internalSubset$156(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      PyObject var3 = var1.getlocal(0).__getattr__("internalSubset");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cloneNode$157(PyFrame var1, ThreadState var2) {
      var1.setline(1257);
      PyObject var3 = var1.getlocal(0).__getattr__("ownerDocument");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var1.setline(1281);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1259);
         var3 = var1.getglobal("DocumentType").__call__(var2, var1.getglobal("None"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1260);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.getlocal(2).__setattr__("name", var3);
         var3 = null;
         var1.setline(1261);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.getlocal(2).__setattr__("nodeName", var3);
         var3 = null;
         var1.setline(1262);
         var3 = var1.getglobal("xml").__getattr__("dom").__getattr__("UserDataHandler").__getattr__("NODE_CLONED");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1263);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1264);
            PyList var6 = new PyList(Py.EmptyObjects);
            var1.getlocal(2).__getattr__("entities").__setattr__((String)"_seq", var6);
            var3 = null;
            var1.setline(1265);
            var6 = new PyList(Py.EmptyObjects);
            var1.getlocal(2).__getattr__("notations").__setattr__((String)"_seq", var6);
            var3 = null;
            var1.setline(1266);
            var3 = var1.getlocal(0).__getattr__("notations").__getattr__("_seq").__iter__();

            label24:
            while(true) {
               var1.setline(1266);
               PyObject var4 = var3.__iternext__();
               PyObject var5;
               if (var4 == null) {
                  var1.setline(1270);
                  var3 = var1.getlocal(0).__getattr__("entities").__getattr__("_seq").__iter__();

                  while(true) {
                     var1.setline(1270);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break label24;
                     }

                     var1.setlocal(6, var4);
                     var1.setline(1271);
                     var5 = var1.getglobal("Entity").__call__(var2, var1.getlocal(6).__getattr__("nodeName"), var1.getlocal(6).__getattr__("publicId"), var1.getlocal(6).__getattr__("systemId"), var1.getlocal(6).__getattr__("notationName"));
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(1273);
                     var5 = var1.getlocal(6).__getattr__("actualEncoding");
                     var1.getlocal(7).__setattr__("actualEncoding", var5);
                     var5 = null;
                     var1.setline(1274);
                     var5 = var1.getlocal(6).__getattr__("encoding");
                     var1.getlocal(7).__setattr__("encoding", var5);
                     var5 = null;
                     var1.setline(1275);
                     var5 = var1.getlocal(6).__getattr__("version");
                     var1.getlocal(7).__setattr__("version", var5);
                     var5 = null;
                     var1.setline(1276);
                     var1.getlocal(2).__getattr__("entities").__getattr__("_seq").__getattr__("append").__call__(var2, var1.getlocal(7));
                     var1.setline(1277);
                     var1.getlocal(6).__getattr__("_call_user_data_handler").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(7));
                  }
               }

               var1.setlocal(4, var4);
               var1.setline(1267);
               var5 = var1.getglobal("Notation").__call__(var2, var1.getlocal(4).__getattr__("nodeName"), var1.getlocal(4).__getattr__("publicId"), var1.getlocal(4).__getattr__("systemId"));
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(1268);
               var1.getlocal(2).__getattr__("notations").__getattr__("_seq").__getattr__("append").__call__(var2, var1.getlocal(5));
               var1.setline(1269);
               var1.getlocal(4).__getattr__("_call_user_data_handler").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
            }
         }

         var1.setline(1278);
         var1.getlocal(0).__getattr__("_call_user_data_handler").__call__(var2, var1.getlocal(3), var1.getlocal(0), var1.getlocal(2));
         var1.setline(1279);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject writexml$158(PyFrame var1, ThreadState var2) {
      var1.setline(1284);
      var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!DOCTYPE "));
      var1.setline(1285);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("name"));
      var1.setline(1286);
      if (var1.getlocal(0).__getattr__("publicId").__nonzero__()) {
         var1.setline(1287);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s  PUBLIC '%s'%s  '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("publicId"), var1.getlocal(4), var1.getlocal(0).__getattr__("systemId")})));
      } else {
         var1.setline(1289);
         if (var1.getlocal(0).__getattr__("systemId").__nonzero__()) {
            var1.setline(1290);
            var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s  SYSTEM '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("systemId")})));
         }
      }

      var1.setline(1291);
      PyObject var3 = var1.getlocal(0).__getattr__("internalSubset");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1292);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" ["));
         var1.setline(1293);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("internalSubset"));
         var1.setline(1294);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]"));
      }

      var1.setline(1295);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned(">")._add(var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Entity$159(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1298);
      PyObject var3 = var1.getname("None");
      var1.setlocal("attributes", var3);
      var3 = null;
      var1.setline(1299);
      var3 = var1.getname("Node").__getattr__("ENTITY_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(1300);
      var3 = var1.getname("None");
      var1.setlocal("nodeValue", var3);
      var3 = null;
      var1.setline(1302);
      var3 = var1.getname("None");
      var1.setlocal("actualEncoding", var3);
      var3 = null;
      var1.setline(1303);
      var3 = var1.getname("None");
      var1.setlocal("encoding", var3);
      var3 = null;
      var1.setline(1304);
      var3 = var1.getname("None");
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(1306);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$160, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1312);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_actualEncoding$161, (PyObject)null);
      var1.setlocal("_get_actualEncoding", var5);
      var3 = null;
      var1.setline(1315);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_encoding$162, (PyObject)null);
      var1.setlocal("_get_encoding", var5);
      var3 = null;
      var1.setline(1318);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_version$163, (PyObject)null);
      var1.setlocal("_get_version", var5);
      var3 = null;
      var1.setline(1321);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, appendChild$164, (PyObject)null);
      var1.setlocal("appendChild", var5);
      var3 = null;
      var1.setline(1325);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, insertBefore$165, (PyObject)null);
      var1.setlocal("insertBefore", var5);
      var3 = null;
      var1.setline(1329);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, removeChild$166, (PyObject)null);
      var1.setlocal("removeChild", var5);
      var3 = null;
      var1.setline(1333);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, replaceChild$167, (PyObject)null);
      var1.setlocal("replaceChild", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$160(PyFrame var1, ThreadState var2) {
      var1.setline(1307);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodeName", var3);
      var3 = null;
      var1.setline(1308);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("notationName", var3);
      var3 = null;
      var1.setline(1309);
      var3 = var1.getglobal("NodeList").__call__(var2);
      var1.getlocal(0).__setattr__("childNodes", var3);
      var3 = null;
      var1.setline(1310);
      var1.getlocal(0).__getattr__("_identified_mixin_init").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_actualEncoding$161(PyFrame var1, ThreadState var2) {
      var1.setline(1313);
      PyObject var3 = var1.getlocal(0).__getattr__("actualEncoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_encoding$162(PyFrame var1, ThreadState var2) {
      var1.setline(1316);
      PyObject var3 = var1.getlocal(0).__getattr__("encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_version$163(PyFrame var1, ThreadState var2) {
      var1.setline(1319);
      PyObject var3 = var1.getlocal(0).__getattr__("version");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject appendChild$164(PyFrame var1, ThreadState var2) {
      var1.setline(1322);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot append children to an entity node")));
   }

   public PyObject insertBefore$165(PyFrame var1, ThreadState var2) {
      var1.setline(1326);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot insert children below an entity node")));
   }

   public PyObject removeChild$166(PyFrame var1, ThreadState var2) {
      var1.setline(1330);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot remove children from an entity node")));
   }

   public PyObject replaceChild$167(PyFrame var1, ThreadState var2) {
      var1.setline(1334);
      throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot replace children of an entity node")));
   }

   public PyObject Notation$168(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1338);
      PyObject var3 = var1.getname("Node").__getattr__("NOTATION_NODE");
      var1.setlocal("nodeType", var3);
      var3 = null;
      var1.setline(1339);
      var3 = var1.getname("None");
      var1.setlocal("nodeValue", var3);
      var3 = null;
      var1.setline(1341);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$169, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$169(PyFrame var1, ThreadState var2) {
      var1.setline(1342);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("nodeName", var3);
      var3 = null;
      var1.setline(1343);
      var1.getlocal(0).__getattr__("_identified_mixin_init").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DOMImplementation$170(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1347);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("core"), PyString.fromInterned("1.0")}), new PyTuple(new PyObject[]{PyString.fromInterned("core"), PyString.fromInterned("2.0")}), new PyTuple(new PyObject[]{PyString.fromInterned("core"), var1.getname("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("xml"), PyString.fromInterned("1.0")}), new PyTuple(new PyObject[]{PyString.fromInterned("xml"), PyString.fromInterned("2.0")}), new PyTuple(new PyObject[]{PyString.fromInterned("xml"), var1.getname("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("ls-load"), PyString.fromInterned("3.0")}), new PyTuple(new PyObject[]{PyString.fromInterned("ls-load"), var1.getname("None")})});
      var1.setlocal("_features", var3);
      var3 = null;
      var1.setline(1357);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, hasFeature$171, (PyObject)null);
      var1.setlocal("hasFeature", var5);
      var3 = null;
      var1.setline(1362);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, createDocument$172, (PyObject)null);
      var1.setlocal("createDocument", var5);
      var3 = null;
      var1.setline(1407);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, createDocumentType$173, (PyObject)null);
      var1.setlocal("createDocumentType", var5);
      var3 = null;
      var1.setline(1415);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getInterface$174, (PyObject)null);
      var1.setlocal("getInterface", var5);
      var3 = null;
      var1.setline(1422);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _create_document$175, (PyObject)null);
      var1.setlocal("_create_document", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject hasFeature$171(PyFrame var1, ThreadState var2) {
      var1.setline(1358);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1359);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1360);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("lower").__call__(var2), var1.getlocal(2)});
      var10000 = var4._in(var1.getlocal(0).__getattr__("_features"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createDocument$172(PyFrame var1, ThreadState var2) {
      var1.setline(1363);
      PyObject var10000 = var1.getlocal(3);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3).__getattr__("parentNode");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1364);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("WrongDocumentErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("doctype object owned by another DOM tree")));
      } else {
         var1.setline(1366);
         var3 = var1.getlocal(0).__getattr__("_create_document").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1368);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
            }
         }

         var3 = var10000.__not__();
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1372);
         var10000 = var1.getlocal(2).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5);
         }

         if (var10000.__nonzero__()) {
            var1.setline(1385);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("InvalidCharacterErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Element with no name")));
         } else {
            var1.setline(1387);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(1388);
               var3 = var1.getglobal("_nssplit").__call__(var2, var1.getlocal(2));
               PyObject[] var4 = Py.unpackSequence(var3, 2);
               PyObject var5 = var4[0];
               var1.setlocal(6, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(7, var5);
               var5 = null;
               var3 = null;
               var1.setline(1389);
               var3 = var1.getlocal(6);
               var10000 = var3._eq(PyString.fromInterned("xml"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(1);
                  var10000 = var3._ne(PyString.fromInterned("http://www.w3.org/XML/1998/namespace"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1391);
                  throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NamespaceErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal use of 'xml' prefix")));
               }

               var1.setline(1392);
               var10000 = var1.getlocal(6);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1393);
                  throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NamespaceErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal use of prefix without namespaces")));
               }

               var1.setline(1395);
               var3 = var1.getlocal(4).__getattr__("createElementNS").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1396);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(1397);
                  var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(3));
               }

               var1.setline(1398);
               var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(8));
            }

            var1.setline(1400);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(1401);
               var3 = var1.getlocal(4);
               var1.getlocal(3).__setattr__("parentNode", var3);
               var1.getlocal(3).__setattr__("ownerDocument", var3);
            }

            var1.setline(1403);
            var3 = var1.getlocal(3);
            var1.getlocal(4).__setattr__("doctype", var3);
            var3 = null;
            var1.setline(1404);
            var3 = var1.getlocal(0);
            var1.getlocal(4).__setattr__("implementation", var3);
            var3 = null;
            var1.setline(1405);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject createDocumentType$173(PyFrame var1, ThreadState var2) {
      var1.setline(1408);
      PyObject var3 = var1.getglobal("DocumentType").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1409);
      var3 = var1.getlocal(2);
      var1.getlocal(4).__setattr__("publicId", var3);
      var3 = null;
      var1.setline(1410);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("systemId", var3);
      var3 = null;
      var1.setline(1411);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getInterface$174(PyFrame var1, ThreadState var2) {
      var1.setline(1416);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("hasFeature").__call__(var2, var1.getlocal(1), var1.getglobal("None")).__nonzero__()) {
         var1.setline(1417);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1419);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _create_document$175(PyFrame var1, ThreadState var2) {
      var1.setline(1423);
      PyObject var3 = var1.getglobal("Document").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ElementInfo$176(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Object that represents content-model information for an element.\n\n    This implementation is not expected to be used in practice; DOM\n    builders should provide implementations which do the right thing\n    using information available to it.\n\n    "));
      var1.setline(1432);
      PyString.fromInterned("Object that represents content-model information for an element.\n\n    This implementation is not expected to be used in practice; DOM\n    builders should provide implementations which do the right thing\n    using information available to it.\n\n    ");
      var1.setline(1434);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("tagName")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(1436);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$177, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1439);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getAttributeType$178, (PyObject)null);
      var1.setlocal("getAttributeType", var5);
      var3 = null;
      var1.setline(1442);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getAttributeTypeNS$179, (PyObject)null);
      var1.setlocal("getAttributeTypeNS", var5);
      var3 = null;
      var1.setline(1445);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isElementContent$180, (PyObject)null);
      var1.setlocal("isElementContent", var5);
      var3 = null;
      var1.setline(1448);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isEmpty$181, PyString.fromInterned("Returns true iff this element is declared to have an EMPTY\n        content model."));
      var1.setlocal("isEmpty", var5);
      var3 = null;
      var1.setline(1453);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isId$182, PyString.fromInterned("Returns true iff the named attribute is a DTD-style ID."));
      var1.setlocal("isId", var5);
      var3 = null;
      var1.setline(1457);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isIdNS$183, PyString.fromInterned("Returns true iff the identified attribute is a DTD-style ID."));
      var1.setlocal("isIdNS", var5);
      var3 = null;
      var1.setline(1461);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getstate__$184, (PyObject)null);
      var1.setlocal("__getstate__", var5);
      var3 = null;
      var1.setline(1464);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setstate__$185, (PyObject)null);
      var1.setlocal("__setstate__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$177(PyFrame var1, ThreadState var2) {
      var1.setline(1437);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tagName", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getAttributeType$178(PyFrame var1, ThreadState var2) {
      var1.setline(1440);
      PyObject var3 = var1.getglobal("_no_type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getAttributeTypeNS$179(PyFrame var1, ThreadState var2) {
      var1.setline(1443);
      PyObject var3 = var1.getglobal("_no_type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isElementContent$180(PyFrame var1, ThreadState var2) {
      var1.setline(1446);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isEmpty$181(PyFrame var1, ThreadState var2) {
      var1.setline(1450);
      PyString.fromInterned("Returns true iff this element is declared to have an EMPTY\n        content model.");
      var1.setline(1451);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isId$182(PyFrame var1, ThreadState var2) {
      var1.setline(1454);
      PyString.fromInterned("Returns true iff the named attribute is a DTD-style ID.");
      var1.setline(1455);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isIdNS$183(PyFrame var1, ThreadState var2) {
      var1.setline(1458);
      PyString.fromInterned("Returns true iff the identified attribute is a DTD-style ID.");
      var1.setline(1459);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getstate__$184(PyFrame var1, ThreadState var2) {
      var1.setline(1462);
      PyObject var3 = var1.getlocal(0).__getattr__("tagName");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setstate__$185(PyFrame var1, ThreadState var2) {
      var1.setline(1465);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tagName", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _clear_id_cache$186(PyFrame var1, ThreadState var2) {
      var1.setline(1468);
      PyObject var3 = var1.getlocal(0).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_NODE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1469);
         var1.getlocal(0).__getattr__("_id_cache").__getattr__("clear").__call__(var2);
         var1.setline(1470);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_id_search_stack", var3);
         var3 = null;
      } else {
         var1.setline(1471);
         if (var1.getglobal("_in_document").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(1472);
            var1.getlocal(0).__getattr__("ownerDocument").__getattr__("_id_cache").__getattr__("clear").__call__(var2);
            var1.setline(1473);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__getattr__("ownerDocument").__setattr__("_id_search_stack", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Document$187(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1476);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getname("Node").__getattr__("ELEMENT_NODE"), var1.getname("Node").__getattr__("PROCESSING_INSTRUCTION_NODE"), var1.getname("Node").__getattr__("COMMENT_NODE"), var1.getname("Node").__getattr__("DOCUMENT_TYPE_NODE")});
      var1.setlocal("_child_node_types", var3);
      var3 = null;
      var1.setline(1479);
      PyObject var4 = var1.getname("Node").__getattr__("DOCUMENT_NODE");
      var1.setlocal("nodeType", var4);
      var3 = null;
      var1.setline(1480);
      PyString var5 = PyString.fromInterned("#document");
      var1.setlocal("nodeName", var5);
      var3 = null;
      var1.setline(1481);
      var4 = var1.getname("None");
      var1.setlocal("nodeValue", var4);
      var3 = null;
      var1.setline(1482);
      var4 = var1.getname("None");
      var1.setlocal("attributes", var4);
      var3 = null;
      var1.setline(1483);
      var4 = var1.getname("None");
      var1.setlocal("doctype", var4);
      var3 = null;
      var1.setline(1484);
      var4 = var1.getname("None");
      var1.setlocal("parentNode", var4);
      var3 = null;
      var1.setline(1485);
      var4 = var1.getname("None");
      var1.setlocal("previousSibling", var4);
      var1.setlocal("nextSibling", var4);
      var1.setline(1487);
      var4 = var1.getname("DOMImplementation").__call__(var2);
      var1.setlocal("implementation", var4);
      var3 = null;
      var1.setline(1491);
      var4 = var1.getname("None");
      var1.setlocal("actualEncoding", var4);
      var3 = null;
      var1.setline(1492);
      var4 = var1.getname("None");
      var1.setlocal("encoding", var4);
      var3 = null;
      var1.setline(1493);
      var4 = var1.getname("None");
      var1.setlocal("standalone", var4);
      var3 = null;
      var1.setline(1494);
      var4 = var1.getname("None");
      var1.setlocal("version", var4);
      var3 = null;
      var1.setline(1495);
      var4 = var1.getname("False");
      var1.setlocal("strictErrorChecking", var4);
      var3 = null;
      var1.setline(1496);
      var4 = var1.getname("None");
      var1.setlocal("errorHandler", var4);
      var3 = null;
      var1.setline(1497);
      var4 = var1.getname("None");
      var1.setlocal("documentURI", var4);
      var3 = null;
      var1.setline(1499);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal("_magic_id_count", var6);
      var3 = null;
      var1.setline(1501);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$188, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(1509);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_elem_info$189, (PyObject)null);
      var1.setlocal("_get_elem_info", var8);
      var3 = null;
      var1.setline(1516);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_actualEncoding$190, (PyObject)null);
      var1.setlocal("_get_actualEncoding", var8);
      var3 = null;
      var1.setline(1519);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_doctype$191, (PyObject)null);
      var1.setlocal("_get_doctype", var8);
      var3 = null;
      var1.setline(1522);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_documentURI$192, (PyObject)null);
      var1.setlocal("_get_documentURI", var8);
      var3 = null;
      var1.setline(1525);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_encoding$193, (PyObject)null);
      var1.setlocal("_get_encoding", var8);
      var3 = null;
      var1.setline(1528);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_errorHandler$194, (PyObject)null);
      var1.setlocal("_get_errorHandler", var8);
      var3 = null;
      var1.setline(1531);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_standalone$195, (PyObject)null);
      var1.setlocal("_get_standalone", var8);
      var3 = null;
      var1.setline(1534);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_strictErrorChecking$196, (PyObject)null);
      var1.setlocal("_get_strictErrorChecking", var8);
      var3 = null;
      var1.setline(1537);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_version$197, (PyObject)null);
      var1.setlocal("_get_version", var8);
      var3 = null;
      var1.setline(1540);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, appendChild$198, (PyObject)null);
      var1.setlocal("appendChild", var8);
      var3 = null;
      var1.setline(1556);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, removeChild$199, (PyObject)null);
      var1.setlocal("removeChild", var8);
      var3 = null;
      var1.setline(1568);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_documentElement$200, (PyObject)null);
      var1.setlocal("_get_documentElement", var8);
      var3 = null;
      var1.setline(1573);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, unlink$201, (PyObject)null);
      var1.setlocal("unlink", var8);
      var3 = null;
      var1.setline(1579);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, cloneNode$202, (PyObject)null);
      var1.setlocal("cloneNode", var8);
      var3 = null;
      var1.setline(1600);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createDocumentFragment$203, (PyObject)null);
      var1.setlocal("createDocumentFragment", var8);
      var3 = null;
      var1.setline(1605);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createElement$204, (PyObject)null);
      var1.setlocal("createElement", var8);
      var3 = null;
      var1.setline(1610);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createTextNode$205, (PyObject)null);
      var1.setlocal("createTextNode", var8);
      var3 = null;
      var1.setline(1618);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createCDATASection$206, (PyObject)null);
      var1.setlocal("createCDATASection", var8);
      var3 = null;
      var1.setline(1626);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createComment$207, (PyObject)null);
      var1.setlocal("createComment", var8);
      var3 = null;
      var1.setline(1631);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createProcessingInstruction$208, (PyObject)null);
      var1.setlocal("createProcessingInstruction", var8);
      var3 = null;
      var1.setline(1636);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createAttribute$209, (PyObject)null);
      var1.setlocal("createAttribute", var8);
      var3 = null;
      var1.setline(1642);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createElementNS$210, (PyObject)null);
      var1.setlocal("createElementNS", var8);
      var3 = null;
      var1.setline(1648);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, createAttributeNS$211, (PyObject)null);
      var1.setlocal("createAttributeNS", var8);
      var3 = null;
      var1.setline(1658);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _create_entity$212, (PyObject)null);
      var1.setlocal("_create_entity", var8);
      var3 = null;
      var1.setline(1663);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _create_notation$213, (PyObject)null);
      var1.setlocal("_create_notation", var8);
      var3 = null;
      var1.setline(1668);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, getElementById$214, (PyObject)null);
      var1.setlocal("getElementById", var8);
      var3 = null;
      var1.setline(1726);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, getElementsByTagName$215, (PyObject)null);
      var1.setlocal("getElementsByTagName", var8);
      var3 = null;
      var1.setline(1729);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, getElementsByTagNameNS$216, (PyObject)null);
      var1.setlocal("getElementsByTagNameNS", var8);
      var3 = null;
      var1.setline(1733);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, isSupported$217, (PyObject)null);
      var1.setlocal("isSupported", var8);
      var3 = null;
      var1.setline(1736);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, importNode$218, (PyObject)null);
      var1.setlocal("importNode", var8);
      var3 = null;
      var1.setline(1743);
      var7 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, writexml$219, (PyObject)null);
      var1.setlocal("writexml", var8);
      var3 = null;
      var1.setline(1754);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, renameNode$220, (PyObject)null);
      var1.setlocal("renameNode", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$188(PyFrame var1, ThreadState var2) {
      var1.setline(1502);
      PyObject var3 = var1.getglobal("NodeList").__call__(var2);
      var1.getlocal(0).__setattr__("childNodes", var3);
      var3 = null;
      var1.setline(1505);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_elem_info", var4);
      var3 = null;
      var1.setline(1506);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_id_cache", var4);
      var3 = null;
      var1.setline(1507);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_id_search_stack", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_elem_info$189(PyFrame var1, ThreadState var2) {
      var1.setline(1510);
      PyTuple var3;
      PyObject var4;
      if (var1.getlocal(1).__getattr__("namespaceURI").__nonzero__()) {
         var1.setline(1511);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("namespaceURI"), var1.getlocal(1).__getattr__("localName")});
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(1513);
         var4 = var1.getlocal(1).__getattr__("tagName");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(1514);
      var4 = var1.getlocal(0).__getattr__("_elem_info").__getattr__("get").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _get_actualEncoding$190(PyFrame var1, ThreadState var2) {
      var1.setline(1517);
      PyObject var3 = var1.getlocal(0).__getattr__("actualEncoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_doctype$191(PyFrame var1, ThreadState var2) {
      var1.setline(1520);
      PyObject var3 = var1.getlocal(0).__getattr__("doctype");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_documentURI$192(PyFrame var1, ThreadState var2) {
      var1.setline(1523);
      PyObject var3 = var1.getlocal(0).__getattr__("documentURI");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_encoding$193(PyFrame var1, ThreadState var2) {
      var1.setline(1526);
      PyObject var3 = var1.getlocal(0).__getattr__("encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_errorHandler$194(PyFrame var1, ThreadState var2) {
      var1.setline(1529);
      PyObject var3 = var1.getlocal(0).__getattr__("errorHandler");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_standalone$195(PyFrame var1, ThreadState var2) {
      var1.setline(1532);
      PyObject var3 = var1.getlocal(0).__getattr__("standalone");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_strictErrorChecking$196(PyFrame var1, ThreadState var2) {
      var1.setline(1535);
      PyObject var3 = var1.getlocal(0).__getattr__("strictErrorChecking");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_version$197(PyFrame var1, ThreadState var2) {
      var1.setline(1538);
      PyObject var3 = var1.getlocal(0).__getattr__("version");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject appendChild$198(PyFrame var1, ThreadState var2) {
      var1.setline(1541);
      PyObject var3 = var1.getlocal(1).__getattr__("nodeType");
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("_child_node_types"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1542);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__(var2, PyString.fromInterned("%s cannot be child of %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var1.getglobal("repr").__call__(var2, var1.getlocal(0))}))));
      } else {
         var1.setline(1544);
         var3 = var1.getlocal(1).__getattr__("parentNode");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1548);
            var1.getlocal(1).__getattr__("parentNode").__getattr__("removeChild").__call__(var2, var1.getlocal(1));
         }

         var1.setline(1550);
         var3 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_get_documentElement").__call__(var2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(1552);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("HierarchyRequestErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("two document elements disallowed")));
         } else {
            var1.setline(1554);
            var3 = var1.getglobal("Node").__getattr__("appendChild").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject removeChild$199(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(1558);
         var1.getlocal(0).__getattr__("childNodes").__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("ValueError"))) {
            var1.setline(1560);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2));
         }

         throw var3;
      }

      var1.setline(1561);
      PyObject var5 = var1.getglobal("None");
      var1.getlocal(1).__setattr__("nextSibling", var5);
      var1.getlocal(1).__setattr__("previousSibling", var5);
      var1.setline(1562);
      var5 = var1.getglobal("None");
      var1.getlocal(1).__setattr__("parentNode", var5);
      var3 = null;
      var1.setline(1563);
      var5 = var1.getlocal(0).__getattr__("documentElement");
      PyObject var10000 = var5._is(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1564);
         var5 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("documentElement", var5);
         var3 = null;
      }

      var1.setline(1566);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _get_documentElement$200(PyFrame var1, ThreadState var2) {
      var1.setline(1569);
      PyObject var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(1569);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1570);
         var5 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var5._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(1571);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject unlink$201(PyFrame var1, ThreadState var2) {
      var1.setline(1574);
      PyObject var3 = var1.getlocal(0).__getattr__("doctype");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1575);
         var1.getlocal(0).__getattr__("doctype").__getattr__("unlink").__call__(var2);
         var1.setline(1576);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("doctype", var3);
         var3 = null;
      }

      var1.setline(1577);
      var1.getglobal("Node").__getattr__("unlink").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cloneNode$202(PyFrame var1, ThreadState var2) {
      var1.setline(1580);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1581);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1582);
         PyObject var4 = var1.getlocal(0).__getattr__("implementation").__getattr__("createDocument").__call__(var2, var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1583);
         var4 = var1.getlocal(0).__getattr__("encoding");
         var1.getlocal(2).__setattr__("encoding", var4);
         var4 = null;
         var1.setline(1584);
         var4 = var1.getlocal(0).__getattr__("standalone");
         var1.getlocal(2).__setattr__("standalone", var4);
         var4 = null;
         var1.setline(1585);
         var4 = var1.getlocal(0).__getattr__("version");
         var1.getlocal(2).__setattr__("version", var4);
         var4 = null;
         var1.setline(1586);
         var4 = var1.getlocal(0).__getattr__("childNodes").__iter__();

         while(true) {
            var1.setline(1586);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(1596);
               var1.getlocal(0).__getattr__("_call_user_data_handler").__call__(var2, var1.getglobal("xml").__getattr__("dom").__getattr__("UserDataHandler").__getattr__("NODE_CLONED"), var1.getlocal(0), var1.getlocal(2));
               var1.setline(1598);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(1587);
            PyObject var6 = var1.getglobal("_clone_node").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(1588);
            PyObject var10000;
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(4).__getattr__("ownerDocument").__getattr__("isSameNode").__call__(var2, var1.getlocal(2)).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }

            var1.setline(1589);
            var1.getlocal(2).__getattr__("childNodes").__getattr__("append").__call__(var2, var1.getlocal(4));
            var1.setline(1590);
            var6 = var1.getlocal(4).__getattr__("nodeType");
            var10000 = var6._eq(var1.getglobal("Node").__getattr__("DOCUMENT_NODE"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1591);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getlocal(2).__getattr__("documentElement");
                  var10000 = var6._is(var1.getglobal("None"));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }
            } else {
               var1.setline(1592);
               var6 = var1.getlocal(4).__getattr__("nodeType");
               var10000 = var6._eq(var1.getglobal("Node").__getattr__("DOCUMENT_TYPE_NODE"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1593);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var6 = var1.getlocal(2).__getattr__("doctype");
                     var10000 = var6._is(var1.getglobal("None"));
                     var6 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(1594);
                  var6 = var1.getlocal(4);
                  var1.getlocal(2).__setattr__("doctype", var6);
                  var6 = null;
               }
            }

            var1.setline(1595);
            var6 = var1.getlocal(2);
            var1.getlocal(4).__setattr__("parentNode", var6);
            var6 = null;
         }
      }
   }

   public PyObject createDocumentFragment$203(PyFrame var1, ThreadState var2) {
      var1.setline(1601);
      PyObject var3 = var1.getglobal("DocumentFragment").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1602);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1603);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createElement$204(PyFrame var1, ThreadState var2) {
      var1.setline(1606);
      PyObject var3 = var1.getglobal("Element").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1607);
      var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1608);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createTextNode$205(PyFrame var1, ThreadState var2) {
      var1.setline(1611);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringTypes")).__not__().__nonzero__()) {
         var1.setline(1612);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("node contents must be a string"));
      } else {
         var1.setline(1613);
         PyObject var3 = var1.getglobal("Text").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1614);
         var3 = var1.getlocal(1);
         var1.getlocal(2).__setattr__("data", var3);
         var3 = null;
         var1.setline(1615);
         var3 = var1.getlocal(0);
         var1.getlocal(2).__setattr__("ownerDocument", var3);
         var3 = null;
         var1.setline(1616);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject createCDATASection$206(PyFrame var1, ThreadState var2) {
      var1.setline(1619);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringTypes")).__not__().__nonzero__()) {
         var1.setline(1620);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("node contents must be a string"));
      } else {
         var1.setline(1621);
         PyObject var3 = var1.getglobal("CDATASection").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1622);
         var3 = var1.getlocal(1);
         var1.getlocal(2).__setattr__("data", var3);
         var3 = null;
         var1.setline(1623);
         var3 = var1.getlocal(0);
         var1.getlocal(2).__setattr__("ownerDocument", var3);
         var3 = null;
         var1.setline(1624);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject createComment$207(PyFrame var1, ThreadState var2) {
      var1.setline(1627);
      PyObject var3 = var1.getglobal("Comment").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1628);
      var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1629);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createProcessingInstruction$208(PyFrame var1, ThreadState var2) {
      var1.setline(1632);
      PyObject var3 = var1.getglobal("ProcessingInstruction").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1633);
      var3 = var1.getlocal(0);
      var1.getlocal(3).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1634);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createAttribute$209(PyFrame var1, ThreadState var2) {
      var1.setline(1637);
      PyObject var3 = var1.getglobal("Attr").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1638);
      var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1639);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(2).__setattr__((String)"value", var4);
      var3 = null;
      var1.setline(1640);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createElementNS$210(PyFrame var1, ThreadState var2) {
      var1.setline(1643);
      PyObject var3 = var1.getglobal("_nssplit").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1644);
      var3 = var1.getglobal("Element").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1645);
      var3 = var1.getlocal(0);
      var1.getlocal(5).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1646);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject createAttributeNS$211(PyFrame var1, ThreadState var2) {
      var1.setline(1649);
      PyObject var3 = var1.getglobal("_nssplit").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1650);
      var3 = var1.getglobal("Attr").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(4), var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1651);
      var3 = var1.getlocal(0);
      var1.getlocal(5).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1652);
      PyString var6 = PyString.fromInterned("");
      var1.getlocal(5).__setattr__((String)"value", var6);
      var3 = null;
      var1.setline(1653);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _create_entity$212(PyFrame var1, ThreadState var2) {
      var1.setline(1659);
      PyObject var3 = var1.getglobal("Entity").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1660);
      var3 = var1.getlocal(0);
      var1.getlocal(5).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1661);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _create_notation$213(PyFrame var1, ThreadState var2) {
      var1.setline(1664);
      PyObject var3 = var1.getglobal("Notation").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1665);
      var3 = var1.getlocal(0);
      var1.getlocal(4).__setattr__("ownerDocument", var3);
      var3 = null;
      var1.setline(1666);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getElementById$214(PyFrame var1, ThreadState var2) {
      var1.setline(1669);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_id_cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1670);
         var3 = var1.getlocal(0).__getattr__("_id_cache").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1671);
         var10000 = var1.getlocal(0).__getattr__("_elem_info");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_magic_id_count");
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(1672);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1674);
            PyObject var4 = var1.getlocal(0).__getattr__("_id_search_stack");
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1675);
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1677);
               PyList var7 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("documentElement")});
               var1.setlocal(2, var7);
               var4 = null;
               var1.setline(1678);
               var4 = var1.getlocal(2);
               var1.getlocal(0).__setattr__("_id_search_stack", var4);
               var4 = null;
            } else {
               var1.setline(1679);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(1682);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }
            }

            var1.setline(1684);
            var4 = var1.getglobal("None");
            var1.setlocal(3, var4);
            var4 = null;

            do {
               var1.setline(1685);
               if (!var1.getlocal(2).__nonzero__()) {
                  break;
               }

               var1.setline(1686);
               var4 = var1.getlocal(2).__getattr__("pop").__call__(var2);
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1688);
               var10000 = var1.getlocal(2).__getattr__("extend");
               PyList var10002 = new PyList();
               var4 = var10002.__getattr__("append");
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1688);
               var4 = var1.getlocal(4).__getattr__("childNodes").__iter__();

               while(true) {
                  var1.setline(1688);
                  PyObject var5 = var4.__iternext__();
                  PyObject var6;
                  if (var5 == null) {
                     var1.setline(1688);
                     var1.dellocal(5);
                     var10000.__call__((ThreadState)var2, (PyObject)var10002);
                     var1.setline(1691);
                     var4 = var1.getlocal(0).__getattr__("_get_elem_info").__call__(var2, var1.getlocal(4));
                     var1.setlocal(7, var4);
                     var4 = null;
                     var1.setline(1692);
                     if (var1.getlocal(7).__nonzero__()) {
                        var1.setline(1696);
                        var4 = var1.getlocal(4).__getattr__("attributes").__getattr__("values").__call__(var2).__iter__();

                        while(true) {
                           var1.setline(1696);
                           var5 = var4.__iternext__();
                           if (var5 == null) {
                              break;
                           }

                           var1.setlocal(8, var5);
                           var1.setline(1697);
                           if (var1.getlocal(8).__getattr__("namespaceURI").__nonzero__()) {
                              var1.setline(1698);
                              if (var1.getlocal(7).__getattr__("isIdNS").__call__(var2, var1.getlocal(8).__getattr__("namespaceURI"), var1.getlocal(8).__getattr__("localName")).__nonzero__()) {
                                 var1.setline(1699);
                                 var6 = var1.getlocal(4);
                                 var1.getlocal(0).__getattr__("_id_cache").__setitem__(var1.getlocal(8).__getattr__("value"), var6);
                                 var6 = null;
                                 var1.setline(1700);
                                 var6 = var1.getlocal(8).__getattr__("value");
                                 var10000 = var6._eq(var1.getlocal(1));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1701);
                                    var6 = var1.getlocal(4);
                                    var1.setlocal(3, var6);
                                    var6 = null;
                                 } else {
                                    var1.setline(1702);
                                    if (var1.getlocal(4).__getattr__("_magic_id_nodes").__not__().__nonzero__()) {
                                       break;
                                    }
                                 }
                              }
                           } else {
                              var1.setline(1704);
                              if (var1.getlocal(7).__getattr__("isId").__call__(var2, var1.getlocal(8).__getattr__("name")).__nonzero__()) {
                                 var1.setline(1705);
                                 var6 = var1.getlocal(4);
                                 var1.getlocal(0).__getattr__("_id_cache").__setitem__(var1.getlocal(8).__getattr__("value"), var6);
                                 var6 = null;
                                 var1.setline(1706);
                                 var6 = var1.getlocal(8).__getattr__("value");
                                 var10000 = var6._eq(var1.getlocal(1));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1707);
                                    var6 = var1.getlocal(4);
                                    var1.setlocal(3, var6);
                                    var6 = null;
                                 } else {
                                    var1.setline(1708);
                                    if (var1.getlocal(4).__getattr__("_magic_id_nodes").__not__().__nonzero__()) {
                                       break;
                                    }
                                 }
                              } else {
                                 var1.setline(1710);
                                 if (var1.getlocal(8).__getattr__("_is_id").__nonzero__()) {
                                    var1.setline(1711);
                                    var6 = var1.getlocal(4);
                                    var1.getlocal(0).__getattr__("_id_cache").__setitem__(var1.getlocal(8).__getattr__("value"), var6);
                                    var6 = null;
                                    var1.setline(1712);
                                    var6 = var1.getlocal(8).__getattr__("value");
                                    var10000 = var6._eq(var1.getlocal(1));
                                    var6 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(1713);
                                       var6 = var1.getlocal(4);
                                       var1.setlocal(3, var6);
                                       var6 = null;
                                    } else {
                                       var1.setline(1714);
                                       var6 = var1.getlocal(4).__getattr__("_magic_id_nodes");
                                       var10000 = var6._eq(Py.newInteger(1));
                                       var6 = null;
                                       if (var10000.__nonzero__()) {
                                          break;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     } else {
                        var1.setline(1716);
                        if (var1.getlocal(4).__getattr__("_magic_id_nodes").__nonzero__()) {
                           var1.setline(1717);
                           var4 = var1.getlocal(4).__getattr__("attributes").__getattr__("values").__call__(var2).__iter__();

                           while(true) {
                              var1.setline(1717);
                              var5 = var4.__iternext__();
                              if (var5 == null) {
                                 break;
                              }

                              var1.setlocal(8, var5);
                              var1.setline(1718);
                              if (var1.getlocal(8).__getattr__("_is_id").__nonzero__()) {
                                 var1.setline(1719);
                                 var6 = var1.getlocal(4);
                                 var1.getlocal(0).__getattr__("_id_cache").__setitem__(var1.getlocal(8).__getattr__("value"), var6);
                                 var6 = null;
                                 var1.setline(1720);
                                 var6 = var1.getlocal(8).__getattr__("value");
                                 var10000 = var6._eq(var1.getlocal(1));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1721);
                                    var6 = var1.getlocal(4);
                                    var1.setlocal(3, var6);
                                    var6 = null;
                                 }
                              }
                           }
                        }
                     }

                     var1.setline(1722);
                     var4 = var1.getlocal(3);
                     var10000 = var4._isnot(var1.getglobal("None"));
                     var4 = null;
                     break;
                  }

                  var1.setlocal(6, var5);
                  var1.setline(1689);
                  var6 = var1.getlocal(6).__getattr__("nodeType");
                  PyObject var10003 = var6._in(var1.getglobal("_nodeTypes_with_children"));
                  var6 = null;
                  if (var10003.__nonzero__()) {
                     var1.setline(1688);
                     var1.getlocal(5).__call__(var2, var1.getlocal(6));
                  }
               }
            } while(!var10000.__nonzero__());

            var1.setline(1724);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getElementsByTagName$215(PyFrame var1, ThreadState var2) {
      var1.setline(1727);
      PyObject var3 = var1.getglobal("_get_elements_by_tagName_helper").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("NodeList").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getElementsByTagNameNS$216(PyFrame var1, ThreadState var2) {
      var1.setline(1730);
      PyObject var3 = var1.getglobal("_get_elements_by_tagName_ns_helper").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("NodeList").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isSupported$217(PyFrame var1, ThreadState var2) {
      var1.setline(1734);
      PyObject var3 = var1.getlocal(0).__getattr__("implementation").__getattr__("hasFeature").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject importNode$218(PyFrame var1, ThreadState var2) {
      var1.setline(1737);
      PyObject var3 = var1.getlocal(1).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_NODE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1738);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot import document nodes")));
      } else {
         var1.setline(1739);
         var3 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_TYPE_NODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1740);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot import document type nodes")));
         } else {
            var1.setline(1741);
            var3 = var1.getglobal("_clone_node").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject writexml$219(PyFrame var1, ThreadState var2) {
      var1.setline(1745);
      PyObject var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1746);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("<?xml version=\"1.0\" ?>")._add(var1.getlocal(4)));
      } else {
         var1.setline(1748);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("<?xml version=\"1.0\" encoding=\"%s\"?>%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)})));
      }

      var1.setline(1749);
      var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

      while(true) {
         var1.setline(1749);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(1750);
         var1.getlocal(6).__getattr__("writexml").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      }
   }

   public PyObject renameNode$220(PyFrame var1, ThreadState var2) {
      var1.setline(1755);
      PyObject var3 = var1.getlocal(1).__getattr__("ownerDocument");
      PyObject var10000 = var3._isnot(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1756);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("WrongDocumentErr").__call__(var2, PyString.fromInterned("cannot rename nodes from other documents;\nexpected %s,\nfound %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1).__getattr__("ownerDocument")}))));
      } else {
         var1.setline(1759);
         var3 = var1.getlocal(1).__getattr__("nodeType");
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("Node").__getattr__("ELEMENT_NODE"), var1.getglobal("Node").__getattr__("ATTRIBUTE_NODE")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1760);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("renameNode() only applies to element and attribute nodes")));
         } else {
            var1.setline(1762);
            var3 = var1.getlocal(2);
            var10000 = var3._ne(var1.getglobal("EMPTY_NAMESPACE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1763);
               PyString var6 = PyString.fromInterned(":");
               var10000 = var6._in(var1.getlocal(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1764);
                  var3 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
                  PyObject[] var4 = Py.unpackSequence(var3, 2);
                  PyObject var5 = var4[0];
                  var1.setlocal(4, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1765);
                  var3 = var1.getlocal(4);
                  var10000 = var3._eq(PyString.fromInterned("xmlns"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(2);
                     var10000 = var3._ne(var1.getglobal("xml").__getattr__("dom").__getattr__("XMLNS_NAMESPACE"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1767);
                     throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NamespaceErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal use of 'xmlns' prefix")));
                  }
               } else {
                  var1.setline(1770);
                  var3 = var1.getlocal(3);
                  var10000 = var3._eq(PyString.fromInterned("xmlns"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(2);
                     var10000 = var3._ne(var1.getglobal("xml").__getattr__("dom").__getattr__("XMLNS_NAMESPACE"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(1).__getattr__("nodeType");
                        var10000 = var3._eq(var1.getglobal("Node").__getattr__("ATTRIBUTE_NODE"));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1773);
                     throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NamespaceErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal use of the 'xmlns' attribute")));
                  }

                  var1.setline(1775);
                  var3 = var1.getglobal("None");
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(1776);
                  var3 = var1.getlocal(3);
                  var1.setlocal(5, var3);
                  var3 = null;
               }
            } else {
               var1.setline(1778);
               var3 = var1.getglobal("None");
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(1779);
               var3 = var1.getglobal("None");
               var1.setlocal(5, var3);
               var3 = null;
            }

            var1.setline(1780);
            var3 = var1.getlocal(1).__getattr__("nodeType");
            var10000 = var3._eq(var1.getglobal("Node").__getattr__("ATTRIBUTE_NODE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1781);
               var3 = var1.getlocal(1).__getattr__("ownerElement");
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(1782);
               var3 = var1.getlocal(6);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1783);
                  var3 = var1.getlocal(1).__getattr__("_is_id");
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1784);
                  var1.getlocal(6).__getattr__("removeAttributeNode").__call__(var2, var1.getlocal(1));
               }
            } else {
               var1.setline(1786);
               var3 = var1.getglobal("None");
               var1.setlocal(6, var3);
               var3 = null;
            }

            var1.setline(1788);
            var3 = var1.getlocal(1).__getattr__("__dict__");
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(1789);
            var3 = var1.getlocal(4);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
            var3 = null;
            var1.setline(1790);
            var3 = var1.getlocal(5);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("localName"), var3);
            var3 = null;
            var1.setline(1791);
            var3 = var1.getlocal(2);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("namespaceURI"), var3);
            var3 = null;
            var1.setline(1792);
            var3 = var1.getlocal(3);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("nodeName"), var3);
            var3 = null;
            var1.setline(1793);
            var3 = var1.getlocal(1).__getattr__("nodeType");
            var10000 = var3._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1794);
               var3 = var1.getlocal(3);
               var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("tagName"), var3);
               var3 = null;
            } else {
               var1.setline(1797);
               var3 = var1.getlocal(3);
               var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("name"), var3);
               var3 = null;
               var1.setline(1798);
               var3 = var1.getlocal(6);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1799);
                  var1.getlocal(6).__getattr__("setAttributeNode").__call__(var2, var1.getlocal(1));
                  var1.setline(1800);
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(1801);
                     var1.getlocal(6).__getattr__("setIdAttributeNode").__call__(var2, var1.getlocal(1));
                  }
               }
            }

            var1.setline(1807);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _clone_node$221(PyFrame var1, ThreadState var2) {
      var1.setline(1817);
      PyString.fromInterned("\n    Clone a node and give it the new owner document.\n    Called by Node.cloneNode and Document.importNode\n    ");
      var1.setline(1818);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("ownerDocument").__getattr__("isSameNode").__call__(var2, var1.getlocal(2)).__nonzero__()) {
         var1.setline(1819);
         var3 = var1.getglobal("xml").__getattr__("dom").__getattr__("UserDataHandler").__getattr__("NODE_CLONED");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1821);
         var3 = var1.getglobal("xml").__getattr__("dom").__getattr__("UserDataHandler").__getattr__("NODE_IMPORTED");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1822);
      var3 = var1.getlocal(0).__getattr__("nodeType");
      PyObject var10000 = var3._eq(var1.getglobal("Node").__getattr__("ELEMENT_NODE"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(1823);
         var3 = var1.getlocal(2).__getattr__("createElementNS").__call__(var2, var1.getlocal(0).__getattr__("namespaceURI"), var1.getlocal(0).__getattr__("nodeName"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1825);
         var3 = var1.getlocal(0).__getattr__("attributes").__getattr__("values").__call__(var2).__iter__();

         label96:
         while(true) {
            var1.setline(1825);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1830);
               if (var1.getlocal(1).__nonzero__()) {
                  var1.setline(1831);
                  var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

                  while(true) {
                     var1.setline(1831);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break label96;
                     }

                     var1.setlocal(7, var4);
                     var1.setline(1832);
                     var5 = var1.getglobal("_clone_node").__call__(var2, var1.getlocal(7), var1.getlocal(1), var1.getlocal(2));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(1833);
                     var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(8));
                  }
               }
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(1826);
            var1.getlocal(4).__getattr__("setAttributeNS").__call__(var2, var1.getlocal(5).__getattr__("namespaceURI"), var1.getlocal(5).__getattr__("nodeName"), var1.getlocal(5).__getattr__("value"));
            var1.setline(1827);
            var5 = var1.getlocal(4).__getattr__("getAttributeNodeNS").__call__(var2, var1.getlocal(5).__getattr__("namespaceURI"), var1.getlocal(5).__getattr__("localName"));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(1828);
            var5 = var1.getlocal(5).__getattr__("specified");
            var1.getlocal(6).__setattr__("specified", var5);
            var5 = null;
         }
      } else {
         var1.setline(1835);
         var3 = var1.getlocal(0).__getattr__("nodeType");
         var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_FRAGMENT_NODE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1836);
            var3 = var1.getlocal(2).__getattr__("createDocumentFragment").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1837);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(1838);
               var3 = var1.getlocal(0).__getattr__("childNodes").__iter__();

               while(true) {
                  var1.setline(1838);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(7, var4);
                  var1.setline(1839);
                  var5 = var1.getglobal("_clone_node").__call__(var2, var1.getlocal(7), var1.getlocal(1), var1.getlocal(2));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(1840);
                  var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(8));
               }
            }
         } else {
            var1.setline(1842);
            var3 = var1.getlocal(0).__getattr__("nodeType");
            var10000 = var3._eq(var1.getglobal("Node").__getattr__("TEXT_NODE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1843);
               var3 = var1.getlocal(2).__getattr__("createTextNode").__call__(var2, var1.getlocal(0).__getattr__("data"));
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(1844);
               var3 = var1.getlocal(0).__getattr__("nodeType");
               var10000 = var3._eq(var1.getglobal("Node").__getattr__("CDATA_SECTION_NODE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1845);
                  var3 = var1.getlocal(2).__getattr__("createCDATASection").__call__(var2, var1.getlocal(0).__getattr__("data"));
                  var1.setlocal(4, var3);
                  var3 = null;
               } else {
                  var1.setline(1846);
                  var3 = var1.getlocal(0).__getattr__("nodeType");
                  var10000 = var3._eq(var1.getglobal("Node").__getattr__("PROCESSING_INSTRUCTION_NODE"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1847);
                     var3 = var1.getlocal(2).__getattr__("createProcessingInstruction").__call__(var2, var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("data"));
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(1849);
                     var3 = var1.getlocal(0).__getattr__("nodeType");
                     var10000 = var3._eq(var1.getglobal("Node").__getattr__("COMMENT_NODE"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1850);
                        var3 = var1.getlocal(2).__getattr__("createComment").__call__(var2, var1.getlocal(0).__getattr__("data"));
                        var1.setlocal(4, var3);
                        var3 = null;
                     } else {
                        var1.setline(1851);
                        var3 = var1.getlocal(0).__getattr__("nodeType");
                        var10000 = var3._eq(var1.getglobal("Node").__getattr__("ATTRIBUTE_NODE"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1852);
                           var3 = var1.getlocal(2).__getattr__("createAttributeNS").__call__(var2, var1.getlocal(0).__getattr__("namespaceURI"), var1.getlocal(0).__getattr__("nodeName"));
                           var1.setlocal(4, var3);
                           var3 = null;
                           var1.setline(1854);
                           var3 = var1.getglobal("True");
                           var1.getlocal(4).__setattr__("specified", var3);
                           var3 = null;
                           var1.setline(1855);
                           var3 = var1.getlocal(0).__getattr__("value");
                           var1.getlocal(4).__setattr__("value", var3);
                           var3 = null;
                        } else {
                           var1.setline(1856);
                           var3 = var1.getlocal(0).__getattr__("nodeType");
                           var10000 = var3._eq(var1.getglobal("Node").__getattr__("DOCUMENT_TYPE_NODE"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(1885);
                              throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__(var2, PyString.fromInterned("Cannot clone node %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
                           }

                           var1.setline(1857);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var3 = var1.getlocal(0).__getattr__("ownerDocument");
                              var10000 = var3._isnot(var1.getlocal(2));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 var10000 = Py.None;
                                 throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                              }
                           }

                           var1.setline(1858);
                           var3 = var1.getglobal("xml").__getattr__("dom").__getattr__("UserDataHandler").__getattr__("NODE_IMPORTED");
                           var1.setlocal(3, var3);
                           var3 = null;
                           var1.setline(1859);
                           var3 = var1.getlocal(2).__getattr__("implementation").__getattr__("createDocumentType").__call__(var2, var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("publicId"), var1.getlocal(0).__getattr__("systemId"));
                           var1.setlocal(4, var3);
                           var3 = null;
                           var1.setline(1861);
                           var3 = var1.getlocal(2);
                           var1.getlocal(4).__setattr__("ownerDocument", var3);
                           var3 = null;
                           var1.setline(1862);
                           if (var1.getlocal(1).__nonzero__()) {
                              var1.setline(1863);
                              PyList var6 = new PyList(Py.EmptyObjects);
                              var1.getlocal(4).__getattr__("entities").__setattr__((String)"_seq", var6);
                              var3 = null;
                              var1.setline(1864);
                              var6 = new PyList(Py.EmptyObjects);
                              var1.getlocal(4).__getattr__("notations").__setattr__((String)"_seq", var6);
                              var3 = null;
                              var1.setline(1865);
                              var3 = var1.getlocal(0).__getattr__("notations").__getattr__("_seq").__iter__();

                              label68:
                              while(true) {
                                 var1.setline(1865);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(1871);
                                    var3 = var1.getlocal(0).__getattr__("entities").__getattr__("_seq").__iter__();

                                    while(true) {
                                       var1.setline(1871);
                                       var4 = var3.__iternext__();
                                       if (var4 == null) {
                                          break label68;
                                       }

                                       var1.setlocal(11, var4);
                                       var1.setline(1872);
                                       var5 = var1.getglobal("Entity").__call__(var2, var1.getlocal(11).__getattr__("nodeName"), var1.getlocal(11).__getattr__("publicId"), var1.getlocal(11).__getattr__("systemId"), var1.getlocal(11).__getattr__("notationName"));
                                       var1.setlocal(12, var5);
                                       var5 = null;
                                       var1.setline(1874);
                                       var5 = var1.getlocal(11).__getattr__("actualEncoding");
                                       var1.getlocal(12).__setattr__("actualEncoding", var5);
                                       var5 = null;
                                       var1.setline(1875);
                                       var5 = var1.getlocal(11).__getattr__("encoding");
                                       var1.getlocal(12).__setattr__("encoding", var5);
                                       var5 = null;
                                       var1.setline(1876);
                                       var5 = var1.getlocal(11).__getattr__("version");
                                       var1.getlocal(12).__setattr__("version", var5);
                                       var5 = null;
                                       var1.setline(1877);
                                       var5 = var1.getlocal(2);
                                       var1.getlocal(12).__setattr__("ownerDocument", var5);
                                       var5 = null;
                                       var1.setline(1878);
                                       var1.getlocal(4).__getattr__("entities").__getattr__("_seq").__getattr__("append").__call__(var2, var1.getlocal(12));
                                       var1.setline(1879);
                                       if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)PyString.fromInterned("_call_user_data_handler")).__nonzero__()) {
                                          var1.setline(1880);
                                          var1.getlocal(11).__getattr__("_call_user_data_handler").__call__(var2, var1.getlocal(3), var1.getlocal(9), var1.getlocal(12));
                                       }
                                    }
                                 }

                                 var1.setlocal(9, var4);
                                 var1.setline(1866);
                                 var5 = var1.getglobal("Notation").__call__(var2, var1.getlocal(9).__getattr__("nodeName"), var1.getlocal(9).__getattr__("publicId"), var1.getlocal(9).__getattr__("systemId"));
                                 var1.setlocal(10, var5);
                                 var5 = null;
                                 var1.setline(1867);
                                 var5 = var1.getlocal(2);
                                 var1.getlocal(10).__setattr__("ownerDocument", var5);
                                 var5 = null;
                                 var1.setline(1868);
                                 var1.getlocal(4).__getattr__("notations").__getattr__("_seq").__getattr__("append").__call__(var2, var1.getlocal(10));
                                 var1.setline(1869);
                                 if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("_call_user_data_handler")).__nonzero__()) {
                                    var1.setline(1870);
                                    var1.getlocal(9).__getattr__("_call_user_data_handler").__call__(var2, var1.getlocal(3), var1.getlocal(9), var1.getlocal(10));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      var1.setline(1890);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_call_user_data_handler")).__nonzero__()) {
         var1.setline(1891);
         var1.getlocal(0).__getattr__("_call_user_data_handler").__call__(var2, var1.getlocal(3), var1.getlocal(0), var1.getlocal(4));
      }

      var1.setline(1892);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _nssplit$222(PyFrame var1, ThreadState var2) {
      var1.setline(1896);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1897);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1898);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1900);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(1).__getitem__(Py.newInteger(0))});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _get_StringIO$223(PyFrame var1, ThreadState var2) {
      var1.setline(1905);
      String[] var3 = new String[]{"StringIO"};
      PyObject[] var5 = imp.importFrom("StringIO", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(1906);
      PyObject var6 = var1.getlocal(0).__call__(var2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _do_pulldom_parse$224(PyFrame var1, ThreadState var2) {
      var1.setline(1909);
      PyObject var10000 = var1.getlocal(0);
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1910);
      var6 = var1.getlocal(3).__getattr__("getEvent").__call__(var2);
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1911);
      var1.getlocal(3).__getattr__("expandNode").__call__(var2, var1.getlocal(5));
      var1.setline(1912);
      var1.getlocal(3).__getattr__("clear").__call__(var2);
      var1.setline(1913);
      var1.getlocal(5).__getattr__("normalize").__call__(var2);
      var1.setline(1914);
      var6 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject parse$225(PyFrame var1, ThreadState var2) {
      var1.setline(1917);
      PyString.fromInterned("Parse a file into a DOM by filename or file object.");
      var1.setline(1918);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
            var10000 = var3._ne(PyString.fromInterned("java"));
            var3 = null;
         }
      }

      String[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(1919);
         String[] var7 = new String[]{"expatbuilder"};
         PyObject[] var9 = imp.importFrom("xml.dom", var7, var1, -1);
         PyObject var8 = var9[0];
         var1.setlocal(3, var8);
         var4 = null;
         var1.setline(1920);
         var3 = var1.getlocal(3).__getattr__("parse").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1922);
         var4 = new String[]{"pulldom"};
         PyObject[] var6 = imp.importFrom("xml.dom", var4, var1, -1);
         PyObject var5 = var6[0];
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(1923);
         var3 = var1.getglobal("_do_pulldom_parse").__call__((ThreadState)var2, var1.getlocal(4).__getattr__("parse"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("parser"), var1.getlocal(1), PyString.fromInterned("bufsize"), var1.getlocal(2)})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parseString$226(PyFrame var1, ThreadState var2) {
      var1.setline(1927);
      PyString.fromInterned("Parse a file into a DOM from a string.");
      var1.setline(1928);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned("java"));
         var3 = null;
      }

      String[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(1929);
         String[] var7 = new String[]{"expatbuilder"};
         PyObject[] var9 = imp.importFrom("xml.dom", var7, var1, -1);
         PyObject var8 = var9[0];
         var1.setlocal(2, var8);
         var4 = null;
         var1.setline(1930);
         var3 = var1.getlocal(2).__getattr__("parseString").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1932);
         var4 = new String[]{"pulldom"};
         PyObject[] var6 = imp.importFrom("xml.dom", var4, var1, -1);
         PyObject var5 = var6[0];
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(1933);
         var3 = var1.getglobal("_do_pulldom_parse").__call__((ThreadState)var2, var1.getlocal(3).__getattr__("parseString"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("parser"), var1.getlocal(1)})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getDOMImplementation$227(PyFrame var1, ThreadState var2) {
      var1.setline(1937);
      PyObject var7;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(1938);
         PyObject var3;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("StringTypes")).__nonzero__()) {
            var1.setline(1939);
            var3 = var1.getglobal("domreg").__getattr__("_parse_feature_string").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(1940);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(1940);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(1941);
            if (var1.getglobal("Document").__getattr__("implementation").__getattr__("hasFeature").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__()) {
               var1.setline(1942);
               var7 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var7;
            }
         }
      }

      var1.setline(1943);
      var7 = var1.getglobal("Document").__getattr__("implementation");
      var1.f_lasti = -1;
      return var7;
   }

   public minidom$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Node$1 = Py.newCode(0, var2, var1, "Node", 34, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __nonzero__$2 = Py.newCode(1, var2, var1, "__nonzero__", 43, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding"};
      toxml$3 = Py.newCode(2, var2, var1, "toxml", 46, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "indent", "newl", "encoding", "writer", "codecs"};
      toprettyxml$4 = Py.newCode(4, var2, var1, "toprettyxml", 49, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      hasChildNodes$5 = Py.newCode(1, var2, var1, "hasChildNodes", 64, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_childNodes$6 = Py.newCode(1, var2, var1, "_get_childNodes", 70, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_firstChild$7 = Py.newCode(1, var2, var1, "_get_firstChild", 73, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_lastChild$8 = Py.newCode(1, var2, var1, "_get_lastChild", 77, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild", "refChild", "c", "index", "node"};
      insertBefore$9 = Py.newCode(3, var2, var1, "insertBefore", 81, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "c"};
      appendChild$10 = Py.newCode(2, var2, var1, "appendChild", 113, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild", "oldChild", "refChild", "index"};
      replaceChild$11 = Py.newCode(3, var2, var1, "replaceChild", 130, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldChild"};
      removeChild$12 = Py.newCode(2, var2, var1, "removeChild", 162, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "L", "child", "node"};
      normalize$13 = Py.newCode(1, var2, var1, "normalize", 178, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "deep"};
      cloneNode$14 = Py.newCode(2, var2, var1, "cloneNode", 205, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "feature", "version"};
      isSupported$15 = Py.newCode(3, var2, var1, "isSupported", 208, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_localName$16 = Py.newCode(1, var2, var1, "_get_localName", 211, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      isSameNode$17 = Py.newCode(2, var2, var1, "isSameNode", 217, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "feature"};
      getInterface$18 = Py.newCode(2, var2, var1, "getInterface", 220, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      getUserData$19 = Py.newCode(2, var2, var1, "getUserData", 230, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "data", "handler", "old", "d"};
      setUserData$20 = Py.newCode(4, var2, var1, "setUserData", 236, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "operation", "src", "dst", "key", "data", "handler"};
      _call_user_data_handler$21 = Py.newCode(4, var2, var1, "_call_user_data_handler", 254, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child"};
      unlink$22 = Py.newCode(1, var2, var1, "unlink", 262, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "childNodes", "last"};
      _append_child$23 = Py.newCode(2, var2, var1, "_append_child", 276, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      _in_document$24 = Py.newCode(1, var2, var1, "_in_document", 286, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"writer", "data"};
      _write_data$25 = Py.newCode(2, var2, var1, "_write_data", 294, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"parent", "name", "rc", "node"};
      _get_elements_by_tagName_helper$26 = Py.newCode(3, var2, var1, "_get_elements_by_tagName_helper", 301, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"parent", "nsURI", "localName", "rc", "node"};
      _get_elements_by_tagName_ns_helper$27 = Py.newCode(4, var2, var1, "_get_elements_by_tagName_ns_helper", 309, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocumentFragment$28 = Py.newCode(0, var2, var1, "DocumentFragment", 318, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$29 = Py.newCode(1, var2, var1, "__init__", 332, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Attr$30 = Py.newCode(0, var2, var1, "Attr", 336, false, false, self, 30, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "qName", "namespaceURI", "localName", "prefix", "d"};
      __init__$31 = Py.newCode(5, var2, var1, "__init__", 345, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_localName$32 = Py.newCode(1, var2, var1, "_get_localName", 359, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_specified$33 = Py.newCode(1, var2, var1, "_get_specified", 362, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "d", "d2"};
      __setattr__$34 = Py.newCode(3, var2, var1, "__setattr__", 365, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "nsuri", "d", "newName"};
      _set_prefix$35 = Py.newCode(2, var2, var1, "_set_prefix", 380, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "d"};
      _set_value$36 = Py.newCode(2, var2, var1, "_set_value", 396, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem", "child"};
      unlink$37 = Py.newCode(1, var2, var1, "unlink", 403, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doc", "elem", "info"};
      _get_isId$38 = Py.newCode(1, var2, var1, "_get_isId", 420, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doc", "elem", "info"};
      _get_schemaType$39 = Py.newCode(1, var2, var1, "_get_schemaType", 436, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NamedNodeMap$40 = Py.newCode(0, var2, var1, "NamedNodeMap", 455, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "attrs", "attrsNS", "ownerElement"};
      __init__$41 = Py.newCode(4, var2, var1, "__init__", 466, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_length$42 = Py.newCode(1, var2, var1, "_get_length", 471, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      item$43 = Py.newCode(2, var2, var1, "item", 474, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "L", "node"};
      items$44 = Py.newCode(1, var2, var1, "items", 480, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "L", "node"};
      itemsNS$45 = Py.newCode(1, var2, var1, "itemsNS", 486, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$46 = Py.newCode(2, var2, var1, "has_key", 492, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$47 = Py.newCode(1, var2, var1, "keys", 498, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keysNS$48 = Py.newCode(1, var2, var1, "keysNS", 501, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$49 = Py.newCode(1, var2, var1, "values", 504, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      get$50 = Py.newCode(3, var2, var1, "get", 507, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$51 = Py.newCode(2, var2, var1, "__cmp__", 513, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attname_or_tuple"};
      __getitem__$52 = Py.newCode(2, var2, var1, "__getitem__", 519, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attname", "value", "node"};
      __setitem__$53 = Py.newCode(3, var2, var1, "__setitem__", 526, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getNamedItem$54 = Py.newCode(2, var2, var1, "getNamedItem", 541, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getNamedItemNS$55 = Py.newCode(3, var2, var1, "getNamedItemNS", 547, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n"};
      removeNamedItem$56 = Py.newCode(2, var2, var1, "removeNamedItem", 553, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName", "n"};
      removeNamedItemNS$57 = Py.newCode(3, var2, var1, "removeNamedItemNS", 565, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "old"};
      setNamedItem$58 = Py.newCode(2, var2, var1, "setNamedItem", 577, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      setNamedItemNS$59 = Py.newCode(2, var2, var1, "setNamedItemNS", 590, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attname_or_tuple", "node"};
      __delitem__$60 = Py.newCode(2, var2, var1, "__delitem__", 593, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$61 = Py.newCode(1, var2, var1, "__getstate__", 598, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      __setstate__$62 = Py.newCode(2, var2, var1, "__setstate__", 601, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TypeInfo$63 = Py.newCode(0, var2, var1, "TypeInfo", 610, false, false, self, 63, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "namespace", "name"};
      __init__$64 = Py.newCode(3, var2, var1, "__init__", 613, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$65 = Py.newCode(1, var2, var1, "__repr__", 617, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_name$66 = Py.newCode(1, var2, var1, "_get_name", 623, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_namespace$67 = Py.newCode(1, var2, var1, "_get_namespace", 626, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Element$68 = Py.newCode(0, var2, var1, "Element", 631, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tagName", "namespaceURI", "prefix", "localName"};
      __init__$69 = Py.newCode(5, var2, var1, "__init__", 645, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_localName$70 = Py.newCode(1, var2, var1, "_get_localName", 660, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_tagName$71 = Py.newCode(1, var2, var1, "_get_tagName", 663, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      unlink$72 = Py.newCode(1, var2, var1, "unlink", 666, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attname"};
      getAttribute$73 = Py.newCode(2, var2, var1, "getAttribute", 673, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getAttributeNS$74 = Py.newCode(3, var2, var1, "getAttributeNS", 679, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attname", "value", "attr", "d"};
      setAttribute$75 = Py.newCode(3, var2, var1, "setAttribute", 685, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "qualifiedName", "value", "prefix", "localname", "attr", "d"};
      setAttributeNS$76 = Py.newCode(4, var2, var1, "setAttributeNS", 700, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrname"};
      getAttributeNode$77 = Py.newCode(2, var2, var1, "getAttributeNode", 722, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getAttributeNodeNS$78 = Py.newCode(3, var2, var1, "getAttributeNodeNS", 725, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "old1", "old2"};
      setAttributeNode$79 = Py.newCode(2, var2, var1, "setAttributeNode", 728, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attr"};
      removeAttribute$80 = Py.newCode(2, var2, var1, "removeAttribute", 748, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName", "attr"};
      removeAttributeNS$81 = Py.newCode(3, var2, var1, "removeAttributeNS", 755, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      removeAttributeNode$82 = Py.newCode(2, var2, var1, "removeAttributeNode", 762, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      hasAttribute$83 = Py.newCode(2, var2, var1, "hasAttribute", 777, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      hasAttributeNS$84 = Py.newCode(3, var2, var1, "hasAttributeNS", 780, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getElementsByTagName$85 = Py.newCode(2, var2, var1, "getElementsByTagName", 783, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getElementsByTagNameNS$86 = Py.newCode(3, var2, var1, "getElementsByTagNameNS", 786, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$87 = Py.newCode(1, var2, var1, "__repr__", 790, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl", "attrs", "a_names", "a_name", "node"};
      writexml$88 = Py.newCode(5, var2, var1, "writexml", 793, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_attributes$89 = Py.newCode(1, var2, var1, "_get_attributes", 821, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      hasAttributes$90 = Py.newCode(1, var2, var1, "hasAttributes", 824, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "idAttr"};
      setIdAttribute$91 = Py.newCode(2, var2, var1, "setIdAttribute", 832, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName", "idAttr"};
      setIdAttributeNS$92 = Py.newCode(3, var2, var1, "setIdAttributeNS", 836, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "idAttr"};
      setIdAttributeNode$93 = Py.newCode(2, var2, var1, "setIdAttributeNode", 840, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"element", "attr"};
      _set_attribute_node$94 = Py.newCode(2, var2, var1, "_set_attribute_node", 857, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Childless$95 = Py.newCode(0, var2, var1, "Childless", 868, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _get_firstChild$96 = Py.newCode(1, var2, var1, "_get_firstChild", 878, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_lastChild$97 = Py.newCode(1, var2, var1, "_get_lastChild", 881, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      appendChild$98 = Py.newCode(2, var2, var1, "appendChild", 884, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      hasChildNodes$99 = Py.newCode(1, var2, var1, "hasChildNodes", 888, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild", "refChild"};
      insertBefore$100 = Py.newCode(3, var2, var1, "insertBefore", 891, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldChild"};
      removeChild$101 = Py.newCode(2, var2, var1, "removeChild", 895, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      normalize$102 = Py.newCode(1, var2, var1, "normalize", 899, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild", "oldChild"};
      replaceChild$103 = Py.newCode(3, var2, var1, "replaceChild", 903, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ProcessingInstruction$104 = Py.newCode(0, var2, var1, "ProcessingInstruction", 908, false, false, self, 104, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "target", "data"};
      __init__$105 = Py.newCode(3, var2, var1, "__init__", 911, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_data$106 = Py.newCode(1, var2, var1, "_get_data", 915, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "d"};
      _set_data$107 = Py.newCode(2, var2, var1, "_set_data", 917, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_target$108 = Py.newCode(1, var2, var1, "_get_target", 921, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "d"};
      _set_target$109 = Py.newCode(2, var2, var1, "_set_target", 923, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      __setattr__$110 = Py.newCode(3, var2, var1, "__setattr__", 927, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl"};
      writexml$111 = Py.newCode(5, var2, var1, "writexml", 935, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CharacterData$112 = Py.newCode(0, var2, var1, "CharacterData", 939, false, false, self, 112, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _get_length$113 = Py.newCode(1, var2, var1, "_get_length", 940, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_data$114 = Py.newCode(1, var2, var1, "_get_data", 944, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "d"};
      _set_data$115 = Py.newCode(2, var2, var1, "_set_data", 946, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      __setattr__$116 = Py.newCode(3, var2, var1, "__setattr__", 953, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "dotdotdot"};
      __repr__$117 = Py.newCode(1, var2, var1, "__repr__", 959, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "count"};
      substringData$118 = Py.newCode(3, var2, var1, "substringData", 968, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      appendData$119 = Py.newCode(2, var2, var1, "appendData", 977, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "arg"};
      insertData$120 = Py.newCode(3, var2, var1, "insertData", 980, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "count"};
      deleteData$121 = Py.newCode(3, var2, var1, "deleteData", 989, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "count", "arg"};
      replaceData$122 = Py.newCode(4, var2, var1, "replaceData", 999, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Text$123 = Py.newCode(0, var2, var1, "Text", 1013, false, false, self, 123, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "offset", "newText", "next"};
      splitText$124 = Py.newCode(2, var2, var1, "splitText", 1023, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl"};
      writexml$125 = Py.newCode(5, var2, var1, "writexml", 1038, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "L", "n"};
      _get_wholeText$126 = Py.newCode(1, var2, var1, "_get_wholeText", 1043, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content", "parent", "n", "next", "d"};
      replaceWholeText$127 = Py.newCode(2, var2, var1, "replaceWholeText", 1061, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem", "info"};
      _get_isWhitespaceInElementContent$128 = Py.newCode(1, var2, var1, "_get_isWhitespaceInElementContent", 1091, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "c"};
      _get_containing_element$129 = Py.newCode(1, var2, var1, "_get_containing_element", 1110, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "c"};
      _get_containing_entref$130 = Py.newCode(1, var2, var1, "_get_containing_entref", 1118, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Comment$131 = Py.newCode(0, var2, var1, "Comment", 1127, false, false, self, 131, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data"};
      __init__$132 = Py.newCode(2, var2, var1, "__init__", 1131, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl"};
      writexml$133 = Py.newCode(5, var2, var1, "writexml", 1134, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CDATASection$134 = Py.newCode(0, var2, var1, "CDATASection", 1140, false, false, self, 134, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl"};
      writexml$135 = Py.newCode(5, var2, var1, "writexml", 1149, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ReadOnlySequentialNamedNodeMap$136 = Py.newCode(0, var2, var1, "ReadOnlySequentialNamedNodeMap", 1155, false, false, self, 136, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "seq"};
      __init__$137 = Py.newCode(2, var2, var1, "__init__", 1158, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$138 = Py.newCode(1, var2, var1, "__len__", 1162, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_length$139 = Py.newCode(1, var2, var1, "_get_length", 1165, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n"};
      getNamedItem$140 = Py.newCode(2, var2, var1, "getNamedItem", 1168, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName", "n"};
      getNamedItemNS$141 = Py.newCode(3, var2, var1, "getNamedItemNS", 1173, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name_or_tuple", "node"};
      __getitem__$142 = Py.newCode(2, var2, var1, "__getitem__", 1178, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      item$143 = Py.newCode(2, var2, var1, "item", 1187, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      removeNamedItem$144 = Py.newCode(2, var2, var1, "removeNamedItem", 1195, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      removeNamedItemNS$145 = Py.newCode(3, var2, var1, "removeNamedItemNS", 1199, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      setNamedItem$146 = Py.newCode(2, var2, var1, "setNamedItem", 1203, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      setNamedItemNS$147 = Py.newCode(2, var2, var1, "setNamedItemNS", 1207, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$148 = Py.newCode(1, var2, var1, "__getstate__", 1211, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      __setstate__$149 = Py.newCode(2, var2, var1, "__setstate__", 1214, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Identified$150 = Py.newCode(0, var2, var1, "Identified", 1221, false, false, self, 150, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "publicId", "systemId"};
      _identified_mixin_init$151 = Py.newCode(3, var2, var1, "_identified_mixin_init", 1227, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_publicId$152 = Py.newCode(1, var2, var1, "_get_publicId", 1231, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_systemId$153 = Py.newCode(1, var2, var1, "_get_systemId", 1234, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocumentType$154 = Py.newCode(0, var2, var1, "DocumentType", 1237, false, false, self, 154, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "qualifiedName", "prefix", "localname"};
      __init__$155 = Py.newCode(2, var2, var1, "__init__", 1245, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_internalSubset$156 = Py.newCode(1, var2, var1, "_get_internalSubset", 1253, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "deep", "clone", "operation", "n", "notation", "e", "entity"};
      cloneNode$157 = Py.newCode(2, var2, var1, "cloneNode", 1256, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl"};
      writexml$158 = Py.newCode(5, var2, var1, "writexml", 1283, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Entity$159 = Py.newCode(0, var2, var1, "Entity", 1297, false, false, self, 159, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "publicId", "systemId", "notation"};
      __init__$160 = Py.newCode(5, var2, var1, "__init__", 1306, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_actualEncoding$161 = Py.newCode(1, var2, var1, "_get_actualEncoding", 1312, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_encoding$162 = Py.newCode(1, var2, var1, "_get_encoding", 1315, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_version$163 = Py.newCode(1, var2, var1, "_get_version", 1318, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild"};
      appendChild$164 = Py.newCode(2, var2, var1, "appendChild", 1321, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild", "refChild"};
      insertBefore$165 = Py.newCode(3, var2, var1, "insertBefore", 1325, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldChild"};
      removeChild$166 = Py.newCode(2, var2, var1, "removeChild", 1329, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newChild", "oldChild"};
      replaceChild$167 = Py.newCode(3, var2, var1, "replaceChild", 1333, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Notation$168 = Py.newCode(0, var2, var1, "Notation", 1337, false, false, self, 168, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "publicId", "systemId"};
      __init__$169 = Py.newCode(4, var2, var1, "__init__", 1341, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DOMImplementation$170 = Py.newCode(0, var2, var1, "DOMImplementation", 1346, false, false, self, 170, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "feature", "version"};
      hasFeature$171 = Py.newCode(3, var2, var1, "hasFeature", 1357, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "qualifiedName", "doctype", "doc", "add_root_element", "prefix", "localname", "element"};
      createDocument$172 = Py.newCode(4, var2, var1, "createDocument", 1362, false, false, self, 172, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "qualifiedName", "publicId", "systemId", "doctype"};
      createDocumentType$173 = Py.newCode(4, var2, var1, "createDocumentType", 1407, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "feature"};
      getInterface$174 = Py.newCode(2, var2, var1, "getInterface", 1415, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _create_document$175 = Py.newCode(1, var2, var1, "_create_document", 1422, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ElementInfo$176 = Py.newCode(0, var2, var1, "ElementInfo", 1425, false, false, self, 176, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name"};
      __init__$177 = Py.newCode(2, var2, var1, "__init__", 1436, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aname"};
      getAttributeType$178 = Py.newCode(2, var2, var1, "getAttributeType", 1439, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getAttributeTypeNS$179 = Py.newCode(3, var2, var1, "getAttributeTypeNS", 1442, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isElementContent$180 = Py.newCode(1, var2, var1, "isElementContent", 1445, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isEmpty$181 = Py.newCode(1, var2, var1, "isEmpty", 1448, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aname"};
      isId$182 = Py.newCode(2, var2, var1, "isId", 1453, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      isIdNS$183 = Py.newCode(3, var2, var1, "isIdNS", 1457, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __getstate__$184 = Py.newCode(1, var2, var1, "__getstate__", 1461, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      __setstate__$185 = Py.newCode(2, var2, var1, "__setstate__", 1464, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      _clear_id_cache$186 = Py.newCode(1, var2, var1, "_clear_id_cache", 1467, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Document$187 = Py.newCode(0, var2, var1, "Document", 1475, false, false, self, 187, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$188 = Py.newCode(1, var2, var1, "__init__", 1501, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "key"};
      _get_elem_info$189 = Py.newCode(2, var2, var1, "_get_elem_info", 1509, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_actualEncoding$190 = Py.newCode(1, var2, var1, "_get_actualEncoding", 1516, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_doctype$191 = Py.newCode(1, var2, var1, "_get_doctype", 1519, false, false, self, 191, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_documentURI$192 = Py.newCode(1, var2, var1, "_get_documentURI", 1522, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_encoding$193 = Py.newCode(1, var2, var1, "_get_encoding", 1525, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_errorHandler$194 = Py.newCode(1, var2, var1, "_get_errorHandler", 1528, false, false, self, 194, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_standalone$195 = Py.newCode(1, var2, var1, "_get_standalone", 1531, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_strictErrorChecking$196 = Py.newCode(1, var2, var1, "_get_strictErrorChecking", 1534, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_version$197 = Py.newCode(1, var2, var1, "_get_version", 1537, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      appendChild$198 = Py.newCode(2, var2, var1, "appendChild", 1540, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldChild"};
      removeChild$199 = Py.newCode(2, var2, var1, "removeChild", 1556, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node"};
      _get_documentElement$200 = Py.newCode(1, var2, var1, "_get_documentElement", 1568, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unlink$201 = Py.newCode(1, var2, var1, "unlink", 1573, false, false, self, 201, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "deep", "clone", "n", "childclone"};
      cloneNode$202 = Py.newCode(2, var2, var1, "cloneNode", 1579, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      createDocumentFragment$203 = Py.newCode(1, var2, var1, "createDocumentFragment", 1600, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tagName", "e"};
      createElement$204 = Py.newCode(2, var2, var1, "createElement", 1605, false, false, self, 204, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "t"};
      createTextNode$205 = Py.newCode(2, var2, var1, "createTextNode", 1610, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "c"};
      createCDATASection$206 = Py.newCode(2, var2, var1, "createCDATASection", 1618, false, false, self, 206, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "c"};
      createComment$207 = Py.newCode(2, var2, var1, "createComment", 1626, false, false, self, 207, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data", "p"};
      createProcessingInstruction$208 = Py.newCode(3, var2, var1, "createProcessingInstruction", 1631, false, false, self, 208, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "qName", "a"};
      createAttribute$209 = Py.newCode(2, var2, var1, "createAttribute", 1636, false, false, self, 209, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "qualifiedName", "prefix", "localName", "e"};
      createElementNS$210 = Py.newCode(3, var2, var1, "createElementNS", 1642, false, false, self, 210, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "qualifiedName", "prefix", "localName", "a"};
      createAttributeNS$211 = Py.newCode(3, var2, var1, "createAttributeNS", 1648, false, false, self, 211, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "notationName", "e"};
      _create_entity$212 = Py.newCode(5, var2, var1, "_create_entity", 1658, false, false, self, 212, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "n"};
      _create_notation$213 = Py.newCode(4, var2, var1, "_create_notation", 1663, false, false, self, 213, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "stack", "result", "node", "_[1688_26]", "child", "info", "attr"};
      getElementById$214 = Py.newCode(2, var2, var1, "getElementById", 1668, false, false, self, 214, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getElementsByTagName$215 = Py.newCode(2, var2, var1, "getElementsByTagName", 1726, false, false, self, 215, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespaceURI", "localName"};
      getElementsByTagNameNS$216 = Py.newCode(3, var2, var1, "getElementsByTagNameNS", 1729, false, false, self, 216, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "feature", "version"};
      isSupported$217 = Py.newCode(3, var2, var1, "isSupported", 1733, false, false, self, 217, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "deep"};
      importNode$218 = Py.newCode(3, var2, var1, "importNode", 1736, false, false, self, 218, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "writer", "indent", "addindent", "newl", "encoding", "node"};
      writexml$219 = Py.newCode(6, var2, var1, "writexml", 1743, false, false, self, 219, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "namespaceURI", "name", "prefix", "localName", "element", "is_id", "d"};
      renameNode$220 = Py.newCode(4, var2, var1, "renameNode", 1754, false, false, self, 220, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "deep", "newOwnerDocument", "operation", "clone", "attr", "a", "child", "c", "n", "notation", "e", "entity"};
      _clone_node$221 = Py.newCode(3, var2, var1, "_clone_node", 1813, false, false, self, 221, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"qualifiedName", "fields"};
      _nssplit$222 = Py.newCode(1, var2, var1, "_nssplit", 1895, false, false, self, 222, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"StringIO"};
      _get_StringIO$223 = Py.newCode(0, var2, var1, "_get_StringIO", 1903, false, false, self, 223, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "args", "kwargs", "events", "toktype", "rootNode"};
      _do_pulldom_parse$224 = Py.newCode(3, var2, var1, "_do_pulldom_parse", 1908, false, false, self, 224, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "parser", "bufsize", "expatbuilder", "pulldom"};
      parse$225 = Py.newCode(3, var2, var1, "parse", 1916, false, false, self, 225, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "parser", "expatbuilder", "pulldom"};
      parseString$226 = Py.newCode(2, var2, var1, "parseString", 1926, false, false, self, 226, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"features", "f", "v"};
      getDOMImplementation$227 = Py.newCode(1, var2, var1, "getDOMImplementation", 1936, false, false, self, 227, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new minidom$py("xml/dom/minidom$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(minidom$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Node$1(var2, var3);
         case 2:
            return this.__nonzero__$2(var2, var3);
         case 3:
            return this.toxml$3(var2, var3);
         case 4:
            return this.toprettyxml$4(var2, var3);
         case 5:
            return this.hasChildNodes$5(var2, var3);
         case 6:
            return this._get_childNodes$6(var2, var3);
         case 7:
            return this._get_firstChild$7(var2, var3);
         case 8:
            return this._get_lastChild$8(var2, var3);
         case 9:
            return this.insertBefore$9(var2, var3);
         case 10:
            return this.appendChild$10(var2, var3);
         case 11:
            return this.replaceChild$11(var2, var3);
         case 12:
            return this.removeChild$12(var2, var3);
         case 13:
            return this.normalize$13(var2, var3);
         case 14:
            return this.cloneNode$14(var2, var3);
         case 15:
            return this.isSupported$15(var2, var3);
         case 16:
            return this._get_localName$16(var2, var3);
         case 17:
            return this.isSameNode$17(var2, var3);
         case 18:
            return this.getInterface$18(var2, var3);
         case 19:
            return this.getUserData$19(var2, var3);
         case 20:
            return this.setUserData$20(var2, var3);
         case 21:
            return this._call_user_data_handler$21(var2, var3);
         case 22:
            return this.unlink$22(var2, var3);
         case 23:
            return this._append_child$23(var2, var3);
         case 24:
            return this._in_document$24(var2, var3);
         case 25:
            return this._write_data$25(var2, var3);
         case 26:
            return this._get_elements_by_tagName_helper$26(var2, var3);
         case 27:
            return this._get_elements_by_tagName_ns_helper$27(var2, var3);
         case 28:
            return this.DocumentFragment$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.Attr$30(var2, var3);
         case 31:
            return this.__init__$31(var2, var3);
         case 32:
            return this._get_localName$32(var2, var3);
         case 33:
            return this._get_specified$33(var2, var3);
         case 34:
            return this.__setattr__$34(var2, var3);
         case 35:
            return this._set_prefix$35(var2, var3);
         case 36:
            return this._set_value$36(var2, var3);
         case 37:
            return this.unlink$37(var2, var3);
         case 38:
            return this._get_isId$38(var2, var3);
         case 39:
            return this._get_schemaType$39(var2, var3);
         case 40:
            return this.NamedNodeMap$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this._get_length$42(var2, var3);
         case 43:
            return this.item$43(var2, var3);
         case 44:
            return this.items$44(var2, var3);
         case 45:
            return this.itemsNS$45(var2, var3);
         case 46:
            return this.has_key$46(var2, var3);
         case 47:
            return this.keys$47(var2, var3);
         case 48:
            return this.keysNS$48(var2, var3);
         case 49:
            return this.values$49(var2, var3);
         case 50:
            return this.get$50(var2, var3);
         case 51:
            return this.__cmp__$51(var2, var3);
         case 52:
            return this.__getitem__$52(var2, var3);
         case 53:
            return this.__setitem__$53(var2, var3);
         case 54:
            return this.getNamedItem$54(var2, var3);
         case 55:
            return this.getNamedItemNS$55(var2, var3);
         case 56:
            return this.removeNamedItem$56(var2, var3);
         case 57:
            return this.removeNamedItemNS$57(var2, var3);
         case 58:
            return this.setNamedItem$58(var2, var3);
         case 59:
            return this.setNamedItemNS$59(var2, var3);
         case 60:
            return this.__delitem__$60(var2, var3);
         case 61:
            return this.__getstate__$61(var2, var3);
         case 62:
            return this.__setstate__$62(var2, var3);
         case 63:
            return this.TypeInfo$63(var2, var3);
         case 64:
            return this.__init__$64(var2, var3);
         case 65:
            return this.__repr__$65(var2, var3);
         case 66:
            return this._get_name$66(var2, var3);
         case 67:
            return this._get_namespace$67(var2, var3);
         case 68:
            return this.Element$68(var2, var3);
         case 69:
            return this.__init__$69(var2, var3);
         case 70:
            return this._get_localName$70(var2, var3);
         case 71:
            return this._get_tagName$71(var2, var3);
         case 72:
            return this.unlink$72(var2, var3);
         case 73:
            return this.getAttribute$73(var2, var3);
         case 74:
            return this.getAttributeNS$74(var2, var3);
         case 75:
            return this.setAttribute$75(var2, var3);
         case 76:
            return this.setAttributeNS$76(var2, var3);
         case 77:
            return this.getAttributeNode$77(var2, var3);
         case 78:
            return this.getAttributeNodeNS$78(var2, var3);
         case 79:
            return this.setAttributeNode$79(var2, var3);
         case 80:
            return this.removeAttribute$80(var2, var3);
         case 81:
            return this.removeAttributeNS$81(var2, var3);
         case 82:
            return this.removeAttributeNode$82(var2, var3);
         case 83:
            return this.hasAttribute$83(var2, var3);
         case 84:
            return this.hasAttributeNS$84(var2, var3);
         case 85:
            return this.getElementsByTagName$85(var2, var3);
         case 86:
            return this.getElementsByTagNameNS$86(var2, var3);
         case 87:
            return this.__repr__$87(var2, var3);
         case 88:
            return this.writexml$88(var2, var3);
         case 89:
            return this._get_attributes$89(var2, var3);
         case 90:
            return this.hasAttributes$90(var2, var3);
         case 91:
            return this.setIdAttribute$91(var2, var3);
         case 92:
            return this.setIdAttributeNS$92(var2, var3);
         case 93:
            return this.setIdAttributeNode$93(var2, var3);
         case 94:
            return this._set_attribute_node$94(var2, var3);
         case 95:
            return this.Childless$95(var2, var3);
         case 96:
            return this._get_firstChild$96(var2, var3);
         case 97:
            return this._get_lastChild$97(var2, var3);
         case 98:
            return this.appendChild$98(var2, var3);
         case 99:
            return this.hasChildNodes$99(var2, var3);
         case 100:
            return this.insertBefore$100(var2, var3);
         case 101:
            return this.removeChild$101(var2, var3);
         case 102:
            return this.normalize$102(var2, var3);
         case 103:
            return this.replaceChild$103(var2, var3);
         case 104:
            return this.ProcessingInstruction$104(var2, var3);
         case 105:
            return this.__init__$105(var2, var3);
         case 106:
            return this._get_data$106(var2, var3);
         case 107:
            return this._set_data$107(var2, var3);
         case 108:
            return this._get_target$108(var2, var3);
         case 109:
            return this._set_target$109(var2, var3);
         case 110:
            return this.__setattr__$110(var2, var3);
         case 111:
            return this.writexml$111(var2, var3);
         case 112:
            return this.CharacterData$112(var2, var3);
         case 113:
            return this._get_length$113(var2, var3);
         case 114:
            return this._get_data$114(var2, var3);
         case 115:
            return this._set_data$115(var2, var3);
         case 116:
            return this.__setattr__$116(var2, var3);
         case 117:
            return this.__repr__$117(var2, var3);
         case 118:
            return this.substringData$118(var2, var3);
         case 119:
            return this.appendData$119(var2, var3);
         case 120:
            return this.insertData$120(var2, var3);
         case 121:
            return this.deleteData$121(var2, var3);
         case 122:
            return this.replaceData$122(var2, var3);
         case 123:
            return this.Text$123(var2, var3);
         case 124:
            return this.splitText$124(var2, var3);
         case 125:
            return this.writexml$125(var2, var3);
         case 126:
            return this._get_wholeText$126(var2, var3);
         case 127:
            return this.replaceWholeText$127(var2, var3);
         case 128:
            return this._get_isWhitespaceInElementContent$128(var2, var3);
         case 129:
            return this._get_containing_element$129(var2, var3);
         case 130:
            return this._get_containing_entref$130(var2, var3);
         case 131:
            return this.Comment$131(var2, var3);
         case 132:
            return this.__init__$132(var2, var3);
         case 133:
            return this.writexml$133(var2, var3);
         case 134:
            return this.CDATASection$134(var2, var3);
         case 135:
            return this.writexml$135(var2, var3);
         case 136:
            return this.ReadOnlySequentialNamedNodeMap$136(var2, var3);
         case 137:
            return this.__init__$137(var2, var3);
         case 138:
            return this.__len__$138(var2, var3);
         case 139:
            return this._get_length$139(var2, var3);
         case 140:
            return this.getNamedItem$140(var2, var3);
         case 141:
            return this.getNamedItemNS$141(var2, var3);
         case 142:
            return this.__getitem__$142(var2, var3);
         case 143:
            return this.item$143(var2, var3);
         case 144:
            return this.removeNamedItem$144(var2, var3);
         case 145:
            return this.removeNamedItemNS$145(var2, var3);
         case 146:
            return this.setNamedItem$146(var2, var3);
         case 147:
            return this.setNamedItemNS$147(var2, var3);
         case 148:
            return this.__getstate__$148(var2, var3);
         case 149:
            return this.__setstate__$149(var2, var3);
         case 150:
            return this.Identified$150(var2, var3);
         case 151:
            return this._identified_mixin_init$151(var2, var3);
         case 152:
            return this._get_publicId$152(var2, var3);
         case 153:
            return this._get_systemId$153(var2, var3);
         case 154:
            return this.DocumentType$154(var2, var3);
         case 155:
            return this.__init__$155(var2, var3);
         case 156:
            return this._get_internalSubset$156(var2, var3);
         case 157:
            return this.cloneNode$157(var2, var3);
         case 158:
            return this.writexml$158(var2, var3);
         case 159:
            return this.Entity$159(var2, var3);
         case 160:
            return this.__init__$160(var2, var3);
         case 161:
            return this._get_actualEncoding$161(var2, var3);
         case 162:
            return this._get_encoding$162(var2, var3);
         case 163:
            return this._get_version$163(var2, var3);
         case 164:
            return this.appendChild$164(var2, var3);
         case 165:
            return this.insertBefore$165(var2, var3);
         case 166:
            return this.removeChild$166(var2, var3);
         case 167:
            return this.replaceChild$167(var2, var3);
         case 168:
            return this.Notation$168(var2, var3);
         case 169:
            return this.__init__$169(var2, var3);
         case 170:
            return this.DOMImplementation$170(var2, var3);
         case 171:
            return this.hasFeature$171(var2, var3);
         case 172:
            return this.createDocument$172(var2, var3);
         case 173:
            return this.createDocumentType$173(var2, var3);
         case 174:
            return this.getInterface$174(var2, var3);
         case 175:
            return this._create_document$175(var2, var3);
         case 176:
            return this.ElementInfo$176(var2, var3);
         case 177:
            return this.__init__$177(var2, var3);
         case 178:
            return this.getAttributeType$178(var2, var3);
         case 179:
            return this.getAttributeTypeNS$179(var2, var3);
         case 180:
            return this.isElementContent$180(var2, var3);
         case 181:
            return this.isEmpty$181(var2, var3);
         case 182:
            return this.isId$182(var2, var3);
         case 183:
            return this.isIdNS$183(var2, var3);
         case 184:
            return this.__getstate__$184(var2, var3);
         case 185:
            return this.__setstate__$185(var2, var3);
         case 186:
            return this._clear_id_cache$186(var2, var3);
         case 187:
            return this.Document$187(var2, var3);
         case 188:
            return this.__init__$188(var2, var3);
         case 189:
            return this._get_elem_info$189(var2, var3);
         case 190:
            return this._get_actualEncoding$190(var2, var3);
         case 191:
            return this._get_doctype$191(var2, var3);
         case 192:
            return this._get_documentURI$192(var2, var3);
         case 193:
            return this._get_encoding$193(var2, var3);
         case 194:
            return this._get_errorHandler$194(var2, var3);
         case 195:
            return this._get_standalone$195(var2, var3);
         case 196:
            return this._get_strictErrorChecking$196(var2, var3);
         case 197:
            return this._get_version$197(var2, var3);
         case 198:
            return this.appendChild$198(var2, var3);
         case 199:
            return this.removeChild$199(var2, var3);
         case 200:
            return this._get_documentElement$200(var2, var3);
         case 201:
            return this.unlink$201(var2, var3);
         case 202:
            return this.cloneNode$202(var2, var3);
         case 203:
            return this.createDocumentFragment$203(var2, var3);
         case 204:
            return this.createElement$204(var2, var3);
         case 205:
            return this.createTextNode$205(var2, var3);
         case 206:
            return this.createCDATASection$206(var2, var3);
         case 207:
            return this.createComment$207(var2, var3);
         case 208:
            return this.createProcessingInstruction$208(var2, var3);
         case 209:
            return this.createAttribute$209(var2, var3);
         case 210:
            return this.createElementNS$210(var2, var3);
         case 211:
            return this.createAttributeNS$211(var2, var3);
         case 212:
            return this._create_entity$212(var2, var3);
         case 213:
            return this._create_notation$213(var2, var3);
         case 214:
            return this.getElementById$214(var2, var3);
         case 215:
            return this.getElementsByTagName$215(var2, var3);
         case 216:
            return this.getElementsByTagNameNS$216(var2, var3);
         case 217:
            return this.isSupported$217(var2, var3);
         case 218:
            return this.importNode$218(var2, var3);
         case 219:
            return this.writexml$219(var2, var3);
         case 220:
            return this.renameNode$220(var2, var3);
         case 221:
            return this._clone_node$221(var2, var3);
         case 222:
            return this._nssplit$222(var2, var3);
         case 223:
            return this._get_StringIO$223(var2, var3);
         case 224:
            return this._do_pulldom_parse$224(var2, var3);
         case 225:
            return this.parse$225(var2, var3);
         case 226:
            return this.parseString$226(var2, var3);
         case 227:
            return this.getDOMImplementation$227(var2, var3);
         default:
            return null;
      }
   }
}
