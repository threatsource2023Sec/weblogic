import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@Filename("cgi.py")
public class cgi$py extends PyFunctionTable implements PyRunnable {
   static cgi$py self;
   static final PyCode f$0;
   static final PyCode initlog$1;
   static final PyCode dolog$2;
   static final PyCode nolog$3;
   static final PyCode parse$4;
   static final PyCode parse_qs$5;
   static final PyCode parse_qsl$6;
   static final PyCode parse_multipart$7;
   static final PyCode _parseparam$8;
   static final PyCode parse_header$9;
   static final PyCode MiniFieldStorage$10;
   static final PyCode __init__$11;
   static final PyCode __repr__$12;
   static final PyCode FieldStorage$13;
   static final PyCode __init__$14;
   static final PyCode __repr__$15;
   static final PyCode __iter__$16;
   static final PyCode __getattr__$17;
   static final PyCode __getitem__$18;
   static final PyCode getvalue$19;
   static final PyCode getfirst$20;
   static final PyCode getlist$21;
   static final PyCode keys$22;
   static final PyCode f$23;
   static final PyCode has_key$24;
   static final PyCode f$25;
   static final PyCode __contains__$26;
   static final PyCode f$27;
   static final PyCode __len__$28;
   static final PyCode __nonzero__$29;
   static final PyCode read_urlencoded$30;
   static final PyCode read_multi$31;
   static final PyCode read_single$32;
   static final PyCode read_binary$33;
   static final PyCode read_lines$34;
   static final PyCode _FieldStorage__write$35;
   static final PyCode read_lines_to_eof$36;
   static final PyCode read_lines_to_outerboundary$37;
   static final PyCode skip_lines$38;
   static final PyCode make_file$39;
   static final PyCode FormContentDict$40;
   static final PyCode __init__$41;
   static final PyCode SvFormContentDict$42;
   static final PyCode __getitem__$43;
   static final PyCode getlist$44;
   static final PyCode values$45;
   static final PyCode items$46;
   static final PyCode InterpFormContentDict$47;
   static final PyCode __getitem__$48;
   static final PyCode values$49;
   static final PyCode items$50;
   static final PyCode FormContent$51;
   static final PyCode values$52;
   static final PyCode indexed_value$53;
   static final PyCode value$54;
   static final PyCode length$55;
   static final PyCode stripped$56;
   static final PyCode pars$57;
   static final PyCode test$58;
   static final PyCode f$59;
   static final PyCode g$60;
   static final PyCode print_exception$61;
   static final PyCode print_environ$62;
   static final PyCode print_form$63;
   static final PyCode print_directory$64;
   static final PyCode print_arguments$65;
   static final PyCode print_environ_usage$66;
   static final PyCode escape$67;
   static final PyCode valid_boundary$68;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setglobal("__doc__", PyString.fromInterned("Support module for CGI (Common Gateway Interface) scripts.\n\nThis module defines a number of utilities for use by CGI scripts\nwritten in Python.\n"));
      var1.setline(16);
      PyString.fromInterned("Support module for CGI (Common Gateway Interface) scripts.\n\nThis module defines a number of utilities for use by CGI scripts\nwritten in Python.\n");
      var1.setline(31);
      PyString var3 = PyString.fromInterned("2.6");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(37);
      String[] var8 = new String[]{"attrgetter"};
      PyObject[] var9 = imp.importFrom("operator", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("attrgetter", var4);
      var4 = null;
      var1.setline(38);
      PyObject var10 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var10);
      var3 = null;
      var1.setline(39);
      var10 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var10);
      var3 = null;
      var1.setline(40);
      var10 = imp.importOne("UserDict", var1, -1);
      var1.setlocal("UserDict", var10);
      var3 = null;
      var1.setline(41);
      var10 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var10);
      var3 = null;
      var1.setline(43);
      var8 = new String[]{"filterwarnings", "catch_warnings", "warn"};
      var9 = imp.importFrom("warnings", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("filterwarnings", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("catch_warnings", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("warn", var4);
      var4 = null;
      ContextManager var11;
      var4 = (var11 = ContextGuard.getManager(var1.getname("catch_warnings").__call__(var2))).__enter__(var2);

      label38: {
         try {
            var1.setline(45);
            if (var1.getname("sys").__getattr__("py3kwarning").__nonzero__()) {
               var1.setline(46);
               var1.getname("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*mimetools has been removed"), (PyObject)var1.getname("DeprecationWarning"));
               var1.setline(48);
               var1.getname("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*rfc822 has been removed"), (PyObject)var1.getname("DeprecationWarning"));
            }

            var1.setline(50);
            var4 = imp.importOne("mimetools", var1, -1);
            var1.setlocal("mimetools", var4);
            var4 = null;
            var1.setline(51);
            var4 = imp.importOne("rfc822", var1, -1);
            var1.setlocal("rfc822", var4);
            var4 = null;
         } catch (Throwable var7) {
            if (var11.__exit__(var2, Py.setException(var7, var1))) {
               break label38;
            }

            throw (Throwable)Py.makeException();
         }

         var11.__exit__(var2, (PyException)null);
      }

      try {
         var1.setline(54);
         var8 = new String[]{"StringIO"};
         var9 = imp.importFrom("cStringIO", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("StringIO", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var12 = Py.setException(var6, var1);
         if (!var12.match(var1.getname("ImportError"))) {
            throw var12;
         }

         var1.setline(56);
         String[] var13 = new String[]{"StringIO"};
         PyObject[] var14 = imp.importFrom("StringIO", var13, var1, -1);
         PyObject var5 = var14[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(58);
      PyList var15 = new PyList(new PyObject[]{PyString.fromInterned("MiniFieldStorage"), PyString.fromInterned("FieldStorage"), PyString.fromInterned("FormContentDict"), PyString.fromInterned("SvFormContentDict"), PyString.fromInterned("InterpFormContentDict"), PyString.fromInterned("FormContent"), PyString.fromInterned("parse"), PyString.fromInterned("parse_qs"), PyString.fromInterned("parse_qsl"), PyString.fromInterned("parse_multipart"), PyString.fromInterned("parse_header"), PyString.fromInterned("print_exception"), PyString.fromInterned("print_environ"), PyString.fromInterned("print_form"), PyString.fromInterned("print_directory"), PyString.fromInterned("print_arguments"), PyString.fromInterned("print_environ_usage"), PyString.fromInterned("escape")});
      var1.setlocal("__all__", var15);
      var3 = null;
      var1.setline(68);
      var3 = PyString.fromInterned("");
      var1.setlocal("logfile", var3);
      var3 = null;
      var1.setline(69);
      var10 = var1.getname("None");
      var1.setlocal("logfp", var10);
      var3 = null;
      var1.setline(71);
      var9 = Py.EmptyObjects;
      PyFunction var16 = new PyFunction(var1.f_globals, var9, initlog$1, PyString.fromInterned("Write a log message, if there is a log file.\n\n    Even though this function is called initlog(), you should always\n    use log(); log is a variable that is set either to initlog\n    (initially), to dolog (once the log file has been opened), or to\n    nolog (when logging is disabled).\n\n    The first argument is a format string; the remaining arguments (if\n    any) are arguments to the % operator, so e.g.\n        log(\"%s: %s\", \"a\", \"b\")\n    will write \"a: b\" to the log file, followed by a newline.\n\n    If the global logfp is not None, it should be a file object to\n    which log data is written.\n\n    If the global logfp is None, the global logfile may be a string\n    giving a filename to open, in append mode.  This file should be\n    world writable!!!  If the file can't be opened, logging is\n    silently disabled (since there is no safe place where we could\n    send an error message).\n\n    "));
      var1.setlocal("initlog", var16);
      var3 = null;
      var1.setline(106);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, dolog$2, PyString.fromInterned("Write a log message to the log file.  See initlog() for docs."));
      var1.setlocal("dolog", var16);
      var3 = null;
      var1.setline(110);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, nolog$3, PyString.fromInterned("Dummy function, assigned to log when logging is disabled."));
      var1.setlocal("nolog", var16);
      var3 = null;
      var1.setline(114);
      var10 = var1.getname("initlog");
      var1.setlocal("log", var10);
      var3 = null;
      var1.setline(122);
      PyInteger var17 = Py.newInteger(0);
      var1.setlocal("maxlen", var17);
      var3 = null;
      var1.setline(124);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("os").__getattr__("environ"), Py.newInteger(0), Py.newInteger(0)};
      var16 = new PyFunction(var1.f_globals, var9, parse$4, PyString.fromInterned("Parse a query in the environment or from a file (default stdin)\n\n        Arguments, all optional:\n\n        fp              : file pointer; default: sys.stdin\n\n        environ         : environment dictionary; default: os.environ\n\n        keep_blank_values: flag indicating whether blank values in\n            percent-encoded forms should be treated as blank strings.\n            A true value indicates that blanks should be retained as\n            blank strings.  The default false value indicates that\n            blank values are to be ignored and treated as if they were\n            not included.\n\n        strict_parsing: flag indicating what to do with parsing errors.\n            If false (the default), errors are silently ignored.\n            If true, errors raise a ValueError exception.\n    "));
      var1.setlocal("parse", var16);
      var3 = null;
      var1.setline(180);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var16 = new PyFunction(var1.f_globals, var9, parse_qs$5, PyString.fromInterned("Parse a query given as a string argument."));
      var1.setlocal("parse_qs", var16);
      var3 = null;
      var1.setline(187);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var16 = new PyFunction(var1.f_globals, var9, parse_qsl$6, PyString.fromInterned("Parse a query given as a string argument."));
      var1.setlocal("parse_qsl", var16);
      var3 = null;
      var1.setline(193);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, parse_multipart$7, PyString.fromInterned("Parse multipart input.\n\n    Arguments:\n    fp   : input file\n    pdict: dictionary containing other parameters of content-type header\n\n    Returns a dictionary just like parse_qs(): keys are the field names, each\n    value is a list of values for that field.  This is easy to use but not\n    much good if you are expecting megabytes to be uploaded -- in that case,\n    use the FieldStorage class instead which is much more flexible.  Note\n    that content-type is the raw, unparsed contents of the content-type\n    header.\n\n    XXX This does not parse nested multipart parts -- use FieldStorage for\n    that.\n\n    XXX This should really be subsumed by FieldStorage altogether -- no\n    point in having two implementations of the same parsing algorithm.\n    Also, FieldStorage protects itself better against certain DoS attacks\n    by limiting the size of the data read in one chunk.  The API here\n    does not support that kind of protection.  This also affects parse()\n    since it can call parse_multipart().\n\n    "));
      var1.setlocal("parse_multipart", var16);
      var3 = null;
      var1.setline(291);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, _parseparam$8, (PyObject)null);
      var1.setlocal("_parseparam", var16);
      var3 = null;
      var1.setline(303);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, parse_header$9, PyString.fromInterned("Parse a Content-type like header.\n\n    Return the main content-type and a dictionary of options.\n\n    "));
      var1.setlocal("parse_header", var16);
      var3 = null;
      var1.setline(327);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("MiniFieldStorage", var9, MiniFieldStorage$10);
      var1.setlocal("MiniFieldStorage", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(352);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("FieldStorage", var9, FieldStorage$13);
      var1.setlocal("FieldStorage", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(774);
      var9 = new PyObject[]{var1.getname("UserDict").__getattr__("UserDict")};
      var4 = Py.makeClass("FormContentDict", var9, FormContentDict$40);
      var1.setlocal("FormContentDict", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(794);
      var9 = new PyObject[]{var1.getname("FormContentDict")};
      var4 = Py.makeClass("SvFormContentDict", var9, SvFormContentDict$42);
      var1.setlocal("SvFormContentDict", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(828);
      var9 = new PyObject[]{var1.getname("SvFormContentDict")};
      var4 = Py.makeClass("InterpFormContentDict", var9, InterpFormContentDict$47);
      var1.setlocal("InterpFormContentDict", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(856);
      var9 = new PyObject[]{var1.getname("FormContentDict")};
      var4 = Py.makeClass("FormContent", var9, FormContent$51);
      var1.setlocal("FormContent", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(882);
      var9 = new PyObject[]{var1.getname("os").__getattr__("environ")};
      var16 = new PyFunction(var1.f_globals, var9, test$58, PyString.fromInterned("Robust test CGI script, usable as main program.\n\n    Write minimal HTTP headers and dump all information provided to\n    the script in HTML form.\n\n    "));
      var1.setlocal("test", var16);
      var3 = null;
      var1.setline(921);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var16 = new PyFunction(var1.f_globals, var9, print_exception$61, (PyObject)null);
      var1.setlocal("print_exception", var16);
      var3 = null;
      var1.setline(935);
      var9 = new PyObject[]{var1.getname("os").__getattr__("environ")};
      var16 = new PyFunction(var1.f_globals, var9, print_environ$62, PyString.fromInterned("Dump the shell environment as HTML."));
      var1.setlocal("print_environ", var16);
      var3 = null;
      var1.setline(947);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, print_form$63, PyString.fromInterned("Dump the contents of a form as HTML."));
      var1.setlocal("print_form", var16);
      var3 = null;
      var1.setline(964);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, print_directory$64, PyString.fromInterned("Dump the current directory as HTML."));
      var1.setlocal("print_directory", var16);
      var3 = null;
      var1.setline(976);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, print_arguments$65, (PyObject)null);
      var1.setlocal("print_arguments", var16);
      var3 = null;
      var1.setline(983);
      var9 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var9, print_environ_usage$66, PyString.fromInterned("Dump a list of environment variables used by CGI as HTML."));
      var1.setlocal("print_environ_usage", var16);
      var3 = null;
      var1.setline(1030);
      var9 = new PyObject[]{var1.getname("None")};
      var16 = new PyFunction(var1.f_globals, var9, escape$67, PyString.fromInterned("Replace special characters \"&\", \"<\" and \">\" to HTML-safe sequences.\n    If the optional flag quote is true, the quotation mark character (\")\n    is also translated."));
      var1.setlocal("escape", var16);
      var3 = null;
      var1.setline(1041);
      var9 = new PyObject[]{PyString.fromInterned("^[ -~]{0,200}[!-~]$")};
      var16 = new PyFunction(var1.f_globals, var9, valid_boundary$68, (PyObject)null);
      var1.setlocal("valid_boundary", var16);
      var3 = null;
      var1.setline(1049);
      var10 = var1.getname("__name__");
      PyObject var10000 = var10._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1050);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initlog$1(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("Write a log message, if there is a log file.\n\n    Even though this function is called initlog(), you should always\n    use log(); log is a variable that is set either to initlog\n    (initially), to dolog (once the log file has been opened), or to\n    nolog (when logging is disabled).\n\n    The first argument is a format string; the remaining arguments (if\n    any) are arguments to the % operator, so e.g.\n        log(\"%s: %s\", \"a\", \"b\")\n    will write \"a: b\" to the log file, followed by a newline.\n\n    If the global logfp is not None, it should be a file object to\n    which log data is written.\n\n    If the global logfp is None, the global logfile may be a string\n    giving a filename to open, in append mode.  This file should be\n    world writable!!!  If the file can't be opened, logging is\n    silently disabled (since there is no safe place where we could\n    send an error message).\n\n    ");
      var1.setline(95);
      PyObject var10000 = var1.getglobal("logfile");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("logfp").__not__();
      }

      PyException var3;
      PyObject var6;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(97);
            var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("logfile"), (PyObject)PyString.fromInterned("a"));
            var1.setglobal("logfp", var6);
            var3 = null;
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (!var3.match(var1.getglobal("IOError"))) {
               throw var3;
            }

            var1.setline(99);
         }
      }

      var1.setline(100);
      if (var1.getglobal("logfp").__not__().__nonzero__()) {
         var1.setline(101);
         var6 = var1.getglobal("nolog");
         var1.setglobal("log", var6);
         var3 = null;
      } else {
         var1.setline(103);
         var6 = var1.getglobal("dolog");
         var1.setglobal("log", var6);
         var3 = null;
      }

      var1.setline(104);
      var10000 = var1.getglobal("log");
      PyObject[] var7 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var7, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dolog$2(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Write a log message to the log file.  See initlog() for docs.");
      var1.setline(108);
      var1.getglobal("logfp").__getattr__("write").__call__(var2, var1.getlocal(0)._mod(var1.getlocal(1))._add(PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject nolog$3(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyString.fromInterned("Dummy function, assigned to log when logging is disabled.");
      var1.setline(112);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$4(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Parse a query in the environment or from a file (default stdin)\n\n        Arguments, all optional:\n\n        fp              : file pointer; default: sys.stdin\n\n        environ         : environment dictionary; default: os.environ\n\n        keep_blank_values: flag indicating whether blank values in\n            percent-encoded forms should be treated as blank strings.\n            A true value indicates that blanks should be retained as\n            blank strings.  The default false value indicates that\n            blank values are to be ignored and treated as if they were\n            not included.\n\n        strict_parsing: flag indicating what to do with parsing errors.\n            If false (the default), errors are silently ignored.\n            If true, errors raise a ValueError exception.\n    ");
      var1.setline(144);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(145);
         var3 = var1.getglobal("sys").__getattr__("stdin");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(146);
      PyString var6 = PyString.fromInterned("REQUEST_METHOD");
      var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(147);
         var6 = PyString.fromInterned("GET");
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("REQUEST_METHOD"), var6);
         var3 = null;
      }

      var1.setline(148);
      var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("REQUEST_METHOD"));
      var10000 = var3._eq(PyString.fromInterned("POST"));
      var3 = null;
      PyObject[] var4;
      PyObject var7;
      PyString var8;
      if (var10000.__nonzero__()) {
         var1.setline(149);
         var3 = var1.getglobal("parse_header").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("CONTENT_TYPE")));
         var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(150);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(PyString.fromInterned("multipart/form-data"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(151);
            var3 = var1.getglobal("parse_multipart").__call__(var2, var1.getlocal(0), var1.getlocal(5));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(152);
         var7 = var1.getlocal(4);
         var10000 = var7._eq(PyString.fromInterned("application/x-www-form-urlencoded"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(153);
            var7 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("CONTENT_LENGTH")));
            var1.setlocal(6, var7);
            var4 = null;
            var1.setline(154);
            var10000 = var1.getglobal("maxlen");
            if (var10000.__nonzero__()) {
               var7 = var1.getlocal(6);
               var10000 = var7._gt(var1.getglobal("maxlen"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(155);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Maximum content length exceeded"));
            }

            var1.setline(156);
            var7 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var7);
            var4 = null;
         } else {
            var1.setline(158);
            var8 = PyString.fromInterned("");
            var1.setlocal(7, var8);
            var4 = null;
         }

         var1.setline(159);
         var8 = PyString.fromInterned("QUERY_STRING");
         var10000 = var8._in(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(160);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(160);
               var7 = var1.getlocal(7)._add(PyString.fromInterned("&"));
               var1.setlocal(7, var7);
               var4 = null;
            }

            var1.setline(161);
            var7 = var1.getlocal(7)._add(var1.getlocal(1).__getitem__(PyString.fromInterned("QUERY_STRING")));
            var1.setlocal(7, var7);
            var4 = null;
         } else {
            var1.setline(162);
            if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
               var1.setline(163);
               if (var1.getlocal(7).__nonzero__()) {
                  var1.setline(163);
                  var7 = var1.getlocal(7)._add(PyString.fromInterned("&"));
                  var1.setlocal(7, var7);
                  var4 = null;
               }

               var1.setline(164);
               var7 = var1.getlocal(7)._add(var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)));
               var1.setlocal(7, var7);
               var4 = null;
            }
         }

         var1.setline(165);
         var7 = var1.getlocal(7);
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("QUERY_STRING"), var7);
         var4 = null;
      } else {
         var1.setline(166);
         var8 = PyString.fromInterned("QUERY_STRING");
         var10000 = var8._in(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(167);
            var7 = var1.getlocal(1).__getitem__(PyString.fromInterned("QUERY_STRING"));
            var1.setlocal(7, var7);
            var4 = null;
         } else {
            var1.setline(169);
            if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
               var1.setline(170);
               var7 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
               var1.setlocal(7, var7);
               var4 = null;
            } else {
               var1.setline(172);
               var8 = PyString.fromInterned("");
               var1.setlocal(7, var8);
               var4 = null;
            }

            var1.setline(173);
            var7 = var1.getlocal(7);
            var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("QUERY_STRING"), var7);
            var4 = null;
         }
      }

      var1.setline(174);
      var3 = var1.getglobal("urlparse").__getattr__("parse_qs").__call__(var2, var1.getlocal(7), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_qs$5(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString.fromInterned("Parse a query given as a string argument.");
      var1.setline(182);
      var1.getglobal("warn").__call__((ThreadState)var2, PyString.fromInterned("cgi.parse_qs is deprecated, use urlparse.parse_qs instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(184);
      PyObject var3 = var1.getglobal("urlparse").__getattr__("parse_qs").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_qsl$6(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString.fromInterned("Parse a query given as a string argument.");
      var1.setline(189);
      var1.getglobal("warn").__call__((ThreadState)var2, PyString.fromInterned("cgi.parse_qsl is deprecated, use urlparse.parse_qsl instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(191);
      PyObject var3 = var1.getglobal("urlparse").__getattr__("parse_qsl").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_multipart$7(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      PyString.fromInterned("Parse multipart input.\n\n    Arguments:\n    fp   : input file\n    pdict: dictionary containing other parameters of content-type header\n\n    Returns a dictionary just like parse_qs(): keys are the field names, each\n    value is a list of values for that field.  This is easy to use but not\n    much good if you are expecting megabytes to be uploaded -- in that case,\n    use the FieldStorage class instead which is much more flexible.  Note\n    that content-type is the raw, unparsed contents of the content-type\n    header.\n\n    XXX This does not parse nested multipart parts -- use FieldStorage for\n    that.\n\n    XXX This should really be subsumed by FieldStorage altogether -- no\n    point in having two implementations of the same parsing algorithm.\n    Also, FieldStorage protects itself better against certain DoS attacks\n    by limiting the size of the data read in one chunk.  The API here\n    does not support that kind of protection.  This also affects parse()\n    since it can call parse_multipart().\n\n    ");
      var1.setline(218);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(219);
      var3 = PyString.fromInterned("boundary");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(220);
         var7 = var1.getlocal(1).__getitem__(PyString.fromInterned("boundary"));
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(221);
      if (var1.getglobal("valid_boundary").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(222);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Invalid boundary in multipart form: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
      } else {
         var1.setline(225);
         var7 = PyString.fromInterned("--")._add(var1.getlocal(2));
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(226);
         var7 = PyString.fromInterned("--")._add(var1.getlocal(2))._add(PyString.fromInterned("--"));
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(227);
         PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(5, var8);
         var3 = null;
         var1.setline(228);
         var3 = PyString.fromInterned("");
         var1.setlocal(6, var3);
         var3 = null;

         while(true) {
            var1.setline(230);
            var7 = var1.getlocal(6);
            var10000 = var7._ne(var1.getlocal(4));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(288);
               var7 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(231);
            PyInteger var9 = Py.newInteger(-1);
            var1.setlocal(7, var9);
            var3 = null;
            var1.setline(232);
            var7 = var1.getglobal("None");
            var1.setlocal(8, var7);
            var3 = null;
            var1.setline(233);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(235);
               var7 = var1.getglobal("mimetools").__getattr__("Message").__call__(var2, var1.getlocal(0));
               var1.setlocal(9, var7);
               var3 = null;
               var1.setline(236);
               var7 = var1.getlocal(9).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-length"));
               var1.setlocal(10, var7);
               var3 = null;
               var1.setline(237);
               if (var1.getlocal(10).__nonzero__()) {
                  try {
                     var1.setline(239);
                     var7 = var1.getglobal("int").__call__(var2, var1.getlocal(10));
                     var1.setlocal(7, var7);
                     var3 = null;
                  } catch (Throwable var6) {
                     PyException var10 = Py.setException(var6, var1);
                     if (!var10.match(var1.getglobal("ValueError"))) {
                        throw var10;
                     }

                     var1.setline(241);
                  }
               }

               var1.setline(242);
               var7 = var1.getlocal(7);
               var10000 = var7._gt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(243);
                  var10000 = var1.getglobal("maxlen");
                  if (var10000.__nonzero__()) {
                     var7 = var1.getlocal(7);
                     var10000 = var7._gt(var1.getglobal("maxlen"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(244);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Maximum content length exceeded"));
                  }

                  var1.setline(245);
                  var7 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(7));
                  var1.setlocal(8, var7);
                  var3 = null;
               } else {
                  var1.setline(247);
                  var3 = PyString.fromInterned("");
                  var1.setlocal(8, var3);
                  var3 = null;
               }
            }

            var1.setline(249);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.setlocal(11, var11);
            var3 = null;

            while(true) {
               var1.setline(250);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(251);
               var7 = var1.getlocal(0).__getattr__("readline").__call__(var2);
               var1.setlocal(12, var7);
               var3 = null;
               var1.setline(252);
               if (var1.getlocal(12).__not__().__nonzero__()) {
                  var1.setline(253);
                  var7 = var1.getlocal(4);
                  var1.setlocal(6, var7);
                  var3 = null;
                  break;
               }

               var1.setline(255);
               var7 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
               var10000 = var7._eq(PyString.fromInterned("--"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(256);
                  var7 = var1.getlocal(12).__getattr__("strip").__call__(var2);
                  var1.setlocal(6, var7);
                  var3 = null;
                  var1.setline(257);
                  var7 = var1.getlocal(6);
                  var10000 = var7._in(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)}));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }
               }

               var1.setline(259);
               var1.getlocal(11).__getattr__("append").__call__(var2, var1.getlocal(12));
            }

            var1.setline(261);
            var7 = var1.getlocal(8);
            var10000 = var7._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(263);
               var7 = var1.getlocal(7);
               var10000 = var7._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(264);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(266);
                     var7 = var1.getlocal(11).__getitem__(Py.newInteger(-1));
                     var1.setlocal(12, var7);
                     var3 = null;
                     var1.setline(267);
                     var7 = var1.getlocal(12).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
                     var10000 = var7._eq(PyString.fromInterned("\r\n"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(268);
                        var7 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
                        var1.setlocal(12, var7);
                        var3 = null;
                     } else {
                        var1.setline(269);
                        var7 = var1.getlocal(12).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
                        var10000 = var7._eq(PyString.fromInterned("\n"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(270);
                           var7 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                           var1.setlocal(12, var7);
                           var3 = null;
                        }
                     }

                     var1.setline(271);
                     var7 = var1.getlocal(12);
                     var1.getlocal(11).__setitem__((PyObject)Py.newInteger(-1), var7);
                     var3 = null;
                     var1.setline(272);
                     var7 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(11));
                     var1.setlocal(8, var7);
                     var3 = null;
                  }
               }

               var1.setline(273);
               var7 = var1.getlocal(9).__getitem__(PyString.fromInterned("content-disposition"));
               var1.setlocal(12, var7);
               var3 = null;
               var1.setline(274);
               if (!var1.getlocal(12).__not__().__nonzero__()) {
                  var1.setline(276);
                  var7 = var1.getglobal("parse_header").__call__(var2, var1.getlocal(12));
                  PyObject[] var4 = Py.unpackSequence(var7, 2);
                  PyObject var5 = var4[0];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(14, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(277);
                  var7 = var1.getlocal(13);
                  var10000 = var7._ne(PyString.fromInterned("form-data"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(279);
                     var3 = PyString.fromInterned("name");
                     var10000 = var3._in(var1.getlocal(14));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(280);
                        var7 = var1.getlocal(14).__getitem__(PyString.fromInterned("name"));
                        var1.setlocal(15, var7);
                        var3 = null;
                        var1.setline(283);
                        var7 = var1.getlocal(15);
                        var10000 = var7._in(var1.getlocal(5));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(284);
                           var1.getlocal(5).__getitem__(var1.getlocal(15)).__getattr__("append").__call__(var2, var1.getlocal(8));
                        } else {
                           var1.setline(286);
                           var11 = new PyList(new PyObject[]{var1.getlocal(8)});
                           var1.getlocal(5).__setitem__((PyObject)var1.getlocal(15), var11);
                           var3 = null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _parseparam$8(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      PyObject var5;
      switch (var1.f_lasti) {
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var5 = (PyObject)var10000;
               var1.setline(301);
               var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var3 = null;
            }
         case 0:
         default:
            var1.setline(292);
            var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var5 = var4._eq(PyString.fromInterned(";"));
            var3 = null;
            if (!var5.__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(293);
               var4 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var3 = null;
               var1.setline(294);
               var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
               var1.setlocal(1, var4);
               var3 = null;

               while(true) {
                  var1.setline(295);
                  var4 = var1.getlocal(1);
                  var5 = var4._gt(Py.newInteger(0));
                  var3 = null;
                  if (var5.__nonzero__()) {
                     var5 = var1.getlocal(0).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\""), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1))._sub(var1.getlocal(0).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\\\""), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1)))._mod(Py.newInteger(2));
                  }

                  if (!var5.__nonzero__()) {
                     var1.setline(297);
                     var4 = var1.getlocal(1);
                     var5 = var4._lt(Py.newInteger(0));
                     var3 = null;
                     if (var5.__nonzero__()) {
                        var1.setline(298);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                        var1.setlocal(1, var4);
                        var3 = null;
                     }

                     var1.setline(299);
                     var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
                     var1.setlocal(2, var4);
                     var3 = null;
                     var1.setline(300);
                     var1.setline(300);
                     var5 = var1.getlocal(2).__getattr__("strip").__call__(var2);
                     var1.f_lasti = 1;
                     var3 = new Object[5];
                     var1.f_savedlocals = var3;
                     return var5;
                  }

                  var1.setline(296);
                  var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"), (PyObject)var1.getlocal(1)._add(Py.newInteger(1)));
                  var1.setlocal(1, var4);
                  var3 = null;
               }
            }
      }
   }

   public PyObject parse_header$9(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyString.fromInterned("Parse a Content-type like header.\n\n    Return the main content-type and a dictionary of options.\n\n    ");
      var1.setline(309);
      PyObject var3 = var1.getglobal("_parseparam").__call__(var2, PyString.fromInterned(";")._add(var1.getlocal(0)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(310);
      var3 = var1.getlocal(1).__getattr__("next").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(311);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(312);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(312);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(321);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(4, var4);
         var1.setline(313);
         PyObject var5 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(314);
         var5 = var1.getlocal(5);
         PyObject var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(315);
            var5 = var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(316);
            var5 = var1.getlocal(4).__getslice__(var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(317);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var10000 = var5._ge(Py.newInteger(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(7).__getitem__(Py.newInteger(0));
               PyObject var10001 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
               var10000 = var5;
               var5 = var10001;
               PyObject var6;
               if ((var6 = var10000._eq(var10001)).__nonzero__()) {
                  var6 = var5._eq(PyString.fromInterned("\""));
               }

               var10000 = var6;
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(318);
               var5 = var1.getlocal(7).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
               var1.setlocal(7, var5);
               var5 = null;
               var1.setline(319);
               var5 = var1.getlocal(7).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\"), (PyObject)PyString.fromInterned("\\")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\""), (PyObject)PyString.fromInterned("\""));
               var1.setlocal(7, var5);
               var5 = null;
            }

            var1.setline(320);
            var5 = var1.getlocal(7);
            var1.getlocal(3).__setitem__(var1.getlocal(6), var5);
            var5 = null;
         }
      }
   }

   public PyObject MiniFieldStorage$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Like FieldStorage, for use when no file uploads are possible."));
      var1.setline(329);
      PyString.fromInterned("Like FieldStorage, for use when no file uploads are possible.");
      var1.setline(332);
      PyObject var3 = var1.getname("None");
      var1.setlocal("filename", var3);
      var3 = null;
      var1.setline(333);
      var3 = var1.getname("None");
      var1.setlocal("list", var3);
      var3 = null;
      var1.setline(334);
      var3 = var1.getname("None");
      var1.setlocal("type", var3);
      var3 = null;
      var1.setline(335);
      var3 = var1.getname("None");
      var1.setlocal("file", var3);
      var3 = null;
      var1.setline(336);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("type_options", var4);
      var3 = null;
      var1.setline(337);
      var3 = var1.getname("None");
      var1.setlocal("disposition", var3);
      var3 = null;
      var1.setline(338);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("disposition_options", var4);
      var3 = null;
      var1.setline(339);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("headers", var4);
      var3 = null;
      var1.setline(341);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$11, PyString.fromInterned("Constructor from field name and value."));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(347);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __repr__$12, PyString.fromInterned("Return printable representation."));
      var1.setlocal("__repr__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyString.fromInterned("Constructor from field name and value.");
      var1.setline(343);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(344);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$12(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Return printable representation.");
      var1.setline(349);
      PyObject var3 = PyString.fromInterned("MiniFieldStorage(%r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("value")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FieldStorage$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Store a sequence of fields, reading multipart/form-data.\n\n    This class provides naming, typing, files stored on disk, and\n    more.  At the top level, it is accessible like a dictionary, whose\n    keys are the field names.  (Note: None can occur as a field name.)\n    The items are either a Python list (if there's multiple values) or\n    another FieldStorage or MiniFieldStorage object.  If it's a single\n    object, it has the following attributes:\n\n    name: the field name, if specified; otherwise None\n\n    filename: the filename, if specified; otherwise None; this is the\n        client side filename, *not* the file name on which it is\n        stored (that's a temporary file you don't deal with)\n\n    value: the value as a *string*; for file uploads, this\n        transparently reads the file every time you request the value\n\n    file: the file(-like) object from which you can read the data;\n        None if the data is stored a simple string\n\n    type: the content-type, or None if not specified\n\n    type_options: dictionary of options specified on the content-type\n        line\n\n    disposition: content-disposition, or None if not specified\n\n    disposition_options: dictionary of corresponding options\n\n    headers: a dictionary(-like) object (sometimes rfc822.Message or a\n        subclass thereof) containing *all* headers\n\n    The class is subclassable, mostly for the purpose of overriding\n    the make_file() method, which is called internally to come up with\n    a file open for reading and writing.  This makes it possible to\n    override the default choice of storing all files in a temporary\n    directory and unlinking them as soon as they have been opened.\n\n    "));
      var1.setline(393);
      PyString.fromInterned("Store a sequence of fields, reading multipart/form-data.\n\n    This class provides naming, typing, files stored on disk, and\n    more.  At the top level, it is accessible like a dictionary, whose\n    keys are the field names.  (Note: None can occur as a field name.)\n    The items are either a Python list (if there's multiple values) or\n    another FieldStorage or MiniFieldStorage object.  If it's a single\n    object, it has the following attributes:\n\n    name: the field name, if specified; otherwise None\n\n    filename: the filename, if specified; otherwise None; this is the\n        client side filename, *not* the file name on which it is\n        stored (that's a temporary file you don't deal with)\n\n    value: the value as a *string*; for file uploads, this\n        transparently reads the file every time you request the value\n\n    file: the file(-like) object from which you can read the data;\n        None if the data is stored a simple string\n\n    type: the content-type, or None if not specified\n\n    type_options: dictionary of options specified on the content-type\n        line\n\n    disposition: content-disposition, or None if not specified\n\n    disposition_options: dictionary of corresponding options\n\n    headers: a dictionary(-like) object (sometimes rfc822.Message or a\n        subclass thereof) containing *all* headers\n\n    The class is subclassable, mostly for the purpose of overriding\n    the make_file() method, which is called internally to come up with\n    a file open for reading and writing.  This makes it possible to\n    override the default choice of storing all files in a temporary\n    directory and unlinking them as soon as they have been opened.\n\n    ");
      var1.setline(395);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), PyString.fromInterned(""), var1.getname("os").__getattr__("environ"), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, PyString.fromInterned("Constructor.  Read multipart/* until last part.\n\n        Arguments, all optional:\n\n        fp              : file pointer; default: sys.stdin\n            (not used when the request method is GET)\n\n        headers         : header dictionary-like object; default:\n            taken from environ as per CGI spec\n\n        outerboundary   : terminating multipart boundary\n            (for internal use only)\n\n        environ         : environment dictionary; default: os.environ\n\n        keep_blank_values: flag indicating whether blank values in\n            percent-encoded forms should be treated as blank strings.\n            A true value indicates that blanks should be retained as\n            blank strings.  The default false value indicates that\n            blank values are to be ignored and treated as if they were\n            not included.\n\n        strict_parsing: flag indicating what to do with parsing errors.\n            If false (the default), errors are silently ignored.\n            If true, errors raise a ValueError exception.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$15, PyString.fromInterned("Return a printable representation."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(516);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$16, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(519);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$17, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(532);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$18, PyString.fromInterned("Dictionary style indexing."));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(546);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getvalue$19, PyString.fromInterned("Dictionary style get() method, including 'value' lookup."));
      var1.setlocal("getvalue", var4);
      var3 = null;
      var1.setline(557);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getfirst$20, PyString.fromInterned(" Return the first value received."));
      var1.setlocal("getfirst", var4);
      var3 = null;
      var1.setline(568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getlist$21, PyString.fromInterned(" Return list of received values."));
      var1.setlocal("getlist", var4);
      var3 = null;
      var1.setline(579);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$22, PyString.fromInterned("Dictionary style keys() method."));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(585);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$24, PyString.fromInterned("Dictionary style has_key() method."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(591);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$26, PyString.fromInterned("Dictionary style __contains__ method."));
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(597);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$28, PyString.fromInterned("Dictionary style len(x) support."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(601);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __nonzero__$29, (PyObject)null);
      var1.setlocal("__nonzero__", var4);
      var3 = null;
      var1.setline(604);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_urlencoded$30, PyString.fromInterned("Internal: read data in query string format."));
      var1.setlocal("read_urlencoded", var4);
      var3 = null;
      var1.setline(615);
      PyObject var5 = var1.getname("None");
      var1.setlocal("FieldStorageClass", var5);
      var3 = null;
      var1.setline(617);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_multi$31, PyString.fromInterned("Internal: read a part that is itself multipart."));
      var1.setlocal("read_multi", var4);
      var3 = null;
      var1.setline(640);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_single$32, PyString.fromInterned("Internal: read an atomic part."));
      var1.setlocal("read_single", var4);
      var3 = null;
      var1.setline(649);
      var5 = Py.newInteger(8)._mul(Py.newInteger(1024));
      var1.setlocal("bufsize", var5);
      var3 = null;
      var1.setline(651);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_binary$33, PyString.fromInterned("Internal: read binary data."));
      var1.setlocal("read_binary", var4);
      var3 = null;
      var1.setline(664);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_lines$34, PyString.fromInterned("Internal: read lines until EOF or outerboundary."));
      var1.setlocal("read_lines", var4);
      var3 = null;
      var1.setline(672);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _FieldStorage__write$35, (PyObject)null);
      var1.setlocal("_FieldStorage__write", var4);
      var3 = null;
      var1.setline(680);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_lines_to_eof$36, PyString.fromInterned("Internal: read lines until EOF."));
      var1.setlocal("read_lines_to_eof", var4);
      var3 = null;
      var1.setline(689);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_lines_to_outerboundary$37, PyString.fromInterned("Internal: read lines until outerboundary."));
      var1.setlocal("read_lines_to_outerboundary", var4);
      var3 = null;
      var1.setline(721);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, skip_lines$38, PyString.fromInterned("Internal: skip lines until outer boundary if defined."));
      var1.setlocal("skip_lines", var4);
      var3 = null;
      var1.setline(742);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, make_file$39, PyString.fromInterned("Overridable: return a readable & writable file.\n\n        The file will be used as follows:\n        - data is written to it\n        - seek(0)\n        - data is read from it\n\n        The 'binary' argument is unused -- the file is always opened\n        in binary mode.\n\n        This version opens a temporary file for reading and writing,\n        and immediately deletes (unlinks) it.  The trick (on Unix!) is\n        that the file can still be used, but it can't be opened by\n        another process, and it will automatically be deleted when it\n        is closed or when the current process terminates.\n\n        If you want a more permanent file, you derive a class which\n        overrides this method.  If you want a visible temporary file\n        that is nevertheless automatically deleted when the script\n        terminates, try defining a __del__ method in a derived class\n        which unlinks the temporary files you have created.\n\n        "));
      var1.setlocal("make_file", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyString.fromInterned("Constructor.  Read multipart/* until last part.\n\n        Arguments, all optional:\n\n        fp              : file pointer; default: sys.stdin\n            (not used when the request method is GET)\n\n        headers         : header dictionary-like object; default:\n            taken from environ as per CGI spec\n\n        outerboundary   : terminating multipart boundary\n            (for internal use only)\n\n        environ         : environment dictionary; default: os.environ\n\n        keep_blank_values: flag indicating whether blank values in\n            percent-encoded forms should be treated as blank strings.\n            A true value indicates that blanks should be retained as\n            blank strings.  The default false value indicates that\n            blank values are to be ignored and treated as if they were\n            not included.\n\n        strict_parsing: flag indicating what to do with parsing errors.\n            If false (the default), errors are silently ignored.\n            If true, errors raise a ValueError exception.\n\n        ");
      var1.setline(424);
      PyString var3 = PyString.fromInterned("GET");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(425);
      PyObject var7 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("keep_blank_values", var7);
      var3 = null;
      var1.setline(426);
      var7 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("strict_parsing", var7);
      var3 = null;
      var1.setline(427);
      var3 = PyString.fromInterned("REQUEST_METHOD");
      PyObject var10000 = var3._in(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(428);
         var7 = var1.getlocal(4).__getitem__(PyString.fromInterned("REQUEST_METHOD")).__getattr__("upper").__call__(var2);
         var1.setlocal(7, var7);
         var3 = null;
      }

      var1.setline(429);
      var7 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("qs_on_post", var7);
      var3 = null;
      var1.setline(430);
      var7 = var1.getlocal(7);
      var10000 = var7._eq(PyString.fromInterned("GET"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var7 = var1.getlocal(7);
         var10000 = var7._eq(PyString.fromInterned("HEAD"));
         var3 = null;
      }

      PyDictionary var8;
      if (var10000.__nonzero__()) {
         var1.setline(431);
         var3 = PyString.fromInterned("QUERY_STRING");
         var10000 = var3._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(432);
            var7 = var1.getlocal(4).__getitem__(PyString.fromInterned("QUERY_STRING"));
            var1.setlocal(8, var7);
            var3 = null;
         } else {
            var1.setline(433);
            if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
               var1.setline(434);
               var7 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
               var1.setlocal(8, var7);
               var3 = null;
            } else {
               var1.setline(436);
               var3 = PyString.fromInterned("");
               var1.setlocal(8, var3);
               var3 = null;
            }
         }

         var1.setline(437);
         var7 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(8));
         var1.setlocal(1, var7);
         var3 = null;
         var1.setline(438);
         var7 = var1.getlocal(2);
         var10000 = var7._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(439);
            var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("content-type"), PyString.fromInterned("application/x-www-form-urlencoded")});
            var1.setlocal(2, var8);
            var3 = null;
         }
      }

      var1.setline(441);
      var7 = var1.getlocal(2);
      var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(442);
         var8 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(443);
         var7 = var1.getlocal(7);
         var10000 = var7._eq(PyString.fromInterned("POST"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(445);
            var3 = PyString.fromInterned("application/x-www-form-urlencoded");
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-type"), var3);
            var3 = null;
         }

         var1.setline(446);
         var3 = PyString.fromInterned("CONTENT_TYPE");
         var10000 = var3._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(447);
            var7 = var1.getlocal(4).__getitem__(PyString.fromInterned("CONTENT_TYPE"));
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-type"), var7);
            var3 = null;
         }

         var1.setline(448);
         var3 = PyString.fromInterned("QUERY_STRING");
         var10000 = var3._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(449);
            var7 = var1.getlocal(4).__getitem__(PyString.fromInterned("QUERY_STRING"));
            var1.getlocal(0).__setattr__("qs_on_post", var7);
            var3 = null;
         }

         var1.setline(450);
         var3 = PyString.fromInterned("CONTENT_LENGTH");
         var10000 = var3._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(451);
            var7 = var1.getlocal(4).__getitem__(PyString.fromInterned("CONTENT_LENGTH"));
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("content-length"), var7);
            var3 = null;
         }
      }

      var1.setline(452);
      var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("stdin");
      }

      var7 = var10000;
      var1.getlocal(0).__setattr__("fp", var7);
      var3 = null;
      var1.setline(453);
      var7 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("headers", var7);
      var3 = null;
      var1.setline(454);
      var7 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("outerboundary", var7);
      var3 = null;
      var1.setline(457);
      PyTuple var9 = new PyTuple(new PyObject[]{PyString.fromInterned(""), new PyDictionary(Py.EmptyObjects)});
      PyObject[] var4 = Py.unpackSequence(var9, 2);
      PyObject var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(458);
      var3 = PyString.fromInterned("content-disposition");
      var10000 = var3._in(var1.getlocal(0).__getattr__("headers"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(459);
         var7 = var1.getglobal("parse_header").__call__(var2, var1.getlocal(0).__getattr__("headers").__getitem__(PyString.fromInterned("content-disposition")));
         var4 = Py.unpackSequence(var7, 2);
         var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(460);
      var7 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("disposition", var7);
      var3 = null;
      var1.setline(461);
      var7 = var1.getlocal(10);
      var1.getlocal(0).__setattr__("disposition_options", var7);
      var3 = null;
      var1.setline(462);
      var7 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("name", var7);
      var3 = null;
      var1.setline(463);
      var3 = PyString.fromInterned("name");
      var10000 = var3._in(var1.getlocal(10));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(464);
         var7 = var1.getlocal(10).__getitem__(PyString.fromInterned("name"));
         var1.getlocal(0).__setattr__("name", var7);
         var3 = null;
      }

      var1.setline(465);
      var7 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("filename", var7);
      var3 = null;
      var1.setline(466);
      var3 = PyString.fromInterned("filename");
      var10000 = var3._in(var1.getlocal(10));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(467);
         var7 = var1.getlocal(10).__getitem__(PyString.fromInterned("filename"));
         var1.getlocal(0).__setattr__("filename", var7);
         var3 = null;
      }

      var1.setline(481);
      var3 = PyString.fromInterned("content-type");
      var10000 = var3._in(var1.getlocal(0).__getattr__("headers"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(482);
         var7 = var1.getglobal("parse_header").__call__(var2, var1.getlocal(0).__getattr__("headers").__getitem__(PyString.fromInterned("content-type")));
         var4 = Py.unpackSequence(var7, 2);
         var5 = var4[0];
         var1.setlocal(11, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(483);
         var10000 = var1.getlocal(0).__getattr__("outerboundary");
         if (!var10000.__nonzero__()) {
            var7 = var1.getlocal(7);
            var10000 = var7._ne(PyString.fromInterned("POST"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(484);
            var9 = new PyTuple(new PyObject[]{PyString.fromInterned("text/plain"), new PyDictionary(Py.EmptyObjects)});
            var4 = Py.unpackSequence(var9, 2);
            var5 = var4[0];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(486);
            var9 = new PyTuple(new PyObject[]{PyString.fromInterned("application/x-www-form-urlencoded"), new PyDictionary(Py.EmptyObjects)});
            var4 = Py.unpackSequence(var9, 2);
            var5 = var4[0];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var3 = null;
         }
      }

      var1.setline(487);
      var7 = var1.getlocal(11);
      var1.getlocal(0).__setattr__("type", var7);
      var3 = null;
      var1.setline(488);
      var7 = var1.getlocal(10);
      var1.getlocal(0).__setattr__("type_options", var7);
      var3 = null;
      var1.setline(489);
      var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"innerboundary", var3);
      var3 = null;
      var1.setline(490);
      var3 = PyString.fromInterned("boundary");
      var10000 = var3._in(var1.getlocal(10));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(491);
         var7 = var1.getlocal(10).__getitem__(PyString.fromInterned("boundary"));
         var1.getlocal(0).__setattr__("innerboundary", var7);
         var3 = null;
      }

      var1.setline(492);
      PyInteger var10 = Py.newInteger(-1);
      var1.setlocal(12, var10);
      var3 = null;
      var1.setline(493);
      var3 = PyString.fromInterned("content-length");
      var10000 = var3._in(var1.getlocal(0).__getattr__("headers"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(495);
            var7 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("headers").__getitem__(PyString.fromInterned("content-length")));
            var1.setlocal(12, var7);
            var3 = null;
         } catch (Throwable var6) {
            PyException var11 = Py.setException(var6, var1);
            if (!var11.match(var1.getglobal("ValueError"))) {
               throw var11;
            }

            var1.setline(497);
         }

         var1.setline(498);
         var10000 = var1.getglobal("maxlen");
         if (var10000.__nonzero__()) {
            var7 = var1.getlocal(12);
            var10000 = var7._gt(var1.getglobal("maxlen"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(499);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Maximum content length exceeded"));
         }
      }

      var1.setline(500);
      var7 = var1.getlocal(12);
      var1.getlocal(0).__setattr__("length", var7);
      var3 = null;
      var1.setline(502);
      var7 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("list", var7);
      var1.getlocal(0).__setattr__("file", var7);
      var1.setline(503);
      var10 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"done", var10);
      var3 = null;
      var1.setline(504);
      var7 = var1.getlocal(11);
      var10000 = var7._eq(PyString.fromInterned("application/x-www-form-urlencoded"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(505);
         var1.getlocal(0).__getattr__("read_urlencoded").__call__(var2);
      } else {
         var1.setline(506);
         var7 = var1.getlocal(11).__getslice__((PyObject)null, Py.newInteger(10), (PyObject)null);
         var10000 = var7._eq(PyString.fromInterned("multipart/"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(507);
            var1.getlocal(0).__getattr__("read_multi").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
         } else {
            var1.setline(509);
            var1.getlocal(0).__getattr__("read_single").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$15(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      PyString.fromInterned("Return a printable representation.");
      var1.setline(513);
      PyObject var3 = PyString.fromInterned("FieldStorage(%r, %r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("value")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$16(PyFrame var1, ThreadState var2) {
      var1.setline(517);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getattr__$17(PyFrame var1, ThreadState var2) {
      var1.setline(520);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(PyString.fromInterned("value"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(521);
         throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
      } else {
         var1.setline(522);
         if (var1.getlocal(0).__getattr__("file").__nonzero__()) {
            var1.setline(523);
            var1.getlocal(0).__getattr__("file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(524);
            var3 = var1.getlocal(0).__getattr__("file").__getattr__("read").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(525);
            var1.getlocal(0).__getattr__("file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         } else {
            var1.setline(526);
            var3 = var1.getlocal(0).__getattr__("list");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(527);
               var3 = var1.getlocal(0).__getattr__("list");
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(529);
               var3 = var1.getglobal("None");
               var1.setlocal(2, var3);
               var3 = null;
            }
         }

         var1.setline(530);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __getitem__$18(PyFrame var1, ThreadState var2) {
      var1.setline(533);
      PyString.fromInterned("Dictionary style indexing.");
      var1.setline(534);
      PyObject var3 = var1.getlocal(0).__getattr__("list");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(535);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not indexable"));
      } else {
         var1.setline(536);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(537);
         var3 = var1.getlocal(0).__getattr__("list").__iter__();

         while(true) {
            var1.setline(537);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(539);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(540);
                  throw Py.makeException(var1.getglobal("KeyError"), var1.getlocal(1));
               }

               var1.setline(541);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(542);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(544);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var4);
            var1.setline(538);
            PyObject var5 = var1.getlocal(3).__getattr__("name");
            var10000 = var5._eq(var1.getlocal(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(538);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
            }
         }
      }
   }

   public PyObject getvalue$19(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyString.fromInterned("Dictionary style get() method, including 'value' lookup.");
      var1.setline(548);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(549);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(550);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(551);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("attrgetter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("value")), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(553);
            var3 = var1.getlocal(3).__getattr__("value");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(555);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getfirst$20(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      PyString.fromInterned(" Return the first value received.");
      var1.setline(559);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(560);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(561);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10000 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(562);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("value");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(564);
            var3 = var1.getlocal(3).__getattr__("value");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(566);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getlist$21(PyFrame var1, ThreadState var2) {
      var1.setline(569);
      PyString.fromInterned(" Return list of received values.");
      var1.setline(570);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      PyList var4;
      if (var10000.__nonzero__()) {
         var1.setline(571);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(572);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var10000 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(573);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("attrgetter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("value")), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(575);
            var4 = new PyList(new PyObject[]{var1.getlocal(2).__getattr__("value")});
            var1.f_lasti = -1;
            return var4;
         }
      } else {
         var1.setline(577);
         var4 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject keys$22(PyFrame var1, ThreadState var2) {
      var1.setline(580);
      PyString.fromInterned("Dictionary style keys() method.");
      var1.setline(581);
      PyObject var3 = var1.getlocal(0).__getattr__("list");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(582);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not indexable"));
      } else {
         var1.setline(583);
         var10000 = var1.getglobal("list");
         PyObject var10002 = var1.getglobal("set");
         var1.setline(583);
         PyObject[] var5 = Py.EmptyObjects;
         PyFunction var4 = new PyFunction(var1.f_globals, var5, f$23, (PyObject)null);
         PyObject var10004 = var4.__call__(var2, var1.getlocal(0).__getattr__("list").__iter__());
         Arrays.fill(var5, (Object)null);
         var3 = var10000.__call__(var2, var10002.__call__(var2, var10004));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$23(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(583);
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

      var1.setline(583);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(583);
         var1.setline(583);
         var6 = var1.getlocal(1).__getattr__("name");
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject has_key$24(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(586);
      PyString.fromInterned("Dictionary style has_key() method.");
      var1.setline(587);
      PyObject var3 = var1.getlocal(0).__getattr__("list");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(588);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not indexable"));
      } else {
         var1.setline(589);
         var10000 = var1.getglobal("any");
         var1.setline(589);
         PyObject var10004 = var1.f_globals;
         PyObject[] var5 = Py.EmptyObjects;
         PyCode var10006 = f$25;
         PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10004, var5, var10006, (PyObject)null, var4);
         PyObject var10002 = var6.__call__(var2, var1.getlocal(0).__getattr__("list").__iter__());
         Arrays.fill(var5, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$25(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(589);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(589);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(589);
         var1.setline(589);
         PyObject var6 = var1.getlocal(1).__getattr__("name");
         var7 = var6._eq(var1.getderef(0));
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject __contains__$26(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(592);
      PyString.fromInterned("Dictionary style __contains__ method.");
      var1.setline(593);
      PyObject var3 = var1.getlocal(0).__getattr__("list");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(594);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not indexable"));
      } else {
         var1.setline(595);
         var10000 = var1.getglobal("any");
         var1.setline(595);
         PyObject var10004 = var1.f_globals;
         PyObject[] var5 = Py.EmptyObjects;
         PyCode var10006 = f$27;
         PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10004, var5, var10006, (PyObject)null, var4);
         PyObject var10002 = var6.__call__(var2, var1.getlocal(0).__getattr__("list").__iter__());
         Arrays.fill(var5, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(595);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(595);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(595);
         var1.setline(595);
         PyObject var6 = var1.getlocal(1).__getattr__("name");
         var7 = var6._eq(var1.getderef(0));
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject __len__$28(PyFrame var1, ThreadState var2) {
      var1.setline(598);
      PyString.fromInterned("Dictionary style len(x) support.");
      var1.setline(599);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __nonzero__$29(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(0).__getattr__("list"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read_urlencoded$30(PyFrame var1, ThreadState var2) {
      var1.setline(605);
      PyString.fromInterned("Internal: read data in query string format.");
      var1.setline(606);
      PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("length"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(607);
      if (var1.getlocal(0).__getattr__("qs_on_post").__nonzero__()) {
         var1.setline(608);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(PyString.fromInterned("&")._add(var1.getlocal(0).__getattr__("qs_on_post")));
         var1.setlocal(1, var3);
      }

      var1.setline(609);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"list", var7);
      var1.setlocal(2, var7);
      var1.setline(610);
      var3 = var1.getglobal("urlparse").__getattr__("parse_qsl").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("keep_blank_values"), var1.getlocal(0).__getattr__("strict_parsing")).__iter__();

      while(true) {
         var1.setline(610);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(613);
            var1.getlocal(0).__getattr__("skip_lines").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(612);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("MiniFieldStorage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }
   }

   public PyObject read_multi$31(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyString.fromInterned("Internal: read a part that is itself multipart.");
      var1.setline(619);
      PyObject var3 = var1.getlocal(0).__getattr__("innerboundary");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(620);
      if (var1.getglobal("valid_boundary").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
         var1.setline(621);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Invalid boundary in multipart form: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)})));
      } else {
         var1.setline(622);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"list", var7);
         var3 = null;
         var1.setline(623);
         if (var1.getlocal(0).__getattr__("qs_on_post").__nonzero__()) {
            var1.setline(624);
            var3 = var1.getglobal("urlparse").__getattr__("parse_qsl").__call__(var2, var1.getlocal(0).__getattr__("qs_on_post"), var1.getlocal(0).__getattr__("keep_blank_values"), var1.getlocal(0).__getattr__("strict_parsing")).__iter__();

            while(true) {
               var1.setline(624);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(627);
                  var3 = var1.getglobal("None");
                  var1.setlocal(7, var3);
                  var3 = null;
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(626);
               var1.getlocal(0).__getattr__("list").__getattr__("append").__call__(var2, var1.getglobal("MiniFieldStorage").__call__(var2, var1.getlocal(5), var1.getlocal(6)));
            }
         }

         var1.setline(629);
         PyObject var10000 = var1.getlocal(0).__getattr__("FieldStorageClass");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("__class__");
         }

         var3 = var10000;
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(630);
         var10000 = var1.getlocal(8);
         PyObject[] var8 = new PyObject[]{var1.getlocal(0).__getattr__("fp"), new PyDictionary(Py.EmptyObjects), var1.getlocal(4), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
         var3 = var10000.__call__(var2, var8);
         var1.setlocal(9, var3);
         var3 = null;

         while(true) {
            var1.setline(633);
            if (!var1.getlocal(9).__getattr__("done").__not__().__nonzero__()) {
               var1.setline(638);
               var1.getlocal(0).__getattr__("skip_lines").__call__(var2);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(634);
            var3 = var1.getglobal("rfc822").__getattr__("Message").__call__(var2, var1.getlocal(0).__getattr__("fp"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(635);
            var10000 = var1.getlocal(8);
            var8 = new PyObject[]{var1.getlocal(0).__getattr__("fp"), var1.getlocal(10), var1.getlocal(4), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
            var3 = var10000.__call__(var2, var8);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(637);
            var1.getlocal(0).__getattr__("list").__getattr__("append").__call__(var2, var1.getlocal(9));
         }
      }
   }

   public PyObject read_single$32(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyString.fromInterned("Internal: read an atomic part.");
      var1.setline(642);
      PyObject var3 = var1.getlocal(0).__getattr__("length");
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(643);
         var1.getlocal(0).__getattr__("read_binary").__call__(var2);
         var1.setline(644);
         var1.getlocal(0).__getattr__("skip_lines").__call__(var2);
      } else {
         var1.setline(646);
         var1.getlocal(0).__getattr__("read_lines").__call__(var2);
      }

      var1.setline(647);
      var1.getlocal(0).__getattr__("file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_binary$33(PyFrame var1, ThreadState var2) {
      var1.setline(652);
      PyString.fromInterned("Internal: read binary data.");
      var1.setline(653);
      PyObject var3 = var1.getlocal(0).__getattr__("make_file").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(654);
      var3 = var1.getlocal(0).__getattr__("length");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(655);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         while(true) {
            var1.setline(656);
            var3 = var1.getlocal(1);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(657);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("read").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("bufsize")));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(658);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(659);
               PyInteger var4 = Py.newInteger(-1);
               var1.getlocal(0).__setattr__((String)"done", var4);
               var3 = null;
               break;
            }

            var1.setline(661);
            var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(2));
            var1.setline(662);
            var3 = var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_lines$34(PyFrame var1, ThreadState var2) {
      var1.setline(665);
      PyString.fromInterned("Internal: read lines until EOF or outerboundary.");
      var1.setline(666);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.getlocal(0).__setattr__("file", var3);
      var1.getlocal(0).__setattr__("_FieldStorage__file", var3);
      var1.setline(667);
      if (var1.getlocal(0).__getattr__("outerboundary").__nonzero__()) {
         var1.setline(668);
         var1.getlocal(0).__getattr__("read_lines_to_outerboundary").__call__(var2);
      } else {
         var1.setline(670);
         var1.getlocal(0).__getattr__("read_lines_to_eof").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _FieldStorage__write$35(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyObject var3 = var1.getlocal(0).__getattr__("_FieldStorage__file");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(674);
         var3 = var1.getlocal(0).__getattr__("_FieldStorage__file").__getattr__("tell").__call__(var2)._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var10000 = var3._gt(Py.newInteger(1000));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(675);
            var3 = var1.getlocal(0).__getattr__("make_file").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
            var1.getlocal(0).__setattr__("file", var3);
            var3 = null;
            var1.setline(676);
            var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("_FieldStorage__file").__getattr__("getvalue").__call__(var2));
            var1.setline(677);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_FieldStorage__file", var3);
            var3 = null;
         }
      }

      var1.setline(678);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_lines_to_eof$36(PyFrame var1, ThreadState var2) {
      var1.setline(681);
      PyString.fromInterned("Internal: read lines until EOF.");

      while(true) {
         var1.setline(682);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(683);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, Py.newInteger(1)._lshift(Py.newInteger(16)));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(684);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(685);
            PyInteger var4 = Py.newInteger(-1);
            var1.getlocal(0).__setattr__((String)"done", var4);
            var3 = null;
            break;
         }

         var1.setline(687);
         var1.getlocal(0).__getattr__("_FieldStorage__write").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_lines_to_outerboundary$37(PyFrame var1, ThreadState var2) {
      var1.setline(690);
      PyString.fromInterned("Internal: read lines until outerboundary.");
      var1.setline(691);
      PyObject var3 = PyString.fromInterned("--")._add(var1.getlocal(0).__getattr__("outerboundary"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(692);
      var3 = var1.getlocal(1)._add(PyString.fromInterned("--"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(693);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(694);
      var3 = var1.getglobal("True");
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(695);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(696);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, Py.newInteger(1)._lshift(Py.newInteger(16)));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(697);
         PyInteger var5;
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(698);
            var5 = Py.newInteger(-1);
            var1.getlocal(0).__setattr__((String)"done", var5);
            var3 = null;
            break;
         }

         var1.setline(700);
         var3 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         PyObject var10000 = var3._eq(PyString.fromInterned("--"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(701);
            var3 = var1.getlocal(5).__getattr__("strip").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(702);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(704);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(705);
               var5 = Py.newInteger(1);
               var1.getlocal(0).__setattr__((String)"done", var5);
               var3 = null;
               break;
            }
         }

         var1.setline(707);
         var3 = var1.getlocal(3);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(708);
         var3 = var1.getlocal(5).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("\r\n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(709);
            var4 = PyString.fromInterned("\r\n");
            var1.setlocal(3, var4);
            var3 = null;
            var1.setline(710);
            var3 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(711);
            var3 = var1.getglobal("True");
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(712);
            var3 = var1.getlocal(5).__getitem__(Py.newInteger(-1));
            var10000 = var3._eq(PyString.fromInterned("\n"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(713);
               var4 = PyString.fromInterned("\n");
               var1.setlocal(3, var4);
               var3 = null;
               var1.setline(714);
               var3 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(715);
               var3 = var1.getglobal("True");
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(717);
               var4 = PyString.fromInterned("");
               var1.setlocal(3, var4);
               var3 = null;
               var1.setline(718);
               var3 = var1.getglobal("False");
               var1.setlocal(4, var3);
               var3 = null;
            }
         }

         var1.setline(719);
         var1.getlocal(0).__getattr__("_FieldStorage__write").__call__(var2, var1.getlocal(7)._add(var1.getlocal(5)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject skip_lines$38(PyFrame var1, ThreadState var2) {
      var1.setline(722);
      PyString.fromInterned("Internal: skip lines until outer boundary if defined.");
      var1.setline(723);
      PyObject var10000 = var1.getlocal(0).__getattr__("outerboundary").__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("done");
      }

      if (var10000.__nonzero__()) {
         var1.setline(724);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(725);
         PyObject var3 = PyString.fromInterned("--")._add(var1.getlocal(0).__getattr__("outerboundary"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(726);
         var3 = var1.getlocal(1)._add(PyString.fromInterned("--"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(727);
         var3 = var1.getglobal("True");
         var1.setlocal(3, var3);
         var3 = null;

         while(true) {
            var1.setline(728);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(729);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2, Py.newInteger(1)._lshift(Py.newInteger(16)));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(730);
            PyInteger var4;
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(731);
               var4 = Py.newInteger(-1);
               var1.getlocal(0).__setattr__((String)"done", var4);
               var3 = null;
               break;
            }

            var1.setline(733);
            var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("--"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(3);
            }

            if (var10000.__nonzero__()) {
               var1.setline(734);
               var3 = var1.getlocal(4).__getattr__("strip").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(735);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(var1.getlocal(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(737);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(738);
                  var4 = Py.newInteger(1);
                  var1.getlocal(0).__setattr__((String)"done", var4);
                  var3 = null;
                  break;
               }
            }

            var1.setline(740);
            var3 = var1.getlocal(4).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject make_file$39(PyFrame var1, ThreadState var2) {
      var1.setline(765);
      PyString.fromInterned("Overridable: return a readable & writable file.\n\n        The file will be used as follows:\n        - data is written to it\n        - seek(0)\n        - data is read from it\n\n        The 'binary' argument is unused -- the file is always opened\n        in binary mode.\n\n        This version opens a temporary file for reading and writing,\n        and immediately deletes (unlinks) it.  The trick (on Unix!) is\n        that the file can still be used, but it can't be opened by\n        another process, and it will automatically be deleted when it\n        is closed or when the current process terminates.\n\n        If you want a more permanent file, you derive a class which\n        overrides this method.  If you want a visible temporary file\n        that is nevertheless automatically deleted when the script\n        terminates, try defining a __del__ method in a derived class\n        which unlinks the temporary files you have created.\n\n        ");
      var1.setline(766);
      PyObject var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(767);
      var3 = var1.getlocal(2).__getattr__("TemporaryFile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("w+b"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FormContentDict$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Form content as dictionary with a list of values per field.\n\n    form = FormContentDict()\n\n    form[key] -> [value, value, ...]\n    key in form -> Boolean\n    form.keys() -> [key, key, ...]\n    form.values() -> [[val, val, ...], [val, val, ...], ...]\n    form.items() ->  [(key, [val, val, ...]), (key, [val, val, ...]), ...]\n    form.dict == {key: [val, val, ...], ...}\n\n    "));
      var1.setline(786);
      PyString.fromInterned("Form content as dictionary with a list of values per field.\n\n    form = FormContentDict()\n\n    form[key] -> [value, value, ...]\n    key in form -> Boolean\n    form.keys() -> [key, key, ...]\n    form.values() -> [[val, val, ...], [val, val, ...], ...]\n    form.items() ->  [(key, [val, val, ...]), (key, [val, val, ...]), ...]\n    form.dict == {key: [val, val, ...], ...}\n\n    ");
      var1.setline(787);
      PyObject[] var3 = new PyObject[]{var1.getname("os").__getattr__("environ"), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$41, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyObject var10000 = var1.getglobal("parse");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"environ", "keep_blank_values", "strict_parsing"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.getlocal(0).__setattr__("dict", var5);
      var1.getlocal(0).__setattr__("data", var5);
      var1.setline(791);
      var5 = var1.getlocal(1).__getitem__(PyString.fromInterned("QUERY_STRING"));
      var1.getlocal(0).__setattr__("query_string", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SvFormContentDict$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Form content as dictionary expecting a single value per field.\n\n    If you only expect a single value for each field, then form[key]\n    will return that single value.  It will raise an IndexError if\n    that expectation is not true.  If you expect a field to have\n    possible multiple values, than you can use form.getlist(key) to\n    get all of the values.  values() and items() are a compromise:\n    they return single strings where there is a single value, and\n    lists of strings otherwise.\n\n    "));
      var1.setline(805);
      PyString.fromInterned("Form content as dictionary expecting a single value per field.\n\n    If you only expect a single value for each field, then form[key]\n    will return that single value.  It will raise an IndexError if\n    that expectation is not true.  If you expect a field to have\n    possible multiple values, than you can use form.getlist(key) to\n    get all of the values.  values() and items() are a compromise:\n    they return single strings where there is a single value, and\n    lists of strings otherwise.\n\n    ");
      var1.setline(806);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$43, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(810);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getlist$44, (PyObject)null);
      var1.setlocal("getlist", var4);
      var3 = null;
      var1.setline(812);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$45, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(819);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$46, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$43(PyFrame var1, ThreadState var2) {
      var1.setline(807);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(808);
         throw Py.makeException(var1.getglobal("IndexError"), PyString.fromInterned("expecting a single value"));
      } else {
         var1.setline(809);
         var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getlist$44(PyFrame var1, ThreadState var2) {
      var1.setline(811);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$45(PyFrame var1, ThreadState var2) {
      var1.setline(813);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(814);
      PyObject var6 = var1.getlocal(0).__getattr__("dict").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(814);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(818);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(815);
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(816);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
         } else {
            var1.setline(817);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject items$46(PyFrame var1, ThreadState var2) {
      var1.setline(820);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(821);
      PyObject var7 = var1.getlocal(0).__getattr__("dict").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(821);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(825);
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
         var1.setline(822);
         PyObject var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         PyObject var10000 = var8._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(823);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3).__getitem__(Py.newInteger(0))})));
         } else {
            var1.setline(824);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }
      }
   }

   public PyObject InterpFormContentDict$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class is present for backwards compatibility only."));
      var1.setline(829);
      PyString.fromInterned("This class is present for backwards compatibility only.");
      var1.setline(830);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$48, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(838);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$49, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(846);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$50, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$48(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      PyObject var3 = var1.getglobal("SvFormContentDict").__getattr__("__getitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(832);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._in(PyString.fromInterned("0123456789+-."));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(833);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var7) {
            PyException var4 = Py.setException(var7, var1);
            if (!var4.match(var1.getglobal("ValueError"))) {
               throw var4;
            }
         }

         try {
            var1.setline(835);
            var3 = var1.getglobal("float").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            PyException var5 = Py.setException(var6, var1);
            if (!var5.match(var1.getglobal("ValueError"))) {
               throw var5;
            }
         }

         var1.setline(836);
      }

      var1.setline(837);
      var3 = var1.getlocal(2).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$49(PyFrame var1, ThreadState var2) {
      var1.setline(839);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(840);
      PyObject var7 = var1.getlocal(0).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(840);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(845);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(2, var4);

         try {
            var1.setline(842);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2)));
         } catch (Throwable var6) {
            PyException var5 = Py.setException(var6, var1);
            if (!var5.match(var1.getglobal("IndexError"))) {
               throw var5;
            }

            var1.setline(844);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(2)));
         }
      }
   }

   public PyObject items$50(PyFrame var1, ThreadState var2) {
      var1.setline(847);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(848);
      PyObject var7 = var1.getlocal(0).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(848);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(853);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(2, var4);

         try {
            var1.setline(850);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getitem__(var1.getlocal(2))})));
         } catch (Throwable var6) {
            PyException var5 = Py.setException(var6, var1);
            if (!var5.match(var1.getglobal("IndexError"))) {
               throw var5;
            }

            var1.setline(852);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(2))})));
         }
      }
   }

   public PyObject FormContent$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class is present for backwards compatibility only."));
      var1.setline(857);
      PyString.fromInterned("This class is present for backwards compatibility only.");
      var1.setline(858);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, values$52, (PyObject)null);
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(861);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, indexed_value$53, (PyObject)null);
      var1.setlocal("indexed_value", var4);
      var3 = null;
      var1.setline(867);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value$54, (PyObject)null);
      var1.setlocal("value", var4);
      var3 = null;
      var1.setline(870);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, length$55, (PyObject)null);
      var1.setlocal("length", var4);
      var3 = null;
      var1.setline(872);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stripped$56, (PyObject)null);
      var1.setlocal("stripped", var4);
      var3 = null;
      var1.setline(875);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pars$57, (PyObject)null);
      var1.setlocal("pars", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject values$52(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(859);
         var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(860);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject indexed_value$53(PyFrame var1, ThreadState var2) {
      var1.setline(862);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(863);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)));
         var10000 = var3._gt(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(864);
            var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)).__getitem__(var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(865);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(866);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject value$54(PyFrame var1, ThreadState var2) {
      var1.setline(868);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(868);
         var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(869);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject length$55(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject stripped$56(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(873);
         var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(874);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject pars$57(PyFrame var1, ThreadState var2) {
      var1.setline(876);
      PyObject var3 = var1.getlocal(0).__getattr__("dict");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$58(PyFrame var1, ThreadState var2) {
      var1.setline(888);
      PyString.fromInterned("Robust test CGI script, usable as main program.\n\n    Write minimal HTTP headers and dump all information provided to\n    the script in HTML form.\n\n    ");
      var1.setline(889);
      Py.println(PyString.fromInterned("Content-type: text/html"));
      var1.setline(890);
      Py.println();
      var1.setline(891);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.getglobal("sys").__setattr__("stderr", var3);
      var3 = null;

      try {
         var1.setline(893);
         var3 = var1.getglobal("FieldStorage").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(894);
         var1.getglobal("print_directory").__call__(var2);
         var1.setline(895);
         var1.getglobal("print_arguments").__call__(var2);
         var1.setline(896);
         var1.getglobal("print_form").__call__(var2, var1.getlocal(1));
         var1.setline(897);
         var1.getglobal("print_environ").__call__(var2, var1.getlocal(0));
         var1.setline(898);
         var1.getglobal("print_environ_usage").__call__(var2);
         var1.setline(899);
         PyObject[] var6 = Py.EmptyObjects;
         PyFunction var7 = new PyFunction(var1.f_globals, var6, f$59, (PyObject)null);
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(901);
         var6 = new PyObject[]{var1.getlocal(2)};
         var7 = new PyFunction(var1.f_globals, var6, g$60, (PyObject)null);
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(903);
         Py.println(PyString.fromInterned("<H3>What follows is a test, not an actual exception:</H3>"));
         var1.setline(904);
         var1.getlocal(3).__call__(var2);
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(906);
         var1.getglobal("print_exception").__call__(var2);
      }

      var1.setline(908);
      Py.println(PyString.fromInterned("<H1>Second try with a small maxlen...</H1>"));
      var1.setline(911);
      PyInteger var8 = Py.newInteger(50);
      var1.setglobal("maxlen", var8);
      var3 = null;

      try {
         var1.setline(913);
         var3 = var1.getglobal("FieldStorage").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(914);
         var1.getglobal("print_directory").__call__(var2);
         var1.setline(915);
         var1.getglobal("print_arguments").__call__(var2);
         var1.setline(916);
         var1.getglobal("print_form").__call__(var2, var1.getlocal(1));
         var1.setline(917);
         var1.getglobal("print_environ").__call__(var2, var1.getlocal(0));
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(919);
         var1.getglobal("print_exception").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$59(PyFrame var1, ThreadState var2) {
      var1.setline(900);
      Py.exec(PyString.fromInterned("testing print_exception() -- <I>italics?</I>"), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject g$60(PyFrame var1, ThreadState var2) {
      var1.setline(902);
      var1.getlocal(0).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_exception$61(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(923);
         var3 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(924);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(925);
      Py.println();
      var1.setline(926);
      Py.println(PyString.fromInterned("<H3>Traceback (most recent call last):</H3>"));
      var1.setline(927);
      var3 = var1.getlocal(4).__getattr__("format_tb").__call__(var2, var1.getlocal(2), var1.getlocal(3))._add(var1.getlocal(4).__getattr__("format_exception_only").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(929);
      Py.println(PyString.fromInterned("<PRE>%s<B>%s</B></PRE>")._mod(new PyTuple(new PyObject[]{var1.getglobal("escape").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null))), var1.getglobal("escape").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(-1)))})));
      var1.setline(933);
      var1.dellocal(2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_environ$62(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      PyString.fromInterned("Dump the shell environment as HTML.");
      var1.setline(937);
      PyObject var3 = var1.getlocal(0).__getattr__("keys").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(938);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(939);
      Py.println();
      var1.setline(940);
      Py.println(PyString.fromInterned("<H3>Shell Environment:</H3>"));
      var1.setline(941);
      Py.println(PyString.fromInterned("<DL>"));
      var1.setline(942);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(942);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(944);
            Py.println(PyString.fromInterned("</DL>"));
            var1.setline(945);
            Py.println();
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(943);
         Py.printComma(PyString.fromInterned("<DT>"));
         Py.printComma(var1.getglobal("escape").__call__(var2, var1.getlocal(2)));
         Py.printComma(PyString.fromInterned("<DD>"));
         Py.println(var1.getglobal("escape").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2))));
      }
   }

   public PyObject print_form$63(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      PyString.fromInterned("Dump the contents of a form as HTML.");
      var1.setline(949);
      PyObject var3 = var1.getlocal(0).__getattr__("keys").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(950);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(951);
      Py.println();
      var1.setline(952);
      Py.println(PyString.fromInterned("<H3>Form Contents:</H3>"));
      var1.setline(953);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(954);
         Py.println(PyString.fromInterned("<P>No form fields."));
      }

      var1.setline(955);
      Py.println(PyString.fromInterned("<DL>"));
      var1.setline(956);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(956);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(961);
            Py.println(PyString.fromInterned("</DL>"));
            var1.setline(962);
            Py.println();
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(957);
         Py.printComma(PyString.fromInterned("<DT>")._add(var1.getglobal("escape").__call__(var2, var1.getlocal(2)))._add(PyString.fromInterned(":")));
         var1.setline(958);
         PyObject var5 = var1.getlocal(0).__getitem__(var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(959);
         Py.println(PyString.fromInterned("<i>")._add(var1.getglobal("escape").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3)))))._add(PyString.fromInterned("</i>")));
         var1.setline(960);
         Py.println(PyString.fromInterned("<DD>")._add(var1.getglobal("escape").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(3)))));
      }
   }

   public PyObject print_directory$64(PyFrame var1, ThreadState var2) {
      var1.setline(965);
      PyString.fromInterned("Dump the current directory as HTML.");
      var1.setline(966);
      Py.println();
      var1.setline(967);
      Py.println(PyString.fromInterned("<H3>Current Working Directory:</H3>"));

      label19: {
         PyException var3;
         try {
            var1.setline(969);
            PyObject var6 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
            var1.setlocal(0, var6);
            var3 = null;
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("os").__getattr__("error"))) {
               PyObject var4 = var3.value;
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(971);
               Py.printComma(PyString.fromInterned("os.error:"));
               Py.println(var1.getglobal("escape").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1))));
               break label19;
            }

            throw var3;
         }

         var1.setline(973);
         Py.println(var1.getglobal("escape").__call__(var2, var1.getlocal(0)));
      }

      var1.setline(974);
      Py.println();
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_arguments$65(PyFrame var1, ThreadState var2) {
      var1.setline(977);
      Py.println();
      var1.setline(978);
      Py.println(PyString.fromInterned("<H3>Command Line Arguments:</H3>"));
      var1.setline(979);
      Py.println();
      var1.setline(980);
      Py.println(var1.getglobal("sys").__getattr__("argv"));
      var1.setline(981);
      Py.println();
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_environ_usage$66(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      PyString.fromInterned("Dump a list of environment variables used by CGI as HTML.");
      var1.setline(985);
      Py.println(PyString.fromInterned("\n<H3>These environment variables could have been set:</H3>\n<UL>\n<LI>AUTH_TYPE\n<LI>CONTENT_LENGTH\n<LI>CONTENT_TYPE\n<LI>DATE_GMT\n<LI>DATE_LOCAL\n<LI>DOCUMENT_NAME\n<LI>DOCUMENT_ROOT\n<LI>DOCUMENT_URI\n<LI>GATEWAY_INTERFACE\n<LI>LAST_MODIFIED\n<LI>PATH\n<LI>PATH_INFO\n<LI>PATH_TRANSLATED\n<LI>QUERY_STRING\n<LI>REMOTE_ADDR\n<LI>REMOTE_HOST\n<LI>REMOTE_IDENT\n<LI>REMOTE_USER\n<LI>REQUEST_METHOD\n<LI>SCRIPT_NAME\n<LI>SERVER_NAME\n<LI>SERVER_PORT\n<LI>SERVER_PROTOCOL\n<LI>SERVER_ROOT\n<LI>SERVER_SOFTWARE\n</UL>\nIn addition, HTTP headers sent by the server may be passed in the\nenvironment as well.  Here are some common variable names:\n<UL>\n<LI>HTTP_ACCEPT\n<LI>HTTP_CONNECTION\n<LI>HTTP_HOST\n<LI>HTTP_PRAGMA\n<LI>HTTP_REFERER\n<LI>HTTP_USER_AGENT\n</UL>\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject escape$67(PyFrame var1, ThreadState var2) {
      var1.setline(1033);
      PyString.fromInterned("Replace special characters \"&\", \"<\" and \">\" to HTML-safe sequences.\n    If the optional flag quote is true, the quotation mark character (\")\n    is also translated.");
      var1.setline(1034);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1035);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1036);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1037);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1038);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1039);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject valid_boundary$68(PyFrame var1, ThreadState var2) {
      var1.setline(1042);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1043);
      var3 = var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public cgi$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"allargs"};
      initlog$1 = Py.newCode(1, var2, var1, "initlog", 71, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fmt", "args"};
      dolog$2 = Py.newCode(2, var2, var1, "dolog", 106, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"allargs"};
      nolog$3 = Py.newCode(1, var2, var1, "nolog", 110, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "environ", "keep_blank_values", "strict_parsing", "ctype", "pdict", "clength", "qs"};
      parse$4 = Py.newCode(4, var2, var1, "parse", 124, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"qs", "keep_blank_values", "strict_parsing"};
      parse_qs$5 = Py.newCode(3, var2, var1, "parse_qs", 180, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"qs", "keep_blank_values", "strict_parsing"};
      parse_qsl$6 = Py.newCode(3, var2, var1, "parse_qsl", 187, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "pdict", "boundary", "nextpart", "lastpart", "partdict", "terminator", "bytes", "data", "headers", "clength", "lines", "line", "key", "params", "name"};
      parse_multipart$7 = Py.newCode(2, var2, var1, "parse_multipart", 193, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "end", "f"};
      _parseparam$8 = Py.newCode(1, var2, var1, "_parseparam", 291, false, false, self, 8, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"line", "parts", "key", "pdict", "p", "i", "name", "value"};
      parse_header$9 = Py.newCode(1, var2, var1, "parse_header", 303, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MiniFieldStorage$10 = Py.newCode(0, var2, var1, "MiniFieldStorage", 327, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "value"};
      __init__$11 = Py.newCode(3, var2, var1, "__init__", 341, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$12 = Py.newCode(1, var2, var1, "__repr__", 347, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FieldStorage$13 = Py.newCode(0, var2, var1, "FieldStorage", 352, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "headers", "outerboundary", "environ", "keep_blank_values", "strict_parsing", "method", "qs", "cdisp", "pdict", "ctype", "clen"};
      __init__$14 = Py.newCode(7, var2, var1, "__init__", 395, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$15 = Py.newCode(1, var2, var1, "__repr__", 511, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$16 = Py.newCode(1, var2, var1, "__iter__", 516, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      __getattr__$17 = Py.newCode(2, var2, var1, "__getattr__", 519, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "found", "item"};
      __getitem__$18 = Py.newCode(2, var2, var1, "__getitem__", 532, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default", "value"};
      getvalue$19 = Py.newCode(3, var2, var1, "getvalue", 546, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default", "value"};
      getfirst$20 = Py.newCode(3, var2, var1, "getfirst", 557, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      getlist$21 = Py.newCode(2, var2, var1, "getlist", 568, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_(583_24)"};
      keys$22 = Py.newCode(1, var2, var1, "keys", 579, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "item"};
      f$23 = Py.newCode(1, var2, var1, "<genexpr>", 583, false, false, self, 23, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key", "_(589_19)"};
      String[] var10001 = var2;
      cgi$py var10007 = self;
      var2 = new String[]{"key"};
      has_key$24 = Py.newCode(2, var10001, var1, "has_key", 585, false, false, var10007, 24, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "item"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"key"};
      f$25 = Py.newCode(1, var10001, var1, "<genexpr>", 589, false, false, var10007, 25, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "key", "_(595_19)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"key"};
      __contains__$26 = Py.newCode(2, var10001, var1, "__contains__", 591, false, false, var10007, 26, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "item"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"key"};
      f$27 = Py.newCode(1, var10001, var1, "<genexpr>", 595, false, false, var10007, 27, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self"};
      __len__$28 = Py.newCode(1, var2, var1, "__len__", 597, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __nonzero__$29 = Py.newCode(1, var2, var1, "__nonzero__", 601, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "qs", "list", "key", "value"};
      read_urlencoded$30 = Py.newCode(1, var2, var1, "read_urlencoded", 604, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "environ", "keep_blank_values", "strict_parsing", "ib", "key", "value", "FieldStorageClass", "klass", "part", "headers"};
      read_multi$31 = Py.newCode(4, var2, var1, "read_multi", 617, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read_single$32 = Py.newCode(1, var2, var1, "read_single", 640, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "todo", "data"};
      read_binary$33 = Py.newCode(1, var2, var1, "read_binary", 651, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read_lines$34 = Py.newCode(1, var2, var1, "read_lines", 664, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _FieldStorage__write$35 = Py.newCode(2, var2, var1, "_FieldStorage__write", 672, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      read_lines_to_eof$36 = Py.newCode(1, var2, var1, "read_lines_to_eof", 680, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "next", "last", "delim", "last_line_lfend", "line", "strippedline", "odelim"};
      read_lines_to_outerboundary$37 = Py.newCode(1, var2, var1, "read_lines_to_outerboundary", 689, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "next", "last", "last_line_lfend", "line", "strippedline"};
      skip_lines$38 = Py.newCode(1, var2, var1, "skip_lines", 721, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "binary", "tempfile"};
      make_file$39 = Py.newCode(2, var2, var1, "make_file", 742, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FormContentDict$40 = Py.newCode(0, var2, var1, "FormContentDict", 774, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "environ", "keep_blank_values", "strict_parsing"};
      __init__$41 = Py.newCode(4, var2, var1, "__init__", 787, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SvFormContentDict$42 = Py.newCode(0, var2, var1, "SvFormContentDict", 794, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key"};
      __getitem__$43 = Py.newCode(2, var2, var1, "__getitem__", 806, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      getlist$44 = Py.newCode(2, var2, var1, "getlist", 810, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "value"};
      values$45 = Py.newCode(1, var2, var1, "values", 812, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "key", "value"};
      items$46 = Py.newCode(1, var2, var1, "items", 819, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InterpFormContentDict$47 = Py.newCode(0, var2, var1, "InterpFormContentDict", 828, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "v"};
      __getitem__$48 = Py.newCode(2, var2, var1, "__getitem__", 830, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "key"};
      values$49 = Py.newCode(1, var2, var1, "values", 838, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "key"};
      items$50 = Py.newCode(1, var2, var1, "items", 846, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FormContent$51 = Py.newCode(0, var2, var1, "FormContent", 856, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key"};
      values$52 = Py.newCode(2, var2, var1, "values", 858, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "location"};
      indexed_value$53 = Py.newCode(3, var2, var1, "indexed_value", 861, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      value$54 = Py.newCode(2, var2, var1, "value", 867, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      length$55 = Py.newCode(2, var2, var1, "length", 870, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      stripped$56 = Py.newCode(2, var2, var1, "stripped", 872, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pars$57 = Py.newCode(1, var2, var1, "pars", 875, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "form", "f", "g"};
      test$58 = Py.newCode(1, var2, var1, "test", 882, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$59 = Py.newCode(0, var2, var1, "f", 899, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"f"};
      g$60 = Py.newCode(1, var2, var1, "g", 901, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"type", "value", "tb", "limit", "traceback", "list"};
      print_exception$61 = Py.newCode(4, var2, var1, "print_exception", 921, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "keys", "key"};
      print_environ$62 = Py.newCode(1, var2, var1, "print_environ", 935, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"form", "keys", "key", "value"};
      print_form$63 = Py.newCode(1, var2, var1, "print_form", 947, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pwd", "msg"};
      print_directory$64 = Py.newCode(0, var2, var1, "print_directory", 964, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      print_arguments$65 = Py.newCode(0, var2, var1, "print_arguments", 976, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      print_environ_usage$66 = Py.newCode(0, var2, var1, "print_environ_usage", 983, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "quote"};
      escape$67 = Py.newCode(2, var2, var1, "escape", 1030, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "_vb_pattern", "re"};
      valid_boundary$68 = Py.newCode(2, var2, var1, "valid_boundary", 1041, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cgi$py("cgi$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cgi$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.initlog$1(var2, var3);
         case 2:
            return this.dolog$2(var2, var3);
         case 3:
            return this.nolog$3(var2, var3);
         case 4:
            return this.parse$4(var2, var3);
         case 5:
            return this.parse_qs$5(var2, var3);
         case 6:
            return this.parse_qsl$6(var2, var3);
         case 7:
            return this.parse_multipart$7(var2, var3);
         case 8:
            return this._parseparam$8(var2, var3);
         case 9:
            return this.parse_header$9(var2, var3);
         case 10:
            return this.MiniFieldStorage$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.__repr__$12(var2, var3);
         case 13:
            return this.FieldStorage$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.__repr__$15(var2, var3);
         case 16:
            return this.__iter__$16(var2, var3);
         case 17:
            return this.__getattr__$17(var2, var3);
         case 18:
            return this.__getitem__$18(var2, var3);
         case 19:
            return this.getvalue$19(var2, var3);
         case 20:
            return this.getfirst$20(var2, var3);
         case 21:
            return this.getlist$21(var2, var3);
         case 22:
            return this.keys$22(var2, var3);
         case 23:
            return this.f$23(var2, var3);
         case 24:
            return this.has_key$24(var2, var3);
         case 25:
            return this.f$25(var2, var3);
         case 26:
            return this.__contains__$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.__len__$28(var2, var3);
         case 29:
            return this.__nonzero__$29(var2, var3);
         case 30:
            return this.read_urlencoded$30(var2, var3);
         case 31:
            return this.read_multi$31(var2, var3);
         case 32:
            return this.read_single$32(var2, var3);
         case 33:
            return this.read_binary$33(var2, var3);
         case 34:
            return this.read_lines$34(var2, var3);
         case 35:
            return this._FieldStorage__write$35(var2, var3);
         case 36:
            return this.read_lines_to_eof$36(var2, var3);
         case 37:
            return this.read_lines_to_outerboundary$37(var2, var3);
         case 38:
            return this.skip_lines$38(var2, var3);
         case 39:
            return this.make_file$39(var2, var3);
         case 40:
            return this.FormContentDict$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this.SvFormContentDict$42(var2, var3);
         case 43:
            return this.__getitem__$43(var2, var3);
         case 44:
            return this.getlist$44(var2, var3);
         case 45:
            return this.values$45(var2, var3);
         case 46:
            return this.items$46(var2, var3);
         case 47:
            return this.InterpFormContentDict$47(var2, var3);
         case 48:
            return this.__getitem__$48(var2, var3);
         case 49:
            return this.values$49(var2, var3);
         case 50:
            return this.items$50(var2, var3);
         case 51:
            return this.FormContent$51(var2, var3);
         case 52:
            return this.values$52(var2, var3);
         case 53:
            return this.indexed_value$53(var2, var3);
         case 54:
            return this.value$54(var2, var3);
         case 55:
            return this.length$55(var2, var3);
         case 56:
            return this.stripped$56(var2, var3);
         case 57:
            return this.pars$57(var2, var3);
         case 58:
            return this.test$58(var2, var3);
         case 59:
            return this.f$59(var2, var3);
         case 60:
            return this.g$60(var2, var3);
         case 61:
            return this.print_exception$61(var2, var3);
         case 62:
            return this.print_environ$62(var2, var3);
         case 63:
            return this.print_form$63(var2, var3);
         case 64:
            return this.print_directory$64(var2, var3);
         case 65:
            return this.print_arguments$65(var2, var3);
         case 66:
            return this.print_environ_usage$66(var2, var3);
         case 67:
            return this.escape$67(var2, var3);
         case 68:
            return this.valid_boundary$68(var2, var3);
         default:
            return null;
      }
   }
}
