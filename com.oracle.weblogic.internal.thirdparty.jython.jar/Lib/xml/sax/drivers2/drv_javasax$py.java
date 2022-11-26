package xml.sax.drivers2;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("xml/sax/drivers2/drv_javasax.py")
public class drv_javasax$py extends PyFunctionTable implements PyRunnable {
   static drv_javasax$py self;
   static final PyCode f$0;
   static final PyCode SAXUnicodeDecodeError$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode _wrap_sax_exception$4;
   static final PyCode JyErrorHandlerWrapper$5;
   static final PyCode __init__$6;
   static final PyCode error$7;
   static final PyCode fatalError$8;
   static final PyCode warning$9;
   static final PyCode JyInputSourceWrapper$10;
   static final PyCode __init__$11;
   static final PyCode JyEntityResolverWrapper$12;
   static final PyCode __init__$13;
   static final PyCode resolveEntity$14;
   static final PyCode JyDTDHandlerWrapper$15;
   static final PyCode __init__$16;
   static final PyCode notationDecl$17;
   static final PyCode unparsedEntityDecl$18;
   static final PyCode SimpleLocator$19;
   static final PyCode __init__$20;
   static final PyCode getColumnNumber$21;
   static final PyCode getLineNumber$22;
   static final PyCode getPublicId$23;
   static final PyCode getSystemId$24;
   static final PyCode JavaSAXParser$25;
   static final PyCode __init__$26;
   static final PyCode parse$27;
   static final PyCode getFeature$28;
   static final PyCode setFeature$29;
   static final PyCode getProperty$30;
   static final PyCode setProperty$31;
   static final PyCode setEntityResolver$32;
   static final PyCode setErrorHandler$33;
   static final PyCode setDTDHandler$34;
   static final PyCode setDocumentLocator$35;
   static final PyCode startDocument$36;
   static final PyCode startElement$37;
   static final PyCode startPrefixMapping$38;
   static final PyCode characters$39;
   static final PyCode ignorableWhitespace$40;
   static final PyCode endElement$41;
   static final PyCode endPrefixMapping$42;
   static final PyCode endDocument$43;
   static final PyCode processingInstruction$44;
   static final PyCode comment$45;
   static final PyCode startCDATA$46;
   static final PyCode endCDATA$47;
   static final PyCode startDTD$48;
   static final PyCode endDTD$49;
   static final PyCode startEntity$50;
   static final PyCode endEntity$51;
   static final PyCode skippedEntity$52;
   static final PyCode _fixTuple$53;
   static final PyCode _makeJavaNsTuple$54;
   static final PyCode _makePythonNsTuple$55;
   static final PyCode AttributesImpl$56;
   static final PyCode __init__$57;
   static final PyCode getLength$58;
   static final PyCode getType$59;
   static final PyCode getValue$60;
   static final PyCode getNames$61;
   static final PyCode getQNames$62;
   static final PyCode getValueByQName$63;
   static final PyCode getNameByQName$64;
   static final PyCode getQNameByName$65;
   static final PyCode __len__$66;
   static final PyCode __getitem__$67;
   static final PyCode keys$68;
   static final PyCode copy$69;
   static final PyCode items$70;
   static final PyCode values$71;
   static final PyCode get$72;
   static final PyCode has_key$73;
   static final PyCode AttributesNSImpl$74;
   static final PyCode __init__$75;
   static final PyCode getType$76;
   static final PyCode getValue$77;
   static final PyCode getNames$78;
   static final PyCode getNameByQName$79;
   static final PyCode getQNameByName$80;
   static final PyCode getQNames$81;
   static final PyCode create_java_parser$82;
   static final PyCode create_parser$83;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nSAX driver for the Java SAX parsers. Can only be used in Jython.\n\n$Id: drv_javasax.py,v 1.5 2003/01/26 09:08:51 loewis Exp $\n"));
      var1.setline(5);
      PyString.fromInterned("\nSAX driver for the Java SAX parsers. Can only be used in Jython.\n\n$Id: drv_javasax.py,v 1.5 2003/01/26 09:08:51 loewis Exp $\n");
      var1.setline(9);
      PyString var3 = PyString.fromInterned("0.10");
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(10);
      var3 = PyString.fromInterned("$Revision: 1.5 $");
      var1.setlocal("revision", var3);
      var3 = null;
      var1.setline(12);
      PyObject var7 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var7);
      var3 = null;
      var1.setline(13);
      String[] var8 = new String[]{"xmlreader", "saxutils"};
      PyObject[] var9 = imp.importFrom("xml.sax", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("xmlreader", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("saxutils", var4);
      var4 = null;
      var1.setline(14);
      var8 = new String[]{"feature_namespaces", "feature_namespace_prefixes"};
      var9 = imp.importFrom("xml.sax.handler", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("feature_namespaces", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("feature_namespace_prefixes", var4);
      var4 = null;
      var1.setline(15);
      var8 = new String[]{"_exceptions"};
      var9 = imp.importFrom("xml.sax", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_exceptions", var4);
      var4 = null;
      var1.setline(18);
      var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var1.setline(19);
      var7 = var1.getname("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var7._ne(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         throw Py.makeException(var1.getname("_exceptions").__getattr__("SAXReaderNotAvailable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("drv_javasax not available in CPython"), (PyObject)var1.getname("None")));
      } else {
         var1.setline(21);
         var1.dellocal("sys");

         PyException var10;
         try {
            var1.setline(25);
            var8 = new String[]{"FilelikeInputStream"};
            var9 = imp.importFrom("org.python.core", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("FilelikeInputStream", var4);
            var4 = null;
            var1.setline(26);
            var8 = new String[]{"XMLReaderFactory"};
            var9 = imp.importFrom("org.xml.sax.helpers", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("XMLReaderFactory", var4);
            var4 = null;
            var1.setline(27);
            var8 = new String[]{"sax"};
            var9 = imp.importFrom("org.xml", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("javasax", var4);
            var4 = null;
            var1.setline(28);
            var8 = new String[]{"LexicalHandler"};
            var9 = imp.importFrom("org.xml.sax.ext", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("LexicalHandler", var4);
            var4 = null;
         } catch (Throwable var5) {
            var10 = Py.setException(var5, var1);
            if (var10.match(var1.getname("ImportError"))) {
               var1.setline(30);
               throw Py.makeException(var1.getname("_exceptions").__getattr__("SAXReaderNotAvailable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SAX is not on the classpath"), (PyObject)var1.getname("None")));
            }

            throw var10;
         }

         try {
            var1.setline(34);
            var8 = new String[]{"SAXParserFactory", "ParserConfigurationException"};
            var9 = imp.importFrom("javax.xml.parsers", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("SAXParserFactory", var4);
            var4 = null;
            var4 = var9[1];
            var1.setlocal("ParserConfigurationException", var4);
            var4 = null;
            var1.setline(35);
            var7 = var1.getname("SAXParserFactory").__getattr__("newInstance").__call__(var2);
            var1.setlocal("factory", var7);
            var3 = null;
            var1.setline(43);
            var1.getname("factory").__getattr__("setFeature").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://apache.org/xml/features/nonvalidating/load-external-dtd"), (PyObject)var1.getname("False"));
            var1.setline(44);
            PyInteger var12 = Py.newInteger(1);
            var1.setlocal("jaxp", var12);
            var3 = null;
         } catch (Throwable var6) {
            var10 = Py.setException(var6, var1);
            if (!var10.match(var1.getname("ImportError"))) {
               throw var10;
            }

            var1.setline(46);
            PyInteger var11 = Py.newInteger(0);
            var1.setlocal("jaxp", var11);
            var4 = null;
         }

         var1.setline(48);
         var8 = new String[]{"String", "Exception"};
         var9 = imp.importFrom("java.lang", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("String", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("JException", var4);
         var4 = null;
         var1.setline(50);
         var9 = new PyObject[]{var1.getname("UnicodeDecodeError")};
         var4 = Py.makeClass("SAXUnicodeDecodeError", var9, SAXUnicodeDecodeError$1);
         var1.setlocal("SAXUnicodeDecodeError", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(58);
         var9 = Py.EmptyObjects;
         PyFunction var13 = new PyFunction(var1.f_globals, var9, _wrap_sax_exception$4, (PyObject)null);
         var1.setlocal("_wrap_sax_exception", var13);
         var3 = null;
         var1.setline(75);
         var9 = new PyObject[]{var1.getname("javasax").__getattr__("ErrorHandler")};
         var4 = Py.makeClass("JyErrorHandlerWrapper", var9, JyErrorHandlerWrapper$5);
         var1.setlocal("JyErrorHandlerWrapper", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(88);
         var9 = new PyObject[]{var1.getname("javasax").__getattr__("InputSource")};
         var4 = Py.makeClass("JyInputSourceWrapper", var9, JyInputSourceWrapper$10);
         var1.setlocal("JyInputSourceWrapper", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(110);
         var9 = new PyObject[]{var1.getname("javasax").__getattr__("EntityResolver")};
         var4 = Py.makeClass("JyEntityResolverWrapper", var9, JyEntityResolverWrapper$12);
         var1.setlocal("JyEntityResolverWrapper", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(117);
         var9 = new PyObject[]{var1.getname("javasax").__getattr__("DTDHandler")};
         var4 = Py.makeClass("JyDTDHandlerWrapper", var9, JyDTDHandlerWrapper$15);
         var1.setlocal("JyDTDHandlerWrapper", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(127);
         var9 = new PyObject[]{var1.getname("xmlreader").__getattr__("Locator")};
         var4 = Py.makeClass("SimpleLocator", var9, SimpleLocator$19);
         var1.setlocal("SimpleLocator", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(147);
         var9 = new PyObject[]{var1.getname("xmlreader").__getattr__("XMLReader"), var1.getname("javasax").__getattr__("ContentHandler"), var1.getname("LexicalHandler")};
         var4 = Py.makeClass("JavaSAXParser", var9, JavaSAXParser$25);
         var1.setlocal("JavaSAXParser", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(275);
         var9 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var9, _fixTuple$53, (PyObject)null);
         var1.setlocal("_fixTuple", var13);
         var3 = null;
         var1.setline(283);
         var9 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var9, _makeJavaNsTuple$54, (PyObject)null);
         var1.setlocal("_makeJavaNsTuple", var13);
         var3 = null;
         var1.setline(286);
         var9 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var9, _makePythonNsTuple$55, (PyObject)null);
         var1.setlocal("_makePythonNsTuple", var13);
         var3 = null;
         var1.setline(289);
         var9 = Py.EmptyObjects;
         var4 = Py.makeClass("AttributesImpl", var9, AttributesImpl$56);
         var1.setlocal("AttributesImpl", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(363);
         var9 = new PyObject[]{var1.getname("AttributesImpl")};
         var4 = Py.makeClass("AttributesNSImpl", var9, AttributesNSImpl$74);
         var1.setlocal("AttributesNSImpl", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(403);
         var9 = new PyObject[]{var1.getname("None")};
         var13 = new PyFunction(var1.f_globals, var9, create_java_parser$82, (PyObject)null);
         var1.setlocal("create_java_parser", var13);
         var3 = null;
         var1.setline(416);
         var9 = new PyObject[]{var1.getname("None")};
         var13 = new PyFunction(var1.f_globals, var9, create_parser$83, (PyObject)null);
         var1.setlocal("create_parser", var13);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject SAXUnicodeDecodeError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(51);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(55);
      PyObject var5 = var1.getname("__repr__");
      var1.setlocal("__str__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("message", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = PyString.fromInterned("SAXUnicodeDecodeError: caused by %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("message")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _wrap_sax_exception$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString var3 = PyString.fromInterned("MalformedByteSequenceException");
      PyObject var10000 = var3._in(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("getException").__call__(var2)));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         var4 = var1.getglobal("SAXUnicodeDecodeError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(68);
         var4 = var1.getglobal("_exceptions").__getattr__("SAXParseException").__call__(var2, var1.getlocal(0).__getattr__("message"), var1.getlocal(0).__getattr__("exception"), var1.getglobal("SimpleLocator").__call__(var2, var1.getlocal(0).__getattr__("columnNumber"), var1.getlocal(0).__getattr__("lineNumber"), var1.getlocal(0).__getattr__("publicId"), var1.getlocal(0).__getattr__("systemId")));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject JyErrorHandlerWrapper$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$7, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$8, (PyObject)null);
      var1.setlocal("fatalError", var4);
      var3 = null;
      var1.setline(85);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, warning$9, (PyObject)null);
      var1.setlocal("warning", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_err_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$7(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      var1.getlocal(0).__getattr__("_err_handler").__getattr__("error").__call__(var2, var1.getglobal("_wrap_sax_exception").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fatalError$8(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      var1.getlocal(0).__getattr__("_err_handler").__getattr__("fatalError").__call__(var2, var1.getglobal("_wrap_sax_exception").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$9(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      var1.getlocal(0).__getattr__("_err_handler").__getattr__("warning").__call__(var2, var1.getglobal("_wrap_sax_exception").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject JyInputSourceWrapper$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(89);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(91);
         var1.getglobal("javasax").__getattr__("InputSource").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      } else {
         var1.setline(92);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("read")).__nonzero__()) {
            var1.setline(93);
            PyObject var3 = var1.getlocal(1);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(94);
            var1.getglobal("javasax").__getattr__("InputSource").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getglobal("FilelikeInputStream").__call__(var2, var1.getlocal(2)));
            var1.setline(95);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("name")).__nonzero__()) {
               var1.setline(96);
               var1.getlocal(0).__getattr__("setSystemId").__call__(var2, var1.getlocal(2).__getattr__("name"));
            }
         } else {
            var1.setline(100);
            if (var1.getlocal(1).__getattr__("getByteStream").__call__(var2).__nonzero__()) {
               var1.setline(101);
               var1.getglobal("javasax").__getattr__("InputSource").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getglobal("FilelikeInputStream").__call__(var2, var1.getlocal(1).__getattr__("getByteStream").__call__(var2)));
            } else {
               var1.setline(104);
               var1.getglobal("javasax").__getattr__("InputSource").__getattr__("__init__").__call__(var2, var1.getlocal(0));
            }

            var1.setline(105);
            if (var1.getlocal(1).__getattr__("getSystemId").__call__(var2).__nonzero__()) {
               var1.setline(106);
               var1.getlocal(0).__getattr__("setSystemId").__call__(var2, var1.getlocal(1).__getattr__("getSystemId").__call__(var2));
            }

            var1.setline(107);
            var1.getlocal(0).__getattr__("setPublicId").__call__(var2, var1.getlocal(1).__getattr__("getPublicId").__call__(var2));
            var1.setline(108);
            var1.getlocal(0).__getattr__("setEncoding").__call__(var2, var1.getlocal(1).__getattr__("getEncoding").__call__(var2));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject JyEntityResolverWrapper$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(111);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, resolveEntity$14, (PyObject)null);
      var1.setlocal("resolveEntity", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_resolver", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resolveEntity$14(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getglobal("JyInputSourceWrapper").__call__(var2, var1.getlocal(0).__getattr__("_resolver").__getattr__("resolveEntity").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject JyDTDHandlerWrapper$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(118);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, notationDecl$17, (PyObject)null);
      var1.setlocal("notationDecl", var4);
      var3 = null;
      var1.setline(124);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unparsedEntityDecl$18, (PyObject)null);
      var1.setlocal("unparsedEntityDecl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject notationDecl$17(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      var1.getlocal(0).__getattr__("_handler").__getattr__("notationDecl").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unparsedEntityDecl$18(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      var1.getlocal(0).__getattr__("_handler").__getattr__("unparsedEntityDecl").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SimpleLocator$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(128);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getColumnNumber$21, (PyObject)null);
      var1.setlocal("getColumnNumber", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLineNumber$22, (PyObject)null);
      var1.setlocal("getLineNumber", var4);
      var3 = null;
      var1.setline(140);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getPublicId$23, (PyObject)null);
      var1.setlocal("getPublicId", var4);
      var3 = null;
      var1.setline(143);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getSystemId$24, (PyObject)null);
      var1.setlocal("getSystemId", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("colNum", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineNum", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("pubId", var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("sysId", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getColumnNumber$21(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyObject var3 = var1.getlocal(0).__getattr__("colNum");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getLineNumber$22(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("lineNum");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getPublicId$23(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getlocal(0).__getattr__("pubId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getSystemId$24(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyObject var3 = var1.getlocal(0).__getattr__("sysId");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject JavaSAXParser$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("SAX driver for the Java SAX parsers."));
      var1.setline(148);
      PyString.fromInterned("SAX driver for the Java SAX parsers.");
      var1.setline(150);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$26, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$27, PyString.fromInterned("Parse an XML document from a URL or an InputSource."));
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getFeature$28, (PyObject)null);
      var1.setlocal("getFeature", var4);
      var3 = null;
      var1.setline(182);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setFeature$29, (PyObject)null);
      var1.setlocal("setFeature", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getProperty$30, (PyObject)null);
      var1.setlocal("getProperty", var4);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setProperty$31, (PyObject)null);
      var1.setlocal("setProperty", var4);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setEntityResolver$32, (PyObject)null);
      var1.setlocal("setEntityResolver", var4);
      var3 = null;
      var1.setline(195);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setErrorHandler$33, (PyObject)null);
      var1.setlocal("setErrorHandler", var4);
      var3 = null;
      var1.setline(199);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDTDHandler$34, (PyObject)null);
      var1.setlocal("setDTDHandler", var4);
      var3 = null;
      var1.setline(204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setDocumentLocator$35, (PyObject)null);
      var1.setlocal("setDocumentLocator", var4);
      var3 = null;
      var1.setline(207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDocument$36, (PyObject)null);
      var1.setlocal("startDocument", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$37, (PyObject)null);
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startPrefixMapping$38, (PyObject)null);
      var1.setlocal("startPrefixMapping", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$39, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      var1.setline(226);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$40, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$41, (PyObject)null);
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endPrefixMapping$42, (PyObject)null);
      var1.setlocal("endPrefixMapping", var4);
      var3 = null;
      var1.setline(239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDocument$43, (PyObject)null);
      var1.setlocal("endDocument", var4);
      var3 = null;
      var1.setline(242);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$44, (PyObject)null);
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, comment$45, (PyObject)null);
      var1.setlocal("comment", var4);
      var3 = null;
      var1.setline(253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startCDATA$46, (PyObject)null);
      var1.setlocal("startCDATA", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endCDATA$47, (PyObject)null);
      var1.setlocal("endCDATA", var4);
      var3 = null;
      var1.setline(259);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startDTD$48, (PyObject)null);
      var1.setlocal("startDTD", var4);
      var3 = null;
      var1.setline(262);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endDTD$49, (PyObject)null);
      var1.setlocal("endDTD", var4);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startEntity$50, (PyObject)null);
      var1.setlocal("startEntity", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endEntity$51, (PyObject)null);
      var1.setlocal("endEntity", var4);
      var3 = null;
      var1.setline(271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, skippedEntity$52, (PyObject)null);
      var1.setlocal("skippedEntity", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$26(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      var1.getglobal("xmlreader").__getattr__("XMLReader").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(152);
      PyObject var3 = var1.getglobal("create_java_parser").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_parser", var3);
      var3 = null;
      var1.setline(153);
      var1.getlocal(0).__getattr__("_parser").__getattr__("setFeature").__call__((ThreadState)var2, (PyObject)var1.getglobal("feature_namespaces"), (PyObject)Py.newInteger(0));
      var1.setline(154);
      var1.getlocal(0).__getattr__("_parser").__getattr__("setFeature").__call__((ThreadState)var2, (PyObject)var1.getglobal("feature_namespace_prefixes"), (PyObject)Py.newInteger(0));
      var1.setline(155);
      var1.getlocal(0).__getattr__("_parser").__getattr__("setContentHandler").__call__(var2, var1.getlocal(0));
      var1.setline(156);
      var3 = var1.getglobal("AttributesNSImpl").__call__(var2);
      var1.getlocal(0).__setattr__("_nsattrs", var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getglobal("AttributesImpl").__call__(var2);
      var1.getlocal(0).__setattr__("_attrs", var3);
      var3 = null;
      var1.setline(158);
      var1.getlocal(0).__getattr__("setEntityResolver").__call__(var2, var1.getlocal(0).__getattr__("getEntityResolver").__call__(var2));
      var1.setline(159);
      var1.getlocal(0).__getattr__("setErrorHandler").__call__(var2, var1.getlocal(0).__getattr__("getErrorHandler").__call__(var2));
      var1.setline(160);
      var1.getlocal(0).__getattr__("setDTDHandler").__call__(var2, var1.getlocal(0).__getattr__("getDTDHandler").__call__(var2));

      try {
         var1.setline(162);
         var1.getlocal(0).__getattr__("_parser").__getattr__("setProperty").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://xml.org/sax/properties/lexical-handler"), (PyObject)var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("Exception"))) {
            throw var6;
         }

         PyObject var4 = var6.value;
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(164);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$27(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyString.fromInterned("Parse an XML document from a URL or an InputSource.");

      try {
         var1.setline(171);
         var1.getlocal(0).__getattr__("_parser").__getattr__("parse").__call__(var2, var1.getglobal("JyInputSourceWrapper").__call__(var2, var1.getlocal(1)));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("JException"))) {
            PyObject var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(174);
            PyString var6 = PyString.fromInterned("MalformedByteSequenceException");
            PyObject var10000 = var6._in(var1.getglobal("str").__call__(var2, var1.getlocal(2)));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(175);
               throw Py.makeException(var1.getglobal("SAXUnicodeDecodeError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2))));
            }

            var1.setline(177);
            throw Py.makeException();
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getFeature$28(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyObject var3 = var1.getlocal(0).__getattr__("_parser").__getattr__("getFeature").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setFeature$29(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      var1.getlocal(0).__getattr__("_parser").__getattr__("setFeature").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getProperty$30(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(0).__getattr__("_parser").__getattr__("getProperty").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setProperty$31(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      var1.getlocal(0).__getattr__("_parser").__getattr__("setProperty").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setEntityResolver$32(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var3 = var1.getglobal("JyEntityResolverWrapper").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__getattr__("_parser").__setattr__("entityResolver", var3);
      var3 = null;
      var1.setline(193);
      var1.getglobal("xmlreader").__getattr__("XMLReader").__getattr__("setEntityResolver").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setErrorHandler$33(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyObject var3 = var1.getglobal("JyErrorHandlerWrapper").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__getattr__("_parser").__setattr__("errorHandler", var3);
      var3 = null;
      var1.setline(197);
      var1.getglobal("xmlreader").__getattr__("XMLReader").__getattr__("setErrorHandler").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDTDHandler$34(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      var1.getlocal(0).__getattr__("_parser").__getattr__("setDTDHandler").__call__(var2, var1.getglobal("JyDTDHandlerWrapper").__call__(var2, var1.getlocal(1)));
      var1.setline(201);
      var1.getglobal("xmlreader").__getattr__("XMLReader").__getattr__("setDTDHandler").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDocumentLocator$35(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("setDocumentLocator").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDocument$36(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startDocument").__call__(var2);
      var1.setline(209);
      PyObject var3 = var1.getlocal(0).__getattr__("_parser").__getattr__("getFeature").__call__(var2, var1.getglobal("feature_namespaces"));
      var1.getlocal(0).__setattr__("_namespaces", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$37(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_namespaces").__nonzero__()) {
         var1.setline(213);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("_nsattrs").__setattr__("_attrs", var3);
         var3 = null;
         var1.setline(214);
         PyObject var10000 = var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startElementNS");
         PyTuple var10002 = new PyTuple;
         PyObject[] var10004 = new PyObject[2];
         PyObject var10007 = var1.getlocal(1);
         if (!var10007.__nonzero__()) {
            var10007 = var1.getglobal("None");
         }

         var10004[0] = var10007;
         var10004[1] = var1.getlocal(2);
         var10002.<init>(var10004);
         var10000.__call__((ThreadState)var2, var10002, (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(0).__getattr__("_nsattrs"));
      } else {
         var1.setline(217);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("_attrs").__setattr__("_attrs", var3);
         var3 = null;
         var1.setline(218);
         var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startElement").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("_attrs"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startPrefixMapping$38(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("startPrefixMapping").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$39(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("characters").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getglobal("String").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$40(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("ignorableWhitespace").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getglobal("String").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElement$41(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      if (var1.getlocal(0).__getattr__("_namespaces").__nonzero__()) {
         var1.setline(232);
         PyObject var10000 = var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endElementNS");
         PyTuple var10002 = new PyTuple;
         PyObject[] var10004 = new PyObject[2];
         PyObject var10007 = var1.getlocal(1);
         if (!var10007.__nonzero__()) {
            var10007 = var1.getglobal("None");
         }

         var10004[0] = var10007;
         var10004[1] = var1.getlocal(2);
         var10002.<init>(var10004);
         var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var1.getlocal(3));
      } else {
         var1.setline(234);
         var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endElement").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endPrefixMapping$42(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endPrefixMapping").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDocument$43(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("endDocument").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$44(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      var1.getlocal(0).__getattr__("_cont_handler").__getattr__("processingInstruction").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject comment$45(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(249);
         var1.getlocal(0).__getattr__("_cont_handler").__getattr__("comment").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getglobal("String").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3))));
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(251);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startCDATA$46(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endCDATA$47(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDTD$48(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endDTD$49(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startEntity$50(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endEntity$51(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject skippedEntity$52(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _fixTuple$53(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("tuple"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._eq(Py.newInteger(2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(277);
         var3 = var1.getlocal(0);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(278);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(279);
            var3 = var1.getlocal(2);
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(280);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(281);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _makeJavaNsTuple$54(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyObject var3 = var1.getglobal("_fixTuple").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("None"), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _makePythonNsTuple$55(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getglobal("_fixTuple").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned(""), (PyObject)var1.getglobal("None"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AttributesImpl$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(291);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$57, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getLength$58, (PyObject)null);
      var1.setlocal("getLength", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getType$59, (PyObject)null);
      var1.setlocal("getType", var4);
      var3 = null;
      var1.setline(300);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValue$60, (PyObject)null);
      var1.setlocal("getValue", var4);
      var3 = null;
      var1.setline(306);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNames$61, (PyObject)null);
      var1.setlocal("getNames", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNames$62, (PyObject)null);
      var1.setlocal("getQNames", var4);
      var3 = null;
      var1.setline(312);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValueByQName$63, (PyObject)null);
      var1.setlocal("getValueByQName", var4);
      var3 = null;
      var1.setline(318);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNameByQName$64, (PyObject)null);
      var1.setlocal("getNameByQName", var4);
      var3 = null;
      var1.setline(324);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNameByName$65, (PyObject)null);
      var1.setlocal("getQNameByName", var4);
      var3 = null;
      var1.setline(330);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$66, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(333);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$67, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(336);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$68, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(339);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$69, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(342);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$70, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(345);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$71, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(348);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$72, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(354);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$73, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$57(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_attrs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getLength$58(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getLength").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getType$59(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getType").__call__(var2, var1.getglobal("_makeJavaNsTuple").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getValue$60(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getValue").__call__(var2, var1.getglobal("_makeJavaNsTuple").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(303);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(304);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getNames$61(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(307);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

      while(true) {
         var1.setline(307);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(307);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(307);
         var1.getlocal(1).__call__(var2, var1.getglobal("_makePythonNsTuple").__call__(var2, var1.getlocal(0).__getattr__("_attrs").__getattr__("getQName").__call__(var2, var1.getlocal(2))));
      }
   }

   public PyObject getQNames$62(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(310);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

      while(true) {
         var1.setline(310);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(310);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(310);
         var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_attrs").__getattr__("getQName").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject getValueByQName$63(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getIndex").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(314);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(315);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(316);
         var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getValue").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getNameByQName$64(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getIndex").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(320);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(321);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(322);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getQNameByName$65(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getIndex").__call__(var2, var1.getglobal("_makeJavaNsTuple").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(326);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(327);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(328);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __len__$66(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getLength").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$67(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyObject var3 = var1.getlocal(0).__getattr__("getValue").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject keys$68(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3 = var1.getlocal(0).__getattr__("getNames").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy$69(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("_attrs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject items$70(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(343);
      var3 = var1.getlocal(0).__getattr__("getNames").__call__(var2).__iter__();

      while(true) {
         var1.setline(343);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(343);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(343);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getitem__(var1.getlocal(2))})));
      }
   }

   public PyObject values$71(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyObject var3 = var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("getValue"), var1.getlocal(0).__getattr__("getNames").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$72(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(350);
         var3 = var1.getlocal(0).__getattr__("getValue").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(352);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject has_key$73(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(356);
         var1.getlocal(0).__getattr__("getValue").__call__(var2, var1.getlocal(1));
         var1.setline(357);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(359);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject AttributesNSImpl$74(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(365);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$75, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(368);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getType$76, (PyObject)null);
      var1.setlocal("getType", var4);
      var3 = null;
      var1.setline(372);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getValue$77, (PyObject)null);
      var1.setlocal("getValue", var4);
      var3 = null;
      var1.setline(379);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNames$78, (PyObject)null);
      var1.setlocal("getNames", var4);
      var3 = null;
      var1.setline(385);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getNameByQName$79, (PyObject)null);
      var1.setlocal("getNameByQName", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNameByName$80, (PyObject)null);
      var1.setlocal("getQNameByName", var4);
      var3 = null;
      var1.setline(398);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getQNames$81, (PyObject)null);
      var1.setlocal("getQNames", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$75(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      var1.getglobal("AttributesImpl").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getType$76(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyObject var3 = var1.getglobal("_makeJavaNsTuple").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getType").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getValue$77(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyObject var3 = var1.getglobal("_makeJavaNsTuple").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(374);
      var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getValue").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(375);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(376);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(377);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getNames$78(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(381);
      PyObject var5 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

      while(true) {
         var1.setline(381);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(383);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(382);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("_makePythonNsTuple").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_attrs").__getattr__("getURI").__call__(var2, var1.getlocal(2)), var1.getlocal(0).__getattr__("_attrs").__getattr__("getLocalName").__call__(var2, var1.getlocal(2))}))));
      }
   }

   public PyObject getNameByQName$79(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getIndex").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(387);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(388);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(389);
         var3 = var1.getglobal("_makePythonNsTuple").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_attrs").__getattr__("getURI").__call__(var2, var1.getlocal(2)), var1.getlocal(0).__getattr__("_attrs").__getattr__("getLocalName").__call__(var2, var1.getlocal(2))})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getQNameByName$80(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var3 = var1.getglobal("_makeJavaNsTuple").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(393);
      var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getIndex").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(394);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(395);
         throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
      } else {
         var1.setline(396);
         var3 = var1.getlocal(0).__getattr__("_attrs").__getattr__("getQName").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getQNames$81(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(399);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

      while(true) {
         var1.setline(399);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(399);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(399);
         var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_attrs").__getattr__("getQName").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject create_java_parser$82(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(405);
         PyObject var3;
         if (var1.getlocal(0).__nonzero__()) {
            var1.setline(406);
            var3 = var1.getglobal("XMLReaderFactory").__getattr__("createXMLReader").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(407);
            if (var1.getglobal("jaxp").__nonzero__()) {
               var1.setline(408);
               var3 = var1.getglobal("factory").__getattr__("newSAXParser").__call__(var2).__getattr__("getXMLReader").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(410);
               var3 = var1.getglobal("XMLReaderFactory").__getattr__("createXMLReader").__call__(var2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         PyObject var5;
         if (var4.match(var1.getglobal("ParserConfigurationException"))) {
            var5 = var4.value;
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(412);
            throw Py.makeException(var1.getglobal("_exceptions").__getattr__("SAXReaderNotAvailable").__call__(var2, var1.getlocal(1).__getattr__("getMessage").__call__(var2)));
         } else if (var4.match(var1.getglobal("javasax").__getattr__("SAXException"))) {
            var5 = var4.value;
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(414);
            throw Py.makeException(var1.getglobal("_exceptions").__getattr__("SAXReaderNotAvailable").__call__(var2, var1.getlocal(1).__getattr__("getMessage").__call__(var2)));
         } else {
            throw var4;
         }
      }
   }

   public PyObject create_parser$83(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      PyObject var3 = var1.getglobal("JavaSAXParser").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public drv_javasax$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SAXUnicodeDecodeError$1 = Py.newCode(0, var2, var1, "SAXUnicodeDecodeError", 50, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"e"};
      _wrap_sax_exception$4 = Py.newCode(1, var2, var1, "_wrap_sax_exception", 58, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JyErrorHandlerWrapper$5 = Py.newCode(0, var2, var1, "JyErrorHandlerWrapper", 75, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "err_handler"};
      __init__$6 = Py.newCode(2, var2, var1, "__init__", 76, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc"};
      error$7 = Py.newCode(2, var2, var1, "error", 79, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc"};
      fatalError$8 = Py.newCode(2, var2, var1, "fatalError", 82, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc"};
      warning$9 = Py.newCode(2, var2, var1, "warning", 85, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JyInputSourceWrapper$10 = Py.newCode(0, var2, var1, "JyInputSourceWrapper", 88, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "f"};
      __init__$11 = Py.newCode(2, var2, var1, "__init__", 89, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JyEntityResolverWrapper$12 = Py.newCode(0, var2, var1, "JyEntityResolverWrapper", 110, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "entityResolver"};
      __init__$13 = Py.newCode(2, var2, var1, "__init__", 111, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pubId", "sysId"};
      resolveEntity$14 = Py.newCode(3, var2, var1, "resolveEntity", 114, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JyDTDHandlerWrapper$15 = Py.newCode(0, var2, var1, "JyDTDHandlerWrapper", 117, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dtdHandler"};
      __init__$16 = Py.newCode(2, var2, var1, "__init__", 118, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId"};
      notationDecl$17 = Py.newCode(4, var2, var1, "notationDecl", 121, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "notationName"};
      unparsedEntityDecl$18 = Py.newCode(5, var2, var1, "unparsedEntityDecl", 124, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SimpleLocator$19 = Py.newCode(0, var2, var1, "SimpleLocator", 127, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "colNum", "lineNum", "pubId", "sysId"};
      __init__$20 = Py.newCode(5, var2, var1, "__init__", 128, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getColumnNumber$21 = Py.newCode(1, var2, var1, "getColumnNumber", 134, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLineNumber$22 = Py.newCode(1, var2, var1, "getLineNumber", 137, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getPublicId$23 = Py.newCode(1, var2, var1, "getPublicId", 140, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getSystemId$24 = Py.newCode(1, var2, var1, "getSystemId", 143, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JavaSAXParser$25 = Py.newCode(0, var2, var1, "JavaSAXParser", 147, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "jdriver", "x"};
      __init__$26 = Py.newCode(2, var2, var1, "__init__", 150, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "e"};
      parse$27 = Py.newCode(2, var2, var1, "parse", 168, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getFeature$28 = Py.newCode(2, var2, var1, "getFeature", 179, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "state"};
      setFeature$29 = Py.newCode(3, var2, var1, "setFeature", 182, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getProperty$30 = Py.newCode(2, var2, var1, "getProperty", 185, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      setProperty$31 = Py.newCode(3, var2, var1, "setProperty", 188, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resolver"};
      setEntityResolver$32 = Py.newCode(2, var2, var1, "setEntityResolver", 191, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err_handler"};
      setErrorHandler$33 = Py.newCode(2, var2, var1, "setErrorHandler", 195, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dtd_handler"};
      setDTDHandler$34 = Py.newCode(2, var2, var1, "setDTDHandler", 199, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locator"};
      setDocumentLocator$35 = Py.newCode(2, var2, var1, "setDocumentLocator", 204, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startDocument$36 = Py.newCode(1, var2, var1, "startDocument", 207, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri", "lname", "qname", "attrs"};
      startElement$37 = Py.newCode(5, var2, var1, "startElement", 211, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      startPrefixMapping$38 = Py.newCode(3, var2, var1, "startPrefixMapping", 220, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "char", "start", "len"};
      characters$39 = Py.newCode(4, var2, var1, "characters", 223, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "char", "start", "len"};
      ignorableWhitespace$40 = Py.newCode(4, var2, var1, "ignorableWhitespace", 226, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri", "lname", "qname"};
      endElement$41 = Py.newCode(4, var2, var1, "endElement", 230, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      endPrefixMapping$42 = Py.newCode(2, var2, var1, "endPrefixMapping", 236, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDocument$43 = Py.newCode(1, var2, var1, "endDocument", 239, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$44 = Py.newCode(3, var2, var1, "processingInstruction", 242, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "char", "start", "len"};
      comment$45 = Py.newCode(4, var2, var1, "comment", 246, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startCDATA$46 = Py.newCode(1, var2, var1, "startCDATA", 253, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endCDATA$47 = Py.newCode(1, var2, var1, "endCDATA", 256, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId"};
      startDTD$48 = Py.newCode(4, var2, var1, "startDTD", 259, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDTD$49 = Py.newCode(1, var2, var1, "endDTD", 262, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      startEntity$50 = Py.newCode(2, var2, var1, "startEntity", 265, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endEntity$51 = Py.newCode(2, var2, var1, "endEntity", 268, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      skippedEntity$52 = Py.newCode(2, var2, var1, "skippedEntity", 271, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"nsTuple", "frm", "to", "nsUri", "localName"};
      _fixTuple$53 = Py.newCode(3, var2, var1, "_fixTuple", 275, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"nsTuple"};
      _makeJavaNsTuple$54 = Py.newCode(1, var2, var1, "_makeJavaNsTuple", 283, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"nsTuple"};
      _makePythonNsTuple$55 = Py.newCode(1, var2, var1, "_makePythonNsTuple", 286, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AttributesImpl$56 = Py.newCode(0, var2, var1, "AttributesImpl", 289, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "attrs"};
      __init__$57 = Py.newCode(2, var2, var1, "__init__", 291, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getLength$58 = Py.newCode(1, var2, var1, "getLength", 294, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getType$59 = Py.newCode(2, var2, var1, "getType", 297, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      getValue$60 = Py.newCode(2, var2, var1, "getValue", 300, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[307_16]", "index"};
      getNames$61 = Py.newCode(1, var2, var1, "getNames", 306, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[310_16]", "index"};
      getQNames$62 = Py.newCode(1, var2, var1, "getQNames", 309, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "qname", "idx"};
      getValueByQName$63 = Py.newCode(2, var2, var1, "getValueByQName", 312, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "qname", "idx"};
      getNameByQName$64 = Py.newCode(2, var2, var1, "getNameByQName", 318, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "idx"};
      getQNameByName$65 = Py.newCode(2, var2, var1, "getQNameByName", 324, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$66 = Py.newCode(1, var2, var1, "__len__", 330, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$67 = Py.newCode(2, var2, var1, "__getitem__", 333, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$68 = Py.newCode(1, var2, var1, "keys", 336, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$69 = Py.newCode(1, var2, var1, "copy", 339, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[343_16]", "name"};
      items$70 = Py.newCode(1, var2, var1, "items", 342, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$71 = Py.newCode(1, var2, var1, "values", 345, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "alt"};
      get$72 = Py.newCode(3, var2, var1, "get", 348, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      has_key$73 = Py.newCode(2, var2, var1, "has_key", 354, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AttributesNSImpl$74 = Py.newCode(0, var2, var1, "AttributesNSImpl", 363, false, false, self, 74, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "attrs"};
      __init__$75 = Py.newCode(2, var2, var1, "__init__", 365, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getType$76 = Py.newCode(2, var2, var1, "getType", 368, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "jname", "value"};
      getValue$77 = Py.newCode(2, var2, var1, "getValue", 372, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "idx"};
      getNames$78 = Py.newCode(1, var2, var1, "getNames", 379, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "qname", "idx"};
      getNameByQName$79 = Py.newCode(2, var2, var1, "getNameByQName", 385, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "idx"};
      getQNameByName$80 = Py.newCode(2, var2, var1, "getQNameByName", 391, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[399_16]", "idx"};
      getQNames$81 = Py.newCode(1, var2, var1, "getQNames", 398, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"jdriver", "e"};
      create_java_parser$82 = Py.newCode(1, var2, var1, "create_java_parser", 403, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"jdriver"};
      create_parser$83 = Py.newCode(1, var2, var1, "create_parser", 416, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new drv_javasax$py("xml/sax/drivers2/drv_javasax$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(drv_javasax$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SAXUnicodeDecodeError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this._wrap_sax_exception$4(var2, var3);
         case 5:
            return this.JyErrorHandlerWrapper$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.error$7(var2, var3);
         case 8:
            return this.fatalError$8(var2, var3);
         case 9:
            return this.warning$9(var2, var3);
         case 10:
            return this.JyInputSourceWrapper$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.JyEntityResolverWrapper$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.resolveEntity$14(var2, var3);
         case 15:
            return this.JyDTDHandlerWrapper$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this.notationDecl$17(var2, var3);
         case 18:
            return this.unparsedEntityDecl$18(var2, var3);
         case 19:
            return this.SimpleLocator$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.getColumnNumber$21(var2, var3);
         case 22:
            return this.getLineNumber$22(var2, var3);
         case 23:
            return this.getPublicId$23(var2, var3);
         case 24:
            return this.getSystemId$24(var2, var3);
         case 25:
            return this.JavaSAXParser$25(var2, var3);
         case 26:
            return this.__init__$26(var2, var3);
         case 27:
            return this.parse$27(var2, var3);
         case 28:
            return this.getFeature$28(var2, var3);
         case 29:
            return this.setFeature$29(var2, var3);
         case 30:
            return this.getProperty$30(var2, var3);
         case 31:
            return this.setProperty$31(var2, var3);
         case 32:
            return this.setEntityResolver$32(var2, var3);
         case 33:
            return this.setErrorHandler$33(var2, var3);
         case 34:
            return this.setDTDHandler$34(var2, var3);
         case 35:
            return this.setDocumentLocator$35(var2, var3);
         case 36:
            return this.startDocument$36(var2, var3);
         case 37:
            return this.startElement$37(var2, var3);
         case 38:
            return this.startPrefixMapping$38(var2, var3);
         case 39:
            return this.characters$39(var2, var3);
         case 40:
            return this.ignorableWhitespace$40(var2, var3);
         case 41:
            return this.endElement$41(var2, var3);
         case 42:
            return this.endPrefixMapping$42(var2, var3);
         case 43:
            return this.endDocument$43(var2, var3);
         case 44:
            return this.processingInstruction$44(var2, var3);
         case 45:
            return this.comment$45(var2, var3);
         case 46:
            return this.startCDATA$46(var2, var3);
         case 47:
            return this.endCDATA$47(var2, var3);
         case 48:
            return this.startDTD$48(var2, var3);
         case 49:
            return this.endDTD$49(var2, var3);
         case 50:
            return this.startEntity$50(var2, var3);
         case 51:
            return this.endEntity$51(var2, var3);
         case 52:
            return this.skippedEntity$52(var2, var3);
         case 53:
            return this._fixTuple$53(var2, var3);
         case 54:
            return this._makeJavaNsTuple$54(var2, var3);
         case 55:
            return this._makePythonNsTuple$55(var2, var3);
         case 56:
            return this.AttributesImpl$56(var2, var3);
         case 57:
            return this.__init__$57(var2, var3);
         case 58:
            return this.getLength$58(var2, var3);
         case 59:
            return this.getType$59(var2, var3);
         case 60:
            return this.getValue$60(var2, var3);
         case 61:
            return this.getNames$61(var2, var3);
         case 62:
            return this.getQNames$62(var2, var3);
         case 63:
            return this.getValueByQName$63(var2, var3);
         case 64:
            return this.getNameByQName$64(var2, var3);
         case 65:
            return this.getQNameByName$65(var2, var3);
         case 66:
            return this.__len__$66(var2, var3);
         case 67:
            return this.__getitem__$67(var2, var3);
         case 68:
            return this.keys$68(var2, var3);
         case 69:
            return this.copy$69(var2, var3);
         case 70:
            return this.items$70(var2, var3);
         case 71:
            return this.values$71(var2, var3);
         case 72:
            return this.get$72(var2, var3);
         case 73:
            return this.has_key$73(var2, var3);
         case 74:
            return this.AttributesNSImpl$74(var2, var3);
         case 75:
            return this.__init__$75(var2, var3);
         case 76:
            return this.getType$76(var2, var3);
         case 77:
            return this.getValue$77(var2, var3);
         case 78:
            return this.getNames$78(var2, var3);
         case 79:
            return this.getNameByQName$79(var2, var3);
         case 80:
            return this.getQNameByName$80(var2, var3);
         case 81:
            return this.getQNames$81(var2, var3);
         case 82:
            return this.create_java_parser$82(var2, var3);
         case 83:
            return this.create_parser$83(var2, var3);
         default:
            return null;
      }
   }
}
