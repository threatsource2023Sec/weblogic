package logging;

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
@Filename("logging/config.py")
public class config$py extends PyFunctionTable implements PyRunnable {
   static config$py self;
   static final PyCode f$0;
   static final PyCode fileConfig$1;
   static final PyCode _resolve$2;
   static final PyCode _strip_spaces$3;
   static final PyCode f$4;
   static final PyCode _encoded$5;
   static final PyCode _create_formatters$6;
   static final PyCode _install_handlers$7;
   static final PyCode _install_loggers$8;
   static final PyCode f$9;
   static final PyCode valid_ident$10;
   static final PyCode ConvertingDict$11;
   static final PyCode __getitem__$12;
   static final PyCode get$13;
   static final PyCode pop$14;
   static final PyCode ConvertingList$15;
   static final PyCode __getitem__$16;
   static final PyCode pop$17;
   static final PyCode ConvertingTuple$18;
   static final PyCode __getitem__$19;
   static final PyCode BaseConfigurator$20;
   static final PyCode __init__$21;
   static final PyCode resolve$22;
   static final PyCode ext_convert$23;
   static final PyCode cfg_convert$24;
   static final PyCode convert$25;
   static final PyCode configure_custom$26;
   static final PyCode as_tuple$27;
   static final PyCode DictConfigurator$28;
   static final PyCode configure$29;
   static final PyCode configure_formatter$30;
   static final PyCode configure_filter$31;
   static final PyCode add_filters$32;
   static final PyCode configure_handler$33;
   static final PyCode add_handlers$34;
   static final PyCode common_logger_config$35;
   static final PyCode configure_logger$36;
   static final PyCode configure_root$37;
   static final PyCode dictConfig$38;
   static final PyCode listen$39;
   static final PyCode ConfigStreamHandler$40;
   static final PyCode handle$41;
   static final PyCode ConfigSocketReceiver$42;
   static final PyCode __init__$43;
   static final PyCode serve_until_stopped$44;
   static final PyCode Server$45;
   static final PyCode __init__$46;
   static final PyCode run$47;
   static final PyCode stopListening$48;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nConfiguration functions for the logging package for Python. The core package\nis based on PEP 282 and comments thereto in comp.lang.python, and influenced\nby Apache's log4j system.\n\nCopyright (C) 2001-2010 Vinay Sajip. All Rights Reserved.\n\nTo use, simply 'import logging' and log away!\n"));
      var1.setline(25);
      PyString.fromInterned("\nConfiguration functions for the logging package for Python. The core package\nis based on PEP 282 and comments thereto in comp.lang.python, and influenced\nby Apache's log4j system.\n\nCopyright (C) 2001-2010 Vinay Sajip. All Rights Reserved.\n\nTo use, simply 'import logging' and log away!\n");
      var1.setline(27);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var3 = imp.importOne("logging.handlers", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(28);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var3 = imp.importOne("cStringIO", var1, -1);
      var1.setlocal("cStringIO", var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(31);
         var3 = imp.importOne("thread", var1, -1);
         var1.setlocal("thread", var3);
         var3 = null;
         var1.setline(32);
         var3 = imp.importOne("threading", var1, -1);
         var1.setlocal("threading", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getname("ImportError"))) {
            throw var6;
         }

         var1.setline(34);
         var4 = var1.getname("None");
         var1.setlocal("thread", var4);
         var4 = null;
      }

