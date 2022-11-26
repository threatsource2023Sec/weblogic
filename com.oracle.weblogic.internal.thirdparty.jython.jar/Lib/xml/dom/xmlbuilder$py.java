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
@Filename("xml/dom/xmlbuilder.py")
public class xmlbuilder$py extends PyFunctionTable implements PyRunnable {
   static xmlbuilder$py self;
   static final PyCode f$0;
   static final PyCode Options$1;
   static final PyCode DOMBuilder$2;
   static final PyCode __init__$3;
   static final PyCode _get_entityResolver$4;
   static final PyCode _set_entityResolver$5;
   static final PyCode _get_errorHandler$6;
   static final PyCode _set_errorHandler$7;
   static final PyCode _get_filter$8;
   static final PyCode _set_filter$9;
   static final PyCode setFeature$10;
   static final PyCode supportsFeature$11;
   static final PyCode canSetFeature$12;
   static final PyCode getFeature$13;
   static final PyCode parseURI$14;
   static final PyCode parse$15;
   static final PyCode parseWithContext$16;
   static final PyCode _parse_bytestream$17;
   static final PyCode _name_xform$18;
   static final PyCode DOMEntityResolver$19;
   static final PyCode resolveEntity$20;
   static final PyCode _get_opener$21;
   static final PyCode _create_opener$22;
   static final PyCode _guess_media_encoding$23;
   static final PyCode DOMInputSource$24;
   static final PyCode __init__$25;
   static final PyCode _get_byteStream$26;
   static final PyCode _set_byteStream$27;
   static final PyCode _get_characterStream$28;
   static final PyCode _set_characterStream$29;
   static final PyCode _get_stringData$30;
   static final PyCode _set_stringData$31;
   static final PyCode _get_encoding$32;
   static final PyCode _set_encoding$33;
   static final PyCode _get_publicId$34;
   static final PyCode _set_publicId$35;
   static final PyCode _get_systemId$36;
   static final PyCode _set_systemId$37;
   static final PyCode _get_baseURI$38;
   static final PyCode _set_baseURI$39;
   static final PyCode DOMBuilderFilter$40;
   static final PyCode _get_whatToShow$41;
   static final PyCode acceptNode$42;
   static final PyCode startContainer$43;
   static final PyCode DocumentLS$44;
   static final PyCode _get_async$45;
   static final PyCode _set_async$46;
   static final PyCode abort$47;
   static final PyCode load$48;
   static final PyCode loadXML$49;
   static final PyCode saveXML$50;
   static final PyCode DOMImplementationLS$51;
   static final PyCode createDOMBuilder$52;
   static final PyCode createDOMWriter$53;
   static final PyCode createDOMInputSource$54;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Implementation of the DOM Level 3 'LS-Load' feature."));
      var1.setline(1);
      PyString.fromInterned("Implementation of the DOM Level 3 'LS-Load' feature.");
      var1.setline(3);
      PyObject var3 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("xml.dom", var1, -1);
      var1.setlocal("xml", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"NodeFilter"};
      PyObject[] var6 = imp.importFrom("xml.dom.NodeFilter", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("NodeFilter", var4);
      var4 = null;
      var1.setline(9);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("DOMBuilder"), PyString.fromInterned("DOMEntityResolver"), PyString.fromInterned("DOMInputSource")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(12);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Options", var6, Options$1);
      var1.setlocal("Options", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(44);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DOMBuilder", var6, DOMBuilder$2);
      var1.setlocal("DOMBuilder", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(208);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, _name_xform$18, (PyObject)null);
      var1.setlocal("_name_xform", var8);
      var3 = null;
      var1.setline(212);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("DOMEntityResolver", var6, DOMEntityResolver$19);
      var1.setlocal("DOMEntityResolver", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(256);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("DOMInputSource", var6, DOMInputSource$24);
      var1.setlocal("DOMInputSource", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(305);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DOMBuilderFilter", var6, DOMBuilderFilter$40);
      var1.setlocal("DOMBuilderFilter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(331);
      var1.dellocal("NodeFilter");
      var1.setline(334);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DocumentLS", var6, DocumentLS$44);
      var1.setlocal("DocumentLS", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(366);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DOMImplementationLS", var6, DOMImplementationLS$51);
      var1.setlocal("DOMImplementationLS", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Options$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Features object that has variables set for each DOMBuilder feature.\n\n    The DOMBuilder class uses an instance of this class to pass settings to\n    the ExpatBuilder class.\n    "));
      var1.setline(17);
      PyString.fromInterned("Features object that has variables set for each DOMBuilder feature.\n\n    The DOMBuilder class uses an instance of this class to pass settings to\n    the ExpatBuilder class.\n    ");
      var1.setline(22);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("namespaces", var3);
      var3 = null;
      var1.setline(23);
      PyObject var4 = var1.getname("True");
      var1.setlocal("namespace_declarations", var4);
      var3 = null;
      var1.setline(24);
      var4 = var1.getname("False");
      var1.setlocal("validation", var4);
      var3 = null;
      var1.setline(25);
      var4 = var1.getname("True");
      var1.setlocal("external_parameter_entities", var4);
      var3 = null;
      var1.setline(26);
      var4 = var1.getname("True");
      var1.setlocal("external_general_entities", var4);
      var3 = null;
      var1.setline(27);
      var4 = var1.getname("True");
      var1.setlocal("external_dtd_subset", var4);
      var3 = null;
      var1.setline(28);
      var4 = var1.getname("False");
      var1.setlocal("validate_if_schema", var4);
      var3 = null;
      var1.setline(29);
      var4 = var1.getname("False");
      var1.setlocal("validate", var4);
      var3 = null;
      var1.setline(30);
      var4 = var1.getname("False");
      var1.setlocal("datatype_normalization", var4);
      var3 = null;
      var1.setline(31);
      var4 = var1.getname("True");
      var1.setlocal("create_entity_ref_nodes", var4);
      var3 = null;
      var1.setline(32);
      var4 = var1.getname("True");
      var1.setlocal("entities", var4);
      var3 = null;
      var1.setline(33);
      var4 = var1.getname("True");
      var1.setlocal("whitespace_in_element_content", var4);
      var3 = null;
      var1.setline(34);
      var4 = var1.getname("True");
      var1.setlocal("cdata_sections", var4);
      var3 = null;
      var1.setline(35);
      var4 = var1.getname("True");
      var1.setlocal("comments", var4);
      var3 = null;
      var1.setline(36);
      var4 = var1.getname("True");
      var1.setlocal("charset_overrides_xml_encoding", var4);
      var3 = null;
      var1.setline(37);
      var4 = var1.getname("False");
      var1.setlocal("infoset", var4);
      var3 = null;
      var1.setline(38);
      var4 = var1.getname("False");
      var1.setlocal("supported_mediatypes_only", var4);
      var3 = null;
      var1.setline(40);
      var4 = var1.getname("None");
      var1.setlocal("errorHandler", var4);
      var3 = null;
      var1.setline(41);
      var4 = var1.getname("None");
      var1.setlocal("filter", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject DOMBuilder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(45);
      PyObject var3 = var1.getname("None");
      var1.setlocal("entityResolver", var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getname("None");
      var1.setlocal("errorHandler", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("None");
      var1.setlocal("filter", var3);
      var3 = null;
      var1.setline(49);
      PyInteger var4 = Py.newInteger(1);
      var1.setlocal("ACTION_REPLACE", var4);
      var3 = null;
      var1.setline(50);
      var4 = Py.newInteger(2);
      var1.setlocal("ACTION_APPEND_AS_CHILDREN", var4);
      var3 = null;
      var1.setline(51);
      var4 = Py.newInteger(3);
      var1.setlocal("ACTION_INSERT_AFTER", var4);
      var3 = null;
      var1.setline(52);
      var4 = Py.newInteger(4);
      var1.setlocal("ACTION_INSERT_BEFORE", var4);
      var3 = null;
      var1.setline(54);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getname("ACTION_REPLACE"), var1.getname("ACTION_APPEND_AS_CHILDREN"), var1.getname("ACTION_INSERT_AFTER"), var1.getname("ACTION_INSERT_BEFORE")});
      var1.setlocal("_legal_actions", var5);
      var3 = null;
      var1.setline(57);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(60);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_entityResolver$4, (PyObject)null);
      var1.setlocal("_get_entityResolver", var7);
      var3 = null;
      var1.setline(62);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _set_entityResolver$5, (PyObject)null);
      var1.setlocal("_set_entityResolver", var7);
      var3 = null;
      var1.setline(65);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_errorHandler$6, (PyObject)null);
      var1.setlocal("_get_errorHandler", var7);
      var3 = null;
      var1.setline(67);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _set_errorHandler$7, (PyObject)null);
      var1.setlocal("_set_errorHandler", var7);
      var3 = null;
      var1.setline(70);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_filter$8, (PyObject)null);
      var1.setlocal("_get_filter", var7);
      var3 = null;
      var1.setline(72);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _set_filter$9, (PyObject)null);
      var1.setlocal("_set_filter", var7);
      var3 = null;
      var1.setline(75);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setFeature$10, (PyObject)null);
      var1.setlocal("setFeature", var7);
      var3 = null;
      var1.setline(89);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, supportsFeature$11, (PyObject)null);
      var1.setlocal("supportsFeature", var7);
      var3 = null;
      var1.setline(92);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, canSetFeature$12, (PyObject)null);
      var1.setlocal("canSetFeature", var7);
      var3 = null;
      var1.setline(101);
      PyDictionary var8 = new PyDictionary(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("namespace_declarations"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("namespace_declarations"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("namespace_declarations"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("namespace_declarations"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("validation"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("validation"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("external_general_entities"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("external_general_entities"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("external_general_entities"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("external_general_entities"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("external_parameter_entities"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("external_parameter_entities"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("external_parameter_entities"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("external_parameter_entities"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("validate_if_schema"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("validate_if_schema"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("create_entity_ref_nodes"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("create_entity_ref_nodes"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("create_entity_ref_nodes"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("create_entity_ref_nodes"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("entities"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("create_entity_ref_nodes"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("entities"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("entities"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("entities"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("whitespace_in_element_content"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("whitespace_in_element_content"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("whitespace_in_element_content"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("whitespace_in_element_content"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("cdata_sections"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("cdata_sections"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("cdata_sections"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("cdata_sections"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("comments"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("comments"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("comments"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("comments"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("charset_overrides_xml_encoding"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("charset_overrides_xml_encoding"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("charset_overrides_xml_encoding"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("charset_overrides_xml_encoding"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("infoset"), Py.newInteger(0)}), new PyList(Py.EmptyObjects), new PyTuple(new PyObject[]{PyString.fromInterned("infoset"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("namespace_declarations"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("validate_if_schema"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("create_entity_ref_nodes"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("entities"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("cdata_sections"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("datatype_normalization"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("whitespace_in_element_content"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("comments"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("charset_overrides_xml_encoding"), Py.newInteger(1)})}), new PyTuple(new PyObject[]{PyString.fromInterned("supported_mediatypes_only"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("supported_mediatypes_only"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("namespaces"), Py.newInteger(0)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("namespaces"), Py.newInteger(0)})}), new PyTuple(new PyObject[]{PyString.fromInterned("namespaces"), Py.newInteger(1)}), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("namespaces"), Py.newInteger(1)})})});
      var1.setlocal("_settings", var8);
      var3 = null;
      var1.setline(162);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getFeature$13, (PyObject)null);
      var1.setlocal("getFeature", var7);
      var3 = null;
      var1.setline(180);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, parseURI$14, (PyObject)null);
      var1.setlocal("parseURI", var7);
      var3 = null;
      var1.setline(187);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, parse$15, (PyObject)null);
      var1.setlocal("parse", var7);
      var3 = null;
      var1.setline(197);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, parseWithContext$16, (PyObject)null);
      var1.setlocal("parseWithContext", var7);
      var3 = null;
      var1.setline(202);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _parse_bytestream$17, (PyObject)null);
      var1.setlocal("_parse_bytestream", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getglobal("Options").__call__(var2);
      var1.getlocal(0).__setattr__("_options", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_entityResolver$4(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getlocal(0).__getattr__("entityResolver");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_entityResolver$5(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("entityResolver", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_errorHandler$6(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getattr__("errorHandler");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_errorHandler$7(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errorHandler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_filter$8(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("filter");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_filter$9(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filter", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setFeature$10(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      if (!var1.getlocal(0).__getattr__("supportsFeature").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(87);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2, PyString.fromInterned("unknown feature: ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(77);
         Object var10000 = var1.getlocal(2);
         if (((PyObject)var10000).__nonzero__()) {
            var10000 = Py.newInteger(1);
         }

         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = Py.newInteger(0);
         }

         Object var3 = var10000;
         var1.setlocal(2, (PyObject)var3);
         var3 = null;

         try {
            var1.setline(79);
            PyObject var10 = var1.getlocal(0).__getattr__("_settings").__getitem__(new PyTuple(new PyObject[]{var1.getglobal("_name_xform").__call__(var2, var1.getlocal(1)), var1.getlocal(2)}));
            var1.setlocal(3, var10);
            var3 = null;
         } catch (Throwable var8) {
            PyException var9 = Py.setException(var8, var1);
            if (var9.match(var1.getglobal("KeyError"))) {
               var1.setline(81);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__(var2, PyString.fromInterned("unsupported feature: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
            }

            throw var9;
         }

         var1.setline(84);
         PyObject var4 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(84);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(1, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(85);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0).__getattr__("_options"), var1.getlocal(1), var1.getlocal(4));
         }
      }
   }

   public PyObject supportsFeature$11(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(0).__getattr__("_options"), var1.getglobal("_name_xform").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject canSetFeature$12(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyTuple var10000 = new PyTuple;
      PyObject[] var10002 = new PyObject[]{var1.getglobal("_name_xform").__call__(var2, var1.getlocal(1)), null};
      Object var10005 = var1.getlocal(2);
      if (((PyObject)var10005).__nonzero__()) {
         var10005 = Py.newInteger(1);
      }

      if (!((PyObject)var10005).__nonzero__()) {
         var10005 = Py.newInteger(0);
      }

      var10002[1] = (PyObject)var10005;
      var10000.<init>(var10002);
      PyTuple var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(94);
      PyObject var4 = var1.getlocal(3);
      PyObject var5 = var4._in(var1.getlocal(0).__getattr__("_settings"));
      var3 = null;
      var4 = var5;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getFeature$13(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3 = var1.getglobal("_name_xform").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(165);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("_options"), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(167);
            PyObject var5 = var1.getlocal(1);
            PyObject var10000 = var5._eq(PyString.fromInterned("infoset"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(168);
               var5 = var1.getlocal(0).__getattr__("_options");
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(169);
               var10000 = var1.getlocal(3).__getattr__("datatype_normalization");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(3).__getattr__("whitespace_in_element_content");
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(3).__getattr__("comments");
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(3).__getattr__("charset_overrides_xml_encoding");
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(3).__getattr__("namespace_declarations");
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(3).__getattr__("validate_if_schema");
                              if (!var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(3).__getattr__("create_entity_ref_nodes");
                                 if (!var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(3).__getattr__("entities");
                                    if (!var10000.__nonzero__()) {
                                       var10000 = var1.getlocal(3).__getattr__("cdata_sections");
                                    }
                                 }
                              }
                           }

                           var10000 = var10000.__not__();
                        }
                     }
                  }
               }

               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(178);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotFoundErr").__call__(var2, PyString.fromInterned("feature %s not known")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))));
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject parseURI$14(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("entityResolver").__nonzero__()) {
         var1.setline(182);
         var3 = var1.getlocal(0).__getattr__("entityResolver").__getattr__("resolveEntity").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(184);
         var3 = var1.getglobal("DOMEntityResolver").__call__(var2).__getattr__("resolveEntity").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(185);
      var3 = var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse$15(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getglobal("copy").__getattr__("copy").__call__(var2, var1.getlocal(0).__getattr__("_options"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getlocal(0).__getattr__("filter");
      var1.getlocal(2).__setattr__("filter", var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getlocal(0).__getattr__("errorHandler");
      var1.getlocal(2).__setattr__("errorHandler", var3);
      var3 = null;
      var1.setline(191);
      var3 = var1.getlocal(1).__getattr__("byteStream");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(192);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__getattr__("systemId");
      }

      if (var10000.__nonzero__()) {
         var1.setline(193);
         var3 = imp.importOne("urllib2", var1, -1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(194);
         var3 = var1.getlocal(4).__getattr__("urlopen").__call__(var2, var1.getlocal(1).__getattr__("systemId"));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(195);
      var3 = var1.getlocal(0).__getattr__("_parse_bytestream").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseWithContext$16(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("_legal_actions"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(199);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a legal action")));
      } else {
         var1.setline(200);
         throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Haven't written this yet...")));
      }
   }

   public PyObject _parse_bytestream$17(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var3 = imp.importOne("xml.dom.expatbuilder", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(204);
      var3 = var1.getlocal(3).__getattr__("dom").__getattr__("expatbuilder").__getattr__("makeBuilder").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getlocal(4).__getattr__("parseFile").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _name_xform$18(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DOMEntityResolver$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(213);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_opener")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(215);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, resolveEntity$20, (PyObject)null);
      var1.setlocal("resolveEntity", var5);
      var3 = null;
      var1.setline(237);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_opener$21, (PyObject)null);
      var1.setlocal("_get_opener", var5);
      var3 = null;
      var1.setline(244);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _create_opener$22, (PyObject)null);
      var1.setlocal("_create_opener", var5);
      var3 = null;
      var1.setline(248);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _guess_media_encoding$23, (PyObject)null);
      var1.setlocal("_guess_media_encoding", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject resolveEntity$20(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(217);
      var3 = var1.getglobal("DOMInputSource").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getlocal(1);
      var1.getlocal(3).__setattr__("publicId", var3);
      var3 = null;
      var1.setline(219);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setattr__("systemId", var3);
      var3 = null;
      var1.setline(220);
      var3 = var1.getlocal(0).__getattr__("_get_opener").__call__(var2).__getattr__("open").__call__(var2, var1.getlocal(2));
      var1.getlocal(3).__setattr__("byteStream", var3);
      var3 = null;
      var1.setline(223);
      var3 = var1.getlocal(0).__getattr__("_guess_media_encoding").__call__(var2, var1.getlocal(3));
      var1.getlocal(3).__setattr__("encoding", var3);
      var3 = null;
      var1.setline(226);
      var3 = imp.importOne("posixpath", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var3 = imp.importOne("urlparse", var1, -1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getlocal(5).__getattr__("urlparse").__call__(var2, var1.getlocal(2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(228);
      var3 = var1.getlocal(6);
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(12, var5);
      var5 = null;
      var3 = null;
      var1.setline(230);
      var10000 = var1.getlocal(9);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(9).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(231);
         var3 = var1.getlocal(4).__getattr__("dirname").__call__(var2, var1.getlocal(9))._add(PyString.fromInterned("/"));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(232);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12)});
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(233);
         var3 = var1.getlocal(5).__getattr__("urlunparse").__call__(var2, var1.getlocal(6));
         var1.getlocal(3).__setattr__("baseURI", var3);
         var3 = null;
      }

      var1.setline(235);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_opener$21(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(239);
         var3 = var1.getlocal(0).__getattr__("_opener");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(241);
            PyObject var5 = var1.getlocal(0).__getattr__("_create_opener").__call__(var2);
            var1.getlocal(0).__setattr__("_opener", var5);
            var5 = null;
            var1.setline(242);
            var3 = var1.getlocal(0).__getattr__("_opener");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _create_opener$22(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyObject var3 = imp.importOne("urllib2", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(246);
      var3 = var1.getlocal(1).__getattr__("build_opener").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _guess_media_encoding$23(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var3 = var1.getlocal(1).__getattr__("byteStream").__getattr__("info").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(250);
      PyString var6 = PyString.fromInterned("Content-Type");
      PyObject var10000 = var6._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(251);
         var3 = var1.getlocal(2).__getattr__("getplist").__call__(var2).__iter__();

         while(true) {
            var1.setline(251);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(252);
            if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset=")).__nonzero__()) {
               var1.setline(253);
               PyObject var5 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1)).__getattr__("lower").__call__(var2);
               var1.f_lasti = -1;
               return var5;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DOMInputSource$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(257);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("byteStream"), PyString.fromInterned("characterStream"), PyString.fromInterned("stringData"), PyString.fromInterned("encoding"), PyString.fromInterned("publicId"), PyString.fromInterned("systemId"), PyString.fromInterned("baseURI")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(260);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$25, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(269);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_byteStream$26, (PyObject)null);
      var1.setlocal("_get_byteStream", var5);
      var3 = null;
      var1.setline(271);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_byteStream$27, (PyObject)null);
      var1.setlocal("_set_byteStream", var5);
      var3 = null;
      var1.setline(274);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_characterStream$28, (PyObject)null);
      var1.setlocal("_get_characterStream", var5);
      var3 = null;
      var1.setline(276);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_characterStream$29, (PyObject)null);
      var1.setlocal("_set_characterStream", var5);
      var3 = null;
      var1.setline(279);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_stringData$30, (PyObject)null);
      var1.setlocal("_get_stringData", var5);
      var3 = null;
      var1.setline(281);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_stringData$31, (PyObject)null);
      var1.setlocal("_set_stringData", var5);
      var3 = null;
      var1.setline(284);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_encoding$32, (PyObject)null);
      var1.setlocal("_get_encoding", var5);
      var3 = null;
      var1.setline(286);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_encoding$33, (PyObject)null);
      var1.setlocal("_set_encoding", var5);
      var3 = null;
      var1.setline(289);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_publicId$34, (PyObject)null);
      var1.setlocal("_get_publicId", var5);
      var3 = null;
      var1.setline(291);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_publicId$35, (PyObject)null);
      var1.setlocal("_set_publicId", var5);
      var3 = null;
      var1.setline(294);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_systemId$36, (PyObject)null);
      var1.setlocal("_get_systemId", var5);
      var3 = null;
      var1.setline(296);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_systemId$37, (PyObject)null);
      var1.setlocal("_set_systemId", var5);
      var3 = null;
      var1.setline(299);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_baseURI$38, (PyObject)null);
      var1.setlocal("_get_baseURI", var5);
      var3 = null;
      var1.setline(301);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_baseURI$39, (PyObject)null);
      var1.setlocal("_set_baseURI", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$25(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("byteStream", var3);
      var3 = null;
      var1.setline(262);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("characterStream", var3);
      var3 = null;
      var1.setline(263);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("stringData", var3);
      var3 = null;
      var1.setline(264);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.setline(265);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("publicId", var3);
      var3 = null;
      var1.setline(266);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("systemId", var3);
      var3 = null;
      var1.setline(267);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("baseURI", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_byteStream$26(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyObject var3 = var1.getlocal(0).__getattr__("byteStream");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_byteStream$27(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("byteStream", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_characterStream$28(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyObject var3 = var1.getlocal(0).__getattr__("characterStream");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_characterStream$29(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("characterStream", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_stringData$30(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getlocal(0).__getattr__("stringData");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_stringData$31(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stringData", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_encoding$32(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyObject var3 = var1.getlocal(0).__getattr__("encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_encoding$33(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_publicId$34(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyObject var3 = var1.getlocal(0).__getattr__("publicId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_publicId$35(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("publicId", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_systemId$36(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyObject var3 = var1.getlocal(0).__getattr__("systemId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_systemId$37(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("systemId", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_baseURI$38(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyObject var3 = var1.getlocal(0).__getattr__("baseURI");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_baseURI$39(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("baseURI", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DOMBuilderFilter$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Element filter which can be used to tailor construction of\n    a DOM instance.\n    "));
      var1.setline(308);
      PyString.fromInterned("Element filter which can be used to tailor construction of\n    a DOM instance.\n    ");
      var1.setline(315);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("FILTER_ACCEPT", var3);
      var3 = null;
      var1.setline(316);
      var3 = Py.newInteger(2);
      var1.setlocal("FILTER_REJECT", var3);
      var3 = null;
      var1.setline(317);
      var3 = Py.newInteger(3);
      var1.setlocal("FILTER_SKIP", var3);
      var3 = null;
      var1.setline(318);
      var3 = Py.newInteger(4);
      var1.setlocal("FILTER_INTERRUPT", var3);
      var3 = null;
      var1.setline(320);
      PyObject var4 = var1.getname("NodeFilter").__getattr__("SHOW_ALL");
      var1.setlocal("whatToShow", var4);
      var3 = null;
      var1.setline(322);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, _get_whatToShow$41, (PyObject)null);
      var1.setlocal("_get_whatToShow", var6);
      var3 = null;
      var1.setline(325);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, acceptNode$42, (PyObject)null);
      var1.setlocal("acceptNode", var6);
      var3 = null;
      var1.setline(328);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, startContainer$43, (PyObject)null);
      var1.setlocal("startContainer", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_whatToShow$41(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyObject var3 = var1.getlocal(0).__getattr__("whatToShow");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject acceptNode$42(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyObject var3 = var1.getlocal(0).__getattr__("FILTER_ACCEPT");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject startContainer$43(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyObject var3 = var1.getlocal(0).__getattr__("FILTER_ACCEPT");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DocumentLS$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mixin to create documents that conform to the load/save spec."));
      var1.setline(335);
      PyString.fromInterned("Mixin to create documents that conform to the load/save spec.");
      var1.setline(337);
      PyObject var3 = var1.getname("False");
      var1.setlocal("async", var3);
      var3 = null;
      var1.setline(339);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _get_async$45, (PyObject)null);
      var1.setlocal("_get_async", var5);
      var3 = null;
      var1.setline(341);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _set_async$46, (PyObject)null);
      var1.setlocal("_set_async", var5);
      var3 = null;
      var1.setline(346);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, abort$47, (PyObject)null);
      var1.setlocal("abort", var5);
      var3 = null;
      var1.setline(352);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, load$48, (PyObject)null);
      var1.setlocal("load", var5);
      var3 = null;
      var1.setline(355);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, loadXML$49, (PyObject)null);
      var1.setlocal("loadXML", var5);
      var3 = null;
      var1.setline(358);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, saveXML$50, (PyObject)null);
      var1.setlocal("saveXML", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_async$45(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_async$46(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(343);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("asynchronous document loading is not supported")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject abort$47(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("haven't figured out what this means yet")));
   }

   public PyObject load$48(PyFrame var1, ThreadState var2) {
      var1.setline(353);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("haven't written this yet")));
   }

   public PyObject loadXML$49(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("haven't written this yet")));
   }

   public PyObject saveXML$50(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(360);
         var3 = var1.getlocal(0);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(361);
         var3 = var1.getlocal(1).__getattr__("ownerDocument");
         var10000 = var3._isnot(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(362);
            throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("WrongDocumentErr").__call__(var2));
         }
      }

      var1.setline(363);
      var3 = var1.getlocal(1).__getattr__("toxml").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DOMImplementationLS$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(367);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("MODE_SYNCHRONOUS", var3);
      var3 = null;
      var1.setline(368);
      var3 = Py.newInteger(2);
      var1.setlocal("MODE_ASYNCHRONOUS", var3);
      var3 = null;
      var1.setline(370);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, createDOMBuilder$52, (PyObject)null);
      var1.setlocal("createDOMBuilder", var5);
      var3 = null;
      var1.setline(381);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, createDOMWriter$53, (PyObject)null);
      var1.setlocal("createDOMWriter", var5);
      var3 = null;
      var1.setline(385);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, createDOMInputSource$54, (PyObject)null);
      var1.setlocal("createDOMInputSource", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject createDOMBuilder$52(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(372);
         throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("schemaType not yet supported")));
      } else {
         var1.setline(374);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("MODE_SYNCHRONOUS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(375);
            var3 = var1.getglobal("DOMBuilder").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(376);
            PyObject var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getlocal(0).__getattr__("MODE_ASYNCHRONOUS"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(377);
               throw Py.makeException(var1.getglobal("xml").__getattr__("dom").__getattr__("NotSupportedErr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("asynchronous builders are not supported")));
            } else {
               var1.setline(379);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unknown value for mode")));
            }
         }
      }
   }

   public PyObject createDOMWriter$53(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("the writer interface hasn't been written yet!")));
   }

   public PyObject createDOMInputSource$54(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = var1.getglobal("DOMInputSource").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public xmlbuilder$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Options$1 = Py.newCode(0, var2, var1, "Options", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DOMBuilder$2 = Py.newCode(0, var2, var1, "DOMBuilder", 44, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$3 = Py.newCode(1, var2, var1, "__init__", 57, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_entityResolver$4 = Py.newCode(1, var2, var1, "_get_entityResolver", 60, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "entityResolver"};
      _set_entityResolver$5 = Py.newCode(2, var2, var1, "_set_entityResolver", 62, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_errorHandler$6 = Py.newCode(1, var2, var1, "_get_errorHandler", 65, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "errorHandler"};
      _set_errorHandler$7 = Py.newCode(2, var2, var1, "_set_errorHandler", 67, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_filter$8 = Py.newCode(1, var2, var1, "_get_filter", 70, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filter"};
      _set_filter$9 = Py.newCode(2, var2, var1, "_set_filter", 72, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "state", "settings", "value"};
      setFeature$10 = Py.newCode(3, var2, var1, "setFeature", 75, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      supportsFeature$11 = Py.newCode(2, var2, var1, "supportsFeature", 89, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "state", "key"};
      canSetFeature$12 = Py.newCode(3, var2, var1, "canSetFeature", 92, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "xname", "options"};
      getFeature$13 = Py.newCode(2, var2, var1, "getFeature", 162, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri", "input"};
      parseURI$14 = Py.newCode(2, var2, var1, "parseURI", 180, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "options", "fp", "urllib2"};
      parse$15 = Py.newCode(2, var2, var1, "parse", 187, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "cnode", "action"};
      parseWithContext$16 = Py.newCode(4, var2, var1, "parseWithContext", 197, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stream", "options", "xml", "builder"};
      _parse_bytestream$17 = Py.newCode(3, var2, var1, "_parse_bytestream", 202, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      _name_xform$18 = Py.newCode(1, var2, var1, "_name_xform", 208, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DOMEntityResolver$19 = Py.newCode(0, var2, var1, "DOMEntityResolver", 212, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "publicId", "systemId", "source", "posixpath", "urlparse", "parts", "scheme", "netloc", "path", "params", "query", "fragment"};
      resolveEntity$20 = Py.newCode(3, var2, var1, "resolveEntity", 215, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_opener$21 = Py.newCode(1, var2, var1, "_get_opener", 237, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "urllib2"};
      _create_opener$22 = Py.newCode(1, var2, var1, "_create_opener", 244, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "info", "param"};
      _guess_media_encoding$23 = Py.newCode(2, var2, var1, "_guess_media_encoding", 248, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DOMInputSource$24 = Py.newCode(0, var2, var1, "DOMInputSource", 256, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$25 = Py.newCode(1, var2, var1, "__init__", 260, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_byteStream$26 = Py.newCode(1, var2, var1, "_get_byteStream", 269, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "byteStream"};
      _set_byteStream$27 = Py.newCode(2, var2, var1, "_set_byteStream", 271, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_characterStream$28 = Py.newCode(1, var2, var1, "_get_characterStream", 274, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "characterStream"};
      _set_characterStream$29 = Py.newCode(2, var2, var1, "_set_characterStream", 276, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_stringData$30 = Py.newCode(1, var2, var1, "_get_stringData", 279, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      _set_stringData$31 = Py.newCode(2, var2, var1, "_set_stringData", 281, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_encoding$32 = Py.newCode(1, var2, var1, "_get_encoding", 284, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding"};
      _set_encoding$33 = Py.newCode(2, var2, var1, "_set_encoding", 286, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_publicId$34 = Py.newCode(1, var2, var1, "_get_publicId", 289, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "publicId"};
      _set_publicId$35 = Py.newCode(2, var2, var1, "_set_publicId", 291, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_systemId$36 = Py.newCode(1, var2, var1, "_get_systemId", 294, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "systemId"};
      _set_systemId$37 = Py.newCode(2, var2, var1, "_set_systemId", 296, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_baseURI$38 = Py.newCode(1, var2, var1, "_get_baseURI", 299, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri"};
      _set_baseURI$39 = Py.newCode(2, var2, var1, "_set_baseURI", 301, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DOMBuilderFilter$40 = Py.newCode(0, var2, var1, "DOMBuilderFilter", 305, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _get_whatToShow$41 = Py.newCode(1, var2, var1, "_get_whatToShow", 322, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      acceptNode$42 = Py.newCode(2, var2, var1, "acceptNode", 325, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      startContainer$43 = Py.newCode(2, var2, var1, "startContainer", 328, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocumentLS$44 = Py.newCode(0, var2, var1, "DocumentLS", 334, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _get_async$45 = Py.newCode(1, var2, var1, "_get_async", 339, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "async"};
      _set_async$46 = Py.newCode(2, var2, var1, "_set_async", 341, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      abort$47 = Py.newCode(1, var2, var1, "abort", 346, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri"};
      load$48 = Py.newCode(2, var2, var1, "load", 352, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source"};
      loadXML$49 = Py.newCode(2, var2, var1, "loadXML", 355, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "snode"};
      saveXML$50 = Py.newCode(2, var2, var1, "saveXML", 358, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DOMImplementationLS$51 = Py.newCode(0, var2, var1, "DOMImplementationLS", 366, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mode", "schemaType"};
      createDOMBuilder$52 = Py.newCode(3, var2, var1, "createDOMBuilder", 370, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      createDOMWriter$53 = Py.newCode(1, var2, var1, "createDOMWriter", 381, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      createDOMInputSource$54 = Py.newCode(1, var2, var1, "createDOMInputSource", 385, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new xmlbuilder$py("xml/dom/xmlbuilder$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(xmlbuilder$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Options$1(var2, var3);
         case 2:
            return this.DOMBuilder$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._get_entityResolver$4(var2, var3);
         case 5:
            return this._set_entityResolver$5(var2, var3);
         case 6:
            return this._get_errorHandler$6(var2, var3);
         case 7:
            return this._set_errorHandler$7(var2, var3);
         case 8:
            return this._get_filter$8(var2, var3);
         case 9:
            return this._set_filter$9(var2, var3);
         case 10:
            return this.setFeature$10(var2, var3);
         case 11:
            return this.supportsFeature$11(var2, var3);
         case 12:
            return this.canSetFeature$12(var2, var3);
         case 13:
            return this.getFeature$13(var2, var3);
         case 14:
            return this.parseURI$14(var2, var3);
         case 15:
            return this.parse$15(var2, var3);
         case 16:
            return this.parseWithContext$16(var2, var3);
         case 17:
            return this._parse_bytestream$17(var2, var3);
         case 18:
            return this._name_xform$18(var2, var3);
         case 19:
            return this.DOMEntityResolver$19(var2, var3);
         case 20:
            return this.resolveEntity$20(var2, var3);
         case 21:
            return this._get_opener$21(var2, var3);
         case 22:
            return this._create_opener$22(var2, var3);
         case 23:
            return this._guess_media_encoding$23(var2, var3);
         case 24:
            return this.DOMInputSource$24(var2, var3);
         case 25:
            return this.__init__$25(var2, var3);
         case 26:
            return this._get_byteStream$26(var2, var3);
         case 27:
            return this._set_byteStream$27(var2, var3);
         case 28:
            return this._get_characterStream$28(var2, var3);
         case 29:
            return this._set_characterStream$29(var2, var3);
         case 30:
            return this._get_stringData$30(var2, var3);
         case 31:
            return this._set_stringData$31(var2, var3);
         case 32:
            return this._get_encoding$32(var2, var3);
         case 33:
            return this._set_encoding$33(var2, var3);
         case 34:
            return this._get_publicId$34(var2, var3);
         case 35:
            return this._set_publicId$35(var2, var3);
         case 36:
            return this._get_systemId$36(var2, var3);
         case 37:
            return this._set_systemId$37(var2, var3);
         case 38:
            return this._get_baseURI$38(var2, var3);
         case 39:
            return this._set_baseURI$39(var2, var3);
         case 40:
            return this.DOMBuilderFilter$40(var2, var3);
         case 41:
            return this._get_whatToShow$41(var2, var3);
         case 42:
            return this.acceptNode$42(var2, var3);
         case 43:
            return this.startContainer$43(var2, var3);
         case 44:
            return this.DocumentLS$44(var2, var3);
         case 45:
            return this._get_async$45(var2, var3);
         case 46:
            return this._set_async$46(var2, var3);
         case 47:
            return this.abort$47(var2, var3);
         case 48:
            return this.load$48(var2, var3);
         case 49:
            return this.loadXML$49(var2, var3);
         case 50:
            return this.saveXML$50(var2, var3);
         case 51:
            return this.DOMImplementationLS$51(var2, var3);
         case 52:
            return this.createDOMBuilder$52(var2, var3);
         case 53:
            return this.createDOMWriter$53(var2, var3);
         case 54:
            return this.createDOMInputSource$54(var2, var3);
         default:
            return null;
      }
   }
}
