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
@Filename("cgitb.py")
public class cgitb$py extends PyFunctionTable implements PyRunnable {
   static cgitb$py self;
   static final PyCode f$0;
   static final PyCode reset$1;
   static final PyCode small$2;
   static final PyCode strong$3;
   static final PyCode grey$4;
   static final PyCode lookup$5;
   static final PyCode scanvars$6;
   static final PyCode html$7;
   static final PyCode f$8;
   static final PyCode reader$9;
   static final PyCode text$10;
   static final PyCode f$11;
   static final PyCode reader$12;
   static final PyCode Hook$13;
   static final PyCode __init__$14;
   static final PyCode __call__$15;
   static final PyCode handle$16;
   static final PyCode enable$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("More comprehensive traceback formatting for Python scripts.\n\nTo enable this module, do:\n\n    import cgitb; cgitb.enable()\n\nat the top of your script.  The optional arguments to enable() are:\n\n    display     - if true, tracebacks are displayed in the web browser\n    logdir      - if set, tracebacks are written to files in this directory\n    context     - number of lines of source code to show for each stack frame\n    format      - 'text' or 'html' controls the output format\n\nBy default, tracebacks are displayed but not saved, the context is 5 lines\nand the output format is 'html' (for backwards compatibility with the\noriginal use of this module)\n\nAlternatively, if you have caught an exception and want cgitb to display it\nfor you, call cgitb.handler().  The optional argument to handler() is a\n3-item tuple (etype, evalue, etb) just like the value of sys.exc_info().\nThe default handler displays output as HTML.\n\n"));
      var1.setline(23);
      PyString.fromInterned("More comprehensive traceback formatting for Python scripts.\n\nTo enable this module, do:\n\n    import cgitb; cgitb.enable()\n\nat the top of your script.  The optional arguments to enable() are:\n\n    display     - if true, tracebacks are displayed in the web browser\n    logdir      - if set, tracebacks are written to files in this directory\n    context     - number of lines of source code to show for each stack frame\n    format      - 'text' or 'html' controls the output format\n\nBy default, tracebacks are displayed but not saved, the context is 5 lines\nand the output format is 'html' (for backwards compatibility with the\noriginal use of this module)\n\nAlternatively, if you have caught an exception and want cgitb to display it\nfor you, call cgitb.handler().  The optional argument to handler() is a\n3-item tuple (etype, evalue, etb) just like the value of sys.exc_info().\nThe default handler displays output as HTML.\n\n");
      var1.setline(24);
      PyObject var3 = imp.importOne("inspect", var1, -1);
      var1.setlocal("inspect", var3);
      var3 = null;
      var1.setline(25);
      var3 = imp.importOne("keyword", var1, -1);
      var1.setlocal("keyword", var3);
      var3 = null;
      var1.setline(26);
      var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var3);
      var3 = null;
      var1.setline(27);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(28);
      var3 = imp.importOne("pydoc", var1, -1);
      var1.setlocal("pydoc", var3);
      var3 = null;
      var1.setline(29);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(30);
      var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var3);
      var3 = null;
      var1.setline(31);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(32);
      var3 = imp.importOne("tokenize", var1, -1);
      var1.setlocal("tokenize", var3);
      var3 = null;
      var1.setline(33);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(34);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(36);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, reset$1, PyString.fromInterned("Return a string that resets the CGI and browser to a known state."));
      var1.setlocal("reset", var6);
      var3 = null;
      var1.setline(46);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal("__UNDEF__", var7);
      var3 = null;
      var1.setline(47);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, small$2, (PyObject)null);
      var1.setlocal("small", var6);
      var3 = null;
      var1.setline(53);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, strong$3, (PyObject)null);
      var1.setlocal("strong", var6);
      var3 = null;
      var1.setline(59);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, grey$4, (PyObject)null);
      var1.setlocal("grey", var6);
      var3 = null;
      var1.setline(65);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, lookup$5, PyString.fromInterned("Find the value for a given name in the given environment."));
      var1.setlocal("lookup", var6);
      var3 = null;
      var1.setline(81);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, scanvars$6, PyString.fromInterned("Scan one logical line of Python and look up values of variables used."));
      var1.setlocal("scanvars", var6);
      var3 = null;
      var1.setline(102);
      var5 = new PyObject[]{Py.newInteger(5)};
      var6 = new PyFunction(var1.f_globals, var5, html$7, PyString.fromInterned("Return a nice HTML document describing a given traceback."));
      var1.setlocal("html", var6);
      var3 = null;
      var1.setline(193);
      var5 = new PyObject[]{Py.newInteger(5)};
      var6 = new PyFunction(var1.f_globals, var5, text$10, PyString.fromInterned("Return a plain text document describing a given traceback."));
      var1.setlocal("text", var6);
      var3 = null;
      var1.setline(259);
      var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Hook", var5, Hook$13);
      var1.setlocal("Hook", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(315);
      var3 = var1.getname("Hook").__call__(var2).__getattr__("handle");
      var1.setlocal("handler", var3);
      var3 = null;
      var1.setline(316);
      var5 = new PyObject[]{Py.newInteger(1), var1.getname("None"), Py.newInteger(5), PyString.fromInterned("html")};
      var6 = new PyFunction(var1.f_globals, var5, enable$17, PyString.fromInterned("Install an exception handler that formats tracebacks as HTML.\n\n    The optional argument 'display' can be set to 0 to suppress sending the\n    traceback to the browser, and 'logdir' can be set to a directory to cause\n    tracebacks to be written to files there."));
      var1.setlocal("enable", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$1(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyString.fromInterned("Return a string that resets the CGI and browser to a known state.");
      var1.setline(38);
      PyString var3 = PyString.fromInterned("<!--: spam\nContent-Type: text/html\n\n<body bgcolor=\"#f0f0f8\"><font color=\"#f0f0f8\" size=\"-5\"> -->\n<body bgcolor=\"#f0f0f8\"><font color=\"#f0f0f8\" size=\"-5\"> --> -->\n</font> </font> </font> </script> </object> </blockquote> </pre>\n</table> </table> </table> </table> </table> </font> </font> </font>");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject small$2(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(49);
         PyObject var4 = PyString.fromInterned("<small>")._add(var1.getlocal(0))._add(PyString.fromInterned("</small>"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(51);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject strong$3(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(55);
         PyObject var4 = PyString.fromInterned("<strong>")._add(var1.getlocal(0))._add(PyString.fromInterned("</strong>"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(57);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject grey$4(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(61);
         PyObject var4 = PyString.fromInterned("<font color=\"#909090\">")._add(var1.getlocal(0))._add(PyString.fromInterned("</font>"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(63);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject lookup$5(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Find the value for a given name in the given environment.");
      var1.setline(67);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var5 = new PyTuple(new PyObject[]{PyString.fromInterned("local"), var1.getlocal(2).__getitem__(var1.getlocal(0))});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(69);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._in(var1.getlocal(1).__getattr__("f_globals"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(70);
            var5 = new PyTuple(new PyObject[]{PyString.fromInterned("global"), var1.getlocal(1).__getattr__("f_globals").__getitem__(var1.getlocal(0))});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(71);
            PyString var6 = PyString.fromInterned("__builtins__");
            var10000 = var6._in(var1.getlocal(1).__getattr__("f_globals"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(72);
               var4 = var1.getlocal(1).__getattr__("f_globals").__getitem__(PyString.fromInterned("__builtins__"));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(73);
               var4 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
               var10000 = var4._is(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects))));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(74);
                  var4 = var1.getlocal(0);
                  var10000 = var4._in(var1.getlocal(3));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(75);
                     var5 = new PyTuple(new PyObject[]{PyString.fromInterned("builtin"), var1.getlocal(3).__getitem__(var1.getlocal(0))});
                     var1.f_lasti = -1;
                     return var5;
                  }
               } else {
                  var1.setline(77);
                  if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(3), var1.getlocal(0)).__nonzero__()) {
                     var1.setline(78);
                     var5 = new PyTuple(new PyObject[]{PyString.fromInterned("builtin"), var1.getglobal("getattr").__call__(var2, var1.getlocal(3), var1.getlocal(0))});
                     var1.f_lasti = -1;
                     return var5;
                  }
               }
            }

            var1.setline(79);
            var5 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("__UNDEF__")});
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject scanvars$6(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Scan one logical line of Python and look up values of variables used.");
      var1.setline(83);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), var1.getglobal("None"), var1.getglobal("None"), PyString.fromInterned(""), var1.getglobal("__UNDEF__")});
      PyObject[] var4 = Py.unpackSequence(var3, 5);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(84);
      PyObject var8 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(84);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            break;
         }

         PyObject[] var10 = Py.unpackSequence(var9, 5);
         PyObject var6 = var10[0];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var10[2];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var10[3];
         var1.setlocal(11, var6);
         var6 = null;
         var6 = var10[4];
         var1.setlocal(12, var6);
         var6 = null;
         var1.setline(85);
         var5 = var1.getlocal(8);
         PyObject var10000 = var5._eq(var1.getglobal("tokenize").__getattr__("NEWLINE"));
         var5 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(86);
         var5 = var1.getlocal(8);
         var10000 = var5._eq(var1.getglobal("tokenize").__getattr__("NAME"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(9);
            var10000 = var5._notin(var1.getglobal("keyword").__getattr__("kwlist"));
            var5 = null;
         }

         PyObject var7;
         PyObject[] var11;
         if (var10000.__nonzero__()) {
            var1.setline(87);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(PyString.fromInterned("."));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(88);
               var5 = var1.getlocal(5);
               var10000 = var5._isnot(var1.getglobal("__UNDEF__"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(89);
                  var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(5), var1.getlocal(9), var1.getglobal("__UNDEF__"));
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(90);
                  var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6)._add(var1.getlocal(9)), var1.getlocal(6), var1.getlocal(7)})));
               }
            } else {
               var1.setline(92);
               var5 = var1.getglobal("lookup").__call__(var2, var1.getlocal(9), var1.getlocal(1), var1.getlocal(2));
               var11 = Py.unpackSequence(var5, 2);
               var7 = var11[0];
               var1.setlocal(13, var7);
               var7 = null;
               var7 = var11[1];
               var1.setlocal(7, var7);
               var7 = null;
               var5 = null;
               var1.setline(93);
               var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(13), var1.getlocal(7)})));
            }
         } else {
            var1.setline(94);
            var5 = var1.getlocal(9);
            var10000 = var5._eq(PyString.fromInterned("."));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(95);
               var5 = var1.getlocal(6);
               var5 = var5._iadd(var1.getlocal(4)._add(PyString.fromInterned(".")));
               var1.setlocal(6, var5);
               var1.setline(96);
               var5 = var1.getlocal(7);
               var1.setlocal(5, var5);
               var5 = null;
            } else {
               var1.setline(98);
               PyTuple var12 = new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("")});
               var11 = Py.unpackSequence(var12, 2);
               var7 = var11[0];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var11[1];
               var1.setlocal(6, var7);
               var7 = null;
               var5 = null;
            }
         }

         var1.setline(99);
         var5 = var1.getlocal(9);
         var1.setlocal(4, var5);
         var5 = null;
      }

      var1.setline(100);
      var8 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject html$7(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("Return a nice HTML document describing a given traceback.");
      var1.setline(104);
      PyObject var3 = var1.getlocal(0);
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
      var1.setline(105);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._is(var1.getglobal("types").__getattr__("ClassType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(106);
         var3 = var1.getlocal(2).__getattr__("__name__");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(107);
      var3 = PyString.fromInterned("Python ")._add(var1.getglobal("sys").__getattr__("version").__getattr__("split").__call__(var2).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned(": "))._add(var1.getglobal("sys").__getattr__("executable"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getglobal("time").__getattr__("ctime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(109);
      var3 = PyString.fromInterned("<body bgcolor=\"#f0f0f8\">")._add(var1.getglobal("pydoc").__getattr__("html").__getattr__("heading").__call__(var2, PyString.fromInterned("<big><big>%s</big></big>")._mod(var1.getglobal("strong").__call__(var2, var1.getglobal("pydoc").__getattr__("html").__getattr__("escape").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2))))), PyString.fromInterned("#ffffff"), PyString.fromInterned("#6622aa"), var1.getlocal(5)._add(PyString.fromInterned("<br>"))._add(var1.getlocal(6))))._add(PyString.fromInterned("\n<p>A problem occurred in a Python script.  Here is the sequence of\nfunction calls leading up to the error, in the order they occurred.</p>"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(116);
      var3 = PyString.fromInterned("<tt>")._add(var1.getglobal("small").__call__(var2, PyString.fromInterned("&nbsp;")._mul(Py.newInteger(5))))._add(PyString.fromInterned("&nbsp;</tt>"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(117);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setlocal(9, var11);
      var3 = null;
      var1.setline(118);
      var3 = var1.getglobal("inspect").__getattr__("getinnerframes").__call__(var2, var1.getlocal(4), var1.getlocal(1));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getlocal(10).__iter__();

      while(true) {
         var1.setline(119);
         PyObject var9 = var3.__iternext__();
         if (var9 == null) {
            var1.setline(173);
            var11 = new PyList(new PyObject[]{PyString.fromInterned("<p>%s: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("strong").__call__(var2, var1.getglobal("pydoc").__getattr__("html").__getattr__("escape").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2)))), var1.getglobal("pydoc").__getattr__("html").__getattr__("escape").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3)))}))});
            var1.setlocal(33, var11);
            var3 = null;
            var1.setline(175);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("BaseException")).__nonzero__()) {
               var1.setline(176);
               var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(3)).__iter__();

               while(true) {
                  var1.setline(176);
                  var9 = var3.__iternext__();
                  if (var9 == null) {
                     break;
                  }

                  var1.setlocal(30, var9);
                  var1.setline(177);
                  var5 = var1.getlocal(30).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                  var10000 = var5._eq(PyString.fromInterned("_"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(178);
                     var5 = var1.getglobal("pydoc").__getattr__("html").__getattr__("repr").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(3), var1.getlocal(30)));
                     var1.setlocal(32, var5);
                     var5 = null;
                     var1.setline(179);
                     var1.getlocal(33).__getattr__("append").__call__(var2, PyString.fromInterned("\n<br>%s%s&nbsp;=\n%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(30), var1.getlocal(32)})));
                  }
               }
            }

            var1.setline(181);
            var3 = var1.getlocal(7)._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(9)))._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(33)))._add(PyString.fromInterned("\n\n\n<!-- The above is a description of an error in a Python program, formatted\n     for a Web browser because the 'cgitb' module was enabled.  In case you\n     are not reading this in a Web browser, here is the original traceback:\n\n%s\n-->\n")._mod(var1.getglobal("pydoc").__getattr__("html").__getattr__("escape").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("traceback").__getattr__("format_exception").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4))))));
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var10 = Py.unpackSequence(var9, 6);
         PyObject var6 = var10[0];
         var1.setlocal(11, var6);
         var6 = null;
         var6 = var10[1];
         var1.setderef(1, var6);
         var6 = null;
         var6 = var10[2];
         var1.setlocal(12, var6);
         var6 = null;
         var6 = var10[3];
         var1.setlocal(13, var6);
         var6 = null;
         var6 = var10[4];
         var1.setlocal(14, var6);
         var6 = null;
         var6 = var10[5];
         var1.setlocal(15, var6);
         var6 = null;
         var1.setline(120);
         PyString var12;
         if (var1.getderef(1).__nonzero__()) {
            var1.setline(121);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getderef(1));
            var1.setderef(1, var5);
            var5 = null;
            var1.setline(122);
            var5 = PyString.fromInterned("<a href=\"file://%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getderef(1), var1.getglobal("pydoc").__getattr__("html").__getattr__("escape").__call__(var2, var1.getderef(1))}));
            var1.setlocal(16, var5);
            var5 = null;
         } else {
            var1.setline(124);
            var12 = PyString.fromInterned("?");
            var1.setderef(1, var12);
            var1.setlocal(16, var12);
         }

         var1.setline(125);
         var5 = var1.getglobal("inspect").__getattr__("getargvalues").__call__(var2, var1.getlocal(11));
         PyObject[] var13 = Py.unpackSequence(var5, 4);
         PyObject var7 = var13[0];
         var1.setlocal(17, var7);
         var7 = null;
         var7 = var13[1];
         var1.setlocal(18, var7);
         var7 = null;
         var7 = var13[2];
         var1.setlocal(19, var7);
         var7 = null;
         var7 = var13[3];
         var1.setlocal(20, var7);
         var7 = null;
         var5 = null;
         var1.setline(126);
         var12 = PyString.fromInterned("");
         var1.setlocal(21, var12);
         var5 = null;
         var1.setline(127);
         var5 = var1.getlocal(13);
         var10000 = var5._ne(PyString.fromInterned("?"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(128);
            var10000 = PyString.fromInterned("in ")._add(var1.getglobal("strong").__call__(var2, var1.getlocal(13)));
            PyObject var10001 = var1.getglobal("inspect").__getattr__("formatargvalues");
            var10 = new PyObject[]{var1.getlocal(17), var1.getlocal(18), var1.getlocal(19), var1.getlocal(20), null};
            var1.setline(130);
            var13 = Py.EmptyObjects;
            var10[4] = new PyFunction(var1.f_globals, var13, f$8);
            String[] var14 = new String[]{"formatvalue"};
            var10001 = var10001.__call__(var2, var10, var14);
            var5 = null;
            var5 = var10000._add(var10001);
            var1.setlocal(21, var5);
            var5 = null;
         }

         var1.setline(132);
         PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
         var1.setderef(0, var15);
         var5 = null;
         var1.setline(133);
         var10 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(12)})};
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var10;
         PyCode var10004 = reader$9;
         var10 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
         PyFunction var16 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var10);
         var1.setlocal(22, var16);
         var5 = null;
         var1.setline(137);
         var5 = var1.getglobal("scanvars").__call__(var2, var1.getlocal(22), var1.getlocal(11), var1.getlocal(20));
         var1.setlocal(23, var5);
         var5 = null;
         var1.setline(139);
         PyList var18 = new PyList(new PyObject[]{PyString.fromInterned("<tr><td bgcolor=\"#d8bbff\">%s%s %s</td></tr>")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("<big>&nbsp;</big>"), var1.getlocal(16), var1.getlocal(21)}))});
         var1.setlocal(24, var18);
         var5 = null;
         var1.setline(141);
         var5 = var1.getlocal(15);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(142);
            var5 = var1.getlocal(12)._sub(var1.getlocal(15));
            var1.setlocal(25, var5);
            var5 = null;
            var1.setline(143);
            var5 = var1.getlocal(14).__iter__();

            while(true) {
               var1.setline(143);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(26, var6);
               var1.setline(144);
               var7 = var1.getglobal("small").__call__(var2, PyString.fromInterned("&nbsp;")._mul(Py.newInteger(5)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(25)))))._add(var1.getglobal("str").__call__(var2, var1.getlocal(25))))._add(PyString.fromInterned("&nbsp;"));
               var1.setlocal(27, var7);
               var7 = null;
               var1.setline(145);
               var7 = var1.getlocal(25);
               var10000 = var7._in(var1.getderef(0));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(146);
                  var7 = PyString.fromInterned("<tt>=&gt;%s%s</tt>")._mod(new PyTuple(new PyObject[]{var1.getlocal(27), var1.getglobal("pydoc").__getattr__("html").__getattr__("preformat").__call__(var2, var1.getlocal(26))}));
                  var1.setlocal(26, var7);
                  var7 = null;
                  var1.setline(147);
                  var1.getlocal(24).__getattr__("append").__call__(var2, PyString.fromInterned("<tr><td bgcolor=\"#ffccee\">%s</td></tr>")._mod(var1.getlocal(26)));
               } else {
                  var1.setline(149);
                  var7 = PyString.fromInterned("<tt>&nbsp;&nbsp;%s%s</tt>")._mod(new PyTuple(new PyObject[]{var1.getlocal(27), var1.getglobal("pydoc").__getattr__("html").__getattr__("preformat").__call__(var2, var1.getlocal(26))}));
                  var1.setlocal(26, var7);
                  var7 = null;
                  var1.setline(150);
                  var1.getlocal(24).__getattr__("append").__call__(var2, PyString.fromInterned("<tr><td>%s</td></tr>")._mod(var1.getglobal("grey").__call__(var2, var1.getlocal(26))));
               }

               var1.setline(151);
               var7 = var1.getlocal(25);
               var7 = var7._iadd(Py.newInteger(1));
               var1.setlocal(25, var7);
            }
         }

         var1.setline(153);
         PyTuple var20 = new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
         var13 = Py.unpackSequence(var20, 2);
         var7 = var13[0];
         var1.setlocal(28, var7);
         var7 = null;
         var7 = var13[1];
         var1.setlocal(29, var7);
         var7 = null;
         var5 = null;
         var1.setline(154);
         var5 = var1.getlocal(23).__iter__();

         while(true) {
            var1.setline(154);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(168);
               var1.getlocal(24).__getattr__("append").__call__(var2, PyString.fromInterned("<tr><td>%s</td></tr>")._mod(var1.getglobal("small").__call__(var2, var1.getglobal("grey").__call__(var2, PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(29))))));
               var1.setline(169);
               var1.getlocal(9).__getattr__("append").__call__(var2, PyString.fromInterned("\n<table width=\"100%%\" cellspacing=0 cellpadding=0 border=0>\n%s</table>")._mod(PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(24))));
               break;
            }

            PyObject[] var17 = Py.unpackSequence(var6, 3);
            PyObject var8 = var17[0];
            var1.setlocal(30, var8);
            var8 = null;
            var8 = var17[1];
            var1.setlocal(31, var8);
            var8 = null;
            var8 = var17[2];
            var1.setlocal(32, var8);
            var8 = null;
            var1.setline(155);
            var7 = var1.getlocal(30);
            var10000 = var7._in(var1.getlocal(28));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(156);
               PyInteger var19 = Py.newInteger(1);
               var1.getlocal(28).__setitem__((PyObject)var1.getlocal(30), var19);
               var7 = null;
               var1.setline(157);
               var7 = var1.getlocal(32);
               var10000 = var7._isnot(var1.getglobal("__UNDEF__"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(158);
                  var7 = var1.getlocal(31);
                  var10000 = var7._in(new PyTuple(new PyObject[]{PyString.fromInterned("global"), PyString.fromInterned("builtin")}));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(159);
                     var7 = PyString.fromInterned("<em>%s</em> ")._mod(var1.getlocal(31))._add(var1.getglobal("strong").__call__(var2, var1.getlocal(30)));
                     var1.setlocal(30, var7);
                     var7 = null;
                  } else {
                     var1.setline(160);
                     var7 = var1.getlocal(31);
                     var10000 = var7._eq(PyString.fromInterned("local"));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(161);
                        var7 = var1.getglobal("strong").__call__(var2, var1.getlocal(30));
                        var1.setlocal(30, var7);
                        var7 = null;
                     } else {
                        var1.setline(163);
                        var7 = var1.getlocal(31)._add(var1.getglobal("strong").__call__(var2, var1.getlocal(30).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(-1))));
                        var1.setlocal(30, var7);
                        var7 = null;
                     }
                  }

                  var1.setline(164);
                  var1.getlocal(29).__getattr__("append").__call__(var2, PyString.fromInterned("%s&nbsp;= %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(30), var1.getglobal("pydoc").__getattr__("html").__getattr__("repr").__call__(var2, var1.getlocal(32))})));
               } else {
                  var1.setline(166);
                  var1.getlocal(29).__getattr__("append").__call__(var2, var1.getlocal(30)._add(PyString.fromInterned(" <em>undefined</em>")));
               }
            }
         }
      }
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = PyString.fromInterned("=")._add(var1.getglobal("pydoc").__getattr__("html").__getattr__("repr").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reader$9(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyInteger var3 = Py.newInteger(1);
      var1.getderef(0).__setitem__((PyObject)var1.getlocal(0).__getitem__(Py.newInteger(0)), var3);
      var3 = null;
      var3 = null;

      PyInteger var5;
      PyObject var6;
      PyObject var7;
      PyObject var11;
      Throwable var10000;
      label25: {
         PyObject var4;
         boolean var10001;
         try {
            var1.setline(135);
            var4 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getderef(1), var1.getlocal(0).__getitem__(Py.newInteger(0)));
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label25;
         }

         var1.setline(136);
         var11 = var1.getlocal(0);
         var5 = Py.newInteger(0);
         var6 = var11;
         var7 = var6.__getitem__(var5);
         var7 = var7._iadd(Py.newInteger(1));
         var6.__setitem__((PyObject)var5, var7);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
         }
      }

      Throwable var10 = var10000;
      Py.addTraceback(var10, var1);
      var1.setline(136);
      var11 = var1.getlocal(0);
      var5 = Py.newInteger(0);
      var6 = var11;
      var7 = var6.__getitem__(var5);
      var7 = var7._iadd(Py.newInteger(1));
      var6.__setitem__((PyObject)var5, var7);
      throw (Throwable)var10;
   }

   public PyObject text$10(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyString.fromInterned("Return a plain text document describing a given traceback.");
      var1.setline(195);
      PyObject var3 = var1.getlocal(0);
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
      var1.setline(196);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._is(var1.getglobal("types").__getattr__("ClassType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(197);
         var3 = var1.getlocal(2).__getattr__("__name__");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(198);
      var3 = PyString.fromInterned("Python ")._add(var1.getglobal("sys").__getattr__("version").__getattr__("split").__call__(var2).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned(": "))._add(var1.getglobal("sys").__getattr__("executable"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getglobal("time").__getattr__("ctime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(200);
      var3 = PyString.fromInterned("%s\n%s\n%s\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(2)), var1.getlocal(5), var1.getlocal(6)}))._add(PyString.fromInterned("\nA problem occurred in a Python script.  Here is the sequence of\nfunction calls leading up to the error, in the order they occurred.\n"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(205);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(8, var10);
      var3 = null;
      var1.setline(206);
      var3 = var1.getglobal("inspect").__getattr__("getinnerframes").__call__(var2, var1.getlocal(4), var1.getlocal(1));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getlocal(9).__iter__();

      while(true) {
         var1.setline(207);
         PyObject var9 = var3.__iternext__();
         if (var9 == null) {
            var1.setline(245);
            var10 = new PyList(new PyObject[]{PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(2)), var1.getglobal("str").__call__(var2, var1.getlocal(3))}))});
            var1.setlocal(31, var10);
            var3 = null;
            var1.setline(246);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("BaseException")).__nonzero__()) {
               var1.setline(247);
               var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(3)).__iter__();

               while(true) {
                  var1.setline(247);
                  var9 = var3.__iternext__();
                  if (var9 == null) {
                     break;
                  }

                  var1.setlocal(28, var9);
                  var1.setline(248);
                  var5 = var1.getglobal("pydoc").__getattr__("text").__getattr__("repr").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(3), var1.getlocal(28)));
                  var1.setlocal(30, var5);
                  var5 = null;
                  var1.setline(249);
                  var1.getlocal(31).__getattr__("append").__call__(var2, PyString.fromInterned("\n%s%s = %s")._mod(new PyTuple(new PyObject[]{PyString.fromInterned(" ")._mul(Py.newInteger(4)), var1.getlocal(28), var1.getlocal(30)})));
               }
            }

            var1.setline(251);
            var3 = var1.getlocal(7)._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(8)))._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(31)))._add(PyString.fromInterned("\n\nThe above is a description of an error in a Python program.  Here is\nthe original traceback:\n\n%s\n")._mod(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("traceback").__getattr__("format_exception").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)))));
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var11 = Py.unpackSequence(var9, 6);
         PyObject var6 = var11[0];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var11[1];
         var1.setderef(1, var6);
         var6 = null;
         var6 = var11[2];
         var1.setlocal(11, var6);
         var6 = null;
         var6 = var11[3];
         var1.setlocal(12, var6);
         var6 = null;
         var6 = var11[4];
         var1.setlocal(13, var6);
         var6 = null;
         var6 = var11[5];
         var1.setlocal(14, var6);
         var6 = null;
         var1.setline(208);
         Object var22 = var1.getderef(1);
         if (((PyObject)var22).__nonzero__()) {
            var22 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getderef(1));
         }

         if (!((PyObject)var22).__nonzero__()) {
            var22 = PyString.fromInterned("?");
         }

         Object var12 = var22;
         var1.setderef(1, (PyObject)var12);
         var5 = null;
         var1.setline(209);
         var5 = var1.getglobal("inspect").__getattr__("getargvalues").__call__(var2, var1.getlocal(10));
         PyObject[] var14 = Py.unpackSequence(var5, 4);
         PyObject var7 = var14[0];
         var1.setlocal(15, var7);
         var7 = null;
         var7 = var14[1];
         var1.setlocal(16, var7);
         var7 = null;
         var7 = var14[2];
         var1.setlocal(17, var7);
         var7 = null;
         var7 = var14[3];
         var1.setlocal(18, var7);
         var7 = null;
         var5 = null;
         var1.setline(210);
         PyString var13 = PyString.fromInterned("");
         var1.setlocal(19, var13);
         var5 = null;
         var1.setline(211);
         var5 = var1.getlocal(12);
         var10000 = var5._ne(PyString.fromInterned("?"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(212);
            var10000 = PyString.fromInterned("in ")._add(var1.getlocal(12));
            PyObject var10001 = var1.getglobal("inspect").__getattr__("formatargvalues");
            var11 = new PyObject[]{var1.getlocal(15), var1.getlocal(16), var1.getlocal(17), var1.getlocal(18), null};
            var1.setline(214);
            var14 = Py.EmptyObjects;
            var11[4] = new PyFunction(var1.f_globals, var14, f$11);
            String[] var15 = new String[]{"formatvalue"};
            var10001 = var10001.__call__(var2, var11, var15);
            var5 = null;
            var5 = var10000._add(var10001);
            var1.setlocal(19, var5);
            var5 = null;
         }

         var1.setline(216);
         PyDictionary var16 = new PyDictionary(Py.EmptyObjects);
         var1.setderef(0, var16);
         var5 = null;
         var1.setline(217);
         var11 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(11)})};
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var11;
         PyCode var10004 = reader$12;
         var11 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
         PyFunction var18 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var11);
         var1.setlocal(20, var18);
         var5 = null;
         var1.setline(221);
         var5 = var1.getglobal("scanvars").__call__(var2, var1.getlocal(20), var1.getlocal(10), var1.getlocal(18));
         var1.setlocal(21, var5);
         var5 = null;
         var1.setline(223);
         PyList var20 = new PyList(new PyObject[]{PyString.fromInterned(" %s %s")._mod(new PyTuple(new PyObject[]{var1.getderef(1), var1.getlocal(19)}))});
         var1.setlocal(22, var20);
         var5 = null;
         var1.setline(224);
         var5 = var1.getlocal(14);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(225);
            var5 = var1.getlocal(11)._sub(var1.getlocal(14));
            var1.setlocal(23, var5);
            var5 = null;
            var1.setline(226);
            var5 = var1.getlocal(13).__iter__();

            while(true) {
               var1.setline(226);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(24, var6);
               var1.setline(227);
               var7 = PyString.fromInterned("%5d ")._mod(var1.getlocal(23));
               var1.setlocal(25, var7);
               var7 = null;
               var1.setline(228);
               var1.getlocal(22).__getattr__("append").__call__(var2, var1.getlocal(25)._add(var1.getlocal(24).__getattr__("rstrip").__call__(var2)));
               var1.setline(229);
               var7 = var1.getlocal(23);
               var7 = var7._iadd(Py.newInteger(1));
               var1.setlocal(23, var7);
            }
         }

         var1.setline(231);
         PyTuple var21 = new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
         var14 = Py.unpackSequence(var21, 2);
         var7 = var14[0];
         var1.setlocal(26, var7);
         var7 = null;
         var7 = var14[1];
         var1.setlocal(27, var7);
         var7 = null;
         var5 = null;
         var1.setline(232);
         var5 = var1.getlocal(21).__iter__();

         while(true) {
            var1.setline(232);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(242);
               var1.getlocal(22).__getattr__("append").__call__(var2, PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(27)));
               var1.setline(243);
               var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("\n%s\n")._mod(PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(22))));
               break;
            }

            PyObject[] var17 = Py.unpackSequence(var6, 3);
            PyObject var8 = var17[0];
            var1.setlocal(28, var8);
            var8 = null;
            var8 = var17[1];
            var1.setlocal(29, var8);
            var8 = null;
            var8 = var17[2];
            var1.setlocal(30, var8);
            var8 = null;
            var1.setline(233);
            var7 = var1.getlocal(28);
            var10000 = var7._in(var1.getlocal(26));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(234);
               PyInteger var19 = Py.newInteger(1);
               var1.getlocal(26).__setitem__((PyObject)var1.getlocal(28), var19);
               var7 = null;
               var1.setline(235);
               var7 = var1.getlocal(30);
               var10000 = var7._isnot(var1.getglobal("__UNDEF__"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(236);
                  var7 = var1.getlocal(29);
                  var10000 = var7._eq(PyString.fromInterned("global"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(236);
                     var7 = PyString.fromInterned("global ")._add(var1.getlocal(28));
                     var1.setlocal(28, var7);
                     var7 = null;
                  } else {
                     var1.setline(237);
                     var7 = var1.getlocal(29);
                     var10000 = var7._ne(PyString.fromInterned("local"));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(237);
                        var7 = var1.getlocal(29)._add(var1.getlocal(28).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(-1)));
                        var1.setlocal(28, var7);
                        var7 = null;
                     }
                  }

                  var1.setline(238);
                  var1.getlocal(27).__getattr__("append").__call__(var2, PyString.fromInterned("%s = %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(28), var1.getglobal("pydoc").__getattr__("text").__getattr__("repr").__call__(var2, var1.getlocal(30))})));
               } else {
                  var1.setline(240);
                  var1.getlocal(27).__getattr__("append").__call__(var2, var1.getlocal(28)._add(PyString.fromInterned(" undefined")));
               }
            }
         }
      }
   }

   public PyObject f$11(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyObject var3 = PyString.fromInterned("=")._add(var1.getglobal("pydoc").__getattr__("text").__getattr__("repr").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reader$12(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyInteger var3 = Py.newInteger(1);
      var1.getderef(0).__setitem__((PyObject)var1.getlocal(0).__getitem__(Py.newInteger(0)), var3);
      var3 = null;
      var3 = null;

      PyInteger var5;
      PyObject var6;
      PyObject var7;
      PyObject var11;
      Throwable var10000;
      label25: {
         PyObject var4;
         boolean var10001;
         try {
            var1.setline(219);
            var4 = var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getderef(1), var1.getlocal(0).__getitem__(Py.newInteger(0)));
         } catch (Throwable var9) {
            var10000 = var9;
            var10001 = false;
            break label25;
         }

         var1.setline(220);
         var11 = var1.getlocal(0);
         var5 = Py.newInteger(0);
         var6 = var11;
         var7 = var6.__getitem__(var5);
         var7 = var7._iadd(Py.newInteger(1));
         var6.__setitem__((PyObject)var5, var7);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var8) {
            var10000 = var8;
            var10001 = false;
         }
      }

      Throwable var10 = var10000;
      Py.addTraceback(var10, var1);
      var1.setline(220);
      var11 = var1.getlocal(0);
      var5 = Py.newInteger(0);
      var6 = var11;
      var7 = var6.__getitem__(var5);
      var7 = var7._iadd(Py.newInteger(1));
      var6.__setitem__((PyObject)var5, var7);
      throw (Throwable)var10;
   }

   public PyObject Hook$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A hook to replace sys.excepthook that shows tracebacks in HTML."));
      var1.setline(260);
      PyString.fromInterned("A hook to replace sys.excepthook that shows tracebacks in HTML.");
      var1.setline(262);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1), var1.getname("None"), Py.newInteger(5), var1.getname("None"), PyString.fromInterned("html")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$15, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      var1.setline(273);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, handle$16, (PyObject)null);
      var1.setlocal("handle", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("display", var3);
      var3 = null;
      var1.setline(265);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("logdir", var3);
      var3 = null;
      var1.setline(266);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("context", var3);
      var3 = null;
      var1.setline(267);
      PyObject var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("stdout");
      }

      var3 = var10000;
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(268);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("format", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$15(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      var1.getlocal(0).__getattr__("handle").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle$16(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(275);
      var3 = var1.getlocal(0).__getattr__("format");
      var10000 = var3._eq(PyString.fromInterned("html"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(276);
         var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getglobal("reset").__call__(var2));
      }

      var1.setline(278);
      var3 = var1.getlocal(0).__getattr__("format");
      var10000 = var3._eq(PyString.fromInterned("html"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("html");
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("text");
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(279);
      var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;

      PyObject[] var4;
      String[] var5;
      PyObject var9;
      try {
         var1.setline(281);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("context"));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(283);
         var10000 = PyString.fromInterned("").__getattr__("join");
         PyObject var10002 = var1.getglobal("traceback").__getattr__("format_exception");
         var4 = Py.EmptyObjects;
         var5 = new String[0];
         var10002 = var10002._callextra(var4, var5, var1.getlocal(1), (PyObject)null);
         var4 = null;
         var9 = var10000.__call__(var2, var10002);
         var1.setlocal(4, var9);
         var4 = null;
         var1.setline(284);
         var9 = var1.getglobal("True");
         var1.setlocal(3, var9);
         var4 = null;
      }

      var1.setline(286);
      if (var1.getlocal(0).__getattr__("display").__nonzero__()) {
         var1.setline(287);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(288);
            var3 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(289);
            var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, PyString.fromInterned("<pre>")._add(var1.getlocal(4))._add(PyString.fromInterned("</pre>\n")));
         } else {
            var1.setline(291);
            var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(4)._add(PyString.fromInterned("\n")));
         }
      } else {
         var1.setline(293);
         var1.getlocal(0).__getattr__("file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<p>A problem occurred in a Python script.\n"));
      }

      var1.setline(295);
      var3 = var1.getlocal(0).__getattr__("logdir");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(296);
         PyList var13 = new PyList(new PyObject[]{PyString.fromInterned(".txt"), PyString.fromInterned(".html")});
         var3 = var1.getlocal(0).__getattr__("format");
         PyObject var10001 = var3._eq(PyString.fromInterned("html"));
         var3 = null;
         var3 = var13.__getitem__(var10001);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(297);
         var10000 = var1.getglobal("tempfile").__getattr__("mkstemp");
         PyObject[] var12 = new PyObject[]{var1.getlocal(5), var1.getlocal(0).__getattr__("logdir")};
         String[] var11 = new String[]{"suffix", "dir"};
         var10000 = var10000.__call__(var2, var12, var11);
         var3 = null;
         var3 = var10000;
         var4 = Py.unpackSequence(var3, 2);
         PyObject var10 = var4[0];
         var1.setlocal(6, var10);
         var5 = null;
         var10 = var4[1];
         var1.setlocal(7, var10);
         var5 = null;
         var3 = null;

         try {
            var1.setline(300);
            var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("w"));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(301);
            var1.getlocal(8).__getattr__("write").__call__(var2, var1.getlocal(4));
            var1.setline(302);
            var1.getlocal(8).__getattr__("close").__call__(var2);
            var1.setline(303);
            var3 = PyString.fromInterned("%s contains the description of this error.")._mod(var1.getlocal(7));
            var1.setlocal(9, var3);
            var3 = null;
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(305);
            var9 = PyString.fromInterned("Tried to save traceback to %s, but failed.")._mod(var1.getlocal(7));
            var1.setlocal(9, var9);
            var4 = null;
         }

         var1.setline(307);
         var3 = var1.getlocal(0).__getattr__("format");
         var10000 = var3._eq(PyString.fromInterned("html"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(308);
            var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, PyString.fromInterned("<p>%s</p>\n")._mod(var1.getlocal(9)));
         } else {
            var1.setline(310);
            var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(9)._add(PyString.fromInterned("\n")));
         }
      }

      try {
         var1.setline(312);
         var1.getlocal(0).__getattr__("file").__getattr__("flush").__call__(var2);
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(313);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject enable$17(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyString.fromInterned("Install an exception handler that formats tracebacks as HTML.\n\n    The optional argument 'display' can be set to 0 to suppress sending the\n    traceback to the browser, and 'logdir' can be set to a directory to cause\n    tracebacks to be written to files there.");
      var1.setline(322);
      PyObject var10000 = var1.getglobal("Hook");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"display", "logdir", "context", "format"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.getglobal("sys").__setattr__("excepthook", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public cgitb$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      reset$1 = Py.newCode(0, var2, var1, "reset", 36, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      small$2 = Py.newCode(1, var2, var1, "small", 47, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      strong$3 = Py.newCode(1, var2, var1, "strong", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      grey$4 = Py.newCode(1, var2, var1, "grey", 59, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "frame", "locals", "builtins"};
      lookup$5 = Py.newCode(3, var2, var1, "lookup", 65, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"reader", "frame", "locals", "vars", "lasttoken", "parent", "prefix", "value", "ttype", "token", "start", "end", "line", "where"};
      scanvars$6 = Py.newCode(3, var2, var1, "scanvars", 81, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"einfo", "context", "etype", "evalue", "etb", "pyver", "date", "head", "indent", "frames", "records", "frame", "lnum", "func", "lines", "index", "link", "args", "varargs", "varkw", "locals", "call", "reader", "vars", "rows", "i", "line", "num", "done", "dump", "name", "where", "value", "exception", "highlight", "file"};
      String[] var10001 = var2;
      cgitb$py var10007 = self;
      var2 = new String[]{"highlight", "file"};
      html$7 = Py.newCode(2, var10001, var1, "html", 102, false, false, var10007, 7, var2, (String[])null, 2, 4097);
      var2 = new String[]{"value"};
      f$8 = Py.newCode(1, var2, var1, "<lambda>", 130, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lnum"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"highlight", "file"};
      reader$9 = Py.newCode(1, var10001, var1, "reader", 133, false, false, var10007, 9, (String[])null, var2, 0, 4097);
      var2 = new String[]{"einfo", "context", "etype", "evalue", "etb", "pyver", "date", "head", "frames", "records", "frame", "lnum", "func", "lines", "index", "args", "varargs", "varkw", "locals", "call", "reader", "vars", "rows", "i", "line", "num", "done", "dump", "name", "where", "value", "exception", "highlight", "file"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"highlight", "file"};
      text$10 = Py.newCode(2, var10001, var1, "text", 193, false, false, var10007, 10, var2, (String[])null, 2, 4097);
      var2 = new String[]{"value"};
      f$11 = Py.newCode(1, var2, var1, "<lambda>", 214, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lnum"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"highlight", "file"};
      reader$12 = Py.newCode(1, var10001, var1, "reader", 217, false, false, var10007, 12, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Hook$13 = Py.newCode(0, var2, var1, "Hook", 259, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "display", "logdir", "context", "file", "format"};
      __init__$14 = Py.newCode(6, var2, var1, "__init__", 262, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "etype", "evalue", "etb"};
      __call__$15 = Py.newCode(4, var2, var1, "__call__", 270, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "info", "formatter", "plain", "doc", "suffix", "fd", "path", "file", "msg"};
      handle$16 = Py.newCode(2, var2, var1, "handle", 273, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"display", "logdir", "context", "format"};
      enable$17 = Py.newCode(4, var2, var1, "enable", 316, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cgitb$py("cgitb$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cgitb$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.reset$1(var2, var3);
         case 2:
            return this.small$2(var2, var3);
         case 3:
            return this.strong$3(var2, var3);
         case 4:
            return this.grey$4(var2, var3);
         case 5:
            return this.lookup$5(var2, var3);
         case 6:
            return this.scanvars$6(var2, var3);
         case 7:
            return this.html$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.reader$9(var2, var3);
         case 10:
            return this.text$10(var2, var3);
         case 11:
            return this.f$11(var2, var3);
         case 12:
            return this.reader$12(var2, var3);
         case 13:
            return this.Hook$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.__call__$15(var2, var3);
         case 16:
            return this.handle$16(var2, var3);
         case 17:
            return this.enable$17(var2, var3);
         default:
            return null;
      }
   }
}
