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
@Filename("traceback.py")
public class traceback$py extends PyFunctionTable implements PyRunnable {
   static traceback$py self;
   static final PyCode f$0;
   static final PyCode _print$1;
   static final PyCode print_list$2;
   static final PyCode format_list$3;
   static final PyCode print_tb$4;
   static final PyCode format_tb$5;
   static final PyCode extract_tb$6;
   static final PyCode print_exception$7;
   static final PyCode format_exception$8;
   static final PyCode format_exception_only$9;
   static final PyCode f$10;
   static final PyCode _format_final_exc_line$11;
   static final PyCode _some_str$12;
   static final PyCode print_exc$13;
   static final PyCode format_exc$14;
   static final PyCode print_last$15;
   static final PyCode print_stack$16;
   static final PyCode format_stack$17;
   static final PyCode extract_stack$18;
   static final PyCode tb_lineno$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Extract, format and print information about Python stack traces."));
      var1.setline(1);
      PyString.fromInterned("Extract, format and print information about Python stack traces.");
      var1.setline(3);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(7);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("extract_stack"), PyString.fromInterned("extract_tb"), PyString.fromInterned("format_exception"), PyString.fromInterned("format_exception_only"), PyString.fromInterned("format_list"), PyString.fromInterned("format_stack"), PyString.fromInterned("format_tb"), PyString.fromInterned("print_exc"), PyString.fromInterned("format_exc"), PyString.fromInterned("print_exception"), PyString.fromInterned("print_last"), PyString.fromInterned("print_stack"), PyString.fromInterned("print_tb"), PyString.fromInterned("tb_lineno")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(12);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("\n")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, _print$1, (PyObject)null);
      var1.setlocal("_print", var6);
      var3 = null;
      var1.setline(16);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, print_list$2, PyString.fromInterned("Print the list of tuples as returned by extract_tb() or\n    extract_stack() as a formatted stack trace to the given file."));
      var1.setlocal("print_list", var6);
      var3 = null;
      var1.setline(27);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, format_list$3, PyString.fromInterned("Format a list of traceback entry tuples for printing.\n\n    Given a list of tuples as returned by extract_tb() or\n    extract_stack(), return a list of strings ready for printing.\n    Each string in the resulting list corresponds to the item with the\n    same index in the argument list.  Each string ends in a newline;\n    the strings may contain internal newlines as well, for those items\n    whose source text line is not None.\n    "));
      var1.setlocal("format_list", var6);
      var3 = null;
      var1.setline(46);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, print_tb$4, PyString.fromInterned("Print up to 'limit' stack trace entries from the traceback 'tb'.\n\n    If 'limit' is omitted or None, all entries are printed.  If 'file'\n    is omitted or None, the output goes to sys.stderr; otherwise\n    'file' should be an open file or file-like object with a write()\n    method.\n    "));
      var1.setlocal("print_tb", var6);
      var3 = null;
      var1.setline(74);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, format_tb$5, PyString.fromInterned("A shorthand for 'format_list(extract_stack(f, limit))."));
      var1.setlocal("format_tb", var6);
      var3 = null;
      var1.setline(78);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, extract_tb$6, PyString.fromInterned("Return list of up to limit pre-processed entries from traceback.\n\n    This is useful for alternate formatting of stack traces.  If\n    'limit' is omitted or None, all entries are extracted.  A\n    pre-processed stack trace entry is a quadruple (filename, line\n    number, function name, text) representing the information that is\n    usually printed for a stack trace.  The text is a string with\n    leading and trailing whitespace stripped; if the source is not\n    available it is None.\n    "));
      var1.setlocal("extract_tb", var6);
      var3 = null;
      var1.setline(110);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, print_exception$7, PyString.fromInterned("Print exception up to 'limit' stack trace entries from 'tb' to 'file'.\n\n    This differs from print_tb() in the following ways: (1) if\n    traceback is not None, it prints a header \"Traceback (most recent\n    call last):\"; (2) it prints the exception type and value after the\n    stack trace; (3) if type is SyntaxError and value has the\n    appropriate format, it prints the line where the syntax error\n    occurred with a caret on the next line indicating the approximate\n    position of the error.\n    "));
      var1.setlocal("print_exception", var6);
      var3 = null;
      var1.setline(130);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, format_exception$8, PyString.fromInterned("Format a stack trace and the exception information.\n\n    The arguments have the same meaning as the corresponding arguments\n    to print_exception().  The return value is a list of strings, each\n    ending in a newline and some containing internal newlines.  When\n    these lines are concatenated and printed, exactly the same text is\n    printed as does print_exception().\n    "));
      var1.setlocal("format_exception", var6);
      var3 = null;
      var1.setline(147);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, format_exception_only$9, PyString.fromInterned("Format the exception part of a traceback.\n\n    The arguments are the exception type and value such as given by\n    sys.last_type and sys.last_value. The return value is a list of\n    strings, each ending in a newline.\n\n    Normally, the list contains a single string; however, for\n    SyntaxError exceptions, it contains several lines that (when\n    printed) display detailed information about where the syntax\n    error occurred.\n\n    The message indicating which exception occurred is always the last\n    string in the list.\n\n    "));
      var1.setlocal("format_exception_only", var6);
      var3 = null;
      var1.setline(202);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _format_final_exc_line$11, PyString.fromInterned("Return a list of a single line -- normal case for format_exception_only"));
      var1.setlocal("_format_final_exc_line", var6);
      var3 = null;
      var1.setline(211);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _some_str$12, (PyObject)null);
      var1.setlocal("_some_str", var6);
      var3 = null;
      var1.setline(224);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, print_exc$13, PyString.fromInterned("Shorthand for 'print_exception(sys.exc_type, sys.exc_value, sys.exc_traceback, limit, file)'.\n    (In fact, it uses sys.exc_info() to retrieve the same information\n    in a thread-safe way.)"));
      var1.setlocal("print_exc", var6);
      var3 = null;
      var1.setline(237);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, format_exc$14, PyString.fromInterned("Like print_exc() but return a string."));
      var1.setlocal("format_exc", var6);
      var3 = null;
      var1.setline(246);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, print_last$15, PyString.fromInterned("This is a shorthand for 'print_exception(sys.last_type,\n    sys.last_value, sys.last_traceback, limit, file)'."));
      var1.setlocal("print_last", var6);
      var3 = null;
      var1.setline(257);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, print_stack$16, PyString.fromInterned("Print a stack trace from its invocation point.\n\n    The optional 'f' argument can be used to specify an alternate\n    stack frame at which to start. The optional 'limit' and 'file'\n    arguments have the same meaning as for print_exception().\n    "));
      var1.setlocal("print_stack", var6);
      var3 = null;
      var1.setline(271);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, format_stack$17, PyString.fromInterned("Shorthand for 'format_list(extract_stack(f, limit))'."));
      var1.setlocal("format_stack", var6);
      var3 = null;
      var1.setline(280);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, extract_stack$18, PyString.fromInterned("Extract the raw traceback from the current stack frame.\n\n    The return value has the same format as for extract_tb().  The\n    optional 'f' and 'limit' arguments have the same meaning as for\n    print_stack().  Each item in the list is a quadruple (filename,\n    line number, function name, text), and the entries are in order\n    from oldest to newest stack frame.\n    "));
      var1.setlocal("extract_stack", var6);
      var3 = null;
      var1.setline(314);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, tb_lineno$19, PyString.fromInterned("Calculate correct line number of traceback given in tb.\n\n    Obsolete in 2.3.\n    "));
      var1.setlocal("tb_lineno", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _print$1(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1)._add(var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_list$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyString.fromInterned("Print the list of tuples as returned by extract_tb() or\n    extract_stack() as a formatted stack trace to the given file.");
      var1.setline(19);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(21);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(21);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 4);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(22);
         var1.getglobal("_print").__call__(var2, var1.getlocal(1), PyString.fromInterned("  File \"%s\", line %d, in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})));
         var1.setline(24);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(25);
            var1.getglobal("_print").__call__(var2, var1.getlocal(1), PyString.fromInterned("    %s")._mod(var1.getlocal(5).__getattr__("strip").__call__(var2)));
         }
      }
   }

   public PyObject format_list$3(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Format a list of traceback entry tuples for printing.\n\n    Given a list of tuples as returned by extract_tb() or\n    extract_stack(), return a list of strings ready for printing.\n    Each string in the resulting list corresponds to the item with the\n    same index in the argument list.  Each string ends in a newline;\n    the strings may contain internal newlines as well, for those items\n    whose source text line is not None.\n    ");
      var1.setline(37);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(38);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(43);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 4);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(39);
         PyObject var8 = PyString.fromInterned("  File \"%s\", line %d, in %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)}));
         var1.setlocal(6, var8);
         var5 = null;
         var1.setline(40);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(41);
            var8 = var1.getlocal(6)._add(PyString.fromInterned("    %s\n")._mod(var1.getlocal(5).__getattr__("strip").__call__(var2)));
            var1.setlocal(6, var8);
            var5 = null;
         }

         var1.setline(42);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(6));
      }
   }

   public PyObject print_tb$4(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString.fromInterned("Print up to 'limit' stack trace entries from the traceback 'tb'.\n\n    If 'limit' is omitted or None, all entries are printed.  If 'file'\n    is omitted or None, the output goes to sys.stderr; otherwise\n    'file' should be an open file or file-like object with a write()\n    method.\n    ");
      var1.setline(54);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(55);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(56);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(57);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("tracebacklimit")).__nonzero__()) {
            var1.setline(58);
            var3 = var1.getglobal("sys").__getattr__("tracebacklimit");
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(59);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(3, var4);
      var3 = null;

      while(true) {
         var1.setline(60);
         var3 = var1.getlocal(0);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(3);
               var10000 = var3._lt(var1.getlocal(1));
               var3 = null;
            }
         }

         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(61);
         var3 = var1.getlocal(0).__getattr__("tb_frame");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(62);
         var3 = var1.getlocal(0).__getattr__("tb_lineno");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(63);
         var3 = var1.getlocal(4).__getattr__("f_code");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(64);
         var3 = var1.getlocal(6).__getattr__("co_filename");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(65);
         var3 = var1.getlocal(6).__getattr__("co_name");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(66);
         var1.getglobal("_print").__call__(var2, var1.getlocal(2), PyString.fromInterned("  File \"%s\", line %d, in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5), var1.getlocal(8)})));
         var1.setline(68);
         var1.getglobal("linecache").__getattr__("checkcache").__call__(var2, var1.getlocal(7));
         var1.setline(69);
         var3 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(7), var1.getlocal(5), var1.getlocal(4).__getattr__("f_globals"));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(70);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(70);
            var1.getglobal("_print").__call__(var2, var1.getlocal(2), PyString.fromInterned("    ")._add(var1.getlocal(9).__getattr__("strip").__call__(var2)));
         }

         var1.setline(71);
         var3 = var1.getlocal(0).__getattr__("tb_next");
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(72);
         var3 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject format_tb$5(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyString.fromInterned("A shorthand for 'format_list(extract_stack(f, limit)).");
      var1.setline(76);
      PyObject var3 = var1.getglobal("format_list").__call__(var2, var1.getglobal("extract_tb").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject extract_tb$6(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned("Return list of up to limit pre-processed entries from traceback.\n\n    This is useful for alternate formatting of stack traces.  If\n    'limit' is omitted or None, all entries are extracted.  A\n    pre-processed stack trace entry is a quadruple (filename, line\n    number, function name, text) representing the information that is\n    usually printed for a stack trace.  The text is a string with\n    leading and trailing whitespace stripped; if the source is not\n    available it is None.\n    ");
      var1.setline(89);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(90);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("tracebacklimit")).__nonzero__()) {
            var1.setline(91);
            var3 = var1.getglobal("sys").__getattr__("tracebacklimit");
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(92);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(93);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(3, var5);
      var3 = null;

      while(true) {
         var1.setline(94);
         var3 = var1.getlocal(0);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(3);
               var10000 = var3._lt(var1.getlocal(1));
               var3 = null;
            }
         }

         if (!var10000.__nonzero__()) {
            var1.setline(107);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(95);
         var3 = var1.getlocal(0).__getattr__("tb_frame");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(96);
         var3 = var1.getlocal(0).__getattr__("tb_lineno");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(97);
         var3 = var1.getlocal(4).__getattr__("f_code");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(98);
         var3 = var1.getlocal(6).__getattr__("co_filename");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(99);
         var3 = var1.getlocal(6).__getattr__("co_name");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(100);
         var1.getglobal("linecache").__getattr__("checkcache").__call__(var2, var1.getlocal(7));
         var1.setline(101);
         var3 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(7), var1.getlocal(5), var1.getlocal(4).__getattr__("f_globals"));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(102);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(102);
            var3 = var1.getlocal(9).__getattr__("strip").__call__(var2);
            var1.setlocal(9, var3);
            var3 = null;
         } else {
            var1.setline(103);
            var3 = var1.getglobal("None");
            var1.setlocal(9, var3);
            var3 = null;
         }

         var1.setline(104);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5), var1.getlocal(8), var1.getlocal(9)})));
         var1.setline(105);
         var3 = var1.getlocal(0).__getattr__("tb_next");
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(106);
         var3 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject print_exception$7(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("Print exception up to 'limit' stack trace entries from 'tb' to 'file'.\n\n    This differs from print_tb() in the following ways: (1) if\n    traceback is not None, it prints a header \"Traceback (most recent\n    call last):\"; (2) it prints the exception type and value after the\n    stack trace; (3) if type is SyntaxError and value has the\n    appropriate format, it prints the line where the syntax error\n    occurred with a caret on the next line indicating the approximate\n    position of the error.\n    ");
      var1.setline(121);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(122);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(123);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(124);
         var1.getglobal("_print").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("Traceback (most recent call last):"));
         var1.setline(125);
         var1.getglobal("print_tb").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      }

      var1.setline(126);
      var3 = var1.getglobal("format_exception_only").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(127);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(128);
         var1.getglobal("_print").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned(""));
      }
   }

   public PyObject format_exception$8(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Format a stack trace and the exception information.\n\n    The arguments have the same meaning as the corresponding arguments\n    to print_exception().  The return value is a list of strings, each\n    ending in a newline and some containing internal newlines.  When\n    these lines are concatenated and printed, exactly the same text is\n    printed as does print_exception().\n    ");
      var1.setline(139);
      PyList var3;
      PyObject var4;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(140);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("Traceback (most recent call last):\n")});
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(141);
         var4 = var1.getlocal(4)._add(var1.getglobal("format_tb").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
         var1.setlocal(4, var4);
         var3 = null;
      } else {
         var1.setline(143);
         var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(144);
      var4 = var1.getlocal(4)._add(var1.getglobal("format_exception_only").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(145);
      var4 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject format_exception_only$9(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyString.fromInterned("Format the exception part of a traceback.\n\n    The arguments are the exception type and value such as given by\n    sys.last_type and sys.last_value. The return value is a list of\n    strings, each ending in a newline.\n\n    Normally, the list contains a single string; however, for\n    SyntaxError exceptions, it contains several lines that (when\n    printed) display detailed information about where the syntax\n    error occurred.\n\n    The message indicating which exception occurred is always the last\n    string in the list.\n\n    ");
      var1.setline(170);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("BaseException"));
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("InstanceType"));
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
               var10000 = var3._is(var1.getglobal("str"));
               var3 = null;
            }
         }
      }

      PyList var10;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         var10 = new PyList(new PyObject[]{var1.getglobal("_format_final_exc_line").__call__(var2, var1.getlocal(0), var1.getlocal(1))});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(175);
         PyObject var4 = var1.getlocal(0).__getattr__("__name__");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(177);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(0), var1.getglobal("SyntaxError")).__not__().__nonzero__()) {
            var1.setline(178);
            var10 = new PyList(new PyObject[]{var1.getglobal("_format_final_exc_line").__call__(var2, var1.getlocal(2), var1.getlocal(1))});
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(181);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var11);
            var4 = null;

            label50: {
               PyObject[] var5;
               try {
                  var1.setline(183);
                  var4 = var1.getlocal(1).__getattr__("args");
                  var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  PyObject[] var7 = Py.unpackSequence(var6, 4);
                  PyObject var8 = var7[0];
                  var1.setlocal(5, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(6, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(7, var8);
                  var8 = null;
                  var8 = var7[3];
                  var1.setlocal(8, var8);
                  var8 = null;
                  var6 = null;
                  var4 = null;
               } catch (Throwable var9) {
                  PyException var12 = Py.setException(var9, var1);
                  if (var12.match(var1.getglobal("Exception"))) {
                     var1.setline(185);
                     break label50;
                  }

                  throw var12;
               }

               var1.setline(187);
               Object var16 = var1.getlocal(5);
               if (!((PyObject)var16).__nonzero__()) {
                  var16 = PyString.fromInterned("<string>");
               }

               Object var13 = var16;
               var1.setlocal(5, (PyObject)var13);
               var5 = null;
               var1.setline(188);
               var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("  File \"%s\", line %d\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
               var1.setline(189);
               PyObject var14 = var1.getlocal(8);
               var10000 = var14._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(190);
                  var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("    %s\n")._mod(var1.getlocal(8).__getattr__("strip").__call__(var2)));
                  var1.setline(191);
                  var14 = var1.getlocal(7);
                  var10000 = var14._isnot(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(192);
                     var14 = var1.getlocal(8).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null).__getattr__("lstrip").__call__(var2);
                     var1.setlocal(9, var14);
                     var5 = null;
                     var1.setline(194);
                     var1.setline(194);
                     var5 = Py.EmptyObjects;
                     PyFunction var15 = new PyFunction(var1.f_globals, var5, f$10, (PyObject)null);
                     var10000 = var15.__call__(var2, var1.getlocal(9).__iter__());
                     Arrays.fill(var5, (Object)null);
                     var14 = var10000;
                     var1.setlocal(9, var14);
                     var5 = null;
                     var1.setline(196);
                     var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("   %s^\n")._mod(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(9))));
                  }
               }

               var1.setline(197);
               var14 = var1.getlocal(4);
               var1.setlocal(1, var14);
               var5 = null;
            }

            var1.setline(199);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_format_final_exc_line").__call__(var2, var1.getlocal(2), var1.getlocal(1)));
            var1.setline(200);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject f$10(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(194);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var6 = (PyObject)var10000;
      }

      var1.setline(194);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(194);
         var1.setline(194);
         var10000 = var1.getlocal(1).__getattr__("isspace").__call__(var2);
         if (((PyObject)var10000).__nonzero__()) {
            var10000 = var1.getlocal(1);
         }

         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned(" ");
         }

         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return (PyObject)var10000;
      }
   }

   public PyObject _format_final_exc_line$11(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyString.fromInterned("Return a list of a single line -- normal case for format_exception_only");
      var1.setline(204);
      PyObject var3 = var1.getglobal("_some_str").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(206);
         var3 = PyString.fromInterned("%s\n")._mod(var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(208);
         var3 = PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(2)}));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(209);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _some_str$12(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(213);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("Exception"))) {
            var1.setline(215);

            try {
               var1.setline(217);
               PyObject var7 = var1.getglobal("unicode").__call__(var2, var1.getlocal(0));
               var1.setlocal(0, var7);
               var4 = null;
               var1.setline(218);
               var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"), (PyObject)PyString.fromInterned("backslashreplace"));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               var4 = Py.setException(var5, var1);
               if (var4.match(var1.getglobal("Exception"))) {
                  var1.setline(220);
                  var1.setline(221);
                  var3 = PyString.fromInterned("<unprintable %s object>")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  throw var4;
               }
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject print_exc$13(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyString.fromInterned("Shorthand for 'print_exception(sys.exc_type, sys.exc_value, sys.exc_traceback, limit, file)'.\n    (In fact, it uses sys.exc_info() to retrieve the same information\n    in a thread-safe way.)");
      var1.setline(228);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(229);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var3 = null;

      PyObject var4;
      try {
         var1.setline(231);
         var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(232);
         var10000 = var1.getglobal("print_exception");
         PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(0), var1.getlocal(1)};
         var10000.__call__(var2, var8);
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(234);
         var4 = var1.getglobal("None");
         var1.setlocal(2, var4);
         var1.setlocal(3, var4);
         var1.setlocal(4, var4);
         throw (Throwable)var7;
      }

      var1.setline(234);
      var4 = var1.getglobal("None");
      var1.setlocal(2, var4);
      var1.setlocal(3, var4);
      var1.setlocal(4, var4);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_exc$14(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyString.fromInterned("Like print_exc() but return a string.");
      Throwable var3 = null;

      Throwable var10000;
      PyObject var9;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(240);
            var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
            PyObject[] var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(3, var6);
            var6 = null;
            var4 = null;
            var1.setline(241);
            var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("format_exception").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0)));
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
            break label25;
         }

         var1.setline(243);
         var9 = var1.getglobal("None");
         var1.setlocal(1, var9);
         var1.setlocal(2, var9);
         var1.setlocal(3, var9);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
         }
      }

      var3 = var10000;
      Py.addTraceback(var3, var1);
      var1.setline(243);
      var9 = var1.getglobal("None");
      var1.setlocal(1, var9);
      var1.setlocal(2, var9);
      var1.setlocal(3, var9);
      throw (Throwable)var3;
   }

   public PyObject print_last$15(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("This is a shorthand for 'print_exception(sys.last_type,\n    sys.last_value, sys.last_traceback, limit, file)'.");
      var1.setline(249);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("last_type")).__not__().__nonzero__()) {
         var1.setline(250);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no last exception")));
      } else {
         var1.setline(251);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(252);
            var3 = var1.getglobal("sys").__getattr__("stderr");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(253);
         var10000 = var1.getglobal("print_exception");
         PyObject[] var4 = new PyObject[]{var1.getglobal("sys").__getattr__("last_type"), var1.getglobal("sys").__getattr__("last_value"), var1.getglobal("sys").__getattr__("last_traceback"), var1.getlocal(0), var1.getlocal(1)};
         var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject print_stack$16(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyString.fromInterned("Print a stack trace from its invocation point.\n\n    The optional 'f' argument can be used to specify an alternate\n    stack frame at which to start. The optional 'limit' and 'file'\n    arguments have the same meaning as for print_exception().\n    ");
      var1.setline(264);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(266);
            throw Py.makeException(var1.getglobal("ZeroDivisionError"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("ZeroDivisionError"))) {
               throw var6;
            }
         }

         var1.setline(268);
         PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)).__getattr__("tb_frame").__getattr__("f_back");
         var1.setlocal(0, var4);
         var4 = null;
      }

      var1.setline(269);
      var1.getglobal("print_list").__call__(var2, var1.getglobal("extract_stack").__call__(var2, var1.getlocal(0), var1.getlocal(1)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_stack$17(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyString.fromInterned("Shorthand for 'format_list(extract_stack(f, limit))'.");
      var1.setline(273);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(275);
            throw Py.makeException(var1.getglobal("ZeroDivisionError"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("ZeroDivisionError"))) {
               throw var6;
            }
         }

         var1.setline(277);
         PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)).__getattr__("tb_frame").__getattr__("f_back");
         var1.setlocal(0, var4);
         var4 = null;
      }

      var1.setline(278);
      var3 = var1.getglobal("format_list").__call__(var2, var1.getglobal("extract_stack").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject extract_stack$18(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyString.fromInterned("Extract the raw traceback from the current stack frame.\n\n    The return value has the same format as for extract_tb().  The\n    optional 'f' and 'limit' arguments have the same meaning as for\n    print_stack().  Each item in the list is a quadruple (filename,\n    line number, function name, text), and the entries are in order\n    from oldest to newest stack frame.\n    ");
      var1.setline(289);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(291);
            throw Py.makeException(var1.getglobal("ZeroDivisionError"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("ZeroDivisionError"))) {
               throw var6;
            }
         }

         var1.setline(293);
         PyObject var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)).__getattr__("tb_frame").__getattr__("f_back");
         var1.setlocal(0, var4);
         var4 = null;
      }

      var1.setline(294);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(295);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("tracebacklimit")).__nonzero__()) {
            var1.setline(296);
            var3 = var1.getglobal("sys").__getattr__("tracebacklimit");
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(297);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(298);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal(3, var8);
      var3 = null;

      while(true) {
         var1.setline(299);
         var3 = var1.getlocal(0);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(3);
               var10000 = var3._lt(var1.getlocal(1));
               var3 = null;
            }
         }

         if (!var10000.__nonzero__()) {
            var1.setline(311);
            var1.getlocal(2).__getattr__("reverse").__call__(var2);
            var1.setline(312);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(300);
         var3 = var1.getlocal(0).__getattr__("f_lineno");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(301);
         var3 = var1.getlocal(0).__getattr__("f_code");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(302);
         var3 = var1.getlocal(5).__getattr__("co_filename");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(303);
         var3 = var1.getlocal(5).__getattr__("co_name");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(304);
         var1.getglobal("linecache").__getattr__("checkcache").__call__(var2, var1.getlocal(6));
         var1.setline(305);
         var3 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(6), var1.getlocal(4), var1.getlocal(0).__getattr__("f_globals"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(306);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(306);
            var3 = var1.getlocal(8).__getattr__("strip").__call__(var2);
            var1.setlocal(8, var3);
            var3 = null;
         } else {
            var1.setline(307);
            var3 = var1.getglobal("None");
            var1.setlocal(8, var3);
            var3 = null;
         }

         var1.setline(308);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(4), var1.getlocal(7), var1.getlocal(8)})));
         var1.setline(309);
         var3 = var1.getlocal(0).__getattr__("f_back");
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(310);
         var3 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject tb_lineno$19(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyString.fromInterned("Calculate correct line number of traceback given in tb.\n\n    Obsolete in 2.3.\n    ");
      var1.setline(319);
      PyObject var3 = var1.getlocal(0).__getattr__("tb_lineno");
      var1.f_lasti = -1;
      return var3;
   }

   public traceback$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file", "str", "terminator"};
      _print$1 = Py.newCode(3, var2, var1, "_print", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"extracted_list", "file", "filename", "lineno", "name", "line"};
      print_list$2 = Py.newCode(2, var2, var1, "print_list", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"extracted_list", "list", "filename", "lineno", "name", "line", "item"};
      format_list$3 = Py.newCode(1, var2, var1, "format_list", 27, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tb", "limit", "file", "n", "f", "lineno", "co", "filename", "name", "line"};
      print_tb$4 = Py.newCode(3, var2, var1, "print_tb", 46, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tb", "limit"};
      format_tb$5 = Py.newCode(2, var2, var1, "format_tb", 74, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tb", "limit", "list", "n", "f", "lineno", "co", "filename", "name", "line"};
      extract_tb$6 = Py.newCode(2, var2, var1, "extract_tb", 78, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"etype", "value", "tb", "limit", "file", "lines", "line"};
      print_exception$7 = Py.newCode(5, var2, var1, "print_exception", 110, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"etype", "value", "tb", "limit", "list"};
      format_exception$8 = Py.newCode(4, var2, var1, "format_exception", 130, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"etype", "value", "stype", "lines", "msg", "filename", "lineno", "offset", "badline", "caretspace", "_(194_30)"};
      format_exception_only$9 = Py.newCode(2, var2, var1, "format_exception_only", 147, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "c"};
      f$10 = Py.newCode(1, var2, var1, "<genexpr>", 194, false, false, self, 10, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"etype", "value", "valuestr", "line"};
      _format_final_exc_line$11 = Py.newCode(2, var2, var1, "_format_final_exc_line", 202, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      _some_str$12 = Py.newCode(1, var2, var1, "_some_str", 211, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"limit", "file", "etype", "value", "tb"};
      print_exc$13 = Py.newCode(2, var2, var1, "print_exc", 224, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"limit", "etype", "value", "tb"};
      format_exc$14 = Py.newCode(1, var2, var1, "format_exc", 237, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"limit", "file"};
      print_last$15 = Py.newCode(2, var2, var1, "print_last", 246, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "limit", "file"};
      print_stack$16 = Py.newCode(3, var2, var1, "print_stack", 257, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "limit"};
      format_stack$17 = Py.newCode(2, var2, var1, "format_stack", 271, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "limit", "list", "n", "lineno", "co", "filename", "name", "line"};
      extract_stack$18 = Py.newCode(2, var2, var1, "extract_stack", 280, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tb"};
      tb_lineno$19 = Py.newCode(1, var2, var1, "tb_lineno", 314, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new traceback$py("traceback$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(traceback$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._print$1(var2, var3);
         case 2:
            return this.print_list$2(var2, var3);
         case 3:
            return this.format_list$3(var2, var3);
         case 4:
            return this.print_tb$4(var2, var3);
         case 5:
            return this.format_tb$5(var2, var3);
         case 6:
            return this.extract_tb$6(var2, var3);
         case 7:
            return this.print_exception$7(var2, var3);
         case 8:
            return this.format_exception$8(var2, var3);
         case 9:
            return this.format_exception_only$9(var2, var3);
         case 10:
            return this.f$10(var2, var3);
         case 11:
            return this._format_final_exc_line$11(var2, var3);
         case 12:
            return this._some_str$12(var2, var3);
         case 13:
            return this.print_exc$13(var2, var3);
         case 14:
            return this.format_exc$14(var2, var3);
         case 15:
            return this.print_last$15(var2, var3);
         case 16:
            return this.print_stack$16(var2, var3);
         case 17:
            return this.format_stack$17(var2, var3);
         case 18:
            return this.extract_stack$18(var2, var3);
         case 19:
            return this.tb_lineno$19(var2, var3);
         default:
            return null;
      }
   }
}
