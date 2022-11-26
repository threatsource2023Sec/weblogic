package xml.parsers;

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
@Filename("xml/parsers/expat.py")
public class expat$py extends PyFunctionTable implements PyRunnable {
   static expat$py self;
   static final PyCode f$0;
   static final PyCode ParserCreate$1;
   static final PyCode XMLParser$2;
   static final PyCode __init__$3;
   static final PyCode GetBase$4;
   static final PyCode SetBase$5;
   static final PyCode _error$6;
   static final PyCode _get_buffer_text$7;
   static final PyCode _set_buffer_text$8;
   static final PyCode _get_returns_unicode$9;
   static final PyCode _set_returns_unicode$10;
   static final PyCode f$11;
   static final PyCode f$12;
   static final PyCode _expat_error$13;
   static final PyCode Parse$14;
   static final PyCode ParseFile$15;
   static final PyCode _encode$16;
   static final PyCode f$17;
   static final PyCode expat$18;
   static final PyCode _expat$19;
   static final PyCode new_method$20;
   static final PyCode XMLEventHandler$21;
   static final PyCode __init__$22;
   static final PyCode _intern$23;
   static final PyCode _qualify$24;
   static final PyCode _char_slice_to_unicode$25;
   static final PyCode _expat_content_model$26;
   static final PyCode _update_location$27;
   static final PyCode processingInstruction$28;
   static final PyCode startElement$29;
   static final PyCode endElement$30;
   static final PyCode characters$31;
   static final PyCode characters$32;
   static final PyCode characters$33;
   static final PyCode startPrefixMapping$34;
   static final PyCode endPrefixMapping$35;
   static final PyCode resolveEntity$36;
   static final PyCode resolveEntity$37;
   static final PyCode resolveEntity$38;
   static final PyCode resolveEntity$39;
   static final PyCode setDocumentLocator$40;
   static final PyCode skippedEntity$41;
   static final PyCode comment$42;
   static final PyCode startCDATA$43;
   static final PyCode endCDATA$44;
   static final PyCode startDTD$45;
   static final PyCode endDTD$46;
   static final PyCode startEntity$47;
   static final PyCode endEntity$48;
   static final PyCode notationDecl$49;
   static final PyCode unparsedEntityDecl$50;
   static final PyCode attributeDecl$51;
   static final PyCode elementDecl$52;
   static final PyCode externalEntityDecl$53;
   static final PyCode internalEntityDecl$54;
   static final PyCode _init_model$55;
   static final PyCode ExpatError$56;
   static final PyCode _init_error_strings$57;
   static final PyCode ErrorString$58;
   static final PyCode _init_errors$59;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("ExpatError"), PyString.fromInterned("ParserCreate"), PyString.fromInterned("XMLParserType"), PyString.fromInterned("error"), PyString.fromInterned("errors")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(28);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(29);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__not__().__nonzero__()) {
         var1.setline(30);
         throw Py.makeException(var1.getname("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("this version of expat requires the jython interpreter")));
      } else {
         var1.setline(33);
         var5 = imp.importOne("re", var1, -1);
         var1.setlocal("re", var5);
         var3 = null;
         var1.setline(34);
         var5 = imp.importOne("types", var1, -1);
         var1.setlocal("types", var5);
         var3 = null;
         var1.setline(37);
         String[] var6 = new String[]{"Py"};
         PyObject[] var7 = imp.importFrom("org.python.core", var6, var1, -1);
         PyObject var4 = var7[0];
         var1.setlocal("Py", var4);
         var4 = null;
         var1.setline(38);
         var6 = new String[]{"StringUtil"};
         var7 = imp.importFrom("org.python.core.util", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("StringUtil", var4);
         var4 = null;
         var1.setline(39);
         var6 = new String[]{"array"};
         var7 = imp.importFrom("jarray", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("array", var4);
         var4 = null;
         var1.setline(42);
         var6 = new String[]{"ByteArrayInputStream"};
         var7 = imp.importFrom("java.io", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("ByteArrayInputStream", var4);
         var4 = null;
         var1.setline(43);
         var6 = new String[]{"String", "StringBuilder"};
         var7 = imp.importFrom("java.lang", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("String", var4);
         var4 = null;
         var4 = var7[1];
         var1.setlocal("StringBuilder", var4);
         var4 = null;
         var1.setline(44);
         var6 = new String[]{"InputSource"};
         var7 = imp.importFrom("org.xml.sax", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("InputSource", var4);
         var4 = null;
         var1.setline(45);
         var6 = new String[]{"SAXNotRecognizedException", "SAXParseException"};
         var7 = imp.importFrom("org.xml.sax", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("SAXNotRecognizedException", var4);
         var4 = null;
         var4 = var7[1];
         var1.setlocal("SAXParseException", var4);
         var4 = null;
         var1.setline(46);
         var6 = new String[]{"XMLReaderFactory"};
         var7 = imp.importFrom("org.xml.sax.helpers", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("XMLReaderFactory", var4);
         var4 = null;
         var1.setline(47);
         var6 = new String[]{"DefaultHandler2"};
         var7 = imp.importFrom("org.xml.sax.ext", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("DefaultHandler2", var4);
         var4 = null;
         var1.setline(50);
         PyString var8 = PyString.fromInterned("org.python.apache.xerces.parsers.SAXParser");
         var1.setlocal("_mangled_xerces_parser_name", var8);
         var3 = null;
         var1.setline(51);
         var8 = PyString.fromInterned("org.apache.xerces.parsers.SAXParser");
         var1.setlocal("_xerces_parser_name", var8);
         var3 = null;
         var1.setline(55);
         PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal("_register", var9);
         var3 = null;
         var1.setline(58);
         var7 = new PyObject[]{var1.getname("None"), var1.getname("None")};
         PyFunction var10 = new PyFunction(var1.f_globals, var7, ParserCreate$1, (PyObject)null);
         var1.setlocal("ParserCreate", var10);
         var3 = null;
         var1.setline(62);
         var7 = new PyObject[]{var1.getname("object")};
         var4 = Py.makeClass("XMLParser", var7, XMLParser$2);
         var1.setlocal("XMLParser", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(221);
         var5 = var1.getname("XMLParser");
         var1.setlocal("XMLParserType", var5);
         var3 = null;
         var1.setline(224);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _encode$16, (PyObject)null);
         var1.setlocal("_encode", var10);
         var3 = null;
         var1.setline(235);
         var7 = new PyObject[]{var1.getname("None"), var1.getname("True"), var1.getname("False"), var1.getname("None")};
         var10 = new PyFunction(var1.f_globals, var7, expat$18, (PyObject)null);
         var1.setlocal("expat", var10);
         var3 = null;
         var1.setline(268);
         var7 = new PyObject[]{var1.getname("DefaultHandler2")};
         var4 = Py.makeClass("XMLEventHandler", var7, XMLEventHandler$21);
         var1.setlocal("XMLEventHandler", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(481);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _init_model$55, (PyObject)null);
         var1.setlocal("_init_model", var10);
         var3 = null;
         var1.setline(492);
         var1.getname("_init_model").__call__(var2);
         var1.setline(493);
         var1.dellocal("_init_model");
         var1.setline(496);
         var7 = new PyObject[]{var1.getname("Exception")};
         var4 = Py.makeClass("ExpatError", var7, ExpatError$56);
         var1.setlocal("ExpatError", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(500);
         var5 = var1.getname("ExpatError");
         var1.setlocal("error", var5);
         var3 = null;
         var1.setline(503);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _init_error_strings$57, (PyObject)null);
         var1.setlocal("_init_error_strings", var10);
         var3 = null;
         var1.setline(550);
         var1.getname("_init_error_strings").__call__(var2);
         var1.setline(551);
         var1.dellocal("_init_error_strings");
         var1.setline(554);
         var7 = Py.EmptyObjects;
         var10 = new PyFunction(var1.f_globals, var7, _init_errors$59, (PyObject)null);
         var1.setlocal("_init_errors", var10);
         var3 = null;
         var1.setline(605);
         var1.getname("_init_errors").__call__(var2);
         var1.setline(606);
         var1.dellocal("_init_errors");
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject ParserCreate$1(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getglobal("XMLParser").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject XMLParser$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(64);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, GetBase$4, (PyObject)null);
      var1.setlocal("GetBase", var4);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, SetBase$5, (PyObject)null);
      var1.setlocal("SetBase", var4);
      var3 = null;
      var1.setline(147);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _error$6, (PyObject)null);
      var1.setlocal("_error", var4);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_buffer_text$7, (PyObject)null);
      var1.setlocal("_get_buffer_text", var4);
      var3 = null;
      var1.setline(153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_buffer_text$8, (PyObject)null);
      var1.setlocal("_set_buffer_text", var4);
      var3 = null;
      var1.setline(156);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_returns_unicode$9, (PyObject)null);
      var1.setlocal("_get_returns_unicode", var4);
      var3 = null;
      var1.setline(159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_returns_unicode$10, (PyObject)null);
      var1.setlocal("_set_returns_unicode", var4);
      var3 = null;
      var1.setline(163);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("_error"), var1.getname("_error"));
      var1.setlocal("ordered_attributes", var5);
      var3 = null;
      var1.setline(164);
      var5 = var1.getname("property").__call__(var2, var1.getname("_error"), var1.getname("_error"));
      var1.setlocal("specified_attributes", var5);
      var3 = null;
      var1.setline(166);
      var5 = var1.getname("property").__call__(var2, var1.getname("_get_buffer_text"), var1.getname("_set_buffer_text"));
      var1.setlocal("buffer_text", var5);
      var3 = null;
      var1.setline(168);
      PyObject var10000 = var1.getname("property");
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var5 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$11)));
      var1.setlocal("buffer_used", var5);
      var3 = null;
      var1.setline(169);
      var10000 = var1.getname("property");
      var1.setline(169);
      var3 = Py.EmptyObjects;
      var5 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$12)));
      var1.setlocal("buffer_size", var5);
      var3 = null;
      var1.setline(171);
      var5 = var1.getname("property").__call__(var2, var1.getname("_get_returns_unicode"), var1.getname("_set_returns_unicode"));
      var1.setlocal("returns_unicode", var5);
      var3 = null;
      var1.setline(173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _expat_error$13, (PyObject)null);
      var1.setlocal("_expat_error", var4);
      var3 = null;
      var1.setline(187);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, Parse$14, (PyObject)null);
      var1.setlocal("Parse", var4);
      var3 = null;
      var1.setline(215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ParseFile$15, (PyObject)null);
      var1.setlocal("ParseFile", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.setline(66);
      PyInteger var11 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"CurrentLineNumber", var11);
      var3 = null;
      var1.setline(67);
      var11 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"CurrentColumnNumber", var11);
      var3 = null;
      var1.setline(68);
      var11 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_NextLineNumber", var11);
      var3 = null;
      var1.setline(69);
      var11 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_NextColumnNumber", var11);
      var3 = null;
      var1.setline(70);
      var11 = Py.newInteger(-1);
      var1.getlocal(0).__setattr__((String)"ErrorLineNumber", var11);
      var3 = null;
      var1.setline(71);
      var11 = Py.newInteger(-1);
      var1.getlocal(0).__setattr__((String)"ErrorColumnNumber", var11);
      var3 = null;
      var1.setline(72);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("ErrorCode", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var16;
      if (var10000.__nonzero__()) {
         var1.setline(75);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("namespace_separator", var3);
         var3 = null;
      } else {
         var1.setline(76);
         if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(83);
            var3 = PyString.fromInterned("ParserCreate() argument 2 must be string or None, not %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(2)).__getattr__("__name__"));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(85);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(3)));
         }

         var1.setline(77);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
         var1.getlocal(0).__setattr__("namespace_separator", var3);
         var3 = null;
         var1.setline(78);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("namespace_separator"));
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(79);
            var16 = PyString.fromInterned("namespace_separator must be at most one character, omitted, or None");
            var1.setlocal(3, var16);
            var3 = null;
            var1.setline(81);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(3)));
         }
      }

      PyObject var4;
      try {
         var1.setline(89);
         var3 = var1.getglobal("XMLReaderFactory").__getattr__("createXMLReader").__call__(var2, var1.getglobal("_mangled_xerces_parser_name"));
         var1.getlocal(0).__setattr__("_reader", var3);
         var3 = null;
      } catch (Throwable var7) {
         Py.setException(var7, var1);
         var1.setline(91);
         var4 = var1.getglobal("XMLReaderFactory").__getattr__("createXMLReader").__call__(var2, var1.getglobal("_xerces_parser_name"));
         var1.getlocal(0).__setattr__("_reader", var4);
         var4 = null;
      }

      var1.setline(93);
      var3 = var1.getlocal(0).__getattr__("namespace_separator");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(95);
            var16 = PyString.fromInterned("http://xml.org/sax/features/namespaces");
            var1.setlocal(4, var16);
            var3 = null;
            var1.setline(96);
            var1.getlocal(0).__getattr__("_reader").__getattr__("setFeature").__call__(var2, var1.getlocal(4), var1.getglobal("False"));
         } catch (Throwable var9) {
            PyException var15 = Py.setException(var9, var1);
            if (var15.match(var1.getglobal("SAXNotRecognizedException"))) {
               var1.setline(98);
               PyString var12 = PyString.fromInterned("namespace support cannot be disabled; set namespace_separator to a string of length 1.");
               var1.setlocal(3, var12);
               var4 = null;
               var1.setline(100);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(3)));
            }

            throw var15;
         }
      }

      var1.setline(102);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_base", var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_buffer_text", var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_returns_unicode", var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getglobal("StringBuilder").__call__(var2);
      var1.getlocal(0).__setattr__("_data", var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getglobal("XMLEventHandler").__call__(var2, var1.getlocal(0));
      var1.getlocal(0).__setattr__("_handler", var3);
      var3 = null;
      var1.setline(109);
      var1.getlocal(0).__getattr__("_reader").__getattr__("setContentHandler").__call__(var2, var1.getlocal(0).__getattr__("_handler"));
      var1.setline(110);
      var1.getlocal(0).__getattr__("_reader").__getattr__("setErrorHandler").__call__(var2, var1.getlocal(0).__getattr__("_handler"));
      var1.setline(111);
      var1.getlocal(0).__getattr__("_reader").__getattr__("setDTDHandler").__call__(var2, var1.getlocal(0).__getattr__("_handler"));
      var1.setline(112);
      var1.getlocal(0).__getattr__("_reader").__getattr__("setEntityResolver").__call__(var2, var1.getlocal(0).__getattr__("_handler"));
      var1.setline(114);
      PyTuple var17 = new PyTuple(new PyObject[]{PyString.fromInterned("lexical-handler"), PyString.fromInterned("declaration-handler")});
      var1.setlocal(5, var17);
      var3 = null;
      var1.setline(115);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(115);
         var4 = var3.__iternext__();
         PyException var5;
         PyObject var6;
         PyObject var13;
         if (var4 == null) {
            var1.setline(123);
            var17 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("nonvalidating/load-external-dtd"), var1.getglobal("False")})});
            var1.setlocal(7, var17);
            var3 = null;
            var1.setline(124);
            var3 = var1.getlocal(7).__iter__();

            while(true) {
               var1.setline(124);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(134);
                  var16 = PyString.fromInterned("http://xml.org/sax/features/external-parameter-entities");
                  var1.setlocal(9, var16);
                  var3 = null;
                  var1.setline(138);
                  var16 = PyString.fromInterned("http://xml.org/sax/features/use-entity-resolver2");
                  var1.setlocal(9, var16);
                  var3 = null;
                  var1.setline(139);
                  if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("_reader").__getattr__("getFeature").__call__(var2, var1.getlocal(9)).__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  } else {
                     var1.f_lasti = -1;
                     return Py.None;
                  }
               }

               PyObject[] var14 = Py.unpackSequence(var4, 2);
               var6 = var14[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var14[1];
               var1.setlocal(8, var6);
               var6 = null;

               try {
                  var1.setline(126);
                  var13 = PyString.fromInterned("http://apache.org/xml/features/")._add(var1.getlocal(6));
                  var1.setlocal(6, var13);
                  var5 = null;
                  var1.setline(127);
                  var1.getlocal(0).__getattr__("_reader").__getattr__("setFeature").__call__(var2, var1.getlocal(6), var1.getlocal(8));
               } catch (Throwable var8) {
                  var5 = Py.setException(var8, var1);
                  if (var5.match(var1.getglobal("SAXNotRecognizedException"))) {
                     var1.setline(129);
                     var6 = PyString.fromInterned("can't set feature %r")._mod(var1.getlocal(6));
                     var1.setlocal(3, var6);
                     var6 = null;
                     var1.setline(130);
                     throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, var1.getlocal(3)));
                  }

                  throw var5;
               }
            }
         }

         var1.setlocal(6, var4);

         try {
            var1.setline(117);
            var13 = PyString.fromInterned("http://xml.org/sax/properties/")._add(var1.getlocal(6));
            var1.setlocal(6, var13);
            var5 = null;
            var1.setline(118);
            var1.getlocal(0).__getattr__("_reader").__getattr__("setProperty").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("_handler"));
         } catch (Throwable var10) {
            var5 = Py.setException(var10, var1);
            if (var5.match(var1.getglobal("SAXNotRecognizedException"))) {
               var1.setline(120);
               var6 = PyString.fromInterned("can't set property %r")._mod(var1.getlocal(6));
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(121);
               throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, var1.getlocal(3)));
            }

            throw var5;
         }
      }
   }

   public PyObject GetBase$4(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getlocal(0).__getattr__("_base");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SetBase$5(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_base", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _error$6(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      throw Py.makeException(var1.getglobal("AttributeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'XMLParser' has no such attribute")));
   }

   public PyObject _get_buffer_text$7(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3 = var1.getlocal(0).__getattr__("_buffer_text");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_buffer_text$8(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_buffer_text", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_returns_unicode$9(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(0).__getattr__("_returns_unicode"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_returns_unicode$10(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_returns_unicode", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$11(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$12(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _expat_error$13(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getlocal(1).__getattr__("getMessage").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(175);
      PyString var4 = PyString.fromInterned("The entity \".*\" was referenced, but not declared\\.");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(176);
      if (var1.getglobal("re").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(2)).__nonzero__()) {
         var1.setline(177);
         var3 = PyString.fromInterned("undefined entity: line %s, column %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("ErrorLineNumber"), var1.getlocal(0).__getattr__("ErrorColumnNumber")}));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(180);
         var3 = var1.getlocal(2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(181);
      var3 = var1.getglobal("ExpatError").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(182);
      var3 = var1.getlocal(0).__getattr__("ErrorLineNumber");
      var1.getlocal(5).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getlocal(0).__getattr__("ErrorColumnNumber");
      var1.getlocal(5).__setattr__("offset", var3);
      var3 = null;
      var1.setline(184);
      var3 = var1.getlocal(0).__getattr__("ErrorCode");
      var1.getlocal(5).__setattr__("code", var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Parse$14(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(193);
         var3 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(195);
      var1.getlocal(0).__getattr__("_data").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(197);
      if (!var1.getlocal(2).__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(198);
         var3 = var1.getglobal("StringUtil").__getattr__("toBytes").__call__(var2, var1.getlocal(0).__getattr__("_data").__getattr__("toString").__call__(var2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(199);
         var3 = var1.getglobal("ByteArrayInputStream").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(200);
         var3 = var1.getglobal("InputSource").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(201);
         var3 = var1.getlocal(0).__getattr__("encoding");
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var1.getlocal(5).__getattr__("setEncoding").__call__(var2, var1.getlocal(0).__getattr__("encoding"));
         }

         try {
            var1.setline(204);
            var1.getlocal(0).__getattr__("_reader").__getattr__("parse").__call__(var2, var1.getlocal(5));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("SAXParseException"))) {
               PyObject var4 = var6.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(209);
               var4 = var1.getlocal(0).__getattr__("_NextLineNumber");
               var1.getlocal(0).__setattr__("ErrorLineNumber", var4);
               var4 = null;
               var1.setline(210);
               var4 = var1.getlocal(0).__getattr__("_NextColumnNumber");
               var1.getlocal(0).__setattr__("ErrorColumnNumber", var4);
               var4 = null;
               var1.setline(211);
               var4 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("ErrorCode", var4);
               var4 = null;
               var1.setline(212);
               throw Py.makeException(var1.getlocal(0).__getattr__("_expat_error").__call__(var2, var1.getlocal(6)));
            }

            throw var6;
         }

         var1.setline(213);
         PyInteger var7 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject ParseFile$15(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var10000 = var1.getlocal(0).__getattr__("Parse");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getattr__("read").__call__(var2), var1.getglobal("True")};
      String[] var4 = new String[]{"isfinal"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _encode$16(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(225);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(226);
         var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getderef(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(228);
         PyObject var4;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("dict")).__nonzero__()) {
            var1.setline(229);
            var4 = var1.getlocal(0).__getattr__("iteritems").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         } else {
            var1.setline(231);
            var4 = var1.getglobal("iter").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(232);
         PyObject var10000 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
         var1.setline(232);
         PyObject var10004 = var1.f_globals;
         PyObject[] var6 = Py.EmptyObjects;
         PyCode var10006 = f$17;
         PyObject[] var5 = new PyObject[]{var1.getclosure(0)};
         PyFunction var7 = new PyFunction(var10004, var6, var10006, (PyObject)null, var5);
         PyObject var10002 = var7.__call__(var2, var1.getlocal(2).__iter__());
         Arrays.fill(var6, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$17(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(232);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(232);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(232);
         var1.setline(232);
         var6 = var1.getglobal("_encode").__call__(var2, var1.getlocal(1), var1.getderef(0));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject expat$18(PyFrame var1, ThreadState var2) {
      var1.to_cell(2, 0);
      var1.to_cell(1, 1);
      var1.to_cell(3, 2);
      var1.to_cell(0, 3);
      var1.setline(236);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = _expat$19;
      var3 = new PyObject[]{var1.getclosure(3), var1.getclosure(1), var1.getclosure(0), var1.getclosure(2)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(265);
      PyObject var5 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _expat$19(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getlocal(0).__getattr__("__name__");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getglobal("id").__call__(var2, var1.getglobal("sys").__getattr__("_getframe").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(239);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getderef(0), var1.getlocal(1)});
      var1.setderef(1, var4);
      var3 = null;
      var1.setline(240);
      var3 = var1.getglobal("_register").__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getderef(1), (PyObject)(new PyList(Py.EmptyObjects))).__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(241);
      var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getderef(2), var1.getderef(3), var1.getderef(4), var1.getderef(5)})));
      var1.setline(243);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = new_method$20;
      var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(262);
      var3 = var1.getderef(0);
      var1.getlocal(3).__setattr__("__name__", var3);
      var3 = null;
      var1.setline(264);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject new_method$20(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(245);
      var3 = var1.getlocal(1).__getattr__("parser");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(246);
      PyObject var10000 = var1.getlocal(1).__getattr__("_update_location");
      PyObject[] var7 = new PyObject[]{var1.getderef(0)};
      String[] var4 = new String[]{"event"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(247);
      var3 = var1.getglobal("_register").__getitem__(var1.getderef(1)).__iter__();

      PyObject[] var5;
      PyObject var9;
      do {
         var1.setline(247);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var5 = Py.unpackSequence(var8, 5);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[4];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(248);
         var9 = var1.getlocal(5);
         var10000 = var9._notin(new PyTuple(new PyObject[]{var1.getglobal("True"), var1.getglobal("False")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(249);
            var9 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            var1.setlocal(5, var9);
            var5 = null;
         }

         var1.setline(250);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(4), var1.getglobal("None"));
            }
         }

         var9 = var10000;
         var1.setlocal(8, var9);
         var5 = null;
         var1.setline(252);
         var10000 = var1.getlocal(8);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(6);
         }
      } while(!var10000.__nonzero__());

      var1.setline(253);
      var10000 = var1.getlocal(3);
      var5 = Py.EmptyObjects;
      String[] var11 = new String[0];
      var10000 = var10000._callextra(var5, var11, var1.getlocal(0), (PyObject)null);
      var5 = null;
      var9 = var10000;
      var1.setlocal(9, var9);
      var5 = null;
      var1.setline(254);
      if (var1.getlocal(8).__nonzero__()) {
         var1.setline(255);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("tuple")).__not__().__nonzero__()) {
            var1.setline(256);
            PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(9)});
            var1.setlocal(9, var10);
            var5 = null;
         }

         var1.setline(257);
         if (var1.getlocal(2).__getattr__("returns_unicode").__not__().__nonzero__()) {
            var1.setline(258);
            var9 = var1.getglobal("_encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("utf-8"));
            var1.setlocal(9, var9);
            var5 = null;
         }

         var1.setline(259);
         var10000 = var1.getlocal(8);
         var5 = Py.EmptyObjects;
         var11 = new String[0];
         var10000._callextra(var5, var11, var1.getlocal(9), (PyObject)null);
         var5 = null;
      }

      var1.setline(260);
      var9 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject XMLEventHandler$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(270);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var3, __init__$22, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(279);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _intern$23, (PyObject)null);
      var1.setlocal("_intern", var6);
      var3 = null;
      var1.setline(282);
      var3 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var3, _qualify$24, (PyObject)null);
      var1.setlocal("_qualify", var6);
      var3 = null;
      var1.setline(291);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _char_slice_to_unicode$25, PyString.fromInterned("Convert a char[] slice to a PyUnicode instance"));
      var1.setlocal("_char_slice_to_unicode", var6);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _expat_content_model$26, (PyObject)null);
      var1.setlocal("_expat_content_model", var6);
      var3 = null;
      var1.setline(300);
      var3 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var3, _update_location$27, (PyObject)null);
      var1.setlocal("_update_location", var6);
      var3 = null;
      var1.setline(322);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, processingInstruction$28, (PyObject)null);
      PyObject var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ProcessingInstructionHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("processingInstruction", var7);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, startElement$29, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("StartElementHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("startElement", var7);
      var3 = null;
      var1.setline(340);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, endElement$30, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndElementHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("endElement", var7);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, characters$31, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CharacterDataHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("characters", var7);
      var3 = null;
      var1.setline(348);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, characters$32, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DefaultHandlerExpand")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("characters", var7);
      var3 = null;
      var1.setline(352);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, characters$33, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DefaultHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("characters", var7);
      var3 = null;
      var1.setline(361);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, startPrefixMapping$34, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("StartNamespaceDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("startPrefixMapping", var7);
      var3 = null;
      var1.setline(365);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, endPrefixMapping$35, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndNamespaceDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("endPrefixMapping", var7);
      var3 = null;
      var1.setline(369);
      var7 = var1.getname("InputSource").__call__(var2, var1.getname("ByteArrayInputStream").__call__(var2, var1.getname("array").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)), (PyObject)PyString.fromInterned("b"))));
      var1.setlocal("empty_source", var7);
      var3 = null;
      var1.setline(371);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, resolveEntity$36, (PyObject)null);
      PyObject var10000 = var1.getname("expat");
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("ExternalEntityRefHandler"), PyString.fromInterned("not_in_dtd"), var1.getname("empty_source")};
      String[] var5 = new String[]{"guard", "returns"};
      var10000 = var10000.__call__(var2, var4, var5);
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("resolveEntity", var7);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, resolveEntity$37, (PyObject)null);
      var10000 = var1.getname("expat");
      var4 = new PyObject[]{PyString.fromInterned("DefaultHandlerExpand"), PyString.fromInterned("not_in_dtd"), var1.getname("empty_source")};
      var5 = new String[]{"guard", "returns"};
      var10000 = var10000.__call__(var2, var4, var5);
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("resolveEntity", var7);
      var3 = null;
      var1.setline(383);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, resolveEntity$38, (PyObject)null);
      var10000 = var1.getname("expat");
      var4 = new PyObject[]{PyString.fromInterned("DefaultHandler"), PyString.fromInterned("not_in_dtd"), var1.getname("empty_source")};
      var5 = new String[]{"guard", "returns"};
      var10000 = var10000.__call__(var2, var4, var5);
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("resolveEntity", var7);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, resolveEntity$39, (PyObject)null);
      var10000 = var1.getname("expat");
      var4 = new PyObject[]{var1.getname("True"), var1.getname("empty_source")};
      var5 = new String[]{"force", "returns"};
      var10000 = var10000.__call__(var2, var4, var5);
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("resolveEntity", var7);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, setDocumentLocator$40, (PyObject)null);
      var1.setlocal("setDocumentLocator", var6);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, skippedEntity$41, (PyObject)null);
      var1.setlocal("skippedEntity", var6);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, comment$42, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CommentHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("comment", var7);
      var3 = null;
      var1.setline(411);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, startCDATA$43, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("StartCdataSectionHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("startCDATA", var7);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, endCDATA$44, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndCdataSectionHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("endCDATA", var7);
      var3 = null;
      var1.setline(419);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, startDTD$45, (PyObject)null);
      var10000 = var1.getname("expat");
      var4 = new PyObject[]{PyString.fromInterned("StartDoctypeDeclHandler"), var1.getname("True")};
      var5 = new String[]{"force"};
      var10000 = var10000.__call__(var2, var4, var5);
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("startDTD", var7);
      var3 = null;
      var1.setline(425);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, endDTD$46, (PyObject)null);
      var10000 = var1.getname("expat");
      var4 = new PyObject[]{PyString.fromInterned("EndDoctypeDeclHandler"), var1.getname("True")};
      var5 = new String[]{"force"};
      var10000 = var10000.__call__(var2, var4, var5);
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("endDTD", var7);
      var3 = null;
      var1.setline(429);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, startEntity$47, (PyObject)null);
      var1.setlocal("startEntity", var6);
      var3 = null;
      var1.setline(435);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, endEntity$48, (PyObject)null);
      var1.setlocal("endEntity", var6);
      var3 = null;
      var1.setline(440);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, notationDecl$49, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NotationDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("notationDecl", var7);
      var3 = null;
      var1.setline(445);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, unparsedEntityDecl$50, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UnparsedEntityDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("unparsedEntityDecl", var7);
      var3 = null;
      var1.setline(452);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, attributeDecl$51, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AttlistDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("attributeDecl", var7);
      var3 = null;
      var1.setline(458);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, elementDecl$52, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ElementDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("elementDecl", var7);
      var3 = null;
      var1.setline(462);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, externalEntityDecl$53, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EntityDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("externalEntityDecl", var7);
      var3 = null;
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, internalEntityDecl$54, (PyObject)null);
      var7 = var1.getname("expat").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EntityDeclHandler")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("internalEntityDecl", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$22(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.setline(272);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_tags", var4);
      var3 = null;
      var1.setline(273);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("not_in_dtd", var3);
      var3 = null;
      var1.setline(274);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_entity", var4);
      var3 = null;
      var1.setline(275);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_previous_event", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _intern$23(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getlocal(0).__getattr__("_tags").__getattr__("setdefault").__call__(var2, var1.getlocal(1), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _qualify$24(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      PyObject var3 = var1.getlocal(0).__getattr__("parser").__getattr__("namespace_separator");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(284);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(285);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(286);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(287);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(289);
            var3 = var1.getlocal(3)._add(var1.getlocal(4))._add(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _char_slice_to_unicode$25(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyString.fromInterned("Convert a char[] slice to a PyUnicode instance");
      var1.setline(293);
      PyObject var3 = var1.getglobal("Py").__getattr__("newUnicode").__call__(var2, var1.getglobal("String").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3)), (PyObject)null)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(294);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _expat_content_model$26(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _update_location$27(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyObject var3 = var1.getlocal(0).__getattr__("parser");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getlocal(0).__getattr__("_locator");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(307);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("startElement"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_previous_event");
         var10000 = var3._eq(PyString.fromInterned("characters"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(308);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("_NextColumnNumber")._sub(Py.newInteger(1)), (PyObject)Py.newInteger(0));
         var1.getlocal(2).__setattr__("_NextColumnNumber", var3);
         var3 = null;
      }

      var1.setline(309);
      var3 = var1.getlocal(1);
      var10000 = var3._eq(PyString.fromInterned("endElement"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_previous_event");
         var10000 = var3._eq(PyString.fromInterned("characters"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(310);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("_NextColumnNumber")._sub(Py.newInteger(2)), (PyObject)Py.newInteger(0));
         var1.getlocal(2).__setattr__("_NextColumnNumber", var3);
         var3 = null;
      }

      var1.setline(313);
      var3 = var1.getlocal(2).__getattr__("_NextLineNumber");
      var1.getlocal(2).__setattr__("CurrentLineNumber", var3);
      var3 = null;
      var1.setline(314);
      var3 = var1.getlocal(2).__getattr__("_NextColumnNumber");
      var1.getlocal(2).__setattr__("CurrentColumnNumber", var3);
      var3 = null;
      var1.setline(315);
      var3 = var1.getlocal(3).__getattr__("getLineNumber").__call__(var2);
      var1.getlocal(2).__setattr__("_NextLineNumber", var3);
      var3 = null;
      var1.setline(316);
      var3 = var1.getlocal(3).__getattr__("getColumnNumber").__call__(var2)._sub(Py.newInteger(1));
      var1.getlocal(2).__setattr__("_NextColumnNumber", var3);
      var3 = null;
      var1.setline(318);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_previous_event", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$28(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject startElement$29(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyObject var3 = var1.getlocal(0).__getattr__("_qualify").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(329);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(330);
      var3 = var1.getlocal(4).__getattr__("getLength").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(331);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(7)).__iter__();

      while(true) {
         var1.setline(331);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(338);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_intern").__call__(var2, var1.getlocal(5)), var1.getlocal(6)});
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(8, var4);
         var1.setline(332);
         PyObject var5 = var1.getlocal(4).__getattr__("getLocalName").__call__(var2, var1.getlocal(8));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(333);
         var5 = var1.getlocal(4).__getattr__("getQName").__call__(var2, var1.getlocal(8));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(334);
         var5 = var1.getlocal(4).__getattr__("getURI").__call__(var2, var1.getlocal(8));
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(335);
         var5 = var1.getlocal(0).__getattr__("_qualify").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(336);
         var5 = var1.getlocal(4).__getattr__("getValue").__call__(var2, var1.getlocal(8));
         var1.setlocal(10, var5);
         var5 = null;
         var1.setline(337);
         var5 = var1.getlocal(10);
         var1.getlocal(6).__setitem__(var1.getlocal(9), var5);
         var5 = null;
      }
   }

   public PyObject endElement$30(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyObject var3 = var1.getlocal(0).__getattr__("_intern").__call__(var2, var1.getlocal(0).__getattr__("_qualify").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject characters$31(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyObject var3 = var1.getlocal(0).__getattr__("_char_slice_to_unicode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject characters$32(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyObject var3 = var1.getlocal(0).__getattr__("_char_slice_to_unicode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject characters$33(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyObject var3 = var1.getlocal(0).__getattr__("_entity").__getitem__(PyString.fromInterned("location"));
      PyObject var10000 = var3._eq(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("parser").__getattr__("CurrentLineNumber"), var1.getlocal(0).__getattr__("parser").__getattr__("CurrentColumnNumber")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(357);
         var3 = PyString.fromInterned("&%s;")._mod(var1.getlocal(0).__getattr__("_entity").__getitem__(PyString.fromInterned("name")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(359);
         var3 = var1.getlocal(0).__getattr__("_char_slice_to_unicode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject startPrefixMapping$34(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject endPrefixMapping$35(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject resolveEntity$36(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(375);
      var3 = var1.getlocal(0).__getattr__("parser").__getattr__("GetBase").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(376);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(4), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject resolveEntity$37(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = PyString.fromInterned("&%s;")._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject resolveEntity$38(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = PyString.fromInterned("&%s;")._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject resolveEntity$39(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setDocumentLocator$40(PyFrame var1, ThreadState var2) {
      var1.setline(393);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_locator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject skippedEntity$41(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyObject var3 = var1.getglobal("ExpatError").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(397);
      var3 = var1.getlocal(0).__getattr__("parser").__getattr__("_NextLineNumber");
      var1.getlocal(2).__setattr__("lineno", var3);
      var1.getlocal(0).__setattr__("ErrorLineNumber", var3);
      var1.setline(398);
      var3 = var1.getlocal(0).__getattr__("parser").__getattr__("_NextColumnNumber");
      var1.getlocal(2).__setattr__("offset", var3);
      var1.getlocal(0).__setattr__("ErrorColumnNumber", var3);
      var1.setline(399);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("code", var3);
      var1.getlocal(0).__setattr__("ErrorCode", var3);
      var1.setline(400);
      PyString var4 = PyString.fromInterned("undefined entity &%s;: line %s, column %s");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(401);
      var3 = var1.getlocal(3)._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2).__getattr__("lineno"), var1.getlocal(2).__getattr__("offset")}));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(402);
      var1.getlocal(2).__getattr__("__init__").__call__(var2, var1.getlocal(3));
      var1.setline(403);
      throw Py.makeException(var1.getlocal(2));
   }

   public PyObject comment$42(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyObject var3 = var1.getlocal(0).__getattr__("_char_slice_to_unicode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject startCDATA$43(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject endCDATA$44(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject startDTD$45(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("not_in_dtd", var3);
      var3 = null;
      var1.setline(422);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(423);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject endDTD$46(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("not_in_dtd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startEntity$47(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_entity", var3);
      var3 = null;
      var1.setline(431);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("parser").__getattr__("_NextLineNumber"), var1.getlocal(0).__getattr__("parser").__getattr__("_NextColumnNumber")});
      var1.getlocal(0).__getattr__("_entity").__setitem__((PyObject)PyString.fromInterned("location"), var4);
      var3 = null;
      var1.setline(433);
      PyObject var5 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("_entity").__setitem__((PyObject)PyString.fromInterned("name"), var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endEntity$48(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject notationDecl$49(PyFrame var1, ThreadState var2) {
      var1.setline(442);
      PyObject var3 = var1.getlocal(0).__getattr__("parser").__getattr__("GetBase").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(443);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(3), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject unparsedEntityDecl$50(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyObject var3 = var1.getlocal(0).__getattr__("parser").__getattr__("GetBase").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(448);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(5), var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject attributeDecl$51(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(456);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject elementDecl$52(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      PyObject var3 = var1.getlocal(0).__getattr__("_expat_content_model").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject externalEntityDecl$53(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyObject var3 = var1.getlocal(0).__getattr__("parser").__getattr__("GetBase").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(465);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(466);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(467);
      var3 = var1.getglobal("None");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(468);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6), var1.getlocal(5), var1.getlocal(4), var1.getlocal(3), var1.getlocal(2), var1.getlocal(7)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject internalEntityDecl$54(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyObject var3 = var1.getlocal(0).__getattr__("parser").__getattr__("GetBase").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(474);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(476);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(477);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(2), var1.getlocal(3), var1.getlocal(6), var1.getlocal(7), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _init_model$55(PyFrame var1, ThreadState var2) {
      var1.setline(483);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pyexpat.model"));
      var1.setglobal("model", var3);
      var3 = null;
      var1.setline(484);
      PyString var7 = PyString.fromInterned("Constants used to interpret content model information.");
      var1.getglobal("model").__setattr__((String)"__doc__", var7);
      var3 = null;
      var1.setline(485);
      var7 = PyString.fromInterned("NONE, OPT, REP, PLUS");
      var1.setlocal(0, var7);
      var3 = null;
      var1.setline(486);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(", "))).__iter__();

      while(true) {
         var1.setline(486);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         if (var4 == null) {
            var1.setline(488);
            var7 = PyString.fromInterned("EMPTY, ANY, MIXED, NAME, CHOICE, SEQ");
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(489);
            var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(", "))).__iter__();

            while(true) {
               var1.setline(489);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(1, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(490);
               var1.getglobal("setattr").__call__(var2, var1.getglobal("model"), PyString.fromInterned("XML_CTYPE_")._add(var1.getlocal(4)), var1.getlocal(1)._add(Py.newInteger(1)));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(487);
         var1.getglobal("setattr").__call__(var2, var1.getglobal("model"), PyString.fromInterned("XML_CQUANT_")._add(var1.getlocal(2)), var1.getlocal(1));
      }
   }

   public PyObject ExpatError$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(497);
      return var1.getf_locals();
   }

   public PyObject _init_error_strings$57(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("out of memory"), PyString.fromInterned("syntax error"), PyString.fromInterned("no element found"), PyString.fromInterned("not well-formed (invalid token)"), PyString.fromInterned("unclosed token"), PyString.fromInterned("partial character"), PyString.fromInterned("mismatched tag"), PyString.fromInterned("duplicate attribute"), PyString.fromInterned("junk after document element"), PyString.fromInterned("illegal parameter entity reference"), PyString.fromInterned("undefined entity"), PyString.fromInterned("recursive entity reference"), PyString.fromInterned("asynchronous entity"), PyString.fromInterned("reference to invalid character number"), PyString.fromInterned("reference to binary entity"), PyString.fromInterned("reference to external entity in attribute"), PyString.fromInterned("XML or text declaration not at start of entity"), PyString.fromInterned("unknown encoding"), PyString.fromInterned("encoding specified in XML declaration is incorrect"), PyString.fromInterned("unclosed CDATA section"), PyString.fromInterned("error in processing external entity reference"), PyString.fromInterned("document is not standalone"), PyString.fromInterned("unexpected parser state - please send a bug report"), PyString.fromInterned("entity declared in parameter entity"), PyString.fromInterned("requested feature requires XML_DTD support in Expat"), PyString.fromInterned("cannot change setting once parsing has begun"), PyString.fromInterned("unbound prefix"), PyString.fromInterned("must not undeclare prefix"), PyString.fromInterned("incomplete markup in parameter entity"), PyString.fromInterned("XML declaration not well-formed"), PyString.fromInterned("text declaration not well-formed"), PyString.fromInterned("illegal character(s) in public id"), PyString.fromInterned("parser suspended"), PyString.fromInterned("parser not suspended"), PyString.fromInterned("parsing aborted"), PyString.fromInterned("parsing finished"), PyString.fromInterned("cannot suspend in external parameter entity")});
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(544);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = ErrorString$58;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setglobal("ErrorString", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ErrorString$58(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(546);
         var3 = var1.getderef(0).__getitem__(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("IndexError"))) {
            var1.setline(548);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _init_errors$59(PyFrame var1, ThreadState var2) {
      var1.setline(557);
      PyObject var3 = var1.getglobal("types").__getattr__("ModuleType").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pyexpat.errors"));
      var1.setglobal("errors", var3);
      var3 = null;
      var1.setline(558);
      PyString var7 = PyString.fromInterned("Constants used to describe error conditions.");
      var1.getglobal("errors").__setattr__((String)"__doc__", var7);
      var3 = null;
      var1.setline(560);
      var7 = PyString.fromInterned("\n    XML_ERROR_NONE\n    XML_ERROR_NONE,\n    XML_ERROR_NO_MEMORY,\n    XML_ERROR_SYNTAX,\n    XML_ERROR_NO_ELEMENTS,\n    XML_ERROR_INVALID_TOKEN,\n    XML_ERROR_UNCLOSED_TOKEN,\n    XML_ERROR_PARTIAL_CHAR,\n    XML_ERROR_TAG_MISMATCH,\n    XML_ERROR_DUPLICATE_ATTRIBUTE,\n    XML_ERROR_JUNK_AFTER_DOC_ELEMENT,\n    XML_ERROR_PARAM_ENTITY_REF,\n    XML_ERROR_UNDEFINED_ENTITY,\n    XML_ERROR_RECURSIVE_ENTITY_REF,\n    XML_ERROR_ASYNC_ENTITY,\n    XML_ERROR_BAD_CHAR_REF,\n    XML_ERROR_BINARY_ENTITY_REF,\n    XML_ERROR_ATTRIBUTE_EXTERNAL_ENTITY_REF,\n    XML_ERROR_MISPLACED_XML_PI,\n    XML_ERROR_UNKNOWN_ENCODING,\n    XML_ERROR_INCORRECT_ENCODING,\n    XML_ERROR_UNCLOSED_CDATA_SECTION,\n    XML_ERROR_EXTERNAL_ENTITY_HANDLING,\n    XML_ERROR_NOT_STANDALONE,\n    XML_ERROR_UNEXPECTED_STATE,\n    XML_ERROR_ENTITY_DECLARED_IN_PE,\n    XML_ERROR_FEATURE_REQUIRES_XML_DTD,\n    XML_ERROR_CANT_CHANGE_FEATURE_ONCE_PARSING,\n    XML_ERROR_UNBOUND_PREFIX,\n    XML_ERROR_UNDECLARING_PREFIX,\n    XML_ERROR_INCOMPLETE_PE,\n    XML_ERROR_XML_DECL,\n    XML_ERROR_TEXT_DECL,\n    XML_ERROR_PUBLICID,\n    XML_ERROR_SUSPENDED,\n    XML_ERROR_NOT_SUSPENDED,\n    XML_ERROR_ABORTED,\n    XML_ERROR_FINISHED,\n    XML_ERROR_SUSPEND_PE\n    ");
      var1.setlocal(0, var7);
      var3 = null;
      var1.setline(601);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(601);
      var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

      while(true) {
         var1.setline(601);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(601);
            var1.dellocal(1);
            PyList var8 = var10000;
            var1.setlocal(0, var8);
            var3 = null;
            var1.setline(602);
            var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)).__iter__();

            while(true) {
               var1.setline(602);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(2, var6);
               var6 = null;
               var1.setline(603);
               var1.getglobal("setattr").__call__(var2, var1.getglobal("errors"), var1.getlocal(2), var1.getglobal("ErrorString").__call__(var2, var1.getlocal(3)._add(Py.newInteger(1))));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(601);
         var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("strip").__call__(var2));
      }
   }

   public expat$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"encoding", "namespace_separator"};
      ParserCreate$1 = Py.newCode(2, var2, var1, "ParserCreate", 58, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      XMLParser$2 = Py.newCode(0, var2, var1, "XMLParser", 62, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "encoding", "namespace_separator", "error", "feature", "sax_properties", "name", "apache_features", "value", "f"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 64, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      GetBase$4 = Py.newCode(1, var2, var1, "GetBase", 141, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "base"};
      SetBase$5 = Py.newCode(2, var2, var1, "SetBase", 144, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _error$6 = Py.newCode(2, var2, var1, "_error", 147, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_buffer_text$7 = Py.newCode(1, var2, var1, "_get_buffer_text", 150, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _set_buffer_text$8 = Py.newCode(2, var2, var1, "_set_buffer_text", 153, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_returns_unicode$9 = Py.newCode(1, var2, var1, "_get_returns_unicode", 156, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _set_returns_unicode$10 = Py.newCode(2, var2, var1, "_set_returns_unicode", 159, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$11 = Py.newCode(1, var2, var1, "<lambda>", 168, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$12 = Py.newCode(1, var2, var1, "<lambda>", 169, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sax_error", "sax_message", "pattern", "expat_message", "error"};
      _expat_error$13 = Py.newCode(2, var2, var1, "_expat_error", 173, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "isfinal", "bytes", "byte_stream", "source", "sax_error"};
      Parse$14 = Py.newCode(3, var2, var1, "Parse", 187, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      ParseFile$15 = Py.newCode(2, var2, var1, "ParseFile", 215, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"arg", "encoding", "iterator", "_(232_25)"};
      String[] var10001 = var2;
      expat$py var10007 = self;
      var2 = new String[]{"encoding"};
      _encode$16 = Py.newCode(2, var10001, var1, "_encode", 224, false, false, var10007, 16, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "_arg"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"encoding"};
      f$17 = Py.newCode(1, var10001, var1, "<genexpr>", 232, false, false, var10007, 17, (String[])null, var2, 0, 4129);
      var2 = new String[]{"callback", "guard", "force", "returns", "_expat"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"force", "guard", "returns", "callback"};
      expat$18 = Py.newCode(4, var10001, var1, "expat", 235, false, false, var10007, 18, var2, (String[])null, 0, 4097);
      var2 = new String[]{"method", "context", "append", "new_method", "name", "key"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"name", "key"};
      String[] var10009 = var2;
      var2 = new String[]{"callback", "guard", "force", "returns"};
      _expat$19 = Py.newCode(1, var10001, var1, "_expat", 236, false, false, var10007, 19, var10009, var2, 2, 4097);
      var2 = new String[]{"args", "self", "parser", "method", "callback", "guard", "force", "returns", "_callback", "results"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"name", "key"};
      new_method$20 = Py.newCode(1, var10001, var1, "new_method", 243, true, false, var10007, 20, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      XMLEventHandler$21 = Py.newCode(0, var2, var1, "XMLEventHandler", 268, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "parser"};
      __init__$22 = Py.newCode(2, var2, var1, "__init__", 270, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      _intern$23 = Py.newCode(2, var2, var1, "_intern", 279, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "local_name", "qname", "namespace", "namespace_separator"};
      _qualify$24 = Py.newCode(4, var2, var1, "_qualify", 282, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "characters", "start", "length", "text"};
      _char_slice_to_unicode$25 = Py.newCode(4, var2, var1, "_char_slice_to_unicode", 291, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "model_"};
      _expat_content_model$26 = Py.newCode(3, var2, var1, "_expat_content_model", 296, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event", "parser", "locator"};
      _update_location$27 = Py.newCode(2, var2, var1, "_update_location", 300, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data"};
      processingInstruction$28 = Py.newCode(3, var2, var1, "processingInstruction", 322, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespace", "local_name", "qname", "attributes", "tag", "attribs", "length", "index", "name", "value"};
      startElement$29 = Py.newCode(5, var2, var1, "startElement", 326, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "namespace", "local_name", "qname"};
      endElement$30 = Py.newCode(4, var2, var1, "endElement", 340, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "characters", "start", "length"};
      characters$31 = Py.newCode(4, var2, var1, "characters", 344, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "characters", "start", "length"};
      characters$32 = Py.newCode(4, var2, var1, "characters", 348, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "characters", "start", "length"};
      characters$33 = Py.newCode(4, var2, var1, "characters", 352, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      startPrefixMapping$34 = Py.newCode(3, var2, var1, "startPrefixMapping", 361, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      endPrefixMapping$35 = Py.newCode(2, var2, var1, "endPrefixMapping", 365, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "baseURI", "systemId", "context", "base"};
      resolveEntity$36 = Py.newCode(5, var2, var1, "resolveEntity", 371, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "baseURI", "systemId"};
      resolveEntity$37 = Py.newCode(5, var2, var1, "resolveEntity", 378, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "baseURI", "systemId"};
      resolveEntity$38 = Py.newCode(5, var2, var1, "resolveEntity", 383, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "baseURI", "systemId"};
      resolveEntity$39 = Py.newCode(5, var2, var1, "resolveEntity", 388, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locator"};
      setDocumentLocator$40 = Py.newCode(2, var2, var1, "setDocumentLocator", 392, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "error", "message"};
      skippedEntity$41 = Py.newCode(2, var2, var1, "skippedEntity", 395, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "characters", "start", "length"};
      comment$42 = Py.newCode(4, var2, var1, "comment", 407, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startCDATA$43 = Py.newCode(1, var2, var1, "startCDATA", 411, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endCDATA$44 = Py.newCode(1, var2, var1, "endCDATA", 415, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "has_internal_subset"};
      startDTD$45 = Py.newCode(4, var2, var1, "startDTD", 419, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDTD$46 = Py.newCode(1, var2, var1, "endDTD", 425, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      startEntity$47 = Py.newCode(2, var2, var1, "startEntity", 429, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endEntity$48 = Py.newCode(2, var2, var1, "endEntity", 435, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "base"};
      notationDecl$49 = Py.newCode(4, var2, var1, "notationDecl", 440, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "notationName", "base"};
      unparsedEntityDecl$50 = Py.newCode(5, var2, var1, "unparsedEntityDecl", 445, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "eName", "aName", "type", "mode", "value", "required"};
      attributeDecl$51 = Py.newCode(6, var2, var1, "attributeDecl", 452, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "model"};
      elementDecl$52 = Py.newCode(3, var2, var1, "elementDecl", 458, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "publicId", "systemId", "base", "value", "is_parameter_entity", "notation_name"};
      externalEntityDecl$53 = Py.newCode(4, var2, var1, "externalEntityDecl", 462, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "base", "is_parameter_entity", "notation_name", "systemId", "publicId"};
      internalEntityDecl$54 = Py.newCode(3, var2, var1, "internalEntityDecl", 471, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"quantifiers", "i", "quantifier", "types_", "type_"};
      _init_model$55 = Py.newCode(0, var2, var1, "_init_model", 481, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExpatError$56 = Py.newCode(0, var2, var1, "ExpatError", 496, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"error_strings"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"error_strings"};
      _init_error_strings$57 = Py.newCode(0, var10001, var1, "_init_error_strings", 503, false, false, var10007, 57, var2, (String[])null, 1, 4097);
      var2 = new String[]{"code"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"error_strings"};
      ErrorString$58 = Py.newCode(1, var10001, var1, "ErrorString", 544, false, false, var10007, 58, (String[])null, var2, 0, 4097);
      var2 = new String[]{"error_names", "_[601_19]", "name", "i"};
      _init_errors$59 = Py.newCode(0, var2, var1, "_init_errors", 554, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new expat$py("xml/parsers/expat$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(expat$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ParserCreate$1(var2, var3);
         case 2:
            return this.XMLParser$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.GetBase$4(var2, var3);
         case 5:
            return this.SetBase$5(var2, var3);
         case 6:
            return this._error$6(var2, var3);
         case 7:
            return this._get_buffer_text$7(var2, var3);
         case 8:
            return this._set_buffer_text$8(var2, var3);
         case 9:
            return this._get_returns_unicode$9(var2, var3);
         case 10:
            return this._set_returns_unicode$10(var2, var3);
         case 11:
            return this.f$11(var2, var3);
         case 12:
            return this.f$12(var2, var3);
         case 13:
            return this._expat_error$13(var2, var3);
         case 14:
            return this.Parse$14(var2, var3);
         case 15:
            return this.ParseFile$15(var2, var3);
         case 16:
            return this._encode$16(var2, var3);
         case 17:
            return this.f$17(var2, var3);
         case 18:
            return this.expat$18(var2, var3);
         case 19:
            return this._expat$19(var2, var3);
         case 20:
            return this.new_method$20(var2, var3);
         case 21:
            return this.XMLEventHandler$21(var2, var3);
         case 22:
            return this.__init__$22(var2, var3);
         case 23:
            return this._intern$23(var2, var3);
         case 24:
            return this._qualify$24(var2, var3);
         case 25:
            return this._char_slice_to_unicode$25(var2, var3);
         case 26:
            return this._expat_content_model$26(var2, var3);
         case 27:
            return this._update_location$27(var2, var3);
         case 28:
            return this.processingInstruction$28(var2, var3);
         case 29:
            return this.startElement$29(var2, var3);
         case 30:
            return this.endElement$30(var2, var3);
         case 31:
            return this.characters$31(var2, var3);
         case 32:
            return this.characters$32(var2, var3);
         case 33:
            return this.characters$33(var2, var3);
         case 34:
            return this.startPrefixMapping$34(var2, var3);
         case 35:
            return this.endPrefixMapping$35(var2, var3);
         case 36:
            return this.resolveEntity$36(var2, var3);
         case 37:
            return this.resolveEntity$37(var2, var3);
         case 38:
            return this.resolveEntity$38(var2, var3);
         case 39:
            return this.resolveEntity$39(var2, var3);
         case 40:
            return this.setDocumentLocator$40(var2, var3);
         case 41:
            return this.skippedEntity$41(var2, var3);
         case 42:
            return this.comment$42(var2, var3);
         case 43:
            return this.startCDATA$43(var2, var3);
         case 44:
            return this.endCDATA$44(var2, var3);
         case 45:
            return this.startDTD$45(var2, var3);
         case 46:
            return this.endDTD$46(var2, var3);
         case 47:
            return this.startEntity$47(var2, var3);
         case 48:
            return this.endEntity$48(var2, var3);
         case 49:
            return this.notationDecl$49(var2, var3);
         case 50:
            return this.unparsedEntityDecl$50(var2, var3);
         case 51:
            return this.attributeDecl$51(var2, var3);
         case 52:
            return this.elementDecl$52(var2, var3);
         case 53:
            return this.externalEntityDecl$53(var2, var3);
         case 54:
            return this.internalEntityDecl$54(var2, var3);
         case 55:
            return this._init_model$55(var2, var3);
         case 56:
            return this.ExpatError$56(var2, var3);
         case 57:
            return this._init_error_strings$57(var2, var3);
         case 58:
            return this.ErrorString$58(var2, var3);
         case 59:
            return this._init_errors$59(var2, var3);
         default:
            return null;
      }
   }
}
