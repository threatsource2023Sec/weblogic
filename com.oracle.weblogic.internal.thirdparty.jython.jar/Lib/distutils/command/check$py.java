package distutils.command;

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
@Filename("distutils/command/check.py")
public class check$py extends PyFunctionTable implements PyRunnable {
   static check$py self;
   static final PyCode f$0;
   static final PyCode SilentReporter$1;
   static final PyCode __init__$2;
   static final PyCode system_message$3;
   static final PyCode check$4;
   static final PyCode initialize_options$5;
   static final PyCode finalize_options$6;
   static final PyCode warn$7;
   static final PyCode run$8;
   static final PyCode check_metadata$9;
   static final PyCode check_restructuredtext$10;
   static final PyCode _check_rst_data$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.check\n\nImplements the Distutils 'check' command.\n"));
      var1.setline(4);
      PyString.fromInterned("distutils.command.check\n\nImplements the Distutils 'check' command.\n");
      var1.setline(5);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(7);
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(8);
      var6 = new String[]{"PKG_INFO_ENCODING"};
      var7 = imp.importFrom("distutils.dist", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("PKG_INFO_ENCODING", var4);
      var4 = null;
      var1.setline(9);
      var6 = new String[]{"DistutilsSetupError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;

      try {
         var1.setline(13);
         var6 = new String[]{"Reporter"};
         var7 = imp.importFrom("docutils.utils", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("Reporter", var4);
         var4 = null;
         var1.setline(14);
         var6 = new String[]{"Parser"};
         var7 = imp.importFrom("docutils.parsers.rst", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("Parser", var4);
         var4 = null;
         var1.setline(15);
         var6 = new String[]{"frontend"};
         var7 = imp.importFrom("docutils", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("frontend", var4);
         var4 = null;
         var1.setline(16);
         var6 = new String[]{"nodes"};
         var7 = imp.importFrom("docutils", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("nodes", var4);
         var4 = null;
         var1.setline(17);
         var6 = new String[]{"StringIO"};
         var7 = imp.importFrom("StringIO", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("StringIO", var4);
         var4 = null;
         var1.setline(19);
         var7 = new PyObject[]{var1.getname("Reporter")};
         var4 = Py.makeClass("SilentReporter", var7, SilentReporter$1);
         var1.setlocal("SilentReporter", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(33);
         PyObject var9 = var1.getname("True");
         var1.setlocal("HAS_DOCUTILS", var9);
         var3 = null;
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(36);
         var4 = var1.getname("False");
         var1.setlocal("HAS_DOCUTILS", var4);
         var4 = null;
      }

      var1.setline(38);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("check", var7, check$4);
      var1.setlocal("check", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SilentReporter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0), PyString.fromInterned("ascii"), PyString.fromInterned("replace")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, system_message$3, (PyObject)null);
      var1.setlocal("system_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"messages", var3);
      var3 = null;
      var1.setline(24);
      PyObject var10000 = var1.getglobal("Reporter").__getattr__("__init__");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
      var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject system_message$3(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      var1.getlocal(0).__getattr__("messages").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})));
      var1.setline(29);
      PyObject var10000 = var1.getglobal("nodes").__getattr__("system_message");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getlocal(0).__getattr__("levels").__getitem__(var1.getlocal(1))};
      String[] var4 = new String[]{"level", "type"};
      var10000 = var10000._callextra(var3, var4, var1.getlocal(3), var1.getlocal(4));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject check$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This command checks the meta-data of the package.\n    "));
      var1.setline(40);
      PyString.fromInterned("This command checks the meta-data of the package.\n    ");
      var1.setline(41);
      PyString var3 = PyString.fromInterned("perform some checks on the package");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(42);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("metadata"), PyString.fromInterned("m"), PyString.fromInterned("Verify meta-data")}), new PyTuple(new PyObject[]{PyString.fromInterned("restructuredtext"), PyString.fromInterned("r"), PyString.fromInterned("Checks if long string meta-data syntax are reStructuredText-compliant")}), new PyTuple(new PyObject[]{PyString.fromInterned("strict"), PyString.fromInterned("s"), PyString.fromInterned("Will exit with an error if a check fails")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(49);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("metadata"), PyString.fromInterned("restructuredtext"), PyString.fromInterned("strict")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(51);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$5, PyString.fromInterned("Sets default values for options."));
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(58);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$6, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(61);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, warn$7, PyString.fromInterned("Counts the number of warnings that occurs."));
      var1.setlocal("warn", var6);
      var3 = null;
      var1.setline(66);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$8, PyString.fromInterned("Runs the command."));
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(82);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, check_metadata$9, PyString.fromInterned("Ensures that all required elements of meta-data are supplied.\n\n        name, version, URL, (author and author_email) or\n        (maintainer and maintainer_email)).\n\n        Warns if any are missing.\n        "));
      var1.setlocal("check_metadata", var6);
      var3 = null;
      var1.setline(112);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, check_restructuredtext$10, PyString.fromInterned("Checks if the long string fields are reST-compliant."));
      var1.setlocal("check_restructuredtext", var6);
      var3 = null;
      var1.setline(125);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _check_rst_data$11, PyString.fromInterned("Returns warnings when the provided data doesn't compile."));
      var1.setlocal("_check_rst_data", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$5(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("Sets default values for options.");
      var1.setline(53);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"restructuredtext", var3);
      var3 = null;
      var1.setline(54);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"metadata", var3);
      var3 = null;
      var1.setline(55);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"strict", var3);
      var3 = null;
      var1.setline(56);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_warnings", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$6(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warn$7(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("Counts the number of warnings that occurs.");
      var1.setline(63);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "_warnings";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(64);
      PyObject var6 = var1.getglobal("Command").__getattr__("warn").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject run$8(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Runs the command.");
      var1.setline(69);
      if (var1.getlocal(0).__getattr__("metadata").__nonzero__()) {
         var1.setline(70);
         var1.getlocal(0).__getattr__("check_metadata").__call__(var2);
      }

      var1.setline(71);
      if (var1.getlocal(0).__getattr__("restructuredtext").__nonzero__()) {
         var1.setline(72);
         if (var1.getglobal("HAS_DOCUTILS").__nonzero__()) {
            var1.setline(73);
            var1.getlocal(0).__getattr__("check_restructuredtext").__call__(var2);
         } else {
            var1.setline(74);
            if (var1.getlocal(0).__getattr__("strict").__nonzero__()) {
               var1.setline(75);
               throw Py.makeException(var1.getglobal("DistutilsSetupError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The docutils package is needed.")));
            }
         }
      }

      var1.setline(79);
      PyObject var10000 = var1.getlocal(0).__getattr__("strict");
      if (var10000.__nonzero__()) {
         PyObject var3 = var1.getlocal(0).__getattr__("_warnings");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(80);
         throw Py.makeException(var1.getglobal("DistutilsSetupError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Please correct your package.")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject check_metadata$9(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Ensures that all required elements of meta-data are supplied.\n\n        name, version, URL, (author and author_email) or\n        (maintainer and maintainer_email)).\n\n        Warns if any are missing.\n        ");
      var1.setline(90);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("metadata");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(92);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(93);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("version"), PyString.fromInterned("url")})).__iter__();

      while(true) {
         var1.setline(93);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(97);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(98);
               var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("missing required meta-data: %s")._mod(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(2))));
            }

            var1.setline(99);
            if (var1.getlocal(1).__getattr__("author").__nonzero__()) {
               var1.setline(100);
               if (var1.getlocal(1).__getattr__("author_email").__not__().__nonzero__()) {
                  var1.setline(101);
                  var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("missing meta-data: if 'author' supplied, ")._add(PyString.fromInterned("'author_email' must be supplied too")));
               }
            } else {
               var1.setline(103);
               if (var1.getlocal(1).__getattr__("maintainer").__nonzero__()) {
                  var1.setline(104);
                  if (var1.getlocal(1).__getattr__("maintainer_email").__not__().__nonzero__()) {
                     var1.setline(105);
                     var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("missing meta-data: if 'maintainer' supplied, ")._add(PyString.fromInterned("'maintainer_email' must be supplied too")));
                  }
               } else {
                  var1.setline(108);
                  var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("missing meta-data: either (author and author_email) ")._add(PyString.fromInterned("or (maintainer and maintainer_email) "))._add(PyString.fromInterned("must be supplied")));
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(94);
         PyObject var10000 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(95);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject check_restructuredtext$10(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyString.fromInterned("Checks if the long string fields are reST-compliant.");
      var1.setline(114);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_long_description").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(115);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__not__().__nonzero__()) {
         var1.setline(116);
         var3 = var1.getlocal(1).__getattr__("decode").__call__(var2, var1.getglobal("PKG_INFO_ENCODING"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(117);
      var3 = var1.getlocal(0).__getattr__("_check_rst_data").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(117);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(118);
         PyObject var5 = var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("line"));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(119);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(120);
            var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(122);
            var5 = PyString.fromInterned("%s (line %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(3)}));
            var1.setlocal(2, var5);
            var5 = null;
         }

         var1.setline(123);
         var1.getlocal(0).__getattr__("warn").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject _check_rst_data$11(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Returns warnings when the provided data doesn't compile.");
      var1.setline(127);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getglobal("Parser").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(129);
      PyObject var10000 = var1.getglobal("frontend").__getattr__("OptionParser");
      PyObject[] var6 = new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("Parser")})};
      String[] var4 = new String[]{"components"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000.__getattr__("get_default_values").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(130);
      PyInteger var8 = Py.newInteger(4);
      var1.getlocal(4).__setattr__((String)"tab_width", var8);
      var3 = null;
      var1.setline(131);
      var3 = var1.getglobal("None");
      var1.getlocal(4).__setattr__("pep_references", var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getglobal("None");
      var1.getlocal(4).__setattr__("rfc_references", var3);
      var3 = null;
      var1.setline(133);
      var10000 = var1.getglobal("SilentReporter");
      var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(4).__getattr__("report_level"), var1.getlocal(4).__getattr__("halt_level"), var1.getlocal(4).__getattr__("warning_stream"), var1.getlocal(4).__getattr__("debug"), var1.getlocal(4).__getattr__("error_encoding"), var1.getlocal(4).__getattr__("error_encoding_error_handler")};
      var4 = new String[]{"stream", "debug", "encoding", "error_handler"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(141);
      var10000 = var1.getglobal("nodes").__getattr__("document");
      var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(2)};
      var4 = new String[]{"source"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(142);
      var1.getlocal(6).__getattr__("note_source").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(-1));

      try {
         var1.setline(144);
         var1.getlocal(3).__getattr__("parse").__call__(var2, var1.getlocal(1), var1.getlocal(6));
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getglobal("AttributeError"))) {
            throw var9;
         }

         PyObject var7 = var9.value;
         var1.setlocal(7, var7);
         var4 = null;
         var1.setline(146);
         var1.getlocal(5).__getattr__("messages").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(-1), PyString.fromInterned("Could not finish the parsing: %s.")._mod(var1.getlocal(7)), PyString.fromInterned(""), new PyDictionary(Py.EmptyObjects)})));
      }

      var1.setline(149);
      var3 = var1.getlocal(5).__getattr__("messages");
      var1.f_lasti = -1;
      return var3;
   }

   public check$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SilentReporter$1 = Py.newCode(0, var2, var1, "SilentReporter", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "report_level", "halt_level", "stream", "debug", "encoding", "error_handler"};
      __init__$2 = Py.newCode(8, var2, var1, "__init__", 21, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "message", "children", "kwargs"};
      system_message$3 = Py.newCode(5, var2, var1, "system_message", 27, true, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      check$4 = Py.newCode(0, var2, var1, "check", 38, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$5 = Py.newCode(1, var2, var1, "initialize_options", 51, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$6 = Py.newCode(1, var2, var1, "finalize_options", 58, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      warn$7 = Py.newCode(2, var2, var1, "warn", 61, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$8 = Py.newCode(1, var2, var1, "run", 66, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "metadata", "missing", "attr"};
      check_metadata$9 = Py.newCode(1, var2, var1, "check_metadata", 82, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "warning", "line"};
      check_restructuredtext$10 = Py.newCode(1, var2, var1, "check_restructuredtext", 112, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "source_path", "parser", "settings", "reporter", "document", "e"};
      _check_rst_data$11 = Py.newCode(2, var2, var1, "_check_rst_data", 125, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new check$py("distutils/command/check$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(check$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SilentReporter$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.system_message$3(var2, var3);
         case 4:
            return this.check$4(var2, var3);
         case 5:
            return this.initialize_options$5(var2, var3);
         case 6:
            return this.finalize_options$6(var2, var3);
         case 7:
            return this.warn$7(var2, var3);
         case 8:
            return this.run$8(var2, var3);
         case 9:
            return this.check_metadata$9(var2, var3);
         case 10:
            return this.check_restructuredtext$10(var2, var3);
         case 11:
            return this._check_rst_data$11(var2, var3);
         default:
            return null;
      }
   }
}
