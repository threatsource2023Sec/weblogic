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
@Filename("cmd.py")
public class cmd$py extends PyFunctionTable implements PyRunnable {
   static cmd$py self;
   static final PyCode f$0;
   static final PyCode Cmd$1;
   static final PyCode __init__$2;
   static final PyCode cmdloop$3;
   static final PyCode precmd$4;
   static final PyCode postcmd$5;
   static final PyCode preloop$6;
   static final PyCode postloop$7;
   static final PyCode parseline$8;
   static final PyCode onecmd$9;
   static final PyCode emptyline$10;
   static final PyCode default$11;
   static final PyCode completedefault$12;
   static final PyCode completenames$13;
   static final PyCode complete$14;
   static final PyCode get_names$15;
   static final PyCode complete_help$16;
   static final PyCode f$17;
   static final PyCode do_help$18;
   static final PyCode print_topics$19;
   static final PyCode columnize$20;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A generic class to build line-oriented command interpreters.\n\nInterpreters constructed with this class obey the following conventions:\n\n1. End of file on input is processed as the command 'EOF'.\n2. A command is parsed out of each line by collecting the prefix composed\n   of characters in the identchars member.\n3. A command `foo' is dispatched to a method 'do_foo()'; the do_ method\n   is passed a single argument consisting of the remainder of the line.\n4. Typing an empty line repeats the last command.  (Actually, it calls the\n   method `emptyline', which may be overridden in a subclass.)\n5. There is a predefined `help' method.  Given an argument `topic', it\n   calls the command `help_topic'.  With no arguments, it lists all topics\n   with defined help_ functions, broken into up to three topics; documented\n   commands, miscellaneous help topics, and undocumented commands.\n6. The command '?' is a synonym for `help'.  The command '!' is a synonym\n   for `shell', if a do_shell method exists.\n7. If completion is enabled, completing commands will be done automatically,\n   and completing of commands args is done by calling complete_foo() with\n   arguments text, line, begidx, endidx.  text is string we are matching\n   against, all returned matches must begin with it.  line is the current\n   input line (lstripped), begidx and endidx are the beginning and end\n   indexes of the text being matched, which could be used to provide\n   different completion depending upon which position the argument is in.\n\nThe `default' method may be overridden to intercept commands for which there\nis no do_ method.\n\nThe `completedefault' method may be overridden to intercept completions for\ncommands that have no complete_ method.\n\nThe data member `self.ruler' sets the character used to draw separator lines\nin the help messages.  If empty, no ruler line is drawn.  It defaults to \"=\".\n\nIf the value of `self.intro' is nonempty when the cmdloop method is called,\nit is printed out on interpreter startup.  This value may be overridden\nvia an optional argument to the cmdloop() method.\n\nThe data members `self.doc_header', `self.misc_header', and\n`self.undoc_header' set the headers used for the help function's\nlistings of documented functions, miscellaneous topics, and undocumented\nfunctions respectively.\n\nThese interpreters use raw_input; thus, if the readline module is loaded,\nthey automatically support Emacs-like command history and editing features.\n"));
      var1.setline(46);
      PyString.fromInterned("A generic class to build line-oriented command interpreters.\n\nInterpreters constructed with this class obey the following conventions:\n\n1. End of file on input is processed as the command 'EOF'.\n2. A command is parsed out of each line by collecting the prefix composed\n   of characters in the identchars member.\n3. A command `foo' is dispatched to a method 'do_foo()'; the do_ method\n   is passed a single argument consisting of the remainder of the line.\n4. Typing an empty line repeats the last command.  (Actually, it calls the\n   method `emptyline', which may be overridden in a subclass.)\n5. There is a predefined `help' method.  Given an argument `topic', it\n   calls the command `help_topic'.  With no arguments, it lists all topics\n   with defined help_ functions, broken into up to three topics; documented\n   commands, miscellaneous help topics, and undocumented commands.\n6. The command '?' is a synonym for `help'.  The command '!' is a synonym\n   for `shell', if a do_shell method exists.\n7. If completion is enabled, completing commands will be done automatically,\n   and completing of commands args is done by calling complete_foo() with\n   arguments text, line, begidx, endidx.  text is string we are matching\n   against, all returned matches must begin with it.  line is the current\n   input line (lstripped), begidx and endidx are the beginning and end\n   indexes of the text being matched, which could be used to provide\n   different completion depending upon which position the argument is in.\n\nThe `default' method may be overridden to intercept commands for which there\nis no do_ method.\n\nThe `completedefault' method may be overridden to intercept completions for\ncommands that have no complete_ method.\n\nThe data member `self.ruler' sets the character used to draw separator lines\nin the help messages.  If empty, no ruler line is drawn.  It defaults to \"=\".\n\nIf the value of `self.intro' is nonempty when the cmdloop method is called,\nit is printed out on interpreter startup.  This value may be overridden\nvia an optional argument to the cmdloop() method.\n\nThe data members `self.doc_header', `self.misc_header', and\n`self.undoc_header' set the headers used for the help function's\nlistings of documented functions, miscellaneous topics, and undocumented\nfunctions respectively.\n\nThese interpreters use raw_input; thus, if the readline module is loaded,\nthey automatically support Emacs-like command history and editing features.\n");
      var1.setline(48);
      PyObject var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var1.setline(50);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("Cmd")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(52);
      PyString var6 = PyString.fromInterned("(Cmd) ");
      var1.setlocal("PROMPT", var6);
      var3 = null;
      var1.setline(53);
      var3 = var1.getname("string").__getattr__("ascii_letters")._add(var1.getname("string").__getattr__("digits"))._add(PyString.fromInterned("_"));
      var1.setlocal("IDENTCHARS", var3);
      var3 = null;
      var1.setline(55);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Cmd", var7, Cmd$1);
      var1.setlocal("Cmd", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Cmd$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A simple framework for writing line-oriented command interpreters.\n\n    These are often useful for test harnesses, administrative tools, and\n    prototypes that will later be wrapped in a more sophisticated interface.\n\n    A Cmd instance or subclass instance is a line-oriented interpreter\n    framework.  There is no good reason to instantiate Cmd itself; rather,\n    it's useful as a superclass of an interpreter class you define yourself\n    in order to inherit Cmd's methods and encapsulate action methods.\n\n    "));
      var1.setline(66);
      PyString.fromInterned("A simple framework for writing line-oriented command interpreters.\n\n    These are often useful for test harnesses, administrative tools, and\n    prototypes that will later be wrapped in a more sophisticated interface.\n\n    A Cmd instance or subclass instance is a line-oriented interpreter\n    framework.  There is no good reason to instantiate Cmd itself; rather,\n    it's useful as a superclass of an interpreter class you define yourself\n    in order to inherit Cmd's methods and encapsulate action methods.\n\n    ");
      var1.setline(67);
      PyObject var3 = var1.getname("PROMPT");
      var1.setlocal("prompt", var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getname("IDENTCHARS");
      var1.setlocal("identchars", var3);
      var3 = null;
      var1.setline(69);
      PyString var4 = PyString.fromInterned("=");
      var1.setlocal("ruler", var4);
      var3 = null;
      var1.setline(70);
      var4 = PyString.fromInterned("");
      var1.setlocal("lastcmd", var4);
      var3 = null;
      var1.setline(71);
      var3 = var1.getname("None");
      var1.setlocal("intro", var3);
      var3 = null;
      var1.setline(72);
      var4 = PyString.fromInterned("");
      var1.setlocal("doc_leader", var4);
      var3 = null;
      var1.setline(73);
      var4 = PyString.fromInterned("Documented commands (type help <topic>):");
      var1.setlocal("doc_header", var4);
      var3 = null;
      var1.setline(74);
      var4 = PyString.fromInterned("Miscellaneous help topics:");
      var1.setlocal("misc_header", var4);
      var3 = null;
      var1.setline(75);
      var4 = PyString.fromInterned("Undocumented commands:");
      var1.setlocal("undoc_header", var4);
      var3 = null;
      var1.setline(76);
      var4 = PyString.fromInterned("*** No help on %s");
      var1.setlocal("nohelp", var4);
      var3 = null;
      var1.setline(77);
      PyInteger var5 = Py.newInteger(1);
      var1.setlocal("use_rawinput", var5);
      var3 = null;
      var1.setline(79);
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("tab"), var1.getname("None"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$2, PyString.fromInterned("Instantiate a line-oriented interpreter framework.\n\n        The optional argument 'completekey' is the readline name of a\n        completion key; it defaults to the Tab key. If completekey is\n        not None and the readline module is available, command completion\n        is done automatically. The optional arguments stdin and stdout\n        specify alternate input and output file objects; if not specified,\n        sys.stdin and sys.stdout are used.\n\n        "));
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(102);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, cmdloop$3, PyString.fromInterned("Repeatedly issue a prompt, accept input, parse an initial prefix\n        off the received input, and dispatch to action methods, passing them\n        the remainder of the line as argument.\n\n        "));
      var1.setlocal("cmdloop", var7);
      var3 = null;
      var1.setline(154);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, precmd$4, PyString.fromInterned("Hook method executed just before the command line is\n        interpreted, but after the input prompt is generated and issued.\n\n        "));
      var1.setlocal("precmd", var7);
      var3 = null;
      var1.setline(161);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, postcmd$5, PyString.fromInterned("Hook method executed just after a command dispatch is finished."));
      var1.setlocal("postcmd", var7);
      var3 = null;
      var1.setline(165);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, preloop$6, PyString.fromInterned("Hook method executed once when the cmdloop() method is called."));
      var1.setlocal("preloop", var7);
      var3 = null;
      var1.setline(169);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, postloop$7, PyString.fromInterned("Hook method executed once when the cmdloop() method is about to\n        return.\n\n        "));
      var1.setlocal("postloop", var7);
      var3 = null;
      var1.setline(176);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, parseline$8, PyString.fromInterned("Parse the line into a command name and a string containing\n        the arguments.  Returns a tuple containing (command, args, line).\n        'command' and 'args' may be None if the line couldn't be parsed.\n        "));
      var1.setlocal("parseline", var7);
      var3 = null;
      var1.setline(196);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, onecmd$9, PyString.fromInterned("Interpret the argument as though it had been typed in response\n        to the prompt.\n\n        This may be overridden, but should not normally need to be;\n        see the precmd() and postcmd() methods for useful execution hooks.\n        The return value is a flag indicating whether interpretation of\n        commands by the interpreter should stop.\n\n        "));
      var1.setlocal("onecmd", var7);
      var3 = null;
      var1.setline(223);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, emptyline$10, PyString.fromInterned("Called when an empty line is entered in response to the prompt.\n\n        If this method is not overridden, it repeats the last nonempty\n        command entered.\n\n        "));
      var1.setlocal("emptyline", var7);
      var3 = null;
      var1.setline(233);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, default$11, PyString.fromInterned("Called on an input line when the command prefix is not recognized.\n\n        If this method is not overridden, it prints an error message and\n        returns.\n\n        "));
      var1.setlocal("default", var7);
      var3 = null;
      var1.setline(242);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, completedefault$12, PyString.fromInterned("Method called to complete an input line when no command-specific\n        complete_*() method is available.\n\n        By default, it returns an empty list.\n\n        "));
      var1.setlocal("completedefault", var7);
      var3 = null;
      var1.setline(251);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, completenames$13, (PyObject)null);
      var1.setlocal("completenames", var7);
      var3 = null;
      var1.setline(255);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, complete$14, PyString.fromInterned("Return the next possible completion for 'text'.\n\n        If a command has not been entered, then complete against command list.\n        Otherwise try to call complete_<command> to get list of completions.\n        "));
      var1.setlocal("complete", var7);
      var3 = null;
      var1.setline(285);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_names$15, (PyObject)null);
      var1.setlocal("get_names", var7);
      var3 = null;
      var1.setline(290);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, complete_help$16, (PyObject)null);
      var1.setlocal("complete_help", var7);
      var3 = null;
      var1.setline(296);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, do_help$18, PyString.fromInterned("List available commands with \"help\" or detailed help with \"help cmd\"."));
      var1.setlocal("do_help", var7);
      var3 = null;
      var1.setline(342);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, print_topics$19, (PyObject)null);
      var1.setlocal("print_topics", var7);
      var3 = null;
      var1.setline(350);
      var6 = new PyObject[]{Py.newInteger(80)};
      var7 = new PyFunction(var1.f_globals, var6, columnize$20, PyString.fromInterned("Display a list of strings as a compact set of columns.\n\n        Each column is only as wide as necessary.\n        Columns are separated by two spaces (one was not legible enough).\n        "));
      var1.setlocal("columnize", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Instantiate a line-oriented interpreter framework.\n\n        The optional argument 'completekey' is the readline name of a\n        completion key; it defaults to the Tab key. If completekey is\n        not None and the readline module is available, command completion\n        is done automatically. The optional arguments stdin and stdout\n        specify alternate input and output file objects; if not specified,\n        sys.stdin and sys.stdout are used.\n\n        ");
      var1.setline(90);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(92);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("stdin", var3);
         var3 = null;
      } else {
         var1.setline(94);
         var3 = var1.getlocal(4).__getattr__("stdin");
         var1.getlocal(0).__setattr__("stdin", var3);
         var3 = null;
      }

      var1.setline(95);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(96);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("stdout", var3);
         var3 = null;
      } else {
         var1.setline(98);
         var3 = var1.getlocal(4).__getattr__("stdout");
         var1.getlocal(0).__setattr__("stdout", var3);
         var3 = null;
      }

      var1.setline(99);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"cmdqueue", var4);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("completekey", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cmdloop$3(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("Repeatedly issue a prompt, accept input, parse an initial prefix\n        off the received input, and dispatch to action methods, passing them\n        the remainder of the line as argument.\n\n        ");
      var1.setline(109);
      var1.getlocal(0).__getattr__("preloop").__call__(var2);
      var1.setline(110);
      PyObject var10000 = var1.getlocal(0).__getattr__("use_rawinput");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("completekey");
      }

      PyException var3;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(112);
            PyObject var11 = imp.importOne("readline", var1, -1);
            var1.setlocal(2, var11);
            var3 = null;
            var1.setline(113);
            var11 = var1.getlocal(2).__getattr__("get_completer").__call__(var2);
            var1.getlocal(0).__setattr__("old_completer", var11);
            var3 = null;
            var1.setline(114);
            var1.getlocal(2).__getattr__("set_completer").__call__(var2, var1.getlocal(0).__getattr__("complete"));
            var1.setline(115);
            var1.getlocal(2).__getattr__("parse_and_bind").__call__(var2, var1.getlocal(0).__getattr__("completekey")._add(PyString.fromInterned(": complete")));
         } catch (Throwable var10) {
            var3 = Py.setException(var10, var1);
            if (!var3.match(var1.getglobal("ImportError"))) {
               throw var3;
            }

            var1.setline(117);
         }
      }

      var3 = null;

      PyException var4;
      PyObject var12;
      try {
         var1.setline(119);
         var12 = var1.getlocal(1);
         var10000 = var12._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(120);
            var12 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("intro", var12);
            var4 = null;
         }

         var1.setline(121);
         if (var1.getlocal(0).__getattr__("intro").__nonzero__()) {
            var1.setline(122);
            var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("intro"))._add(PyString.fromInterned("\n")));
         }

         var1.setline(123);
         var12 = var1.getglobal("None");
         var1.setlocal(3, var12);
         var4 = null;

         while(true) {
            var1.setline(124);
            if (!var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(144);
               var1.getlocal(0).__getattr__("postloop").__call__(var2);
               break;
            }

            var1.setline(125);
            if (var1.getlocal(0).__getattr__("cmdqueue").__nonzero__()) {
               var1.setline(126);
               var12 = var1.getlocal(0).__getattr__("cmdqueue").__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setlocal(4, var12);
               var4 = null;
            } else {
               var1.setline(128);
               if (var1.getlocal(0).__getattr__("use_rawinput").__nonzero__()) {
                  try {
                     var1.setline(130);
                     var12 = var1.getglobal("raw_input").__call__(var2, var1.getlocal(0).__getattr__("prompt"));
                     var1.setlocal(4, var12);
                     var4 = null;
                  } catch (Throwable var8) {
                     var4 = Py.setException(var8, var1);
                     if (!var4.match(var1.getglobal("EOFError"))) {
                        throw var4;
                     }

                     var1.setline(132);
                     PyString var5 = PyString.fromInterned("EOF");
                     var1.setlocal(4, var5);
                     var5 = null;
                  }
               } else {
                  var1.setline(134);
                  var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("prompt"));
                  var1.setline(135);
                  var1.getlocal(0).__getattr__("stdout").__getattr__("flush").__call__(var2);
                  var1.setline(136);
                  var12 = var1.getlocal(0).__getattr__("stdin").__getattr__("readline").__call__(var2);
                  var1.setlocal(4, var12);
                  var4 = null;
                  var1.setline(137);
                  if (var1.getglobal("len").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                     var1.setline(138);
                     PyString var13 = PyString.fromInterned("EOF");
                     var1.setlocal(4, var13);
                     var4 = null;
                  } else {
                     var1.setline(140);
                     var12 = var1.getlocal(4).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
                     var1.setlocal(4, var12);
                     var4 = null;
                  }
               }
            }

            var1.setline(141);
            var12 = var1.getlocal(0).__getattr__("precmd").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var12);
            var4 = null;
            var1.setline(142);
            var12 = var1.getlocal(0).__getattr__("onecmd").__call__(var2, var1.getlocal(4));
            var1.setlocal(3, var12);
            var4 = null;
            var1.setline(143);
            var12 = var1.getlocal(0).__getattr__("postcmd").__call__(var2, var1.getlocal(3), var1.getlocal(4));
            var1.setlocal(3, var12);
            var4 = null;
         }
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(146);
         var10000 = var1.getlocal(0).__getattr__("use_rawinput");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("completekey");
         }

         if (var10000.__nonzero__()) {
            try {
               var1.setline(148);
               var12 = imp.importOne("readline", var1, -1);
               var1.setlocal(2, var12);
               var4 = null;
               var1.setline(149);
               var1.getlocal(2).__getattr__("set_completer").__call__(var2, var1.getlocal(0).__getattr__("old_completer"));
            } catch (Throwable var6) {
               var4 = Py.setException(var6, var1);
               if (!var4.match(var1.getglobal("ImportError"))) {
                  throw var4;
               }

               var1.setline(151);
            }
         }

         throw (Throwable)var9;
      }

      var1.setline(146);
      var10000 = var1.getlocal(0).__getattr__("use_rawinput");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("completekey");
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(148);
            var12 = imp.importOne("readline", var1, -1);
            var1.setlocal(2, var12);
            var4 = null;
            var1.setline(149);
            var1.getlocal(2).__getattr__("set_completer").__call__(var2, var1.getlocal(0).__getattr__("old_completer"));
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (!var4.match(var1.getglobal("ImportError"))) {
               throw var4;
            }

            var1.setline(151);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject precmd$4(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyString.fromInterned("Hook method executed just before the command line is\n        interpreted, but after the input prompt is generated and issued.\n\n        ");
      var1.setline(159);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject postcmd$5(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyString.fromInterned("Hook method executed just after a command dispatch is finished.");
      var1.setline(163);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject preloop$6(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyString.fromInterned("Hook method executed once when the cmdloop() method is called.");
      var1.setline(167);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject postloop$7(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyString.fromInterned("Hook method executed once when the cmdloop() method is about to\n        return.\n\n        ");
      var1.setline(174);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseline$8(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyString.fromInterned("Parse the line into a command name and a string containing\n        the arguments.  Returns a tuple containing (command, args, line).\n        'command' and 'args' may be None if the line couldn't be parsed.\n        ");
      var1.setline(181);
      PyObject var3 = var1.getlocal(1).__getattr__("strip").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(182);
      PyTuple var7;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(183);
         var7 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(184);
         PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var4._eq(PyString.fromInterned("?"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(185);
            var4 = PyString.fromInterned("help ")._add(var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(1, var4);
            var4 = null;
         } else {
            var1.setline(186);
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(PyString.fromInterned("!"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(187);
               if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("do_shell")).__nonzero__()) {
                  var1.setline(190);
                  var7 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getlocal(1)});
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setline(188);
               var4 = PyString.fromInterned("shell ")._add(var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               var1.setlocal(1, var4);
               var4 = null;
            }
         }

         var1.setline(191);
         PyTuple var8 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
         PyObject[] var5 = Py.unpackSequence(var8, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;

         while(true) {
            var1.setline(192);
            var4 = var1.getlocal(2);
            var10000 = var4._lt(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(1).__getitem__(var1.getlocal(2));
               var10000 = var4._in(var1.getlocal(0).__getattr__("identchars"));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(193);
               var8 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null), var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2)});
               var5 = Py.unpackSequence(var8, 2);
               var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(194);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(1)});
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(192);
            var4 = var1.getlocal(2)._add(Py.newInteger(1));
            var1.setlocal(2, var4);
            var4 = null;
         }
      }
   }

   public PyObject onecmd$9(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyString.fromInterned("Interpret the argument as though it had been typed in response\n        to the prompt.\n\n        This may be overridden, but should not normally need to be;\n        see the precmd() and postcmd() methods for useful execution hooks.\n        The return value is a flag indicating whether interpretation of\n        commands by the interpreter should stop.\n\n        ");
      var1.setline(206);
      PyObject var3 = var1.getlocal(0).__getattr__("parseline").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(207);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(208);
         var3 = var1.getlocal(0).__getattr__("emptyline").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(209);
         PyObject var7 = var1.getlocal(2);
         PyObject var10000 = var7._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(210);
            var3 = var1.getlocal(0).__getattr__("default").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(211);
            var7 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("lastcmd", var7);
            var4 = null;
            var1.setline(212);
            var7 = var1.getlocal(1);
            var10000 = var7._eq(PyString.fromInterned("EOF"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(213);
               PyString var8 = PyString.fromInterned("");
               var1.getlocal(0).__setattr__((String)"lastcmd", var8);
               var4 = null;
            }

            var1.setline(214);
            var7 = var1.getlocal(2);
            var10000 = var7._eq(PyString.fromInterned(""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(215);
               var3 = var1.getlocal(0).__getattr__("default").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               try {
                  var1.setline(218);
                  var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("do_")._add(var1.getlocal(2)));
                  var1.setlocal(4, var7);
                  var4 = null;
               } catch (Throwable var6) {
                  PyException var9 = Py.setException(var6, var1);
                  if (var9.match(var1.getglobal("AttributeError"))) {
                     var1.setline(220);
                     var3 = var1.getlocal(0).__getattr__("default").__call__(var2, var1.getlocal(1));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  throw var9;
               }

               var1.setline(221);
               var3 = var1.getlocal(4).__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject emptyline$10(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyString.fromInterned("Called when an empty line is entered in response to the prompt.\n\n        If this method is not overridden, it repeats the last nonempty\n        command entered.\n\n        ");
      var1.setline(230);
      if (var1.getlocal(0).__getattr__("lastcmd").__nonzero__()) {
         var1.setline(231);
         PyObject var3 = var1.getlocal(0).__getattr__("onecmd").__call__(var2, var1.getlocal(0).__getattr__("lastcmd"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject default$11(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Called on an input line when the command prefix is not recognized.\n\n        If this method is not overridden, it prints an error message and\n        returns.\n\n        ");
      var1.setline(240);
      var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("*** Unknown syntax: %s\n")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject completedefault$12(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("Method called to complete an input line when no command-specific\n        complete_*() method is available.\n\n        By default, it returns an empty list.\n\n        ");
      var1.setline(249);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject completenames$13(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyObject var3 = PyString.fromInterned("do_")._add(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(253);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(253);
      var3 = var1.getlocal(0).__getattr__("get_names").__call__(var2).__iter__();

      while(true) {
         var1.setline(253);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(253);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(5, var4);
         var1.setline(253);
         if (var1.getlocal(5).__getattr__("startswith").__call__(var2, var1.getlocal(3)).__nonzero__()) {
            var1.setline(253);
            var1.getlocal(4).__call__(var2, var1.getlocal(5).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null));
         }
      }
   }

   public PyObject complete$14(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Return the next possible completion for 'text'.\n\n        If a command has not been entered, then complete against command list.\n        Otherwise try to call complete_<command> to get list of completions.\n        ");
      var1.setline(261);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(262);
         var3 = imp.importOne("readline", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(263);
         var3 = var1.getlocal(3).__getattr__("get_line_buffer").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(264);
         var3 = var1.getlocal(4).__getattr__("lstrip").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(265);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(266);
         var3 = var1.getlocal(3).__getattr__("get_begidx").__call__(var2)._sub(var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(267);
         var3 = var1.getlocal(3).__getattr__("get_endidx").__call__(var2)._sub(var1.getlocal(6));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(268);
         var3 = var1.getlocal(7);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(278);
            var3 = var1.getlocal(0).__getattr__("completenames");
            var1.setlocal(12, var3);
            var3 = null;
         } else {
            var1.setline(269);
            var3 = var1.getlocal(0).__getattr__("parseline").__call__(var2, var1.getlocal(5));
            PyObject[] var4 = Py.unpackSequence(var3, 3);
            PyObject var5 = var4[0];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(10, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(11, var5);
            var5 = null;
            var3 = null;
            var1.setline(270);
            var3 = var1.getlocal(9);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(271);
               var3 = var1.getlocal(0).__getattr__("completedefault");
               var1.setlocal(12, var3);
               var3 = null;
            } else {
               try {
                  var1.setline(274);
                  var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("complete_")._add(var1.getlocal(9)));
                  var1.setlocal(12, var3);
                  var3 = null;
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (!var10.match(var1.getglobal("AttributeError"))) {
                     throw var10;
                  }

                  var1.setline(276);
                  PyObject var8 = var1.getlocal(0).__getattr__("completedefault");
                  var1.setlocal(12, var8);
                  var4 = null;
               }
            }
         }

         var1.setline(279);
         var3 = var1.getlocal(12).__call__(var2, var1.getlocal(1), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8));
         var1.getlocal(0).__setattr__("completion_matches", var3);
         var3 = null;
      }

      try {
         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("completion_matches").__getitem__(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var9 = Py.setException(var6, var1);
         if (var9.match(var1.getglobal("IndexError"))) {
            var1.setline(283);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var9;
         }
      }
   }

   public PyObject get_names$15(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0).__getattr__("__class__"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject complete_help$16(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(291);
      PyObject var10000 = var1.getglobal("set");
      PyObject var10002 = var1.getlocal(0).__getattr__("completenames");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getderef(0), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(292);
      var10000 = var1.getglobal("set");
      var1.setline(292);
      PyObject var10004 = var1.f_globals;
      var3 = Py.EmptyObjects;
      PyCode var10006 = f$17;
      PyObject[] var6 = new PyObject[]{var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10004, var3, var10006, (PyObject)null, var6);
      var10002 = var7.__call__(var2, var1.getlocal(0).__getattr__("get_names").__call__(var2).__iter__());
      Arrays.fill(var3, (Object)null);
      var5 = var10000.__call__(var2, var10002);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(294);
      var5 = var1.getglobal("list").__call__(var2, var1.getlocal(2)._or(var1.getlocal(3)));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$17(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(292);
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

      do {
         var1.setline(292);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(293);
      } while(!var1.getlocal(1).__getattr__("startswith").__call__(var2, PyString.fromInterned("help_")._add(var1.getderef(0).__getitem__(Py.newInteger(0)))).__nonzero__());

      var1.setline(292);
      var1.setline(292);
      var6 = var1.getlocal(1).__getslice__(Py.newInteger(5), (PyObject)null, (PyObject)null);
      var1.f_lasti = 1;
      var5 = new Object[]{null, null, null, var3, var4};
      var1.f_savedlocals = var5;
      return var6;
   }

   public PyObject do_help$18(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyString.fromInterned("List available commands with \"help\" or detailed help with \"help cmd\".");
      var1.setline(298);
      PyException var3;
      PyObject var8;
      PyObject var9;
      if (var1.getlocal(1).__nonzero__()) {
         try {
            var1.setline(301);
            var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("help_")._add(var1.getlocal(1)));
            var1.setlocal(2, var8);
            var3 = null;
         } catch (Throwable var7) {
            var3 = Py.setException(var7, var1);
            if (var3.match(var1.getglobal("AttributeError"))) {
               PyException var4;
               try {
                  var1.setline(304);
                  var9 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("do_")._add(var1.getlocal(1))).__getattr__("__doc__");
                  var1.setlocal(3, var9);
                  var4 = null;
                  var1.setline(305);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(306);
                     var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(3))));
                     var1.setline(307);
                     var1.f_lasti = -1;
                     return Py.None;
                  }
               } catch (Throwable var6) {
                  var4 = Py.setException(var6, var1);
                  if (!var4.match(var1.getglobal("AttributeError"))) {
                     throw var4;
                  }

                  var1.setline(309);
               }

               var1.setline(310);
               var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("nohelp")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})))));
               var1.setline(311);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var3;
         }

         var1.setline(312);
         var1.getlocal(2).__call__(var2);
      } else {
         var1.setline(314);
         var8 = var1.getlocal(0).__getattr__("get_names").__call__(var2);
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(315);
         PyList var10 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var10);
         var3 = null;
         var1.setline(316);
         var10 = new PyList(Py.EmptyObjects);
         var1.setlocal(6, var10);
         var3 = null;
         var1.setline(317);
         PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(7, var12);
         var3 = null;
         var1.setline(318);
         var8 = var1.getlocal(4).__iter__();

         label67:
         while(true) {
            var1.setline(318);
            var9 = var8.__iternext__();
            PyObject var5;
            PyObject var10000;
            if (var9 == null) {
               var1.setline(321);
               var1.getlocal(4).__getattr__("sort").__call__(var2);
               var1.setline(323);
               PyString var13 = PyString.fromInterned("");
               var1.setlocal(9, var13);
               var3 = null;
               var1.setline(324);
               var8 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(324);
                  var9 = var8.__iternext__();
                  if (var9 == null) {
                     var1.setline(337);
                     var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("doc_leader"))));
                     var1.setline(338);
                     var1.getlocal(0).__getattr__("print_topics").__call__(var2, var1.getlocal(0).__getattr__("doc_header"), var1.getlocal(5), Py.newInteger(15), Py.newInteger(80));
                     var1.setline(339);
                     var1.getlocal(0).__getattr__("print_topics").__call__(var2, var1.getlocal(0).__getattr__("misc_header"), var1.getlocal(7).__getattr__("keys").__call__(var2), Py.newInteger(15), Py.newInteger(80));
                     var1.setline(340);
                     var1.getlocal(0).__getattr__("print_topics").__call__(var2, var1.getlocal(0).__getattr__("undoc_header"), var1.getlocal(6), Py.newInteger(15), Py.newInteger(80));
                     break label67;
                  }

                  var1.setlocal(8, var9);
                  var1.setline(325);
                  var5 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                  var10000 = var5._eq(PyString.fromInterned("do_"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(326);
                     var5 = var1.getlocal(8);
                     var10000 = var5._eq(var1.getlocal(9));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(328);
                        var5 = var1.getlocal(8);
                        var1.setlocal(9, var5);
                        var5 = null;
                        var1.setline(329);
                        var5 = var1.getlocal(8).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
                        var1.setlocal(10, var5);
                        var5 = null;
                        var1.setline(330);
                        var5 = var1.getlocal(10);
                        var10000 = var5._in(var1.getlocal(7));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(331);
                           var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(10));
                           var1.setline(332);
                           var1.getlocal(7).__delitem__(var1.getlocal(10));
                        } else {
                           var1.setline(333);
                           if (var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(8)).__getattr__("__doc__").__nonzero__()) {
                              var1.setline(334);
                              var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(10));
                           } else {
                              var1.setline(336);
                              var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(10));
                           }
                        }
                     }
                  }
               }
            }

            var1.setlocal(8, var9);
            var1.setline(319);
            var5 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
            var10000 = var5._eq(PyString.fromInterned("help_"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(320);
               PyInteger var11 = Py.newInteger(1);
               var1.getlocal(7).__setitem__((PyObject)var1.getlocal(8).__getslice__(Py.newInteger(5), (PyObject)null, (PyObject)null), var11);
               var5 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_topics$19(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(344);
         var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1))));
         var1.setline(345);
         if (var1.getlocal(0).__getattr__("ruler").__nonzero__()) {
            var1.setline(346);
            var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("ruler")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1))))));
         }

         var1.setline(347);
         var1.getlocal(0).__getattr__("columnize").__call__(var2, var1.getlocal(2), var1.getlocal(4)._sub(Py.newInteger(1)));
         var1.setline(348);
         var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject columnize$20(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyString.fromInterned("Display a list of strings as a compact set of columns.\n\n        Each column is only as wide as necessary.\n        Columns are separated by two spaces (one was not legible enough).\n        ");
      var1.setline(356);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(357);
         var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<empty>\n"));
         var1.setline(358);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(359);
         PyList var10000 = new PyList();
         PyObject var3 = var10000.__getattr__("append");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(359);
         var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

         while(true) {
            var1.setline(359);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(359);
               var1.dellocal(4);
               PyList var10 = var10000;
               var1.setlocal(3, var10);
               var3 = null;
               var1.setline(361);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(362);
                  throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("list[i] not a string for i in %s")._mod(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(3)))));
               } else {
                  var1.setline(364);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(365);
                  var3 = var1.getlocal(6);
                  PyObject var15 = var3._eq(Py.newInteger(1));
                  var3 = null;
                  if (var15.__nonzero__()) {
                     var1.setline(366);
                     var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)))));
                     var1.setline(367);
                     var1.f_lasti = -1;
                     return Py.None;
                  } else {
                     var1.setline(369);
                     var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

                     PyObject var5;
                     PyObject var6;
                     PyInteger var7;
                     PyList var11;
                     PyObject var13;
                     do {
                        var1.setline(369);
                        var4 = var3.__iternext__();
                        PyInteger var12;
                        if (var4 == null) {
                           var1.setline(388);
                           var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                           var1.setlocal(7, var5);
                           var5 = null;
                           var1.setline(389);
                           var12 = Py.newInteger(1);
                           var1.setlocal(8, var12);
                           var5 = null;
                           var1.setline(390);
                           var11 = new PyList(new PyObject[]{Py.newInteger(0)});
                           var1.setlocal(9, var11);
                           var5 = null;
                           break;
                        }

                        var1.setlocal(7, var4);
                        var1.setline(370);
                        var5 = var1.getlocal(6)._add(var1.getlocal(7))._sub(Py.newInteger(1))._floordiv(var1.getlocal(7));
                        var1.setlocal(8, var5);
                        var5 = null;
                        var1.setline(371);
                        var11 = new PyList(Py.EmptyObjects);
                        var1.setlocal(9, var11);
                        var5 = null;
                        var1.setline(372);
                        var12 = Py.newInteger(-2);
                        var1.setlocal(10, var12);
                        var5 = null;
                        var1.setline(373);
                        var5 = var1.getglobal("range").__call__(var2, var1.getlocal(8)).__iter__();

                        do {
                           var1.setline(373);
                           var6 = var5.__iternext__();
                           if (var6 == null) {
                              break;
                           }

                           var1.setlocal(11, var6);
                           var1.setline(374);
                           var7 = Py.newInteger(0);
                           var1.setlocal(12, var7);
                           var7 = null;
                           var1.setline(375);
                           var13 = var1.getglobal("range").__call__(var2, var1.getlocal(7)).__iter__();

                           while(true) {
                              var1.setline(375);
                              PyObject var8 = var13.__iternext__();
                              if (var8 == null) {
                                 break;
                              }

                              var1.setlocal(13, var8);
                              var1.setline(376);
                              PyObject var9 = var1.getlocal(13)._add(var1.getlocal(7)._mul(var1.getlocal(11)));
                              var1.setlocal(5, var9);
                              var9 = null;
                              var1.setline(377);
                              var9 = var1.getlocal(5);
                              var15 = var9._ge(var1.getlocal(6));
                              var9 = null;
                              if (var15.__nonzero__()) {
                                 break;
                              }

                              var1.setline(379);
                              var9 = var1.getlocal(1).__getitem__(var1.getlocal(5));
                              var1.setlocal(14, var9);
                              var9 = null;
                              var1.setline(380);
                              var9 = var1.getglobal("max").__call__(var2, var1.getlocal(12), var1.getglobal("len").__call__(var2, var1.getlocal(14)));
                              var1.setlocal(12, var9);
                              var9 = null;
                           }

                           var1.setline(381);
                           var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(12));
                           var1.setline(382);
                           var13 = var1.getlocal(10);
                           var13 = var13._iadd(var1.getlocal(12)._add(Py.newInteger(2)));
                           var1.setlocal(10, var13);
                           var1.setline(383);
                           var13 = var1.getlocal(10);
                           var15 = var13._gt(var1.getlocal(2));
                           var7 = null;
                        } while(!var15.__nonzero__());

                        var1.setline(385);
                        var5 = var1.getlocal(10);
                        var15 = var5._le(var1.getlocal(2));
                        var5 = null;
                     } while(!var15.__nonzero__());

                     var1.setline(391);
                     var3 = var1.getglobal("range").__call__(var2, var1.getlocal(7)).__iter__();

                     label79:
                     while(true) {
                        var1.setline(391);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        var1.setlocal(13, var4);
                        var1.setline(392);
                        var11 = new PyList(Py.EmptyObjects);
                        var1.setlocal(15, var11);
                        var5 = null;
                        var1.setline(393);
                        var5 = var1.getglobal("range").__call__(var2, var1.getlocal(8)).__iter__();

                        while(true) {
                           var1.setline(393);
                           var6 = var5.__iternext__();
                           if (var6 == null) {
                              while(true) {
                                 var1.setline(400);
                                 var15 = var1.getlocal(15);
                                 if (var15.__nonzero__()) {
                                    var15 = var1.getlocal(15).__getitem__(Py.newInteger(-1)).__not__();
                                 }

                                 if (!var15.__nonzero__()) {
                                    var1.setline(402);
                                    var5 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(15))).__iter__();

                                    while(true) {
                                       var1.setline(402);
                                       var6 = var5.__iternext__();
                                       if (var6 == null) {
                                          var1.setline(404);
                                          var1.getlocal(0).__getattr__("stdout").__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getglobal("str").__call__(var2, PyString.fromInterned("  ").__getattr__("join").__call__(var2, var1.getlocal(15)))));
                                          continue label79;
                                       }

                                       var1.setlocal(11, var6);
                                       var1.setline(403);
                                       var13 = var1.getlocal(15).__getitem__(var1.getlocal(11)).__getattr__("ljust").__call__(var2, var1.getlocal(9).__getitem__(var1.getlocal(11)));
                                       var1.getlocal(15).__setitem__(var1.getlocal(11), var13);
                                       var7 = null;
                                    }
                                 }

                                 var1.setline(401);
                                 var1.getlocal(15).__delitem__((PyObject)Py.newInteger(-1));
                              }
                           }

                           var1.setlocal(11, var6);
                           var1.setline(394);
                           var13 = var1.getlocal(13)._add(var1.getlocal(7)._mul(var1.getlocal(11)));
                           var1.setlocal(5, var13);
                           var7 = null;
                           var1.setline(395);
                           var13 = var1.getlocal(5);
                           var15 = var13._ge(var1.getlocal(6));
                           var7 = null;
                           if (var15.__nonzero__()) {
                              var1.setline(396);
                              PyString var14 = PyString.fromInterned("");
                              var1.setlocal(14, var14);
                              var7 = null;
                           } else {
                              var1.setline(398);
                              var13 = var1.getlocal(1).__getitem__(var1.getlocal(5));
                              var1.setlocal(14, var13);
                              var7 = null;
                           }

                           var1.setline(399);
                           var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(14));
                        }
                     }
                  }
               }
            }

            var1.setlocal(5, var4);
            var1.setline(360);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)), var1.getglobal("str")).__not__().__nonzero__()) {
               var1.setline(359);
               var1.getlocal(4).__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public cmd$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Cmd$1 = Py.newCode(0, var2, var1, "Cmd", 55, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "completekey", "stdin", "stdout", "sys"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 79, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "intro", "readline", "stop", "line"};
      cmdloop$3 = Py.newCode(2, var2, var1, "cmdloop", 102, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      precmd$4 = Py.newCode(2, var2, var1, "precmd", 154, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stop", "line"};
      postcmd$5 = Py.newCode(3, var2, var1, "postcmd", 161, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      preloop$6 = Py.newCode(1, var2, var1, "preloop", 165, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      postloop$7 = Py.newCode(1, var2, var1, "postloop", 169, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "i", "n", "cmd", "arg"};
      parseline$8 = Py.newCode(2, var2, var1, "parseline", 176, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "cmd", "arg", "func"};
      onecmd$9 = Py.newCode(2, var2, var1, "onecmd", 196, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      emptyline$10 = Py.newCode(1, var2, var1, "emptyline", 223, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      default$11 = Py.newCode(2, var2, var1, "default", 233, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ignored"};
      completedefault$12 = Py.newCode(2, var2, var1, "completedefault", 242, true, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "ignored", "dotext", "_[253_16]", "a"};
      completenames$13 = Py.newCode(3, var2, var1, "completenames", 251, true, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "state", "readline", "origline", "line", "stripped", "begidx", "endidx", "cmd", "args", "foo", "compfunc"};
      complete$14 = Py.newCode(3, var2, var1, "complete", 255, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_names$15 = Py.newCode(1, var2, var1, "get_names", 285, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "commands", "topics", "_(292_21)"};
      String[] var10001 = var2;
      cmd$py var10007 = self;
      var2 = new String[]{"args"};
      complete_help$16 = Py.newCode(2, var10001, var1, "complete_help", 290, true, false, var10007, 16, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "a"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"args"};
      f$17 = Py.newCode(1, var10001, var1, "<genexpr>", 292, false, false, var10007, 17, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "arg", "func", "doc", "names", "cmds_doc", "cmds_undoc", "help", "name", "prevname", "cmd"};
      do_help$18 = Py.newCode(2, var2, var1, "do_help", 296, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "cmds", "cmdlen", "maxcol"};
      print_topics$19 = Py.newCode(5, var2, var1, "print_topics", 342, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "displaywidth", "nonstrings", "_[359_22]", "i", "size", "nrows", "ncols", "colwidths", "totwidth", "col", "colwidth", "row", "x", "texts"};
      columnize$20 = Py.newCode(3, var2, var1, "columnize", 350, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cmd$py("cmd$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cmd$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Cmd$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.cmdloop$3(var2, var3);
         case 4:
            return this.precmd$4(var2, var3);
         case 5:
            return this.postcmd$5(var2, var3);
         case 6:
            return this.preloop$6(var2, var3);
         case 7:
            return this.postloop$7(var2, var3);
         case 8:
            return this.parseline$8(var2, var3);
         case 9:
            return this.onecmd$9(var2, var3);
         case 10:
            return this.emptyline$10(var2, var3);
         case 11:
            return this.default$11(var2, var3);
         case 12:
            return this.completedefault$12(var2, var3);
         case 13:
            return this.completenames$13(var2, var3);
         case 14:
            return this.complete$14(var2, var3);
         case 15:
            return this.get_names$15(var2, var3);
         case 16:
            return this.complete_help$16(var2, var3);
         case 17:
            return this.f$17(var2, var3);
         case 18:
            return this.do_help$18(var2, var3);
         case 19:
            return this.print_topics$19(var2, var3);
         case 20:
            return this.columnize$20(var2, var3);
         default:
            return null;
      }
   }
}
