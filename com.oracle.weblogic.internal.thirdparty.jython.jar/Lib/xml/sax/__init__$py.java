package xml;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/sax/__init__.py")
public class sax$py extends PyFunctionTable implements PyRunnable {
   static sax$py self;
   static final PyCode f$0;
   static final PyCode parse$1;
   static final PyCode parseString$2;
   static final PyCode make_parser$3;
   static final PyCode _create_parser$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Simple API for XML (SAX) implementation for Python.\n\nThis module provides an implementation of the SAX 2 interface;\ninformation about the Java version of the interface can be found at\nhttp://www.megginson.com/SAX/.  The Python version of the interface is\ndocumented at <...>.\n\nThis package contains the following modules:\n\nhandler -- Base classes and constants which define the SAX 2 API for\n           the 'client-side' of SAX for Python.\n\nsaxutils -- Implementation of the convenience classes commonly used to\n            work with SAX.\n\nxmlreader -- Base classes and constants which define the SAX 2 API for\n             the parsers used with SAX for Python.\n\ndrivers2 -- Contains the driver for that wraps a Java sax implementation in python\n            objects.\n"));
      var1.setline(21);
      PyString.fromInterned("Simple API for XML (SAX) implementation for Python.\n\nThis module provides an implementation of the SAX 2 interface;\ninformation about the Java version of the interface can be found at\nhttp://www.megginson.com/SAX/.  The Python version of the interface is\ndocumented at <...>.\n\nThis package contains the following modules:\n\nhandler -- Base classes and constants which define the SAX 2 API for\n           the 'client-side' of SAX for Python.\n\nsaxutils -- Implementation of the convenience classes commonly used to\n            work with SAX.\n\nxmlreader -- Base classes and constants which define the SAX 2 API for\n             the parsers used with SAX for Python.\n\ndrivers2 -- Contains the driver for that wraps a Java sax implementation in python\n            objects.\n");
      var1.setline(23);
      String[] var3 = new String[]{"InputSource"};
      PyObject[] var5 = imp.importFrom("xmlreader", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("InputSource", var4);
      var4 = null;
      var1.setline(24);
      var3 = new String[]{"ContentHandler", "ErrorHandler"};
      var5 = imp.importFrom("handler", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("ContentHandler", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("ErrorHandler", var4);
      var4 = null;
      var1.setline(25);
      var3 = new String[]{"SAXException", "SAXNotRecognizedException", "SAXParseException", "SAXNotSupportedException", "SAXReaderNotAvailable"};
      var5 = imp.importFrom("_exceptions", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("SAXException", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("SAXNotRecognizedException", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("SAXParseException", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("SAXNotSupportedException", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("SAXReaderNotAvailable", var4);
      var4 = null;
      var1.setline(30);
      var5 = new PyObject[]{var1.getname("ErrorHandler").__call__(var2)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, parse$1, (PyObject)null);
      var1.setlocal("parse", var6);
      var3 = null;
      var1.setline(36);
      var5 = new PyObject[]{var1.getname("ErrorHandler").__call__(var2)};
      var6 = new PyFunction(var1.f_globals, var5, parseString$2, (PyObject)null);
      var1.setlocal("parseString", var6);
      var3 = null;
      var1.setline(55);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("xml.sax.drivers2.drv_javasax")});
      var1.setlocal("default_parser_list", var7);
      var3 = null;
      var1.setline(58);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal("_false", var8);
      var3 = null;
      var1.setline(59);
      PyObject var9;
      if (var1.getname("_false").__nonzero__()) {
         var1.setline(60);
         var9 = imp.importOne("xml.sax.drivers2.drv_javasax", var1, -1);
         var1.setlocal("xml", var9);
         var3 = null;
      }

      var1.setline(62);
      var9 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var9);
      var3 = null;
      var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(63);
      if (var1.getname("os").__getattr__("environ").__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PY_SAX_PARSER")).__nonzero__()) {
         var1.setline(64);
         var9 = var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("PY_SAX_PARSER")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal("default_parser_list", var9);
         var3 = null;
      }

      var1.setline(65);
      var1.dellocal("os");
      var1.setline(67);
      PyString var10 = PyString.fromInterned("python.xml.sax.parser");
      var1.setlocal("_key", var10);
      var3 = null;
      var1.setline(68);
      var9 = var1.getname("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var9._eq(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getname("sys").__getattr__("registry").__getattr__("containsKey").__call__(var2, var1.getname("_key"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(69);
         var9 = var1.getname("sys").__getattr__("registry").__getattr__("getProperty").__call__(var2, var1.getname("_key")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal("default_parser_list", var9);
         var3 = null;
      }

      var1.setline(72);
      var5 = new PyObject[]{new PyList(Py.EmptyObjects)};
      var6 = new PyFunction(var1.f_globals, var5, make_parser$3, PyString.fromInterned("Creates and returns a SAX parser.\n\n    Creates the first parser it is able to instantiate of the ones\n    given in the list created by doing parser_list +\n    default_parser_list.  The lists must contain the names of Python\n    modules containing both a SAX parser and a create_parser function."));
      var1.setlocal("make_parser", var6);
      var3 = null;
      var1.setline(98);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _create_parser$4, (PyObject)null);
      var1.setlocal("_create_parser", var6);
      var3 = null;
      var1.setline(102);
      var1.dellocal("sys");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$1(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getglobal("make_parser").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(32);
      var1.getlocal(3).__getattr__("setContentHandler").__call__(var2, var1.getlocal(1));
      var1.setline(33);
      var1.getlocal(3).__getattr__("setErrorHandler").__call__(var2, var1.getlocal(2));
      var1.setline(34);
      var1.getlocal(3).__getattr__("parse").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseString$2(PyFrame var1, ThreadState var2) {
      PyException var3;
      String[] var4;
      try {
         var1.setline(38);
         String[] var7 = new String[]{"StringIO"};
         PyObject[] var8 = imp.importFrom("cStringIO", var7, var1, -1);
         PyObject var11 = var8[0];
         var1.setlocal(3, var11);
         var4 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(40);
         var4 = new String[]{"StringIO"};
         PyObject[] var10 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal(3, var5);
         var5 = null;
      }

      var1.setline(42);
      PyObject var9 = var1.getlocal(2);
      PyObject var10000 = var9._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(43);
         var9 = var1.getglobal("ErrorHandler").__call__(var2);
         var1.setlocal(2, var9);
         var3 = null;
      }

      var1.setline(44);
      var9 = var1.getglobal("make_parser").__call__(var2);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(45);
      var1.getlocal(4).__getattr__("setContentHandler").__call__(var2, var1.getlocal(1));
      var1.setline(46);
      var1.getlocal(4).__getattr__("setErrorHandler").__call__(var2, var1.getlocal(2));
      var1.setline(48);
      var9 = var1.getglobal("InputSource").__call__(var2);
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(49);
      var1.getlocal(5).__getattr__("setByteStream").__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(0)));
      var1.setline(50);
      var1.getlocal(4).__getattr__("parse").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_parser$3(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyString.fromInterned("Creates and returns a SAX parser.\n\n    Creates the first parser it is able to instantiate of the ones\n    given in the list created by doing parser_list +\n    default_parser_list.  The lists must contain the names of Python\n    modules containing both a SAX parser and a create_parser function.");
      var1.setline(80);
      PyObject var3 = var1.getlocal(0)._add(var1.getglobal("default_parser_list")).__iter__();

      while(true) {
         var1.setline(80);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(94);
            throw Py.makeException(var1.getglobal("SAXReaderNotAvailable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No parsers found"), (PyObject)var1.getglobal("None")));
         }

         var1.setlocal(1, var4);

         try {
            var1.setline(82);
            PyObject var5 = var1.getglobal("_create_parser").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var5;
         } catch (Throwable var8) {
            PyException var6 = Py.setException(var8, var1);
            if (var6.match(var1.getglobal("ImportError"))) {
               PyObject var7 = var6.value;
               var1.setlocal(2, var7);
               var7 = null;
               var1.setline(84);
               var7 = imp.importOne("sys", var1, -1);
               var1.setlocal(3, var7);
               var7 = null;
               var1.setline(85);
               if (var1.getlocal(3).__getattr__("modules").__getattr__("has_key").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                  var1.setline(88);
                  throw Py.makeException();
               }
            } else {
               if (!var6.match(var1.getglobal("SAXReaderNotAvailable"))) {
                  throw var6;
               }

               var1.setline(92);
            }
         }
      }
   }

   public PyObject _create_parser$4(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(0), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyList(new PyObject[]{PyString.fromInterned("create_parser")}));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(1).__getattr__("create_parser").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public sax$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"source", "handler", "errorHandler", "parser"};
      parse$1 = Py.newCode(3, var2, var1, "parse", 30, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "handler", "errorHandler", "StringIO", "parser", "inpsrc"};
      parseString$2 = Py.newCode(3, var2, var1, "parseString", 36, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"parser_list", "parser_name", "e", "sys"};
      make_parser$3 = Py.newCode(1, var2, var1, "make_parser", 72, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"parser_name", "drv_module"};
      _create_parser$4 = Py.newCode(1, var2, var1, "_create_parser", 98, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sax$py("xml/sax$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sax$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.parse$1(var2, var3);
         case 2:
            return this.parseString$2(var2, var3);
         case 3:
            return this.make_parser$3(var2, var3);
         case 4:
            return this._create_parser$4(var2, var3);
         default:
            return null;
      }
   }
}