      var1.setline(36);
      String[] var7 = new String[]{"ThreadingTCPServer", "StreamRequestHandler"};
      PyObject[] var8 = imp.importFrom("SocketServer", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("ThreadingTCPServer", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("StreamRequestHandler", var4);
      var4 = null;
      var1.setline(39);
      PyInteger var9 = Py.newInteger(9030);
      var1.setlocal("DEFAULT_LOGGING_CONFIG_PORT", var9);
      var3 = null;
      var1.setline(41);
      var3 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(42);
         var9 = Py.newInteger(10054);
         var1.setlocal("RESET_ERROR", var9);
         var3 = null;
      } else {
         var1.setline(44);
         var9 = Py.newInteger(104);
         var1.setlocal("RESET_ERROR", var9);
         var3 = null;
      }

      var1.setline(51);
      var3 = var1.getname("None");
      var1.setlocal("_listener", var3);
      var3 = null;
      var1.setline(53);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var10 = new PyFunction(var1.f_globals, var8, fileConfig$1, PyString.fromInterned("\n    Read the logging configuration from a ConfigParser-format file.\n\n    This can be called several times from an application, allowing an end user\n    the ability to select from various pre-canned configurations (if the\n    developer provides a mechanism to present the choices and load the chosen\n    configuration).\n    "));
      var1.setlocal("fileConfig", var10);
      var3 = null;
      var1.setline(84);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _resolve$2, PyString.fromInterned("Resolve a dotted name to a global object."));
      var1.setlocal("_resolve", var10);
      var3 = null;
      var1.setline(98);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _strip_spaces$3, (PyObject)null);
      var1.setlocal("_strip_spaces", var10);
      var3 = null;
      var1.setline(101);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _encoded$5, (PyObject)null);
      var1.setlocal("_encoded", var10);
      var3 = null;
      var1.setline(104);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _create_formatters$6, PyString.fromInterned("Create and return formatters"));
      var1.setlocal("_create_formatters", var10);
      var3 = null;
      var1.setline(133);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _install_handlers$7, PyString.fromInterned("Install and return handlers"));
      var1.setlocal("_install_handlers", var10);
      var3 = null;
      var1.setline(176);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _install_loggers$8, PyString.fromInterned("Create and install loggers"));
      var1.setlocal("_install_loggers", var10);
      var3 = null;
      var1.setline(268);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[a-z_][a-z0-9_]*$"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("IDENTIFIER", var3);
      var3 = null;
      var1.setline(271);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, valid_ident$10, (PyObject)null);
      var1.setlocal("valid_ident", var10);
      var3 = null;
      var1.setline(287);
      var8 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("ConvertingDict", var8, ConvertingDict$11);
      var1.setlocal("ConvertingDict", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(324);
      var8 = new PyObject[]{var1.getname("list")};
      var4 = Py.makeClass("ConvertingList", var8, ConvertingList$15);
      var1.setlocal("ConvertingList", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(347);
      var8 = new PyObject[]{var1.getname("tuple")};
      var4 = Py.makeClass("ConvertingTuple", var8, ConvertingTuple$18);
      var1.setlocal("ConvertingTuple", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(359);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BaseConfigurator", var8, BaseConfigurator$20);
      var1.setlocal("BaseConfigurator", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(492);
      var8 = new PyObject[]{var1.getname("BaseConfigurator")};
      var4 = Py.makeClass("DictConfigurator", var8, DictConfigurator$28);
      var1.setlocal("DictConfigurator", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(773);
      var3 = var1.getname("DictConfigurator");
      var1.setlocal("dictConfigClass", var3);
      var3 = null;
      var1.setline(775);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, dictConfig$38, PyString.fromInterned("Configure logging using a dictionary."));
      var1.setlocal("dictConfig", var10);
      var3 = null;
      var1.setline(780);
      var8 = new PyObject[]{var1.getname("DEFAULT_LOGGING_CONFIG_PORT")};
      var10 = new PyFunction(var1.f_globals, var8, listen$39, PyString.fromInterned("\n    Start up a socket server on the specified port, and listen for new\n    configurations.\n\n    These will be sent as a file suitable for processing by fileConfig().\n    Returns a Thread object on which you can call start() to start the server,\n    and which you can join() when appropriate. To stop the server, call\n    stopListening().\n    "));
      var1.setlocal("listen", var10);
      var3 = null;
      var1.setline(895);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, stopListening$48, PyString.fromInterned("\n    Stop the listening server which was created with a call to listen().\n    "));
      var1.setlocal("stopListening", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileConfig$1(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned("\n    Read the logging configuration from a ConfigParser-format file.\n\n    This can be called several times from an application, allowing an end user\n    the ability to select from various pre-canned configurations (if the\n    developer provides a mechanism to present the choices and load the chosen\n    configuration).\n    ");
      var1.setline(62);
      PyObject var3 = imp.importOne("ConfigParser", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(3).__getattr__("ConfigParser").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(65);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("readline")).__nonzero__()) {
         var1.setline(66);
         var1.getlocal(4).__getattr__("readfp").__call__(var2, var1.getlocal(0));
      } else {
         var1.setline(68);
         var1.getlocal(4).__getattr__("read").__call__(var2, var1.getlocal(0));
      }

      var1.setline(70);
      var3 = var1.getglobal("_create_formatters").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(73);
      var1.getglobal("logging").__getattr__("_acquireLock").__call__(var2);
      var3 = null;

      try {
         var1.setline(75);
         var1.getglobal("logging").__getattr__("_handlers").__getattr__("clear").__call__(var2);
         var1.setline(76);
         var1.getglobal("logging").__getattr__("_handlerList").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setline(78);
         PyObject var4 = var1.getglobal("_install_handlers").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(79);
         var1.getglobal("_install_loggers").__call__(var2, var1.getlocal(4), var1.getlocal(6), var1.getlocal(2));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(81);
         var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(81);
      var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _resolve$2(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Resolve a dotted name to a global object.");
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(89);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(96);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(90);
         PyObject var5 = var1.getlocal(1)._add(PyString.fromInterned("."))._add(var1.getlocal(3));
         var1.setlocal(1, var5);
         var5 = null;

         try {
            var1.setline(92);
            var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(2, var5);
            var5 = null;
         } catch (Throwable var7) {
            PyException var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("AttributeError"))) {
               throw var8;
            }

            var1.setline(94);
            var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
            var1.setline(95);
            PyObject var6 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(2, var6);
            var6 = null;
         }
      }
   }

   public PyObject _strip_spaces$3(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(99);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$4)), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$4(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _encoded$5(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      var1.setline(102);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__() ? var1.getlocal(0) : var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _create_formatters$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Create and return formatters");
      var1.setline(106);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("formatters"), (PyObject)PyString.fromInterned("keys"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(107);
      if (var1.getglobal("len").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(108);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(109);
         PyObject var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(110);
         var4 = var1.getglobal("_strip_spaces").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(111);
         PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var8);
         var4 = null;
         var1.setline(112);
         var4 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(112);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(130);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(113);
            PyObject var6 = PyString.fromInterned("formatter_%s")._mod(var1.getlocal(3));
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(114);
            var6 = var1.getlocal(0).__getattr__("options").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(115);
            PyString var9 = PyString.fromInterned("format");
            PyObject var10000 = var9._in(var1.getlocal(5));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(116);
               var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("format"), (PyObject)Py.newInteger(1));
               var1.setlocal(6, var6);
               var6 = null;
            } else {
               var1.setline(118);
               var6 = var1.getglobal("None");
               var1.setlocal(6, var6);
               var6 = null;
            }

            var1.setline(119);
            var9 = PyString.fromInterned("datefmt");
            var10000 = var9._in(var1.getlocal(5));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(120);
               var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("datefmt"), (PyObject)Py.newInteger(1));
               var1.setlocal(7, var6);
               var6 = null;
            } else {
               var1.setline(122);
               var6 = var1.getglobal("None");
               var1.setlocal(7, var6);
               var6 = null;
            }

            var1.setline(123);
            var6 = var1.getglobal("logging").__getattr__("Formatter");
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(124);
            var9 = PyString.fromInterned("class");
            var10000 = var9._in(var1.getlocal(5));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(125);
               var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("class"));
               var1.setlocal(9, var6);
               var6 = null;
               var1.setline(126);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(127);
                  var6 = var1.getglobal("_resolve").__call__(var2, var1.getlocal(9));
                  var1.setlocal(8, var6);
                  var6 = null;
               }
            }

            var1.setline(128);
            var6 = var1.getlocal(8).__call__(var2, var1.getlocal(6), var1.getlocal(7));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(129);
            var6 = var1.getlocal(10);
            var1.getlocal(2).__setitem__(var1.getlocal(3), var6);
            var6 = null;
         }
      }
   }

   public PyObject _install_handlers$7(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("Install and return handlers");
      var1.setline(135);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("handlers"), (PyObject)PyString.fromInterned("keys"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(136);
      if (var1.getglobal("len").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(137);
         PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(138);
         PyObject var4 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(139);
         var4 = var1.getglobal("_strip_spaces").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(140);
         PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(3, var10);
         var4 = null;
         var1.setline(141);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var11);
         var4 = null;
         var1.setline(142);
         var4 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(142);
            PyObject var5 = var4.__iternext__();
            PyObject var7;
            PyObject[] var15;
            if (var5 == null) {
               var1.setline(171);
               var4 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(171);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(173);
                     var3 = var1.getlocal(3);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var15 = Py.unpackSequence(var5, 2);
                  var7 = var15[0];
                  var1.setlocal(11, var7);
                  var7 = null;
                  var7 = var15[1];
                  var1.setlocal(14, var7);
                  var7 = null;
                  var1.setline(172);
                  var1.getlocal(11).__getattr__("setTarget").__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(14)));
               }
            }

            var1.setlocal(5, var5);
            var1.setline(143);
            PyObject var6 = PyString.fromInterned("handler_%s")._mod(var1.getlocal(5));
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(144);
            var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("class"));
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(145);
            var6 = var1.getlocal(0).__getattr__("options").__call__(var2, var1.getlocal(6));
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(146);
            PyString var13 = PyString.fromInterned("formatter");
            PyObject var10000 = var13._in(var1.getlocal(8));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(147);
               var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("formatter"));
               var1.setlocal(9, var6);
               var6 = null;
            } else {
               var1.setline(149);
               var13 = PyString.fromInterned("");
               var1.setlocal(9, var13);
               var6 = null;
            }

            try {
               var1.setline(151);
               var6 = var1.getglobal("eval").__call__(var2, var1.getlocal(7), var1.getglobal("vars").__call__(var2, var1.getglobal("logging")));
               var1.setlocal(7, var6);
               var6 = null;
            } catch (Throwable var8) {
               PyException var14 = Py.setException(var8, var1);
               if (!var14.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("NameError")}))) {
                  throw var14;
               }

               var1.setline(153);
               var7 = var1.getglobal("_resolve").__call__(var2, var1.getlocal(7));
               var1.setlocal(7, var7);
               var7 = null;
            }

            var1.setline(154);
            var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("args"));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(155);
            var6 = var1.getglobal("eval").__call__(var2, var1.getlocal(10), var1.getglobal("vars").__call__(var2, var1.getglobal("logging")));
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(156);
            var10000 = var1.getlocal(7);
            var15 = Py.EmptyObjects;
            String[] var12 = new String[0];
            var10000 = var10000._callextra(var15, var12, var1.getlocal(10), (PyObject)null);
            var6 = null;
            var6 = var10000;
            var1.setlocal(11, var6);
            var6 = null;
            var1.setline(157);
            var13 = PyString.fromInterned("level");
            var10000 = var13._in(var1.getlocal(8));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(158);
               var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("level"));
               var1.setlocal(12, var6);
               var6 = null;
               var1.setline(159);
               var1.getlocal(11).__getattr__("setLevel").__call__(var2, var1.getglobal("logging").__getattr__("_levelNames").__getitem__(var1.getlocal(12)));
            }

            var1.setline(160);
            if (var1.getglobal("len").__call__(var2, var1.getlocal(9)).__nonzero__()) {
               var1.setline(161);
               var1.getlocal(11).__getattr__("setFormatter").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(9)));
            }

            var1.setline(162);
            if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(7), var1.getglobal("logging").__getattr__("handlers").__getattr__("MemoryHandler")).__nonzero__()) {
               var1.setline(163);
               var13 = PyString.fromInterned("target");
               var10000 = var13._in(var1.getlocal(8));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(164);
                  var6 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("target"));
                  var1.setlocal(13, var6);
                  var6 = null;
               } else {
                  var1.setline(166);
                  var13 = PyString.fromInterned("");
                  var1.setlocal(13, var13);
                  var6 = null;
               }

               var1.setline(167);
               if (var1.getglobal("len").__call__(var2, var1.getlocal(13)).__nonzero__()) {
                  var1.setline(168);
                  var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(13)})));
               }
            }

            var1.setline(169);
            var6 = var1.getlocal(11);
            var1.getlocal(3).__setitem__(var1.getlocal(5), var6);
            var6 = null;
         }
      }
   }

   public PyObject _install_loggers$8(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyString.fromInterned("Create and install loggers");
      var1.setline(180);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("loggers"), (PyObject)PyString.fromInterned("keys"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(181);
      var3 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(182);
      PyObject var10000 = var1.getglobal("list");
      PyObject var10002 = var1.getglobal("map");
      var1.setline(182);
      PyObject[] var7 = Py.EmptyObjects;
      var3 = var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var7, f$9)), (PyObject)var1.getlocal(3)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(183);
      var1.getlocal(3).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("root"));
      var1.setline(184);
      PyString var8 = PyString.fromInterned("logger_root");
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(185);
      var3 = var1.getglobal("logging").__getattr__("root");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(186);
      var3 = var1.getlocal(5);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getlocal(0).__getattr__("options").__call__(var2, var1.getlocal(4));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(188);
      var8 = PyString.fromInterned("level");
      var10000 = var8._in(var1.getlocal(7));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("level"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(190);
         var1.getlocal(6).__getattr__("setLevel").__call__(var2, var1.getglobal("logging").__getattr__("_levelNames").__getitem__(var1.getlocal(8)));
      }

      var1.setline(191);
      var3 = var1.getlocal(5).__getattr__("handlers").__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(191);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(193);
            var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("handlers"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(194);
            if (var1.getglobal("len").__call__(var2, var1.getlocal(10)).__nonzero__()) {
               var1.setline(195);
               var3 = var1.getlocal(10).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(196);
               var3 = var1.getglobal("_strip_spaces").__call__(var2, var1.getlocal(10));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(197);
               var3 = var1.getlocal(10).__iter__();

               while(true) {
                  var1.setline(197);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(11, var4);
                  var1.setline(198);
                  var1.getlocal(6).__getattr__("addHandler").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(11)));
               }
            }

            var1.setline(209);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(5).__getattr__("manager").__getattr__("loggerDict").__getattr__("keys").__call__(var2));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(214);
            var1.getlocal(12).__getattr__("sort").__call__(var2);
            var1.setline(217);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.setlocal(13, var11);
            var3 = null;
            var1.setline(219);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               PyObject var5;
               PyObject var6;
               do {
                  var1.setline(219);
                  var4 = var3.__iternext__();
                  PyInteger var10;
                  if (var4 == null) {
                     var1.setline(257);
                     var3 = var1.getlocal(12).__iter__();

                     while(true) {
                        var1.setline(257);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        var1.setlocal(6, var4);
                        var1.setline(258);
                        var5 = var1.getlocal(5).__getattr__("manager").__getattr__("loggerDict").__getitem__(var1.getlocal(6));
                        var1.setlocal(16, var5);
                        var5 = null;
                        var1.setline(259);
                        var5 = var1.getlocal(6);
                        var10000 = var5._in(var1.getlocal(13));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(260);
                           var5 = var1.getglobal("logging").__getattr__("NOTSET");
                           var1.getlocal(16).__setattr__("level", var5);
                           var5 = null;
                           var1.setline(261);
                           PyList var12 = new PyList(Py.EmptyObjects);
                           var1.getlocal(16).__setattr__((String)"handlers", var12);
                           var5 = null;
                           var1.setline(262);
                           var10 = Py.newInteger(1);
                           var1.getlocal(16).__setattr__((String)"propagate", var10);
                           var5 = null;
                        } else {
                           var1.setline(263);
                           if (var1.getlocal(2).__nonzero__()) {
                              var1.setline(264);
                              var10 = Py.newInteger(1);
                              var1.getlocal(16).__setattr__((String)"disabled", var10);
                              var5 = null;
                           }
                        }
                     }
                  }

                  var1.setlocal(6, var4);
                  var1.setline(220);
                  var5 = PyString.fromInterned("logger_%s")._mod(var1.getlocal(6));
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(221);
                  var5 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("qualname"));
                  var1.setlocal(14, var5);
                  var5 = null;
                  var1.setline(222);
                  var5 = var1.getlocal(0).__getattr__("options").__call__(var2, var1.getlocal(4));
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(223);
                  PyString var9 = PyString.fromInterned("propagate");
                  var10000 = var9._in(var1.getlocal(7));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(224);
                     var5 = var1.getlocal(0).__getattr__("getint").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("propagate"));
                     var1.setlocal(15, var5);
                     var5 = null;
                  } else {
                     var1.setline(226);
                     var10 = Py.newInteger(1);
                     var1.setlocal(15, var10);
                     var5 = null;
                  }

                  var1.setline(227);
                  var5 = var1.getglobal("logging").__getattr__("getLogger").__call__(var2, var1.getlocal(14));
                  var1.setlocal(16, var5);
                  var5 = null;
                  var1.setline(228);
                  var5 = var1.getlocal(14);
                  var10000 = var5._in(var1.getlocal(12));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(229);
                     var5 = var1.getlocal(12).__getattr__("index").__call__(var2, var1.getlocal(14))._add(Py.newInteger(1));
                     var1.setlocal(17, var5);
                     var5 = null;
                     var1.setline(230);
                     var5 = var1.getlocal(14)._add(PyString.fromInterned("."));
                     var1.setlocal(18, var5);
                     var5 = null;
                     var1.setline(231);
                     var5 = var1.getglobal("len").__call__(var2, var1.getlocal(18));
                     var1.setlocal(19, var5);
                     var5 = null;
                     var1.setline(232);
                     var5 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
                     var1.setlocal(20, var5);
                     var5 = null;

                     while(true) {
                        var1.setline(233);
                        var5 = var1.getlocal(17);
                        var10000 = var5._lt(var1.getlocal(20));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(237);
                           var1.getlocal(12).__getattr__("remove").__call__(var2, var1.getlocal(14));
                           break;
                        }

                        var1.setline(234);
                        var5 = var1.getlocal(12).__getitem__(var1.getlocal(17)).__getslice__((PyObject)null, var1.getlocal(19), (PyObject)null);
                        var10000 = var5._eq(var1.getlocal(18));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(235);
                           var1.getlocal(13).__getattr__("append").__call__(var2, var1.getlocal(12).__getitem__(var1.getlocal(17)));
                        }

                        var1.setline(236);
                        var5 = var1.getlocal(17);
                        var5 = var5._iadd(Py.newInteger(1));
                        var1.setlocal(17, var5);
                     }
                  }

                  var1.setline(238);
                  var9 = PyString.fromInterned("level");
                  var10000 = var9._in(var1.getlocal(7));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(239);
                     var5 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("level"));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(240);
                     var1.getlocal(16).__getattr__("setLevel").__call__(var2, var1.getglobal("logging").__getattr__("_levelNames").__getitem__(var1.getlocal(8)));
                  }

                  var1.setline(241);
                  var5 = var1.getlocal(16).__getattr__("handlers").__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

                  while(true) {
                     var1.setline(241);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        var1.setline(243);
                        var5 = var1.getlocal(15);
                        var1.getlocal(16).__setattr__("propagate", var5);
                        var5 = null;
                        var1.setline(244);
                        var10 = Py.newInteger(0);
                        var1.getlocal(16).__setattr__((String)"disabled", var10);
                        var5 = null;
                        var1.setline(245);
                        var5 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("handlers"));
                        var1.setlocal(10, var5);
                        var5 = null;
                        var1.setline(246);
                        break;
                     }

                     var1.setlocal(9, var6);
                     var1.setline(242);
                     var1.getlocal(16).__getattr__("removeHandler").__call__(var2, var1.getlocal(9));
                  }
               } while(!var1.getglobal("len").__call__(var2, var1.getlocal(10)).__nonzero__());

               var1.setline(247);
               var5 = var1.getlocal(10).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(248);
               var5 = var1.getglobal("_strip_spaces").__call__(var2, var1.getlocal(10));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(249);
               var5 = var1.getlocal(10).__iter__();

               while(true) {
                  var1.setline(249);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(11, var6);
                  var1.setline(250);
                  var1.getlocal(16).__getattr__("addHandler").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(11)));
               }
            }
         }

         var1.setlocal(9, var4);
         var1.setline(192);
         var1.getlocal(5).__getattr__("removeHandler").__call__(var2, var1.getlocal(9));
      }
   }

   public PyObject f$9(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject valid_ident$10(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getglobal("IDENTIFIER").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(273);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(274);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Not a valid Python identifier: %r")._mod(var1.getlocal(0))));
      } else {
         var1.setline(275);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ConvertingDict$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A converting dictionary wrapper."));
      var1.setline(288);
      PyString.fromInterned("A converting dictionary wrapper.");
      var1.setline(290);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$12, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(302);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$13, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(314);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, pop$14, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$12(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getglobal("dict").__getattr__("__getitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(292);
      var3 = var1.getlocal(0).__getattr__("configurator").__getattr__("convert").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(294);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(295);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(296);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ConvertingDict"), var1.getglobal("ConvertingList"), var1.getglobal("ConvertingTuple")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(298);
            var3 = var1.getlocal(0);
            var1.getlocal(3).__setattr__("parent", var3);
            var3 = null;
            var1.setline(299);
            var3 = var1.getlocal(1);
            var1.getlocal(3).__setattr__("key", var3);
            var3 = null;
         }
      }

      var1.setline(300);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$13(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyObject var3 = var1.getglobal("dict").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getlocal(0).__getattr__("configurator").__getattr__("convert").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(306);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(307);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(308);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(4));
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ConvertingDict"), var1.getglobal("ConvertingList"), var1.getglobal("ConvertingTuple")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(310);
            var3 = var1.getlocal(0);
            var1.getlocal(4).__setattr__("parent", var3);
            var3 = null;
            var1.setline(311);
            var3 = var1.getlocal(1);
            var1.getlocal(4).__setattr__("key", var3);
            var3 = null;
         }
      }

      var1.setline(312);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pop$14(PyFrame var1, ThreadState var2) {
      var1.setline(315);
      PyObject var3 = var1.getglobal("dict").__getattr__("pop").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(316);
      var3 = var1.getlocal(0).__getattr__("configurator").__getattr__("convert").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(317);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(318);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(4));
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ConvertingDict"), var1.getglobal("ConvertingList"), var1.getglobal("ConvertingTuple")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(320);
            var3 = var1.getlocal(0);
            var1.getlocal(4).__setattr__("parent", var3);
            var3 = null;
            var1.setline(321);
            var3 = var1.getlocal(1);
            var1.getlocal(4).__setattr__("key", var3);
            var3 = null;
         }
      }

      var1.setline(322);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ConvertingList$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A converting list wrapper."));
      var1.setline(325);
      PyString.fromInterned("A converting list wrapper.");
      var1.setline(326);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$16, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(338);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, pop$17, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$16(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getglobal("list").__getattr__("__getitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(328);
      var3 = var1.getlocal(0).__getattr__("configurator").__getattr__("convert").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(331);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(332);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ConvertingDict"), var1.getglobal("ConvertingList"), var1.getglobal("ConvertingTuple")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(334);
            var3 = var1.getlocal(0);
            var1.getlocal(3).__setattr__("parent", var3);
            var3 = null;
            var1.setline(335);
            var3 = var1.getlocal(1);
            var1.getlocal(3).__setattr__("key", var3);
            var3 = null;
         }
      }

      var1.setline(336);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pop$17(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      PyObject var3 = var1.getglobal("list").__getattr__("pop").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getlocal(0).__getattr__("configurator").__getattr__("convert").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(342);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ConvertingDict"), var1.getglobal("ConvertingList"), var1.getglobal("ConvertingTuple")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(344);
            var3 = var1.getlocal(0);
            var1.getlocal(3).__setattr__("parent", var3);
            var3 = null;
         }
      }

      var1.setline(345);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ConvertingTuple$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A converting tuple wrapper."));
      var1.setline(348);
      PyString.fromInterned("A converting tuple wrapper.");
      var1.setline(349);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$19, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$19(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyObject var3 = var1.getglobal("tuple").__getattr__("__getitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(351);
      var3 = var1.getlocal(0).__getattr__("configurator").__getattr__("convert").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(352);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(353);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("ConvertingDict"), var1.getglobal("ConvertingList"), var1.getglobal("ConvertingTuple")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(355);
            var3 = var1.getlocal(0);
            var1.getlocal(3).__setattr__("parent", var3);
            var3 = null;
            var1.setline(356);
            var3 = var1.getlocal(1);
            var1.getlocal(3).__setattr__("key", var3);
            var3 = null;
         }
      }

      var1.setline(357);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BaseConfigurator$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    The configurator base class which defines some useful defaults.\n    "));
      var1.setline(362);
      PyString.fromInterned("\n    The configurator base class which defines some useful defaults.\n    ");
      var1.setline(364);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?P<prefix>[a-z]+)://(?P<suffix>.*)$"));
      var1.setlocal("CONVERT_PATTERN", var3);
      var3 = null;
      var1.setline(366);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*(\\w+)\\s*"));
      var1.setlocal("WORD_PATTERN", var3);
      var3 = null;
      var1.setline(367);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\.\\s*(\\w+)\\s*"));
      var1.setlocal("DOT_PATTERN", var3);
      var3 = null;
      var1.setline(368);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\[\\s*(\\w+)\\s*\\]\\s*"));
      var1.setlocal("INDEX_PATTERN", var3);
      var3 = null;
      var1.setline(369);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\d+$"));
      var1.setlocal("DIGIT_PATTERN", var3);
      var3 = null;
      var1.setline(371);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("ext"), PyString.fromInterned("ext_convert"), PyString.fromInterned("cfg"), PyString.fromInterned("cfg_convert")});
      var1.setlocal("value_converters", var4);
      var3 = null;
      var1.setline(377);
      var3 = var1.getname("__import__");
      var1.setlocal("importer", var3);
      var3 = null;
      var1.setline(379);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(383);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, resolve$22, PyString.fromInterned("\n        Resolve strings to objects using standard import and attribute\n        syntax.\n        "));
      var1.setlocal("resolve", var6);
      var3 = null;
      var1.setline(406);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, ext_convert$23, PyString.fromInterned("Default converter for the ext:// protocol."));
      var1.setlocal("ext_convert", var6);
      var3 = null;
      var1.setline(410);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, cfg_convert$24, PyString.fromInterned("Default converter for the cfg:// protocol."));
      var1.setlocal("cfg_convert", var6);
      var3 = null;
      var1.setline(444);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, convert$25, PyString.fromInterned("\n        Convert values to an appropriate type. dicts, lists and tuples are\n        replaced by their converting alternatives. Strings are checked to\n        see if they have a conversion format and are converted if they do.\n        "));
      var1.setlocal("convert", var6);
      var3 = null;
      var1.setline(472);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, configure_custom$26, PyString.fromInterned("Configure an object with a user-supplied factory."));
      var1.setlocal("configure_custom", var6);
      var3 = null;
      var1.setline(486);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, as_tuple$27, PyString.fromInterned("Utility function which converts lists to tuples."));
      var1.setlocal("as_tuple", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyObject var3 = var1.getglobal("ConvertingDict").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("config", var3);
      var3 = null;
      var1.setline(381);
      var3 = var1.getlocal(0);
      var1.getlocal(0).__getattr__("config").__setattr__("configurator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resolve$22(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyString.fromInterned("\n        Resolve strings to objects using standard import and attribute\n        syntax.\n        ");
      var1.setline(388);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(389);
      var3 = var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var5;
      PyObject[] var6;
      try {
         var1.setline(391);
         var3 = var1.getlocal(0).__getattr__("importer").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(392);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(392);
            PyObject var10 = var3.__iternext__();
            if (var10 == null) {
               var1.setline(399);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(5, var10);
            var1.setline(393);
            var5 = var1.getlocal(3);
            var5 = var5._iadd(PyString.fromInterned(".")._add(var1.getlocal(5)));
            var1.setlocal(3, var5);

            try {
               var1.setline(395);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(4, var5);
               var5 = null;
            } catch (Throwable var8) {
               PyException var13 = Py.setException(var8, var1);
               if (!var13.match(var1.getglobal("AttributeError"))) {
                  throw var13;
               }

               var1.setline(397);
               var1.getlocal(0).__getattr__("importer").__call__(var2, var1.getlocal(3));
               var1.setline(398);
               PyObject var12 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(4, var12);
               var6 = null;
            }
         }
      } catch (Throwable var9) {
         PyException var4 = Py.setException(var9, var1);
         if (var4.match(var1.getglobal("ImportError"))) {
            var1.setline(401);
            var5 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(6, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(7, var7);
            var7 = null;
            var5 = null;
            var1.setline(402);
            var5 = var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Cannot resolve %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)})));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(403);
            PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)});
            var6 = Py.unpackSequence(var11, 2);
            var7 = var6[0];
            var1.getlocal(8).__setattr__("__cause__", var7);
            var7 = null;
            var7 = var6[1];
            var1.getlocal(8).__setattr__("__traceback__", var7);
            var7 = null;
            var5 = null;
            var1.setline(404);
            throw Py.makeException(var1.getlocal(8));
         } else {
            throw var4;
         }
      }
   }

   public PyObject ext_convert$23(PyFrame var1, ThreadState var2) {
      var1.setline(407);
      PyString.fromInterned("Default converter for the ext:// protocol.");
      var1.setline(408);
      PyObject var3 = var1.getlocal(0).__getattr__("resolve").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cfg_convert$24(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyString.fromInterned("Default converter for the cfg:// protocol.");
      var1.setline(412);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(413);
      var3 = var1.getlocal(0).__getattr__("WORD_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(414);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(415);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to convert %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(417);
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(3).__getattr__("end").__call__(var2), (PyObject)null, (PyObject)null);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(418);
         var3 = var1.getlocal(0).__getattr__("config").__getitem__(var1.getlocal(3).__getattr__("groups").__call__(var2).__getitem__(Py.newInteger(0)));
         var1.setlocal(4, var3);
         var3 = null;

         while(true) {
            var1.setline(420);
            if (!var1.getlocal(2).__nonzero__()) {
               var1.setline(442);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(421);
            var3 = var1.getlocal(0).__getattr__("DOT_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(422);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(423);
               var3 = var1.getlocal(4).__getitem__(var1.getlocal(3).__getattr__("groups").__call__(var2).__getitem__(Py.newInteger(0)));
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(425);
               var3 = var1.getlocal(0).__getattr__("INDEX_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(426);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(427);
                  var3 = var1.getlocal(3).__getattr__("groups").__call__(var2).__getitem__(Py.newInteger(0));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(428);
                  if (var1.getlocal(0).__getattr__("DIGIT_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
                     var1.setline(429);
                     var3 = var1.getlocal(4).__getitem__(var1.getlocal(5));
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     try {
                        var1.setline(432);
                        var3 = var1.getglobal("int").__call__(var2, var1.getlocal(5));
                        var1.setlocal(6, var3);
                        var3 = null;
                        var1.setline(433);
                        var3 = var1.getlocal(4).__getitem__(var1.getlocal(6));
                        var1.setlocal(4, var3);
                        var3 = null;
                     } catch (Throwable var5) {
                        PyException var6 = Py.setException(var5, var1);
                        if (!var6.match(var1.getglobal("TypeError"))) {
                           throw var6;
                        }

                        var1.setline(435);
                        PyObject var4 = var1.getlocal(4).__getitem__(var1.getlocal(5));
                        var1.setlocal(4, var4);
                        var4 = null;
                     }
                  }
               }
            }

            var1.setline(436);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.setline(439);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to convert %r at %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}))));
            }

            var1.setline(437);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(3).__getattr__("end").__call__(var2), (PyObject)null, (PyObject)null);
            var1.setlocal(2, var3);
            var3 = null;
         }
      }
   }

   public PyObject convert$25(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyString.fromInterned("\n        Convert values to an appropriate type. dicts, lists and tuples are\n        replaced by their converting alternatives. Strings are checked to\n        see if they have a conversion format and are converted if they do.\n        ");
      var1.setline(450);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("ConvertingDict")).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("dict"));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(451);
         var3 = var1.getglobal("ConvertingDict").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(452);
         var3 = var1.getlocal(0);
         var1.getlocal(1).__setattr__("configurator", var3);
         var3 = null;
      } else {
         var1.setline(453);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("ConvertingList")).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(454);
            var3 = var1.getglobal("ConvertingList").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(455);
            var3 = var1.getlocal(0);
            var1.getlocal(1).__setattr__("configurator", var3);
            var3 = null;
         } else {
            var1.setline(456);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("ConvertingTuple")).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(458);
               var3 = var1.getglobal("ConvertingTuple").__call__(var2, var1.getlocal(1));
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(459);
               var3 = var1.getlocal(0);
               var1.getlocal(1).__setattr__("configurator", var3);
               var3 = null;
            } else {
               var1.setline(460);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
                  var1.setline(461);
                  var3 = var1.getlocal(0).__getattr__("CONVERT_PATTERN").__getattr__("match").__call__(var2, var1.getlocal(1));
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(462);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(463);
                     var3 = var1.getlocal(2).__getattr__("groupdict").__call__(var2);
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(464);
                     var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("prefix"));
                     var1.setlocal(4, var3);
                     var3 = null;
                     var1.setline(465);
                     var3 = var1.getlocal(0).__getattr__("value_converters").__getattr__("get").__call__(var2, var1.getlocal(4), var1.getglobal("None"));
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(466);
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(467);
                        var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("suffix"));
                        var1.setlocal(6, var3);
                        var3 = null;
                        var1.setline(468);
                        var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(5));
                        var1.setlocal(5, var3);
                        var3 = null;
                        var1.setline(469);
                        var3 = var1.getlocal(5).__call__(var2, var1.getlocal(6));
                        var1.setlocal(1, var3);
                        var3 = null;
                     }
                  }
               }
            }
         }
      }

      var1.setline(470);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject configure_custom$26(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyString.fromInterned("Configure an object with a user-supplied factory.");
      var1.setline(474);
      PyObject var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("()"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(475);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__call__")).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("types"), (PyObject)PyString.fromInterned("ClassType"));
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
            var10000 = var3._ne(var1.getglobal("types").__getattr__("ClassType"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(476);
         var3 = var1.getlocal(0).__getattr__("resolve").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(477);
      var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(479);
      var10000 = var1.getglobal("dict");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(479);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(479);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(479);
            var1.dellocal(5);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(480);
            var10000 = var1.getlocal(2);
            PyObject[] var8 = Py.EmptyObjects;
            String[] var7 = new String[0];
            var10000 = var10000._callextra(var8, var7, (PyObject)null, var1.getlocal(4));
            var3 = null;
            var3 = var10000;
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(481);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(482);
               var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(482);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var1.setline(483);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9));
               }
            }

            var1.setline(484);
            var3 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(6, var4);
         var1.setline(479);
         if (var1.getglobal("valid_ident").__call__(var2, var1.getlocal(6)).__nonzero__()) {
            var1.setline(479);
            var1.getlocal(5).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(1).__getitem__(var1.getlocal(6))})));
         }
      }
   }

   public PyObject as_tuple$27(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyString.fromInterned("Utility function which converts lists to tuples.");
      var1.setline(488);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__nonzero__()) {
         var1.setline(489);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(490);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DictConfigurator$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Configure logging using a dictionary-like object to describe the\n    configuration.\n    "));
      var1.setline(496);
      PyString.fromInterned("\n    Configure logging using a dictionary-like object to describe the\n    configuration.\n    ");
      var1.setline(498);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, configure$29, PyString.fromInterned("Do the configuration."));
      var1.setlocal("configure", var4);
      var3 = null;
      var1.setline(642);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, configure_formatter$30, PyString.fromInterned("Configure a formatter from a dictionary."));
      var1.setlocal("configure_formatter", var4);
      var3 = null;
      var1.setline(664);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, configure_filter$31, PyString.fromInterned("Configure a filter from a dictionary."));
      var1.setlocal("configure_filter", var4);
      var3 = null;
      var1.setline(673);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_filters$32, PyString.fromInterned("Add filters to a filterer from a list of names."));
      var1.setlocal("add_filters", var4);
      var3 = null;
      var1.setline(681);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, configure_handler$33, PyString.fromInterned("Configure a handler from a dictionary."));
      var1.setlocal("configure_handler", var4);
      var3 = null;
      var1.setline(734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_handlers$34, PyString.fromInterned("Add handlers to a logger from a list of names."));
      var1.setlocal("add_handlers", var4);
      var3 = null;
      var1.setline(742);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, common_logger_config$35, PyString.fromInterned("\n        Perform configuration which is common to root and non-root loggers.\n        "));
      var1.setlocal("common_logger_config", var4);
      var3 = null;
      var1.setline(760);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, configure_logger$36, PyString.fromInterned("Configure a non-root logger from a dictionary."));
      var1.setlocal("configure_logger", var4);
      var3 = null;
      var1.setline(768);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, configure_root$37, PyString.fromInterned("Configure a root logger from a dictionary."));
      var1.setlocal("configure_root", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject configure$29(PyFrame var1, ThreadState var2) {
      var1.setline(499);
      PyString.fromInterned("Do the configuration.");
      var1.setline(501);
      PyObject var3 = var1.getlocal(0).__getattr__("config");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(502);
      PyString var17 = PyString.fromInterned("version");
      PyObject var10000 = var17._notin(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(503);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dictionary doesn't specify a version")));
      } else {
         var1.setline(504);
         var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("version"));
         var10000 = var3._ne(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(505);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unsupported version: %s")._mod(var1.getlocal(1).__getitem__(PyString.fromInterned("version")))));
         } else {
            var1.setline(506);
            var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("incremental"), (PyObject)var1.getglobal("False"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(507);
            PyDictionary var18 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(3, var18);
            var3 = null;
            var1.setline(508);
            var1.getglobal("logging").__getattr__("_acquireLock").__call__(var2);
            var3 = null;

            try {
               var1.setline(510);
               PyObject var4;
               PyObject var5;
               PyObject var6;
               PyObject var7;
               PyException var19;
               PyException var20;
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(511);
                  var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("handlers"), (PyObject)var1.getlocal(3));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(512);
                  var4 = var1.getlocal(4).__iter__();

                  label170:
                  while(true) {
                     var1.setline(512);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(526);
                        var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("loggers"), (PyObject)var1.getlocal(3));
                        var1.setlocal(10, var4);
                        var4 = null;
                        var1.setline(527);
                        var4 = var1.getlocal(10).__iter__();

                        while(true) {
                           var1.setline(527);
                           var5 = var4.__iternext__();
                           if (var5 == null) {
                              var1.setline(533);
                              var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("root"), (PyObject)var1.getglobal("None"));
                              var1.setlocal(11, var4);
                              var4 = null;
                              var1.setline(534);
                              if (var1.getlocal(11).__nonzero__()) {
                                 try {
                                    var1.setline(536);
                                    var1.getlocal(0).__getattr__("configure_root").__call__(var2, var1.getlocal(11), var1.getglobal("True"));
                                 } catch (Throwable var13) {
                                    var20 = Py.setException(var13, var1);
                                    if (var20.match(var1.getglobal("StandardError"))) {
                                       var5 = var20.value;
                                       var1.setlocal(9, var5);
                                       var5 = null;
                                       var1.setline(538);
                                       throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure root logger: %s")._mod(var1.getlocal(9))));
                                    }

                                    throw var20;
                                 }
                              }
                              break label170;
                           }

                           var1.setlocal(5, var5);

                           try {
                              var1.setline(529);
                              var1.getlocal(0).__getattr__("configure_logger").__call__(var2, var1.getlocal(5), var1.getlocal(10).__getitem__(var1.getlocal(5)), var1.getglobal("True"));
                           } catch (Throwable var14) {
                              var19 = Py.setException(var14, var1);
                              if (var19.match(var1.getglobal("StandardError"))) {
                                 var7 = var19.value;
                                 var1.setlocal(9, var7);
                                 var7 = null;
                                 var1.setline(531);
                                 throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure logger %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}))));
                              }

                              throw var19;
                           }
                        }
                     }

                     var1.setlocal(5, var5);
                     var1.setline(513);
                     var6 = var1.getlocal(5);
                     var10000 = var6._notin(var1.getglobal("logging").__getattr__("_handlers"));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(514);
                        throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("No handler found with name %r")._mod(var1.getlocal(5))));
                     }

                     try {
                        var1.setline(518);
                        var6 = var1.getglobal("logging").__getattr__("_handlers").__getitem__(var1.getlocal(5));
                        var1.setlocal(6, var6);
                        var6 = null;
                        var1.setline(519);
                        var6 = var1.getlocal(4).__getitem__(var1.getlocal(5));
                        var1.setlocal(7, var6);
                        var6 = null;
                        var1.setline(520);
                        var6 = var1.getlocal(7).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("level"), (PyObject)var1.getglobal("None"));
                        var1.setlocal(8, var6);
                        var6 = null;
                        var1.setline(521);
                        if (var1.getlocal(8).__nonzero__()) {
                           var1.setline(522);
                           var1.getlocal(6).__getattr__("setLevel").__call__(var2, var1.getglobal("logging").__getattr__("_checkLevel").__call__(var2, var1.getlocal(8)));
                        }
                     } catch (Throwable var15) {
                        var19 = Py.setException(var15, var1);
                        if (var19.match(var1.getglobal("StandardError"))) {
                           var7 = var19.value;
                           var1.setlocal(9, var7);
                           var7 = null;
                           var1.setline(524);
                           throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure handler %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}))));
                        }

                        throw var19;
                     }
                  }
               } else {
                  var1.setline(541);
                  var4 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("disable_existing_loggers"), (PyObject)var1.getglobal("True"));
                  var1.setlocal(12, var4);
                  var4 = null;
                  var1.setline(543);
                  var1.getglobal("logging").__getattr__("_handlers").__getattr__("clear").__call__(var2);
                  var1.setline(544);
                  var1.getglobal("logging").__getattr__("_handlerList").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
                  var1.setline(547);
                  var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("formatters"), (PyObject)var1.getlocal(3));
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(548);
                  var4 = var1.getlocal(13).__iter__();

                  label209:
                  while(true) {
                     var1.setline(548);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(556);
                        var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filters"), (PyObject)var1.getlocal(3));
                        var1.setlocal(14, var4);
                        var4 = null;
                        var1.setline(557);
                        var4 = var1.getlocal(14).__iter__();

                        while(true) {
                           var1.setline(557);
                           var5 = var4.__iternext__();
                           if (var5 == null) {
                              var1.setline(567);
                              var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("handlers"), (PyObject)var1.getlocal(3));
                              var1.setlocal(4, var4);
                              var4 = null;
                              var1.setline(568);
                              var4 = var1.getglobal("sorted").__call__(var2, var1.getlocal(4)).__iter__();

                              while(true) {
                                 var1.setline(568);
                                 var5 = var4.__iternext__();
                                 if (var5 == null) {
                                    var1.setline(586);
                                    var4 = var1.getglobal("logging").__getattr__("root");
                                    var1.setlocal(11, var4);
                                    var4 = null;
                                    var1.setline(587);
                                    var4 = var1.getlocal(11).__getattr__("manager").__getattr__("loggerDict").__getattr__("keys").__call__(var2);
                                    var1.setlocal(15, var4);
                                    var4 = null;
                                    var1.setline(592);
                                    var1.getlocal(15).__getattr__("sort").__call__(var2);
                                    var1.setline(595);
                                    PyList var21 = new PyList(Py.EmptyObjects);
                                    var1.setlocal(16, var21);
                                    var4 = null;
                                    var1.setline(597);
                                    var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("loggers"), (PyObject)var1.getlocal(3));
                                    var1.setlocal(10, var4);
                                    var4 = null;
                                    var1.setline(598);
                                    var4 = var1.getlocal(10).__iter__();

                                    while(true) {
                                       var1.setline(598);
                                       var5 = var4.__iternext__();
                                       if (var5 == null) {
                                          var1.setline(622);
                                          var4 = var1.getlocal(15).__iter__();

                                          while(true) {
                                             var1.setline(622);
                                             var5 = var4.__iternext__();
                                             if (var5 == null) {
                                                var1.setline(632);
                                                var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("root"), (PyObject)var1.getglobal("None"));
                                                var1.setlocal(11, var4);
                                                var4 = null;
                                                var1.setline(633);
                                                if (var1.getlocal(11).__nonzero__()) {
                                                   try {
                                                      var1.setline(635);
                                                      var1.getlocal(0).__getattr__("configure_root").__call__(var2, var1.getlocal(11));
                                                   } catch (Throwable var8) {
                                                      var20 = Py.setException(var8, var1);
                                                      if (var20.match(var1.getglobal("StandardError"))) {
                                                         var5 = var20.value;
                                                         var1.setlocal(9, var5);
                                                         var5 = null;
                                                         var1.setline(637);
                                                         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure root logger: %s")._mod(var1.getlocal(9))));
                                                      }

                                                      throw var20;
                                                   }
                                                }
                                                break label209;
                                             }

                                             var1.setlocal(21, var5);
                                             var1.setline(623);
                                             var6 = var1.getlocal(11).__getattr__("manager").__getattr__("loggerDict").__getitem__(var1.getlocal(21));
                                             var1.setlocal(22, var6);
                                             var6 = null;
                                             var1.setline(624);
                                             var6 = var1.getlocal(21);
                                             var10000 = var6._in(var1.getlocal(16));
                                             var6 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(625);
                                                var6 = var1.getglobal("logging").__getattr__("NOTSET");
                                                var1.getlocal(22).__setattr__("level", var6);
                                                var6 = null;
                                                var1.setline(626);
                                                PyList var22 = new PyList(Py.EmptyObjects);
                                                var1.getlocal(22).__setattr__((String)"handlers", var22);
                                                var6 = null;
                                                var1.setline(627);
                                                var6 = var1.getglobal("True");
                                                var1.getlocal(22).__setattr__("propagate", var6);
                                                var6 = null;
                                             } else {
                                                var1.setline(628);
                                                if (var1.getlocal(12).__nonzero__()) {
                                                   var1.setline(629);
                                                   var6 = var1.getglobal("True");
                                                   var1.getlocal(22).__setattr__("disabled", var6);
                                                   var6 = null;
                                                }
                                             }
                                          }
                                       }

                                       var1.setlocal(5, var5);
                                       var1.setline(599);
                                       var6 = var1.getglobal("_encoded").__call__(var2, var1.getlocal(5));
                                       var1.setlocal(5, var6);
                                       var6 = null;
                                       var1.setline(600);
                                       var6 = var1.getlocal(5);
                                       var10000 = var6._in(var1.getlocal(15));
                                       var6 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(601);
                                          var6 = var1.getlocal(15).__getattr__("index").__call__(var2, var1.getlocal(5));
                                          var1.setlocal(17, var6);
                                          var6 = null;
                                          var1.setline(602);
                                          var6 = var1.getlocal(5)._add(PyString.fromInterned("."));
                                          var1.setlocal(18, var6);
                                          var6 = null;
                                          var1.setline(603);
                                          var6 = var1.getglobal("len").__call__(var2, var1.getlocal(18));
                                          var1.setlocal(19, var6);
                                          var6 = null;
                                          var1.setline(604);
                                          var6 = var1.getglobal("len").__call__(var2, var1.getlocal(15));
                                          var1.setlocal(20, var6);
                                          var6 = null;
                                          var1.setline(605);
                                          var6 = var1.getlocal(17)._add(Py.newInteger(1));
                                          var1.setlocal(17, var6);
                                          var6 = null;

                                          while(true) {
                                             var1.setline(606);
                                             var6 = var1.getlocal(17);
                                             var10000 = var6._lt(var1.getlocal(20));
                                             var6 = null;
                                             if (var10000.__nonzero__()) {
                                                var6 = var1.getlocal(15).__getitem__(var1.getlocal(17)).__getslice__((PyObject)null, var1.getlocal(19), (PyObject)null);
                                                var10000 = var6._eq(var1.getlocal(18));
                                                var6 = null;
                                             }

                                             if (!var10000.__nonzero__()) {
                                                var1.setline(610);
                                                var1.getlocal(15).__getattr__("remove").__call__(var2, var1.getlocal(5));
                                                break;
                                             }

                                             var1.setline(608);
                                             var1.getlocal(16).__getattr__("append").__call__(var2, var1.getlocal(15).__getitem__(var1.getlocal(17)));
                                             var1.setline(609);
                                             var6 = var1.getlocal(17)._add(Py.newInteger(1));
                                             var1.setlocal(17, var6);
                                             var6 = null;
                                          }
                                       }

                                       try {
                                          var1.setline(612);
                                          var1.getlocal(0).__getattr__("configure_logger").__call__(var2, var1.getlocal(5), var1.getlocal(10).__getitem__(var1.getlocal(5)));
                                       } catch (Throwable var9) {
                                          var19 = Py.setException(var9, var1);
                                          if (var19.match(var1.getglobal("StandardError"))) {
                                             var7 = var19.value;
                                             var1.setlocal(9, var7);
                                             var7 = null;
                                             var1.setline(614);
                                             throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure logger %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}))));
                                          }

                                          throw var19;
                                       }
                                    }
                                 }

                                 var1.setlocal(5, var5);

                                 try {
                                    var1.setline(570);
                                    var6 = var1.getlocal(0).__getattr__("configure_handler").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(5)));
                                    var1.setlocal(6, var6);
                                    var6 = null;
                                    var1.setline(571);
                                    var6 = var1.getlocal(5);
                                    var1.getlocal(6).__setattr__("name", var6);
                                    var6 = null;
                                    var1.setline(572);
                                    var6 = var1.getlocal(6);
                                    var1.getlocal(4).__setitem__(var1.getlocal(5), var6);
                                    var6 = null;
                                 } catch (Throwable var10) {
                                    var19 = Py.setException(var10, var1);
                                    if (var19.match(var1.getglobal("StandardError"))) {
                                       var7 = var19.value;
                                       var1.setlocal(9, var7);
                                       var7 = null;
                                       var1.setline(574);
                                       throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure handler %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}))));
                                    }

                                    throw var19;
                                 }
                              }
                           }

                           var1.setlocal(5, var5);

                           try {
                              var1.setline(559);
                              var6 = var1.getlocal(0).__getattr__("configure_filter").__call__(var2, var1.getlocal(14).__getitem__(var1.getlocal(5)));
                              var1.getlocal(14).__setitem__(var1.getlocal(5), var6);
                              var6 = null;
                           } catch (Throwable var11) {
                              var19 = Py.setException(var11, var1);
                              if (var19.match(var1.getglobal("StandardError"))) {
                                 var7 = var19.value;
                                 var1.setlocal(9, var7);
                                 var7 = null;
                                 var1.setline(561);
                                 throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure filter %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}))));
                              }

                              throw var19;
                           }
                        }
                     }

                     var1.setlocal(5, var5);

                     try {
                        var1.setline(550);
                        var6 = var1.getlocal(0).__getattr__("configure_formatter").__call__(var2, var1.getlocal(13).__getitem__(var1.getlocal(5)));
                        var1.getlocal(13).__setitem__(var1.getlocal(5), var6);
                        var6 = null;
                     } catch (Throwable var12) {
                        var19 = Py.setException(var12, var1);
                        if (var19.match(var1.getglobal("StandardError"))) {
                           var7 = var19.value;
                           var1.setlocal(9, var7);
                           var7 = null;
                           var1.setline(553);
                           throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to configure formatter %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}))));
                        }

                        throw var19;
                     }
                  }
               }
            } catch (Throwable var16) {
               Py.addTraceback(var16, var1);
               var1.setline(640);
               var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
               throw (Throwable)var16;
            }

            var1.setline(640);
            var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject configure_formatter$30(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      PyString.fromInterned("Configure a formatter from a dictionary.");
      var1.setline(644);
      PyString var3 = PyString.fromInterned("()");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(645);
         var6 = var1.getlocal(1).__getitem__(PyString.fromInterned("()"));
         var1.setlocal(2, var6);
         var3 = null;

         try {
            var1.setline(647);
            var6 = var1.getlocal(0).__getattr__("configure_custom").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var6);
            var3 = null;
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (!var8.match(var1.getglobal("TypeError"))) {
               throw var8;
            }

            PyObject var4 = var8.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(649);
            PyString var7 = PyString.fromInterned("'format'");
            var10000 = var7._notin(var1.getglobal("str").__call__(var2, var1.getlocal(4)));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(650);
               throw Py.makeException();
            }

            var1.setline(655);
            var4 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("format"));
            var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("fmt"), var4);
            var4 = null;
            var1.setline(656);
            var4 = var1.getlocal(2);
            var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("()"), var4);
            var4 = null;
            var1.setline(657);
            var4 = var1.getlocal(0).__getattr__("configure_custom").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var4);
            var4 = null;
         }
      } else {
         var1.setline(659);
         var6 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("format"), (PyObject)var1.getglobal("None"));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(660);
         var6 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("datefmt"), (PyObject)var1.getglobal("None"));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(661);
         var6 = var1.getglobal("logging").__getattr__("Formatter").__call__(var2, var1.getlocal(5), var1.getlocal(6));
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(662);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject configure_filter$31(PyFrame var1, ThreadState var2) {
      var1.setline(665);
      PyString.fromInterned("Configure a filter from a dictionary.");
      var1.setline(666);
      PyString var3 = PyString.fromInterned("()");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(667);
         var4 = var1.getlocal(0).__getattr__("configure_custom").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(669);
         var4 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(670);
         var4 = var1.getglobal("logging").__getattr__("Filter").__call__(var2, var1.getlocal(3));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(671);
      var4 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject add_filters$32(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      PyString.fromInterned("Add filters to a filterer from a list of names.");
      var1.setline(675);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(675);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);

         try {
            var1.setline(677);
            var1.getlocal(1).__getattr__("addFilter").__call__(var2, var1.getlocal(0).__getattr__("config").__getitem__(PyString.fromInterned("filters")).__getitem__(var1.getlocal(3)));
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("StandardError"))) {
               PyObject var6 = var5.value;
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(679);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to add filter %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)}))));
            }

            throw var5;
         }
      }
   }

   public PyObject configure_handler$33(PyFrame var1, ThreadState var2) {
      var1.setline(682);
      PyString.fromInterned("Configure a handler from a dictionary.");
      var1.setline(683);
      PyObject var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("formatter"), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(684);
      PyObject var4;
      PyException var9;
      if (var1.getlocal(2).__nonzero__()) {
         try {
            var1.setline(686);
            var3 = var1.getlocal(0).__getattr__("config").__getitem__(PyString.fromInterned("formatters")).__getitem__(var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var7) {
            var9 = Py.setException(var7, var1);
            if (var9.match(var1.getglobal("StandardError"))) {
               var4 = var9.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(688);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to set formatter %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}))));
            }

            throw var9;
         }
      }

      var1.setline(690);
      var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("level"), (PyObject)var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(691);
      var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filters"), (PyObject)var1.getglobal("None"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(692);
      PyString var10 = PyString.fromInterned("()");
      PyObject var10000 = var10._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(693);
         var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("()"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(694);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("__call__")).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("types"), (PyObject)PyString.fromInterned("ClassType"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
               var10000 = var3._ne(var1.getglobal("types").__getattr__("ClassType"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(695);
            var3 = var1.getlocal(0).__getattr__("resolve").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(696);
         var3 = var1.getlocal(6);
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(698);
         var3 = var1.getlocal(0).__getattr__("resolve").__call__(var2, var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("class")));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(700);
         var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(8), var1.getglobal("logging").__getattr__("handlers").__getattr__("MemoryHandler"));
         if (var10000.__nonzero__()) {
            var10 = PyString.fromInterned("target");
            var10000 = var10._in(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            try {
               var1.setline(703);
               var3 = var1.getlocal(0).__getattr__("config").__getitem__(PyString.fromInterned("handlers")).__getitem__(var1.getlocal(1).__getitem__(PyString.fromInterned("target")));
               var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("target"), var3);
               var3 = null;
            } catch (Throwable var6) {
               var9 = Py.setException(var6, var1);
               if (var9.match(var1.getglobal("StandardError"))) {
                  var4 = var9.value;
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(705);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to set target handler %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(PyString.fromInterned("target")), var1.getlocal(3)}))));
               }

               throw var9;
            }
         } else {
            var1.setline(707);
            var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(8), var1.getglobal("logging").__getattr__("handlers").__getattr__("SMTPHandler"));
            if (var10000.__nonzero__()) {
               var10 = PyString.fromInterned("mailhost");
               var10000 = var10._in(var1.getlocal(1));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(709);
               var3 = var1.getlocal(0).__getattr__("as_tuple").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("mailhost")));
               var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("mailhost"), var3);
               var3 = null;
            } else {
               var1.setline(710);
               var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(8), var1.getglobal("logging").__getattr__("handlers").__getattr__("SysLogHandler"));
               if (var10000.__nonzero__()) {
                  var10 = PyString.fromInterned("address");
                  var10000 = var10._in(var1.getlocal(1));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(712);
                  var3 = var1.getlocal(0).__getattr__("as_tuple").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("address")));
                  var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("address"), var3);
                  var3 = null;
               }
            }
         }

         var1.setline(713);
         var3 = var1.getlocal(8);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(714);
      var10000 = var1.getglobal("dict");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(714);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(714);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(714);
            var1.dellocal(10);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(9, var3);
            var3 = null;

            try {
               var1.setline(716);
               var10000 = var1.getlocal(7);
               PyObject[] var14 = Py.EmptyObjects;
               String[] var13 = new String[0];
               var10000 = var10000._callextra(var14, var13, (PyObject)null, var1.getlocal(9));
               var3 = null;
               var3 = var10000;
               var1.setlocal(12, var3);
               var3 = null;
            } catch (Throwable var8) {
               var9 = Py.setException(var8, var1);
               if (!var9.match(var1.getglobal("TypeError"))) {
                  throw var9;
               }

               var4 = var9.value;
               var1.setlocal(13, var4);
               var4 = null;
               var1.setline(718);
               PyString var11 = PyString.fromInterned("'stream'");
               var10000 = var11._notin(var1.getglobal("str").__call__(var2, var1.getlocal(13)));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(719);
                  throw Py.makeException();
               }

               var1.setline(724);
               var4 = var1.getlocal(9).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stream"));
               var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("strm"), var4);
               var4 = null;
               var1.setline(725);
               var10000 = var1.getlocal(7);
               PyObject[] var12 = Py.EmptyObjects;
               String[] var5 = new String[0];
               var10000 = var10000._callextra(var12, var5, (PyObject)null, var1.getlocal(9));
               var4 = null;
               var4 = var10000;
               var1.setlocal(12, var4);
               var4 = null;
            }

            var1.setline(726);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(727);
               var1.getlocal(12).__getattr__("setFormatter").__call__(var2, var1.getlocal(2));
            }

            var1.setline(728);
            var3 = var1.getlocal(4);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(729);
               var1.getlocal(12).__getattr__("setLevel").__call__(var2, var1.getglobal("logging").__getattr__("_checkLevel").__call__(var2, var1.getlocal(4)));
            }

            var1.setline(730);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(731);
               var1.getlocal(0).__getattr__("add_filters").__call__(var2, var1.getlocal(12), var1.getlocal(5));
            }

            var1.setline(732);
            var3 = var1.getlocal(12);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(11, var4);
         var1.setline(714);
         if (var1.getglobal("valid_ident").__call__(var2, var1.getlocal(11)).__nonzero__()) {
            var1.setline(714);
            var1.getlocal(10).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(1).__getitem__(var1.getlocal(11))})));
         }
      }
   }

   public PyObject add_handlers$34(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyString.fromInterned("Add handlers to a logger from a list of names.");
      var1.setline(736);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(736);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);

         try {
            var1.setline(738);
            var1.getlocal(1).__getattr__("addHandler").__call__(var2, var1.getlocal(0).__getattr__("config").__getitem__(PyString.fromInterned("handlers")).__getitem__(var1.getlocal(3)));
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("StandardError"))) {
               PyObject var6 = var5.value;
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(740);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unable to add handler %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)}))));
            }

            throw var5;
         }
      }
   }

   public PyObject common_logger_config$35(PyFrame var1, ThreadState var2) {
      var1.setline(745);
      PyString.fromInterned("\n        Perform configuration which is common to root and non-root loggers.\n        ");
      var1.setline(746);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("level"), (PyObject)var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(747);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(748);
         var1.getlocal(1).__getattr__("setLevel").__call__(var2, var1.getglobal("logging").__getattr__("_checkLevel").__call__(var2, var1.getlocal(4)));
      }

      var1.setline(749);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(751);
         var3 = var1.getlocal(1).__getattr__("handlers").__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(751);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(753);
               var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("handlers"), (PyObject)var1.getglobal("None"));
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(754);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(755);
                  var1.getlocal(0).__getattr__("add_handlers").__call__(var2, var1.getlocal(1), var1.getlocal(6));
               }

               var1.setline(756);
               var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filters"), (PyObject)var1.getglobal("None"));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(757);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(758);
                  var1.getlocal(0).__getattr__("add_filters").__call__(var2, var1.getlocal(1), var1.getlocal(7));
               }
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(752);
            var1.getlocal(1).__getattr__("removeHandler").__call__(var2, var1.getlocal(5));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject configure_logger$36(PyFrame var1, ThreadState var2) {
      var1.setline(761);
      PyString.fromInterned("Configure a non-root logger from a dictionary.");
      var1.setline(762);
      PyObject var3 = var1.getglobal("logging").__getattr__("getLogger").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(763);
      var1.getlocal(0).__getattr__("common_logger_config").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
      var1.setline(764);
      var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("propagate"), (PyObject)var1.getglobal("None"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(765);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(766);
         var3 = var1.getlocal(5);
         var1.getlocal(4).__setattr__("propagate", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject configure_root$37(PyFrame var1, ThreadState var2) {
      var1.setline(769);
      PyString.fromInterned("Configure a root logger from a dictionary.");
      var1.setline(770);
      PyObject var3 = var1.getglobal("logging").__getattr__("getLogger").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(771);
      var1.getlocal(0).__getattr__("common_logger_config").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dictConfig$38(PyFrame var1, ThreadState var2) {
      var1.setline(776);
      PyString.fromInterned("Configure logging using a dictionary.");
      var1.setline(777);
      var1.getglobal("dictConfigClass").__call__(var2, var1.getlocal(0)).__getattr__("configure").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listen$39(PyFrame var1, ThreadState var2) {
      var1.setline(789);
      PyString.fromInterned("\n    Start up a socket server on the specified port, and listen for new\n    configurations.\n\n    These will be sent as a file suitable for processing by fileConfig().\n    Returns a Thread object on which you can call start() to start the server,\n    and which you can join() when appropriate. To stop the server, call\n    stopListening().\n    ");
      var1.setline(790);
      if (var1.getglobal("thread").__not__().__nonzero__()) {
         var1.setline(791);
         throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("listen() needs threading to work")));
      } else {
         var1.setline(793);
         PyObject[] var3 = new PyObject[]{var1.getglobal("StreamRequestHandler")};
         PyObject var4 = Py.makeClass("ConfigStreamHandler", var3, ConfigStreamHandler$40);
         var1.setlocal(1, var4);
         var4 = null;
         Arrays.fill(var3, (Object)null);
         var1.setline(842);
         var3 = new PyObject[]{var1.getglobal("ThreadingTCPServer")};
         var4 = Py.makeClass("ConfigSocketReceiver", var3, ConfigSocketReceiver$42);
         var1.setlocal(2, var4);
         var4 = null;
         Arrays.fill(var3, (Object)null);
         var1.setline(872);
         var3 = new PyObject[]{var1.getglobal("threading").__getattr__("Thread")};
         var4 = Py.makeClass("Server", var3, Server$45);
         var1.setderef(0, var4);
         var4 = null;
         Arrays.fill(var3, (Object)null);
         var1.setline(893);
         PyObject var5 = var1.getderef(0).__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(0));
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject ConfigStreamHandler$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n        Handler for a logging configuration request.\n\n        It expects a completely new logging configuration and uses fileConfig\n        to install it.\n        "));
      var1.setline(799);
      PyString.fromInterned("\n        Handler for a logging configuration request.\n\n        It expects a completely new logging configuration and uses fileConfig\n        to install it.\n        ");
      var1.setline(800);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle$41, PyString.fromInterned("\n            Handle a request.\n\n            Each request is expected to be a 4-byte length, packed using\n            struct.pack(\">L\", n), followed by the config file.\n            Uses fileConfig() to do the grunt work.\n            "));
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle$41(PyFrame var1, ThreadState var2) {
      var1.setline(807);
      PyString.fromInterned("\n            Handle a request.\n\n            Each request is expected to be a 4-byte length, packed using\n            struct.pack(\">L\", n), followed by the config file.\n            Uses fileConfig() to do the grunt work.\n            ");
      var1.setline(808);
      PyObject var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var10000;
      PyObject var4;
      try {
         var1.setline(810);
         var3 = var1.getlocal(0).__getattr__("connection");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(811);
         var3 = var1.getlocal(2).__getattr__("recv").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(812);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var3._eq(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(813);
            var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">L"), (PyObject)var1.getlocal(3)).__getitem__(Py.newInteger(0));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(814);
            var3 = var1.getlocal(0).__getattr__("connection").__getattr__("recv").__call__(var2, var1.getlocal(4));
            var1.setlocal(3, var3);
            var3 = null;

            while(true) {
               var1.setline(815);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var10000 = var3._lt(var1.getlocal(4));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  try {
                     var1.setline(818);
                     var3 = imp.importOne("json", var1, -1);
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(819);
                     var3 = var1.getlocal(5).__getattr__("loads").__call__(var2, var1.getlocal(3));
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(820);
                     if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("dict")).__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }

                     var1.setline(821);
                     var1.getglobal("dictConfig").__call__(var2, var1.getlocal(6));
                  } catch (Throwable var6) {
                     Py.setException(var6, var1);
                     var1.setline(825);
                     var4 = var1.getglobal("cStringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(3));
                     var1.setlocal(7, var4);
                     var4 = null;

                     try {
                        var1.setline(827);
                        var1.getglobal("fileConfig").__call__(var2, var1.getlocal(7));
                     } catch (Throwable var5) {
                        PyException var9 = Py.setException(var5, var1);
                        if (var9.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("SystemExit")}))) {
                           var1.setline(829);
                           throw Py.makeException();
                        }

                        var1.setline(831);
                        var1.getglobal("traceback").__getattr__("print_exc").__call__(var2);
                     }
                  }

                  var1.setline(832);
                  if (var1.getlocal(0).__getattr__("server").__getattr__("ready").__nonzero__()) {
                     var1.setline(833);
                     var1.getlocal(0).__getattr__("server").__getattr__("ready").__getattr__("set").__call__(var2);
                  }
                  break;
               }

               var1.setline(816);
               var3 = var1.getlocal(3)._add(var1.getlocal(2).__getattr__("recv").__call__(var2, var1.getlocal(4)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(3)))));
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getglobal("socket").__getattr__("error"))) {
            throw var8;
         }

         var4 = var8.value;
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(835);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(8).__getattr__("args"), var1.getglobal("tuple")).__not__().__nonzero__()) {
            var1.setline(836);
            throw Py.makeException();
         }

         var1.setline(838);
         var4 = var1.getlocal(8).__getattr__("args").__getitem__(Py.newInteger(0));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(839);
         var4 = var1.getlocal(9);
         var10000 = var4._ne(var1.getglobal("RESET_ERROR"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(840);
            throw Py.makeException();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ConfigSocketReceiver$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n        A simple TCP socket-based logging config receiver.\n        "));
      var1.setline(845);
      PyString.fromInterned("\n        A simple TCP socket-based logging config receiver.\n        ");
      var1.setline(847);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("allow_reuse_address", var3);
      var3 = null;
      var1.setline(849);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("localhost"), var1.getname("DEFAULT_LOGGING_CONFIG_PORT"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$43, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(858);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, serve_until_stopped$44, (PyObject)null);
      var1.setlocal("serve_until_stopped", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$43(PyFrame var1, ThreadState var2) {
      var1.setline(851);
      var1.getglobal("ThreadingTCPServer").__getattr__("__init__").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), (PyObject)var1.getlocal(3));
      var1.setline(852);
      var1.getglobal("logging").__getattr__("_acquireLock").__call__(var2);
      var1.setline(853);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"abort", var3);
      var3 = null;
      var1.setline(854);
      var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
      var1.setline(855);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"timeout", var3);
      var3 = null;
      var1.setline(856);
      PyObject var4 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("ready", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject serve_until_stopped$44(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyObject var3 = imp.importOne("select", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(860);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(2, var6);
      var3 = null;

      while(true) {
         var1.setline(861);
         if (!var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(870);
            var1.getlocal(0).__getattr__("socket").__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(862);
         var3 = var1.getlocal(1).__getattr__("select").__call__(var2, new PyList(new PyObject[]{var1.getlocal(0).__getattr__("socket").__getattr__("fileno").__call__(var2)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), var1.getlocal(0).__getattr__("timeout"));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(865);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(866);
            var1.getlocal(0).__getattr__("handle_request").__call__(var2);
         }

         var1.setline(867);
         var1.getglobal("logging").__getattr__("_acquireLock").__call__(var2);
         var1.setline(868);
         var3 = var1.getlocal(0).__getattr__("abort");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(869);
         var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
      }
   }

   public PyObject Server$45(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(874);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = __init__$46;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(881);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$47, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$46(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("__init__").__call__(var2);
      var1.setline(876);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("rcvr", var3);
      var3 = null;
      var1.setline(877);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("hdlr", var3);
      var3 = null;
      var1.setline(878);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(879);
      var3 = var1.getglobal("threading").__getattr__("Event").__call__(var2);
      var1.getlocal(0).__setattr__("ready", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$47(PyFrame var1, ThreadState var2) {
      var1.setline(882);
      PyObject var10000 = var1.getlocal(0).__getattr__("rcvr");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("port"), var1.getlocal(0).__getattr__("hdlr"), var1.getlocal(0).__getattr__("ready")};
      String[] var4 = new String[]{"port", "handler", "ready"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(884);
      var5 = var1.getlocal(0).__getattr__("port");
      var10000 = var5._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(885);
         var5 = var1.getlocal(1).__getattr__("server_address").__getitem__(Py.newInteger(1));
         var1.getlocal(0).__setattr__("port", var5);
         var3 = null;
      }

      var1.setline(886);
      var1.getlocal(0).__getattr__("ready").__getattr__("set").__call__(var2);
      var1.setline(888);
      var1.getglobal("logging").__getattr__("_acquireLock").__call__(var2);
      var1.setline(889);
      var5 = var1.getlocal(1);
      var1.setglobal("_listener", var5);
      var3 = null;
      var1.setline(890);
      var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
      var1.setline(891);
      var1.getlocal(1).__getattr__("serve_until_stopped").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopListening$48(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyString.fromInterned("\n    Stop the listening server which was created with a call to listen().\n    ");
      var1.setline(900);
      var1.getglobal("logging").__getattr__("_acquireLock").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(902);
         if (var1.getglobal("_listener").__nonzero__()) {
            var1.setline(903);
            PyInteger var4 = Py.newInteger(1);
            var1.getglobal("_listener").__setattr__((String)"abort", var4);
            var4 = null;
            var1.setline(904);
            PyObject var6 = var1.getglobal("None");
            var1.setglobal("_listener", var6);
            var4 = null;
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(906);
         var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(906);
      var1.getglobal("logging").__getattr__("_releaseLock").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public config$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fname", "defaults", "disable_existing_loggers", "ConfigParser", "cp", "formatters", "handlers"};
      fileConfig$1 = Py.newCode(3, var2, var1, "fileConfig", 53, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "used", "found", "n"};
      _resolve$2 = Py.newCode(1, var2, var1, "_resolve", 84, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"alist"};
      _strip_spaces$3 = Py.newCode(1, var2, var1, "_strip_spaces", 98, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$4 = Py.newCode(1, var2, var1, "<lambda>", 99, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      _encoded$5 = Py.newCode(1, var2, var1, "_encoded", 101, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cp", "flist", "formatters", "form", "sectname", "opts", "fs", "dfs", "c", "class_name", "f"};
      _create_formatters$6 = Py.newCode(1, var2, var1, "_create_formatters", 104, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cp", "formatters", "hlist", "handlers", "fixups", "hand", "sectname", "klass", "opts", "fmt", "args", "h", "level", "target", "t"};
      _install_handlers$7 = Py.newCode(2, var2, var1, "_install_handlers", 133, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cp", "handlers", "disable_existing_loggers", "llist", "sectname", "root", "log", "opts", "level", "h", "hlist", "hand", "existing", "child_loggers", "qn", "propagate", "logger", "i", "prefixed", "pflen", "num_existing"};
      _install_loggers$8 = Py.newCode(3, var2, var1, "_install_loggers", 176, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$9 = Py.newCode(1, var2, var1, "<lambda>", 182, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "m"};
      valid_ident$10 = Py.newCode(1, var2, var1, "valid_ident", 271, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConvertingDict$11 = Py.newCode(0, var2, var1, "ConvertingDict", 287, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "value", "result"};
      __getitem__$12 = Py.newCode(2, var2, var1, "__getitem__", 290, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default", "value", "result"};
      get$13 = Py.newCode(3, var2, var1, "get", 302, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default", "value", "result"};
      pop$14 = Py.newCode(3, var2, var1, "pop", 314, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConvertingList$15 = Py.newCode(0, var2, var1, "ConvertingList", 324, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "value", "result"};
      __getitem__$16 = Py.newCode(2, var2, var1, "__getitem__", 326, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "idx", "value", "result"};
      pop$17 = Py.newCode(2, var2, var1, "pop", 338, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConvertingTuple$18 = Py.newCode(0, var2, var1, "ConvertingTuple", 347, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "value", "result"};
      __getitem__$19 = Py.newCode(2, var2, var1, "__getitem__", 349, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseConfigurator$20 = Py.newCode(0, var2, var1, "BaseConfigurator", 359, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "config"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 379, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "name", "used", "found", "frag", "e", "tb", "v"};
      resolve$22 = Py.newCode(2, var2, var1, "resolve", 383, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      ext_convert$23 = Py.newCode(2, var2, var1, "ext_convert", 406, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "rest", "m", "d", "idx", "n"};
      cfg_convert$24 = Py.newCode(2, var2, var1, "cfg_convert", 410, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "m", "d", "prefix", "converter", "suffix"};
      convert$25 = Py.newCode(2, var2, var1, "convert", 444, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config", "c", "props", "kwargs", "_[479_23]", "k", "result", "name", "value"};
      configure_custom$26 = Py.newCode(2, var2, var1, "configure_custom", 472, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      as_tuple$27 = Py.newCode(2, var2, var1, "as_tuple", 486, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DictConfigurator$28 = Py.newCode(0, var2, var1, "DictConfigurator", 492, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "config", "incremental", "EMPTY_DICT", "handlers", "name", "handler", "handler_config", "level", "e", "loggers", "root", "disable_existing", "formatters", "filters", "existing", "child_loggers", "i", "prefixed", "pflen", "num_existing", "log", "logger"};
      configure$29 = Py.newCode(1, var2, var1, "configure", 498, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config", "factory", "result", "te", "fmt", "dfmt"};
      configure_formatter$30 = Py.newCode(2, var2, var1, "configure_formatter", 642, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config", "result", "name"};
      configure_filter$31 = Py.newCode(2, var2, var1, "configure_filter", 664, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filterer", "filters", "f", "e"};
      add_filters$32 = Py.newCode(3, var2, var1, "add_filters", 673, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config", "formatter", "e", "level", "filters", "c", "factory", "klass", "kwargs", "_[714_23]", "k", "result", "te"};
      configure_handler$33 = Py.newCode(2, var2, var1, "configure_handler", 681, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "logger", "handlers", "h", "e"};
      add_handlers$34 = Py.newCode(3, var2, var1, "add_handlers", 734, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "logger", "config", "incremental", "level", "h", "handlers", "filters"};
      common_logger_config$35 = Py.newCode(4, var2, var1, "common_logger_config", 742, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "config", "incremental", "logger", "propagate"};
      configure_logger$36 = Py.newCode(4, var2, var1, "configure_logger", 760, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config", "incremental", "root"};
      configure_root$37 = Py.newCode(3, var2, var1, "configure_root", 768, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"config"};
      dictConfig$38 = Py.newCode(1, var2, var1, "dictConfig", 775, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"port", "ConfigStreamHandler", "ConfigSocketReceiver", "Server"};
      String[] var10001 = var2;
      config$py var10007 = self;
      var2 = new String[]{"Server"};
      listen$39 = Py.newCode(1, var10001, var1, "listen", 780, false, false, var10007, 39, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      ConfigStreamHandler$40 = Py.newCode(0, var2, var1, "ConfigStreamHandler", 793, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tempfile", "conn", "chunk", "slen", "json", "d", "file", "e", "errcode"};
      handle$41 = Py.newCode(1, var2, var1, "handle", 800, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConfigSocketReceiver$42 = Py.newCode(0, var2, var1, "ConfigSocketReceiver", 842, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "handler", "ready"};
      __init__$43 = Py.newCode(5, var2, var1, "__init__", 849, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "select", "abort", "rd", "wr", "ex"};
      serve_until_stopped$44 = Py.newCode(1, var2, var1, "serve_until_stopped", 858, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Server$45 = Py.newCode(0, var2, var1, "Server", 872, false, false, self, 45, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "rcvr", "hdlr", "port"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Server"};
      __init__$46 = Py.newCode(4, var10001, var1, "__init__", 874, false, false, var10007, 46, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "server"};
      run$47 = Py.newCode(1, var2, var1, "run", 881, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      stopListening$48 = Py.newCode(0, var2, var1, "stopListening", 895, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new config$py("logging/config$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(config$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.fileConfig$1(var2, var3);
         case 2:
            return this._resolve$2(var2, var3);
         case 3:
            return this._strip_spaces$3(var2, var3);
         case 4:
            return this.f$4(var2, var3);
         case 5:
            return this._encoded$5(var2, var3);
         case 6:
            return this._create_formatters$6(var2, var3);
         case 7:
            return this._install_handlers$7(var2, var3);
         case 8:
            return this._install_loggers$8(var2, var3);
         case 9:
            return this.f$9(var2, var3);
         case 10:
            return this.valid_ident$10(var2, var3);
         case 11:
            return this.ConvertingDict$11(var2, var3);
         case 12:
            return this.__getitem__$12(var2, var3);
         case 13:
            return this.get$13(var2, var3);
         case 14:
            return this.pop$14(var2, var3);
         case 15:
            return this.ConvertingList$15(var2, var3);
         case 16:
            return this.__getitem__$16(var2, var3);
         case 17:
            return this.pop$17(var2, var3);
         case 18:
            return this.ConvertingTuple$18(var2, var3);
         case 19:
            return this.__getitem__$19(var2, var3);
         case 20:
            return this.BaseConfigurator$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.resolve$22(var2, var3);
         case 23:
            return this.ext_convert$23(var2, var3);
         case 24:
            return this.cfg_convert$24(var2, var3);
         case 25:
            return this.convert$25(var2, var3);
         case 26:
            return this.configure_custom$26(var2, var3);
         case 27:
            return this.as_tuple$27(var2, var3);
         case 28:
            return this.DictConfigurator$28(var2, var3);
         case 29:
            return this.configure$29(var2, var3);
         case 30:
            return this.configure_formatter$30(var2, var3);
         case 31:
            return this.configure_filter$31(var2, var3);
         case 32:
            return this.add_filters$32(var2, var3);
         case 33:
            return this.configure_handler$33(var2, var3);
         case 34:
            return this.add_handlers$34(var2, var3);
         case 35:
            return this.common_logger_config$35(var2, var3);
         case 36:
            return this.configure_logger$36(var2, var3);
         case 37:
            return this.configure_root$37(var2, var3);
         case 38:
            return this.dictConfig$38(var2, var3);
         case 39:
            return this.listen$39(var2, var3);
         case 40:
            return this.ConfigStreamHandler$40(var2, var3);
         case 41:
            return this.handle$41(var2, var3);
         case 42:
            return this.ConfigSocketReceiver$42(var2, var3);
         case 43:
            return this.__init__$43(var2, var3);
         case 44:
            return this.serve_until_stopped$44(var2, var3);
         case 45:
            return this.Server$45(var2, var3);
         case 46:
            return this.__init__$46(var2, var3);
         case 47:
            return this.run$47(var2, var3);
         case 48:
            return this.stopListening$48(var2, var3);
         default:
            return null;
      }
   }
}
