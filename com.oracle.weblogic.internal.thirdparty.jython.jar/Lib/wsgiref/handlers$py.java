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
@Filename("wsgiref/handlers.py")
public class handlers$py extends PyFunctionTable implements PyRunnable {
   static handlers$py self;
   static final PyCode f$0;
   static final PyCode dict$1;
   static final PyCode format_date_time$2;
   static final PyCode BaseHandler$3;
   static final PyCode run$4;
   static final PyCode setup_environ$5;
   static final PyCode finish_response$6;
   static final PyCode get_scheme$7;
   static final PyCode set_content_length$8;
   static final PyCode cleanup_headers$9;
   static final PyCode start_response$10;
   static final PyCode send_preamble$11;
   static final PyCode write$12;
   static final PyCode sendfile$13;
   static final PyCode finish_content$14;
   static final PyCode close$15;
   static final PyCode send_headers$16;
   static final PyCode result_is_file$17;
   static final PyCode client_is_modern$18;
   static final PyCode log_exception$19;
   static final PyCode handle_error$20;
   static final PyCode error_output$21;
   static final PyCode _write$22;
   static final PyCode _flush$23;
   static final PyCode get_stdin$24;
   static final PyCode get_stderr$25;
   static final PyCode add_cgi_vars$26;
   static final PyCode SimpleHandler$27;
   static final PyCode __init__$28;
   static final PyCode get_stdin$29;
   static final PyCode get_stderr$30;
   static final PyCode add_cgi_vars$31;
   static final PyCode _write$32;
   static final PyCode _flush$33;
   static final PyCode BaseCGIHandler$34;
   static final PyCode CGIHandler$35;
   static final PyCode __init__$36;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Base classes for server/gateway implementations"));
      var1.setline(1);
      PyString.fromInterned("Base classes for server/gateway implementations");
      var1.setline(3);
      String[] var3 = new String[]{"StringType"};
      PyObject[] var6 = imp.importFrom("types", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringType", var4);
      var4 = null;
      var1.setline(4);
      var3 = new String[]{"FileWrapper", "guess_scheme", "is_hop_by_hop"};
      var6 = imp.importFrom("util", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal("FileWrapper", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("guess_scheme", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("is_hop_by_hop", var4);
      var4 = null;
      var1.setline(5);
      var3 = new String[]{"Headers"};
      var6 = imp.importFrom("headers", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal("Headers", var4);
      var4 = null;
      var1.setline(7);
      PyObject var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var7 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var7);
      var3 = null;
      var1.setline(9);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("BaseHandler"), PyString.fromInterned("SimpleHandler"), PyString.fromInterned("BaseCGIHandler"), PyString.fromInterned("CGIHandler")});
      var1.setlocal("__all__", var8);
      var3 = null;

      try {
         var1.setline(12);
         var1.getname("dict");
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("NameError"))) {
            throw var10;
         }

         var1.setline(14);
         PyObject[] var9 = Py.EmptyObjects;
         PyFunction var11 = new PyFunction(var1.f_globals, var9, dict$1, (PyObject)null);
         var1.setlocal("dict", var11);
         var4 = null;
      }

