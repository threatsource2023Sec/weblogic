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
@MTime(1498849383000L)
@Filename("CGIHTTPServer.py")
public class CGIHTTPServer$py extends PyFunctionTable implements PyRunnable {
   static CGIHTTPServer$py self;
   static final PyCode f$0;
   static final PyCode CGIHTTPRequestHandler$1;
   static final PyCode do_POST$2;
   static final PyCode send_head$3;
   static final PyCode is_cgi$4;
   static final PyCode is_executable$5;
   static final PyCode is_python$6;
   static final PyCode run_cgi$7;
   static final PyCode _url_collapse_path$8;
   static final PyCode nobody_uid$9;
   static final PyCode f$10;
   static final PyCode executable$11;
   static final PyCode test$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("CGI-savvy HTTP Server.\n\nThis module builds on SimpleHTTPServer by implementing GET and POST\nrequests to cgi-bin scripts.\n\nIf the os.fork() function is not present (e.g. on Windows),\nos.popen2() is used as a fallback, with slightly altered semantics; if\nthat function is not present either (e.g. on Macintosh), only Python\nscripts are supported, and they are executed by the current process.\n\nIn all cases, the implementation is intentionally naive -- all\nrequests are executed sychronously.\n\nSECURITY WARNING: DON'T USE THIS CODE UNLESS YOU ARE INSIDE A FIREWALL\n-- it may execute arbitrary Python code or external programs.\n\nNote that status code 200 is sent prior to execution of a CGI script, so\nscripts cannot send other status codes such as 302 (redirect).\n"));
      var1.setline(19);
      PyString.fromInterned("CGI-savvy HTTP Server.\n\nThis module builds on SimpleHTTPServer by implementing GET and POST\nrequests to cgi-bin scripts.\n\nIf the os.fork() function is not present (e.g. on Windows),\nos.popen2() is used as a fallback, with slightly altered semantics; if\nthat function is not present either (e.g. on Macintosh), only Python\nscripts are supported, and they are executed by the current process.\n\nIn all cases, the implementation is intentionally naive -- all\nrequests are executed sychronously.\n\nSECURITY WARNING: DON'T USE THIS CODE UNLESS YOU ARE INSIDE A FIREWALL\n-- it may execute arbitrary Python code or external programs.\n\nNote that status code 200 is sent prior to execution of a CGI script, so\nscripts cannot send other status codes such as 302 (redirect).\n");
      var1.setline(22);
      PyString var3 = PyString.fromInterned("0.4");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(24);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("CGIHTTPRequestHandler")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(26);
      PyObject var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(27);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(28);
      var6 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var6);
      var3 = null;
      var1.setline(29);
      var6 = imp.importOne("BaseHTTPServer", var1, -1);
      var1.setlocal("BaseHTTPServer", var6);
      var3 = null;
      var1.setline(30);
      var6 = imp.importOne("SimpleHTTPServer", var1, -1);
      var1.setlocal("SimpleHTTPServer", var6);
      var3 = null;
      var1.setline(31);
      var6 = imp.importOne("select", var1, -1);
      var1.setlocal("select", var6);
      var3 = null;
      var1.setline(32);
      var6 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var6);
      var3 = null;
      var1.setline(35);
      PyObject[] var7 = new PyObject[]{var1.getname("SimpleHTTPServer").__getattr__("SimpleHTTPRequestHandler")};
      PyObject var4 = Py.makeClass("CGIHTTPRequestHandler", var7, CGIHTTPRequestHandler$1);
      var1.setlocal("CGIHTTPRequestHandler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(303);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, _url_collapse_path$8, PyString.fromInterned("\n    Given a URL path, remove extra '/'s and '.' path elements and collapse\n    any '..' references and returns a colllapsed path.\n\n    Implements something akin to RFC-2396 5.2 step 6 to parse relative paths.\n    The utility of this function is limited to is_cgi method and helps\n    preventing some security attacks.\n\n    Returns: A tuple of (head, tail) where tail is everything after the final /\n    and head is everything before it.  Head will always start with a '/' and,\n    if it contains anything else, never have a trailing '/'.\n\n    Raises: IndexError if too many '..' occur within the path.\n\n    "));
      var1.setlocal("_url_collapse_path", var8);
      var3 = null;
      var1.setline(345);
      var6 = var1.getname("None");
      var1.setlocal("nobody", var6);
      var3 = null;
      var1.setline(347);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, nobody_uid$9, PyString.fromInterned("Internal routine to get nobody's uid"));
      var1.setlocal("nobody_uid", var8);
      var3 = null;
      var1.setline(363);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, executable$11, PyString.fromInterned("Test for executable file."));
      var1.setlocal("executable", var8);
      var3 = null;
      var1.setline(372);
      var7 = new PyObject[]{var1.getname("CGIHTTPRequestHandler"), var1.getname("BaseHTTPServer").__getattr__("HTTPServer")};
      var8 = new PyFunction(var1.f_globals, var7, test$12, (PyObject)null);
      var1.setlocal("test", var8);
      var3 = null;
      var1.setline(377);
      var6 = var1.getname("__name__");
      PyObject var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(378);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CGIHTTPRequestHandler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Complete HTTP server with GET, HEAD and POST commands.\n\n    GET and HEAD also support running CGI scripts.\n\n    The POST command is *only* implemented for CGI scripts.\n\n    "));
      var1.setline(43);
      PyString.fromInterned("Complete HTTP server with GET, HEAD and POST commands.\n\n    GET and HEAD also support running CGI scripts.\n\n    The POST command is *only* implemented for CGI scripts.\n\n    ");
      var1.setline(46);
      PyObject var3 = var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("fork"));
      var1.setlocal("have_fork", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("popen2"));
      var1.setlocal("have_popen2", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("popen3"));
      var1.setlocal("have_popen3", var3);
      var3 = null;
      var1.setline(52);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal("rbufsize", var4);
      var3 = null;
      var1.setline(54);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, do_POST$2, PyString.fromInterned("Serve a POST request.\n\n        This is only implemented for CGI scripts.\n\n        "));
      var1.setlocal("do_POST", var6);
      var3 = null;
      var1.setline(66);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, send_head$3, PyString.fromInterned("Version of send_head that support CGI scripts"));
      var1.setlocal("send_head", var6);
      var3 = null;
      var1.setline(73);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_cgi$4, PyString.fromInterned("Test whether self.path corresponds to a CGI script.\n\n        Returns True and updates the cgi_info attribute to the tuple\n        (dir, rest) if self.path requires running a CGI script.\n        Returns False otherwise.\n\n        If any exception is raised, the caller should assume that\n        self.path was rejected as invalid and act accordingly.\n\n        The default implementation tests whether the normalized url\n        path begins with one of the strings in self.cgi_directories\n        (and the next character is a '/' or the end of the string).\n        "));
      var1.setlocal("is_cgi", var6);
      var3 = null;
      var1.setline(95);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("/cgi-bin"), PyString.fromInterned("/htbin")});
      var1.setlocal("cgi_directories", var7);
      var3 = null;
      var1.setline(97);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_executable$5, PyString.fromInterned("Test whether argument path is an executable file."));
      var1.setlocal("is_executable", var6);
      var3 = null;
      var1.setline(101);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_python$6, PyString.fromInterned("Test whether argument path is a Python script."));
      var1.setlocal("is_python", var6);
      var3 = null;
      var1.setline(106);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run_cgi$7, PyString.fromInterned("Execute a CGI script."));
      var1.setlocal("run_cgi", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject do_POST$2(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("Serve a POST request.\n\n        This is only implemented for CGI scripts.\n\n        ");
      var1.setline(61);
      if (var1.getlocal(0).__getattr__("is_cgi").__call__(var2).__nonzero__()) {
         var1.setline(62);
         var1.getlocal(0).__getattr__("run_cgi").__call__(var2);
      } else {
         var1.setline(64);
         var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(501), (PyObject)PyString.fromInterned("Can only POST to CGI scripts"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_head$3(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Version of send_head that support CGI scripts");
      var1.setline(68);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("is_cgi").__call__(var2).__nonzero__()) {
         var1.setline(69);
         var3 = var1.getlocal(0).__getattr__("run_cgi").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(71);
         var3 = var1.getglobal("SimpleHTTPServer").__getattr__("SimpleHTTPRequestHandler").__getattr__("send_head").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject is_cgi$4(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyString.fromInterned("Test whether self.path corresponds to a CGI script.\n\n        Returns True and updates the cgi_info attribute to the tuple\n        (dir, rest) if self.path requires running a CGI script.\n        Returns False otherwise.\n\n        If any exception is raised, the caller should assume that\n        self.path was rejected as invalid and act accordingly.\n\n        The default implementation tests whether the normalized url\n        path begins with one of the strings in self.cgi_directories\n        (and the next character is a '/' or the end of the string).\n        ");
      var1.setline(87);
      PyObject var3 = var1.getglobal("_url_collapse_path").__call__(var2, var1.getlocal(0).__getattr__("path"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(89);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null), var1.getlocal(1).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(90);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("cgi_directories"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.getlocal(0).__setattr__((String)"cgi_info", var6);
         var3 = null;
         var1.setline(92);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(93);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject is_executable$5(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("Test whether argument path is an executable file.");
      var1.setline(99);
      PyObject var3 = var1.getglobal("executable").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_python$6(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyString.fromInterned("Test whether argument path is a Python script.");
      var1.setline(103);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(104);
      var3 = var1.getlocal(3).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned(".py"), PyString.fromInterned(".pyw")}));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run_cgi$7(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Execute a CGI script.");
      var1.setline(108);
      PyObject var3 = var1.getlocal(0).__getattr__("path");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(109);
      var3 = var1.getlocal(0).__getattr__("cgi_info");
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(111);
      var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1)));
      var1.setlocal(4, var3);
      var3 = null;

      PyTuple var11;
      PyObject var10000;
      while(true) {
         var1.setline(112);
         var3 = var1.getlocal(4);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(113);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(114);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(116);
         var3 = var1.getlocal(0).__getattr__("translate_path").__call__(var2, var1.getlocal(5));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(117);
         if (!var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(7)).__nonzero__()) {
            break;
         }

         var1.setline(118);
         var11 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
         var4 = Py.unpackSequence(var11, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(119);
         var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(124);
      var3 = var1.getlocal(3).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getlocal(4);
      var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      PyString var14;
      if (var10000.__nonzero__()) {
         var1.setline(126);
         var11 = new PyTuple(new PyObject[]{var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null), var1.getlocal(3).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
         var4 = Py.unpackSequence(var11, 2);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(128);
         var14 = PyString.fromInterned("");
         var1.setlocal(8, var14);
         var3 = null;
      }

      var1.setline(132);
      var3 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(4);
      var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(134);
         var11 = new PyTuple(new PyObject[]{var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null), var1.getlocal(3).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null)});
         var4 = Py.unpackSequence(var11, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(136);
         var11 = new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("")});
         var4 = Py.unpackSequence(var11, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(138);
      var3 = var1.getlocal(2)._add(PyString.fromInterned("/"))._add(var1.getlocal(9));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(139);
      var3 = var1.getlocal(0).__getattr__("translate_path").__call__(var2, var1.getlocal(10));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(140);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(11)).__not__().__nonzero__()) {
         var1.setline(141);
         var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(404), (PyObject)PyString.fromInterned("No such CGI script (%r)")._mod(var1.getlocal(10)));
         var1.setline(142);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(143);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(11)).__not__().__nonzero__()) {
            var1.setline(144);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(403), (PyObject)PyString.fromInterned("CGI script is not a plain file (%r)")._mod(var1.getlocal(10)));
            var1.setline(146);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(147);
            var3 = var1.getlocal(0).__getattr__("is_python").__call__(var2, var1.getlocal(10));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(148);
            if (var1.getlocal(12).__not__().__nonzero__()) {
               var1.setline(149);
               var10000 = var1.getlocal(0).__getattr__("have_fork");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("have_popen2");
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("have_popen3");
                  }
               }

               if (var10000.__not__().__nonzero__()) {
                  var1.setline(150);
                  var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(403), (PyObject)PyString.fromInterned("CGI script is not a Python script (%r)")._mod(var1.getlocal(10)));
                  var1.setline(152);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(153);
               if (var1.getlocal(0).__getattr__("is_executable").__call__(var2, var1.getlocal(11)).__not__().__nonzero__()) {
                  var1.setline(154);
                  var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(403), (PyObject)PyString.fromInterned("CGI script is not executable (%r)")._mod(var1.getlocal(10)));
                  var1.setline(156);
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }

            var1.setline(160);
            var3 = var1.getglobal("copy").__getattr__("deepcopy").__call__(var2, var1.getglobal("os").__getattr__("environ"));
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(161);
            var3 = var1.getlocal(0).__getattr__("version_string").__call__(var2);
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("SERVER_SOFTWARE"), var3);
            var3 = null;
            var1.setline(162);
            var3 = var1.getlocal(0).__getattr__("server").__getattr__("server_name");
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("SERVER_NAME"), var3);
            var3 = null;
            var1.setline(163);
            var14 = PyString.fromInterned("CGI/1.1");
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("GATEWAY_INTERFACE"), var14);
            var3 = null;
            var1.setline(164);
            var3 = var1.getlocal(0).__getattr__("protocol_version");
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("SERVER_PROTOCOL"), var3);
            var3 = null;
            var1.setline(165);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("server").__getattr__("server_port"));
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("SERVER_PORT"), var3);
            var3 = null;
            var1.setline(166);
            var3 = var1.getlocal(0).__getattr__("command");
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("REQUEST_METHOD"), var3);
            var3 = null;
            var1.setline(167);
            var3 = var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(3));
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(168);
            var3 = var1.getlocal(14);
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("PATH_INFO"), var3);
            var3 = null;
            var1.setline(169);
            var3 = var1.getlocal(0).__getattr__("translate_path").__call__(var2, var1.getlocal(14));
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("PATH_TRANSLATED"), var3);
            var3 = null;
            var1.setline(170);
            var3 = var1.getlocal(10);
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("SCRIPT_NAME"), var3);
            var3 = null;
            var1.setline(171);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(172);
               var3 = var1.getlocal(8);
               var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("QUERY_STRING"), var3);
               var3 = null;
            }

            var1.setline(173);
            var3 = var1.getlocal(0).__getattr__("address_string").__call__(var2);
            var1.setlocal(15, var3);
            var3 = null;
            var1.setline(174);
            var3 = var1.getlocal(15);
            var10000 = var3._ne(var1.getlocal(0).__getattr__("client_address").__getitem__(Py.newInteger(0)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(175);
               var3 = var1.getlocal(15);
               var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("REMOTE_HOST"), var3);
               var3 = null;
            }

            var1.setline(176);
            var3 = var1.getlocal(0).__getattr__("client_address").__getitem__(Py.newInteger(0));
            var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("REMOTE_ADDR"), var3);
            var3 = null;
            var1.setline(177);
            var3 = var1.getlocal(0).__getattr__("headers").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("authorization"));
            var1.setlocal(16, var3);
            var3 = null;
            var1.setline(178);
            PyObject var10;
            PyException var15;
            if (var1.getlocal(16).__nonzero__()) {
               var1.setline(179);
               var3 = var1.getlocal(16).__getattr__("split").__call__(var2);
               var1.setlocal(16, var3);
               var3 = null;
               var1.setline(180);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(16));
               var10000 = var3._eq(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(181);
                  var3 = imp.importOne("base64", var1, -1);
                  var1.setlocal(17, var3);
                  var3 = null;
                  var3 = imp.importOne("binascii", var1, -1);
                  var1.setlocal(18, var3);
                  var3 = null;
                  var1.setline(182);
                  var3 = var1.getlocal(16).__getitem__(Py.newInteger(0));
                  var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("AUTH_TYPE"), var3);
                  var3 = null;
                  var1.setline(183);
                  var3 = var1.getlocal(16).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
                  var10000 = var3._eq(PyString.fromInterned("basic"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     label248: {
                        try {
                           var1.setline(185);
                           var3 = var1.getlocal(17).__getattr__("decodestring").__call__(var2, var1.getlocal(16).__getitem__(Py.newInteger(1)));
                           var1.setlocal(16, var3);
                           var3 = null;
                        } catch (Throwable var9) {
                           var15 = Py.setException(var9, var1);
                           if (var15.match(var1.getlocal(18).__getattr__("Error"))) {
                              var1.setline(187);
                              break label248;
                           }

                           throw var15;
                        }

                        var1.setline(189);
                        var10 = var1.getlocal(16).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
                        var1.setlocal(16, var10);
                        var4 = null;
                        var1.setline(190);
                        var10 = var1.getglobal("len").__call__(var2, var1.getlocal(16));
                        var10000 = var10._eq(Py.newInteger(2));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(191);
                           var10 = var1.getlocal(16).__getitem__(Py.newInteger(0));
                           var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("REMOTE_USER"), var10);
                           var4 = null;
                        }
                     }
                  }
               }
            }

            var1.setline(193);
            var3 = var1.getlocal(0).__getattr__("headers").__getattr__("typeheader");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(194);
               var3 = var1.getlocal(0).__getattr__("headers").__getattr__("type");
               var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("CONTENT_TYPE"), var3);
               var3 = null;
            } else {
               var1.setline(196);
               var3 = var1.getlocal(0).__getattr__("headers").__getattr__("typeheader");
               var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("CONTENT_TYPE"), var3);
               var3 = null;
            }

            var1.setline(197);
            var3 = var1.getlocal(0).__getattr__("headers").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-length"));
            var1.setlocal(19, var3);
            var3 = null;
            var1.setline(198);
            if (var1.getlocal(19).__nonzero__()) {
               var1.setline(199);
               var3 = var1.getlocal(19);
               var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("CONTENT_LENGTH"), var3);
               var3 = null;
            }

            var1.setline(200);
            var3 = var1.getlocal(0).__getattr__("headers").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("referer"));
            var1.setlocal(20, var3);
            var3 = null;
            var1.setline(201);
            if (var1.getlocal(20).__nonzero__()) {
               var1.setline(202);
               var3 = var1.getlocal(20);
               var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("HTTP_REFERER"), var3);
               var3 = null;
            }

            var1.setline(203);
            PyList var16 = new PyList(Py.EmptyObjects);
            var1.setlocal(21, var16);
            var3 = null;
            var1.setline(204);
            var3 = var1.getlocal(0).__getattr__("headers").__getattr__("getallmatchingheaders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("accept")).__iter__();

            while(true) {
               var1.setline(204);
               var10 = var3.__iternext__();
               if (var10 == null) {
                  var1.setline(209);
                  var3 = PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(21));
                  var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("HTTP_ACCEPT"), var3);
                  var3 = null;
                  var1.setline(210);
                  var3 = var1.getlocal(0).__getattr__("headers").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("user-agent"));
                  var1.setlocal(23, var3);
                  var3 = null;
                  var1.setline(211);
                  if (var1.getlocal(23).__nonzero__()) {
                     var1.setline(212);
                     var3 = var1.getlocal(23);
                     var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("HTTP_USER_AGENT"), var3);
                     var3 = null;
                  }

                  var1.setline(213);
                  var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(0).__getattr__("headers").__getattr__("getheaders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cookie")));
                  var1.setlocal(24, var3);
                  var3 = null;
                  var1.setline(214);
                  if (var1.getlocal(24).__nonzero__()) {
                     var1.setline(215);
                     var3 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(24));
                     var1.getlocal(13).__setitem__((PyObject)PyString.fromInterned("HTTP_COOKIE"), var3);
                     var3 = null;
                  }

                  var1.setline(219);
                  var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("QUERY_STRING"), PyString.fromInterned("REMOTE_HOST"), PyString.fromInterned("CONTENT_LENGTH"), PyString.fromInterned("HTTP_USER_AGENT"), PyString.fromInterned("HTTP_COOKIE"), PyString.fromInterned("HTTP_REFERER")})).__iter__();

                  while(true) {
                     var1.setline(219);
                     var10 = var3.__iternext__();
                     if (var10 == null) {
                        var1.setline(223);
                        var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(200), (PyObject)PyString.fromInterned("Script output follows"));
                        var1.setline(225);
                        var3 = var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+"), (PyObject)PyString.fromInterned(" "));
                        var1.setlocal(26, var3);
                        var3 = null;
                        var1.setline(227);
                        if (var1.getlocal(0).__getattr__("have_fork").__nonzero__()) {
                           var1.setline(229);
                           var16 = new PyList(new PyObject[]{var1.getlocal(9)});
                           var1.setlocal(27, var16);
                           var3 = null;
                           var1.setline(230);
                           var14 = PyString.fromInterned("=");
                           var10000 = var14._notin(var1.getlocal(26));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(231);
                              var1.getlocal(27).__getattr__("append").__call__(var2, var1.getlocal(26));
                           }

                           var1.setline(232);
                           var3 = var1.getglobal("nobody_uid").__call__(var2);
                           var1.setlocal(28, var3);
                           var3 = null;
                           var1.setline(233);
                           var1.getlocal(0).__getattr__("wfile").__getattr__("flush").__call__(var2);
                           var1.setline(234);
                           var3 = var1.getglobal("os").__getattr__("fork").__call__(var2);
                           var1.setlocal(29, var3);
                           var3 = null;
                           var1.setline(235);
                           var3 = var1.getlocal(29);
                           var10000 = var3._ne(Py.newInteger(0));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(237);
                              var3 = var1.getglobal("os").__getattr__("waitpid").__call__((ThreadState)var2, (PyObject)var1.getlocal(29), (PyObject)Py.newInteger(0));
                              var4 = Py.unpackSequence(var3, 2);
                              var5 = var4[0];
                              var1.setlocal(29, var5);
                              var5 = null;
                              var5 = var4[1];
                              var1.setlocal(30, var5);
                              var5 = null;
                              var3 = null;

                              do {
                                 var1.setline(239);
                                 if (!var1.getglobal("select").__getattr__("select").__call__(var2, new PyList(new PyObject[]{var1.getlocal(0).__getattr__("rfile")}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0)).__getitem__(Py.newInteger(0)).__nonzero__()) {
                                    break;
                                 }

                                 var1.setline(240);
                              } while(!var1.getlocal(0).__getattr__("rfile").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__not__().__nonzero__());

                              var1.setline(242);
                              if (var1.getlocal(30).__nonzero__()) {
                                 var1.setline(243);
                                 var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CGI script exit status %#x"), (PyObject)var1.getlocal(30));
                              }

                              var1.setline(244);
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           try {
                              try {
                                 var1.setline(248);
                                 var1.getglobal("os").__getattr__("setuid").__call__(var2, var1.getlocal(28));
                              } catch (Throwable var6) {
                                 var15 = Py.setException(var6, var1);
                                 if (!var15.match(var1.getglobal("os").__getattr__("error"))) {
                                    throw var15;
                                 }

                                 var1.setline(250);
                              }

                              var1.setline(251);
                              var1.getglobal("os").__getattr__("dup2").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("rfile").__getattr__("fileno").__call__(var2), (PyObject)Py.newInteger(0));
                              var1.setline(252);
                              var1.getglobal("os").__getattr__("dup2").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("wfile").__getattr__("fileno").__call__(var2), (PyObject)Py.newInteger(1));
                              var1.setline(253);
                              var1.getglobal("os").__getattr__("execve").__call__(var2, var1.getlocal(11), var1.getlocal(27), var1.getlocal(13));
                           } catch (Throwable var7) {
                              Py.setException(var7, var1);
                              var1.setline(255);
                              var1.getlocal(0).__getattr__("server").__getattr__("handle_error").__call__(var2, var1.getlocal(0).__getattr__("request"), var1.getlocal(0).__getattr__("client_address"));
                              var1.setline(256);
                              var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(127));
                           }
                        } else {
                           var1.setline(260);
                           var3 = imp.importOne("subprocess", var1, -1);
                           var1.setlocal(31, var3);
                           var3 = null;
                           var1.setline(261);
                           var16 = new PyList(new PyObject[]{var1.getlocal(11)});
                           var1.setlocal(32, var16);
                           var3 = null;
                           var1.setline(262);
                           if (var1.getlocal(0).__getattr__("is_python").__call__(var2, var1.getlocal(11)).__nonzero__()) {
                              var1.setline(263);
                              var3 = var1.getglobal("sys").__getattr__("executable");
                              var1.setlocal(33, var3);
                              var3 = null;
                              var1.setline(264);
                              if (var1.getlocal(33).__getattr__("lower").__call__(var2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("w.exe")).__nonzero__()) {
                                 var1.setline(266);
                                 var3 = var1.getlocal(33).__getslice__((PyObject)null, Py.newInteger(-5), (PyObject)null)._add(var1.getlocal(33).__getslice__(Py.newInteger(-4), (PyObject)null, (PyObject)null));
                                 var1.setlocal(33, var3);
                                 var3 = null;
                              }

                              var1.setline(267);
                              var3 = (new PyList(new PyObject[]{var1.getlocal(33), PyString.fromInterned("-u")}))._add(var1.getlocal(32));
                              var1.setlocal(32, var3);
                              var3 = null;
                           }

                           var1.setline(268);
                           var14 = PyString.fromInterned("=");
                           var10000 = var14._notin(var1.getlocal(8));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(269);
                              var1.getlocal(32).__getattr__("append").__call__(var2, var1.getlocal(8));
                           }

                           var1.setline(271);
                           var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("command: %s"), (PyObject)var1.getlocal(31).__getattr__("list2cmdline").__call__(var2, var1.getlocal(32)));

                           try {
                              var1.setline(273);
                              var3 = var1.getglobal("int").__call__(var2, var1.getlocal(19));
                              var1.setlocal(34, var3);
                              var3 = null;
                           } catch (Throwable var8) {
                              var15 = Py.setException(var8, var1);
                              if (!var15.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("ValueError")}))) {
                                 throw var15;
                              }

                              var1.setline(275);
                              PyInteger var12 = Py.newInteger(0);
                              var1.setlocal(34, var12);
                              var4 = null;
                           }

                           var1.setline(276);
                           var10000 = var1.getlocal(31).__getattr__("Popen");
                           PyObject[] var17 = new PyObject[]{var1.getlocal(32), var1.getlocal(31).__getattr__("PIPE"), var1.getlocal(31).__getattr__("PIPE"), var1.getlocal(31).__getattr__("PIPE"), var1.getlocal(13)};
                           String[] var13 = new String[]{"stdin", "stdout", "stderr", "env"};
                           var10000 = var10000.__call__(var2, var17, var13);
                           var3 = null;
                           var3 = var10000;
                           var1.setlocal(35, var3);
                           var3 = null;
                           var1.setline(282);
                           var3 = var1.getlocal(0).__getattr__("command").__getattr__("lower").__call__(var2);
                           var10000 = var3._eq(PyString.fromInterned("post"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var3 = var1.getlocal(34);
                              var10000 = var3._gt(Py.newInteger(0));
                              var3 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(283);
                              var3 = var1.getlocal(0).__getattr__("rfile").__getattr__("read").__call__(var2, var1.getlocal(34));
                              var1.setlocal(36, var3);
                              var3 = null;
                           } else {
                              var1.setline(285);
                              var3 = var1.getglobal("None");
                              var1.setlocal(36, var3);
                              var3 = null;
                           }

                           do {
                              var1.setline(287);
                              if (!var1.getglobal("select").__getattr__("select").__call__(var2, new PyList(new PyObject[]{var1.getlocal(0).__getattr__("rfile").__getattr__("_sock")}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0)).__getitem__(Py.newInteger(0)).__nonzero__()) {
                                 break;
                              }

                              var1.setline(288);
                           } while(!var1.getlocal(0).__getattr__("rfile").__getattr__("_sock").__getattr__("recv").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__not__().__nonzero__());

                           var1.setline(290);
                           var3 = var1.getlocal(35).__getattr__("communicate").__call__(var2, var1.getlocal(36));
                           var4 = Py.unpackSequence(var3, 2);
                           var5 = var4[0];
                           var1.setlocal(37, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(38, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(291);
                           var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, var1.getlocal(37));
                           var1.setline(292);
                           if (var1.getlocal(38).__nonzero__()) {
                              var1.setline(293);
                              var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s"), (PyObject)var1.getlocal(38));
                           }

                           var1.setline(294);
                           var1.getlocal(35).__getattr__("stderr").__getattr__("close").__call__(var2);
                           var1.setline(295);
                           var1.getlocal(35).__getattr__("stdout").__getattr__("close").__call__(var2);
                           var1.setline(296);
                           var3 = var1.getlocal(35).__getattr__("returncode");
                           var1.setlocal(39, var3);
                           var3 = null;
                           var1.setline(297);
                           if (var1.getlocal(39).__nonzero__()) {
                              var1.setline(298);
                              var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CGI script exit status %#x"), (PyObject)var1.getlocal(39));
                           } else {
                              var1.setline(300);
                              var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CGI script exited OK"));
                           }
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(25, var10);
                     var1.setline(221);
                     var1.getlocal(13).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(25), (PyObject)PyString.fromInterned(""));
                  }
               }

               var1.setlocal(22, var10);
               var1.setline(205);
               var5 = var1.getlocal(22).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
               var10000 = var5._in(PyString.fromInterned("\t\n\r "));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(206);
                  var1.getlocal(21).__getattr__("append").__call__(var2, var1.getlocal(22).__getattr__("strip").__call__(var2));
               } else {
                  var1.setline(208);
                  var5 = var1.getlocal(21)._add(var1.getlocal(22).__getslice__(Py.newInteger(7), (PyObject)null, (PyObject)null).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")));
                  var1.setlocal(21, var5);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject _url_collapse_path$8(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyString.fromInterned("\n    Given a URL path, remove extra '/'s and '.' path elements and collapse\n    any '..' references and returns a colllapsed path.\n\n    Implements something akin to RFC-2396 5.2 step 6 to parse relative paths.\n    The utility of this function is limited to is_cgi method and helps\n    preventing some security attacks.\n\n    Returns: A tuple of (head, tail) where tail is everything after the final /\n    and head is everything before it.  Head will always start with a '/' and,\n    if it contains anything else, never have a trailing '/'.\n\n    Raises: IndexError if too many '..' occur within the path.\n\n    ");
      var1.setline(321);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(322);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(323);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

      while(true) {
         var1.setline(323);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(328);
            PyString var7;
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(329);
               var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(330);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(331);
                  var3 = var1.getlocal(4);
                  var10000 = var3._eq(PyString.fromInterned(".."));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(332);
                     var1.getlocal(2).__getattr__("pop").__call__(var2);
                     var1.setline(333);
                     var7 = PyString.fromInterned("");
                     var1.setlocal(4, var7);
                     var3 = null;
                  } else {
                     var1.setline(334);
                     var3 = var1.getlocal(4);
                     var10000 = var3._eq(PyString.fromInterned("."));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(335);
                        var7 = PyString.fromInterned("");
                        var1.setlocal(4, var7);
                        var3 = null;
                     }
                  }
               }
            } else {
               var1.setline(337);
               var7 = PyString.fromInterned("");
               var1.setlocal(4, var7);
               var3 = null;
            }

            var1.setline(339);
            PyTuple var8 = new PyTuple(new PyObject[]{PyString.fromInterned("/")._add(PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(2))), var1.getlocal(4)});
            var1.setlocal(5, var8);
            var3 = null;
            var1.setline(340);
            var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(342);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(324);
         PyObject var5 = var1.getlocal(3);
         var10000 = var5._eq(PyString.fromInterned(".."));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(325);
            var1.getlocal(2).__getattr__("pop").__call__(var2);
         } else {
            var1.setline(326);
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3);
               var10000 = var5._ne(PyString.fromInterned("."));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(327);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
            }
         }
      }
   }

   public PyObject nobody_uid$9(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Internal routine to get nobody's uid");
      var1.setline(350);
      PyObject var8;
      if (var1.getglobal("nobody").__nonzero__()) {
         var1.setline(351);
         var8 = var1.getglobal("nobody");
         var1.f_lasti = -1;
         return var8;
      } else {
         PyException var4;
         PyObject var9;
         try {
            var1.setline(353);
            var9 = imp.importOne("pwd", var1, -1);
            var1.setlocal(0, var9);
            var4 = null;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (var4.match(var1.getglobal("ImportError"))) {
               var1.setline(355);
               PyInteger var3 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         try {
            var1.setline(357);
            var9 = var1.getlocal(0).__getattr__("getpwnam").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nobody")).__getitem__(Py.newInteger(2));
            var1.setglobal("nobody", var9);
            var4 = null;
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (!var4.match(var1.getglobal("KeyError"))) {
               throw var4;
            }

            var1.setline(359);
            PyInteger var10000 = Py.newInteger(1);
            PyObject var10001 = var1.getglobal("max");
            PyObject var10003 = var1.getglobal("map");
            var1.setline(359);
            PyObject[] var5 = Py.EmptyObjects;
            PyObject var10 = var10000._add(var10001.__call__(var2, var10003.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$10)), (PyObject)var1.getlocal(0).__getattr__("getpwall").__call__(var2))));
            var1.setglobal("nobody", var10);
            var5 = null;
         }

         var1.setline(360);
         var8 = var1.getglobal("nobody");
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject f$10(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject executable$11(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyString.fromInterned("Test for executable file.");

      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(366);
         var6 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(368);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(369);
      var6 = var1.getlocal(1).__getattr__("st_mode")._and(Py.newInteger(73));
      PyObject var10000 = var6._ne(Py.newInteger(0));
      var3 = null;
      var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject test$12(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      var1.getglobal("SimpleHTTPServer").__getattr__("test").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public CGIHTTPServer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CGIHTTPRequestHandler$1 = Py.newCode(0, var2, var1, "CGIHTTPRequestHandler", 35, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      do_POST$2 = Py.newCode(1, var2, var1, "do_POST", 54, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      send_head$3 = Py.newCode(1, var2, var1, "send_head", 66, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "collapsed_path", "dir_sep", "head", "tail"};
      is_cgi$4 = Py.newCode(1, var2, var1, "is_cgi", 73, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path"};
      is_executable$5 = Py.newCode(2, var2, var1, "is_executable", 97, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "head", "tail"};
      is_python$6 = Py.newCode(2, var2, var1, "is_python", 101, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "dir", "rest", "i", "nextdir", "nextrest", "scriptdir", "query", "script", "scriptname", "scriptfile", "ispy", "env", "uqrest", "host", "authorization", "base64", "binascii", "length", "referer", "accept", "line", "ua", "co", "k", "decoded_query", "args", "nobody", "pid", "sts", "subprocess", "cmdline", "interp", "nbytes", "p", "data", "stdout", "stderr", "status"};
      run_cgi$7 = Py.newCode(1, var2, var1, "run_cgi", 106, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "path_parts", "head_parts", "part", "tail_part", "splitpath", "collapsed_path"};
      _url_collapse_path$8 = Py.newCode(1, var2, var1, "_url_collapse_path", 303, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pwd"};
      nobody_uid$9 = Py.newCode(0, var2, var1, "nobody_uid", 347, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$10 = Py.newCode(1, var2, var1, "<lambda>", 359, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "st"};
      executable$11 = Py.newCode(1, var2, var1, "executable", 363, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"HandlerClass", "ServerClass"};
      test$12 = Py.newCode(2, var2, var1, "test", 372, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new CGIHTTPServer$py("CGIHTTPServer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(CGIHTTPServer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CGIHTTPRequestHandler$1(var2, var3);
         case 2:
            return this.do_POST$2(var2, var3);
         case 3:
            return this.send_head$3(var2, var3);
         case 4:
            return this.is_cgi$4(var2, var3);
         case 5:
            return this.is_executable$5(var2, var3);
         case 6:
            return this.is_python$6(var2, var3);
         case 7:
            return this.run_cgi$7(var2, var3);
         case 8:
            return this._url_collapse_path$8(var2, var3);
         case 9:
            return this.nobody_uid$9(var2, var3);
         case 10:
            return this.f$10(var2, var3);
         case 11:
            return this.executable$11(var2, var3);
         case 12:
            return this.test$12(var2, var3);
         default:
            return null;
      }
   }
}
