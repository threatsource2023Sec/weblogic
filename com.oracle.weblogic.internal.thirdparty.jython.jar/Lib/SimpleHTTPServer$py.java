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
@Filename("SimpleHTTPServer.py")
public class SimpleHTTPServer$py extends PyFunctionTable implements PyRunnable {
   static SimpleHTTPServer$py self;
   static final PyCode f$0;
   static final PyCode SimpleHTTPRequestHandler$1;
   static final PyCode do_GET$2;
   static final PyCode do_HEAD$3;
   static final PyCode send_head$4;
   static final PyCode list_directory$5;
   static final PyCode f$6;
   static final PyCode translate_path$7;
   static final PyCode copyfile$8;
   static final PyCode guess_type$9;
   static final PyCode test$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Simple HTTP Server.\n\nThis module builds on BaseHTTPServer by implementing the standard GET\nand HEAD requests in a fairly straightforward manner.\n\n"));
      var1.setline(6);
      PyString.fromInterned("Simple HTTP Server.\n\nThis module builds on BaseHTTPServer by implementing the standard GET\nand HEAD requests in a fairly straightforward manner.\n\n");
      var1.setline(9);
      PyString var3 = PyString.fromInterned("0.6");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(11);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("SimpleHTTPRequestHandler")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(13);
      PyObject var8 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var8);
      var3 = null;
      var1.setline(14);
      var8 = imp.importOne("posixpath", var1, -1);
      var1.setlocal("posixpath", var8);
      var3 = null;
      var1.setline(15);
      var8 = imp.importOne("BaseHTTPServer", var1, -1);
      var1.setlocal("BaseHTTPServer", var8);
      var3 = null;
      var1.setline(16);
      var8 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var8);
      var3 = null;
      var1.setline(17);
      var8 = imp.importOne("cgi", var1, -1);
      var1.setlocal("cgi", var8);
      var3 = null;
      var1.setline(18);
      var8 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var8);
      var3 = null;
      var1.setline(19);
      var8 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var8);
      var3 = null;
      var1.setline(20);
      var8 = imp.importOne("mimetypes", var1, -1);
      var1.setlocal("mimetypes", var8);
      var3 = null;

      String[] var4;
      PyObject var10;
      PyObject[] var13;
      try {
         var1.setline(22);
         String[] var12 = new String[]{"StringIO"};
         var13 = imp.importFrom("cStringIO", var12, var1, -1);
         var10 = var13[0];
         var1.setlocal("StringIO", var10);
         var4 = null;
      } catch (Throwable var6) {
         PyException var11 = Py.setException(var6, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(24);
         var4 = new String[]{"StringIO"};
         PyObject[] var9 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var9[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(27);
      var13 = new PyObject[]{var1.getname("BaseHTTPServer").__getattr__("BaseHTTPRequestHandler")};
      var10 = Py.makeClass("SimpleHTTPRequestHandler", var13, SimpleHTTPRequestHandler$1);
      var1.setlocal("SimpleHTTPRequestHandler", var10);
      var4 = null;
      Arrays.fill(var13, (Object)null);
      var1.setline(219);
      var13 = new PyObject[]{var1.getname("SimpleHTTPRequestHandler"), var1.getname("BaseHTTPServer").__getattr__("HTTPServer")};
      PyFunction var14 = new PyFunction(var1.f_globals, var13, test$10, (PyObject)null);
      var1.setlocal("test", var14);
      var3 = null;
      var1.setline(224);
      var8 = var1.getname("__name__");
      PyObject var10000 = var8._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(225);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SimpleHTTPRequestHandler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple HTTP request handler with GET and HEAD commands.\n\n    This serves files from the current directory and any of its\n    subdirectories.  The MIME type for files is determined by\n    calling the .guess_type() method.\n\n    The GET and HEAD requests are identical except that the HEAD\n    request omits the actual contents of the file.\n\n    "));
      var1.setline(38);
      PyString.fromInterned("Simple HTTP request handler with GET and HEAD commands.\n\n    This serves files from the current directory and any of its\n    subdirectories.  The MIME type for files is determined by\n    calling the .guess_type() method.\n\n    The GET and HEAD requests are identical except that the HEAD\n    request omits the actual contents of the file.\n\n    ");
      var1.setline(40);
      PyObject var3 = PyString.fromInterned("SimpleHTTP/")._add(var1.getname("__version__"));
      var1.setlocal("server_version", var3);
      var3 = null;
      var1.setline(42);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, do_GET$2, PyString.fromInterned("Serve a GET request."));
      var1.setlocal("do_GET", var5);
      var3 = null;
      var1.setline(49);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, do_HEAD$3, PyString.fromInterned("Serve a HEAD request."));
      var1.setlocal("do_HEAD", var5);
      var3 = null;
      var1.setline(55);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, send_head$4, PyString.fromInterned("Common code for GET and HEAD commands.\n\n        This sends the response code and MIME headers.\n\n        Return value is either a file object (which has to be copied\n        to the outputfile by the caller unless the command was HEAD,\n        and must be closed by the caller under all circumstances), or\n        None, in which case the caller has nothing further to do.\n\n        "));
      var1.setlocal("send_head", var5);
      var3 = null;
      var1.setline(103);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, list_directory$5, PyString.fromInterned("Helper to produce a directory listing (absent index.html).\n\n        Return value is either a file object, or None (indicating an\n        error).  In either case, the headers are sent, making the\n        interface the same as for send_head().\n\n        "));
      var1.setlocal("list_directory", var5);
      var3 = null;
      var1.setline(146);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, translate_path$7, PyString.fromInterned("Translate a /-separated PATH to the local filename syntax.\n\n        Components that mean special things to the local file system\n        (e.g. drive or directory names) are ignored.  (XXX They should\n        probably be diagnosed.)\n\n        "));
      var1.setlocal("translate_path", var5);
      var3 = null;
      var1.setline(168);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copyfile$8, PyString.fromInterned("Copy all data between two file objects.\n\n        The SOURCE argument is a file object open for reading\n        (or anything with a read() method) and the DESTINATION\n        argument is a file object open for writing (or\n        anything with a write() method).\n\n        The only reason for overriding this would be to change\n        the block size or perhaps to replace newlines by CRLF\n        -- note however that this the default server uses this\n        to copy binary data as well.\n\n        "));
      var1.setlocal("copyfile", var5);
      var3 = null;
      var1.setline(184);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, guess_type$9, PyString.fromInterned("Guess the type of a file.\n\n        Argument is a PATH (a filename).\n\n        Return value is a string of the form type/subtype,\n        usable for a MIME Content-type header.\n\n        The default implementation looks the file's extension\n        up in the table self.extensions_map, using application/octet-stream\n        as a default; however it would be permissible (if\n        slow) to look inside the data to make a better guess.\n\n        "));
      var1.setlocal("guess_type", var5);
      var3 = null;
      var1.setline(208);
      if (var1.getname("mimetypes").__getattr__("inited").__not__().__nonzero__()) {
         var1.setline(209);
         var1.getname("mimetypes").__getattr__("init").__call__(var2);
      }

      var1.setline(210);
      var3 = var1.getname("mimetypes").__getattr__("types_map").__getattr__("copy").__call__(var2);
      var1.setlocal("extensions_map", var3);
      var3 = null;
      var1.setline(211);
      var1.getname("extensions_map").__getattr__("update").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("application/octet-stream"), PyString.fromInterned(".py"), PyString.fromInterned("text/plain"), PyString.fromInterned(".c"), PyString.fromInterned("text/plain"), PyString.fromInterned(".h"), PyString.fromInterned("text/plain")})));
      return var1.getf_locals();
   }

   public PyObject do_GET$2(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Serve a GET request.");
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("send_head").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(45);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(46);
         var1.getlocal(0).__getattr__("copyfile").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("wfile"));
         var1.setline(47);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_HEAD$3(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyString.fromInterned("Serve a HEAD request.");
      var1.setline(51);
      PyObject var3 = var1.getlocal(0).__getattr__("send_head").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(52);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(53);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_head$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyString.fromInterned("Common code for GET and HEAD commands.\n\n        This sends the response code and MIME headers.\n\n        Return value is either a file object (which has to be copied\n        to the outputfile by the caller unless the command was HEAD,\n        and must be closed by the caller under all circumstances), or\n        None, in which case the caller has nothing further to do.\n\n        ");
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getattr__("translate_path").__call__(var2, var1.getlocal(0).__getattr__("path"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(68);
      PyObject var4;
      PyObject var5;
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(69);
         if (var1.getlocal(0).__getattr__("path").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__().__nonzero__()) {
            var1.setline(71);
            var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(301));
            var1.setline(72);
            var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Location"), (PyObject)var1.getlocal(0).__getattr__("path")._add(PyString.fromInterned("/")));
            var1.setline(73);
            var1.getlocal(0).__getattr__("end_headers").__call__(var2);
            var1.setline(74);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(75);
         var4 = (new PyTuple(new PyObject[]{PyString.fromInterned("index.html"), PyString.fromInterned("index.htm")})).__iter__();

         PyObject var6;
         do {
            var1.setline(75);
            var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(81);
               var3 = var1.getlocal(0).__getattr__("list_directory").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(76);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(77);
         } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(3)).__nonzero__());

         var1.setline(78);
         var6 = var1.getlocal(3);
         var1.setlocal(1, var6);
         var6 = null;
      }

      var1.setline(82);
      var4 = var1.getlocal(0).__getattr__("guess_type").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var4);
      var4 = null;

      PyException var9;
      try {
         var1.setline(87);
         var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(2, var4);
         var4 = null;
      } catch (Throwable var7) {
         var9 = Py.setException(var7, var1);
         if (var9.match(var1.getglobal("IOError"))) {
            var1.setline(89);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(404), (PyObject)PyString.fromInterned("File not found"));
            var1.setline(90);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         throw var9;
      }

      var1.setline(91);
      var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(200));
      var1.setline(92);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)var1.getlocal(4));

      try {
         var1.setline(94);
         var4 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(2).__getattr__("fileno").__call__(var2));
         var1.setlocal(5, var4);
         var4 = null;
      } catch (Throwable var8) {
         var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getglobal("OSError"))) {
            throw var9;
         }

         var5 = var9.value;
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(97);
         var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var5);
         var5 = null;
      }

      var1.setline(98);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(6))));
      var1.setline(99);
      var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Last-Modified"), (PyObject)var1.getlocal(0).__getattr__("date_time_string").__call__(var2, var1.getlocal(5).__getattr__("st_mtime")));
      var1.setline(100);
      var1.getlocal(0).__getattr__("end_headers").__call__(var2);
      var1.setline(101);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject list_directory$5(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("Helper to produce a directory listing (absent index.html).\n\n        Return value is either a file object, or None (indicating an\n        error).  In either case, the headers are sent, making the\n        interface the same as for send_head().\n\n        ");

      PyException var3;
      PyObject var4;
      PyObject var8;
      try {
         var1.setline(112);
         var8 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(114);
            var1.getlocal(0).__getattr__("send_error").__call__((ThreadState)var2, (PyObject)Py.newInteger(404), (PyObject)PyString.fromInterned("No permission to list directory"));
            var1.setline(115);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(116);
      PyObject var10000 = var1.getlocal(2).__getattr__("sort");
      PyObject[] var9 = new PyObject[1];
      var1.setline(116);
      PyObject[] var5 = Py.EmptyObjects;
      var9[0] = new PyFunction(var1.f_globals, var5, f$6);
      String[] var10 = new String[]{"key"};
      var10000.__call__(var2, var9, var10);
      var3 = null;
      var1.setline(117);
      var8 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(118);
      var8 = var1.getglobal("cgi").__getattr__("escape").__call__(var2, var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(0).__getattr__("path")));
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(119);
      var1.getlocal(3).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">"));
      var1.setline(120);
      var1.getlocal(3).__getattr__("write").__call__(var2, PyString.fromInterned("<html>\n<title>Directory listing for %s</title>\n")._mod(var1.getlocal(4)));
      var1.setline(121);
      var1.getlocal(3).__getattr__("write").__call__(var2, PyString.fromInterned("<body>\n<h2>Directory listing for %s</h2>\n")._mod(var1.getlocal(4)));
      var1.setline(122);
      var1.getlocal(3).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<hr>\n<ul>\n"));
      var1.setline(123);
      var8 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(123);
         PyObject var11 = var8.__iternext__();
         if (var11 == null) {
            var1.setline(135);
            var1.getlocal(3).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</ul>\n<hr>\n</body>\n</html>\n"));
            var1.setline(136);
            var8 = var1.getlocal(3).__getattr__("tell").__call__(var2);
            var1.setlocal(9, var8);
            var3 = null;
            var1.setline(137);
            var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(138);
            var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(200));
            var1.setline(139);
            if (var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__not__().__nonzero__()) {
               var1.setline(140);
               var8 = var1.getglobal("sys").__getattr__("getfilesystemencoding").__call__(var2);
               var1.setlocal(10, var8);
               var3 = null;
               var1.setline(141);
               var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)PyString.fromInterned("text/html; charset=%s")._mod(var1.getlocal(10)));
            }

            var1.setline(142);
            var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(9)));
            var1.setline(143);
            var1.getlocal(0).__getattr__("end_headers").__call__(var2);
            var1.setline(144);
            var4 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setlocal(5, var11);
         var1.setline(124);
         PyObject var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(125);
         var6 = var1.getlocal(5);
         var1.setlocal(7, var6);
         var1.setlocal(8, var6);
         var1.setline(127);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(6)).__nonzero__()) {
            var1.setline(128);
            var6 = var1.getlocal(5)._add(PyString.fromInterned("/"));
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(129);
            var6 = var1.getlocal(5)._add(PyString.fromInterned("/"));
            var1.setlocal(8, var6);
            var6 = null;
         }

         var1.setline(130);
         if (var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(6)).__nonzero__()) {
            var1.setline(131);
            var6 = var1.getlocal(5)._add(PyString.fromInterned("@"));
            var1.setlocal(7, var6);
            var6 = null;
         }

         var1.setline(133);
         var1.getlocal(3).__getattr__("write").__call__(var2, PyString.fromInterned("<li><a href=\"%s\">%s</a>\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("urllib").__getattr__("quote").__call__(var2, var1.getlocal(8)), var1.getglobal("cgi").__getattr__("escape").__call__(var2, var1.getlocal(7))})));
      }
   }

   public PyObject f$6(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject translate_path$7(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Translate a /-separated PATH to the local filename syntax.\n\n        Components that mean special things to the local file system\n        (e.g. drive or directory names) are ignored.  (XXX They should\n        probably be diagnosed.)\n\n        ");
      var1.setline(155);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(156);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getglobal("posixpath").__getattr__("normpath").__call__(var2, var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(158);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(160);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(161);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(161);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(166);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(162);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(3));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(3, var7);
         var7 = null;
         var5 = null;
         var1.setline(163);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(3));
         var6 = Py.unpackSequence(var5, 2);
         var7 = var6[0];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(3, var7);
         var7 = null;
         var5 = null;
         var1.setline(164);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("curdir"), var1.getglobal("os").__getattr__("pardir")}));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(165);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.setlocal(1, var5);
            var5 = null;
         }
      }
   }

   public PyObject copyfile$8(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString.fromInterned("Copy all data between two file objects.\n\n        The SOURCE argument is a file object open for reading\n        (or anything with a read() method) and the DESTINATION\n        argument is a file object open for writing (or\n        anything with a write() method).\n\n        The only reason for overriding this would be to change\n        the block size or perhaps to replace newlines by CRLF\n        -- note however that this the default server uses this\n        to copy binary data as well.\n\n        ");
      var1.setline(182);
      var1.getglobal("shutil").__getattr__("copyfileobj").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject guess_type$9(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Guess the type of a file.\n\n        Argument is a PATH (a filename).\n\n        Return value is a string of the form type/subtype,\n        usable for a MIME Content-type header.\n\n        The default implementation looks the file's extension\n        up in the table self.extensions_map, using application/octet-stream\n        as a default; however it would be permissible (if\n        slow) to look inside the data to make a better guess.\n\n        ");
      var1.setline(199);
      PyObject var3 = var1.getglobal("posixpath").__getattr__("splitext").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(200);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("extensions_map"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(201);
         var3 = var1.getlocal(0).__getattr__("extensions_map").__getitem__(var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(202);
         PyObject var6 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(203);
         var6 = var1.getlocal(3);
         var10000 = var6._in(var1.getlocal(0).__getattr__("extensions_map"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(204);
            var3 = var1.getlocal(0).__getattr__("extensions_map").__getitem__(var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(206);
            var3 = var1.getlocal(0).__getattr__("extensions_map").__getitem__(PyString.fromInterned(""));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject test$10(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      var1.getglobal("BaseHTTPServer").__getattr__("test").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public SimpleHTTPServer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SimpleHTTPRequestHandler$1 = Py.newCode(0, var2, var1, "SimpleHTTPRequestHandler", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f"};
      do_GET$2 = Py.newCode(1, var2, var1, "do_GET", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      do_HEAD$3 = Py.newCode(1, var2, var1, "do_HEAD", 49, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "f", "index", "ctype", "fs", "AttributeError"};
      send_head$4 = Py.newCode(1, var2, var1, "send_head", 55, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "list", "f", "displaypath", "name", "fullname", "displayname", "linkname", "length", "encoding"};
      list_directory$5 = Py.newCode(2, var2, var1, "list_directory", 103, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a"};
      f$6 = Py.newCode(1, var2, var1, "<lambda>", 116, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "words", "word", "drive", "head"};
      translate_path$7 = Py.newCode(2, var2, var1, "translate_path", 146, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "outputfile"};
      copyfile$8 = Py.newCode(3, var2, var1, "copyfile", 168, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "base", "ext"};
      guess_type$9 = Py.newCode(2, var2, var1, "guess_type", 184, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"HandlerClass", "ServerClass"};
      test$10 = Py.newCode(2, var2, var1, "test", 219, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new SimpleHTTPServer$py("SimpleHTTPServer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(SimpleHTTPServer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SimpleHTTPRequestHandler$1(var2, var3);
         case 2:
            return this.do_GET$2(var2, var3);
         case 3:
            return this.do_HEAD$3(var2, var3);
         case 4:
            return this.send_head$4(var2, var3);
         case 5:
            return this.list_directory$5(var2, var3);
         case 6:
            return this.f$6(var2, var3);
         case 7:
            return this.translate_path$7(var2, var3);
         case 8:
            return this.copyfile$8(var2, var3);
         case 9:
            return this.guess_type$9(var2, var3);
         case 10:
            return this.test$10(var2, var3);
         default:
            return null;
      }
   }
}
