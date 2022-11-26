package xml;

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
@MTime(1498849383000L)
@Filename("xml/dom/__init__.py")
public class dom$py extends PyFunctionTable implements PyRunnable {
   static dom$py self;
   static final PyCode f$0;
   static final PyCode Node$1;
   static final PyCode DOMException$2;
   static final PyCode __init__$3;
   static final PyCode _get_code$4;
   static final PyCode IndexSizeErr$5;
   static final PyCode DomstringSizeErr$6;
   static final PyCode HierarchyRequestErr$7;
   static final PyCode WrongDocumentErr$8;
   static final PyCode InvalidCharacterErr$9;
   static final PyCode NoDataAllowedErr$10;
   static final PyCode NoModificationAllowedErr$11;
   static final PyCode NotFoundErr$12;
   static final PyCode NotSupportedErr$13;
   static final PyCode InuseAttributeErr$14;
   static final PyCode InvalidStateErr$15;
   static final PyCode SyntaxErr$16;
   static final PyCode InvalidModificationErr$17;
   static final PyCode NamespaceErr$18;
   static final PyCode InvalidAccessErr$19;
   static final PyCode ValidationErr$20;
   static final PyCode UserDataHandler$21;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("W3C Document Object Model implementation for Python.\n\nThe Python mapping of the Document Object Model is documented in the\nPython Library Reference in the section on the xml.dom package.\n\nThis package contains the following modules:\n\nminidom -- A simple implementation of the Level 1 DOM with namespace\n           support added (based on the Level 2 specification) and other\n           minor Level 2 functionality.\n\npulldom -- DOM builder supporting on-demand tree-building for selected\n           subtrees of the document.\n\n"));
      var1.setline(15);
      PyString.fromInterned("W3C Document Object Model implementation for Python.\n\nThe Python mapping of the Document Object Model is documented in the\nPython Library Reference in the section on the xml.dom package.\n\nThis package contains the following modules:\n\nminidom -- A simple implementation of the Level 1 DOM with namespace\n           support added (based on the Level 2 specification) and other\n           minor Level 2 functionality.\n\npulldom -- DOM builder supporting on-demand tree-building for selected\n           subtrees of the document.\n\n");
      var1.setline(18);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Node", var3, Node$1);
      var1.setlocal("Node", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(43);
      PyInteger var5 = Py.newInteger(1);
      var1.setlocal("INDEX_SIZE_ERR", var5);
      var3 = null;
      var1.setline(44);
      var5 = Py.newInteger(2);
      var1.setlocal("DOMSTRING_SIZE_ERR", var5);
      var3 = null;
      var1.setline(45);
      var5 = Py.newInteger(3);
      var1.setlocal("HIERARCHY_REQUEST_ERR", var5);
      var3 = null;
      var1.setline(46);
      var5 = Py.newInteger(4);
      var1.setlocal("WRONG_DOCUMENT_ERR", var5);
      var3 = null;
      var1.setline(47);
      var5 = Py.newInteger(5);
      var1.setlocal("INVALID_CHARACTER_ERR", var5);
      var3 = null;
      var1.setline(48);
      var5 = Py.newInteger(6);
      var1.setlocal("NO_DATA_ALLOWED_ERR", var5);
      var3 = null;
      var1.setline(49);
      var5 = Py.newInteger(7);
      var1.setlocal("NO_MODIFICATION_ALLOWED_ERR", var5);
      var3 = null;
      var1.setline(50);
      var5 = Py.newInteger(8);
      var1.setlocal("NOT_FOUND_ERR", var5);
      var3 = null;
      var1.setline(51);
      var5 = Py.newInteger(9);
      var1.setlocal("NOT_SUPPORTED_ERR", var5);
      var3 = null;
      var1.setline(52);
      var5 = Py.newInteger(10);
      var1.setlocal("INUSE_ATTRIBUTE_ERR", var5);
      var3 = null;
      var1.setline(53);
      var5 = Py.newInteger(11);
      var1.setlocal("INVALID_STATE_ERR", var5);
      var3 = null;
      var1.setline(54);
      var5 = Py.newInteger(12);
      var1.setlocal("SYNTAX_ERR", var5);
      var3 = null;
      var1.setline(55);
      var5 = Py.newInteger(13);
      var1.setlocal("INVALID_MODIFICATION_ERR", var5);
      var3 = null;
      var1.setline(56);
      var5 = Py.newInteger(14);
      var1.setlocal("NAMESPACE_ERR", var5);
      var3 = null;
      var1.setline(57);
      var5 = Py.newInteger(15);
      var1.setlocal("INVALID_ACCESS_ERR", var5);
      var3 = null;
      var1.setline(58);
      var5 = Py.newInteger(16);
      var1.setlocal("VALIDATION_ERR", var5);
      var3 = null;
      var1.setline(61);
      var3 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("DOMException", var3, DOMException$2);
      var1.setlocal("DOMException", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(75);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("IndexSizeErr", var3, IndexSizeErr$5);
      var1.setlocal("IndexSizeErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(78);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("DomstringSizeErr", var3, DomstringSizeErr$6);
      var1.setlocal("DomstringSizeErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(81);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("HierarchyRequestErr", var3, HierarchyRequestErr$7);
      var1.setlocal("HierarchyRequestErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(84);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("WrongDocumentErr", var3, WrongDocumentErr$8);
      var1.setlocal("WrongDocumentErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(87);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("InvalidCharacterErr", var3, InvalidCharacterErr$9);
      var1.setlocal("InvalidCharacterErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(90);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("NoDataAllowedErr", var3, NoDataAllowedErr$10);
      var1.setlocal("NoDataAllowedErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(93);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("NoModificationAllowedErr", var3, NoModificationAllowedErr$11);
      var1.setlocal("NoModificationAllowedErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(96);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("NotFoundErr", var3, NotFoundErr$12);
      var1.setlocal("NotFoundErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(99);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("NotSupportedErr", var3, NotSupportedErr$13);
      var1.setlocal("NotSupportedErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(102);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("InuseAttributeErr", var3, InuseAttributeErr$14);
      var1.setlocal("InuseAttributeErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(105);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("InvalidStateErr", var3, InvalidStateErr$15);
      var1.setlocal("InvalidStateErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(108);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("SyntaxErr", var3, SyntaxErr$16);
      var1.setlocal("SyntaxErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(111);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("InvalidModificationErr", var3, InvalidModificationErr$17);
      var1.setlocal("InvalidModificationErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(114);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("NamespaceErr", var3, NamespaceErr$18);
      var1.setlocal("NamespaceErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(117);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("InvalidAccessErr", var3, InvalidAccessErr$19);
      var1.setlocal("InvalidAccessErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(120);
      var3 = new PyObject[]{var1.getname("DOMException")};
      var4 = Py.makeClass("ValidationErr", var3, ValidationErr$20);
      var1.setlocal("ValidationErr", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var4 = Py.makeClass("UserDataHandler", var3, UserDataHandler$21);
      var1.setlocal("UserDataHandler", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(133);
      PyString var6 = PyString.fromInterned("http://www.w3.org/XML/1998/namespace");
      var1.setlocal("XML_NAMESPACE", var6);
      var3 = null;
      var1.setline(134);
      var6 = PyString.fromInterned("http://www.w3.org/2000/xmlns/");
      var1.setlocal("XMLNS_NAMESPACE", var6);
      var3 = null;
      var1.setline(135);
      var6 = PyString.fromInterned("http://www.w3.org/1999/xhtml");
      var1.setlocal("XHTML_NAMESPACE", var6);
      var3 = null;
      var1.setline(136);
      PyObject var7 = var1.getname("None");
      var1.setlocal("EMPTY_NAMESPACE", var7);
      var3 = null;
      var1.setline(137);
      var7 = var1.getname("None");
      var1.setlocal("EMPTY_PREFIX", var7);
      var3 = null;
      var1.setline(139);
      String[] var8 = new String[]{"getDOMImplementation", "registerDOMImplementation"};
      var3 = imp.importFrom("domreg", var8, var1, -1);
      var4 = var3[0];
      var1.setlocal("getDOMImplementation", var4);
      var4 = null;
      var4 = var3[1];
      var1.setlocal("registerDOMImplementation", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Node$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class giving the NodeType constants."));
      var1.setline(19);
      PyString.fromInterned("Class giving the NodeType constants.");
      var1.setline(28);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("ELEMENT_NODE", var3);
      var3 = null;
      var1.setline(29);
      var3 = Py.newInteger(2);
      var1.setlocal("ATTRIBUTE_NODE", var3);
      var3 = null;
      var1.setline(30);
      var3 = Py.newInteger(3);
      var1.setlocal("TEXT_NODE", var3);
      var3 = null;
      var1.setline(31);
      var3 = Py.newInteger(4);
      var1.setlocal("CDATA_SECTION_NODE", var3);
      var3 = null;
      var1.setline(32);
      var3 = Py.newInteger(5);
      var1.setlocal("ENTITY_REFERENCE_NODE", var3);
      var3 = null;
      var1.setline(33);
      var3 = Py.newInteger(6);
      var1.setlocal("ENTITY_NODE", var3);
      var3 = null;
      var1.setline(34);
      var3 = Py.newInteger(7);
      var1.setlocal("PROCESSING_INSTRUCTION_NODE", var3);
      var3 = null;
      var1.setline(35);
      var3 = Py.newInteger(8);
      var1.setlocal("COMMENT_NODE", var3);
      var3 = null;
      var1.setline(36);
      var3 = Py.newInteger(9);
      var1.setlocal("DOCUMENT_NODE", var3);
      var3 = null;
      var1.setline(37);
      var3 = Py.newInteger(10);
      var1.setlocal("DOCUMENT_TYPE_NODE", var3);
      var3 = null;
      var1.setline(38);
      var3 = Py.newInteger(11);
      var1.setlocal("DOCUMENT_FRAGMENT_NODE", var3);
      var3 = null;
      var1.setline(39);
      var3 = Py.newInteger(12);
      var1.setlocal("NOTATION_NODE", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject DOMException$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class for DOM exceptions.\n    Exceptions with specific codes are specializations of this class."));
      var1.setline(63);
      PyString.fromInterned("Abstract base class for DOM exceptions.\n    Exceptions with specific codes are specializations of this class.");
      var1.setline(65);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_code$4, (PyObject)null);
      var1.setlocal("_get_code", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._is(var1.getglobal("DOMException"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DOMException should not be instantiated directly")));
      } else {
         var1.setline(69);
         var10000 = var1.getglobal("Exception").__getattr__("__init__");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
         String[] var4 = new String[0];
         var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_code$4(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getlocal(0).__getattr__("code");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IndexSizeErr$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyObject var3 = var1.getname("INDEX_SIZE_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject DomstringSizeErr$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(79);
      PyObject var3 = var1.getname("DOMSTRING_SIZE_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject HierarchyRequestErr$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(82);
      PyObject var3 = var1.getname("HIERARCHY_REQUEST_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject WrongDocumentErr$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(85);
      PyObject var3 = var1.getname("WRONG_DOCUMENT_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject InvalidCharacterErr$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(88);
      PyObject var3 = var1.getname("INVALID_CHARACTER_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject NoDataAllowedErr$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(91);
      PyObject var3 = var1.getname("NO_DATA_ALLOWED_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject NoModificationAllowedErr$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(94);
      PyObject var3 = var1.getname("NO_MODIFICATION_ALLOWED_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject NotFoundErr$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(97);
      PyObject var3 = var1.getname("NOT_FOUND_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject NotSupportedErr$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(100);
      PyObject var3 = var1.getname("NOT_SUPPORTED_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject InuseAttributeErr$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(103);
      PyObject var3 = var1.getname("INUSE_ATTRIBUTE_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject InvalidStateErr$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(106);
      PyObject var3 = var1.getname("INVALID_STATE_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject SyntaxErr$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(109);
      PyObject var3 = var1.getname("SYNTAX_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject InvalidModificationErr$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(112);
      PyObject var3 = var1.getname("INVALID_MODIFICATION_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject NamespaceErr$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(115);
      PyObject var3 = var1.getname("NAMESPACE_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject InvalidAccessErr$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(118);
      PyObject var3 = var1.getname("INVALID_ACCESS_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject ValidationErr$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(121);
      PyObject var3 = var1.getname("VALIDATION_ERR");
      var1.setlocal("code", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject UserDataHandler$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class giving the operation constants for UserDataHandler.handle()."));
      var1.setline(124);
      PyString.fromInterned("Class giving the operation constants for UserDataHandler.handle().");
      var1.setline(128);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("NODE_CLONED", var3);
      var3 = null;
      var1.setline(129);
      var3 = Py.newInteger(2);
      var1.setlocal("NODE_IMPORTED", var3);
      var3 = null;
      var1.setline(130);
      var3 = Py.newInteger(3);
      var1.setlocal("NODE_DELETED", var3);
      var3 = null;
      var1.setline(131);
      var3 = Py.newInteger(4);
      var1.setlocal("NODE_RENAMED", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public dom$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Node$1 = Py.newCode(0, var2, var1, "Node", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DOMException$2 = Py.newCode(0, var2, var1, "DOMException", 61, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kw"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 65, true, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_code$4 = Py.newCode(1, var2, var1, "_get_code", 71, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IndexSizeErr$5 = Py.newCode(0, var2, var1, "IndexSizeErr", 75, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DomstringSizeErr$6 = Py.newCode(0, var2, var1, "DomstringSizeErr", 78, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HierarchyRequestErr$7 = Py.newCode(0, var2, var1, "HierarchyRequestErr", 81, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      WrongDocumentErr$8 = Py.newCode(0, var2, var1, "WrongDocumentErr", 84, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidCharacterErr$9 = Py.newCode(0, var2, var1, "InvalidCharacterErr", 87, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NoDataAllowedErr$10 = Py.newCode(0, var2, var1, "NoDataAllowedErr", 90, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NoModificationAllowedErr$11 = Py.newCode(0, var2, var1, "NoModificationAllowedErr", 93, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NotFoundErr$12 = Py.newCode(0, var2, var1, "NotFoundErr", 96, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NotSupportedErr$13 = Py.newCode(0, var2, var1, "NotSupportedErr", 99, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InuseAttributeErr$14 = Py.newCode(0, var2, var1, "InuseAttributeErr", 102, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidStateErr$15 = Py.newCode(0, var2, var1, "InvalidStateErr", 105, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SyntaxErr$16 = Py.newCode(0, var2, var1, "SyntaxErr", 108, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidModificationErr$17 = Py.newCode(0, var2, var1, "InvalidModificationErr", 111, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NamespaceErr$18 = Py.newCode(0, var2, var1, "NamespaceErr", 114, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidAccessErr$19 = Py.newCode(0, var2, var1, "InvalidAccessErr", 117, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ValidationErr$20 = Py.newCode(0, var2, var1, "ValidationErr", 120, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UserDataHandler$21 = Py.newCode(0, var2, var1, "UserDataHandler", 123, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dom$py("xml/dom$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dom$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Node$1(var2, var3);
         case 2:
            return this.DOMException$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._get_code$4(var2, var3);
         case 5:
            return this.IndexSizeErr$5(var2, var3);
         case 6:
            return this.DomstringSizeErr$6(var2, var3);
         case 7:
            return this.HierarchyRequestErr$7(var2, var3);
         case 8:
            return this.WrongDocumentErr$8(var2, var3);
         case 9:
            return this.InvalidCharacterErr$9(var2, var3);
         case 10:
            return this.NoDataAllowedErr$10(var2, var3);
         case 11:
            return this.NoModificationAllowedErr$11(var2, var3);
         case 12:
            return this.NotFoundErr$12(var2, var3);
         case 13:
            return this.NotSupportedErr$13(var2, var3);
         case 14:
            return this.InuseAttributeErr$14(var2, var3);
         case 15:
            return this.InvalidStateErr$15(var2, var3);
         case 16:
            return this.SyntaxErr$16(var2, var3);
         case 17:
            return this.InvalidModificationErr$17(var2, var3);
         case 18:
            return this.NamespaceErr$18(var2, var3);
         case 19:
            return this.InvalidAccessErr$19(var2, var3);
         case 20:
            return this.ValidationErr$20(var2, var3);
         case 21:
            return this.UserDataHandler$21(var2, var3);
         default:
            return null;
      }
   }
}
