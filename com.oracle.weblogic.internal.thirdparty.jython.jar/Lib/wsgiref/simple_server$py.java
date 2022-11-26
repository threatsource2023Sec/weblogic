package wsgiref;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
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
@Filename("wsgiref/simple_server.py")
public class simple_server$py extends PyFunctionTable implements PyRunnable {
   static simple_server$py self;
   static final PyCode f$0;
   static final PyCode ServerHandler$1;
   static final PyCode close$2;
   static final PyCode WSGIServer$3;
   static final PyCode server_bind$4;
   static final PyCode setup_environ$5;
   static final PyCode get_app$6;
   static final PyCode set_app$7;
   static final PyCode WSGIRequestHandler$8;
   static final PyCode get_environ$9;
   static final PyCode get_stderr$10;
   static final PyCode handle$11;
   static final PyCode demo_app$12;
   static final PyCode make_server$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("BaseHTTPServer that implements the Python WSGI protocol (PEP 333, rev 1.21)\n\nThis is both an example of how WSGI can be implemented, and a basis for running\nsimple web applications on a local machine, such as might be done when testing\nor debugging an application.  It has not been reviewed for security issues,\nhowever, and we strongly recommend that you use a \"real\" web server for\nproduction use.\n\nFor example usage, see the 'if __name__==\"__main__\"' block at the end of the\nmodule.  See also the BaseHTTPServer module docs for other API information.\n"));
      var1.setline(11);
      PyString.fromInterned("BaseHTTPServer that implements the Python WSGI protocol (PEP 333, rev 1.21)\n\nThis is both an example of how WSGI can be implemented, and a basis for running\nsimple web applications on a local machine, such as might be done when testing\nor debugging an application.  It has not been reviewed for security issues,\nhowever, and we strongly recommend that you use a \"real\" web server for\nproduction use.\n\nFor example usage, see the 'if __name__==\"__main__\"' block at the end of the\nmodule.  See also the BaseHTTPServer module docs for other API information.\n");
      var1.setline(13);
      String[] var3 = new String[]{"BaseHTTPRequestHandler", "HTTPServer"};
      PyObject[] var5 = imp.importFrom("BaseHTTPServer", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("BaseHTTPRequestHandler", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("HTTPServer", var4);
      var4 = null;
      var1.setline(14);
      PyObject var6 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var6);
      var3 = null;
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(15);
      var3 = new String[]{"SimpleHandler"};
      var5 = imp.importFrom("wsgiref.handlers", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("SimpleHandler", var4);
      var4 = null;
      var1.setline(17);
      PyString var7 = PyString.fromInterned("0.1");
      var1.setlocal("__version__", var7);
      var3 = null;
      var1.setline(18);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("WSGIServer"), PyString.fromInterned("WSGIRequestHandler"), PyString.fromInterned("demo_app"), PyString.fromInterned("make_server")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(21);
      var6 = PyString.fromInterned("WSGIServer/")._add(var1.getname("__version__"));
      var1.setlocal("server_version", var6);
      var3 = null;
      var1.setline(22);
      var6 = PyString.fromInterned("Python/")._add(var1.getname("sys").__getattr__("version").__getattr__("split").__call__(var2).__getitem__(Py.newInteger(0)));
      var1.setlocal("sys_version", var6);
      var3 = null;
      var1.setline(23);
      var6 = var1.getname("server_version")._add(PyString.fromInterned(" "))._add(var1.getname("sys_version"));
      var1.setlocal("software_version", var6);
      var3 = null;
      var1.setline(26);
      var5 = new PyObject[]{var1.getname("SimpleHandler")};
      var4 = Py.makeClass("ServerHandler", var5, ServerHandler$1);
      var1.setlocal("ServerHandler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(40);
      var5 = new PyObject[]{var1.getname("HTTPServer")};
      var4 = Py.makeClass("WSGIServer", var5, WSGIServer$3);
      var1.setlocal("WSGIServer", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(69);
      var5 = new PyObject[]{var1.getname("BaseHTTPRequestHandler")};
      var4 = Py.makeClass("WSGIRequestHandler", var5, WSGIRequestHandler$8);
      var1.setlocal("WSGIRequestHandler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(128);
      var5 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var5, demo_app$12, (PyObject)null);
      var1.setlocal("demo_app", var9);
      var3 = null;
      var1.setline(140);
      var5 = new PyObject[]{var1.getname("WSGIServer"), var1.getname("WSGIRequestHandler")};
      var9 = new PyFunction(var1.f_globals, var5, make_server$13, PyString.fromInterned("Create a new WSGI server listening on `host` and `port` for `app`"));
      var1.setlocal("make_server", var9);
      var3 = null;
      var1.setline(149);
      var6 = var1.getname("__name__");
      PyObject var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(150);
         var6 = var1.getname("make_server").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)Py.newInteger(8000), (PyObject)var1.getname("demo_app"));
         var1.setlocal("httpd", var6);
         var3 = null;
         var1.setline(151);
         var6 = var1.getname("httpd").__getattr__("socket").__getattr__("getsockname").__call__(var2);
         var1.setlocal("sa", var6);
         var3 = null;
         var1.setline(152);
         Py.printComma(PyString.fromInterned("Serving HTTP on"));
         Py.printComma(var1.getname("sa").__getitem__(Py.newInteger(0)));
         Py.printComma(PyString.fromInterned("port"));
         Py.printComma(var1.getname("sa").__getitem__(Py.newInteger(1)));
         Py.println(PyString.fromInterned("..."));
         var1.setline(153);
         var6 = imp.importOne("webbrowser", var1, -1);
         var1.setlocal("webbrowser", var6);
         var3 = null;
         var1.setline(154);
         var1.getname("webbrowser").__getattr__("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://localhost:8000/xyz?abc"));
         var1.setline(155);
         var1.getname("httpd").__getattr__("handle_request").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ServerHandler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(28);
      PyObject var3 = var1.getname("software_version");
      var1.setlocal("server_software", var3);
      var3 = null;
      var1.setline(30);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, close$2, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject close$2(PyFrame var1, ThreadState var2) {
      Object var3 = null;

      try {
         var1.setline(32);
         var1.getlocal(0).__getattr__("request_handler").__getattr__("log_request").__call__(var2, var1.getlocal(0).__getattr__("status").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("bytes_sent"));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(36);
         var1.getglobal("SimpleHandler").__getattr__("close").__call__(var2, var1.getlocal(0));
         throw (Throwable)var4;
      }

      var1.setline(36);
      var1.getglobal("SimpleHandler").__getattr__("close").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject WSGIServer$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("BaseHTTPServer that implements the Python WSGI protocol"));
      var1.setline(42);
      PyString.fromInterned("BaseHTTPServer that implements the Python WSGI protocol");
      var1.setline(44);
      PyObject var3 = var1.getname("None");
      var1.setlocal("application", var3);
      var3 = null;
      var1.setline(46);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, server_bind$4, PyString.fromInterned("Override server_bind to store the server name."));
      var1.setlocal("server_bind", var5);
      var3 = null;
      var1.setline(51);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setup_environ$5, (PyObject)null);
      var1.setlocal("setup_environ", var5);
      var3 = null;
      var1.setline(61);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_app$6, (PyObject)null);
      var1.setlocal("get_app", var5);
      var3 = null;
      var1.setline(64);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_app$7, (PyObject)null);
      var1.setlocal("set_app", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject server_bind$4(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyString.fromInterned("Override server_bind to store the server name.");
      var1.setline(48);
      var1.getglobal("HTTPServer").__getattr__("server_bind").__call__(var2, var1.getlocal(0));
      var1.setline(49);
      var1.getlocal(0).__getattr__("setup_environ").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setup_environ$5(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var1.getlocal(0).__setattr__((String)"base_environ", var3);
      var1.setline(54);
      PyObject var4 = var1.getlocal(0).__getattr__("server_name");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("SERVER_NAME"), var4);
      var3 = null;
      var1.setline(55);
      PyString var5 = PyString.fromInterned("CGI/1.1");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("GATEWAY_INTERFACE"), var5);
      var3 = null;
      var1.setline(56);
      var4 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("server_port"));
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("SERVER_PORT"), var4);
      var3 = null;
      var1.setline(57);
      var5 = PyString.fromInterned("");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("REMOTE_HOST"), var5);
      var3 = null;
      var1.setline(58);
      var5 = PyString.fromInterned("");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("CONTENT_LENGTH"), var5);
      var3 = null;
      var1.setline(59);
      var5 = PyString.fromInterned("");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("SCRIPT_NAME"), var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_app$6(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0).__getattr__("application");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_app$7(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("application", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject WSGIRequestHandler$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(71);
      PyObject var3 = PyString.fromInterned("WSGIServer/")._add(var1.getname("__version__"));
      var1.setlocal("server_version", var3);
      var3 = null;
      var1.setline(73);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, get_environ$9, (PyObject)null);
      var1.setlocal("get_environ", var5);
      var3 = null;
      var1.setline(110);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_stderr$10, (PyObject)null);
      var1.setlocal("get_stderr", var5);
      var3 = null;
      var1.setline(113);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle$11, PyString.fromInterned("Handle a single HTTP request"));
      var1.setlocal("handle", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject get_environ$9(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getlocal(0).__getattr__("server").__getattr__("base_environ").__getattr__("copy").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getlocal(0).__getattr__("request_version");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("SERVER_PROTOCOL"), var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getlocal(0).__getattr__("command");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("REQUEST_METHOD"), var3);
      var3 = null;
      var1.setline(77);
      PyString var9 = PyString.fromInterned("?");
      PyObject var10000 = var9._in(var1.getlocal(0).__getattr__("path"));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var3 = var1.getlocal(0).__getattr__("path").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(80);
         PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("path"), PyString.fromInterned("")});
         var4 = Py.unpackSequence(var10, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(82);
      var3 = var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(2));
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("PATH_INFO"), var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(3);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("QUERY_STRING"), var3);
      var3 = null;
      var1.setline(85);
      var3 = var1.getlocal(0).__getattr__("address_string").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(86);
      var3 = var1.getlocal(4);
      var10000 = var3._ne(var1.getlocal(0).__getattr__("client_address").__getitem__(Py.newInteger(0)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(87);
         var3 = var1.getlocal(4);
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("REMOTE_HOST"), var3);
         var3 = null;
      }

      var1.setline(88);
      var3 = var1.getlocal(0).__getattr__("client_address").__getitem__(Py.newInteger(0));
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("REMOTE_ADDR"), var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getlocal(0).__getattr__("headers").__getattr__("typeheader");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         var3 = var1.getlocal(0).__getattr__("headers").__getattr__("type");
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("CONTENT_TYPE"), var3);
         var3 = null;
      } else {
         var1.setline(93);
         var3 = var1.getlocal(0).__getattr__("headers").__getattr__("typeheader");
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("CONTENT_TYPE"), var3);
         var3 = null;
      }

      var1.setline(95);
      var3 = var1.getlocal(0).__getattr__("headers").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-length"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(96);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(97);
         var3 = var1.getlocal(5);
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("CONTENT_LENGTH"), var3);
         var3 = null;
      }

      var1.setline(99);
      var3 = var1.getlocal(0).__getattr__("headers").__getattr__("headers").__iter__();

      while(true) {
         var1.setline(99);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(108);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(6, var8);
         var1.setline(100);
         var5 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(7, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(8, var7);
         var7 = null;
         var5 = null;
         var1.setline(101);
         var5 = var1.getlocal(7).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_")).__getattr__("upper").__call__(var2);
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(101);
         var5 = var1.getlocal(8).__getattr__("strip").__call__(var2);
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(102);
         var5 = var1.getlocal(7);
         var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(104);
            var5 = PyString.fromInterned("HTTP_")._add(var1.getlocal(7));
            var10000 = var5._in(var1.getlocal(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(105);
               var10000 = var1.getlocal(1);
               var5 = PyString.fromInterned("HTTP_")._add(var1.getlocal(7));
               PyObject var11 = var10000;
               var7 = var11.__getitem__(var5);
               var7 = var7._iadd(PyString.fromInterned(",")._add(var1.getlocal(8)));
               var11.__setitem__(var5, var7);
            } else {
               var1.setline(107);
               var5 = var1.getlocal(8);
               var1.getlocal(1).__setitem__(PyString.fromInterned("HTTP_")._add(var1.getlocal(7)), var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject get_stderr$10(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getglobal("sys").__getattr__("stderr");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handle$11(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyString.fromInterned("Handle a single HTTP request");
      var1.setline(116);
      PyObject var3 = var1.getlocal(0).__getattr__("rfile").__getattr__("readline").__call__(var2);
      var1.getlocal(0).__setattr__("raw_requestline", var3);
      var3 = null;
      var1.setline(117);
      if (var1.getlocal(0).__getattr__("parse_request").__call__(var2).__not__().__nonzero__()) {
         var1.setline(118);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(120);
         var3 = var1.getglobal("ServerHandler").__call__(var2, var1.getlocal(0).__getattr__("rfile"), var1.getlocal(0).__getattr__("wfile"), var1.getlocal(0).__getattr__("get_stderr").__call__(var2), var1.getlocal(0).__getattr__("get_environ").__call__(var2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(123);
         var3 = var1.getlocal(0);
         var1.getlocal(1).__setattr__("request_handler", var3);
         var3 = null;
         var1.setline(124);
         var1.getlocal(1).__getattr__("run").__call__(var2, var1.getlocal(0).__getattr__("server").__getattr__("get_app").__call__(var2));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject demo_app$12(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      String[] var3 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("StringIO", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(130);
      PyObject var8 = var1.getlocal(2).__call__(var2);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(131);
      var8 = var1.getlocal(3);
      Py.println(var8, PyString.fromInterned("Hello world!"));
      var1.setline(132);
      var8 = var1.getlocal(3);
      Py.printlnv(var8);
      var1.setline(133);
      var8 = var1.getlocal(0).__getattr__("items").__call__(var2);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(133);
      var1.getlocal(4).__getattr__("sort").__call__(var2);
      var1.setline(134);
      var8 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(134);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(136);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("200 OK"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("text/plain")})})));
            var1.setline(137);
            PyList var10 = new PyList(new PyObject[]{var1.getlocal(3).__getattr__("getvalue").__call__(var2)});
            var1.f_lasti = -1;
            return var10;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(135);
         PyObject var9 = var1.getlocal(3);
         Py.printComma(var9, var1.getlocal(5));
         Py.printComma(var9, PyString.fromInterned("="));
         Py.println(var9, var1.getglobal("repr").__call__(var2, var1.getlocal(6)));
      }
   }

   public PyObject make_server$13(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Create a new WSGI server listening on `host` and `port` for `app`");
      var1.setline(144);
      PyObject var3 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})), (PyObject)var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(145);
      var1.getlocal(5).__getattr__("set_app").__call__(var2, var1.getlocal(2));
      var1.setline(146);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public simple_server$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ServerHandler$1 = Py.newCode(0, var2, var1, "ServerHandler", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      close$2 = Py.newCode(1, var2, var1, "close", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WSGIServer$3 = Py.newCode(0, var2, var1, "WSGIServer", 40, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      server_bind$4 = Py.newCode(1, var2, var1, "server_bind", 46, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "env"};
      setup_environ$5 = Py.newCode(1, var2, var1, "setup_environ", 51, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_app$6 = Py.newCode(1, var2, var1, "get_app", 61, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "application"};
      set_app$7 = Py.newCode(2, var2, var1, "set_app", 64, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WSGIRequestHandler$8 = Py.newCode(0, var2, var1, "WSGIRequestHandler", 69, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "env", "path", "query", "host", "length", "h", "k", "v"};
      get_environ$9 = Py.newCode(1, var2, var1, "get_environ", 73, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stderr$10 = Py.newCode(1, var2, var1, "get_stderr", 110, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      handle$11 = Py.newCode(1, var2, var1, "handle", 113, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "start_response", "StringIO", "stdout", "h", "k", "v"};
      demo_app$12 = Py.newCode(2, var2, var1, "demo_app", 128, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"host", "port", "app", "server_class", "handler_class", "server"};
      make_server$13 = Py.newCode(5, var2, var1, "make_server", 140, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new simple_server$py("wsgiref/simple_server$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(simple_server$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ServerHandler$1(var2, var3);
         case 2:
            return this.close$2(var2, var3);
         case 3:
            return this.WSGIServer$3(var2, var3);
         case 4:
            return this.server_bind$4(var2, var3);
         case 5:
            return this.setup_environ$5(var2, var3);
         case 6:
            return this.get_app$6(var2, var3);
         case 7:
            return this.set_app$7(var2, var3);
         case 8:
            return this.WSGIRequestHandler$8(var2, var3);
         case 9:
            return this.get_environ$9(var2, var3);
         case 10:
            return this.get_stderr$10(var2, var3);
         case 11:
            return this.handle$11(var2, var3);
         case 12:
            return this.demo_app$12(var2, var3);
         case 13:
            return this.make_server$13(var2, var3);
         default:
            return null;
      }
   }
}
