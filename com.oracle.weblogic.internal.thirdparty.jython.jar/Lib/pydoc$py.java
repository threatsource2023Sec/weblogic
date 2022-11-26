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
@Filename("pydoc.py")
public class pydoc$py extends PyFunctionTable implements PyRunnable {
   static pydoc$py self;
   static final PyCode f$0;
   static final PyCode deque$1;
   static final PyCode popleft$2;
   static final PyCode pathdirs$3;
   static final PyCode getdoc$4;
   static final PyCode splitdoc$5;
   static final PyCode classname$6;
   static final PyCode isdata$7;
   static final PyCode replace$8;
   static final PyCode cram$9;
   static final PyCode stripid$10;
   static final PyCode _is_some_method$11;
   static final PyCode allmethods$12;
   static final PyCode _split_list$13;
   static final PyCode visiblename$14;
   static final PyCode classify_class_attrs$15;
   static final PyCode fixup$16;
   static final PyCode ispackage$17;
   static final PyCode source_synopsis$18;
   static final PyCode synopsis$19;
   static final PyCode ErrorDuringImport$20;
   static final PyCode __init__$21;
   static final PyCode __str__$22;
   static final PyCode importfile$23;
   static final PyCode safeimport$24;
   static final PyCode Doc$25;
   static final PyCode document$26;
   static final PyCode fail$27;
   static final PyCode getdocloc$28;
   static final PyCode HTMLRepr$29;
   static final PyCode __init__$30;
   static final PyCode escape$31;
   static final PyCode repr$32;
   static final PyCode repr1$33;
   static final PyCode repr_string$34;
   static final PyCode repr_instance$35;
   static final PyCode HTMLDoc$36;
   static final PyCode page$37;
   static final PyCode heading$38;
   static final PyCode section$39;
   static final PyCode bigsection$40;
   static final PyCode preformat$41;
   static final PyCode multicolumn$42;
   static final PyCode grey$43;
   static final PyCode namelink$44;
   static final PyCode classlink$45;
   static final PyCode modulelink$46;
   static final PyCode modpkglink$47;
   static final PyCode markup$48;
   static final PyCode formattree$49;
   static final PyCode docmodule$50;
   static final PyCode f$51;
   static final PyCode f$52;
   static final PyCode docclass$53;
   static final PyCode HorizontalRule$54;
   static final PyCode __init__$55;
   static final PyCode maybe$56;
   static final PyCode spill$57;
   static final PyCode spilldescriptors$58;
   static final PyCode spilldata$59;
   static final PyCode f$60;
   static final PyCode f$61;
   static final PyCode f$62;
   static final PyCode f$63;
   static final PyCode f$64;
   static final PyCode f$65;
   static final PyCode f$66;
   static final PyCode f$67;
   static final PyCode f$68;
   static final PyCode formatvalue$69;
   static final PyCode docroutine$70;
   static final PyCode _docdescriptor$71;
   static final PyCode docproperty$72;
   static final PyCode docother$73;
   static final PyCode docdata$74;
   static final PyCode index$75;
   static final PyCode TextRepr$76;
   static final PyCode __init__$77;
   static final PyCode repr1$78;
   static final PyCode repr_string$79;
   static final PyCode repr_instance$80;
   static final PyCode TextDoc$81;
   static final PyCode bold$82;
   static final PyCode f$83;
   static final PyCode indent$84;
   static final PyCode f$85;
   static final PyCode section$86;
   static final PyCode formattree$87;
   static final PyCode f$88;
   static final PyCode docmodule$89;
   static final PyCode f$90;
   static final PyCode docclass$91;
   static final PyCode makename$92;
   static final PyCode HorizontalRule$93;
   static final PyCode __init__$94;
   static final PyCode maybe$95;
   static final PyCode spill$96;
   static final PyCode spilldescriptors$97;
   static final PyCode spilldata$98;
   static final PyCode f$99;
   static final PyCode f$100;
   static final PyCode f$101;
   static final PyCode f$102;
   static final PyCode f$103;
   static final PyCode f$104;
   static final PyCode f$105;
   static final PyCode formatvalue$106;
   static final PyCode docroutine$107;
   static final PyCode _docdescriptor$108;
   static final PyCode docproperty$109;
   static final PyCode docdata$110;
   static final PyCode docother$111;
   static final PyCode AnsiDoc$112;
   static final PyCode bold$113;
   static final PyCode pager$114;
   static final PyCode JLine2Pager$115;
   static final PyCode available$116;
   static final PyCode __init__$117;
   static final PyCode visible$118;
   static final PyCode handle_prompt$119;
   static final PyCode page$120;
   static final PyCode getpager$121;
   static final PyCode f$122;
   static final PyCode f$123;
   static final PyCode f$124;
   static final PyCode f$125;
   static final PyCode f$126;
   static final PyCode f$127;
   static final PyCode f$128;
   static final PyCode plain$129;
   static final PyCode pipepager$130;
   static final PyCode tempfilepager$131;
   static final PyCode ttypager$132;
   static final PyCode f$133;
   static final PyCode f$134;
   static final PyCode plainpager$135;
   static final PyCode describe$136;
   static final PyCode locate$137;
   static final PyCode _OldStyleClass$138;
   static final PyCode resolve$139;
   static final PyCode render_doc$140;
   static final PyCode doc$141;
   static final PyCode writedoc$142;
   static final PyCode writedocs$143;
   static final PyCode Helper$144;
   static final PyCode __init__$145;
   static final PyCode f$146;
   static final PyCode f$147;
   static final PyCode __repr__$148;
   static final PyCode __call__$149;
   static final PyCode interact$150;
   static final PyCode getline$151;
   static final PyCode help$152;
   static final PyCode intro$153;
   static final PyCode list$154;
   static final PyCode listkeywords$155;
   static final PyCode listsymbols$156;
   static final PyCode listtopics$157;
   static final PyCode showtopic$158;
   static final PyCode showsymbol$159;
   static final PyCode listmodules$160;
   static final PyCode callback$161;
   static final PyCode onerror$162;
   static final PyCode Scanner$163;
   static final PyCode __init__$164;
   static final PyCode next$165;
   static final PyCode ModuleScanner$166;
   static final PyCode run$167;
   static final PyCode apropos$168;
   static final PyCode callback$169;
   static final PyCode onerror$170;
   static final PyCode serve$171;
   static final PyCode Message$172;
   static final PyCode __init__$173;
   static final PyCode DocHandler$174;
   static final PyCode send_document$175;
   static final PyCode do_GET$176;
   static final PyCode bltinlink$177;
   static final PyCode f$178;
   static final PyCode log_message$179;
   static final PyCode DocServer$180;
   static final PyCode __init__$181;
   static final PyCode serve_until_quit$182;
   static final PyCode server_activate$183;
   static final PyCode gui$184;
   static final PyCode GUI$185;
   static final PyCode __init__$186;
   static final PyCode ready$187;
   static final PyCode open$188;
   static final PyCode quit$189;
   static final PyCode search$190;
   static final PyCode update$191;
   static final PyCode stop$192;
   static final PyCode done$193;
   static final PyCode select$194;
   static final PyCode goto$195;
   static final PyCode collapse$196;
   static final PyCode expand$197;
   static final PyCode hide$198;
   static final PyCode ispath$199;
   static final PyCode cli$200;
   static final PyCode BadUsage$201;
   static final PyCode ready$202;
   static final PyCode stopped$203;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Generate Python documentation in HTML or text for interactive use.\n\nIn the Python interpreter, do \"from pydoc import help\" to provide online\nhelp.  Calling help(thing) on a Python object documents the object.\n\nOr, at the shell command line outside of Python:\n\nRun \"pydoc <name>\" to show documentation on something.  <name> may be\nthe name of a function, module, package, or a dotted reference to a\nclass or function within a module or module in a package.  If the\nargument contains a path segment delimiter (e.g. slash on Unix,\nbackslash on Windows) it is treated as the path to a Python source file.\n\nRun \"pydoc -k <keyword>\" to search for a keyword in the synopsis lines\nof all available modules.\n\nRun \"pydoc -p <port>\" to start an HTTP server on a given port on the\nlocal machine to generate documentation web pages.\n\nFor platforms without a command line, \"pydoc -g\" starts the HTTP server\nand also pops up a little window for controlling it.\n\nRun \"pydoc -w <name>\" to write out the HTML documentation for a module\nto a file named \"<name>.html\".\n\nModule docs for core modules are assumed to be in\n\n    http://docs.python.org/library/\n\nThis can be overridden by setting the PYTHONDOCS environment variable\nto a different URL or to a local directory containing the Library\nReference Manual pages.\n"));
      var1.setline(35);
      PyString.fromInterned("Generate Python documentation in HTML or text for interactive use.\n\nIn the Python interpreter, do \"from pydoc import help\" to provide online\nhelp.  Calling help(thing) on a Python object documents the object.\n\nOr, at the shell command line outside of Python:\n\nRun \"pydoc <name>\" to show documentation on something.  <name> may be\nthe name of a function, module, package, or a dotted reference to a\nclass or function within a module or module in a package.  If the\nargument contains a path segment delimiter (e.g. slash on Unix,\nbackslash on Windows) it is treated as the path to a Python source file.\n\nRun \"pydoc -k <keyword>\" to search for a keyword in the synopsis lines\nof all available modules.\n\nRun \"pydoc -p <port>\" to start an HTTP server on a given port on the\nlocal machine to generate documentation web pages.\n\nFor platforms without a command line, \"pydoc -g\" starts the HTTP server\nand also pops up a little window for controlling it.\n\nRun \"pydoc -w <name>\" to write out the HTML documentation for a module\nto a file named \"<name>.html\".\n\nModule docs for core modules are assumed to be in\n\n    http://docs.python.org/library/\n\nThis can be overridden by setting the PYTHONDOCS environment variable\nto a different URL or to a local directory containing the Library\nReference Manual pages.\n");
      var1.setline(37);
      PyString var3 = PyString.fromInterned("Ka-Ping Yee <ping@lfw.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(38);
      var3 = PyString.fromInterned("26 February 2001");
      var1.setlocal("__date__", var3);
      var3 = null;
      var1.setline(40);
      var3 = PyString.fromInterned("$Revision: 88564 $");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(41);
      var3 = PyString.fromInterned("Guido van Rossum, for an excellent programming language.\nTommy Burnette, the original creator of manpy.\nPaul Prescod, for all his work on onlinehelp.\nRichard Chamberlain, for the first implementation of textdoc.\n");
      var1.setlocal("__credits__", var3);
      var3 = null;
      var1.setline(55);
      PyObject var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var7 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var7);
      var3 = null;
      var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var7 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var7);
      var3 = null;
      var7 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var7);
      var3 = null;
      var7 = imp.importOne("inspect", var1, -1);
      var1.setlocal("inspect", var7);
      var3 = null;
      var7 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var7);
      var3 = null;
      var7 = imp.importOne("pkgutil", var1, -1);
      var1.setlocal("pkgutil", var7);
      var3 = null;
      var7 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var7);
      var3 = null;
      var1.setline(56);
      String[] var9 = new String[]{"Repr"};
      PyObject[] var10 = imp.importFrom("repr", var9, var1, -1);
      PyObject var4 = var10[0];
      var1.setlocal("Repr", var4);
      var4 = null;
      var1.setline(57);
      var9 = new String[]{"expandtabs", "find", "join", "lower", "split", "strip", "rfind", "rstrip"};
      var10 = imp.importFrom("string", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("expandtabs", var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal("find", var4);
      var4 = null;
      var4 = var10[2];
      var1.setlocal("join", var4);
      var4 = null;
      var4 = var10[3];
      var1.setlocal("lower", var4);
      var4 = null;
      var4 = var10[4];
      var1.setlocal("split", var4);
      var4 = null;
      var4 = var10[5];
      var1.setlocal("strip", var4);
      var4 = null;
      var4 = var10[6];
      var1.setlocal("rfind", var4);
      var4 = null;
      var4 = var10[7];
      var1.setlocal("rstrip", var4);
      var4 = null;
      var1.setline(58);
      var9 = new String[]{"extract_tb"};
      var10 = imp.importFrom("traceback", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("extract_tb", var4);
      var4 = null;

      try {
         var1.setline(60);
         var9 = new String[]{"deque"};
         var10 = imp.importFrom("collections", var9, var1, -1);
         var4 = var10[0];
         var1.setlocal("deque", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var11 = Py.setException(var6, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(63);
         PyObject[] var8 = new PyObject[]{var1.getname("list")};
         PyObject var5 = Py.makeClass("deque", var8, deque$1);
         var1.setlocal("deque", var5);
         var5 = null;
         Arrays.fill(var8, (Object)null);
      }

      var1.setline(67);
      var7 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var7._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var7 = var1.getname("os").__getattr__("name");
         var10000 = var7._eq(PyString.fromInterned("java"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var7 = var1.getname("os").__getattr__("_name");
            var10000 = var7._eq(PyString.fromInterned("nt"));
            var3 = null;
         }
      }

      var7 = var10000;
      var1.setlocal("_is_windows", var7);
      var3 = null;
      var1.setline(71);
      var10 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var10, pathdirs$3, PyString.fromInterned("Convert sys.path into a list of absolute, existing, unique paths."));
      var1.setlocal("pathdirs", var12);
      var3 = null;
      var1.setline(83);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, getdoc$4, PyString.fromInterned("Get the doc string or comments for an object."));
      var1.setlocal("getdoc", var12);
      var3 = null;
      var1.setline(88);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, splitdoc$5, PyString.fromInterned("Split a doc string into a synopsis line (if any) and the rest."));
      var1.setlocal("splitdoc", var12);
      var3 = null;
      var1.setline(97);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, classname$6, PyString.fromInterned("Get a class name and qualify it with a module name if necessary."));
      var1.setlocal("classname", var12);
      var3 = null;
      var1.setline(104);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, isdata$7, PyString.fromInterned("Check if an object is of a type that probably means it's data."));
      var1.setlocal("isdata", var12);
      var3 = null;
      var1.setline(110);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, replace$8, PyString.fromInterned("Do a series of global replacements on a string."));
      var1.setlocal("replace", var12);
      var3 = null;
      var1.setline(117);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, cram$9, PyString.fromInterned("Omit part of a string if needed to make it fit in a maximum length."));
      var1.setlocal("cram", var12);
      var3 = null;
      var1.setline(125);
      var7 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" at 0x[0-9a-f]{6,16}(>+)$"), (PyObject)var1.getname("re").__getattr__("IGNORECASE"));
      var1.setlocal("_re_stripid", var7);
      var3 = null;
      var1.setline(126);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, stripid$10, PyString.fromInterned("Remove the hexadecimal id from a Python object representation."));
      var1.setlocal("stripid", var12);
      var3 = null;
      var1.setline(131);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _is_some_method$11, (PyObject)null);
      var1.setlocal("_is_some_method", var12);
      var3 = null;
      var1.setline(134);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, allmethods$12, (PyObject)null);
      var1.setlocal("allmethods", var12);
      var3 = null;
      var1.setline(144);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _split_list$13, PyString.fromInterned("Split sequence s via predicate, and return pair ([true], [false]).\n\n    The return value is a 2-tuple of lists,\n        ([x for x in s if predicate(x)],\n         [x for x in s if not predicate(x)])\n    "));
      var1.setlocal("_split_list", var12);
      var3 = null;
      var1.setline(161);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var10, visiblename$14, PyString.fromInterned("Decide whether to show documentation on a variable."));
      var1.setlocal("visiblename", var12);
      var3 = null;
      var1.setline(178);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, classify_class_attrs$15, PyString.fromInterned("Wrap inspect.classify_class_attrs, with fixup for data descriptors."));
      var1.setlocal("classify_class_attrs", var12);
      var3 = null;
      var1.setline(189);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, ispackage$17, PyString.fromInterned("Guess whether a path refers to a package directory."));
      var1.setlocal("ispackage", var12);
      var3 = null;
      var1.setline(197);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, source_synopsis$18, (PyObject)null);
      var1.setlocal("source_synopsis", var12);
      var3 = null;
      var1.setline(214);
      var10 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      var12 = new PyFunction(var1.f_globals, var10, synopsis$19, PyString.fromInterned("Get the one-line summary out of a module file."));
      var1.setlocal("synopsis", var12);
      var3 = null;
      var1.setline(236);
      var10 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("ErrorDuringImport", var10, ErrorDuringImport$20);
      var1.setlocal("ErrorDuringImport", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(251);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, importfile$23, PyString.fromInterned("Import a Python source file or compiled file given its path."));
      var1.setlocal("importfile", var12);
      var3 = null;
      var1.setline(270);
      var10 = new PyObject[]{Py.newInteger(0), new PyDictionary(Py.EmptyObjects)};
      var12 = new PyFunction(var1.f_globals, var10, safeimport$24, PyString.fromInterned("Import a module; handle errors; return None if the module isn't found.\n\n    If the module *is* found but an exception occurs, it's wrapped in an\n    ErrorDuringImport exception and reraised.  Unlike __import__, if a\n    package path is specified, the module at the end of the path is returned,\n    not the package at the beginning.  If the optional 'forceload' argument\n    is 1, we reload the module from disk (unless it's a dynamic extension)."));
      var1.setlocal("safeimport", var12);
      var3 = null;
      var1.setline(320);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Doc", var10, Doc$25);
      var1.setlocal("Doc", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(376);
      var10 = new PyObject[]{var1.getname("Repr")};
      var4 = Py.makeClass("HTMLRepr", var10, HTMLRepr$29);
      var1.setlocal("HTMLRepr", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(418);
      var10 = new PyObject[]{var1.getname("Doc")};
      var4 = Py.makeClass("HTMLDoc", var10, HTMLDoc$36);
      var1.setlocal("HTMLDoc", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(959);
      var10 = new PyObject[]{var1.getname("Repr")};
      var4 = Py.makeClass("TextRepr", var10, TextRepr$76);
      var1.setlocal("TextRepr", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(991);
      var10 = new PyObject[]{var1.getname("Doc")};
      var4 = Py.makeClass("TextDoc", var10, TextDoc$81);
      var1.setlocal("TextDoc", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1333);
      var10 = new PyObject[]{var1.getname("TextDoc")};
      var4 = Py.makeClass("AnsiDoc", var10, AnsiDoc$112);
      var1.setlocal("AnsiDoc", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1343);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, pager$114, PyString.fromInterned("The first time this is called, determine what kind of pager to use."));
      var1.setlocal("pager", var12);
      var3 = null;
      var1.setline(1350);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("JLine2Pager", var10, JLine2Pager$115);
      var1.setlocal("JLine2Pager", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1428);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, getpager$121, PyString.fromInterned("Decide what method to use for paging through text."));
      var1.setlocal("getpager", var12);
      var3 = null;
      var1.setline(1461);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, plain$129, PyString.fromInterned("Remove boldface formatting from text."));
      var1.setlocal("plain", var12);
      var3 = null;
      var1.setline(1465);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, pipepager$130, PyString.fromInterned("Page through text by feeding it to another program."));
      var1.setlocal("pipepager", var12);
      var3 = null;
      var1.setline(1474);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, tempfilepager$131, PyString.fromInterned("Page through text by invoking a program on a temporary file."));
      var1.setlocal("tempfilepager", var12);
      var3 = null;
      var1.setline(1486);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, ttypager$132, PyString.fromInterned("Page through text on a text terminal."));
      var1.setlocal("ttypager", var12);
      var3 = null;
      var1.setline(1524);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, plainpager$135, PyString.fromInterned("Simply print unformatted text.  This is the ultimate fallback."));
      var1.setlocal("plainpager", var12);
      var3 = null;
      var1.setline(1528);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, describe$136, PyString.fromInterned("Produce a short description of the given thing."));
      var1.setlocal("describe", var12);
      var3 = null;
      var1.setline(1557);
      var10 = new PyObject[]{Py.newInteger(0)};
      var12 = new PyFunction(var1.f_globals, var10, locate$137, PyString.fromInterned("Locate an object by name or dotted path, importing as necessary."));
      var1.setlocal("locate", var12);
      var3 = null;
      var1.setline(1578);
      var10000 = var1.getname("_is_windows");
      if (var10000.__nonzero__()) {
         var10000 = var1.getname("JLine2Pager").__getattr__("available").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(1579);
         var7 = var1.getname("AnsiDoc").__call__(var2);
         var1.setlocal("text", var7);
         var3 = null;
      } else {
         var1.setline(1581);
         var7 = var1.getname("TextDoc").__call__(var2);
         var1.setlocal("text", var7);
         var3 = null;
      }

      var1.setline(1582);
      var7 = var1.getname("HTMLDoc").__call__(var2);
      var1.setlocal("html", var7);
      var3 = null;
      var1.setline(1584);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_OldStyleClass", var10, _OldStyleClass$138);
      var1.setlocal("_OldStyleClass", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1585);
      var7 = var1.getname("type").__call__(var2, var1.getname("_OldStyleClass").__call__(var2));
      var1.setlocal("_OLD_INSTANCE_TYPE", var7);
      var3 = null;
      var1.setline(1587);
      var10 = new PyObject[]{Py.newInteger(0)};
      var12 = new PyFunction(var1.f_globals, var10, resolve$139, PyString.fromInterned("Given an object or a path to an object, get the object and its name."));
      var1.setlocal("resolve", var12);
      var3 = null;
      var1.setline(1598);
      var10 = new PyObject[]{PyString.fromInterned("Python Library Documentation: %s"), Py.newInteger(0)};
      var12 = new PyFunction(var1.f_globals, var10, render_doc$140, PyString.fromInterned("Render text documentation, given an object or a path to an object."));
      var1.setlocal("render_doc", var12);
      var3 = null;
      var1.setline(1623);
      var10 = new PyObject[]{PyString.fromInterned("Python Library Documentation: %s"), Py.newInteger(0)};
      var12 = new PyFunction(var1.f_globals, var10, doc$141, PyString.fromInterned("Display text documentation, given an object or a path to an object."));
      var1.setlocal("doc", var12);
      var3 = null;
      var1.setline(1630);
      var10 = new PyObject[]{Py.newInteger(0)};
      var12 = new PyFunction(var1.f_globals, var10, writedoc$142, PyString.fromInterned("Write HTML documentation to a file in the current directory."));
      var1.setlocal("writedoc", var12);
      var3 = null;
      var1.setline(1642);
      var10 = new PyObject[]{PyString.fromInterned(""), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var10, writedocs$143, PyString.fromInterned("Write out HTML documentation for all modules in a directory tree."));
      var1.setlocal("writedocs", var12);
      var3 = null;
      var1.setline(1649);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Helper", var10, Helper$144);
      var1.setlocal("Helper", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2009);
      var7 = var1.getname("Helper").__call__(var2);
      var1.setlocal("help", var7);
      var3 = null;
      var1.setline(2011);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Scanner", var10, Scanner$163);
      var1.setlocal("Scanner", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2035);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("ModuleScanner", var10, ModuleScanner$166);
      var1.setlocal("ModuleScanner", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2079);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, apropos$168, PyString.fromInterned("Print all the one-line module summaries that contain a substring."));
      var1.setlocal("apropos", var12);
      var3 = null;
      var1.setline(2093);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var10, serve$171, (PyObject)null);
      var1.setlocal("serve", var12);
      var3 = null;
      var1.setline(2184);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, gui$184, PyString.fromInterned("Graphical interface (starts web server and pops up a control window)."));
      var1.setlocal("gui", var12);
      var3 = null;
      var1.setline(2367);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, ispath$199, (PyObject)null);
      var1.setlocal("ispath", var12);
      var3 = null;
      var1.setline(2370);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, cli$200, PyString.fromInterned("Command-line interface (looks at sys.argv to decide what to do)."));
      var1.setlocal("cli", var12);
      var3 = null;
      var1.setline(2453);
      var7 = var1.getname("__name__");
      var10000 = var7._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2453);
         var1.getname("cli").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject deque$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(64);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, popleft$2, (PyObject)null);
      var1.setlocal("popleft", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject popleft$2(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pathdirs$3(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Convert sys.path into a list of absolute, existing, unique paths.");
      var1.setline(73);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(74);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(75);
      PyObject var6 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(75);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(81);
            var6 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(76);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("abspath");
         Object var10002 = var1.getlocal(2);
         if (!((PyObject)var10002).__nonzero__()) {
            var10002 = PyString.fromInterned(".");
         }

         PyObject var5 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(77);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(78);
         var5 = var1.getlocal(3);
         var10000 = var5._notin(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2));
         }

         if (var10000.__nonzero__()) {
            var1.setline(79);
            var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
            var1.setline(80);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject getdoc$4(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("Get the doc string or comments for an object.");
      var1.setline(85);
      PyObject var10000 = var1.getglobal("inspect").__getattr__("getdoc").__call__(var2, var1.getlocal(0));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("inspect").__getattr__("getcomments").__call__(var2, var1.getlocal(0));
      }

      PyObject var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(86);
      Object var5 = var1.getlocal(1);
      if (((PyObject)var5).__nonzero__()) {
         var5 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("^ *\n"), (PyObject)PyString.fromInterned(""), (PyObject)var1.getglobal("rstrip").__call__(var2, var1.getlocal(1)));
      }

      if (!((PyObject)var5).__nonzero__()) {
         var5 = PyString.fromInterned("");
      }

      Object var4 = var5;
      var1.f_lasti = -1;
      return (PyObject)var4;
   }

   public PyObject splitdoc$5(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Split a doc string into a synopsis line (if any) and the rest.");
      var1.setline(90);
      PyObject var3 = var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("strip").__call__(var2, var1.getlocal(0)), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(92);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), PyString.fromInterned("")});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(93);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var4._ge(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("rstrip").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(94);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("\n"))});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(95);
            var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("\n"))});
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject classname$6(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("Get a class name and qualify it with a module name if necessary.");
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("__name__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(0).__getattr__("__module__");
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(101);
         var3 = var1.getlocal(0).__getattr__("__module__")._add(PyString.fromInterned("."))._add(var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(102);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdata$7(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Check if an object is of a type that probably means it's data.");
      var1.setline(106);
      PyObject var10000 = var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(0));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("inspect").__getattr__("isroutine").__call__(var2, var1.getlocal(0));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("inspect").__getattr__("isframe").__call__(var2, var1.getlocal(0));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("istraceback").__call__(var2, var1.getlocal(0));
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("inspect").__getattr__("iscode").__call__(var2, var1.getlocal(0));
                  }
               }
            }
         }
      }

      PyObject var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject replace$8(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyString.fromInterned("Do a series of global replacements on a string.");

      while(true) {
         var1.setline(112);
         PyObject var3;
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(115);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(113);
         var3 = var1.getglobal("join").__call__(var2, var1.getglobal("split").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getitem__(Py.newInteger(0))), var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(114);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject cram$9(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyString.fromInterned("Omit part of a string if needed to make it fit in a maximum length.");
      var1.setline(119);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(120);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1)._sub(Py.newInteger(3))._floordiv(Py.newInteger(2)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(121);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1)._sub(Py.newInteger(3))._sub(var1.getlocal(2)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(122);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)._add(PyString.fromInterned("..."))._add(var1.getlocal(0).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(0))._sub(var1.getlocal(3)), (PyObject)null, (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(123);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject stripid$10(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned("Remove the hexadecimal id from a Python object representation.");
      var1.setline(129);
      PyObject var3 = var1.getglobal("_re_stripid").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\1"), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _is_some_method$11(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var10000 = var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(0));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("inspect").__getattr__("ismethoddescriptor").__call__(var2, var1.getlocal(0));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject allmethods$12(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(136);
      PyObject var7 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(0), var1.getglobal("_is_some_method")).__iter__();

      while(true) {
         var1.setline(136);
         PyObject var4 = var7.__iternext__();
         PyObject[] var5;
         if (var4 == null) {
            var1.setline(138);
            var7 = var1.getlocal(0).__getattr__("__bases__").__iter__();

            while(true) {
               var1.setline(138);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(140);
                  var7 = var1.getlocal(1).__getattr__("keys").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(140);
                     var4 = var7.__iternext__();
                     if (var4 == null) {
                        var1.setline(142);
                        var7 = var1.getlocal(1);
                        var1.f_lasti = -1;
                        return var7;
                     }

                     var1.setlocal(2, var4);
                     var1.setline(141);
                     PyObject var9 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2));
                     var1.getlocal(1).__setitem__(var1.getlocal(2), var9);
                     var5 = null;
                  }
               }

               var1.setlocal(4, var4);
               var1.setline(139);
               var1.getlocal(1).__getattr__("update").__call__(var2, var1.getglobal("allmethods").__call__(var2, var1.getlocal(4)));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(137);
         PyInteger var8 = Py.newInteger(1);
         var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var8);
         var5 = null;
      }
   }

   public PyObject _split_list$13(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Split sequence s via predicate, and return pair ([true], [false]).\n\n    The return value is a 2-tuple of lists,\n        ([x for x in s if predicate(x)],\n         [x for x in s if not predicate(x)])\n    ");
      var1.setline(152);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(153);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(154);
      PyObject var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(154);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(159);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(155);
         if (var1.getlocal(1).__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(156);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(158);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject visiblename$14(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyString.fromInterned("Decide whether to show documentation on a variable.");
      var1.setline(164);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("__builtins__"), PyString.fromInterned("__doc__"), PyString.fromInterned("__file__"), PyString.fromInterned("__path__"), PyString.fromInterned("__module__"), PyString.fromInterned("__name__"), PyString.fromInterned("__slots__"), PyString.fromInterned("__package__")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(166);
      PyObject var5 = var1.getlocal(0);
      PyObject var10000 = var5._in(var1.getlocal(3));
      var3 = null;
      PyInteger var6;
      if (var10000.__nonzero__()) {
         var1.setline(166);
         var6 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(168);
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(168);
            var6 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(170);
            var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_fields"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(171);
               var6 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(172);
               PyObject var4 = var1.getlocal(1);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(174);
                  var4 = var1.getlocal(0);
                  var10000 = var4._in(var1.getlocal(1));
                  var4 = null;
                  var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(176);
                  var5 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_")).__not__();
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      }
   }

   public PyObject classify_class_attrs$15(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyString.fromInterned("Wrap inspect.classify_class_attrs, with fixup for data descriptors.");
      var1.setline(180);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, fixup$16, (PyObject)null);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(185);
      PyObject var5 = var1.getglobal("map").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("classify_class_attrs").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject fixup$16(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
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
      var3 = null;
      var1.setline(182);
      if (var1.getglobal("inspect").__getattr__("isdatadescriptor").__call__(var2, var1.getlocal(4)).__nonzero__()) {
         var1.setline(183);
         PyString var6 = PyString.fromInterned("data descriptor");
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(184);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject ispackage$17(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyString.fromInterned("Guess whether a path refers to a package directory.");
      var1.setline(191);
      PyObject var5;
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(192);
         PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned(".py"), PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo"), PyString.fromInterned("$py.class")})).__iter__();

         while(true) {
            var1.setline(192);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(193);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), PyString.fromInterned("__init__")._add(var1.getlocal(1)))).__nonzero__()) {
               var1.setline(194);
               var5 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var5;
            }
         }
      }

      var1.setline(195);
      var5 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject source_synopsis$18(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var10000;
      do {
         var1.setline(199);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("#"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("strip").__call__(var2, var1.getlocal(1)).__not__();
         }

         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(200);
         var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(201);
      } while(!var1.getlocal(1).__not__().__nonzero__());

      var1.setline(202);
      var3 = var1.getglobal("strip").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(203);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("r\"\"\""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(203);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(204);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("\"\"\""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(205);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(206);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("\\"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(206);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
         }

         do {
            var1.setline(207);
            if (!var1.getglobal("strip").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
               break;
            }

            var1.setline(208);
            var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(209);
         } while(!var1.getlocal(1).__not__().__nonzero__());

         var1.setline(210);
         var3 = var1.getglobal("strip").__call__(var2, var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("\"\"\"")).__getitem__(Py.newInteger(0)));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(211);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(212);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject synopsis$19(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyString.fromInterned("Get the one-line summary out of a module file.");
      var1.setline(216);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_mtime");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")})));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(218);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
      }

      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(219);
         var3 = var1.getglobal("inspect").__getattr__("getmoduleinfo").__call__(var2, var1.getlocal(0));
         var1.setlocal(5, var3);
         var3 = null;

         try {
            var1.setline(221);
            var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
            var1.setlocal(6, var3);
            var3 = null;
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (var9.match(var1.getglobal("IOError"))) {
               var1.setline(224);
               var8 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var8;
            }

            throw var9;
         }

         var1.setline(225);
         var10000 = var1.getlocal(5);
         if (var10000.__nonzero__()) {
            PyString var10 = PyString.fromInterned("b");
            var10000 = var10._in(var1.getlocal(5).__getitem__(Py.newInteger(2)));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            try {
               var1.setline(226);
               var3 = var1.getglobal("imp").__getattr__("load_module").__call__(var2, PyString.fromInterned("__temp__"), var1.getlocal(6), var1.getlocal(0), var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               var1.setlocal(7, var3);
               var3 = null;
            } catch (Throwable var6) {
               Py.setException(var6, var1);
               var1.setline(227);
               var8 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(228);
            Object var12 = var1.getlocal(7).__getattr__("__doc__");
            if (!((PyObject)var12).__nonzero__()) {
               var12 = PyString.fromInterned("");
            }

            var3 = ((PyObject)var12).__getattr__("splitlines").__call__(var2).__getitem__(Py.newInteger(0));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(229);
            var1.getglobal("sys").__getattr__("modules").__delitem__((PyObject)PyString.fromInterned("__temp__"));
         } else {
            var1.setline(231);
            var3 = var1.getglobal("source_synopsis").__call__(var2, var1.getlocal(6));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(232);
            var1.getlocal(6).__getattr__("close").__call__(var2);
         }

         var1.setline(233);
         PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)});
         var1.getlocal(1).__setitem__((PyObject)var1.getlocal(0), var11);
         var3 = null;
      }

      var1.setline(234);
      var8 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject ErrorDuringImport$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Errors that occurred while trying to import something to document it."));
      var1.setline(237);
      PyString.fromInterned("Errors that occurred while trying to import something to document it.");
      var1.setline(238);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(245);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$22, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyObject var3 = var1.getlocal(2);
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
      var1.setline(240);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(241);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("exc", var3);
      var3 = null;
      var1.setline(242);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("tb", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$22(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyObject var3 = var1.getlocal(0).__getattr__("exc");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(247);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._is(var1.getglobal("types").__getattr__("ClassType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(248);
         var3 = var1.getlocal(1).__getattr__("__name__");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(249);
      var3 = PyString.fromInterned("problem in %s - %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("filename"), var1.getlocal(1), var1.getlocal(0).__getattr__("value")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject importfile$23(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Import a Python source file or compiled file given its path.");
      var1.setline(253);
      PyObject var3 = var1.getglobal("imp").__getattr__("get_magic").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(254);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getlocal(2).__getattr__("read").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(256);
         var3 = var1.getglobal("imp").__getattr__("PY_COMPILED");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(258);
         var3 = var1.getglobal("imp").__getattr__("PY_SOURCE");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(259);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(260);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(262);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(264);
         var3 = var1.getglobal("imp").__getattr__("load_module").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(0), new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned("r"), var1.getlocal(3)}));
         var1.setlocal(7, var3);
         var3 = null;
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(266);
         throw Py.makeException(var1.getglobal("ErrorDuringImport").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2)));
      }

      var1.setline(267);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(268);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject safeimport$24(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("Import a module; handle errors; return None if the module isn't found.\n\n    If the module *is* found but an exception occurs, it's wrapped in an\n    ErrorDuringImport exception and reraised.  Unlike __import__, if a\n    package path is specified, the module at the end of the path is returned,\n    not the package at the beginning.  If the optional 'forceload' argument\n    is 1, we reload the module from disk (unless it's a dynamic extension).");

      PyObject var3;
      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      PyObject var10;
      PyObject var10000;
      try {
         var1.setline(283);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._in(var1.getglobal("sys").__getattr__("modules"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(284);
            var3 = var1.getlocal(0);
            var10000 = var3._notin(var1.getglobal("sys").__getattr__("builtin_module_names"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(291);
               PyList var12 = new PyList();
               var3 = var12.__getattr__("append");
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(291);
               var3 = var1.getglobal("sys").__getattr__("modules").__iter__();

               label75:
               while(true) {
                  var1.setline(291);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(291);
                     var1.dellocal(4);
                     PyList var9 = var12;
                     var1.setlocal(3, var9);
                     var3 = null;
                     var1.setline(292);
                     var3 = (new PyList(new PyObject[]{var1.getlocal(0)}))._add(var1.getlocal(3)).__iter__();

                     while(true) {
                        var1.setline(292);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break label75;
                        }

                        var1.setlocal(6, var4);
                        var1.setline(294);
                        var10 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(6));
                        var1.getlocal(2).__setitem__(var1.getlocal(6), var10);
                        var5 = null;
                        var1.setline(295);
                        var1.getglobal("sys").__getattr__("modules").__delitem__(var1.getlocal(6));
                     }
                  }

                  var1.setlocal(5, var4);
                  var1.setline(291);
                  if (var1.getlocal(5).__getattr__("startswith").__call__(var2, var1.getlocal(0)._add(PyString.fromInterned("."))).__nonzero__()) {
                     var1.setline(291);
                     var1.getlocal(4).__call__(var2, var1.getlocal(5));
                  }
               }
            }
         }

         var1.setline(296);
         var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(0));
         var1.setlocal(7, var3);
         var3 = null;
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(299);
         var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
         var5 = Py.unpackSequence(var4, 3);
         var6 = var5[0];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(10, var6);
         var6 = null;
         var1.setlocal(11, var4);
         var1.setline(300);
         var4 = var1.getlocal(0);
         var10000 = var4._in(var1.getglobal("sys").__getattr__("modules"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(302);
            throw Py.makeException(var1.getglobal("ErrorDuringImport").__call__(var2, var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(0)).__getattr__("__file__"), var1.getlocal(11)));
         }

         var1.setline(303);
         var4 = var1.getlocal(8);
         var10000 = var4._is(var1.getglobal("SyntaxError"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(305);
            throw Py.makeException(var1.getglobal("ErrorDuringImport").__call__(var2, var1.getlocal(9).__getattr__("filename"), var1.getlocal(11)));
         }

         var1.setline(306);
         var4 = var1.getlocal(8);
         var10000 = var4._is(var1.getglobal("ImportError"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getglobal("extract_tb").__call__(var2, var1.getlocal(10)).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(2));
            var10000 = var4._eq(PyString.fromInterned("safeimport"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(309);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(312);
         throw Py.makeException(var1.getglobal("ErrorDuringImport").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2)));
      }

      var1.setline(313);
      var3 = var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned(".")).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(313);
         var10 = var3.__iternext__();
         if (var10 == null) {
            var1.setline(316);
            var4 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setlocal(12, var10);

         try {
            var1.setline(314);
            var6 = var1.getglobal("getattr").__call__(var2, var1.getlocal(7), var1.getlocal(12));
            var1.setlocal(7, var6);
            var6 = null;
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (var11.match(var1.getglobal("AttributeError"))) {
               var1.setline(315);
               var4 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var4;
            }

            throw var11;
         }
      }
   }

   public PyObject Doc$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(321);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, document$26, PyString.fromInterned("Generate documentation for an object."));
      var1.setlocal("document", var4);
      var3 = null;
      var1.setline(339);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, fail$27, PyString.fromInterned("Raise an exception for unimplemented types."));
      var1.setlocal("fail", var4);
      var3 = null;
      var1.setline(345);
      PyObject var5 = var1.getname("fail");
      var1.setlocal("docmodule", var5);
      var1.setlocal("docclass", var5);
      var1.setlocal("docroutine", var5);
      var1.setlocal("docother", var5);
      var1.setlocal("docproperty", var5);
      var1.setlocal("docdata", var5);
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdocloc$28, PyString.fromInterned("Return the location of module docs or None"));
      var1.setlocal("getdocloc", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject document$26(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("Generate documentation for an object.");
      var1.setline(323);
      PyObject var3 = (new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}))._add(var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(328);
      PyObject var10000;
      if (var1.getglobal("inspect").__getattr__("isgetsetdescriptor").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(328);
         var10000 = var1.getlocal(0).__getattr__("docdata");
         PyObject[] var8 = Py.EmptyObjects;
         String[] var9 = new String[0];
         var10000 = var10000._callextra(var8, var9, var1.getlocal(3), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(329);
         PyException var4;
         String[] var5;
         PyObject[] var7;
         if (var1.getglobal("inspect").__getattr__("ismemberdescriptor").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(329);
            var10000 = var1.getlocal(0).__getattr__("docdata");
            var7 = Py.EmptyObjects;
            var5 = new String[0];
            var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            try {
               var1.setline(331);
               if (var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                  var1.setline(331);
                  var10000 = var1.getlocal(0).__getattr__("docmodule");
                  var7 = Py.EmptyObjects;
                  var5 = new String[0];
                  var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(332);
               if (var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                  var1.setline(332);
                  var10000 = var1.getlocal(0).__getattr__("docclass");
                  var7 = Py.EmptyObjects;
                  var5 = new String[0];
                  var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(333);
               if (var1.getglobal("inspect").__getattr__("isroutine").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                  var1.setline(333);
                  var10000 = var1.getlocal(0).__getattr__("docroutine");
                  var7 = Py.EmptyObjects;
                  var5 = new String[0];
                  var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }
            } catch (Throwable var6) {
               var4 = Py.setException(var6, var1);
               if (!var4.match(var1.getglobal("AttributeError"))) {
                  throw var4;
               }

               var1.setline(335);
            }

            var1.setline(336);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("property")).__nonzero__()) {
               var1.setline(336);
               var10000 = var1.getlocal(0).__getattr__("docproperty");
               var7 = Py.EmptyObjects;
               var5 = new String[0];
               var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(337);
               var10000 = var1.getlocal(0).__getattr__("docother");
               var7 = Py.EmptyObjects;
               var5 = new String[0];
               var10000 = var10000._callextra(var7, var5, var1.getlocal(3), (PyObject)null);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject fail$27(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyString.fromInterned("Raise an exception for unimplemented types.");
      var1.setline(341);
      PyString var10000 = PyString.fromInterned("don't know how to document object%s of type %s");
      PyTuple var10001 = new PyTuple;
      PyObject[] var10003 = new PyObject[2];
      PyObject var10006 = var1.getlocal(2);
      if (var10006.__nonzero__()) {
         var10006 = PyString.fromInterned(" ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
      }

      var10003[0] = var10006;
      var10003[1] = var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__");
      var10001.<init>(var10003);
      PyObject var3 = var10000._mod(var10001);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(343);
      throw Py.makeException(var1.getglobal("TypeError"), var1.getlocal(4));
   }

   public PyObject getdocloc$28(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Return the location of module docs or None");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(351);
         var6 = var1.getglobal("inspect").__getattr__("getabsfile").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("TypeError"))) {
            throw var3;
         }

         var1.setline(353);
         PyString var4 = PyString.fromInterned("(built-in)");
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(355);
      var6 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PYTHONDOCS"), (PyObject)PyString.fromInterned("http://docs.python.org/library"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(357);
      var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("python")._add(var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null)));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(359);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("type").__call__(var2, var1.getglobal("os")));
      if (var10000.__nonzero__()) {
         var6 = var1.getlocal(1).__getattr__("__name__");
         var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned("errno"), PyString.fromInterned("exceptions"), PyString.fromInterned("gc"), PyString.fromInterned("imp"), PyString.fromInterned("marshal"), PyString.fromInterned("posix"), PyString.fromInterned("signal"), PyString.fromInterned("sys"), PyString.fromInterned("thread"), PyString.fromInterned("zipimport")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__getattr__("startswith").__call__(var2, var1.getlocal(4));
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__getattr__("startswith").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("site-packages"))).__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(1).__getattr__("__name__");
            var10000 = var6._notin(new PyTuple(new PyObject[]{PyString.fromInterned("xml.etree"), PyString.fromInterned("test.pydoc_mod")}));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(366);
         if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("http://")).__nonzero__()) {
            var1.setline(367);
            var6 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")), var1.getlocal(1).__getattr__("__name__")}));
            var1.setlocal(3, var6);
            var3 = null;
         } else {
            var1.setline(369);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getattr__("__name__")._add(PyString.fromInterned(".html")));
            var1.setlocal(3, var6);
            var3 = null;
         }
      } else {
         var1.setline(371);
         var6 = var1.getglobal("None");
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(372);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject HTMLRepr$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for safely making an HTML representation of a Python object."));
      var1.setline(377);
      PyString.fromInterned("Class for safely making an HTML representation of a Python object.");
      var1.setline(378);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, escape$31, (PyObject)null);
      var1.setlocal("escape", var4);
      var3 = null;
      var1.setline(387);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr$32, (PyObject)null);
      var1.setlocal("repr", var4);
      var3 = null;
      var1.setline(390);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr1$33, (PyObject)null);
      var1.setlocal("repr1", var4);
      var3 = null;
      var1.setline(397);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_string$34, (PyObject)null);
      var1.setlocal("repr_string", var4);
      var3 = null;
      var1.setline(408);
      PyObject var5 = var1.getname("repr_string");
      var1.setlocal("repr_str", var5);
      var3 = null;
      var1.setline(410);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_instance$35, (PyObject)null);
      var1.setlocal("repr_instance", var4);
      var3 = null;
      var1.setline(416);
      var5 = var1.getname("repr_string");
      var1.setlocal("repr_unicode", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      var1.getglobal("Repr").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(380);
      PyInteger var3 = Py.newInteger(20);
      var1.getlocal(0).__setattr__((String)"maxlist", var3);
      var1.getlocal(0).__setattr__((String)"maxtuple", var3);
      var1.setline(381);
      var3 = Py.newInteger(10);
      var1.getlocal(0).__setattr__((String)"maxdict", var3);
      var3 = null;
      var1.setline(382);
      var3 = Py.newInteger(100);
      var1.getlocal(0).__setattr__((String)"maxstring", var3);
      var1.getlocal(0).__setattr__((String)"maxother", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject escape$31(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyObject var10000 = var1.getglobal("replace");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("&"), PyString.fromInterned("&amp;"), PyString.fromInterned("<"), PyString.fromInterned("&lt;"), PyString.fromInterned(">"), PyString.fromInterned("&gt;")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject repr$32(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      PyObject var3 = var1.getglobal("Repr").__getattr__("repr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr1$33(PyFrame var1, ThreadState var2) {
      var1.setline(391);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("type").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("__name__")).__nonzero__()) {
         var1.setline(392);
         var3 = PyString.fromInterned("repr_")._add(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("split").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__")), (PyObject)PyString.fromInterned("_")));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(393);
         if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(3)).__nonzero__()) {
            var1.setline(394);
            var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3)).__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(395);
      var3 = var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getglobal("cram").__call__(var2, var1.getglobal("stripid").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1))), var1.getlocal(0).__getattr__("maxother")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_string$34(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyObject var3 = var1.getglobal("cram").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("maxstring"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(399);
      var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(400);
      PyString var4 = PyString.fromInterned("\\");
      PyObject var10000 = var4._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var4 = PyString.fromInterned("\\");
         var10000 = var4._notin(var1.getglobal("replace").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("\\\\"), (PyObject)PyString.fromInterned("")));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(403);
         var3 = PyString.fromInterned("r")._add(var1.getlocal(4).__getitem__(Py.newInteger(0)))._add(var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getlocal(3)))._add(var1.getlocal(4).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(404);
         var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("((\\\\[\\\\abfnrtv\\'\"]|\\\\[0-9]..|\\\\x..|\\\\u....)+)"), (PyObject)PyString.fromInterned("<font color=\"#c040c0\">\\1</font>"), (PyObject)var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getlocal(4)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject repr_instance$35(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(412);
         var3 = var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getglobal("cram").__call__(var2, var1.getglobal("stripid").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1))), var1.getlocal(0).__getattr__("maxstring")));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(414);
         var3 = var1.getlocal(0).__getattr__("escape").__call__(var2, PyString.fromInterned("<%s instance>")._mod(var1.getlocal(1).__getattr__("__class__").__getattr__("__name__")));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject HTMLDoc$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Formatter class for HTML documentation."));
      var1.setline(419);
      PyString.fromInterned("Formatter class for HTML documentation.");
      var1.setline(423);
      PyObject var3 = var1.getname("HTMLRepr").__call__(var2);
      var1.setlocal("_repr_instance", var3);
      var3 = null;
      var1.setline(424);
      var3 = var1.getname("_repr_instance").__getattr__("repr");
      var1.setlocal("repr", var3);
      var3 = null;
      var1.setline(425);
      var3 = var1.getname("_repr_instance").__getattr__("escape");
      var1.setlocal("escape", var3);
      var3 = null;
      var1.setline(427);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, page$37, PyString.fromInterned("Format an HTML page."));
      var1.setlocal("page", var5);
      var3 = null;
      var1.setline(436);
      var4 = new PyObject[]{PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, heading$38, PyString.fromInterned("Format a page heading."));
      var1.setlocal("heading", var5);
      var3 = null;
      var1.setline(447);
      var4 = new PyObject[]{Py.newInteger(6), PyString.fromInterned(""), var1.getname("None"), PyString.fromInterned("&nbsp;")};
      var5 = new PyFunction(var1.f_globals, var4, section$39, PyString.fromInterned("Format a section with a heading."));
      var1.setlocal("section", var5);
      var3 = null;
      var1.setline(469);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, bigsection$40, PyString.fromInterned("Format a section with a big heading."));
      var1.setlocal("bigsection", var5);
      var3 = null;
      var1.setline(474);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, preformat$41, PyString.fromInterned("Format literal preformatted text."));
      var1.setlocal("preformat", var5);
      var3 = null;
      var1.setline(480);
      var4 = new PyObject[]{Py.newInteger(4)};
      var5 = new PyFunction(var1.f_globals, var4, multicolumn$42, PyString.fromInterned("Format a list of items into a multi-column list."));
      var1.setlocal("multicolumn", var5);
      var3 = null;
      var1.setline(492);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, grey$43, (PyObject)null);
      var1.setlocal("grey", var5);
      var3 = null;
      var1.setline(494);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, namelink$44, PyString.fromInterned("Make a link for an identifier, given name-to-URL mappings."));
      var1.setlocal("namelink", var5);
      var3 = null;
      var1.setline(501);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, classlink$45, PyString.fromInterned("Make a link for a class."));
      var1.setlocal("classlink", var5);
      var3 = null;
      var1.setline(509);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, modulelink$46, PyString.fromInterned("Make a link for a module."));
      var1.setlocal("modulelink", var5);
      var3 = null;
      var1.setline(513);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, modpkglink$47, PyString.fromInterned("Make a link for a module or package to display in an index."));
      var1.setlocal("modpkglink", var5);
      var3 = null;
      var1.setline(528);
      var4 = new PyObject[]{var1.getname("None"), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)};
      var5 = new PyFunction(var1.f_globals, var4, markup$48, PyString.fromInterned("Mark up some plain text, given a context of symbols to look for.\n        Each context dictionary maps object names to anchor names."));
      var1.setlocal("markup", var5);
      var3 = null;
      var1.setline(566);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, formattree$49, PyString.fromInterned("Produce HTML for a class tree as given by inspect.getclasstree()."));
      var1.setlocal("formattree", var5);
      var3 = null;
      var1.setline(585);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docmodule$50, PyString.fromInterned("Produce HTML documentation for a module object."));
      var1.setlocal("docmodule", var5);
      var3 = null;
      var1.setline(709);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)};
      var5 = new PyFunction(var1.f_globals, var4, docclass$53, PyString.fromInterned("Produce HTML documentation for a class object."));
      var1.setlocal("docclass", var5);
      var3 = null;
      var1.setline(861);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, formatvalue$69, PyString.fromInterned("Format an argument default value as text."));
      var1.setlocal("formatvalue", var5);
      var3 = null;
      var1.setline(865);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docroutine$70, PyString.fromInterned("Produce HTML documentation for a function or method object."));
      var1.setlocal("docroutine", var5);
      var3 = null;
      var1.setline(919);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _docdescriptor$71, (PyObject)null);
      var1.setlocal("_docdescriptor", var5);
      var3 = null;
      var1.setline(932);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docproperty$72, PyString.fromInterned("Produce html documentation for a property."));
      var1.setlocal("docproperty", var5);
      var3 = null;
      var1.setline(936);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docother$73, PyString.fromInterned("Produce HTML documentation for a data object."));
      var1.setlocal("docother", var5);
      var3 = null;
      var1.setline(941);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docdata$74, PyString.fromInterned("Produce html documentation for a data descriptor."));
      var1.setlocal("docdata", var5);
      var3 = null;
      var1.setline(945);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, index$75, PyString.fromInterned("Generate an HTML index for a directory of modules."));
      var1.setlocal("index", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject page$37(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyString.fromInterned("Format an HTML page.");
      var1.setline(429);
      PyObject var3 = PyString.fromInterned("\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n<html><head><title>Python: %s</title>\n</head><body bgcolor=\"#f0f0f8\">\n%s\n</body></html>")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject heading$38(PyFrame var1, ThreadState var2) {
      var1.setline(437);
      PyString.fromInterned("Format a page heading.");
      var1.setline(438);
      PyString var10000 = PyString.fromInterned("\n<table width=\"100%%\" cellspacing=0 cellpadding=2 border=0 summary=\"heading\">\n<tr bgcolor=\"%s\">\n<td valign=bottom>&nbsp;<br>\n<font color=\"%s\" face=\"helvetica, arial\">&nbsp;<br>%s</font></td\n><td align=right valign=bottom\n><font color=\"%s\" face=\"helvetica, arial\">%s</font></td></tr></table>\n    ");
      PyTuple var10001 = new PyTuple;
      PyObject[] var10003 = new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(1), var1.getlocal(2), null};
      Object var10006 = var1.getlocal(4);
      if (!((PyObject)var10006).__nonzero__()) {
         var10006 = PyString.fromInterned("&nbsp;");
      }

      var10003[4] = (PyObject)var10006;
      var10001.<init>(var10003);
      PyObject var3 = var10000._mod(var10001);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject section$39(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyString.fromInterned("Format a section with a heading.");
      var1.setline(450);
      PyObject var3 = var1.getlocal(7);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(451);
         var3 = PyString.fromInterned("<tt>")._add(PyString.fromInterned("&nbsp;")._mul(var1.getlocal(5)))._add(PyString.fromInterned("</tt>"));
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(452);
      var3 = PyString.fromInterned("<p>\n<table width=\"100%%\" cellspacing=0 cellpadding=2 border=0 summary=\"section\">\n<tr bgcolor=\"%s\">\n<td colspan=3 valign=bottom>&nbsp;<br>\n<font color=\"%s\" face=\"helvetica, arial\">%s</font></td></tr>\n    ")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(1)}));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(458);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(459);
         var3 = var1.getlocal(9)._add(PyString.fromInterned("\n<tr bgcolor=\"%s\"><td rowspan=2>%s</td>\n<td colspan=2>%s</td></tr>\n<tr><td>%s</td>")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(7), var1.getlocal(6), var1.getlocal(8)})));
         var1.setlocal(9, var3);
         var3 = null;
      } else {
         var1.setline(464);
         var3 = var1.getlocal(9)._add(PyString.fromInterned("\n<tr><td bgcolor=\"%s\">%s</td><td>%s</td>")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(7), var1.getlocal(8)})));
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(467);
      var3 = var1.getlocal(9)._add(PyString.fromInterned("\n<td width=\"100%%\">%s</td></tr></table>")._mod(var1.getlocal(4)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bigsection$40(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("Format a section with a big heading.");
      var1.setline(471);
      PyObject var3 = PyString.fromInterned("<big><strong>%s</strong></big>")._mod(var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(472);
      PyObject var10000 = var1.getlocal(0).__getattr__("section");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject preformat$41(PyFrame var1, ThreadState var2) {
      var1.setline(475);
      PyString.fromInterned("Format literal preformatted text.");
      var1.setline(476);
      PyObject var3 = var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getglobal("expandtabs").__call__(var2, var1.getlocal(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(477);
      PyObject var10000 = var1.getglobal("replace");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("\n\n"), PyString.fromInterned("\n \n"), PyString.fromInterned("\n\n"), PyString.fromInterned("\n \n"), PyString.fromInterned(" "), PyString.fromInterned("&nbsp;"), PyString.fromInterned("\n"), PyString.fromInterned("<br>\n")};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject multicolumn$42(PyFrame var1, ThreadState var2) {
      var1.setline(481);
      PyString.fromInterned("Format a list of items into a multi-column list.");
      var1.setline(482);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(483);
      PyObject var8 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._add(var1.getlocal(3))._sub(Py.newInteger(1))._floordiv(var1.getlocal(3));
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(484);
      var8 = var1.getglobal("range").__call__(var2, var1.getlocal(3)).__iter__();

      while(true) {
         var1.setline(484);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(490);
            var8 = PyString.fromInterned("<table width=\"100%%\" summary=\"list\"><tr>%s</tr></table>")._mod(var1.getlocal(4));
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(6, var4);
         var1.setline(485);
         PyObject var5 = var1.getlocal(4)._add(PyString.fromInterned("<td width=\"%d%%\" valign=top>")._mod(Py.newInteger(100)._floordiv(var1.getlocal(3))));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(486);
         var5 = var1.getglobal("range").__call__(var2, var1.getlocal(5)._mul(var1.getlocal(6)), var1.getlocal(5)._mul(var1.getlocal(6))._add(var1.getlocal(5))).__iter__();

         while(true) {
            var1.setline(486);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(489);
               var5 = var1.getlocal(4)._add(PyString.fromInterned("</td>"));
               var1.setlocal(4, var5);
               var5 = null;
               break;
            }

            var1.setlocal(7, var6);
            var1.setline(487);
            PyObject var7 = var1.getlocal(7);
            PyObject var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(488);
               var7 = var1.getlocal(4)._add(var1.getlocal(2).__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(7))))._add(PyString.fromInterned("<br>\n"));
               var1.setlocal(4, var7);
               var7 = null;
            }
         }
      }
   }

   public PyObject grey$43(PyFrame var1, ThreadState var2) {
      var1.setline(492);
      PyObject var3 = PyString.fromInterned("<font color=\"#909090\">%s</font>")._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject namelink$44(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyString.fromInterned("Make a link for an identifier, given name-to-URL mappings.");
      var1.setline(496);
      PyObject var3 = var1.getlocal(2).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(496);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(499);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(497);
         var5 = var1.getlocal(1);
         var10000 = var5._in(var1.getlocal(3));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(498);
      var5 = PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(var1.getlocal(1)), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject classlink$45(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      PyString.fromInterned("Make a link for a class.");
      var1.setline(503);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("__name__"), var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("__module__"))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(504);
      PyObject var10000 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(4), var1.getlocal(3));
      PyObject var6;
      if (var10000.__nonzero__()) {
         var6 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(3));
         var10000 = var6._is(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(505);
         var6 = PyString.fromInterned("<a href=\"%s.html#%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("__name__"), var1.getlocal(3), var1.getglobal("classname").__call__(var2, var1.getlocal(1), var1.getlocal(2))}));
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(507);
         var6 = var1.getglobal("classname").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject modulelink$46(PyFrame var1, ThreadState var2) {
      var1.setline(510);
      PyString.fromInterned("Make a link for a module.");
      var1.setline(511);
      PyObject var3 = PyString.fromInterned("<a href=\"%s.html\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("__name__"), var1.getlocal(1).__getattr__("__name__")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject modpkglink$47(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyString.fromInterned("Make a link for a module or package to display in an index.");
      var1.setline(515);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(516);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(517);
         var3 = var1.getlocal(0).__getattr__("grey").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(518);
         PyObject var6;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(519);
            var6 = PyString.fromInterned("%s.%s.html")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)}));
            var1.setlocal(6, var6);
            var4 = null;
         } else {
            var1.setline(521);
            var6 = PyString.fromInterned("%s.html")._mod(var1.getlocal(2));
            var1.setlocal(6, var6);
            var4 = null;
         }

         var1.setline(522);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(523);
            var6 = PyString.fromInterned("<strong>%s</strong>&nbsp;(package)")._mod(var1.getlocal(2));
            var1.setlocal(7, var6);
            var4 = null;
         } else {
            var1.setline(525);
            var6 = var1.getlocal(2);
            var1.setlocal(7, var6);
            var4 = null;
         }

         var1.setline(526);
         var3 = PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)}));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject markup$48(PyFrame var1, ThreadState var2) {
      var1.setline(530);
      PyString.fromInterned("Mark up some plain text, given a context of symbols to look for.\n        Each context dictionary maps object names to anchor names.");
      var1.setline(531);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("escape");
      }

      PyObject var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(532);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(533);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(534);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\b((http|ftp)://\\S+[\\w/]|RFC[- ]?(\\d+)|PEP[- ]?(\\d+)|(self\\.)?(\\w+))"));
      var1.setlocal(8, var3);
      var3 = null;

      while(true) {
         var1.setline(538);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(539);
         var3 = var1.getlocal(8).__getattr__("search").__call__(var2, var1.getlocal(1), var1.getlocal(7));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(540);
         if (var1.getlocal(9).__not__().__nonzero__()) {
            break;
         }

         var1.setline(541);
         var3 = var1.getlocal(9).__getattr__("span").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(542);
         var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(7), var1.getlocal(10), (PyObject)null)));
         var1.setline(544);
         var3 = var1.getlocal(9).__getattr__("groups").__call__(var2);
         var4 = Py.unpackSequence(var3, 6);
         var5 = var4[0];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(13, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(14, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(16, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(17, var5);
         var5 = null;
         var3 = null;
         var1.setline(545);
         if (var1.getlocal(13).__nonzero__()) {
            var1.setline(546);
            var3 = var1.getlocal(2).__call__(var2, var1.getlocal(12)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;"));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(547);
            var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(18)})));
         } else {
            var1.setline(548);
            if (var1.getlocal(14).__nonzero__()) {
               var1.setline(549);
               var3 = PyString.fromInterned("http://www.rfc-editor.org/rfc/rfc%d.txt")._mod(var1.getglobal("int").__call__(var2, var1.getlocal(14)));
               var1.setlocal(18, var3);
               var3 = null;
               var1.setline(550);
               var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(2).__call__(var2, var1.getlocal(12))})));
            } else {
               var1.setline(551);
               if (var1.getlocal(15).__nonzero__()) {
                  var1.setline(552);
                  var3 = PyString.fromInterned("http://www.python.org/dev/peps/pep-%04d/")._mod(var1.getglobal("int").__call__(var2, var1.getlocal(15)));
                  var1.setlocal(18, var3);
                  var3 = null;
                  var1.setline(553);
                  var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(2).__call__(var2, var1.getlocal(12))})));
               } else {
                  var1.setline(554);
                  var3 = var1.getlocal(1).__getslice__(var1.getlocal(11), var1.getlocal(11)._add(Py.newInteger(1)), (PyObject)null);
                  var10000 = var3._eq(PyString.fromInterned("("));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(555);
                     var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("namelink").__call__(var2, var1.getlocal(17), var1.getlocal(5), var1.getlocal(3), var1.getlocal(4)));
                  } else {
                     var1.setline(556);
                     if (var1.getlocal(16).__nonzero__()) {
                        var1.setline(557);
                        var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("self.<strong>%s</strong>")._mod(var1.getlocal(17)));
                     } else {
                        var1.setline(559);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("namelink").__call__(var2, var1.getlocal(17), var1.getlocal(4)));
                     }
                  }
               }
            }
         }

         var1.setline(560);
         var3 = var1.getlocal(11);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(561);
      var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null)));
      var1.setline(562);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formattree$49(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyString.fromInterned("Produce HTML for a class tree as given by inspect.getclasstree().");
      var1.setline(568);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(569);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         while(true) {
            var1.setline(569);
            PyObject var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(583);
               var8 = PyString.fromInterned("<dl>\n%s</dl>\n")._mod(var1.getlocal(4));
               var1.f_lasti = -1;
               return var8;
            }

            var1.setlocal(5, var4);
            var1.setline(570);
            PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(5));
            PyObject var10000 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects))));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(571);
               var5 = var1.getlocal(5);
               PyObject[] var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(6, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(7, var7);
               var7 = null;
               var5 = null;
               var1.setline(572);
               var5 = var1.getlocal(4)._add(PyString.fromInterned("<dt><font face=\"helvetica, arial\">"));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(573);
               var5 = var1.getlocal(4)._add(var1.getlocal(0).__getattr__("classlink").__call__(var2, var1.getlocal(6), var1.getlocal(2)));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(574);
               var10000 = var1.getlocal(7);
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(7);
                  var10000 = var5._ne(new PyTuple(new PyObject[]{var1.getlocal(3)}));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(575);
                  PyList var10 = new PyList(Py.EmptyObjects);
                  var1.setlocal(8, var10);
                  var5 = null;
                  var1.setline(576);
                  var5 = var1.getlocal(7).__iter__();

                  while(true) {
                     var1.setline(576);
                     PyObject var9 = var5.__iternext__();
                     if (var9 == null) {
                        var1.setline(578);
                        var5 = var1.getlocal(4)._add(PyString.fromInterned("("))._add(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned(", ")))._add(PyString.fromInterned(")"));
                        var1.setlocal(4, var5);
                        var5 = null;
                        break;
                     }

                     var1.setlocal(9, var9);
                     var1.setline(577);
                     var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("classlink").__call__(var2, var1.getlocal(9), var1.getlocal(2)));
                  }
               }

               var1.setline(579);
               var5 = var1.getlocal(4)._add(PyString.fromInterned("\n</font></dt>"));
               var1.setlocal(4, var5);
               var5 = null;
            } else {
               var1.setline(580);
               var5 = var1.getglobal("type").__call__(var2, var1.getlocal(5));
               var10000 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(581);
                  var5 = var1.getlocal(4)._add(PyString.fromInterned("<dd>\n%s</dd>\n")._mod(var1.getlocal(0).__getattr__("formattree").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(6))));
                  var1.setlocal(4, var5);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject docmodule$50(PyFrame var1, ThreadState var2) {
      var1.setline(586);
      PyString.fromInterned("Produce HTML documentation for a module object.");
      var1.setline(587);
      PyObject var3 = var1.getlocal(1).__getattr__("__name__");
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      PyException var12;
      try {
         var1.setline(589);
         var3 = var1.getlocal(1).__getattr__("__all__");
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var11) {
         var12 = Py.setException(var11, var1);
         if (!var12.match(var1.getglobal("AttributeError"))) {
            throw var12;
         }

         var1.setline(591);
         var4 = var1.getglobal("None");
         var1.setlocal(5, var4);
         var4 = null;
      }

      var1.setline(592);
      var3 = var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("."));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(593);
      PyList var14 = new PyList(Py.EmptyObjects);
      var1.setlocal(7, var14);
      var3 = null;
      var1.setline(594);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(6))._sub(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(594);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(598);
            var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(7)._add(var1.getlocal(6).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null)), (PyObject)PyString.fromInterned("."));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(599);
            var3 = PyString.fromInterned("<big><big><strong>%s</strong></big></big>")._mod(var1.getlocal(9));
            var1.setlocal(10, var3);
            var3 = null;

            try {
               var1.setline(601);
               var3 = var1.getglobal("inspect").__getattr__("getabsfile").__call__(var2, var1.getlocal(1));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(602);
               var3 = var1.getlocal(11);
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(603);
               if (var1.getglobal("_is_windows").__nonzero__()) {
                  var1.setline(604);
                  var3 = imp.importOne("nturl2path", var1, -1);
                  var1.setlocal(13, var3);
                  var3 = null;
                  var1.setline(605);
                  var3 = var1.getlocal(13).__getattr__("pathname2url").__call__(var2, var1.getlocal(11));
                  var1.setlocal(12, var3);
                  var3 = null;
               }

               var1.setline(606);
               var3 = PyString.fromInterned("<a href=\"file:%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(11)}));
               var1.setlocal(14, var3);
               var3 = null;
            } catch (Throwable var10) {
               var12 = Py.setException(var10, var1);
               if (!var12.match(var1.getglobal("TypeError"))) {
                  throw var12;
               }

               var1.setline(608);
               PyString var13 = PyString.fromInterned("(built-in)");
               var1.setlocal(14, var13);
               var4 = null;
            }

            var1.setline(609);
            var14 = new PyList(Py.EmptyObjects);
            var1.setlocal(15, var14);
            var3 = null;
            var1.setline(610);
            PyObject var10000;
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__version__")).__nonzero__()) {
               var1.setline(611);
               var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__version__"));
               var1.setlocal(16, var3);
               var3 = null;
               var1.setline(612);
               var3 = var1.getlocal(16).__getslice__((PyObject)null, Py.newInteger(11), (PyObject)null);
               var10000 = var3._eq(PyString.fromInterned("$")._add(PyString.fromInterned("Revision: ")));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(16).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
                  var10000 = var3._eq(PyString.fromInterned("$"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(613);
                  var3 = var1.getglobal("strip").__call__(var2, var1.getlocal(16).__getslice__(Py.newInteger(11), Py.newInteger(-1), (PyObject)null));
                  var1.setlocal(16, var3);
                  var3 = null;
               }

               var1.setline(614);
               var1.getlocal(15).__getattr__("append").__call__(var2, PyString.fromInterned("version %s")._mod(var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getlocal(16))));
            }

            var1.setline(615);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__date__")).__nonzero__()) {
               var1.setline(616);
               var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__date__"))));
            }

            var1.setline(617);
            if (var1.getlocal(15).__nonzero__()) {
               var1.setline(618);
               var3 = var1.getlocal(10)._add(PyString.fromInterned(" (%s)")._mod(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)PyString.fromInterned(", "))));
               var1.setlocal(10, var3);
               var3 = null;
            }

            var1.setline(619);
            var3 = var1.getlocal(0).__getattr__("getdocloc").__call__(var2, var1.getlocal(1));
            var1.setlocal(17, var3);
            var3 = null;
            var1.setline(620);
            var3 = var1.getlocal(17);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(621);
               var3 = PyString.fromInterned("<br><a href=\"%(docloc)s\">Module Docs</a>")._mod(var1.getglobal("locals").__call__(var2));
               var1.setlocal(17, var3);
               var3 = null;
            } else {
               var1.setline(623);
               PyString var18 = PyString.fromInterned("");
               var1.setlocal(17, var18);
               var3 = null;
            }

            var1.setline(624);
            var3 = var1.getlocal(0).__getattr__("heading").__call__(var2, var1.getlocal(10), PyString.fromInterned("#ffffff"), PyString.fromInterned("#7799ee"), PyString.fromInterned("<a href=\".\">index</a><br>")._add(var1.getlocal(14))._add(var1.getlocal(17)));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(628);
            var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("ismodule"));
            var1.setlocal(19, var3);
            var3 = null;
            var1.setline(630);
            PyTuple var19 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
            PyObject[] var15 = Py.unpackSequence(var19, 2);
            PyObject var5 = var15[0];
            var1.setlocal(20, var5);
            var5 = null;
            var5 = var15[1];
            var1.setlocal(21, var5);
            var5 = null;
            var3 = null;
            var1.setline(631);
            var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("isclass")).__iter__();

            while(true) {
               var1.setline(631);
               var4 = var3.__iternext__();
               PyObject var6;
               PyObject[] var16;
               if (var4 == null) {
                  var1.setline(638);
                  var3 = var1.getlocal(20).__iter__();

                  while(true) {
                     var1.setline(638);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(646);
                        var19 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
                        var15 = Py.unpackSequence(var19, 2);
                        var5 = var15[0];
                        var1.setlocal(27, var5);
                        var5 = null;
                        var5 = var15[1];
                        var1.setlocal(28, var5);
                        var5 = null;
                        var3 = null;
                        var1.setline(647);
                        var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("isroutine")).__iter__();

                        while(true) {
                           var1.setline(647);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(655);
                              var14 = new PyList(Py.EmptyObjects);
                              var1.setlocal(29, var14);
                              var3 = null;
                              var1.setline(656);
                              var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("isdata")).__iter__();

                              while(true) {
                                 var1.setline(656);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(660);
                                    var3 = var1.getlocal(0).__getattr__("markup").__call__(var2, var1.getglobal("getdoc").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("preformat"), var1.getlocal(28), var1.getlocal(21));
                                    var1.setlocal(30, var3);
                                    var3 = null;
                                    var1.setline(661);
                                    var10000 = var1.getlocal(30);
                                    if (var10000.__nonzero__()) {
                                       var10000 = PyString.fromInterned("<tt>%s</tt>")._mod(var1.getlocal(30));
                                    }

                                    var3 = var10000;
                                    var1.setlocal(30, var3);
                                    var3 = null;
                                    var1.setline(662);
                                    var3 = var1.getlocal(18)._add(PyString.fromInterned("<p>%s</p>\n")._mod(var1.getlocal(30)));
                                    var1.setlocal(18, var3);
                                    var3 = null;
                                    var1.setline(664);
                                    PyObject var10002;
                                    PyObject[] var20;
                                    if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__path__")).__nonzero__()) {
                                       var1.setline(665);
                                       var14 = new PyList(Py.EmptyObjects);
                                       var1.setlocal(31, var14);
                                       var3 = null;
                                       var1.setline(666);
                                       var3 = var1.getglobal("pkgutil").__getattr__("iter_modules").__call__(var2, var1.getlocal(1).__getattr__("__path__")).__iter__();

                                       while(true) {
                                          var1.setline(666);
                                          var4 = var3.__iternext__();
                                          if (var4 == null) {
                                             var1.setline(668);
                                             var1.getlocal(31).__getattr__("sort").__call__(var2);
                                             var1.setline(669);
                                             var3 = var1.getlocal(0).__getattr__("multicolumn").__call__(var2, var1.getlocal(31), var1.getlocal(0).__getattr__("modpkglink"));
                                             var1.setlocal(34, var3);
                                             var3 = null;
                                             var1.setline(670);
                                             var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Package Contents"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#aa55cc"), var1.getlocal(34)));
                                             var1.setlocal(18, var3);
                                             var3 = null;
                                             break;
                                          }

                                          var16 = Py.unpackSequence(var4, 3);
                                          var6 = var16[0];
                                          var1.setlocal(32, var6);
                                          var6 = null;
                                          var6 = var16[1];
                                          var1.setlocal(25, var6);
                                          var6 = null;
                                          var6 = var16[2];
                                          var1.setlocal(33, var6);
                                          var6 = null;
                                          var1.setline(667);
                                          var1.getlocal(31).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(25), var1.getlocal(2), var1.getlocal(33), Py.newInteger(0)})));
                                       }
                                    } else {
                                       var1.setline(672);
                                       if (var1.getlocal(19).__nonzero__()) {
                                          var1.setline(673);
                                          var10000 = var1.getlocal(0).__getattr__("multicolumn");
                                          var10002 = var1.getlocal(19);
                                          var1.setline(674);
                                          var20 = new PyObject[]{var1.getlocal(0)};
                                          var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyFunction(var1.f_globals, var20, f$51)));
                                          var1.setlocal(34, var3);
                                          var3 = null;
                                          var1.setline(675);
                                          var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Modules"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#aa55cc"), var1.getlocal(34)));
                                          var1.setlocal(18, var3);
                                          var3 = null;
                                       }
                                    }

                                    var1.setline(678);
                                    if (var1.getlocal(20).__nonzero__()) {
                                       var1.setline(679);
                                       var10000 = var1.getglobal("map");
                                       var1.setline(679);
                                       var20 = Py.EmptyObjects;
                                       var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var20, f$52)), (PyObject)var1.getlocal(20));
                                       var1.setlocal(35, var3);
                                       var3 = null;
                                       var1.setline(680);
                                       var14 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("formattree").__call__(var2, var1.getglobal("inspect").__getattr__("getclasstree").__call__((ThreadState)var2, (PyObject)var1.getlocal(35), (PyObject)Py.newInteger(1)), var1.getlocal(2))});
                                       var1.setlocal(34, var14);
                                       var3 = null;
                                       var1.setline(682);
                                       var3 = var1.getlocal(20).__iter__();

                                       while(true) {
                                          var1.setline(682);
                                          var4 = var3.__iternext__();
                                          if (var4 == null) {
                                             var1.setline(684);
                                             var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Classes"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#ee77aa"), var1.getglobal("join").__call__(var2, var1.getlocal(34))));
                                             var1.setlocal(18, var3);
                                             var3 = null;
                                             break;
                                          }

                                          var16 = Py.unpackSequence(var4, 2);
                                          var6 = var16[0];
                                          var1.setlocal(22, var6);
                                          var6 = null;
                                          var6 = var16[1];
                                          var1.setlocal(23, var6);
                                          var6 = null;
                                          var1.setline(683);
                                          var10000 = var1.getlocal(34).__getattr__("append");
                                          var10002 = var1.getlocal(0).__getattr__("document");
                                          var16 = new PyObject[]{var1.getlocal(23), var1.getlocal(22), var1.getlocal(2), var1.getlocal(28), var1.getlocal(21)};
                                          var10000.__call__(var2, var10002.__call__(var2, var16));
                                       }
                                    }

                                    var1.setline(686);
                                    if (var1.getlocal(27).__nonzero__()) {
                                       var1.setline(687);
                                       var14 = new PyList(Py.EmptyObjects);
                                       var1.setlocal(34, var14);
                                       var3 = null;
                                       var1.setline(688);
                                       var3 = var1.getlocal(27).__iter__();

                                       while(true) {
                                          var1.setline(688);
                                          var4 = var3.__iternext__();
                                          if (var4 == null) {
                                             var1.setline(690);
                                             var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Functions"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#eeaa77"), var1.getglobal("join").__call__(var2, var1.getlocal(34))));
                                             var1.setlocal(18, var3);
                                             var3 = null;
                                             break;
                                          }

                                          var16 = Py.unpackSequence(var4, 2);
                                          var6 = var16[0];
                                          var1.setlocal(22, var6);
                                          var6 = null;
                                          var6 = var16[1];
                                          var1.setlocal(23, var6);
                                          var6 = null;
                                          var1.setline(689);
                                          var10000 = var1.getlocal(34).__getattr__("append");
                                          var10002 = var1.getlocal(0).__getattr__("document");
                                          var16 = new PyObject[]{var1.getlocal(23), var1.getlocal(22), var1.getlocal(2), var1.getlocal(28), var1.getlocal(21)};
                                          var10000.__call__(var2, var10002.__call__(var2, var16));
                                       }
                                    }

                                    var1.setline(692);
                                    if (var1.getlocal(29).__nonzero__()) {
                                       var1.setline(693);
                                       var14 = new PyList(Py.EmptyObjects);
                                       var1.setlocal(34, var14);
                                       var3 = null;
                                       var1.setline(694);
                                       var3 = var1.getlocal(29).__iter__();

                                       while(true) {
                                          var1.setline(694);
                                          var4 = var3.__iternext__();
                                          if (var4 == null) {
                                             var1.setline(696);
                                             var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Data"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#55aa55"), var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(34), (PyObject)PyString.fromInterned("<br>\n"))));
                                             var1.setlocal(18, var3);
                                             var3 = null;
                                             break;
                                          }

                                          var16 = Py.unpackSequence(var4, 2);
                                          var6 = var16[0];
                                          var1.setlocal(22, var6);
                                          var6 = null;
                                          var6 = var16[1];
                                          var1.setlocal(23, var6);
                                          var6 = null;
                                          var1.setline(695);
                                          var1.getlocal(34).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("document").__call__(var2, var1.getlocal(23), var1.getlocal(22)));
                                       }
                                    }

                                    var1.setline(698);
                                    if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__author__")).__nonzero__()) {
                                       var1.setline(699);
                                       var3 = var1.getlocal(0).__getattr__("markup").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__author__")), var1.getlocal(0).__getattr__("preformat"));
                                       var1.setlocal(34, var3);
                                       var3 = null;
                                       var1.setline(700);
                                       var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Author"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#7799ee"), var1.getlocal(34)));
                                       var1.setlocal(18, var3);
                                       var3 = null;
                                    }

                                    var1.setline(702);
                                    if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__credits__")).__nonzero__()) {
                                       var1.setline(703);
                                       var3 = var1.getlocal(0).__getattr__("markup").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__credits__")), var1.getlocal(0).__getattr__("preformat"));
                                       var1.setlocal(34, var3);
                                       var3 = null;
                                       var1.setline(704);
                                       var3 = var1.getlocal(18)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Credits"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#7799ee"), var1.getlocal(34)));
                                       var1.setlocal(18, var3);
                                       var3 = null;
                                    }

                                    var1.setline(707);
                                    var3 = var1.getlocal(18);
                                    var1.f_lasti = -1;
                                    return var3;
                                 }

                                 var16 = Py.unpackSequence(var4, 2);
                                 var6 = var16[0];
                                 var1.setlocal(22, var6);
                                 var6 = null;
                                 var6 = var16[1];
                                 var1.setlocal(23, var6);
                                 var6 = null;
                                 var1.setline(657);
                                 if (var1.getglobal("visiblename").__call__(var2, var1.getlocal(22), var1.getlocal(5), var1.getlocal(1)).__nonzero__()) {
                                    var1.setline(658);
                                    var1.getlocal(29).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(22), var1.getlocal(23)})));
                                 }
                              }
                           }

                           var16 = Py.unpackSequence(var4, 2);
                           var6 = var16[0];
                           var1.setlocal(22, var6);
                           var6 = null;
                           var6 = var16[1];
                           var1.setlocal(23, var6);
                           var6 = null;
                           var1.setline(649);
                           var5 = var1.getlocal(5);
                           var10000 = var5._isnot(var1.getglobal("None"));
                           var5 = null;
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getglobal("inspect").__getattr__("isbuiltin").__call__(var2, var1.getlocal(23));
                              if (!var10000.__nonzero__()) {
                                 var5 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(23));
                                 var10000 = var5._is(var1.getlocal(1));
                                 var5 = null;
                              }
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(651);
                              if (var1.getglobal("visiblename").__call__(var2, var1.getlocal(22), var1.getlocal(5), var1.getlocal(1)).__nonzero__()) {
                                 var1.setline(652);
                                 var1.getlocal(27).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(22), var1.getlocal(23)})));
                                 var1.setline(653);
                                 var5 = PyString.fromInterned("#-")._add(var1.getlocal(22));
                                 var1.getlocal(28).__setitem__(var1.getlocal(22), var5);
                                 var5 = null;
                                 var1.setline(654);
                                 if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(23)).__nonzero__()) {
                                    var1.setline(654);
                                    var5 = var1.getlocal(28).__getitem__(var1.getlocal(22));
                                    var1.getlocal(28).__setitem__(var1.getlocal(23), var5);
                                    var5 = null;
                                 }
                              }
                           }
                        }
                     }

                     var16 = Py.unpackSequence(var4, 2);
                     var6 = var16[0];
                     var1.setlocal(22, var6);
                     var6 = null;
                     var6 = var16[1];
                     var1.setlocal(23, var6);
                     var6 = null;
                     var1.setline(639);
                     var5 = var1.getlocal(23).__getattr__("__bases__").__iter__();

                     while(true) {
                        var1.setline(639);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           break;
                        }

                        var1.setlocal(24, var6);
                        var1.setline(640);
                        PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(24).__getattr__("__name__"), var1.getlocal(24).__getattr__("__module__")});
                        PyObject[] var8 = Py.unpackSequence(var7, 2);
                        PyObject var9 = var8[0];
                        var1.setlocal(22, var9);
                        var9 = null;
                        var9 = var8[1];
                        var1.setlocal(25, var9);
                        var9 = null;
                        var7 = null;
                        var1.setline(641);
                        PyObject var17 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getlocal(25));
                        var1.setlocal(26, var17);
                        var7 = null;
                        var1.setline(642);
                        var17 = var1.getlocal(25);
                        var10000 = var17._ne(var1.getlocal(2));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(26);
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(26), var1.getlocal(22));
                           }
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(643);
                           var17 = var1.getglobal("getattr").__call__(var2, var1.getlocal(26), var1.getlocal(22));
                           var10000 = var17._is(var1.getlocal(24));
                           var7 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(644);
                              var17 = var1.getlocal(22);
                              var10000 = var17._in(var1.getlocal(21));
                              var7 = null;
                              if (var10000.__not__().__nonzero__()) {
                                 var1.setline(645);
                                 var17 = var1.getlocal(25)._add(PyString.fromInterned(".html#"))._add(var1.getlocal(22));
                                 var1.getlocal(21).__setitem__(var1.getlocal(22), var17);
                                 var1.getlocal(21).__setitem__(var1.getlocal(24), var17);
                              }
                           }
                        }
                     }
                  }
               }

               var16 = Py.unpackSequence(var4, 2);
               var6 = var16[0];
               var1.setlocal(22, var6);
               var6 = null;
               var6 = var16[1];
               var1.setlocal(23, var6);
               var6 = null;
               var1.setline(633);
               var5 = var1.getlocal(5);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(23));
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1);
                  }

                  var5 = var10000;
                  var10000 = var5._is(var1.getlocal(1));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(635);
                  if (var1.getglobal("visiblename").__call__(var2, var1.getlocal(22), var1.getlocal(5), var1.getlocal(1)).__nonzero__()) {
                     var1.setline(636);
                     var1.getlocal(20).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(22), var1.getlocal(23)})));
                     var1.setline(637);
                     var5 = PyString.fromInterned("#")._add(var1.getlocal(22));
                     var1.getlocal(21).__setitem__(var1.getlocal(22), var5);
                     var1.getlocal(21).__setitem__(var1.getlocal(23), var5);
                  }
               }
            }
         }

         var1.setlocal(8, var4);
         var1.setline(595);
         var1.getlocal(7).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s.html\"><font color=\"#ffffff\">%s</font></a>")._mod(new PyTuple(new PyObject[]{var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getslice__((PyObject)null, var1.getlocal(8)._add(Py.newInteger(1)), (PyObject)null), (PyObject)PyString.fromInterned(".")), var1.getlocal(6).__getitem__(var1.getlocal(8))})));
      }
   }

   public PyObject f$51(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      PyObject var3 = var1.getlocal(1).__getattr__("modulelink").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$52(PyFrame var1, ThreadState var2) {
      var1.setline(679);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docclass$53(PyFrame var1, ThreadState var2) {
      var1.to_cell(4, 2);
      var1.to_cell(0, 3);
      var1.to_cell(5, 5);
      var1.to_cell(3, 6);
      var1.to_cell(1, 8);
      var1.setline(711);
      PyString.fromInterned("Produce HTML documentation for a class object.");
      var1.setline(712);
      PyObject var3 = var1.getderef(8).__getattr__("__name__");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(713);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(7);
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(714);
      var3 = var1.getderef(8).__getattr__("__bases__");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(716);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(9, var10);
      var3 = null;
      var1.setline(717);
      var3 = var1.getlocal(9).__getattr__("append");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(720);
      PyObject[] var14 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("HorizontalRule", var14, HorizontalRule$54);
      var1.setlocal(10, var4);
      var4 = null;
      Arrays.fill(var14, (Object)null);
      var1.setline(727);
      var3 = var1.getlocal(10).__call__(var2);
      var1.setderef(7, var3);
      var3 = null;
      var1.setline(730);
      var3 = var1.getglobal("deque").__call__(var2, var1.getglobal("inspect").__getattr__("getmro").__call__(var2, var1.getderef(8)));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(731);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(11));
      var10000 = var3._gt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(732);
         var1.getderef(7).__getattr__("maybe").__call__(var2);
         var1.setline(733);
         var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<dl><dt>Method resolution order:</dt>\n"));
         var1.setline(734);
         var3 = var1.getlocal(11).__iter__();

         while(true) {
            var1.setline(734);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(737);
               var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</dl>\n"));
               break;
            }

            var1.setlocal(12, var4);
            var1.setline(735);
            var1.getderef(0).__call__(var2, PyString.fromInterned("<dd>%s</dd>\n")._mod(var1.getderef(3).__getattr__("classlink").__call__(var2, var1.getlocal(12), var1.getderef(8).__getattr__("__module__"))));
         }
      }

      var1.setline(739);
      var14 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var14;
      PyCode var10004 = spill$57;
      var14 = new PyObject[]{var1.getclosure(7), var1.getclosure(0), var1.getclosure(8), var1.getclosure(3), var1.getclosure(6), var1.getclosure(2), var1.getclosure(5), var1.getclosure(1)};
      PyFunction var16 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var14);
      var1.setlocal(13, var16);
      var3 = null;
      var1.setline(757);
      var14 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var14;
      var10004 = spilldescriptors$58;
      var14 = new PyObject[]{var1.getclosure(7), var1.getclosure(0), var1.getclosure(3), var1.getclosure(6)};
      var16 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var14);
      var1.setlocal(14, var16);
      var3 = null;
      var1.setline(766);
      var14 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var14;
      var10004 = spilldata$59;
      var14 = new PyObject[]{var1.getclosure(7), var1.getclosure(0), var1.getclosure(3), var1.getclosure(8), var1.getclosure(6), var1.getclosure(2), var1.getclosure(5), var1.getclosure(1)};
      var16 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var14);
      var1.setlocal(15, var16);
      var3 = null;
      var1.setline(788);
      var10000 = var1.getglobal("filter");
      var1.setline(788);
      var14 = Py.EmptyObjects;
      PyObject[] var10005 = var14;
      PyObject var21 = var1.f_globals;
      PyCode var10006 = f$60;
      var14 = new PyObject[]{var1.getclosure(8)};
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var21, var10005, var10006, var14)), (PyObject)var1.getglobal("classify_class_attrs").__call__(var2, var1.getderef(8)));
      var1.setlocal(16, var3);
      var3 = null;
      var1.setline(790);
      PyDictionary var17 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(1, var17);
      var3 = null;
      var1.setline(791);
      var3 = var1.getlocal(16).__iter__();

      while(true) {
         var1.setline(791);
         var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var11;
         if (var4 == null) {
            while(true) {
               var1.setline(806);
               if (!var1.getlocal(16).__nonzero__()) {
                  var1.setline(843);
                  var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(9));
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(845);
                  var3 = var1.getlocal(2);
                  var10000 = var3._eq(var1.getlocal(7));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(846);
                     var3 = PyString.fromInterned("<a name=\"%s\">class <strong>%s</strong></a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(7)}));
                     var1.setlocal(24, var3);
                     var3 = null;
                  } else {
                     var1.setline(849);
                     var3 = PyString.fromInterned("<strong>%s</strong> = <a name=\"%s\">class %s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(2), var1.getlocal(7)}));
                     var1.setlocal(24, var3);
                     var3 = null;
                  }

                  var1.setline(851);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(852);
                     var10 = new PyList(Py.EmptyObjects);
                     var1.setlocal(25, var10);
                     var3 = null;
                     var1.setline(853);
                     var3 = var1.getlocal(8).__iter__();

                     while(true) {
                        var1.setline(853);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.setline(855);
                           var3 = var1.getlocal(24)._add(PyString.fromInterned("(%s)")._mod(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(25), (PyObject)PyString.fromInterned(", "))));
                           var1.setlocal(24, var3);
                           var3 = null;
                           break;
                        }

                        var1.setlocal(12, var4);
                        var1.setline(854);
                        var1.getlocal(25).__getattr__("append").__call__(var2, var1.getderef(3).__getattr__("classlink").__call__(var2, var1.getlocal(12), var1.getderef(8).__getattr__("__module__")));
                     }
                  }

                  var1.setline(856);
                  var10000 = var1.getderef(3).__getattr__("markup");
                  var14 = new PyObject[]{var1.getglobal("getdoc").__call__(var2, var1.getderef(8)), var1.getderef(3).__getattr__("preformat"), var1.getderef(2), var1.getderef(5), var1.getderef(1)};
                  var3 = var10000.__call__(var2, var14);
                  var1.setlocal(26, var3);
                  var3 = null;
                  var1.setline(857);
                  var10000 = var1.getlocal(26);
                  if (var10000.__nonzero__()) {
                     var10000 = PyString.fromInterned("<tt>%s<br>&nbsp;</tt>")._mod(var1.getlocal(26));
                  }

                  var3 = var10000;
                  var1.setlocal(26, var3);
                  var3 = null;
                  var1.setline(859);
                  var10000 = var1.getderef(3).__getattr__("section");
                  var14 = new PyObject[]{var1.getlocal(24), PyString.fromInterned("#000000"), PyString.fromInterned("#ffc8d8"), var1.getlocal(9), Py.newInteger(3), var1.getlocal(26)};
                  var3 = var10000.__call__(var2, var14);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(807);
               if (var1.getlocal(11).__nonzero__()) {
                  var1.setline(808);
                  var3 = var1.getlocal(11).__getattr__("popleft").__call__(var2);
                  var1.setderef(4, var3);
                  var3 = null;
               } else {
                  var1.setline(810);
                  var3 = var1.getlocal(16).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2));
                  var1.setderef(4, var3);
                  var3 = null;
               }

               var1.setline(811);
               var10000 = var1.getglobal("_split_list");
               var10002 = var1.getlocal(16);
               var1.setline(811);
               var14 = Py.EmptyObjects;
               PyObject[] var23 = var14;
               PyObject var22 = var1.f_globals;
               PyCode var10007 = f$61;
               var14 = new PyObject[]{var1.getclosure(4)};
               var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyFunction(var22, var23, var10007, var14)));
               PyObject[] var12 = Py.unpackSequence(var3, 2);
               var11 = var12[0];
               var1.setlocal(16, var11);
               var5 = null;
               var11 = var12[1];
               var1.setlocal(22, var11);
               var5 = null;
               var3 = null;
               var1.setline(813);
               var3 = var1.getderef(4);
               var10000 = var3._is(var1.getglobal("__builtin__").__getattr__("object"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(814);
                  var3 = var1.getlocal(22);
                  var1.setlocal(16, var3);
                  var3 = null;
               } else {
                  var1.setline(816);
                  var3 = var1.getderef(4);
                  var10000 = var3._is(var1.getderef(8));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(817);
                     PyString var18 = PyString.fromInterned("defined here");
                     var1.setlocal(23, var18);
                     var3 = null;
                  } else {
                     var1.setline(819);
                     var3 = PyString.fromInterned("inherited from %s")._mod(var1.getderef(3).__getattr__("classlink").__call__(var2, var1.getderef(4), var1.getderef(8).__getattr__("__module__")));
                     var1.setlocal(23, var3);
                     var3 = null;
                  }

                  var1.setline(821);
                  var3 = var1.getlocal(23);
                  var3 = var3._iadd(PyString.fromInterned(":<br>\n"));
                  var1.setlocal(23, var3);

                  try {
                     var1.setline(825);
                     var10000 = var1.getlocal(16).__getattr__("sort");
                     var14 = new PyObject[1];
                     var1.setline(825);
                     var12 = Py.EmptyObjects;
                     var14[0] = new PyFunction(var1.f_globals, var12, f$62);
                     String[] var15 = new String[]{"key"};
                     var10000.__call__(var2, var14, var15);
                     var3 = null;
                  } catch (Throwable var7) {
                     PyException var19 = Py.setException(var7, var1);
                     if (!var19.match(var1.getglobal("TypeError"))) {
                        throw var19;
                     }

                     var1.setline(827);
                     var10000 = var1.getlocal(16).__getattr__("sort");
                     var1.setline(827);
                     var12 = Py.EmptyObjects;
                     var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var12, f$63)));
                  }

                  var1.setline(830);
                  var10000 = var1.getlocal(13);
                  var10002 = PyString.fromInterned("Methods %s")._mod(var1.getlocal(23));
                  PyObject var20 = var1.getlocal(16);
                  var1.setline(831);
                  var14 = Py.EmptyObjects;
                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var20, (PyObject)(new PyFunction(var1.f_globals, var14, f$64)));
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(832);
                  var10000 = var1.getlocal(13);
                  var10002 = PyString.fromInterned("Class methods %s")._mod(var1.getlocal(23));
                  var20 = var1.getlocal(16);
                  var1.setline(833);
                  var14 = Py.EmptyObjects;
                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var20, (PyObject)(new PyFunction(var1.f_globals, var14, f$65)));
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(834);
                  var10000 = var1.getlocal(13);
                  var10002 = PyString.fromInterned("Static methods %s")._mod(var1.getlocal(23));
                  var20 = var1.getlocal(16);
                  var1.setline(835);
                  var14 = Py.EmptyObjects;
                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var20, (PyObject)(new PyFunction(var1.f_globals, var14, f$66)));
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(836);
                  var10000 = var1.getlocal(14);
                  var10002 = PyString.fromInterned("Data descriptors %s")._mod(var1.getlocal(23));
                  var20 = var1.getlocal(16);
                  var1.setline(837);
                  var14 = Py.EmptyObjects;
                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var20, (PyObject)(new PyFunction(var1.f_globals, var14, f$67)));
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(838);
                  var10000 = var1.getlocal(15);
                  var10002 = PyString.fromInterned("Data and other attributes %s")._mod(var1.getlocal(23));
                  var20 = var1.getlocal(16);
                  var1.setline(839);
                  var14 = Py.EmptyObjects;
                  var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var20, (PyObject)(new PyFunction(var1.f_globals, var14, f$68)));
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(840);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var3 = var1.getlocal(16);
                     var10000 = var3._eq(new PyList(Py.EmptyObjects));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(841);
                  var3 = var1.getlocal(22);
                  var1.setlocal(16, var3);
                  var3 = null;
               }
            }
         }

         var5 = Py.unpackSequence(var4, 4);
         PyObject var6 = var5[0];
         var1.setlocal(17, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(18, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(19, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(20, var6);
         var6 = null;
         var1.setline(792);
         var11 = PyString.fromInterned("#")._add(var1.getlocal(2))._add(PyString.fromInterned("-"))._add(var1.getlocal(17));
         var1.getderef(1).__setitem__(var1.getlocal(17), var11);
         var1.setlocal(21, var11);

         PyException var13;
         try {
            var1.setline(794);
            var11 = var1.getglobal("getattr").__call__(var2, var1.getderef(8), var1.getlocal(2));
            var1.setlocal(20, var11);
            var5 = null;
         } catch (Throwable var9) {
            var13 = Py.setException(var9, var1);
            if (!var13.match(var1.getglobal("Exception"))) {
               throw var13;
            }

            var1.setline(798);
         }

         try {
            var1.setline(802);
            var11 = var1.getlocal(21);
            var1.getderef(1).__setitem__(var1.getlocal(20), var11);
            var5 = null;
         } catch (Throwable var8) {
            var13 = Py.setException(var8, var1);
            if (!var13.match(var1.getglobal("TypeError"))) {
               throw var13;
            }

            var1.setline(804);
         }
      }
   }

   public PyObject HorizontalRule$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(721);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$55, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(723);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = maybe$56;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("maybe", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$55(PyFrame var1, ThreadState var2) {
      var1.setline(722);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"needone", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject maybe$56(PyFrame var1, ThreadState var2) {
      var1.setline(724);
      if (var1.getlocal(0).__getattr__("needone").__nonzero__()) {
         var1.setline(725);
         var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<hr>\n"));
      }

      var1.setline(726);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"needone", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject spill$57(PyFrame var1, ThreadState var2) {
      var1.setline(740);
      PyObject var3 = var1.getglobal("_split_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(741);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(742);
         var1.getderef(0).__getattr__("maybe").__call__(var2);
         var1.setline(743);
         var1.getderef(1).__call__(var2, var1.getlocal(0));
         var1.setline(744);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(744);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               break;
            }

            PyObject[] var9 = Py.unpackSequence(var8, 4);
            PyObject var6 = var9[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var9[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var9[3];
            var1.setlocal(7, var6);
            var6 = null;

            label30: {
               try {
                  var1.setline(746);
                  var5 = var1.getglobal("getattr").__call__(var2, var1.getderef(2), var1.getlocal(4));
                  var1.setlocal(7, var5);
                  var5 = null;
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (var10.match(var1.getglobal("Exception"))) {
                     var1.setline(750);
                     var1.getderef(1).__call__(var2, var1.getderef(3).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(4), var1.getlocal(7), var1.getderef(4)));
                     break label30;
                  }

                  throw var10;
               }

               var1.setline(752);
               PyObject var10000 = var1.getderef(1);
               PyObject var10002 = var1.getderef(3).__getattr__("document");
               PyObject[] var11 = new PyObject[]{var1.getlocal(7), var1.getlocal(4), var1.getderef(4), var1.getderef(5), var1.getderef(6), var1.getderef(7), var1.getderef(2)};
               var10000.__call__(var2, var10002.__call__(var2, var11));
            }

            var1.setline(754);
            var1.getderef(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         }
      }

      var1.setline(755);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spilldescriptors$58(PyFrame var1, ThreadState var2) {
      var1.setline(758);
      PyObject var3 = var1.getglobal("_split_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(759);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(760);
         var1.getderef(0).__getattr__("maybe").__call__(var2);
         var1.setline(761);
         var1.getderef(1).__call__(var2, var1.getlocal(0));
         var1.setline(762);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(762);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               break;
            }

            PyObject[] var8 = Py.unpackSequence(var7, 4);
            PyObject var6 = var8[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var8[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var8[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var8[3];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(763);
            var1.getderef(1).__call__(var2, var1.getderef(2).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(4), var1.getlocal(7), var1.getderef(3)));
         }
      }

      var1.setline(764);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spilldata$59(PyFrame var1, ThreadState var2) {
      var1.setline(767);
      PyObject var3 = var1.getglobal("_split_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(768);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(769);
         var1.getderef(0).__getattr__("maybe").__call__(var2);
         var1.setline(770);
         var1.getderef(1).__call__(var2, var1.getlocal(0));
         var1.setline(771);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(771);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               break;
            }

            PyObject[] var8 = Py.unpackSequence(var7, 4);
            PyObject var6 = var8[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var8[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var8[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var8[3];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(772);
            var5 = var1.getderef(2).__getattr__("docother").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getderef(3), var1.getlocal(4)), var1.getlocal(4), var1.getderef(4));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(773);
            PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("__call__"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("inspect").__getattr__("isdatadescriptor").__call__(var2, var1.getlocal(7));
            }

            if (var10000.__nonzero__()) {
               var1.setline(775);
               var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("__doc__"), (PyObject)var1.getglobal("None"));
               var1.setlocal(9, var5);
               var5 = null;
            } else {
               var1.setline(777);
               var5 = var1.getglobal("None");
               var1.setlocal(9, var5);
               var5 = null;
            }

            var1.setline(778);
            var5 = var1.getlocal(9);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(779);
               var1.getderef(1).__call__(var2, PyString.fromInterned("<dl><dt>%s</dl>\n")._mod(var1.getlocal(8)));
            } else {
               var1.setline(781);
               var10000 = var1.getderef(2).__getattr__("markup");
               var8 = new PyObject[]{var1.getglobal("getdoc").__call__(var2, var1.getlocal(7)), var1.getderef(2).__getattr__("preformat"), var1.getderef(5), var1.getderef(6), var1.getderef(7)};
               var5 = var10000.__call__(var2, var8);
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(783);
               var5 = PyString.fromInterned("<dd><tt>%s</tt>")._mod(var1.getlocal(9));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(784);
               var1.getderef(1).__call__(var2, PyString.fromInterned("<dl><dt>%s%s</dl>\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)})));
            }

            var1.setline(785);
            var1.getderef(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         }
      }

      var1.setline(786);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$60(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyObject var10000 = var1.getglobal("visiblename");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getderef(0)};
      String[] var4 = new String[]{"obj"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$61(PyFrame var1, ThreadState var2) {
      var1.setline(811);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
      PyObject var10000 = var3._is(var1.getderef(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$62(PyFrame var1, ThreadState var2) {
      var1.setline(825);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$63(PyFrame var1, ThreadState var2) {
      var1.setline(827);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$64(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("method"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$65(PyFrame var1, ThreadState var2) {
      var1.setline(833);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("class method"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$66(PyFrame var1, ThreadState var2) {
      var1.setline(835);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("static method"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$67(PyFrame var1, ThreadState var2) {
      var1.setline(837);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("data descriptor"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$68(PyFrame var1, ThreadState var2) {
      var1.setline(839);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatvalue$69(PyFrame var1, ThreadState var2) {
      var1.setline(862);
      PyString.fromInterned("Format an argument default value as text.");
      var1.setline(863);
      PyObject var3 = var1.getlocal(0).__getattr__("grey").__call__(var2, PyString.fromInterned("=")._add(var1.getlocal(0).__getattr__("repr").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docroutine$70(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      PyString.fromInterned("Produce HTML documentation for a function or method object.");
      var1.setline(868);
      PyObject var3 = var1.getlocal(1).__getattr__("__name__");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(869);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8);
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(870);
      Object var11 = var1.getlocal(7);
      if (((PyObject)var11).__nonzero__()) {
         var11 = var1.getlocal(7).__getattr__("__name__");
      }

      if (!((PyObject)var11).__nonzero__()) {
         var11 = PyString.fromInterned("");
      }

      var3 = ((PyObject)var11)._add(PyString.fromInterned("-"))._add(var1.getlocal(2));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(871);
      PyString var7 = PyString.fromInterned("");
      var1.setlocal(10, var7);
      var3 = null;
      var1.setline(872);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(11, var9);
      var3 = null;
      var1.setline(873);
      if (var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(874);
         var3 = var1.getlocal(1).__getattr__("im_class");
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(875);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(876);
            var3 = var1.getlocal(12);
            var10000 = var3._isnot(var1.getlocal(7));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(877);
               var3 = PyString.fromInterned(" from ")._add(var1.getlocal(0).__getattr__("classlink").__call__(var2, var1.getlocal(12), var1.getlocal(3)));
               var1.setlocal(10, var3);
               var3 = null;
            }
         } else {
            var1.setline(879);
            var3 = var1.getlocal(1).__getattr__("im_self");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(880);
               var3 = PyString.fromInterned(" method of %s instance")._mod(var1.getlocal(0).__getattr__("classlink").__call__(var2, var1.getlocal(1).__getattr__("im_self").__getattr__("__class__"), var1.getlocal(3)));
               var1.setlocal(10, var3);
               var3 = null;
            } else {
               var1.setline(883);
               var3 = PyString.fromInterned(" unbound %s method")._mod(var1.getlocal(0).__getattr__("classlink").__call__(var2, var1.getlocal(12), var1.getlocal(3)));
               var1.setlocal(10, var3);
               var3 = null;
            }
         }

         var1.setline(884);
         var3 = var1.getlocal(1).__getattr__("im_func");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(886);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(var1.getlocal(8));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(887);
         var3 = PyString.fromInterned("<a name=\"%s\"><strong>%s</strong></a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(8)}));
         var1.setlocal(13, var3);
         var3 = null;
      } else {
         var1.setline(889);
         var10000 = var1.getlocal(7);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(8);
            var10000 = var3._in(var1.getlocal(7).__getattr__("__dict__"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(7).__getattr__("__dict__").__getitem__(var1.getlocal(8));
               var10000 = var3._is(var1.getlocal(1));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(891);
            var3 = PyString.fromInterned("<a href=\"#%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(7).__getattr__("__name__")._add(PyString.fromInterned("-"))._add(var1.getlocal(8)), var1.getlocal(8)}));
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(893);
            var9 = Py.newInteger(1);
            var1.setlocal(11, var9);
            var3 = null;
         } else {
            var1.setline(895);
            var3 = var1.getlocal(8);
            var1.setlocal(14, var3);
            var3 = null;
         }

         var1.setline(896);
         var3 = PyString.fromInterned("<a name=\"%s\"><strong>%s</strong></a> = %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(2), var1.getlocal(14)}));
         var1.setlocal(13, var3);
         var3 = null;
      }

      var1.setline(898);
      PyObject[] var4;
      if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(899);
         var3 = var1.getglobal("inspect").__getattr__("getargspec").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 4);
         PyObject var5 = var4[0];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(16, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(17, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(18, var5);
         var5 = null;
         var3 = null;
         var1.setline(900);
         var10000 = var1.getglobal("inspect").__getattr__("formatargspec");
         PyObject[] var10 = new PyObject[]{var1.getlocal(15), var1.getlocal(16), var1.getlocal(17), var1.getlocal(18), var1.getlocal(0).__getattr__("formatvalue")};
         String[] var6 = new String[]{"formatvalue"};
         var10000 = var10000.__call__(var2, var10, var6);
         var3 = null;
         var3 = var10000;
         var1.setlocal(19, var3);
         var3 = null;
         var1.setline(902);
         var3 = var1.getlocal(8);
         var10000 = var3._eq(PyString.fromInterned("<lambda>"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(903);
            var3 = PyString.fromInterned("<strong>%s</strong> <em>lambda</em> ")._mod(var1.getlocal(2));
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(904);
            var3 = var1.getlocal(19).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(19, var3);
            var3 = null;
         }
      } else {
         var1.setline(906);
         var7 = PyString.fromInterned("(...)");
         var1.setlocal(19, var7);
         var3 = null;
      }

      var1.setline(908);
      var10000 = var1.getlocal(13)._add(var1.getlocal(19));
      PyObject var10001 = var1.getlocal(10);
      if (var10001.__nonzero__()) {
         var10001 = var1.getlocal(0).__getattr__("grey").__call__(var2, PyString.fromInterned("<font face=\"helvetica, arial\">%s</font>")._mod(var1.getlocal(10)));
      }

      var3 = var10000._add(var10001);
      var1.setlocal(20, var3);
      var3 = null;
      var1.setline(911);
      if (var1.getlocal(11).__nonzero__()) {
         var1.setline(912);
         var3 = PyString.fromInterned("<dl><dt>%s</dt></dl>\n")._mod(var1.getlocal(20));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(914);
         var10000 = var1.getlocal(0).__getattr__("markup");
         var4 = new PyObject[]{var1.getglobal("getdoc").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("preformat"), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         PyObject var8 = var10000.__call__(var2, var4);
         var1.setlocal(21, var8);
         var4 = null;
         var1.setline(916);
         var10000 = var1.getlocal(21);
         if (var10000.__nonzero__()) {
            var10000 = PyString.fromInterned("<dd><tt>%s</tt></dd>")._mod(var1.getlocal(21));
         }

         var8 = var10000;
         var1.setlocal(21, var8);
         var4 = null;
         var1.setline(917);
         var3 = PyString.fromInterned("<dl><dt>%s</dt>%s</dl>\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(20), var1.getlocal(21)}));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _docdescriptor$71(PyFrame var1, ThreadState var2) {
      var1.setline(920);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(921);
      PyObject var4 = var1.getlocal(4).__getattr__("append");
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(923);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(924);
         var1.getlocal(5).__call__(var2, PyString.fromInterned("<dl><dt><strong>%s</strong></dt>\n")._mod(var1.getlocal(1)));
      }

      var1.setline(925);
      var4 = var1.getlocal(2).__getattr__("__doc__");
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(926);
         var4 = var1.getlocal(0).__getattr__("markup").__call__(var2, var1.getglobal("getdoc").__call__(var2, var1.getlocal(2)), var1.getlocal(0).__getattr__("preformat"));
         var1.setlocal(6, var4);
         var3 = null;
         var1.setline(927);
         var1.getlocal(5).__call__(var2, PyString.fromInterned("<dd><tt>%s</tt></dd>\n")._mod(var1.getlocal(6)));
      }

      var1.setline(928);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</dl>\n"));
      var1.setline(930);
      var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject docproperty$72(PyFrame var1, ThreadState var2) {
      var1.setline(933);
      PyString.fromInterned("Produce html documentation for a property.");
      var1.setline(934);
      PyObject var3 = var1.getlocal(0).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docother$73(PyFrame var1, ThreadState var2) {
      var1.setline(937);
      PyString.fromInterned("Produce HTML documentation for a data object.");
      var1.setline(938);
      Object var10000 = var1.getlocal(2);
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("<strong>%s</strong> = ")._mod(var1.getlocal(2));
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      Object var3 = var10000;
      var1.setlocal(5, (PyObject)var3);
      var3 = null;
      var1.setline(939);
      PyObject var4 = var1.getlocal(5)._add(var1.getlocal(0).__getattr__("repr").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject docdata$74(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyString.fromInterned("Produce html documentation for a data descriptor.");
      var1.setline(943);
      PyObject var3 = var1.getlocal(0).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject index$75(PyFrame var1, ThreadState var2) {
      var1.setline(946);
      PyString.fromInterned("Generate an HTML index for a directory of modules.");
      var1.setline(947);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(948);
      PyObject var7 = var1.getlocal(2);
      PyObject var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(948);
         PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var8);
         var3 = null;
      }

      var1.setline(949);
      var7 = var1.getglobal("pkgutil").__getattr__("iter_modules").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)}))).__iter__();

      while(true) {
         var1.setline(949);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(953);
            var1.getlocal(3).__getattr__("sort").__call__(var2);
            var1.setline(954);
            var7 = var1.getlocal(0).__getattr__("multicolumn").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("modpkglink"));
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(955);
            var7 = var1.getlocal(0).__getattr__("bigsection").__call__(var2, var1.getlocal(1), PyString.fromInterned("#ffffff"), PyString.fromInterned("#ee77aa"), var1.getlocal(7));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(950);
         var10000 = var1.getlocal(3).__getattr__("append");
         PyObject[] var10004 = new PyObject[]{var1.getlocal(5), PyString.fromInterned(""), var1.getlocal(6), null};
         PyObject var9 = var1.getlocal(5);
         PyObject var10007 = var9._in(var1.getlocal(2));
         var5 = null;
         var10004[3] = var10007;
         var10000.__call__((ThreadState)var2, (PyObject)(new PyTuple(var10004)));
         var1.setline(951);
         PyInteger var10 = Py.newInteger(1);
         var1.getlocal(2).__setitem__((PyObject)var1.getlocal(5), var10);
         var5 = null;
      }
   }

   public PyObject TextRepr$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for safely making a text representation of a Python object."));
      var1.setline(960);
      PyString.fromInterned("Class for safely making a text representation of a Python object.");
      var1.setline(961);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$77, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(967);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr1$78, (PyObject)null);
      var1.setlocal("repr1", var4);
      var3 = null;
      var1.setline(974);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_string$79, (PyObject)null);
      var1.setlocal("repr_string", var4);
      var3 = null;
      var1.setline(983);
      PyObject var5 = var1.getname("repr_string");
      var1.setlocal("repr_str", var5);
      var3 = null;
      var1.setline(985);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, repr_instance$80, (PyObject)null);
      var1.setlocal("repr_instance", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$77(PyFrame var1, ThreadState var2) {
      var1.setline(962);
      var1.getglobal("Repr").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(963);
      PyInteger var3 = Py.newInteger(20);
      var1.getlocal(0).__setattr__((String)"maxlist", var3);
      var1.getlocal(0).__setattr__((String)"maxtuple", var3);
      var1.setline(964);
      var3 = Py.newInteger(10);
      var1.getlocal(0).__setattr__((String)"maxdict", var3);
      var3 = null;
      var1.setline(965);
      var3 = Py.newInteger(100);
      var1.getlocal(0).__setattr__((String)"maxstring", var3);
      var1.getlocal(0).__setattr__((String)"maxother", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject repr1$78(PyFrame var1, ThreadState var2) {
      var1.setline(968);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("type").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("__name__")).__nonzero__()) {
         var1.setline(969);
         var3 = PyString.fromInterned("repr_")._add(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("split").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__")), (PyObject)PyString.fromInterned("_")));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(970);
         if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(3)).__nonzero__()) {
            var1.setline(971);
            var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3)).__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(972);
      var3 = var1.getglobal("cram").__call__(var2, var1.getglobal("stripid").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1))), var1.getlocal(0).__getattr__("maxother"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repr_string$79(PyFrame var1, ThreadState var2) {
      var1.setline(975);
      PyObject var3 = var1.getglobal("cram").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("maxstring"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(976);
      var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(977);
      PyString var4 = PyString.fromInterned("\\");
      PyObject var10000 = var4._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var4 = PyString.fromInterned("\\");
         var10000 = var4._notin(var1.getglobal("replace").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("\\\\"), (PyObject)PyString.fromInterned("")));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(980);
         var3 = PyString.fromInterned("r")._add(var1.getlocal(4).__getitem__(Py.newInteger(0)))._add(var1.getlocal(3))._add(var1.getlocal(4).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(981);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject repr_instance$80(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(987);
         var3 = var1.getglobal("cram").__call__(var2, var1.getglobal("stripid").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1))), var1.getlocal(0).__getattr__("maxstring"));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(989);
         var3 = PyString.fromInterned("<%s instance>")._mod(var1.getlocal(1).__getattr__("__class__").__getattr__("__name__"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject TextDoc$81(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Formatter class for text documentation."));
      var1.setline(992);
      PyString.fromInterned("Formatter class for text documentation.");
      var1.setline(996);
      PyObject var3 = var1.getname("TextRepr").__call__(var2);
      var1.setlocal("_repr_instance", var3);
      var3 = null;
      var1.setline(997);
      var3 = var1.getname("_repr_instance").__getattr__("repr");
      var1.setlocal("repr", var3);
      var3 = null;
      var1.setline(999);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, bold$82, PyString.fromInterned("Format a string in bold by overstriking."));
      var1.setlocal("bold", var5);
      var3 = null;
      var1.setline(1003);
      var4 = new PyObject[]{PyString.fromInterned("    ")};
      var5 = new PyFunction(var1.f_globals, var4, indent$84, PyString.fromInterned("Indent text by prepending a given prefix to each line."));
      var1.setlocal("indent", var5);
      var3 = null;
      var1.setline(1011);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, section$86, PyString.fromInterned("Format a section with a given heading."));
      var1.setlocal("section", var5);
      var3 = null;
      var1.setline(1017);
      var4 = new PyObject[]{var1.getname("None"), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, formattree$87, PyString.fromInterned("Render in text a class tree as returned by inspect.getclasstree()."));
      var1.setlocal("formattree", var5);
      var3 = null;
      var1.setline(1033);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docmodule$89, PyString.fromInterned("Produce text documentation for a given module object."));
      var1.setlocal("docmodule", var5);
      var3 = null;
      var1.setline(1133);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docclass$91, PyString.fromInterned("Produce text documentation for a given class object."));
      var1.setlocal("docclass", var5);
      var3 = null;
      var1.setline(1253);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, formatvalue$106, PyString.fromInterned("Format an argument default value as text."));
      var1.setlocal("formatvalue", var5);
      var3 = null;
      var1.setline(1257);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docroutine$107, PyString.fromInterned("Produce text documentation for a function or method object."));
      var1.setlocal("docroutine", var5);
      var3 = null;
      var1.setline(1300);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _docdescriptor$108, (PyObject)null);
      var1.setlocal("_docdescriptor", var5);
      var3 = null;
      var1.setline(1313);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docproperty$109, PyString.fromInterned("Produce text documentation for a property."));
      var1.setlocal("docproperty", var5);
      var3 = null;
      var1.setline(1317);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docdata$110, PyString.fromInterned("Produce text documentation for a data descriptor."));
      var1.setlocal("docdata", var5);
      var3 = null;
      var1.setline(1321);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, docother$111, PyString.fromInterned("Produce text documentation for a data object."));
      var1.setlocal("docother", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject bold$82(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      PyString.fromInterned("Format a string in bold by overstriking.");
      var1.setline(1001);
      PyObject var10000 = var1.getglobal("join");
      PyObject var10002 = var1.getglobal("map");
      var1.setline(1001);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$83)), (PyObject)var1.getlocal(1)), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$83(PyFrame var1, ThreadState var2) {
      var1.setline(1001);
      PyObject var3 = var1.getlocal(0)._add(PyString.fromInterned("\b"))._add(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject indent$84(PyFrame var1, ThreadState var2) {
      var1.setline(1004);
      PyString.fromInterned("Indent text by prepending a given prefix to each line.");
      var1.setline(1005);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1005);
         PyString var5 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(1006);
         PyObject var4 = var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1007);
         PyObject var10000 = var1.getglobal("map");
         var1.setline(1007);
         PyObject[] var6 = new PyObject[]{var1.getlocal(2)};
         var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var6, f$85)), (PyObject)var1.getlocal(3));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1008);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1008);
            var4 = var1.getglobal("rstrip").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(-1)));
            var1.getlocal(3).__setitem__((PyObject)Py.newInteger(-1), var4);
            var4 = null;
         }

         var1.setline(1009);
         PyObject var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$85(PyFrame var1, ThreadState var2) {
      var1.setline(1007);
      PyObject var3 = var1.getlocal(1)._add(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject section$86(PyFrame var1, ThreadState var2) {
      var1.setline(1012);
      PyString.fromInterned("Format a section with a given heading.");
      var1.setline(1013);
      PyObject var3 = var1.getlocal(0).__getattr__("bold").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned("\n"))._add(var1.getglobal("rstrip").__call__(var2, var1.getlocal(0).__getattr__("indent").__call__(var2, var1.getlocal(2))))._add(PyString.fromInterned("\n\n"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formattree$87(PyFrame var1, ThreadState var2) {
      var1.setline(1018);
      PyString.fromInterned("Render in text a class tree as returned by inspect.getclasstree().");
      var1.setline(1019);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1020);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1020);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(1031);
            var8 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(6, var4);
         var1.setline(1021);
         PyObject var5 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
         PyObject var10000 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects))));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1022);
            var5 = var1.getlocal(6);
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(8, var7);
            var7 = null;
            var5 = null;
            var1.setline(1023);
            var5 = var1.getlocal(5)._add(var1.getlocal(4))._add(var1.getglobal("classname").__call__(var2, var1.getlocal(7), var1.getlocal(2)));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(1024);
            var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(8);
               var10000 = var5._ne(new PyTuple(new PyObject[]{var1.getlocal(3)}));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1025);
               var10000 = var1.getglobal("map");
               var1.setline(1025);
               PyObject[] var9 = new PyObject[]{var1.getlocal(2)};
               var5 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var9, f$88)), (PyObject)var1.getlocal(8));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(1026);
               var5 = var1.getlocal(5)._add(PyString.fromInterned("(%s)")._mod(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned(", "))));
               var1.setlocal(5, var5);
               var5 = null;
            }

            var1.setline(1027);
            var5 = var1.getlocal(5)._add(PyString.fromInterned("\n"));
            var1.setlocal(5, var5);
            var5 = null;
         } else {
            var1.setline(1028);
            var5 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
            var10000 = var5._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1029);
               var5 = var1.getlocal(5)._add(var1.getlocal(0).__getattr__("formattree").__call__(var2, var1.getlocal(6), var1.getlocal(2), var1.getlocal(7), var1.getlocal(4)._add(PyString.fromInterned("    "))));
               var1.setlocal(5, var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject f$88(PyFrame var1, ThreadState var2) {
      var1.setline(1025);
      PyObject var3 = var1.getglobal("classname").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docmodule$89(PyFrame var1, ThreadState var2) {
      var1.setline(1034);
      PyString.fromInterned("Produce text documentation for a given module object.");
      var1.setline(1035);
      PyObject var3 = var1.getlocal(1).__getattr__("__name__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1036);
      var3 = var1.getglobal("splitdoc").__call__(var2, var1.getglobal("getdoc").__call__(var2, var1.getlocal(1)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1037);
      PyObject var10000 = var1.getlocal(0).__getattr__("section");
      PyString var10002 = PyString.fromInterned("NAME");
      PyObject var10003 = var1.getlocal(2);
      PyObject var10004 = var1.getlocal(4);
      if (var10004.__nonzero__()) {
         var10004 = PyString.fromInterned(" - ")._add(var1.getlocal(4));
      }

      var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003._add(var10004));
      var1.setlocal(6, var3);
      var3 = null;

      PyObject var9;
      PyException var10;
      try {
         var1.setline(1040);
         var3 = var1.getlocal(1).__getattr__("__all__");
         var1.setlocal(7, var3);
         var3 = null;
      } catch (Throwable var8) {
         var10 = Py.setException(var8, var1);
         if (!var10.match(var1.getglobal("AttributeError"))) {
            throw var10;
         }

         var1.setline(1042);
         var9 = var1.getglobal("None");
         var1.setlocal(7, var9);
         var4 = null;
      }

      try {
         var1.setline(1045);
         var3 = var1.getglobal("inspect").__getattr__("getabsfile").__call__(var2, var1.getlocal(1));
         var1.setlocal(8, var3);
         var3 = null;
      } catch (Throwable var7) {
         var10 = Py.setException(var7, var1);
         if (!var10.match(var1.getglobal("TypeError"))) {
            throw var10;
         }

         var1.setline(1047);
         PyString var11 = PyString.fromInterned("(built-in)");
         var1.setlocal(8, var11);
         var4 = null;
      }

      var1.setline(1048);
      var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FILE"), (PyObject)var1.getlocal(8)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1050);
      var3 = var1.getlocal(0).__getattr__("getdocloc").__call__(var2, var1.getlocal(1));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1051);
      var3 = var1.getlocal(9);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1052);
         var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MODULE DOCS"), (PyObject)var1.getlocal(9)));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(1054);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(1055);
         var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DESCRIPTION"), (PyObject)var1.getlocal(5)));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(1057);
      PyList var13 = new PyList(Py.EmptyObjects);
      var1.setlocal(10, var13);
      var3 = null;
      var1.setline(1058);
      var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("isclass")).__iter__();

      while(true) {
         var1.setline(1058);
         var9 = var3.__iternext__();
         PyObject var6;
         PyObject[] var12;
         if (var9 == null) {
            var1.setline(1064);
            var13 = new PyList(Py.EmptyObjects);
            var1.setlocal(13, var13);
            var3 = null;
            var1.setline(1065);
            var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("isroutine")).__iter__();

            while(true) {
               var1.setline(1065);
               var9 = var3.__iternext__();
               if (var9 == null) {
                  var1.setline(1071);
                  var13 = new PyList(Py.EmptyObjects);
                  var1.setlocal(14, var13);
                  var3 = null;
                  var1.setline(1072);
                  var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("isdata")).__iter__();

                  while(true) {
                     var1.setline(1072);
                     var9 = var3.__iternext__();
                     if (var9 == null) {
                        var1.setline(1076);
                        var13 = new PyList(Py.EmptyObjects);
                        var1.setlocal(15, var13);
                        var3 = null;
                        var1.setline(1077);
                        var3 = var1.getglobal("set").__call__(var2);
                        var1.setlocal(16, var3);
                        var3 = null;
                        var1.setline(1078);
                        if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__path__")).__nonzero__()) {
                           var1.setline(1079);
                           var3 = var1.getglobal("pkgutil").__getattr__("iter_modules").__call__(var2, var1.getlocal(1).__getattr__("__path__")).__iter__();

                           while(true) {
                              var1.setline(1079);
                              var9 = var3.__iternext__();
                              if (var9 == null) {
                                 var1.setline(1086);
                                 var1.getlocal(15).__getattr__("sort").__call__(var2);
                                 var1.setline(1087);
                                 var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PACKAGE CONTENTS"), (PyObject)var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)PyString.fromInterned("\n"))));
                                 var1.setlocal(6, var3);
                                 var3 = null;
                                 break;
                              }

                              var12 = Py.unpackSequence(var9, 3);
                              var6 = var12[0];
                              var1.setlocal(17, var6);
                              var6 = null;
                              var6 = var12[1];
                              var1.setlocal(18, var6);
                              var6 = null;
                              var6 = var12[2];
                              var1.setlocal(19, var6);
                              var6 = null;
                              var1.setline(1080);
                              var1.getlocal(16).__getattr__("add").__call__(var2, var1.getlocal(18));
                              var1.setline(1081);
                              if (var1.getlocal(19).__nonzero__()) {
                                 var1.setline(1082);
                                 var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(18)._add(PyString.fromInterned(" (package)")));
                              } else {
                                 var1.setline(1084);
                                 var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(18));
                              }
                           }
                        }

                        var1.setline(1091);
                        var13 = new PyList(Py.EmptyObjects);
                        var1.setlocal(20, var13);
                        var3 = null;
                        var1.setline(1092);
                        var3 = var1.getglobal("inspect").__getattr__("getmembers").__call__(var2, var1.getlocal(1), var1.getglobal("inspect").__getattr__("ismodule")).__iter__();

                        while(true) {
                           var1.setline(1092);
                           var9 = var3.__iternext__();
                           if (var9 == null) {
                              var1.setline(1095);
                              if (var1.getlocal(20).__nonzero__()) {
                                 var1.setline(1096);
                                 var1.getlocal(20).__getattr__("sort").__call__(var2);
                                 var1.setline(1097);
                                 var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SUBMODULES"), (PyObject)var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(20), (PyObject)PyString.fromInterned("\n"))));
                                 var1.setlocal(6, var3);
                                 var3 = null;
                              }

                              var1.setline(1100);
                              if (var1.getlocal(10).__nonzero__()) {
                                 var1.setline(1101);
                                 var10000 = var1.getglobal("map");
                                 var1.setline(1101);
                                 PyObject[] var14 = Py.EmptyObjects;
                                 var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var14, f$90)), (PyObject)var1.getlocal(10));
                                 var1.setlocal(21, var3);
                                 var3 = null;
                                 var1.setline(1102);
                                 var13 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("formattree").__call__(var2, var1.getglobal("inspect").__getattr__("getclasstree").__call__((ThreadState)var2, (PyObject)var1.getlocal(21), (PyObject)Py.newInteger(1)), var1.getlocal(2))});
                                 var1.setlocal(22, var13);
                                 var3 = null;
                                 var1.setline(1104);
                                 var3 = var1.getlocal(10).__iter__();

                                 while(true) {
                                    var1.setline(1104);
                                    var9 = var3.__iternext__();
                                    if (var9 == null) {
                                       var1.setline(1106);
                                       var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CLASSES"), (PyObject)var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(22), (PyObject)PyString.fromInterned("\n"))));
                                       var1.setlocal(6, var3);
                                       var3 = null;
                                       break;
                                    }

                                    var12 = Py.unpackSequence(var9, 2);
                                    var6 = var12[0];
                                    var1.setlocal(11, var6);
                                    var6 = null;
                                    var6 = var12[1];
                                    var1.setlocal(12, var6);
                                    var6 = null;
                                    var1.setline(1105);
                                    var1.getlocal(22).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("document").__call__(var2, var1.getlocal(12), var1.getlocal(11), var1.getlocal(2)));
                                 }
                              }

                              var1.setline(1108);
                              if (var1.getlocal(13).__nonzero__()) {
                                 var1.setline(1109);
                                 var13 = new PyList(Py.EmptyObjects);
                                 var1.setlocal(22, var13);
                                 var3 = null;
                                 var1.setline(1110);
                                 var3 = var1.getlocal(13).__iter__();

                                 while(true) {
                                    var1.setline(1110);
                                    var9 = var3.__iternext__();
                                    if (var9 == null) {
                                       var1.setline(1112);
                                       var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FUNCTIONS"), (PyObject)var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(22), (PyObject)PyString.fromInterned("\n"))));
                                       var1.setlocal(6, var3);
                                       var3 = null;
                                       break;
                                    }

                                    var12 = Py.unpackSequence(var9, 2);
                                    var6 = var12[0];
                                    var1.setlocal(11, var6);
                                    var6 = null;
                                    var6 = var12[1];
                                    var1.setlocal(12, var6);
                                    var6 = null;
                                    var1.setline(1111);
                                    var1.getlocal(22).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("document").__call__(var2, var1.getlocal(12), var1.getlocal(11), var1.getlocal(2)));
                                 }
                              }

                              var1.setline(1114);
                              if (var1.getlocal(14).__nonzero__()) {
                                 var1.setline(1115);
                                 var13 = new PyList(Py.EmptyObjects);
                                 var1.setlocal(22, var13);
                                 var3 = null;
                                 var1.setline(1116);
                                 var3 = var1.getlocal(14).__iter__();

                                 while(true) {
                                    var1.setline(1116);
                                    var9 = var3.__iternext__();
                                    if (var9 == null) {
                                       var1.setline(1118);
                                       var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DATA"), (PyObject)var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(22), (PyObject)PyString.fromInterned("\n"))));
                                       var1.setlocal(6, var3);
                                       var3 = null;
                                       break;
                                    }

                                    var12 = Py.unpackSequence(var9, 2);
                                    var6 = var12[0];
                                    var1.setlocal(11, var6);
                                    var6 = null;
                                    var6 = var12[1];
                                    var1.setlocal(12, var6);
                                    var6 = null;
                                    var1.setline(1117);
                                    var10000 = var1.getlocal(22).__getattr__("append");
                                    PyObject var16 = var1.getlocal(0).__getattr__("docother");
                                    var12 = new PyObject[]{var1.getlocal(12), var1.getlocal(11), var1.getlocal(2), Py.newInteger(70)};
                                    String[] var15 = new String[]{"maxlen"};
                                    var16 = var16.__call__(var2, var12, var15);
                                    var5 = null;
                                    var10000.__call__(var2, var16);
                                 }
                              }

                              var1.setline(1120);
                              if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__version__")).__nonzero__()) {
                                 var1.setline(1121);
                                 var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__version__"));
                                 var1.setlocal(23, var3);
                                 var3 = null;
                                 var1.setline(1122);
                                 var3 = var1.getlocal(23).__getslice__((PyObject)null, Py.newInteger(11), (PyObject)null);
                                 var10000 = var3._eq(PyString.fromInterned("$")._add(PyString.fromInterned("Revision: ")));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var3 = var1.getlocal(23).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
                                    var10000 = var3._eq(PyString.fromInterned("$"));
                                    var3 = null;
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(1123);
                                    var3 = var1.getglobal("strip").__call__(var2, var1.getlocal(23).__getslice__(Py.newInteger(11), Py.newInteger(-1), (PyObject)null));
                                    var1.setlocal(23, var3);
                                    var3 = null;
                                 }

                                 var1.setline(1124);
                                 var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("VERSION"), (PyObject)var1.getlocal(23)));
                                 var1.setlocal(6, var3);
                                 var3 = null;
                              }

                              var1.setline(1125);
                              if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__date__")).__nonzero__()) {
                                 var1.setline(1126);
                                 var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DATE"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__date__"))));
                                 var1.setlocal(6, var3);
                                 var3 = null;
                              }

                              var1.setline(1127);
                              if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__author__")).__nonzero__()) {
                                 var1.setline(1128);
                                 var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AUTHOR"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__author__"))));
                                 var1.setlocal(6, var3);
                                 var3 = null;
                              }

                              var1.setline(1129);
                              if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__credits__")).__nonzero__()) {
                                 var1.setline(1130);
                                 var3 = var1.getlocal(6)._add(var1.getlocal(0).__getattr__("section").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CREDITS"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("__credits__"))));
                                 var1.setlocal(6, var3);
                                 var3 = null;
                              }

                              var1.setline(1131);
                              var3 = var1.getlocal(6);
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var12 = Py.unpackSequence(var9, 2);
                           var6 = var12[0];
                           var1.setlocal(11, var6);
                           var6 = null;
                           var6 = var12[1];
                           var1.setlocal(12, var6);
                           var6 = null;
                           var1.setline(1093);
                           var10000 = var1.getlocal(12).__getattr__("__name__").__getattr__("startswith").__call__(var2, var1.getlocal(2)._add(PyString.fromInterned(".")));
                           if (var10000.__nonzero__()) {
                              var5 = var1.getlocal(11);
                              var10000 = var5._notin(var1.getlocal(16));
                              var5 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(1094);
                              var1.getlocal(20).__getattr__("append").__call__(var2, var1.getlocal(11));
                           }
                        }
                     }

                     var12 = Py.unpackSequence(var9, 2);
                     var6 = var12[0];
                     var1.setlocal(11, var6);
                     var6 = null;
                     var6 = var12[1];
                     var1.setlocal(12, var6);
                     var6 = null;
                     var1.setline(1073);
                     if (var1.getglobal("visiblename").__call__(var2, var1.getlocal(11), var1.getlocal(7), var1.getlocal(1)).__nonzero__()) {
                        var1.setline(1074);
                        var1.getlocal(14).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12)})));
                     }
                  }
               }

               var12 = Py.unpackSequence(var9, 2);
               var6 = var12[0];
               var1.setlocal(11, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(12, var6);
               var6 = null;
               var1.setline(1067);
               var5 = var1.getlocal(7);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("isbuiltin").__call__(var2, var1.getlocal(12));
                  if (!var10000.__nonzero__()) {
                     var5 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(12));
                     var10000 = var5._is(var1.getlocal(1));
                     var5 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1069);
                  if (var1.getglobal("visiblename").__call__(var2, var1.getlocal(11), var1.getlocal(7), var1.getlocal(1)).__nonzero__()) {
                     var1.setline(1070);
                     var1.getlocal(13).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12)})));
                  }
               }
            }
         }

         var12 = Py.unpackSequence(var9, 2);
         var6 = var12[0];
         var1.setlocal(11, var6);
         var6 = null;
         var6 = var12[1];
         var1.setlocal(12, var6);
         var6 = null;
         var1.setline(1060);
         var5 = var1.getlocal(7);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(12));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(1);
            }

            var5 = var10000;
            var10000 = var5._is(var1.getlocal(1));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1062);
            if (var1.getglobal("visiblename").__call__(var2, var1.getlocal(11), var1.getlocal(7), var1.getlocal(1)).__nonzero__()) {
               var1.setline(1063);
               var1.getlocal(10).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12)})));
            }
         }
      }
   }

   public PyObject f$90(PyFrame var1, ThreadState var2) {
      var1.setline(1101);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docclass$91(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.to_cell(3, 2);
      var1.to_cell(0, 4);
      var1.setline(1134);
      PyString.fromInterned("Produce text documentation for a given class object.");
      var1.setline(1135);
      PyObject var3 = var1.getderef(0).__getattr__("__name__");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1136);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(5);
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1137);
      var3 = var1.getderef(0).__getattr__("__bases__");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1139);
      PyObject[] var6 = new PyObject[]{var1.getderef(0).__getattr__("__module__")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, makename$92, (PyObject)null);
      var1.setlocal(7, var8);
      var3 = null;
      var1.setline(1142);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(var1.getlocal(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1143);
         var3 = PyString.fromInterned("class ")._add(var1.getderef(4).__getattr__("bold").__call__(var2, var1.getlocal(5)));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(1145);
         var3 = var1.getderef(4).__getattr__("bold").__call__(var2, var1.getlocal(2))._add(PyString.fromInterned(" = class "))._add(var1.getlocal(5));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1146);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(1147);
         var3 = var1.getglobal("map").__call__(var2, var1.getlocal(7), var1.getlocal(6));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1148);
         var3 = var1.getlocal(8)._add(PyString.fromInterned("(%s)")._mod(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned(", "))));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1150);
      var3 = var1.getglobal("getdoc").__call__(var2, var1.getderef(0));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1151);
      Object var11 = var1.getlocal(10);
      if (((PyObject)var11).__nonzero__()) {
         var11 = new PyList(new PyObject[]{var1.getlocal(10)._add(PyString.fromInterned("\n"))});
      }

      if (!((PyObject)var11).__nonzero__()) {
         var11 = new PyList(Py.EmptyObjects);
      }

      Object var9 = var11;
      var1.setlocal(11, (PyObject)var9);
      var3 = null;
      var1.setline(1152);
      var3 = var1.getlocal(11).__getattr__("append");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(1155);
      var3 = var1.getglobal("deque").__call__(var2, var1.getglobal("inspect").__getattr__("getmro").__call__(var2, var1.getderef(0)));
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(1156);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(12));
      var10000 = var3._gt(Py.newInteger(2));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(1157);
         var1.getderef(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method resolution order:"));
         var1.setline(1158);
         var3 = var1.getlocal(12).__iter__();

         while(true) {
            var1.setline(1158);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1160);
               var1.getderef(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
               break;
            }

            var1.setlocal(13, var4);
            var1.setline(1159);
            var1.getderef(1).__call__(var2, PyString.fromInterned("    ")._add(var1.getlocal(7).__call__(var2, var1.getlocal(13))));
         }
      }

      var1.setline(1163);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("HorizontalRule", var6, HorizontalRule$93);
      var1.setlocal(14, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1170);
      var3 = var1.getlocal(14).__call__(var2);
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(1172);
      var6 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var6;
      PyCode var10004 = spill$96;
      var6 = new PyObject[]{var1.getclosure(3), var1.getclosure(1), var1.getclosure(0), var1.getclosure(4), var1.getclosure(2)};
      var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(15, var8);
      var3 = null;
      var1.setline(1189);
      var6 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var6;
      var10004 = spilldescriptors$97;
      var6 = new PyObject[]{var1.getclosure(3), var1.getclosure(1), var1.getclosure(4), var1.getclosure(2)};
      var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(16, var8);
      var3 = null;
      var1.setline(1198);
      var6 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var6;
      var10004 = spilldata$98;
      var6 = new PyObject[]{var1.getclosure(3), var1.getclosure(1), var1.getclosure(4), var1.getclosure(0), var1.getclosure(2)};
      var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(17, var8);
      var3 = null;
      var1.setline(1213);
      var10000 = var1.getglobal("filter");
      var1.setline(1213);
      var6 = Py.EmptyObjects;
      PyObject[] var10005 = var6;
      PyObject var13 = var1.f_globals;
      PyCode var10006 = f$99;
      var6 = new PyObject[]{var1.getclosure(0)};
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var13, var10005, var10006, var6)), (PyObject)var1.getglobal("classify_class_attrs").__call__(var2, var1.getderef(0)));
      var1.setlocal(18, var3);
      var3 = null;

      while(true) {
         var1.setline(1215);
         if (!var1.getlocal(18).__nonzero__()) {
            var1.setline(1248);
            var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(11));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(1249);
            if (var1.getlocal(11).__not__().__nonzero__()) {
               var1.setline(1250);
               var3 = var1.getlocal(8)._add(PyString.fromInterned("\n"));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1251);
            var3 = var1.getlocal(8)._add(PyString.fromInterned("\n"))._add(var1.getderef(4).__getattr__("indent").__call__((ThreadState)var2, (PyObject)var1.getglobal("rstrip").__call__(var2, var1.getlocal(11)), (PyObject)PyString.fromInterned(" |  ")))._add(PyString.fromInterned("\n"));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1216);
         if (var1.getlocal(12).__nonzero__()) {
            var1.setline(1217);
            var3 = var1.getlocal(12).__getattr__("popleft").__call__(var2);
            var1.setderef(5, var3);
            var3 = null;
         } else {
            var1.setline(1219);
            var3 = var1.getlocal(18).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(2));
            var1.setderef(5, var3);
            var3 = null;
         }

         var1.setline(1220);
         var10000 = var1.getglobal("_split_list");
         var10002 = var1.getlocal(18);
         var1.setline(1220);
         var6 = Py.EmptyObjects;
         PyObject[] var15 = var6;
         PyObject var14 = var1.f_globals;
         PyCode var10007 = f$100;
         var6 = new PyObject[]{var1.getclosure(5)};
         var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyFunction(var14, var15, var10007, var6)));
         PyObject[] var7 = Py.unpackSequence(var3, 2);
         PyObject var5 = var7[0];
         var1.setlocal(18, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(19, var5);
         var5 = null;
         var3 = null;
         var1.setline(1222);
         var3 = var1.getderef(5);
         var10000 = var3._is(var1.getglobal("__builtin__").__getattr__("object"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1223);
            var3 = var1.getlocal(19);
            var1.setlocal(18, var3);
            var3 = null;
         } else {
            var1.setline(1225);
            var3 = var1.getderef(5);
            var10000 = var3._is(var1.getderef(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1226);
               PyString var10 = PyString.fromInterned("defined here");
               var1.setlocal(20, var10);
               var3 = null;
            } else {
               var1.setline(1228);
               var3 = PyString.fromInterned("inherited from %s")._mod(var1.getglobal("classname").__call__(var2, var1.getderef(5), var1.getderef(0).__getattr__("__module__")));
               var1.setlocal(20, var3);
               var3 = null;
            }

            var1.setline(1232);
            var1.getlocal(18).__getattr__("sort").__call__(var2);
            var1.setline(1235);
            var10000 = var1.getlocal(15);
            var10002 = PyString.fromInterned("Methods %s:\n")._mod(var1.getlocal(20));
            PyObject var12 = var1.getlocal(18);
            var1.setline(1236);
            var6 = Py.EmptyObjects;
            var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var12, (PyObject)(new PyFunction(var1.f_globals, var6, f$101)));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(1237);
            var10000 = var1.getlocal(15);
            var10002 = PyString.fromInterned("Class methods %s:\n")._mod(var1.getlocal(20));
            var12 = var1.getlocal(18);
            var1.setline(1238);
            var6 = Py.EmptyObjects;
            var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var12, (PyObject)(new PyFunction(var1.f_globals, var6, f$102)));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(1239);
            var10000 = var1.getlocal(15);
            var10002 = PyString.fromInterned("Static methods %s:\n")._mod(var1.getlocal(20));
            var12 = var1.getlocal(18);
            var1.setline(1240);
            var6 = Py.EmptyObjects;
            var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var12, (PyObject)(new PyFunction(var1.f_globals, var6, f$103)));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(1241);
            var10000 = var1.getlocal(16);
            var10002 = PyString.fromInterned("Data descriptors %s:\n")._mod(var1.getlocal(20));
            var12 = var1.getlocal(18);
            var1.setline(1242);
            var6 = Py.EmptyObjects;
            var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var12, (PyObject)(new PyFunction(var1.f_globals, var6, f$104)));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(1243);
            var10000 = var1.getlocal(17);
            var10002 = PyString.fromInterned("Data and other attributes %s:\n")._mod(var1.getlocal(20));
            var12 = var1.getlocal(18);
            var1.setline(1244);
            var6 = Py.EmptyObjects;
            var3 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var12, (PyObject)(new PyFunction(var1.f_globals, var6, f$105)));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(1245);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(18);
               var10000 = var3._eq(new PyList(Py.EmptyObjects));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(1246);
            var3 = var1.getlocal(19);
            var1.setlocal(18, var3);
            var3 = null;
         }
      }
   }

   public PyObject makename$92(PyFrame var1, ThreadState var2) {
      var1.setline(1140);
      PyObject var3 = var1.getglobal("classname").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HorizontalRule$93(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1164);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$94, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1166);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = maybe$95;
      var3 = new PyObject[]{var1.f_back.getclosure(1)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("maybe", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$94(PyFrame var1, ThreadState var2) {
      var1.setline(1165);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"needone", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject maybe$95(PyFrame var1, ThreadState var2) {
      var1.setline(1167);
      if (var1.getlocal(0).__getattr__("needone").__nonzero__()) {
         var1.setline(1168);
         var1.getderef(0).__call__(var2, PyString.fromInterned("-")._mul(Py.newInteger(70)));
      }

      var1.setline(1169);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"needone", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject spill$96(PyFrame var1, ThreadState var2) {
      var1.setline(1173);
      PyObject var3 = var1.getglobal("_split_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(1174);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1175);
         var1.getderef(0).__getattr__("maybe").__call__(var2);
         var1.setline(1176);
         var1.getderef(1).__call__(var2, var1.getlocal(0));
         var1.setline(1177);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(1177);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               break;
            }

            PyObject[] var9 = Py.unpackSequence(var8, 4);
            PyObject var6 = var9[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var9[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var9[3];
            var1.setlocal(7, var6);
            var6 = null;

            try {
               var1.setline(1179);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getderef(2), var1.getlocal(4));
               var1.setlocal(7, var5);
               var5 = null;
            } catch (Throwable var7) {
               PyException var10 = Py.setException(var7, var1);
               if (var10.match(var1.getglobal("Exception"))) {
                  var1.setline(1183);
                  var1.getderef(1).__call__(var2, var1.getderef(3).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(4), var1.getlocal(7), var1.getderef(4)));
                  continue;
               }

               throw var10;
            }

            var1.setline(1185);
            var1.getderef(1).__call__(var2, var1.getderef(3).__getattr__("document").__call__(var2, var1.getlocal(7), var1.getlocal(4), var1.getderef(4), var1.getderef(2)));
         }
      }

      var1.setline(1187);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spilldescriptors$97(PyFrame var1, ThreadState var2) {
      var1.setline(1190);
      PyObject var3 = var1.getglobal("_split_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(1191);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1192);
         var1.getderef(0).__getattr__("maybe").__call__(var2);
         var1.setline(1193);
         var1.getderef(1).__call__(var2, var1.getlocal(0));
         var1.setline(1194);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(1194);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               break;
            }

            PyObject[] var8 = Py.unpackSequence(var7, 4);
            PyObject var6 = var8[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var8[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var8[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var8[3];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(1195);
            var1.getderef(1).__call__(var2, var1.getderef(2).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(4), var1.getlocal(7), var1.getderef(3)));
         }
      }

      var1.setline(1196);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spilldata$98(PyFrame var1, ThreadState var2) {
      var1.setline(1199);
      PyObject var3 = var1.getglobal("_split_list").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(1200);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1201);
         var1.getderef(0).__getattr__("maybe").__call__(var2);
         var1.setline(1202);
         var1.getderef(1).__call__(var2, var1.getlocal(0));
         var1.setline(1203);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(1203);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               break;
            }

            PyObject[] var8 = Py.unpackSequence(var7, 4);
            PyObject var6 = var8[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var8[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var8[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var8[3];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(1204);
            PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("__call__"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("inspect").__getattr__("isdatadescriptor").__call__(var2, var1.getlocal(7));
            }

            if (var10000.__nonzero__()) {
               var1.setline(1206);
               var5 = var1.getglobal("getdoc").__call__(var2, var1.getlocal(7));
               var1.setlocal(8, var5);
               var5 = null;
            } else {
               var1.setline(1208);
               var5 = var1.getglobal("None");
               var1.setlocal(8, var5);
               var5 = null;
            }

            var1.setline(1209);
            var10000 = var1.getderef(1);
            PyObject var10002 = var1.getderef(2).__getattr__("docother");
            var8 = new PyObject[]{var1.getglobal("getattr").__call__(var2, var1.getderef(3), var1.getlocal(4)), var1.getlocal(4), var1.getderef(4), Py.newInteger(70), var1.getlocal(8)};
            String[] var9 = new String[]{"maxlen", "doc"};
            var10002 = var10002.__call__(var2, var8, var9);
            var5 = null;
            var10000.__call__(var2, var10002._add(PyString.fromInterned("\n")));
         }
      }

      var1.setline(1211);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$99(PyFrame var1, ThreadState var2) {
      var1.setline(1213);
      PyObject var10000 = var1.getglobal("visiblename");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getderef(0)};
      String[] var4 = new String[]{"obj"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$100(PyFrame var1, ThreadState var2) {
      var1.setline(1220);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
      PyObject var10000 = var3._is(var1.getderef(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$101(PyFrame var1, ThreadState var2) {
      var1.setline(1236);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("method"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$102(PyFrame var1, ThreadState var2) {
      var1.setline(1238);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("class method"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$103(PyFrame var1, ThreadState var2) {
      var1.setline(1240);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("static method"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$104(PyFrame var1, ThreadState var2) {
      var1.setline(1242);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("data descriptor"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$105(PyFrame var1, ThreadState var2) {
      var1.setline(1244);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatvalue$106(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      PyString.fromInterned("Format an argument default value as text.");
      var1.setline(1255);
      PyObject var3 = PyString.fromInterned("=")._add(var1.getlocal(0).__getattr__("repr").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docroutine$107(PyFrame var1, ThreadState var2) {
      var1.setline(1258);
      PyString.fromInterned("Produce text documentation for a function or method object.");
      var1.setline(1259);
      PyObject var3 = var1.getlocal(1).__getattr__("__name__");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1260);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(5);
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1261);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(1262);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(7, var9);
      var3 = null;
      var1.setline(1263);
      if (var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1264);
         var3 = var1.getlocal(1).__getattr__("im_class");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(1265);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(1266);
            var3 = var1.getlocal(8);
            var10000 = var3._isnot(var1.getlocal(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1267);
               var3 = PyString.fromInterned(" from ")._add(var1.getglobal("classname").__call__(var2, var1.getlocal(8), var1.getlocal(3)));
               var1.setlocal(6, var3);
               var3 = null;
            }
         } else {
            var1.setline(1269);
            var3 = var1.getlocal(1).__getattr__("im_self");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1270);
               var3 = PyString.fromInterned(" method of %s instance")._mod(var1.getglobal("classname").__call__(var2, var1.getlocal(1).__getattr__("im_self").__getattr__("__class__"), var1.getlocal(3)));
               var1.setlocal(6, var3);
               var3 = null;
            } else {
               var1.setline(1273);
               var3 = PyString.fromInterned(" unbound %s method")._mod(var1.getglobal("classname").__call__(var2, var1.getlocal(8), var1.getlocal(3)));
               var1.setlocal(6, var3);
               var3 = null;
            }
         }

         var1.setline(1274);
         var3 = var1.getlocal(1).__getattr__("im_func");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1276);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(var1.getlocal(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1277);
         var3 = var1.getlocal(0).__getattr__("bold").__call__(var2, var1.getlocal(5));
         var1.setlocal(9, var3);
         var3 = null;
      } else {
         var1.setline(1279);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(5);
            var10000 = var3._in(var1.getlocal(4).__getattr__("__dict__"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(4).__getattr__("__dict__").__getitem__(var1.getlocal(5));
               var10000 = var3._is(var1.getlocal(1));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1281);
            var9 = Py.newInteger(1);
            var1.setlocal(7, var9);
            var3 = null;
         }

         var1.setline(1282);
         var3 = var1.getlocal(0).__getattr__("bold").__call__(var2, var1.getlocal(2))._add(PyString.fromInterned(" = "))._add(var1.getlocal(5));
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(1283);
      PyObject[] var4;
      if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1284);
         var3 = var1.getglobal("inspect").__getattr__("getargspec").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 4);
         PyObject var5 = var4[0];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(11, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(13, var5);
         var5 = null;
         var3 = null;
         var1.setline(1285);
         var10000 = var1.getglobal("inspect").__getattr__("formatargspec");
         PyObject[] var10 = new PyObject[]{var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(0).__getattr__("formatvalue")};
         String[] var7 = new String[]{"formatvalue"};
         var10000 = var10000.__call__(var2, var10, var7);
         var3 = null;
         var3 = var10000;
         var1.setlocal(14, var3);
         var3 = null;
         var1.setline(1287);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(PyString.fromInterned("<lambda>"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1288);
            var3 = var1.getlocal(0).__getattr__("bold").__call__(var2, var1.getlocal(2))._add(PyString.fromInterned(" lambda "));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(1289);
            var3 = var1.getlocal(14).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(14, var3);
            var3 = null;
         }
      } else {
         var1.setline(1291);
         var6 = PyString.fromInterned("(...)");
         var1.setlocal(14, var6);
         var3 = null;
      }

      var1.setline(1292);
      var3 = var1.getlocal(9)._add(var1.getlocal(14))._add(var1.getlocal(6));
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(1294);
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(1295);
         var3 = var1.getlocal(15)._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1297);
         Object var11 = var1.getglobal("getdoc").__call__(var2, var1.getlocal(1));
         if (!((PyObject)var11).__nonzero__()) {
            var11 = PyString.fromInterned("");
         }

         Object var8 = var11;
         var1.setlocal(16, (PyObject)var8);
         var4 = null;
         var1.setline(1298);
         var10000 = var1.getlocal(15)._add(PyString.fromInterned("\n"));
         PyObject var10001 = var1.getlocal(16);
         if (var10001.__nonzero__()) {
            var10001 = var1.getglobal("rstrip").__call__(var2, var1.getlocal(0).__getattr__("indent").__call__(var2, var1.getlocal(16)))._add(PyString.fromInterned("\n"));
         }

         var3 = var10000._add(var10001);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _docdescriptor$108(PyFrame var1, ThreadState var2) {
      var1.setline(1301);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1302);
      PyObject var4 = var1.getlocal(4).__getattr__("append");
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(1304);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1305);
         var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("bold").__call__(var2, var1.getlocal(1)));
         var1.setline(1306);
         var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.setline(1307);
      Object var10000 = var1.getglobal("getdoc").__call__(var2, var1.getlocal(2));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      Object var5 = var10000;
      var1.setlocal(6, (PyObject)var5);
      var3 = null;
      var1.setline(1308);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(1309);
         var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("indent").__call__(var2, var1.getlocal(6)));
         var1.setline(1310);
         var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.setline(1311);
      var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject docproperty$109(PyFrame var1, ThreadState var2) {
      var1.setline(1314);
      PyString.fromInterned("Produce text documentation for a property.");
      var1.setline(1315);
      PyObject var3 = var1.getlocal(0).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docdata$110(PyFrame var1, ThreadState var2) {
      var1.setline(1318);
      PyString.fromInterned("Produce text documentation for a data descriptor.");
      var1.setline(1319);
      PyObject var3 = var1.getlocal(0).__getattr__("_docdescriptor").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docother$111(PyFrame var1, ThreadState var2) {
      var1.setline(1322);
      PyString.fromInterned("Produce text documentation for a data object.");
      var1.setline(1323);
      PyObject var3 = var1.getlocal(0).__getattr__("repr").__call__(var2, var1.getlocal(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1324);
      Object var10000;
      PyObject var4;
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(1325);
         var10000 = var1.getlocal(2);
         if (((PyObject)var10000).__nonzero__()) {
            var10000 = var1.getlocal(2)._add(PyString.fromInterned(" = "));
         }

         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("");
         }

         var3 = ((PyObject)var10000)._add(var1.getlocal(7));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(1326);
         var3 = var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(8)));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1327);
         var3 = var1.getlocal(9);
         var4 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var4.__nonzero__()) {
            var1.setline(1327);
            var3 = var1.getlocal(7).__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null)._add(PyString.fromInterned("..."));
            var1.setlocal(7, var3);
            var3 = null;
         }
      }

      var1.setline(1328);
      var10000 = var1.getlocal(2);
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("bold").__call__(var2, var1.getlocal(2))._add(PyString.fromInterned(" = "));
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      var3 = ((PyObject)var10000)._add(var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1329);
      var3 = var1.getlocal(6);
      var4 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var4.__nonzero__()) {
         var1.setline(1330);
         var3 = var1.getlocal(8);
         var3 = var3._iadd(PyString.fromInterned("\n")._add(var1.getlocal(0).__getattr__("indent").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6)))));
         var1.setlocal(8, var3);
      }

      var1.setline(1331);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AnsiDoc$112(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Formatter class for ANSI texst documentation."));
      var1.setline(1334);
      PyString.fromInterned("Formatter class for ANSI texst documentation.");
      var1.setline(1336);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, bold$113, PyString.fromInterned("Format a string in bold by overstriking."));
      var1.setlocal("bold", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject bold$113(PyFrame var1, ThreadState var2) {
      var1.setline(1337);
      PyString.fromInterned("Format a string in bold by overstriking.");
      var1.setline(1338);
      PyObject var3 = PyString.fromInterned("\u001b[1m")._add(var1.getlocal(1))._add(PyString.fromInterned("\u001b[0m"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pager$114(PyFrame var1, ThreadState var2) {
      var1.setline(1344);
      PyString.fromInterned("The first time this is called, determine what kind of pager to use.");
      var1.setline(1346);
      PyObject var3 = var1.getglobal("getpager").__call__(var2);
      var1.setglobal("pager", var3);
      var3 = null;
      var1.setline(1347);
      var1.getglobal("pager").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject JLine2Pager$115(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1353);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, available$116, (PyObject)null);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("available", var5);
      var3 = null;
      var1.setline(1361);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __init__$117, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1375);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, visible$118, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("visible", var5);
      var3 = null;
      var1.setline(1381);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_prompt$119, (PyObject)null);
      var1.setlocal("handle_prompt", var4);
      var3 = null;
      var1.setline(1408);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, page$120, (PyObject)null);
      var1.setlocal("page", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject available$116(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(1356);
         var1.getglobal("sys").__getattr__("_jy_console").__getattr__("reader");
         var1.setline(1357);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(1359);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __init__$117(PyFrame var1, ThreadState var2) {
      var1.setline(1362);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(1363);
      var3 = var1.getglobal("sys").__getattr__("_jy_console").__getattr__("reader");
      var1.getlocal(0).__setattr__("reader", var3);
      var3 = null;
      var1.setline(1364);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"index", var5);
      var3 = null;
      var1.setline(1365);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("bold"), PyString.fromInterned("\u001b[1m"), PyString.fromInterned("negative"), PyString.fromInterned("\u001b[7m"), PyString.fromInterned("normal"), PyString.fromInterned("\u001b[0m")});
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1370);
      PyObject var10000 = PyString.fromInterned("--more-- space/{bold}b{normal}ack/{bold}q{normal}uit:").__getattr__("format");
      PyObject[] var7 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var7, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("more_prompt_back", var3);
      var3 = null;
      var1.setline(1371);
      var10000 = PyString.fromInterned("--more-- space/{bold}q{normal}uit:").__getattr__("format");
      var7 = Py.EmptyObjects;
      var4 = new String[0];
      var10000 = var10000._callextra(var7, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("more_prompt", var3);
      var3 = null;
      var1.setline(1372);
      var10000 = PyString.fromInterned("{negative}(END){normal} {bold}b{normal}ack/{bold}q{normal}uit:").__getattr__("format");
      var7 = Py.EmptyObjects;
      var4 = new String[0];
      var10000 = var10000._callextra(var7, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("end_prompt_back", var3);
      var3 = null;
      var1.setline(1373);
      var10000 = PyString.fromInterned("{negative}(END){normal} {bold}q{normal}uit:").__getattr__("format");
      var7 = Py.EmptyObjects;
      var4 = new String[0];
      var10000 = var10000._callextra(var7, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("end_prompt", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject visible$118(PyFrame var1, ThreadState var2) {
      var1.setline(1379);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("terminal").__getattr__("height")._sub(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handle_prompt$119(PyFrame var1, ThreadState var2) {
      var1.setline(1382);
      PyObject var3 = var1.getlocal(0).__getattr__("index")._sub(var1.getlocal(0).__getattr__("visible"));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1383);
      var3 = var1.getlocal(0).__getattr__("index");
      var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1384);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1385);
            var1.getlocal(0).__getattr__("reader").__getattr__("resetPromptLine").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("end_prompt_back"), (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0));
         } else {
            var1.setline(1387);
            var1.getlocal(0).__getattr__("reader").__getattr__("resetPromptLine").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("end_prompt"), (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0));
         }
      } else {
         var1.setline(1389);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1390);
            var1.getlocal(0).__getattr__("reader").__getattr__("resetPromptLine").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("more_prompt_back"), (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0));
         } else {
            var1.setline(1392);
            var1.getlocal(0).__getattr__("reader").__getattr__("resetPromptLine").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("more_prompt"), (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0));
         }
      }

      var1.setline(1393);
      var3 = var1.getglobal("chr").__call__(var2, var1.getlocal(0).__getattr__("reader").__getattr__("readCharacter").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1394);
      var1.getlocal(0).__getattr__("reader").__getattr__("resetPromptLine").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0));
      var1.setline(1395);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(PyString.fromInterned("q"));
      var3 = null;
      PyString var8;
      if (var10000.__nonzero__()) {
         var1.setline(1396);
         var8 = PyString.fromInterned("quit");
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(1397);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("b"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1398);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(1399);
               var8 = PyString.fromInterned("reprompt");
               var1.f_lasti = -1;
               return var8;
            } else {
               var1.setline(1400);
               var10000 = var1.getlocal(0);
               String var7 = "index";
               PyObject var5 = var10000;
               PyObject var6 = var5.__getattr__(var7);
               var6 = var6._isub(var1.getlocal(0).__getattr__("visible")._mul(Py.newInteger(2)));
               var5.__setattr__(var7, var6);
               var1.setline(1401);
               var4 = var1.getlocal(0).__getattr__("index");
               var10000 = var4._lt(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1402);
                  PyInteger var9 = Py.newInteger(0);
                  var1.getlocal(0).__setattr__((String)"index", var9);
                  var4 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         } else {
            var1.setline(1403);
            var4 = var1.getlocal(0).__getattr__("index");
            var10000 = var4._ne(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1404);
               var8 = PyString.fromInterned("forward");
               var1.f_lasti = -1;
               return var8;
            } else {
               var1.setline(1406);
               var8 = PyString.fromInterned("reprompt");
               var1.f_lasti = -1;
               return var8;
            }
         }
      }
   }

   public PyObject page$120(PyFrame var1, ThreadState var2) {
      var1.setline(1411);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         PyObject var10000;
         PyObject var6;
         do {
            var1.setline(1412);
            var6 = var1.getlocal(0).__getattr__("index");
            var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1425);
               var1.getlocal(0).__getattr__("reader").__getattr__("resetPromptLine").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(1413);
            var6 = var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(0).__getattr__("index"));
            var1.setlocal(2, var6);
            var3 = null;
            var1.setline(1414);
            var1.getlocal(0).__getattr__("reader").__getattr__("print").__call__(var2, var1.getlocal(2)._add(PyString.fromInterned("\n")));
            var1.setline(1415);
            var10000 = var1.getlocal(0);
            String var7 = "index";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var7);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var7, var5);
            var1.setline(1416);
            var6 = var1.getlocal(1);
            var6 = var6._iadd(Py.newInteger(1));
            var1.setlocal(1, var6);
            var1.setline(1417);
            var6 = var1.getlocal(1);
            var10000 = var6._eq(var1.getlocal(0).__getattr__("visible"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(0).__getattr__("index");
               var10000 = var6._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data")));
               var3 = null;
            }
         } while(!var10000.__nonzero__());

         do {
            var1.setline(1418);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1419);
            var6 = var1.getlocal(0).__getattr__("handle_prompt").__call__(var2);
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(1420);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(PyString.fromInterned("quit"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1421);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(1422);
            var6 = var1.getlocal(3);
            var10000 = var6._ne(PyString.fromInterned("reprompt"));
            var3 = null;
         } while(!var10000.__nonzero__());

         var1.setline(1424);
         var3 = Py.newInteger(0);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject getpager$121(PyFrame var1, ThreadState var2) {
      var1.setline(1429);
      PyString.fromInterned("Decide what method to use for paging through text.");
      var1.setline(1430);
      PyObject var10000 = var1.getglobal("_is_windows");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("JLine2Pager").__getattr__("available").__call__(var2);
      }

      PyFunction var3;
      if (var10000.__nonzero__()) {
         var1.setline(1431);
         var1.setline(1431);
         PyObject[] var14 = Py.EmptyObjects;
         var3 = new PyFunction(var1.f_globals, var14, f$122);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1432);
         PyObject var4 = var1.getglobal("type").__call__(var2, var1.getglobal("sys").__getattr__("stdout"));
         var10000 = var4._isnot(var1.getglobal("types").__getattr__("FileType"));
         var4 = null;
         PyObject var11;
         if (var10000.__nonzero__()) {
            var1.setline(1433);
            var11 = var1.getglobal("plainpager");
            var1.f_lasti = -1;
            return var11;
         } else {
            var1.setline(1434);
            var10000 = var1.getglobal("sys").__getattr__("stdin").__getattr__("isatty").__call__(var2).__not__();
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("sys").__getattr__("stdout").__getattr__("isatty").__call__(var2).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1435);
               var11 = var1.getglobal("plainpager");
               var1.f_lasti = -1;
               return var11;
            } else {
               var1.setline(1436);
               PyString var12 = PyString.fromInterned("PAGER");
               var10000 = var12._in(var1.getglobal("os").__getattr__("environ"));
               var4 = null;
               PyObject[] var16;
               if (var10000.__nonzero__()) {
                  var1.setline(1437);
                  if (var1.getglobal("_is_windows").__nonzero__()) {
                     var1.setline(1438);
                     var1.setline(1438);
                     var16 = Py.EmptyObjects;
                     var3 = new PyFunction(var1.f_globals, var16, f$123);
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1439);
                     var4 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TERM"));
                     var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("dumb"), PyString.fromInterned("emacs")}));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1440);
                        var1.setline(1440);
                        var16 = Py.EmptyObjects;
                        var3 = new PyFunction(var1.f_globals, var16, f$124);
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(1442);
                        var1.setline(1442);
                        var16 = Py.EmptyObjects;
                        var3 = new PyFunction(var1.f_globals, var16, f$125);
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }
               } else {
                  var1.setline(1443);
                  var4 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TERM"));
                  var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("dumb"), PyString.fromInterned("emacs")}));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1444);
                     var11 = var1.getglobal("plainpager");
                     var1.f_lasti = -1;
                     return var11;
                  } else {
                     var1.setline(1445);
                     var10000 = var1.getglobal("_is_windows");
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("os2"));
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(1446);
                        var1.setline(1446);
                        var16 = Py.EmptyObjects;
                        var3 = new PyFunction(var1.f_globals, var16, f$126);
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(1447);
                        var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("system"));
                        if (var10000.__nonzero__()) {
                           var4 = var1.getglobal("os").__getattr__("system").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(less) 2>/dev/null"));
                           var10000 = var4._eq(Py.newInteger(0));
                           var4 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(1448);
                           var1.setline(1448);
                           var16 = Py.EmptyObjects;
                           var3 = new PyFunction(var1.f_globals, var16, f$127);
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(1450);
                           var4 = imp.importOne("tempfile", var1, -1);
                           var1.setlocal(0, var4);
                           var4 = null;
                           var1.setline(1451);
                           var4 = var1.getlocal(0).__getattr__("mkstemp").__call__(var2);
                           PyObject[] var5 = Py.unpackSequence(var4, 2);
                           PyObject var6 = var5[0];
                           var1.setlocal(1, var6);
                           var6 = null;
                           var6 = var5[1];
                           var1.setlocal(2, var6);
                           var6 = null;
                           var4 = null;
                           var1.setline(1452);
                           var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(1));
                           var4 = null;

                           Throwable var17;
                           label99: {
                              boolean var10001;
                              label100: {
                                 try {
                                    var1.setline(1454);
                                    var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("system"));
                                    if (var10000.__nonzero__()) {
                                       PyObject var13 = var1.getglobal("os").__getattr__("system").__call__(var2, PyString.fromInterned("more \"%s\"")._mod(var1.getlocal(2)));
                                       var10000 = var13._eq(Py.newInteger(0));
                                       var5 = null;
                                    }

                                    if (!var10000.__nonzero__()) {
                                       break label100;
                                    }

                                    var1.setline(1455);
                                    var1.setline(1455);
                                    var5 = Py.EmptyObjects;
                                    var3 = new PyFunction(var1.f_globals, var5, f$128);
                                 } catch (Throwable var10) {
                                    var17 = var10;
                                    var10001 = false;
                                    break label99;
                                 }

                                 var1.setline(1459);
                                 var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(2));

                                 try {
                                    var1.f_lasti = -1;
                                    return var3;
                                 } catch (Throwable var7) {
                                    var17 = var7;
                                    var10001 = false;
                                    break label99;
                                 }
                              }

                              try {
                                 var1.setline(1457);
                                 var11 = var1.getglobal("ttypager");
                              } catch (Throwable var9) {
                                 var17 = var9;
                                 var10001 = false;
                                 break label99;
                              }

                              var1.setline(1459);
                              var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(2));

                              try {
                                 var1.f_lasti = -1;
                                 return var11;
                              } catch (Throwable var8) {
                                 var17 = var8;
                                 var10001 = false;
                              }
                           }

                           Throwable var15 = var17;
                           Py.addTraceback(var15, var1);
                           var1.setline(1459);
                           var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(2));
                           throw (Throwable)var15;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject f$122(PyFrame var1, ThreadState var2) {
      var1.setline(1431);
      PyObject var3 = var1.getglobal("JLine2Pager").__call__(var2, var1.getlocal(0)).__getattr__("page").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$123(PyFrame var1, ThreadState var2) {
      var1.setline(1438);
      PyObject var3 = var1.getglobal("tempfilepager").__call__(var2, var1.getglobal("plain").__call__(var2, var1.getlocal(0)), var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("PAGER")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$124(PyFrame var1, ThreadState var2) {
      var1.setline(1440);
      PyObject var3 = var1.getglobal("pipepager").__call__(var2, var1.getglobal("plain").__call__(var2, var1.getlocal(0)), var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("PAGER")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$125(PyFrame var1, ThreadState var2) {
      var1.setline(1442);
      PyObject var3 = var1.getglobal("pipepager").__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("PAGER")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$126(PyFrame var1, ThreadState var2) {
      var1.setline(1446);
      PyObject var3 = var1.getglobal("tempfilepager").__call__((ThreadState)var2, (PyObject)var1.getglobal("plain").__call__(var2, var1.getlocal(0)), (PyObject)PyString.fromInterned("more <"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$127(PyFrame var1, ThreadState var2) {
      var1.setline(1448);
      PyObject var3 = var1.getglobal("pipepager").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("less"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$128(PyFrame var1, ThreadState var2) {
      var1.setline(1455);
      PyObject var3 = var1.getglobal("pipepager").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("more"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject plain$129(PyFrame var1, ThreadState var2) {
      var1.setline(1462);
      PyString.fromInterned("Remove boldface formatting from text.");
      var1.setline(1463);
      PyObject var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(".\b"), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pipepager$130(PyFrame var1, ThreadState var2) {
      var1.setline(1466);
      PyString.fromInterned("Page through text by feeding it to another program.");
      var1.setline(1467);
      PyObject var3 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(1469);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(0));
         var1.setline(1470);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("IOError"))) {
            throw var5;
         }

         var1.setline(1472);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tempfilepager$131(PyFrame var1, ThreadState var2) {
      var1.setline(1475);
      PyString.fromInterned("Page through text by invoking a program on a temporary file.");
      var1.setline(1476);
      PyObject var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1477);
      var3 = var1.getlocal(2).__getattr__("mktemp").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1478);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1479);
      var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(0));
      var1.setline(1480);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var3 = null;

      try {
         var1.setline(1482);
         var1.getglobal("os").__getattr__("system").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned(" \""))._add(var1.getlocal(3))._add(PyString.fromInterned("\"")));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(1484);
         var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(3));
         throw (Throwable)var4;
      }

      var1.setline(1484);
      var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ttypager$132(PyFrame var1, ThreadState var2) {
      var1.setline(1487);
      PyString.fromInterned("Page through text on a text terminal.");
      var1.setline(1488);
      PyObject var3 = var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("plain").__call__(var2, var1.getlocal(0)), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(1490);
         var3 = imp.importOne("tty", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1491);
         var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("fileno").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1492);
         var3 = var1.getlocal(2).__getattr__("tcgetattr").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1493);
         var1.getlocal(2).__getattr__("setcbreak").__call__(var2, var1.getlocal(3));
         var1.setline(1494);
         var1.setline(1494);
         PyObject[] var10 = Py.EmptyObjects;
         PyFunction var11 = new PyFunction(var1.f_globals, var10, f$133);
         var1.setlocal(5, var11);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("AttributeError")}))) {
            throw var7;
         }

         var1.setline(1496);
         var4 = var1.getglobal("None");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1497);
         var1.setline(1497);
         PyObject[] var8 = Py.EmptyObjects;
         PyFunction var9 = new PyFunction(var1.f_globals, var8, f$134);
         var1.setlocal(5, var9);
         var4 = null;
      }

      var3 = null;

      try {
         var1.setline(1500);
         var4 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LINES"), (PyObject)Py.newInteger(25))._sub(Py.newInteger(1));
         var1.setlocal(6, var4);
         var1.setlocal(7, var4);
         var1.setline(1501);
         var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null), (PyObject)PyString.fromInterned("\n"))._add(PyString.fromInterned("\n")));

         while(true) {
            var1.setline(1502);
            if (!var1.getlocal(1).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null).__nonzero__()) {
               break;
            }

            var1.setline(1503);
            var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-- more --"));
            var1.setline(1504);
            var1.getglobal("sys").__getattr__("stdout").__getattr__("flush").__call__(var2);
            var1.setline(1505);
            var4 = var1.getlocal(5).__call__(var2);
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(1507);
            var4 = var1.getlocal(8);
            PyObject var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("q"), PyString.fromInterned("Q")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1508);
               var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r          \r"));
               break;
            }

            var1.setline(1510);
            var4 = var1.getlocal(8);
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("\r"), PyString.fromInterned("\n")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1511);
               var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("\r          \r")._add(var1.getlocal(1).__getitem__(var1.getlocal(6)))._add(PyString.fromInterned("\n")));
               var1.setline(1512);
               var4 = var1.getlocal(6)._add(Py.newInteger(1));
               var1.setlocal(6, var4);
               var4 = null;
            } else {
               var1.setline(1514);
               var4 = var1.getlocal(8);
               var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("b"), PyString.fromInterned("B"), PyString.fromInterned("\u001b")}));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1515);
                  var4 = var1.getlocal(6)._sub(var1.getlocal(7))._sub(var1.getlocal(7));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(1516);
                  var4 = var1.getlocal(6);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1516);
                     PyInteger var12 = Py.newInteger(0);
                     var1.setlocal(6, var12);
                     var4 = null;
                  }
               }

               var1.setline(1517);
               var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("\n")._add(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getslice__(var1.getlocal(6), var1.getlocal(6)._add(var1.getlocal(7)), (PyObject)null), (PyObject)PyString.fromInterned("\n")))._add(PyString.fromInterned("\n")));
               var1.setline(1518);
               var4 = var1.getlocal(6)._add(var1.getlocal(7));
               var1.setlocal(6, var4);
               var4 = null;
            }
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1521);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(1522);
            var1.getlocal(2).__getattr__("tcsetattr").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("TCSAFLUSH"), var1.getlocal(4));
         }

         throw (Throwable)var5;
      }

      var1.setline(1521);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1522);
         var1.getlocal(2).__getattr__("tcsetattr").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("TCSAFLUSH"), var1.getlocal(4));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$133(PyFrame var1, ThreadState var2) {
      var1.setline(1494);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$134(PyFrame var1, ThreadState var2) {
      var1.setline(1497);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject plainpager$135(PyFrame var1, ThreadState var2) {
      var1.setline(1525);
      PyString.fromInterned("Simply print unformatted text.  This is the ultimate fallback.");
      var1.setline(1526);
      var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getglobal("plain").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject describe$136(PyFrame var1, ThreadState var2) {
      var1.setline(1529);
      PyString.fromInterned("Produce a short description of the given thing.");
      var1.setline(1530);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(1531);
         var3 = var1.getlocal(0).__getattr__("__name__");
         var10000 = var3._in(var1.getglobal("sys").__getattr__("builtin_module_names"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1532);
            var3 = PyString.fromInterned("built-in module ")._add(var1.getlocal(0).__getattr__("__name__"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1533);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__path__")).__nonzero__()) {
               var1.setline(1534);
               var3 = PyString.fromInterned("package ")._add(var1.getlocal(0).__getattr__("__name__"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1536);
               var3 = PyString.fromInterned("module ")._add(var1.getlocal(0).__getattr__("__name__"));
               var1.f_lasti = -1;
               return var3;
            }
         }
      } else {
         var1.setline(1537);
         if (var1.getglobal("inspect").__getattr__("isbuiltin").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(1538);
            var3 = PyString.fromInterned("built-in function ")._add(var1.getlocal(0).__getattr__("__name__"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1539);
            if (var1.getglobal("inspect").__getattr__("isgetsetdescriptor").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(1540);
               var3 = PyString.fromInterned("getset descriptor %s.%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__objclass__").__getattr__("__module__"), var1.getlocal(0).__getattr__("__objclass__").__getattr__("__name__"), var1.getlocal(0).__getattr__("__name__")}));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1543);
               if (var1.getglobal("inspect").__getattr__("ismemberdescriptor").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                  var1.setline(1544);
                  var3 = PyString.fromInterned("member descriptor %s.%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__objclass__").__getattr__("__module__"), var1.getlocal(0).__getattr__("__objclass__").__getattr__("__name__"), var1.getlocal(0).__getattr__("__name__")}));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(1547);
                  if (var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(1548);
                     var3 = PyString.fromInterned("class ")._add(var1.getlocal(0).__getattr__("__name__"));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1549);
                     if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                        var1.setline(1550);
                        var3 = PyString.fromInterned("function ")._add(var1.getlocal(0).__getattr__("__name__"));
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(1551);
                        if (var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                           var1.setline(1552);
                           var3 = PyString.fromInterned("method ")._add(var1.getlocal(0).__getattr__("__name__"));
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(1553);
                           PyObject var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
                           var10000 = var4._is(var1.getglobal("types").__getattr__("InstanceType"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1554);
                              var3 = PyString.fromInterned("instance of ")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"));
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(1555);
                              var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__");
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject locate$137(PyFrame var1, ThreadState var2) {
      var1.setline(1558);
      PyString.fromInterned("Locate an object by name or dotted path, importing as necessary.");
      var1.setline(1559);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1559);
      var3 = var1.getglobal("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned(".")).__iter__();

      while(true) {
         var1.setline(1559);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1559);
            var1.dellocal(3);
            PyList var8 = var10000;
            var1.setlocal(2, var8);
            var3 = null;
            var1.setline(1560);
            PyTuple var10 = new PyTuple(new PyObject[]{var1.getglobal("None"), Py.newInteger(0)});
            PyObject[] var9 = Py.unpackSequence(var10, 2);
            PyObject var5 = var9[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var9[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;

            while(true) {
               var1.setline(1561);
               var3 = var1.getlocal(6);
               PyObject var12 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
               var3 = null;
               if (!var12.__nonzero__()) {
                  break;
               }

               var1.setline(1562);
               var3 = var1.getglobal("safeimport").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(6)._add(Py.newInteger(1)), (PyObject)null), (PyObject)PyString.fromInterned(".")), var1.getlocal(1));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1563);
               if (!var1.getlocal(7).__nonzero__()) {
                  break;
               }

               var1.setline(1563);
               var10 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(6)._add(Py.newInteger(1))});
               var9 = Py.unpackSequence(var10, 2);
               var5 = var9[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var9[1];
               var1.setlocal(6, var5);
               var5 = null;
               var3 = null;
            }

            var1.setline(1565);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(1566);
               var3 = var1.getlocal(5);
               var1.setlocal(8, var3);
               var3 = null;
            } else {
               var1.setline(1568);
               var3 = var1.getglobal("__builtin__");
               var1.setlocal(8, var3);
               var3 = null;
            }

            var1.setline(1569);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null).__iter__();

            while(true) {
               var1.setline(1569);
               var4 = var3.__iternext__();
               PyObject var6;
               if (var4 == null) {
                  var1.setline(1574);
                  var6 = var1.getlocal(8);
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setlocal(4, var4);

               try {
                  var1.setline(1571);
                  var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(8), var1.getlocal(4));
                  var1.setlocal(8, var5);
                  var5 = null;
               } catch (Throwable var7) {
                  PyException var11 = Py.setException(var7, var1);
                  if (var11.match(var1.getglobal("AttributeError"))) {
                     var1.setline(1573);
                     var6 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var6;
                  }

                  throw var11;
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(1559);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(1559);
            var1.getlocal(3).__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject _OldStyleClass$138(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1584);
      return var1.getf_locals();
   }

   public PyObject resolve$139(PyFrame var1, ThreadState var2) {
      var1.setline(1588);
      PyString.fromInterned("Given an object or a path to an object, get the object and its name.");
      var1.setline(1589);
      PyTuple var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
         var1.setline(1590);
         PyObject var5 = var1.getglobal("locate").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(1591);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(1592);
            throw Py.makeException(var1.getglobal("ImportError"), PyString.fromInterned("no Python documentation found for %r")._mod(var1.getlocal(0)));
         } else {
            var1.setline(1593);
            var3 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0)});
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(1595);
         PyObject var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__name__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1596);
         PyObject[] var10002 = new PyObject[]{var1.getlocal(0), null};
         var1.setline(1596);
         var10002[1] = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("str")).__nonzero__() ? var1.getlocal(3) : var1.getglobal("None");
         var3 = new PyTuple(var10002);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject render_doc$140(PyFrame var1, ThreadState var2) {
      var1.setline(1599);
      PyString.fromInterned("Render text documentation, given an object or a path to an object.");
      var1.setline(1600);
      PyObject var3 = var1.getglobal("resolve").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1601);
      var3 = var1.getglobal("describe").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1602);
      var3 = var1.getglobal("inspect").__getattr__("getmodule").__call__(var2, var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1603);
      PyObject var10000 = var1.getlocal(4);
      if (var10000.__nonzero__()) {
         PyString var6 = PyString.fromInterned(".");
         var10000 = var6._in(var1.getlocal(4));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1604);
         var3 = var1.getlocal(5);
         var3 = var3._iadd(PyString.fromInterned(" in ")._add(var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(4).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")), (PyObject)null)));
         var1.setlocal(5, var3);
      } else {
         var1.setline(1605);
         var10000 = var1.getlocal(6);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(6);
            var10000 = var3._isnot(var1.getlocal(3));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1606);
            var3 = var1.getlocal(5);
            var3 = var3._iadd(PyString.fromInterned(" in module ")._add(var1.getlocal(6).__getattr__("__name__")));
            var1.setlocal(5, var3);
         }
      }

      var1.setline(1607);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
      var10000 = var3._is(var1.getglobal("_OLD_INSTANCE_TYPE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1610);
         var3 = var1.getlocal(3).__getattr__("__class__");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1611);
         var10000 = var1.getglobal("inspect").__getattr__("ismodule").__call__(var2, var1.getlocal(3));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("inspect").__getattr__("isclass").__call__(var2, var1.getlocal(3));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("inspect").__getattr__("isroutine").__call__(var2, var1.getlocal(3));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("inspect").__getattr__("isgetsetdescriptor").__call__(var2, var1.getlocal(3));
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getglobal("inspect").__getattr__("ismemberdescriptor").__call__(var2, var1.getlocal(3));
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("property"));
                     }
                  }
               }
            }
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(1619);
            var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1620);
            var3 = var1.getlocal(5);
            var3 = var3._iadd(PyString.fromInterned(" object"));
            var1.setlocal(5, var3);
         }
      }

      var1.setline(1621);
      var3 = var1.getlocal(1)._mod(var1.getlocal(5))._add(PyString.fromInterned("\n\n"))._add(var1.getglobal("text").__getattr__("document").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject doc$141(PyFrame var1, ThreadState var2) {
      var1.setline(1624);
      PyString.fromInterned("Display text documentation, given an object or a path to an object.");

      try {
         var1.setline(1626);
         var1.getglobal("pager").__call__(var2, var1.getglobal("render_doc").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("ErrorDuringImport")}))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1628);
         Py.println(var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writedoc$142(PyFrame var1, ThreadState var2) {
      var1.setline(1631);
      PyString.fromInterned("Write HTML documentation to a file in the current directory.");

      PyException var3;
      try {
         var1.setline(1633);
         PyObject var7 = var1.getglobal("resolve").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var5 = var8[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(1634);
         var7 = var1.getglobal("html").__getattr__("page").__call__(var2, var1.getglobal("describe").__call__(var2, var1.getlocal(2)), var1.getglobal("html").__getattr__("document").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(1635);
         var7 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3)._add(PyString.fromInterned(".html")), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(1636);
         var1.getlocal(5).__getattr__("write").__call__(var2, var1.getlocal(4));
         var1.setline(1637);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         var1.setline(1638);
         Py.printComma(PyString.fromInterned("wrote"));
         Py.println(var1.getlocal(3)._add(PyString.fromInterned(".html")));
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("ErrorDuringImport")}))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1640);
         Py.println(var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writedocs$143(PyFrame var1, ThreadState var2) {
      var1.setline(1643);
      PyString.fromInterned("Write out HTML documentation for all modules in a directory tree.");
      var1.setline(1644);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1644);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(1645);
      var3 = var1.getglobal("pkgutil").__getattr__("walk_packages").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(0)})), (PyObject)var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(1645);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1647);
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(1646);
         var1.getglobal("writedoc").__call__(var2, var1.getlocal(4));
      }
   }

   public PyObject Helper$144(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1662);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("and"), PyString.fromInterned("BOOLEAN"), PyString.fromInterned("as"), PyString.fromInterned("with"), PyString.fromInterned("assert"), new PyTuple(new PyObject[]{PyString.fromInterned("assert"), PyString.fromInterned("")}), PyString.fromInterned("break"), new PyTuple(new PyObject[]{PyString.fromInterned("break"), PyString.fromInterned("while for")}), PyString.fromInterned("class"), new PyTuple(new PyObject[]{PyString.fromInterned("class"), PyString.fromInterned("CLASSES SPECIALMETHODS")}), PyString.fromInterned("continue"), new PyTuple(new PyObject[]{PyString.fromInterned("continue"), PyString.fromInterned("while for")}), PyString.fromInterned("def"), new PyTuple(new PyObject[]{PyString.fromInterned("function"), PyString.fromInterned("")}), PyString.fromInterned("del"), new PyTuple(new PyObject[]{PyString.fromInterned("del"), PyString.fromInterned("BASICMETHODS")}), PyString.fromInterned("elif"), PyString.fromInterned("if"), PyString.fromInterned("else"), new PyTuple(new PyObject[]{PyString.fromInterned("else"), PyString.fromInterned("while for")}), PyString.fromInterned("except"), PyString.fromInterned("try"), PyString.fromInterned("exec"), new PyTuple(new PyObject[]{PyString.fromInterned("exec"), PyString.fromInterned("")}), PyString.fromInterned("finally"), PyString.fromInterned("try"), PyString.fromInterned("for"), new PyTuple(new PyObject[]{PyString.fromInterned("for"), PyString.fromInterned("break continue while")}), PyString.fromInterned("from"), PyString.fromInterned("import"), PyString.fromInterned("global"), new PyTuple(new PyObject[]{PyString.fromInterned("global"), PyString.fromInterned("NAMESPACES")}), PyString.fromInterned("if"), new PyTuple(new PyObject[]{PyString.fromInterned("if"), PyString.fromInterned("TRUTHVALUE")}), PyString.fromInterned("import"), new PyTuple(new PyObject[]{PyString.fromInterned("import"), PyString.fromInterned("MODULES")}), PyString.fromInterned("in"), new PyTuple(new PyObject[]{PyString.fromInterned("in"), PyString.fromInterned("SEQUENCEMETHODS2")}), PyString.fromInterned("is"), PyString.fromInterned("COMPARISON"), PyString.fromInterned("lambda"), new PyTuple(new PyObject[]{PyString.fromInterned("lambda"), PyString.fromInterned("FUNCTIONS")}), PyString.fromInterned("not"), PyString.fromInterned("BOOLEAN"), PyString.fromInterned("or"), PyString.fromInterned("BOOLEAN"), PyString.fromInterned("pass"), new PyTuple(new PyObject[]{PyString.fromInterned("pass"), PyString.fromInterned("")}), PyString.fromInterned("print"), new PyTuple(new PyObject[]{PyString.fromInterned("print"), PyString.fromInterned("")}), PyString.fromInterned("raise"), new PyTuple(new PyObject[]{PyString.fromInterned("raise"), PyString.fromInterned("EXCEPTIONS")}), PyString.fromInterned("return"), new PyTuple(new PyObject[]{PyString.fromInterned("return"), PyString.fromInterned("FUNCTIONS")}), PyString.fromInterned("try"), new PyTuple(new PyObject[]{PyString.fromInterned("try"), PyString.fromInterned("EXCEPTIONS")}), PyString.fromInterned("while"), new PyTuple(new PyObject[]{PyString.fromInterned("while"), PyString.fromInterned("break continue if TRUTHVALUE")}), PyString.fromInterned("with"), new PyTuple(new PyObject[]{PyString.fromInterned("with"), PyString.fromInterned("CONTEXTMANAGERS EXCEPTIONS yield")}), PyString.fromInterned("yield"), new PyTuple(new PyObject[]{PyString.fromInterned("yield"), PyString.fromInterned("")})});
      var1.setlocal("keywords", var3);
      var3 = null;
      var1.setline(1697);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("STRINGS"), new PyTuple(new PyObject[]{PyString.fromInterned("'"), PyString.fromInterned("'''"), PyString.fromInterned("r'"), PyString.fromInterned("u'"), PyString.fromInterned("\"\"\""), PyString.fromInterned("\""), PyString.fromInterned("r\""), PyString.fromInterned("u\"")}), PyString.fromInterned("OPERATORS"), new PyTuple(new PyObject[]{PyString.fromInterned("+"), PyString.fromInterned("-"), PyString.fromInterned("*"), PyString.fromInterned("**"), PyString.fromInterned("/"), PyString.fromInterned("//"), PyString.fromInterned("%"), PyString.fromInterned("<<"), PyString.fromInterned(">>"), PyString.fromInterned("&"), PyString.fromInterned("|"), PyString.fromInterned("^"), PyString.fromInterned("~"), PyString.fromInterned("<"), PyString.fromInterned(">"), PyString.fromInterned("<="), PyString.fromInterned(">="), PyString.fromInterned("=="), PyString.fromInterned("!="), PyString.fromInterned("<>")}), PyString.fromInterned("COMPARISON"), new PyTuple(new PyObject[]{PyString.fromInterned("<"), PyString.fromInterned(">"), PyString.fromInterned("<="), PyString.fromInterned(">="), PyString.fromInterned("=="), PyString.fromInterned("!="), PyString.fromInterned("<>")}), PyString.fromInterned("UNARY"), new PyTuple(new PyObject[]{PyString.fromInterned("-"), PyString.fromInterned("~")}), PyString.fromInterned("AUGMENTEDASSIGNMENT"), new PyTuple(new PyObject[]{PyString.fromInterned("+="), PyString.fromInterned("-="), PyString.fromInterned("*="), PyString.fromInterned("/="), PyString.fromInterned("%="), PyString.fromInterned("&="), PyString.fromInterned("|="), PyString.fromInterned("^="), PyString.fromInterned("<<="), PyString.fromInterned(">>="), PyString.fromInterned("**="), PyString.fromInterned("//=")}), PyString.fromInterned("BITWISE"), new PyTuple(new PyObject[]{PyString.fromInterned("<<"), PyString.fromInterned(">>"), PyString.fromInterned("&"), PyString.fromInterned("|"), PyString.fromInterned("^"), PyString.fromInterned("~")}), PyString.fromInterned("COMPLEX"), new PyTuple(new PyObject[]{PyString.fromInterned("j"), PyString.fromInterned("J")})});
      var1.setlocal("_symbols_inverse", var3);
      var3 = null;
      var1.setline(1708);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("%"), PyString.fromInterned("OPERATORS FORMATTING"), PyString.fromInterned("**"), PyString.fromInterned("POWER"), PyString.fromInterned(","), PyString.fromInterned("TUPLES LISTS FUNCTIONS"), PyString.fromInterned("."), PyString.fromInterned("ATTRIBUTES FLOAT MODULES OBJECTS"), PyString.fromInterned("..."), PyString.fromInterned("ELLIPSIS"), PyString.fromInterned(":"), PyString.fromInterned("SLICINGS DICTIONARYLITERALS"), PyString.fromInterned("@"), PyString.fromInterned("def class"), PyString.fromInterned("\\"), PyString.fromInterned("STRINGS"), PyString.fromInterned("_"), PyString.fromInterned("PRIVATENAMES"), PyString.fromInterned("__"), PyString.fromInterned("PRIVATENAMES SPECIALMETHODS"), PyString.fromInterned("`"), PyString.fromInterned("BACKQUOTES"), PyString.fromInterned("("), PyString.fromInterned("TUPLES FUNCTIONS CALLS"), PyString.fromInterned(")"), PyString.fromInterned("TUPLES FUNCTIONS CALLS"), PyString.fromInterned("["), PyString.fromInterned("LISTS SUBSCRIPTS SLICINGS"), PyString.fromInterned("]"), PyString.fromInterned("LISTS SUBSCRIPTS SLICINGS")});
      var1.setlocal("symbols", var3);
      var3 = null;
      var1.setline(1725);
      PyObject var8 = var1.getname("_symbols_inverse").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(1725);
         PyObject var4 = var8.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(1732);
            var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("TYPES"), new PyTuple(new PyObject[]{PyString.fromInterned("types"), PyString.fromInterned("STRINGS UNICODE NUMBERS SEQUENCES MAPPINGS FUNCTIONS CLASSES MODULES FILES inspect")}), PyString.fromInterned("STRINGS"), new PyTuple(new PyObject[]{PyString.fromInterned("strings"), PyString.fromInterned("str UNICODE SEQUENCES STRINGMETHODS FORMATTING TYPES")}), PyString.fromInterned("STRINGMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("string-methods"), PyString.fromInterned("STRINGS FORMATTING")}), PyString.fromInterned("FORMATTING"), new PyTuple(new PyObject[]{PyString.fromInterned("formatstrings"), PyString.fromInterned("OPERATORS")}), PyString.fromInterned("UNICODE"), new PyTuple(new PyObject[]{PyString.fromInterned("strings"), PyString.fromInterned("encodings unicode SEQUENCES STRINGMETHODS FORMATTING TYPES")}), PyString.fromInterned("NUMBERS"), new PyTuple(new PyObject[]{PyString.fromInterned("numbers"), PyString.fromInterned("INTEGER FLOAT COMPLEX TYPES")}), PyString.fromInterned("INTEGER"), new PyTuple(new PyObject[]{PyString.fromInterned("integers"), PyString.fromInterned("int range")}), PyString.fromInterned("FLOAT"), new PyTuple(new PyObject[]{PyString.fromInterned("floating"), PyString.fromInterned("float math")}), PyString.fromInterned("COMPLEX"), new PyTuple(new PyObject[]{PyString.fromInterned("imaginary"), PyString.fromInterned("complex cmath")}), PyString.fromInterned("SEQUENCES"), new PyTuple(new PyObject[]{PyString.fromInterned("typesseq"), PyString.fromInterned("STRINGMETHODS FORMATTING xrange LISTS")}), PyString.fromInterned("MAPPINGS"), PyString.fromInterned("DICTIONARIES"), PyString.fromInterned("FUNCTIONS"), new PyTuple(new PyObject[]{PyString.fromInterned("typesfunctions"), PyString.fromInterned("def TYPES")}), PyString.fromInterned("METHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("typesmethods"), PyString.fromInterned("class def CLASSES TYPES")}), PyString.fromInterned("CODEOBJECTS"), new PyTuple(new PyObject[]{PyString.fromInterned("bltin-code-objects"), PyString.fromInterned("compile FUNCTIONS TYPES")}), PyString.fromInterned("TYPEOBJECTS"), new PyTuple(new PyObject[]{PyString.fromInterned("bltin-type-objects"), PyString.fromInterned("types TYPES")}), PyString.fromInterned("FRAMEOBJECTS"), PyString.fromInterned("TYPES"), PyString.fromInterned("TRACEBACKS"), PyString.fromInterned("TYPES"), PyString.fromInterned("NONE"), new PyTuple(new PyObject[]{PyString.fromInterned("bltin-null-object"), PyString.fromInterned("")}), PyString.fromInterned("ELLIPSIS"), new PyTuple(new PyObject[]{PyString.fromInterned("bltin-ellipsis-object"), PyString.fromInterned("SLICINGS")}), PyString.fromInterned("FILES"), new PyTuple(new PyObject[]{PyString.fromInterned("bltin-file-objects"), PyString.fromInterned("")}), PyString.fromInterned("SPECIALATTRIBUTES"), new PyTuple(new PyObject[]{PyString.fromInterned("specialattrs"), PyString.fromInterned("")}), PyString.fromInterned("CLASSES"), new PyTuple(new PyObject[]{PyString.fromInterned("types"), PyString.fromInterned("class SPECIALMETHODS PRIVATENAMES")}), PyString.fromInterned("MODULES"), new PyTuple(new PyObject[]{PyString.fromInterned("typesmodules"), PyString.fromInterned("import")}), PyString.fromInterned("PACKAGES"), PyString.fromInterned("import"), PyString.fromInterned("EXPRESSIONS"), new PyTuple(new PyObject[]{PyString.fromInterned("operator-summary"), PyString.fromInterned("lambda or and not in is BOOLEAN COMPARISON BITWISE SHIFTING BINARY FORMATTING POWER UNARY ATTRIBUTES SUBSCRIPTS SLICINGS CALLS TUPLES LISTS DICTIONARIES BACKQUOTES")}), PyString.fromInterned("OPERATORS"), PyString.fromInterned("EXPRESSIONS"), PyString.fromInterned("PRECEDENCE"), PyString.fromInterned("EXPRESSIONS"), PyString.fromInterned("OBJECTS"), new PyTuple(new PyObject[]{PyString.fromInterned("objects"), PyString.fromInterned("TYPES")}), PyString.fromInterned("SPECIALMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("specialnames"), PyString.fromInterned("BASICMETHODS ATTRIBUTEMETHODS CALLABLEMETHODS SEQUENCEMETHODS1 MAPPINGMETHODS SEQUENCEMETHODS2 NUMBERMETHODS CLASSES")}), PyString.fromInterned("BASICMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("customization"), PyString.fromInterned("cmp hash repr str SPECIALMETHODS")}), PyString.fromInterned("ATTRIBUTEMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("attribute-access"), PyString.fromInterned("ATTRIBUTES SPECIALMETHODS")}), PyString.fromInterned("CALLABLEMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("callable-types"), PyString.fromInterned("CALLS SPECIALMETHODS")}), PyString.fromInterned("SEQUENCEMETHODS1"), new PyTuple(new PyObject[]{PyString.fromInterned("sequence-types"), PyString.fromInterned("SEQUENCES SEQUENCEMETHODS2 SPECIALMETHODS")}), PyString.fromInterned("SEQUENCEMETHODS2"), new PyTuple(new PyObject[]{PyString.fromInterned("sequence-methods"), PyString.fromInterned("SEQUENCES SEQUENCEMETHODS1 SPECIALMETHODS")}), PyString.fromInterned("MAPPINGMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("sequence-types"), PyString.fromInterned("MAPPINGS SPECIALMETHODS")}), PyString.fromInterned("NUMBERMETHODS"), new PyTuple(new PyObject[]{PyString.fromInterned("numeric-types"), PyString.fromInterned("NUMBERS AUGMENTEDASSIGNMENT SPECIALMETHODS")}), PyString.fromInterned("EXECUTION"), new PyTuple(new PyObject[]{PyString.fromInterned("execmodel"), PyString.fromInterned("NAMESPACES DYNAMICFEATURES EXCEPTIONS")}), PyString.fromInterned("NAMESPACES"), new PyTuple(new PyObject[]{PyString.fromInterned("naming"), PyString.fromInterned("global ASSIGNMENT DELETION DYNAMICFEATURES")}), PyString.fromInterned("DYNAMICFEATURES"), new PyTuple(new PyObject[]{PyString.fromInterned("dynamic-features"), PyString.fromInterned("")}), PyString.fromInterned("SCOPING"), PyString.fromInterned("NAMESPACES"), PyString.fromInterned("FRAMES"), PyString.fromInterned("NAMESPACES"), PyString.fromInterned("EXCEPTIONS"), new PyTuple(new PyObject[]{PyString.fromInterned("exceptions"), PyString.fromInterned("try except finally raise")}), PyString.fromInterned("COERCIONS"), new PyTuple(new PyObject[]{PyString.fromInterned("coercion-rules"), PyString.fromInterned("CONVERSIONS")}), PyString.fromInterned("CONVERSIONS"), new PyTuple(new PyObject[]{PyString.fromInterned("conversions"), PyString.fromInterned("COERCIONS")}), PyString.fromInterned("IDENTIFIERS"), new PyTuple(new PyObject[]{PyString.fromInterned("identifiers"), PyString.fromInterned("keywords SPECIALIDENTIFIERS")}), PyString.fromInterned("SPECIALIDENTIFIERS"), new PyTuple(new PyObject[]{PyString.fromInterned("id-classes"), PyString.fromInterned("")}), PyString.fromInterned("PRIVATENAMES"), new PyTuple(new PyObject[]{PyString.fromInterned("atom-identifiers"), PyString.fromInterned("")}), PyString.fromInterned("LITERALS"), new PyTuple(new PyObject[]{PyString.fromInterned("atom-literals"), PyString.fromInterned("STRINGS BACKQUOTES NUMBERS TUPLELITERALS LISTLITERALS DICTIONARYLITERALS")}), PyString.fromInterned("TUPLES"), PyString.fromInterned("SEQUENCES"), PyString.fromInterned("TUPLELITERALS"), new PyTuple(new PyObject[]{PyString.fromInterned("exprlists"), PyString.fromInterned("TUPLES LITERALS")}), PyString.fromInterned("LISTS"), new PyTuple(new PyObject[]{PyString.fromInterned("typesseq-mutable"), PyString.fromInterned("LISTLITERALS")}), PyString.fromInterned("LISTLITERALS"), new PyTuple(new PyObject[]{PyString.fromInterned("lists"), PyString.fromInterned("LISTS LITERALS")}), PyString.fromInterned("DICTIONARIES"), new PyTuple(new PyObject[]{PyString.fromInterned("typesmapping"), PyString.fromInterned("DICTIONARYLITERALS")}), PyString.fromInterned("DICTIONARYLITERALS"), new PyTuple(new PyObject[]{PyString.fromInterned("dict"), PyString.fromInterned("DICTIONARIES LITERALS")}), PyString.fromInterned("BACKQUOTES"), new PyTuple(new PyObject[]{PyString.fromInterned("string-conversions"), PyString.fromInterned("repr str STRINGS LITERALS")}), PyString.fromInterned("ATTRIBUTES"), new PyTuple(new PyObject[]{PyString.fromInterned("attribute-references"), PyString.fromInterned("getattr hasattr setattr ATTRIBUTEMETHODS")}), PyString.fromInterned("SUBSCRIPTS"), new PyTuple(new PyObject[]{PyString.fromInterned("subscriptions"), PyString.fromInterned("SEQUENCEMETHODS1")}), PyString.fromInterned("SLICINGS"), new PyTuple(new PyObject[]{PyString.fromInterned("slicings"), PyString.fromInterned("SEQUENCEMETHODS2")}), PyString.fromInterned("CALLS"), new PyTuple(new PyObject[]{PyString.fromInterned("calls"), PyString.fromInterned("EXPRESSIONS")}), PyString.fromInterned("POWER"), new PyTuple(new PyObject[]{PyString.fromInterned("power"), PyString.fromInterned("EXPRESSIONS")}), PyString.fromInterned("UNARY"), new PyTuple(new PyObject[]{PyString.fromInterned("unary"), PyString.fromInterned("EXPRESSIONS")}), PyString.fromInterned("BINARY"), new PyTuple(new PyObject[]{PyString.fromInterned("binary"), PyString.fromInterned("EXPRESSIONS")}), PyString.fromInterned("SHIFTING"), new PyTuple(new PyObject[]{PyString.fromInterned("shifting"), PyString.fromInterned("EXPRESSIONS")}), PyString.fromInterned("BITWISE"), new PyTuple(new PyObject[]{PyString.fromInterned("bitwise"), PyString.fromInterned("EXPRESSIONS")}), PyString.fromInterned("COMPARISON"), new PyTuple(new PyObject[]{PyString.fromInterned("comparisons"), PyString.fromInterned("EXPRESSIONS BASICMETHODS")}), PyString.fromInterned("BOOLEAN"), new PyTuple(new PyObject[]{PyString.fromInterned("booleans"), PyString.fromInterned("EXPRESSIONS TRUTHVALUE")}), PyString.fromInterned("ASSERTION"), PyString.fromInterned("assert"), PyString.fromInterned("ASSIGNMENT"), new PyTuple(new PyObject[]{PyString.fromInterned("assignment"), PyString.fromInterned("AUGMENTEDASSIGNMENT")}), PyString.fromInterned("AUGMENTEDASSIGNMENT"), new PyTuple(new PyObject[]{PyString.fromInterned("augassign"), PyString.fromInterned("NUMBERMETHODS")}), PyString.fromInterned("DELETION"), PyString.fromInterned("del"), PyString.fromInterned("PRINTING"), PyString.fromInterned("print"), PyString.fromInterned("RETURNING"), PyString.fromInterned("return"), PyString.fromInterned("IMPORTING"), PyString.fromInterned("import"), PyString.fromInterned("CONDITIONAL"), PyString.fromInterned("if"), PyString.fromInterned("LOOPING"), new PyTuple(new PyObject[]{PyString.fromInterned("compound"), PyString.fromInterned("for while break continue")}), PyString.fromInterned("TRUTHVALUE"), new PyTuple(new PyObject[]{PyString.fromInterned("truth"), PyString.fromInterned("if while and or not BASICMETHODS")}), PyString.fromInterned("DEBUGGING"), new PyTuple(new PyObject[]{PyString.fromInterned("debugger"), PyString.fromInterned("pdb")}), PyString.fromInterned("CONTEXTMANAGERS"), new PyTuple(new PyObject[]{PyString.fromInterned("context-managers"), PyString.fromInterned("with")})});
            var1.setlocal("topics", var3);
            var3 = null;
            var1.setline(1826);
            PyObject[] var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
            PyFunction var11 = new PyFunction(var1.f_globals, var10, __init__$145, (PyObject)null);
            var1.setlocal("__init__", var11);
            var3 = null;
            var1.setline(1830);
            var10000 = var1.getname("property");
            var1.setline(1830);
            var10 = Py.EmptyObjects;
            var8 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var10, f$146)));
            var1.setlocal("input", var8);
            var3 = null;
            var1.setline(1831);
            var10000 = var1.getname("property");
            var1.setline(1831);
            var10 = Py.EmptyObjects;
            var8 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var10, f$147)));
            var1.setlocal("output", var8);
            var3 = null;
            var1.setline(1833);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, __repr__$148, (PyObject)null);
            var1.setlocal("__repr__", var11);
            var3 = null;
            var1.setline(1839);
            var8 = var1.getname("object").__call__(var2);
            var1.setlocal("_GoInteractive", var8);
            var3 = null;
            var1.setline(1840);
            var10 = new PyObject[]{var1.getname("_GoInteractive")};
            var11 = new PyFunction(var1.f_globals, var10, __call__$149, (PyObject)null);
            var1.setlocal("__call__", var11);
            var3 = null;
            var1.setline(1853);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, interact$150, (PyObject)null);
            var1.setlocal("interact", var11);
            var3 = null;
            var1.setline(1865);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, getline$151, PyString.fromInterned("Read one line, using raw_input when available."));
            var1.setlocal("getline", var11);
            var3 = null;
            var1.setline(1874);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, help$152, (PyObject)null);
            var1.setlocal("help", var11);
            var3 = null;
            var1.setline(1892);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, intro$153, (PyObject)null);
            var1.setlocal("intro", var11);
            var3 = null;
            var1.setline(1909);
            var10 = new PyObject[]{Py.newInteger(4), Py.newInteger(80)};
            var11 = new PyFunction(var1.f_globals, var10, list$154, (PyObject)null);
            var1.setlocal("list", var11);
            var3 = null;
            var1.setline(1923);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, listkeywords$155, (PyObject)null);
            var1.setlocal("listkeywords", var11);
            var3 = null;
            var1.setline(1930);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, listsymbols$156, (PyObject)null);
            var1.setlocal("listsymbols", var11);
            var3 = null;
            var1.setline(1938);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, listtopics$157, (PyObject)null);
            var1.setlocal("listtopics", var11);
            var3 = null;
            var1.setline(1945);
            var10 = new PyObject[]{PyString.fromInterned("")};
            var11 = new PyFunction(var1.f_globals, var10, showtopic$158, (PyObject)null);
            var1.setlocal("showtopic", var11);
            var3 = null;
            var1.setline(1977);
            var10 = Py.EmptyObjects;
            var11 = new PyFunction(var1.f_globals, var10, showsymbol$159, (PyObject)null);
            var1.setlocal("showsymbol", var11);
            var3 = null;
            var1.setline(1982);
            var10 = new PyObject[]{PyString.fromInterned("")};
            var11 = new PyFunction(var1.f_globals, var10, listmodules$160, (PyObject)null);
            var1.setlocal("listmodules", var11);
            var3 = null;
            return var1.getf_locals();
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal("topic", var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal("symbols_", var6);
         var6 = null;
         var1.setline(1726);
         PyObject var9 = var1.getname("symbols_").__iter__();

         while(true) {
            var1.setline(1726);
            var6 = var9.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal("symbol", var6);
            var1.setline(1727);
            PyObject var7 = var1.getname("symbols").__getattr__("get").__call__(var2, var1.getname("symbol"), var1.getname("topic"));
            var1.setlocal("topics", var7);
            var7 = null;
            var1.setline(1728);
            var7 = var1.getname("topic");
            var10000 = var7._notin(var1.getname("topics"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1729);
               var7 = var1.getname("topics")._add(PyString.fromInterned(" "))._add(var1.getname("topic"));
               var1.setlocal("topics", var7);
               var7 = null;
            }

            var1.setline(1730);
            var7 = var1.getname("topics");
            var1.getname("symbols").__setitem__(var1.getname("symbol"), var7);
            var7 = null;
         }
      }
   }

   public PyObject __init__$145(PyFrame var1, ThreadState var2) {
      var1.setline(1827);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_input", var3);
      var3 = null;
      var1.setline(1828);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_output", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$146(PyFrame var1, ThreadState var2) {
      var1.setline(1830);
      PyObject var10000 = var1.getlocal(0).__getattr__("_input");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("stdin");
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$147(PyFrame var1, ThreadState var2) {
      var1.setline(1831);
      PyObject var10000 = var1.getlocal(0).__getattr__("_output");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("stdout");
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$148(PyFrame var1, ThreadState var2) {
      var1.setline(1834);
      PyObject var3 = var1.getglobal("inspect").__getattr__("stack").__call__(var2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(3));
      PyObject var10000 = var3._eq(PyString.fromInterned("?"));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(1835);
         var1.getlocal(0).__call__(var2);
         var1.setline(1836);
         var4 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1837);
         var4 = PyString.fromInterned("<pydoc.Helper instance>");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject __call__$149(PyFrame var1, ThreadState var2) {
      var1.setline(1841);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getlocal(0).__getattr__("_GoInteractive"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1842);
         var1.getlocal(0).__getattr__("help").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(1844);
         var1.getlocal(0).__getattr__("intro").__call__(var2);
         var1.setline(1845);
         var1.getlocal(0).__getattr__("interact").__call__(var2);
         var1.setline(1846);
         var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nYou are now leaving help and returning to the Python interpreter.\nIf you want to ask for help on a particular object directly from the\ninterpreter, you can type \"help(object)\".  Executing \"help('string')\"\nhas the same effect as typing a particular string at the help> prompt.\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject interact$150(PyFrame var1, ThreadState var2) {
      var1.setline(1854);
      var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));

      while(true) {
         var1.setline(1855);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         PyException var3;
         PyObject var5;
         try {
            var1.setline(1857);
            var5 = var1.getlocal(0).__getattr__("getline").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("help> "));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(1858);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               break;
            }
         } catch (Throwable var4) {
            var3 = Py.setException(var4, var1);
            if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getglobal("EOFError")}))) {
               break;
            }

            throw var3;
         }

         var1.setline(1861);
         PyObject var10000 = var1.getglobal("strip");
         PyObject var10002 = var1.getglobal("replace");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("\""), PyString.fromInterned(""), PyString.fromInterned("'"), PyString.fromInterned("")};
         var5 = var10000.__call__(var2, var10002.__call__(var2, var6));
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(1862);
         var5 = var1.getglobal("lower").__call__(var2, var1.getlocal(1));
         var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("q"), PyString.fromInterned("quit")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(1863);
         var1.getlocal(0).__getattr__("help").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getline$151(PyFrame var1, ThreadState var2) {
      var1.setline(1866);
      PyString.fromInterned("Read one line, using raw_input when available.");
      var1.setline(1867);
      PyObject var3 = var1.getlocal(0).__getattr__("input");
      PyObject var10000 = var3._is(var1.getglobal("sys").__getattr__("stdin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1868);
         var3 = var1.getglobal("raw_input").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1870);
         var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, var1.getlocal(1));
         var1.setline(1871);
         var1.getlocal(0).__getattr__("output").__getattr__("flush").__call__(var2);
         var1.setline(1872);
         var3 = var1.getlocal(0).__getattr__("input").__getattr__("readline").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject help$152(PyFrame var1, ThreadState var2) {
      var1.setline(1875);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1876);
         var3 = var1.getlocal(1).__getattr__("strip").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1877);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("help"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1877);
            var1.getlocal(0).__getattr__("intro").__call__(var2);
         } else {
            var1.setline(1878);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(PyString.fromInterned("keywords"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1878);
               var1.getlocal(0).__getattr__("listkeywords").__call__(var2);
            } else {
               var1.setline(1879);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(PyString.fromInterned("symbols"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1879);
                  var1.getlocal(0).__getattr__("listsymbols").__call__(var2);
               } else {
                  var1.setline(1880);
                  var3 = var1.getlocal(1);
                  var10000 = var3._eq(PyString.fromInterned("topics"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1880);
                     var1.getlocal(0).__getattr__("listtopics").__call__(var2);
                  } else {
                     var1.setline(1881);
                     var3 = var1.getlocal(1);
                     var10000 = var3._eq(PyString.fromInterned("modules"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1881);
                        var1.getlocal(0).__getattr__("listmodules").__call__(var2);
                     } else {
                        var1.setline(1882);
                        var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null);
                        var10000 = var3._eq(PyString.fromInterned("modules "));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1883);
                           var1.getlocal(0).__getattr__("listmodules").__call__(var2, var1.getglobal("split").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(1)));
                        } else {
                           var1.setline(1884);
                           var3 = var1.getlocal(1);
                           var10000 = var3._in(var1.getlocal(0).__getattr__("symbols"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1884);
                              var1.getlocal(0).__getattr__("showsymbol").__call__(var2, var1.getlocal(1));
                           } else {
                              var1.setline(1885);
                              var3 = var1.getlocal(1);
                              var10000 = var3._in(var1.getlocal(0).__getattr__("keywords"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(1885);
                                 var1.getlocal(0).__getattr__("showtopic").__call__(var2, var1.getlocal(1));
                              } else {
                                 var1.setline(1886);
                                 var3 = var1.getlocal(1);
                                 var10000 = var3._in(var1.getlocal(0).__getattr__("topics"));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1886);
                                    var1.getlocal(0).__getattr__("showtopic").__call__(var2, var1.getlocal(1));
                                 } else {
                                    var1.setline(1887);
                                    if (var1.getlocal(1).__nonzero__()) {
                                       var1.setline(1887);
                                       var1.getglobal("doc").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Help on %s:"));
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } else {
         var1.setline(1888);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Helper")).__nonzero__()) {
            var1.setline(1888);
            var1.getlocal(0).__call__(var2);
         } else {
            var1.setline(1889);
            var1.getglobal("doc").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Help on %s:"));
         }
      }

      var1.setline(1890);
      var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject intro$153(PyFrame var1, ThreadState var2) {
      var1.setline(1893);
      var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, PyString.fromInterned("\nWelcome to Python %s!  This is the online help utility.\n\nIf this is your first time using Python, you should definitely check out\nthe tutorial on the Internet at http://docs.python.org/%s/tutorial/.\n\nEnter the name of any module, keyword, or topic to get help on writing\nPython programs and using Python modules.  To quit this help utility and\nreturn to the interpreter, just type \"quit\".\n\nTo get a list of available modules, keywords, or topics, type \"modules\",\n\"keywords\", or \"topics\".  Each module also comes with a one-line summary\nof what it does; to list the modules whose summaries contain a given word\nsuch as \"spam\", type \"modules spam\".\n")._mod(var1.getglobal("tuple").__call__(var2, (new PyList(new PyObject[]{var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null)}))._mul(Py.newInteger(2)))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject list$154(PyFrame var1, ThreadState var2) {
      var1.setline(1910);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1911);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(1912);
      var3 = var1.getlocal(3)._div(var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1913);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._add(var1.getlocal(2))._sub(Py.newInteger(1))._div(var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1914);
      var3 = var1.getglobal("range").__call__(var2, var1.getlocal(5)).__iter__();

      while(true) {
         var1.setline(1914);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(1915);
         PyObject var5 = var1.getglobal("range").__call__(var2, var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(1915);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(1921);
               var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               break;
            }

            var1.setlocal(7, var6);
            var1.setline(1916);
            PyObject var7 = var1.getlocal(7)._mul(var1.getlocal(5))._add(var1.getlocal(6));
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(1917);
            var7 = var1.getlocal(8);
            PyObject var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1918);
               var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(8)));
               var1.setline(1919);
               var7 = var1.getlocal(7);
               var10000 = var7._lt(var1.getlocal(2)._sub(Py.newInteger(1)));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1920);
                  var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, PyString.fromInterned(" ")._add(PyString.fromInterned(" ")._mul(var1.getlocal(4)._sub(Py.newInteger(1))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(8)))))));
               }
            }
         }
      }
   }

   public PyObject listkeywords$155(PyFrame var1, ThreadState var2) {
      var1.setline(1924);
      var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nHere is a list of the Python keywords.  Enter any keyword to get more help.\n\n"));
      var1.setline(1928);
      var1.getlocal(0).__getattr__("list").__call__(var2, var1.getlocal(0).__getattr__("keywords").__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listsymbols$156(PyFrame var1, ThreadState var2) {
      var1.setline(1931);
      var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nHere is a list of the punctuation symbols which Python assigns special meaning\nto. Enter any symbol to get more help.\n\n"));
      var1.setline(1936);
      var1.getlocal(0).__getattr__("list").__call__(var2, var1.getlocal(0).__getattr__("symbols").__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listtopics$157(PyFrame var1, ThreadState var2) {
      var1.setline(1939);
      var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nHere is a list of available topics.  Enter any topic name to get more help.\n\n"));
      var1.setline(1943);
      var1.getlocal(0).__getattr__("list").__call__(var2, var1.getlocal(0).__getattr__("topics").__getattr__("keys").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject showtopic$158(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var9;
      try {
         var1.setline(1947);
         var9 = imp.importOne("pydoc_data.topics", var1, -1);
         var1.setlocal(3, var9);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(1949);
            var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nSorry, topic and keyword documentation is not available because the\nmodule \"pydoc_data.topics\" could not be found.\n"));
            var1.setline(1953);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(1954);
      var9 = var1.getlocal(0).__getattr__("topics").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("keywords").__getattr__("get").__call__(var2, var1.getlocal(1)));
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(1955);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(1956);
         var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, PyString.fromInterned("no documentation found for %s\n")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
         var1.setline(1957);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1958);
         var9 = var1.getglobal("type").__call__(var2, var1.getlocal(4));
         PyObject var10000 = var9._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1959);
            var9 = var1.getlocal(0).__getattr__("showtopic").__call__(var2, var1.getlocal(4), var1.getlocal(2));
            var1.f_lasti = -1;
            return var9;
         } else {
            var1.setline(1961);
            PyObject var4 = var1.getlocal(4);
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;

            try {
               var1.setline(1963);
               var4 = var1.getlocal(3).__getattr__("topics").__getattr__("topics").__getitem__(var1.getlocal(5));
               var1.setlocal(7, var4);
               var4 = null;
            } catch (Throwable var8) {
               PyException var10 = Py.setException(var8, var1);
               if (var10.match(var1.getglobal("KeyError"))) {
                  var1.setline(1965);
                  var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, PyString.fromInterned("no documentation found for %s\n")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
                  var1.setline(1966);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               throw var10;
            }

            var1.setline(1967);
            var1.getglobal("pager").__call__(var2, var1.getglobal("strip").__call__(var2, var1.getlocal(7))._add(PyString.fromInterned("\n")));
            var1.setline(1968);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1969);
               Object var11 = var1.getlocal(6);
               if (!((PyObject)var11).__nonzero__()) {
                  var11 = PyString.fromInterned("");
               }

               var4 = ((PyObject)var11)._add(PyString.fromInterned(" "))._add(var1.getlocal(2));
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(1970);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1971);
               var4 = imp.importOne("StringIO", var1, -1);
               var1.setlocal(8, var4);
               var4 = null;
               var4 = imp.importOne("formatter", var1, -1);
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(1972);
               var4 = var1.getlocal(8).__getattr__("StringIO").__call__(var2);
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(1973);
               var1.getlocal(9).__getattr__("DumbWriter").__call__(var2, var1.getlocal(10)).__getattr__("send_flowing_data").__call__(var2, PyString.fromInterned("Related help topics: ")._add(var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("split").__call__(var2, var1.getlocal(6)), (PyObject)PyString.fromInterned(", ")))._add(PyString.fromInterned("\n")));
               var1.setline(1975);
               var1.getlocal(0).__getattr__("output").__getattr__("write").__call__(var2, PyString.fromInterned("\n%s\n")._mod(var1.getlocal(10).__getattr__("getvalue").__call__(var2)));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject showsymbol$159(PyFrame var1, ThreadState var2) {
      var1.setline(1978);
      PyObject var3 = var1.getlocal(0).__getattr__("symbols").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1979);
      var3 = var1.getlocal(2).__getattr__("partition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
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
      var1.setline(1980);
      var1.getlocal(0).__getattr__("showtopic").__call__(var2, var1.getlocal(3), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listmodules$160(PyFrame var1, ThreadState var2) {
      var1.setline(1983);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1984);
         var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nHere is a list of matching modules.  Enter any module name to get more help.\n\n"));
         var1.setline(1988);
         var1.getglobal("apropos").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(1990);
         var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nPlease wait a moment while I gather a list of all available modules...\n\n"));
         var1.setline(1994);
         PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1995);
         PyObject[] var5 = new PyObject[]{var1.getlocal(2)};
         PyFunction var6 = new PyFunction(var1.f_globals, var5, callback$161, (PyObject)null);
         var1.setderef(0, var6);
         var3 = null;
         var1.setline(2000);
         var5 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var5;
         PyCode var10004 = onerror$162;
         var5 = new PyObject[]{var1.getclosure(0)};
         var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(2002);
         PyObject var10000 = var1.getglobal("ModuleScanner").__call__(var2).__getattr__("run");
         var5 = new PyObject[]{var1.getderef(0), var1.getlocal(3)};
         String[] var4 = new String[]{"onerror"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(2003);
         var1.getlocal(0).__getattr__("list").__call__(var2, var1.getlocal(2).__getattr__("keys").__call__(var2));
         var1.setline(2004);
         var1.getlocal(0).__getattr__("output").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nEnter any module name to get more help.  Or, type \"modules spam\" to search\nfor modules whose descriptions contain the word \"spam\".\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject callback$161(PyFrame var1, ThreadState var2) {
      var1.setline(1996);
      PyObject var10000 = var1.getlocal(1);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(-9), (PyObject)null, (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned(".__init__"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1997);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null)._add(PyString.fromInterned(" (package)"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1998);
      var3 = var1.getglobal("find").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("."));
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1999);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(3).__setitem__((PyObject)var1.getlocal(1), var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject onerror$162(PyFrame var1, ThreadState var2) {
      var1.setline(2001);
      var1.getderef(0).__call__(var2, var1.getglobal("None"), var1.getlocal(0), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Scanner$163(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A generic tree iterator."));
      var1.setline(2012);
      PyString.fromInterned("A generic tree iterator.");
      var1.setline(2013);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$164, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2019);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$165, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$164(PyFrame var1, ThreadState var2) {
      var1.setline(2014);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("roots", var3);
      var3 = null;
      var1.setline(2015);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"state", var4);
      var3 = null;
      var1.setline(2016);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("children", var3);
      var3 = null;
      var1.setline(2017);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("descendp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject next$165(PyFrame var1, ThreadState var2) {
      var1.setline(2020);
      PyObject var3;
      PyObject var4;
      if (var1.getlocal(0).__getattr__("state").__not__().__nonzero__()) {
         var1.setline(2021);
         if (var1.getlocal(0).__getattr__("roots").__not__().__nonzero__()) {
            var1.setline(2022);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(2023);
         var4 = var1.getlocal(0).__getattr__("roots").__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(2024);
         PyList var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("children").__call__(var2, var1.getlocal(1))})});
         var1.getlocal(0).__setattr__((String)"state", var7);
         var4 = null;
      }

      var1.setline(2025);
      var4 = var1.getlocal(0).__getattr__("state").__getitem__(Py.newInteger(-1));
      PyObject[] var5 = Py.unpackSequence(var4, 2);
      PyObject var6 = var5[0];
      var1.setlocal(2, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(3, var6);
      var6 = null;
      var4 = null;
      var1.setline(2026);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(2027);
         var1.getlocal(0).__getattr__("state").__getattr__("pop").__call__(var2);
         var1.setline(2028);
         var3 = var1.getlocal(0).__getattr__("next").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2029);
         var4 = var1.getlocal(3).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(2030);
         if (var1.getlocal(0).__getattr__("descendp").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(2031);
            var1.getlocal(0).__getattr__("state").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("children").__call__(var2, var1.getlocal(4))})));
         }

         var1.setline(2032);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ModuleScanner$166(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An interruptible scanner that searches module synopses."));
      var1.setline(2036);
      PyString.fromInterned("An interruptible scanner that searches module synopses.");
      var1.setline(2038);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, run$167, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$167(PyFrame var1, ThreadState var2) {
      var1.setline(2039);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(2039);
         var3 = var1.getglobal("lower").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(2040);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("quit", var3);
      var3 = null;
      var1.setline(2041);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(2043);
      var3 = var1.getglobal("sys").__getattr__("builtin_module_names").__iter__();

      while(true) {
         var1.setline(2043);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(2053);
            var10000 = var1.getglobal("pkgutil").__getattr__("walk_packages");
            PyObject[] var9 = new PyObject[]{var1.getlocal(4)};
            String[] var8 = new String[]{"onerror"};
            var10000 = var10000.__call__(var2, var9, var8);
            var3 = null;
            var3 = var10000.__iter__();

            while(true) {
               var1.setline(2053);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               PyObject[] var11 = Py.unpackSequence(var4, 3);
               PyObject var6 = var11[0];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var11[1];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var11[2];
               var1.setlocal(9, var6);
               var6 = null;
               var1.setline(2054);
               if (var1.getlocal(0).__getattr__("quit").__nonzero__()) {
                  break;
               }

               var1.setline(2056);
               var5 = var1.getlocal(2);
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2057);
                  var1.getlocal(1).__call__((ThreadState)var2, var1.getglobal("None"), (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned(""));
               } else {
                  var1.setline(2059);
                  var5 = var1.getlocal(8).__getattr__("find_module").__call__(var2, var1.getlocal(6));
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(2060);
                  Object var13;
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)PyString.fromInterned("get_source")).__nonzero__()) {
                     var1.setline(2061);
                     var5 = imp.importOne("StringIO", var1, -1);
                     var1.setlocal(11, var5);
                     var5 = null;
                     var1.setline(2062);
                     var13 = var1.getglobal("source_synopsis").__call__(var2, var1.getlocal(11).__getattr__("StringIO").__call__(var2, var1.getlocal(10).__getattr__("get_source").__call__(var2, var1.getlocal(6))));
                     if (!((PyObject)var13).__nonzero__()) {
                        var13 = PyString.fromInterned("");
                     }

                     Object var12 = var13;
                     var1.setlocal(7, (PyObject)var12);
                     var5 = null;
                     var1.setline(2065);
                     if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)PyString.fromInterned("get_filename")).__nonzero__()) {
                        var1.setline(2066);
                        var5 = var1.getlocal(10).__getattr__("get_filename").__call__(var2, var1.getlocal(6));
                        var1.setlocal(12, var5);
                        var5 = null;
                     } else {
                        var1.setline(2068);
                        var5 = var1.getglobal("None");
                        var1.setlocal(12, var5);
                        var5 = null;
                     }
                  } else {
                     var1.setline(2070);
                     var5 = var1.getlocal(10).__getattr__("load_module").__call__(var2, var1.getlocal(6));
                     var1.setlocal(13, var5);
                     var5 = null;
                     var1.setline(2071);
                     var13 = var1.getlocal(13).__getattr__("__doc__");
                     if (!((PyObject)var13).__nonzero__()) {
                        var13 = PyString.fromInterned("");
                     }

                     var5 = ((PyObject)var13).__getattr__("splitlines").__call__(var2).__getitem__(Py.newInteger(0));
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(2072);
                     var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(13), (PyObject)PyString.fromInterned("__file__"), (PyObject)var1.getglobal("None"));
                     var1.setlocal(12, var5);
                     var5 = null;
                  }

                  var1.setline(2073);
                  var5 = var1.getglobal("find").__call__(var2, var1.getglobal("lower").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned(" - "))._add(var1.getlocal(7))), var1.getlocal(2));
                  var10000 = var5._ge(Py.newInteger(0));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2074);
                     var1.getlocal(1).__call__(var2, var1.getlocal(12), var1.getlocal(6), var1.getlocal(7));
                  }
               }
            }

            var1.setline(2076);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(2077);
               var1.getlocal(3).__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(2044);
         var5 = var1.getlocal(6);
         var10000 = var5._ne(PyString.fromInterned("__main__"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2045);
            PyInteger var10 = Py.newInteger(1);
            var1.getlocal(5).__setitem__((PyObject)var1.getlocal(6), var10);
            var5 = null;
            var1.setline(2046);
            var5 = var1.getlocal(2);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2047);
               var1.getlocal(1).__call__((ThreadState)var2, var1.getglobal("None"), (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned(""));
            } else {
               var1.setline(2049);
               var10000 = var1.getglobal("split");
               Object var10002 = var1.getglobal("__import__").__call__(var2, var1.getlocal(6)).__getattr__("__doc__");
               if (!((PyObject)var10002).__nonzero__()) {
                  var10002 = PyString.fromInterned("");
               }

               var5 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("\n")).__getitem__(Py.newInteger(0));
               var1.setlocal(7, var5);
               var5 = null;
               var1.setline(2050);
               var5 = var1.getglobal("find").__call__(var2, var1.getglobal("lower").__call__(var2, var1.getlocal(6)._add(PyString.fromInterned(" - "))._add(var1.getlocal(7))), var1.getlocal(2));
               var10000 = var5._ge(Py.newInteger(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2051);
                  var1.getlocal(1).__call__(var2, var1.getglobal("None"), var1.getlocal(6), var1.getlocal(7));
               }
            }
         }
      }
   }

   public PyObject apropos$168(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(2080);
      PyString.fromInterned("Print all the one-line module summaries that contain a substring.");
      var1.setline(2081);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var3, callback$169, (PyObject)null);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(2085);
      var3 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var3, onerror$170, (PyObject)null);
      var1.setlocal(2, var7);
      var3 = null;
      ContextManager var9;
      PyObject var4 = (var9 = ContextGuard.getManager(var1.getglobal("warnings").__getattr__("catch_warnings").__call__(var2))).__enter__(var2);

      label16: {
         try {
            var1.setline(2088);
            var1.getglobal("warnings").__getattr__("filterwarnings").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ignore"));
            var1.setline(2089);
            PyObject var10000 = var1.getglobal("ModuleScanner").__call__(var2).__getattr__("run");
            PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(0), var1.getlocal(2)};
            String[] var5 = new String[]{"onerror"};
            var10000.__call__(var2, var8, var5);
            var4 = null;
         } catch (Throwable var6) {
            if (var9.__exit__(var2, Py.setException(var6, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var9.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject callback$169(PyFrame var1, ThreadState var2) {
      var1.setline(2082);
      PyObject var3 = var1.getlocal(1).__getslice__(Py.newInteger(-9), (PyObject)null, (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(".__init__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2083);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null)._add(PyString.fromInterned(" (package)"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2084);
      Py.printComma(var1.getlocal(1));
      var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = PyString.fromInterned("- ")._add(var1.getlocal(2));
      }

      Py.println(var10000);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject onerror$170(PyFrame var1, ThreadState var2) {
      var1.setline(2086);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject serve$171(PyFrame var1, ThreadState var2) {
      var1.setline(2094);
      PyObject var3 = imp.importOne("BaseHTTPServer", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var3 = imp.importOne("mimetools", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var3 = imp.importOne("select", var1, -1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2097);
      PyObject[] var7 = new PyObject[]{var1.getlocal(4).__getattr__("Message")};
      PyObject var4 = Py.makeClass("Message", var7, Message$172);
      var1.setlocal(6, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2106);
      var7 = new PyObject[]{var1.getlocal(3).__getattr__("BaseHTTPRequestHandler")};
      var4 = Py.makeClass("DocHandler", var7, DocHandler$174);
      var1.setlocal(7, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2152);
      var7 = new PyObject[]{var1.getlocal(3).__getattr__("HTTPServer")};
      var4 = Py.makeClass("DocServer", var7, DocServer$180);
      var1.setlocal(8, var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2171);
      var3 = var1.getlocal(3).__getattr__("HTTPServer");
      var1.getlocal(8).__setattr__("base", var3);
      var3 = null;
      var1.setline(2172);
      var3 = var1.getlocal(7);
      var1.getlocal(8).__setattr__("handler", var3);
      var3 = null;
      var1.setline(2173);
      var3 = var1.getlocal(6);
      var1.getlocal(7).__setattr__("MessageClass", var3);
      var3 = null;
      var3 = null;

      try {
         try {
            var1.setline(2176);
            var1.getlocal(8).__call__(var2, var1.getlocal(0), var1.getlocal(1)).__getattr__("serve_until_quit").__call__(var2);
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("KeyboardInterrupt"), var1.getlocal(5).__getattr__("error")}))) {
               throw var8;
            }

            var1.setline(2178);
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(2180);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2180);
            var1.getlocal(2).__call__(var2);
         }

         throw (Throwable)var6;
      }

      var1.setline(2180);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(2180);
         var1.getlocal(2).__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Message$172(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2098);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$173, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$173(PyFrame var1, ThreadState var2) {
      var1.setline(2099);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2100);
      var1.getlocal(3).__getattr__("__bases__").__getitem__(Py.newInteger(0)).__getattr__("__bases__").__getitem__(Py.newInteger(0)).__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(2101);
      var3 = var1.getlocal(0).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-transfer-encoding"));
      var1.getlocal(0).__setattr__("encodingheader", var3);
      var3 = null;
      var1.setline(2102);
      var3 = var1.getlocal(0).__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-type"));
      var1.getlocal(0).__setattr__("typeheader", var3);
      var3 = null;
      var1.setline(2103);
      var1.getlocal(0).__getattr__("parsetype").__call__(var2);
      var1.setline(2104);
      var1.getlocal(0).__getattr__("parseplist").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DocHandler$174(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2107);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, send_document$175, (PyObject)null);
      var1.setlocal("send_document", var4);
      var3 = null;
      var1.setline(2115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_GET$176, (PyObject)null);
      var1.setlocal("do_GET", var4);
      var3 = null;
      var1.setline(2150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, log_message$179, (PyObject)null);
      var1.setlocal("log_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject send_document$175(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(2109);
         var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(200));
         var1.setline(2110);
         var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Type"), (PyObject)PyString.fromInterned("text/html"));
         var1.setline(2111);
         var1.getlocal(0).__getattr__("end_headers").__call__(var2);
         var1.setline(2112);
         var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, var1.getglobal("html").__getattr__("page").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("IOError"))) {
            throw var3;
         }

         var1.setline(2113);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_GET$176(PyFrame var1, ThreadState var2) {
      var1.setline(2116);
      PyObject var3 = var1.getlocal(0).__getattr__("path");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2117);
      var3 = var1.getlocal(1).__getslice__(Py.newInteger(-5), (PyObject)null, (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(".html"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2117);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-5), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2118);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2118);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2119);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(PyString.fromInterned("."));
         var3 = null;
      }

      PyObject var4;
      PyObject[] var8;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(2121);
            var10000 = var1.getglobal("locate");
            var8 = new PyObject[]{var1.getlocal(1), Py.newInteger(1)};
            String[] var6 = new String[]{"forceload"};
            var10000 = var10000.__call__(var2, var8, var6);
            var3 = null;
            var3 = var10000;
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("ErrorDuringImport"))) {
               var4 = var7.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(2123);
               var1.getlocal(0).__getattr__("send_document").__call__(var2, var1.getlocal(1), var1.getglobal("html").__getattr__("escape").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3))));
               var1.setline(2124);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var7;
         }

         var1.setline(2125);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2126);
            var1.getlocal(0).__getattr__("send_document").__call__(var2, var1.getglobal("describe").__call__(var2, var1.getlocal(2)), var1.getglobal("html").__getattr__("document").__call__(var2, var1.getlocal(2), var1.getlocal(1)));
         } else {
            var1.setline(2128);
            var1.getlocal(0).__getattr__("send_document").__call__(var2, var1.getlocal(1), PyString.fromInterned("no Python documentation found for %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
         }
      } else {
         var1.setline(2131);
         var3 = var1.getglobal("html").__getattr__("heading").__call__((ThreadState)var2, PyString.fromInterned("<big><big><strong>Python: Index of Modules</strong></big></big>"), (PyObject)PyString.fromInterned("#ffffff"), (PyObject)PyString.fromInterned("#7799ee"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(2134);
         var8 = Py.EmptyObjects;
         PyFunction var9 = new PyFunction(var1.f_globals, var8, bltinlink$177, (PyObject)null);
         var1.setlocal(5, var9);
         var3 = null;
         var1.setline(2136);
         var10000 = var1.getglobal("filter");
         var1.setline(2136);
         var8 = Py.EmptyObjects;
         var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var8, f$178)), (PyObject)var1.getglobal("sys").__getattr__("builtin_module_names"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(2138);
         var3 = var1.getglobal("html").__getattr__("multicolumn").__call__(var2, var1.getlocal(6), var1.getlocal(5));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(2139);
         PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("<p>")._add(var1.getglobal("html").__getattr__("bigsection").__call__(var2, PyString.fromInterned("Built-in Modules"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#ee77aa"), var1.getlocal(7)))});
         var1.setlocal(8, var10);
         var3 = null;
         var1.setline(2142);
         PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(9, var11);
         var3 = null;
         var1.setline(2143);
         var3 = var1.getglobal("sys").__getattr__("path").__iter__();

         while(true) {
            var1.setline(2143);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(2145);
               var3 = var1.getlocal(4)._add(var1.getglobal("join").__call__(var2, var1.getlocal(8)))._add(PyString.fromInterned("<p align=right>\n<font color=\"#909090\" face=\"helvetica, arial\"><strong>\npydoc</strong> by Ka-Ping Yee &lt;ping@lfw.org&gt;</font>"));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(2148);
               var1.getlocal(0).__getattr__("send_document").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Index of Modules"), (PyObject)var1.getlocal(7));
               break;
            }

            var1.setlocal(10, var4);
            var1.setline(2144);
            var1.getlocal(8).__getattr__("append").__call__(var2, var1.getglobal("html").__getattr__("index").__call__(var2, var1.getlocal(10), var1.getlocal(9)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bltinlink$177(PyFrame var1, ThreadState var2) {
      var1.setline(2135);
      PyObject var3 = PyString.fromInterned("<a href=\"%s.html\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(0)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$178(PyFrame var1, ThreadState var2) {
      var1.setline(2136);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._ne(PyString.fromInterned("__main__"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject log_message$179(PyFrame var1, ThreadState var2) {
      var1.setline(2150);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DocServer$180(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2153);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$181, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, serve_until_quit$182, (PyObject)null);
      var1.setlocal("serve_until_quit", var4);
      var3 = null;
      var1.setline(2167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, server_activate$183, (PyObject)null);
      var1.setlocal("server_activate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$181(PyFrame var1, ThreadState var2) {
      var1.setline(2154);
      PyString var3 = PyString.fromInterned("localhost");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2155);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"address", var4);
      var3 = null;
      var1.setline(2156);
      PyObject var5 = PyString.fromInterned("http://%s:%d/")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1)}));
      var1.getlocal(0).__setattr__("url", var5);
      var3 = null;
      var1.setline(2157);
      var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("callback", var5);
      var3 = null;
      var1.setline(2158);
      var1.getlocal(0).__getattr__("base").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("address"), var1.getlocal(0).__getattr__("handler"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject serve_until_quit$182(PyFrame var1, ThreadState var2) {
      var1.setline(2161);
      PyObject var3 = imp.importOne("select", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2162);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("quit", var3);
      var3 = null;

      while(true) {
         var1.setline(2163);
         if (!var1.getlocal(0).__getattr__("quit").__not__().__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2164);
         var3 = var1.getlocal(1).__getattr__("select").__call__(var2, new PyList(new PyObject[]{var1.getlocal(0).__getattr__("socket").__getattr__("fileno").__call__(var2)}), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(1));
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
         var1.setline(2165);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2165);
            var1.getlocal(0).__getattr__("handle_request").__call__(var2);
         }
      }
   }

   public PyObject server_activate$183(PyFrame var1, ThreadState var2) {
      var1.setline(2168);
      var1.getlocal(0).__getattr__("base").__getattr__("server_activate").__call__(var2, var1.getlocal(0));
      var1.setline(2169);
      if (var1.getlocal(0).__getattr__("callback").__nonzero__()) {
         var1.setline(2169);
         var1.getlocal(0).__getattr__("callback").__call__(var2, var1.getlocal(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gui$184(PyFrame var1, ThreadState var2) {
      var1.setline(2185);
      PyString.fromInterned("Graphical interface (starts web server and pops up a control window).");
      var1.setline(2186);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("GUI", var3, GUI$185);
      var1.setlocal(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(2350);
      PyObject var7 = imp.importOne("Tkinter", var1, -1);
      var1.setlocal(1, var7);
      var3 = null;

      try {
         var1.setline(2352);
         var7 = var1.getlocal(1).__getattr__("Tk").__call__(var2);
         var1.setlocal(2, var7);
         var3 = null;
         var3 = null;

         try {
            var1.setline(2358);
            var4 = var1.getlocal(0).__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(2359);
            var1.getlocal(2).__getattr__("mainloop").__call__(var2);
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(2361);
            var1.getlocal(2).__getattr__("destroy").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(2361);
         var1.getlocal(2).__getattr__("destroy").__call__(var2);
      } catch (Throwable var6) {
         PyException var8 = Py.setException(var6, var1);
         if (!var8.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var8;
         }

         var1.setline(2363);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject GUI$185(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2187);
      PyObject[] var3 = new PyObject[]{Py.newInteger(7464)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$186, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ready$187, (PyObject)null);
      var1.setlocal("ready", var4);
      var3 = null;
      var1.setline(2263);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, open$188, (PyObject)null);
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(2275);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, quit$189, (PyObject)null);
      var1.setlocal("quit", var4);
      var3 = null;
      var1.setline(2280);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, search$190, (PyObject)null);
      var1.setlocal("search", var4);
      var3 = null;
      var1.setline(2298);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, update$191, (PyObject)null);
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(2304);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, stop$192, (PyObject)null);
      var1.setlocal("stop", var4);
      var3 = null;
      var1.setline(2309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, done$193, (PyObject)null);
      var1.setlocal("done", var4);
      var3 = null;
      var1.setline(2317);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, select$194, (PyObject)null);
      var1.setlocal("select", var4);
      var3 = null;
      var1.setline(2320);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, goto$195, (PyObject)null);
      var1.setlocal("goto", var4);
      var3 = null;
      var1.setline(2326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, collapse$196, (PyObject)null);
      var1.setlocal("collapse", var4);
      var3 = null;
      var1.setline(2337);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, expand$197, (PyObject)null);
      var1.setlocal("expand", var4);
      var3 = null;
      var1.setline(2346);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, hide$198, (PyObject)null);
      var1.setlocal("hide", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$186(PyFrame var1, ThreadState var2) {
      var1.setline(2188);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("window", var3);
      var3 = null;
      var1.setline(2189);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("server", var3);
      var3 = null;
      var1.setline(2190);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("scanner", var3);
      var3 = null;
      var1.setline(2192);
      var3 = imp.importOne("Tkinter", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2193);
      var3 = var1.getlocal(3).__getattr__("Frame").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("server_frm", var3);
      var3 = null;
      var1.setline(2194);
      PyObject var10000 = var1.getlocal(3).__getattr__("Label");
      PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("server_frm"), PyString.fromInterned("Starting server...\n ")};
      String[] var4 = new String[]{"text"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("title_lbl", var3);
      var3 = null;
      var1.setline(2196);
      var10000 = var1.getlocal(3).__getattr__("Button");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("server_frm"), PyString.fromInterned("open browser"), var1.getlocal(0).__getattr__("open"), PyString.fromInterned("disabled")};
      var4 = new String[]{"text", "command", "state"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("open_btn", var3);
      var3 = null;
      var1.setline(2198);
      var10000 = var1.getlocal(3).__getattr__("Button");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("server_frm"), PyString.fromInterned("quit serving"), var1.getlocal(0).__getattr__("quit"), PyString.fromInterned("disabled")};
      var4 = new String[]{"text", "command", "state"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("quit_btn", var3);
      var3 = null;
      var1.setline(2201);
      var3 = var1.getlocal(3).__getattr__("Frame").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("search_frm", var3);
      var3 = null;
      var1.setline(2202);
      var10000 = var1.getlocal(3).__getattr__("Label");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("search_frm"), PyString.fromInterned("Search for")};
      var4 = new String[]{"text"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("search_lbl", var3);
      var3 = null;
      var1.setline(2203);
      var3 = var1.getlocal(3).__getattr__("Entry").__call__(var2, var1.getlocal(0).__getattr__("search_frm"));
      var1.getlocal(0).__setattr__("search_ent", var3);
      var3 = null;
      var1.setline(2204);
      var1.getlocal(0).__getattr__("search_ent").__getattr__("bind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<Return>"), (PyObject)var1.getlocal(0).__getattr__("search"));
      var1.setline(2205);
      var10000 = var1.getlocal(3).__getattr__("Button");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("search_frm"), PyString.fromInterned("stop"), Py.newInteger(0), var1.getlocal(0).__getattr__("stop"), PyString.fromInterned("disabled")};
      var4 = new String[]{"text", "pady", "command", "state"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("stop_btn", var3);
      var3 = null;
      var1.setline(2207);
      if (var1.getglobal("_is_windows").__nonzero__()) {
         var1.setline(2209);
         var10000 = var1.getlocal(0).__getattr__("stop_btn").__getattr__("pack");
         var6 = new PyObject[]{PyString.fromInterned("right")};
         var4 = new String[]{"side"};
         var10000.__call__(var2, var6, var4);
         var3 = null;
      }

      var1.setline(2211);
      var1.getlocal(0).__getattr__("window").__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pydoc"));
      var1.setline(2212);
      var1.getlocal(0).__getattr__("window").__getattr__("protocol").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("WM_DELETE_WINDOW"), (PyObject)var1.getlocal(0).__getattr__("quit"));
      var1.setline(2213);
      var10000 = var1.getlocal(0).__getattr__("title_lbl").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("top"), PyString.fromInterned("x")};
      var4 = new String[]{"side", "fill"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2214);
      var10000 = var1.getlocal(0).__getattr__("open_btn").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("left"), PyString.fromInterned("x"), Py.newInteger(1)};
      var4 = new String[]{"side", "fill", "expand"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2215);
      var10000 = var1.getlocal(0).__getattr__("quit_btn").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("right"), PyString.fromInterned("x"), Py.newInteger(1)};
      var4 = new String[]{"side", "fill", "expand"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2216);
      var10000 = var1.getlocal(0).__getattr__("server_frm").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("top"), PyString.fromInterned("x")};
      var4 = new String[]{"side", "fill"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2218);
      var10000 = var1.getlocal(0).__getattr__("search_lbl").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("left")};
      var4 = new String[]{"side"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2219);
      var10000 = var1.getlocal(0).__getattr__("search_ent").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("right"), PyString.fromInterned("x"), Py.newInteger(1)};
      var4 = new String[]{"side", "fill", "expand"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2220);
      var10000 = var1.getlocal(0).__getattr__("search_frm").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("top"), PyString.fromInterned("x")};
      var4 = new String[]{"side", "fill"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2221);
      var1.getlocal(0).__getattr__("search_ent").__getattr__("focus_set").__call__(var2);
      var1.setline(2223);
      PyTuple var10 = new PyTuple;
      PyObject[] var10002 = new PyObject[]{PyString.fromInterned("helvetica"), null};
      Object var10005 = var1.getglobal("_is_windows");
      if (((PyObject)var10005).__nonzero__()) {
         var10005 = Py.newInteger(8);
      }

      if (!((PyObject)var10005).__nonzero__()) {
         var10005 = Py.newInteger(10);
      }

      var10002[1] = (PyObject)var10005;
      var10.<init>(var10002);
      PyTuple var8 = var10;
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(2224);
      var10000 = var1.getlocal(3).__getattr__("Listbox");
      var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(4), Py.newInteger(6)};
      var4 = new String[]{"font", "height"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("result_lst", var3);
      var3 = null;
      var1.setline(2225);
      var1.getlocal(0).__getattr__("result_lst").__getattr__("bind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<Button-1>"), (PyObject)var1.getlocal(0).__getattr__("select"));
      var1.setline(2226);
      var1.getlocal(0).__getattr__("result_lst").__getattr__("bind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<Double-Button-1>"), (PyObject)var1.getlocal(0).__getattr__("goto"));
      var1.setline(2227);
      var10000 = var1.getlocal(3).__getattr__("Scrollbar");
      var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("vertical"), var1.getlocal(0).__getattr__("result_lst").__getattr__("yview")};
      var4 = new String[]{"orient", "command"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("result_scr", var3);
      var3 = null;
      var1.setline(2229);
      var10000 = var1.getlocal(0).__getattr__("result_lst").__getattr__("config");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("result_scr").__getattr__("set")};
      var4 = new String[]{"yscrollcommand"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2231);
      var3 = var1.getlocal(3).__getattr__("Frame").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("result_frm", var3);
      var3 = null;
      var1.setline(2232);
      var10000 = var1.getlocal(3).__getattr__("Button");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("result_frm"), PyString.fromInterned("go to selected"), var1.getlocal(0).__getattr__("goto")};
      var4 = new String[]{"text", "command"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("goto_btn", var3);
      var3 = null;
      var1.setline(2234);
      var10000 = var1.getlocal(3).__getattr__("Button");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("result_frm"), PyString.fromInterned("hide results"), var1.getlocal(0).__getattr__("hide")};
      var4 = new String[]{"text", "command"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("hide_btn", var3);
      var3 = null;
      var1.setline(2236);
      var10000 = var1.getlocal(0).__getattr__("goto_btn").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("left"), PyString.fromInterned("x"), Py.newInteger(1)};
      var4 = new String[]{"side", "fill", "expand"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2237);
      var10000 = var1.getlocal(0).__getattr__("hide_btn").__getattr__("pack");
      var6 = new PyObject[]{PyString.fromInterned("right"), PyString.fromInterned("x"), Py.newInteger(1)};
      var4 = new String[]{"side", "fill", "expand"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(2239);
      var1.getlocal(0).__getattr__("window").__getattr__("update").__call__(var2);
      var1.setline(2240);
      var3 = var1.getlocal(0).__getattr__("window").__getattr__("winfo_width").__call__(var2);
      var1.getlocal(0).__setattr__("minwidth", var3);
      var3 = null;
      var1.setline(2241);
      var3 = var1.getlocal(0).__getattr__("window").__getattr__("winfo_height").__call__(var2);
      var1.getlocal(0).__setattr__("minheight", var3);
      var3 = null;
      var1.setline(2242);
      var3 = var1.getlocal(0).__getattr__("server_frm").__getattr__("winfo_reqheight").__call__(var2)._add(var1.getlocal(0).__getattr__("search_frm").__getattr__("winfo_reqheight").__call__(var2))._add(var1.getlocal(0).__getattr__("result_lst").__getattr__("winfo_reqheight").__call__(var2))._add(var1.getlocal(0).__getattr__("result_frm").__getattr__("winfo_reqheight").__call__(var2));
      var1.getlocal(0).__setattr__("bigminheight", var3);
      var3 = null;
      var1.setline(2246);
      var8 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("minwidth"), var1.getlocal(0).__getattr__("bigminheight")});
      PyObject[] var7 = Py.unpackSequence(var8, 2);
      PyObject var5 = var7[0];
      var1.getlocal(0).__setattr__("bigwidth", var5);
      var5 = null;
      var5 = var7[1];
      var1.getlocal(0).__setattr__("bigheight", var5);
      var5 = null;
      var3 = null;
      var1.setline(2247);
      PyInteger var9 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"expanded", var9);
      var3 = null;
      var1.setline(2248);
      var1.getlocal(0).__getattr__("window").__getattr__("wm_geometry").__call__(var2, PyString.fromInterned("%dx%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("minwidth"), var1.getlocal(0).__getattr__("minheight")})));
      var1.setline(2249);
      var1.getlocal(0).__getattr__("window").__getattr__("wm_minsize").__call__(var2, var1.getlocal(0).__getattr__("minwidth"), var1.getlocal(0).__getattr__("minheight"));
      var1.setline(2250);
      var1.getlocal(0).__getattr__("window").__getattr__("tk").__getattr__("willdispatch").__call__(var2);
      var1.setline(2252);
      var3 = imp.importOne("threading", var1, -1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2253);
      var10000 = var1.getlocal(5).__getattr__("Thread");
      var6 = new PyObject[]{var1.getglobal("serve"), new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("ready"), var1.getlocal(0).__getattr__("quit")})};
      var4 = new String[]{"target", "args"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var10000.__getattr__("start").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ready$187(PyFrame var1, ThreadState var2) {
      var1.setline(2257);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("server", var3);
      var3 = null;
      var1.setline(2258);
      PyObject var10000 = var1.getlocal(0).__getattr__("title_lbl").__getattr__("config");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Python documentation server at\n")._add(var1.getlocal(1).__getattr__("url"))};
      String[] var4 = new String[]{"text"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2260);
      var10000 = var1.getlocal(0).__getattr__("open_btn").__getattr__("config");
      var5 = new PyObject[]{PyString.fromInterned("normal")};
      var4 = new String[]{"state"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2261);
      var10000 = var1.getlocal(0).__getattr__("quit_btn").__getattr__("config");
      var5 = new PyObject[]{PyString.fromInterned("normal")};
      var4 = new String[]{"state"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$188(PyFrame var1, ThreadState var2) {
      var1.setline(2264);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("server").__getattr__("url");
      }

      PyObject var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(2266);
         var3 = imp.importOne("webbrowser", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2267);
         var1.getlocal(3).__getattr__("open").__call__(var2, var1.getlocal(2));
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("ImportError"))) {
            throw var6;
         }

         var1.setline(2269);
         if (var1.getglobal("_is_windows").__nonzero__()) {
            var1.setline(2270);
            var1.getglobal("os").__getattr__("system").__call__(var2, PyString.fromInterned("start \"%s\"")._mod(var1.getlocal(2)));
         } else {
            var1.setline(2272);
            PyObject var4 = var1.getglobal("os").__getattr__("system").__call__(var2, PyString.fromInterned("netscape -remote \"openURL(%s)\" &")._mod(var1.getlocal(2)));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(2273);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(2273);
               var1.getglobal("os").__getattr__("system").__call__(var2, PyString.fromInterned("netscape \"%s\" &")._mod(var1.getlocal(2)));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject quit$189(PyFrame var1, ThreadState var2) {
      var1.setline(2276);
      if (var1.getlocal(0).__getattr__("server").__nonzero__()) {
         var1.setline(2277);
         PyInteger var3 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("server").__setattr__((String)"quit", var3);
         var3 = null;
      }

      var1.setline(2278);
      var1.getlocal(0).__getattr__("window").__getattr__("quit").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject search$190(PyFrame var1, ThreadState var2) {
      var1.setline(2281);
      PyObject var3 = var1.getlocal(0).__getattr__("search_ent").__getattr__("get").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2282);
      PyObject var10000 = var1.getlocal(0).__getattr__("stop_btn").__getattr__("pack");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("right")};
      String[] var4 = new String[]{"side"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2283);
      var10000 = var1.getlocal(0).__getattr__("stop_btn").__getattr__("config");
      var5 = new PyObject[]{PyString.fromInterned("normal")};
      var4 = new String[]{"state"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2284);
      var10000 = var1.getlocal(0).__getattr__("search_lbl").__getattr__("config");
      var5 = new PyObject[]{PyString.fromInterned("Searching for \"%s\"...")._mod(var1.getlocal(2))};
      var4 = new String[]{"text"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2285);
      var1.getlocal(0).__getattr__("search_ent").__getattr__("forget").__call__(var2);
      var1.setline(2286);
      var10000 = var1.getlocal(0).__getattr__("search_lbl").__getattr__("pack");
      var5 = new PyObject[]{PyString.fromInterned("left")};
      var4 = new String[]{"side"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2287);
      var1.getlocal(0).__getattr__("result_lst").__getattr__("delete").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("end"));
      var1.setline(2288);
      var10000 = var1.getlocal(0).__getattr__("goto_btn").__getattr__("config");
      var5 = new PyObject[]{PyString.fromInterned("disabled")};
      var4 = new String[]{"state"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2289);
      var1.getlocal(0).__getattr__("expand").__call__(var2);
      var1.setline(2291);
      var3 = imp.importOne("threading", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2292);
      if (var1.getlocal(0).__getattr__("scanner").__nonzero__()) {
         var1.setline(2293);
         PyInteger var6 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("scanner").__setattr__((String)"quit", var6);
         var3 = null;
      }

      var1.setline(2294);
      var3 = var1.getglobal("ModuleScanner").__call__(var2);
      var1.getlocal(0).__setattr__("scanner", var3);
      var3 = null;
      var1.setline(2295);
      var10000 = var1.getlocal(3).__getattr__("Thread");
      var5 = new PyObject[]{var1.getlocal(0).__getattr__("scanner").__getattr__("run"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("update"), var1.getlocal(2), var1.getlocal(0).__getattr__("done")})};
      var4 = new String[]{"target", "args"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var10000.__getattr__("start").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject update$191(PyFrame var1, ThreadState var2) {
      var1.setline(2299);
      PyObject var3 = var1.getlocal(2).__getslice__(Py.newInteger(-9), (PyObject)null, (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(".__init__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2300);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null)._add(PyString.fromInterned(" (package)"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(2301);
      var10000 = var1.getlocal(0).__getattr__("result_lst").__getattr__("insert");
      PyString var10002 = PyString.fromInterned("end");
      PyObject var10003 = var1.getlocal(2)._add(PyString.fromInterned(" - "));
      Object var10004 = var1.getlocal(3);
      if (!((PyObject)var10004).__nonzero__()) {
         var10004 = PyString.fromInterned("(no description)");
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003._add((PyObject)var10004));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stop$192(PyFrame var1, ThreadState var2) {
      var1.setline(2305);
      if (var1.getlocal(0).__getattr__("scanner").__nonzero__()) {
         var1.setline(2306);
         PyInteger var3 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("scanner").__setattr__((String)"quit", var3);
         var3 = null;
         var1.setline(2307);
         PyObject var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("scanner", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject done$193(PyFrame var1, ThreadState var2) {
      var1.setline(2310);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("scanner", var3);
      var3 = null;
      var1.setline(2311);
      PyObject var10000 = var1.getlocal(0).__getattr__("search_lbl").__getattr__("config");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("Search for")};
      String[] var4 = new String[]{"text"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2312);
      var10000 = var1.getlocal(0).__getattr__("search_lbl").__getattr__("pack");
      var5 = new PyObject[]{PyString.fromInterned("left")};
      var4 = new String[]{"side"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2313);
      var10000 = var1.getlocal(0).__getattr__("search_ent").__getattr__("pack");
      var5 = new PyObject[]{PyString.fromInterned("right"), PyString.fromInterned("x"), Py.newInteger(1)};
      var4 = new String[]{"side", "fill", "expand"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(2314);
      if (var1.getglobal("_is_windows").__not__().__nonzero__()) {
         var1.setline(2314);
         var1.getlocal(0).__getattr__("stop_btn").__getattr__("forget").__call__(var2);
      }

      var1.setline(2315);
      var10000 = var1.getlocal(0).__getattr__("stop_btn").__getattr__("config");
      var5 = new PyObject[]{PyString.fromInterned("disabled")};
      var4 = new String[]{"state"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject select$194(PyFrame var1, ThreadState var2) {
      var1.setline(2318);
      PyObject var10000 = var1.getlocal(0).__getattr__("goto_btn").__getattr__("config");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("normal")};
      String[] var4 = new String[]{"state"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject goto$195(PyFrame var1, ThreadState var2) {
      var1.setline(2321);
      PyObject var3 = var1.getlocal(0).__getattr__("result_lst").__getattr__("curselection").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2322);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(2323);
         var3 = var1.getglobal("split").__call__(var2, var1.getlocal(0).__getattr__("result_lst").__getattr__("get").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)))).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2324);
         PyObject var10000 = var1.getlocal(0).__getattr__("open");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("server").__getattr__("url")._add(var1.getlocal(3))._add(PyString.fromInterned(".html"))};
         String[] var4 = new String[]{"url"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject collapse$196(PyFrame var1, ThreadState var2) {
      var1.setline(2327);
      if (var1.getlocal(0).__getattr__("expanded").__not__().__nonzero__()) {
         var1.setline(2327);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(2328);
         var1.getlocal(0).__getattr__("result_frm").__getattr__("forget").__call__(var2);
         var1.setline(2329);
         var1.getlocal(0).__getattr__("result_scr").__getattr__("forget").__call__(var2);
         var1.setline(2330);
         var1.getlocal(0).__getattr__("result_lst").__getattr__("forget").__call__(var2);
         var1.setline(2331);
         PyObject var3 = var1.getlocal(0).__getattr__("window").__getattr__("winfo_width").__call__(var2);
         var1.getlocal(0).__setattr__("bigwidth", var3);
         var3 = null;
         var1.setline(2332);
         var3 = var1.getlocal(0).__getattr__("window").__getattr__("winfo_height").__call__(var2);
         var1.getlocal(0).__setattr__("bigheight", var3);
         var3 = null;
         var1.setline(2333);
         var1.getlocal(0).__getattr__("window").__getattr__("wm_geometry").__call__(var2, PyString.fromInterned("%dx%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("minwidth"), var1.getlocal(0).__getattr__("minheight")})));
         var1.setline(2334);
         var1.getlocal(0).__getattr__("window").__getattr__("wm_minsize").__call__(var2, var1.getlocal(0).__getattr__("minwidth"), var1.getlocal(0).__getattr__("minheight"));
         var1.setline(2335);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"expanded", var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject expand$197(PyFrame var1, ThreadState var2) {
      var1.setline(2338);
      if (var1.getlocal(0).__getattr__("expanded").__nonzero__()) {
         var1.setline(2338);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(2339);
         PyObject var10000 = var1.getlocal(0).__getattr__("result_frm").__getattr__("pack");
         PyObject[] var3 = new PyObject[]{PyString.fromInterned("bottom"), PyString.fromInterned("x")};
         String[] var4 = new String[]{"side", "fill"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
         var1.setline(2340);
         var10000 = var1.getlocal(0).__getattr__("result_scr").__getattr__("pack");
         var3 = new PyObject[]{PyString.fromInterned("right"), PyString.fromInterned("y")};
         var4 = new String[]{"side", "fill"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
         var1.setline(2341);
         var10000 = var1.getlocal(0).__getattr__("result_lst").__getattr__("pack");
         var3 = new PyObject[]{PyString.fromInterned("top"), PyString.fromInterned("both"), Py.newInteger(1)};
         var4 = new String[]{"side", "fill", "expand"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
         var1.setline(2342);
         var1.getlocal(0).__getattr__("window").__getattr__("wm_geometry").__call__(var2, PyString.fromInterned("%dx%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("bigwidth"), var1.getlocal(0).__getattr__("bigheight")})));
         var1.setline(2343);
         var1.getlocal(0).__getattr__("window").__getattr__("wm_minsize").__call__(var2, var1.getlocal(0).__getattr__("minwidth"), var1.getlocal(0).__getattr__("bigminheight"));
         var1.setline(2344);
         PyInteger var5 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"expanded", var5);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject hide$198(PyFrame var1, ThreadState var2) {
      var1.setline(2347);
      var1.getlocal(0).__getattr__("stop").__call__(var2);
      var1.setline(2348);
      var1.getlocal(0).__getattr__("collapse").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ispath$199(PyFrame var1, ThreadState var2) {
      var1.setline(2368);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("find").__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("sep"));
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cli$200(PyFrame var1, ThreadState var2) {
      var1.setline(2371);
      PyString.fromInterned("Command-line interface (looks at sys.argv to decide what to do).");
      var1.setline(2372);
      PyObject var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(2373);
      PyObject[] var10 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("BadUsage", var10, BadUsage$201);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2377);
      PyString var11 = PyString.fromInterned("");
      PyObject var10000 = var11._notin(var1.getglobal("sys").__getattr__("path"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2378);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2379);
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getglobal("sys").__getattr__("path"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2380);
            var1.getglobal("sys").__getattr__("path").__getattr__("remove").__call__(var2, var1.getlocal(2));
         }

         var1.setline(2381);
         var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("."));
      }

      try {
         var1.setline(2384);
         var3 = var1.getlocal(0).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("gk:p:w"));
         PyObject[] var12 = Py.unpackSequence(var3, 2);
         PyObject var5 = var12[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(2385);
         PyInteger var15 = Py.newInteger(0);
         var1.setlocal(5, var15);
         var3 = null;
         var1.setline(2387);
         var3 = var1.getlocal(3).__iter__();

         label97:
         while(true) {
            var1.setline(2387);
            var4 = var3.__iternext__();
            PyObject var6;
            PyException var17;
            if (var4 == null) {
               var1.setline(2408);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  var1.setline(2408);
                  throw Py.makeException(var1.getlocal(1));
               }

               var1.setline(2409);
               var3 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(2409);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label97;
                  }

                  var1.setlocal(11, var4);
                  var1.setline(2410);
                  var10000 = var1.getglobal("ispath").__call__(var2, var1.getlocal(11));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(11)).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(2411);
                     Py.println(PyString.fromInterned("file %r does not exist")._mod(var1.getlocal(11)));
                     break label97;
                  }

                  try {
                     var1.setline(2414);
                     var10000 = var1.getglobal("ispath").__call__(var2, var1.getlocal(11));
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(11));
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(2415);
                        var5 = var1.getglobal("importfile").__call__(var2, var1.getlocal(11));
                        var1.setlocal(11, var5);
                        var5 = null;
                     }

                     var1.setline(2416);
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(2417);
                        var10000 = var1.getglobal("ispath").__call__(var2, var1.getlocal(11));
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(11));
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(2418);
                           var1.getglobal("writedocs").__call__(var2, var1.getlocal(11));
                        } else {
                           var1.setline(2420);
                           var1.getglobal("writedoc").__call__(var2, var1.getlocal(11));
                        }
                     } else {
                        var1.setline(2422);
                        var1.getglobal("help").__getattr__("help").__call__(var2, var1.getlocal(11));
                     }
                  } catch (Throwable var8) {
                     var17 = Py.setException(var8, var1);
                     if (!var17.match(var1.getglobal("ErrorDuringImport"))) {
                        throw var17;
                     }

                     var6 = var17.value;
                     var1.setlocal(12, var6);
                     var6 = null;
                     var1.setline(2424);
                     Py.println(var1.getlocal(12));
                  }
               }
            }

            PyObject[] var14 = Py.unpackSequence(var4, 2);
            var6 = var14[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var14[1];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(2388);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("-g"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2389);
               var1.getglobal("gui").__call__(var2);
               var1.setline(2390);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(2391);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("-k"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2392);
               var1.getglobal("apropos").__call__(var2, var1.getlocal(7));
               var1.setline(2393);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(2394);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("-p"));
            var5 = null;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(2396);
                  var5 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
                  var1.setlocal(8, var5);
                  var5 = null;
               } catch (Throwable var7) {
                  var17 = Py.setException(var7, var1);
                  if (var17.match(var1.getglobal("ValueError"))) {
                     var1.setline(2398);
                     throw Py.makeException(var1.getlocal(1));
                  }

                  throw var17;
               }

               var1.setline(2399);
               var14 = Py.EmptyObjects;
               PyFunction var18 = new PyFunction(var1.f_globals, var14, ready$202, (PyObject)null);
               var1.setlocal(9, var18);
               var5 = null;
               var1.setline(2401);
               var14 = Py.EmptyObjects;
               var18 = new PyFunction(var1.f_globals, var14, stopped$203, (PyObject)null);
               var1.setlocal(10, var18);
               var5 = null;
               var1.setline(2403);
               var1.getglobal("serve").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(10));
               var1.setline(2404);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(2405);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("-w"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2406);
               PyInteger var16 = Py.newInteger(1);
               var1.setlocal(5, var16);
               var5 = null;
            }
         }
      } catch (Throwable var9) {
         PyException var13 = Py.setException(var9, var1);
         if (!var13.match(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("error"), var1.getlocal(1)}))) {
            throw var13;
         }

         var1.setline(2427);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         var1.setlocal(13, var4);
         var4 = null;
         var1.setline(2428);
         Py.println(PyString.fromInterned("pydoc - the Python documentation tool\n\n%s <name> ...\n    Show text documentation on something.  <name> may be the name of a\n    Python keyword, topic, function, module, or package, or a dotted\n    reference to a class or function within a module or module in a\n    package.  If <name> contains a '%s', it is used as the path to a\n    Python source file to document. If name is 'keywords', 'topics',\n    or 'modules', a listing of these things is displayed.\n\n%s -k <keyword>\n    Search for a keyword in the synopsis lines of all available modules.\n\n%s -p <port>\n    Start an HTTP server on the given port on the local machine.\n\n%s -g\n    Pop up a graphical interface for finding and serving documentation.\n\n%s -w <name> ...\n    Write out the HTML documentation for a module to a file in the current\n    directory.  If <name> contains a '%s', it is treated as a filename; if\n    it names a directory, documentation is written for all the contents.\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(13), var1.getglobal("os").__getattr__("sep"), var1.getlocal(13), var1.getlocal(13), var1.getlocal(13), var1.getlocal(13), var1.getglobal("os").__getattr__("sep")})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BadUsage$201(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2373);
      return var1.getf_locals();
   }

   public PyObject ready$202(PyFrame var1, ThreadState var2) {
      var1.setline(2400);
      Py.println(PyString.fromInterned("pydoc server ready at %s")._mod(var1.getlocal(0).__getattr__("url")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopped$203(PyFrame var1, ThreadState var2) {
      var1.setline(2402);
      Py.println(PyString.fromInterned("pydoc server stopped"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public pydoc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      deque$1 = Py.newCode(0, var2, var1, "deque", 63, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      popleft$2 = Py.newCode(1, var2, var1, "popleft", 64, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dirs", "normdirs", "dir", "normdir"};
      pathdirs$3 = Py.newCode(0, var2, var1, "pathdirs", 71, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "result"};
      getdoc$4 = Py.newCode(1, var2, var1, "getdoc", 83, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"doc", "lines"};
      splitdoc$5 = Py.newCode(1, var2, var1, "splitdoc", 88, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "modname", "name"};
      classname$6 = Py.newCode(2, var2, var1, "classname", 97, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isdata$7 = Py.newCode(1, var2, var1, "isdata", 104, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "pairs"};
      replace$8 = Py.newCode(2, var2, var1, "replace", 110, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "maxlen", "pre", "post"};
      cram$9 = Py.newCode(2, var2, var1, "cram", 117, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      stripid$10 = Py.newCode(1, var2, var1, "stripid", 126, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj"};
      _is_some_method$11 = Py.newCode(1, var2, var1, "_is_some_method", 131, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cl", "methods", "key", "value", "base"};
      allmethods$12 = Py.newCode(1, var2, var1, "allmethods", 134, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "predicate", "yes", "no", "x"};
      _split_list$13 = Py.newCode(2, var2, var1, "_split_list", 144, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "all", "obj", "_hidden_names"};
      visiblename$14 = Py.newCode(3, var2, var1, "visiblename", 161, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "fixup"};
      classify_class_attrs$15 = Py.newCode(1, var2, var1, "classify_class_attrs", 178, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "name", "kind", "cls", "value"};
      fixup$16 = Py.newCode(1, var2, var1, "fixup", 180, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "ext"};
      ispackage$17 = Py.newCode(1, var2, var1, "ispackage", 189, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "line", "result"};
      source_synopsis$18 = Py.newCode(1, var2, var1, "source_synopsis", 197, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "cache", "mtime", "lastupdate", "result", "info", "file", "module"};
      synopsis$19 = Py.newCode(2, var2, var1, "synopsis", 214, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ErrorDuringImport$20 = Py.newCode(0, var2, var1, "ErrorDuringImport", 236, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "exc_info", "exc", "value", "tb"};
      __init__$21 = Py.newCode(3, var2, var1, "__init__", 238, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc"};
      __str__$22 = Py.newCode(1, var2, var1, "__str__", 245, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "magic", "file", "kind", "filename", "name", "ext", "module"};
      importfile$23 = Py.newCode(1, var2, var1, "importfile", 251, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "forceload", "cache", "subs", "_[291_24]", "m", "key", "module", "exc", "value", "tb", "info", "part"};
      safeimport$24 = Py.newCode(3, var2, var1, "safeimport", 270, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Doc$25 = Py.newCode(0, var2, var1, "Doc", 320, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "object", "name", "args"};
      document$26 = Py.newCode(4, var2, var1, "document", 321, true, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "args", "message"};
      fail$27 = Py.newCode(4, var2, var1, "fail", 339, true, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "file", "docloc", "basedir"};
      getdocloc$28 = Py.newCode(2, var2, var1, "getdocloc", 347, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTMLRepr$29 = Py.newCode(0, var2, var1, "HTMLRepr", 376, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$30 = Py.newCode(1, var2, var1, "__init__", 378, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      escape$31 = Py.newCode(2, var2, var1, "escape", 384, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      repr$32 = Py.newCode(2, var2, var1, "repr", 387, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "methodname"};
      repr1$33 = Py.newCode(3, var2, var1, "repr1", 390, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "test", "testrepr"};
      repr_string$34 = Py.newCode(3, var2, var1, "repr_string", 397, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_instance$35 = Py.newCode(3, var2, var1, "repr_instance", 410, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTMLDoc$36 = Py.newCode(0, var2, var1, "HTMLDoc", 418, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "title", "contents"};
      page$37 = Py.newCode(3, var2, var1, "page", 427, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "fgcol", "bgcol", "extras"};
      heading$38 = Py.newCode(5, var2, var1, "heading", 436, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "fgcol", "bgcol", "contents", "width", "prelude", "marginalia", "gap", "result"};
      section$39 = Py.newCode(9, var2, var1, "section", 447, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "args"};
      bigsection$40 = Py.newCode(3, var2, var1, "bigsection", 469, true, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      preformat$41 = Py.newCode(2, var2, var1, "preformat", 474, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "format", "cols", "result", "rows", "col", "i"};
      multicolumn$42 = Py.newCode(4, var2, var1, "multicolumn", 480, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      grey$43 = Py.newCode(2, var2, var1, "grey", 492, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "dicts", "dict"};
      namelink$44 = Py.newCode(3, var2, var1, "namelink", 494, true, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "modname", "name", "module"};
      classlink$45 = Py.newCode(3, var2, var1, "classlink", 501, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      modulelink$46 = Py.newCode(2, var2, var1, "modulelink", 509, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "name", "path", "ispackage", "shadowed", "url", "text"};
      modpkglink$47 = Py.newCode(2, var2, var1, "modpkglink", 513, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "escape", "funcs", "classes", "methods", "results", "here", "pattern", "match", "start", "end", "all", "scheme", "rfc", "pep", "selfdot", "name", "url"};
      markup$48 = Py.newCode(6, var2, var1, "markup", 528, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "modname", "parent", "result", "entry", "c", "bases", "parents", "base"};
      formattree$49 = Py.newCode(4, var2, var1, "formattree", 566, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "ignored", "all", "parts", "links", "i", "linkedname", "head", "path", "url", "nturl2path", "filelink", "info", "version", "docloc", "result", "modules", "classes", "cdict", "key", "value", "base", "modname", "module", "funcs", "fdict", "data", "doc", "modpkgs", "importer", "ispkg", "contents", "classlist"};
      docmodule$50 = Py.newCode(5, var2, var1, "docmodule", 585, true, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key_value", "s"};
      f$51 = Py.newCode(2, var2, var1, "<lambda>", 674, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key_value"};
      f$52 = Py.newCode(1, var2, var1, "<lambda>", 679, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "funcs", "classes", "ignored", "realname", "bases", "contents", "HorizontalRule", "mro", "base", "spill", "spilldescriptors", "spilldata", "attrs", "key", "kind", "homecls", "value", "anchor", "inherited", "tag", "title", "parents", "doc", "push", "mdict", "thisclass", "hr"};
      String[] var10001 = var2;
      pydoc$py var10007 = self;
      var2 = new String[]{"push", "mdict", "funcs", "self", "thisclass", "classes", "mod", "hr", "object"};
      docclass$53 = Py.newCode(7, var10001, var1, "docclass", 709, true, false, var10007, 53, var2, (String[])null, 4, 4097);
      var2 = new String[0];
      HorizontalRule$54 = Py.newCode(0, var2, var1, "HorizontalRule", 720, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$55 = Py.newCode(1, var2, var1, "__init__", 721, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"push"};
      maybe$56 = Py.newCode(1, var10001, var1, "maybe", 723, false, false, var10007, 56, (String[])null, var2, 0, 4097);
      var2 = new String[]{"msg", "attrs", "predicate", "ok", "name", "kind", "homecls", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"hr", "push", "object", "self", "mod", "funcs", "classes", "mdict"};
      spill$57 = Py.newCode(3, var10001, var1, "spill", 739, false, false, var10007, 57, (String[])null, var2, 0, 4097);
      var2 = new String[]{"msg", "attrs", "predicate", "ok", "name", "kind", "homecls", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"hr", "push", "self", "mod"};
      spilldescriptors$58 = Py.newCode(3, var10001, var1, "spilldescriptors", 757, false, false, var10007, 58, (String[])null, var2, 0, 4097);
      var2 = new String[]{"msg", "attrs", "predicate", "ok", "name", "kind", "homecls", "value", "base", "doc"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"hr", "push", "self", "object", "mod", "funcs", "classes", "mdict"};
      spilldata$59 = Py.newCode(3, var10001, var1, "spilldata", 766, false, false, var10007, 59, (String[])null, var2, 0, 4097);
      var2 = new String[]{"data"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"object"};
      f$60 = Py.newCode(1, var10001, var1, "<lambda>", 788, false, false, var10007, 60, (String[])null, var2, 0, 4097);
      var2 = new String[]{"t"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"thisclass"};
      f$61 = Py.newCode(1, var10001, var1, "<lambda>", 811, false, false, var10007, 61, (String[])null, var2, 0, 4097);
      var2 = new String[]{"t"};
      f$62 = Py.newCode(1, var2, var1, "<lambda>", 825, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t1", "t2"};
      f$63 = Py.newCode(2, var2, var1, "<lambda>", 827, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$64 = Py.newCode(1, var2, var1, "<lambda>", 831, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$65 = Py.newCode(1, var2, var1, "<lambda>", 833, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$66 = Py.newCode(1, var2, var1, "<lambda>", 835, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$67 = Py.newCode(1, var2, var1, "<lambda>", 837, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$68 = Py.newCode(1, var2, var1, "<lambda>", 839, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      formatvalue$69 = Py.newCode(2, var2, var1, "formatvalue", 861, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "funcs", "classes", "methods", "cl", "realname", "anchor", "note", "skipdocs", "imclass", "title", "reallink", "args", "varargs", "varkw", "defaults", "argspec", "decl", "doc"};
      docroutine$70 = Py.newCode(8, var2, var1, "docroutine", 865, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "mod", "results", "push", "doc"};
      _docdescriptor$71 = Py.newCode(4, var2, var1, "_docdescriptor", 919, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "cl"};
      docproperty$72 = Py.newCode(5, var2, var1, "docproperty", 932, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "ignored", "lhs"};
      docother$73 = Py.newCode(5, var2, var1, "docother", 936, true, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "cl"};
      docdata$74 = Py.newCode(5, var2, var1, "docdata", 941, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir", "shadowed", "modpkgs", "importer", "name", "ispkg", "contents"};
      index$75 = Py.newCode(3, var2, var1, "index", 945, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextRepr$76 = Py.newCode(0, var2, var1, "TextRepr", 959, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$77 = Py.newCode(1, var2, var1, "__init__", 961, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "methodname"};
      repr1$78 = Py.newCode(3, var2, var1, "repr1", 967, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level", "test", "testrepr"};
      repr_string$79 = Py.newCode(3, var2, var1, "repr_string", 974, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "level"};
      repr_instance$80 = Py.newCode(3, var2, var1, "repr_instance", 985, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextDoc$81 = Py.newCode(0, var2, var1, "TextDoc", 991, false, false, self, 81, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "text"};
      bold$82 = Py.newCode(2, var2, var1, "bold", 999, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ch"};
      f$83 = Py.newCode(1, var2, var1, "<lambda>", 1001, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "prefix", "lines"};
      indent$84 = Py.newCode(3, var2, var1, "indent", 1003, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "prefix"};
      f$85 = Py.newCode(2, var2, var1, "<lambda>", 1007, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "contents"};
      section$86 = Py.newCode(3, var2, var1, "section", 1011, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "modname", "parent", "prefix", "result", "entry", "c", "bases", "parents"};
      formattree$87 = Py.newCode(5, var2, var1, "formattree", 1017, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c", "m"};
      f$88 = Py.newCode(2, var2, var1, "<lambda>", 1025, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "synop", "desc", "result", "all", "file", "docloc", "classes", "key", "value", "funcs", "data", "modpkgs", "modpkgs_names", "importer", "modname", "ispkg", "submodules", "classlist", "contents", "version"};
      docmodule$89 = Py.newCode(4, var2, var1, "docmodule", 1033, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key_value"};
      f$90 = Py.newCode(1, var2, var1, "<lambda>", 1101, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "ignored", "realname", "bases", "makename", "title", "parents", "doc", "contents", "mro", "base", "HorizontalRule", "spill", "spilldescriptors", "spilldata", "attrs", "inherited", "tag", "push", "hr", "thisclass"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"object", "push", "mod", "hr", "self", "thisclass"};
      docclass$91 = Py.newCode(5, var10001, var1, "docclass", 1133, true, false, var10007, 91, var2, (String[])null, 3, 4097);
      var2 = new String[]{"c", "m"};
      makename$92 = Py.newCode(2, var2, var1, "makename", 1139, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HorizontalRule$93 = Py.newCode(0, var2, var1, "HorizontalRule", 1163, false, false, self, 93, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$94 = Py.newCode(1, var2, var1, "__init__", 1164, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"push"};
      maybe$95 = Py.newCode(1, var10001, var1, "maybe", 1166, false, false, var10007, 95, (String[])null, var2, 0, 4097);
      var2 = new String[]{"msg", "attrs", "predicate", "ok", "name", "kind", "homecls", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"hr", "push", "object", "self", "mod"};
      spill$96 = Py.newCode(3, var10001, var1, "spill", 1172, false, false, var10007, 96, (String[])null, var2, 0, 4097);
      var2 = new String[]{"msg", "attrs", "predicate", "ok", "name", "kind", "homecls", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"hr", "push", "self", "mod"};
      spilldescriptors$97 = Py.newCode(3, var10001, var1, "spilldescriptors", 1189, false, false, var10007, 97, (String[])null, var2, 0, 4097);
      var2 = new String[]{"msg", "attrs", "predicate", "ok", "name", "kind", "homecls", "value", "doc"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"hr", "push", "self", "object", "mod"};
      spilldata$98 = Py.newCode(3, var10001, var1, "spilldata", 1198, false, false, var10007, 98, (String[])null, var2, 0, 4097);
      var2 = new String[]{"data"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"object"};
      f$99 = Py.newCode(1, var10001, var1, "<lambda>", 1213, false, false, var10007, 99, (String[])null, var2, 0, 4097);
      var2 = new String[]{"t"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"thisclass"};
      f$100 = Py.newCode(1, var10001, var1, "<lambda>", 1220, false, false, var10007, 100, (String[])null, var2, 0, 4097);
      var2 = new String[]{"t"};
      f$101 = Py.newCode(1, var2, var1, "<lambda>", 1236, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$102 = Py.newCode(1, var2, var1, "<lambda>", 1238, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$103 = Py.newCode(1, var2, var1, "<lambda>", 1240, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$104 = Py.newCode(1, var2, var1, "<lambda>", 1242, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      f$105 = Py.newCode(1, var2, var1, "<lambda>", 1244, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      formatvalue$106 = Py.newCode(2, var2, var1, "formatvalue", 1253, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "cl", "realname", "note", "skipdocs", "imclass", "title", "args", "varargs", "varkw", "defaults", "argspec", "decl", "doc"};
      docroutine$107 = Py.newCode(5, var2, var1, "docroutine", 1257, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "mod", "results", "push", "doc"};
      _docdescriptor$108 = Py.newCode(4, var2, var1, "_docdescriptor", 1300, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "cl"};
      docproperty$109 = Py.newCode(5, var2, var1, "docproperty", 1313, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "cl"};
      docdata$110 = Py.newCode(5, var2, var1, "docdata", 1317, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "parent", "maxlen", "doc", "repr", "line", "chop"};
      docother$111 = Py.newCode(7, var2, var1, "docother", 1321, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AnsiDoc$112 = Py.newCode(0, var2, var1, "AnsiDoc", 1333, false, false, self, 112, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "text"};
      bold$113 = Py.newCode(2, var2, var1, "bold", 1336, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      pager$114 = Py.newCode(1, var2, var1, "pager", 1343, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      JLine2Pager$115 = Py.newCode(0, var2, var1, "JLine2Pager", 1350, false, false, self, 115, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      available$116 = Py.newCode(0, var2, var1, "available", 1353, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "ansi_codes"};
      __init__$117 = Py.newCode(2, var2, var1, "__init__", 1361, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      visible$118 = Py.newCode(1, var2, var1, "visible", 1375, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "can_go_back", "c"};
      handle_prompt$119 = Py.newCode(1, var2, var1, "handle_prompt", 1381, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "row_count", "line", "action"};
      page$120 = Py.newCode(1, var2, var1, "page", 1408, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tempfile", "fd", "filename"};
      getpager$121 = Py.newCode(0, var2, var1, "getpager", 1428, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$122 = Py.newCode(1, var2, var1, "<lambda>", 1431, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$123 = Py.newCode(1, var2, var1, "<lambda>", 1438, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$124 = Py.newCode(1, var2, var1, "<lambda>", 1440, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$125 = Py.newCode(1, var2, var1, "<lambda>", 1442, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$126 = Py.newCode(1, var2, var1, "<lambda>", 1446, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$127 = Py.newCode(1, var2, var1, "<lambda>", 1448, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      f$128 = Py.newCode(1, var2, var1, "<lambda>", 1455, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      plain$129 = Py.newCode(1, var2, var1, "plain", 1461, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "cmd", "pipe"};
      pipepager$130 = Py.newCode(2, var2, var1, "pipepager", 1465, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "cmd", "tempfile", "filename", "file"};
      tempfilepager$131 = Py.newCode(2, var2, var1, "tempfilepager", 1474, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "lines", "tty", "fd", "old", "getchar", "r", "inc", "c"};
      ttypager$132 = Py.newCode(1, var2, var1, "ttypager", 1486, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$133 = Py.newCode(0, var2, var1, "<lambda>", 1494, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$134 = Py.newCode(0, var2, var1, "<lambda>", 1497, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      plainpager$135 = Py.newCode(1, var2, var1, "plainpager", 1524, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"thing"};
      describe$136 = Py.newCode(1, var2, var1, "describe", 1528, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "forceload", "parts", "_[1559_13]", "part", "module", "n", "nextmodule", "object"};
      locate$137 = Py.newCode(2, var2, var1, "locate", 1557, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _OldStyleClass$138 = Py.newCode(0, var2, var1, "_OldStyleClass", 1584, false, false, self, 138, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"thing", "forceload", "object", "name"};
      resolve$139 = Py.newCode(2, var2, var1, "resolve", 1587, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"thing", "title", "forceload", "object", "name", "desc", "module"};
      render_doc$140 = Py.newCode(3, var2, var1, "render_doc", 1598, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"thing", "title", "forceload", "value"};
      doc$141 = Py.newCode(3, var2, var1, "doc", 1623, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"thing", "forceload", "object", "name", "page", "file", "value"};
      writedoc$142 = Py.newCode(2, var2, var1, "writedoc", 1630, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dir", "pkgpath", "done", "importer", "modname", "ispkg"};
      writedocs$143 = Py.newCode(3, var2, var1, "writedocs", 1642, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Helper$144 = Py.newCode(0, var2, var1, "Helper", 1649, false, false, self, 144, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "output"};
      __init__$145 = Py.newCode(3, var2, var1, "__init__", 1826, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$146 = Py.newCode(1, var2, var1, "<lambda>", 1830, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$147 = Py.newCode(1, var2, var1, "<lambda>", 1831, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$148 = Py.newCode(1, var2, var1, "__repr__", 1833, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      __call__$149 = Py.newCode(2, var2, var1, "__call__", 1840, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      interact$150 = Py.newCode(1, var2, var1, "interact", 1853, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prompt"};
      getline$151 = Py.newCode(2, var2, var1, "getline", 1865, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request"};
      help$152 = Py.newCode(2, var2, var1, "help", 1874, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      intro$153 = Py.newCode(1, var2, var1, "intro", 1892, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "columns", "width", "colw", "rows", "row", "col", "i"};
      list$154 = Py.newCode(4, var2, var1, "list", 1909, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      listkeywords$155 = Py.newCode(1, var2, var1, "listkeywords", 1923, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      listsymbols$156 = Py.newCode(1, var2, var1, "listsymbols", 1930, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      listtopics$157 = Py.newCode(1, var2, var1, "listtopics", 1938, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "topic", "more_xrefs", "pydoc_data", "target", "label", "xrefs", "doc", "StringIO", "formatter", "buffer"};
      showtopic$158 = Py.newCode(3, var2, var1, "showtopic", 1945, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "symbol", "target", "topic", "_", "xrefs"};
      showsymbol$159 = Py.newCode(2, var2, var1, "showsymbol", 1977, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "modules", "onerror", "callback"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"callback"};
      listmodules$160 = Py.newCode(2, var10001, var1, "listmodules", 1982, false, false, var10007, 160, var2, (String[])null, 1, 4097);
      var2 = new String[]{"path", "modname", "desc", "modules"};
      callback$161 = Py.newCode(4, var2, var1, "callback", 1995, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"modname"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"callback"};
      onerror$162 = Py.newCode(1, var10001, var1, "onerror", 2000, false, false, var10007, 162, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Scanner$163 = Py.newCode(0, var2, var1, "Scanner", 2011, false, false, self, 163, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "roots", "children", "descendp"};
      __init__$164 = Py.newCode(4, var2, var1, "__init__", 2013, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "root", "node", "children", "child"};
      next$165 = Py.newCode(1, var2, var1, "next", 2019, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ModuleScanner$166 = Py.newCode(0, var2, var1, "ModuleScanner", 2035, false, false, self, 166, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "callback", "key", "completer", "onerror", "seen", "modname", "desc", "importer", "ispkg", "loader", "StringIO", "path", "module"};
      run$167 = Py.newCode(5, var2, var1, "run", 2038, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key", "callback", "onerror"};
      apropos$168 = Py.newCode(1, var2, var1, "apropos", 2079, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "modname", "desc"};
      callback$169 = Py.newCode(3, var2, var1, "callback", 2081, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"modname"};
      onerror$170 = Py.newCode(1, var2, var1, "onerror", 2085, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"port", "callback", "completer", "BaseHTTPServer", "mimetools", "select", "Message", "DocHandler", "DocServer"};
      serve$171 = Py.newCode(3, var2, var1, "serve", 2093, false, false, self, 171, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Message$172 = Py.newCode(0, var2, var1, "Message", 2097, false, false, self, 172, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "seekable", "Message"};
      __init__$173 = Py.newCode(3, var2, var1, "__init__", 2098, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocHandler$174 = Py.newCode(0, var2, var1, "DocHandler", 2106, false, false, self, 174, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "title", "contents"};
      send_document$175 = Py.newCode(3, var2, var1, "send_document", 2107, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "obj", "value", "heading", "bltinlink", "names", "contents", "indices", "seen", "dir"};
      do_GET$176 = Py.newCode(1, var2, var1, "do_GET", 2115, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      bltinlink$177 = Py.newCode(1, var2, var1, "bltinlink", 2134, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$178 = Py.newCode(1, var2, var1, "<lambda>", 2136, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      log_message$179 = Py.newCode(2, var2, var1, "log_message", 2150, true, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocServer$180 = Py.newCode(0, var2, var1, "DocServer", 2152, false, false, self, 180, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "port", "callback", "host"};
      __init__$181 = Py.newCode(3, var2, var1, "__init__", 2153, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "select", "rd", "wr", "ex"};
      serve_until_quit$182 = Py.newCode(1, var2, var1, "serve_until_quit", 2160, false, false, self, 182, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      server_activate$183 = Py.newCode(1, var2, var1, "server_activate", 2167, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"GUI", "Tkinter", "root", "gui"};
      gui$184 = Py.newCode(0, var2, var1, "gui", 2184, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GUI$185 = Py.newCode(0, var2, var1, "GUI", 2186, false, false, self, 185, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "window", "port", "Tkinter", "font", "threading"};
      __init__$186 = Py.newCode(3, var2, var1, "__init__", 2187, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server"};
      ready$187 = Py.newCode(2, var2, var1, "ready", 2256, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event", "url", "webbrowser", "rc"};
      open$188 = Py.newCode(3, var2, var1, "open", 2263, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event"};
      quit$189 = Py.newCode(2, var2, var1, "quit", 2275, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event", "key", "threading"};
      search$190 = Py.newCode(2, var2, var1, "search", 2280, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "modname", "desc"};
      update$191 = Py.newCode(4, var2, var1, "update", 2298, false, false, self, 191, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event"};
      stop$192 = Py.newCode(2, var2, var1, "stop", 2304, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      done$193 = Py.newCode(1, var2, var1, "done", 2309, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event"};
      select$194 = Py.newCode(2, var2, var1, "select", 2317, false, false, self, 194, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event", "selection", "modname"};
      goto$195 = Py.newCode(2, var2, var1, "goto", 2320, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      collapse$196 = Py.newCode(1, var2, var1, "collapse", 2326, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      expand$197 = Py.newCode(1, var2, var1, "expand", 2337, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "event"};
      hide$198 = Py.newCode(2, var2, var1, "hide", 2346, false, false, self, 198, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      ispath$199 = Py.newCode(1, var2, var1, "ispath", 2367, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"getopt", "BadUsage", "scriptdir", "opts", "args", "writing", "opt", "val", "port", "ready", "stopped", "arg", "value", "cmd"};
      cli$200 = Py.newCode(0, var2, var1, "cli", 2370, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BadUsage$201 = Py.newCode(0, var2, var1, "BadUsage", 2373, false, false, self, 201, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"server"};
      ready$202 = Py.newCode(1, var2, var1, "ready", 2399, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      stopped$203 = Py.newCode(0, var2, var1, "stopped", 2401, false, false, self, 203, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pydoc$py("pydoc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pydoc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.deque$1(var2, var3);
         case 2:
            return this.popleft$2(var2, var3);
         case 3:
            return this.pathdirs$3(var2, var3);
         case 4:
            return this.getdoc$4(var2, var3);
         case 5:
            return this.splitdoc$5(var2, var3);
         case 6:
            return this.classname$6(var2, var3);
         case 7:
            return this.isdata$7(var2, var3);
         case 8:
            return this.replace$8(var2, var3);
         case 9:
            return this.cram$9(var2, var3);
         case 10:
            return this.stripid$10(var2, var3);
         case 11:
            return this._is_some_method$11(var2, var3);
         case 12:
            return this.allmethods$12(var2, var3);
         case 13:
            return this._split_list$13(var2, var3);
         case 14:
            return this.visiblename$14(var2, var3);
         case 15:
            return this.classify_class_attrs$15(var2, var3);
         case 16:
            return this.fixup$16(var2, var3);
         case 17:
            return this.ispackage$17(var2, var3);
         case 18:
            return this.source_synopsis$18(var2, var3);
         case 19:
            return this.synopsis$19(var2, var3);
         case 20:
            return this.ErrorDuringImport$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.__str__$22(var2, var3);
         case 23:
            return this.importfile$23(var2, var3);
         case 24:
            return this.safeimport$24(var2, var3);
         case 25:
            return this.Doc$25(var2, var3);
         case 26:
            return this.document$26(var2, var3);
         case 27:
            return this.fail$27(var2, var3);
         case 28:
            return this.getdocloc$28(var2, var3);
         case 29:
            return this.HTMLRepr$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this.escape$31(var2, var3);
         case 32:
            return this.repr$32(var2, var3);
         case 33:
            return this.repr1$33(var2, var3);
         case 34:
            return this.repr_string$34(var2, var3);
         case 35:
            return this.repr_instance$35(var2, var3);
         case 36:
            return this.HTMLDoc$36(var2, var3);
         case 37:
            return this.page$37(var2, var3);
         case 38:
            return this.heading$38(var2, var3);
         case 39:
            return this.section$39(var2, var3);
         case 40:
            return this.bigsection$40(var2, var3);
         case 41:
            return this.preformat$41(var2, var3);
         case 42:
            return this.multicolumn$42(var2, var3);
         case 43:
            return this.grey$43(var2, var3);
         case 44:
            return this.namelink$44(var2, var3);
         case 45:
            return this.classlink$45(var2, var3);
         case 46:
            return this.modulelink$46(var2, var3);
         case 47:
            return this.modpkglink$47(var2, var3);
         case 48:
            return this.markup$48(var2, var3);
         case 49:
            return this.formattree$49(var2, var3);
         case 50:
            return this.docmodule$50(var2, var3);
         case 51:
            return this.f$51(var2, var3);
         case 52:
            return this.f$52(var2, var3);
         case 53:
            return this.docclass$53(var2, var3);
         case 54:
            return this.HorizontalRule$54(var2, var3);
         case 55:
            return this.__init__$55(var2, var3);
         case 56:
            return this.maybe$56(var2, var3);
         case 57:
            return this.spill$57(var2, var3);
         case 58:
            return this.spilldescriptors$58(var2, var3);
         case 59:
            return this.spilldata$59(var2, var3);
         case 60:
            return this.f$60(var2, var3);
         case 61:
            return this.f$61(var2, var3);
         case 62:
            return this.f$62(var2, var3);
         case 63:
            return this.f$63(var2, var3);
         case 64:
            return this.f$64(var2, var3);
         case 65:
            return this.f$65(var2, var3);
         case 66:
            return this.f$66(var2, var3);
         case 67:
            return this.f$67(var2, var3);
         case 68:
            return this.f$68(var2, var3);
         case 69:
            return this.formatvalue$69(var2, var3);
         case 70:
            return this.docroutine$70(var2, var3);
         case 71:
            return this._docdescriptor$71(var2, var3);
         case 72:
            return this.docproperty$72(var2, var3);
         case 73:
            return this.docother$73(var2, var3);
         case 74:
            return this.docdata$74(var2, var3);
         case 75:
            return this.index$75(var2, var3);
         case 76:
            return this.TextRepr$76(var2, var3);
         case 77:
            return this.__init__$77(var2, var3);
         case 78:
            return this.repr1$78(var2, var3);
         case 79:
            return this.repr_string$79(var2, var3);
         case 80:
            return this.repr_instance$80(var2, var3);
         case 81:
            return this.TextDoc$81(var2, var3);
         case 82:
            return this.bold$82(var2, var3);
         case 83:
            return this.f$83(var2, var3);
         case 84:
            return this.indent$84(var2, var3);
         case 85:
            return this.f$85(var2, var3);
         case 86:
            return this.section$86(var2, var3);
         case 87:
            return this.formattree$87(var2, var3);
         case 88:
            return this.f$88(var2, var3);
         case 89:
            return this.docmodule$89(var2, var3);
         case 90:
            return this.f$90(var2, var3);
         case 91:
            return this.docclass$91(var2, var3);
         case 92:
            return this.makename$92(var2, var3);
         case 93:
            return this.HorizontalRule$93(var2, var3);
         case 94:
            return this.__init__$94(var2, var3);
         case 95:
            return this.maybe$95(var2, var3);
         case 96:
            return this.spill$96(var2, var3);
         case 97:
            return this.spilldescriptors$97(var2, var3);
         case 98:
            return this.spilldata$98(var2, var3);
         case 99:
            return this.f$99(var2, var3);
         case 100:
            return this.f$100(var2, var3);
         case 101:
            return this.f$101(var2, var3);
         case 102:
            return this.f$102(var2, var3);
         case 103:
            return this.f$103(var2, var3);
         case 104:
            return this.f$104(var2, var3);
         case 105:
            return this.f$105(var2, var3);
         case 106:
            return this.formatvalue$106(var2, var3);
         case 107:
            return this.docroutine$107(var2, var3);
         case 108:
            return this._docdescriptor$108(var2, var3);
         case 109:
            return this.docproperty$109(var2, var3);
         case 110:
            return this.docdata$110(var2, var3);
         case 111:
            return this.docother$111(var2, var3);
         case 112:
            return this.AnsiDoc$112(var2, var3);
         case 113:
            return this.bold$113(var2, var3);
         case 114:
            return this.pager$114(var2, var3);
         case 115:
            return this.JLine2Pager$115(var2, var3);
         case 116:
            return this.available$116(var2, var3);
         case 117:
            return this.__init__$117(var2, var3);
         case 118:
            return this.visible$118(var2, var3);
         case 119:
            return this.handle_prompt$119(var2, var3);
         case 120:
            return this.page$120(var2, var3);
         case 121:
            return this.getpager$121(var2, var3);
         case 122:
            return this.f$122(var2, var3);
         case 123:
            return this.f$123(var2, var3);
         case 124:
            return this.f$124(var2, var3);
         case 125:
            return this.f$125(var2, var3);
         case 126:
            return this.f$126(var2, var3);
         case 127:
            return this.f$127(var2, var3);
         case 128:
            return this.f$128(var2, var3);
         case 129:
            return this.plain$129(var2, var3);
         case 130:
            return this.pipepager$130(var2, var3);
         case 131:
            return this.tempfilepager$131(var2, var3);
         case 132:
            return this.ttypager$132(var2, var3);
         case 133:
            return this.f$133(var2, var3);
         case 134:
            return this.f$134(var2, var3);
         case 135:
            return this.plainpager$135(var2, var3);
         case 136:
            return this.describe$136(var2, var3);
         case 137:
            return this.locate$137(var2, var3);
         case 138:
            return this._OldStyleClass$138(var2, var3);
         case 139:
            return this.resolve$139(var2, var3);
         case 140:
            return this.render_doc$140(var2, var3);
         case 141:
            return this.doc$141(var2, var3);
         case 142:
            return this.writedoc$142(var2, var3);
         case 143:
            return this.writedocs$143(var2, var3);
         case 144:
            return this.Helper$144(var2, var3);
         case 145:
            return this.__init__$145(var2, var3);
         case 146:
            return this.f$146(var2, var3);
         case 147:
            return this.f$147(var2, var3);
         case 148:
            return this.__repr__$148(var2, var3);
         case 149:
            return this.__call__$149(var2, var3);
         case 150:
            return this.interact$150(var2, var3);
         case 151:
            return this.getline$151(var2, var3);
         case 152:
            return this.help$152(var2, var3);
         case 153:
            return this.intro$153(var2, var3);
         case 154:
            return this.list$154(var2, var3);
         case 155:
            return this.listkeywords$155(var2, var3);
         case 156:
            return this.listsymbols$156(var2, var3);
         case 157:
            return this.listtopics$157(var2, var3);
         case 158:
            return this.showtopic$158(var2, var3);
         case 159:
            return this.showsymbol$159(var2, var3);
         case 160:
            return this.listmodules$160(var2, var3);
         case 161:
            return this.callback$161(var2, var3);
         case 162:
            return this.onerror$162(var2, var3);
         case 163:
            return this.Scanner$163(var2, var3);
         case 164:
            return this.__init__$164(var2, var3);
         case 165:
            return this.next$165(var2, var3);
         case 166:
            return this.ModuleScanner$166(var2, var3);
         case 167:
            return this.run$167(var2, var3);
         case 168:
            return this.apropos$168(var2, var3);
         case 169:
            return this.callback$169(var2, var3);
         case 170:
            return this.onerror$170(var2, var3);
         case 171:
            return this.serve$171(var2, var3);
         case 172:
            return this.Message$172(var2, var3);
         case 173:
            return this.__init__$173(var2, var3);
         case 174:
            return this.DocHandler$174(var2, var3);
         case 175:
            return this.send_document$175(var2, var3);
         case 176:
            return this.do_GET$176(var2, var3);
         case 177:
            return this.bltinlink$177(var2, var3);
         case 178:
            return this.f$178(var2, var3);
         case 179:
            return this.log_message$179(var2, var3);
         case 180:
            return this.DocServer$180(var2, var3);
         case 181:
            return this.__init__$181(var2, var3);
         case 182:
            return this.serve_until_quit$182(var2, var3);
         case 183:
            return this.server_activate$183(var2, var3);
         case 184:
            return this.gui$184(var2, var3);
         case 185:
            return this.GUI$185(var2, var3);
         case 186:
            return this.__init__$186(var2, var3);
         case 187:
            return this.ready$187(var2, var3);
         case 188:
            return this.open$188(var2, var3);
         case 189:
            return this.quit$189(var2, var3);
         case 190:
            return this.search$190(var2, var3);
         case 191:
            return this.update$191(var2, var3);
         case 192:
            return this.stop$192(var2, var3);
         case 193:
            return this.done$193(var2, var3);
         case 194:
            return this.select$194(var2, var3);
         case 195:
            return this.goto$195(var2, var3);
         case 196:
            return this.collapse$196(var2, var3);
         case 197:
            return this.expand$197(var2, var3);
         case 198:
            return this.hide$198(var2, var3);
         case 199:
            return this.ispath$199(var2, var3);
         case 200:
            return this.cli$200(var2, var3);
         case 201:
            return this.BadUsage$201(var2, var3);
         case 202:
            return this.ready$202(var2, var3);
         case 203:
            return this.stopped$203(var2, var3);
         default:
            return null;
      }
   }
}
