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
@MTime(1498849384000L)
@Filename("doctest.py")
public class doctest$py extends PyFunctionTable implements PyRunnable {
   static doctest$py self;
   static final PyCode f$0;
   static final PyCode register_optionflag$1;
   static final PyCode _extract_future_flags$2;
   static final PyCode _normalize_module$3;
   static final PyCode _load_testfile$4;
   static final PyCode _indent$5;
   static final PyCode _exception_traceback$6;
   static final PyCode _SpoofOut$7;
   static final PyCode getvalue$8;
   static final PyCode truncate$9;
   static final PyCode _ellipsis_match$10;
   static final PyCode _comment_line$11;
   static final PyCode _OutputRedirectingPdb$12;
   static final PyCode __init__$13;
   static final PyCode set_trace$14;
   static final PyCode set_continue$15;
   static final PyCode trace_dispatch$16;
   static final PyCode _module_relative_path$17;
   static final PyCode Example$18;
   static final PyCode __init__$19;
   static final PyCode __eq__$20;
   static final PyCode __ne__$21;
   static final PyCode __hash__$22;
   static final PyCode DocTest$23;
   static final PyCode __init__$24;
   static final PyCode __repr__$25;
   static final PyCode __eq__$26;
   static final PyCode __ne__$27;
   static final PyCode __hash__$28;
   static final PyCode __cmp__$29;
   static final PyCode DocTestParser$30;
   static final PyCode parse$31;
   static final PyCode get_doctest$32;
   static final PyCode get_examples$33;
   static final PyCode _parse_example$34;
   static final PyCode _find_options$35;
   static final PyCode _min_indent$36;
   static final PyCode _check_prompt_blank$37;
   static final PyCode _check_prefix$38;
   static final PyCode DocTestFinder$39;
   static final PyCode __init__$40;
   static final PyCode find$41;
   static final PyCode _from_module$42;
   static final PyCode _find$43;
   static final PyCode _get_test$44;
   static final PyCode _find_lineno$45;
   static final PyCode DocTestRunner$46;
   static final PyCode __init__$47;
   static final PyCode report_start$48;
   static final PyCode report_success$49;
   static final PyCode report_failure$50;
   static final PyCode report_unexpected_exception$51;
   static final PyCode _failure_header$52;
   static final PyCode _DocTestRunner__run$53;
   static final PyCode _DocTestRunner__record_outcome$54;
   static final PyCode _DocTestRunner__patched_linecache_getlines$55;
   static final PyCode run$56;
   static final PyCode summarize$57;
   static final PyCode merge$58;
   static final PyCode OutputChecker$59;
   static final PyCode check_output$60;
   static final PyCode _do_a_fancy_diff$61;
   static final PyCode output_difference$62;
   static final PyCode DocTestFailure$63;
   static final PyCode __init__$64;
   static final PyCode __str__$65;
   static final PyCode UnexpectedException$66;
   static final PyCode __init__$67;
   static final PyCode __str__$68;
   static final PyCode DebugRunner$69;
   static final PyCode run$70;
   static final PyCode report_unexpected_exception$71;
   static final PyCode report_failure$72;
   static final PyCode testmod$73;
   static final PyCode testfile$74;
   static final PyCode run_docstring_examples$75;
   static final PyCode Tester$76;
   static final PyCode __init__$77;
   static final PyCode runstring$78;
   static final PyCode rundoc$79;
   static final PyCode rundict$80;
   static final PyCode run__test__$81;
   static final PyCode summarize$82;
   static final PyCode merge$83;
   static final PyCode set_unittest_reportflags$84;
   static final PyCode DocTestCase$85;
   static final PyCode __init__$86;
   static final PyCode setUp$87;
   static final PyCode tearDown$88;
   static final PyCode runTest$89;
   static final PyCode format_failure$90;
   static final PyCode debug$91;
   static final PyCode id$92;
   static final PyCode __eq__$93;
   static final PyCode __ne__$94;
   static final PyCode __hash__$95;
   static final PyCode __repr__$96;
   static final PyCode shortDescription$97;
   static final PyCode SkipDocTestCase$98;
   static final PyCode __init__$99;
   static final PyCode setUp$100;
   static final PyCode test_skip$101;
   static final PyCode shortDescription$102;
   static final PyCode DocTestSuite$103;
   static final PyCode DocFileCase$104;
   static final PyCode id$105;
   static final PyCode __repr__$106;
   static final PyCode format_failure$107;
   static final PyCode DocFileTest$108;
   static final PyCode DocFileSuite$109;
   static final PyCode script_from_examples$110;
   static final PyCode testsource$111;
   static final PyCode debug_src$112;
   static final PyCode debug_script$113;
   static final PyCode debug$114;
   static final PyCode _TestClass$115;
   static final PyCode __init__$116;
   static final PyCode square$117;
   static final PyCode get$118;
   static final PyCode _test$119;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Module doctest -- a framework for running examples in docstrings.\n\nIn simplest use, end each module M to be tested with:\n\ndef _test():\n    import doctest\n    doctest.testmod()\n\nif __name__ == \"__main__\":\n    _test()\n\nThen running the module as a script will cause the examples in the\ndocstrings to get executed and verified:\n\npython M.py\n\nThis won't display anything unless an example fails, in which case the\nfailing example(s) and the cause(s) of the failure(s) are printed to stdout\n(why not stderr? because stderr is a lame hack <0.2 wink>), and the final\nline of output is \"Test failed.\".\n\nRun it with the -v switch instead:\n\npython M.py -v\n\nand a detailed report of all examples tried is printed to stdout, along\nwith assorted summaries at the end.\n\nYou can force verbose mode by passing \"verbose=True\" to testmod, or prohibit\nit by passing \"verbose=False\".  In either of those cases, sys.argv is not\nexamined by testmod.\n\nThere are a variety of other ways to run doctests, including integration\nwith the unittest framework, and support for running non-Python text\nfiles containing doctests.  There are also many ways to override parts\nof doctest's default behaviors.  See the Library Reference Manual for\ndetails.\n"));
      var1.setline(46);
      PyString.fromInterned("Module doctest -- a framework for running examples in docstrings.\n\nIn simplest use, end each module M to be tested with:\n\ndef _test():\n    import doctest\n    doctest.testmod()\n\nif __name__ == \"__main__\":\n    _test()\n\nThen running the module as a script will cause the examples in the\ndocstrings to get executed and verified:\n\npython M.py\n\nThis won't display anything unless an example fails, in which case the\nfailing example(s) and the cause(s) of the failure(s) are printed to stdout\n(why not stderr? because stderr is a lame hack <0.2 wink>), and the final\nline of output is \"Test failed.\".\n\nRun it with the -v switch instead:\n\npython M.py -v\n\nand a detailed report of all examples tried is printed to stdout, along\nwith assorted summaries at the end.\n\nYou can force verbose mode by passing \"verbose=True\" to testmod, or prohibit\nit by passing \"verbose=False\".  In either of those cases, sys.argv is not\nexamined by testmod.\n\nThere are a variety of other ways to run doctests, including integration\nwith the unittest framework, and support for running non-Python text\nfiles containing doctests.  There are also many ways to override parts\nof doctest's default behaviors.  See the Library Reference Manual for\ndetails.\n");
      var1.setline(48);
      PyString var3 = PyString.fromInterned("reStructuredText en");
      var1.setlocal("__docformat__", var3);
      var3 = null;
      var1.setline(50);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("register_optionflag"), PyString.fromInterned("DONT_ACCEPT_TRUE_FOR_1"), PyString.fromInterned("DONT_ACCEPT_BLANKLINE"), PyString.fromInterned("NORMALIZE_WHITESPACE"), PyString.fromInterned("ELLIPSIS"), PyString.fromInterned("SKIP"), PyString.fromInterned("IGNORE_EXCEPTION_DETAIL"), PyString.fromInterned("COMPARISON_FLAGS"), PyString.fromInterned("REPORT_UDIFF"), PyString.fromInterned("REPORT_CDIFF"), PyString.fromInterned("REPORT_NDIFF"), PyString.fromInterned("REPORT_ONLY_FIRST_FAILURE"), PyString.fromInterned("REPORTING_FLAGS"), PyString.fromInterned("Example"), PyString.fromInterned("DocTest"), PyString.fromInterned("DocTestParser"), PyString.fromInterned("DocTestFinder"), PyString.fromInterned("DocTestRunner"), PyString.fromInterned("OutputChecker"), PyString.fromInterned("DocTestFailure"), PyString.fromInterned("UnexpectedException"), PyString.fromInterned("DebugRunner"), PyString.fromInterned("testmod"), PyString.fromInterned("testfile"), PyString.fromInterned("run_docstring_examples"), PyString.fromInterned("Tester"), PyString.fromInterned("DocTestSuite"), PyString.fromInterned("DocFileSuite"), PyString.fromInterned("set_unittest_reportflags"), PyString.fromInterned("script_from_examples"), PyString.fromInterned("testsource"), PyString.fromInterned("debug_src"), PyString.fromInterned("debug")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(96);
      PyObject var6 = imp.importOne("__future__", var1, -1);
      var1.setlocal("__future__", var6);
      var3 = null;
      var1.setline(98);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var6 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var6);
      var3 = null;
      var6 = imp.importOne("inspect", var1, -1);
      var1.setlocal("inspect", var6);
      var3 = null;
      var6 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var6);
      var3 = null;
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(99);
      var6 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var6);
      var3 = null;
      var6 = imp.importOne("difflib", var1, -1);
      var1.setlocal("difflib", var6);
      var3 = null;
      var6 = imp.importOne("pdb", var1, -1);
      var1.setlocal("pdb", var6);
      var3 = null;
      var6 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var6);
      var3 = null;
      var1.setline(100);
      var6 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var6);
      var3 = null;
      var1.setline(101);
      String[] var7 = new String[]{"StringIO"};
      PyObject[] var8 = imp.importFrom("StringIO", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(102);
      var7 = new String[]{"namedtuple"};
      var8 = imp.importFrom("collections", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var1.setline(104);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestResults"), (PyObject)PyString.fromInterned("failed attempted"));
      var1.setlocal("TestResults", var6);
      var3 = null;
      var1.setline(127);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("OPTIONFLAGS_BY_NAME", var9);
      var3 = null;
      var1.setline(128);
      var8 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var8, register_optionflag$1, (PyObject)null);
      var1.setlocal("register_optionflag", var10);
      var3 = null;
      var1.setline(132);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DONT_ACCEPT_TRUE_FOR_1"));
      var1.setlocal("DONT_ACCEPT_TRUE_FOR_1", var6);
      var3 = null;
      var1.setline(133);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DONT_ACCEPT_BLANKLINE"));
      var1.setlocal("DONT_ACCEPT_BLANKLINE", var6);
      var3 = null;
      var1.setline(134);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NORMALIZE_WHITESPACE"));
      var1.setlocal("NORMALIZE_WHITESPACE", var6);
      var3 = null;
      var1.setline(135);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ELLIPSIS"));
      var1.setlocal("ELLIPSIS", var6);
      var3 = null;
      var1.setline(136);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SKIP"));
      var1.setlocal("SKIP", var6);
      var3 = null;
      var1.setline(137);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IGNORE_EXCEPTION_DETAIL"));
      var1.setlocal("IGNORE_EXCEPTION_DETAIL", var6);
      var3 = null;
      var1.setline(139);
      var6 = var1.getname("DONT_ACCEPT_TRUE_FOR_1")._or(var1.getname("DONT_ACCEPT_BLANKLINE"))._or(var1.getname("NORMALIZE_WHITESPACE"))._or(var1.getname("ELLIPSIS"))._or(var1.getname("SKIP"))._or(var1.getname("IGNORE_EXCEPTION_DETAIL"));
      var1.setlocal("COMPARISON_FLAGS", var6);
      var3 = null;
      var1.setline(146);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("REPORT_UDIFF"));
      var1.setlocal("REPORT_UDIFF", var6);
      var3 = null;
      var1.setline(147);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("REPORT_CDIFF"));
      var1.setlocal("REPORT_CDIFF", var6);
      var3 = null;
      var1.setline(148);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("REPORT_NDIFF"));
      var1.setlocal("REPORT_NDIFF", var6);
      var3 = null;
      var1.setline(149);
      var6 = var1.getname("register_optionflag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("REPORT_ONLY_FIRST_FAILURE"));
      var1.setlocal("REPORT_ONLY_FIRST_FAILURE", var6);
      var3 = null;
      var1.setline(151);
      var6 = var1.getname("REPORT_UDIFF")._or(var1.getname("REPORT_CDIFF"))._or(var1.getname("REPORT_NDIFF"))._or(var1.getname("REPORT_ONLY_FIRST_FAILURE"));
      var1.setlocal("REPORTING_FLAGS", var6);
      var3 = null;
      var1.setline(157);
      var3 = PyString.fromInterned("<BLANKLINE>");
      var1.setlocal("BLANKLINE_MARKER", var3);
      var3 = null;
      var1.setline(158);
      var3 = PyString.fromInterned("...");
      var1.setlocal("ELLIPSIS_MARKER", var3);
      var3 = null;
      var1.setline(178);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _extract_future_flags$2, PyString.fromInterned("\n    Return the compiler-flags associated with the future features that\n    have been imported into the given namespace (globs).\n    "));
      var1.setlocal("_extract_future_flags", var10);
      var3 = null;
      var1.setline(190);
      var8 = new PyObject[]{Py.newInteger(2)};
      var10 = new PyFunction(var1.f_globals, var8, _normalize_module$3, PyString.fromInterned("\n    Return the module specified by `module`.  In particular:\n      - If `module` is a module, then return module.\n      - If `module` is a string, then import and return the\n        module with that name.\n      - If `module` is None, then return the calling module.\n        The calling module is assumed to be the module of\n        the stack frame at the given depth in the call stack.\n    "));
      var1.setlocal("_normalize_module", var10);
      var3 = null;
      var1.setline(209);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _load_testfile$4, (PyObject)null);
      var1.setlocal("_load_testfile", var10);
      var3 = null;
      var1.setline(223);
      Object var10000 = var1.getname("getattr").__call__((ThreadState)var2, var1.getname("sys").__getattr__("__stdout__"), (PyObject)PyString.fromInterned("encoding"), (PyObject)var1.getname("None"));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("utf-8");
      }

      Object var11 = var10000;
      var1.setlocal("_encoding", (PyObject)var11);
      var3 = null;
      var1.setline(225);
      var8 = new PyObject[]{Py.newInteger(4)};
      var10 = new PyFunction(var1.f_globals, var8, _indent$5, PyString.fromInterned("\n    Add the given number of space characters to the beginning of\n    every non-blank line in `s`, and return the result.\n    If the string `s` is Unicode, it is encoded using the stdout\n    encoding and the `backslashreplace` error handler.\n    "));
      var1.setlocal("_indent", var10);
      var3 = null;
      var1.setline(237);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _exception_traceback$6, PyString.fromInterned("\n    Return a string containing a traceback message for the given\n    exc_info tuple (as returned by sys.exc_info()).\n    "));
      var1.setlocal("_exception_traceback", var10);
      var3 = null;
      var1.setline(249);
      var8 = new PyObject[]{var1.getname("StringIO")};
      var4 = Py.makeClass("_SpoofOut", var8, _SpoofOut$7);
      var1.setlocal("_SpoofOut", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(272);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _ellipsis_match$10, PyString.fromInterned("\n    Essentially the only subtle case:\n    >>> _ellipsis_match('aa...aa', 'aaa')\n    False\n    "));
      var1.setlocal("_ellipsis_match", var10);
      var3 = null;
      var1.setline(321);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _comment_line$11, PyString.fromInterned("Return a commented form of the given line"));
      var1.setlocal("_comment_line", var10);
      var3 = null;
      var1.setline(329);
      var8 = new PyObject[]{var1.getname("pdb").__getattr__("Pdb")};
      var4 = Py.makeClass("_OutputRedirectingPdb", var8, _OutputRedirectingPdb$12);
      var1.setlocal("_OutputRedirectingPdb", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(365);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _module_relative_path$17, (PyObject)null);
      var1.setlocal("_module_relative_path", var10);
      var3 = null;
      var1.setline(401);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Example", var8, Example$18);
      var1.setlocal("Example", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(473);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("DocTest", var8, DocTest$23);
      var1.setlocal("DocTest", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(549);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("DocTestParser", var8, DocTestParser$30);
      var1.setlocal("DocTestParser", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(784);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("DocTestFinder", var8, DocTestFinder$39);
      var1.setlocal("DocTestFinder", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1082);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("DocTestRunner", var8, DocTestRunner$46);
      var1.setlocal("DocTestRunner", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1513);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("OutputChecker", var8, OutputChecker$59);
      var1.setlocal("OutputChecker", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1646);
      var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("DocTestFailure", var8, DocTestFailure$63);
      var1.setlocal("DocTestFailure", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1665);
      var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("UnexpectedException", var8, UnexpectedException$66);
      var1.setlocal("UnexpectedException", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1684);
      var8 = new PyObject[]{var1.getname("DocTestRunner")};
      var4 = Py.makeClass("DebugRunner", var8, DebugRunner$69);
      var1.setlocal("DebugRunner", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1796);
      var6 = var1.getname("None");
      var1.setlocal("master", var6);
      var3 = null;
      var1.setline(1798);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True"), Py.newInteger(0), var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var10 = new PyFunction(var1.f_globals, var8, testmod$73, PyString.fromInterned("m=None, name=None, globs=None, verbose=None, report=True,\n       optionflags=0, extraglobs=None, raise_on_error=False,\n       exclude_empty=False\n\n    Test examples in docstrings in functions and classes reachable\n    from module m (or the current module if m is not supplied), starting\n    with m.__doc__.\n\n    Also test examples reachable from dict m.__test__ if it exists and is\n    not None.  m.__test__ maps names to functions, classes and strings;\n    function and class docstrings are tested even if the name is private;\n    strings are tested directly, as if they were docstrings.\n\n    Return (#failures, #tests).\n\n    See help(doctest) for an overview.\n\n    Optional keyword arg \"name\" gives the name of the module; by default\n    use m.__name__.\n\n    Optional keyword arg \"globs\" gives a dict to be used as the globals\n    when executing examples; by default, use m.__dict__.  A copy of this\n    dict is actually used for each docstring, so that each docstring's\n    examples start with a clean slate.\n\n    Optional keyword arg \"extraglobs\" gives a dictionary that should be\n    merged into the globals that are used to execute examples.  By\n    default, no extra globals are used.  This is new in 2.4.\n\n    Optional keyword arg \"verbose\" prints lots of stuff if true, prints\n    only failures if false; by default, it's true iff \"-v\" is in sys.argv.\n\n    Optional keyword arg \"report\" prints a summary at the end when true,\n    else prints nothing at the end.  In verbose mode, the summary is\n    detailed, else very brief (in fact, empty if all tests passed).\n\n    Optional keyword arg \"optionflags\" or's together module constants,\n    and defaults to 0.  This is new in 2.3.  Possible values (see the\n    docs for details):\n\n        DONT_ACCEPT_TRUE_FOR_1\n        DONT_ACCEPT_BLANKLINE\n        NORMALIZE_WHITESPACE\n        ELLIPSIS\n        SKIP\n        IGNORE_EXCEPTION_DETAIL\n        REPORT_UDIFF\n        REPORT_CDIFF\n        REPORT_NDIFF\n        REPORT_ONLY_FIRST_FAILURE\n\n    Optional keyword arg \"raise_on_error\" raises an exception on the\n    first unexpected exception or failure. This allows failures to be\n    post-mortem debugged.\n\n    Advanced tomfoolery:  testmod runs methods of a local instance of\n    class doctest.Tester, then merges the results into (or creates)\n    global Tester instance doctest.master.  Methods of doctest.master\n    can be called directly too, if you want to do something unusual.\n    Passing report=0 to testmod is especially useful then, to delay\n    displaying a summary.  Invoke doctest.master.summarize(verbose)\n    when you're done fiddling.\n    "));
      var1.setlocal("testmod", var10);
      var3 = null;
      var1.setline(1902);
      var8 = new PyObject[]{var1.getname("True"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True"), Py.newInteger(0), var1.getname("None"), var1.getname("False"), var1.getname("DocTestParser").__call__(var2), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var8, testfile$74, PyString.fromInterned("\n    Test examples in the given file.  Return (#failures, #tests).\n\n    Optional keyword arg \"module_relative\" specifies how filenames\n    should be interpreted:\n\n      - If \"module_relative\" is True (the default), then \"filename\"\n         specifies a module-relative path.  By default, this path is\n         relative to the calling module's directory; but if the\n         \"package\" argument is specified, then it is relative to that\n         package.  To ensure os-independence, \"filename\" should use\n         \"/\" characters to separate path segments, and should not\n         be an absolute path (i.e., it may not begin with \"/\").\n\n      - If \"module_relative\" is False, then \"filename\" specifies an\n        os-specific path.  The path may be absolute or relative (to\n        the current working directory).\n\n    Optional keyword arg \"name\" gives the name of the test; by default\n    use the file's basename.\n\n    Optional keyword argument \"package\" is a Python package or the\n    name of a Python package whose directory should be used as the\n    base directory for a module relative filename.  If no package is\n    specified, then the calling module's directory is used as the base\n    directory for module relative filenames.  It is an error to\n    specify \"package\" if \"module_relative\" is False.\n\n    Optional keyword arg \"globs\" gives a dict to be used as the globals\n    when executing examples; by default, use {}.  A copy of this dict\n    is actually used for each docstring, so that each docstring's\n    examples start with a clean slate.\n\n    Optional keyword arg \"extraglobs\" gives a dictionary that should be\n    merged into the globals that are used to execute examples.  By\n    default, no extra globals are used.\n\n    Optional keyword arg \"verbose\" prints lots of stuff if true, prints\n    only failures if false; by default, it's true iff \"-v\" is in sys.argv.\n\n    Optional keyword arg \"report\" prints a summary at the end when true,\n    else prints nothing at the end.  In verbose mode, the summary is\n    detailed, else very brief (in fact, empty if all tests passed).\n\n    Optional keyword arg \"optionflags\" or's together module constants,\n    and defaults to 0.  Possible values (see the docs for details):\n\n        DONT_ACCEPT_TRUE_FOR_1\n        DONT_ACCEPT_BLANKLINE\n        NORMALIZE_WHITESPACE\n        ELLIPSIS\n        SKIP\n        IGNORE_EXCEPTION_DETAIL\n        REPORT_UDIFF\n        REPORT_CDIFF\n        REPORT_NDIFF\n        REPORT_ONLY_FIRST_FAILURE\n\n    Optional keyword arg \"raise_on_error\" raises an exception on the\n    first unexpected exception or failure. This allows failures to be\n    post-mortem debugged.\n\n    Optional keyword arg \"parser\" specifies a DocTestParser (or\n    subclass) that should be used to extract tests from the files.\n\n    Optional keyword arg \"encoding\" specifies an encoding that should\n    be used to convert the file to unicode.\n\n    Advanced tomfoolery:  testmod runs methods of a local instance of\n    class doctest.Tester, then merges the results into (or creates)\n    global Tester instance doctest.master.  Methods of doctest.master\n    can be called directly too, if you want to do something unusual.\n    Passing report=0 to testmod is especially useful then, to delay\n    displaying a summary.  Invoke doctest.master.summarize(verbose)\n    when you're done fiddling.\n    "));
      var1.setlocal("testfile", var10);
      var3 = null;
      var1.setline(2027);
      var8 = new PyObject[]{var1.getname("False"), PyString.fromInterned("NoName"), var1.getname("None"), Py.newInteger(0)};
      var10 = new PyFunction(var1.f_globals, var8, run_docstring_examples$75, PyString.fromInterned("\n    Test examples in the given object's docstring (`f`), using `globs`\n    as globals.  Optional argument `name` is used in failure messages.\n    If the optional argument `verbose` is true, then generate output\n    even if there are no failures.\n\n    `compileflags` gives the set of flags that should be used by the\n    Python compiler when running the examples.  If not specified, then\n    it will default to the set of future-import flags that apply to\n    `globs`.\n\n    Optional keyword arg `optionflags` specifies options for the\n    testing and output.  See the documentation for `testmod` for more\n    information.\n    "));
      var1.setlocal("run_docstring_examples", var10);
      var3 = null;
      var1.setline(2056);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Tester", var8, Tester$76);
      var1.setlocal("Tester", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(2119);
      PyInteger var12 = Py.newInteger(0);
      var1.setlocal("_unittest_reportflags", var12);
      var3 = null;
      var1.setline(2121);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, set_unittest_reportflags$84, PyString.fromInterned("Sets the unittest option flags.\n\n    The old flag is returned so that a runner could restore the old\n    value if it wished to:\n\n      >>> import doctest\n      >>> old = doctest._unittest_reportflags\n      >>> doctest.set_unittest_reportflags(REPORT_NDIFF |\n      ...                          REPORT_ONLY_FIRST_FAILURE) == old\n      True\n\n      >>> doctest._unittest_reportflags == (REPORT_NDIFF |\n      ...                                   REPORT_ONLY_FIRST_FAILURE)\n      True\n\n    Only reporting flags can be set:\n\n      >>> doctest.set_unittest_reportflags(ELLIPSIS)\n      Traceback (most recent call last):\n      ...\n      ValueError: ('Only reporting flags allowed', 8)\n\n      >>> doctest.set_unittest_reportflags(old) == (REPORT_NDIFF |\n      ...                                   REPORT_ONLY_FIRST_FAILURE)\n      True\n    "));
      var1.setlocal("set_unittest_reportflags", var10);
      var3 = null;
      var1.setline(2157);
      var8 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("DocTestCase", var8, DocTestCase$85);
      var1.setlocal("DocTestCase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(2320);
      var8 = new PyObject[]{var1.getname("DocTestCase")};
      var4 = Py.makeClass("SkipDocTestCase", var8, SkipDocTestCase$98);
      var1.setlocal("SkipDocTestCase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(2337);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var8, DocTestSuite$103, PyString.fromInterned("\n    Convert doctest tests for a module to a unittest test suite.\n\n    This converts each documentation string in a module that\n    contains doctest tests to a unittest test case.  If any of the\n    tests in a doc string fail, then the test case fails.  An exception\n    is raised showing the name of the file containing the test and a\n    (sometimes approximate) line number.\n\n    The `module` argument provides the module to be tested.  The argument\n    can be either a module or a module name.\n\n    If no argument is given, the calling module is used.\n\n    A number of options may be provided as keyword arguments:\n\n    setUp\n      A set-up function.  This is called before running the\n      tests in each file. The setUp function will be passed a DocTest\n      object.  The setUp function can access the test globals as the\n      globs attribute of the test passed.\n\n    tearDown\n      A tear-down function.  This is called after running the\n      tests in each file.  The tearDown function will be passed a DocTest\n      object.  The tearDown function can access the test globals as the\n      globs attribute of the test passed.\n\n    globs\n      A dictionary containing initial global variables for the tests.\n\n    optionflags\n       A set of doctest option flags expressed as an integer.\n    "));
      var1.setlocal("DocTestSuite", var10);
      var3 = null;
      var1.setline(2412);
      var8 = new PyObject[]{var1.getname("DocTestCase")};
      var4 = Py.makeClass("DocFileCase", var8, DocFileCase$104);
      var1.setlocal("DocFileCase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(2426);
      var8 = new PyObject[]{var1.getname("True"), var1.getname("None"), var1.getname("None"), var1.getname("DocTestParser").__call__(var2), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var8, DocFileTest$108, (PyObject)null);
      var1.setlocal("DocFileTest", var10);
      var3 = null;
      var1.setline(2455);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, DocFileSuite$109, PyString.fromInterned("A unittest suite for one or more doctest files.\n\n    The path to each doctest file is given as a string; the\n    interpretation of that string depends on the keyword argument\n    \"module_relative\".\n\n    A number of options may be provided as keyword arguments:\n\n    module_relative\n      If \"module_relative\" is True, then the given file paths are\n      interpreted as os-independent module-relative paths.  By\n      default, these paths are relative to the calling module's\n      directory; but if the \"package\" argument is specified, then\n      they are relative to that package.  To ensure os-independence,\n      \"filename\" should use \"/\" characters to separate path\n      segments, and may not be an absolute path (i.e., it may not\n      begin with \"/\").\n\n      If \"module_relative\" is False, then the given file paths are\n      interpreted as os-specific paths.  These paths may be absolute\n      or relative (to the current working directory).\n\n    package\n      A Python package or the name of a Python package whose directory\n      should be used as the base directory for module relative paths.\n      If \"package\" is not specified, then the calling module's\n      directory is used as the base directory for module relative\n      filenames.  It is an error to specify \"package\" if\n      \"module_relative\" is False.\n\n    setUp\n      A set-up function.  This is called before running the\n      tests in each file. The setUp function will be passed a DocTest\n      object.  The setUp function can access the test globals as the\n      globs attribute of the test passed.\n\n    tearDown\n      A tear-down function.  This is called after running the\n      tests in each file.  The tearDown function will be passed a DocTest\n      object.  The tearDown function can access the test globals as the\n      globs attribute of the test passed.\n\n    globs\n      A dictionary containing initial global variables for the tests.\n\n    optionflags\n      A set of doctest option flags expressed as an integer.\n\n    parser\n      A DocTestParser (or subclass) that should be used to extract\n      tests from the files.\n\n    encoding\n      An encoding that will be used to convert the files to unicode.\n    "));
      var1.setlocal("DocFileSuite", var10);
      var3 = null;
      var1.setline(2528);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, script_from_examples$110, PyString.fromInterned("Extract script from text with examples.\n\n       Converts text with examples to a Python script.  Example input is\n       converted to regular code.  Example output and all other words\n       are converted to comments:\n\n       >>> text = '''\n       ...       Here are examples of simple math.\n       ...\n       ...           Python has super accurate integer addition\n       ...\n       ...           >>> 2 + 2\n       ...           5\n       ...\n       ...           And very friendly error messages:\n       ...\n       ...           >>> 1/0\n       ...           To Infinity\n       ...           And\n       ...           Beyond\n       ...\n       ...           You can use logic if you want:\n       ...\n       ...           >>> if 0:\n       ...           ...    blah\n       ...           ...    blah\n       ...           ...\n       ...\n       ...           Ho hum\n       ...           '''\n\n       >>> print script_from_examples(text)\n       # Here are examples of simple math.\n       #\n       #     Python has super accurate integer addition\n       #\n       2 + 2\n       # Expected:\n       ## 5\n       #\n       #     And very friendly error messages:\n       #\n       1/0\n       # Expected:\n       ## To Infinity\n       ## And\n       ## Beyond\n       #\n       #     You can use logic if you want:\n       #\n       if 0:\n          blah\n          blah\n       #\n       #     Ho hum\n       <BLANKLINE>\n       "));
      var1.setlocal("script_from_examples", var10);
      var3 = null;
      var1.setline(2610);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, testsource$111, PyString.fromInterned("Extract the test sources from a doctest docstring as a script.\n\n    Provide the module (or dotted name of the module) containing the\n    test to be debugged and the name (within the module) of the object\n    with the doc string with tests to be debugged.\n    "));
      var1.setlocal("testsource", var10);
      var3 = null;
      var1.setline(2626);
      var8 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var8, debug_src$112, PyString.fromInterned("Debug a single doctest docstring, in argument `src`'"));
      var1.setlocal("debug_src", var10);
      var3 = null;
      var1.setline(2631);
      var8 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var8, debug_script$113, PyString.fromInterned("Debug a test script.  `src` is the script, as a string."));
      var1.setlocal("debug_script", var10);
      var3 = null;
      var1.setline(2663);
      var8 = new PyObject[]{var1.getname("False")};
      var10 = new PyFunction(var1.f_globals, var8, debug$114, PyString.fromInterned("Debug a single doctest docstring.\n\n    Provide the module (or dotted name of the module) containing the\n    test to be debugged and the name (within the module) of the object\n    with the docstring with tests to be debugged.\n    "));
      var1.setlocal("debug", var10);
      var3 = null;
      var1.setline(2677);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("_TestClass", var8, _TestClass$115);
      var1.setlocal("_TestClass", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(2721);
      var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("_TestClass"), var1.getname("_TestClass"), PyString.fromInterned("string"), PyString.fromInterned("\n                      Example of a string object, searched as-is.\n                      >>> x = 1; y = 2\n                      >>> x + y, x * y\n                      (3, 2)\n                      "), PyString.fromInterned("bool-int equivalence"), PyString.fromInterned("\n                                    In 2.2, boolean expressions displayed\n                                    0 or 1.  By default, we still accept\n                                    them.  This can be disabled by passing\n                                    DONT_ACCEPT_TRUE_FOR_1 to the new\n                                    optionflags argument.\n                                    >>> 4 == 4\n                                    1\n                                    >>> 4 == 4\n                                    True\n                                    >>> 4 > 4\n                                    0\n                                    >>> 4 > 4\n                                    False\n                                    "), PyString.fromInterned("blank lines"), PyString.fromInterned("\n                Blank lines can be marked with <BLANKLINE>:\n                    >>> print 'foo\\n\\nbar\\n'\n                    foo\n                    <BLANKLINE>\n                    bar\n                    <BLANKLINE>\n            "), PyString.fromInterned("ellipsis"), PyString.fromInterned("\n                If the ellipsis flag is used, then '...' can be used to\n                elide substrings in the desired output:\n                    >>> print range(1000) #doctest: +ELLIPSIS\n                    [0, 1, 2, ..., 999]\n            "), PyString.fromInterned("whitespace normalization"), PyString.fromInterned("\n                If the whitespace normalization flag is used, then\n                differences in whitespace are ignored.\n                    >>> print range(30) #doctest: +NORMALIZE_WHITESPACE\n                    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,\n                     15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26,\n                     27, 28, 29]\n            ")});
      var1.setlocal("__test__", var9);
      var3 = null;
      var1.setline(2772);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, _test$119, (PyObject)null);
      var1.setlocal("_test", var10);
      var3 = null;
      var1.setline(2797);
      var6 = var1.getname("__name__");
      PyObject var13 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var13.__nonzero__()) {
         var1.setline(2798);
         var1.getname("sys").__getattr__("exit").__call__(var2, var1.getname("_test").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register_optionflag$1(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getglobal("OPTIONFLAGS_BY_NAME").__getattr__("setdefault").__call__(var2, var1.getlocal(0), Py.newInteger(1)._lshift(var1.getglobal("len").__call__(var2, var1.getglobal("OPTIONFLAGS_BY_NAME"))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _extract_future_flags$2(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyString.fromInterned("\n    Return the compiler-flags associated with the future features that\n    have been imported into the given namespace (globs).\n    ");
      var1.setline(183);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(184);
      PyObject var6 = var1.getglobal("__future__").__getattr__("all_feature_names").__iter__();

      while(true) {
         var1.setline(184);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(188);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(185);
         PyObject var5 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(2), var1.getglobal("None"));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(186);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._is(var1.getglobal("getattr").__call__(var2, var1.getglobal("__future__"), var1.getlocal(2)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(187);
            var5 = var1.getlocal(1);
            var5 = var5._ior(var1.getlocal(3).__getattr__("compiler_flag"));
            var1.setlocal(1, var5);
         }
      }
   }

   public PyObject _normalize_module$3(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyString.fromInterned("\n    Return the module specified by `module`.  In particular:\n      - If `module` is a module, then return module.\n      - If `module` is a string, then import and return the\n        module with that name.\n      - If `module` is None, then return the calling module.\n        The calling module is assumed to be the module of\n        the stack frame at the given depth in the call stack.\n    ");
      var1.setline(200);
      PyObject var3;
      if (var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(201);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(202);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__nonzero__()) {
            var1.setline(203);
            var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(0), var1.getglobal("globals").__call__(var2), var1.getglobal("locals").__call__(var2), new PyList(new PyObject[]{PyString.fromInterned("*")}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(204);
            PyObject var4 = var1.getlocal(0);
            PyObject var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(205);
               var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getglobal("sys").__getattr__("_getframe").__call__(var2, var1.getlocal(1)).__getattr__("f_globals").__getitem__(PyString.fromInterned("__name__")));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(207);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Expected a module, string, or None")));
            }
         }
      }
   }

   public PyObject _load_testfile$4(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(210);
      PyTuple var8;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(211);
         PyObject var3 = var1.getglobal("_normalize_module").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(3));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(212);
         var3 = var1.getglobal("_module_relative_path").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(213);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__loader__")).__nonzero__()) {
            var1.setline(214);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("__loader__"), (PyObject)PyString.fromInterned("get_data")).__nonzero__()) {
               var1.setline(215);
               var3 = var1.getlocal(1).__getattr__("__loader__").__getattr__("get_data").__call__(var2, var1.getlocal(0));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(218);
               var8 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n")), var1.getlocal(0)});
               var1.f_lasti = -1;
               return var8;
            }
         }
      }

      ContextManager var4;
      PyObject var5 = (var4 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(0)))).__enter__(var2);

      Throwable var10000;
      label38: {
         boolean var10001;
         try {
            var1.setlocal(4, var5);
            var1.setline(220);
            var8 = new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("read").__call__(var2), var1.getlocal(0)});
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label38;
         }

         var4.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var8;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      if (!var4.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _indent$5(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyString.fromInterned("\n    Add the given number of space characters to the beginning of\n    every non-blank line in `s`, and return the result.\n    If the string `s` is Unicode, it is encoded using the stdout\n    encoding and the `backslashreplace` error handler.\n    ");
      var1.setline(232);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(233);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getglobal("_encoding"), (PyObject)PyString.fromInterned("backslashreplace"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(235);
      var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?m)^(?!$)"), (PyObject)var1.getlocal(1)._mul(PyString.fromInterned(" ")), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _exception_traceback$6(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyString.fromInterned("\n    Return a string containing a traceback message for the given\n    exc_info tuple (as returned by sys.exc_info()).\n    ");
      var1.setline(243);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(244);
      var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(245);
      PyObject var10000 = var1.getglobal("traceback").__getattr__("print_exception");
      PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1)};
      String[] var7 = new String[]{"file"};
      var10000.__call__(var2, var6, var7);
      var3 = null;
      var1.setline(246);
      var3 = var1.getlocal(1).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _SpoofOut$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(250);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, getvalue$8, (PyObject)null);
      var1.setlocal("getvalue", var4);
      var3 = null;
      var1.setline(263);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, truncate$9, (PyObject)null);
      var1.setlocal("truncate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getvalue$8(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getglobal("StringIO").__getattr__("getvalue").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(255);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(256);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(PyString.fromInterned("\n"));
         var1.setlocal(1, var3);
      }

      var1.setline(259);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("softspace")).__nonzero__()) {
         var1.setline(260);
         var1.getlocal(0).__delattr__("softspace");
      }

      var1.setline(261);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject truncate$9(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      var1.getglobal("StringIO").__getattr__("truncate").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(265);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("softspace")).__nonzero__()) {
         var1.setline(266);
         var1.getlocal(0).__delattr__("softspace");
      }

      var1.setline(267);
      if (var1.getlocal(0).__getattr__("buf").__not__().__nonzero__()) {
         var1.setline(269);
         PyString var3 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"buf", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ellipsis_match$10(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("\n    Essentially the only subtle case:\n    >>> _ellipsis_match('aa...aa', 'aaa')\n    False\n    ");
      var1.setline(278);
      PyObject var3 = var1.getglobal("ELLIPSIS_MARKER");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(279);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(282);
         PyObject var4 = var1.getlocal(0).__getattr__("split").__call__(var2, var1.getglobal("ELLIPSIS_MARKER"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(283);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var4._ge(Py.newInteger(2));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(286);
         PyTuple var7 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
         PyObject[] var5 = Py.unpackSequence(var7, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(287);
         var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(288);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(289);
            if (!var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(293);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(290);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(291);
            var1.getlocal(2).__delitem__((PyObject)Py.newInteger(0));
         }

         var1.setline(294);
         var4 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(295);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(296);
            if (!var1.getlocal(1).__getattr__("endswith").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(300);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(297);
            var4 = var1.getlocal(4);
            var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
            var1.setlocal(4, var4);
            var1.setline(298);
            var1.getlocal(2).__delitem__((PyObject)Py.newInteger(-1));
         }

         var1.setline(302);
         var4 = var1.getlocal(3);
         var10000 = var4._gt(var1.getlocal(4));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(305);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(310);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(310);
               PyObject var8 = var4.__iternext__();
               if (var8 == null) {
                  var1.setline(319);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(5, var8);
               var1.setline(314);
               var6 = var1.getlocal(1).__getattr__("find").__call__(var2, var1.getlocal(5), var1.getlocal(3), var1.getlocal(4));
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(315);
               var6 = var1.getlocal(3);
               var10000 = var6._lt(Py.newInteger(0));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(316);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(317);
               var6 = var1.getlocal(3);
               var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
               var1.setlocal(3, var6);
            }
         }
      }
   }

   public PyObject _comment_line$11(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("Return a commented form of the given line");
      var1.setline(323);
      PyObject var3 = var1.getlocal(0).__getattr__("rstrip").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(324);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(325);
         var3 = PyString.fromInterned("# ")._add(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(327);
         PyString var4 = PyString.fromInterned("#");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _OutputRedirectingPdb$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A specialized version of the python debugger that redirects stdout\n    to a given stream when interacting with the user.  Stdout is *not*\n    redirected when traced code is executed.\n    "));
      var1.setline(334);
      PyString.fromInterned("\n    A specialized version of the python debugger that redirects stdout\n    to a given stream when interacting with the user.  Stdout is *not*\n    redirected when traced code is executed.\n    ");
      var1.setline(335);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(342);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, set_trace$14, (PyObject)null);
      var1.setlocal("set_trace", var4);
      var3 = null;
      var1.setline(348);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_continue$15, (PyObject)null);
      var1.setlocal("set_continue", var4);
      var3 = null;
      var1.setline(354);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, trace_dispatch$16, (PyObject)null);
      var1.setlocal("trace_dispatch", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_OutputRedirectingPdb__out", var3);
      var3 = null;
      var1.setline(337);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_OutputRedirectingPdb__debugger_used", var3);
      var3 = null;
      var1.setline(338);
      PyObject var10000 = var1.getglobal("pdb").__getattr__("Pdb").__getattr__("__init__");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1)};
      String[] var4 = new String[]{"stdout"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(340);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"use_rawinput", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_trace$14(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_OutputRedirectingPdb__debugger_used", var3);
      var3 = null;
      var1.setline(344);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(345);
         var3 = var1.getglobal("sys").__getattr__("_getframe").__call__(var2).__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(346);
      var1.getglobal("pdb").__getattr__("Pdb").__getattr__("set_trace").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_continue$15(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      if (var1.getlocal(0).__getattr__("_OutputRedirectingPdb__debugger_used").__nonzero__()) {
         var1.setline(352);
         var1.getglobal("pdb").__getattr__("Pdb").__getattr__("set_continue").__call__(var2, var1.getlocal(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject trace_dispatch$16(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(357);
      var3 = var1.getlocal(0).__getattr__("_OutputRedirectingPdb__out");
      var1.getglobal("sys").__setattr__("stdout", var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      String[] var5;
      PyObject var10;
      label25: {
         boolean var10001;
         PyObject var8;
         try {
            var1.setline(360);
            PyObject var11 = var1.getglobal("pdb").__getattr__("Pdb").__getattr__("trace_dispatch");
            PyObject[] var4 = new PyObject[]{var1.getlocal(0)};
            var5 = new String[0];
            var11 = var11._callextra(var4, var5, var1.getlocal(1), (PyObject)null);
            var4 = null;
            var8 = var11;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label25;
         }

         var1.setline(362);
         var10 = var1.getlocal(2);
         var1.getglobal("sys").__setattr__("stdout", var10);
         var5 = null;

         try {
            var1.f_lasti = -1;
            return var8;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      Throwable var9 = var10000;
      Py.addTraceback(var9, var1);
      var1.setline(362);
      var10 = var1.getlocal(2);
      var1.getglobal("sys").__setattr__("stdout", var10);
      var5 = null;
      throw (Throwable)var9;
   }

   public PyObject _module_relative_path$17(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      if (var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(367);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("Expected a module: %r")._mod(var1.getlocal(0)));
      } else {
         var1.setline(368);
         if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__nonzero__()) {
            var1.setline(369);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Module-relative files may not have absolute paths"));
         } else {
            var1.setline(372);
            PyObject var10000;
            PyObject var3;
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__file__")).__nonzero__()) {
               var1.setline(374);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("__file__")).__getitem__(Py.newInteger(0));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(375);
               var3 = var1.getlocal(0).__getattr__("__name__");
               var10000 = var3._eq(PyString.fromInterned("__main__"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(383);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Can't resolve paths relative to the module ")._add(var1.getlocal(0))._add(PyString.fromInterned(" (it has no __file__)"))));
               }

               var1.setline(377);
               var3 = var1.getglobal("len").__call__(var2, var1.getglobal("sys").__getattr__("argv"));
               var10000 = var3._gt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0));
                  var10000 = var3._ne(PyString.fromInterned(""));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(378);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0))).__getitem__(Py.newInteger(0));
                  var1.setlocal(2, var3);
                  var3 = null;
               } else {
                  var1.setline(380);
                  var3 = var1.getglobal("os").__getattr__("curdir");
                  var1.setlocal(2, var3);
                  var3 = null;
               }
            }

            var1.setline(387);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
            PyObject[] var5 = new PyObject[]{var1.getlocal(2)};
            String[] var4 = new String[0];
            var10000 = var10000._callextra(var5, var4, var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")), (PyObject)null);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject Example$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A single doctest example, consisting of source code and expected\n    output.  `Example` defines the following attributes:\n\n      - source: A single Python statement, always ending with a newline.\n        The constructor adds a newline if needed.\n\n      - want: The expected output from running the source code (either\n        from stdout, or a traceback in case of exception).  `want` ends\n        with a newline unless it's empty, in which case it's an empty\n        string.  The constructor adds a newline if needed.\n\n      - exc_msg: The exception message generated by the example, if\n        the example is expected to generate an exception; or `None` if\n        it is not expected to generate an exception.  This exception\n        message is compared against the return value of\n        `traceback.format_exception_only()`.  `exc_msg` ends with a\n        newline unless it's `None`.  The constructor adds a newline\n        if needed.\n\n      - lineno: The line number within the DocTest string containing\n        this Example where the Example begins.  This line number is\n        zero-based, with respect to the beginning of the DocTest.\n\n      - indent: The example's indentation in the DocTest string.\n        I.e., the number of space characters that preceed the\n        example's first prompt.\n\n      - options: A dictionary mapping from option flags to True or\n        False, which is used to override default options for this\n        example.  Any option flags not contained in this dictionary\n        are left at their default value (as specified by the\n        DocTestRunner's optionflags).  By default, no options are set.\n    "));
      var1.setline(435);
      PyString.fromInterned("\n    A single doctest example, consisting of source code and expected\n    output.  `Example` defines the following attributes:\n\n      - source: A single Python statement, always ending with a newline.\n        The constructor adds a newline if needed.\n\n      - want: The expected output from running the source code (either\n        from stdout, or a traceback in case of exception).  `want` ends\n        with a newline unless it's empty, in which case it's an empty\n        string.  The constructor adds a newline if needed.\n\n      - exc_msg: The exception message generated by the example, if\n        the example is expected to generate an exception; or `None` if\n        it is not expected to generate an exception.  This exception\n        message is compared against the return value of\n        `traceback.format_exception_only()`.  `exc_msg` ends with a\n        newline unless it's `None`.  The constructor adds a newline\n        if needed.\n\n      - lineno: The line number within the DocTest string containing\n        this Example where the Example begins.  This line number is\n        zero-based, with respect to the beginning of the DocTest.\n\n      - indent: The example's indentation in the DocTest string.\n        I.e., the number of space characters that preceed the\n        example's first prompt.\n\n      - options: A dictionary mapping from option flags to True or\n        False, which is used to override default options for this\n        example.  Any option flags not contained in this dictionary\n        are left at their default value (as specified by the\n        DocTestRunner's optionflags).  By default, no options are set.\n    ");
      var1.setline(436);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0), Py.newInteger(0), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(454);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$20, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$21, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(468);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$22, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
         var1.setline(440);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(PyString.fromInterned("\n"));
         var1.setlocal(1, var3);
      }

      var1.setline(441);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(442);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(PyString.fromInterned("\n"));
         var1.setlocal(2, var3);
      }

      var1.setline(443);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(444);
         var3 = var1.getlocal(3);
         var3 = var3._iadd(PyString.fromInterned("\n"));
         var1.setlocal(3, var3);
      }

      var1.setline(446);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("source", var3);
      var3 = null;
      var1.setline(447);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("want", var3);
      var3 = null;
      var1.setline(448);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(449);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("indent", var3);
      var3 = null;
      var1.setline(450);
      var3 = var1.getlocal(6);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(450);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(6, var4);
         var3 = null;
      }

      var1.setline(451);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("options", var3);
      var3 = null;
      var1.setline(452);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("exc_msg", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __eq__$20(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(456);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(458);
         PyObject var4 = var1.getlocal(0).__getattr__("source");
         var10000 = var4._eq(var1.getlocal(1).__getattr__("source"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("want");
            var10000 = var4._eq(var1.getlocal(1).__getattr__("want"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("lineno");
               var10000 = var4._eq(var1.getlocal(1).__getattr__("lineno"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("indent");
                  var10000 = var4._eq(var1.getlocal(1).__getattr__("indent"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("options");
                     var10000 = var4._eq(var1.getlocal(1).__getattr__("options"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(0).__getattr__("exc_msg");
                        var10000 = var4._eq(var1.getlocal(1).__getattr__("exc_msg"));
                        var4 = null;
                     }
                  }
               }
            }
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$21(PyFrame var1, ThreadState var2) {
      var1.setline(466);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$22(PyFrame var1, ThreadState var2) {
      var1.setline(469);
      PyObject var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("want"), var1.getlocal(0).__getattr__("lineno"), var1.getlocal(0).__getattr__("indent"), var1.getlocal(0).__getattr__("exc_msg")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DocTest$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A collection of doctest examples that should be run in a single\n    namespace.  Each `DocTest` defines the following attributes:\n\n      - examples: the list of examples.\n\n      - globs: The namespace (aka globals) that the examples should\n        be run in.\n\n      - name: A name identifying the DocTest (typically, the name of\n        the object whose docstring this DocTest was extracted from).\n\n      - filename: The name of the file that this DocTest was extracted\n        from, or `None` if the filename is unknown.\n\n      - lineno: The line number within filename where this DocTest\n        begins, or `None` if the line number is unavailable.  This\n        line number is zero-based, with respect to the beginning of\n        the file.\n\n      - docstring: The string that the examples were extracted from,\n        or `None` if the string is unavailable.\n    "));
      var1.setline(496);
      PyString.fromInterned("\n    A collection of doctest examples that should be run in a single\n    namespace.  Each `DocTest` defines the following attributes:\n\n      - examples: the list of examples.\n\n      - globs: The namespace (aka globals) that the examples should\n        be run in.\n\n      - name: A name identifying the DocTest (typically, the name of\n        the object whose docstring this DocTest was extracted from).\n\n      - filename: The name of the file that this DocTest was extracted\n        from, or `None` if the filename is unknown.\n\n      - lineno: The line number within filename where this DocTest\n        begins, or `None` if the line number is unavailable.  This\n        line number is zero-based, with respect to the beginning of\n        the file.\n\n      - docstring: The string that the examples were extracted from,\n        or `None` if the string is unavailable.\n    ");
      var1.setline(497);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$24, PyString.fromInterned("\n        Create a new DocTest containing the given examples.  The\n        DocTest's globals are initialized with a copy of `globs`.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$25, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(521);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$26, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(532);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$27, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(535);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$28, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(539);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$29, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$24(PyFrame var1, ThreadState var2) {
      var1.setline(501);
      PyString.fromInterned("\n        Create a new DocTest containing the given examples.  The\n        DocTest's globals are initialized with a copy of `globs`.\n        ");
      var1.setline(502);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__not__().__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("DocTest no longer accepts str; use DocTestParser instead"));
      } else {
         var1.setline(504);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("examples", var3);
         var3 = null;
         var1.setline(505);
         var3 = var1.getlocal(6);
         var1.getlocal(0).__setattr__("docstring", var3);
         var3 = null;
         var1.setline(506);
         var3 = var1.getlocal(2).__getattr__("copy").__call__(var2);
         var1.getlocal(0).__setattr__("globs", var3);
         var3 = null;
         var1.setline(507);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(508);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("filename", var3);
         var3 = null;
         var1.setline(509);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("lineno", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __repr__$25(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("examples"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(513);
         var4 = PyString.fromInterned("no examples");
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(514);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("examples"));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(515);
            var4 = PyString.fromInterned("1 example");
            var1.setlocal(1, var4);
            var3 = null;
         } else {
            var1.setline(517);
            var3 = PyString.fromInterned("%d examples")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("examples")));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(518);
      var3 = PyString.fromInterned("<DocTest %s from %s:%s (%s)>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("lineno"), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$26(PyFrame var1, ThreadState var2) {
      var1.setline(522);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(523);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(525);
         PyObject var4 = var1.getlocal(0).__getattr__("examples");
         var10000 = var4._eq(var1.getlocal(1).__getattr__("examples"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("docstring");
            var10000 = var4._eq(var1.getlocal(1).__getattr__("docstring"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("globs");
               var10000 = var4._eq(var1.getlocal(1).__getattr__("globs"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("name");
                  var10000 = var4._eq(var1.getlocal(1).__getattr__("name"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("filename");
                     var10000 = var4._eq(var1.getlocal(1).__getattr__("filename"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(0).__getattr__("lineno");
                        var10000 = var4._eq(var1.getlocal(1).__getattr__("lineno"));
                        var4 = null;
                     }
                  }
               }
            }
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$27(PyFrame var1, ThreadState var2) {
      var1.setline(533);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$28(PyFrame var1, ThreadState var2) {
      var1.setline(536);
      PyObject var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("docstring"), var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("lineno")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$29(PyFrame var1, ThreadState var2) {
      var1.setline(540);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("DocTest")).__not__().__nonzero__()) {
         var1.setline(541);
         PyInteger var4 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(542);
         PyObject var3 = var1.getglobal("cmp").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("lineno"), var1.getglobal("id").__call__(var2, var1.getlocal(0))})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("name"), var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("lineno"), var1.getglobal("id").__call__(var2, var1.getlocal(1))})));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject DocTestParser$30(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A class used to parse strings containing doctest examples.\n    "));
      var1.setline(552);
      PyString.fromInterned("\n    A class used to parse strings containing doctest examples.\n    ");
      var1.setline(558);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n        # Source consists of a PS1 line followed by zero or more PS2 lines.\n        (?P<source>\n            (?:^(?P<indent> [ ]*) >>>    .*)    # PS1 line\n            (?:\\n           [ ]*  \\.\\.\\. .*)*)  # PS2 lines\n        \\n?\n        # Want consists of any non-blank lines that do not start with PS1.\n        (?P<want> (?:(?![ ]*$)    # Not a blank line\n                     (?![ ]*>>>)  # Not a line starting with PS1\n                     .*$\\n?       # But any other line\n                  )*)\n        "), (PyObject)var1.getname("re").__getattr__("MULTILINE")._or(var1.getname("re").__getattr__("VERBOSE")));
      var1.setlocal("_EXAMPLE_RE", var3);
      var3 = null;
      var1.setline(580);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n        # Grab the traceback header.  Different versions of Python have\n        # said different things on the first traceback line.\n        ^(?P<hdr> Traceback\\ \\(\n            (?: most\\ recent\\ call\\ last\n            |   innermost\\ last\n            ) \\) :\n        )\n        \\s* $                # toss trailing whitespace on the header.\n        (?P<stack> .*?)      # don't blink: absorb stuff until...\n        ^ (?P<msg> \\w+ .*)   #     a line *starts* with alphanum.\n        "), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("MULTILINE"))._or(var1.getname("re").__getattr__("DOTALL")));
      var1.setlocal("_EXCEPTION_RE", var3);
      var3 = null;
      var1.setline(595);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[ ]*(#.*)?$")).__getattr__("match");
      var1.setlocal("_IS_BLANK_OR_COMMENT", var3);
      var3 = null;
      var1.setline(597);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("<string>")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, parse$31, PyString.fromInterned("\n        Divide the given string into examples and intervening text,\n        and return them as a list of alternating Examples and strings.\n        Line numbers for the Examples are 0-based.  The optional\n        argument `name` is a name identifying this string, and is only\n        used for error messages.\n        "));
      var1.setlocal("parse", var5);
      var3 = null;
      var1.setline(636);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_doctest$32, PyString.fromInterned("\n        Extract all doctest examples from the given string, and\n        collect them into a `DocTest` object.\n\n        `globs`, `name`, `filename`, and `lineno` are attributes for\n        the new `DocTest` object.  See the documentation for `DocTest`\n        for more information.\n        "));
      var1.setlocal("get_doctest", var5);
      var3 = null;
      var1.setline(648);
      var4 = new PyObject[]{PyString.fromInterned("<string>")};
      var5 = new PyFunction(var1.f_globals, var4, get_examples$33, PyString.fromInterned("\n        Extract all doctest examples from the given string, and return\n        them as a list of `Example` objects.  Line numbers are\n        0-based, because it's most common in doctests that nothing\n        interesting appears on the same line as opening triple-quote,\n        and so the first interesting line is called \"line 1\" then.\n\n        The optional argument `name` is a name identifying this\n        string, and is only used for error messages.\n        "));
      var1.setlocal("get_examples", var5);
      var3 = null;
      var1.setline(662);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _parse_example$34, PyString.fromInterned("\n        Given a regular expression match from `_EXAMPLE_RE` (`m`),\n        return a pair `(source, want)`, where `source` is the matched\n        example's source code (with prompts and indentation stripped);\n        and `want` is the example's expected output (with indentation\n        stripped).\n\n        `name` is the string's name, and `lineno` is the line number\n        where the example starts; both are used for error messages.\n        "));
      var1.setlocal("_parse_example", var5);
      var3 = null;
      var1.setline(713);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#\\s*doctest:\\s*([^\\n\\'\"]*)$"), (PyObject)var1.getname("re").__getattr__("MULTILINE"));
      var1.setlocal("_OPTION_DIRECTIVE_RE", var3);
      var3 = null;
      var1.setline(716);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _find_options$35, PyString.fromInterned("\n        Return a dictionary containing option overrides extracted from\n        option directives in the given source string.\n\n        `name` is the string's name, and `lineno` is the line number\n        where the example starts; both are used for error messages.\n        "));
      var1.setlocal("_find_options", var5);
      var3 = null;
      var1.setline(744);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([ ]*)(?=\\S)"), (PyObject)var1.getname("re").__getattr__("MULTILINE"));
      var1.setlocal("_INDENT_RE", var3);
      var3 = null;
      var1.setline(746);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _min_indent$36, PyString.fromInterned("Return the minimum indentation of any non-blank line in `s`"));
      var1.setlocal("_min_indent", var5);
      var3 = null;
      var1.setline(754);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _check_prompt_blank$37, PyString.fromInterned("\n        Given the lines of a source string (including prompts and\n        leading indentation), check to make sure that every prompt is\n        followed by a space character.  If any line is not followed by\n        a space character, then raise ValueError.\n        "));
      var1.setlocal("_check_prompt_blank", var5);
      var3 = null;
      var1.setline(768);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _check_prefix$38, PyString.fromInterned("\n        Check that every line in the given list starts with the given\n        prefix; if any line does not, then raise a ValueError.\n        "));
      var1.setlocal("_check_prefix", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject parse$31(PyFrame var1, ThreadState var2) {
      var1.setline(604);
      PyString.fromInterned("\n        Divide the given string into examples and intervening text,\n        and return them as a list of alternating Examples and strings.\n        Line numbers for the Examples are 0-based.  The optional\n        argument `name` is a name identifying this string, and is only\n        used for error messages.\n        ");
      var1.setline(605);
      PyObject var3 = var1.getlocal(1).__getattr__("expandtabs").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(607);
      var3 = var1.getlocal(0).__getattr__("_min_indent").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(608);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(609);
         var10000 = PyString.fromInterned("\n").__getattr__("join");
         PyList var10002 = new PyList();
         var3 = var10002.__getattr__("append");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(609);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(609);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(609);
               var1.dellocal(4);
               var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(1, var3);
               var3 = null;
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(609);
            var1.getlocal(4).__call__(var2, var1.getlocal(5).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null));
         }
      }

      var1.setline(611);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var10);
      var3 = null;
      var1.setline(612);
      PyTuple var11 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0)});
      PyObject[] var8 = Py.unpackSequence(var11, 2);
      PyObject var5 = var8[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(614);
      var3 = var1.getlocal(0).__getattr__("_EXAMPLE_RE").__getattr__("finditer").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(614);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(633);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null));
            var1.setline(634);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(9, var4);
         var1.setline(616);
         var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(7), var1.getlocal(9).__getattr__("start").__call__(var2), (PyObject)null));
         var1.setline(618);
         var5 = var1.getlocal(8);
         var5 = var5._iadd(var1.getlocal(1).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)var1.getlocal(7), (PyObject)var1.getlocal(9).__getattr__("start").__call__(var2)));
         var1.setlocal(8, var5);
         var1.setline(620);
         var5 = var1.getlocal(0).__getattr__("_parse_example").__call__(var2, var1.getlocal(9), var1.getlocal(2), var1.getlocal(8));
         PyObject[] var6 = Py.unpackSequence(var5, 4);
         PyObject var7 = var6[0];
         var1.setlocal(10, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(11, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(12, var7);
         var7 = null;
         var7 = var6[3];
         var1.setlocal(13, var7);
         var7 = null;
         var5 = null;
         var1.setline(623);
         if (var1.getlocal(0).__getattr__("_IS_BLANK_OR_COMMENT").__call__(var2, var1.getlocal(10)).__not__().__nonzero__()) {
            var1.setline(624);
            var10000 = var1.getlocal(6).__getattr__("append");
            PyObject var13 = var1.getglobal("Example");
            PyObject[] var12 = new PyObject[]{var1.getlocal(10), var1.getlocal(12), var1.getlocal(13), var1.getlocal(8), var1.getlocal(3)._add(var1.getglobal("len").__call__(var2, var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("indent")))), var1.getlocal(11)};
            String[] var9 = new String[]{"lineno", "indent", "options"};
            var13 = var13.__call__(var2, var12, var9);
            var5 = null;
            var10000.__call__(var2, var13);
         }

         var1.setline(629);
         var5 = var1.getlocal(8);
         var5 = var5._iadd(var1.getlocal(1).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)var1.getlocal(9).__getattr__("start").__call__(var2), (PyObject)var1.getlocal(9).__getattr__("end").__call__(var2)));
         var1.setlocal(8, var5);
         var1.setline(631);
         var5 = var1.getlocal(9).__getattr__("end").__call__(var2);
         var1.setlocal(7, var5);
         var5 = null;
      }
   }

   public PyObject get_doctest$32(PyFrame var1, ThreadState var2) {
      var1.setline(644);
      PyString.fromInterned("\n        Extract all doctest examples from the given string, and\n        collect them into a `DocTest` object.\n\n        `globs`, `name`, `filename`, and `lineno` are attributes for\n        the new `DocTest` object.  See the documentation for `DocTest`\n        for more information.\n        ");
      var1.setline(645);
      PyObject var10000 = var1.getglobal("DocTest");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("get_examples").__call__(var2, var1.getlocal(1), var1.getlocal(3)), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(1)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject get_examples$33(PyFrame var1, ThreadState var2) {
      var1.setline(658);
      PyString.fromInterned("\n        Extract all doctest examples from the given string, and return\n        them as a list of `Example` objects.  Line numbers are\n        0-based, because it's most common in doctests that nothing\n        interesting appears on the same line as opening triple-quote,\n        and so the first interesting line is called \"line 1\" then.\n\n        The optional argument `name` is a name identifying this\n        string, and is only used for error messages.\n        ");
      var1.setline(659);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(659);
      var3 = var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(659);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(659);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(660);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("Example")).__nonzero__()) {
            var1.setline(659);
            var1.getlocal(3).__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject _parse_example$34(PyFrame var1, ThreadState var2) {
      var1.setline(672);
      PyString.fromInterned("\n        Given a regular expression match from `_EXAMPLE_RE` (`m`),\n        return a pair `(source, want)`, where `source` is the matched\n        example's source code (with prompts and indentation stripped);\n        and `want` is the example's expected output (with indentation\n        stripped).\n\n        `name` is the string's name, and `lineno` is the line number\n        where the example starts; both are used for error messages.\n        ");
      var1.setline(674);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("indent")));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(678);
      var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("source")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(679);
      var1.getlocal(0).__getattr__("_check_prompt_blank").__call__(var2, var1.getlocal(5), var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
      var1.setline(680);
      var1.getlocal(0).__getattr__("_check_prefix").__call__(var2, var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), PyString.fromInterned(" ")._mul(var1.getlocal(4))._add(PyString.fromInterned(".")), var1.getlocal(2), var1.getlocal(3));
      var1.setline(681);
      PyObject var10000 = PyString.fromInterned("\n").__getattr__("join");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(681);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(681);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(681);
            var1.dellocal(7);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(686);
            var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("want"));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(687);
            var3 = var1.getlocal(9).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(688);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
            var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" *$"), (PyObject)var1.getlocal(10).__getitem__(Py.newInteger(-1)));
            }

            if (var10000.__nonzero__()) {
               var1.setline(689);
               var1.getlocal(10).__delitem__((PyObject)Py.newInteger(-1));
            }

            var1.setline(690);
            var1.getlocal(0).__getattr__("_check_prefix").__call__(var2, var1.getlocal(10), PyString.fromInterned(" ")._mul(var1.getlocal(4)), var1.getlocal(2), var1.getlocal(3)._add(var1.getglobal("len").__call__(var2, var1.getlocal(5))));
            var1.setline(692);
            var10000 = PyString.fromInterned("\n").__getattr__("join");
            var10002 = new PyList();
            var3 = var10002.__getattr__("append");
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(692);
            var3 = var1.getlocal(10).__iter__();

            while(true) {
               var1.setline(692);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(692);
                  var1.dellocal(11);
                  var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(695);
                  var3 = var1.getlocal(0).__getattr__("_EXCEPTION_RE").__getattr__("match").__call__(var2, var1.getlocal(9));
                  var1.setlocal(1, var3);
                  var3 = null;
                  var1.setline(696);
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(697);
                     var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("msg"));
                     var1.setlocal(13, var3);
                     var3 = null;
                  } else {
                     var1.setline(699);
                     var3 = var1.getglobal("None");
                     var1.setlocal(13, var3);
                     var3 = null;
                  }

                  var1.setline(702);
                  var3 = var1.getlocal(0).__getattr__("_find_options").__call__(var2, var1.getlocal(6), var1.getlocal(2), var1.getlocal(3));
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(704);
                  PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(14), var1.getlocal(9), var1.getlocal(13)});
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(12, var4);
               var1.setline(692);
               var1.getlocal(11).__call__(var2, var1.getlocal(12).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
            }
         }

         var1.setlocal(8, var4);
         var1.setline(681);
         var1.getlocal(7).__call__(var2, var1.getlocal(8).__getslice__(var1.getlocal(4)._add(Py.newInteger(4)), (PyObject)null, (PyObject)null));
      }
   }

   public PyObject _find_options$35(PyFrame var1, ThreadState var2) {
      var1.setline(723);
      PyString.fromInterned("\n        Return a dictionary containing option overrides extracted from\n        option directives in the given source string.\n\n        `name` is the string's name, and `lineno` is the line number\n        where the example starts; both are used for error messages.\n        ");
      var1.setline(724);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(726);
      PyObject var8 = var1.getlocal(0).__getattr__("_OPTION_DIRECTIVE_RE").__getattr__("finditer").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(726);
         PyObject var4 = var8.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(736);
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_IS_BLANK_OR_COMMENT").__call__(var2, var1.getlocal(1));
            }

            if (var10000.__nonzero__()) {
               var1.setline(737);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("line %r of the doctest for %s has an option directive on a line with no example: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(1)}))));
            }

            var1.setline(740);
            var8 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(5, var4);
         var1.setline(727);
         PyObject var5 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","), (PyObject)PyString.fromInterned(" ")).__getattr__("split").__call__(var2);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(728);
         var5 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(728);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(7, var6);
            var1.setline(729);
            PyObject var7 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var7._notin(PyString.fromInterned("+-"));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var7 = var1.getlocal(7).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var10000 = var7._notin(var1.getglobal("OPTIONFLAGS_BY_NAME"));
               var7 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(731);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("line %r of the doctest for %s has an invalid option: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)._add(Py.newInteger(1)), var1.getlocal(2), var1.getlocal(7)}))));
            }

            var1.setline(734);
            var7 = var1.getglobal("OPTIONFLAGS_BY_NAME").__getitem__(var1.getlocal(7).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(735);
            var7 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var7._eq(PyString.fromInterned("+"));
            var7 = null;
            var7 = var10000;
            var1.getlocal(4).__setitem__(var1.getlocal(8), var7);
            var7 = null;
         }
      }
   }

   public PyObject _min_indent$36(PyFrame var1, ThreadState var2) {
      var1.setline(747);
      PyString.fromInterned("Return the minimum indentation of any non-blank line in `s`");
      var1.setline(748);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(748);
      var3 = var1.getlocal(0).__getattr__("_INDENT_RE").__getattr__("findall").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(748);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(748);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(749);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            PyObject var7 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var7.__nonzero__()) {
               var1.setline(750);
               var3 = var1.getglobal("min").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(752);
               PyInteger var6 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var6;
            }
         }

         var1.setlocal(4, var4);
         var1.setline(748);
         var1.getlocal(3).__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(4)));
      }
   }

   public PyObject _check_prompt_blank$37(PyFrame var1, ThreadState var2) {
      var1.setline(760);
      PyString.fromInterned("\n        Given the lines of a source string (including prompts and\n        leading indentation), check to make sure that every prompt is\n        followed by a space character.  If any line is not followed by\n        a space character, then raise ValueError.\n        ");
      var1.setline(761);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

      PyObject var10000;
      do {
         var1.setline(761);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(762);
         PyObject var7 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
         var10000 = var7._ge(var1.getlocal(2)._add(Py.newInteger(4)));
         var5 = null;
         if (var10000.__nonzero__()) {
            var7 = var1.getlocal(6).__getitem__(var1.getlocal(2)._add(Py.newInteger(3)));
            var10000 = var7._ne(PyString.fromInterned(" "));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(763);
      throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("line %r of the docstring for %s lacks blank after %s: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)._add(var1.getlocal(5))._add(Py.newInteger(1)), var1.getlocal(3), var1.getlocal(6).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(Py.newInteger(3)), (PyObject)null), var1.getlocal(6)}))));
   }

   public PyObject _check_prefix$38(PyFrame var1, ThreadState var2) {
      var1.setline(772);
      PyString.fromInterned("\n        Check that every line in the given list starts with the given\n        prefix; if any line does not, then raise a ValueError.\n        ");
      var1.setline(773);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

      PyObject var10000;
      do {
         var1.setline(773);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(774);
         var10000 = var1.getlocal(6);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(6).__getattr__("startswith").__call__(var2, var1.getlocal(2)).__not__();
         }
      } while(!var10000.__nonzero__());

      var1.setline(775);
      throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("line %r of the docstring for %s has inconsistent leading whitespace: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)._add(var1.getlocal(5))._add(Py.newInteger(1)), var1.getlocal(3), var1.getlocal(6)}))));
   }

   public PyObject DocTestFinder$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A class used to extract the DocTests that are relevant to a given\n    object, from its docstring and the docstrings of its contained\n    objects.  Doctests can currently be extracted from the following\n    object types: modules, functions, classes, methods, staticmethods,\n    classmethods, and properties.\n    "));
      var1.setline(791);
      PyString.fromInterned("\n    A class used to extract the DocTests that are relevant to a given\n    object, from its docstring and the docstrings of its contained\n    objects.  Doctests can currently be extracted from the following\n    object types: modules, functions, classes, methods, staticmethods,\n    classmethods, and properties.\n    ");
      var1.setline(793);
      PyObject[] var3 = new PyObject[]{var1.getname("False"), var1.getname("DocTestParser").__call__(var2), var1.getname("True"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$40, PyString.fromInterned("\n        Create a new doctest finder.\n\n        The optional argument `parser` specifies a class or\n        function that should be used to create new DocTest objects (or\n        objects that implement the same interface as DocTest).  The\n        signature for this factory function should match the signature\n        of the DocTest constructor.\n\n        If the optional argument `recurse` is false, then `find` will\n        only examine the given object, and not any contained objects.\n\n        If the optional argument `exclude_empty` is false, then `find`\n        will include tests for objects with empty docstrings.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(815);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, find$41, PyString.fromInterned("\n        Return a list of the DocTests that are defined by the given\n        object's docstring, or by any of its contained objects'\n        docstrings.\n\n        The optional parameter `module` is the module that contains\n        the given object.  If the module is not specified or is None, then\n        the test finder will attempt to automatically determine the\n        correct module.  The object's module is used:\n\n            - As a default namespace, if `globs` is not specified.\n            - To prevent the DocTestFinder from extracting DocTests\n              from objects that are imported from other modules.\n            - To find the name of the file containing the object.\n            - To help find the line number of the object within its\n              file.\n\n        Contained objects whose module does not match `module` are ignored.\n\n        If `module` is False, no attempt to find the module will be made.\n        This is obscure, of use mostly in tests:  if `module` is False, or\n        is None but cannot be found automatically, then all objects are\n        considered to belong to the (non-existent) module, so all contained\n        objects will (recursively) be searched for doctests.\n\n        The globals for each DocTest is formed by combining `globs`\n        and `extraglobs` (bindings in `extraglobs` override bindings\n        in `globs`).  A new copy of the globals dictionary is created\n        for each DocTest.  If `globs` is not specified, then it\n        defaults to the module's `__dict__`, if specified, or {}\n        otherwise.  If `extraglobs` is not specified, then it defaults\n        to {}.\n\n        "));
      var1.setlocal("find", var4);
      var3 = null;
      var1.setline(908);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _from_module$42, PyString.fromInterned("\n        Return true if the given object is defined in the given\n        module.\n        "));
      var1.setlocal("_from_module", var4);
      var3 = null;
      var1.setline(928);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _find$43, PyString.fromInterned("\n        Find tests for the given object and any contained objects, and\n        add them to `tests`.\n        "));
      var1.setlocal("_find", var4);
      var3 = null;
      var1.setline(991);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_test$44, PyString.fromInterned("\n        Return a DocTest for the given object, if it defines a docstring;\n        otherwise, return None.\n        "));
      var1.setlocal("_get_test", var4);
      var3 = null;
      var1.setline(1030);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _find_lineno$45, PyString.fromInterned("\n        Return a line number of the given object's docstring.  Note:\n        this method assumes that the object has a docstring.\n        "));
      var1.setlocal("_find_lineno", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$40(PyFrame var1, ThreadState var2) {
      var1.setline(809);
      PyString.fromInterned("\n        Create a new doctest finder.\n\n        The optional argument `parser` specifies a class or\n        function that should be used to create new DocTest objects (or\n        objects that implement the same interface as DocTest).  The\n        signature for this factory function should match the signature\n        of the DocTest constructor.\n\n        If the optional argument `recurse` is false, then `find` will\n        only examine the given object, and not any contained objects.\n\n        If the optional argument `exclude_empty` is false, then `find`\n        will include tests for objects with empty docstrings.\n        ");
      var1.setline(810);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_parser", var3);
      var3 = null;
      var1.setline(811);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_verbose", var3);
      var3 = null;
      var1.setline(812);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_recurse", var3);
      var3 = null;
      var1.setline(813);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_exclude_empty", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find$41(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      PyString.fromInterned("\n        Return a list of the DocTests that are defined by the given\n        object's docstring, or by any of its contained objects'\n        docstrings.\n\n        The optional parameter `module` is the module that contains\n        the given object.  If the module is not specified or is None, then\n        the test finder will attempt to automatically determine the\n        correct module.  The object's module is used:\n\n            - As a default namespace, if `globs` is not specified.\n            - To prevent the DocTestFinder from extracting DocTests\n              from objects that are imported from other modules.\n            - To find the name of the file containing the object.\n            - To help find the line number of the object within its\n              file.\n\n        Contained objects whose module does not match `module` are ignored.\n\n        If `module` is False, no attempt to find the module will be made.\n        This is obscure, of use mostly in tests:  if `module` is False, or\n        is None but cannot be found automatically, then all objects are\n        considered to belong to the (non-existent) module, so all contained\n        objects will (recursively) be searched for doctests.\n\n        The globals for each DocTest is formed by combining `globs`\n        and `extraglobs` (bindings in `extraglobs` override bindings\n        in `globs`).  A new copy of the globals dictionary is created\n        for each DocTest.  If `globs` is not specified, then it\n        defaults to the module's `__dict__`, if specified, or {}\n        otherwise.  If `extraglobs` is not specified, then it defaults\n        to {}.\n\n        ");
      var1.setline(851);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(852);
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__name__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(853);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(854);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("DocTestFinder.find: name must be given when obj.__name__ doesn't exist: %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(1))}))));
         }
      }

      var1.setline(861);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(862);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(863);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(864);
            var3 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      try {
         var1.setline(870);
         var10000 = var1.getglobal("inspect").__getattr__("getsourcefile").__call__(var2, var1.getlocal(1));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("inspect").__getattr__("getfile").__call__(var2, var1.getlocal(1));
         }

         var3 = var10000;
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(871);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(875);
            var3 = var1.getglobal("linecache").__getattr__("getlines").__call__(var2, var1.getlocal(6), var1.getlocal(3).__getattr__("__dict__"));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(879);
            var3 = var1.getglobal("linecache").__getattr__("getlines").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(880);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            var1.setline(881);
            var3 = var1.getglobal("None");
            var1.setlocal(7, var3);
            var3 = null;
         }
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("TypeError"))) {
            throw var6;
         }

         var1.setline(883);
         PyObject var4 = var1.getglobal("None");
         var1.setlocal(7, var4);
         var4 = null;
      }

      var1.setline(886);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(887);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(888);
            PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(4, var7);
            var3 = null;
         } else {
            var1.setline(890);
            var3 = var1.getlocal(3).__getattr__("__dict__").__getattr__("copy").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
         }
      } else {
         var1.setline(892);
         var3 = var1.getlocal(4).__getattr__("copy").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(893);
      var3 = var1.getlocal(5);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(894);
         var1.getlocal(4).__getattr__("update").__call__(var2, var1.getlocal(5));
      }

      var1.setline(895);
      PyString var8 = PyString.fromInterned("__name__");
      var10000 = var8._notin(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(896);
         var8 = PyString.fromInterned("__main__");
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("__name__"), var8);
         var3 = null;
      }

      var1.setline(899);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(8, var9);
      var3 = null;
      var1.setline(900);
      var10000 = var1.getlocal(0).__getattr__("_find");
      PyObject[] var10 = new PyObject[]{var1.getlocal(8), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(7), var1.getlocal(4), new PyDictionary(Py.EmptyObjects)};
      var10000.__call__(var2, var10);
      var1.setline(905);
      var1.getlocal(8).__getattr__("sort").__call__(var2);
      var1.setline(906);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _from_module$42(PyFrame var1, ThreadState var2) {
      var1.setline(912);
      PyString.fromInterned("\n        Return true if the given object is defined in the given\n        module.\n        ");
      var1.setline(913);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(914);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(915);
         PyObject var4 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(2));
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(916);
            var4 = var1.getlocal(1);
            var10000 = var4._is(var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(2)));
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(917);
            if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(2)).__nonzero__()) {
               var1.setline(918);
               var4 = var1.getlocal(1).__getattr__("__dict__");
               var10000 = var4._is(var1.getlocal(2).__getattr__("func_globals"));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(919);
               if (var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                  var1.setline(920);
                  var4 = var1.getlocal(1).__getattr__("__name__");
                  var10000 = var4._eq(var1.getlocal(2).__getattr__("__module__"));
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(921);
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__module__")).__nonzero__()) {
                     var1.setline(922);
                     var4 = var1.getlocal(1).__getattr__("__name__");
                     var10000 = var4._eq(var1.getlocal(2).__getattr__("__module__"));
                     var4 = null;
                     var3 = var10000;
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(923);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("property")).__nonzero__()) {
                        var1.setline(924);
                        var3 = var1.getglobal("True");
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(926);
                        throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("object must be a class or function")));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _find$43(PyFrame var1, ThreadState var2) {
      var1.setline(932);
      PyString.fromInterned("\n        Find tests for the given object and any contained objects, and\n        add them to `tests`.\n        ");
      var1.setline(933);
      if (var1.getlocal(0).__getattr__("_verbose").__nonzero__()) {
         var1.setline(934);
         Py.println(PyString.fromInterned("Finding tests in %s")._mod(var1.getlocal(3)));
      }

      var1.setline(937);
      PyObject var3 = var1.getglobal("id").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._in(var1.getlocal(7));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(938);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(939);
         PyInteger var7 = Py.newInteger(1);
         var1.getlocal(7).__setitem__((PyObject)var1.getglobal("id").__call__(var2, var1.getlocal(2)), var7);
         var3 = null;
         var1.setline(942);
         var10000 = var1.getlocal(0).__getattr__("_get_test");
         PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(6), var1.getlocal(5)};
         var3 = var10000.__call__(var2, var8);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(943);
         var3 = var1.getlocal(8);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(944);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(8));
         }

         var1.setline(947);
         var10000 = var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(2));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_recurse");
         }

         PyObject var4;
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         if (var10000.__nonzero__()) {
            var1.setline(948);
            var3 = var1.getlocal(2).__getattr__("__dict__").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(948);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(949);
               var9 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(9)}));
               var1.setlocal(9, var9);
               var5 = null;
               var1.setline(951);
               var10000 = var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(10));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(10));
               }

               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_from_module").__call__(var2, var1.getlocal(4), var1.getlocal(10));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(953);
                  var10000 = var1.getlocal(0).__getattr__("_find");
                  var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(10), var1.getlocal(9), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
                  var10000.__call__(var2, var5);
               }
            }
         }

         var1.setline(957);
         var10000 = var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(2));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_recurse");
         }

         if (var10000.__nonzero__()) {
            var1.setline(958);
            var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("__test__"), (PyObject)(new PyDictionary(Py.EmptyObjects))).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(958);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(959);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("basestring")).__not__().__nonzero__()) {
                  var1.setline(960);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("DocTestFinder.find: __test__ keys must be strings: %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(9))}))));
               }

               var1.setline(963);
               var10000 = var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(10));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(10));
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(10));
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(10));
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("basestring"));
                        }
                     }
                  }
               }

               if (var10000.__not__().__nonzero__()) {
                  var1.setline(966);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("DocTestFinder.find: __test__ values must be strings, functions, methods, classes, or modules: %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(10))}))));
               }

               var1.setline(970);
               var9 = PyString.fromInterned("%s.__test__.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(9)}));
               var1.setlocal(9, var9);
               var5 = null;
               var1.setline(971);
               var10000 = var1.getlocal(0).__getattr__("_find");
               var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(10), var1.getlocal(9), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
               var10000.__call__(var2, var5);
            }
         }

         var1.setline(975);
         var10000 = var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(2));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_recurse");
         }

         if (var10000.__nonzero__()) {
            var1.setline(976);
            var3 = var1.getlocal(2).__getattr__("__dict__").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(976);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(978);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("staticmethod")).__nonzero__()) {
                  var1.setline(979);
                  var9 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(9));
                  var1.setlocal(10, var9);
                  var5 = null;
               }

               var1.setline(980);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("classmethod")).__nonzero__()) {
                  var1.setline(981);
                  var9 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(9)).__getattr__("im_func");
                  var1.setlocal(10, var9);
                  var5 = null;
               }

               var1.setline(984);
               var10000 = var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(10));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(10));
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("property"));
                  }
               }

               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_from_module").__call__(var2, var1.getlocal(4), var1.getlocal(10));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(987);
                  var9 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(9)}));
                  var1.setlocal(9, var9);
                  var5 = null;
                  var1.setline(988);
                  var10000 = var1.getlocal(0).__getattr__("_find");
                  var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(10), var1.getlocal(9), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
                  var10000.__call__(var2, var5);
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_test$44(PyFrame var1, ThreadState var2) {
      var1.setline(995);
      PyString.fromInterned("\n        Return a DocTest for the given object, if it defines a docstring;\n        otherwise, return None.\n        ");
      var1.setline(998);
      PyObject var10000;
      PyException var3;
      PyString var4;
      PyObject var6;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(999);
         var6 = var1.getlocal(1);
         var1.setlocal(6, var6);
         var3 = null;
      } else {
         try {
            var1.setline(1002);
            var6 = var1.getlocal(1).__getattr__("__doc__");
            var10000 = var6._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1003);
               PyString var7 = PyString.fromInterned("");
               var1.setlocal(6, var7);
               var3 = null;
            } else {
               var1.setline(1005);
               var6 = var1.getlocal(1).__getattr__("__doc__");
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(1006);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("basestring")).__not__().__nonzero__()) {
                  var1.setline(1007);
                  var6 = var1.getglobal("str").__call__(var2, var1.getlocal(6));
                  var1.setlocal(6, var6);
                  var3 = null;
               }
            }
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (!var3.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("AttributeError")}))) {
               throw var3;
            }

            var1.setline(1009);
            var4 = PyString.fromInterned("");
            var1.setlocal(6, var4);
            var4 = null;
         }
      }

      var1.setline(1012);
      var6 = var1.getlocal(0).__getattr__("_find_lineno").__call__(var2, var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(7, var6);
      var3 = null;
      var1.setline(1015);
      var10000 = var1.getlocal(0).__getattr__("_exclude_empty");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(6).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1016);
         var6 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(1019);
         PyObject var8 = var1.getlocal(3);
         var10000 = var8._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1020);
            var8 = var1.getglobal("None");
            var1.setlocal(8, var8);
            var4 = null;
         } else {
            var1.setline(1022);
            var8 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("__file__"), (PyObject)var1.getlocal(3).__getattr__("__name__"));
            var1.setlocal(8, var8);
            var4 = null;
            var1.setline(1023);
            var8 = var1.getlocal(8).__getslice__(Py.newInteger(-4), (PyObject)null, (PyObject)null);
            var10000 = var8._in(new PyTuple(new PyObject[]{PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1024);
               var8 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(8, var8);
               var4 = null;
            } else {
               var1.setline(1025);
               if (var1.getlocal(8).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$py.class")).__nonzero__()) {
                  var1.setline(1026);
                  var8 = PyString.fromInterned("%s.py")._mod(var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null));
                  var1.setlocal(8, var8);
                  var4 = null;
               }
            }
         }

         var1.setline(1027);
         var10000 = var1.getlocal(0).__getattr__("_parser").__getattr__("get_doctest");
         PyObject[] var9 = new PyObject[]{var1.getlocal(6), var1.getlocal(4), var1.getlocal(2), var1.getlocal(8), var1.getlocal(7)};
         var6 = var10000.__call__(var2, var9);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject _find_lineno$45(PyFrame var1, ThreadState var2) {
      var1.setline(1034);
      PyString.fromInterned("\n        Return a line number of the given object's docstring.  Note:\n        this method assumes that the object has a docstring.\n        ");
      var1.setline(1035);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1038);
      if (var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1039);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(1044);
      PyObject var10000;
      PyObject var4;
      PyObject var5;
      if (var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1045);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1046);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1047);
         var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("^\\s*class\\s*%s\\b")._mod(var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__name__"), (PyObject)PyString.fromInterned("-"))));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1049);
         var4 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(1049);
            var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(5, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(1050);
            if (var1.getlocal(4).__getattr__("match").__call__(var2, var1.getlocal(6)).__nonzero__()) {
               var1.setline(1051);
               PyObject var9 = var1.getlocal(5);
               var1.setlocal(3, var9);
               var6 = null;
               break;
            }
         }
      }

      var1.setline(1055);
      if (var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1055);
         var4 = var1.getlocal(1).__getattr__("im_func");
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(1056);
      if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1056);
         var4 = var1.getlocal(1).__getattr__("func_code");
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(1057);
      if (var1.getglobal("inspect").__getattr__("istraceback").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1057);
         var4 = var1.getlocal(1).__getattr__("tb_frame");
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(1058);
      if (var1.getglobal("inspect").__getattr__("isframe").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1058);
         var4 = var1.getlocal(1).__getattr__("f_code");
         var1.setlocal(1, var4);
         var4 = null;
      }

      var1.setline(1059);
      if (var1.getglobal("inspect").__getattr__("iscode").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1060);
         var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("co_firstlineno"), (PyObject)var1.getglobal("None"))._sub(Py.newInteger(1));
         var1.setlocal(3, var4);
         var4 = null;
      }

      var1.setline(1067);
      var4 = var1.getlocal(3);
      var10000 = var4._isnot(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1068);
         var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1069);
            var3 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1070);
         var4 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(^|.*:)\\s*\\w*(\"|')"));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1071);
         var4 = var1.getglobal("range").__call__(var2, var1.getlocal(3), var1.getglobal("len").__call__(var2, var1.getlocal(2))).__iter__();

         while(true) {
            var1.setline(1071);
            var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(3, var5);
            var1.setline(1072);
            if (var1.getlocal(4).__getattr__("match").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3))).__nonzero__()) {
               var1.setline(1073);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }

      var1.setline(1076);
      var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DocTestRunner$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A class used to run DocTest test cases, and accumulate statistics.\n    The `run` method is used to process a single DocTest case.  It\n    returns a tuple `(f, t)`, where `t` is the number of test cases\n    tried, and `f` is the number of test cases that failed.\n\n        >>> tests = DocTestFinder().find(_TestClass)\n        >>> runner = DocTestRunner(verbose=False)\n        >>> tests.sort(key = lambda test: test.name)\n        >>> for test in tests:\n        ...     print test.name, '->', runner.run(test)\n        _TestClass -> TestResults(failed=0, attempted=2)\n        _TestClass.__init__ -> TestResults(failed=0, attempted=2)\n        _TestClass.get -> TestResults(failed=0, attempted=2)\n        _TestClass.square -> TestResults(failed=0, attempted=1)\n\n    The `summarize` method prints a summary of all the test cases that\n    have been run by the runner, and returns an aggregated `(f, t)`\n    tuple:\n\n        >>> runner.summarize(verbose=1)\n        4 items passed all tests:\n           2 tests in _TestClass\n           2 tests in _TestClass.__init__\n           2 tests in _TestClass.get\n           1 tests in _TestClass.square\n        7 tests in 4 items.\n        7 passed and 0 failed.\n        Test passed.\n        TestResults(failed=0, attempted=7)\n\n    The aggregated number of tried examples and failed examples is\n    also available via the `tries` and `failures` attributes:\n\n        >>> runner.tries\n        7\n        >>> runner.failures\n        0\n\n    The comparison between expected outputs and actual outputs is done\n    by an `OutputChecker`.  This comparison may be customized with a\n    number of option flags; see the documentation for `testmod` for\n    more information.  If the option flags are insufficient, then the\n    comparison may also be customized by passing a subclass of\n    `OutputChecker` to the constructor.\n\n    The test runner's display output can be controlled in two ways.\n    First, an output function (`out) can be passed to\n    `TestRunner.run`; this function will be called with strings that\n    should be displayed.  It defaults to `sys.stdout.write`.  If\n    capturing the output is not sufficient, then the display output\n    can be also customized by subclassing DocTestRunner, and\n    overriding the methods `report_start`, `report_success`,\n    `report_unexpected_exception`, and `report_failure`.\n    "));
      var1.setline(1137);
      PyString.fromInterned("\n    A class used to run DocTest test cases, and accumulate statistics.\n    The `run` method is used to process a single DocTest case.  It\n    returns a tuple `(f, t)`, where `t` is the number of test cases\n    tried, and `f` is the number of test cases that failed.\n\n        >>> tests = DocTestFinder().find(_TestClass)\n        >>> runner = DocTestRunner(verbose=False)\n        >>> tests.sort(key = lambda test: test.name)\n        >>> for test in tests:\n        ...     print test.name, '->', runner.run(test)\n        _TestClass -> TestResults(failed=0, attempted=2)\n        _TestClass.__init__ -> TestResults(failed=0, attempted=2)\n        _TestClass.get -> TestResults(failed=0, attempted=2)\n        _TestClass.square -> TestResults(failed=0, attempted=1)\n\n    The `summarize` method prints a summary of all the test cases that\n    have been run by the runner, and returns an aggregated `(f, t)`\n    tuple:\n\n        >>> runner.summarize(verbose=1)\n        4 items passed all tests:\n           2 tests in _TestClass\n           2 tests in _TestClass.__init__\n           2 tests in _TestClass.get\n           1 tests in _TestClass.square\n        7 tests in 4 items.\n        7 passed and 0 failed.\n        Test passed.\n        TestResults(failed=0, attempted=7)\n\n    The aggregated number of tried examples and failed examples is\n    also available via the `tries` and `failures` attributes:\n\n        >>> runner.tries\n        7\n        >>> runner.failures\n        0\n\n    The comparison between expected outputs and actual outputs is done\n    by an `OutputChecker`.  This comparison may be customized with a\n    number of option flags; see the documentation for `testmod` for\n    more information.  If the option flags are insufficient, then the\n    comparison may also be customized by passing a subclass of\n    `OutputChecker` to the constructor.\n\n    The test runner's display output can be controlled in two ways.\n    First, an output function (`out) can be passed to\n    `TestRunner.run`; this function will be called with strings that\n    should be displayed.  It defaults to `sys.stdout.write`.  If\n    capturing the output is not sufficient, then the display output\n    can be also customized by subclassing DocTestRunner, and\n    overriding the methods `report_start`, `report_success`,\n    `report_unexpected_exception`, and `report_failure`.\n    ");
      var1.setline(1140);
      PyObject var3 = PyString.fromInterned("*")._mul(Py.newInteger(70));
      var1.setlocal("DIVIDER", var3);
      var3 = null;
      var1.setline(1142);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$47, PyString.fromInterned("\n        Create a new test runner.\n\n        Optional keyword arg `checker` is the `OutputChecker` that\n        should be used to compare the expected outputs and actual\n        outputs of doctest examples.\n\n        Optional keyword arg 'verbose' prints lots of stuff if true,\n        only failures if false; by default, it's true iff '-v' is in\n        sys.argv.\n\n        Optional argument `optionflags` can be used to control how the\n        test runner compares expected output to actual output, and how\n        it displays failures.  See the documentation for `testmod` for\n        more information.\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1178);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, report_start$48, PyString.fromInterned("\n        Report that the test runner is about to process the given\n        example.  (Only displays a message if verbose=True)\n        "));
      var1.setlocal("report_start", var5);
      var3 = null;
      var1.setline(1191);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, report_success$49, PyString.fromInterned("\n        Report that the given example ran successfully.  (Only\n        displays a message if verbose=True)\n        "));
      var1.setlocal("report_success", var5);
      var3 = null;
      var1.setline(1199);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, report_failure$50, PyString.fromInterned("\n        Report that the given example failed.\n        "));
      var1.setlocal("report_failure", var5);
      var3 = null;
      var1.setline(1206);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, report_unexpected_exception$51, PyString.fromInterned("\n        Report that the given example raised an unexpected exception.\n        "));
      var1.setlocal("report_unexpected_exception", var5);
      var3 = null;
      var1.setline(1213);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _failure_header$52, (PyObject)null);
      var1.setlocal("_failure_header", var5);
      var3 = null;
      var1.setline(1233);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _DocTestRunner__run$53, PyString.fromInterned("\n        Run the examples in `test`.  Write the outcome of each example\n        with one of the `DocTestRunner.report_*` methods, using the\n        writer function `out`.  `compileflags` is the set of compiler\n        flags that should be used to execute examples.  Return a tuple\n        `(f, t)`, where `t` is the number of examples tried, and `f`\n        is the number of examples that failed.  The examples are run\n        in the namespace `test.globs`.\n        "));
      var1.setlocal("_DocTestRunner__run", var5);
      var3 = null;
      var1.setline(1357);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _DocTestRunner__record_outcome$54, PyString.fromInterned("\n        Record the fact that the given DocTest (`test`) generated `f`\n        failures out of `t` tried examples.\n        "));
      var1.setlocal("_DocTestRunner__record_outcome", var5);
      var3 = null;
      var1.setline(1367);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<doctest (?P<name>.+)\\[(?P<examplenum>\\d+)\\]>$"));
      var1.setlocal("_DocTestRunner__LINECACHE_FILENAME_RE", var3);
      var3 = null;
      var1.setline(1370);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _DocTestRunner__patched_linecache_getlines$55, (PyObject)null);
      var1.setlocal("_DocTestRunner__patched_linecache_getlines", var5);
      var3 = null;
      var1.setline(1381);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, run$56, PyString.fromInterned("\n        Run the examples in `test`, and display the results using the\n        writer function `out`.\n\n        The examples are run in the namespace `test.globs`.  If\n        `clear_globs` is true (the default), then this namespace will\n        be cleared after the test runs, to help with garbage\n        collection.  If you would like to examine the namespace after\n        the test completes, then use `clear_globs=False`.\n\n        `compileflags` gives the set of flags that should be used by\n        the Python compiler when running the examples.  If not\n        specified, then it will default to the set of future-import\n        flags that apply to `globs`.\n\n        The output of each example is checked using\n        `DocTestRunner.check_output`, and the results are formatted by\n        the `DocTestRunner.report_*` methods.\n        "));
      var1.setlocal("run", var5);
      var3 = null;
      var1.setline(1443);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, summarize$57, PyString.fromInterned("\n        Print a summary of all the test cases that have been run by\n        this DocTestRunner, and return a tuple `(f, t)`, where `f` is\n        the total number of failed examples, and `t` is the total\n        number of tried examples.\n\n        The optional `verbose` argument controls how detailed the\n        summary is.  If the verbosity is not specified, then the\n        DocTestRunner's verbosity is used.\n        "));
      var1.setlocal("summarize", var5);
      var3 = null;
      var1.setline(1500);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, merge$58, (PyObject)null);
      var1.setlocal("merge", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(1158);
      PyString.fromInterned("\n        Create a new test runner.\n\n        Optional keyword arg `checker` is the `OutputChecker` that\n        should be used to compare the expected outputs and actual\n        outputs of doctest examples.\n\n        Optional keyword arg 'verbose' prints lots of stuff if true,\n        only failures if false; by default, it's true iff '-v' is in\n        sys.argv.\n\n        Optional argument `optionflags` can be used to control how the\n        test runner compares expected output to actual output, and how\n        it displays failures.  See the documentation for `testmod` for\n        more information.\n        ");
      var1.setline(1159);
      PyObject var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("OutputChecker").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.getlocal(0).__setattr__("_checker", var3);
      var3 = null;
      var1.setline(1160);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1161);
         PyString var4 = PyString.fromInterned("-v");
         var10000 = var4._in(var1.getglobal("sys").__getattr__("argv"));
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1162);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_verbose", var3);
      var3 = null;
      var1.setline(1163);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("optionflags", var3);
      var3 = null;
      var1.setline(1164);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("original_optionflags", var3);
      var3 = null;
      var1.setline(1167);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"tries", var5);
      var3 = null;
      var1.setline(1168);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"failures", var5);
      var3 = null;
      var1.setline(1169);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_name2ft", var6);
      var3 = null;
      var1.setline(1172);
      var3 = var1.getglobal("_SpoofOut").__call__(var2);
      var1.getlocal(0).__setattr__("_fakeout", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject report_start$48(PyFrame var1, ThreadState var2) {
      var1.setline(1182);
      PyString.fromInterned("\n        Report that the test runner is about to process the given\n        example.  (Only displays a message if verbose=True)\n        ");
      var1.setline(1183);
      if (var1.getlocal(0).__getattr__("_verbose").__nonzero__()) {
         var1.setline(1184);
         if (var1.getlocal(3).__getattr__("want").__nonzero__()) {
            var1.setline(1185);
            var1.getlocal(1).__call__(var2, PyString.fromInterned("Trying:\n")._add(var1.getglobal("_indent").__call__(var2, var1.getlocal(3).__getattr__("source")))._add(PyString.fromInterned("Expecting:\n"))._add(var1.getglobal("_indent").__call__(var2, var1.getlocal(3).__getattr__("want"))));
         } else {
            var1.setline(1188);
            var1.getlocal(1).__call__(var2, PyString.fromInterned("Trying:\n")._add(var1.getglobal("_indent").__call__(var2, var1.getlocal(3).__getattr__("source")))._add(PyString.fromInterned("Expecting nothing\n")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject report_success$49(PyFrame var1, ThreadState var2) {
      var1.setline(1195);
      PyString.fromInterned("\n        Report that the given example ran successfully.  (Only\n        displays a message if verbose=True)\n        ");
      var1.setline(1196);
      if (var1.getlocal(0).__getattr__("_verbose").__nonzero__()) {
         var1.setline(1197);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ok\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject report_failure$50(PyFrame var1, ThreadState var2) {
      var1.setline(1202);
      PyString.fromInterned("\n        Report that the given example failed.\n        ");
      var1.setline(1203);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_failure_header").__call__(var2, var1.getlocal(2), var1.getlocal(3))._add(var1.getlocal(0).__getattr__("_checker").__getattr__("output_difference").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(0).__getattr__("optionflags"))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject report_unexpected_exception$51(PyFrame var1, ThreadState var2) {
      var1.setline(1209);
      PyString.fromInterned("\n        Report that the given example raised an unexpected exception.\n        ");
      var1.setline(1210);
      var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("_failure_header").__call__(var2, var1.getlocal(2), var1.getlocal(3))._add(PyString.fromInterned("Exception raised:\n"))._add(var1.getglobal("_indent").__call__(var2, var1.getglobal("_exception_traceback").__call__(var2, var1.getlocal(4)))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _failure_header$52(PyFrame var1, ThreadState var2) {
      var1.setline(1214);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("DIVIDER")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1215);
      PyObject var4;
      if (var1.getlocal(1).__getattr__("filename").__nonzero__()) {
         var1.setline(1216);
         var4 = var1.getlocal(1).__getattr__("lineno");
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(2).__getattr__("lineno");
            var10000 = var4._isnot(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1217);
            var4 = var1.getlocal(1).__getattr__("lineno")._add(var1.getlocal(2).__getattr__("lineno"))._add(Py.newInteger(1));
            var1.setlocal(4, var4);
            var3 = null;
         } else {
            var1.setline(1219);
            PyString var5 = PyString.fromInterned("?");
            var1.setlocal(4, var5);
            var3 = null;
         }

         var1.setline(1220);
         var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("File \"%s\", line %s, in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(4), var1.getlocal(1).__getattr__("name")})));
      } else {
         var1.setline(1223);
         var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("Line %s, in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("lineno")._add(Py.newInteger(1)), var1.getlocal(1).__getattr__("name")})));
      }

      var1.setline(1224);
      var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed example:"));
      var1.setline(1225);
      var4 = var1.getlocal(2).__getattr__("source");
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(1226);
      var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_indent").__call__(var2, var1.getlocal(5)));
      var1.setline(1227);
      var4 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _DocTestRunner__run$53(PyFrame var1, ThreadState var2) {
      var1.setline(1242);
      PyString.fromInterned("\n        Run the examples in `test`.  Write the outcome of each example\n        with one of the `DocTestRunner.report_*` methods, using the\n        writer function `out`.  `compileflags` is the set of compiler\n        flags that should be used to execute examples.  Return a tuple\n        `(f, t)`, where `t` is the number of examples tried, and `f`\n        is the number of examples that failed.  The examples are run\n        in the namespace `test.globs`.\n        ");
      var1.setline(1244);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(4, var3);
      var1.setlocal(5, var3);
      var1.setline(1248);
      PyObject var11 = var1.getlocal(0).__getattr__("optionflags");
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(1250);
      var11 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
      PyObject[] var4 = Py.unpackSequence(var11, 3);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(1252);
      var11 = var1.getlocal(0).__getattr__("_checker").__getattr__("check_output");
      var1.setlocal(10, var11);
      var3 = null;
      var1.setline(1255);
      var11 = var1.getname("enumerate").__call__(var2, var1.getlocal(1).__getattr__("examples")).__iter__();

      while(true) {
         PyObject var6;
         PyObject[] var13;
         PyObject var10000;
         do {
            var1.setline(1255);
            PyObject var12 = var11.__iternext__();
            if (var12 == null) {
               var1.setline(1351);
               var11 = var1.getlocal(6);
               var1.getlocal(0).__setattr__("optionflags", var11);
               var3 = null;
               var1.setline(1354);
               var1.getlocal(0).__getattr__("_DocTestRunner__record_outcome").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(5));
               var1.setline(1355);
               var11 = var1.getname("TestResults").__call__(var2, var1.getlocal(4), var1.getlocal(5));
               var1.f_lasti = -1;
               return var11;
            }

            var13 = Py.unpackSequence(var12, 2);
            var6 = var13[0];
            var1.setlocal(11, var6);
            var6 = null;
            var6 = var13[1];
            var1.setlocal(12, var6);
            var6 = null;
            var1.setline(1259);
            var10000 = var1.getlocal(0).__getattr__("optionflags")._and(var1.getname("REPORT_ONLY_FIRST_FAILURE"));
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(4);
               var10000 = var5._gt(Py.newInteger(0));
               var5 = null;
            }

            var5 = var10000;
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(1263);
            var5 = var1.getlocal(6);
            var1.getlocal(0).__setattr__("optionflags", var5);
            var5 = null;
            var1.setline(1264);
            if (var1.getlocal(12).__getattr__("options").__nonzero__()) {
               var1.setline(1265);
               var5 = var1.getlocal(12).__getattr__("options").__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(1265);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  PyObject[] var7 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(14, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(15, var8);
                  var8 = null;
                  var1.setline(1266);
                  PyObject var9;
                  String var14;
                  if (var1.getlocal(15).__nonzero__()) {
                     var1.setline(1267);
                     var10000 = var1.getlocal(0);
                     var14 = "optionflags";
                     var8 = var10000;
                     var9 = var8.__getattr__(var14);
                     var9 = var9._ior(var1.getlocal(14));
                     var8.__setattr__(var14, var9);
                  } else {
                     var1.setline(1269);
                     var10000 = var1.getlocal(0);
                     var14 = "optionflags";
                     var8 = var10000;
                     var9 = var8.__getattr__(var14);
                     var9 = var9._iand(var1.getlocal(14).__invert__());
                     var8.__setattr__(var14, var9);
                  }
               }
            }

            var1.setline(1272);
         } while(var1.getlocal(0).__getattr__("optionflags")._and(var1.getname("SKIP")).__nonzero__());

         var1.setline(1276);
         var5 = var1.getlocal(5);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(5, var5);
         var1.setline(1277);
         if (var1.getlocal(13).__not__().__nonzero__()) {
            var1.setline(1278);
            var1.getlocal(0).__getattr__("report_start").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(12));
         }

         var1.setline(1283);
         var5 = PyString.fromInterned("<doctest %s[%d]>")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("name"), var1.getlocal(11)}));
         var1.setlocal(16, var5);
         var5 = null;

         try {
            var1.setline(1290);
            var10000 = var1.getname("compile");
            var13 = new PyObject[]{var1.getlocal(12).__getattr__("source"), var1.getlocal(16), PyString.fromInterned("single"), var1.getlocal(2), Py.newInteger(1)};
            Py.exec(var10000.__call__(var2, var13), var1.getlocal(1).__getattr__("globs"), (PyObject)null);
            var1.setline(1292);
            var1.getlocal(0).__getattr__("debugger").__getattr__("set_continue").__call__(var2);
            var1.setline(1293);
            var5 = var1.getname("None");
            var1.setlocal(17, var5);
            var5 = null;
         } catch (Throwable var10) {
            PyException var16 = Py.setException(var10, var1);
            if (var16.match(var1.getname("KeyboardInterrupt"))) {
               var1.setline(1295);
               throw Py.makeException();
            }

            var1.setline(1297);
            var6 = var1.getname("sys").__getattr__("exc_info").__call__(var2);
            var1.setlocal(17, var6);
            var6 = null;
            var1.setline(1298);
            var1.getlocal(0).__getattr__("debugger").__getattr__("set_continue").__call__(var2);
         }

         var1.setline(1300);
         var5 = var1.getlocal(0).__getattr__("_fakeout").__getattr__("getvalue").__call__(var2);
         var1.setlocal(18, var5);
         var5 = null;
         var1.setline(1301);
         var1.getlocal(0).__getattr__("_fakeout").__getattr__("truncate").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(1302);
         var5 = var1.getlocal(8);
         var1.setlocal(19, var5);
         var5 = null;
         var1.setline(1306);
         var5 = var1.getlocal(17);
         var10000 = var5._is(var1.getname("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1307);
            if (var1.getlocal(10).__call__(var2, var1.getlocal(12).__getattr__("want"), var1.getlocal(18), var1.getlocal(0).__getattr__("optionflags")).__nonzero__()) {
               var1.setline(1308);
               var5 = var1.getlocal(7);
               var1.setlocal(19, var5);
               var5 = null;
            }
         } else {
            var1.setline(1312);
            var5 = var1.getname("sys").__getattr__("exc_info").__call__(var2);
            var1.setlocal(20, var5);
            var5 = null;
            var1.setline(1313);
            var10000 = var1.getname("traceback").__getattr__("format_exception_only");
            var13 = Py.EmptyObjects;
            String[] var15 = new String[0];
            var10000 = var10000._callextra(var13, var15, var1.getlocal(20).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null), (PyObject)null);
            var5 = null;
            var5 = var10000.__getitem__(Py.newInteger(-1));
            var1.setlocal(21, var5);
            var5 = null;
            var1.setline(1314);
            if (var1.getlocal(13).__not__().__nonzero__()) {
               var1.setline(1315);
               var5 = var1.getlocal(18);
               var5 = var5._iadd(var1.getname("_exception_traceback").__call__(var2, var1.getlocal(20)));
               var1.setlocal(18, var5);
            }

            var1.setline(1319);
            var5 = var1.getlocal(12).__getattr__("exc_msg");
            var10000 = var5._is(var1.getname("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1320);
               var5 = var1.getlocal(9);
               var1.setlocal(19, var5);
               var5 = null;
            } else {
               var1.setline(1323);
               if (var1.getlocal(10).__call__(var2, var1.getlocal(12).__getattr__("exc_msg"), var1.getlocal(21), var1.getlocal(0).__getattr__("optionflags")).__nonzero__()) {
                  var1.setline(1324);
                  var5 = var1.getlocal(7);
                  var1.setlocal(19, var5);
                  var5 = null;
               } else {
                  var1.setline(1327);
                  if (var1.getlocal(0).__getattr__("optionflags")._and(var1.getname("IGNORE_EXCEPTION_DETAIL")).__nonzero__()) {
                     var1.setline(1328);
                     var5 = var1.getname("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?:[^:]*\\.)?([^:]*:)"), (PyObject)var1.getlocal(12).__getattr__("exc_msg"));
                     var1.setlocal(22, var5);
                     var5 = null;
                     var1.setline(1329);
                     var5 = var1.getname("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?:[^:]*\\.)?([^:]*:)"), (PyObject)var1.getlocal(21));
                     var1.setlocal(23, var5);
                     var5 = null;
                     var1.setline(1330);
                     var10000 = var1.getlocal(22);
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(23);
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(10).__call__(var2, var1.getlocal(22).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(23).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(0).__getattr__("optionflags"));
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(1332);
                        var5 = var1.getlocal(7);
                        var1.setlocal(19, var5);
                        var5 = null;
                     }
                  }
               }
            }
         }

         var1.setline(1335);
         var5 = var1.getlocal(19);
         var10000 = var5._is(var1.getlocal(7));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1336);
            if (var1.getlocal(13).__not__().__nonzero__()) {
               var1.setline(1337);
               var1.getlocal(0).__getattr__("report_success").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(12), var1.getlocal(18));
            }
         } else {
            var1.setline(1338);
            var5 = var1.getlocal(19);
            var10000 = var5._is(var1.getlocal(8));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1339);
               if (var1.getlocal(13).__not__().__nonzero__()) {
                  var1.setline(1340);
                  var1.getlocal(0).__getattr__("report_failure").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(12), var1.getlocal(18));
               }

               var1.setline(1341);
               var5 = var1.getlocal(4);
               var5 = var5._iadd(Py.newInteger(1));
               var1.setlocal(4, var5);
            } else {
               var1.setline(1342);
               var5 = var1.getlocal(19);
               var10000 = var5._is(var1.getlocal(9));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1343);
                  if (var1.getlocal(13).__not__().__nonzero__()) {
                     var1.setline(1344);
                     var1.getlocal(0).__getattr__("report_unexpected_exception").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(12), var1.getlocal(20));
                  }

                  var1.setline(1346);
                  var5 = var1.getlocal(4);
                  var5 = var5._iadd(Py.newInteger(1));
                  var1.setlocal(4, var5);
               } else {
                  var1.setline(1348);
                  if (var1.getglobal("__debug__").__nonzero__() && !var1.getname("False").__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{PyString.fromInterned("unknown outcome"), var1.getlocal(19)}));
                  }
               }
            }
         }
      }
   }

   public PyObject _DocTestRunner__record_outcome$54(PyFrame var1, ThreadState var2) {
      var1.setline(1361);
      PyString.fromInterned("\n        Record the fact that the given DocTest (`test`) generated `f`\n        failures out of `t` tried examples.\n        ");
      var1.setline(1362);
      PyObject var3 = var1.getlocal(0).__getattr__("_name2ft").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("name"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0)})));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1363);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2)._add(var1.getlocal(4)), var1.getlocal(3)._add(var1.getlocal(5))});
      var1.getlocal(0).__getattr__("_name2ft").__setitem__((PyObject)var1.getlocal(1).__getattr__("name"), var6);
      var3 = null;
      var1.setline(1364);
      PyObject var10000 = var1.getlocal(0);
      String var7 = "failures";
      PyObject var8 = var10000;
      var5 = var8.__getattr__(var7);
      var5 = var5._iadd(var1.getlocal(2));
      var8.__setattr__(var7, var5);
      var1.setline(1365);
      var10000 = var1.getlocal(0);
      var7 = "tries";
      var8 = var10000;
      var5 = var8.__getattr__(var7);
      var5 = var5._iadd(var1.getlocal(3));
      var8.__setattr__(var7, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _DocTestRunner__patched_linecache_getlines$55(PyFrame var1, ThreadState var2) {
      var1.setline(1371);
      PyObject var3 = var1.getlocal(0).__getattr__("_DocTestRunner__LINECACHE_FILENAME_RE").__getattr__("match").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1372);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
         var10000 = var3._eq(var1.getlocal(0).__getattr__("test").__getattr__("name"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1373);
         var3 = var1.getlocal(0).__getattr__("test").__getattr__("examples").__getitem__(var1.getglobal("int").__call__(var2, var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("examplenum"))));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1374);
         var3 = var1.getlocal(4).__getattr__("source");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1375);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(1376);
            var3 = var1.getlocal(5).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"), (PyObject)PyString.fromInterned("backslashreplace"));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(1377);
         var3 = var1.getlocal(5).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1379);
         var3 = var1.getlocal(0).__getattr__("save_linecache_getlines").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject run$56(PyFrame var1, ThreadState var2) {
      var1.setline(1400);
      PyString.fromInterned("\n        Run the examples in `test`, and display the results using the\n        writer function `out`.\n\n        The examples are run in the namespace `test.globs`.  If\n        `clear_globs` is true (the default), then this namespace will\n        be cleared after the test runs, to help with garbage\n        collection.  If you would like to examine the namespace after\n        the test completes, then use `clear_globs=False`.\n\n        `compileflags` gives the set of flags that should be used by\n        the Python compiler when running the examples.  If not\n        specified, then it will default to the set of future-import\n        flags that apply to `globs`.\n\n        The output of each example is checked using\n        `DocTestRunner.check_output`, and the results are formatted by\n        the `DocTestRunner.report_*` methods.\n        ");
      var1.setline(1401);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(1403);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1404);
         var3 = var1.getglobal("_extract_future_flags").__call__(var2, var1.getlocal(1).__getattr__("globs"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1406);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1407);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1408);
         var3 = var1.getlocal(5).__getattr__("write");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1409);
      var3 = var1.getlocal(0).__getattr__("_fakeout");
      var1.getglobal("sys").__setattr__("stdout", var3);
      var3 = null;
      var1.setline(1416);
      var3 = var1.getglobal("pdb").__getattr__("set_trace");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1417);
      var3 = var1.getglobal("_OutputRedirectingPdb").__call__(var2, var1.getlocal(5));
      var1.getlocal(0).__setattr__("debugger", var3);
      var3 = null;
      var1.setline(1418);
      var1.getlocal(0).__getattr__("debugger").__getattr__("reset").__call__(var2);
      var1.setline(1419);
      var3 = var1.getlocal(0).__getattr__("debugger").__getattr__("set_trace");
      var1.getglobal("pdb").__setattr__("set_trace", var3);
      var3 = null;
      var1.setline(1423);
      var3 = var1.getglobal("linecache").__getattr__("getlines");
      var1.getlocal(0).__setattr__("save_linecache_getlines", var3);
      var3 = null;
      var1.setline(1424);
      var3 = var1.getlocal(0).__getattr__("_DocTestRunner__patched_linecache_getlines");
      var1.getglobal("linecache").__setattr__("getlines", var3);
      var3 = null;
      var1.setline(1427);
      var3 = var1.getglobal("sys").__getattr__("displayhook");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1428);
      var3 = var1.getglobal("sys").__getattr__("__displayhook__");
      var1.getglobal("sys").__setattr__("displayhook", var3);
      var3 = null;
      var3 = null;

      PyObject var5;
      Throwable var9;
      label42: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(1431);
            var4 = var1.getlocal(0).__getattr__("_DocTestRunner__run").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         } catch (Throwable var7) {
            var9 = var7;
            var10001 = false;
            break label42;
         }

         var1.setline(1433);
         var5 = var1.getlocal(5);
         var1.getglobal("sys").__setattr__("stdout", var5);
         var5 = null;
         var1.setline(1434);
         var5 = var1.getlocal(6);
         var1.getglobal("pdb").__setattr__("set_trace", var5);
         var5 = null;
         var1.setline(1435);
         var5 = var1.getlocal(0).__getattr__("save_linecache_getlines");
         var1.getglobal("linecache").__setattr__("getlines", var5);
         var5 = null;
         var1.setline(1436);
         var5 = var1.getlocal(7);
         var1.getglobal("sys").__setattr__("displayhook", var5);
         var5 = null;
         var1.setline(1437);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(1438);
            var1.getlocal(1).__getattr__("globs").__getattr__("clear").__call__(var2);
         }

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var6) {
            var9 = var6;
            var10001 = false;
         }
      }

      Throwable var8 = var9;
      Py.addTraceback(var8, var1);
      var1.setline(1433);
      var5 = var1.getlocal(5);
      var1.getglobal("sys").__setattr__("stdout", var5);
      var5 = null;
      var1.setline(1434);
      var5 = var1.getlocal(6);
      var1.getglobal("pdb").__setattr__("set_trace", var5);
      var5 = null;
      var1.setline(1435);
      var5 = var1.getlocal(0).__getattr__("save_linecache_getlines");
      var1.getglobal("linecache").__setattr__("getlines", var5);
      var5 = null;
      var1.setline(1436);
      var5 = var1.getlocal(7);
      var1.getglobal("sys").__setattr__("displayhook", var5);
      var5 = null;
      var1.setline(1437);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1438);
         var1.getlocal(1).__getattr__("globs").__getattr__("clear").__call__(var2);
      }

      throw (Throwable)var8;
   }

   public PyObject summarize$57(PyFrame var1, ThreadState var2) {
      var1.setline(1453);
      PyString.fromInterned("\n        Print a summary of all the test cases that have been run by\n        this DocTestRunner, and return a tuple `(f, t)`, where `f` is\n        the total number of failed examples, and `t` is the total\n        number of tried examples.\n\n        The optional `verbose` argument controls how detailed the\n        summary is.  If the verbosity is not specified, then the\n        DocTestRunner's verbosity is used.\n        ");
      var1.setline(1454);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1455);
         var3 = var1.getlocal(0).__getattr__("_verbose");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1456);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var10);
      var3 = null;
      var1.setline(1457);
      var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(1458);
      var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(1459);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(5, var11);
      var1.setlocal(6, var11);
      var1.setline(1460);
      var3 = var1.getlocal(0).__getattr__("_name2ft").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1460);
         PyObject var4 = var3.__iternext__();
         PyObject[] var6;
         PyObject[] var8;
         if (var4 == null) {
            var1.setline(1471);
            PyObject var12;
            PyObject[] var15;
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(1472);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(1473);
                  Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
                  Py.println(PyString.fromInterned("items had no tests:"));
                  var1.setline(1474);
                  var1.getlocal(2).__getattr__("sort").__call__(var2);
                  var1.setline(1475);
                  var3 = var1.getlocal(2).__iter__();

                  while(true) {
                     var1.setline(1475);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(11, var4);
                     var1.setline(1476);
                     Py.printComma(PyString.fromInterned("   "));
                     Py.println(var1.getlocal(11));
                  }
               }

               var1.setline(1477);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(1478);
                  Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
                  Py.println(PyString.fromInterned("items passed all tests:"));
                  var1.setline(1479);
                  var1.getlocal(3).__getattr__("sort").__call__(var2);
                  var1.setline(1480);
                  var3 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(1480);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var15 = Py.unpackSequence(var4, 2);
                     var12 = var15[0];
                     var1.setlocal(11, var12);
                     var6 = null;
                     var12 = var15[1];
                     var1.setlocal(12, var12);
                     var6 = null;
                     var1.setline(1481);
                     Py.println(PyString.fromInterned(" %3d tests in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(11)})));
                  }
               }
            }

            var1.setline(1482);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1483);
               Py.println(var1.getlocal(0).__getattr__("DIVIDER"));
               var1.setline(1484);
               Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
               Py.println(PyString.fromInterned("items had failures:"));
               var1.setline(1485);
               var1.getlocal(4).__getattr__("sort").__call__(var2);
               var1.setline(1486);
               var3 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(1486);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var15 = Py.unpackSequence(var4, 2);
                  var12 = var15[0];
                  var1.setlocal(11, var12);
                  var6 = null;
                  var12 = var15[1];
                  PyObject[] var13 = Py.unpackSequence(var12, 2);
                  PyObject var14 = var13[0];
                  var1.setlocal(9, var14);
                  var8 = null;
                  var14 = var13[1];
                  var1.setlocal(10, var14);
                  var8 = null;
                  var6 = null;
                  var1.setline(1487);
                  Py.println(PyString.fromInterned(" %3d of %3d in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)})));
               }
            }

            var1.setline(1488);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(1489);
               Py.printComma(var1.getlocal(5));
               Py.printComma(PyString.fromInterned("tests in"));
               Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_name2ft")));
               Py.println(PyString.fromInterned("items."));
               var1.setline(1490);
               Py.printComma(var1.getlocal(5)._sub(var1.getlocal(6)));
               Py.printComma(PyString.fromInterned("passed and"));
               Py.printComma(var1.getlocal(6));
               Py.println(PyString.fromInterned("failed."));
            }

            var1.setline(1491);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1492);
               Py.printComma(PyString.fromInterned("***Test Failed***"));
               Py.printComma(var1.getlocal(6));
               Py.println(PyString.fromInterned("failures."));
            } else {
               var1.setline(1493);
               if (var1.getlocal(1).__nonzero__()) {
                  var1.setline(1494);
                  Py.println(PyString.fromInterned("Test passed."));
               }
            }

            var1.setline(1495);
            var3 = var1.getglobal("TestResults").__call__(var2, var1.getlocal(6), var1.getlocal(5));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(7, var4);
         var1.setline(1461);
         PyObject var5 = var1.getlocal(7);
         var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var6[1];
         var8 = Py.unpackSequence(var7, 2);
         PyObject var9 = var8[0];
         var1.setlocal(9, var9);
         var9 = null;
         var9 = var8[1];
         var1.setlocal(10, var9);
         var9 = null;
         var7 = null;
         var5 = null;
         var1.setline(1462);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var5 = var1.getlocal(9);
            var10000 = var5._le(var1.getlocal(10));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(1463);
         var5 = var1.getlocal(5);
         var5 = var5._iadd(var1.getlocal(10));
         var1.setlocal(5, var5);
         var1.setline(1464);
         var5 = var1.getlocal(6);
         var5 = var5._iadd(var1.getlocal(9));
         var1.setlocal(6, var5);
         var1.setline(1465);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1466);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(8));
         } else {
            var1.setline(1467);
            var5 = var1.getlocal(9);
            var10000 = var5._eq(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1468);
               var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(10)})));
            } else {
               var1.setline(1470);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
            }
         }
      }
   }

   public PyObject merge$58(PyFrame var1, ThreadState var2) {
      var1.setline(1501);
      PyObject var3 = var1.getlocal(0).__getattr__("_name2ft");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1502);
      var3 = var1.getlocal(1).__getattr__("_name2ft").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1502);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         PyObject[] var7 = Py.unpackSequence(var6, 2);
         PyObject var8 = var7[0];
         var1.setlocal(4, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(5, var8);
         var8 = null;
         var6 = null;
         var1.setline(1503);
         PyObject var9 = var1.getlocal(3);
         PyObject var10000 = var9._in(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1508);
            var9 = var1.getlocal(2).__getitem__(var1.getlocal(3));
            PyObject[] var10 = Py.unpackSequence(var9, 2);
            PyObject var11 = var10[0];
            var1.setlocal(6, var11);
            var7 = null;
            var11 = var10[1];
            var1.setlocal(7, var11);
            var7 = null;
            var5 = null;
            var1.setline(1509);
            var9 = var1.getlocal(4)._add(var1.getlocal(6));
            var1.setlocal(4, var9);
            var5 = null;
            var1.setline(1510);
            var9 = var1.getlocal(5)._add(var1.getlocal(7));
            var1.setlocal(5, var9);
            var5 = null;
         }

         var1.setline(1511);
         PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.getlocal(2).__setitem__((PyObject)var1.getlocal(3), var12);
         var5 = null;
      }
   }

   public PyObject OutputChecker$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A class used to check the whether the actual output from a doctest\n    example matches the expected output.  `OutputChecker` defines two\n    methods: `check_output`, which compares a given pair of outputs,\n    and returns true if they match; and `output_difference`, which\n    returns a string describing the differences between two outputs.\n    "));
      var1.setline(1520);
      PyString.fromInterned("\n    A class used to check the whether the actual output from a doctest\n    example matches the expected output.  `OutputChecker` defines two\n    methods: `check_output`, which compares a given pair of outputs,\n    and returns true if they match; and `output_difference`, which\n    returns a string describing the differences between two outputs.\n    ");
      var1.setline(1521);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, check_output$60, PyString.fromInterned("\n        Return True iff the actual output from an example (`got`)\n        matches the expected output (`want`).  These strings are\n        always considered to match if they are identical; but\n        depending on what option flags the test runner is using,\n        several non-exact match types are also possible.  See the\n        documentation for `TestRunner` for more information about\n        option flags.\n        "));
      var1.setlocal("check_output", var4);
      var3 = null;
      var1.setline(1575);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _do_a_fancy_diff$61, (PyObject)null);
      var1.setlocal("_do_a_fancy_diff", var4);
      var3 = null;
      var1.setline(1598);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, output_difference$62, PyString.fromInterned("\n        Return a string describing the differences between the\n        expected output for a given example (`example`) and the actual\n        output (`got`).  `optionflags` is the set of option flags used\n        to compare `want` and `got`.\n        "));
      var1.setlocal("output_difference", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject check_output$60(PyFrame var1, ThreadState var2) {
      var1.setline(1530);
      PyString.fromInterned("\n        Return True iff the actual output from an example (`got`)\n        matches the expected output (`want`).  These strings are\n        always considered to match if they are identical; but\n        depending on what option flags the test runner is using,\n        several non-exact match types are also possible.  See the\n        documentation for `TestRunner` for more information about\n        option flags.\n        ");
      var1.setline(1533);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1534);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1538);
         PyTuple var4;
         if (var1.getlocal(3)._and(var1.getglobal("DONT_ACCEPT_TRUE_FOR_1")).__not__().__nonzero__()) {
            var1.setline(1539);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
            var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("True\n"), PyString.fromInterned("1\n")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1540);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1541);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
            var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("False\n"), PyString.fromInterned("0\n")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1542);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1546);
         PyObject var5;
         if (var1.getlocal(3)._and(var1.getglobal("DONT_ACCEPT_BLANKLINE")).__not__().__nonzero__()) {
            var1.setline(1548);
            var5 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?m)^%s\\s*?$")._mod(var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getglobal("BLANKLINE_MARKER"))), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(1));
            var1.setlocal(1, var5);
            var4 = null;
            var1.setline(1552);
            var5 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?m)^\\s*?$"), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(2));
            var1.setlocal(2, var5);
            var4 = null;
            var1.setline(1553);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1554);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1559);
         if (var1.getlocal(3)._and(var1.getglobal("NORMALIZE_WHITESPACE")).__nonzero__()) {
            var1.setline(1560);
            var5 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(2).__getattr__("split").__call__(var2));
            var1.setlocal(2, var5);
            var4 = null;
            var1.setline(1561);
            var5 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(1).__getattr__("split").__call__(var2));
            var1.setlocal(1, var5);
            var4 = null;
            var1.setline(1562);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(var1.getlocal(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1563);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1567);
         if (var1.getlocal(3)._and(var1.getglobal("ELLIPSIS")).__nonzero__()) {
            var1.setline(1568);
            if (var1.getglobal("_ellipsis_match").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__()) {
               var1.setline(1569);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(1572);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _do_a_fancy_diff$61(PyFrame var1, ThreadState var2) {
      var1.setline(1577);
      PyObject var3;
      if (var1.getlocal(3)._and(var1.getglobal("REPORT_UDIFF")._or(var1.getglobal("REPORT_CDIFF"))._or(var1.getglobal("REPORT_NDIFF"))).__not__().__nonzero__()) {
         var1.setline(1580);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1592);
         if (var1.getlocal(3)._and(var1.getglobal("REPORT_NDIFF")).__nonzero__()) {
            var1.setline(1593);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1596);
            PyObject var4 = var1.getlocal(1).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            PyObject var10000 = var4._gt(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(2).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               var10000 = var4._gt(Py.newInteger(2));
               var4 = null;
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject output_difference$62(PyFrame var1, ThreadState var2) {
      var1.setline(1604);
      PyString.fromInterned("\n        Return a string describing the differences between the\n        expected output for a given example (`example`) and the actual\n        output (`got`).  `optionflags` is the set of option flags used\n        to compare `want` and `got`.\n        ");
      var1.setline(1605);
      PyObject var3 = var1.getlocal(1).__getattr__("want");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1608);
      if (var1.getlocal(3)._and(var1.getglobal("DONT_ACCEPT_BLANKLINE")).__not__().__nonzero__()) {
         var1.setline(1609);
         var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?m)^[ ]*(?=\n)"), (PyObject)var1.getglobal("BLANKLINE_MARKER"), (PyObject)var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1612);
      PyObject var10000;
      PyString var7;
      if (!var1.getlocal(0).__getattr__("_do_a_fancy_diff").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
         var1.setline(1637);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(1638);
            var3 = PyString.fromInterned("Expected:\n%sGot:\n%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("_indent").__call__(var2, var1.getlocal(4)), var1.getglobal("_indent").__call__(var2, var1.getlocal(2))}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1639);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1640);
               var3 = PyString.fromInterned("Expected:\n%sGot nothing\n")._mod(var1.getglobal("_indent").__call__(var2, var1.getlocal(4)));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1641);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(1642);
                  var3 = PyString.fromInterned("Expected nothing\nGot:\n%s")._mod(var1.getglobal("_indent").__call__(var2, var1.getlocal(2)));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1644);
                  var7 = PyString.fromInterned("Expected nothing\nGot nothing\n");
                  var1.f_lasti = -1;
                  return var7;
               }
            }
         }
      } else {
         var1.setline(1614);
         var3 = var1.getlocal(4).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1615);
         var3 = var1.getlocal(2).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1617);
         String[] var4;
         PyObject[] var6;
         if (var1.getlocal(3)._and(var1.getglobal("REPORT_UDIFF")).__nonzero__()) {
            var1.setline(1618);
            var10000 = var1.getglobal("difflib").__getattr__("unified_diff");
            var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), Py.newInteger(2)};
            var4 = new String[]{"n"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(1619);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(7)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(1620);
            var7 = PyString.fromInterned("unified diff with -expected +actual");
            var1.setlocal(8, var7);
            var3 = null;
         } else {
            var1.setline(1621);
            if (var1.getlocal(3)._and(var1.getglobal("REPORT_CDIFF")).__nonzero__()) {
               var1.setline(1622);
               var10000 = var1.getglobal("difflib").__getattr__("context_diff");
               var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), Py.newInteger(2)};
               var4 = new String[]{"n"};
               var10000 = var10000.__call__(var2, var6, var4);
               var3 = null;
               var3 = var10000;
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1623);
               var3 = var1.getglobal("list").__call__(var2, var1.getlocal(7)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1624);
               var7 = PyString.fromInterned("context diff with expected followed by actual");
               var1.setlocal(8, var7);
               var3 = null;
            } else {
               var1.setline(1625);
               if (var1.getlocal(3)._and(var1.getglobal("REPORT_NDIFF")).__nonzero__()) {
                  var1.setline(1626);
                  var10000 = var1.getglobal("difflib").__getattr__("Differ");
                  var6 = new PyObject[]{var1.getglobal("difflib").__getattr__("IS_CHARACTER_JUNK")};
                  var4 = new String[]{"charjunk"};
                  var10000 = var10000.__call__(var2, var6, var4);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(1627);
                  var3 = var1.getglobal("list").__call__(var2, var1.getlocal(9).__getattr__("compare").__call__(var2, var1.getlocal(5), var1.getlocal(6)));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1628);
                  var7 = PyString.fromInterned("ndiff with -expected +actual");
                  var1.setlocal(8, var7);
                  var3 = null;
               } else {
                  var1.setline(1630);
                  if (var1.getglobal("__debug__").__nonzero__() && !Py.newInteger(0).__nonzero__()) {
                     throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Bad diff option"));
                  }
               }
            }
         }

         var1.setline(1632);
         PyList var9 = new PyList();
         var3 = var9.__getattr__("append");
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(1632);
         var3 = var1.getlocal(7).__iter__();

         while(true) {
            var1.setline(1632);
            PyObject var5 = var3.__iternext__();
            if (var5 == null) {
               var1.setline(1632);
               var1.dellocal(10);
               PyList var8 = var9;
               var1.setlocal(7, var8);
               var3 = null;
               var1.setline(1633);
               var3 = PyString.fromInterned("Differences (%s):\n")._mod(var1.getlocal(8))._add(var1.getglobal("_indent").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(7))));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(11, var5);
            var1.setline(1632);
            var1.getlocal(10).__call__(var2, var1.getlocal(11).__getattr__("rstrip").__call__(var2)._add(PyString.fromInterned("\n")));
         }
      }
   }

   public PyObject DocTestFailure$63(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A DocTest example has failed in debugging mode.\n\n    The exception instance has variables:\n\n    - test: the DocTest object being run\n\n    - example: the Example object that failed\n\n    - got: the actual output\n    "));
      var1.setline(1656);
      PyString.fromInterned("A DocTest example has failed in debugging mode.\n\n    The exception instance has variables:\n\n    - test: the DocTest object being run\n\n    - example: the Example object that failed\n\n    - got: the actual output\n    ");
      var1.setline(1657);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$64, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1662);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$65, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$64(PyFrame var1, ThreadState var2) {
      var1.setline(1658);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(1659);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("example", var3);
      var3 = null;
      var1.setline(1660);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("got", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$65(PyFrame var1, ThreadState var2) {
      var1.setline(1663);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject UnexpectedException$66(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A DocTest example has encountered an unexpected exception\n\n    The exception instance has variables:\n\n    - test: the DocTest object being run\n\n    - example: the Example object that failed\n\n    - exc_info: the exception info\n    "));
      var1.setline(1675);
      PyString.fromInterned("A DocTest example has encountered an unexpected exception\n\n    The exception instance has variables:\n\n    - test: the DocTest object being run\n\n    - example: the Example object that failed\n\n    - exc_info: the exception info\n    ");
      var1.setline(1676);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$67, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1681);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$68, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$67(PyFrame var1, ThreadState var2) {
      var1.setline(1677);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(1678);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("example", var3);
      var3 = null;
      var1.setline(1679);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("exc_info", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$68(PyFrame var1, ThreadState var2) {
      var1.setline(1682);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DebugRunner$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Run doc tests but raise an exception as soon as there is a failure.\n\n       If an unexpected exception occurs, an UnexpectedException is raised.\n       It contains the test, the example, and the original exception:\n\n         >>> runner = DebugRunner(verbose=False)\n         >>> test = DocTestParser().get_doctest('>>> raise KeyError\\n42',\n         ...                                    {}, 'foo', 'foo.py', 0)\n         >>> try:\n         ...     runner.run(test)\n         ... except UnexpectedException, failure:\n         ...     pass\n\n         >>> failure.test is test\n         True\n\n         >>> failure.example.want\n         '42\\n'\n\n         >>> exc_info = failure.exc_info\n         >>> raise exc_info[0], exc_info[1], exc_info[2]\n         Traceback (most recent call last):\n         ...\n         KeyError\n\n       We wrap the original exception to give the calling application\n       access to the test and example information.\n\n       If the output doesn't match, then a DocTestFailure is raised:\n\n         >>> test = DocTestParser().get_doctest('''\n         ...      >>> x = 1\n         ...      >>> x\n         ...      2\n         ...      ''', {}, 'foo', 'foo.py', 0)\n\n         >>> try:\n         ...    runner.run(test)\n         ... except DocTestFailure, failure:\n         ...    pass\n\n       DocTestFailure objects provide access to the test:\n\n         >>> failure.test is test\n         True\n\n       As well as to the example:\n\n         >>> failure.example.want\n         '2\\n'\n\n       and the actual output:\n\n         >>> failure.got\n         '1\\n'\n\n       If a failure or error occurs, the globals are left intact:\n\n         >>> if '__builtins__' in test.globs:\n         ...     del test.globs['__builtins__']\n         >>> test.globs\n         {'x': 1}\n\n         >>> test = DocTestParser().get_doctest('''\n         ...      >>> x = 2\n         ...      >>> raise KeyError\n         ...      ''', {}, 'foo', 'foo.py', 0)\n\n         >>> runner.run(test)\n         Traceback (most recent call last):\n         ...\n         UnexpectedException: <DocTest foo from foo.py:0 (2 examples)>\n\n         >>> if '__builtins__' in test.globs:\n         ...     del test.globs['__builtins__']\n         >>> test.globs\n         {'x': 2}\n\n       But the globals are cleared if there is no error:\n\n         >>> test = DocTestParser().get_doctest('''\n         ...      >>> x = 2\n         ...      ''', {}, 'foo', 'foo.py', 0)\n\n         >>> runner.run(test)\n         TestResults(failed=0, attempted=1)\n\n         >>> test.globs\n         {}\n\n       "));
      var1.setline(1775);
      PyString.fromInterned("Run doc tests but raise an exception as soon as there is a failure.\n\n       If an unexpected exception occurs, an UnexpectedException is raised.\n       It contains the test, the example, and the original exception:\n\n         >>> runner = DebugRunner(verbose=False)\n         >>> test = DocTestParser().get_doctest('>>> raise KeyError\\n42',\n         ...                                    {}, 'foo', 'foo.py', 0)\n         >>> try:\n         ...     runner.run(test)\n         ... except UnexpectedException, failure:\n         ...     pass\n\n         >>> failure.test is test\n         True\n\n         >>> failure.example.want\n         '42\\n'\n\n         >>> exc_info = failure.exc_info\n         >>> raise exc_info[0], exc_info[1], exc_info[2]\n         Traceback (most recent call last):\n         ...\n         KeyError\n\n       We wrap the original exception to give the calling application\n       access to the test and example information.\n\n       If the output doesn't match, then a DocTestFailure is raised:\n\n         >>> test = DocTestParser().get_doctest('''\n         ...      >>> x = 1\n         ...      >>> x\n         ...      2\n         ...      ''', {}, 'foo', 'foo.py', 0)\n\n         >>> try:\n         ...    runner.run(test)\n         ... except DocTestFailure, failure:\n         ...    pass\n\n       DocTestFailure objects provide access to the test:\n\n         >>> failure.test is test\n         True\n\n       As well as to the example:\n\n         >>> failure.example.want\n         '2\\n'\n\n       and the actual output:\n\n         >>> failure.got\n         '1\\n'\n\n       If a failure or error occurs, the globals are left intact:\n\n         >>> if '__builtins__' in test.globs:\n         ...     del test.globs['__builtins__']\n         >>> test.globs\n         {'x': 1}\n\n         >>> test = DocTestParser().get_doctest('''\n         ...      >>> x = 2\n         ...      >>> raise KeyError\n         ...      ''', {}, 'foo', 'foo.py', 0)\n\n         >>> runner.run(test)\n         Traceback (most recent call last):\n         ...\n         UnexpectedException: <DocTest foo from foo.py:0 (2 examples)>\n\n         >>> if '__builtins__' in test.globs:\n         ...     del test.globs['__builtins__']\n         >>> test.globs\n         {'x': 2}\n\n       But the globals are cleared if there is no error:\n\n         >>> test = DocTestParser().get_doctest('''\n         ...      >>> x = 2\n         ...      ''', {}, 'foo', 'foo.py', 0)\n\n         >>> runner.run(test)\n         TestResults(failed=0, attempted=1)\n\n         >>> test.globs\n         {}\n\n       ");
      var1.setline(1777);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, run$70, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(1783);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, report_unexpected_exception$71, (PyObject)null);
      var1.setlocal("report_unexpected_exception", var4);
      var3 = null;
      var1.setline(1786);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, report_failure$72, (PyObject)null);
      var1.setlocal("report_failure", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$70(PyFrame var1, ThreadState var2) {
      var1.setline(1778);
      PyObject var10000 = var1.getglobal("DocTestRunner").__getattr__("run");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("False")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(1779);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1780);
         var1.getlocal(1).__getattr__("globs").__getattr__("clear").__call__(var2);
      }

      var1.setline(1781);
      var4 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject report_unexpected_exception$71(PyFrame var1, ThreadState var2) {
      var1.setline(1784);
      throw Py.makeException(var1.getglobal("UnexpectedException").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)));
   }

   public PyObject report_failure$72(PyFrame var1, ThreadState var2) {
      var1.setline(1787);
      throw Py.makeException(var1.getglobal("DocTestFailure").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)));
   }

   public PyObject testmod$73(PyFrame var1, ThreadState var2) {
      var1.setline(1863);
      PyString.fromInterned("m=None, name=None, globs=None, verbose=None, report=True,\n       optionflags=0, extraglobs=None, raise_on_error=False,\n       exclude_empty=False\n\n    Test examples in docstrings in functions and classes reachable\n    from module m (or the current module if m is not supplied), starting\n    with m.__doc__.\n\n    Also test examples reachable from dict m.__test__ if it exists and is\n    not None.  m.__test__ maps names to functions, classes and strings;\n    function and class docstrings are tested even if the name is private;\n    strings are tested directly, as if they were docstrings.\n\n    Return (#failures, #tests).\n\n    See help(doctest) for an overview.\n\n    Optional keyword arg \"name\" gives the name of the module; by default\n    use m.__name__.\n\n    Optional keyword arg \"globs\" gives a dict to be used as the globals\n    when executing examples; by default, use m.__dict__.  A copy of this\n    dict is actually used for each docstring, so that each docstring's\n    examples start with a clean slate.\n\n    Optional keyword arg \"extraglobs\" gives a dictionary that should be\n    merged into the globals that are used to execute examples.  By\n    default, no extra globals are used.  This is new in 2.4.\n\n    Optional keyword arg \"verbose\" prints lots of stuff if true, prints\n    only failures if false; by default, it's true iff \"-v\" is in sys.argv.\n\n    Optional keyword arg \"report\" prints a summary at the end when true,\n    else prints nothing at the end.  In verbose mode, the summary is\n    detailed, else very brief (in fact, empty if all tests passed).\n\n    Optional keyword arg \"optionflags\" or's together module constants,\n    and defaults to 0.  This is new in 2.3.  Possible values (see the\n    docs for details):\n\n        DONT_ACCEPT_TRUE_FOR_1\n        DONT_ACCEPT_BLANKLINE\n        NORMALIZE_WHITESPACE\n        ELLIPSIS\n        SKIP\n        IGNORE_EXCEPTION_DETAIL\n        REPORT_UDIFF\n        REPORT_CDIFF\n        REPORT_NDIFF\n        REPORT_ONLY_FIRST_FAILURE\n\n    Optional keyword arg \"raise_on_error\" raises an exception on the\n    first unexpected exception or failure. This allows failures to be\n    post-mortem debugged.\n\n    Advanced tomfoolery:  testmod runs methods of a local instance of\n    class doctest.Tester, then merges the results into (or creates)\n    global Tester instance doctest.master.  Methods of doctest.master\n    can be called directly too, if you want to do something unusual.\n    Passing report=0 to testmod is especially useful then, to delay\n    displaying a summary.  Invoke doctest.master.summarize(verbose)\n    when you're done fiddling.\n    ");
      var1.setline(1867);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1871);
         var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__main__"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1874);
      if (var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(1875);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("testmod: module required; %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)}))));
      } else {
         var1.setline(1878);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1879);
            var3 = var1.getlocal(0).__getattr__("__name__");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(1882);
         var10000 = var1.getglobal("DocTestFinder");
         PyObject[] var6 = new PyObject[]{var1.getlocal(8)};
         String[] var4 = new String[]{"exclude_empty"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1884);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(1885);
            var10000 = var1.getglobal("DebugRunner");
            var6 = new PyObject[]{var1.getlocal(3), var1.getlocal(5)};
            var4 = new String[]{"verbose", "optionflags"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(10, var3);
            var3 = null;
         } else {
            var1.setline(1887);
            var10000 = var1.getglobal("DocTestRunner");
            var6 = new PyObject[]{var1.getlocal(3), var1.getlocal(5)};
            var4 = new String[]{"verbose", "optionflags"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(10, var3);
            var3 = null;
         }

         var1.setline(1889);
         var10000 = var1.getlocal(9).__getattr__("find");
         var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(6)};
         var4 = new String[]{"globs", "extraglobs"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000.__iter__();

         while(true) {
            var1.setline(1889);
            PyObject var5 = var3.__iternext__();
            if (var5 == null) {
               var1.setline(1892);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(1893);
                  var1.getlocal(10).__getattr__("summarize").__call__(var2);
               }

               var1.setline(1895);
               var3 = var1.getglobal("master");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1896);
                  var3 = var1.getlocal(10);
                  var1.setglobal("master", var3);
                  var3 = null;
               } else {
                  var1.setline(1898);
                  var1.getglobal("master").__getattr__("merge").__call__(var2, var1.getlocal(10));
               }

               var1.setline(1900);
               var3 = var1.getglobal("TestResults").__call__(var2, var1.getlocal(10).__getattr__("failures"), var1.getlocal(10).__getattr__("tries"));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(11, var5);
            var1.setline(1890);
            var1.getlocal(10).__getattr__("run").__call__(var2, var1.getlocal(11));
         }
      }
   }

   public PyObject testfile$74(PyFrame var1, ThreadState var2) {
      var1.setline(1981);
      PyString.fromInterned("\n    Test examples in the given file.  Return (#failures, #tests).\n\n    Optional keyword arg \"module_relative\" specifies how filenames\n    should be interpreted:\n\n      - If \"module_relative\" is True (the default), then \"filename\"\n         specifies a module-relative path.  By default, this path is\n         relative to the calling module's directory; but if the\n         \"package\" argument is specified, then it is relative to that\n         package.  To ensure os-independence, \"filename\" should use\n         \"/\" characters to separate path segments, and should not\n         be an absolute path (i.e., it may not begin with \"/\").\n\n      - If \"module_relative\" is False, then \"filename\" specifies an\n        os-specific path.  The path may be absolute or relative (to\n        the current working directory).\n\n    Optional keyword arg \"name\" gives the name of the test; by default\n    use the file's basename.\n\n    Optional keyword argument \"package\" is a Python package or the\n    name of a Python package whose directory should be used as the\n    base directory for a module relative filename.  If no package is\n    specified, then the calling module's directory is used as the base\n    directory for module relative filenames.  It is an error to\n    specify \"package\" if \"module_relative\" is False.\n\n    Optional keyword arg \"globs\" gives a dict to be used as the globals\n    when executing examples; by default, use {}.  A copy of this dict\n    is actually used for each docstring, so that each docstring's\n    examples start with a clean slate.\n\n    Optional keyword arg \"extraglobs\" gives a dictionary that should be\n    merged into the globals that are used to execute examples.  By\n    default, no extra globals are used.\n\n    Optional keyword arg \"verbose\" prints lots of stuff if true, prints\n    only failures if false; by default, it's true iff \"-v\" is in sys.argv.\n\n    Optional keyword arg \"report\" prints a summary at the end when true,\n    else prints nothing at the end.  In verbose mode, the summary is\n    detailed, else very brief (in fact, empty if all tests passed).\n\n    Optional keyword arg \"optionflags\" or's together module constants,\n    and defaults to 0.  Possible values (see the docs for details):\n\n        DONT_ACCEPT_TRUE_FOR_1\n        DONT_ACCEPT_BLANKLINE\n        NORMALIZE_WHITESPACE\n        ELLIPSIS\n        SKIP\n        IGNORE_EXCEPTION_DETAIL\n        REPORT_UDIFF\n        REPORT_CDIFF\n        REPORT_NDIFF\n        REPORT_ONLY_FIRST_FAILURE\n\n    Optional keyword arg \"raise_on_error\" raises an exception on the\n    first unexpected exception or failure. This allows failures to be\n    post-mortem debugged.\n\n    Optional keyword arg \"parser\" specifies a DocTestParser (or\n    subclass) that should be used to extract tests from the files.\n\n    Optional keyword arg \"encoding\" specifies an encoding that should\n    be used to convert the file to unicode.\n\n    Advanced tomfoolery:  testmod runs methods of a local instance of\n    class doctest.Tester, then merges the results into (or creates)\n    global Tester instance doctest.master.  Methods of doctest.master\n    can be called directly too, if you want to do something unusual.\n    Passing report=0 to testmod is especially useful then, to delay\n    displaying a summary.  Invoke doctest.master.summarize(verbose)\n    when you're done fiddling.\n    ");
      var1.setline(1984);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1985);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Package may only be specified for module-relative paths.")));
      } else {
         var1.setline(1989);
         PyObject var3 = var1.getglobal("_load_testfile").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(0, var5);
         var5 = null;
         var3 = null;
         var1.setline(1992);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1993);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(1996);
         var3 = var1.getlocal(4);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1997);
            PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(4, var7);
            var3 = null;
         } else {
            var1.setline(1999);
            var3 = var1.getlocal(4).__getattr__("copy").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(2000);
         var3 = var1.getlocal(8);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2001);
            var1.getlocal(4).__getattr__("update").__call__(var2, var1.getlocal(8));
         }

         var1.setline(2002);
         PyString var8 = PyString.fromInterned("__name__");
         var10000 = var8._notin(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2003);
            var8 = PyString.fromInterned("__main__");
            var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("__name__"), var8);
            var3 = null;
         }

         var1.setline(2005);
         String[] var6;
         PyObject[] var9;
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(2006);
            var10000 = var1.getglobal("DebugRunner");
            var9 = new PyObject[]{var1.getlocal(5), var1.getlocal(7)};
            var6 = new String[]{"verbose", "optionflags"};
            var10000 = var10000.__call__(var2, var9, var6);
            var3 = null;
            var3 = var10000;
            var1.setlocal(13, var3);
            var3 = null;
         } else {
            var1.setline(2008);
            var10000 = var1.getglobal("DocTestRunner");
            var9 = new PyObject[]{var1.getlocal(5), var1.getlocal(7)};
            var6 = new String[]{"verbose", "optionflags"};
            var10000 = var10000.__call__(var2, var9, var6);
            var3 = null;
            var3 = var10000;
            var1.setlocal(13, var3);
            var3 = null;
         }

         var1.setline(2010);
         var3 = var1.getlocal(11);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2011);
            var3 = var1.getlocal(12).__getattr__("decode").__call__(var2, var1.getlocal(11));
            var1.setlocal(12, var3);
            var3 = null;
         }

         var1.setline(2014);
         var10000 = var1.getlocal(10).__getattr__("get_doctest");
         var9 = new PyObject[]{var1.getlocal(12), var1.getlocal(4), var1.getlocal(2), var1.getlocal(0), Py.newInteger(0)};
         var3 = var10000.__call__(var2, var9);
         var1.setlocal(14, var3);
         var3 = null;
         var1.setline(2015);
         var1.getlocal(13).__getattr__("run").__call__(var2, var1.getlocal(14));
         var1.setline(2017);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(2018);
            var1.getlocal(13).__getattr__("summarize").__call__(var2);
         }

         var1.setline(2020);
         var3 = var1.getglobal("master");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2021);
            var3 = var1.getlocal(13);
            var1.setglobal("master", var3);
            var3 = null;
         } else {
            var1.setline(2023);
            var1.getglobal("master").__getattr__("merge").__call__(var2, var1.getlocal(13));
         }

         var1.setline(2025);
         var3 = var1.getglobal("TestResults").__call__(var2, var1.getlocal(13).__getattr__("failures"), var1.getlocal(13).__getattr__("tries"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject run_docstring_examples$75(PyFrame var1, ThreadState var2) {
      var1.setline(2043);
      PyString.fromInterned("\n    Test examples in the given object's docstring (`f`), using `globs`\n    as globals.  Optional argument `name` is used in failure messages.\n    If the optional argument `verbose` is true, then generate output\n    even if there are no failures.\n\n    `compileflags` gives the set of flags that should be used by the\n    Python compiler when running the examples.  If not specified, then\n    it will default to the set of future-import flags that apply to\n    `globs`.\n\n    Optional keyword arg `optionflags` specifies options for the\n    testing and output.  See the documentation for `testmod` for more\n    information.\n    ");
      var1.setline(2045);
      PyObject var10000 = var1.getglobal("DocTestFinder");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getglobal("False")};
      String[] var4 = new String[]{"verbose", "recurse"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var7 = var10000;
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(2046);
      var10000 = var1.getglobal("DocTestRunner");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(5)};
      var4 = new String[]{"verbose", "optionflags"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var7 = var10000;
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(2047);
      var10000 = var1.getlocal(6).__getattr__("find");
      var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(3), var1.getlocal(1)};
      var4 = new String[]{"globs"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var7 = var10000.__iter__();

      while(true) {
         var1.setline(2047);
         PyObject var8 = var7.__iternext__();
         if (var8 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(8, var8);
         var1.setline(2048);
         var10000 = var1.getlocal(7).__getattr__("run");
         PyObject[] var5 = new PyObject[]{var1.getlocal(8), var1.getlocal(4)};
         String[] var6 = new String[]{"compileflags"};
         var10000.__call__(var2, var5, var6);
         var5 = null;
      }
   }

   public PyObject Tester$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2057);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$77, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2077);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runstring$78, (PyObject)null);
      var1.setlocal("runstring", var4);
      var3 = null;
      var1.setline(2086);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, rundoc$79, (PyObject)null);
      var1.setlocal("rundoc", var4);
      var3 = null;
      var1.setline(2095);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, rundict$80, (PyObject)null);
      var1.setlocal("rundict", var4);
      var3 = null;
      var1.setline(2103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run__test__$81, (PyObject)null);
      var1.setlocal("run__test__", var4);
      var3 = null;
      var1.setline(2109);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, summarize$82, (PyObject)null);
      var1.setlocal("summarize", var4);
      var3 = null;
      var1.setline(2112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, merge$83, (PyObject)null);
      var1.setlocal("merge", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$77(PyFrame var1, ThreadState var2) {
      var1.setline(2059);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("class Tester is deprecated; use class doctest.DocTestRunner instead"), var1.getglobal("DeprecationWarning"), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(2062);
      PyObject var5 = var1.getlocal(1);
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var5 = var1.getlocal(2);
         var10000 = var5._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(2063);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Tester.__init__: must specify mod or globs")));
      } else {
         var1.setline(2064);
         var5 = var1.getlocal(1);
         var10000 = var5._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(1)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(2065);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Tester.__init__: mod must be a module; %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)}))));
         } else {
            var1.setline(2067);
            var5 = var1.getlocal(2);
            var10000 = var5._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2068);
               var5 = var1.getlocal(1).__getattr__("__dict__");
               var1.setlocal(2, var5);
               var3 = null;
            }

            var1.setline(2069);
            var5 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("globs", var5);
            var3 = null;
            var1.setline(2071);
            var5 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("verbose", var5);
            var3 = null;
            var1.setline(2072);
            var5 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("optionflags", var5);
            var3 = null;
            var1.setline(2073);
            var5 = var1.getglobal("DocTestFinder").__call__(var2);
            var1.getlocal(0).__setattr__("testfinder", var5);
            var3 = null;
            var1.setline(2074);
            var10000 = var1.getglobal("DocTestRunner");
            var3 = new PyObject[]{var1.getlocal(3), var1.getlocal(4)};
            var4 = new String[]{"verbose", "optionflags"};
            var10000 = var10000.__call__(var2, var3, var4);
            var3 = null;
            var5 = var10000;
            var1.getlocal(0).__setattr__("testrunner", var5);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject runstring$78(PyFrame var1, ThreadState var2) {
      var1.setline(2078);
      PyObject var10000 = var1.getglobal("DocTestParser").__call__(var2).__getattr__("get_doctest");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("globs"), var1.getlocal(2), var1.getglobal("None"), var1.getglobal("None")};
      PyObject var6 = var10000.__call__(var2, var3);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(2079);
      if (var1.getlocal(0).__getattr__("verbose").__nonzero__()) {
         var1.setline(2080);
         Py.printComma(PyString.fromInterned("Running string"));
         Py.println(var1.getlocal(2));
      }

      var1.setline(2081);
      var6 = var1.getlocal(0).__getattr__("testrunner").__getattr__("run").__call__(var2, var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(2082);
      if (var1.getlocal(0).__getattr__("verbose").__nonzero__()) {
         var1.setline(2083);
         Py.printComma(var1.getlocal(4));
         Py.printComma(PyString.fromInterned("of"));
         Py.printComma(var1.getlocal(5));
         Py.printComma(PyString.fromInterned("examples failed in string"));
         Py.println(var1.getlocal(2));
      }

      var1.setline(2084);
      var6 = var1.getglobal("TestResults").__call__(var2, var1.getlocal(4), var1.getlocal(5));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject rundoc$79(PyFrame var1, ThreadState var2) {
      var1.setline(2087);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(4, var3);
      var1.setlocal(5, var3);
      var1.setline(2088);
      PyObject var10000 = var1.getlocal(0).__getattr__("testfinder").__getattr__("find");
      PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("globs")};
      String[] var4 = new String[]{"module", "globs"};
      var10000 = var10000.__call__(var2, var8, var4);
      var3 = null;
      PyObject var9 = var10000;
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(2090);
      var9 = var1.getlocal(6).__iter__();

      while(true) {
         var1.setline(2090);
         PyObject var10 = var9.__iternext__();
         if (var10 == null) {
            var1.setline(2093);
            var9 = var1.getglobal("TestResults").__call__(var2, var1.getlocal(4), var1.getlocal(5));
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(7, var10);
         var1.setline(2091);
         PyObject var5 = var1.getlocal(0).__getattr__("testrunner").__getattr__("run").__call__(var2, var1.getlocal(7));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(9, var7);
         var7 = null;
         var5 = null;
         var1.setline(2092);
         PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(var1.getlocal(8)), var1.getlocal(5)._add(var1.getlocal(9))});
         var6 = Py.unpackSequence(var11, 2);
         var7 = var6[0];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(5, var7);
         var7 = null;
         var5 = null;
      }
   }

   public PyObject rundict$80(PyFrame var1, ThreadState var2) {
      var1.setline(2096);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2097);
      var3 = var1.getlocal(4).__getattr__("ModuleType").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2098);
      var1.getlocal(5).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(1));
      var1.setline(2099);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2100);
         var3 = var1.getglobal("False");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2101);
      var3 = var1.getlocal(0).__getattr__("rundoc").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run__test__$81(PyFrame var1, ThreadState var2) {
      var1.setline(2104);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2105);
      var3 = var1.getlocal(3).__getattr__("ModuleType").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2106);
      var3 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("__test__", var3);
      var3 = null;
      var1.setline(2107);
      var3 = var1.getlocal(0).__getattr__("rundoc").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject summarize$82(PyFrame var1, ThreadState var2) {
      var1.setline(2110);
      PyObject var3 = var1.getlocal(0).__getattr__("testrunner").__getattr__("summarize").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject merge$83(PyFrame var1, ThreadState var2) {
      var1.setline(2113);
      var1.getlocal(0).__getattr__("testrunner").__getattr__("merge").__call__(var2, var1.getlocal(1).__getattr__("testrunner"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_unittest_reportflags$84(PyFrame var1, ThreadState var2) {
      var1.setline(2147);
      PyString.fromInterned("Sets the unittest option flags.\n\n    The old flag is returned so that a runner could restore the old\n    value if it wished to:\n\n      >>> import doctest\n      >>> old = doctest._unittest_reportflags\n      >>> doctest.set_unittest_reportflags(REPORT_NDIFF |\n      ...                          REPORT_ONLY_FIRST_FAILURE) == old\n      True\n\n      >>> doctest._unittest_reportflags == (REPORT_NDIFF |\n      ...                                   REPORT_ONLY_FIRST_FAILURE)\n      True\n\n    Only reporting flags can be set:\n\n      >>> doctest.set_unittest_reportflags(ELLIPSIS)\n      Traceback (most recent call last):\n      ...\n      ValueError: ('Only reporting flags allowed', 8)\n\n      >>> doctest.set_unittest_reportflags(old) == (REPORT_NDIFF |\n      ...                                   REPORT_ONLY_FIRST_FAILURE)\n      True\n    ");
      var1.setline(2150);
      PyObject var3 = var1.getlocal(0)._and(var1.getglobal("REPORTING_FLAGS"));
      PyObject var10000 = var3._ne(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2151);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Only reporting flags allowed"), (PyObject)var1.getlocal(0)));
      } else {
         var1.setline(2152);
         var3 = var1.getglobal("_unittest_reportflags");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2153);
         var3 = var1.getlocal(0);
         var1.setglobal("_unittest_reportflags", var3);
         var3 = null;
         var1.setline(2154);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject DocTestCase$85(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2159);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$86, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2169);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$87, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(2175);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$88, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(2183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runTest$89, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      var1.setline(2207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_failure$90, (PyObject)null);
      var1.setlocal("format_failure", var4);
      var3 = null;
      var1.setline(2219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$91, PyString.fromInterned("Run the test case without results and without catching exceptions\n\n           The unit test framework includes a debug method on test cases\n           and test suites to support post-mortem debugging.  The test code\n           is run in such a way that errors are not caught.  This way a\n           caller can catch the errors and initiate post-mortem debugging.\n\n           The DocTestCase provides a debug method that raises\n           UnexpectedException errors if there is an unexpected\n           exception:\n\n             >>> test = DocTestParser().get_doctest('>>> raise KeyError\\n42',\n             ...                {}, 'foo', 'foo.py', 0)\n             >>> case = DocTestCase(test)\n             >>> try:\n             ...     case.debug()\n             ... except UnexpectedException, failure:\n             ...     pass\n\n           The UnexpectedException contains the test, the example, and\n           the original exception:\n\n             >>> failure.test is test\n             True\n\n             >>> failure.example.want\n             '42\\n'\n\n             >>> exc_info = failure.exc_info\n             >>> raise exc_info[0], exc_info[1], exc_info[2]\n             Traceback (most recent call last):\n             ...\n             KeyError\n\n           If the output doesn't match, then a DocTestFailure is raised:\n\n             >>> test = DocTestParser().get_doctest('''\n             ...      >>> x = 1\n             ...      >>> x\n             ...      2\n             ...      ''', {}, 'foo', 'foo.py', 0)\n             >>> case = DocTestCase(test)\n\n             >>> try:\n             ...    case.debug()\n             ... except DocTestFailure, failure:\n             ...    pass\n\n           DocTestFailure objects provide access to the test:\n\n             >>> failure.test is test\n             True\n\n           As well as to the example:\n\n             >>> failure.example.want\n             '2\\n'\n\n           and the actual output:\n\n             >>> failure.got\n             '1\\n'\n\n           "));
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(2291);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, id$92, (PyObject)null);
      var1.setlocal("id", var4);
      var3 = null;
      var1.setline(2294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$93, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(2304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$94, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(2307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$95, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(2311);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$96, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(2315);
      PyObject var5 = var1.getname("__repr__");
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(2317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shortDescription$97, (PyObject)null);
      var1.setlocal("shortDescription", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$86(PyFrame var1, ThreadState var2) {
      var1.setline(2162);
      var1.getglobal("unittest").__getattr__("TestCase").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(2163);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_dt_optionflags", var3);
      var3 = null;
      var1.setline(2164);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("_dt_checker", var3);
      var3 = null;
      var1.setline(2165);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_dt_test", var3);
      var3 = null;
      var1.setline(2166);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_dt_setUp", var3);
      var3 = null;
      var1.setline(2167);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_dt_tearDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$87(PyFrame var1, ThreadState var2) {
      var1.setline(2170);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2172);
      var3 = var1.getlocal(0).__getattr__("_dt_setUp");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2173);
         var1.getlocal(0).__getattr__("_dt_setUp").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$88(PyFrame var1, ThreadState var2) {
      var1.setline(2176);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2178);
      var3 = var1.getlocal(0).__getattr__("_dt_tearDown");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2179);
         var1.getlocal(0).__getattr__("_dt_tearDown").__call__(var2, var1.getlocal(1));
      }

      var1.setline(2181);
      var1.getlocal(1).__getattr__("globs").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runTest$89(PyFrame var1, ThreadState var2) {
      var1.setline(2184);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2185);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2186);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2187);
      var3 = var1.getlocal(0).__getattr__("_dt_optionflags");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2189);
      if (var1.getlocal(4)._and(var1.getglobal("REPORTING_FLAGS")).__not__().__nonzero__()) {
         var1.setline(2192);
         var3 = var1.getlocal(4);
         var3 = var3._ior(var1.getglobal("_unittest_reportflags"));
         var1.setlocal(4, var3);
      }

      var1.setline(2194);
      PyObject var10000 = var1.getglobal("DocTestRunner");
      PyObject[] var11 = new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("_dt_checker"), var1.getglobal("False")};
      String[] var4 = new String[]{"optionflags", "checker", "verbose"};
      var10000 = var10000.__call__(var2, var11, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      PyObject var8;
      try {
         var1.setline(2198);
         var8 = PyString.fromInterned("-")._mul(Py.newInteger(70));
         var1.getlocal(5).__setattr__("DIVIDER", var8);
         var4 = null;
         var1.setline(2199);
         var10000 = var1.getlocal(5).__getattr__("run");
         PyObject[] var10 = new PyObject[]{var1.getlocal(1), var1.getlocal(3).__getattr__("write"), var1.getglobal("False")};
         String[] var5 = new String[]{"out", "clear_globs"};
         var10000 = var10000.__call__(var2, var10, var5);
         var4 = null;
         var8 = var10000;
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var6 = var9[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(7, var6);
         var6 = null;
         var4 = null;
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(2202);
         var8 = var1.getlocal(2);
         var1.getglobal("sys").__setattr__("stdout", var8);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(2202);
      var8 = var1.getlocal(2);
      var1.getglobal("sys").__setattr__("stdout", var8);
      var4 = null;
      var1.setline(2204);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(2205);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(0).__getattr__("format_failure").__call__(var2, var1.getlocal(3).__getattr__("getvalue").__call__(var2))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject format_failure$90(PyFrame var1, ThreadState var2) {
      var1.setline(2208);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2209);
      var3 = var1.getlocal(2).__getattr__("lineno");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2210);
         PyString var4 = PyString.fromInterned("unknown line number");
         var1.setlocal(3, var4);
         var3 = null;
      } else {
         var1.setline(2212);
         var3 = PyString.fromInterned("%s")._mod(var1.getlocal(2).__getattr__("lineno"));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2213);
      var3 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(2).__getattr__("name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2214);
      var3 = PyString.fromInterned("Failed doctest test for %s\n  File \"%s\", line %s, in %s\n\n%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("name"), var1.getlocal(2).__getattr__("filename"), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject debug$91(PyFrame var1, ThreadState var2) {
      var1.setline(2283);
      PyString.fromInterned("Run the test case without results and without catching exceptions\n\n           The unit test framework includes a debug method on test cases\n           and test suites to support post-mortem debugging.  The test code\n           is run in such a way that errors are not caught.  This way a\n           caller can catch the errors and initiate post-mortem debugging.\n\n           The DocTestCase provides a debug method that raises\n           UnexpectedException errors if there is an unexpected\n           exception:\n\n             >>> test = DocTestParser().get_doctest('>>> raise KeyError\\n42',\n             ...                {}, 'foo', 'foo.py', 0)\n             >>> case = DocTestCase(test)\n             >>> try:\n             ...     case.debug()\n             ... except UnexpectedException, failure:\n             ...     pass\n\n           The UnexpectedException contains the test, the example, and\n           the original exception:\n\n             >>> failure.test is test\n             True\n\n             >>> failure.example.want\n             '42\\n'\n\n             >>> exc_info = failure.exc_info\n             >>> raise exc_info[0], exc_info[1], exc_info[2]\n             Traceback (most recent call last):\n             ...\n             KeyError\n\n           If the output doesn't match, then a DocTestFailure is raised:\n\n             >>> test = DocTestParser().get_doctest('''\n             ...      >>> x = 1\n             ...      >>> x\n             ...      2\n             ...      ''', {}, 'foo', 'foo.py', 0)\n             >>> case = DocTestCase(test)\n\n             >>> try:\n             ...    case.debug()\n             ... except DocTestFailure, failure:\n             ...    pass\n\n           DocTestFailure objects provide access to the test:\n\n             >>> failure.test is test\n             True\n\n           As well as to the example:\n\n             >>> failure.example.want\n             '2\\n'\n\n           and the actual output:\n\n             >>> failure.got\n             '1\\n'\n\n           ");
      var1.setline(2285);
      var1.getlocal(0).__getattr__("setUp").__call__(var2);
      var1.setline(2286);
      PyObject var10000 = var1.getglobal("DebugRunner");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("_dt_optionflags"), var1.getlocal(0).__getattr__("_dt_checker"), var1.getglobal("False")};
      String[] var4 = new String[]{"optionflags", "checker", "verbose"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(2288);
      var10000 = var1.getlocal(1).__getattr__("run");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("_dt_test"), var1.getglobal("False")};
      var4 = new String[]{"clear_globs"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(2289);
      var1.getlocal(0).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject id$92(PyFrame var1, ThreadState var2) {
      var1.setline(2292);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test").__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$93(PyFrame var1, ThreadState var2) {
      var1.setline(2295);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2296);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2298);
         PyObject var4 = var1.getlocal(0).__getattr__("_dt_test");
         var10000 = var4._eq(var1.getlocal(1).__getattr__("_dt_test"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("_dt_optionflags");
            var10000 = var4._eq(var1.getlocal(1).__getattr__("_dt_optionflags"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("_dt_setUp");
               var10000 = var4._eq(var1.getlocal(1).__getattr__("_dt_setUp"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("_dt_tearDown");
                  var10000 = var4._eq(var1.getlocal(1).__getattr__("_dt_tearDown"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getattr__("_dt_checker");
                     var10000 = var4._eq(var1.getlocal(1).__getattr__("_dt_checker"));
                     var4 = null;
                  }
               }
            }
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$94(PyFrame var1, ThreadState var2) {
      var1.setline(2305);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$95(PyFrame var1, ThreadState var2) {
      var1.setline(2308);
      PyObject var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_dt_optionflags"), var1.getlocal(0).__getattr__("_dt_setUp"), var1.getlocal(0).__getattr__("_dt_tearDown"), var1.getlocal(0).__getattr__("_dt_checker")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$96(PyFrame var1, ThreadState var2) {
      var1.setline(2312);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test").__getattr__("name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2313);
      var3 = PyString.fromInterned("%s (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(-1)), PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shortDescription$97(PyFrame var1, ThreadState var2) {
      var1.setline(2318);
      PyObject var3 = PyString.fromInterned("Doctest: ")._add(var1.getlocal(0).__getattr__("_dt_test").__getattr__("name"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SkipDocTestCase$98(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2321);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$99, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2325);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$100, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(2328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skip$101, (PyObject)null);
      var1.setlocal("test_skip", var4);
      var3 = null;
      var1.setline(2331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shortDescription$102, (PyObject)null);
      var1.setlocal("shortDescription", var4);
      var3 = null;
      var1.setline(2334);
      PyObject var5 = var1.getname("shortDescription");
      var1.setlocal("__str__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$99(PyFrame var1, ThreadState var2) {
      var1.setline(2322);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("module", var3);
      var3 = null;
      var1.setline(2323);
      var1.getglobal("DocTestCase").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$100(PyFrame var1, ThreadState var2) {
      var1.setline(2326);
      var1.getlocal(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DocTestSuite will not work with -O2 and above"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skip$101(PyFrame var1, ThreadState var2) {
      var1.setline(2329);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shortDescription$102(PyFrame var1, ThreadState var2) {
      var1.setline(2332);
      PyObject var3 = PyString.fromInterned("Skipping tests from %s")._mod(var1.getlocal(0).__getattr__("module").__getattr__("__name__"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DocTestSuite$103(PyFrame var1, ThreadState var2) {
      var1.setline(2372);
      PyString.fromInterned("\n    Convert doctest tests for a module to a unittest test suite.\n\n    This converts each documentation string in a module that\n    contains doctest tests to a unittest test case.  If any of the\n    tests in a doc string fail, then the test case fails.  An exception\n    is raised showing the name of the file containing the test and a\n    (sometimes approximate) line number.\n\n    The `module` argument provides the module to be tested.  The argument\n    can be either a module or a module name.\n\n    If no argument is given, the calling module is used.\n\n    A number of options may be provided as keyword arguments:\n\n    setUp\n      A set-up function.  This is called before running the\n      tests in each file. The setUp function will be passed a DocTest\n      object.  The setUp function can access the test globals as the\n      globs attribute of the test passed.\n\n    tearDown\n      A tear-down function.  This is called after running the\n      tests in each file.  The tearDown function will be passed a DocTest\n      object.  The tearDown function can access the test globals as the\n      globs attribute of the test passed.\n\n    globs\n      A dictionary containing initial global variables for the tests.\n\n    optionflags\n       A set of doctest option flags expressed as an integer.\n    ");
      var1.setline(2374);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2375);
         var3 = var1.getglobal("DocTestFinder").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2377);
      var3 = var1.getglobal("_normalize_module").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(2378);
      var10000 = var1.getlocal(3).__getattr__("find");
      PyObject[] var9 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)};
      String[] var4 = new String[]{"globs", "extraglobs"};
      var10000 = var10000.__call__(var2, var9, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2380);
      var10000 = var1.getlocal(5).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("sys").__getattr__("flags").__getattr__("optimize");
         var10000 = var3._ge(Py.newInteger(2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(2382);
         var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(2383);
         var1.getlocal(6).__getattr__("addTest").__call__(var2, var1.getglobal("SkipDocTestCase").__call__(var2, var1.getlocal(0)));
         var1.setline(2384);
         var3 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2385);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(2393);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("has no docstrings")));
         } else {
            var1.setline(2395);
            var1.getlocal(5).__getattr__("sort").__call__(var2);
            var1.setline(2396);
            PyObject var8 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
            var1.setlocal(6, var8);
            var4 = null;
            var1.setline(2398);
            var8 = var1.getlocal(5).__iter__();

            while(true) {
               var1.setline(2398);
               PyObject var5 = var8.__iternext__();
               if (var5 == null) {
                  var1.setline(2410);
                  var3 = var1.getlocal(6);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(7, var5);
               var1.setline(2399);
               PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(7).__getattr__("examples"));
               var10000 = var6._eq(Py.newInteger(0));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(2401);
                  if (var1.getlocal(7).__getattr__("filename").__not__().__nonzero__()) {
                     var1.setline(2402);
                     var6 = var1.getlocal(0).__getattr__("__file__");
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(2403);
                     var6 = var1.getlocal(8).__getslice__(Py.newInteger(-4), (PyObject)null, (PyObject)null);
                     var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo")}));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2404);
                        var6 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                        var1.setlocal(8, var6);
                        var6 = null;
                     } else {
                        var1.setline(2405);
                        if (var1.getlocal(8).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$py.class")).__nonzero__()) {
                           var1.setline(2406);
                           var6 = PyString.fromInterned("%s.py")._mod(var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null));
                           var1.setlocal(8, var6);
                           var6 = null;
                        }
                     }

                     var1.setline(2407);
                     var6 = var1.getlocal(8);
                     var1.getlocal(7).__setattr__("filename", var6);
                     var6 = null;
                  }

                  var1.setline(2408);
                  var10000 = var1.getlocal(6).__getattr__("addTest");
                  PyObject var10002 = var1.getglobal("DocTestCase");
                  PyObject[] var10 = new PyObject[]{var1.getlocal(7)};
                  String[] var7 = new String[0];
                  var10002 = var10002._callextra(var10, var7, (PyObject)null, var1.getlocal(4));
                  var6 = null;
                  var10000.__call__(var2, var10002);
               }
            }
         }
      }
   }

   public PyObject DocFileCase$104(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2414);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, id$105, (PyObject)null);
      var1.setlocal("id", var4);
      var3 = null;
      var1.setline(2417);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$106, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(2419);
      PyObject var5 = var1.getname("__repr__");
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(2421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_failure$107, (PyObject)null);
      var1.setlocal("format_failure", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject id$105(PyFrame var1, ThreadState var2) {
      var1.setline(2415);
      PyObject var3 = PyString.fromInterned("_").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_dt_test").__getattr__("name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$106(PyFrame var1, ThreadState var2) {
      var1.setline(2418);
      PyObject var3 = var1.getlocal(0).__getattr__("_dt_test").__getattr__("filename");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_failure$107(PyFrame var1, ThreadState var2) {
      var1.setline(2422);
      PyObject var3 = PyString.fromInterned("Failed doctest test for %s\n  File \"%s\", line 0\n\n%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_dt_test").__getattr__("name"), var1.getlocal(0).__getattr__("_dt_test").__getattr__("filename"), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DocFileTest$108(PyFrame var1, ThreadState var2) {
      var1.setline(2429);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2430);
         PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(3, var6);
         var3 = null;
      } else {
         var1.setline(2432);
         var3 = var1.getlocal(3).__getattr__("copy").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2434);
      var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(2435);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Package may only be specified for module-relative paths.")));
      } else {
         var1.setline(2439);
         var3 = var1.getglobal("_load_testfile").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(0, var5);
         var5 = null;
         var3 = null;
         var1.setline(2441);
         PyString var8 = PyString.fromInterned("__file__");
         var10000 = var8._notin(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2442);
            var3 = var1.getlocal(0);
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("__file__"), var3);
            var3 = null;
         }

         var1.setline(2445);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(2448);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2449);
            var3 = var1.getlocal(7).__getattr__("decode").__call__(var2, var1.getlocal(5));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(2452);
         var10000 = var1.getlocal(4).__getattr__("get_doctest");
         PyObject[] var9 = new PyObject[]{var1.getlocal(7), var1.getlocal(3), var1.getlocal(8), var1.getlocal(0), Py.newInteger(0)};
         var3 = var10000.__call__(var2, var9);
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(2453);
         var10000 = var1.getglobal("DocFileCase");
         var9 = new PyObject[]{var1.getlocal(9)};
         String[] var7 = new String[0];
         var10000 = var10000._callextra(var9, var7, (PyObject)null, var1.getlocal(6));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject DocFileSuite$109(PyFrame var1, ThreadState var2) {
      var1.setline(2510);
      PyString.fromInterned("A unittest suite for one or more doctest files.\n\n    The path to each doctest file is given as a string; the\n    interpretation of that string depends on the keyword argument\n    \"module_relative\".\n\n    A number of options may be provided as keyword arguments:\n\n    module_relative\n      If \"module_relative\" is True, then the given file paths are\n      interpreted as os-independent module-relative paths.  By\n      default, these paths are relative to the calling module's\n      directory; but if the \"package\" argument is specified, then\n      they are relative to that package.  To ensure os-independence,\n      \"filename\" should use \"/\" characters to separate path\n      segments, and may not be an absolute path (i.e., it may not\n      begin with \"/\").\n\n      If \"module_relative\" is False, then the given file paths are\n      interpreted as os-specific paths.  These paths may be absolute\n      or relative (to the current working directory).\n\n    package\n      A Python package or the name of a Python package whose directory\n      should be used as the base directory for module relative paths.\n      If \"package\" is not specified, then the calling module's\n      directory is used as the base directory for module relative\n      filenames.  It is an error to specify \"package\" if\n      \"module_relative\" is False.\n\n    setUp\n      A set-up function.  This is called before running the\n      tests in each file. The setUp function will be passed a DocTest\n      object.  The setUp function can access the test globals as the\n      globs attribute of the test passed.\n\n    tearDown\n      A tear-down function.  This is called after running the\n      tests in each file.  The tearDown function will be passed a DocTest\n      object.  The tearDown function can access the test globals as the\n      globs attribute of the test passed.\n\n    globs\n      A dictionary containing initial global variables for the tests.\n\n    optionflags\n      A set of doctest option flags expressed as an integer.\n\n    parser\n      A DocTestParser (or subclass) that should be used to extract\n      tests from the files.\n\n    encoding\n      An encoding that will be used to convert the files to unicode.\n    ");
      var1.setline(2511);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2516);
      if (var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("module_relative"), (PyObject)var1.getglobal("True")).__nonzero__()) {
         var1.setline(2517);
         var3 = var1.getglobal("_normalize_module").__call__(var2, var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("package")));
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("package"), var3);
         var3 = null;
      }

      var1.setline(2519);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(2519);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2522);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(2520);
         PyObject var10000 = var1.getlocal(2).__getattr__("addTest");
         PyObject var10002 = var1.getglobal("DocFileTest");
         PyObject[] var5 = new PyObject[]{var1.getlocal(3)};
         String[] var6 = new String[0];
         var10002 = var10002._callextra(var5, var6, (PyObject)null, var1.getlocal(1));
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject script_from_examples$110(PyFrame var1, ThreadState var2) {
      var1.setline(2585);
      PyString.fromInterned("Extract script from text with examples.\n\n       Converts text with examples to a Python script.  Example input is\n       converted to regular code.  Example output and all other words\n       are converted to comments:\n\n       >>> text = '''\n       ...       Here are examples of simple math.\n       ...\n       ...           Python has super accurate integer addition\n       ...\n       ...           >>> 2 + 2\n       ...           5\n       ...\n       ...           And very friendly error messages:\n       ...\n       ...           >>> 1/0\n       ...           To Infinity\n       ...           And\n       ...           Beyond\n       ...\n       ...           You can use logic if you want:\n       ...\n       ...           >>> if 0:\n       ...           ...    blah\n       ...           ...    blah\n       ...           ...\n       ...\n       ...           Ho hum\n       ...           '''\n\n       >>> print script_from_examples(text)\n       # Here are examples of simple math.\n       #\n       #     Python has super accurate integer addition\n       #\n       2 + 2\n       # Expected:\n       ## 5\n       #\n       #     And very friendly error messages:\n       #\n       1/0\n       # Expected:\n       ## To Infinity\n       ## And\n       ## Beyond\n       #\n       #     You can use logic if you want:\n       #\n       if 0:\n          blah\n          blah\n       #\n       #     Ho hum\n       <BLANKLINE>\n       ");
      var1.setline(2586);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2587);
      PyObject var8 = var1.getglobal("DocTestParser").__call__(var2).__getattr__("parse").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         PyList var10000;
         PyObject var5;
         PyObject var6;
         PyObject var7;
         do {
            while(true) {
               var1.setline(2587);
               PyObject var4 = var8.__iternext__();
               if (var4 == null) {
                  while(true) {
                     var1.setline(2602);
                     PyObject var9 = var1.getlocal(1);
                     if (var9.__nonzero__()) {
                        var8 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
                        var9 = var8._eq(PyString.fromInterned("#"));
                        var3 = null;
                     }

                     if (!var9.__nonzero__()) {
                        while(true) {
                           var1.setline(2604);
                           var9 = var1.getlocal(1);
                           if (var9.__nonzero__()) {
                              var8 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                              var9 = var8._eq(PyString.fromInterned("#"));
                              var3 = null;
                           }

                           if (!var9.__nonzero__()) {
                              var1.setline(2608);
                              var8 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned("\n"));
                              var1.f_lasti = -1;
                              return var8;
                           }

                           var1.setline(2605);
                           var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                        }
                     }

                     var1.setline(2603);
                     var1.getlocal(1).__getattr__("pop").__call__(var2);
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(2588);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Example")).__nonzero__()) {
                  var1.setline(2590);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("source").__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
                  var1.setline(2592);
                  var5 = var1.getlocal(2).__getattr__("want");
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(2593);
                  break;
               }

               var1.setline(2598);
               var5 = var1.getlocal(1);
               var10000 = new PyList();
               var6 = var10000.__getattr__("append");
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(2599);
               var6 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

               while(true) {
                  var1.setline(2599);
                  var7 = var6.__iternext__();
                  if (var7 == null) {
                     var1.setline(2599);
                     var1.dellocal(6);
                     var5 = var5._iadd(var10000);
                     var1.setlocal(1, var5);
                     break;
                  }

                  var1.setlocal(5, var7);
                  var1.setline(2598);
                  var1.getlocal(6).__call__(var2, var1.getglobal("_comment_line").__call__(var2, var1.getlocal(5)));
               }
            }
         } while(!var1.getlocal(3).__nonzero__());

         var1.setline(2594);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("# Expected:"));
         var1.setline(2595);
         var5 = var1.getlocal(1);
         var10000 = new PyList();
         var6 = var10000.__getattr__("append");
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(2595);
         var6 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__iter__();

         while(true) {
            var1.setline(2595);
            var7 = var6.__iternext__();
            if (var7 == null) {
               var1.setline(2595);
               var1.dellocal(4);
               var5 = var5._iadd(var10000);
               var1.setlocal(1, var5);
               break;
            }

            var1.setlocal(5, var7);
            var1.setline(2595);
            var1.getlocal(4).__call__(var2, PyString.fromInterned("## ")._add(var1.getlocal(5)));
         }
      }
   }

   public PyObject testsource$111(PyFrame var1, ThreadState var2) {
      var1.setline(2616);
      PyString.fromInterned("Extract the test sources from a doctest docstring as a script.\n\n    Provide the module (or dotted name of the module) containing the\n    test to be debugged and the name (within the module) of the object\n    with the doc string with tests to be debugged.\n    ");
      var1.setline(2617);
      PyObject var3 = var1.getglobal("_normalize_module").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(2618);
      var3 = var1.getglobal("DocTestFinder").__call__(var2).__getattr__("find").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2619);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2619);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(2619);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2619);
            var1.dellocal(4);
            PyList var6 = var10000;
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(2620);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(2621);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("not found in tests")));
            }

            var1.setline(2622);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(2623);
            var3 = var1.getglobal("script_from_examples").__call__(var2, var1.getlocal(3).__getattr__("docstring"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(2624);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(2619);
         PyObject var5 = var1.getlocal(5).__getattr__("name");
         PyObject var10001 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10001.__nonzero__()) {
            var1.setline(2619);
            var1.getlocal(4).__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject debug_src$112(PyFrame var1, ThreadState var2) {
      var1.setline(2627);
      PyString.fromInterned("Debug a single doctest docstring, in argument `src`'");
      var1.setline(2628);
      PyObject var3 = var1.getglobal("script_from_examples").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2629);
      var1.getglobal("debug_script").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug_script$113(PyFrame var1, ThreadState var2) {
      var1.setline(2632);
      PyString.fromInterned("Debug a test script.  `src` is the script, as a string.");
      var1.setline(2633);
      PyObject var3 = imp.importOne("pdb", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2638);
      var3 = var1.getglobal("tempfile").__getattr__("mktemp").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py"), (PyObject)PyString.fromInterned("doctestdebug"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2639);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2640);
      var1.getlocal(5).__getattr__("write").__call__(var2, var1.getlocal(0));
      var1.setline(2641);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var3 = null;

      try {
         var1.setline(2644);
         PyObject var4;
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2645);
            var4 = var1.getlocal(2).__getattr__("copy").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
         } else {
            var1.setline(2647);
            PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(2, var7);
            var4 = null;
         }

         var1.setline(2649);
         if (var1.getlocal(1).__nonzero__()) {
            try {
               var1.setline(2651);
               var1.getglobal("execfile").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(2));
            } catch (Throwable var5) {
               Py.setException(var5, var1);
               var1.setline(2653);
               Py.println(var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1)));
               var1.setline(2654);
               var1.getlocal(3).__getattr__("post_mortem").__call__(var2, var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
            }
         } else {
            var1.setline(2658);
            var1.getlocal(3).__getattr__("run").__call__(var2, PyString.fromInterned("execfile(%r)")._mod(var1.getlocal(4)), var1.getlocal(2), var1.getlocal(2));
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2661);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(4));
         throw (Throwable)var6;
      }

      var1.setline(2661);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug$114(PyFrame var1, ThreadState var2) {
      var1.setline(2669);
      PyString.fromInterned("Debug a single doctest docstring.\n\n    Provide the module (or dotted name of the module) containing the\n    test to be debugged and the name (within the module) of the object\n    with the docstring with tests to be debugged.\n    ");
      var1.setline(2670);
      PyObject var3 = var1.getglobal("_normalize_module").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(2671);
      var3 = var1.getglobal("testsource").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2672);
      var1.getglobal("debug_script").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(0).__getattr__("__dict__"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _TestClass$115(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A pointless class, for sanity-checking of docstring testing.\n\n    Methods:\n        square()\n        get()\n\n    >>> _TestClass(13).get() + _TestClass(-12).get()\n    1\n    >>> hex(_TestClass(13).square().get())\n    '0xa9'\n    "));
      var1.setline(2689);
      PyString.fromInterned("\n    A pointless class, for sanity-checking of docstring testing.\n\n    Methods:\n        square()\n        get()\n\n    >>> _TestClass(13).get() + _TestClass(-12).get()\n    1\n    >>> hex(_TestClass(13).square().get())\n    '0xa9'\n    ");
      var1.setline(2691);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$116, PyString.fromInterned("val -> _TestClass object with associated value val.\n\n        >>> t = _TestClass(123)\n        >>> print t.get()\n        123\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2701);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, square$117, PyString.fromInterned("square() -> square TestClass's associated value\n\n        >>> _TestClass(13).square().get()\n        169\n        "));
      var1.setlocal("square", var4);
      var3 = null;
      var1.setline(2711);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get$118, PyString.fromInterned("get() -> return TestClass's associated value.\n\n        >>> x = _TestClass(-42)\n        >>> print x.get()\n        -42\n        "));
      var1.setlocal("get", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$116(PyFrame var1, ThreadState var2) {
      var1.setline(2697);
      PyString.fromInterned("val -> _TestClass object with associated value val.\n\n        >>> t = _TestClass(123)\n        >>> print t.get()\n        123\n        ");
      var1.setline(2699);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("val", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject square$117(PyFrame var1, ThreadState var2) {
      var1.setline(2706);
      PyString.fromInterned("square() -> square TestClass's associated value\n\n        >>> _TestClass(13).square().get()\n        169\n        ");
      var1.setline(2708);
      PyObject var3 = var1.getlocal(0).__getattr__("val")._pow(Py.newInteger(2));
      var1.getlocal(0).__setattr__("val", var3);
      var3 = null;
      var1.setline(2709);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$118(PyFrame var1, ThreadState var2) {
      var1.setline(2717);
      PyString.fromInterned("get() -> return TestClass's associated value.\n\n        >>> x = _TestClass(-42)\n        >>> print x.get()\n        -42\n        ");
      var1.setline(2719);
      PyObject var3 = var1.getlocal(0).__getattr__("val");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _test$119(PyFrame var1, ThreadState var2) {
      var1.setline(2773);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2773);
      var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(2773);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(2773);
            var1.dellocal(1);
            PyList var9 = var10000;
            var1.setlocal(0, var9);
            var3 = null;
            var1.setline(2774);
            PyInteger var11;
            PyObject var15;
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(2775);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(2776);
               PyString var12 = PyString.fromInterned("__loader__");
               var15 = var12._in(var1.getglobal("globals").__call__(var2));
               var3 = null;
               if (var15.__nonzero__()) {
                  var1.setline(2777);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(3));
                  PyObject[] var10 = Py.unpackSequence(var3, 2);
                  var5 = var10[0];
                  var1.setlocal(3, var5);
                  var5 = null;
                  var5 = var10[1];
                  var1.setlocal(4, var5);
                  var5 = null;
                  var3 = null;
               }

               var1.setline(2778);
               Py.println(PyString.fromInterned("usage: {0} [-v] file ...").__getattr__("format").__call__(var2, var1.getlocal(3)));
               var1.setline(2779);
               var11 = Py.newInteger(2);
               var1.f_lasti = -1;
               return var11;
            }

            var1.setline(2780);
            var4 = var1.getlocal(0).__iter__();

            do {
               var1.setline(2780);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(2794);
                  var11 = Py.newInteger(0);
                  var1.f_lasti = -1;
                  return var11;
               }

               var1.setlocal(5, var5);
               var1.setline(2781);
               PyObject var6;
               PyObject[] var7;
               PyObject var8;
               if (var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py")).__nonzero__()) {
                  var1.setline(2785);
                  var6 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(5));
                  var7 = Py.unpackSequence(var6, 2);
                  var8 = var7[0];
                  var1.setlocal(6, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(5, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(2786);
                  var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(6));
                  var1.setline(2787);
                  var6 = var1.getglobal("__import__").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null));
                  var1.setlocal(7, var6);
                  var6 = null;
                  var1.setline(2788);
                  var1.getglobal("sys").__getattr__("path").__delitem__((PyObject)Py.newInteger(0));
                  var1.setline(2789);
                  var6 = var1.getglobal("testmod").__call__(var2, var1.getlocal(7));
                  var7 = Py.unpackSequence(var6, 2);
                  var8 = var7[0];
                  var1.setlocal(8, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(4, var8);
                  var8 = null;
                  var6 = null;
               } else {
                  var1.setline(2791);
                  var15 = var1.getglobal("testfile");
                  PyObject[] var14 = new PyObject[]{var1.getlocal(5), var1.getglobal("False")};
                  String[] var13 = new String[]{"module_relative"};
                  var15 = var15.__call__(var2, var14, var13);
                  var6 = null;
                  var6 = var15;
                  var7 = Py.unpackSequence(var6, 2);
                  var8 = var7[0];
                  var1.setlocal(8, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(4, var8);
                  var8 = null;
                  var6 = null;
               }

               var1.setline(2792);
            } while(!var1.getlocal(8).__nonzero__());

            var1.setline(2793);
            var11 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(2, var4);
         var1.setline(2773);
         PyObject var10001 = var1.getlocal(2);
         if (var10001.__nonzero__()) {
            var5 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var10001 = var5._ne(PyString.fromInterned("-"));
            var5 = null;
         }

         if (var10001.__nonzero__()) {
            var1.setline(2773);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public doctest$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name"};
      register_optionflag$1 = Py.newCode(1, var2, var1, "register_optionflag", 128, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"globs", "flags", "fname", "feature"};
      _extract_future_flags$2 = Py.newCode(1, var2, var1, "_extract_future_flags", 178, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "depth"};
      _normalize_module$3 = Py.newCode(2, var2, var1, "_normalize_module", 190, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "package", "module_relative", "file_contents", "f"};
      _load_testfile$4 = Py.newCode(3, var2, var1, "_load_testfile", 209, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "indent"};
      _indent$5 = Py.newCode(2, var2, var1, "_indent", 225, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"exc_info", "excout", "exc_type", "exc_val", "exc_tb"};
      _exception_traceback$6 = Py.newCode(1, var2, var1, "_exception_traceback", 237, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _SpoofOut$7 = Py.newCode(0, var2, var1, "_SpoofOut", 249, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "result"};
      getvalue$8 = Py.newCode(1, var2, var1, "getvalue", 250, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      truncate$9 = Py.newCode(2, var2, var1, "truncate", 263, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"want", "got", "ws", "startpos", "endpos", "w"};
      _ellipsis_match$10 = Py.newCode(2, var2, var1, "_ellipsis_match", 272, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line"};
      _comment_line$11 = Py.newCode(1, var2, var1, "_comment_line", 321, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _OutputRedirectingPdb$12 = Py.newCode(0, var2, var1, "_OutputRedirectingPdb", 329, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "out"};
      __init__$13 = Py.newCode(2, var2, var1, "__init__", 335, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      set_trace$14 = Py.newCode(2, var2, var1, "set_trace", 342, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      set_continue$15 = Py.newCode(1, var2, var1, "set_continue", 348, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "save_stdout"};
      trace_dispatch$16 = Py.newCode(2, var2, var1, "trace_dispatch", 354, true, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "path", "basedir"};
      _module_relative_path$17 = Py.newCode(2, var2, var1, "_module_relative_path", 365, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Example$18 = Py.newCode(0, var2, var1, "Example", 401, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "want", "exc_msg", "lineno", "indent", "options"};
      __init__$19 = Py.newCode(7, var2, var1, "__init__", 436, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$20 = Py.newCode(2, var2, var1, "__eq__", 454, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$21 = Py.newCode(2, var2, var1, "__ne__", 465, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$22 = Py.newCode(1, var2, var1, "__hash__", 468, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocTest$23 = Py.newCode(0, var2, var1, "DocTest", 473, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "examples", "globs", "name", "filename", "lineno", "docstring"};
      __init__$24 = Py.newCode(7, var2, var1, "__init__", 497, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "examples"};
      __repr__$25 = Py.newCode(1, var2, var1, "__repr__", 511, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$26 = Py.newCode(2, var2, var1, "__eq__", 521, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$27 = Py.newCode(2, var2, var1, "__ne__", 532, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$28 = Py.newCode(1, var2, var1, "__hash__", 535, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$29 = Py.newCode(2, var2, var1, "__cmp__", 539, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocTestParser$30 = Py.newCode(0, var2, var1, "DocTestParser", 549, false, false, self, 30, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "string", "name", "min_indent", "_[609_32]", "l", "output", "charno", "lineno", "m", "source", "options", "want", "exc_msg"};
      parse$31 = Py.newCode(3, var2, var1, "parse", 597, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "globs", "name", "filename", "lineno"};
      get_doctest$32 = Py.newCode(6, var2, var1, "get_doctest", 636, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "name", "_[659_16]", "x"};
      get_examples$33 = Py.newCode(3, var2, var1, "get_examples", 648, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "name", "lineno", "indent", "source_lines", "source", "_[681_28]", "sl", "want", "want_lines", "_[692_26]", "wl", "exc_msg", "options"};
      _parse_example$34 = Py.newCode(4, var2, var1, "_parse_example", 662, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "name", "lineno", "options", "m", "option_strings", "option", "flag"};
      _find_options$35 = Py.newCode(4, var2, var1, "_find_options", 716, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "indents", "_[748_19]", "indent"};
      _min_indent$36 = Py.newCode(2, var2, var1, "_min_indent", 746, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "indent", "name", "lineno", "i", "line"};
      _check_prompt_blank$37 = Py.newCode(5, var2, var1, "_check_prompt_blank", 754, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "prefix", "name", "lineno", "i", "line"};
      _check_prefix$38 = Py.newCode(5, var2, var1, "_check_prefix", 768, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocTestFinder$39 = Py.newCode(0, var2, var1, "DocTestFinder", 784, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "parser", "recurse", "exclude_empty"};
      __init__$40 = Py.newCode(5, var2, var1, "__init__", 793, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "name", "module", "globs", "extraglobs", "file", "source_lines", "tests"};
      find$41 = Py.newCode(6, var2, var1, "find", 815, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "object"};
      _from_module$42 = Py.newCode(3, var2, var1, "_from_module", 908, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tests", "obj", "name", "module", "source_lines", "globs", "seen", "test", "valname", "val"};
      _find$43 = Py.newCode(8, var2, var1, "_find", 928, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "name", "module", "globs", "source_lines", "docstring", "lineno", "filename"};
      _get_test$44 = Py.newCode(6, var2, var1, "_get_test", 991, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "source_lines", "lineno", "pat", "i", "line"};
      _find_lineno$45 = Py.newCode(3, var2, var1, "_find_lineno", 1030, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocTestRunner$46 = Py.newCode(0, var2, var1, "DocTestRunner", 1082, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "checker", "verbose", "optionflags"};
      __init__$47 = Py.newCode(4, var2, var1, "__init__", 1142, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "test", "example"};
      report_start$48 = Py.newCode(4, var2, var1, "report_start", 1178, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "test", "example", "got"};
      report_success$49 = Py.newCode(5, var2, var1, "report_success", 1191, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "test", "example", "got"};
      report_failure$50 = Py.newCode(5, var2, var1, "report_failure", 1199, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "test", "example", "exc_info"};
      report_unexpected_exception$51 = Py.newCode(5, var2, var1, "report_unexpected_exception", 1206, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "example", "out", "lineno", "source"};
      _failure_header$52 = Py.newCode(3, var2, var1, "_failure_header", 1213, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "compileflags", "out", "failures", "tries", "original_optionflags", "SUCCESS", "FAILURE", "BOOM", "check", "examplenum", "example", "quiet", "optionflag", "val", "filename", "exception", "got", "outcome", "exc_info", "exc_msg", "m1", "m2"};
      _DocTestRunner__run$53 = Py.newCode(4, var2, var1, "_DocTestRunner__run", 1233, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "f", "t", "f2", "t2"};
      _DocTestRunner__record_outcome$54 = Py.newCode(4, var2, var1, "_DocTestRunner__record_outcome", 1357, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "module_globals", "m", "example", "source"};
      _DocTestRunner__patched_linecache_getlines$55 = Py.newCode(3, var2, var1, "_DocTestRunner__patched_linecache_getlines", 1370, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "compileflags", "out", "clear_globs", "save_stdout", "save_set_trace", "save_displayhook"};
      run$56 = Py.newCode(5, var2, var1, "run", 1381, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "verbose", "notests", "passed", "failed", "totalt", "totalf", "x", "name", "f", "t", "thing", "count"};
      summarize$57 = Py.newCode(2, var2, var1, "summarize", 1443, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "d", "name", "f", "t", "f2", "t2"};
      merge$58 = Py.newCode(2, var2, var1, "merge", 1500, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OutputChecker$59 = Py.newCode(0, var2, var1, "OutputChecker", 1513, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "want", "got", "optionflags"};
      check_output$60 = Py.newCode(4, var2, var1, "check_output", 1521, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "want", "got", "optionflags"};
      _do_a_fancy_diff$61 = Py.newCode(4, var2, var1, "_do_a_fancy_diff", 1575, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "example", "got", "optionflags", "want", "want_lines", "got_lines", "diff", "kind", "engine", "_[1632_20]", "line"};
      output_difference$62 = Py.newCode(4, var2, var1, "output_difference", 1598, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocTestFailure$63 = Py.newCode(0, var2, var1, "DocTestFailure", 1646, false, false, self, 63, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "example", "got"};
      __init__$64 = Py.newCode(4, var2, var1, "__init__", 1657, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$65 = Py.newCode(1, var2, var1, "__str__", 1662, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnexpectedException$66 = Py.newCode(0, var2, var1, "UnexpectedException", 1665, false, false, self, 66, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "example", "exc_info"};
      __init__$67 = Py.newCode(4, var2, var1, "__init__", 1676, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$68 = Py.newCode(1, var2, var1, "__str__", 1681, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DebugRunner$69 = Py.newCode(0, var2, var1, "DebugRunner", 1684, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "compileflags", "out", "clear_globs", "r"};
      run$70 = Py.newCode(5, var2, var1, "run", 1777, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "test", "example", "exc_info"};
      report_unexpected_exception$71 = Py.newCode(5, var2, var1, "report_unexpected_exception", 1783, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "test", "example", "got"};
      report_failure$72 = Py.newCode(5, var2, var1, "report_failure", 1786, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m", "name", "globs", "verbose", "report", "optionflags", "extraglobs", "raise_on_error", "exclude_empty", "finder", "runner", "test"};
      testmod$73 = Py.newCode(9, var2, var1, "testmod", 1798, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "module_relative", "name", "package", "globs", "verbose", "report", "optionflags", "extraglobs", "raise_on_error", "parser", "encoding", "text", "runner", "test"};
      testfile$74 = Py.newCode(12, var2, var1, "testfile", 1902, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "globs", "verbose", "name", "compileflags", "optionflags", "finder", "runner", "test"};
      run_docstring_examples$75 = Py.newCode(6, var2, var1, "run_docstring_examples", 2027, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Tester$76 = Py.newCode(0, var2, var1, "Tester", 2056, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mod", "globs", "verbose", "optionflags"};
      __init__$77 = Py.newCode(5, var2, var1, "__init__", 2057, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "name", "test", "f", "t"};
      runstring$78 = Py.newCode(3, var2, var1, "runstring", 2077, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "module", "f", "t", "tests", "test", "f2", "t2"};
      rundoc$79 = Py.newCode(4, var2, var1, "rundoc", 2086, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d", "name", "module", "types", "m"};
      rundict$80 = Py.newCode(4, var2, var1, "rundict", 2095, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d", "name", "types", "m"};
      run__test__$81 = Py.newCode(3, var2, var1, "run__test__", 2103, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "verbose"};
      summarize$82 = Py.newCode(2, var2, var1, "summarize", 2109, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      merge$83 = Py.newCode(2, var2, var1, "merge", 2112, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"flags", "old"};
      set_unittest_reportflags$84 = Py.newCode(1, var2, var1, "set_unittest_reportflags", 2121, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocTestCase$85 = Py.newCode(0, var2, var1, "DocTestCase", 2157, false, false, self, 85, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "optionflags", "setUp", "tearDown", "checker"};
      __init__$86 = Py.newCode(6, var2, var1, "__init__", 2159, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      setUp$87 = Py.newCode(1, var2, var1, "setUp", 2169, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      tearDown$88 = Py.newCode(1, var2, var1, "tearDown", 2175, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "old", "new", "optionflags", "runner", "failures", "tries"};
      runTest$89 = Py.newCode(1, var2, var1, "runTest", 2183, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err", "test", "lineno", "lname"};
      format_failure$90 = Py.newCode(2, var2, var1, "format_failure", 2207, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "runner"};
      debug$91 = Py.newCode(1, var2, var1, "debug", 2219, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      id$92 = Py.newCode(1, var2, var1, "id", 2291, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$93 = Py.newCode(2, var2, var1, "__eq__", 2294, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$94 = Py.newCode(2, var2, var1, "__ne__", 2304, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$95 = Py.newCode(1, var2, var1, "__hash__", 2307, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __repr__$96 = Py.newCode(1, var2, var1, "__repr__", 2311, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      shortDescription$97 = Py.newCode(1, var2, var1, "shortDescription", 2317, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SkipDocTestCase$98 = Py.newCode(0, var2, var1, "SkipDocTestCase", 2320, false, false, self, 98, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module"};
      __init__$99 = Py.newCode(2, var2, var1, "__init__", 2321, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setUp$100 = Py.newCode(1, var2, var1, "setUp", 2325, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_skip$101 = Py.newCode(1, var2, var1, "test_skip", 2328, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      shortDescription$102 = Py.newCode(1, var2, var1, "shortDescription", 2331, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "globs", "extraglobs", "test_finder", "options", "tests", "suite", "test", "filename"};
      DocTestSuite$103 = Py.newCode(5, var2, var1, "DocTestSuite", 2337, false, true, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocFileCase$104 = Py.newCode(0, var2, var1, "DocFileCase", 2412, false, false, self, 104, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      id$105 = Py.newCode(1, var2, var1, "id", 2414, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$106 = Py.newCode(1, var2, var1, "__repr__", 2417, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err"};
      format_failure$107 = Py.newCode(2, var2, var1, "format_failure", 2421, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "module_relative", "package", "globs", "parser", "encoding", "options", "doc", "name", "test"};
      DocFileTest$108 = Py.newCode(7, var2, var1, "DocFileTest", 2426, false, true, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"paths", "kw", "suite", "path"};
      DocFileSuite$109 = Py.newCode(2, var2, var1, "DocFileSuite", 2455, true, true, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "output", "piece", "want", "_[2595_27]", "l", "_[2598_23]"};
      script_from_examples$110 = Py.newCode(1, var2, var1, "script_from_examples", 2528, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "name", "tests", "test", "_[2619_12]", "t", "testsrc"};
      testsource$111 = Py.newCode(2, var2, var1, "testsource", 2610, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "pm", "globs", "testsrc"};
      debug_src$112 = Py.newCode(3, var2, var1, "debug_src", 2626, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "pm", "globs", "pdb", "srcfilename", "f"};
      debug_script$113 = Py.newCode(3, var2, var1, "debug_script", 2631, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "name", "pm", "testsrc"};
      debug$114 = Py.newCode(3, var2, var1, "debug", 2663, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _TestClass$115 = Py.newCode(0, var2, var1, "_TestClass", 2677, false, false, self, 115, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "val"};
      __init__$116 = Py.newCode(2, var2, var1, "__init__", 2691, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      square$117 = Py.newCode(1, var2, var1, "square", 2701, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get$118 = Py.newCode(1, var2, var1, "get", 2711, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"testfiles", "_[2773_17]", "arg", "name", "_", "filename", "dirname", "m", "failures"};
      _test$119 = Py.newCode(0, var2, var1, "_test", 2772, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new doctest$py("doctest$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(doctest$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.register_optionflag$1(var2, var3);
         case 2:
            return this._extract_future_flags$2(var2, var3);
         case 3:
            return this._normalize_module$3(var2, var3);
         case 4:
            return this._load_testfile$4(var2, var3);
         case 5:
            return this._indent$5(var2, var3);
         case 6:
            return this._exception_traceback$6(var2, var3);
         case 7:
            return this._SpoofOut$7(var2, var3);
         case 8:
            return this.getvalue$8(var2, var3);
         case 9:
            return this.truncate$9(var2, var3);
         case 10:
            return this._ellipsis_match$10(var2, var3);
         case 11:
            return this._comment_line$11(var2, var3);
         case 12:
            return this._OutputRedirectingPdb$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.set_trace$14(var2, var3);
         case 15:
            return this.set_continue$15(var2, var3);
         case 16:
            return this.trace_dispatch$16(var2, var3);
         case 17:
            return this._module_relative_path$17(var2, var3);
         case 18:
            return this.Example$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.__eq__$20(var2, var3);
         case 21:
            return this.__ne__$21(var2, var3);
         case 22:
            return this.__hash__$22(var2, var3);
         case 23:
            return this.DocTest$23(var2, var3);
         case 24:
            return this.__init__$24(var2, var3);
         case 25:
            return this.__repr__$25(var2, var3);
         case 26:
            return this.__eq__$26(var2, var3);
         case 27:
            return this.__ne__$27(var2, var3);
         case 28:
            return this.__hash__$28(var2, var3);
         case 29:
            return this.__cmp__$29(var2, var3);
         case 30:
            return this.DocTestParser$30(var2, var3);
         case 31:
            return this.parse$31(var2, var3);
         case 32:
            return this.get_doctest$32(var2, var3);
         case 33:
            return this.get_examples$33(var2, var3);
         case 34:
            return this._parse_example$34(var2, var3);
         case 35:
            return this._find_options$35(var2, var3);
         case 36:
            return this._min_indent$36(var2, var3);
         case 37:
            return this._check_prompt_blank$37(var2, var3);
         case 38:
            return this._check_prefix$38(var2, var3);
         case 39:
            return this.DocTestFinder$39(var2, var3);
         case 40:
            return this.__init__$40(var2, var3);
         case 41:
            return this.find$41(var2, var3);
         case 42:
            return this._from_module$42(var2, var3);
         case 43:
            return this._find$43(var2, var3);
         case 44:
            return this._get_test$44(var2, var3);
         case 45:
            return this._find_lineno$45(var2, var3);
         case 46:
            return this.DocTestRunner$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.report_start$48(var2, var3);
         case 49:
            return this.report_success$49(var2, var3);
         case 50:
            return this.report_failure$50(var2, var3);
         case 51:
            return this.report_unexpected_exception$51(var2, var3);
         case 52:
            return this._failure_header$52(var2, var3);
         case 53:
            return this._DocTestRunner__run$53(var2, var3);
         case 54:
            return this._DocTestRunner__record_outcome$54(var2, var3);
         case 55:
            return this._DocTestRunner__patched_linecache_getlines$55(var2, var3);
         case 56:
            return this.run$56(var2, var3);
         case 57:
            return this.summarize$57(var2, var3);
         case 58:
            return this.merge$58(var2, var3);
         case 59:
            return this.OutputChecker$59(var2, var3);
         case 60:
            return this.check_output$60(var2, var3);
         case 61:
            return this._do_a_fancy_diff$61(var2, var3);
         case 62:
            return this.output_difference$62(var2, var3);
         case 63:
            return this.DocTestFailure$63(var2, var3);
         case 64:
            return this.__init__$64(var2, var3);
         case 65:
            return this.__str__$65(var2, var3);
         case 66:
            return this.UnexpectedException$66(var2, var3);
         case 67:
            return this.__init__$67(var2, var3);
         case 68:
            return this.__str__$68(var2, var3);
         case 69:
            return this.DebugRunner$69(var2, var3);
         case 70:
            return this.run$70(var2, var3);
         case 71:
            return this.report_unexpected_exception$71(var2, var3);
         case 72:
            return this.report_failure$72(var2, var3);
         case 73:
            return this.testmod$73(var2, var3);
         case 74:
            return this.testfile$74(var2, var3);
         case 75:
            return this.run_docstring_examples$75(var2, var3);
         case 76:
            return this.Tester$76(var2, var3);
         case 77:
            return this.__init__$77(var2, var3);
         case 78:
            return this.runstring$78(var2, var3);
         case 79:
            return this.rundoc$79(var2, var3);
         case 80:
            return this.rundict$80(var2, var3);
         case 81:
            return this.run__test__$81(var2, var3);
         case 82:
            return this.summarize$82(var2, var3);
         case 83:
            return this.merge$83(var2, var3);
         case 84:
            return this.set_unittest_reportflags$84(var2, var3);
         case 85:
            return this.DocTestCase$85(var2, var3);
         case 86:
            return this.__init__$86(var2, var3);
         case 87:
            return this.setUp$87(var2, var3);
         case 88:
            return this.tearDown$88(var2, var3);
         case 89:
            return this.runTest$89(var2, var3);
         case 90:
            return this.format_failure$90(var2, var3);
         case 91:
            return this.debug$91(var2, var3);
         case 92:
            return this.id$92(var2, var3);
         case 93:
            return this.__eq__$93(var2, var3);
         case 94:
            return this.__ne__$94(var2, var3);
         case 95:
            return this.__hash__$95(var2, var3);
         case 96:
            return this.__repr__$96(var2, var3);
         case 97:
            return this.shortDescription$97(var2, var3);
         case 98:
            return this.SkipDocTestCase$98(var2, var3);
         case 99:
            return this.__init__$99(var2, var3);
         case 100:
            return this.setUp$100(var2, var3);
         case 101:
            return this.test_skip$101(var2, var3);
         case 102:
            return this.shortDescription$102(var2, var3);
         case 103:
            return this.DocTestSuite$103(var2, var3);
         case 104:
            return this.DocFileCase$104(var2, var3);
         case 105:
            return this.id$105(var2, var3);
         case 106:
            return this.__repr__$106(var2, var3);
         case 107:
            return this.format_failure$107(var2, var3);
         case 108:
            return this.DocFileTest$108(var2, var3);
         case 109:
            return this.DocFileSuite$109(var2, var3);
         case 110:
            return this.script_from_examples$110(var2, var3);
         case 111:
            return this.testsource$111(var2, var3);
         case 112:
            return this.debug_src$112(var2, var3);
         case 113:
            return this.debug_script$113(var2, var3);
         case 114:
            return this.debug$114(var2, var3);
         case 115:
            return this._TestClass$115(var2, var3);
         case 116:
            return this.__init__$116(var2, var3);
         case 117:
            return this.square$117(var2, var3);
         case 118:
            return this.get$118(var2, var3);
         case 119:
            return this._test$119(var2, var3);
         default:
            return null;
      }
   }
}
