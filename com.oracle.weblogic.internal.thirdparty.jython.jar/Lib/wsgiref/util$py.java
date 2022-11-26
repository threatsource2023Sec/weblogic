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
@Filename("wsgiref/util.py")
public class util$py extends PyFunctionTable implements PyRunnable {
   static util$py self;
   static final PyCode f$0;
   static final PyCode FileWrapper$1;
   static final PyCode __init__$2;
   static final PyCode __getitem__$3;
   static final PyCode __iter__$4;
   static final PyCode next$5;
   static final PyCode guess_scheme$6;
   static final PyCode application_uri$7;
   static final PyCode request_uri$8;
   static final PyCode shift_path_info$9;
   static final PyCode setup_testing_defaults$10;
   static final PyCode is_hop_by_hop$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Miscellaneous WSGI-related Utilities"));
      var1.setline(1);
      PyString.fromInterned("Miscellaneous WSGI-related Utilities");
      var1.setline(3);
      PyObject var3 = imp.importOne("posixpath", var1, -1);
      var1.setlocal("posixpath", var3);
      var3 = null;
      var1.setline(5);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("FileWrapper"), PyString.fromInterned("guess_scheme"), PyString.fromInterned("application_uri"), PyString.fromInterned("request_uri"), PyString.fromInterned("shift_path_info"), PyString.fromInterned("setup_testing_defaults")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(11);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("FileWrapper", var6, FileWrapper$1);
      var1.setlocal("FileWrapper", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(35);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, guess_scheme$6, PyString.fromInterned("Return a guess for whether 'wsgi.url_scheme' should be 'http' or 'https'\n    "));
      var1.setlocal("guess_scheme", var7);
      var3 = null;
      var1.setline(43);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, application_uri$7, PyString.fromInterned("Return the application's base URI (no PATH_INFO or QUERY_STRING)"));
      var1.setlocal("application_uri", var7);
      var3 = null;
      var1.setline(63);
      var6 = new PyObject[]{Py.newInteger(1)};
      var7 = new PyFunction(var1.f_globals, var6, request_uri$8, PyString.fromInterned("Return the full request URI, optionally including the query string"));
      var1.setlocal("request_uri", var7);
      var3 = null;
      var1.setline(76);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, shift_path_info$9, PyString.fromInterned("Shift a name from PATH_INFO to SCRIPT_NAME, returning it\n\n    If there are no remaining path segments in PATH_INFO, return None.\n    Note: 'environ' is modified in-place; use a copy if you need to keep\n    the original PATH_INFO or SCRIPT_NAME.\n\n    Note: when PATH_INFO is just a '/', this returns '' and appends a trailing\n    '/' to SCRIPT_NAME, even though empty path segments are normally ignored,\n    and SCRIPT_NAME doesn't normally end in a '/'.  This is intentional\n    behavior, to ensure that an application can tell the difference between\n    '/x' and '/x/' when traversing to objects.\n    "));
      var1.setlocal("shift_path_info", var7);
      var3 = null;
      var1.setline(117);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, setup_testing_defaults$10, PyString.fromInterned("Update 'environ' with trivial defaults for testing purposes\n\n    This adds various parameters required for WSGI, including HTTP_HOST,\n    SERVER_NAME, SERVER_PORT, REQUEST_METHOD, SCRIPT_NAME, PATH_INFO,\n    and all of the wsgi.* variables.  It only supplies default values,\n    and does not replace any existing settings for these variables.\n\n    This routine is intended to make it easier for unit tests of WSGI\n    servers and applications to set up dummy environments.  It should *not*\n    be used by actual WSGI servers or applications, since the data is fake!\n    "));
      var1.setlocal("setup_testing_defaults", var7);
      var3 = null;
      var1.setline(157);
      var3 = (new PyDictionary(new PyObject[]{PyString.fromInterned("connection"), Py.newInteger(1), PyString.fromInterned("keep-alive"), Py.newInteger(1), PyString.fromInterned("proxy-authenticate"), Py.newInteger(1), PyString.fromInterned("proxy-authorization"), Py.newInteger(1), PyString.fromInterned("te"), Py.newInteger(1), PyString.fromInterned("trailers"), Py.newInteger(1), PyString.fromInterned("transfer-encoding"), Py.newInteger(1), PyString.fromInterned("upgrade"), Py.newInteger(1)})).__getattr__("__contains__");
      var1.setlocal("_hoppish", var3);
      var3 = null;
      var1.setline(163);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, is_hop_by_hop$11, PyString.fromInterned("Return true if 'header_name' is an HTTP/1.1 \"Hop-by-Hop\" header"));
      var1.setlocal("is_hop_by_hop", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FileWrapper$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Wrapper to convert file-like objects to iterables"));
      var1.setline(12);
      PyString.fromInterned("Wrapper to convert file-like objects to iterables");
      var1.setline(14);
      PyObject[] var3 = new PyObject[]{Py.newInteger(8192)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(20);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$3, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$4, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$5, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filelike", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("blksize", var3);
      var3 = null;
      var1.setline(17);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("close")).__nonzero__()) {
         var1.setline(18);
         var3 = var1.getlocal(1).__getattr__("close");
         var1.getlocal(0).__setattr__("close", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$3(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getlocal(0).__getattr__("filelike").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("blksize"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(22);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(23);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(24);
         throw Py.makeException(var1.getglobal("IndexError"));
      }
   }

   public PyObject __iter__$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$5(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(0).__getattr__("filelike").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("blksize"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(31);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(32);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(33);
         throw Py.makeException(var1.getglobal("StopIteration"));
      }
   }

   public PyObject guess_scheme$6(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyString.fromInterned("Return a guess for whether 'wsgi.url_scheme' should be 'http' or 'https'\n    ");
      var1.setline(38);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTPS"));
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("yes"), PyString.fromInterned("on"), PyString.fromInterned("1")}));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(39);
         var4 = PyString.fromInterned("https");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(41);
         var4 = PyString.fromInterned("http");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject application_uri$7(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyString.fromInterned("Return the application's base URI (no PATH_INFO or QUERY_STRING)");
      var1.setline(45);
      PyObject var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.url_scheme"))._add(PyString.fromInterned("://"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(46);
      String[] var5 = new String[]{"quote"};
      PyObject[] var6 = imp.importFrom("urllib", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(48);
      PyObject var10000;
      if (var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTP_HOST")).__nonzero__()) {
         var1.setline(49);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(var1.getlocal(0).__getitem__(PyString.fromInterned("HTTP_HOST")));
         var1.setlocal(1, var3);
      } else {
         var1.setline(51);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(var1.getlocal(0).__getitem__(PyString.fromInterned("SERVER_NAME")));
         var1.setlocal(1, var3);
         var1.setline(53);
         var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.url_scheme"));
         var10000 = var3._eq(PyString.fromInterned("https"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(54);
            var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("SERVER_PORT"));
            var10000 = var3._ne(PyString.fromInterned("443"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(55);
               var3 = var1.getlocal(1);
               var3 = var3._iadd(PyString.fromInterned(":")._add(var1.getlocal(0).__getitem__(PyString.fromInterned("SERVER_PORT"))));
               var1.setlocal(1, var3);
            }
         } else {
            var1.setline(57);
            var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("SERVER_PORT"));
            var10000 = var3._ne(PyString.fromInterned("80"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(58);
               var3 = var1.getlocal(1);
               var3 = var3._iadd(PyString.fromInterned(":")._add(var1.getlocal(0).__getitem__(PyString.fromInterned("SERVER_PORT"))));
               var1.setlocal(1, var3);
            }
         }
      }

      var1.setline(60);
      var3 = var1.getlocal(1);
      var10000 = var1.getlocal(2);
      Object var10002 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME"));
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("/");
      }

      var3 = var3._iadd(var10000.__call__((ThreadState)var2, (PyObject)var10002));
      var1.setlocal(1, var3);
      var1.setline(61);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject request_uri$8(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString.fromInterned("Return the full request URI, optionally including the query string");
      var1.setline(65);
      PyObject var3 = var1.getglobal("application_uri").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(66);
      String[] var5 = new String[]{"quote"};
      PyObject[] var6 = imp.importFrom("urllib", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(67);
      PyObject var10000 = var1.getlocal(3);
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PATH_INFO"), (PyObject)PyString.fromInterned("")), PyString.fromInterned("/;=,")};
      String[] var7 = new String[]{"safe"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(68);
      if (var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME")).__not__().__nonzero__()) {
         var1.setline(69);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.setlocal(2, var3);
      } else {
         var1.setline(71);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(4));
         var1.setlocal(2, var3);
      }

      var1.setline(72);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("QUERY_STRING"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(73);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(PyString.fromInterned("?")._add(var1.getlocal(0).__getitem__(PyString.fromInterned("QUERY_STRING"))));
         var1.setlocal(2, var3);
      }

      var1.setline(74);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shift_path_info$9(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned("Shift a name from PATH_INFO to SCRIPT_NAME, returning it\n\n    If there are no remaining path segments in PATH_INFO, return None.\n    Note: 'environ' is modified in-place; use a copy if you need to keep\n    the original PATH_INFO or SCRIPT_NAME.\n\n    Note: when PATH_INFO is just a '/', this returns '' and appends a trailing\n    '/' to SCRIPT_NAME, even though empty path segments are normally ignored,\n    and SCRIPT_NAME doesn't normally end in a '/'.  This is intentional\n    behavior, to ensure that an application can tell the difference between\n    '/x' and '/x/' when traversing to objects.\n    ");
      var1.setline(89);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PATH_INFO"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(90);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(91);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(93);
         PyObject var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(94);
         PyList var10000 = new PyList();
         var4 = var10000.__getattr__("append");
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(94);
         var4 = var1.getlocal(2).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__iter__();

         while(true) {
            var1.setline(94);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(94);
               var1.dellocal(3);
               PyList var7 = var10000;
               var1.getlocal(2).__setslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null, var7);
               var4 = null;
               var1.setline(95);
               var4 = var1.getlocal(2).__getitem__(Py.newInteger(1));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(96);
               var1.getlocal(2).__delitem__((PyObject)Py.newInteger(1));
               var1.setline(98);
               var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME"), (PyObject)PyString.fromInterned(""));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(99);
               var4 = var1.getglobal("posixpath").__getattr__("normpath").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned("/"))._add(var1.getlocal(5)));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(100);
               if (var1.getlocal(6).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__nonzero__()) {
                  var1.setline(101);
                  var4 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(102);
               PyObject var8 = var1.getlocal(5).__not__();
               if (var8.__nonzero__()) {
                  var8 = var1.getlocal(6).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__();
               }

               if (var8.__nonzero__()) {
                  var1.setline(103);
                  var4 = var1.getlocal(6);
                  var4 = var4._iadd(PyString.fromInterned("/"));
                  var1.setlocal(6, var4);
               }

               var1.setline(105);
               var4 = var1.getlocal(6);
               var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("SCRIPT_NAME"), var4);
               var4 = null;
               var1.setline(106);
               var4 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(2));
               var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("PATH_INFO"), var4);
               var4 = null;
               var1.setline(113);
               var4 = var1.getlocal(5);
               var8 = var4._eq(PyString.fromInterned("."));
               var4 = null;
               if (var8.__nonzero__()) {
                  var1.setline(114);
                  var4 = var1.getglobal("None");
                  var1.setlocal(5, var4);
                  var4 = null;
               }

               var1.setline(115);
               var3 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var5);
            var1.setline(94);
            PyObject var10001 = var1.getlocal(4);
            if (var10001.__nonzero__()) {
               PyObject var6 = var1.getlocal(4);
               var10001 = var6._ne(PyString.fromInterned("."));
               var6 = null;
            }

            if (var10001.__nonzero__()) {
               var1.setline(94);
               var1.getlocal(3).__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public PyObject setup_testing_defaults$10(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyString.fromInterned("Update 'environ' with trivial defaults for testing purposes\n\n    This adds various parameters required for WSGI, including HTTP_HOST,\n    SERVER_NAME, SERVER_PORT, REQUEST_METHOD, SCRIPT_NAME, PATH_INFO,\n    and all of the wsgi.* variables.  It only supplies default values,\n    and does not replace any existing settings for these variables.\n\n    This routine is intended to make it easier for unit tests of WSGI\n    servers and applications to set up dummy environments.  It should *not*\n    be used by actual WSGI servers or applications, since the data is fake!\n    ");
      var1.setline(130);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SERVER_NAME"), (PyObject)PyString.fromInterned("127.0.0.1"));
      var1.setline(131);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SERVER_PROTOCOL"), (PyObject)PyString.fromInterned("HTTP/1.0"));
      var1.setline(133);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HTTP_HOST"), (PyObject)var1.getlocal(0).__getitem__(PyString.fromInterned("SERVER_NAME")));
      var1.setline(134);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("REQUEST_METHOD"), (PyObject)PyString.fromInterned("GET"));
      var1.setline(136);
      PyString var3 = PyString.fromInterned("SCRIPT_NAME");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = PyString.fromInterned("PATH_INFO");
         var10000 = var3._notin(var1.getlocal(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(137);
         var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME"), (PyObject)PyString.fromInterned(""));
         var1.setline(138);
         var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PATH_INFO"), (PyObject)PyString.fromInterned("/"));
      }

      var1.setline(140);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.version"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0)})));
      var1.setline(141);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.run_once"), (PyObject)Py.newInteger(0));
      var1.setline(142);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.multithread"), (PyObject)Py.newInteger(0));
      var1.setline(143);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.multiprocess"), (PyObject)Py.newInteger(0));
      var1.setline(145);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("StringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(146);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.input"), (PyObject)var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var1.setline(147);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.errors"), (PyObject)var1.getlocal(1).__call__(var2));
      var1.setline(148);
      var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("wsgi.url_scheme"), (PyObject)var1.getglobal("guess_scheme").__call__(var2, var1.getlocal(0)));
      var1.setline(150);
      PyObject var7 = var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.url_scheme"));
      var10000 = var7._eq(PyString.fromInterned("http"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(151);
         var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SERVER_PORT"), (PyObject)PyString.fromInterned("80"));
      } else {
         var1.setline(152);
         var7 = var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.url_scheme"));
         var10000 = var7._eq(PyString.fromInterned("https"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(153);
            var1.getlocal(0).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SERVER_PORT"), (PyObject)PyString.fromInterned("443"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_hop_by_hop$11(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyString.fromInterned("Return true if 'header_name' is an HTTP/1.1 \"Hop-by-Hop\" header");
      var1.setline(165);
      PyObject var3 = var1.getglobal("_hoppish").__call__(var2, var1.getlocal(0).__getattr__("lower").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FileWrapper$1 = Py.newCode(0, var2, var1, "FileWrapper", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filelike", "blksize"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "data"};
      __getitem__$3 = Py.newCode(2, var2, var1, "__getitem__", 20, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$4 = Py.newCode(1, var2, var1, "__iter__", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      next$5 = Py.newCode(1, var2, var1, "next", 29, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ"};
      guess_scheme$6 = Py.newCode(1, var2, var1, "guess_scheme", 35, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "url", "quote"};
      application_uri$7 = Py.newCode(1, var2, var1, "application_uri", 43, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "include_query", "url", "quote", "path_info"};
      request_uri$8 = Py.newCode(2, var2, var1, "request_uri", 63, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "path_info", "path_parts", "_[94_24]", "p", "name", "script_name"};
      shift_path_info$9 = Py.newCode(1, var2, var1, "shift_path_info", 76, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "StringIO"};
      setup_testing_defaults$10 = Py.newCode(1, var2, var1, "setup_testing_defaults", 117, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"header_name"};
      is_hop_by_hop$11 = Py.newCode(1, var2, var1, "is_hop_by_hop", 163, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new util$py("wsgiref/util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FileWrapper$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__getitem__$3(var2, var3);
         case 4:
            return this.__iter__$4(var2, var3);
         case 5:
            return this.next$5(var2, var3);
         case 6:
            return this.guess_scheme$6(var2, var3);
         case 7:
            return this.application_uri$7(var2, var3);
         case 8:
            return this.request_uri$8(var2, var3);
         case 9:
            return this.shift_path_info$9(var2, var3);
         case 10:
            return this.setup_testing_defaults$10(var2, var3);
         case 11:
            return this.is_hop_by_hop$11(var2, var3);
         default:
            return null;
      }
   }
}