      var1.setline(30);
      var8 = new PyList(new PyObject[]{PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")});
      var1.setlocal("_weekdayname", var8);
      var3 = null;
      var1.setline(31);
      var8 = new PyList(new PyObject[]{var1.getname("None"), PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")});
      var1.setlocal("_monthname", var8);
      var3 = null;
      var1.setline(35);
      var6 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var6, format_date_time$2, (PyObject)null);
      var1.setlocal("format_date_time", var12);
      var3 = null;
      var1.setline(42);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("BaseHandler", var6, BaseHandler$3);
      var1.setlocal("BaseHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(358);
      var6 = new PyObject[]{var1.getname("BaseHandler")};
      var4 = Py.makeClass("SimpleHandler", var6, SimpleHandler$27);
      var1.setlocal("SimpleHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(399);
      var6 = new PyObject[]{var1.getname("SimpleHandler")};
      var4 = Py.makeClass("BaseCGIHandler", var6, BaseCGIHandler$34);
      var1.setlocal("BaseCGIHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(423);
      var6 = new PyObject[]{var1.getname("BaseCGIHandler")};
      var4 = Py.makeClass("CGIHandler", var6, CGIHandler$35);
      var1.setlocal("CGIHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dict$1(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(16);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(16);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(18);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(17);
         PyObject var8 = var1.getlocal(3);
         var1.getlocal(1).__setitem__(var1.getlocal(2), var8);
         var5 = null;
      }
   }

   public PyObject format_date_time$2(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 9);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[7];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[8];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(37);
      var3 = PyString.fromInterned("%s, %02d %3s %4d %02d:%02d:%02d GMT")._mod(new PyTuple(new PyObject[]{var1.getglobal("_weekdayname").__getitem__(var1.getlocal(7)), var1.getlocal(3), var1.getglobal("_monthname").__getitem__(var1.getlocal(2)), var1.getlocal(1), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BaseHandler$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Manage the invocation of a WSGI application"));
      var1.setline(43);
      PyString.fromInterned("Manage the invocation of a WSGI application");
      var1.setline(46);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0)});
      var1.setlocal("wsgi_version", var3);
      var3 = null;
      var1.setline(47);
      PyObject var4 = var1.getname("True");
      var1.setlocal("wsgi_multithread", var4);
      var3 = null;
      var1.setline(48);
      var4 = var1.getname("True");
      var1.setlocal("wsgi_multiprocess", var4);
      var3 = null;
      var1.setline(49);
      var4 = var1.getname("False");
      var1.setlocal("wsgi_run_once", var4);
      var3 = null;
      var1.setline(51);
      var4 = var1.getname("True");
      var1.setlocal("origin_server", var4);
      var3 = null;
      var1.setline(52);
      PyString var5 = PyString.fromInterned("1.0");
      var1.setlocal("http_version", var5);
      var3 = null;
      var1.setline(53);
      var4 = var1.getname("None");
      var1.setlocal("server_software", var4);
      var3 = null;
      var1.setline(58);
      var4 = var1.getname("dict").__call__(var2, var1.getname("os").__getattr__("environ").__getattr__("items").__call__(var2));
      var1.setlocal("os_environ", var4);
      var3 = null;
      var1.setline(61);
      var4 = var1.getname("FileWrapper");
      var1.setlocal("wsgi_file_wrapper", var4);
      var3 = null;
      var1.setline(62);
      var4 = var1.getname("Headers");
      var1.setlocal("headers_class", var4);
      var3 = null;
      var1.setline(65);
      var4 = var1.getname("None");
      var1.setlocal("traceback_limit", var4);
      var3 = null;
      var1.setline(66);
      var5 = PyString.fromInterned("500 Internal Server Error");
      var1.setlocal("error_status", var5);
      var3 = null;
      var1.setline(67);
      PyList var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("text/plain")})});
      var1.setlocal("error_headers", var6);
      var3 = null;
      var1.setline(68);
      var5 = PyString.fromInterned("A server error occurred.  Please contact the administrator.");
      var1.setlocal("error_body", var5);
      var3 = null;
      var1.setline(71);
      var4 = var1.getname("None");
      var1.setlocal("status", var4);
      var1.setlocal("result", var4);
      var1.setline(72);
      var4 = var1.getname("False");
      var1.setlocal("headers_sent", var4);
      var3 = null;
      var1.setline(73);
      var4 = var1.getname("None");
      var1.setlocal("headers", var4);
      var3 = null;
      var1.setline(74);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal("bytes_sent", var7);
      var3 = null;
      var1.setline(76);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, run$4, PyString.fromInterned("Invoke the application"));
      var1.setlocal("run", var9);
      var3 = null;
      var1.setline(96);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, setup_environ$5, PyString.fromInterned("Set up the environment for one request"));
      var1.setlocal("setup_environ", var9);
      var3 = null;
      var1.setline(117);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, finish_response$6, PyString.fromInterned("Send any iterable data, then close self and the iterable\n\n        Subclasses intended for use in asynchronous servers will\n        want to redefine this method, such that it sets up callbacks\n        in the event loop to iterate over the data, and to call\n        'self.close()' once the response is finished.\n        "));
      var1.setlocal("finish_response", var9);
      var3 = null;
      var1.setline(134);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, get_scheme$7, PyString.fromInterned("Return the URL scheme being used"));
      var1.setlocal("get_scheme", var9);
      var3 = null;
      var1.setline(139);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, set_content_length$8, PyString.fromInterned("Compute Content-Length or switch to chunked encoding if possible"));
      var1.setlocal("set_content_length", var9);
      var3 = null;
      var1.setline(152);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, cleanup_headers$9, PyString.fromInterned("Make any necessary header changes or defaults\n\n        Subclasses can extend this to add other defaults.\n        "));
      var1.setlocal("cleanup_headers", var9);
      var3 = null;
      var1.setline(160);
      var8 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var8, start_response$10, PyString.fromInterned("'start_response()' callable as specified by PEP 333"));
      var1.setlocal("start_response", var9);
      var3 = null;
      var1.setline(187);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, send_preamble$11, PyString.fromInterned("Transmit version/status/date/server, via self._write()"));
      var1.setlocal("send_preamble", var9);
      var3 = null;
      var1.setline(201);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, write$12, PyString.fromInterned("'write()' callable as specified by PEP 333"));
      var1.setlocal("write", var9);
      var3 = null;
      var1.setline(221);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, sendfile$13, PyString.fromInterned("Platform-specific file transmission\n\n        Override this method in subclasses to support platform-specific\n        file transmission.  It is only called if the application's\n        return iterable ('self.result') is an instance of\n        'self.wsgi_file_wrapper'.\n\n        This method should return a true value if it was able to actually\n        transmit the wrapped file-like object using a platform-specific\n        approach.  It should return a false value if normal iteration\n        should be used instead.  An exception can be raised to indicate\n        that transmission was attempted, but failed.\n\n        NOTE: this method should call 'self.send_headers()' if\n        'self.headers_sent' is false and it is going to attempt direct\n        transmission of the file.\n        "));
      var1.setlocal("sendfile", var9);
      var3 = null;
      var1.setline(242);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, finish_content$14, PyString.fromInterned("Ensure headers and content have both been sent"));
      var1.setlocal("finish_content", var9);
      var3 = null;
      var1.setline(252);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, close$15, PyString.fromInterned("Close the iterable (if needed) and reset all instance vars\n\n        Subclasses may want to also drop the client connection.\n        "));
      var1.setlocal("close", var9);
      var3 = null;
      var1.setline(265);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, send_headers$16, PyString.fromInterned("Transmit headers to the client, via self._write()"));
      var1.setlocal("send_headers", var9);
      var3 = null;
      var1.setline(274);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, result_is_file$17, PyString.fromInterned("True if 'self.result' is an instance of 'self.wsgi_file_wrapper'"));
      var1.setlocal("result_is_file", var9);
      var3 = null;
      var1.setline(280);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, client_is_modern$18, PyString.fromInterned("True if client can accept status and headers"));
      var1.setlocal("client_is_modern", var9);
      var3 = null;
      var1.setline(285);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, log_exception$19, PyString.fromInterned("Log the 'exc_info' tuple in the server log\n\n        Subclasses may override to retarget the output or change its format.\n        "));
      var1.setlocal("log_exception", var9);
      var3 = null;
      var1.setline(301);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, handle_error$20, PyString.fromInterned("Log current error, and send error output to client if possible"));
      var1.setlocal("handle_error", var9);
      var3 = null;
      var1.setline(309);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, error_output$21, PyString.fromInterned("WSGI mini-app to create error output\n\n        By default, this just uses the 'error_status', 'error_headers',\n        and 'error_body' attributes to generate an output page.  It can\n        be overridden in a subclass to dynamically generate diagnostics,\n        choose an appropriate message for the user's preferred language, etc.\n\n        Note, however, that it's not recommended from a security perspective to\n        spit out diagnostics to any old user; ideally, you should have to do\n        something special to enable diagnostic output, which is why we don't\n        include any here!\n        "));
      var1.setlocal("error_output", var9);
      var3 = null;
      var1.setline(328);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _write$22, PyString.fromInterned("Override in subclass to buffer data for send to client\n\n        It's okay if this method actually transmits the data; BaseHandler\n        just separates write and flush operations for greater efficiency\n        when the underlying system actually has such a distinction.\n        "));
      var1.setlocal("_write", var9);
      var3 = null;
      var1.setline(337);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _flush$23, PyString.fromInterned("Override in subclass to force sending of recent '_write()' calls\n\n        It's okay if this method is a no-op (i.e., if '_write()' actually\n        sends the data.\n        "));
      var1.setlocal("_flush", var9);
      var3 = null;
      var1.setline(345);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, get_stdin$24, PyString.fromInterned("Override in subclass to return suitable 'wsgi.input'"));
      var1.setlocal("get_stdin", var9);
      var3 = null;
      var1.setline(349);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, get_stderr$25, PyString.fromInterned("Override in subclass to return suitable 'wsgi.errors'"));
      var1.setlocal("get_stderr", var9);
      var3 = null;
      var1.setline(353);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, add_cgi_vars$26, PyString.fromInterned("Override in subclass to insert CGI variables in 'self.environ'"));
      var1.setlocal("add_cgi_vars", var9);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Invoke the application");

      try {
         var1.setline(84);
         var1.getlocal(0).__getattr__("setup_environ").__call__(var2);
         var1.setline(85);
         PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("environ"), var1.getlocal(0).__getattr__("start_response"));
         var1.getlocal(0).__setattr__("result", var3);
         var3 = null;
         var1.setline(86);
         var1.getlocal(0).__getattr__("finish_response").__call__(var2);
      } catch (Throwable var6) {
         Py.setException(var6, var1);

         try {
            var1.setline(89);
            var1.getlocal(0).__getattr__("handle_error").__call__(var2);
         } catch (Throwable var5) {
            Py.setException(var5, var1);
            var1.setline(92);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(93);
            throw Py.makeException();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setup_environ$5(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Set up the environment for one request");
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("os_environ").__getattr__("copy").__call__(var2);
      var1.setlocal(1, var3);
      var1.getlocal(0).__setattr__("environ", var3);
      var1.setline(100);
      var1.getlocal(0).__getattr__("add_cgi_vars").__call__(var2);
      var1.setline(102);
      var3 = var1.getlocal(0).__getattr__("get_stdin").__call__(var2);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.input"), var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getlocal(0).__getattr__("get_stderr").__call__(var2);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.errors"), var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getlocal(0).__getattr__("wsgi_version");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.version"), var3);
      var3 = null;
      var1.setline(105);
      var3 = var1.getlocal(0).__getattr__("wsgi_run_once");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.run_once"), var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(0).__getattr__("get_scheme").__call__(var2);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.url_scheme"), var3);
      var3 = null;
      var1.setline(107);
      var3 = var1.getlocal(0).__getattr__("wsgi_multithread");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.multithread"), var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getlocal(0).__getattr__("wsgi_multiprocess");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.multiprocess"), var3);
      var3 = null;
      var1.setline(110);
      var3 = var1.getlocal(0).__getattr__("wsgi_file_wrapper");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(111);
         var3 = var1.getlocal(0).__getattr__("wsgi_file_wrapper");
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("wsgi.file_wrapper"), var3);
         var3 = null;
      }

      var1.setline(113);
      var10000 = var1.getlocal(0).__getattr__("origin_server");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("server_software");
      }

      if (var10000.__nonzero__()) {
         var1.setline(114);
         var1.getlocal(1).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SERVER_SOFTWARE"), (PyObject)var1.getlocal(0).__getattr__("server_software"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish_response$6(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Send any iterable data, then close self and the iterable\n\n        Subclasses intended for use in asynchronous servers will\n        want to redefine this method, such that it sets up callbacks\n        in the event loop to iterate over the data, and to call\n        'self.close()' once the response is finished.\n        ");
      Object var3 = null;

      try {
         var1.setline(126);
         PyObject var10000 = var1.getlocal(0).__getattr__("result_is_file").__call__(var2).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("sendfile").__call__(var2).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(127);
            PyObject var4 = var1.getlocal(0).__getattr__("result").__iter__();

            while(true) {
               var1.setline(127);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(129);
                  var1.getlocal(0).__getattr__("finish_content").__call__(var2);
                  break;
               }

               var1.setlocal(1, var5);
               var1.setline(128);
               var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
            }
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(131);
         var1.getlocal(0).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(131);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_scheme$7(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyString.fromInterned("Return the URL scheme being used");
      var1.setline(136);
      PyObject var3 = var1.getglobal("guess_scheme").__call__(var2, var1.getlocal(0).__getattr__("environ"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_content_length$8(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyString.fromInterned("Compute Content-Length or switch to chunked encoding if possible");

      label21: {
         PyException var3;
         try {
            var1.setline(142);
            PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("result"));
            var1.setlocal(1, var6);
            var3 = null;
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError"), var1.getglobal("NotImplementedError")}))) {
               var1.setline(144);
               break label21;
            }

            throw var3;
         }

         var1.setline(146);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(147);
            var4 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("bytes_sent"));
            var1.getlocal(0).__getattr__("headers").__setitem__((PyObject)PyString.fromInterned("Content-Length"), var4);
            var4 = null;
            var1.setline(148);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup_headers$9(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyString.fromInterned("Make any necessary header changes or defaults\n\n        Subclasses can extend this to add other defaults.\n        ");
      var1.setline(157);
      PyString var3 = PyString.fromInterned("Content-Length");
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("headers"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(158);
         var1.getlocal(0).__getattr__("set_content_length").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_response$10(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("'start_response()' callable as specified by PEP 333");
      var1.setline(163);
      PyObject var10000;
      PyObject var3;
      PyObject var4;
      if (var1.getlocal(3).__nonzero__()) {
         var3 = null;

         try {
            var1.setline(165);
            if (var1.getlocal(0).__getattr__("headers_sent").__nonzero__()) {
               var1.setline(167);
               throw Py.makeException(var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getitem__(Py.newInteger(1)), var1.getlocal(3).__getitem__(Py.newInteger(2)));
            }
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(169);
            var4 = var1.getglobal("None");
            var1.setlocal(3, var4);
            var4 = null;
            throw (Throwable)var7;
         }

         var1.setline(169);
         var4 = var1.getglobal("None");
         var1.setlocal(3, var4);
         var4 = null;
      } else {
         var1.setline(170);
         var3 = var1.getlocal(0).__getattr__("headers");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(171);
            throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Headers already set!")));
         }
      }

      var1.setline(173);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._is(var1.getglobal("StringType"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Status must be a string"));
         }
      }

      var1.setline(174);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._ge(Py.newInteger(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Status must be at least 4 characters"));
         }
      }

      var1.setline(175);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("int").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null)).__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Status message must begin w/3-digit code"));
      } else {
         var1.setline(176);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(3));
            var10000 = var3._eq(PyString.fromInterned(" "));
            var3 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Status message must have a space after code"));
            }
         }

         var1.setline(177);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var1.setline(178);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(178);
               var4 = var3.__iternext__();
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
               var1.setline(179);
               PyObject var8;
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var8 = var1.getglobal("type").__call__(var2, var1.getlocal(4));
                  var10000 = var8._is(var1.getglobal("StringType"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Header names must be strings"));
                  }
               }

               var1.setline(180);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var8 = var1.getglobal("type").__call__(var2, var1.getlocal(5));
                  var10000 = var8._is(var1.getglobal("StringType"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Header values must be strings"));
                  }
               }

               var1.setline(181);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("is_hop_by_hop").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Hop-by-hop headers not allowed"));
               }
            }
         }

         var1.setline(182);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("status", var3);
         var3 = null;
         var1.setline(183);
         var3 = var1.getlocal(0).__getattr__("headers_class").__call__(var2, var1.getlocal(2));
         var1.getlocal(0).__setattr__("headers", var3);
         var3 = null;
         var1.setline(184);
         var3 = var1.getlocal(0).__getattr__("write");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject send_preamble$11(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString.fromInterned("Transmit version/status/date/server, via self._write()");
      var1.setline(189);
      if (var1.getlocal(0).__getattr__("origin_server").__nonzero__()) {
         var1.setline(190);
         if (var1.getlocal(0).__getattr__("client_is_modern").__call__(var2).__nonzero__()) {
            var1.setline(191);
            var1.getlocal(0).__getattr__("_write").__call__(var2, PyString.fromInterned("HTTP/%s %s\r\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("http_version"), var1.getlocal(0).__getattr__("status")})));
            var1.setline(192);
            PyString var3 = PyString.fromInterned("Date");
            PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("headers"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(193);
               var1.getlocal(0).__getattr__("_write").__call__(var2, PyString.fromInterned("Date: %s\r\n")._mod(var1.getglobal("format_date_time").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2))));
            }

            var1.setline(196);
            var10000 = var1.getlocal(0).__getattr__("server_software");
            if (var10000.__nonzero__()) {
               var3 = PyString.fromInterned("Server");
               var10000 = var3._notin(var1.getlocal(0).__getattr__("headers"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(197);
               var1.getlocal(0).__getattr__("_write").__call__(var2, PyString.fromInterned("Server: %s\r\n")._mod(var1.getlocal(0).__getattr__("server_software")));
            }
         }
      } else {
         var1.setline(199);
         var1.getlocal(0).__getattr__("_write").__call__(var2, PyString.fromInterned("Status: %s\r\n")._mod(var1.getlocal(0).__getattr__("status")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$12(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyString.fromInterned("'write()' callable as specified by PEP 333");
      var1.setline(204);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._is(var1.getglobal("StringType"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("write() argument must be string"));
         }
      }

      var1.setline(206);
      if (var1.getlocal(0).__getattr__("status").__not__().__nonzero__()) {
         var1.setline(207);
         throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("write() before start_response()")));
      } else {
         var1.setline(209);
         if (var1.getlocal(0).__getattr__("headers_sent").__not__().__nonzero__()) {
            var1.setline(211);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__setattr__("bytes_sent", var3);
            var3 = null;
            var1.setline(212);
            var1.getlocal(0).__getattr__("send_headers").__call__(var2);
         } else {
            var1.setline(214);
            var10000 = var1.getlocal(0);
            String var6 = "bytes_sent";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var6);
            var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var4.__setattr__(var6, var5);
         }

         var1.setline(217);
         var1.getlocal(0).__getattr__("_write").__call__(var2, var1.getlocal(1));
         var1.setline(218);
         var1.getlocal(0).__getattr__("_flush").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject sendfile$13(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyString.fromInterned("Platform-specific file transmission\n\n        Override this method in subclasses to support platform-specific\n        file transmission.  It is only called if the application's\n        return iterable ('self.result') is an instance of\n        'self.wsgi_file_wrapper'.\n\n        This method should return a true value if it was able to actually\n        transmit the wrapped file-like object using a platform-specific\n        approach.  It should return a false value if normal iteration\n        should be used instead.  An exception can be raised to indicate\n        that transmission was attempted, but failed.\n\n        NOTE: this method should call 'self.send_headers()' if\n        'self.headers_sent' is false and it is going to attempt direct\n        transmission of the file.\n        ");
      var1.setline(239);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject finish_content$14(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyString.fromInterned("Ensure headers and content have both been sent");
      var1.setline(244);
      if (var1.getlocal(0).__getattr__("headers_sent").__not__().__nonzero__()) {
         var1.setline(247);
         var1.getlocal(0).__getattr__("headers").__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)PyString.fromInterned("0"));
         var1.setline(248);
         var1.getlocal(0).__getattr__("send_headers").__call__(var2);
      } else {
         var1.setline(250);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$15(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyString.fromInterned("Close the iterable (if needed) and reset all instance vars\n\n        Subclasses may want to also drop the client connection.\n        ");
      Object var3 = null;

      PyObject var4;
      PyInteger var6;
      try {
         var1.setline(258);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("result"), (PyObject)PyString.fromInterned("close")).__nonzero__()) {
            var1.setline(259);
            var1.getlocal(0).__getattr__("result").__getattr__("close").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(261);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("result", var4);
         var1.getlocal(0).__setattr__("headers", var4);
         var1.getlocal(0).__setattr__("status", var4);
         var1.getlocal(0).__setattr__("environ", var4);
         var1.setline(262);
         var6 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"bytes_sent", var6);
         var4 = null;
         var1.setline(262);
         var4 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("headers_sent", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(261);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("result", var4);
      var1.getlocal(0).__setattr__("headers", var4);
      var1.getlocal(0).__setattr__("status", var4);
      var1.getlocal(0).__setattr__("environ", var4);
      var1.setline(262);
      var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"bytes_sent", var6);
      var4 = null;
      var1.setline(262);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("headers_sent", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_headers$16(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyString.fromInterned("Transmit headers to the client, via self._write()");
      var1.setline(267);
      var1.getlocal(0).__getattr__("cleanup_headers").__call__(var2);
      var1.setline(268);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("headers_sent", var3);
      var3 = null;
      var1.setline(269);
      PyObject var10000 = var1.getlocal(0).__getattr__("origin_server").__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("client_is_modern").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(270);
         var1.getlocal(0).__getattr__("send_preamble").__call__(var2);
         var1.setline(271);
         var1.getlocal(0).__getattr__("_write").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("headers")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject result_is_file$17(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyString.fromInterned("True if 'self.result' is an instance of 'self.wsgi_file_wrapper'");
      var1.setline(276);
      PyObject var3 = var1.getlocal(0).__getattr__("wsgi_file_wrapper");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(277);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("result"), var1.getlocal(1));
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject client_is_modern$18(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("True if client can accept status and headers");
      var1.setline(282);
      PyObject var3 = var1.getlocal(0).__getattr__("environ").__getitem__(PyString.fromInterned("SERVER_PROTOCOL")).__getattr__("upper").__call__(var2);
      PyObject var10000 = var3._ne(PyString.fromInterned("HTTP/0.9"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject log_exception$19(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyString.fromInterned("Log the 'exc_info' tuple in the server log\n\n        Subclasses may override to retarget the output or change its format.\n        ");
      Object var3 = null;

      PyObject var4;
      try {
         var1.setline(291);
         String[] var7 = new String[]{"print_exception"};
         PyObject[] var8 = imp.importFrom("traceback", var7, var1, -1);
         PyObject var5 = var8[0];
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(292);
         var4 = var1.getlocal(0).__getattr__("get_stderr").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(293);
         PyObject var10000 = var1.getlocal(2);
         var8 = new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(2)), var1.getlocal(0).__getattr__("traceback_limit"), var1.getlocal(3)};
         var10000.__call__(var2, var8);
         var1.setline(297);
         var1.getlocal(3).__getattr__("flush").__call__(var2);
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(299);
         var4 = var1.getglobal("None");
         var1.setlocal(1, var4);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(299);
      var4 = var1.getglobal("None");
      var1.setlocal(1, var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_error$20(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyString.fromInterned("Log current error, and send error output to client if possible");
      var1.setline(303);
      var1.getlocal(0).__getattr__("log_exception").__call__(var2, var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
      var1.setline(304);
      if (var1.getlocal(0).__getattr__("headers_sent").__not__().__nonzero__()) {
         var1.setline(305);
         PyObject var3 = var1.getlocal(0).__getattr__("error_output").__call__(var2, var1.getlocal(0).__getattr__("environ"), var1.getlocal(0).__getattr__("start_response"));
         var1.getlocal(0).__setattr__("result", var3);
         var3 = null;
         var1.setline(306);
         var1.getlocal(0).__getattr__("finish_response").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error_output$21(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyString.fromInterned("WSGI mini-app to create error output\n\n        By default, this just uses the 'error_status', 'error_headers',\n        and 'error_body' attributes to generate an output page.  It can\n        be overridden in a subclass to dynamically generate diagnostics,\n        choose an appropriate message for the user's preferred language, etc.\n\n        Note, however, that it's not recommended from a security perspective to\n        spit out diagnostics to any old user; ideally, you should have to do\n        something special to enable diagnostic output, which is why we don't\n        include any here!\n        ");
      var1.setline(322);
      var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("error_status"), var1.getlocal(0).__getattr__("error_headers").__getslice__((PyObject)null, (PyObject)null, (PyObject)null), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
      var1.setline(323);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("error_body")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _write$22(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyString.fromInterned("Override in subclass to buffer data for send to client\n\n        It's okay if this method actually transmits the data; BaseHandler\n        just separates write and flush operations for greater efficiency\n        when the underlying system actually has such a distinction.\n        ");
      var1.setline(335);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject _flush$23(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyString.fromInterned("Override in subclass to force sending of recent '_write()' calls\n\n        It's okay if this method is a no-op (i.e., if '_write()' actually\n        sends the data.\n        ");
      var1.setline(343);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject get_stdin$24(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyString.fromInterned("Override in subclass to return suitable 'wsgi.input'");
      var1.setline(347);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject get_stderr$25(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyString.fromInterned("Override in subclass to return suitable 'wsgi.errors'");
      var1.setline(351);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject add_cgi_vars$26(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyString.fromInterned("Override in subclass to insert CGI variables in 'self.environ'");
      var1.setline(355);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject SimpleHandler$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handler that's just initialized with streams, environment, etc.\n\n    This handler subclass is intended for synchronous HTTP/1.0 origin servers,\n    and handles sending the entire response output, given the correct inputs.\n\n    Usage::\n\n        handler = SimpleHandler(\n            inp,out,err,env, multithread=False, multiprocess=True\n        )\n        handler.run(app)"));
      var1.setline(369);
      PyString.fromInterned("Handler that's just initialized with streams, environment, etc.\n\n    This handler subclass is intended for synchronous HTTP/1.0 origin servers,\n    and handles sending the entire response output, given the correct inputs.\n\n    Usage::\n\n        handler = SimpleHandler(\n            inp,out,err,env, multithread=False, multiprocess=True\n        )\n        handler.run(app)");
      var1.setline(371);
      PyObject[] var3 = new PyObject[]{var1.getname("True"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$28, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(381);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_stdin$29, (PyObject)null);
      var1.setlocal("get_stdin", var4);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_stderr$30, (PyObject)null);
      var1.setlocal("get_stderr", var4);
      var3 = null;
      var1.setline(387);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_cgi_vars$31, (PyObject)null);
      var1.setlocal("add_cgi_vars", var4);
      var3 = null;
      var1.setline(390);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _write$32, (PyObject)null);
      var1.setlocal("_write", var4);
      var3 = null;
      var1.setline(394);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _flush$33, (PyObject)null);
      var1.setlocal("_flush", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$28(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stdin", var3);
      var3 = null;
      var1.setline(375);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("stdout", var3);
      var3 = null;
      var1.setline(376);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("stderr", var3);
      var3 = null;
      var1.setline(377);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("base_env", var3);
      var3 = null;
      var1.setline(378);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("wsgi_multithread", var3);
      var3 = null;
      var1.setline(379);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("wsgi_multiprocess", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_stdin$29(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyObject var3 = var1.getlocal(0).__getattr__("stdin");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_stderr$30(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyObject var3 = var1.getlocal(0).__getattr__("stderr");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_cgi_vars$31(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      var1.getlocal(0).__getattr__("environ").__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("base_env"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write$32(PyFrame var1, ThreadState var2) {
      var1.setline(391);
      var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(392);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout").__getattr__("write");
      var1.getlocal(0).__setattr__("_write", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _flush$33(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      var1.getlocal(0).__getattr__("stdout").__getattr__("flush").__call__(var2);
      var1.setline(396);
      PyObject var3 = var1.getlocal(0).__getattr__("stdout").__getattr__("flush");
      var1.getlocal(0).__setattr__("_flush", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BaseCGIHandler$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("CGI-like systems using input/output/error streams and environ mapping\n\n    Usage::\n\n        handler = BaseCGIHandler(inp,out,err,env)\n        handler.run(app)\n\n    This handler class is useful for gateway protocols like ReadyExec and\n    FastCGI, that have usable input/output/error streams and an environment\n    mapping.  It's also the base class for CGIHandler, which just uses\n    sys.stdin, os.environ, and so on.\n\n    The constructor also takes keyword arguments 'multithread' and\n    'multiprocess' (defaulting to 'True' and 'False' respectively) to control\n    the configuration sent to the application.  It sets 'origin_server' to\n    False (to enable CGI-like output), and assumes that 'wsgi.run_once' is\n    False.\n    "));
      var1.setline(418);
      PyString.fromInterned("CGI-like systems using input/output/error streams and environ mapping\n\n    Usage::\n\n        handler = BaseCGIHandler(inp,out,err,env)\n        handler.run(app)\n\n    This handler class is useful for gateway protocols like ReadyExec and\n    FastCGI, that have usable input/output/error streams and an environment\n    mapping.  It's also the base class for CGIHandler, which just uses\n    sys.stdin, os.environ, and so on.\n\n    The constructor also takes keyword arguments 'multithread' and\n    'multiprocess' (defaulting to 'True' and 'False' respectively) to control\n    the configuration sent to the application.  It sets 'origin_server' to\n    False (to enable CGI-like output), and assumes that 'wsgi.run_once' is\n    False.\n    ");
      var1.setline(420);
      PyObject var3 = var1.getname("False");
      var1.setlocal("origin_server", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject CGIHandler$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("CGI-based invocation via sys.stdin/stdout/stderr and os.environ\n\n    Usage::\n\n        CGIHandler().run(app)\n\n    The difference between this class and BaseCGIHandler is that it always\n    uses 'wsgi.run_once' of 'True', 'wsgi.multithread' of 'False', and\n    'wsgi.multiprocess' of 'True'.  It does not take any initialization\n    parameters, but always uses 'sys.stdin', 'os.environ', and friends.\n\n    If you need to override any of these parameters, use BaseCGIHandler\n    instead.\n    "));
      var1.setline(438);
      PyString.fromInterned("CGI-based invocation via sys.stdin/stdout/stderr and os.environ\n\n    Usage::\n\n        CGIHandler().run(app)\n\n    The difference between this class and BaseCGIHandler is that it always\n    uses 'wsgi.run_once' of 'True', 'wsgi.multithread' of 'False', and\n    'wsgi.multiprocess' of 'True'.  It does not take any initialization\n    parameters, but always uses 'sys.stdin', 'os.environ', and friends.\n\n    If you need to override any of these parameters, use BaseCGIHandler\n    instead.\n    ");
      var1.setline(440);
      PyObject var3 = var1.getname("True");
      var1.setlocal("wsgi_run_once", var3);
      var3 = null;
      var1.setline(444);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("os_environ", var4);
      var3 = null;
      var1.setline(446);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$36, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$36(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyObject var10000 = var1.getglobal("BaseCGIHandler").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("sys").__getattr__("stdin"), var1.getglobal("sys").__getattr__("stdout"), var1.getglobal("sys").__getattr__("stderr"), var1.getglobal("dict").__call__(var2, var1.getglobal("os").__getattr__("environ").__getattr__("items").__call__(var2)), var1.getglobal("False"), var1.getglobal("True")};
      String[] var4 = new String[]{"multithread", "multiprocess"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public handlers$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"items", "d", "k", "v"};
      dict$1 = Py.newCode(1, var2, var1, "dict", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timestamp", "year", "month", "day", "hh", "mm", "ss", "wd", "y", "z"};
      format_date_time$2 = Py.newCode(1, var2, var1, "format_date_time", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseHandler$3 = Py.newCode(0, var2, var1, "BaseHandler", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "application"};
      run$4 = Py.newCode(2, var2, var1, "run", 76, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "env"};
      setup_environ$5 = Py.newCode(1, var2, var1, "setup_environ", 96, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      finish_response$6 = Py.newCode(1, var2, var1, "finish_response", 117, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_scheme$7 = Py.newCode(1, var2, var1, "get_scheme", 134, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blocks"};
      set_content_length$8 = Py.newCode(1, var2, var1, "set_content_length", 139, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      cleanup_headers$9 = Py.newCode(1, var2, var1, "cleanup_headers", 152, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "status", "headers", "exc_info", "name", "val"};
      start_response$10 = Py.newCode(4, var2, var1, "start_response", 160, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      send_preamble$11 = Py.newCode(1, var2, var1, "send_preamble", 187, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write$12 = Py.newCode(2, var2, var1, "write", 201, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      sendfile$13 = Py.newCode(1, var2, var1, "sendfile", 221, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finish_content$14 = Py.newCode(1, var2, var1, "finish_content", 242, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$15 = Py.newCode(1, var2, var1, "close", 252, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      send_headers$16 = Py.newCode(1, var2, var1, "send_headers", 265, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "wrapper"};
      result_is_file$17 = Py.newCode(1, var2, var1, "result_is_file", 274, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      client_is_modern$18 = Py.newCode(1, var2, var1, "client_is_modern", 280, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc_info", "print_exception", "stderr"};
      log_exception$19 = Py.newCode(2, var2, var1, "log_exception", 285, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_error$20 = Py.newCode(1, var2, var1, "handle_error", 301, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "environ", "start_response"};
      error_output$21 = Py.newCode(3, var2, var1, "error_output", 309, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      _write$22 = Py.newCode(2, var2, var1, "_write", 328, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _flush$23 = Py.newCode(1, var2, var1, "_flush", 337, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stdin$24 = Py.newCode(1, var2, var1, "get_stdin", 345, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stderr$25 = Py.newCode(1, var2, var1, "get_stderr", 349, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      add_cgi_vars$26 = Py.newCode(1, var2, var1, "add_cgi_vars", 353, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SimpleHandler$27 = Py.newCode(0, var2, var1, "SimpleHandler", 358, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stdin", "stdout", "stderr", "environ", "multithread", "multiprocess"};
      __init__$28 = Py.newCode(7, var2, var1, "__init__", 371, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stdin$29 = Py.newCode(1, var2, var1, "get_stdin", 381, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_stderr$30 = Py.newCode(1, var2, var1, "get_stderr", 384, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      add_cgi_vars$31 = Py.newCode(1, var2, var1, "add_cgi_vars", 387, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      _write$32 = Py.newCode(2, var2, var1, "_write", 390, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _flush$33 = Py.newCode(1, var2, var1, "_flush", 394, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseCGIHandler$34 = Py.newCode(0, var2, var1, "BaseCGIHandler", 399, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CGIHandler$35 = Py.newCode(0, var2, var1, "CGIHandler", 423, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$36 = Py.newCode(1, var2, var1, "__init__", 446, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new handlers$py("wsgiref/handlers$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(handlers$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.dict$1(var2, var3);
         case 2:
            return this.format_date_time$2(var2, var3);
         case 3:
            return this.BaseHandler$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.setup_environ$5(var2, var3);
         case 6:
            return this.finish_response$6(var2, var3);
         case 7:
            return this.get_scheme$7(var2, var3);
         case 8:
            return this.set_content_length$8(var2, var3);
         case 9:
            return this.cleanup_headers$9(var2, var3);
         case 10:
            return this.start_response$10(var2, var3);
         case 11:
            return this.send_preamble$11(var2, var3);
         case 12:
            return this.write$12(var2, var3);
         case 13:
            return this.sendfile$13(var2, var3);
         case 14:
            return this.finish_content$14(var2, var3);
         case 15:
            return this.close$15(var2, var3);
         case 16:
            return this.send_headers$16(var2, var3);
         case 17:
            return this.result_is_file$17(var2, var3);
         case 18:
            return this.client_is_modern$18(var2, var3);
         case 19:
            return this.log_exception$19(var2, var3);
         case 20:
            return this.handle_error$20(var2, var3);
         case 21:
            return this.error_output$21(var2, var3);
         case 22:
            return this._write$22(var2, var3);
         case 23:
            return this._flush$23(var2, var3);
         case 24:
            return this.get_stdin$24(var2, var3);
         case 25:
            return this.get_stderr$25(var2, var3);
         case 26:
            return this.add_cgi_vars$26(var2, var3);
         case 27:
            return this.SimpleHandler$27(var2, var3);
         case 28:
            return this.__init__$28(var2, var3);
         case 29:
            return this.get_stdin$29(var2, var3);
         case 30:
            return this.get_stderr$30(var2, var3);
         case 31:
            return this.add_cgi_vars$31(var2, var3);
         case 32:
            return this._write$32(var2, var3);
         case 33:
            return this._flush$33(var2, var3);
         case 34:
            return this.BaseCGIHandler$34(var2, var3);
         case 35:
            return this.CGIHandler$35(var2, var3);
         case 36:
            return this.__init__$36(var2, var3);
         default:
            return null;
      }
   }
}
