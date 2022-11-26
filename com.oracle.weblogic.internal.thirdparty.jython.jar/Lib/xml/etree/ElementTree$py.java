package xml.etree;

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
@Filename("xml/etree/ElementTree.py")
public class ElementTree$py extends PyFunctionTable implements PyRunnable {
   static ElementTree$py self;
   static final PyCode f$0;
   static final PyCode _SimpleElementPath$1;
   static final PyCode find$2;
   static final PyCode findtext$3;
   static final PyCode iterfind$4;
   static final PyCode findall$5;
   static final PyCode ParseError$6;
   static final PyCode iselement$7;
   static final PyCode Element$8;
   static final PyCode __init__$9;
   static final PyCode __repr__$10;
   static final PyCode makeelement$11;
   static final PyCode copy$12;
   static final PyCode __len__$13;
   static final PyCode __nonzero__$14;
   static final PyCode __getitem__$15;
   static final PyCode __setitem__$16;
   static final PyCode __delitem__$17;
   static final PyCode append$18;
   static final PyCode extend$19;
   static final PyCode insert$20;
   static final PyCode remove$21;
   static final PyCode getchildren$22;
   static final PyCode find$23;
   static final PyCode findtext$24;
   static final PyCode findall$25;
   static final PyCode iterfind$26;
   static final PyCode clear$27;
   static final PyCode get$28;
   static final PyCode set$29;
   static final PyCode keys$30;
   static final PyCode items$31;
   static final PyCode iter$32;
   static final PyCode getiterator$33;
   static final PyCode itertext$34;
   static final PyCode SubElement$35;
   static final PyCode Comment$36;
   static final PyCode ProcessingInstruction$37;
   static final PyCode QName$38;
   static final PyCode __init__$39;
   static final PyCode __str__$40;
   static final PyCode __hash__$41;
   static final PyCode __cmp__$42;
   static final PyCode ElementTree$43;
   static final PyCode __init__$44;
   static final PyCode getroot$45;
   static final PyCode _setroot$46;
   static final PyCode parse$47;
   static final PyCode iter$48;
   static final PyCode getiterator$49;
   static final PyCode find$50;
   static final PyCode findtext$51;
   static final PyCode findall$52;
   static final PyCode iterfind$53;
   static final PyCode write$54;
   static final PyCode write_c14n$55;
   static final PyCode _namespaces$56;
   static final PyCode encode$57;
   static final PyCode add_qname$58;
   static final PyCode _serialize_xml$59;
   static final PyCode f$60;
   static final PyCode _serialize_html$61;
   static final PyCode f$62;
   static final PyCode _serialize_text$63;
   static final PyCode register_namespace$64;
   static final PyCode _raise_serialization_error$65;
   static final PyCode _encode$66;
   static final PyCode _escape_cdata$67;
   static final PyCode _escape_attrib$68;
   static final PyCode _escape_attrib_html$69;
   static final PyCode tostring$70;
   static final PyCode dummy$71;
   static final PyCode tostringlist$72;
   static final PyCode dummy$73;
   static final PyCode dump$74;
   static final PyCode parse$75;
   static final PyCode iterparse$76;
   static final PyCode _IterParseIterator$77;
   static final PyCode __init__$78;
   static final PyCode handler$79;
   static final PyCode handler$80;
   static final PyCode handler$81;
   static final PyCode handler$82;
   static final PyCode handler$83;
   static final PyCode next$84;
   static final PyCode __iter__$85;
   static final PyCode XML$86;
   static final PyCode XMLID$87;
   static final PyCode fromstringlist$88;
   static final PyCode TreeBuilder$89;
   static final PyCode __init__$90;
   static final PyCode close$91;
   static final PyCode _flush$92;
   static final PyCode data$93;
   static final PyCode start$94;
   static final PyCode end$95;
   static final PyCode XMLParser$96;
   static final PyCode __init__$97;
   static final PyCode _raiseerror$98;
   static final PyCode _fixtext$99;
   static final PyCode _fixname$100;
   static final PyCode _start$101;
   static final PyCode _start_list$102;
   static final PyCode _data$103;
   static final PyCode _end$104;
   static final PyCode _comment$105;
   static final PyCode _pi$106;
   static final PyCode _default$107;
   static final PyCode doctype$108;
   static final PyCode feed$109;
   static final PyCode close$110;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Comment"), PyString.fromInterned("dump"), PyString.fromInterned("Element"), PyString.fromInterned("ElementTree"), PyString.fromInterned("fromstring"), PyString.fromInterned("fromstringlist"), PyString.fromInterned("iselement"), PyString.fromInterned("iterparse"), PyString.fromInterned("parse"), PyString.fromInterned("ParseError"), PyString.fromInterned("PI"), PyString.fromInterned("ProcessingInstruction"), PyString.fromInterned("QName"), PyString.fromInterned("SubElement"), PyString.fromInterned("tostring"), PyString.fromInterned("tostringlist"), PyString.fromInterned("TreeBuilder"), PyString.fromInterned("VERSION"), PyString.fromInterned("XML"), PyString.fromInterned("XMLParser"), PyString.fromInterned("XMLTreeBuilder")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(75);
      PyString var8 = PyString.fromInterned("1.3.0");
      var1.setlocal("VERSION", var8);
      var3 = null;
      var1.setline(99);
      PyObject var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(100);
      var9 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var9);
      var3 = null;
      var1.setline(101);
      var9 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var9);
      var3 = null;
      var1.setline(104);
      PyObject[] var10 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("_SimpleElementPath", var10, _SimpleElementPath$1);
      var1.setlocal("_SimpleElementPath", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);

      PyException var11;
      String[] var12;
      try {
         var1.setline(127);
         var12 = new String[]{"ElementPath"};
         var10 = imp.importFrom("", var12, var1, 1);
         var4 = var10[0];
         var1.setlocal("ElementPath", var4);
         var4 = null;
      } catch (Throwable var7) {
         var11 = Py.setException(var7, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(129);
         var4 = var1.getname("_SimpleElementPath").__call__(var2);
         var1.setlocal("ElementPath", var4);
         var4 = null;
      }

      var1.setline(138);
      var10 = new PyObject[]{var1.getname("SyntaxError")};
      var4 = Py.makeClass("ParseError", var10, ParseError$6);
      var1.setlocal("ParseError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(150);
      var10 = Py.EmptyObjects;
      PyFunction var13 = new PyFunction(var1.f_globals, var10, iselement$7, (PyObject)null);
      var1.setlocal("iselement", var13);
      var3 = null;
      var1.setline(171);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Element", var10, Element$8);
      var1.setlocal("Element", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(511);
      var9 = var1.getname("Element");
      var1.setlocal("_Element", var9);
      var1.setlocal("_ElementInterface", var9);
      var1.setline(527);
      var10 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      var13 = new PyFunction(var1.f_globals, var10, SubElement$35, (PyObject)null);
      var1.setlocal("SubElement", var13);
      var3 = null;
      var1.setline(546);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, Comment$36, (PyObject)null);
      var1.setlocal("Comment", var13);
      var3 = null;
      var1.setline(561);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, ProcessingInstruction$37, (PyObject)null);
      var1.setlocal("ProcessingInstruction", var13);
      var3 = null;
      var1.setline(568);
      var9 = var1.getname("ProcessingInstruction");
      var1.setlocal("PI", var9);
      var3 = null;
      var1.setline(580);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("QName", var10, QName$38);
      var1.setlocal("QName", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(605);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ElementTree", var10, ElementTree$43);
      var1.setlocal("ElementTree", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(833);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, _namespaces$56, (PyObject)null);
      var1.setlocal("_namespaces", var13);
      var3 = null;
      var1.setline(901);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _serialize_xml$59, (PyObject)null);
      var1.setlocal("_serialize_xml", var13);
      var3 = null;
      var1.setline(948);
      PyTuple var14 = new PyTuple(new PyObject[]{PyString.fromInterned("area"), PyString.fromInterned("base"), PyString.fromInterned("basefont"), PyString.fromInterned("br"), PyString.fromInterned("col"), PyString.fromInterned("frame"), PyString.fromInterned("hr"), PyString.fromInterned("img"), PyString.fromInterned("input"), PyString.fromInterned("isindex"), PyString.fromInterned("link"), PyString.fromInterned("meta"), PyString.fromInterned("param")});
      var1.setlocal("HTML_EMPTY", var14);
      var3 = null;

      try {
         var1.setline(952);
         var9 = var1.getname("set").__call__(var2, var1.getname("HTML_EMPTY"));
         var1.setlocal("HTML_EMPTY", var9);
         var3 = null;
      } catch (Throwable var6) {
         var11 = Py.setException(var6, var1);
         if (!var11.match(var1.getname("NameError"))) {
            throw var11;
         }

         var1.setline(954);
      }

      var1.setline(956);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _serialize_html$61, (PyObject)null);
      var1.setlocal("_serialize_html", var13);
      var3 = null;
      var1.setline(1006);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _serialize_text$63, (PyObject)null);
      var1.setlocal("_serialize_text", var13);
      var3 = null;
      var1.setline(1012);
      PyDictionary var15 = new PyDictionary(new PyObject[]{PyString.fromInterned("xml"), var1.getname("_serialize_xml"), PyString.fromInterned("html"), var1.getname("_serialize_html"), PyString.fromInterned("text"), var1.getname("_serialize_text")});
      var1.setlocal("_serialize", var15);
      var3 = null;
      var1.setline(1031);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, register_namespace$64, (PyObject)null);
      var1.setlocal("register_namespace", var13);
      var3 = null;
      var1.setline(1039);
      var15 = new PyDictionary(new PyObject[]{PyString.fromInterned("http://www.w3.org/XML/1998/namespace"), PyString.fromInterned("xml"), PyString.fromInterned("http://www.w3.org/1999/xhtml"), PyString.fromInterned("html"), PyString.fromInterned("http://www.w3.org/1999/02/22-rdf-syntax-ns#"), PyString.fromInterned("rdf"), PyString.fromInterned("http://schemas.xmlsoap.org/wsdl/"), PyString.fromInterned("wsdl"), PyString.fromInterned("http://www.w3.org/2001/XMLSchema"), PyString.fromInterned("xs"), PyString.fromInterned("http://www.w3.org/2001/XMLSchema-instance"), PyString.fromInterned("xsi"), PyString.fromInterned("http://purl.org/dc/elements/1.1/"), PyString.fromInterned("dc")});
      var1.setlocal("_namespace_map", var15);
      var3 = null;
      var1.setline(1052);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _raise_serialization_error$65, (PyObject)null);
      var1.setlocal("_raise_serialization_error", var13);
      var3 = null;
      var1.setline(1057);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _encode$66, (PyObject)null);
      var1.setlocal("_encode", var13);
      var3 = null;
      var1.setline(1063);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _escape_cdata$67, (PyObject)null);
      var1.setlocal("_escape_cdata", var13);
      var3 = null;
      var1.setline(1079);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _escape_attrib$68, (PyObject)null);
      var1.setlocal("_escape_attrib", var13);
      var3 = null;
      var1.setline(1096);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, _escape_attrib_html$69, (PyObject)null);
      var1.setlocal("_escape_attrib_html", var13);
      var3 = null;
      var1.setline(1122);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, tostring$70, (PyObject)null);
      var1.setlocal("tostring", var13);
      var3 = null;
      var1.setline(1143);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, tostringlist$72, (PyObject)null);
      var1.setlocal("tostringlist", var13);
      var3 = null;
      var1.setline(1162);
      var10 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var10, dump$74, (PyObject)null);
      var1.setlocal("dump", var13);
      var3 = null;
      var1.setline(1182);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, parse$75, (PyObject)null);
      var1.setlocal("parse", var13);
      var3 = null;
      var1.setline(1198);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, iterparse$76, (PyObject)null);
      var1.setlocal("iterparse", var13);
      var3 = null;
      var1.setline(1212);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_IterParseIterator", var10, _IterParseIterator$77);
      var1.setlocal("_IterParseIterator", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1310);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, XML$86, (PyObject)null);
      var1.setlocal("XML", var13);
      var3 = null;
      var1.setline(1326);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, XMLID$87, (PyObject)null);
      var1.setlocal("XMLID", var13);
      var3 = null;
      var1.setline(1346);
      var9 = var1.getname("XML");
      var1.setlocal("fromstring", var9);
      var3 = null;
      var1.setline(1358);
      var10 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var10, fromstringlist$88, (PyObject)null);
      var1.setlocal("fromstringlist", var13);
      var3 = null;
      var1.setline(1378);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TreeBuilder", var10, TreeBuilder$89);
      var1.setlocal("TreeBuilder", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1468);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("XMLParser", var10, XMLParser$96);
      var1.setlocal("XMLParser", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1673);
      var9 = var1.getname("XMLParser");
      var1.setlocal("XMLTreeBuilder", var9);
      var3 = null;

      try {
         var1.setline(1677);
         var12 = new String[]{"_serialize_c14n"};
         var10 = imp.importFrom("ElementC14N", var12, var1, -1);
         var4 = var10[0];
         var1.setlocal("_serialize_c14n", var4);
         var4 = null;
         var1.setline(1678);
         var9 = var1.getname("_serialize_c14n");
         var1.getname("_serialize").__setitem__((PyObject)PyString.fromInterned("c14n"), var9);
         var3 = null;
      } catch (Throwable var5) {
         var11 = Py.setException(var5, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(1680);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _SimpleElementPath$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(106);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, find$2, (PyObject)null);
      var1.setlocal("find", var4);
      var3 = null;
      var1.setline(111);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, findtext$3, (PyObject)null);
      var1.setlocal("findtext", var4);
      var3 = null;
      var1.setline(116);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, iterfind$4, (PyObject)null);
      var1.setlocal("iterfind", var4);
      var3 = null;
      var1.setline(123);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, findall$5, (PyObject)null);
      var1.setlocal("findall", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject find$2(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(1).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(107);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(110);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(108);
         var5 = var1.getlocal(4).__getattr__("tag");
         var10000 = var5._eq(var1.getlocal(2));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(109);
      var5 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject findtext$3(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(0).__getattr__("find").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(115);
         Object var5 = var1.getlocal(5).__getattr__("text");
         if (!((PyObject)var5).__nonzero__()) {
            var5 = PyString.fromInterned("");
         }

         Object var4 = var5;
         var1.f_lasti = -1;
         return (PyObject)var4;
      }
   }

   public PyObject iterfind$4(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      label42: {
         label41: {
            Object var10000;
            switch (var1.f_lasti) {
               case 0:
               default:
                  var1.setline(117);
                  var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                  var7 = var3._eq(PyString.fromInterned(".//"));
                  var3 = null;
                  if (!var7.__nonzero__()) {
                     break label41;
                  }

                  var1.setline(118);
                  var3 = var1.getlocal(1).__getattr__("iter").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null)).__iter__();
                  break;
               case 1:
                  var5 = var1.f_savedlocals;
                  var3 = (PyObject)var5[3];
                  var4 = (PyObject)var5[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var7 = (PyObject)var10000;
                  break;
               case 2:
                  var5 = var1.f_savedlocals;
                  var3 = (PyObject)var5[3];
                  var4 = (PyObject)var5[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var7 = (PyObject)var10000;
                  break label42;
            }

            var1.setline(118);
            var4 = var3.__iternext__();
            if (var4 != null) {
               var1.setlocal(4, var4);
               var1.setline(119);
               var1.setline(119);
               var7 = var1.getlocal(4);
               var1.f_lasti = 1;
               var5 = new Object[]{null, null, null, var3, var4};
               var1.f_savedlocals = var5;
               return var7;
            }
         }

         var1.setline(120);
         var3 = var1.getlocal(1).__iter__();
      }

      do {
         var1.setline(120);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(121);
         PyObject var6 = var1.getlocal(4).__getattr__("tag");
         var7 = var6._eq(var1.getlocal(2));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(122);
      var1.setline(122);
      var7 = var1.getlocal(4);
      var1.f_lasti = 2;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject findall$5(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iterfind").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ParseError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(139);
      return var1.getf_locals();
   }

   public PyObject iselement$7(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Element"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("tag"));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Element$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(177);
      PyObject var3 = var1.getname("None");
      var1.setlocal("tag", var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getname("None");
      var1.setlocal("attrib", var3);
      var3 = null;
      var1.setline(195);
      var3 = var1.getname("None");
      var1.setlocal("text", var3);
      var3 = null;
      var1.setline(203);
      var3 = var1.getname("None");
      var1.setlocal("tail", var3);
      var3 = null;
      var1.setline(207);
      PyObject[] var4 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(214);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$10, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(224);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, makeelement$11, (PyObject)null);
      var1.setlocal("makeelement", var5);
      var3 = null;
      var1.setline(233);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copy$12, (PyObject)null);
      var1.setlocal("copy", var5);
      var3 = null;
      var1.setline(247);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __len__$13, (PyObject)null);
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(250);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __nonzero__$14, (PyObject)null);
      var1.setlocal("__nonzero__", var5);
      var3 = null;
      var1.setline(265);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getitem__$15, (PyObject)null);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(275);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setitem__$16, (PyObject)null);
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(289);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __delitem__$17, (PyObject)null);
      var1.setlocal("__delitem__", var5);
      var3 = null;
      var1.setline(300);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, append$18, (PyObject)null);
      var1.setlocal("append", var5);
      var3 = null;
      var1.setline(310);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, extend$19, (PyObject)null);
      var1.setlocal("extend", var5);
      var3 = null;
      var1.setline(320);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, insert$20, (PyObject)null);
      var1.setlocal("insert", var5);
      var3 = null;
      var1.setline(335);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, remove$21, (PyObject)null);
      var1.setlocal("remove", var5);
      var3 = null;
      var1.setline(346);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getchildren$22, (PyObject)null);
      var1.setlocal("getchildren", var5);
      var3 = null;
      var1.setline(362);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, find$23, (PyObject)null);
      var1.setlocal("find", var5);
      var3 = null;
      var1.setline(377);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, findtext$24, (PyObject)null);
      var1.setlocal("findtext", var5);
      var3 = null;
      var1.setline(389);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, findall$25, (PyObject)null);
      var1.setlocal("findall", var5);
      var3 = null;
      var1.setline(401);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, iterfind$26, (PyObject)null);
      var1.setlocal("iterfind", var5);
      var3 = null;
      var1.setline(409);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, clear$27, (PyObject)null);
      var1.setlocal("clear", var5);
      var3 = null;
      var1.setline(424);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, get$28, (PyObject)null);
      var1.setlocal("get", var5);
      var3 = null;
      var1.setline(434);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set$29, (PyObject)null);
      var1.setlocal("set", var5);
      var3 = null;
      var1.setline(445);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, keys$30, (PyObject)null);
      var1.setlocal("keys", var5);
      var3 = null;
      var1.setline(455);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, items$31, (PyObject)null);
      var1.setlocal("items", var5);
      var3 = null;
      var1.setline(471);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, iter$32, (PyObject)null);
      var1.setlocal("iter", var5);
      var3 = null;
      var1.setline(481);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, getiterator$33, (PyObject)null);
      var1.setlocal("getiterator", var5);
      var3 = null;
      var1.setline(498);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, itertext$34, (PyObject)null);
      var1.setlocal("itertext", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyObject var3 = var1.getlocal(2).__getattr__("copy").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(209);
      var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(3));
      var1.setline(210);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tag", var3);
      var3 = null;
      var1.setline(211);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("attrib", var3);
      var3 = null;
      var1.setline(212);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_children", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$10(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyObject var3 = PyString.fromInterned("<Element %s at 0x%x>")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("tag")), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makeelement$11(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy$12(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(0).__getattr__("makeelement").__call__(var2, var1.getlocal(0).__getattr__("tag"), var1.getlocal(0).__getattr__("attrib"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getlocal(0).__getattr__("text");
      var1.getlocal(1).__setattr__("text", var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(0).__getattr__("tail");
      var1.getlocal(1).__setattr__("tail", var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$13(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_children"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __nonzero__$14(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("The behavior of this method will change in future versions.  Use specific 'len(elem)' or 'elem is not None' test instead."), var1.getglobal("FutureWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(256);
      PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_children"));
      var10000 = var5._ne(Py.newInteger(0));
      var3 = null;
      var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject __getitem__$15(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = var1.getlocal(0).__getattr__("_children").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setitem__$16(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("_children").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$17(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      var1.getlocal(0).__getattr__("_children").__delitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append$18(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      var1.getlocal(0).__getattr__("_children").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject extend$19(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      var1.getlocal(0).__getattr__("_children").__getattr__("extend").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject insert$20(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      var1.getlocal(0).__getattr__("_children").__getattr__("insert").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject remove$21(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      var1.getlocal(0).__getattr__("_children").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getchildren$22(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("This method will be removed in future versions.  Use 'list(elem)' or iteration over elem instead."), var1.getglobal("DeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(352);
      PyObject var5 = var1.getlocal(0).__getattr__("_children");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject find$23(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyObject var3 = var1.getglobal("ElementPath").__getattr__("find").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findtext$24(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyObject var3 = var1.getglobal("ElementPath").__getattr__("findtext").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findall$25(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyObject var3 = var1.getglobal("ElementPath").__getattr__("findall").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterfind$26(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyObject var3 = var1.getglobal("ElementPath").__getattr__("iterfind").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject clear$27(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      var1.getlocal(0).__getattr__("attrib").__getattr__("clear").__call__(var2);
      var1.setline(411);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_children", var3);
      var3 = null;
      var1.setline(412);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("text", var4);
      var1.getlocal(0).__setattr__("tail", var4);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get$28(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyObject var3 = var1.getlocal(0).__getattr__("attrib").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set$29(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("attrib").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject keys$30(PyFrame var1, ThreadState var2) {
      var1.setline(446);
      PyObject var3 = var1.getlocal(0).__getattr__("attrib").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject items$31(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyObject var3 = var1.getlocal(0).__getattr__("attrib").__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iter$32(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      Object[] var8;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(472);
            var3 = var1.getlocal(1);
            var9 = var3._eq(PyString.fromInterned("*"));
            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(473);
               var3 = var1.getglobal("None");
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(474);
            var3 = var1.getlocal(1);
            var9 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var9.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("tag");
               var9 = var3._eq(var1.getlocal(1));
               var3 = null;
            }

            if (var9.__nonzero__()) {
               var1.setline(475);
               var1.setline(475);
               var9 = var1.getlocal(0);
               var1.f_lasti = 1;
               var8 = new Object[5];
               var1.f_savedlocals = var8;
               return var9;
            }

            var1.setline(476);
            var3 = var1.getlocal(0).__getattr__("_children").__iter__();
            break;
         case 1:
            var8 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
            var1.setline(476);
            var3 = var1.getlocal(0).__getattr__("_children").__iter__();
            break;
         case 2:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
            var1.setline(477);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var1.setlocal(2, var6);
               var1.setline(478);
               var1.setline(478);
               var9 = var1.getlocal(2);
               var1.f_lasti = 2;
               var7 = new Object[]{null, null, null, var3, var4, var5, var6};
               var1.f_savedlocals = var7;
               return var9;
            }
      }

      do {
         var1.setline(476);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(477);
         var5 = var1.getlocal(2).__getattr__("iter").__call__(var2, var1.getlocal(1)).__iter__();
         var1.setline(477);
         var6 = var5.__iternext__();
      } while(var6 == null);

      var1.setlocal(2, var6);
      var1.setline(478);
      var1.setline(478);
      var9 = var1.getlocal(2);
      var1.f_lasti = 2;
      var7 = new Object[]{null, null, null, var3, var4, var5, var6};
      var1.f_savedlocals = var7;
      return var9;
   }

   public PyObject getiterator$33(PyFrame var1, ThreadState var2) {
      var1.setline(483);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("This method will be removed in future versions.  Use 'elem.iter()' or 'list(elem.iter())' instead."), var1.getglobal("PendingDeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(488);
      PyObject var5 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iter").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject itertext$34(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      Object[] var7;
      Object[] var8;
      PyObject var9;
      PyObject var10;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(499);
            var3 = var1.getlocal(0).__getattr__("tag");
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(500);
            var10 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__();
            if (var10.__nonzero__()) {
               var3 = var1.getlocal(1);
               var10 = var3._isnot(var1.getglobal("None"));
               var3 = null;
            }

            if (var10.__nonzero__()) {
               var1.setline(501);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(502);
            if (var1.getlocal(0).__getattr__("text").__nonzero__()) {
               var1.setline(503);
               var1.setline(503);
               var10 = var1.getlocal(0).__getattr__("text");
               var1.f_lasti = 1;
               var8 = new Object[5];
               var1.f_savedlocals = var8;
               return var10;
            }

            var1.setline(504);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var8 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
            var1.setline(504);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 2:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var9 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
            var1.setline(505);
            var6 = var9.__iternext__();
            if (var6 != null) {
               var1.setlocal(3, var6);
               var1.setline(506);
               var1.setline(506);
               var10 = var1.getlocal(3);
               var1.f_lasti = 2;
               var7 = new Object[]{null, null, null, var3, var4, var9, var6};
               var1.f_savedlocals = var7;
               return var10;
            }

            var1.setline(507);
            if (var1.getlocal(2).__getattr__("tail").__nonzero__()) {
               var1.setline(508);
               var1.setline(508);
               var10 = var1.getlocal(2).__getattr__("tail");
               var1.f_lasti = 3;
               var5 = new Object[8];
               var5[3] = var3;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var10;
            }
            break;
         case 3:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
      }

      do {
         var1.setline(504);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(505);
         var9 = var1.getlocal(2).__getattr__("itertext").__call__(var2).__iter__();
         var1.setline(505);
         var6 = var9.__iternext__();
         if (var6 != null) {
            var1.setlocal(3, var6);
            var1.setline(506);
            var1.setline(506);
            var10 = var1.getlocal(3);
            var1.f_lasti = 2;
            var7 = new Object[]{null, null, null, var3, var4, var9, var6};
            var1.f_savedlocals = var7;
            return var10;
         }

         var1.setline(507);
      } while(!var1.getlocal(2).__getattr__("tail").__nonzero__());

      var1.setline(508);
      var1.setline(508);
      var10 = var1.getlocal(2).__getattr__("tail");
      var1.f_lasti = 3;
      var5 = new Object[8];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var10;
   }

   public PyObject SubElement$35(PyFrame var1, ThreadState var2) {
      var1.setline(528);
      PyObject var3 = var1.getlocal(2).__getattr__("copy").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(529);
      var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(3));
      var1.setline(530);
      var3 = var1.getlocal(0).__getattr__("makeelement").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(531);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(4));
      var1.setline(532);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Comment$36(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyObject var3 = var1.getglobal("Element").__call__(var2, var1.getglobal("Comment"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(548);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__setattr__("text", var3);
      var3 = null;
      var1.setline(549);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ProcessingInstruction$37(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyObject var3 = var1.getglobal("Element").__call__(var2, var1.getglobal("ProcessingInstruction"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(563);
      var3 = var1.getlocal(0);
      var1.getlocal(2).__setattr__("text", var3);
      var3 = null;
      var1.setline(564);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(565);
         var3 = var1.getlocal(2).__getattr__("text")._add(PyString.fromInterned(" "))._add(var1.getlocal(1));
         var1.getlocal(2).__setattr__("text", var3);
         var3 = null;
      }

      var1.setline(566);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject QName$38(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(581);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$39, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(585);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$40, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(587);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$41, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(589);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$42, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$39(PyFrame var1, ThreadState var2) {
      var1.setline(582);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(583);
         var3 = PyString.fromInterned("{%s}%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(584);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("text", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$40(PyFrame var1, ThreadState var2) {
      var1.setline(586);
      PyObject var3 = var1.getlocal(0).__getattr__("text");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$41(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyObject var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("text"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$42(PyFrame var1, ThreadState var2) {
      var1.setline(590);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("QName")).__nonzero__()) {
         var1.setline(591);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("text"), var1.getlocal(1).__getattr__("text"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(592);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("text"), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ElementTree$43(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(607);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$44, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(619);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getroot$45, (PyObject)null);
      var1.setlocal("getroot", var4);
      var3 = null;
      var1.setline(629);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _setroot$46, (PyObject)null);
      var1.setlocal("_setroot", var4);
      var3 = null;
      var1.setline(644);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, parse$47, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(671);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, iter$48, (PyObject)null);
      var1.setlocal("iter", var4);
      var3 = null;
      var1.setline(676);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getiterator$49, (PyObject)null);
      var1.setlocal("getiterator", var4);
      var3 = null;
      var1.setline(694);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, find$50, (PyObject)null);
      var1.setlocal("find", var4);
      var3 = null;
      var1.setline(718);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, findtext$51, (PyObject)null);
      var1.setlocal("findtext", var4);
      var3 = null;
      var1.setline(739);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, findall$52, (PyObject)null);
      var1.setlocal("findall", var4);
      var3 = null;
      var1.setline(761);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, iterfind$53, (PyObject)null);
      var1.setlocal("iterfind", var4);
      var3 = null;
      var1.setline(787);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, write$54, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(826);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_c14n$55, (PyObject)null);
      var1.setlocal("write_c14n", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$44(PyFrame var1, ThreadState var2) {
      var1.setline(609);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_root", var3);
      var3 = null;
      var1.setline(610);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(611);
         var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getroot$45(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyObject var3 = var1.getlocal(0).__getattr__("_root");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _setroot$46(PyFrame var1, ThreadState var2) {
      var1.setline(631);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_root", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$47(PyFrame var1, ThreadState var2) {
      var1.setline(645);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(646);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("read")).__not__().__nonzero__()) {
         var1.setline(647);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(648);
         var3 = var1.getglobal("True");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var3 = null;

      Throwable var10000;
      label60: {
         PyObject[] var4;
         PyObject var12;
         boolean var10001;
         try {
            var1.setline(650);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(651);
               PyObject var14 = var1.getglobal("XMLParser");
               var4 = new PyObject[]{var1.getglobal("TreeBuilder").__call__(var2)};
               String[] var5 = new String[]{"target"};
               var14 = var14.__call__(var2, var4, var5);
               var4 = null;
               var12 = var14;
               var1.setlocal(2, var12);
               var4 = null;
            }
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label60;
         }

         while(true) {
            label67: {
               label56: {
                  try {
                     var1.setline(652);
                     if (!Py.newInteger(1).__nonzero__()) {
                        break label56;
                     }
                  } catch (Throwable var11) {
                     var10000 = var11;
                     var10001 = false;
                     break;
                  }

                  try {
                     var1.setline(653);
                     var12 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(65536));
                     var1.setlocal(4, var12);
                     var4 = null;
                     var1.setline(654);
                     if (!var1.getlocal(4).__not__().__nonzero__()) {
                        break label67;
                     }
                  } catch (Throwable var10) {
                     var10000 = var10;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var1.setline(657);
                  var12 = var1.getlocal(2).__getattr__("close").__call__(var2);
                  var1.getlocal(0).__setattr__("_root", var12);
                  var4 = null;
                  var1.setline(658);
                  var12 = var1.getlocal(0).__getattr__("_root");
               } catch (Throwable var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }

               var1.setline(660);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(661);
                  var1.getlocal(1).__getattr__("close").__call__(var2);
               }

               try {
                  var1.f_lasti = -1;
                  return var12;
               } catch (Throwable var6) {
                  var10000 = var6;
                  var10001 = false;
                  break;
               }
            }

            try {
               var1.setline(656);
               var1.getlocal(2).__getattr__("feed").__call__(var2, var1.getlocal(4));
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break;
            }
         }
      }

      Throwable var13 = var10000;
      Py.addTraceback(var13, var1);
      var1.setline(660);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(661);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }

      throw (Throwable)var13;
   }

   public PyObject iter$48(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyObject var3 = var1.getlocal(0).__getattr__("_root").__getattr__("iter").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getiterator$49(PyFrame var1, ThreadState var2) {
      var1.setline(678);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("This method will be removed in future versions.  Use 'tree.iter()' or 'list(tree.iter())' instead."), var1.getglobal("PendingDeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(683);
      PyObject var5 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iter").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject find$50(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(697);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(698);
         var10000 = var1.getglobal("warnings").__getattr__("warn");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("This search is broken in 1.3 and earlier, and will be fixed in a future version.  If you rely on the current behaviour, change it to %r")._mod(var1.getlocal(1)), var1.getglobal("FutureWarning"), Py.newInteger(2)};
         String[] var4 = new String[]{"stacklevel"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      }

      var1.setline(704);
      var3 = var1.getlocal(0).__getattr__("_root").__getattr__("find").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findtext$51(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(721);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(722);
         var10000 = var1.getglobal("warnings").__getattr__("warn");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("This search is broken in 1.3 and earlier, and will be fixed in a future version.  If you rely on the current behaviour, change it to %r")._mod(var1.getlocal(1)), var1.getglobal("FutureWarning"), Py.newInteger(2)};
         String[] var4 = new String[]{"stacklevel"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      }

      var1.setline(728);
      var3 = var1.getlocal(0).__getattr__("_root").__getattr__("findtext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findall$52(PyFrame var1, ThreadState var2) {
      var1.setline(741);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(742);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(743);
         var10000 = var1.getglobal("warnings").__getattr__("warn");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("This search is broken in 1.3 and earlier, and will be fixed in a future version.  If you rely on the current behaviour, change it to %r")._mod(var1.getlocal(1)), var1.getglobal("FutureWarning"), Py.newInteger(2)};
         String[] var4 = new String[]{"stacklevel"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      }

      var1.setline(749);
      var3 = var1.getlocal(0).__getattr__("_root").__getattr__("findall").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterfind$53(PyFrame var1, ThreadState var2) {
      var1.setline(763);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(764);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(765);
         var10000 = var1.getglobal("warnings").__getattr__("warn");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("This search is broken in 1.3 and earlier, and will be fixed in a future version.  If you rely on the current behaviour, change it to %r")._mod(var1.getlocal(1)), var1.getglobal("FutureWarning"), Py.newInteger(2)};
         String[] var4 = new String[]{"stacklevel"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      }

      var1.setline(771);
      var3 = var1.getlocal(0).__getattr__("_root").__getattr__("iterfind").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$54(PyFrame var1, ThreadState var2) {
      var1.setline(794);
      PyObject var10000;
      PyString var3;
      PyObject var8;
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(795);
         var3 = PyString.fromInterned("xml");
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(796);
         var8 = var1.getlocal(5);
         var10000 = var8._notin(var1.getglobal("_serialize"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(798);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unknown method %r")._mod(var1.getlocal(5))));
         }
      }

      var1.setline(799);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("write")).__nonzero__()) {
         var1.setline(800);
         var8 = var1.getlocal(1);
         var1.setlocal(6, var8);
         var3 = null;
      } else {
         var1.setline(802);
         var8 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
         var1.setlocal(6, var8);
         var3 = null;
      }

      var3 = null;

      PyObject var4;
      try {
         var1.setline(804);
         var4 = var1.getlocal(6).__getattr__("write");
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(805);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(806);
            var4 = var1.getlocal(5);
            var10000 = var4._eq(PyString.fromInterned("c14n"));
            var4 = null;
            PyString var9;
            if (var10000.__nonzero__()) {
               var1.setline(807);
               var9 = PyString.fromInterned("utf-8");
               var1.setlocal(2, var9);
               var4 = null;
            } else {
               var1.setline(809);
               var9 = PyString.fromInterned("us-ascii");
               var1.setlocal(2, var9);
               var4 = null;
            }
         } else {
            var1.setline(810);
            var10000 = var1.getlocal(3);
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(3);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(2);
                  var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned("utf-8"), PyString.fromInterned("us-ascii")}));
                  var4 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(812);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(PyString.fromInterned("xml"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(813);
                  var1.getlocal(7).__call__(var2, PyString.fromInterned("<?xml version='1.0' encoding='%s'?>\n")._mod(var1.getlocal(2)));
               }
            }
         }

         var1.setline(814);
         var4 = var1.getlocal(5);
         var10000 = var4._eq(PyString.fromInterned("text"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(815);
            var1.getglobal("_serialize_text").__call__(var2, var1.getlocal(7), var1.getlocal(0).__getattr__("_root"), var1.getlocal(2));
         } else {
            var1.setline(817);
            var4 = var1.getglobal("_namespaces").__call__(var2, var1.getlocal(0).__getattr__("_root"), var1.getlocal(2), var1.getlocal(4));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(9, var6);
            var6 = null;
            var4 = null;
            var1.setline(820);
            var4 = var1.getglobal("_serialize").__getitem__(var1.getlocal(5));
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(821);
            var10000 = var1.getlocal(10);
            PyObject[] var10 = new PyObject[]{var1.getlocal(7), var1.getlocal(0).__getattr__("_root"), var1.getlocal(2), var1.getlocal(8), var1.getlocal(9)};
            var10000.__call__(var2, var10);
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(823);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getlocal(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(824);
            var1.getlocal(6).__getattr__("close").__call__(var2);
         }

         throw (Throwable)var7;
      }

      var1.setline(823);
      var4 = var1.getlocal(1);
      var10000 = var4._isnot(var1.getlocal(6));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(824);
         var1.getlocal(6).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_c14n$55(PyFrame var1, ThreadState var2) {
      var1.setline(828);
      PyObject var10000 = var1.getlocal(0).__getattr__("write");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("c14n")};
      String[] var4 = new String[]{"method"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _namespaces$56(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 1);
      var1.to_cell(2, 4);
      var1.setline(837);
      PyDictionary var3 = new PyDictionary(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(840);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(841);
      if (var1.getderef(4).__nonzero__()) {
         var1.setline(842);
         PyString var10 = PyString.fromInterned("");
         var1.getderef(0).__setitem__((PyObject)var1.getderef(4), var10);
         var3 = null;
      }

      var1.setline(844);
      PyObject[] var11 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var11;
      PyCode var10004 = encode$57;
      var11 = new PyObject[]{var1.getclosure(1)};
      PyFunction var12 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var11);
      var1.setderef(3, var12);
      var3 = null;
      var1.setline(847);
      var11 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var11;
      var10004 = add_qname$58;
      var11 = new PyObject[]{var1.getclosure(0), var1.getclosure(2), var1.getclosure(3), var1.getclosure(4)};
      var12 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var11);
      var1.setlocal(3, var12);
      var3 = null;

      PyObject var4;
      PyObject var15;
      try {
         var1.setline(876);
         var15 = var1.getlocal(0).__getattr__("iter");
         var1.setlocal(4, var15);
         var3 = null;
      } catch (Throwable var9) {
         PyException var14 = Py.setException(var9, var1);
         if (!var14.match(var1.getglobal("AttributeError"))) {
            throw var14;
         }

         var1.setline(878);
         var4 = var1.getlocal(0).__getattr__("getiterator");
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(879);
      var15 = var1.getlocal(4).__call__(var2).__iter__();

      while(true) {
         var1.setline(879);
         var4 = var15.__iternext__();
         if (var4 == null) {
            var1.setline(899);
            PyTuple var16 = new PyTuple(new PyObject[]{var1.getderef(2), var1.getderef(0)});
            var1.f_lasti = -1;
            return var16;
         }

         var1.setlocal(0, var4);
         var1.setline(880);
         PyObject var5 = var1.getlocal(0).__getattr__("tag");
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(881);
         PyObject var10000;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("QName")).__nonzero__()) {
            var1.setline(882);
            var5 = var1.getlocal(5).__getattr__("text");
            var10000 = var5._notin(var1.getderef(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(883);
               var1.getlocal(3).__call__(var2, var1.getlocal(5).__getattr__("text"));
            }
         } else {
            var1.setline(884);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(885);
               var5 = var1.getlocal(5);
               var10000 = var5._notin(var1.getderef(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(886);
                  var1.getlocal(3).__call__(var2, var1.getlocal(5));
               }
            } else {
               var1.setline(887);
               var5 = var1.getlocal(5);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(5);
                  var10000 = var5._isnot(var1.getglobal("Comment"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(5);
                     var10000 = var5._isnot(var1.getglobal("PI"));
                     var5 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(888);
                  var1.getglobal("_raise_serialization_error").__call__(var2, var1.getlocal(5));
               }
            }
         }

         var1.setline(889);
         var5 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(889);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(896);
               var5 = var1.getlocal(0).__getattr__("text");
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(897);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("QName"));
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(8).__getattr__("text");
                  var10000 = var5._notin(var1.getderef(2));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(898);
                  var1.getlocal(3).__call__(var2, var1.getlocal(8).__getattr__("text"));
               }
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(6, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(7, var8);
            var8 = null;
            var1.setline(890);
            PyObject var13;
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("QName")).__nonzero__()) {
               var1.setline(891);
               var13 = var1.getlocal(6).__getattr__("text");
               var1.setlocal(6, var13);
               var7 = null;
            }

            var1.setline(892);
            var13 = var1.getlocal(6);
            var10000 = var13._notin(var1.getderef(2));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(893);
               var1.getlocal(3).__call__(var2, var1.getlocal(6));
            }

            var1.setline(894);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("QName"));
            if (var10000.__nonzero__()) {
               var13 = var1.getlocal(7).__getattr__("text");
               var10000 = var13._notin(var1.getderef(2));
               var7 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(895);
               var1.getlocal(3).__call__(var2, var1.getlocal(7).__getattr__("text"));
            }
         }
      }
   }

   public PyObject encode$57(PyFrame var1, ThreadState var2) {
      var1.setline(845);
      PyObject var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getderef(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_qname$58(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(850);
         PyObject var7 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         PyObject var10000 = var7._eq(PyString.fromInterned("{"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(851);
            var7 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__getattr__("rsplit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}"), (PyObject)Py.newInteger(1));
            PyObject[] var4 = Py.unpackSequence(var7, 2);
            PyObject var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
            var1.setline(852);
            var7 = var1.getderef(0).__getattr__("get").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(853);
            var7 = var1.getlocal(3);
            var10000 = var7._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(854);
               var7 = var1.getglobal("_namespace_map").__getattr__("get").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var7);
               var3 = null;
               var1.setline(855);
               var7 = var1.getlocal(3);
               var10000 = var7._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(856);
                  var7 = PyString.fromInterned("ns%d")._mod(var1.getglobal("len").__call__(var2, var1.getderef(0)));
                  var1.setlocal(3, var7);
                  var3 = null;
               }

               var1.setline(857);
               var7 = var1.getlocal(3);
               var10000 = var7._ne(PyString.fromInterned("xml"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(858);
                  var7 = var1.getlocal(3);
                  var1.getderef(0).__setitem__(var1.getlocal(1), var7);
                  var3 = null;
               }
            }

            var1.setline(859);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(860);
               var7 = var1.getderef(2).__call__(var2, PyString.fromInterned("%s:%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)})));
               var1.getderef(1).__setitem__(var1.getlocal(0), var7);
               var3 = null;
            } else {
               var1.setline(862);
               var7 = var1.getderef(2).__call__(var2, var1.getlocal(2));
               var1.getderef(1).__setitem__(var1.getlocal(0), var7);
               var3 = null;
            }
         } else {
            var1.setline(864);
            if (var1.getderef(3).__nonzero__()) {
               var1.setline(866);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot use non-qualified names with default_namespace option")));
            }

            var1.setline(870);
            var7 = var1.getderef(2).__call__(var2, var1.getlocal(0));
            var1.getderef(1).__setitem__(var1.getlocal(0), var7);
            var3 = null;
         }
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("TypeError"))) {
            throw var3;
         }

         var1.setline(872);
         var1.getglobal("_raise_serialization_error").__call__(var2, var1.getlocal(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _serialize_xml$59(PyFrame var1, ThreadState var2) {
      var1.setline(902);
      PyObject var3 = var1.getlocal(1).__getattr__("tag");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(903);
      var3 = var1.getlocal(1).__getattr__("text");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(904);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("Comment"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(905);
         var1.getlocal(0).__call__(var2, PyString.fromInterned("<!--%s-->")._mod(var1.getglobal("_encode").__call__(var2, var1.getlocal(6), var1.getlocal(2))));
      } else {
         var1.setline(906);
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("ProcessingInstruction"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(907);
            var1.getlocal(0).__call__(var2, PyString.fromInterned("<?%s?>")._mod(var1.getglobal("_encode").__call__(var2, var1.getlocal(6), var1.getlocal(2))));
         } else {
            var1.setline(909);
            var3 = var1.getlocal(3).__getitem__(var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(910);
            var3 = var1.getlocal(5);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            PyObject var4;
            PyObject[] var5;
            if (var10000.__nonzero__()) {
               var1.setline(911);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(912);
                  var1.getlocal(0).__call__(var2, var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(6), var1.getlocal(2)));
               }

               var1.setline(913);
               var3 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(913);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(7, var4);
                  var1.setline(914);
                  var10000 = var1.getglobal("_serialize_xml");
                  var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(7), var1.getlocal(2), var1.getlocal(3), var1.getglobal("None")};
                  var10000.__call__(var2, var5);
               }
            } else {
               var1.setline(916);
               var1.getlocal(0).__call__(var2, PyString.fromInterned("<")._add(var1.getlocal(5)));
               var1.setline(917);
               var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(918);
               var10000 = var1.getlocal(8);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(919);
                  PyObject var6;
                  PyObject var9;
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(920);
                     var10000 = var1.getglobal("sorted");
                     PyObject[] var10 = new PyObject[]{var1.getlocal(4).__getattr__("items").__call__(var2), null};
                     var1.setline(921);
                     PyObject[] var7 = Py.EmptyObjects;
                     var10[1] = new PyFunction(var1.f_globals, var7, f$60);
                     String[] var8 = new String[]{"key"};
                     var10000 = var10000.__call__(var2, var10, var8);
                     var3 = null;
                     var3 = var10000.__iter__();

                     while(true) {
                        var1.setline(920);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var5 = Py.unpackSequence(var4, 2);
                        var6 = var5[0];
                        var1.setlocal(9, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(10, var6);
                        var6 = null;
                        var1.setline(922);
                        if (var1.getlocal(10).__nonzero__()) {
                           var1.setline(923);
                           var9 = PyString.fromInterned(":")._add(var1.getlocal(10));
                           var1.setlocal(10, var9);
                           var5 = null;
                        }

                        var1.setline(924);
                        var1.getlocal(0).__call__(var2, PyString.fromInterned(" xmlns%s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(10).__getattr__("encode").__call__(var2, var1.getlocal(2)), var1.getglobal("_escape_attrib").__call__(var2, var1.getlocal(9), var1.getlocal(2))})));
                     }
                  }

                  var1.setline(928);
                  var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(8)).__iter__();

                  while(true) {
                     var1.setline(928);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(10, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(929);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("QName")).__nonzero__()) {
                        var1.setline(930);
                        var9 = var1.getlocal(10).__getattr__("text");
                        var1.setlocal(10, var9);
                        var5 = null;
                     }

                     var1.setline(931);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("QName")).__nonzero__()) {
                        var1.setline(932);
                        var9 = var1.getlocal(3).__getitem__(var1.getlocal(9).__getattr__("text"));
                        var1.setlocal(9, var9);
                        var5 = null;
                     } else {
                        var1.setline(934);
                        var9 = var1.getglobal("_escape_attrib").__call__(var2, var1.getlocal(9), var1.getlocal(2));
                        var1.setlocal(9, var9);
                        var5 = null;
                     }

                     var1.setline(935);
                     var1.getlocal(0).__call__(var2, PyString.fromInterned(" %s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(var1.getlocal(10)), var1.getlocal(9)})));
                  }
               }

               var1.setline(936);
               var10000 = var1.getlocal(6);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(937);
                  var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
                  var1.setline(938);
                  if (var1.getlocal(6).__nonzero__()) {
                     var1.setline(939);
                     var1.getlocal(0).__call__(var2, var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(6), var1.getlocal(2)));
                  }

                  var1.setline(940);
                  var3 = var1.getlocal(1).__iter__();

                  while(true) {
                     var1.setline(940);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(942);
                        var1.getlocal(0).__call__(var2, PyString.fromInterned("</")._add(var1.getlocal(5))._add(PyString.fromInterned(">")));
                        break;
                     }

                     var1.setlocal(7, var4);
                     var1.setline(941);
                     var10000 = var1.getglobal("_serialize_xml");
                     var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(7), var1.getlocal(2), var1.getlocal(3), var1.getglobal("None")};
                     var10000.__call__(var2, var5);
                  }
               } else {
                  var1.setline(944);
                  var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" />"));
               }
            }
         }
      }

      var1.setline(945);
      if (var1.getlocal(1).__getattr__("tail").__nonzero__()) {
         var1.setline(946);
         var1.getlocal(0).__call__(var2, var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(1).__getattr__("tail"), var1.getlocal(2)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$60(PyFrame var1, ThreadState var2) {
      var1.setline(921);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _serialize_html$61(PyFrame var1, ThreadState var2) {
      var1.setline(957);
      PyObject var3 = var1.getlocal(1).__getattr__("tag");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(958);
      var3 = var1.getlocal(1).__getattr__("text");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(959);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("Comment"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(960);
         var1.getlocal(0).__call__(var2, PyString.fromInterned("<!--%s-->")._mod(var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(6), var1.getlocal(2))));
      } else {
         var1.setline(961);
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("ProcessingInstruction"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(962);
            var1.getlocal(0).__call__(var2, PyString.fromInterned("<?%s?>")._mod(var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(6), var1.getlocal(2))));
         } else {
            var1.setline(964);
            var3 = var1.getlocal(3).__getitem__(var1.getlocal(5));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(965);
            var3 = var1.getlocal(5);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            PyObject var4;
            PyObject[] var5;
            if (var10000.__nonzero__()) {
               var1.setline(966);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(967);
                  var1.getlocal(0).__call__(var2, var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(6), var1.getlocal(2)));
               }

               var1.setline(968);
               var3 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(968);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(7, var4);
                  var1.setline(969);
                  var10000 = var1.getglobal("_serialize_html");
                  var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(7), var1.getlocal(2), var1.getlocal(3), var1.getglobal("None")};
                  var10000.__call__(var2, var5);
               }
            } else {
               var1.setline(971);
               var1.getlocal(0).__call__(var2, PyString.fromInterned("<")._add(var1.getlocal(5)));
               var1.setline(972);
               var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(973);
               var10000 = var1.getlocal(8);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(974);
                  PyObject var6;
                  PyObject var9;
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(975);
                     var10000 = var1.getglobal("sorted");
                     PyObject[] var10 = new PyObject[]{var1.getlocal(4).__getattr__("items").__call__(var2), null};
                     var1.setline(976);
                     PyObject[] var7 = Py.EmptyObjects;
                     var10[1] = new PyFunction(var1.f_globals, var7, f$62);
                     String[] var8 = new String[]{"key"};
                     var10000 = var10000.__call__(var2, var10, var8);
                     var3 = null;
                     var3 = var10000.__iter__();

                     while(true) {
                        var1.setline(975);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var5 = Py.unpackSequence(var4, 2);
                        var6 = var5[0];
                        var1.setlocal(9, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(10, var6);
                        var6 = null;
                        var1.setline(977);
                        if (var1.getlocal(10).__nonzero__()) {
                           var1.setline(978);
                           var9 = PyString.fromInterned(":")._add(var1.getlocal(10));
                           var1.setlocal(10, var9);
                           var5 = null;
                        }

                        var1.setline(979);
                        var1.getlocal(0).__call__(var2, PyString.fromInterned(" xmlns%s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(10).__getattr__("encode").__call__(var2, var1.getlocal(2)), var1.getglobal("_escape_attrib").__call__(var2, var1.getlocal(9), var1.getlocal(2))})));
                     }
                  }

                  var1.setline(983);
                  var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(8)).__iter__();

                  while(true) {
                     var1.setline(983);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(10, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(984);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("QName")).__nonzero__()) {
                        var1.setline(985);
                        var9 = var1.getlocal(10).__getattr__("text");
                        var1.setlocal(10, var9);
                        var5 = null;
                     }

                     var1.setline(986);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("QName")).__nonzero__()) {
                        var1.setline(987);
                        var9 = var1.getlocal(3).__getitem__(var1.getlocal(9).__getattr__("text"));
                        var1.setlocal(9, var9);
                        var5 = null;
                     } else {
                        var1.setline(989);
                        var9 = var1.getglobal("_escape_attrib_html").__call__(var2, var1.getlocal(9), var1.getlocal(2));
                        var1.setlocal(9, var9);
                        var5 = null;
                     }

                     var1.setline(991);
                     var1.getlocal(0).__call__(var2, PyString.fromInterned(" %s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(var1.getlocal(10)), var1.getlocal(9)})));
                  }
               }

               var1.setline(992);
               var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
               var1.setline(993);
               var3 = var1.getlocal(5).__getattr__("lower").__call__(var2);
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(994);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(995);
                  var3 = var1.getlocal(11);
                  var10000 = var3._eq(PyString.fromInterned("script"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(11);
                     var10000 = var3._eq(PyString.fromInterned("style"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(996);
                     var1.getlocal(0).__call__(var2, var1.getglobal("_encode").__call__(var2, var1.getlocal(6), var1.getlocal(2)));
                  } else {
                     var1.setline(998);
                     var1.getlocal(0).__call__(var2, var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(6), var1.getlocal(2)));
                  }
               }

               var1.setline(999);
               var3 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(999);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1001);
                     var3 = var1.getlocal(11);
                     var10000 = var3._notin(var1.getglobal("HTML_EMPTY"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1002);
                        var1.getlocal(0).__call__(var2, PyString.fromInterned("</")._add(var1.getlocal(5))._add(PyString.fromInterned(">")));
                     }
                     break;
                  }

                  var1.setlocal(7, var4);
                  var1.setline(1000);
                  var10000 = var1.getglobal("_serialize_html");
                  var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(7), var1.getlocal(2), var1.getlocal(3), var1.getglobal("None")};
                  var10000.__call__(var2, var5);
               }
            }
         }
      }

      var1.setline(1003);
      if (var1.getlocal(1).__getattr__("tail").__nonzero__()) {
         var1.setline(1004);
         var1.getlocal(0).__call__(var2, var1.getglobal("_escape_cdata").__call__(var2, var1.getlocal(1).__getattr__("tail"), var1.getlocal(2)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$62(PyFrame var1, ThreadState var2) {
      var1.setline(976);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _serialize_text$63(PyFrame var1, ThreadState var2) {
      var1.setline(1007);
      PyObject var3 = var1.getlocal(1).__getattr__("itertext").__call__(var2).__iter__();

      while(true) {
         var1.setline(1007);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1009);
            if (var1.getlocal(1).__getattr__("tail").__nonzero__()) {
               var1.setline(1010);
               var1.getlocal(0).__call__(var2, var1.getlocal(1).__getattr__("tail").__getattr__("encode").__call__(var2, var1.getlocal(2)));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1008);
         var1.getlocal(0).__call__(var2, var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject register_namespace$64(PyFrame var1, ThreadState var2) {
      var1.setline(1032);
      if (var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ns\\d+$"), (PyObject)var1.getlocal(0)).__nonzero__()) {
         var1.setline(1033);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Prefix format reserved for internal use")));
      } else {
         var1.setline(1034);
         PyObject var3 = var1.getglobal("_namespace_map").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(1034);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1037);
               var3 = var1.getlocal(0);
               var1.getglobal("_namespace_map").__setitem__(var1.getlocal(1), var3);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(1035);
            PyObject var7 = var1.getlocal(2);
            PyObject var10000 = var7._eq(var1.getlocal(1));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var7 = var1.getlocal(3);
               var10000 = var7._eq(var1.getlocal(0));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1036);
               var1.getglobal("_namespace_map").__delitem__(var1.getlocal(2));
            }
         }
      }
   }

   public PyObject _raise_serialization_error$65(PyFrame var1, ThreadState var2) {
      var1.setline(1053);
      throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("cannot serialize %r (type %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__")}))));
   }

   public PyObject _encode$66(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(1059);
         PyObject var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("xmlcharrefreplace"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
            var1.setline(1061);
            var1.getglobal("_raise_serialization_error").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return Py.None;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _escape_cdata$67(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(1069);
         PyString var3 = PyString.fromInterned("&");
         PyObject var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(1070);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1071);
         var3 = PyString.fromInterned("<");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1072);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1073);
         var3 = PyString.fromInterned(">");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1074);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1075);
         var6 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("xmlcharrefreplace"));
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
            var1.setline(1077);
            var1.getglobal("_raise_serialization_error").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return Py.None;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _escape_attrib$68(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(1082);
         PyString var3 = PyString.fromInterned("&");
         PyObject var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(1083);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1084);
         var3 = PyString.fromInterned("<");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1085);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1086);
         var3 = PyString.fromInterned(">");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1087);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1088);
         var3 = PyString.fromInterned("\"");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1089);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1090);
         var3 = PyString.fromInterned("\n");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1091);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("&#10;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1092);
         var6 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("xmlcharrefreplace"));
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
            var1.setline(1094);
            var1.getglobal("_raise_serialization_error").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return Py.None;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _escape_attrib_html$69(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(1099);
         PyString var3 = PyString.fromInterned("&");
         PyObject var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(1100);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1101);
         var3 = PyString.fromInterned(">");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1102);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1103);
         var3 = PyString.fromInterned("\"");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1104);
            var6 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;"));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(1105);
         var6 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("xmlcharrefreplace"));
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
            var1.setline(1107);
            var1.getglobal("_raise_serialization_error").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return Py.None;
         } else {
            throw var4;
         }
      }
   }

   public PyObject tostring$70(PyFrame var1, ThreadState var2) {
      var1.setline(1123);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("dummy", var3, dummy$71);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1125);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(1126);
      PyObject var6 = var1.getlocal(3).__call__(var2);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1127);
      var6 = var1.getlocal(4).__getattr__("append");
      var1.getlocal(5).__setattr__("write", var6);
      var3 = null;
      var1.setline(1128);
      PyObject var10000 = var1.getglobal("ElementTree").__call__(var2, var1.getlocal(0)).__getattr__("write");
      var3 = new PyObject[]{var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)};
      String[] var7 = new String[]{"method"};
      var10000.__call__(var2, var3, var7);
      var3 = null;
      var1.setline(1129);
      var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject dummy$71(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1124);
      return var1.getf_locals();
   }

   public PyObject tostringlist$72(PyFrame var1, ThreadState var2) {
      var1.setline(1144);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("dummy", var3, dummy$73);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1146);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(1147);
      PyObject var6 = var1.getlocal(3).__call__(var2);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1148);
      var6 = var1.getlocal(4).__getattr__("append");
      var1.getlocal(5).__setattr__("write", var6);
      var3 = null;
      var1.setline(1149);
      PyObject var10000 = var1.getglobal("ElementTree").__call__(var2, var1.getlocal(0)).__getattr__("write");
      var3 = new PyObject[]{var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)};
      String[] var7 = new String[]{"method"};
      var10000.__call__(var2, var3, var7);
      var3 = null;
      var1.setline(1151);
      var6 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject dummy$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1145);
      return var1.getf_locals();
   }

   public PyObject dump$74(PyFrame var1, ThreadState var2) {
      var1.setline(1164);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("ElementTree")).__not__().__nonzero__()) {
         var1.setline(1165);
         var3 = var1.getglobal("ElementTree").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1166);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("sys").__getattr__("stdout"));
      var1.setline(1167);
      var3 = var1.getlocal(0).__getattr__("getroot").__call__(var2).__getattr__("tail");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1168);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
         var10000 = var3._ne(PyString.fromInterned("\n"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1169);
         var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$75(PyFrame var1, ThreadState var2) {
      var1.setline(1183);
      PyObject var3 = var1.getglobal("ElementTree").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1184);
      var1.getlocal(2).__getattr__("parse").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1185);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterparse$76(PyFrame var1, ThreadState var2) {
      var1.setline(1199);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1200);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("read")).__not__().__nonzero__()) {
         var1.setline(1201);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(1202);
         var3 = var1.getglobal("True");
         var1.setlocal(3, var3);
         var3 = null;
      }

      try {
         var1.setline(1204);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(1205);
            PyObject var10000 = var1.getglobal("XMLParser");
            PyObject[] var6 = new PyObject[]{var1.getglobal("TreeBuilder").__call__(var2)};
            String[] var4 = new String[]{"target"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(1206);
         var3 = var1.getglobal("_IterParseIterator").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(1208);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1209);
            var1.getlocal(0).__getattr__("close").__call__(var2);
         }

         var1.setline(1210);
         throw Py.makeException();
      }
   }

   public PyObject _IterParseIterator$77(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1214);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$78, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$84, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(1297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$85, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$78(PyFrame var1, ThreadState var2) {
      var1.setline(1215);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_file", var3);
      var3 = null;
      var1.setline(1216);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_close_file", var3);
      var3 = null;
      var1.setline(1217);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_events", var8);
      var3 = null;
      var1.setline(1218);
      PyInteger var9 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_index", var9);
      var3 = null;
      var1.setline(1219);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_error", var3);
      var3 = null;
      var1.setline(1220);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("root", var3);
      var1.getlocal(0).__setattr__("_root", var3);
      var1.setline(1221);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_parser", var3);
      var3 = null;
      var1.setline(1223);
      var3 = var1.getlocal(0).__getattr__("_parser").__getattr__("_parser");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1224);
      var3 = var1.getlocal(0).__getattr__("_events").__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1225);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1226);
         var8 = new PyList(new PyObject[]{PyString.fromInterned("end")});
         var1.setlocal(2, var8);
         var3 = null;
      }

      var1.setline(1227);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         while(true) {
            var1.setline(1227);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(6, var4);
            var1.setline(1228);
            PyObject var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("start"));
            var5 = null;
            PyObject[] var10;
            PyFunction var12;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(1230);
                  PyInteger var15 = Py.newInteger(1);
                  var1.getlocal(3).__setattr__((String)"ordered_attributes", var15);
                  var5 = null;
                  var1.setline(1231);
                  var15 = Py.newInteger(1);
                  var1.getlocal(3).__setattr__((String)"specified_attributes", var15);
                  var5 = null;
                  var1.setline(1232);
                  var10 = new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(0).__getattr__("_parser").__getattr__("_start_list")};
                  var12 = new PyFunction(var1.f_globals, var10, handler$79, (PyObject)null);
                  var1.setlocal(7, var12);
                  var5 = null;
                  var1.setline(1235);
                  var5 = var1.getlocal(7);
                  var1.getlocal(3).__setattr__("StartElementHandler", var5);
                  var5 = null;
               } catch (Throwable var7) {
                  PyException var14 = Py.setException(var7, var1);
                  if (!var14.match(var1.getglobal("AttributeError"))) {
                     throw var14;
                  }

                  var1.setline(1237);
                  PyObject[] var6 = new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(0).__getattr__("_parser").__getattr__("_start")};
                  PyFunction var11 = new PyFunction(var1.f_globals, var6, handler$80, (PyObject)null);
                  var1.setlocal(7, var11);
                  var6 = null;
                  var1.setline(1240);
                  PyObject var13 = var1.getlocal(7);
                  var1.getlocal(3).__setattr__("StartElementHandler", var13);
                  var6 = null;
               }
            } else {
               var1.setline(1241);
               var5 = var1.getlocal(6);
               var10000 = var5._eq(PyString.fromInterned("end"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1242);
                  var10 = new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(0).__getattr__("_parser").__getattr__("_end")};
                  var12 = new PyFunction(var1.f_globals, var10, handler$81, (PyObject)null);
                  var1.setlocal(7, var12);
                  var5 = null;
                  var1.setline(1245);
                  var5 = var1.getlocal(7);
                  var1.getlocal(3).__setattr__("EndElementHandler", var5);
                  var5 = null;
               } else {
                  var1.setline(1246);
                  var5 = var1.getlocal(6);
                  var10000 = var5._eq(PyString.fromInterned("start-ns"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1247);
                     var10 = new PyObject[]{var1.getlocal(6), var1.getlocal(5)};
                     var12 = new PyFunction(var1.f_globals, var10, handler$82, (PyObject)null);
                     var1.setlocal(7, var12);
                     var5 = null;
                     var1.setline(1253);
                     var5 = var1.getlocal(7);
                     var1.getlocal(3).__setattr__("StartNamespaceDeclHandler", var5);
                     var5 = null;
                  } else {
                     var1.setline(1254);
                     var5 = var1.getlocal(6);
                     var10000 = var5._eq(PyString.fromInterned("end-ns"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(1259);
                        throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unknown event %r")._mod(var1.getlocal(6))));
                     }

                     var1.setline(1255);
                     var10 = new PyObject[]{var1.getlocal(6), var1.getlocal(5)};
                     var12 = new PyFunction(var1.f_globals, var10, handler$83, (PyObject)null);
                     var1.setlocal(7, var12);
                     var5 = null;
                     var1.setline(1257);
                     var5 = var1.getlocal(7);
                     var1.getlocal(3).__setattr__("EndNamespaceDeclHandler", var5);
                     var5 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject handler$79(PyFrame var1, ThreadState var2) {
      var1.setline(1234);
      var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(1))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handler$80(PyFrame var1, ThreadState var2) {
      var1.setline(1239);
      var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(1))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handler$81(PyFrame var1, ThreadState var2) {
      var1.setline(1244);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3).__call__(var2, var1.getlocal(0))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handler$82(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(1249);
         Object var10000 = var1.getlocal(1);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("");
         }

         PyObject var5 = ((PyObject)var10000).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.setlocal(1, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("UnicodeError"))) {
            throw var3;
         }

         var1.setline(1251);
      }

      var1.setline(1252);
      PyObject var6 = var1.getlocal(3);
      PyTuple var10002 = new PyTuple;
      PyObject[] var10004 = new PyObject[]{var1.getlocal(2), null};
      PyTuple var10007 = new PyTuple;
      PyObject[] var10009 = new PyObject[2];
      Object var10012 = var1.getlocal(0);
      if (!((PyObject)var10012).__nonzero__()) {
         var10012 = PyString.fromInterned("");
      }

      var10009[0] = (PyObject)var10012;
      var10012 = var1.getlocal(1);
      if (!((PyObject)var10012).__nonzero__()) {
         var10012 = PyString.fromInterned("");
      }

      var10009[1] = (PyObject)var10012;
      var10007.<init>(var10009);
      var10004[1] = var10007;
      var10002.<init>(var10004);
      var6.__call__((ThreadState)var2, (PyObject)var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handler$83(PyFrame var1, ThreadState var2) {
      var1.setline(1256);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("None")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject next$84(PyFrame var1, ThreadState var2) {
      try {
         while(true) {
            var1.setline(1263);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            PyObject var10000;
            PyException var4;
            PyObject var5;
            PyObject var10;
            try {
               var1.setline(1265);
               PyObject var3 = var1.getlocal(0).__getattr__("_events").__getitem__(var1.getlocal(0).__getattr__("_index"));
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(1266);
               var10000 = var1.getlocal(0);
               String var9 = "_index";
               var10 = var10000;
               var5 = var10.__getattr__(var9);
               var5 = var5._iadd(Py.newInteger(1));
               var10.__setattr__(var9, var5);
               var1.setline(1267);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var7) {
               var4 = Py.setException(var7, var1);
               if (!var4.match(var1.getglobal("IndexError"))) {
                  throw var4;
               }
            }

            var1.setline(1269);
            var1.setline(1270);
            if (var1.getlocal(0).__getattr__("_error").__nonzero__()) {
               var1.setline(1271);
               var10 = var1.getlocal(0).__getattr__("_error");
               var1.setlocal(2, var10);
               var4 = null;
               var1.setline(1272);
               var10 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_error", var10);
               var4 = null;
               var1.setline(1273);
               throw Py.makeException(var1.getlocal(2));
            }

            var1.setline(1274);
            var10 = var1.getlocal(0).__getattr__("_parser");
            var10000 = var10._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1275);
               var10 = var1.getlocal(0).__getattr__("_root");
               var1.getlocal(0).__setattr__("root", var10);
               var4 = null;
               break;
            }

            var1.setline(1278);
            var1.getlocal(0).__getattr__("_events").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
            var1.setline(1279);
            PyInteger var11 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_index", var11);
            var4 = null;
            var1.setline(1280);
            var10 = var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(16384));
            var1.setlocal(3, var10);
            var4 = null;
            var1.setline(1281);
            if (var1.getlocal(3).__nonzero__()) {
               try {
                  var1.setline(1283);
                  var1.getlocal(0).__getattr__("_parser").__getattr__("feed").__call__(var2, var1.getlocal(3));
               } catch (Throwable var6) {
                  var4 = Py.setException(var6, var1);
                  if (!var4.match(var1.getglobal("SyntaxError"))) {
                     throw var4;
                  }

                  var5 = var4.value;
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(1285);
                  var5 = var1.getlocal(4);
                  var1.getlocal(0).__setattr__("_error", var5);
                  var5 = null;
               }
            } else {
               var1.setline(1287);
               var10 = var1.getlocal(0).__getattr__("_parser").__getattr__("close").__call__(var2);
               var1.getlocal(0).__setattr__("_root", var10);
               var4 = null;
               var1.setline(1288);
               var10 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_parser", var10);
               var4 = null;
            }
         }
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(1290);
         if (var1.getlocal(0).__getattr__("_close_file").__nonzero__()) {
            var1.setline(1291);
            var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
         }

         var1.setline(1292);
         throw Py.makeException();
      }

      var1.setline(1293);
      if (var1.getlocal(0).__getattr__("_close_file").__nonzero__()) {
         var1.setline(1294);
         var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
      }

      var1.setline(1295);
      throw Py.makeException(var1.getglobal("StopIteration"));
   }

   public PyObject __iter__$85(PyFrame var1, ThreadState var2) {
      var1.setline(1298);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject XML$86(PyFrame var1, ThreadState var2) {
      var1.setline(1311);
      PyObject var5;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1312);
         PyObject var10000 = var1.getglobal("XMLParser");
         PyObject[] var3 = new PyObject[]{var1.getglobal("TreeBuilder").__call__(var2)};
         String[] var4 = new String[]{"target"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var5 = var10000;
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(1313);
      var1.getlocal(1).__getattr__("feed").__call__(var2, var1.getlocal(0));
      var1.setline(1314);
      var5 = var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject XMLID$87(PyFrame var1, ThreadState var2) {
      var1.setline(1327);
      PyObject[] var3;
      PyObject var6;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1328);
         PyObject var10000 = var1.getglobal("XMLParser");
         var3 = new PyObject[]{var1.getglobal("TreeBuilder").__call__(var2)};
         String[] var4 = new String[]{"target"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var6 = var10000;
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(1329);
      var1.getlocal(1).__getattr__("feed").__call__(var2, var1.getlocal(0));
      var1.setline(1330);
      var6 = var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1331);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(1332);
      var6 = var1.getlocal(2).__getattr__("iter").__call__(var2).__iter__();

      while(true) {
         var1.setline(1332);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.setline(1336);
            PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(4, var7);
         var1.setline(1333);
         PyObject var5 = var1.getlocal(4).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("id"));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1334);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(1335);
            var5 = var1.getlocal(4);
            var1.getlocal(3).__setitem__(var1.getlocal(5), var5);
            var5 = null;
         }
      }
   }

   public PyObject fromstringlist$88(PyFrame var1, ThreadState var2) {
      var1.setline(1359);
      PyObject var5;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1360);
         PyObject var10000 = var1.getglobal("XMLParser");
         PyObject[] var3 = new PyObject[]{var1.getglobal("TreeBuilder").__call__(var2)};
         String[] var4 = new String[]{"target"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var5 = var10000;
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(1361);
      var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1361);
         PyObject var6 = var5.__iternext__();
         if (var6 == null) {
            var1.setline(1363);
            var5 = var1.getlocal(1).__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var6);
         var1.setline(1362);
         var1.getlocal(1).__getattr__("feed").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject TreeBuilder$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1380);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$90, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1396);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$91, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1401);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _flush$92, (PyObject)null);
      var1.setlocal("_flush", var4);
      var3 = null;
      var1.setline(1419);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, data$93, (PyObject)null);
      var1.setlocal("data", var4);
      var3 = null;
      var1.setline(1430);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, start$94, (PyObject)null);
      var1.setlocal("start", var4);
      var3 = null;
      var1.setline(1446);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end$95, (PyObject)null);
      var1.setlocal("end", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$90(PyFrame var1, ThreadState var2) {
      var1.setline(1381);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_data", var3);
      var3 = null;
      var1.setline(1382);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_elem", var3);
      var3 = null;
      var1.setline(1383);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_last", var4);
      var3 = null;
      var1.setline(1384);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_tail", var4);
      var3 = null;
      var1.setline(1385);
      var4 = var1.getlocal(1);
      PyObject var10000 = var4._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1386);
         var4 = var1.getglobal("Element");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(1387);
      var4 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_factory", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$91(PyFrame var1, ThreadState var2) {
      var1.setline(1397);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_elem"));
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("missing end tags"));
         }
      }

      var1.setline(1398);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_last");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("missing toplevel element"));
         }
      }

      var1.setline(1399);
      var3 = var1.getlocal(0).__getattr__("_last");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _flush$92(PyFrame var1, ThreadState var2) {
      var1.setline(1402);
      if (var1.getlocal(0).__getattr__("_data").__nonzero__()) {
         var1.setline(1403);
         PyObject var3 = var1.getlocal(0).__getattr__("_last");
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1404);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_data"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1405);
            if (var1.getlocal(0).__getattr__("_tail").__nonzero__()) {
               var1.setline(1406);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("_last").__getattr__("tail");
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("internal error (tail)"));
                  }
               }

               var1.setline(1407);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("_last").__setattr__("tail", var3);
               var3 = null;
            } else {
               var1.setline(1409);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("_last").__getattr__("text");
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("internal error (text)"));
                  }
               }

               var1.setline(1410);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__getattr__("_last").__setattr__("text", var3);
               var3 = null;
            }
         }

         var1.setline(1411);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_data", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject data$93(PyFrame var1, ThreadState var2) {
      var1.setline(1420);
      var1.getlocal(0).__getattr__("_data").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start$94(PyFrame var1, ThreadState var2) {
      var1.setline(1431);
      var1.getlocal(0).__getattr__("_flush").__call__(var2);
      var1.setline(1432);
      PyObject var3 = var1.getlocal(0).__getattr__("_factory").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.getlocal(0).__setattr__("_last", var3);
      var1.setlocal(3, var3);
      var1.setline(1433);
      if (var1.getlocal(0).__getattr__("_elem").__nonzero__()) {
         var1.setline(1434);
         var1.getlocal(0).__getattr__("_elem").__getitem__(Py.newInteger(-1)).__getattr__("append").__call__(var2, var1.getlocal(3));
      }

      var1.setline(1435);
      var1.getlocal(0).__getattr__("_elem").__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(1436);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_tail", var4);
      var3 = null;
      var1.setline(1437);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject end$95(PyFrame var1, ThreadState var2) {
      var1.setline(1447);
      var1.getlocal(0).__getattr__("_flush").__call__(var2);
      var1.setline(1448);
      PyObject var3 = var1.getlocal(0).__getattr__("_elem").__getattr__("pop").__call__(var2);
      var1.getlocal(0).__setattr__("_last", var3);
      var3 = null;
      var1.setline(1449);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_last").__getattr__("tag");
         PyObject var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("end tag mismatch (expected %s, got %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_last").__getattr__("tag"), var1.getlocal(1)})));
         }
      }

      var1.setline(1452);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_tail", var4);
      var3 = null;
      var1.setline(1453);
      var3 = var1.getlocal(0).__getattr__("_last");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject XMLParser$96(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1470);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$97, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1515);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _raiseerror$98, (PyObject)null);
      var1.setlocal("_raiseerror", var4);
      var3 = null;
      var1.setline(1521);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fixtext$99, (PyObject)null);
      var1.setlocal("_fixtext", var4);
      var3 = null;
      var1.setline(1528);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fixname$100, (PyObject)null);
      var1.setlocal("_fixname", var4);
      var3 = null;
      var1.setline(1539);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _start$101, (PyObject)null);
      var1.setlocal("_start", var4);
      var3 = null;
      var1.setline(1548);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _start_list$102, (PyObject)null);
      var1.setlocal("_start_list", var4);
      var3 = null;
      var1.setline(1558);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _data$103, (PyObject)null);
      var1.setlocal("_data", var4);
      var3 = null;
      var1.setline(1561);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _end$104, (PyObject)null);
      var1.setlocal("_end", var4);
      var3 = null;
      var1.setline(1564);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _comment$105, (PyObject)null);
      var1.setlocal("_comment", var4);
      var3 = null;
      var1.setline(1572);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _pi$106, (PyObject)null);
      var1.setlocal("_pi", var4);
      var3 = null;
      var1.setline(1580);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _default$107, (PyObject)null);
      var1.setlocal("_default", var4);
      var3 = null;
      var1.setline(1635);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, doctype$108, PyString.fromInterned("This method of XMLParser is deprecated."));
      var1.setlocal("doctype", var4);
      var3 = null;
      var1.setline(1644);
      PyObject var5 = var1.getname("doctype");
      var1.setlocal("_XMLParser__doctype", var5);
      var3 = null;
      var1.setline(1651);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, feed$109, (PyObject)null);
      var1.setlocal("feed", var4);
      var3 = null;
      var1.setline(1663);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$110, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$97(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyException var4;
      PyObject var13;
      try {
         var1.setline(1472);
         String[] var10 = new String[]{"expat"};
         PyObject[] var11 = imp.importFrom("xml.parsers", var10, var1, -1);
         var13 = var11[0];
         var1.setlocal(4, var13);
         var4 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         try {
            var1.setline(1475);
            var13 = imp.importOneAs("pyexpat", var1, -1);
            var1.setlocal(4, var13);
            var4 = null;
         } catch (Throwable var5) {
            var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("ImportError"))) {
               var1.setline(1477);
               throw Py.makeException(var1.getglobal("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No module named expat; use SimpleXMLTreeBuilder instead")));
            }

            throw var4;
         }
      }

      var1.setline(1480);
      PyObject var12 = var1.getlocal(4).__getattr__("ParserCreate").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("}"));
      var1.setlocal(5, var12);
      var3 = null;
      var1.setline(1481);
      var12 = var1.getlocal(2);
      PyObject var10000 = var12._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1482);
         var12 = var1.getglobal("TreeBuilder").__call__(var2);
         var1.setlocal(2, var12);
         var3 = null;
      }

      var1.setline(1484);
      var12 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("parser", var12);
      var1.getlocal(0).__setattr__("_parser", var12);
      var1.setline(1485);
      var12 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("target", var12);
      var1.getlocal(0).__setattr__("_target", var12);
      var1.setline(1486);
      var12 = var1.getlocal(4).__getattr__("error");
      var1.getlocal(0).__setattr__("_error", var12);
      var3 = null;
      var1.setline(1487);
      PyDictionary var14 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_names", var14);
      var3 = null;
      var1.setline(1489);
      var12 = var1.getlocal(0).__getattr__("_default");
      var1.getlocal(5).__setattr__("DefaultHandlerExpand", var12);
      var3 = null;
      var1.setline(1490);
      var12 = var1.getlocal(0).__getattr__("_start");
      var1.getlocal(5).__setattr__("StartElementHandler", var12);
      var3 = null;
      var1.setline(1491);
      var12 = var1.getlocal(0).__getattr__("_end");
      var1.getlocal(5).__setattr__("EndElementHandler", var12);
      var3 = null;
      var1.setline(1492);
      var12 = var1.getlocal(0).__getattr__("_data");
      var1.getlocal(5).__setattr__("CharacterDataHandler", var12);
      var3 = null;
      var1.setline(1494);
      var12 = var1.getlocal(0).__getattr__("_comment");
      var1.getlocal(5).__setattr__("CommentHandler", var12);
      var3 = null;
      var1.setline(1495);
      var12 = var1.getlocal(0).__getattr__("_pi");
      var1.getlocal(5).__setattr__("ProcessingInstructionHandler", var12);
      var3 = null;

      PyInteger var15;
      try {
         var1.setline(1498);
         var15 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("_parser").__setattr__((String)"buffer_text", var15);
         var3 = null;
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(1500);
      }

      try {
         var1.setline(1503);
         var15 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("_parser").__setattr__((String)"ordered_attributes", var15);
         var3 = null;
         var1.setline(1504);
         var15 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("_parser").__setattr__((String)"specified_attributes", var15);
         var3 = null;
         var1.setline(1505);
         var12 = var1.getlocal(0).__getattr__("_start_list");
         var1.getlocal(5).__setattr__("StartElementHandler", var12);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(1507);
      }

      var1.setline(1508);
      var12 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_doctype", var12);
      var3 = null;
      var1.setline(1509);
      var14 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"entity", var14);
      var3 = null;

      try {
         var1.setline(1511);
         var12 = PyString.fromInterned("Expat %d.%d.%d")._mod(var1.getlocal(4).__getattr__("version_info"));
         var1.getlocal(0).__setattr__("version", var12);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(1513);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _raiseerror$98(PyFrame var1, ThreadState var2) {
      var1.setline(1516);
      PyObject var3 = var1.getglobal("ParseError").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1517);
      var3 = var1.getlocal(1).__getattr__("code");
      var1.getlocal(2).__setattr__("code", var3);
      var3 = null;
      var1.setline(1518);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("lineno"), var1.getlocal(1).__getattr__("offset")});
      var1.getlocal(2).__setattr__((String)"position", var4);
      var3 = null;
      var1.setline(1519);
      throw Py.makeException(var1.getlocal(2));
   }

   public PyObject _fixtext$99(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(1524);
         var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("UnicodeError"))) {
            var1.setline(1526);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _fixname$100(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(1531);
         var6 = var1.getlocal(0).__getattr__("_names").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(1533);
         PyObject var4 = var1.getlocal(1);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1534);
         PyString var7 = PyString.fromInterned("}");
         PyObject var10000 = var7._in(var1.getlocal(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1535);
            var4 = PyString.fromInterned("{")._add(var1.getlocal(2));
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1536);
         var4 = var1.getlocal(0).__getattr__("_fixtext").__call__(var2, var1.getlocal(2));
         var1.getlocal(0).__getattr__("_names").__setitem__(var1.getlocal(1), var4);
         var1.setlocal(2, var4);
      }

      var1.setline(1537);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _start$101(PyFrame var1, ThreadState var2) {
      var1.setline(1540);
      PyObject var3 = var1.getlocal(0).__getattr__("_fixname");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1541);
      var3 = var1.getlocal(0).__getattr__("_fixtext");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1542);
      var3 = var1.getlocal(3).__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1543);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(1544);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1544);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1546);
            var3 = var1.getlocal(0).__getattr__("target").__getattr__("start").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(1545);
         PyObject var8 = var1.getlocal(4).__call__(var2, var1.getlocal(7));
         var1.getlocal(5).__setitem__(var1.getlocal(3).__call__(var2, var1.getlocal(6)), var8);
         var5 = null;
      }
   }

   public PyObject _start_list$102(PyFrame var1, ThreadState var2) {
      var1.setline(1549);
      PyObject var3 = var1.getlocal(0).__getattr__("_fixname");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1550);
      var3 = var1.getlocal(0).__getattr__("_fixtext");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1551);
      var3 = var1.getlocal(3).__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1552);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1553);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1554);
         var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(1554);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(6, var4);
            var1.setline(1555);
            PyObject var5 = var1.getlocal(4).__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6)._add(Py.newInteger(1))));
            var1.getlocal(5).__setitem__(var1.getlocal(3).__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6))), var5);
            var5 = null;
         }
      }

      var1.setline(1556);
      var3 = var1.getlocal(0).__getattr__("target").__getattr__("start").__call__(var2, var1.getlocal(1), var1.getlocal(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _data$103(PyFrame var1, ThreadState var2) {
      var1.setline(1559);
      PyObject var3 = var1.getlocal(0).__getattr__("target").__getattr__("data").__call__(var2, var1.getlocal(0).__getattr__("_fixtext").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _end$104(PyFrame var1, ThreadState var2) {
      var1.setline(1562);
      PyObject var3 = var1.getlocal(0).__getattr__("target").__getattr__("end").__call__(var2, var1.getlocal(0).__getattr__("_fixname").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _comment$105(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(1566);
         PyObject var6 = var1.getlocal(0).__getattr__("target").__getattr__("comment");
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(1568);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(1570);
      PyObject var4 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("_fixtext").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _pi$106(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(1574);
         PyObject var6 = var1.getlocal(0).__getattr__("target").__getattr__("pi");
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(1576);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(1578);
      PyObject var4 = var1.getlocal(3).__call__(var2, var1.getlocal(0).__getattr__("_fixtext").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("_fixtext").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _default$107(PyFrame var1, ThreadState var2) {
      var1.setline(1581);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1582);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("&"));
      var3 = null;
      PyObject var5;
      PyObject[] var8;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1585);
            var1.getlocal(0).__getattr__("target").__getattr__("data").__call__(var2, var1.getlocal(0).__getattr__("entity").__getitem__(var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null)));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               var1.setline(1587);
               String[] var4 = new String[]{"expat"};
               var8 = imp.importFrom("xml.parsers", var4, var1, -1);
               var5 = var8[0];
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(1588);
               PyObject var9 = var1.getlocal(3).__getattr__("error").__call__(var2, PyString.fromInterned("undefined entity %s: line %d, column %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("_parser").__getattr__("ErrorLineNumber"), var1.getlocal(0).__getattr__("_parser").__getattr__("ErrorColumnNumber")})));
               var1.setlocal(4, var9);
               var4 = null;
               var1.setline(1593);
               PyInteger var10 = Py.newInteger(11);
               var1.getlocal(4).__setattr__((String)"code", var10);
               var4 = null;
               var1.setline(1594);
               var9 = var1.getlocal(0).__getattr__("_parser").__getattr__("ErrorLineNumber");
               var1.getlocal(4).__setattr__("lineno", var9);
               var4 = null;
               var1.setline(1595);
               var9 = var1.getlocal(0).__getattr__("_parser").__getattr__("ErrorColumnNumber");
               var1.getlocal(4).__setattr__("offset", var9);
               var4 = null;
               var1.setline(1596);
               throw Py.makeException(var1.getlocal(4));
            }

            throw var7;
         }
      } else {
         var1.setline(1597);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("<"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("<!DOCTYPE"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1598);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"_doctype", var11);
            var3 = null;
         } else {
            var1.setline(1599);
            var3 = var1.getlocal(0).__getattr__("_doctype");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1601);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(PyString.fromInterned(">"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1602);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("_doctype", var3);
                  var3 = null;
                  var1.setline(1603);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(1604);
               var3 = var1.getlocal(1).__getattr__("strip").__call__(var2);
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(1605);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1606);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(1607);
               var1.getlocal(0).__getattr__("_doctype").__getattr__("append").__call__(var2, var1.getlocal(1));
               var1.setline(1608);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_doctype"));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(1609);
               var3 = var1.getlocal(5);
               var10000 = var3._gt(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1610);
                  var3 = var1.getlocal(0).__getattr__("_doctype").__getitem__(Py.newInteger(1));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(1611);
                  var3 = var1.getlocal(6);
                  var10000 = var3._eq(PyString.fromInterned("PUBLIC"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(5);
                     var10000 = var3._eq(Py.newInteger(4));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1612);
                     var3 = var1.getlocal(0).__getattr__("_doctype");
                     var8 = Py.unpackSequence(var3, 4);
                     var5 = var8[0];
                     var1.setlocal(7, var5);
                     var5 = null;
                     var5 = var8[1];
                     var1.setlocal(6, var5);
                     var5 = null;
                     var5 = var8[2];
                     var1.setlocal(8, var5);
                     var5 = null;
                     var5 = var8[3];
                     var1.setlocal(9, var5);
                     var5 = null;
                     var3 = null;
                  } else {
                     var1.setline(1613);
                     var3 = var1.getlocal(6);
                     var10000 = var3._eq(PyString.fromInterned("SYSTEM"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(5);
                        var10000 = var3._eq(Py.newInteger(3));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(1617);
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setline(1614);
                     var3 = var1.getlocal(0).__getattr__("_doctype");
                     var8 = Py.unpackSequence(var3, 3);
                     var5 = var8[0];
                     var1.setlocal(7, var5);
                     var5 = null;
                     var5 = var8[1];
                     var1.setlocal(6, var5);
                     var5 = null;
                     var5 = var8[2];
                     var1.setlocal(9, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(1615);
                     var3 = var1.getglobal("None");
                     var1.setlocal(8, var3);
                     var3 = null;
                  }

                  var1.setline(1618);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1619);
                     var3 = var1.getlocal(8).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(8, var3);
                     var3 = null;
                  }

                  var1.setline(1620);
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("target"), (PyObject)PyString.fromInterned("doctype")).__nonzero__()) {
                     var1.setline(1621);
                     var1.getlocal(0).__getattr__("target").__getattr__("doctype").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null));
                  } else {
                     var1.setline(1622);
                     var3 = var1.getlocal(0).__getattr__("doctype");
                     var10000 = var3._isnot(var1.getlocal(0).__getattr__("_XMLParser__doctype"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1624);
                        var1.getlocal(0).__getattr__("_XMLParser__doctype").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null));
                        var1.setline(1625);
                        var1.getlocal(0).__getattr__("doctype").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null));
                     }
                  }

                  var1.setline(1626);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("_doctype", var3);
                  var3 = null;
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject doctype$108(PyFrame var1, ThreadState var2) {
      var1.setline(1636);
      PyString.fromInterned("This method of XMLParser is deprecated.");
      var1.setline(1637);
      var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("This method of XMLParser is deprecated.  Define doctype() method on the TreeBuilder target."), (PyObject)var1.getglobal("DeprecationWarning"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$109(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(1653);
         var1.getlocal(0).__getattr__("_parser").__getattr__("Parse").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getlocal(0).__getattr__("_error"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1655);
         var1.getlocal(0).__getattr__("_raiseerror").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$110(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(1665);
         var1.getlocal(0).__getattr__("_parser").__getattr__("Parse").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(1));
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getlocal(0).__getattr__("_error"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1667);
         var1.getlocal(0).__getattr__("_raiseerror").__call__(var2, var1.getlocal(1));
      }

      var1.setline(1668);
      PyObject var6 = var1.getlocal(0).__getattr__("target").__getattr__("close").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1669);
      var1.getlocal(0).__delattr__("target");
      var1.getlocal(0).__delattr__("_parser");
      var1.setline(1670);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public ElementTree$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _SimpleElementPath$1 = Py.newCode(0, var2, var1, "_SimpleElementPath", 104, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "element", "tag", "namespaces", "elem"};
      find$2 = Py.newCode(4, var2, var1, "find", 106, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "tag", "default", "namespaces", "elem"};
      findtext$3 = Py.newCode(5, var2, var1, "findtext", 111, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "tag", "namespaces", "elem"};
      iterfind$4 = Py.newCode(4, var2, var1, "iterfind", 116, false, false, self, 4, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "element", "tag", "namespaces"};
      findall$5 = Py.newCode(4, var2, var1, "findall", 123, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ParseError$6 = Py.newCode(0, var2, var1, "ParseError", 138, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"element"};
      iselement$7 = Py.newCode(1, var2, var1, "iselement", 150, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Element$8 = Py.newCode(0, var2, var1, "Element", 171, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tag", "attrib", "extra"};
      __init__$9 = Py.newCode(4, var2, var1, "__init__", 207, false, true, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$10 = Py.newCode(1, var2, var1, "__repr__", 214, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrib"};
      makeelement$11 = Py.newCode(3, var2, var1, "makeelement", 224, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem"};
      copy$12 = Py.newCode(1, var2, var1, "copy", 233, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$13 = Py.newCode(1, var2, var1, "__len__", 247, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __nonzero__$14 = Py.newCode(1, var2, var1, "__nonzero__", 250, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$15 = Py.newCode(2, var2, var1, "__getitem__", 265, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "element"};
      __setitem__$16 = Py.newCode(3, var2, var1, "__setitem__", 275, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __delitem__$17 = Py.newCode(2, var2, var1, "__delitem__", 289, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      append$18 = Py.newCode(2, var2, var1, "append", 300, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elements"};
      extend$19 = Py.newCode(2, var2, var1, "extend", 310, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index", "element"};
      insert$20 = Py.newCode(3, var2, var1, "insert", 320, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      remove$21 = Py.newCode(2, var2, var1, "remove", 335, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getchildren$22 = Py.newCode(1, var2, var1, "getchildren", 346, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "namespaces"};
      find$23 = Py.newCode(3, var2, var1, "find", 362, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "default", "namespaces"};
      findtext$24 = Py.newCode(4, var2, var1, "findtext", 377, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "namespaces"};
      findall$25 = Py.newCode(3, var2, var1, "findall", 389, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "namespaces"};
      iterfind$26 = Py.newCode(3, var2, var1, "iterfind", 401, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$27 = Py.newCode(1, var2, var1, "clear", 409, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      get$28 = Py.newCode(3, var2, var1, "get", 424, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      set$29 = Py.newCode(3, var2, var1, "set", 434, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$30 = Py.newCode(1, var2, var1, "keys", 445, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$31 = Py.newCode(1, var2, var1, "items", 455, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "e"};
      iter$32 = Py.newCode(2, var2, var1, "iter", 471, false, false, self, 32, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "tag"};
      getiterator$33 = Py.newCode(2, var2, var1, "getiterator", 481, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "e", "s"};
      itertext$34 = Py.newCode(1, var2, var1, "itertext", 498, false, false, self, 34, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"parent", "tag", "attrib", "extra", "element"};
      SubElement$35 = Py.newCode(4, var2, var1, "SubElement", 527, false, true, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "element"};
      Comment$36 = Py.newCode(1, var2, var1, "Comment", 546, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"target", "text", "element"};
      ProcessingInstruction$37 = Py.newCode(2, var2, var1, "ProcessingInstruction", 561, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      QName$38 = Py.newCode(0, var2, var1, "QName", 580, false, false, self, 38, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "text_or_uri", "tag"};
      __init__$39 = Py.newCode(3, var2, var1, "__init__", 581, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$40 = Py.newCode(1, var2, var1, "__str__", 585, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$41 = Py.newCode(1, var2, var1, "__hash__", 587, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$42 = Py.newCode(2, var2, var1, "__cmp__", 589, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ElementTree$43 = Py.newCode(0, var2, var1, "ElementTree", 605, false, false, self, 43, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "element", "file"};
      __init__$44 = Py.newCode(3, var2, var1, "__init__", 607, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getroot$45 = Py.newCode(1, var2, var1, "getroot", 619, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      _setroot$46 = Py.newCode(2, var2, var1, "_setroot", 629, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "parser", "close_source", "data"};
      parse$47 = Py.newCode(3, var2, var1, "parse", 644, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      iter$48 = Py.newCode(2, var2, var1, "iter", 671, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      getiterator$49 = Py.newCode(2, var2, var1, "getiterator", 676, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "namespaces"};
      find$50 = Py.newCode(3, var2, var1, "find", 694, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "default", "namespaces"};
      findtext$51 = Py.newCode(4, var2, var1, "findtext", 718, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "namespaces"};
      findall$52 = Py.newCode(3, var2, var1, "findall", 739, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "namespaces"};
      iterfind$53 = Py.newCode(3, var2, var1, "iterfind", 761, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_or_filename", "encoding", "xml_declaration", "default_namespace", "method", "file", "write", "qnames", "namespaces", "serialize"};
      write$54 = Py.newCode(6, var2, var1, "write", 787, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      write_c14n$55 = Py.newCode(2, var2, var1, "write_c14n", 826, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"elem", "encoding", "default_namespace", "add_qname", "iterate", "tag", "key", "value", "text", "namespaces", "qnames", "encode"};
      String[] var10001 = var2;
      ElementTree$py var10007 = self;
      var2 = new String[]{"namespaces", "encoding", "qnames", "encode", "default_namespace"};
      _namespaces$56 = Py.newCode(3, var10001, var1, "_namespaces", 833, false, false, var10007, 56, var2, (String[])null, 3, 4097);
      var2 = new String[]{"text"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"encoding"};
      encode$57 = Py.newCode(1, var10001, var1, "encode", 844, false, false, var10007, 57, (String[])null, var2, 0, 4097);
      var2 = new String[]{"qname", "uri", "tag", "prefix"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"namespaces", "qnames", "encode", "default_namespace"};
      add_qname$58 = Py.newCode(1, var10001, var1, "add_qname", 847, false, false, var10007, 58, (String[])null, var2, 0, 4097);
      var2 = new String[]{"write", "elem", "encoding", "qnames", "namespaces", "tag", "text", "e", "items", "v", "k"};
      _serialize_xml$59 = Py.newCode(5, var2, var1, "_serialize_xml", 901, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$60 = Py.newCode(1, var2, var1, "<lambda>", 921, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"write", "elem", "encoding", "qnames", "namespaces", "tag", "text", "e", "items", "v", "k", "ltag"};
      _serialize_html$61 = Py.newCode(5, var2, var1, "_serialize_html", 956, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$62 = Py.newCode(1, var2, var1, "<lambda>", 976, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"write", "elem", "encoding", "part"};
      _serialize_text$63 = Py.newCode(3, var2, var1, "_serialize_text", 1006, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "uri", "k", "v"};
      register_namespace$64 = Py.newCode(2, var2, var1, "register_namespace", 1031, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      _raise_serialization_error$65 = Py.newCode(1, var2, var1, "_raise_serialization_error", 1052, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "encoding"};
      _encode$66 = Py.newCode(2, var2, var1, "_encode", 1057, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "encoding"};
      _escape_cdata$67 = Py.newCode(2, var2, var1, "_escape_cdata", 1063, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "encoding"};
      _escape_attrib$68 = Py.newCode(2, var2, var1, "_escape_attrib", 1079, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "encoding"};
      _escape_attrib_html$69 = Py.newCode(2, var2, var1, "_escape_attrib_html", 1096, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"element", "encoding", "method", "dummy", "data", "file"};
      tostring$70 = Py.newCode(3, var2, var1, "tostring", 1122, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dummy$71 = Py.newCode(0, var2, var1, "dummy", 1123, false, false, self, 71, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"element", "encoding", "method", "dummy", "data", "file"};
      tostringlist$72 = Py.newCode(3, var2, var1, "tostringlist", 1143, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dummy$73 = Py.newCode(0, var2, var1, "dummy", 1144, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"elem", "tail"};
      dump$74 = Py.newCode(1, var2, var1, "dump", 1162, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "parser", "tree"};
      parse$75 = Py.newCode(2, var2, var1, "parse", 1182, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "events", "parser", "close_source"};
      iterparse$76 = Py.newCode(3, var2, var1, "iterparse", 1198, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _IterParseIterator$77 = Py.newCode(0, var2, var1, "_IterParseIterator", 1212, false, false, self, 77, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "events", "parser", "close_source", "append", "event", "handler"};
      __init__$78 = Py.newCode(5, var2, var1, "__init__", 1214, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tag", "attrib_in", "event", "append", "start"};
      handler$79 = Py.newCode(5, var2, var1, "handler", 1232, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tag", "attrib_in", "event", "append", "start"};
      handler$80 = Py.newCode(5, var2, var1, "handler", 1237, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tag", "event", "append", "end"};
      handler$81 = Py.newCode(4, var2, var1, "handler", 1242, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "uri", "event", "append"};
      handler$82 = Py.newCode(4, var2, var1, "handler", 1247, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "event", "append"};
      handler$83 = Py.newCode(3, var2, var1, "handler", 1255, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item", "e", "data", "exc"};
      next$84 = Py.newCode(1, var2, var1, "next", 1261, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$85 = Py.newCode(1, var2, var1, "__iter__", 1297, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "parser"};
      XML$86 = Py.newCode(2, var2, var1, "XML", 1310, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "parser", "tree", "ids", "elem", "id"};
      XMLID$87 = Py.newCode(2, var2, var1, "XMLID", 1326, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sequence", "parser", "text"};
      fromstringlist$88 = Py.newCode(2, var2, var1, "fromstringlist", 1358, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TreeBuilder$89 = Py.newCode(0, var2, var1, "TreeBuilder", 1378, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "element_factory"};
      __init__$90 = Py.newCode(2, var2, var1, "__init__", 1380, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$91 = Py.newCode(1, var2, var1, "close", 1396, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      _flush$92 = Py.newCode(1, var2, var1, "_flush", 1401, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      data$93 = Py.newCode(2, var2, var1, "data", 1419, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs", "elem"};
      start$94 = Py.newCode(3, var2, var1, "start", 1430, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      end$95 = Py.newCode(2, var2, var1, "end", 1446, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      XMLParser$96 = Py.newCode(0, var2, var1, "XMLParser", 1468, false, false, self, 96, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "html", "target", "encoding", "expat", "parser"};
      __init__$97 = Py.newCode(4, var2, var1, "__init__", 1470, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "err"};
      _raiseerror$98 = Py.newCode(2, var2, var1, "_raiseerror", 1515, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      _fixtext$99 = Py.newCode(2, var2, var1, "_fixtext", 1521, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "name"};
      _fixname$100 = Py.newCode(2, var2, var1, "_fixname", 1528, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrib_in", "fixname", "fixtext", "attrib", "key", "value"};
      _start$101 = Py.newCode(3, var2, var1, "_start", 1539, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrib_in", "fixname", "fixtext", "attrib", "i"};
      _start_list$102 = Py.newCode(3, var2, var1, "_start_list", 1548, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      _data$103 = Py.newCode(2, var2, var1, "_data", 1558, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      _end$104 = Py.newCode(2, var2, var1, "_end", 1561, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "comment"};
      _comment$105 = Py.newCode(2, var2, var1, "_comment", 1564, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data", "pi"};
      _pi$106 = Py.newCode(3, var2, var1, "_pi", 1572, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "prefix", "expat", "err", "n", "type", "name", "pubid", "system"};
      _default$107 = Py.newCode(2, var2, var1, "_default", 1580, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "pubid", "system"};
      doctype$108 = Py.newCode(4, var2, var1, "doctype", 1635, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "v"};
      feed$109 = Py.newCode(2, var2, var1, "feed", 1651, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "v", "tree"};
      close$110 = Py.newCode(1, var2, var1, "close", 1663, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ElementTree$py("xml/etree/ElementTree$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ElementTree$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._SimpleElementPath$1(var2, var3);
         case 2:
            return this.find$2(var2, var3);
         case 3:
            return this.findtext$3(var2, var3);
         case 4:
            return this.iterfind$4(var2, var3);
         case 5:
            return this.findall$5(var2, var3);
         case 6:
            return this.ParseError$6(var2, var3);
         case 7:
            return this.iselement$7(var2, var3);
         case 8:
            return this.Element$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.__repr__$10(var2, var3);
         case 11:
            return this.makeelement$11(var2, var3);
         case 12:
            return this.copy$12(var2, var3);
         case 13:
            return this.__len__$13(var2, var3);
         case 14:
            return this.__nonzero__$14(var2, var3);
         case 15:
            return this.__getitem__$15(var2, var3);
         case 16:
            return this.__setitem__$16(var2, var3);
         case 17:
            return this.__delitem__$17(var2, var3);
         case 18:
            return this.append$18(var2, var3);
         case 19:
            return this.extend$19(var2, var3);
         case 20:
            return this.insert$20(var2, var3);
         case 21:
            return this.remove$21(var2, var3);
         case 22:
            return this.getchildren$22(var2, var3);
         case 23:
            return this.find$23(var2, var3);
         case 24:
            return this.findtext$24(var2, var3);
         case 25:
            return this.findall$25(var2, var3);
         case 26:
            return this.iterfind$26(var2, var3);
         case 27:
            return this.clear$27(var2, var3);
         case 28:
            return this.get$28(var2, var3);
         case 29:
            return this.set$29(var2, var3);
         case 30:
            return this.keys$30(var2, var3);
         case 31:
            return this.items$31(var2, var3);
         case 32:
            return this.iter$32(var2, var3);
         case 33:
            return this.getiterator$33(var2, var3);
         case 34:
            return this.itertext$34(var2, var3);
         case 35:
            return this.SubElement$35(var2, var3);
         case 36:
            return this.Comment$36(var2, var3);
         case 37:
            return this.ProcessingInstruction$37(var2, var3);
         case 38:
            return this.QName$38(var2, var3);
         case 39:
            return this.__init__$39(var2, var3);
         case 40:
            return this.__str__$40(var2, var3);
         case 41:
            return this.__hash__$41(var2, var3);
         case 42:
            return this.__cmp__$42(var2, var3);
         case 43:
            return this.ElementTree$43(var2, var3);
         case 44:
            return this.__init__$44(var2, var3);
         case 45:
            return this.getroot$45(var2, var3);
         case 46:
            return this._setroot$46(var2, var3);
         case 47:
            return this.parse$47(var2, var3);
         case 48:
            return this.iter$48(var2, var3);
         case 49:
            return this.getiterator$49(var2, var3);
         case 50:
            return this.find$50(var2, var3);
         case 51:
            return this.findtext$51(var2, var3);
         case 52:
            return this.findall$52(var2, var3);
         case 53:
            return this.iterfind$53(var2, var3);
         case 54:
            return this.write$54(var2, var3);
         case 55:
            return this.write_c14n$55(var2, var3);
         case 56:
            return this._namespaces$56(var2, var3);
         case 57:
            return this.encode$57(var2, var3);
         case 58:
            return this.add_qname$58(var2, var3);
         case 59:
            return this._serialize_xml$59(var2, var3);
         case 60:
            return this.f$60(var2, var3);
         case 61:
            return this._serialize_html$61(var2, var3);
         case 62:
            return this.f$62(var2, var3);
         case 63:
            return this._serialize_text$63(var2, var3);
         case 64:
            return this.register_namespace$64(var2, var3);
         case 65:
            return this._raise_serialization_error$65(var2, var3);
         case 66:
            return this._encode$66(var2, var3);
         case 67:
            return this._escape_cdata$67(var2, var3);
         case 68:
            return this._escape_attrib$68(var2, var3);
         case 69:
            return this._escape_attrib_html$69(var2, var3);
         case 70:
            return this.tostring$70(var2, var3);
         case 71:
            return this.dummy$71(var2, var3);
         case 72:
            return this.tostringlist$72(var2, var3);
         case 73:
            return this.dummy$73(var2, var3);
         case 74:
            return this.dump$74(var2, var3);
         case 75:
            return this.parse$75(var2, var3);
         case 76:
            return this.iterparse$76(var2, var3);
         case 77:
            return this._IterParseIterator$77(var2, var3);
         case 78:
            return this.__init__$78(var2, var3);
         case 79:
            return this.handler$79(var2, var3);
         case 80:
            return this.handler$80(var2, var3);
         case 81:
            return this.handler$81(var2, var3);
         case 82:
            return this.handler$82(var2, var3);
         case 83:
            return this.handler$83(var2, var3);
         case 84:
            return this.next$84(var2, var3);
         case 85:
            return this.__iter__$85(var2, var3);
         case 86:
            return this.XML$86(var2, var3);
         case 87:
            return this.XMLID$87(var2, var3);
         case 88:
            return this.fromstringlist$88(var2, var3);
         case 89:
            return this.TreeBuilder$89(var2, var3);
         case 90:
            return this.__init__$90(var2, var3);
         case 91:
            return this.close$91(var2, var3);
         case 92:
            return this._flush$92(var2, var3);
         case 93:
            return this.data$93(var2, var3);
         case 94:
            return this.start$94(var2, var3);
         case 95:
            return this.end$95(var2, var3);
         case 96:
            return this.XMLParser$96(var2, var3);
         case 97:
            return this.__init__$97(var2, var3);
         case 98:
            return this._raiseerror$98(var2, var3);
         case 99:
            return this._fixtext$99(var2, var3);
         case 100:
            return this._fixname$100(var2, var3);
         case 101:
            return this._start$101(var2, var3);
         case 102:
            return this._start_list$102(var2, var3);
         case 103:
            return this._data$103(var2, var3);
         case 104:
            return this._end$104(var2, var3);
         case 105:
            return this._comment$105(var2, var3);
         case 106:
            return this._pi$106(var2, var3);
         case 107:
            return this._default$107(var2, var3);
         case 108:
            return this.doctype$108(var2, var3);
         case 109:
            return this.feed$109(var2, var3);
         case 110:
            return this.close$110(var2, var3);
         default:
            return null;
      }
   }
}
