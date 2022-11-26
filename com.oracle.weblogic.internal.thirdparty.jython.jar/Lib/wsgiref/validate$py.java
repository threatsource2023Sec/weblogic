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
@Filename("wsgiref/validate.py")
public class validate$py extends PyFunctionTable implements PyRunnable {
   static validate$py self;
   static final PyCode f$0;
   static final PyCode WSGIWarning$1;
   static final PyCode assert_$2;
   static final PyCode validator$3;
   static final PyCode lint_app$4;
   static final PyCode start_response_wrapper$5;
   static final PyCode InputWrapper$6;
   static final PyCode __init__$7;
   static final PyCode read$8;
   static final PyCode readline$9;
   static final PyCode readlines$10;
   static final PyCode __iter__$11;
   static final PyCode close$12;
   static final PyCode ErrorWrapper$13;
   static final PyCode __init__$14;
   static final PyCode write$15;
   static final PyCode flush$16;
   static final PyCode writelines$17;
   static final PyCode close$18;
   static final PyCode WriteWrapper$19;
   static final PyCode __init__$20;
   static final PyCode __call__$21;
   static final PyCode PartialIteratorWrapper$22;
   static final PyCode __init__$23;
   static final PyCode __iter__$24;
   static final PyCode IteratorWrapper$25;
   static final PyCode __init__$26;
   static final PyCode __iter__$27;
   static final PyCode next$28;
   static final PyCode close$29;
   static final PyCode __del__$30;
   static final PyCode check_environ$31;
   static final PyCode check_input$32;
   static final PyCode check_errors$33;
   static final PyCode check_status$34;
   static final PyCode check_headers$35;
   static final PyCode check_content_type$36;
   static final PyCode check_exc_info$37;
   static final PyCode check_iterator$38;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nMiddleware to check for obedience to the WSGI specification.\n\nSome of the things this checks:\n\n* Signature of the application and start_response (including that\n  keyword arguments are not used).\n\n* Environment checks:\n\n  - Environment is a dictionary (and not a subclass).\n\n  - That all the required keys are in the environment: REQUEST_METHOD,\n    SERVER_NAME, SERVER_PORT, wsgi.version, wsgi.input, wsgi.errors,\n    wsgi.multithread, wsgi.multiprocess, wsgi.run_once\n\n  - That HTTP_CONTENT_TYPE and HTTP_CONTENT_LENGTH are not in the\n    environment (these headers should appear as CONTENT_LENGTH and\n    CONTENT_TYPE).\n\n  - Warns if QUERY_STRING is missing, as the cgi module acts\n    unpredictably in that case.\n\n  - That CGI-style variables (that don't contain a .) have\n    (non-unicode) string values\n\n  - That wsgi.version is a tuple\n\n  - That wsgi.url_scheme is 'http' or 'https' (@@: is this too\n    restrictive?)\n\n  - Warns if the REQUEST_METHOD is not known (@@: probably too\n    restrictive).\n\n  - That SCRIPT_NAME and PATH_INFO are empty or start with /\n\n  - That at least one of SCRIPT_NAME or PATH_INFO are set.\n\n  - That CONTENT_LENGTH is a positive integer.\n\n  - That SCRIPT_NAME is not '/' (it should be '', and PATH_INFO should\n    be '/').\n\n  - That wsgi.input has the methods read, readline, readlines, and\n    __iter__\n\n  - That wsgi.errors has the methods flush, write, writelines\n\n* The status is a string, contains a space, starts with an integer,\n  and that integer is in range (> 100).\n\n* That the headers is a list (not a subclass, not another kind of\n  sequence).\n\n* That the items of the headers are tuples of strings.\n\n* That there is no 'status' header (that is used in CGI, but not in\n  WSGI).\n\n* That the headers don't contain newlines or colons, end in _ or -, or\n  contain characters codes below 037.\n\n* That Content-Type is given if there is content (CGI often has a\n  default content type, but WSGI does not).\n\n* That no Content-Type is given when there is no content (@@: is this\n  too restrictive?)\n\n* That the exc_info argument to start_response is a tuple or None.\n\n* That all calls to the writer are with strings, and no other methods\n  on the writer are accessed.\n\n* That wsgi.input is used properly:\n\n  - .read() is called with zero or one argument\n\n  - That it returns a string\n\n  - That readline, readlines, and __iter__ return strings\n\n  - That .close() is not called\n\n  - No other methods are provided\n\n* That wsgi.errors is used properly:\n\n  - .write() and .writelines() is called with a string\n\n  - That .close() is not called, and no other methods are provided.\n\n* The response iterator:\n\n  - That it is not a string (it should be a list of a single string; a\n    string will work, but perform horribly).\n\n  - That .next() returns a string\n\n  - That the iterator is not iterated over until start_response has\n    been called (that can signal either a server or application\n    error).\n\n  - That .close() is called (doesn't raise exception, only prints to\n    sys.stderr, because we only know it isn't called when the object\n    is garbage collected).\n"));
      var1.setline(110);
      PyString.fromInterned("\nMiddleware to check for obedience to the WSGI specification.\n\nSome of the things this checks:\n\n* Signature of the application and start_response (including that\n  keyword arguments are not used).\n\n* Environment checks:\n\n  - Environment is a dictionary (and not a subclass).\n\n  - That all the required keys are in the environment: REQUEST_METHOD,\n    SERVER_NAME, SERVER_PORT, wsgi.version, wsgi.input, wsgi.errors,\n    wsgi.multithread, wsgi.multiprocess, wsgi.run_once\n\n  - That HTTP_CONTENT_TYPE and HTTP_CONTENT_LENGTH are not in the\n    environment (these headers should appear as CONTENT_LENGTH and\n    CONTENT_TYPE).\n\n  - Warns if QUERY_STRING is missing, as the cgi module acts\n    unpredictably in that case.\n\n  - That CGI-style variables (that don't contain a .) have\n    (non-unicode) string values\n\n  - That wsgi.version is a tuple\n\n  - That wsgi.url_scheme is 'http' or 'https' (@@: is this too\n    restrictive?)\n\n  - Warns if the REQUEST_METHOD is not known (@@: probably too\n    restrictive).\n\n  - That SCRIPT_NAME and PATH_INFO are empty or start with /\n\n  - That at least one of SCRIPT_NAME or PATH_INFO are set.\n\n  - That CONTENT_LENGTH is a positive integer.\n\n  - That SCRIPT_NAME is not '/' (it should be '', and PATH_INFO should\n    be '/').\n\n  - That wsgi.input has the methods read, readline, readlines, and\n    __iter__\n\n  - That wsgi.errors has the methods flush, write, writelines\n\n* The status is a string, contains a space, starts with an integer,\n  and that integer is in range (> 100).\n\n* That the headers is a list (not a subclass, not another kind of\n  sequence).\n\n* That the items of the headers are tuples of strings.\n\n* That there is no 'status' header (that is used in CGI, but not in\n  WSGI).\n\n* That the headers don't contain newlines or colons, end in _ or -, or\n  contain characters codes below 037.\n\n* That Content-Type is given if there is content (CGI often has a\n  default content type, but WSGI does not).\n\n* That no Content-Type is given when there is no content (@@: is this\n  too restrictive?)\n\n* That the exc_info argument to start_response is a tuple or None.\n\n* That all calls to the writer are with strings, and no other methods\n  on the writer are accessed.\n\n* That wsgi.input is used properly:\n\n  - .read() is called with zero or one argument\n\n  - That it returns a string\n\n  - That readline, readlines, and __iter__ return strings\n\n  - That .close() is not called\n\n  - No other methods are provided\n\n* That wsgi.errors is used properly:\n\n  - .write() and .writelines() is called with a string\n\n  - That .close() is not called, and no other methods are provided.\n\n* The response iterator:\n\n  - That it is not a string (it should be a list of a single string; a\n    string will work, but perform horribly).\n\n  - That .next() returns a string\n\n  - That the iterator is not iterated over until start_response has\n    been called (that can signal either a server or application\n    error).\n\n  - That .close() is called (doesn't raise exception, only prints to\n    sys.stderr, because we only know it isn't called when the object\n    is garbage collected).\n");
      var1.setline(111);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("validator")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(114);
      PyObject var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(115);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(116);
      String[] var6 = new String[]{"DictType", "StringType", "TupleType", "ListType"};
      PyObject[] var7 = imp.importFrom("types", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DictType", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("StringType", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("TupleType", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("ListType", var4);
      var4 = null;
      var1.setline(117);
      var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(119);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[a-zA-Z][a-zA-Z0-9\\-_]*$"));
      var1.setlocal("header_re", var5);
      var3 = null;
      var1.setline(120);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\000-\\037]"));
      var1.setlocal("bad_header_value_re", var5);
      var3 = null;
      var1.setline(122);
      var7 = new PyObject[]{var1.getname("Warning")};
      var4 = Py.makeClass("WSGIWarning", var7, WSGIWarning$1);
      var1.setlocal("WSGIWarning", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(127);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, assert_$2, (PyObject)null);
      var1.setlocal("assert_", var8);
      var3 = null;
      var1.setline(131);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, validator$3, PyString.fromInterned("\n    When applied between a WSGI server and a WSGI application, this\n    middleware will check for WSGI compliancy on a number of levels.\n    This middleware does not modify the request or response in any\n    way, but will raise an AssertionError if anything seems off\n    (except for a failure to close the application iterator, which\n    will be printed to stderr -- there's no way to raise an exception\n    at that point).\n    "));
      var1.setlocal("validator", var8);
      var3 = null;
      var1.setline(186);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("InputWrapper", var7, InputWrapper$6);
      var1.setlocal("InputWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(220);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("ErrorWrapper", var7, ErrorWrapper$13);
      var1.setlocal("ErrorWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(239);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("WriteWrapper", var7, WriteWrapper$19);
      var1.setlocal("WriteWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(248);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("PartialIteratorWrapper", var7, PartialIteratorWrapper$22);
      var1.setlocal("PartialIteratorWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(257);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("IteratorWrapper", var7, IteratorWrapper$25);
      var1.setlocal("IteratorWrapper", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(290);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_environ$31, (PyObject)null);
      var1.setlocal("check_environ", var8);
      var3 = null;
      var1.setline(355);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_input$32, (PyObject)null);
      var1.setlocal("check_input", var8);
      var3 = null;
      var1.setline(361);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_errors$33, (PyObject)null);
      var1.setlocal("check_errors", var8);
      var3 = null;
      var1.setline(367);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_status$34, (PyObject)null);
      var1.setlocal("check_status", var8);
      var3 = null;
      var1.setline(382);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_headers$35, (PyObject)null);
      var1.setlocal("check_headers", var8);
      var3 = null;
      var1.setline(407);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_content_type$36, (PyObject)null);
      var1.setlocal("check_content_type", var8);
      var3 = null;
      var1.setline(421);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_exc_info$37, (PyObject)null);
      var1.setlocal("check_exc_info", var8);
      var3 = null;
      var1.setline(426);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_iterator$38, (PyObject)null);
      var1.setlocal("check_iterator", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject WSGIWarning$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised in response to WSGI-spec-related warnings\n    "));
      var1.setline(125);
      PyString.fromInterned("\n    Raised in response to WSGI-spec-related warnings\n    ");
      return var1.getf_locals();
   }

   public PyObject assert_$2(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(129);
         PyObject var10000 = var1.getglobal("AssertionError");
         PyObject[] var3 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
         var3 = null;
         throw Py.makeException(var10000);
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject validator$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(141);
      PyString.fromInterned("\n    When applied between a WSGI server and a WSGI application, this\n    middleware will check for WSGI compliancy on a number of levels.\n    This middleware does not modify the request or response in any\n    way, but will raise an AssertionError if anything seems off\n    (except for a failure to close the application iterator, which\n    will be printed to stderr -- there's no way to raise an exception\n    at that point).\n    ");
      var1.setline(143);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = lint_app$4;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(184);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject lint_app$4(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10002 = var3._eq(Py.newInteger(2));
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("Two arguments required"));
      var1.setline(145);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__not__(), (PyObject)PyString.fromInterned("No keyword arguments allowed"));
      var1.setline(146);
      var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setderef(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(148);
      var1.getglobal("check_environ").__call__(var2, var1.getlocal(2));
      var1.setline(152);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setderef(1, var6);
      var3 = null;
      var1.setline(154);
      PyObject[] var7 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      PyObject[] var10003 = var7;
      PyCode var10004 = start_response_wrapper$5;
      var7 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("InputWrapper").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("wsgi.input")));
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("wsgi.input"), var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getglobal("ErrorWrapper").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("wsgi.errors")));
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("wsgi.errors"), var3);
      var3 = null;
      var1.setline(176);
      var3 = var1.getderef(2).__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(177);
      var10000 = var1.getglobal("assert_");
      var3 = var1.getlocal(4);
      var10002 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10002.__nonzero__()) {
         var3 = var1.getlocal(4);
         var10002 = var3._ne(var1.getglobal("False"));
         var3 = null;
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("The application must return an iterator, if only an empty list"));
      var1.setline(180);
      var1.getglobal("check_iterator").__call__(var2, var1.getlocal(4));
      var1.setline(182);
      var3 = var1.getglobal("IteratorWrapper").__call__(var2, var1.getlocal(4), var1.getderef(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject start_response_wrapper$5(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10002 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (!var10002.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10002 = var3._eq(Py.newInteger(3));
         var3 = null;
      }

      var10000.__call__(var2, var10002, PyString.fromInterned("Invalid number of arguments: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
      var1.setline(157);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__not__(), (PyObject)PyString.fromInterned("No keyword arguments allowed"));
      var1.setline(158);
      var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(160);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(161);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(163);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(165);
      var1.getglobal("check_status").__call__(var2, var1.getlocal(2));
      var1.setline(166);
      var1.getglobal("check_headers").__call__(var2, var1.getlocal(3));
      var1.setline(167);
      var1.getglobal("check_content_type").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(168);
      var1.getglobal("check_exc_info").__call__(var2, var1.getlocal(4));
      var1.setline(170);
      var1.getderef(0).__getattr__("append").__call__(var2, var1.getglobal("None"));
      var1.setline(171);
      var10000 = var1.getglobal("WriteWrapper");
      var10002 = var1.getderef(1);
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var5, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject InputWrapper$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(188);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$8, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$9, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readlines$10, (PyObject)null);
      var1.setlocal("readlines", var4);
      var3 = null;
      var1.setline(210);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$11, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$12, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("input", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$8(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10002 = var3._le(Py.newInteger(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(193);
      var10000 = var1.getlocal(0).__getattr__("input").__getattr__("read");
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(194);
      var10000 = var1.getglobal("assert_");
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      var10002 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(195);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$9(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getlocal(0).__getattr__("input").__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(199);
      PyObject var10000 = var1.getglobal("assert_");
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10002 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(200);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readlines$10(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10002 = var3._le(Py.newInteger(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(204);
      var10000 = var1.getlocal(0).__getattr__("input").__getattr__("readlines");
      PyObject[] var6 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var6, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(205);
      var10000 = var1.getglobal("assert_");
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      var10002 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(206);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(206);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(208);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var7);
         var1.setline(207);
         var10000 = var1.getglobal("assert_");
         PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
         var10002 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject __iter__$11(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var5;
      switch (var1.f_lasti) {
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var5 = (PyObject)var10000;
            }
         case 0:
         default:
            var1.setline(211);
            if (!Py.newInteger(1).__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(212);
               PyObject var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
               var1.setlocal(1, var4);
               var3 = null;
               var1.setline(213);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(214);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  var1.setline(215);
                  var1.setline(215);
                  var5 = var1.getlocal(1);
                  var1.f_lasti = 1;
                  var3 = new Object[4];
                  var1.f_savedlocals = var3;
                  return var5;
               }
            }
      }
   }

   public PyObject close$12(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("input.close() must not be called"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ErrorWrapper$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(222);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$15, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$16, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(232);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writelines$17, (PyObject)null);
      var1.setlocal("writelines", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$18, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$15(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10002 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(227);
      var1.getlocal(0).__getattr__("errors").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$16(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      var1.getlocal(0).__getattr__("errors").__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writelines$17(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(233);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(234);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject close$18(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("errors.close() must not be called"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject WriteWrapper$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(241);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(244);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$21, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("writer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$21(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10002 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(246);
      var1.getlocal(0).__getattr__("writer").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PartialIteratorWrapper$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(250);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$24, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("iterator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$24(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyObject var3 = var1.getglobal("IteratorWrapper").__call__(var2, var1.getlocal(0).__getattr__("iterator"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IteratorWrapper$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(259);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$26, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$27, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$28, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(278);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$29, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(283);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$30, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$26(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("original_iterator", var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("iterator", var3);
      var3 = null;
      var1.setline(262);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.setline(263);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("check_start_response", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$27(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$28(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("closed").__not__(), (PyObject)PyString.fromInterned("Iterator read after closed"));
      var1.setline(271);
      PyObject var3 = var1.getlocal(0).__getattr__("iterator").__getattr__("next").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(272);
      var3 = var1.getlocal(0).__getattr__("check_start_response");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(273);
         var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("check_start_response"), (PyObject)PyString.fromInterned("The application returns and we started iterating over its body, but start_response has not yet been called"));
         var1.setline(275);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("check_start_response", var3);
         var3 = null;
      }

      var1.setline(276);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$29(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.setline(280);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("original_iterator"), (PyObject)PyString.fromInterned("close")).__nonzero__()) {
         var1.setline(281);
         var1.getlocal(0).__getattr__("original_iterator").__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$30(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      if (var1.getlocal(0).__getattr__("closed").__not__().__nonzero__()) {
         var1.setline(285);
         var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Iterator garbage collected without being closed"));
      }

      var1.setline(287);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("closed"), (PyObject)PyString.fromInterned("Iterator garbage collected without being closed"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_environ$31(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10002 = var3._is(var1.getglobal("DictType"));
      var3 = null;
      var10000.__call__(var2, var10002, PyString.fromInterned("Environment is not of the right type: %r (environment: %r)")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)), var1.getlocal(0)})));
      var1.setline(295);
      var3 = (new PyList(new PyObject[]{PyString.fromInterned("REQUEST_METHOD"), PyString.fromInterned("SERVER_NAME"), PyString.fromInterned("SERVER_PORT"), PyString.fromInterned("wsgi.version"), PyString.fromInterned("wsgi.input"), PyString.fromInterned("wsgi.errors"), PyString.fromInterned("wsgi.multithread"), PyString.fromInterned("wsgi.multiprocess"), PyString.fromInterned("wsgi.run_once")})).__iter__();

      while(true) {
         var1.setline(295);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(302);
            var3 = (new PyList(new PyObject[]{PyString.fromInterned("HTTP_CONTENT_TYPE"), PyString.fromInterned("HTTP_CONTENT_LENGTH")})).__iter__();

            while(true) {
               var1.setline(302);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(307);
                  PyString var6 = PyString.fromInterned("QUERY_STRING");
                  var10000 = var6._notin(var1.getlocal(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(308);
                     var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("QUERY_STRING is not in the WSGI environment; the cgi module will use sys.argv when this variable is missing, so application errors are more likely"), (PyObject)var1.getglobal("WSGIWarning"));
                  }

                  var1.setline(314);
                  var3 = var1.getlocal(0).__getattr__("keys").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(314);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(322);
                        var10000 = var1.getglobal("assert_");
                        var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.version")));
                        var10002 = var3._is(var1.getglobal("TupleType"));
                        var3 = null;
                        var10000.__call__(var2, var10002, PyString.fromInterned("wsgi.version should be a tuple (%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.version"))})));
                        var1.setline(324);
                        var10000 = var1.getglobal("assert_");
                        var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.url_scheme"));
                        var10002 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("http"), PyString.fromInterned("https")}));
                        var3 = null;
                        var10000.__call__(var2, var10002, PyString.fromInterned("wsgi.url_scheme unknown: %r")._mod(var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.url_scheme"))));
                        var1.setline(327);
                        var1.getglobal("check_input").__call__(var2, var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.input")));
                        var1.setline(328);
                        var1.getglobal("check_errors").__call__(var2, var1.getlocal(0).__getitem__(PyString.fromInterned("wsgi.errors")));
                        var1.setline(331);
                        var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("REQUEST_METHOD"));
                        var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("GET"), PyString.fromInterned("HEAD"), PyString.fromInterned("POST"), PyString.fromInterned("OPTIONS"), PyString.fromInterned("PUT"), PyString.fromInterned("DELETE"), PyString.fromInterned("TRACE")}));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(333);
                           var1.getglobal("warnings").__getattr__("warn").__call__(var2, PyString.fromInterned("Unknown REQUEST_METHOD: %r")._mod(var1.getlocal(0).__getitem__(PyString.fromInterned("REQUEST_METHOD"))), var1.getglobal("WSGIWarning"));
                        }

                        var1.setline(337);
                        var10000 = var1.getglobal("assert_");
                        var10002 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME")).__not__();
                        if (!var10002.__nonzero__()) {
                           var10002 = var1.getlocal(0).__getitem__(PyString.fromInterned("SCRIPT_NAME")).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
                        }

                        var10000.__call__(var2, var10002, PyString.fromInterned("SCRIPT_NAME doesn't start with /: %r")._mod(var1.getlocal(0).__getitem__(PyString.fromInterned("SCRIPT_NAME"))));
                        var1.setline(340);
                        var10000 = var1.getglobal("assert_");
                        var10002 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PATH_INFO")).__not__();
                        if (!var10002.__nonzero__()) {
                           var10002 = var1.getlocal(0).__getitem__(PyString.fromInterned("PATH_INFO")).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
                        }

                        var10000.__call__(var2, var10002, PyString.fromInterned("PATH_INFO doesn't start with /: %r")._mod(var1.getlocal(0).__getitem__(PyString.fromInterned("PATH_INFO"))));
                        var1.setline(343);
                        if (var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CONTENT_LENGTH")).__nonzero__()) {
                           var1.setline(344);
                           var10000 = var1.getglobal("assert_");
                           var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getitem__(PyString.fromInterned("CONTENT_LENGTH")));
                           var10002 = var3._ge(Py.newInteger(0));
                           var3 = null;
                           var10000.__call__(var2, var10002, PyString.fromInterned("Invalid CONTENT_LENGTH: %r")._mod(var1.getlocal(0).__getitem__(PyString.fromInterned("CONTENT_LENGTH"))));
                        }

                        var1.setline(347);
                        if (var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME")).__not__().__nonzero__()) {
                           var1.setline(348);
                           var10000 = var1.getglobal("assert_");
                           var6 = PyString.fromInterned("PATH_INFO");
                           var10002 = var6._in(var1.getlocal(0));
                           var3 = null;
                           var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("One of SCRIPT_NAME or PATH_INFO are required (PATH_INFO should at least be '/' if SCRIPT_NAME is empty)"));
                        }

                        var1.setline(351);
                        var10000 = var1.getglobal("assert_");
                        var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SCRIPT_NAME"));
                        var10002 = var3._ne(PyString.fromInterned("/"));
                        var3 = null;
                        var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("SCRIPT_NAME cannot be '/'; it should instead be '', and PATH_INFO should be '/'"));
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(1, var4);
                     var1.setline(315);
                     PyString var7 = PyString.fromInterned(".");
                     var10000 = var7._in(var1.getlocal(1));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(318);
                        var10000 = var1.getglobal("assert_");
                        var5 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(1)));
                        var10002 = var5._is(var1.getglobal("StringType"));
                        var5 = null;
                        var10000.__call__(var2, var10002, PyString.fromInterned("Environmental variable %s is not a string: %r (value: %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(1))), var1.getlocal(0).__getitem__(var1.getlocal(1))})));
                     }
                  }
               }

               var1.setlocal(1, var4);
               var1.setline(303);
               var10000 = var1.getglobal("assert_");
               var5 = var1.getlocal(1);
               var10002 = var5._notin(var1.getlocal(0));
               var5 = null;
               var10000.__call__(var2, var10002, PyString.fromInterned("Environment should not have the key: %s (use %s instead)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1).__getslice__(Py.newInteger(5), (PyObject)null, (PyObject)null)})));
            }
         }

         var1.setlocal(1, var4);
         var1.setline(299);
         var10000 = var1.getglobal("assert_");
         var5 = var1.getlocal(1);
         var10002 = var5._in(var1.getlocal(0));
         var5 = null;
         var10000.__call__(var2, var10002, PyString.fromInterned("Environment missing required key: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      }
   }

   public PyObject check_input$32(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = (new PyList(new PyObject[]{PyString.fromInterned("read"), PyString.fromInterned("readline"), PyString.fromInterned("readlines"), PyString.fromInterned("__iter__")})).__iter__();

      while(true) {
         var1.setline(356);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(357);
         var1.getglobal("assert_").__call__(var2, var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(1)), PyString.fromInterned("wsgi.input (%r) doesn't have the attribute %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
      }
   }

   public PyObject check_errors$33(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = (new PyList(new PyObject[]{PyString.fromInterned("flush"), PyString.fromInterned("write"), PyString.fromInterned("writelines")})).__iter__();

      while(true) {
         var1.setline(362);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(363);
         var1.getglobal("assert_").__call__(var2, var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(1)), PyString.fromInterned("wsgi.errors (%r) doesn't have the attribute %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
      }
   }

   public PyObject check_status$34(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10002 = var3._is(var1.getglobal("StringType"));
      var3 = null;
      var10000.__call__(var2, var10002, PyString.fromInterned("Status must be a string (not %r)")._mod(var1.getlocal(0)));
      var1.setline(371);
      var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(372);
      var10000 = var1.getglobal("assert_");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var10002 = var3._eq(Py.newInteger(3));
      var3 = null;
      var10000.__call__(var2, var10002, PyString.fromInterned("Status codes must be three characters: %r")._mod(var1.getlocal(1)));
      var1.setline(374);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(375);
      var10000 = var1.getglobal("assert_");
      var3 = var1.getlocal(2);
      var10002 = var3._ge(Py.newInteger(100));
      var3 = null;
      var10000.__call__(var2, var10002, PyString.fromInterned("Status code is invalid: %r")._mod(var1.getlocal(2)));
      var1.setline(376);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var10000 = var3._lt(Py.newInteger(4));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(3));
         var10000 = var3._ne(PyString.fromInterned(" "));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(377);
         var1.getglobal("warnings").__getattr__("warn").__call__(var2, PyString.fromInterned("The status string (%r) should be a three-digit integer followed by a single space and a status explanation")._mod(var1.getlocal(0)), var1.getglobal("WSGIWarning"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_headers$35(PyFrame var1, ThreadState var2) {
      var1.setline(383);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10002 = var3._is(var1.getglobal("ListType"));
      var3 = null;
      var10000.__call__(var2, var10002, PyString.fromInterned("Headers (%r) must be of type list: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("type").__call__(var2, var1.getlocal(0))})));
      var1.setline(386);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(387);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(387);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(388);
         var10000 = var1.getglobal("assert_");
         PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var10002 = var5._is(var1.getglobal("TupleType"));
         var5 = null;
         var10000.__call__(var2, var10002, PyString.fromInterned("Individual headers (%r) must be of type tuple: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("type").__call__(var2, var1.getlocal(2))})));
         var1.setline(391);
         var10000 = var1.getglobal("assert_");
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10002 = var5._eq(Py.newInteger(2));
         var5 = null;
         var10000.__call__(var2, var10002);
         var1.setline(392);
         var5 = var1.getlocal(2);
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(4, var7);
         var7 = null;
         var5 = null;
         var1.setline(393);
         var10000 = var1.getglobal("assert_");
         var5 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         var10002 = var5._ne(PyString.fromInterned("status"));
         var5 = null;
         var10000.__call__(var2, var10002, PyString.fromInterned("The Status header cannot be used; it conflicts with CGI script, and HTTP status is not given through headers (value: %r).")._mod(var1.getlocal(4)));
         var1.setline(397);
         var5 = var1.getglobal("None");
         var1.getlocal(1).__setitem__(var1.getlocal(3).__getattr__("lower").__call__(var2), var5);
         var5 = null;
         var1.setline(398);
         var10000 = var1.getglobal("assert_");
         PyString var9 = PyString.fromInterned("\n");
         var10002 = var9._notin(var1.getlocal(3));
         var5 = null;
         if (var10002.__nonzero__()) {
            var9 = PyString.fromInterned(":");
            var10002 = var9._notin(var1.getlocal(3));
            var5 = null;
         }

         var10000.__call__(var2, var10002, PyString.fromInterned("Header names may not contain ':' or '\\n': %r")._mod(var1.getlocal(3)));
         var1.setline(400);
         var1.getglobal("assert_").__call__(var2, var1.getglobal("header_re").__getattr__("search").__call__(var2, var1.getlocal(3)), PyString.fromInterned("Bad header name: %r")._mod(var1.getlocal(3)));
         var1.setline(401);
         var10000 = var1.getglobal("assert_");
         var10002 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-")).__not__();
         if (var10002.__nonzero__()) {
            var10002 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__not__();
         }

         var10000.__call__(var2, var10002, PyString.fromInterned("Names may not end in '-' or '_': %r")._mod(var1.getlocal(3)));
         var1.setline(403);
         if (var1.getglobal("bad_header_value_re").__getattr__("search").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(404);
            var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("Bad header value: %r (bad char: %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("bad_header_value_re").__getattr__("search").__call__(var2, var1.getlocal(4)).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))})));
         }
      }
   }

   public PyObject check_content_type$36(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(411);
      PyTuple var7 = new PyTuple(new PyObject[]{Py.newInteger(204), Py.newInteger(304)});
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(412);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(412);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(418);
            var3 = var1.getlocal(2);
            var10000 = var3._notin(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(419);
               var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("No Content-Type header found in headers (%s)")._mod(var1.getlocal(1)));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(413);
         PyObject var8 = var1.getlocal(4).__getattr__("lower").__call__(var2);
         var10000 = var8._eq(PyString.fromInterned("content-type"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(414);
            var8 = var1.getlocal(2);
            var10000 = var8._notin(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(415);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(416);
            var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("Content-Type header found in a %s response, which must not return content.")._mod(var1.getlocal(2)));
         }
      }
   }

   public PyObject check_exc_info$37(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyObject var10000 = var1.getglobal("assert_");
      PyObject var3 = var1.getlocal(0);
      PyObject var10002 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10002.__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
         var10002 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects))));
         var3 = null;
      }

      var10000.__call__(var2, var10002, PyString.fromInterned("exc_info (%r) is not a tuple: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("type").__call__(var2, var1.getlocal(0))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_iterator$38(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      var1.getglobal("assert_").__call__((ThreadState)var2, (PyObject)var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__not__(), (PyObject)PyString.fromInterned("You should not return a string as your application iterator, instead return a single-item list containing that string."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public validate$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      WSGIWarning$1 = Py.newCode(0, var2, var1, "WSGIWarning", 122, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cond", "args"};
      assert_$2 = Py.newCode(2, var2, var1, "assert_", 127, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"application", "lint_app"};
      String[] var10001 = var2;
      validate$py var10007 = self;
      var2 = new String[]{"application"};
      validator$3 = Py.newCode(1, var10001, var1, "validator", 131, false, false, var10007, 3, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kw", "environ", "start_response_wrapper", "iterator", "start_response", "start_response_started"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"start_response", "start_response_started"};
      String[] var10009 = var2;
      var2 = new String[]{"application"};
      lint_app$4 = Py.newCode(2, var10001, var1, "lint_app", 143, true, true, var10007, 4, var10009, var2, 2, 4097);
      var2 = new String[]{"args", "kw", "status", "headers", "exc_info"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"start_response_started", "start_response"};
      start_response_wrapper$5 = Py.newCode(2, var10001, var1, "start_response_wrapper", 154, true, true, var10007, 5, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      InputWrapper$6 = Py.newCode(0, var2, var1, "InputWrapper", 186, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "wsgi_input"};
      __init__$7 = Py.newCode(2, var2, var1, "__init__", 188, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "v"};
      read$8 = Py.newCode(2, var2, var1, "read", 191, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "v"};
      readline$9 = Py.newCode(1, var2, var1, "readline", 197, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "lines", "line"};
      readlines$10 = Py.newCode(2, var2, var1, "readlines", 202, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      __iter__$11 = Py.newCode(1, var2, var1, "__iter__", 210, false, false, self, 11, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      close$12 = Py.newCode(1, var2, var1, "close", 217, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ErrorWrapper$13 = Py.newCode(0, var2, var1, "ErrorWrapper", 220, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "wsgi_errors"};
      __init__$14 = Py.newCode(2, var2, var1, "__init__", 222, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      write$15 = Py.newCode(2, var2, var1, "write", 225, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$16 = Py.newCode(1, var2, var1, "flush", 229, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq", "line"};
      writelines$17 = Py.newCode(2, var2, var1, "writelines", 232, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$18 = Py.newCode(1, var2, var1, "close", 236, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WriteWrapper$19 = Py.newCode(0, var2, var1, "WriteWrapper", 239, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "wsgi_writer"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 241, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      __call__$21 = Py.newCode(2, var2, var1, "__call__", 244, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PartialIteratorWrapper$22 = Py.newCode(0, var2, var1, "PartialIteratorWrapper", 248, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "wsgi_iterator"};
      __init__$23 = Py.newCode(2, var2, var1, "__init__", 250, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$24 = Py.newCode(1, var2, var1, "__iter__", 253, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IteratorWrapper$25 = Py.newCode(0, var2, var1, "IteratorWrapper", 257, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "wsgi_iterator", "check_start_response"};
      __init__$26 = Py.newCode(3, var2, var1, "__init__", 259, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$27 = Py.newCode(1, var2, var1, "__iter__", 265, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "v"};
      next$28 = Py.newCode(1, var2, var1, "next", 268, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$29 = Py.newCode(1, var2, var1, "close", 278, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$30 = Py.newCode(1, var2, var1, "__del__", 283, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"environ", "key"};
      check_environ$31 = Py.newCode(1, var2, var1, "check_environ", 290, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"wsgi_input", "attr"};
      check_input$32 = Py.newCode(1, var2, var1, "check_input", 355, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"wsgi_errors", "attr"};
      check_errors$33 = Py.newCode(1, var2, var1, "check_errors", 361, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"status", "status_code", "status_int"};
      check_status$34 = Py.newCode(1, var2, var1, "check_status", 367, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"headers", "header_names", "item", "name", "value"};
      check_headers$35 = Py.newCode(1, var2, var1, "check_headers", 382, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"status", "headers", "code", "NO_MESSAGE_BODY", "name", "value"};
      check_content_type$36 = Py.newCode(2, var2, var1, "check_content_type", 407, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"exc_info"};
      check_exc_info$37 = Py.newCode(1, var2, var1, "check_exc_info", 421, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"iterator"};
      check_iterator$38 = Py.newCode(1, var2, var1, "check_iterator", 426, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new validate$py("wsgiref/validate$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(validate$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.WSGIWarning$1(var2, var3);
         case 2:
            return this.assert_$2(var2, var3);
         case 3:
            return this.validator$3(var2, var3);
         case 4:
            return this.lint_app$4(var2, var3);
         case 5:
            return this.start_response_wrapper$5(var2, var3);
         case 6:
            return this.InputWrapper$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.read$8(var2, var3);
         case 9:
            return this.readline$9(var2, var3);
         case 10:
            return this.readlines$10(var2, var3);
         case 11:
            return this.__iter__$11(var2, var3);
         case 12:
            return this.close$12(var2, var3);
         case 13:
            return this.ErrorWrapper$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.write$15(var2, var3);
         case 16:
            return this.flush$16(var2, var3);
         case 17:
            return this.writelines$17(var2, var3);
         case 18:
            return this.close$18(var2, var3);
         case 19:
            return this.WriteWrapper$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.__call__$21(var2, var3);
         case 22:
            return this.PartialIteratorWrapper$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.__iter__$24(var2, var3);
         case 25:
            return this.IteratorWrapper$25(var2, var3);
         case 26:
            return this.__init__$26(var2, var3);
         case 27:
            return this.__iter__$27(var2, var3);
         case 28:
            return this.next$28(var2, var3);
         case 29:
            return this.close$29(var2, var3);
         case 30:
            return this.__del__$30(var2, var3);
         case 31:
            return this.check_environ$31(var2, var3);
         case 32:
            return this.check_input$32(var2, var3);
         case 33:
            return this.check_errors$33(var2, var3);
         case 34:
            return this.check_status$34(var2, var3);
         case 35:
            return this.check_headers$35(var2, var3);
         case 36:
            return this.check_content_type$36(var2, var3);
         case 37:
            return this.check_exc_info$37(var2, var3);
         case 38:
            return this.check_iterator$38(var2, var3);
         default:
            return null;
      }
   }
}
