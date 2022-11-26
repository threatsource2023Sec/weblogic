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
@Filename("code.py")
public class code$py extends PyFunctionTable implements PyRunnable {
   static code$py self;
   static final PyCode f$0;
   static final PyCode softspace$1;
   static final PyCode InteractiveInterpreter$2;
   static final PyCode __init__$3;
   static final PyCode runsource$4;
   static final PyCode runcode$5;
   static final PyCode showsyntaxerror$6;
   static final PyCode showtraceback$7;
   static final PyCode write$8;
   static final PyCode InteractiveConsole$9;
   static final PyCode __init__$10;
   static final PyCode resetbuffer$11;
   static final PyCode interact$12;
   static final PyCode push$13;
   static final PyCode raw_input$14;
   static final PyCode interact$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utilities needed to emulate Python's interactive interpreter.\n\n"));
      var1.setline(3);
      PyString.fromInterned("Utilities needed to emulate Python's interactive interpreter.\n\n");
      var1.setline(8);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(10);
      String[] var5 = new String[]{"CommandCompiler", "compile_command"};
      PyObject[] var6 = imp.importFrom("codeop", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("CommandCompiler", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("compile_command", var4);
      var4 = null;
      var1.setline(12);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("InteractiveInterpreter"), PyString.fromInterned("InteractiveConsole"), PyString.fromInterned("interact"), PyString.fromInterned("compile_command")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(15);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, softspace$1, (PyObject)null);
      var1.setlocal("softspace", var8);
      var3 = null;
      var1.setline(28);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("InteractiveInterpreter", var6, InteractiveInterpreter$2);
      var1.setlocal("InteractiveInterpreter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(174);
      var6 = new PyObject[]{var1.getname("InteractiveInterpreter")};
      var4 = Py.makeClass("InteractiveConsole", var6, InteractiveConsole$9);
      var1.setlocal("InteractiveConsole", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(284);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, interact$15, PyString.fromInterned("Closely emulate the interactive Python interpreter.\n\n    This is a backwards compatible interface to the InteractiveConsole\n    class.  When readfunc is not specified, it attempts to import the\n    readline module to enable GNU readline if it is available.\n\n    Arguments (all optional, all default to None):\n\n    banner -- passed to InteractiveConsole.interact()\n    readfunc -- if not None, replaces InteractiveConsole.raw_input()\n    local -- passed to InteractiveInterpreter.__init__()\n\n    "));
      var1.setlocal("interact", var8);
      var3 = null;
      var1.setline(309);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(310);
         var1.getname("interact").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject softspace$1(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;

      PyException var6;
      PyObject var7;
      try {
         var1.setline(18);
         var7 = var1.getlocal(0).__getattr__("softspace");
         var1.setlocal(2, var7);
         var3 = null;
      } catch (Throwable var5) {
         var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("AttributeError"))) {
            throw var6;
         }

         var1.setline(20);
      }

      try {
         var1.setline(22);
         var7 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("softspace", var7);
         var3 = null;
      } catch (Throwable var4) {
         var6 = Py.setException(var4, var1);
         if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("TypeError")}))) {
            throw var6;
         }

         var1.setline(25);
      }

      var1.setline(26);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject InteractiveInterpreter$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for InteractiveConsole.\n\n    This class deals with parsing and interpreter state (the user's\n    namespace); it doesn't deal with input buffering or prompting or\n    input file naming (the filename is always passed in explicitly).\n\n    "));
      var1.setline(35);
      PyString.fromInterned("Base class for InteractiveConsole.\n\n    This class deals with parsing and interpreter state (the user's\n    namespace); it doesn't deal with input buffering or prompting or\n    input file naming (the filename is always passed in explicitly).\n\n    ");
      var1.setline(37);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Constructor.\n\n        The optional 'locals' argument specifies the dictionary in\n        which code will be executed; it defaults to a newly created\n        dictionary with key \"__name__\" set to \"__console__\" and key\n        \"__doc__\" set to None.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(51);
      var3 = new PyObject[]{PyString.fromInterned("<input>"), PyString.fromInterned("single")};
      var4 = new PyFunction(var1.f_globals, var3, runsource$4, PyString.fromInterned("Compile and run some source in the interpreter.\n\n        Arguments are as for compile_command().\n\n        One several things can happen:\n\n        1) The input is incorrect; compile_command() raised an\n        exception (SyntaxError or OverflowError).  A syntax traceback\n        will be printed by calling the showsyntaxerror() method.\n\n        2) The input is incomplete, and more input is required;\n        compile_command() returned None.  Nothing happens.\n\n        3) The input is complete; compile_command() returned a code\n        object.  The code is executed by calling self.runcode() (which\n        also handles run-time exceptions, except for SystemExit).\n\n        The return value is True in case 2, False in the other cases (unless\n        an exception is raised).  The return value can be used to\n        decide whether to use sys.ps1 or sys.ps2 to prompt the next\n        line.\n\n        "));
      var1.setlocal("runsource", var4);
      var3 = null;
      var1.setline(90);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runcode$5, PyString.fromInterned("Execute a code object.\n\n        When an exception occurs, self.showtraceback() is called to\n        display a traceback.  All exceptions are caught except\n        SystemExit, which is reraised.\n\n        A note about KeyboardInterrupt: this exception may occur\n        elsewhere in this code, and may not always be caught.  The\n        caller should be prepared to deal with it.\n\n        "));
      var1.setlocal("runcode", var4);
      var3 = null;
      var1.setline(112);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, showsyntaxerror$6, PyString.fromInterned("Display the syntax error that just occurred.\n\n        This doesn't display a stack trace because there isn't one.\n\n        If a filename is given, it is stuffed in the exception instead\n        of what was there before (because Python's parser always uses\n        \"<string>\" when reading from a string).\n\n        The output is written by self.write(), below.\n\n        "));
      var1.setlocal("showsyntaxerror", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, showtraceback$7, PyString.fromInterned("Display the exception that just occurred.\n\n        We remove the first stack item because it is our own code.\n\n        The output is written by self.write(), below.\n\n        "));
      var1.setlocal("showtraceback", var4);
      var3 = null;
      var1.setline(164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$8, PyString.fromInterned("Write a string.\n\n        The base implementation writes to sys.stderr; a subclass may\n        replace this with a different implementation.\n\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Constructor.\n\n        The optional 'locals' argument specifies the dictionary in\n        which code will be executed; it defaults to a newly created\n        dictionary with key \"__name__\" set to \"__console__\" and key\n        \"__doc__\" set to None.\n\n        ");
      var1.setline(46);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(47);
         PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("__name__"), PyString.fromInterned("__console__"), PyString.fromInterned("__doc__"), var1.getglobal("None")});
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(48);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("locals", var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getglobal("CommandCompiler").__call__(var2);
      var1.getlocal(0).__setattr__("compile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runsource$4(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("Compile and run some source in the interpreter.\n\n        Arguments are as for compile_command().\n\n        One several things can happen:\n\n        1) The input is incorrect; compile_command() raised an\n        exception (SyntaxError or OverflowError).  A syntax traceback\n        will be printed by calling the showsyntaxerror() method.\n\n        2) The input is incomplete, and more input is required;\n        compile_command() returned None.  Nothing happens.\n\n        3) The input is complete; compile_command() returned a code\n        object.  The code is executed by calling self.runcode() (which\n        also handles run-time exceptions, except for SystemExit).\n\n        The return value is True in case 2, False in the other cases (unless\n        an exception is raised).  The return value can be used to\n        decide whether to use sys.ps1 or sys.ps2 to prompt the next\n        line.\n\n        ");

      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(76);
         var6 = var1.getlocal(0).__getattr__("compile").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("OverflowError"), var1.getglobal("SyntaxError"), var1.getglobal("ValueError")}))) {
            var1.setline(79);
            var1.getlocal(0).__getattr__("showsyntaxerror").__call__(var2, var1.getlocal(2));
            var1.setline(80);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(82);
      var6 = var1.getlocal(4);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(84);
         var4 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(87);
         var1.getlocal(0).__getattr__("runcode").__call__(var2, var1.getlocal(4));
         var1.setline(88);
         var4 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject runcode$5(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("Execute a code object.\n\n        When an exception occurs, self.showtraceback() is called to\n        display a traceback.  All exceptions are caught except\n        SystemExit, which is reraised.\n\n        A note about KeyboardInterrupt: this exception may occur\n        elsewhere in this code, and may not always be caught.  The\n        caller should be prepared to deal with it.\n\n        ");

      label23: {
         try {
            var1.setline(103);
            Py.exec(var1.getlocal(1), var1.getlocal(0).__getattr__("locals"), (PyObject)null);
         } catch (Throwable var4) {
            PyException var3 = Py.setException(var4, var1);
            if (var3.match(var1.getname("SystemExit"))) {
               var1.setline(105);
               throw Py.makeException();
            }

            var1.setline(107);
            var1.getlocal(0).__getattr__("showtraceback").__call__(var2);
            break label23;
         }

         var1.setline(109);
         if (var1.getname("softspace").__call__((ThreadState)var2, (PyObject)var1.getname("sys").__getattr__("stdout"), (PyObject)Py.newInteger(0)).__nonzero__()) {
            var1.setline(110);
            Py.println();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject showsyntaxerror$6(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Display the syntax error that just occurred.\n\n        This doesn't display a stack trace because there isn't one.\n\n        If a filename is given, it is stuffed in the exception instead\n        of what was there before (because Python's parser always uses\n        \"<string>\" when reading from a string).\n\n        The output is written by self.write(), below.\n\n        ");
      var1.setline(124);
      PyObject var3 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.getglobal("sys").__setattr__("last_traceback", var5);
      var5 = null;
      var3 = null;
      var1.setline(125);
      var3 = var1.getlocal(2);
      var1.getglobal("sys").__setattr__("last_type", var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getlocal(3);
      var1.getglobal("sys").__setattr__("last_value", var3);
      var3 = null;
      var1.setline(127);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("SyntaxError"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         label19: {
            try {
               var1.setline(130);
               var3 = var1.getlocal(3);
               var4 = Py.unpackSequence(var3, 2);
               var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               PyObject[] var6 = Py.unpackSequence(var5, 4);
               PyObject var7 = var6[0];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(6, var7);
               var7 = null;
               var7 = var6[2];
               var1.setlocal(7, var7);
               var7 = null;
               var7 = var6[3];
               var1.setlocal(8, var7);
               var7 = null;
               var5 = null;
               var3 = null;
            } catch (Throwable var8) {
               Py.setException(var8, var1);
               var1.setline(133);
               break label19;
            }

            var1.setline(136);
            PyObject var9 = var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8)})));
            var1.setlocal(3, var9);
            var4 = null;
            var1.setline(137);
            var9 = var1.getlocal(3);
            var1.getglobal("sys").__setattr__("last_value", var9);
            var4 = null;
         }
      }

      var1.setline(138);
      var3 = var1.getglobal("traceback").__getattr__("format_exception_only").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(139);
      var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("write"), var1.getlocal(9));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject showtraceback$7(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Display the exception that just occurred.\n\n        We remove the first stack item because it is our own code.\n\n        The output is written by self.write(), below.\n\n        ");
      Object var3 = null;

      PyObject var4;
      try {
         var1.setline(150);
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
         var1.setline(151);
         var4 = var1.getlocal(1);
         var1.getglobal("sys").__setattr__("last_type", var4);
         var4 = null;
         var1.setline(152);
         var4 = var1.getlocal(2);
         var1.getglobal("sys").__setattr__("last_value", var4);
         var4 = null;
         var1.setline(153);
         var4 = var1.getlocal(3);
         var1.getglobal("sys").__setattr__("last_traceback", var4);
         var4 = null;
         var1.setline(154);
         var4 = var1.getglobal("traceback").__getattr__("extract_tb").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(155);
         var1.getlocal(4).__delslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var1.setline(156);
         var4 = var1.getglobal("traceback").__getattr__("format_list").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(157);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(158);
            var1.getlocal(5).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("Traceback (most recent call last):\n"));
         }

         var1.setline(159);
         var4 = var1.getglobal("traceback").__getattr__("format_exception_only").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.getlocal(5).__setslice__(var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)null, (PyObject)null, var4);
         var4 = null;
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(161);
         var4 = var1.getglobal("None");
         var1.setlocal(4, var4);
         var1.setlocal(3, var4);
         throw (Throwable)var7;
      }

      var1.setline(161);
      var4 = var1.getglobal("None");
      var1.setlocal(4, var4);
      var1.setlocal(3, var4);
      var1.setline(162);
      var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("write"), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$8(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Write a string.\n\n        The base implementation writes to sys.stderr; a subclass may\n        replace this with a different implementation.\n\n        ");
      var1.setline(171);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InteractiveConsole$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Closely emulate the behavior of the interactive Python interpreter.\n\n    This class builds on InteractiveInterpreter and adds prompting\n    using the familiar sys.ps1 and sys.ps2, and input buffering.\n\n    "));
      var1.setline(180);
      PyString.fromInterned("Closely emulate the behavior of the interactive Python interpreter.\n\n    This class builds on InteractiveInterpreter and adds prompting\n    using the familiar sys.ps1 and sys.ps2, and input buffering.\n\n    ");
      var1.setline(182);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("<console>")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$10, PyString.fromInterned("Constructor.\n\n        The optional locals argument will be passed to the\n        InteractiveInterpreter base class.\n\n        The optional filename argument should specify the (file)name\n        of the input stream; it will show up in tracebacks.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, resetbuffer$11, PyString.fromInterned("Reset the input buffer."));
      var1.setlocal("resetbuffer", var4);
      var3 = null;
      var1.setline(200);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, interact$12, PyString.fromInterned("Closely emulate the interactive Python console.\n\n        The optional banner argument specify the banner to print\n        before the first interaction; by default it prints a banner\n        similar to the one printed by the real Python interpreter,\n        followed by the current class name in parentheses (so as not\n        to confuse this with the real interpreter -- since it's so\n        close!).\n\n        "));
      var1.setlocal("interact", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push$13, PyString.fromInterned("Push a line to the interpreter.\n\n        The line should not have a trailing newline; it may have\n        internal newlines.  The line is appended to a buffer and the\n        interpreter's runsource() method is called with the\n        concatenated contents of the buffer as source.  If this\n        indicates that the command was executed or invalid, the buffer\n        is reset; otherwise, the command is incomplete, and the buffer\n        is left as it was after the line was appended.  The return\n        value is 1 if more input is required, 0 if the line was dealt\n        with in some way (this is the same as runsource()).\n\n        "));
      var1.setlocal("push", var4);
      var3 = null;
      var1.setline(270);
      var3 = new PyObject[]{PyString.fromInterned("")};
      var4 = new PyFunction(var1.f_globals, var3, raw_input$14, PyString.fromInterned("Write a prompt and read a line.\n\n        The returned line does not include the trailing newline.\n        When the user enters the EOF key sequence, EOFError is raised.\n\n        The base implementation uses the built-in function\n        raw_input(); a subclass may replace this with a different\n        implementation.\n\n        "));
      var1.setlocal("raw_input", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Constructor.\n\n        The optional locals argument will be passed to the\n        InteractiveInterpreter base class.\n\n        The optional filename argument should specify the (file)name\n        of the input stream; it will show up in tracebacks.\n\n        ");
      var1.setline(192);
      var1.getglobal("InteractiveInterpreter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(193);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(194);
      var1.getlocal(0).__getattr__("resetbuffer").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resetbuffer$11(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Reset the input buffer.");
      var1.setline(198);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject interact$12(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyString.fromInterned("Closely emulate the interactive Python console.\n\n        The optional banner argument specify the banner to print\n        before the first interaction; by default it prints a banner\n        similar to the one printed by the real Python interpreter,\n        followed by the current class name in parentheses (so as not\n        to confuse this with the real interpreter -- since it's so\n        close!).\n\n        ");

      PyException var3;
      PyString var4;
      try {
         var1.setline(212);
         var1.getglobal("sys").__getattr__("ps1");
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(214);
         var4 = PyString.fromInterned(">>> ");
         var1.getglobal("sys").__setattr__((String)"ps1", var4);
         var4 = null;
      }

      try {
         var1.setline(216);
         var1.getglobal("sys").__getattr__("ps2");
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(218);
         var4 = PyString.fromInterned("... ");
         var1.getglobal("sys").__setattr__((String)"ps2", var4);
         var4 = null;
      }

      var1.setline(219);
      PyString var9 = PyString.fromInterned("Type \"help\", \"copyright\", \"credits\" or \"license\" for more information.");
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(220);
      PyObject var10 = var1.getlocal(1);
      PyObject var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(221);
         var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("Python %s on %s\n%s\n(%s)\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("version"), var1.getglobal("sys").__getattr__("platform"), var1.getlocal(2), var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")})));
      } else {
         var1.setline(225);
         var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1))));
      }

      var1.setline(226);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(3, var11);
      var3 = null;

      while(true) {
         var1.setline(227);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         try {
            var1.setline(229);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(230);
               var10 = var1.getglobal("sys").__getattr__("ps2");
               var1.setlocal(4, var10);
               var3 = null;
            } else {
               var1.setline(232);
               var10 = var1.getglobal("sys").__getattr__("ps1");
               var1.setlocal(4, var10);
               var3 = null;
            }

            try {
               var1.setline(234);
               var10 = var1.getlocal(0).__getattr__("raw_input").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var10);
               var3 = null;
               var1.setline(236);
               var10 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("stdin"), (PyObject)PyString.fromInterned("encoding"), (PyObject)var1.getglobal("None"));
               var1.setlocal(6, var10);
               var3 = null;
               var1.setline(237);
               var10000 = var1.getlocal(6);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("unicode")).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(238);
                  var10 = var1.getlocal(5).__getattr__("decode").__call__(var2, var1.getlocal(6));
                  var1.setlocal(5, var10);
                  var3 = null;
               }
            } catch (Throwable var5) {
               var3 = Py.setException(var5, var1);
               if (var3.match(var1.getglobal("EOFError"))) {
                  var1.setline(240);
                  var1.getlocal(0).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
                  break;
               }

               throw var3;
            }

            var1.setline(243);
            PyObject var13 = var1.getlocal(0).__getattr__("push").__call__(var2, var1.getlocal(5));
            var1.setlocal(3, var13);
            var4 = null;
         } catch (Throwable var6) {
            var3 = Py.setException(var6, var1);
            if (!var3.match(var1.getglobal("KeyboardInterrupt"))) {
               throw var3;
            }

            var1.setline(245);
            var1.getlocal(0).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nKeyboardInterrupt\n"));
            var1.setline(246);
            var1.getlocal(0).__getattr__("resetbuffer").__call__(var2);
            var1.setline(247);
            PyInteger var12 = Py.newInteger(0);
            var1.setlocal(3, var12);
            var4 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push$13(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyString.fromInterned("Push a line to the interpreter.\n\n        The line should not have a trailing newline; it may have\n        internal newlines.  The line is appended to a buffer and the\n        interpreter's runsource() method is called with the\n        concatenated contents of the buffer as source.  If this\n        indicates that the command was executed or invalid, the buffer\n        is reset; otherwise, the command is incomplete, and the buffer\n        is left as it was after the line was appended.  The return\n        value is 1 if more input is required, 0 if the line was dealt\n        with in some way (this is the same as runsource()).\n\n        ");
      var1.setline(263);
      var1.getlocal(0).__getattr__("buffer").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(264);
      PyObject var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(265);
      var3 = var1.getlocal(0).__getattr__("runsource").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("filename"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(266);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(267);
         var1.getlocal(0).__getattr__("resetbuffer").__call__(var2);
      }

      var1.setline(268);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject raw_input$14(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString.fromInterned("Write a prompt and read a line.\n\n        The returned line does not include the trailing newline.\n        When the user enters the EOF key sequence, EOFError is raised.\n\n        The base implementation uses the built-in function\n        raw_input(); a subclass may replace this with a different\n        implementation.\n\n        ");
      var1.setline(281);
      PyObject var3 = var1.getglobal("raw_input").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject interact$15(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyString.fromInterned("Closely emulate the interactive Python interpreter.\n\n    This is a backwards compatible interface to the InteractiveConsole\n    class.  When readfunc is not specified, it attempts to import the\n    readline module to enable GNU readline if it is available.\n\n    Arguments (all optional, all default to None):\n\n    banner -- passed to InteractiveConsole.interact()\n    readfunc -- if not None, replaces InteractiveConsole.raw_input()\n    local -- passed to InteractiveInterpreter.__init__()\n\n    ");
      var1.setline(298);
      PyObject var3 = var1.getglobal("InteractiveConsole").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(299);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(300);
         var3 = var1.getlocal(1);
         var1.getlocal(3).__setattr__("raw_input", var3);
         var3 = null;
      } else {
         try {
            var1.setline(303);
            var3 = imp.importOne("readline", var1, -1);
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("ImportError"))) {
               throw var5;
            }

            var1.setline(305);
         }
      }

      var1.setline(306);
      var1.getlocal(3).__getattr__("interact").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public code$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file", "newvalue", "oldvalue"};
      softspace$1 = Py.newCode(2, var2, var1, "softspace", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InteractiveInterpreter$2 = Py.newCode(0, var2, var1, "InteractiveInterpreter", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "locals"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "filename", "symbol", "code"};
      runsource$4 = Py.newCode(4, var2, var1, "runsource", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code"};
      runcode$5 = Py.newCode(2, var2, var1, "runcode", 90, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "type", "value", "msg", "dummy_filename", "lineno", "offset", "line", "list"};
      showsyntaxerror$6 = Py.newCode(2, var2, var1, "showsyntaxerror", 112, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "tb", "tblist", "list"};
      showtraceback$7 = Py.newCode(1, var2, var1, "showtraceback", 141, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write$8 = Py.newCode(2, var2, var1, "write", 164, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InteractiveConsole$9 = Py.newCode(0, var2, var1, "InteractiveConsole", 174, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "locals", "filename"};
      __init__$10 = Py.newCode(3, var2, var1, "__init__", 182, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      resetbuffer$11 = Py.newCode(1, var2, var1, "resetbuffer", 196, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "banner", "cprt", "more", "prompt", "line", "encoding"};
      interact$12 = Py.newCode(2, var2, var1, "interact", 200, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "source", "more"};
      push$13 = Py.newCode(2, var2, var1, "push", 249, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prompt"};
      raw_input$14 = Py.newCode(2, var2, var1, "raw_input", 270, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"banner", "readfunc", "local", "console", "readline"};
      interact$15 = Py.newCode(3, var2, var1, "interact", 284, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new code$py("code$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(code$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.softspace$1(var2, var3);
         case 2:
            return this.InteractiveInterpreter$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.runsource$4(var2, var3);
         case 5:
            return this.runcode$5(var2, var3);
         case 6:
            return this.showsyntaxerror$6(var2, var3);
         case 7:
            return this.showtraceback$7(var2, var3);
         case 8:
            return this.write$8(var2, var3);
         case 9:
            return this.InteractiveConsole$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this.resetbuffer$11(var2, var3);
         case 12:
            return this.interact$12(var2, var3);
         case 13:
            return this.push$13(var2, var3);
         case 14:
            return this.raw_input$14(var2, var3);
         case 15:
            return this.interact$15(var2, var3);
         default:
            return null;
      }
   }
}
